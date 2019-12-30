package com.tcmis.client.het.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.chain.commands.servlet.SetContentType;

import com.tcmis.client.common.beans.MaterialBean;
import com.tcmis.client.het.beans.HetContainerEntryInputBean;
import com.tcmis.client.het.beans.HetContainerEntryViewBean;
import com.tcmis.client.het.beans.HetContainerUsageBean;
import com.tcmis.client.het.beans.HetPermitBean;
import com.tcmis.client.het.beans.HetUsageBean;
import com.tcmis.client.het.factory.HetContainerUsageBeanFactory;
import com.tcmis.client.het.factory.HetPermitBeanFactory;
import com.tcmis.client.het.factory.HetUsageBeanFactory;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.BeanCopyException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.SqlHandler;

public class ContainerEntryProcess extends GenericProcess {
	private static final BigDecimal	ZERO	= new BigDecimal("0.0");
	Log								log		= LogFactory.getLog(this.getClass());

	public ContainerEntryProcess(String client, String locale) {
		super(client, locale);
	}

	public ContainerEntryProcess(String client) {
		super(client);
	}

	@SuppressWarnings("unchecked")
	public Collection<HetContainerEntryViewBean> getFlatSearchResults(HetContainerEntryInputBean inputBean, PersonnelBean user) throws BaseException {

		factory.setBeanObject(new HetContainerEntryViewBean());

		StringBuilder query = new StringBuilder("select * from table (pkg_hazardous_emissions_track.fx_cont_item_search('");
		query.append(user.getCompanyId()).append("','").append(inputBean.getFacilityId()).append("'");
		if (inputBean.hasWorkArea()) query.append(",").append(inputBean.getWorkArea()).append(",");
		else
			query.append(",'',");
		if (inputBean.hasSearchArgument()) {
			query.append("'").append(inputBean.getSearchField()).append("','").append(inputBean.getSearchMode()).append("','").append(inputBean.getSearchArgument()).append("'");
		}
		else {
			query.append("'','',''");
		}

		query.append(",'").append(inputBean.isNonHaasPurchased() ? "Y" : "N").append("'))");
		if (inputBean.isNonHaasPurchased()) {
			query.append(" order by cat_part_no ASC, cust_msds_no, component_msds_no");
		}
		else {
			query.append(" order by cat_part_no ASC, item_id, part_id, cust_msds_no");
		}

		return factory.selectQuery(query.toString());
	}

	@SuppressWarnings("unchecked")
	public Collection<HetContainerEntryViewBean> getSearchResults(HetContainerEntryInputBean inputBean, PersonnelBean user) throws BaseException {
		Vector<HetContainerEntryViewBean> flat = (Vector<HetContainerEntryViewBean>) getFlatSearchResults(inputBean, user);

		if (!flat.isEmpty()) {

			Vector orderedCollection = new Vector<HetContainerEntryViewBean>();
			Vector<HetContainerEntryViewBean> innerCollection = new Vector<HetContainerEntryViewBean>();
			HetContainerEntryViewBean curBean = flat.firstElement();

			for (HetContainerEntryViewBean b : flat) {
				if (inputBean.isNonHaasPurchased()) {
					if (curBean.isNonHasPurchasedKit() && curBean.getCustMsdsNo().equals(b.getCustMsdsNo())) {
						innerCollection.add(b);
					}
					else {
						orderedCollection.add(innerCollection);
						innerCollection = new Vector<HetContainerEntryViewBean>();
						innerCollection.add(b);
						curBean = b;

					}
				}
				else {
					if (curBean.getItemId().compareTo(b.getItemId()) == 0 && curBean.getPartId().compareTo(b.getPartId()) != 0) innerCollection.add(b);
					else {
						orderedCollection.add(innerCollection);
						innerCollection = new Vector<HetContainerEntryViewBean>();
						innerCollection.add(b);
						curBean = b;

					}
				}
			}
			if (!innerCollection.isEmpty()) orderedCollection.add(innerCollection);

			return orderedCollection;
		}
		else
			return flat;
	}

