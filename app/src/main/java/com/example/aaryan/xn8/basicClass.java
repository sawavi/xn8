package com.example.aaryan.xn8;

/**
 * Created by aaryan on 12/15/2016.
 */


import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class basicClass {

//    public static String mDBDepositSlip = "addDeposit";
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;




    public boolean isUserLogedIn(){


        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            // Not signed in
          return false;

        } else {

           return true;
        }

    }

}
