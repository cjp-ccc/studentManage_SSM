package com.cjp.controller;


import com.cjp.entity.Student;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sct")
public class SCTController {

    @Autowired
    private OptionalCourseServiceImpl optionalCourseService;
    @Autowired
    private StudentServiceImpl studentService;

    @Autowired
    private TeacherServiceImpl teacherService;

    @Autowired
    private SCTServiceImpl sctService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView model){
        model.setViewName("selectedCourse/selectedCourse_list1");
//        List<OptionalCourse> optionalCourses = optionalCourseService.findAll();
//        List<Student> students = studentService.findAll();
//        model.addObject("optionalCourseList",optionalCourses);
//        model.addObject("optionalCourseListJson", JSONArray.fromObject(optionalCourses));
//        model.addObject("studentList",students);
//        model.addObject("studentListJson", JSONArray.fromObject(students));
        return model;
    }

    @RequestMapping(value = "/get_list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(
//            @RequestParam(value = "optionalCourseId",required = false) Integer optionalCourseId,
//            @RequestParam(value = "studentId",required = false) Integer studentId,
            HttpServletRequest request,
            Page page
    ){
        Map<String,Object> ret = new HashMap<>();
        Map<String,Object> queryMap = new HashMap<>();


//        if (optionalCourseId != null){
//            queryMap.put("optionalCourseId",optionalCourseId);
//        }
//
//        if (studentId != null){
//            queryMap.put("studentId",studentId);
//        }

        Student student = (Student) request.getSession().getAttribute("user");
        queryMap.put("studentId",student.getId());
        queryMap.put("offset",page.getOffset());
        queryMap.put("pageSize",page.getRows());

        ret.put("rows",sctService.findList(queryMap));
        ret.put("total",sctService.getTotal(queryMap));
        return ret;
    }

}
