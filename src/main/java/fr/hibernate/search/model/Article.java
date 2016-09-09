package fr.hibernate.search.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;

@Entity
@Table(name = "articles")
public class Article {

	@Id
	@Field(name = "article_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer id;
	
	@Column(length = 40)
	@Field
	public String designation;
	
	@ManyToMany(mappedBy = "article")
	@ContainedIn
	public List<BillDetail> billDetails = new LinkedList<BillDetail>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public List<BillDetail> getBillDetails() {
		return billDetails;
	}

	public void setBillDetails(List<BillDetail> billDetails) {
		this.billDetails = billDetails;
	}
}