package com.tcmis.client.report.process;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import com.tcmis.client.common.beans.FacilityGroupBean;
import com.tcmis.client.report.beans.FacilityPrRulesBean;
import com.tcmis.common.admin.beans.FacilityBean;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.catalog.beans.MyWorkareaFacilityViewBean;
import com.tcmis.client.common.beans.PrRulesViewBean;
import com.tcmis.client.report.beans.ReceiptDocsObjBean;
import com.tcmis.client.report.beans.ReceiptDocumentViewerInputBean;
import com.tcmis.client.report.factory.DeliveredReceiptDocsOvBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.admin.beans.PersonnelBean;

/******************************************************************************
 * Process to build a web page for user to view receipt documents.
 * @version 1.0
 *****************************************************************************/
public class ReceiptDocumentViewerProcess extends GenericProcess {
	Log log = LogFactory.getLog(this.getClass());

	public ReceiptDocumentViewerProcess(String client) {
		super(client);
	}

	public ReceiptDocumentViewerProcess(String client, String locale) {
		super(client, locale);
	}

	public boolean isFeatureRelease(PersonnelBean personnelBean, ReceiptDocumentViewerInputBean inputBean, String featureRelease) {
	    try {
            StringBuilder facilityIds = new StringBuilder("");
            if (!StringHandler.isBlankString(inputBean.getFacilityId())) {
                getIdsString(facilityIds, inputBean.getFacilityId());
            } else {
                if (!StringHandler.isBlankString(inputBean.getFacilityIdSel()))
                    facilityIds.append("'").append(inputBean.getFacilityIdSel()).append("'");
                else
                    facilityIds.append(getFacilityIdsString(inputBean, personnelBean.getPersonnelIdBigDecimal()));
            }
            StringBuilder query = new StringBuilder("select count(*) from feature_release where feature = '").append(featureRelease).append("'");
            query.append(" and active = 'Y' and company_id = '").append(inputBean.getCompanyId()).append("'");
            query.append(" and scope in (").append(facilityIds.toString()).append(")");
            return ((new BigDecimal(factory.selectSingleValue(query.toString()))).intValue() > 0);
        }catch(Exception e) {
	        e.printStackTrace();
	        return false;
        }
    }

	private void getIdsString(StringBuilder idsString, String selectedList) {
		String[] idsList = selectedList.split("\\|");
		for (int i = 0; i < idsList.length; i++) {
			if (i > 0)
				idsString.append(",");
			idsString.append("'").append(idsList[i]).append("'");
		}
	}

	public Collection getUserFacilityGroupFacilityDropDownData(BigDecimal personnelId, String companyId) throws Exception {
		Vector results = new Vector();
		factory.setBean(new FacilityGroupBean());
		StringBuilder query = new StringBuilder("select * from (select nvl(fg.company_id,uf.company_id) company_id,nvl(fg.facility_group_id,'Other') facility_group_id,nvl(fg.facility_group_description,'Other') facility_group_description,uf.facility_id,f.facility_name,2 display_order");
		query.append(" from user_facility uf, facility_group_member fgm, facility_group fg, facility f");
		query.append(" where uf.company_id = fgm.company_id(+) and uf.facility_id = fgm.facility_id(+) and fgm.company_id = fg.company_id(+)");
		query.append(" and fgm.facility_group_id = fg.facility_group_id(+) and uf.company_id = f.company_id and uf.facility_id = f.facility_id");
		query.append(" and uf.personnel_id = ").append(personnelId).append(" and uf.company_id = '").append(companyId).append("' and exists (select null from user_application ua where uf.company_id = ua.company_id");
		query.append(" and uf.facility_id = ua.facility_id and uf.personnel_id = ua.personnel_id)");
		query.append(" union all");
		query.append(" select uf.company_id,'All' facility_group_id,'My Facility Groups' facility_group_description,uf.facility_id,f.facility_name,1 display_order");
		query.append(" from user_facility uf, facility f");
		query.append(" where uf.company_id = f.company_id and uf.facility_id = f.facility_id and uf.personnel_id = ").append(personnelId).append(" and uf.company_id = '").append(companyId).append("'");
		query.append(" and exists (select null from user_application ua where uf.company_id = ua.company_id");
		query.append(" and uf.facility_id = ua.facility_id and uf.personnel_id = ua.personnel_id)");
		query.append(" order by display_order, facility_group_description,facility_name)");
		Collection<FacilityGroupBean> beans = factory.selectQuery(query.toString());
		String lastFacilityGroupId = "";
		for (FacilityGroupBean bean : beans) {
			if (lastFacilityGroupId.equals(bean.getFacilityGroupId())) {
				FacilityGroupBean facilityGroupBean = (FacilityGroupBean)results.lastElement();
				Collection facilityIdList = facilityGroupBean.getFacilityList();
				FacilityBean facilityBean = new FacilityBean();
				facilityBean.setFacilityId(bean.getFacilityId());
				facilityBean.setFacilityName(bean.getFacilityName());
				facilityIdList.add(facilityBean);
			}else {
				Collection facilityIdList = new Vector();
				FacilityBean facilityBean = new FacilityBean();
				facilityBean.setFacilityId(bean.getFacilityId());
				facilityBean.setFacilityName(bean.getFacilityName());
				facilityIdList.add(facilityBean);
				bean.setFacilityList(facilityIdList);
				results.add(bean);
			}
			lastFacilityGroupId = bean.getFacilityGroupId();
		}
		return results;
	} //end of method

