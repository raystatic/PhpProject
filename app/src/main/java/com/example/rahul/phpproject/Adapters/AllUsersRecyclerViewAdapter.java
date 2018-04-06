package com.example.rahul.phpproject.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rahul.phpproject.R;
import com.example.rahul.phpproject.model.Users;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by rahul on 1/4/18.
 */

public class AllUsersRecyclerViewAdapter extends RecyclerView.Adapter<AllUsersRecyclerViewAdapter.MyViewHolder> {

    Context context;
    JSONArray responseImage;
    JSONArray email;
    JSONArray name;


    public AllUsersRecyclerViewAdapter(Context context, JSONArray responseImage, JSONArray email, JSONArray name) {
        this.context = context;
        this.responseImage = responseImage;
        this.email = email;
        this.name = name;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.all_users_adapter,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //Users users=usersArrayList.get(position);
        String imageResponse= null;
        try {
            imageResponse = responseImage.getString(position);
            Log.e("imageAdap"+position,imageResponse);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Bitmap userimage=StringToBitMap(imageResponse);
        holder.userImage.setImageBitmap(userimage);
        try {
            holder.name.setText(name.getString(position));
            Log.e("nameAdap"+position,name.getString(position));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            holder.email.setText(email.getString(position));
            Log.e("emailAdap"+position,email.getString(position));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return name.length();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView userImage;
        TextView name,email;
        View parent;

        public MyViewHolder(View itemView) {
            super(itemView);
            userImage=itemView.findViewById(R.id.imageUsers);
            name=itemView.findViewById(R.id.tvNameUsers);
            email=itemView.findViewById(R.id.tvEmailUsers);
            parent=this.itemView;
        }
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

}
