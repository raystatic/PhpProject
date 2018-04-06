package com.example.rahul.phpproject.background;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rahul.phpproject.Fragments.ProfileFragment;
import com.example.rahul.phpproject.Interfaces.ProfileInterface;
import com.example.rahul.phpproject.R;
import com.example.rahul.phpproject.model.Profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by rahul on 31/3/18.
 */

public class ProfileData extends AsyncTask<String,Void, Profile> {

    Context context;
    public String response;
    //CircleImageView profile;
    String responseImage;
    String name;
    String Email;
    String password;

    ProfileInterface profileInterface;
    Profile profile=new Profile();


    public ProfileData(Context context, ProfileInterface profileInterface) {
        this.context = context;
        this.profileInterface = profileInterface;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    public Profile doInBackground(String... voids) {

        String addUserUrl="http://192.168.43.92/phpProject/profileData.php";

        String email=voids[0];
        String Password=voids[1];

//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        try {
            URL url=new URL(addUserUrl);
            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            String userData= URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(Password,"UTF-8")+"&"+
                    URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8");
            bufferedWriter.write(userData);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();


            InputStream inputStream=httpURLConnection.getInputStream();

            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
            response="";
            String line="";
            while((line=bufferedReader.readLine())!=null)
            {
                response+=line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            JSONObject jsonObject=new JSONObject(response);

            responseImage=jsonObject.getString("encodedImage");
            name=jsonObject.getString("name");
///**/            name="ray";
            Email=jsonObject.getString("email");
            password=jsonObject.getString("password");

            Log.e("image2",responseImage);
            Log.e("name",name);
            Log.e("email",Email);
            Log.e("pass",Password);

            profile.setImageResource(responseImage);
            profile.setEmail(Email);
            profile.setName(name);
            profile.setPassword(password);





        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return profile;

        //return null;
    }

    @Override
    protected void onPostExecute(Profile aVoid) {
        super.onPostExecute(aVoid);
//        Log.d("responseImage",aVoid);

            if (aVoid==null)
            {
                Log.d("avoid2","null");
            }

        profileInterface.onProfileBackgroungFinishes(aVoid.getImageResource(),aVoid.getName(),aVoid.getEmail(),aVoid.getPassword());

    }

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

    public String getResponseImage()
    {
        return responseImage;
    }

}
