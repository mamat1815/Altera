package com.alterra;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import com.alterra.data.models.User;
import com.alterra.utility.ViewFactory;

public class AdministratorController implements Initializable {

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnAdministrator;

    @FXML
    private Button btnSupervisor;

    @FXML
    private Button btnSurveyor;

    @FXML
    private Pane pnMain;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        openPage("dashboard-view");
        btnDashboard.setStyle("-fx-background-color: #4880FF");
    }

    @SuppressWarnings("exports")
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnDashboard) {
            openPage("dashboard-view");
        } else if (event.getSource() == btnAdministrator) {
            openPage("admin-view");
            btnAdministrator.setStyle("-fx-background-color: #4880FF");
        } else if (event.getSource() == btnSupervisor) {
            openPage("supervisor-view");
            btnSupervisor.setStyle("-fx-background-color: #4880FF");
        } else if (event.getSource() == btnSurveyor) {
            openPage("surveyor-view");
            btnSurveyor.setStyle("-fx-background-color: #4880FF");
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
        btnDashboard.setStyle(null);
        btnAdministrator.setStyle(null);
        btnSupervisor.setStyle(null);
        btnSurveyor.setStyle(null);
    }

    public void initData(@SuppressWarnings("exports") User user) {
        System.out.println(user.getEmail());
    }
}