package com.example.rahul.phpproject.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rahul.phpproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by rahul on 8/4/18.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {


    Context context;
    ArrayList<String> sender, reciever, msg;
    String friendEmail, myEmail;

    public ChatAdapter(Context context, ArrayList<String> sender, ArrayList<String> reciever, ArrayList<String> msg, String friendEmail, String myEmail) {
        this.context = context;
        this.sender = sender;
        this.reciever = reciever;
        this.msg = msg;
        this.friendEmail=friendEmail;
        this.myEmail=myEmail;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.chat_adapter_layout,parent,false);

        Log.e("senderLength",sender.size()+"");
        Log.e("recLength",reciever.size()+"");
        Log.e("msgLength",msg.size()+"");

        for (int i=0; i<sender.size(); i++)
        {
            Log.e("senders"+i,sender.get(i));
        }
        for (int i=0; i<reciever.size(); i++)
        {
            Log.e("recievers"+i,reciever.get(i));
        }
        for (int i=0; i<msg.size(); i++)
        {
            Log.e("msg"+i,msg.get(i));
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        try {
            String senders=sender.get(position);
            String recievers=reciever.get(position);
            String msgs=msg.get(position);

//            if (recievers.equals(friendEmail))
//            {
//                holder.sender.setText(recievers);
//            }
//            else if (senders.equals(myEmail))
//            {
//                holder.sender.setText("You");
//            }
            holder.sender.setText(senders);

            holder.msg.setText(msgs);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return sender.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView msg,sender;

        public MyViewHolder(View itemView) {
            super(itemView);

            msg=itemView.findViewById(R.id.tvMsgChat);
            sender=itemView.findViewById(R.id.tvFriendChat);

        }
    }

}
