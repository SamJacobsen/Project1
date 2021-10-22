package com.project1.models;

public class LoginRequest {

    private String uName;
    private String psw;

    public LoginRequest() {
    }

    public String getuName() {
        return uName;
    }

    public String getPsw() {
        return psw;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }
}
