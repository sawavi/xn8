package com.example.aaryan.xn8;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TwoFragmentOne.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TwoFragmentOne#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TwoFragmentOne extends Fragment implements View.OnClickListener {

    //defining view objects
    private EditText editTextAccNum;
    ///
    private TextView textViewAccAdded;
    private EditText editTextDebitCardNum;
    private EditText editTextIfscCodeNum;
    private EditText editTextBankName;
    private EditText editTextBranchName;
    private CheckBox checkCur;
    private CheckBox checkSav;

    private String mVarAccNum;
/////
    private String mVarAccType;
    private String mVarDebitCardNum;
    private String mVarIfscCodeNum;
    private String mVarBankName;
    private String mVarBranchName;

    private String mVarLocalUserId;  //private String UserID;


    private Button buttonSave;

    private ProgressDialog progressDialog;


    //defining firebaseauth object


    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mFirebaseAuth;




    public TwoFragmentOne() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View viewTwo = inflater.inflate(R.layout.fragment_two_fragment_one, container, false);

        //TODO: look into mFirebaseAuth and firebaseUser later
        mFirebaseAuth = FirebaseAuth.getInstance();          //initializing firebase auth object
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference  = mFirebaseDatabase.getReference("addBankAccounts");  // get reference to 'users' node


        editTextAccNum = (EditText) viewTwo.findViewById(R.id.fieldAccNum);
        textViewAccAdded= (TextView) viewTwo.findViewById(R.id.textAccAdded);
        editTextDebitCardNum= (EditText) viewTwo.findViewById(R.id.fieldCardNum);
        editTextIfscCodeNum= (EditText) viewTwo.findViewById(R.id.fieldIFSCCode);
        editTextBankName= (EditText) viewTwo.findViewById(R.id.fieldBankName);
        editTextBranchName= (EditText) viewTwo.findViewById(R.id.fieldBankBranch);
        checkCur =  (CheckBox) viewTwo.findViewById(R.id.checkCurrent);
        checkSav = (CheckBox) viewTwo.findViewById(R.id.checkSaving);
        buttonSave = (Button) viewTwo.findViewById(R.id.btSave);

        progressDialog = new ProgressDialog(getActivity());  //attaching listener to button
        buttonSave.setOnClickListener(this);  //


        checkCur.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(isChecked){
                    checkSav.setChecked(false);
                    // Code to display your message.
                }
            }
        });


        checkSav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(isChecked){
                    checkCur.setChecked(false);
                    // Code to display your message.
                }
            }
        });



        return viewTwo;
    }

    // TODO:


    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.btSave:
                progressDialog.setMessage("Adding Account Details, Please Wait...");
                progressDialog.show();
                userAddAccValidate();
                break;

        //    case R.id.btResendPwd:
        //        ResendUserPwd();
        //        break;
            // TODO:
        }


    }

    private void saveUserAddAccData(){


        userAccountModel localUser =new userAccountModel();
        localUser.setAccNum(mVarAccNum);
        localUser.setAccType(mVarAccType);
        localUser.setDebitCardNum(mVarDebitCardNum);
        localUser.setIfscCodeNum(mVarIfscCodeNum);
        localUser.setBankName(mVarBankName);
        localUser.setBranchName(mVarBranchName);

      //  DatabaseReference childRef = mDatabaseReference;
     //   childRef.child(mVarLocalUserId).setValue(localUser);


        if (mFirebaseUser == null) {
            Toast.makeText(getActivity(),"You Are Not Logged In",Toast.LENGTH_LONG).show();
            buttonSave.setEnabled(false);
        } else {
            mVarLocalUserId = mFirebaseUser.getUid();
            mDatabaseReference.child(mVarLocalUserId).push().setValue(localUser);
            buttonSave.setText("Add More");
            textViewAccAdded.setText("Account Added");

            progressDialog.setMessage("Account Details Added" );
            progressDialog.show();


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    progressDialog.dismiss();
                }
            }, 3000);

        }

    }
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkCurrent:
                if (checked){}
                else
                    break;
            case R.id.checkSaving:
                if (checked){}
                else
                    break;
        }
    }
    private void userAddAccValidate(){

        mVarAccNum = editTextAccNum.getText().toString().trim();
       // mVarAccType = editTextAccType.getText().toString().trim();
        mVarDebitCardNum = editTextDebitCardNum.getText().toString().trim();
        mVarIfscCodeNum = editTextIfscCodeNum.getText().toString().trim();
        mVarBankName = editTextBankName.getText().toString().trim();
        mVarBranchName = editTextBranchName.getText().toString().trim();



        if (    (((CheckBox) getView().findViewById(R.id.checkCurrent)).isChecked())  ) {
            //setting value   //??check out is it setting in database too??
            checkSav.setEnabled(false);
            mVarAccType = "Current";
        }
        else{
            mVarAccType = "Saving";
        }

        if( (!(   (   (CheckBox) getView().findViewById(R.id.checkCurrent)).isChecked()   )) &&   (!(   (   (CheckBox) getView().findViewById(R.id.checkSaving)).isChecked()   )) ){
            Toast.makeText(getActivity(),"Please Select Account Type",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(mVarAccNum)){
            Toast.makeText(getActivity(),"Please Enter Account Number",Toast.LENGTH_LONG).show();
            return;
        }
/**Optinal
        if(TextUtils.isEmpty(mVarDebitCardNum)){
            Toast.makeText(getActivity(),"Please Enter Debit Card Optional",Toast.LENGTH_LONG).show();
            return;
        }
*/
        if(TextUtils.isEmpty(mVarIfscCodeNum)){
            Toast.makeText(getActivity(),"Please Enter IFSC Code Number",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(mVarBankName)){
            Toast.makeText(getActivity(),"Please Enter Bank Name",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(mVarBranchName)){
            Toast.makeText(getActivity(),"Please Enter Branch Name",Toast.LENGTH_LONG).show();
            return;
        }


        saveUserAddAccData();
    }
}
