package com.iig.gcp.hipdashboard.service;

import java.sql.SQLException;
import java.util.ArrayList;

public interface HipService {

	ArrayList<String> getfeeds() throws SQLException, Exception;
	ArrayList<String> getfeedsFromLoggerStats() throws SQLException, Exception;

}
