package com.alterra;


import java.io.IOException;

import com.alterra.utility.ViewFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.Pane;

public class SurveyorController {
    
    @FXML
    private Spinner<Integer> hourSpinner;

    @FXML
    private Spinner<Integer> minuteSpinner;

    @FXML
    private Button setTimeButton;

    @FXML
    private Button btnCandidate;

    @FXML
    private Button btnElection;

    @FXML
    private Pane pnMain;

    @FXML
    private void initialize() {
        // Inisialisasi spinner untuk jam dan menit
        // hourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23));
        // minuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59));
        openPage("election-view");
        btnElection.setStyle("-fx-background-color: #4880FF");
    }

    @SuppressWarnings("exports")
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnElection) {
            openPage("election-view");
            btnElection.setStyle("-fx-background-color: #4880FF");
        } else if (event.getSource() == btnCandidate) {
            openPage("candidate-view");
            btnCandidate.setStyle("-fx-background-color: #4880FF");
        }
    }

    @FXML
    private void setTime() {
        int hour = hourSpinner.getValue();
        int minute = minuteSpinner.getValue();
        // Lakukan sesuatu dengan waktu yang dipilih
        System.out.println("Waktu yang dipilih: " + hour + ":" + minute);
        // Misalnya, Anda bisa menyimpan waktu ke suatu variabel atau melakukan aksi lainnya
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
    }
}
