package com.cjp.service.impl;

import com.cjp.dao.GradeDao;
import com.cjp.dao.OptionalCourseDao;
import com.cjp.entity.Grade;
import com.cjp.entity.OptionalCourse;
import com.cjp.entity.SelectedCourse;
import com.cjp.service.GradeService;
import com.cjp.service.OptionalCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GradeServiceImpl implements GradeService {

    @Autowired
    private GradeDao optionalCourseDao;

    @Override
    public int add(Grade course) {
        return optionalCourseDao.add(course);
    }

    @Override
    public int edit(Grade course) {
        return optionalCourseDao.edit(course);
    }

    @Override
    public int delete(String username) {
        return optionalCourseDao.delete(username);
    }

    @Override
    public List<Grade> findAll() {
        return optionalCourseDao.findAll();
    }

    @Override
    public List<Grade> findList(Map<String, Object> queryMap) {
        return optionalCourseDao.findList(queryMap);
    }

    @Override
    public int getTotal(Map<String, Object> queryMap) {
        return optionalCourseDao.getTotal(queryMap);
    }

    @Override
    public Grade findByName(String username) {
        return optionalCourseDao.findByName(username);
    }

    public List<SelectedCourse> findExit(Map<String, Object> queryMap){
        return optionalCourseDao.findExit(queryMap);
    }
}
