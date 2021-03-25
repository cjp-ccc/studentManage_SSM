package com.cjp.dao;


import com.cjp.entity.CourseTeacher;
import com.cjp.entity.SelectedCourse;
import com.cjp.entity.StudentCourseTeacher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SelectedCourseDao {
    public int add(SelectedCourse course);
    public int edit(SelectedCourse course);
    public int delete(String name);

    public List<SelectedCourse> findAll();
    public List<SelectedCourse> findList(Map<String, Object> queryMap);

    public int getTotal(Map<String, Object> queryMap);

    public List<SelectedCourse> findExit(Map<String, Object> queryMap);

    public SelectedCourse findOne(Map<String,Object> queryMap);
    public List<SelectedCourse> findByStudentId(Map<String,Object> queryMap);
}
