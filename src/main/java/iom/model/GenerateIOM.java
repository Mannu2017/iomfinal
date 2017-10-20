package iom.model;

public class GenerateIOM {
	
	private String refno;
	
	private String pan;
	
	private String pran;
	
	private String etds;
	
	private String tan;
	
	private String paper;
	
	private String air;
	
	private String g24;
	
	private String totalrecord;
	
	private String ackdate;
	
	private String branchcode;
	
	public String getBranchcode() {
		return branchcode;
	}

	public void setBranchcode(String branchcode) {
		this.branchcode = branchcode;
	}

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

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getPran() {
		return pran;
	}

	public void setPran(String pran) {
		this.pran = pran;
	}

	public String getEtds() {
		return etds;
	}

	public void setEtds(String etds) {
		this.etds = etds;
	}

	public String getTan() {
		return tan;
	}

	public void setTan(String tan) {
		this.tan = tan;
	}

	public String getPaper() {
		return paper;
	}

	public void setPaper(String paper) {
		this.paper = paper;
	}

	public String getAir() {
		return air;
	}

	public void setAir(String air) {
		this.air = air;
	}

	public String getG24() {
		return g24;
	}

	public void setG24(String g24) {
		this.g24 = g24;
	}

	public String getTotalrecord() {
		return totalrecord;
	}

	public void setTotalrecord(String totalrecord) {
		this.totalrecord = totalrecord;
	}

	@Override
	public String toString() {
		return "GenerateIOM [refno=" + refno + ", pan=" + pan + ", pran=" + pran + ", etds=" + etds + ", tan=" + tan
				+ ", paper=" + paper + ", air=" + air + ", g24=" + g24 + ", totalrecord=" + totalrecord + ", ackdate="
				+ ackdate + "]";
	}

}
