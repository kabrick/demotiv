package com.kabricks.ster.demotiv;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.kabricks.ster.demotiv.lists.ListActivity;

public class Dashboard extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_dashboard:
                    Toast.makeText(Dashboard.this, "Home", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.action_lists:
                    Intent newActivity = new Intent(getApplicationContext(), ListActivity.class);
                    startActivity(newActivity);
                case R.id.action_options:
                    Toast.makeText(Dashboard.this, "More Options", Toast.LENGTH_LONG).show();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_dashboard);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        MyDBHelper dbHelper = new MyDBHelper(this);
        dbHelper.createInit();
    }

}
