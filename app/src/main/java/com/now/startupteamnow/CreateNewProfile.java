package com.now.startupteamnow;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateNewProfile extends AppCompatActivity {
    ImageView pc ;
    private Uri image;
    private Button btnPhoto, createbtn;
    private EditText name, surname, adgunu;
    private Spinner spinner;
    private String imageName, nameUser, surnameUser;
    private byte[] imagearray;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_profile);

        FINDall();

        //Drop List Made
        String[] items = new String[]{"Kişi", "Qadın", "Cins"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items){
            @Override
            public int getCount() {
                // to show hint "Select Gender" and dont able to select
                return 2;
            }
        };

        spinner.setAdapter(adapter);
        spinner.setSelection(2);

        //Date Picker Made
        adgunu.setShowSoftInputOnFocus(false);

        adgunu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(CreateNewProfile.this, R.style.Theme_AppCompat_Dialog_MinWidth,
                        dateSetListener,year,month,day);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        adgunu.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(CreateNewProfile.this, R.style.Theme_AppCompat_Dialog_MinWidth,
                            dateSetListener,year,month,day);
                    Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                String result = dayOfMonth + "/" + month + "/" + year;
                adgunu.setText(result);
            }
        };

        //Photo Upload
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });

        //Get Info From Prev Intent
        Intent getIntent = getIntent();
        final String telefon = getIntent.getStringExtra("telefon");
        final String email = getIntent.getStringExtra("email");
        final String password = getIntent.getStringExtra("password");

        // Create Button
        createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            try{
                if(adgunu.getText().length() > 0 && name.getText().length() > 1 && surname.getText().length() > 1 && image != null && !spinner.getSelectedItem().toString().equals("Cins")){

                    nameUser = name.getText().toString();
                    surnameUser = surname.getText().toString();

                    JsonApi jsonApi = Request_And_API_Key.GetRetrofit().create(JsonApi.class);

                    CreateUser user = new CreateUser();
                    String[] fixedAdgunu = adgunu.getText().toString().split("/");
                    String AdGunuIsFixed = fixedAdgunu[1] + "/" + fixedAdgunu[0] + "/" + fixedAdgunu[2];
                    user.setDate(AdGunuIsFixed);
                    user.setEmail(email);
                    user.setImgName(imageName);
                    user.setMale(spinner.getSelectedItem().toString().equals("Kişi"));
                    user.setName(nameUser);
                    user.setPassword(password);
                    user.setPhoneNumber(telefon);
                    user.setSurname(surnameUser);

                    // Create a request body with file and image media type
                    RequestBody fileReqBody = RequestBody.create(MediaType.parse(Objects.requireNonNull(getContentResolver().getType(image))), imagearray);

                    // Create MultipartBody.Part using file request-body,file name and part name
                    MultipartBody.Part part = MultipartBody.Part.createFormData("file", imageName, fileReqBody);

                    final Call<CheckResponse> call = jsonApi.postNewUser(Request_And_API_Key.Api_Key, part, user.getMap());
                    call.enqueue(new Callback<CheckResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<CheckResponse> call, @NonNull Response<CheckResponse> response) {
                            if(!response.isSuccessful()){
                                Toast.makeText(CreateNewProfile.this, "Serverdə Xəta Var", Toast.LENGTH_LONG).show();
                            }

                            if(response.body() != null){
                                CheckResponse data = response.body();
                                SharedPreferences.Editor sp = getSharedPreferences("Login", MODE_PRIVATE).edit();
                                sp.putInt("id", data.getId());
                                sp.putString("token", data.getToken());
                                sp.apply();

                                Intent intent = new Intent(CreateNewProfile.this, HomePage.class);
                                startActivity(intent);
                            }
                        }
                        @Override
                        public void onFailure(@NonNull Call<CheckResponse> call, @NonNull Throwable t) {
                            Toast.makeText(CreateNewProfile.this, "Serverdə Xəta Var", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else{
                    Toast.makeText(CreateNewProfile.this, "Lazımlı Yerləri Doldurun", Toast.LENGTH_LONG).show();
                }
            }catch (Exception e){
                Log.d("Qanli Error", e.toString());
            }

            }
        });

    }

    private void OpenGallery(){
        Intent intent =  new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 0){
            if(data != null){
                image = data.getData();
                if(image != null){
                    try {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, stream);

                        imagearray = stream.toByteArray();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    File file = new File(getPath(image));
                    imageName = file.getName();

                    Picasso.get().load(file).into(pc);
                }
            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(), uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        assert cursor != null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void FINDall(){
        pc = findViewById(R.id.uploadedpc);
        spinner = findViewById(R.id.spinner);
        adgunu = findViewById(R.id.adgunu);
        btnPhoto = findViewById(R.id.btnUpload);
        name = findViewById(R.id.EditName);
        surname = findViewById(R.id.EditSurname);
        createbtn = findViewById(R.id.createbtn);
    }
}

