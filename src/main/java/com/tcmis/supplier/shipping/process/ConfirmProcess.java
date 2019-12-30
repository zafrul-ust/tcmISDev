package com.tcmis.supplier.shipping.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.factory.UserSupplierLocationBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.supplier.shipping.beans.GasBoxShipmentViewBean;
import com.tcmis.supplier.shipping.beans.GasPalletShipmentViewBean;
import com.tcmis.supplier.shipping.beans.SupplierPackingViewBean;
import com.tcmis.supplier.shipping.beans.SupplierShippingInputBean;
import com.tcmis.supplier.shipping.factory.GasBoxShipmentViewBeanFactory;
import com.tcmis.supplier.shipping.factory.GasOpenShipmentViewBeanFactory;
import com.tcmis.supplier.shipping.factory.GasPalletShipmentViewBeanFactory;
import com.tcmis.supplier.shipping.factory.SupplierCarrierBeanFactory;

/*******************************************************************************
 * Process for SupplierPackingView
 *
 * @version 1.0
 ******************************************************************************/
public class ConfirmProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public ConfirmProcess(String client) {
		super(client);
	}

	public Collection getSearchResultLtltl(SupplierShippingInputBean bean)
	throws BaseException {

		DbManager dbManager = new DbManager(getClient());
		GasPalletShipmentViewBeanFactory factory = new GasPalletShipmentViewBeanFactory(
				dbManager);
		SearchCriteria criteria = new SearchCriteria();

		if (bean.getSupplier() != null && !bean.getSupplier().equals(""))
			criteria.addCriterion("supplier", SearchCriterion.EQUALS, bean
					.getSupplier());
		/*		if (bean.getShipFromLocationId() != null
				&& !bean.getShipFromLocationId().equals(""))
			criteria.addCriterion("shipFromLocationId", SearchCriterion.EQUALS,
					bean.getShipFromLocationId());*/
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
		criteria.addCriterion("usgovTcn", SearchCriterion.IS_NOT,null);

		SortCriteria scriteria = new SortCriteria();
		scriteria.addCriterion("shipViaLocationId");
		scriteria.addCriterion("shipViaCompanyId");
		scriteria.addCriterion("palletId");
		return factory.select(criteria, scriteria);
	}

	public Collection getSearchResultParcel(SupplierShippingInputBean bean)
	throws BaseException {

		DbManager dbManager = new DbManager(getClient());
		GasBoxShipmentViewBeanFactory factory = new GasBoxShipmentViewBeanFactory(
				dbManager);
		SearchCriteria criteria = new SearchCriteria();

		if (bean.getSupplier() != null && !bean.getSupplier().equals(""))
			criteria.addCriterion("supplier", SearchCriterion.EQUALS, bean
					.getSupplier());
		if (bean.getShipFromLocationId() != null
				&& !bean.getShipFromLocationId().equals(""))
			criteria.addCriterion("shipFromLocationId", SearchCriterion.EQUALS,
					bean.getShipFromLocationId());

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

		criteria.addCriterion("usgovTcn", SearchCriterion.IS_NOT,null);

		SortCriteria scriteria = new SortCriteria();
		scriteria.addCriterion("shipViaLocationId");
		scriteria.addCriterion("ultimateDodaac");
		scriteria.addCriterion("supplierSalesOrderNo");
		return factory.select(criteria,scriteria);
	}

	public Collection getSearchResultLtltlDropDown(String supplier,String locationId)
	throws BaseException {

		DbManager dbManager = new DbManager(getClient());
		GasOpenShipmentViewBeanFactory factory = new GasOpenShipmentViewBeanFactory(
				dbManager);
		SearchCriteria criteria = new SearchCriteria();

		criteria.addCriterion("shipToCompanyId", SearchCriterion.EQUALS, supplier);
		criteria.addCriterion("shipToLocationId", SearchCriterion.EQUALS,
				locationId);
		return factory.selectDropDown(criteria,null);
	}

	public Collection getValidCarriers(SupplierShippingInputBean bean)
	throws BaseException {

		DbManager dbManager = new DbManager(getClient());
		SupplierCarrierBeanFactory factory = new SupplierCarrierBeanFactory(
				dbManager);
		SearchCriteria criteria = new SearchCriteria();

		if (bean.getSupplier() != null && !bean.getSupplier().equals(""))
			criteria.addCriterion("supplier", SearchCriterion.EQUALS, bean
					.getSupplier());

		SortCriteria scriteria = new SortCriteria();
		scriteria.addCriterion("carrierName");
		return factory.select(criteria, scriteria);
	}

	public String shipConfirmsLtltl(Collection beans1) throws BaseException {
		int shipConfirmCount = 0;
		String errMsg ="";
		GasPalletShipmentViewBean bean = null;
		for (Object obj : beans1) {
			if (obj == null)
				continue;
			bean = (GasPalletShipmentViewBean) obj;
			String id = bean.getOk();
			String err = "";
			if ((id != null && id.length() > 0)
					&& (bean.getCurrentCarrierName() != null && bean.getCurrentCarrierName().length() > 0)) {
				//				if( err != null && !err.equalsIgnoreCase("ok") )
				{
					BigDecimal shipmentId = new BigDecimal(shipConfirmLtltl(bean));
					if(log.isDebugEnabled()) {
						log.debug("shipmentId p_gas_shipment_setup ltl "+shipmentId);
					}
					bean.setCurrentShipmentId(shipmentId);
					//					err = updateLtlTlShipmentId(bean);
					if( bean.getCurrentCarrierName().length() > 0 ||
							bean.getCurrentTrackingNumber().length() > 0 )
						updateLtlTlShipmentId(bean);
				}
				errMsg += err;
				shipConfirmCount++;
			}
		}
		return errMsg ;
	}

	public String shipConfirmLtltl(GasPalletShipmentViewBean bean)
	throws BaseException {
		Collection inArgs = new Vector();
		if (bean.getCurrentCarrierName() != null) {
			inArgs.add(bean.getCurrentCarrierName().trim());
		} else {
			inArgs.add("");
		}
		if (bean.getCurrentTrackingNumber() != null) {
			inArgs.add(bean.getCurrentTrackingNumber().trim());
		} else {
			inArgs.add("");
		}
		if (bean.getShipViaCompanyId() != null) {
			inArgs.add(bean.getShipViaCompanyId());
		} else {
			inArgs.add("");
		}
		if (bean.getShipViaLocationId() != null) {
			inArgs.add(bean.getShipViaLocationId());
		} else {
			inArgs.add("");
		}
		/*if (bean.getCustomerPoNo() != null) {
			inArgs.add(bean.getCustomerPoNo());
		} else */{
			inArgs.add("");
		}

		Vector outArgs = new Vector();
		outArgs.add(new Integer(java.sql.Types.VARCHAR));

		DbManager dbManager = new DbManager(getClient());
		if(log.isDebugEnabled()) {
			log.debug("Calling p_gas_shipment_setup ltl "+inArgs+outArgs);
		}
		Vector v = (Vector) dbManager.doProcedure("p_gas_shipment_setup", inArgs,outArgs);
		return (String) v.get(0);
	}

	public String shipConfirmParcel(GasBoxShipmentViewBean bean)
	throws BaseException {
		Collection inArgs = new Vector();
		if (bean.getCurrentCarrierName() != null) {
			inArgs.add(bean.getCurrentCarrierName().trim());
		} else {
			inArgs.add("");
		}
		if (bean.getCurrentTrackingNumber() != null) {
			inArgs.add(bean.getCurrentTrackingNumber().trim());
		} else {
			inArgs.add("");
		}
		if (bean.getShipViaCompanyId() != null) {
			inArgs.add(bean.getShipViaCompanyId());
		} else {
			inArgs.add("");
		}
		if (bean.getShipViaLocationId() != null) {
			inArgs.add(bean.getShipViaLocationId());
		} else {
			inArgs.add("");
		}
		/*if (bean.getCustomerPoNo() != null) {
			inArgs.add(bean.getCustomerPoNo());
		} else*/ {
			inArgs.add("");
		}

		Vector outArgs = new Vector();
		outArgs.add(new Integer(java.sql.Types.VARCHAR));

		DbManager dbManager = new DbManager(getClient());
		if(log.isDebugEnabled()) {
			log.debug("Calling p_gas_shipment_setup parcel "+inArgs+outArgs);
		}
		Vector v = (Vector) dbManager.doProcedure("p_gas_shipment_setup", inArgs,outArgs);
		return (String) v.get(0);
	}

	public Collection shipConfirmBatch(BigDecimal batchNumber)
	throws BaseException {
		Collection inArgs = new Vector();

		inArgs.add(batchNumber);

		Vector outArgs = new Vector();
		outArgs.add(new Integer(java.sql.Types.VARCHAR));
		DbManager dbManager = new DbManager(getClient());
		GenericProcedureFactory procFactory = new GenericProcedureFactory(
				dbManager);
		return procFactory.doProcedure("p_packing_ship_confirm", inArgs,
				outArgs);
	}

	/*Ths is updating the shipment ID on the box*/
	public void updateParcelShipmentId(GasBoxShipmentViewBean bean) throws BaseException {
		Collection inArgs = new Vector();

		inArgs.add(bean.getBoxId());
		inArgs.add(bean.getCurrentShipmentId());
		if (bean.getCurrentTrackingNumber() != null) {
			inArgs.add(bean.getCurrentTrackingNumber());
		} else {
			inArgs.add("");
		}
		if (bean.getCurrentCarrierName() != null) {
			inArgs.add(bean.getCurrentCarrierName());
		} else {
			inArgs.add("");
		}

		Vector outArgs = new Vector();
		outArgs.add(new Integer(java.sql.Types.VARCHAR));
		DbManager dbManager = new DbManager(getClient());
		GenericProcedureFactory procFactory = new GenericProcedureFactory(
				dbManager);
		if(log.isDebugEnabled()) {
			log.debug("call p_gas_shipment_box_link "+inArgs);
		}
		procFactory.doProcedure("p_gas_shipment_box_link", inArgs);
	}

	/*Ths is updating the shipment ID on the pallet*/
	public void updateLtlTlShipmentId(GasPalletShipmentViewBean bean) throws BaseException {
		Collection inArgs = new Vector();

		inArgs.add(bean.getPalletId());
		inArgs.add(bean.getCurrentShipmentId());
		if (bean.getCurrentTrackingNumber() != null) {
			inArgs.add(bean.getCurrentTrackingNumber());
		} else {
			inArgs.add("");
		}
		if (bean.getCurrentCarrierName() != null) {
			inArgs.add(bean.getCurrentCarrierName());
		} else {
			inArgs.add("");
		}
		if (bean.getShipViaLocationId() != null) {
			inArgs.add(bean.getShipViaLocationId());
		} else {
			inArgs.add("");
		}

		Vector outArgs = new Vector();
		outArgs.add(new Integer(java.sql.Types.VARCHAR));
		DbManager dbManager = new DbManager(getClient());
		GenericProcedureFactory procFactory = new GenericProcedureFactory(
				dbManager);
		if(log.isDebugEnabled()) {
			log.debug("call p_gas_shipment_pallet_link  "+inArgs);
		}
		procFactory.doProcedure("p_gas_shipment_pallet_link", inArgs);
	}

	public Object[] showResultLtltl(SupplierShippingInputBean pickBean) throws BaseException {
		Vector bv = (Vector) this.getSearchResultLtltl(pickBean);
		// calculate m1 and m2
		HashMap m1 = new HashMap();
		Integer i1 = null;
		String partNum = null;
		String key = null;
		HashMap idMap = new HashMap();
		GasPalletShipmentViewBean pbean = null;
		for (int i = 0; i < bv.size(); i++) {
			pbean = (GasPalletShipmentViewBean) bv.get(i);
			partNum = pbean.getPalletId()+pbean.getShipViaLocationId();

			// init shipmentid dropdown key
			key = pbean.getShipViaCompanyId() + "%%^^()-!@" +pbean.getShipViaLocationId();
			if( idMap.get(key) == null) {
				idMap.put(key, pbean);
			}

			if (m1.get(partNum) == null) {
				i1 = new Integer(0);
				m1.put(partNum, i1);
			}
			i1 = (Integer) m1.get(partNum);
			i1 = new Integer(i1.intValue() + 1);
			m1.put(partNum, i1);
		}
		/*Not giving them option to manage shipments, they enter carrier tracking
    and then I calculate the shipment Id and assign it to the pallet.*/
		/*		for(String mkey:(java.util.Set<String>)idMap.keySet()) {
			pbean = (GasPalletShipmentViewBean) idMap.get(mkey);
			pbean.setShipmentIdDropDown(this.getSearchResultLtltlDropDown(pbean.getShipViaCompanyId(),pbean.getShipViaLocationId()));
		}
		for (int i = 0; i < bv.size(); i++) {
			pbean = (GasPalletShipmentViewBean) bv.get(i);
			key = pbean.getShipViaCompanyId() + "%%^^()-!@" +pbean.getShipViaLocationId();
			pbean.setShipmentIdDropDown(((GasPalletShipmentViewBean) idMap.get(key)).getShipmentIdDropDown());
		}*/
		Object[] objs = {bv,m1};
		return objs;
	}

	public Object[] showResultParcel(SupplierShippingInputBean pickBean) throws BaseException {
		Vector bv = (Vector) this.getSearchResultParcel(pickBean);
		// calculate m1 and m2
		HashMap m1 = new HashMap();
		Integer i1 = null;
		String partNum = null;
		int j = 0;

		GasBoxShipmentViewBean pbean = null;
		for (int i = 0; i < bv.size(); i++) {
			pbean = (GasBoxShipmentViewBean) bv.get(i);
			if( pbean.getCurrentCarrierName() == null )
				pbean.setCurrentCarrierName("");
			partNum = pbean.getRadianPo()+"^^"+pbean.getUsgovTcn();
			/*      if( pbean.getSupplierSalesOrderNo() != null )
				partNum += pbean.getSupplierSalesOrderNo();
      if( pbean.getCurrentCarrierName() != null )
				partNum += pbean.getCurrentCarrierName();*/

			if (m1.get(partNum) == null) {
				i1 = new Integer(0);
				m1.put(partNum, i1);
			}
			i1 = (Integer) m1.get(partNum);
			i1 = new Integer(i1.intValue() + 1);
			m1.put(partNum, i1);

		}
		Object[] objs = {bv,m1};
		return objs;
	}

	public Collection getLtltlConfirmShipmentResult(SupplierShippingInputBean bean)
	throws BaseException {

		DbManager dbManager = new DbManager(getClient());
		GasPalletShipmentViewBeanFactory factory = new GasPalletShipmentViewBeanFactory(
				dbManager);
		SearchCriteria criteria = new SearchCriteria();

		if (bean.getSupplier() != null && !bean.getSupplier().equals(""))
			criteria.addCriterion("supplier", SearchCriterion.EQUALS, bean
					.getSupplier());
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
		criteria.addCriterion("usgovTcn", SearchCriterion.IS_NOT,null);
		criteria.addCriterion("currentCarrierName", SearchCriterion.IS_NOT,null);
		criteria.addCriterion("currentShipmentId", SearchCriterion.IS_NOT,null);
		/*		if (bean.getDeliveryTicket() != null && !bean.getDeliveryTicket().equals(""))
			criteria.addCriterion("supplierSalesOrderNo", SearchCriterion.EQUALS, bean
					.getDeliveryTicket().toString());*/
		return factory.selectDistinct(criteria);
	}

	public Collection getParcelConfirmShipmentResult(SupplierShippingInputBean bean) throws Exception {
		DbManager dbManager = new DbManager(getClient());
		GasBoxShipmentViewBeanFactory factory = new GasBoxShipmentViewBeanFactory(
				dbManager);
		SearchCriteria criteria = new SearchCriteria();

		if (bean.getSupplier() != null && !bean.getSupplier().equals(""))
			criteria.addCriterion("supplier", SearchCriterion.EQUALS, bean
					.getSupplier());
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
		criteria.addCriterion("usgovTcn", SearchCriterion.IS_NOT,null);
		criteria.addCriterion("currentCarrierName", SearchCriterion.IS_NOT,null);
		criteria.addCriterion("currentShipmentId", SearchCriterion.IS_NOT,null);
		/*		if (bean.getDeliveryTicket() != null && !bean.getDeliveryTicket().equals(""))
			criteria.addCriterion("supplierSalesOrderNo", SearchCriterion.EQUALS, bean
					.getDeliveryTicket().toString());*/
		return factory.selectDistinct(criteria);

	} //end of method

	public String shipConfirmsParcel(Collection beans1) throws BaseException {
		String err ="";
		String partNum = null;
		GasBoxShipmentViewBean bean = null;
		HashMap map = new HashMap(); // shipment id map.
		for (Object obj : beans1) {
			if (obj == null)
				continue;
			bean = (GasBoxShipmentViewBean) obj;
			partNum = bean.getRadianPo()+"^^"+bean.getUsgovTcn()+"^^";
			if(bean.getCurrentCarrierName() != null && bean.getCurrentCarrierName().length() > 0)
			{
				partNum += bean.getCurrentCarrierName()+StringHandler.emptyIfNull(bean.getCurrentTrackingNumber());

				BigDecimal id = (BigDecimal) map.get(partNum);
				String updateOk = bean.getOk();
				if ( id == null && (updateOk != null && updateOk.length() > 0) ) {
					id = new BigDecimal(shipConfirmParcel(bean));
					map.put(partNum,id);
				}
				bean.setCurrentShipmentId(id);
				if(log.isDebugEnabled()) {
					log.debug("shipmentId p_gas_shipment_setup ltl "+id);
				}
				updateParcelShipmentId(bean);
			}
		}
		return err ;
	}
	// don't use it for now, no difference.
	public String confirmShipmentParcel(Collection beans1,SupplierPackingViewBean sbean) throws BaseException {
		//		 get batchId
		DbManager dbManager = new DbManager(getClient());
		BigDecimal batchNumber = new BigDecimal(dbManager.getOracleSequence("shipping_batch_seq"));
		GasBoxShipmentViewBean bean = null;
		HashMap map = new HashMap(); // shipment id map.
		for (Object obj : beans1) {
			if (obj == null)
				continue;
			bean = (GasBoxShipmentViewBean) obj;
			if( bean.getOk()!=null && bean.getOk().equals("ok") )
				updateParcel(bean,batchNumber,sbean);
		}
		String err = confirmBatch(batchNumber);
		if(log.isDebugEnabled()) {
			log.debug("result from p_packing_ship_confirm "+err);
		}
		return err ;
	}

	public String confirmShipmentLtltl(Collection beans1,SupplierPackingViewBean sbean) throws BaseException {
		//		 get batchId
		DbManager dbManager = new DbManager(getClient());
		BigDecimal batchNumber = new BigDecimal(dbManager.getOracleSequence("shipping_batch_seq"));
		GasPalletShipmentViewBean bean = null;
		HashMap map = new HashMap(); // shipment id map.
		for (Object obj : beans1) {
			if (obj == null)
				continue;
			bean = (GasPalletShipmentViewBean) obj;
			if(bean.getOk()!=null && bean.getOk().equals("ok") )
			{
				if (!StringHandler.isBlankString(bean.getCurrentCarrierName())
						&& ! (StringHandler.isBlankString(bean.getCurrentTrackingNumber())
								&& !bean.getCurrentCarrierName().equalsIgnoreCase("Airgas Truck"))
								&& sbean.getDateDelivered() != null && sbean.getDateShipped() != null)
				{
					updatePallet(bean,batchNumber,sbean);
				}
			}
		}
		String err = confirmBatch(batchNumber);
		if(log.isDebugEnabled()) {
			log.debug("result from p_packing_ship_confirm "+err);
		}
		return err ;
	}

	// don't use it now, no difference
	public void updateParcel(GasBoxShipmentViewBean bean,BigDecimal batchId,SupplierPackingViewBean sbean) throws BaseException {
		Collection inArgs = new Vector();

		inArgs.add(bean.getCurrentShipmentId());
		inArgs.add(bean.getCurrentCarrierName());
		inArgs.add(bean.getCurrentTrackingNumber());
		if( sbean == null || sbean.getDateDelivered() == null )
			inArgs.add("");
		else
			inArgs.add(new Timestamp(sbean.getDateDelivered().getTime())); //date delivered.
		if( sbean == null || sbean.getDateShipped() == null )
			inArgs.add("");
		else
			inArgs.add(new Timestamp(sbean.getDateShipped().getTime())); //date_shipped.
		inArgs.add(batchId);

		DbManager dbManager = new DbManager(getClient());
		GenericProcedureFactory procFactory = new GenericProcedureFactory(
				dbManager);
		if(log.isDebugEnabled()) {
			log.debug("call p_gas_shipment_update  "+inArgs);
		}
		procFactory.doProcedure("p_gas_shipment_update", inArgs);
	}

	public void updatePallet(GasPalletShipmentViewBean bean,BigDecimal batchId,SupplierPackingViewBean sbean) throws BaseException {
		Collection inArgs = new Vector();

		inArgs.add(bean.getCurrentShipmentId());
		inArgs.add(bean.getCurrentCarrierName());
		inArgs.add(bean.getCurrentTrackingNumber());
		if( sbean == null || sbean.getDateDelivered() == null )
			inArgs.add("");
		else
			inArgs.add(new Timestamp(sbean.getDateDelivered().getTime())); //date delivered.
		if( sbean == null || sbean.getDateShipped() == null )
			inArgs.add("");
		else
			inArgs.add(new Timestamp(sbean.getDateShipped().getTime())); // date_shipped.
		inArgs.add(batchId);

		DbManager dbManager = new DbManager(getClient());
		GenericProcedureFactory procFactory = new GenericProcedureFactory(
				dbManager);
		if(log.isDebugEnabled()) {
			log.debug("call p_gas_shipment_update  "+inArgs);
		}
		procFactory.doProcedure("p_gas_shipment_update", inArgs);
	}

	public String confirmBatch(BigDecimal batchId) throws BaseException {
		Collection inArgs = new Vector();

		inArgs.add(batchId);

		Vector outArgs = new Vector();
		outArgs.add(new Integer(java.sql.Types.VARCHAR));
		DbManager dbManager = new DbManager(getClient());
		GenericProcedureFactory procFactory = new GenericProcedureFactory(
				dbManager);
		Vector v = (Vector) procFactory.doProcedure("p_packing_ship_confirm", inArgs,outArgs);
		if(log.isDebugEnabled()) {
			log.debug("call p_packing_ship_confirm  "+inArgs+outArgs);
		}
		return (String) v.get(0);
	}

}
