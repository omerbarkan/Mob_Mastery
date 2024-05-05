package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Account extends AppCompatActivity implements View.OnClickListener {

    Button btnSignFragment, btnLogFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Intent intent = getIntent();
        String frag = intent.getExtras().getString("Fragment");


        btnLogFragment = findViewById(R.id.btLogFragment);
        btnSignFragment = findViewById(R.id.btSignFragment);
        btnLogFragment.setOnClickListener(this);
        btnSignFragment.setOnClickListener(this);

        if(frag.equals("Sign"))
        {
            replaceFragment(new SignUpFragment());
        }
        else if(frag.equals("Log"))
        {
            replaceFragment(new LogInFragment());
        }
    }


    @Override
    public void onBackPressed() {
    }

    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        if(v == btnLogFragment)
        {
            replaceFragment(new LogInFragment());
        }
        if(v == btnSignFragment)
        {
            replaceFragment(new SignUpFragment());
        }
    }
}