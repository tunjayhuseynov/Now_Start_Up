package com.now.startupteamnow;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Objects;

public class CreateNewProfile extends AppCompatActivity {

    ImageView pc ;
    private Uri image = null;
    private String nameUser;
    private String surnameUser;

    private DatePickerDialog.OnDateSetListener dateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_profile);

        pc = findViewById(R.id.uploadedpc);

        //Drop List Made
        final Spinner spinner = findViewById(R.id.spinner);

        String[] items = new String[]{"Kisi", "Qadin", "Cins"};

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

        final EditText adgunu = findViewById(R.id.adgunu);


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

                String result = String.valueOf(dayOfMonth) + "/" + String.valueOf(month) + "/" + String.valueOf(year);
                adgunu.setText(result);
            }
        };


        //Photo Upload

        Button btnPhoto = findViewById(R.id.btnUpload);
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });

        // Name

        final EditText name = findViewById(R.id.editname);
        nameUser = name.getText().toString();

        // Surname

        final EditText surname = findViewById(R.id.editsurname);
        surnameUser = surname.getText().toString();


        // Create Button
        Button createbtn = findViewById(R.id.createbtn);
        createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(adgunu.getText().length() > 0 && name.getText().length() > 1 && surname.getText().length() > 1 && image != null && !spinner.getSelectedItem().toString().equals("Cins")){

                    Toast.makeText(CreateNewProfile.this, "Tebrikler!", Toast.LENGTH_LONG).show();
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
            assert data != null;
            image = data.getData();
            Picasso.get().load(image).into(pc);

        }
    }
}
