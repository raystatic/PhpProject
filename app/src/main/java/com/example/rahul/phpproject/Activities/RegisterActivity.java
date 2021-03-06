package com.example.rahul.phpproject.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rahul.phpproject.R;
import com.example.rahul.phpproject.background.Register;
import com.example.rahul.phpproject.background.UploadImage;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    CircleImageView profileImage;
    EditText name, email, password;
    Button Reg, goToLogin;
    SharedPreferences sharedPreferences;

    Uri imageUri;
    Bitmap bitmap;
    String addUserUrl="http://192.168.0.6/phpProject/uploadImage.php";

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

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"SelectPicture"),PICK_IMAGE);



            }
        });


        Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Name=name.getText().toString();
                String Email=email.getText().toString();
                String Password=password.getText().toString();

                if (!TextUtils.isEmpty(Name) && !TextUtils.isEmpty(Email) && !TextUtils.isEmpty(Password))
                {

                    if (Email.endsWith(".com") || Email.endsWith(".in"))
                    {
                        if (Password.length()>=6)
                        {
                            editor.putString("name",Name);
                            editor.putString("email",Email);
                            editor.putString("password",Password);
                            editor.apply();

                            Register register=new Register(RegisterActivity.this);
                            register.execute(Name,Email,Password);
              //              Toast.makeText(getApplicationContext(),Password.length()+"",Toast.LENGTH_LONG).show();

                       }
                       else
                        {
                            Toast.makeText(getApplicationContext(),"Password must be of atleast 6 characters",Toast.LENGTH_LONG).show();
                        }
                    }

                    else
                    {
                        Toast.makeText(getApplicationContext(),"Please enter a valid email address(e.g; abc@xyz.com)",Toast.LENGTH_LONG).show();
                    }

                }
                else {
                    Toast.makeText(getApplicationContext(),"All fields are compulsory",Toast.LENGTH_LONG).show();
                }
            }
        });

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==PICK_IMAGE)
        {
            imageUri=data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                profileImage.setImageBitmap(bitmap);

                UploadImage uploadImage=new UploadImage(RegisterActivity.this);
                uploadImage.execute(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
