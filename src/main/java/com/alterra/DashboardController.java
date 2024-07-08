package com.alterra;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.alterra.data.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

public class DashboardController implements Initializable {

    @FXML
    private Label lbTotal;

    @FXML
    private Label lbBelum;

    @FXML
    private Label lbSudah;

    @FXML
    private BarChart<String, Integer> barLive;

    private DatabaseReference dbRef;
    private ArrayList<User> users;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        dbRef = FirebaseDatabase.getInstance().getReference("users");
        users = new ArrayList<>();
        fetchData();
    }

    private void fetchData() {
        users.clear();
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                users.clear();
                int[] counts = new int[3]; // Array untuk menyimpan nilai totalUsers, belumVote, dan sudahVote
                Map<String, Integer> candidateVotes = new HashMap<>();

                for (DataSnapshot child : snapshot.getChildren()) {
                    User user = child.getValue(User.class);
                    users.add(user);
                    counts[0]++; // Increment totalUsers
                    if (user.getRole() == 4 && !user.getVote().getCandidateId().isEmpty()) {
                        String candidateId = user.getVote().getCandidateId();
                        candidateVotes.put(candidateId, candidateVotes.getOrDefault(candidateId, 0) + 1);
                    }
                }

                // Update UI components inside Platform.runLater()
                Platform.runLater(() -> {
                    lbTotal.setText(String.valueOf(counts[0]));
                    lbBelum.setText(String.valueOf(counts[1]));
                    lbSudah.setText(String.valueOf(counts[0] - counts[1]));

                    // Populate BarChart
                    populateBarChart(candidateVotes);
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {
                throw new UnsupportedOperationException("Failed to read value.", error.toException());
            }
        });
    }

    private void populateBarChart(Map<String, Integer> candidateVotes) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        barLive.setTitle("Votes per Candidate");
        xAxis.setLabel("Candidate ID");
        yAxis.setLabel("Vote Count");

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        ObservableList<XYChart.Data<String, Integer>> data = FXCollections.observableArrayList();

        for (String candidateId : candidateVotes.keySet()) {
            int voteCount = candidateVotes.get(candidateId);
            data.add(new XYChart.Data<>(candidateId, voteCount));
        }

        series.setData(data);
        barLive.getData().add(series);
    }
}
