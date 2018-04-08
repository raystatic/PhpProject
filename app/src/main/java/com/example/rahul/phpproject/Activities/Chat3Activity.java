package com.example.rahul.phpproject.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahul.phpproject.Adapters.ChatAdapter;
import com.example.rahul.phpproject.Interfaces.ChatInterface;
import com.example.rahul.phpproject.R;
import com.example.rahul.phpproject.background.Chat;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat3Activity extends AppCompatActivity{

    TextView friendName, info;
    String friend, friendEmail;
    SharedPreferences sharedPreferences;
    String myEmail, msg;
    CircleImageView send;
    EditText etMsg;
    RecyclerView recyclerView;
    ArrayList<String> senders, recievers, msgs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat3);

        Toolbar toolbar=findViewById(R.id.toolbar);
        friendName=findViewById(R.id.tvChatName);
        send=findViewById(R.id.send);
        etMsg=findViewById(R.id.etTypeChat);
        recyclerView=findViewById(R.id.recyclerViewChats);
        info=findViewById(R.id.infotv);

       // FrameLayout frameLayout=findViewById(R.id.containerChat);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent=getIntent();

        friend=intent.getStringExtra("friend");
        friendEmail=intent.getStringExtra("email");

        friendName.setText(friend);

        senders=new ArrayList<>();
        recievers=new ArrayList<>();
        msgs=new ArrayList<>();

        sharedPreferences=getSharedPreferences("users",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        myEmail=sharedPreferences.getString("email","rahul");

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                msg=etMsg.getText().toString();
                if (msg.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter some text first",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    info.setVisibility(View.GONE);

                    try {
                       // ChatInterface chatInterface=null;
                       Chat chat = new Chat(Chat3Activity.this, new ChatInterface() {
                           @Override
                           public void onChatFinishes(ArrayList<String> senders, ArrayList<String> reciever, ArrayList<String> msgs) {
                               LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
                               ChatAdapter adapter=new ChatAdapter(getApplicationContext(),senders,reciever,msgs, friendEmail, myEmail);
                               recyclerView.setLayoutManager(layoutManager);
                               recyclerView.setAdapter(adapter);
                           }
                       });
                        chat.execute(myEmail,friendEmail,msg);
                        etMsg.setText("");
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onChatFinishes(JSONArray senders, JSONArray recievers, JSONArray msgs) {
//
//
//
//        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
//        ChatAdapter adapter=new ChatAdapter(getApplicationContext(),senders,recievers,msgs, friendEmail, myEmail);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter);
//    }
////
//    public void chatMap(JSONArray senders, JSONArray recievers, JSONArray msgs)
//    {
//
//    }


}
