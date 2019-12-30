package com.tcmis.client.common.process;

//import java.io.*;
//import java.io.PrintWriter;

//import java.io.Writer;
//import java.math.BigDecimal;
//import java.text.DateFormat;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.common.beans.FlammabilityControlZoneBean;
import com.tcmis.client.common.factory.FlammabilityControlZoneBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;

/******************************************************************************
 * Process used by ManufacturerSearchAction
 * @version 1.0
 *****************************************************************************/

public class ClientFlamCtrlZoneProcess extends BaseProcess {
	static Log log = LogFactory.getLog(ClientFlamCtrlZoneProcess.class);
	protected DbManager dbManager = null;
	protected FlammabilityControlZoneBeanFactory factory = null;

	public ClientFlamCtrlZoneProcess(String client) {
		super(client);
		try {
			dbManager = new DbManager(client, getLocale());
			factory = new FlammabilityControlZoneBeanFactory(dbManager);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public Collection<FlammabilityControlZoneBean> getFlammabilityControlZones(String companyId, String facilityId ) throws BaseException, Exception {
		SearchCriteria criteria = new SearchCriteria("companyId", SearchCriterion.EQUALS, companyId);
		criteria.addCriterion("facilityId",  SearchCriterion.EQUALS, facilityId);
		SortCriteria sort = new SortCriteria("flammabilityControlZoneDesc");
		return factory.select(criteria, sort);
	}

	public int insertFlammabilityControlZone(FlammabilityControlZoneBean bean) throws BaseException, Exception {
		return factory.insert(bean);
	}

	public int updateFlammabilityControlZone(FlammabilityControlZoneBean bean) throws BaseException, Exception {
		return factory.update(bean);
	}

}
