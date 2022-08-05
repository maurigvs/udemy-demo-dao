package org.example.model.dao;

import org.example.model.entities.Department;

import java.util.List;

public interface DepartmentDAO {

    void insert(Department obj);

    void update(Department obj);
    
    void deletebyId(Integer id);
    
    Department findById(Integer id);

    List<Department> findAll();
}
