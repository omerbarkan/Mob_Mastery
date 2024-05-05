package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvError;
    EditText etEmail, etPassword, etUsername;
    Button btSign, btLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataManager.pullPlayers();
        Intent intent = new Intent(this, DataChangesService.class);
        startService(intent);

        btSign = findViewById(R.id.btSign);
        btLog = findViewById(R.id.btLog);

        btSign.setOnClickListener(this);
        btLog.setOnClickListener(this);

    }

    @Override
    public void onBackPressed()
    {

    }

    @Override
    public void onClick(View v) {
        if(v == btLog)
        {
            Intent intent = new Intent(MainActivity.this, Account.class);
            intent.putExtra("Fragment", "Log");
            startActivity(intent);


        }

        if(v == btSign)
        {
            Intent intent = new Intent(MainActivity.this, Account.class);
            intent.putExtra("Fragment", "Sign");
            startActivity(intent);
        }
    }
}