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
import com.tcmis.internal.supply.beans.MRCancellationInputBean;
import com.tcmis.internal.supply.beans.MRCancellationViewBean;

/**
 * @author spiros.petratos
 * 
 */
public class MRCancellationProcess extends BaseProcess
{
	private static final String	BUY_ORDER								= "BuyOrder";
	private static final String	BUYER_ID								= "buyerId";
	private static final String	BUYER_ID_CHECKED						= "Y";
	private static final String	COMPANY_ID								= "opsEntityId";
	private static final String	DOC_NUM									= "docNum";
	private static final String	DOC_TYPE								= "docType";
	private static final String	HUB										= "hub";
	private static final String	INVENTORY_GROUP							= "inventoryGroup";
	private static final String	LABEL_CSR								= "label.csr";
	private static final String	LABEL_CUSTOMER							= "label.customer";
	private static final String	LABEL_DESCRIPTION						= "label.description";
	private static final String	LABEL_INVENTORYGROUP					= "label.inventorygroup";
	private static final String	LABEL_MR								= "label.mr";
	private static final String	LABEL_NEEDDATE							= "label.needdate";
	private static final String	LABEL_ORDERQTY							= "label.orderedqty";
	private static final String	LABEL_PARTNUMBER						= "label.partnumber";
	private static final String	LABEL_PROMISEDDATE						= "label.promiseddate";
	private static final String	LABEL_QTYONBUYORDER						= "label.qtyonbuyorder";
	private static final String	LABEL_QTYONPO							= "label.qtyonpo";
	private static final String	LABEL_QTYSHIPPED						= "label.qtyshipped";
	private static final String	LABEL_REQUESTOR							= "label.requestor";
	private static final String	MR										= "MR";
	private static final String	PO										= "PO";
	private static final String	PR_NUMBER								= "prNumber";
	private static final String	RECEIPT									= "Receipt";
	private static final String	TABLE_NAME								= "REQUEST_CANCEL_VIEW";

	/**
	 * @param client
	 */
	public MRCancellationProcess(String client)
	{
		super(client);
	}

	/**
	 * @param client
	 * @param locale
	 */
	public MRCancellationProcess(String client, String locale)
	{
		super(client, locale);
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
	public ExcelHandler getExcelReport(MRCancellationInputBean inputBean,
			Locale locale) throws BaseException, Exception
	{
		ResourceLibrary library = new ResourceLibrary(
				IHaasConstants.CST_TCM_COMMON_RESOURCES, locale);
		Collection<MRCancellationViewBean> data = getMRCancellationViewBeanCollection(inputBean);

		ExcelHandler pw = new ExcelHandler(library);
		pw.addTable();
		// write column headers
		pw.addRow();

		String[] headerkeys = { LABEL_MR, LABEL_INVENTORYGROUP, LABEL_PARTNUMBER, 
				LABEL_DESCRIPTION, LABEL_ORDERQTY, LABEL_QTYSHIPPED, 
				LABEL_QTYONBUYORDER, LABEL_QTYONPO, LABEL_CUSTOMER, 
				LABEL_CSR, LABEL_REQUESTOR, LABEL_NEEDDATE, LABEL_PROMISEDDATE};
		int[] widths = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		int[] types = { pw.TYPE_STRING, pw.TYPE_STRING, pw.TYPE_NUMBER, 
				pw.TYPE_STRING, pw.TYPE_NUMBER, pw.TYPE_NUMBER, 
				pw.TYPE_NUMBER, pw.TYPE_NUMBER, pw.TYPE_STRING, 
				pw.TYPE_STRING, pw.TYPE_STRING, pw.TYPE_STRING, pw.TYPE_CALENDAR};

		int[] aligns = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		pw.applyColumnHeader(headerkeys, types, widths, aligns);

		for (MRCancellationViewBean mrcancellation: data)
		{
			pw.addRow();
			pw.addCell(mrcancellation.getMrDataString());
			pw.addCell(mrcancellation.getInventoryGroup());
			pw.addCell(mrcancellation.getFacPartNo());
			pw.addCell(mrcancellation.getItemDesc());
			pw.addCell(mrcancellation.getQuantity());
			pw.addCell(mrcancellation.getShippedQuantity());
			pw.addCell(mrcancellation.getQtyOnBuyOrder());
			pw.addCell(mrcancellation.getQtyOnPurchaseOrder());
			pw.addCell(mrcancellation.getCustomerName());
			pw.addCell(mrcancellation.getCsrName());
			pw.addCell(mrcancellation.getRequestorName());
			pw.addCell(mrcancellation.getNeedDateString());
			pw.addCell(mrcancellation.getPromiseDate());
		}
		return pw;
	}

	/**
	 * @param inputBean
	 * @return
	 * @throws BaseException
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Collection<MRCancellationViewBean> getMRCancellationViewBeanCollection(
			MRCancellationInputBean inputBean) throws BaseException, Exception
	{
		DbManager dbManager = new DbManager(getClient(), getLocale());

		GenericSqlFactory mrCancellationFactory = new GenericSqlFactory(dbManager,
				new MRCancellationViewBean());
		Collection<MRCancellationViewBean> searchResult = null;
		SearchCriteria searchCriteria = new SearchCriteria();

		if (inputBean.getOpsEntityId() != null
				&& inputBean.getOpsEntityId().length() > 0)
		{
			searchCriteria = addSearchCriteria(searchCriteria, COMPANY_ID,
					SearchCriterion.EQUALS, inputBean.getOpsEntityId());
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

		if (inputBean.getCheckBuyerId() != null
				&& inputBean.getCheckBuyerId().equals(BUYER_ID_CHECKED))
		{
			searchCriteria = addSearchCriteria(searchCriteria, BUYER_ID,
					SearchCriterion.EQUALS, inputBean.getCurrentUser());
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
		searchResult = mrCancellationFactory.select(searchCriteria, null,
				TABLE_NAME);

		inputBean.setTotalLines(searchResult.size());
		return searchResult;
	}

}
