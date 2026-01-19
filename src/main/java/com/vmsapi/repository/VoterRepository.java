package com.vmsapi.repository;

import com.vmsapi.model.Voter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VoterRepository extends JpaRepository<Voter, Integer> {
    List<Voter> findByDoorNo(String doorNo);
}
