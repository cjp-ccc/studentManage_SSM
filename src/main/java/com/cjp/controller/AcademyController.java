package com.cjp.controller;

import com.cjp.entity.Academy;
import com.cjp.page.Page;
import com.cjp.service.impl.AcademyServiceImpl;
import com.cjp.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
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



@Controller
@RequestMapping("/academy")
public class AcademyController {

    @Autowired
    private AcademyServiceImpl academyService;

    /**
     * 年级列表
     * @param model
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView model){
        model.setViewName("academy/academy_list");
        return model;
    }


    @RequestMapping(value = "/get_list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(
            @RequestParam(value = "name",required = false,defaultValue = "") String name,
            Page page
    ){
        Map<String,Object> ret = new HashMap<>();
        Map<String,Object> queryMap = new HashMap<>();

        queryMap.put("name","%"+name+"%");
        queryMap.put("offset",page.getOffset());
        queryMap.put("pageSize",page.getRows());

        ret.put("rows",academyService.findList(queryMap));
        ret.put("total",academyService.getTotal(queryMap));
        return ret;
    }

    /**
     * 添加功能
     * @param academy
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(Academy academy){
        Map<String,String> ret = new HashMap<>();

        if(StringUtils.isEmpty(academy.getName())){
            ret.put("type","error");
            ret.put("msg","班级名称不能为空");
            return ret;
        }

        if(academyService.add(academy) <= 0){
            ret.put("type","error");
            ret.put("msg","添加失败");
            return ret;
        }

        ret.put("type","success");
        ret.put("msg","添加成功");
        return ret;
    }


    /**
     * 修改功能
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> edit(Academy academy){
        Map<String,String> ret = new HashMap<>();

        if(StringUtils.isEmpty(academy.getName())){
            ret.put("type","error");
            ret.put("msg","年级名不能为空");
            return ret;
        }

        if(academyService.edit(academy)<=0){
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
        if(ids == null || ids.length == 0){
            ret.put("type","error");
            ret.put("msg","请选择要删除的数据");
            return ret;
        }

        try {
            String idsString = StringUtil.joinString(Arrays.asList(ids),",");

            if(academyService.delete(idsString) <= 0){
                ret.put("type","error");
                ret.put("msg","删除失败");
                return ret;
            }
        } catch (Exception e) {
            ret.put("type","error");
            ret.put("msg","该年级下存在班级信息，请勿删除");
            return ret;
        }



        ret.put("type","success");
        ret.put("msg","删除成功");
        return ret;
    }
}
