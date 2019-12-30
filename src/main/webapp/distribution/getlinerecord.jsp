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
<c:forEach var="salesQuoteLine" items="${lineColl}" varStatus="status">
 
 <c:set var="currencyId" value='${salesQuoteLine.currencyId}' />
 
 <fmt:setLocale value="en_US"/>
	 <fmt:formatNumber var="catalogPrice" value="${salesQuoteLine.catalogPrice}"  pattern="${unitpricecurrencyformat}"></fmt:formatNumber>
	 <fmt:formatNumber var="unitOfSalePrice" value="${salesQuoteLine.unitOfSalePrice}"  pattern="${unitpricecurrencyformat}"></fmt:formatNumber>
 <fmt:setLocale value="${pageLocale}" scope="page"/>
 
 <fmt:setLocale value="en_US"/>
 	<fmt:formatNumber var="value" value="${extPrice}"  pattern="${totalcurrencyformat}"></fmt:formatNumber>
 <fmt:setLocale value="${pageLocale}" scope="page"/>
  <c:set var="rcptQualityHoldSpec" value='' />
  <c:set var="rcptQualityHoldShelfLife" value='' />
  <c:if test="${salesQuoteLine.rcptQualityHoldSpec eq 'Y'}"><c:set var="rcptQualityHoldSpec" value='X' /></c:if>
  <c:if test="${salesQuoteLine.rcptQualityHoldShelfLife eq 'Y'}"><c:set var="rcptQualityHoldShelfLife" value='X' /></c:if>
  
  <tcmis:jsReplace var="catPartNo" value="${salesQuoteLine.catPartNo}" processMultiLines="false" />
  
  <tcmis:jsReplace var="specListConcat" value="${salesQuoteLine.specListConcat}" processMultiLines="true" />
  <tcmis:jsReplace var="specDetailConcat" value="${salesQuoteLine.specDetailConcat}" processMultiLines="true" />
  <tcmis:jsReplace var="specLibraryConcat" value="${salesQuoteLine.specLibraryConcat}" processMultiLines="true" />
  <tcmis:jsReplace var="specCocConcat" value="${salesQuoteLine.specCocConcat}" processMultiLines="false" />
  <tcmis:jsReplace var="specCoaConcat" value="${salesQuoteLine.specCoaConcat}" processMultiLines="false" />
 
 <c:set var="rcptQualityHoldSpec" value='' />
 <c:set var="rcptQualityHoldShelfLife" value='' />
 <c:if test="${salesQuoteLine.rcptQualityHoldSpec eq 'Y'}"><c:set var="rcptQualityHoldSpec" value='X' /></c:if>
 <c:if test="${salesQuoteLine.rcptQualityHoldShelfLife eq 'Y'}"><c:set var="rcptQualityHoldShelfLife" value='X' /></c:if>
  
 
	jsonobj[${status.index}]={
		   customerPoLine:  	'${salesQuoteLine.customerPoLine}',
		   alternateName:  			'${salesQuoteLine.alternateName}',
		   partDescription:  	'<tcmis:jsReplace value="${salesQuoteLine.partDescription}" processMultiLines="true"/>',
		   specList:  			'${salesQuoteLine.specList}',
		   hazardous:  			'${salesQuoteLine.hazardous}',
		   quantity:   			'${salesQuoteLine.quantity}',
//		   currencyId:   		'${salesQuoteLine.currencyId}',
		   catalogPrice:   		'${catalogPrice}',
		   priceBreakAvailable:   		'${salesQuoteLine.priceBreakAvailable}',
		   totalUnitOfSaleQuantity:   		'${salesQuoteLine.totalUnitOfSaleQuantity}',
		   unitOfSale:   		'${salesQuoteLine.unitOfSale}',
		   unitOfSalePrice:   	'${unitOfSalePrice}',
		   requiredShelfLife:   	'${salesQuoteLine.requiredShelfLife}',
		   deliveryType:   	'${salesQuoteLine.deliveryType}',
		   critical:   	'${salesQuoteLine.critical}',
		   inventoryGroup:   	'${salesQuoteLine.inventoryGroup}',
		   rcptQualityHoldSpec:  		'${rcptQualityHoldSpec}',
		   rcptQualityHoldShelfLife:  			'${rcptQualityHoldShelfLife}',
		   customerPartNo:  			'<tcmis:jsReplace value="${salesQuoteLine.customerPartNo}" processMultiLines="false"/>',
//		   companyId:   	'${salesQuoteLine.companyId}',		   
//		   prNumber:   	'${salesQuoteLine.prNumber}',
//		   catalogCompanyId:   	'${salesQuoteLine.catalogCompanyId}',
//		   catalogId:   	'${salesQuoteLine.catalogId}',
//		   partGroupNo:   	'${salesQuoteLine.partGroupNo}',
		   unitOfSale:   	'${salesQuoteLine.unitOfSale}',
		   unitOfSaleQuantityPerEach:   		'${salesQuoteLine.unitOfSaleQuantityPerEach}',
		   catPartNo:   		'${catPartNo}',
		   application:   		'${salesQuoteLine.application}',
		   qtyInvoiced:   		'${salesQuoteLine.qtyInvoiced}',
		   requestLineStatus:   		'${salesQuoteLine.requestLineStatus}',
		   itemId:   		    '${salesQuoteLine.itemId}',
		   expectedUnitCost:   		'${salesQuoteLine.expectedUnitCost}',
		   specListConcat:   	'${specListConcat}',		
		   specDetailConcat:   		'${specDetailConcat}',
		   specLibraryConcat:   	'${specLibraryConcat}',
		   specCocConcat:   		'${specCocConcat}',
		   specCoaConcat:   		'${specCoaConcat}',
		   minimumGrossMargin:   		'${salesQuoteLine.minimumGrossMargin}',
		   maximumGrossMargin:   		'${salesQuoteLine.maximumGrossMargin}',
		   medianSupplyLeadTime:   		'${salesQuoteLine.medianSupplyLeadTime}',
		   inventoryGroupName:   		'<tcmis:jsReplace value="${salesQuoteLine.inventoryGroupName}" />',
		   quantity:   		'${salesQuoteLine.quantity}',
		   quantityReturnAuthorized:   		'${salesQuoteLine.quantityReturnAuthorized}',
		   catalogPriceAvailable:   		'${salesQuoteLine.catalogPriceAvailable}',
		   mvItem:   		'${salesQuoteLine.mvItem}',
		   originalSalesQuoteType:   		'${salesQuoteLine.originalSalesQuoteType}',
		   blanketOrderRemainingQty:   		'${salesQuoteLine.blanketOrderRemainingQty}',
		   shippingReference:   		'${salesQuoteLine.shippingReference}',
		   radianPo:   		'${salesQuoteLine.radianPo}',		   
		   poLine:   		'${salesQuoteLine.poLine}',
		   lineAddCharges:   		'${salesQuoteLine.lineAddCharges}',
		   qtyOnhand:   		'${salesQuoteLine.qtyOnhand}',
		   qtyInpurchasing:   		'${salesQuoteLine.qtyInpurchasing}',
		   salesOrder:   		'${salesQuoteLine.salesOrder}'	   		   		 		
	};
</c:forEach>

<tcmis:jsReplace var="errorString" value="${tcmISError}" processMultiLines="true" />

<c:choose>
	  <c:when test="${!empty tcmISError}" >
	    parent.opener.showScratchPadErrorMessages('${errorString}');
	    parent.closeAllchildren();
	    parent.window.close(); 
	  </c:when>
	  <c:otherwise>
	    if(jsonobj.length>0) {
	    	for(var i=0; i < jsonobj.length; i++) {
		    	parent.window.opener.addPartNumber(jsonobj[i]);
	
		    	if ('${mvItem}' != 'Y' && jsonobj[i].mvItem == 'Y') {
		    		parent.window.opener.submitSave();
		    	}
	    	}
	    	//parent.closeAllchildren();	
		    //parent.window.close(); 
	    	parent.addNewPartsToCart('Y');
	    }	  
	  </c:otherwise>
</c:choose>

// -->
</script>
</head>
<body></body>
</html>