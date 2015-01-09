package be.chaouki.booker.service.impl;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.inject.Inject;

import be.chaouki.booker.dao.DepartmentDAO;
import be.chaouki.booker.dao.DoctorDAO;
import be.chaouki.booker.dao.PatientDAO;
import be.chaouki.booker.entities.Department;
import be.chaouki.booker.entities.Doctor;
import be.chaouki.booker.entities.Patient;
import be.chaouki.booker.service.DataService;

@Singleton
public class DataServiceImpl implements DataService {

	@Inject
	private DoctorDAO doctorDAO;
	@Inject 
	private DepartmentDAO departmentDAOy;
	@Inject 
	private PatientDAO patientDAO;
	
    @Inject private Logger logger;
    
    private List<Doctor> doctors;
    private List<Doctor> doctorsFetched;
    private List<Department> departments;
    private List<Department> departmentsFetched;
    private List<Patient> patients;
    private List<Patient> patientsFetched;
    
    @Override
	public List<Doctor> getDoctors() {
		return doctors;
	}

	@Override
	public List<Department> getDepartments() {
		return departments;
	}

	@Override
	public List<Patient> getPatients() {
		return patients;
	}

	@Override
	public List<Doctor> getDoctorsAndFetch() {
		return doctorsFetched;
	}

	@Override
	public List<Department> getDepartmentsAndFetch() {
		return departmentsFetched;
	}

	@Override
	public List<Patient> getPatientsAndFetch() {
		return patientsFetched;
	}
    
	@Override
	public Patient getPatientByName(String name){
		return patientDAO.findByName(name);
	}
	
    @PostConstruct
    private void init() {
    	logger.info("Readying "+this.getClass().getName()+" bean");
    	doctors=doctorDAO.findAll();
    	doctorsFetched=doctorDAO.findAllAndFetch();
    	departments=departmentDAOy.findAll();
    	departmentsFetched=departmentDAOy.findAllAndFetch();
    	patients=patientDAO.findAll();
    	patientsFetched=patientDAO.findAllAndFetch();
    	
    }
	
	@PreDestroy
	private void post(){
		logger.info("Destroying "+this.getClass().getName()+" bean");
	}

	@Override
	public Doctor getDoctorByName(String name) {
		return doctorDAO.findByName(name);
	}

	@Override
	public Department getDepartmentByName(String name) {
		return departmentDAOy.findByName(name);
	}
	
}
