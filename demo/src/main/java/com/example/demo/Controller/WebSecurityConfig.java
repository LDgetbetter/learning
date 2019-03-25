package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity    //注解在config上面，表示启动security
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //security设置
    @Override
    protected void configure(HttpSecurity http) throws Exception{

        http
                .authorizeRequests()   //定义哪些url需要被保护
                    .antMatchers("/","/home").permitAll() //匹配的，允许
                    .anyRequest().authenticated()
                    .and()
                .formLogin()            //定义当用户需要登陆时，转到的登陆页面
                    .loginPage("/login")
                    .permitAll()
                    .and()
                .logout()
                    .permitAll();

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    //设置账号密码
        auth
                .inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("user")
                .password(new BCryptPasswordEncoder().encode("password")).roles("USER");
        // 将密码进行编码，不然无法跳转到对应网页
    }


}
