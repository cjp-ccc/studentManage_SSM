package com.cjp.controller;

import com.cjp.entity.*;
import com.cjp.page.Page;
import com.cjp.service.impl.*;
import com.cjp.utils.StringUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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
@RequestMapping("/courseTeacher")
public class CourseTeacherController {

    @Autowired
    private CourseServiceImpl courseService;
    @Autowired
    private TeacherServiceImpl teacherService;

    @Autowired
    private CourseTeacherServiceImpl courseTeacherService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView model){
        model.setViewName("courseTeacher/courseTeacher_list");
        List<Course> courseServiceAll = courseService.findAll();
        List<Teacher> teachers = teacherService.findAll();
        model.addObject("courseList",courseServiceAll);
        model.addObject("courseListJson", JSONArray.fromObject(courseServiceAll));
        model.addObject("teacherList",teachers);
        model.addObject("teacherListJson", JSONArray.fromObject(teachers));
        return model;
    }

    @RequestMapping(value = "/get_list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(
            @RequestParam(value = "courseId",required = false) Integer courseId,
            @RequestParam(value = "teacherId",required = false) Integer teacherId,
            Page page
    ){
        Map<String,Object> ret = new HashMap<>();
        Map<String,Object> queryMap = new HashMap<>();

        if (courseId != null){
            queryMap.put("courseId",courseId);
        }

        if (teacherId != null){
            queryMap.put("teacherId",teacherId);
        }
        queryMap.put("offset",page.getOffset());
        queryMap.put("pageSize",page.getRows());

        ret.put("rows",courseTeacherService.findList(queryMap));
        ret.put("total",courseTeacherService.getTotal(queryMap));
        return ret;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(CourseTeacher courseTeacher){
        Map<String,String> ret = new HashMap<>();

        if(courseTeacher.getTeacherId() == null){
            ret.put("type","error");
            ret.put("msg","教师不能为空");
            return ret;
        }


        if(courseTeacher.getCourseId() == null){
            ret.put("type","error");
            ret.put("msg","课程不能为空");
            return ret;
        }

        if(courseTeacherService.add(courseTeacher) <= 0){
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
    public Map<String,String> edit(CourseTeacher courseTeacher){
        Map<String,String> ret = new HashMap<>();

        if(courseTeacher.getTeacherId() == null){
            ret.put("type","error");
            ret.put("msg","教师不能为空");
            return ret;
        }


        if(courseTeacher.getCourseId() == null){
            ret.put("type","error");
            ret.put("msg","课程不能为空");
            return ret;
        }

        if(courseTeacherService.edit(courseTeacher)<=0){
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

            if(courseTeacherService.delete(idsString) <= 0){
                ret.put("type","error");
                ret.put("msg","删除失败");
                return ret;
            }
        } catch (Exception e) {
            ret.put("type","error");
            ret.put("msg","该班级下存在学生信息，请勿删除");
            return ret;
        }

        ret.put("type","success");
        ret.put("msg","删除成功");
        return ret;
    }
}
