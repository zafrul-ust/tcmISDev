package com.tcmis.internal.distribution.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import com.tcmis.client.catalog.process.EngEvalProcess;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.factory.PersonnelBeanFactory;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.CustomerCreditBean;
import com.tcmis.internal.distribution.beans.OpsEntCustomerCurrencyViewBean;
import com.tcmis.internal.distribution.beans.OrderCsrViewBean;
import com.tcmis.internal.distribution.beans.SalesQuoteLineViewBean;
import com.tcmis.internal.distribution.beans.SalesQuoteViewBean;

/**
 * ***************************************************************************
 * Process for Scratch Pad Process
 *
 * @version 1.0
 *          ***************************************************************************
 */
public class ScratchPadProcess extends GenericProcess {
	static private String urlRoot = null;
	static { 
// No Default		
		ResourceLibrary lconfig = new ResourceLibrary("tcmISWebResource");
		urlRoot = lconfig.getString("hosturl");
	}
	
	public static Log log = LogFactory.getLog(ScratchPadProcess.class);
	private Connection connection = null;
	ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());

	public ScratchPadProcess(String client) {
		super(client);
	}

	public ScratchPadProcess(String client, String locale) {
		super(client, locale);
	}
	
	public Collection getCscDropDown() throws BaseException {
		factory.setBeanObject(new OrderCsrViewBean());
		
		SearchCriteria criteria = new SearchCriteria();
		SortCriteria sortCriteria = new SortCriteria();
		sortCriteria.addCriterion("csrName");
		return factory.select(criteria, sortCriteria, "ORDER_CSR_VIEW");
	}
	
	public Collection getScratchPadId(BigDecimal personnelId) throws BaseException {
		Collection inArgs = new Vector();
		inArgs.add(personnelId);
		Vector outArgs = new Vector();
		outArgs.add(new Integer(java.sql.Types.NUMERIC));
		outArgs.add(new Integer(java.sql.Types.VARCHAR));
		if (log.isDebugEnabled()) {
			log.debug("PKG_SALES_QUOTE.p_new_scratch_pad"+inArgs);
		}
		return factory.doProcedure("PKG_SALES_QUOTE.p_new_scratch_pad", inArgs, outArgs);
	}

	public Collection doCustomerCredit(BigDecimal customerId, String opsEntityId) throws BaseException {
		Collection result = null;
		try {
			if(connection == null) {
				connection = dbManager.getConnection();
			}
			result = getCustomerCredit(customerId,opsEntityId);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
		}
		return result;
	}
	
	private Collection getCustomerCurrency(BigDecimal customerId, String opsEntityId) throws BaseException {
		StringBuilder query = new StringBuilder("select * from ops_ent_customer_currency_view where customer_id = ");
		query.append(customerId);
		query.append(" and ops_entity_id = '");
		query.append(opsEntityId).append("' order by currency_display ");
		
		factory.setBeanObject(new OpsEntCustomerCurrencyViewBean());
		Collection resultsCollection = factory.selectQuery(query.toString(), connection);
        if (resultsCollection.size() == 0)
        {
           resultsCollection = factory.selectQuery("select * from OPS_ENTITY_HOME_CURRENCY_VIEW where ops_entity_id = '"+opsEntityId+"'", connection); 
        }
        return resultsCollection;
    }
	
	private Collection getCustomerCredit(BigDecimal customerId, String opsEntityId) throws BaseException {
		StringBuilder query = new StringBuilder("select * from table (PKG_CUSTOMER_CREDIT.FX_customer_credit('");
		query.append(StringHandler.emptyIfNull(customerId));
		query.append("','");
		query.append(StringHandler.emptyIfNull(opsEntityId));
		query.append("'))");
		
		factory.setBeanObject(new CustomerCreditBean());
		return factory.selectQuery(query.toString(), connection);
	}

	public String getDupCheck(BigDecimal prNumber, String poNumber, String companyId) throws BaseException {
		
		StringBuilder query = new StringBuilder("select count(*) from (select pr_number from pr where company_id = '");
		query.append(companyId);
		query.append("' and po_number = '");
		query.append(poNumber);
		query.append("' and pr_status = 'posubmit' and pr_number <> ");
		query.append(prNumber);
		query.append(" union select pr_number from sales_quote where quote_type = 'Blanket Order' and status = 'Confirmed' and company_id = '");
		query.append(companyId);
		query.append("' and po_number = '");
		query.append(poNumber);
		query.append("' )");
		
		return factory.selectSingleValue(query.toString());
	}
	
	private String getRowCount(BigDecimal prNumber) throws BaseException {
		
		StringBuilder query = new StringBuilder("select count(*) from sales_quote_line_view where pr_number = ").append(prNumber);
		
		return factory.selectSingleValue(query.toString(), connection);
	}
	
	public Object[] doAddNewLine(BigDecimal personnelId, SalesQuoteViewBean inputBean, SalesQuoteLineViewBean bean,String toServer) throws BaseException {
		Object[] obj = new Object[2];
		String error = null;
		try {
			if(connection == null) {
				connection = dbManager.getConnection();
			}
			if("Y".equals(toServer)) {
				String count = this.getRowCount(inputBean.getPrNumber());
				BigDecimal c = new BigDecimal(count);
				c = c.add(new BigDecimal(1));
				bean.setLineItem(c.toString());
		/*		BigDecimal lineItem = new BigDecimal(bean.getLineItem());
			
				if(c.compareTo(lineItem) != 0) {
					obj[0] = library.getString("label.refreshb4addingline");
					return obj;
				}  */
				
				if("MR".equals(inputBean.getOrderType()))
					error = this.callUpdateRequestLineItem(personnelId,  inputBean,  bean);
				else
					error = this.callSalesQuoteLine(personnelId, inputBean, bean, "");
				
				if(error != null && !error.equalsIgnoreCase("OK"))
					obj[0] = error;
				else 
					obj[1] = this.getSalesQuoteLineResult(inputBean.getPrNumber(), bean.getLineItem());
			}
			else 
				obj[1] = this.getSalesQuoteLineResult(inputBean.getPrNumber(), bean.getLineItem());
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
		}
		
		return obj;
	}

	private boolean searchScratchPadId(DynaBean form, HttpServletRequest request) throws BaseException, Exception {
//		log.debug("Get newScratchPadId next.");
		BigDecimal newScratchPadId = new BigDecimal((String) (form).get("scratchPadId"));
		if (log.isDebugEnabled()) {
			log.debug("newScratchPadId:"+newScratchPadId+"**");
		}
		Vector<SalesQuoteViewBean> salesQuoteColl = (Vector<SalesQuoteViewBean>) this.getSalesQuoteResult(newScratchPadId);
		try {
			if (salesQuoteColl.size() > 0) {
				request.setAttribute("salesQuoteViewCollection", salesQuoteColl.get(0));
				if(salesQuoteColl.get(0).getCustomerId() != null && salesQuoteColl.get(0).getOpsEntityId() != null) {
					request.setAttribute("customerCurrencyColl", getCustomerCurrency(salesQuoteColl.get(0).getCustomerId(), salesQuoteColl.get(0).getOpsEntityId()));
				}
				request.setAttribute("showOverCreditLimit", "true");
				request.setAttribute("blank", "N");
				//            if ((salesQuoteColl.get(0).getAvailableCredit() != null && BigDecimal.ZERO.compareTo(salesQuoteColl.get(0).getAvailableCredit()) > 0) || "N".equalsIgnoreCase(salesQuoteColl.get(0).getWithinTerms())) {
				if(salesQuoteColl.get(0).getCustomerOpsEntityId() == null) {
					Vector<CustomerCreditBean> customerCreditColl = (Vector<CustomerCreditBean>) this.getCustomerCredit(salesQuoteColl.get(0).getCustomerId(), salesQuoteColl.get(0).getOpsEntityId());
					request.setAttribute("customerCreditColl", customerCreditColl.get(0));
				}
				//            }
				Collection salesQuoteLineColl = this.getSalesQuoteLineResult(newScratchPadId, "all");
				request.setAttribute("salesQuoteLineViewCollection", salesQuoteLineColl);
				return true;
			} else {
				request.setAttribute("popUpSearch", "Y");
				request.setAttribute("blank", "Y");
				//            request.setAttribute("salesQuoteViewCollection", new SalesQuoteViewBean());
				request.setAttribute("salesQuoteViewCollection", null);
				request.setAttribute("scratchPadId", null);
				request.setAttribute("tcmISError", library.getString("main.nodatafound"));
				return false;
			}
		} catch (Exception ex) {
			log.debug(ex);
			request.setAttribute("tcmISError", library.getString("main.nodatafound"));
			return false;
		}
	}

	private void saveQuote(DynaBean form, BigDecimal personnelId, SalesQuoteViewBean inputBean, Collection<SalesQuoteLineViewBean> updateBeanCollection, HttpServletRequest request) throws BaseException, Exception {
		String errorMessage = null;
		if ( inputBean.getTotalRows().equals(new BigDecimal(0))) {
			errorMessage = this.callSalesQuote(personnelId, inputBean, null, "Y");
		} else {
			errorMessage = this.callSalesQuote(personnelId, inputBean, null, "N");
		}
		if (errorMessage != null && !errorMessage.equalsIgnoreCase("OK"))
			request.setAttribute("tcmISError", errorMessage);

		String action = "";

		if (!inputBean.getTotalRows().equals(new BigDecimal(0))) {
			Collection<String> errorMessageCollection1 = this.updateSalesQuoteLine(personnelId, inputBean, updateBeanCollection, action, "Y");
			request.setAttribute("tcmISErrors", errorMessageCollection1);
		}

		request.setAttribute("selectedLineItem", inputBean.getSelectedLineItem());
		request.setAttribute("selectedRowId", inputBean.getSelectedRowId());
	}

	private void saveMR(DynaBean form, BigDecimal personnelId, SalesQuoteViewBean inputBean, Collection<SalesQuoteLineViewBean> updateBeanCollection, HttpServletRequest request) throws BaseException, Exception {
		String errorMessage = null;
		if ( inputBean.getTotalRows().equals(new BigDecimal(0))) {
			errorMessage = this.callUpdatePurchaseRequest(personnelId, inputBean, "Y");
		} else {
			errorMessage = this.callUpdatePurchaseRequest(personnelId, inputBean, "N");
		}
		if (errorMessage != null && !errorMessage.equalsIgnoreCase("OK"))
			request.setAttribute("tcmISError", errorMessage);

		if (!inputBean.getTotalRows().equals(new BigDecimal(0))) {
			Collection<String> errorMessages = this.doUpdateRequestLineItem(personnelId, inputBean, updateBeanCollection);
			request.setAttribute("tcmISErrors", errorMessages);
		}
		
		if("Pending Acceptance".equals(inputBean.getReleaseStatus()) && !inputBean.getHoldComments().equals(inputBean.getOriginalHoldComments())) {
			String query = "update purchase_request set hold_comments = "+SqlHandler.delimitString(StringHandler.emptyIfNull(inputBean.getHoldComments()))+
            			   " where pr_number = "+inputBean.getPrNumber().toString();
			factory.deleteInsertUpdate(query,connection);
		}
			
		request.setAttribute("selectedLineItem", inputBean.getSelectedLineItem());
		request.setAttribute("selectedRowId", inputBean.getSelectedRowId());
	}

	public Boolean doSearchScratchPadId(DynaBean form, HttpServletRequest request) throws BaseException {
		Boolean r = false;
		try {
			if(connection == null) {
				connection = dbManager.getConnection();
			}
			r = this.searchScratchPadId(form, request);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
		}
		return r;
	}

	public void doSaveNew(DynaBean form, BigDecimal personnelId, SalesQuoteViewBean inputBean, Collection<SalesQuoteLineViewBean> updateBeanCollection, HttpServletRequest request) throws BaseException {
		try {
			if(connection == null) {
				connection = dbManager.getConnection();
			}
			this.saveQuote(form, personnelId, inputBean, updateBeanCollection, request);

			this.searchScratchPadId(form, request);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
		}
	}

	public void doConfirmQuote(DynaBean form, BigDecimal personnelId, SalesQuoteViewBean inputBean, Collection<SalesQuoteLineViewBean> updateBeanCollection, HttpServletRequest request) throws BaseException {
		try {
			if(connection == null) {
				connection = dbManager.getConnection();
			}

			Date today = new Date();
			String errorMessage = this.callSalesQuote(personnelId, inputBean, today, "N");
			if (errorMessage != null && !errorMessage.equalsIgnoreCase("OK")) {
				request.setAttribute("tcmISError", errorMessage);
			}

			String action = "";

			if (!inputBean.getTotalRows().equals(new BigDecimal(0))) {
				Collection<String> errorMessageCollection1 = this.updateSalesQuoteLine(personnelId, inputBean, updateBeanCollection, action, "Y");
				request.setAttribute("tcmISErrors", errorMessageCollection1);
			}

			String quoteErrorMessage = this.callConfirmQuote(inputBean);
			/*TODO handle error message*/
			if (quoteErrorMessage != null && !quoteErrorMessage.equalsIgnoreCase("OK")) {
				//request.setAttribute("tcmISError", errorMessage);
			}

			this.searchScratchPadId(form, request);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
		}
	}

	public void doConfirmMR(DynaBean form, PersonnelBean personnelBean, SalesQuoteViewBean inputBean, Collection<SalesQuoteLineViewBean> updateBeanCollection, HttpServletRequest request) throws BaseException {
		try {
			if(connection == null) {
				connection = dbManager.getConnection();
			}

			this.saveMR(form, new BigDecimal(personnelBean.getPersonnelId()), inputBean, updateBeanCollection, request);

			if("Y".equals(inputBean.getMarginOutSideLimits())) {
				Vector<SalesQuoteViewBean> salesQuoteColl = (Vector<SalesQuoteViewBean>) this.getSalesQuoteResult(inputBean.getPrNumber());
			//	Collection salesQuoteLineColl = this.getSalesQuoteLineResult(inputBean.getPrNumber());
				this.sendNotification(personnelBean, salesQuoteColl.get(0), updateBeanCollection);
			}

			String errorMessage = this.callConfirmPurchaseRequest(new BigDecimal(personnelBean.getPersonnelId()), inputBean);
			if (errorMessage != null && !errorMessage.equalsIgnoreCase("OK")) {
				request.setAttribute("tcmISError", errorMessage);
			}

			this.searchScratchPadId(form, request);
			request.setAttribute("afterConfirmMr", "Y");
			boolean test = false ; // still atesting phase
			if( !test ) {
			try {
				SalesQuoteViewBean myBean = new SalesQuoteViewBean();
				Vector<SalesQuoteViewBean> myBeans = (Vector<SalesQuoteViewBean>) factory.setBean(myBean).selectQuery("select EMAIL_ORDER_ACK,REQUESTOR_EMAIL,fx_personnel_id_to_email(CUSTOMER_SERVICE_REP_ID) END_USER,PO_NUMBER,CUSTOMER_SERVICE_REP_NAME from sales_quote_view where PR_NUMBER = " + inputBean.getPrNumber());
				myBean = myBeans.get(0);
				String emailAck = myBean.getEmailOrderAck();
				String emailAddr = myBean.getRequestorEmail();
				String csrEmail = myBean.getEndUser();
				String customerPO = myBean.getPoNumber();
				String repName = myBean.getCustomerServiceRepName();
				if( "Y".equalsIgnoreCase(inputBean.getEmailOrderAck() ) && "Y".equalsIgnoreCase(emailAck) && !isBlank(emailAddr) ) {
					//				http://www.tcmis.com
					String url = urlRoot + "HaasReports/report/printsalesorders.do?fromInternal=Y&prNumber="+inputBean.getPrNumber()+"&personnelId="+personnelBean.getPersonnelId();
					String subject = "Confirmation of your order PO: " + customerPO;
					String msg = "Many thanks for the above referenced order. We are pleased to attach our confirmation of this order.\n";
						   msg+= "Please contact me should you have any questions or wish to make a change to your order. Please reference our confirmation number "
							   	  + inputBean.getPrNumber() + " in your correspondence.";
						   msg += "\n\nRegards\n" + repName;
						   
					String attachmentName = "MR_Confirmation_"+inputBean.getPrNumber()+".pdf";
					{
						EmailMRConfirmPDFWorkerProcess p = new EmailMRConfirmPDFWorkerProcess(emailAddr,subject,msg,attachmentName,url,csrEmail); 
						Thread thread = new Thread(p);
						thread.start();
					}
					//				String AbsolutePath = HaasReports/report/printsalesorders.do?prNumber="+$v("prNumber")+"&personnelId="+id;
				}
			} catch(Exception ex) {} // don't break;
// attach comfirnation email.
//			public static void sendErrorEmailAttach(String subject, String msg,String attachmentPath) {
//				MailProcess.sendEmail("edierror@haasgroupintl.com","","edierror@haasgroupintl.com",subject,msg,
//						"CabinetCount:"+attachmentPath,
//						attachmentPath);
//			}
			} // end of test block
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
		}
	}
	
	public void doAcceptMR(DynaBean form, PersonnelBean personnelBean, SalesQuoteViewBean inputBean, Collection<SalesQuoteLineViewBean> updateBeanCollection, HttpServletRequest request) throws BaseException {
		try {
			if(connection == null) {
				connection = dbManager.getConnection();
			}

			this.saveMR(form, new BigDecimal(personnelBean.getPersonnelId()), inputBean, updateBeanCollection, request);

			String errorMessage = this.acceptPurchaseRequest(inputBean, personnelBean); //this.callConfirmPurchaseRequest(new BigDecimal(personnelBean.getPersonnelId()), inputBean);
			if (errorMessage != null && !errorMessage.equalsIgnoreCase("OK")) {
				request.setAttribute("tcmISError", errorMessage);
			}  

			this.searchScratchPadId(form, request);
			boolean test = false ; // still atesting phase
			if( !test ) {
			try {
				SalesQuoteViewBean myBean = new SalesQuoteViewBean();
				Vector<SalesQuoteViewBean> myBeans = (Vector<SalesQuoteViewBean>) factory.setBean(myBean).selectQuery("select EMAIL_ORDER_ACK,REQUESTOR_EMAIL,fx_personnel_id_to_email(CUSTOMER_SERVICE_REP_ID) END_USER,PO_NUMBER,CUSTOMER_SERVICE_REP_NAME from sales_quote_view where PR_NUMBER = " + inputBean.getPrNumber());
				myBean = myBeans.get(0);
				String emailAck = myBean.getEmailOrderAck();
				String emailAddr = myBean.getRequestorEmail();
				String csrEmail = myBean.getEndUser();
				String customerPO = myBean.getPoNumber();
				String repName = myBean.getCustomerServiceRepName();
				if( "Y".equalsIgnoreCase(emailAck) && !isBlank(emailAddr) ) {
					//				http://www.tcmis.com
					String url = urlRoot + "HaasReports/report/printsalesorders.do?fromInternal=Y&prNumber="+inputBean.getPrNumber()+"&personnelId="+personnelBean.getPersonnelId();
					String subject = "Confirmation of your order PO: " + customerPO;
					String msg = "Many thanks for the above referenced order. We are pleased to attach our confirmation of this order.\n";
						   msg+= "Please contact me should you have any questions or wish to make a change to your order. Please reference our confirmation number "
							   	  + inputBean.getPrNumber() + " in your correspondence.";
						   msg += "\n\nRegards\n" + repName;
						   
					String attachmentName = "MR_Confirmation_"+inputBean.getPrNumber()+".pdf";
					{
						EmailMRConfirmPDFWorkerProcess p = new EmailMRConfirmPDFWorkerProcess(emailAddr,subject,msg,attachmentName,url,csrEmail); 
						Thread thread = new Thread(p);
						thread.start();
					}
					//				String AbsolutePath = HaasReports/report/printsalesorders.do?prNumber="+$v("prNumber")+"&personnelId="+id;
				}
			} catch(Exception ex) {} // don't break;
// attach comfirnation email.
//			public static void sendErrorEmailAttach(String subject, String msg,String attachmentPath) {
//				MailProcess.sendEmail("edierror@haasgroupintl.com","","edierror@haasgroupintl.com",subject,msg,
//						"CabinetCount:"+attachmentPath,
//						attachmentPath);
//			}
			} // end of test block
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
		}
	}
	
	public String acceptPurchaseRequest(SalesQuoteViewBean bean, PersonnelBean personnelBean) throws BaseException, Exception {
		Collection outArgs = null;
		String errorCode = null;
		
		try {
			Collection inArgs = buildProcedureInput(bean.getCompanyId(), bean.getPrNumber(), personnelBean.getPersonnelId());

			outArgs = new Vector(1);
			outArgs.add(new Integer(java.sql.Types.VARCHAR));

			if (log.isDebugEnabled()) {
				log.debug("Input Args for p_accept_purchase_request :" + inArgs);
			}
			Vector error = (Vector) factory.doProcedure("pkg_rli_sales_order.p_accept_purchase_request", inArgs, outArgs);
			if (error.size() > 0 && error.get(0) != null && !"ok".equalsIgnoreCase((String) error.get(0))) {
				errorCode = (String) error.get(0);
				log.info("p_accept_purchase_request: " + bean.getPrNumber() + " Error Code " + errorCode + " ");
			}
			
		} catch (Exception ex) {
			errorCode = library.getString("generic.error");
			if (log.isDebugEnabled()) {
				log.debug("Error accept MR with Pr Number: " + bean.getPrNumber());
			}
			ex.printStackTrace();
		}
		
		return errorCode;
	}

	public void doNewQuoteFromScratchPad(DynaBean form, BigDecimal personnelId, SalesQuoteViewBean inputBean, Collection<SalesQuoteLineViewBean> updateBeanCollection, HttpServletRequest request) throws BaseException {
		try {
			if(connection == null) {
				connection = dbManager.getConnection();
			}

			if (inputBean.getStatus() != null && !inputBean.getStatus().equalsIgnoreCase("Confirmed")) {
				this.saveQuote(form, personnelId, inputBean, updateBeanCollection, request);
			}
			BigDecimal previousScratchPadId = new BigDecimal((String) (form).get("scratchPadId"));

			Vector headerResult = this.callNewSalesQuote(personnelId, previousScratchPadId, inputBean);
			if (headerResult.get(1) != null && !((String) headerResult.get(1)).equalsIgnoreCase("OK")) {
				request.setAttribute("tcmISError", headerResult.get(1));
			}

			BigDecimal newPrNumber = (BigDecimal) headerResult.get(0);

			Vector errorMessages = this.callNewSalesQuoteLine(personnelId, previousScratchPadId, updateBeanCollection, newPrNumber);

			this.searchScratchPadId(form, request);
			request.setAttribute("tcmISErrors", errorMessages);
			request.setAttribute("newPrNumber", newPrNumber);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
		}
	}

	public void doNewQuoteFromMR(DynaBean form, BigDecimal personnelId, SalesQuoteViewBean inputBean, Collection<SalesQuoteLineViewBean> updateBeanCollection, HttpServletRequest request) throws BaseException {
		try {
			if(connection == null) {
				connection = dbManager.getConnection();
			}

			this.saveMR(form, personnelId, inputBean, updateBeanCollection, request);

			BigDecimal previousScratchPadId = new BigDecimal((String) (form).get("scratchPadId"));


			Vector headerResult = this.callNewSalesQuote(personnelId, previousScratchPadId, inputBean);
			if (headerResult.get(1) != null && !((String) headerResult.get(1)).equalsIgnoreCase("OK")) {
				request.setAttribute("tcmISError", headerResult.get(1));
			}

			BigDecimal newPrNumber = (BigDecimal) headerResult.get(0);

			Vector errorMessages = this.callNewSalesQuoteLine(personnelId, previousScratchPadId, updateBeanCollection, newPrNumber);
			request.setAttribute("tcmISErrors", errorMessages);
			request.setAttribute("newPrNumber", newPrNumber);

			this.searchScratchPadId(form, request);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
		}
	}

	public void doCreateMRfromQuote(DynaBean form, BigDecimal personnelId, SalesQuoteViewBean inputBean, Collection<SalesQuoteLineViewBean> updateBeanCollection, HttpServletRequest request) throws BaseException {
		try {
			if(connection == null) {
				connection = dbManager.getConnection();
			}

			if (inputBean.getStatus() != null && !inputBean.getStatus().equalsIgnoreCase("Confirmed")) {
				this.saveQuote(form, personnelId, inputBean, updateBeanCollection, request);
			}

			BigDecimal previousScratchPadId = new BigDecimal((String) (form).get("scratchPadId"));

			Object[] results = this.callCreateMrFromQuote(personnelId, previousScratchPadId, updateBeanCollection);

			request.setAttribute("tcmISErrors", results[1]);
			request.setAttribute("newPrNumber", results[0]);

			this.searchScratchPadId(form, request);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
		}
	}

	public void doCreateMRfromMR(DynaBean form, BigDecimal personnelId, SalesQuoteViewBean inputBean, Collection<SalesQuoteLineViewBean> updateBeanCollection, HttpServletRequest request) throws BaseException {
		try {
			if(connection == null) {
				connection = dbManager.getConnection();
			}

			this.saveMR(form, personnelId, inputBean, updateBeanCollection, request);

			BigDecimal previousScratchPadId = new BigDecimal((String) (form).get("scratchPadId"));

			Object[] results = this.callCopyMultiMrLine(personnelId, previousScratchPadId, updateBeanCollection);

			String errorMessage = null;
			if (results[1] != null && !((String) results[1]).equalsIgnoreCase("OK")) {
				errorMessage = results[1].toString();
			}

			request.setAttribute("newPrNumber", results[0]);
			request.setAttribute("tcmISErrors", errorMessage);

			this.searchScratchPadId(form, request);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
		}
	}

	public void doNewScratchPadFromQuote(DynaBean form, BigDecimal personnelId, SalesQuoteViewBean inputBean, Collection<SalesQuoteLineViewBean> updateBeanCollection, HttpServletRequest request) throws BaseException {
		try {
			if(connection == null) {
				connection = dbManager.getConnection();
			}

			if (inputBean.getStatus() != null && !inputBean.getStatus().equalsIgnoreCase("Confirmed")) {
				this.saveQuote(form, personnelId, inputBean, updateBeanCollection, request);
			}
			BigDecimal previousScratchPadId = new BigDecimal((String) (form).get("scratchPadId"));

			Vector headerResult = this.callCreateScratchPad(personnelId, previousScratchPadId);
			if (headerResult.get(1) != null && !((String) headerResult.get(1)).equalsIgnoreCase("OK")) {
				request.setAttribute("tcmISError", headerResult.get(1));
			}

			BigDecimal newPrNumber = (BigDecimal) headerResult.get(0);

			Vector errorMessages = this.callCreateScratchPadLine(personnelId, previousScratchPadId, updateBeanCollection, newPrNumber);

			request.setAttribute("tcmISErrors", errorMessages);
			request.setAttribute("newPrNumber", newPrNumber);

			this.searchScratchPadId(form, request);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
		}
	}

	public void doNewScratchPadFromMR(DynaBean form, BigDecimal personnelId, SalesQuoteViewBean inputBean, Collection<SalesQuoteLineViewBean> updateBeanCollection, HttpServletRequest request) throws BaseException {
		try {
			if(connection == null) {
				connection = dbManager.getConnection();
			}

			this.saveMR(form, personnelId, inputBean, updateBeanCollection, request);

			BigDecimal previousScratchPadId = new BigDecimal((String) (form).get("scratchPadId"));

			Vector headerResult = this.callCreateScratchPad(personnelId, previousScratchPadId);
			if (headerResult.get(1) != null && !((String) headerResult.get(1)).equalsIgnoreCase("OK")) {
				request.setAttribute("tcmISError", headerResult.get(1));
			}

			BigDecimal newPrNumber = (BigDecimal) headerResult.get(0);

			Vector errorMessages = this.callCreateScratchPadLine(personnelId, previousScratchPadId, updateBeanCollection, newPrNumber);

			request.setAttribute("tcmISErrors", errorMessages);
			request.setAttribute("newPrNumber", newPrNumber);

			this.searchScratchPadId(form, request);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
		}
	}

	public void doAutoAllocateForMR(DynaBean form, BigDecimal personnelId, SalesQuoteViewBean inputBean, Collection<SalesQuoteLineViewBean> updateBeanCollection, HttpServletRequest request) throws BaseException {
		try {
			if(connection == null) {
				connection = dbManager.getConnection();
			}

			this.saveMR(form, personnelId, inputBean, updateBeanCollection, request);

			Vector errorMessages = this.callLineItemAllocate(updateBeanCollection);

			this.searchScratchPadId(form, request);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
		}
	}

	public void doAutoAllocateForSP(DynaBean form, BigDecimal personnelId, SalesQuoteViewBean inputBean, Collection<SalesQuoteLineViewBean> updateBeanCollection, HttpServletRequest request) throws BaseException {
		try {
			if(connection == null) {
				connection = dbManager.getConnection();
			}

			this.saveQuote(form, personnelId, inputBean, updateBeanCollection, request);

			Vector errorMessages = this.callLineItemAllocate(updateBeanCollection);

			this.searchScratchPadId(form, request);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
		}
	}

	public void doDeleteQuoteLine(DynaBean form, BigDecimal personnelId, SalesQuoteViewBean inputBean, Locale locale, HttpServletRequest request) throws BaseException {
		try {
			if(connection == null) {
				connection = dbManager.getConnection();
			}

			if (!inputBean.getTotalRows().equals(new BigDecimal(0))) {
				Collection updateBeanCollection = BeanHandler.getBeans(form, "salesQuoteLineBean", new SalesQuoteLineViewBean(), locale);
				
				// Save all
				this.saveQuote(form, personnelId, inputBean, updateBeanCollection, request);
				
				// Save all rows including duplicated rows before deleting
				// Vector<String> errorMessageCollection1 = this.updateSalesQuoteLine(personnelId, inputBean, updateBeanCollection, "", "Y");
				// request.setAttribute("tcmISErrors", errorMessageCollection1);

				// Delete selected rows
				Vector<String> errorMessageCollection1 = this.updateSalesQuoteLine(personnelId, inputBean, updateBeanCollection, "DELETE", "");
				request.setAttribute("tcmISErrors", errorMessageCollection1);
			}

			this.searchScratchPadId(form, request);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
		}
	}

	public void doDeleteMRLine(DynaBean form, BigDecimal personnelId, SalesQuoteViewBean inputBean, Locale locale, HttpServletRequest request) throws BaseException {
		try {
			if(connection == null) {
				connection = dbManager.getConnection();
			}

			if (!inputBean.getTotalRows().equals(new BigDecimal(0))) {
				Collection updateBeanCollection = BeanHandler.getBeans(form, "salesQuoteLineBean", new SalesQuoteLineViewBean(), locale);
				
				// Save all
				this.saveMR(form, personnelId, inputBean, updateBeanCollection, request);
				
				// Save all line items before deleting a line
				// Vector<String> errorMessages = this.doUpdateRequestLineItem(personnelId, inputBean, updateBeanCollection);

				// Delete selected rows
				Vector<String> errorMessages = this.deleteRliLine(inputBean, updateBeanCollection);
				request.setAttribute("tcmISErrors", errorMessages);
			}

			this.searchScratchPadId(form, request);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
		}
	}

	public void doDeleteQuote(DynaBean form, BigDecimal personnelId, SalesQuoteViewBean inputBean, HttpServletRequest request) throws BaseException {
		try {
			if(connection == null) {
				connection = dbManager.getConnection();
			}

			String errorMsg = this.callDeleteQuote(personnelId, inputBean.getPrNumber());
			if (errorMsg != null && !errorMsg.equalsIgnoreCase("OK")) {
				request.setAttribute("tcmISError", errorMsg);
				this.searchScratchPadId(form, request);
				request.setAttribute("showOverCreditLimit", "false");
			}
			else request.setAttribute("closeTab", "Y");
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
		}
	}

	public void doDeleteMR(DynaBean form, BigDecimal personnelId, SalesQuoteViewBean inputBean, HttpServletRequest request) throws BaseException {
		try {
			if(connection == null) {
				connection = dbManager.getConnection();
			}

			String errorMsg = this.callDeleteMR(inputBean.getPrNumber());
			if (errorMsg != null && !errorMsg.equalsIgnoreCase("OK")) {
				request.setAttribute("tcmISError", errorMsg);
				this.searchScratchPadId(form, request);
				request.setAttribute("showOverCreditLimit", "false");
			} else request.setAttribute("closeTab", "Y");
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
		}
	}

	public void doCancelQuote(DynaBean form, SalesQuoteViewBean inputBean, HttpServletRequest request) throws BaseException {
		try {
			if(connection == null) {
				connection = dbManager.getConnection();
			}

			String errorMsg = this.callCancelQuote(inputBean.getPrNumber());
			if (errorMsg != null) request.setAttribute("tcmISError", errorMsg);

			this.searchScratchPadId(form, request);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
		}
	}

	public void doSave(DynaBean form, BigDecimal personnelId, SalesQuoteViewBean inputBean, Collection<SalesQuoteLineViewBean> updateBeanCollection, HttpServletRequest request) throws BaseException {
		try {
			if(connection == null) {
				connection = dbManager.getConnection();
			}

			if ("MR".equals(inputBean.getOrderType()))
				this.saveMR(form, personnelId, inputBean, updateBeanCollection, request);
			else this.saveQuote(form, personnelId, inputBean, updateBeanCollection, request);

			this.searchScratchPadId(form, request);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
		}
	}

	public void doCancelMRLine(DynaBean form, BigDecimal personnelId, SalesQuoteViewBean inputBean, Collection<SalesQuoteLineViewBean> updateBeanCollection, HttpServletRequest request) throws BaseException {
		try {
			if(connection == null) {
				connection = dbManager.getConnection();
			}

			String errMsg = null;
			errMsg = this.callCancelRliLine(personnelId, inputBean, updateBeanCollection);

			if (errMsg != null && !"".equals(errMsg)) request.setAttribute("tcmISError", errMsg);

			this.searchScratchPadId(form, request);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
		}
	}

	public void doReleaseMRLine(DynaBean form, BigDecimal personnelId, SalesQuoteViewBean inputBean, Collection<SalesQuoteLineViewBean> updateBeanCollection, HttpServletRequest request, PersonnelBean personnelBean) throws BaseException {
		try {
			if(connection == null) {
				connection = dbManager.getConnection();
			}

			//save data
			this.saveMR(form, personnelId, inputBean, updateBeanCollection, request);

			String errMsg = null;
			errMsg = request.getParameter("tcmISError");
			String errMsgs = request.getParameter("tcmISErrors");
			if (StringHandler.isBlankString(errMsg) && StringHandler.isBlankString(errMsgs)) {
				//release data
				errMsg = this.callReleaseMRLine(inputBean, updateBeanCollection,personnelBean);
				if (errMsg != null && !"".equals(errMsg)) {
					request.setAttribute("tcmISError", errMsg);
				}else {
					//allocate data
					errMsg = "";
					Vector errorMessages = this.callLineItemAllocate(updateBeanCollection);
					for (int i = 0; i < errorMessages.size(); i++) {
						if (errorMessages.elementAt(i) != null) {
							errMsg += errorMessages.elementAt(i);
						}
					}
					if (errMsg != null && !"".equals(errMsg)) {
						request.setAttribute("tcmISError", errMsg);
					}
				}
			}

			//load screen data
			this.searchScratchPadId(form, request);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
		}
	}

	public void doReleaseMR(DynaBean form, BigDecimal personnelId, SalesQuoteViewBean inputBean, Collection<SalesQuoteLineViewBean> updateBeanCollection, HttpServletRequest request, PersonnelBean personnelBean) throws BaseException {
		try {
			if(connection == null) {
				connection = dbManager.getConnection();
			}

			//	save data
			this.saveMR(form, personnelId, inputBean, updateBeanCollection, request);
			String errMsg = null;
			errMsg = request.getParameter("tcmISError");
			String errMsgs = request.getParameter("tcmISErrors");
			if (StringHandler.isBlankString(errMsg) && StringHandler.isBlankString(errMsgs)) {
				//release data
				errMsg = "";
				Vector errorMessages = this.callReleaseMR(updateBeanCollection,personnelBean);
				for (int i = 0; i < errorMessages.size(); i++) {
					if (errorMessages.elementAt(i) != null) {
						errMsg += errorMessages.elementAt(i);
					}
				}
				if (errMsg != null && !"".equals(errMsg)) {
					request.setAttribute("tcmISError", errMsg);
				}else {
					//allocate data
					errMsg = "";
					errorMessages = this.callLineItemAllocate(updateBeanCollection);
					for (int i = 0; i < errorMessages.size(); i++) {
						if (errorMessages.elementAt(i) != null) {
							errMsg += errorMessages.elementAt(i);
						}
					}
					if (errMsg != null && !"".equals(errMsg)) {
						request.setAttribute("tcmISError", errMsg);
					}
				}
			}
			//load screen data
			this.searchScratchPadId(form, request);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
		}
	}


	private Vector callNewSalesQuote(BigDecimal personnelId, BigDecimal previousScratchPadId, SalesQuoteViewBean inputBean) throws BaseException {
		Collection inArgs = new Vector();
		inArgs.add(personnelId);

		if (!isBlank(previousScratchPadId))
			inArgs.add(previousScratchPadId);
		else
			inArgs.add("");

		if("SP".equals(inputBean.getNewOrderType()))
			inArgs.add("Scratch Pad");
		else if("B".equals(inputBean.getNewOrderType()))
			inArgs.add("Blanket Order");
		else
			inArgs.add("Quote"); // This is for Quote

		Vector outArgs = new Vector();
		outArgs.add(new Integer(java.sql.Types.NUMERIC));
		outArgs.add(new Integer(java.sql.Types.VARCHAR));
		if (log.isDebugEnabled()) {
			log.debug("PKG_SALES_QUOTE.P_NEW_SALES_QUOTE"+inArgs);
		}
		Vector v = (Vector) factory.doProcedure(connection,"PKG_SALES_QUOTE.P_NEW_SALES_QUOTE", inArgs, outArgs);
		return v;
	}


	private Vector callNewSalesQuoteLine(BigDecimal personnelId, BigDecimal previousScratchPadId, Collection<SalesQuoteLineViewBean> updateBeanCollection, BigDecimal newPrNumber) throws BaseException {
		Vector errorMessages = new Vector();
		try {
			for (SalesQuoteLineViewBean bean : updateBeanCollection) {
				if (bean.getOkDoUpdate().startsWith("true")) {
					Collection inArgs = new Vector();
					inArgs.add(personnelId);

					if (!isBlank(previousScratchPadId)) inArgs.add(previousScratchPadId);
					else inArgs.add("");

					if (!isBlank(bean.getLineItem())) inArgs.add(bean.getLineItem());
					else inArgs.add("");

					if (!isBlank(newPrNumber)) inArgs.add(newPrNumber);
					else inArgs.add("");

					Vector outArgs = new Vector();
					outArgs.add(new Integer(java.sql.Types.NUMERIC));
					outArgs.add(new Integer(java.sql.Types.VARCHAR));
					if (log.isDebugEnabled()) {
						log.debug("PKG_SALES_QUOTE.P_NEW_SALES_QUOTE_LINE"+inArgs);
					}
					Vector v = (Vector) factory.doProcedure(connection,"PKG_SALES_QUOTE.P_NEW_SALES_QUOTE_LINE", inArgs, outArgs);
					if (v.get(1) != null && !((String) v.get(1)).equalsIgnoreCase("OK") && !((String) v.get(1)).equalsIgnoreCase("")) {
						errorMessages.add(v.get(1));
					}
				}
			}
			return errorMessages;
		} catch (Exception ex) {
			ex.printStackTrace();
			errorMessages.add(library.getString("generic.error"));
			return errorMessages;
		}

	}

	private Vector callCreateScratchPad(BigDecimal personnelId, BigDecimal previousScratchPadId) throws BaseException {
		Collection inArgs = new Vector();
		inArgs.add(personnelId);

		if (!isBlank(previousScratchPadId)) inArgs.add(previousScratchPadId);
		else inArgs.add("");

		Vector outArgs = new Vector();
		outArgs.add(new Integer(java.sql.Types.NUMERIC));
		outArgs.add(new Integer(java.sql.Types.VARCHAR));
		if (log.isDebugEnabled()) {
			log.debug("PKG_SALES_QUOTE.P_CREATE_SCRATCH_PAD"+inArgs);
		}
		Vector v = (Vector) factory.doProcedure(connection, "PKG_SALES_QUOTE.P_CREATE_SCRATCH_PAD", inArgs, outArgs);
		return v;
	}

	private Vector callCreateScratchPadLine(BigDecimal personnelId, BigDecimal previousScratchPadId, Collection<SalesQuoteLineViewBean> updateBeanCollection, BigDecimal newPrNumber) throws BaseException {
		Vector errorMessages = new Vector();

		for (SalesQuoteLineViewBean bean : updateBeanCollection) {
			if (bean.getOkDoUpdate().startsWith("true")) {
				Collection inArgs = new Vector();
				inArgs.add(personnelId);

				if (!isBlank(previousScratchPadId)) inArgs.add(previousScratchPadId);
				else inArgs.add("");

				if (!isBlank(bean.getLineItem())) inArgs.add(bean.getLineItem());
				else inArgs.add("");

				if (!isBlank(newPrNumber)) inArgs.add(newPrNumber);
				else inArgs.add("");

				Vector outArgs = new Vector();
				outArgs.add(new Integer(java.sql.Types.NUMERIC));
				outArgs.add(new Integer(java.sql.Types.VARCHAR));
				if (log.isDebugEnabled()) {
					log.debug("PKG_SALES_QUOTE.P_CREATE_SCRATCH_PAD_LINE"+inArgs);
				}
				Vector v = (Vector) factory.doProcedure(connection,"PKG_SALES_QUOTE.P_CREATE_SCRATCH_PAD_LINE", inArgs, outArgs);
				if (v.get(1) != null && !((String) v.get(1)).equalsIgnoreCase("OK") && !((String) v.get(1)).equalsIgnoreCase("")) {
					errorMessages.add(v.get(1));
				}
			}
		}
		return errorMessages;
	}


	private Object[] callCopyMultiMrLine(BigDecimal personnelId, BigDecimal previousScratchPadId, Collection<SalesQuoteLineViewBean> updateBeanCollection) throws BaseException {
		Object[] obj = new Object[2];
		Collection inArgs = new Vector();

		if (!isBlank(previousScratchPadId)) inArgs.add(previousScratchPadId);
		else inArgs.add("");

		String lineItemInput = "";
		if (updateBeanCollection.size() != 0) {
			for (SalesQuoteLineViewBean bean : updateBeanCollection) {
				if (bean.getOkDoUpdate().startsWith("true")) {
					lineItemInput += "|" + bean.getLineItem();
				}
			}
			if (lineItemInput.length() > 0 && lineItemInput.charAt(0) == '|') lineItemInput = lineItemInput.substring(1);
		}
		inArgs.add(lineItemInput);
		inArgs.add(personnelId);

		//inArgs.add(newPrNumber);

		Vector outArgs = new Vector();
		outArgs.add(new Integer(java.sql.Types.NUMERIC));
		outArgs.add(new Integer(java.sql.Types.VARCHAR));
		outArgs.add(new Integer(java.sql.Types.VARCHAR));

		try {
			if (log.isDebugEnabled()) {
				log.debug("PKG_RLI_SALES_ORDER.P_COPY_MULTI_MR_LINE"+inArgs);
			}
			Vector v = (Vector) factory.doProcedure(connection, "PKG_RLI_SALES_ORDER.P_COPY_MULTI_MR_LINE", inArgs, outArgs);
			obj[0] = v.get(0);
			obj[1] = v.get(2);
			return obj;
		} catch (Exception ex) {
			ex.printStackTrace();
			obj[1] = library.getString("generic.error");
			return obj;
		}
	}

	public BigDecimal getNextPrNumberSeq() throws BaseException {
		BigDecimal b = null;
		try {
			if(connection == null) {
				connection = dbManager.getConnection();
			}
			b = new BigDecimal(new SqlManager().getOracleSequence(connection, "pr_seq"));
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbManager.returnConnection(connection);
		}
		return b;
	}

	private Object[] callCreateMrFromQuote(BigDecimal personnelId, BigDecimal previousScratchPadId, Collection<SalesQuoteLineViewBean> updateBeanCollection) throws BaseException {
		Vector errorMessages = new Vector();
		Vector results = new Vector();
		Object[] obj = new Object[2];
		try {

			results = callCreateMrFromQuoteLine(personnelId, previousScratchPadId, updateBeanCollection);
			if (results.get(1) != null && !((String) results.get(1)).equalsIgnoreCase("OK") && !((String) results.get(1)).equalsIgnoreCase(""))
				errorMessages.add(results.get(1));

			obj[0] = results.get(0);
			obj[1] = errorMessages;
			return obj;
		} catch (Exception ex) {
			ex.printStackTrace();
			errorMessages.add(library.getString("generic.error"));
			obj[1] = errorMessages;
			return obj;
		}

	}

	private Vector callCreateMrFromQuoteLine(BigDecimal personnelId, BigDecimal previousScratchPadId, Collection<SalesQuoteLineViewBean> updateBeanCollection) throws BaseException {

		Collection inArgs = new Vector();
		if (!isBlank(previousScratchPadId)) inArgs.add(previousScratchPadId);
		else inArgs.add("");

		String lineItemInput = "";
		if (updateBeanCollection.size() != 0) {
			for (SalesQuoteLineViewBean bean : updateBeanCollection) {
				if (bean.getOkDoUpdate().startsWith("true")) {
					lineItemInput += "|" + bean.getLineItem();
				}
			}
			if (lineItemInput.length() > 0 && lineItemInput.charAt(0) == '|') lineItemInput = lineItemInput.substring(1);
		}

		inArgs.add(lineItemInput);
		inArgs.add(personnelId);

		Vector outArgs = new Vector();
		outArgs.add(new Integer(java.sql.Types.NUMERIC));
		outArgs.add(new Integer(java.sql.Types.VARCHAR));

		if (log.isDebugEnabled()) {
			log.debug("PKG_RLI_SALES_ORDER.P_CREATE_MR_FROM_QUOTE"+inArgs);
		}
		Vector v = (Vector) factory.doProcedure(connection, "PKG_RLI_SALES_ORDER.P_CREATE_MR_FROM_QUOTE", inArgs, outArgs);
		return v;
	}


	private String callSalesQuote(BigDecimal personnelId, SalesQuoteViewBean bean, Date today, String disconnectOrNot) throws BaseException {

		if (bean.getRequestorToProcedure() == null || bean.getRequestorToProcedure().trim().length() == 0) {
			bean.setRequestorToProcedure("0");
		}

		String shipToState = bean.getShipToState();
		if ("NONE".equalsIgnoreCase(bean.getShipToState())) {
			shipToState = "";
		}
		String billToState = bean.getBillToState();
		if ("NONE".equalsIgnoreCase(billToState)) {
			billToState = "";
		}

		Vector inArgs = buildProcedureInput(personnelId, bean.getPrNumber(), bean.getCompanyId(), bean.getCustomerId(), bean.getOpsEntityId(), bean.getOpsCompanyId(), bean.getInventoryGroup(), bean.getRequestorToProcedure(), bean.getFieldSalesRepId(), bean.getSubmittedBy(), bean.getSalesAgentId(), bean.getCustomerServiceRepId(), bean.getPoNumber(), bean.getPaymentTerms(), bean.getQuoteExpireDate(), bean.getPriceGroupId(), bean.getRequestorNameToProcedure(), bean.getRequestorPhoneToProcedure(), bean.getRequestorFaxToProcedure(), bean.getRequestorEmailToProcedure(), bean.getFacilityId(), bean.getAccountSysId(), bean.getChargeType(), bean.getEndUser(), bean.getShipToLocationId(), bean.getShipToCompanyId(), bean.getShipToAddressLine1ToProcedure(), bean.getShipToAddressLine2ToProcedure(), bean.getShipToAddressLine3ToProcedure(), bean.getShipToAddressLine4ToProcedure(), bean.getShipToAddressLine5ToProcedure(), bean.getShipToCity(), shipToState, bean.getShipToZip(), bean.getShipToCountry(), bean.getSpecialInstructions(), today, bean.getBillToLocationId(), bean.getBillToCompanyId(), bean.getBillToAddressLine1(), bean.getBillToAddressLine2(), bean.getBillToAddressLine3(), bean.getBillToAddressLine4(), bean.getBillToAddressLine5(), bean.getBillToCity(), billToState, bean.getBillToZip(), bean.getBillToCountry(), bean.getCurrencyId(), bean.getCarrierAccountId(), bean.getCarrierContact(), bean.getCarrierServiceType(), bean.getIncoTerms(), bean.getInternalNote(), "", "");

		Vector outArgs = new Vector();
		outArgs.add(new Integer(java.sql.Types.VARCHAR));

		try {
			log.debug("PKG_SALES_QUOTE.P_sales_quote"+inArgs);
			Vector v = (Vector) factory.doProcedure(connection,"PKG_SALES_QUOTE.P_sales_quote", inArgs, outArgs);
			String errorMessage = (String) v.get(0);
            log.debug("errorMessage  "+errorMessage);
            return errorMessage;
		} catch (Exception ex) {
			ex.printStackTrace();
			return library.getString("generic.error");
		}
	}

	private String callUpdatePurchaseRequest(BigDecimal personnelId, SalesQuoteViewBean bean, String disconnectOrNot) throws BaseException {
		if (bean.getRequestor() == null) {
			bean.setRequestor(new BigDecimal("0"));
		}

		String shipToState = bean.getShipToState();
		if ("NONE".equalsIgnoreCase(shipToState)) {
			shipToState = "";
		}
		String billToState = bean.getBillToState();
		if ("NONE".equalsIgnoreCase(billToState)) {
			billToState = "";
		}

		Vector inArgs = buildProcedureInput(bean.getCompanyId(), bean.getFacilityId(), bean.getPrNumber(), bean.getRequestor(), bean.getSubmittedDate(), bean.getShipToLocationId(), "", bean.getPoNumber(), "", bean.getEndUser(), bean.getAccountSysId(), bean.getChargeType(), bean.getCustomerId(), bean.getOriginalSalesQuoteId(), bean.getPriceGroupId(), bean.getRequestorName(), bean.getRequestorPhone(), bean.getRequestorFax(), bean.getRequestorEmail(), bean.getShipToCompanyId(), bean.getCustomerServiceRepId(), bean.getPaymentTerms(), bean.getOpsEntityId(), bean.getOpsCompanyId(), bean.getSpecialInstructions(), bean.getCarrierAccountId(), bean.getCarrierContact(), bean.getCarrierServiceType(), bean.getInventoryGroup(), bean.getShipToAddressLine1ToProcedure(), bean.getShipToAddressLine2ToProcedure(), bean.getShipToAddressLine3ToProcedure(), bean.getShipToAddressLine4ToProcedure(), bean.getShipToAddressLine5ToProcedure(), bean.getShipToCity(), shipToState, bean.getShipToZip(), bean.getShipToCountry(), bean.getBillToCompanyId(), bean.getBillToLocationId(), bean.getBillToAddressLine1(), bean.getBillToAddressLine2(), bean.getBillToAddressLine3(), bean.getBillToAddressLine4(), bean.getBillToAddressLine5(), bean.getBillToCity(), billToState, bean.getBillToZip(), bean.getBillToCountry(), bean.getIncoTerms(), bean.getCurrencyId(), bean.getCustomerPoDate(), bean.getInternalNote(), "", bean.getOrderShiptoNote());
		Vector outArgs = new Vector();
		outArgs.add(new Integer(java.sql.Types.VARCHAR));

		Vector inArgs2 = new Vector();
		inArgs2.add("Y");
		inArgs2.add("Y");

		try {
			if (log.isDebugEnabled()) {
				log.debug("PKG_RLI_SALES_ORDER.P_UPDATE_PURCHASE_REQUEST"+inArgs);
			}
			Vector v = (Vector) factory.doProcedure(connection,"PKG_RLI_SALES_ORDER.P_UPDATE_PURCHASE_REQUEST", inArgs, outArgs, inArgs2);
			String errorMessage = (String) v.get(0);

            log.debug("PKG_RLI_SALES_ORDER.P_UPDATE_PURCHASE_REQUEST  "+errorMessage);
            return errorMessage;
		} catch (Exception ex) {
			ex.printStackTrace();
			return library.getString("generic.error");
		}
	}
	
	private Vector doUpdateRequestLineItem(BigDecimal personnelId, SalesQuoteViewBean inputBean, Collection<SalesQuoteLineViewBean> updateBeanCollection) throws BaseException {
		Vector errorMessages = new Vector();
		try {
			String shipToLocationId = this.getShipToLocationId(inputBean.getPrNumber());
			
			inputBean.setShipToLocationId(shipToLocationId);
			
			for (SalesQuoteLineViewBean bean : updateBeanCollection) {
				String error = callUpdateRequestLineItem(personnelId, inputBean, bean);
				if (error != null)
					errorMessages.add(error);
			}
			
		    // TODO: The following codes need to be remove when PKG_RLI_SALES_ORDER.P_UPSERT_REQUEST_LINE_ITEM is updated
			StringBuilder query = new StringBuilder("update request_line_item set ship_to_location_id = '").append(shipToLocationId);
            query.append("' where pr_number = ").append(inputBean.getPrNumber());
            factory.deleteInsertUpdate(query.toString(),connection);
			
			return errorMessages;
		} catch (Exception ex) {
			ex.printStackTrace();
			errorMessages.add(library.getString("generic.error"));
			return errorMessages;
		}
	}
	
	private String getShipToLocationId(BigDecimal prNumber) throws BaseException {
		StringBuilder query = new StringBuilder("select ship_to_location_id from pr where pr_number = ").append(prNumber);
		
		return factory.selectSingleValue(query.toString(), connection);
	}

	private String callUpdateRequestLineItem(BigDecimal personnelId, SalesQuoteViewBean inputBean, SalesQuoteLineViewBean bean) throws BaseException {
		String error = null;
		if (bean.getRequestLineStatus() != null && !"Cancelled".equalsIgnoreCase(bean.getRequestLineStatus()) && bean.getSalesOrder() == null ) {
				String shipComplete = "N";
				String consolidateShipment = "N";
				String taxExempt = "N";
				String dropShipOverride = "N";
				String forceHold = "N";
				String scrap = "n";
				
				if (bean.getShipComplete() != null && bean.getShipComplete().contains("true")) shipComplete = "Y";

				if (bean.getConsolidateShipment() != null && bean.getConsolidateShipment().contains("true")) consolidateShipment = "Y";

				if (bean.getTaxExempt() != null && bean.getTaxExempt().contains("true")) taxExempt = "Y";

				if (bean.getDropShipOverride() != null && bean.getDropShipOverride().contains("true")) dropShipOverride = "Y";
				
				if (bean.getForceHold() != null && bean.getForceHold().contains("true")) forceHold = "Y";
				
				if (bean.getScrap() != null && bean.getScrap().contains("true")) scrap = "y";

				//    	SpecList: specId concatenation using commas
				Vector inArgs = buildProcedureInput(inputBean.getCompanyId(), inputBean.getPrNumber(), bean.getLineItem(), bean.getQuantity(),
									bean.getRequiredDatetime(), inputBean.getPoNumber(), bean.getCustomerPoLine(), "", "",
									bean.getCritical(), bean.getExternalNote(), bean.getDeliveryType(), bean.getCatalogPrice(),
									bean.getCurrencyId(), bean.getRequiredShelfLife(), bean.getPromisedDate(), shipComplete, consolidateShipment,
									bean.getCustomerPartNo(), taxExempt, bean.getDeliveryNote(), bean.getItemId(), bean.getSpecListConcat(),
									bean.getExpectedUnitCost(), bean.getInventoryGroup(), "XXX", bean.getSpecDetailConcat(),
									bean.getSpecCocConcat(), bean.getSpecCoaConcat(), bean.getSpecLibraryConcat(), inputBean.getFacilityId(),
									inputBean.getShipToCompanyId(), inputBean.getShipToLocationId(), inputBean.getOpsEntityId(),
									inputBean.getOpsCompanyId(), dropShipOverride, bean.getInternalNote(), bean.getPurchasingNote(),
									bean.getUnitOfSale(), bean.getUnitOfSaleQuantityPerEach(), bean.getUnitOfSalePrice(), 
									bean.getShippingReference(), "Y", forceHold, bean.getForceHoldComment(), scrap);

				Vector outArgs = new Vector();
				outArgs.add(new Integer(java.sql.Types.VARCHAR));

				if (log.isDebugEnabled()) {
						log.debug("PKG_RLI_SALES_ORDER.P_UPSERT_REQUEST_LINE_ITEM"+inArgs);
				}
				Vector v = (Vector) factory.doProcedure(connection,"PKG_RLI_SALES_ORDER.P_UPSERT_REQUEST_LINE_ITEM", inArgs, outArgs);
				if (v.get(0) != null && !((String) v.get(0)).equalsIgnoreCase("OK") && !((String) v.get(0)).equalsIgnoreCase("")) {
						error = (String)v.get(0);
						
						if (log.isDebugEnabled()) {
							log.debug(v.get(0));
						}
				}
		}
		return error;
			
	}

	public Vector updateSalesQuoteLine(BigDecimal personnelId, SalesQuoteViewBean inputBean, Collection<SalesQuoteLineViewBean> updateBeanCollection, String action, String saveAll) throws BaseException {
		Vector messages = new Vector();
		String errorMsg;
		for (SalesQuoteLineViewBean bean : updateBeanCollection) {

			if (saveAll.equalsIgnoreCase("Y") || (bean.getLineItem().equals(inputBean.getSelectedRowId()))) {
				errorMsg = null;
				errorMsg = callSalesQuoteLine(personnelId, inputBean, bean, action);
				if (errorMsg != null && !errorMsg.equalsIgnoreCase("OK")) {
					messages.add(errorMsg);
				}
			}
		}
		return messages;
	}


	private String callSalesQuoteLine(BigDecimal personnelId, SalesQuoteViewBean inputBean, SalesQuoteLineViewBean bean, String action) throws BaseException {
		String shipComplete = null;
		String consolidateShipment = null;
		String taxExempt = null;
		String critical = null;
		/*TODO critical cannot be NONE it is a single char*/
		if (bean.getShipComplete() != null && bean.getShipComplete().contains("true")) {
			shipComplete = "Y";
		} else {
			shipComplete = "N";
		}

		if (bean.getConsolidateShipment() != null && bean.getConsolidateShipment().contains("true")) {
			consolidateShipment = "Y";
		} else {
			consolidateShipment = "N";
		}

		if (bean.getTaxExempt() != null && bean.getTaxExempt().contains("true")) {
			taxExempt = "Y";
		} else {
			taxExempt = "N";
		}

		Vector inArgs = buildProcedureInput(personnelId, inputBean.getPrNumber(), bean.getLineItem(), inputBean.getCompanyId(), bean.getApplication(), bean.getCatalogCompanyId(), bean.getCatalogId(), "", bean.getPartGroupNo(), bean.getQuantity(), bean.getRequiredDatetime(), bean.getCatalogPrice(), bean.getCurrencyId(), bean.getRequiredShelfLife(), shipComplete, consolidateShipment, bean.getPromisedDate(), bean.getCritical(), bean.getExternalNote(), bean.getCustomerPartNo(), bean.getCustomerPoLine(), action, taxExempt, bean.getDeliveryNote(), bean.getItemId(), bean.getSpecListConcat(), bean.getDeliveryType(), bean.getExpectedUnitCost(), bean.getInventoryGroup(), bean.getSpecDetailConcat(), bean.getSpecCocConcat(), bean.getSpecCoaConcat(), bean.getSpecLibraryConcat(), bean.getInternalNote(), bean.getPurchasingNote(), bean.getUnitOfSale(), bean.getUnitOfSaleQuantityPerEach(), bean.getUnitOfSalePrice());

		Collection outArgs = new Vector(1);
		outArgs.add(new Integer(java.sql.Types.VARCHAR));
		try {
			if (log.isDebugEnabled()) {
				log.debug("PKG_SALES_QUOTE.P_sales_quote_line"+inArgs);
			}
			Collection coll = factory.doProcedure(connection,"PKG_SALES_QUOTE.P_sales_quote_line", inArgs, outArgs);
			String errorMessage = "";
			errorMessage = (String) ((Vector) coll).get(0);
            log.info("errorMessage from PKG_SALES_QUOTE.P_sales_quote_line  "+errorMessage+"");
            return errorMessage;
		} catch (Exception e) {
			e.printStackTrace();
			return library.getString("generic.error");
		}
	}

	public Vector deleteRliLine(SalesQuoteViewBean inputBean, Collection<SalesQuoteLineViewBean> updateBeanCollection) throws BaseException {
		Vector messages = new Vector();
		String errorMsg;
		try {
			for (SalesQuoteLineViewBean bean : updateBeanCollection) {

				if (bean.getLineItem().equals(inputBean.getSelectedRowId())) {
					errorMsg = null;
					errorMsg = callDeleteRliLine(inputBean.getPrNumber(), bean);
					if (errorMsg != null && !errorMsg.equalsIgnoreCase("OK") && !errorMsg.equalsIgnoreCase("")) {
						messages.add(errorMsg);
					}
				}
			}
			return messages;
		} catch (Exception ex) {
			ex.printStackTrace();
			messages.add(library.getString("generic.error"));
			return messages;
		}
	}

	private String callDeleteRliLine(BigDecimal scratchPadId, SalesQuoteLineViewBean bean) throws BaseException {
		Collection inArgs = new Vector();

		if (!isBlank(scratchPadId)) {
			inArgs.add(scratchPadId);
		} else {
			inArgs.add("");
		}

		if (!isBlank(bean.getLineItem())) {
			inArgs.add(bean.getLineItem());
		} else {
			inArgs.add("");
		}

		Collection outArgs = new Vector(1);
		outArgs.add(new Integer(java.sql.Types.VARCHAR));
		try {
			if (log.isDebugEnabled()) {
				log.debug("P_DELETE_RLI_LINE"+inArgs);
			}
			Collection coll = factory.doProcedure(connection,"P_DELETE_RLI_LINE", inArgs, outArgs);
			return (String) ((Vector) coll).get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			return library.getString("generic.error");
		}
	}

	private String callDeleteQuote(BigDecimal personnelId, BigDecimal scratchPadId) throws BaseException {
		Collection inArgs = new Vector();

		inArgs.add(personnelId);

		if (!isBlank(scratchPadId)) {
			inArgs.add(scratchPadId);
		} else {
			inArgs.add("");
		}

		Collection outArgs = new Vector(1);
		outArgs.add(new Integer(java.sql.Types.VARCHAR));
		try {
			if (log.isDebugEnabled()) {
				log.debug("PKG_SALES_QUOTE.P_DELETE_SALES_QUOTE"+inArgs);
			}
			Collection coll = factory.doProcedure(connection,"PKG_SALES_QUOTE.P_DELETE_SALES_QUOTE", inArgs, outArgs);
			return (String) ((Vector) coll).get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			return library.getString("generic.error");
		}
	}

	private String callDeleteMR(BigDecimal scratchPadId) throws BaseException {
		Collection inArgs = new Vector();

		if (!isBlank(scratchPadId)) {
			inArgs.add(scratchPadId);
		} else {
			inArgs.add("");
		}

		Collection outArgs = new Vector(1);
		outArgs.add(new Integer(java.sql.Types.VARCHAR));
		try {
			if (log.isDebugEnabled()) {
				log.debug("P_DELETE_MR"+inArgs);
			}
			Collection coll = factory.doProcedure(connection,"P_DELETE_MR", inArgs, outArgs);
			return (String) ((Vector) coll).get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			return library.getString("generic.error");
		}
	}

	private String callCancelQuote(BigDecimal prNumber) throws BaseException {
		Collection inArgs = new Vector();

		inArgs.add(prNumber);

		Collection outArgs = new Vector(1);
		outArgs.add(new Integer(java.sql.Types.VARCHAR));
		if (log.isDebugEnabled()) {
			log.debug("p_cancel_quote"+inArgs);
		}
		try {
			Collection coll = factory.doProcedure(connection,"Pkg_sales_quote.p_cancel_quote", inArgs, outArgs);
			return (String) ((Vector) coll).get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			return library.getString("generic.error");
		}
	}

	private String callConfirmQuote(SalesQuoteViewBean inputBean) throws BaseException {

		Vector inArgs = buildProcedureInput(inputBean.getPrNumber());

		Collection outArgs = new Vector(1);
		outArgs.add(new Integer(java.sql.Types.VARCHAR));

		try {
			Vector coll = (Vector) factory.doProcedure(connection, "PKG_SALES_QUOTE.P_SALES_QUOTE_CONFIRM", inArgs, outArgs);

			return (String) (coll).get(0);

		} catch (Exception ex) {
			ex.printStackTrace();
			return library.getString("generic.error");
		}

	}

	private String callConfirmPurchaseRequest(BigDecimal personnelId, SalesQuoteViewBean inputBean) throws BaseException {
		Vector inArgs = buildProcedureInput(inputBean.getCompanyId(), inputBean.getPrNumber(), personnelId, inputBean.getDateDelivered());
		Collection outArgs = new Vector(1);
		outArgs.add(new Integer(java.sql.Types.VARCHAR));
		try {
			Vector coll = (Vector) factory.doProcedure(connection,"PKG_RLI_SALES_ORDER.P_CONFIRM_PURCHASE_REQUEST", inArgs, outArgs);
			if (log.isDebugEnabled()) {
				log.debug((coll).get(0));
			}
			//move catalog add request to next queue
			EngEvalProcess engEvalProcess = new EngEvalProcess(this.getClient(),this.getLocale(),urlRoot);
			engEvalProcess.setFactoryConnection(factory,connection);
			engEvalProcess.setNextStatusAction("Submit");
			engEvalProcess.advanceDistributionCatalogAddRequest(inputBean.getPrNumber());
			return (String) (coll).get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			return library.getString("generic.error");
		}
	}

	private Collection getSalesQuoteResult(BigDecimal scratchPadId) throws BaseException {
		Vector<SalesQuoteViewBean> salesQuoteColl = null;

		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("prNumber", SearchCriterion.EQUALS, scratchPadId.toString());

		SortCriteria sort = new SortCriteria();

		factory.setBeanObject(new SalesQuoteViewBean());
		salesQuoteColl = (Vector<SalesQuoteViewBean>) factory.select(criteria, sort, connection, "sales_quote_view");
		return salesQuoteColl;
	}


	private Collection getSalesQuoteLineResult(BigDecimal scratchPadId, String lineItem) throws BaseException {
        String searchQuery = "select * from Sales_quote_line_view where pr_number = "+scratchPadId.toString()+"";
		//SearchCriteria criteria = new SearchCriteria();
		//criteria.addCriterion("prNumber", SearchCriterion.EQUALS, scratchPadId.toString());
		
		if(!"all".equals(lineItem))
            searchQuery += " and to_number(line_item) >= "+lineItem+"";
            //criteria.addCriterion("lineItem", SearchCriterion.GREATER_THAN_OR_EQUAL_TO, lineItem);

		//SortCriteria sort = new SortCriteria();
        searchQuery += " order by to_number(line_item) asc";
        //sort.addCriterion("toNumber(lineItem)");

        factory.setBeanObject(new SalesQuoteLineViewBean());
		return factory.selectQuery(searchQuery, connection);
	}

	private Vector callLineItemAllocate(Collection<SalesQuoteLineViewBean> updateBeanCollection) throws BaseException {
		Vector errMsgs = new Vector();
		int i = 0;
		for (SalesQuoteLineViewBean bean : updateBeanCollection) {
			if (bean.getRequiredShelfLife() == null) {
				i = 0;
			} else {
				i = bean.getRequiredShelfLife().compareTo(BigDecimal.ZERO);
			}
			if (i > 0) {
				String errorMsg = allocateLineDirect(bean);
				errMsgs.add(errorMsg);
			}
		}
		return errMsgs;
	}

	private String allocateLineDirect(SalesQuoteLineViewBean bean) throws BaseException {
		String errorMsg = getProcError(connection, buildProcedureInput(bean.getCompanyId(), bean.getPrNumber(), bean.getLineItem()), new Vector(), "Pkg_rli_sales_order.P_LINE_ITEM_ALLOCATE");
		return errorMsg;
	}

	private String callCancelRliLine(BigDecimal personnelId, SalesQuoteViewBean inputBean, Collection<SalesQuoteLineViewBean> updateBeanCollection) throws BaseException {
		String errorMsg = null;
		for (SalesQuoteLineViewBean bean : updateBeanCollection) {
			if (bean.getLineItem().equals(inputBean.getSelectedRowId())) {
				errorMsg = cancelRLIline(personnelId, inputBean, bean);
			}
		}
		return errorMsg;
	}

	private String cancelRLIline(BigDecimal personnelId, SalesQuoteViewBean inputBean, SalesQuoteLineViewBean bean) throws BaseException {
		Vector inArgs = buildProcedureInput(bean.getPrNumber(), bean.getLineItem(), personnelId, inputBean.getCancelMRLineReason());

		Collection outArgs = new Vector(1);
		outArgs.add(new Integer(java.sql.Types.VARCHAR));

		Collection inArgs1 = new Vector(1);
		inArgs1.add("rqcancel");

		//log.debug("P_CONFIRM_PURCHASE_REQUEST"+inArgs);
		try {
			Vector coll = (Vector) factory.doProcedure(connection,"PKG_RLI_SALES_ORDER.P_CANCEL_RLI_LINE", inArgs, outArgs, inArgs1);
			return (String) (coll).get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			return library.getString("generic.error");
		}
	}

	private Vector callReleaseMR(Collection<SalesQuoteLineViewBean> updateBeanCollection, PersonnelBean personnelBean) throws BaseException {
		Vector errMsgs = new Vector();
		for (SalesQuoteLineViewBean bean : updateBeanCollection) {
			if ("true".equals(bean.getOkDoUpdate())) {
				String errMsg = releaseMRLine(bean,personnelBean);
				if (errMsg != null && !"".equals(errMsg)) errMsgs.add(errMsg);
			}
		}
		return errMsgs;
	}

	private String callReleaseMRLine(SalesQuoteViewBean inputBean, Collection<SalesQuoteLineViewBean> updateBeanCollection, PersonnelBean personnelBean) throws BaseException {
		Vector errMsgs = new Vector();
		for (SalesQuoteLineViewBean bean : updateBeanCollection) {
			if (bean.getLineItem().equals(inputBean.getSelectedRowId())) {
				String errorMsg = releaseMRLine(bean,personnelBean);
				errMsgs.add(errorMsg);
			}
		}
		return (String) errMsgs.get(0);
	}

	private String releaseMRLine(SalesQuoteLineViewBean bean, PersonnelBean personnelBean) throws BaseException {
		Vector inArgs = buildProcedureInput(bean.getCompanyId(), bean.getPrNumber(),bean.getLineItem(),personnelBean.getPersonnelId());

		Collection outArgs = new Vector(1);
		outArgs.add(new Integer(java.sql.Types.VARCHAR));

		try {
			Vector coll = (Vector) factory.doProcedure(connection,"pkg_rli_sales_order.P_RELEASE_PURCHASE_REQUEST", inArgs, outArgs);
			return (String) (coll).get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			return library.getString("generic.error");
		}
	}  //end of method

	private void sendNotification(PersonnelBean personnelBean, SalesQuoteViewBean inputBean, Collection<SalesQuoteLineViewBean> updateBeanCollection) throws
	BaseException {
		try {
			PersonnelBeanFactory factory = new PersonnelBeanFactory(dbManager);
			Collection c = null;
			HashSet<String> opsmap = new HashSet();
			opsmap.add(inputBean.getOpsEntityId());

			c = factory.selectDistinctUserByOpsPermission("MarginException",opsmap.toArray());
			
			String subject = library.getString("label.mrwithmarginoutsidelimitsconfirmed").replace("{0}", inputBean.getPrNumber().toString());
			
			StringBuilder msg = new StringBuilder("");
			for (SalesQuoteLineViewBean bean: updateBeanCollection) {
				if (!"Cancelled".equals(bean.getRequestLineStatus()) && !"Canceled".equals(bean.getRequestLineStatus()) && bean.getCatalogPrice() != null && bean.getCatalogPrice().compareTo(BigDecimal.ZERO) != 0) {
				
					if (bean.getExpectedUnitCost() != null && !"".equals(bean.getExpectedUnitCost()) && bean.getExpectedUnitCost().compareTo(BigDecimal.ZERO) != 0) {
			   			BigDecimal margin = (bean.getCatalogPrice().subtract(bean.getExpectedUnitCost())).multiply(new BigDecimal(100)).divide(bean.getCatalogPrice(),1,BigDecimal.ROUND_HALF_UP);
				   		if (margin.compareTo(bean.getMaximumGrossMargin()) > 0 || margin.compareTo(bean.getMinimumGrossMargin()) < 0) {
				   			if (bean.getPartDescription() == null)
				   				msg.append(library.getString("label.mrwithmarginforoutsidelimitsconfirmed").replace("{0}", inputBean.getPrNumber().toString()+"-"+bean.getLineItem()).replace("{1}", bean.getCatPartNo()).replace("{2}", margin.toString())).append("\n\n");
				   			else
				   				msg.append(library.getString("label.mrwithmarginforoutsidelimitsconfirmed").replace("{0}", inputBean.getPrNumber().toString()+"-"+bean.getLineItem()).replace("{1}", bean.getCatPartNo()+"-"+bean.getPartDescription()).replace("{2}", margin.toString())).append("\n\n");
				   		}
			   		}				 
			   }  
			}
			
			ResourceLibrary resource = new ResourceLibrary("label");
			String hostUrl = resource.getString("label.hosturl");

			
			msg.append("\n").append(library.getString("label.customer")).append(": ").append(inputBean.getCustomerName());
			msg.append("\n").append(library.getString("label.csr")).append(": ").append(inputBean.getCustomerServiceRepName());
			msg.append("\n").append(library.getString("label.mr")).append(": ").append(hostUrl).append("/tcmIS/distribution/scratchpadmain.do?uAction=searchScratchPadId&fromEmail=Y&scratchPadId=").append(inputBean.getPrNumber().toString());
			
			String message = msg.toString();
			
			if(log.isDebugEnabled()) {
				log.debug(message);	
			}
			
			String[] to = new String[c.size()];
			Iterator it = c.iterator();
			for (int i = 0; it.hasNext(); i++) {
				PersonnelBean b = (PersonnelBean) it.next();
				to[i] = b.getEmail();
			}

			String[] cc = new String[0];
			if(log.isDebugEnabled()) {
				for(int i=0; i<to.length;i++) {
					log.debug("Sending email to:" + to[i]);
				}
			}  

			if (to.length > 0)
				MailProcess.sendEmail(to, cc, subject, message.toString(), true);

		}
		catch (Exception e) {
			log.error("Error sending notification:" + e.getMessage(), e);
		}
	}

} //end of class
