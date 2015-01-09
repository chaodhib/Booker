package be.chaouki.booker.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


/**
 * Entity implementation class for Entity: Department
 *
 */
@Entity

public class Department implements Serializable, Comparable<Department> {
	
	private static final long serialVersionUID = 7531691085135864099L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(unique=true, nullable=false) 
	private String name;
	
	@OneToMany(mappedBy="department")
	private List<Doctor> doctors;
	
	private Boolean external;

	public Department() {
		super(); 
	}
  
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getExternal() {
		return external;
	}

	public void setExternal(Boolean external) {
		this.external = external;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((doctors == null) ? 0 : doctors.hashCode());
		result = prime * result
				+ ((external == null) ? 0 : external.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Department other = (Department) obj;
		if (doctors == null) {
			if (other.doctors != null)
				return false;
		} else if (!doctors.equals(other.doctors))
			return false;
		if (external == null) {
			if (other.external != null)
				return false;
		} else if (!external.equals(other.external))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + ", doctors="
				+ doctors + ", external=" + external + "]";
	}

	@Override
	public int compareTo(Department department) {
		return this.getName().compareTo(department.getName());
	}
}
