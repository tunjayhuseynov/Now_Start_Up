package com.now.startupteamnow;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            Toast.makeText(CameraResult.this, "Kamerada xəta baş verdi. Yenidən Yoxlayın", Toast.LENGTH_LONG).show();
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
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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


                JsonApi jsonApi = Request_And_API_Key.GetRetrofit().create(JsonApi.class);
                final Call<QRcode> barcodeList = jsonApi.getBarcodeList(Request_And_API_Key.Api_Key, result.getContents());


                barcodeList.enqueue(new Callback<QRcode>() {
                    @Override
                    public void onResponse(@NonNull Call<QRcode> call, @NonNull Response<QRcode> response) {
                        if(!response.isSuccessful()){
                            Toast.makeText(CameraResult.this, "Çəkilişdə Xəta", Toast.LENGTH_LONG).show();
                            return;
                        }
                        QRcode qRcodes = response.body();
                        assert qRcodes != null;

                        if(qRcodes.getCode().equals(result.getContents()) && qRcodes.getxCoordination() > lat -0.005 && qRcodes.getxCoordination() < lat+0.005 && qRcodes.getyCoordination() > lon-0.005 && qRcodes.getyCoordination() < lon+0.005){
                            PostBonus(qRcodes.getBonus());
                        }
                        else
                            {
                            Toast.makeText(CameraResult.this, "QR Code Mövcud Deyil", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<QRcode> call, @NonNull Throwable t) {
                        Toast.makeText(CameraResult.this, "Cəkilişdə Xəta", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void PostBonus(int amount){
        JsonApi jsonApi = Request_And_API_Key.GetRetrofit().create(JsonApi.class);
        final Call<Integer> request = jsonApi.postBonus(Request_And_API_Key.Api_Key,Request_And_API_Key.GetId(this),Request_And_API_Key.GetToken(this), amount);

        request.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                if(!response.isSuccessful()) {
                    return;
                }

                if(response.body() != null){
                    int res = response.body();
                    if(res != -1){
                        Toast.makeText(CameraResult.this, "Bonus Köçürüldü", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                Toast.makeText(CameraResult.this, "Cəkilişdə Xəta", Toast.LENGTH_LONG).show();
            }
        });
    }
}
