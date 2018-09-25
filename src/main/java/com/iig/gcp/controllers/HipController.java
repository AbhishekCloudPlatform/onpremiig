package com.iig.gcp.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.iig.gcp.hipdashboard.service.HipService;

@Controller
public class HipController {

	@Autowired
	HipService hipService;
	
	@RequestMapping(value = { "/hip"}, method = RequestMethod.GET)
    public String hipDashboard(ModelMap map) {
		
		try {
		ArrayList<String> fs = hipService.getfeedsFromLoggerStats();
		map.addAttribute("feed_id", fs);
		}
		catch(Exception e) {
			
			e.printStackTrace();
		}
        return "/hip/hipdashboard";
    }
	
	@RequestMapping(value = { "/hipmaster"}, method = RequestMethod.GET)
	public ModelAndView hipmasterDashboard(ModelMap map)
			throws Exception {
		ArrayList<String> fs = hipService.getfeeds();
		map.addAttribute("feed_id", fs);
        return  new ModelAndView("/hip/hipmasterdashboard");
    }
}
