package com.iig.gcp.login.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.iig.gcp.login.dto.Project;
import com.iig.gcp.login.dto.UserAccount;
import com.iig.gcp.utils.ConnectionUtils;

@Component
public class LoginDAOImpl implements LoginDAO {

	@Override
	public ArrayList<UserAccount> getUserAccount() throws Exception {
		
		ArrayList<UserAccount> arrUsers= new ArrayList<UserAccount>();
		String sql = "select user_id,user_pass,user_sequence, coalesce(is_admin,'N') as is_admin from juniper_user_master";

		Connection conn= ConnectionUtils.getConnection();
		PreparedStatement pstm = conn.prepareStatement(sql); 
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
				UserAccount user = new UserAccount();
				user.setUser_id(rs.getString(1));
				user.setUser_pass(rs.getString(2));
				user.setUser_sequence(rs.getInt(3));
				user.setIs_admin(rs.getString(4));;
				arrUsers.add(user);	
		}
		ConnectionUtils.closeQuietly(conn);
		return arrUsers;
	}

	@Override
	public String getMenuCodes(String userid,String project) throws Exception {
		 	String menu_code="";
	        String menu_link=null;
	        ArrayList<String> menu_name=new ArrayList<String>();
	        ArrayList<Integer> menu_levell=new ArrayList<Integer>();       
	        int menu_level=0;
	        int i=0;
	        
	        String sql = "select f.feature_link, f.feature_level,f.feature_name from juniper_pro_u_feat_master l inner join juniper_user_master u on l.user_sequence=u.user_sequence inner join juniper_project_master p on l.project_sequence=p.project_sequence inner join juniper_feature_master f on l.feature_sequence=f.feature_sequence where u.user_id=? and p.project_id=? order by f.feature_order;";
			Connection conn= ConnectionUtils.getConnection();

			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, userid);
			pstm.setString(2, project);
			ResultSet rs = pstm.executeQuery();
			while(rs.next())
			{
				menu_link = rs.getString(1);
				menu_level = rs.getInt(2);
				menu_name.add(rs.getString(3));
				menu_levell.add(menu_level);
				if(menu_level==1)
				{
					if(i==0)
					{
						menu_code=menu_link;
					}
					else
					{
						menu_code=menu_code+"</ul></li>"+menu_link;
					}
				}
				else
				{
					menu_code=menu_code+menu_link;
				}
				i++;
			}
			menu_code=menu_code+"</ul></li>";


		ConnectionUtils.closeQuietly(conn);

		return menu_code;
	}

	@Override
	public ArrayList<Project> getProjects(String username) throws Exception {
		
		  ArrayList<Project> arrProject=new ArrayList<Project>();
		  String sql = "select distinct p.project_id,p.project_sequence from juniper_pro_u_feat_master l inner join juniper_user_master u on l.user_sequence=u.user_sequence inner join juniper_project_master p on l.project_sequence=p.project_sequence where u.user_id=?;";
			Connection conn= ConnectionUtils.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, username);
			ResultSet rs = pstm.executeQuery();
			Project prj =null;
			while (rs.next()) {
				prj=new Project();
				prj.setProject_id(rs.getString(1));
				prj.setProject_sequence(rs.getInt(2));
				arrProject.add(prj);	
		}
		ConnectionUtils.closeQuietly(conn);
		return arrProject;
		
	}

}
