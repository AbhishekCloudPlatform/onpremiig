package com.iig.gcp.controllers;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iig.gcp.feedlogging.dto.FeedLoggerDTO;
import com.iig.gcp.hipdashboard.dto.HipDashboardDTO;
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
	
	@RequestMapping(value = { "/hip/feedIdFilter"}, method = RequestMethod.POST)
	public ModelAndView hipFeedFilter(ModelMap map,@Valid @RequestParam("feed_id") String feed_id)
			throws Exception {
		
		ArrayList<String> arrBatchDate=new ArrayList<String>();
		ArrayList<String> arrDuration=new ArrayList<String>();
		ArrayList<HipDashboardDTO> arrHipDashboard = hipService.getTableChartLoggerStats(feed_id);
		map.addAttribute("arrHipDashboard", arrHipDashboard);
		
		ObjectMapper mapper = new ObjectMapper();

		for(HipDashboardDTO hipVO :arrHipDashboard) {
			//System.out.println("Job Id:"+archiveJob.getDuration());
			arrBatchDate.add(hipVO.getBatch_date().toString());
			arrDuration.add(hipVO.getDuration());
		}
		String json = mapper.writeValueAsString(arrBatchDate);
		//System.out.println("json String"+json);
		//System.out.println(" arrDuration"+arrDuration);
		map.addAttribute("x", arrBatchDate);
		map.addAttribute("y",arrDuration);
		
        return  new ModelAndView("/hip/hipdashboard2");
    }
	
	@RequestMapping(value = { "/hip/hipmasterdashboard1"}, method = RequestMethod.POST)
	public ModelAndView hipmasterDashboard1(@Valid @ModelAttribute("feed_id") String feed_id, ModelMap map)
			throws Exception {
		ArrayList<FeedLoggerDTO> fs = hipService.getfeeddetails(feed_id);
		map.addAttribute("feed", fs);
        return  new ModelAndView("/hip/hipmasterdashboard1");
    }
}
