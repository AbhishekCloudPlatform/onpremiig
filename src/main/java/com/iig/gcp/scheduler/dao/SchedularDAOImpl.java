package com.iig.gcp.scheduler.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Component;

import com.iig.gcp.scheduler.dto.ArchiveJobsDTO;
import com.iig.gcp.scheduler.dto.DailyJobsDTO;
import com.iig.gcp.scheduler.dto.MasterJobsDTO;
import com.iig.gcp.utils.ConnectionUtils;

@Component
public class SchedularDAOImpl implements SchedularDAO {

	
	//Master Table
	@Override
	public ArrayList<String> getFeedFromMaster() throws Exception {
		ArrayList<String> arrFeedId = new ArrayList<String>();
		Connection conn=ConnectionUtils.getConnection();			
		String query="Select distinct batch_id from iigs_ui_master_job_detail order by batch_id;";
		PreparedStatement pstm = conn.prepareStatement(query);
		ResultSet rs =pstm.executeQuery();
		while (rs.next()) {
			arrFeedId.add(rs.getString(1));
		}
		ConnectionUtils.closeQuietly(conn);
		return arrFeedId;
	}

	
	/**
	 * 
	 */
	@Override
	public List<MasterJobsDTO> allLoadJobs() throws Exception {
		List<MasterJobsDTO> scheduledJobs = new ArrayList<MasterJobsDTO>();
		Connection conn=ConnectionUtils.getConnection();			
		String query="Select job_id,job_name,batch_id,case when weekly_flag='Y' then concat('Weekly on ',week_run_day) when daily_flag='Y' then concat('Daily at ',substr(job_schedule_time,1,5)) when monthly_flag='Y' then concat('Monthly on ',month_run_day ) when yearly_flag='Y' then concat('Yearly on ',month_run_val ,' month') end as consolidated_Schedule,case when weekly_flag='Y' then 'Weekly' when daily_flag='Y' then 'Daily' when monthly_flag='Y' then 'Monthly' when yearly_flag='Y' then 'Yearly' end as Schedule from iigs_ui_master_job_detail order by batch_id, job_id;";
		PreparedStatement pstm = conn.prepareStatement(query);
		ResultSet rs =pstm.executeQuery();
		MasterJobsDTO dto=null;
		while (rs.next()) {
			dto=new MasterJobsDTO();
			dto.setJob_id(rs.getString(1));
			dto.setJob_name(rs.getString(2));
			dto.setBatch_id(rs.getString(3));
			dto.setConsolidatedSchedule(rs.getString(4));
			scheduledJobs.add(dto);
		}
		ConnectionUtils.closeQuietly(conn);
		return scheduledJobs;
	}
	
	/**
	 * 
	 */
	@Override
	public List<MasterJobsDTO> typAndBatchLoadJobs(String frequency, String  batchId) throws Exception {
		List<MasterJobsDTO> scheduledJobs = new ArrayList<MasterJobsDTO>();
		Connection conn=ConnectionUtils.getConnection();	
		if(batchId.equals("ALL") && frequency.equals("ALL")){
			batchId="%";
			frequency="%";
		}else if(batchId.equals("ALL") && !frequency.equals("ALL")) {
			batchId="%";
		}else if(!batchId.equals("ALL") && frequency.equals("ALL")) {
			frequency="%";
		}
		
		String query="Select job_id,job_name,batch_id,case when weekly_flag='Y' then concat('Weekly on ',week_run_day) when daily_flag='Y' then concat('Daily at ',substr(job_schedule_time,1,5)) when monthly_flag='Y' then concat('Monthly on ',month_run_day ) when yearly_flag='Y' then concat('Yearly on ',month_run_val ,' month') end as consolidated_Schedule,case when weekly_flag='Y' then 'Weekly' when daily_flag='Y' then 'Daily' when monthly_flag='Y' then 'Monthly' when yearly_flag='Y' then 'Yearly' end as Schedule from iigs_ui_master_job_detail where case when weekly_flag='Y' then 'Weekly' when daily_flag='Y' then 'Daily' when monthly_flag='Y' then 'Monthly' when yearly_flag='Y' then 'Yearly' end like ? and batch_id like ? order by batch_id, job_id;";
		PreparedStatement pstm = conn.prepareStatement(query);
		pstm.setString(1, frequency);
		pstm.setString(2, batchId);
		ResultSet rs =pstm.executeQuery();
		MasterJobsDTO dto=null;
		while (rs.next()) {
			dto=new MasterJobsDTO();
			dto.setJob_id(rs.getString(1));
			dto.setJob_name(rs.getString(2));
			dto.setBatch_id(rs.getString(3));
			dto.setConsolidatedSchedule(rs.getString(4));
			scheduledJobs.add(dto);
		}
		ConnectionUtils.closeQuietly(conn);
		return scheduledJobs;
	}


