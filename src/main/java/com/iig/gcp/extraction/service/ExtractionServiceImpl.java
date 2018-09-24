package com.iig.gcp.extraction.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.iig.gcp.constants.MySqlConstants;
import com.iig.gcp.extraction.dto.ConnectionMaster;
import com.iig.gcp.extraction.dto.CountryMaster;
import com.iig.gcp.extraction.dto.ReservoirMaster;
import com.iig.gcp.extraction.dto.SourceSystemMaster;
import com.iig.gcp.utils.ConnectionUtils;

@Service
public class ExtractionServiceImpl implements ExtractionService {
	@Override
	public String invokeRest(String json, String url) throws UnsupportedOperationException, Exception {
		String resp = null;
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost postRequest = new HttpPost(MySqlConstants.EXTRACTION_COMPUTE_URL + url);
		postRequest.setHeader("Content-Type", "application/json");
		StringEntity input = new StringEntity(json);
		postRequest.setEntity(input);
		HttpResponse response = httpClient.execute(postRequest);
		String response_string = EntityUtils.toString(response.getEntity(), "UTF-8");
		if (response.getStatusLine().getStatusCode() != 200) {
			resp = "Error" + response_string;
			throw new Exception("Error" + response_string);
		} else {
			resp = response_string;
		}
		return resp;
	}