	private String buildFacilityIdsString(Collection facilityCollection) {
		StringBuilder facilityIdsString = new StringBuilder("");
		Iterator iter = facilityCollection.iterator();
		int count = 0;
		while (iter.hasNext()) {
			FacilityGroupBean facilityGroupBean = (FacilityGroupBean)iter.next();
			if (count > 0)
				facilityIdsString.append(",");
			facilityIdsString.append("'").append(facilityGroupBean.getFacilityId()).append("'");
			count++;
		}
		return facilityIdsString.toString();
	}

	private String getFacilityIdsFromFacilityGroup(String companyId, String facilityGroupIds) throws Exception {
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("companyId", SearchCriterion.EQUALS, companyId);
		criteria.addCriterion("facilityGroupId", SearchCriterion.IN, facilityGroupIds);
		factory.setBeanObject(new FacilityGroupBean());
		return buildFacilityIdsString(factory.select(criteria,null,"facility_group_member"));
	}

	private String getMyFacilityIds(String companyId, BigDecimal personnelId) throws Exception {
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("companyId", SearchCriterion.EQUALS, companyId);
		criteria.addCriterion("personnelId", SearchCriterion.IN, personnelId.toString());
		factory.setBeanObject(new FacilityGroupBean());
		return buildFacilityIdsString(factory.select(criteria,null,"user_facility"));
	}

	private String getMyFacilityIdsNotMemberOfGroup(String companyId, BigDecimal personnelId) throws Exception {
		StringBuilder query = new StringBuilder("select uf.facility_id from user_facility uf where company_id = '").append(companyId).append("'");
		query.append(" and personnel_id = ").append(personnelId);
		query.append(" and not exists (select null from facility_group_member fgm where uf.company_id = fgm.company_id");
		query.append(" and uf.facility_id = fgm.facility_id)");
		factory.setBeanObject(new FacilityGroupBean());
		return buildFacilityIdsString(factory.selectQuery(query.toString()));
	}

	private void getFacilityGroupIdsString(StringBuilder facilityGroupIds, String selectedFacilityGroup, String selectedSingleFacility) {
		if (!StringHandler.isBlankString(selectedFacilityGroup))
			getIdsString(facilityGroupIds, selectedFacilityGroup);
		else if (!"All".equals(selectedSingleFacility))
			facilityGroupIds.append(selectedSingleFacility);
	}

	private String getFacilityIdsString(ReceiptDocumentViewerInputBean inputBean, BigDecimal personnelId) throws Exception{
		StringBuilder facilityGroupIds = new StringBuilder("");
		getFacilityGroupIdsString(facilityGroupIds,inputBean.getFacilityGroupId(),inputBean.getFacilityGroupIdSel());
		String facilityIds = "";
        if (!StringHandler.isBlankString(facilityGroupIds.toString())) {
        	if ("Other".equals(facilityGroupIds.toString()))
				facilityIds = getMyFacilityIdsNotMemberOfGroup(inputBean.getCompanyId(),personnelId);
        	else
				facilityIds = getFacilityIdsFromFacilityGroup(inputBean.getCompanyId(), facilityGroupIds.toString());
		}else
			facilityIds = getMyFacilityIds(inputBean.getCompanyId(),personnelId);
		return facilityIds;
	}

