package com.cjp.interceptor;


import com.google.gson.Gson;
import net.sf.json.JSONObject;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 登陆过滤拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object o) throws Exception {
        String url = request.getRequestURI();
        Object user =  request.getSession().getAttribute("user");
        Gson gson = new Gson();
        if(user == null){
            //表示未登录，登录失效
            System.out.println("未登录或登录失效，url = " + url);

            /**
             * 说明它是ajax请求
             */
            if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
                Map<String,String> ret = new HashMap<>();

                ret.put("msg","登陆状态已失效，请重新登陆");
                response.getWriter().write(JSONObject.fromObject(ret).toString());
//                response.getWriter().write(gson.toJson(ret));
                return false;
             }

            response.sendRedirect(request.getContextPath() + "/system/login");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
