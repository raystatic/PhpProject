package com.example.rahul.phpproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rahul.phpproject.background.Register;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    CircleImageView profileImage;
    EditText name, email, password;
    Button Reg, goToLogin;
    SharedPreferences sharedPreferences;

    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        profileImage=findViewById(R.id.profileImage);
        name=findViewById(R.id.nameReg);
        email=findViewById(R.id.userNameReg);
        password=findViewById(R.id.passwordReg);
        Reg=findViewById(R.id.btnLoginReg);
        goToLogin=findViewById(R.id.goToLogin);

        sharedPreferences=getSharedPreferences("users",MODE_PRIVATE);
        final SharedPreferences.Editor editor=sharedPreferences.edit();

        imageUri=null;


        Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Name=name.getText().toString();
                String Email=email.getText().toString();
                String Password=email.getText().toString();

                editor.putString("name",Name);
                editor.putString("email",Email);
                editor.putString("password",Password);
                editor.apply();

                Register register=new Register(RegisterActivity.this);
                register.execute(Name,Email,Password);

//                startActivity(new Intent(getApplicationContext(),MainActivity.class));
//                //finish();
            }
        });

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

}
