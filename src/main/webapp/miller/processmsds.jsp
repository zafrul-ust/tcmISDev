<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html:html lang="true">
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=iso-8859-1">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">
<LINK REL="SHORTCUT ICON" HREF="https://www.tcmis.com/images/buttons/tcmIS.ico"></LINK>
<LINK REL="stylesheet" TYPE="text/css" HREF="/css/clientpages.css"></LINK>
<SCRIPT SRC="/js/calendar.js" LANGUAGE="JavaScript"></SCRIPT>
<SCRIPT SRC="/js/common/formchek.js" LANGUAGE="JavaScript"></SCRIPT>
<SCRIPT SRC="/js/miller/msds.js" LANGUAGE="JavaScript"></SCRIPT>
<title>
<fmt:message key="processmsds.title"/>
</title>


</HEAD>

<BODY>
<DIV ID="TRANSITPAGE" STYLE="display: none;">

<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B><fmt:message key="label.pleasewait"/></B></FONT><center>

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
<B><fmt:message key="processmsds.title"/></B>
</TD>
<TD WIDTH="30%" ALIGN="RIGHT" HEIGHT="22" CLASS="headingr">
<A HREF="/tcmIS/miller/showsearchmsds.do" STYLE="color:#FFFFFF"><fmt:message key="label.home"/></A>
</TD>
</TR>
</TABLE>
<TABLE BORDER="0" CELLSPACING=1 CELLPADDING=0 WIDTH="100%" CLASS="moveup">
<TR>
<TD width="10%" CLASS="announce">
</TD>
<TD width="30%" CLASS="announce">
<html:errors/>
<logic:present name="errorBean" scope="request">
  <font face=Verdana size=2 color="red">
    <b><bean:message key="excel.label.oracleerror"/>: <c:out value="${requestScope.errorBean.cause}"/></b>
  </font>
</logic:present>

<BR>
</TD>
</TR>
</TABLE>

<html:form action="/updatemsds.do" enctype="multipart/form-data">

<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="3" WIDTH="100%" CLASS="moveup">

<TR VALIGN="MIDDLE">
<TD WIDTH="25%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.msds"/>:</B>&nbsp;
</TD>
<TD WIDTH="25%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">
<html:hidden property="msds" value="${requestScope.currentMsdsLoadViewBeanCollection[0].customerMsdsNo}"/>
<html:hidden property="onLine" value="${requestScope.currentMsdsLoadViewBeanCollection[0].onLine}"/>
<html:hidden property="fileName" value="${requestScope.currentMsdsLoadViewBeanCollection[0].content}"/>
<c:out value="${requestScope.currentMsdsLoadViewBeanCollection[0].customerMsdsNo}"/>
</TD>
<TD WIDTH="25%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">
&nbsp;
</TD>
<TD WIDTH="25%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">
&nbsp;
</TD>
</TR>

<TR VALIGN="MIDDLE">
<TD WIDTH="25%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.status"/>:</B>&nbsp;
</TD>

<TD WIDTH="25%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">
<html:select property="status" styleClass="HEADER" size="1" value="${requestScope.currentMsdsLoadViewBeanCollection[0].status}">
  <html:options collection="vvCustomerMsdsStatusBeanCollection" property="status" labelProperty="status"/>
</html:select>
</TD>

<TD CLASS="announce" HEIGHT="35" WIDTH="25%" ALIGN="LEFT">
&nbsp;
</TD>
<TD CLASS="announce" HEIGHT="35" WIDTH="25%" ALIGN="LEFT">
&nbsp;
</TD>
</TR>

<TR VALIGN="MIDDLE">
<TD WIDTH="25%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="searchmsds.label.hazardclassification"/>:</B>&nbsp;
</TD>

<TD WIDTH="25%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">
<html:select property="hazardClassification" styleClass="HEADER" size="1">
  <html:option value=""></html:option>
  <html:options collection="vvMsdsHazardClassificationBeanCollection" property="hazardClassification"
