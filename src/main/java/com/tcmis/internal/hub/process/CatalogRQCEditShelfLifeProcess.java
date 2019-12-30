package com.tcmis.internal.hub.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.tcmis.client.catalog.beans.CatalogStorageTempBean;
import com.tcmis.client.catalog.beans.VvShelfLifeBasisBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.VvStorageTempBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.CatalogRQCEditShelfLifeBean;

public class CatalogRQCEditShelfLifeProcess extends GenericProcess {

	public CatalogRQCEditShelfLifeProcess(String client, Locale locale) {
		super(client, locale);
	}
	
	public void setFormInfo(HttpServletRequest request, CatalogRQCEditShelfLifeBean input) throws BaseException {
		Connection conn = dbManager.getConnection();
		try {
			request.setAttribute("vvShelfLifeBasis", getShelfLifeBasisCollection(conn));
	    	boolean inseparable = isKitInseparable(input, conn);
	    	request.setAttribute("inseparable", inseparable);
	    	boolean labelColorRequired = isLabelColorRequired(input, conn);
	    	request.setAttribute("labelColorRequired", labelColorRequired);
	    	request.setAttribute("distributionIG", isDistributionIG(input, conn));
	    	request.setAttribute("vvSource", getValidSources(conn));
	    	request.setAttribute("vvLabelColor", getValidLabelColors(input,conn));

	    	Collection<CatalogRQCEditShelfLifeBean> beanColl = null;
			if (input.getCaller().equals("CIG")) {
				request.setAttribute("vvStorageTemp", getInventoryGroupStorageTempCollection(input, conn));
		    	beanColl = getComponents(input, conn);
			}
			else {
				request.setAttribute("vvCatalogStorageTemp", getCatalogStorageTempCollection(input, conn));
		    	beanColl = getCatalogComponents(input, conn);
			}
	    	if (inseparable) {
	    		CatalogRQCEditShelfLifeBean first = beanColl.stream().findFirst().orElse(new CatalogRQCEditShelfLifeBean());
	    		request.setAttribute("components", Collections.singletonList(first));
	    	}
	    	else {
	    		request.setAttribute("components", beanColl);
	    	}
		}
		finally {
			dbManager.returnConnection(conn);
		}
	}
	
	private Collection<VvShelfLifeBasisBean> getShelfLifeBasisCollection(Connection conn) throws BaseException {
		return factory.setBean(new VvShelfLifeBasisBean()).selectQuery("select * from vv_shelf_life_basis",conn);
	}
	
	private Collection<String> getValidSources(Connection conn) throws BaseException {
		Collection<CatalogRQCEditShelfLifeBean> sourceColl = factory.setBean(new CatalogRQCEditShelfLifeBean()).selectQuery("select distinct source from component_inventory_group", conn);
		Collection<String> strColl = sourceColl.stream().map(bean -> bean.getSource()).collect(Collectors.toList());
		return strColl;
	}
	
	private Collection<VvStorageTempBean> getInventoryGroupStorageTempCollection(CatalogRQCEditShelfLifeBean input, Connection conn) throws BaseException {
		StringBuilder query = new StringBuilder();
		query.append("select storage_temp temperature from inventory_group_storage_temp where hub = ");
		query.append(input.getHub()); // hub
		query.append(" and inventory_group = ").append(SqlHandler.delimitString(input.getInventoryGroup()));
		query.append(" order by display_order");
		return factory.setBean(new VvStorageTempBean()).selectQuery(query.toString(),conn);
	}
	
	private Collection<CatalogStorageTempBean> getCatalogStorageTempCollection(CatalogRQCEditShelfLifeBean input, Connection conn) throws BaseException {
		StringBuilder query = new StringBuilder();
		query.append("select customer_temperature_id, display_temp from catalog_storage_temp where company_id = ");
		query.append(SqlHandler.delimitString(input.getCompanyId()));
		query.append(" and catalog_id = ").append(SqlHandler.delimitString(input.getCatalogId()));
		query.append(" order by display_order");
		return factory.setBean(new CatalogStorageTempBean()).selectQuery(query.toString(),conn);
	}
	
	private boolean isKitInseparable(CatalogRQCEditShelfLifeBean input, Connection conn) throws BaseException {
		StringBuilder query = new StringBuilder();
		query.append("select i.inseparable_kit from item i where item_id = ").append(input.getItemId());
		
		return "Y".equals(factory.selectSingleValue(query.toString(),conn))?true:false;
	}
	
