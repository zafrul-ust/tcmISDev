package com.tcmis.client.catalog.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.catalog.beans.
 PkgInventoryDetailWebPrInventoryDetailBean;
import com.tcmis.client.catalog.beans.ItemBean;
import com.tcmis.client.catalog.beans.InventoryDetailOnHandMaterialBean;
import com.tcmis.client.catalog.beans.InventoryDetailInSupplyChainBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.factory.ReceiptDocumentViewBeanFactory;

/******************************************************************************
 * CLASSNAME: PkgInventoryDetailWebPrInventoryDetailBeanFactory <br>
 * @version: 1.0, May 13, 2005 <br>
 *****************************************************************************/

public class PkgInventoryDetailWebPrInventoryDetailBeanFactory
 extends BaseBeanFactory {

 Log log = LogFactory.getLog(this.getClass());

 //constructor
 public PkgInventoryDetailWebPrInventoryDetailBeanFactory(DbManager dbManager) {
	super(dbManager);
 }

 public Collection select(String catPartNo, String inventoryGroup,String catalogId, String partGroupNo, String catalogCompanyId, String calledFrom) throws BaseException {
	Connection connection = this.getDbManager().getConnection();

	Collection cin = new Vector();

	if (catalogId != null && catalogId.length() > 0) {
	 cin.add(new String(catalogId));
	}
	else {
	 cin.add("");
	}

	if (catPartNo != null && catPartNo.length() > 0) {
	 cin.add(new String(catPartNo));
	}
	else {
	 cin.add("");
	}

	if (partGroupNo != null && partGroupNo.length() > 0) {
	 cin.add(new String(partGroupNo));
	}
	else {
	 cin.add("");
	}

	if (inventoryGroup != null && inventoryGroup.trim().length() > 0) {
	 cin.add(new String(inventoryGroup));
	}
	else {
	 cin.add("");
	}

	Collection cout = new Vector();
	cout.add(new Integer(java.sql.Types.VARCHAR));
	cout.add(new Integer(java.sql.Types.VARCHAR));
	cout.add(new Integer(java.sql.Types.VARCHAR));
	 
	Collection result;
	if (!StringHandler.isBlankString(catalogCompanyId)) {
		Collection inOpt = new Vector(1);
		inOpt.add(new String(catalogCompanyId));
		result = this.getDbManager().doProcedure("PKG_INVENTORY_DETAIL_WEB.PR_INVENTORY_DETAIL", cin, cout,inOpt);
	}else {
		result = this.getDbManager().doProcedure("PKG_INVENTORY_DETAIL_WEB.PR_INVENTORY_DETAIL", cin, cout);
	}

	Iterator i11 = result.iterator();
	String searchQuery1 = "";
	String searchQuery2 = "";
	String searchQuery3 = "";
	int count = 0;
	while (i11.hasNext()) {
	 count++;
	 switch (count) {
		case 1:
		 searchQuery1 = (String) i11.next();
		 break;
		case 2:
		 searchQuery2 = (String) i11.next();
		 break;
		case 3:
		 searchQuery3 = (String) i11.next();
		 break;
	 }
	}

	Collection c;
     if ("inventoryDetails".equals(calledFrom)) {
        c = selectInventoryDetails(searchQuery1, searchQuery2, searchQuery3, connection);
     }else {
        //catalog right mouse click
        GenericSqlFactory factory = new GenericSqlFactory(getDbManager());
        c = getInventoryOnHand(factory,connection,searchQuery2);
     }
    this.getDbManager().returnConnection(connection);
	return c;
 }

 public Collection selectInventoryDetails(String query1, String query2, String query3,Connection conn) throws BaseException {
	Collection pkgInventoryDetailWebPrInventoryDetailBeanColl = new Vector();
	Collection<InventoryDetailOnHandMaterialBean> inventoryDetailOnHandMaterialBeanColl = new Vector();

	PkgInventoryDetailWebPrInventoryDetailBean pkgInventoryDetailWebPrInventoryDetailBean = new PkgInventoryDetailWebPrInventoryDetailBean();
	GenericSqlFactory factory = new GenericSqlFactory(getDbManager());
	ReceiptDocumentViewBeanFactory receiptDocumentFactory = new ReceiptDocumentViewBeanFactory(this.getDbManager());
	SearchCriteria criteria = new SearchCriteria();
	
    //item details
    factory.setBeanObject(new ItemBean());
	pkgInventoryDetailWebPrInventoryDetailBean.setItemDescription(factory.selectQuery(query1, conn));
	//inventory on hand
    query2 = "select item_id,lot_status,inventory_group,sum(quantity) quantity,mfg_lot,expire_date,ready_to_ship_date,owner_segment_id, program_id,trace_id,receipt_id,quality_tracking_number " + "from " + "("+
		 		query2+ ") group by item_id,lot_status,inventory_group,mfg_lot,expire_date,ready_to_ship_date,owner_segment_id,program_id,trace_id,receipt_id,quality_tracking_number";
	factory.setBeanObject(new InventoryDetailOnHandMaterialBean());
	inventoryDetailOnHandMaterialBeanColl = factory.selectQuery(query2, conn);
	//get receipt documents 
	Iterator itr = inventoryDetailOnHandMaterialBeanColl.iterator();
	while(itr.hasNext()){
		InventoryDetailOnHandMaterialBean onHandMaterial = (InventoryDetailOnHandMaterialBean) itr.next();
		criteria = new SearchCriteria();
		criteria.addCriterion("receiptId", SearchCriterion.EQUALS, ""+onHandMaterial.getReceiptId());
		criteria.addCriterion("documentUrl", SearchCriterion.IS_NOT, null);

		onHandMaterial.setReceiptDocumentColl(receiptDocumentFactory.select(criteria));
	}
	
    pkgInventoryDetailWebPrInventoryDetailBean.setOnHandMaterial(inventoryDetailOnHandMaterialBeanColl);
    //inventory in supply chain
    factory.setBeanObject(new InventoryDetailInSupplyChainBean());
	pkgInventoryDetailWebPrInventoryDetailBean.setInSupplyChain(factory.selectQuery(query3,conn));

	pkgInventoryDetailWebPrInventoryDetailBeanColl.add(pkgInventoryDetailWebPrInventoryDetailBean);
	return pkgInventoryDetailWebPrInventoryDetailBeanColl;
 }

 public Collection getInventoryOnHand(GenericSqlFactory factory, Connection conn, String query ) throws BaseException {
    query = "select item_id,sum(quantity) quantity from "+"("+query+ ") group by item_id";
	factory.setBeanObject(new InventoryDetailOnHandMaterialBean());
    return factory.selectQuery(query, conn);
 }

}