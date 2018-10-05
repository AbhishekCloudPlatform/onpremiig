package com.iig.gcp.hipdashboard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.stereotype.Component;


import com.iig.gcp.hipdashboard.dto.HipDashboardDTO;
import com.iig.gcp.feedlogging.dto.FeedLoggerDTO;
import com.iig.gcp.utils.ConnectionUtils;
@Component
public class HipDAOImpl implements HipDAO {

	
	@Override
	public ArrayList<String> getfeeds() throws SQLException, Exception {
		ArrayList<String> arr = new ArrayList<String>();
		Connection connection = null;
		try {
			connection = ConnectionUtils.getConnection();
			PreparedStatement pstm = connection.prepareStatement("select distinct feed_id from logger_master order by feed_id; ");
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				arr.add(rs.getString(1));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}	
		ConnectionUtils.closeQuietly(connection);
		return arr;
	}
	
	@Override
	public ArrayList<FeedLoggerDTO> getfeeddetails(String feed_id) throws SQLException, Exception {
		ArrayList<FeedLoggerDTO> arr = new ArrayList<FeedLoggerDTO>();
		Connection connection = null;
		try {
			connection = ConnectionUtils.getConnection();
			PreparedStatement pstm = connection.prepareStatement("select * from logger_master where feed_id='"+feed_id+"'");
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				FeedLoggerDTO fl=new FeedLoggerDTO();
				fl.setFeed_id(rs.getString(1));
				fl.setClassification(rs.getString(2));
				fl.setSubclassification(rs.getString(3));
				fl.setValue(rs.getString(4));
				arr.add(fl);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}	
		ConnectionUtils.closeQuietly(connection);
		return arr;
	}

	@Override
	public ArrayList<String> getfeedsFromLoggerStats() throws SQLException, Exception {
		ArrayList<String> arr = new ArrayList<String>();
		Connection connection = null;
		try {
			connection = ConnectionUtils.getConnection();
			PreparedStatement pstm = connection.prepareStatement("select distinct event_feed_id from logger_stats_master order by event_feed_id;");
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				arr.add(rs.getString(1));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}	
		ConnectionUtils.closeQuietly(connection);
		return arr;
	}

	@Override
	public ArrayList<HipDashboardDTO> getTableChartLoggerStats(@Valid String feed_id) throws SQLException, Exception {
		// TODO Auto-generated method stub
		ArrayList<HipDashboardDTO> arrHipDashboard=new ArrayList<HipDashboardDTO>();
		HipDashboardDTO hipDashboardDTO = null;
		Connection conn = ConnectionUtils.getConnection();
		PreparedStatement pstm = conn.prepareStatement("select event_feed_id, event_batch_date,event_run_id,start_time,end_time,duration from (select st.event_feed_id, st.event_batch_date, st.event_run_id, st.event_value as start_time, en.event_value as end_time, cast(time_to_sec(timediff(en.event_value,st.event_value))/60 as char) as duration from (select * from logger_stats_master where event_type='start') st inner join (select * from logger_stats_master where event_type='end') en on st.event_feed_id=en.event_feed_id and st.event_run_id=en.event_run_id and st.event_batch_date=en.event_batch_date)a  where event_feed_id = ? order by event_batch_date;");
		pstm.setString(1, feed_id);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			hipDashboardDTO = new HipDashboardDTO();
			hipDashboardDTO.setBatch_id(rs.getString(1));
			hipDashboardDTO.setBatch_date(rs.getString(2));
			hipDashboardDTO.setRun_id(rs.getString(3));
			hipDashboardDTO.setStart_time(rs.getString(4));
			hipDashboardDTO.setEnd_time(rs.getString(5));
			hipDashboardDTO.setDuration(rs.getString(6));
			
			arrHipDashboard.add(hipDashboardDTO);
		}
		return arrHipDashboard;
	}
	
	
	
	
	
	
}
