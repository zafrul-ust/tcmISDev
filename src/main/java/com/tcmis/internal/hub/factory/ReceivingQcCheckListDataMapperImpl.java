package com.tcmis.internal.hub.factory;

import java.math.BigDecimal;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.SqlHandler;

import radian.tcmis.common.util.StringHandler;

public class ReceivingQcCheckListDataMapperImpl extends GenericSqlFactory implements ReceivingQcCheckListDataMapper {

	public ReceivingQcCheckListDataMapperImpl(DbManager dbManager) {
		super(dbManager);
	}
	
	public ReceivingQcCheckListDataMapperImpl(DbManager dbManager, Object bean) {
		super(dbManager, bean);
	}
	
	@Override
	public String definedShelfLifeItem(BigDecimal po, BigDecimal poLine) throws BaseException {
		String subquery = "select supplier, item_id, inventory_group"
				+ " from po_line"
				+ " where radian_po = " + SqlHandler.validBigDecimal(po)
				+ " and p.po_line = " + SqlHandler.validBigDecimal(poLine);
		return definedShelfLifeItem(subquery);
	}

	@Override
	public String definedShelfLifeItem(BigDecimal receiptId) throws BaseException {
		String subquery = "select p.supplier, r.item_id, r.inventory_group"
				+ " from po p, receipt r"
				+ " where (r.receipt_id = " + SqlHandler.validBigDecimal(receiptId)
				+ " or r.original_receipt_id = " + SqlHandler.validBigDecimal(receiptId)
				+ ") and p.radian_po = r.radian_po";
		return definedShelfLifeItem(subquery);		
	}
	
	private String definedShelfLifeItem(String subquery) throws BaseException {
		String item = null;
		
		String query = "with p as (" + subquery + ")"
				+ " select coalesce(y.item_id, 0) item"
				+ " from supplier_inventory_life x, ig_supplier_defined_item_life y, p"
				+ " where x.item_id = y.item_id"
				+ " and x.supplier = y.supplier"
				+ " and x.ops_entity_id = y.ops_entity_id"
				+ " and y.item_id = p.item_id"
				+ " and y.supplier = p.supplier"
				+ " and y.inventory_group = p.inventory_group";
		
		item = selectSingleValue(query);
		
		return item;
	}

}
