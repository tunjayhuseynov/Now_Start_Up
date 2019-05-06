package com.now.startupteamnow;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.location.Location;
import android.os.Bundle;
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

public class CameraResult extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private float lat;
    private float lon;

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
                final Call<List<QRcode>> barcodeList = jsonApi.getBarcodeList();

                barcodeList.enqueue(new Callback<List<QRcode>>() {
                    @Override
                    public void onResponse(Call<List<QRcode>> call, Response<List<QRcode>> response) {
                        if(!response.isSuccessful()){ Toast.makeText(CameraResult.this, "Cekilisde Xeta", Toast.LENGTH_LONG).show(); return;}
                        List<QRcode> qRcodes = response.body();
                        assert qRcodes != null;
                        for (int i = 0; i < qRcodes.size(); i++){
                            QRcode anItemOfList = qRcodes.get(i);
                            if(anItemOfList.getCode().equals(result.getContents()) && anItemOfList.getxCoordination() > lat -0.1 && anItemOfList.getxCoordination() < lat+0.1 && anItemOfList.getyCoordination() > lon-0.1 && anItemOfList.getyCoordination() < lon+0.1){
                                Toast.makeText(CameraResult.this, "Bonus Kocuruldu", Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<QRcode>> call, Throwable t) {

                    }
                });
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
