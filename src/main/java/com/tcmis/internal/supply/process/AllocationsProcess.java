/**
 * 
 */
package com.tcmis.internal.supply.process;

import java.util.Collection;
import java.util.Locale;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;
import com.tcmis.common.web.IHaasConstants;
import com.tcmis.internal.supply.beans.AllocationsInputBean;
import com.tcmis.internal.supply.beans.MrAllocationViewBean;

/**
 * @author spiros.petratos
 * 
 */
public class AllocationsProcess extends BaseProcess
{
	private static final String	ALLOCATIONANALYSIS_LABEL_SUPPLIERMFGLOT	= "allocationanalysis.label.suppliermfglot";
	private static final String	BUY_ORDER								= "BuyOrder";
	private static final String	COMPANY_ID								= "companyId";
	private static final String	DOC_NUM									= "docNum";
	private static final String	DOC_TYPE								= "docType";
	private static final String	HUB										= "hub";
	private static final String	INVENTORY_GROUP							= "inventoryGroup";
	private static final String	LABEL_ALLOCATIONTYPE					= "label.allocationtype";
	private static final String	LABEL_CSR								= "label.csr";
	private static final String	LABEL_CUSTOMER							= "label.customer";
	private static final String	LABEL_DATE_CREATED						= "label.date.created";
	private static final String	LABEL_DEDICATED							= "label.dedicated";
	private static final String	LABEL_DELIVERYDATE						= "label.deliverydate";
	private static final String	LABEL_DESCRIPTION						= "label.description";
	private static final String	LABEL_INVENTORYGROUP					= "label.inventorygroup";
	private static final String	LABEL_ITEMID							= "label.itemid";
	private static final String	LABEL_CATPARTNO							= "label.catpartno";
	private static final String	LABEL_REFERENCE							= "label.reference";
	private static final String	MR										= "MR";
	private static final String	PO										= "PO";
	private static final String	PR_NUMBER								= "prNumber";
	private static final String	RECEIPT									= "Receipt";
	private static final String	TABLE_NAME								= "MR_ALLOCATION_VIEW";

	/**
	 * @param client
	 */
	public AllocationsProcess(String client)
	{
		super(client);
	}

	/**
	 * @param client
	 * @param locale
	 */
	public AllocationsProcess(String client, String locale)
	{
		super(client, locale);
	}

