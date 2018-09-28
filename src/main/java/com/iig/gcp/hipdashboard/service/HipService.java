package com.iig.gcp.hipdashboard.service;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.validation.Valid;
import com.iig.gcp.hipdashboard.dto.HipDashboardDTO;
import com.iig.gcp.feedlogging.dto.FeedLoggerDTO;

public interface HipService {

	ArrayList<String> getfeeds() throws SQLException, Exception;
	ArrayList<FeedLoggerDTO> getfeeddetails(String feed_id) throws SQLException, Exception;
	ArrayList<String> getfeedsFromLoggerStats() throws SQLException, Exception;
	ArrayList<HipDashboardDTO> getTableChartLoggerStats(@Valid String feed_id) throws SQLException, Exception;

}
