package com.cjp.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 可选课程
 */
@Data
public class OptionalCourse implements Serializable {
    private Integer id;  //主键
    private Integer teacherId;  //任课老师id
    private String name;   //课程名
    private String courseDate;  //上课时间
    private String courseLocation;   //上课地点
    private Integer selectedNum;   //已选人数
    private Integer maxNum;   //最大选课人数
}
