package com.techelevator.projects.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.exception.DaoException;
import com.techelevator.projects.model.Project;

public class
JdbcProjectDao implements ProjectDao {

	private final String PROJECT_SELECT = "SELECT p.project_id, p.name, p.from_date, p.to_date FROM project p";

	private final JdbcTemplate jdbcTemplate;

	public JdbcProjectDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Project getProjectById(int projectId) {
		Project project = null;
		String sql = PROJECT_SELECT +
				" WHERE p.project_id=?";
		try {
			SqlRowSet results = jdbcTemplate.queryForRowSet(sql, projectId);
			if (results.next()) {
				project = mapRowToProject(results);
			}
		}catch (CannotGetJdbcConnectionException e){
			throw new DaoException("Unable to connect to server or host", e);
		} catch (DataIntegrityViolationException e){
			throw new DaoException("Data integrity violation occurs", e);
		}
		return project;
	}

	@Override
	public List<Project> getProjects() {
		List<Project> allProjects = new ArrayList<>();
		String sql = PROJECT_SELECT;
		try {
			SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
			while (results.next()) {
				Project projectResult = mapRowToProject(results);
				allProjects.add(projectResult);
			}
		} catch (CannotGetJdbcConnectionException e){
			throw new DaoException("Unable to connect to server or host", e);
		} catch (DataIntegrityViolationException e){
			throw new DaoException("Data integrity violation occurs", e);
		}

		return allProjects;
		}

	@Override
	public Project createProject(Project newProject) {
		Project newProjects = null;
		String sql = "insert into project (name, from_date, to_date) " +"values (?, ?, ?) returning project_id;";
		try{
			int newProjectId = jdbcTemplate.queryForObject(sql, int.class, newProject.getName(), newProject.getFromDate(), newProject.getToDate());
			newProjects = getProjectById(newProjectId);
		}catch (CannotGetJdbcConnectionException e){
		throw new DaoException("Unable to connect to server or host", e);
	} catch (DataIntegrityViolationException e){
		throw new DaoException("Data integrity violation occurs", e);
	}
		return newProjects;
	}
	
	@Override
	public void linkProjectEmployee(int projectId, int employeeId) {
		String sql = "insert into project_employee(project_id, employee_id)" + "values(?, ?);";
		try {
			jdbcTemplate.update(sql, projectId, employeeId);
		} catch (CannotGetJdbcConnectionException e){
			throw new DaoException("Unable to connect to server or host", e);
		} catch (DataIntegrityViolationException e){
			throw new DaoException("Data integrity violation occurs", e);
		}
	}

	@Override
	public void unlinkProjectEmployee(int projectId, int employeeId) {
		String sql = "Delete from project_employee where project_id = ? and employee_id = ?;";
		try {
			jdbcTemplate.update(sql, projectId, employeeId);
		} catch (CannotGetJdbcConnectionException e){
			throw new DaoException("Unable to connect to server or host", e);
		} catch (DataIntegrityViolationException e){
			throw new DaoException("Data integrity violation occurs", e);
		}
	}

	@Override
	public Project updateProject(Project project) {
		Project updatedProject = null;
		String sql = "update project set project_id = ?, name = ?, from_date = ?, to_date = ? where project_id = ?;";
		try{
			int updatedRows = jdbcTemplate.update(sql, project.getId(), project.getName(), project.getFromDate(), project.getToDate(), project.getId());
			if(updatedRows == 0){
				throw new DaoException("Unable to update project");
			} else {
				updatedProject = getProjectById(project.getId());
			}
		} catch (CannotGetJdbcConnectionException e){
			throw new DaoException("Unable to connect to server or host", e);
		} catch (DataIntegrityViolationException e){
			throw new DaoException("Data integrity violation occurs", e);
		}
		return updatedProject;
	}
	@Override
	public int deleteProjectById(int projectId) {
		int numOfRows = 0;
		String deleteProjectSql = "Delete from project where project_id = ?;";
		String deleteProjEmployeeSql = "Delete from project_employee where project_id = ?;";
		try{
			jdbcTemplate.update(deleteProjEmployeeSql, projectId);
			numOfRows = jdbcTemplate.update(deleteProjectSql, projectId);
		} catch (CannotGetJdbcConnectionException e){
			throw new DaoException("Unable to connect to server or host", e);
		} catch (DataIntegrityViolationException e){
			throw new DaoException("Data integrity violation occurs", e);
		}
		return numOfRows;
	}
	
	private Project mapRowToProject(SqlRowSet results) {
		Project project = new Project();
		project.setId(results.getInt("project_id"));
		project.setName(results.getString("name"));
		if (results.getDate("from_date") != null) {
			project.setFromDate(results.getDate("from_date").toLocalDate());
		}
		if (results.getDate("to_date") != null) {
			project.setToDate(results.getDate("to_date").toLocalDate());
		}
		return project;
	}

}
