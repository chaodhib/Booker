package be.chaouki.booker.dao.jpa;

import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import be.chaouki.booker.dao.AppointmentDAO;
import be.chaouki.booker.entities.Doctor;
import be.chaouki.booker.entities.Patient;
import be.chaouki.booker.entities.Appointment;

public class AppointmentDAOJPA implements AppointmentDAO {
	
	@Inject
    private EntityManager em;
	
	@Inject
    private Logger logger;
	
	@Override
	public void add(Appointment appointment) {
		logger.info("Prise de rendez-vous pour "+appointment.getPatient().getName()+ "à "+appointment.getTimeStart().getTime());
		em.persist(appointment);
	}

	@Override
	public Appointment findById(Integer id) {
		return em.find(Appointment.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Appointment> findAll() {
		Query query=em.createQuery("FROM Appointment");
		return query.getResultList();
	}

	@Override
	public List<Appointment> findByPatient(Patient patient) {
		Patient managedPatient=em.merge(patient);
		managedPatient.getAppointments().size(); // fetch the appointments
		return managedPatient.getAppointments();
	}

	@Override
	public List<Appointment> findByDoctor(Doctor doctor) {
		Doctor managedDoctor=em.merge(doctor);
		managedDoctor.getAppointments().size(); // fetch the appointments
		return managedDoctor.getAppointments();
	}
	
	@Override
	public List<Appointment> findByDoctorFromTo(Doctor doctor, Calendar from,
			Calendar to) {
		String queryS=
				  "SELECT DISTINCT Appointment "
				+ "FROM Appointment appointment "
				+ "WHERE appointment.doctor.name LIKE :doctor AND :from <= appointment.timeStart AND appointment.timeStart < :to "
				+ "OR appointment.doctor.name LIKE :doctor AND :from < appointment.timeEnd AND appointment.timeEnd <= :to ";
		TypedQuery<Appointment> query=em.createQuery(queryS, Appointment.class);
		query.setParameter("doctor", doctor.getName() + "%");
		query.setParameter("from", from);
		query.setParameter("to", to);
		return query.getResultList();
	}

	@Override
	public List<Appointment> findByPatientFromTo(Patient patient, Calendar from,
			Calendar to) {
		String queryS=
				  "SELECT DISTINCT Appointment "
				+ "FROM Appointment appointment "
				+ "WHERE appointment.patient.name LIKE :patient AND :from <= appointment.timeStart AND appointment.timeStart < :to "
				+ "OR appointment.patient.name LIKE :patient AND :from < appointment.timeEnd AND appointment.timeEnd <= :to ";
		TypedQuery<Appointment> query=em.createQuery(queryS, Appointment.class);
		query.setParameter("patient", patient.getName() + "%");
		query.setParameter("from", from);
		query.setParameter("to", to);
		return query.getResultList();
	}

	@Override
	public void modify(Appointment appointment) {
		logger.info("Appointment Dao - modif appointment id: "+appointment.getId());
		Appointment appointmentManaged=findById(appointment.getId());
		if(appointmentManaged!=null){
			em.merge(appointment);
			logger.info("Modif appointment ok");
		}
		else
			logger.info("Modif appointment NOT ok");
	}

	@Override
	public void remove(Appointment appointment) {
		em.remove(em.merge(appointment));
	}
	
	@Override
	public void remove(Integer id) {
		Appointment toRemove=findById(id);
		if(toRemove!=null)
			em.remove(toRemove);;
	}

	@PostConstruct
	private void init(){
		logger.info("Readying "+this.getClass().getName()+" bean");
	}
	
	@PreDestroy
	private void post(){
		logger.info("Destroying "+this.getClass().getName()+" bean");
	}


}
