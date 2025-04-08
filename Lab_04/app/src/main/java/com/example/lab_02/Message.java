package com.example.lab_02;

public class Message {
    private String name;
    private String number;

    Message(String _name, String _number) {
        name = _name;
        number = _number;
    }

    public String getName() {
        return name;
    }
    public String getNumber() {
        return number;
    }
    public void setName(String _name) {
        name = _name;
    }
    public void setNumber(String _number) {
        number = _number;
    }
}
