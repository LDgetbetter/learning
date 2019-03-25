package com.example.demo.com.didispace.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;




@Component
public class BlogProperties {

    //从properties中获取属性

    @Value("${com.example.demo.name}")
    private String name;

    @Value("${com.example.demo.password}")
    private String password;

    @Value("${randomKey}")
    private String ranKey;

    public String getRanKey() {
        return ranKey;
    }



    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
