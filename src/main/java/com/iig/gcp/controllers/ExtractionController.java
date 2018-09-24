package com.iig.gcp.controllers;

import java.io.IOException;
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

import com.iig.gcp.extraction.dto.ConnectionMaster;
import com.iig.gcp.extraction.dto.CountryMaster;
import com.iig.gcp.extraction.dto.ReservoirMaster;
import com.iig.gcp.extraction.dto.SourceSystemMaster;
import com.iig.gcp.extraction.service.ExtractionService;
import com.iig.gcp.utils.DBUtils;

@Controller
public class ExtractionController {

	@Autowired
	private ExtractionService es;

	
	@RequestMapping(value = "/extraction/ConnectionHome", method = RequestMethod.GET)
	public ModelAndView ConnectionHome() {
		return new ModelAndView("extraction/ConnectionHome");
	}

	@RequestMapping(value = "/extraction/ConnectionDetails", method = RequestMethod.POST)
	public ModelAndView ConnectionDetails(@Valid @ModelAttribute("src_val") String src_val, ModelMap model) {
		model.addAttribute("src_val", src_val);
		return new ModelAndView("extraction/ConnectionDetails");
	}

	@RequestMapping(value = "/extraction/ConnectionDetails1", method = RequestMethod.POST)
	public ModelAndView ConnectionDetails1(@Valid @ModelAttribute("x") String x, @ModelAttribute("src_val") String src_val, ModelMap model) throws UnsupportedOperationException, Exception {
//		System.out.println(x);
		String resp = es.invokeRest(x, "addConnection");
		model.addAttribute("successString", resp.toString());
		model.addAttribute("src_val", src_val);
		return new ModelAndView("extraction/ConnectionDetails");
	}
	
	@RequestMapping(value = "/extraction/SystemHome", method = RequestMethod.GET)
	public ModelAndView SystemHome() {
		return new ModelAndView("extraction/SystemHome");
	}

	@RequestMapping(value = "/extraction/SystemDetails", method = RequestMethod.POST)
	public ModelAndView SystemDetails(@Valid @ModelAttribute("src_val") String src_val, ModelMap model) {
		model.addAttribute("src_val", src_val);
		ArrayList<ConnectionMaster> conn_val = es.getConnections(src_val);
		model.addAttribute("conn_val", conn_val);
		/*ArrayList<String> buckets = DBUtils.getBuckets();
		model.addAttribute("buckets", buckets);*/
		ArrayList<CountryMaster> countries = es.getCountries();
		model.addAttribute("countries", countries);
		ArrayList<ReservoirMaster> reservoir = es.getReservoirs();
		model.addAttribute("reservoir", reservoir);
		return new ModelAndView("extraction/SystemDetails");
	}

	@RequestMapping(value = "/extraction/SystemDetails1", method = RequestMethod.POST)
	public ModelAndView SystemDetails1(@Valid @RequestParam(value = "sun", required = true) String sun, ModelMap model) throws UnsupportedOperationException, Exception {
		int stat = es.checkNames(sun);
		model.addAttribute("stat", stat);
		return new ModelAndView("extraction/SystemDetails1");
	}

	@RequestMapping(value = "/extraction/SystemDetails2", method = RequestMethod.POST)
	public ModelAndView SystemDetails2(@Valid @ModelAttribute("src_val") String src_val, @ModelAttribute("x") String x, ModelMap model) throws UnsupportedOperationException, Exception {
//		System.out.println(x);
		String resp = es.invokeRest(x, "onboardSystem");
		model.addAttribute("successString", resp.toString());
		model.addAttribute("src_val", src_val);
		ArrayList<ConnectionMaster> conn_val = es.getConnections(src_val);
		model.addAttribute("conn_val", conn_val);
		/*ArrayList<String> buckets = DBUtils.getBuckets();
		model.addAttribute("buckets", buckets);*/
		ArrayList<CountryMaster> countries = es.getCountries();
		model.addAttribute("countries", countries);
		ArrayList<ReservoirMaster> reservoir = es.getReservoirs();
		model.addAttribute("reservoir", reservoir);
		return new ModelAndView("extraction/SystemDetails");
	}

	@RequestMapping(value = "/extraction/DataHome", method = RequestMethod.GET)
	public ModelAndView DataHome() {
		return new ModelAndView("extraction/DataHome");
	}

