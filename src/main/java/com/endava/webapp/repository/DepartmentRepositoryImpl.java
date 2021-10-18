package com.endava.webapp.repository;

import com.endava.webapp.model.Department;
import com.endava.webapp.repository.exceptions.DeletionFailedException;
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
public class DepartmentRepositoryImpl implements DepartmentRepository {
    private EntityManager entityManager;

    @Autowired
    private DepartmentRepositoryImpl(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Department getDepartmentById(final int id) {
        return entityManager.find(Department.class, id);
    }

    @Override
    @Transactional
    public List<Department> getDepartments() {
        Query theQuery = entityManager.createQuery("from departments");
        return theQuery.getResultList();
    }

    @Override
    @Transactional
    public Department addDepartment(final Department department) {
        return entityManager.merge(department);
    }

    @Override
    @Transactional
    public Department updateDepartment(final Department department, final int id) {
        Query theQuery = entityManager
                .createQuery(
                        "update departments set department_name = :depName, " +
                                "location = :loc where department_id = :id");
        theQuery.setParameter("depName", department.getName());
        theQuery.setParameter("loc", department.getLocation());
        theQuery.setParameter("id", id);
        int affectedRows = theQuery.executeUpdate();
        if (affectedRows == 0) {
            throw new UpdateFailedException("Updating department failed, no rows affected.");
        }
        return getDepartmentById(id);
    }

    @Override
    @Transactional
    public void deleteDepartment(final int id) {
        Query theQuery = entityManager
                .createQuery(
                        "delete from departments where department_id = :id");
        theQuery.setParameter("id", id);
        int affectedRows = theQuery.executeUpdate();
        if (affectedRows == 0) {
            throw new DeletionFailedException("Deletion department failed, no rows affected.");
        }
    }
}
