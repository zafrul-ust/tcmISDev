<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html:html lang="true">
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=iso-8859-1">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">
<LINK REL="SHORTCUT ICON" HREF="https://www.tcmis.com/images/buttons/tcmIS.ico"></LINK>
<LINK REL="stylesheet" TYPE="text/css" HREF="/css/clientpages.css">
<SCRIPT SRC="/js/common/peiprojects.js" LANGUAGE="JavaScript"></SCRIPT>
<SCRIPT SRC="/js/common/projectdocument.js" LANGUAGE="JavaScript"></SCRIPT>
<SCRIPT SRC="/js/calendar/newcalendar.js" LANGUAGE="JavaScript"></SCRIPT>
<SCRIPT SRC="/js/common/formchek.js" LANGUAGE="JavaScript"></SCRIPT>
<SCRIPT SRC="/js/calendar/AnchorPosition.js" LANGUAGE="JavaScript"></SCRIPT>
<SCRIPT SRC="/js/calendar/PopupWindow.js" LANGUAGE="JavaScript"></SCRIPT>

<title>
<fmt:message key="peiproject.label.project"/>
</title>
</HEAD>

<c:set var="submitPrint" value='${param.submitPrint}'/>
<c:choose>
  <c:when test="${!empty submitPrint && !empty submitPrint}" >
    <BODY BGCOLOR="#FFFFFF" TEXT="#000000" LINK="#FFFFFF" ALINK="" VLINK="#FFFF66" onLoad="showPdfPrint()">
  </c:when>
  <c:otherwise>
   <BODY BGCOLOR="#FFFFFF" TEXT="#000000" LINK="#FFFFFF" ALINK="" VLINK="#FFFF66">
  </c:otherwise>
</c:choose>



<TABLE BORDER=0 WIDTH="100%" >
<TR VALIGN="TOP">
<TD WIDTH="200">
<img src="/images/tcmtitlegif.gif" border=0 align="left">
</TD>
<TD ALIGN="right">
<img src="/images/addeditprojects.gif" border=0 align="right">
</TD>
</TR>
</TABLE>

<%@ include file="title.jsp" %>

<DIV ID="TRANSITPAGE" STYLE="display: none;">
<P><BR><BR><BR></P>
<center>
 <fmt:message key="label.pleasewait"/>
</center>
</DIV>

<DIV ID="MAINPAGE" STYLE="">
<html:form action="/peiproject.do" onsubmit="return submitOnlyOnce();">

<c:set var="colorClass" value=''/>
<c:set var="rowCount" value='${0}'/>

<c:if test="${!empty errorMessage}">
 <c:out value="${errorMessage}"/>
</c:if>

<html:errors/>
<TABLE BORDER="0" BGCOLOR="#a2a2a2" WIDTH="800" CELLSPACING="1" CELLPADDING="3" CLASS="moveup">
<c:forEach var="peiProjectViewBean" items="${peiProjectViewBeanCollection}" varStatus="status">

<INPUT TYPE="hidden" NAME="projectId" VALUE="<c:out value="${status.current.projectId}"/>">
<TR VALIGN="MIDDLE">
<TD WIDTH="5%" COLSPAN="7" CLASS="white">
<html:button property="submitPrint" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'" onclick="printPdf()">
     <fmt:message key="label.print"/>
</html:button>
</TD>
</TR>

<TR VALIGN="MIDDLE">
<TD WIDTH="5%" CLASS="bluer"><B><fmt:message key="peiproject.label.projectname"/>:</B></TD>
<TD COLSPAN="3" WIDTH="5%" CLASS="bluel">
<c:out value="${status.current.projectName}"/>
</TD>

<TD WIDTH="5%" CLASS="bluer"><B><fmt:message key="peiproject.label.hassprojectmanager"/>:</B></TD>
<TD WIDTH="6%" COLSPAN="2" CLASS="bluel">
<INPUT TYPE="hidden" NAME="projectManagerId" VALUE="<c:out value="${projectManagerId}"/>">
<c:out value="${projectManager}"/>
</TD>
</TR>

