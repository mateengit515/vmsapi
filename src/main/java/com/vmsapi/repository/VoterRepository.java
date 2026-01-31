package com.vmsapi.repository;

import com.vmsapi.model.Voter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface VoterRepository extends JpaRepository<Voter, Integer> {
    List<Voter> findByDoorNo(String doorNo);

    @Transactional
    @Modifying
    @Query("UPDATE Voter v SET v.incharge = :incharge WHERE v.epicNo = :epicNo")
    void updateInchargeByEpicNo(String epicNo, String incharge);

    @Transactional
    @Modifying
    @Query("UPDATE Voter v SET v.incharge = :incharge WHERE v.doorNo = :doorNo")
    void updateInchargeByDoorNo(@Param("doorNo") String doorNo, @Param("incharge") String incharge);

    @Query(value = "SELECT door_no as doorNo, house_total as houseTotal, incharge as incharge, house_order as houseOrder FROM ( SELECT door_no, house_total, incharge, house_order, ROW_NUMBER() OVER (PARTITION BY door_no ORDER BY house_order ASC) AS rn FROM voters_new ) t WHERE rn = 1 ORDER BY house_order ASC, door_no ASC", nativeQuery = true)
    List<DoorNoSummary> findDoorNoSummary();

    @Query(value = "SELECT door_no as doorNo, house_total as houseTotal, incharge as incharge, house_order as houseOrder FROM ( SELECT door_no, house_total, incharge, house_order, ROW_NUMBER() OVER (PARTITION BY door_no ORDER BY house_order ASC) AS rn FROM voters_new ) t WHERE rn = 1 AND incharge = :incharge ORDER BY house_order ASC, door_no ASC", nativeQuery = true)
    List<DoorNoSummary> findDoorNoSummaryByIncharge(@Param("incharge") String incharge);

    @Query("SELECT v FROM Voter v WHERE v.doorNo LIKE :doorNoPattern")
    List<Voter> findByDoorNoLike(@org.springframework.data.repository.query.Param("doorNoPattern") String doorNoPattern);

    @Transactional
    @Modifying
    @Query("UPDATE Voter v SET v.status = :status WHERE v.epicNo = :epicNo")
    void updateStatusByEpicNo(@Param("epicNo") String epicNo, @Param("status") String status);

    @Query("SELECT v FROM Voter v ORDER BY v.serialNo ASC")
    List<Voter> findAllOrderBySerialNo();

    @Transactional
    @Modifying
    @Query("UPDATE Voter v SET v.voted = :voted WHERE v.epicNo = :epicNo")
    void updateVotedByEpicNo(@Param("epicNo") String epicNo, @Param("voted") String voted);

    @Transactional
    @Modifying
    @Query("UPDATE Voter v SET v.contactNumber = :contactNumber WHERE v.epicNo = :epicNo")
    void updateContactNumberByEpicNo(@Param("epicNo") String epicNo, @Param("contactNumber") String contactNumber);
}
