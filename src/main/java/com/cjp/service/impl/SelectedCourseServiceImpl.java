package com.cjp.service.impl;

import com.cjp.dao.CourseTeacherDao;
import com.cjp.dao.SelectedCourseDao;
import com.cjp.entity.CourseTeacher;
import com.cjp.entity.SelectedCourse;
import com.cjp.service.CourseTeacherService;
import com.cjp.service.SelectedCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SelectedCourseServiceImpl implements SelectedCourseService {

    @Autowired
    private SelectedCourseDao teacherDao;

    @Override
    public int add(SelectedCourse teacher) {
        return teacherDao.add(teacher);
    }

    @Override
    public int edit(SelectedCourse teacher) {
        return teacherDao.edit(teacher);
    }

    @Override
    public int delete(String username) {
        return teacherDao.delete(username);
    }

    @Override
    public List<SelectedCourse> findAll() {
        return teacherDao.findAll();
    }

    @Override
    public List<SelectedCourse> findList(Map<String, Object> queryMap) {
        return teacherDao.findList(queryMap);
    }

    @Override
    public int getTotal(Map<String, Object> queryMap) {
        return teacherDao.getTotal(queryMap);
    }

    public List<SelectedCourse> findExit(Map<String, Object> queryMap){
        return teacherDao.findExit(queryMap);
    }


    @Override
    public SelectedCourse findOne(Map<String, Object> queryMap) {
        return teacherDao.findOne(queryMap);
    }

    @Override
    public List<SelectedCourse> findByStudentId(Map<String, Object> queryMap) {
        return teacherDao.findByStudentId(queryMap);
    }
}
