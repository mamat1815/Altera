package com.alterra.utility;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.FileInputStream;
import java.io.IOException;

public class FirebaseInitializer {
    @SuppressWarnings("deprecation")
    public static void initialize() throws IOException {
        try {
            FileInputStream serviceAccount = new FileInputStream("alterra-elvis-firebase-adminsdk-aljyo-6eeab2f959.json");
            
            FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://alterra-elvis-default-rtdb.firebaseio.com/")
                .build();
    
            FirebaseApp.initializeApp(options);
            System.out.println("Firebase has been initialized");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to initialize Firebase" + e.getMessage()); 
        }
    }
}
