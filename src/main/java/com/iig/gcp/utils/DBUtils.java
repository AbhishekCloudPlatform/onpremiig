package com.iig.gcp.utils;

import java.util.ArrayList;

import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class DBUtils {
	public static ArrayList<String> getBuckets() {

        ArrayList<String> bucketList= new ArrayList<String>();
       
        Storage storage = StorageOptions.getDefaultInstance().getService();
        for (Bucket bucket : storage.list().iterateAll()) 
        {
           bucketList.add(bucket.getName());       
           }
        return bucketList;
}
}



/*import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gcp.beans.AirflowBean;
import com.gcp.beans.BatchBean;
import com.gcp.beans.BigQueryTableDetails;
import com.gcp.beans.BusinessGlossaryBean;
import com.gcp.beans.EeBean;
import com.gcp.beans.FieldBean;
import com.gcp.beans.FileBean;
import com.gcp.beans.PipelineBean;
import com.gcp.beans.PullConnectionBean;
import com.gcp.beans.ReservoirBean;
import com.gcp.beans.SystemBean;
import com.gcp.beans.TriggerTableBean;
import com.gcp.beans.UserAccount;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.Dataset;
import com.google.cloud.bigquery.Table;

public class DBUtils {

	private static final Logger LOG = LoggerFactory.getLogger(DBUtils.class);

	static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	static String SPACE = " ";
	static String COMMA = ",";
	static String SEMICOLON = ";";
	static String QUOTE="\'";


	public static UserAccount authenticateUser(Connection conn, //
			String username, String password) throws SQLException, InvalidKeyException, InvalidAlgorithmParameterException {

		String sql = "Select a.login_id, a.username, a.email_id, "
				+ "a.group_id, a.reservoir_id, a.menu_id, a.password from login_master a "
				+ "where a.status!='user_deleted' and a.username = ?";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, username);
		ResultSet rs = pstm.executeQuery();
		String pwd=null;
		if (rs.next()) {
			Decrypter d = new Decrypter();
			pwd=d.decrypter(rs.getString(7));
			if(pwd.equals(password))
			{
				UserAccount user = new UserAccount();
				user.setLogin_id(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setEmail_id(rs.getString(3));
				user.setGroup_id(rs.getString(4));
				user.setReservoir_id(rs.getString(5));
				user.setMenu_id(rs.getString(6));
				return user;
			}
			return null;
		}
		return null;
	}

	public static UserAccount findUser(Connection conn, //
			String userName, String password) throws SQLException {

		String sql = "Select a.user_id, a.encrpt_pwd  from MSTR_USR_TBL a " //
				+ " where a.user_id = ? and a.encrpt_pwd= ?";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userName);
		pstm.setString(2, password);
		ResultSet rs = pstm.executeQuery();


		if (rs.next()) {
			UserAccount user = new UserAccount();
			user.setUsername(userName);

			return user;
		}
		return null;
	}

	public static void insertSystemValue(Connection conn,ArrayList<SystemBean> systemBeans) throws SQLException {

		//ArrayList<SystemBean> systemBeans= new ArrayList<SystemBean> ();

		String tableName = "mstr_src_sys_dtls";

		for(SystemBean bean : systemBeans)
		{


			String insertSysEntry="INSERT INTO"+SPACE+tableName+SPACE+"VALUES"+"("+QUOTE+bean.getSrc_sys_id()+QUOTE+COMMA+QUOTE+bean.getSrc_Eim_id()+QUOTE+COMMA+QUOTE+bean.getCty_cde()+QUOTE + COMMA + QUOTE + bean.getSrc_sys_name()+QUOTE + COMMA+ QUOTE 
					+ bean.getSrc_sys_desc()+QUOTE + COMMA + QUOTE + dateFormat.format(bean.getCreated_Date())+QUOTE + COMMA + bean.getUpdated_Date() + COMMA +QUOTE + bean.getCreated_by()+QUOTE + COMMA +QUOTE + " "+QUOTE+")"+SEMICOLON;

			LOG.info(insertSysEntry);
			Statement statement = conn.createStatement();

			statement.execute(insertSysEntry);
			LOG.info("SYSTEM META DATA INSERTED SUCCESFULLY!!");
		}


	}

	public static void insertSystemTargetValue(Connection conn,ArrayList<SystemBean> systemBeans) throws SQLException {
		String tableName1 = "mstr_src_sys_dtls";
		String tableName2= "mstr_src_tgt_link_tbl";
		for(SystemBean bean : systemBeans)
		{
			String insertSysEntry1="INSERT INTO"+SPACE+tableName1+SPACE+"VALUES"+"("+QUOTE+bean.getSrc_sys_id()+QUOTE+COMMA+QUOTE+bean.getSrc_Eim_id()+QUOTE+COMMA+QUOTE+bean.getCty_cde()+QUOTE + COMMA + QUOTE + bean.getSrc_sys_name()+QUOTE + COMMA+ QUOTE 
					+ bean.getSrc_sys_desc()+QUOTE + COMMA + QUOTE +bean.getMetadata_status()+ QUOTE+COMMA + QUOTE+dateFormat.format(bean.getCreated_Date())+QUOTE + COMMA + bean.getUpdated_Date() + COMMA +QUOTE + bean.getCreated_by()+QUOTE + COMMA +QUOTE + " "+QUOTE+")"+SEMICOLON;

			String insertSysEntry2="INSERT INTO"+SPACE+tableName2+SPACE+"VALUES"+"("+QUOTE+bean.getSrc_sys_id()+QUOTE+COMMA+QUOTE+bean.getSrc_bkt()+QUOTE+COMMA+QUOTE+bean.getSrc_loc()+QUOTE+COMMA+QUOTE+bean.getTgt_prjt()+QUOTE+COMMA+QUOTE+bean.getTgt_ds()+QUOTE+
					COMMA+QUOTE+dateFormat.format(bean.getCreated_Date())+QUOTE+COMMA+ bean.getUpdated_Date()+COMMA+QUOTE+bean.getCreated_by()+QUOTE+COMMA+QUOTE+bean.getUpdated_by()+QUOTE+COMMA+QUOTE+bean.getTgt_type()+QUOTE+COMMA+QUOTE+bean.getSrc_type()+QUOTE+")"+SEMICOLON;
			Statement statement1 = conn.createStatement();
			Statement statement2 = conn.createStatement();
			statement1.execute(insertSysEntry1);
			statement2.execute(insertSysEntry2);
		}
	}

	public static void insertFileValue(Connection conn,ArrayList<FileBean> fileBeans) throws SQLException{


		String tableName = "mstr_file_dtls";
		for(FileBean fBean : fileBeans)
		{
			String insertFileEntry="INSERT INTO"+SPACE+tableName+SPACE+"VALUES"+"("+QUOTE+fBean.getSrc_sys_id()+QUOTE+COMMA+QUOTE+fBean.getSrc_file_id()
			+QUOTE + COMMA + QUOTE + fBean.getSrc_file_name()+QUOTE + COMMA + QUOTE + fBean.getSrc_file_desc()+QUOTE + COMMA + 
			QUOTE + fBean.getSrc_file_type()+QUOTE + COMMA +QUOTE+ fBean.getSrc_file_delimiter()+QUOTE +COMMA +QUOTE + fBean.getTgt_tbl_name()
			+QUOTE + COMMA +QUOTE +fBean.getSrc_sch_loc()+QUOTE + COMMA +QUOTE + fBean.getSrc_hdr_cnt()+QUOTE + COMMA +QUOTE+ fBean.getSrc_trl_cnt()
			+QUOTE + COMMA +QUOTE + fBean.getSrc_cnt_start_idx()+QUOTE + COMMA +QUOTE +fBean.getSrc_cnt_end_idx()+QUOTE + COMMA +QUOTE+ fBean.getData_class_catg()
			+QUOTE + COMMA +QUOTE+ fBean.getMetadata_sts()+QUOTE + COMMA +QUOTE+ dateFormat.format(fBean.getCrtd_dt())+QUOTE + COMMA  + fBean.getUptd_dt() + COMMA +QUOTE+ fBean.getCrtd_by()
			+QUOTE + COMMA +QUOTE+ ""+QUOTE+")"+SEMICOLON;



			LOG.info(insertFileEntry);
			Statement  statement = conn.createStatement();

			statement.execute(insertFileEntry);
			LOG.info("FILE META DATA INSERTED SUCCESFULLY!!");
		}

	}

	public static void insertFieldValue(Connection conn,ArrayList<FieldBean> fieldBeans) throws SQLException{


		String tableName = "mstr_tbl_fld_dtls";
		for(FieldBean fBean : fieldBeans)
		{
			String insertFieldEntry="INSERT INTO"+SPACE+tableName+SPACE+"VALUES"+"("+QUOTE+fBean.getSrc_sys_id()+QUOTE+COMMA+QUOTE+fBean.getSrc_file_id()
			+QUOTE+COMMA+QUOTE+fBean.getFld_pos_num()+QUOTE+COMMA+QUOTE+fBean.getSrc_sch_name()+QUOTE+COMMA+QUOTE+fBean.getSrc_fld_name()
			+QUOTE+COMMA+QUOTE+fBean.getSrc_fld_desc()+QUOTE+COMMA+QUOTE+fBean.getSrc_fld_data_typ()+QUOTE+COMMA+QUOTE+fBean.getTrg_fld_data_typ()
			+QUOTE+COMMA+QUOTE+fBean.getFld_null_flg()+QUOTE+COMMA+QUOTE+fBean.getTgt_tbl_prtn_flg()+QUOTE+COMMA+QUOTE+fBean.getPii_flg()
			+QUOTE+COMMA+QUOTE+fBean.getFxd_fld_strt_idx()+QUOTE+COMMA+QUOTE+fBean.getFxd_fld_end_idx()+QUOTE+COMMA+QUOTE+fBean.getFxd_fld_len()
			+QUOTE+COMMA+QUOTE+dateFormat.format(fBean.getCrtd_dt())+QUOTE+COMMA+fBean.getUptd_dt()+COMMA+QUOTE+fBean.getCrtd_by()+QUOTE+COMMA+QUOTE+""+QUOTE+COMMA+QUOTE+"NA"+QUOTE+")"+SEMICOLON;

			LOG.info(insertFieldEntry);
			Statement	  statement = conn.createStatement();

			statement.execute(insertFieldEntry);
			LOG.info("FIELD META DATA INSERTED SUCCESFULLY!!");
		}

	}

	public static void insertReservoirValue(Connection conn,ArrayList<ReservoirBean> rsvrBeans) throws SQLException {

		String tableName = "mstr_rsvr_dtls";

		for(ReservoirBean bean : rsvrBeans)
		{


			String insertRsvrEntry="INSERT INTO"+SPACE+tableName+SPACE+"VALUES"+"("+QUOTE+bean.getReservoir_id()+QUOTE+COMMA+QUOTE+bean.getResorvoir_name()+QUOTE+COMMA+QUOTE+bean.getReservoir_description()+QUOTE + ")"+SEMICOLON;


			Statement statement = conn.createStatement();

			statement.execute(insertRsvrEntry);
			LOG.info("RESERVOIR DATA INSERTED SUCCESFULLY!!");
		}
	}

	public static void insertBatchValue(Connection conn,ArrayList<BatchBean> batchBeans) throws SQLException {

		String tableName = "mstr_batch_dtls";

		for(BatchBean bean : batchBeans)
		{


			String insertBatchEntry="INSERT INTO"+SPACE+tableName+SPACE+"VALUES" +"("    
					+ QUOTE + bean.getReservoir_id()+ QUOTE + COMMA
					+ QUOTE + bean.getBatch_id()+ QUOTE + COMMA 
					+ QUOTE + bean.getBatch_name()+ QUOTE + COMMA 
					+ QUOTE + bean.getBatch_desc()+ QUOTE + COMMA 
					+ QUOTE + bean.getBatch_type() + QUOTE + COMMA 
					+ QUOTE + bean.getFreequency() + QUOTE + COMMA 
					+ QUOTE + bean.getSchedule_time() + QUOTE + COMMA 
					+ QUOTE + bean.getStatus() + QUOTE + ")"+SEMICOLON;


			Statement statement = conn.createStatement();

			statement.execute(insertBatchEntry);
			LOG.info("BATCH DATA INSERTED SUCCESFULLY!!");
		}
	}

	public static void insertPipelineValue(Connection conn,ArrayList<PipelineBean> pipelineBeans) throws SQLException {

		String tableName = "mstr_pipeline_dtls";

		for(PipelineBean bean : pipelineBeans)
		{


			String insertPipelineEntry="INSERT INTO"+SPACE+tableName+SPACE+"VALUES" +"("    
					+ QUOTE + bean.getReservoir_id()+ QUOTE + COMMA
					+ QUOTE + bean.getBatch_id()+ QUOTE + COMMA 
					+ QUOTE + bean.getPipeline_id()+ QUOTE + COMMA 
					+ QUOTE + bean.getPipeline_name() + QUOTE + COMMA 
					+ QUOTE + bean.getPipeline_type() + QUOTE + COMMA 
					+ QUOTE + bean.getScript_loc() + QUOTE + COMMA 
					+ QUOTE + bean.getScripts() + QUOTE + COMMA 
					+ QUOTE + bean.getUpstream() + QUOTE + COMMA 
					+ QUOTE + bean.getDownstream() + QUOTE + ")"+SEMICOLON;
System.out.println(insertPipelineEntry);
			Statement statement = conn.createStatement();
			statement.executeUpdate(insertPipelineEntry);
			LOG.info("PIPELINE DATA INSERTED SUCCESFULLY!!");
		}


	}

	public static void updateBatchTable(Connection conn, String batch_id , String rsvr_id) throws SQLException {

		String tableName = "mstr_batch_dtls";

		String updateString= "UPDATE" + SPACE + tableName +SPACE+"SET STATUS = 'to be executed' where batch_id=" 
				+ QUOTE + batch_id + QUOTE + "and rsvr_id=" + QUOTE + rsvr_id + QUOTE + SEMICOLON; 	

		Statement statement = conn.createStatement();

		statement.execute(updateString);
		LOG.info("BATCH DEPLOYED SUCCESFULLY!!");
	}

	public static List<SystemBean>  getSystemId(Connection conn) throws SQLException{
		ArrayList<SystemBean> arrSysbean= new ArrayList<SystemBean>();

		String query="Select src_sys_id from mstr_src_sys_dtls";
		Statement statement = conn.createStatement();
		ResultSet rs =statement.executeQuery(query);
		while (rs.next()) {
			String src_sys_id=rs.getString("src_sys_id") ;
			System.out.println("Inside query--value--"+src_sys_id);
			SystemBean systemBean = new SystemBean();
			systemBean.setSrc_sys_id(src_sys_id);
			arrSysbean.add(systemBean);
		}
		return arrSysbean;
	}


	public static void removeBigQuery(Connection conn, String src_sys_id) throws TimeoutException, InterruptedException, SQLException{

		ArrayList<BigQueryTableDetails> arrayDet = new ArrayList<BigQueryTableDetails>();
		BigQueryTableDetails details;

		String query= "select a.tgt_tbl_name,b.tgt_prjt,b.tgt_ds from mstr_file_dtls a inner join mstr_src_tgt_link_tbl b on a.src_sys_id=b.src_sys_id where a.src_sys_id= ?";
		PreparedStatement pstm = conn.prepareStatement(query);		
		pstm.setString(1, src_sys_id);
		ResultSet rs= pstm.executeQuery();
		if(rs!=null && rs.isBeforeFirst()){
			while (rs.next()) {
				// System.out.println("check"+rs.getString("tgt_tbl_name")+rs.getString("tgt_prjt")+rs.getString("tgt_ds"));
				details = new BigQueryTableDetails();
				details.setTable(rs.getString("tgt_tbl_name"));
				details.setProject(rs.getString("tgt_prjt"));
				details.setDataSet(rs.getString("tgt_ds"));
				arrayDet.add(details);
			}}
		//System.out.println("Size Matter"+arrayDet.size());
		for(int i=0;i<arrayDet.size();i++){

			details=arrayDet.get(i);
			if(details!=null) {
				removeTable(details.getTable(),details.getProject(),details.getDataSet()); 
			}
		}
	}

	public static void removeBigQueryRealTime(Connection conn, String src_sys_id) throws TimeoutException, InterruptedException, SQLException{

		ArrayList<BigQueryTableDetails> arrayDet = new ArrayList<BigQueryTableDetails>();
		BigQueryTableDetails details;

		String query= "select a.tgt_prjt,b.tgt_dtst,c.tgt_tbl_name from mstr_src_tgt_link_tbl a inner join mstr_pull_src_sys_dtls b on a.src_sys_id=b.src_sys_id inner join mstr_file_dtls c on b.src_sys_id=c.src_sys_id where b.loadtype='Real Time' and b.src_sys_id= ?";
		details = new BigQueryTableDetails();
		PreparedStatement pstm = conn.prepareStatement(query);		
		pstm.setString(1, src_sys_id);
		ResultSet rs= pstm.executeQuery();

		if(rs!=null && rs.isBeforeFirst()){
			while (rs.next()) {
				// System.out.println("check"+rs.getString("tgt_tbl_name")+rs.getString("tgt_prjt")+rs.getString("tgt_ds"));


				details.setProject(rs.getString(1));
				details.setDataSet(rs.getString(2));
				details.setTable(rs.getString(3));
				details.setCt_table(rs.getString(3)+"_CT");
				details.setVw_table(rs.getString(3)+"_VW");

				arrayDet.add(details);
			}
		}

		for (BigQueryTableDetails table:arrayDet) {

			if(table!=null) {

				removeTable(details.getTable(),details.getProject(),details.getDataSet()); 
			}

		}
	}

	public static void removeTable(String table, String project, String dataSet) {

		if(project!=null && dataSet!=null && table!=null) {
			BigQuery bigquery = BigQueryOptions.getDefaultInstance()
					.toBuilder()
					.setProjectId(project)
					.build()
					.getService();
			Dataset dataset = bigquery.getDataset(dataSet);
			Table tab = dataset.get(table);
			if (tab !=null){
				tab.delete();
			}
		}
	}


	public static void removeSystemEntry(Connection conn, String src_sys_id) throws SQLException {

		String query="DELETE FROM mstr_src_sys_dtls WHERE src_sys_id= ?";
		PreparedStatement pstm = conn.prepareStatement(query);
		pstm.setString(1, src_sys_id);
		pstm.execute();

	}

	public static void removeSystemTargetEntry(Connection conn, String src_sys_id) throws SQLException {

		String query="DELETE FROM mstr_src_tgt_link_tbl WHERE src_sys_id= ?";
		PreparedStatement pstm = conn.prepareStatement(query);
		pstm.setString(1, src_sys_id);
		pstm.execute();
	}

	public static void removeFileEntry(Connection conn, String src_sys_id)throws SQLException {
		String query="DELETE FROM mstr_file_dtls WHERE src_sys_id= ? ";
		PreparedStatement pstm = conn.prepareStatement(query);
		pstm.setString(1, src_sys_id);
		pstm.execute();
	}


	public static void removeFieldEntry(Connection conn, String src_sys_id)throws SQLException {
		String query="DELETE FROM mstr_tbl_fld_dtls WHERE src_sys_id= ? ";
		PreparedStatement pstm = conn.prepareStatement(query);
		pstm.setString(1, src_sys_id);
		pstm.execute();

	}
	public static void removebatchAuditJobTblEntry(Connection conn, String src_sys_id)throws SQLException {
		String query="DELETE FROM batch_audit_job_dtls WHERE src_sys_id= ? ";
		PreparedStatement pstm = conn.prepareStatement(query);
		pstm.setString(1, src_sys_id);
		pstm.execute();

	}

	public static void removebatchAuditTblEntry(Connection conn, String src_sys_id)throws SQLException {
		String query="DELETE FROM batch_audit_tbl WHERE src_sys_id= ? ";
		PreparedStatement pstm = conn.prepareStatement(query);
		pstm.setString(1, src_sys_id);
		pstm.execute();

	}

	public static void removeDataflowCtrlTblEntry(Connection conn, String src_sys_id)throws SQLException {
		String query="DELETE FROM mstr_dataflow_ctrl_tbl WHERE src_sys_id= ? ";
		PreparedStatement pstm = conn.prepareStatement(query);
		pstm.setString(1, src_sys_id);
		pstm.execute();

	}

	public static void removeDataflowFileJobTblEntry(Connection conn, String src_sys_id)throws SQLException {
		String query="DELETE FROM mstr_dataflow_file_job_tbl WHERE src_sys_id= ? ";
		PreparedStatement pstm = conn.prepareStatement(query);
		pstm.setString(1, src_sys_id);
		pstm.execute();

	}

	public static void removeReservoirTblEntry(Connection conn, String rsvr_id)throws SQLException {
		String query="DELETE FROM mstr_rsvr_dtls WHERE rsvr_id= ? ";
		PreparedStatement pstm = conn.prepareStatement(query);
		pstm.setString(1, rsvr_id);
		pstm.execute();

	}

	public static void removeBatchTblEntry(Connection conn, String rsvr_id)throws SQLException {
		String query="DELETE FROM mstr_batch_dtls WHERE rsvr_id= ? ";
		PreparedStatement pstm = conn.prepareStatement(query);
		pstm.setString(1, rsvr_id);
		pstm.execute();

	}

	public static void removePipelineTblEntry(Connection conn, String rsvr_id)throws SQLException {
		String query="DELETE FROM mstr_pipeline_dtls WHERE rsvr_id= ? ";
		PreparedStatement pstm = conn.prepareStatement(query);
		pstm.setString(1, rsvr_id);
		pstm.execute();

	}


	public static List<ReservoirBean>  getReservoirId(Connection conn,HttpServletRequest request) throws SQLException{
		ArrayList<ReservoirBean> arrReservoirBean= new ArrayList<ReservoirBean>();
		HttpSession session = request.getSession();
		UserAccount loginedUser = (UserAccount) session.getAttribute("loginedUser");
		String res=loginedUser.getReservoir_id();
		String query="select rsvr_id from mstr_rsvr_dtls where rsvr_id in ("+res+") and rsvr_desc!='Deleted'";
		Statement statement = conn.createStatement();
		ResultSet rs =statement.executeQuery(query);
		while (rs.next()) {
			String reservoir_id=rs.getString(1) ;
			ReservoirBean reserBean = new ReservoirBean();
			reserBean.setReservoir_id(reservoir_id);
			arrReservoirBean.add(reserBean);

		}
		return arrReservoirBean;
	}

	public static ArrayList<String>  getReservoirId(HttpServletRequest request) {
		ArrayList<String> lstReservoirId= new ArrayList<String>();
		HttpSession session = request.getSession();
		UserAccount loginedUser = (UserAccount) session.getAttribute("loginedUser");
		String res=loginedUser.getReservoir_id();
		try{

			Connection conn=ConnectionUtils.getConnection2();
			String query="select rsvr_id from mstr_rsvr_dtls where rsvr_id in ("+res+") and rsvr_desc!='Deleted'";
			Statement statement = conn.createStatement();
			ResultSet rs =statement.executeQuery(query);
			while (rs.next()) {
				String reservoir_id=rs.getString(1) ;
				lstReservoirId.add(reservoir_id);
			}
			ConnectionUtils.closeQuietly(conn);
			//return lstReservoirId;
		}catch(Exception e){
			return null;	

		}
		return lstReservoirId;
	}


	public static List<BatchBean> getBatchDetails(Connection conn, String reservoir_id) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<BatchBean> arrBatchBean = new ArrayList<BatchBean>();
		String query="select batch_id,batch_name,batch_desc from mstr_batch_dtls where rsvr_id = ? and batch_type='Regular' and status='not started' ";
		PreparedStatement pstm = conn.prepareStatement(query);
		pstm.setString(1, reservoir_id);
		ResultSet rs =pstm.executeQuery();
		while (rs.next()) {
			String batch_id=rs.getString(1);
			String batch_name=rs.getString(2);
			String batch_desc=rs.getString(3);
			BatchBean batchBean= new BatchBean();
			batchBean.setBatch_id(batch_id);
			batchBean.setBatch_name(batch_name);
			batchBean.setBatch_desc(batch_desc);
			arrBatchBean.add(batchBean);
		}
		return arrBatchBean;
	}

	public static List<ReservoirBean> getReservoirDetails(Connection conn,HttpServletRequest request) throws SQLException {
		HttpSession session = request.getSession();
		UserAccount loginedUser = (UserAccount) session.getAttribute("loginedUser");
		String res=loginedUser.getReservoir_id();
		ArrayList<ReservoirBean> arrReservoirBean= new ArrayList<ReservoirBean>();
		String query="select * from mstr_rsvr_dtls where rsvr_id in ("+res+") and rsvr_desc!='Deleted'";
		PreparedStatement pstm = conn.prepareStatement(query);
		ResultSet rs =pstm.executeQuery();
		while (rs.next()) {

			ReservoirBean reserBean = new ReservoirBean();
			reserBean.setReservoir_id(rs.getString(1));
			reserBean.setResorvoir_name(rs.getString(2));
			reserBean.setReservoir_description(rs.getString(3));
			reserBean.setReservoir_url(rs.getString(4));
			reserBean.setReservoir_db(rs.getString(5));
			reserBean.setReservoir_loc(rs.getString(6));
			arrReservoirBean.add(reserBean);
		}
		// TODO Auto-generated method stub
		return arrReservoirBean;
	}

	public static List<ReservoirBean> getReservoirDetails1(Connection conn,HttpServletRequest request,String rid) throws SQLException {
		ArrayList<ReservoirBean> arrReservoirBean= new ArrayList<ReservoirBean>();
		String query="select * from mstr_rsvr_dtls where rsvr_id='"+rid+"' and rsvr_desc!='Deleted'";
		PreparedStatement pstm = conn.prepareStatement(query);
		ResultSet rs =pstm.executeQuery();
		while (rs.next()) {

			ReservoirBean reserBean = new ReservoirBean();
			reserBean.setReservoir_id(rs.getString(1));
			reserBean.setResorvoir_name(rs.getString(2));
			reserBean.setReservoir_description(rs.getString(3));
			reserBean.setReservoir_url(rs.getString(4));
			reserBean.setReservoir_db(rs.getString(5));
			reserBean.setReservoir_loc(rs.getString(6));
			arrReservoirBean.add(reserBean);
		}
		// TODO Auto-generated method stub
		return arrReservoirBean;
	}


	public static void insertReservoirDestails(Connection conn, String reservoirID, String reservoirName, String reservoirDesc, String reservoirurl, String reservoirdb, String reservoirloc) throws SQLException {
		// TODO Auto-generated method stub
		String insertQuery="insert into mstr_rsvr_dtls (rsvr_id,rsvr_name,rsvr_desc,rsvr_url,rsvr_db,rsvr_loc) values (" +
				QUOTE + reservoirID + QUOTE + COMMA +
				QUOTE + reservoirName + QUOTE + COMMA +
				QUOTE + reservoirDesc + QUOTE + COMMA +
				QUOTE + reservoirurl + QUOTE + COMMA +
				QUOTE + reservoirdb + QUOTE + COMMA +
				QUOTE + reservoirloc + QUOTE + ")" + SEMICOLON;
		Statement statement = conn.createStatement();
		statement.execute(insertQuery);

	}

	public static void updateReservoirDetails(Connection conn, String reservoirID, String reservoirName, String reservoirDesc, String reservoirurl, String reservoirdb, String reservoirloc) throws SQLException {
		// TODO Auto-generated method stub
		String updateQuery="update mstr_rsvr_dtls set rsvr_name='" +reservoirName+"',rsvr_desc='" +
				reservoirDesc+"',rsvr_url='" +reservoirurl+"',rsvr_db='"+reservoirdb+"',rsvr_loc='"+reservoirloc+
				"' where rsvr_id='" +reservoirID+"'";
		Statement statement = conn.createStatement();
		statement.execute(updateQuery);
	}

	public static void insertTriggerValue(Connection con, String bucketName, String action) throws SQLException{

		String query = "Select count(*) from cld_func_ex where bucket_name='"+bucketName+"'";

		PreparedStatement pstm = con.prepareStatement(query);
		ResultSet rs =pstm.executeQuery();

		rs.next();

		if(rs.getInt(1) > 0){
			String updateQuery="update cld_func_ex set cld_action='"+action+"', status='to be executed' where bucket_name='"+bucketName+"'";
			Statement statement = con.createStatement();
			statement.executeUpdate(updateQuery);
		}else{

			String insertQuery="insert into cld_func_ex(bucket_name,cld_action,status) values ("+QUOTE + bucketName + QUOTE + COMMA +
					QUOTE + action + QUOTE+ COMMA + QUOTE + "to be executed" + QUOTE+")"+SEMICOLON;
			Statement statement = con.createStatement();
			statement.execute(insertQuery);
		}
	}

	public static Map<String,Timestamp> getDeployedTrigger(Connection conn) throws SQLException {
		String query="select bucket_name,create_time from cld_func_ex where status='Deployed';";

		PreparedStatement pstm = conn.prepareStatement(query);
		ResultSet rs =pstm.executeQuery();
		Map<String,Timestamp> bucketMap = new HashMap();

		while(rs.next()){
			bucketMap.put(rs.getString(1), rs.getTimestamp(2));
		}

		return bucketMap;
	}

	public static ArrayList<TriggerTableBean> getTriggerDetail(Connection conn, String bucketName) throws SQLException {

		String query="select file_name, file_type, create_timestamp from mstr_trig_tbl where bucket_name='"+bucketName+"'";

		PreparedStatement pstm = conn.prepareStatement(query);
		ResultSet rs =pstm.executeQuery();

		ArrayList<TriggerTableBean> triggerBeanList = new ArrayList<>();

		while(rs.next()){
			TriggerTableBean triggerTableBean = new TriggerTableBean();
			triggerTableBean.setBucketName(rs.getString(1));
			triggerTableBean.setFileType(rs.getString(2));
			triggerTableBean.setTimestamp(rs.getTimestamp(3));

			triggerBeanList.add(triggerTableBean);
		}

		return triggerBeanList;
	}

	public static ArrayList<String> getConnectionMetadata(Connection conn) throws SQLException {
		String query="select src_sys_id from mstr_pull_src_sys_dtls";
		ArrayList<String> srcSystemList = new ArrayList<>();
		try{
			PreparedStatement pstm = conn.prepareStatement(query);
			ResultSet rs =pstm.executeQuery();

			while(rs.next()){
				srcSystemList.add(rs.getString(1));
			}
		}catch(SQLException e){
			throw e;
		}
		finally {
			conn.close();
		}
		return srcSystemList;
	}


	public static PullConnectionBean getConnectionDetail(Connection conn, String srcSystemId) throws SQLException {
		String query="select src_sys_id,src_sys_type,host,uname,pswd,port,db_name from mstr_pull_src_sys_dtls where src_sys_id='"+srcSystemId+"'";
		PullConnectionBean pullConnectionBean =null;

		try{
			PreparedStatement pstm = conn.prepareStatement(query);
			ResultSet rs =pstm.executeQuery();

			while(rs.next()){
				pullConnectionBean = new PullConnectionBean();
				pullConnectionBean.setSrc_system_id(rs.getString(1));
				pullConnectionBean.setSrc_sys_type(rs.getString(2));
				pullConnectionBean.setHost(rs.getString(3));
				pullConnectionBean.setUname(rs.getString(4));
				pullConnectionBean.setPwd(rs.getString(5));
				pullConnectionBean.setPort(rs.getString(6));
				pullConnectionBean.setDbName(rs.getString(7));
			}
		}catch(SQLException e){
			LOG.error("Error occured :"+e);
			throw e;
		}
		finally {
			conn.close();
		}

		return pullConnectionBean;
	}


	public static ArrayList<String> getEeid(Connection conn) throws SQLException {
		// TODO Auto-generated method stub
		String query="select e_id from mstr_ee_dtls where status='not deployed'";
		ArrayList<String> eids = new ArrayList<>();
		PreparedStatement pstm = conn.prepareStatement(query);
		ResultSet rs =pstm.executeQuery();
		if(rs.isBeforeFirst()){
			while (rs.next()){
				eids.add(rs.getString(1));
			}
		}
		return eids;
	}

	public static ArrayList<EeBean> getEntitlements(Connection conn)throws SQLException {
		ArrayList<String> groupEmail = new ArrayList<>();
		ArrayList<EeBean> beans=new ArrayList<EeBean>();
		String query="select e_id,bq_dataset,usr_grp,entitlement_name,description from mstr_ee_dtls";
		PreparedStatement pstm = conn.prepareStatement(query);
		ResultSet rs =pstm.executeQuery();
		if(rs.isBeforeFirst()){
			while (rs.next()){
				EeBean bean= new EeBean();

				bean.setDataset(rs.getString(2));
				bean.setEntitlement_name(rs.getString(4));
				bean.setDescription(rs.getString(5));

				String tables="";
				String query2="select distinct bq_tbl from mstr_ee_tbl_fld_dtls where e_id=?";
				PreparedStatement pstm2 = conn.prepareStatement(query2);
				pstm2.setString(1, rs.getString(1));
				ResultSet rs2 =pstm2.executeQuery();
				if(rs2.isBeforeFirst())
				{
					while (rs2.next()){
						tables=tables+rs2.getString(1)+","+" ";
					}
				}

				if(tables != null && !tables.isEmpty()){
					tables=tables.substring(0, tables.length()-2);
					bean.setTables(tables);

				}


				String query1="select group_email from cdg_master.group_master where group_id in ("+String.join(",", rs.getString(3))+")";

				PreparedStatement pstm1 = conn.prepareStatement(query1);
				ResultSet rs1 = pstm1.executeQuery();
				if (rs1.isBeforeFirst()){
					while (rs1.next()){
						groupEmail.add(rs1.getString(1));
					}
				}
				bean.setUser_grp(groupEmail);
				beans.add(bean);
			}
		}
		return beans;
	}

	*//*********** Business Glossary *********************//*

	public static List<BusinessGlossaryBean>  viewBusinessCatalogueInfo(Connection conn,HttpServletRequest request) throws SQLException{
		String query=JuniperConstants.GLOSSARY_VIEW_QUERY;
		PreparedStatement pstm = conn.prepareStatement(query);
		ResultSet rs =pstm.executeQuery();

		ArrayList<BusinessGlossaryBean> businessBeanList = new ArrayList<>();

		while(rs.next()){
			BusinessGlossaryBean busniessBean= new BusinessGlossaryBean();
			busniessBean.setLob_id(rs.getInt(1));
			busniessBean.setLob_name(rs.getString(2));
			busniessBean.setBde_id(rs.getInt(3));
			busniessBean.setBde_name(rs.getString(4));
			busniessBean.setBde_desc(rs.getString(5));
			busniessBean.setStatus(rs.getString(6));
			busniessBean.setApproved_by(rs.getString(7));

			businessBeanList.add(busniessBean);
		}

		return businessBeanList;
	}

	public static void insertBDElement(Connection conn,ArrayList<BusinessGlossaryBean> businessBean) throws SQLException {


		String tableName = "gl_bde_master";
		String databaseName="cdg_master";

		for(BusinessGlossaryBean bean : businessBean)
		{
			String insertBDEEntry="INSERT INTO"+SPACE+databaseName+"."+tableName+SPACE+"(bde_name,bde_desc,created_by,status,lob_id)"+"VALUES" +"("    

							+ QUOTE + bean.getBde_name()+ QUOTE + COMMA
							+ QUOTE + bean.getBde_desc()+ QUOTE + COMMA
							+ QUOTE + bean.getCreated_by()+ QUOTE + COMMA
							//+ QUOTE + bean.getCreated_date()+ QUOTE + COMMA
							+ QUOTE + bean.getStatus()+ QUOTE + COMMA							
							+ QUOTE + bean.getLob_id() +  QUOTE + ")"+SEMICOLON;

			Statement statement = conn.createStatement();

			statement.execute(insertBDEEntry);
			LOG.info("BDElement DATA INSERTED SUCCESFULLY!!");
		}


	}

	public static ArrayList<BusinessGlossaryBean> getBDGlossaryDetail(Connection conn, String labelKey) throws SQLException {

		StringBuffer stringBuffer= new StringBuffer();
		for(String label : labelKey.split(",")){
			stringBuffer.append("'"+label+"',");
		}

		stringBuffer.setLength(stringBuffer.length()-1);

		String query="select dataset_name, table_name, bq_component_name,bq_component_value,bq_component_type from cdg_master.gl_search_output_result where search_word IN ("+stringBuffer.toString()+")";
		LOG.info(query);
		PreparedStatement pstm = conn.prepareStatement(query);
		ResultSet rs =pstm.executeQuery();

		ArrayList<BusinessGlossaryBean> glossaryBeanList = new ArrayList<>();

		while(rs.next()){
			BusinessGlossaryBean glossaryBean = new BusinessGlossaryBean();
			glossaryBean.setDataset_name(rs.getString(1));
			glossaryBean.setTable_name(rs.getString(2));
			glossaryBean.setBq_componenet_name(rs.getString(3));
			glossaryBean.setBq_component_value(rs.getString(4));
			glossaryBean.setBq_componenet_type(rs.getString(5));					
			glossaryBeanList.add(glossaryBean);
		}

		return glossaryBeanList;
	}


	public static void updateApproval(Connection conn,ArrayList<BusinessGlossaryBean> businessBean) throws SQLException {


		String tableName = "gl_bde_master";
		String databaseName="cdg_master";

		for(BusinessGlossaryBean bean : businessBean)
		{


			String updateString= "UPDATE" + SPACE +databaseName+"."+tableName+SPACE+"SET STATUS ='"+ bean.getStatus() +"',approved_by='"+ bean.getApproved_by() +"' where bde_id=" 
					+ QUOTE + bean.getBde_id() + QUOTE +  SEMICOLON; 	

			Statement statement = conn.createStatement();

			statement.execute(updateString);
			LOG.info("STATUS  UPDATED SUCCESFULLY!!");
		}
	}




	public static void insertMappedData(Connection conn,ArrayList<BusinessGlossaryBean> businessBean) throws SQLException {


		String tableName = "gl_bde_mapping";
		String databaseName="cdg_master";

		for(BusinessGlossaryBean bean : businessBean)
		{
			String insertBDEEntry="INSERT INTO"+SPACE+databaseName+"."+tableName+SPACE+"(bde_id,dataset_name,table_name,column_name,created_by)"+"VALUES" +"("    

							+ QUOTE + bean.getBde_id()+ QUOTE + COMMA
							+ QUOTE + bean.getDataset_name()+ QUOTE + COMMA
							+ QUOTE + bean.getTable_name()+ QUOTE + COMMA
							+ QUOTE + bean.getColumn_name()+ QUOTE + COMMA
							+ QUOTE + bean.getCreated_by()+ QUOTE + COMMA
							+ ")"+SEMICOLON;

			LOG.info(insertBDEEntry);
			Statement statement = conn.createStatement();

			LOG.info(insertBDEEntry);
			System.out.println("BDElement DATA INSERTED SUCCESFULLY!!");
		}

	}
	public static ArrayList<String> getEntitlementsDataset(Connection connection) throws SQLException {

		ArrayList<String> entitlementList = new ArrayList<>();
		String query = "select distinct(bq_dataset) from mstr_ee_dtls";

		Statement statement = connection.createStatement();

		ResultSet rs = statement.executeQuery(query);

		while(rs.next()){
			entitlementList.add(rs.getString(1));
		}

		return entitlementList;
	}

	public static ArrayList<String> getEntitlementName(String parameter, Connection connection) throws SQLException {
		ArrayList<String> entitlementName = new ArrayList<>();
		String query = "select entitlement_name from mstr_ee_dtls where bq_dataset='"+parameter+"'";

		Statement statement = connection.createStatement();

		ResultSet rs = statement.executeQuery(query);

		while(rs.next()){
			entitlementName.add(rs.getString(1));
		}

		return entitlementName;

	}

	public static void deleteEntitlement(String entitlement, String dataset, Connection connection) throws SQLException {
		String query = "delete from mstr_ee_dtls where bq_dataset='"+entitlement+"' and bq_dataset='"+dataset+"'";
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
	}
	

	public static List<AirflowBean> getAirflowDetails(Connection conn,HttpServletRequest request) throws SQLException {
		HttpSession session = request.getSession();
		UserAccount loginedUser = (UserAccount) session.getAttribute("loginedUser");
		
		ArrayList<AirflowBean> arrAFBean= new ArrayList<AirflowBean>();
		String query="select * from etl_pipeline.mstr_airflow_dtls";
		PreparedStatement pstm = conn.prepareStatement(query);
		ResultSet rs =pstm.executeQuery();
		while (rs.next()) {

			AirflowBean reserBean = new AirflowBean();
			reserBean.setAirflow_id(rs.getInt(1));
			reserBean.setUrl(rs.getString(2));
			reserBean.setLocation(rs.getString(3));
			reserBean.setDatabase(rs.getString(4));
			reserBean.setStatus(rs.getString(5));
			
			arrAFBean.add(reserBean);
		}
		// TODO Auto-generated method stub
		return arrAFBean;
	}
	

	public static List<ReservoirBean> getReservoirInfo(Connection conn,HttpServletRequest request) throws SQLException {
		HttpSession session = request.getSession();
		UserAccount loginedUser = (UserAccount) session.getAttribute("loginedUser");
		String res=loginedUser.getReservoir_id();
		ArrayList<ReservoirBean> arrReservoirBean= new ArrayList<ReservoirBean>();
		String query="select rsvr_id  from mstr_rsvr_dtls where rsvr_id in ("+res+")";
		PreparedStatement pstm = conn.prepareStatement(query);
		ResultSet rs =pstm.executeQuery();
		while (rs.next()) {

			ReservoirBean reserBean = new ReservoirBean();
			reserBean.setReservoir_id(rs.getString(1));					
			arrReservoirBean.add(reserBean);
		}
		// TODO Auto-generated method stub
		return arrReservoirBean;
	}
	
	public static List<AirflowBean> getAirflowInfo(Connection conn,HttpServletRequest request) throws SQLException {
	
		List<AirflowBean> arrReservoirBean= new ArrayList<AirflowBean>();
		String query="select airflow_id,airflow_url,airflow_loc,airflow_db from etl_pipeline.mstr_airflow_dtls where status='unassigned'";
		PreparedStatement pstm = conn.prepareStatement(query);
		ResultSet rs =pstm.executeQuery();
		while (rs.next()) {

			AirflowBean reserBean = new AirflowBean();
			reserBean.setAirflow_id(rs.getInt(1));	
			reserBean.setUrl(rs.getString(2));	
			reserBean.setLocation(rs.getString(3));	
			reserBean.setDatabase(rs.getString(4));	
			arrReservoirBean.add(reserBean);
		}
		// TODO Auto-generated method stub
		return arrReservoirBean;
	}
}


*/