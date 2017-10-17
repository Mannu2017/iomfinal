package iom.model;

import org.hibernate.validator.constraints.NotEmpty;

public class ReportRequeast {

	@NotEmpty(message="Please Select Date")
	private String reqdate;
	
	private String status;
	
	private String coname;
	
	public String getConame() {
		return coname;
	}

	public void setConame(String coname) {
		this.coname = coname;
	}

	public String getReqdate() {
		return reqdate;
	}

	public void setReqdate(String reqdate) {
		this.reqdate = reqdate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
