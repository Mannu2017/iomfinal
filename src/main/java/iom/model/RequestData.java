package iom.model;

public class RequestData {

	String type;
	
	String rdate;
	
	String status;
	
	String ackno;
	
	String ackstatus;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
