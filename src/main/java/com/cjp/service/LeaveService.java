package com.cjp.service;

import com.cjp.entity.Academy;
import com.cjp.entity.Leave;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface LeaveService {
    public int add(Leave clazz);
    public int edit(Leave clazz);
    public int delete(String ids);
    public List<Leave> findList(Map<String, Object> queryMap);
    public List<Leave> findAll();
    public int getTotal(Map<String, Object> queryMap);
    public int check(Leave clazz);
}
