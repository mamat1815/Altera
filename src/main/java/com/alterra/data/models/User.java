package com.alterra.data.models;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class User {
    private String id;
    private int role;
    private String name; 
    private String username;
    private String email;
    private String password;
    private Vote vote ;

    public User() {

    }

    public User(String id, int role, String name, String username, String email, String password, Vote vote) {
        this.id = id;
        this.role = role;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.vote = vote;
    }

    public String getEmail() {
        return email;
    }



    public void setEmail(String email) {
        this.email = email;
    }

    public String  getId() {
        return id;
    }

    public int getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Vote getVote() {
        return vote;
    }

    public static void signIn(String email, String password, LoginCallback loginCallback) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
        usersRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        User user = dataSnapshot.getValue(User.class);
                        if (user != null && password.equals(user.getPassword())) {
                            loginCallback.onSuccess(user);
                            return;
                        }
                    }
                }
                loginCallback.onFailure("Invalid email or password.");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                loginCallback.onFailure("Login Failed: " + error.getMessage());
            }
        });
    }

    public interface LoginCallback {
        void onSuccess(User user);
        void onFailure(String eMessage);
    }

}

