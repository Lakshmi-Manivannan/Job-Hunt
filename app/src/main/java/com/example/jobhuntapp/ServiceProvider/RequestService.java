package com.example.jobhuntapp.ServiceProvider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jobhuntapp.Databases.SessionManager;
import com.example.jobhuntapp.HelperClasses.ServiceProvidersView;
import com.example.jobhuntapp.R;
import com.example.jobhuntapp.User.MainActivity;
import com.example.jobhuntapp.User.UserDashboard;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RequestService extends AppCompatActivity {

    ImageView img;
    TextView name,Desc,location,category;
    Button request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_service);

        name = (TextView) findViewById(R.id.name_profile);
        img = (ImageView) findViewById(R.id.img_profile);
        Desc = (TextView) findViewById(R.id.desc_profile);
        location = (TextView) findViewById(R.id.location_profile);
        category = (TextView) findViewById(R.id.category_profile);
        request = (Button) findViewById(R.id.request_service);

        Uri uri = Uri.parse(getIntent().getStringExtra("image"));

        name.setText(getIntent().getStringExtra("name"));
        Desc.setText("Description :\n "+getIntent().getStringExtra("description"));
        category.setText("Category :"+getIntent().getStringExtra("category"));

        if(getIntent().hasExtra("number"))
            location.setText("Location :"+getIntent().getStringExtra("location")+"\nPhone Number : "+getIntent().getStringExtra("number")+"\nEmail : "+getIntent().getStringExtra("email"));
        else
            location.setText("Location :"+getIntent().getStringExtra("location"));
        Picasso.with(getApplicationContext()).load(uri).into(img);
        final SessionManager sessionManager = new SessionManager(this);
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sessionManager.checkLogin())
                    startActivity(new Intent(RequestService.this, AddRequest.class));
                else
                {
                    Toast .makeText(RequestService.this,"Please Login",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            }
        });
    }
}