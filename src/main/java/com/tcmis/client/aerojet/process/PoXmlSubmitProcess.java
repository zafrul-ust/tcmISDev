package com.tcmis.client.aerojet.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.order.beans.CustomerPoPreStageBean;
import com.tcmis.client.order.factory.CustomerPoPreStageBeanFactory;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.StringHandler;

public class PoXmlSubmitProcess extends GenericProcess {
    Log log = LogFactory.getLog(this.getClass());
    Connection connection = null;

    public PoXmlSubmitProcess(String client, Locale locale) {
        super(client, locale);
    }

    /* Need this for some old code that is dependent */
    public PoXmlSubmitProcess(String client) {
        super(client);
    }

    public void setConnection (Connection conn) {
        this.connection = conn;
    }

    public String processData(Collection customerPoPreStageBeanCol, BigDecimal loadId) throws BaseException {
    	String errorMessage = "OK";
    	int numOfRowsInserted = 0;
    	try {
            connection = dbManager.getConnection();

			Iterator iterator = customerPoPreStageBeanCol.iterator();        
	        CustomerPoPreStageBeanFactory custFactory = new CustomerPoPreStageBeanFactory(dbManager);
	        while (iterator.hasNext()) {
	          CustomerPoPreStageBean customerPoPreStageBean = (CustomerPoPreStageBean) iterator.next();
	          numOfRowsInserted += custFactory.insert(customerPoPreStageBean, connection);
	        }
	        log.debug("No Of rows inserted in customerPoPreStage :" + numOfRowsInserted);
	        //call the procedure if data successfully inserted
            if (numOfRowsInserted > 0) {            	
                Vector inArgs = new Vector(1);
                inArgs.add(loadId);
       
                factory.doProcedure(connection,"pkg_dbuy_from_customer.LOAD_ID_PRE_STAGE_TO_STAGE", inArgs);
            }            
    	} catch (Exception e) {
    		errorMessage = e.getMessage();
    		log.error(e);
    		MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",
            		"PoXmlSubmitProcess Error encountered while calling pkg_dbuy_from_customer.LOAD_ID_PRE_STAGE_TO_STAGE for loadId:" + loadId ,errorMessage);
    	} finally {
            dbManager.returnConnection(connection);
            connection = null;
        }
		return errorMessage;
	}
    
    public BigDecimal getNextLoadIdSeq() {
        BigDecimal b = null;
        Connection connection = null;
        try {
            connection = dbManager.getConnection();
            b = new BigDecimal("" + new SqlManager().getOracleSequence(connection, "CUSTOMER_PO_LOAD_SEQ"));
        }catch (Exception ex) {
        }finally {
            try {
                dbManager.returnConnection(connection);
            }catch (Exception ee) {}
            connection = null;
        }
        return b;
    }

    public String getApplicationLogonId (String name) {
    	String applicationLogonId = "";
        Connection connection = null;
        try {
            connection = dbManager.getConnection();
           	//String[] result = name.split("[\\,\\s]+");
            String[] firstLastName = name.split("\\,", 2);

            String firstName = "";
        	String lastName = "";
        	String middleInitial = "";
        	int pos = 0;

        	int length = firstLastName.length;        	
        	if (length > 1) {        		
        		firstName = firstLastName[1].trim();
        		pos = firstName.lastIndexOf(" ");
        	}
        	if (length > 0) {
        		lastName = firstLastName[0].trim();
        	}
        	
        	if (pos > 0) {
        		middleInitial = firstName.substring(pos+1,pos+2);
        		firstName = firstName.substring(0,pos).trim();
        	}
            
        	StringBuffer query = new StringBuffer();
        	query.append("select c.company_application_logon_id ");
        	query.append(" from "); 
        	query.append(" personnel p, company_application_logon c "); 
        	query.append(" where "); 
        	query.append(" p.personnel_id = c.personnel_id ");
        	query.append(" and ((p.last_name like " + GenericProcess.getSqlString(lastName));
        	query.append("         and p.first_name like " + GenericProcess.getSqlString(firstName));
        	query.append("         and p.mid_initial like " + GenericProcess.getSqlString(middleInitial) + " ) "); 
        	query.append("	  or (p.last_name like " + GenericProcess.getSqlString(lastName));
        	query.append("	      and p.first_name like " + GenericProcess.getSqlString(firstName) + "))");

        	GenericSqlFactory factory = new GenericSqlFactory(dbManager);
        	applicationLogonId = factory.selectSingleValue(query.toString(),connection);
            
        	if (StringHandler.isBlankString(applicationLogonId)) {
        		applicationLogonId = name;
        	}
        	
        } catch (Exception ex) {        
        	applicationLogonId = name;
        	log.error("exception occured on while retreiving the company application logon id ", ex);
        } finally {
            try {
                dbManager.returnConnection(connection);
            } catch (Exception ee) {}
            connection = null;
        }
    	return applicationLogonId;
    }

    public String processChangeData(Collection customerPoPreStageBeanCol, BigDecimal loadId) throws BaseException {
    	String errorMessage = "OK";
    	int numOfRowsInserted = 0;
    	try {
            connection = dbManager.getConnection();

			Iterator iterator = customerPoPreStageBeanCol.iterator();        
	        CustomerPoPreStageBeanFactory custFactory = new CustomerPoPreStageBeanFactory(dbManager);
	        while (iterator.hasNext()) {
	          CustomerPoPreStageBean customerPoPreStageBean = (CustomerPoPreStageBean) iterator.next();
	          numOfRowsInserted += custFactory.insert(customerPoPreStageBean, connection);
	        }
	        log.debug("No Of rows inserted in customerPoPreStage :" + numOfRowsInserted);
            
    	} catch (Exception e) {
    		errorMessage = e.getMessage();
    		log.error(e);
    		MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",
            		"PoXmlSubmitProcess Error encountered while inserting data into the customer_Po_Pre_Stage table",errorMessage);
    	} finally {
            dbManager.returnConnection(connection);
            connection = null;
        }
		return errorMessage;
	}
    
    
}
