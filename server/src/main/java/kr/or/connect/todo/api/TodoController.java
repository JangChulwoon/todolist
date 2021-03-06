package kr.or.connect.todo.api;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.todo.service.TodoService;
import kr.or.connect.vo.Todo;

//공통 부분을 requsetMapping으로 묶어줌 
@RestController
@RequestMapping("/api/todos")
@ComponentScan(basePackages = "kr.or.connect")
public class TodoController {
	@Autowired
	private final TodoService service;
	private final Logger log = LoggerFactory.getLogger(TodoController.class);

	public TodoController(TodoService service) {
		this.service = service;
	}
	
	
	@GetMapping
	Collection<Todo> readList() {
		return service.findAll();
	}

	@GetMapping("/{id}")
	Todo read(@PathVariable  Integer id) {
		return service.findById(id);
	}
	
	@GetMapping("/completed")
	Collection<Todo> readCompletedList() {
		return service.findByCompleted();
	}
	
	@GetMapping("/uncompleted")
	Collection<Todo> readUncompletedList() {
		return service.findByUncompleted();
	}

	//@ResponseStatus(HttpStatus.CREATED) 입력 처리하는 메서드 앞에 붙인다
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	Todo create(@RequestBody Todo todo) {
		Todo newtodo = service.create(todo);
		log.info("Todo created : {}" , newtodo);
		return todo;
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void update(@PathVariable Integer id, @RequestBody Todo todo) {
		todo.setId(id);
		service.update(todo);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void delete(@PathVariable Integer id) {
		service.delete(id);
	}
	
	@DeleteMapping("/completed")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void deleteCompleted() {
		service.deleteCompleted();
	}
	
}