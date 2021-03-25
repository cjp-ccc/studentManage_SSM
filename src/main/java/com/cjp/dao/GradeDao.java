package com.cjp.dao;


import com.cjp.entity.Grade;
import com.cjp.entity.SelectedCourse;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface GradeDao {
    public int add(Grade course);
    public int edit(Grade course);
    public int delete(String name);

    public List<Grade> findAll();
    public List<Grade> findList(Map<String, Object> queryMap);

    public int getTotal(Map<String, Object> queryMap);
    public Grade findByName(String username);

    public List<SelectedCourse> findExit(Map<String, Object> queryMap);

}
