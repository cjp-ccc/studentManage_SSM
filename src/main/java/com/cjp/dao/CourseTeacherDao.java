package com.cjp.dao;


import com.cjp.entity.Course;
import com.cjp.entity.CourseTeacher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CourseTeacherDao {
    public int add(CourseTeacher course);
    public int edit(CourseTeacher course);
    public int delete(String name);

    public List<CourseTeacher> findAll();
    public List<CourseTeacher> findList(Map<String, Object> queryMap);
    public int getTotal(Map<String, Object> queryMap);

}
