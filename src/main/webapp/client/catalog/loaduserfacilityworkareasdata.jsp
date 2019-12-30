<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html:html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<!-- Add any other stylesheets you need for the page here -->

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<%--NEW - grid resize--%>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>


<title>
<fmt:message key="label.and"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};

<c:set var="minimumCharLength" value='${28}'/>
<c:set var="maxFacilityDescLength" value='${0}'/>
<c:set var="maxApplicationDescLength" value='${0}'/>

var altFacilityId = new Array(
<c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
   <c:if test="${status.index > 0}">
    ,
   </c:if>
   '${status.current.facilityId}'
</c:forEach>
);

var altFacilityName = new Array(
<c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
   <c:if test="${status.index > 0}">
    ,
   </c:if>
   '${status.current.facilityName}'
	<c:if test="${maxFacilityDescLength < fn:length(status.current.facilityName)}">
		<c:set var="maxFacilityDescLength" value='${fn:length(status.current.facilityName)}'/>
	</c:if>
</c:forEach>
);

var altFacilityEcommerce = new Array(
<c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
   <c:if test="${status.index > 0}">
    ,
   </c:if>
   '${status.current.ecommerce}'
</c:forEach>
);

var altApplication = new Array();
<c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
<c:set var="applicationCount" value='${0}'/>
altApplication['<tcmis:jsReplace value="${status.current.facilityId}"/>'] = new Array(
 <c:forEach items="${status.current.applicationBeanCollection}" varStatus="status1">
 <c:if test="${status1.current.status == 'A'}">
 	<c:if test="${applicationCount > 0}">
    ,
   	</c:if>
    '<tcmis:jsReplace value="${status1.current.application}"/>'
	<c:set var="applicationCount" value='${applicationCount+1}'/>
 </c:if>
 </c:forEach>
);
</c:forEach>

var altApplicationDesc = new Array();
<c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
<c:set var="applicationCount" value='${0}'/>
altApplicationDesc['<tcmis:jsReplace value="${status.current.facilityId}"/>'] = new Array(
 <c:forEach items="${status.current.applicationBeanCollection}" varStatus="status1">
 <c:if test="${status1.current.status == 'A'}">
 	<c:if test="${applicationCount > 0}">
    ,
   	</c:if>
    '<tcmis:jsReplace value="${status1.current.applicationDesc}"/>'
	<c:set var="applicationCount" value='${applicationCount+1}'/>
	<c:if test="${maxApplicationDescLength < fn:length(status1.current.applicationDesc)}">
		<c:set var="maxApplicationDescLength" value='${fn:length(status1.current.applicationDesc)}'/>
	</c:if>
 </c:if>
 </c:forEach>
);
</c:forEach>

<c:if test="${maxFacilityDescLength > minimumCharLength}">
	<c:set var="minimumCharLength" value='${maxFacilityDescLength}'/>
</c:if>

<c:if test="${maxApplicationDescLength > minimumCharLength}">
	<c:set var="minimumCharLength" value='${maxApplicationDescLength}'/>
</c:if>

function loadpopup() {
	eval('parent.${param.callback}(altFacilityId,altFacilityName,altFacilityEcommerce,altApplication,altApplicationDesc)');
}

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="loadpopup()">

<div class="interface" id="resultsPage" style="width:650px;height:400px;">
<div class="backGroundContent">

<c:set var="dataCount" value='${0}'/>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>

<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
</div>

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

<!-- You can build your error messages below. But we want to trigger the pop-up from the main page.
So this is just used to feed the pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
  ${tcmISError}<br/>
  <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
  </c:forEach>
</div>

</body>
</html:html>