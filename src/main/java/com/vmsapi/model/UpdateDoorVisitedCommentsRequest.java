package com.vmsapi.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateDoorVisitedCommentsRequest {
    @JsonAlias({"door_no", "doorNo"})
    @JsonProperty("door_no")
    private String doorNo;
    @JsonAlias({"visited"})
    @JsonProperty("visited")
    private String visited;
    @JsonAlias({"comments"})
    @JsonProperty("comments")
    private String comments;

    public String getDoorNo() { return doorNo; }
    public void setDoorNo(String doorNo) { this.doorNo = doorNo; }
    public String getVisited() { return visited; }
    public void setVisited(String visited) { this.visited = visited; }
    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }
}
