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
<SCRIPT SRC="/js/clientlabels.js" LANGUAGE="JavaScript"></SCRIPT>
<SCRIPT SRC="/js/calendar.js" LANGUAGE="JavaScript"></SCRIPT>

<title>
<fmt:message key="label.printlabels"/>:
</title>

<c:set var="buttonPrint" value='${param.buttonPrint}'/>
<c:set var="filePath" value='${sessionScope.filePath}'/>
<c:set var="doexcelpopup" value='${doexcelpopup}'/>
<c:choose>
  <c:when test="${!empty doexcelpopup && !empty buttonPrint}" >
    <META HTTP-EQUIV="Refresh" CONTENT="0; url=<c:out value="${filePath}" />">
    </HEAD>
    <%--<BODY BGCOLOR="#FFFFFF" TEXT="#000000" LINK="#FFFFFF" onLoad="doprintzpl('<c:out value="${filePath}" />')">--%>
      <BODY BGCOLOR="#FFFFFF" TEXT="#000000" LINK="#FFFFFF">
      <TITLE>Printing Labels</TITLE>
      </HEAD>
      <BODY BGCOLOR="#FFFFFF" TEXT="#000000">
      <CENTER><img src="/images/tcmintro.gif"  border=1 align="middle"><P></P><BR>
      <FONT FACE="Arial" size="5" color="#000080"><b>Printing Labels</b></font><P></P><BR>
      <FONT FACE="Arial" size="4" color="#000080"><b></b></font><P></P><BR>
      <FONT FACE="Arial" size="4" color="#000080"><b>Please Wait ...</b></font><P></P><BR>
      </CENTER>
  </c:when>
  <c:otherwise>
</HEAD>

<BODY BGCOLOR="#FFFFFF" TEXT="#000000" LINK="#FFFFFF">

<DIV ID="TRANSITPAGE" STYLE="display: none;">
<P><BR><BR><BR></P>
<center>
 <fmt:message key="label.pleasewait"/>
</center>
</DIV>

<DIV ID="MAINPAGE" STYLE="">
<TABLE BORDER=0 WIDTH=100%>
<TR VALIGN="TOP">
<TD WIDTH="200">
<img src="/images/tcmtitlegif.gif" border=0 align="left">
</TD>
<TD ALIGN="right">
<img src="/images/tcmistcmis32.gif" border=0 align="right">
</TD>
</TR>
</Table>
<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="100%">
<TR><TD WIDTH="70%" ALIGN="LEFT" HEIGHT="22" CLASS="heading">
<B><fmt:message key="label.printlabels"/>:</B>
</TD>
<TD WIDTH="30%" ALIGN="RIGHT" HEIGHT="22" CLASS="headingr">
</TD>
</TR>
</TABLE>

<html:form action="/label.do" onsubmit="return SubmitOnlyOnce();">

<TABLE BORDER=0 WIDTH=100%>
<TR VALIGN="MIDDLE">
<TD CLASS="announce" HEIGHT="35" WIDTH="10%" ALIGN="LEFT">
<html:submit property="buttonPrint" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'">
     <fmt:message key="label.printlabels"/>
</html:submit>
</TD>

<TD CLASS="announce" HEIGHT="35" WIDTH="10%" ALIGN="LEFT">
<html:button property="buttonCreateExcel" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'" onclick= "return cancel()">
     <fmt:message key="label.close"/>
</html:button>
</TD>
</TR>
</TABLE>

<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>
<TABLE  BORDER="0" BGCOLOR="#a2a2a2" CELLSPACING=1 CELLPADDING=2 WIDTH="100%">
<c:forEach var="clientLabelBean" items="${clientLabelBeanCollection}" varStatus="status">

<c:if test="${status.index % 10 == 0}">
<c:set var="dataCount" value='${dataCount+1}'/>
<TR align="center">
<TH width="5%" CLASS="results"><fmt:message key="printlabel.label.partnum"/></TH>
<TH width="15%" CLASS="results"><fmt:message key="printlabel.label.description"/></TH>
<TH width="5%" CLASS="results"><fmt:message key="label.po"/></TH>
<TH width="5%" CLASS="results"><fmt:message key="printlabel.label.shelflife"/></TH>
<TH width="7%" CLASS="results"><fmt:message key="printlabel.label.expirationdate"/></TH>
<TH width="7%" CLASS="results"><fmt:message key="printlabel.label.recertexpdate"/></TH>
<TH width="5%" CLASS="results"><fmt:message key="printlabel.label.employee"/></TH>
<TH width="2%" CLASS="results"><fmt:message key="label.labelqty"/></TH>
</TR>
</c:if>

<c:choose>
  <c:when test="${status.index % 2 == 0}" >
   <c:set var="colorClass" value='CLASS=blue'/>
  </c:when>
  <c:otherwise>
   <c:set var="colorClass" value='CLASS=white'/>
  </c:otherwise>
</c:choose>

<TR align="center">
 <TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.catPartNo}"/></B></TD>
 <TD <c:out value="${colorClass}"/> width="15%"><c:out value="${status.current.partDescription}"/></TD>
  <INPUT TYPE="hidden" NAME="catPartNo" value="<c:out value="${status.current.catPartNo}"/>" >
  <INPUT TYPE="hidden" NAME="partDescription" value="<c:out value="${status.current.partDescription}"/>" >
 <TD <c:out value="${colorClass}"/> width="5%">
  <INPUT TYPE="text" NAME="customerPo" value="<c:out value="${status.current.customerPo}"/>" SIZE="10">
 </TD>
 <TD <c:out value="${colorClass}"/> width="5%">
  <INPUT TYPE="hidden" NAME="shelfLife" value="<c:out value="${status.current.shelfLife}"/>">
  <c:out value="${status.current.shelfLife}"/>
 </TD>
 <TD <c:out value="${colorClass}"/> width="7%">
  <INPUT TYPE="text" NAME="expirationDate" ID="expirationDate<c:out value="${status.index}"/>" value="<c:out value="${status.current.expirationDate}"/>" size="8">
  <FONT SIZE="4"><a href="javascript: void(0);" ID="expirationDate" STYLE="color:#0000ff;cursor:pointer;text-decoration:underline" onClick="return getCalendar(document.clientLabelForm.expirationDate<c:out value="${status.index}"/>);">&diams;</a></FONT>
 </TD>
 <TD <c:out value="${colorClass}"/> width="7%">
  <INPUT TYPE="text" NAME="recertExpDate" ID="recertExpDate<c:out value="${status.index}"/>" value="<c:out value="${status.current.recertExpDate}"/>" size="8">
  <FONT SIZE="4"><a href="javascript: void(0);" ID="recertExpDate" STYLE="color:#0000ff;cursor:pointer;text-decoration:underline" onClick="return getCalendar(document.clientLabelForm.recertExpDate<c:out value="${status.index}"/>);">&diams;</a></FONT>
 </TD>
 <TD <c:out value="${colorClass}"/> width="5%">
 <INPUT TYPE="text" NAME="employeeNum" value="<c:out value="${status.current.employeeNum}"/>" SIZE="10">
 </TD>
 <TD <c:out value="${colorClass}"/> width="2%">
 <INPUT TYPE="text" NAME="labelQty" value="<c:out value="${status.current.labelQty}"/>" SIZE="2">
 </TD>
</TR>
</c:forEach>

<c:if test="${dataCount == 0}">
<TD width="100%" CLASS="white">
<fmt:message key="main.nodatafound"/>
</TD>
</c:if>
</TABLE>
</html:form>
</DIV>
</c:otherwise>
</c:choose>

</BODY>
</html:html>
