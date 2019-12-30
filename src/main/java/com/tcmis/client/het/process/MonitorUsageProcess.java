package com.tcmis.client.het.process;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.het.beans.HetContainerUsageBean;
import com.tcmis.client.het.beans.HetPermitBean;
import com.tcmis.client.het.beans.HetUsageAuditViewBean;
import com.tcmis.client.het.beans.HetUsageBean;
import com.tcmis.client.het.beans.MonitorUsageInputBean;
import com.tcmis.client.het.beans.VvHetApplicationMethodBean;
import com.tcmis.client.het.beans.VvHetSubstrateBean;
import com.tcmis.client.het.factory.HetContainerUsageBeanFactory;
import com.tcmis.client.het.factory.HetUsageBeanFactory;
import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;

public class MonitorUsageProcess extends GenericProcess {

	/**
	 * ******************************************************************
	 * Process for the PoExpediting Section
	 * 
	 * @version 1.0
	 *          *************************************************************
	 *          ****
	 */

	Log							log		= LogFactory.getLog(this.getClass());

	private static BigDecimal	ZERO	= new BigDecimal("0.0");

	public MonitorUsageProcess(String client, String locale) {
		super(client, locale);
	}

	public MonitorUsageProcess(String client) {
		super(client);
	}

	@SuppressWarnings("unchecked")
	public Collection<HetUsageAuditViewBean> getSearchHistory(MonitorUsageInputBean input, PersonnelBean user) throws BaseException {
		factory.setBeanObject(new HetUsageAuditViewBean());

		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("usageId", SearchCriterion.EQUALS, input.getUsageId());

		SortCriteria sort = new SortCriteria();
		sort.addCriterion("modifiedDate", "desc");
		return factory.select(criteria, sort, "HET_USAGE_AUDIT_VIEW");
	}

	@SuppressWarnings("unchecked")
	public Collection<HetUsageBean> getSearchResult(MonitorUsageInputBean input, PersonnelBean user) throws BaseException {
		HetUsageBeanFactory factory = new HetUsageBeanFactory(dbManager);

		SearchCriteria criteria = new SearchCriteria();

		criteria.addCriterion("facilityId", SearchCriterion.EQUALS, input.getFacilityId());
		criteria.addCriterion("companyId", SearchCriterion.EQUALS, user.getCompanyId());

		if (!input.isContainerSearch()) {
			if (input.hasReportingEntity()) {
				criteria.addCriterion("reportingEntityId", SearchCriterion.EQUALS, input.getReportingEntity());
			}

			if (input.hasApplicationId()) {
				criteria.addCriterion("applicationId", SearchCriterion.EQUALS, input.getApplicationId());
			}
		}

		if (input.hasSearchArgument()) {
			criteria.addCriterionWithMode(input.getSearchField(), input.getSearchMode(), input.getSearchArgument());
		}
		if (input.hasFromDate()) {
			criteria.addCriterion("usageDate", SearchCriterion.FROM_DATE, input.getUsageDateFrom());
		}
		if (input.hasToDate()) {
			criteria.addCriterion("usageDate", SearchCriterion.TO_DATE, input.getUsageDateTo());
		}

		SortCriteria sort = new SortCriteria();
		sort.addCriterion("transactionId", "desc");
		sort.addCriterion("usageDate");
		sort.addCriterion("msdsNo");

		return factory.select(criteria, sort);
	}

	public Collection update(Collection<HetUsageBean> inputLines, PersonnelBean user) throws BaseException {
		Vector errorMessages = new Vector();
		HetUsageBeanFactory factory = new HetUsageBeanFactory(dbManager);
		HetContainerUsageBeanFactory containerFactory = new HetContainerUsageBeanFactory(dbManager);
		BigDecimal personnelId = user.getPersonnelIdBigDecimal();

		PermissionBean permissionBean = user.getPermissionBean();
		for (HetUsageBean inputBean : inputLines) {
			try {
				inputBean.setUserId(personnelId);
				HetUsageBean oldBean = factory.selectBean(inputBean.getUsageId());
				HetContainerUsageBean container = containerFactory.select(inputBean.getContainerId());

				if (container != null) {
					// Now correct the container.
					if (inputBean.isDiscarded()) {
						if (oldBean.isDiscarded()) {
							// Container is ALREADY discarded don't need to
							// modify it
						}
						else {
							// Zero out the old container
							container.setAmountRemaining(ZERO);
							containerFactory.update(container);
						}
					}
					else {
						if (oldBean.isDiscarded()) {
							// Add any amount remaining from this usage record
							// back into the container
							container.setAmountRemaining(container.getAmountRemaining().add(inputBean.getAmountRemaining()));
							containerFactory.update(container);
						}
						else if ((oldBean.getAmountRemaining() != null && oldBean.getAmountRemaining().compareTo(inputBean.getAmountRemaining()) != 0)
								|| (oldBean.getAmountRemaining() == null && inputBean.getAmountRemaining() != null)) {
							// They manually adjusted the amount remaining,
							// trust them to get it right
							container.setAmountRemaining(inputBean.getAmountRemaining());
							containerFactory.update(container);
						}
						else if (oldBean.getQuantity() != null && oldBean.getQuantity().compareTo(inputBean.getQuantity()) == 1) {
							// They reduced quantity used
							BigDecimal difference = oldBean.getQuantity().subtract(inputBean.getQuantity());
							container.setAmountRemaining(container.getAmountRemaining().add(difference));
							inputBean.setAmountRemaining(container.getAmountRemaining());
						}
						else if (oldBean.getQuantity() == null) {
							BigDecimal difference = inputBean.getQuantity();
							if (difference.compareTo(container.getAmountRemaining()) == 1) {
								// They're trying to increase it to more than
								// was left, reset to what was left
								inputBean.setQuantity(container.getAmountRemaining());
								container.setAmountRemaining(ZERO);
							}
							else {
								container.setAmountRemaining(container.getAmountRemaining().subtract(difference));
							}
							inputBean.setAmountRemaining(container.getAmountRemaining());
						}
						else if (oldBean.getQuantity().compareTo(inputBean.getQuantity()) == -1) {
							// They increased quantity used
							BigDecimal difference = inputBean.getQuantity().subtract(oldBean.getQuantity());
							if (difference.compareTo(container.getAmountRemaining()) == 1) {
								// They're trying to increase it to more than
								// was left, reset to what was left
								inputBean.setQuantity(container.getAmountRemaining());
								container.setAmountRemaining(ZERO);
							}
							else {
								container.setAmountRemaining(container.getAmountRemaining().subtract(difference));
							}
							inputBean.setAmountRemaining(container.getAmountRemaining());
						}
					}
				}
				factory.update(inputBean);
			}
			catch (Exception e) {
				errorMessages.add(e.getMessage());
			}
		}

		return (errorMessages.size() > 0 ? errorMessages : null);
	}