	private boolean isLabelColorRequired(CatalogRQCEditShelfLifeBean input, Connection conn) throws BaseException {
		String query = "select igd.require_label_color from inventory_group_definition igd";
		if (input.getInventoryGroup() == null || input.getInventoryGroup().isEmpty()) {
			if ( ! (input.getFacilityId() == null || input.getFacilityId().isEmpty())) {
				query += ", facility_inventory_group fig where fig.facility_id = "
						+ SqlHandler.delimitString(input.getFacilityId())
						+ " and fig.inventory_group = igd.inventory_group";
			}
		}
		else {
			query += " where igd.inventory_group = "+SqlHandler.delimitString(input.getInventoryGroup());
		}
		
		return "Y".equals(factory.selectSingleValue(query.toString(),conn))?true:false;
	}
	
	private Collection<String> getValidLabelColors(CatalogRQCEditShelfLifeBean input, Connection conn) throws BaseException {
		String query = "select iglc.label_color from inventory_group_label_color iglc";
		if (input.getInventoryGroup() == null || input.getInventoryGroup().isEmpty()) {
			if ( ! (input.getFacilityId() == null || input.getFacilityId().isEmpty())) {
				query += ", facility_inventory_group fig where fig.facility_id = "
						+ SqlHandler.delimitString(input.getFacilityId())
						+ " and fig.inventory_group = iglc.inventory_group";
			}
		}
		else {
			query += " where iglc.inventory_group = " + SqlHandler.delimitString(input.getInventoryGroup());
		}
		query += " order by label_color";
		
		Collection<CatalogRQCEditShelfLifeBean> labelColorBeanColl = factory.setBean(new CatalogRQCEditShelfLifeBean()).selectQuery(query, conn);
		Collection<String> labelColorColl = null;
		if ( ! (labelColorBeanColl == null || labelColorBeanColl.isEmpty())) {
			labelColorColl = labelColorBeanColl.stream().map(bean -> bean.getLabelColor()).collect(Collectors.toList());
		}
		
		return labelColorColl;
	}
	
	private boolean isDistributionIG(CatalogRQCEditShelfLifeBean input, Connection conn) throws BaseException {
		StringBuilder query = new StringBuilder();
		query.append("select count(*) ");
		query.append("from inventory_group_definition igd where ");
		query.append("igd.inventory_group = ").append(SqlHandler.delimitString(input.getInventoryGroup()));
		query.append(" and ");
		query.append("igd.distributor_ops = 'Y'");
		
		int count = 0;
		try {
			count = Integer.parseInt(factory.selectSingleValue(query.toString(),conn));
		}
		catch (Exception e) {
			throw new BaseException(e);
		}
		return count > 0;
	}
	
	private Collection<CatalogRQCEditShelfLifeBean> getComponents(CatalogRQCEditShelfLifeBean input, Connection conn) throws BaseException {
		StringBuilder query = new StringBuilder();
		query.append("select cig.*, b.jsp_label, b.shelf_life_basis_desc, igd.distributor_ops dist ");
		query.append("from component_inventory_group cig, vv_shelf_life_basis b, inventory_group_definition igd where ");
		query.append("cig.item_id = ").append(input.getItemId());
		query.append(" and ");
		query.append("cig.inventory_group = ").append(SqlHandler.delimitString(input.getInventoryGroup()));
		query.append(" and ");
		query.append("cig.inventory_group = igd.inventory_group");
		query.append(" and ");
		query.append("cig.shelf_life_basis = b.shelf_life_basis(+)");
		query.append(" order by part_id");
		
		Collection<CatalogRQCEditShelfLifeBean> components = factory.setBean(new CatalogRQCEditShelfLifeBean()).selectQuery(query.toString(),conn);
		return components;
	}
	
	private Collection<CatalogRQCEditShelfLifeBean> getCatalogComponents(CatalogRQCEditShelfLifeBean input, Connection conn) throws BaseException {
		StringBuilder query = new StringBuilder();
		query.append("select cpic.*, b.jsp_label, b.shelf_life_basis_desc, cst.display_temp storage_temp ");
		query.append("from catalog_part_item_component cpic, vv_shelf_life_basis b, catalog_storage_temp cst where ");
		query.append("cpic.item_id = ").append(input.getItemId());
		query.append(" and ");
		query.append("cpic.catalog_id = ").append(SqlHandler.delimitString(input.getCatalogId()));
		query.append(" and ");
		query.append("cpic.company_id = ").append(SqlHandler.delimitString(input.getCompanyId()));
		query.append(" and ");
		query.append("cpic.catalog_id = cst.catalog_id");
		query.append(" and ");
		query.append("cpic.company_id = cst.company_id");
		query.append(" and "); 
		query.append("cst.customer_temperature_id = cpic.customer_temperature_id");
		query.append(" and ");
		query.append("cpic.shelf_life_basis = b.shelf_life_basis(+)");
		query.append(" order by part_id");
		
		Collection<CatalogRQCEditShelfLifeBean> components = factory.setBean(new CatalogRQCEditShelfLifeBean()).selectQuery(query.toString(),conn);
		return components;
	}
	
