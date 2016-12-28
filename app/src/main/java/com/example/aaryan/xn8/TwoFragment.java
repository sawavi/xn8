package com.example.aaryan.xn8;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class TwoFragment extends Fragment {

    private View v;


    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        basicClass baseClass =new basicClass();

        if (baseClass.isUserLogedIn() == false) {
            // Not signed in, launch the Sign In activity : Correct method ;)
            //startActivity(new Intent(this.getActivity(), sendRegistrationActivity.class));
            //  return v;
            v = inflater.inflate(R.layout.fragment_five, container, false);
            Toast.makeText(this.getActivity(),"if user not signed in",Toast.LENGTH_LONG).show();

        } else {

            Toast.makeText(this.getActivity(),"Else user signed in",Toast.LENGTH_LONG).show();
        }

        // Inflate the layout for this fragment
        return v;
    }
}
