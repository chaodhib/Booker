package be.chaouki.booker.servicetests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import be.chaouki.booker.dao.AppointmentDAO;
import be.chaouki.booker.dao.DoctorDAO;
import be.chaouki.booker.dao.PatientDAO;
import be.chaouki.booker.entities.Appointment;
import be.chaouki.booker.entities.Department;
import be.chaouki.booker.entities.Doctor;
import be.chaouki.booker.entities.Patient;
import be.chaouki.booker.service.AppointmentService;
import be.chaouki.booker.service.MessagingService;
import be.chaouki.booker.service.impl.AppointmentServiceImpl;

public class AppointmentServiceTests {

	private AppointmentDAO appointmentDaoMock;
	private PatientDAO patientDaoMock;
	private DoctorDAO doctorDaoMock;
	private MessagingService messagingServiceMock;
	private Logger logger;
	
	private AppointmentService beanToTest;
	private Patient patient1;
	private Patient patient2;
	private Doctor doctor1;
	private Doctor doctor2;
	private Department department;
	
	@Before
	public void setUp(){
		patient1=new Patient();
		patient1.setId(0);
		patient1.setName("Francois");
		patient1.setAppointments(new ArrayList<Appointment>());
		
		patient2=new Patient();
		patient2.setId(1);
		patient2.setName("Chaouki");
		patient2.setAppointments(new ArrayList<Appointment>());
		
		department=new Department();
		department.setExternal(false);
		department.setId(0);
		department.setName("Chosologie");
		department.setDoctors(new ArrayList<Doctor>());
		
		doctor1=new Doctor();
		doctor1.setId(0);
		doctor1.setName("D Erp");
		doctor1.setAppointments(new ArrayList<Appointment>());
		doctor1.setDepartment(department);
		department.getDoctors().add(doctor1);
		
		doctor2=new Doctor();
		doctor2.setId(1);
		doctor2.setName("H aha");
		doctor2.setAppointments(new ArrayList<Appointment>());
		doctor2.setDepartment(department);
		department.getDoctors().add(doctor2);
		
		appointmentDaoMock=Mockito.mock(AppointmentDAO.class);
		patientDaoMock=Mockito.mock(PatientDAO.class);
		doctorDaoMock=Mockito.mock(DoctorDAO.class);
		messagingServiceMock=Mockito.mock(MessagingService.class);
		logger=Mockito.mock(Logger.class);
		beanToTest=new AppointmentServiceImpl(appointmentDaoMock, patientDaoMock, doctorDaoMock, messagingServiceMock, logger);
	}
	
	@Test
	public void registerSingleAppointmentTest(){
		// scenario: we want to schedule an appointment for tomorrow from 1PM to 2PM. Both patient and doctor are completely free.
		final int DURATION=60; // in minutes
		Calendar start=Calendar.getInstance();
		start.add(Calendar.DAY_OF_MONTH, 1);
		start.set(Calendar.HOUR_OF_DAY, 13);
		
		Calendar end=Calendar.getInstance();
		start.add(Calendar.DAY_OF_MONTH, 1);
		start.set(Calendar.HOUR_OF_DAY, 14);
		
		Mockito.when(appointmentDaoMock.findByPatientFromTo(patient1, start, end)).thenReturn(new ArrayList<Appointment>());
		Mockito.when(appointmentDaoMock.findByDoctorFromTo(doctor1, start, end)).thenReturn(new ArrayList<Appointment>());
		
		boolean appointmentTaken=beanToTest.registerAppointment(patient1, doctor1, start, 60);
		assertTrue(appointmentTaken);
	}
	
	
	@Test
	public void registerAppointmentBeforeEndOfLastForPatientTest() {
		// scenario: the patient has an appointment that finishes in 1 hour from now and has a duration of 2 hours.
		// we want to try to make another appointment for that patient now (duration 1 hour)
		// appt1 between patient 1 and doc 1 (in this test, we assume that its registered and confirmed)
		// appt2 between patient 1 and doc 2 (in this test, we are trying to register this)
		
		// appointment 1
		final int DURATION_1=120; // in minutes
		Calendar start1=Calendar.getInstance();
		start1.add(Calendar.MINUTE, -DURATION_1/2);
		Calendar end1=Calendar.getInstance();
		end1.add(Calendar.MINUTE, DURATION_1/2);
		
		// appointment 2
		final int DURATION_2=60; // in minutes
		Calendar start2=Calendar.getInstance();
		Calendar end2=Calendar.getInstance();
		end2.add(Calendar.MINUTE, DURATION_2);
		
		Appointment apmt1=new Appointment(patient1, doctor1, start1, end1);
		patient1.getAppointments().add(apmt1);
		
		// appointments of patient1 between start2 and end2
		List<Appointment> patient1Apps=new ArrayList<Appointment>();
		patient1Apps.add(apmt1);
		
		Mockito.when(appointmentDaoMock.findByPatientFromTo(patient1, start2, end2)).thenReturn(patient1Apps);
		Mockito.when(appointmentDaoMock.findByDoctorFromTo(doctor2, start2, end2)).thenReturn(new ArrayList<Appointment>());
		
		boolean appointmentTaken=beanToTest.registerAppointment(patient1, doctor2, start2, DURATION_2);
		assertFalse(appointmentTaken);
	}
	
	@Test
	public void registerAppointmentOverTimeLimitTest() {
		// try to register an appointment with a duration above the limit.
		final int DURATION=AppointmentService.APPOINTMENT_DURATION_LIMIT+1; // in minutes
		
		Calendar start=Calendar.getInstance();
		Calendar end=Calendar.getInstance();
		end.add(Calendar.MINUTE, DURATION);
		
		Mockito.when(appointmentDaoMock.findByPatientFromTo(patient1, start, end)).thenReturn(new ArrayList<Appointment>());
		Mockito.when(appointmentDaoMock.findByDoctorFromTo(doctor1, start, end)).thenReturn(new ArrayList<Appointment>());
		boolean appointmentTaken=beanToTest.registerAppointment(patient1, doctor1, start, DURATION);
		assertFalse(appointmentTaken);
	}
}
