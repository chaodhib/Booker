package be.chaouki.booker.dao;

import java.util.List;

import be.chaouki.booker.entities.Patient;

public interface PatientDAO {
	List<Patient> findAll();
	List<Patient> findAllAndFetch();
	Patient findById(Integer id);
	Patient findByName(String name);
}
