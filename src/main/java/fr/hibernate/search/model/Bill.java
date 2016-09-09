package fr.hibernate.search.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.IndexedEmbedded;

@Entity
@Table(name = "bills")
public class Bill {

	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH);

	private static SimpleDateFormat formatter2 = new SimpleDateFormat("dd MMMMM yyyy", Locale.FRENCH);

	{
		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		formatter2.setTimeZone(TimeZone.getTimeZone("GMT"));
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Temporal(TemporalType.DATE)
	@Column(length = 13)
	private Date date = new Date();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "client_id")
	@IndexedEmbedded
	private Client client;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "bill_id")
	@ContainedIn
	private List<BillDetail> billDetails = new LinkedList<BillDetail>();

	@Field(name = "designation")
	public String getLabel() {
		return "Bill n°" + this.id + " - " + this.getDateFormated();
	}

	@Field(name = "date")
	public String getDateFormated() {
		return formatter.format(this.date);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<BillDetail> getBillDetails() {
		return billDetails;
	}

	public void setBillDetails(List<BillDetail> billDetails) {
		this.billDetails = billDetails;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return this.date;
	}
}