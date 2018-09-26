package com.iig.gcp.feedlogging.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.stereotype.Component;

import com.iig.gcp.feedlogging.dto.FeedLoggerDTO;
import com.iig.gcp.utils.ConnectionUtils;

@Component
public class FeedLoggerDAOImpl implements FeedLoggerDAO {
	private static String SPACE = " ";
	private static String COMMA = ",";
	private static String SEMICOLON = ";";
	private static String QUOTE = "\'";
	private static String TABLE_NAME = "logger_master";
	private static String DATABASE_NAME = "iigs_scheduler_db";

	@Override
	public void loadFeed(List<FeedLoggerDTO> dataLoggerCollection) throws Exception, SQLException {

		Connection conn = ConnectionUtils.getConnection();

		for (FeedLoggerDTO feedLogger : dataLoggerCollection)
			try {
					String insertFeedLoggerQuery = "INSERT INTO" + SPACE + DATABASE_NAME + "." + TABLE_NAME + SPACE
							+ "(feed_id,classification,subclassification,value)" + "VALUES" + "("
							+ QUOTE + feedLogger.getFeed_id() + QUOTE + COMMA + QUOTE + feedLogger.getClassification()
							+ QUOTE + COMMA + QUOTE + feedLogger.getSubclassification() + QUOTE + COMMA + QUOTE
							+ feedLogger.getValue() + QUOTE + ")" + SEMICOLON;

					Statement statement = conn.createStatement();
					statement.execute(insertFeedLoggerQuery);
				
			} catch (Exception e) {
				System.out.println("Cannot write Feed in database");
				e.printStackTrace();
			}
		ConnectionUtils.closeQuietly(conn);
	}

}
