package com.example.rahul.phpproject.background;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;

/**
 * Created by rahul on 30/3/18.
 */

public class UploadImage extends AsyncTask<Bitmap, Void, String> {

    Context context;
    ProgressDialog progressDialog;

    public UploadImage(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
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
    protected String doInBackground(Bitmap... voids) {

        String addUserUrl="http://192.168.43.92/phpProject/uploadImage.php";

        Bitmap bitmap=voids[0];

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
            String userData= URLEncoder.encode("image","UTF-8")+"="+URLEncoder.encode(imageToString(bitmap),"UTF-8");
            Log.d("userData",userData);
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
    protected void onPostExecute(String aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(context,aVoid,Toast.LENGTH_LONG).show();
    }

    public String imageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imageBytes=byteArrayOutputStream.toByteArray();
        return android.util.Base64.encodeToString(imageBytes, android.util.Base64.DEFAULT);
    }

}
