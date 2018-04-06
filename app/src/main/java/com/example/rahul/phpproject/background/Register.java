package com.example.rahul.phpproject.background;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.rahul.phpproject.Activities.MainActivity;
import com.example.rahul.phpproject.Activities.RegisterActivity;

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

/**
 * Created by rahul on 30/3/18.
 */

public class Register extends AsyncTask<String, Void, String>{

    Context context;
    ProgressDialog progressDialog;
    String regiterUrl;

    public Register(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
//        progressDialog=new ProgressDialog(context);
//        progressDialog.setProgressStyle(R.style.Theme_AppCompat_DayNight_DarkActionBar);
//        progressDialog.setCancelable(false);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("Signing Up..");
//        progressDialog.show();
////        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                progressDialog.dismiss();
//            }
//        },3000);
    }

    @Override
    protected String doInBackground(String... voids) {
        String addUserUrl="http://192.168.43.92/phpProject/register.php";

        String name=voids[0];
        String email=voids[1];
        String Password=voids[2];

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
            String userData= URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                    URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(Password,"UTF-8")+"&"+
                    URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8");
            bufferedWriter.write(userData);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream=httpURLConnection.getInputStream();

            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
            String response="";
            String line="";
            while((line=bufferedReader.readLine())!=null)
            {
                response+=line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            return response;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(final String aVoid) {

//        if (progressDialog!=null && progressDialog.isShowing())
//        {
//            progressDialog.dismiss();
//        }

        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setMessage(aVoid)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (aVoid.equals("User already exist with this email address") || aVoid.equals("Error in registration"))
                            dialogInterface.cancel();
                        else {
                            dialogInterface.cancel();
                            context.startActivity(new Intent(context, MainActivity.class));
                            new RegisterActivity().finish();
                        }
                    }
                });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
}
