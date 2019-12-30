package com.tcmis.client.operations.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.catalog.factory.CatalogPartUnitOfSaleBeanFactory;
import com.tcmis.client.operations.beans.EdiOrderStatusInputBean;
import com.tcmis.client.order.factory.CustomerPoStageBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.internal.hub.beans.EdiOrderErrorViewBean;
import com.tcmis.internal.hub.factory.EdiOrderErrorViewBeanFactory;
import com.tcmis.internal.hub.factory.EdiShiptoMappingViewBeanFactory;

/******************************************************************************

 * Process methods for EDI Order error status page

 * @version 2.0

 *****************************************************************************/

public class EdiOrderStatusProcess extends BaseProcess {

	ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());

	Log log = LogFactory.getLog(this.getClass());

	public EdiOrderStatusProcess(String client) {
		super(client);
	}

	public EdiOrderStatusProcess(String client,String locale) {
		super(client,locale);
	}

	/*
	 * Create a Collection of EDI_ORDER_ERROR_VIEW
	 */
	public Collection getSearchResult(EdiOrderStatusInputBean inputBean) throws BaseException {
		log.debug("executing EDI status qry");
		DbManager dbManager = new DbManager(this.getClient());
		EdiOrderErrorViewBeanFactory beanFactory = new EdiOrderErrorViewBeanFactory(dbManager);
		SearchCriteria searchCriteria = new SearchCriteria();
		searchCriteria.addCriterion("companyId",SearchCriterion.EQUALS,inputBean.getCompanyId());
		if(this.isBlank(inputBean.getSearchArgument()))
			searchCriteria.addCriterion("currentOrderStatus",SearchCriterion.EQUALS,"ERROR");
		else
			searchCriteria.addCriterionWithMode(inputBean.getSearchField(), inputBean.getSearchMode(), inputBean.getSearchArgument() );
		Collection errorStatusBeans = beanFactory.select(searchCriteria);
		dbManager = null;
		beanFactory = null;
		return errorStatusBeans;
	}

	/*
	 * Create a Collection of all valid ship-to's for the given company_id
	 */
	public Collection getShiptoList(String companyId) throws BaseException {
		Collection shiptoList = null;
		log.debug("executing EDI shipto list query");
		DbManager dbManager = new DbManager(this.getClient());
		EdiShiptoMappingViewBeanFactory beanFactory = new EdiShiptoMappingViewBeanFactory(dbManager);
		SearchCriteria searchCriteria = new SearchCriteria();
		searchCriteria.addCriterion("companyId",SearchCriterion.EQUALS,companyId);
		shiptoList = beanFactory.select(searchCriteria);
		dbManager = null;
		beanFactory = null;
		return shiptoList;
	}

	/*
	 * get all valid UOS for the given company
	 */
	public Collection getValidUnitOfSale(String companyId) throws BaseException {
		Collection validUOS = null;
		DbManager dbManager = new DbManager(this.getClient());
		// FacItemBeanFactory beanFactory = new FacItemBeanFactory(dbManager);
		CatalogPartUnitOfSaleBeanFactory beanFactory = new CatalogPartUnitOfSaleBeanFactory(dbManager);
		SearchCriteria searchCriteria = new SearchCriteria();
		searchCriteria.addCriterion("companyId",SearchCriterion.EQUALS,companyId);
		validUOS = beanFactory.selectDistinctUOS(searchCriteria);
		dbManager = null;
		beanFactory = null;
		return validUOS;
	}

	public void resetStatus(BigDecimal personnelId, EdiOrderStatusInputBean inputBean,Collection<EdiOrderErrorViewBean> updateBeanCollection) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);

		for(EdiOrderErrorViewBean bean: updateBeanCollection) {
			if(!bean.getOkDoReset().equals("true")) continue;

			if(bean.getUnitPriceOnOrder() == null) bean.setUnitPriceOnOrder("0");

			if ((bean.getOrderedQty().compareTo(bean.getOldOrderedQty())!=0) || !bean.getOrderedUom().equalsIgnoreCase(bean.getOldOrderedUom()) || bean.getUnitPriceOrig().compareTo(new BigDecimal(bean.getUnitPriceOnOrder()))!=0) {
				Collection inArgs = new Vector();
				inArgs.add(bean.getLoadId());
				inArgs.add(bean.getLoadLine());
				inArgs.add(bean.getLineSequence());
				inArgs.add(bean.getOrderedQty());
				inArgs.add(bean.getOrderedUom());
				inArgs.add(bean.getShiptoPartyId());
				inArgs.add(personnelId);
				inArgs.add("From web page.");
				inArgs.add(bean.getCurrencyId());
				inArgs.add(bean.getUnitPriceOnOrder());
				inArgs.add("");
				inArgs.add("");
				inArgs.add("");
				inArgs.add("");
				inArgs.add("");
				if (log.isDebugEnabled()) {
					log.debug("pkg_dbuy_from_customer.P_CREATE_PRE_860"+inArgs);
				}
				procFactory.doProcedure("pkg_dbuy_from_customer.P_CREATE_PRE_860",inArgs);
			} else if (! bean.getShiptoPartyId().equalsIgnoreCase(bean.getOldShiptoPartyId())) { // add here?? || ! catPartNo.equalsIgnoreCase(oldCatPart)
				Collection inArgs1 = new Vector();
				inArgs1.add(bean.getCompanyId());
				inArgs1.add(bean.getCustomerPoNo());
				inArgs1.add(bean.getCustomerPoLineNo());
				inArgs1.add(bean.getTransactionType());
				inArgs1.add(bean.getLineSequence());
				inArgs1.add(bean.getChangeOrderSequence());
				inArgs1.add(bean.getShiptoPartyId());
				inArgs1.add(personnelId);
				if (log.isDebugEnabled()) {
					log.debug("pkg_dbuy_from_customer.CUSTOMER_PO_SHIPTO_UPDATE"+inArgs1);
				}
				procFactory.doProcedure("pkg_dbuy_from_customer.CUSTOMER_PO_SHIPTO_UPDATE",inArgs1);
			} else {                                                                               // reset the error status
				Collection inArgs3 = new Vector();
				inArgs3.add(bean.getCompanyId());
				inArgs3.add(bean.getCustomerPoNo());
				inArgs3.add(bean.getCustomerPoLineNo());
				inArgs3.add(bean.getTransactionType());
				inArgs3.add(bean.getLineSequence());
				inArgs3.add(bean.getChangeOrderSequence());
				if (log.isDebugEnabled()) {
					log.debug("pkg_dbuy_from_customer.CUSTOMER_PO_RESET"+inArgs3);
				}
				procFactory.doProcedure("pkg_dbuy_from_customer.CUSTOMER_PO_RESET", inArgs3);
			}
		}
	}


	/*
  public void releaseLine(String companyId, String poNum, String lineNum, String txnType,
                          String lineSeq, String chgOrder, int personnelId) throws BaseException {

    DbManager dbManager = new DbManager(this.getClient());
    BigDecimal dLineSeq;
    BigDecimal dChgOrdSeq;
    BigDecimal dPersonnelId=null;
    try {
      dPersonnelId = new BigDecimal(personnelId);
      dLineSeq = new BigDecimal(lineSeq);
      dChgOrdSeq = new BigDecimal(chgOrder);
    } catch (NumberFormatException nfe) {
      dLineSeq = new BigDecimal(0);
      dChgOrdSeq = new BigDecimal(0);
      if (dPersonnelId==null) dPersonnelId = new BigDecimal(0);
      log.error("Invalid number for releaseLine()");
      return;
    }

    try {
      GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
      Vector params = new Vector(7);
      params.add(companyId);
      params.add(poNum);
      params.add(lineNum);
      params.add(txnType);
      params.add(dLineSeq);
      params.add(dChgOrdSeq);
      params.add(dPersonnelId);
      procFactory.doProcedure("pkg_dbuy_from_customer.customer_po_release", params);
      log.debug("GenericProcedureFactory::called pkg_dbuy_from_customer.customer_po_release("+poNum+","+lineNum+")");
    } catch (BaseException be) {
      log.error("Base Exception calling release line for EDI: (ProcedureFactory failed.) " + be);
      log.trace(be);
    }
  }  */

	public int setToIgnoreStatus(String companyId, String poNum, String lineNum, String loadId,
			String loadLine, String logonId) throws BaseException  {
		DbManager dbManager = new DbManager(this.getClient());
		CustomerPoStageBeanFactory factory = new CustomerPoStageBeanFactory(dbManager);
		Date now = new Date();
		String newComments = "Set to IGNORE " + DateHandler.formatOracleDate(now) + " by " + logonId;
		return factory.updateStatusIgnore(companyId, poNum, lineNum, loadId, loadLine, newComments);
	}

	/*
  public Collection getPartList(String orderedQty, String companyId, String facilityId, String catPartNo) throws BaseException {
     Collection partList = null;

     String query = "";
     query += "select fi.fac_part_no cat_part_no, ";
     query += "       fi.part_description, ";
     query += "       fi.unit_of_sale catalog_uos, ";
     query += "       fi.unit_of_sale_quantity_per_each uos_per_packaging, ";
     query += "       ROUND(" + orderedQty + " / fi.unit_of_sale_quantity_per_each, 4) mr_qty, ";
     query += "       i.item_id, ";
     query += "       fx_kit_packaging(i.item_id) tcm_packaging, ";
     query += "       i.item_desc ";
     query += "  from customer.fac_item fi, ";
     query += "       customer.catalog_part_item_group cpig, ";
     query += "       item i ";
     query += " where fi.company_id = '"+ companyId + "' ";
     query += "   and fi.facility_id = '" + facilityId + "' ";
     query += "   and nvl(fi.label_spec,fi.fac_part_no) = '" + catPartNo + "' ";
     query += "   and fi.company_id=cpig.company_id ";
     query += "   and fi.fac_part_no=cpig.cat_part_no ";
     query += "   and fi.facility_id=cpig.catalog_id ";
     query += "   and cpig.item_id=i.item_id ";
     query += "   and cpig.status in ('A','D')";

     DbManager dbManager = new DbManager(this.getClient());
     InternalPartListBeanFactory beanFactory = new InternalPartListBeanFactory(dbManager);

     partList = beanFactory.select(query);

     dbManager = null;
     beanFactory = null;

     return partList;
  }

  public String getReleaseStatus(String companyId) throws BaseException {
     String releaseStatus = "NO";
     String query = "select count(*) from edi_order_check_rule x where rule_id='MANUAL RELEASE REQUIRED' and company_id='" + companyId + "'";
     DbManager dbManager = new DbManager(this.getClient());
     EdiOrderCheckRuleBeanFactory beanFactory = new EdiOrderCheckRuleBeanFactory(dbManager);

     SearchCriteria searchCriteria = new SearchCriteria();
     searchCriteria.addCriterion("companyId",SearchCriterion.EQUALS,companyId);
     searchCriteria.addCriterion("",SearchCriterion.EQUALS,"MANUAL RELEASE REQUIRED");

     Collection status = beanFactory.select(searchCriteria);

     if (status.size()>0) {
        releaseStatus = "RELEASE";
     }
     dbManager = null;
     beanFactory = null;

     return releaseStatus;
  } */


	public ExcelHandler getExcelReport(EdiOrderStatusInputBean inputBean, Locale locale)
	throws Exception {

		Collection<EdiOrderErrorViewBean> data = getSearchResult(inputBean);
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);
		pw.addTable();

		//write column headers
		pw.addRow();

		if("USGOV".equals(inputBean.getCompanyId())) {
			String[] headerkeys = {
					"label.orderreceived","label.po","label.transactionrefnum","label.line","label.partnum",
					"label.mfg.part.num","label.facility","label.quantity","label.uom","label.currency",
					"label.unitprice","label.shipto","label.addrchangerequestid","label.addrchangetype","label.needdate",
					"label.requestor","label.status","label.errorss","label.tcm.uos","label.uos.per.ea",
					"label.mr.qty","label.tcm.packaging","label.line.note","label.po.note","label.ordertype",
					"label.company","label.shiptododaac","label.shiptoaddress","label.markfordodaac","label.markforaddress"
			};

			int[] types = {
					pw.TYPE_CALENDAR,0,0,0,0,
					0,0,0,0,0,
					pw.TYPE_NUMBER,0,0,0,pw.TYPE_CALENDAR,
					0,0,0,0,0,
					0,0,0,0,0,
					0,0,0,0,0};

			int[] widths = {
					10,15,15,6,12,
					0,15,0,3,0,
					0,0,0,0,0,
					15,0,0,0,0,
					0,20,0,0,0,
					11,0,0,0,0};

			pw.applyColumnHeader(headerkeys, types, widths, null );
		}
		else {
			String[] headerkeys = {
					"label.orderreceived","label.po","label.line","label.partnum","label.mfg.part.num",
					"label.quantity","label.uom","label.currency","label.unitprice","label.shipto",
					"label.needdate","label.requestor","label.status","label.errorss","label.tcm.uos",
					"label.uos.per.ea","label.mr.qty","label.tcm.packaging","label.line.note","label.po.note",
					"label.ordertype","label.company"
			};

			int[] types = {
					pw.TYPE_CALENDAR,0,0,0,0,
					0,0,0,0,0,
					pw.TYPE_CALENDAR,0,0,0,0,
					0,0,0,0,0,
					0,0};

			int[] widths = {
					10,12,6,12,15,
					10,6,0,0,0,
					0,12,0,0,6,
					6,0,20,0,0,
					0,11};

			pw.applyColumnHeader(headerkeys, types, widths, null );
		}

		for(EdiOrderErrorViewBean bean:data) {
			pw.addRow();

			pw.addCell(bean.getDateIssued());
			pw.addCell(bean.getCustomerPoNo());
			if("USGOV".equals(inputBean.getCompanyId()) && "DLA Gases".equals(bean.getFacilityId()) && "1".equals(bean.getCustomerPoLineNoTrim()))
				pw.addCell(bean.getTransactionRefNum());
			else if("USGOV".equals(inputBean.getCompanyId()))
				pw.addCell("");
			pw.addCell(bean.getCustomerPoLineNoTrim());
			pw.addCell(bean.getCatPartNoOnOrder());
			pw.addCell(bean.getManufacturerPartNum());
			if("USGOV".equals(inputBean.getCompanyId()))
				pw.addCell(bean.getFacilityId());
			pw.addCell(bean.getOrderedQty());
			pw.addCell(bean.getOrderedUom());
			pw.addCell(bean.getCurrencyId());
			pw.addCell(bean.getUnitPriceOnOrder());
			pw.addCell(bean.getShiptoPartyId());
			if("USGOV".equals(inputBean.getCompanyId())) {
				pw.addCell(bean.getAddressChangeRequestId());
				if("dpms".equals(bean.getAddressChangeType()) && "N".equals(bean.getSentToDpms()))
					pw.addCell(library.getString("label.verification"));
				else
					pw.addCell(bean.getAddressChangeType());
			}
			pw.addCell(bean.getRequestedDeliveryDate());
			pw.addCell(bean.getBuyerNameOnPo());
			pw.addCell(bean.getCurrentOrderStatus());
			pw.addCell(bean.getErrorDetail());
			pw.addCell(bean.getCatalogUos());
			pw.addCell(bean.getUosPerPackaging());
			pw.addCell(bean.getMrQty());
			pw.addCell(bean.getPackaging());
			pw.addCell(bean.getCustomerPoLineNote());
			pw.addCell(bean.getCustomerPoNote());
			pw.addCell(bean.getTransactionTypeDisplay());
			pw.addCell(bean.getCompanyId());
			if("USGOV".equals(inputBean.getCompanyId())) {
				pw.addCell(bean.getShipToDodaac());
				pw.addCell(bean.getShipToAddress());
				pw.addCell(bean.getMarkForDodaac());
				pw.addCell(bean.getMarkForAddress());
			}

		}
		return pw;
	}

}
