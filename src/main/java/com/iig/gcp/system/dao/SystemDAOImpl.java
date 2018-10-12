package com.iig.gcp.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Component;

import com.iig.gcp.extraction.dto.CountryMaster;
import com.iig.gcp.utils.ConnectionUtils;

@Component
public class SystemDAOImpl implements SystemDAO{
	
	private static String SPACE = " ";
	private static String COMMA = ",";
	private static String SEMICOLON = ";";
	private static String QUOTE = "\'";
	private static String DATABASE_NAME = "iigs_scheduler_db";
	private static String SYSTEM_MASTER_TABLE ="juniper_system_master";
	private static String COUNTRY_REGION_MAPPING_TABLE ="country_region_master";
	
	
	/* (non-Javadoc)
	 * @see com.iig.gcp.system.dao.SystemDAO#registerSystem(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String registerSystem(String systemEIM,String systemName,String region,String country,String owner, String platformType,String targetProject,String serviceAccount,String targetBucket,String knoxURL,String hadoopPort,String hostName,String filePort,String userName,String environmentType) throws ClassNotFoundException, SQLException {
		Connection conn = ConnectionUtils.getConnection();
		String registerProjectQuery;
		try {
			if(platformType.equals("GCS")) {
				 registerProjectQuery = "INSERT INTO" + SPACE + DATABASE_NAME + "." + SYSTEM_MASTER_TABLE
						+ SPACE
						+ "(system_sequence,system_eim,system_name,system_region,system_country,owner,created_by,created_date,platform_type,target_project,service_account,target_bucket,environment_type)" 
						+ "VALUES (system_sequence "+ COMMA
						+ QUOTE + systemEIM+ QUOTE + COMMA
						+ QUOTE + systemName+ QUOTE  + COMMA
						+ QUOTE + region+ QUOTE  + COMMA
						+ QUOTE + country+ QUOTE  + COMMA
						+ QUOTE + owner+ QUOTE + COMMA
						+ QUOTE + userName+ QUOTE + COMMA
						+   "now()"+ COMMA
						//+ QUOTE + "updated by"+ QUOTE + COMMA
						//+ QUOTE + "now()"+ QUOTE + COMMA
						+ QUOTE + platformType+ QUOTE + COMMA
						+ QUOTE + targetProject+ QUOTE + COMMA 
						+ QUOTE + serviceAccount+ QUOTE + COMMA 
						+ QUOTE + targetBucket+ QUOTE + COMMA
						+ QUOTE + environmentType+ QUOTE +
						")" + SEMICOLON;
			}else if(platformType.equals("HDFS")) {
				 registerProjectQuery = "INSERT INTO" + SPACE + DATABASE_NAME + "." + SYSTEM_MASTER_TABLE
						+ SPACE
						+ "(system_sequence,system_eim,system_name,system_region,system_country,owner,created_by,created_date,platform_type,knox_url,hadoop_port,environment_type)" 
						+ "VALUES (system_sequence "+ COMMA
						+ QUOTE + systemEIM+ QUOTE + COMMA
						+ QUOTE + systemName+ QUOTE  + COMMA
						+ QUOTE + region+ QUOTE  + COMMA
						+ QUOTE + country+ QUOTE  + COMMA
						+ QUOTE + owner+ QUOTE + COMMA
						+ QUOTE + userName+ QUOTE + COMMA
						+   "now()"+ COMMA
						//+ QUOTE + "updated by"+ QUOTE + COMMA
						//+ QUOTE + "now()"+ QUOTE + COMMA
						+ QUOTE + platformType+ QUOTE + COMMA
						+ QUOTE + knoxURL+ QUOTE + COMMA
						+ QUOTE + hadoopPort+ QUOTE + COMMA+  QUOTE + environmentType+ QUOTE +
						")" + SEMICOLON;
			}else if(platformType.equals("FILE") || platformType.equals("ORACLE")) {
				 registerProjectQuery = "INSERT INTO" + SPACE + DATABASE_NAME + "." + SYSTEM_MASTER_TABLE
						+ SPACE
						+ "(system_sequence,system_eim,system_name,system_region,system_country,owner,created_by,created_date,platform_type,host_name,file_port,environment_type)" 
						+ "VALUES (system_sequence "+ COMMA
						+ QUOTE + systemEIM+ QUOTE + COMMA
						+ QUOTE + systemName+ QUOTE  + COMMA
						+ QUOTE + region+ QUOTE  + COMMA
						+ QUOTE + country+ QUOTE  + COMMA
						+ QUOTE + owner+ QUOTE + COMMA
						+ QUOTE + userName+ QUOTE + COMMA
						+   "now()"+ COMMA
						//+ QUOTE + "updated by"+ QUOTE + COMMA
						//+ QUOTE + "now()"+ QUOTE + COMMA
						+ QUOTE + platformType+ QUOTE + COMMA
						+ QUOTE + hostName+ QUOTE + COMMA
						+ QUOTE + filePort+ QUOTE 
						+ QUOTE + hadoopPort+ QUOTE + COMMA+  QUOTE + environmentType+ QUOTE +
						")" + SEMICOLON;
			}else {
				 registerProjectQuery = "INSERT INTO" + SPACE + DATABASE_NAME + "." + SYSTEM_MASTER_TABLE
						+ SPACE
						+ "(system_sequence,system_eim,system_name,system_region,system_country,created_by,created_date,system_host,system_port,platform_type,owner,environment_type)" 
						+ "VALUES (system_sequence "+ COMMA
						+ QUOTE + systemEIM+ QUOTE + COMMA
						+ QUOTE + systemName+ QUOTE  + COMMA
						+ QUOTE + region+ QUOTE  + COMMA
						+ QUOTE + country+ QUOTE  + COMMA
						+ QUOTE + userName+ QUOTE + COMMA
						+   "now()"+ COMMA
						//+ QUOTE + "updated by"+ QUOTE + COMMA
						//+ QUOTE + "now()"+ QUOTE + COMMA
						+ QUOTE + hostName+ QUOTE + COMMA 
						+ QUOTE + filePort+ QUOTE + COMMA
						+ QUOTE + platformType+ QUOTE + COMMA
						+ QUOTE + owner+ QUOTE 
						+ QUOTE + hadoopPort+ QUOTE + COMMA+  QUOTE + environmentType+ QUOTE 
						+ ")" + SEMICOLON;
			}
			
			Statement statement = conn.createStatement();
			statement.execute(registerProjectQuery);
			ConnectionUtils.closeQuietly(conn);
			return "Success";

		} catch (Exception e) {
			e.printStackTrace();
			return "Failure";
		}
	}


	/* (non-Javadoc)
	 * @see com.iig.gcp.system.dao.SystemDAO#fetchCountries(java.lang.String)
	 */
	@Override
	public List<CountryMaster> fetchCountries(@Valid String region) {
		CountryMaster cm = null;
		ArrayList<CountryMaster> countries = new ArrayList<CountryMaster>();
		Connection connection;
		try {
			connection = ConnectionUtils.getConnection();
			PreparedStatement pstm = connection.prepareStatement("select country_code,country_name from "+DATABASE_NAME + "." +COUNTRY_REGION_MAPPING_TABLE+" where region=? ");
			pstm.setString(1, region);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				cm = new CountryMaster();
				cm.setCountry_code(rs.getString(1));
				cm.setCountry_name(rs.getString(2));
				countries.add(cm);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return countries;
	}


	

	/* (non-Javadoc)
	 * @see com.iig.gcp.system.dao.SystemDAO#checkEIM(java.lang.String)
	 */
	@Override
	public int checkEIM(@Valid String system_eim) {
		Connection connection=null;
		int stat=0;
		try {
			connection = ConnectionUtils.getConnection();
			PreparedStatement pstm = connection.prepareStatement("select system_eim from "+DATABASE_NAME + "." +SYSTEM_MASTER_TABLE+ " where system_eim='"+system_eim+"'");
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				stat=1;break;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		ConnectionUtils.closeQuietly(connection);
		return stat;
	}
}
