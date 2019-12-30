package com.tcmis.internal.hub.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.UserFacilityViewBean;
import com.tcmis.common.admin.factory.PersonnelBeanFactory;
import com.tcmis.common.admin.factory.UserFacilitySelectOvBeanFactory;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.TestRequestInputBean;
import com.tcmis.internal.hub.beans.TestResultBean;
import com.tcmis.internal.hub.factory.TestRequestBeanFactory;

public class TestRequestSearchProcess extends GenericProcess {

	private static String	RESOURCE_BUNDLE	= "com.tcmis.common.resources.CommonResources";
	private String			URL				= "";
	private ResourceLibrary	library			= null;

	private ResourceLibrary	resource		= new ResourceLibrary("label");
	private String			hostUrl			= resource.getString("label.hosturl");

	public TestRequestSearchProcess(String client) {
		super(client);
	}

	public TestRequestSearchProcess(String client, String locale, String URL) {
		super(client, locale);
		this.URL = URL;
		library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
	}

	public void cancelDuplicateTestRequest(TestRequestInputBean input, PersonnelBean personnelBean) throws BaseException {
        try {
            DbManager dbManager = new DbManager(getClient());
		    GenericSqlFactory factory = new GenericSqlFactory(dbManager);
            StringBuilder query = new StringBuilder("select * from lab_test where test_request_id <> ").append(input.getTestRequestId());
            query.append(" and status = 'New' and test_request_id like '").append(input.getTestRequestId().intValue()).append(".%'");
            


        Vector inArgs = buildProcedureInput(input.getTestRequestId(),
                                            (input.getDateCancelled() == null ? null : new Timestamp(input.getDateCancelled().getTime())),
                                            input.getCancelledBy(),
                                            input.getReasonForCancellation());

        }catch(Exception e) {
            e.printStackTrace();
        }
    }  //end of method  

