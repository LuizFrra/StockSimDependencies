package com.luizfrra.Models;

public class RegisterDetails {

    public final String realmId;

    public final String clientId;

    public final String userId;

    public final String email;

    public final String userName;


    public RegisterDetails(String realmId, String clientId, String userId, String email, String userName) {
        this.realmId = realmId;
        this.clientId = clientId;
        this.userId = userId;
        this.email = email;
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "{" +
                "realmId:'" + realmId + '\'' +
                ", clientId:'" + clientId + '\'' +
                ", userId:'" + userId + '\'' +
                ", email:'" + email + '\'' +
                ", userName:'" + userName + '\'' +
                '}';
    }
}
