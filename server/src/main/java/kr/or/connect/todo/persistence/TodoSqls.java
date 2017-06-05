package kr.or.connect.todo.persistence;

public class TodoSqls {
	static final String SELECT_BY_ID = "SELECT id, todo, completed, date FROM TODO where id = :id";
	static final String SELECT_ALL = "SELECT id, todo, completed, date  FROM Todo";
	static final String SELECT_Completed = "SELECT id, todo, completed, date  FROM Todo where completed = 1";
	static final String SELECT_Uncompleted = "SELECT id, todo, completed, date  FROM Todo where completed = 0";
	static final String DELETE_BY_ID = "DELETE FROM TODO WHERE id= :id";
	static final String DELETE_BY_Completed = "DELETE FROM TODO WHERE completed = 1";
	static final String UPDATE = "UPDATE Todo SET\n" + "completed = :completed \n" + "WHERE id = :id";
}
