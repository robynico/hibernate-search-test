package fr.hibernate.search.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.search.annotations.Field;

@Embeddable
public class BillDetailId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Field
	@Column(name = "bill_id")
	public Integer bill_id;

	@Field
	@Column(name = "number")
	public Integer number;

	public BillDetailId() {
	}
	
	public BillDetailId(Integer id, int number) {
		this.bill_id = id;
		this.number = number;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bill_id == null) ? 0 : bill_id.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
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
		BillDetailId other = (BillDetailId) obj;
		if (bill_id == null) {
			if (other.bill_id != null)
				return false;
		} else if (!bill_id.equals(other.bill_id))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}

}