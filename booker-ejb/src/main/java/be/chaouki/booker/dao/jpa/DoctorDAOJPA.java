package be.chaouki.booker.dao.jpa;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import be.chaouki.booker.dao.DoctorDAO;
import be.chaouki.booker.entities.Doctor;

public class DoctorDAOJPA implements DoctorDAO {

	@Inject
    private EntityManager em;
	
	@Inject
    private Logger logger;

	@SuppressWarnings("unchecked")
	@Override
    public List<Doctor> findAll() {
		Query query=em.createQuery("FROM Doctor");
		return query.getResultList();
    }

	@Override
	public List<Doctor> findAllAndFetch() {
		TypedQuery<Doctor> query=em.createQuery("SELECT DISTINCT doctor FROM Doctor doctor left join fetch doctor.appointments", Doctor.class);
		return query.getResultList();
	}
	
	@Override
	public Doctor findById(Integer id) {
		return em.find(Doctor.class, id);
	}

	@Override
	public Doctor findByName(String name) {
		TypedQuery<Doctor> query=em.createQuery("SELECT DISTINCT doctor FROM Doctor doctor left join fetch doctor.appointments WHERE doctor.name LIKE :name", Doctor.class);
		query.setParameter("name", name + "%");
		List<Doctor> results=query.getResultList();
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
