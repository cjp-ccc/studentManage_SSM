package com.cjp.utils;

import java.util.Date;
import java.util.List;

/**
 * 实用工具类
 */

public class StringUtil{
    public static String joinString(List<Integer> list, String split){
        String ret = "";
        for(Integer i : list){
          ret += i + split;
        }
        if(!"".equals(ret)){
            ret = ret.substring(0,ret.length() - split.length());
        }

        return ret;
    }

    //生成一个时间戳的工号或学号
    public static String generateSn(String prefix,String suffix){
        return prefix + new Date().getTime() + suffix;
    }
}