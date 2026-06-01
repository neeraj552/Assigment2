package com.Undoo.School.dto;

import lombok.Data;

@Data
public class CreateOfferingRequest {
    private String title;
    private Long courseId;
    private Long teacherId;

}
