package com.tcmis.internal.hub.process;


import java.math.BigDecimal;
import java.util.*;
import java.sql.Timestamp;

import org.apache.commons.logging.*;

import com.tcmis.common.db.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.internal.hub.beans.UserHubCarrierViewBean;
import com.tcmis.internal.hub.beans.FreightTlTrackingViewBean;

/******************************************************************************
 * Process for TruckLoadFreightProcess
 * @version 1.0
 *****************************************************************************/
public class TruckLoadFreightProcess extends BaseProcess {
  	Log log = LogFactory.getLog(this.getClass());
  	ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());

  	public TruckLoadFreightProcess(String client, String locale) {
   	super(client,locale);
  	}

	public Collection getHazmatDetail(FreightTlTrackingViewBean inputBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),this.getLocale());
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager,new FreightTlTrackingViewBean());
		String query = "select distinct iata_identification from part_dot_view where item_id in ( select distinct item_id"+
						   " from rli, daily_freight_advice where pr_number = freight_advice_number and line_item = freight_advice_line"+
							" and hazardOus_flag = 'Y' and consolidation_number = '"+inputBean.getConsolidationNumber()+"')";
		return genericSqlFactory.selectQuery(query);
	} //end of method

	public String updateData(PersonnelBean personnelBean, FreightTlTrackingViewBean inputBean, Collection beans) throws BaseException {
		String result = "OK";
		try {
			DbManager dbManager = new DbManager(getClient(),this.getLocale());
			GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager,new FreightTlTrackingViewBean());
			Iterator iter = beans.iterator();
			boolean dataChanged = false;
			while(iter.hasNext()) {
				FreightTlTrackingViewBean bean = (FreightTlTrackingViewBean)iter.next();
				if (StringHandler.isBlankString(bean.getOk())) continue;
				if (!StringHandler.isBlankString(bean.getConsolidationNumber())) {
					//update current consolidation number
					Collection cin = new Vector(8);
					cin.add(bean.getConsolidationNumber());
					if (StringHandler.isBlankString(bean.getTrackingNumber())) {
						cin.add("");
					}else {
						cin.add(bean.getTrackingNumber());
					}
					cin.add(bean.getCarrierName());
					cin.add(bean.getCarrierCode());
					//cin.add(bean.getScheduledShipDate());
					cin.add(new Timestamp(((java.util.Date)bean.getScheduledShipDate()).getTime()));
					cin.add(bean.getTransportationMode());
					if ("Single".equalsIgnoreCase(bean.getOrderCount())) {
						cin.add(bean.getQuantity().multiply(bean.getItemWeight()));
						//if this is a split line then set to "Y" else set to "N"
						//need to setSplitLine() from jsp and js
						if ("Y".equalsIgnoreCase(bean.getSplitLine())) {
							cin.add("Y");
						}else {
							cin.add("N");
						}


					}else {
						cin.add(bean.getOriginalShippingWeight());
						cin.add("N");
					}
					cin.add(bean.getQuantity());
					genericSqlFactory.doProcedure("pkg_freight_advice.P_UPDATE_TL_TRACKING_NUMBER", cin);
					dataChanged = true;
				}else {
					//split lines
					Collection cin = new Vector(8);
					cin.add(bean.getOriginalConsolidationNumber());
					cin.add(bean.getConsolidationNumber());
					if (StringHandler.isBlankString(bean.getTrackingNumber())) {
						cin.add("");
					}else {
						cin.add(bean.getTrackingNumber());
					}
					cin.add(bean.getCarrierName());
					cin.add(bean.getCarrierCode());
					//cin.add(bean.getScheduledShipDate());
					cin.add(new Timestamp(((java.util.Date)bean.getScheduledShipDate()).getTime()));
					cin.add(bean.getTransportationMode());
					cin.add(bean.getQuantity().multiply(bean.getItemWeight()));
					cin.add(bean.getQuantity());
					cin.add(bean.getOriginalQuantity());
					cin.add(bean.getOriginalPalletCount());
					cin.add(bean.getShippingUnits());
					genericSqlFactory.doProcedure("pkg_freight_advice.P_INSERT_TL_TRACKING_NUMBER", cin);
					dataChanged = true;
				}
			}
			if (dataChanged) {
				Collection cin = new Vector(0);
				genericSqlFactory.doProcedure("pkg_freight_advice.P_INSERT_FREIGHT_ADVICE", cin);
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Vector cancelData(PersonnelBean personnelBean, FreightTlTrackingViewBean inputBean, Collection beans) throws BaseException {
		Vector messages = new Vector();
		try {
			DbManager dbManager = new DbManager(getClient(),this.getLocale());
			GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager,new FreightTlTrackingViewBean());
			Iterator iter = beans.iterator();
			boolean dataChanged = false;
			Vector result = new Vector();
			while(iter.hasNext()) {
				FreightTlTrackingViewBean bean = (FreightTlTrackingViewBean)iter.next();
				if (StringHandler.isBlankString(bean.getOk())) continue;
				if (!StringHandler.isBlankString(bean.getConsolidationNumber())) {
					//update current consolidation number
					Vector cin = new Vector();
					cin.add(bean.getConsolidationNumber());
					Vector cout = new Vector();
					cout.add(new Integer(java.sql.Types.VARCHAR));
					result = (Vector) genericSqlFactory.doProcedure("Pkg_freight_advice.P_CANCEL_TL", cin, cout);
					String error = (String)result.get(0);
					if ("1".equals(error)) {
						messages.add(bean.getConsolidationNumber()+" : "+library.getString("label.error1"));
					}else if ("2".equals(error)) {
						messages.add(bean.getConsolidationNumber()+" : "+library.getString("label.error2"));
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			messages.add(library.getString("generic.error"));
		}  
		return messages;
	}

	public Boolean hasEditPermission(PersonnelBean personnelBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),this.getLocale());
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
		String query = "select count(*) from user_group_member_ops_entity where user_group_id = 'TransManager' and personnel_id = "+personnelBean.getPersonnelId();
		if ("0".equalsIgnoreCase(genericSqlFactory.selectSingleValue(query))) {
			return new Boolean(false);
		}else {
			return new Boolean(true);
		}
	} //end of method

	public Collection getSearchData(FreightTlTrackingViewBean inputBean, PersonnelBean personnelBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),this.getLocale());
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager,new FreightTlTrackingViewBean());
		SearchCriteria criteria = new SearchCriteria();
		if (!StringHandler.isBlankString(inputBean.getHub())) {
			criteria.addCriterion("hub", SearchCriterion.EQUALS, inputBean.getHub());
			return genericSqlFactory.select(criteria,null,"freight_tl_tracking_view");
		}else {
			String query = "select * from freight_tl_tracking_view where hub in (select hub from user_hub where personnel_id = "+personnelBean.getPersonnelId()+")";
			return genericSqlFactory.selectQuery(query);
		}
	} //end of method

	public Collection getDropdownData(PersonnelBean personnelBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),this.getLocale());
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager,new UserHubCarrierViewBean());
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("personnelId", SearchCriterion.EQUALS, (new BigDecimal(personnelBean.getPersonnelId())).toString());
        if (personnelBean.getCompanyId() != null && personnelBean.getCompanyId().length() > 0) {
				criteria.addCriterion("companyId", SearchCriterion.EQUALS, "" + personnelBean.getCompanyId());
	    }
        SortCriteria scriteria = new SortCriteria();
		scriteria.addCriterion("companyId");
		scriteria.addCriterion("hubName");
		scriteria.addCriterion("carrierName");
		return genericSqlFactory.select(criteria,scriteria,"user_hub_carrier_view");
	} //end of method

	public Collection createDropdownRelationalObject(Collection dataColl) {
		Vector result = new Vector();
		Iterator iter = dataColl.iterator();
		String lastCompanyHub = "";
		while(iter.hasNext()) {
			UserHubCarrierViewBean bean = (UserHubCarrierViewBean)iter.next();
			String currentCompanyHub = bean.getCompanyId()+bean.getBranchPlant();
			if (lastCompanyHub.equalsIgnoreCase(currentCompanyHub)) {
				UserHubCarrierViewBean newBean = (UserHubCarrierViewBean)result.lastElement();
				Collection c = newBean.getCarrierCollection();
				UserHubCarrierViewBean newCarrierBean = new UserHubCarrierViewBean();
				newCarrierBean.setCarrierAccountId(bean.getCarrierAccountId());
				newCarrierBean.setCarrierName(bean.getCarrierName());
				c.add(newCarrierBean);
			}else {
				UserHubCarrierViewBean newBean = new UserHubCarrierViewBean();
				Collection c = new Vector();
				newBean.setCompanyId(bean.getCompanyId());
				newBean.setBranchPlant(bean.getBranchPlant());
				newBean.setHubName(bean.getHubName());
				UserHubCarrierViewBean newCarrierBean = new UserHubCarrierViewBean();
				newCarrierBean.setCarrierAccountId(bean.getCarrierAccountId());
				newCarrierBean.setCarrierName(bean.getCarrierName());
				c.add(newCarrierBean);
				newBean.setCarrierCollection(c);
				result.addElement(newBean);
			}
			lastCompanyHub = currentCompanyHub;
		}
		return result;
	} //end of method

  public ExcelHandler getExcelReport(FreightTlTrackingViewBean bean, PersonnelBean personnelBean, Locale locale) throws NoDataException, BaseException, Exception {
	ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
   Collection<FreightTlTrackingViewBean> data = this.getSearchData(bean,personnelBean);
   ExcelHandler pw = new ExcelHandler(library);
	pw.addTable();

	//	  write column headers
   pw.addRow();
   pw.addCellKeyBold("label.hub");
   pw.addCell(bean.getHubName());

	  /*
	pw.addRow();
   pw.addCellKeyBold("label.carrier");
   pw.addCell(bean.getCarrierName());
      */

	//blank row
   pw.addRow();

   //result table
   //write column headers
   pw.addRow();

   /*Pass the header keys for the Excel.*/
   String[] headerkeys = {
     "label.consolidationno","label.carrier","label.account","label.transportationmode","label.trackingnumber","label.shipdate","label.requireddate","label.shipfrom","label.shipto",
	  "label.shippingweight","label.palletcount","label.oconus","label.transportationpriority","label.haz","label.qtyperpallet","label.qty","label.ordertype"};

   /*This array defines the type of the excel column.
   0 means default depending on the data type. */
   int[] types = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
   /*This array defines the default width of the column when the Excel file opens.
   0 means the width will be default depending on the data type.*/
   int[] widths = {0,15,0,0,15,15,15,30,30,0,0,0,0,0,0,0,0};
   /*This array defines the horizontal alignment of the data in a cell.
   0 means excel defaults the horizontal alignemnt by the data type.*/
   int[] horizAligns = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

   pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

	String tmpExcelDateFormat = library.getString("java.exceldateformat");
	pw.setColumnDateFormat(5,tmpExcelDateFormat);

	//now write data
	for (FreightTlTrackingViewBean member : data) {
		pw.addRow();
		pw.addCell(member.getConsolidationNumber());
		pw.addCell(member.getCarrierName());
		pw.addCell(member.getCarrierCode());
		pw.addCell(member.getTransportationMode());
		pw.addCell(member.getTrackingNumber());
		pw.addCell(member.getScheduledShipDate());
		pw.addCell(member.getRequiredDatetime());
		pw.addCell(member.getShipFrom());
		pw.addCell(member.getShipTo());
		pw.addCell(member.getShippingWeight());
		pw.addCell(member.getPalletCount());
		pw.addCell(member.getExport());
		pw.addCell(member.getTransportationMode());
		pw.addCell(member.getHazardousFlag());
		pw.addCell(member.getItemsPerPallet());
		pw.addCell(member.getQuantity());
		pw.addCell(member.getOrderCount());
	}
   return pw;
 } //end of method

}
