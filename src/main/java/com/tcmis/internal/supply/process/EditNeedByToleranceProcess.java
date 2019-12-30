package com.tcmis.internal.supply.process;
import java.util.Collection;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.internal.supply.beans.EditNeedByToleranceInputBean;

public class EditNeedByToleranceProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public EditNeedByToleranceProcess(String client, String locale) {
		super(client, locale);
	}

	public EditNeedByToleranceProcess(String client) {
		super(client);
	}
	
	public Vector update (EditNeedByToleranceInputBean input , PersonnelBean user) throws BaseException {
		Vector errorMessages = new Vector();
		Collection inArgs = null;
		Collection outArgs = null;
		String errorMsg = "";
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);
		try {
			inArgs = new Vector(2);
			inArgs.add(input.getInventoryGroup());
			inArgs.add(input.getNeedByTolerance());
			outArgs = new Vector(1);
			outArgs.add(new Integer(java.sql.Types.VARCHAR));
			Vector error = (Vector) factory.doProcedure("PKG_CONSOLIDATE_BUYORDER.P_UPDATE_IGD_NEED_BY_TOLERANCE", inArgs, outArgs);
			if(error.size()>0 && error.get(0) != null)
			      {
			     	 String errorCode = (String) error.get(0);
			     	 log.info("Error in Procedure PKG_CONSOLIDATE_BUYORDER.P_UPDATE_IGD_NEED_BY_TOLERANCE: "+ input.getInventoryGroupName() + " Error Code "+errorCode+" ");
			     	 errorMessages.add(errorCode);
			      }  
			}
			catch (Exception e) {
				errorMsg = "Error updating: " + input.getInventoryGroupName() + " ";
				errorMessages.add(errorMsg);
			}
		factory = null;
		dbManager = null;
		return (errorMessages.size() > 0 ? errorMessages : null);
	}

}
