package com.iig.gcp.feedlogging.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iig.gcp.feedlogging.dto.FeedLoggerDTO;

public interface FeedLoggingService {
public void feedDataLogging(List<FeedLoggerDTO> dataLoggerCollection) throws SQLException, Exception;

}
