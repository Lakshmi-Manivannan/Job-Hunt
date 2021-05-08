package com.example.jobhuntapp.User;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobhuntapp.Databases.SessionManager;
import com.example.jobhuntapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.HashMap;

public class Profile extends AppCompatActivity {

    TextView _uname,_fullname,_email,_pswd,_pno;
    ImageView _image,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

         _uname = (TextView) findViewById(R.id.user_uname_profile);
         _fullname = (TextView) findViewById(R.id.user_name_profile);
         _email = (TextView) findViewById(R.id.user_email_profile);
         _pswd = (TextView) findViewById(R.id.user_pswd_profile);
         _pno = (TextView) findViewById(R.id.user_phone_profile);
         _image = (ImageView) findViewById(R.id.user_img_profile);
         back = (ImageView) findViewById(R.id.back_to);

         back.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(getApplicationContext(),UserDashboard.class));
             }
         });

        SessionManager sessionManager = new SessionManager(this);

        if(sessionManager.checkLogin())
        {
            HashMap<String,String> userDetails =sessionManager.getUserDetailFromSession();

             _uname.setText("Username : "+userDetails.get(SessionManager.Key_USERNAME));
             _fullname.setText(userDetails.get(SessionManager.Key_FULLNAME));
             _email.setText("Email : "+userDetails.get(SessionManager.Key_EMAIL));
             _pswd.setText("Password : "+userDetails.get(SessionManager.Key_PASSWORD));
             _pno.setText("Phone Number : "+userDetails.get(SessionManager.Key_PNO));

            Uri uri = Uri.parse(userDetails.get(SessionManager.Key_IMAGE));

            Picasso.with(getApplicationContext()).load(uri).into(_image);

        }
        else {
            Toast.makeText(getApplicationContext(),"Login Please",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

    }

}