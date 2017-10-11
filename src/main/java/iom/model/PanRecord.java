package iom.model;

public class PanRecord {

	String ackno,name,ackdate,status;
	int slno;

	public int getSlno() {
		return slno;
	}

	public void setSlno(int slno) {
		this.slno = slno;
	}

	public String getAckno() {
		return ackno;
	}

	public void setAckno(String ackno) {
		this.ackno = ackno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAckdate() {
		return ackdate;
	}

	public void setAckdate(String ackdate) {
		this.ackdate = ackdate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