	public void updateShelfLifeStorageTemp(CatalogRQCEditShelfLifeBean input, PersonnelBean user, Collection<CatalogRQCEditShelfLifeBean> beanColl) throws BaseException {
		Connection conn = dbManager.getConnection();
		try {
			if (beanColl.size() > 1) {
				int i = 1;
				for (CatalogRQCEditShelfLifeBean bean : beanColl) {
					bean.setItemId(input.getItemId());
					bean.setPartId(new BigDecimal(i++));
					if (input.getCaller().isEmpty() || input.getCaller().equals("CIG")) {
						bean.setApplyToAllDist(input.isApplyToAllDist());
						bean.setInventoryGroup(input.getInventoryGroup());
						updateShelfLifeStorageTempCIG(bean, user, conn);
					}
					else {
						bean.setCompanyId(input.getCompanyId());
						bean.setCatalogCompanyId(input.getCatalogCompanyId());
						bean.setCatalogId(input.getCatalogId());
						updateShelfLifeStorageTempCPIC(bean, user, conn);
					}
				}
			}
			else {
				CatalogRQCEditShelfLifeBean bean = beanColl.stream().findFirst().orElse(null);
				if (bean != null) {
					bean.setApplyToAllDist(input.isApplyToAllDist());
					bean.setItemId(input.getItemId());
					bean.setInventoryGroup(input.getInventoryGroup());
					bean.setPartId(new BigDecimal(-1));
					if (input.getCaller().isEmpty() || input.getCaller().equals("CIG")) {
						bean.setApplyToAllDist(input.isApplyToAllDist());
						bean.setInventoryGroup(input.getInventoryGroup());
						updateShelfLifeStorageTempCIG(bean, user, conn);
					}
					else {
						bean.setCompanyId(input.getCompanyId());
						bean.setCatalogCompanyId(input.getCatalogCompanyId());
						bean.setCatalogId(input.getCatalogId());
						updateShelfLifeStorageTempCPIC(bean, user, conn);
					}
				}
			}
		}
		finally {
			dbManager.returnConnection(conn);
		}
	}
	
	private void updateShelfLifeStorageTempCIG(CatalogRQCEditShelfLifeBean input, PersonnelBean user, Connection conn) throws BaseException {
		StringBuilder stmt = new StringBuilder();
		stmt.append("update component_inventory_group set ");
		if ( ! input.isApplyToAllDist()) {
			stmt.append("storage_temp = ").append(SqlHandler.delimitString(input.getStorageTemp()));
			stmt.append(",");
		}
		stmt.append("shelf_life_days = ").append(input.getShelfLifeDays());
		stmt.append(",shelf_life_basis = ").append(SqlHandler.delimitString(input.getShelfLifeBasis()));
		stmt.append(",last_updated_by = ").append(user.getPersonnelId());
		stmt.append(",last_updated_on = sysdate");
		if (input.getLabelColor() != null && ! input.getLabelColor().isEmpty()) {
			stmt.append(",label_color = ").append(SqlHandler.delimitString(input.getLabelColor()));
		}
		stmt.append(",source = ").append(SqlHandler.delimitString(input.getSource()));
		stmt.append(",comments = rtrim(").append(SqlHandler.delimitString(input.getComments()+" ")).append(" || comments)");
		stmt.append(" where item_id = ").append(input.getItemId());
		if ( ! input.isApplyToAllDist()) {
			stmt.append(" and inventory_group = ").append(SqlHandler.delimitString(input.getInventoryGroup()));
		}
		else {
			stmt.append(" and inventory_group in (select inventory_group from inventory_group_definition where distributor_ops = 'Y')");
		}
		if (input.getPartId().intValue() > 0) {
			stmt.append(" and part_id = ").append(input.getPartId());
		}
		
		factory.deleteInsertUpdate(stmt.toString(),conn);
	}
	
	private void updateShelfLifeStorageTempCPIC(CatalogRQCEditShelfLifeBean input, PersonnelBean user, Connection conn) throws BaseException {
		StringBuilder stmt = new StringBuilder();
		stmt.append("update catalog_part_item_component set ");
		stmt.append("shelf_life_days = ").append(input.getShelfLifeDays());
		stmt.append(",shelf_life_basis = ").append(SqlHandler.delimitString(input.getShelfLifeBasis()));
		stmt.append(",customer_temperature_id = ").append(SqlHandler.delimitString(input.getStorageTemp()));
		stmt.append(" where item_id = ").append(input.getItemId());
		stmt.append(" and company_id = ").append(SqlHandler.delimitString(input.getCompanyId()));
		stmt.append(" and catalog_id = ").append(SqlHandler.delimitString(input.getCatalogId()));
		if (input.getPartId().intValue() > 0) {
			stmt.append(" and part_id = ").append(input.getPartId());
		}
		
		factory.deleteInsertUpdate(stmt.toString(),conn);
	}
}
