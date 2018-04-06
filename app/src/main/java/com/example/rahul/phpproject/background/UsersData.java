package com.example.rahul.phpproject.background;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.rahul.phpproject.Interfaces.UsersDataInterface;
import com.example.rahul.phpproject.model.Users;

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

/**
 * Created by rahul on 1/4/18.
 */

public class UsersData extends AsyncTask<String, Void, ArrayList<JSONArray>>{

    Context context;
    UsersDataInterface usersDataInterface;
    JSONArray responseImage;
    JSONArray Email;
    JSONArray name;
    JSONArray password;
    Users users=new Users();
    ArrayList<Users> usersArrayList=new ArrayList<>();

    ArrayList<JSONArray> jsonArrays=new ArrayList<>();

    public UsersData(Context context, UsersDataInterface usersDataInterface) {
        this.context = context;
        this.usersDataInterface = usersDataInterface;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected ArrayList<JSONArray> doInBackground(String... voids) {

        String addUserUrl="http://192.168.43.92/phpProject/usersData.php";

        String email=voids[0];
        String password=voids[1];

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
            String userData= URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"+
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

            Log.e("responseArray",response);

//            ArrayList<String> User_List = new ArrayList<>();
//
//
//            JSONObject jsonObject=new JSONObject(response);
//
////            responseImage=new JSONArray(jsonObject);
////            name=new JSONArray(jsonObject);
////            Email=new JSONArray(jsonObject);
////            responseImage=jsonObject.getString("encodedImage");
////            name=jsonObject.getString("name");
///////**/            name="ray";
////            Email=jsonObject.getString("email");
////            //password=jsonObject.getString("password");
////
////
//////          //  Log.e("pass",Password);
////
////            Log.e("name_length", name);
////
////
////            users.setImageResponse(responseImage);
////            users.setName(name);
////            users.setEmail(Email);
////
////            usersArrayList.add(users);
//
//
//            JSONArray jsonArray=new JSONArray(response);
//
//            Log.e("response_length",jsonArray.length()+"");
//
//            Log.e("hello","world");
//
//            for (int i=0; i<jsonArray.length(); i++)
//            {
//                jsonObject=jsonArray.getJSONObject(i);
//                User_List.add(jsonObject.getString("names"));
//            }
//
//
//            for (int i=0; i<jsonArray.length(); i++) {
////                Log.e("image2Users",responseImage);
//                Log.e("nameUsers", User_List.get(i));
//                //              Log.e("emailUsers",Email);            }
//            }
            JSONObject jsonObject=new JSONObject(response);
            responseImage=jsonObject.getJSONArray("encodedImage");
            Email=jsonObject.getJSONArray("emails");
            name=jsonObject.getJSONArray("names");

//            Users[] users=new Users[responseImage.length()];

            for (int i=0; i<responseImage.length(); i++)
            {
                Log.d("jsonResponse"+i,responseImage.getString(i));
                //users.setImageResponse(responseImage.getString(i));
                //usersArrayList.add(users);


            }
            for (int i=0; i<Email.length(); i++)
            {
                Log.d("jsonEmail"+i,Email.getString(i));
                users.setEmail(Email.getString(i));


            }
            for (int i=0; i<name.length(); i++)
            {
                Log.d("jsonName"+i,name.getString(i));
                users.setName(name.getString(i));
                usersArrayList.add(i,users);

            }

            //usersArrayList.add(users);

//            for (int i=0; i<name.length(); i++)
//            {
//                Log.d("usersName"+i,usersArrayList.get(i).getName());
//                //users.setName(name.getString(i));
//
//            }
//            Log.d("usersName"+0,usersArrayList.get(1).getName());

            jsonArrays.add(0,responseImage);
            jsonArrays.add(1,Email);
            jsonArrays.add(2,name);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //return usersArrayList;
        return jsonArrays;
        //return null;
    }

    @Override
    protected void onPostExecute(ArrayList<JSONArray> aVoid) {
        super.onPostExecute(aVoid);
        usersDataInterface.onUsersbackgroundFinishes(aVoid.get(0),aVoid.get(1),aVoid.get(2));
    }
}
