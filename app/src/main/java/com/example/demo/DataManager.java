package com.example.demo;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DataManager {

    public static int currentPlayer = -1;
    private static final String dbMainList = "players";
    private static ArrayList<Player> players;

    public static void pullPlayers()
    {
        DBManager.getDb().getReference(dbMainList).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<ArrayList<Player>> t = new GenericTypeIndicator<ArrayList<Player>>() {
                };
                players = snapshot.getValue(t);
                if(players == null)
                {
                    players = new ArrayList<Player>();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public static void addNewPlayer(Player player)
    {
        getPlayers().add(player);
        DBManager.getDb().getReference(dbMainList).setValue(players);
    }

    public static ArrayList<Player> getPlayers()
    {
        if(players == null)
        {
            players = new ArrayList<Player>();
        }
        return players;
    }
}

