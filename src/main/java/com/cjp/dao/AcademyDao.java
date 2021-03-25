package com.cjp.dao;

import com.cjp.entity.Academy;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AcademyDao {
    public int add(Academy academy);
    public int edit(Academy academy);
    public int delete(String ids);
    public List<Academy> findList(Map<String, Object> queryMap);
    public int getTotal(Map<String, Object> queryMap);
    public List<Academy> findAll();

}
