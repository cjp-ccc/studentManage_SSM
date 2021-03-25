package com.cjp.service.impl;

import com.cjp.dao.StudentDao;
import com.cjp.entity.Student;
import com.cjp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentDao;

    @Override
    public int add(Student student) {
        return studentDao.add(student);
    }

    @Override
    public int edit(Student student) {
        return studentDao.edit(student);
    }

    @Override
    public int delete(String username) {
        return studentDao.delete(username);
    }

    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    @Override
    public List<Student> findList(Map<String, Object> queryMap) {
        return studentDao.findList(queryMap);
    }

    @Override
    public int getTotal(Map<String, Object> queryMap) {
        return studentDao.getTotal(queryMap);
    }

    @Override
    public Student findByName(String username) {
        return studentDao.findByName(username);
    }
}
