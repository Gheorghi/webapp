package com.endava.webapp.repository;

import com.endava.webapp.configuration.Datasource;
import com.endava.webapp.model.Employee;
import com.endava.webapp.repository.exceptions.ResourceNotFoundException;
import com.endava.webapp.repository.exceptions.UniqueConstraintException;
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
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final String SELECT_UNIQUENESS = "select * from employees where <?> like upper(?) or <?> like lower(?)";
    private final String SELECT_EMPLOYEE_BY_ID = "select * from employees where employee_id = ?";
    private final String SELECT_ALL_EMPLOYEES = "select * from employees";
    private final String DELETE_EMPLOYEE_BY_ID = "delete from employees where employee_id = ?";
    private final String UPDATE_EMPLOYEE = "update employees set first_name=?, last_name=?,department_Id=?, " +
            "email = ?,  phone_Number=?, salary = ? where employee_id = ?";
    private final String INSERT_EMPLOYEE =
            "insert into employees(first_name, last_name, department_id, email, phone_number, salary) " +
                    "values (?, ?, ?, ?, ?, ?)";
    private final String[] columnNames = new String[]{"employee_Id", "first_Name", "last_Name",
            "department_Id", "email", "phone_Number", "salary"};


    private final Datasource datasource;

    public Employee addEmployee(final Employee employee) {
        try (var stmt = datasource.getConnection()
                .prepareStatement(INSERT_EMPLOYEE, columnNames)) {
            stmt.setString(1, employee.getFirstName());
            stmt.setString(2, employee.getLastName());
            stmt.setInt(3, employee.getDepartmentId());
            stmt.setString(4, employee.getEmail());
            stmt.setString(5, employee.getPhoneNumber());
            stmt.setBigDecimal(6, employee.getSalary());
            int affectedRows = stmt.executeUpdate();
            datasource.getConnection().commit();

            if (affectedRows == 0) {
                throw new SQLException("Creating employee failed, no rows affected.");
            }
            try (var rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    var employeeInserted = new Employee();
                    employeeInserted.setEmployeeId(rs.getInt(1));
                    employeeInserted.setFirstName(rs.getString(2));
                    employeeInserted.setLastName(rs.getString(3));
                    employeeInserted.setDepartmentId(rs.getInt(4));
                    employeeInserted.setEmail(rs.getString(5));
                    employeeInserted.setPhoneNumber(rs.getString(6));
                    employeeInserted.setSalary(rs.getBigDecimal(7));
                    return employeeInserted;
                } else {
                    throw new SQLException("Creating employee failed, details were not obtained.");
                }
            }
        } catch (final SQLException e) {
            return throwProperException(e);
        }
    }

    public Employee updateEmployee(final Employee employee, final int id) {
        try (var stmt = datasource.getConnection().prepareStatement(UPDATE_EMPLOYEE,
                columnNames)) {
            stmt.setString(1, employee.getFirstName());
            stmt.setString(2, employee.getLastName());
            stmt.setInt(3, employee.getDepartmentId());
            stmt.setString(4, employee.getEmail());
            stmt.setString(5, employee.getPhoneNumber());
            stmt.setBigDecimal(6, employee.getSalary());
            stmt.setInt(7, id);
            int affectedRows = stmt.executeUpdate();
            datasource.getConnection().commit();

            if (affectedRows == 0) {
                throw new SQLException("Updating employee failed, no rows affected.");
            }
            try (var rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    var employeeInserted = new Employee();
                    employeeInserted.setEmployeeId(rs.getInt(1));
                    employeeInserted.setFirstName(rs.getString(2));
                    employeeInserted.setLastName(rs.getString(3));
                    employeeInserted.setDepartmentId(rs.getInt(4));
                    employeeInserted.setEmail(rs.getString(5));
                    employeeInserted.setPhoneNumber(rs.getString(6));
                    employeeInserted.setSalary(rs.getBigDecimal(7));
                    return employeeInserted;
                } else {
                    throw new SQLException("Updating employee failed, details were not obtained.");
                }
            }
        } catch (final SQLException e) {
            return throwProperException(e);
        }
    }

    public Employee getEmployeeById(final int id) {
        try (var stmt = datasource.getConnection().prepareStatement(SELECT_EMPLOYEE_BY_ID)) {
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                var employee = new Employee();
                employee.setEmployeeId(rs.getInt("employee_Id"));
                employee.setFirstName(rs.getString("first_Name"));
                employee.setLastName(rs.getString("last_Name"));
                employee.setDepartmentId(rs.getInt("department_Id"));
                employee.setEmail(rs.getString("email"));
                employee.setPhoneNumber(rs.getString("phone_Number"));
                employee.setSalary(rs.getBigDecimal("salary"));
                return employee;
            }
        } catch (final SQLException e) {
            log.error("SQLException, caused by {}", e.getMessage());
        }
        throw new ResourceNotFoundException("Resource not found exception");
    }

    public List<Employee> getEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        try (var stmt = datasource.getConnection().prepareStatement(SELECT_ALL_EMPLOYEES)) {
            var rs = stmt.executeQuery();
            while (rs.next()) {
                var employee = new Employee();
                employee.setEmployeeId(rs.getInt("employee_Id"));
                employee.setFirstName(rs.getString("first_Name"));
                employee.setLastName(rs.getString("last_Name"));
                employee.setDepartmentId(rs.getInt("department_Id"));
                employee.setEmail(rs.getString("email"));
                employee.setPhoneNumber(rs.getString("phone_Number"));
                employee.setSalary(rs.getBigDecimal("salary"));
                employeeList.add(employee);
            }
            return employeeList;
        } catch (final SQLException e) {
            log.error("SQLException, caused by {}", e.getMessage());
        }
        return employeeList;
    }

    public void deleteEmployee(final int id) {
        Connection connection = datasource.getConnection();
        try (var stmt = connection.prepareStatement(DELETE_EMPLOYEE_BY_ID)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            connection.commit();

            if (affectedRows == 0) {
                throw new SQLException("Deleting employee failed, no rows affected.");
            }
        } catch (final SQLException e) {
            throw new RuntimeException("Exception msg: " + e.getMessage());
        }
    }

    public boolean isInColumn(final String column, final String value) {
        try (var stmt = datasource.getConnection()
                .prepareStatement(SELECT_UNIQUENESS.replace("<?>", column))) {
            stmt.setString(1, value);
            stmt.setObject(2, value);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                return false;
            }
            return true;
        } catch (final SQLException e) {
            log.error("SQLException, caused by {}", e.getMessage());
        }
        throw new ResourceNotFoundException("Resource not found exception");
    }

    private Employee throwProperException(final SQLException e) {
        String exceptionMSG = e.getMessage().toLowerCase();
        if (exceptionMSG.contains("unique constraint")) {
            if (exceptionMSG.contains("email")) {
                exceptionMSG = "Validation failed: Email is already registered";
            }
            if (exceptionMSG.contains("phone_number")) {
                exceptionMSG = "Validation failed: Phone number is already registered";
            }
            throw new UniqueConstraintException(exceptionMSG);
        } else
            throw new RuntimeException("Exception msg: " + e.getMessage());
    }
}
