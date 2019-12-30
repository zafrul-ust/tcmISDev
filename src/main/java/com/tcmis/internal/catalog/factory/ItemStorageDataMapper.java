package com.tcmis.internal.catalog.factory;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.internal.catalog.beans.ItemStorageBean;

public class ItemStorageDataMapper extends GenericSqlFactory implements IItemStorageDataMapper {
	
	public ItemStorageDataMapper(DbManager dbManager) {
		super(dbManager);
	}
	
	private Collection<String> listWmsEnabledInventoryGroups() throws BaseException {
		String query = "select igd.inventory_group"
				+ " from hub, inventory_group_definition igd"
				+ " where hub.automated_putaway = 'Y'"
				+ " and hub.branch_plant = igd.hub";
		
		@SuppressWarnings("unchecked")
		Collection<ItemStorageBean> hubs = this.setBean(new ItemStorageBean()).selectQuery(query);
		
		return hubs.stream().map(ItemStorageBean::getInventoryGroup).collect(Collectors.toList());
	}
	
	@SuppressWarnings("unchecked")
	private Collection<ItemStorageBean> listConfirmedPurchaseOrders() throws BaseException {
		List<ItemStorageBean> poList = new ArrayList<>();
		this.setBean(new ItemStorageBean());
		String dataTransferStatusConstraint = "IN ('OPEN', 'ERROR', 'IN_WAREHOUSE')";
		
		String query = "SELECT item_id FROM po_detail_view WHERE data_transfer_status " + dataTransferStatusConstraint;
		poList.addAll(this.selectQuery(query));
		
		query = "SELECT item_id FROM transfer_inbound_detail_view WHERE data_transfer_status_inbound " + dataTransferStatusConstraint;
		poList.addAll(this.selectQuery(query));
		
		query = "SELECT item_id FROM citr_detail_view WHERE data_transfer_status " + dataTransferStatusConstraint;
		poList.addAll(this.selectQuery(query));
		
		query = "SELECT item_id FROM customer_return_detail_view WHERE data_transfer_status " + dataTransferStatusConstraint;
		poList.addAll(this.selectQuery(query));
		
		return poList;
	}
	
	@SuppressWarnings("unchecked")
	private Collection<ItemStorageBean> findItemsWithIncompleteStorageData(Collection<String> inventoryGroups, Collection<String> additionalConstraints) throws BaseException {
		StringBuilder query = new StringBuilder("with igp as (select p1.item_id,p1.part_id,cig.inventory_group from component_inventory_group cig,part p1")
				.append(" where cig.item_id = p1.item_id and cig.inventory_group in(")
				.append(inventoryGroups.stream().map(SqlHandler::delimitString).collect(Collectors.joining(","))).append(")")
				.append(" group by p1.item_id,p1.part_id,cig.inventory_group)")
				.append(" select igp.inventory_group,igd.inventory_group_name,p.item_id,i.item_desc,coalesce(i.inseparable_kit, 'Y') inseparable_kit,")
				.append(" p.part_id,p.material_id,m.wms_acid_base,m.wms_aerosol,p.wms_container,p.wms_cont_pressure_relieving,m.wms_corrosive,")
				.append(" m.wms_flammable,m.wms_organic_peroxide,m.wms_oxidizer,m.wms_pyrophoric,m.wms_reactive,m.wms_toxic,m.wms_water_miscible,")
				.append(" case when ps.display_pkg_style = 'Tote' then 'Y' when length(ps.display_pkg_style) > 0 then 'N' else '' end ibc,")
				.append(" m.wms_water_reactive,p.part_size||' '||p.size_unit||' '||p.pkg_style packaging,")
				.append(" m.material_desc")
				.append(" from inventory_group_definition igd,vv_pkg_style ps,global.material m,part p,item i,igp")
				.append(" where ( m.wms_acid_base is null or m.wms_aerosol is null or p.wms_container is null or")
				.append(" p.wms_cont_pressure_relieving is null or m.wms_corrosive is null or m.wms_flammable is null or")
				.append(" m.wms_organic_peroxide is null or m.wms_oxidizer is null or m.wms_pyrophoric is null or")
				.append(" m.wms_reactive is null or m.wms_toxic is null or m.wms_water_miscible is null or")
				.append(" m.wms_water_reactive is null or ps.display_pkg_style is null or p.part_size is null or")
				.append(" p.size_unit is null or p.wms_container is null or p.wms_cont_pressure_relieving is null or")
				.append(" ps.display_pkg_style is null) and igd.inventory_group = igp.inventory_group and")
				.append(" ps.pkg_style (+) = p.pkg_style and m.material_id = p.material_id and p.item_id = igp.item_id and")
				.append(" p.part_id = igp.part_id and i.item_id = igp.item_id");
		if ( ! additionalConstraints.isEmpty()) {
			query.append(" and ").append(additionalConstraints.stream().collect(Collectors.joining(" and ")));
		}
		query.append(" order by igp.inventory_group,p.item_id,p.part_id");
		
		return this.setBean(new ItemStorageBean()).selectQuery(query.toString());
	}
	
	@Override
	public Collection<ItemStorageBean> getSearchData(ItemStorageBean inputBean) throws BaseException, SQLException {
		List<String> additionalConstraints = new ArrayList<>();
		if (inputBean.hasSearchText()) {
			if ("contains".equals(inputBean.getSearchType())) {
				additionalConstraints.add("lower(" + SqlHandler.validQuery(inputBean.getSearchWhat()) 
						+ ") like lower('%" + SqlHandler.validQuery(inputBean.getSearchText()) + "%')");
			} else if ("startsWith".equals(inputBean.getSearchType())) {
				additionalConstraints.add("lower(" + SqlHandler.validQuery(inputBean.getSearchWhat()) 
						+ ") like lower('" + SqlHandler.validQuery(inputBean.getSearchText()) + "%')");
			} else if ("endsWith".equals(inputBean.getSearchType())) {
				additionalConstraints.add("lower(" + SqlHandler.validQuery(inputBean.getSearchWhat()) 
						+ ") like lower('%" + SqlHandler.validQuery(inputBean.getSearchText()) + "')");
			} else {
				additionalConstraints.add(SqlHandler.validQuery(inputBean.getSearchWhat()) 
						+ " = " + SqlHandler.delimitString(inputBean.getSearchText()));
			}
		}
		return findItemsWithIncompleteStorageData(Arrays.asList(inputBean.getInventoryGroup()), additionalConstraints);
	} //end of method
	
	@Override
	public Collection<BigDecimal> findItemsWithIncompleteStorageData() throws BaseException {
		return findItemsWithIncompleteStorageData(
				listWmsEnabledInventoryGroups(),
				Arrays.asList("p.item_id in ("+listConfirmedPurchaseOrders().stream()
						.map(po -> po.getItemId().toString())
						.collect(Collectors.joining(","))+")"))
				.stream().map(ItemStorageBean::getItemId).collect(Collectors.toSet());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<PersonnelBean> listEmailRecipients() throws BaseException {
		String query = "select p.email "
				+ "from global.personnel p, customer.company_user_group_member c "
				+ "where p.personnel_id = c.personnel_id "
				+ "and c.company_id = 'Radian'"
				+ "and c.user_group_id = 'ItemStorageDataNotification'";
		
		return this.setBean(new PersonnelBean()).selectQuery(query);
	}
} //end of class
