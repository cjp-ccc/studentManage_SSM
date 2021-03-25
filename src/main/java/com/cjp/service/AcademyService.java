package com.cjp.service;

import com.cjp.entity.Academy;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface AcademyService {
    public int add(Academy academy);
    public int edit(Academy academy);
    public int delete(String ids);
    public List<Academy> findList(Map<String, Object> queryMap);
    public int getTotal(Map<String, Object> queryMap);
    public List<Academy> findAll();
}
