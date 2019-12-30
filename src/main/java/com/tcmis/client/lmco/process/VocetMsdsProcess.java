package com.tcmis.client.lmco.process;

import java.sql.Connection;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.lmco.beans.VocetCoatCategoryBean;
import com.tcmis.client.lmco.beans.VocetMsdsSearchViewBean;
import com.tcmis.client.lmco.beans.VocetStatusBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.*;

/**
 * ******************************************************************
 * Process for the Vocet MSDS
 * 
 * @version 1.0
 * *****************************************************************
 */

public class VocetMsdsProcess extends GenericProcess {
	Log log = LogFactory.getLog(this.getClass());

	public VocetMsdsProcess(String client, Locale locale) {
		super(client, locale);
	}

	public VocetMsdsProcess(String client) {
		super(client);
	}
	
	public Collection<VocetStatusBean> getVocetStatusColl() throws BaseException {
		SearchCriteria criteria = new SearchCriteria();
		
		SortCriteria sort = new SortCriteria();
		sort.setSortAscending(true);
		sort.addCriterion("facilityId,vocetStatusDesc");
				
		return factory.setBean(new VocetStatusBean()).select(criteria, sort, "vocet_status");
	}
	
	public Collection<VocetCoatCategoryBean> getVocetCoatCategoryColl() throws BaseException {
		SearchCriteria criteria = new SearchCriteria();
		
		SortCriteria sort = new SortCriteria();
		sort.setSortAscending(true);
		sort.addCriterion("facilityId,vocetCoatCategoryDesc");
				
		return factory.setBean(new VocetCoatCategoryBean()).select(criteria, sort, "vocet_coat_category");
	}

	public Collection<VocetMsdsSearchViewBean> getSearchResult(VocetMsdsSearchViewBean inputBean) throws BaseException {
		factory.setBean(new VocetMsdsSearchViewBean());

        StringBuilder query = new StringBuilder(" select nvl(to_char(customer_mixture_number), customer_msds_number) customer_msds_or_mixture_no,  ");
		query.append("company_id, facility_Id, material_Id, customer_mixture_number, customer_msds_number, mixture_Desc, trade_name, vocet_Status, vocet_Category_Id, customer_Msds_Db, updated_by_name, updated_on");
		query.append(" from VOCET_MSDS_SEARCH_VIEW where facility_id ='").append(inputBean.getFacilityId()).append("'");
        if(!StringHandler.isBlankString(inputBean.getSearchText())) {
            if ("=".equalsIgnoreCase(inputBean.getMatchType())) {
                //if number field
                if ("material_id".equals(inputBean.getSearchField())) {
                    query.append(" and ").append(inputBean.getSearchField()).append(" = ").append(inputBean.getSearchText());
                }else if ("customer_msds_or_mixture_no".equals(inputBean.getSearchField())) {
                    query.append(" and ( (customer_msds_number = '").append(inputBean.getSearchText()).append("') ");
                    query.append(" or customer_mixture_number = '").append(inputBean.getSearchText()).append("')");
                }else {
                    //string fields
                    query.append(" and ").append(inputBean.getSearchField()).append(" = '").append(inputBean.getSearchText()).append("'");
                }
            }else if ("like".equalsIgnoreCase(inputBean.getMatchType())) {
                if ("customer_msds_or_mixture_no".equals(inputBean.getSearchField())) {
                    query.append(" and ( (customer_msds_number like '%").append(inputBean.getSearchText()).append("%')");
                    query.append(" or customer_mixture_number like '%").append(inputBean.getSearchText()).append("%')");
                }else {
                    query.append(" and ").append(inputBean.getSearchField()).append(" like '%").append(inputBean.getSearchText()).append("%'");
                }
            }else if ("starts with".equalsIgnoreCase(inputBean.getMatchType())) {
                if ("customer_msds_or_mixture_no".equals(inputBean.getSearchField())) {
                    query.append(" and ( (customer_msds_number like '").append(inputBean.getSearchText()).append("%')");
                    query.append(" or customer_mixture_number like '").append(inputBean.getSearchText()).append("%')");
                }else {
                    query.append(" and ").append(inputBean.getSearchField()).append(" = '").append(inputBean.getSearchText()).append("%'");
                }
            }else if ("end with".equalsIgnoreCase(inputBean.getMatchType())) {
                if ("customer_msds_or_mixture_no".equals(inputBean.getSearchField())) {
                    query.append(" and ( (customer_msds_number like '%").append(inputBean.getSearchText()).append("')");
                    query.append(" or customer_mixture_number like '%").append(inputBean.getSearchText()).append("')");
                }else {
                    query.append(" and ").append(inputBean.getSearchField()).append(" = '%").append(inputBean.getSearchText()).append("'");
                }
            }else if ("in csv list".equalsIgnoreCase(inputBean.getMatchType())) {
                //if number field
                if ("material_id".equals(inputBean.getSearchField())) {
                    query.append(" and ").append(inputBean.getSearchField()).append(" in (").append(inputBean.getSearchText()).append(")");
                }else {
                    //string fields
                    String[] tmpString = inputBean.getSearchText().split(",");
                    StringBuilder inList = new StringBuilder("");
                    for (int i = 0; i < tmpString.length; i++) {
                        if (i == 0) {
                            inList.append(SqlHandler.delimitString(tmpString[i]));
                        }else {
                            inList.append(",").append(SqlHandler.delimitString(tmpString[i]));
                        }
                    }
                    if ("customer_msds_or_mixture_no".equals(inputBean.getSearchField())) {
                        query.append(" and ( (customer_msds_number  in (").append(inList.toString()).append("))");
                        query.append(" or customer_mixture_number  in (").append(inList.toString()).append("))");
                    }else {
                        query.append(" and ").append(inputBean.getSearchField()).append(" in (").append(inList.toString()).append(")");
                    }
                }
            }
        }
        if(!StringHandler.isBlankString(inputBean.getVocetStatus()))
			query.append(" and vocet_status = '").append(inputBean.getVocetStatus()).append("'");

		if(!StringHandler.isBlankString(inputBean.getVocetCategoryId()))
			query.append(" and vocet_category_id = '").append(inputBean.getVocetCategoryId()).append("'");
        if(inputBean.getUpdatedBy() != null) {
            query.append(" and updated_by = ").append(inputBean.getUpdatedBy());
        }

        query.append(" order by customer_mixture_number,customer_msds_number,material_id");
		return factory.selectQuery(query.toString());
	}
	
