<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>
<%@ include file="/common/locale.jsp" %>
<%@page import="org.json.*"%>
<html>
<head>
<script language="JavaScript" type="text/javascript">
<!--
// content of your Javascript goes here
var jsonobj = new Array();
<c:forEach var="customerInfoBean" items="${customerInfo}" varStatus="status">
  <tcmis:jsReplace var="customerName" value='${customerBean.customerName}' processMultiLines="false" />
  <tcmis:jsReplace var="billToAddress" value='${customerBean.billToAddress}' processMultiLines="true" />
  <tcmis:jsReplace var="locationDesc" value='${customerBean.locationDesc}' processMultiLines="true"/>
  <tcmis:jsReplace var="shipToAddressLine1" value='${customerBean.shipToAddressLine1Display}' processMultiLines="true"/>
  <tcmis:jsReplace var="shipToAddressLine2" value='${customerBean.shipToAddressLine2Display}' processMultiLines="true"/>
  <tcmis:jsReplace var="shipToAddressLine3" value='${customerBean.shipToAddressLine3Display}' processMultiLines="true"/>
  <tcmis:jsReplace var="shipToAddressLine4" value='${customerBean.shipToAddressLine4Display}' processMultiLines="true"/>
  <tcmis:jsReplace var="shipToAddressLine5" value='${customerBean.shipToAddressLine5Display}' processMultiLines="true"/>
  <tcmis:jsReplace var="addressLine1Display" value='${customerBean.addressLine1Display}' processMultiLines="true"/>
  <tcmis:jsReplace var="addressLine2Display" value='${customerBean.addressLine2Display}' processMultiLines="true"/>
  <tcmis:jsReplace var="addressLine3Display" value='${customerBean.addressLine3Display}' processMultiLines="true"/>
  <tcmis:jsReplace var="addressLine4Display" value='${customerBean.addressLine4Display}' processMultiLines="true"/>
  <tcmis:jsReplace var="addressLine5Display" value='${customerBean.addressLine5Display}' processMultiLines="true"/>
  <tcmis:jsReplace var="notes" value='${customerBean.notes}' processMultiLines="true"/>
  
  <tcmis:jsReplace var="locationShortName" value='${customerBean.locationShortName}' processMultiLines="false"/>
  <tcmis:jsReplace var="opsEntityName" value='${customerBean.opsEntityName}' processMultiLines="false"/>
  <tcmis:jsReplace var="salesAgentName" value='${customerBean.salesAgentName}' processMultiLines="false"/>
  <tcmis:jsReplace var="fieldSalesRepName" value='${customerBean.fieldSalesRepName}' processMultiLines="false"/>
	jsonobj[${status.index}]={
		   customerId:   		'${status.current.customerId}',	
		   customerName:   		'${customerName}',
		   companyId:   		'${status.current.companyId}',	
		   facilityId:   		'${status.current.facilityId}',	
		   shipToLocationId:   		'${status.current.shipToLocationId}',
		   shipToCompanyId:   		'${status.current.shipToCompanyId}',
		   salesAgentId:   		'${status.current.salesAgentId}',
		   salesAgentName:   		'<tcmis:jsReplace value="${status.current.salesAgentName}" />',		
		   inventoryGroupDefault:   		'${status.current.inventoryGroupDefault}',
		   inventoryGroupName:   		'<tcmis:jsReplace value="${status.current.inventoryGroupName}" />',
		   fieldSalesRepId:   		'${status.current.fieldSalesRepId}',
		   fieldSalesRepName:   		'<tcmis:jsReplace value="${status.current.fieldSalesRepName}" />',
		   locationShortName:   		'${locationShortName}',	
		   locationDesc:   		'${locationDesc}',
		   billToAddress:   		'${billToAddress}',
		   paymentTerms:   		'${status.current.paymentTerms}',
		   creditLimit:   		'${status.current.creditLimit}',	
		   overdueLimit:   		'${status.current.overdueLimit}',
		   overdueLimitBasis:   		'${status.current.overdueLimitBasis}',
		   creditStatus:   		'${status.current.creditStatus}',			
           currencyId:   		'${status.current.defaultCurrencyId}',
           fixedCurrency:   		'${status.current.fixedCurrency}',
           priceGroupId:   		'${status.current.priceGroupId}',
           shelfLifeRequired:   		'${status.current.shelfLifeRequired}',
           shipComplete:   		'${status.current.shipComplete}',
           accountSysId:   		'${status.current.accountSysId}',
		   chargeType:   		'${status.current.chargeType}',
		   shipToAddressLine1Display:  '${shipToAddressLine1Display}',
		   shipToAddressLine2Display:  '${shipToAddressLine2Display}',
		   shipToAddressLine3Display:  '${shipToAddressLine3Display}',
		   shipToAddressLine4Display:  '${shipToAddressLine4Display}',
		   shipToAddressLine5Display:  '${shipToAddressLine5Display}',
		   billToLocationId:   		'${status.current.billToLocationId}',
		   billToCompanyId:   		'${status.current.billToCompanyId}',
		   addressLine1Display:   		'${addressLine1Display}',
		   addressLine2Display:   		'${addressLine2Display}',
		   addressLine3Display:   		'${addressLine3Display}',
		   addressLine4Display:   		'${addressLine4Display}',
		   addressLine5Display:   		'${addressLine5Display}',		
		   notes:   		'${notes}',
		   chargeFreight:   		'${status.current.chargeFreight}',
		   opsCompanyId:   		'${status.current.opsCompanyId}',
		   opsEntityId:   		'${status.current.opsEntityId}',
		   operatingEntityName:   		'${opsEntityName}',
		   availableCredit:   		'${status.current.availableCredit}'					   		   		 		
	};
</c:forEach>
if(jsonobj.length>0)
  parent.displayCustomerInfo(jsonobj[0],${personnelId},'<tcmis:jsReplace value="${lastName}"/>','<tcmis:jsReplace value="${firstName}" />','N');
// -->
</script>
</head>
<body></body>
</html>