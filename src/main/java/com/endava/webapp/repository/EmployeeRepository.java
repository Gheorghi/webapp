package com.endava.webapp.repository;

import com.endava.webapp.model.Employee;
import com.endava.webapp.configuration.DBConnectorManager;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Optional;

@Repository
public class EmployeeRepository {

    private final String SELECT_EMPLOYEE_BY_ID = "select * from employees where employee_id = ?";
    private final String SELECT_ALL_EMPLOYEES = "select * from employees";
    private final String DELETE_EMPLOYEE_BY_ID = "delete from employees where employee_id = ?";
    private final String UPDATE_EMPLOYEES_BY_ID = "update employees set salary = ?, commission_pct=?, manager_id=? where employee_id = ?";

    public Optional<Employee> getEmployeeById(int id) {
        try (var stmt = DBConnectorManager.getInstance().getConnection().prepareStatement(SELECT_EMPLOYEE_BY_ID)) {
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                var employee = new Employee();
                employee.setEmployeeId(rs.getInt("employee_Id"));
                employee.setFirstName(rs.getString("first_Name"));
                employee.setLastName(rs.getString("last_Name"));
                employee.setEmail(rs.getString("email"));
                employee.setPhoneNumber(rs.getString("phone_Number"));
                employee.setHireDate(rs.getDate("hire_Date"));
                employee.setSalary(rs.getBigDecimal("salary"));
                employee.setCommissionPct(rs.getBigDecimal("commission_Pct"));
                employee.setManagerId(rs.getInt("manager_Id"));
                employee.setDepartmentId(rs.getInt("department_Id"));
                return Optional.of(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

//    public static void main(String[] args) {
//        EmployeeDAO employeeRepository = new EmployeeDAO();
//        var con = DBConnectorManager.getInstance().getConnection();
//        var from = employeeRepository.getEmployeeById(102);
//    }

}