	@RequestMapping(value = "/extraction/DataDetails", method = RequestMethod.POST)
	public ModelAndView DataDetails(@Valid @ModelAttribute("src_val") String src_val, ModelMap model) throws IOException {
		model.addAttribute("src_val", src_val);
		ArrayList<SourceSystemMaster> src_sys_val = es.getSources(src_val);
		model.addAttribute("src_sys_val", src_sys_val);
		return new ModelAndView("extraction/DataDetails");
	}

	@RequestMapping(value = "/extraction/DataDetails1", method = RequestMethod.POST)
	public ModelAndView DataDetails1(@Valid @ModelAttribute("src_sys_id") int src_sys_id, @ModelAttribute("src_val") String src_val, ModelMap model)
			throws UnsupportedOperationException, Exception {
		ConnectionMaster conn_val = es.getConnections1(src_val,src_sys_id);
		model.addAttribute("conn_val", conn_val);
		String ext_type=es.getExtType(src_sys_id);
		model.addAttribute("ext_type", ext_type);
		ArrayList<String> tables = es.getTables(src_val, conn_val.getConnection_id());
		model.addAttribute("tables", tables);
		return new ModelAndView("extraction/DataDetails1");
	}
	
	@RequestMapping(value = "/extraction/DataDetails2", method = RequestMethod.POST)
	public ModelAndView DataDetails2(@Valid @ModelAttribute("id") String id, @ModelAttribute("src_val") String src_val, @ModelAttribute("table_name") String table_name,
			@ModelAttribute("connection_id") int connection_id, ModelMap model) throws UnsupportedOperationException, Exception {
		ArrayList<String> fields = es.getFields(id, src_val, table_name, connection_id);
		model.addAttribute("fields", fields);
		model.addAttribute("id", id);
		return new ModelAndView("extraction/DataDetails2");
	}
	
	@RequestMapping(value = "/extraction/DataDetails3", method = RequestMethod.POST)
	public ModelAndView DataDetails3(@Valid @ModelAttribute("src_val") String src_val, @ModelAttribute("x") String x, ModelMap model) throws UnsupportedOperationException, Exception {
//		System.out.println(x);
		String resp = es.invokeRest(x, "addTableInfo");
		model.addAttribute("successString", resp.toString());
		model.addAttribute("src_val", src_val);
		ArrayList<SourceSystemMaster> src_sys_val = es.getSources(src_val);
		model.addAttribute("src_sys_val", src_sys_val);
		return new ModelAndView("extraction/DataDetails");
	}

	@RequestMapping(value = "/extraction/ExtractHome", method = RequestMethod.GET)
	public ModelAndView ExtractHome() {
		return new ModelAndView("extraction/ExtractHome");
	}

	@RequestMapping(value = "/extraction/ExtractData", method = RequestMethod.POST)
	public ModelAndView ExtractData(@Valid @ModelAttribute("src_val") String src_val, ModelMap model) throws IOException {
		model.addAttribute("src_val", src_val);
		ArrayList<SourceSystemMaster> src_sys_val = es.getSources(src_val);
		model.addAttribute("src_sys_val", src_sys_val);
		return new ModelAndView("extraction/ExtractData");
	}

	@RequestMapping(value = "/extraction/ExtractData1", method = RequestMethod.POST)
	public ModelAndView ExtractData1(@Valid @ModelAttribute("src_unique_name") String src_unique_name, @ModelAttribute("src_val") String src_val, ModelMap model)
			throws UnsupportedOperationException, Exception {
		String ext_type=es.getExtType1(src_unique_name);
		model.addAttribute("ext_type", ext_type);
		return new ModelAndView("extraction/ExtractData1");
	}

	@RequestMapping(value = "/extraction/ExtractData2", method = RequestMethod.POST)
	public ModelAndView ExtractData2(@Valid @ModelAttribute("src_val") String src_val, @ModelAttribute("x") String x, @ModelAttribute("ext_type") String ext_type, ModelMap model) throws UnsupportedOperationException, Exception {
		String resp=null;
//		System.out.println(x);
		if(ext_type.equalsIgnoreCase("Batch"))
		{
			resp = es.invokeRest(x, "createDag");
		}
		else
		{
			resp = es.invokeRest(x, "extractData");
		}
		model.addAttribute("successString", resp.toString());
		model.addAttribute("src_val", src_val);
		ArrayList<SourceSystemMaster> src_sys_val = es.getSources(src_val);
		model.addAttribute("src_sys_val", src_sys_val);
		return new ModelAndView("extraction/ExtractData");
	}
}
