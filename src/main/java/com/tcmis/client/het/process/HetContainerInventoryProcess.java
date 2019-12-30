package com.tcmis.client.het.process;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.common.beans.SizeUnitViewBean;
import com.tcmis.client.het.beans.FxAllAppsInBldgBean;
import com.tcmis.client.het.beans.HetContainerInventoryInputBean;
import com.tcmis.client.het.beans.HetContainerInventoryViewBean;
import com.tcmis.client.het.beans.HetContainerUsageBean;
import com.tcmis.client.het.beans.HetUsageBean;
import com.tcmis.client.het.beans.VvHetDeletionCodeBean;
import com.tcmis.client.het.factory.HetCartBeanFactory;
import com.tcmis.client.het.factory.HetContainerUsageBeanFactory;
import com.tcmis.client.het.factory.HetUsageBeanFactory;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;

public class HetContainerInventoryProcess extends GenericProcess {

	private static final BigDecimal	ZERO	= new BigDecimal("0");
	Log								log		= LogFactory.getLog(this.getClass());

	public HetContainerInventoryProcess(String client, Locale locale) {
		super(client, locale);
	}

	public HetContainerInventoryProcess(String client) {
		super(client);
	}

	@SuppressWarnings("unchecked")
	public Collection<VvHetDeletionCodeBean> getDeletionCodes(String companyId) throws BaseException {
		factory.setBeanObject(new VvHetDeletionCodeBean());
		SearchCriteria criteria = new SearchCriteria("companyId", SearchCriterion.EQUALS, companyId);
		SortCriteria sort = new SortCriteria("deletionDesc");
		return factory.select(criteria, sort, "VV_HET_DELETION_CODE");
	}

	public Collection<SizeUnitViewBean> getUOMs() throws BaseException {
		factory.setBeanObject(new SizeUnitViewBean());
		StringBuffer query = new StringBuffer("select x.* from vv_size_unit x, size_unit_conversion z");
		query.append(" where x.size_unit = z.from_unit");
		query.append("   and z.to_unit = 'fluid oz'");
		query.append("   and exists ");
		query.append("         ( select size_unit from size_unit_conversion y");
		query.append("           where x.size_unit = y.from_unit");
		query.append("             and Y.TO_UNIT = 'lb' )");
		
		return factory.selectQuery(query.toString());
	}

	@SuppressWarnings("unchecked")
	public Collection<HetContainerInventoryViewBean> getSearchFlatResult(HetContainerInventoryInputBean inputBean, PersonnelBean user) throws BaseException {

		factory.setBeanObject(new HetContainerInventoryViewBean());
		SearchCriteria criteria = new SearchCriteria();
		SortCriteria sort = new SortCriteria();

		criteria.addCriterion("companyId", SearchCriterion.EQUALS, user.getCompanyId());
		criteria.addCriterion("facilityId", SearchCriterion.EQUALS, inputBean.getFacilityId());

		if (inputBean.hasWorkAreaGroup()) {
			criteria.addCriterion("reportingEntityId", SearchCriterion.EQUALS, inputBean.getWorkAreaGroup());
			if (inputBean.hasWorkArea()) {
				criteria.addCriterion("applicationId", SearchCriterion.EQUALS, inputBean.getWorkArea().toPlainString());
			}
		}

		if (inputBean.hasSearchArgument()) {
			criteria.addCriterionWithMode(inputBean.getSearchField(), inputBean.getSearchMode(), inputBean.getSearchArgument());
		}

		sort.addCriterion("application");
		sort.addCriterion("catPartNo");
		sort.addCriterion("itemId");
		sort.addCriterion("kitId");
		sort.addCriterion("containerId");

		return factory.select(criteria, sort, "HET_CONTAINER_INVENTORY");
	}

