package com.iig.gcp.scheduler.dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import com.iig.gcp.scheduler.dto.ArchiveJobsDTO;
import com.iig.gcp.scheduler.dto.DailyJobsDTO;
import com.iig.gcp.scheduler.dto.MasterJobsDTO;

public interface SchedularDAO {
	
	//Master Table
	 ArrayList<String> getFeedFromMaster() throws Exception;
	 List<MasterJobsDTO> allLoadJobs() throws Exception;
	 List<MasterJobsDTO> typAndBatchLoadJobs(String strFrequencyType, String strBatchId) throws Exception;
	 MasterJobsDTO orderJobFromMaster(String feedId, String jobId) throws ClassNotFoundException, SQLException, ParseException;
	 String deleteJobFromMaster(String feedId, String jobId) throws Exception;

	
	//Archive Table
	ArrayList<String> getFeedIdList() throws Exception;
	ArrayList<ArchiveJobsDTO> getListOfArchievJobs(@Valid String feed_id)throws Exception;
	ArrayList<ArchiveJobsDTO> getChartDetails(@Valid String job_id) throws Exception;
	List<ArchiveJobsDTO> getRunStats(@Valid String job_id, @Valid String feed_id) throws Exception;
	
	//Current Table
	List<DailyJobsDTO> allCurrentJobs() throws Exception;
	ArrayList<String> getFeedFromCurrent() throws Exception;
	List<DailyJobsDTO> filterCurrentJobs(String status, String feedId) throws Exception;
	HashMap<String, ArrayList<String>> allCurrentJobsGroupByFeedId() throws Exception;
	String moveJobFromMasterToCurrentJob(MasterJobsDTO masterJobDTO) throws ClassNotFoundException, SQLException;
	String runScheduleJob(@Valid String feedId, String jobId, String batchDate) throws Exception;
	String stopScheduleJob(@Valid String feedId, String jobId, String batchDate) throws Exception;
	String suspendJobFromMaster(String feedId, String jobId) throws ClassNotFoundException, SQLException;
	String unSuspendJobFromMaster(@Valid String feedId, String jobId);
}
