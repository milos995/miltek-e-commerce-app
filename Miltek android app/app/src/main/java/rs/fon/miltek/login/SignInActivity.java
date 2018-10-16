package rs.fon.miltek.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import rs.fon.miltek.R;
import rs.fon.miltek.databinding.ActivitySigninBinding;
import rs.fon.miltek.home.HomeActivity;
import rs.fon.miltek.utility.DataValidation;
import rs.fon.miltek.utility.ErrorMessageDialog;
import rs.fon.miltek.utility.SharedPreferenceUtils;
import rs.fon.miltek.utility.VolleyRequestQueue;

public class SignInActivity extends AppCompatActivity {
    private String TAG = "SignInActivity";
    private ActivitySigninBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signin);
        Log.e(TAG, "signin activity start");

        binding.tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        binding.tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        binding.tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void login() {
        if (binding.etEmail.getText().toString().isEmpty() || binding.etPassword.getText().toString().isEmpty()) {
            showError(getString(R.string.empty_error));
        } else {
            if (DataValidation.isValidEmail(binding.etEmail.getText().toString())) {
                try {
                    String url = "https://miltek.000webhostapp.com/api/user/login.php";

                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("email", binding.etEmail.getText().toString());
                    jsonBody.put("password", binding.etPassword.getText().toString());

                    JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                int status = response.getInt("status");
                                if(status == 0){
                                    String message = response.getString("message");
                                    showError(message);
                                }else{
                                    JSONObject data = response.getJSONObject("data");
                                    SharedPreferenceUtils.getInstance().saveInt("user_id", data.getInt("user_id"));
                                    SharedPreferenceUtils.getInstance().saveString("full_name", data.getString("full_name"));
                                    SharedPreferenceUtils.getInstance().saveString("email", data.getString("email"));
                                    SharedPreferenceUtils.getInstance().saveString("city", data.getString("city"));
                                    SharedPreferenceUtils.getInstance().saveInt("zip_code", data.getInt("zip_code"));
                                    SharedPreferenceUtils.getInstance().saveString("address", data.getString("address"));
                                    SharedPreferenceUtils.getInstance().saveString("phone", data.getString("phone"));
                                    showSuccess(getString(R.string.login_success));
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
            } else {
                showError(getString(R.string.email_error));
            }
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
                Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.show();
    }
}
