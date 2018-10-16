package rs.fon.miltek.login;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import rs.fon.miltek.R;
import rs.fon.miltek.databinding.ActivityForgotPasswordBinding;
import rs.fon.miltek.utility.DataValidation;
import rs.fon.miltek.utility.ErrorMessageDialog;
import rs.fon.miltek.utility.VolleyRequestQueue;

public class ForgotPasswordActivity extends AppCompatActivity{
    private String TAG = "ForgotPasswordActivity";
    private EditText email;
    private ActivityForgotPasswordBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password);

        Log.e(TAG, " start forgot password Activity");

        email = binding.etEmail;

        binding.tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.tvSendPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPassword();
            }
        });
    }

    private void sendPassword() {
        if(email.getText().toString().isEmpty()){
            showError(getString(R.string.email_empty_error));
            return;
        }

        if(!DataValidation.isValidEmail(email.getText().toString())){
            showError(getString(R.string.email_error));
            return;
        }

        try {
            String url = "https://miltek.000webhostapp.com/api/user/forgot_password.php";

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("email", email.getText().toString());

            JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e(TAG, response.toString());
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

        Log.e(TAG, " radi");
    }

    private void showSuccess(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(R.string.success);
        builder.setMessage(message);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        });
        builder.show();
    }

    private void showError(String errorMessage) {
        ErrorMessageDialog.showMessage(getString(R.string.error), errorMessage, this);
    }
}
