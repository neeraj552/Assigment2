package com.Undoo.School.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Undoo.School.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    List<Booking> findByParentId(Long parentId);
    boolean existsByParent_IdAndOffering_Id(
        Long parentId,
        Long offeringId);

}
