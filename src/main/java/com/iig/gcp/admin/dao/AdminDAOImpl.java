package com.iig.gcp.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.iig.gcp.admin.dto.Feature;
import com.iig.gcp.utils.ConnectionUtils;

@Component
public class AdminDAOImpl implements AdminDAO{

	@Override
	public String getUser(String user) throws Exception {
		Connection connection=null;
		//int stat=0;
		String userid=null;
			connection = ConnectionUtils.getConnection();
			PreparedStatement pstm = connection.prepareStatement("select user_id from juniper_user_master where user_id='"+user+"'");
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				userid=rs.getString(1);
				break;
			}
		
		ConnectionUtils.closeQuietly(connection);
		return userid;
	}

	@Override
	public ArrayList<Feature> getFeatures() throws Exception {
		Connection connection=null;
		Feature feature=null;
		 ArrayList<Feature> arrFeatures =new  ArrayList<Feature>();
			connection = ConnectionUtils.getConnection();
			PreparedStatement pstm = connection.prepareStatement("select feature_name,feature_order, feature_level,feature_sequence  from juniper_feature_master order by feature_sequence");
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				feature=new Feature();
				feature.setFeature_name(rs.getString(1));
				feature.setFeature_order(rs.getInt(2));
				feature.setFeature_level(rs.getInt(3));
				feature.setFeature_sequence(rs.getInt(4));

				arrFeatures.add(feature);
			}
		
		ConnectionUtils.closeQuietly(connection);
		return arrFeatures;
	}

}
