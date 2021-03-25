package com.cjp.service.impl;

import com.cjp.dao.AdminDao;
import com.cjp.entity.Admin;
import com.cjp.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Override
    public Admin findByAdminName(String name) {
        return adminDao.findByAdminName(name);
    }

    @Override
    public int add(Admin admin) {
        return adminDao.add(admin);
    }

    @Override
    public List<Admin> findList(Map<String, Object> queryMap) {
        return adminDao.findList(queryMap);
    }

    @Override
    public int getTotal(Map<String, Object> queryMap) {
        return adminDao.getTotal(queryMap);
    }

    @Override
    public int edit(Admin admin) {
        return adminDao.edit(admin);
    }

    @Override
    public int delete(String ids) {
        return adminDao.delete(ids);
    }
}
