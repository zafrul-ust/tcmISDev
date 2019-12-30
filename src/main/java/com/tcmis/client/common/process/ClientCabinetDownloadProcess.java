package com.tcmis.client.common.process;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.internal.hub.beans.CabinetLabelInputBean;

public class ClientCabinetDownloadProcess extends GenericProcess {
	Log log = LogFactory.getLog(this.getClass());

	public ClientCabinetDownloadProcess(String client) {
		super(client);
	}

	public ClientCabinetDownloadProcess(String client, Locale locale) {
		super(client, locale);
	}

	public String getLoginData(CabinetLabelInputBean input) throws BaseException, Exception {
		Connection conn = null;
		StringBuilder csv = new StringBuilder();
		
		try{
			DbManager dbManager = new DbManager(getClient());
			conn = dbManager.getConnection();
			Statement st = conn.createStatement();

			// build the query
			StringBuilder query = new StringBuilder("select rpad(LOGON_ID,20,' ') LOGON_ID,lpad(PERSONNEL_ID,8,'0') PERSONNEL_ID ");
			query.append("from personnel ");
			query.append("where personnel_id in ");
			query.append("(select personnel_id from user_group_member where user_group_id = 'Scanner Functions' and facility_id='").append(input.getFacilityId()).append("') ");
			query.append(" and company_id = 'Radian' order by LOGON_ID");
			
			boolean hasData = false;
			ResultSet rs = st.executeQuery(query.toString());			
			
			while(rs.next()){
				hasData = true;
				
				csv.append(rs.getString(1)).append(","); // LOGON_ID
				csv.append(rs.getString(2)).append(","); // PERSONNEL_ID
			}
			
			if(!hasData)
				csv.append("No Data Found");
			
			rs.close();	
			st.close();
		}
		catch(Exception ex){ 
			ex.printStackTrace();
		}
		finally{
			conn.close();
		}
		
		return csv.toString();
	}
	
	public String getBinData(CabinetLabelInputBean input) throws BaseException, Exception {
		Connection conn = null;
		StringBuilder csv = new StringBuilder();
		
		try{
			DbManager dbManager = new DbManager(getClient());
			conn = dbManager.getConnection();
			Statement st = conn.createStatement();
			
			String[] cabinetIds = input.getCabinetIdArray();
			
			// build the query
			StringBuilder query = new StringBuilder("select lpad(BIN_ID,8,'0') BIN_ID1,BIN_ID,lpad(CABINET_ID,8,'0') CABINET_ID1,CABINET_ID,BIN_NAME,lpad(ITEM_ID,8,'0') ITEM_ID1,ITEM_ID,rpad(COMPANY_ID,30,' ') COMPANY_ID1,STATUS,COUNT_TYPE ");
			query.append("from cabinet_bin_item_view ");
			query.append("where count_type in ('RECEIPT_ID', 'RECEIPT', 'ADVRECEIPT') ");
			
			if(cabinetIds.length > 0 && !cabinetIds[0].equals("")) {
				query.append("and CABINET_ID in (");
				
				// get a list of work areas to search
				for(int i=0; i<cabinetIds.length; i++){
					query.append(cabinetIds[0]);
					
					if(i != cabinetIds.length-1)
						query.append(",");
				}
				
				query.append(") ");
			}
			query.append("and item_id is not null ");
			query.append("union all select lpad(BIN_ID,8,'0') BIN_ID1,BIN_ID,lpad(CABINET_ID,8,'0') CABINET_ID1,CABINET_ID,BIN_NAME,lpad(decode(CATALOG_DESC,'Haas Products', item_id, ");
			query.append("FX_BEST_BIN (bin_id)),8,'0') ITEM_ID1,decode(CATALOG_DESC,'Haas Products', item_id, FX_BEST_BIN (bin_id)) item_id,rpad(COMPANY_ID,30,' ') COMPANY_ID1,STATUS,COUNT_TYPE ");
			query.append("from cabinet_bin_item_view ");
			query.append("where count_type = 'ITEM_ID' and CPIG_STATUS in ('A','D') ");
			
			if(cabinetIds.length > 0 && !cabinetIds[0].equals("")) {
				query.append("and CABINET_ID in (");
				
				// get a list of work areas to search
				for(int i=0; i<cabinetIds.length; i++){
					query.append(cabinetIds[0]);
					
					if(i != cabinetIds.length-1)
						query.append(",");
				}
				
				query.append(") ");
			}
			query.append("and item_id is not null ");

			query.append("union all select lpad(BIN_ID,8,'0') BIN_ID1,BIN_ID,lpad(CABINET_ID,8,'0') CABINET_ID1,CABINET_ID,BIN_NAME,lpad(ITEM_ID,8,'0') ITEM_ID1,ITEM_ID,rpad(COMPANY_ID,30,' ') COMPANY_ID1,STATUS,COUNT_TYPE ");
			query.append("from cabinet_bin_item_view ");
			query.append("where count_type = 'KanBan' and CPIG_STATUS = 'A' ");
			
			if(cabinetIds.length > 0 && !cabinetIds[0].equals("")) {
				query.append("and CABINET_ID in (");
				
				// get a list of work areas to search
				for(int i=0; i<cabinetIds.length; i++){
					query.append(cabinetIds[0]);
					
					if(i != cabinetIds.length-1)
						query.append(",");
				}
				
				query.append(") ");
			}
			query.append("and item_id is not null order by CABINET_ID,BIN_ID,ITEM_ID asc");
			
			boolean hasData = false;
			ResultSet rs = st.executeQuery(query.toString());			
			
			while(rs.next()){
				hasData = true;
				
				csv.append(rs.getString(1)).append(","); // BIN_ID1
				csv.append(rs.getString(2)).append(","); // BIN_ID
				csv.append(rs.getString(3)).append(","); // CABINET_ID1
				csv.append(rs.getString(4)).append(","); // CABINET_ID
				csv.append(rs.getString(5)).append(","); // BIN_NAME
				csv.append(rs.getString(6)).append(","); // ITEM_ID1
				csv.append(rs.getString(7)).append(","); // ITEM_ID
				csv.append(rs.getString(8)).append("\n"); // COMPANY_ID1
			}
			
			if(!hasData)
				csv.append("No Data Found");
			
			rs.close();	
			st.close();
		}
		catch(Exception ex){ 
			ex.printStackTrace();
		}
		finally{
			conn.close();
		}
		
		return csv.toString();
	}
	
