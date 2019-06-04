package com.now.startupteamnow;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.location.Location;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static maes.tech.intentanim.CustomIntent.customType;

public class CameraResult extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private float lat;
    private float lon;
    private String username = "Nowteam";
    private String password = "5591980now";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_result);

        try {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setCaptureActivity(ScanCamera.class);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
            integrator.setCameraId(0);  // Use a specific camera of the device
            integrator.setBeepEnabled(false);
            integrator.initiateScan();
        } catch (Exception e) {
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Intent intent = new Intent(CameraResult.this, HomePage.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();

                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }
                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                lat = (float) location.getLatitude();
                                lon = (float) location.getLongitude();
                                }
                            }
                        });
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BuildConfig.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                JsonApi jsonApi = retrofit.create(JsonApi.class);
                Map<String, String> parameters = new HashMap<>();

                String base = username + ":" + password;
                String authhead = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

                final Call<QRcode> barcodeList = jsonApi.getBarcodeList(authhead, result.getContents());


                barcodeList.enqueue(new Callback<QRcode>() {
                    @Override
                    public void onResponse(Call<QRcode> call, Response<QRcode> response) {
                        if(!response.isSuccessful()){ Toast.makeText(CameraResult.this, "Cekilisde Xeta", Toast.LENGTH_LONG).show(); return;}
                        QRcode qRcodes = response.body();
                        assert qRcodes != null;
                        if(qRcodes.getCode().equals(result.getContents()) && qRcodes.getxCoordination() > lat -0.005 && qRcodes.getxCoordination() < lat+0.005 && qRcodes.getyCoordination() > lon-0.005 && qRcodes.getyCoordination() < lon+0.005){
                            Toast.makeText(CameraResult.this, "Bonus Kocuruldu", Toast.LENGTH_LONG).show();

                        }else{
                            Toast.makeText(CameraResult.this, "QR Code Movcud Deyil", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<QRcode> call, Throwable t) {

                    }
                });
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void PostBonus(int amount){
        SharedPreferences sp = this.getSharedPreferences("Login", MODE_PRIVATE);
        String token = sp.getString("token", null);
        final int userid = sp.getInt("id", 0);


        String authhead = "Basic Tm93dGVhbTo1NTkxOTgwTm93";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonApi jsonApi = retrofit.create(JsonApi.class);



        final Call<Integer> request = jsonApi.postBonus(authhead,userid,token, amount);

        request.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(!response.isSuccessful()) {return;}

                if(response.body() != null){
                    int res = response.body();
                    if(res != -1){
                        Toast.makeText(CameraResult.this, "Tamamlandı", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }

}
