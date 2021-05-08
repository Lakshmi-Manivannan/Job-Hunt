package com.example.jobhuntapp.ServiceProvider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.jobhuntapp.Databases.SessionManager;
import com.example.jobhuntapp.HelperClasses.UserHelperClass;
import com.example.jobhuntapp.R;
import com.example.jobhuntapp.User.SignUp;
import com.example.jobhuntapp.User.SignUp2;
import com.example.jobhuntapp.User.UserDashboard;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AddRequest extends AppCompatActivity {

    RadioButton radioButton;
    RadioGroup radioGroup;
    TextInputLayout location;
    Button request_service;
    String _uname;
    String place,category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_request);

        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        location = (TextInputLayout) findViewById(R.id.Location);
        request_service = (Button) findViewById(R.id.request_service_add);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String,String> userDetails =sessionManager.getUserDetailFromSession();
        _uname = userDetails.get(SessionManager.Key_USERNAME);
        final String image = userDetails.get(SessionManager.Key_IMAGE);

        request_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId=radioGroup.getCheckedRadioButtonId();
                radioButton=(RadioButton)findViewById(selectedId);
                category = radioButton.getText().toString();
                place = location.getEditText().getText().toString();


                Intent intent = new Intent(AddRequest.this, UserDashboard.class);

                String timeStamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("requestServices");

                RequestServiceHelperClass serviceHelperClass = new RequestServiceHelperClass(_uname,place,category,image,timeStamp);

                myRef.child(timeStamp).setValue(serviceHelperClass);

                startActivity(intent);

            }
        });


    }
}