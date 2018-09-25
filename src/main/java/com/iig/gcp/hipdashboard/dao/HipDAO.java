package com.iig.gcp.hipdashboard.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface HipDAO {

	public ArrayList<String> getfeeds() throws SQLException, Exception;

	public ArrayList<String> getfeedsFromLoggerStats() throws SQLException, Exception;
}
