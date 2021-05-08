package com.example.jobhuntapp.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jobhuntapp.R;
import com.example.jobhuntapp.User.MainActivity;
import com.example.jobhuntapp.User.UserDashboard;

public class Splash_Screen extends AppCompatActivity {

    Animation top,bottom;
    ImageView logo;
    TextView name,slogan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash__screen);

        top = (Animation) AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottom = (Animation) AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        logo = (ImageView) findViewById(R.id.imageView);
        name = (TextView) findViewById(R.id.textView);
        slogan = (TextView) findViewById(R.id.textView2);


        logo.setAnimation(top);
        name.setAnimation(bottom);
        slogan.setAnimation(bottom);

        LogoLauncher logoLauncher = new LogoLauncher();
        logoLauncher.start();
    }

    private class LogoLauncher extends Thread{
        public void run(){
            try {
                sleep(2000);
            }catch (Exception e){
                e.printStackTrace();
            }
            Intent intent = new Intent(Splash_Screen.this, UserDashboard.class);
            /*Pair[] pairs = new Pair[3];
            pairs[0] = new Pair<View,String>(logo,"logo_image");
            pairs[1] = new Pair<View,String>(name,"logo_text");
            pairs[2] = new Pair<View,String>(name,"logo_slogan");
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Splash_Screen.this,pairs);
            startActivity(intent,options.toBundle());*/
            startActivity(intent);
            Splash_Screen.this.finish();
        }
    }
}