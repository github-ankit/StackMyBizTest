package com.liveup.stackmybiztest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.liveup.stackmybiztest.databinding.ActivityUserProfileBinding;
import com.squareup.picasso.Picasso;

public class UserProfileActivity extends AppCompatActivity {


    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    boolean mNeverAskPermission = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUserProfileBinding binding = ActivityUserProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //Getting user info saved in shared preferences
        SharedPreferences localSharedPreferences = this.getSharedPreferences("UserDetails", 0);

        try {
            binding.profileName.setText(localSharedPreferences.getString(Constants.USERNAME, ""));
            binding.userEmail.setText(localSharedPreferences.getString(Constants.USEREMAIL, ""));

            binding.ImageNotFoundTV.setVisibility(View.GONE);
            Picasso.get().load(localSharedPreferences.getString(Constants.USERPIC, "")).into(binding.profileIV);

        } catch (Exception e) {
            Log.d("Error : ", e.getMessage());
            Utils.showToast(this, "Something went wrong");
        }

        //Click listener
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


    // Broadcast receiver
    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String latitude = intent.getStringExtra(LocationMonitoringService.EXTRA_LATITUDE);
            String longitude = intent.getStringExtra(LocationMonitoringService.EXTRA_LONGITUDE);

            Utils.showToast(UserProfileActivity.this, latitude + " - " + longitude);
            Log.d("TAG- Getting location", latitude + " - " + longitude);


        }
    };

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


        // Unregestring the broadcast receiver and stoping the service
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        Intent intent = new Intent(this, LocationMonitoringService.class);
        stopService(intent);

    }


    /**
     * Step 1: Check Google Play services
     */
    private void checkGooglePlayService() {

        //Check whether this user has installed Google play service which is being used by Location updates.
        if (isGooglePlayServicesAvailable()) {

            //Passing null to indicate that it is executing for the first time.
            checkInternetConnectivity(null);

        } else {
            Utils.showToast(this,"Google play service not available");
        }
    }


    /**
     * Step 2: Check & Prompt Internet connection
     */
    private Boolean checkInternetConnectivity(DialogInterface dialog) {

        if (!Utils.CheckConnectivty(this)) {
            return false;
        }


        if (dialog != null) {
            dialog.dismiss();
        }

        //Yes there is active internet connection. Next check Location is granted by user or not.

        if (checkPermissions()) { //Yes permissions are granted by the user. Go to the next step.
            startLocationService();
        } else {  //No user has not granted the permissions yet. Request now.
            requestPermissions();
        }
        return true;
    }


    /**
     * Step 3: Start the Location Monitor Service
     */
    private void startLocationService() {

        //Start location sharing service to app server.........
        Intent intent = new Intent(this, LocationMonitoringService.class);
        startService(intent);

        Utils.showToast(this,"Location Tracking Started");
    }

    /**
     * Return the availability of GooglePlayServices
     */
    public boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(this, status, 2404).show();
            }
            return false;
        }
        return true;
    }


    /**
     * Return the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        int permissionState1 = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        int permissionState2 = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        return permissionState1 == PackageManager.PERMISSION_GRANTED && permissionState2 == PackageManager.PERMISSION_GRANTED;

    }


    /**
     * Start permissions requests.
     */
    private void requestPermissions() {

        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        boolean shouldProvideRationale2 =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION);


        // Provide an additional rationale to the img_user. This would happen if the img_user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale || shouldProvideRationale2) {
            Log.i("TAG", "Displaying permission rationale to provide additional context.");

            if (!mNeverAskPermission) {

                MaterialAlertDialogBuilder mAlertBuilder = new MaterialAlertDialogBuilder(this, R.style.MyDialogTheme);
                mAlertBuilder.setTitle("Permission required");
                mAlertBuilder.setMessage("Location permission is needed");

                String positiveText = getString(android.R.string.ok);
                mAlertBuilder.setPositiveButton(positiveText,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // dismiss alert dialog, update preferences with game score and restart play fragment

                                Log.d("myTag", "positive button clicked");
                                mNeverAskPermission = true;
                                ActivityCompat.requestPermissions(UserProfileActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                                        REQUEST_PERMISSIONS_REQUEST_CODE);
                                dialog.dismiss();

                            }
                        });
                mAlertBuilder.create().show();

            }
        } else {
            Log.i("TAG", "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the img_user denied the permission
            // previously and checked "Never ask again".


            ActivityCompat.requestPermissions(UserProfileActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        checkGooglePlayService();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Registering broadcast receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(
                broadcastReceiver, new IntentFilter(LocationMonitoringService.ACTION_LOCATION_BROADCAST));

    }

    @Override
    protected void onPause() {
        super.onPause();

        //Unregistering the broadcast so that it doesn't receive any further location data
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        signOut();
    }
}