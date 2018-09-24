package com.iig.gcp.extraction.service;

import java.util.ArrayList;

import com.iig.gcp.extraction.dto.ConnectionMaster;
import com.iig.gcp.extraction.dto.CountryMaster;
import com.iig.gcp.extraction.dto.ReservoirMaster;
import com.iig.gcp.extraction.dto.SourceSystemMaster;

public interface ExtractionService {

	public String invokeRest(String json,String url) throws UnsupportedOperationException, Exception ;
	public ArrayList<ConnectionMaster> getConnections(String src_val);
	public ConnectionMaster getConnections1(String src_val,int src_sys_id);
	public String getExtType(int src_sys_id);
	public String getExtType1(String src_unique_name);
	public ArrayList<SourceSystemMaster> getSources(String src_val);
	public ArrayList<String> getTables(String src_val,int conn_id);
	public ArrayList<String> getFields(String id,String src_val,String table_name,int conn_id);
	public ArrayList<CountryMaster> getCountries();
	public ArrayList<ReservoirMaster> getReservoirs();
	public int checkNames(String sun);
}