	@Override
	public ArrayList<ConnectionMaster> getConnections(String src_val) {
		// TODO Auto-generated method stub
		Connection connection;
		ConnectionMaster conn = null;
		ArrayList<ConnectionMaster> arrConnectionMaster = new ArrayList<ConnectionMaster>();
		try {
			connection = ConnectionUtils.getConnection();
			PreparedStatement pstm = connection.prepareStatement(MySqlConstants.GET_SOURCE_CONNECTIONS);
			pstm.setString(1, src_val);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				conn = new ConnectionMaster();
				conn.setConnection_id(rs.getInt(1));
				conn.setConnection_name(rs.getString(2));
				conn.setHost_name(rs.getString(3));
				conn.setPort_no(rs.getString(4));
				conn.setUsername(rs.getString(5));
				conn.setPassword(rs.getString(6));
				conn.setDatabase_name(rs.getString(7));
				conn.setService_name(rs.getString(8));
				arrConnectionMaster.add(conn);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return arrConnectionMaster;
	}

	@Override
	public ArrayList<String> getTargets() {
		ArrayList<String> arr = new ArrayList<String>();
		Connection connection;
		try {
			connection = ConnectionUtils.getConnection();
			PreparedStatement pstm = connection.prepareStatement("select target_unique_name from target_master");
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				arr.add(rs.getString(1));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return arr;
	}
	
	@Override
	public ConnectionMaster getConnections1(String src_val,int src_sys_id) {
		// TODO Auto-generated method stub
		Connection connection;
		ConnectionMaster conn = new ConnectionMaster();
		try {
			connection = ConnectionUtils.getConnection();
			PreparedStatement pstm = connection.prepareStatement("select connection_id from extraction_master where src_sys_id="+src_sys_id);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				conn.setConnection_id(rs.getInt(1));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	@Override
	public String getExtType(int src_sys_id) {
		// TODO Auto-generated method stub
		String val=null;
		Connection connection;
		try {
			connection = ConnectionUtils.getConnection();
			PreparedStatement pstm = connection.prepareStatement("select extraction_type from extraction_master where src_sys_id="+src_sys_id);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				val=rs.getString(1);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return val;
	}
	
	@Override
	public String getExtType1(String src_unique_name) {
		// TODO Auto-generated method stub
		String val=null;
		Connection connection;
		try {
			connection = ConnectionUtils.getConnection();
			PreparedStatement pstm = connection.prepareStatement("select extraction_type from extraction_master "
					+ "where src_sys_id=(select src_sys_id from source_system_master where src_unique_name='"+src_unique_name+"')");
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				val=rs.getString(1);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return val;
	}
	
	@Override
	public ArrayList<String> getTables(String src_val, int conn_id) {
		ArrayList<ConnectionMaster> arrcm = getConnections(src_val);
		ArrayList<String> arrTbl = new ArrayList<String>();
		String query = null, connectionUrl = null, host = null, port = null, username = null, password = null, db = null, service = null;
		Connection serverConnection = null;
		Statement st = null;
		for (ConnectionMaster cm : arrcm) {
			if (conn_id == cm.getConnection_id()) {
				host = cm.getHost_name();
				port = cm.getPort_no();
				username = cm.getUsername();
				password = cm.getPassword();
				db = cm.getDatabase_name();
				service = cm.getService_name();
			}
		}
		try {
			if (src_val.equalsIgnoreCase("Mssql")) {
				query = "SELECT TABLE_SCHEMA,TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_CATALOG='" + db + "'";
				connectionUrl = "jdbc:sqlserver://" + host + ":" + port + ";DatabaseName=" + db;
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			} else if (src_val.equalsIgnoreCase("unix"))
				;
			else {
				query = "SELECT table_name FROM user_tables";
				Class.forName("oracle.jdbc.driver.OracleDriver");
				connectionUrl = "jdbc:oracle:thin:@" + host + ":" + port + "/" + service + "";
			}
			if (src_val.equalsIgnoreCase("unix"))
				;
			else {
				serverConnection = DriverManager.getConnection(connectionUrl, username, password);
				st = serverConnection.createStatement();

				ResultSet rs2 = st.executeQuery(query);
				while (rs2.next()) {
					if (src_val.equalsIgnoreCase("Mssql")) {
						arrTbl.add(rs2.getString(1) + "." + rs2.getString(2));
					} else {
						arrTbl.add(rs2.getString(1));
					}
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (src_val.equalsIgnoreCase("unix"))
				;
			else {
				try {
					st.close();
					serverConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return arrTbl;
	}

	@Override
	public ArrayList<String> getFields(String id, String src_val, String table_name, int conn_id) {
		ArrayList<ConnectionMaster> arrcm = getConnections(src_val);
		ArrayList<String> arrFld = new ArrayList<String>();
		String query = null, connectionUrl = null, host = null, port = null, username = null, password = null, db = null, service = null;
		Connection serverConnection = null;
		Statement st = null;
		for (ConnectionMaster cm : arrcm) {
			if (conn_id == cm.getConnection_id()) {
				host = cm.getHost_name();
				port = cm.getPort_no();
				username = cm.getUsername();
				password = cm.getPassword();
				db = cm.getDatabase_name();
				service = cm.getService_name();
			}
		}
		String tbls[] = table_name.split(",");
		for (int i = 0; i < tbls.length; i++) {
			try {
				if (src_val.equalsIgnoreCase("Mssql")) {
					String temp = tbls[i].substring(tbls[i].lastIndexOf(".") + 1);
					query = "select ColumnName = col.column_name " + "FROM information_schema.tables tbl INNER JOIN information_schema.columns col ON col.table_name = tbl.table_name "
							+ "LEFT JOIN sys.extended_properties prop ON prop.major_id = object_id(tbl.table_schema + '.' + tbl.table_name) AND prop.minor_id = 0 AND prop.name = '" + temp + "' "
							+ "WHERE tbl.table_type = 'base table' AND tbl.table_name = '" + temp + "'";
					connectionUrl = "jdbc:sqlserver://" + host + ":" + port + ";DatabaseName=" + db;
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				} else if (src_val.equalsIgnoreCase("Unix"))
					;
				else {
					query = "SELECT column_name FROM user_tab_cols where table_name='" + tbls[i] + "'";
					Class.forName("oracle.jdbc.driver.OracleDriver");
					connectionUrl = "jdbc:oracle:thin:@" + host + ":" + port + "/" + service + "";
				}
				if (src_val.equalsIgnoreCase("Unix"))
					;
				else {
					serverConnection = DriverManager.getConnection(connectionUrl, username, password);
					st = serverConnection.createStatement();
					ResultSet rs2 = st.executeQuery(query);
					while (rs2.next()) {
						arrFld.add(rs2.getString(1));
					}
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (src_val.equalsIgnoreCase("Unix"))
					;
				else {
					try {
						st.close();
						serverConnection.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return arrFld;
	}
	
	@Override
	public ArrayList<SourceSystemMaster> getSources(String src_val) {
		SourceSystemMaster ssm = null;
		ArrayList<SourceSystemMaster> arrssm = new ArrayList<SourceSystemMaster>();
		Connection connection;
		try {
			connection = ConnectionUtils.getConnection();
			PreparedStatement pstm = connection.prepareStatement("select src_sys_id,src_unique_name from source_system_master");
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				ssm = new SourceSystemMaster();
				ssm.setSrc_sys_id(rs.getInt(1));
				ssm.setSrc_unique_name(rs.getString(2));
				arrssm.add(ssm);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return arrssm;
	}

	@Override
	public ArrayList<CountryMaster> getCountries() {
		CountryMaster cm = null;
		ArrayList<CountryMaster> arrcm = new ArrayList<CountryMaster>();
		Connection connection;
		try {
			connection = ConnectionUtils.getConnection();
			PreparedStatement pstm = connection.prepareStatement("select country_code,country_name from country_master");
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				cm = new CountryMaster();
				cm.setCountry_code(rs.getString(1));
				cm.setCountry_name(rs.getString(2));
				arrcm.add(cm);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return arrcm;
	}
	
	@Override
	public ArrayList<ReservoirMaster> getReservoirs() {
		ReservoirMaster rm = null;
		ArrayList<ReservoirMaster> arrrm = new ArrayList<ReservoirMaster>();
		Connection connection;
		try {
			connection = ConnectionUtils.getConnection();
			PreparedStatement pstm = connection.prepareStatement("select reservoir_id,reservoir_name,reservoir_desc from reservoir_master where reservoir_status='Y' and lower(reservoir_desc) like '%extraction%'");
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				rm = new ReservoirMaster();
				rm.setReservoir_id(rs.getInt(1));
				rm.setReservoir_name(rs.getString(2));
				rm.setReservoir_desc(rs.getString(3));
				arrrm.add(rm);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return arrrm;
	}
	
	@Override
	public int checkNames(String sun)
	{
		Connection connection=null;
		int stat=0;
		try {
			connection = ConnectionUtils.getConnection();
			PreparedStatement pstm = connection.prepareStatement("select src_unique_name from source_system_master where src_unique_name='"+sun+"'");
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