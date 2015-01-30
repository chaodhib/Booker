package be.chaouki.booker.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import be.chaouki.booker.entities.Department;
import be.chaouki.booker.entities.Patient;
import be.chaouki.booker.service.MessagingService;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.MapMessage;

/**
 * Session Bean implementation class MessagingExternalServiceImpl
 */
@Stateless
public class MessagingServiceImpl implements MessagingService {

	@Inject private Logger logger;
	
	@Inject @JMSConnectionFactory("java:/ConnectionFactory")
	private JMSContext jmsContext;
	
	@Resource(lookup = "java:/jms/AppointmentRequestQueue")
	private Destination messageDestination;
	
	@Override
	public void sendAppointmentRequest(Patient patient, Department department,
			Calendar time, Integer duration) {
		if(patient==null || department==null || !department.getExternal() 
				|| time==null || duration==null)
			throw new IllegalArgumentException();
		
		MapMessage message=jmsContext.createMapMessage();
		try {
			message.setString("patient", patient.getName());
			message.setString("department", department.getName());
			message.setString("time", time.getTime().toString());
			message.setString("duration", duration.toString());
			JMSProducer producer=jmsContext.createProducer();
			producer.send(messageDestination, message);
			
			logger.info("Demande de rdv correctement envoyée");
			logger.info("Informations de la demande:");
			logger.info(patient.getName() + " - " + department.getName() 
					+ " - " + time.getTime().toString()
					+ " - " + duration.toString());
		} catch (JMSException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public List<Object> getUntreatedNegativeReplies() {
		// TODO Auto-generated method stub
		return null;
	}

}
