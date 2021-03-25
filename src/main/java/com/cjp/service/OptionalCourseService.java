package com.cjp.service;

import com.cjp.entity.OptionalCourse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface OptionalCourseService {
    public int add(OptionalCourse course);
    public int edit(OptionalCourse course);
    public int delete(String username);

    public List<OptionalCourse> findAll();
    public List<OptionalCourse> findList(Map<String, Object> queryMap);
    public int getTotal(Map<String, Object> queryMap);
    public OptionalCourse findByName(String username);
}
