package com.example.rahul.phpproject.background;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.rahul.phpproject.Activities.Chat3Activity;
import com.example.rahul.phpproject.Interfaces.ChatInterface;

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
 * Created by rahul on 9/4/18.
 */

public class GetChats extends AsyncTask<String, Void, ArrayList<JSONArray>> {


    Context context;
    String response;
    JSONArray senders, recievers, msgs;
    ArrayList<JSONArray> jsonArrays=new ArrayList<>();
    ChatInterface chatInterface;

    public GetChats(Context context, ChatInterface chatInterface) {
        this.context = context;
        this.chatInterface=chatInterface;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<JSONArray> doInBackground(String... voids) {

        String addUserUrl="http://192.168.43.92/phpProject/getChats.php";

        String myEmail=voids[0];
        String friendEmail=voids[1];
        //String msg=voids[2];

        try {
            URL url=new URL(addUserUrl);
            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            String userData= URLEncoder.encode("myEmail","UTF-8")+"="+URLEncoder.encode(myEmail,"UTF-8")+"&"+
                    URLEncoder.encode("friendEmail","UTF-8")+"="+URLEncoder.encode(friendEmail,"UTF-8");
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

            Log.e("msgResponse",response);

            JSONObject jsonObject=new JSONObject(response);
            senders=jsonObject.getJSONArray("sender");
            recievers=jsonObject.getJSONArray("reciever");
            msgs=jsonObject.getJSONArray("msg");

            for (int i=0; i<senders.length(); i++)
            {
                Log.d("senderResponse"+i,senders.getString(i));
                //users.setImageResponse(responseImage.getString(i));
                //usersArrayList.add(users);


            }
            for (int i=0; i<recievers.length(); i++)
            {
                Log.d("reciverEmail"+i,recievers.getString(i));
                //users.setEmail(Email.getString(i));


            }
            for (int i=0; i<msgs.length(); i++)
            {
                Log.d("msg"+i,msgs.getString(i));
//                users.setName(name.getString(i));
//                usersArrayList.add(i,users);

            }


            jsonArrays.add(senders);
            jsonArrays.add(recievers);
            jsonArrays.add(msgs);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        return null;
        return jsonArrays;
    }

    @Override
    protected void onPostExecute(ArrayList<JSONArray> aVoid) {
        super.onPostExecute(aVoid);

        ArrayList<String> senders=new ArrayList<>();
        ArrayList<String> recievers=new ArrayList<>();
        ArrayList<String> msgs=new ArrayList<>();

        Intent intent=new Intent(context, Chat3Activity.class);

        for (int i=0; i<aVoid.get(0).length(); i++)
        {
            try {
                senders.add(i,aVoid.get(0).getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        for (int i=0; i<aVoid.get(1).length(); i++)
        {
            try {
                recievers.add(i,aVoid.get(1).getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        for (int i=0; i<aVoid.get(2).length(); i++)
        {
            try {
                msgs.add(i,aVoid.get(2).getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        chatInterface.onChatFinishes(senders,recievers,msgs);

    }
}
