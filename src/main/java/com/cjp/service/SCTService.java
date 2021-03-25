package com.cjp.service;

import com.cjp.entity.StudentCourseTeacher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface SCTService {
    public List<StudentCourseTeacher> findAll();
    public List<StudentCourseTeacher> findList(Map<String, Object> queryMap);

    public int getTotal(Map<String, Object> queryMap);
}
