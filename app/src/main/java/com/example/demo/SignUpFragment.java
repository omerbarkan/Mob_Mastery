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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import java.util.ArrayList;


public class SignUpFragment extends Fragment implements View.OnClickListener {

    TextView tvError;
    EditText etEmail, etPassword, etUsername;
    Button btSign;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        etUsername = view.findViewById(R.id.etUsername);
        btSign = view.findViewById(R.id.btSignUp);
        tvError = view.findViewById(R.id.tvErrorSignUp);

        btSign.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v == btSign)
        {
            if(etEmail.length() > 0 && etPassword.length() > 0 && etUsername.length() > 0)
            {
                DBManager.getAuth().createUserWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d("User Auth", "User signed in Successfully");
                                    Toast.makeText(getContext(), "Successful sign-up", Toast.LENGTH_SHORT).show();
                                    Player player = new Player(etUsername.getText().toString(), DBManager.getAuth().getCurrentUser().getEmail());
                                    DataManager.addNewPlayer(player);
                                    DataManager.pullPlayers();

                                    ArrayList<Player> players = DataManager.getPlayers();
                                    for(int i = 0; i < players.size(); i++)
                                    {
                                        if(players.get(i).getEmail().toString().equals(etEmail.getText().toString()) )
                                        {
                                            DataManager.currentPlayer = i;
                                        }
                                    }

                                    Intent intent = new Intent(getContext(), MainGame.class);
                                    startActivity(intent);


                                }
                                else
                                {

                                    Toast.makeText(getContext(), ((FirebaseAuthException) task.getException()).getErrorCode(), Toast.LENGTH_SHORT).show();
                                    Log.d("User Exception", task.getException().getClass().getName());

                                    if (task.getException() instanceof FirebaseAuthUserCollisionException)
                                    {
                                        tvError.setText("This user already exist. Please login in or change username.");
                                    }
                                    else if (task.getException() instanceof FirebaseAuthWeakPasswordException)
                                    {
                                        tvError.setText("The password is too short. password have to be at least 6 characters.");
                                    }
                                    else if(task.getException() instanceof FirebaseAuthEmailException)
                                    {
                                        tvError.setText("The email is wrong. Please make sure the email is okay.");
                                    }
                                    else if(task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                                    {
                                        tvError.setText("The email is wrong. Please make sure the email is okay.");
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