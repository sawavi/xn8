package com.example.aaryan.xn8;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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

    private EditText editTextDebitCardNum;
    private EditText editTextIfscCodeNum;
    private EditText editTextBankName;
    private EditText editTextBranchName;
    private CheckBox checkCur;
    private CheckBox checkSav;

    //layout toggling

    private RelativeLayout addBankLayout;
    private RelativeLayout doneLayout;
    private ViewGroup.LayoutParams doneParams;
    private ScrollView myScrollOne;
    private Button buttonDoneLeft;
    private Button buttonDoneRight;
    private TextView textViewDoneMsg;
    private DisplayMetrics metrics;


    private String mVarAccNum;

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
        View viewOne = inflater.inflate(R.layout.fragment_two_fragment_one, container, false);
        metrics = getActivity().getResources().getDisplayMetrics();
        //TODO: look into mFirebaseAuth and firebaseUser later
        mFirebaseAuth = FirebaseAuth.getInstance();          //initializing firebase auth object
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference  = mFirebaseDatabase.getReference("addBankAccounts");  // get reference to 'users' node


        editTextAccNum = (EditText) viewOne.findViewById(R.id.fieldAccNum);
        editTextDebitCardNum= (EditText) viewOne.findViewById(R.id.fieldCardNum);
        editTextIfscCodeNum= (EditText) viewOne.findViewById(R.id.fieldIFSCCode);
        editTextBankName= (EditText) viewOne.findViewById(R.id.fieldBankName);
        editTextBranchName= (EditText) viewOne.findViewById(R.id.fieldBankBranch);
        checkCur =  (CheckBox) viewOne.findViewById(R.id.checkCurrent);
        checkSav = (CheckBox) viewOne.findViewById(R.id.checkSaving);
        buttonSave = (Button) viewOne.findViewById(R.id.btSave);


        //layout toggling

        addBankLayout = (RelativeLayout) viewOne.findViewById(R.id.addBankLayout);
        doneLayout = (RelativeLayout) viewOne.findViewById(R.id.doneLayout);
        doneParams = doneLayout.getLayoutParams();

        buttonDoneLeft = (Button) viewOne.findViewById(R.id.btDoneLeft);
        buttonDoneRight = (Button) viewOne.findViewById(R.id.btDoneRight);
        textViewDoneMsg = (TextView) viewOne.findViewById(R.id.textDoneMsg);

        //layout toggling : setting Scroll to foucus on top
        myScrollOne = (ScrollView) viewOne.findViewById(R.id.scrollFragOne);

        if (mFirebaseUser == null) {
            doneParams.height = metrics.heightPixels;
            doneParams.width = metrics.widthPixels;
            doneLayout.setBackgroundColor(Color.parseColor("#F5F5F5"));
            doneLayout.setVisibility(View.VISIBLE);

            myScrollOne.smoothScrollTo(0,0); //excellent solution to set focus on top of reappearing layout
            addBankLayout.setVisibility(View.GONE);    //View.GONE --disappear element and vacant spcae too
            buttonSave.setVisibility(View.INVISIBLE);  //View.INVISIBLE --disappear element but keeps space occupied

            //display doneLayout with only text ; buttons gone
            textViewDoneMsg.setText(getResources().getString(R.string.textFragOneDone));
            buttonDoneLeft.setVisibility(View.INVISIBLE);
            buttonDoneRight.setVisibility(View.INVISIBLE);

        }


        progressDialog = new ProgressDialog(getActivity());  //attaching listener to button
        buttonSave.setOnClickListener(this);  //

        buttonDoneLeft.setOnClickListener(this);//



        //Simplest way to Toggle CheckBox
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

        return viewOne;
    }
    // TODO:

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.btSave:
            userAddAccValidate();
                break;
            case R.id.btDoneLeft:
                reappearAddBankDisplay();
                break;
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

            mVarLocalUserId = mFirebaseUser.getUid();
            mDatabaseReference.child(mVarLocalUserId).push().setValue(localUser);

            buttonSave.setText("Add More");

            progressDialog.setMessage("Adding Account Details..... " );
            progressDialog.show();
            // handler thread used to delay dismis of Dialog by 2 Sec
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    progressDialog.dismiss();
                }
            }, 2000);

        disappearAddBankDisplay();

    }

    private void disappearAddBankDisplay(){

        doneParams.height = addBankLayout.getHeight();
        doneParams.width = addBankLayout.getWidth();
        doneLayout.setBackgroundColor(Color.parseColor("#F5F5F5"));

        myScrollOne.smoothScrollTo(0,0); //excellent solution to set focus on top of reappearing layout
        addBankLayout.setVisibility(View.GONE);    //View.GONE --disappear element and vacant spcae too
        buttonSave.setVisibility(View.INVISIBLE);  //View.INVISIBLE --disappear element but keeps space occupied

        //display doneLayout with two buttons
        doneLayout.setVisibility(View.VISIBLE);
        textViewDoneMsg.setText(getResources().getString(R.string.textFragOneDoneSaved));

    }

    private void reappearAddBankDisplay(){

        doneLayout.setVisibility(View.GONE);
        addBankLayout.setVisibility(View.VISIBLE);
        buttonSave.setVisibility(View.VISIBLE);
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
        mVarDebitCardNum = editTextDebitCardNum.getText().toString().trim();
        mVarIfscCodeNum = editTextIfscCodeNum.getText().toString().trim();
        mVarBankName = editTextBankName.getText().toString().trim();
        mVarBranchName = editTextBranchName.getText().toString().trim();



        if (    (((CheckBox) getView().findViewById(R.id.checkCurrent)).isChecked())  ) {
            //setting value   //??check out is it setting in database too??
            mVarAccType = "Current";
        }
        if (    (((CheckBox) getView().findViewById(R.id.checkSaving)).isChecked())  ){
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