	public String getReceiptData(CabinetLabelInputBean input) throws BaseException, Exception {
		Connection conn = null;
		StringBuilder csv = new StringBuilder();
		
		try{
			DbManager dbManager = new DbManager(getClient());
			conn = dbManager.getConnection();
			Statement st = conn.createStatement();
			
			String[] cabinetIds = input.getCabinetIdArray();
			
			// build the query
			StringBuilder query = new StringBuilder("select distinct lpad(RECEIPT_ID,8,'0') RECEIPT_ID1,RECEIPT_ID,lpad(ITEM_ID,8,'0') ITEM_ID1,ITEM_ID ");
			query.append("from cabinet_receipt_item_view ");
			query.append("where ");
			
			if(cabinetIds.length > 0 && !cabinetIds[0].equals("")) {
				query.append("CABINET_ID in (");
				
				// get a list of work areas to search
				for(int i=0; i<cabinetIds.length; i++){
					query.append(cabinetIds[0]);
					
					if(i != cabinetIds.length-1)
						query.append(",");
				}
				
				query.append(") and ");
			}
			query.append("item_id is not null order by RECEIPT_ID ASC");
			
			boolean hasData = false;
			ResultSet rs = st.executeQuery(query.toString());			
			
			while(rs.next()){
				hasData = true;
				
				csv.append(rs.getString(1)).append(","); // RECEIPT_ID1
				csv.append(rs.getString(2)).append(","); // RECEIPT_ID
				csv.append(rs.getString(3)).append(","); // ITEM_ID1
				csv.append(rs.getString(4)).append(","); // ITEM_ID
			}
			
			if(!hasData)
				csv.append("No Data Found");
			
			rs.close();	
			st.close();
		}
		catch(Exception ex){ 
			ex.printStackTrace();
		}
		finally{
			conn.close();
		}
		
		return csv.toString();
	}
	
