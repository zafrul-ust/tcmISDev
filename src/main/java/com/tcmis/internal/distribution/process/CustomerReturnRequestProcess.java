package com.tcmis.internal.distribution.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.order.beans.RequestLineItemBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.factory.PersonnelBeanFactory;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.CustomerReturnFeeBean;
import com.tcmis.internal.distribution.beans.CustomerReturnRcptShpViewBean;
import com.tcmis.internal.distribution.beans.CustomerReturnReasonBean;
import com.tcmis.internal.distribution.beans.CustomerReturnRequestBean;
import com.tcmis.internal.distribution.beans.CustomerReturnRequestInputBean;
import com.tcmis.internal.distribution.beans.CustomerReturnRequestDetailBean;
import com.tcmis.internal.distribution.beans.CustomerReturnRequestViewBean;
import com.tcmis.internal.distribution.beans.CustomerReturnTrackingInputBean;
import com.tcmis.internal.distribution.beans.MrAddChargeViewBean;
import com.tcmis.internal.distribution.beans.NewFeesItemsViewBean;
import com.tcmis.internal.distribution.beans.VvReturnReasonBean;
import com.tcmis.internal.distribution.factory.CustomerReturnFeeBeanFactory;
import com.tcmis.internal.distribution.factory.CustomerReturnReasonBeanFactory;
import com.tcmis.internal.distribution.factory.CustomerReturnRequestBeanFactory;
import com.tcmis.internal.hub.beans.CustomerReturnInputBean;
import com.tcmis.internal.hub.beans.MrIssueReceiptDetailViewBean;
import com.tcmis.internal.hub.process.CustomerReturnProcess;

/******************************************************************************
 * Process used by CustomerReturnRequestAction
 *
 * @version 1.0
 *****************************************************************************/

public class CustomerReturnRequestProcess extends GenericProcess {
	Log log = LogFactory.getLog(this.getClass());
	ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
	DbManager dbManager;
	GenericSqlFactory factory;

