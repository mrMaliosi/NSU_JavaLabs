package ru.nsu.ccfit.malinovskii.MessageTypes;

public class Text {
    String message;
    Text(){
        this.message = "";
    }

    public Text(String message){
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
