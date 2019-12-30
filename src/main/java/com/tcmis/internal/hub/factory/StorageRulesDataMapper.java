package com.tcmis.internal.hub.factory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbConnectionException;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.sql.builder.QueryBuilder;
import com.tcmis.common.util.sql.builder.QueryBuilderFactory;
import com.tcmis.common.util.sql.builder.QueryTable;
import com.tcmis.internal.hub.beans.ShippingInfoStorageVvBean;
import com.tcmis.internal.hub.beans.StorageRule;

public class StorageRulesDataMapper extends GenericSqlFactory implements IStorageRulesDataMapper {

	public StorageRulesDataMapper(String client, String locale) {
		super(new DbManager(client, locale));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<StorageRule> listStorageRulesByHub(String branchPlant) throws BaseException {
		Connection conn = getDbManager().getConnection();
		Collection<StorageRule> storageRules = null;
		try {
			QueryTable rules = new QueryTable("hub_storage_rule");
			QueryBuilder query = QueryBuilderFactory.generateBuilder(conn)
					.selectAll()
					.from(rules)
					.whereStringEquals("branch_plant", branchPlant)
					.orderBy(rules.col("rule_order"));
			storageRules = setBean(new StorageRule()).selectQuery(query.toString());
		} catch(SQLException e) {
			throw new BaseException(e);
		} finally {
			getDbManager().returnConnection(conn);
		}
		return storageRules;
	}

	@Override
	public void updateStorageRules(Collection<StorageRule> inputRules) throws BaseException {
		Connection conn = getDbManager().getConnection();
		try {
			try {
				conn.setAutoCommit(false);
				conn.prepareStatement("SET CONSTRAINT ak_hub_storage_rule DEFERRED").execute();
				for (StorageRule rule : inputRules) {
					if (rule.isRuleUpdated() || rule.isDelete()) {
						updateStorageRule(rule, conn);
					}
				}
				conn.prepareStatement("SET CONSTRAINT ak_hub_storage_rule IMMEDIATE").execute();
				conn.commit();
			} catch(SQLException e) {
				conn.rollback();
				throw new BaseException(e);
			} finally {
				conn.setAutoCommit(true);
			}
		} catch(SQLException e) {
			throw new BaseException(e);
		} finally {
			getDbManager().returnConnection(conn);
		}
	}
	
	private void updateStorageRule(StorageRule rule, Connection conn) throws BaseException, SQLException {
		QueryTable rules = new QueryTable("hub_storage_rule");
		if (rule.isDelete()) {
			QueryBuilder query = QueryBuilderFactory.generateBuilder(conn)
					.delete()
					.from(rules)
					.whereNumberEquals("hub_storage_rule_id", rule.getHubStorageRuleId());
			deleteInsertUpdate(query.toString());
		}
		else {
			QueryBuilder query = QueryBuilderFactory.generateBuilder(conn)
					.update(rules)
					.setNumber("rule_order", rule.getRuleOrder())
					.setString("branch_plant", rule.getBranchPlant())
					.setString("wms_acid_base", rule.getWmsAcidBase())
					.setString("wms_aerosol", rule.getWmsAerosol())
					.setString("wms_container", rule.getWmsContainer())
					.setString("wms_corrosive", rule.getWmsCorrosive())
					.setString("wms_flammable", rule.getWmsFlammable())
					.setString("wms_gas", rule.getWmsGas())
					.setString("wms_organic_peroxide", rule.getWmsOrganicPeroxide())
					.setString("wms_oxidizer", rule.getWmsOxidizer())
					.setString("wms_pyrophoric", rule.getWmsPyrophoric())
					.setString("wms_reactive", rule.getWmsReactive())
					.setString("wms_storage_temp", rule.getWmsStorageTemp())
					.setString("wms_toxic", rule.getWmsToxic())
					.setString("wms_water_miscible", rule.getWmsWaterMiscible())
					.setString("wms_water_reactive", rule.getWmsWaterReactive())
					.setString("wms_pressure_relieving", rule.getWmsPressureRelieving())
					.setString("ibc", rule.getIbc())
					.setString("detect_min_size", rule.getDetectMinSize())
					.setNumber("min_size", rule.getMinSize())
					.setString("detect_max_size", rule.getDetectMaxSize())
					.setNumber("max_size", rule.getMaxSize())
					.setString("size_unit", rule.getSizeUnit())
					.setString("storage_family", rule.getStorageFamily())
					.setString("alt_storage_family", rule.getAltStorageFamily())
					.whereNumberEquals("hub_storage_rule_id", rule.getHubStorageRuleId());
			
			if (0 == deleteInsertUpdate(query.toString(), conn)) {
				deleteInsertUpdate(query.toInsertString(), conn);
			}
		}
	}

	@Override
	public Collection<String> listAcidBaseChoices() throws BaseException {
		String query = "select wms_acid_base from vv_wms_acid_base order by priority desc";
		  
		Collection<ShippingInfoStorageVvBean> coll = setBean(new ShippingInfoStorageVvBean()).selectQuery(query);
		return coll.stream().map(ShippingInfoStorageVvBean::getWmsAcidBase).collect(Collectors.toList());
	}
	 
	@Override
	public Collection<String> listCorrosiveChoices() throws BaseException {
		String query = "select wms_corrosive from vv_wms_corrosive order by priority desc";
		  
		Collection<ShippingInfoStorageVvBean> coll = setBean(new ShippingInfoStorageVvBean()).selectQuery(query);
		return coll.stream().map(ShippingInfoStorageVvBean::getWmsCorrosive).collect(Collectors.toList());
	}
	
	@Override
	public Collection<String> listAerosolChoices() throws BaseException {
		String query = "select wms_aerosol from vv_wms_aerosol order by priority desc";
		  
		Collection<ShippingInfoStorageVvBean> coll = setBean(new ShippingInfoStorageVvBean()).selectQuery(query);
		return coll.stream().map(ShippingInfoStorageVvBean::getWmsAerosol).collect(Collectors.toList());
	}
	
	@Override
	public Collection<String> listFlammableChoices() throws BaseException {
		String query = "select wms_flammable from vv_wms_flammable order by priority desc";
		  
		Collection<ShippingInfoStorageVvBean> coll = setBean(new ShippingInfoStorageVvBean()).selectQuery(query);
		return coll.stream().map(ShippingInfoStorageVvBean::getWmsFlammable).collect(Collectors.toList());
	}
	
	@Override
	public Collection<String> listToxicChoices() throws BaseException {
		String query = "select wms_toxic from vv_wms_toxic order by priority desc";
		  
		Collection<ShippingInfoStorageVvBean> coll = setBean(new ShippingInfoStorageVvBean()).selectQuery(query);
		return coll.stream().map(ShippingInfoStorageVvBean::getWmsToxic).collect(Collectors.toList());
	}
	
	@Override
	public Collection<String> listOxidizerChoices() throws BaseException {
		String query = "select wms_oxidizer from vv_wms_oxidizer order by priority desc";
		  
		Collection<ShippingInfoStorageVvBean> coll = setBean(new ShippingInfoStorageVvBean()).selectQuery(query);
		return coll.stream().map(ShippingInfoStorageVvBean::getWmsOxidizer).collect(Collectors.toList());
	}
	
	@Override
	public Collection<String> listReactiveChoices() throws BaseException {
		String query = "select wms_reactive from vv_wms_reactive order by priority desc";
		  
		Collection<ShippingInfoStorageVvBean> coll = setBean(new ShippingInfoStorageVvBean()).selectQuery(query);
		return coll.stream().map(ShippingInfoStorageVvBean::getWmsReactive).collect(Collectors.toList());
	}
	
	@Override
	public Collection<String> listWaterReactiveChoices() throws BaseException {
		String query = "select wms_water_reactive from vv_wms_water_reactive order by priority desc";
		  
		Collection<ShippingInfoStorageVvBean> coll = setBean(new ShippingInfoStorageVvBean()).selectQuery(query);
		return coll.stream().map(ShippingInfoStorageVvBean::getWmsWaterReactive).collect(Collectors.toList());
	}
	
	@Override
	public Collection<String> listOrganicPeroxideChoices() throws BaseException {
		String query = "select wms_organic_peroxide from vv_wms_organic_peroxide order by priority desc";
		  
		Collection<ShippingInfoStorageVvBean> coll = setBean(new ShippingInfoStorageVvBean()).selectQuery(query);
		return coll.stream().map(ShippingInfoStorageVvBean::getWmsOrganicPeroxide).collect(Collectors.toList());
	}
	
	@Override
	public Collection<String> listContainerChoices() throws BaseException {
		String query = "select wms_container from vv_wms_container order by priority desc";
		  
		Collection<ShippingInfoStorageVvBean> coll = setBean(new ShippingInfoStorageVvBean()).selectQuery(query);
		return coll.stream().map(ShippingInfoStorageVvBean::getWmsContainer).collect(Collectors.toList());
	}
	
	@Override
	public Collection<String> listHubStorageFamiliesByHub(String branchPlant) throws BaseException {
		Connection conn = getDbManager().getConnection();
		Collection<String> hubStorageFamilies = null;
		try {
			QueryTable hsf = new QueryTable("hub_storage_family");
			QueryBuilder query = QueryBuilderFactory.generateBuilder(conn)
					.select(hsf.col("storage_family"))
					.from(hsf)
					.whereStringEquals("branch_plant", branchPlant)
					.orderBy(hsf.col("storage_family"));
			
			Collection<ShippingInfoStorageVvBean> coll = setBean(new ShippingInfoStorageVvBean()).selectQuery(query.toString());
			hubStorageFamilies = coll.stream().map(ShippingInfoStorageVvBean::getStorageFamily).collect(Collectors.toList());
		} catch(SQLException e) {
			throw new BaseException(e);
		} finally {
			getDbManager().returnConnection(conn);
		}
		return hubStorageFamilies;
	}
	
	@Override
	public Collection<String> listSizeUnits() throws BaseException {
		return Arrays.asList("fluid oz","gal","kg","lb","liter");
	}
	
	@Override
	public Collection<String> listGasChoices() throws BaseException {
		String query = "select wms_gas from vv_wms_gas order by priority desc";
		  
		Collection<ShippingInfoStorageVvBean> coll = setBean(new ShippingInfoStorageVvBean()).selectQuery(query);
		return coll.stream().map(ShippingInfoStorageVvBean::getWmsGas).collect(Collectors.toList());
	}
	
	@Override
	public Collection<String> listStorageTempChoices() throws BaseException {
		String query = "select wms_storage_temp from vv_wms_storage_temp order by priority desc";
		  
		Collection<ShippingInfoStorageVvBean> coll = setBean(new ShippingInfoStorageVvBean()).selectQuery(query);
		return coll.stream().map(ShippingInfoStorageVvBean::getWmsStorageTemp).collect(Collectors.toList());
	}
}