<TR VALIGN="MIDDLE">
<TD WIDTH="5%" CLASS="whiter"><B><fmt:message key="peiproject.label.projectdesc"/>:</B></TD>
<TD COLSPAN="6" CLASS="whitel"><c:out value="${status.current.projectDesc}"/></TD>
</TR>

<TR VALIGN="MIDDLE">
<TD WIDTH="5%" CLASS="bluer"><B><fmt:message key="peiproject.label.keywords"/>:</B></TD>

<TD WIDTH="5%" COLSPAN="6" CLASS="bluel">
  <c:set var="keywordCount" value='${0}'/>
  <c:forEach var="vvPeiProjectKeywordBean" items="${vvPeiProjectKeywordCollection}" varStatus="statusKeyword">
  <c:set var="selectedKeywordId" value='${statusKeyword.current.keywordId}'/>
   <c:forEach var="peiProjectKeywordBean" items="${status.current.keywordsCollection}" varStatus="statusProjectKeyword">
    <c:set var="currentKeywordId" value='${statusProjectKeyword.current.keywordId}'/>
    <c:choose>
     <c:when test="${currentKeywordId == selectedKeywordId}">
      <c:if test="${keywordCount > 0}">
      ,
      </c:if>
      <c:out value="${statusKeyword.current.keywordDesc}"/>
      <c:set var="keywordCount" value='${keywordCount+1}'/>
     </c:when>
    </c:choose>
   </c:forEach>
  </c:forEach>
</TD>
</TR>

<TR VALIGN="MIDDLE">
<TD WIDTH="5%" CLASS="whiter"><B><fmt:message key="label.facility"/>:</B></TD>

<TD WIDTH="5%" COLSPAN="2" CLASS="whitel">
<c:set var="selectedFacilityId" value='${status.current.facilityId}'/>
  <c:forEach var="facilityBean" items="${vvProjectFacilityCollection}" varStatus="statusFacility">
  <c:set var="currentFacilityId" value='${statusFacility.current.facilityId}'/>

  <c:choose>
   <c:when test="${currentFacilityId == selectedFacilityId}">
    <c:out value="${statusFacility.current.facilityName}"/>
   </c:when>
  </c:choose>
  </c:forEach>
</TD>

<TD WIDTH="2%" CLASS="whiter"><B><fmt:message key="peiproject.label.clientcontact"/>:</B></TD>
<TD WIDTH="6%" CLASS="whitel">
<c:out value="${status.current.customerContactManager}"/>
</TD>

<TD WIDTH="5%" COLSPAN="2" CLASS="whitel">
<B><fmt:message key="peiproject.label.bestpractice"/>:</B>

<c:set var="showOnlyBestPractice" value='${status.current.bestPractice}'/>
<c:if test="${showOnlyBestPractice != null}" >
 <c:set var="checkBoxChecked" value='Yes'/>
</c:if>
<c:out value="${checkBoxChecked}"/>
</TD>
</TR>

<TR VALIGN="MIDDLE">
<TD WIDTH="5%" CLASS="bluer"><B><fmt:message key="label.status"/>:</B></TD>

<TD WIDTH="5%" CLASS="bluel">
<c:set var="selectedProjectStatus" value='${status.current.projectStatus}'/>
  <c:forEach var="vvPeiProjectStatusBean" items="${vvPeiProjectStatusCollection}" varStatus="statusProject">
  <c:set var="currentProjectStatus" value='${statusProject.current.projectStatus}'/>

  <c:choose>
   <c:when test="${currentProjectStatus == selectedProjectStatus}">
    <c:out value="${statusProject.current.projectStatusDesc}"/>
   </c:when>
  </c:choose>
  </c:forEach>
</TD>

<TD WIDTH="5%" CLASS="bluer"><B><fmt:message key="peiproject.label.startdate"/>:</B><BR><fmt:message key="label.dateformat"/></TD>
<TD WIDTH="5%" CLASS="bluel">
<fmt:formatDate var="formattedStartDate" value="${status.current.startDate}" pattern="MM/dd/yyyy"/>
<c:out value="${formattedStartDate}"/>
</TD>

