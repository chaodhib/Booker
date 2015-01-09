package be.chaouki.booker.entities;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity implementation class for Entity: Appointment
 *
 */
@Entity

public class Appointment implements Serializable, Comparable<Appointment> {
	
	private static final long serialVersionUID = -8641555923956214149L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	private Patient patient;
	
	@ManyToOne
	private Doctor doctor;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar timeStart;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar timeEnd;
	
	public Appointment() {
		super();
	}

	public Appointment(Patient patient, Doctor doctor,
			Calendar timeStart, Calendar timeEnd) {
		super();
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.doctor = doctor;
		this.patient = patient;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Calendar getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Calendar timeStart) {
		this.timeStart = timeStart;
	}

	public Calendar getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Calendar timeEnd) {
		this.timeEnd = timeEnd;
	}

	@Override
	public int compareTo(Appointment appointment) {
		return this.timeStart.compareTo(appointment.timeStart);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((doctor == null) ? 0 : doctor.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((patient == null) ? 0 : patient.hashCode());
		result = prime * result + ((timeEnd == null) ? 0 : timeEnd.hashCode());
		result = prime * result
				+ ((timeStart == null) ? 0 : timeStart.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Appointment other = (Appointment) obj;
		if (doctor == null) {
			if (other.doctor != null)
				return false;
		} else if (!doctor.equals(other.doctor))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (patient == null) {
			if (other.patient != null)
				return false;
		} else if (!patient.equals(other.patient))
			return false;
		if (timeEnd == null) {
			if (other.timeEnd != null)
				return false;
		} else if (!timeEnd.equals(other.timeEnd))
			return false;
		if (timeStart == null) {
			if (other.timeStart != null)
				return false;
		} else if (!timeStart.equals(other.timeStart))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Appointment [id=" + id + ", patient=" + patient + ", doctor="
				+ doctor + ", timeStart=" + timeStart + ", timeEnd=" + timeEnd
				+ "]";
	}

	
}
