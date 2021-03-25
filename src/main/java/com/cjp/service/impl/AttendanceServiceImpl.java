package com.cjp.service.impl;

import com.cjp.dao.AttendanceDao;
import com.cjp.dao.SelectedCourseDao;
import com.cjp.entity.Attendance;
import com.cjp.entity.SelectedCourse;
import com.cjp.service.AttendanceService;
import com.cjp.service.SelectedCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceDao teacherDao;

    @Override
    public int add(Attendance teacher) {
        return teacherDao.add(teacher);
    }

    @Override
    public int edit(Attendance teacher) {
        return teacherDao.edit(teacher);
    }

    @Override
    public int delete(String username) {
        return teacherDao.delete(username);
    }

    @Override
    public List<Attendance> findAll() {
        return teacherDao.findAll();
    }

    @Override
    public List<Attendance> findList(Map<String, Object> queryMap) {
        return teacherDao.findList(queryMap);
    }

    @Override
    public int getTotal(Map<String, Object> queryMap) {
        return teacherDao.getTotal(queryMap);
    }

    public List<Attendance> findExit(Map<String, Object> queryMap){
        return teacherDao.findExit(queryMap);
    }


    @Override
    public Attendance findOne(Map<String, Object> queryMap) {
        return teacherDao.findOne(queryMap);
    }
}
