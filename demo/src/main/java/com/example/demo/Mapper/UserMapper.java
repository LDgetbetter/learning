package com.example.demo.Mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.example.demo.service.User;

import java.util.List;
import java.util.Map;

@Component   //Bean
@Mapper     //MyBatis 声明
@CacheConfig(cacheNames = "users")   //配置缓存对象名
public interface UserMapper {
    //定义数据库的具体操作

    @Cacheable  //缓存
    @Select("SELECT * FROM users WHERE name = #{name}")
    User findByName(@Param("name") String name);       //查找符合条件的数据，与User的属性对应返回User对象

    @Insert("INSERT INTO users(name,age) VALUES(#{name},#{age})")
    int insert(@Param("name") String name, @Param("age") Integer age);   //insert和update的返回值表示有几行数据受影响

    @Insert("INSERT INTO users(name,age) VALUES(#{name,jdbcType=VARCHAR},#{age,jdbcType=INTEGER})")
    int insertByMap(Map<String,Object> map);     //只能插入一条数据，若String（key）与字段不匹配则忽略，put了多个name跟age只会保留一个，若String（key）数量不足，则用null补充

    @Update("UPDATE users SET age=#{age} WHERE name = #{name}")
    void update(User user); //将User的name与age传过去

    @Delete("DELETE FROM users WHERE id = #{id}")
    void delete(Long id);   //根据ID删除

    @Insert("INSERT INTO users(name,age) VALUES(#{name},#{age})")
    int save(User user);    //根据User中的属性对应直接插入

    @Results({
    //映射查询结果集到实体类属性
            @Result(property = "name",column = "name"),  //property为实体属性名，column为数据库字段名
            @Result(property = "age",column = "age")
    })
    @Select("SELECT name,age FROM users")
    List<User> findAll();  //对每一条记录，都返回一个user对象，得到一个对象list
}
