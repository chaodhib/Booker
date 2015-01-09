package be.chaouki.booker.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.model.TreeNode;

import be.chaouki.booker.entities.Department;
import be.chaouki.booker.entities.Doctor;
import be.chaouki.booker.entities.Patient;
import be.chaouki.booker.service.AppointmentService;
import be.chaouki.booker.service.DataService;

@Model
public class AppointmentController {
	
	@Inject private Logger logger;
	@Inject private FacesContext facesContext;
	@Inject private DataService dataService;
	@Inject private AppointmentService appointmentService;
	
	private String selectedPatientName;
	private Patient selectedPatient;
	
	private TreeNode selectedNode;
	private Department selectedDepartment;
	private Doctor selectedDoctor;
	
	private Date selectedTime;
	private Calendar selectedTimeCal;
	
	private Integer selectedDuration=30;
	
//	private int selectedDuree2
	

	
	@PostConstruct
	private void init(){
		logger.info("Readying "+this.getClass().getName()+" bean");
//		selectedNode=new DefaultTreeNode();
		this.selectedDepartment=null;
		this.selectedDoctor=null;
	}
	
	@PreDestroy
	private void post(){
		logger.info("Destroying "+this.getClass().getName()+" bean");
	}
	
	public String processNewAppointment(){
		
        if(selectedNode == null || selectedPatientName==null ||  selectedPatientName.isEmpty() || selectedTime==null 
        		|| selectedDuration==null) {
        	facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Paramètres manquant!","Paramètres manquant!"));
			return "index";
        }
        
    	prepAppointment();
    	
    	boolean priseAppointmentOk=false;
    	if(selectedDoctor!=null)
    		priseAppointmentOk=appointmentService.registerAppointment(selectedPatient, selectedDoctor, selectedTimeCal, selectedDuration);
    	else if(selectedDepartment!=null)
    		priseAppointmentOk=appointmentService.registerAppointment(selectedPatient, selectedDepartment, selectedTimeCal, selectedDuration);
    	
    	if(priseAppointmentOk){
    		facesContext.addMessage(null, new FacesMessage("Rdv pris!"));
    	} 
    	else{
    		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Problème d'indisponibilité!","Problème d'indisponibilité!"));
    	}
        
        return "index";
	}

	public void modifyAppointment(Integer id){
        if(selectedNode == null || selectedPatientName==null ||  selectedPatientName.isEmpty() || selectedTime==null 
        		|| selectedDuration==null) {
        	facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Paramètres manquant!","Paramètres manquant!"));
			return;
        }
        
        prepAppointment();
        
        if(selectedDoctor==null){
        	facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Selection de service non admise!","Selection de service non admise!"));
			return;
        }

		boolean modifAppointmentOk=appointmentService.modifyAppointment(id, selectedPatient, selectedDoctor, selectedTimeCal, selectedDuration);
		if(modifAppointmentOk){
    		facesContext.addMessage(null, new FacesMessage("Rdv modifié!"));
    	} 
    	else{
    		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Problème d'indisponibilité!","Problème d'indisponibilité!"));
    	}
	}
	
	private void prepAppointment() {
		selectedPatient=dataService.getPatientByName(selectedPatientName);
		logger.info("Traitement de l'input d'un rdv"
				+ " patient: " + selectedPatient
				+ " service/docteurr: " + selectedNode);
		
		if(selectedNode.getData() instanceof Doctor){
			logger.info("Node de type docteur");
			selectedDoctor=(Doctor) selectedNode.getData();
		}
		else if(selectedNode.getData() instanceof Department){
			logger.info("Node de type service");
			selectedDepartment= (Department) selectedNode.getData();
		}
		else
			throw new IllegalArgumentException();
		
		// conversion Date -> Calendar
		selectedTimeCal=Calendar.getInstance();
		selectedTimeCal.setTime(selectedTime);
		logger.info("Temps "+selectedTimeCal);
		
		logger.info("Duree "+selectedDuration);
	}

	public String getSelectedPatientName() {
		return selectedPatientName;
	}

	public void setSelectedPatientName(String selectedPatientName) {
		this.selectedPatientName = selectedPatientName;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public Date getSelectedTime() {
		return selectedTime;
	}

	public void setSelectedTime(Date selectedTime) {
		this.selectedTime = selectedTime;
	}

	public Integer getSelectedDuration() {
		return selectedDuration;
	}

	public void setSelectedDuration(Integer selectedDuration) {
		this.selectedDuration = selectedDuration;
	}
	
}
