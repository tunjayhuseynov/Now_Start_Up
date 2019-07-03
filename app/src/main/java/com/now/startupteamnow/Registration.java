package com.now.startupteamnow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Registration extends AppCompatActivity {

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z.]+";
    private Button ireli;
    private boolean telready = false;
    private boolean mailready =false;
    private boolean passraedy =false;
    private EditText tel;
    private EditText mail;
    private EditText pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        final ProgressBar bar = findViewById(R.id.barReg);
        bar.setVisibility(View.INVISIBLE);
        ireli =  findViewById(R.id.next);
        tel = findViewById(R.id.tel);
        mail = findViewById(R.id.mail);
        pass = findViewById(R.id.sifre);

        ireli.setEnabled(false);

        tel.addTextChangedListener(textWatcher);
        mail.addTextChangedListener(textWatcher);
        pass.addTextChangedListener(textWatcher);


        ireli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ireli.setVisibility(View.INVISIBLE);
                bar.setVisibility(View.VISIBLE);

                JsonApi jsonApi = Request_And_API_Key.GetRetrofit().create(JsonApi.class);

                final Call<CheckResponse> call = jsonApi.CheckUser( Request_And_API_Key.Api_Key, tel.getText().toString(), pass.getText().toString());

                call.enqueue(new Callback<CheckResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<CheckResponse> call, @NonNull Response<CheckResponse> response) {
                        if(!response.isSuccessful()){
                            Toast.makeText(Registration.this, "Serverdə Xəta Var", Toast.LENGTH_LONG).show();
                        }else{
                            CheckResponse checkResponse = response.body();
                            assert checkResponse != null;

                            if(!checkResponse.isFound()){
                                Intent next = new Intent(Registration.this, CreateNewProfile.class);
                                next.putExtra("telefon", tel.getText().toString());
                                next.putExtra("email", mail.getText().toString());
                                next.putExtra("password", pass.getText().toString());
                                startActivity(next);
                            }else{
                                Toast.makeText(Registration.this, "Belə Bir Nömrə Artıq Mövcutdur", Toast
                                .LENGTH_LONG).show();
                            }
                            ireli.setVisibility(View.VISIBLE);
                            bar.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CheckResponse> call, @NonNull Throwable t) {
                        Toast.makeText(Registration.this, "Internetdə Xəta Var", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });


    }

    public TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(tel.getText().hashCode() == s.hashCode()){
                telready = s.length() == 10 || s.length() == 13;

            }
            if(mail.getText().hashCode() == s.hashCode()){
                mailready = s.toString().trim().matches(emailPattern);
                Toast.makeText(Registration.this, String.valueOf(mailready), Toast.LENGTH_SHORT).show();

            }
            if(pass.getText().hashCode() == s.hashCode()) {
                passraedy = s.length()>5;
                Toast.makeText(Registration.this, String.valueOf(passraedy), Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(mailready && telready && passraedy){ireli.setEnabled(true);}else{ireli.setEnabled(false);}
        }
    };

}
