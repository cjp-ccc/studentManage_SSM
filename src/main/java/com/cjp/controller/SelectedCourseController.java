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
@RequestMapping("/selectedCourse")
public class SelectedCourseController {

    @Autowired
    private OptionalCourseServiceImpl optionalCourseService;
    @Autowired
    private StudentServiceImpl studentService;

    @Autowired
    private SelectedCourseServiceImpl selectedCourseService;

    @Autowired
    private GradeServiceImpl gradeService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView model){
        model.setViewName("selectedCourse/selectedCourse_list");
        List<OptionalCourse> optionalCourses = optionalCourseService.findAll();
        List<Student> students = studentService.findAll();
        model.addObject("optionalCourseList",optionalCourses);
        model.addObject("optionalCourseListJson", JSONArray.fromObject(optionalCourses));
        model.addObject("studentList",students);
        model.addObject("studentListJson", JSONArray.fromObject(students));
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
        queryMap.put("offset",page.getOffset());
        queryMap.put("pageSize",page.getRows());

        ret.put("rows",selectedCourseService.findList(queryMap));
        ret.put("total",selectedCourseService.getTotal(queryMap));
        return ret;
    }

    /**
     * 添加选课
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(SelectedCourse selectedCourse){
        Map<String,String> ret = new HashMap<>();
        Map<String,Object> queryMap = new HashMap<>();

        if(selectedCourse.getOptionalCourseId() == null){
            ret.put("type","error");
            ret.put("msg","所选课程不能为空");
            return ret;
        }




        if(selectedCourse.getStudentId() == null){
            ret.put("type","error");
            ret.put("msg","学生不能为空");
            return ret;
        }


        queryMap.put("studentId",selectedCourse.getStudentId());
        queryMap.put("optionalCourseId",selectedCourse.getOptionalCourseId());

        List<SelectedCourse> exit = selectedCourseService.findExit(queryMap);

        if(exit.size() != 0){
            ret.put("type","error");
            ret.put("msg","您已经为学生添加了选课，请不要重复选择");
            return  ret;
        }

        if(selectedCourseService.add(selectedCourse) <= 0){
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
    public Map<String,String> edit(SelectedCourse selectedCourse){
        Map<String,String> ret = new HashMap<>();

        if(selectedCourse.getStudentId() == null){
            ret.put("type","error");
            ret.put("msg","学生不能为空");
            return ret;
        }


        if(selectedCourse.getOptionalCourseId() == null){
            ret.put("type","error");
            ret.put("msg","所选课程不能为空");
            return ret;
        }

        if(selectedCourseService.edit(selectedCourse)<=0){
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

            if(selectedCourseService.delete(idsString) <= 0){
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
     * 为选课添加成绩
     */
    @RequestMapping(value = "/addGrade",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> selectCourse(
            Grade grade
    ){
        Map<String,String> ret = new HashMap<>();
        Map<String,Object> queryMap = new HashMap<>();


        if(grade == null){
            ret.put("type","error");
            ret.put("msg","请选择要给分的选课");
            return ret;
        }

        queryMap.put("id",grade.getId());
        SelectedCourse selectedCourse = selectedCourseService.findOne(queryMap);
        grade.setStudentId(selectedCourse.getStudentId());
        grade.setOptionalCourseId(selectedCourse.getOptionalCourseId());

        queryMap.put("studentId",selectedCourse.getStudentId());
        queryMap.put("optionalCourseId",selectedCourse.getOptionalCourseId());
        List<SelectedCourse> exit = gradeService.findExit(queryMap);

        if(exit.size() != 0){
            ret.put("type","error");
            ret.put("msg","您已经添加了该成绩，请前往查看");
            return  ret;
        }


        if(gradeService.add(grade) <= 0){
            ret.put("type","error");
            ret.put("msg","添加成绩失败");

            return  ret;
        }


        ret.put("type","success");
        ret.put("msg","添加成绩成功");
        System.out.println("添加成绩成功");
        return ret;
    }
}
