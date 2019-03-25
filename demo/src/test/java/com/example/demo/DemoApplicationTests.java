package com.example.demo;
import com.example.demo.Controller.UserController;
import com.example.demo.Mapper.UserMapper;
import com.example.demo.com.didispace.web.BlogProperties;
import com.example.demo.service.Task;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.service.Sender;
import com.example.demo.service.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration    //模拟ServletContext容器
public class DemoApplicationTests {

	private MockMvc mvc;

	@Autowired
	private BlogProperties blogProperties;
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private Sender sender;

	@Autowired
	private Task task;

	@Before
	public void setUp() throws Exception{   //init a mvc，模拟http请求
		// 注册一个contrller的mockmvc
		mvc = MockMvcBuilders.standaloneSetup(new UserController()).build();
	}
	@Before
	public void before() {
		userMapper.insert("AAA", 10);
	}

	@Test
	public void hello() throws Exception {
		sender.send();	//消息发送
	}


	@Test
	public void testUserController() throws Exception{
		//测试User Controller
		RequestBuilder request = get("/users/");

		//查user列表，应该为空
		mvc.perform(request)
				.andExpect(status().isOk()) // 有响应
				.andExpect(content().string( "[]" ) );  //content的string形式与预期进行对比

		//post提交一个user
		request = post("/users/")
						.param("id","1")
						.param("name","testMaster")
						.param("age","20");
		mvc.perform(request)
				.andExpect(content().string("Success"));

		// 获取user列表
		request = get("/users/");
		mvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().string("[{\"id\":1,\"name\":\"testMaster\",\"age\":20}]"));

		//将id为1的user信息进行修改
		request = put("/users/1")
						.param("name","测试大师")
						.param("age","30");
		mvc.perform(request)
						.andExpect(content().string("success"));

		//查询user，此时user信息应该已经修改
		request = get("/users/1");
		mvc.perform(request)
						.andExpect(content().string("{\"id\":1,\"name\":\"测试大师\",\"age\":30}"));

		//删除id为1的user
		request = delete("/users/1");
		mvc.perform(request)
						.andExpect(content().string("success"));

		//查询user，应为空
		request = get("/users/");
		mvc.perform(request)
						.andExpect(status().isOk())
						.andExpect(content().string("[]"));

	}

	@Transactional //(propagation = ,isolation = )   //声明事务，可以指定隔离级别和传播行为，任务未完成自动回滚
	@Test
	public void test() throws Exception{

		userMapper.insert("AAA", 10);
		userMapper.insert("BBB", 20);
		userMapper.insert("CCC", 30);
		userMapper.insert("DDD", 40);
		userMapper.insert("EEE", 50);
		userMapper.insert("FFF", 60);
		userMapper.insert("GGG", 70);
		userMapper.insert("HHHHHHHHHHHHH", 80);
		userMapper.insert("III", 90);
		userMapper.insert("JJJ", 100);
	}



	@Test
	public void test2() throws Exception{

		User u1 = userMapper.findByName("AAA");
		System.out.println("第一次查询：" + u1.getAge());

		User u2 = userMapper.findByName("AAA");
		System.out.println("第二次查询：" + u2.getAge());

	}



	@Test
	@Rollback
	public void testUserMapper() throws Exception{

		//insert 一条数据并进行验证
		userMapper.insert("AAA",20);
		User u = userMapper.findByName("AAA");   //根据名字查找对应的数据库内容，返回User对象
		Assert.assertEquals(20,u.getAge().intValue());

		u.setAge(30);
		userMapper.update(u);
		u = userMapper.findByName("AAA");
		Assert.assertEquals(30,u.getAge().intValue());

		userMapper.delete(u.getID());
		u = userMapper.findByName("AAA");
		Assert.assertEquals(null,u);




	}

	@Test
	public void testInsertByMap() throws Exception{

		Map<String, Object> map = new HashMap<>();
		map.put("name", "CCC");
		map.put("age", 40);
		userMapper.insertByMap(map);

	}

	@Test
	@Rollback
	public void testUserMapper2() throws Exception {

		//测试Result注解

		List<User> userList = userMapper.findAll();
		for(User user : userList) {
			Assert.assertEquals(null, user.getID());
			Assert.assertNotEquals(null, user.getName());
		}
	}

	@Test
	@Rollback
	public void findByName() throws Exception {
		userMapper.insert("AAA", 20);
		User u = userMapper.findByName("AAA");
		Assert.assertEquals(20, u.getAge().intValue());
	}

	@Test
	@Rollback
	public void findByName2() throws Exception {
		Map<String,Object> map = new HashMap<>();
		map.put("age", 40);
		map.put("name", "CCC");

		userMapper.insertByMap(map);
		User u = userMapper.findByName("CCC");
		Assert.assertEquals(40, u.getAge().intValue());
	}


	@Test
	public void getHello() throws Exception{
		//参数使用 测试
		Assert.assertEquals(blogProperties.getName(),"root");
		Assert.assertEquals(blogProperties.getPassword(),"root123");
		System.out.println(blogProperties.getRanKey());  //输出随机数
	}


	@Test
	public void test3() throws Exception{

		long start = System.currentTimeMillis();

		Future<String> task1 = task.doTaskOne();
		Future<String> task2 = task.doTaskTwo();
		Future<String> task3 = task.doTaskThree();

		while(true) {
			if(task1.isDone() && task2.isDone() && task3.isDone()) {
				// 三个任务都调用完成，退出循环等待
				break;
			}
			Thread.sleep(1000);
		}

		long end = System.currentTimeMillis();

		System.out.println("任务全部完成，总耗时：" + (end - start) + "毫秒");

	}




}

