package ru.nsu.ccfit.malinovskii.MessageTypes;

public class Message {
    String data;
    String type;
    String login;

    public Message(){
        this.type = "";
        this.data = "";
        this.login = "";
    }


    public Message(String type, String data, String login){
        this.type = type;
        this.data = data;
        this.login = login;
    }

    public void setData(String data){
        this.data = data;
    }

    public void setType(String type){
        this.type = type;
    }

    public void setLogin(String login){
        this.login = login;
    }

    public String getData(){
        return this.data;
    }

    public String getType(){
        return this.type;
    }

    public String getLogin(){
        return this.login;
    }
}
