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

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/grade")
public class GradeController {

    @Autowired
    private OptionalCourseServiceImpl optionalCourseService;
    @Autowired
    private StudentServiceImpl studentService;

    @Autowired
    private ClazzServiceImpl clazzService;

    @Autowired
    private GradeServiceImpl gradeService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView model){
        model.setViewName("grade/grade_list");
        List<Clazz> clazzes = clazzService.findAll();
        List<OptionalCourse> optionalCourses = optionalCourseService.findAll();
        List<Student> students = studentService.findAll();
        model.addObject("optionalCourseList",optionalCourses);
        model.addObject("optionalCourseListJson", JSONArray.fromObject(optionalCourses));
        model.addObject("studentList",students);
        model.addObject("studentListJson", JSONArray.fromObject(students));
        model.addObject("clazzList",clazzes);
        model.addObject("clazzListJson", JSONArray.fromObject(clazzes));
        return model;
    }

    @RequestMapping(value = "/get_list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(
            @RequestParam(value = "optionalCourseId",required = false) Integer optionalCourseId,
            @RequestParam(value = "studentId",required = false) Integer studentId,
            Page page,
            HttpServletRequest request
    ){
        Map<String,Object> ret = new HashMap<>();
        Map<String,Object> queryMap = new HashMap<>();

        if (optionalCourseId != null){
            queryMap.put("optionalCourseId",optionalCourseId);
        }

        if (studentId != null){
            queryMap.put("studentId",studentId);
        }


        //获取登录的类型
        Object attribute = request.getSession().getAttribute("userType");

        if("2".equals(attribute.toString())){
            //说明是学生
            Student student = (Student) request.getSession().getAttribute("user");
            queryMap.put("studentId",student.getId());
        }

        queryMap.put("offset",page.getOffset());
        queryMap.put("pageSize",page.getRows());

        System.out.println(gradeService.findList(queryMap));
        ret.put("rows",gradeService.findList(queryMap));
        ret.put("total",gradeService.getTotal(queryMap));
        return ret;
    }




//    @RequestMapping(value = "/add",method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String,String> add(CourseTeacher courseTeacher){
//        Map<String,String> ret = new HashMap<>();
//
//        if(courseTeacher.getTeacherId() == null){
//            ret.put("type","error");
//            ret.put("msg","教师不能为空");
//            return ret;
//        }
//
//
//        if(courseTeacher.getCourseId() == null){
//            ret.put("type","error");
//            ret.put("msg","课程不能为空");
//            return ret;
//        }
//
//        if(courseTeacherService.add(courseTeacher) <= 0){
//            ret.put("type","error");
//            ret.put("msg","添加失败");
//            return ret;
//        }
//        ret.put("type","success");
//        ret.put("msg","添加成功");
//        return ret;
//    }
//
//
    /**
     * 修改功能
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> edit(Grade grade){
        Map<String,String> ret = new HashMap<>();

        if(grade.getScore() == null){
            ret.put("type","error");
            ret.put("msg","分数不能为空");
            return ret;
        }


        if(grade.getGpa() == null){
            ret.put("type","error");
            ret.put("msg","绩点不能为空");
            return ret;
        }

        if(gradeService.edit(grade)<=0){
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

            if(gradeService.delete(idsString) <= 0){
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
