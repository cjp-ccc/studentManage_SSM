package com.cjp.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class Course implements Serializable {
    private Integer id;
    private String name;
}
