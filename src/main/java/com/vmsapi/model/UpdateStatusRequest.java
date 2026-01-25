package com.vmsapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateStatusRequest {
    @JsonProperty("epic_no")
    private String epicNo;
    @JsonProperty("status")
    private String status;

    public String getEpicNo() {
        return epicNo;
    }

    public void setEpicNo(String epicNo) {
        this.epicNo = epicNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

