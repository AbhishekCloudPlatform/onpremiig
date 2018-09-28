package com.iig.gcp.scheduler.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.iig.gcp.scheduler.dto.ArchiveJobsDTO;
import com.iig.gcp.scheduler.dto.DailyJobsDTO;
import com.iig.gcp.scheduler.dto.MasterJobsDTO;
import com.iig.gcp.utils.ConnectionUtils;

@Component
public class SchedularDAOImpl implements SchedularDAO {

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	DateFormat dateFormat2 = new SimpleDateFormat("hh:mm:ss");
	Date date = new Date();

	private static String SPACE = " ";
	private static String COMMA = ",";
	private static String SEMICOLON = ";";
	private static String QUOTE = "\'";
	private static String DATABASE_NAME = "iigs_scheduler_db";
	private static String FEED_MASTER_TABLE = "iigs_ui_master_job_detail";
	private static String FEED_CURRENT_TABLE = "iigs_current_job_detail";

	// Master Table
	@Override
	public ArrayList<String> getFeedFromMaster() throws Exception {
		ArrayList<String> arrFeedId = new ArrayList<String>();
		Connection conn = ConnectionUtils.getConnection();
		String query = "Select distinct batch_id from iigs_ui_master_job_detail order by batch_id;";
		PreparedStatement pstm = conn.prepareStatement(query);
		ResultSet rs = pstm.executeQuery();
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
		Connection conn = ConnectionUtils.getConnection();
		String query = "Select job_id,job_name,batch_id,case when weekly_flag='Y' then concat('Weekly on ',week_run_day) when daily_flag='Y' then concat('Daily at ',substr(job_schedule_time,1,2)) when monthly_flag='Y' then concat('Monthly on ',month_run_day ) when yearly_flag='Y' then concat('Yearly on ',month_run_val ,' month') end as consolidated_Schedule,case when weekly_flag='Y' then 'Weekly' when daily_flag='Y' then 'Daily' when monthly_flag='Y' then 'Monthly' when yearly_flag='Y' then 'Yearly' end as Schedule from iigs_ui_master_job_detail order by batch_id, job_id;";
		PreparedStatement pstm = conn.prepareStatement(query);
		ResultSet rs = pstm.executeQuery();
		MasterJobsDTO dto = null;
		while (rs.next()) {
			dto = new MasterJobsDTO();
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
	public List<MasterJobsDTO> typAndBatchLoadJobs(String frequency, String batchId) throws Exception {
		List<MasterJobsDTO> scheduledJobs = new ArrayList<MasterJobsDTO>();
		Connection conn = ConnectionUtils.getConnection();
		if (batchId.equals("ALL") && frequency.equals("ALL")) {
			batchId = "%";
			frequency = "%";
		} else if (batchId.equals("ALL") && !frequency.equals("ALL")) {
			batchId = "%";
		} else if (!batchId.equals("ALL") && frequency.equals("ALL")) {
			frequency = "%";
		}

		String query = "Select job_id,job_name,batch_id,case when weekly_flag='Y' then concat('Weekly on ',week_run_day) when daily_flag='Y' then concat('Daily at ',substr(job_schedule_time,1,2)) when monthly_flag='Y' then concat('Monthly on ',month_run_day ) when yearly_flag='Y' then concat('Yearly on ',month_run_val ,' month') end as consolidated_Schedule,case when weekly_flag='Y' then 'Weekly' when daily_flag='Y' then 'Daily' when monthly_flag='Y' then 'Monthly' when yearly_flag='Y' then 'Yearly' end as Schedule from iigs_ui_master_job_detail where case when weekly_flag='Y' then 'Weekly' when daily_flag='Y' then 'Daily' when monthly_flag='Y' then 'Monthly' when yearly_flag='Y' then 'Yearly' end like ? and batch_id like ? order by batch_id, job_id;";
		PreparedStatement pstm = conn.prepareStatement(query);
		pstm.setString(1, frequency);
		pstm.setString(2, batchId);
		ResultSet rs = pstm.executeQuery();
		MasterJobsDTO dto = null;
		while (rs.next()) {
			dto = new MasterJobsDTO();
			dto.setJob_id(rs.getString(1));
			dto.setJob_name(rs.getString(2));
			dto.setBatch_id(rs.getString(3));
			dto.setConsolidatedSchedule(rs.getString(4));
			scheduledJobs.add(dto);
		}
		ConnectionUtils.closeQuietly(conn);
		return scheduledJobs;
	}

	// Archive Table
	@Override
	public ArrayList<String> getFeedIdList() throws Exception {

		ArrayList<String> arrFeedId = new ArrayList<String>();
		Connection conn = ConnectionUtils.getConnection();
		String query = "select distinct batch_id from iigs_archive_job_detail order by batch_id";
		PreparedStatement pstm = conn.prepareStatement(query);
		ResultSet rs = pstm.executeQuery();

		while (rs.next()) {
			arrFeedId.add(rs.getString(1));
		}
		ConnectionUtils.closeQuietly(conn);
		return arrFeedId;
	}

	@Override
	public ArrayList<ArchiveJobsDTO> getListOfArchievJobs(@Valid String feed_id) throws Exception {
		ArrayList<ArchiveJobsDTO> arrArchiveJobsDTO = new ArrayList<ArchiveJobsDTO>();
		Connection conn = ConnectionUtils.getConnection();
		String query = "select job_id from iigs_archive_job_detail where batch_id=? order by job_id";
		PreparedStatement pstm = conn.prepareStatement(query);
		pstm.setString(1, feed_id);
		ResultSet rs = pstm.executeQuery();
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
		ArrayList<ArchiveJobsDTO> arrArchiveJobsDTO = new ArrayList<ArchiveJobsDTO>();
		Connection conn = ConnectionUtils.getConnection();
		String query = "select job_id, batch_id, status, start_time, end_time, batch_date, timediff(end_time,start_time) as duration from iigs_archive_job_detail where job_id=? order by batch_id, job_id";
		PreparedStatement pstm = conn.prepareStatement(query);
		pstm.setString(1, job_id);
		ResultSet rs = pstm.executeQuery();
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

	public List<ArchiveJobsDTO> getRunStats(@Valid String job_id, @Valid String feed_id) throws Exception {
		List<ArchiveJobsDTO> archiveJobs = new ArrayList<ArchiveJobsDTO>();
		Connection conn = ConnectionUtils.getConnection();
		String query = "select job_id, batch_id, job_name, start_time, end_time, batch_date, timediff(end_time,start_time) as duration from iigs_archive_job_detail where batch_id=? and job_id=? order by batch_date";
		PreparedStatement pstm = conn.prepareStatement(query);
		pstm.setString(1, feed_id);
		pstm.setString(2, job_id);
		ResultSet rs = pstm.executeQuery();
		ArchiveJobsDTO dto = null;
		while (rs.next()) {
			dto = new ArchiveJobsDTO();
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

	// Current Table
	@Override
	public HashMap<String, ArrayList<String>> allCurrentJobsGroupByFeedId() throws Exception {
		HashMap<String, ArrayList<String>> hsMap = new HashMap<String, ArrayList<String>>();
		ArrayList<String> arrKey = new ArrayList<String>();
		ArrayList<String> arrValue = new ArrayList<String>();
		Connection conn = ConnectionUtils.getConnection();

		String query = "select count(job_id), batch_id from iigs_current_job_detail group by batch_id";
		// int all = 0, completed=0, running=0, failed=0, waiting =0, scheduled=0;
		PreparedStatement pstm = conn.prepareStatement(query);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			System.out.println("in DB K" + String.valueOf(rs.getInt(1)));
			arrKey.add(String.valueOf(rs.getInt(1)));
			arrValue.add(rs.getString(2));
		}
		hsMap.put("arrkey", arrKey);
		hsMap.put("arrValue", arrValue);
		return hsMap;
	}

	@Override
	public List<DailyJobsDTO> allCurrentJobs() throws Exception {
		List<DailyJobsDTO> scheduledJobs = new ArrayList<DailyJobsDTO>();
		Connection conn = ConnectionUtils.getConnection();
		String query = "Select job_id,job_name,batch_id, CAST(job_schedule_time as char), case when status='C' then 'Completed' when status='F' then 'Failed' when status='R' then 'Running' when status='W' then 'Waiting' else 'To Run' end as status from iigs_current_job_detail order by job_schedule_time ;";
		PreparedStatement pstm = conn.prepareStatement(query);
		ResultSet rs = pstm.executeQuery();
		DailyJobsDTO dto = null;
		while (rs.next()) {
			dto = new DailyJobsDTO();
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
		Connection conn = ConnectionUtils.getConnection();
		String query = "Select distinct batch_id from iigs_current_job_detail order by batch_id;";
		PreparedStatement pstm = conn.prepareStatement(query);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			arrFeedId.add(rs.getString(1));
		}
		ConnectionUtils.closeQuietly(conn);
		return arrFeedId;
	}

	@Override
	public List<DailyJobsDTO> filterCurrentJobs(String status, String feedId) throws Exception {
		List<DailyJobsDTO> scheduledJobs = new ArrayList<DailyJobsDTO>();
		Connection conn = ConnectionUtils.getConnection();
		if (status.equals("ALL") && feedId.equals("ALL")) {
			status = "%";
			feedId = "%";
		} else if (status.equals("ALL") && !feedId.equals("ALL")) {
			status = "%";
		} else if (!status.equals("ALL") && feedId.equals("ALL")) {
			feedId = "%";
		}
		// Need to change the query
		String query = "Select job_id,job_name,batch_id, cast(job_schedule_time as char), case when status='C' then 'Completed' when status='F' then 'Failed' when status='R' then 'Running' when status='W' then 'Waiting' else 'To Run' end as status from iigs_current_job_detail where case when status='' then 'T' else status end like ? and batch_id like ? order by job_schedule_time desc;";
		PreparedStatement pstm = conn.prepareStatement(query);
		pstm.setString(1, status);
		pstm.setString(2, feedId);
		ResultSet rs = pstm.executeQuery();
		DailyJobsDTO dto = null;
		while (rs.next()) {
			dto = new DailyJobsDTO();
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

	@Override
	public MasterJobsDTO orderJobFromMaster(String feedId, String jobId)
			throws ClassNotFoundException, SQLException, ParseException {
		MasterJobsDTO masterJobDTO = null;
		Connection conn = ConnectionUtils.getConnection();
		String query = "select project_id,feed_id,source_emid,target_emid,job_id,job_name,batch_id,pre_processing,post_processing,"
				+ "command,argument_1,argument_2,argument_3,argument_4,argument_5,"
				+ "daily_flag,monthly_flag,job_schedule_time,"
				+ "predessor_job_id_1,predessor_job_id_2,predessor_job_id_3,predessor_job_id_4,"
				+ "predessor_job_id_5,predessor_job_id_6,predessor_job_id_7,predessor_job_id_8,predessor_job_id_9,predessor_job_id_10,"
				+ "weekly_flag,week_run_day,month_run_day,month_run_val,is_dependent_job,command_type,yearly_flag,"
				+ "week_num_month from " + FEED_MASTER_TABLE + " where batch_id= ? and job_id=?;";
		PreparedStatement pstm = conn.prepareStatement(query);
		pstm.setString(1, feedId);
		pstm.setString(2, jobId);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			masterJobDTO = new MasterJobsDTO();

			masterJobDTO.setProject_id(rs.getString(1));
			masterJobDTO.setFeed_id(rs.getString(2));
			masterJobDTO.setSource_emid(rs.getString(3));
			masterJobDTO.setTarget_emid(rs.getString(4));
			// current_date
			masterJobDTO.setJob_id(rs.getString(5));
			masterJobDTO.setJob_name(rs.getString(6));
			masterJobDTO.setBatch_id(rs.getString(7));

			masterJobDTO.setPre_processing(rs.getString(8));
			masterJobDTO.setPost_processing(rs.getString(9));
			masterJobDTO.setCommand(rs.getString(10));
			masterJobDTO.setArgument_1(rs.getString(11));
			masterJobDTO.setArgument_2(rs.getString(12));
			masterJobDTO.setArgument_3(rs.getString(13));
			masterJobDTO.setArgument_4(rs.getString(14));
			masterJobDTO.setArgument_5(rs.getString(15));

			if (rs.getString(16) != null && !rs.getString(16).equals("")) {
				char dailyFlag = rs.getString(16).charAt(0);
				masterJobDTO.setDaily_flag(dailyFlag);
			} else {
				masterJobDTO.setDaily_flag(' ');
			}

			if (rs.getString(17) != null && !rs.getString(17).equals("")) {
				char monthlyFlag = rs.getString(17).charAt(0);
				masterJobDTO.setMonthly_flag(monthlyFlag);
			} else {
				masterJobDTO.setMonthly_flag(' ');
			}

			masterJobDTO.setJob_schedule_time(rs.getString(18));

			// status

			String predessor_job_id_1 = rs.getString(19);
			masterJobDTO.setPredessor_job_id_1(predessor_job_id_1);

			String predessor_job_id_2 = rs.getString(20);
			masterJobDTO.setPredessor_job_id_2(predessor_job_id_2);

			String predessor_job_id_3 = rs.getString(21);
			masterJobDTO.setPredessor_job_id_3(predessor_job_id_3);

			String predessor_job_id_4 = rs.getString(22);
			masterJobDTO.setPredessor_job_id_4(predessor_job_id_4);

			String predessor_job_id_5 = rs.getString(23);
			masterJobDTO.setPredessor_job_id_5(predessor_job_id_5);

			String predessor_job_id_6 = rs.getString(24);
			masterJobDTO.setPredessor_job_id_6(predessor_job_id_6);

			String predessor_job_id_7 = rs.getString(25);
			masterJobDTO.setPredessor_job_id_7(predessor_job_id_7);

			String predessor_job_id_8 = rs.getString(26);
			masterJobDTO.setPredessor_job_id_8(predessor_job_id_8);

			String predessor_job_id_9 = rs.getString(27);
			masterJobDTO.setPredessor_job_id_9(predessor_job_id_9);

			String predessor_job_id_10 = rs.getString(28);
			masterJobDTO.setPredessor_job_id_10(predessor_job_id_10);

			if (rs.getString(29) != null && !rs.getString(29).equals("")) {
				char weeklyFlag = rs.getString(29).charAt(0);
				masterJobDTO.setWeekly_flag(weeklyFlag);
			} else {
				masterJobDTO.setWeekly_flag(' ');
			}

			String weekRunDay = rs.getString(30);
			masterJobDTO.setWeek_run_day(weekRunDay);

			String monthlyRunDay = rs.getString(31);
			masterJobDTO.setMonth_run_day(monthlyRunDay);

			String monthlyRunVal = rs.getString(32);
			masterJobDTO.setMonth_run_val(monthlyRunVal);

			masterJobDTO.setIs_dependent_job(rs.getString(33));

			String commandType = rs.getString(34);
			masterJobDTO.setCommand_type(commandType);

			// timestamp

			if (rs.getString(35) != null && !rs.getString(35).equals("")) {
				char yearlyFlag = rs.getString(35).charAt(0);
				masterJobDTO.setYearly_flag(yearlyFlag);
			} else {
				masterJobDTO.setYearly_flag(' ');
			}

			if (rs.getString(36) != null && !rs.getString(36).equals("")) {
				String weekNumMonth = rs.getString(36);
				int i = Integer.parseInt(weekNumMonth);
				masterJobDTO.setWeek_num_month(i);
			}

		}
		ConnectionUtils.closeQuietly(conn);
		return masterJobDTO;
	}

	@Override
	public String moveJobFromMasterToCurrentJob(MasterJobsDTO masterJobDTO)
			throws ClassNotFoundException, SQLException {
		Connection conn = ConnectionUtils.getConnection();
		String message;
		try {
			String insertCurrentFeedLoggerQuery = "INSERT INTO" + SPACE + DATABASE_NAME + "." + FEED_CURRENT_TABLE
					+ SPACE
					+ "(project_id,feed_id,source_emid,target_emid,batch_date,job_id,job_name,batch_id,pre_processing,post_processing,"
					+ "command,argument_1,argument_2,argument_3,argument_4,argument_5,daily_flag,monthly_flag,job_schedule_time,status,"
					+ "predessor_job_id_1,predessor_job_id_2,predessor_job_id_3,predessor_job_id_4,predessor_job_id_5,"
					+ "predessor_job_id_6,predessor_job_id_7,predessor_job_id_8,predessor_job_id_9,predessor_job_id_10,"
					+ "weekly_flag,week_run_day,month_run_day,month_run_val,is_dependent_job,command_type,last_update_ts,"
					+ "yearly_flag,week_num_month )" + "VALUES" + "(" 
					+ QUOTE + masterJobDTO.getProject_id()+ QUOTE  + COMMA
					+ QUOTE + masterJobDTO.getFeed_id() +QUOTE + COMMA 
					+ QUOTE + masterJobDTO.getSource_emid()+QUOTE  + COMMA 
					+ QUOTE + masterJobDTO.getTarget_emid()+ QUOTE + COMMA 
					+ QUOTE + dateFormat.format(date)+ QUOTE + COMMA 
					+ QUOTE + masterJobDTO.getJob_id() + QUOTE+ COMMA 
					+ QUOTE + masterJobDTO.getJob_name() + QUOTE+ COMMA 
					+ QUOTE + masterJobDTO.getBatch_id()+ QUOTE + COMMA 
					+ QUOTE + masterJobDTO.getPre_processing()+ QUOTE + COMMA 
					+ QUOTE + masterJobDTO.getPost_processing()+ QUOTE + COMMA 
					+ QUOTE + masterJobDTO.getCommand()+ QUOTE + COMMA 
					+ QUOTE + masterJobDTO.getArgument_1()+ QUOTE + COMMA 
					+ QUOTE + masterJobDTO.getArgument_2()+ QUOTE + COMMA
					+ QUOTE + masterJobDTO.getArgument_3()+ QUOTE + COMMA 
					+ QUOTE + masterJobDTO.getArgument_4() + QUOTE+ COMMA
					+ QUOTE + masterJobDTO.getArgument_5() + QUOTE+ COMMA 
					+ QUOTE + masterJobDTO.getDaily_flag()+ QUOTE + COMMA 
					+ QUOTE + masterJobDTO.getMonthly_flag()+ QUOTE + COMMA 
					+ QUOTE + masterJobDTO.getJob_schedule_time()+ QUOTE + COMMA
					+ QUOTE + "" + QUOTE+ COMMA 
					+ QUOTE + masterJobDTO.getPredessor_job_id_1() +QUOTE+ COMMA 
					+ QUOTE + masterJobDTO.getPredessor_job_id_2() +QUOTE+ COMMA 
					+ QUOTE + masterJobDTO.getPredessor_job_id_3() +QUOTE+ COMMA 
					+ QUOTE + masterJobDTO.getPredessor_job_id_4()+ QUOTE + COMMA 
					+ QUOTE + masterJobDTO.getPredessor_job_id_5()+ QUOTE+ COMMA 
					+ QUOTE + masterJobDTO.getPredessor_job_id_6()+ QUOTE + COMMA 
					+ QUOTE + masterJobDTO.getPredessor_job_id_7()+ QUOTE + COMMA 
					+ QUOTE + masterJobDTO.getPredessor_job_id_8()+ QUOTE + COMMA 
					+ QUOTE + masterJobDTO.getPredessor_job_id_9()+ QUOTE + COMMA 
					+ QUOTE + masterJobDTO.getPredessor_job_id_10()+QUOTE + COMMA 
					+ QUOTE + masterJobDTO.getWeekly_flag() + QUOTE+ COMMA
					+ QUOTE + masterJobDTO.getWeek_run_day() +QUOTE+ COMMA 
					+ QUOTE + masterJobDTO.getMonth_run_day() + QUOTE+ COMMA
					+ QUOTE + masterJobDTO.getMonth_run_val()+ QUOTE  + COMMA
					+ QUOTE + masterJobDTO.getIs_dependent_job()+ QUOTE  + COMMA 
					+ QUOTE + masterJobDTO.getCommand_type() + QUOTE + COMMA 
					+ QUOTE + dateFormat1.format(date) + QUOTE+ COMMA
					+ QUOTE + masterJobDTO.getYearly_flag()+ QUOTE + COMMA 
					+ QUOTE + masterJobDTO.getWeek_num_month()+QUOTE + ")";

			Statement statement = conn.createStatement();
			statement.execute(insertCurrentFeedLoggerQuery);
			ConnectionUtils.closeQuietly(conn);
			message = "Success";
			return message;

		} catch (Exception e) {
			System.out.println("cant write");
			message = "Failure";
			return message;
		}
	}

	/*	*//**
			* 
			*/
	/*
	 * 
	 * @Override public List<MasterJobsDTO> batchIdLoadJobs(String strBatchId)
	 * throws Exception { List<MasterJobsDTO> scheduledJobs = new
	 * ArrayList<MasterJobsDTO>(); Connection conn=ConnectionUtils.getConnection();
	 * String
	 * query="Select job_id,job_name,batch_id,case when weekly_flag='Y' then concat('Weekly on ',week_run_day) when daily_flag='Y' then concat('Daily at ',substr(job_schedule_time,1,2)) when monthly_flag='Y' then concat('Monthly on ',month_run_day ) when yearly_flag='Y' then concat('Yearly on ',month_run_val ,' month') end as consolidated_Schedule,case when weekly_flag='Y' then 'Weekly' when daily_flag='Y' then 'Daily' when monthly_flag='Y' then 'Monthly' when yearly_flag='Y' then 'Yearly' end as Schedule from iigs_ui_master_job_detail where batch_id = ?;"
	 * ; PreparedStatement pstm = conn.prepareStatement(query); pstm.setString(1,
	 * strBatchId); ResultSet rs =pstm.executeQuery(); MasterJobsDTO dto=null; while
	 * (rs.next()) { dto=new MasterJobsDTO(); dto.setJob_id(rs.getString(1));
	 * dto.setJob_name(rs.getString(2)); dto.setBatch_id(rs.getString(3));
	 * dto.setConsolidatedSchedule(rs.getString(4)); scheduledJobs.add(dto); }
	 * ConnectionUtils.closeQuietly(conn); return scheduledJobs; }
	 * 
	 * 
	 * 
	 * 
	 *//**
		* 
		*//*
			 * @Override public List<MasterJobsDTO> batchidLoadJobs(String batchId,String
			 * frequency) throws Exception { List<MasterJobsDTO> scheduledJobs = new
			 * ArrayList<MasterJobsDTO>(); Connection conn=ConnectionUtils.getConnection();
			 * if(batchId.equals("ALL") && frequency.equals("ALL")) { batchId="%";
			 * frequency="%"; }else if(batchId.equals("ALL") && !frequency.equals("ALL")) {
			 * batchId="%"; }else if(!batchId.equals("ALL") && frequency.equals("ALL")) {
			 * frequency="%"; }
			 * 
			 * String
			 * query="Select job_id,job_name,batch_id,case when weekly_flag='Y' then concat('Weekly on ',week_run_day) when daily_flag='Y' then concat('Daily at ',substr(job_schedule_time,1,2)) when monthly_flag='Y' then concat('Monthly on ',month_run_day ) when yearly_flag='Y' then concat('Yearly on ',month_run_val ,' month') end as consolidated_Schedule,case when weekly_flag='Y' then 'Weekly' when daily_flag='Y' then 'Daily' when monthly_flag='Y' then 'Monthly' when yearly_flag='Y' then 'Yearly' end as Schedule from iigs_ui_master_job_detail where batch_id like ? and case when weekly_flag='Y' then 'Weekly' when daily_flag='Y' then 'Daily' when monthly_flag='Y' then 'Monthly' when yearly_flag='Y' then 'Yearly' end like ?"
			 * ; PreparedStatement pstm = conn.prepareStatement(query); pstm.setString(1,
			 * batchId); pstm.setString(2, frequency); ResultSet rs =pstm.executeQuery();
			 * MasterJobsDTO dto=null; while (rs.next()) { dto=new MasterJobsDTO();
			 * dto.setJob_id(rs.getString(1)); dto.setJob_name(rs.getString(2));
			 * dto.setBatch_id(rs.getString(3));
			 * dto.setConsolidatedSchedule(rs.getString(4)); scheduledJobs.add(dto); }
			 * ConnectionUtils.closeQuietly(conn); return scheduledJobs; }
			 */
	/**
	 * 
	 *//*
		 * @Override public HashMap<String,List<DailyJobsDTO>> allCurrentJobs() throws
		 * Exception { HashMap<String,List<DailyJobsDTO>> hsMap = new
		 * HashMap<String,List<DailyJobsDTO>>(); // TODO Auto-generated method stub
		 * List<DailyJobsDTO> arrCompleterJobs = new ArrayList<DailyJobsDTO>();
		 * List<DailyJobsDTO> arrFailedJobs = new ArrayList<DailyJobsDTO>();
		 * List<DailyJobsDTO> arrNotStarted = new ArrayList<DailyJobsDTO>();
		 * 
		 * Connection conn=ConnectionUtils.getConnection();
		 * 
		 * String
		 * query="select job_id, job_name, batch_id, batch_date, job_schedule_time, status, start_time, end_time from iigs_current_job_detail"
		 * ; PreparedStatement pstm = conn.prepareStatement(query); ResultSet rs
		 * =pstm.executeQuery(); DailyJobsDTO dto=null; while (rs.next()) { dto=new
		 * DailyJobsDTO(); dto.setJob_id(rs.getString(1));
		 * dto.setJob_name(rs.getString(2)); dto.setBatch_id(rs.getString(3));
		 * dto.setBatch_date(rs.getDate(4)); dto.setJob_schedule_time(rs.getDate(5));
		 * dto.setStatus(rs.getString(6)); dto.setStart_time(rs.getDate(7));
		 * dto.setEnd_time(rs.getDate(8)); if(rs.getString(6).equals("C")) {
		 * arrCompleterJobs.add(dto); } else if (rs.getString(6).equals("F")) {
		 * arrFailedJobs.add(dto); } else { arrNotStarted.add(dto); }
		 * 
		 * 
		 * } hsMap.put("completed", arrCompleterJobs); hsMap.put("failed",
		 * arrFailedJobs); hsMap.put("notstarted", arrNotStarted);
		 * ConnectionUtils.closeQuietly(conn); return hsMap; }
		 */

	/*
	 * private String job_id; private String job_name; private String batch_id;
	 * private String command; private char daily_flag; private String
	 * job_schedule_time; private char weekly_flag; private String week_run_day;
	 * private char monthly_flag; private String month_run_val; private String
	 * month_run_day; private char yearly_flag;
	 */

	/*
	 * @Override public List<MasterJobsDTO> loadJobs() throws Exception {
	 * List<MasterJobsDTO> scheduledJobs = new ArrayList<MasterJobsDTO>();
	 * Connection conn=ConnectionUtils.getConnection(); String
	 * query="select job_id,job_name,batch_id,command,daily_flag,job_schedule_time,weekly_flag,week_run_day,monthly_flag,month_run_val,month_run_day,yearly_flag from iigs_ui_master_job_detail"
	 * ; PreparedStatement pstm = conn.prepareStatement(query); ResultSet rs
	 * =pstm.executeQuery(); MasterJobsDTO dto; while (rs.next()) { dto = new
	 * MasterJobsDTO();
	 * 
	 * job_id=rs.getString(1); dto.setJob_id(job_id);
	 * 
	 * job_name=rs.getString(2); dto.setJob_name(job_name);
	 * 
	 * batch_id=rs.getString(3); dto.setBatch_id(batch_id);
	 * 
	 * command=rs.getString(4); dto.setCommand(command);
	 * 
	 * String dailyJobFlag=rs.getString(5); if(dailyJobFlag.equals("")){
	 * dto.setDaily_flag('N'); } else{ daily_flag=dailyJobFlag.charAt(0);
	 * dto.setDaily_flag(daily_flag); }
	 * 
	 * job_schedule_time = rs.getString(6);
	 * dto.setJob_schedule_time(job_schedule_time);
	 * 
	 * String weeklyJobFlag=rs.getString(7);
	 * 
	 * if(weeklyJobFlag.equals("")){ dto.setWeekly_flag('N');
	 * dto.setWeek_run_day("N"); } else { weekly_flag=weeklyJobFlag.charAt(0);
	 * dto.setWeekly_flag(weekly_flag);
	 * 
	 * week_run_day = rs.getString(8); dto.setWeek_run_day(week_run_day); }
	 * 
	 * String monthlyJobFlag=rs.getString(9); if(monthlyJobFlag.equals("")) {
	 * dto.setMonthly_flag('N'); dto.setMonth_run_day("N"); } else {
	 * monthly_flag=monthlyJobFlag.charAt(0); dto.setMonthly_flag(monthly_flag);
	 * 
	 * month_run_val = rs.getString(10); dto.setMonth_run_val(month_run_val);
	 * 
	 * month_run_day = rs.getString(11); dto.setMonth_run_day(month_run_day); }
	 * 
	 * String yearlyJobFlag=rs.getString(12); if(yearlyJobFlag.equals("")) {
	 * dto.setYearly_flag('N'); }else { yearly_flag=yearlyJobFlag.charAt(0);
	 * dto.setYearly_flag(yearly_flag);
	 * 
	 * month_run_val = rs.getString(10); dto.setMonth_run_val(month_run_val);
	 * 
	 * month_run_day = rs.getString(11); dto.setMonth_run_day(month_run_day); }
	 * 
	 * scheduledJobs.add(dto); } return scheduledJobs; }
	 * 
	 * @Override public List<MasterJobsDTO> loadJobsWithBatchIdAndJobType(String
	 * batch_id, String job_type) throws Exception { // TODO Auto-generated method
	 * stub return null; }
	 * 
	 * 
	 * @Override public List<MasterJobsDTO> getScheduledBatchJobs() throws Exception
	 * { // TODO Auto-generated method stub return null; }
	 * 
	 * @Override public List<MasterJobsDTO> getDailyScheduledBatchJobs() throws
	 * Exception { // TODO Auto-generated method stub return null; }
	 * 
	 * @Override public List<MasterJobsDTO>
	 * getScheduledBatchJobsWithBatchIdsAndJobType(String batch_id, String job_type)
	 * throws Exception { // TODO Auto-generated method stub return null; }
	 * 
	 * @Override public List<String> getBatchIdsFromMasterTable() throws Exception {
	 * // TODO Auto-generated method stub
	 * 
	 * return null; }
	 */

}
