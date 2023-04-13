package com.ankit.whatsappclone.Models;

public class MessageModel {
    String uID, message, messageId;
    Long timestamp;
    public MessageModel(String uID, String message, String messageId, Long timestamp) {
        this.uID = uID;
        this.message = message;
        this.timestamp = timestamp;
    }

    public MessageModel(String uID, String message) {
        this.uID = uID;
        this.message = message;
    }
public MessageModel(){

}
    public String getuID() {
        return uID;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageId() {
        return messageId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }


}
