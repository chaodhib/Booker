package be.chaouki.bookermod.service;

import javax.ejb.Stateless;

@Stateless
public class AppointmentService {

	public boolean registerAppointment(String patient, String department, String timeStart, String timeEnd) {
		// Parsing vers les objects utilisés par le programme externe
		// ...
		
		// Appel aux methodes de services et de dao en utilisant le programme du campus externe
		// ...
		
		return true;
	}
}
