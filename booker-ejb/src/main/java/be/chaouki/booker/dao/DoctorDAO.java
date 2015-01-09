package be.chaouki.booker.dao;

import java.util.List;

import be.chaouki.booker.entities.Doctor;

public interface DoctorDAO {
	List<Doctor> findAll();
	List<Doctor> findAllAndFetch();
	Doctor findById(Integer id);
	Doctor findByName(String name);
}
