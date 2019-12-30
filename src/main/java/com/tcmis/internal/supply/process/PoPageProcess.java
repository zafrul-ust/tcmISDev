package com.tcmis.internal.supply.process;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.supply.beans.InternalAssociatedPrsBean;
import com.tcmis.internal.supply.beans.PoHeaderViewBean;
import com.tcmis.internal.supply.beans.PoViewBean;
import com.tcmis.internal.supply.beans.PurchaseOrderBean;
import com.tcmis.internal.supply.beans.VvItemComponentViewBean;
import com.tcmis.internal.supply.factory.ClientBuyHistoryViewBeanFactory;
import com.tcmis.internal.supply.factory.PoHeaderViewBeanFactory;
import com.tcmis.supplier.wbuy.beans.PoLineDetailViewBean;
import com.tcmis.supplier.wbuy.factory.InternalFlowdownsBeanFactory;
import com.tcmis.supplier.wbuy.factory.InternalSpecsBeanFactory;
import com.tcmis.supplier.wbuy.factory.PoLineDetailViewBeanFactory;

import oracle.jdbc.OracleTypes;
import radian.tcmis.common.util.SqlHandler;

public class PoPageProcess   extends GenericProcess {
	 Log log = LogFactory.getLog(this.getClass());

	 public PoPageProcess(String client) {
		super(client);
	 }

	
	public Collection getAllLineData(PoHeaderViewBean poHeaderBean, Collection lineBeans, PersonnelBean personnelBean) throws BaseException 
	{
		Collection specBeans = new Vector();
		Collection flowdownBeans = new Vector();
		String specSQL = null;
		String flowdownSQL = null;
		DbManager dbManager = new DbManager(this.getClient());

		InternalSpecsBeanFactory specsFactory = new InternalSpecsBeanFactory(dbManager);
		InternalFlowdownsBeanFactory flowdownsFactory = new InternalFlowdownsBeanFactory(dbManager);

		if (poHeaderBean != null && lineBeans != null) {
			Iterator iter = lineBeans.iterator();
			while (iter.hasNext()) {
				PoLineDetailViewBean lineBean = (PoLineDetailViewBean) iter.next();
				// specs
				specSQL = getSpecSQL(poHeaderBean, lineBean);
				// flowdowns
				flowdownSQL = getFlowdownSQL(poHeaderBean, lineBean);
				
				if (log.isDebugEnabled()) {
					log.debug("getting specs view");
				}
				if (specSQL != null) {
					specSQL = "SELECT distinct spec_id,spec_library,detail,saved_coc,saved_coa, spec_id_display FROM ( " + specSQL + " ) WHERE (saved_coc = 'Y' or saved_coa = 'Y')";
					try {
						specBeans = specsFactory.select(specSQL);
						lineBean.setSpecs(specBeans);
					}
					catch (BaseException e) {
						log.error("Exception getting data for specs: " + e);
					}
				}
				log.debug("gettting flowdowns");
				if (flowdownSQL != null) {
					flowdownSQL = "SELECT distinct company_id, catalog_id, flow_down, flow_down_desc, revision_date, flow_down_type FROM ( " + flowdownSQL + " ) WHERE OK = 'Y'";
					// System.out.println("flowdown sql: " + flowdownSQL);
					try {
						flowdownBeans = flowdownsFactory.select(flowdownSQL);
						lineBean.setFlowdowns(flowdownBeans);
					}
					catch (BaseException e) {
						log.error("Exception getting data for flowdown: " + e);
					}
				}
			}
		}

		return lineBeans;
	}

	public Collection getSinglePurchaseOrderHeader(PurchaseOrderBean input) throws BaseException 
	{
		DbManager dbManager = new DbManager(getClient());
		PoHeaderViewBeanFactory factory = new PoHeaderViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("radianPo", SearchCriterion.EQUALS, input.getRadianPo().toPlainString(),
		SearchCriterion.IGNORE_CASE);
		
		return factory.select(criteria);
	}
	 
