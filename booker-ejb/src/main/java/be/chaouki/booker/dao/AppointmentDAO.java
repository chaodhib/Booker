package be.chaouki.booker.dao;

import java.util.Calendar;
import java.util.List;

import be.chaouki.booker.entities.*;

public interface AppointmentDAO {
	// C
	void add(Appointment appointment);
	
	// R
	Appointment findById(Integer id);
	List<Appointment> findAll();
	List<Appointment> findByDoctorFromTo(Doctor doctor, Calendar from, Calendar to);
	List<Appointment> findByPatientFromTo(Patient patient, Calendar from, Calendar to);
	List<Appointment> findByPatient(Patient patient);
	List<Appointment> findByDoctor(Doctor doctor);
	
	// U
	void modify(Appointment appointment);
	
	// D
	void remove(Appointment appointment);
	void remove(Integer id);
}
