package com.techsell.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.techsell.global.AppConfig;
import com.techsell.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @Bind(R.id.txtSlogan)
    TextView txtSlogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);

        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(2500);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (AppConfig.isLogin(SplashActivity.this)) {
                        Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    }
                }

        };

        thread.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }


    @Override
    public void onBackPressed() {
    }
}
