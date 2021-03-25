package com.cjp.service.impl;


import com.cjp.dao.ProfessionDao;
import com.cjp.entity.Profession;
import com.cjp.service.ProfessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProfessionServiceImpl implements ProfessionService {

    @Autowired
    private ProfessionDao professionDao;

    @Override
    public int add(Profession profession) {
        return professionDao.add(profession);
    }

    @Override
    public int edit(Profession profession) {
        return professionDao.edit(profession);
    }

    @Override
    public int delete(String ids) {
        return professionDao.delete(ids);
    }

    @Override
    public List<Profession> findList(Map<String, Object> queryMap) {
        return professionDao.findList(queryMap);
    }

    @Override
    public List<Profession> findAll() {
        return professionDao.findAll();
    }

    @Override
    public int getTotal(Map<String, Object> queryMap) {
        return professionDao.getTotal(queryMap);
    }
}
