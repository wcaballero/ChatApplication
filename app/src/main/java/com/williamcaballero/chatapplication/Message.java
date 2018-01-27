package com.williamcaballero.chatapplication;

/**
 * Created by williamcaballero on 1/27/18.
 */

// used to pull messages from the database
public class Message {

    private String content;

    public Message() {

    }

    public Message(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
