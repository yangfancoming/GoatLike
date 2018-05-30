package com.solituder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoatApplicationTests {

	@Test
	public void contextLoads() {
        BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
        String temp = "123";
        String result = encoder.encode(temp);
        System.out.println(result);

	}

}