	public Vector update(Collection<HetContainerEntryViewBean> beans, PersonnelBean user, HetContainerEntryInputBean input) throws BaseException {
		Vector errorMessages = new Vector();
		Vector<HetContainerEntryViewBean> insertedNHP = new Vector<HetContainerEntryViewBean>();
		HetContainerUsageBeanFactory factory = new HetContainerUsageBeanFactory(dbManager);
		UsageLoggingProcess usageProcess = new UsageLoggingProcess(client, getLocaleObject());
		BigDecimal kitStart;
		int newKitId = -1;
		BigDecimal transactionId = null;

		for (int j = 0; j < beans.size();) {
			HetContainerEntryViewBean bean = ((Vector<HetContainerEntryViewBean>) beans).get(j);

			if (bean.getKitStart() != null) {
				Vector<HetContainerEntryViewBean> kits = new Vector<HetContainerEntryViewBean>();
				kits.add(bean);
				while (true) {
					if ((j + 1) < beans.size()) {
						kitStart = ((Vector<HetContainerEntryViewBean>) beans).get(++j).getKitStart();
						if (kitStart != null && kitStart.intValue() == bean.getKitStart().intValue()) kits.add(((Vector<HetContainerEntryViewBean>) beans).get(j));
						else
							break;
					}
					else {
						++j;
						break;
					}
				}
				if (input.isNonHaasPurchased()) {
					if (bean.getQuantity() != null) {
						int numContainers = bean.getQuantity().intValue();
						for (int k = 0; k < numContainers; k++) {

							transactionId = new BigDecimal(dbManager.getOracleSequence("HET_TRANSACTION_SEQ"));
							if (kits.size() > 1) {
								newKitId = dbManager.getOracleSequence("HET_MISC_SEQ");
							}

							for (int i = 0; i < kits.size(); i++) {
								bean = (kits).get(i);
								HetContainerUsageBean container = new HetContainerUsageBean();
								HetContainerEntryViewBean nhpPrintLabelBean = new HetContainerEntryViewBean();

								String[] containerId = bean.getContainerId().split("-");
								String cId = containerId[0] + "-" + dbManager.getOracleSequence("HET_NONHAAS_CONTID_SEQ");
								container.setContainerId(cId);
								container.setContainerType(bean.getContainerType());
								container.setAmountRemaining(bean.getAmountRemaining());
								container.setUnitOfMeasure(bean.getUnitOfMeasure());
								container.setItemId(bean.getItemId());
								container.setApplicationId(bean.getApplicationId());
								container.setMaterialId(bean.getMaterialId());
								container.setPartId(bean.getPartId());
								container.setSolvent(bean.isSolvent());
								container.setDiluent(bean.isDiluent());
								container.setExpirationDate(bean.getExpirationDate());
								container.setPackaging(bean.getContainerSize());
								container.setCatPartNo(bean.getCatPartNo());
								container.setMaterialDesc(bean.getMaterialDesc());
								container.setMsdsNumber(bean.getComponentMsdsNo());
								container.setPartSize(bean.getAmountRemaining());
								container.setSizeUnit(bean.getUnitOfMeasure());
								container.setCustomerMsdsDb(bean.getCustomerMsdsDb());

								if (!bean.isNonHaasPurchased()) {
									container.setReceiptId(new BigDecimal(bean.getReceiptId()));
								}

								HetUsageBean usage = null;
								// One Time Record
								if (!bean.isUsageLoggingRequired()) {
									usage = createUsage(input, bean, container, user);
									usage.setTransactionId(transactionId);
									usage.setMsdsNo(bean.getComponentMsdsNo());
									container.setAmountRemaining(ZERO);
								}

								BeanHandler.copyAttributes(bean, nhpPrintLabelBean);
								BeanHandler.copyAttributes(container, nhpPrintLabelBean);

								if (kits.size() > 1) container.setKitId(new BigDecimal(newKitId));

								try {
									factory.insert(container);
									// One Time Record
									if (!bean.isUsageLoggingRequired()) {
										usageProcess.insertUsage(input.getFacilityId(), usage, user);
									}
									insertedNHP.add(nhpPrintLabelBean);
								}
								catch (Exception e) {
									String errorMsg = "Error updating: " + bean.getContainerId() + " " + e.getMessage() + "\n";
									errorMessages.add(errorMsg);
								}
							}
						}
					}
				}
				else if (bean.hasContainerId()) {
					transactionId = new BigDecimal(dbManager.getOracleSequence("HET_TRANSACTION_SEQ"));
					if (kits.size() > 1) {
						newKitId = dbManager.getOracleSequence("HET_MISC_SEQ");
					}
					for (int i = 0; i < kits.size(); i++) {
						bean = (kits).get(i);
						HetContainerUsageBean containerUsage = getDefaultContainer(bean.getMaterialId().toPlainString(), bean.getItemId().toPlainString(), bean.getReceiptId());
						containerUsage.setContainerId(bean.getContainerId());
						containerUsage.setContainerType(bean.getContainerType());
						containerUsage.setItemId(bean.getItemId());
						containerUsage.setApplicationId(bean.getApplicationId());
						containerUsage.setMaterialId(bean.getMaterialId());
						containerUsage.setPartId(bean.getPartId());
						containerUsage.setCustomerMsdsDb(bean.getCustomerMsdsDb());
						containerUsage.setCatPartNo(bean.getCatPartNo());
						containerUsage.setMsdsNumber(bean.getComponentMsdsNo());
						containerUsage.setSolvent(bean.isSolvent());
						containerUsage.setDiluent(bean.isDiluent());

						if (kits.size() > 1) {
							containerUsage.setKitId(new BigDecimal(newKitId));
						}

						try {
							// One Time Record
							if (!bean.isUsageLoggingRequired()) {
								HetUsageBean usage = createUsage(input, bean, containerUsage, user);
								usage.setTransactionId(transactionId);
								containerUsage.setAmountRemaining(ZERO);
								factory.insert(containerUsage);
								usageProcess.insertUsage(input.getFacilityId(), usage, user);
							}
							else {
								factory.insert(containerUsage);
							}
						}
						catch (Exception e) {
							String errorMsg = "Error updating: Container " + bean.getContainerId() + e.getMessage() + "\n";
							errorMessages.add(errorMsg);
						}
					}
				}
			}   
			else
				++j;
		}
		return ((errorMessages.size() > 0 ? errorMessages : fillOutNHPBeansForLabel(insertedNHP)));
	}

