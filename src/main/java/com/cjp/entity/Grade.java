package com.cjp.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Grade implements Serializable {
    private Integer id;
    private Integer score;
    private Float gpa;
    private Integer studentId;
    private Integer optionalCourseId;
    private Student student;
    private OptionalCourse optionalCourse;
}
