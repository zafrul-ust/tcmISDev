package com.tcmis.client.common.process;

//import java.io.*;
//import java.io.PrintWriter;

//import java.io.Writer;
//import java.math.BigDecimal;
//import java.text.DateFormat;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.common.beans.FacLocDeliveryPointBean;
import com.tcmis.client.common.factory.FacLocDeliveryPointBeanFactory;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;

/******************************************************************************
 * Process used by ManufacturerSearchAction
 * @version 1.0
 *****************************************************************************/

public class ClientDeliveryPointProcess extends GenericProcess {
	Log log = LogFactory.getLog(this.getClass());
	private FacLocDeliveryPointBeanFactory deliveryPointFactory;

	public ClientDeliveryPointProcess(String client) {
		super(client);
		deliveryPointFactory = new FacLocDeliveryPointBeanFactory(dbManager);
	}

	public Collection<FacLocDeliveryPointBean> getDeliveryPointsForDock(String companyId, String facilityId, String locationId) throws BaseException, Exception {
		SearchCriteria criteria = new SearchCriteria("companyId", SearchCriterion.EQUALS, companyId);
		criteria.addCriterion("facilityId", SearchCriterion.EQUALS, facilityId);
		criteria.addCriterion("locationId", SearchCriterion.EQUALS, locationId);
		SortCriteria sort = new SortCriteria("deliveryPointDesc");
		return deliveryPointFactory.select(criteria, sort);
	}

	public String getNextId() throws BaseException, Exception {
		return dbManager.getOracleSequence("misc_seq") + "";
	}

	public int insertDeliveryPoint(FacLocDeliveryPointBean bean) throws BaseException, Exception {
		bean.setDeliveryPoint(getNextId());
		bean.setDeliveryPointLongDesc(bean.getDeliveryPointDesc());
		return deliveryPointFactory.insert(bean);
	}

	public int updateDeliveryPoint(FacLocDeliveryPointBean bean) throws BaseException, Exception {
		return deliveryPointFactory.update(bean);
	}

}
