package com.tcmis.client.het.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.common.beans.UserFacWagWaViewBean;
import com.tcmis.client.het.beans.FxIncomingShipDetailDataBean;
import com.tcmis.client.het.beans.HetContainerInventoryViewBean;
import com.tcmis.client.het.beans.HetContainerUsageBean;
import com.tcmis.client.het.beans.HetPermitBean;
import com.tcmis.client.het.beans.HetUsageBean;
import com.tcmis.client.het.beans.ShipmentReceivingInputBean;
import com.tcmis.client.het.factory.HetContainerUsageBeanFactory;
import com.tcmis.client.het.factory.HetPermitBeanFactory;
import com.tcmis.client.het.factory.HetUsageBeanFactory;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.BeanCopyException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;

public class ShipmentReceivingProcess extends GenericProcess {

	private static final BigDecimal ZERO = new BigDecimal("0.0");
	Log log = LogFactory.getLog(this.getClass());

	public ShipmentReceivingProcess(String client, Locale locale) {
		super(client, locale);
	}

	public ShipmentReceivingProcess(String client) {
		super(client);
	}

	public Collection getFlatResults(String companyId, ShipmentReceivingInputBean inputBean) throws BaseException {
		factory.setBean(new FxIncomingShipDetailDataBean());

		String shipmentId = "";
		String withinDays = "";

		if ("shipmentId".equals(inputBean.getSearchField()))
			shipmentId = inputBean.getSearchArgument();
		else
			withinDays = inputBean.getSearchArgument();

		return factory.selectQuery("select * from table (PKG_HAZARDOUS_EMISSIONS_TRACK.fx_incoming_ship_detail_data(" + getSqlString(companyId) + "," + getSqlString(inputBean.getFacilityId()) + "," + getSqlString(inputBean.getWorkArea()) + ","
				+ getSqlString(inputBean.getWorkAreaGroup()) + "," + getSqlString(shipmentId) + "," + getSqlString(withinDays) + ")) "
				+ " order by shipment_id, application_id,  item_id, pr_number, part_id, customer_Msds_Or_Mixture_No, customer_msds_no");
	}

