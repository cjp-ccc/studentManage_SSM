package com.cjp.controller;

import com.cjp.entity.Clazz;
import com.cjp.entity.Student;

import com.cjp.page.Page;

import com.cjp.service.impl.ClazzServiceImpl;
import com.cjp.service.impl.StudentServiceImpl;
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

import java.util.*;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private ClazzServiceImpl clazzService;

    @Autowired
    private StudentServiceImpl studentService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView model){
        model.setViewName("student/student_list");
        List<Clazz> findAll = clazzService.findAll();
        model.addObject("clazzList",findAll);
        model.addObject("clazzListJson", JSONArray.fromObject(findAll));
        return model;
    }


    /**
     * 添加学生
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(Student student){
        Map<String,String> ret = new HashMap<>();

        if(StringUtils.isEmpty(student.getName())){
            ret.put("type","error");
            ret.put("msg","学生姓名不能为空");
            return ret;
        }

        if(StringUtils.isEmpty(student.getPassword())){
            ret.put("type","error");
            ret.put("msg","密码不能为空");
            return ret;
        }

        if(student.getClazzId() == null){
            ret.put("type","error");
            ret.put("msg","请选择所属班级");
            return ret;
        }

        if(isExist(student.getName(),null)){
            ret.put("type","error");
            ret.put("msg","该姓名已经存在");
            return ret;
        }
        student.setSn(StringUtil.generateSn("S",""));

        if(studentService.add(student) <= 0){
            ret.put("type","error");
            ret.put("msg","添加失败");
            return  ret;
        }

        ret.put("type","success");
        ret.put("msg","添加成功");
        return ret;
    }

    /**
     * 修改学生
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> edit(Student student){
        Map<String,String> ret = new HashMap<>();

        if(StringUtils.isEmpty(student.getName())){
            ret.put("type","error");
            ret.put("msg","学生姓名不能为空");
            return ret;
        }

        if(StringUtils.isEmpty(student.getPassword())){
            ret.put("type","error");
            ret.put("msg","密码不能为空");
            return ret;
        }

        if(student.getClazzId() == null){
            ret.put("type","error");
            ret.put("msg","请选择所属班级");
            return ret;
        }

        if(student.getAdmissionTime() == null){
            ret.put("type","error");
            ret.put("msg","请选择入学年份");
            return ret;
        }

        if(isExist(student.getName(),student.getId())){
            ret.put("type","error");
            ret.put("msg","该姓名已经存在");
            return ret;
        }
        student.setSn(StringUtil.generateSn("S",""));

        if(studentService.edit(student) <= 0){
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
     * @param clazzId
     * @param page
     * @return
     */
    //前端那边的异步请求的/get_list路径
    @RequestMapping(value = "/get_list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> findList(
            @RequestParam(value = "name" ,required = false,defaultValue = "")String name,
            @RequestParam(value = "admissionTime" ,required = false,defaultValue = "")Integer admissionTime,
            @RequestParam(value = "clazzId",required = false)Integer clazzId,
            HttpServletRequest request,
            Page page
    ){
       Map<String,Object> ret = new HashMap<>();
       Map<String,Object> queryMap = new HashMap<>();

       //模糊查询
       queryMap.put("name","%"+name+"%");
       queryMap.put("admissionTime",admissionTime);

       //获取登录的类型
       Object attribute = request.getSession().getAttribute("userType");


       if("2".equals(attribute.toString())){
           //说明是学生
           Student loginedStudent = (Student) request.getSession().getAttribute("user");
           queryMap.put("name","%"+loginedStudent.getName()+"%");

       }

       if(clazzId != null){
           queryMap.put("clazzId",clazzId);
       }

       queryMap.put("offset",page.getOffset());
       queryMap.put("pageSize",page.getRows());
        ret.put("rows",studentService.findList(queryMap));
       ret.put("total",studentService.getTotal(queryMap));
       return ret;
    }

    /**
     * 判断学生姓名是否存在
     * @param name
     * @param id
     * @return
     */
    private boolean isExist(String name,Integer id){
        Student student = studentService.findByName(name);
        if(student != null){
            if(id == null){
                return true;
            }

            if(student.getId().intValue() != id.intValue()){
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

            if(studentService.delete(idsString) <= 0){
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

