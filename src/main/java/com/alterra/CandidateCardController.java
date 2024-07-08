package com.alterra;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CandidateCardController {

    @FXML
    private Label nameLabel;

    @FXML
    private Label bioLabel;

    @FXML
    private Label misiLabel;

    @FXML
    private Label visiLabel;

    @FXML
    private Button voteButton;

    private String candidateId;

    public void setCandidate(String name, String bio, String misi, String visi, String id) {
        nameLabel.setText(name);
        bioLabel.setText(bio);
        misiLabel.setText(misi);
        visiLabel.setText(visi);
        candidateId = id;
    }

    @FXML
    private void handleVote() {
        // Implement vote handling logic here
        System.out.println("Voted for candidate ID: " + candidateId);
        // Here you can call a method to record the vote in Firebase
    }
}
