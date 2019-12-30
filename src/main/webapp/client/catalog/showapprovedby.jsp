<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>
<%@page import="org.json.*"%>
<html>
<head>

<script language="JavaScript" type="text/javascript">
<!--

 
// -->
</script>


</head>

<body bgcolor="#ffffff">

<c:choose>
	<c:when test="${param.action == 'searchLineItem'}">
		<script type="text/javascript">
		<!--
		/*Storing the data to be displayed in a JSON object array.*/

		var jsonMainData = {			      								
			approvedBy:'<tcmis:jsReplace  value="${requestLineItemColl[0].fullName}" processMultiLines="false"/>',
			phone:'${requestLineItemColl[0].phone}',
			email:'${requestLineItemColl[0].email}',
			reason:'<tcmis:jsReplace  value="${requestLineItemColl[0].userApprovalComment}" processMultiLines="true"/>'						
		};
		
		eval('parent.showApprovedByWin(jsonMainData,"searchLineItem")');
		//-->
		</script>
	</c:when>
    <c:otherwise>	
		<script type="text/javascript">
		<!--
		/*Storing the data to be displayed in a JSON object array.*/		
		var jsonMainData = {			      									
			approvedBy:'<tcmis:jsReplace value="${purchaseRequestColl[0].fullName}" processMultiLines="false"/>',
			phone:'${purchaseRequestColl[0].phone}',
			email:'${purchaseRequestColl[0].email}',
			reason:'<tcmis:jsReplace  value="${purchaseRequestColl[0].rejectionReason}" processMultiLines="true"/>'								
		};	
		eval('parent.showApprovedByWin(jsonMainData,"searchPr")');
		//-->
		</script>
	</c:otherwise>
</c:choose>


</body>
</html:html>