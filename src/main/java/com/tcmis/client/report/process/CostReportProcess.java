package com.tcmis.client.report.process;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.*;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.creator.CodeCreator;
import com.tcmis.common.admin.factory.VvItemTypeBeanFactory;
import com.tcmis.common.admin.beans.PersonnelBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.report.factory.*;

import java.math.BigDecimal;
import java.util.*;
import java.lang.reflect.Method;
import java.io.*;

import com.tcmis.client.catalog.beans.FacLocAppBean;
import com.tcmis.client.report.beans.*;

/**
 * ***************************************************************************
 * Process to process data for cost report.
 *
 * @version 1.0
 *          ***************************************************************************
 */
public class CostReportProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());
	String reportType = "Cost";

	public CostReportProcess(String client) {
		super(client);
	}

	public CostReportProcess(String client, Locale locale) {
		super(client);
	}

	public void fixingOldTemplates() throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		CostReportTemplateBeanFactory factory = new CostReportTemplateBeanFactory(dbManager);
		//SearchCriteria criteria = new SearchCriteria();
		//criteria.addCriterion("personnelId", SearchCriterion.EQUALS, "86030");
		//criteria.addCriterion("companyId", SearchCriterion.EQUALS, "RAYTHEON");
		//criteria.addCriterion("reportName", SearchCriterion.EQUALS, "trong73108");
		Collection coll = factory.select(null,null);
		Iterator iter = coll.iterator();
		int count = 0;
		while(iter.hasNext()) {
			CostReportTemplateBean bean = (CostReportTemplateBean)iter.next();
			String tmp = bean.getReportField();
			String newValue = "";
			if (tmp != null && tmp.length() > 0) {
				String[] tmp2 = tmp.split(";");
				for (int i = 0; i < tmp2.length; i++) {
					if ("charge_num_1".equalsIgnoreCase(tmp2[i])) {
						newValue += "charge_number_1;";
					}else if ("charge_num_2".equalsIgnoreCase(tmp2[i])) {
						newValue += "charge_number_2;";
					}else if ("po".equalsIgnoreCase(tmp2[i])) {
						newValue += "user_po;";
					}else if ("fac".equalsIgnoreCase(tmp2[i])) {
						newValue += "facility;";
					}else if ("wa".equalsIgnoreCase(tmp2[i])) {
						newValue += "work_area;";
					}else if ("waste_order_no".equalsIgnoreCase(tmp2[i])) {
						newValue += "waste_order;";
					}else if ("waste_manifest_id".equalsIgnoreCase(tmp2[i])) {
						newValue += "waste_manifest;";
					}else if ("item_type".equalsIgnoreCase(tmp2[i])) {
						newValue += "type;";
					}else if ("part_desc".equalsIgnoreCase(tmp2[i])) {
						newValue += "part_description;";
					}else if ("item_desc".equalsIgnoreCase(tmp2[i])) {
						newValue += "item_description;";
					}else if ("reference_number".equalsIgnoreCase(tmp2[i])) {
						newValue += "reference;";
					}else {
						newValue += tmp2[i].toLowerCase()+";";
					}
				}
				//removing the last ";"
				if (newValue.length() > 1) {
					newValue = newValue.substring(0,newValue.length()-1);
				}
				//update cost_report_template.report_field here
				String query = "update cost_report_template set report_field = '"+newValue+"' where personnel_id = "+bean.getPersonnelId().toString()+
				               " and company_id = '"+bean.getCompanyId()+"' and report_name = '"+bean.getReportName()+"'";
			}
		}
	}

	private String buildSalesTaxQuery(CostReportInputBean inputBean, String where) {
	  String query = "select currency_id,sales_tax_desc";
	  if (inputBean.getSqlFields().contains("percent_split_charge")) {
		 query += ",sum(sales_tax_amount) sales_tax_amount";
	  }else {
		 query += ",sum(sales_tax_amount * percent_split_charge) sales_tax_amount";
	  }
	  query += " from cost_report_sales_tax_view"+where+" group by currency_id,sales_tax_desc";
	  return (query);
  }
	public Collection getSalesTax(BigDecimal personnelId, CostReportInputBean inputBean, String whereClauseForLink, Locale locale) throws BaseException {
		Collection resultColl = null;
		DbManager dbManager = null;
		String where = " where ";
		dbManager = new DbManager(getClient(),locale.toString());
		//format user selected data
		getReportFields(inputBean,locale,"html");
		buildWhereClause(personnelId,inputBean);
		where += inputBean.getWhereClause();
		if (whereClauseForLink != null) {
			where += " and "+whereClauseForLink.toString();
		}
		CostReportSalesTaxViewBeanFactory factory = new CostReportSalesTaxViewBeanFactory(dbManager);
		resultColl = factory.select(buildSalesTaxQuery(inputBean,where));

		return resultColl;
  }

  public ExcelHandler getExcelReport(BigDecimal personnelId, CostReportInputBean inputBean, Locale locale) throws NoDataException, BaseException, Exception {
   ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
   Collection data = this.getSearchResults(personnelId,inputBean,locale,"excel");

	String tmpInvoicedBetween = "";
	if (inputBean.getInvoiceDateBegin() != null && inputBean.getInvoiceDateEnd() != null) {
		tmpInvoicedBetween = DateHandler.formatDate(inputBean.getInvoiceDateBegin(),library.getString("java.dateformat"))+" "+library.getString("label.and")+" "+DateHandler.formatDate(inputBean.getInvoiceDateEnd(),library.getString("java.dateformat"));
	}else if (inputBean.getInvoiceDateBegin() != null) {
		tmpInvoicedBetween = "> "+DateHandler.formatDate(inputBean.getInvoiceDateBegin(),library.getString("java.dateformat"));
	}else if (inputBean.getInvoiceDateEnd() != null) {
		tmpInvoicedBetween = "< "+DateHandler.formatDate(inputBean.getInvoiceDateEnd(),library.getString("java.dateformat"));
	}
	String tmpDeliveredBetween = "";
	if (inputBean.getDateDeliveredBegin() != null && inputBean.getDateDeliveredEnd() != null) {
		tmpDeliveredBetween = DateHandler.formatDate(inputBean.getDateDeliveredBegin(),library.getString("java.dateformat"))+" "+library.getString("label.and")+" "+DateHandler.formatDate(inputBean.getDateDeliveredEnd(),library.getString("java.dateformat"));
	}else if (inputBean.getDateDeliveredBegin() != null) {
		tmpDeliveredBetween = "> "+DateHandler.formatDate(inputBean.getDateDeliveredBegin(),library.getString("java.dateformat"));
	}else if (inputBean.getDateDeliveredEnd() != null) {
		tmpDeliveredBetween = "< "+DateHandler.formatDate(inputBean.getDateDeliveredEnd(),library.getString("java.dateformat"));
	}

	Iterator iterator = data.iterator();
	ExcelHandler pw = new ExcelHandler(library);
	pw.addTable();

	pw.addRow();
	pw.addTdRegionBold(library.getString("label.company")+": "+inputBean.getCompanyName(),1,4);
	pw.addTdRegionBold(library.getString("label.requestor")+": "+inputBean.getRequestorName(),1,4);
   pw.addRow();
	pw.addTdRegionBold(library.getString("label.reportinggroup")+": "+inputBean.getReportingGroup(),1,4);
	pw.addTdRegionBold(library.getString("label.unitofmeasure")+": "+inputBean.getUomName(),1,4);
	pw.addRow();
	pw.addTdRegionBold(library.getString("label.facility")+": "+inputBean.getFacilityName(),1,4);
	pw.addTdRegionBold(library.getString("label.searchby")+": "+inputBean.getSearchByName()+" "+inputBean.getSearchType()+" "+inputBean.getSearchText(),1,4);
	pw.addRow();
	pw.addTdRegionBold(library.getString("label.workarea")+": "+inputBean.getApplicationName(),1,4);
	pw.addTdRegionBold(library.getString("label.accountingsystem")+": "+inputBean.getAccountSysName(),1,4);
	pw.addRow();
	pw.addTdRegionBold(library.getString("label.invoicenr")+": "+inputBean.getSearchByInvoiceNumber(),1,4);
	pw.addTdRegionBold(library.getString("label.invoiceperiod")+": "+inputBean.getSearchByInvoicePeriod(),1,4);
	pw.addRow();
	pw.addTdRegionBold(library.getString("label.invoicedbetween")+": "+tmpInvoicedBetween,1,4);
	pw.addTdRegionBold(library.getString("label.deliveredbetween")+": "+tmpDeliveredBetween,1,4);
	pw.addRow();
	pw.addTdRegionBold(library.getString("label.invoicetype")+": "+inputBean.getInvoiceTypeName(),1,4);
	pw.addTdRegionBold(library.getString("label.itemtype")+": "+inputBean.getItemTypeName(),1,4);

	//blank row
   pw.addRow();

	//result table
   pw.addRow();
   //write column headers
	int nonDisplayColumnIndex = -1;
	int nonDisplayCount = 0;
	if (inputBean.getReportFields() != null) {
		Iterator iter = inputBean.getReportFields().iterator();
		int count = 0;
		while(iter.hasNext()) {
			String tmp = (String)iter.next();
			//don't display these columns
			if ("voucherUrl".equals(tmp) || "whereClauseForLink".equals(tmp)) {
				//whereclauseforlink is not in data array for excel
				if ("voucherUrl".equals(tmp)) {
					nonDisplayColumnIndex = count;
				}
			}else {
				pw.addCellBold(tmp);
			}
			count++;
		}
	}

	//now write data
	Object[] columnNames = inputBean.getSqlFields().toArray();
	boolean firstRow = true;

	//paragraph style columns
	Vector setColumnParagraph = new Vector(5);
	setColumnParagraph.add("item_desc");
	setColumnParagraph.add("part_description");

	//short columns
	Vector setColumnShortFormat = new Vector();
	setColumnShortFormat.add("account_sys_id");
	setColumnShortFormat.add("account_number");
	setColumnShortFormat.add("account_number2");
	setColumnShortFormat.add("account_number3");
	setColumnShortFormat.add("account_number4");
	setColumnShortFormat.add("po_number");
	setColumnShortFormat.add("facility_display");
	setColumnShortFormat.add("application_desc");
	setColumnShortFormat.add("requestor_name");
	setColumnShortFormat.add("finance_approver_name");  
	setColumnShortFormat.add("cat_part_no");
	setColumnShortFormat.add("packaging");
	setColumnShortFormat.add("mfg_desc");
	setColumnShortFormat.add("mfg_lot");
	setColumnShortFormat.add("customer_invoice_no");
	setColumnShortFormat.add("customer_part_no");
	setColumnShortFormat.add("shipping_reference");
	setColumnShortFormat.add("currency_id");
	setColumnShortFormat.add("cat_part_attribute");
	setColumnShortFormat.add("quality_id");

	//floating number columns
	Vector setColumnFloatingNumberFormat = new Vector(13);
	setColumnFloatingNumberFormat.add("percent_split_charge");
	setColumnFloatingNumberFormat.add("invoice_unit_price");
	setColumnFloatingNumberFormat.add("unit_rebate");
	setColumnFloatingNumberFormat.add("total_freight_charge");
	setColumnFloatingNumberFormat.add("total_add_charge");
	setColumnFloatingNumberFormat.add("total_sales_tax");
	setColumnFloatingNumberFormat.add("service_fee");
	setColumnFloatingNumberFormat.add("pei_amount");
	setColumnFloatingNumberFormat.add("net_amount");
	setColumnFloatingNumberFormat.add("total_rebate");

	while (iterator.hasNext()) {
     pw.addRow();
	  //go thru loop to set each column format
	  if (firstRow) {
			for (int i = 0; i < (columnNames.length-inputBean.getAdditionalFields().size()); i++) {
				//skip nondisplay column
				if (i == nonDisplayColumnIndex) {
					continue;
				}

				String[] tmpName = columnNames[i].toString().split(" ");
				String column = tmpName[tmpName.length-1];
				//floating number
				if(setColumnFloatingNumberFormat.contains(column)) {
					pw.setColumnDigit(i,2);
				}
				//paragraph
				if (setColumnParagraph.contains(column)) {
					pw.setColumnParagraph(i);
					pw.setColumnWidthNow(i,50);
				}
				//short
				if (setColumnShortFormat.contains(column)) {
					pw.setColumnParagraph(i);
					pw.setColumnWidthNow(i,20);
				}
			}
	  }

	  //GO THRU DATA
	  CostReportViewBean excelBean = (CostReportViewBean)iterator.next();
	  for (int i = 0; i < (columnNames.length-inputBean.getAdditionalFields().size()); i++) {
			//skip nondisplay column
			if (i == nonDisplayColumnIndex) {
				continue;
			}

			String[] tmpName = columnNames[i].toString().split(" ");
			String column = tmpName[tmpName.length-1];
			try {
				Object columnObject = BeanHandler.getFieldObject(excelBean,column);
				if (columnObject == null) {
					pw.addCell("");
				}else if (columnObject instanceof String) {
					pw.addCell((String)columnObject);
				} else if (columnObject instanceof BigDecimal) {
					pw.addCell((BigDecimal) columnObject);
				} else if (columnObject instanceof Date) {
					if (firstRow) {
						String tmpExcelDateFormat = library.getString("java.exceldateformat");
					   if ("date_delivered".equalsIgnoreCase(column)) {
							if ("Day".equalsIgnoreCase(inputBean.getDateDeliveredGroupBy())) {
						   	tmpExcelDateFormat = library.getString("java.exceldateformat");
							}else if ("Month".equalsIgnoreCase(inputBean.getDateDeliveredGroupBy())) {
						     	tmpExcelDateFormat = library.getString("java.excelmonthyearformat");
							}else {
								tmpExcelDateFormat = library.getString("java.yearformat");
							}
						}
						pw.setColumnDateFormat(i,tmpExcelDateFormat);
						pw.setColumnWidthNow(i,11);
					}
					pw.addCell((Date)columnObject);
				}else {
					pw.addCell(columnObject.toString());
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		firstRow = false;
	}
	//total per currency
	if (inputBean.getTotalPerCurrencyDisplay()!= null) {
		Iterator iterC = inputBean.getTotalPerCurrencyDisplay().iterator();
		while(iterC.hasNext()) {
			pw.addRow();
			Iterator innerIter = ((Collection)iterC.next()).iterator();
			int totalColumnIndex = 0;
			while(innerIter.hasNext()) {
				String tmpVal = StringHandler.emptyIfNull((String)innerIter.next());
				if (tmpVal.length() > 0 && !"&nbsp;".equals(tmpVal)) {
					if (tmpVal.startsWith(library.getString("label.total"))) {
						pw.addCellBold(tmpVal);
					}else {
						pw.setColumnWidthNow(totalColumnIndex,13);
						pw.setColumnDigitBold(totalColumnIndex,2);
						pw.addCellBold(new BigDecimal(tmpVal));
					}
				}else {
					pw.addCell("");
				}
				totalColumnIndex++;
			}
		}
	}
	return pw;
 } //end of method

	public Collection getItemType(String category) throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		VvItemTypeBeanFactory factory = new VvItemTypeBeanFactory(dbManager);
		if ("itemType".equals(category)) {
			SearchCriteria criteria = new SearchCriteria();
			criteria.addCriterion("itemType", SearchCriterion.NOT_EQUAL,"DU");
			criteria.addCriterion("itemType", SearchCriterion.NOT_EQUAL,"OB");
			SortCriteria sortCriteria = new SortCriteria();
			sortCriteria.addCriterion("typeDesc");
			return (factory.select(criteria,sortCriteria));
		}else {
			return (factory.selectCommodity());
		}
	}

	public void setMissingDataFromTemplate(PersonnelBean personnelBean, CostReportInputBean bean) {
		String urlPageArg = "costreportsearch.do?action=openTemplate&templateId=";
		try {
			//get data from existing template because it's not carry to jsp
			if (!StringHandler.isBlankString(bean.getTemplateId())) {
				Collection templateColl = getTemplate(personnelBean.getPersonnelIdBigDecimal(),new BigDecimal(bean.getTemplateId()),null);
				Iterator iter = templateColl.iterator();
				AdHocTemplateBean templateBean = null;
				while (iter.hasNext()) {
					templateBean = (AdHocTemplateBean)iter.next();
					break;
				}
				if (templateBean != null) {
					bean.setCompanyId(templateBean.getCompanyId());
					bean.setOwner(templateBean.getOwner());
					bean.setStatus(templateBean.getStatus());
					bean.setUrlPageArg(templateBean.getUrlPageArg());
					bean.setUserGroupId(StringHandler.emptyIfNull(templateBean.getUserGroupId()));
				}else {
					bean.setTemplateId("''");
					bean.setCompanyId(personnelBean.getCompanyId());
					bean.setOwner("PERSONNEL_ID");
					bean.setStatus("A");
					bean.setUrlPageArg(urlPageArg);
					bean.setUserGroupId("");
				}
			}else {
				bean.setTemplateId("''");
				if(bean.getCompanyId() == null)
					bean.setCompanyId(personnelBean.getCompanyId());
				bean.setOwner("PERSONNEL_ID");
				bean.setStatus("A");
				bean.setUrlPageArg(urlPageArg);
				bean.setUserGroupId("");
			}
		}catch(Exception e) {
			e.printStackTrace();
			bean.setTemplateId("''");
			bean.setCompanyId(personnelBean.getCompanyId());
			bean.setOwner("PERSONNEL_ID");
			bean.setStatus("A");
			bean.setUrlPageArg(urlPageArg);
			bean.setUserGroupId("");
		}
	}  //end of method

	String createStringForDatabaseFx(PersonnelBean personnelBean, CostReportInputBean bean, Locale locale) {
		String separator = "|";
		String reportFieldString = "";
		try {
			reportFieldString = buildReportFieldForTemplate(bean,locale);
		}catch(Exception e) {
			e.printStackTrace();
		}
		//check for space holder
		if ("xxblankxx".equals(reportFieldString)) {
			reportFieldString = "";
		}

		String chargeNumber1 = "''";
		if (!StringHandler.isBlankString(bean.getAccountNumber())) {
			chargeNumber1 = "'"+SqlHandler.validQuery(bean.getAccountNumber())+"'";
		}
		String chargeNumber2 = "''";
		if (!StringHandler.isBlankString(bean.getAccountNumber2())) {
			chargeNumber2 = "'"+SqlHandler.validQuery(bean.getAccountNumber2())+"'";
		}
		String chargeNumber3 = "''";
		if (!StringHandler.isBlankString(bean.getAccountNumber3())) {
			chargeNumber3 = "'"+SqlHandler.validQuery(bean.getAccountNumber3())+"'";
		}
		String chargeNumber4 = "''";
		if (!StringHandler.isBlankString(bean.getAccountNumber4())) {
			chargeNumber4 = "'"+SqlHandler.validQuery(bean.getAccountNumber4())+"'";
		}
		String poNumber = "''";
		if (!StringHandler.isBlankString(bean.getPo())) {
			chargeNumber4 = "'"+SqlHandler.validQuery(bean.getPo())+"'";
		}

		String requestor = "''";
		String requestorName = "''";
		if (!StringHandler.isBlankString(bean.getRequestorName())) {
			requestor = bean.getRequestorId().toString();
			requestorName = "'"+SqlHandler.validQuery(bean.getRequestorName())+"'";
		}

		String searchText = "''";
		if (!StringHandler.isBlankString(bean.getSearchText())) {
			searchText = "'"+SqlHandler.validQuery(bean.getSearchText())+"'";
		}

		String lastModifiedBy = "''";
		String lastModifiedOn = "null";
		if (!"''".equalsIgnoreCase(bean.getTemplateId())) {
			lastModifiedBy = personnelBean.getPersonnelId()+"";
			lastModifiedOn = "sysdate";
		}

		String customerInvoice = "";
		String invoicePeriod = "''";
		if (bean.getSearchByInvoiceNumber() != null) {
			if (bean.getSearchByInvoiceNumber().length() > 0) {
				customerInvoice = bean.getSearchByInvoiceNumber();
			}
		}
		if (bean.getSearchByInvoicePeriod() != null) {
			if (bean.getSearchByInvoicePeriod().length() > 0) {
				invoicePeriod = bean.getSearchByInvoicePeriod();
			}
		}
		String invoiceType = "";
		if (bean.getSearchByInvoiceType() != null) {
			for(int i=0; i < bean.getSearchByInvoiceType().length; i++) {
				invoiceType += bean.getSearchByInvoiceType()[i]+";";
			}
			//removing the last ";"
			if (invoiceType.length() > 1) {
				invoiceType = invoiceType.substring(0,invoiceType.length()-1);
			}
		}
		String itemType = "";
		if (bean.getSearchByItemType() != null) {
			for(int i=0; i < bean.getSearchByItemType().length; i++) {
				itemType += bean.getSearchByItemType()[i]+";";
			}
			//removing the last ";"
			if (itemType.length() > 1) {
				itemType = itemType.substring(0,itemType.length()-1);
			}
		}

		String functionValue =
		bean.getTemplateId()+","+																					//template id
		"'Cost',"+																									//report type
		"null,"+          			 																			//base field id
		personnelBean.getPersonnelId()+","+    																	   //user id
		"'"+separator+"',"+                                 													//separator
		DateHandler.getOracleToDateFunction(bean.getDateDeliveredBegin(), "yyyyMMdd", "YYYYMMDD")+","+  //start date
		DateHandler.getOracleToDateFunction(bean.getDateDeliveredEnd(), "yyyyMMdd", "YYYYMMDD")+","+     //end date
		"null,"+																											//list id
		"null,"+																												//additional where
		"null,"+																												//additional from
		"null,"+																										//CAS number
		"'"+bean.getFacilityId()+"',"+																					//facility id
		"'"+bean.getApplication()+"',"+																				//work area
		"null,"+																										//part number
		"null,"+                     										                                //part number search by type
		"null,"+				                                                                  //dock
		"null,"+				                                                          //delivery point
		"null,"+                                                                      //category
		"null,"+																												//manufacturer
		"null,"+											                                                   //mfg search by type
		"'N',"+                        																				//column name is alias
		"null,"+                                                                          //part location
		"null,"+				 																					//part category
		"null,"+																												//accumulation point
		"null,"+																												//vendor id
		"null,"+       																										//waste item id
		"null,"+																												//waste management options
		"null,"+																												//exclude hub
		"null,"+																							//query type [list,singlechemical,all]
		"null,"+																												//chemical list
		"null,"+																												//chemical list format
		"'"+SqlHandler.validQuery(bean.getTemplateName().trim())+"',"+  									//template name
		"null,"+																												//output mode [active,batch]
		"null,"+																												//report name
		"null,"+																												//management option desc [waste]
		"null,"+                                                                                    //waste profile desc [waste]
		"'"+bean.getCompanyId()+"',"+		      	   															//company_id
		"'N',"+																												//debug
		"'"+bean.getReportingGroup()+"',"+																			//reporting entity id
		"'"+bean.getUserGroupId()+"',"+																				//NEW STARTS HERE user_group_id
		"'"+bean.getOwner()+"',"+ 																						//owner
		"'"+bean.getStatus()+"',"+                           													//status
		lastModifiedBy+","+																								//last modified by
		lastModifiedOn+","+																								//last modified on
		"'"+SqlHandler.validQuery(bean.getTemplateDescription().trim())+"',"+  							//template description
		"'"+bean.getUrlPageArg()+"',"+																				//url page arg
		"'"+bean.getAccountSysId()+"',"+																				//account sys id --starts cost report data
		"'"+bean.getChargeType()+"',"+		  																		//charge type
		chargeNumber1+","+																								//charge number 1
		chargeNumber2+","+																								//charge number 2
		"'"+bean.getSearchBy()+"',"+																					//search by
		searchText+","+																									//search text
		"null,"+						  																			   //invoice
		invoicePeriod+","+																								//invoice period
		"'"+reportFieldString+"',"+   																				//report fields
		DateHandler.getOracleToDateFunction(bean.getInvoiceDateBegin(), "yyyyMMdd", "YYYYMMDD")+","+	//invoice start date
		DateHandler.getOracleToDateFunction(bean.getInvoiceDateEnd(), "yyyyMMdd", "YYYYMMDD")+","+	//invoice end date
		requestor+","+																										//requestor
		requestorName+","+																								//requestor name
		"'"+invoiceType+"',"+																							//commodity
		"'"+bean.getDateDeliveredGroupBy()+"',"+										  							//date delivered group by
		"null,"+																												//source hub
		"'"+itemType+"',"+   																							//item type
		"'"+bean.getSearchType()+"',"+																				//search type
		"'"+bean.getReportType()+"',"+																				//output file type
		"'"+SqlHandler.validQuery(bean.getUom())+"',"+  															//uom
		chargeNumber3+","+																								//charge number 3
		chargeNumber4+","+  																								//charge number 4
		poNumber+","+ 		                                                                                                    // po number
		"null,"+                                                                                                                // area_id
		"null,"+			                                                                                                    // building_id
		"null,"+			                                                                                                    // room_id
		"null,"+			                                                                                                    // customer_part_no
		"null,"+			                                                                                                    // shipping_reference
		"'"+customerInvoice+"',"+                                                                                               // customer_invoice_no
        "null,"+	                                                                                                            //invoice number
        "null,"+                                                                                                                //search parameter
        "null,"+                                                	                                                            //search value
        "null,"+                                                	                                                            //msds match type
        "null,"+                                            	                                                                //mfg
    	"null,"+                                                    	                                                        //physical state
    	"null,"+                                    	                                                                        //ph
    	"null,"+                                                	                                                            //ph compare
    	"null,"+                                                	                                                            //flash point
    	"null,"+                                                        	                                                    //flash point compare
    	"null,"+	                                                                                                            //flash point unit
    	"null,"+	                                                                                                            //vapor pressure
    	"null,"+	                                                                                                            //vapor pressure compare
    	"null,"+	                                                                                                            //vapor pressure unit
    	"null,"+	                                                                                                            //voc
    	"null,"+	                                                                                                            //voc compare
    	"null,"+                                                                                                                //voc unit
    	"null,"+                                                                                                                //solid
        "null,"+                                                                                                                //solid compare
    	"null,"+                                                                                                                //nfpa health
    	"null,"+                                                                                                                //nfpa health compare
    	"null,"+                                                                                                                //nfpa flammability
    	"null,"+                                                                                                                //nfpa flammability compare
    	"null,"+                                                                                                                //nfpa reactivity
    	"null,"+                                                                                                                //nfpa reactivity compare
    	"null,"+                                                                                                                //specific hazard
    	"null,"+                                                                                                                //hmis health
    	"null,"+                                                                                                                //hmis health compare
    	"null,"+                                                                                                                //hmis flammability
    	"null,"+                                                                                                                //hmis flammability compare
    	"null,"+                                                                                                                //hmis reactivity
    	"null,"+                                                                                                                //hmis reactivity compare
    	"null,"+                                                                                                                //personal protection
        "null,"+                                                                                                                //constraint seperator
        "null,"+	                                                                                                            //full database
        "null,"+	                                                                                                            //approved
        "null,"+                                                                                                                //kit only
        "null,"+                                                                                                                //stocked
      	"null,"+                                                                                                                //voc less water exempt
    	"null,"+                                                                                                                //voc less water exempt compare
    	"null," +                                                                                                               //vol less water exempt unit
    	"null";                                                                                                                 //dept

		return functionValue;
	}

	public String saveTemplate(PersonnelBean personnelBean, CostReportInputBean inputBean, Locale locale) throws BaseException {
		setMissingDataFromTemplate(personnelBean,inputBean);

		DbManager dbManager = new DbManager(getClient(),locale.toString());
		String query = "select pkg_ad_hoc_report.fx_save_template("+createStringForDatabaseFx(personnelBean,inputBean,locale)+") from dual";
		
		if (log.isDebugEnabled()) {
			log.debug("query:"+query);
		}
	
		DataSet queryDataSet = dbManager.select(query);
		DataSetRow dsr = queryDataSet.getDataSetRow(1);
		String reportQuery = dsr.getString(queryDataSet.getColumnName(1));
		String[] result = reportQuery.split(":");
		String templateId = "";
		if ("OK".equalsIgnoreCase(result[0])) {
			reportQuery = "OK";
			templateId = result[1];
		}
		return templateId;
	}

	public String buildReportFieldForTemplate(CostReportInputBean inputBean, Locale locale) throws BaseException {
		String result = "";
		getReportFields(inputBean,locale,"html");
		if (inputBean.getReportFieldForTemplate() != null) {
			Object[] columnNames = inputBean.getReportFieldForTemplate().toArray();
			for (int i = 0; i < columnNames.length; i++) {
				result += (String)columnNames[i]+";";
			}
		}
		//remove the last ";"
		if (result.length() > 1) {
			result = result.substring(0,result.length()-1);
		}
		return result;
	}

	public Collection getTemplate(BigDecimal personnelId, BigDecimal templateId, String allowEdit) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),this.getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new AdHocTemplateBean());
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("reportType",SearchCriterion.EQUALS,reportType);
		criteria.addCriterion("personnelId",SearchCriterion.EQUALS,personnelId.toString());
		if (templateId != null) {
			criteria.addCriterion("templateId",SearchCriterion.EQUALS,templateId.toString());
		}
		if ("Y".equalsIgnoreCase(allowEdit)) {
			criteria.addCriterion("allowEdit",SearchCriterion.EQUALS,"Y");
		}
		criteria.addCriterion("pageId",SearchCriterion.IS,null);
        SortCriteria sortcriteria = new SortCriteria();
		sortcriteria.addCriterion("templateDescription");
		sortcriteria.addCriterion("templateId");
        return factory.select(criteria,sortcriteria,"ad_hoc_template");
	}

	public CostReportInputBean openTemplate(BigDecimal personnelId, CostReportInputBean inputBean) throws BaseException {
		CostReportInputBean resultBean = new CostReportInputBean();
		/*
		DbManager dbManager = new DbManager(getClient());
		CostReportTemplateBeanFactory factory = new CostReportTemplateBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("personnelId", SearchCriterion.EQUALS, (personnelId.toString()));
		criteria.addCriterion("reportName", SearchCriterion.EQUALS, (inputBean.getTemplateName()));
		translateData(resultBean,factory.select(criteria,null));
		*/
		translateData(resultBean,getTemplate(personnelId,new BigDecimal(inputBean.getTemplateId()),null));
		return resultBean;
	}

	public void translateData(CostReportInputBean resultBean,Collection templateData) {
		Iterator iter = templateData.iterator();
		while(iter.hasNext()) {
			AdHocTemplateBean bean = (AdHocTemplateBean)iter.next();
			resultBean.setTemplateId(bean.getTemplateId().toString());
			resultBean.setTemplateName(bean.getTemplateName());
			resultBean.setGlobalizationLabelLetter(bean.getGlobalizationLabelLetter());
			if (bean.getRequestor() != null) {
				resultBean.setRequestorId(bean.getRequestor().toString());
			}
			resultBean.setRequestorName(bean.getRequestorName());
			resultBean.setCompanyId(bean.getCompanyId());
			resultBean.setReportingGroup(bean.getReportingEntityId());
			resultBean.setFacilityId(bean.getFacilityId());
			resultBean.setApplication(bean.getApplication());
			resultBean.setAccountSysId(bean.getAccountSysId());
			resultBean.setChargeType(bean.getChargeType());
			resultBean.setAccountNumber(bean.getChargeNumber1());
			resultBean.setAccountNumber2(bean.getChargeNumber2());
			resultBean.setAccountNumber3(bean.getChargeNumber3());
			resultBean.setAccountNumber4(bean.getChargeNumber4());
			resultBean.setUom(bean.getUom());
			resultBean.setSearchBy(bean.getSearchBy());
			resultBean.setSearchType(bean.getSearchType());     
			resultBean.setSearchText(bean.getSearchText());
			
			if (bean.getCustomerInvoiceNo() != null) {
				resultBean.setSearchByInvoiceNumber(bean.getCustomerInvoiceNo());
			}
			
			if (bean.getInvoicePeriod() != null) {
				resultBean.setSearchByInvoicePeriod(bean.getInvoicePeriod().toString());
			}
			resultBean.setDateDeliveredBegin(bean.getStartDate());
			resultBean.setDateDeliveredEnd(bean.getStopDate());
			decodeReportField(resultBean,bean.getReportField());
			resultBean.setInvoiceDateBegin(bean.getInvoiceStartDate());
			resultBean.setInvoiceDateEnd(bean.getInvoiceEndDate());
			resultBean.setDateDeliveredGroupBy(bean.getDateDeliveredGroupBy());
			Collection tmpColl = new Vector(17);
			stringToCollection(tmpColl,bean.getCommodity());
			resultBean.setInvoiceTypeCollection(tmpColl);
			tmpColl = null;
			tmpColl = new Vector(17);
			stringToCollection(tmpColl,bean.getItemType());
			resultBean.setItemTypeCollection(tmpColl);
			resultBean.setReportType(bean.getOutputFileType());
		}
	}

	public void stringToCollection(Collection tmpColl, String input) {
		if (input != null) {
			if (input.length() > 0) {
				String[] tmp = input.split(";");
				for (int i = 0; i < tmp.length; i++) {
					tmpColl.add(tmp[i]);
				}
			}
		}
	}

	public void decodeReportField(CostReportInputBean resultBean, String reportField) {
		if (!StringHandler.isBlankString(reportField)) {
			if (reportField.length() > 0) {
				String[] tmpArray = reportField.split(";");
				for(int i = 0; i < tmpArray.length; i++) {
					String setter = "set" + CodeCreator.getFieldHandlerName(tmpArray[i].toLowerCase());
					Class beanClass = resultBean.getClass();
					try {
						Object[] setterParams = new Object[1];
						setterParams[0] = "true";
						Method setterMethod = beanClass.getMethod(setter, String.class);
						setterMethod.invoke(resultBean, setterParams);
					}catch (Exception e) {

					}
				}
			}
		}
	}

  public Collection getAdditionalCharge(BigDecimal personnelId, CostReportInputBean inputBean, String whereClauseForLink, Locale locale) throws BaseException {
	Collection resultColl = null;
	DbManager dbManager = null;
	String where = " where ";
	dbManager = new DbManager(getClient(),locale.toString());
	//format user selected data
	getReportFields(inputBean,locale,"html");
	buildWhereClause(personnelId,inputBean);
	where += inputBean.getWhereClause();
	if (!StringHandler.isBlankString(whereClauseForLink)) {
		where += " and "+whereClauseForLink.toString();
	}
	CostReportAddChargeViewBeanFactory factory = new CostReportAddChargeViewBeanFactory(dbManager);
	resultColl = factory.select(buildAddChargeQuery(inputBean,where));

	return resultColl;
  }

  private String buildAddChargeQuery(CostReportInputBean inputBean, String where) {
	  String query = "select currency_id,additional_charge_desc";
	  if (inputBean.getSqlFields().contains("percent_split_charge")) {
		 query += ",fx_fix_rounding_error(sum(additional_charge_amount)) additional_charge_amount";
	  }else {
		 query += ",fx_fix_rounding_error(sum(additional_charge_amount * percent_split_charge)) additional_charge_amount";
	  }
	  query += " from cost_report_add_charge_view"+where+" group by currency_id,additional_charge_desc";
	  return (query);
  }

  public Collection getSearchHtmlResults(BigDecimal personnelId, CostReportInputBean inputBean, Locale locale) throws BaseException {
	Collection resultColl = getSearchResults(personnelId,inputBean,locale,"html");

	//formatting data_delivered here
	Iterator iter = inputBean.getSqlFields().iterator();
	int dateDeliveredIndex = -1;
	int count = 0;
	while (iter.hasNext()) {
		if (iter.next().toString().endsWith("date_delivered")) {
			dateDeliveredIndex = count;
			break;
		}
		count++;
	}
	if (dateDeliveredIndex > -1) {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		String tmpDateFormat = "";
		if ("Day".equalsIgnoreCase(inputBean.getDateDeliveredGroupBy())) {
			tmpDateFormat = library.getString("java.dateformat");
		}else if ("Month".equalsIgnoreCase(inputBean.getDateDeliveredGroupBy())) {
			tmpDateFormat = library.getString("java.monthyearformat");
		}else {
			tmpDateFormat = library.getString("java.yearformat");
		}
		Iterator iter2 = resultColl.iterator();
		//loop for each row
		while (iter2.hasNext()) {
			//vector of each column
			Vector tmpVal = (Vector)iter2.next();
			String tmp = DateHandler.formatDate((Date)tmpVal.elementAt(dateDeliveredIndex),tmpDateFormat);
			tmpVal.removeElementAt(dateDeliveredIndex);
			tmpVal.insertElementAt(tmp,dateDeliveredIndex);
		}
	}
	return resultColl;
  }

  public Collection getSearchResults(BigDecimal personnelId, CostReportInputBean inputBean, Locale locale, String reportOption) throws BaseException {
	Collection resultColl = null;
	DbManager dbManager = null;
	dbManager = new DbManager(getClient(),locale.toString());
	CostReportViewBeanFactory factory = new CostReportViewBeanFactory(dbManager);
	//format user selected data
	getReportFields(inputBean,locale,reportOption);
	if (inputBean.getReportFields() == null || inputBean.getReportFields().size() == 0) {
		BaseException be = new BaseException("");
		be.setMessageKey("error.selectreportfield");
		throw be;
	}
	if ("excel".equalsIgnoreCase(reportOption)) {
		resultColl = factory.selectExcel(buildQuery(personnelId,inputBean),inputBean);
	}else {
		resultColl = factory.select(buildQuery(personnelId,inputBean),inputBean);
	}
	//building total per currency to display
	buildTotalPerCurrencyDisplay(inputBean,locale);

	return resultColl;
  }

	private void buildTotalPerCurrencyDisplay(CostReportInputBean inputBean, Locale locale) {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		Collection displayColl = new Vector(inputBean.getTotalPerCurrency().size());
		if (inputBean.getTotalPerCurrency().size() == 0) {
			inputBean.setTotalPerCurrencyDisplay(new Vector(0));
		}else {
			Collection totalColl = inputBean.getTotalPerCurrency();
			Iterator iter = totalColl.iterator();
			int nonDisplayColumn = getNonDisplayColumn(inputBean);
			while(iter.hasNext()) {
				CostReportTotalPerCurrencyBean bean = (CostReportTotalPerCurrencyBean)iter.next();
				Collection columnData = new Vector(inputBean.getReportFields().size());
				//add blank to non-total columns
				//Note: reportFields.size -1 for currency
				for (int i = 0; i < ((inputBean.getReportFields().size()-1)-(getNumberOfTotalColumn(inputBean))-(nonDisplayColumn)); i++) {
					columnData.add("&nbsp;");
				}
				//NOTE: the following columns have to be in order
				if (inputBean.getSqlFields().contains("total_freight_charge")) {
					if (bean.getTotalFreightCharge()!= null) {
						columnData.add(bean.getTotalFreightCharge().toString());
					}else {
						columnData.add("&nbsp;");
					}
				}
				if (inputBean.getSqlFields().contains("total_add_charge")) {
					if (bean.getTotalAddCharge()!= null) {
						columnData.add(bean.getTotalAddCharge().toString());
					}else {
						columnData.add("&nbsp;");
					}
				}
				if (inputBean.getSqlFields().contains("total_sales_tax")) {
					if(bean.getTotalSalesTax() != null) {
						columnData.add(bean.getTotalSalesTax().toString());
					}else {
						columnData.add("&nbsp;");
					}
				}
				if (inputBean.getSqlFields().contains("service_fee")) {
					if(bean.getTotalServiceFee() != null) {
						columnData.add(bean.getTotalServiceFee().toString());
					}else {
						columnData.add("&nbsp;");
					}
				}
				if (inputBean.getSqlFields().contains("pei_amount")) {
					if(bean.getTotalPeiAmount() != null) {
						columnData.add(bean.getTotalPeiAmount().toString());
					}else {
						columnData.add("&nbsp;");
					}
				}
				if (inputBean.getSqlFields().contains("net_amount")) {
					if(bean.getTotalNetAmount() != null) {
						columnData.add(bean.getTotalNetAmount().toString());
					}else {
						columnData.add("&nbsp;");
					}
				}
				if (inputBean.getSqlFields().contains("total_rebate")) {
					if(bean.getTotalMaterialSaving() != null) {
						columnData.add(bean.getTotalMaterialSaving().toString());
					}else {
						columnData.add("&nbsp;");
					}
				}
				columnData.add(library.getString("label.total")+" "+bean.getCurrencyId());
				displayColl.add(columnData);
			}
		}
		inputBean.setTotalPerCurrencyDisplay(displayColl);
	}

	private int getNonDisplayColumn(CostReportInputBean inputBean) {
		int count = 0;
		if (inputBean.getSqlFields().contains("fx_voucher_url(receipt_id) voucher_url")) {
			count++;
		}
		if (inputBean.getReportFields().contains("whereClauseForLink")) {
			count++;
		}
		return count;
	}

	private int getNumberOfTotalColumn(CostReportInputBean inputBean) {
		int count = 0;
		if (inputBean.getSqlFields().contains("total_freight_charge")) {
			count++;
		}
		if (inputBean.getSqlFields().contains("total_add_charge")) {
			count++;
		}
		if (inputBean.getSqlFields().contains("total_sales_tax")) {
			count++;
		}
		if (inputBean.getSqlFields().contains("service_fee")) {
			count++;
		}
		if (inputBean.getSqlFields().contains("pei_amount")) {
			count++;
		}
		if (inputBean.getSqlFields().contains("net_amount")) {
			count++;
		}
		if (inputBean.getSqlFields().contains("total_rebate")) {
			count++;
		}
		return count;
	}

	private String buildQuery(BigDecimal personnelId, CostReportInputBean inputBean) {
		String query = "";
		String groupBy = "";
		String where = "";

		boolean needGroupBy = false;
		//determine whether part_desc, item_desc and/or manufacturer is one of the field user is interested in
		getAdditionalFields(inputBean);
		//build select statement
		Collection sqlFields = inputBean.getSqlFields();
		Collection groupByFields = inputBean.getGroupBy();
		Iterator iter = sqlFields.iterator();
		Iterator iterGroupBy = groupByFields.iterator();
		boolean qpeExist = false;
		while (iter.hasNext()) {
			String tmp = (String)iter.next();
			String tmpGroupBy = (String)iterGroupBy.next();
			if (tmp.equalsIgnoreCase("quantity")) {
				//if split charge
				if (sqlFields.contains("percent_split_charge")) {
                  if ("Invoice".equalsIgnoreCase(inputBean.getUom())) {
                    tmp = "fx_fix_rounding_error(sum(unit_of_sale_quantity_per_each * "+tmp+")) "+tmp;
                  }else {
                      tmp = "fx_fix_rounding_error(sum("+tmp+")) "+tmp;
                  }
				}else {
                    if ("Invoice".equalsIgnoreCase(inputBean.getUom())) {
                        tmp = "fx_fix_rounding_error(sum(unit_of_sale_quantity_per_each * "+tmp+")) "+tmp;
                    }else {
                        tmp = "fx_fix_rounding_error(sum("+tmp+")) "+tmp;    
                    }
				}
				needGroupBy = true;
			}else if (tmp.equalsIgnoreCase("invoice_unit_price")) {
                if ("Invoice".equalsIgnoreCase(inputBean.getUom())) {
                    tmp = "fx_fix_rounding_error(("+tmp+" / unit_of_sale_quantity_per_each)) "+tmp;
                    groupBy += tmpGroupBy + ",";
                    if (!qpeExist) {
                        groupBy += "unit_of_sale_quantity_per_each,";
                        qpeExist = true;
                    }
                }else {
                    tmp = "fx_fix_rounding_error(("+tmp+")) "+tmp;
                    groupBy += tmpGroupBy + ",";
                }
			}else if (tmp.equalsIgnoreCase("unit_rebate")) {
                if ("Invoice".equalsIgnoreCase(inputBean.getUom())) {
                    tmp = "fx_fix_rounding_error(("+tmp+" / unit_of_sale_quantity_per_each)) "+tmp;
                    groupBy += tmpGroupBy + ",";
                    if (!qpeExist) {
                        groupBy += "unit_of_sale_quantity_per_each,";
                        qpeExist = true;
                    }
                }else {
                    tmp = "fx_fix_rounding_error(("+tmp+")) "+tmp;
                    groupBy += tmpGroupBy + ",";
                }
			}else if (tmp.equalsIgnoreCase("total_freight_charge") ||
				 		 tmp.equalsIgnoreCase("total_add_charge") ||
						  tmp.equalsIgnoreCase("total_sales_tax") ||
						  tmp.equalsIgnoreCase("service_fee") ||
						  tmp.equalsIgnoreCase("pei_amount")) {
				if (sqlFields.contains("percent_split_charge")) {
				  tmp = "fx_fix_rounding_error(sum("+tmp+")) "+tmp;
				}else {
				  tmp = "fx_fix_rounding_error(sum("+tmp+")) "+tmp;
				}
				needGroupBy = true;
			}else if (tmp.equalsIgnoreCase("net_amount")) {
				tmp = "fx_fix_rounding_error(sum("+tmp+")) "+tmp;
				needGroupBy = true;
			}else if (tmp.equalsIgnoreCase("total_rebate")) {
				tmp = "fx_fix_rounding_error(sum("+tmp+")) "+tmp;
				needGroupBy = true;
			}else if (tmp.equalsIgnoreCase("percent_split_charge")) {
				tmp = "fx_fix_rounding_error(percent_split_charge) "+tmp;
				groupBy += "percent_split_charge,";
			}else if (tmp.equalsIgnoreCase("invoice_unit_price")) {
				tmp = "invoice_unit_price ";
			}else {
				//don't add item_desc to group by
				if (!tmpGroupBy.equalsIgnoreCase("fx_display_item_desc(item_id)")) {
				  groupBy += tmpGroupBy + ",";
				}
			}
			query += tmp+",";
	   }
	  	//removing the last commas ','
	  	if (query.length() > 2) {
			query = query.substring(0,query.length()-1);
	 	}

		//build where clause
		buildWhereClause(personnelId,inputBean);
		where = inputBean.getWhereClause();

		//build sql
		query = "select distinct "+query+" from cost_report_view ";
		if (where.length() > 0) {
			query += " where "+where;
		}

		//if group by required - remove the last commas ','
		if (needGroupBy) {
			if (groupBy.length() > 2) {
				groupBy = groupBy.substring(0,groupBy.length()-1);
				query += " group by "+groupBy;
			}
		}

		String orderBy = inputBean.getOrderBy();
		if (!StringHandler.isBlankString(orderBy)) {
			query += " order by "+orderBy;
		}
		return query;
	} //end of method

	private void buildWhereClause(BigDecimal personnelId, CostReportInputBean inputBean) {
		String where = "";
		//temporary keep this for additional charge drill down
		Collection sqlCols = new Vector(51);
		Collection sqlVals = new Vector(51);
		String tmp = inputBean.getRequestorId();
		String tmpVal = "";
		if (tmp != null) {
		  if (tmp.length() > 0 && !"0".equalsIgnoreCase(tmp)) {
			tmpVal = "requestor = "+tmp;
			where += tmpVal+ " and ";
			sqlCols.add("requestor");
			sqlVals.add(tmpVal);
		  }
		}
		String companyId = "";
		tmp = inputBean.getCompanyId();
		if (tmp != null && "TCM_OPS".equals(this.getClient())) {
		  if ("My Companies".equalsIgnoreCase(tmp)) {
			tmpVal= "company_id in (select company_id from user_company where personnel_id = "+personnelId.toString()+")";
		  }else {
			companyId = tmp;
			tmpVal = "company_id = '"+tmp+"'";
		  }
		  where += tmpVal+" and ";
		  sqlCols.add("company_id");
		  sqlVals.add(tmpVal);
		}
		String reportingGroup = "";
		tmp = inputBean.getReportingGroup();
		if (tmp != null) {
			if ("My Reporting Groups".equalsIgnoreCase(tmp)) {
				if (companyId.length() > 0) {
					tmpVal = "facility_id in (select facility_id from cost_report_group where company_id = '"+companyId+"')";
				}else {
			 		tmpVal = "facility_id in (select facility_id from cost_report_group crp,user_company uc where"+
				            " crp.company_id = uc.company_id and uc.personnel_id = "+personnelId.toString()+")";
				}
			}else {
			 reportingGroup = tmp;
			 tmpVal = "facility_id in (select facility_id from cost_report_group where cost_report_group = '"+tmp+"')";
			}
			where += tmpVal + " and ";
			sqlCols.add("cost_report_group");
			sqlVals.add(tmpVal);
		}
		tmp = inputBean.getFacilityId();
		if (tmp != null) {
			if ("My Facilities".equalsIgnoreCase(tmp)) {
			 tmpVal = "facility_id in (select ugm.facility_id from user_group_member ugm, cost_report_group crp"+
								  " where ugm.user_group_id = 'CostReport' and ugm.personnel_id = "+personnelId.toString()+
								  " and ugm.company_id = crp.company_id and ugm.facility_id = crp.facility_id";
			 if (reportingGroup.length() > 0) {
				 tmpVal += " and crp.cost_report_group = '"+reportingGroup+"'";
			 }
			 tmpVal += ")";
			}else {
				tmpVal = "facility_id = '"+tmp+"'";
			}
			where += tmpVal + " and ";
			sqlCols.add("facility_id");
			sqlVals.add(tmpVal);
		}
		tmp = inputBean.getApplication();
		if (tmp != null) {
			if (!"My Work Areas".equalsIgnoreCase(tmp)) {
			 tmpVal = "application = '"+tmp+"'";
			 where += tmpVal+" and ";
			 sqlCols.add("application");
			 sqlVals.add(tmpVal);
			}
		}
		tmp = inputBean.getAccountSysId();
		if (tmp != null) {
			if (!"My Accounting Systems".equalsIgnoreCase(tmp)) {
				tmpVal = "account_sys_id = '"+tmp+"'";
				where += tmpVal +" and ";
		 		sqlCols.add("account_sys_id");
		 		sqlVals.add(tmpVal);
				tmp = inputBean.getChargeType();
				if (!StringHandler.isBlankString(tmp)) {
					tmpVal = "lower(charge_type) = lower('"+tmp+"')";
					where += tmpVal +" and ";
					sqlCols.add("charge_type");
					sqlVals.add(tmpVal);
				}
				tmp = inputBean.getPo();
				if (!StringHandler.isBlankString(tmp)) {
					tmpVal = "lower(po_number) like lower('%"+tmp+"%')";
					where += tmpVal +" and ";
					sqlCols.add("po_number");
					sqlVals.add(tmpVal);
				}
				tmp = inputBean.getAccountNumber();
				if (!StringHandler.isBlankString(tmp)) {
					tmpVal = "lower(account_number) like lower('%"+tmp+"%')";
					where += tmpVal +" and ";
					sqlCols.add("account_number");
					sqlVals.add(tmpVal);
				}
				tmp = inputBean.getAccountNumber2();
				if (!StringHandler.isBlankString(tmp)) {
					tmpVal = "lower(account_number2) like lower('%"+tmp+"%')";
					where += tmpVal +" and ";
					sqlCols.add("account_number2");
					sqlVals.add(tmpVal);
				}
				tmp = inputBean.getAccountNumber3();
				if (!StringHandler.isBlankString(tmp)) {
					tmpVal = "lower(account_number3) like lower('%"+tmp+"%')";
					where += tmpVal +" and ";
					sqlCols.add("account_number3");
					sqlVals.add(tmpVal);
				}
				tmp = inputBean.getAccountNumber4();
				if (!StringHandler.isBlankString(tmp)) {
					tmpVal = "lower(account_number4) like lower('%"+tmp+"%')";
					where += tmpVal +" and ";
					sqlCols.add("account_number4");
					sqlVals.add(tmpVal);
				}
			}
		}

		tmp = inputBean.getSearchText();
		if (!StringHandler.isBlankString(tmp)) {
			tmpVal = "";
			String searchBy = inputBean.getSearchBy();
			String searchType = inputBean.getSearchType();
			if ("is".equalsIgnoreCase(searchType)) {
				tmpVal = "lower("+searchBy+") = lower('"+tmp.trim()+"')";
			}else if ("contains".equalsIgnoreCase(searchType)) {
				tmpVal = "lower("+searchBy+") like lower('%"+tmp.trim()+"%')";
			}else if ("startWith".equalsIgnoreCase(searchType)) {
				tmpVal = "lower("+searchBy+") like lower('"+tmp.trim()+"%')";
			}else if ("endWith".equalsIgnoreCase(searchType)) {
				tmpVal = "lower("+searchBy+") like lower('%"+tmp.trim()+"')";
			}
			if (tmpVal.length() > 0) {
				where += tmpVal+" and ";
				sqlCols.add(searchBy);
				sqlVals.add(tmpVal);
			}
		}

		tmp = inputBean.getSearchByInvoiceNumber();
		if (!StringHandler.isBlankString(tmp)) {
			tmpVal = " customer_invoice_no = '"+tmp.trim()+"'";
		 	where += tmpVal+" and ";
		 	sqlCols.add("invoice");
		 	sqlVals.add(tmpVal);
		}
		tmp = inputBean.getSearchByInvoicePeriod();
		if (!StringHandler.isBlankString(tmp)) {
		 	tmpVal = "invoice_period like '%"+tmp.trim()+"%'";
			where += tmpVal+" and ";
		 	sqlCols.add("invoice_period");
		 	sqlVals.add(tmpVal);
		}
		//invoice date
		Date tmpBegin = inputBean.getInvoiceDateBegin();
		Date tmpEnd = inputBean.getInvoiceDateEnd();
		if (tmpBegin != null && tmpEnd != null) {
			tmpVal = "(invoice_date between to_date('"+DateHandler.formatDate(tmpBegin,"MM/dd/yyyy")+"','mm/dd/yyyy') and to_date('"+DateHandler.formatDate(tmpEnd,"MM/dd/yyyy")+"','mm/dd/yyyy'))";
			where += tmpVal+" and ";
			sqlCols.add("invoice_date");
			sqlVals.add(tmpVal);
		}else if (tmpBegin != null) {
			tmpVal = "invoice_date > to_date('"+DateHandler.formatDate(tmpBegin,"MM/dd/yyyy")+"','mm/dd/yyyy')";
			where += tmpVal+" and ";
			sqlCols.add("invoice_date");
			sqlVals.add(tmpVal);
		}else if (tmpEnd != null) {
			tmpVal = "invoice_date < to_date('"+DateHandler.formatDate(tmpEnd,"MM/dd/yyyy")+"','mm/dd/yyyy')";
			where += tmpVal+" and ";
			sqlCols.add("invoice_date");
			sqlVals.add(tmpVal);
		}
		//delivered date
		tmpBegin = inputBean.getDateDeliveredBegin();
		tmpEnd = inputBean.getDateDeliveredEnd();
		if (tmpBegin != null && tmpEnd != null) {
		 	tmpVal = "(date_delivered between to_date('"+DateHandler.formatDate(tmpBegin,"MM/dd/yyyy")+"','mm/dd/yyyy') and to_date('"+DateHandler.formatDate(tmpEnd,"MM/dd/yyyy")+"','mm/dd/yyyy'))";
		 	where += tmpVal+" and ";
		 	sqlCols.add("date_delivered");
		 	sqlVals.add(tmpVal);
		}else if (tmpBegin != null) {
			tmpVal = "date_delivered > to_date('"+DateHandler.formatDate(tmpBegin,"MM/dd/yyyy")+"','mm/dd/yyyy')";
		 	where += tmpVal+" and ";
		 	sqlCols.add("date_delivered");
		 	sqlVals.add(tmpVal);
		}else if (tmpEnd != null) {
			tmpVal = "date_delivered < to_date('"+DateHandler.formatDate(tmpEnd,"MM/dd/yyyy")+"','mm/dd/yyyy')";
			where += tmpVal+" and ";
			sqlCols.add("date_delivered");
		 	sqlVals.add(tmpVal);
		}

		//commodity
		String[] tmpArray = inputBean.getSearchByInvoiceType();
		int iii = 0;
		if (tmpArray != null) {
			tmp = "";
			for (iii = 0; iii < tmpArray.length; iii++) {
		 		if (!"All Types".equalsIgnoreCase(tmpArray[iii])) {
					tmp += "'"+tmpArray[iii]+"',";
		 		}
			}
			//removing the last commas ','
			if (tmp.length() > 0 ) {
		 		tmp = tmp.substring(0,tmp.length()-1);
				tmpVal = "commodity in ("+tmp+")";
				where += tmpVal+" and ";
		 		sqlCols.add("commodity");
		 		sqlVals.add(tmpVal);
			}

		}
		//item type
		tmpArray = null;
		tmpArray = inputBean.getSearchByItemType();
		if (tmpArray != null) {
			tmp = "";
			for (iii = 0; iii < tmpArray.length; iii++) {
			 if (!"All Types".equalsIgnoreCase(tmpArray[iii])) {
				if ("MA".equalsIgnoreCase(tmpArray[iii])) {
				  tmp += "'"+tmpArray[iii]+"','DU','OB',";
				}else {
				  tmp += "'"+tmpArray[iii]+"',";
				}
			 }
			}
			//removing the last commas ','
			if (tmp.length() > 0 ) {
			 	tmp = tmp.substring(0,tmp.length()-1);
				tmpVal = "item_type in ("+tmp+")";
			 	where += tmpVal+" and ";
			 	sqlCols.add("item_type");
			 	sqlVals.add(tmpVal);
			}

		}
		if (where.length() > 0) {
			//remobing the last "and"
			where = where.substring(0,where.length()-4);
		}
		inputBean.setWhereClause(where);
		//inputBean.setAddSqlCols(sqlCols);
		//inputBean.setAddSqlVals(sqlVals);
	} //end of method

  private void getAdditionalFields(CostReportInputBean inputBean) {
    Collection additionalFields = new Vector(11);
	 Collection sqlFields = inputBean.getSqlFields();
	 Collection groupByFields = inputBean.getGroupBy();
	 Collection reportFields = inputBean.getReportFields();
	 //part description
    if (sqlFields.contains("part_description") && !sqlFields.contains("cat_part_no")) {
      additionalFields.add("cat_part_no");
      sqlFields.add("cat_part_no");
		groupByFields.add("cat_part_no");
		//reportFields.add("cat_part_no");
    }
    //item description
    if (sqlFields.contains("fx_display_item_desc(item_id) item_desc") && !sqlFields.contains("item_id")) {
      additionalFields.add("item_id");
      sqlFields.add("item_id");
		groupByFields.add("item_id");
		//reportFields.add("item_id");
    }
    //manufacturer
    if (sqlFields.contains("mfg_desc")) {
      additionalFields.add("mfg_id");
      sqlFields.add("mfg_id");
		groupByFields.add("mfg_id"); 
		//reportFields.add("mfg_id");
    }
    //work area desc
    if (sqlFields.contains("application_desc")) {
      additionalFields.add("application");
      sqlFields.add("application");
		groupByFields.add("application");
		//reportFields.add("application");
    }
	 inputBean.setAdditionalFields(additionalFields);
  } //end of method

  /* Very Important -
   * You have to make sure that the columns are position here matches with
   * what is on the webpage
  */
  private void getReportFields(CostReportInputBean inputBean, Locale locale, String reportOption) {
	 ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
	 int numFields = 55;
	 Collection reportFields = new Vector(numFields);
	 Collection sqlFields = new Vector(numFields);
	 Collection groupByFields = new Vector(numFields);
	 Collection reportFieldForTemplate = new Vector(numFields);
	 int[] reportFieldType = new int[numFields];
	 int fieldIndex = 0;
	 String orderBy = "";
	 if ("Yes".equalsIgnoreCase(inputBean.getInvoice())) {
      reportFields.add(library.getString("label.invoice"));
      sqlFields.add("invoice");
		groupByFields.add("invoice");
		reportFieldForTemplate.add("invoice");
		reportFieldType[fieldIndex++] = CostReportInputBean.STRING_TYPE;
	 }
	if ("Yes".equalsIgnoreCase(inputBean.getCustomerInvoiceNo())) {
	      reportFields.add(library.getString("label.invoice"));
	      sqlFields.add("customer_invoice_no");
			groupByFields.add("customer_invoice_no");
			reportFieldForTemplate.add("customer_invoice_no");
			reportFieldType[fieldIndex++] = CostReportInputBean.STRING_TYPE;
	}
    if ("Yes".equalsIgnoreCase(inputBean.getInvoiceType())) {
      reportFields.add(library.getString("label.invoicetype"));
      sqlFields.add("commodity");
		groupByFields.add("commodity");
		reportFieldForTemplate.add("invoice_type");
		reportFieldType[fieldIndex++] = CostReportInputBean.STRING_TYPE;
	 }
    if ("Yes".equalsIgnoreCase(inputBean.getInvoiceDate())) {
      reportFields.add(library.getString("label.invoicedate"));
		sqlFields.add("invoice_date");
		groupByFields.add("invoice_date");
		reportFieldForTemplate.add("invoice_date");
		reportFieldType[fieldIndex++] = CostReportInputBean.DATE_TYPE;
	 }
    if ("Yes".equalsIgnoreCase(inputBean.getInvoicePeriod())) {
      //splitting this field into three
		reportFields.add(library.getString("label.invoiceperiod"));
		sqlFields.add("invoice_period");
		groupByFields.add("invoice_period");
		reportFieldType[fieldIndex++] = CostReportInputBean.NUMBER_TYPE;
		reportFields.add(library.getString("label.invoiceperiodstartdate"));
		sqlFields.add("start_date");
		groupByFields.add("start_date");
		reportFieldType[fieldIndex++] = CostReportInputBean.DATE_TYPE;
		reportFields.add(library.getString("label.invoiceperiodenddate"));
		sqlFields.add("end_date");
		groupByFields.add("end_date");
		reportFieldType[fieldIndex++] = CostReportInputBean.DATE_TYPE;
		//but keep it as one for template data
		reportFieldForTemplate.add("invoice_period");
	 }
    if ("Yes".equalsIgnoreCase(inputBean.getInvoiceLine())) {
      reportFields.add(library.getString("label.invoiceline"));
      sqlFields.add("invoice_line");
		groupByFields.add("invoice_line");
		reportFieldForTemplate.add("invoice_line");
		reportFieldType[fieldIndex++] = CostReportInputBean.NUMBER_TYPE;
	 }
	 if ("Yes".equalsIgnoreCase(inputBean.getAccountingSystem())) {
      reportFields.add(library.getString("label.accountingsystem"));
      sqlFields.add("account_sys_id");
		groupByFields.add("account_sys_id");
		reportFieldForTemplate.add("accounting_system");
		reportFieldType[fieldIndex++] = CostReportInputBean.STRING_TYPE;
	 }
    if ("Yes".equalsIgnoreCase(inputBean.getChargeNumber1())) {
      reportFields.add(library.getString("label.chargenumber1"));
      sqlFields.add("account_number");
		groupByFields.add("account_number");
		reportFieldForTemplate.add("charge_number_1");
		reportFieldType[fieldIndex++] = CostReportInputBean.STRING_TYPE;
	 }
    if ("Yes".equalsIgnoreCase(inputBean.getChargeNumber2())) {
      reportFields.add(library.getString("label.chargenumber2"));
      sqlFields.add("account_number2");
		groupByFields.add("account_number2");
		reportFieldForTemplate.add("charge_number_2");
		reportFieldType[fieldIndex++] = CostReportInputBean.STRING_TYPE;
	 }
	 if ("Yes".equalsIgnoreCase(inputBean.getChargeNumber3())) {
      reportFields.add(library.getString("label.chargenumber3"));
      sqlFields.add("account_number3");
		groupByFields.add("account_number3");
		reportFieldForTemplate.add("charge_number_3");
		reportFieldType[fieldIndex++] = CostReportInputBean.STRING_TYPE;
	 }
	 if ("Yes".equalsIgnoreCase(inputBean.getChargeNumber4())) {
      reportFields.add(library.getString("label.chargenumber4"));
      sqlFields.add("account_number4");
		groupByFields.add("account_number4");
		reportFieldForTemplate.add("charge_number_4");
		reportFieldType[fieldIndex++] = CostReportInputBean.STRING_TYPE;
	 }
	 if ("Yes".equalsIgnoreCase(inputBean.getUserPo())) {
      reportFields.add(library.getString("label.userpo"));
      sqlFields.add("po_number");
		groupByFields.add("po_number");
		reportFieldForTemplate.add("user_po");
		reportFieldType[fieldIndex++] = CostReportInputBean.STRING_TYPE;
	 }
    if ("Yes".equalsIgnoreCase(inputBean.getSplitCharge())) {
      reportFields.add(library.getString("label.splitcharge"));
      sqlFields.add("percent_split_charge");
		groupByFields.add("percent_split_charge");
		reportFieldForTemplate.add("split_charge");
		reportFieldType[fieldIndex++] = CostReportInputBean.NUMBER_TYPE;
	 }
	 if ("Yes".equalsIgnoreCase(inputBean.getCompany())) {
      reportFields.add(library.getString("label.company"));
      sqlFields.add("company_id");
		groupByFields.add("company_id");
		reportFieldForTemplate.add("company");
		reportFieldType[fieldIndex++] = CostReportInputBean.STRING_TYPE;
	 }
    if ("Yes".equalsIgnoreCase(inputBean.getFacility())) {
      reportFields.add(library.getString("label.facility"));
      sqlFields.add("facility_display");
		groupByFields.add("facility_display");
		reportFieldForTemplate.add("facility");
		reportFieldType[fieldIndex++] = CostReportInputBean.STRING_TYPE;
	 }
    if ("Yes".equalsIgnoreCase(inputBean.getWorkArea())) {
      reportFields.add(library.getString("label.workarea"));
      sqlFields.add("application_desc");
		groupByFields.add("application_desc");
		reportFieldForTemplate.add("work_area");
		reportFieldType[fieldIndex++] = CostReportInputBean.STRING_TYPE;
	 }
    if ("Yes".equalsIgnoreCase(inputBean.getRequestor())) {
      reportFields.add(library.getString("label.requestor"));
      sqlFields.add("requestor_name");
		groupByFields.add("requestor_name");
		reportFieldForTemplate.add("requestor");
		reportFieldType[fieldIndex++] = CostReportInputBean.STRING_TYPE;
	 }
    if ("Yes".equalsIgnoreCase(inputBean.getFinanceApprover())) {
      reportFields.add(library.getString("label.financeapprover"));
      sqlFields.add("finance_approver_name");
		groupByFields.add("finance_approver_name");
		reportFieldForTemplate.add("finance_approver");
		reportFieldType[fieldIndex++] = CostReportInputBean.STRING_TYPE;
	 }
    if ("Yes".equalsIgnoreCase(inputBean.getMrLine())) {
      reportFields.add(library.getString("label.mrline"));
      sqlFields.add("mr_line");
		groupByFields.add("mr_line");
		reportFieldForTemplate.add("mr_line");
		reportFieldType[fieldIndex++] = CostReportInputBean.STRING_TYPE;
	 }
	 if ("Yes".equalsIgnoreCase(inputBean.getWasteOrder())) {
      reportFields.add(library.getString("label.wasteorder"));
      sqlFields.add("waste_order_no");
		groupByFields.add("waste_order_no");
		reportFieldForTemplate.add("waste_order");
		reportFieldType[fieldIndex++] = CostReportInputBean.NUMBER_TYPE;
	 }
    if ("Yes".equalsIgnoreCase(inputBean.getWasteManifest())) {
      reportFields.add(library.getString("label.wastemanifest"));
      sqlFields.add("waste_manifest_id");
		groupByFields.add("waste_manifest_id");
		reportFieldForTemplate.add("waste_manifest");
		reportFieldType[fieldIndex++] = CostReportInputBean.STRING_TYPE;
	 }
    if ("Yes".equalsIgnoreCase(inputBean.getPartNumber())) {
      reportFields.add(library.getString("label.partnumber"));
      sqlFields.add("cat_part_no");
		groupByFields.add("cat_part_no");
		reportFieldForTemplate.add("part_number");
		reportFieldType[fieldIndex++] = CostReportInputBean.STRING_TYPE;
	 }
    if ("Yes".equalsIgnoreCase(inputBean.getType())) {
      reportFields.add(library.getString("label.type"));
      sqlFields.add("type_desc");
		groupByFields.add("type_desc");
		reportFieldForTemplate.add("type");
		reportFieldType[fieldIndex++] = CostReportInputBean.STRING_TYPE;
	 }
	 if ("Yes".equalsIgnoreCase(inputBean.getPartDescription())) {
      reportFields.add(library.getString("label.partdescription"));
      sqlFields.add("part_description");
		groupByFields.add("part_description");
		reportFieldForTemplate.add("part_description");
		reportFieldType[fieldIndex++] = CostReportInputBean.STRING_TYPE;
	 }
	 if ("Yes".equalsIgnoreCase(inputBean.getPackaging())) {
      reportFields.add(library.getString("label.packaging"));
      sqlFields.add("packaging");
		groupByFields.add("packaging");
		reportFieldForTemplate.add("packaging");
		reportFieldType[fieldIndex++] = CostReportInputBean.STRING_TYPE;
	}

	if ("Yes".equalsIgnoreCase(inputBean.getCatPartAttribute())) {
		  reportFields.add(inputBean.getCatPartAttributeHeader());
		  sqlFields.add("cat_part_attribute");
		  groupByFields.add("cat_part_attribute");
		  reportFieldForTemplate.add("cat_part_attribute");
		  reportFieldType[fieldIndex++] = CostReportInputBean.STRING_TYPE;
	}

	if ("Yes".equalsIgnoreCase(inputBean.getQualityId())) {
		  reportFields.add(inputBean.getQualityIdLabel());
		  sqlFields.add("quality_id");
		  groupByFields.add("quality_id");
		  reportFieldForTemplate.add("quality_id");
		  reportFieldType[fieldIndex++] = CostReportInputBean.STRING_TYPE;
    }

    if ("Yes".equalsIgnoreCase(inputBean.getItem())) {
      reportFields.add(library.getString("label.item"));
      sqlFields.add("item_id");
		groupByFields.add("item_id");
		reportFieldForTemplate.add("item");
		reportFieldType[fieldIndex++] = CostReportInputBean.NUMBER_TYPE;
	 }
    if ("Yes".equalsIgnoreCase(inputBean.getItemDescription())) {
      reportFields.add(library.getString("label.itemdescription"));
      sqlFields.add("fx_display_item_desc(item_id) item_desc");
		groupByFields.add("fx_display_item_desc(item_id)");
		reportFieldForTemplate.add("item_description");
		reportFieldType[fieldIndex++] = CostReportInputBean.STRING_TYPE;
	 }
    if ("Yes".equalsIgnoreCase(inputBean.getManufacturer())) {
      reportFields.add(library.getString("label.manufacturer"));
      sqlFields.add("mfg_desc");
		groupByFields.add("mfg_desc");
		reportFieldForTemplate.add("manufacturer");
		reportFieldType[fieldIndex++] = CostReportInputBean.STRING_TYPE;
	 }
	 if ("Yes".equalsIgnoreCase(inputBean.getHaasPo())) {
      reportFields.add(library.getString("label.haaspo"));
      sqlFields.add("radian_po");
		groupByFields.add("radian_po");
		reportFieldForTemplate.add("haas_po");
		reportFieldType[fieldIndex++] = CostReportInputBean.NUMBER_TYPE;
	 }
    if ("Yes".equalsIgnoreCase(inputBean.getReference())) {
      reportFields.add(library.getString("label.reference"));
      sqlFields.add("reference_number");
		groupByFields.add("reference_number");
		reportFieldForTemplate.add("reference");
		reportFieldType[fieldIndex++] = CostReportInputBean.STRING_TYPE;
	 }
    if ("Yes".equalsIgnoreCase(inputBean.getReceiptId())) {
		if ("html".equalsIgnoreCase(reportOption)) {
			DbManager dbManager = null;
			try {
				dbManager = new DbManager(getClient());
				TcmisFeatureBeanFactory factory = new TcmisFeatureBeanFactory(dbManager);
				SearchCriteria criteria = new SearchCriteria();
				criteria.addCriterion("feature", SearchCriterion.EQUALS,"costReport.displayVoucher");
				criteria.addCriterion("featureMode", SearchCriterion.EQUALS,"1");
			  if (factory.selectExist(criteria)) {
				 reportFields.add("voucherUrl");
				 sqlFields.add("fx_voucher_url(receipt_id) voucher_url");
				 groupByFields.add("fx_voucher_url(receipt_id)");
				 reportFieldType[fieldIndex++] = CostReportInputBean.STRING_TYPE;
			  }
			}catch (Exception e) {
			  e.printStackTrace();
			}finally {
				dbManager = null;
			}
		}
		reportFields.add(library.getString("label.receiptid"));
        sqlFields.add("receipt_id");
		groupByFields.add("receipt_id");
		reportFieldForTemplate.add("receipt_id");
		reportFieldType[fieldIndex++] = CostReportInputBean.NUMBER_TYPE;
	 }
	 if ("Yes".equalsIgnoreCase(inputBean.getMfgLot())) {
      reportFields.add(library.getString("label.mfglot"));
      sqlFields.add("mfg_lot");
		groupByFields.add("mfg_lot");
		reportFieldForTemplate.add("mfg_lot");
		reportFieldType[fieldIndex++] = CostReportInputBean.STRING_TYPE;
	 }
     if ("Yes".equalsIgnoreCase(inputBean.getCustomerPartNo())) {
        reportFields.add(library.getString("label.customerpartnumber"));
        sqlFields.add("customer_part_no");
  		groupByFields.add("customer_part_no");
  		reportFieldForTemplate.add("customer_part_no");
  		reportFieldType[fieldIndex++] = CostReportInputBean.STRING_TYPE;
  	 }
     if ("Yes".equalsIgnoreCase(inputBean.getShippingReference())) {
        reportFields.add(library.getString("label.shippingreferenece"));
        sqlFields.add("shipping_reference");
  		groupByFields.add("shipping_reference");
  		reportFieldForTemplate.add("shipping_reference");
  		reportFieldType[fieldIndex++] = CostReportInputBean.STRING_TYPE;
  	 }   
	 if ("Yes".equalsIgnoreCase(inputBean.getDateDelivered())) {
      if ("Day".equalsIgnoreCase(inputBean.getDateDeliveredGroupBy())) {
        reportFields.add(library.getString("label.datedeliveredbyday"));
        sqlFields.add("to_date(to_char(date_delivered,'"+library.getString("java.databasedateformat")+"'),'"+library.getString("java.databasedateformat")+"') date_delivered");
		  groupByFields.add("to_date(to_char(date_delivered,'"+library.getString("java.databasedateformat")+"'),'"+library.getString("java.databasedateformat")+"')");
		}else if ("Month".equalsIgnoreCase(inputBean.getDateDeliveredGroupBy())) {
        reportFields.add(library.getString("label.datedeliveredbymonth"));
        sqlFields.add("to_date(to_char(date_delivered,'"+library.getString("java.databasemonthyearformat")+"'),'"+library.getString("java.databasemonthyearformat")+"') date_delivered");
		  groupByFields.add("to_date(to_char(date_delivered,'"+library.getString("java.databasemonthyearformat")+"'),'"+library.getString("java.databasemonthyearformat")+"')");
		}else {
        reportFields.add(library.getString("label.datedeliveredbyyear"));
        sqlFields.add("to_date(to_char(date_delivered,'"+library.getString("java.yearformat")+"'),'"+library.getString("java.yearformat")+"') date_delivered");
		  groupByFields.add("to_date(to_char(date_delivered,'"+library.getString("java.yearformat")+"'),'"+library.getString("java.yearformat")+"')");
		}
		orderBy += "date_delivered";
		reportFieldForTemplate.add("date_delivered");
		reportFieldType[fieldIndex++] = CostReportInputBean.DATE_TYPE;
	 }
	 boolean needUom = true;
	 if ("Yes".equalsIgnoreCase(inputBean.getQuantity())) {
      reportFields.add(library.getString("label.quantity"));
      sqlFields.add("quantity");
		groupByFields.add("quantity");
		reportFieldForTemplate.add("quantity");
		reportFieldType[fieldIndex++] = CostReportInputBean.NUMBER_TYPE;
		if (needUom) {
			needUom = false;
			reportFields.add(library.getString("label.uom"));
			if ("Invoice".equalsIgnoreCase(inputBean.getUom())) {
				sqlFields.add("unit_of_sale");
				groupByFields.add("unit_of_sale");
				reportFieldForTemplate.add("unit_of_sale");
			}else {
				sqlFields.add("item_uom");
				groupByFields.add("item_uom");
				reportFieldForTemplate.add("item_uom");
			}
			reportFieldType[fieldIndex++] = CostReportInputBean.STRING_TYPE;
		}
	 }
	 if ("Yes".equalsIgnoreCase(inputBean.getInvoiceUnitPrice())) {
		if (needUom) {
			needUom = false;
			reportFields.add(library.getString("label.uom"));
			if ("Invoice".equalsIgnoreCase(inputBean.getUom())) {
				sqlFields.add("unit_of_sale");
				groupByFields.add("unit_of_sale");
				reportFieldForTemplate.add("unit_of_sale");
			}else {
				sqlFields.add("item_uom");
				groupByFields.add("item_uom");
				reportFieldForTemplate.add("item_uom");
			}
			reportFieldType[fieldIndex++] = CostReportInputBean.STRING_TYPE;
		}
      reportFields.add(library.getString("label.unitprice"));
      sqlFields.add("invoice_unit_price");
		groupByFields.add("invoice_unit_price");
		reportFieldForTemplate.add("invoice_unit_price");
		reportFieldType[fieldIndex++] = CostReportInputBean.NUMBER_TYPE;
	 }
    if ("Yes".equalsIgnoreCase(inputBean.getUnitRebate())) {
		if (needUom) {
			needUom = false;
			reportFields.add(library.getString("label.uom"));
			if ("Invoice".equalsIgnoreCase(inputBean.getUom())) {
				sqlFields.add("unit_of_sale");
				groupByFields.add("unit_of_sale");
				reportFieldForTemplate.add("unit_of_sale");
			}else {
				sqlFields.add("item_uom");
				groupByFields.add("item_uom");
				reportFieldForTemplate.add("item_uom");
			}
			reportFieldType[fieldIndex++] = CostReportInputBean.STRING_TYPE;
		}
        reportFields.add(library.getString("label.unitrebate"));
        sqlFields.add("unit_rebate");
		groupByFields.add("unit_rebate");
		reportFieldForTemplate.add("unit_rebate");
		reportFieldType[fieldIndex++] = CostReportInputBean.NUMBER_TYPE;
	 }
	 if ("Yes".equalsIgnoreCase(inputBean.getTotalFreightCharge())) {
		reportFields.add(library.getString("label.totalfreightcharge"));
        sqlFields.add("total_freight_charge");
		groupByFields.add("total_freight_charge");
		reportFieldForTemplate.add("total_freight_charge");
		reportFieldType[fieldIndex++] = CostReportInputBean.NUMBER_TYPE;
	 }
	 boolean whereClauseForLinkExist = false;
	 if ("Yes".equalsIgnoreCase(inputBean.getTotalAdditionalCharge())) {
      if (!whereClauseForLinkExist && "html".equalsIgnoreCase(reportOption)) {
			reportFields.add("whereClauseForLink");
			whereClauseForLinkExist = true;
		}
		reportFields.add(library.getString("label.totaladditionalcharge"));
        sqlFields.add("total_add_charge");
		groupByFields.add("total_add_charge");
		reportFieldForTemplate.add("total_additional_charge");
		reportFieldType[fieldIndex++] = CostReportInputBean.NUMBER_TYPE;
	 }
    if ("Yes".equalsIgnoreCase(inputBean.getTotalSalesTax())) {
      if (!whereClauseForLinkExist && "html".equalsIgnoreCase(reportOption)) {
			reportFields.add("whereClauseForLink");
			whereClauseForLinkExist = true;
		}
		reportFields.add(library.getString("label.totalsalestax"));
		sqlFields.add("total_sales_tax");
		groupByFields.add("total_sales_tax");
		reportFieldForTemplate.add("total_sales_tax");
		reportFieldType[fieldIndex++] = CostReportInputBean.NUMBER_TYPE;
	 }
    if ("Yes".equalsIgnoreCase(inputBean.getServiceFee())) {
      reportFields.add(library.getString("label.servicefee"));
      sqlFields.add("service_fee");
		groupByFields.add("service_fee");
		reportFieldForTemplate.add("service_fee");
		reportFieldType[fieldIndex++] = CostReportInputBean.NUMBER_TYPE;
	 }
    if ("Yes".equalsIgnoreCase(inputBean.getPeiAmount())) {
      reportFields.add(library.getString("label.peiamount"));
      sqlFields.add("pei_amount");
		groupByFields.add("pei_amount");
		reportFieldForTemplate.add("pei_amount");
		reportFieldType[fieldIndex++] = CostReportInputBean.NUMBER_TYPE;
	 }
	 if ("Yes".equalsIgnoreCase(inputBean.getNetAmount())) {
      reportFields.add(library.getString("label.netamount"));
      sqlFields.add("net_amount");
		groupByFields.add("net_amount");
		reportFieldForTemplate.add("net_amount");
		reportFieldType[fieldIndex++] = CostReportInputBean.NUMBER_TYPE;
	 }
    if ("Yes".equalsIgnoreCase(inputBean.getMaterialSavings())) {
      reportFields.add(library.getString("label.materialsavings"));
      sqlFields.add("total_rebate");
		groupByFields.add("total_rebate");
		reportFieldForTemplate.add("material_savings");
		reportFieldType[fieldIndex++] = CostReportInputBean.NUMBER_TYPE;
	 }

    //show currency
     showCurrency(reportFields,sqlFields,groupByFields,library);
    //put report fields into user's selected data
	 inputBean.setReportFields(reportFields);
	 inputBean.setSqlFields(sqlFields);
	 inputBean.setOrderBy(orderBy);
	 inputBean.setGroupBy(groupByFields);
	 inputBean.setReportFieldForTemplate(reportFieldForTemplate);
	 inputBean.setReportFieldType(reportFieldType);
  } //end of method

  private void showCurrency(Collection reportFields, Collection sqlFields, Collection groupByFields, ResourceLibrary library) {
    try {
	  if (sqlFields.contains("net_amount") || sqlFields.contains("pei_amount") ||
			sqlFields.contains("service_fee") || sqlFields.contains("total_freight_charge") || sqlFields.contains("total_add_charge") ||
			sqlFields.contains("unit_rebate") || sqlFields.contains("invoice_unit_price") ||
			sqlFields.contains("total_rebate") || sqlFields.contains("total_sales_tax")) {
		 reportFields.add(library.getString("label.currency"));
		 sqlFields.add("currency_id");
		 groupByFields.add("currency_id");
	  }
    }catch (Exception e) {
      e.printStackTrace();
    }
  } // end of method

  public Collection getNormalizedResultCollection(Collection inputCollection) throws BaseException {
	Collection normalizedCollection = new Vector(inputCollection.size());
	if (inputCollection.size() > 0) {
		try {
			Iterator iterator = inputCollection.iterator();
			String previousCompanyId = "";
			String previousCompanyReportGroup = "";
			String previousFacilityId = "";
			String previousAccountSysId = "";
			String previousChargeType = "";
			CostReportChargeTypeBean chargeTypeBean = null;
			CostReportAccountSysBean accountSysBean = null;
			FacLocAppBean facLocAppBean = null;
			CostReportFacilityBean facilityBean = null;
			CostReportGroupBean costReportGroupBean = null;
			CostReportCompanyGroupBean companyGroupBean = null;
			Collection reportingGroupList = null;
			Collection facilityList = null;
			Collection accountSysList = null;
			Collection applicationList = null;
			Vector applicationKeyList = null;
			Collection chargeTypeList = null;
			//company accounting systems and charge types
			Vector companyAccountSysChargeTypeKey = new Vector();
			Collection companyAccountSysList = null;
			Collection companyAccountSysChargeTypeList = null;
			//group accounting systems and charge types
			Vector groupAccountSysChargeTypeKey = new Vector();
			Collection groupAccountSysList = null;
			Collection groupAccountSysChargeTypeList = null;


			while (iterator.hasNext()) {
				CostReportInitialViewBean flatBean = (CostReportInitialViewBean) iterator.next();
				if (!previousCompanyId.equals(flatBean.getCompanyId())) {
					companyGroupBean= new CostReportCompanyGroupBean();
					this.copyCompanyBean(companyGroupBean, flatBean);
					normalizedCollection.add(companyGroupBean);
					reportingGroupList = new Vector();
					companyGroupBean.setCostReportGroupList(reportingGroupList);
					previousCompanyReportGroup = "";
					//company accounting systems and charge types
					companyAccountSysList = new Vector();
					companyGroupBean.setCompanyAccountSysList(companyAccountSysList);
				}
				if (!previousCompanyReportGroup.equals(flatBean.getCompanyId()+flatBean.getCostReportGroup())) {
					costReportGroupBean = new CostReportGroupBean();
					this.copyReportGroupBean(costReportGroupBean, flatBean);
					reportingGroupList.add(costReportGroupBean);
					facilityList = new Vector();
					costReportGroupBean.setFacilityList(facilityList);
					previousFacilityId = "";
					//group accounting systems and charge types
					groupAccountSysList = new Vector();
					costReportGroupBean.setGroupAccountSysList(groupAccountSysList);
				}
				if (!previousFacilityId.equals(flatBean.getCompanyId()+flatBean.getCostReportGroup()+flatBean.getFacilityId())) {
					facilityBean = new CostReportFacilityBean();
					this.copyFacilityBean(facilityBean, flatBean);
					facilityList.add(facilityBean);
					accountSysList = new Vector();
					facilityBean.setAccountSysList(accountSysList);
					previousAccountSysId = "";
					applicationList = new Vector();
					facilityBean.setApplicationList(applicationList);
					applicationKeyList = new Vector();
				}
				if (!applicationKeyList.contains(flatBean.getApplication())) {
					facLocAppBean = new FacLocAppBean();
					this.copyApplicationBean(facLocAppBean, flatBean);
					applicationList.add(facLocAppBean);
					applicationKeyList.add(flatBean.getApplication());
				}
				if (!previousAccountSysId.equals(flatBean.getCompanyId()+flatBean.getCostReportGroup()+flatBean.getFacilityId()+flatBean.getAccountSysId())) {
					accountSysBean = new CostReportAccountSysBean();
					this.copyAccountSysBean(accountSysBean, flatBean);
					accountSysList.add(accountSysBean);
					chargeTypeList = new Vector();
					accountSysBean.setChargeTypeList(chargeTypeList);
					previousChargeType = "";
				}
				if (!previousChargeType.equals(flatBean.getCompanyId()+flatBean.getCostReportGroup()+flatBean.getFacilityId()+flatBean.getAccountSysId()+flatBean.getChargeType())) {
					chargeTypeBean = new CostReportChargeTypeBean();
					this.copyChargeTypeBean(chargeTypeBean, flatBean);
					chargeTypeList.add(chargeTypeBean);
				}
				//company account systems and charge types
				if (!companyAccountSysChargeTypeKey.contains(flatBean.getCompanyId()+flatBean.getAccountSysId())) {
					accountSysBean = new CostReportAccountSysBean();
					this.copyAccountSysBean(accountSysBean, flatBean);
					companyAccountSysList.add(accountSysBean);
					companyAccountSysChargeTypeList = new Vector();
					accountSysBean.setChargeTypeList(companyAccountSysChargeTypeList);
					companyAccountSysChargeTypeKey.add(flatBean.getCompanyId()+flatBean.getAccountSysId());
				}
				if (!companyAccountSysChargeTypeKey.contains(flatBean.getCompanyId()+flatBean.getAccountSysId()+flatBean.getChargeType())) {
					chargeTypeBean = new CostReportChargeTypeBean();
					this.copyChargeTypeBean(chargeTypeBean, flatBean);
					companyAccountSysChargeTypeList.add(chargeTypeBean);
					companyAccountSysChargeTypeKey.add(flatBean.getCompanyId()+flatBean.getAccountSysId()+flatBean.getChargeType());
				}
				//group account systems and charge types
				if (!groupAccountSysChargeTypeKey.contains(flatBean.getCompanyId()+flatBean.getCostReportGroup()+flatBean.getAccountSysId())) {
					accountSysBean = new CostReportAccountSysBean();
					this.copyAccountSysBean(accountSysBean, flatBean);
					groupAccountSysList.add(accountSysBean);
					groupAccountSysChargeTypeList = new Vector();
					accountSysBean.setChargeTypeList(groupAccountSysChargeTypeList);
					groupAccountSysChargeTypeKey.add(flatBean.getCompanyId()+flatBean.getCostReportGroup()+flatBean.getAccountSysId());
				}
				if (!groupAccountSysChargeTypeKey.contains(flatBean.getCompanyId()+flatBean.getCostReportGroup()+flatBean.getAccountSysId()+flatBean.getChargeType())) {
					chargeTypeBean = new CostReportChargeTypeBean();
					this.copyChargeTypeBean(chargeTypeBean, flatBean);
					groupAccountSysChargeTypeList.add(chargeTypeBean);
					groupAccountSysChargeTypeKey.add(flatBean.getCompanyId()+flatBean.getCostReportGroup()+flatBean.getAccountSysId()+flatBean.getChargeType());
				}

				previousCompanyId = flatBean.getCompanyId();
				previousCompanyReportGroup = previousCompanyId + flatBean.getCostReportGroup();
				previousFacilityId = previousCompanyReportGroup + flatBean.getFacilityId();
				previousAccountSysId = previousFacilityId + flatBean.getAccountSysId();
				previousChargeType = previousAccountSysId + flatBean.getChargeType();

			} //end of while
		} catch (Exception e) {
			log.error("Error normalizing data", e);
			throw new BaseException(e);
		}
	}
	return normalizedCollection;
} //end of method

