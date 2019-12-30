package com.tcmis.client.het.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.tcmis.client.het.beans.FxItemSearchBean;
import com.tcmis.client.het.beans.HetContainerUsageBean;
import com.tcmis.client.het.beans.HetUsageBean;
import com.tcmis.client.het.beans.HetUsageLoggingViewBean;
import com.tcmis.client.het.beans.UsageLoggingInputBean;
import com.tcmis.client.het.beans.VvHetApplicationMethodBean;
import com.tcmis.client.het.beans.VvHetSubstrateBean;
import com.tcmis.client.het.factory.HetCartBeanFactory;
import com.tcmis.client.het.factory.HetContainerUsageBeanFactory;
import com.tcmis.client.het.factory.HetUsageBeanFactory;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.factory.PersonnelBeanFactory;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SqlHandler;

public class UsageLoggingProcess extends GenericProcess {
	private static BigDecimal ZERO = new BigDecimal("0.0");

	public UsageLoggingProcess(String dbUser, Locale tcmISLocale) {
		super(dbUser, tcmISLocale);
	}

	private int daysElapsed(Date startDate, Date endDate) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(startDate);
		int startYYYYDDD = (cal.get(Calendar.YEAR) * 1000) + cal.get(Calendar.DAY_OF_YEAR);
		cal.setTime(endDate);
		int endYYYYDDD = (cal.get(Calendar.YEAR) * 1000) + cal.get(Calendar.DAY_OF_YEAR);
		return (endYYYYDDD - startYYYYDDD) + 1;
	}

	private BigDecimal getAmountInDifferentUnit(BigDecimal amount, BigDecimal itemId, BigDecimal materialId, String originalUnitOfMeasure, String newUnitOfMeasure) throws BaseException {
		try {
			return new BigDecimal(factory.getFunctionValue("FX_HET_MATL_SIZE_UNIT_CONVERT", materialId, originalUnitOfMeasure, newUnitOfMeasure, amount));
		}
		catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public Collection<VvHetApplicationMethodBean> getApplicationMethods(String companyId, String facilityId, BigDecimal workArea) throws BaseException {
		factory.setBeanObject(new VvHetApplicationMethodBean());
		StringBuilder query = new StringBuilder("select * from table (PKG_HAZARDOUS_EMISSIONS_TRACK.FX_METHOD_ID_AREA_SEARCH('");
		query.append(companyId).append("','");
		query.append(facilityId).append("',");
		query.append(workArea).append(")) order by METHOD");
		return factory.selectQuery(query.toString());
	}

	@SuppressWarnings("unchecked")
	public Collection<FxItemSearchBean> getSolvents(UsageLoggingInputBean input, PersonnelBean user) throws BaseException {
		factory.setBeanObject(new FxItemSearchBean());
		StringBuilder query = new StringBuilder("select t.*, hcu.container_type from table (PKG_HAZARDOUS_EMISSIONS_TRACK.fx_item_search('");
		query.append(user.getCompanyId()).append("','").append(input.getFacilityId()).append("',").append(input.getWorkArea());
		if (input.hasCartName()) {
			query.append(",'CART_NAME','is','").append(input.getCartName()).append("'))");
		}
		else {
			query.append(",'','',''))");
		}
		query.append(" t, het_container_usage hcu where t.DILUENT = 'Y' and hcu.container_id = t.container_id");
		query.append(" order by t.cat_part_no, t.item_id, t.kit_id, t.part_id");

		return factory.selectQuery(query.toString());
	}

	@SuppressWarnings("unchecked")
	public Collection<FxItemSearchBean> getAdHocMixture(UsageLoggingInputBean input, PersonnelBean user) throws BaseException {
		factory.setBeanObject(new FxItemSearchBean());

		StringBuilder query = new StringBuilder("select t.*, hcu.container_type from table (PKG_HAZARDOUS_EMISSIONS_TRACK.fx_item_search('");
		query.append(user.getCompanyId()).append("','").append(input.getFacilityId()).append("',").append(input.getWorkArea());
		if (input.hasCartName()) {
			query.append(",'CART_NAME','is','").append(input.getCartName()).append("'))");
		}
		else {
			query.append(",'','',''))");
		}
		query.append(" t, het_container_usage hcu where t.CONTAINER_ID NOT IN ('").append(input.getContainerIds().replaceAll(";", "','")).append("')");
		query.append(" and hcu.container_id = t.container_id order by t.cat_part_no, t.item_id, t.kit_id, t.part_id");

		return factory.selectQuery(query.toString());
	}

	@SuppressWarnings("unchecked")
	public Collection<FxItemSearchBean> getAddContainerMixture(UsageLoggingInputBean input, PersonnelBean user) throws BaseException {
		factory.setBeanObject(new FxItemSearchBean());

		StringBuilder query = new StringBuilder("select * from table (PKG_HAZARDOUS_EMISSIONS_TRACK.fx_usage_logging('");
		query.append(user.getCompanyId()).append("','").append(input.getFacilityId()).append("',").append(input.getWorkArea());
		if (input.hasCartName()) {
			query.append(",'CART_NAME','is','").append(input.getCartName()).append("'))");
		}
		else {
			query.append(",'','',''))");
		}
		query.append(" where mixture_id = '").append(input.getMixtureId()).append("'");
		query.append(" and  CUST_MSDS_NO NOT IN ('").append(input.getMsdsPreviouslyUsed().replaceAll(";", "','")).append("')");
		query.append(" order by cat_part_no, item_id, kit_id, cat_part_no");

		return factory.selectQuery(query.toString());
	}

	@SuppressWarnings("unchecked")
	public Collection<FxItemSearchBean> getAlternateContainers(UsageLoggingInputBean input, PersonnelBean user) throws BaseException {
		factory.setBeanObject(new FxItemSearchBean());
		StringBuilder query = new StringBuilder("select t.*, hcu.container_type from table (PKG_HAZARDOUS_EMISSIONS_TRACK.fx_item_search('");
		query.append(user.getCompanyId()).append("','").append(input.getFacilityId()).append("',").append(input.getWorkArea());
		if (input.hasCartName()) {
			query.append(",'CART_NAME','is','").append(input.getCartName()).append("'))");
		}
		else {
			query.append(",'','',''))");
		}
		query.append(" t, het_container_usage hcu " );
		query.append(" where hcu.container_id = t.container_id ");
		query.append(" and t.material_id = '").append(input.getMaterialId()).append("'");
				query.append(" order by t.cat_part_no, t.item_id, t.kit_id, t.part_id");
		return factory.selectQuery(query.toString());
	}

	public String getMixtureMSDSCount(UsageLoggingInputBean input) throws BaseException {
		return factory.selectSingleValue("select count(*) from het_MIXTURE_MSDS where mixture_id = " + input.getMixtureId());
	}

	@SuppressWarnings("unchecked")
	public Collection<HetUsageLoggingViewBean> getSearchData(UsageLoggingInputBean input, PersonnelBean user) throws BaseException {

		factory.setBeanObject(new HetUsageLoggingViewBean());

		StringBuilder query = new StringBuilder("select ful.*, hcu.solvent from table (PKG_HAZARDOUS_EMISSIONS_TRACK.fx_usage_logging('");
		query.append(user.getCompanyId()).append("','").append(input.getFacilityId()).append("',");
		if (input.hasSearchArgument()) {
			query.append(input.getWorkArea()).append(",'").append(input.getSearchField()).append("','").append(input.getSearchMode()).append("','").append(input.getSearchArgument()).append("'");
		}
		else {
			query.append(input.getWorkArea()).append(",'','',''");
		}
		query.append(")) ful , het_container_usage hcu where ful.container_id = hcu.container_id ");
		query.append(" order by ful.cart_id desc, ful.sort_order ASC, ful.mixture_id, ful.cat_part_no ASC, ful.item_id, ful.kit_id, ful.material_id");

		return factory.selectQuery(query.toString());
	}

	@SuppressWarnings("unchecked")
	public Collection<VvHetSubstrateBean> getSubstrates(String companyId, String facilityId, BigDecimal workArea) throws BaseException {
		factory.setBeanObject(new VvHetSubstrateBean());
		StringBuilder query = new StringBuilder("select * from table (PKG_HAZARDOUS_EMISSIONS_TRACK.FX_SUBSTRATE_ID_AREA_SEARCH('");
		query.append(companyId).append("','");
		query.append(facilityId).append("',");
		query.append(workArea).append(")) order by SUBSTRATE");
		return factory.selectQuery(query.toString());
	}


	private BigDecimal getTransactionId() throws BaseException {
		return new BigDecimal(dbManager.getOracleSequence("HET_TRANSACTION_SEQ"));
	}

	public boolean isWorkAreaAllowedSplitKits(String companyId,String facilityId, String workArea) throws BaseException{
		StringBuffer query = new StringBuffer("select count(*) from FAC_LOC_APP where COMPANY_ID =").append(SqlHandler.delimitString(companyId));
		query.append(" and FACILITY_ID = ").append(SqlHandler.delimitString(facilityId));
		query.append(" and APPLICATION = ").append(SqlHandler.delimitString(workArea));
		query.append(" and ALLOW_SPLIT_KITS = 'Y'");
		return !"0".equalsIgnoreCase(factory.selectSingleValue(query.toString()));
	}

	public String logUsage(UsageLoggingInputBean input, Collection<HetUsageBean> usageRows, PersonnelBean user) throws BaseException, Exception {
		String errMsg = null;
		HetContainerUsageBeanFactory containerFactory = new HetContainerUsageBeanFactory(dbManager);

		BigDecimal lastCartId = ZERO;
		BigDecimal lastKitId = ZERO;
		HetUsageBean currentCart = null;
		HetUsageBean currentKit = null;
		BigDecimal transactionId = null;
		try {
			for (HetUsageBean usage : usageRows) {
				usage.setUserId(user.getPersonnelIdBigDecimal());
				usage.setApplicationId(input.getWorkArea());
				usage.setStandalone(user.isStandalone());
				if (usage.isUsageForCart()) {
					if (!lastCartId.equals(usage.getCartId())) {
						currentCart = usage;
						lastCartId = usage.getCartId();

						//Start of logging a Cart, check cart in
						HetCartBeanFactory cartFactory = new HetCartBeanFactory(dbManager);
						cartFactory.markLastLogged(user.getCompanyId(), usage.getCartId());
					}
					else {
						usage.setEmployee(currentCart.getEmployee());
						usage.setUsageDate(currentCart.getUsageDate());
					}
				}
				if (usage.isUsageForKit()) {
					if (!lastKitId.equals(usage.getKitId())) {
						transactionId = getTransactionId() ; // Get a new TransactionId once per kit
						currentKit = usage;
						lastKitId = usage.getKitId();
					}
					else {
						usage.setEmployee(currentKit.getEmployee());
						usage.setUsageDate(currentKit.getUsageDate());
						usage.setDiscarded(currentKit.isDiscarded());
						usage.setPartType(currentKit.getPartType());
						usage.setPermit(currentKit.getPermit());
						usage.setApplicationMethod(currentKit.getApplicationMethod());
						usage.setSubstrate(currentKit.getSubstrate());
						usage.setRemarks(currentKit.getRemarks());
					}
				}
				else {
					transactionId = getTransactionId() ; // Get a new TransactionId for each usage
				}
				usage.setTransactionId(transactionId);
				
				updateContainer(containerFactory, usage);

				BigDecimal originalRemaining = usage.getAmountRemaining();
				BigDecimal remaining = usage.getAmountRemaining().add(usage.getQuantity());
				if (usage.getQuantity().compareTo(ZERO) == 1) {
					if (usage.isUsageForCart()) {
						// Average the usage out over the days since the cart was checked out
						int numDays = daysElapsed(usage.getCheckedOut(), usage.getUsageDate());
						if (numDays > 1) {
							BigDecimal dailyUsage = usage.getQuantity().divide(new BigDecimal(numDays), 8, RoundingMode.DOWN);
							GregorianCalendar startDate = new GregorianCalendar();
							usage.setQuantity(dailyUsage);
							startDate.setTime(usage.getCheckedOut());
							for (int i = 1; i < numDays; i++) {
								remaining = remaining.subtract(dailyUsage);
								usage.setAmountRemaining(remaining);
								usage.setUsageDate(startDate.getTime());
								insertUsage(input.getFacilityId(), usage, user);
								startDate.roll(Calendar.DAY_OF_YEAR, true);
							}
							usage.setAmountRemaining(originalRemaining);
							usage.setQuantity(remaining.subtract(originalRemaining));
							usage.setUsageDate(startDate.getTime());
							insertUsage(input.getFacilityId(), usage, user);	
						}
						else {
							insertUsage(input.getFacilityId(), usage, user);
						}
					}
					else {
						insertUsage(input.getFacilityId(), usage, user);
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
			errMsg = library.getString("generic.error");
		}
		return errMsg;

	}
	
	public void insertUsage(String facilityId, HetUsageBean usage, PersonnelBean user) throws BaseException {
		HetUsageBeanFactory usageFactory = new HetUsageBeanFactory(dbManager);
		usageFactory.insert(usage);
		if (usage.isUsageUnathorized()) {
			notifyUnauthorizedUsage(facilityId, usage, user);
		}		
	}
	
	private void notifyUnauthorizedUsage(String facilityId, HetUsageBean usage, PersonnelBean user) throws BaseException {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		PersonnelBeanFactory userFactory = new PersonnelBeanFactory(dbManager);
		@SuppressWarnings("unchecked")
		Collection<PersonnelBean> usersToBeNotified = userFactory.selectUsersByGroup(facilityId, "heTrackingEditor");
		String[] emailAddresses = new String[usersToBeNotified.size()];
		int index = 0;
		for (PersonnelBean userToBeNotified: usersToBeNotified) {
			emailAddresses[index++] = userToBeNotified.getEmail();
		}
		
		String msg = "On " + formatter.format(new Date()) + " Chemical usage was logged with an unauthorized Application Method and/or Substrate.\n";
		msg += "Usage Specifics:\n";
		msg += "\tEmployee:\t" +user.getFullName() + "\n"; 
		msg += "\tLogon ID:\t" +user.getLogonId() + "\n"; 
		msg += "\tTransaction ID:\t" +usage.getTransactionId() + "\n"; 
		msg += "\tContainer ID:\t" + usage.getContainerId() + "\n";
		msg += "\tUsage Date:\t" + usage.getUsageDate() + "\n";
		
		MailProcess mailer = new MailProcess();
		mailer.sendEmail(emailAddresses, null, "Unauthorized HET Application Method and/or Substrate", msg, false);
	}

	private void updateContainer(HetContainerUsageBeanFactory containerFactory, HetUsageBean usage) throws BaseException {
		HetContainerUsageBean container = containerFactory.select(usage.getContainerId());

		if (usage.hasContentLeft()) {
			if (usage.getUnitOfMeasure().equals(container.getUnitOfMeasure())) {
				usage.setQuantity(container.getAmountRemaining().subtract(usage.getAmountRemaining()));
			}
			else {
				BigDecimal containerQuantity = getAmountInDifferentUnit(container.getAmountRemaining(), container.getItemId(), container.getMaterialId(), container.getUnitOfMeasure(), usage.getUnitOfMeasure());
				usage.setQuantity(containerQuantity.subtract(usage.getAmountRemaining()));
			}

			if (usage.isDiscarded()) {
				container.setAmountRemaining(ZERO);
			}
			else {
				container.setAmountRemaining(usage.getAmountRemaining());
				container.setUnitOfMeasure(usage.getUnitOfMeasure());
			}
		}
		else {
			usage.setQuantity(container.getAmountRemaining());
			usage.setUnitOfMeasure(container.getUnitOfMeasure());
			container.setAmountRemaining(ZERO);
		}
		container.setApplicationId(usage.getApplicationId());
		containerFactory.update(container);
	}

	public HetUsageBean validateContainer(HetUsageBean input) throws BaseException {

		if (input.hasEmployee()) {
			input.setValidEmployee(validateEmployee(input));
		}

		if (input.hasContainer()) {
			HetContainerUsageBeanFactory containerFactory = new HetContainerUsageBeanFactory(dbManager);
			HetContainerUsageBean container = containerFactory.select(input.getContainerId());

			if (container.isEmpty()) {
				input.setValidNonEmptyContainer(false);
			}
			else if (input.hasContentLeft()) {
				if (input.getUnitOfMeasure().equals(container.getUnitOfMeasure())) {
					if (input.getAmountRemaining().compareTo(container.getAmountRemaining()) == 1) { // Greater than remaining amount
						input.setValidQuantity(false);
						input.setQuantity(container.getAmountRemaining());
					}
				}
				//			else if (input.isNonHaasPurchased()) {
				//				input.setValidUnitOfMeasure(false);
				//			}
				else {
					BigDecimal containerQuantity = getAmountInDifferentUnit(container.getAmountRemaining(), container.getItemId(), container.getMaterialId(), container.getUnitOfMeasure(), input.getUnitOfMeasure());
					if (containerQuantity == null) {
						input.setValidConversion(false);
					}
					else if (input.getAmountRemaining().compareTo(containerQuantity) == 1) { // Greater than remaining amount
						input.setValidQuantity(false);
						input.setQuantity(containerQuantity);
					}
				}
			}

			if (input.isUsageForCart() && !input.isCheckInDateInRange()) {
				input.setUsageDate(new Date());
				input.setValidCheckInDate(false);
			}
		}
		return input;
	}

	private boolean validateEmployee(HetUsageBean input) throws BaseException {
		StringBuilder query = new StringBuilder("SELECT count(uf.personnel_id) FROM user_facility uf, personnel p");
		query.append(" WHERE uf.company_id = ").append(SqlHandler.delimitString(input.getCompanyId()));
		query.append(" AND uf.facility_id =  ").append(SqlHandler.delimitString(input.getFacilityId()));
		query.append(" AND uf.personnel_id = p.personnel_id");
		query.append(" AND p.logon_id =").append(SqlHandler.delimitString(input.getEmployee().trim()));
		return !"0".equals(factory.selectSingleValue(query.toString()));
	}
}
