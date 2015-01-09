package be.chaouki.booker.backingbeans;

import java.util.List;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import be.chaouki.booker.entities.Doctor;
import be.chaouki.booker.entities.Patient;
import be.chaouki.booker.entities.Department;
import be.chaouki.booker.service.DataService;

@SuppressWarnings("unused")
@ApplicationScoped
public class DataProducer {
	@Inject private DataService dataService;
	@Inject private Logger logger;
	
	@Produces
	@Named
	private List<Doctor> doctors;
	
	@Produces
	@Named
    private List<Department> departments;
	
	@Produces
	@Named
    private List<Patient> patients;
    
    @PostConstruct
    private void init(){
    	doctors=dataService.getDoctors();
    	departments=dataService.getDepartmentsAndFetch();
    	patients=dataService.getPatients();
    }

}
