package com.endava.webapp.configuration;

import com.endava.webapp.model.Department;
import com.endava.webapp.model.Employee;
import com.endava.webapp.repository.DepartmentRepository;
import com.endava.webapp.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommandLineAppStartupRunner implements CommandLineRunner {
    @Autowired
    DepartmentRepository departmentRepository;

    public CommandLineAppStartupRunner(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Autowired
    EmployeeRepository employeeRepository;

    public CommandLineAppStartupRunner(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @Override
    public void run(String... args) {
        log.info("------- START TO FILL DEPARTMENTS TABLE-------");
        departmentRepository.addDepartment(new Department(1, "Administration", "Moldova"));
        departmentRepository.addDepartment(new Department(2, "Marketing", "Amsterdam"));
        departmentRepository.addDepartment(new Department(3, "Purchasing", "Berlin"));
        departmentRepository.addDepartment(new Department(4, "Human Resources", "USA"));
        departmentRepository.addDepartment(new Department(5, "Shipping", "Brazil"));
        departmentRepository.addDepartment(new Department(6, "IT", "China"));
        departmentRepository.addDepartment(new Department(7, "Public Relations", "Madagascar"));
        departmentRepository.addDepartment(new Department(8, "Sales", "Italy"));

        log.info("------- START TO FILL EMPLOYEES TABLE-------");
        employeeRepository
                .addEmployee(new Employee(1, "Steven", "King",
                        departmentRepository.getDepartmentById(1), "sking@com.f",
                        "515.123.4567", BigDecimal.valueOf(24000)));

        employeeRepository.addEmployee(new Employee(2, "Neena", "Kochhar",
                departmentRepository.getDepartmentById(2), "nkochhar@com.d",
                "515.123.4568", BigDecimal.valueOf(17000)));

        employeeRepository.addEmployee(new Employee(3, "Lex", "De Haan",
                departmentRepository.getDepartmentById(3), "ldehaan@com.d",
                "515.123.4569", BigDecimal.valueOf(14000)));

        employeeRepository.addEmployee(new Employee(4, "Alexander", "Hunold",
                departmentRepository.getDepartmentById(5), "ahunold@com.d",
                "590.423.4567", BigDecimal.valueOf(4011)));

        employeeRepository.addEmployee(new Employee(5, "Bruce", "Ernst",
                departmentRepository.getDepartmentById(6), "bernst@com.d",
                "590.423.4568", BigDecimal.valueOf(6065)));

        employeeRepository.addEmployee(new Employee(6, "David", "King",
                departmentRepository.getDepartmentById(4), "daustin@com.d",
                "590.423.4569", BigDecimal.valueOf(4800)));

        employeeRepository.addEmployee(new Employee(7, "Austin", "King",
                departmentRepository.getDepartmentById(7), "king@com.com",
                "857.246.9877", BigDecimal.valueOf(21054)));

        employeeRepository.addEmployee(new Employee(8, "Valli", "Pataballa",
                departmentRepository.getDepartmentById(8), "vpatabal@com.d",
                "590.423.4560", BigDecimal.valueOf(42800)));

        employeeRepository.addEmployee(new Employee(9, "Diana", "Lorentz",
                departmentRepository.getDepartmentById(5), "dlorentz@com.d",
                "590.423.5567", BigDecimal.valueOf(41100)));

        employeeRepository.addEmployee(new Employee(10, "Nancy", "Greenberg",
                departmentRepository.getDepartmentById(2), "ngreenbe@com.d",
                "515.124.4569", BigDecimal.valueOf(200)));

        employeeRepository.addEmployee(new Employee(11, "Daniel", "Faviet",
                departmentRepository.getDepartmentById(1), "dfaviet@com.d",
                "515.124.4169", BigDecimal.valueOf(9000)));
    }
}
