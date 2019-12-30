<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html:html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>

<script type="text/javascript">
<!--
<c:set var="orderStatusDataCount" value='${0}'/>
<c:if test="${!empty orderStatusDetailCollection}">
var orderStatusJsonMainData = new Array();
var orderStatusJsonMainData = {
rows:[
<c:forEach var="orderStatus" items="${orderStatusDetailCollection}" varStatus="orderStatus">
<c:if test="${orderStatus.index > 0}">,</c:if>

<tcmis:jsReplace var="materialDesc" value="${orderStatus.current.materialDesc}"  processMultiLines="true"/>
<tcmis:jsReplace var="packaging" value="${orderStatus.current.packaging}"  processMultiLines="true"/>
<fmt:formatDate var="fmtRequestDate" value="${orderStatus.current.requestDate}" pattern="${dateFormatPattern}"/>

 /*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
 data:['${orderStatus.current.materialDesc}','${packaging}','${materialDesc}','${orderStatus.current.requestId}','${fmtRequestDate}',
       '${orderStatus.current.requestorName}','${orderStatus.current.facilityId}','${orderStatus.current.requestStatusDesc}',
       '${orderStatus.current.application}']} 
 <c:set var="orderStatusDataCount" value='${orderStatusDataCount+1}'/>
 </c:forEach>
]};
</c:if>

var title =	'<fmt:message key="label.approvaldetail"/>';



<c:set var="approvalDataCount" value='${0}'/>
<c:if test="${!empty approvalDetailCollection}">
var approvalJsonMainData = new Array();
var approvalJsonMainData = {
rows:[
<c:forEach var="approvalDetail" items="${approvalDetailCollection}" varStatus="approvalStatus">
<c:if test="${approvalStatus.index > 0}">,</c:if>

<fmt:formatDate var="fmtApprovalDate" value="${approvalStatus.current.approvalDate}" pattern="${dateFormatPattern}"/>
<c:set var="approvalDateSortable" value="${approvalStatus.current.approvalDate.time}"/>

<tcmis:jsReplace var="reason" value="${approvalStatus.current.reason}"  processMultiLines="true"/>
<c:set var="tmpApprovalRole"><c:out value="${approvalStatus.current.approvalRole}"></c:out></c:set>
 /*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
 data:['<tcmis:jsReplace value="${tmpApprovalRole}"/>','${approvalStatus.current.status}',
	 '<tcmis:jsReplace value="${approvalStatus.current.chemicalApprovers}"/>', 
    '${fmtApprovalDate}','${approvalDateSortable}','${reason}']}
<c:set var="approvalDataCount" value='${approvalDataCount+1}'/>
 </c:forEach>
]};
</c:if>

if( (typeof( approvalJsonMainData ) != 'undefined') && (typeof( orderStatusJsonMainData ) != 'undefined'))
parent.showApprovalDetailWin(orderStatusJsonMainData,approvalJsonMainData,title);
//-->
</script>

</head>
<body>
</body>
</html:html>