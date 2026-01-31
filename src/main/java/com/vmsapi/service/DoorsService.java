package com.vmsapi.service;

import com.vmsapi.model.DoorRow;
import com.vmsapi.repository.DoorsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoorsService {
    private final DoorsRepository repo;

    public DoorsService(DoorsRepository repo) {
        this.repo = repo;
    }

    public List<DoorRow> getAllDoors() {
        List<Object[]> raw = repo.findAllDoorsRaw();
        return mapRawToDoorRows(raw);
    }

    public List<DoorRow> getDoorsForIncharge(String incharge) {
        List<Object[]> raw = repo.findDoorsRawByIncharge(incharge);
        return mapRawToDoorRows(raw);
    }

    private List<DoorRow> mapRawToDoorRows(List<Object[]> raw) {
        List<DoorRow> out = new ArrayList<>();
        for (Object[] r : raw) {
            DoorRow d = new DoorRow();
            // expected columns: house_total, incharge, comments, door_no, visited, house_order, status
            d.setHouseTotal(r[0] == null ? null : ((Number) r[0]).intValue());
            d.setIncharge(r[1] == null ? null : r[1].toString());
            d.setComments(r[2] == null ? null : r[2].toString());
            d.setDoorNo(r[3] == null ? null : r[3].toString());
            d.setVisited(r[4] == null ? null : r[4].toString());
            d.setHouseOrder(r[5] == null ? null : ((Number) r[5]).intValue());
            d.setStatus(r[6] == null ? null : r[6].toString());
            out.add(d);
        }
        return out;
    }

    @Transactional
    public void updateStatus(String doorNo, String status) {
        repo.upsertStatusByDoorNo(doorNo, status);
    }

    @Transactional
    public void updateVisited(String doorNo, String visited) {
        repo.updateVisitedByDoorNo(doorNo, visited);
    }

    @Transactional
    public void updateComments(String doorNo, String comments) {
        repo.updateCommentsByDoorNo(doorNo, comments);
    }

    @Transactional
    public void updateVisitedAndComments(String doorNo, String visited, String comments) {
        repo.updateVisitedAndCommentsByDoorNo(doorNo, visited, comments);
    }
}
