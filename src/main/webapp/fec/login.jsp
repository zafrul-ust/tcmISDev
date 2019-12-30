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
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>

<title><fmt:message key="login.title"/></title>
</head>

<%--<BODY BGCOLOR="#FFFFFF" TEXT="#000000">--%>
<BODY BGCOLOR="#FFFFFF" TEXT="#000000" LINK="#FFFFFF" ALINK="" VLINK="#FFFF66">

<div class="topNavigation" id="topNavigation">
<TABLE BORDER=0 WIDTH=100% >
<TR VALIGN="TOP">
<TD WIDTH="200">
<img src="/images/tcmtitlegif.gif" border=0 align="left">
</TD>
<TD ALIGN="right">
<img src="/images/tcmistcmis32.gif" border=0 align="right">
</TD>
</TR>
</Table>

<%@ include file="/common/clientnavigation.jsp" %>
</div>



<%--
<TABLE BORDER=0 WIDTH=100% CLASS="moveupmore">
<TR VALIGN="TOP">
<TD WIDTH="200">
<img src="/images/tcmtitlegif.gif" border=0 align="left">
</TD>
<TD ALIGN="right">
<img src="/images/tcmistcmis32.gif" border=0 align="right">
</TD>
</TR>
</Table>

<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="100%" CLASS="moveup">
<TR>
<TD WIDTH="70%" ALIGN="LEFT" HEIGHT="22" CLASS="heading">
<fmt:message key="login.header"/>
</TD>
<TD WIDTH="30%" ALIGN="RIGHT" HEIGHT="22" CLASS="headingr">
</TD>
</TR>
</TABLE>
--%>
<html:form action="/login.do">

<TABLE WIDTH="800" BORDER="0" CLASS="moveup">
<TR VALIGN="TOP"><TD BGCOLOR="#E6E8FA" HEIGHT="400">
<TABLE WIDTH="150" BORDER="0" CLASS="moveup">
<tr>
<td width="5%" CLASS="announce" ALIGN="RIGHT">
<B><fmt:message key="label.logonid"/>:</B>
</td>
<td width="95%" ALIGN="LEFT">
<html:text styleClass="HEADER" property="logonId" size="10" maxlength="15"/>
</td>
</tr>
<tr>
<td width="5%" CLASS="announce" ALIGN="RIGHT">
<B><fmt:message key="label.password"/>:</B>
</td>
<td width="95%" ALIGN="LEFT" >
<html:password styleClass="HEADER" property="password" size="10" maxlength="15"/>
</td>
</tr>

<tr>
<td width="5%" align="right">
<html:submit styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'">
     <fmt:message key="label.login"/>
</html:submit>
</td>
<td width="95%" align="left">
<html:reset styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'">
   <fmt:message key="label.reset"/>
</html:reset>
</td>
</tr>

<html:hidden property="client" value="FEC"/>
<html:hidden property="companyId" value=""/>
<html:hidden property="home" value=""/>
<html:hidden property="requestedPage" value="${requestScope.requestedPage}"/>
<html:hidden property="requestedURLWithParameters" value="${requestScope.requestedURLWithParameters}"/>

<TR><TD>&nbsp;</TD></TR>
<TR><TD>&nbsp;</TD></TR>
<TR><TD COLSPAN="2" CLASS="bluel"><html:errors/></TD></TR>
</TABLE>
</TD>
<TD WIDTH="650" HEIGHT="400" VALIGN="TOP">
<TABLE  BORDER="0" CELLSPACING=1 CELLPADDING=0 WIDTH="100%" CLASS="moveup">
<TR>
<TD WIDTH="550" COLSPAN="3">
<fmt:message key="main.loginmessage"/>
<TD>
</TR>
</TABLE>
</TD>
</TR></TABLE>
</html:form>

</body>
</html:html>
