package com.example.jobhuntapp.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jobhuntapp.Databases.SessionManager;
import com.example.jobhuntapp.HelperClasses.UserHelperClass;
import com.example.jobhuntapp.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

public class SignUp2 extends AppCompatActivity {

    TextInputLayout pno;
    Button sign_in,next;
    TextView textView;
    ImageView back;

    CountryCodePicker countryCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        pno = (TextInputLayout) findViewById(R.id.pno) ;
        back = (ImageView) findViewById(R.id.back_arrow) ;
        sign_in = (Button) findViewById(R.id.sign_in2);
        next = (Button) findViewById(R.id.next2);
        textView = (TextView) findViewById(R.id.textViewaccount);
        countryCodePicker = (CountryCodePicker) findViewById(R.id.country_code);



        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp2.this,MainActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp2.this,SignUp.class);
                startActivity(intent);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!validatePhoneNumber())
                {
                    return;
                }
                Intent intent = new Intent(SignUp2.this, UserDashboard.class);

                Pair[] pairs = new Pair[4];
                pairs[0] = new Pair<View,String>(back,"signup_image_view");
                pairs[1] = new Pair<View,String>(textView,"signup_text_view");
                pairs[2] = new Pair<View,String>(next,"signup_next_btn");
                pairs[3] = new Pair<View,String>(sign_in,"signup_login_btn");

                String _getUserEnteredPhoneNumber = pno.getEditText().getText().toString().trim();
                    //Remove first zero if entered!
                if (_getUserEnteredPhoneNumber.charAt(0) == '0') {
                    _getUserEnteredPhoneNumber = _getUserEnteredPhoneNumber.substring(1);
                }
                    //Complete phone number
                final String _phoneNo = "+" + countryCodePicker.getFullNumber() + _getUserEnteredPhoneNumber;

                String S_name = getIntent().getStringExtra("fullname");
                String S_uname = getIntent().getStringExtra("username");
                String S_email = getIntent().getStringExtra("email");
                String S_pswd = getIntent().getStringExtra("password");

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("users");
                UserHelperClass helperClass = new UserHelperClass(S_name,S_uname,S_pswd,S_email,_phoneNo,getIntent().getStringExtra("image"));

                myRef.child(S_uname).setValue(helperClass);

                //Shared preferences
                SessionManager sessionManager = new SessionManager(SignUp2.this);
                sessionManager.createLoginSession(S_name,S_uname,S_pswd,_phoneNo,getIntent().getStringExtra("image"),S_email);

                startActivity(intent);
                SignUp2.this.finish();
            }
        });

    }

    private boolean validatePhoneNumber() {
        String val = pno.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,20}\\z";
        if (val.isEmpty()) {
            pno.setError("Enter valid phone number");
            return false;
        } else if (!val.matches(checkspaces)) {
            pno.setError("No White spaces are allowed!");
            return false;
        } else {
            pno.setError(null);
            pno.setErrorEnabled(false);
            return true;
        }
    }
}