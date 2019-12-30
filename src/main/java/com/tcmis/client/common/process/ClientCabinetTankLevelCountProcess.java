package com.tcmis.client.common.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.common.beans.TankLevelCountBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.DateHandler;

public class ClientCabinetTankLevelCountProcess extends GenericProcess {
	
	Log log = LogFactory.getLog(this.getClass());
	
	public ClientCabinetTankLevelCountProcess(String client, Locale locale) {
		super(client, locale);
		// TODO Auto-generated constructor stub
	}
	
	public ClientCabinetTankLevelCountProcess(String client) {
		super(client);
	}

	public HashMap<String, Object> insertTankLevelCount(Collection<TankLevelCountBean> tankLevelCountBeanCol, PersonnelBean pbean, String uploadSequence) throws BaseException,Exception {
		DbManager dbManager = new DbManager(getClient(),locale.toString());
	    Connection connection = dbManager.getConnection();
	    HashMap<String, Object> returnMessage = new HashMap<String, Object>();
		StringBuilder query = new StringBuilder();
		BigDecimal nextupLoadSeq = new BigDecimal(uploadSequence);//dont take a new sequence id if the insert failed. Try again with the same one.
		BigDecimal binId = new BigDecimal(0);
		String lastQtyOnHand = null;
		
		try {
			connection.setAutoCommit(false);
			returnMessage.put("uploadSequence", nextupLoadSeq.toString());
			String deleteQuery = "delete from work_area_bin_count_stage";
            int count1 = factory.deleteInsertUpdate(deleteQuery,connection);            
            boolean queryGenerated = false;
			for(TankLevelCountBean bean : tankLevelCountBeanCol) {
				 if(bean.getCountDatetime() == null)
					 break;
				String afterReplLevelQty = (bean.getAfterReplLevelQty() == null) ? "''" : "'"+bean.getAfterReplLevelQty()+"'";
				query = new StringBuilder();	
				query.append("INSERT INTO WORK_AREA_BIN_COUNT_STAGE (UPLOAD_SEQUENCE, BIN_ID, COUNT_DATETIME, COMPANY_ID,");
				query.append("COUNT_TYPE,PERSONNEL_ID, COUNT_QUANTITY,COUNT_SOURCE, AFTER_REPL_LEVEL_QTY)");
				query.append(" VALUES (");
				query.append(nextupLoadSeq + " ," + bean.getBinId().intValue() + ", " + DateHandler.getOracleToDateFunction(bean.getCountDatetime()) +
						",'" + bean.getCompanyId() + "','" + bean.getCountType() + "'," + pbean.getPersonnelId() +
						"," + bean.getCountQuantity() + ",'LEVEL_COUNT_ENTRY_FORM'," + afterReplLevelQty + ")");

				factory.deleteInsertUpdate(query.toString(),connection);
				queryGenerated = true;	 				
				lastQtyOnHand = bean.getCountQuantity().toString();
				binId = bean.getBinId();
			}

			boolean errorInProcedureCall = false;
			if (queryGenerated){				

				connection.commit();
	            //String errorRetrivalQuerytemp = "select count(*) from work_area_bin_count_stage where UPLOAD_SEQUENCE = "+ nextupLoadSeq ;
                //String count = factory.selectSingleValue(errorRetrivalQuerytemp,connection);
                //log.debug("before procedure call no of rows in table - " + count);
				
				Vector inArgs = new Vector();	
				inArgs.add(nextupLoadSeq);
				Vector outArgs = new Vector(1);
	            outArgs.add(new Integer(java.sql.Types.VARCHAR));	            
	            Vector errorV = (Vector) factory.doProcedure(connection, "PKG_WORK_AREA_BIN_COUNT.P_COPY_STAGE_TO_COUNT", inArgs, outArgs);
	            if (errorV != null && errorV.size() > 0 && errorV.get(0) != null) {
	                log.debug("Error on execution of the first procedure PKG_WORK_AREA_BIN_COUNT.P_COPY_STAGE_TO_COUNT - " + errorV.get(0));
	                returnMessage.put("tcmISError", (String)errorV.get(0));
	                //errorRetrivalQuerytemp = "select count(*) from work_area_bin_count_stage where UPLOAD_SEQUENCE = "+ nextupLoadSeq ;
	                //count = factory.selectSingleValue(errorRetrivalQuerytemp,connection);
	                //log.debug("after first procc call - number of rows in table - " + count);
	                errorInProcedureCall = true;
	            } else {
	            	
	            	Vector inArgsNext = new Vector();	
					inArgsNext.add(nextupLoadSeq);//A_UPLOAD_SEQUENCE					
					Vector outArgsNext = new Vector(1);
		            outArgsNext.add(new Integer(java.sql.Types.VARCHAR));//A_ERROR 
		            Vector inOptArgsNext = new Vector();
		            inOptArgsNext.add(binId);//A_BIN_ID
		            inOptArgsNext.add("N");//A_TEST_ON_HAND_INVENTORY 
		            inOptArgsNext.add("Y");//A_PROCESS_COUNT 
		            //Vector errorNext = (Vector) factory.doProcedure(connection, "PKG_WORK_AREA_BIN_COUNT.P_PROCESS_COUNT", inArgsNext, outArgsNext);
		            Vector errorNext = (Vector) factory.doProcedure(connection, "PKG_WORK_AREA_BIN_COUNT.P_PROCESS_PART_COUNT", inArgsNext, outArgsNext, inOptArgsNext);
		            if (errorNext != null && errorNext.size() > 0 && errorNext.get(0) != null && !"ok".equalsIgnoreCase((String) errorNext.get(0))) {
		            	log.debug("Error on execution of the second procedure PKG_WORK_AREA_BIN_COUNT.P_PROCESS_COUNT - " + errorNext.get(0));
		            	returnMessage.put("tcmISError", (String) errorNext.get(0));
		            	errorInProcedureCall = true;
		            	//errorRetrivalQuerytemp = "select count(*) from work_area_bin_count_stage where UPLOAD_SEQUENCE = "+ nextupLoadSeq ;
		                //count = factory.selectSingleValue(errorRetrivalQuerytemp,connection);
		                //log.debug("after second proc call - number of rows in table - " + count);
		            }
	            }
	            if (errorInProcedureCall) {
	            	String errorRetrivalQuery = "select UPLOAD_SEQUENCE, BIN_ID, COUNT_DATETIME, COUNT_TYPE, COUNT_QUANTITY, " +
	            			    " COMPANY_ID, COUNT_SOURCE, PERSONNEL_ID, AFTER_REPL_LEVEL_QTY, PROCESSING_STATUS, PROCESS_ERROR_MSG " +
                				" from WORK_AREA_BIN_COUNT_STAGE where UPLOAD_SEQUENCE = "+ nextupLoadSeq ;
	                factory.setBean(new TankLevelCountBean());	
	                Collection<TankLevelCountBean> errTankCol =  factory.selectQuery(errorRetrivalQuery,connection);
	                Iterator iter = errTankCol.iterator();
	                while (iter.hasNext()) {
	                	TankLevelCountBean errbean = (TankLevelCountBean)iter.next();
	                	//log.debug("Error on table - " + errbean.getProcessErrorMsg() + " and " + errbean.getBinId() + " and "+ errbean.getUploadSequence());
	                }
	                returnMessage.put("errTankCol", errTankCol);
	            } else {
	            	returnMessage.put("lastQtyOnHand",lastQtyOnHand);
	            }
			}
			connection.commit();
			connection.setAutoCommit(true);
		}
		catch (Exception e) {
			log.error("Error reading inserted data:" + e.getMessage());	
			returnMessage.put("tcmISError",e.getMessage());
		} finally {
			connection.commit();
			connection.setAutoCommit(true);
			dbManager.returnConnection(connection);
		}
		return returnMessage;
	}
	
    public BigDecimal getNextUpLoadSeq() throws BaseException {
		BigDecimal b = null;
		Connection connection = null;
		DbManager dbManager = new DbManager(getClient(), this.getLocale());
		try {
			connection = dbManager.getConnection();
			b = new BigDecimal("" + new SqlManager().getOracleSequence(connection, "upload_sequence"));
		}
		finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
		}
		return b;
	}
    
    public String getLevelUnit(TankLevelCountBean inputBean) throws BaseException {
    	String levelUnit = "";
		Connection connection = null;
		DbManager dbManager = new DbManager(getClient(), this.getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);
		try {
			connection = dbManager.getConnection();
			StringBuilder query = new StringBuilder("select size_unit from cabinet_part_inventory where ");
			query.append("company_id='").append(inputBean.getCompanyId()).append("' ");
			query.append("and bin_id=").append(inputBean.getBinId());
			levelUnit = factory.selectSingleValue(query.toString());
		}
		finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
		}
		return levelUnit;
    }
	
}
