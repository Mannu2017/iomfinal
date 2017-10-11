package iom.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="usermaster")
public class User {

	@Id
	@Column(name="userid")
	private int userid;
	
	@Column(name="branchname", columnDefinition="nvarchar(100)")
	private String branchname;
	
	@Column(name="username", columnDefinition="nvarchar(50)")
	private String username;
	
	@Column(name="fullname", columnDefinition="nvarchar(100)")
	private String fullname;

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getBranchname() {
		return branchname;
	}

	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
}
