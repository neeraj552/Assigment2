package com.Undoo.School.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Undoo.School.dto.BookingRequest;
import com.Undoo.School.entity.Booking;
import com.Undoo.School.entity.Offering;
import com.Undoo.School.repository.OfferingRepository;
import com.Undoo.School.service.BookingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/parent")
@RequiredArgsConstructor
public class ParentController {
    private final OfferingRepository offeringRepository;
    private final BookingService bookingService;

    @GetMapping("/offerings")
    public ResponseEntity<List<Offering>> getOffering(){
        return ResponseEntity.ok(offeringRepository.findAll());
    }
    @PostMapping("/book")
public ResponseEntity<Booking> bookOffering(
        @RequestBody BookingRequest request) {

    return ResponseEntity.ok(
            bookingService.bookOffering(
                    request.getParentId(),
                    request.getOfferingId()
            ));
}
@GetMapping("/{parentId}/bookings")
public ResponseEntity<List<Booking>> getBooking(@PathVariable Long parentId){
    return ResponseEntity.ok(bookingService.getBookings(parentId));
}

}
