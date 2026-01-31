package com.vmsapi.controller;

import com.vmsapi.model.DoorRow;
import com.vmsapi.model.UpdateDoorCommentsRequest;
import com.vmsapi.model.UpdateDoorStatusRequest;
import com.vmsapi.model.UpdateDoorVisitedCommentsRequest;
import com.vmsapi.model.UpdateDoorVisitedRequest;
import com.vmsapi.service.DoorsService;
import com.vmsapi.security.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/vi/doors")
public class DoorsController {
    private final DoorsService doorsService;
    private final Logger logger = LoggerFactory.getLogger(DoorsController.class);

    public DoorsController(DoorsService doorsService) {
        this.doorsService = doorsService;
    }

    @GetMapping
    public List<DoorRow> listDoors(HttpServletRequest request) {
        String role = SecurityUtil.getCurrentRole(request);
        String username = SecurityUtil.getCurrentUsername();

        if ("incharge".equals(role) && username != null) {
            return doorsService.getDoorsForIncharge(username);
        }
        return doorsService.getAllDoors();
    }

    @PostMapping("/update-status")
    public ResponseEntity<Void> updateStatus(@RequestBody UpdateDoorStatusRequest req) {
        String doorNo = req.getDoorNo();
        String status = req.getStatus();
        logger.debug("update-status payload received: doorNo='{}', status='{}'", doorNo, status);
        if (doorNo != null && doorNo.endsWith("/")) {
            doorNo = doorNo.substring(0, doorNo.length() - 1);
        }
        doorsService.updateStatus(doorNo, status);
        logger.debug("update-status applied for doorNo='{}' with status='{}'", doorNo, status);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/update-visited")
    public ResponseEntity<Void> updateVisited(@RequestBody UpdateDoorVisitedRequest req) {
        doorsService.updateVisited(req.getDoorNo(), req.getVisited());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/update-comments")
    public ResponseEntity<Void> updateComments(@RequestBody UpdateDoorCommentsRequest req) {
        doorsService.updateComments(req.getDoorNo(), req.getComments());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/update-visited-comments")
    public ResponseEntity<Void> updateVisitedAndComments(@RequestBody UpdateDoorVisitedCommentsRequest req) {
        doorsService.updateVisitedAndComments(req.getDoorNo(), req.getVisited(), req.getComments());
        return ResponseEntity.noContent().build();
    }
}
