package com.vmsapi.service;

import com.vmsapi.repository.DoorsRepository;
import com.vmsapi.repository.VoterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InchargeService {

    private final VoterRepository voterRepository;
    private final DoorsRepository doorsRepository;

    public InchargeService(VoterRepository voterRepository, DoorsRepository doorsRepository) {
        this.voterRepository = voterRepository;
        this.doorsRepository = doorsRepository;
    }

    @Transactional
    public void updateInchargeByDoorNo(String doorNo, String incharge) {
        if (doorNo == null || doorNo.isBlank()) {
            throw new IllegalArgumentException("doorNo is required");
        }
        if (incharge == null) {
            incharge = "";
        }

        // Update voters_new table rows (all voters for that door)
        voterRepository.updateInchargeByDoorNo(doorNo, incharge);

        // Upsert into doors table so the door exists and its incharge is set
        int updated = doorsRepository.upsertInchargeByDoorNo(doorNo, incharge);
        if (updated < 1) {
            throw new IllegalStateException("Failed to update or insert door row for doorNo=" + doorNo);
        }
    }
}
