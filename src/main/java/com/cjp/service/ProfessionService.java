package com.cjp.service;


import com.cjp.entity.Profession;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ProfessionService {
    public int add(Profession profession);
    public int edit(Profession profession);
    public int delete(String ids);
    public List<Profession> findList(Map<String, Object> queryMap);
    public List<Profession> findAll();
    public int getTotal(Map<String, Object> queryMap);
}
