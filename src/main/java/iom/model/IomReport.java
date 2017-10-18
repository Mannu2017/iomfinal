package iom.model;

public class IomReport {
	
	private String reqtype;
	
	private String reqdate;
	
	private String status;
	
	private String requeastno;
	
	

	public String getRequeastno() {
		return requeastno;
	}

	public void setRequeastno(String requeastno) {
		this.requeastno = requeastno;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReqtype() {
		return reqtype;
	}

	public void setReqtype(String reqtype) {
		this.reqtype = reqtype;
	}

	public String getReqdate() {
		return reqdate;
	}

	public void setReqdate(String reqdate) {
		this.reqdate = reqdate;
	}
	
}
