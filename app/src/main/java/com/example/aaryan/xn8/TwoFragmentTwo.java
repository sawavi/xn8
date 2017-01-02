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
 * {@link TwoFragmentTwo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TwoFragmentTwo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TwoFragmentTwo extends Fragment {

    /**
     * soft keyboard was overlapping fragment edittext * additionaly android:paddingbottom:"50dp" was hiding exta 50dp space whenever softkeyboard poped up
     * removed android:paddingBottom="50dp"  and added android:windowSoftInputMode="stateHidden|adjustPan" in AndroidMenifest.xml
     */

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
        return inflater.inflate(R.layout.fragment_two_fragment_two, container, false);
    }

    // TODO:

}