	 private String getSpecSQL(PoHeaderViewBean header, PoLineDetailViewBean line) throws BaseException 
	 {
		String resultQuery = null;
		Connection connect1 = null;
		CallableStatement cs = null;
		DbManager dbManager = new DbManager(this.getClient());

		connect1 = dbManager.getConnection();
		try {
			cs = connect1.prepareCall("{call pkg_po.Po_spec(?,?,?,?,?,?,?,?)}");

			String shipToLoc = header.getShipToLocationId();
			String shiptocompanyid = header.getShipToCompanyId();

			String invengrp = header.getInventoryGroup();
			BigDecimal itemID = line.getItemId();
			BigDecimal radianpo = header.getRadianPo();
			BigDecimal radianbpo = header.getBo();
			BigDecimal lineID = line.getPoLine();
			BigDecimal amendment = line.getAmendment();

			cs.setString(1, shipToLoc); // Ship To Loc
			if (shiptocompanyid.length() > 0) { // Ship To Company ID
				cs.setString(2, shiptocompanyid);
			}
			else {
				cs.setNull(2, OracleTypes.VARCHAR);
			}
			if (itemID.longValue() > 0) { // Item ID
				cs.setBigDecimal(3, itemID);
			}
			else {
				cs.setNull(3, OracleTypes.INTEGER);
			}
			if (radianpo.intValue() > 0) { // Radian PO
				cs.setBigDecimal(4, radianpo);
			}
			else if (radianbpo.intValue() > 0) {
				cs.setBigDecimal(4, radianbpo);
			}
			else {
				cs.setNull(4, OracleTypes.INTEGER);
			}
			if (lineID.longValue() > 0) { // Line ID
				cs.setBigDecimal(5, lineID);
			}
			else {
				cs.setNull(5, OracleTypes.INTEGER);
			}
			if (amendment.longValue() >= 0) { // amendment
				cs.setBigDecimal(6, amendment);
			}
			else {
				cs.setNull(6, OracleTypes.INTEGER);
			}
			if (invengrp != null && invengrp.length() > 0) { // Inventory Group
				cs.setString(7, invengrp);
			}
			else {
				cs.setNull(7, OracleTypes.VARCHAR);
			}

			cs.registerOutParameter(8, OracleTypes.VARCHAR);
			cs.execute();

			Object o = cs.getObject(8);
			if (o != null)
				resultQuery = o.toString();
		}
		catch (SQLException sqle) {
			log.error("SQL Exception in getSpecSQL: " + sqle);
		}
		finally {
			dbManager.returnConnection(connect1);
		}
		return resultQuery;
	}
	 
	 private String getFlowdownSQL(PoHeaderViewBean header, PoLineDetailViewBean line) throws BaseException 
	 {
		String resultQuery = null;
		Connection connect1 = null;
		CallableStatement cs = null;
		DbManager dbManager = new DbManager(this.getClient());

		connect1 = dbManager.getConnection();
		try {
			cs = connect1.prepareCall("{call pkg_po.Po_flowdown(?,?,?,?,?,?,?)}");

			String shipToLoc = header.getShipToLocationId();
			String shiptocompanyid = header.getShipToCompanyId();

			BigDecimal itemID = line.getItemId();
			BigDecimal radianpo = header.getRadianPo();
			BigDecimal radianbpo = header.getBo();
			BigDecimal lineID = line.getPoLine();
			BigDecimal amendment = line.getAmendment();

			cs.setString(1, shipToLoc); // Ship To Loc
			if (shiptocompanyid.length() > 0) { // Ship To Company ID
				cs.setString(2, shiptocompanyid);
			}
			else {
				cs.setNull(2, OracleTypes.VARCHAR);
			}
			if (itemID.longValue() > 0) { // Item ID
				cs.setBigDecimal(3, itemID);
			}
			else {
				cs.setNull(3, OracleTypes.INTEGER);
			}
			if (radianpo.intValue() > 0) { // Radian PO
				cs.setBigDecimal(4, radianpo);
			}
			else if (radianbpo.intValue() > 0) {
				cs.setBigDecimal(4, radianbpo);
			}
			else {
				cs.setNull(4, OracleTypes.INTEGER);
			}
			if (lineID.longValue() > 0) { // Line ID
				cs.setBigDecimal(5, lineID);
			}
			else {
				cs.setNull(5, OracleTypes.INTEGER);
			}
			if (amendment.longValue() >= 0) { // amendment
				cs.setBigDecimal(6, amendment);
			}
			else {
				cs.setNull(6, OracleTypes.INTEGER);
			}

			cs.registerOutParameter(7, OracleTypes.VARCHAR);
			cs.execute();

			Object o = cs.getObject(7);
			if (o != null)
				resultQuery = o.toString();
		}
		catch (SQLException sqle) {
			log.error("SQL Exception in getFlowdownSQL: " + sqle);
		}
		finally {
			dbManager.returnConnection(connect1);
		}
		return resultQuery;
	}
	 