<TD WIDTH="5%" CLASS="bluer"><B><fmt:message key="label.approveddate"/>:</B><BR><fmt:message key="label.dateformat"/></TD>
<TD WIDTH="5%" COLSPAN="2" CLASS="bluel">
<fmt:formatDate var="formattedApprovedDate" value="${status.current.approvedDate}" pattern="MM/dd/yyyy"/>
<c:out value="${formattedApprovedDate}"/>
</TD>
</TR>

<TR VALIGN="MIDDLE">
<TD WIDTH="5%" CLASS="whiter"><B><fmt:message key="label.category"/>:</B></TD>

<TD WIDTH="5%" CLASS="whitel">
<c:set var="selectedProjectCategory" value='${status.current.projectCategory}'/>
  <c:forEach var="vvProjectCategoryBean" items="${vvProjectCategoryCollection}" varStatus="statusCategory">
  <c:set var="currentProjectCategory" value='${statusCategory.current.projectCategory}'/>

  <c:choose>
   <c:when test="${currentProjectCategory == selectedProjectCategory}">
    <c:out value="${statusCategory.current.projectCategoryDesc}"/>
   </c:when>
  </c:choose>
  </c:forEach>
</TD>

<TD WIDTH="5%" CLASS="whiter"><B><fmt:message key="peiproject.label.projectedfinishdate"/>:</B><BR><fmt:message key="label.dateformat"/></TD>
<TD WIDTH="5%" CLASS="whitel">
<fmt:formatDate var="formattedProjectedFinistedDate" value="${status.current.projectedFinistedDate}" pattern="MM/dd/yyyy"/>
<c:out value="${formattedProjectedFinistedDate}"/>
</TD>

<TD WIDTH="5%" CLASS="whiter"><B><fmt:message key="peiproject.label.actualfinishdate"/>:</B><BR><fmt:message key="label.dateformat"/></TD>
<TD WIDTH="5%" COLSPAN="2" CLASS="whitel">
<fmt:formatDate var="formattedActualFinishDate" value="${status.current.actualFinishDate}" pattern="MM/dd/yyyy"/>
<c:out value="${formattedActualFinishDate}"/>
</TD>
</TR>

<TR VALIGN="MIDDLE">
<TD WIDTH="5%" CLASS="bluer"><B><fmt:message key="label.priority"/>:</B></TD>
<TD WIDTH="5%" CLASS="bluel">
<c:set var="selectedPriority" value='${status.current.priority}'/>
  <c:forEach var="vvProjectPriorityBean" items="${vvProjectPriorityCollection}" varStatus="statusPriority">
  <c:set var="currentPriority" value='${statusPriority.current.projectPriority}'/>

  <c:choose>
   <c:when test="${currentPriority == selectedPriority}">
    <c:out value="${statusPriority.current.projectPriorityDesc}"/>
   </c:when>
  </c:choose>
  </c:forEach>
</TD>

<TD WIDTH="5%" CLASS="bluer"><B><fmt:message key="peiproject.label.projectedcost"/>:</B></TD>
<TD WIDTH="2%" CLASS="bluel"><c:out value="${status.current.pojectedCost}"/></TD>

<TD WIDTH="5%" CLASS="bluer"><B><fmt:message key="peiproject.label.actualcost"/>:</B></TD>
<TD WIDTH="5%" COLSPAN="2" CLASS="bluel"><c:out value="${status.current.actualCost}"/></TD>
</TR>

<TR VALIGN="MIDDLE">
<TD WIDTH="5%" COLSPAN="7" CLASS="whitel"><B><fmt:message key="peiproject.label.costinformation"/>:
<TABLE ID="savingstable" BORDER="0" BGCOLOR="#a2a2a2" WIDTH="60%" CELLSPACING="1" CELLPADDING="3">
<c:set var="colorClass" value='blue'/>
<c:set var="savingsCount" value='${0}'/>
<c:forEach var="peiProjectSavingsBean" items="${status.current.savingsCollection}" varStatus="statusSavings">

