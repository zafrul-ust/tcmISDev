package com.tcmis.client.common.process;

//import java.io.*;
//import java.io.PrintWriter;

//import java.io.Writer;
//import java.math.BigDecimal;
//import java.text.DateFormat;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.common.beans.VocZoneBean;
import com.tcmis.client.common.factory.VocZoneBeanFactory;
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

public class ClientVocZoneProcess extends BaseProcess {
	static Log log = LogFactory.getLog(ClientVocZoneProcess.class);
	protected DbManager dbManager = null;
	protected VocZoneBeanFactory factory = null;

	public ClientVocZoneProcess(String client) {
		super(client);
		try {
			dbManager = new DbManager(client, getLocale());
			factory = new VocZoneBeanFactory(dbManager);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public Collection<VocZoneBean> getVocZones(String companyId, String facilityId ) throws BaseException, Exception {
		SearchCriteria criteria = new SearchCriteria("companyId", SearchCriterion.EQUALS, companyId);
		criteria.addCriterion("facilityId",  SearchCriterion.EQUALS, facilityId);
		SortCriteria sort = new SortCriteria("vocZoneDescription");
		return factory.select(criteria, sort);
	}

	public int insertVocZone(VocZoneBean bean) throws BaseException, Exception {
		return factory.insert(bean);
	}

	public int updateVocZone(VocZoneBean bean) throws BaseException, Exception {
		return factory.update(bean);
	}

}
