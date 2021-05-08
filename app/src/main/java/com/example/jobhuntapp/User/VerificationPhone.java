package com.example.jobhuntapp.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.jobhuntapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

import static com.google.firebase.auth.PhoneAuthProvider.*;

public class VerificationPhone extends AppCompatActivity {

    Button verify;
    ProgressBar progressBar;
    String verificationCodeBySystem;

    DatabaseReference databaseReference;

    FirebaseAuth mAuth;
    PinView otpFromUser;
    TextView sample;
    ImageView cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_phone);

        verify = (Button) findViewById(R.id.verify);
        cancel = (ImageView) findViewById(R.id.cancel);
        otpFromUser = (PinView) findViewById(R.id.pin_view);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        sample = (TextView) findViewById(R.id.sample);

        //Getting intent data from other activity
        String S_name = getIntent().getStringExtra("fullname");
        String S_uname = getIntent().getStringExtra("username");
        String S_email = getIntent().getStringExtra("email");
        String S_phno = getIntent().getStringExtra("pno");
        String S_pswd = getIntent().getStringExtra("password");

       String s = S_email+S_name+S_uname+S_phno+S_pswd;
       sample.setText(s);

        otpFromUser = (PinView) findViewById(R.id.pin_view);
        mAuth = FirebaseAuth.getInstance();



        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerificationPhone.this,SignUp.class);
                startActivity(intent);
            }
        });

        sendVerificationCode(S_phno);

    }



    public void nextScreen(View view){
        String code = otpFromUser.getText().toString();
        if (code.isEmpty() || code.length() < 6){
            otpFromUser.setError("Enter valid code");
            otpFromUser.requestFocus();
            return;
        }
        verifyVerificationCode(code);
    }

    private void sendVerificationCode(String mobileNo) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobileNo,
                60,
                TimeUnit.SECONDS,
                (Activity) TaskExecutors.MAIN_THREAD,
                mCallback
        );
    }

    //the callback to detect the verification status
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null){
                otpFromUser.setText(code);
                // verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(VerificationPhone.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            //storing the verification id that is sent to the user
            verificationCodeBySystem = s;
        }
    };

    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem,code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //verification successful we will start the profile activity
                            Toast.makeText(VerificationPhone.this, "Successful", Toast.LENGTH_SHORT).show();
                        }else {
                            //verification unsuccessful.. display an error message

                            String msg = "Something went wrong";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                                msg = "Invalid code entered";
                            }

                            Snackbar snackbar = Snackbar.make(findViewById(R.id.parent),msg,Snackbar.LENGTH_SHORT);
                            snackbar.setAction("Dismiss", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                            snackbar.show();
                        }
                    }
                });

    }







    /*private void sendVerificationToUser(String s_phno) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(s_phno)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {

            String code = credential.getSmsCode();
            if(code!=null){
                otpFromUser.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
           Toast.makeText(VerificationPhone.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(@NonNull String verificationId,
                @NonNull PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(verificationId,token);

            verificationCodeBySystem = verificationId;
        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem,code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(VerificationPhone.this,"Verification Success",Toast.LENGTH_LONG).show();
                        } else {

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(VerificationPhone.this,"Verification Failed",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }
    public void nextScreen(View view){
        String code = otpFromUser.getText().toString();
        if(!code.isEmpty()){
            verifyCode(code);
        }
    }*/
}
















//String EnteredPhoneNo = getIntent().getStringExtra("phoneNo");

        /*OtpInitiate(EnteredPhoneNo);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp_code = otp.getText().toString();
                /*if(otp_code.isEmpty() || otp_code.length()<6){
                    otp.setError("Wrong OTP.....");
                    otp.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(otp_code);//
               if(otp_code.isEmpty() || otp_code.length()<6){
                    otp.setError("Wrong OTP.....");
                    otp.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem,otp_code);
                signInWithPhoneAuthCredential(credential);
            }
        });*/




    /*private void OtpInitiate(String enteredPhoneNo) {
        mCallbacks = new OnVerificationStateChangedCallbacks(){
            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                //Get the code in global variable
                verificationCodeBySystem = s;
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                String code = phoneAuthCredential.getSmsCode();
                if(code!=null){
                    progressBar.setVisibility(View.VISIBLE);
                    signInWithPhoneAuthCredential(phoneAuthCredential);
                    }
            }
            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(VerificationPhone.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        };
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber("+91 "+enteredPhoneNo)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(VerificationPhone.this,UserDashboard.class));
                            finish();
                        } else {
                            Toast.makeText(VerificationPhone.this,"SignIn Code Error",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }*/