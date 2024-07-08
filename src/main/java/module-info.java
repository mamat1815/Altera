module com.alterra {
    requires javafx.controls;
    requires javafx.fxml;
    requires firebase.admin;
    requires com.google.auth.oauth2;
    requires com.google.auth;
    requires javafx.base;
    requires java.sql;

    opens com.alterra to javafx.fxml, firebase.admin;
    opens com.alterra.data.models to firebase.admin;
    exports com.alterra;
}
