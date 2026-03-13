package com.example.demo.dto.subscription;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

    private String userId;
    private String email;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
