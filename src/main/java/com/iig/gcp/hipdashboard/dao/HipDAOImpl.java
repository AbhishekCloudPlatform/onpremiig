package com.iig.gcp.hipdashboard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.stereotype.Component;

import com.iig.gcp.hipdashboard.dto.HipDashboardDTO;
import com.iig.gcp.utils.ConnectionUtils;
@Component
public class HipDAOImpl implements HipDAO {

	
	@Override
	public ArrayList<String> getfeeds() throws SQLException, Exception {
		ArrayList<String> arr = new ArrayList<String>();
		Connection connection = null;
		try {
			connection = ConnectionUtils.getConnection();
			PreparedStatement pstm = connection.prepareStatement("select distinct feed_id from logger_master");
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
	public ArrayList<String> getfeedsFromLoggerStats() throws SQLException, Exception {
		ArrayList<String> arr = new ArrayList<String>();
		Connection connection = null;
		try {
			connection = ConnectionUtils.getConnection();
			PreparedStatement pstm = connection.prepareStatement("select distinct feed_id from logger_stats_master order by feed_id;");
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
		PreparedStatement pstm = conn.prepareStatement("select feed_id, batch_date,run_id,start_time,end_time,duration from (select st.feed_id, st.batch_date, st.run_id, st.value as start_time, en.value as end_time, cast(time_to_sec(timediff(en.value,st.value))/60 as char) as duration from (select * from logger_stats_master where field='start') st inner join (select * from logger_stats_master where field='end') en on st.feed_id=en.feed_id and st.run_id=en.run_id and st.batch_date=en.batch_date)a  where feed_id like ? order by batch_date;");
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
