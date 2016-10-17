package com.echin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import com.mmk.BaseApplication;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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