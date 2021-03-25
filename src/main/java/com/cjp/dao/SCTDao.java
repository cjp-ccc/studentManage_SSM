package com.cjp.dao;

import com.cjp.entity.SelectedCourse;
import com.cjp.entity.StudentCourseTeacher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SCTDao {
    public List<StudentCourseTeacher> findAll();
    public List<StudentCourseTeacher> findList(Map<String, Object> queryMap);

    public int getTotal(Map<String, Object> queryMap);
}