labelProperty="hazardClassification"/>
</html:select>
</TD>

<TD CLASS="announce" HEIGHT="35" WIDTH="25%" ALIGN="LEFT">
&nbsp;
</TD>
<TD CLASS="announce" HEIGHT="35" WIDTH="25%" ALIGN="LEFT">
&nbsp;
</TD>
</TR>

<TR VALIGN="MIDDLE">
<TD WIDTH="25%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.tradename"/>:</B>&nbsp;
</TD>
<TD WIDTH="25%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">
<html:text property="tradeName" value="${requestScope.currentMsdsLoadViewBeanCollection[0].tradeName}"/>
</TD>
<TD WIDTH="25%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">
<html:submit property="submitUpdate" value="Update"/>
</TD>
<TD WIDTH="25%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
&nbsp;
</TD>
</TR>

<TR VALIGN="MIDDLE">
<TD WIDTH="25%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.manufacturername"/>:</B>&nbsp;
</TD>
<TD WIDTH="25%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">
<html:text property="manufacturerName" value="${requestScope.currentMsdsLoadViewBeanCollection[0].manufacturerName}"/>
</TD>
<TD WIDTH="25%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">
&nbsp;
</TD>
<TD WIDTH="25%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
&nbsp;
</TD>
</TR>

<TR VALIGN="MIDDLE">
<TD colspan="4" WIDTH="25%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
&nbsp;
</TD>
</TR>

<TR VALIGN="MIDDLE">
<TD WIDTH="25%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.revisiondate"/>:</B>&nbsp;
</TD>

<TD WIDTH="25%" HEIGHT="35" ALIGN="LEFT" CLASS="announce"><c:out value='${param.revisionDate}'/>

<c:choose>
<c:when test="${requestScope.currentMsdsLoadViewBeanCollection[0].revisionDate == null}" >
<INPUT CLASS="HEADER" TYPE="text" NAME="revisionDate" value="<tcmis:getDateTag numberOfDaysFromToday="0"/>" maxlength="10" size="10">
</c:when>
<c:otherwise>
<%--
<c:out value='${requestScope.currentMsdsLoadViewBeanCollection[0].revisionDate}'/>
--%>
<INPUT CLASS="HEADER" TYPE="text" NAME="revisionDate" value="<fmt:formatDate pattern="MM/dd/yyyy" value="${requestScope.currentMsdsLoadViewBeanCollection[0].revisionDate}"/>" maxlength="10" size="10">
</c:otherwise>
</c:choose>
<FONT SIZE="4"><a href="javascript: void(0);" ID="revisionDate" onClick="return getCalendar(document.processMsdsForm.revisionDate);">&diams;</a></FONT>

</TD>

<TD CLASS="announce" HEIGHT="35" WIDTH="25%" ALIGN="LEFT">
&nbsp;
</TD>
<TD CLASS="announce" HEIGHT="35" WIDTH="25%" ALIGN="LEFT">
&nbsp;
</TD>
</TR>
<TR VALIGN="MIDDLE">
<TD WIDTH="25%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="processmsds.label.imagefile"/>:</B>&nbsp;
</TD>
<TD WIDTH="25%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">
<c:choose>
<c:when test="${requestScope.currentMsdsLoadViewBeanCollection[0].onLine =='Y'}">
<A HREF="<c:out value="${requestScope.currentMsdsLoadViewBeanCollection[0].content}"/>" TARGET="NEW"><fmt:message key="processmsds.link.imagefile"/></A>
</c:when>
<c:otherwise>
<fmt:message key="processmsds.label.noimage"/>
</c:otherwise>
</c:choose>
</TD>

