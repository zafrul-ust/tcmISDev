package com.tcmis.internal.distribution.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Locale;
import java.util.Vector;

import com.tcmis.client.catalog.process.EngEvalProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.MrReleaseInputBean;
import com.tcmis.internal.distribution.beans.SalesQuoteHoldOvBean;
import com.tcmis.internal.distribution.beans.SalesQuoteLineObjBean;
import com.tcmis.internal.distribution.factory.SalesQuoteHoldOvBeanFactory;

public class MrReleaseProcess extends GenericProcess {
	ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());

	public MrReleaseProcess(String client) {
		super(client);
	}

	public MrReleaseProcess(String client, String locale) {
		super(client, locale);
	}

	@SuppressWarnings("unchecked")
	public Collection<SalesQuoteHoldOvBean> getSalesOrderObj(MrReleaseInputBean inputSearchBean, PersonnelBean personnelBean) throws BaseException {

		DbManager dbManager = new DbManager(getClient(), getLocale());
		SalesQuoteHoldOvBeanFactory factory = new SalesQuoteHoldOvBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		//		StringBuffer orderTypeList = new StringBuffer();
		try {
			if (!StringHandler.isBlankString(inputSearchBean.getOpsEntityId()))
				criteria.addCriterion("opsEntityId", SearchCriterion.EQUALS, inputSearchBean.getOpsEntityId());
			if (!StringHandler.isBlankString(inputSearchBean.getHub()))
				criteria.addCriterion("hub", SearchCriterion.EQUALS, inputSearchBean.getHub());
			if (!StringHandler.isBlankString(inputSearchBean.getInventoryGroup())) {
				criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, inputSearchBean.getInventoryGroup());
			} else {
				String invQuery = " select inventory_group from user_inventory_group where personnel_id = " + personnelBean.getPersonnelId();
				if (personnelBean.getCompanyId() != null && personnelBean.getCompanyId().length() != 0)
					invQuery += " and company_id = '" + personnelBean.getCompanyId() + "' ";
				criteria.addCriterion("inventoryGroup", SearchCriterion.IN, invQuery);
			}

			/* Searching for customer id */
			if (null != inputSearchBean.getCustomerId()) {
				criteria.addCriterion("customerId", SearchCriterion.EQUALS, inputSearchBean.getCustomerId().toString());
			}

			/* Searching for order FROM date */
			if (inputSearchBean.getConfirmFromDate() != null) {
				criteria.addCriterion("submittedDate", SearchCriterion.FROM_DATE, inputSearchBean.getConfirmFromDate());
			}
			/* Searching for order TO date */
			if (inputSearchBean.getConfirmToDate() != null) {
				criteria.addCriterion("submittedDate", SearchCriterion.TO_DATE, inputSearchBean.getConfirmToDate());
			}
			/* Searching for promised ship FROM date */

			if (!StringHandler.isBlankString(inputSearchBean.getSearchArgument())) {
				String mode = inputSearchBean.getSearchMode();
				String field = inputSearchBean.getSearchField();

				if (mode.equals("is"))
					criteria.addCriterion(field, SearchCriterion.EQUALS, inputSearchBean.getSearchArgument());
				if (mode.equals("like"))
					criteria.addCriterion(field, SearchCriterion.LIKE, inputSearchBean.getSearchArgument(), SearchCriterion.IGNORE_CASE);
				if (mode.equals("startsWith"))
					criteria.addCriterion(field, SearchCriterion.STARTS_WITH, inputSearchBean.getSearchArgument(), SearchCriterion.IGNORE_CASE);
				if (mode.equals("endsWith"))
					criteria.addCriterion(field, SearchCriterion.ENDS_WITH, inputSearchBean.getSearchArgument(), SearchCriterion.IGNORE_CASE);
			}

			// personnel id
			if (null != inputSearchBean.getPersonnelId()) {
				criteria.addCriterion("submittedBy", SearchCriterion.EQUALS, inputSearchBean.getPersonnelId().toString());
			}
			criteria.addCriterion("orderStatus", SearchCriterion.NOT_EQUAL, "Complete");
			// Searching for order status
			String additionalWhere = "";
			String allSelected = "";
			String qualityHoldSelected = "";
			String otherStatusSelected = "";
			if ((null != inputSearchBean.getOrderStatus()) && (inputSearchBean.getOrderStatus().length > 0)) {
				boolean initFlag = true;
				for (int i = 0; i < inputSearchBean.getOrderStatus().length; i++) {
					if (inputSearchBean.getOrderStatus()[i].length() > 0) {
						if (inputSearchBean.getOrderStatus()[i].toString().equalsIgnoreCase("All")) {
							allSelected = " release_status != 'Released'";
						}else if (inputSearchBean.getOrderStatus()[i].toString().equalsIgnoreCase("Quality Hold")) {
							qualityHoldSelected = " quality_hold = 'Y'";
						} else {
							if (otherStatusSelected.length() == 0) {
								otherStatusSelected = "'"+inputSearchBean.getOrderStatus()[i].toString()+"'";
							}else {
								otherStatusSelected += ",'"+inputSearchBean.getOrderStatus()[i].toString()+"'";
							}
						}
					}
				}
			}
			if (allSelected.length() > 0) {
				if (qualityHoldSelected.length() == 0) {
					additionalWhere = " and ("+allSelected+" or quality_hold = 'Y'";
				}else {
					additionalWhere = " and ("+allSelected;
				}
			}
			if (qualityHoldSelected.length() > 0) {
				if (additionalWhere.length() == 0) {
					additionalWhere = " and ("+qualityHoldSelected;
				}else {
					additionalWhere += " or "+qualityHoldSelected;
				}
			}
			boolean needClose = true;
			if (otherStatusSelected.length() > 0) {
				if (additionalWhere.length() == 0) {
					if (otherStatusSelected.indexOf(",") > 0) {
						additionalWhere = " and release_status in ("+otherStatusSelected+")";
					}else {
						additionalWhere = " and release_status = "+otherStatusSelected;
					}
					needClose = false;
				}else {
					String oper = " and ";
					if (qualityHoldSelected.length() > 0) {
						oper = " or ";
					}
					if (otherStatusSelected.indexOf(",") > 0) {
						additionalWhere += oper+"release_status in ("+otherStatusSelected+")";
					}else {
						additionalWhere += oper+"release_status = "+otherStatusSelected;
					}
				}
			}
			//adding ) if
			if (additionalWhere.length() > 0 && needClose) {
				additionalWhere += ")";
			}


			SortCriteria sort = new SortCriteria();
			sort.setSortAscending(true);
			sort.addCriterion("customerName,opsEntityId,inventoryGroup,prNumber");
			return factory.selectObject(criteria, sort,additionalWhere);
		} finally {
			dbManager = null;
			factory = null;
		}
	}

	public ExcelHandler getExcelReport(Collection<SalesQuoteHoldOvBean> data, Locale locale) throws
	NoDataException, BaseException, Exception {

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
		//write column headers
		pw.addRow();
		pw.addThRegion("label.inventorygroup",2,1);
		pw.addThRegion("label.status",2,1);
		pw.addThRegion("label.customer",2,1);
		pw.addThRegion("label.materialrequestorigin",2,1);
		pw.addThRegion("label.shipto",2,1);
		pw.addThRegion("label.customerreference",2,1);
		pw.addThRegion("label.mr",2,1);
		pw.addThRegion("label.enteredby",2,1);
		pw.addThRegion("label.holddate",2,1);
		pw.addThRegion("label.contact",2,1);
		pw.addThRegion("label.totalvalue",2,1);
		pw.addThRegion("label.availablecredit",2,1);
		pw.addThRegion("label.interms",2,1);
		pw.addThRegion("label.billtonote",2,1);
		pw.addThRegion("label.shiptonote",2,1);
		pw.addThRegion("label.orderinternalnote",2,1);
		pw.addThRegion("label.orderexternalnote",2,1);
		pw.addThRegion("label.holdcomments",2,1);
		pw.addThRegion("label.qualityholdinfo",1,9);

		pw.addRow();
		pw.addTdEmpty();pw.addTdEmpty();pw.addTdEmpty();pw.addTdEmpty();pw.addTdEmpty();pw.addTdEmpty();
		pw.addTdEmpty();pw.addTdEmpty();pw.addTdEmpty();pw.addTdEmpty();pw.addTdEmpty();pw.addTdEmpty();
		pw.addTdEmpty();pw.addTdEmpty();pw.addTdEmpty();pw.addTdEmpty();pw.addTdEmpty();
		pw.addCellKeyBold("label.line");
		pw.addCellKeyBold("label.spec");
		pw.addCellKeyBold("label.sl");
		pw.addCellKeyBold("label.date");
		pw.addCellKeyBold("label.itemid");
		pw.addCellKeyBold("label.partdesc");
//		pw.addCellKeyBold("label.needdate");
//		pw.addCellKeyBold("label.promiseddate");
		pw.addCellKeyBold("label.note");
		pw.addCellKeyBold("label.expertreviewcomment");
		pw.addCellKeyBold("label.forceholdcomment");

		/*Pass the header keys for the Excel.*/
		String[] headerkeys = {
				"label.inventorygroup","label.status","label.customer","label.materialrequestorigin","label.shipto","label.customerreference",
				"label.mr","label.enteredby","label.holddate","label.contact","label.totalvalue", "label.availablecredit",
				"label.interms","label.billtonote","label.shiptonote","label.orderinternalnote","label.orderexternalnote","label.holdcomments",
				"label.line","label.spec","label.sl","label.date",  //"label.needdate","label.promiseddate",
				"label.itemid","label.partdesc","label.note","label.expertreviewcomment","label.forceholdcomment"};

		int[] types = {
				0,0,0,0,0,0,
				pw.TYPE_STRING,0,pw.TYPE_CALENDAR,0,0,0,
				0,0,0,0,0,0,
				0,0,0,pw.TYPE_CALENDAR,  //pw.TYPE_CALENDAR,pw.TYPE_CALENDAR,
				0,0,0,0,0};
//				0,0,0,0,0};  

		/*This array defines the default width of the column when the Excel file opens.
		0 means the width will be default depending on the data type.*/
		int[] widths = {
				15,15,20,15,20,15,
				0,15,0,15,0,15,
				0,20,20,20,20,20,
				0,11,0,0,  //0,0,
				0,20,0,20,20};
		/*This array defines the horizontal alignment of the data in a cell.
		0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = {
				0,0,0,0,0,0,
				0,0,0,0,0,
				0,0,0,0,  //0,0,
				0,0,0,0,0};

		pw.setColumnHeader(headerkeys, types, widths, horizAligns);
		pw.applyColumnWidth();

		// format the numbers to the special columns
		pw.setColumnDigit(8, 2);

//		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy",this.getLocaleObject());
		
		for (SalesQuoteHoldOvBean member : data) {
			Collection<SalesQuoteLineObjBean> lineColl = member.getLine();
			for (SalesQuoteLineObjBean line :lineColl) {
				pw.addRow();
				pw.addCell(member.getInventoryGroupName());
				pw.addCell(member.getDisplayStatus());
				pw.addCell(member.getCustomerName());
				pw.addCell(member.getMaterialRequestOrigin());
				pw.addCell(StringHandler.emptyIfNull(member.getShipToAddressLine1())+" "+StringHandler.emptyIfNull(member.getShipToAddressLine2())+" "+StringHandler.emptyIfNull(member.getShipToAddressLine3())+" "+StringHandler.emptyIfNull(member.getShipToAddressLine4())+" "+StringHandler.emptyIfNull(member.getShipToAddressLine5()));
				pw.addCell(member.getPoNumber());
				pw.addCell(member.getPrNumber());
				pw.addCell(member.getSubmittedByName());
				pw.addCell(member.getDateFirstConfirmed());
				pw.addCell(member.getRequestorName());
				pw.addCell(member.getTotalExtendedPrice());
				pw.addCell(member.getAvailableCredit());
				pw.addCell(member.getWithinTerms());
				pw.addCell(member.getCustomerNote());
				pw.addCell(member.getShiptoNote());
				pw.addCell(member.getInternalNote());
				pw.addCell(member.getSpecialInstructions());
				pw.addCell(member.getHoldComments());

				pw.addCell(line.getLineItem());
				pw.addCell(line.getRcptQualityHoldSpec());
				pw.addCell(line.getRcptQualityHoldShelfLife());
				pw.addCell(line.getRcptQualHoldSpecSetDate());
//				pw.addCell(line.getRequiredDatetime());
//				pw.addCell(line.getPromiseDate());
/*				if(line.getRcptQualHoldSpecSetDate() != null)
					pw.addCell(dateFormat.format(line.getRcptQualHoldSpecSetDate()));
				else
					pw.addCell("");  */
				pw.addCell(line.getItemId());
				pw.addCell(line.getPartDescription());
				pw.addCell(line.getRcptQualityHoldNote());
				pw.addCell(line.getExpertReviewDesc());
				pw.addCell(line.getForceHoldComment());
			}
		}
		return pw;
	}

	public String updateNote(BigDecimal personnelId, MrReleaseInputBean ib) throws BaseException {

		//		a_pr_number request_line_item.pr_number%type,
		//		a_line_item request_line_item.line_item%type,
		//		a_rcpt_quality_hold_note request_line_item.rcpt_quality_hold_note%type,
		//		a_modified_by request_line_item.last_modified_by%type default null) is
		return getProcError(buildProcedureInput(ib.getPrNumber(), ib.getLineItem(), ib.getRcptQualityHoldNote(), personnelId), new Vector(), "PKG_RLI_SALES_ORDER.P_RCPT_QUALITY_HOLD_NOTE");
	}

	public Collection cancelMR(BigDecimal personnelId, MrReleaseInputBean inputSearchBean) throws BaseException, Exception {
		Collection outArgs = null;
		String errorMsg = "";
		String errorCode = null;
		//Collection resultCollection = null;
		Vector errorMessages = new Vector();

		DbManager dbManager = new DbManager(getClient());
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);

		if ((inputSearchBean != null)) {


			try {
				Collection inArgs = buildProcedureInput(inputSearchBean.getPrNumber(), null, personnelId, inputSearchBean.getCancelMRReason());

				outArgs = new Vector(1);
				outArgs.add(new Integer(java.sql.Types.VARCHAR));

				Collection inArgs1 = new Vector(1);
				inArgs1.add("rqcancel");

				if (log.isDebugEnabled()) {
					log.debug("Input Args for PKG_RLI_SALES_ORDER.P_CANCEL_RLI_LINE  :" + inArgs + outArgs + inArgs1);
				}
				Vector error = (Vector) factory.doProcedure("PKG_RLI_SALES_ORDER.P_CANCEL_RLI_LINE", inArgs, outArgs, inArgs1);
				if (error.size() > 0 && error.get(0) != null && !"ok".equalsIgnoreCase((String) error.get(0))) {
					errorCode = (String) error.get(0);
					log.info("Error in Procedure PKG_RLI_SALES_ORDER.P_CANCEL_RLI_LINE: " + inputSearchBean.getPrNumber() + " Error Code " + errorCode + " ");
					errorMessages.add(errorCode);

				}
			} catch (Exception e) {
				errorMsg = "Error deleting record with Pr Number: " + inputSearchBean.getPrNumber();
				errorMessages.add(errorMsg);
			}

		}


		return errorMessages;


	}

	public Collection releaseCreditHold(MrReleaseInputBean inputSearchBean, PersonnelBean personnelBean) throws BaseException, Exception {
		Collection outArgs = null;
		String errorMsg = "";
		String errorCode = null;
		Vector errorMessages = new Vector();

		DbManager dbManager = new DbManager(getClient());
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);

		if ((inputSearchBean != null)) {


			try {
				Collection inArgs = buildProcedureInput(inputSearchBean.getCompanyId(), inputSearchBean.getPrNumber(),personnelBean.getPersonnelId());

				outArgs = new Vector(1);
				outArgs.add(new Integer(java.sql.Types.VARCHAR));

				if (log.isDebugEnabled()) {
					log.debug("Input Args for p_release_credit_hold  :" + inArgs);
				}
				Vector error = (Vector) factory.doProcedure("pkg_rli_sales_order.p_release_credit_hold", inArgs, outArgs);
				if (error.size() > 0 && error.get(0) != null && !"ok".equalsIgnoreCase((String) error.get(0))) {
					errorCode = (String) error.get(0);
					log.info("Error in Procedure pkg_rli_sales_order.p_release_credit_hold: " + inputSearchBean.getPrNumber() + " Error Code " + errorCode + " ");
					errorMessages.add(errorCode);
				}
				//move catalog add request to next queue
				advanceCatalogAddRequest(inputSearchBean.getPrNumber());
			} catch (Exception e) {
				errorMsg = "Error releasing credit record with Pr Number: " + inputSearchBean.getPrNumber();
				errorMessages.add(errorMsg);
			}
		}
		return errorMessages;
	}
	
	public Collection releaseForceHold(MrReleaseInputBean inputSearchBean, PersonnelBean personnelBean) throws BaseException, Exception {
		Collection outArgs = null;
		String errorMsg = "";
		String errorCode = null;
		Vector errorMessages = new Vector();

		DbManager dbManager = new DbManager(getClient());
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);

		if ((inputSearchBean != null)) {


			try {
				Collection inArgs = buildProcedureInput(personnelBean.getPersonnelId(), inputSearchBean.getPrNumber(),inputSearchBean.getLineItem());

				outArgs = new Vector(1);
				outArgs.add(new Integer(java.sql.Types.VARCHAR));

				if (log.isDebugEnabled()) {
					log.debug("Input Args for P_RELEASE_FORCE_HOLD  :" + inArgs);
				}
				Vector error = (Vector) factory.doProcedure("pkg_rli_sales_order.P_RELEASE_FORCE_HOLD", inArgs, outArgs);
				if (error.size() > 0 && error.get(0) != null && !"ok".equalsIgnoreCase((String) error.get(0))) {
					errorCode = (String) error.get(0);
					log.info("Error in Procedure P_RELEASE_FORCE_HOLD: " + inputSearchBean.getPrNumber() + " Error Code " + errorCode + " ");
					errorMessages.add(errorCode);
				}
				//move catalog add request to next queue
				advanceCatalogAddRequest(inputSearchBean.getPrNumber());
			} catch (Exception e) {
				errorMsg = "Error releasing force hold record with Pr Number: " + inputSearchBean.getPrNumber();
				errorMessages.add(errorMsg);
			}
		}
		return errorMessages;
	}
	
	public Collection releaseExpertReviewHold(MrReleaseInputBean inputSearchBean, PersonnelBean personnelBean) throws BaseException, Exception {
		Collection outArgs = null;
		String errorMsg = "";
		String errorCode = null;
		Vector errorMessages = new Vector();

		DbManager dbManager = new DbManager(getClient());
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);

		if ((inputSearchBean != null)) {


			try {
				Collection inArgs = buildProcedureInput(personnelBean.getPersonnelId(), inputSearchBean.getPrNumber(),inputSearchBean.getLineItem());

				outArgs = new Vector(1);
				outArgs.add(new Integer(java.sql.Types.VARCHAR));

				if (log.isDebugEnabled()) {
					log.debug("Input Args for P_RELEASE_EXPERT_REVIEW  :" + inArgs);
				}
				Vector error = (Vector) factory.doProcedure("pkg_rli_sales_order.P_RELEASE_EXPERT_REVIEW", inArgs, outArgs);
				if (error.size() > 0 && error.get(0) != null && !"ok".equalsIgnoreCase((String) error.get(0))) {
					errorCode = (String) error.get(0);
					log.info("Error in pkg_rli_sales_order.P_RELEASE_EXPERT_REVIEW: " + inputSearchBean.getPrNumber() + " Error Code " + errorCode + " ");
					errorMessages.add(errorCode);
				}
				//move catalog add request to next queue
				advanceCatalogAddRequest(inputSearchBean.getPrNumber());
			} catch (Exception e) {
				errorMsg = "Error releasing expert review record with Pr Number: " + inputSearchBean.getPrNumber();
				errorMessages.add(errorMsg);
			}
		}
		return errorMessages;
	}

	private void advanceCatalogAddRequest(BigDecimal prNumber) throws Exception {
		DbManager dbManager = new DbManager(getClient());
		Connection connection = null;
		try {
			connection = dbManager.getConnection();
			GenericSqlFactory factory = new GenericSqlFactory(dbManager);
			EngEvalProcess engEvalProcess = new EngEvalProcess(this.getClient(),this.getLocale(),"");
			engEvalProcess.setFactoryConnection(factory,connection);
			engEvalProcess.setNextStatusAction("Submit");
			engEvalProcess.advanceDistributionCatalogAddRequest(prNumber);
		} catch (Exception e){
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
		}
	}
	
	public Collection acceptPurchaseRequest(MrReleaseInputBean bean, PersonnelBean personnelBean) throws BaseException, Exception {
		Collection outArgs = null;
		String errorMsg = "";
		String errorCode = null;
		Vector errorMessages = new Vector();

		DbManager dbManager = new DbManager(getClient());
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);

		if ((bean != null)) {

			try {
				Collection inArgs = buildProcedureInput(bean.getCompanyId(), bean.getPrNumber(), personnelBean.getPersonnelId());

				outArgs = new Vector(1);
				outArgs.add(new Integer(java.sql.Types.VARCHAR));

				if (log.isDebugEnabled()) {
					log.debug("Input Args for p_accept_purchase_request :" + inArgs);
				}
				Vector error = (Vector) factory.doProcedure(" pkg_rli_sales_order.p_accept_purchase_request", inArgs, outArgs);
				if (error.size() > 0 && error.get(0) != null && !"ok".equalsIgnoreCase((String) error.get(0))) {
					errorCode = (String) error.get(0);
					log.info("p_accept_purchase_request: " + bean.getPrNumber() + " Error Code " + errorCode + " ");
					errorMessages.add(errorCode);
				}
			} catch (Exception e) {
				errorMsg = "Error accept MR with Pr Number: " + bean.getPrNumber();
				errorMessages.add(errorMsg);
			}
		}
		return errorMessages;
	}
