package com.example.demo.com.didispace.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice   //作用在所有注解了@RequestMapping的方法上
public class globalExceptionHandler {
    public static final String ERROR = "error";
    @ExceptionHandler   //异常处理
    public ModelAndView defaultErrorHandler(HttpServletRequest req,Exception e) throws Exception{

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception",e);
        mav.addObject("url",req.getRequestURL());   //获得发生异常时的http请求的url，exception，传给error.html
        mav.setViewName(ERROR);   //展示页面
        return mav;

    }

}