<TD CLASS="announce" HEIGHT="35" WIDTH="25%" ALIGN="LEFT">
&nbsp;
</TD>
<TD CLASS="announce" HEIGHT="35" WIDTH="25%" ALIGN="LEFT">
&nbsp;
</TD>
</TR>
<TR VALIGN="MIDDLE">
<TD WIDTH="25%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="processmsds.label.updatefile"/>:</B>&nbsp;
</TD>
<TD WIDTH="25%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">
<html:file property="theFile" value="${requestScope.processMsdsForm.theFile}" size="50"/>
</TD>
</html:form>
<TD CLASS="announce" HEIGHT="35" WIDTH="25%" ALIGN="LEFT">
&nbsp;
</TD>
<TD CLASS="announce" HEIGHT="35" WIDTH="25%" ALIGN="LEFT">
<html:form action="/searchmsdslocation.do">
<html:hidden property="msds" value="${requestScope.currentMsdsLoadViewBeanCollection[0].customerMsdsNo}"/>
<html:submit property="submitSearch">
<fmt:message key="searchmsds.button.updatelocation"/>
</html:submit>
</html:form>
</TD>
</TR>


</TABLE>

<TABLE  BORDER="0" CELLSPACING=0 CELLPADDING=0 WIDTH="100%">
<tr>
<td>
</TD>
</tr>
</table>

<c:if test="${currentMsdsLoadViewBeanCollection != null}">
<c:set var="resultCount" value='0'/>
<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="100%" CLASS="moveup">
<TR><TD WIDTH="100%" ALIGN="LEFT" HEIGHT="22" CLASS="heading">
<B><fmt:message key="processmsds.label.currentlocations"/> <c:out value="${requestScope.currentMsdsLoadViewBeanCollection[0].customerMsdsNo}"/></B>
</TD>
</TR>
</TABLE>
<TABLE BORDER="0" BGCOLOR="#000000" CELLSPACING="1" CELLPADDING="2" WIDTH="100%" CLASS="moveup">
</c:if>
<c:forEach var="currentMsdsLoadViewBean" items="${currentMsdsLoadViewBeanCollection}" varStatus="status">
<c:if test="${status.index == 0}">
<tr align="center">
<TH width="15%"  height="38"><fmt:message key="label.facility"/></TH>
<TH width="15%"  height="38"><fmt:message key="processmsds.label.department"/></TH>
<TH width="15%"  height="38"><fmt:message key="processmsds.label.building"/></TH>
<TH width="15%"  height="38"><fmt:message key="processmsds.label.floor"/></TH>
</tr>
</c:if>

<c:set var="resultCount" value='${resultCount+1}'/>

<c:choose>
  <c:when test="${status.index % 2 == 0}" >
   <c:set var="colorClass" value='CLASS=blue'/>
   <c:set var="invisibleClass" value='CLASS=INVISIBLEHEADBLUE'/>
  </c:when>
  <c:otherwise>
   <c:set var="colorClass" value='CLASS=white'/>
   <c:set var="invisibleClass" value='CLASS=INVISIBLEHEADWHITE'/>
  </c:otherwise>
</c:choose>

<TR align="center">

<td <c:out value="${colorClass}"/> width="15%" height="38">
 <c:out value="${status.current.facilityId}"/>
</td>
<td <c:out value="${colorClass}"/> width="15%" height="38">
  <c:out value="${status.current.department}"/>
</td>
<td <c:out value="${colorClass}"/> width="15%" height="38">
  <c:out value="${status.current.building}"/>
</td>
<td <c:out value="${colorClass}"/> width="15%" height="38">
  <c:out value="${status.current.floor}"/>
</td>



</tr>

</c:forEach>
<c:if test="${resultCount == 0}">
<tr>
<td <c:out value="${colorClass}"/> width="15%" height="38" CLASS="heading">
<fmt:message key="processmsds.label.nolocations"/>
</td>
</tr>
</c:if>
<c:if test="${currentMsdsLoadViewBeanCollection != null}">
</table>
</c:if>

</DIV>
</body>
</html:html>

