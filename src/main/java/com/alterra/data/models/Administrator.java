package com.alterra.data.models;

import com.alterra.utility.AlertUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Administrator extends User{

    private static FirebaseAuth auth = FirebaseAuth.getInstance();
    private static DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("users");

    public Administrator() {

    }
    public Administrator(String id, int role, String name, String username, String email, String password, Vote vote) {
        super(id, role, name, username, email, password, vote);
    }

    public static void createUser(String role, String name, String email, String password, String username) {
        Integer roleInt = 0;
        switch (role) {
            case "Administrator":
                roleInt = 1;
                break;
            case "Supervisor":
                roleInt = 2;
                break;
            case "Surveyor":
                roleInt = 3;
                break;
            default:
                roleInt = 0;
                break;
        }

        try {
            CreateRequest request = new CreateRequest()
                .setEmail(email)
                .setEmailVerified(false)
                .setPassword(password)
                .setDisplayName(name)
                .setDisabled(false);

            UserRecord userRecord = auth.createUser(request);
            User user = new User(userRecord.getUid(), roleInt, name, username, email, password, new Vote());
            dbRef.child(userRecord.getUid()).setValue(user, (error, ref) -> {
                if (error != null) {
                    System.err.println("Data could not be saved: " + error.getMessage());
                    AlertUtil.showAlert("Data could not be saved: ", error.getMessage());
                    error.toException().printStackTrace();
                } else {
                    System.out.println("Data saved successfully.");
                }
            });
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            System.out.println("Error creating user: " + e.getMessage());
        }
    }
}
