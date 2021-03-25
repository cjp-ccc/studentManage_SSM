package com.cjp.controller;

import com.cjp.entity.Academy;

import com.cjp.entity.Profession;
import com.cjp.page.Page;
import com.cjp.service.impl.AcademyServiceImpl;

import com.cjp.service.impl.ProfessionServiceImpl;
import com.cjp.utils.StringUtil;
import net.sf.json.JSONArray;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/profession")
public class ProfessionController {

    @Autowired
    private AcademyServiceImpl academyService;

    @Autowired
    private ProfessionServiceImpl professionService;


    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView model){
        model.setViewName("profession/profession_list");
        List<Academy> findAll = academyService.findAll();
        model.addObject("academyList",findAll);
        model.addObject("academyListJson", JSONArray.fromObject(findAll));
        return model;
    }

    @RequestMapping(value = "/get_list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(
            @RequestParam(value = "name",required = false,defaultValue = "") String name,
            @RequestParam(value = "academyId",required = false) Integer academyId,
            Page page
    ){
        Map<String,Object> ret = new HashMap<>();
        Map<String,Object> queryMap = new HashMap<>();

        queryMap.put("name","%"+name+"%");

        if (academyId != null){
            queryMap.put("academyId",academyId);
        }
        queryMap.put("offset",page.getOffset());
        queryMap.put("pageSize",page.getRows());

        ret.put("rows",professionService.findList(queryMap));
        ret.put("total",professionService.getTotal(queryMap));
        return ret;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(Profession profession){
        Map<String,String> ret = new HashMap<>();

        if(profession == null){
            ret.put("type","error");
            ret.put("msg","专业名称不能为空");
            return ret;
        }

        if(professionService.add(profession) <= 0){
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
    public Map<String,String> edit(Profession profession){
        Map<String,String> ret = new HashMap<>();

        if(StringUtils.isEmpty(profession.getName())){
            ret.put("type","error");
            ret.put("msg","专业名不能为空");
            return ret;
        }

        if(profession.getAcademyId() == null){
            ret.put("type","error");
            ret.put("msg","所属学院不能为空");
            return ret;
        }

        if(professionService.edit(profession)<=0){
            ret.put("type","error");
            ret.put("msg","修改失败");
            return ret;
        }

        ret.put("type","success");
        ret.put("msg","修改成功");
        return ret;
    }

    /**
     * 删除班级
     * @param ids
     * @return
     */
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

            if(professionService.delete(idsString) <= 0){
                ret.put("type","error");
                ret.put("msg","删除失败");
                return ret;
            }
        } catch (Exception e) {
            ret.put("type","error");
            ret.put("msg","该学院下存在专业信息，请勿删除");
            return ret;
        }

        ret.put("type","success");
        ret.put("msg","删除成功");
        return ret;
    }
}
