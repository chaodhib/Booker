package be.chaouki.booker.rest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import be.chaouki.booker.entities.Doctor;
import be.chaouki.booker.entities.Patient;
import be.chaouki.booker.entities.Appointment;
import be.chaouki.booker.entities.Department;
import be.chaouki.booker.service.DataService;
import be.chaouki.booker.service.AppointmentService;

@Path("/appointment")
@RequestScoped
public class AppointmentResourceRESTService {

	@Inject private Logger logger;
	@Inject private DataService dataService;
	@Inject private AppointmentService appointmentService;
	
	@Path("/patient/{name}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Appointment> listAppointmentByPatient(@PathParam("name") String name){
		List<Appointment> appointments=dataService.getPatientByName(name).getAppointments();
		for(Appointment appointment:appointments){
			appointment.setPatient(null);
			appointment.getDoctor().setAppointments(null);
			appointment.getDoctor().setDepartment(null);
		}
		
		return appointments;
	}
	
	@Path("/doctor/{name}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Appointment> listAppointmentByDoctor(@PathParam("name") String name){
		List<Appointment> appointments=dataService.getDoctorByName(name).getAppointments();
		for(Appointment appointment:appointments){
			appointment.getPatient().setAppointments(null);
			appointment.setDoctor(null);
		}
		
		return appointments;
	}
	
	@Path("/new/by_doctor/{doctor}/{patient}/{start}/{duration}")
	@PUT
	@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
	public boolean prendreAppointmentParDoctor(
			@PathParam("patient") String patientName,
			@PathParam("doctor") String doctorName,
			@PathParam("start") String startString,
			@PathParam("duration") String durationString){
		try {
			Patient patient=dataService.getPatientByName(patientName);
			Doctor doctor=dataService.getDoctorByName(doctorName);
			
			Calendar debut = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.FRANCE);
			debut.setTime(sdf.parse(startString));
		
			Integer duree=Integer.parseInt(durationString);
			
			logger.info("Prise de appointment par REST");
			logger.info(patient.getName()+" - "+doctor.getName()+" - "+debut.getTime()+" - "+duree);
			return appointmentService.registerAppointment(patient, doctor, debut, duree);
		} catch (Exception e) {
			return false;
		}
	}
	
	@Path("/new/by_department/{department}/{patient}/{start}/{duration}")
	@PUT
	@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
	public boolean prendreAppointmentPardepartment(
			@PathParam("patient") String patientName,
			@PathParam("department") String departmentName,
			@PathParam("start") String startString,
			@PathParam("duration") String durationString){
		try {
			Patient patient=dataService.getPatientByName(patientName);
			Department service=dataService.getDepartmentByName(departmentName);
			
			Calendar debut = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.FRANCE);
			debut.setTime(sdf.parse(startString));
		
			Integer duree=Integer.parseInt(durationString);
			
			logger.info("Prise de rdv par REST");
			logger.info(patient.getName()+" - "+service.getName()+" - "+debut.getTime()+" - "+duree);
			return appointmentService.registerAppointment(patient, service, debut, duree);
		} catch (Exception e) {
			return false;
		}
	}

	@Path("/modifiy")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
	public boolean modifierAppointment(Appointment appointment){
		logger.info("Modification de rdv par REST");
		logger.info(appointment.toString());
		if(entityAppointmentValid(appointment)){
			appointmentService.modifyAppointment(appointment.getId(), appointment.getPatient(), 
					appointment.getDoctor(), appointment.getTimeStart(), appointment.getTimeEnd());
			return true;
		}
		else
			return false;
	}
	
	@Path("/delete")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
	public boolean deleteAppointment(Appointment appointment){
		logger.info("Suppression de rdv par REST");
		logger.info(appointment.toString());
		if(entityAppointmentValid(appointment)){
			appointmentService.deleteAppointment(appointment);
			return true;
		}
		else
			return false;
	}
	
	@Path("/delete/{id}")
	@DELETE
	@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
	public boolean deleteAppointmentById(@PathParam("id") String idString){
		logger.info("Suppression de rdv par REST");
		
		try {
			Integer id = Integer.parseInt(idString);
			if(id<=0)
				return false;
			appointmentService.deleteAppointment(id);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
		
	}
	
	private boolean entityAppointmentValid(Appointment appointment){
		if(appointment.getId()!=null 
				&& appointment.getDoctor()!=null 
				&& appointment.getDoctor().getId()!=null 
				&& appointment.getPatient()!=null 
				&& appointment.getPatient().getId()!=null 
				&& appointment.getTimeStart()!=null
				&& appointment.getTimeEnd()!=null)
			return true;
		else
			return false;
	}
}
