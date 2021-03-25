package com.cjp.service.impl;

import com.cjp.dao.OptionalCourseDao;
import com.cjp.entity.OptionalCourse;
import com.cjp.service.OptionalCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OptionalCourseServiceImpl implements OptionalCourseService {

    @Autowired
    private OptionalCourseDao optionalCourseDao;

    @Override
    public int add(OptionalCourse course) {
        return optionalCourseDao.add(course);
    }

    @Override
    public int edit(OptionalCourse course) {
        return optionalCourseDao.edit(course);
    }

    @Override
    public int delete(String username) {
        return optionalCourseDao.delete(username);
    }

    @Override
    public List<OptionalCourse> findAll() {
        return optionalCourseDao.findAll();
    }

    @Override
    public List<OptionalCourse> findList(Map<String, Object> queryMap) {
        return optionalCourseDao.findList(queryMap);
    }

    @Override
    public int getTotal(Map<String, Object> queryMap) {
        return optionalCourseDao.getTotal(queryMap);
    }

    @Override
    public OptionalCourse findByName(String username) {
        return optionalCourseDao.findByName(username);
    }
}
