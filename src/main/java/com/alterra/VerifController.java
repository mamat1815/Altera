package com.alterra;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.alterra.data.models.Election;
import com.alterra.data.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;

public class VerifController implements Initializable {

    @FXML
    private ChoiceBox<String> electionBox;

    @FXML
    private TableView<User> tbUser;

    @FXML
    private TableColumn<User, String> colNama;

    @FXML
    private TableColumn<User, String> colEmail;

    @FXML
    private TableColumn<User, String> colElec;

    @FXML
    private Button updateButton;

    private DatabaseReference dbElect;
    private ArrayList<Election> elections;
    private DatabaseReference dbRef;
    private ArrayList<User> users;

    private ValueEventListener usersListener;
    private ValueEventListener electionListener;
    private User selectedUser;
    private String uid;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        dbElect = FirebaseDatabase.getInstance().getReference("election");
        elections = new ArrayList<>();
        loadCBElections();

        dbRef = FirebaseDatabase.getInstance().getReference("users");
        users = new ArrayList<>();

        colNama.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        colEmail.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));
        colElec.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getVote().getElectionId()));

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

        loadUsers();
    }

    @FXML
    private void handleUpdateButtonAction() {

        if (selectedUser != null) {
            String selectedElection = electionBox.getValue();
            for (Election election : elections) {
                if (election.getTitle().equals(selectedElection)) {
                   uid= election.getUid();
                }
            }
            if (selectedElection != null) {
                selectedUser.getVote().setElectionId(uid);
                dbRef.child(selectedUser.getId()).setValue(selectedUser, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError error, DatabaseReference ref) {
                        if (error != null) {
                            showAlert("Error", "Failed to update user: " + error.getMessage());
                        } else {
                            showAlert("Success", "User updated successfully.");
                            loadUsers(); // Refresh table view
                        }
                    }
                });
            } else {
                showAlert("Warning", "Please select an election from the ChoiceBox.");
            }
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

    private void loadCBElections() {
        elections.clear();
        electionListener = dbElect.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                elections.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    Election election = child.getValue(Election.class);
                    if (election != null) {
                        elections.add(election);
                        electionBox.getItems().add(election.getTitle());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Error loading elections: " + error.getMessage());
                error.toException().printStackTrace();
            }
        });
    }
}
