package be.chaouki.booker.backingbeans;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import be.chaouki.booker.entities.Department;
import be.chaouki.booker.entities.Doctor;
import be.chaouki.booker.service.DataService;

@ViewScoped
public class DoctorsTreeProducer implements Serializable {
	
	private static final long serialVersionUID = -8935040995421555059L;
	
	@Inject transient private DataService dataService;
	@Inject transient private Logger logger;
	
	@Produces
	@Named
	private TreeNode doctorsTree;
	
	@SuppressWarnings("unused")
	@PostConstruct
	private void generateTree() {
		doctorsTree = new DefaultTreeNode();
		TreeNode departmentNode=null;
		for(Department department : dataService.getDepartments()){
			logger.info(department.toString());
			departmentNode=new DefaultTreeNode(department, doctorsTree);
			
			List<Doctor> doctors=department.getDoctors();
			TreeNode doctorNode=null;
			for(Doctor doctor : doctors){
				doctorNode=new DefaultTreeNode("doctor", doctor, departmentNode);
			}
		}
	}
}
