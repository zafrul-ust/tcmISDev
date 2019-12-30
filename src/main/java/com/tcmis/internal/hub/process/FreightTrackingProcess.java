package com.tcmis.internal.hub.process;


import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.logging.*;

import com.tcmis.common.db.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.internal.hub.beans.HubTrackingNumbersViewBean;
import com.tcmis.internal.hub.beans.UserHubCarrierViewBean;
import com.tcmis.client.common.beans.PointOfSaleInventoryViewBean;

/******************************************************************************
 * Process for FreightTrackingProcess
 * @version 1.0
 *****************************************************************************/
public class FreightTrackingProcess extends BaseProcess {
  	Log log = LogFactory.getLog(this.getClass());

  	public FreightTrackingProcess(String client, String locale) {
   	super(client,locale);
  	}

	public String updateData(PersonnelBean personnelBean, Collection beans) throws BaseException {
		String result = "OK";
		
		try {
			DbManager dbManager = new DbManager(getClient(),this.getLocale());
			GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager,new HubTrackingNumbersViewBean());
			Iterator iter = beans.iterator();
			
			while(iter.hasNext()) {
				HubTrackingNumbersViewBean bean = (HubTrackingNumbersViewBean)iter.next();
				
				if (StringHandler.isBlankString(bean.getOk())) continue;
				
				Collection cin = new Vector(4);
				
				cin.add(bean.getHub());
				cin.add(bean.getCarrierName());
				cin.add(bean.getFirstTrackingNumber());
				cin.add(bean.getLastTrackingNumber());
				
				if(!StringHandler.isBlankString(bean.getTrackingNumberPrefix()))
					cin.add(bean.getTrackingNumberPrefix());
				
				Collection cout = new Vector(1);
				cout.add(new Integer(java.sql.Types.VARCHAR));     //error value
				
				Collection procedureData = genericSqlFactory.doProcedure("pkg_freight_advice.p_set_hub_tracking_numbers", cin, cout);
				Iterator i11 = procedureData.iterator();
				
				while (i11.hasNext()) {
					Object tmp = i11.next();
					if (tmp != null) {
						result = tmp.toString();
					}
				}
				
				if (!"OK".equalsIgnoreCase(result) && !"2".equalsIgnoreCase(result)) {
				  String esub = "Error while calling procedure to p_set_hub_tracking_numbers (hub:carrier "+bean.getHub()+":"+bean.getCarrierName()+")";
				  String emsg = "Procedure: p_set_hub_tracking_numbers has an error.\n User :"+personnelBean.getPersonnelId();
				  
				  ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
				  
				  result = library.getString("label.freighttracking.error."+result);
				  emsg += result;
				  
				  MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",esub,emsg);
				}else {
					result = "OK";
				}
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
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

	public Collection getSearchData(HubTrackingNumbersViewBean inputBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),this.getLocale());
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager,new HubTrackingNumbersViewBean());
		
		StringBuilder query = new StringBuilder("SELECT * FROM hub_tracking_numbers_view");
		StringBuilder queryCriteria = new StringBuilder();
		
		if (!StringHandler.isBlankString(inputBean.getHub())) {
			queryCriteria.append("hub").append(SearchCriterion.EQUALS).append(SqlHandler.delimitString(inputBean.getHub()));
		}
		
		if (!StringHandler.isBlankString(inputBean.getCarrierName()) && !(inputBean.getCarrierName().equalsIgnoreCase("All"))) {
			if(queryCriteria.length() > 0)
				queryCriteria.append(" AND ");
			
			queryCriteria.append("carrier_name").append(SearchCriterion.EQUALS).append(SqlHandler.delimitString(inputBean.getCarrierName()));
		}
		
		if (inputBean.isLowBatchesOnly()) {
			if(queryCriteria.length() > 0)
				queryCriteria.append(" AND ");
			
			queryCriteria.append("curr_tracking_number ").append(SearchCriterion.GREATER_THAN_OR_EQUAL_TO).append("triggering_number");		
			queryCriteria.append(" AND ");
			
			if(inputBean.isIncludeExpired()) {
				queryCriteria.append("status IN (").append(SqlHandler.delimitString("CURRENT")).append(",").append(SqlHandler.delimitString("EXPIRED")).append(")");
			}
			else {
				queryCriteria.append("status ").append(SearchCriterion.EQUALS).append(SqlHandler.delimitString("CURRENT"));
			}
		}
		else if (!inputBean.isIncludeExpired()) {
			if(queryCriteria.length() > 0)
				queryCriteria.append(" AND ");
		
			queryCriteria.append("status ").append(SearchCriterion.NOT_EQUAL).append(SqlHandler.delimitString("EXPIRED"));
		}
		
		if (queryCriteria.length() > 0) {
			query.append(" WHERE ");
			query.append(queryCriteria.toString());
		}
		
		return genericSqlFactory.selectQuery(query.toString()); 
		  
		 
	} //end of method

	public Collection getDropdownData(PersonnelBean personnelBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),this.getLocale());
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager,new UserHubCarrierViewBean());
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("personnelId", SearchCriterion.EQUALS, (new BigDecimal(personnelBean.getPersonnelId())).toString());
		
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

  public ExcelHandler getExcelReport(HubTrackingNumbersViewBean bean, Locale locale) throws NoDataException, BaseException, Exception {
	  ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
	  Collection<HubTrackingNumbersViewBean> data = this.getSearchData(bean);
	  ExcelHandler pw = new ExcelHandler(library);

	  pw.addTable();

	  //	  write column headers
	  pw.addRow();
	  pw.addCellKeyBold("label.hub");
	  pw.addCell(bean.getHubName());

	  pw.addRow();
	  pw.addCellKeyBold("label.carrier");
	  pw.addCell(bean.getCarrierName());

	  //blank row
	  pw.addRow();

	  //result table
	  //write column headers
	  pw.addRow();

	  /*Pass the header keys for the Excel.*/
	  String[] headerkeys = {"label.hub","label.batchnumber","label.carrier","label.prefix","label.starttrackingnumber", "label.endtrackingnumber","label.currenttrackingnumber","label.status","label.checkdigit"};

	  /*This array defines the type of the excel column.
	    0 means default depending on the data type. */
	  int[] types = {0,0,0,0,0,0,0,0,0};
   
	  /*This array defines the default width of the column when the Excel file opens.
   		0 means the width will be default depending on the data type.*/
	  int[] widths = {0,0,0,0,0,0,0,0,0};
   
	  /*This array defines the horizontal alignment of the data in a cell.
   		0 means excel defaults the horizontal alignemnt by the data type.*/
	  int[] horizAligns = {0,0,0,0,0,0,0,0,0};

	  pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
	  
	  //now write data
	  for (HubTrackingNumbersViewBean member : data) {
		  pw.addRow();
		  pw.addCell(member.getHubName());
		  pw.addCell(member.getBatchNumber());
		  pw.addCell(member.getCarrierName());
		  pw.addCell(member.getTrackingNumberPrefix());
		  pw.addCell(member.getFirstTrackingNumber());
		  pw.addCell(member.getLastTrackingNumber());
		  pw.addCell(member.getCurrTrackingNumber());
		  pw.addCell(member.getStatus());
		  pw.addCell(!StringHandler.isBlankString(member.getCheckdigitFunction()) ? "Y" : "N");
	  }
   
	  return pw;
 } //end of method

}
