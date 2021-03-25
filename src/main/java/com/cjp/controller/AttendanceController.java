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
import java.util.*;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private OptionalCourseServiceImpl optionalCourseService;
    @Autowired
    private StudentServiceImpl studentService;

    @Autowired
    private AttendanceServiceImpl attendanceService;

    @Autowired
    private SelectedCourseServiceImpl selectedCourseService;


    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView model,HttpServletRequest request){


        model.setViewName("attendance/attendance_list");
        List<OptionalCourse> optionalCourses = optionalCourseService.findAll();
        List<Student> students = studentService.findAll();

        model.addObject("optionalCourseList",optionalCourses);
        model.addObject("optionalCourseListJson", JSONArray.fromObject(optionalCourses));
        model.addObject("studentList",students);
        model.addObject("studentListJson", JSONArray.fromObject(students));

//        //获取登录的类型
//        Object attribute = request.getSession().getAttribute("userType");


//        //登录是学生
//        if("2".equals(attribute.toString())){
//            Map<String,Object> queryMap = new HashMap<>();
//
//            Student student = (Student) request.getSession().getAttribute("user");
//            queryMap.put("studentId",student.getId());
//            List<SelectedCourse> selectedCourses = selectedCourseService.findByStudentId(queryMap);
//            model.addObject("selectedCourseList",selectedCourses);
//            model.addObject("selectedCourseListJson", JSONArray.fromObject(selectedCourses));
//        }

        return model;
    }

    /**
     * 异步查询数据
     * @param optionalCourseId
     * @param studentId
     * @param page
     * @return
     */
    @RequestMapping(value = "/get_list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(
            @RequestParam(value = "optionalCourseId",required = false) Integer optionalCourseId,
            @RequestParam(value = "studentId",required = false) Integer studentId,
            HttpServletRequest request,
            Page page
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
        studentId = null;


        if("2".equals(attribute.toString())){
            Student student = (Student) request.getSession().getAttribute("user");

            studentId = student.getId();
            queryMap.put("studentId",studentId);
        }
        queryMap.put("offset",page.getOffset());
        queryMap.put("pageSize",page.getRows());

        ret.put("rows",attendanceService.findList(queryMap));
        ret.put("total",attendanceService.getTotal(queryMap));
        return ret;
    }

    /**
     * 添加签到
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(Attendance attendance){
        Map<String,String> ret = new HashMap<>();
        Map<String,Object> queryMap = new HashMap<>();

        if(attendance.getOptionalCourseId() == null){
            ret.put("type","error");
            ret.put("msg","所选课程不能为空");
            return ret;
        }




        if(attendance.getStudentId() == null){
            ret.put("type","error");
            ret.put("msg","学生不能为空");
            return ret;
        }

        if(attendanceService.add(attendance) <= 0){
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
    public Map<String,String> edit(Attendance attendance){
        Map<String,String> ret = new HashMap<>();

        if(attendance.getStudentId() == null){
            ret.put("type","error");
            ret.put("msg","学生不能为空");
            return ret;
        }


        if(attendance.getOptionalCourseId() == null){
            ret.put("type","error");
            ret.put("msg","所选课程不能为空");
            return ret;
        }

        if(attendanceService.edit(attendance)<=0){
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

            if(attendanceService.delete(idsString) <= 0){
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



    /**
     * 学生签到
     */
    @RequestMapping(value = "/attend",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> attend(
            HttpServletRequest request,
            Attendance attendance
    ){
        Map<String,String> ret = new HashMap<>();
        Map<String,Object> queryMap = new HashMap<>();


        //获取登录的类型
        Object attribute = request.getSession().getAttribute("userType");
        Integer studentId = null;


        if("2".equals(attribute.toString())){
            Student student = (Student) request.getSession().getAttribute("user");

            studentId = student.getId();
            queryMap.put("studentId",studentId);
        }
        //获取当前时间
        Date currentTime = new Date();

        attendance.setStudentId(studentId);
        attendance.setAttendTime(currentTime);

        queryMap.put("optionalCourseId",attendance.getOptionalCourseId());

        List<Attendance> exit = attendanceService.findExit(queryMap);

        if(exit.size() != 0){
            ret.put("type","error");
            ret.put("msg","您已经签过到了，请不要重复签到");
            return  ret;
        }


        if(attendanceService.add(attendance) <= 0){
            ret.put("type","error");
            ret.put("msg","签到失败");

            return  ret;
        }


        ret.put("type","success");
        ret.put("msg","签到成功");
        return ret;
    }
}
