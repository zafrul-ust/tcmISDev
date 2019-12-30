package com.tcmis.client.common.process;

//import java.io.*;
//import java.io.PrintWriter;

//import java.io.Writer;
//import java.math.BigDecimal;
//import java.text.DateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.catalog.beans.ReportingEntityBean;
import com.tcmis.internal.catalog.factory.ReportingEntityBeanFactory;

/******************************************************************************
 * Process used by ManufacturerSearchAction
 * @version 1.0
 *****************************************************************************/

public class ClientWorkAreaGroupProcess extends GenericProcess {
	Log log = LogFactory.getLog(this.getClass());

	public ClientWorkAreaGroupProcess(String client) {
		super(client);
	}

	public Collection getWorkAreaGroupsForFacility(String companyId, String facilityId) throws BaseException, Exception {
		String query = "SELECT f.facility_name, r.* ";
		query += "FROM facility f, reporting_entity r ";
		query += "WHERE r.facility_id = f.facility_id AND r.company_id = '" + companyId + "' ";

		if (!StringHandler.isBlankString(facilityId) && !"All".equalsIgnoreCase(facilityId)) {
			query += " AND r.facility_id = '" + facilityId + "' ";
		}

		query += "ORDER BY description";

		return factory.setBean(new ReportingEntityBean()).selectQuery(query);
	}

	public HashMap<String, Vector<ReportingEntityBean>> getWorkAreaGroupsForCompany(PersonnelBean user) throws BaseException, Exception {
		StringBuilder query = new StringBuilder("SELECT f.facility_id, f.facility_name, r.reporting_entity_id, r.description ");
		query.append("FROM facility f, reporting_entity r, user_facility uf ");
		query.append("WHERE f.active = 'y' and uf.company_id = f.company_id and uf.facility_id = f.facility_id AND uf.company_id = '").append(user.getCompanyId()).append("' ");
		query.append(" and uf.personnel_id = ").append(user.getPersonnelId()).append(" and uf.company_id = r.company_id(+) and uf.facility_id = r.facility_id(+)");
		query.append(" ORDER BY facility_name, description");

		Collection<ReportingEntityBean> beans = factory.setBean(new ReportingEntityBean()).selectQuery(query.toString());
		Collection<ReportingEntityBean> facilityBeans = null;
		HashMap results = new LinkedHashMap<String, Vector<ReportingEntityBean>>();
		String currentFacility = "";
		for (ReportingEntityBean row : beans) {
			if (!row.getFacilityId().equals(currentFacility)) {
				if (facilityBeans != null) {
					results.put(currentFacility, facilityBeans);
				}
				facilityBeans = new Vector<ReportingEntityBean>();
				currentFacility = row.getFacilityId();
			}
			facilityBeans.add(row);
		}
		if (facilityBeans != null) {
			results.put(currentFacility, facilityBeans);
		}

		return results;
	}

	public String getNextReportingEntityId() throws BaseException, Exception {
		return dbManager.getOracleSequence("cabinet_seq") + "";
	}

	public int insertWorkAreaGroup(ReportingEntityBean bean) throws BaseException, Exception {
		ReportingEntityBeanFactory rebFactory = new ReportingEntityBeanFactory(dbManager);
		bean.setReportingEntityId(getNextReportingEntityId());
		return rebFactory.insert(bean);
	}

	public int updateWorkAreaGroup(ReportingEntityBean bean) throws BaseException, Exception {
		ReportingEntityBeanFactory rebFactory = new ReportingEntityBeanFactory(dbManager);
		return rebFactory.update(bean);
	}

}
