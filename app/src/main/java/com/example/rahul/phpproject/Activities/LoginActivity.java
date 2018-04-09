package com.example.rahul.phpproject.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rahul.phpproject.R;
import com.example.rahul.phpproject.background.Login;

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

                String Email=email.getText().toString();
                String Password=password.getText().toString();

                if (!TextUtils.isEmpty(Email) && !TextUtils.isEmpty(Password))
                {
                    if (Email.endsWith(".com") || Email.endsWith(".in"))
                    {
                        editor.putString("email",Email);
                        editor.putString("password",Password);
                        editor.apply();

                        Login login=new Login(LoginActivity.this);
                        login.execute(Email,Password);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Please enter a valid email address(e.g; abc@xyz.com)",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"All the fields are compulsory",Toast.LENGTH_LONG).show();
                }

            }
        });

        goToReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
