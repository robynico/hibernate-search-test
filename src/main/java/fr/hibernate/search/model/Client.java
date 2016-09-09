package fr.hibernate.search.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;

@Entity
@Table(name = "clients")
public class Client {

	@Id
	@Field(name = "client_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer id;
	
	@Field
	@Column(length = 50)
	public String name;

	@OneToMany(mappedBy = "client")
	@ContainedIn
	public List<Bill> bills = new ArrayList<Bill>();
}