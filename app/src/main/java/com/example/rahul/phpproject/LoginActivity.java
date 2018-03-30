package com.example.rahul.phpproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button login, goToReg;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email=findViewById(R.id.userName);
        password=findViewById(R.id.password);
        login=findViewById(R.id.btnLogin);
        goToReg=findViewById(R.id.goToRegister);

        sharedPreferences=getSharedPreferences("users",MODE_PRIVATE);
        final SharedPreferences.Editor editor=sharedPreferences.edit();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editor.putString("email",email.getText().toString());
                editor.putString("password",password.getText().toString());
                editor.apply();

                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

        goToReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });

    }
}
