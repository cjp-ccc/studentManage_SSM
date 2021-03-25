package com.cjp.service;

import com.cjp.entity.Student;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface StudentService {
    public int add(Student student);
    public int edit(Student student);
    public int delete(String username);

    public List<Student> findAll();
    public List<Student> findList(Map<String, Object> queryMap);
    public int getTotal(Map<String, Object> queryMap);
    public Student findByName(String username);
}
