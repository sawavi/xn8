package com.example.aaryan.xn8;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FiveFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FiveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FiveFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FiveFragment() {
        // Required empty public constructor



        // TODO: working example of fragment inserting data to firebase

/**
 * public class TwoFragmentOne extends Fragment implements View.OnClickListener {

 //defining view objects
 private EditText editTextTextAccNum;
 ///
 private EditText editTextYourIdIs;
 private EditText editTextFirstName;
 private EditText editTextLastName;
 private EditText editTextMobNum;
 private EditText editTextEmail;
 private EditText editTextPanNum;
 private EditText editTextAadhaarNum;


 private String mVarAccNum;
 /////

 private String mVarLocalUserId;  //private String UserID;
 private String accType;
 private String debitCardNum;

 private String ifscCodeNum;
 private String bankName;
 private String branchName;

 private boolean mVarCardToggle;


 private Button buttonSave;
 private ProgressDialog progressDialog;


 //defining firebaseauth object

 private String authExcep;


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


 editTextTextAccNum = (EditText) viewTwo.findViewById(R.id.fieldAccNum);

 buttonSave = (Button) viewTwo.findViewById(R.id.btSave);

 progressDialog = new ProgressDialog(getActivity());  //attaching listener to button
 buttonSave.setOnClickListener(this);  //



 return viewTwo;
 }

 // TODO:


 @Override
 public void onClick(View v) {

 switch(v.getId()){
 case R.id.btSave:
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

 //  DatabaseReference childRef = mDatabaseReference;
 //   childRef.child(mVarLocalUserId).setValue(localUser);


 if (mFirebaseUser == null) {
 Toast.makeText(getActivity(),"You Are Not Logged In",Toast.LENGTH_LONG).show();

 } else {
 mVarLocalUserId = mFirebaseUser.getUid();
 mDatabaseReference.child(mVarLocalUserId).push().setValue(localUser);
 buttonSave.setText("Add One More");
 }

 }

 private void userAddAccValidate(){

 mVarAccNum = editTextTextAccNum.getText().toString().trim();

 if(TextUtils.isEmpty(mVarAccNum)){
 Toast.makeText(getActivity(),"Please Enter Account Number",Toast.LENGTH_LONG).show();
 return;
 }

 saveUserAddAccData();
 }
 }

 */


























    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FiveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FiveFragment newInstance(String param1, String param2) {
        FiveFragment fragment = new FiveFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_five, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