/*	
	public Vector updatePromiseDate(MrReleaseInputBean inputBean) throws BaseException {
	  
	    Vector errorMessages = new Vector();
	    
	    dbManager = new DbManager(client,this.getLocale());
	    Connection conn = dbManager.getConnection();
	    GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
	    
	    try {
	    	StringBuilder query = new StringBuilder("update request_line_item set PROMISE_DATE = to_date('");
	    	query.append(DateHandler.formatDate(inputBean.getPromiseDate(),"MM/dd/yyyy"));
	    	query.append("','mm/dd/yyyy') where pr_number = ");
	    	query.append(inputBean.getPrNumber());
	    	query.append(" and line_item = '");
	    	query.append(inputBean.getLineItem());
	    	query.append("'");
	      
			genericSqlFactory.deleteInsertUpdate(query.toString());
		}
		catch (Exception e) {
				e.printStackTrace();
				errorMessages.add(library.getString("error.db.update"));
	    }
	
	    return errorMessages;
	}  */
	
	public Collection releaseQualityHold(MrReleaseInputBean inputSearchBean, PersonnelBean personnelBean) throws BaseException, Exception {
		Collection outArgs = null;
		String errorMsg = "";
		String errorCode = null;
		Vector errorMessages = new Vector();

		DbManager dbManager = new DbManager(getClient());
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);

		if ((inputSearchBean != null)) {
			try {
				Collection inArgs = buildProcedureInput(inputSearchBean.getCompanyId(), inputSearchBean.getPrNumber(),inputSearchBean.getLineItem(),personnelBean.getPersonnelId());

				outArgs = new Vector(1);
				outArgs.add(new Integer(java.sql.Types.VARCHAR));

				if (log.isDebugEnabled()) {
					log.debug("Input Args for p_release_purchase_request  :" + inArgs);
				}
				Vector error = (Vector) factory.doProcedure("pkg_rli_sales_order.p_release_purchase_request", inArgs, outArgs);
				if (error.size() > 0 && error.get(0) != null && !"ok".equalsIgnoreCase((String) error.get(0))) {
					errorCode = (String) error.get(0);
					log.info("Error in Procedure pkg_rli_sales_order.p_release_purchase_request: " + inputSearchBean.getPrNumber() + " Error Code " + errorCode + " ");
					errorMessages.add(errorCode);
				}
				//move catalog add request to next queue
				advanceCatalogAddRequest(inputSearchBean.getPrNumber());
			} catch (Exception e) {
				errorMsg = "Error releasing quality record with Pr Number: " + inputSearchBean.getPrNumber();
				errorMessages.add(errorMsg);
			}
		}
		return errorMessages;
	}

	private String allocateLineDirect(MrReleaseInputBean inputSearchBean) throws BaseException {
		String errorMsg = getProcError(buildProcedureInput(inputSearchBean.getCompanyId(), inputSearchBean.getPrNumber(), inputSearchBean.getLineItem()), new Vector(), "Pkg_rli_sales_order.P_LINE_ITEM_ALLOCATE");
		return errorMsg;
	}

} //end of class
