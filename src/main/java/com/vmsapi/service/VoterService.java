package com.vmsapi.service;

import com.vmsapi.model.Voter;
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
        return voterRepository.findAll();
    }

    public List<Voter> getVotersByDoorNo(String doorNo) {
        return voterRepository.findByDoorNo(doorNo);
    }
}
