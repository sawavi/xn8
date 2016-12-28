package com.example.aaryan.xn8;

// this app is creating toolbar and tabs
// it basically uses Android Design Support Library - so the APP can back port all the way
// tutorial combined by following
//http://www.androidhive.info/2015/09/android-material-design-working-with-tabs/
//and
//http://www.androidhive.info/2015/04/android-getting-started-with-material-design/
//

import com.google.firebase.auth.FirebaseAuth;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private FirebaseAuth firebaseAuth;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        ActionBar newActionBar= getSupportActionBar();
        newActionBar.setLogo(R.mipmap.ic_launcher);
        newActionBar.setDisplayUseLogoEnabled(true);
        //it showing back arrow on main activity ,instead of logo
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        firebaseAuth = FirebaseAuth.getInstance();




        basicClass baseClass =new basicClass();

        if (baseClass.isUserLogedIn() == false) {

            Toast.makeText(this,"mainActivity: Pls do register yourself.",Toast.LENGTH_LONG).show();

        } else {


///IMP: load user data and profile here; if user signed in

            Toast.makeText(this ,"mainActivity: Else user signed in",Toast.LENGTH_LONG).show();
        }
    }


    //  method used by mToolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //  method used by mToolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


///IMP: add logic for : if users are not signed in
        /// its illogical to signout and update profile at first place ;)




        switch (item.getItemId()) {
            case R.id.menu_log_out:
                firebaseAuth.signOut();
                // mUsername = ANONYMOUS;
             //   startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.menu_profile_edit:
             //   startActivity(new Intent(this, editUserProfileActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //  method used by tabs
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "HOME");
        adapter.addFragment(new TwoFragment(), "eBank");
        adapter.addFragment(new ThreeFragment(), "eToken");
        adapter.addFragment(new FourFragment(), "ATMs");
     //   adapter.addFragment(new FiveFragment(), "Message");

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
}


///set tabs invisible for non-loged in user here in main activity
///use firebase to check loged in user here not in OneFragment
///GUESS.... viewpager will allow setting Tabs invisible here
///create new methods for firebase user logged in IF ELSE and set visible and invisible things here only