    public boolean testRequiredCustomerResponse (BigDecimal testRequestId) throws BaseException {
        boolean result = false;
        try {
            DbManager dbManager = new DbManager(getClient());
		    GenericSqlFactory factory = new GenericSqlFactory(dbManager);
            StringBuilder query = new StringBuilder("select count(*) from lab_test_results ltr, cust_lab_test_required cltr, lab_test lt");
            query.append(" where ltr.test_request_id = ").append(testRequestId).append(" and ltr.test_id = cltr.test_id");
            query.append(" and cltr.require_customer_response = 'Y' and cltr.active = 'Y'");
            query.append(" and ltr.test_request_id = lt.test_request_id and lt.company_id = cltr.company_id");
            query.append(" and lt.catalog_company_id = cltr.catalog_company_id and lt.catalog_id = cltr.catalog_id");
            query.append(" and lt.cat_part_no = cltr.cat_part_no and lt.part_group_no = cltr.part_group_no");
            BigDecimal tmpVal = new BigDecimal(factory.selectSingleValue(query.toString()));
            if (tmpVal.intValue() > 0)
                result = true;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return result;
	} //end of method

    // Generates an HTML table for output to MS Excel of the search the user
	// just conducted.
	public ExcelHandler getExcelReport(TestRequestInputBean input, PersonnelBean user, Locale locale) {
		ResourceLibrary library = new ResourceLibrary(RESOURCE_BUNDLE, locale);
		ExcelHandler pw = new ExcelHandler(library);
		Collection data;
		try {
			data = this.getSearchData(input, user);
		}
		catch (BaseException e) {
			log.debug("caught BaseException; " + e.toString());
			return pw;
		}
		Iterator iterator = data.iterator();
		pw.addTable();

		String[] headerkeys = { "label.requestno", "label.status", "label.datecreated", "label.partno", "label.partdescription", "label.item", "label.receiptid", "label.lotnumber", "label.lotstatus", "receivedreceipts.label.dor", "label.datesenttolab",
				"label.datesamplesreceived", "label.datetestscompleted", "label.daysinlab", "label.materialqualified", "label.qualitynote" };
		/*
		 * This array defines the type of the excel column. 0 means default
		 * depending on the data type. pw.TYPE_PARAGRAPH defaults to 40
		 * characters. pw.TYPE_CALENDAR set up the date with no time.
		 * pw.TYPE_DATE set up the date with time.
		 */
		int[] types = { 0, 0, pw.TYPE_CALENDAR, 0, 0, 0, 0, 0, 0, pw.TYPE_CALENDAR, pw.TYPE_CALENDAR, pw.TYPE_CALENDAR, pw.TYPE_CALENDAR, pw.TYPE_NUMBER, 0, 0 };

		/*
		 * This array defines the default width of the column when the Excel
		 * file opens. 0 means the width will be default depending on the data
		 * type.
		 */
		int[] widths = { 0, 0, 0, 0, 30, 0, 0, 15, 0, 0, 0, 0, 0, 0, 0, 0 };
		/*
		 * This array defines the horizontal alignment of the data in a cell. 0
		 * means excel defaults the horizontal alignemnt by the data type.
		 */
		int[] horizAligns = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		// Add header row using addCellKeyBold() to produce emphasis.
		/*
		 * pw.addCellKeyBold("label.requestno");
		 * pw.addCellKeyBold("label.status");
		 * pw.addCellKeyBold("label.datecreated");
		 * pw.addCellKeyBold("label.partno."); pw.addCellKeyBold("label.item");
		 * pw.addCellKeyBold("label.receiptid"); pw.addCellKeyBold("label.lot");
		 * pw.addCellKeyBold("label.partdescription");
		 * pw.addCellKeyBold("receivedreceipts.label.dor");
		 * pw.addCellKeyBold("label.datesenttolab");
		 * pw.addCellKeyBold("label.datesamplesreceived");
		 * pw.addCellKeyBold("label.datetestscompleted");
		 * pw.addCellKeyBold("label.daysinlab");
		 * pw.addCellKeyBold("label.materialqualified");
		 */

		// Iterate over search result collection to build data rows.
		TestRequestInputBean row;
		while (iterator.hasNext()) {
			// The search bean
			row = (TestRequestInputBean) iterator.next();

			// The rows are added as populated using addCell() to produce a
			// basic field element.
			pw.addRow();
			pw.addCell(row.getTestRequestId());
			pw.addCell(row.getRequestStatus());
			pw.addCell(row.getCreateDate() == null ? new Timestamp(row.getCreateDate().getTime()) : row.getCreateDate());
			pw.addCell(row.getCatPartNo());
			pw.addCell(row.getPartDescription());
			pw.addCell(row.getItemId());
			pw.addCell(row.getReceiptId());
			pw.addCell(row.getMfgLot());
			pw.addCell(row.getLotStatus());
			pw.addCell(row.getDateOfReceipt() != null ? new Timestamp(row.getDateOfReceipt().getTime()) : row.getDateOfReceipt());
			pw.addCell(row.getDateToLab() != null ? new Timestamp(row.getDateToLab().getTime()) : row.getDateToLab());
			pw.addCell(row.getDateReceivedByLab() != null ? new Timestamp(row.getDateReceivedByLab().getTime()) : row.getDateReceivedByLab());
			pw.addCell(row.getDateTestComplete() != null ? new Timestamp(row.getDateTestComplete().getTime()) : row.getDateTestComplete());
			pw.addCell(String.valueOf(row.getLabAge().setScale(2, BigDecimal.ROUND_UP)));
			pw.addCell(row.getMaterialQualified());
			pw.addCell(row.getQualityTrackingNumber());
		}
		return pw;
	}

	// Retrieves data from the search view of the LAB_TEST data
	public Collection getSearchData(TestRequestInputBean input, PersonnelBean user) throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		TestRequestBeanFactory factory = new TestRequestBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();

		criteria.addCriterion("opsEntityId", SearchCriterion.EQUALS, input.getOpsEntityId());
		criteria.addCriterion("hub", SearchCriterion.EQUALS, input.getHub());
		if (!StringHandler.isBlankString(input.getInventoryGroup())) {
			criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, input.getInventoryGroup());
		}
		else {
			if ("Radian".equals(user.getCompanyId())) {
				String invQuery = " select inventory_group from user_inventory_group where personnel_id = " + user.getPersonnelId();
				invQuery += " and company_id = '" + user.getCompanyId() + "' " + " and ops_entity_id = '" + input.getOpsEntityId() + "' ";
				invQuery += " and hub =  " + input.getHub();
				criteria.addCriterion("inventoryGroup", SearchCriterion.IN, invQuery);
			}
			else {
				SearchCriteria facilityCriteria = new SearchCriteria("personnelId", SearchCriterion.EQUALS, "" + user.getPersonnelId());
				facilityCriteria.addCriterion("companyId", SearchCriterion.EQUALS, user.getCompanyId());
				if (input.getFacilityId() != null)
					facilityCriteria.addCriterion("facilityId", SearchCriterion.EQUALS, input.getFacilityId());
				SearchCriteria inventoryGroupCriteria = new SearchCriteria();
				inventoryGroupCriteria.addNestedQuery("facilityId", "select facility_id from user_facility", facilityCriteria);
				criteria.addNestedQuery("inventoryGroup", "select distinct inventory_group from facility_inventory_group", inventoryGroupCriteria);
			}
		}
		if (!StringHandler.isBlankString(input.getCompanyId())) {
			criteria.addCriterion("companyId", SearchCriterion.EQUALS, input.getCompanyId());
		}
		if (!StringHandler.isBlankString(input.getRequestStatus())) {
			criteria.addCriterion("requestStatus", SearchCriterion.EQUALS, input.getRequestStatus());
		}
		if (input.getLabAge() != null) criteria.addCriterion("labAge", SearchCriterion.GREATER_THAN, input.getLabAge().toString());

