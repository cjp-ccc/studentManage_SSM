package com.cjp.controller;


import com.cjp.entity.Admin;
import com.cjp.page.Page;

import com.cjp.service.impl.AdminServiceImpl;
import com.cjp.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理员控制器
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminServiceImpl adminService;


    /**
     * 用户管理列表
     * @param model
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView model){
        model.setViewName("user/user_list");
        return model;
    }

    /**
     * 添加用户
     * @param admin
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(Admin admin){
        Map<String,String> ret = new HashMap<>();

        if(admin == null){
            ret.put("type","error");
            ret.put("msg","数据绑定出错，请联系开发作者");
            return ret;
        }

        if(StringUtils.isEmpty(admin.getName())){
            ret.put("type","error");
            ret.put("msg","用户名不能为空");
            return ret;
        }

        if(StringUtils.isEmpty(admin.getPassword())){
            ret.put("type","error");
            ret.put("msg","密码不能为空");
            return ret;
        }

        Admin exitUser = adminService.findByAdminName(admin.getName());
        if(exitUser!=null){
            ret.put("type","error");
            ret.put("msg","用户名已经存在");
            return ret;
        }

        if(adminService.add(admin) <= 0){
            ret.put("type","error");
            ret.put("msg","添加失败");
            return ret;
        }

        ret.put("type","success");
        ret.put("msg","添加成功");
        return ret;
    }

    /**
     * 查询用户列表
     */
    @RequestMapping(value = "/get_list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(
            @RequestParam(value = "name" ,required = false,defaultValue = "")String username,
            Page page
    ){
        Map<String,Object> ret = new HashMap<>();
        Map<String,Object> queryMap = new HashMap<>();

        queryMap.put("name","%"+username+"%");
        queryMap.put("offset",page.getOffset());
        queryMap.put("pageSize",page.getRows());
        ret.put("rows",adminService.findList(queryMap));

        ret.put("total",adminService.getTotal(queryMap));

        return ret;

    }

    /**
     * 修改用户
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> edit(Admin admin){
        Map<String,String> ret = new HashMap<>();

        if(admin == null){
            ret.put("type","error");
            ret.put("msg","数据绑定出错，请联系开发作者");
            return ret;
        }

        if(StringUtils.isEmpty(admin.getName())){
            ret.put("type","error");
            ret.put("msg","用户名不能为空");
            return ret;
        }

        if(StringUtils.isEmpty(admin.getPassword())){
            ret.put("type","error");
            ret.put("msg","密码不能为空");
            return ret;
        }

        Admin exitUser = adminService.findByAdminName(admin.getName());
        if(exitUser!=null){
            if(admin.getId() != exitUser.getId()){
                ret.put("type","error");
                ret.put("msg","用户名已经存在，请重新修改");
                return ret;
            }
        }

        if(adminService.edit(admin) <= 0){
            ret.put("type","error");
            ret.put("msg","修改失败");
            return ret;
        }

        ret.put("type","success");
        ret.put("msg","修改成功");
        return ret;
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> delete(
            @RequestParam(value = "ids[]",required = true) Integer[] ids
    ){
        Map<String,String> ret = new HashMap<>();
        if(ids == null){
            ret.put("type","error");
            ret.put("msg","请选择要删除的数据");
            return ret;
        }

        String idsString = StringUtil.joinString(Arrays.asList(ids),",");

        if(adminService.delete(idsString) <= 0){
            ret.put("type","error");
            ret.put("msg","删除失败");
            return ret;
        }

        ret.put("type","success");
        ret.put("msg","删除成功");
        return ret;
    }
}