	public Collection getSearchResult(ReceiptDocumentViewerInputBean inputBean, BigDecimal personnelId) throws Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		DeliveredReceiptDocsOvBeanFactory factory = new DeliveredReceiptDocsOvBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();

		//facility
		if (!StringHandler.isBlankString(inputBean.getFacilityId())) {
			StringBuilder facilityIds = new StringBuilder("");
			getIdsString(facilityIds, inputBean.getFacilityId());
			criteria.addCriterion("facilityId", SearchCriterion.IN, facilityIds.toString());
		} else {
			if (!StringHandler.isBlankString(inputBean.getFacilityIdSel()))
				criteria.addCriterion("facilityId", SearchCriterion.EQUALS, inputBean.getFacilityIdSel());
			else
				criteria.addCriterion("facilityId", SearchCriterion.IN, getFacilityIdsString(inputBean,personnelId));
		}
		//work area
		if (inputBean.getApplicationId() != null && inputBean.getApplicationId().length() > 0)
			criteria.addCriterion("application", SearchCriterion.EQUALS, inputBean.getApplicationId());

		/* Searching for delivery FROM date */
		if (inputBean.getDeliveryFromDate() != null)
			criteria.addCriterion("dateDelivered", SearchCriterion.FROM_DATE, inputBean.getDeliveryFromDate());

		/* Searching for delivery TO date */
		if (inputBean.getDeliveryToDate() != null)
			criteria.addCriterion("dateDelivered", SearchCriterion.TO_DATE, inputBean.getDeliveryToDate());
		
		/* Searching for delivery FROM date */
		if (inputBean.getExpireFromDate() != null)
			criteria.addCriterion("expireDate", SearchCriterion.FROM_DATE, inputBean.getExpireFromDate());

		/* Searching for delivery TO date */
		if (inputBean.getExpireToDate() != null)
			criteria.addCriterion("expireDate", SearchCriterion.TO_DATE, inputBean.getExpireToDate());

		String additionalWhere = "";
		if (!StringHandler.isBlankString(inputBean.getSearchText())) {
			if ("STARTS_WITH".equals(inputBean.getSearchType())) {
				criteria.addCriterion(inputBean.getSearchBy(), " LIKE", inputBean.getSearchText(), SearchCriterion.IGNORE_CASE);
			}
			else if ("ENDS_WITH".equals(inputBean.getSearchType())) {
				criteria.addCriterion(inputBean.getSearchBy(), "LIKE ", inputBean.getSearchText(), SearchCriterion.IGNORE_CASE);
			}
			else {
				criteria.addCriterion(inputBean.getSearchBy(), inputBean.getSearchType(), inputBean.getSearchText(), SearchCriterion.IGNORE_CASE);
			}
		}

