package com.example.rmxline.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_line")
public class UserLineModel {
    @Id
    private String rcc;
    @Column(name = "user_token")
    private String userToken;
    @Column(name = "read_massage")
    private boolean readMassage;
    @Column(name = "status")
    private boolean status;
    @Column(name = "username")
    private String username;
    @Column(name = "rcc_time")
    private String rccTime;

    public UserLineModel() {
    }

    public UserLineModel(String rcc, String userToken, boolean readMassage, boolean status, String username, String rccTime) {
        this.rcc = rcc;
        this.userToken = userToken;
        this.readMassage = readMassage;
        this.status = status;
        this.username = username;
        this.rccTime = rccTime;
    }

    public String getRcc() {
        return rcc;
    }

    public void setRcc(String rcc) {
        this.rcc = rcc;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public boolean isReadMassage() {
        return readMassage;
    }

    public void setReadMassage(boolean readMassage) {
        this.readMassage = readMassage;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRccTime() {
        return rccTime;
    }

    public void setRccTime(String rccTime) {
        this.rccTime = rccTime;
    }
}
