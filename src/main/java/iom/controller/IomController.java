package iom.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import iom.model.CourierStatus;
import iom.model.GenerateIOM;
import iom.model.GetRefdetail;
import iom.model.IomReport;
import iom.model.PanRecord;
import iom.model.ReportRequeast;
import iom.model.ReqDate;
import iom.model.RequestData;
import iom.model.User;
import iom.service.UserService;
import iom.utility.IomUtility;

@Controller
public class IomController {
	
	@Autowired
	UserService userService;
	
	IomUtility iomUtility=new IomUtility();
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<GrantedAuthority> list=(List<GrantedAuthority>) auth.getAuthorities();
        if (list.get(0).getAuthority().equalsIgnoreCase("Administrator")) {
            return "redirect:/admin/home";
        } else if (list.get(0).getAuthority().equalsIgnoreCase("User")) {
            return "redirect:/user/home";
        } else {
        	return "redirect:/login";
		}
    }
	
	@RequestMapping(value="/error", method={RequestMethod.GET})
	public ModelAndView getError()
	{
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("error");
		return modelAndView;
	}
	
	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/home", method = RequestMethod.GET)
	public ModelAndView adminhome(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user=userService.findByUsername(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getFullname());
		modelAndView.setViewName("admin/home");
		return modelAndView;
	}
	
	@RequestMapping(value="/user/home", method = RequestMethod.GET)
	public ModelAndView userhome(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user=userService.findByUsername(auth.getName());
		RequestData requestData=new RequestData();
		modelAndView.addObject("userName", "Welcome " + user.getFullname());
		modelAndView.addObject("branchname","Branch: "+user.getBranchname());
		modelAndView.addObject("requestData",requestData);
		modelAndView.addObject("msg","");
		modelAndView.setViewName("user/home");
		return modelAndView;
		
	}
	
	@RequestMapping( method = RequestMethod.POST , value="/user/home")
	public ModelAndView getData(@Valid RequestData requestData,BindingResult result){
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		User user=userService.findByUsername(auth.getName());
			
		try {
			if (result.hasErrors()) {
				ModelAndView modelAndView=new ModelAndView();
				modelAndView.addObject("userName","Welcome "+user.getFullname());
				modelAndView.addObject("branchname","Branch: "+user.getBranchname());
				modelAndView.addObject("requestData",requestData);
				modelAndView.addObject("msg","");
				modelAndView.addObject("result", false);
				modelAndView.setViewName("user/home");
				return modelAndView;
			}
			
			if (requestData.getAckno()==null) {
				requestData.setAckno("0");
			}
			if (requestData.getAckstatus()==null) {
				requestData.setAckstatus(" ");
			}
			if (requestData.getAckremarks()==null) {
				requestData.setAckremarks(" ");
			}
			
			List<PanRecord>	panRecords= iomUtility.sendRequeast(requestData.getType(),requestData.getRdate(),user.getBranchname(),requestData.getAckno(),requestData.getAckstatus(),requestData.getAckremarks());
			
			if (panRecords.size()==0) {
				ModelAndView modelAndView=new ModelAndView();
				modelAndView.addObject("userName","Welcome "+user.getFullname());
				modelAndView.addObject("branchname","Branch: "+user.getBranchname());
				modelAndView.addObject("requestData",requestData);
				modelAndView.addObject("msg","No Record found");
				modelAndView.addObject("result", false);
				modelAndView.setViewName("user/home");
				return modelAndView;
			}
			
			ModelAndView modelAndView=new ModelAndView();
			modelAndView.addObject("userName", "Welcome " + user.getFullname());
			modelAndView.addObject("branchname","Branch: "+user.getBranchname());
			modelAndView.addObject("msg","");
			modelAndView.addObject("result", true);
			modelAndView.addObject("panrecords",panRecords);
			modelAndView.setViewName("user/home");
			return modelAndView;
			
		} catch (Exception e) {
			ModelAndView modelAndView=new ModelAndView();
			modelAndView.addObject("userName","Welcome "+user.getFullname());
			modelAndView.addObject("branchname","Branch: "+user.getBranchname());
			modelAndView.addObject("requestData",requestData);
			modelAndView.addObject("msg","Data Error");
			modelAndView.addObject("result", false);
			modelAndView.setViewName("user/home");
			return modelAndView;
		}
	}
	
	@RequestMapping(value="/user/submit", method = RequestMethod.GET)
	public ModelAndView userCourier(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ReportRequeast reportRequeast=new ReportRequeast();
		reportRequeast.setStatus("N");
		User user=userService.findByUsername(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getFullname());
		modelAndView.addObject("branchname","Branch: "+user.getBranchname());
		modelAndView.addObject("reportRequeast",reportRequeast);
		modelAndView.addObject("msg","");
		modelAndView.setViewName("user/submit");
		return modelAndView;
	}
	
	@RequestMapping( method = RequestMethod.POST , value="/user/submit")
	public ModelAndView getCReport(@Valid ReportRequeast reportRequeast,BindingResult result){
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		User user=userService.findByUsername(auth.getName());
		
		if (result.hasErrors()) {
			ModelAndView modelAndView=new ModelAndView();
			modelAndView.addObject("userName","Welcome "+user.getFullname());
			modelAndView.addObject("branchname","Branch: "+user.getBranchname());
			modelAndView.addObject("reportRequeast",reportRequeast);
			modelAndView.addObject("msg","");
			modelAndView.setViewName("user/submit");
			return modelAndView;
		}
		
			ReqDate reqDate=iomUtility.getReport(user.getBranchname(),reportRequeast.getReqdate(),reportRequeast.getConame(),reportRequeast.getTrackid(),reportRequeast.getRemarks());
			
			if (reqDate.getTotal()==0) {
				ModelAndView modelAndView=new ModelAndView();
				modelAndView.addObject("userName","Welcome "+user.getFullname());
				modelAndView.addObject("branchname","Branch: "+user.getBranchname());
				modelAndView.addObject("reportRequeast",reportRequeast);
				modelAndView.addObject("msg","Please Update Your Application Status  Click Home Option for Update");
				modelAndView.setViewName("user/submit");
				return modelAndView;
			} 
			
			if (reqDate.getTotal()>0) {
				ModelAndView modelAndView=new ModelAndView();
				reportRequeast.setStatus("V");
				modelAndView.addObject("userName","Welcome "+user.getFullname());
				modelAndView.addObject("branchname","Branch: "+user.getBranchname());
				modelAndView.addObject("reportRequeast",reportRequeast);
				modelAndView.addObject("panCou",reqDate.getPan());
				modelAndView.addObject("pranCou",reqDate.getPran());
				modelAndView.addObject("etdsCou",reqDate.getEtds());
				modelAndView.addObject("tanCou",reqDate.getTan());
				modelAndView.addObject("paperCou",reqDate.getPaper());
				modelAndView.addObject("airCou",reqDate.getAir());
				modelAndView.addObject("g24Cou",reqDate.getG24());
				modelAndView.addObject("totalCou",reqDate.getTotal());
				modelAndView.addObject("refid",reqDate.getRefno());
				modelAndView.addObject("msg","");
				modelAndView.setViewName("user/submit");
				return modelAndView;
				
			}
			
			
			ModelAndView modelAndView=new ModelAndView();
			modelAndView.addObject("userName","Welcome "+user.getFullname());
			modelAndView.addObject("branchname","Branch: "+user.getBranchname());
			modelAndView.addObject("reportRequeast",reportRequeast);
			modelAndView.addObject("msg","");
			modelAndView.setViewName("user/submit");
			return modelAndView;
	}
	
	@RequestMapping(value="/user/report", method = RequestMethod.GET)
	public ModelAndView userReport(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		IomReport iomReport=new IomReport();
		iomReport.setStatus("N");
		User user=userService.findByUsername(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getFullname());
		modelAndView.addObject("branchname","Branch: "+user.getBranchname());
		modelAndView.addObject("iomReport",iomReport);
		modelAndView.addObject("msg","");
		modelAndView.setViewName("user/report");
		return modelAndView;
	}
	
	@RequestMapping( method = RequestMethod.POST , value="/user/report")
	public ModelAndView getIomReport(@Valid IomReport iomReport,BindingResult result){
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		User user=userService.findByUsername(auth.getName());
		
		if (result.hasErrors()) {
			ModelAndView modelAndView=new ModelAndView();
			modelAndView.addObject("userName","Welcome "+user.getFullname());
			modelAndView.addObject("branchname","Branch: "+user.getBranchname());
			modelAndView.addObject("iomReport",iomReport);
			modelAndView.addObject("msg","");
			modelAndView.setViewName("user/report");
			return modelAndView;
		}
		
		if (iomReport.getReqtype().equals("Get_Reference_No")) {
			GetRefdetail getRefdetail=iomUtility.getref(iomReport.getReqdate(),user.getBranchname());
			
			if (!getRefdetail.getRefno().equals("0")) {
				iomReport.setStatus("I");
				ModelAndView modelAndView=new ModelAndView();
				modelAndView.addObject("userName","Welcome "+user.getFullname());
				modelAndView.addObject("branchname","Branch: "+user.getBranchname());
				modelAndView.addObject("iomReport",iomReport);
				modelAndView.addObject("totalrecord",getRefdetail.getTotal());
				modelAndView.addObject("requpdate",getRefdetail.getRequpdate());
				modelAndView.addObject("coname",getRefdetail.getConame());
				modelAndView.addObject("refno",getRefdetail.getRefno());
				modelAndView.addObject("msg","");
				modelAndView.setViewName("user/report");
				return modelAndView;
			} else {
				iomReport.setStatus("N");
				ModelAndView modelAndView=new ModelAndView();
				modelAndView.addObject("userName","Welcome "+user.getFullname());
				modelAndView.addObject("branchname","Branch: "+user.getBranchname());
				modelAndView.addObject("iomReport",iomReport);
				modelAndView.addObject("msg",iomReport.getReqdate()+" Referance No Not Found or Courier Details Not Update");
				modelAndView.setViewName("user/report");
				return modelAndView;
			}
			
			
		} else if(iomReport.getReqtype().equals("Courier_Status")) {
			CourierStatus courierStatus=iomUtility.courierStatus(iomReport.getRequeastno(),user.getBranchname());
			
			if (!courierStatus.getRefno().equals("0")) {
				iomReport.setStatus("C");
				ModelAndView modelAndView=new ModelAndView();
				modelAndView.addObject("userName","Welcome "+user.getFullname());
				modelAndView.addObject("branchname","Branch: "+user.getBranchname());
				modelAndView.addObject("iomReport",iomReport);
				modelAndView.addObject("cototal",courierStatus.getTotalcou());
				modelAndView.addObject("corefid",courierStatus.getRefno());
				modelAndView.addObject("coconame",courierStatus.getConame());
				modelAndView.addObject("cotrackid",courierStatus.getTrackid());
				modelAndView.addObject("cocostatus",courierStatus.getCostatus());
				modelAndView.addObject("coupdate",courierStatus.getUpdate());
				modelAndView.addObject("msg","");
				modelAndView.setViewName("user/report");
				return modelAndView;
				
			} else {
				iomReport.setStatus("N");
				ModelAndView modelAndView=new ModelAndView();
				modelAndView.addObject("userName","Welcome "+user.getFullname());
				modelAndView.addObject("branchname","Branch: "+user.getBranchname());
				modelAndView.addObject("iomReport",iomReport);
				modelAndView.addObject("msg","Referance No Not Found or Courier Details Not Update");
				modelAndView.setViewName("user/report");
				return modelAndView;
			}
		}
		List<GenerateIOM> generateIOMs=iomUtility.iomGenearet(iomReport.getRequeastno());	
					
		System.out.println("LL: "+generateIOMs.toString());
		
		return new ModelAndView("IOMPdf","generateIOMs", generateIOMs);
	}
	
}