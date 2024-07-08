package com.alterra;

import java.net.URL;
import java.util.ResourceBundle;

import com.alterra.data.models.User;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class UserController implements Initializable{

    @FXML
    Label lbNama;

    private User user;

    public void initData(@SuppressWarnings("exports") User dataUser) {
        user = dataUser;
        System.out.println(dataUser.getEmail());
        lbNama.setText(user.getName());
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
    }
    
}
