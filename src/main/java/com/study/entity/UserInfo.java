package com.study.entity;

import lombok.Data;

@Data
public class UserInfo {
    private Integer id;
    private String name;
    private Integer age;
    private String email;
    private String address;
    private Byte deleteFlag;
    private Integer entityVersion;
}
