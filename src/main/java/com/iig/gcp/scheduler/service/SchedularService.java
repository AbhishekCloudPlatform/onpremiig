package com.iig.gcp.scheduler.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import com.iig.gcp.scheduler.dto.ArchiveJobsDTO;
import com.iig.gcp.scheduler.dto.DailyJobsDTO;
import com.iig.gcp.scheduler.dto.MasterJobsDTO;

public interface SchedularService {

	//Current Table
	List<DailyJobsDTO> allCurrentJobs() throws Exception;
	ArrayList<String> getFeedFromCurrent() throws Exception;
	List<DailyJobsDTO> filterCurrentJobs(String status, String feedId) throws Exception;
	HashMap<String, ArrayList<String>> allCurrentJobsGroupByFeedId() throws Exception;
	String runScheduleJob(@Valid String feedId, String jobId, String batchDate) throws Exception;
	String stopScheduleJob(@Valid String feedId, String jobId, String batchDate) throws Exception;

	
	//Archive table
	ArrayList<String> getFeedIdList() throws Exception;
	ArrayList<ArchiveJobsDTO> getListOfArchievJobs(@Valid String feed_id) throws Exception;
	ArrayList<ArchiveJobsDTO> getChartDetails(@Valid String job_id) throws Exception;
	List<ArchiveJobsDTO> getRunStats(@Valid String job_id, @Valid String feed_id) throws Exception;
	
	//Master Table
	ArrayList<String> getFeedFromMaster()throws Exception;
	List<MasterJobsDTO> allLoadJobs() throws Exception;
	List<MasterJobsDTO> typeLoadJobs(String frequency, String batchId) throws Exception;
	MasterJobsDTO orderJobFromMaster(String feedId,String jobId) throws ClassNotFoundException, SQLException, ParseException;
	String moveJobFromMasterToCurrentJob(MasterJobsDTO masterJobDTO) throws ClassNotFoundException, SQLException;
	String deleteJobFromMaster(String feedId,String jobId) throws Exception;
	String suspendJobFromMaster(String feedId, String jobId, String scheduleInfo) throws ClassNotFoundException, SQLException;


	
	
		
		

}
