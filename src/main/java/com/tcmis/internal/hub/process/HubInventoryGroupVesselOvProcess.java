package com.tcmis.internal.hub.process;

import java.util.Collection;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.internal.hub.beans.HubInventoryGroupVesselOvBean;
import com.tcmis.internal.hub.factory.HubInventoryGroupVesselOvBeanFactory;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import java.util.Locale;
import java.io.Writer;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.SearchCriterion;

   /******************************************************************************
   * Process for HubInventoryGroupVesselOv
   * @version 1.0
   *****************************************************************************/
  public class HubInventoryGroupVesselOvProcess
   extends BaseProcess {
   Log log = LogFactory.getLog(this.getClass());

   public HubInventoryGroupVesselOvProcess(String client) {
  	super(client);
   }

   public Collection getSearchResult(HubInventoryGroupVesselOvBean
  	bean) throws BaseException {

  	DbManager dbManager = new DbManager(getClient());
  	HubInventoryGroupVesselOvBeanFactory factory = new
  	 HubInventoryGroupVesselOvBeanFactory(dbManager);
  	SearchCriteria criteria = new SearchCriteria();
  	
  	 criteria.addCriterion("personnelId",
  		SearchCriterion.EQUALS,
  		bean.getPersonnelId().toString());

  	return factory.selectObject(criteria);
   }

   public void setHubBean(HttpServletRequest request,BigDecimal personnelBean) throws BaseException{
		
	    HubInventoryGroupVesselOvBean hbean= new HubInventoryGroupVesselOvBean();
	    hbean.setPersonnelId(personnelBean);
	     
	    request.setAttribute("hubBean",this.getSearchResult(hbean));
  }
}