	/**
	 * @param inputBean
	 * @return
	 * @throws BaseException
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Collection<MrAllocationViewBean> getAllocationsViewBeanCollection(
			AllocationsInputBean inputBean) throws BaseException, Exception
	{
		DbManager dbManager = new DbManager(getClient(), getLocale());

		GenericSqlFactory allocationsFactory = new GenericSqlFactory(dbManager,
				new MrAllocationViewBean());
		Collection<MrAllocationViewBean> searchResult = null;
		SearchCriteria searchCriteria = new SearchCriteria();

		if (inputBean.getOpsEntityId() != null
				&& inputBean.getOpsEntityId().length() > 0)
		{
			//searchCriteria = addSearchCriteria(searchCriteria, COMPANY_ID,
			//SearchCriterion.EQUALS, inputBean.getOpsEntityId());
		}

		if (inputBean.getHub() != null && inputBean.getHub().length() > 0)
		{
			searchCriteria = addSearchCriteria(searchCriteria, HUB,
					SearchCriterion.EQUALS, inputBean.getHub());
		}

		if (inputBean.getInventoryGroup() != null
				&& inputBean.getInventoryGroup().length() > 0)
		{
			searchCriteria = addSearchCriteria(searchCriteria, INVENTORY_GROUP,
					SearchCriterion.EQUALS, inputBean.getInventoryGroup());
		}


        if (!StringHandler.isBlankString(inputBean.getSearchArgument()))
		{
			String searchArgument = inputBean.getSearchArgument();
			if (inputBean.getSearchField() != null
					&& inputBean.getSearchField().equals(MR))
			{
				searchCriteria = addSearchCriteria(searchCriteria, PR_NUMBER,
						SearchCriterion.EQUALS, searchArgument);
			}
			if (inputBean.getSearchField() != null
					&& inputBean.getSearchField().equals("itemId"))
			{
				searchCriteria = addSearchCriteria(searchCriteria, "itemId",
						SearchCriterion.EQUALS, searchArgument);
			}
            else
			{
				if (inputBean.getSearchField() != null
						&& inputBean.getSearchField().equals(PO))
				{
					searchCriteria = addSearchCriteria(searchCriteria,
							DOC_TYPE, SearchCriterion.EQUALS, PO);
					searchCriteria = addSearchCriteria(searchCriteria, DOC_NUM,
							SearchCriterion.EQUALS, searchArgument);
				}
				else
				{
					if (inputBean.getSearchField() != null
							&& inputBean.getSearchField().equals(RECEIPT))
					{
						searchCriteria = addSearchCriteria(searchCriteria,
								DOC_TYPE, SearchCriterion.EQUALS, "OV");
						searchCriteria = addSearchCriteria(searchCriteria,
								DOC_NUM, SearchCriterion.EQUALS, searchArgument);
					}
					else
					{
						if (inputBean.getSearchField() != null
								&& inputBean.getSearchField().equals(BUY_ORDER))
						{
							searchCriteria = addSearchCriteria(searchCriteria,
									DOC_TYPE, SearchCriterion.EQUALS, "PR");
							searchCriteria = addSearchCriteria(searchCriteria,
									DOC_NUM, SearchCriterion.EQUALS,
									searchArgument);
						}
						else
						{
							searchCriteria = addSearchCriteria(searchCriteria,
									inputBean.getSearchField(),
									SearchCriterion.EQUALS, searchArgument);
						}
					}
				}
			}
		}
		searchResult = allocationsFactory.select(searchCriteria, null,
				TABLE_NAME);

		inputBean.setTotalLines(searchResult.size());
		return searchResult;
	}

	private SearchCriteria addSearchCriteria(SearchCriteria searchCriteria,
			String property, String criterion, String propertyValue)
	{
		searchCriteria.addCriterion(property, criterion, propertyValue);
		return searchCriteria;
	}

	/**
	 * @param inputBean
	 * @param locale
	 * @return
	 * @throws BaseException
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public ExcelHandler getExcelReport(AllocationsInputBean inputBean,
			Locale locale) throws BaseException, Exception
	{
		ResourceLibrary library = new ResourceLibrary(
				IHaasConstants.CST_TCM_COMMON_RESOURCES, locale);
		Collection<MrAllocationViewBean> data = getAllocationsViewBeanCollection(inputBean);

		ExcelHandler pw = new ExcelHandler(library);
		pw.addTable();
		// write column headers
		pw.addRow();

		String[] headerkeys = { LABEL_ALLOCATIONTYPE, LABEL_REFERENCE,
				ALLOCATIONANALYSIS_LABEL_SUPPLIERMFGLOT, LABEL_ITEMID,
				LABEL_DESCRIPTION, LABEL_INVENTORYGROUP, LABEL_CATPARTNO,
				LABEL_DATE_CREATED, LABEL_DELIVERYDATE, LABEL_DEDICATED,
				LABEL_CUSTOMER, LABEL_CSR };
		int[] widths = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		int[] types = { pw.TYPE_STRING, pw.TYPE_STRING, pw.TYPE_STRING,
				pw.TYPE_NUMBER, pw.TYPE_STRING, pw.TYPE_STRING, pw.TYPE_STRING,
				pw.TYPE_CALENDAR, pw.TYPE_CALENDAR, pw.TYPE_STRING,
				pw.TYPE_STRING, pw.TYPE_STRING };

		int[] aligns = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		pw.applyColumnHeader(headerkeys, types, widths, aligns);

		for (MrAllocationViewBean allocation : data)
		{
			pw.addRow();
			pw.addCell(allocation.getDocType());
			pw.addCell(allocation.getDocNum());
			pw.addCell(allocation.getMfgLot());
			pw.addCell(allocation.getItemId());
			pw.addCell(allocation.getItemDesc());
			pw.addCell(allocation.getInventoryGroup());
			pw.addCell(allocation.getCatPartNo());
			pw.addCell(allocation.getDateCreated());
			pw.addCell(allocation.getDateToDeliver());
			pw.addCell(allocation.getDedicated());
			pw.addCell(allocation.getCustomerName());
			pw.addCell(allocation.getCsrName());
		}
		return pw;
	}

}
