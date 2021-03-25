package com.cjp.service.impl;

import com.cjp.dao.CourseDao;
import com.cjp.entity.Course;
import com.cjp.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDao courseDao;

    @Override
    public int add(Course course) {
        return courseDao.add(course);
    }

    @Override
    public int edit(Course course) {
        return courseDao.edit(course);
    }

    @Override
    public int delete(String username) {
        return courseDao.delete(username);
    }

    @Override
    public List<Course> findAll() {
        return courseDao.findAll();
    }

    @Override
    public List<Course> findList(Map<String, Object> queryMap) {
        return courseDao.findList(queryMap);
    }

    @Override
    public int getTotal(Map<String, Object> queryMap) {
        return courseDao.getTotal(queryMap);
    }

    @Override
    public Course findByName(String username) {
        return courseDao.findByName(username);
    }
}
