package iom.model;

public class CourierStatus {
	
	private String refno;
	
	private int totalcou;
	
	private String coname;
	
	private String trackid;
	
	private String costatus;
	
	private String update;
	
	private String ackdate;

	public String getAckdate() {
		return ackdate;
	}

	public void setAckdate(String ackdate) {
		this.ackdate = ackdate;
	}

	public String getRefno() {
		return refno;
	}

	public void setRefno(String refno) {
		this.refno = refno;
	}

	public int getTotalcou() {
		return totalcou;
	}

	public void setTotalcou(int totalcou) {
		this.totalcou = totalcou;
	}

	public String getConame() {
		return coname;
	}

	public void setConame(String coname) {
		this.coname = coname;
	}

	public String getTrackid() {
		return trackid;
	}

	public void setTrackid(String trackid) {
		this.trackid = trackid;
	}

	public String getCostatus() {
		return costatus;
	}

	public void setCostatus(String costatus) {
		this.costatus = costatus;
	}

	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}

}