<c:if test="${statusSavings.index == 0}">
<TR VALIGN="MIDDLE">
<TD width="5%" CLASS="whiter" height="38"><B><fmt:message key="label.currency"/>:</B></TD>
<TD CLASS="whitel" width="5%" COLSPAN="2">
<c:set var="selectedCurrencyId" value='${statusSavings.current.currencyId}'/>

<c:forEach var="vvCurrencyBean" items="${vvCurrencyCollection}" varStatus="statusCurrency">
<c:set var="currentCurrencyId" value='${statusCurrency.current.currencyId}'/>
<c:choose>
<c:when test="${selectedCurrencyId == currentCurrencyId}" >
<c:out value="${statusCurrency.current.currencyDescription}"/>
</c:when>
</c:choose>
</c:forEach>
</TD>
</TR>
</c:if>

<c:if test="${statusSavings.index % 10 == 0}">
<TR align="center" ID="savingsRowHeader">
<TH width="5%" CLASS="tableheader" height="38"><fmt:message key="label.period"/></TH>
<TH width="5%" CLASS="tableheader" height="38"><fmt:message key="peiproject.label.projectedsavings"/></TH>
<TH width="5%" CLASS="tableheader" height="38"><fmt:message key="peiproject.label.actualsavings"/></TH>
</TR>
</c:if>

<c:choose>
  <c:when test="${statusSavings.index % 2 == 0}" >
   <c:set var="colorClass" value='CLASS=blue'/>
  </c:when>
  <c:otherwise>
   <c:set var="colorClass" value='CLASS=white'/>
  </c:otherwise>
</c:choose>

<TR align="center" ID="savingsRowId<c:out value="${statusSavings.index}"/>">

<INPUT TYPE="hidden" name="peiProjectSavingsBean[<c:out value="${statusSavings.index}"/>].periodId" ID="periodId<c:out value="${statusSavings.index}"/>" value="<c:out value="${statusSavings.current.periodId}"/>">

<TD <c:out value="${colorClass}"/> width="5%"><c:out value="${statusSavings.current.periodName}"/></TD>
<TD <c:out value="${colorClass}"/> width="5%"><c:out value="${statusSavings.current.projectedPeriodSavings}"/></TD>
<TD <c:out value="${colorClass}"/> width="5%"><c:out value="${statusSavings.current.actualPeriodSavings}"/>"</TD>

<c:set var="savingsCount" value='${savingsCount+1}'/>
</c:forEach>

<c:if test="${savingsCount == 0}" >
<TR VALIGN="MIDDLE">
<TD width="5%" CLASS="whiter" height="38"><B><fmt:message key="label.currency"/>:</B></TD>
<TD CLASS="whitel" width="5%" COLSPAN="2">
 <c:set var="selectedCurrencyId" value='USD'/>
 <tcmis:isCNServer>
	<c:set var="selectedCurrencyId" value='CNY'/>
 </tcmis:isCNServer>  

<c:forEach var="vvCurrencyBean" items="${vvCurrencyCollection}" varStatus="statusCurrency">
<c:set var="currentCurrencyId" value='${statusCurrency.current.currencyId}'/>
<c:choose>
<c:when test="${selectedCurrencyId == currentCurrencyId}" >
 <c:out value="${statusCurrency.current.currencyDescription}"/>
</c:when>
</c:choose>
</c:forEach>
</TD>
</TR>

<TR align="center" ID="savingsRowHeader">
<TH width="2%" CLASS="tableheader" height="38"><fmt:message key="label.period"/></TH>
<TH width="5%" CLASS="tableheader" height="38"><fmt:message key="peiproject.label.projectedsavings"/></TH>
<TH width="5%" CLASS="tableheader" height="38"><fmt:message key="peiproject.label.actualsavings"/></TH>
</TR>
</c:if>

