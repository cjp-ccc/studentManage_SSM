package com.cjp.service.impl;

import com.cjp.dao.LeaveDao;
import com.cjp.entity.Leave;
import com.cjp.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    private LeaveDao clazzDao;

    @Override
    public int add(Leave clazz) {
        return clazzDao.add(clazz);
    }

    @Override
    public int edit(Leave clazz) {
        return clazzDao.edit(clazz);
    }

    @Override
    public int delete(String ids) {
        return clazzDao.delete(ids);
    }

    @Override
    public List<Leave> findList(Map<String, Object> queryMap) {
        return clazzDao.findList(queryMap);
    }

    @Override
    public List<Leave> findAll() {
        return clazzDao.findAll();
    }

    @Override
    public int getTotal(Map<String, Object> queryMap) {
        return clazzDao.getTotal(queryMap);
    }


    public int check(Leave clazz){return clazzDao.check(clazz);}
}
