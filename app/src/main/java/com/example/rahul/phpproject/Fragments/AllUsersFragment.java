package com.example.rahul.phpproject.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rahul.phpproject.Adapters.AllUsersRecyclerViewAdapter;
import com.example.rahul.phpproject.Interfaces.UsersDataInterface;
import com.example.rahul.phpproject.R;
import com.example.rahul.phpproject.background.UsersData;
import com.example.rahul.phpproject.model.Users;

import org.json.JSONArray;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllUsersFragment extends Fragment implements UsersDataInterface{

    RecyclerView recyclerViewUsers;
    AllUsersRecyclerViewAdapter adapter;
    SharedPreferences sharedPreferences;

    public AllUsersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_all_users,container,false);
        recyclerViewUsers=view.findViewById(R.id.recyclerViewUsers);

        sharedPreferences=getActivity().getSharedPreferences("users", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        UsersData usersData=new UsersData(getActivity().getApplicationContext(),this);

        String Email=sharedPreferences.getString("email","rahul");
        String Password=sharedPreferences.getString("password","123456789");

        usersData.execute(Email,Password);

        return view;
    }

    @Override
    public void onUsersbackgroundFinishes(JSONArray responseImage, JSONArray email, JSONArray name) {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerViewUsers.setLayoutManager(linearLayoutManager);
        adapter=new AllUsersRecyclerViewAdapter(getActivity().getApplicationContext(),responseImage,email,name);
        recyclerViewUsers.setAdapter(adapter);
    }

}