<TR align="center">
<TD width="2%" CLASS="black" height="38"><fmt:message key="label.total"/></TD>
<TD width="5%" CLASS="blackl" ID="totalProjectedPeriodSavings" height="38"><c:out value="${status.current.totalProjectedPeriodSavings}"/></TD>
<TD width="5%" CLASS="blackl" ID="totalActualPeriodSavings" height="38"><c:out value="${status.current.totalActualPeriodSavings}"/></TD>
</TR>
</TABLE>

<INPUT TYPE="hidden" NAME="savingsCount" ID="savingsCount" VALUE="<c:out value="${savingsCount}"/>">
</TD>
</TR>

<TR VALIGN="MIDDLE">
<TD WIDTH="5%" COLSPAN="7" CLASS="bluel"><B><fmt:message key="peiproject.label.associateddocuments"/>:
<TABLE ID="documentstable" BORDER="0" BGCOLOR="#a2a2a2" WIDTH="40%" CELLSPACING="1" CELLPADDING="3">
<c:set var="colorClass" value='white'/>
<c:set var="documentCount" value='${0}'/>

<c:forEach var="peiProjectDocumentBean" items="${status.current.documentsCollection}" varStatus="statusDocument">
  <c:if test="${statusDocument.index % 10 == 0}">
   <TR align="center">
   <TH width="5%" height="38"><fmt:message key="label.document"/></TH>
   </TR>
  </c:if>

  <c:choose>
   <c:when test="${statusDocument.index % 2 == 0}" >
    <c:set var="colorClass" value='blue'/>
   </c:when>
   <c:otherwise>
    <c:set var="colorClass" value='white'/>
   </c:otherwise>
  </c:choose>

  <INPUT TYPE="hidden" name="peiProjectDocumentBean[<c:out value="${statusDocument.index}"/>].url" ID="url<c:out value="${statusDocument.index}"/>" value="<c:out value="${statusDocument.current.url}"/>">
  <INPUT TYPE="hidden" name="peiProjectDocumentBean[<c:out value="${statusDocument.index}"/>].documentId" ID="documentId<c:out value="${statusDocument.index}"/>" value="<c:out value="${statusDocument.current.documentId}"/>">

  <TR align="center" CLASS="<c:out value="${colorClass}"/>" ID="documentRowId<c:out value="${statusDocument.index}"/>">
  <TD width="5%" ID="documentUrl<c:out value="${statusDocument.index}"/>">
   <A HREF="<c:out value="${statusDocument.current.url}"/>" TARGET="newDocument<c:out value="${statusDocument.index}"/>" STYLE="color:#0000ff;cursor:pointer;text-decoration:underline">
    <c:out value="${statusDocument.current.documentName}"/>
   </A>
  </TD>
  </TR>
<c:set var="documentCount" value='${documentCount+1}'/>
</c:forEach>

<c:if test="${documentCount == 0}" >
<TR align="center">
 <TH width="5%" height="38"><fmt:message key="label.document"/></TH>
</TR>
</c:if>
</TABLE>

<INPUT TYPE="hidden" NAME="documentCount" ID="documentCount" VALUE="<c:out value="${documentCount}"/>">
<INPUT TYPE="hidden" NAME="deletePermission" ID="deletePermission" VALUE="<c:out value="${deletePermission}"/>">
</TD>
</TR>

<TR VALIGN="MIDDLE">
<TD WIDTH="5%" CLASS="whiter"><B><fmt:message key="label.comments"/>:</B></TD>
<TD COLSPAN="6" CLASS="whitel"><c:out value="${status.current.comments}"/></TD>
</TR>
</c:forEach>
</TABLE>

<c:if test="${empty peiProjectViewBeanCollection}" >
<c:if test="${peiProjectViewBeanCollection != null}" >
<TABLE  BORDER="0" CELLSPACING=0 CELLPADDING=0 WIDTH="100%" CLASS="moveup">
<tr>
<TD HEIGHT="25" WIDTH="100%" VALIGN="MIDDLE" BGCOLOR="#a2a2a2">
<fmt:message key="main.nodatafound"/>
</TD>
</tr>
</TABLE>
</c:if>
</c:if>

</html:form>
</DIV>
</BODY>
</html:html>


