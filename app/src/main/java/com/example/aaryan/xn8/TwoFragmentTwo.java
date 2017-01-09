package com.example.aaryan.xn8;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
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
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TwoFragmentTwo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TwoFragmentTwo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TwoFragmentTwo extends Fragment implements View.OnClickListener {

    /**
     * soft keyboard was overlapping fragment edittext * additionaly android:paddingbottom:"50dp" was hiding exta 50dp space whenever softkeyboard poped up
     * removed android:paddingBottom="50dp"  and added android:windowSoftInputMode="stateHidden|adjustPan" in AndroidMenifest.xml
     */

    // TODO: imp : transaction id on slip>>>
    // TODO: deposited in branch : the moment user select tab:choose bank
    // ....deposited in branch and transaction id must append and show an uneditable Slip only after user doen't present he can delete slip


    //defining view objects
    private EditText editTextAccName;
    private EditText editTextAccNum;
    private EditText editTextDebitCardNum;
    private EditText editTextPanNum;
    //private EditText editTextIfscCodeNum;
    //private EditText editTextBankName;
    private EditText editTextBranchName;

    //layout toggling

    private RelativeLayout slipLayout;
    private RelativeLayout doneLayout;
    private ViewGroup.LayoutParams doneParams;
    private ScrollView myScrollTwo;
    private Button buttonDoneLeft;
    private Button buttonDoneRight;
    private TextView textViewDoneMsg;

    private CheckBox checkDeposit;
    private CheckBox checkWithdrawal;
    private CheckBox checkDD;

    private CheckBox checkCur;
    private CheckBox checkSav;

    private String mVarAccName;
    private String mVarAccNum;
    private String mVarAccType;
    private String mVarDebitCardNum;
    private String mVarPanNum;
    //private String mVarIfscCodeNum;
    //private String mVarBankName;
    private String mVarBranchName;

    private String mVarLocalUserId;  //private String UserID;


    private Button buttonSave;

    private ProgressDialog progressDialog;


    //defining firebaseauth object


    private DatabaseReference mDatabaseRefDeposit;
    private DatabaseReference mDatabaseRefWithdrawal;
    private DatabaseReference mDatabaseRefDD;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mFirebaseAuth;


    public TwoFragmentTwo() {
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
        View viewTwo = inflater.inflate(R.layout.fragment_two_fragment_two, container, false);

        //only working solution to get parent width height in onCreateView()
      //  Display display = getActivity().getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
        //TODO: look into mFirebaseAuth and firebaseUser later
        mFirebaseAuth = FirebaseAuth.getInstance();          //initializing firebase auth object
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        //ref added in if-else
        //mDatabaseRefDeposit = mFirebaseDatabase.getReference("addDeposit");  // get reference to 'users' node

        editTextAccName = (EditText) viewTwo.findViewById(R.id.fieldACName);
        editTextAccNum = (EditText) viewTwo.findViewById(R.id.fieldAccNum);
        editTextDebitCardNum = (EditText) viewTwo.findViewById(R.id.fieldCardNum);
        editTextPanNum = (EditText) viewTwo.findViewById(R.id.fieldPanNo);
        // editTextIfscCodeNum= (EditText) viewTwo.findViewById(R.id.fieldIFSCCode);
        // editTextBankName= (EditText) viewTwo.findViewById(R.id.fieldBankName);
        editTextBranchName = (EditText) viewTwo.findViewById(R.id.fieldBankBranch);

        buttonSave = (Button) viewTwo.findViewById(R.id.btSave);

       //layout toggling

        slipLayout = (RelativeLayout) viewTwo.findViewById(R.id.fillSlipLayout);
        doneLayout = (RelativeLayout) viewTwo.findViewById(R.id.doneLayout);
        doneParams = doneLayout.getLayoutParams();

        buttonDoneLeft = (Button) viewTwo.findViewById(R.id.btDoneLeft);
        buttonDoneRight = (Button) viewTwo.findViewById(R.id.btDoneRight);
        textViewDoneMsg = (TextView) viewTwo.findViewById(R.id.textDoneMsg);

        //layout toggling : setting Scroll to foucus on top
        myScrollTwo= (ScrollView) viewTwo.findViewById(R.id.scrollFragTwo);


        if (mFirebaseUser == null) {
            doneParams.height = metrics.heightPixels;
            doneParams.width = metrics.widthPixels;
            doneLayout.setBackgroundColor(Color.parseColor("#F5F5F5"));
            doneLayout.setVisibility(View.VISIBLE);

            myScrollTwo.smoothScrollTo(0,0); //excellent solution to set focus on top of reappearing layout
            slipLayout.setVisibility(View.GONE);    //View.GONE --disappear element and vacant spcae too
            buttonSave.setVisibility(View.INVISIBLE);  //View.INVISIBLE --disappear element but keeps space occupied

            //display doneLayout with only text ; buttons gone
            textViewDoneMsg.setText(getResources().getString(R.string.textFragTwoDone));
            buttonDoneLeft.setVisibility(View.INVISIBLE);
            buttonDoneRight.setVisibility(View.INVISIBLE);

        }


        checkDeposit = (CheckBox) viewTwo.findViewById(R.id.checkDeposit);
        checkWithdrawal = (CheckBox) viewTwo.findViewById(R.id.checkWithdrawal);
        checkDD = (CheckBox) viewTwo.findViewById(R.id.checkDD);


        checkCur = (CheckBox) viewTwo.findViewById(R.id.checkCurrent);
        checkSav = (CheckBox) viewTwo.findViewById(R.id.checkSaving);



        progressDialog = new ProgressDialog(getActivity());  //attaching listener to button
        buttonSave.setOnClickListener(this);

        buttonDoneLeft.setOnClickListener(this);//


        //Slip Types

        checkDeposit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    checkWithdrawal.setChecked(false);
                    checkDD.setChecked(false);
                    mDatabaseRefDeposit = mFirebaseDatabase.getReference("addDeposit");
                }
            }
        });

        checkWithdrawal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    checkDeposit.setChecked(false);
                    checkDD.setChecked(false);
                    mDatabaseRefWithdrawal = mFirebaseDatabase.getReference("addWithdrawal");
                }
            }
        });

        checkDD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    checkDeposit.setChecked(false);
                    checkWithdrawal.setChecked(false);
                    mDatabaseRefDD = mFirebaseDatabase.getReference("addDD");
                }
            }
        });


        //Simplest way to Toggle CheckBox
        checkCur.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    checkSav.setChecked(false);
                    // Code to display your message.
                }
            }
        });


        checkSav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
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
        switch (v.getId()) {
            case R.id.btSave:
                userSlipValidate();
                break;
            case R.id.btDoneLeft:
                reappearSlipDisplay();
                    break;
        }
    }

    private void saveDepositSlip() {

        userSlipsModel localUser = new userSlipsModel();

        localUser.setAccName(mVarAccName);
        localUser.setAccNum(mVarAccNum);
        localUser.setAccType(mVarAccType);
        localUser.setDebitCardNum(mVarDebitCardNum);
        localUser.setPanNum(mVarPanNum);
        // localUser.setIfscCodeNum(mVarIfscCodeNum);
        //localUser.setBankName(mVarBankName);
        localUser.setBranchName(mVarBranchName);

        if ((((CheckBox) getView().findViewById(R.id.checkDeposit)).isChecked())) {

            mVarLocalUserId = mFirebaseUser.getUid();
            mDatabaseRefDeposit.child(mVarLocalUserId).push().setValue(localUser);
        } else if ((((CheckBox) getView().findViewById(R.id.checkWithdrawal)).isChecked())) {

            mVarLocalUserId = mFirebaseUser.getUid();
            mDatabaseRefWithdrawal.child(mVarLocalUserId).push().setValue(localUser);
        } else if ((((CheckBox) getView().findViewById(R.id.checkDD)).isChecked())) {

            mVarLocalUserId = mFirebaseUser.getUid();
            mDatabaseRefDD.child(mVarLocalUserId).push().setValue(localUser);
        }


        progressDialog.setMessage("Adding Slip Details.....");
        progressDialog.show();
        // handler thread used to delay dismis of Dialog by 2 Sec
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                progressDialog.dismiss();
            }
        }, 2000);

        //replace slipLayout with doneLayout
        disappearSlipDisplay();

    }


    private void disappearSlipDisplay(){

        doneParams.height = slipLayout.getHeight();
        doneParams.width = slipLayout.getWidth();
        doneLayout.setBackgroundColor(Color.parseColor("#F5F5F5"));

        myScrollTwo.smoothScrollTo(0,0); //excellent solution to set focus on top of reappearing layout
        slipLayout.setVisibility(View.GONE);    //View.GONE --disappear element and vacant spcae too
        buttonSave.setVisibility(View.INVISIBLE);  //View.INVISIBLE --disappear element but keeps space occupied

        //display doneLayout with two buttons
        doneLayout.setVisibility(View.VISIBLE);
        textViewDoneMsg.setText(getResources().getString(R.string.textFragTwoDoneSaved));

    }
    private void reappearSlipDisplay(){

        doneLayout.setVisibility(View.GONE);
        slipLayout.setVisibility(View.VISIBLE);
        buttonSave.setVisibility(View.VISIBLE);
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.checkCurrent:
                if (checked) {
                } else
                    break;
            case R.id.checkSaving:
                if (checked) {
                } else
                    break;
        }
    }

    private void userSlipValidate() {

        mVarAccName = editTextAccName.getText().toString().trim();
        mVarAccNum = editTextAccNum.getText().toString().trim();
        mVarDebitCardNum = editTextDebitCardNum.getText().toString().trim();
        mVarPanNum = editTextPanNum.getText().toString().trim();
        // mVarIfscCodeNum = editTextIfscCodeNum.getText().toString().trim();
        // mVarBankName = editTextBankName.getText().toString().trim();
        mVarBranchName = editTextBranchName.getText().toString().trim();


        if ((((CheckBox) getView().findViewById(R.id.checkCurrent)).isChecked())) {
            mVarAccType = "Current";
        } else {
            mVarAccType = "Saving";
        }
        if ((!(((CheckBox) getView().findViewById(R.id.checkCurrent)).isChecked())) && (!(((CheckBox) getView().findViewById(R.id.checkSaving)).isChecked()))) {
            Toast.makeText(getActivity(), "Please Select Account Type", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(mVarAccName)) {
            Toast.makeText(getActivity(), "Please Enter Name", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(mVarAccNum)) {
            Toast.makeText(getActivity(), "Please Enter Account Number", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(mVarBranchName)) {
            Toast.makeText(getActivity(), "Please Enter Branch Name", Toast.LENGTH_LONG).show();
            return;
        }
        if ((!(((CheckBox) getView().findViewById(R.id.checkDeposit)).isChecked())) && (!(((CheckBox) getView().findViewById(R.id.checkWithdrawal)).isChecked())) && (!(((CheckBox) getView().findViewById(R.id.checkDD)).isChecked()))) {
            Toast.makeText(getActivity(), "Please Select Slip Type", Toast.LENGTH_LONG).show();
            return;
        }

        saveDepositSlip();

/** PAN Number is Optional in many case
 if(TextUtils.isEmpty(mVarPanNum)){
 Toast.makeText(getActivity(),"Please Enter Pan Number",Toast.LENGTH_LONG).show();
 return;
 }
 */
/**Optinal  is Optional in many case
 if(TextUtils.isEmpty(mVarDebitCardNum)){
 Toast.makeText(getActivity(),"Please Enter Debit Card Optional",Toast.LENGTH_LONG).show();
 return;
 }
 */
    }
}








