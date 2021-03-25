package com.cjp.service.impl;

import com.cjp.dao.CourseTeacherDao;

import com.cjp.entity.CourseTeacher;
import com.cjp.entity.Teacher;
import com.cjp.service.CourseTeacherService;
import com.cjp.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CourseTeacherServiceImpl implements CourseTeacherService {

    @Autowired
    private CourseTeacherDao teacherDao;

    @Override
    public int add(CourseTeacher teacher) {
        return teacherDao.add(teacher);
    }

    @Override
    public int edit(CourseTeacher teacher) {
        return teacherDao.edit(teacher);
    }

    @Override
    public int delete(String username) {
        return teacherDao.delete(username);
    }

    @Override
    public List<CourseTeacher> findAll() {
        return teacherDao.findAll();
    }

    @Override
    public List<CourseTeacher> findList(Map<String, Object> queryMap) {
        return teacherDao.findList(queryMap);
    }

    @Override
    public int getTotal(Map<String, Object> queryMap) {
        return teacherDao.getTotal(queryMap);
    }

}
