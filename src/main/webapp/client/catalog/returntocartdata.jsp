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
<c:forEach var="lineItemBean" items="${lineItemCollection}" varStatus="status">
	<fmt:formatDate var="fmtDate" value="${status.current.requiredDatetime}" pattern="${dateFormatPattern}"/>
   <c:set var="criticalFlag" value="false"/>
   <c:if test="${status.current.critical == 'y'}">
		<c:set var="criticalFlag" value="true"/>
   </c:if>
	jsonobj[${status.index}]={
		   prNumber:   		'${status.current.prNumber}',
		   lineItem:   		'${status.current.lineItem}',
			workArea:   		'${status.current.application}',
		   workAreaDesc:  	'<tcmis:jsReplace value="${status.current.applicationDisplay}" processMultiLines="true"/>',
			part:					'${status.current.facPartNo}',
			description:		'<tcmis:jsReplace value="${status.current.partDescription}" processMultiLines="true"/>',
			examplePackaging: '<tcmis:jsReplace value="${status.current.examplePackaging}" processMultiLines="true"/>',
			qty:					'${status.current.quantity}',
			extPrice: 			'${status.current.prExtendedPrice}',
			leadTimeInDays: 	'',
			dateNeed: 			'${fmtDate}',
			notes: 				'<tcmis:jsReplace value="${status.current.notes}" processMultiLines="true"/>',
			critical:			'${criticalFlag}',
			facilityId:			'${status.current.facilityId}',
			catalogId:			'${status.current.catalogId}',
			catalogCompanyId:	'${status.current.catalogCompanyId}',
			partGroupNo:		'${status.current.partGroupNo}',
			catalogPrice:		'${status.current.catalogPrice}',
			baselinePrice:		'${status.current.catalogPrice}',
			currencyId:			'${status.current.currencyId}',
			orderQuantityRule:'${status.current.orderQuantityRule}',
			inventoryGroup:	'${status.current.inventoryGroup}',
		   accountSysId:	   '${status.current.accountSysId}',
		   medianLeadTime:   '${status.current.medianLeadTime}',
		   medianMrLeadTime: '${status.current.medianMrLeadTime}',
		   medianSupplyLeadTime:   '${status.current.medianSupplyLeadTime}',
			itemType:			'${status.current.itemType}'
	};
</c:forEach>
 parent.showShoppingCartData(jsonobj); 
// -->
</script>
</head>
<body></body>
</html>