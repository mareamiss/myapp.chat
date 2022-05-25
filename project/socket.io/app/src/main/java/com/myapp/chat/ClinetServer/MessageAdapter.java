package com.myapp.chat.ClinetServer;

public class MessageAdapter {

    private String nickname;
    private String message ;

    public MessageAdapter(){

    }
    public MessageAdapter(String nickname, String message) {
        this.nickname = nickname;
        this.message = message;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}