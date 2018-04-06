package com.example.rahul.phpproject.Interfaces;

import com.example.rahul.phpproject.model.Users;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by rahul on 1/4/18.
 */

public interface UsersDataInterface {

    public void onUsersbackgroundFinishes(JSONArray responseImage, JSONArray email, JSONArray name);

}
