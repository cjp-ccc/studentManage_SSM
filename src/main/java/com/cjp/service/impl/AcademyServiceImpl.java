package com.cjp.service.impl;

import com.cjp.dao.AcademyDao;

import com.cjp.entity.Academy;

import com.cjp.service.AcademyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AcademyServiceImpl implements AcademyService {

    @Autowired
    private AcademyDao academyDao;
    @Override

    public int add(Academy academy) {
        return academyDao.add(academy);
    }

    @Override
    public int edit(Academy academy) {
        return academyDao.edit(academy);
    }

    @Override
    public int delete(String ids) {
        return academyDao.delete(ids);
    }

    @Override
    public List<Academy> findList(Map<String, Object> queryMap) {
        return academyDao.findList(queryMap);
    }

    @Override
    public int getTotal(Map<String, Object> queryMap) {
        return academyDao.getTotal(queryMap);
    }

    @Override
    public List<Academy> findAll() {
        return academyDao.findAll();
    }
}
