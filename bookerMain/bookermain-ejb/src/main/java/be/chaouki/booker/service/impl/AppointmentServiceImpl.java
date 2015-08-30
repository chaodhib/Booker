package be.chaouki.booker.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import be.chaouki.booker.dao.AppointmentDAO;
import be.chaouki.booker.dao.DoctorDAO;
import be.chaouki.booker.dao.PatientDAO;
import be.chaouki.booker.entities.Appointment;
import be.chaouki.booker.entities.Department;
import be.chaouki.booker.entities.Doctor;
import be.chaouki.booker.entities.Patient;
import be.chaouki.booker.service.AppointmentService;
import be.chaouki.booker.service.MessagingService;

/**
 * Session Bean implementation class AppointmentDepartmentImpl
 */
@Stateless
public class AppointmentServiceImpl implements AppointmentService {
	
	@Inject private AppointmentDAO appointmentDAO;
	@Inject private PatientDAO patientDAO;
	@Inject private DoctorDAO doctorDAO;
	
	@Inject private MessagingService messagingService;
	
	@Inject private Logger logger;
	
	public AppointmentServiceImpl() {}

	// for unit testing
	public AppointmentServiceImpl(AppointmentDAO appointmentDAO, PatientDAO patientDAO, DoctorDAO doctorDAO,
			MessagingService messagingService, Logger logger) {
		super();
		this.appointmentDAO = appointmentDAO;
		this.patientDAO = patientDAO;
		this.doctorDAO = doctorDAO;
		this.messagingService = messagingService;
		this.logger = logger;
	}

	@Override
	public boolean registerAppointment(Patient patient, Doctor doctor, Calendar time, Integer duration) {
		// Check des parametres
		if(patient ==null ||doctor==null||time==null ||duration==null)
			throw new IllegalArgumentException();
		if(time.before(Calendar.getInstance()) || duration>APPOINTMENT_DURATION_LIMIT)
			return false;// si le rdv demandé est pour une date postérieure ou si la duree de rdv demandée est trop grande
		
		// Creation et initialisation de la date de fin de rdv
		Calendar fin=(Calendar) time.clone();
		fin.add(Calendar.MINUTE, duration);
		
		//Check dispo patient
		List<Appointment> appointments=appointmentDAO.findByPatientFromTo(patient, time, fin);
		if(appointments.size()!=0)
			return false;
		
		//Check dispo docteur
		appointments=appointmentDAO.findByDoctorFromTo(doctor, time, fin);
		if(appointments.size()!=0)
			return false;
		
		Appointment appointment=new Appointment(patient, doctor, time, fin);
    	try {
    		appointmentDAO.add(appointment);
    		logger.info("Prise de rdv réussie");
			return true;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "L'enregistrement d'un rdv dans la DB a échoué", e);
			return false;
		}
	}
	
	@Override
	public boolean registerAppointment(Patient patient, Department department, Calendar time, Integer duration) {
		// Check des parametres
		if(department==null || time==null || duration==null)
			throw new IllegalArgumentException();
		
		// Instantiation et calcul de la date de fin de rdv
		Calendar timeEnd=(Calendar) time.clone();
		timeEnd.add(Calendar.MINUTE, duration);
		
		// Si le service (department) est interne, check des dispos de tous les docteurs un par un. 
		Doctor doctor=null;
		List<Appointment> appointments=null;
		if(!department.getExternal()){
			boolean isAppointmentPossible=false;
			for(Doctor docCandidate : department.getDoctors()){
				appointments=appointmentDAO.findByDoctorFromTo(docCandidate, time, timeEnd);
				if(appointments.size()==0){
					isAppointmentPossible=true;
					doctor=docCandidate;
					break;
				}
			}
			if(!isAppointmentPossible)
				return false;
		}
		else {	// Si le service est externe, envoi d'une demande de rdv
			messagingService.sendAppointmentRequest(patient, department, time, duration);
			return true;
		}
		
		return  registerAppointment(patient, doctor, time, duration);
	}
	
	@Override
	public boolean modifyAppointment(Integer id, Patient newPat, Doctor newDoc,
			Calendar newTimeStart, Calendar newTimeEnd) {
		if(id==null || newPat==null || newPat.getId()==null || newDoc==null || newDoc.getId()==null
				|| newTimeStart==null || newTimeEnd==null)
			throw new IllegalArgumentException();
		
		// A. Check dispo patient et doctor pour les nouvelles heures
		List<Appointment> appointments=null;
		
		// A.1 Verif patient
		// complete les infos de l'entity recue en parametre
		Patient patientDB=patientDAO.findById(newPat.getId());
		if(patientDB==null)
			return false;
		
		appointments=appointmentDAO.findByPatientFromTo(patientDB, newTimeStart, newTimeEnd);
		if(appointments.size()>1)
			return false;
		if(appointments.size()==1){ 
			// ignore le appointment en cours de modification dans la gestion des disponibilites
			if(appointments.get(0).getId()!=id)
				return false;
		}
		
		// A.2 Verif docteur
		// complete les infos de l'entity recue en parametre
		Doctor doctorDB=doctorDAO.findById(newDoc.getId());
		if(doctorDB==null)
			return false;
		
		appointments=appointmentDAO.findByDoctorFromTo(doctorDB, newTimeStart, newTimeEnd);
		if(appointments.size()>1)
			return false;
		if(appointments.size()==1){
			// ignore le appointment en cours de modification dans la gestion des disponibilites
			if(appointments.get(0).getId()!=id)
				return false;
		}
		
		Appointment appointment=new Appointment(patientDB, doctorDB, newTimeStart, newTimeEnd);
		appointment.setId(id);
		try {
			appointmentDAO.modify(appointment);
			logger.info("Modification de rdv réussie");
			return true;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "La modification d'un rdv dans la DB a échoué", e);
			return false;
		}
	}
	
	@Override
	public boolean modifyAppointment(Integer id, Patient newPat, Doctor newDoc,
			Calendar newTimeStart, Integer newDur) {
		if(newTimeStart==null || newDur==null)
			throw new IllegalArgumentException();
		
		// Instantiation et calcul de la date de fin de rdv
		Calendar newTimeEnd=(Calendar) newTimeStart.clone();
		newTimeEnd.add(Calendar.MINUTE, newDur);
		
		return modifyAppointment(id, newPat, newDoc, newTimeStart, newTimeEnd);
	}

	@Override
	public List<Appointment> getAppointmentByPatient(Patient patient) {
		return appointmentDAO.findByPatient(patient);
	}

	@Override
	public List<Appointment> getAppointmentByDoctor(Doctor doctor) {
		return appointmentDAO.findByDoctor(doctor);
	}

	@Override
	public void deleteAppointment(Appointment appointment) {
		appointmentDAO.remove(appointment.getId());
	}

	@Override
	public void deleteAppointment(Integer id) {
		appointmentDAO.remove(id);
	}

}
