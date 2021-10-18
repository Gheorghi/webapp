package com.endava.webapp.repository;

import com.endava.webapp.model.Employee;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeRepository {
    private final EntityManager entityManager;

    @Override
    @Transactional
    public Employee addEmployee(final Employee employee) {
        return entityManager.merge(employee);
    }

    @Override
    @Transactional
    public Employee updateEmployee(final Employee employee, final int id) {
        return entityManager.merge(employee);
    }

    @Override
    public Employee getEmployeeById(final int id) {
        return entityManager.find(Employee.class, id);
    }

    @Override
    public List<Employee> getEmployees() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);
        criteriaQuery.select(employeeRoot);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    @Transactional
    public void deleteEmployee(final int id) {
        entityManager.remove(getEmployeeById(id));
        entityManager.flush();
        entityManager.clear();
    }

    @Override
    public boolean isEmailUnique(final String email) {
        CriteriaBuilder qb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        val root = cq.from(Employee.class);
        Predicate emailPredicate = qb.equal(root.get("email"), email);
        cq.select(qb.count(root));
        cq.where(emailPredicate);
        return entityManager.createQuery(cq).getSingleResult() == 0;
    }

    @Override
    public boolean isPhoneUnique(final String phone) {
        CriteriaBuilder qb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        val root = cq.from(Employee.class);
        Predicate phonePredicate = qb.equal(root.get("phoneNumber"), phone);
        cq.select(qb.count(root));
        cq.where(phonePredicate);
        return entityManager.createQuery(cq).getSingleResult() == 0;
    }
}
