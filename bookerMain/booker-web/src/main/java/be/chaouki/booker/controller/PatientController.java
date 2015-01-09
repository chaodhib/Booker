package be.chaouki.booker.controller;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.inject.Model;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

import be.chaouki.booker.backingbeans.ModifyAppointment;
import be.chaouki.booker.entities.Patient;
import be.chaouki.booker.entities.Appointment;
import be.chaouki.booker.service.DataService;
import be.chaouki.booker.service.AppointmentService;

@Named
@ViewScoped
public class PatientController implements Serializable {

	private static final long serialVersionUID = 6317481352543256830L;
	@Inject transient private Logger logger;
	@Inject transient private FacesContext facesContext;
	@Inject transient private DataService dataService;
	@Inject transient private AppointmentService appointmentService;
	
	private List<Appointment> appointmentsToDisplay;
	
	private String selectedPatientName;
	private Patient selectedPatient;
	
	@PostConstruct
	private void init(){
		logger.info("Readying "+this.getClass().getName()+" bean");
	}
	
	@PreDestroy
	private void post(){
		logger.info("Destroying "+this.getClass().getName()+" bean");
	}
	
	
	public void loadApptsOfPatient(){
		logger.info("!!! Appel de loadApptsOfPatient!!!");
		if(selectedPatientName!=null && !selectedPatientName.isEmpty()){
			selectedPatient=dataService.getPatientByName(selectedPatientName);
			appointmentsToDisplay=appointmentService.getAppointmentByPatient(selectedPatient);
			
			logger.info(appointmentsToDisplay.size() + " rdvs trouvés");
			if(appointmentsToDisplay.size()>1){
					Collections.sort(appointmentsToDisplay);
			}
		}
	}
	
	public void deleteAppointment(Appointment appointment){
		appointmentService.deleteAppointment(appointment);
		loadApptsOfPatient();
	}

	public String getSelectedPatientName() {
		return selectedPatientName;
	}

	public void setSelectedPatientName(String selectedPatientName) {
		this.selectedPatientName = selectedPatientName;
	}

	public List<Appointment> getAppointmentsToDisplay() {
		return appointmentsToDisplay;
	}

	public void setAppointmentsToDisplay(List<Appointment> appointmentsToDisplay) {
		this.appointmentsToDisplay = appointmentsToDisplay;
	}
	
	
}
