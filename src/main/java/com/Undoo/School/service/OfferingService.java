package com.Undoo.School.service;

import org.springframework.stereotype.Service;

import com.Undoo.School.dto.CreateOfferingRequest;
import com.Undoo.School.entity.Course;
import com.Undoo.School.entity.Offering;
import com.Undoo.School.entity.Teacher;
import com.Undoo.School.repository.CourseRepository;
import com.Undoo.School.repository.OfferingRepository;
import com.Undoo.School.repository.TeacherRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OfferingService {
    private final OfferingRepository offeringRepository;
    private final TeacherRepository teacherRepository;
    private final CourseRepository  courseRepository;

    public Offering createOffering(CreateOfferingRequest request){
        System.out.println("Teacher Id = " + request.getTeacherId());
    System.out.println("Course Id = " + request.getCourseId());
    System.out.println("Title = " + request.getTitle());
        Teacher teacher = teacherRepository
               .findById(request.getTeacherId())
               .orElseThrow(
                () -> new RuntimeException("Teacher Not Found"));
        
        Course course = courseRepository
               .findById(request.getCourseId())
               .orElseThrow(
                () -> new RuntimeException("Course Not Found"));

        Offering offering = Offering.builder()
               .title(request.getTitle())
               .teacher(teacher)
               .course(course)
               .build();
        
        return offeringRepository.save(offering);
    }

}