void checkingData(Collection col) {
	Iterator iterator = col.iterator();
	while (iterator.hasNext()) {
		CostReportCompanyGroupBean a = (CostReportCompanyGroupBean)iterator.next();
		Collection x = a.getCostReportGroupList();
		Iterator iterator2 = x.iterator();
		while (iterator2.hasNext()) {
			CostReportGroupBean b = (CostReportGroupBean)iterator2.next();
			Collection y = b.getFacilityList();
			Iterator iterator3 = y.iterator();
			while (iterator3.hasNext()) {
				CostReportFacilityBean c = (CostReportFacilityBean)iterator3.next();
				Collection q = c.getApplicationList();
				Iterator i4 = q.iterator();
				while(i4.hasNext()) {
					FacLocAppBean d = (FacLocAppBean)i4.next();
				}
				Collection l = c.getAccountSysList();
				Iterator i5 = l.iterator();
				while (i5.hasNext()) {
					CostReportAccountSysBean f = (CostReportAccountSysBean)i5.next();
					Collection u = f.getChargeTypeList();
					Iterator i6 = u.iterator();
					while (i6.hasNext()) {
						CostReportChargeTypeBean k = (CostReportChargeTypeBean)i6.next();
					}
				}
			}
		}
	}
}

private void copyCompanyBean(CostReportCompanyGroupBean bean, CostReportInitialViewBean flatBean) {
	bean.setCompanyId(flatBean.getCompanyId());
	bean.setCompanyName(flatBean.getCompanyName());
}

