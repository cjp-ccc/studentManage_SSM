package com.cjp.dao;


import com.cjp.entity.Attendance;
import com.cjp.entity.SelectedCourse;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AttendanceDao {
    public int add(Attendance course);
    public int edit(Attendance course);
    public int delete(String name);

    public List<Attendance> findAll();
    public List<Attendance> findList(Map<String, Object> queryMap);

    public int getTotal(Map<String, Object> queryMap);

    public List<Attendance> findExit(Map<String, Object> queryMap);

    public Attendance findOne(Map<String, Object> queryMap);
}
