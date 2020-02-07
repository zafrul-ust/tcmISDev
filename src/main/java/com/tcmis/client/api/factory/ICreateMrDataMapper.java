package com.tcmis.client.api.factory;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

import com.tcmis.client.api.beans.EcommerceShipmentNotificationBean;
import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.order.beans.RequestLineItemBean;
import com.tcmis.common.exceptions.BaseException;

public interface ICreateMrDataMapper {

	BigDecimal getPersonnelIdFromAddressId(String addressId) throws BaseException;

	Optional<CatalogInputBean> getPrRulesByCompanyFacility(String companyId, String facilityId) throws BaseException;

	RequestLineItemBean getRequestLineItemByPrNumber(BigDecimal prNumber) throws BaseException;

	void setEndUserContactInfo(BigDecimal prNumber, String endUser, String contactInfo) throws BaseException;
	
	Optional<RequestLineItemBean> getPrLineFromPoLine(String poNumber, String releaseNumber, 
			String companyId, String facilityId, String catalogCompanyId, String catalogId) throws BaseException;
	
	void updateMrLine(RequestLineItemBean rli) throws BaseException;

	void cancelMrLine(RequestLineItemBean rli) throws BaseException;
	
	EcommerceShipmentNotificationBean getRequestByPrNumber(BigDecimal prNumber) throws BaseException;

	Collection<EcommerceShipmentNotificationBean> getRequestLinesByPrNumber(BigDecimal prNumber) throws BaseException;

	boolean isSendOrderConfirmationEmail(BigDecimal prNumber);
}
