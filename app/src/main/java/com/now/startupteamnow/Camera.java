package com.now.startupteamnow;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;

import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.camerakit.CameraKitView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;


import java.io.File;
import java.io.FileOutputStream;

public class Camera extends AppCompatActivity {

    private CameraKitView cameraKitView;
    private static final int REQUEST_LOCATION = 1;
    private Button captureBtn;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private LocationRequest locationRequest;
    private static final long UPDATE_INTERVAL = 1000, FASTEST_INTERVAL = 1000;
    private String lat, lon;
    private static boolean UpdateBegan = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        cameraKitView = findViewById(R.id.camera);
        captureBtn = findViewById(R.id.button);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        if(!checkPlayServices()){
            buildAlertMessageNoGoogleService();
            return;
        }

        if (!isLocationEnabled(Camera.this)){
            buildAlertMessageNoGps();
            return;
        }


        captureBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                if (ActivityCompat.checkSelfPermission(Camera.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    RequestPermission();
                    return;
                }


                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(Camera.this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    lat = String.valueOf(location.getLatitude());
                                    lon = String.valueOf(location.getLongitude());
                                    Toast.makeText(Camera.this, lat + " Last " + lon, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });



                cameraKitView.captureImage(new CameraKitView.ImageCallback() {
                    @Override
                    public void onImage(CameraKitView cameraKitView, final byte[] capturedImage) {
                        // capturedImage contains the image from the CameraKitView.
                        File savedPhoto = new File(Environment.getExternalStorageDirectory(), "lastImage.jpg");
                        try {
                            FileOutputStream outputStream = new FileOutputStream(savedPhoto.getPath());
                            outputStream.write(capturedImage);
                            outputStream.close();

                        } catch (java.io.IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }


    public static boolean isLocationEnabled(Context context) {
        return getLocationMode(context) != Settings.Secure.LOCATION_MODE_OFF;
    }

    private static int getLocationMode(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE, Settings.Secure.LOCATION_MODE_OFF);
    }

private void RequestPermission(){
        ActivityCompat.requestPermissions(Camera.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
}

    @Override
    protected void onStart() {
        super.onStart();
        cameraKitView.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraKitView.onResume();
        if(isLocationEnabled(Camera.this)){
            startLocationUpdates();
        }

    }

    @Override
    protected void onPause() {
        cameraKitView.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        cameraKitView.onStop();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Zəhmət olmasa GPS xidmətini aktiv edəsiniz")
                .setCancelable(false)
                .setPositiveButton("Yaxşı", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("Xeyr", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        Intent intent = new Intent(Camera.this, HomePage.class);
                        startActivity(intent);
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    protected void buildAlertMessageNoGoogleService() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Zəhmət olmasa Google Play Service yükləyib aktiv edəsiniz")
                .setCancelable(false)
                .setPositiveButton("Yaxşı", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        Intent intent = new Intent(Camera.this, HomePage.class);
                        startActivity(intent);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST);
            } else {
                finish();
            }

            return false;
        }

        return true;
    }

    private void startLocationUpdates() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "You need to enable permissions to display location !", Toast.LENGTH_SHORT).show();
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, new LocationCallback(){

            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    lat = String.valueOf(location.getLatitude());
                    lon = String.valueOf(location.getLongitude());
                    Toast.makeText(Camera.this, lat + " Update " + lon, Toast.LENGTH_LONG).show();
                }
            };

        },null);
    }
}
