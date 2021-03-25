package com.cjp.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class CourseTeacher implements Serializable {
    private Integer id;
    //前端写的是teacherId,你这里写的是teacher_id，细心一点
    private Integer teacherId;
    private Integer courseId;
}
