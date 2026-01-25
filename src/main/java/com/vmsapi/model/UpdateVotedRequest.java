package com.vmsapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateVotedRequest {
    @JsonProperty("epic_no")
    private String epicNo;
    @JsonProperty("voted")
    private String voted;

    public String getEpicNo() {
        return epicNo;
    }

    public void setEpicNo(String epicNo) {
        this.epicNo = epicNo;
    }

    public String getVoted() {
        return voted;
    }

    public void setVoted(String voted) {
        this.voted = voted;
    }
}

