package com.example.administrationkarnal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class HomeScreen extends AppCompatActivity
{
    TabLayout layout;
    ViewPager pager;
    adapterViewPager adapterViewpager;
    Toolbar toolbar;
    TextView name;
    FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        layout=findViewById(R.id.tabayout);
        name=findViewById(R.id.title);
        name.setText(JournalApi.getInstance().getName());
        toolbar=findViewById(R.id.toolbarLayout);
        setSupportActionBar(toolbar);
        mauth=FirebaseAuth.getInstance();
        JournalApi api=JournalApi.getInstance();
        Log.d("current_user",""+api.getName()+"/"+api.getDepartment()+"/"+api.getEmail());
     //   Log.d("current_user",""+mauth.getCurrentUser().getEmail());

        pager=findViewById(R.id.viewpager);
        layout.setupWithViewPager(pager);


        adapterViewpager=new adapterViewPager(getSupportFragmentManager());
        layout.setupWithViewPager(pager);
        pager.setAdapter(adapterViewpager);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id=item.getItemId();
        switch (id)
        {
            case R.id.logout:
            {
                if (mauth.getCurrentUser()!=null)
                {
                    mauth.signOut();
                    startActivity(new Intent(HomeScreen.this,splashScreen.class));
                    finish();
                }
            }
        }
        return super.onOptionsItemSelected(item);

    }
}
