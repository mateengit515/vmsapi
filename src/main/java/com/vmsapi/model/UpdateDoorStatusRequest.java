package com.vmsapi.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateDoorStatusRequest {
    @JsonAlias({"door_no", "doorNo"})
    @JsonProperty("door_no")
    private String doorNo;

    @JsonAlias({"status"})
    @JsonProperty("status")
    private String status;

    public String getDoorNo() { return doorNo; }
    public void setDoorNo(String doorNo) { this.doorNo = doorNo; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