	public String doMaterialIdSearchLogic(String search) {
        Hashtable r = StringHandler.getLogicalHashFromString(search);
		Vector opers = (Vector) r.get("OPERATORS");
		Vector likes = (Vector) r.get("LIKES");
		String result = "";
		//no operator
		if (opers.size() < 1) {
			result = " (material_id like '%"+search+"%')";
            return result;
		}

		//contains operation in search text
		result += " ( (material_id like '%" + likes.elementAt(0).toString().trim() + "%') ";
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
			if (butNot) {
				result += " " + op + " (material_id "+lk+" '%"+searchS+"%')";
            }else {
				result += " " + op + " (material_id "+lk+" '%"+searchS+"%')";
            }
		}
		result += " ) ";

		return result;
	}
	
	public String doMsdsSearchLogic(String search) {
        Hashtable r = StringHandler.getLogicalHashFromString(search);
		Vector opers = (Vector) r.get("OPERATORS");
		Vector likes = (Vector) r.get("LIKES");
		String result = "";
		//no operator
		if (opers.size() < 1) {
			result = " (mixture_id like '%"+search+"%'"+
                     " or lower(customer_msds_number) like lower('%" + search + "%'))";
            return result;
		}

		//contains operation in search text
		result += " ( (mixture_id like '%" + likes.elementAt(0).toString().trim() + "%'"+
                  " or lower(customer_msds_number) like lower('%" + likes.elementAt(0).toString().trim() + "%')) ";
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
			if (butNot) {
				result += " " + op + " (mixture_id "+lk+" '%"+searchS+"%'"+
                          " and lower(customer_msds_number) " + lk + " lower('%" + searchS + "%')) ";
            }else {
				result += " " + op + " (mixture_id "+lk+" '%"+searchS+"%'"+
                          " or lower(customer_msds_number) " + lk + " lower('%" + searchS + "%')) ";
            }
		}
		result += " ) ";

		return result;
	}
	
	public String doTradeNameSearchLogic(String search) {
        Hashtable r = StringHandler.getLogicalHashFromString(search);
		Vector opers = (Vector) r.get("OPERATORS");
		Vector likes = (Vector) r.get("LIKES");
		String result = "";
		//no operator
		if (opers.size() < 1) {
			result = " (lower(mixture_desc_or_trade_name) like lower('%" + search + "%'))";
            return result;
		}

		//contains operation in search text
		result += " (lower(mixture_desc_or_trade_name) like lower('%" + likes.elementAt(0).toString().trim() + "%') ";
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
			if (butNot) {
				result += " " + op + " lower(mixture_desc_or_trade_name) " + lk + " lower('%" + searchS + "%') ";
            }else {
				result += " " + op + " lower(mixture_desc_or_trade_name) " + lk + " lower('%" + searchS + "%')) ";
            }
		}
		result += " ) ";

		return result;
	}


	public String update(Collection<VocetMsdsSearchViewBean> inputLines, VocetMsdsSearchViewBean input, PersonnelBean personnelBean) throws BaseException {
		String errorMsg = null;
		Connection connection = dbManager.getConnection();
		Collection inArgs = null;
		try {
			for (VocetMsdsSearchViewBean inputBean : inputLines) {
				if("Y".equals(inputBean.getChanged())) {

					inArgs = new Vector(6);
					inArgs.add(inputBean.getCompanyId());
					inArgs.add(inputBean.getCustomerMsdsDb());
					inArgs.add(inputBean.getCustomerMsdsOrMixtureNo());
					inArgs.add(inputBean.getVocetStatus());
					inArgs.add(inputBean.getVocetCategoryId());
					inArgs.add(personnelBean.getPersonnelId());
                    inArgs.add(input.getFacilityId());

                    Vector outArgs = new Vector();

					Vector error = (Vector) factory.doProcedure(connection,"pkg_vocet_msds.p_upsert", inArgs, outArgs);

				}
			}
		}
		catch (Exception e) {
			errorMsg = "Error upsert msds ";
		}
		finally {
			dbManager.returnConnection(connection);
		}

		return errorMsg;
	}
	
	public ExcelHandler getExcelReport(Collection<VocetMsdsSearchViewBean> data, Locale locale) throws NoDataException, BaseException, Exception {

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
		//write column headers
		pw.addRow();
		/*Pass the header keys for the Excel.*/
		String[] headerkeys = {"label.kitmsds", "report.label.kitDescription", "label.msds", "label.status", "label.category", "label.materialid", "label.tradename", "label.lastUpdatedBy", "label.laastUpdatedDate"};
		/*This array defines the type of the excel column.
		0 means default depending on the data type.
		pw.TYPE_PARAGRAPH defaults to 40 characters.
		pw.TYPE_CALENDAR set up the date with no time.
		pw.TYPE_DATE set up the date with time.*/
		int[] types = { 0, pw.TYPE_PARAGRAPH, 0, 0, 0, 0, pw.TYPE_PARAGRAPH, 0, pw.TYPE_CALENDAR};

		/*This array defines the default width of the column when the Excel file opens.
		0 means the width will be default depending on the data type.*/
		int[] widths = { 8, 0, 8, 8, 0, 0, 0, 0, 0};
		/*This array defines the horizontal alignment of the data in a cell.
		0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = { 0, 0, 0, 0, 0, 0, 0, 0, 0};

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		for (VocetMsdsSearchViewBean member : data) {

			pw.addRow();
            pw.addCell(member.getCustomerMixtureNumber());
            pw.addCell(member.getMixtureDesc());
            pw.addCell(member.getCustomerMsdsNumber());
			pw.addCell(member.getVocetStatus());
			pw.addCell(member.getVocetCategoryId());
            pw.addCell(member.getMaterialId());
			pw.addCell(member.getTradeName());
			pw.addCell(member.getUpdatedByName());
			pw.addCell(member.getUpdatedOn());
		}
		return pw;
	}

}