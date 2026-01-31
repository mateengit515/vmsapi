package com.vmsapi.controller;

import com.vmsapi.model.Voter;
import com.vmsapi.model.UpdateInchargeRequest;
import com.vmsapi.model.UpdateStatusRequest;
import com.vmsapi.model.UpdateVotedRequest;
import com.vmsapi.model.UpdateContactNumberRequest;
import com.vmsapi.repository.DoorNoSummary;
import com.vmsapi.service.VoterService;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import com.vmsapi.security.SecurityUtil;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerMapping;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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

    // Use a catch-all mapping so door numbers may contain slashes and optional trailing slash
    @GetMapping("/door/**")
    public VoterListResponse getVotersByDoorNo(HttpServletRequest request) {
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String doorNo = new AntPathMatcher().extractPathWithinPattern(bestMatchPattern, path);

        if (doorNo == null) doorNo = "";
        // Normalize: remove trailing slash if present
        if (doorNo.endsWith("/")) {
            doorNo = doorNo.substring(0, doorNo.length() - 1);
        }
        // URL decode in case the client encoded slashes or special chars
        doorNo = URLDecoder.decode(doorNo, StandardCharsets.UTF_8);

        return new VoterListResponse(voterService.getVotersByDoorNo(doorNo));
    }

    @PostMapping("/update-incharge")
    public void updateIncharge(@RequestBody UpdateInchargeRequest request) {
        voterService.updateInchargeByDoorNo(request.getDoorNo(), request.getIncharge());
    }

    @PostMapping("/update-contact")
    public void updateContact(@RequestBody UpdateContactNumberRequest request) {
        voterService.updateContactNumberByEpicNo(request.getEpicNo(), request.getContactNumber());
    }

    @GetMapping("/door-summary")
    public List<DoorNoSummary> getDoorNoSummary(HttpServletRequest request) {
        String role = SecurityUtil.getCurrentRole(request);
        String username = SecurityUtil.getCurrentUsername();


        if ("incharge".equals(role)) {
            // Only show doors assigned to this incharge
            return voterService.getDoorNoSummaryForIncharge(username);
        } else {
            // Admin or other roles see all
            return voterService.getDoorNoSummary();
        }
    }

    @PostMapping("/update-status")
    public void updateStatus(@RequestBody UpdateStatusRequest request) {
        voterService.updateStatusByEpicNo(request.getEpicNo(), request.getStatus());
    }

    @PostMapping("/update-voted")
    public void updateVoted(@RequestBody UpdateVotedRequest request) {
        voterService.updateVotedByEpicNo(request.getEpicNo(), request.getVoted());
    }

    @GetMapping("/whoami")
    public String whoami(HttpServletRequest request) {
        String username = SecurityUtil.getCurrentUsername();
        String role = SecurityUtil.getCurrentRole(request);
        return "username=" + username + ", role=" + role;
    }
}
