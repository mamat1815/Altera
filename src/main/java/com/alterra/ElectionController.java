package com.alterra;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

import com.alterra.data.models.Election;
import com.alterra.data.models.Surveyor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
public class ElectionController implements Initializable {

    @FXML
    private Spinner<Integer> spStartHour;
    @FXML
    private Spinner<Integer> spStartMinute;
    @FXML
    private Spinner<Integer> spEndHour;
    @FXML
    private Spinner<Integer> spEndMinute;

    @FXML
    private DatePicker dpElection;

    @FXML
    private TextField tfTitle;

    @FXML
    private TableView<Election> tbElections;

    @FXML
    private TableColumn<Election,String> tcStart;

    @FXML
    private TableColumn<Election,String> tcEnd;

    @FXML
    private TableColumn<Election,String> tcTitle;

    @FXML
    private TableColumn<Election,String> tcDate;

    private DatabaseReference dbRef;

    ArrayList<Election> elections;
    @SuppressWarnings("unused")
    private ValueEventListener electionListener;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Initialize spinners for selecting hours and minutes
        spStartHour.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23));
        spStartMinute.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59));
        spEndHour.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23));
        spEndMinute.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59));

        dbRef = FirebaseDatabase.getInstance().getReference("election");
        elections = new ArrayList<>();

        tcDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getElectionDate()));
        tcEnd.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTimeEnd()));
        tcStart.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTimeStart()));
        tcTitle.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitle()));
        System.out.println("Election Init");
        loadElections();
        System.out.println("elec load");
    }

    private void loadElections() {
        elections.clear();
        electionListener = dbRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                elections.clear();
                System.out.println(snapshot);
                for (DataSnapshot child : snapshot.getChildren()) {
                    Election election = child.getValue(Election.class);
                    elections.add(election);
                    System.out.println(election.getTitle());
                    
                }
                tbElections.getItems().setAll(elections);
                
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Error loading users: " + error.getMessage());
            }
            
        });
    }

    @FXML
    private void setTime() {
        int hourStart = spStartHour.getValue();
        int minuteStart = spStartMinute.getValue();
        int hourEnd = spEndHour.getValue();
        int minuteEnd = spEndMinute.getValue();
        String title = tfTitle.getText();
        System.out.println(title);
        String electionDate = dpElection.getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        
        System.out.println(dpElection.getValue().getMonth());
        // Calendar startCalendar = Calendar.getInstance();
        // startCalendar.set(Calendar.HOUR_OF_DAY, hourStart);
        // startCalendar.set(Calendar.MINUTE, minuteStart);
        // startCalendar.set(Calendar.SECOND, 0); 
        // startCalendar.set(Calendar.MILLISECOND, 0);

        // Timestamp startTimestamp = new Timestamp(startCalendar.getTimeInMillis());

        // Calendar endCalendar = Calendar.getInstance();
        // endCalendar.set(Calendar.HOUR_OF_DAY, hourEnd);
        // endCalendar.set(Calendar.MINUTE, minuteEnd);
        // endCalendar.set(Calendar.SECOND, 0); 
        // endCalendar.set(Calendar.MILLISECOND, 0);

        // Timestamp endTimestamp = new Timestamp(endCalendar.getTimeInMillis());
        // System.out.println(endTimestamp);

        // Create a new Election object with the selected times and title
        String startTimestamp = hourStart + ":" + minuteStart;
        String endTimestamp = hourEnd + ":" + minuteEnd;
        Election election = new Election(title, startTimestamp, endTimestamp, electionDate);
        Surveyor.addElection(election);
    }

    
}
