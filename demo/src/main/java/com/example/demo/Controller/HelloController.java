package com.example.demo.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//hello world 应用程序
    @RestController   //@Controller 是修饰class，用来创建处理HTTP请求的对象，rest默认返回json格式
    public class HelloController {

        @RequestMapping("/exception")  //配置url映射
        public String error () throws Exception{
            throw new Exception("发生错误");

    }

    }

