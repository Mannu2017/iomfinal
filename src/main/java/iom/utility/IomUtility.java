package iom.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import iom.model.PanRecord;
import iom.model.RequestData;

@Component
public class IomUtility {
	private Connection panconn;
	
	RequestData requestData1=new RequestData();
	
	public IomUtility()
	{
		try{
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		panconn=DriverManager.getConnection("jdbc:sqlserver://192.168.84.90;user=sa;password=Karvy@123;database=tinbos");
		}catch(Exception e)
		{	
			System.out.println("Error:"+e);
		}	
	}

	public List<PanRecord> sendRequeast(String type, String rdate, String branchname, String ackno, String ackstatus, String ackremarks){
		
		List<PanRecord> panRecords=new  ArrayList<PanRecord>();
		PanRecord panRecord = null;
		Date passdat = null;
		try {
			passdat = new SimpleDateFormat("yyyy-MM-dd").parse(rdate);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		DateFormat df=new SimpleDateFormat("yyyyMMdd");
		String reqdate=df.format(passdat);
		
		if (type.equals("Pan")) {
			String bcode=null;
				try {		
					PreparedStatement ps=panconn.prepareStatement("select BranchCode from Branch_Master where BranchName='"+branchname+"';");
					ResultSet rs=ps.executeQuery();
					if (rs.next()) {
						bcode=rs.getString(1);
						PreparedStatement ppss=panconn.prepareStatement("select count(*) from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and Type='"+type+"'");
						ResultSet rrss=ppss.executeQuery();
						if (rrss.next()) {
							if (rrss.getInt(1)==0) {
								PreparedStatement pss=panconn.prepareStatement("insert into tinbosiomrecord (BranchCode,Ack_No,Application_Name,Ack_Date,Status,type)select '"+bcode+"',ack_no as ackno,(first_name+' '+middle_name+' '+last_name) as name,ack_date,'Dispatched' as ackdate,'"+type+"' from pan where ack_date='"+reqdate+"' and ack_no like '"+bcode+"%';");
								pss.execute();
								pss.close();
							}	
						}
						ppss.close();
						rrss.close();
						
					}
					ps.close();
					rs.close();
				} catch (Exception e) {
					System.out.println(e);
				}
				
				try {
					if (ackno.equals("0")) {
						PreparedStatement ps1=panconn.prepareStatement("select Ack_No,Application_Name,Ack_Date,status from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and type='"+type+"';");
						ResultSet rs1=ps1.executeQuery();
						int slid=0;
						while (rs1.next()) {
							slid=1+slid;
							panRecord=new PanRecord();
							panRecord.setAckno(rs1.getString(1));
							panRecord.setName(rs1.getString(2));
							panRecord.setAckdate(rs1.getString(3));
							panRecord.setSlno(slid);
							panRecord.setStatus(rs1.getString(4));
							panRecords.add(panRecord);
						}
						ps1.close();
						rs1.close();
						
					}else {
						
						if (ackstatus.equals("Dispatched")) {
							PreparedStatement ps2=panconn.prepareStatement("update TinbosIOMRecord set Status='"+ackstatus+"',Remarks=null where Ack_No='"+ackno+"';");
							ps2.execute();
							
							PreparedStatement ps3=panconn.prepareStatement("select Ack_No,Application_Name,Ack_Date,status from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and type='"+type+"';");
							ResultSet rs3=ps3.executeQuery();
							int slid=0;
							while (rs3.next()) {
								slid=1+slid;
								panRecord=new PanRecord();
								panRecord.setAckno(rs3.getString(1));
								panRecord.setName(rs3.getString(2));
								panRecord.setAckdate(rs3.getString(3));
								panRecord.setSlno(slid);
								panRecord.setStatus(rs3.getString(4));
								panRecords.add(panRecord);
							}
							ps2.close();
							ps3.close();
							rs3.close();
						} else if (ackstatus.equals("Pending")) {
							
							PreparedStatement ps2=panconn.prepareStatement("update TinbosIOMRecord set Status='"+ackstatus+"',Remarks='"+ackremarks+"' where Ack_No='"+ackno+"';");
							ps2.execute();
							
							PreparedStatement ps3=panconn.prepareStatement("select Ack_No,Application_Name,Ack_Date,status from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and type='"+type+"';");
							ResultSet rs3=ps3.executeQuery();
							int slid=0;
							while (rs3.next()) {
								slid=1+slid;
								panRecord=new PanRecord();
								panRecord.setAckno(rs3.getString(1));
								panRecord.setName(rs3.getString(2));
								panRecord.setAckdate(rs3.getString(3));
								panRecord.setSlno(slid);
								panRecord.setStatus(rs3.getString(4));
								panRecords.add(panRecord);
							}
							ps2.close();
							ps3.close();
							rs3.close();
							
						} else if (ackstatus.equals("Reject")) {
							
							PreparedStatement ps2=panconn.prepareStatement("update TinbosIOMRecord set Status='"+ackstatus+"',Remarks='"+ackremarks+"' where Ack_No='"+ackno+"';");
							ps2.execute();
							
							PreparedStatement ps3=panconn.prepareStatement("select Ack_No,Application_Name,Ack_Date,status from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and type='"+type+"';");
							ResultSet rs3=ps3.executeQuery();
							int slid=0;
							while (rs3.next()) {
								slid=1+slid;
								panRecord=new PanRecord();
								panRecord.setAckno(rs3.getString(1));
								panRecord.setName(rs3.getString(2));
								panRecord.setAckdate(rs3.getString(3));
								panRecord.setSlno(slid);
								panRecord.setStatus(rs3.getString(4));
								panRecords.add(panRecord);
							}
							ps2.close();
							ps3.close();
							rs3.close();
							
						}
						
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
		} else if (type.equals("Pran")) {
			
			String bcode=null;
			try {		
				PreparedStatement ps=panconn.prepareStatement("select BranchCode from Branch_Master where BranchName='"+branchname+"';");
				ResultSet rs=ps.executeQuery();
				if (rs.next()) {
					bcode=Integer.toString(Integer.parseInt(rs.getString(1)));
					PreparedStatement ppss=panconn.prepareStatement("select count(*) from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and Type='"+type+"'");
					ResultSet rrss=ppss.executeQuery();
					if (rrss.next()) {
						if (rrss.getInt(1)==0) {
							PreparedStatement pss=panconn.prepareStatement("insert into tinbosiomrecord (BranchCode,Ack_No,Application_Name,Ack_Date,Status,type) select '0"+bcode+"',ackno,formtype as name,ackdate,'Dispatched' as ackdate,'"+type+"' from cramis where ackdate='"+reqdate+"' and ackno like '"+bcode+"%';");
							pss.execute();
							pss.close();
						}	
					}
					ppss.close();
					rrss.close();
					
				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			
			try {
				if (ackno.equals("0")) {
					PreparedStatement ps1=panconn.prepareStatement("select Ack_No,Application_Name,Ack_Date,status from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and type='"+type+"';");
					ResultSet rs1=ps1.executeQuery();
					int slid=0;
					while (rs1.next()) {
						slid=1+slid;
						panRecord=new PanRecord();
						panRecord.setAckno(rs1.getString(1));
						if (rs1.getString(2)==null) {
							panRecord.setName("No Name");
						} else {
							panRecord.setName(rs1.getString(2));
						}
						panRecord.setAckdate(rs1.getString(3));
						panRecord.setSlno(slid);
						panRecord.setStatus(rs1.getString(4));
						panRecords.add(panRecord);
					}
					ps1.close();
					rs1.close();
					
				}else {
					
					if (ackstatus.equals("Dispatched")) {
						PreparedStatement ps2=panconn.prepareStatement("update TinbosIOMRecord set Status='"+ackstatus+"',Remarks=null where Ack_No='"+ackno+"';");
						ps2.execute();
						
						PreparedStatement ps3=panconn.prepareStatement("select Ack_No,Application_Name,Ack_Date,status from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and type='"+type+"';");
						ResultSet rs3=ps3.executeQuery();
						int slid=0;
						while (rs3.next()) {
							slid=1+slid;
							panRecord=new PanRecord();
							panRecord.setAckno(rs3.getString(1));
							if (rs3.getString(2)==null) {
								panRecord.setName("No Name");
							} else {
								panRecord.setName(rs3.getString(2));
							}
							
							panRecord.setAckdate(rs3.getString(3));
							panRecord.setSlno(slid);
							panRecord.setStatus(rs3.getString(4));
							panRecords.add(panRecord);
						}
						ps2.close();
						ps3.close();
						rs3.close();
					} else if (ackstatus.equals("Pending")) {
						
						PreparedStatement ps2=panconn.prepareStatement("update TinbosIOMRecord set Status='"+ackstatus+"',Remarks='"+ackremarks+"' where Ack_No='"+ackno+"';");
						ps2.execute();
						
						PreparedStatement ps3=panconn.prepareStatement("select Ack_No,Application_Name,Ack_Date,status from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and type='"+type+"';");
						ResultSet rs3=ps3.executeQuery();
						int slid=0;
						while (rs3.next()) {
							slid=1+slid;
							panRecord=new PanRecord();
							panRecord.setAckno(rs3.getString(1));
							if (rs3.getString(2)==null) {
								panRecord.setName("No Name");
							} else {
								panRecord.setName(rs3.getString(2));
							}
							panRecord.setAckdate(rs3.getString(3));
							panRecord.setSlno(slid);
							panRecord.setStatus(rs3.getString(4));
							panRecords.add(panRecord);
						}
						ps2.close();
						ps3.close();
						rs3.close();
						
					} else if (ackstatus.equals("Reject")) {
						
						PreparedStatement ps2=panconn.prepareStatement("update TinbosIOMRecord set Status='"+ackstatus+"',Remarks='"+ackremarks+"' where Ack_No='"+ackno+"';");
						ps2.execute();
						
						PreparedStatement ps3=panconn.prepareStatement("select Ack_No,Application_Name,Ack_Date,status from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and type='"+type+"';");
						ResultSet rs3=ps3.executeQuery();
						int slid=0;
						while (rs3.next()) {
							slid=1+slid;
							panRecord=new PanRecord();
							panRecord.setAckno(rs3.getString(1));
							if (rs3.getString(2)==null) {
								panRecord.setName("No Name");
							} else {
								panRecord.setName(rs3.getString(2));
							}
							panRecord.setAckdate(rs3.getString(3));
							panRecord.setSlno(slid);
							panRecord.setStatus(rs3.getString(4));
							panRecords.add(panRecord);
						}
						ps2.close();
						ps3.close();
						rs3.close();
						
					}
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		} else if (type.equals("Etds")) {
			
			String bcode=null;
			try {		
				PreparedStatement ps=panconn.prepareStatement("select BranchCode from Branch_Master where BranchName='"+branchname+"';");
				ResultSet rs=ps.executeQuery();
				if (rs.next()) {
					bcode=rs.getString(1);
					PreparedStatement ppss=panconn.prepareStatement("select count(*) from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and Type='"+type+"'");
					ResultSet rrss=ppss.executeQuery();
					if (rrss.next()) {
						if (rrss.getInt(1)==0) {
							PreparedStatement pss=panconn.prepareStatement("insert into tinbosiomrecord (BranchCode,Ack_No,Application_Name,Ack_Date,Status,type)select '"+bcode+"',ack_no as ackno,NAME,ack_date,'Dispatched' as ackdate,'"+type+"' from EtdsQuarterly where ack_date='"+reqdate+"' and ack_no like '"+bcode+"%' and ele_phy='E' ;");
							pss.execute();
							pss.close();
						}	
					}
					ppss.close();
					rrss.close();
					
				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			
			try {
				if (ackno.equals("0")) {
					PreparedStatement ps1=panconn.prepareStatement("select Ack_No,Application_Name,Ack_Date,status from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and type='"+type+"';");
					ResultSet rs1=ps1.executeQuery();
					int slid=0;
					while (rs1.next()) {
						slid=1+slid;
						panRecord=new PanRecord();
						panRecord.setAckno(rs1.getString(1));
						if (rs1.getString(2)==null) {
							panRecord.setName("No Name");
						} else {
							panRecord.setName(rs1.getString(2));
						}
						panRecord.setAckdate(rs1.getString(3));
						panRecord.setSlno(slid);
						panRecord.setStatus(rs1.getString(4));
						panRecords.add(panRecord);
					}
					ps1.close();
					rs1.close();
					
				}else {
					
					if (ackstatus.equals("Dispatched")) {
						PreparedStatement ps2=panconn.prepareStatement("update TinbosIOMRecord set Status='"+ackstatus+"',Remarks=null where Ack_No='"+ackno+"';");
						ps2.execute();
						
						PreparedStatement ps3=panconn.prepareStatement("select Ack_No,Application_Name,Ack_Date,status from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and type='"+type+"';");
						ResultSet rs3=ps3.executeQuery();
						int slid=0;
						while (rs3.next()) {
							slid=1+slid;
							panRecord=new PanRecord();
							panRecord.setAckno(rs3.getString(1));
							if (rs3.getString(2)==null) {
								panRecord.setName("No Name");
							} else {
								panRecord.setName(rs3.getString(2));
							}
							
							panRecord.setAckdate(rs3.getString(3));
							panRecord.setSlno(slid);
							panRecord.setStatus(rs3.getString(4));
							panRecords.add(panRecord);
						}
						ps2.close();
						ps3.close();
						rs3.close();
					} else if (ackstatus.equals("Pending")) {
						
						PreparedStatement ps2=panconn.prepareStatement("update TinbosIOMRecord set Status='"+ackstatus+"',Remarks='"+ackremarks+"' where Ack_No='"+ackno+"';");
						ps2.execute();
						
						PreparedStatement ps3=panconn.prepareStatement("select Ack_No,Application_Name,Ack_Date,status from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and type='"+type+"';");
						ResultSet rs3=ps3.executeQuery();
						int slid=0;
						while (rs3.next()) {
							slid=1+slid;
							panRecord=new PanRecord();
							panRecord.setAckno(rs3.getString(1));
							if (rs3.getString(2)==null) {
								panRecord.setName("No Name");
							} else {
								panRecord.setName(rs3.getString(2));
							}
							panRecord.setAckdate(rs3.getString(3));
							panRecord.setSlno(slid);
							panRecord.setStatus(rs3.getString(4));
							panRecords.add(panRecord);
						}
						ps2.close();
						ps3.close();
						rs3.close();
						
					} else if (ackstatus.equals("Reject")) {
						
						PreparedStatement ps2=panconn.prepareStatement("update TinbosIOMRecord set Status='"+ackstatus+"',Remarks='"+ackremarks+"' where Ack_No='"+ackno+"';");
						ps2.execute();
						
						PreparedStatement ps3=panconn.prepareStatement("select Ack_No,Application_Name,Ack_Date,status from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and type='"+type+"';");
						ResultSet rs3=ps3.executeQuery();
						int slid=0;
						while (rs3.next()) {
							slid=1+slid;
							panRecord=new PanRecord();
							panRecord.setAckno(rs3.getString(1));
							if (rs3.getString(2)==null) {
								panRecord.setName("No Name");
							} else {
								panRecord.setName(rs3.getString(2));
							}
							panRecord.setAckdate(rs3.getString(3));
							panRecord.setSlno(slid);
							panRecord.setStatus(rs3.getString(4));
							panRecords.add(panRecord);
						}
						ps2.close();
						ps3.close();
						rs3.close();
						
					}
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		} else if (type.equals("Tan")) {
			
			String bcode=null;
			try {		
				PreparedStatement ps=panconn.prepareStatement("select BranchCode from Branch_Master where BranchName='"+branchname+"';");
				ResultSet rs=ps.executeQuery();
				if (rs.next()) {
					bcode=rs.getString(1);
					PreparedStatement ppss=panconn.prepareStatement("select count(*) from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and Type='"+type+"'");
					ResultSet rrss=ppss.executeQuery();
					if (rrss.next()) {
						if (rrss.getInt(1)==0) {
							PreparedStatement pss=panconn.prepareStatement("insert into tinbosiomrecord (BranchCode,Ack_No,Application_Name,Ack_Date,Status,type)select '"+bcode+"',ack_no as ackno,(first_name+' '+middle_name+' '+last_name+' '+Name_Of_Deductor) as name,ack_date,'Dispatched' as ackdate,'"+type+"' from tan where ack_date='"+reqdate+"' and ack_no like '"+bcode+"%';");
							pss.execute();
							pss.close();
						}	
					}
					ppss.close();
					rrss.close();
					
				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			
			try {
				if (ackno.equals("0")) {
					PreparedStatement ps1=panconn.prepareStatement("select Ack_No,Application_Name,Ack_Date,status from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and type='"+type+"';");
					ResultSet rs1=ps1.executeQuery();
					int slid=0;
					while (rs1.next()) {
						slid=1+slid;
						panRecord=new PanRecord();
						panRecord.setAckno(rs1.getString(1));
						if (rs1.getString(2)==null) {
							panRecord.setName("No Name");
						} else {
							panRecord.setName(rs1.getString(2));
						}
						panRecord.setAckdate(rs1.getString(3));
						panRecord.setSlno(slid);
						panRecord.setStatus(rs1.getString(4));
						panRecords.add(panRecord);
					}
					ps1.close();
					rs1.close();
					
				}else {
					
					if (ackstatus.equals("Dispatched")) {
						PreparedStatement ps2=panconn.prepareStatement("update TinbosIOMRecord set Status='"+ackstatus+"',Remarks=null where Ack_No='"+ackno+"';");
						ps2.execute();
						
						PreparedStatement ps3=panconn.prepareStatement("select Ack_No,Application_Name,Ack_Date,status from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and type='"+type+"';");
						ResultSet rs3=ps3.executeQuery();
						int slid=0;
						while (rs3.next()) {
							slid=1+slid;
							panRecord=new PanRecord();
							panRecord.setAckno(rs3.getString(1));
							if (rs3.getString(2)==null) {
								panRecord.setName("No Name");
							} else {
								panRecord.setName(rs3.getString(2));
							}
							
							panRecord.setAckdate(rs3.getString(3));
							panRecord.setSlno(slid);
							panRecord.setStatus(rs3.getString(4));
							panRecords.add(panRecord);
						}
						ps2.close();
						ps3.close();
						rs3.close();
					} else if (ackstatus.equals("Pending")) {
						
						PreparedStatement ps2=panconn.prepareStatement("update TinbosIOMRecord set Status='"+ackstatus+"',Remarks='"+ackremarks+"' where Ack_No='"+ackno+"';");
						ps2.execute();
						
						PreparedStatement ps3=panconn.prepareStatement("select Ack_No,Application_Name,Ack_Date,status from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and type='"+type+"';");
						ResultSet rs3=ps3.executeQuery();
						int slid=0;
						while (rs3.next()) {
							slid=1+slid;
							panRecord=new PanRecord();
							panRecord.setAckno(rs3.getString(1));
							if (rs3.getString(2)==null) {
								panRecord.setName("No Name");
							} else {
								panRecord.setName(rs3.getString(2));
							}
							panRecord.setAckdate(rs3.getString(3));
							panRecord.setSlno(slid);
							panRecord.setStatus(rs3.getString(4));
							panRecords.add(panRecord);
						}
						ps2.close();
						ps3.close();
						rs3.close();
						
					} else if (ackstatus.equals("Reject")) {
						
						PreparedStatement ps2=panconn.prepareStatement("update TinbosIOMRecord set Status='"+ackstatus+"',Remarks='"+ackremarks+"' where Ack_No='"+ackno+"';");
						ps2.execute();
						
						PreparedStatement ps3=panconn.prepareStatement("select Ack_No,Application_Name,Ack_Date,status from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and type='"+type+"';");
						ResultSet rs3=ps3.executeQuery();
						int slid=0;
						while (rs3.next()) {
							slid=1+slid;
							panRecord=new PanRecord();
							panRecord.setAckno(rs3.getString(1));
							if (rs3.getString(2)==null) {
								panRecord.setName("No Name");
							} else {
								panRecord.setName(rs3.getString(2));
							}
							panRecord.setAckdate(rs3.getString(3));
							panRecord.setSlno(slid);
							panRecord.setStatus(rs3.getString(4));
							panRecords.add(panRecord);
						}
						ps2.close();
						ps3.close();
						rs3.close();
						
					}
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		} else if (type.equals("Paper_Return")) {
			
			String bcode=null;
			try {		
				PreparedStatement ps=panconn.prepareStatement("select BranchCode from Branch_Master where BranchName='"+branchname+"';");
				ResultSet rs=ps.executeQuery();
				if (rs.next()) {
					bcode=rs.getString(1);
					PreparedStatement ppss=panconn.prepareStatement("select count(*) from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and Type='"+type+"'");
					ResultSet rrss=ppss.executeQuery();
					if (rrss.next()) {
						if (rrss.getInt(1)==0) {
							PreparedStatement pss=panconn.prepareStatement("insert into tinbosiomrecord (BranchCode,Ack_No,Application_Name,Ack_Date,Status,type)select '"+bcode+"',ack_no as ackno,NAME,ack_date,'Dispatched' as ackdate,'"+type+"' from EtdsQuarterly where ack_date='"+reqdate+"' and ack_no like '"+bcode+"%' and ele_phy='P' ;");
							pss.execute();
							pss.close();
						}	
					}
					ppss.close();
					rrss.close();
					
				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			
			try {
				if (ackno.equals("0")) {
					PreparedStatement ps1=panconn.prepareStatement("select Ack_No,Application_Name,Ack_Date,status from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and type='"+type+"';");
					ResultSet rs1=ps1.executeQuery();
					int slid=0;
					while (rs1.next()) {
						slid=1+slid;
						panRecord=new PanRecord();
						panRecord.setAckno(rs1.getString(1));
						if (rs1.getString(2)==null) {
							panRecord.setName("No Name");
						} else {
							panRecord.setName(rs1.getString(2));
						}
						panRecord.setAckdate(rs1.getString(3));
						panRecord.setSlno(slid);
						panRecord.setStatus(rs1.getString(4));
						panRecords.add(panRecord);
					}
					ps1.close();
					rs1.close();
					
				}else {
					
					if (ackstatus.equals("Dispatched")) {
						PreparedStatement ps2=panconn.prepareStatement("update TinbosIOMRecord set Status='"+ackstatus+"',Remarks=null where Ack_No='"+ackno+"';");
						ps2.execute();
						
						PreparedStatement ps3=panconn.prepareStatement("select Ack_No,Application_Name,Ack_Date,status from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and type='"+type+"';");
						ResultSet rs3=ps3.executeQuery();
						int slid=0;
						while (rs3.next()) {
							slid=1+slid;
							panRecord=new PanRecord();
							panRecord.setAckno(rs3.getString(1));
							if (rs3.getString(2)==null) {
								panRecord.setName("No Name");
							} else {
								panRecord.setName(rs3.getString(2));
							}
							
							panRecord.setAckdate(rs3.getString(3));
							panRecord.setSlno(slid);
							panRecord.setStatus(rs3.getString(4));
							panRecords.add(panRecord);
						}
						ps2.close();
						ps3.close();
						rs3.close();
					} else if (ackstatus.equals("Pending")) {
						
						PreparedStatement ps2=panconn.prepareStatement("update TinbosIOMRecord set Status='"+ackstatus+"',Remarks='"+ackremarks+"' where Ack_No='"+ackno+"';");
						ps2.execute();
						
						PreparedStatement ps3=panconn.prepareStatement("select Ack_No,Application_Name,Ack_Date,status from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and type='"+type+"';");
						ResultSet rs3=ps3.executeQuery();
						int slid=0;
						while (rs3.next()) {
							slid=1+slid;
							panRecord=new PanRecord();
							panRecord.setAckno(rs3.getString(1));
							if (rs3.getString(2)==null) {
								panRecord.setName("No Name");
							} else {
								panRecord.setName(rs3.getString(2));
							}
							panRecord.setAckdate(rs3.getString(3));
							panRecord.setSlno(slid);
							panRecord.setStatus(rs3.getString(4));
							panRecords.add(panRecord);
						}
						ps2.close();
						ps3.close();
						rs3.close();
						
					} else if (ackstatus.equals("Reject")) {
						
						PreparedStatement ps2=panconn.prepareStatement("update TinbosIOMRecord set Status='"+ackstatus+"',Remarks='"+ackremarks+"' where Ack_No='"+ackno+"';");
						ps2.execute();
						
						PreparedStatement ps3=panconn.prepareStatement("select Ack_No,Application_Name,Ack_Date,status from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and type='"+type+"';");
						ResultSet rs3=ps3.executeQuery();
						int slid=0;
						while (rs3.next()) {
							slid=1+slid;
							panRecord=new PanRecord();
							panRecord.setAckno(rs3.getString(1));
							if (rs3.getString(2)==null) {
								panRecord.setName("No Name");
							} else {
								panRecord.setName(rs3.getString(2));
							}
							panRecord.setAckdate(rs3.getString(3));
							panRecord.setSlno(slid);
							panRecord.setStatus(rs3.getString(4));
							panRecords.add(panRecord);
						}
						ps2.close();
						ps3.close();
						rs3.close();
						
					}
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		} else if (type.equals("Air")) {
			
			String bcode=null;
			try {		
				PreparedStatement ps=panconn.prepareStatement("select BranchCode from Branch_Master where BranchName='"+branchname+"';");
				ResultSet rs=ps.executeQuery();
				if (rs.next()) {
					bcode=rs.getString(1);
					PreparedStatement ppss=panconn.prepareStatement("select count(*) from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and Type='"+type+"'");
					ResultSet rrss=ppss.executeQuery();
					if (rrss.next()) {
						if (rrss.getInt(1)==0) {
							PreparedStatement pss=panconn.prepareStatement("insert into tinbosiomrecord (BranchCode,Ack_No,Application_Name,Ack_Date,Status,type)select '"+bcode+"',ack_no as ackno,NAME,ack_date,'Dispatched' as ackdate,'"+type+"' from air where ack_date='"+reqdate+"' and ack_no like '"+bcode+"%';");
							pss.execute();
							pss.close();
						}	
					}
					ppss.close();
					rrss.close();
					
				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			
			try {
				if (ackno.equals("0")) {
					PreparedStatement ps1=panconn.prepareStatement("select Ack_No,Application_Name,Ack_Date,status from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and type='"+type+"';");
					ResultSet rs1=ps1.executeQuery();
					int slid=0;
					while (rs1.next()) {
						slid=1+slid;
						panRecord=new PanRecord();
						panRecord.setAckno(rs1.getString(1));
						if (rs1.getString(2)==null) {
							panRecord.setName("No Name");
						} else {
							panRecord.setName(rs1.getString(2));
						}
						panRecord.setAckdate(rs1.getString(3));
						panRecord.setSlno(slid);
						panRecord.setStatus(rs1.getString(4));
						panRecords.add(panRecord);
					}
					ps1.close();
					rs1.close();
					
				}else {
					
					if (ackstatus.equals("Dispatched")) {
						PreparedStatement ps2=panconn.prepareStatement("update TinbosIOMRecord set Status='"+ackstatus+"',Remarks=null where Ack_No='"+ackno+"';");
						ps2.execute();
						
						PreparedStatement ps3=panconn.prepareStatement("select Ack_No,Application_Name,Ack_Date,status from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and type='"+type+"';");
						ResultSet rs3=ps3.executeQuery();
						int slid=0;
						while (rs3.next()) {
							slid=1+slid;
							panRecord=new PanRecord();
							panRecord.setAckno(rs3.getString(1));
							if (rs3.getString(2)==null) {
								panRecord.setName("No Name");
							} else {
								panRecord.setName(rs3.getString(2));
							}
							
							panRecord.setAckdate(rs3.getString(3));
							panRecord.setSlno(slid);
							panRecord.setStatus(rs3.getString(4));
							panRecords.add(panRecord);
						}
						ps2.close();
						ps3.close();
						rs3.close();
					} else if (ackstatus.equals("Pending")) {
						
						PreparedStatement ps2=panconn.prepareStatement("update TinbosIOMRecord set Status='"+ackstatus+"',Remarks='"+ackremarks+"' where Ack_No='"+ackno+"';");
						ps2.execute();
						
						PreparedStatement ps3=panconn.prepareStatement("select Ack_No,Application_Name,Ack_Date,status from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and type='"+type+"';");
						ResultSet rs3=ps3.executeQuery();
						int slid=0;
						while (rs3.next()) {
							slid=1+slid;
							panRecord=new PanRecord();
							panRecord.setAckno(rs3.getString(1));
							if (rs3.getString(2)==null) {
								panRecord.setName("No Name");
							} else {
								panRecord.setName(rs3.getString(2));
							}
							panRecord.setAckdate(rs3.getString(3));
							panRecord.setSlno(slid);
							panRecord.setStatus(rs3.getString(4));
							panRecords.add(panRecord);
						}
						ps2.close();
						ps3.close();
						rs3.close();
						
					} else if (ackstatus.equals("Reject")) {
						
						PreparedStatement ps2=panconn.prepareStatement("update TinbosIOMRecord set Status='"+ackstatus+"',Remarks='"+ackremarks+"' where Ack_No='"+ackno+"';");
						ps2.execute();
						
						PreparedStatement ps3=panconn.prepareStatement("select Ack_No,Application_Name,Ack_Date,status from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and type='"+type+"';");
						ResultSet rs3=ps3.executeQuery();
						int slid=0;
						while (rs3.next()) {
							slid=1+slid;
							panRecord=new PanRecord();
							panRecord.setAckno(rs3.getString(1));
							if (rs3.getString(2)==null) {
								panRecord.setName("No Name");
							} else {
								panRecord.setName(rs3.getString(2));
							}
							panRecord.setAckdate(rs3.getString(3));
							panRecord.setSlno(slid);
							panRecord.setStatus(rs3.getString(4));
							panRecords.add(panRecord);
						}
						ps2.close();
						ps3.close();
						rs3.close();
						
					}
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else if (type.equals("24G")) {
			
			String bcode=null;
			try {		
				PreparedStatement ps=panconn.prepareStatement("select BranchCode from Branch_Master where BranchName='"+branchname+"';");
				ResultSet rs=ps.executeQuery();
				if (rs.next()) {
					bcode=rs.getString(1);
					PreparedStatement ppss=panconn.prepareStatement("select count(*) from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and Type='"+type+"'");
					ResultSet rrss=ppss.executeQuery();
					if (rrss.next()) {
						if (rrss.getInt(1)==0) {
							PreparedStatement pss=panconn.prepareStatement("insert into tinbosiomrecord (BranchCode,Ack_No,Application_Name,Ack_Date,Status,type)select '"+bcode+"',ackno,AOName,ack_date,'Dispatched' as ackdate,'"+type+"' from From24gULoadfiles where ack_date='"+reqdate+"' and ackno like '"+bcode+"%';");
							pss.execute();
							pss.close();
						}	
					}
					ppss.close();
					rrss.close();
					
				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			
			try {
				if (ackno.equals("0")) {
					PreparedStatement ps1=panconn.prepareStatement("select Ack_No,Application_Name,Ack_Date,status from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and type='"+type+"';");
					ResultSet rs1=ps1.executeQuery();
					int slid=0;
					while (rs1.next()) {
						slid=1+slid;
						panRecord=new PanRecord();
						panRecord.setAckno(rs1.getString(1));
						if (rs1.getString(2)==null) {
							panRecord.setName("No Name");
						} else {
							panRecord.setName(rs1.getString(2));
						}
						panRecord.setAckdate(rs1.getString(3));
						panRecord.setSlno(slid);
						panRecord.setStatus(rs1.getString(4));
						panRecords.add(panRecord);
					}
					ps1.close();
					rs1.close();
					
				}else {
					
					if (ackstatus.equals("Dispatched")) {
						PreparedStatement ps2=panconn.prepareStatement("update TinbosIOMRecord set Status='"+ackstatus+"',Remarks=null where Ack_No='"+ackno+"';");
						ps2.execute();
						
						PreparedStatement ps3=panconn.prepareStatement("select Ack_No,Application_Name,Ack_Date,status from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and type='"+type+"';");
						ResultSet rs3=ps3.executeQuery();
						int slid=0;
						while (rs3.next()) {
							slid=1+slid;
							panRecord=new PanRecord();
							panRecord.setAckno(rs3.getString(1));
							if (rs3.getString(2)==null) {
								panRecord.setName("No Name");
							} else {
								panRecord.setName(rs3.getString(2));
							}
							
							panRecord.setAckdate(rs3.getString(3));
							panRecord.setSlno(slid);
							panRecord.setStatus(rs3.getString(4));
							panRecords.add(panRecord);
						}
						ps2.close();
						ps3.close();
						rs3.close();
					} else if (ackstatus.equals("Pending")) {
						
						PreparedStatement ps2=panconn.prepareStatement("update TinbosIOMRecord set Status='"+ackstatus+"',Remarks='"+ackremarks+"' where Ack_No='"+ackno+"';");
						ps2.execute();
						
						PreparedStatement ps3=panconn.prepareStatement("select Ack_No,Application_Name,Ack_Date,status from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and type='"+type+"';");
						ResultSet rs3=ps3.executeQuery();
						int slid=0;
						while (rs3.next()) {
							slid=1+slid;
							panRecord=new PanRecord();
							panRecord.setAckno(rs3.getString(1));
							if (rs3.getString(2)==null) {
								panRecord.setName("No Name");
							} else {
								panRecord.setName(rs3.getString(2));
							}
							panRecord.setAckdate(rs3.getString(3));
							panRecord.setSlno(slid);
							panRecord.setStatus(rs3.getString(4));
							panRecords.add(panRecord);
						}
						ps2.close();
						ps3.close();
						rs3.close();
						
					} else if (ackstatus.equals("Reject")) {
						
						PreparedStatement ps2=panconn.prepareStatement("update TinbosIOMRecord set Status='"+ackstatus+"',Remarks='"+ackremarks+"' where Ack_No='"+ackno+"';");
						ps2.execute();
						
						PreparedStatement ps3=panconn.prepareStatement("select Ack_No,Application_Name,Ack_Date,status from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and type='"+type+"';");
						ResultSet rs3=ps3.executeQuery();
						int slid=0;
						while (rs3.next()) {
							slid=1+slid;
							panRecord=new PanRecord();
							panRecord.setAckno(rs3.getString(1));
							if (rs3.getString(2)==null) {
								panRecord.setName("No Name");
							} else {
								panRecord.setName(rs3.getString(2));
							}
							panRecord.setAckdate(rs3.getString(3));
							panRecord.setSlno(slid);
							panRecord.setStatus(rs3.getString(4));
							panRecords.add(panRecord);
						}
						ps2.close();
						ps3.close();
						rs3.close();
						
					}
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else if (type.equals("All")) {
			
			try {
				PreparedStatement pStatement=panconn.prepareStatement("select type from TinbosRtype order by id");
				ResultSet rSet=pStatement.executeQuery();
				while (rSet.next()) {
					
					if (rSet.getString(1).equals("Pan")) {	
						String bcode=null;
						try {		
							PreparedStatement ps=panconn.prepareStatement("select BranchCode from Branch_Master where BranchName='"+branchname+"';");
							ResultSet rs=ps.executeQuery();
							if (rs.next()) {
								bcode=rs.getString(1);
								PreparedStatement ppss=panconn.prepareStatement("select count(*) from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and Type='"+rSet.getString(1)+"'");
								ResultSet rrss=ppss.executeQuery();
								if (rrss.next()) {
									if (rrss.getInt(1)==0) {
										PreparedStatement pss=panconn.prepareStatement("insert into tinbosiomrecord (BranchCode,Ack_No,Application_Name,Ack_Date,Status,type)select '"+bcode+"',ack_no as ackno,(first_name+' '+middle_name+' '+last_name) as name,ack_date,'Dispatched' as ackdate,'"+rSet.getString(1)+"' from pan where ack_date='"+reqdate+"' and ack_no like '"+bcode+"%';");
										pss.execute();
										pss.close();
									}	
								}
								ppss.close();
								rrss.close();
								
							}
							ps.close();
							rs.close();
						} catch (Exception e) {
							System.out.println(e);
						}
					}else if (rSet.getString(1).equals("Pran")) {
						String bcode=null;
						try {		
							PreparedStatement ps=panconn.prepareStatement("select BranchCode from Branch_Master where BranchName='"+branchname+"';");
							ResultSet rs=ps.executeQuery();
							if (rs.next()) {
								bcode=Integer.toString(Integer.parseInt(rs.getString(1)));
								PreparedStatement ppss=panconn.prepareStatement("select count(*) from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and Type='"+rSet.getString(1)+"'");
								ResultSet rrss=ppss.executeQuery();
								if (rrss.next()) {
									if (rrss.getInt(1)==0) {
										PreparedStatement pss=panconn.prepareStatement("insert into tinbosiomrecord (BranchCode,Ack_No,Application_Name,Ack_Date,Status,type) select '0"+bcode+"',ackno,formtype as name,ackdate,'Dispatched' as ackdate,'"+rSet.getString(1)+"' from cramis where ackdate='"+reqdate+"' and ackno like '"+bcode+"%';");
										pss.execute();
										pss.close();
									}	
								}
								ppss.close();
								rrss.close();
								
							}
							ps.close();
							rs.close();
						} catch (Exception e) {
							System.out.println(e);
						}
						
					} else if (rSet.getString(1).equals("24G")) {
						String bcode=null;
						try {		
							PreparedStatement ps=panconn.prepareStatement("select BranchCode from Branch_Master where BranchName='"+branchname+"';");
							ResultSet rs=ps.executeQuery();
							if (rs.next()) {
								bcode=rs.getString(1);
								PreparedStatement ppss=panconn.prepareStatement("select count(*) from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and Type='"+rSet.getString(1)+"'");
								ResultSet rrss=ppss.executeQuery();
								if (rrss.next()) {
									if (rrss.getInt(1)==0) {
										PreparedStatement pss=panconn.prepareStatement("insert into tinbosiomrecord (BranchCode,Ack_No,Application_Name,Ack_Date,Status,type)select '"+bcode+"',ackno,AOName,ack_date,'Dispatched' as ackdate,'"+rSet.getString(1)+"' from From24gULoadfiles where ack_date='"+reqdate+"' and ackno like '"+bcode+"%';");
										pss.execute();
										pss.close();
									}	
								}
								ppss.close();
								rrss.close();
								
							}
							ps.close();
							rs.close();
						} catch (Exception e) {
							System.out.println(e);
						}
						
					} else if (rSet.getString(1).equals("Etds")) {
						String bcode=null;
						try {		
							PreparedStatement ps=panconn.prepareStatement("select BranchCode from Branch_Master where BranchName='"+branchname+"';");
							ResultSet rs=ps.executeQuery();
							if (rs.next()) {
								bcode=rs.getString(1);
								PreparedStatement ppss=panconn.prepareStatement("select count(*) from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and Type='"+rSet.getString(1)+"'");
								ResultSet rrss=ppss.executeQuery();
								if (rrss.next()) {
									if (rrss.getInt(1)==0) {
										PreparedStatement pss=panconn.prepareStatement("insert into tinbosiomrecord (BranchCode,Ack_No,Application_Name,Ack_Date,Status,type)select '"+bcode+"',ack_no as ackno,NAME,ack_date,'Dispatched' as ackdate,'"+rSet.getString(1)+"' from EtdsQuarterly where ack_date='"+reqdate+"' and ack_no like '"+bcode+"%' and ele_phy='E' ;");
										pss.execute();
										pss.close();
									}	
								}
								ppss.close();
								rrss.close();
								
							}
							ps.close();
							rs.close();
						} catch (Exception e) {
							System.out.println(e);
						}
						
					} else if (rSet.getString(1).equals("Tan")) {

						String bcode=null;
						try {		
							PreparedStatement ps=panconn.prepareStatement("select BranchCode from Branch_Master where BranchName='"+branchname+"';");
							ResultSet rs=ps.executeQuery();
							if (rs.next()) {
								bcode=rs.getString(1);
								PreparedStatement ppss=panconn.prepareStatement("select count(*) from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and Type='"+rSet.getString(1)+"'");
								ResultSet rrss=ppss.executeQuery();
								if (rrss.next()) {
									if (rrss.getInt(1)==0) {
										PreparedStatement pss=panconn.prepareStatement("insert into tinbosiomrecord (BranchCode,Ack_No,Application_Name,Ack_Date,Status,type)select '"+bcode+"',ack_no as ackno,(first_name+' '+middle_name+' '+last_name+' '+Name_Of_Deductor) as name,ack_date,'Dispatched' as ackdate,'"+rSet.getString(1)+"' from tan where ack_date='"+reqdate+"' and ack_no like '"+bcode+"%';");
										pss.execute();
										pss.close();
									}	
								}
								ppss.close();
								rrss.close();
								
							}
							ps.close();
							rs.close();
						} catch (Exception e) {
							System.out.println(e);
						}
						
					}	else if (rSet.getString(1).equals("Paper_Return")) {
						String bcode=null;
						try {		
							PreparedStatement ps=panconn.prepareStatement("select BranchCode from Branch_Master where BranchName='"+branchname+"';");
							ResultSet rs=ps.executeQuery();
							if (rs.next()) {
								bcode=rs.getString(1);
								PreparedStatement ppss=panconn.prepareStatement("select count(*) from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and Type='"+rSet.getString(1)+"'");
								ResultSet rrss=ppss.executeQuery();
								if (rrss.next()) {
									if (rrss.getInt(1)==0) {
										PreparedStatement pss=panconn.prepareStatement("insert into tinbosiomrecord (BranchCode,Ack_No,Application_Name,Ack_Date,Status,type)select '"+bcode+"',ack_no as ackno,NAME,ack_date,'Dispatched' as ackdate,'"+rSet.getString(1)+"' from EtdsQuarterly where ack_date='"+reqdate+"' and ack_no like '"+bcode+"%' and ele_phy='P' ;");
										pss.execute();
										pss.close();
									}	
								}
								ppss.close();
								rrss.close();
								
							}
							ps.close();
							rs.close();
						} catch (Exception e) {
							System.out.println(e);
						}
					} else if (rSet.getString(1).equals("Air")) {
						String bcode=null;
						try {		
							PreparedStatement ps=panconn.prepareStatement("select BranchCode from Branch_Master where BranchName='"+branchname+"';");
							ResultSet rs=ps.executeQuery();
							if (rs.next()) {
								bcode=rs.getString(1);
								PreparedStatement ppss=panconn.prepareStatement("select count(*) from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"' and Type='"+rSet.getString(1)+"'");
								ResultSet rrss=ppss.executeQuery();
								if (rrss.next()) {
									if (rrss.getInt(1)==0) {
										PreparedStatement pss=panconn.prepareStatement("insert into tinbosiomrecord (BranchCode,Ack_No,Application_Name,Ack_Date,Status,type)select '"+bcode+"',ack_no as ackno,NAME,ack_date,'Dispatched' as ackdate,'"+rSet.getString(1)+"' from air where ack_date='"+reqdate+"' and ack_no like '"+bcode+"%';");
										pss.execute();
										pss.close();
									}	
								}
								ppss.close();
								rrss.close();
								
							}
							ps.close();
							rs.close();
						} catch (Exception e) {
							System.out.println(e);
						}
						
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				String codeb=null;
				PreparedStatement pl=panconn.prepareStatement("select BranchCode from Branch_Master where BranchName='"+branchname+"';");
				ResultSet rl=pl.executeQuery();
				if (rl.next()) {
					codeb=rl.getString(1);
				}
				
				if (ackno.equals("0")) {
					PreparedStatement ps1=panconn.prepareStatement("select Ack_No,Application_Name,Ack_Date,status,type from TinbosIOMRecord where BranchCode='"+codeb+"' and Ack_Date='"+reqdate+"';");
					ResultSet rs1=ps1.executeQuery();
					int slid=0;
					while (rs1.next()) {
						slid=1+slid;
						panRecord=new PanRecord();
						panRecord.setAckno(rs1.getString(1));
						if (rs1.getString(2)==null) {
							panRecord.setName("No Name");
						} else {
							panRecord.setName(rs1.getString(2));
						}
						panRecord.setAckdate(rs1.getString(3));
						panRecord.setSlno(slid);
						panRecord.setStatus(rs1.getString(4)+" ("+rs1.getString(5)+")");
						panRecords.add(panRecord);
					}
					ps1.close();
					rs1.close();
					
				} else {
					
					if (ackstatus.equals("Dispatched")) {
						PreparedStatement ps2=panconn.prepareStatement("update TinbosIOMRecord set Status='"+ackstatus+"',Remarks=null where Ack_No='"+ackno+"';");
						ps2.execute();
						
						PreparedStatement ps3=panconn.prepareStatement("select Ack_No,Application_Name,Ack_Date,status,type from TinbosIOMRecord where BranchCode='"+codeb+"' and Ack_Date='"+reqdate+"';");
						ResultSet rs3=ps3.executeQuery();
						int slid=0;
						while (rs3.next()) {
							slid=1+slid;
							panRecord=new PanRecord();
							panRecord.setAckno(rs3.getString(1));
							if (rs3.getString(2)==null) {
								panRecord.setName("No Name");
							} else {
								panRecord.setName(rs3.getString(2));
							}
							
							panRecord.setAckdate(rs3.getString(3));
							panRecord.setSlno(slid);
							panRecord.setStatus(rs3.getString(4)+" ("+rs3.getString(5)+")");
							panRecords.add(panRecord);
						}
						ps2.close();
						ps3.close();
						rs3.close();
					} else if (ackstatus.equals("Pending")) {
						
						PreparedStatement ps2=panconn.prepareStatement("update TinbosIOMRecord set Status='"+ackstatus+"',Remarks='"+ackremarks+"' where Ack_No='"+ackno+"';");
						ps2.execute();
						
						PreparedStatement ps3=panconn.prepareStatement("select Ack_No,Application_Name,Ack_Date,status,type from TinbosIOMRecord where BranchCode='"+codeb+"' and Ack_Date='"+reqdate+"';");
						ResultSet rs3=ps3.executeQuery();
						int slid=0;
						while (rs3.next()) {
							slid=1+slid;
							panRecord=new PanRecord();
							panRecord.setAckno(rs3.getString(1));
							if (rs3.getString(2)==null) {
								panRecord.setName("No Name");
							} else {
								panRecord.setName(rs3.getString(2));
							}
							panRecord.setAckdate(rs3.getString(3));
							panRecord.setSlno(slid);
							panRecord.setStatus(rs3.getString(4)+" ("+rs3.getString(5)+")");
							panRecords.add(panRecord);
						}
						ps2.close();
						ps3.close();
						rs3.close();
						
					} else if (ackstatus.equals("Reject")) {
						
						PreparedStatement ps2=panconn.prepareStatement("update TinbosIOMRecord set Status='"+ackstatus+"',Remarks='"+ackremarks+"' where Ack_No='"+ackno+"';");
						ps2.execute();
						
						PreparedStatement ps3=panconn.prepareStatement("select Ack_No,Application_Name,Ack_Date,status,type from TinbosIOMRecord where BranchCode='"+codeb+"' and Ack_Date='"+reqdate+"';");
						ResultSet rs3=ps3.executeQuery();
						int slid=0;
						while (rs3.next()) {
							slid=1+slid;
							panRecord=new PanRecord();
							panRecord.setAckno(rs3.getString(1));
							if (rs3.getString(2)==null) {
								panRecord.setName("No Name");
							} else {
								panRecord.setName(rs3.getString(2));
							}
							panRecord.setAckdate(rs3.getString(3));
							panRecord.setSlno(slid);
							panRecord.setStatus(rs3.getString(4)+" ("+rs3.getString(5)+")");
							panRecords.add(panRecord);
						}
						ps2.close();
						ps3.close();
						rs3.close();
						
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return panRecords;
	}
}