	private Vector<HetContainerEntryViewBean> fillOutNHPBeansForLabel(Vector<HetContainerEntryViewBean> containers) throws BaseException {
		factory.setBean(new HetContainerEntryViewBean());
		for (HetContainerEntryViewBean container : containers) {
			Collection<HetContainerEntryViewBean> filledInContainers = factory.selectQuery("select mat.trade_name, MFG.MFG_DESC manufacturer from material mat, manufacturer mfg where mfg.mfg_id(+) = mat.mfg_id AND mat.material_id = "
					+ container.getMaterialId());
			if (!filledInContainers.isEmpty()) {
				HetContainerEntryViewBean filledIn = filledInContainers.iterator().next();
				container.setTradeName(filledIn.getTradeName());
				container.setManufacturer(filledIn.getManufacturer());
			}
		}

		return containers;
	}

	private HetUsageBean createUsage(HetContainerEntryInputBean input, HetContainerEntryViewBean bean, HetContainerUsageBean containerUsage, PersonnelBean user) throws BeanCopyException, BaseException {
		HetUsageBean usage = new HetUsageBean();
		BeanHandler.copyAttributes(bean, usage);
		BeanHandler.copyAttributes(containerUsage, usage);

		usage.setMsdsNo(bean.getComponentMsdsNo());
		usage.setAmountRemaining(ZERO);
		usage.setQuantity(containerUsage.getAmountRemaining());
		usage.setEmployee(user.getLogonId());
		usage.setUserId(user.getPersonnelIdBigDecimal());
		usage.setUsageDate(new Date());
		usage.setCompanyId(user.getCompanyId());
		usage.setFacilityId(input.getFacilityId());
		usage.setReportingEntityId(input.getWorkAreaGroup());
		usage.setApplicationId(input.getWorkArea());
		usage.setPartType(bean.getDefaultPartType());
		if ("NA".equals(bean.getDefaultPermitId())) {
			usage.setPermit("Unauthorized");
		}
		else if ("NONE".equals(bean.getDefaultPermitId())) {
			usage.setPermit("NONE");
		}
		else {
			HetPermitBean permit = new HetPermitBeanFactory(dbManager).getPermit(bean.getDefaultPermitId());
			usage.setPermit(permit.getPermitName());
			usage.setControlDevice(permit.getControlDevice());
		}
		usage.setApplicationMethod(bean.getDefaultApplicationMethodCod());
		usage.setSubstrate(bean.getDefaultSubstrateCode());
		return usage;
	}

	private HetContainerUsageBean getDefaultContainer(String materialId, String itemId, String receiptId) throws BaseException {
		factory.setBeanObject(new HetContainerUsageBean());

		StringBuilder query = new StringBuilder("select nvl(p.net_wt_unit, p.size_unit) unit_of_measure,");
		query.append(" decode(p.net_wt_unit, null, p.part_size, p.net_wt) amount_remaining,");
		query.append(" r.mfg_lot,");
		query.append(" r.expire_date expiration_date");
		query.append(" from part p, receipt r ");
		query.append("where p.material_id = ").append(materialId);
		query.append(" and p.item_id = ").append(itemId);
		query.append(" and r.item_id = p.item_id ");
		query.append(" and r.receipt_id = ").append(receiptId);

		return (HetContainerUsageBean) factory.selectQuery(query.toString()).iterator().next();
	}

	public String validateContainerInventoried(String receiptId, String containerId, String itemId) throws BaseException {

		String query = "select count(*) from receipt where receipt_id = " + receiptId + " and item_id = " + itemId;
		if ("0".equals(factory.selectSingleValue(query))) {
			return "receipt";
		}
		query = "select count(*) from het_container_usage where container_id = '" + containerId + "'";
		if (!"0".equals(factory.selectSingleValue(query))) {
			return "container";
		}
		return null;

	}

	public boolean isContainerOTR(HetUsageBean input) throws BaseException {
		StringBuilder query = new StringBuilder("select FX_MATL_VOC_20_GPL_16_FL_OZ ('");
		query.append(input.getMaterialId()).append("', ");
		query.append(input.getAmountRemaining()).append(", '");
		query.append(input.getUnitOfMeasure()).append("') from DUAL");
		return !"Y".equals(factory.selectSingleValue(query.toString()));
	}

	public boolean isWorkAreaUsageLoggingOverride(HetContainerEntryInputBean input) throws BaseException {
		StringBuilder query = new StringBuilder("select override_usage_logging from fac_loc_app where application_id = ");
		query.append(input.getWorkArea());
		return "Y".equals(factory.selectSingleValue(query.toString()));
	}

}
