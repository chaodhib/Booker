package be.chaouki.booker.service;

import java.util.List;

import be.chaouki.booker.entities.Doctor;
import be.chaouki.booker.entities.Patient;
import be.chaouki.booker.entities.Department;

/**
 * <p>Methodes pour acceder aux donnees statiques de l'application
 * L'état de la base de donnée est inchangée avant et après</p>
 * 
 * <p>Methods that access the static datas of the appication.
 * In the current version of the application, the entities 
 * of type Doctor, Patient and Department cannot be changed. 
 * The state of the DB is unchanged by the calls to this interface's methods.</p>
 * @author Chaouki
 *
 */
public interface DataService {

	
	public abstract List<Doctor> getDoctors();

	public abstract List<Department> getDepartments();

	public abstract List<Patient> getPatients();
	
	public abstract List<Doctor> getDoctorsAndFetch();
	
	public abstract List<Department> getDepartmentsAndFetch();
	
	public abstract List<Patient> getPatientsAndFetch();

	public abstract Patient getPatientByName(String name);

	public abstract Doctor getDoctorByName(String name);

	public abstract Department getDepartmentByName(String name);

}