private void copyReportGroupBean(CostReportGroupBean bean, CostReportInitialViewBean flatBean) {
	bean.setCostReportGroup(flatBean.getCostReportGroup());
}

private void copyFacilityBean(CostReportFacilityBean bean, CostReportInitialViewBean flatBean) {
	bean.setFacilityId(flatBean.getFacilityId());
	bean.setFacilityName(flatBean.getFacilityName());
}

private void copyApplicationBean(FacLocAppBean bean, CostReportInitialViewBean flatBean) {
	bean.setApplication(flatBean.getApplication());
	bean.setApplicationDesc(flatBean.getApplicationDesc());
}

private void copyAccountSysBean(CostReportAccountSysBean bean, CostReportInitialViewBean flatBean) {
	bean.setAccountSysId(flatBean.getAccountSysId());
}

private void copyChargeTypeBean(CostReportChargeTypeBean bean, CostReportInitialViewBean flatBean) {
	bean.setChargeType(flatBean.getChargeType());
	bean.setPoRequired(flatBean.getPoRequired());
	bean.setPrAccountRequired(flatBean.getPrAccountRequired());
	bean.setChargeLabel1(flatBean.getChargeLabel1());
	bean.SetChargeLabel2(flatBean.getChargeLabel2());
}

public Collection getCompaniesFacilitiesData(BigDecimal personnelId) {
	Collection dropdownColl = null;
	DbManager dbManager = null;
	try {
		dbManager = new DbManager(getClient());
		CostReportInitialOvBeanFactory factory = new CostReportInitialOvBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("personnelId", SearchCriterion.EQUALS, (personnelId.toString()));
		dropdownColl = factory.selectObject(criteria);
	} catch (Exception e) {

	} finally {
		dbManager = null;
	}

	return dropdownColl;
} //end of method

	public String getCatPartAttributeHeader(BigDecimal personnelId) {
		String result = "";
		try {
			DbManager dbManager = new DbManager(getClient(), getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager, new CostReportInputBean());
			StringBuilder query = new StringBuilder("select distinct cat_part_attribute_header from catalog c, catalog_facility cf, user_facility uf");
			query.append(" where c.company_id = cf.company_id and c.catalog_company_id = cf.catalog_company_id and c.catalog_id = cf.catalog_id");
			query.append(" and cf.company_id = uf.company_id and cf.facility_id = uf.facility_id and uf.personnel_id = ").append(personnelId);
			Iterator iter = factory.selectQuery(query.toString()).iterator();
			String catPartAttributeHeader = "";
			while (iter.hasNext()) {
				CostReportInputBean bean = (CostReportInputBean)iter.next();
				if (!StringHandler.isBlankString(bean.getCatPartAttributeHeader())) {
					if (catPartAttributeHeader.length() > 1)
						catPartAttributeHeader += "; ";
					catPartAttributeHeader += bean.getCatPartAttributeHeader();
				}
			}
			//due to display limitation - only display first 50 characters
			if (catPartAttributeHeader.length() > 50)
				catPartAttributeHeader = catPartAttributeHeader.substring(0,49);
			result = catPartAttributeHeader;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}	//end of method

	public String getQualityIdLabel(BigDecimal personnelId) {
		String result = "";
		try {
			DbManager dbManager = new DbManager(getClient(), getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager, new CostReportInputBean());
			StringBuilder query = new StringBuilder("select distinct quality_id_label from catalog c, catalog_facility cf, user_facility uf");
			query.append(" where c.company_id = cf.company_id and c.catalog_company_id = cf.catalog_company_id and c.catalog_id = cf.catalog_id");
			query.append(" and cf.company_id = uf.company_id and cf.facility_id = uf.facility_id and uf.personnel_id = ").append(personnelId);
			Iterator iter = factory.selectQuery(query.toString()).iterator();
			String qualityIdLabel = "";
			while (iter.hasNext()) {
				CostReportInputBean bean = (CostReportInputBean)iter.next();
				if (!StringHandler.isBlankString(bean.getQualityIdLabel())) {
					if (qualityIdLabel.length() > 1)
						qualityIdLabel += "; ";
					qualityIdLabel += bean.getQualityIdLabel();
				}
			}
			//due to display limitation - only display first 50 characters
			if (qualityIdLabel.length() > 50)
				qualityIdLabel = qualityIdLabel.substring(0,49);
			result = qualityIdLabel;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}	//end of method

} //end of class
