package com.alterra.data.models;

import java.sql.Timestamp;

public class Vote {
    private int id;
    private int candidateId;
    private int voterId;
    private Timestamp voteTime;

    public Vote() {
    }   

    public Vote(int id, int candidateId, int voterId, Timestamp voteTime) {
        this.id = id;
        this.candidateId = candidateId;
        this.voterId = voterId;
        this.voteTime = voteTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    public int getVoterId() {
        return voterId;
    }

    public void setVoterId(int voterId) {
        this.voterId = voterId;
    }

    public Timestamp getVoteTime() {
        return voteTime;
    }

    public void setVoteTime(Timestamp voteTime) {
        this.voteTime = voteTime;
    }

    public static void validateVote() {

    }

    public static void countVote() {

    }

    

}
