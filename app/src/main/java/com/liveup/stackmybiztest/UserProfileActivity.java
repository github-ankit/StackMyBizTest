package com.liveup.stackmybiztest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.liveup.stackmybiztest.databinding.ActivityLoginBinding;
import com.liveup.stackmybiztest.databinding.ActivityUserProfileBinding;
import com.squareup.picasso.Picasso;

public class UserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUserProfileBinding binding = ActivityUserProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences localSharedPreferences = this.getSharedPreferences("UserDetails", 0);


        try {
            binding.profileName.setText(localSharedPreferences.getString(Constants.USERNAME, ""));
            binding.userEmail.setText(localSharedPreferences.getString(Constants.USEREMAIL, ""));

            binding.ImageNotFoundTV.setVisibility(View.GONE);
            Picasso.get().load(localSharedPreferences.getString(Constants.USERPIC, "")).into(binding.profileIV);

        }
        catch (Exception e){
            Log.d("Error : ", e.getMessage());
            Utils.showToast(this,"Something went wrong");
        }

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);
                builder.setTitle("Logout ");
                builder.setMessage("Do you want to Logout?");
                builder.setCancelable(false);


                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                signOut();
                            }
                        });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });

    }

    private void signOut() {


        GoogleSignInClient mGoogleSignInClient;

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(UserProfileActivity.this, gso);
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(UserProfileActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Utils.showToast(UserProfileActivity.this, "Signed Out");
                        finish();
                    }
                });
    }

}