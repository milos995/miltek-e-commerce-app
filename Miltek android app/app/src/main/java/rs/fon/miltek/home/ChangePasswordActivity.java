package rs.fon.miltek.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import rs.fon.miltek.R;
import rs.fon.miltek.databinding.ActivityChangePasswordBinding;
import rs.fon.miltek.login.SignInActivity;
import rs.fon.miltek.utility.DataValidation;
import rs.fon.miltek.utility.ErrorMessageDialog;
import rs.fon.miltek.utility.SharedPreferenceUtils;
import rs.fon.miltek.utility.VolleyRequestQueue;

public class ChangePasswordActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private ActivityChangePasswordBinding binding;
    private EditText etCurrentPassword, etNewPassword, etRetypePassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_password);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getString(R.string.change_password));

        etCurrentPassword = binding.etCurrentPassword;
        etNewPassword = binding.etNewPassword;
        etRetypePassword = binding.etRetypePassword;

        binding.tvChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
    }

    private void changePassword() {
        if(etCurrentPassword.getText().toString().isEmpty() || etNewPassword.getText().toString().isEmpty() || etRetypePassword.getText().toString().isEmpty()){
            showError(getString(R.string.empty_error));
            return;
        }

        if(!DataValidation.isValidPassword(etCurrentPassword.getText().toString()) || !DataValidation.isValidPassword(etNewPassword.getText().toString()) || !DataValidation.isValidPassword(etRetypePassword.getText().toString())){
            showError(getString(R.string.password_error));
            return;
        }

        if(!DataValidation.isValidRetypePassword(etNewPassword.getText().toString(), etRetypePassword.getText().toString())){
            showError(getString(R.string.retype_password_error));
            return;
        }

        try {
            String url = "https://miltek.000webhostapp.com/api/user/change_password.php";

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("user_id", SharedPreferenceUtils.getInstance().getInt("user_id"));
            jsonBody.put("current_password", etCurrentPassword.getText().toString());
            jsonBody.put("new_password", etNewPassword.getText().toString());

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

            VolleyRequestQueue.getInstance(ChangePasswordActivity.this).addToRequestQueue(jor);
        }catch (JSONException ex){
            ex.printStackTrace();
        }
    }

    private void showError(String errorMessage) {
        ErrorMessageDialog.showMessage(getString(R.string.error), errorMessage, ChangePasswordActivity.this);
    }

    private void showSuccess(String successMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);
        builder.setCancelable(false);
        builder.setTitle(R.string.success);
        builder.setMessage(successMessage);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                SharedPreferenceUtils.getInstance().clearAllValues();
                Intent intent = new Intent(ChangePasswordActivity.this, SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
