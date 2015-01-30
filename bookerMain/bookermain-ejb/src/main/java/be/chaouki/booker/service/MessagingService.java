package be.chaouki.booker.service;

import java.util.Calendar;
import java.util.List;

import be.chaouki.booker.entities.Department;
import be.chaouki.booker.entities.Patient;


public interface MessagingService {

	void sendAppointmentRequest(Patient patient, Department department, Calendar time, Integer duration);

	List<Object> getUntreatedNegativeReplies();
}
