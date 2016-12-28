package com.example.aaryan.xn8;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class OneFragment extends Fragment {

    public static final String ANONYMOUS = "anonymous";

    private Button logBut;
    private Button regBut;

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
                // 1 Unlike Activity in fragment create new activity by intent like below
                // 2 use getActivity() method  ; not like usual way
                // view v will be returned
        View v = inflater.inflate(R.layout.fragment_one, container, false);


        regBut = (Button)v.findViewById(R.id.btReg);
        logBut = (Button)v.findViewById(R.id.btLog);

        basicClass baseClass =new basicClass();

        if (baseClass.isUserLogedIn() == false) {
                    // Not signed in, launch the Sign In activity : Correct method ;)
                    // startActivity(new Intent(this.getActivity(), sendRegistrationActivity.class));
                    //  return v;
                    //// regBut.setFocusableInTouchMode(true);
                    ////Toast.makeText(this.getActivity(),"if user not signed in",Toast.LENGTH_LONG).show();

        } else {

            logBut.setVisibility(View.GONE);
            regBut.setVisibility(View.GONE);
            Toast.makeText(this.getActivity(),"Else user signed in",Toast.LENGTH_LONG).show();
        }

        regBut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), sendRegistrationActivity.class);
                startActivity(intent);
            }
        });


        logBut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), sendLoginActivity.class);
                startActivity(intent);
            }
        });

        return v ;
    }
                /// IMP : to start New Activity from Fragment you need to use getActivity() method  ; not like usual way
                // public void sendRegistration(View view) {
                //     Intent intent = new Intent(getActivity(), sendRegistrationActivity.class);  //invokes another

                //     startActivity(intent); //   finish intent and start new activity
                // }
}
