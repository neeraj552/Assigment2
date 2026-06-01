package com.Undoo.School.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Undoo.School.dto.CreateOfferingRequest;
import com.Undoo.School.dto.CreateSessionRequest;
import com.Undoo.School.entity.Offering;
import com.Undoo.School.entity.Session;
import com.Undoo.School.service.OfferingService;
import com.Undoo.School.service.SessionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final OfferingService offeringService;
    private final SessionService sessionService;

    @PostMapping("/offerings")
    public ResponseEntity<Offering> createOffering(
            @RequestBody CreateOfferingRequest request) {

        return ResponseEntity.ok(
                offeringService.createOffering(request));
    }
    @PostMapping("/offerings/{offeringId}/sessions")
    public ResponseEntity<Session> addSession(@PathVariable Long offeringId, @RequestBody CreateSessionRequest request){
        return ResponseEntity.ok(
               sessionService.addSession(offeringId
                , request));
    }
}
