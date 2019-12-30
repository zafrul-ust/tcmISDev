<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>
<c:set var="page" scope="request" value="${page}"/>
<html:html lang="true">
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=iso-8859-1">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">
<SCRIPT SRC="/js/client/report/formattedscaqmdreport.js" LANGUAGE="JavaScript"></SCRIPT>
<SCRIPT SRC="/clientscripts/calendar.js" LANGUAGE="JavaScript"></SCRIPT>
<LINK REL="SHORTCUT ICON" HREF="https://www.tcmis.com/images/buttons/tcmIS.ico"></LINK>
<LINK REL="stylesheet" TYPE="text/css" HREF="/css/clientpages.css"></LINK>
<LINK REL="stylesheet" TYPE="text/css" HREF="/css/global.css"></LINK>
<title><fmt:message key="login.title"/></title>
</head>

<c:set var="generateReport" value='${param.generateReport}'/>

<BODY BGCOLOR="#FFFFFF" TEXT="#000000" LINK="#FFFFFF" ALINK="" VLINK="#FFFF66">
<TABLE BORDER=0 WIDTH=100% >
<TR VALIGN="TOP">
<TD WIDTH="200">
<img src="/images/tcmtitlegif.gif" border=0 align="left">
</TD>
<TD ALIGN="right">
<img src="/images/formattedScaqmd.gif" border=0 align="right">
</TD>
</TR>
</Table>

<%@ include file="title.jsp" %>

<DIV ID="TRANSITPAGE" STYLE="display: none;">
<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B><fmt:message key="label.pleasewait"/></B></FONT></center>
</DIV>

<%-- body of report --%>
<DIV ID="MAINPAGE" STYLE="">
<html:form action="/formattedscaqmdreport.do" onsubmit="return submitOnlyOnce();">
<%-- global variables --%>
<c:set var="selectedBeginMonth" value=''/>
<c:set var="selectedBeginYear" value=''/>
<c:choose>
<c:when test="${empty generateReport}" >
   <c:set var="selectedBeginMonth" value='${beginMonth}'/>
   <c:set var="selectedBeginYear" value='${beginYear}'/>
</c:when>
<c:otherwise>
   <c:set var="selectedBeginMonth" value='${param.beginMonth}'/>
   <c:set var="selectedBeginYear" value='${param.beginYear}'/>
</c:otherwise>
</c:choose>

<c:set var="showReport" value='${showReport}'/>
<c:choose>
  <c:when test="${empty showReport || showReport == null}" >
    <BODY BGCOLOR="#FFFFFF" TEXT="#000000">
  </c:when>
  <c:otherwise>
   <BODY BGCOLOR="#FFFFFF" TEXT="#000000" onLoad="showReportResult()">
  </c:otherwise>
</c:choose>
<%-- end of global variables --%>

<%-- add header info here --%>
<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="3" WIDTH="100%" CLASS="moveup">
<TR><TD><center><B><fmt:message key="formattedscaqmdreport.label.tile"/></B></center></TD></TR>
   <TR>
      <TD>
         <html:radio property="reportType" value="masterList"/>
         <B><fmt:message key="formattedscaqmdreport.radio.masterList"/></B>
      </TD>
   </TR>
   <TR>
      <TD>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         <html:checkbox property="includeCommentField" value="Y"/>
         <B><fmt:message key="formattedscaqmdreport.label.includeCommentField"/></B>
      </TD>
   </TR>
   <TR>
      <TD>
         <html:radio property="reportType" value=""/>
         <B><fmt:message key="formattedscaqmdreport.radio.monthly"/></B>
      </TD>
   </TR>
   <TR>
      <TD>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         <html:select property="beginMonth" name="beginMonth" value="${selectedBeginMonth}">
         <html:option value="0">January</html:option>
         <html:option value="1">February</html:option>
         <html:option value="2">March</html:option>
         <html:option value="3">April</html:option>
         <html:option value="4">May</html:option>
         <html:option value="5">June</html:option>
         <html:option value="6">July</html:option>
         <html:option value="7">August</html:option>
         <html:option value="8">September</html:option>
         <html:option value="9">October</html:option>
         <html:option value="10">November</html:option>
         <html:option value="11">December</html:option>
         </html:select>

         <html:select property="beginYear" name="beginYear" value="${selectedBeginYear}">
         <html:options collection="yearCollection" name="KeyValuePairBean" labelProperty="value" property="key"/>
         </html:select>
      </TD>
   </TR>
   <%-- blank lines --%>
   <TR>
      <TD>&nbsp;</TD>
   </TR>
   <TR>
      <TD>&nbsp;</TD>
   </TR>
   <TR>
      <TD>&nbsp;
         <B><fmt:message key="formattedscaqmdreport.label.outputFormat"/></B>
      </TD>
   </TR>
   <TR>
      <TD>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         <html:radio property="reportFormat" value=""/>
         <B><fmt:message key="formattedscaqmdreport.radio.pdf"/></B>
         &nbsp;&nbsp;&nbsp;
         <html:radio property="reportFormat" value="xls"/>
         <B><fmt:message key="formattedscaqmdreport.radio.xls"/></B>
      </TD>
   </TR>

   <TR>
      <TD COLSPAN="5"><center>
         <html:submit property="generateReport" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'" onclick="generateReportAudit()">
         <fmt:message key="button.generatereport"/>
         </html:submit>
      </center></TD>
   </TR>
</Table>
<%-- end of report paramater info --%>

</html:form>
</DIV>
<%-- end of body of report --%>



</body>
</html:html>
