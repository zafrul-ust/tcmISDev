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
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<script language="JavaScript" type="text/javascript">
<!--
// content of your Javascript goes here
var jsonobj = new Array();
<c:forEach var="bean" items="${resultColl}" varStatus="status">
 
 <tcmis:jsReplace var="catalogDesc" value="${bean.catalogDesc}" />
 
	jsonobj[${status.index}]={
		   companyIdCustomerMsds:  	'${bean.companyId}|${bean.customerMsdsDb}',
		   catalogDesc:  			'${catalogDesc}'		
	};
</c:forEach>
if(jsonobj.length == 1) {
	if('${param.callBack}' == 'addPart')
		parent.addPart(jsonobj[0].companyIdCustomerMsds);
	else
		parent.setCompanyIdandCustomerMsdsDbvalidateLine(jsonobj[0].companyIdCustomerMsds);

} else if(jsonobj.length>1) {
	loc = "../client/catalog/selectcompanyidcustomermsdsdb.jsp?callBack="+'${param.callBack}';  
	openWinGeneric(loc,"selectcompanyidcustomermsdsdb","650","220","yes","50","50","no");  
} else {
	try {
		alert("No data available for adding a part.");
    	parent.parent.closeTransitWin();
   } catch(ex) {}
}	  

// -->
</script>
</head>
<body></body>
</html>