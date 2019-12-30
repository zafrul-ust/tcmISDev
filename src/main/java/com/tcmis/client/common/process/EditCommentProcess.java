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
import com.tcmis.client.common.beans.CommentBean;

/******************************************************************************
 * Process for ItemCatalogProcess
 * @version 1.0
 *****************************************************************************/
public class EditCommentProcess extends BaseProcess {

	Log log = LogFactory.getLog(this.getClass());

	public EditCommentProcess(String client,String locale) {
		super(client,locale);
	}

	
	public Collection updateComments(BigDecimal personnelId,String facilityId,BigDecimal typeId,Collection<CommentBean> beans) throws BaseException {
		Vector errorMessages = new Vector();
		Collection inArgs = null;
		Collection outArgs = null;
		String errorMsg = "";
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);

		for (CommentBean inputBean : beans) {
			if("new".equals(inputBean.getStatus()) && inputBean.getCommentId() != null && inputBean.getCommentId().length() > 0) {
				try {
					inArgs = new Vector(6);
					inArgs.add("");
					inArgs.add(facilityId);
					inArgs.add(typeId);
					inArgs.add(inputBean.getCommentId());
					inArgs.add(inputBean.getCommentTxt());
					inArgs.add(personnelId);
					outArgs = new Vector(1);
					outArgs.add(new Integer(java.sql.Types.VARCHAR));
					log.debug("new arguments: " + inArgs);

					Vector error = (Vector) factory.doProcedure("PKG_SECONDARY_LABEL.P_INS_VV_TYPE_COMMENT_DATA", inArgs, outArgs);

					if(error.size()>0 && error.get(0) != null)
					{
						 String errorCode = (String) error.get(0);
				     	 log.info("updating Type: " + inputBean.getCommentTxt()+ errorCode);
				     	 if(!"ok".equalsIgnoreCase(errorCode))
				     		 errorMessages.add(errorCode);
					}  
				}
				catch (Exception e) {
					errorMsg = "Error updating Type: " + inputBean.getCommentTxt() ;
					errorMessages.add(errorMsg);
				}
			}
			else if(!inputBean.getCommentTxt().equals(inputBean.getOriginalCommentTxt()) && inputBean.getCommentId() != null && inputBean.getCommentId().length() > 0) {
				try {
					inArgs = new Vector(6);
					inArgs.add("");
					inArgs.add(facilityId);
					inArgs.add(typeId);
					inArgs.add(inputBean.getCommentId());
					inArgs.add(inputBean.getCommentTxt());
					inArgs.add(personnelId);
					outArgs = new Vector(1);
					outArgs.add(new Integer(java.sql.Types.VARCHAR));
					log.debug("Update arguments: " + inArgs);

					Vector error = (Vector) factory.doProcedure("PKG_SECONDARY_LABEL.P_UPD_VV_TYPE_COMMENT_DATA", inArgs, outArgs);

					if(error.size()>0 && error.get(0) != null)
					{
						 String errorCode = (String) error.get(0);
				     	 log.info("updating Type: " + inputBean.getCommentTxt()+ errorCode);
				     	 if(!"ok".equalsIgnoreCase(errorCode))
				     		 errorMessages.add(errorCode);
					}  
				}
				catch (Exception e) {
					errorMsg = "Error updating Type: " + inputBean.getCommentTxt() ;
					errorMessages.add(errorMsg);
				}
			}
		}

		factory = null;
		dbManager = null;

		return (errorMessages.size() > 0 ? errorMessages : null);
	}
	

} //end of class

