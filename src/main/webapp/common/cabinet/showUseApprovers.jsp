<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

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

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />
<!-- CSS for YUI -->
<%--
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>
<!-- Add any other stylesheets you need for the page here -->

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>

<script src="/js/common/cabinet/showApprovers.js" language="JavaScript"></script>

<title>
<fmt:message key="showapprovers.label.title"/>
</title>
</head>
<body BGCOLOR="#FFFFFF" TEXT="#000000" onload="showApproversWindow()">

<html:form action="/useapprovalstatus.do" onsubmit="return SubmitOnlyOnce();">
<c:set var="dataCount" value='${0}'/>
<c:set var="colorClass" value=''/>
<div id="showApproversBody">

<c:forEach var="overLimitUseApproverViewBean" items="${overLimitUseApproverViewBeanColl}" varStatus="status">
<c:set var="dataCount" value='${dataCount+1}'/>
<c:if test="${status.index == 0}">
<b><fmt:message key="label.workarea"/>:</b> <c:out value="${status.current.applicationDesc}"/>
<table border="0" cellpadding="0" cellspacing="0" class="tableResults">
</c:if>
<c:if test="${status.index % 10 == 0}">
<tr align="center">
<th width="50%"><fmt:message key="label.approver"/></th>
<th width="25%"><fmt:message key="label.phone"/></th>
<th width="25%"><fmt:message key="label.email"/></th>
</tr>
</c:if>

<c:choose>
  <c:when test="${status.index % 2 == 0}" >
   <c:set var="colorClass" value='class=alt'/>
  </c:when>
  <c:otherwise>
   <c:set var="colorClass" value=''/>
  </c:otherwise>
</c:choose>

<tr align="center">
  <td <c:out value="${colorClass}"/> width="40%"><c:out value="${status.current.useApprover}"/></td>
  <td <c:out value="${colorClass}"/> width="30%"><c:out value="${status.current.useApproverPhone}"/></td>
  <td <c:out value="${colorClass}"/> width="30%"><c:out value="${status.current.useApproverEmail}"/></td>
</tr>
</c:forEach>
</table>

<c:if test="${dataCount == 0}">
<div class="white">
<fmt:message key="showapprovers.label.nonefound"/>
</div>
</c:if>

</div>

</html:form>

</body>
</html:html>
