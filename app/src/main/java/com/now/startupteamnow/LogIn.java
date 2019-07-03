package com.now.startupteamnow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogIn extends AppCompatActivity {

    String patter = "(\\+994|0)(77|70|50|51|55)[0-9]{7}";
    private EditText number, pass;
    private Button registration, login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        number = findViewById(R.id.number);
        pass = findViewById(R.id.pass);

        registration = findViewById(R.id.qeydiyyat);
        login = findViewById(R.id.daxilol);

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent goToReg = new Intent(LogIn.this, Registration.class);
                startActivity(goToReg);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(number.getText().toString().trim().matches(patter) && pass.length() > 5){
                    Intent check = new Intent(LogIn.this, Check.class);
                    check.putExtra("number",number.getText().toString());
                    check.putExtra("pass", pass.getText().toString());
                    startActivity(check);
                }else if(!number.getText().toString().trim().matches(patter)){
                    Toast.makeText(LogIn.this, "Zəhmət olmasa nömrəni düzgün daxil edin", Toast.LENGTH_LONG).show();
                }
                else if(pass.length() > 5){
                    Toast.makeText(LogIn.this, "Şifrə uzunluğu minimum 6 olmalıdır", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(LogIn.this, "Məlumatlar düzgün daxil edilməyib", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
