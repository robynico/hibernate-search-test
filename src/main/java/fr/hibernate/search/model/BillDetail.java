package fr.hibernate.search.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

@Entity
@Table(name = "billdetails", uniqueConstraints = { @UniqueConstraint(columnNames = { "bill_id", "number" }) })
@Indexed(index = "hibernate")
public class BillDetail {

	@EmbeddedId
	@DocumentId
	@FieldBridge(impl = BillIDFieldBridge.class)
	public BillDetailId id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "article_id")
	@IndexedEmbedded
	private Article article;

	@Field
	private Double prize;

	@Field
	private Double quantity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bill_id", referencedColumnName = "id", insertable = false, updatable = false)
	@IndexedEmbedded
	public Bill bill;

	public BillDetail() {
	}
	
	public BillDetail(Integer id, int number) {
		this.id = new BillDetailId(id, number);
	}

	@Field(name = "designation")
	public String getDesignation() {
		return "Line n°" + this.id.number + ", article : " + this.article.designation + ", prize : " + String.format("%.5g%n", this.prize);
	}

	public BillDetailId getId() {
		return id;
	}

	public void setId(BillDetailId id) {
		this.id = id;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public Double getPrize() {
		return prize;
	}

	public void setPrize(Double prize) {
		this.prize = prize;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}
}