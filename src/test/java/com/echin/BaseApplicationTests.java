package com.echin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.mmk.BaseApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BaseApplication.class)
@WebAppConfiguration
public class BaseApplicationTests {

	@Test
	public void contextLoads() {
	}
	
	@Test
	public void login(){
		
	}

}