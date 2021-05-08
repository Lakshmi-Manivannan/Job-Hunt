package com.example.jobhuntapp.Databases;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;

    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String Key_FULLNAME = "fullname";
    public static final String Key_USERNAME = "username";
    public static final String Key_PASSWORD = "password";
    public static final String Key_EMAIL = "email";
    public static final String Key_IMAGE = "image";
    public static final String Key_PNO = "pno";


    public SessionManager(Context _context){
        context = _context;
        userSession= context.getSharedPreferences("userLoginSession",Context.MODE_PRIVATE);
        editor = userSession.edit();
    }

    public  void createLoginSession(String fullname,String username,String password,String pno,String image,String email){

        editor.putBoolean(IS_LOGIN,true);

        editor.putString(Key_FULLNAME,fullname);
        editor.putString(Key_USERNAME,username);
        editor.putString(Key_EMAIL,email);
        editor.putString(Key_IMAGE,image);
        editor.putString(Key_PNO,pno);
        editor.putString(Key_PASSWORD,password);

        editor.commit();
    }

    public HashMap<String , String> getUserDetailFromSession(){

        HashMap<String,String> userdata = new HashMap<String,String>();

        userdata.put(Key_FULLNAME,userSession.getString(Key_FULLNAME,null));
        userdata.put(Key_USERNAME,userSession.getString(Key_USERNAME,null));
        userdata.put(Key_EMAIL,userSession.getString(Key_EMAIL,null));
        userdata.put(Key_IMAGE,userSession.getString(Key_IMAGE,null));
        userdata.put(Key_PNO,userSession.getString(Key_PNO,null));
        userdata.put(Key_PASSWORD,userSession.getString(Key_PASSWORD,null));

        return userdata;
    }

    public boolean checkLogin(){
        if((userSession.getBoolean(IS_LOGIN,false))){
            return true;
        }
        else
            return false;
    }

    public void logout(){
        editor.clear();
        editor.commit();
    }
}
