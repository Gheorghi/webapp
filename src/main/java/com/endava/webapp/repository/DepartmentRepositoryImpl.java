package com.endava.webapp.repository;

import com.endava.webapp.model.Department;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DepartmentRepositoryImpl implements DepartmentRepository {

    private final EntityManager entityManager;

    @Override
    public Department getDepartmentById(final int id) {
        return entityManager.find(Department.class, id);
    }

    @Override
    public List<Department> getDepartments() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Department> criteriaQuery = criteriaBuilder.createQuery(Department.class);
        Root<Department> employeeRoot = criteriaQuery.from(Department.class);
        criteriaQuery.select(employeeRoot);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    @Transactional
    public Department addDepartment(final Department department) {
        return entityManager.merge(department);
    }

    @Override
    @Transactional
    public Department updateDepartment(final Department department, final int id) {
        return entityManager.merge(department);
    }

    @Override
    @Transactional
    public void deleteDepartment(final int id) {
        entityManager.remove(getDepartmentById(id));
        entityManager.flush();
        entityManager.clear();
    }
}
