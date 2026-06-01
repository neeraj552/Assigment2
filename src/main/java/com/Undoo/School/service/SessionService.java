package com.Undoo.School.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.stereotype.Service;

import com.Undoo.School.dto.CreateSessionRequest;
import com.Undoo.School.entity.Offering;
import com.Undoo.School.entity.Session;
import com.Undoo.School.repository.OfferingRepository;
import com.Undoo.School.repository.SessionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final OfferingRepository offeringRepository;

    public Session addSession(Long offeringId, CreateSessionRequest request){

         Offering offering =
                offeringRepository.findById(offeringId)
                        .orElseThrow();

        ZoneId zone =
                ZoneId.of(request.getTimezone());

        Instant startUtc =
                LocalDateTime.parse(request.getStartTime())
                        .atZone(zone)
                        .toInstant();

        Instant endUtc =
                LocalDateTime.parse(request.getEndTime())
                        .atZone(zone)
                        .toInstant();

        Session session = Session.builder()
                .offering(offering)
                .teacher(offering.getTeacher())
                .startTime(startUtc)
                .endTime(endUtc)
                .build();

        return sessionRepository.save(session);
    }

}
