package com.iig.gcp.hipdashboard.service;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iig.gcp.feedlogging.dto.FeedLoggerDTO;
import com.iig.gcp.hipdashboard.dao.HipDAO;

@Service
public class HipServiceImpl implements HipService {

	@Autowired
	HipDAO hipDao;
	
	@Override
	public ArrayList<String> getfeeds() throws SQLException, Exception {
		// TODO Auto-generated method stub
		return hipDao.getfeeds();
	}
	
	@Override
	public ArrayList<FeedLoggerDTO> getfeeddetails(String feed_id) throws SQLException, Exception {
		// TODO Auto-generated method stub
		return hipDao.getfeeddetails(feed_id);
	}

	@Override
	public ArrayList<String> getfeedsFromLoggerStats() throws SQLException, Exception {
		// TODO Auto-generated method stub
		return hipDao.getfeedsFromLoggerStats();
	}

}
