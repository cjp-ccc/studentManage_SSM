package com.cjp.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Teacher implements Serializable {
    private Integer id;
    private String tn;
    private String name;
    private String sex;
    private String mobile;

}
