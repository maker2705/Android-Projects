package com.ankit.whatsappclone.Models;

public class Users {
    String profilePic,userName,mail,password,userID,lastMessage,status;

    public  Users(){
    //empty constructor to be used by firebase (time created:00:39 26Feb2023)
    }

    public Users(String profilePic, String userName, String mail, String password, String userID, String lastMessage, String status) {
        this.profilePic = profilePic;
        this.userName = userName;
        this.mail = mail;
        this.password = password;
        this.userID = userID;
        this.lastMessage = lastMessage;
        this.status = status;
    }

    //creating another constructor for signup with only three items i.e. email, username, password


    public Users(String userName, String mail, String password) {
        this.userName = userName;
        this.mail = mail;
        this.password = password;
    }

    //creating getters and setters for all of the variables.
    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
