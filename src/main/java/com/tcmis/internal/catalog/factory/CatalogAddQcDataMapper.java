package com.tcmis.internal.catalog.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.internal.catalog.beans.CatalogAddQcInputBean;
import com.tcmis.internal.catalog.beans.CatalogAddRequestVendorBean;
import com.tcmis.internal.catalog.beans.CatalogAddVendorQueueInputBean;

public interface CatalogAddQcDataMapper {

	public Collection<CatalogAddRequestVendorBean> listRequests(CatalogAddVendorQueueInputBean input, String fallbackStatus, boolean fullRequest) throws BaseException;
	public void updateRequestAssignedTo(HashMap<String, Collection<String>> assignments, Collection<String> errorMessages);
	public void updateWqiAssignedTo(CatalogAddVendorQueueInputBean input, CatalogAddRequestVendorBean row) throws BaseException;
	public void unassignWqi(CatalogAddVendorQueueInputBean input, CatalogAddRequestVendorBean row) throws BaseException;
	public String userSupplier(PersonnelBean user) throws BaseException;
	public void approveWorkQueueItem(CatalogAddRequestVendorBean bean, PersonnelBean personnelBean, Connection connection) throws BaseException;
	public Collection<CatalogAddQcInputBean> listCatalogVendor() throws BaseException;
	public String vendorNotificationEmail(CatalogAddRequestVendorBean bean) throws BaseException;
}
