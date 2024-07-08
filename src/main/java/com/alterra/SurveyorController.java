package com.alterra;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.alterra.utility.ViewFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class SurveyorController implements Initializable{
    

    @FXML
    private Button setTimeButton;

    @FXML
    private Button btnCandidate;

    @FXML
    private Button btnElection;

    @FXML
    private Button btnVerif;

    @FXML
    private Pane pnMain;
    



    @SuppressWarnings("exports")
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnElection) {
            openPage("election-view");
            btnElection.setStyle("-fx-background-color: #4880FF");
        } else if (event.getSource() == btnCandidate) {
            openPage("candidate-view");
            btnCandidate.setStyle("-fx-background-color: #4880FF");
        } else if (event.getSource() == btnVerif) {
            openPage("verif-view");
            btnVerif.setStyle("-fx-background-color: #4880FF");
        }
    }



    @FXML
    public void openPage(String fxml) {
        Pane parent;
        resetButtonStyles();
        try {
            parent = new ViewFactory().loadFXMLPane(fxml);
            pnMain.getChildren().clear();
            pnMain.getChildren().add(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void resetButtonStyles() {
        btnCandidate.setStyle(null);
        btnElection.setStyle(null);
        btnVerif.setStyle(null);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        openPage("candidate-view");
        btnCandidate.setStyle("-fx-background-color: #4880FF");

        
    }
}
