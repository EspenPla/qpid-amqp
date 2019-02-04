package io.sesam.amqp;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AmqpApplicationTests {

	@BeforeClass
	public static void setUp() {
        System.setProperty("url", "foo");
	}

	@Test
	public void contextLoads() {
	}

}

