<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
	<meta http-equiv="expires" content="-1"/>
	<%@ include file="/common/locale.jsp" %>

	<script>
		function doCallback() {
		<c:if test="${not empty container}">
			<c:choose>
				<c:when test="${!container.validNonEmptyContainer}">
					parent.invalidEmptyContainer(${param.rowId}, "${param.containerId}");
				</c:when>
				<c:when test="${!container.validContainer}">
					parent.invalidContainer(${param.rowId}, "${param.containerId}", "${param.itemId}");
				</c:when>
				<c:when test="${!container.validQuantity}">
					parent.invalidQuantity(${param.rowId}, "${param.containerId}", "${container.quantity}", "${container.amountRemaining}", "${container.unitOfMeasure}");
				</c:when>
				<c:when test="${!container.validUnitOfMeasure}">
					parent.invalidUOM(${param.rowId}, "${container.unitOfMeasure}", "${param.containerId}");
				</c:when>
				<c:when test="${!container.validConversion}">
					parent.invalidConversion(${param.rowId}, "${container.unitOfMeasure}", "${param.containerId}");
				</c:when>
				<c:when test="${!container.validEmployee}">
					parent.invalidEmployee(${param.rowId});
				</c:when>
				<c:when test="${!container.validCheckInDate}">
					parent.invalidCheckInDate(${param.rowId}, '<fmt:formatDate value="${container.checkedOut}" pattern="${dateFormatPattern}"/>', '<fmt:formatDate value="${container.usageDate}" pattern="${dateFormatPattern}"/>');
				</c:when>
				<c:otherwise>
					parent.finishSubmit();
				</c:otherwise>
			</c:choose>
		</c:if>
		<c:if test="${!empty isInventoriedContainer}">
			parent.returnValidateContainerId('${isInventoriedContainer}', '${containerId}', '${receiptId}', '${itemId}', '${param.rowId}');
		</c:if>
		}
	</script>	

</head>

<body onload="doCallback();">
</body>
</html>
