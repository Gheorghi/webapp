package com.endava.webapp.repository;

import com.endava.webapp.configuration.Datasource;
import com.endava.webapp.model.Department;
import com.endava.webapp.repository.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DepartmentRepositoryImpl implements DepartmentRepository {
    private final String SELECT_DEPARTMENT_BY_ID = "select * from departments where department_id = ?";
    private final String SELECT_ALL_DEPARTMENTS = "select * from departments";
    private final String DELETE_DEPARTMENT_BY_ID = "delete from departments where department_id = ?";
    private final String UPDATE_DEPARTMENT =
            "update departments set department_name = ?, location = ? where department_id = ?";
    private final String INSERT_DEPARTMENT = "insert into departments(department_name, location) values (?, ?)";
    private final String[] columnNames = new String[]{"department_id", "department_name", "location"};

    private final Datasource datasource;

    public Department getDepartmentById(final int id) {
        try (var stmt = datasource.getConnection().prepareStatement(SELECT_DEPARTMENT_BY_ID)) {
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                var department = new Department();
                department.setId(rs.getInt("department_id"));
                department.setName(rs.getString("department_name"));
                department.setLocation(rs.getString("location"));
                return department;
            }
        } catch (final SQLException e) {
            log.error("SQLException, caused by {}", e.getMessage());
        }
        throw new ResourceNotFoundException("Resource not found exception");
    }

    public List<Department> getDepartments() {
        List<Department> departmetnsList = new ArrayList<>();
        try (var stmt = datasource.getConnection().prepareStatement(SELECT_ALL_DEPARTMENTS)) {
            var rs = stmt.executeQuery();
            while (rs.next()) {
                var department = new Department();
                department.setId(rs.getInt("department_id"));
                department.setName(rs.getString("department_name"));
                department.setLocation(rs.getString("location"));
                departmetnsList.add(department);
            }
            return departmetnsList;
        } catch (final SQLException e) {
            log.error("SQLException, caused by {}", e.getMessage());
        }
        return departmetnsList;
    }

    public Department addDepartment(final Department department) {
        Connection connection = datasource.getConnection();
        try (var stmt = connection.prepareStatement(INSERT_DEPARTMENT, columnNames)) {
            stmt.setString(1, department.getName());
            stmt.setString(2, department.getLocation());
            int affectedRows = stmt.executeUpdate();
            connection.commit();

            if (affectedRows == 0) {
                throw new SQLException("Creating department failed, no rows affected.");
            }
            try (var rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    var departmentInserted = new Department();
                    departmentInserted.setId(rs.getInt(1));
                    departmentInserted.setName(rs.getString(2));
                    departmentInserted.setLocation(rs.getString(3));
                    return departmentInserted;
                } else {
                    throw new SQLException("Creating department failed, details were not obtained.");
                }
            }
        } catch (final SQLException e) {
            throw new RuntimeException("Exception msg: " + e.getMessage());
        }
    }

    public Department updateDepartment(final Department department, final int id) {
        Connection connection = datasource.getConnection();
        try (var stmt = connection.prepareStatement(UPDATE_DEPARTMENT, columnNames)) {
            stmt.setString(1, department.getName());
            stmt.setString(2, department.getLocation());
            stmt.setInt(3, id);
            int affectedRows = stmt.executeUpdate();
            connection.commit();

            if (affectedRows == 0) {
                throw new SQLException("Updating department failed, no rows affected.");
            }
            try (var rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    var departmentInserted = new Department();
                    departmentInserted.setId(rs.getInt(1));
                    departmentInserted.setName(rs.getString(2));
                    departmentInserted.setLocation(rs.getString(3));
                    return departmentInserted;
                } else {
                    throw new SQLException("Updating department failed, details were not obtained.");
                }
            }
        } catch (final SQLException e) {
            throw new RuntimeException("Exception msg: " + e.getMessage());
        }
    }

    public void deleteDepartment(final int id) {
        Connection connection = datasource.getConnection();
        try (var stmt = connection.prepareStatement(DELETE_DEPARTMENT_BY_ID)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            connection.commit();

            if (affectedRows == 0) {
                throw new SQLException("Deleting department failed, no rows affected.");
            }
        } catch (final SQLException e) {
            throw new RuntimeException("Exception msg: " + e.getMessage());
        }
    }
}
