package com.now.startupteamnow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LogIn extends AppCompatActivity {

    private EditText number;
    private EditText pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        number = findViewById(R.id.number);
        pass = findViewById(R.id.pass);

        Button registration = findViewById(R.id.qeydiyyat);
        Button login = findViewById(R.id.daxilol);

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
                Intent check = new Intent(LogIn.this, Check.class);
                check.putExtra("number",number.getText());
                check.putExtra("pass", pass.getText());
                startActivity(check);
            }
        });
    }
}
