package com.tcmis.client.dana.process;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.admin.beans.CustomerFacilityIgViewBean;
import com.tcmis.common.admin.beans.InventoryGroupDefinitionBean;
import com.tcmis.common.admin.factory.CustomerFacilityIgViewBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.internal.hub.beans.ReceivingInputBean;

/******************************************************************************
 * Process for receiving
 * @version 1.0
 *****************************************************************************/
public class DanaReadonlyReceivingProcess
 extends BaseProcess {
 Log log = LogFactory.getLog(this.getClass());

 public DanaReadonlyReceivingProcess(String client) {
	super(client);
 }

 public Collection getCustomerFacilityIgViewBean() throws BaseException,
	Exception {
	DbManager dbManager = new DbManager(getClient());
	CustomerFacilityIgViewBeanFactory customerFacilityIgViewBeanFactory = new
	 CustomerFacilityIgViewBeanFactory(dbManager);
	SearchCriteria criteria = new SearchCriteria();

	Collection customerFacilityIgViewBeanCollection =
	 customerFacilityIgViewBeanFactory.select(criteria);

	HashMap resultdata = new HashMap();
	//Collection hubID = null;
	Collection finalCustomerFacilityIgViewBeanCollection = new Vector();
	String lastFacility = "";

	Iterator i11 = customerFacilityIgViewBeanCollection.iterator();
	while (i11.hasNext()) {

	 CustomerFacilityIgViewBean customerFacilityIgViewBean = (
		CustomerFacilityIgViewBean) i11.next(); ;

	 String tmpInventoryGroup = customerFacilityIgViewBean.getInventoryGroup();
	 String tmpinvgrpname = customerFacilityIgViewBean.getInventoryGroupName();
	 String tmpFacility = customerFacilityIgViewBean.getFacilityId();

	 if (!tmpFacility.equalsIgnoreCase(lastFacility)) {
		//hubID.add(tmpFacility);
		finalCustomerFacilityIgViewBeanCollection.add(customerFacilityIgViewBean);

		HashMap fh = new HashMap();
		Vector invGrp = new Vector();
		Vector invname = new Vector();

		invGrp.add("All");
		invname.add("All");

		invGrp.add(tmpInventoryGroup);
		invname.add(tmpinvgrpname);
		fh.put("INVENTORY_GROUP", invGrp);
		fh.put("INVENTORY_GROUP_NAME", invname);
		resultdata.put(tmpFacility, fh);
	 }
	 else {
		HashMap fh = (HashMap) resultdata.get(tmpFacility);
		Vector invGrp = (Vector) fh.get("INVENTORY_GROUP");
		Vector invName = (Vector) fh.get("INVENTORY_GROUP_NAME");
		if (!invGrp.contains(tmpInventoryGroup)) {
		 invGrp.add(tmpInventoryGroup);
		 invName.add(tmpinvgrpname);
		}

		//fh.put( "FACILITY_ID",facID );
		fh.put("INVENTORY_GROUP", invGrp);
		fh.put("INVENTORY_GROUP_NAME", invName);
		resultdata.put(tmpFacility, fh);
	 }
	 lastFacility = tmpFacility;
	}

	i11 = finalCustomerFacilityIgViewBeanCollection.iterator();
	while (i11.hasNext()) {

	 CustomerFacilityIgViewBean customerFacilityIgViewBean = (
		CustomerFacilityIgViewBean) i11.next(); ;
	 String tmpFacility = customerFacilityIgViewBean.getFacilityId();

	 HashMap fh = (HashMap) resultdata.get(tmpFacility);
	 Vector invGrp = (Vector) fh.get("INVENTORY_GROUP");
	 Vector invName = (Vector) fh.get("INVENTORY_GROUP_NAME");

	 Collection inventoryGroupDefinitionBeanCollection = new Vector();

	 for (int i = 0; i < invGrp.size(); i++) {
		String inveGroup = (String) invGrp.elementAt(i);
		String invGroupName = (String) invName.elementAt(i);

		InventoryGroupDefinitionBean inventoryGroupDefinitionBean = new
		 InventoryGroupDefinitionBean();
		inventoryGroupDefinitionBean.setInventoryGroup(inveGroup);
		inventoryGroupDefinitionBean.setInventoryGroupName(invGroupName);
		inventoryGroupDefinitionBean.setIssueGeneration("Counting");

		inventoryGroupDefinitionBeanCollection.add(inventoryGroupDefinitionBean);
	 }
	 customerFacilityIgViewBean.setInventoryGroupDefinitionBeanCollection(
		inventoryGroupDefinitionBeanCollection);
	}

	return finalCustomerFacilityIgViewBeanCollection;
 }

 private boolean validateInput(ReceivingInputBean bean) {
	/*
	 if (bean == null ||
	 bean.getCompanyId() == null || bean.getCompanyId().trim().length() < 1 ||
	 bean.getPersonnelId() == 0 ||
	 bean.getQueryName() == null || bean.getQueryName().trim().length() < 1 ||
	 bean.getQuery() == null || bean.getQuery().trim().length() < 1) {
		return false;
	 }
	 else {
		return true;
	 }
	 */
	return true;
 }

}
