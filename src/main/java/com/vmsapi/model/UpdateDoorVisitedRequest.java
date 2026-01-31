package com.vmsapi.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateDoorVisitedRequest {
    @JsonAlias({"door_no", "doorNo"})
    @JsonProperty("door_no")
    private String doorNo;
    @JsonAlias({"visited"})
    @JsonProperty("visited")
    private String visited;

    public String getDoorNo() { return doorNo; }
    public void setDoorNo(String doorNo) { this.doorNo = doorNo; }
    public String getVisited() { return visited; }
    public void setVisited(String visited) { this.visited = visited; }
}
