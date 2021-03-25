package com.cjp.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class Attendance implements Serializable {
    private Integer id;
    private Integer studentId;
    private Integer optionalCourseId;
    private Date attendTime;
    private String attendDate;
}
