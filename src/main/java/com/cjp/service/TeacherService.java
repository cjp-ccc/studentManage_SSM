package com.cjp.service;

import com.cjp.entity.Teacher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface TeacherService {
    public int add(Teacher teacher);
    public int edit(Teacher teacher);
    public int delete(String username);

    public List<Teacher> findAll();
    public List<Teacher> findList(Map<String, Object> queryMap);
    public int getTotal(Map<String, Object> queryMap);
    public Teacher findByName(String username);
}
