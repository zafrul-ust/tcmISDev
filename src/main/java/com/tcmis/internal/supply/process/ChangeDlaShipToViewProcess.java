package com.tcmis.internal.supply.process;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.internal.supply.beans.ChangeDlaShipToInputBean;
import com.tcmis.internal.supply.factory.ChangeDlaShipToViewBeanFactory;

/******************************************************************************
 * Process for ChangeDlaShipToView
 * @version 1.0
 *****************************************************************************/
public class ChangeDlaShipToViewProcess
extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public ChangeDlaShipToViewProcess(String client) {
		super(client);
	}

	public ChangeDlaShipToViewProcess(String client,String locale) {
		super(client,locale);
	}

	public Collection getSearchResult(ChangeDlaShipToInputBean
			bean) throws BaseException {

		DbManager dbManager = new DbManager(getClient(),getLocale());
		ChangeDlaShipToViewBeanFactory factory = new
		ChangeDlaShipToViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();

		if (bean.getPrNumber() != null ) {
			criteria.addCriterion("prNumber",
					SearchCriterion.EQUALS,
					bean.getPrNumber().toString());
		}

		if (bean.getLineItem() != null &&
				bean.getLineItem().length() > 0) {
			criteria.addCriterion("lineItem",
					SearchCriterion.EQUALS,
					bean.getLineItem());
		}


		return factory.select(criteria);
	}

	public Collection update(ChangeDlaShipToInputBean bean)  throws
	BaseException {
		Collection inArgs = new Vector();
		if( bean.getPrNumber() != null) {
			inArgs.add( bean.getPrNumber() );
		}
		else {
			inArgs.add("");
		}
		if( bean.getLineItem() != null) {
			inArgs.add( bean.getLineItem() );
		}
		else {
			inArgs.add("");
		}
		if( bean.getDodaac() != null) {
			inArgs.add( bean.getDodaac() );
		}
		else {
			inArgs.add("");
		}
		if( bean.getShipViaLocationId() != null) {
			inArgs.add( bean.getShipViaLocationId() );
		}
		else {
			inArgs.add("");
		}
		if( bean.getShipToLocationId() != null) {
			inArgs.add( bean.getShipToLocationId() );
		}
		else {
			inArgs.add("");
		}
		if( bean.getMfDodaac() != null) {
			inArgs.add( bean.getMfDodaac() );
		}
		else {
			inArgs.add("");
		}

		if (bean.getDesiredShipDate()!= null) {
			try {
				inArgs.add(new Timestamp(bean.getDesiredShipDate().getTime()));
			}
			catch (Exception ex) {
			}
		}
		else {
			inArgs.add("");
		}

		if( bean.getRadianPO() != null) {
			inArgs.add( bean.getRadianPO() );
		}
		else {
			inArgs.add("");
		}

		Vector outArgs = new Vector();
		outArgs.add( new Integer(java.sql.Types.VARCHAR) );
		DbManager dbManager = new DbManager(getClient(),getLocale());
		ChangeDlaShipToViewBeanFactory factory = new
		ChangeDlaShipToViewBeanFactory(dbManager);
		if(log.isDebugEnabled()) {
			log.debug("p_change_dla_ship_to"+inArgs);
		}
		Collection errMsgs = factory.doProcedure("p_change_dla_ship_to", inArgs,outArgs);
		if (errMsgs.toArray()[0] == null)
			//	   {  errMsgs.add("test");
			//		   return errMsgs;}
			return null;
		else
			return errMsgs;
	}

}
