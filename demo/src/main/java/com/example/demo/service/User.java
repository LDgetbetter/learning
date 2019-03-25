package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class User {
    //基本信息
    private Long id;
    private String name;
    private Integer age;

    public Long getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
