package com.liveup.stackmybiztest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.liveup.stackmybiztest.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Utils.CheckConnectivty(this);

        binding.forgotPasswordTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, UserProfileActivity.class));
            }
        });

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utils.CheckConnectivty(LoginActivity.this)) {

                    if (binding.userNamEmailET.getText().toString().isEmpty()) {
                        Utils.showToast(LoginActivity.this, "Enter email id");
                    } else if (!Utils.isValidEmail(binding.userNamEmailET.getText().toString())) {
                        Utils.showToast(LoginActivity.this, "Enter valid email id");
                    } else if (binding.passwordET.getText().toString().isEmpty()) {
                        Utils.showToast(LoginActivity.this, "Enter password");
                    } else {
                        startActivity(new Intent(LoginActivity.this, UserProfileActivity.class));
                    }

                }


            }
        });


    }

    @Override
    public void onBackPressed() {

        if (!this.doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            Utils.showToast(this, "Press again to close app");

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 5000);

        } else {

            super.onBackPressed();

            finishAffinity();


        }
    }


}