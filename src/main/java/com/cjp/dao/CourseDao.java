package com.cjp.dao;


import com.cjp.entity.Course;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CourseDao {
    public Course findByName(String username);
    public int add(Course course);
    public int edit(Course course);
    public int delete(String name);

    public List<Course> findAll();
    public List<Course> findList(Map<String, Object> queryMap);
    public int getTotal(Map<String, Object> queryMap);

}
