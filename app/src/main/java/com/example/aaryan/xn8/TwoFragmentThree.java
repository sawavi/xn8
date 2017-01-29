package com.example.aaryan.xn8;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.aaryan.xn8.TwoFragmentTwo.MyPREFERENCES;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TwoFragmentThree.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TwoFragmentThree#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TwoFragmentThree extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {


    private Context context;

    //SharedPreferences
    private static SharedPreferences tmepSharedPref;


    //local fields
    private EditText editTextIfscCodeNum;

    private String mVarIfscCodeNum;


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


    private Button buttonApply;
    private ProgressDialog progressDialog;


    //defining firebaseauth object
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDBApplyTokenRef;
    private DatabaseReference mDBReadSlipsRef;

    //listView slips
    private String varStringSlipText;
    private int varNumOfRows;
    private int varCharLimit;
    private ListView slipListView;
    private ArrayAdapter<String> slipsAdapter;
    private ArrayList<String> slipsListArray = new ArrayList<String>();

    //spinner
    private List<String> spinListArray;
    private static Spinner spinner;


    //++1 unassigned Adapter in onCreateView() creating null point exception randomly; here assigned it to fake listArray
    //private  ArrayAdapter<String> slipsAdapter;

    //+1 DONT USE Var in OnCeateView(); using variable to get sharedPref values and use it ...........creating mess
    //    private  int tempInt;

    //set size of listview depending on number of rows ??? hardcoded in layout


    public TwoFragmentThree() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ////////////////variables////////////////////////
        varNumOfRows = 3;
        varCharLimit = 6;

        View viewThree = inflater.inflate(R.layout.fragment_two_fragment_three, container, false);


        editTextIfscCodeNum = (EditText) viewThree.findViewById(R.id.fieldIFSCCode);
        buttonApply = (Button) viewThree.findViewById(R.id.btApplyToken);


        ///////////////////firebase DB///////////////////////////////////////////////
        mFirebaseAuth = FirebaseAuth.getInstance();          //initializing firebase auth object
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        mDBApplyTokenRef = mFirebaseDatabase.getReference("addToken");  // get reference to 'users' node

        //////////////////////////////SharedPref//////////////////////////////////////
        tmepSharedPref = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        //[-]  tempVarSharedPref = (tmepSharedPref.getInt("keySlipType", 0));

        if (mFirebaseUser != null) {
            mVarLocalUserId = mFirebaseUser.getUid();

        }

        ///////listView///////////////////////////height=140dp is fixed in layout ;)////////////////////

        context = getActivity();
        slipListView = (ListView) viewThree.findViewById(R.id.listView1);

        /**
         System.out.println("______oncreateView Var: listArray____" + slipsListArray);
         **/

        //slipsAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item, spinListArray);
        //++1 unassigned Adapter in onCreateView() creating null point exception randomly; here assigned it to slipsListArray
        //which is again being assigned to right listView in writeToSlipAdapter()
        slipsAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item, slipsListArray);

        slipListView.setAdapter(slipsAdapter);

        slipListView.setItemsCanFocus(false);

        slipListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);  // we want multiple clicks

        slipListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                CheckedTextView ctv = (CheckedTextView) arg1;
                if (ctv.isChecked()) {
                    Toast.makeText(getActivity(), "now it is checked" + ctv.getText(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "now it is unchecked" + ctv.getText(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        ///////////////// Spinner element//////////////////////////////////////////////////////////////////////////////

        spinner = (Spinner) viewThree.findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        spinListArray = new ArrayList<String>();
        spinListArray.add("Choose Saved Slips");
        spinListArray.add("Deposit Slips");
        spinListArray.add("Withdrawal Slips");
        spinListArray.add("DD Slips");

        // Creating slipsAdapter for spinner

        ArrayAdapter<String> spinnerDataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spinListArray);

        // Drop down layout style - list view with radio button
        spinnerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data slipsAdapter to spinner
        spinner.setAdapter(spinnerDataAdapter);
        spinner.setSelection(0);


        //////////////////////////////////////////layout toggling///////////////////////////////////

        metrics = getActivity().getResources().getDisplayMetrics(); //Toggling layouts :geting values

        wrapLayout = (RelativeLayout) viewThree.findViewById(R.id.slipWraperLayout);
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

            myScrollThree.smoothScrollTo(0, 0); //excellent solution to set focus on top of reappearing layout
            wrapLayout.setVisibility(View.GONE);
            chooseBankLayout.setVisibility(View.GONE);    //View.GONE --disappear element and vacant spcae too
            buttonApply.setVisibility(View.INVISIBLE);  //View.INVISIBLE --disappear element but keeps space occupied

            //display doneLayout with only text ; buttons gone
            textViewDoneMsg.setText(getResources().getString(R.string.textFragThreeDone));
            buttonDoneLeft.setVisibility(View.INVISIBLE);
            buttonDoneRight.setVisibility(View.INVISIBLE);
        }

        //+1 else DoneLayout-Gone is not need as its height=1dp is set in .xml
        // doneLayout.setVisibility(View.GONE);
        //////////////////////////////////////////////////////////////////////////////////////


        progressDialog = new ProgressDialog(getActivity());  //attaching listener to button


        buttonApply.setOnClickListener(this);  //
        buttonDoneLeft.setOnClickListener(this);////back button


        return viewThree; //Ending OnCreateView()

    }//Ending OnCreateView()


    ///////////////////set spinner based on last saved list///////////////////////////////////////////////////////////

    protected static void setMySpinnerSelection() {
        //TODO: this methods is being called from FragmentTwo, parent fragment on tab switch  //rethink before changing
        //+1 the method needs to be public static and variable to , so that it can be called from parent fragments or Activity
        //////////////////using SharedPreferance data to get lastSavedSlip type

        spinner.setSelection((tmepSharedPref.getInt("keySlipType", 0)));

    }


    /////////spinner events//////////////////////////////////////////////////////
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        //error null object reference
        //((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#038ad4"));
        switch (position) {
            case 0:
                Toast.makeText(getActivity(), "spin0 " + item, Toast.LENGTH_LONG).show();
                clearListView();
                break;
            case 1:
                mDBReadSlipsRef = FirebaseDatabase.getInstance().getReference("addDeposit").child(mVarLocalUserId);
                varStringSlipText = "Deposit Slip : ";
                populateSlipsToDisplay();
                Toast.makeText(getActivity(), "spin1 " + item, Toast.LENGTH_LONG).show();

                break;
            case 2:
                mDBReadSlipsRef = FirebaseDatabase.getInstance().getReference("addWithdrawal").child(mVarLocalUserId);
                varStringSlipText = "Withdrawal Slip : ";
                populateSlipsToDisplay();
                Toast.makeText(getActivity(), "spin2 " + item, Toast.LENGTH_LONG).show();
                break;
            case 3:
                mDBReadSlipsRef = FirebaseDatabase.getInstance().getReference("addDD").child(mVarLocalUserId);
                varStringSlipText = "DD Slip : ";
                populateSlipsToDisplay();
                Toast.makeText(getActivity(), "spin3 " + item, Toast.LENGTH_LONG).show();
                break;
        }
        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


    ////////////////buttons events///////////////////////////////////////////////////////////////////////
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btApplyToken:
                applyTokenValidate();
                break;
            case R.id.btDoneLeft:
                reappearApplyTokenDisplay();
                break;
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    private void populateSlipsToDisplay() {

        //its not needed here as a public static
        //if (mFirebaseUser != null) {
        //    mVarLocalUserId = mFirebaseUser.getUid();


        //+1 pointing mDBReadSlipsRef upto UID, and then fetching by dataSnapshot.getChildren() gets object
        //+1//which by using postSnapshot.child("accName").getValue()  gives values of objects
        //+1//postSnapshot.getKey() >>>>>>>returns pushID
        //+1//mDBReadSlipsRef.limitToLast(1)  >>>>>>>limits records to value int
        //+1//DataSnapshot >>>>> always return last entered data i.e. latest data......
        //+1// Timestamping???? ....if needed use android time , insert it into field and sort by it and get the records by dataSnapshot

        ////iterates limitToLast(2)* number of childerns i.e.  twice ..  limit 2 * child 2 .......look for iterator later

        //+1 clears prviously added data
        slipsListArray.clear();

        mDBReadSlipsRef.limitToLast(varNumOfRows).addValueEventListener(new ValueEventListener() {
            //+1 Refreshing ListView .....listView was retaining last displayed records and appending below new one
            //????+1 simply making slipsListArray local variable than global , automatically refreshing slipListView ;)

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    //iterates limitToLast(2)* number of childerns i.e.

                    /**    // //used i because for (DataSnapshot iterates as long as child it has  :twice ..  limit 2 * child 2
                     for (int i = 0; i < 1; i++) {
                     viewHolderSlipsArray[i] = postSnapshot.getValue(viewHolderSlips.class);
                     System.out.println("array value  " + i + "_________" + viewHolderSlipsArray[i].getAccName());
                     }

                     //after for loop postSnapshot returns with new child, so even value of remains to 0 , it returns next child ;)
                     //+1    viewHolderSlips vhSlips = postSnapshot.getValue(viewHolderSlips.class);
                     //+1     System.out.println("new 9   object" + "_________" + vhSlips.getAccName());

                     */
                    String name = (String) postSnapshot.child("accName").getValue();
                    String amount = (String) postSnapshot.child("totalAmount").getValue();

                    if (name.length() > varCharLimit) {
                        name = name.substring(0, varCharLimit);
                    }
                    //make format string, bolde deposit slip
                    slipsListArray.add(varStringSlipText + name + " / " + amount + " / " + "10:11");
                    //+1     System.out.println("new 9   object" + "_________" + vhSlips.getAccName());


                    writeToSlipAdapter();

                    System.out.println("______Var: listArray____" + slipsListArray);
                    System.out.println("______Var: name + amount____" + name + "__" + amount);
                    System.out.println("______postSnapshot.getValue()__" + postSnapshot.getValue());
                    System.out.println("______postSnapshot.getKey()__" + postSnapshot.getKey());
                    System.out.println("______dataSnapshot.getValue()__" + dataSnapshot.getValue());

                }


                System.out.println("______xxxxxxxxxxxxxxxxfrag 3 from listView int Value____" + tmepSharedPref.getInt("keySlipType", 0));

                //+1 slipsAdapter.clear();         // Error: results null point exception after working fine for 3-4 times
                //slipsAdapter.clear();
                // slipsAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item, slipsListArray);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });

    }

    public void writeToSlipAdapter() {


        if (slipsListArray.size() == 0) {
            slipsListArray.add("Nothing To Show: Add Slips First.  ");
        }

        slipsAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item, slipsListArray);
        slipListView.setAdapter(slipsAdapter);

        /**
         System.out.println("______uuuuuuuuuuuu method writeToPub Var: listArray____" + slipsListArray);
         */
    }

    /////////////////////////clearing listview needed as its appending results to previous one
    private void clearListView() {

        if (slipsAdapter != null) {
            //Error Solved : by assigning adapter in onCreateView();to random
            // Error:null point exception being raised on slipsAdapter.clear()
            slipsAdapter.clear();

        }
        //+1  Errors out null object reference  //  slipListView.setAdapter(null);
    }


    //////////////////////////////////////////field validation///////////////////////////////////////
    private void applyTokenValidate() {

        mVarIfscCodeNum = editTextIfscCodeNum.getText().toString().trim();

        if (TextUtils.isEmpty(mVarIfscCodeNum)) {
            Toast.makeText(getActivity(), "Please Enter IFSC Code Number", Toast.LENGTH_LONG).show();
            return;
        }
        applyTokenSave();
    }


    ////////////////////////// save token//////////////////////////////////////////////////////////////
    private void applyTokenSave() {

        userTokenModel localUser = new userTokenModel();


        localUser.setIfscCodeNum(mVarIfscCodeNum);
        //localUser.setBankName(mVarSlipID);


        //mVarLocalUserId = mFirebaseUser.getUid();     //value got in onCreateView()
        mDBApplyTokenRef.child(mVarLocalUserId).push().setValue(localUser);

        buttonApply.setText("Add More");

        progressDialog.setMessage("Adding Account Details..... ");
        progressDialog.show();
        // handler thread used to delay dismis of Dialog by 2 Sec
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                progressDialog.dismiss();
            }
        }, 2000);


        disappearApplyTokenDisplay();

    }


    private void disappearApplyTokenDisplay() {
        doneParams.height = wrapLayout.getHeight();
        doneParams.width = wrapLayout.getWidth();
        doneLayout.setBackgroundColor(Color.parseColor("#F5F5F5"));

        myScrollThree.smoothScrollTo(0, 0); //excellent solution to set focus on top of reappearing layout

        chooseBankLayout.setVisibility(View.GONE);
        wrapLayout.setVisibility(View.GONE);    //View.GONE --disappear element and vacant spcae too
        buttonApply.setVisibility(View.INVISIBLE);  //View.INVISIBLE --disappear element but keeps space occupied

        //display doneLayout with two buttons
        doneLayout.setVisibility(View.VISIBLE);
        textViewDoneMsg.setText(getResources().getString(R.string.textFragThreeDoneSaved));
    }

    private void reappearApplyTokenDisplay() {
        doneLayout.setVisibility(View.GONE);
        chooseBankLayout.setVisibility(View.VISIBLE);
        wrapLayout.setVisibility(View.VISIBLE);
        buttonApply.setVisibility(View.VISIBLE);
    }


}//End : class ending
