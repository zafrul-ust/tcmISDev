package com.tcmis.client.common.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.client.common.beans.TypeBean;

/******************************************************************************
 * Process for ItemCatalogProcess
 * @version 1.0
 *****************************************************************************/
public class EditTypeProcess extends BaseProcess {

	Log log = LogFactory.getLog(this.getClass());

	public EditTypeProcess(String client,String locale) {
		super(client,locale);
	}

	
	public Collection updateTypes(BigDecimal personnelId,String facilityId, Collection<TypeBean> beans) throws BaseException {
		Vector errorMessages = new Vector();
		Collection inArgs = null;
		Collection outArgs = null;
		String errorMsg = "";
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);

		for (TypeBean inputBean : beans) {
			if(inputBean.getTypeId() == null) {
				try {
					inArgs = new Vector(4);
					inArgs.add("");
					inArgs.add(facilityId);
					inArgs.add(inputBean.getTypeName());
					inArgs.add(personnelId);
					outArgs = new Vector(1);
					outArgs.add(new Integer(java.sql.Types.VARCHAR));
					log.debug("new arguments: " + inArgs);

					Vector error = (Vector) factory.doProcedure("PKG_SECONDARY_LABEL.P_INS_VV_TYPE_DATA", inArgs, outArgs);

					if(error.size()>0 && error.get(0) != null)
					{
						 String errorCode = (String) error.get(0);
				     	 log.info("updating Type: " + inputBean.getTypeName()+ errorCode);
				     	 if(!"ok".equalsIgnoreCase(errorCode))
				     		 errorMessages.add(errorCode);
					}  
				}
				catch (Exception e) {
					errorMsg = "Error updating Type: " + inputBean.getTypeName() ;
					errorMessages.add(errorMsg);
				}
			}
			else if(inputBean.getTypeName() != null && !inputBean.getTypeName().equals(inputBean.getOriginalTypeName())) {
				try {
					inArgs = new Vector(3);
					inArgs.add(inputBean.getTypeId());
					inArgs.add(inputBean.getTypeName());
					inArgs.add(personnelId);
					outArgs = new Vector(1);
					outArgs.add(new Integer(java.sql.Types.VARCHAR));
					log.debug("Update arguments: " + inArgs);

					Vector error = (Vector) factory.doProcedure("PKG_SECONDARY_LABEL.P_UPD_VV_TYPE_DATA", inArgs, outArgs);

					if(error.size()>0 && error.get(0) != null  )
					{
					     	 String errorCode = (String) error.get(0);
					     	 log.info("updating Type: " + inputBean.getTypeName()+ errorCode);
					     	 if(!"ok".equalsIgnoreCase(errorCode))
					     		 errorMessages.add(errorCode);
					}  
				}
				catch (Exception e) {
					errorMsg = "Error updating Type: " + inputBean.getTypeName() ;
					errorMessages.add(errorMsg);
				}
			}
		}

		factory = null;
		dbManager = null;

		return (errorMessages.size() > 0 ? errorMessages : null);
	}
	

} //end of class

