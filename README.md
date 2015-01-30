> FR

Booker :
L'application a comme but une aide à la gestion des prises de rendez-vous avec les différents services d'un hôpital.  Il permet un encodage aisé et intuitif d'un nouveau rdv ainsi que  la modification, la lecture et la suppression des rendez-vous existants. Il est conçu de manière utilisé par un membre du personnel de l'hôpital.

Projet perso à titre d'experimentation de technologies. Une architecture 3 tiers a été utilisé avec les technologies suivantes : JPA, EJB (dont un MDB), CDI, JSF, Primefaces et Web Service REST.

Une démonstration live de l'application en ligne par OpenShift est en cours de préparation. En attendant, voici des captures d’écran de l'application en cours d'utilisation:

Prise de rdv:
<a href="http://imgur.com/VPG4f4b"><img src="http://i.imgur.com/VPG4f4b.png?1" title="Prise de rdv" /></a>
<a href="http://imgur.com/YIncNaN"><img src="http://i.imgur.com/YIncNaN.png?1" title="source: imgur.com" /></a>
Vue rendez-vous par patient:
<a href="http://imgur.com/OGUw00l"><img src="http://i.imgur.com/OGUw00l.png?1" title="source: imgur.com" /></a>
Liste des entités pour l'instant statique de l'application: les docteurs, patients et services:
<a href="http://imgur.com/UQChpYF"><img src="http://i.imgur.com/UQChpYF.png?1" title="source: imgur.com" /></a>


Le projet est constitué d'un EAR (le projet principal) et d'un petit module EJB indépendant communicant avec le projet principal par JMS.

Celui-ci a été testé sur un serveur Wildfly (JBoss) 8.2 et emploi Maven. Il nécessite 3 configurations du serveur:
- Une datasource bindé au JNDI "java:jboss/datasources/bookerDS" (pour les tests, j'ai utilisé une BD MySQL
- Une JMS Connection Factory bindé à "java:/ConnectionFactory"
- et une JMS Destination de type Queue bindé à "java:/jms/AppointmentRequestQueue".

Il n'est pas nécessaire que le projet principal soit déployé pour deployer le module externe et vice-versa.

> EN

Booker :
This application provides a convenient and intuitive way to book and manage appointments in the context of a hospital. It's aimed at secretaries and hospital's staff to easily book a new appointment, modify or cancel an existing one and find all the appointments a particular patient or doctor has.

I made this application to experiment with various technologies : JPA, EJB (including MDB), CDI, JSF and Primefaces and Web Service REST;

A live online version of the application is in the works. Meanwhile, we can see the application working on these screenshots:

Booking of a new appointment:
<a href="http://imgur.com/VPG4f4b"><img src="http://i.imgur.com/VPG4f4b.png?1" title="Prise de rdv" /></a>
<a href="http://imgur.com/YIncNaN"><img src="http://i.imgur.com/YIncNaN.png?1" title="source: imgur.com" /></a>
View of a patient's appointments:
<a href="http://imgur.com/OGUw00l"><img src="http://i.imgur.com/OGUw00l.png?1" title="source: imgur.com" /></a>
View on the static entities of the application (patients, doctors, departments):
<a href="http://imgur.com/UQChpYF"><img src="http://i.imgur.com/UQChpYF.png?1" title="source: imgur.com" /></a>

The project includes one EAR (the main project) and one small EJB module which communicates with the main project using JMS.

For deployment and testing, I used Wildfly (JBoss) 8.2. In order to successfully  deploy and run, the application needs 3 resources given by the server:
- A datasource binded to the JNDI "java:jboss/datasources/bookerDS" (I used a MySQL DB)
- A JMS Connection Factory binded to "java:/ConnectionFactory"
- And a JMS Destination (Queue) binded to "java:/jms/AppointmentRequestQueue"

The main project and the JMS module are completly independant. They dont share any dependencies. Also it is not necessary to deploy them both at the same time.
