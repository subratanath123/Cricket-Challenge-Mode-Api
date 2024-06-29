package com.example.demo.dto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class LevelList {

    @JsonProperty("LevelList")
    private List<String>  levelList;

    public List<String> getLevelList() {
        return levelList;
    }

    public void setLevelList(List<String> levelList) {
        this.levelList = levelList;
    }

    public LevelList(List<String> levelList) {
        this.levelList = levelList;
    }
}
