package model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Employee {
	@Id @GeneratedValue 
	private long id;

	private String name;
	private String surname;

	@OneToMany(mappedBy = "employee") 
	private List<RepairOrder> repairOrders;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public List<RepairOrder> getRepairOrders() {
		return repairOrders;
	}

	public void setRepairOrders(List<RepairOrder> repairOrders) {
		this.repairOrders = repairOrders;
	}
}
