package com.iig.gcp.system.service;

import java.sql.SQLException;
import java.util.List;

import javax.validation.Valid;

import com.iig.gcp.extraction.dto.CountryMaster;

public interface SystemService {
	String registerSystem(String systemEIM,String systemName,String region,String country,String owner, String platformType,String targetProject,String serviceAccount,String targetBucket,String knoxURL,String hadoopPort,String hostName,String filePort,String userName,String environmentType) throws ClassNotFoundException, SQLException;
	List<CountryMaster> fetchCountries(@Valid String region);
	int checkEIM(@Valid String system_eim);
}
