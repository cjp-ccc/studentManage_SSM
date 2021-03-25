package com.cjp.dao;

import com.cjp.entity.OptionalCourse;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OptionalCourseDao {
    public OptionalCourse findByName(String username);
    public int add(OptionalCourse course);
    public int edit(OptionalCourse course);
    public int delete(String name);

    public List<OptionalCourse> findAll();
    public List<OptionalCourse> findList(Map<String, Object> queryMap);
    public int getTotal(Map<String, Object> queryMap);

}
