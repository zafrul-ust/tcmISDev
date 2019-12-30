package com.tcmis.client.common.process;

import java.util.Collection;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.catalog.process.CatalogProcess;
import com.tcmis.client.common.beans.AccountSysHeaderViewBean;
import com.tcmis.client.common.beans.ChargeNoBean;
import com.tcmis.client.common.beans.ChargeNumberSetUpInputBean;
import com.tcmis.client.common.beans.PrRulesFacilityViewBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.CustomerUpdateInputAddressBean;

/******************************************************************************
 * Process for Charge Number Set Up 
 * @version 1.0
 *****************************************************************************/
public class ChargeNumberSetUpProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());
  ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());

  public ChargeNumberSetUpProcess(String client) {
    super(client);
  }
	
  public ChargeNumberSetUpProcess(String client, String locale) {
    super(client,locale);
  }

  public Collection<PrRulesFacilityViewBean> getDropdown(PersonnelBean personnelBean) 
	throws BaseException {

		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new PrRulesFacilityViewBean());
		SearchCriteria criteria = new SearchCriteria();
		
		try {

			criteria.addCriterion("personnelId", SearchCriterion.EQUALS, Integer.toString(personnelBean.getPersonnelId()));

			SortCriteria sort = new SortCriteria();
			sort.setSortAscending(true);		
			sort.addCriterion("facilityName,accountSysName,chargeType");		  	
			return factory.select(criteria, sort, "pr_rules_facility_view");
		}  finally {
			dbManager = null;
			factory = null;
		}		
  }
  
  public Collection<AccountSysHeaderViewBean> getHeaderLabels(ChargeNumberSetUpInputBean inputBean) 
	throws BaseException {

		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new AccountSysHeaderViewBean());
		SearchCriteria criteria = new SearchCriteria();
		
		try {
			criteria.addCriterion("accountSysId", SearchCriterion.EQUALS, inputBean.getAccountSysId());
			String[] chargeInfo = inputBean.getChargeType().split(";");
			criteria.addCriterion("chargeType", SearchCriterion.EQUALS, chargeInfo[0]);

			SortCriteria sort = new SortCriteria();
			return factory.select(criteria, null, "ACCOUNT_SYS_HEADERS_VIEW");
		}  finally {
			dbManager = null;
			factory = null;
		}		
  }
  
  public Collection<ChargeNoBean> getSearchResult(ChargeNumberSetUpInputBean inputBean) throws BaseException {
	  
	    DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new ChargeNoBean());
		Collection<ChargeNoBean> c = null;
		
		String[] chargeInfo = inputBean.getChargeType().split(";");
		
		StringBuilder query= new StringBuilder("select * from table (pkg_account_sys.fx_get_tbl_charge_no('', '");
		query.append(inputBean.getAccountSysId()).append( "', '" ).append(chargeInfo[0]).append("', '");
		query.append(StringHandler.emptyIfNull(inputBean.getChargeNumber1()) ).append("', '");
		query.append(StringHandler.emptyIfNull(inputBean.getChargeNumber2()) ).append("', '");
		query.append(StringHandler.emptyIfNull(inputBean.getChargeNumber3()) ).append("', '");
		query.append(StringHandler.emptyIfNull(inputBean.getChargeNumber4()) ).append("', '");
		query.append(StringHandler.emptyIfNull(chargeInfo[1])).append("'))");

		c = factory.selectQuery(query.toString());

		return c;
  }
  
  public Vector update(ChargeNumberSetUpInputBean inputBean, Collection<ChargeNoBean> beans, PersonnelBean personnelBean) throws BaseException {
	  Vector errorMessages = new Vector();
	  String errorMsg = "";
	  String[] chargeInfo;
	  CatalogProcess catalogProcess = new CatalogProcess(this.getClient(),this.getLocale());

      if("N".equals(inputBean.getDependent())) {
		// If it is not dependent, the user can only add new charge numbers.
		for (ChargeNoBean bean : beans) {
            CatalogInputBean catalogInputBean = new CatalogInputBean();
			catalogInputBean.setCompanyId(inputBean.getCompanyId());
			catalogInputBean.setAccountSysId(inputBean.getAccountSysId());
			catalogInputBean.setDescription(bean.getDescription());
			chargeInfo = inputBean.getChargeType().split(";");
			catalogInputBean.setChargeType(chargeInfo[0]);
            catalogInputBean.setChargeId(chargeInfo[1]);
            StringBuilder chargeNumbersForValidate = null;
			StringBuilder chargeNumbersMsg = new StringBuilder(bean.getChargeNumber1());
            int startingPoint = 1;
            if (bean.getChargeNo1Alias() != null)
                startingPoint = bean.getChargeNo1Alias().intValue();
            //build charge number string for valid
            switch(startingPoint)
            {
                case 1:
                    chargeNumbersForValidate = new StringBuilder(bean.getChargeNumber1());
                    appendOtherChargeNumbers(bean, chargeNumbersForValidate, chargeNumbersMsg);
                break;
                case 2:
                    chargeNumbersForValidate = new StringBuilder("!");
                    chargeNumbersForValidate.append(bean.getChargeNumber1());
                    appendOtherChargeNumbers(bean, chargeNumbersForValidate, chargeNumbersMsg);
                break;
                case 3:
                    chargeNumbersForValidate = new StringBuilder("!!");
                    chargeNumbersForValidate.append(bean.getChargeNumber1());
                    appendOtherChargeNumbers(bean, chargeNumbersForValidate, chargeNumbersMsg);
                break;
                case 4:
                    chargeNumbersForValidate = new StringBuilder("!!!");
                    chargeNumbersForValidate.append(bean.getChargeNumber1());
                break;
            }
            chargeNumbersForValidate.append("!100");

            //build charge number string for insert
            StringBuilder chargeNumbersForInsert = new StringBuilder(bean.getChargeNumber1());
			if(bean.getChargeNumber2() != null && bean.getChargeNumber2().length() > 0) {
				chargeNumbersForInsert.append("!").append(bean.getChargeNumber2());
			}
			if(bean.getChargeNumber3() != null && bean.getChargeNumber3().length() > 0) {
				chargeNumbersForInsert.append("!").append(bean.getChargeNumber3());
			}
			if(bean.getChargeNumber4() != null && bean.getChargeNumber4().length() > 0) {
				chargeNumbersForInsert.append("!").append(bean.getChargeNumber4());
			}
			chargeNumbersForInsert.append("!100");

            //set charge number for validate
			catalogInputBean.setChargeNumbers(chargeNumbersForValidate.toString());
			String errorVal = "OK";
            //since we are using the below method to valid for input
            //need to set ignoreNullChargeNumber flag
            catalogInputBean.setIgnoreNullChargeNumber("Y");
            errorVal = catalogProcess.validateChargeNumbers(catalogInputBean);
            //need to set the chargeNumbers for insert string
            catalogInputBean.setChargeNumbers(chargeNumbersForInsert.toString());
			
            if ("Failed".equals(errorVal)) {
				CabinetDefinitionManagementProcess cabinetDMProcess = new CabinetDefinitionManagementProcess(this.getClient(),this.getLocale());
                String error = cabinetDMProcess.addChargeNumberFromSetup(catalogInputBean, personnelBean);
                if(!"OK".equals(error)) {
					errorMsg = library.getString("label.failaddingchargenumber")+" "+chargeNumbersMsg.toString();
					errorMessages.add(errorMsg);
				}
			}else if ("OK".equals(errorVal)) {
                errorMsg = library.getString("label.chargenumbersexisted")+" "+chargeNumbersMsg.toString();
				errorMessages.add(errorMsg);    
            }else {
                if (!StringHandler.isBlankString(errorVal))
                    errorMessages.add(errorVal);
			}
		}
	  }
	  // This is to update or add charge numbers for dependent one.
	  else {
		  for (ChargeNoBean bean : beans) {
              if(("New".equals(bean.getChanged())) ||
				 ("true".equals(bean.getStatus()) && !"A".equals(bean.getOriginalStatus())) ||
				 (!"true".equals(bean.getStatus()) && "A".equals(bean.getOriginalStatus())) ||
				 (!bean.getChargeNumber1().equals(bean.getOriginalChargeNo1())) ||   
				 (bean.getChargeNumber2() != null && bean.getChargeNumber2().length() > 0 && !bean.getChargeNumber2().equals(bean.getOriginalChargeNo2())) ||
				 (bean.getChargeNumber3() != null && bean.getChargeNumber3().length() > 0 && !bean.getChargeNumber3().equals(bean.getOriginalChargeNo3())) ||
				 (bean.getChargeNumber4() != null && bean.getChargeNumber4().length() > 0 && !bean.getChargeNumber4().equals(bean.getOriginalChargeNo4())) ||
				 (bean.getDescription()!= null && bean.getDescription().length() > 0 && !bean.getDescription().equals(bean.getOriginalDescription()))
				 ) {
				  // Update data
				  CatalogInputBean catalogInputBean = new CatalogInputBean();
				  catalogInputBean.setDescription(bean.getDescription());
				  BeanHandler.copyAttributes(bean, catalogInputBean);
				  catalogInputBean.setCompanyId(inputBean.getCompanyId());
				  catalogInputBean.setAccountSysId(inputBean.getAccountSysId());
				  chargeInfo = inputBean.getChargeType().split(";");
				  catalogInputBean.setChargeType(chargeInfo[0]);
				  catalogInputBean.setChargeId(chargeInfo[1]);
                  StringBuilder chargeNumbersForValidate = null;
                  StringBuilder chargeNumbersMsg = new StringBuilder(bean.getChargeNumber1());
                  int startingPoint = 1;
                  if (bean.getChargeNo1Alias() != null)
                    startingPoint = bean.getChargeNo1Alias().intValue();
                  //build charge number string for valid
                  switch(startingPoint)
                    {
                        case 1:
                            chargeNumbersForValidate = new StringBuilder(bean.getChargeNumber1());
                            appendOtherChargeNumbers(bean, chargeNumbersForValidate, chargeNumbersMsg);
                        break;
                        case 2:
                            chargeNumbersForValidate = new StringBuilder("!");
                            chargeNumbersForValidate.append(bean.getChargeNumber1());
                            appendOtherChargeNumbers(bean, chargeNumbersForValidate, chargeNumbersMsg);
                        break;
                        case 3:
                            chargeNumbersForValidate = new StringBuilder("!!");
                            chargeNumbersForValidate.append(bean.getChargeNumber1());
                            appendOtherChargeNumbers(bean, chargeNumbersForValidate, chargeNumbersMsg);
                        break;
                        case 4:
                            chargeNumbersForValidate = new StringBuilder("!!!");
                            chargeNumbersForValidate.append(bean.getChargeNumber1());
                        break;
                  }
                  chargeNumbersForValidate.append("!100");

                  //build charge number string for insert
                  StringBuilder chargeNumbersForInsert = new StringBuilder(bean.getChargeNumber1());
                  if(bean.getChargeNumber2() != null && bean.getChargeNumber2().length() > 0) {
                        chargeNumbersForInsert.append("!").append(bean.getChargeNumber2());
                  }
                  if(bean.getChargeNumber3() != null && bean.getChargeNumber3().length() > 0) {
                        chargeNumbersForInsert.append("!").append(bean.getChargeNumber3());
                  }
                  if(bean.getChargeNumber4() != null && bean.getChargeNumber4().length() > 0) {
                        chargeNumbersForInsert.append("!").append(bean.getChargeNumber4());
                  }
                  chargeNumbersForInsert.append("!100");

                  //set charge number for validate
                  catalogInputBean.setChargeNumbers(chargeNumbersForValidate.toString());
                  String errorVal = "OK";
                  //since we are using the below method to valid for input
                  //need to set ignoreNullChargeNumber flag
                  catalogInputBean.setIgnoreNullChargeNumber("Y");
                  errorVal = catalogProcess.validateChargeNumbers(catalogInputBean);
                  //need to set the chargeNumbers for insert string
                  catalogInputBean.setChargeNumbers(chargeNumbersForInsert.toString());
				  if (("Failed".equals(errorVal) && "Y".equals(bean.getChanged())) ||
					   ("OK".equals(errorVal) && "Y".equals(bean.getChanged()) && "true".equals(bean.getStatus()) && !"A".equals(bean.getOriginalStatus()))	||
					   ("OK".equals(errorVal) && "Y".equals(bean.getChanged()) && !"true".equals(bean.getStatus()) && "A".equals(bean.getOriginalStatus())) ||
					   ("OK".equals(errorVal) && "Y".equals(bean.getChanged()) && !bean.getDescription().equals(bean.getOriginalDescription()))) 
					   {
					    DbManager dbManager;
					    GenericSqlFactory factory;
					    try{
					    	// Update Charge_Number table 
							 dbManager = new DbManager(getClient(),this.getLocale());
							 factory = new GenericSqlFactory(dbManager,new CustomerUpdateInputAddressBean());
							 String status = "true".equals(bean.getStatus()) ? "A" : "I";
							 StringBuilder query = new StringBuilder("update charge_number set status = '").append(status).append("'");
							 query.append(", charge_number_1 = '").append(bean.getChargeNumber1()).append("'");
							 if(bean.getChargeNumber2() != null && bean.getChargeNumber2().length() > 0)
								 query.append(", charge_number_2 = '").append(bean.getChargeNumber2()).append("'");
							 if(bean.getChargeNumber3() != null && bean.getChargeNumber3().length() > 0)
								 query.append(", charge_number_3 = '").append(bean.getChargeNumber3()).append("'");
							 if(bean.getChargeNumber4() != null && bean.getChargeNumber4().length() > 0)
								 query.append(", charge_number_4 = '").append(bean.getChargeNumber4()).append("' ");				 
							 query.append(", last_updated_by = ").append(personnelBean.getPersonnelId());
							 query.append(", description = ").append((StringHandler.isBlankString(bean.getDescription())?"null":"'" + bean.getDescription() + "'"));
							 query.append(", last_updated_on  = sysdate");					 
							 query.append(" where charge_id = '").append(bean.getChargeId1()).append("' ");
							 query.append(" and charge_number_1 = '").append(bean.getOriginalChargeNo1()).append("' ");
							 if(bean.getOriginalChargeNo2() != null && bean.getOriginalChargeNo2().length() > 0)
								 query.append(" and charge_number_2 = '").append(bean.getOriginalChargeNo2()).append("' ");
							 if(bean.getOriginalChargeNo3() != null && bean.getOriginalChargeNo3().length() > 0)
								 query.append(" and charge_number_3 = '").append(bean.getOriginalChargeNo3()).append("' ");
							 if(bean.getOriginalChargeNo4() != null && bean.getOriginalChargeNo4().length() > 0)
								 query.append(" and charge_number_4 = '").append(bean.getOriginalChargeNo4()).append("' ");
							 int i = factory.deleteInsertUpdate(query.toString());
							// Update Directed_Charge table 
							 StringBuilder query1 = new StringBuilder("update directed_charge set charge_number_1 = '").append(bean.getChargeNumber1()).append("'");
							 if(bean.getChargeNumber2() != null && bean.getChargeNumber2().length() > 0)
								 query1.append(", charge_number_2 = '").append(bean.getChargeNumber2()).append("'");
							 if(bean.getChargeNumber3() != null && bean.getChargeNumber3().length() > 0)
								 query1.append(", charge_number_3 = '").append(bean.getChargeNumber3()).append("'");
							 if(bean.getChargeNumber4() != null && bean.getChargeNumber4().length() > 0)
								 query1.append(", charge_number_4 = '").append(bean.getChargeNumber4()).append("' ");
							 query1.append(" where facility_id = '").append(inputBean.getFacilityId()).append("' ");
							 query1.append(" and account_sys_id = '").append(inputBean.getAccountSysId()).append("' ");
							 chargeInfo = inputBean.getChargeType().split(";");
							 query1.append(" and charge_type = '").append(chargeInfo[0]).append("' ");
							 query1.append(" and lower(charge_number_1) = lower('").append(bean.getOriginalChargeNo1()).append("') ");
							 if(bean.getOriginalChargeNo2() != null && bean.getOriginalChargeNo2().length() > 0)
								 query1.append(" and lower(charge_number_2) = lower('").append(bean.getOriginalChargeNo2()).append("') ");
							 if(bean.getOriginalChargeNo3() != null && bean.getOriginalChargeNo3().length() > 0)
								 query1.append(" and lower(charge_number_3) = lower('").append(bean.getOriginalChargeNo3()).append("') ");
							 if(bean.getOriginalChargeNo4() != null && bean.getOriginalChargeNo4().length() > 0)
								 query1.append(" and lower(charge_number_4 )= lower('").append(bean.getOriginalChargeNo4()).append("') ");
							 int j = factory.deleteInsertUpdate(query1.toString());
					    } catch(Exception ex) {
					    	errorMsg = library.getString("label.failupdatingchargenumber")+" "+chargeNumbersMsg.toString();
							errorMessages.add(errorMsg);
					    }finally{
							dbManager = null;
							factory = null;
					    }
				  }else if("Failed".equals(errorVal) && "New".equals(bean.getChanged())) {
					    CabinetDefinitionManagementProcess cabinetDMProcess = new CabinetDefinitionManagementProcess(this.getClient(),this.getLocale());
					    String error = cabinetDMProcess.addChargeNumberFromSetup(catalogInputBean, personnelBean);
					    if("DUP".equals(error))
					    {
							  errorMsg = library.getString("label.chargenumbersexisted")+" "+chargeNumbersMsg.toString();
							  errorMessages.add(errorMsg);
					    }
					    else if(!"OK".equals(error)) {
							errorMsg = library.getString("label.failaddingchargenumber")+" "+chargeNumbersMsg.toString();
							errorMessages.add(errorMsg);
						}
						
				  } else if("OK".equals(errorVal)){
					  errorMsg = library.getString("label.chargenumbersexisted")+" "+chargeNumbersMsg.toString();
					  errorMessages.add(errorMsg);
				  } else {
                    if (!StringHandler.isBlankString(errorVal))
                        errorMessages.add(errorVal);
                  }
			  }
		  }

	  }

	  return (errorMessages.size() > 0 ? errorMessages : null);
  }
  
  public ExcelHandler getExcelReport(AccountSysHeaderViewBean headers, Collection<ChargeNoBean> data, Locale locale) throws
      NoDataException, BaseException, Exception {
    
	ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
	ExcelHandler pw = new ExcelHandler(library);

	pw.addTable();
//write column headers
	pw.addRow();

	pw.addCellKeyBold("label.active");
	
	int count = 0;
	if(headers.getChargeLabel1() != null && headers.getChargeLabel1().length() > 0){
		pw.addCellBold(headers.getChargeLabel1());
		count++;
	}
	if(headers.getChargeLabel2() != null && headers.getChargeLabel2().length() > 0) {
		pw.addCellBold(headers.getChargeLabel2());
		count++;
  	}
	if(headers.getChargeLabel3() != null && headers.getChargeLabel3().length() > 0) {
		pw.addCellBold(headers.getChargeLabel3());
		count++;
	}
	if(headers.getChargeLabel4() != null && headers.getChargeLabel4().length() > 0) {
		pw.addCellBold(headers.getChargeLabel4());
		count++;
  	}
	String[] headerkeys = {};
	int[] widths = {
				0,30,30,30,30};
	pw.applyColumnHeader(headerkeys, null, widths);
	pw.applyColumnWidth();
	
	for (ChargeNoBean member : data) {
	  
      pw.addRow();
      pw.addCell(member.getStatus());
      if(member.getChargeNumber1() != null && member.getChargeNumber1().length() > 0)
    	  pw.addCell(member.getChargeNumber1());
      if(member.getChargeNumber2() != null && member.getChargeNumber2().length() > 0)
    	  pw.addCell(member.getChargeNumber2());
      if(member.getChargeNumber3() != null && member.getChargeNumber3().length() > 0)
    	  pw.addCell(member.getChargeNumber3());
      if(member.getChargeNumber4() != null && member.getChargeNumber4().length() > 0)
    	  pw.addCell(member.getChargeNumber4());
    }
    return pw;
  }
  
  public String getInUseCount(ChargeNumberSetUpInputBean inputBean) throws BaseException {
	  
	    DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);
		
		StringBuilder query= new StringBuilder("select count(*) from directed_charge where facility_id = '").append(inputBean.getFacilityId());
		query.append("' and account_sys_id = '").append(inputBean.getAccountSysId());
		String[] chargeInfo = inputBean.getChargeType().split(";");
		query.append( "' and charge_type = '" ).append(chargeInfo[0]).append("'");
		if(inputBean.getChargeNumber1() != null && inputBean.getChargeNumber1().length() > 0)
			query.append( " and lower(charge_number_1) = lower('" ).append(StringHandler.emptyIfNull(inputBean.getChargeNumber1()) ).append("')");
		if(inputBean.getChargeNumber2() != null && inputBean.getChargeNumber2().length() > 0)
			query.append( " and lower(charge_number_2) = lower('" ).append(StringHandler.emptyIfNull(inputBean.getChargeNumber2()) ).append("')");
		if(inputBean.getChargeNumber3() != null && inputBean.getChargeNumber3().length() > 0)
			query.append( " and lower(charge_number_3) = lower('" ).append(StringHandler.emptyIfNull(inputBean.getChargeNumber3()) ).append("')");
		if(inputBean.getChargeNumber4() != null && inputBean.getChargeNumber4().length() > 0)
			query.append( " and lower(charge_number_4) = lower('" ).append(StringHandler.emptyIfNull(inputBean.getChargeNumber4()) ).append("')");

		String c = factory.selectSingleValue(query.toString());

		return c;
  }
  
  private void appendOtherChargeNumbers(ChargeNoBean bean, StringBuilder chargeNumbers, StringBuilder chargeNumbersMsg)
  {	  
		if(bean.getChargeNumber2() != null && bean.getChargeNumber2().length() > 0) {
			chargeNumbers.append("!").append(bean.getChargeNumber2());
			chargeNumbersMsg.append(", ").append(bean.getChargeNumber2());
		}
		
		if(bean.getChargeNumber3() != null && bean.getChargeNumber3().length() > 0) {
			chargeNumbers.append("!").append(bean.getChargeNumber3());
			chargeNumbersMsg.append(", ").append(bean.getChargeNumber3());
		}
		
		if(bean.getChargeNumber4() != null && bean.getChargeNumber4().length() > 0) {
			chargeNumbers.append("!").append(bean.getChargeNumber4());
			chargeNumbersMsg.append(", ").append(bean.getChargeNumber4());
		}  	
	  
  }
}
