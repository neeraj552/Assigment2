package com.Undoo.School.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Undoo.School.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking,Long> {

}
