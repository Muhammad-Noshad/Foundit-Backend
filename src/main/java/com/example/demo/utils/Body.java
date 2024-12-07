package com.example.demo.utils;

import org.springframework.stereotype.Service;

public class Body {
    private String message;

    public Body(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

