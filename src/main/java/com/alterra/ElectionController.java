package com.alterra;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

public class ElectionController implements Initializable {

    @FXML
    private Spinner spStartHour;
    @FXML
    private Spinner spStartMinute;
    @FXML
    private Spinner spEndHour;
    @FXML
    private Spinner spEndMinute;

    @FXML
    private TextField tfTitle;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        spStartHour.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23));
        spStartMinute.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59));
        spEndHour.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23));
        spEndMinute.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59));
    }

    

    
}
