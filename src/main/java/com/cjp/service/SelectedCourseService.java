package com.cjp.service;


import com.cjp.entity.SelectedCourse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface SelectedCourseService {
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
