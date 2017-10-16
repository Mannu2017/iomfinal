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

import iom.model.PanRecord;
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
        } 
        return "redirect:/login";
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
		User user=userService.findByUsername(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getFullname());
		modelAndView.addObject("branchname","Branch: "+user.getBranchname());
		modelAndView.addObject("msg","");
		modelAndView.setViewName("user/submit");
		return modelAndView;
	}
	
	
	
}