	public String getLegacyReceiptData(CabinetLabelInputBean input) throws BaseException, Exception {
		Connection conn = null;
		StringBuilder csv = new StringBuilder();
		
		try{
			DbManager dbManager = new DbManager(getClient());
			conn = dbManager.getConnection();
			Statement st = conn.createStatement();
			
			String[] cabinetIds = input.getCabinetIdArray();
			
			// build the query
			StringBuilder query = new StringBuilder("select distinct CUSTOMER_RECEIPT_ID,lpad(RECEIPT_ID,8,'0') RECEIPT_ID1,RECEIPT_ID,lpad(ITEM_ID,8,'0') ITEM_ID1,ITEM_ID ");
			query.append("from cabinet_receipt_item_view ");
			query.append("where ");

			if(cabinetIds.length > 0 && !cabinetIds[0].equals("")) {
				query.append("CABINET_ID in (");
				
				// get a list of work areas to search
				for(int i=0; i<cabinetIds.length; i++){
					query.append(cabinetIds[0]);
					
					if(i != cabinetIds.length-1)
						query.append(",");
				}
				
				query.append(") and ");
			}
			
			query.append("CUSTOMER_RECEIPT_ID is not null and item_id is not null order by CUSTOMER_RECEIPT_ID ASC");
			
			boolean hasData = false;
			ResultSet rs = st.executeQuery(query.toString());			
			
			while(rs.next()){
				hasData = true;
				
				csv.append(rs.getString(1)).append(","); // CUSTOMER_RECEIPT_ID
				csv.append(rs.getString(2)).append(","); // RECEIPT_ID1
				csv.append(rs.getString(3)).append(","); // RECEIPT_ID
				csv.append(rs.getString(4)).append(","); // ITEM_ID1
				csv.append(rs.getString(5)).append(","); // ITEM_ID
			}
			
			if(!hasData)
				csv.append("No Data Found");
			
			rs.close();	
			st.close();
		}
		catch(Exception ex){ 
			ex.printStackTrace();
		}
		finally{
			conn.close();
		}
		
		return csv.toString();
	}
	
	public String getWorkAreaData(CabinetLabelInputBean input) throws BaseException, Exception {
		Connection conn = null;
		StringBuilder csv = new StringBuilder();
		
		try{
			DbManager dbManager = new DbManager(getClient());
			conn = dbManager.getConnection();
			Statement st = conn.createStatement();
			
			String[] cabinetIds = input.getCabinetIdArray();
			
			// build the query
			StringBuilder query = new StringBuilder("select lpad(BIN_ID,8,'0') BIN_ID1, BIN_ID,lpad(CABINET_ID,8,'0') CABINET_ID1,CABINET_ID,BIN_NAME,lpad(ITEM_ID,8,'0') ITEM_ID1,ITEM_ID,rpad(COMPANY_ID,30,' ') COMPANY_ID1 ");
			query.append("from cabinet_bin_item_view ");
			query.append(" where ");

			if(cabinetIds.length > 0 && !cabinetIds[0].equals("")) {
				query.append("CABINET_ID in (");
				
				// get a list of work areas to search
				for(int i=0; i<cabinetIds.length; i++){
					query.append(cabinetIds[0]);
					
					if(i != cabinetIds.length-1)
						query.append(",");
				}
				
				query.append(") and ");
			}
			query.append("item_id is not null and status = 'A' order by BIN_ID,ITEM_ID asc");
			
			boolean hasData = false;
			ResultSet rs = st.executeQuery(query.toString());			
			
			while(rs.next()){
				hasData = true;
				
				csv.append(rs.getString(1)).append(","); // BIN_ID1
				csv.append(rs.getString(2)).append(","); // BIN_ID
				csv.append(rs.getString(3)).append(","); // CABINET_ID1
				csv.append(rs.getString(4)).append(","); // CABINET_ID
				csv.append(rs.getString(5)).append(","); // BIN_NAME
				csv.append(rs.getString(6)).append(","); // ITEM_ID1
				csv.append(rs.getString(7)).append(","); // ITEM_ID
				csv.append(rs.getString(8)).append("\n"); // COMPANY_ID1
			}
			
			if(!hasData)
				csv.append("No Data Found");
			
			rs.close();	
			st.close();
		}
		catch(Exception ex){ 
			ex.printStackTrace();
		}
		finally{
			conn.close();
		}
		
		return csv.toString();
	}
}
