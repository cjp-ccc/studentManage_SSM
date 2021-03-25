package com.cjp.dao;

import com.cjp.entity.Student;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface StudentDao {
    public Student findByName(String username);
    public int add(Student student);
    public int edit(Student student);
    public int delete(String name);

    public List<Student> findAll();
    public List<Student> findList(Map<String, Object> queryMap);
    public int getTotal(Map<String, Object> queryMap);

}