	@SuppressWarnings("unchecked")
	public Collection<FxIncomingShipDetailDataBean> getNestedResults(String companyId, Locale locale, ShipmentReceivingInputBean inputBean) throws BaseException {

		Collection<FxIncomingShipDetailDataBean> flatResults = getFlatResults(companyId,inputBean);
		try {
			setDropdownsforGrid(companyId, locale, flatResults);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		//Vector<FxIncomingShipDetailDataBean> flat = (Vector<FxIncomingShipDetailDataBean>) fullFlat;
		if (!flatResults.isEmpty()) {
			Vector itemCollection = new Vector<HetContainerInventoryViewBean>();
			Vector<FxIncomingShipDetailDataBean> kitIdCollection = new Vector<FxIncomingShipDetailDataBean>();
			FxIncomingShipDetailDataBean prevBean = flatResults.iterator().next();
			int rowCount = 0;
			int notYetReceived = 0;
			int kitCount = 1;

			for (FxIncomingShipDetailDataBean currentBean : flatResults) {
				if (prevBean.getRowKey().equals(currentBean.getRowKey()) && !prevBean.getPartId().equals(currentBean.getPartId()) ) {
					if(currentBean.isInseparableKit() && prevBean.isInseparableKit() && kitIdCollection.size() > 0)
					{
						prevBean.setMaterialDesc(prevBean.getMaterialDesc() + ", " + currentBean.getMaterialDesc());
						//prevBean.setCustomerMsdsNo(prevBean.getCustomerMsdsNo() + ", " + currentBean.getCustomerMsdsNo());
						prevBean.setContainerSize(prevBean.getContainerSize() + ", " + currentBean.getContainerSize());
					}
					else
					{
						currentBean.setRowCount(rowCount);
						kitIdCollection.add(currentBean);
					}
				}
				else {
					for (int i = 1; i <= notYetReceived; i++) {
						rowCount++;
						itemCollection.add(cloneKit(kitIdCollection, kitCount++));
					}
					kitIdCollection = new Vector<FxIncomingShipDetailDataBean>();
					currentBean.setRowCount(rowCount);
					kitIdCollection.add(currentBean);
				}
				prevBean = currentBean;
				if(currentBean.getComponentsPerItem() != null && currentBean.getComponentsPerItem().intValue() > 0)
					notYetReceived = currentBean.getNotInContainer().intValue() * currentBean.getComponentsPerItem().intValue();
				else
					notYetReceived = currentBean.getNotInContainer().intValue();
			}
			if (!kitIdCollection.isEmpty()) {
				for (int i = 1; i <= notYetReceived; i++) {
					rowCount++;
					itemCollection.add(cloneKit(kitIdCollection, kitCount++));
				}
			}

			return itemCollection;
		}
		else
			return flatResults;
	}

	private Vector<FxIncomingShipDetailDataBean> cloneKit(Vector<FxIncomingShipDetailDataBean> original, int kitCount) throws BeanCopyException {
		Vector<FxIncomingShipDetailDataBean> clone =  new Vector<FxIncomingShipDetailDataBean>(original.size());
		for (FxIncomingShipDetailDataBean master : original) {
			FxIncomingShipDetailDataBean copy = new FxIncomingShipDetailDataBean();
			BeanHandler.copyAttributes(master, copy);
			copy.setKitCount(kitCount);
			clone.add(copy);
		}
		return clone;
	}

	public void setDropdownsforGrid(String companyId, Locale locale, Collection<FxIncomingShipDetailDataBean> results) throws BaseException, Exception {

		BigDecimal currentApplicationId = null;
		Collection currentPermits = null;
		Collection currentSubstrates = null;
		Collection currentApplicationMethods = null;
		for (FxIncomingShipDetailDataBean bean : results) {

			if (currentApplicationId == null || bean.getApplicationId().compareTo(currentApplicationId) != 0) {
				currentPermits = new PermitManagementProcess(getClient()).getActivePermits(companyId, bean.getFacilityId(), bean.getApplicationId());
				currentSubstrates = new UsageLoggingProcess(getClient(), locale).getSubstrates(companyId, bean.getFacilityId(), bean.getApplicationId());
				currentApplicationMethods = new UsageLoggingProcess(getClient(), locale).getApplicationMethods(companyId, bean.getFacilityId(), bean.getApplicationId());

				currentApplicationId = bean.getApplicationId();
			}

			bean.setPermits(currentPermits);
			bean.setSubstrates(currentSubstrates);
			bean.setApplicationMethods(currentApplicationMethods);
		}
	}

	public String saveBeans(Collection<FxIncomingShipDetailDataBean> beans, ShipmentReceivingInputBean input, PersonnelBean user) throws BaseException {
		HetContainerUsageBeanFactory HCUfactory = new HetContainerUsageBeanFactory(dbManager);
		HetUsageBeanFactory usageFactory = new HetUsageBeanFactory(dbManager);
		String errMsg = null;
		int kitCount = -1;
		BigDecimal currentTransactionId = null;
		BigDecimal currentKitId = null;
		for (FxIncomingShipDetailDataBean inputRow : beans) {
			if (inputRow.hasContainerId()) {
				try {
					inputRow.setContainerId(inputRow.getReceiptId() + "-" + inputRow.getContainerId());
					HetContainerUsageBean containerUsage = setDefaultContainerValues(createContainer(inputRow));

					// One Time Record (i.e. does NOT require daily logging)
					if (!inputRow.isUsageLoggingReq()) {
						if (currentTransactionId == null || !inputRow.isKit() || inputRow.getKitCount() != kitCount) {
							currentTransactionId = new BigDecimal(dbManager.getOracleSequence("HET_TRANSACTION_SEQ"));
						}
						inputRow.setTransactionId(currentTransactionId);

						HetUsageBean usage = createUsage(input, inputRow, containerUsage, user);
						usageFactory.insert(usage);
						containerUsage.setAmountRemaining(ZERO);
					}

					if (inputRow.isKit()) {
						if (currentKitId == null || inputRow.getKitCount() != kitCount) {
							currentKitId = new BigDecimal(dbManager.getOracleSequence("HET_MISC_SEQ"));
						}
						containerUsage.setKitId(currentKitId);
					}
					HCUfactory.insert(containerUsage);

					kitCount = inputRow.getKitCount();
				}
				catch (Exception e) {
					log.error("error inserting container", e);
					errMsg = "Error saving container:" + inputRow.getContainerId() + ",";
				}
			}
		}
		return errMsg;
	}

	private HetContainerUsageBean createContainer(FxIncomingShipDetailDataBean container) throws BeanCopyException {
		HetContainerUsageBean containerUsage = new HetContainerUsageBean();
		BeanHandler.copyAttributes(container, containerUsage);
		containerUsage.setExpirationDate(container.getExpireDate());
		containerUsage.setCatPartNo(container.getFacpartNo());
		containerUsage.setMsdsNumber(container.getCustomerMsdsOrMixtureNo());
		return containerUsage;
	}

	private HetUsageBean createUsage(ShipmentReceivingInputBean input, FxIncomingShipDetailDataBean inputRow, HetContainerUsageBean containerUsage, PersonnelBean user) throws BeanCopyException, BaseException {
		HetUsageBean usage = new HetUsageBean();
		BeanHandler.copyAttributes(inputRow, usage);
		BeanHandler.copyAttributes(containerUsage, usage);

		usage.setAmountRemaining(ZERO);
		usage.setCatPartNo(inputRow.getFacpartNo());
		usage.setQuantity(containerUsage.getAmountRemaining());
		usage.setEmployee(user.getLogonId());
		usage.setUserId(user.getPersonnelIdBigDecimal());
		usage.setUsageDate(new Date());
		usage.setMsdsNo(inputRow.getCustomerMsdsNo());
		usage.setCompanyId(user.getCompanyId());
		usage.setFacilityId(input.getFacilityId());
		//usage.setReportingEntityId(input.getWorkAreaGroup());
		//usage.setApplicationId(inputRow.getApplicationId());
		usage.setPartType(inputRow.getDefaultPartType());
		if ("NONE".equals(inputRow.getDefaultPermitId())) {
			usage.setPermit("NONE");
		}
		else {
			HetPermitBean permit = new HetPermitBeanFactory(dbManager).getPermit(inputRow.getDefaultPermitId());
			usage.setPermit(permit.getPermitName());
			usage.setControlDevice(permit.getControlDevice());
		}
		usage.setApplicationMethod(inputRow.getDefaultApplicationMethodCod());
		usage.setSubstrateCode(inputRow.getDefaultSubstrateCode());
		usage.setSubstrate(inputRow.getDefaultSubstrateCode());
		usage.setTransactionId(inputRow.getTransactionId());
		return usage;
	}

	private HetContainerUsageBean setDefaultContainerValues(HetContainerUsageBean container) throws BaseException {
		factory.setBeanObject(new HetContainerUsageBean());
		StringBuilder query = new StringBuilder("select nvl(p.net_wt_unit, p.size_unit) unit_of_measure,  decode(p.net_wt_unit, null, p.part_size, p.net_wt) amount_remaining from part p ");
		query.append("where material_id = ").append(container.getMaterialId());
		query.append(" and item_id = ").append(container.getItemId());
		HetContainerUsageBean defaultBean = (HetContainerUsageBean) factory.selectQuery(query.toString()).iterator().next();
		container.setUnitOfMeasure(defaultBean.getUnitOfMeasure());
		container.setAmountRemaining(defaultBean.getAmountRemaining());
		return container;
	}

	public ExcelHandler getExcelReport(Collection<FxIncomingShipDetailDataBean> data, Locale locale) throws NoDataException, BaseException, Exception {

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
		//write column headers
		pw.addRow();
		/*Pass the header keys for the Excel.*/
		String[] headerkeys = { "label.shipmentid", "label.workarea", "label.partnumber", "label.item", "label.containeridprefix", "label.containeridsuffix", "label.msds", "label.materialdesc", "label.containersize", "label.solvent", "label.diluent" };
		/*This array defines the type of the excel column.
		0 means default depending on the data type.
		pw.TYPE_PARAGRAPH defaults to 40 characters.
		pw.TYPE_CALENDAR set up the date with no time.
		pw.TYPE_DATE set up the date with time.*/
		int[] types = { 0, 0, 0, 0, 0, 0, pw.TYPE_PARAGRAPH, pw.TYPE_PARAGRAPH, 0, 0, 0 };

		/*This array defines the default width of the column when the Excel file opens.
		0 means the width will be default depending on the data type.*/
		int[] widths = { 10, 20, 15, 0, 15, 15, 0, 0, 15, 0, 0 };
		/*This array defines the horizontal alignment of the data in a cell.
		0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = { 0, 0, 0, 0, 0, 0, 0, 0, 0, pw.ALIGN_CENTER , pw.ALIGN_CENTER };

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		for (FxIncomingShipDetailDataBean member : data) {
			pw.addRow();
			pw.addCell(member.getShipmentId());
			pw.addCell(member.getApplicationDesc());
			pw.addCell(member.getFacpartNo());
			pw.addCell(member.getItemId());
			pw.addCell(member.getReceiptId());
			pw.addCell("");
			pw.addCell(member.getCustomerMsdsNo());
			pw.addCell(member.getMaterialDesc());
			pw.addCell(member.getContainerSize()); // Container size
			pw.addCell(member.isSolvent() ? "Y" : "N");
			pw.addCell(member.isDiluent() ? "Y" : "N");
		}
		return pw;
	}
	
	public Collection getUserFacilityWorkAreaGroupWorkAreaCollection(int personnelId) throws BaseException  {
		factory.setBeanObject(new UserFacWagWaViewBean());
		StringBuilder query = new StringBuilder("select distinct ufwwv.* from user_fac_wag_wa_view ufwwv, facility f where ufwwv.personnel_id = ").append(personnelId);
		query.append(" and ufwwv.reporting_entity_status = 'A' and ufwwv.status = 'A' and f.facility_id = ufwwv.facility_id and UPPER(f.active) = 'Y'");
		query.append(" order by ufwwv.facility_name, ufwwv.reporting_entity_desc, ufwwv.application_desc");
		return createRelationalObject(factory.selectQuery(query.toString()));
	}
	
	public Collection createRelationalObject(Collection userFacilityWorkAreaGroupWorkAreaColl) {
		Vector result = new Vector();
		Iterator iter = userFacilityWorkAreaGroupWorkAreaColl.iterator();
		String lastFacilityId = "";
		String lastWorkAreaGroup = "";
		while(iter.hasNext()) {
			UserFacWagWaViewBean bean = (UserFacWagWaViewBean)iter.next();
			String currentFacilityId = bean.getFacilityId();
			String currentWorkAreaGroup = bean.getReportingEntityId();
			if (currentFacilityId.equals(lastFacilityId)) {
				UserFacWagWaViewBean facilityBean = (UserFacWagWaViewBean)result.lastElement();
				Collection facilityWorkAreaColl = facilityBean.getFacilityWorkAreaColl();
				if (currentWorkAreaGroup.equals(lastWorkAreaGroup)) {
					Vector workAreaGroupColl = (Vector)facilityBean.getWorkAreaGroupColl();
					UserFacWagWaViewBean workAreaGroupBean = (UserFacWagWaViewBean)workAreaGroupColl.lastElement();
					Collection workAreaColl = workAreaGroupBean.getWorkAreaColl();
					UserFacWagWaViewBean workAreaBean = new UserFacWagWaViewBean();
					copyWorkAreaData(bean,workAreaBean);
					workAreaColl.add(workAreaBean);
					facilityWorkAreaColl.add(workAreaBean);
				}else {
					Collection workAreaGroupColl = facilityBean.getWorkAreaGroupColl();
					Collection workAreaColl = new Vector();
					UserFacWagWaViewBean workAreaGroupBean = new UserFacWagWaViewBean();
					UserFacWagWaViewBean workAreaBean = new UserFacWagWaViewBean();
					copyWorkAreaData(bean,workAreaBean);
					workAreaColl.add(workAreaBean);
					copyWorkAreaGroupData(bean,workAreaGroupBean);
					workAreaGroupBean.setWorkAreaColl(workAreaColl);
					workAreaGroupColl.add(workAreaGroupBean);
					facilityWorkAreaColl.add(workAreaBean);
				}
			}else {
				Collection workAreaGroupColl = new Vector();
				Collection workAreaColl = new Vector();
				UserFacWagWaViewBean workAreaGroupBean = new UserFacWagWaViewBean();
				UserFacWagWaViewBean workAreaBean = new UserFacWagWaViewBean();
				copyWorkAreaData(bean,workAreaBean);
				workAreaColl.add(workAreaBean);
				copyWorkAreaGroupData(bean,workAreaGroupBean);
				workAreaGroupBean.setWorkAreaColl(workAreaColl);
				workAreaGroupColl.add(workAreaGroupBean);
				bean.setWorkAreaGroupColl(workAreaGroupColl);
				//set workArea for facility here
				Collection facilityWorkAreaColl = new Vector();
				facilityWorkAreaColl.add(workAreaBean);
				bean.setFacilityWorkAreaColl(facilityWorkAreaColl);
				result.addElement(bean);
			}
			lastFacilityId = currentFacilityId;
			lastWorkAreaGroup = currentWorkAreaGroup;
		}
		return result;
	}

	private void copyWorkAreaData(UserFacWagWaViewBean fromBean, UserFacWagWaViewBean toBean) {
		toBean.setApplication(fromBean.getApplication());
		toBean.setApplicationDesc(fromBean.getApplicationDesc());
		toBean.setStatus(fromBean.getStatus());
		toBean.setManualMrCreation(fromBean.getManualMrCreation());
		toBean.setAllowStocking(fromBean.getAllowStocking());
		toBean.setApplicationId(fromBean.getApplicationId());
	}

	private void copyWorkAreaGroupData(UserFacWagWaViewBean fromBean, UserFacWagWaViewBean toBean) {
		toBean.setReportingEntityId(fromBean.getReportingEntityId());
		toBean.setReportingEntityDesc(fromBean.getReportingEntityDesc());
		toBean.setReportingEntityStatus(fromBean.getReportingEntityStatus());
	}
}