	@SuppressWarnings("unchecked")
	public Collection<HetContainerInventoryViewBean> getSearchResult(Collection<HetContainerInventoryViewBean> flat) throws BaseException {

		if (!flat.isEmpty()) {
			Vector itemCollection = new Vector<HetContainerInventoryViewBean>();
			Vector<HetContainerInventoryViewBean> kitIdCollection = new Vector<HetContainerInventoryViewBean>();
			HetContainerInventoryViewBean curBean = flat.iterator().next();
			for (HetContainerInventoryViewBean b : flat) {

				if (curBean.getItemId() != null && b.getItemId() != null && curBean.getItemId().compareTo(b.getItemId()) == 0) {
					if (b.getKitId() == null) {
						if (kitIdCollection.isEmpty()) kitIdCollection.add(b);
						else {
							itemCollection.add(kitIdCollection);
							kitIdCollection = new Vector<HetContainerInventoryViewBean>();
							kitIdCollection.add(b);
							curBean = b;
						}
					}
					else if (b.getKitId() != null && curBean.getKitId() != null && b.getKitId().compareTo(curBean.getKitId()) == 0) {
						kitIdCollection.add(b);
					}
					else {
						itemCollection.add(kitIdCollection);
						kitIdCollection = new Vector<HetContainerInventoryViewBean>();
						kitIdCollection.add(b);
						curBean = b;
					}
				}
				else {
					itemCollection.add(kitIdCollection);
					kitIdCollection = new Vector<HetContainerInventoryViewBean>();
					kitIdCollection.add(b);
					curBean = b;
				}

			}
			if (!kitIdCollection.isEmpty()) itemCollection.add(kitIdCollection);

			return itemCollection;
		}
		else
			return flat;
	}

	public String transfer(Collection<HetContainerInventoryViewBean> containers) throws BaseException {
		String errMsg = null;
		HetCartBeanFactory cartFactory = new HetCartBeanFactory(dbManager);
		for (HetContainerInventoryViewBean containerView : containers) {
			try {
				if (containerView.isOnCart() && cartFactory.isCartCheckedOut(containerView.getCartName())) {
					errMsg += "Error Transfering Container " + containerView.getContainerId() + ", Cart has been checked out.\n";
					continue;
				}
				StringBuilder query = new StringBuilder("update HET_CONTAINER_USAGE set ");
				query.append("application_id = ").append(containerView.getApplicationId());
				query.append(" where container_id = '").append(containerView.getContainerId()).append("'");
				factory.deleteInsertUpdate(query.toString());
				if (containerView.isOnCart()) {
					deleteContainerFromCart(containerView.getContainerId());
				}

			}
			catch (Exception e) {
				e.printStackTrace();
				errMsg = "error Transfering item_id:" + containerView.getItemId();
			}
		}
		return errMsg;
	}

	public String delete(Collection<HetContainerInventoryViewBean> containers, PersonnelBean user) throws BaseException {
		String errMsg = null;
		HetContainerUsageBeanFactory containerFactory = new HetContainerUsageBeanFactory(dbManager);
		HetUsageBeanFactory usageFactory = new HetUsageBeanFactory(dbManager);
		HetCartBeanFactory cartFactory = new HetCartBeanFactory(dbManager);
		for (HetContainerInventoryViewBean containerView : containers) {
			try {
				// Check for cart, error if cart checked out otherwise Remove
				// from cart
				if (containerView.isOnCart()) {
					if (cartFactory.isCartCheckedOut(containerView.getCartName())) {
						errMsg += "Error Deleting Container " + containerView.getContainerId() + ", Cart has been checked out.\n";
						continue;
					}
					else {
						deleteContainerFromCart(containerView.getContainerId());
					}
				}

				// Zero out container
				HetContainerUsageBean container = new HetContainerUsageBean(containerView);
				container.setAmountRemaining(ZERO);
				containerFactory.update(container);

				// Add zero usage && amount remaining usage logging entry with
				// deletion code && remark
				HetUsageBean usage = new HetUsageBean();
				usage.setStandalone(user.isStandalone());
				BeanHandler.copyAttributes(containerView, usage);
				usage.setAmountRemaining(ZERO);
				usage.setQuantity(ZERO);
				usage.setDiscarded(true);
				usage.setUsageDate(new Date());
				usage.setRemarks("Container Deleted (" + containerView.getDeletionCode() + " -  " + containerView.getDeletionDesc() + ")");
				usage.setMsdsNo(containerView.getMsdsNumber());
				usage.setUserId(user.getPersonnelIdBigDecimal());
				usage.setTransactionId(new BigDecimal(dbManager.getOracleSequence("HET_TRANSACTION_SEQ")));
				usageFactory.insert(usage);
			}
			catch (Exception e) {
				e.printStackTrace();
				errMsg += "Error Deleting Container:" + containerView.getContainerId() + "\n";
			}
		}
		return errMsg;
	}

	private void deleteContainerFromCart(String containerId) throws BaseException {
		StringBuilder query = new StringBuilder("delete from HET_CART_ITEM where container_id = '").append(containerId).append("'");
		factory.deleteInsertUpdate(query.toString());
	}

	public Collection<FxAllAppsInBldgBean> getWorkAreaColl(Collection<HetContainerInventoryViewBean> results) throws BaseException {
		HashMap<BigDecimal, FxAllAppsInBldgBean> workAreas = new HashMap<BigDecimal, FxAllAppsInBldgBean>();

		for (HetContainerInventoryViewBean container : results) {
			FxAllAppsInBldgBean workArea = new FxAllAppsInBldgBean(container);
			workAreas.put(workArea.getApplicationId(), workArea);
		}
		return workAreas.values();
	}

