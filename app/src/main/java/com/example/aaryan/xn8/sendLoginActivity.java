package com.example.aaryan.xn8;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class sendLoginActivity extends AppCompatActivity implements View.OnClickListener {


    private Toolbar myToolbar;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonResend;
    private Button buttonSendPwd;
    private ProgressDialog progressDialog;


    private RelativeLayout loginUserLayout;
    private RelativeLayout resendLayout;


    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_login);
/**
        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); ///back button on actionbar
**/
        //getting already running firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //initializing views
        editTextEmail = (EditText) findViewById(R.id.fieldID);
        editTextPassword = (EditText) findViewById(R.id.fieldPwd);

        buttonLogin = (Button) findViewById(R.id.btLogin);
        buttonResend= (Button) findViewById(R.id.btResendPwd);
        buttonSendPwd=(Button) findViewById(R.id.btSendPass2);
        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        buttonLogin.setOnClickListener(this);
        buttonResend.setOnClickListener(this);
        buttonSendPwd.setOnClickListener(this);


        loginUserLayout = (RelativeLayout) findViewById(R.id.loginFieldsLayout);
        resendLayout = (RelativeLayout) findViewById(R.id.resendPassLayout);

        resendLayout.setVisibility(View.GONE);


    }


    @Override
    public void onClick(View view) {
        //calling register method on click
        switch(view.getId()){
            case R.id.btLogin:
                LoginUser();
                    break;
            case R.id.btResendPwd:
                forgotPwd();
                    break;

            case R.id.btSendPass2:
                sendPwdByMail();
                break;
        }
    }

    private  void sendPwdByMail(){
        // TODO:btSendPass2   send email to user
        Toast.makeText(sendLoginActivity.this,"Password Sent To Your Registered Email ID",Toast.LENGTH_LONG).show();
    }

    private void LoginUser(){

        //getting email and password from edit texts
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please Enter Your ID",Toast.LENGTH_LONG).show();
            return;
        }
        //IMP:  else logic is just to make any number into email id and then pass it firebase
        //as firebase does not allow phone numbers to user register App
        //password is not less than Six alphabets
        else{
            email=email.concat("@satya.com");
            Toast.makeText(this,"Concated"+email,Toast.LENGTH_LONG).show();
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please Enter Your Password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Sign In Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            //if success return to main activity to load user profile and other details

                            startActivity(new Intent(sendLoginActivity.this, MainActivity.class));
                            Toast.makeText(sendLoginActivity.this,"Successfully Logged In",Toast.LENGTH_LONG).show();

                        }else{
                            //display some message here
                            Toast.makeText(sendLoginActivity.this,"Log In  Error:Click Re-Send Password,If Forgotten ",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }



    private void forgotPwd(){

        // TODO:  IMP : add server side functionality to resend password ; and make id registration complusary

        loginUserLayout.setVisibility(View.GONE);
        resendLayout.setVisibility(View.VISIBLE);




    }



    @Override
    public void onStart() { super.onStart();
        // Check if user is signed in.
        // TODO: Add code to check if user is signed in.
    }

    @Override
    public void onPause() {  super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //added
        switch (item.getItemId()) {
            case R.id.menu_log_out:
                //no need of log out
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //
    }





}
