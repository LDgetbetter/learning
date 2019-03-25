package com.example.demo.Controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.User;

import java.util.*;

@RestController
@RequestMapping(value = "/users")

public class UserController {
    //创建线程安全的Map
    static Map<Long, User> users = Collections.synchronizedMap(new HashMap<Long, User>());

    @ApiOperation(value="获取用户列表", notes="")      //Swagger2注解，将其加入swagger2中
    @RequestMapping(value = "/", method = RequestMethod.GET)     //查询用户列表，对/的get请求进行匹配
    public List<User> getUserList() {

        List<User> r = new ArrayList<User>(users.values());    //users中的user转换到r中
        return r;
    }

    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @RequestMapping(value = "/", method = RequestMethod.POST)    //add一个用户
    public String postUser(@ModelAttribute User user) {    //添加到模型对象中的
        users.put(user.getID(), user);                          //将user放入users
        return "Success";

    }

    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public User getUser(@PathVariable Long id){     //从url中获取信息，而requestParam的信息部分在参数
        //获取url中id值的User信息
        return users.get(id);

    }

    @ApiOperation(value="更新用户详细信息", notes="根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    })
    @RequestMapping(value= "/{id}",method = RequestMethod.PUT)
    public String putUser(@PathVariable Long id , @ModelAttribute User user) {
        // update user
        User u = users.get(id);
        u.setName(user.getName());
        u.setAge(user.getAge());
        users.put(id,u);
        return "success";

    }

    @ApiOperation(value="删除用户", notes="根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long id){
        // delete a user with id
        users.remove(id);
        return "success";

    }





}