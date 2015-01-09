package be.chaouki.booker.dao;

import java.util.List;

import be.chaouki.booker.entities.Department;

public interface DepartmentDAO {
	List<Department> findAll();
	List<Department> findAllAndFetch();
	Department findById(Integer id);
	Department findByName(String name);
}
