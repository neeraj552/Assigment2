package com.Undoo.School.dto;

import lombok.Data;

@Data
public class CreateSessionRequest {
    private String startTime;
    private String endTime;
    private String timezone;

}
