package com.iig.gcp.feedlogging.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iig.gcp.feedlogging.dao.FeedLoggerDAOImpl;
import com.iig.gcp.feedlogging.dto.FeedLoggerDTO;
import com.iig.gcp.utils.ConnectionUtils;

@Service
public class FeedLoggingServiceImpl implements FeedLoggingService{

	@Autowired
	FeedLoggerDAOImpl feedLoggerDAOImpl;
	
	@Override
	public void feedDataLogging(List<FeedLoggerDTO> dataLoggerCollection) throws SQLException, Exception {
		feedLoggerDAOImpl.loadFeed(dataLoggerCollection);
	}

	@Override
	public ArrayList<String> getfeeds() throws SQLException, Exception {
		ArrayList<String> arr = new ArrayList<String>();
		Connection connection;
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
		return arr;
	}
}
