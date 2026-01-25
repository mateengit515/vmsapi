package com.vmsapi.controller;

import com.vmsapi.model.Voter;
import com.vmsapi.model.UpdateInchargeRequest;
import com.vmsapi.model.UpdateStatusRequest;
import com.vmsapi.model.UpdateVotedRequest;
import com.vmsapi.repository.DoorNoSummary;
import com.vmsapi.service.VoterService;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;



class VoterListResponse {
    private List<Voter> list;

    public VoterListResponse(List<Voter> list) {
        // Sort by serialNo (SLNo) ascending
        list.sort(Comparator.comparingInt(Voter::getSerialNo));
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

    @PostMapping("/update-incharge")
    public void updateIncharge(@RequestBody UpdateInchargeRequest request) {
        voterService.updateInchargeByDoorNo(request.getDoorNo(), request.getIncharge());
    }

    @GetMapping("/door-summary")
    public List<DoorNoSummary> getDoorNoSummary() {
        return voterService.getDoorNoSummary();
    }

    @PostMapping("/update-status")
    public void updateStatus(@RequestBody UpdateStatusRequest request) {
        voterService.updateStatusByEpicNo(request.getEpicNo(), request.getStatus());
    }

    @PostMapping("/update-voted")
    public void updateVoted(@RequestBody UpdateVotedRequest request) {
        voterService.updateVotedByEpicNo(request.getEpicNo(), request.getVoted());
    }
}
