package com.cjp.dao;


import com.cjp.entity.Teacher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TeacherDao {
    public Teacher findByName(String username);
    public int add(Teacher teacher);
    public int edit(Teacher teacher);
    public int delete(String name);

    public List<Teacher> findAll();
    public List<Teacher> findList(Map<String, Object> queryMap);
    public int getTotal(Map<String, Object> queryMap);

}
