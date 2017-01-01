package com.example.aaryan.xn8;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sendRegistrationActivity extends AppCompatActivity implements View.OnClickListener{

    // to include Child Activity in Tabs need to use GroupActivity and HostActivity
    //work-around: toolbar added individualy in it as its an Activity poping up seperatly from Fragmented Tabs
    // tool bar and menu added, not whole tabs
    // to return to main Activity used getSupportActionBar().setDisplayHomeAsUpEnabled(true); method and
    // configured in AndroidMenifest as parent activity
    // so that upon clicking back button on tool bar it will return to main activity


    private Toolbar myToolbar;


    //defining view objects
    private EditText editTextPassword;
    private EditText editTextYourIdIs;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextMobNum;
    private EditText editTextEmail;
    private EditText editTextPanNum;
    private EditText editTextAadhaarNum;


    private String firePassword;
    private String mVarYourIdIs;
    private String mVarFirstName;
    private String mVarLastName;
    private double mVarMobNum;
    private String mVarEmailId;
    private String mVarPanNum;
    private String mVarAadhaarNum;

    private boolean mVarCardToggle;


    private Button buttonSubmit;
    private ProgressDialog progressDialog;


    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;
    private String authExcep;


    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mFirebaseDatabase;
    private String mVarLocalUserId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_registration);

        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //back button on ActionBar

        firebaseAuth = FirebaseAuth.getInstance();          //initializing firebase auth object

        mFirebaseDatabase = FirebaseDatabase.getInstance();

        mDatabaseReference  = mFirebaseDatabase.getReference("users");  // get reference to 'users' node


        //initializing views
        editTextPassword = (EditText) findViewById(R.id.fieldPwd);
        editTextYourIdIs = (EditText) findViewById(R.id.fieldYourIdIs);

        editTextFirstName = (EditText) findViewById(R.id.fieldFirstName);
        editTextLastName = (EditText) findViewById(R.id.fieldLastName);
        editTextMobNum = (EditText) findViewById(R.id.fieldMobileNo);
        editTextEmail = (EditText) findViewById(R.id.fieldEmailId);
        editTextPanNum = (EditText) findViewById(R.id.fieldPanNo);
        editTextAadhaarNum = (EditText) findViewById(R.id.fieldAadhaarNo);

        buttonSubmit = (Button) findViewById(R.id.btSubmit);

        progressDialog = new ProgressDialog(this);  //attaching listener to button
        buttonSubmit.setOnClickListener(this);  //



            //setting value for editTextYourIdIs upon condition
            editTextPanNum.addTextChangedListener(new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (s.length() != 0) {
                        if (    (((CheckBox) findViewById(R.id.checkbox_pan)).isChecked())  ) {
                            //setting value   //??check out is it setting in database too??
                            editTextYourIdIs.setText(editTextPanNum.getText().toString().trim());
                            mVarCardToggle =true;
                        }
                    }

                }
            });


        editTextAadhaarNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() != 0) {
                    if (    ( ( (CheckBox) findViewById(R.id.checkbox_aadhaar)).isChecked() ) && (!(((CheckBox) findViewById(R.id.checkbox_pan)).isChecked()))  ) {
                        //setting value   //??check out is it setting in database too??
                        editTextYourIdIs.setText(editTextAadhaarNum.getText().toString().trim());
                        mVarCardToggle =false;
                    }
                }

            }
        });

        //non editable edit text
        editTextYourIdIs.setEnabled(false);

    }


    @Override
    public void onClick(View view) {
                registerUser();     //calling register method on click
    }


    private void addUserProfileDetails(){

        //instead of adding data-structure in FIREBASE by importing JSON file ; simply push POJO Object into database

        userProfileModel localUser =new userProfileModel();

        localUser.setFirstName(mVarFirstName);
        localUser.setLastName(mVarLastName);
        localUser.setMobileNumber(mVarMobNum);
        localUser.setEmailId(mVarEmailId);
        localUser.setPanCard(mVarPanNum);
        localUser.setAadhaarCard(mVarAadhaarNum);
        localUser.setYourIdIs(mVarYourIdIs);
        localUser.setUID(mVarLocalUserId);
        localUser.setUID(firePassword);

        //mFirebaseDatabase.child("users").push().setValue(localUser);

        DatabaseReference childRef = mDatabaseReference;


        //mVarLocalUserId = childRef.push().getKey();   ---> it simply gets unique key and pushes into database ; its unique to every push operation
        // though its unique key ,it not similar to getUid(), as it returns unique key per User

 //-->       mVarLocalUserId = childRef.push().getKey();

        // adds the child's data to the value passed in from the text box.
        //childRef.setValue(mVarFirstName);

        //adds data to perticularly mentioned node-fields
        //childRef.child("mVarFirstName").setValue(localUser.getFirstName());
        //childRef.child("mVarLastName").setValue(localUser.getLastName());

        // adds whole object into database
        //childRef.child(mVarLocalUserId).setValue(localUser);
        childRef.child(mVarLocalUserId).setValue(localUser);

    }

    private void registerUser(){


        //String fireEmail = editTextEmail.getText().toString().trim();
        String fireEmail;
        firePassword  = editTextPassword.getText().toString().trim();


        mVarFirstName = editTextFirstName.getText().toString().trim();
        mVarLastName = editTextLastName.getText().toString().trim();

        mVarEmailId = editTextEmail.getText().toString().trim();
        mVarPanNum = editTextPanNum.getText().toString().trim();
        mVarAadhaarNum = editTextAadhaarNum.getText().toString().trim();

        //assigning value to email; i.e. IDs turning into email by appending extra string
        // assigning values  n toggling mVarYourIdIs to store in database

        if(mVarCardToggle == true) {
            mVarYourIdIs = mVarPanNum;
            fireEmail = mVarPanNum;
            fireEmail=fireEmail.concat("@satya.com");
            Toast.makeText(this,"Concated Pan"+fireEmail,Toast.LENGTH_LONG).show();
        }else {
            mVarYourIdIs = mVarAadhaarNum;
            fireEmail = mVarAadhaarNum;
            fireEmail=fireEmail.concat("@satya.com");
            Toast.makeText(this,"Concated Aadhaar"+fireEmail,Toast.LENGTH_LONG).show();
        }

        //TextUtils.isEmpty(mVarFirstName) --> causes crash to check for Double Value ; use below logic
        if ((editTextMobNum.getText().toString().trim()).equals("")){
            Toast.makeText(this,"Please Enter Mobile Number",Toast.LENGTH_LONG).show();
            return;
        }else{
            //setting doubles value here
            mVarMobNum = Double.parseDouble(editTextMobNum.getText().toString().trim());
        }

        //validating other values

        if(     (   (   (CheckBox) findViewById (R.id.checkbox_pan) ).isChecked()   ) && (TextUtils.isEmpty(mVarPanNum))){
            Toast.makeText(this,"Please Enter Pan Number",Toast.LENGTH_LONG).show();
            return;
        }
        if(    (   (   (CheckBox) findViewById (R.id.checkbox_aadhaar) ).isChecked()   ) && (TextUtils.isEmpty(mVarAadhaarNum))){
            Toast.makeText(this,"Please Enter Aadhaar Number",Toast.LENGTH_LONG).show();
            return;
        }
        if( (!(   (   (CheckBox) findViewById (R.id.checkbox_pan) ).isChecked()   )) &&   (!(   (   (CheckBox) findViewById (R.id.checkbox_aadhaar) ).isChecked()   )) ){
            Toast.makeText(this,"Atleast Pan Or Aadhaar Card Must Selected",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(mVarFirstName)){
            Toast.makeText(this,"Please Enter First Name",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(mVarLastName)){
            Toast.makeText(this,"Please Enter Last Name",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(mVarEmailId)){
            Toast.makeText(this,"Please Enter Email Id",Toast.LENGTH_LONG).show();
            return;
        }
        if(     (      !((CheckBox) findViewById (R.id.checkAgg) ).isChecked() )   ){
            Toast.makeText(this,"Please Accept Agreement To Register",Toast.LENGTH_LONG).show();
            return;
        }


      /*
        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }
        //IMP:  else logic is just to make any number into email id and then pass it firebase
        //as firebase does not allow phone numbers to user register App
        //password is not less than Six alphabets
        else{
            email=email.concat("@satya.com");
            Toast.makeText(this,"Concated"+email,Toast.LENGTH_LONG).show();
        }
*/
        if(TextUtils.isEmpty(firePassword)){
            Toast.makeText(this,"Please Enter Password",Toast.LENGTH_LONG).show();
            return;
        }


        //if the email and password are not empty
        //displaying a progress dialog
        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();




        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(fireEmail, firePassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            //if success return to main activity to load user profile and other details


                            FirebaseUser fbuser = task.getResult().getUser();
                            mVarLocalUserId = fbuser.getUid();

                            addUserProfileDetails();

                            startActivity(new Intent(sendRegistrationActivity.this, MainActivity.class));
                            Toast.makeText(sendRegistrationActivity.this,"Successfully registered",Toast.LENGTH_LONG).show();

                        }else if(!task.isSuccessful()){
                            //display some message here
                            try {
                                throw task.getException();
                            } catch(FirebaseAuthWeakPasswordException e) {
                                authExcep = e.getMessage();
                            } catch(FirebaseAuthInvalidCredentialsException e) {
                                authExcep = e.getMessage();
                            } catch(FirebaseAuthUserCollisionException e) {
                                authExcep = e.getMessage();
                            } catch(Exception e) {
                                authExcep = e.getMessage();
                            }


                            Toast.makeText(sendRegistrationActivity.this,"Error :" + authExcep ,Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }





//look tomarrow
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_pan:
                if (checked){


                }
                // Put some meat on the sandwich
                else
                // Remove the meat
                break;
            case R.id.checkbox_aadhaar:
                if (checked){}
                // Cheese me
                else
                // I'm lactose intolerant
                break;
            // TODO: Veggie sandwich
        }
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

        getMenuInflater().inflate(R.menu.menu_main, menu);      // Inflate the menu; this adds items to the action bar if it is present.
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //added
        switch (item.getItemId()) {
            case R.id.menu_log_out:
                //firebaseAuth.signOut();
                // mUsername = ANONYMOUS;
                //startActivity(new Intent(this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //
    }



}