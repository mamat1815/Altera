package com.alterra;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.alterra.data.models.Administrator;
import com.alterra.data.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AdminController implements Initializable{

    @FXML
    private ChoiceBox<String> cbRole;

    @FXML
    private TextField tfNama;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfPassword;

    @FXML
    private TextField tfUsername;

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    private DatabaseReference dbRef;
    private ArrayList<User> users;
    
    @SuppressWarnings("unused")
    private ValueEventListener  usersListener;

    @FXML
    private TableView<User> tbUser;

    @FXML
    private TableColumn<User, String> colNama;

    @FXML
    private TableColumn<User, String> colEmail;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        cbRole.getItems().add("Administrator");
        cbRole.getItems().add("Supervisor");
        cbRole.getItems().add("Surveyor");
        cbRole.getItems().add("User");
        dbRef = FirebaseDatabase.getInstance().getReference("users");

        users = new ArrayList<>();

        colNama.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        colEmail.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));
        System.out.println("Admin Controller Initialized");
        loadUsers();
        System.out.println("Users loaded");

    }

    private void loadUsers() {
        users.clear();
        usersListener = dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                users.clear();
                System.out.println(snapshot);
                for (DataSnapshot child : snapshot.getChildren()) {
                    User user = child.getValue(User.class);
                    users.add(user);
                }
                tbUser.getItems().setAll(users);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Error loading users: " + error.getMessage());
                error.toException().printStackTrace();
            }

        });
    }

    @SuppressWarnings("exports")
    @FXML
    public void handleCreateUser(ActionEvent event) {
        String role = cbRole.getValue();
        String nama = tfNama.getText();
        String email = tfEmail.getText();
        String password = tfPassword.getText();
        String username = tfUsername.getText();
        Administrator.createUser(role, nama, email, password, username);
        // Integer roleInt = 0;
        // switch (role) {
        //     case "Administrator":
        //         roleInt = 1;
        //         break;
        //     case "Supervisor":
        //         roleInt = 2;
        //         break;
        //     case "Surveyor":
        //         roleInt = 3;
        //         break;
        
        //     default:
        //         roleInt = 0;
        //         break;
        // }

        // try {
        //     System.out.println("Creating user");
        //     CreateRequest request = new CreateRequest()
        //         .setEmail(email)
        //         .setEmailVerified(false)
        //         .setPassword(password)
        //         .setDisplayName(nama)
        //         .setDisabled(false);


        //     UserRecord userRecord = auth.createUser(request);
        //     System.out.println("User created: " + userRecord.getUid());
        //     User user = new User(userRecord.getUid(), roleInt, nama, username, email, password, new Vote());
        //     dbRef.child(userRecord.getUid()).setValue(user, (error, ref) -> {
        //         if (error != null) {
        //             System.err.println("Data could not be saved: " + error.getMessage());
        //             error.toException().printStackTrace();
        //         } else {
        //             System.out.println("Data saved successfully.");
        //         }
        //     });
            
        // } catch (FirebaseAuthException e) {
        //     e.printStackTrace();
        //     System.out.println("Error creating user: " + e.getMessage());
        // }
    }


    
}
