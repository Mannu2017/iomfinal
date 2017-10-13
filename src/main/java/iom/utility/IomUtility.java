package iom.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
		panconn=DriverManager.getConnection("jdbc:sqlserver://localhost;user=Anu;password=Karvy@123;database=tinbos");
		}catch(Exception e)
		{	
			System.out.println("Error:"+e);
		}	
	}

	public List<PanRecord> sendRequeast(String type, String rdate, String branchname){
		
		List<PanRecord> panRecords=new  ArrayList<PanRecord>();
		PanRecord panRecord = null;
		
		if (type.equals("Pan")) {
			try {	
				
				Date passdat=new SimpleDateFormat("yyyy-MM-dd").parse(rdate);
				DateFormat df=new SimpleDateFormat("yyyyMMdd");
				String reqdate=df.format(passdat);
				PreparedStatement ps=panconn.prepareStatement("select BranchCode from Branch_Master where BranchName='"+branchname+"';");
				ResultSet rs=ps.executeQuery();
				if (rs.next()) {
					
					PreparedStatement ppss=panconn.prepareStatement("select count(*) from TinbosIOMRecord where Ack_No like '"+rs.getString(1)+"%' and Ack_Date='"+reqdate+"'");
					ResultSet rrss=ppss.executeQuery();
					if (rrss.next()) {
						System.out.println("Date: "+rrss.getInt(1));
						if (rrss.getInt(1)==0) {
							PreparedStatement pss=panconn.prepareStatement("insert into tinbosiomrecord (Reference_No,Ack_No,Application_Name,Ack_Date,Status)select '"+rs.getString(1)+reqdate+"',ack_no as ackno,(first_name+' '+middle_name+' '+last_name) as name,ack_date,'Dispatched' as ackdate from pan where ack_date='"+reqdate+"' and ack_no like '"+rs.getString(1)+"%';");
							pss.execute();
						}	
					}
					
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
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			
			
			
		}else {
			
		}
		
		return panRecords;
	}
}
