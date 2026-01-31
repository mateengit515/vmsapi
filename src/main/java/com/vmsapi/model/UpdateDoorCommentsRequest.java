package com.vmsapi.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateDoorCommentsRequest {

    @JsonAlias({"door_no","doorNo"})
    @JsonProperty("door_no")
    private String doorNo;

    @JsonAlias({"comments"})
    @JsonProperty("comments")
    private String comments;

    public UpdateDoorCommentsRequest() {}

    public String getDoorNo() { return doorNo; }
    public void setDoorNo(String doorNo) { this.doorNo = doorNo; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }
}
