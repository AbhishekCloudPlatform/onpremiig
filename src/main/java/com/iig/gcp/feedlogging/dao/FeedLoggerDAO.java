package com.iig.gcp.feedlogging.dao;

import java.sql.SQLException;
import java.util.List;

import com.iig.gcp.feedlogging.dto.FeedLoggerDTO;

public interface FeedLoggerDAO {
public void loadFeed(List<FeedLoggerDTO> dataLoggerCollection) throws Exception, SQLException;
}
