package com.Undoo.School.service;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Undoo.School.entity.Booking;
import com.Undoo.School.entity.Offering;
import com.Undoo.School.entity.Parent;
import com.Undoo.School.entity.Session;
import com.Undoo.School.exception.TimeConflictException;
import com.Undoo.School.repository.BookingRepository;
import com.Undoo.School.repository.OfferingRepository;
import com.Undoo.School.repository.ParentRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ParentRepository parentRepository;
    private final OfferingRepository offeringRepository;


    @Transactional
    public Booking bookOffering(
            Long parentId,
            Long offeringId) {

        Parent parent = parentRepository
        .lockParent(parentId)
        .orElseThrow(() ->
                new RuntimeException("Parent not found"));

        Offering offering = offeringRepository
                .findById(offeringId)
                .orElseThrow(() ->
                        new RuntimeException("Offering not found"));

        if (bookingRepository.existsByParent_IdAndOffering_Id(
                parentId,
                offeringId)) {

            throw new TimeConflictException(
                    "Offering already booked");
        }

        List<Booking> existingBookings =
                bookingRepository.findByParentId(parentId);


        for (Booking existingBooking : existingBookings) {

            List<Session> existingSessions =
                    existingBooking.getOffering().getSessions();

            List<Session> newSessions =
                    offering.getSessions();

            for (Session existingSession : existingSessions) {

                for (Session newSession : newSessions) {

                    boolean overlap =
                            existingSession.getStartTime()
                                    .isBefore(newSession.getEndTime())
                            &&
                            existingSession.getEndTime()
                                    .isAfter(newSession.getStartTime());

                    if (overlap) {
                        throw new TimeConflictException(
                                "Time conflict detected");
                    }
                }
            }
        }

        Booking booking = Booking.builder()
                .parent(parent)
                .offering(offering)
                .createdAt(Instant.now())
                .build();

        return bookingRepository.save(booking);

        
    }
        public List<Booking> getBookings(Long parentId) {

            parentRepository.findById(parentId)
            .orElseThrow(() ->
                    new RuntimeException("Parent not found"));

        return bookingRepository.findByParentId(parentId);
        }
}