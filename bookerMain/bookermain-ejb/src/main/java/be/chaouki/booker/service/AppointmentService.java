package be.chaouki.booker.service;

import java.util.Calendar;
import java.util.List;

import be.chaouki.booker.entities.Doctor;
import be.chaouki.booker.entities.Patient;
import be.chaouki.booker.entities.Appointment;
import be.chaouki.booker.entities.Department;

public interface AppointmentService {
	

	public final static int APPOINTMENT_DURATION_LIMIT=120; // in minutes
	
	/** <p><b>FR:</b> Enregistrement d'un nouveau rdv.</p>
	 *  
	 *  <p><b>EN:</b> Attempt to register a new appointment. Returns true if the attempt succeeded. False if
	 *  there was a conflict of schedule.</p>
	 * @param patient
	 * @param department
	 * @param doctor
	 * @param time
	 * @param duration Duration in minutes
	 * @return
	 */
	boolean registerAppointment(Patient patient, Doctor doctor, Calendar time, Integer duration);
	
	/** <p><b>FR:</b> Enregistrement d'un nouveau rdv. Il faut que la liste doctors
	 * du department soit déja fetchée.</p>
	 *  
	 *  <p><b>EN:</b> Attempt to register a new appointment. Returns true if the attempt succeeded. False if
	 *  there was a conflict of schedule. The doctors from the department needs to be already initialized</p>
	 * @param patient
	 * @param department
	 * @param doctor
	 * @param time
	 * @param duration Duration in minutes
	 * @return
	 */
	boolean registerAppointment(Patient patient, Department department, Calendar time, Integer duration);
	
	/** newPat.id and newDoc.id <b>cannot</b> be null
	 * 
	 * @param id
	 * @param newPat
	 * @param newDoc
	 * @param newTim
	 * @param newDur
	 * @return
	 */
	boolean modifyAppointment(Integer id, Patient newPat, Doctor newDoc, Calendar newTim, Integer newDur);
	
	boolean modifyAppointment(Integer id, Patient newPat, Doctor newDoc,
			Calendar newTimeStart, Calendar newTimeEnd);
	
	void deleteAppointment(Appointment appointment);
	
	void deleteAppointment(Integer id);
	
	List<Appointment> getAppointmentByPatient(Patient patient);
	
	List<Appointment> getAppointmentByDoctor(Doctor doctor);


	
}
