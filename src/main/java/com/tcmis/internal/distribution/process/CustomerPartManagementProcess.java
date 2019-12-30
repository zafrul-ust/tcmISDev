package com.tcmis.internal.distribution.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.internal.distribution.beans.CustomerPartManagementInputBean;

import java.util.*;
import java.math.BigDecimal;
import java.sql.Connection;

/******************************************************************************
 * Process tcreate the .
 * @version 1.0
 *****************************************************************************/
public class CustomerPartManagementProcess extends GenericProcess {
  	Log log = LogFactory.getLog(this.getClass());

  	public CustomerPartManagementProcess(String client,String locale) {
		super(client,locale);
	}

	public Collection updateData(CustomerPartManagementInputBean inputBean, Collection beans) throws Exception {
		DbManager dbManager = new DbManager(getClient(),this.getLocale());
		Connection connection = dbManager.getConnection();
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);
		Vector resultCollection = null;
		Collection inArgs = null;
		//String result = "";
		try {
			Iterator iter = beans.iterator();
			String customerBilltoCompanyId = "";
			boolean needToCallProcedure = false;
			while (iter.hasNext()) {
				CustomerPartManagementInputBean bean = (CustomerPartManagementInputBean)iter.next();
				if ("true".equals(bean.getOk())) {
					inArgs = new Vector(9);
					Collection outArgs = new Vector(2);
					outArgs.add(new Integer(java.sql.Types.VARCHAR));
					outArgs.add(new Integer(java.sql.Types.VARCHAR));
					Collection inOptArgs = new Vector(4);
					//new record
					if(bean.getOldCustomerPartNo() == null || bean.getOldCustomerPartNo().length() == 0) {
						//create new record
						//get customerBilltoCompanyId if it's empty
						if (customerBilltoCompanyId.length() == 0) {
							customerBilltoCompanyId = getCustomerBilltoCompanyId(factory,inputBean.getCustomerId());
						}
						inArgs.add(customerBilltoCompanyId);
						inArgs.add(bean.getCustomerPartNo());
						inArgs.add(inputBean.getCustomerId());
						inArgs.add(bean.getCatPartNo());
						inOptArgs.add(bean.getStatus());
						inOptArgs.add("");
						inOptArgs.add(bean.getCatalogCompanyId());
						inOptArgs.add(bean.getCatalogId());
						needToCallProcedure = true;
					}else {
						if (bean.getCustomerPartNo().equalsIgnoreCase(bean.getOldCustomerPartNo())) {
							//change status
							if (!bean.getStatus().equals(bean.getOldStatus())) {
								inArgs.add(bean.getCompanyId());
								inArgs.add(bean.getCustomerPartNo());
								inArgs.add(bean.getCustomerId());
								inArgs.add(bean.getCatPartNo());
								inOptArgs.add(bean.getStatus());
								inOptArgs.add(bean.getOldCustomerPartNo());
								inOptArgs.add(bean.getCatalogCompanyId());
								inOptArgs.add(bean.getCatalogId());
								needToCallProcedure = true;
							}
						}else {
							//create new record
							inArgs.add(bean.getCompanyId());
							inArgs.add(bean.getCustomerPartNo());
							inArgs.add(bean.getCustomerId());
							inArgs.add(bean.getCatPartNo());
							inOptArgs.add(bean.getStatus());
							inOptArgs.add(bean.getOldCustomerPartNo());
							inOptArgs.add(bean.getCatalogCompanyId());
							inOptArgs.add(bean.getCatalogId());
							needToCallProcedure = true;
						}
					}
					if (needToCallProcedure) {
						log.debug(inArgs);
						log.debug(inOptArgs);
						resultCollection = (Vector)factory.doProcedure(connection,"Pkg_rli_sales_order.P_CUSTOMER_PART_UPSERT",inArgs,outArgs,inOptArgs);
						/*
						Collection procedureData = factory.doProcedure(connection,"Pkg_rli_sales_order.P_CUSTOMER_PART_UPSERT",inArgs,outArgs,inOptArgs);
						Iterator i11 = procedureData.iterator();
						String tmpResult = "";
						while (i11.hasNext()) {
							tmpResult = i11.next().toString();
						}
						log.debug("result from procedure call:"+tmpResult+"*");
						*/
						needToCallProcedure = false;
						/*for(Object s : resultCollection)
							result = (String)s;*/
							
					}
				} //end of user check ok
			} //end of loop
		}catch (Exception e) {
			e.printStackTrace();
		    //MailProcess.sendEmail("deverror@tcmis.com",null,"deverror@tcmis.com","Main error", e.getMessage());
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			factory = null;
		}
			 if(!resultCollection.get(1).toString().equalsIgnoreCase("ok"))
			 {
				return resultCollection;
			 }
			 else
			 	return null;

	}

	public void deleteData(CustomerPartManagementInputBean inputBean, Collection<CustomerPartManagementInputBean> beans) throws Exception {
		for (CustomerPartManagementInputBean bean:beans) {
			if ("true".equals(bean.getOk())) {
				if(isBlank(bean.getCustomerPartNo()) ) continue;
				String query = 
					"delete from customer.customer_part where customer_id = " + bean.getCustomerId() +
					" and customer_part_no = " + getSqlString(bean.getCustomerPartNo() ) +
					" and cat_part_no = " + getSqlString(bean.getCatPartNo());
				factory.deleteInsertUpdate(query);
			}
		}
	}

	private String getCustomerBilltoCompanyId (GenericSqlFactory factory, BigDecimal customerId) {
		String result = "";
		try {
			StringBuilder query = new StringBuilder("select bill_to_company_id from bill_to_customer where customer_id = ").append(customerId);
			result = factory.selectSingleValue(query.toString());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public HashMap getSearchDataRowSpan(Collection dataColl) throws Exception {
		HashMap m1 = new HashMap();
		Integer i1 = null;
		BigDecimal lastItemId = new BigDecimal("0");
		//looping thru line data
		Iterator iter = dataColl.iterator();
		while(iter.hasNext()) {
			CustomerPartManagementInputBean tmpBean = (CustomerPartManagementInputBean) iter.next();
			BigDecimal currentItemId = tmpBean.getItemId();
			if (currentItemId.equals(lastItemId)) {
				currentItemId = lastItemId;
			}

			if (m1.get(currentItemId) == null) {
				i1 = new Integer(0);
				m1.put(currentItemId, i1);
			}
			i1 = (Integer) m1.get(currentItemId);
			i1 = new Integer(i1.intValue() + 1);
			m1.put(currentItemId, i1);

			lastItemId = currentItemId;
		}
		return m1;
	}

	public Collection getSearchData(CustomerPartManagementInputBean inputBean) throws Exception {
		DbManager dbManager = new DbManager(getClient(),this.getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new CustomerPartManagementInputBean());
		StringBuilder query = new StringBuilder("select ci.catalog_company_id,ci.catalog_id,ci.item_id,ci.part_description,cis.cat_part_no,");
		query.append("cp.customer_id,cp.company_id,cp.customer_part_no,cp.status,cp.date_inserted,");
		query.append("pkg_intercompany_mr.FX_CAT_PART_SPEC_LIST (cis.cat_part_no,'SPEC_ID, DETAIL, COC, COA',', ',cis.catalog_company_id,cis.catalog_id,'Y') spec_list");
		query.append(" from catalog_item ci,catalog_item_spec cis,customer.customer_part cp");
		query.append(" where ci.catalog_company_id = cis.catalog_company_id and ci.catalog_id = cis.catalog_id");
		query.append(" and ci.item_id = cis.item_id and cis.catalog_company_id = cp.catalog_company_id(+)");
		query.append(" and cis.catalog_id = cp.catalog_id(+) and cis.cat_part_no = cp.cat_part_no(+)");
		query.append(" and cp.customer_id(+) = ").append(inputBean.getCustomerId());
        if ("item".equals(inputBean.getSearchBy())) {
			if ("is".equals(inputBean.getSearchType())) {
				query.append(" and ci.item_id = '").append(inputBean.getSearchText()).append("'");
			}else {
				query.append(" and (").append(doSearchLogic(StringHandler.replace(inputBean.getSearchText(),"'","''"),"itemId")).append(")");
			}
		}
        else if ("customerPartNo".equals(inputBean.getSearchBy())) {
			if ("is".equals(inputBean.getSearchType())) {
				query.append(" and cp.customer_part_no = '").append(inputBean.getSearchText()).append("'");
			}else {
				query.append(" and (").append(doSearchLogic(StringHandler.replace(inputBean.getSearchText(),"'","''"),"customerPartNo")).append(")");
			}
		}else {
			if ("is".equals(inputBean.getSearchType())) {
				query.append(" and ci.part_description = '").append(inputBean.getSearchText()).append("'");
			}else {
				query.append(" and (").append(doSearchLogic(StringHandler.replace(inputBean.getSearchText(),"'","''"),"partDescription")).append(")");
			}
		}
		if ("Y".equalsIgnoreCase(inputBean.getActiveOnly())) {
			query.append(" and status = 'A'");
		}
		query.append(" order by item_id");
		return factory.selectQuery(query.toString());
	}

	public String doSearchLogic(String search, String field) {
		Hashtable r = StringHandler.getLogicalHashFromString(search);
		Vector opers = (Vector) r.get("OPERATORS");
		Vector likes = (Vector) r.get("LIKES");
		String result = "";
		//no operator
		if (opers.size() < 1) {
			if ("itemId".equals(field)) {
				result = " (ci.item_id like lower('%" + search + "%'))";
			}
            else if ("customerPartNo".equals(field)) {
				result = " (lower(cp.customer_part_no) like lower('%" + search + "%'))";
			}else {
				result = " (lower(part_description) like lower('%" + search + "%'))";
			}
			return result;
		}

		//contains operation in search text
		if ("itemId".equals(field)) {
			result += " ( (ci.item_id like lower('%" + likes.elementAt(0).toString().trim() + "%')) ";
		}
        else if ("customerPartNo".equals(field)) {
			result += " ( (lower(cp.customer_part_no) like lower('%" + likes.elementAt(0).toString().trim() + "%')) ";
		}else {
			result += " ( (lower(part_description) like lower('%" + likes.elementAt(0).toString().trim() + "%')) ";
		}
		boolean butNot = false;
		for (int i = 0; i < opers.size(); i++) {
			String op = opers.elementAt(i).toString();
			String lk = "like";
			if (op.equalsIgnoreCase("but not")) {
				op = "and";
				lk = "not like";
				butNot = true;
			}
			String searchS = likes.elementAt(i + 1).toString().trim();
			if ("itemId".equals(field)) {
				if (butNot) {
					result += " " + op + " (ci.item_id " + lk + " lower('%" + searchS + "%')) ";
				}else {
					result += " " + op + " (ci.item_id " + lk + " lower('%" + searchS + "%')) ";
				}
			}
            else if ("customerPartNo".equals(field)) {
				if (butNot) {
					result += " " + op + " (lower(cp.customer_part_no) " + lk + " lower('%" + searchS + "%')) ";
				}else {
					result += " " + op + " (lower(cp.customer_part_no) " + lk + " lower('%" + searchS + "%')) ";
				}
			}else {
				if (butNot) {
					result += " " + op + " (lower(part_description) " + lk + " lower('%" + searchS + "%')) ";
				}else {
					result += " " + op + " (lower(part_description) " + lk + " lower('%" + searchS + "%')) ";
				}
			}
		}
		result += " ) ";

		return result;
	}

  public ExcelHandler getExcelReport(CustomerPartManagementInputBean inputBean) throws NoDataException, BaseException, Exception {
   ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
	ExcelHandler pw = new ExcelHandler(library);
	pw.addTable();

	//	  write column headers
   pw.addRow();
   pw.addCellKeyBold("label.customerpartmanagement");

   //blank row
   pw.addRow();

   //result table
   //write column headers
   pw.addRow();

   //Pass the header keys for the Excel.
   String[] headerkeys = {
     "label.item",
	  "label.desc",
	  "label.specs",
	  "label.customerpart",
	  "label.status",
	  "label.date"
		};

   //This array defines the type of the excel column.
   //0 means default depending on the data type.
   int[] types = {
     0,0,0,0,0,pw.TYPE_CALENDAR};
   //This array defines the default width of the column when the Excel file opens.
   //0 means the width will be default depending on the data type.
   int[] widths = {
     15,30,30,15,10,10};
   //This array defines the horizontal alignment of the data in a cell.
   //0 means excel defaults the horizontal alignemnt by the data type.
   int[] horizAligns = {
     0,0,0,0,0,0};

   pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

	Collection<CustomerPartManagementInputBean> data = this.getSearchData(inputBean);
   //now write data
	for (CustomerPartManagementInputBean member : data) {
		 pw.addRow();
		 pw.addCell(member.getItemId());
		 pw.addCell(member.getPartDescription());
		 pw.addCell(member.getSpecList());
		 pw.addCell(member.getCustomerPartNo());
		 pw.addCell(member.getStatus());
		 pw.addCell(member.getDateInserted());
	}
   return pw;
 } //end of method
} //end of class