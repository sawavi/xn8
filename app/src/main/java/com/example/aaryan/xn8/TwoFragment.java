package com.example.aaryan.xn8;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class TwoFragment extends Fragment {

    private View view;
    private ViewPager viewPagerTwo;
    private TabLayout tabLayout;
    private TwoFragment.ViewPagerAdapter adapter;

    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //moved code from setupViewPager()
        adapter = new TwoFragment.ViewPagerAdapter(getFragmentManager());
        adapter.addFragment(new TwoFragmentOne(), "Add Bank Accounts");
        adapter.addFragment(new TwoFragmentTwo(), "1.Fill In Slips");
        adapter.addFragment(new TwoFragmentThree(), "2.Choose Bank");
        adapter.addFragment(new TwoFragmentFour(), "3.Get Tokens");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_two, container, false);

        viewPagerTwo = (ViewPager) view.findViewById(R.id.viewpagerFragTwo);
        //sub-fragments view was loading very slowly, increasing limit solved this, i guess
        //viewPagerTwo.setOffscreenPageLimit(5); preloads fragment views , by default its set to one
        viewPagerTwo.setOffscreenPageLimit(5);
        setupViewPager(viewPagerTwo);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs2);
        tabLayout.setupWithViewPager(viewPagerTwo);


        viewPagerTwo.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            // optional
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            // optional
            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                        Toast.makeText(getActivity(), "tab0 ", Toast.LENGTH_LONG).show();

                        break;
                    case 1:
                        Toast.makeText(getActivity(), "tab1 ", Toast.LENGTH_LONG).show();
                        break;
                    case 2:
//TODO: TwoFragmentThree.getUserSlipsToDisplay(); was made into public static , just to use it here on tabs selection
                        //also mVarLocalUserId in TwoFragmentThree made into static .....if any issues think about again....so far works OK
                        //below method is supposed to load list of updated saved slips automatically on tab selection
                     //   TwoFragmentThree.clearListView();
                        TwoFragmentThree.setMySpinnerSelection();
                        Toast.makeText(getActivity(), "tab2 ", Toast.LENGTH_LONG).show();
                        break;
                    case 3:
                        Toast.makeText(getActivity(), "tab3 ", Toast.LENGTH_LONG).show();
                        break;
                }
            }

            // optional
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        //simply manipulating tabs;   have set style
//        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_blue);

        return view;
     /*        basicClass baseClass =new basicClass();
        if (baseClass.isUserLogedIn() == false) {
            // Not signed in, launch the Sign In activity : Correct method ;)
            //startActivity(new Intent(this.getActivity(), sendRegistrationActivity.class));
            //  return v;
            view = inflater.inflate(R.layout.fragment_five, container, false);
            Toast.makeText(this.getActivity(),"if user not signed in",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.getActivity(),"Else user signed in",Toast.LENGTH_LONG).show();
        }
        // Inflate the layout for this fragment*/
    }


    //  method used by tabs
    private void setupViewPager(ViewPager viewPager) {
        //IMP: unlike Activity;in fragment use below code ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //
    /*  Simply moved below code to OnCreate(); and made adapter as global variable ; lets see if it loads fragments faster or not

        TwoFragment.ViewPagerAdapter adapter = new TwoFragment.ViewPagerAdapter(getFragmentManager());
        adapter.addFragment(new TwoFragmentOne(), "Add Bank Details");
        adapter.addFragment(new TwoFragmentTwo(), "Deposit");
        adapter.addFragment(new TwoFragmentThree(), "Withdrawal");
        adapter.addFragment(new TwoFragmentFour(), "DD");
    */
        viewPager.setAdapter(adapter);
    }

    //  method used by tabs
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

/**
 @Override public void passData(String data) {
 TwoFragmentThree fragmentB = new TwoFragmentThree ();
 Bundle args = new Bundle();
 args.putString(TwoFragmentThree.DATA_RECEIVE, data);
 fragmentB .setArguments(args);
 getFragmentManager().beginTransaction()
 .replace(R.id.container, fragmentB )
 .commit();
 }

 **/

}