	public Collection<FxAllAppsInBldgBean> getWorkAreasForTransfer(HetContainerInventoryInputBean input, PersonnelBean user) throws BaseException {
		factory.setBean(new FxAllAppsInBldgBean());
		StringBuilder query = new StringBuilder("select * from table(PKG_HAZARDOUS_EMISSIONS_TRACK.fx_show_all_bldg_apps('").append(user.getCompanyId()).append("','");
		query.append(input.getFacilityId()).append("',").append(input.getMaterialId()).append("))");
		query.append(" order by application_desc");
		return factory.selectQuery(query.toString());
	}

	public ExcelHandler getExcelReport(Collection<HetContainerInventoryViewBean> data, Locale locale) throws NoDataException, BaseException, Exception {

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);
		String indefinite = library.getString("label.indefinite");
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy");

		pw.addTable();
		// write column headers
		pw.addRow();
		/* Pass the header keys for the Excel. */
		String[] headerkeys = { "label.workarea", "label.cart", "label.partnumber", "label.item", "label.containerid", "label.quantity", "label.unit", "label.expirationdate", "label.msds", "label.materialdesc", "label.containersize", "label.solvent",
				"label.diluent" };
		/*
		 * This array defines the type of the excel column. 0 means default
		 * depending on the data type. pw.TYPE_PARAGRAPH defaults to 40
		 * characters. pw.TYPE_CALENDAR set up the date with no time.
		 * pw.TYPE_DATE set up the date with time.
		 */
		int[] types = { 0, 0, pw.TYPE_NUMBER, pw.TYPE_NUMBER, 0, 0, pw.TYPE_NUMBER, pw.TYPE_CALENDAR, 0, pw.TYPE_PARAGRAPH, 0, 0, 0 };

		/*
		 * This array defines the default width of the column when the Excel
		 * file opens. 0 means the width will be default depending on the data
		 * type.
		 */
		int[] widths = { 20, 0, 0, 0, 20, 0, 0, 0, 0, 0, 20, 0, 0 };
		/*
		 * This array defines the horizontal alignment of the data in a cell. 0
		 * means excel defaults the horizontal alignemnt by the data type.
		 */
		int[] horizAligns = { 0, 0, 0, 0, 0, 0, 0, pw.ALIGN_CENTER, 0, 0, 0, pw.ALIGN_CENTER, pw.ALIGN_CENTER };

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		for (HetContainerInventoryViewBean member : data) {
			pw.addRow();
			pw.addCell(member.getApplicationDesc());
			pw.addCell(member.getCartName());
			pw.addCell(member.getCatPartNo());
			pw.addCell(member.getItemId());
			pw.addCell(member.getContainerId());
			pw.addCell(member.getAmountRemaining());
			pw.addCell(member.getUnitOfMeasure());
			if (member.getExpirationDate() != null) {
				if ("3000".equals(simpleDateformat.format(member.getExpirationDate()))) {
					pw.addCell(indefinite);
				}
				else {
					pw.addCell(member.getExpirationDate());
				}
			}
			else {
				pw.addCell("");
			}
			pw.addCell(member.getMsdsNumber());
			pw.addCell(member.getMaterialDesc());
			pw.addCell(member.getPackaging());
			pw.addCell(member.isSolvent() ? "Y" : "N");
			pw.addCell(member.isDiluent() ? "Y" : "N");
		}
		return pw;
	}

	public String update(Collection<HetContainerInventoryViewBean> containers, PersonnelBean user) throws BaseException {
		String errMsg = null;
		HetContainerUsageBeanFactory containerFactory = new HetContainerUsageBeanFactory(dbManager);
		HetCartBeanFactory cartFactory = new HetCartBeanFactory(dbManager);
		for (HetContainerInventoryViewBean containerView : containers) {
			try {
				// Check for cart, error if cart checked out
				if (containerView.isOnCart() && cartFactory.isCartCheckedOut(containerView.getCartName())) {
					errMsg += "Error Updating Container " + containerView.getContainerId() + ", Cart has been checked out.\n";
					continue;
				}

				// Update container
				containerFactory.update(new HetContainerUsageBean(containerView));

			}
			catch (Exception e) {
				e.printStackTrace();
				errMsg += "Error Updating Container:" + containerView.getContainerId() + "\n";
			}
		}
		return errMsg;
	}
}