	//Archive Table
	@Override
	public ArrayList<String> getFeedIdList() throws Exception {
		
		ArrayList<String> arrFeedId= new ArrayList<String>();
		Connection conn=ConnectionUtils.getConnection();	
		String query="select distinct batch_id from iigs_archive_job_detail order by batch_id";
		PreparedStatement pstm = conn.prepareStatement(query);
		ResultSet rs =pstm.executeQuery();

		while (rs.next()) {
			arrFeedId.add(rs.getString(1));
		}
		ConnectionUtils.closeQuietly(conn);
		return arrFeedId;
	}

	@Override
	public ArrayList<ArchiveJobsDTO> getListOfArchievJobs(@Valid String feed_id) throws Exception {
		ArrayList<ArchiveJobsDTO> arrArchiveJobsDTO =new ArrayList<ArchiveJobsDTO>();
		Connection conn=ConnectionUtils.getConnection();	
		String query="select distinct job_id from iigs_archive_job_detail where batch_id=? order by job_id";
		PreparedStatement pstm = conn.prepareStatement(query);
		pstm.setString(1,feed_id);
		ResultSet rs =pstm.executeQuery();
		ArchiveJobsDTO archiveJobsDTO = null;
		while (rs.next()) {
			archiveJobsDTO = new ArchiveJobsDTO();
			archiveJobsDTO.setJob_id(rs.getString(1));			
			arrArchiveJobsDTO.add(archiveJobsDTO);
		}
		
		return arrArchiveJobsDTO;
	}

	@Override
	public ArrayList<ArchiveJobsDTO> getChartDetails(@Valid String job_id) throws Exception {
		ArrayList<ArchiveJobsDTO> arrArchiveJobsDTO =new ArrayList<ArchiveJobsDTO>();
		Connection conn=ConnectionUtils.getConnection();	
		String query="select job_id, batch_id, status, start_time, end_time, batch_date, timediff(end_time,start_time) as duration from iigs_archive_job_detail where job_id=? order by batch_id, job_id";
		PreparedStatement pstm = conn.prepareStatement(query);
		pstm.setString(1,job_id);
		ResultSet rs =pstm.executeQuery();
		ArchiveJobsDTO archiveJobsDTO = null;
		while (rs.next()) {
			archiveJobsDTO = new ArchiveJobsDTO();
			archiveJobsDTO.setJob_id(rs.getString(1));
			archiveJobsDTO.setBatch_id(rs.getString(2));
			archiveJobsDTO.setStatus(rs.getString(3));
			archiveJobsDTO.setStart_time(rs.getString(4));
			archiveJobsDTO.setEnd_time(rs.getString(5));
			archiveJobsDTO.setBatch_date(rs.getString(6));
			archiveJobsDTO.setDuration(rs.getString(7));
			arrArchiveJobsDTO.add(archiveJobsDTO);
		}
		
		return arrArchiveJobsDTO;
	}

	public List<ArchiveJobsDTO> getRunStats(@Valid String job_id, @Valid String feed_id) throws Exception{
		List<ArchiveJobsDTO> archiveJobs= new ArrayList<ArchiveJobsDTO>();
		Connection conn=ConnectionUtils.getConnection();		
		String query="select job_id, batch_id, job_name, start_time, end_time, batch_date, timediff(end_time,start_time) as duration from iigs_archive_job_detail where batch_id=? and job_id=? order by batch_date";
		PreparedStatement pstm = conn.prepareStatement(query);
		pstm.setString(1,feed_id);
		pstm.setString(2,job_id);
		ResultSet rs =pstm.executeQuery();
		ArchiveJobsDTO dto=null;
		while (rs.next()) {
			dto=new ArchiveJobsDTO();
			dto.setJob_id(rs.getString(1));
			dto.setBatch_id(rs.getString(2));
			dto.setJob_name(rs.getString(3));
			dto.setStart_time(rs.getString(4));
			dto.setEnd_time(rs.getString(5));
			dto.setBatch_date(rs.getString(6));
			dto.setDuration(rs.getString(7));
			archiveJobs.add(dto);
		}
		ConnectionUtils.closeQuietly(conn);
		return archiveJobs;		
	}
	
