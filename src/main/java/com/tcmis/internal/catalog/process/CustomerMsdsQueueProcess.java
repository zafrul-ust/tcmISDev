package com.tcmis.internal.catalog.process;

import java.util.Collection;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.CompanyBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.catalog.beans.CatalogAddQcInputBean;
import com.tcmis.internal.catalog.beans.CustomerIndexingMsdsQViewBean;
import com.tcmis.internal.catalog.beans.CustomerMsdsQueueInputBean;
/******************************************************************************
 * Process for CustomerMsdsQueueProcess
 * @version 1.0
 *****************************************************************************/
public class CustomerMsdsQueueProcess extends GenericProcess {

  Log log = LogFactory.getLog(this.getClass());

  public CustomerMsdsQueueProcess(String client) {
    super(client);
  }
  
  public CustomerMsdsQueueProcess(String client,String locale) {
	    super(client,locale);
 }


  public Collection getsearchResult(CustomerMsdsQueueInputBean bean) throws BaseException {
	  String fromView = "custom_indexing_msds_q_view";
      factory.setBeanObject(new CustomerIndexingMsdsQViewBean());
	  
	  SearchCriteria searchCriteria = new SearchCriteria();
	  if(!StringHandler.isBlankString(bean.getCompanyId()))
		  searchCriteria.addCriterion("companyId",SearchCriterion.EQUALS,bean.getCompanyId());
	  
	  searchCriteria.addCriterionWithMode(bean.getSearchField(), bean.getSearchMode(), bean.getSearchArgument());
	  
	  SortCriteria sortCriteria = new SortCriteria();
	  sortCriteria.addCriterion("companyName");
	  sortCriteria.addCriterion("companyId");
	  sortCriteria.addCriterion("materialId");
      sortCriteria.addCriterion("revisionDate","desc");

      if (bean.isGlobalIndexingStatus()) {
        fromView = "msds_indexing_q_view";
        searchCriteria.addCriterion("idOnly",SearchCriterion.EQUALS,"N");   
      }else if (bean.isGlobalQcStatus()) {
        fromView = "msds_qc_q_view";
        addAssignedToSearchCriterion(bean, searchCriteria);
      }else if (bean.isCustomerIndexingStatus()) {
        fromView = "custom_indexing_msds_q_view";
      }else if (bean.isCustomerQcStatus()) {
        fromView = "company_msds_qc_q_view";
        addAssignedToSearchCriterion(bean, searchCriteria);
      }else if (bean.isMaxcomIndexingStatus()) {
        fromView = "msds_indexing_q_view";
        searchCriteria.addCriterion("idOnly",SearchCriterion.EQUALS,"Y");
      }

      return factory.select(searchCriteria, sortCriteria,fromView);
  }
  
  private void addAssignedToSearchCriterion(CustomerMsdsQueueInputBean bean, SearchCriteria criteria) {
	  if ( ! bean.isAssignedToAll()) {
      	if (bean.isAssignedToUnassigned()) {
      		criteria.addCriterion("assignedTo", SearchCriterion.IS, "NULL");
      	}
      	else {
      		criteria.addCriterion("assignedTo", SearchCriterion.EQUALS, bean.getAssignedTo().toString());
      	}
      }
  }
  
  @SuppressWarnings("rawtypes")
	public Collection getCatalogUsers(CustomerMsdsQueueInputBean input) throws BaseException {
		StringBuilder query = new StringBuilder("select personnel_id, fx_personnel_id_to_name(personnel_id) personnel_name, company_id");
		query.append(" from customer.user_company where status = 'A'");
        query.append(" and msds_qc_role = 'Y'");
      if ( ! input.isMyCompanies()) {
          query.append(" and company_id = ").append(SqlHandler.delimitString(input.getCompanyId()));
      }
      query.append(" order by company_id, 2");
	  return getSearchResult(query.toString(),new CustomerMsdsQueueInputBean());
	}
  
  public void assignUser(CustomerMsdsQueueInputBean input, Collection<CustomerIndexingMsdsQViewBean> sdsColl) throws BaseException {
	  for (CustomerIndexingMsdsQViewBean bean : sdsColl) {
		  if (bean.isAssigneeUpdated()) {
			  String table = "";
			  if (input.isGlobalQcStatus()) {
				  table = "msds_qc";
			  }
			  else if (input.isCustomerQcStatus()) {
				  table = "company_msds_qc";
			  }
			  if ( ! table.isEmpty()) {
				  String stmt = "update "+table+" set assigned_to = "+bean.getAssignedTo() +
						  " where material_id = "+bean.getMaterialId() + 
						  " and revision_date = "+DateHandler.getOracleToDateFunction(bean.getRevisionDate());
				  factory.deleteInsertUpdate(stmt);
			  }
		  }
	  }
  }

  public ExcelHandler  getExcelReport(CustomerMsdsQueueInputBean bean, Locale locale) throws NoDataException, BaseException {
	  ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		Collection<CustomerIndexingMsdsQViewBean> data = getsearchResult(bean);
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
		//write column headers
		pw.addRow();
		/*Pass the header keys for the Excel.*/
		String[] headerkeys = {
				"label.company","label.material","label.description","label.tradename","label.manufacturer",
				"label.companyrevisiondate","label.revisiondate","label.globaldataentrycompelete","label.comments"};
		/*This array defines the type of the excel column.
 			0 means default depending on the data type. */
		int[] types = {
				0,0,pw.TYPE_PARAGRAPH,0,0,pw.TYPE_CALENDAR,pw.TYPE_CALENDAR, 0, pw.TYPE_PARAGRAPH};
		/*This array defines the default width of the column when the Excel file opens.
 			0 means the width will be default depending on the data type.*/
		int[] widths = {
				15,10,25,25,25,0,0,0,0};
		/*This array defines the horizontal alignment of the data in a cell.
 			0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = {
				0,0,0,0,0,0,0,0,0};

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		// now write data
		for (CustomerIndexingMsdsQViewBean member : data) {
			pw.addRow();

			pw.addCell(member.getCompanyName());
			pw.addCell(member.getMaterialId());
			pw.addCell(member.getMaterialDesc());
			pw.addCell(member.getTradeName());
			pw.addCell(member.getMfgDesc());
			pw.addCell(member.getCurrentCustRevisionDate());
			pw.addCell(member.getRevisionDate());
			pw.addCell(member.getGlobalDec());
			pw.addCell(member.getComments());
		}
		return pw;
    } //end of method
  
  
    public Collection getMyCompanies(int personnelId) throws BaseException,Exception {
        factory.setBeanObject(new CompanyBean());
        StringBuilder query = new StringBuilder("select uc.personnel_id, uc.company_id, c.company_name");
        query.append(" from customer.user_company uc, customer.company c where uc.personnel_id = ").append(personnelId);
        query.append(" and uc.company_id = c.company_id and c.company_msds = 'Y'");
        query.append(" order by lower(c.company_name)");
        return factory.selectQuery(query.toString());
    } //end of method
  
} //end of class

