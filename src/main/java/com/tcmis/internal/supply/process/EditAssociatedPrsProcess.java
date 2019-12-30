package com.tcmis.internal.supply.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Vector;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.factory.CompanyBeanFactory;
import com.tcmis.common.admin.factory.PersonnelBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.supply.beans.AssociatePrViewBean;
import com.tcmis.internal.supply.beans.EditAssociatedPrsInputBean;
import com.tcmis.internal.supply.factory.AssociatePrViewBeanFactory;

import radian.tcmis.common.util.SqlHandler;

public class EditAssociatedPrsProcess extends BaseProcess {

	public EditAssociatedPrsProcess(String client, String locale) {
		super(client, locale);
	}


	public Collection getSearchResult(EditAssociatedPrsInputBean bean, PersonnelBean pbean, String action)
			throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		AssociatePrViewBeanFactory factory = new AssociatePrViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();

		if (bean.getInventoryGroup() != null && !bean.getInventoryGroup().equalsIgnoreCase("ALL")) {
			criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, bean.getInventoryGroup());
		}
		
		if(bean.getItemId() != null )
			criteria.addCriterion("itemId", SearchCriterion.EQUALS, bean.getItemId().toString());
		
		String statusExcludes = "'Closed','Cancel'";
		criteria.addCriterion("status", SearchCriterion.NOT_IN, statusExcludes);
				
		if (bean.getBuyerId() != null && bean.getBuyerId().compareTo(BigDecimal.ZERO) > 0 ) {
			SearchCriteria subCriteria1 = new SearchCriteria();
			subCriteria1.addCriterion("buyerId", SearchCriterion.IS, null);
						
			SearchCriteria subCriteria2 = new SearchCriteria();
			subCriteria2.addCriterion("buyerId", SearchCriterion.EQUALS, pbean.getPersonnelIdBigDecimal() + "");
						
			SearchCriteria subCriteria3 = new SearchCriteria();
			subCriteria3.addCriterion("buyerId", SearchCriterion.EQUALS, bean.getBuyerId() + "");
			
			criteria.addSubCriteria(subCriteria1,subCriteria2,subCriteria3 );
		}
		
		String supplyPathIncludes = "'Purchaser','Dbuy','Wbuy','Ibuy'";
		if (bean.getSupplyPath() != null
				&& !bean.getSupplyPath().equalsIgnoreCase("ALL")) {
			criteria.addCriterion("supplyPath", SearchCriterion.EQUALS,
					bean.getSupplyPath());
		} else {
			criteria.addCriterion("supplyPath", SearchCriterion.IN, supplyPathIncludes);
		}
		
		if (action.equalsIgnoreCase("addBuyOrdersToPO")) {
			criteria.addCriterion("radianPo", SearchCriterion.IS, null);
		} else {			
	    	SearchCriteria subCriteria1 = new SearchCriteria();
	    	subCriteria1.addCriterion("radianPo", SearchCriterion.IS, null);
	    	
	    	SearchCriteria subCriteria2 = new SearchCriteria();
	    	subCriteria2.addCriterion("radianPo",  SearchCriterion.EQUALS , StringHandler.emptyIfNull(bean.getRadianPo()));
	    	criteria.addSubCriteria(subCriteria1,subCriteria2);			
		}
	     
	    if (bean.getMrNumber() != null && bean.getMrNumber().compareTo(BigDecimal.ZERO) > 0) {
			criteria.addCriterion("mrNumber",  SearchCriterion.EQUALS , bean.getMrNumber() + "");
		}
	    
	    if (bean.getRaytheonPo() != null && !bean.getRaytheonPo().equals("")) {
			criteria.addCriterion("raytheonPo",  SearchCriterion.EQUALS , bean.getRaytheonPo());
		}
	    
	    if (bean.getCompanyId() != null && !bean.getCompanyId().equalsIgnoreCase("All")) {
			criteria.addCriterion("companyId",  SearchCriterion.EQUALS , bean.getCompanyId());
		}
	    
	    if (bean.getShipToLocationId() != null && !bean.getShipToLocationId().equals("")) {
			criteria.addCriterion("shipToLocationId",  SearchCriterion.LIKE , bean.getShipToLocationId());
		}
	    
		// add description search if it's not null
		if (bean.getSearchWhat() != null && bean.getSearchText() != null
				&& bean.getSearchText().trim().length() > 0) {
			// check search criterion
			String criterion = null;
			if (bean.getSearchType() != null
					&& bean.getSearchType().equalsIgnoreCase("IS")) {
				criterion = SearchCriterion.EQUALS;
			} else if (bean.getSearchType() != null
					&& bean.getSearchType().equalsIgnoreCase("CONTAINS")) {
				criterion = SearchCriterion.LIKE;
			} else if (bean.getSearchType() != null
					&& bean.getSearchType().equalsIgnoreCase("IN")) {
				criterion = SearchCriterion.IN;
			}

			// check what to search by
			if (bean.getSearchWhat() != null
					&& bean.getSearchWhat().equalsIgnoreCase("MFG_ID")) {
				criteria.addCriterion("mfgId", criterion, bean.getSearchText(),
						SearchCriterion.IGNORE_CASE);
			} else if (bean.getSearchWhat() != null
					&& bean.getSearchWhat().equalsIgnoreCase("PART_ID")) {
				criteria.addCriterion("partId", criterion,
						bean.getSearchText(), SearchCriterion.IGNORE_CASE);
			} else if (bean.getSearchWhat() != null
					&& bean.getSearchWhat().equalsIgnoreCase("ITEM_TYPE")) {
				criteria.addCriterion("itemType", criterion,
						bean.getSearchText());
			} else if (bean.getSearchWhat() != null
					&& bean.getSearchWhat().equalsIgnoreCase("PR_NUMBER")) {
				criteria.addCriterion("prNumber", criterion,
						bean.getSearchText());
			}
		}

		SortCriteria sortCriteria = null;
		if (bean.getSortBy() != null) {
			sortCriteria = new SortCriteria();
			sortCriteria.addCriterion(bean.getSortBy());
		}
		
		Collection associatePrViewBeanCollection = new Vector();
		associatePrViewBeanCollection = factory.select(criteria, sortCriteria);

		return associatePrViewBeanCollection;
	}
	
	//return whether the radianPo in question has been fully disassociated or not
	public boolean UpdateEditPrs(Collection<AssociatePrViewBean> pageGridBeans, EditAssociatedPrsInputBean bean, PersonnelBean pBean, Collection<String> buyOrderStatusToLockColl) throws BaseException, Exception {		
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(new DbManager(getClient(), getLocale()));
		StringBuffer sbUpdateQuery;
		
		boolean atLeastOneDisassociation = false;
		int okCount = 0;
		for (AssociatePrViewBean buyPageBean : pageGridBeans) {
			//counting number of prs still associated with the current radianPo
			okCount = "true".equalsIgnoreCase(buyPageBean.getOk()) ? okCount + 1 : okCount;
			if (!buyOrderStatusToLockColl.contains(buyPageBean.getStatus())) {
				if ("Y".equalsIgnoreCase(buyPageBean.getChanged())) {
					if ("true".equalsIgnoreCase(buyPageBean.getOk())) {
						  sbUpdateQuery = new StringBuffer();
						  sbUpdateQuery.append(" Update buy_order set BUYER_COMPANY_ID = "+ SqlHandler.delimitString(buyPageBean.getBuyerCompanyId()));
						  sbUpdateQuery.append(" ,RADIAN_PO = " +  bean.getRadianPo());
						  sbUpdateQuery.append(" ,BUYER_ID = " + pBean.getPersonnelId());
						  sbUpdateQuery.append(" ,DATE_CHANGED=sysdate where item_id =" + bean.getItemId());
						  sbUpdateQuery.append(" and pr_number=" + buyPageBean.getPrNumber());
						  sbUpdateQuery.append(" and frozen = 'N' ");
						  genericSqlFactory.deleteInsertUpdate(sbUpdateQuery.toString());
						  
						  sbUpdateQuery = new StringBuffer();
						  sbUpdateQuery.append(" update po set BRANCH_PLANT = "+ SqlHandler.delimitString(buyPageBean.getBranchPlant()));
						  sbUpdateQuery.append(" where RADIAN_PO = " + bean.getRadianPo());
						  sbUpdateQuery.append(" and BRANCH_PLANT is null");
						  genericSqlFactory.deleteInsertUpdate(sbUpdateQuery.toString());
					} else {
						atLeastOneDisassociation = true;
						sbUpdateQuery = new StringBuffer();
						sbUpdateQuery.append(" Update buy_order set SUPPLY_PATH = 'Purchaser', ");
						sbUpdateQuery.append(" BUYER_COMPANY_ID = " + SqlHandler.delimitString(buyPageBean.getBuyerCompanyId()));
						sbUpdateQuery.append(" ,RADIAN_PO=null, PO_DETACH_BY=" + pBean.getPersonnelId());
						sbUpdateQuery.append(" ,STATUS='In Progress', PO_IN_JDE='n', DATE_CHANGED=sysdate ");
						sbUpdateQuery.append(" where item_id =" + bean.getItemId());
						sbUpdateQuery.append(" and radian_po = " + buyPageBean.getRadianPo());
						sbUpdateQuery.append(" and pr_number=" + buyPageBean.getPrNumber());
						sbUpdateQuery.append(" and frozen = 'N'");
						genericSqlFactory.deleteInsertUpdate(sbUpdateQuery.toString());
					}
				}
			}
		}
		
		//if there's no prs associated with the current radianPo and disassociation action has been carried out, raise the flag that radianPo has been fully disassociated
		if (okCount == 0 && atLeastOneDisassociation)
			return true;
		else
			return false;
	}
	
	public Collection getBuyerDropDown() throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		PersonnelBeanFactory factory = new PersonnelBeanFactory(dbManager);
		return factory.selectBuyOrdersBuyer();
	}
	
	public Collection getCompanyDropDown() throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		CompanyBeanFactory factory = new CompanyBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria("customer", SearchCriterion.EQUALS, "Y");
		criteria.addCriterion("companyId", SearchCriterion.NOT_EQUAL, "ALL");
		return factory.selectCustomer(criteria);
	}


	public boolean UpdateAddPrsToPo (
			Collection<AssociatePrViewBean> pageGridBeans, EditAssociatedPrsInputBean bean,
			PersonnelBean personnelBean) throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());		
	    Connection connection = dbManager.getConnection();
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager, new EditAssociatedPrsInputBean());		
		boolean result = false;
		boolean isChanged = false;
		StringBuffer sbUpdateQuery = null;
		int totalLines = 0;
		String prNumberList = "";
		try {
			for (AssociatePrViewBean buyPageBean : pageGridBeans){			
				if (buyPageBean.getChanged() != null && buyPageBean.getChanged().equalsIgnoreCase("Y")) {					
					if (buyPageBean.getOk() != null && buyPageBean.getOk().equalsIgnoreCase("true")) {
						isChanged = true;	
					}
				}
				
				if(isChanged) {					
					sbUpdateQuery = new StringBuffer();
					sbUpdateQuery.append(" Update buy_order set ");
					sbUpdateQuery.append(" BUYER_COMPANY_ID = "+ SqlHandler.delimitString(personnelBean.getCompanyId()));
					sbUpdateQuery.append(" , RADIAN_PO = " + bean.getRadianPo());
					sbUpdateQuery.append(" , BUYER_ID = " + personnelBean.getPersonnelId());
					sbUpdateQuery.append(" , DATE_CHANGED = sysdate ");
					sbUpdateQuery.append(" where item_id =" + buyPageBean.getItemId());
					sbUpdateQuery.append(" and pr_number=" + buyPageBean.getPrNumber());
					sbUpdateQuery.append(" and frozen = 'N'");
					//unassociatedprs = true;
					int iUpdated = genericSqlFactory.deleteInsertUpdate(sbUpdateQuery.toString());
					if (iUpdated > 0 ) {
						sbUpdateQuery = new StringBuffer();
						sbUpdateQuery.append(" update po set BRANCH_PLANT = "+ SqlHandler.delimitString(buyPageBean.getBranchPlant()));
						sbUpdateQuery.append(" where RADIAN_PO = " + bean.getRadianPo());
						sbUpdateQuery.append(" and BRANCH_PLANT is null");
						genericSqlFactory.deleteInsertUpdate(sbUpdateQuery.toString());
						
						totalLines = totalLines + iUpdated;	
						if (totalLines > 1) {
		                    prNumberList += ",";
		                }
		                prNumberList += buyPageBean.getPrNumber() + "";
					}
					isChanged = false;
				}
			}
			//call P_ADD_BUY_ORDERS_TO_PO
	        if (totalLines > 0)
	        {
	        	result = addLineToPo(prNumberList,bean,personnelBean);
	        }
		} catch (Exception e) {
			e.printStackTrace();	
			result = false;
			return result;
	   }
	   finally {
			if (connection != null) {
				dbManager.returnConnection(connection);
			}
	   }
	   return result;
	}
	
	public boolean addLineToPo(String prNumberList,EditAssociatedPrsInputBean bean,
			PersonnelBean personnelBean ) throws BaseException, Exception
	{
		DbManager dbManager = new DbManager(getClient(), getLocale());		
	    Connection connection = dbManager.getConnection();
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
		boolean result = false;
	    String errorCode = "";
	    
		try
        {
			Vector inArgs = new Vector();			
			inArgs.add(bean.getRadianPo());
			inArgs.add(prNumberList);
			inArgs.add(personnelBean.getPersonnelId());
			inArgs.add(bean.getCurrencyId());
									
			Vector outArgs = new Vector();			
		    outArgs.add(new Integer(java.sql.Types.VARCHAR));

		    //invoke procedure to compare the excel data
			Vector procErrorMessage = (Vector) genericSqlFactory.doProcedure(connection, "Pkg_buy_order.P_ADD_BUY_ORDERS_TO_PO", inArgs, outArgs);
			
			log.info("calling Procedure Pkg_buy_order.P_ADD_BUY_ORDERS_TO_PO: Radian PO = " + bean.getRadianPo() + " prNumberList = " + prNumberList );
			
			if ( procErrorMessage.get(0) != null && !"ok".equalsIgnoreCase((String)procErrorMessage.get(0))) {
				result = false;
				errorCode = (String) procErrorMessage.get(0);
				log.info( "Error from Pkg_buy_order.P_ADD_BUY_ORDERS_TO_PO - Radian Po " + bean.getRadianPo() + " and prNumberList " + prNumberList + ": " + errorCode);
			} else {
		    	result = true;
		    }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }
	
	
}
