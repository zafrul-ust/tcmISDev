package com.tcmis.internal.distribution.process;

//import java.io.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.factory.PersonnelBeanFactory;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.CustomerReturnFeeBean;
import com.tcmis.internal.distribution.beans.CustomerReturnRcptShpViewBean;
import com.tcmis.internal.distribution.beans.CustomerReturnReasonBean;
import com.tcmis.internal.distribution.beans.CustomerReturnRequestBean;
import com.tcmis.internal.distribution.beans.CustomerReturnRequestInputBean;
import com.tcmis.internal.distribution.beans.CustomerReturnRequestViewBean;
import com.tcmis.internal.distribution.beans.MrAddChargeViewBean;
import com.tcmis.internal.distribution.beans.NewFeesItemsViewBean;
import com.tcmis.internal.distribution.beans.VvReturnReasonBean;
import com.tcmis.internal.distribution.factory.CustomerReturnFeeBeanFactory;
import com.tcmis.internal.distribution.factory.CustomerReturnReasonBeanFactory;
import com.tcmis.internal.distribution.factory.CustomerReturnRequestBeanFactory;

/******************************************************************************
 * Process used by CustomerReturnRequestAction
 * 
 * @version 1.0
 *****************************************************************************/

public class CustomerReturnRequestProcess extends GenericProcess {
	Log log = LogFactory.getLog(this.getClass());
	ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());

	public CustomerReturnRequestProcess(String client) {
		super(client);
	}

	@SuppressWarnings("unchecked")
	public Collection<VvReturnReasonBean> getReasonList() throws BaseException,
	Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory customerReturnReasonFactory = new GenericSqlFactory(
				dbManager, new VvReturnReasonBean());
		SortCriteria sortCcriteria = new SortCriteria();
		sortCcriteria.setSortAscending(true);
		sortCcriteria.addCriterion("reasonDescription");
		Collection<VvReturnReasonBean> c = customerReturnReasonFactory.select(
				null, sortCcriteria, "vv_return_reason");
		return c;
	}

	public BigDecimal addCustomerReturnRequest(
			CustomerReturnRequestInputBean inputBean,
			PersonnelBean personnelBean) throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		CustomerReturnRequestBeanFactory factory = new CustomerReturnRequestBeanFactory(
				dbManager);
		CustomerReturnRequestBean dataBean = new CustomerReturnRequestBean();
		BigDecimal rmaId;
		dataBean.setPrNumber(new BigDecimal(inputBean.getPrNumber()));

		dataBean.setLineItem(inputBean.getLineItem());

		dataBean.setRmaStatus("Draft");

		dataBean.setCompanyId(inputBean.getCompanyId());

		dataBean.setReplacementCatPartNo(inputBean.getReplacementCatPartNo());

		dataBean.setCatalogCompanyId(inputBean.getCatalogCompanyId());

		dataBean.setCatalogId("Global");

		dataBean.setOpsEntityId(inputBean.getOpsEntityId());

		dataBean.setCustomerServiceRepId(new BigDecimal(personnelBean.getPersonnelId()));

		dataBean.setRequestStartDate(new java.util.Date());

		dataBean.setReturnMaterial("Y");

		dataBean.setWantReplacementMaterial("Y");

		//dataBean.setCustomerServiceRepId(inputBean.getCsrId());

		dataBean.setOriginalUnitPrice(inputBean.getOriginalUnitPrice());

		rmaId = factory.insertCustomerRequest(dataBean);

		return rmaId;
	}

	public String updateCustomerReturnRequest(CustomerReturnRequestInputBean inputBean,
			PersonnelBean personnelBean) throws BaseException, Exception {

		DbManager dbManager = new DbManager(getClient(), getLocale());

		CustomerReturnRequestBeanFactory factory = new CustomerReturnRequestBeanFactory(dbManager);

		String errorMsg = "";
		try {
			//		    	SpecList: specId concatenation using commas
			Vector inArgs = buildProcedureInput(inputBean.getRmaId(),inputBean.getReturnQuantityRequested(),inputBean.getNewUnitPrice(),inputBean.getReturnMaterial(),
					inputBean.getWantReplacement(),inputBean.getRequestorName(),inputBean.getRequestorEmail(),inputBean.getRequestorPhone(),
					inputBean.getCustomerServiceRepId(),inputBean.getReturnNotes(),inputBean.getReplacementItemId(),
					inputBean.getSpecListConcat(),inputBean.getSpecLibraryList(),
					inputBean.getDetailConcat(),inputBean.getCocConcat(),inputBean.getCoaConcat(),inputBean.getReplacementRequiredDatetime(),
					inputBean.getReplacementPromiseDate(),inputBean.getCorrectItemShipped());

			Vector outArgs = new Vector();
			outArgs.add( new Integer(java.sql.Types.VARCHAR) );

			GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
			if (log.isDebugEnabled()) {
				log.debug("pkg_customer_return.p_update_request"+inArgs);
			}
			Vector v = (Vector) procFactory.doProcedure("pkg_customer_return.p_update_request", inArgs, outArgs);
			errorMsg = (String) v.get(0);

			if ("submit".equalsIgnoreCase(inputBean.getAction()) ) {
				GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
				//TODO update customer_return_request set rma_Status = 'Pending Approval' where customer_rma_id = inputBean.getRmaId()
				String query = "update customer_return_request set rma_Status = 'Pending Approval' "+
				" where customer_rma_id = "+inputBean.getRmaId().toString()+" ";
				genericSqlFactory.deleteInsertUpdate(query);
				//update
				sendNotificationMail(inputBean, personnelBean);
			}

			return errorMsg;
		}catch(Exception ex){
			errorMsg = library.getString("generic.error");
			return errorMsg;
		}

		/*		CustomerReturnRequestBean dataBean = new CustomerReturnRequestBean();

		dataBean.setRmaStatus(inputBean.getRmaStatus());

		dataBean.setReturnRequestorName(inputBean.getRequestorName());

		dataBean.setReturnRequestorEmail(inputBean.getRequestorEmail());

		dataBean.setReturnRequestorPhone(inputBean.getRequestorPhone());

		dataBean.setQuantityReturnRequested(inputBean
				.getReturnQuantityRequested());

		dataBean.setWantReplacementMaterial(inputBean.getWantReplacement());

		dataBean.setReturnMaterial(inputBean.getReturnMaterial());

		dataBean.setReplacementCatPartNo(inputBean.getReplacementCartPN());

		dataBean.setNewUnitPrice(inputBean.getNewUnitPrice());

		dataBean.setCustomerRmaId(inputBean.getRmaId());

		dataBean.setQuantityReturnAuthorized(inputBean
				.getQuantityReturnAuthorized());

		dataBean.setRejectionComment(inputBean.getDenialComments());

		dataBean.setReplacementRequiredDatetime(inputBean.getReplacementRequiredDatetime());

		dataBean.setReplacementPromiseDate(inputBean.getReplacementPromiseDate());


		if ("approve".equalsIgnoreCase(inputBean.getAction())) {
			dataBean.setApproverId(new BigDecimal(personnelBean
					.getPersonnelId()));
			dataBean.setApprovalDate(new java.util.Date());

		}
		factory.update(dataBean);
		 */

	}

	@SuppressWarnings("unchecked")
	public CustomerReturnRequestViewBean getCustomerReturnRequestData(
			CustomerReturnRequestInputBean inputBean) throws BaseException,
			Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory customerReturnRequestFactory = new GenericSqlFactory(
				dbManager, new CustomerReturnRequestViewBean());
		SearchCriteria searchCriteria = new SearchCriteria();
		if (null != inputBean.getRmaId()) {
			searchCriteria.addCriterion("customerRmaId",
					SearchCriterion.EQUALS, inputBean.getRmaId().toString());
		}
		Collection<CustomerReturnRequestViewBean> c = customerReturnRequestFactory
		.select(searchCriteria, null, "CUSTOMER_RETURN_REQUEST_VIEW");

		if (c.size() != 0)
			return ((Vector<CustomerReturnRequestViewBean>) c).get(0);
		else
			return null;

	}

	public void addReturnReasons(Collection<VvReturnReasonBean> beans,
			CustomerReturnRequestInputBean inputBean) throws BaseException,
			Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		CustomerReturnReasonBeanFactory factory = new CustomerReturnReasonBeanFactory(
				dbManager);
		CustomerReturnReasonBean returnReasonBean = new CustomerReturnReasonBean();

		SearchCriteria criteria = new SearchCriteria();
		if (null != inputBean.getRmaId()) {
			criteria.addCriterion("customerRmaId",
					SearchCriterion.EQUALS, inputBean.getRmaId()
					.toString());
		}
		factory.delete(criteria);

		Vector v = new Vector();

		if (beans != null && !beans.isEmpty()) {
			if (StringHandler.isBlankString(inputBean.getReasonsDeleteOnly())) {
				for (VvReturnReasonBean bean : beans) {
					if(!v.contains(bean.getReturnReasonId())) {
						returnReasonBean.setCustomerRmaId(inputBean.getRmaId());
						if (!StringHandler.isBlankString(bean.getReturnReasonId())) {
							returnReasonBean.setReturnReasonId(bean.getReturnReasonId());
						}
						factory.insert(returnReasonBean);
						v.add(bean.getReturnReasonId());
					}
				}
			}
		}
	}

	public void updateCustomerReturnRcptShipped(Collection<CustomerReturnRcptShpViewBean> beans,
			CustomerReturnRequestInputBean inputBean) throws BaseException,
			Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);

		String query = "delete from CUSTOMER_RETURN_RCPT_SHIPPED where CUSTOMER_RMA_ID = " + inputBean.getRmaId();
		genericSqlFactory.deleteInsertUpdate(query);

		if (beans != null && !beans.isEmpty() && "N".equals(inputBean.getCorrectItemShipped())) {
			if (StringHandler.isBlankString(inputBean.getReasonsDeleteOnly())) {
				for (CustomerReturnRcptShpViewBean bean : beans) {
					query = "insert into CUSTOMER_RETURN_RCPT_SHIPPED (CUSTOMER_RMA_ID,ITEM_ID,RECEIPT_ID,QUANTITY)" +
					"values ("+inputBean.getRmaId()+","+bean.getItemId()+","+bean.getReceiptId()+","+bean.getQuantity()+")";

					genericSqlFactory.deleteInsertUpdate(query);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public Collection<VvReturnReasonBean> getAddedCustomerReasons(
			CustomerReturnRequestInputBean inputBean) throws BaseException,
			Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());

		GenericSqlFactory customerReturnReasonFactory = new GenericSqlFactory(
				dbManager, new VvReturnReasonBean());

		Collection<VvReturnReasonBean> c = null;
		if (null != inputBean.getRmaId()) {

			c = customerReturnReasonFactory
			.selectQuery("select a.RETURN_REASON_ID, a.REASON_LABEL from vv_return_reason a , "
					+ " customer_return_reason b where a.RETURN_REASON_ID = b.RETURN_REASON_ID "
					+ " and b.CUSTOMER_RMA_ID ="
					+ inputBean.getRmaId().toString());
		}
		return c;
	}

	public Collection<CustomerReturnRcptShpViewBean> getCustomerReturnRcptShpViewColl(
			CustomerReturnRequestInputBean inputBean) throws BaseException,
			Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());

		GenericSqlFactory customerReturnReasonFactory = new GenericSqlFactory(dbManager, new CustomerReturnRcptShpViewBean());

		Collection<CustomerReturnRcptShpViewBean> c = null;
		if (null != inputBean.getRmaId()) {

			c = customerReturnReasonFactory.selectQuery("select * from CUSTOMER_RETURN_RCPT_SHP_VIEW"
					+ " where CUSTOMER_RMA_ID = "+ inputBean.getRmaId().toString());
		}
		return c;
	}

	@SuppressWarnings("unchecked")
	private void sendNotificationMail(CustomerReturnRequestInputBean inputBean,
			PersonnelBean personnelBean) {
		try {

			DbManager dbManager = new DbManager(getClient(), getLocale());
			PersonnelBeanFactory factory = new PersonnelBeanFactory(dbManager);
			String name = (null != personnelBean.getFullName()) ? personnelBean
					.getFullName() : personnelBean.getLastName() + ","
					+ personnelBean.getFirstName();
					Collection c = null;

					String subject = null;

					String message = null;

					String pre = "New Customer Return Request ";
					
					ResourceLibrary resource = new ResourceLibrary("label");
	                String hostUrl = resource.getString("label.hosturl");

					if ("submit".equalsIgnoreCase(inputBean.getAction())) {
						c = factory
						.selectDistinctUserByInventoryPermission("customerReturnApproval",inputBean.getInventoryGroup());
						subject = pre + " Requested by " + inputBean.getCsrName()
						+ " is Pending Approval";
						message = pre
						+ "\n\nRequested by "
						+ inputBean.getCsrName()
						+ " - has been added and is waiting approval. \n\n"
						+ "Please click on the link below.\n\n"
						+ hostUrl + "/tcmIS/distribution/customerreturnrequest.do?action=search&rmaId="
						+ inputBean.getRmaId() + "&lineItem="
						+ inputBean.getLineItem() + "&prNumber="
						+ inputBean.getPrNumber();
					}
					// change to the requester email
					else if ("approve".equalsIgnoreCase(inputBean.getAction())) {
						c = factory.selectDistinctEmail(new SearchCriteria(
								"personnelId", SearchCriterion.EQUALS, ""
								+ inputBean.getCustomerServiceRepId()));
						subject = pre + inputBean.getCsrName() + " Approved";
						message = pre
						+ " Requested by "
						+ inputBean.getCsrName()
						+ "- has been Approved by "
						+ name
						+ ". \n\n"
						+ "Please click on the link below.\n\n"
						+ hostUrl + "/tcmIS/distribution/customerreturnrequest.do?action=search&rmaId="
						+ inputBean.getRmaId() + "&lineItem="
						+ inputBean.getLineItem() + "&prNumber="
						+ inputBean.getPrNumber();
					}
					// change to the requester email
					else if ("deny".equalsIgnoreCase(inputBean.getAction())) {
						c = factory.selectDistinctEmail(new SearchCriteria(
								"personnelId", SearchCriterion.EQUALS, ""
								+ personnelBean.getPersonnelId()));
						subject = pre + inputBean.getCsrName() + " Rejected";
						message = pre
						+ " Requested by "
						+ inputBean.getCsrName()
						+ "- has been Denied by "
						+ name
						+ ". \n\n"
						+ "Please click on the link below.\n\n"
						+ hostUrl + "/tcmIS/distribution/customerreturnrequest.do?action=search&rmaId="
						+ inputBean.getRmaId() + "&lineItem="
						+ inputBean.getLineItem() + "&prNumber="
						+ inputBean.getPrNumber();
					}
					String[] to = new String[c.size()];
					Iterator it = c.iterator();
					for (int i = 0; it.hasNext(); i++) {
						PersonnelBean b = (PersonnelBean) it.next();
						to[i] = b.getEmail();
					}
					String[] cc = new String[0];
					if (log.isDebugEnabled()) {
						for (int i = 0; i < to.length; i++) {
							log.debug("Sending email to:" + to[i]);
						}
					}

					if (to.length > 0) {
						MailProcess.sendEmail(to, cc, subject, message, true);
					}
		} catch (Exception e) {
			log.error("Error sending email.", e);
		}
	}

	@SuppressWarnings("unchecked")
	public Collection<MrAddChargeViewBean> getOriginalFeeHeaderChargeList(
			CustomerReturnRequestInputBean inputBean) throws BaseException,
			Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());

		GenericSqlFactory factory = new GenericSqlFactory(
				dbManager, new MrAddChargeViewBean());
		Collection<MrAddChargeViewBean> c =  null;
		StringBuilder query= new StringBuilder("select * from table (pkg_rli_sales_order.fx_additional_charge(");
		query.append( (null!=inputBean.getCompanyId()?"'"+inputBean.getCompanyId()+"'":null) +"," +
				inputBean.getPrNumber()  + "))");

		c = factory.selectQuery(query.toString());

		return c;



	}

	@SuppressWarnings("unchecked")
	public Collection<MrAddChargeViewBean> getOriginalFeeLineChargeList(
			CustomerReturnRequestInputBean inputBean) throws BaseException,
			Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());

		GenericSqlFactory factory = new GenericSqlFactory(
				dbManager, new MrAddChargeViewBean());
		Collection<MrAddChargeViewBean> c =  null;
		StringBuilder query= new StringBuilder("select * from table (PKG_CUSTOMER_RETURN.FX_ADDITIONAL_CHARGE(");
		query.append( (null!=inputBean.getCompanyId()?"'"+inputBean.getCompanyId()+"'":null)).append("," ).append(
				inputBean.getPrNumber() ).append(",'" ).append(
						inputBean.getLineItem() ).append( "'))");

		c = factory.selectQuery(query.toString());

		return c;
	}

	@SuppressWarnings("unchecked")
	public Collection<NewFeesItemsViewBean> getNewFeesItemList()
	throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());

		GenericSqlFactory newFeesItemFactory = new GenericSqlFactory(dbManager,
				new NewFeesItemsViewBean());

		Collection<NewFeesItemsViewBean> c = null;

		c = newFeesItemFactory
		.selectQuery("select item.item_id,item.item_desc from item, vv_item_type it where item.item_type = it.item_type and it.ma_add_charge = 'y' order by item_desc");

		return c;
	}

	public void addCustomerReturnFees(Collection<CustomerReturnFeeBean> beans,
			CustomerReturnRequestInputBean inputBean) throws BaseException,
			Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		CustomerReturnFeeBeanFactory factory = new CustomerReturnFeeBeanFactory(
				dbManager);
		//		CustomerReturnFeeBean returnFeeBean = new CustomerReturnFeeBean();

		// For removal only delete and dont insert

		SearchCriteria criteria = new SearchCriteria();

		if (null != inputBean.getRmaId()) {

			criteria.addCriterion("customerRmaId",
					SearchCriterion.EQUALS, inputBean.getRmaId()
					.toString());
		}
		// delete all of same rma from table first.
		factory.delete(criteria);


		if (beans != null && !beans.isEmpty()) {

			BigDecimal customerRmaId = inputBean.getRmaId();
			BigDecimal itemId = new BigDecimal("148742");
			// Only insert data if this is add and the price is not null.
			if (StringHandler.isBlankString(inputBean.getNewFeesDeleteOnly())) {
				for (CustomerReturnFeeBean bean : beans) {

					////					if (null != bean.getNewprice()) {
					////						returnFeeBean.setReturnPrice(bean.getNewprice());
					////
					////						if (null != bean.getItemId()) {
					////							returnFeeBean.setItemId(bean.getItemId());
					////						}
					////						if (!StringHandler.isBlankString(bean
					////								.getSelectCurrency())) {
					////							returnFeeBean.setCurrencyId(bean
					////									.getSelectCurrency());
					////						}
					//
					//						returnFeeBean.setCustomerRmaId(inputBean.getRmaId());
					//						returnFeeBean.setFeeDescription(bean.getItemDesc())
					//						factory.insert(returnFeeBean);
					bean.setCustomerRmaId(customerRmaId);
					if( isBlank(bean.getItemId()) ) bean.setItemId(itemId);
					factory.insert(bean);
				}
			}
		}

	}

	@SuppressWarnings("unchecked")
	public Collection getAddedNewsFeesList(
			CustomerReturnRequestInputBean inputBean) throws BaseException,
			Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());

		//		GenericSqlFactory factory = new GenericSqlFactory(dbManager,
		//				new MrReturnChargeViewBean());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,
				new CustomerReturnFeeBean());

		SortCriteria sortCcriteria = new SortCriteria();

		//		Collection<MrReturnChargeViewBean> c = null;

		sortCcriteria.setSortAscending(true);

		SearchCriteria criteria = new SearchCriteria();

		sortCcriteria.addCriterion("itemId");//feeItemId

		if (null != inputBean.getRmaId()) {
			criteria.addCriterion("customerRmaId", SearchCriterion.EQUALS,
					inputBean.getRmaId().toString());
		}

		//c = factory.select(criteria, sortCcriteria, "mr_return_charge_view");

		//		return c;
		return factory.select(criteria, sortCcriteria, "CUSTOMER_RETURN_FEE");
	}



	public void deleteCustomerReturnRequest(
			CustomerReturnRequestInputBean inputBean) throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());

		CustomerReturnRequestBeanFactory factory = new CustomerReturnRequestBeanFactory(
				dbManager);
		SearchCriteria criteria = new SearchCriteria();

		if (null != inputBean.getRmaId()) {
			criteria.addCriterion("customerRmaId",
					SearchCriterion.EQUALS, inputBean.getRmaId()
					.toString());
		}


		CustomerReturnFeeBeanFactory factory1 = new CustomerReturnFeeBeanFactory(
				dbManager);
		CustomerReturnReasonBeanFactory factory2 = new CustomerReturnReasonBeanFactory(
				dbManager);
		// delete all of same rma from table first.
		factory1.delete(criteria);
		factory2.delete(criteria);
		factory.delete(criteria);

	}


	public String approveRejectCustomerReturnRequest(	CustomerReturnRequestInputBean inputBean,	PersonnelBean personnelBean)
	throws BaseException, Exception {
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		String errorCode = "";
		/*    a_customer_rma_id customer_return_request.customer_rma_id%type,
				a_rma_status customer_return_request.rma_status%type,
				a_approver_id customer_return_request.approver_id%type,
				a_error out varchar2,
				a_quantity_return_authorized customer_return_request.quantity_return_authorized%type default null,
				a_rejection_comment customer_return_request.rejection_comment%type default null
		 */
		// makre sure to remove the hard coded company_id later.
		Collection inArgs =
			buildProcedureInput(
					inputBean.getRmaId(),
					inputBean.getRmaStatus(),
					personnelBean.getPersonnelId()
			);

		Collection inArgs2 =
			buildProcedureInput(
					inputBean.getQuantityReturnAuthorized(),
					inputBean.getDenialComments()
			);

		Vector outArgs = new Vector();
		outArgs.add( new Integer(java.sql.Types.VARCHAR) );

		if (log.isDebugEnabled()) {
			log.debug("inArgs for pkg_customer_return.P_AUTHORIZE_REJECT_RETURN:"+inArgs);
		}
		//unblock this for real run
		Vector error = (Vector) factory.doProcedure("pkg_customer_return.P_AUTHORIZE_REJECT_RETURN", inArgs, outArgs,inArgs2);
		//Vector error = new Vector();

		if(error.size()>0 && error.get(0) != null)
		{
			errorCode = error.get(0).toString();
		}


		if (("approve".equalsIgnoreCase(inputBean.getAction()))  && (errorCode.trim().length()==0) ) {
			sendNotificationMail(inputBean, personnelBean);
		}

		return errorCode;
	}
}
