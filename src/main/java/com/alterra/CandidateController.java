package com.alterra;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.alterra.data.models.Candidate;
import com.alterra.data.models.Surveyor;
import com.alterra.data.models.User;
import com.alterra.data.models.Vote;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CandidateController implements Initializable{

    @FXML
    private TextField tfNama;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfPassword;

    @FXML
    private TextField tfUsername;

    @FXML
    private TextArea taVisi;

    @FXML
    private TextArea taMisi;

    @FXML
    private TextArea taBio;

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

    private User selectedUser;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        dbRef = FirebaseDatabase.getInstance().getReference("users");
                users = new ArrayList<>();

        colNama.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        colEmail.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));
        System.out.println("Admin Controller Initialized");
        loadUsers();
        System.out.println("Users loaded");

        tbUser.setRowFactory(tv -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    selectedUser = row.getItem();
                    System.out.println("Data yang dipilih: " + selectedUser.getName() + ", " + selectedUser.getEmail());
                }
            });
            return row;
        });
    }
    @FXML
    private void handleDeleteButtonAction() {
        if (selectedUser != null) {
            dbRef.child(selectedUser.getId()).removeValue(new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError error, DatabaseReference ref) {
                    if (error != null) {
                        showAlert("Error", "Failed to delete user: " + error.getMessage());
                    } else {
                        showAlert("Success", "User deleted successfully.");
                        loadUsers(); // Refresh table view
                    }
                }
            });
        } else {
            showAlert("Warning", "No user selected. Please select a user from the table.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
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
                    if (user.getRole() == 4) {
                        users.add(user);
                    }
                    
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

    @FXML
    private  void createCandidate() {
        String nama = tfNama.getText();
        String email = tfEmail.getText();
        String password = tfPassword.getText();
        String username = tfUsername.getText();
        String visi = taVisi.getText();
        String misi = taMisi.getText();
        String bio = taBio.getText();
        Surveyor.createCandidate(nama,email,password,username,visi,misi,bio);
    }
    
}
