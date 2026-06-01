package com.Undoo.School.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Undoo.School.entity.Offering;

@Repository
public interface OfferingRepository extends JpaRepository<Offering, Long> {
    
}