	public Collection delete(Collection<HetUsageBean> inputLines, PersonnelBean user) throws BaseException {
		Vector errorMessages = new Vector();
		String errorMsg = "";
		HetUsageBeanFactory factory = new HetUsageBeanFactory(dbManager);
		for (HetUsageBean inputBean : inputLines) {
			try {
				factory.delete(inputBean);
			}
			catch (Exception e) {
				errorMsg = "Error deleting- CatPartNo: " + inputBean.getCatPartNo() + " MSDSNo:" + inputBean.getMsdsNo() + "";
				errorMessages.add(errorMsg);
			}
		}

		return (errorMessages.size() > 0 ? errorMessages : null);
	}

	public ExcelHandler getExcelReport(Collection<HetUsageBean> data, Locale locale) throws NoDataException, BaseException, Exception {

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
		// write column headers
		pw.addRow();
		/* Pass the header keys for the Excel. */
		String[] headerkeys = { "label.workarea", "label.loggedby", "label.loggeddate", "label.transactionid", "label.partno",
								"label.msdsnumber", "label.employee", "label.containerid", "label.building", "label.permit",
								"label.controldevice","label.applicationmethod", "label.parttype", "label.painted", "label.substrate",
								"label.quantity", "label.unitofmeasure", "label.remaining", "label.discard", "label.usagedate",
								"label.tradename", "label.exported", "label.modified", "label.remarks"};
		/*
		 * This array defines the type of the excel column. 0 means default
		 * depending on the data type. pw.TYPE_PARAGRAPH defaults to 40
		 * characters. pw.TYPE_CALENDAR set up the date with no time.
		 * pw.TYPE_DATE set up the date with time.
		 */
		int[] types = { 0, 0, pw.TYPE_CALENDAR, 0, 0,
						0, 0, 0, 0, 0,
						0, 0, 0, 0, 0,
						0, 0, 0, 0, pw.TYPE_CALENDAR,
						0, 0, 0, pw.TYPE_PARAGRAPH};

		/*
		 * This array defines the default width of the column when the Excel
		 * file opens. 0 means the width will be default depending on the data
		 * type.
		 */
		int[] widths = {0, 0, 0, 0, 0,
						0, 0, 0, 0, 0,
						0, 0, 0, 0, 0,
						0, 0, 0, 0, 0,
						0, 0, 0, 0};
		/*
		 * This array defines the horizontal alignment of the data in a cell. 0
		 * means excel defaults the horizontal alignemnt by the data type.
		 */
		int[] horizAligns = {0, 0, 0, 0, 0,
								0, 0, 0, 0, 0,
								0, 0, 0, 0, 0,
								0, 0, 0, 0, 0,
								0, pw.ALIGN_CENTER, pw.ALIGN_CENTER, 0};

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		// format the numbers to the special columns
		/*
		 * pw.setColumnDigit(6, 2); pw.setColumnDigit(7, 2);
		 */

		for (HetUsageBean member : data) {
			pw.addRow();
			pw.addCell(member.getApplication());
			pw.addCell(member.getLoggerName());
			pw.addCell(member.getInsertDate());
			pw.addCell(member.getTransactionId());
			pw.addCell(member.getCatPartNo());
			pw.addCell(member.getMsdsNo());
			pw.addCell(member.getEmployee());
			pw.addCell(member.getContainerId());
			pw.addCell(member.getBuildingName());
			pw.addCell(member.getPermit());
			pw.addCell(member.getControlDevice());
			pw.addCell(member.getApplicationMethod());
			pw.addCell(member.getPartType());
			pw.addCell(member.isPainted() ? "Y" : "N");
			pw.addCell(member.getSubstrate());
			pw.addCell(member.getQuantity());
			pw.addCell(member.getUnitOfMeasure());
			pw.addCell(member.getAmountRemaining());
			pw.addCell(member.isDiscarded() ? "Y" : "N");
			pw.addCell(member.getUsageDate());
			pw.addCell(member.getTradeName());
			pw.addCell(member.isExported() ? "Y" : "N");
			pw.addCell(member.getModified() ? "Y" : "N");
			pw.addCell(member.getRemarks());
		}
		return pw;
	}

}
