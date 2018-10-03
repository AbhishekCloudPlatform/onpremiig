package com.iig.gcp.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.validation.Valid;

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

	
	private static String SPACE = " ";
	private static String COMMA = ",";
	private static String SEMICOLON = ";";
	private static String QUOTE = "\'";
	private static String DATABASE_NAME = "iigs_scheduler_db";
	private static String PROJECT_MASTER_TABLE ="juniper_project_master";

	
	/*
	 * This method accepts inputs from project registration form and add in project master table.
	 * (non-Javadoc)
	 * @see com.iig.gcp.project.dao.ProjectDAO#registerProject(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String registerProject(@Valid String projectId, String projectName, String projectOwner,
			String projectDetails) throws ClassNotFoundException, Exception 
	{
		Connection conn = ConnectionUtils.getConnection();
		try {
			String registerProjectQuery = "INSERT INTO" + SPACE + DATABASE_NAME + "." + PROJECT_MASTER_TABLE
					+ SPACE
					+ "(project_sequence,project_id,project_name,project_owner,project_details)" + "VALUES (project_sequence "+ COMMA
					+ QUOTE + projectId+ QUOTE  + COMMA
					+ QUOTE + projectName+ QUOTE  + COMMA
					+ QUOTE + projectOwner+ QUOTE  + COMMA
					+ QUOTE + projectDetails+ QUOTE + ")";
			
			Statement statement = conn.createStatement();
			statement.execute(registerProjectQuery);
			ConnectionUtils.closeQuietly(conn);
			return "Project Registration Completed successfully";

		} catch (Exception e) {
			e.printStackTrace();
			return "Project Registration Failed";
		}
	}

	@Override
	public ArrayList<Feature> getFeatures(String userid, String project) throws Exception {
		Connection connection=null;
		Feature feature=null;
		 ArrayList<Feature> arrFeatures =new  ArrayList<Feature>();
			connection = ConnectionUtils.getConnection();
			PreparedStatement pstm = connection.prepareStatement("select  f.feature_sequence, f.feature_name,u.user_sequence from juniper_pro_u_feat_master l inner join juniper_user_master u on l.user_sequence=u.user_sequence inner join juniper_project_master p on l.project_sequence=p.project_sequence inner join juniper_feature_master f on l.feature_sequence=f.feature_sequence where u.user_id=? and p.project_id=?;");

			pstm.setString(1,userid);
			pstm.setString(2,project);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				feature=new Feature();
				feature.setFeature_sequence(rs.getInt(1));
				feature.setFeature_name(rs.getString(2));
				feature.setSelected_user_sequence(rs.getInt(3));
				arrFeatures.add(feature);
			}
		
		ConnectionUtils.closeQuietly(connection);
		return arrFeatures;
	}

	@Override
	public ArrayList<Feature> getFeaturesAlready(String userid, String project) throws Exception {
		Connection connection=null;
		Feature feature=null;
		 ArrayList<Feature> arrFeatures =new  ArrayList<Feature>();
			connection = ConnectionUtils.getConnection();
			PreparedStatement pstm = connection.prepareStatement("select f.feature_sequence, f.feature_name from juniper_feature_master f left join (select l.feature_sequence from juniper_pro_u_feat_master l inner join juniper_user_master u on l.user_sequence=u.user_sequence inner join juniper_project_master p on l.project_sequence=p.project_sequence where u.user_id=? and p.project_id=?) feat on feat.feature_sequence = f.feature_sequence  where feat.feature_sequence is null;");

			pstm.setString(1,userid);
			pstm.setString(2,project);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				feature=new Feature();
				feature.setFeature_sequence(rs.getInt(1));
				feature.setFeature_name(rs.getString(2));
				arrFeatures.add(feature);
			}
		
		ConnectionUtils.closeQuietly(connection);
		return arrFeatures;
	}

	@Override
	public int getUserSequence(String userid) throws Exception {
		int seq = 0;
		Connection connection=null;
		connection = ConnectionUtils.getConnection();
		PreparedStatement pstm = connection.prepareStatement("select user_sequence from  juniper_user_master where user_id=?");

		pstm.setString(1,userid);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			seq=rs.getInt(1);
		}
		ConnectionUtils.closeQuietly(connection);
		return seq;
	}

	@Override
	public void onboardUser(int projectseq, int selectUser_Seq, String feature_seq) throws Exception {
		deleteEntries(projectseq,selectUser_Seq);
		Connection connection=null;
		connection = ConnectionUtils.getConnection();
		String[] arrString =feature_seq.split(",");
		for(String feature:arrString) {
			PreparedStatement pstm = connection.prepareStatement("insert ignore into juniper_pro_u_feat_master values (juniper_pro_u_feat_sequence,?,?,?);");
			pstm.setInt(1,selectUser_Seq);
			pstm.setInt(2,projectseq);
			pstm.setString(3,feature);
			pstm.executeUpdate();
			
		}
		ConnectionUtils.closeQuietly(connection);
	}

	private void deleteEntries(int projectseq, int selectUser_Seq) throws Exception {
		Connection connection=null;
		connection = ConnectionUtils.getConnection();
		PreparedStatement pstm = connection.prepareStatement("delete from juniper_pro_u_feat_master where user_sequence=? and project_sequence=?;");
		pstm.setInt(1,selectUser_Seq);
		pstm.setInt(2,projectseq);
		pstm.executeUpdate();
		ConnectionUtils.closeQuietly(connection);
	}

}
