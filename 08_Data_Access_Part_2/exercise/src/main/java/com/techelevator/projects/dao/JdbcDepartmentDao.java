package com.techelevator.projects.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.techelevator.projects.model.Employee;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.exception.DaoException;
import com.techelevator.projects.model.Department;

public class JdbcDepartmentDao implements DepartmentDao {

	private final String DEPARTMENT_SELECT = "SELECT d.department_id, d.name FROM department d ";
	
	private final JdbcTemplate jdbcTemplate;

	public JdbcDepartmentDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Department getDepartmentById(int id) {
		Department department = null;
		String sql = DEPARTMENT_SELECT +
				" WHERE d.department_id = ?";
		try {
			SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
			if (results.next()) {
				department = mapRowToDepartment(results);
			}
		}catch (CannotGetJdbcConnectionException e){
			throw new DaoException("Unable to connect to server or database", e);
		}

		return department;
	}

	@Override
	public List<Department> getDepartments() {
		List<Department> departments = new ArrayList<>();
		String sql = DEPARTMENT_SELECT;
		try {
			SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
			if(results.next()) {
				departments.add(mapRowToDepartment(results));
			}
		}catch (CannotGetJdbcConnectionException e){
			throw new DaoException("Unable to connect to server or database", e);
		}
		return departments;
	}

	@Override
	public Department createDepartment(Department department) {
		Department departments = null;
		String sql = "insert into department (name) " + "values (?) returning department_id";
		try{
			int newDepartments = jdbcTemplate.queryForObject(sql, int.class, department.getName());
			departments = getDepartmentById(newDepartments);
		}catch(CannotGetJdbcConnectionException | DataIntegrityViolationException e) {
			throw new DaoException("Unable to connect to server or database", e);
		}
		return departments;
	}

	@Override
	public Department updateDepartment(Department department) {
		Department updatedDepartment = null;
		String sql = "update department set department_id = ?, name = ? where department_id = ?";
		try {
			int rowsUpdated = jdbcTemplate.update(sql, department.getId(), department.getName(), department.getId());
			if (rowsUpdated > 0) {
				updatedDepartment = getDepartmentById(department.getId());
			} else {
				throw new DaoException("updateDepartment() not implemented");
			}
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to server or database", e);
		} catch (DataIntegrityViolationException e) {
			throw new DaoException("data integrity violation occurs", e);
		}
		return updatedDepartment;
		}

	@Override
	public int deleteDepartmentById(int id) {
		int numOfRows = 0;
		String deleteProjectEmployeeSql = "Delete from project_employee where employee_id in (select employee_id from employee where department_id =?);";
		String deleteEmployeeSql = "Delete from employee where department_id = ?;";
		String deleteDepartmentSql = "Delete from department where department_id = ?;";
		try {
			jdbcTemplate.update(deleteProjectEmployeeSql, id);
			jdbcTemplate.update(deleteEmployeeSql, id);
			numOfRows = jdbcTemplate.update(deleteDepartmentSql, id);
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to server or database", e);
		} catch (DataIntegrityViolationException e) {
			throw new DaoException("Data integrity violation", e);
		}
		return numOfRows;
	}

	private Department mapRowToDepartment(SqlRowSet results) {
		Department department = new Department();
		department.setId(results.getInt("department_id"));
		department.setName(results.getString("name"));
		return department;
	}

}
