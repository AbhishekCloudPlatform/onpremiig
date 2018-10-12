package com.iig.gcp.system.service;

import java.sql.SQLException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iig.gcp.extraction.dto.CountryMaster;
import com.iig.gcp.system.dao.SystemDAO;

@Service
public class SystemServiceImpl implements SystemService {
	
	@Autowired
	SystemDAO systemDAO;

	@Override
	public String registerSystem(String systemEIM,String systemName,String region,String country,String owner, String platformType,String targetProject,String serviceAccount,String targetBucket,String knoxURL,String hadoopPort,String hostName,String filePort,String userName, String environmentType) throws ClassNotFoundException, SQLException {
		return systemDAO.registerSystem(systemEIM,systemName,region,country,owner,platformType,targetProject,serviceAccount,targetBucket,knoxURL,hadoopPort,hostName,filePort,userName,environmentType);
	}

	@Override
	public List<CountryMaster> fetchCountries(@Valid String region) {
		return systemDAO.fetchCountries(region);
	}

	@Override
	public int checkEIM(@Valid String system_eim) {
		return systemDAO.checkEIM(system_eim);
	}
}
