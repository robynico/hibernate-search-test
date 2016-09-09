package fr.hibernate.search.model;

import org.hibernate.search.bridge.TwoWayStringBridge;

public class BillIDFieldBridge implements TwoWayStringBridge {

	@Override
	public Object stringToObject(String stringValue) {
		String[] split = stringValue.split("_");
		BillDetailId ret = new BillDetailId(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
		return ret;
	}

	@Override
	public String objectToString(Object object) {
		BillDetailId id = (BillDetailId) object;
		return id.bill_id + "_" + id.number;
	}
}