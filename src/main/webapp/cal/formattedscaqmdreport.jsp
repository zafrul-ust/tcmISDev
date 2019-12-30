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
<SCRIPT SRC="/js/calendar/newcalendar.js" LANGUAGE="JavaScript"></SCRIPT>
<SCRIPT SRC="/js/calendar/AnchorPosition.js" LANGUAGE="JavaScript"></SCRIPT>
<SCRIPT SRC="/js/calendar/PopupWindow.js" LANGUAGE="JavaScript"></SCRIPT>
<LINK REL="SHORTCUT ICON" HREF="https://www.tcmis.com/images/buttons/tcmIS.ico"></LINK>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js" language="JavaScript"></script>
<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>

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

<tcmis:form action="/formattedscaqmdreport.do" onsubmit="return submitOnlyOnce();">
<%-- global variables --%>
<c:set var="selectedBeginDate" value=''/>
<c:set var="selectedEndDate" value=''/>
<c:choose>
<c:when test="${empty generateReport}" >
   <c:set var="selectedBeginDate" value='${beginDate}'/>
   <c:set var="selectedEndDate" value='${endDate}'/>
</c:when>
<c:otherwise>
   <c:set var="selectedBeginDate" value='${param.beginDate}'/>
   <c:set var="selectedEndDate" value='${param.endDate}'/>
</c:otherwise>
</c:choose>

<c:set var="showReport" value='${showReport}'/>
<c:choose>
  <c:when test="${empty showReport || showReport == null}" >
    <BODY BGCOLOR="#FFFFFF" TEXT="#000000" LINK="#FFFFFF" ALINK="" VLINK="#FFFF66">
  </c:when>
  <c:otherwise>
   <BODY BGCOLOR="#FFFFFF" TEXT="#000000" LINK="#FFFFFF" ALINK="" VLINK="#FFFF66" onLoad="showReportResult()">
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
         <B><fmt:message key="formattedscaqmdreport.label.beginDate"/></B>
         <INPUT TYPE="text" name="beginDate" ID="beginDate" value="<c:out value="${selectedBeginDate}"/>" size="6" maxlength="10" Class="INVISIBLEHEADWHITE">
         <FONT SIZE="4"><a href="javascript: void(0);" ID="linkbeginDate" onClick="return getCalendar(document.formattedScaqmdReportForm.beginDate);" STYLE="color:#0000ff;cursor:pointer;text-decoration:underline">&diams;</a></FONT></TD>
      </TD>
   </TR>
   <TR>
      <TD>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         <B><fmt:message key="formattedscaqmdreport.label.endDate"/></B>
         <INPUT TYPE="text" name="endDate" ID="endDate" value="<c:out value="${selectedEndDate}"/>" size="6" maxlength="10" Class="INVISIBLEHEADWHITE">
         <FONT SIZE="4"><a href="javascript: void(0);" ID="linkendDate" onClick="return getCalendar(document.formattedScaqmdReportForm.endDate);" STYLE="color:#0000ff;cursor:pointer;text-decoration:underline">&diams;</a></FONT></TD>
      </TD>
   </TR>
   <%-- blank line --%>
   <TR>
      <TD>&nbsp;</TD>
   </TR>
   <TR>
      <TD>&nbsp;
         <B><fmt:message key="formattedscaqmdreport.label.workGroup"/></B>
      </TD>
   </TR>
   <TR>
      <TD>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         <html:radio property="workGroup" value=""/>
         <B><fmt:message key="formattedscaqmdreport.radio.acmx"/></B>
         <html:radio property="workGroup" value="GSE"/>
         <B><fmt:message key="formattedscaqmdreport.radio.gse"/></B>
         <html:radio property="workGroup" value="All"/>
         <B><fmt:message key="label.all"/></B>
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

</tcmis:form>
</DIV>
<%-- end of body of report --%>

</body>
</html:html>
