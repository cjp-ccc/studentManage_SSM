package com.cjp.service;

import com.cjp.entity.Admin;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface AdminService {
    public Admin findByAdminName(String name);

    public int add(Admin user);

    public List<Admin> findList(Map<String,Object> queryMap);

    public int getTotal(Map<String,Object> queryMap);

    public int edit(Admin user);

    public int delete(String ids);
}
