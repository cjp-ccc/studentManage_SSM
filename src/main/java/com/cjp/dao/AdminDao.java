package com.cjp.dao;

import com.cjp.entity.Admin;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AdminDao {
    /**
     * 根据用户名查用户
     * @param name
     * @return
     */
    public Admin findByAdminName(String name);

    /**
     * 添加用户
     * @param admin
     * @return
     */
    public int add(Admin admin);

    /**
     * 查询用户列表
     */
    public List<Admin> findList(Map<String,Object> queryMap);

    public int getTotal(Map<String,Object> queryMap);

    public int edit(Admin admin);

    public int delete(String ids);

}
