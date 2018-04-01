package com.example.rahul.phpproject.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahul.phpproject.Activities.MainActivity;
import com.example.rahul.phpproject.Interfaces.ProfileInterface;
import com.example.rahul.phpproject.R;
import com.example.rahul.phpproject.background.ProfileData;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements ProfileInterface {

    Button button;
    CircleImageView profile;
    TextView tvname, tvemail, tvpassword;
    SharedPreferences sharedPreferences;
    Bitmap profileImage;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile, container, false);

        button=view.findViewById(R.id.btnLogout);
        profile=view.findViewById(R.id.imageProfile);
        tvname=view.findViewById(R.id.nameProfile);
        tvemail=view.findViewById(R.id.emailProfile);
        tvpassword=view.findViewById(R.id.passwordProfile);

//        Bundle bundle=this.getArguments();
//        if (bundle!=null)
//        {
//            String responseImage=bundle.getString("avoid");
//            Log.d("avoid",responseImage);
//        }
//        else
//        {
//            Toast.makeText(getActivity(),"bundle=null",Toast.LENGTH_LONG).show();
//        }


        sharedPreferences=getActivity().getSharedPreferences("users", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor=sharedPreferences.edit();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.clear();
                editor.apply();

                startActivity(new Intent(getActivity().getApplicationContext(),MainActivity.class));
                getActivity().finish();

            }
        });

        ProfileData profileData=new ProfileData(getActivity().getApplicationContext(),this);
        String Email=sharedPreferences.getString("email","rahul");
        String Password=sharedPreferences.getString("password","123456789");

        profileData.execute(Email,Password);



        return view;
    }

//    public void setProfile(Bitmap bitmap)
//    {
//        profile.setImageBitmap(bitmap);
//    }
    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    @Override
    public void onProfileBackgroungFinishes(String imageResponse, String name, String email, String password) {
        profileImage=StringToBitMap(imageResponse);
        profile.setImageBitmap(profileImage);
       tvname.setText(name);
       tvemail.setText(email);
       tvpassword.setText(password);
    }
}
