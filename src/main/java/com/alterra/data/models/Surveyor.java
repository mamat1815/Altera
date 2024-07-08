    package com.alterra.data.models;

    import java.util.Date;
    
    import com.alterra.utility.AlertUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;

    public class Surveyor extends User{
        private Vote vote;
        private Candidate candidate;
        private static DatabaseReference dbRefElection = FirebaseDatabase.getInstance().getReference("election");
        private static FirebaseAuth auth = FirebaseAuth.getInstance();
        private static DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("users");

        public Surveyor(Vote vote, Candidate candidate) {
            this.vote = vote;
            this.candidate = candidate;
        }
        public Surveyor(String id, int role, String name, String username, String email, String password, Vote vote,
                Vote vote2, Candidate candidate) {
            super(id, role, name, username, email, password, vote);
            vote = vote2;
            this.candidate = candidate;
        }

        public Surveyor() {

        }
        public Vote getVote() {
            return vote;
        }
        public void setVote(Vote vote) {
            this.vote = vote;
        }
        public Candidate getCandidate() {
            return candidate;
        }
        public void setCandidate(Candidate candidate) {
            this.candidate = candidate;
        }

        public static void addElection(Election election) {
            String key = dbRefElection.push().getKey();

            if (key != null) {
                election.setUid(key);
                dbRefElection.child(key).setValue(election , (error , ref)-> {
                    if (error != null) {
                        System.err.println("Data could not be saved: " + error.getMessage());

                        AlertUtil.showAlert("Data could not be saved: ", error.getMessage());
                        error.toException().printStackTrace();
                    } else {
                        System.out.println("Data saved successfully.");
                        System.out.println(election.getTitle());
                    }
                });
            }
        }
        
        public static void createCandidate(String nama, String email, String password, String username, String visi,
                String misi, String bio) {
                    try {
                         CreateRequest request = new CreateRequest()
                            .setEmail(email)
                            .setEmailVerified(false)
                            .setPassword(password)
                            .setDisplayName(nama)
                            .setDisabled(false);
                        UserRecord userRecord = auth.createUser(request);
                        Date d = new Date();
                        Candidate candidate = new Candidate(userRecord.getUid(), 4, nama, username, email, password, new Vote("","","",d,""), bio, visi, misi);
                       
                        
                        dbRef.child(userRecord.getUid()).setValue(candidate, (error, ref) -> {
                            if (error != null) {
                                System.err.println("Data could not be saved: " + error.getMessage());
                                AlertUtil.showAlert("Data could not be saved: ", error.getMessage());
                                error.toException().printStackTrace();
                            } else {
                                System.out.println("Data saved successfully.");
                            }
                        });
                    } catch (FirebaseAuthException e) {
                        e.printStackTrace();
                        System.out.println("Error creating user: " + e.getMessage());
                    }
        }
    
}
