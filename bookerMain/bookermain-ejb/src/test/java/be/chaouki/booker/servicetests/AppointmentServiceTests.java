package be.chaouki.booker.servicetests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
import be.chaouki.booker.service.MessagingService;
import be.chaouki.booker.service.impl.AppointmentServiceImpl;

public class AppointmentServiceTests {

	private AppointmentDAO appointmentDaoMock;
	private PatientDAO patientDaoMock;
	private DoctorDAO doctorDaoMock;
	private MessagingService messagingServiceMock;
	
	private AppointmentServiceImpl beanToTest;
	private Patient patient;
	private Doctor doctor;
	private Department department;
	
	@Before
	public void setUp(){
		patient=new Patient();
		patient.setId(0);
		patient.setName("Francois");
		patient.setAppointments(new ArrayList<Appointment>());
		
		department=new Department();
		department.setExternal(false);
		department.setId(0);
		department.setName("Chosologie");
		department.setDoctors(new ArrayList<Doctor>());
		
		doctor=new Doctor();
		doctor.setId(0);
		doctor.setName("D Erp");
		doctor.setAppointments(new ArrayList<Appointment>());
		doctor.setDepartment(department);
		department.getDoctors().add(doctor);
		
		appointmentDaoMock=Mockito.mock(AppointmentDAO.class);
		beanToTest=new AppointmentServiceImpl(appointmentDaoMock, patientDaoMock, doctorDaoMock, messagingServiceMock);
	}
	
	@Test
	public void registerAppointmentBeforeEndOfLastForPatientTest() {
		// scenario: the patient has an appointment that finishes in 1 hour.
		Calendar start=Calendar.getInstance();
		
		Calendar startPreviousApmt=Calendar.getInstance();
		startPreviousApmt.add(Calendar.HOUR, -1);
		Calendar endPreviousApmt=Calendar.getInstance();
		endPreviousApmt.add(Calendar.HOUR, 1);
		
		Appointment apmt=new Appointment(patient, doctor, startPreviousApmt, endPreviousApmt);
		patient.getAppointments().add(apmt);
		
		boolean appointmentTaken=beanToTest.registerAppointment(patient, doctor, start, AppointmentServiceImpl.APPOINTMENT_DURATION_LIMIT+1);
		assertFalse(appointmentTaken);
	}
	
	@Test
	public void registerAppointmentOverTimeLimitTest() {
		Calendar time=Calendar.getInstance();
		boolean appointmentTaken=beanToTest.registerAppointment(patient, doctor, time, AppointmentServiceImpl.APPOINTMENT_DURATION_LIMIT+1);
		assertFalse(appointmentTaken);
	}
	
	@Test
	public void registerAppointmentTest(){
		// scenario: we want to schedule an appointment for tomorrow from 1PM to 2PM. Both patient and doctor are available.
		Calendar start=Calendar.getInstance();
		start.add(Calendar.DAY_OF_MONTH, 1);
		start.setTime(new Date(0, 0, 0, 13, 0, 0));// bad code but i'm in a hurry and tired
		Calendar end=(Calendar) start.clone(); // bad code but i'm in a hurry and tired
		end.add(Calendar.HOUR_OF_DAY, 1);
		
		Mockito.when(appointmentDaoMock.findByPatientFromTo(patient, start, end)).thenReturn(new ArrayList<Appointment>());
		Mockito.when(appointmentDaoMock.findByDoctorFromTo(doctor, start, end)).thenReturn(new ArrayList<Appointment>());
//		Mockito.when(appointmentDaoMock.add(appointment)).thenReturn(void);
		
		boolean appointmentTaken=beanToTest.registerAppointment(patient, doctor, start, 60);
		assertTrue(appointmentTaken);
	}
}
