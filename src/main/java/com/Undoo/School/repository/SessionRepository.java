package com.Undoo.School.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Undoo.School.entity.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

}
