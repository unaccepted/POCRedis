package com.mkyong.common;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.util.prefs.BackingStoreException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.integration.redis.util.RedisLockRegistry;

/**
 * This class check the fail-over test for redis server
 *
 * @author amanj
 *
 */
public class App {
	static long i = 0;
	static ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
	static StringRedisTemplate template = (StringRedisTemplate) context.getBean("stringRedisTemplate");

	public void getValue(long c) throws InterruptedException, ConnectException, SocketException {
		try {
			getValue1();
		} catch (ConnectException e) {
			// When master is down, the following api retry the request to
			// server for 10 times, waiting each time for 1000ms and increment
			// the counter(=c)
			if (c == 10) {
				System.out.println("error");
				throw new ConnectException();
			} else {
				c = c + 1;
				System.out.println("connecting.." + c);
				Thread.sleep(5000);
				getValue(c);
			}
		}
	}

	public static void main(String[] args) throws BackingStoreException, InterruptedException, IOException {
		App app = new App();

		// spring redis lock
		RedisLockRegistry registry = new RedisLockRegistry(template.getConnectionFactory(), "redisTests");
		// System.out.println(template.getConnectionFactory().getConnection().ping());
		for (i = 0; i < 100; i++) {
			try {
				app.getValue(0);
			} catch (ConnectException e) {
				System.out.println("ConnectException");
			}

			Thread.sleep(1000);
		}
	}

	private static void getValue1() throws ConnectException, InterruptedException {
		try {
			template.opsForHash().put("amannodenew002", "aman" + i, "value" + i);
			System.out.println(template.opsForHash().get("amannodenew002", "aman" + i));
		} catch (Exception e) {
			throw new ConnectException();
		}
	}
}