package com.cjp.entity;


import lombok.Data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class Student implements Serializable {
    private Integer id;
    private String sn;
    private Integer clazzId;
    private String name;
    private String password;
    private String sex;
    private String mobile;
    private Date admissionTime;
    private String admissionYear;


}
