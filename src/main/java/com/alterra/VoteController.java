package com.alterra;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.alterra.data.models.Candidate;
import com.alterra.data.models.Election;
import com.alterra.data.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class VoteController implements Initializable {
    
    @FXML
    private TableView<Candidate> tbElections;

    @FXML
    private TableColumn<Candidate,String> tcStart;

    @FXML
    private TableColumn<Candidate,String> tcEnd;

    @FXML
    private TableColumn<Candidate,String> tcTitle;

    @FXML
    private TableColumn<Candidate,String> tcDate;


    private User user;
    private ArrayList<Candidate> candidates;
    private DatabaseReference dbRef;
    private ValueEventListener candEventListener;
    private User selectedUser;

    public void initData(User dataUser) {
        user = dataUser;
        candidates = new ArrayList<>();
        System.out.println(user.getName());
        dbRef = FirebaseDatabase.getInstance().getReference("users");
        tcDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getVisi()));
        tcEnd.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBiograph()));
        tcStart.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMisi()));
        tcTitle.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));

        tbElections.setRowFactory(tv -> {
            TableRow<Candidate> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    selectedUser = row.getItem();
                    System.out.println("Data yang dipilih: " + selectedUser.getId() + ", " + selectedUser.getEmail());
                }
            });
            return row;
        });
        
        loadCard();
    }

    private void loadCard() {
        candidates.clear();
        candEventListener = dbRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                candidates.clear(); // Clear the container before adding new cards
                for (DataSnapshot child : snapshot.getChildren()) {
                    Candidate candidate = child.getValue(Candidate.class);
                    System.out.println(candidate.getBiograph());
                    if (candidate.getRole() == 4) {
                        candidates.add(candidate);
                    }
                }
                tbElections.getItems().setAll(candidates);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                throw new UnsupportedOperationException("Unimplemented method 'onCancelled'");
            }
        });
    }

    
    @FXML
    private void handleVote() {
        if (user != null && selectedUser != null) {
            System.out.println("Voted for candidate ID: " + selectedUser.getId());
            System.out.println("User ID: " + user.getId());
    
            // Update Firebase with the vote
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(user.getId());
            user.getVote().setCandidateId(selectedUser.getId());
            userRef.setValue(user, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError error, DatabaseReference ref) {
                    if (error != null) {
                        System.out.println("Failed to vote: " + error.getMessage());
                    } else {
                        System.out.println("Vote recorded successfully.");
                        loadCard(); // Refresh table view
                    }
                }
            });
        } else {
            System.out.println("User or selected candidate is null.");
            // Handle null user or selected candidate case
        }
    }
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // candidates = new ArrayList<>();
        // dbRef = FirebaseDatabase.getInstance().getReference("users");
        // tcDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getVisi()));
        // tcEnd.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBiograph()));
        // tcStart.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMisi()));
        // tcTitle.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        // loadCard();
    }
}
