package com.cjp.service.impl;

import com.cjp.dao.SCTDao;
import com.cjp.entity.StudentCourseTeacher;
import com.cjp.service.SCTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SCTServiceImpl implements SCTService {
    @Autowired
    private SCTDao sctDao;

    @Override
    public List<StudentCourseTeacher> findAll() {
        return sctDao.findAll();
    }

    @Override
    public List<StudentCourseTeacher> findList(Map<String, Object> queryMap) {
        return sctDao.findList(queryMap);
    }

    @Override
    public int getTotal(Map<String, Object> queryMap) {
        return sctDao.getTotal(queryMap);
    }
}
