package com.cjp.service.impl;

import com.cjp.dao.TeacherDao;
import com.cjp.entity.Teacher;
import com.cjp.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherDao teacherDao;

    @Override
    public int add(Teacher teacher) {
        return teacherDao.add(teacher);
    }

    @Override
    public int edit(Teacher teacher) {
        return teacherDao.edit(teacher);
    }

    @Override
    public int delete(String username) {
        return teacherDao.delete(username);
    }

    @Override
    public List<Teacher> findAll() {
        return teacherDao.findAll();
    }

    @Override
    public List<Teacher> findList(Map<String, Object> queryMap) {
        return teacherDao.findList(queryMap);
    }

    @Override
    public int getTotal(Map<String, Object> queryMap) {
        return teacherDao.getTotal(queryMap);
    }

    @Override
    public Teacher findByName(String username) {
        return teacherDao.findByName(username);
    }
}
