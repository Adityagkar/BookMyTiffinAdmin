package com.example.bookmytiffinadmin;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

//import com.example.bookmytiffinadmin.dummy.DummyContent;
import com.example.bookmytiffinadmin.dummy.UserData;

public class MainActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener , UpdateTiffin.OnFragmentInteractionListener,ChangePlans.OnFragmentInteractionListener{

    TextView textView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    textView.setVisibility(View.INVISIBLE);
                    fragment = new ItemFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_dashboard:
                    textView.setVisibility(View.INVISIBLE);
                    fragment = new ChangePlans();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_notifications:
                    textView.setVisibility(View.INVISIBLE);
                    fragment = new UpdateTiffin();
                    loadFragment(fragment);

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       textView = findViewById(R.id.textViewBye);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onListFragmentInteraction(UserData item) {

    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