		if (!StringHandler.isBlankString(input.getSearchArgument())) {
			String mode = input.getSearchMode();
			String field = input.getSearchField();
			if (mode.equals("is")) {
				criteria.addCriterion(field, SearchCriterion.EQUALS, input.getSearchArgument());
			}
			else if (mode.equals("like")) {
				criteria.addCriterion(field, SearchCriterion.LIKE, input.getSearchArgument());
			}
			else if (mode.equals("startsWith")) {
				criteria.addCriterion(field, SearchCriterion.STARTS_WITH, input.getSearchArgument());
			}
			else if (mode.equals("endsWith")) {
				criteria.addCriterion(field, SearchCriterion.ENDS_WITH, input.getSearchArgument());
			}
		}
		return factory.select(criteria);
	}

	public Hashtable createTestRequest(BigDecimal receiptId, BigDecimal createdByPersonnelId) throws BaseException {
		Hashtable dataH = new Hashtable(3);
		DbManager dbManager = new DbManager(getClient());
		TestRequestBeanFactory factory = new TestRequestBeanFactory(dbManager);
		Vector results;
		BigDecimal parentTestRequestId, newTestRequestId;
		results = (Vector) factory.insert(receiptId, createdByPersonnelId);
		parentTestRequestId = (BigDecimal) results.get(0);
		String result = results.get(1).toString();
		if (result.equalsIgnoreCase("OK") && parentTestRequestId != null) {
			newTestRequestId = factory.getTestRequestId(parentTestRequestId);
			dataH.put("REQUEST_ID",newTestRequestId);
		}
		else {
			dataH.put("REQUEST_ID",BigDecimal.ZERO);
		}
		dataH.put("ERROR_MSG",result);

		return dataH;
	}

	// Looks up a test request by either the TestRequestId or the ReceiptId
	public TestRequestInputBean getExistingTestRequest(BigDecimal testRequestId) throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		TestRequestBeanFactory factory = new TestRequestBeanFactory(dbManager);
		Collection<TestRequestInputBean> results = factory.select(testRequestId);
		TestRequestInputBean[] resultsArr = results.toArray(new TestRequestInputBean[0]);
		return resultsArr[0];
	}

	public String signTestRequest(TestRequestInputBean input, PersonnelBean personnelBean) throws BaseException {
		// Lab_Test records.
		Collection results;
		Vector inArgs = buildProcedureInput(input.getTestRequestId(),
                                            (input.getDateToLab() == null ? null : new Timestamp(input.getDateToLab().getTime())),
                                            input.getToLabPersonnelId(),
                                            (input.getDateReceivedByLab() == null ? null : new Timestamp(input.getDateReceivedByLab().getTime())),
                                            input.getSamplesReceivedPersonnelId(),
                                            (input.getDateTestComplete() == null ? null : new Timestamp(input.getDateTestComplete().getTime())),
                                            input.getTestCompletedPersonnelId(), 
                                            input.getRequestStatus(),
				                            input.getMaterialQualified(),
                                            (input.getExpirationDate() == null ? null : new Timestamp(input.getExpirationDate().getTime())),
                                            (input.getQualificationDate() == null ? null : new Timestamp(input.getQualificationDate().getTime())),
				                            input.getQualificationPersonnelId(),
                                            input.getClosedDate() == null ? null : new Timestamp(input.getClosedDate().getTime()),
                                            ("Complete".equalsIgnoreCase(input.getRequestStatus()) ? input.getClosedPersonnelId() : null),
                                            input.getSampleSize(),
				                            input.getQualityTrackingNumber());

		Vector<Integer> outArgs = new Vector<Integer>();
		outArgs.add(new Integer(java.sql.Types.VARCHAR)); // Error code
		if (log.isDebugEnabled()) {
			log.debug("inArgs:" + inArgs);
		}

		// Call the procedure to do the update for the LAB_TEST record.
		results = factory.doProcedure("P_LAB_TEST_UPDATE", inArgs, outArgs);

		StringBuilder sb = new StringBuilder("");
		String comma = "";
		for (Object r : results) {
			sb.append(r.toString()).append(comma);
			comma = ", ";
		}

		if (results == null || results.isEmpty()) {
			return "";
		}

		return sb.toString();
	}

	public String updateTestRequest(TestRequestInputBean input, PersonnelBean personnelBean) throws BaseException {
		// Lab_Test records.
		Collection results = null;
		Vector inArgs;
		StringBuilder sb = new StringBuilder("");
		String comma = "", signResult;

		signResult = signTestRequest(input, personnelBean);
		if ("OK".equals(signResult)) {
			sb.append(signResult);
			Vector<Integer> outArgs = new Vector<Integer>();
			outArgs.add(new Integer(java.sql.Types.VARCHAR)); // Error code

			// Now update the LAB_TEST_RESULTS.
			Collection<TestResultBean> tresults = input.getTestResults();
			for (TestResultBean tb : tresults) {
				inArgs = buildProcedureInput(input.getTestRequestId(), tb.getTestId(), tb.getNote(), tb.getPassFail(), tb.getLabLogNumber());
				if (log.isDebugEnabled()) {
					log.debug("inArgs:" + inArgs);
				}
				results = factory.doProcedure("P_LAB_TEST_RESULTS_UPDATE", inArgs, outArgs);
				for (Object r : results) {
					sb.append(r.toString()).append(comma);
					comma = ", ";
				}
			}
		}
		return sb.toString();
	}

	public String cancelTestRequest(TestRequestInputBean input) throws BaseException {
		// Lab_Test records.
		Collection results;
		Vector inArgs = buildProcedureInput(input.getTestRequestId(), (input.getDateCancelled() == null ? null : new Timestamp(input.getDateCancelled().getTime())), input.getCancelledBy(), input.getReasonForCancellation());

		Vector<Integer> outArgs = new Vector<Integer>();
		outArgs.add(new Integer(java.sql.Types.VARCHAR)); // Error code
		if (log.isDebugEnabled()) {
			log.debug("inArgs:" + inArgs);
		}

		// Call the procedure to do the cancel for the LAB_TEST record.
		results = factory.doProcedure("P_LAB_TEST_CANCEL", inArgs, outArgs);

		StringBuilder sb = new StringBuilder("");
		String comma = "";
		for (Object r : results) {
			sb.append(r.toString()).append(comma);
			comma = ", ";
		}

		if (results == null || results.isEmpty()) {
			return "";
		}

		return sb.toString();
	}

	public String getClientUrl(TestRequestInputBean inputBean) {
		ResourceLibrary companyLibrary = new ResourceLibrary("company");
		@SuppressWarnings("unchecked")
		Enumeration<String> companyModules = companyLibrary.getKeys();
		for (String module = companyModules.nextElement(); companyModules.hasMoreElements(); module = companyModules.nextElement()) {
			String companyName = companyLibrary.getString(module);
			if (inputBean.getCompanyId().equalsIgnoreCase(companyName)) {
				return hostUrl + "/tcmIS" + module;
			}
		}

		return URL;

	}

	public void sendNotificationMail(TestRequestInputBean inputBean, PersonnelBean personnelBean) {
		try {
			Collection c = null;
			Collection c1 = null;
			String subject = null;

			String message = null;

			String pre = "New MARS Request ";

			if ("To Lab".equalsIgnoreCase(inputBean.getRequestStatus())) {
                DbManager dbManager = new DbManager(getClient(), getLocale());
			    PersonnelBeanFactory factory = new PersonnelBeanFactory(dbManager);
                c1 = factory.selectDistinctUserByCatalogIGPermission("labPersonnel", inputBean.getCatalogId(), inputBean.getInventoryGroup(), inputBean.getCompanyId());
				subject = pre + " Requested by " + inputBean.getCreateSignature() + " is Pending Testing for " + inputBean.getCompanyId();
				message = pre + "\n\nRequested by " + inputBean.getCreateSignature() + " - has been created and is waiting for Testing. \n\n" + "Please click on the link below.\n\n" + getClientUrl(inputBean)
						+ "/testrequestform.do?uAction=search&testRequestId=" + inputBean.getTestRequestId();
			}else if ("QC".equalsIgnoreCase(inputBean.getRequestStatus())) {
                DbManager dbManagerHaas = new DbManager("TCM_OPS", getLocale());
                PersonnelBeanFactory factoryHaas = new PersonnelBeanFactory(dbManagerHaas);
                c = factoryHaas.selectDistinctUserByInventoryPermission("labPersonnel", inputBean.getInventoryGroup());
				subject = pre + " Requested by " + inputBean.getCreateSignature() + " is Pending Closing for " + inputBean.getCompanyId();
				message = pre + "\n\nRequested by " + inputBean.getCreateSignature() + " - has been tested and is waiting closure. \n\n"
                              + "Please click on the link below.\n\n"
                              + hostUrl+ "/tcmIS/haas/testrequestform.do?uAction=search&testRequestId=" + inputBean.getTestRequestId();
			}
			if (c != null) {
				String[] to = new String[c.size()];
				Iterator it = c.iterator();
				for (int i = 0; it.hasNext(); i++) {
					PersonnelBean b = (PersonnelBean) it.next();
					to[i] = b.getEmail();
				}

				String[] cc = new String[0];
				if (to.length > 0) {
					MailProcess.sendEmail(to, cc, subject, message, true);
				}
			}
			if (c1 != null) {
				String[] to1 = new String[c1.size()];
				Iterator it1 = c1.iterator();
				for (int j = 0; it1.hasNext(); j++) {
					PersonnelBean b = (PersonnelBean) it1.next();
					to1[j] = b.getEmail();
				}
				String[] cc1 = new String[0];
				if (to1.length > 0) {
					MailProcess.sendEmail(to1, cc1, subject, message, true);
				}
			}
		}
		catch (Exception e) {
			log.error("Error sending email.", e);
		}
	}

	@SuppressWarnings("unchecked")
	public Collection getFacilitySelect(int personnelId) throws BaseException {

	  	DbManager dbManager = new DbManager(getClient(),getLocale());
	  	GenericSqlFactory factory = new GenericSqlFactory(dbManager, new UserFacilityViewBean());
        StringBuilder query = new StringBuilder("SELECT uf.company_id, uf.personnel_id, uf.facility_id, f.facility_name, f.active FROM user_facility uf, facility f");
        query.append(" WHERE uf.company_id = f.company_id ");
        query.append("  AND uf.facility_id = f.facility_id");
        query.append("  AND uf.personnel_id = " + personnelId);
        query.append(" ORDER BY facility_name");
        
        Collection facilityCol = factory.selectQuery(query.toString());
        
	  	
	  	return facilityCol;
	}
}
