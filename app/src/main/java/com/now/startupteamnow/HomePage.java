package com.now.startupteamnow;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static maes.tech.intentanim.CustomIntent.customType;

public class HomePage extends AppCompatActivity  {
    private TextView fullname, amount;
    private ImageView profileImage, backImage;
    private ProgressBar bar;
    public  String res;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final long UPDATE_INTERVAL = 1000, FASTEST_INTERVAL = 1000;
    private String lat, lon;
    private static boolean isLocGranted;
    private Toolbar mTopToolbar;
    private BottomNavigationView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        try{
            FindAllWidgets();
            if (!checkPlayServices()){
                buildAlertMessageNoGoogleService();
            }

            setSupportActionBar(mTopToolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

            fullname.setText(""); amount.setText(""); profileImage.setVisibility(View.INVISIBLE);


            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

            startLocationUpdates();

            GetUserInfo();

            view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.list:
                            Intent intent = new Intent(HomePage.this, List.class);
                            startActivity(intent);
                            customType(HomePage.this,"left-to-right");
                            return true;
                        case R.id.history:
                            Intent intent2 = new Intent(HomePage.this, History.class);
                            startActivity(intent2);
                            customType(HomePage.this,"right-to-left");
                            return true;
                        default:
                            return true;
                    }
                }
            });

        }
        catch (Exception e){
            Log.d("Qanli" ,e.toString());
        }

    }

    private void GetUserInfo(){
        JsonApi jsonApi = Request_And_API_Key.GetRetrofit().create(JsonApi.class);
        final Call<User> user = jsonApi.getUserWithPost(Request_And_API_Key.Api_Key, Request_And_API_Key.GetId(this), Request_And_API_Key.GetToken(this));

        user.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(HomePage.this, "Server İlə Əlaqədə Xəta Aşkarlandı", Toast.LENGTH_LONG).show();
                    return;
                }
                bar.setVisibility(View.INVISIBLE); profileImage.setVisibility(View.VISIBLE);

                if(response.body() != null){
                    User user1 = response.body();
                    if(user1.getId() > 0 && user1.getToken() != null){
                        String FullName = user1.getName() + " " + user1.getSurname();

                        ImageProcess(user1.getImgPath());

                        String textBonus = String.valueOf(user1.getBonus()); amount.setText(textBonus); fullname.setText(FullName);
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                bar.setVisibility(View.INVISIBLE); profileImage.setVisibility(View.VISIBLE);
                Toast.makeText(HomePage.this, "Server İlə Əlaqədə Xəta Aşkarlandı", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
         inflater.inflate(R.menu.bottom_navigation_menu, menu);
        return false;
    }

    public void OpenCamera(View view){
        if(!isLocGranted){
            startLocationUpdates();
        }
        else if(!isLocationEnabled(HomePage.this)){
            buildAlertMessageNoGps();
        }
        else{
            Intent intent = new Intent(this, CameraResult.class);
            this.startActivity(intent);
            customType(HomePage.this,"fadein-to-fadeout");
        }
    }

    private void startLocationUpdates() {
        try {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(HomePage.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {

                    ActivityCompat.requestPermissions(HomePage.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            0);
                }
            }
            else
                {
                isLocGranted = true;
                LocationRequest locationRequest = new LocationRequest();
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                locationRequest.setInterval(UPDATE_INTERVAL);
                locationRequest.setFastestInterval(FASTEST_INTERVAL);

                locationCallback =  new LocationCallback() {

                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        if (locationResult == null) {
                            return;
                        }
                        for (Location location : locationResult.getLocations()) {
                            lat = String.valueOf(location.getLatitude());
                            lon = String.valueOf(location.getLongitude());
                            Log.d("Location: " , lat + " " + lon);
                        }
                    }



                };
                fusedLocationClient.requestLocationUpdates(locationRequest,locationCallback, null);
            }

        }catch (Exception e){
            Toast.makeText(HomePage.this, "GPS`də Xəta \n Zəhmət Olmasa Tətbiqi Yenidən Başladın" , Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
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

    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    protected void buildAlertMessageNoGoogleService() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Zəhmət olmasa Google Play Service yükləyib aktiv edəsiniz")
                .setCancelable(false)
                .setPositiveButton("Yaxşı", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        finish();
                        System.exit(0);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
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
                        Toast.makeText(HomePage.this, "Zəhmət Olmasa GPS`i aktiv edin" , Toast.LENGTH_SHORT).show();
                        dialog.cancel();

                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public static boolean isLocationEnabled(Context context) {
        return getLocationMode(context) != Settings.Secure.LOCATION_MODE_OFF;
    }

    private static int getLocationMode(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE, Settings.Secure.LOCATION_MODE_OFF);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // If request is cancelled, the result arrays are empty.
        if (requestCode == 0) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                isLocGranted = true;
            } else {
                Toast.makeText(this, "Zəhmət Olmasa Gpa Icazesini Təsdiq Edin!", Toast.LENGTH_LONG).show();

                isLocGranted = false;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
            startLocationUpdates();
        view.setSelectedItemId(R.id.main_menu);
    }


    private void FindAllWidgets(){
        view = findViewById(R.id.bottom_navigation);
        view.setSelectedItemId(R.id.main_menu);
        fullname = findViewById(R.id.fullname);
        amount = findViewById(R.id.amount);
        profileImage = findViewById(R.id.profilimage);
        bar = findViewById(R.id.progressBar3);
        backImage = findViewById(R.id.BackImage);
        mTopToolbar = findViewById(R.id.mToolbar);
    }

    private void ImageProcess(String imageUrl){
        Glide.with(HomePage.this)
                .load(BuildConfig.BASE_URL + "images/"+imageUrl)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(profileImage);

        Glide.with(HomePage.this)
                .load(BuildConfig.BASE_URL + "images/"+imageUrl)
                .transform(new BlurTransformation(getApplicationContext()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(backImage);
    }

}