	public CustomerReturnRequestProcess(String client) {
		super(client);

		dbManager = new DbManager(getClient(), getLocale());
		factory = new GenericSqlFactory(dbManager);
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

	public BigDecimal addCustomerReturnRequest(CustomerReturnRequestInputBean inputBean,
												PersonnelBean personnelBean) throws BaseException, Exception {
		return addCustomerReturnRequest(inputBean, personnelBean, null);
	}

	public BigDecimal addCustomerReturnRequest(CustomerReturnRequestInputBean inputBean,
												PersonnelBean personnelBean,
												Connection connection) throws BaseException, Exception {
		CustomerReturnRequestBean dataBean = new CustomerReturnRequestBean();

		BeanHandler.copyAttributes(inputBean, dataBean);

		dataBean.setPrNumber(new BigDecimal(inputBean.getPrNumber()));
		dataBean.setCustomerServiceRepId(personnelBean.getPersonnelIdBigDecimal());
		dataBean.setReturnRequestorName(inputBean.getRequestorName());
		dataBean.setReturnRequestorEmail(inputBean.getRequestorEmail());
		dataBean.setReturnRequestorPhone(inputBean.getRequestorPhone());
		dataBean.setQuantityReturnRequested(inputBean.getReturnQuantityRequested());
		dataBean.setWantReplacementMaterial(inputBean.getWantReplacement());
		if (dataBean.getApprovalDate() != null) {
			dataBean.setApproverId(personnelBean.getPersonnelIdBigDecimal());
		}

		if (StringHandler.isBlankString(inputBean.getCatalogId())) {
			RequestLineItemBean rliData = getRequestLineItemData(inputBean.getCompanyId(), new BigDecimal(inputBean.getPrNumber()), inputBean.getLineItem());

			dataBean.setCatalogCompanyId(rliData.getCatalogCompanyId());
			dataBean.setCatalogId(rliData.getCatalogId());
		} else {
			dataBean.setCatalogCompanyId(inputBean.getCatalogCompanyId());
			dataBean.setCatalogId(inputBean.getCatalogId());
		}

		if (connection == null) {
			return new CustomerReturnRequestBeanFactory(dbManager).insertCustomerRequest(dataBean);
		} else {
			return new CustomerReturnRequestBeanFactory(dbManager).insertCustomerRequest(dataBean, connection);
		}
	}

	private RequestLineItemBean getRequestLineItemData(String companyId, BigDecimal prNumber, String lineItem)
			throws BaseException, InstantiationException, IllegalAccessException {
		if (StringHandler.isBlankString(companyId)
				|| prNumber == null
				|| StringHandler.isBlankString(lineItem)) {
			throw new BaseException("Missing primary key for request_line_item");
		}
		StringBuilder query = new StringBuilder("select * from rli");
		query.append(" where company_id = ").append(SqlHandler.delimitString(companyId));
		query.append(" and pr_number = ").append(prNumber);
		query.append(" and line_item = ").append(SqlHandler.delimitString(lineItem));

		return (RequestLineItemBean) factory.selectSingleBean(query.toString(), RequestLineItemBean.class).orElse(null);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String updateCustomerReturnRequest(CustomerReturnRequestInputBean inputBean,
			PersonnelBean personnelBean) throws BaseException, Exception {

		DbManager dbManager = new DbManager(getClient(), getLocale());

		String errorMsg = "";
		try {
			// SpecList: specId concatenation using commas
			Vector inArgs = buildProcedureInput(inputBean.getRmaId(),inputBean.getReturnQuantityRequested(),inputBean.getNewUnitPrice(),inputBean.getReturnMaterial(),
					inputBean.getWantReplacement(),inputBean.getRequestorName(),inputBean.getRequestorEmail(),inputBean.getRequestorPhone(),
					inputBean.getCustomerServiceRepId(),inputBean.getReturnNotes(),inputBean.getReplacementItemId(),
					inputBean.getSpecListConcat(),inputBean.getSpecLibraryList(),
					inputBean.getDetailConcat(),inputBean.getCocConcat(),inputBean.getCoaConcat(),inputBean.getReplacementRequiredDatetime(),
					inputBean.getReplacementPromiseDate(),inputBean.getCorrectItemShipped());

			Vector outArgs = new Vector();
			outArgs.add( new Integer(Types.VARCHAR) );

			GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
			if (log.isDebugEnabled()) {
				log.debug("pkg_customer_return.p_update_request"+inArgs);
			}
			Vector v = (Vector) procFactory.doProcedure("pkg_customer_return.p_update_request", inArgs, outArgs);
			errorMsg = (String) v.get(0);

			if ("submit".equalsIgnoreCase(inputBean.getAction()) ) {
				GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
				String query = "update customer_return_request set rma_Status = 'Pending Approval' "+
				" where customer_rma_id = "+inputBean.getRmaId().toString()+" ";
				genericSqlFactory.deleteInsertUpdate(query);
				sendNotificationMail(inputBean, personnelBean);
			}

			return errorMsg;
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return library.getString("generic.error");
		}
	}

	public CustomerReturnRequestViewBean getCustomerReturnRequestData(CustomerReturnRequestInputBean inputBean,
																		PersonnelBean personnelBean) throws BaseException, Exception {
		CustomerReturnTrackingProcess trackingProcess = new CustomerReturnTrackingProcess(getClient(), getLocaleObject());

		CustomerReturnTrackingInputBean trackingInputBean = new CustomerReturnTrackingInputBean();
		trackingInputBean.setSearchField("customerRmaId");
		trackingInputBean.setSearchMode("is");
		trackingInputBean.setSearchArgument(inputBean.getRmaId().toPlainString());

		Vector<CustomerReturnRequestViewBean> c = (Vector<CustomerReturnRequestViewBean>) trackingProcess.getSearchResult(trackingInputBean, personnelBean, false);

		if (c.size() != 0)
			return c.get(0);
		else
			return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
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

	public void updateReceiptItemShippedIncorrectly(Collection<CustomerReturnRcptShpViewBean> beans,
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
		Collection<VvReturnReasonBean> c = Collections.EMPTY_LIST;
		if (null != inputBean.getRmaId()) {
			c = factory.setBean(new VvReturnReasonBean())
			.selectQuery("select a.RETURN_REASON_ID, a.REASON_LABEL from vv_return_reason a , "
					+ " customer_return_reason b where a.RETURN_REASON_ID = b.RETURN_REASON_ID "
					+ " and b.CUSTOMER_RMA_ID ="
					+ inputBean.getRmaId().toString());
		}
		return c;
	}

	@SuppressWarnings("unchecked")
	public Collection<CustomerReturnRcptShpViewBean> getCustomerReturnRcptShpViewColl(
			CustomerReturnRequestInputBean inputBean) throws BaseException,
			Exception {
		Collection<CustomerReturnRcptShpViewBean> c = Collections.EMPTY_LIST;
		if (null != inputBean.getRmaId()) {

			c = factory.setBean(new CustomerReturnRcptShpViewBean()).selectQuery("select * from CUSTOMER_RETURN_RCPT_SHP_VIEW"
					+ " where CUSTOMER_RMA_ID = "+ inputBean.getRmaId().toString());
		}
		return c;
	}

	@SuppressWarnings("rawtypes")
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
						subject = pre + "Requested by " + inputBean.getCsrName()
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
						+ "Requested by "
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
						+ "Requested by "
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
		StringBuilder query= new StringBuilder("select * from table (pkg_rli_sales_order.fx_additional_charge(");
		query.append( (null!=inputBean.getCompanyId()?"'"+inputBean.getCompanyId()+"'":null) +"," +
				inputBean.getPrNumber()  + "))");

		return factory.setBean(new MrAddChargeViewBean()).selectQuery(query.toString());
	}

	@SuppressWarnings("unchecked")
	public Collection<MrAddChargeViewBean> getOriginalFeeLineChargeList(
			CustomerReturnRequestInputBean inputBean) throws BaseException,
			Exception {
		StringBuilder query= new StringBuilder("select * from table (PKG_CUSTOMER_RETURN.FX_ADDITIONAL_CHARGE(");
		query.append( (null!=inputBean.getCompanyId()?"'"+inputBean.getCompanyId()+"'":null)).append("," ).append(
				inputBean.getPrNumber() ).append(",'" ).append(
						inputBean.getLineItem() ).append( "'))");

		return factory.setBean(new MrAddChargeViewBean()).selectQuery(query.toString());
	}

	@SuppressWarnings("unchecked")
	public Collection<NewFeesItemsViewBean> getNewFeesItemList()
	throws BaseException, Exception {
		return factory.setBean(new NewFeesItemsViewBean())
		.selectQuery("select item.item_id,item.item_desc from item, vv_item_type it"
						+ " where item.item_type = it.item_type and it.ma_add_charge = 'y' order by item_desc");
	}

	public void addCustomerReturnFees(Collection<CustomerReturnFeeBean> beans,
			CustomerReturnRequestInputBean inputBean) throws BaseException,
			Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		CustomerReturnFeeBeanFactory factory = new CustomerReturnFeeBeanFactory(
				dbManager);

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
					bean.setCustomerRmaId(customerRmaId);
					if( isBlank(bean.getItemId()) ) bean.setItemId(itemId);
					factory.insert(bean);
				}
			}
		}

	}

	@SuppressWarnings("rawtypes")
	public Collection getAddedNewsFeesList(
			CustomerReturnRequestInputBean inputBean) throws BaseException,
			Exception {
		SortCriteria sortCcriteria = new SortCriteria();

		sortCcriteria.setSortAscending(true);

		SearchCriteria criteria = new SearchCriteria();

		sortCcriteria.addCriterion("itemId");//feeItemId

		if (null != inputBean.getRmaId()) {
			criteria.addCriterion("customerRmaId", SearchCriterion.EQUALS,
					inputBean.getRmaId().toString());
		}

		return factory.setBean(new CustomerReturnFeeBean()).select(criteria, sortCcriteria, "CUSTOMER_RETURN_FEE");
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String approveRejectCustomerReturnRequest(CustomerReturnRequestInputBean inputBean,
														PersonnelBean personnelBean)
			throws BaseException, Exception {
		String errorCode = "";
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
		outArgs.add( new Integer(Types.VARCHAR) );

		if (log.isDebugEnabled()) {
			log.debug("inArgs for pkg_customer_return.P_AUTHORIZE_REJECT_RETURN:"+inArgs);
		}
		Vector error = (Vector) factory.doProcedure("pkg_customer_return.P_AUTHORIZE_REJECT_RETURN", inArgs, outArgs,inArgs2);

		if(CollectionUtils.isNotEmpty(error) && error.get(0) != null) {
			errorCode = error.get(0).toString();
		}

		if ("approve".equalsIgnoreCase(inputBean.getAction()) && StringUtils.isBlank(errorCode)) {
			sendNotificationMail(inputBean, personnelBean);

			StringBuilder query = new StringBuilder("update customer_return_request");
			query.append(" set data_transfer_status = 'OPEN'");
			query.append(" where customer_rma_id = ").append(inputBean.getRmaId());

			factory.deleteInsertUpdate(query.toString());
		}

		return errorCode;
	}

	public Collection<MrIssueReceiptDetailViewBean> getCmsIssuesWithReturnQuantity(BigDecimal customerRmaId,
																					String companyId,
																					String prNumber,
																					String lineItem)
			throws BaseException {
		CustomerReturnProcess process = new CustomerReturnProcess(getClient(), getLocale());

		CustomerReturnInputBean inputBean = new CustomerReturnInputBean();
		inputBean.setCompanyId(companyId);
		inputBean.setPrNumber(new BigDecimal(prNumber));
		inputBean.setLineItem(lineItem);
		inputBean.setCustomerRmaId(customerRmaId);

		Collection<MrIssueReceiptDetailViewBean> result = process.getCmsIssuesWithReturnQuantity(inputBean);

		if (CollectionUtils.isNotEmpty(result)) {
			calculateIssueReturnReceivedQuantity(result);
		}

		return result;
	}

	private void calculateIssueReturnReceivedQuantity(Collection<MrIssueReceiptDetailViewBean> coll) {
		String lastReceiptId = "";
		BigDecimal leftOver = BigDecimal.ZERO;

		for (MrIssueReceiptDetailViewBean bean : coll) {
			if (bean.getTotalPendingReturn() == null) {
				bean.setTotalPendingReturn(BigDecimal.ZERO);
			}

			if (bean.getTotalShipped().compareTo(BigDecimal.ZERO) == -1) {
				bean.setTotalReturned(BigDecimal.ZERO);

				bean.setTotalAvailable(BigDecimal.ZERO);
			} else {
				BigDecimal totalReturned = bean.getCrTotalReturnedQuantity().add(bean.getCoNewReturnedQuantity());

				BigDecimal issueQtyAvailableForCOOldStyleReturn = bean.getTotalShipped()
																		.subtract(bean.getTotalPendingReturn())
																		.subtract(totalReturned);

				if (issueQtyAvailableForCOOldStyleReturn.compareTo(BigDecimal.ZERO) == 1) {
					//if we have some returned_quantity not accounted for in customer_return_request_detail table,
					//we can assume that the leftover returned_quantity were done in old-style CMS CO return process
					String curReceiptId = bean.getReceiptId().toPlainString();

					if (!lastReceiptId.equals(curReceiptId)) {
						leftOver = bean.getCoTotalReturnedQuantity();
					}

					BigDecimal qtyUsedByOldStyleReturn = leftOver.min(issueQtyAvailableForCOOldStyleReturn);

					totalReturned = totalReturned.add(qtyUsedByOldStyleReturn);

					leftOver = leftOver.subtract(qtyUsedByOldStyleReturn);

					lastReceiptId = curReceiptId;
				}

				bean.setTotalReturned(totalReturned);

				bean.setTotalAvailable(bean.getTotalShipped()
											.subtract(bean.getTotalPendingReturn())
											.subtract(bean.getTotalReturned()));
			}
		}
	}

	public void confirmCmsCustomerReturnRequest(CustomerReturnRequestInputBean inputBean,
													Collection<CustomerReturnReasonBean> returnReasonBeans,
													Collection<CustomerReturnRequestDetailBean> returnIssuesBeans,
													PersonnelBean personnelBean) throws Exception {
		Connection connection = null;

		try {
			connection = dbManager.getConnection();
			connection.setAutoCommit(false);

			BigDecimal customerRmaId = addCustomerReturnRequest(inputBean, personnelBean, connection);

			updateReturnReasons(customerRmaId, returnReasonBeans, connection);

			updateReceiptsToReturn(customerRmaId, returnIssuesBeans, connection);

			inputBean.setRmaId(customerRmaId);

			connection.commit();
		} catch (Exception e) {
			log.error(e.getMessage(), e);

			if (connection != null) {
				connection.rollback();
			}

			throw e;
		} finally {
			if (connection != null) {
				connection.setAutoCommit(true);
				dbManager.returnConnection(connection);
			}
		}
	}

	public CustomerReturnRequestViewBean getReturnRequestHeaderInfo(CustomerReturnRequestInputBean inputBean,
																	PersonnelBean personnelBean) throws Exception {
		return inputBean.getRmaId() == null ? getReturnRequestHeaderInfo(inputBean.getCompanyId(),
																			new BigDecimal(inputBean.getPrNumber()),
																			inputBean.getLineItem(),
																			inputBean.getReturnType(),
																			personnelBean.getPersonnelIdBigDecimal())
											: getCustomerReturnRequestData(inputBean, personnelBean);
	}

	/**
	 * Light version of customer_return_request_view. Allow getting data without inserting into
	 * customer_return_request first.
	 *
	 * @param companyId
	 * @param prNumber
	 * @param lineItem
	 * @param returnType
	 * @param personnelId
	 * @return
	 * @throws BaseException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public CustomerReturnRequestViewBean getReturnRequestHeaderInfo(String companyId,
																	BigDecimal prNumber,
																	String lineItem,
																	String returnType,
																	BigDecimal personnelId)
			throws BaseException, InstantiationException, IllegalAccessException {
		StringBuilder query = new StringBuilder("select rli.pr_number, rli.line_item, rli.facility_id");
		query.append(", rli.inventory_group, rli.currency_id, rli.fac_part_no, rli.fac_part_no");
		query.append(", fx_operating_entity_id (rli.inventory_group) ops_entity_id");
		query.append(", fx_operating_entity_name (rli.inventory_group) ops_entity_name");
		query.append(", sysdate request_start_date");
		query.append(", fx_part_description(fi.company_id, fi.facility_id, fi.fac_part_no) part_description");
		query.append(", fx_inventory_group_name (rli.inventory_group) inventory_group_name");
		query.append(", DECODE (pr.requestor, 0, '', fx_personnel_id_to_name (pr.requestor)) requestor_name");
		query.append(", DECODE (pr.requestor, 0, '', fx_personnel_id_to_phone (pr.requestor)) requestor_phone");
		query.append(", DECODE (pr.requestor, 0, '', fx_personnel_id_to_email (pr.requestor)) requestor_email");
		query.append(", rli.fac_part_no replacement_cat_part_no");
		query.append(", fx_company_name(rli.company_id) customer_name");
		query.append(", rli.quantity order_quantity");
		query.append(", rli.catalog_price unit_price");
		query.append(", igd.hub");
		query.append(", fx_location_address(rli.ship_to_location_id, rli.company_id) ship_to_address");
		query.append(", (SELECT SUM (quantity) FROM issue");
		query.append(" WHERE ship_confirm_date IS NOT NULL");
		query.append(" AND company_id = rli.company_id");
		query.append(" AND pr_number = rli.pr_number");
		query.append(" AND line_item = rli.line_item) quantity_shipped");
		query.append(", fx_personnel_id_to_name(").append(personnelId).append(") csr_name");
		query.append(", ").append(personnelId).append(" customer_service_rep_id");
		query.append(", ").append(SqlHandler.delimitString(returnType)).append(" return_type");

		query.append(" FROM customer.fac_item fi");
		query.append(", customer.request_line_item rli");
		query.append(", customer.purchase_request pr");
		query.append(", inventory_group_definition igd");

		query.append(" WHERE fi.company_id(+) = rli.catalog_company_id");
		query.append(" AND fi.facility_id(+) = rli.catalog_id");
		query.append(" AND fi.fac_part_no(+) = rli.fac_part_no");
		query.append(" AND rli.inventory_group = igd.inventory_group");
		query.append(" AND rli.company_id = pr.company_id");
		query.append(" AND rli.pr_number = pr.pr_number");

		if (!StringHandler.isBlankString(companyId)) {
			query.append(" AND rli.company_id = ").append(SqlHandler.delimitString(companyId));
		}

		if (prNumber != null) {
			query.append(" AND rli.pr_number = ").append(prNumber);
		}

		if (!StringHandler.isBlankString(lineItem)) {
			query.append(" AND rli.line_item = ").append(SqlHandler.delimitString(lineItem));
		}

		return (CustomerReturnRequestViewBean) factory.selectSingleBean(query.toString(),
																		CustomerReturnRequestViewBean.class).orElse(null);
	}

	/**
	 * Insert records with unique return_reason_ids into customer_return_reason table. Will override
	 * existing records for the given customer_rma_id.
	 *
	 * @param customerRmaId the customer_rma_id that records will use to do insertion
	 * @param returnReasonBeans records to be inserted
	 * @param connection
	 * @throws BaseException
	 */
	private void updateReturnReasons(BigDecimal customerRmaId,
										Collection<CustomerReturnReasonBean> returnReasonBeans,
										Connection connection) throws BaseException {
		StringBuilder query = new StringBuilder("delete from customer_return_reason");
		query.append(" where customer_rma_id = ").append(customerRmaId);

		factory.deleteInsertUpdate(query.toString(), connection);

		if (CollectionUtils.isNotEmpty(returnReasonBeans)) {
			List<String> insertedReaturnReasonIds = new ArrayList<String>();
			CustomerReturnReasonBeanFactory customerReturnReasonFactory = new CustomerReturnReasonBeanFactory(dbManager);

			for (CustomerReturnReasonBean bean : returnReasonBeans) {
				if (!insertedReaturnReasonIds.contains(bean.getReturnReasonId())) {
					bean.setCustomerRmaId(customerRmaId);

					customerReturnReasonFactory.insert(bean, connection);

					insertedReaturnReasonIds.add(bean.getReturnReasonId());
				}
			}
		}
	}

	/**
	 * Insert records with positive quantity (> 0) records into customer_return_request_detail table. Will override
	 * existing records for the given customer_rma_id.
	 *
	 * @param customerRmaId the customer_rma_id that records will use to do insertion
	 * @param returnIssuesBeans records to be inserted
	 * @param connection
	 * @throws BaseException
	 */
	private void updateReceiptsToReturn(BigDecimal customerRmaId,
										Collection<CustomerReturnRequestDetailBean> returnIssuesBeans,
										Connection connection) throws BaseException {
		StringBuilder query = new StringBuilder("delete from CUSTOMER_RETURN_REQUEST_DETAIL");
		query.append(" where customer_rma_id = ").append(customerRmaId);

		factory.deleteInsertUpdate(query.toString(), connection);

		if (CollectionUtils.isNotEmpty(returnIssuesBeans)) {
			for (CustomerReturnRequestDetailBean bean : returnIssuesBeans) {
				if (bean.getQuantity().compareTo(BigDecimal.ZERO) == 1) {
					bean.setCustomerRmaId(customerRmaId);

					insertCustomerReturnRequestReceiptRecord(bean, connection);
				}
			}
		}
	}

	private void insertCustomerReturnRequestReceiptRecord(CustomerReturnRequestDetailBean bean, Connection connection)
			throws BaseException {
		StringBuilder subQuery = new StringBuilder("select MAX(rma_line) + 1");
		subQuery.append(" from customer_return_request_detail");
		subQuery.append(" where customer_rma_id = ").append(bean.getCustomerRmaId());

		StringBuilder query = new StringBuilder("insert into CUSTOMER_RETURN_REQUEST_DETAIL");
		query.append(" (customer_rma_id");
		query.append(", rma_line");
		query.append(", item_id");
		query.append(", receipt_id");
		query.append(", issue_id");
		query.append(", quantity)");
		query.append(" select ").append(bean.getCustomerRmaId()).append(" customer_rma_id");
		query.append(", nvl((").append(subQuery.toString()).append("), 1) rma_line");
		query.append(", ").append(bean.getItemId()).append(" item_id");
		query.append(", ").append(bean.getReceiptId()).append(" receipt_id");
		query.append(", ").append(bean.getIssueId()).append(" issue_id");
		query.append(", ").append(bean.getQuantity()).append(" quantity");
		query.append(" from dual");

		factory.deleteInsertUpdate(query.toString(), connection);
	}
}
