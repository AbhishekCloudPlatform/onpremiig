package com.iig.gcp.login.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.iig.gcp.login.dto.UserAccount;
import com.iig.gcp.utils.ConnectionUtils;

@Component
public class LoginDAOImpl implements LoginDAO {

	@Override
	public ArrayList<UserAccount> getUserAccount() throws Exception {
		
		ArrayList<UserAccount> arrUsers= new ArrayList<UserAccount>();
		String sql = "Select a.login_id, a.username,a.password, a.email_id, "
				+ " a.menu_id from login_master a ";

		Connection conn= ConnectionUtils.getConnection();
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		if (rs.next()) {
				UserAccount user = new UserAccount();
				user.setLogin_id(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setEmail_id(rs.getString(4));
				user.setMenu_id(rs.getString(5));
				arrUsers.add(user);	
		}
		return arrUsers;
	}

	@Override
	public String getMenuCodes(String menu_id) throws Exception {
		 	String menu_code="";
	        String menu_link=null;
	        ArrayList<String> menu_name=new ArrayList<String>();
	        ArrayList<Integer> menu_levell=new ArrayList<Integer>();       
	        int menu_level=0;
	        int i=0;
	        
	        String sql = "select menu_link,menu_level,menu_name from menu_master where menu_id in ("+menu_id+") order by menu_order";
			Connection conn= ConnectionUtils.getConnection();

			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
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


		
		return menu_code;
	}

}
