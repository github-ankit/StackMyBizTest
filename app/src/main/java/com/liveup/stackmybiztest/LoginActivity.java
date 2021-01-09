package com.liveup.stackmybiztest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.liveup.stackmybiztest.databinding.ActivityLoginBinding;

import java.sql.Time;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 101;
    boolean doubleBackToExitPressedOnce = false;
    private GoogleSignInClient mGoogleSignInClient;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Users");


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


        binding.gSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signIn();
            }
        });

        createRequest();


    }

    private void createRequest() {

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


    }


    @Override
    protected void onStart() {
        super.onStart();

        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    private void updateUI(GoogleSignInAccount account) {

        if (account != null) {

            SharedPreferences.Editor localEditor = this.getSharedPreferences("UserDetails", 0).edit();
            localEditor.putString(Constants.USERNAME, account.getDisplayName());
            localEditor.putString(Constants.USEREMAIL, account.getEmail());
            localEditor.putString(Constants.USERPIC, account.getPhotoUrl().toString());

            localEditor.commit();

            Date d = new Date();
            CharSequence s  = DateFormat.format("EEEE, MMMM d, yyyy ", d.getTime());


            myRef.child("User").setValue(account.getEmail()+"Login Time - "+ s );




            startActivity(new Intent(LoginActivity.this, UserProfileActivity.class));
        }

    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Status :--", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
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