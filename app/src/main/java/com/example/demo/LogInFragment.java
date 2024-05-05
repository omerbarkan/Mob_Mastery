package com.example.demo;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import java.util.ArrayList;


public class LogInFragment extends Fragment implements View.OnClickListener {

    TextView tvError;
    EditText etEmail, etPassword;
    Button btLog;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_log_in, container, false);

        etEmail = view.findViewById(R.id.etEmailLogIn);
        etPassword = view.findViewById(R.id.etPasswordLogIn);
        btLog = view.findViewById(R.id.btLogIn);
        tvError = view.findViewById(R.id.tvErrorLogIn);

        btLog.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        if(v == btLog)
        {
            if(etEmail.length() > 0 && etPassword.length() > 0)
            {
                DBManager.getAuth().signInWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Log.d("User Auth", "User sign-in successfully");

                         ArrayList<Player> players = DataManager.getPlayers();
                            for(int i = 0; i < players.size(); i++)
                            {
                                if(players.get(i).getEmail().toString().equals(etEmail.getText().toString()) )
                                {
                                    DataManager.currentPlayer = i;
                                }
                            }

                            Intent intent = new Intent(requireContext(), MainGame.class);
                            startActivity(intent);


                        }
                        else
                        {
                            if (task.getException() instanceof FirebaseAuthInvalidUserException)
                            {
                                tvError.setText("user doesn't exist, one of the fields may be incorrect");
                            }
                        }
                    }
                });
            }
            else
            {
                tvError.setText("Please fill all fields.");
            }



        }
    }
}