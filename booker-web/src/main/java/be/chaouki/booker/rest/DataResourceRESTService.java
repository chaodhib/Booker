package be.chaouki.booker.rest;

import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import be.chaouki.booker.entities.Department;
import be.chaouki.booker.entities.Doctor;
import be.chaouki.booker.entities.Patient;
import be.chaouki.booker.service.DataService;

@Path("/data")
@RequestScoped
public class DataResourceRESTService {

	@Inject private Logger logger;
	@Inject private DataService dataService;
	
	@Path("/patients")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Patient> listAllPatients(){ 
		
		List<Patient> patients=dataService.getPatients();
		for(Patient patient:patients){		//enlever les references cycliques
			patient.setAppointments(null);
		}
		return patients;
	}
	
	@Path("/doctors")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Doctor> listAllDoctors(){
		
		List<Doctor> doctors = dataService.getDoctors();
		for(Doctor doctor:doctors){		//enlever les references cycliques
			doctor.setAppointments(null);
			doctor.getDepartment().setDoctors(null);
		}
		return doctors;
	}
	
	@Path("/services")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Department> listAlldepartments(){
		
		List<Department> services = dataService.getDepartments();
		for(Department service:services){	//enlever les references cycliques
			service.setDoctors(null);
		}
		return services;
	}

}
