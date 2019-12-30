package com.tcmis.internal.hub.factory;


import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.internal.hub.beans.CabinetMrCreateViewBean;

/******************************************************************************
 * CLASSNAME: CabinetMrCreateViewBeanFactory <br>
 * 
 * @version: 1.0, Jul 27, 2006 <br>
 *****************************************************************************/

public class CabinetMrCreateViewBeanFactory extends CabinetMaterialRequestFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_ORDERING_FACILITY = "ORDERING_FACILITY";
	public String ATTRIBUTE_ORDERING_APPLICATION = "ORDERING_APPLICATION";
	public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_MR_QUANTITY = "MR_QUANTITY";
	public String ATTRIBUTE_STOCKED = "STOCKED";
	public String ATTRIBUTE_UNIT_PRICE = "UNIT_PRICE";
	public String ATTRIBUTE_CATALOG_PRICE = "CATALOG_PRICE";
	public String ATTRIBUTE_ACCOUNT_SYS_ID = "ACCOUNT_SYS_ID";
	public String ATTRIBUTE_HUB = "HUB";
	public String ATTRIBUTE_COUNT_DATETIME = "COUNT_DATETIME";
	public String ATTRIBUTE_NUM_BINS = "NUM_BINS";
	public String ATTRIBUTE_NUM_BINS_SCANNED = "NUM_BINS_SCANNED";
	public String ATTRIBUTE_CABINET_COUNT = "CABINET_COUNT";
	public String ATTRIBUTE_OPEN_MR_QTY = "OPEN_MR_QTY";
	public String ATTRIBUTE_REORDER_POINT = "REORDER_POINT";
	public String ATTRIBUTE_STOCKING_LEVEL = "STOCKING_LEVEL";
	public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";
	public String ATTRIBUTE_CATALOG_COMPANY_ID = "CATALOG_COMPANY_ID";
	public String AUTO_APPROVE_CAB_SCAN_FIN_APPR = "AUTO_APPROVE_CAB_SCAN_FIN_APPR";
	public String AUTO_APPROVE_CAB_SCAN_USE_APPR = "AUTO_APPROVE_CAB_SCAN_USE_APPR";
    public String ATTRIBUTE_BIN_ID = "BIN_ID";
    public String ATTRIBUTE_CUSTOMER_ID = "CUSTOMER_ID";
    public String ATTRIBUTE_BILL_TO_COMPANY_ID = "BILL_TO_COMPANY_ID";
    public String ATTRIBUTE_BILL_TO_LOCATION_ID = "BILL_TO_LOCATION_ID";
    public String ATTRIBUTE_PAYMENT_TERMS = "PAYMENT_TERMS";
    public String ATTRIBUTE_DEF_CUSTOMER_SERVICE_REP_ID = "DEF_CUSTOMER_SERVICE_REP_ID";
    public String ATTRIBUTE_FIELD_SALES_REP_ID = "FIELD_SALES_REP_ID";
    public String ATTRIBUTE_OPS_COMPANY_ID = "OPS_COMPANY_ID";
    public String ATTRIBUTE_OPS_ENTITY_ID = "OPS_ENTITY_ID";
    public String ATTRIBUTE_SHELF_LIFE_REQUIRED = "SHELF_LIFE_REQUIRED";
    public String ATTRIBUTE_DISTRIBUTOR_OPS = "DISTRIBUTOR_OPS";
    public String ATTRIBUTE_DEFAULT_SHIPPING_TERMS = "DEFAULT_SHIPPING_TERMS";
	public String ATTRIBUTE_PRICE_GROUP_ID = "PRICE_GROUP_ID";
	public String ATTRIBUTE_DIST_CURRENCY_ID = "DIST_CURRENCY_ID";
	public String ATTRIBUTE_SHIP_COMPLETE = "SHIP_COMPLETE";
	public String ATTRIBUTE_DEFAULT_CURRENCY_ID = "DEFAULT_CURRENCY_ID";
	public String ATTRIBUTE_DIST_UNIT_PRICE = "DIST_UNIT_PRICE";
    public String ATTRIBUTE_EXPECTED_UNIT_COST = "EXPECTED_UNIT_COST";
    public String ATTRIBUTE_CUSTOMER_PART_NO = "CUSTOMER_PART_NO";
    public String ATTRIBUTE_DROP_SHIP_OVERRIDE = "DROP_SHIP_OVERRIDE";

    //table name
	public String TABLE = "CABINET_MR_CREATE_VIEW";

	//constructor
	public CabinetMrCreateViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if (attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if (attributeName.equals("catalogId")) {
			return ATTRIBUTE_CATALOG_ID;
		}
		else if (attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if (attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if (attributeName.equals("orderingFacility")) {
			return ATTRIBUTE_ORDERING_FACILITY;
		}
		else if (attributeName.equals("orderingApplication")) {
			return ATTRIBUTE_ORDERING_APPLICATION;
		}
		else if (attributeName.equals("partGroupNo")) {
			return ATTRIBUTE_PART_GROUP_NO;
		}
		else if (attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if (attributeName.equals("mrQuantity")) {
			return ATTRIBUTE_MR_QUANTITY;
		}
		else if (attributeName.equals("stocked")) {
			return ATTRIBUTE_STOCKED;
		}
		else if (attributeName.equals("unitPrice")) {
			return ATTRIBUTE_UNIT_PRICE;
		}
		else if (attributeName.equals("catalogPrice")) {
			return ATTRIBUTE_CATALOG_PRICE;
		}
		else if (attributeName.equals("accountSysId")) {
			return ATTRIBUTE_ACCOUNT_SYS_ID;
		}
		else if (attributeName.equals("hub")) {
			return ATTRIBUTE_HUB;
		}
		else if (attributeName.equals("countDatetime")) {
			return ATTRIBUTE_COUNT_DATETIME;
		}
		else if (attributeName.equals("numBins")) {
			return ATTRIBUTE_NUM_BINS;
		}
		else if (attributeName.equals("numBinsScanned")) {
			return ATTRIBUTE_NUM_BINS_SCANNED;
		}
		else if (attributeName.equals("cabinetCount")) {
			return ATTRIBUTE_CABINET_COUNT;
		}
		else if (attributeName.equals("openMrQty")) {
			return ATTRIBUTE_OPEN_MR_QTY;
		}
		else if (attributeName.equals("reorderPoint")) {
			return ATTRIBUTE_REORDER_POINT;
		}
		else if (attributeName.equals("stockingLevel")) {
			return ATTRIBUTE_STOCKING_LEVEL;
		}
		else if (attributeName.equals("currencyId")) {
			return ATTRIBUTE_CURRENCY_ID;
		}
		else if (attributeName.equals("catalogCompanyId")) {
			return ATTRIBUTE_CATALOG_COMPANY_ID;
		}
		else if (attributeName.equals("autoApproveCabScanFinAppr")) {
			return AUTO_APPROVE_CAB_SCAN_FIN_APPR;
		}
		else if (attributeName.equals("autoApproveCabScanUseAppr")) {
			return AUTO_APPROVE_CAB_SCAN_USE_APPR;
		}
        else if(attributeName.equals("binId")) {
			return ATTRIBUTE_BIN_ID;
		}
        else if(attributeName.equals("customerId")) {
			return ATTRIBUTE_CUSTOMER_ID;
        }
        else if(attributeName.equals("billToCompanyId")) {
			return ATTRIBUTE_BILL_TO_COMPANY_ID;
        }
        else if(attributeName.equals("billToLocationId")) {
			return ATTRIBUTE_BILL_TO_LOCATION_ID;
        }
        else if(attributeName.equals("paymentTerms")) {
			return ATTRIBUTE_PAYMENT_TERMS;
        }
        else if(attributeName.equals("defCustomerServiceRepId")){
        	return ATTRIBUTE_DEF_CUSTOMER_SERVICE_REP_ID;
        }
        else if(attributeName.equals("fieldSalesRepId")){
        	return ATTRIBUTE_FIELD_SALES_REP_ID;
        }
        else if(attributeName.equals("opsCompanyId")){
        	return ATTRIBUTE_OPS_COMPANY_ID;
        }
        else if(attributeName.equals("opsEntityId")){
        	return ATTRIBUTE_OPS_ENTITY_ID;
        }
        else if(attributeName.equals("shelfLifeRequired"))
        {
        	return ATTRIBUTE_SHELF_LIFE_REQUIRED;
        }
        else if(attributeName.equals("distributorOps"))
        {
        	return ATTRIBUTE_DISTRIBUTOR_OPS;        
        }
        else if(attributeName.equals("defaultShippingTerms"))
        {
        	return ATTRIBUTE_DEFAULT_SHIPPING_TERMS;
        }
		else if(attributeName.equals("priceGroupId")) {
			return ATTRIBUTE_PRICE_GROUP_ID;
		}
		else if(attributeName.equals("distCurrencyId")) {
			return ATTRIBUTE_DIST_CURRENCY_ID;
		}
		else if(attributeName.equals("shipComplete")) {
			return ATTRIBUTE_SHIP_COMPLETE;
		}
		else if(attributeName.equals("defaultCurrencyId")) {
			return ATTRIBUTE_DEFAULT_CURRENCY_ID;
		}
		else if(attributeName.equals("distUnitPrice")) {
			return ATTRIBUTE_DIST_UNIT_PRICE;
		}
        else if(attributeName.equals("expectedUnitCost")) {
			return ATTRIBUTE_EXPECTED_UNIT_COST;
		}
        else if(attributeName.equals("customerPartNo")) {
			return ATTRIBUTE_CUSTOMER_PART_NO;
		}
        else if(attributeName.equals("dropShipOverride")) {
                    return ATTRIBUTE_DROP_SHIP_OVERRIDE;
        }                
        else {
			return super.getColumnName(attributeName);
		}
	}

	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, CabinetMrCreateViewBean.class);
	}

    //select
	public Collection select(SearchCriteria criteria) throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(criteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

	public Collection select(SearchCriteria criteria, Connection conn) throws BaseException {

		Collection cabinetMrCreateViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " + getWhereClause(criteria) + "order by " + ATTRIBUTE_ITEM_ID;

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			CabinetMrCreateViewBean cabinetMrCreateViewBean = new CabinetMrCreateViewBean();
			load(dataSetRow, cabinetMrCreateViewBean);
			cabinetMrCreateViewBeanColl.add(cabinetMrCreateViewBean);
		}

		return cabinetMrCreateViewBeanColl;
	}
}