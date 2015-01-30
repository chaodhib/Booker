package be.chaouki.booker.controller;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import be.chaouki.booker.entities.Doctor;
import be.chaouki.booker.entities.Appointment;
import be.chaouki.booker.service.AppointmentService;
import be.chaouki.booker.service.DataService;
import be.chaouki.booker.service.AppointmentService;

@Named
@ViewScoped
public class DoctorController implements Serializable {

	private static final long serialVersionUID = -6836858601671642667L;
	@Inject transient private Logger logger;
	@Inject transient private FacesContext facesContext;
	@Inject transient private DataService dataService;
	@Inject transient private AppointmentService appointmentService;
	
	private List<Appointment> appointmentsToDisplay;
	
	private String selectedDoctorName;
	private Doctor selectedDoctor;
	
	@PostConstruct
	private void init(){
		logger.info("Readying "+this.getClass().getName()+" bean");
	}
	
	@PreDestroy
	private void post(){
		logger.info("Destroying "+this.getClass().getName()+" bean");
	}
	
	
	public void loadApptsOfDoctor(){
		logger.info("!!! Appel de loadApptsOfDoctor!!!");
		if(selectedDoctorName!=null && !selectedDoctorName.isEmpty()){
			selectedDoctor=dataService.getDoctorByName(selectedDoctorName);
			appointmentsToDisplay=appointmentService.getAppointmentByDoctor(selectedDoctor);
			
			logger.info(appointmentsToDisplay.size() + " rdvs trouvés");
			if(appointmentsToDisplay.size()>1){
					Collections.sort(appointmentsToDisplay);
			}
		}
	}
	
	public void deleteAppointment(Appointment appointment){
		appointmentService.deleteAppointment(appointment);
		loadApptsOfDoctor();
	}

	public String getSelectedDoctorName() {
		return selectedDoctorName;
	}

	public void setSelectedDoctorName(String selectedDoctorName) {
		this.selectedDoctorName = selectedDoctorName;
	}

	public List<Appointment> getAppointmentsToDisplay() {
		return appointmentsToDisplay;
	}

	public void setAppointmentsToDisplay(List<Appointment> appointmentsToDisplay) {
		this.appointmentsToDisplay = appointmentsToDisplay;
	}
	
	
}