		SortCriteria sort = new SortCriteria();
		if (inputBean.isGroupByMR()) {
			sort.addCriterion("mrLine");
			sort.addCriterion("dateDelivered");
			sort.addCriterion("receiptId");
		}
		else {
			sort.addCriterion("mfgLot");
			sort.addCriterion("receiptId");
			sort.addCriterion("mrLine");

		}
		return factory.selectObject(criteria, sort);
	} //end of method

	public ExcelHandler getExcelReport(PersonnelBean user, ReceiptDocumentViewerInputBean inputBean, Collection bean, Locale locale) throws NoDataException, BaseException {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		Collection<ReceiptDocsObjBean> data = bean;
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
		//write column headers
		pw.addRow();
		/*Pass the header keys for the Excel.*/
		if (inputBean.isGroupByMR()) {
			if (inputBean.showDeliveriesCostData()) {
				if(inputBean.isPoRequiredFacility())
				{
					String[] headerkeys = { "label.mrline", "label.podaskline", "label.workarea", "label.catalog", "label.mrquantity", "label.partno", "label.partdesc", "label.receipt", "label.lot", "label.expiredate",
							"label.item", "label.itemdesc", "label.qty", "label.extendedprice", "label.additional.charges", "label.freightcharge", "label.delivereddate", "label.shipmentid", "label.confirmedby", "label.dateconfirmed", "label.comments" };
					/*This array defines the type of the excel column.
					0 means default depending on the data type. */
					int[] types = { 0, 0, 0, 0, 0, 0, pw.TYPE_PARAGRAPH, 0, 0, pw.TYPE_CALENDAR, 0, pw.TYPE_PARAGRAPH, 0, 0, 0, 0, pw.TYPE_CALENDAR, 0, 0, pw.TYPE_CALENDAR, pw.TYPE_PARAGRAPH };
					
					/*This array defines the default width of the column when the Excel file opens.
					0 means the width will be default depending on the data type.*/
					int[] widths = { 0, 0, 15, 0, 0, 20, 0, 0, 20, 0, 0, 0, 0, 15, 15, 0, 0, 10, 15, 15, 0 };
					/*This array defines the horizontal alignment of the data in a cell.
					0 means excel defaults the horizontal alignemnt by the data type.*/
					int[] horizAligns = { 0, 0, 0, 0,  pw.ALIGN_CENTER, 0, 0, 0, 0, 0, 0, 0, pw.ALIGN_CENTER, 0, 0, 0, 0, 0, 0, 0, 0 };
	
					pw.setColumnDigit(13, 4);
					pw.setColumnDigit(14, 4);
					pw.setColumnDigit(15, 4);
					pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
				}
				else
				{
					String[] headerkeys = { "label.mrline", "label.workarea", "label.catalog", "label.mrquantity", "label.partno", "label.partdesc", "label.receipt", "label.lot", "label.expiredate",
							"label.item", "label.itemdesc", "label.qty", "label.extendedprice", "label.additional.charges", "label.freightcharge", "label.delivereddate", "label.shipmentid", "label.confirmedby", "label.dateconfirmed", "label.comments" };
					/*This array defines the type of the excel column.
					0 means default depending on the data type. */
					int[] types = { 0, 0, 0, 0, 0, pw.TYPE_PARAGRAPH, 0, 0, pw.TYPE_CALENDAR, 0, pw.TYPE_PARAGRAPH, 0, 0, 0, 0,pw.TYPE_CALENDAR, 0, 0, pw.TYPE_CALENDAR, pw.TYPE_PARAGRAPH };
					
					/*This array defines the default width of the column when the Excel file opens.
					0 means the width will be default depending on the data type.*/
					int[] widths = { 0,  15, 0, 0, 20, 0, 0, 20, 0, 0, 0, 0, 15, 15, 0, 0, 10, 15, 15, 0 };
					/*This array defines the horizontal alignment of the data in a cell.
					0 means excel defaults the horizontal alignemnt by the data type.*/
					int[] horizAligns = { 0,  0, 0, pw.ALIGN_CENTER, 0, 0, 0, 0, 0, 0, 0, pw.ALIGN_CENTER, 0, 0, 0, 0, 0, 0, 0, 0 };
	
					pw.setColumnDigit(12, 4);
					pw.setColumnDigit(13, 4);
					pw.setColumnDigit(14, 4);
					pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
				}

				String preMRLine = "";
				String indefinite = library.getString("label.indefinite");
				SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy");
				 NumberFormat formatter = new DecimalFormat("#,##0.00");


				// now write data
				for (ReceiptDocsObjBean member : data) {
					pw.addRow();
					String curMRLine = member.getMrLine() + member.getDateDelivered();
					pw.addCell(member.getMrLine());
					if(inputBean.isPoRequiredFacility())
					{
						if ("-".equals(member.getPoLine()))
							pw.addCell("");
						else
							pw.addCell(member.getPoLine());
					}
					pw.addCell(member.getApplicationDesc());
					pw.addCell(member.getCatalogDesc());
					pw.addCell(member.getMrLineQty());
					pw.addCell(member.getFacPartNo());
					pw.addCell(member.getPartDescription());
					pw.addCell(member.getReceiptId());
					pw.addCell(member.getMfgLot());
					if (member.getExpireDate() != null && "3000".equals(simpleDateformat.format(member.getExpireDate())))
						pw.addCell(indefinite);
					else
						pw.addCell(member.getExpireDate());
					pw.addCell(member.getItemId());
					pw.addCell(member.getItemDesc());
					pw.addCell(member.getQuantityIssued());
					if ( isBlank(member.getExtendedPrice()) )
						pw.addCell("");
					else
						pw.addCell(formatter.format(member.getExtendedPrice()) + " " + member.getCurrencyId());
					if ( isBlank(member.getAdditionalCharges()) )
						pw.addCell("");
					else
						pw.addCell(formatter.format(member.getAdditionalCharges()) + " " + member.getCurrencyId());
					if ( isBlank(member.getFreightCharges()) )
						pw.addCell("");
					else
						pw.addCell(formatter.format(member.getFreightCharges()) + " " + member.getCurrencyId());
					
					pw.addCell(member.getDateDelivered());
					pw.addCell(member.getShipmentId());
					pw.addCell(member.getConfirmedBy());
					pw.addCell(member.getDeliveryConfirmationDate());
					pw.addCell(member.getDeliveryComments());
					preMRLine = member.getMrLine() + member.getDateDelivered();
				}

			}
			else {
				if(inputBean.isPoRequiredFacility())
				{
					String[] headerkeys = { "label.mrline", "label.podaskline", "label.workarea", "label.catalog", "label.mrquantity", "label.partno", "label.partdesc", "label.receipt", "label.lot", "label.expiredate",
							"label.item", "label.itemdesc", "label.qty", "label.delivereddate", "label.shipmentid", "label.confirmedby", "label.dateconfirmed", "label.comments" };
					/*This array defines the type of the excel column.
					0 means default depending on the data type. */
					int[] types = { 0, 0, 0, 0, 0, 0, pw.TYPE_PARAGRAPH, 0, 0, pw.TYPE_CALENDAR, 0, pw.TYPE_PARAGRAPH, 0, pw.TYPE_CALENDAR, 0, 0, pw.TYPE_CALENDAR, pw.TYPE_PARAGRAPH };
					/*This array defines the default width of the column when the Excel file opens.
					0 means the width will be default depending on the data type.*/
					int[] widths = { 0, 0,  15, 0, 0, 20, 0, 0, 20, 0, 0, 0, 0, 0, 10, 15, 15, 0 };
					/*This array defines the horizontal alignment of the data in a cell.
					0 means excel defaults the horizontal alignemnt by the data type.*/
					int[] horizAligns = { 0, 0, 0, 0, 0, pw.ALIGN_CENTER, 0, 0, 0, 0, 0, 0, 0, 0, 0, pw.ALIGN_CENTER, 0, 0, 0 };
					pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
				}
				else
				{
					String[] headerkeys = { "label.mrline", "label.workarea", "label.catalog", "label.mrquantity", "label.partno", "label.partdesc", "label.receipt", "label.lot", "label.expiredate",
							"label.item", "label.itemdesc", "label.qty", "label.delivereddate", "label.shipmentid", "label.confirmedby", "label.dateconfirmed", "label.comments" };
					/*This array defines the type of the excel column.
					0 means default depending on the data type. */
					int[] types = { 0, 0, 0, 0, 0, pw.TYPE_PARAGRAPH, 0, 0, pw.TYPE_CALENDAR, 0, pw.TYPE_PARAGRAPH, 0, pw.TYPE_CALENDAR, 0, 0, pw.TYPE_CALENDAR, pw.TYPE_PARAGRAPH };
					/*This array defines the default width of the column when the Excel file opens.
					0 means the width will be default depending on the data type.*/
					int[] widths = { 0,  15, 0, 0, 20, 0, 0, 20, 0, 0, 0, 0, 0, 10, 15, 15, 0 };
					/*This array defines the horizontal alignment of the data in a cell.
					0 means excel defaults the horizontal alignemnt by the data type.*/
					int[] horizAligns = { 0, 0, 0, pw.ALIGN_CENTER, 0, 0, 0, 0, 0, 0, 0, 0, 0, pw.ALIGN_CENTER, 0, 0, 0 };
					pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
				}

				String preMRLine = "";
				String indefinite = library.getString("label.indefinite");
				SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy");

				// now write data
				for (ReceiptDocsObjBean member : data) {
					pw.addRow();
					String curMRLine = member.getMrLine() + member.getDateDelivered();
					pw.addCell(member.getMrLine());
					if(inputBean.isPoRequiredFacility())
					{
						if ("-".equals(member.getPoLine()))
							pw.addCell("");
						else
							pw.addCell(member.getPoLine());
					}
					pw.addCell(member.getApplicationDesc());
					pw.addCell(member.getCatalogDesc());
					pw.addCell(member.getMrLineQty());
					pw.addCell(member.getFacPartNo());
					pw.addCell(member.getPartDescription());
					pw.addCell(member.getReceiptId());
					pw.addCell(member.getMfgLot());
					if (member.getExpireDate() != null && "3000".equals(simpleDateformat.format(member.getExpireDate())))
						pw.addCell(indefinite);
					else
						pw.addCell(member.getExpireDate());
					pw.addCell(member.getItemId());
					pw.addCell(member.getItemDesc());
					pw.addCell(member.getQuantityIssued());
					pw.addCell(member.getDateDelivered());
					pw.addCell(member.getShipmentId());
					pw.addCell(member.getConfirmedBy());
					pw.addCell(member.getDeliveryConfirmationDate());
					pw.addCell(member.getDeliveryComments());
					preMRLine = member.getMrLine() + member.getDateDelivered();
				}
			}
		}
		else {
			if (inputBean.showDeliveriesCostData()) {
				if(inputBean.isPoRequiredFacility())
				{
					String[] headerkeys = { "label.lot", "label.expiredate", "label.item", "label.itemdesc", "label.receipt", "label.qty", "label.extendedprice", "label.additional.charges", "label.freightcharge", "label.workarea", "label.catalog",
							"label.mrline", "label.podaskline", "label.delivereddate", "label.mrquantity", "label.partno", "label.partdesc", "label.shipmentid", "label.confirmedby", "label.dateconfirmed", "label.comments" };
					/*This array defines the type of the excel column.
					0 means default depending on the data type. */
					int[] types = { 0, pw.TYPE_CALENDAR, 0, pw.TYPE_PARAGRAPH, 0, 0, 0, 0, 0, 0, 0, 0, 0, pw.TYPE_CALENDAR, 0, 0, pw.TYPE_PARAGRAPH, 0, 0, pw.TYPE_CALENDAR, pw.TYPE_PARAGRAPH };
					/*This array defines the default width of the column when the Excel file opens.
					0 means the width will be default depending on the data type.*/
					int[] widths = { 20, 0, 0, 0, 0, 0, 15, 15, 0, 20, 0, 0, 0, 0, 0, 20, 0, 10, 15, 15, 0 };
					/*This array defines the horizontal alignment of the data in a cell.
					0 means excel defaults the horizontal alignemnt by the data type.*/
					int[] horizAligns = { 0, 0, 0, 0, 0, pw.ALIGN_CENTER, 0, 0, 0, 0, 0, 0, 0, 0, pw.ALIGN_CENTER, 0, 0, 0, 0, 0, 0 };
	
					pw.setColumnDigit(6, 4);
					pw.setColumnDigit(7, 4);
					pw.setColumnDigit(8, 4);
					pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
				}
				else
				{
					String[] headerkeys = { "label.lot", "label.expiredate", "label.item", "label.itemdesc", "label.receipt", 
											"label.qty","label.extendedprice", "label.additional.charges", "label.freightcharge", "label.workarea", 
											"label.catalog","label.mrline", "label.podaskline", "label.delivereddate", "label.mrquantity", "label.partno", "label.partdesc", "label.shipmentid", "label.confirmedby", "label.dateconfirmed", "label.comments" };
					/*This array defines the type of the excel column.
					0 means default depending on the data type. */
					int[] types = { 0, pw.TYPE_CALENDAR, 0, pw.TYPE_PARAGRAPH, 0, 
									0, 0, 0, 0, 0, 
									0, 0, pw.TYPE_CALENDAR, 0, 0, pw.TYPE_PARAGRAPH, 0, 0, 0, pw.TYPE_CALENDAR, pw.TYPE_PARAGRAPH };
					/*This array defines the default width of the column when the Excel file opens.
					0 means the width will be default depending on the data type.*/
					int[] widths = { 20, 0, 0, 0, 0,
									 0, 15, 15, 0, 20, 
									 0, 0, 0, 0, 20, 0, 0, 10, 15, 15, 0 };
					/*This array defines the horizontal alignment of the data in a cell.
					0 means excel defaults the horizontal alignemnt by the data type.*/
					int[] horizAligns = { 0, 0, 0, 0, 0, 
										  pw.ALIGN_CENTER, 0, 0, 0, 0, 
										  0, 0, 0, pw.ALIGN_CENTER, 0, 0, 0, 0, 0, 0, 0 };
	
					pw.setColumnDigit(5, 4);
					pw.setColumnDigit(6, 4);
					pw.setColumnDigit(7, 4);
					pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
				}

				String preLot = "";
				String indefinite = library.getString("label.indefinite");
				SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy");
				NumberFormat formatter = new DecimalFormat("#,##0.00");
				// now write data
				for (ReceiptDocsObjBean member : data) {
					pw.addRow();
					String curLot = member.getMfgLot() + member.getExpireDate();
					pw.addCell(member.getMfgLot());
					if (member.getExpireDate() != null && "3000".equals(simpleDateformat.format(member.getExpireDate())))
						pw.addCell(indefinite);
					else
						pw.addCell(member.getExpireDate());
					pw.addCell(member.getItemId());
					pw.addCell(member.getItemDesc());
					pw.addCell(member.getReceiptId());
					pw.addCell(member.getQuantityIssued());
					pw.addCell(formatter.format(member.getExtendedPrice()) + " " + member.getCurrencyId());
					pw.addCell(formatter.format(member.getAdditionalCharges()) + " " + member.getCurrencyId());
					pw.addCell(formatter.format(member.getFreightCharges()) + " " + member.getCurrencyId());
					pw.addCell(member.getApplicationDesc());
					pw.addCell(member.getCatalogDesc());
					pw.addCell(member.getMrLine());
					if(inputBean.isPoRequiredFacility())
					{
						if ("-".equals(member.getPoLine()))
							pw.addCell("");
						else
							pw.addCell(member.getPoLine());
					}
					pw.addCell(member.getDateDelivered());
					pw.addCell(member.getMrLineQty());
					pw.addCell(member.getFacPartNo());
					pw.addCell(member.getPartDescription());
					pw.addCell(member.getShipmentId());
					pw.addCell(member.getConfirmedBy());
					pw.addCell(member.getDeliveryConfirmationDate());
					pw.addCell(member.getDeliveryComments());
					preLot = member.getMfgLot() + member.getExpireDate();
				}

			}
			else {
				if(inputBean.isPoRequiredFacility())
				{
					String[] headerkeys = { "label.lot", "label.expiredate", "label.item", "label.itemdesc", "label.receipt", "label.qty", "label.workarea", "label.catalog", "label.mrline", "label.podaskline", "label.delivereddate",
							"label.mrquantity", "label.partno", "label.partdesc", "label.shipmentid", "label.confirmedby", "label.dateconfirmed", "label.comments" };
					/*This array defines the type of the excel column.
					0 means default depending on the data type. */
					int[] types = { 0, pw.TYPE_CALENDAR, 0, pw.TYPE_PARAGRAPH, 0, 0, 0, 0, 0, 0, pw.TYPE_CALENDAR, 0, 0, pw.TYPE_PARAGRAPH, 0, 0, pw.TYPE_CALENDAR, pw.TYPE_PARAGRAPH };
					/*This array defines the default width of the column when the Excel file opens.
					0 means the width will be default depending on the data type.*/
					int[] widths = { 20, 0, 0, 0, 0, 0, 20, 0, 0, 0, 0, 0, 20, 0, 10, 15, 15, 0 };
					/*This array defines the horizontal alignment of the data in a cell.
					0 means excel defaults the horizontal alignemnt by the data type.*/
					int[] horizAligns = { 0, 0, 0, 0, 0, pw.ALIGN_CENTER, 0, 0, 0, 0, 0, pw.ALIGN_CENTER, 0, 0, 0, 0, 0, 0 };
					pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
				}
				else
				{
					String[] headerkeys = { "label.lot", "label.expiredate", "label.item", "label.itemdesc", "label.receipt", 
							"label.qty", "label.workarea", "label.catalog", "label.mrline", 
							"label.delivereddate","label.mrquantity", "label.partno", "label.partdesc", "label.shipmentid", "label.confirmedby", 
							"label.dateconfirmed", "label.comments" };
					/*This array defines the type of the excel column.
					0 means default depending on the data type. */
					int[] types = { 0, pw.TYPE_CALENDAR, 0, pw.TYPE_PARAGRAPH, 0, 
									0, 0, 0, 0, 
									pw.TYPE_CALENDAR, 0, 0, pw.TYPE_PARAGRAPH, 0, 0, pw.TYPE_CALENDAR, pw.TYPE_PARAGRAPH };
					/*This array defines the default width of the column when the Excel file opens.
					0 means the width will be default depending on the data type.*/
					int[] widths = { 20, 0, 0, 0, 0, 
									  0, 20, 0, 0,
									  0, 0, 20, 0, 10, 15, 15, 0 };
					/*This array defines the horizontal alignment of the data in a cell.
					0 means excel defaults the horizontal alignemnt by the data type.*/
					int[] horizAligns = { 0, 0, 0, 0, 0, 
										pw.ALIGN_CENTER, 0, 0, 0,
										0, pw.ALIGN_CENTER, 0, 0, 0, 0, 0, 0 };
					pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
				}
	
				String preLot = "";
				String indefinite = library.getString("label.indefinite");
				SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy");

				// now write data
				for (ReceiptDocsObjBean member : data) {
					pw.addRow();
					String curLot = member.getMfgLot() + member.getExpireDate();
					pw.addCell(member.getMfgLot());
					if (member.getExpireDate() != null && "3000".equals(simpleDateformat.format(member.getExpireDate())))
						pw.addCell(indefinite);
					else
						pw.addCell(member.getExpireDate());
					pw.addCell(member.getItemId());
					pw.addCell(member.getItemDesc());
					pw.addCell(member.getReceiptId());
					pw.addCell(member.getQuantityIssued());
					pw.addCell(member.getApplicationDesc());
					pw.addCell(member.getCatalogDesc());
					pw.addCell(member.getMrLine());
					if(inputBean.isPoRequiredFacility())
					{
						if ("-".equals(member.getPoLine()))
							pw.addCell("");
						else
							pw.addCell(member.getPoLine());
					}
					pw.addCell(member.getDateDelivered());
					pw.addCell(member.getMrLineQty());
					pw.addCell(member.getFacPartNo());
					pw.addCell(member.getPartDescription());
					pw.addCell(member.getShipmentId());
					pw.addCell(member.getConfirmedBy());
					pw.addCell(member.getDeliveryConfirmationDate());
					pw.addCell(member.getDeliveryComments());
					preLot = member.getMfgLot() + member.getExpireDate();
				}
			}
		}

		return pw;
	}

	public Collection getPrRulesForFacility(ReceiptDocumentViewerInputBean inputBean, String companyId, BigDecimal personnelId) throws Exception {
        Vector results = new Vector();
		StringBuilder query = getPrRulesSql(companyId);
		if (!StringHandler.isBlankString(inputBean.getFacilityId())) {
			query.append(" and facility_id in (");
			getIdsString(query,inputBean.getFacilityId());
			query.append(")");
		}else {
			if (!StringHandler.isBlankString(inputBean.getFacilityIdSel()))
				query.append(" and facility_id = '").append(inputBean.getFacilityIdSel()).append("'");
			else
				query.append(" and facility_id in (").append(getFacilityIdsString(inputBean,personnelId)).append(")");
		}
        query.append(" order by facility_id,account_sys_label");

        factory.setBeanObject(new PrRulesViewBean());
        Collection<PrRulesViewBean> beans = factory.selectQuery(query.toString());
        String lastFacilityId = "";
        for (PrRulesViewBean bean : beans) {
            if (lastFacilityId.equals(bean.getFacilityId())) {
                FacilityPrRulesBean facilityPrRulesBean = (FacilityPrRulesBean) results.lastElement();
                Collection accountSysList = facilityPrRulesBean.getAccountSysList();
                accountSysList.add(bean);
            }else {
                FacilityPrRulesBean facilityPrRulesBean = new FacilityPrRulesBean();
                facilityPrRulesBean.setFacilityId(bean.getFacilityId());
                Collection accountSysList = new Vector();
                accountSysList.add(bean);
                facilityPrRulesBean.setAccountSysList(accountSysList);
                results.add(facilityPrRulesBean);
            }
            lastFacilityId = bean.getFacilityId();
        }
		return results;
	}

	public Collection getFacilityRequiredPo(Vector<MyWorkareaFacilityViewBean> beans, String companyId) throws BaseException
	{
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory fac = new GenericSqlFactory(dbManager, new PrRulesViewBean());
		StringBuilder query = getPrRulesSql(companyId);
		query.append(" and po_required = 'p'");
		if(beans != null && !beans.isEmpty())
		{
			String currentFacility = beans.firstElement().getFacilityId();
			query.append(" and facility_id in ('" + currentFacility + "'");
			for(MyWorkareaFacilityViewBean bean: beans)
			{
				if(!currentFacility.equalsIgnoreCase(bean.getFacilityId()))
				{
					currentFacility = bean.getFacilityId();
					query.append(",'" + currentFacility + "'");
				}
			}
		}
		query.append(")");
		return fac.selectQuery(query.toString());
	}

	private StringBuilder getPrRulesSql(String companyId) {
		return new StringBuilder("select distinct facility_id, po_required, account_sys_id,account_sys_label,account_sys_desc,fac_item_charge_type_override from pr_rules_view").append(" where status = 'A' and company_id = '" +companyId+ "'");
	}

} //end of class
