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

	var directedChargeAppColl = new Array();
	var prRulesColl = new Array();
	var facAccountSysPoForDirectColl = new Array();
	var facAccountSysPoForIndirectColl = new Array();
	var chargeNumberForDirectColl = new Array();
	var chargeNumberForIndirectColl = new Array();

	<c:forEach var="tmpBean" items="${posAccountSysData.directedChargeAppColl}" varStatus="status">
		directedChargeAppColl[${status.index}]={
			application:'${tmpBean.application}'
		};
	</c:forEach>

	<c:forEach var="tmpBean" items="${posAccountSysData.prRulesColl}" varStatus="status">
		prRulesColl[${status.index}]={
			chargeType:'${tmpBean.chargeType}',
			poRequired:'${tmpBean.poRequired}',
			prAccountRequired:'${tmpBean.prAccountRequired}',
			chargeDisplay1:'${tmpBean.chargeDisplay1}',
			chargeDisplay2:'${tmpBean.chargeDisplay2}',
			chargeLabel1:'${tmpBean.chargeLabel1}',
			chargeLabel2:'${tmpBean.chargeLabel2}',
			unitPriceRequired:'${tmpBean.unitPriceRequired}',
			poSeqRequired:'${tmpBean.poSeqRequired}',
			customerRequisition:'${tmpBean.customerRequisition}'
		};
	</c:forEach>

	<fmt:formatDate var="fmtPosDefaultNeedDate" value="${posDefaultNeedDate}" pattern="${dateFormatPattern}"/>
	parent.loadPosAccountSysData("${posAccountSysData.workAreaOption}","${posAccountSysData.orderingLimit}",directedChargeAppColl,prRulesColl,"${fmtPosDefaultNeedDate}");
// -->
</script>
</head>
<body></body>
</html>