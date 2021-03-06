package kr.or.connect.vo;

import java.sql.Timestamp;

public class Todo {
	private int id;
	private String todo;
	private String completed;
	private Timestamp  date;
	
	public Todo() {
		// TODO Auto-generated constructor stub
	}
	
	
	public Todo(String todo) {
		super();
		this.todo = todo;
	}

	public Todo(String todo,String completed) {
		super();
		this.todo = todo;
		this.completed = completed;
	}

	
	public Todo(int id, String todo, String completed, Timestamp date) {
		this.id = id;
		this.todo = todo;
		this.completed = completed;
		this.date = date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTodo() {
		return todo;
	}
	public void setTodo(String todo) {
		this.todo = todo;
	}
	public String getCompleted() {
		return completed;
	}
	public void setCompleted(String completed) {
		this.completed = completed;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	
}
