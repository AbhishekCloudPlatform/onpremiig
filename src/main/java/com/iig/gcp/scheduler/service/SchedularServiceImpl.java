package com.iig.gcp.scheduler.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iig.gcp.scheduler.dao.SchedularDAO;
import com.iig.gcp.scheduler.dto.ArchiveJobsDTO;
import com.iig.gcp.scheduler.dto.DailyJobsDTO;
import com.iig.gcp.scheduler.dto.MasterJobsDTO;

@Service
public class SchedularServiceImpl implements SchedularService{
	
	@Autowired
	SchedularDAO schedularDAO;
	
	//Master Table
	@Override
	public ArrayList<String> getFeedFromMaster() throws Exception {
		// TODO Auto-generated method stub
		return schedularDAO.getFeedFromMaster();
	}
	
	/**
	 * 
	 */
	@Override
	public List<MasterJobsDTO> allLoadJobs() throws Exception {
		return schedularDAO.allLoadJobs();
	}

	
	@Override
	public List<MasterJobsDTO> typeLoadJobs(String frequency, String batchId) throws Exception {
		// TODO Auto-generated method stub
		return schedularDAO.typAndBatchLoadJobs(frequency, batchId);
	}
	
	
	//Archive Table;
	@Override
	public ArrayList<String> getFeedIdList() throws Exception {
		// TODO Auto-generated method stub
		return schedularDAO.getFeedIdList();
	}

	@Override
	public ArrayList<ArchiveJobsDTO> getListOfArchievJobs(@Valid String feed_id) throws Exception {
		// TODO Auto-generated method stub
		return schedularDAO.getListOfArchievJobs(feed_id);
	}

	@Override
	public ArrayList<ArchiveJobsDTO> getChartDetails(@Valid String job_id) throws Exception {
		// TODO Auto-generated method stub
		return schedularDAO.getChartDetails(job_id);
	}

	@Override
	public List<ArchiveJobsDTO> getRunStats(@Valid String job_id,@Valid String feed_id) throws Exception {
		// TODO Auto-generated method stub
		return schedularDAO.getRunStats(job_id,feed_id);
	}
	
	//Current Table;
	@Override
	public List<DailyJobsDTO> allCurrentJobs() throws Exception {
		// TODO Auto-generated method stub
		return schedularDAO.allCurrentJobs();
	}

	@Override
	public ArrayList<String> getFeedFromCurrent() throws Exception {
		// TODO Auto-generated method stub
		return schedularDAO.getFeedFromCurrent();
	}

	@Override
	public List<DailyJobsDTO> filterCurrentJobs(String status, String feedId) throws Exception {
		// TODO Auto-generated method stub
		return schedularDAO.filterCurrentJobs(status, feedId);
	}

	@Override
	public HashMap<String, ArrayList<String>> allCurrentJobsGroupByFeedId() throws Exception {
		// TODO Auto-generated method stub
		return schedularDAO.allCurrentJobsGroupByFeedId();
	}

	@Override
	public MasterJobsDTO orderJobFromMaster(String feedId, String jobId) throws ClassNotFoundException, SQLException, ParseException {
		return schedularDAO.orderJobFromMaster(feedId, jobId);
		
	}

	@Override
	public String moveJobFromMasterToCurrentJob(MasterJobsDTO masterJobDTO) throws ClassNotFoundException, SQLException {
		return schedularDAO.moveJobFromMasterToCurrentJob(masterJobDTO);
		
	}
	
	@Override
	public String deleteJobFromMaster(String feedId, String jobId) throws Exception {
		return schedularDAO.deleteJobFromMaster(feedId, jobId);
		
	}

	@Override
	public String runScheduleJob(@Valid String feedId, String jobId, String batchDate) throws Exception {
		return schedularDAO.runScheduleJob(feedId, jobId, batchDate);
	}

	@Override
	public String stopScheduleJob(@Valid String feedId, String jobId, String batchDate) throws Exception{
		return schedularDAO.stopScheduleJob(feedId, jobId, batchDate);
	}

	@Override
	public String suspendJobFromMaster(String feedId, String jobId, String scheduleInfo) throws ClassNotFoundException, SQLException {
		return schedularDAO.suspendJobFromMaster(feedId,jobId,scheduleInfo);
	}	



	
	


	


}
