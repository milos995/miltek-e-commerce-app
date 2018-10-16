package rs.fon.miltek.home.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import rs.fon.miltek.R;
import rs.fon.miltek.home.ChangePasswordActivity;
import rs.fon.miltek.utility.ErrorMessageDialog;
import rs.fon.miltek.utility.SharedPreferenceUtils;
import rs.fon.miltek.utility.VolleyRequestQueue;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccountFragment extends Fragment {
    TextView tvFullName, tvEmail, tvSave, tvChangePassword;
    EditText etCity, etZipCode, etAddress, etPhone;

    public MyAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_account, container, false);
        tvFullName = (TextView) view.findViewById(R.id.tvFullName);
        tvEmail = (TextView) view.findViewById(R.id.tvEmail);
        tvSave = (TextView) view.findViewById(R.id.tvSave);
        tvChangePassword = (TextView) view.findViewById(R.id.tvChangePassword);
        etCity = (EditText) view.findViewById(R.id.etCity);
        etZipCode = (EditText) view.findViewById(R.id.etZipCode);
        etAddress = (EditText) view.findViewById(R.id.etAddress);
        etPhone = (EditText) view.findViewById(R.id.etPhone);

        loadData();

        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAddress();
            }
        });

        tvChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void loadData() {
        tvFullName.setText(getString(R.string.full_name_placeholder, SharedPreferenceUtils.getInstance().getString("full_name")));
        tvEmail.setText(getString(R.string.email_placeholder, SharedPreferenceUtils.getInstance().getString("email")));
        etCity.setText(SharedPreferenceUtils.getInstance().getString("city"));
        etAddress.setText(SharedPreferenceUtils.getInstance().getString("address"));
        etPhone.setText(SharedPreferenceUtils.getInstance().getString("phone"));
        if(SharedPreferenceUtils.getInstance().getInt("zip_code") != 0){
            etZipCode.setText(SharedPreferenceUtils.getInstance().getInt("zip_code")+"");
        }
    }

    private void saveAddress() {
        try {
            String url = "https://miltek.000webhostapp.com/api/shipping_address/save_shipping_address.php";

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("user_id", SharedPreferenceUtils.getInstance().getInt("user_id"));
            jsonBody.put("city", etCity.getText().toString());
            if(etZipCode.getText().toString().equals("")){
                jsonBody.put("zip_code", 0);
            }else {
                jsonBody.put("zip_code", Integer.parseInt(etZipCode.getText().toString()));
            }
            jsonBody.put("address", etAddress.getText().toString());
            jsonBody.put("phone", etPhone.getText().toString());

            JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        int status = response.getInt("status");
                        if(status == 0){
                            String message = response.getString("message");
                            showError(message);
                        }else{
                            String message = response.getString("message");
                            showSuccess(message);
                            SharedPreferenceUtils.getInstance().saveString("city", etCity.getText().toString());
                            SharedPreferenceUtils.getInstance().saveInt("zip_code", Integer.parseInt(etZipCode.getText().toString()));
                            SharedPreferenceUtils.getInstance().saveString("address", etAddress.getText().toString());
                            SharedPreferenceUtils.getInstance().saveString("phone", etPhone.getText().toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    showError(getString(R.string.server_error));
                }
            });

            VolleyRequestQueue.getInstance(getContext()).addToRequestQueue(jor);
        }catch (JSONException ex){
            ex.printStackTrace();
        }
    }

    private void showError(String errorMessage) {
        ErrorMessageDialog.showMessage(getString(R.string.error), errorMessage, getContext());
    }

    private void showSuccess(String successMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setTitle(R.string.success);
        builder.setMessage(successMessage);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}
