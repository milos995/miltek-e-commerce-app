package rs.fon.miltek.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import rs.fon.miltek.R;
import rs.fon.miltek.home.HomeActivity;
import rs.fon.miltek.login.SignInActivity;
import rs.fon.miltek.utility.SharedPreferenceUtils;

public class SplashActivity extends AppCompatActivity{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }

    private void init() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // is user logged in
                if(SharedPreferenceUtils.getInstance().getString("email").equalsIgnoreCase("")){
                    // fo to signin activity
                    Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
                    startActivity(intent);
                }else{
                    // go to home activity
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, 3000);
    }
}
