package com.endava.webapp.repository;

import com.endava.webapp.model.Employee;
import com.endava.webapp.repository.exceptions.DeletionFailedException;
import com.endava.webapp.repository.exceptions.UniqueConstraintException;
import com.endava.webapp.repository.exceptions.UpdateFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeRepository {
    private EntityManager entityManager;

    @Autowired
    private EmployeeRepositoryImpl(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Employee addEmployee(final Employee employee) {
        return entityManager.merge(employee);
    }

    @Override
    @Transactional
    public Employee updateEmployee(final Employee employee, final int id) {
        Query theQuery = entityManager
                .createQuery(
                        "update employees set " +
                                "first_name=:first_name, last_name=:last_name, " +
                                "department_Id=:department_Id, email = :email,  " +
                                "phone_Number=:phone_Number, salary = :salary " +
                                "where employee_id = :employee_id");
        theQuery.setParameter("first_name", employee.getFirstName());
        theQuery.setParameter("last_name", employee.getLastName());
        theQuery.setParameter("department_Id", employee.getDepartmentId());
        theQuery.setParameter("email", employee.getEmail());
        theQuery.setParameter("phone_Number", employee.getPhoneNumber());
        theQuery.setParameter("salary", employee.getSalary());
        theQuery.setParameter("employee_id", id);
        int affectedRows = theQuery.executeUpdate();
        if (affectedRows == 0) {
            throw new UpdateFailedException("Updating department failed, no rows affected.");
        }
        return getEmployeeById(id);
    }

    @Override
    @Transactional
    public Employee getEmployeeById(final int id) {
        return entityManager.find(Employee.class, id);
    }

    @Override
    @Transactional
    public List<Employee> getEmployees() {
        Query theQuery = entityManager.createQuery("from employees");
        return theQuery.getResultList();
    }

    @Override
    @Transactional
    public void deleteEmployee(final int id) {
        Query theQuery = entityManager
                .createQuery("delete from employees where employee_id = :id");
        theQuery.setParameter("id", id);
        int affectedRows = theQuery.executeUpdate();
        if (affectedRows == 0) {
            throw new DeletionFailedException("Deletion employee failed, no rows affected.");
        }
    }

    @Override
    public final boolean isEmailExist(final String value) {
        return this.getEmployees().stream()
                .noneMatch(empl -> empl.getEmail().equalsIgnoreCase(value));
    }

    @Override
    public final boolean isPhoneExist(final String value) {
        return this.getEmployees().stream()
                .noneMatch(empl -> empl.getPhoneNumber().equalsIgnoreCase(value));
    }
}
