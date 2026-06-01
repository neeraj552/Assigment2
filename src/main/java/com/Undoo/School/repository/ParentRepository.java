package com.Undoo.School.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Undoo.School.entity.Parent;

import jakarta.persistence.LockModeType;

@Repository
public interface ParentRepository
        extends JpaRepository<Parent, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            SELECT p
            FROM Parent p
            WHERE p.id = :id
            """)
    Optional<Parent> lockParent(
            @Param("id") Long id);
}