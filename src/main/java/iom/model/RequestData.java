package iom.model;

import org.hibernate.validator.constraints.NotEmpty;

public class RequestData {

	String type;
	
	@NotEmpty(message="Please select Date")
	String rdate;
	
	String ackno;
	
	String ackstatus;
	
	String ackremarks;
	

	public String getAckremarks() {
		return ackremarks;
	}

	public void setAckremarks(String ackremarks) {
		
		this.ackremarks = ackremarks;
	}

	public String getAckno() {
		return ackno;
	}

	public void setAckno(String ackno) {
		this.ackno = ackno;
	}

	public String getAckstatus() {
		return ackstatus;
	}

	public void setAckstatus(String ackstatus) {
		this.ackstatus = ackstatus;
	}

	public String getRdate() {
		return rdate;
	}

	public void setRdate(String rdate) {
		this.rdate = rdate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
