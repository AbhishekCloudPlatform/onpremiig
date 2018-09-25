package com.iig.gcp.hipdashboard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

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
	
	
	
	
	
	
}
