package com.now.startupteamnow;

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

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private Button ireli;
    private boolean telready = false;
    private boolean mailready =false;
    private boolean passraedy =false;
    private String username = "Nowteam";
    private String password = "5591980Now";
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

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BuildConfig.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                JsonApi jsonApi = retrofit.create(JsonApi.class);


                UserInput userInput = new UserInput(tel.getText().toString(), pass.getText().toString());

                String base = username + ":" + password;
                String authhead = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
                final Call<CheckResponse> call = jsonApi.postCheckUser( authhead,userInput);

                call.enqueue(new Callback<CheckResponse>() {
                    @Override
                    public void onResponse(Call<CheckResponse> call, Response<CheckResponse> response) {
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
                        }


                    }

                    @Override
                    public void onFailure(Call<CheckResponse> call, Throwable t) {

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
