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
import android.widget.EditText;
import android.widget.LinearLayout;
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
 * {@link TwoFragmentThree.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TwoFragmentThree#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TwoFragmentThree extends Fragment implements View.OnClickListener {

    private CheckBox checkSlip1;

    private EditText editTextIfscCodeNum;

    private String mVarIfscCodeNum;
    private String mVarSlip1;

    //Layout Manipulation
    private RelativeLayout wrapLayout;
    private RelativeLayout chooseBankLayout;
    private RelativeLayout doneLayout;
    private ViewGroup.LayoutParams doneParams;
    private ScrollView myScrollThree;
    private Button buttonDoneLeft;
    private Button buttonDoneRight;
    private TextView textViewDoneMsg;
    private DisplayMetrics metrics;



    private String mVarLocalUserId;  //private String UserID;

    private Button buttonSave;

    private ProgressDialog progressDialog;


    //defining firebaseauth object
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mFirebaseAuth;



    public TwoFragmentThree() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewThree = inflater.inflate(R.layout.fragment_two_fragment_three, container, false);


        metrics = getActivity().getResources().getDisplayMetrics();
        //TODO: look into mFirebaseAuth and firebaseUser later
        mFirebaseAuth = FirebaseAuth.getInstance();          //initializing firebase auth object
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference  = mFirebaseDatabase.getReference("addToken");  // get reference to 'users' node


        editTextIfscCodeNum= (EditText) viewThree.findViewById(R.id.fieldIFSCCode);
        checkSlip1 = (CheckBox) viewThree.findViewById(R.id.checkDeposit);

        buttonSave = (Button) viewThree.findViewById(R.id.btSave);


        //layout toggling
        wrapLayout = (RelativeLayout) viewThree.findViewById(R.id.wrapLayout);
        chooseBankLayout = (RelativeLayout) viewThree.findViewById(R.id.chooseBankLayout);
        doneLayout = (RelativeLayout) viewThree.findViewById(R.id.doneLayout);
        doneParams = doneLayout.getLayoutParams();

        buttonDoneLeft = (Button) viewThree.findViewById(R.id.btDoneLeft);
        buttonDoneRight = (Button) viewThree.findViewById(R.id.btDoneRight);
        textViewDoneMsg = (TextView) viewThree.findViewById(R.id.textDoneMsg);

        //layout toggling : setting Scroll to foucus on top
        myScrollThree = (ScrollView) viewThree.findViewById(R.id.scrollFragThree);

        if (mFirebaseUser == null) {
            doneParams.height = metrics.heightPixels;
            doneParams.width = metrics.widthPixels;
            doneLayout.setBackgroundColor(Color.parseColor("#F5F5F5"));
            doneLayout.setVisibility(View.VISIBLE);

            myScrollThree.smoothScrollTo(0,0); //excellent solution to set focus on top of reappearing layout
            wrapLayout.setVisibility(View.GONE);
            chooseBankLayout.setVisibility(View.GONE);    //View.GONE --disappear element and vacant spcae too
            buttonSave.setVisibility(View.INVISIBLE);  //View.INVISIBLE --disappear element but keeps space occupied

            //display doneLayout with only text ; buttons gone
            textViewDoneMsg.setText(getResources().getString(R.string.textFragThreeDone));
            buttonDoneLeft.setVisibility(View.INVISIBLE);
            buttonDoneRight.setVisibility(View.INVISIBLE);
        }
        //else DoneLayout-Gone is not need as its height=1dp is set in .xml
        // doneLayout.setVisibility(View.GONE);

        progressDialog = new ProgressDialog(getActivity());  //attaching listener to button
        buttonSave.setOnClickListener(this);  //

        buttonDoneLeft.setOnClickListener(this);//






        return viewThree; //Ending OnCreateView()
    }//Ending OnCreateView()


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btSave:
                userApplyTokenValidate();
                break;
            case R.id.btDoneLeft:
                reappearApplyTokenDisplay();
                break;

        }
        }


    private void userApplyTokenValidate(){

        mVarIfscCodeNum = editTextIfscCodeNum.getText().toString().trim();

        if (    (((CheckBox) getView().findViewById(R.id.checkDeposit)).isChecked())  ) {
            //setting value   //??check out is it setting in database too??
            mVarSlip1 = "SlipXYZ";
        }

        if(TextUtils.isEmpty(mVarIfscCodeNum)){
            Toast.makeText(getActivity(),"Please Enter IFSC Code Number",Toast.LENGTH_LONG).show();
            return;
        }

        userApplyTokenSave();
    }


    private void userApplyTokenSave(){

        /**
        userTokenModel localUser =new userTokenModel();


        localUser.setIfscCodeNum(mVarIfscCodeNum);
        localUser.setBankName(mVarSlip1);

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
**/
        disappearApplyTokenDisplay();

    }


    private void disappearApplyTokenDisplay(){

        doneParams.height = wrapLayout.getHeight();
        doneParams.width = wrapLayout.getWidth();
        doneLayout.setBackgroundColor(Color.parseColor("#F5F5F5"));

        myScrollThree.smoothScrollTo(0,0); //excellent solution to set focus on top of reappearing layout

        chooseBankLayout.setVisibility(View.GONE);
        wrapLayout.setVisibility(View.GONE);    //View.GONE --disappear element and vacant spcae too
        buttonSave.setVisibility(View.INVISIBLE);  //View.INVISIBLE --disappear element but keeps space occupied

        //display doneLayout with two buttons
        doneLayout.setVisibility(View.VISIBLE);
        textViewDoneMsg.setText(getResources().getString(R.string.textFragThreeDoneSaved));

    }




    private void reappearApplyTokenDisplay(){

        doneLayout.setVisibility(View.GONE);
        chooseBankLayout.setVisibility(View.VISIBLE);
        wrapLayout.setVisibility(View.VISIBLE);
        buttonSave.setVisibility(View.VISIBLE);
    }

}//End
