package com.Undoo.School.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Undoo.School.entity.Parent;


@Repository
public interface ParentRepository extends JpaRepository<Parent, Long>{

}
