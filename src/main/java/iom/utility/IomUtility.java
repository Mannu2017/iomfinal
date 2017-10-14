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
		String bcode;
		if (type.equals("Pan")) {
			
			
				try {		
					PreparedStatement ps=panconn.prepareStatement("select BranchCode from Branch_Master where BranchName='"+branchname+"';");
					ResultSet rs=ps.executeQuery();
					if (rs.next()) {
						bcode=rs.getString(1);
						PreparedStatement ppss=panconn.prepareStatement("select count(*) from TinbosIOMRecord where Ack_No like '"+bcode+"%' and Ack_Date='"+reqdate+"'");
						ResultSet rrss=ppss.executeQuery();
						if (rrss.next()) {
							if (rrss.getInt(1)==0) {
								PreparedStatement pss=panconn.prepareStatement("insert into tinbosiomrecord (BranchCode,Ack_No,Application_Name,Ack_Date,Status,type)select '"+bcode+"',ack_no as ackno,(first_name+' '+middle_name+' '+last_name) as name,ack_date,'Dispatched' as ackdate,'"+type+"' from pan where ack_date='"+reqdate+"' and ack_no like '"+rs.getString(1)+"%';");
								pss.execute();
							}	
						}	
					}
					if (ackno.equals("0")) {
						PreparedStatement pps=panconn.prepareStatement("select Ack_No,Application_Name,Ack_Date,status from TinbosIOMRecord where Ack_No like '"+rs.getString(1)+"%' and Ack_Date='"+reqdate+"'");
						ResultSet rrs=pps.executeQuery();
						int slid=0;
						while (rrs.next()) {
							slid=1+slid;
							panRecord=new PanRecord();
							panRecord.setAckno(rrs.getString(1));
							panRecord.setName(rrs.getString(2));
							panRecord.setAckdate(rrs.getString(3));
							panRecord.setSlno(slid);
							panRecord.setStatus(rrs.getString(4));
							panRecords.add(panRecord);
							
						}
					}else {
						if (ackstatus.equals("Dispatched")) {
							PreparedStatement pps=panconn.prepareStatement("update TinbosIOMRecord set Status='"+ackstatus+"',Remarks=null where Ack_No='"+ackno+"';");
							pps.execute();
							PreparedStatement ppss=panconn.prepareStatement("select Ack_No,Application_Name,Ack_Date,status from TinbosIOMRecord where Ack_No like '"+rs.getString(1)+"%' and Ack_Date='"+reqdate+"'");
							ResultSet rrss=ppss.executeQuery();
							int slid=0;
							while (rrss.next()) {
								slid=1+slid;
								panRecord=new PanRecord();
								panRecord.setAckno(rrss.getString(1));
								panRecord.setName(rrss.getString(2));
								panRecord.setAckdate(rrss.getString(3));
								panRecord.setSlno(slid);
								panRecord.setStatus(rrss.getString(4));
								panRecords.add(panRecord);
								
							}
							
						}else if (ackstatus.equals("Pending")) {
							PreparedStatement pps=panconn.prepareStatement("update TinbosIOMRecord set Status='"+ackstatus+"',Remarks='"+ackremarks+"' where Ack_No='"+ackno+"';");
							pps.execute();
							PreparedStatement ppss=panconn.prepareStatement("select Ack_No,Application_Name,Ack_Date,status from TinbosIOMRecord where Ack_No like '"+rs.getString(1)+"%' and Ack_Date='"+reqdate+"'");
							ResultSet rrss=ppss.executeQuery();
							int slid=0;
							while (rrss.next()) {
								slid=1+slid;
								panRecord=new PanRecord();
								panRecord.setAckno(rrss.getString(1));
								panRecord.setName(rrss.getString(2));
								panRecord.setAckdate(rrss.getString(3));
								panRecord.setSlno(slid);
								panRecord.setStatus(rrss.getString(4));
								panRecords.add(panRecord);
								
							}
						}else if (ackstatus.equals("Reject")) {
							PreparedStatement pps=panconn.prepareStatement("update TinbosIOMRecord set Status='"+ackstatus+"',Remarks='"+ackremarks+"' where Ack_No='"+ackno+"';");
							pps.execute();
							PreparedStatement ppss=panconn.prepareStatement("select Ack_No,Application_Name,Ack_Date,status from TinbosIOMRecord where Ack_No like '"+rs.getString(1)+"%' and Ack_Date='"+reqdate+"'");
							ResultSet rrss=ppss.executeQuery();
							int slid=0;
							while (rrss.next()) {
								slid=1+slid;
								panRecord=new PanRecord();
								panRecord.setAckno(rrss.getString(1));
								panRecord.setName(rrss.getString(2));
								panRecord.setAckdate(rrss.getString(3));
								panRecord.setSlno(slid);
								panRecord.setStatus(rrss.getString(4));
								panRecords.add(panRecord);
								
							}
						}
					}
				} catch (Exception e) {
					System.out.println(e);
				}
			
			
		} else if (type.equals("Pran")) {
			System.out.println("Hello Mannu");
			
		}
		
		return panRecords;
	}
}