    public Collection<InternalAssociatedPrsBean> getAssociatedPrCol(PurchaseOrderBean poBean, PersonnelBean personnelBean) throws BaseException 
	{
		String resultQuery = null;
		Connection conn = null;
		CallableStatement cs = null;
		DbManager dbManager = new DbManager(this.getClient());
		conn = dbManager.getConnection();
		
		try {
			Collection inArgs = new Vector(15);
			Collection optArgs = new Vector(2);

			if (poBean.getHub() == null || "All".equalsIgnoreCase(poBean.getHub())) {
				inArgs.add("ALL");
			} else {
				inArgs.add(poBean.getHub());
			}
			if (poBean.getInventoryGroup() == null || "All".equalsIgnoreCase(poBean.getInventoryGroup())) {
				inArgs.add("ALL");
			} else {
				inArgs.add(poBean.getInventoryGroup());
			}
			inArgs.add("ALL"); //FacilityId
			inArgs.add("ALL"); //CompanyId
			inArgs.add("ALL"); //BuyerId
			inArgs.add("RADIAN_PO");
			inArgs.add("IS");
			inArgs.add(poBean.getRadianPo());
			inArgs.add("All Statuses");
			inArgs.add("ALL");
			inArgs.add("BUYER_NAME,item_id");		
			inArgs.add("NO");		
			inArgs.add("NO");		
			inArgs.add("NO");
			inArgs.add("NO");
			
			//optArgs.add(personnelBean.getPersonnelId());
			optArgs.add(null);
			if(poBean.getOpsEntityId() == null || "All".equalsIgnoreCase(poBean.getOpsEntityId())) {
				optArgs.add("");
			}
			else {
				optArgs.add(poBean.getOpsEntityId());
			}

			Collection outArgs = new Vector(1);
			outArgs.add(new Integer(java.sql.Types.VARCHAR));
			GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
			conn.setAutoCommit(false);
			Collection resultCollection = procFactory.doProcedure(conn, "PKG_BUY_ORDER.p_buy_page_query", inArgs, outArgs, optArgs);

			Iterator iterator = resultCollection.iterator();
			while(iterator.hasNext()) {
				resultQuery = (String)iterator.next();
			}
			if(log.isDebugEnabled()) {
				log.debug("QUERY:" + resultQuery);
			}
			
			Collection<InternalAssociatedPrsBean> associatedPrBeans = null;
			
			if (resultQuery != null) {
				try {
					factory.setBean(new InternalAssociatedPrsBean());
					associatedPrBeans = factory.selectQuery(resultQuery, conn);
					return associatedPrBeans;
				}
				catch (BaseException e) {
					log.error("Exception getting data for associated Prs: " + e);
				}
			}
			conn.setAutoCommit(true);
		}
		catch (Exception sqle) {
			log.error("SQL Exception in getFlowdownSQL: " + sqle);
		}
		finally {
			dbManager.returnConnection(conn);
		}
		return null;		
	}
	 
	  public JSONArray buildsendLookahead(PoLineDetailViewBean poLineDetailViewBean) throws BaseException 
	  {
		  JSONArray results = new JSONArray();
		  factory.setBean(new PoLineDetailViewBean());
	 	  
		  Collection<PoLineDetailViewBean> beans = factory.selectQuery("select distinct x.INVENTORY_GROUP,x.PART_GROUP_NO,x.CAT_PART_NO,x.LOOK_AHEAD_DAYS,x.COMPANY_ID,x.CATALOG_ID from customer.catalog_part_inventory x, buy_order y where y.PART_ID= x.CAT_PART_NO and x.COMPANY_ID = y.COMPANY_ID and y.RADIAN_PO=" + poLineDetailViewBean.getRadianPo() + " and y.ITEM_ID=" + poLineDetailViewBean.getItemId() + " ");
		  
		  for (PoLineDetailViewBean bean : beans) {
				results.put(BeanHandler.getJsonObject(bean));
		  }
		  
		  return results;
	  }
	  
	  public JSONArray buildItemNotes(PoLineDetailViewBean poLineDetailViewBean, String user) throws BaseException 
	  {
		  JSONArray results = new JSONArray();
		  factory.setBean(new PoLineDetailViewBean());
	 	  
		  Collection<PoLineDetailViewBean> beans = factory.selectQuery("select ibc.ITEM_ID ,ibc.COMMENTS,ibc.TRANSACTION_DATE, to_char(ibc.TRANSACTION_DATE,'mm/dd/yyyy') TRANS_DATE, p.first_name ||' '|| p.last_name Full_name from item_buyer_comments ibc,personnel p  where item_id = " + poLineDetailViewBean.getItemId() + " and ibc.buyer_id = p.personnel_id and company_id = "+SqlHandler.delimitString(user)+" order by TRANSACTION_DATE desc");
		  
		  for (PoLineDetailViewBean bean : beans) {
				results.put(BeanHandler.getJsonObject(bean));
		  }
		  
		  return results;
	  }
	  
