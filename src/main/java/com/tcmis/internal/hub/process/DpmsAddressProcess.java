package com.tcmis.internal.hub.process;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Mar 14, 2008
 * Time: 10:55:03 AM
 * To change this template use File | Settings | File Templates.
 */

import java.util.Calendar;
import java.util.Collection;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.order.factory.CustomerPoStageBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.DpmsAddressInputBean;
import com.tcmis.internal.hub.beans.LocationSearchViewBean;
import com.tcmis.internal.hub.factory.CustPoAddressChangeViewBeanFactory;
import com.tcmis.internal.hub.factory.LocationSearchViewBeanFactory;

/******************************************************************************
 * Process for
 * @version 1.0
 *****************************************************************************/

public class DpmsAddressProcess
extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public DpmsAddressProcess(String client) {
		super(client);
	}

	public Collection getRecord(DpmsAddressInputBean bean) throws
	BaseException {
		DbManager dbManager = new DbManager(getClient());
		SearchCriteria searchCriteria = new SearchCriteria("addressChangeRequestId", SearchCriterion.EQUALS, bean.getAddressChangeRequestId().toString());
		searchCriteria.addCriterion("companyId", SearchCriterion.EQUALS, "USGOV");
		CustPoAddressChangeViewBeanFactory factory = new CustPoAddressChangeViewBeanFactory(dbManager);
		return factory.select(searchCriteria, null);
	}

	public Collection<LocationSearchViewBean> getAddressList(DpmsAddressInputBean bean) throws
	BaseException {
		DbManager dbManager = new DbManager(getClient());
		LocationSearchViewBeanFactory factory = new LocationSearchViewBeanFactory(dbManager);
		SearchCriteria searchCriteria = new SearchCriteria();
		/*
    if("DPMS".equalsIgnoreCase(bean.getType())){
        searchCriteria.addCriterion("dodaacType", SearchCriterion.EQUALS, "POE");
        searchCriteria.addValueToCriterion("dodaacType", "CCP");
        searchCriteria.addValueToCriterion("dodaacType", "DODAAC");
    }
    else if("NOL".equalsIgnoreCase(bean.getType())){
        searchCriteria.addCriterion("dodaacType", SearchCriterion.EQUALS, "NOL");
    }
    else {
        if(!StringHandler.isBlankString(bean.getDodaac())) {
            searchCriteria.addCriterion("dodaac", SearchCriterion.EQUALS, bean.getDodaac());
        }
    }
		 */
		if(!StringHandler.isBlankString(bean.getDodaacType())) {
			searchCriteria.addCriterion("dodaacType", SearchCriterion.EQUALS, bean.getDodaacType());
		}
		if(!StringHandler.isBlankString(bean.getDodaac())) {
			searchCriteria.addCriterion("dodaac", SearchCriterion.EQUALS, bean.getDodaac(), SearchCriterion.IGNORE_CASE);
		}
		if(!StringHandler.isBlankString(bean.getSearchText())) {
			searchCriteria.addCriterion("search", SearchCriterion.LIKE, bean.getSearchText(), SearchCriterion.IGNORE_CASE);
		}
		SortCriteria sortCriteria = new SortCriteria("city");
		//sortCriteria.addCriterion("city");
		//searchCriteria.addCriterion("search", SearchCriterion.LIKE, "25600");
		return factory.select(searchCriteria, sortCriteria);
	}

	public void updateAddress(DpmsAddressInputBean bean) throws
	BaseException, Exception {
		if(log.isDebugEnabled()) {
			log.debug("bean:" + bean.toString());
			log.debug("date:" + bean.getDpmsReplyDate());
		}
		Collection inArgs = null;
		DbManager dbManager = new DbManager(getClient());
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);

		Boolean doUpdate = true;
		if (bean.getDpmsReplyDate() !=null)
		{
			Calendar oneWeekago = Calendar.getInstance();
			oneWeekago.add(Calendar.HOUR, -168);

			Calendar dpmsEmailDate = Calendar.getInstance();
			dpmsEmailDate.setTime(bean.getDpmsReplyDate());

			if (log.isDebugEnabled()) {
				log.debug("Older than "+com.tcmis.common.util.DateHandler.getOracleDate(oneWeekago.getTime()));
				log.debug("dpmsEmailDate than "+com.tcmis.common.util.DateHandler.getOracleDate(dpmsEmailDate.getTime()));
				log.debug(oneWeekago.compareTo(dpmsEmailDate));
			}
			if (oneWeekago.compareTo(dpmsEmailDate) > 0)
			{
				doUpdate = false;
			}
		}

		if (doUpdate)
		{
			if(bean.getMfDodaacType() != null) {
				inArgs = new Vector(12);
				inArgs.add("USGOV");
				inArgs.add(bean.getAddressChangeRequestId());
				inArgs.add(bean.getDodaac());//new_ship_to_dodaac
				inArgs.add(bean.getDodaacType());//new_ship_to_dodaac_type
				inArgs.add(bean.getNotes());//shipping_notes
				inArgs.add(bean.getLocationId());//new_ship_to_location_id
				inArgs.add(bean.getPortOfEmbarkation());//POE
				inArgs.add(bean.getPortOfDebarkation());//POD
				if(bean.getDpmsReplyDate() == null) {
					inArgs.add("");
				}
				else {
					inArgs.add(new java.sql.Timestamp(bean.getDpmsReplyDate().getTime())); //reply time stamp
				}
				inArgs.add(bean.getPersonnelId());
				inArgs.add(bean.getMfLocationId());//mark_for_location_id
				inArgs.add(bean.getMfDodaac());//new_mark_for_dodaac
				inArgs.add(bean.getMfDodaacType());//mark_for_dodaac_type

			}
			else { //this should never happen now in the new version
				inArgs = new Vector(10);
				inArgs.add("USGOV");
				inArgs.add(bean.getAddressChangeRequestId());
				inArgs.add(bean.getDodaac());//new_ship_to_dodaac
				inArgs.add(bean.getDodaacType());//new_ship_to_dodaac_type
				inArgs.add(bean.getNotes());//shipping_notes
				inArgs.add(bean.getLocationId());//new_ship_to_location_id
				inArgs.add(bean.getPortOfEmbarkation());//POE
				inArgs.add(bean.getPortOfDebarkation());//POD
				if(bean.getDpmsReplyDate() == null) {
					inArgs.add("");
				}
				else {
					inArgs.add(new java.sql.Timestamp(bean.getDpmsReplyDate().getTime())); //reply time stamp
				}
				inArgs.add(bean.getPersonnelId());
			}
			if(log.isDebugEnabled()) {
				log.debug("pkg_dbuy_from_customer.p_address_change_update:" + inArgs);
			}
			factory.doProcedure("pkg_dbuy_from_customer.p_address_change_update", inArgs);
		}

		//update milstrip if it was null
		if(StringHandler.isBlankString(bean.getOldMilstripCode())) {
			CustomerPoStageBeanFactory fac = new CustomerPoStageBeanFactory(dbManager);
			fac.updateMilstrip(bean.getMilstripCode(), bean.getAddressChangeRequestId());
		}
	}

	public void addAddress(DpmsAddressInputBean bean) throws
	BaseException, Exception {
		if(log.isDebugEnabled()) {
			log.debug("bean:" + bean);
		}
		if("None".equalsIgnoreCase(bean.getStateAbbrev())) {
			bean.setStateAbbrev("");
		}
		DbManager dbManager = new DbManager(getClient());
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);
		Collection inArgs = new Vector(12);
		inArgs.add(bean.getDodaac());
		inArgs.add("USGOV");
		inArgs.add(bean.getAddress1());
		inArgs.add(bean.getCountryAbbrev());
		inArgs.add(bean.getStateAbbrev());
		inArgs.add(bean.getCity());
		inArgs.add(bean.getZip());
		inArgs.add(bean.getAddress1());
		inArgs.add(bean.getAddress2());
		inArgs.add(bean.getAddress3());
		inArgs.add(bean.getAddress4());
		inArgs.add(bean.getDodaacType());
		Collection outArgs = new Vector(1);
		outArgs.add(new Integer(java.sql.Types.VARCHAR));
		if(log.isDebugEnabled()) {
			log.debug("Pkg_dbuy_from_customer.CREATE_POE:" + inArgs);
		}
		factory.doProcedure("Pkg_dbuy_from_customer.CREATE_POE", inArgs, outArgs);

	}
}