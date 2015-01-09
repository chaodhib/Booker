> FR

Booker 

Projet perso à titre d'experimentation de technologies. Technologies utilisées : JPA, EJB (dont un MDB), CDI, JSF et Primefaces.

Le projet est constitué d'un EAR (le projet principal) et d'un petit module EJB indépendant communicant avec le projet principal par JMS.

Celui-ci a été testé sur un serveur Wildfly (JBoss) 8.2 et emploi Maven. Il nécessite 3 configurations du serveur:
- Une datasource bindé au JNDI "java:jboss/datasources/bookerDS"
- Une JMS Connection Factory bindé à "java:/ConnectionFactory"
- et une JMS Destination de type Queue bindé à "java:/jms/AppointmentRequestQueue".

Il n'est pas nécessaire que le projet principal soit déployé pour deployer le module et vice-versa.

> EN

Booker 

I made this application to experiment with various technologies : JPA, EJB (including MDB), CDI, JSF and Primefaces.

The project includes one EAR (the main project) and one small EJB module which communicates with the main project using JMS.

For deployment and testing, I used Wildfly (JBoss) 8.2. In order to successfuly deploy and run, the application needs 3 resources given by the server:
- A datasource binded to the JNDI "java:jboss/datasources/bookerDS" (I used a MySQL DB)
- A JMS Connection Factory binded to "java:/ConnectionFactory"
- And a JMS Destination (Queue) binded to "java:/jms/AppointmentRequestQueue"

The main project and the JMS module are completly independant. They dont share any dependencies. Also it is not necessary to deploy them both at the same time.
