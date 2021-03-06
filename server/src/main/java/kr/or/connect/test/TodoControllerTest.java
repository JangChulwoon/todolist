package kr.or.connect.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import kr.or.connect.todo.TodoApplication;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TodoApplication.class)
@WebAppConfiguration
@SpringBootTest
public class TodoControllerTest {

	@Autowired
	WebApplicationContext wac;
	MockMvc mvc;

	
	@Before
	public void setUp() {
		this.mvc = webAppContextSetup(this.wac)
			.alwaysDo(print(System.out))
			.build();
	}
	
	@Test
	public void shouldSelect() throws Exception {
		mvc.perform(
			get("/api/todos/1")
				.contentType(MediaType.APPLICATION_JSON)
		);
	
	}
	
	
	@Test
	public void shouldAllSelect() throws Exception {
		mvc.perform(
			get("/api/todos/")
				.contentType(MediaType.APPLICATION_JSON)
		);
	}
	
	// completed 테스트 
	@Test
	public void shouldCompletedSelect() throws Exception {
		mvc.perform(
			get("/api/todos/completed")
				.contentType(MediaType.APPLICATION_JSON)
		);
	}
	
	
	@Test
	public void shouldCreate() throws Exception {
		// id는 autoIncrease 
		
		String requestBody = "{\"todo\":\"Test\", \"completed\":\"1\",\"date\":\"2015-03-05\"}";

		mvc.perform(
			post("/api/todos/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody)
			)
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").exists())
			.andExpect(jsonPath("$.todo").value("Test"))
			.andExpect(jsonPath("$.completed").value("1"))
			.andExpect(jsonPath("$.date").value("2015-03-05"));
	}
	
	
	@Test
	public void shouldUpdate() throws Exception {
		String requestBody = "{\"todo\":\"Test\", \"completed\":\"1\"}";

		mvc.perform(
			put("/api/todos/3")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody)
			)
			.andExpect(status().isNoContent());
	}

	@Test
	public void shouldDelete() throws Exception {
		mvc.perform(
			delete("/api/todos/4")
				.contentType(MediaType.APPLICATION_JSON)
		)
		.andExpect(status().isNoContent());
	}
	

}