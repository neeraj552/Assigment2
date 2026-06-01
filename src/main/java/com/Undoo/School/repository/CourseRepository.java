package com.Undoo.School.repository;

import org.hibernate.boot.models.JpaAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Undoo.School.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository< Course, Long>{

}
