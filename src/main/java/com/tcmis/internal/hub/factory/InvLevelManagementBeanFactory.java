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
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.internal.hub.beans.InvLevelManagementBean;
import com.tcmis.internal.hub.beans.InvLevelManagementInputBean;

/******************************************************************************
 * CLASSNAME: InvLevelManagementBeanFactory <br>
 * 
 * @version: 1.0, Sep 26, 2007 <br>
 *****************************************************************************/

public class InvLevelManagementBeanFactory extends BaseBeanFactory {

	public String	ATTRIBUTE_AVG_RP_VALUE				= "AVG_RP_VALUE";

	public String	ATTRIBUTE_AVG_SL_VALUE				= "AVG_SL_VALUE";
	public String	ATTRIBUTE_AVG_UNIT_PRICE			= "AVG_UNIT_PRICE";
	public String	ATTRIBUTE_CURRENCY_ID				= "CURRENCY_ID";
	public String	ATTRIBUTE_CUSTOMER_PART				= "CUSTOMER_PART";
	public String	ATTRIBUTE_HUB						= "HUB";
	public String	ATTRIBUTE_HUB_NAME					= "HUB_NAME";
	public String	ATTRIBUTE_IN_TRANSIT				= "IN_TRANSIT";
	public String	ATTRIBUTE_INPURCHASING_MINUS_ALLOC	= "INPURCHASING_MINUS_ALLOC";
	public String	ATTRIBUTE_INVENTORY_GROUP			= "INVENTORY_GROUP";
	public String	ATTRIBUTE_INVENTORY_GROUP_NAME		= "INVENTORY_GROUP_NAME";
	public String	ATTRIBUTE_ISSUED_30_60				= "ISSUED_30_60";
	public String	ATTRIBUTE_ISSUED_60_90				= "ISSUED_60_90";
	public String	ATTRIBUTE_ISSUED_LAST_30			= "ISSUED_LAST_30";
	public String	ATTRIBUTE_ITEM_DESC					= "ITEM_DESC";
	public String	ATTRIBUTE_ITEM_ID					= "ITEM_ID";
	public String	ATTRIBUTE_LAST_RECEIVED				= "LAST_RECEIVED";
	public String	ATTRIBUTE_MAX_UNIT_PRICE			= "MAX_UNIT_PRICE";
	public String	ATTRIBUTE_MIN_UNIT_PRICE			= "MIN_UNIT_PRICE";
	public String	ATTRIBUTE_NONPICKABLE_MINUS_ALLOC	= "NONPICKABLE_MINUS_ALLOC";
	public String	ATTRIBUTE_ON_HAND					= "ON_HAND";
	public String	ATTRIBUTE_ON_HAND_VALUE				= "ON_HAND_VALUE";
	public String	ATTRIBUTE_ONORDER_MINUS_ALLOC		= "ONORDER_MINUS_ALLOC";
	public String	ATTRIBUTE_OPERATING_ENTITY_NAME		= "OPERATING_ENTITY_NAME";
	public String	ATTRIBUTE_OPS_COMPANY_ID			= "OPS_COMPANY_ID";
	public String	ATTRIBUTE_OPS_ENTITY_ID				= "OPS_ENTITY_ID";
	public String	ATTRIBUTE_PICKABLE_MINUS_ALLOC		= "PICKABLE_MINUS_ALLOC";
	public String	ATTRIBUTE_REORDER_POINT				= "REORDER_POINT";
	public String	ATTRIBUTE_REORDER_QUANTITY			= "REORDER_QUANTITY";
	public String	ATTRIBUTE_STOCKING_LEVEL			= "STOCKING_LEVEL";
	public String	ATTRIBUTE_STOCKING_METHOD			= "STOCKING_METHOD";
	public String   ATTRIBUTE_PURCHASE_CURRENCY_ID		= "PURCHASE_CURRENCY_ID";
	public String   ATTRIBUTE_PURCHASE_UNIT_COST		= "PURCHASE_UNIT_COST";

	Log				log									= LogFactory.getLog(this.getClass());

