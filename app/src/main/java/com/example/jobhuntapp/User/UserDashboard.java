package com.example.jobhuntapp.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobhuntapp.Databases.SessionManager;
import com.example.jobhuntapp.HelperClasses.HomeAdapter.AllServiceAdapter;
import com.example.jobhuntapp.HelperClasses.HomeAdapter.AllServiceClass;
import com.example.jobhuntapp.HelperClasses.HomeAdapter.CategoriesAdapter;
import com.example.jobhuntapp.HelperClasses.HomeAdapter.CategoriesClass;
import com.example.jobhuntapp.HelperClasses.HomeAdapter.MostRequestedAdapter;
import com.example.jobhuntapp.HelperClasses.HomeAdapter.MostRequestedClass;
import com.example.jobhuntapp.HelperClasses.ServiceProviderViewAdapter;
import com.example.jobhuntapp.R;
import com.example.jobhuntapp.ServiceProvider.AddRequest;
import com.example.jobhuntapp.ServiceProvider.AllServiceProvider;
import com.example.jobhuntapp.ServiceProvider.RequestService;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.collection.LLRBNode;

import java.util.ArrayList;


public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    static final float END_SCALE = 0.7f;
    RecyclerView all_services, most_requested_services, categories;
    MostRequestedAdapter  adapter;
    AllServiceAdapter adapter1;
    ImageView menu_icon,add_request;
    View view_cat;

    CategoriesAdapter adapter3;

    LinearLayout contentView;
    LinearLayout plumbing,self_care,computer,cooking;
    DrawerLayout drawerLayout;
    NavigationView navigationView;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    String CategoryInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        all_services = (RecyclerView) findViewById(R.id.all_services);
        all_services();


        most_requested_services = (RecyclerView) findViewById(R.id.most_requested_service);
        most_requested_services();

        categories = (RecyclerView) findViewById(R.id.categories);
        categories();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        plumbing = (LinearLayout) findViewById(R.id.plumbing_category);
        self_care = (LinearLayout) findViewById(R.id.self_care_category);
        computer = (LinearLayout) findViewById(R.id.computer_category);
        cooking = (LinearLayout) findViewById(R.id.cooking_category);


        menu_icon = (ImageView) findViewById(R.id.menu_icon);
        add_request = (ImageView) findViewById(R.id.add_request2);
        contentView = (LinearLayout) findViewById(R.id.content_view);
        final SessionManager sessionManager = new SessionManager(this);


        add_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sessionManager.checkLogin())
                    startActivity(new Intent(UserDashboard.this, AddRequest.class));
                else
                {
                    Toast .makeText(UserDashboard.this,"Please Login",Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(UserDashboard.this,UserDashboard.class));
                break;
            case R.id.nav_search:
                Toast .makeText(UserDashboard.this,"Search Icon Selected",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_request_service:
                if(sessionManager.checkLogin())
                    startActivity(new Intent(UserDashboard.this, AddRequest.class));
                else
                {
                    Toast .makeText(UserDashboard.this,"Please Login",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
                break;
            case R.id.nav_share:
                Toast .makeText(UserDashboard.this,"Share Icon Selected",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_login:
                if(!sessionManager.checkLogin())
                    startActivity(new Intent(UserDashboard.this, MainActivity.class));
                else
                    Toast .makeText(UserDashboard.this,"Please Logout to ReLogin",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_profile :
                if(sessionManager.checkLogin())
                    startActivity(new Intent(UserDashboard.this, Profile.class));
                else
                {
                    Toast .makeText(UserDashboard.this,"Please Login",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
                break;
            case R.id.nav_logout:
                if(sessionManager.checkLogin())
                {
                    Toast .makeText(UserDashboard.this,"Logged out successfully",Toast.LENGTH_SHORT).show();
                    sessionManager.logout();
                    startActivity(new Intent(UserDashboard.this, UserDashboard.class));
                }
                break;
            case R.id.nav_requested_services:
                if(sessionManager.checkLogin())
                    startActivity(new Intent(UserDashboard.this, RequestedServices.class));
                else
                {
                    Toast .makeText(UserDashboard.this,"Please Login",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
                break;
        }
        return true;
    }

    private void categories() {
        categories.setHasFixedSize(true);
        categories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        final ArrayList<CategoriesClass> categoriesClasses = new ArrayList<>();

        categoriesClasses.add(new CategoriesClass(R.drawable.plumber,"PLUMBING", Color.rgb(	212, 203, 229)));
        categoriesClasses.add(new CategoriesClass(R.drawable.selfcare,"self-care",Color.rgb(	122, 220, 207)));
        categoriesClasses.add(new CategoriesClass(R.drawable.chef,"chef",Color.rgb(	247, 197, 159)));
        categoriesClasses.add(new CategoriesClass(R.drawable.computer,"Computer",Color.rgb(	184, 215, 245)));

        adapter3 = new CategoriesAdapter(categoriesClasses,getApplicationContext());
        categories.setAdapter(adapter3);

        adapter3.setOnItemClickListener(new CategoriesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                categoriesClasses.get(position);
                if((categoriesClasses.get(position).getTitle().equalsIgnoreCase("PLUMBING"))){
                    Plumbing(view_cat);
                }
                else if((categoriesClasses.get(position).getTitle().equalsIgnoreCase("self-care")))
                {
                    Self_care(view_cat);
                }
                else if((categoriesClasses.get(position).getTitle().equalsIgnoreCase("chef")))
                {
                    Cooking(view_cat);
                }
                else if((categoriesClasses.get(position).getTitle().equalsIgnoreCase("Computer")))
                {
                    Laptop_Service(view_cat);
                }
            }
        });
    }

    private void most_requested_services() {
        most_requested_services.setHasFixedSize(true);
        most_requested_services.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("service_providers");

        final ArrayList<MostRequestedClass>  mostRequestedClasses = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshots : snapshot.getChildren()){
                    mostRequestedClasses.add(new MostRequestedClass(dataSnapshots.child("imageUrl").getValue().toString(),dataSnapshots.child("full_name").getValue().toString(),dataSnapshots.child("category").getValue().toString(),dataSnapshots.child("location").getValue().toString(),dataSnapshots.child("description").getValue().toString()));
                }
                adapter = new MostRequestedAdapter(mostRequestedClasses,getApplicationContext());
                most_requested_services.setAdapter(adapter);



                adapter.setOnItemClickListener(new MostRequestedAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        mostRequestedClasses.get(position);
                        Intent intent = new Intent(UserDashboard.this, RequestService.class);
                        intent.putExtra("name",mostRequestedClasses.get(position).getFull_name().toString());
                        intent.putExtra("image",mostRequestedClasses.get(position).getImage().toString());
                        intent.putExtra("description",mostRequestedClasses.get(position).getDescription().toString());
                        intent.putExtra("category",mostRequestedClasses.get(position).getCategory().toString());
                        intent.putExtra("location",mostRequestedClasses.get(position).getLocation().toString());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void all_services() {
        all_services.setHasFixedSize(true);
        all_services.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("service_providers");

        final ArrayList<AllServiceClass>  allServiceClasses = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshots : snapshot.getChildren()){
                    allServiceClasses.add(new AllServiceClass(dataSnapshots.child("imageUrl").getValue().toString(),dataSnapshots.child("full_name").getValue().toString(),dataSnapshots.child("category").getValue().toString(),dataSnapshots.child("location").getValue().toString(),dataSnapshots.child("description").getValue().toString()));
                }
                adapter1 = new AllServiceAdapter(allServiceClasses,getApplicationContext());
                all_services.setAdapter(adapter1);


                adapter1.setOnItemClickListener(new AllServiceAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        allServiceClasses.get(position);
                        Intent intent = new Intent(UserDashboard.this, RequestService.class);
                        intent.putExtra("name",allServiceClasses.get(position).getFull_name().toString());
                        intent.putExtra("image",allServiceClasses.get(position).getImage().toString());
                        intent.putExtra("description",allServiceClasses.get(position).getDescription().toString());
                        intent.putExtra("category",allServiceClasses.get(position).getCategory().toString());
                        intent.putExtra("location",allServiceClasses.get(position).getLocation().toString());
                        startActivity(intent);
                    }
                });
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void Plumbing(View view){
        CategoryInput = "Plumbing";
        Category_screen(view);
    }
    public void Cooking(View view){
        CategoryInput = "Cooking";
        Category_screen(view);
    }
    public void Self_care(View view){
        CategoryInput = "Self-Care";
        Category_screen(view);
    }
    public void Laptop_Service(View view){
        CategoryInput = "Laptop Service";
        Category_screen(view);
    }

    public void Teaching(View view){
        CategoryInput = "Teaching";
        Category_screen(view);
    }

    public void Category_screen(View view) {
        Intent intent = new Intent(UserDashboard.this, AllServiceProvider.class);
        intent.putExtra("category",CategoryInput);
        startActivity(intent);

    }

    public void ViewAll(View view) {
        startActivity(new Intent(UserDashboard.this, AllServiceProvider.class));
    }
}