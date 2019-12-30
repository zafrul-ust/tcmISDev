package com.tcmis.client.catalog.process;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.catalog.beans.FacilityIgViewOvBean;
import com.tcmis.client.catalog.beans.InventoryGroupNameOvBean;
import com.tcmis.common.framework.BaseProcess;

/******************************************************************************
 * Process for level management
 * @version 1.0
 *****************************************************************************/
public class FacilityIgViewOvProcess
 extends BaseProcess {
 Log log = LogFactory.getLog(this.getClass());

 public FacilityIgViewOvProcess(String client) {
	super(client);
 }

 public boolean isCollection(Collection facilityIgViewOvBeanCollection,
	String facilityId, String inventoryGroup) {
	boolean isCollection = false;
	Iterator facilityIterator = facilityIgViewOvBeanCollection.iterator();
	while (facilityIterator.hasNext()) {
	 FacilityIgViewOvBean facilityIgViewOvBean = (FacilityIgViewOvBean)
		facilityIterator.next();
	 String currentfacilityId = facilityIgViewOvBean.getFacilityId();

	 if (currentfacilityId.equalsIgnoreCase(facilityId)) {
		Collection inventoryGroupCollection = (Collection) facilityIgViewOvBean.
		 getInventoryGroups();

		Iterator inventoryGroupIterator = inventoryGroupCollection.iterator();
		while (inventoryGroupIterator.hasNext()) {
		 InventoryGroupNameOvBean inventoryGroupNameOvBean = (
			InventoryGroupNameOvBean) inventoryGroupIterator.next();
		 String currentInventoryGroup = inventoryGroupNameOvBean.getInventoryGroup();
		 //log.info("currentInventoryGroup "+currentInventoryGroup+"   inventoryGroup   "+inventoryGroup+"");
		 if (currentInventoryGroup.equalsIgnoreCase(inventoryGroup)) {
			String currentCollection = inventoryGroupNameOvBean.getCollection();
			if ("Y".equalsIgnoreCase(currentCollection)) {
			 isCollection = true;
			}
		 }
		}
	 }
	}

	return isCollection;
 }
}
