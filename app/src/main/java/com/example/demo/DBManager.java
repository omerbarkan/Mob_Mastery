package com.example.demo;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DBManager {
    private static FirebaseAuth auth;

    private static FirebaseDatabase db;
    public static FirebaseAuth getAuth()
    {
        if(auth == null)
            auth = FirebaseAuth.getInstance();
        return auth;
    }

    public static FirebaseDatabase getDb()
    {
        if(db == null)
            db = FirebaseDatabase.getInstance("https://mobmastery-b14d9-default-rtdb.europe-west1.firebasedatabase.app/");
        return db;
    }

    public static DatabaseReference getMainRoot()
    {
        return getDb().getReference("players");
    }
}
