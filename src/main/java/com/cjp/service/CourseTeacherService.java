package com.cjp.service;

import com.cjp.entity.Course;
import com.cjp.entity.CourseTeacher;
import com.cjp.entity.Teacher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CourseTeacherService {
    public int add(CourseTeacher course);
    public int edit(CourseTeacher course);
    public int delete(String name);

    public List<CourseTeacher> findAll();
    public List<CourseTeacher> findList(Map<String, Object> queryMap);
    public int getTotal(Map<String, Object> queryMap);
}
