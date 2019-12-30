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
<tcmis:fontSizeCss currentCss="clientpages.css"/>
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
<%--<script type="text/javascript" src="/js/menu/contextmenu.js"></script>--%>

<SCRIPT SRC="/js/catalog.js" LANGUAGE="JavaScript"></SCRIPT>
<LINK REL="stylesheet" TYPE="text/css" HREF="/css/rightclick.css"></LINK>
<SCRIPT SRC="/js/rightclick.js" LANGUAGE="JavaScript"></SCRIPT>

<%@ include file="/common/catalog/catalogpageheader.jsp" %>

</HEAD>

<c:set var="doexcelpopup" value='${doexcelpopup}'/>
<c:set var="facilityOrAllCatalog" value='${param.facilityOrAllCatalog}'/>

<c:set var="buttonCreateExcel" value='${param.buttonCreateExcel}'/>
<c:choose>
  <c:when test="${!empty doexcelpopup && !empty buttonCreateExcel}" >
    <BODY BGCOLOR="#FFFFFF" TEXT="#000000" LINK="#FFFFFF" ALINK="" VLINK="#FFFF66" onLoad="doexcelpopup()">
  </c:when>
  <c:otherwise>
   <BODY BGCOLOR="#FFFFFF" TEXT="#000000" LINK="#FFFFFF" ALINK="" VLINK="#FFFF66">
  </c:otherwise>
</c:choose>

<%-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure --%>
<div class="topNavigation" id="topNavigation">
<TABLE BORDER=0 WIDTH=100% >
<TR VALIGN="TOP">
<TD WIDTH="200">
<img src="/images/tcmtitlegif.gif" border=0 align="left">
</TD>
<TD ALIGN="right">
<img src="/images/catalog.gif" border=0 align="right">
</TD>
</TR>
</Table>

<%@ include file="/common/clientnavigation.jsp" %>
</div>
<%--
<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="100%" CLASS="moveup">
<TR><TD WIDTH="70%" ALIGN="LEFT" HEIGHT="22" CLASS="heading">
<B><fmt:message key="label.catalog"/></B>
</TD>
<TD WIDTH="30%" ALIGN="RIGHT" HEIGHT="22" CLASS="headingr">
<A HREF="/tcmIS/Hub/Home" STYLE="color:#FFFFFF"><fmt:message key="label.home"/></A>
</TD>
</TR>
</TABLE>
--%>

<DIV ID="TRANSITPAGE" STYLE="display: none;">

<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B><fmt:message key="label.pleasewait"/></B></FONT></center>

</DIV>

<DIV ID="MAINPAGE" STYLE="">
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
<!--
var rowId  =  document.getElementById("MAINPAGE");
rowId.attachEvent('onmouseup',function () {
        makeRightClickNormal();});
// -->
</SCRIPT>

<tcmis:form action="/catalog.do" onsubmit="return SubmitOnlyOnce();">

<%@ include file="/common/catalog/catalogheader.jsp" %>
<%@ include file="/common/catalog/catalogdetails.jsp" %>

</tcmis:form>
</DIV>

<DIV id="ie5menu" class="skin0" onMouseover="highlightie5()" onMouseout="lowlightie5()" onClick="jumptoie5();">
</DIV>

</body>
</html:html>