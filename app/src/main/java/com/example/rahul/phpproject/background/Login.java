package com.example.rahul.phpproject.background;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.rahul.phpproject.Activities.LoginActivity;
import com.example.rahul.phpproject.Activities.MainActivity;

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
 * Created by rahul on 31/3/18.
 */

public class Login extends AsyncTask<String, Void, String> {


    Context context;


    public Login(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... voids) {

        String addUserUrl="http://192.168.0.6/phpProject/login.php";

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
            String userData=URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(Password,"UTF-8")+"&"+
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

        if (aVoid.equals("login success"))
        {
            context.startActivity(new Intent(context,MainActivity.class));
            new LoginActivity().finish();
        }
        else if(aVoid.equals("Invalid email or password"))
        {

            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            builder.setMessage(aVoid)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
        }
    }
}
