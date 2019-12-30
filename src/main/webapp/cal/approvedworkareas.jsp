<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<%--<tcmis:loggedIn indicator="true" forwardPage="/hub/Home.do"/>--%>
<html:html lang="true">
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=iso-8859-1">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">
<LINK REL="SHORTCUT ICON" HREF="https://www.tcmis.com/images/buttons/tcmIS.ico"></LINK>
<LINK REL="stylesheet" TYPE="text/css" HREF="/css/clientpages.css"></LINK>
<SCRIPT SRC="/js/ordertracking.js" LANGUAGE="JavaScript"></SCRIPT>

<title>
<fmt:message key="approvedworkareas.label.title"/>
</title>
</HEAD>

<body BGCOLOR="#FFFFFF" TEXT="#000000">

<DIV ID="TRANSITPAGE" STYLE="display: none;">
<P><BR><BR><BR></P>
<center>
 <fmt:message key="label.pleasewait"/>
</center>
</DIV>

<DIV ID="MAINPAGE" STYLE="">
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
<TR><TD WIDTH="70%" ALIGN="LEFT" HEIGHT="22" CLASS="heading">
<B><fmt:message key="approvedworkareas.label.title"/></B>
</TD>
<TD WIDTH="30%" ALIGN="RIGHT" HEIGHT="22" CLASS="headingr">
</TD>
</TR>
</TABLE>

<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="3" WIDTH="100%" CLASS="moveup">
<TR VALIGN="MIDDLE">
<TD WIDTH="7%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.catalogid"/>:</B>&nbsp;
</TD>

<TD CLASS="announce" HEIGHT="35" WIDTH="10%" ALIGN="LEFT">
<c:out value='${param.catalogId}'/>
</TD>

</TR>
<TR VALIGN="MIDDLE">

<TD WIDTH="5%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.facilityid"/>:</B>&nbsp;
</TD>

<TD CLASS="announce" HEIGHT="35" WIDTH="10%" ALIGN="LEFT">
<c:out value='${param.selectedFacilityId}'/>
</TD>

</TR>
<TR VALIGN="MIDDLE">

<TD WIDTH="5%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.partnumber"/>:</B>&nbsp;
</TD>

<TD CLASS="announce" HEIGHT="35" WIDTH="5%" ALIGN="LEFT">
<c:out value='${param.catPartNo}'/>
</TD>

</TR>
</TABLE>

<BR>
<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="3" WIDTH="100%">
<TR VALIGN="MIDDLE">
<TD CLASS="announce" HEIGHT="35" WIDTH="10%" ALIGN="LEFT">
<html:button property="buttonCancel" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'" onclick= "cancel()">
     <fmt:message key="label.close"/>
</html:button>
</TD>
</TR>
</TABLE>

<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>

<TABLE BORDER="0" width="98%" CELLPADDING="1" CELLSPACING="1">
<TR align="center">
<TH width="5%" CLASS="results"><fmt:message key="label.workareas"/></TH>
<TH width="5%" CLASS="results"><fmt:message key="label.usergroup"/></TH>
<TH width="5%" CLASS="results"><fmt:message key="approvedworkareas.label.restriction1"/></TH>
<TH width="5%" CLASS="results"><fmt:message key="approvedworkareas.label.restriction2"/></TH>
<TH width="5%" CLASS="results"><fmt:message key="approvedworkareas.label.members"/></TH>
</TR>
</TABLE>

<TABLE CLASS="columnar" WIDTH="100%" CLASS="moveup">
<TBODY>
<TR>
<TD VALIGN="TOP">
<DIV ID="orderdetail" CLASS="scroll_column250">
<TABLE BORDER="0" CELLSPACING="1" CELLPADDING="3" WIDTH="100%" ID="line_table" CLASS="moveup">

<c:forEach var="approvedWorkAreasBean" items="${approvedWorkAreasBeanCollection}" varStatus="status">
<c:set var="dataCount" value='${dataCount+1}'/>

<c:choose>
  <c:when test="${status.index % 2 == 0}" >
   <c:set var="colorClass" value='CLASS=blue'/>
  </c:when>
  <c:otherwise>
   <c:set var="colorClass" value='CLASS=white'/>
  </c:otherwise>
</c:choose>

<TR align="center">
  <TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.applicationDescDisplay}"/></TD>
  <TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.userGroupId}"/></TD>
  <TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.limit1}"/></TD>
  <TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.limit2}"/></TD>
  <TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.userGroupMembers}"/></TD>
</TR>

</c:forEach>
<c:if test="${dataCount == 0}">
<TD width="100%" 'CLASS=white'>
<fmt:message key="main.nodatafound"/>
</TD>
</c:if>

</TABLE>
</DIV>
</TD>
</TR>
</TBODY>
</TABLE>

</DIV>
</BODY>
</html:html>
