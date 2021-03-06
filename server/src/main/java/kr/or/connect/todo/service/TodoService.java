package kr.or.connect.todo.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import kr.or.connect.todo.persistence.TodoDao;
import kr.or.connect.vo.Todo;

@Service
public class TodoService {
	private TodoDao tododao;

	public TodoService(TodoDao tododao) {
		this.tododao = tododao;
	}

	public Todo findById(Integer id) {
		return tododao.selectById(id);
	}
	
	public Collection<Todo> findByCompleted(){
		return tododao.selectCompleted();
	}
	
	public Collection<Todo> findByUncompleted(){
		return tododao.selectUncompleted();
	}
	
	public Collection<Todo> findAll() {
		return tododao.selectAll();
	}

	public Todo create(Todo todo) {
		Integer id = tododao.insert(todo);
		todo.setId(id);
		return todo;
	}

	public boolean update(Todo todo) {
		int affected = tododao.update(todo);
		return affected == 1;
	}

	public boolean delete(Integer id) {
		int affected = tododao.deleteById(id);
		return affected == 1;
	}
	
	public boolean deleteCompleted() {
		int affected = tododao.deleteCompletedAll();
		return affected == 1;
	}
	
}
