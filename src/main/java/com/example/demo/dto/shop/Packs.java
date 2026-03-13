package com.example.demo.dto.shop;

import java.io.Serializable;

public class Packs implements Serializable {

    private String packName;
    private String title;

    public Packs() {
    }

    public Packs(String packName, String title) {
        this.packName = packName;
        this.title = title;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
