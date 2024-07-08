package com.alterra;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class DashboardController {
    @FXML
    private Button btnExcel;

    @FXML
    private void saveToExcel(ActionEvent event) {
        System.out.println("Save to Excel");

    }
}