	//Current Table
	@Override
	public HashMap<String, ArrayList<String>> allCurrentJobsGroupByFeedId() throws Exception {
		HashMap<String,ArrayList<String>> hsMap = new HashMap<String,ArrayList<String>>();
		ArrayList<String> arrKey = new ArrayList<String>();
		ArrayList<String> arrValue = new ArrayList<String>();
		Connection conn=ConnectionUtils.getConnection();	
		
		String query="select count(job_id), batch_id from iigs_current_job_detail group by batch_id";
		//int all = 0, completed=0, running=0, failed=0, waiting =0, scheduled=0;
		PreparedStatement pstm = conn.prepareStatement(query);
		ResultSet rs =pstm.executeQuery();
		while (rs.next()) {
			System.out.println("in DB K"+String.valueOf(rs.getInt(1)));
				arrKey.add(String.valueOf(rs.getInt(1)));
				arrValue.add(rs.getString(2));
		}
		hsMap.put("arrkey",arrKey);
		hsMap.put("arrValue",arrValue);
		return hsMap;
	}


	@Override
	public List<DailyJobsDTO> allCurrentJobs() throws Exception {
		List<DailyJobsDTO> scheduledJobs = new ArrayList<DailyJobsDTO>();
		Connection conn=ConnectionUtils.getConnection();			
		String query="Select job_id,job_name,batch_id, CAST(job_schedule_time as char), case when status='C' then 'Completed' when status='F' then 'Failed' when status='R' then 'Running' when status='W' then 'Waiting' else 'To Run' end as status from iigs_current_job_detail order by job_schedule_time ;";
		PreparedStatement pstm = conn.prepareStatement(query);
		ResultSet rs =pstm.executeQuery();
		DailyJobsDTO dto=null;
		while (rs.next()) {
			dto=new DailyJobsDTO();
			dto.setJob_id(rs.getString(1));
			dto.setJob_name(rs.getString(2));
			dto.setBatch_id(rs.getString(3));
			dto.setJob_schedule_time(rs.getString(4));
			dto.setStatus(rs.getString(5));
			scheduledJobs.add(dto);
		}
		ConnectionUtils.closeQuietly(conn);
		return scheduledJobs;
	}


	@Override
	public ArrayList<String> getFeedFromCurrent() throws Exception {
		ArrayList<String> arrFeedId = new ArrayList<String>();
		Connection conn=ConnectionUtils.getConnection();			
		String query="Select distinct batch_id from iigs_current_job_detail order by batch_id;";
		PreparedStatement pstm = conn.prepareStatement(query);
		ResultSet rs =pstm.executeQuery();
		while (rs.next()) {
			arrFeedId.add(rs.getString(1));
		}
		ConnectionUtils.closeQuietly(conn);
		return arrFeedId;
	}


	@Override
	public List<DailyJobsDTO> filterCurrentJobs(String status, String feedId) throws Exception {
		List<DailyJobsDTO> scheduledJobs = new ArrayList<DailyJobsDTO>();
		Connection conn=ConnectionUtils.getConnection();	
		if(status.equals("ALL") && feedId.equals("ALL")){
			status="%";
			feedId="%";
		}else if(status.equals("ALL") && !feedId.equals("ALL")) {
			status="%";
		}else if(!status.equals("ALL") && feedId.equals("ALL")) {
			feedId="%";
		}
		//Need to change the query
		String query="Select job_id,job_name,batch_id, cast(job_schedule_time as char), case when status='C' then 'Completed' when status='F' then 'Failed' when status='R' then 'Running' when status='W' then 'Waiting' else 'To Run' end as status from iigs_current_job_detail where case when status='' then 'T' else status end like ? and batch_id like ? order by job_schedule_time desc;";
		PreparedStatement pstm = conn.prepareStatement(query);
		pstm.setString(1, status);
		pstm.setString(2, feedId);
		ResultSet rs =pstm.executeQuery();
		DailyJobsDTO dto=null;
		while (rs.next()) {
			dto=new DailyJobsDTO();
			dto.setJob_id(rs.getString(1));
			dto.setJob_name(rs.getString(2));
			dto.setBatch_id(rs.getString(3));
			dto.setStatus(rs.getString(5));
			dto.setJob_schedule_time(rs.getString(4));
			scheduledJobs.add(dto);
		}
		ConnectionUtils.closeQuietly(conn);
		return scheduledJobs;
	}
	
}
