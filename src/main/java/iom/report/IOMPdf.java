package iom.report;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

import iom.model.GenerateIOM;

public class IOMPdf extends AbstractITextPdfView{
	
	Connection conn;
	String branchName;

	Font f1,f2,f3,f4,f5,f6,f7,f8,f9;
	Phrase ph=new Phrase();;
	ListItem hed,com,add,add1,add2,con,p1,p2,p3,p4,p5,p6;
	Phrase ph1,ph2;
	Chunk gp;
	
	private final String USER_AGENT = "Mozilla/5.0";
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		f1=new Font(FontFamily.TIMES_ROMAN);
		f1.setSize(10);
		f1.setStyle(1);
		f5 = new com.itextpdf.text.Font(FontFamily.TIMES_ROMAN);
		f4=new Font(FontFamily.TIMES_ROMAN);
		
		f2=new Font(FontFactory.getFont("Cooper Black", 16));
		f2.setColor(BaseColor.BLACK);
		f2.setStyle(1);
		f5.setSize(9);
		f4.setSize(12);
		
		f3=new Font(FontFamily.COURIER);
		f3.setSize(10);
		f3.setColor(BaseColor.DARK_GRAY);
		f4.setStyle(Font.BOLDITALIC);
		f4.setColor(BaseColor.WHITE);
		
		f6=new Font(FontFamily.TIMES_ROMAN);
		f6.setSize(12);
		f6.setStyle(Font.BOLD);
		
		f7=new Font(FontFamily.TIMES_ROMAN);
		f7.setSize(12);
		f7.setStyle(Font.NORMAL);
		
		f8=new Font();
		f8.setSize(9);
		
		f9=new Font();
		f9.setSize(10);
		
		gp=new Chunk(new VerticalPositionMark());
		Paragraph gap=new Paragraph(" ");
		
		GenerateIOM generateIOM = (GenerateIOM) model.get("generateIOM");
		
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn=DriverManager.getConnection("jdbc:sqlserver://localhost;user=Anu;password=Karvy@123;database=tinbos");
			
			PreparedStatement ps=conn.prepareStatement("select BranchName from Branch_Master where BranchCode='"+generateIOM.getBranchcode()+"'");
			ResultSet rs=ps.executeQuery();
			if (rs.next()) {
				branchName=rs.getString(1);
			}
			ps.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		document.open();
		document.setPageSize(PageSize.A4);
		
		Resource resource=new ClassPathResource("images/karlogo.jpg");
		InputStream rInputStream=resource.getInputStream();
		BufferedImage bufferedImage = ImageIO.read(rInputStream);
		Image img=Image.getInstance(writer,bufferedImage,1);
		
		resource=new ClassPathResource("images/karlogo.jpg");
		rInputStream=resource.getInputStream();
		bufferedImage = ImageIO.read(rInputStream);
		Image img1=Image.getInstance(writer,bufferedImage,1);
		img.scaleToFit(120, 100);
		img1.scaleToFit(120, 100);
		Paragraph imgp=new Paragraph();
		imgp.add(new Chunk(img,0,0,true));
		imgp.add(new Phrase("                                     Tinbos IOM Report                                    ",f1));
		imgp.add(new Chunk(img1,0,0,true));
		PdfPCell imgcl=new PdfPCell();
		imgcl.setBorder(0);
		imgcl.addElement(imgp);
		PdfPTable imt=new PdfPTable(1);
		imt.setWidthPercentage(90);
		imt.addCell(imgcl);
		ListItem head=new ListItem("Karvy Data Management Services Limited",f2);
		head.setAlignment(Element.ALIGN_CENTER);
		ListItem adres=new ListItem("“Karvy House”,No.46, Avenue-4,Street No.1,Road No.10,Banjara Hills,Hyderabad",f3);
		adres.setAlignment(Element.ALIGN_CENTER);
		ListItem adres1=new ListItem("Telangana 500034, Contact No: 04023312454",f3);
		adres1.setAlignment(Element.ALIGN_CENTER);
		
		PdfPTable tab2=new PdfPTable(3);
		tab2.setWidthPercentage(85.0F);
        tab2.setHorizontalAlignment(Element.ALIGN_CENTER);
        tab2.addCell(new Phrase("Branch: "+branchName,f7));
        tab2.addCell(new Phrase("Total: "+generateIOM.getTotalrecord(),f7));
        tab2.addCell(new Phrase("Ref No: "+generateIOM.getRefno(),f7));
     //   tab2.addCell(new Phrase("Ack Date: "+generateIOM.getAckdate(),f3));
        float[] columnWidths = {1f,1f, 1f, 1f,1f,1f,1f};
		PdfPTable tab3=new PdfPTable(columnWidths);
		tab3.setWidthPercentage(85.0F);
        tab3.setHorizontalAlignment(Element.ALIGN_CENTER);
        tab3.addCell(new Phrase("Pan: "+generateIOM.getPan(),f7));
        tab3.addCell(new Phrase("Pran: "+generateIOM.getPran(),f7));
        tab3.addCell(new Phrase("Etds: "+generateIOM.getEtds(),f7));
        tab3.addCell(new Phrase("Tan: "+generateIOM.getTan(),f7));
        tab3.addCell(new Phrase("Paper: "+generateIOM.getPaper(),f7));
        tab3.addCell(new Phrase("Air: "+generateIOM.getAir(),f7));
        tab3.addCell(new Phrase("24G: "+generateIOM.getG24(),f7));
        
        float[] columnW = {1f,2f, 3f, 2f,1f,2f,3f};
        PdfPTable tab=new PdfPTable(columnW);
        tab.setWidthPercentage(97.0F);
        try {
			PreparedStatement ps=conn.prepareStatement("select Ack_No,Application_Name,Type,Ack_Date,Status,Remarks from TinbosIOMRecord  where Reference_No='"+generateIOM.getRefno()+"'");
			ResultSet rs=ps.executeQuery();
			tab.addCell(new Phrase("Sl No",f7));
			tab.addCell(new Phrase("Ack No",f7));
			tab.addCell(new Phrase("Name",f7));
			tab.addCell(new Phrase("Ack Date",f7));
			tab.addCell(new Phrase("Type",f7));
			tab.addCell(new Phrase("Status",f7));
			tab.addCell(new Phrase("Remarks",f7));
			PdfPCell[] pc = tab.getRow(0).getCells();
            for (int j = 0; j < pc.length; j++) {
              pc[j].setBackgroundColor(new BaseColor(196, 188, 188));
            }
            int slno=0;
			while (rs.next()) {
				slno=1+slno;
				//System.out.println(slno);
				tab.addCell(new Phrase(slno+".",f8));
				tab.addCell(new Phrase(rs.getString(1),f8));
				if (rs.getString(2)==null) {
					tab.addCell(new Phrase("No Name",f8));
				} else {
					tab.addCell(new Phrase(rs.getString(2),f8));
				}
				tab.addCell(new Phrase(rs.getString(4),f8));
				tab.addCell(new Phrase(rs.getString(3),f8));
				tab.addCell(new Phrase(rs.getString(5),f8));
				if(rs.getString(6)==null) {
					tab.addCell(new Phrase(" "));
				}else {
				tab.addCell(new Phrase(rs.getString(6),f8));
				}
			}
        	
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        
         
		document.add(imt);
		document.add(head);
		document.add(adres);
		document.add(adres1);
		document.add(gap);
		document.add(tab2);
		document.add(tab3);
		document.add(gap);
		document.add(tab);
		document.close();
		
	}
	
}
