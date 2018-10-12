package com.iig.gcp.hipdashboard.dao;

import java.sql.SQLException;
import java.util.ArrayList;


import javax.validation.Valid;
import com.iig.gcp.hipdashboard.dto.HipDashboardDTO;
import com.iig.gcp.feedlogging.dto.FeedLoggerDTO;

public interface HipDAO {

	public ArrayList<String> getfeeds() throws SQLException, Exception;
	public ArrayList<FeedLoggerDTO> getfeeddetails(String feed_id) throws SQLException, Exception;
	public ArrayList<String> getfeedsFromLoggerStats() throws SQLException, Exception;

	public ArrayList<HipDashboardDTO> getTableChartLoggerStats(@Valid String feed_id) throws SQLException, Exception;
	public String checkFeedAvailable(@Valid String feed_id) throws SQLException, Exception;
}
