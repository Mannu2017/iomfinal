package iom.model;

import org.hibernate.validator.constraints.NotEmpty;

public class ReportRequeast {

	@NotEmpty(message="Please Select Date")
	private String reqdate;
	
	private String status;
	
	private String coname;
	
	private String trackid;
	
	private String remarks;
	
	public String getTrackid() {
		return trackid;
	}

	public void setTrackid(String trackid) {
		this.trackid = trackid;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

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
