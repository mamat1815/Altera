package com.alterra.data.models;


public class Election {
    private String uid;
    private String title;
    public String timeStart;
    public String timeEnd;
    public String electionDate;
    public Election(String title ,String timeStart, String timeEnd,String electionDate) {
        this.title = title;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.electionDate = electionDate;
    }
    public Election() {

    }
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getTimeStart() {
        return timeStart;
    }
    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }
    public String getTimeEnd() {
        return timeEnd;
    }
    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    

   
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public static void recordVote() {
        
    }
    public String getElectionDate() {
        return electionDate;
    }
    public void setElectionDate(String electionDate) {
        this.electionDate = electionDate;
    }

    
}
