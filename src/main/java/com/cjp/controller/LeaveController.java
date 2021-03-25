package com.cjp.controller;


import com.cjp.entity.*;
import com.cjp.page.Page;
import com.cjp.service.StudentService;
import com.cjp.service.impl.*;
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
@RequestMapping("/leave")
public class LeaveController {

    @Autowired
    private StudentServiceImpl studentService;

    @Autowired
    private LeaveServiceImpl leaveService;



    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView model){
        model.setViewName("leave/leave_list");
        List<Student> findAll = studentService.findAll();
        model.addObject("studentList",findAll);
        model.addObject("studentListJson", JSONArray.fromObject(findAll));
        return model;
    }


    /**
     * 请假
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(Leave leave,HttpServletRequest request){
        Map<String,String> ret = new HashMap<>();

        Integer studentId = null;
        //获取登录的类型
        Object attribute = request.getSession().getAttribute("userType");

        if("2".equals(attribute.toString())){
            //说明是学生
            Student student = (Student) request.getSession().getAttribute("user");
            studentId = student.getId();
        }

        leave.setStudentId(studentId);
        leave.setStatus(0);



        if(leave.getReason() == null){
            ret.put("type","error");
            ret.put("msg","请假原因不能为空");
            return ret;
        }



        if(leaveService.add(leave) <= 0){
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
    public Map<String,String> edit(Leave leave){
        Map<String,String> ret = new HashMap<>();

        if(leave.getReason() == null){
            ret.put("type","error");
            ret.put("msg","请假原因不能为空");
            return ret;
        }

        if(leaveService.edit(leave) <= 0){
            ret.put("type","error");
            ret.put("msg","修改失败");
            return  ret;
        }

        ret.put("type","success");
        ret.put("msg","修改成功");
        return ret;
    }


    /**
     * 审核请假
     */
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> check(Leave leave){
        Map<String,String> ret = new HashMap<>();

        if(leave.getStatus() == null){
            ret.put("type","error");
            ret.put("msg","审核装填不能为空");
            return ret;
        }

        if(leave.getRemark() == null){
            ret.put("type","error");
            ret.put("msg","批复内容不能为空");
            return ret;
        }


        if(leaveService.check(leave) <= 0){
            ret.put("type","error");
            ret.put("msg","审核失败");
            return  ret;
        }

        ret.put("type","success");
        ret.put("msg","审核成功");
        return ret;
    }


    /**
     * 获取学生列表
     * @param
     * @param
     * @param page
     * @return
     */
    //前端那边的异步请求的/get_list路径
    @RequestMapping(value = "/get_list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> findList(
            @RequestParam(value = "status" ,required = false,defaultValue = "")Integer status,
            @RequestParam(value = "studentId" ,required = false)Integer studentId,
            Page page,
            HttpServletRequest request
    ){
       Map<String,Object> ret = new HashMap<>();
       Map<String,Object> queryMap = new HashMap<>();

        if (studentId != null){
            queryMap.put("studentId",studentId);
        }

        if (status != null){
            queryMap.put("status",status);
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
        ret.put("rows",leaveService.findList(queryMap));
       ret.put("total",leaveService.getTotal(queryMap));
       return ret;
    }

    /**
     * 判断学生姓名是否存在
     * @param name
     * @param id
     * @return
     */
//    private boolean isExist(String name,Integer id){
//        OptionalCourse course = courseService.findByName(name);
//        if(course != null){
//            if(id == null){
//                return true;
//            }
//
//            if(course.getId().intValue() != id.intValue()){
//                return true;
//            }
//        }
//
//        return false;
//    }


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

            if(leaveService.delete(idsString) <= 0){
                ret.put("type","error");
                ret.put("msg","删除课程失败");
                return ret;
            }
        } catch (Exception e) {
            ret.put("type","error");
            ret.put("msg","该请假下存在其他信息，请勿删除");
            return ret;
        }

        ret.put("type","success");
        ret.put("msg","删除成功");
        return ret;
    }

    /**
     * 选课
     */
//    @RequestMapping(value = "/selectCourse",method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String,String> selectCourse(
//            @RequestParam(value = "ids[]",required = true) Integer[] ids,
//            HttpServletRequest request
//    ){
//        Map<String,String> ret = new HashMap<>();
//        Map<String,Object> queryMap = new HashMap<>();
//        Integer optionalCourseId = ids[0];
//        if(optionalCourseId == null){
//            ret.put("type","error");
//            ret.put("msg","请选择要删除的数据");
//            return ret;
//        }
//
//        SelectedCourse selectedCourse = new SelectedCourse();
//        selectedCourse.setOptionalCourseId(optionalCourseId);
//
//        Integer studentId = 0;
//
//        //获取登录的类型
//        Object attribute = request.getSession().getAttribute("userType");
//        if("2".equals(attribute.toString())){
//            //说明是学生
//            Student student = (Student)request.getSession().getAttribute("user");
//            studentId = student.getId();
//        }
//
//        selectedCourse.setStudentId(studentId);
//
//        queryMap.put("studentId",studentId);
//        queryMap.put("optionalCourseId",optionalCourseId);
//        List<SelectedCourse> exit = selectedCourseService.findExit(queryMap);
//
//        if(exit.size() != 0){
//            ret.put("type","error");
//            ret.put("msg","您已经选了该课程，请不要重复选择");
//            return  ret;
//        }
//
//        if(selectedCourseService.add(selectedCourse) <= 0){
//            ret.put("type","error");
//            ret.put("msg","选课失败");
//
//            return  ret;
//        }
//
//
//        ret.put("type","success");
//        ret.put("msg","选课成功");
//        System.out.println("选课成功");
//        return ret;
//    }


}

