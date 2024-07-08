package com.alterra.data.models;

public class Candidate extends User{
    private String biograph;
    private String visi;
    private String misi;
    public Candidate() {

    }

    public Candidate(String id, int role, String name, String username, String email, String password, Vote vote) {
        super(id, role, name, username, email, password, vote);
    }

    public Candidate(String id, int role, String name, String username, String email, String password, Vote vote,
            String biograph, String visi, String misi) {
        super(id, role, name, username, email, password, vote);
        this.biograph = biograph;
        this.visi = visi;
        this.misi = misi;
    }

    public String getBiograph() {
        return biograph;
    }

    public void setBiograph(String biograph) {
        this.biograph = biograph;
    }

    public String getVisi() {
        return visi;
    }

    public void setVisi(String visi) {
        this.visi = visi;
    }

    public String getMisi() {
        return misi;
    }

    public void setMisi(String misi) {
        this.misi = misi;
    }
    


}
