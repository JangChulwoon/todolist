package kr.or.connect.todo.persistence;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.vo.Todo;

@Repository
public class TodoDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<Todo> rowMapper = BeanPropertyRowMapper.newInstance(Todo.class);

	
	// Connection 과 매핑을 할때 사용함

	private static final String SELECT_BY_ID =
			"SELECT id, todo, completed, date FROM TODO where id = :id";
	private static final String DELETE_BY_ID = "DELETE FROM TODO WHERE id= :id";
	private static final String DELETE_BY_Completed = "DELETE FROM TODO WHERE completed = 1";
	private static final String COUNT_Todo = "SELECT COUNT(*) FROM Todo";
	private static final String UPDATE =
			"UPDATE Todo SET\n"
			+ "completed = :completed \n"
			+ "WHERE id = :id";
	
	private static final String SELECT_ALL =
			"SELECT id, todo, completed, date  FROM Todo";

	private static final String SELECT_Completed =
			"SELECT id, todo, completed, date  FROM Todo where completed = 1";

	private static final String SELECT_Uncompleted =
			"SELECT id, todo, completed, date  FROM Todo where completed = 0";
	
	public TodoDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("todo")
				.usingGeneratedKeyColumns("id")
				.usingColumns("todo","completed","date");
	}
	


	public List<Todo> selectAll() {
		Map<String, Object> params = Collections.emptyMap();
		return jdbc.query(SELECT_ALL, params, rowMapper);
	}
	
	public List<Todo> selectCompleted() {
		Map<String, Object> params = Collections.emptyMap();
		return jdbc.query(SELECT_Completed, params, rowMapper);
	}
	
	public List<Todo> selectUncompleted() {
		Map<String, Object> params = Collections.emptyMap();
		return jdbc.query(SELECT_Uncompleted, params, rowMapper);
	}
	

	

	// key value를 반환한다. 
	public Integer insert(Todo todo) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(todo);
		return insertAction.executeAndReturnKey(params).intValue();
	}
	
	
	public int countTodos() {
		Map<String, Object> params = Collections.emptyMap();
		return jdbc.queryForObject(COUNT_Todo, params, Integer.class);
	}
	
	public Todo selectById(Integer id) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		return jdbc.queryForObject(SELECT_BY_ID, params, rowMapper);
	}
	

	public int deleteById(Integer id) {
			Map<String, ?> params = Collections.singletonMap("id", id);
			return jdbc.update(DELETE_BY_ID, params);
	}
	
	// 완료 목록을 삭제하는 부분
	public int deleteCompletedAll() {
		Map<String, Object> params = Collections.emptyMap();
		return jdbc.update(DELETE_BY_Completed, params);
	}
	
	
	// complete 상태를 바꾸는 update 
	public int update(Todo todo) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(todo);
		return jdbc.update(UPDATE, params);
	}
	

}
