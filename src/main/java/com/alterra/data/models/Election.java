package com.alterra.data.models;

import java.sql.Timestamp;

public class Election {
    private String uid;
    private Timestamp timeStart;
    private Timestamp timeEnd;
    public Election(String uid, Timestamp timeStart, Timestamp timeEnd) {
        this.uid = uid;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }
    public Election() {

    }
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public Timestamp getTimeStart() {
        return timeStart;
    }
    public void setTimeStart(Timestamp timeStart) {
        this.timeStart = timeStart;
    }
    public Timestamp getTimeEnd() {
        return timeEnd;
    }
    public void setTimeEnd(Timestamp timeEnd) {
        this.timeEnd = timeEnd;
    }

    public static void recordVote() {
        
    }

    
}
