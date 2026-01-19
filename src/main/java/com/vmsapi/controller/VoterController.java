package com.vmsapi.controller;

import com.vmsapi.model.Voter;
import com.vmsapi.service.VoterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

class VoterListResponse {
    private List<Voter> list;

    public VoterListResponse(List<Voter> list) {
        this.list = list;
    }

    public List<Voter> getList() {
        return list;
    }

    public void setList(List<Voter> list) {
        this.list = list;
    }
}

@RestController
@RequestMapping("/api/vi/voters")
public class VoterController {

    private final VoterService voterService;

    public VoterController(VoterService voterService) {
        this.voterService = voterService;
    }

    @GetMapping
    public VoterListResponse listVoters() {
        return new VoterListResponse(voterService.getVoters());
    }

    @GetMapping("/door/{doorNo}")
    public VoterListResponse getVotersByDoorNo(@PathVariable String doorNo) {
        return new VoterListResponse(voterService.getVotersByDoorNo(doorNo));
    }
}
