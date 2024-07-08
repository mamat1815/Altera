package com.alterra.data.models;


import java.util.Date;

public class Vote {
    private String id;
    private String candidateId;
    private String voterId;
    private Date voteTime;
    private String electionId;

    public Vote() {
        
    }   

    public Vote(String id, String candidateId, String voterId, Date voteTime, String election) {
        this.id = id;
        this.candidateId = candidateId;
        this.voterId = voterId;
        this.voteTime = voteTime;
        this.electionId = election;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public String getVoterId() {
        return voterId;
    }

    public void setVoterId(String voterId) {
        this.voterId = voterId;
    }

    public Date getVoteTime() {
        return voteTime;
    }

    public void setVoteTime(Date voteTime) {
        this.voteTime = voteTime;
    }

    public static void validateVote() {

    }

    public static void countVote() {

    }

    public String getElectionId() {
        return electionId;
    }

    public void setElectionId(String electionId) {
        this.electionId = electionId;
    }
    

    

}
