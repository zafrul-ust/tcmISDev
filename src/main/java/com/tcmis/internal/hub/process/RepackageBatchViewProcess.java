package com.tcmis.internal.hub.process;

import java.util.Collection;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.internal.hub.beans.RepackageBatchViewBean;
import com.tcmis.internal.hub.factory.RepackageBatchViewBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import java.util.Locale;
import java.io.Writer;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.SearchCriterion;

import java.math.BigDecimal;
   /******************************************************************************
   * Process for RepackageBatchView
   * @version 1.0
   *****************************************************************************/
  public class RepackageBatchViewProcess
   extends BaseProcess {
   Log log = LogFactory.getLog(this.getClass());

   public RepackageBatchViewProcess(String client) {
  	super(client);
   }

   public Collection getSearchResult(RepackageBatchViewBean
  	bean) throws BaseException {

  	DbManager dbManager = new DbManager(getClient());
  	RepackageBatchViewBeanFactory factory = new
  	 RepackageBatchViewBeanFactory(dbManager);
  	SearchCriteria criteria = new SearchCriteria();
  	 criteria.addCriterion("batchId",
  		SearchCriterion.EQUALS,
  		bean.getBatchId().toString());
  	return factory.select(criteria);
   }

public Collection updateValue(RepackageBatchViewBean bean)  throws
BaseException {
Collection inArgs = new Vector();
if( bean.getReceiptId() != null) {
	inArgs.add( bean.getReceiptId() );
	}
	else {
	inArgs.add("");
	}
if( bean.getItemId() != null) {
inArgs.add( bean.getItemId() );
}
else {
inArgs.add("");
}
if( bean.getQuantityRepackaged() != null) {
	inArgs.add( bean.getQuantityRepackaged() );
	}
	else {
	inArgs.add("");
	}
if( bean.getSizeUnit() != null) {
	inArgs.add( bean.getSizeUnit() );
	}
	else {
	inArgs.add("");
	}

if( bean.getBin() != null) {
	inArgs.add( bean.getBin() );
	}
	else {
	inArgs.add("");
	}
inArgs.add(new BigDecimal("0"));// I don't have repackage_order now...

Vector outArgs = new Vector();
outArgs.add( new Integer(java.sql.Types.NUMERIC) );
outArgs.add( new Integer(java.sql.Types.VARCHAR) );
DbManager dbManager = new DbManager(getClient());
GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
Vector v = (Vector) procFactory.doProcedure("p_repackage", inArgs,outArgs);
return v; 
}

}
