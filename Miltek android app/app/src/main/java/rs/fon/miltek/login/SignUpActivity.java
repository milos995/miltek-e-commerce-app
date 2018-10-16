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
import rs.fon.miltek.databinding.ActivitySignupBinding;
import rs.fon.miltek.utility.DataValidation;
import rs.fon.miltek.utility.ErrorMessageDialog;
import rs.fon.miltek.utility.VolleyRequestQueue;

public class SignUpActivity extends AppCompatActivity{
    private String TAG = "SignUpActivity";
    private ActivitySignupBinding binding;
    private EditText fullName, email, password, retypePassword;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);

        Log.e(TAG, " start signup Activity");

        fullName = binding.etFullName;
        email = binding.etEmail;
        password = binding.etPassword;
        retypePassword = binding.etRetypePassword;

        binding.tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        
        binding.tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

    }

    private void register() {
        if(fullName.getText().toString().isEmpty() || email.getText().toString().isEmpty() || password.getText().toString().isEmpty() || retypePassword.getText().toString().isEmpty()){
            showError(getString(R.string.empty_error));
            return;
        }

        if(!DataValidation.isValidEmail(email.getText().toString())){
            showError(getString(R.string.email_error));
            return;
        }

        if(!DataValidation.isValidPassword(password.getText().toString())){
            showError(getString(R.string.password_error));
            return;
        }

        if(!DataValidation.isValidRetypePassword(password.getText().toString(), retypePassword.getText().toString())){
            showError(getString(R.string.retype_password_error));
            return;
        }

        try {
            String url = "https://miltek.000webhostapp.com/api/user/register.php";

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("full_name", fullName.getText().toString());
            jsonBody.put("email", email.getText().toString());
            jsonBody.put("password", password.getText().toString());

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
                finish();
            }
        });
        builder.show();
    }
}
