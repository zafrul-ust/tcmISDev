package com.tcmis.client.openCustomer.process;

import java.util.Collection;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.EncryptHandler;

/******************************************************************************
 * Process for changing password
 * @version 1.0
 *****************************************************************************/
public class ForgetPasswordProcess extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public ForgetPasswordProcess(String client) {
    super(client);
  }

  public String getPassword()  throws BaseException {
	    DbManager dbManager = new DbManager(getClient());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);
			
		StringBuilder query = new StringBuilder("SELECT PKG_OPEN_CUSTOMER.FX_GET_RANDOM_PWD FROM DUAL");
			
//	log.debug(query);	
		return factory.selectSingleValue(query.toString());
  } //end of method
  
 
  public String createNewPassword(String logonId, String newPassword, String action) throws BaseException{
	 try {
		Collection inArgs = new Vector();
	  	Collection outArgs = new Vector();

	  	inArgs.add(logonId);
		inArgs.add(newPassword);
		inArgs.add(EncryptHandler.encrypt(newPassword));
		inArgs.add(action);

		outArgs.add(java.sql.Types.VARCHAR);

		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
		Vector c = (Vector)procFactory.doProcedure("PKG_OPEN_CUSTOMER.P_PERSONNEL_RESET_PWD", inArgs, outArgs);
		String errMsg = (String)c.get(0); 
		return errMsg;
  	} catch (Exception ex) {
		throw new BaseException(ex.getMessage());
	}

  } //end of method


} //end of class
