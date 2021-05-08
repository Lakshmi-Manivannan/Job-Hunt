package com.example.jobhuntapp.ServiceProvider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.jobhuntapp.Databases.SessionManager;
import com.example.jobhuntapp.HelperClasses.ServiceProviderViewAdapter;
import com.example.jobhuntapp.HelperClasses.ServiceProvidersView;
import com.example.jobhuntapp.R;
import com.example.jobhuntapp.User.MainActivity;
import com.example.jobhuntapp.User.Profile;
import com.example.jobhuntapp.User.RequestedServices;
import com.example.jobhuntapp.User.UserDashboard;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllServiceProvider extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    static final float END_SCALE = 0.7f;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    RecyclerView  all_service_provider;
    ServiceProviderViewAdapter adapter;

    ImageView menu_icon,img_profile,add_request;

    LinearLayout contentView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_service_provider);

        all_service_provider = (RecyclerView) findViewById(R.id.all_service_providers);

        if(getIntent().hasExtra("category")){
            categorial_service_provider(getIntent().getStringExtra("category").toString());
        }
        else
            all_service_provider();


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        menu_icon = (ImageView) findViewById(R.id.menu_icon1);
        img_profile = (ImageView) findViewById(R.id.img_profile);
        add_request = (ImageView) findViewById(R.id.add_request1);
        contentView = (LinearLayout) findViewById(R.id.content_view);
        final SessionManager sessionManager = new SessionManager(this);
        add_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sessionManager.checkLogin())
                    startActivity(new Intent(AllServiceProvider.this, AddRequest.class));
                else
                {
                    Toast .makeText(AllServiceProvider.this,"Please Login",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
            }
        });

        navigationDrawer();
    }

    //Navigation
    private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else
                    drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        animateNavigationDrawer();
    }

    @SuppressWarnings("deprecation")
    private void animateNavigationDrawer() {

        drawerLayout.setScrimColor(getResources().getColor(R.color.banner_background));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerVisible(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        SessionManager sessionManager = new SessionManager(this);
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.nav_home:
                startActivity(new Intent(AllServiceProvider.this,UserDashboard.class));
                break;
            case R.id.nav_search:
                Toast.makeText(AllServiceProvider.this,"Search Icon Selected",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_request_service:
                if(sessionManager.checkLogin())
                    startActivity(new Intent(AllServiceProvider.this, AddRequest.class));
                else
                {
                    Toast .makeText(AllServiceProvider.this,"Please Login",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
                break;
            case R.id.nav_share:
                Toast .makeText(AllServiceProvider.this,"Share Icon Selected",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_login:
                if(!sessionManager.checkLogin())
                    startActivity(new Intent(AllServiceProvider.this, MainActivity.class));
                else
                    Toast .makeText(AllServiceProvider.this,"Please Logout to ReLogin",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_profile :
                if(sessionManager.checkLogin())
                    startActivity(new Intent(AllServiceProvider.this, Profile.class));
                else
                {
                    Toast .makeText(AllServiceProvider.this,"Please Login",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
                break;
            case R.id.nav_logout:
                if(sessionManager.checkLogin())
                {
                    Toast .makeText(AllServiceProvider.this,"Logged out successfully",Toast.LENGTH_SHORT).show();
                    sessionManager.logout();
                    startActivity(new Intent(AllServiceProvider.this, UserDashboard.class));
                }
                break;
            case R.id.nav_requested_services:
                if(sessionManager.checkLogin())
                    startActivity(new Intent(AllServiceProvider.this, RequestedServices.class));
                else
                {
                    Toast .makeText(AllServiceProvider.this,"Please Login",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
                break;
        }
        return true;
    }


    private void all_service_provider() {

        all_service_provider.setHasFixedSize(true);
        all_service_provider.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("service_providers");

        final ArrayList<ServiceProvidersView>  serviceProvidersViews = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshots : snapshot.getChildren()){
                    serviceProvidersViews.add(new ServiceProvidersView(dataSnapshots.child("imageUrl").getValue().toString(),dataSnapshots.child("full_name").getValue().toString(),dataSnapshots.child("category").getValue().toString(),dataSnapshots.child("location").getValue().toString(),dataSnapshots.child("description").getValue().toString()));
                }
                adapter = new ServiceProviderViewAdapter(serviceProvidersViews,getApplicationContext());
                all_service_provider.setAdapter(adapter);

                adapter.setOnItemClickListener(new ServiceProviderViewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        serviceProvidersViews.get(position);
                        Intent intent = new Intent(AllServiceProvider.this, RequestService.class);
                        intent.putExtra("name",serviceProvidersViews.get(position).getFull_name().toString());
                        intent.putExtra("image",serviceProvidersViews.get(position).getImage().toString());
                        intent.putExtra("description",serviceProvidersViews.get(position).getDescription().toString());
                        intent.putExtra("category",serviceProvidersViews.get(position).getCategory().toString());
                        intent.putExtra("location",serviceProvidersViews.get(position).getLocation().toString());
                        startActivity(intent);

                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    private   void categorial_service_provider(final String string) {

        all_service_provider.setHasFixedSize(true);
        all_service_provider.setLayoutManager(new LinearLayoutManager(AllServiceProvider.this,LinearLayoutManager.VERTICAL, false));

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("service_providers");

        final ArrayList<ServiceProvidersView>  serviceProvidersViews = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshots : snapshot.getChildren()){

                    if((string.equalsIgnoreCase(dataSnapshots.child("category").getValue().toString()))){
                        serviceProvidersViews.add(new ServiceProvidersView(dataSnapshots.child("imageUrl").getValue().toString(),dataSnapshots.child("full_name").getValue().toString(),dataSnapshots.child("category").getValue().toString(),dataSnapshots.child("location").getValue().toString(),dataSnapshots.child("description").getValue().toString()));
                    }
                }
                adapter = new ServiceProviderViewAdapter(serviceProvidersViews,getApplicationContext());
                all_service_provider.setAdapter(adapter);

                adapter.setOnItemClickListener(new ServiceProviderViewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        serviceProvidersViews.get(position);
                        Intent intent = new Intent(AllServiceProvider.this, RequestService.class);
                        intent.putExtra("name",serviceProvidersViews.get(position).getFull_name().toString());
                        intent.putExtra("image",serviceProvidersViews.get(position).getImage().toString());
                        intent.putExtra("description",serviceProvidersViews.get(position).getDescription().toString());
                        intent.putExtra("category",serviceProvidersViews.get(position).getCategory().toString());
                        intent.putExtra("location",serviceProvidersViews.get(position).getLocation().toString());
                        startActivity(intent);
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}