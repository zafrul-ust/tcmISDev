package com.tcmis.supplier.shipping.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.factory.UserSupplierLocationBeanFactory;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.supplier.shipping.beans.SupplierPackingSummaryViewBean;
import com.tcmis.supplier.shipping.beans.SupplierShippingInputBean;
import com.tcmis.supplier.shipping.factory.SupplierLocPicklistOvBeanFactory;
import com.tcmis.supplier.shipping.factory.SupplierPackingSummaryViewBeanFactory;

/******************************************************************************
 * Process for SupplierLocPicklistOv
 * @version 1.0
 *****************************************************************************/
public class LabelingProcess
extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public LabelingProcess(String client) {
		super(client);
	}

	public Collection getSearchDropDown(BigDecimal pid) throws BaseException {

		DbManager dbManager = new DbManager(getClient());
		SupplierLocPicklistOvBeanFactory factory = new
		SupplierLocPicklistOvBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("personnelId",
				SearchCriterion.EQUALS,
				pid.toString());

		return factory.selectDLAObject(criteria);
	}

	public Collection getSearchResult(SupplierShippingInputBean bean)
	throws BaseException {

		DbManager dbManager = new DbManager(getClient());
		SupplierPackingSummaryViewBeanFactory factory = new SupplierPackingSummaryViewBeanFactory(
				dbManager);
		SearchCriteria criteria = new SearchCriteria();

		if (bean.getSupplier() != null && !bean.getSupplier().equals(""))
			criteria.addCriterion("supplier", SearchCriterion.EQUALS, bean
					.getSupplier());
		/*	if (bean.getShipFromLocationId() != null
			&& !bean.getShipFromLocationId().equals(""))
		criteria.addCriterion("shipFromLocationId", SearchCriterion.EQUALS,
				bean.getShipFromLocationId());*/
		if (bean.getPicklistId() != null && !bean.getPicklistId().equals(""))
			criteria.addCriterion("picklistId", SearchCriterion.EQUALS, bean
					.getPicklistId().toString());

		if (bean.getSuppLocationIdArray() != null && bean.getSuppLocationIdArray().length == 1
				&& bean.getSuppLocationIdArray()[0].length() == 0)
		{
			/*All supplier locations means all supplier locations the user has privilages to*/
			UserSupplierLocationBeanFactory userSuppLocfactory = new
			UserSupplierLocationBeanFactory(dbManager);
			SearchCriteria suppLoccriteria = new SearchCriteria();

			suppLoccriteria.addCriterion("supplier",
					SearchCriterion.EQUALS,
					bean.getSupplier());

			suppLoccriteria.addCriterion("personnelId",
					SearchCriterion.EQUALS,
					bean.getPersonnelId().toString());
			Collection userSuppLocCollection = userSuppLocfactory.selectDistinctSuppLoc(suppLoccriteria,null);
			criteria.addCriterion("shipFromLocationId", SearchCriterion.EQUALS, userSuppLocCollection,"");
		}
		else  if(bean.getSuppLocationIdArray() != null && bean.getSuppLocationIdArray().length > 0) {
			boolean initFlag = true;
			for(int i=0; i<bean.getSuppLocationIdArray().length; i++) {
				if(bean.getSuppLocationIdArray()[i].length() > 0) {
					if (initFlag) {
						criteria.addCriterion("shipFromLocationId", SearchCriterion.EQUALS,
								bean.getSuppLocationIdArray()[i].toString());
					}
					else {
						criteria.addValueToCriterion("shipFromLocationId",
								bean.getSuppLocationIdArray()[i].toString());
					}
					initFlag = false;
				}
			}
		}

		String s = null;
		s = bean.getSearchArgument();
		if ( s != null && !s.equals("") ) {
			String mode = bean.getSearchMode();
			String field = bean.getSearchField();
			if( field.equals("cityCommaState"))
				s = s.replaceAll("\\s",""); // remove all white spaces
			if( mode.equals("is") )
				criteria.addCriterion(field,
						SearchCriterion.EQUALS,
						s);
			if( mode.equals("contains"))
				criteria.addCriterion(field,
						SearchCriterion.LIKE,
						s,SearchCriterion.IGNORE_CASE);
			if( mode.equals("startsWith"))
				criteria.addCriterion(field,
						SearchCriterion.STARTS_WITH,
						s,SearchCriterion.IGNORE_CASE);
			if( mode.equals("endsWith"))
				criteria.addCriterion(field,
						SearchCriterion.ENDS_WITH,
						s,SearchCriterion.IGNORE_CASE);
		}

		SortCriteria scriteria = new SortCriteria();
		// SHIP_TO_LOCATION_ID,ULTIMATE_SHIP_TO_DODAAC,PR_NUMBER,LINE_ITEM
		//
		scriteria.addCriterion("shipToLocationId");
		scriteria.addCriterion("picklistId");
		scriteria.addCriterion("ultimateShipToDodaac");
		scriteria.addCriterion("prNumber");
		scriteria.addCriterion("lineItem");
		return factory.select(criteria, scriteria);
	}

	public Object[] showResults(SupplierShippingInputBean inputBean) throws BaseException {
		Vector beans = (Vector) this.getSearchResult(inputBean);
		// calculate shipToPicklistCountMap and dodaacCountMap
		HashMap shipToPicklistCountMap = new HashMap();
		HashMap dodaacCountMap = new HashMap();
		Integer shipToPicklistCount = null;
		HashMap shipToPicklistToDodaac = null;
		Integer dodaacCount = null;

		SupplierPackingSummaryViewBean pbean = null;
		for (int i = 0; i < beans.size(); i++) {
			pbean = (SupplierPackingSummaryViewBean) beans.get(i);
			String shipToPicklist = pbean.getShipToLocationId()+pbean.getPicklistId();
			if (shipToPicklist == null)
				shipToPicklist = "";
			if (shipToPicklistCountMap.get(shipToPicklist) == null) {
				shipToPicklistCount = new Integer(0);
				shipToPicklistCountMap.put(shipToPicklist, shipToPicklistCount);
				shipToPicklistToDodaac = new HashMap();
				dodaacCountMap.put(shipToPicklist, shipToPicklistToDodaac);
			}
			shipToPicklistCount = (Integer) shipToPicklistCountMap.get(shipToPicklist);
			shipToPicklistCount = new Integer(shipToPicklistCount.intValue() + 1);
			shipToPicklistCountMap.put(shipToPicklist, shipToPicklistCount);

			String dodaac = pbean.getUltimateShipToDodaac();
			if (dodaac == null)
				dodaac = "";
			dodaac = "itemId" + dodaac; // "itemId" is the discriminator for null object.

			if (shipToPicklistToDodaac.get(dodaac) == null) {
				dodaacCount = new Integer(0);
				shipToPicklistToDodaac.put(dodaac, dodaacCount);
			}
			dodaacCount = (Integer) shipToPicklistToDodaac.get(dodaac);
			dodaacCount = new Integer(dodaacCount.intValue() + 1);
			shipToPicklistToDodaac.put(dodaac, dodaacCount);
		}
		Object result[] = {beans,shipToPicklistCountMap,dodaacCountMap};
		return result;
		/*		do this in the action.
		 * 		request.setAttribute("pageNameViewBeanCollection", beans);
		request.setAttribute("rowCountPart", shipToPicklistCountMap);
		request.setAttribute("rowCountItem", dodaacCountMap);
		 */
	}

	public String update(Collection beans1) throws BaseException {
		SupplierPackingSummaryViewBean bean=null;
		
		String errorMsg = "";
		for(Object obj:beans1) {
			if( obj == null ) continue;
			bean = (SupplierPackingSummaryViewBean)obj;
			String ok = bean.getOk();
			if( ok != null && ok.equals("ok") ) {
				errorMsg = undoMr(bean);
				if(errorMsg != null){
					return errorMsg;
				}
				
			}
		}
		
		return errorMsg;
	}

	public String undoMr(SupplierPackingSummaryViewBean bean)  throws
	BaseException {
		DbManager dbManager = new DbManager(getClient());
		GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
		String result = "OK";
		/*Call p_gas_undo*/
		Vector gasundoargs = new Vector(4);
		gasundoargs.add("USGOV");
		gasundoargs.add(bean.getPrNumber());
		gasundoargs.add(bean.getLineItem());
		gasundoargs.add(bean.getPicklistId());
		if(log.isDebugEnabled()) {
			log.debug("p_gas_undo "+gasundoargs);
		}
		Collection cout = new Vector(1);
		cout.add(new Integer(java.sql.Types.VARCHAR));     //error value
		if (log.isDebugEnabled()) {
			log.debug(cout.toString());
		}
		
		Collection procedureData = procFactory.doProcedure("p_gas_undo", gasundoargs, cout);

		/*Call p_line_item_allocate*/
		/* Vector allocateargs = new Vector(2);
		 allocateargs.add(bean.getPrNumber());
		 allocateargs.add(bean.getLineItem());
		 log.debug("p_line_item_allocate "+allocateargs);
		 procFactory.doProcedure("p_line_item_allocate", allocateargs, new Vector());*/
		
		Iterator i11 = procedureData.iterator();
		while (i11.hasNext()) {
			Object tmp = i11.next();
			if (tmp != null) {
				result = tmp.toString();
			}
		}
		if (!"OK".equalsIgnoreCase(result)) {
			String emsg = "Procedure: p_gas_undo has an error.\n";
			emsg += result;
			
		}
		return result;
	}

}
