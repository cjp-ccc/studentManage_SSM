package com.cjp.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class StudentCourseTeacher implements Serializable {

    private Integer studentId;
    private Integer optionalCourseId;
    private Integer teacherId;
    private String studentName;
    private String studentSn;
    private String optionalCourseName;
    private String teacherName;
    private String optionalCourseDate;
    private String optionalCourseLocation;
}
