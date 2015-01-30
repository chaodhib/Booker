package be.chaouki.booker.dao.jpa;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import be.chaouki.booker.dao.DepartmentDAO;
import be.chaouki.booker.entities.Department;

public class DepartmentDAOJPA implements DepartmentDAO {

	@Inject
    private EntityManager em;
	
	@Inject
    private Logger logger;

	@SuppressWarnings("unchecked")
	@Override
    public List<Department> findAll() {
		Query query=em.createQuery("FROM Department");
		return query.getResultList();
    }

	@Override
	public List<Department> findAllAndFetch() {
		TypedQuery<Department> query=em.createQuery("SELECT DISTINCT department FROM Department department left join fetch department.doctors", Department.class);
		return query.getResultList();
	}

	@Override
	public Department findById(Integer id) {
		return em.find(Department.class, id);
	}
	
	@Override
	public Department findByName(String name) {
        TypedQuery<Department> query=em.createQuery("SELECT DISTINCT department FROM Department department left join fetch department.doctors WHERE department.name LIKE :name", Department.class);
		query.setParameter("name", name + "%");
		List<Department> results= query.getResultList();
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
