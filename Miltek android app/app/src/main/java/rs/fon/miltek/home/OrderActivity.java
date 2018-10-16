package rs.fon.miltek.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

import rs.fon.miltek.R;
import rs.fon.miltek.databinding.ActivityOrderBinding;
import rs.fon.miltek.utility.ErrorMessageDialog;
import rs.fon.miltek.utility.SharedPreferenceUtils;
import rs.fon.miltek.utility.VolleyRequestQueue;

public class OrderActivity extends AppCompatActivity{
    private ActivityOrderBinding binding;
    private ActionBar actionBar;
    private DecimalFormat df = new DecimalFormat("#0.00");
    private double total;
    private String quantity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getString(R.string.order));

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            total = extras.getDouble("TOTAL");
            quantity = extras.getString("QUANTITY");
            binding.tvInTotal.setText(df.format(total)+" RSD");
            binding.tvOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    placeOrder();
                }
            });
        }

        binding.tvMyAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.etCity.setText(SharedPreferenceUtils.getInstance().getString("city"));
                binding.etAddress.setText(SharedPreferenceUtils.getInstance().getString("address"));
                binding.etPhone.setText(SharedPreferenceUtils.getInstance().getString("phone"));
                if(SharedPreferenceUtils.getInstance().getInt("zip_code") != 0){
                    binding.etZipCode.setText(SharedPreferenceUtils.getInstance().getInt("zip_code")+"");
                }
            }
        });
    }

    private void placeOrder() {
        if(binding.etCity.getText().toString().isEmpty() || binding.etZipCode.getText().toString().isEmpty()
                || binding.etAddress.getText().toString().isEmpty() || binding.etPhone.getText().toString().isEmpty()){
            showError(getString(R.string.empty_error));
            return;
        }

        try {
            String url = "https://miltek.000webhostapp.com/api/orders/place_order.php";

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("user_id", SharedPreferenceUtils.getInstance().getInt("user_id"));
            jsonBody.put("city", binding.etCity.getText().toString());
            jsonBody.put("zip_code", binding.etZipCode.getText().toString());
            jsonBody.put("address", binding.etAddress.getText().toString());
            jsonBody.put("phone", binding.etPhone.getText().toString());
            jsonBody.put("quantity", quantity);
            jsonBody.put("in_total", total);

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

            VolleyRequestQueue.getInstance(this).addToRequestQueue(jor);
        }catch (JSONException ex){
            ex.printStackTrace();
        }
    }

    private void showError(String errorMessage) {
        ErrorMessageDialog.showMessage(getString(R.string.error), errorMessage, this);
    }

    private void showSuccess(String successMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(R.string.success);
        builder.setMessage(successMessage);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(OrderActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        builder.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
