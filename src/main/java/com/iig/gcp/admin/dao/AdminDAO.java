package com.iig.gcp.admin.dao;

import java.util.ArrayList;

import com.iig.gcp.admin.dto.Feature;

public interface AdminDAO {

	String getUser(String user) throws Exception;

	ArrayList<Feature> getFeatures() throws Exception;



}
