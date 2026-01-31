package com.vmsapi.service;

import com.vmsapi.model.Voter;
import com.vmsapi.repository.DoorNoSummary;
import com.vmsapi.repository.VoterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoterService {

    private final VoterRepository voterRepository;

    public VoterService(VoterRepository voterRepository) {
        this.voterRepository = voterRepository;
    }

    public List<Voter> getVoters() {
        return voterRepository.findAllOrderBySerialNo();
    }

    public List<Voter> getVotersByDoorNo(String doorNo) {
        return voterRepository.findByDoorNo(doorNo);
    }

    public void updateInchargeByEpicNo(String epicNo, String incharge) {
        voterRepository.updateInchargeByEpicNo(epicNo, incharge);
    }

    public void updateInchargeByDoorNo(String doorNo, String incharge) {
        voterRepository.updateInchargeByDoorNo(doorNo, incharge);
    }

    public List<DoorNoSummary> getDoorNoSummary() {
        return voterRepository.findDoorNoSummary();
    }

    public List<DoorNoSummary> getDoorNoSummaryForIncharge(String incharge) {
        // Filter door summary by incharge
        return voterRepository.findDoorNoSummaryByIncharge(incharge);
    }

    public void updateStatusByEpicNo(String epicNo, String status) {
        voterRepository.updateStatusByEpicNo(epicNo, status);
    }

    public void updateVotedByEpicNo(String epicNo, String voted) {
        voterRepository.updateVotedByEpicNo(epicNo, voted);
    }

    public void updateContactNumberByEpicNo(String epicNo, String contactNumber) {
        voterRepository.updateContactNumberByEpicNo(epicNo, contactNumber);
    }
}