	// constructor
	public InvLevelManagementBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	@Override
	public String getColumnName(String attributeName) {
		if (attributeName.equals("hub")) {
			return ATTRIBUTE_HUB;
		}
		else if (attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if (attributeName.equals("inventoryGroupName")) {
			return ATTRIBUTE_INVENTORY_GROUP_NAME;
		}
		else if (attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if (attributeName.equals("pickableMinusAlloc")) {
			return ATTRIBUTE_PICKABLE_MINUS_ALLOC;
		}
		else if (attributeName.equals("nonpickableMinusAlloc")) {
			return ATTRIBUTE_NONPICKABLE_MINUS_ALLOC;
		}
		else if (attributeName.equals("onorderMinusAlloc")) {
			return ATTRIBUTE_ONORDER_MINUS_ALLOC;
		}
		else if (attributeName.equals("inpurchasingMinusAlloc")) {
			return ATTRIBUTE_INPURCHASING_MINUS_ALLOC;
		}
		else if (attributeName.equals("lastReceived")) {
			return ATTRIBUTE_LAST_RECEIVED;
		}
		else if (attributeName.equals("onHand")) {
			return ATTRIBUTE_ON_HAND;
		}
		else if (attributeName.equals("onHandValue")) {
			return ATTRIBUTE_ON_HAND_VALUE;
		}
		else if (attributeName.equals("avgUnitPrice")) {
			return ATTRIBUTE_AVG_UNIT_PRICE;
		}
		else if (attributeName.equals("maxUnitPrice")) {
			return ATTRIBUTE_MAX_UNIT_PRICE;
		}
		else if (attributeName.equals("minUnitPrice")) {
			return ATTRIBUTE_MIN_UNIT_PRICE;
		}
		else if (attributeName.equals("purchaseCurrencyId")) {
			return ATTRIBUTE_PURCHASE_CURRENCY_ID;
		}
		else if (attributeName.equals("purchaseUnitCost")) {
			return ATTRIBUTE_PURCHASE_UNIT_COST;
		}
		else if (attributeName.equals("reorderPoint")) {
			return ATTRIBUTE_REORDER_POINT;
		}
		else if (attributeName.equals("stockingLevel")) {
			return ATTRIBUTE_STOCKING_LEVEL;
		}
		else if (attributeName.equals("stockingMethod")) {
			return ATTRIBUTE_STOCKING_METHOD;
		}
		else if (attributeName.equals("issuedLast30")) {
			return ATTRIBUTE_ISSUED_LAST_30;
		}
		else if (attributeName.equals("issued3060")) {
			return ATTRIBUTE_ISSUED_30_60;
		}
		else if (attributeName.equals("issued6090")) {
			return ATTRIBUTE_ISSUED_60_90;
		}
		else if (attributeName.equals("avgRpValue")) {
			return ATTRIBUTE_AVG_RP_VALUE;
		}
		else if (attributeName.equals("avgSlValue")) {
			return ATTRIBUTE_AVG_SL_VALUE;
		}
		else if (attributeName.equals("itemDesc")) {
			return ATTRIBUTE_ITEM_DESC;
		}
		else if (attributeName.equals("opsCompanyId")) {
			return ATTRIBUTE_OPS_COMPANY_ID;
		}
		else if (attributeName.equals("opsEntityId")) {
			return ATTRIBUTE_OPS_ENTITY_ID;
		}
		else if (attributeName.equals("operatingEntityName")) {
			return ATTRIBUTE_OPERATING_ENTITY_NAME;
		}
		else if (attributeName.equals("hubName")) {
			return ATTRIBUTE_HUB_NAME;
		}
		else if (attributeName.equals("currencyId")) {
			return ATTRIBUTE_CURRENCY_ID;
		}
		else if (attributeName.equals("reorderQuantity")) {
			return ATTRIBUTE_REORDER_QUANTITY;
		}
		else if (attributeName.equals("inTransit")) {
			return ATTRIBUTE_IN_TRANSIT;
		}
		else if (attributeName.equals("customerPart")) {
			return ATTRIBUTE_CUSTOMER_PART;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}

	public Collection getInvLevelManagementBeanCollection(InvLevelManagementInputBean bean) throws BaseException {
		Collection invLevelManagementBeanColl = new Vector();
		Connection connection = null;
		try {
			connection = this.getDbManager().getConnection();
			Collection cin = new Vector();
			if (bean.getInventoryGroup() != null && bean.getInventoryGroup().length() > 0 && !"All".equalsIgnoreCase(bean.getInventoryGroup())) {
				cin.add(new String(bean.getInventoryGroup())); // position 1
			}
			else {
				cin.add(""); // position 1
				// cin.add("All"); // position 1
			}

			cin.add(""); // position 2

			if (bean.getHub() != null && bean.getHub().length() > 0 && !"All".equalsIgnoreCase(bean.getHub())) {
				cin.add(new String(bean.getHub())); // position 3
			}
			else {
				cin.add(""); // position 3
			}

			if (bean.getSearchArgument() != null && bean.getSearchArgument().length() > 0) {

				cin.add((bean.getSearchArgument())); // position 4
				cin.add((bean.getSearchField())); // position 5
			}
			else {
				cin.add(""); // position 4
				cin.add(""); // position 5
			}

			if (bean.getShowOnlyOnHand() != null && bean.getShowOnlyOnHand().equals("Yes")) {
				cin.add("Y"); // position 6
			}
			else {
				cin.add(""); // position 6
			}

			if (bean.getDoLimitToRecent() != null && bean.getDoLimitToRecent().equals("Yes")) {
				cin.add(bean.getDaysSinceIssuedLimit().trim()); // position 7
			}
			else {
				cin.add(""); // position 7
			}

			cin.add(bean.getPersonnelId());
			cin.add(bean.getOpsEntityId());

			Collection cout = new Vector();

			cout.add(new Integer(java.sql.Types.VARCHAR));
			cout.add(new Integer(java.sql.Types.VARCHAR));

			Collection result = null;
			SqlManager sqlManager = new SqlManager();
			result = sqlManager.doProcedure(connection, "PKG_LEVEL_MANAGEMENT.PR_LEVEL_MANAGEMENT", cin, cout);

			Iterator resultIterator = result.iterator();
			String searchQuery = "";

			while (resultIterator.hasNext()) {
				searchQuery = (String) resultIterator.next();;
			}

			DataSet dataSet = new SqlManager().select(connection, searchQuery);
			Iterator dataIter = dataSet.iterator();

			while (dataIter.hasNext()) {
				DataSetRow dataSetRow = (DataSetRow) dataIter.next();
				InvLevelManagementBean invLevelManagementBean = new InvLevelManagementBean();
				load(dataSetRow, invLevelManagementBean);
				invLevelManagementBeanColl.add(invLevelManagementBean);
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}

		return invLevelManagementBeanColl;
	}


	// get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, InvLevelManagementBean.class);
	}
}