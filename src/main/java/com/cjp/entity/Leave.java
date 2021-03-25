package com.cjp.entity;

import lombok.Data;

@Data
public class Leave {
    private Integer id;
    private Integer studentId;
    private String reason;
    private String remark;
    private Integer status = 0;  //状态

}
