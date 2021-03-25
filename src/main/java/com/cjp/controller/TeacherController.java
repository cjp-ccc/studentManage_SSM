package com.cjp.controller;

import com.cjp.entity.Clazz;
import com.cjp.entity.Teacher;
import com.cjp.page.Page;
import com.cjp.service.impl.ClazzServiceImpl;
import com.cjp.service.impl.TeacherServiceImpl;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

//    @Autowired
//    private ClazzServiceImpl clazzService;

    @Autowired
    private TeacherServiceImpl teacherService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView model){
        model.setViewName("teacher/teacher_list");
//        List<Clazz> findAll = clazzService.findAll();
//        model.addObject("clazzList",findAll);
//        model.addObject("clazzListJson", JSONArray.fromObject(findAll));
        return model;
    }


    /**
     * 添加学生
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(Teacher teacher){
        Map<String,String> ret = new HashMap<>();

        if(StringUtils.isEmpty(teacher.getName())){
            ret.put("type","error");
            ret.put("msg","学生姓名不能为空");
            return ret;
        }

//        if(StringUtils.isEmpty(teacher.getPassword())){
//            ret.put("type","error");
//            ret.put("msg","密码不能为空");
//            return ret;
//        }
//
//        if(teacher.getClazzId() == null){
//            ret.put("type","error");
//            ret.put("msg","请选择所属班级");
//            return ret;
//        }

//        if(isExist(teacher.getName(),null)){
//            ret.put("type","error");
//            ret.put("msg","该姓名已经存在");
//            return ret;
//        }
//        teacher.setSn(StringUtil.generateSn("S",""));
        teacher.setTn(StringUtil.generateSn("T",""));

        if(teacherService.add(teacher) <= 0){
            ret.put("type","error");
            ret.put("msg","添加失败");
            return  ret;
        }

        ret.put("type","success");
        ret.put("msg","添加成功");
        return ret;
    }

    /**
     * 修改老师
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> edit(Teacher teacher){
        Map<String,String> ret = new HashMap<>();

        if(StringUtils.isEmpty(teacher.getName())){
            ret.put("type","error");
            ret.put("msg","学生姓名不能为空");
            return ret;
        }

//        if(StringUtils.isEmpty(teacher.getPassword())){
//            ret.put("type","error");
//            ret.put("msg","密码不能为空");
//            return ret;
//        }

//        if(teacher.getClazzId() == null){
//            ret.put("type","error");
//            ret.put("msg","请选择所属班级");
//            return ret;
//        }

//        if(isExist(teacher.getName(),teacher.getId())){
//            ret.put("type","error");
//            ret.put("msg","该姓名已经存在");
//            return ret;
//        }
        teacher.setTn(StringUtil.generateSn("T",""));

        if(teacherService.edit(teacher) <= 0){
            ret.put("type","error");
            ret.put("msg","修改失败");
            return  ret;
        }

        ret.put("type","success");
        ret.put("msg","修改成功");
        return ret;
    }

    /**
     * 获取学生列表
     * @param name
     * @param
     * @param page
     * @return
     */
    //前端那边的异步请求的/get_list路径
    @RequestMapping(value = "/get_list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> findList(
            @RequestParam(value = "name" ,required = false,defaultValue = "")String name,
            HttpServletRequest request,
            Page page
    ){
       Map<String,Object> ret = new HashMap<>();
       Map<String,Object> queryMap = new HashMap<>();

       queryMap.put("name","%"+name+"%");

       Object attribute = request.getSession().getAttribute("userType");


        System.out.println(attribute);


       queryMap.put("offset",page.getOffset());
       queryMap.put("pageSize",page.getRows());
        ret.put("rows",teacherService.findList(queryMap));
       ret.put("total",teacherService.getTotal(queryMap));
       return ret;
    }

    /**
     * 判断学生姓名是否存在
     * @param name
     * @param id
     * @return
     */
    private boolean isExist(String name,Integer id){
        Teacher teacher = teacherService.findByName(name);
        if(teacher != null){
            if(id == null){
                return true;
            }

            if(teacher.getId().intValue() != id.intValue()){
                return true;
            }
        }

        return false;
    }


    /**
     * 删除学生
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

            if(teacherService.delete(idsString) <= 0){
                ret.put("type","error");
                ret.put("msg","删除学生失败");
                return ret;
            }
        } catch (Exception e) {
            ret.put("type","error");
            ret.put("msg","该学生下存在其他信息，请勿删除");
            return ret;
        }

        ret.put("type","success");
        ret.put("msg","删除成功");
        return ret;
    }
}

