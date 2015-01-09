package be.chaouki.bookermod.mdb;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import be.chaouki.bookermod.service.AppointmentService;

/**
 * Message-Driven Bean implementation class for: AppointmentRequestMDB
 */
@MessageDriven(
		activationConfig = { 
				@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
				@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/AppointmentRequestQueue")
		})
public class AppointmentRequestMDB implements MessageListener {

	@Inject AppointmentService appointmentService;
	@Inject private Logger logger;

    @Override
    public void onMessage(Message message) {
    	logger.info("Traitement d'un message en provenance de AppointmentRequestQueue");
        if(message instanceof ObjectMessage){
        	;
        }
        else if(message instanceof TextMessage){
        	;
        }
        else if(message instanceof MapMessage ){
        	logger.info("Reception d'une demande de rdv");
        	logger.info("Demande de rdv en cours de traitement");
        	MapMessage mapMessage=(MapMessage) message;
        	try {
        		String patient=mapMessage.getString("patient");
        		String department=mapMessage.getString("department");
        		String time=mapMessage.getString("time");
        		String duration=mapMessage.getString("duration");
        		
        		logger.info("Informations de la demande:");
    			logger.info(patient + " - " + department + " - " + time+ " - " + duration);
    			
    			appointmentService.registerAppointment(patient, department, time, duration);
    			logger.info("Demande de RDV correctement traitée par le module externe");
				
			} catch (JMSException e) {
				logger.log(Level.SEVERE, "Le traitement d'un message de type demande de rdv a échoué", e);
			}
        }
        logger.info("Fin de traitement de message");
    }
}
