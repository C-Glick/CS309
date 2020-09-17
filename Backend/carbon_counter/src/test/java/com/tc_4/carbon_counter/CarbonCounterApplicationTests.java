package com.tc_4.carbon_counter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.tc_4.carbon_counter.models.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest
class CarbonCounterApplicationTests {

	@Test
	void contextLoads() {
	}
	/*@LocalServerPort
    private int port;
	
    @Autowired
    private TestRestTemplate restTemplate;
	@Test
	void permissionCheckTest(){
		assert(this.restTemplate.getForObject("http://localhost/:" + port + "/user/test2", 
		String.class)).contains("Hello, World");
	}
	*/
}
