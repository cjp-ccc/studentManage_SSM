package com.cjp.service;

import com.cjp.entity.Grade;
import com.cjp.entity.OptionalCourse;
import com.cjp.entity.SelectedCourse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface GradeService {
    public int add(Grade course);
    public int edit(Grade course);
    public int delete(String username);

    public List<Grade> findAll();
    public List<Grade> findList(Map<String, Object> queryMap);
    public int getTotal(Map<String, Object> queryMap);
    public Grade findByName(String username);

    public List<SelectedCourse> findExit(Map<String, Object> queryMap);
}