	  public Collection getMaterialLines(String radianpo) throws BaseException {
		  Collection materialBeans = null;
		  DbManager dbManager = new DbManager(this.getClient());
		  PoLineDetailViewBeanFactory poLinesFactory = new PoLineDetailViewBeanFactory(dbManager);
		  try {
			  SearchCriteria searchCriteria = new SearchCriteria();
			  searchCriteria.addCriterion("radianPo", SearchCriterion.EQUALS, radianpo);
			  materialBeans = poLinesFactory.select(searchCriteria);
		  }
		  catch (BaseException be2) {
			  log.error("Base Exception in getMaterialLines: " + be2);
		  }
		  finally {
			  dbManager = null;
			  poLinesFactory = null;
		  }
		  return materialBeans;
	}
		
	public Collection<VvItemComponentViewBean> getMsdsRev(PurchaseOrderBean input) 
		throws BaseException {
		String msdsQuery = "select ITEM_ID,DESCRIPTION,CONTENT,MANUFACTURER,PACKAGING,REVISION_DATE,ON_LINE from item_component_view where item_id = " + input.getItemId() + "";
		GenericSqlFactory factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()), new VvItemComponentViewBean());
		Collection<VvItemComponentViewBean> beans = factory.selectQuery(msdsQuery);
		return beans;
	}
	
	public boolean getMsdsRevCheck(PurchaseOrderBean input) {	
		StringBuffer msdsCheckQuery = new StringBuffer();
		if (!StringHandler.isBlankString(input.getRadianPo().toString())) {
			msdsCheckQuery.append("Select MSDS_REQUESTED_DATE from po_line_detail_view ");
			msdsCheckQuery.append(" where RADIAN_PO = " + input.getRadianPo().toPlainString());
			msdsCheckQuery.append(" and PO_LINE = " + input.getPoLine().toPlainString() );
			msdsCheckQuery.append(" and AMENDMENT=" + input.getAmendment().toPlainString() ) ;
		} 
		boolean msdscheck = false; 		
		GenericSqlFactory factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()));
		try {
			String value =  factory.selectSingleValue(msdsCheckQuery.toString());
			if (StringHandler.isBlankString(value))
				msdscheck = false;				
			else
				msdscheck = true;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return msdscheck;		
	}
	
	public Collection<PoViewBean> getTcmBuysCol (PurchaseOrderBean input) throws BaseException { 
		DbManager dbManager = new DbManager(getClient());		
		SearchCriteria searchCriteria = new SearchCriteria();
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new PoViewBean());  
		
		if(! StringHandler.isBlankString(input.getItemId().toPlainString())) {
		     searchCriteria.addCriterion("itemId", SearchCriterion.EQUALS, input.getItemId().toString() );	
		} 
		if(! StringHandler.isBlankString(input.getSupplier())) {
			 searchCriteria.addCriterion("supplier", SearchCriterion.EQUALS, input.getSupplier() );	
		} 
		if(! StringHandler.isBlankString(input.getOpsEntityId())) {
			 searchCriteria.addCriterion("opsEntityId", SearchCriterion.EQUALS, input.getOpsEntityId() );
		}

		SortCriteria sortCriteria = new SortCriteria();
		sortCriteria.addCriterion( "radianPo" , "desc" );	
		sortCriteria.setSortAscending(false);
		Collection<PoViewBean> c = factory.select(searchCriteria, sortCriteria, "PO_VIEW");
		
		return c;	
	}
	
	public Collection getClientBuysCol (PurchaseOrderBean input) throws BaseException { 
		
		DbManager dbManager = new DbManager(getClient());
		ClientBuyHistoryViewBeanFactory factory = new ClientBuyHistoryViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("itemId", SearchCriterion.EQUALS, input.getItemId().toPlainString(), SearchCriterion.IGNORE_CASE);
		SortCriteria sortCriteria = new SortCriteria();
		sortCriteria.addCriterion( "poDate");
		sortCriteria.setSortAscending(false);
		return factory.select(criteria, sortCriteria);
	}
}
