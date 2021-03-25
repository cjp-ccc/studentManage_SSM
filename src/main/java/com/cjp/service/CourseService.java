package com.cjp.service;

import com.cjp.entity.Course;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CourseService {
    public int add(Course course);
    public int edit(Course course);
    public int delete(String username);

    public List<Course> findAll();
    public List<Course> findList(Map<String, Object> queryMap);
    public int getTotal(Map<String, Object> queryMap);
    public Course findByName(String username);
}
