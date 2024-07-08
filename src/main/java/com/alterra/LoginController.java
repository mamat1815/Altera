package com.alterra;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.alterra.data.models.User;
import com.alterra.utility.Utility;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

public class LoginController implements Initializable {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private Stage primaryStage; // Stage utama aplikasi

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Initialize any necessary components
    }

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();
        System.out.println("handle Login");
        if (Utility.isValidAcc(email, password)) {
            System.out.println("signIn");
            User.signIn(email, password, new User.LoginCallback() {
                @Override
                public void onSuccess(User user) {
                    System.out.println("Login successful for: " + user.getName());
                    // Menutup stage utama aplikasi (jika perlu)
                    if (primaryStage != null) {
                        primaryStage.close();
                    }
                    
                    // Pindah ke administrator-view.fxml setelah login berhasil
                    Platform.runLater(() -> {
                        try {
                            String fxml;
                            if (user.getRole() == 0) {
                                fxml = "user-view.fxml";
                                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
                                Parent root = loader.load();

                                // Mengambil instance dari controller administrator-view.fxml
                                UserController userController = loader.getController();
                                userController.initData(user); // Mengirim data user ke controller

                                // Membuat stage baru untuk administrator view
                                Stage userStage = new Stage();
                                userStage.initStyle(StageStyle.DECORATED); // Style stage sesuai kebutuhan
                                userStage.initModality(Modality.APPLICATION_MODAL); // Stage modal
                                userStage.setTitle("User View"); // Judul stage

                                // Mengatur ukuran scene sesuai kebutuhan
                                Scene scene = new Scene(root, 800, 600); // Ganti 800 dan 600 dengan ukuran yang Anda inginkan
                                userStage.setScene(scene);

                                // Menutup stage login setelah menampilkan adminStage
                                userStage.setOnShown(event -> {
                                    Stage loginStage = (Stage) emailField.getScene().getWindow();
                                    loginStage.close();
                                });

                                userStage.showAndWait();
                            } else {
                                fxml = "administrator-view.fxml";
                                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
                                Parent root = loader.load();

                                // Mengambil instance dari controller administrator-view.fxml
                                AdministratorController adminController = loader.getController();
                                adminController.initData(user); // Mengirim data user ke controller

                                // Membuat stage baru untuk administrator view
                                Stage adminStage = new Stage();
                                adminStage.initStyle(StageStyle.DECORATED); // Style stage sesuai kebutuhan
                                adminStage.initModality(Modality.APPLICATION_MODAL); // Stage modal
                                adminStage.setTitle("Administrator View"); // Judul stage

                                // Mengatur ukuran scene sesuai kebutuhan
                                Scene scene = new Scene(root, 800, 600); // Ganti 800 dan 600 dengan ukuran yang Anda inginkan
                                adminStage.setScene(scene);

                                // Menutup stage login setelah menampilkan adminStage
                                adminStage.setOnShown(event -> {
                                    Stage loginStage = (Stage) emailField.getScene().getWindow();
                                    loginStage.close();
                                });

                                adminStage.showAndWait();
                                
                            }
                             // Menampilkan stage dan menunggu sampai ditutup
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }

                @Override
                public void onFailure(String eMessage) {
                    showAlert("Error", eMessage);
                }
            });
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setPrimaryStage(@SuppressWarnings("exports") Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
