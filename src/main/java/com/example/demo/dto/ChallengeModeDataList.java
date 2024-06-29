package com.example.demo.dto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ChallengeModeDataList {

    @JsonProperty("ChallengeModeDataList")
    private List<ChallengeModeData>  challengeModeDataList;

    public ChallengeModeDataList(List<ChallengeModeData> challengeModeDataList) {
        this.challengeModeDataList = challengeModeDataList;
    }

    public List<ChallengeModeData> getChallengeModeDataList() {
        return challengeModeDataList;
    }

    public void setChallengeModeDataList(List<ChallengeModeData> challengeModeDataList) {
        this.challengeModeDataList = challengeModeDataList;
    }
}
