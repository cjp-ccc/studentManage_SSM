package com.cjp.controller;

import com.cjp.entity.Course;
import com.cjp.page.Page;
import com.cjp.service.impl.CourseServiceImpl;
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
@RequestMapping("/course")
public class CourseController {


    @Autowired
    private CourseServiceImpl courseService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView model){
        model.setViewName("course/course_list");
        return model;
    }


    /**
     * 添加老师可授课课程
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(Course course){
        Map<String,String> ret = new HashMap<>();

        if(StringUtils.isEmpty(course.getName())){
            ret.put("type","error");
            ret.put("msg","课程名称不能为空");
            return ret;
        }

//        if(StringUtils.isEmpty(course.getPassword())){
//            ret.put("type","error");
//            ret.put("msg","密码不能为空");
//            return ret;
//        }
//
//        if(course.getClazzId() == null){
//            ret.put("type","error");
//            ret.put("msg","请选择所属班级");
//            return ret;
//        }

        if(isExist(course.getName(),null)){
            ret.put("type","error");
            ret.put("msg","该课程已经存在");
            return ret;
        }


        if(courseService.add(course) <= 0){
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
    public Map<String,String> edit(Course course){
        Map<String,String> ret = new HashMap<>();

        if(StringUtils.isEmpty(course.getName())){
            ret.put("type","error");
            ret.put("msg","学生姓名不能为空");
            return ret;
        }

//        if(StringUtils.isEmpty(course.getPassword())){
//            ret.put("type","error");
//            ret.put("msg","密码不能为空");
//            return ret;
//        }

//        if(course.getClazzId() == null){
//            ret.put("type","error");
//            ret.put("msg","请选择所属班级");
//            return ret;
//        }

        if(isExist(course.getName(),course.getId())){
            ret.put("type","error");
            ret.put("msg","该姓名已经存在");
            return ret;
        }


        if(courseService.edit(course) <= 0){
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
            Page page
    ){
       Map<String,Object> ret = new HashMap<>();
       Map<String,Object> queryMap = new HashMap<>();

       queryMap.put("name","%"+name+"%");
       queryMap.put("offset",page.getOffset());
       queryMap.put("pageSize",page.getRows());
        ret.put("rows",courseService.findList(queryMap));
       ret.put("total",courseService.getTotal(queryMap));
       return ret;
    }

    /**
     * 判断学生姓名是否存在
     * @param name
     * @param id
     * @return
     */
    private boolean isExist(String name,Integer id){
        Course course = courseService.findByName(name);
        if(course != null){
            if(id == null){
                return true;
            }

            if(course.getId().intValue() != id.intValue()){
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

            if(courseService.delete(idsString) <= 0){
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

