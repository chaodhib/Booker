package be.chaouki.booker.dao.jpa;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import be.chaouki.booker.dao.PatientDAO;
import be.chaouki.booker.entities.Patient;

public class PatientDAOJPA implements PatientDAO {

	@Inject
    private EntityManager em;
	
	@Inject
    private Logger logger;

	@SuppressWarnings("unchecked")
	@Override
    public List<Patient> findAll() {
		Query query=em.createQuery("FROM Patient");
		return query.getResultList();
    }

	@Override
	public List<Patient> findAllAndFetch() {
		TypedQuery<Patient> query=em.createQuery("SELECT DISTINCT patient FROM Patient patient left join fetch patient.appointments", Patient.class);
		return query.getResultList();
	}
	
	@Override
	public Patient findById(Integer id) {
		return em.find(Patient.class, id);
	}
	
	@Override
	public Patient findByName(String name) {
        TypedQuery<Patient> query=em.createQuery("SELECT DISTINCT patient FROM Patient patient left join fetch patient.appointments WHERE patient.name LIKE :name", Patient.class);
		query.setParameter("name", name + "%");
		List<Patient> results=query.getResultList();
		if(results.size()==1)
			return results.get(0);
		else if(results.size()==0)
			return null;
		else
			throw new IllegalStateException("Multiple entities with the same name found in the database");
	}
	
	@PostConstruct
	private void init(){
		logger.info("Readying "+this.getClass().getName()+" bean");
	}
	
	@PreDestroy
	private void post(){
		logger.info("Destroying "+this.getClass().getName()+" bean");
	}




}
