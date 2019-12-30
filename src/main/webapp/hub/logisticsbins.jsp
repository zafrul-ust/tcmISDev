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
<tcmis:fontSizeCss currentCss="global.css"/>
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

<script type="text/javascript" src="/js/hub/logistics.js"></script>


<title>
 <fmt:message key="hubbin.add.title"/>
</title>
</HEAD>
<c:choose>
<c:when test="${!empty resultAddBin}" >
 <BODY onLoad="addBintoMainPage()">
</c:when>
<c:otherwise>
 <BODY>
</c:otherwise>
</c:choose>

<DIV ID="TRANSITPAGE" STYLE="display: none;">
<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B><fmt:message key="label.pleasewait"/></B></FONT></center>
</DIV>

<DIV ID="MAINPAGE" STYLE="">
<html:form action="/showlogisticsbin.do" onsubmit="return SubmitOnlyOnce();">

<INPUT CLASS="INVISIBLEHEADWHITE" TYPE="hidden" ID="itemId" NAME="itemId" value="<c:out value="${itemId}"/>" readonly>
<INPUT CLASS="INVISIBLEHEADWHITE" TYPE="hidden" ID="rowNumber" NAME="rowNumber" value="<c:out value="${rowNumber}"/>" readonly>
<INPUT CLASS="INVISIBLEHEADWHITE" TYPE="hidden" ID="branchPlant" NAME="branchPlant" value="<c:out value="${branchPlant}"/>" readonly>

<c:choose>
<c:when test="${!empty resultAddBin}" >
<INPUT CLASS="INVISIBLEHEADWHITE" TYPE="hidden" ID="vvHubBin" NAME="vvHubBin" value="<c:out value="${vvHubBin}"/>" readonly>
<BR><BR><fmt:message key="hubbin.add.successmessage"/>
<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="4" WIDTH="250" CLASS="moveup">
<TR>
<TD WIDTH="5%" ALIGN="CENTER" CLASS="announce">
 <html:button property="addBinToItemBinCollection" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'" onclick= "addBintoMainPage()">
   <fmt:message key="label.ok"/>
 </html:button>
</TD>
</TR>
</TABLE>
</c:when>
<c:otherwise>
<BR><BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<SELECT ID="vvHubBin" NAME="vvHubBin" CLASS="HEADER" size="1">
<OPTION Value="NONE"><fmt:message key="hubbin.label.selectionfromoption"/></OPTION>
 <c:forEach var="vvHubBinsBean" items="${vvHubBinsBeanBeanCollection}" varStatus="status">
  <OPTION value="<c:out value="${status.current.bin}"/>"><c:out value="${status.current.bin}"/></OPTION>
 </c:forEach>
</SELECT>

<BR><BR>
<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="4" WIDTH="250" CLASS="moveup">
<TR>
<TD WIDTH="5%" ALIGN="CENTER" CLASS="announce">
 <html:submit property="addBinToItemBinCollection" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'">
   <fmt:message key="label.add"/>
 </html:submit>
</TD>

<TD WIDTH="5%" ALIGN="CENTER" CLASS="announce">
 <html:button property="cancelButton" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'" onclick= "cancel()">
   <fmt:message key="label.cancel"/>
 </html:button>
</TD>
</TR>
</TABLE>
</c:otherwise>
</c:choose>

</html:form>
</DIV>
</BODY>
</html:html>