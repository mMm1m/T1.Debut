package org.example.task_1;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import javax.sql.DataSource;
import okhttp3.OkHttpClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class Task1ApplicationTests {

	@Autowired
	private ApplicationContext context;

	@Test
	public void testDataSourceBean() {
		DataSource dataSource = context.getBean(DataSource.class);
		assertNotNull(dataSource);
	}

	@Test
	public void testOkHttpClientBean() {
		OkHttpClient okHttpClient = context.getBean(OkHttpClient.class);
		assertNotNull(okHttpClient);
	}
}
