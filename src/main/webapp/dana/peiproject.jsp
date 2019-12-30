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

<c:set var="projectManagerId" value='${status.current.projectManagerId}'/>
<c:set var="projectManager" value='${status.current.projectManager}'/>

<c:if test="${empty projectManagerId}">
<c:set var="firstname" value='${sessionScope.personnelBean.firstName}'/>
<c:set var="lastname" value='${sessionScope.personnelBean.lastName}'/>
<c:set var="projectManager" value='${lastname}'/>
<c:set var="projectManagerId" value='${sessionScope.personnelBean.personnelId}'/>
</c:if>

<c:set var="adminPermission" value=''/>
<c:set var="updatePermission" value=''/>
<tcmis:facilityPermission indicator="true" userGroupId="ProjectMgmt">
 <c:set var="adminPermission" value='Yes'/>
 <c:set var="updatePermission" value='Yes'/>
</tcmis:facilityPermission>

<c:if test="${projectManagerId == sessionScope.personnelBean.personnelId}">
 <c:set var="updatePermission" value='Yes'/>
</c:if>

<INPUT TYPE="hidden" NAME="projectId" VALUE="<c:out value="${status.current.projectId}"/>">
<INPUT TYPE="hidden" NAME="companyId" VALUE="<c:out value="${status.current.companyId}"/>">
<INPUT TYPE="hidden" NAME="insertOrUpdate" VALUE="<c:out value="${status.current.insertOrUpdate}"/>">

<TR VALIGN="MIDDLE">
<TD WIDTH="5%" COLSPAN="7" CLASS="white">
<c:choose>
<c:when test="${updatePermission == 'Yes'}">
<html:submit property="submitSaveAsNew" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'" onclick="return saveAsNewProject()">
     <fmt:message key="peiproject.button.saveasnew"/>
</html:submit>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<html:submit property="submitUpdate" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'" onclick="return updateProject()">
     <fmt:message key="button.update"/>
</html:submit>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<html:submit property="submitNew" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'" onclick="return newProject()">
     <fmt:message key="peiproject.button.newproject"/>
</html:submit>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<%--<html:submit property="returnToList" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'" onclick="return returnToList()">
     <fmt:message key="peiproject.button.returntolist"/>
</html:submit>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>
</c:when>
<c:otherwise>
  &nbsp;
</c:otherwise>
</c:choose>
<html:button property="submitPrint" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'" onclick="printPdf()">
     <fmt:message key="label.print"/>
</html:button>
</TD>
</TR>

<TR VALIGN="MIDDLE">
<TD WIDTH="5%" CLASS="bluer"><B><fmt:message key="peiproject.label.projectname"/>:</B></TD>

<TD COLSPAN="3" WIDTH="5%" CLASS="bluel">
<INPUT CLASS="HEADER" TYPE="text" NAME="projectName" value="<c:out value="${status.current.projectName}"/>" size="50">
</TD>

<TD WIDTH="5%" CLASS="bluer"><B><fmt:message key="peiproject.label.hassprojectmanager"/>:</B></TD>

<TD WIDTH="6%" COLSPAN="2" CLASS="bluel">
<INPUT TYPE="hidden" NAME="projectManagerId" VALUE="<c:out value="${projectManagerId}"/>">

<c:set var="submitNew" value='${param.submitNew}'/>
<c:choose>
<c:when test="${!empty submitNew}">
<c:set var="firstname" value='${sessionScope.personnelBean.firstName}'/>
<c:set var="lastname" value='${sessionScope.personnelBean.lastName}'/>
<INPUT CLASS="INVISIBLEHEADBLUE" TYPE="text" NAME="projectManager" value="<c:out value="${lastname}"/>,<c:out value="${firstname}"/>" size="12" readonly>
</c:when>
<c:otherwise>
 <INPUT CLASS="INVISIBLEHEADBLUE" TYPE="text" NAME="projectManager" value="<c:out value="${projectManager}"/>" size="12" readonly>
</c:otherwise>
</c:choose>
<c:if test="${adminPermission == 'Yes'}">
 <INPUT CLASS="SUBMIT" onmouseover="className='SUBMITHOVER'" onMouseout="className='SUBMIT'" name="searchpersonnellike" value="..." OnClick="getProjectManager()" type="button">
</c:if>

<%--<select name="projectManagerId" CLASS="HEADER">
  <option value="0"><fmt:message key="label.pleaseselect"/></option>
  <c:forEach var="personnelBean" items="${vvProjectManagerCollection}" varStatus="statusPersonnel">
  <c:set var="currentProjectManager" value='${statusPersonnel.current.personnelId}'/>

  <c:choose>
   <c:when test="${currentProjectManager == selectedProjectManager}">
    <option value="<c:out value="${currentProjectManager}"/>" selected><c:out value="${statusPersonnel.current.firstName}"/>&nbsp;<c:out value="${statusPersonnel.current.lastName}"/></option>
   </c:when>
   <c:otherwise>
    <option value="<c:out value="${currentProjectManager}"/>"><c:out value="${statusPersonnel.current.firstName}"/>&nbsp;<c:out value="${statusPersonnel.current.lastName}"/></option>
   </c:otherwise>
  </c:choose>
  </c:forEach>
</select>
--%>
</TD>
</TR>

<TR VALIGN="MIDDLE">
<TD WIDTH="5%" CLASS="whiter"><B><fmt:message key="peiproject.label.projectdesc"/>:</B></TD>
<TD COLSPAN="6" CLASS="whitel"><TEXTAREA name="projectDesc" rows="5" cols="100"><c:out value="${status.current.projectDesc}"/></TEXTAREA></TD>
</TR>

<TR VALIGN="MIDDLE">
<TD WIDTH="5%" CLASS="bluer"><B><fmt:message key="peiproject.label.keywords"/>:</B></TD>

<TD WIDTH="5%" COLSPAN="2" CLASS="bluel"><B><fmt:message key="peiproject.label.basekeywords"/></B><BR>
<select name="basekeywords" size="4" multiple CLASS="HEADER">
 <c:forEach var="vvPeiProjectKeywordBean" items="${status.current.baseKeywordsCollection}" varStatus="statusKeyword">
 <c:set var="currentKeywordId" value='${statusKeyword.current.keywordId}'/>
  <option value="<c:out value="${currentKeywordId}"/>"><c:out value="${statusKeyword.current.keywordDesc}"/></option>
 </c:forEach>
</select>
</TD>

<TD WIDTH="2%" CLASS="blue">
<INPUT TYPE="button" ID="basekeywordsss" VALUE="===>" NAME="NewtskButton" onclick= "transferMultipleItems(this.form.basekeywords,this.form.keywordsCollectionSelect)" CLASS="SUBMIT" onmouseover="className='SUBMITHOVER'" onMouseout="className='SUBMIT'"><BR><BR><BR>
<INPUT TYPE="button" ID="basekeywordssss" VALUE="<===" NAME="NewtskButton" onclick= "transferMultipleItems(this.form.keywordsCollectionSelect,this.form.basekeywords)" CLASS="SUBMIT" onmouseover="className='SUBMITHOVER'" onMouseout="className='SUBMIT'">
</TD>

<TD WIDTH="5%" COLSPAN="3" CLASS="bluel"><B><fmt:message key="peiproject.label.selectedkeywords"/></B><BR>
<select name="keywordsCollectionSelect" size="4" multiple CLASS="HEADER">
  <c:forEach var="vvPeiProjectKeywordBean" items="${vvPeiProjectKeywordCollection}" varStatus="statusKeyword">
  <c:set var="selectedKeywordId" value='${statusKeyword.current.keywordId}'/>
   <c:forEach var="peiProjectKeywordBean" items="${status.current.keywordsCollection}" varStatus="statusProjectKeyword">
    <c:set var="currentKeywordId" value='${statusProjectKeyword.current.keywordId}'/>
    <c:choose>
     <c:when test="${currentKeywordId == selectedKeywordId}">
      <option value="<c:out value="${currentKeywordId}"/>"><c:out value="${statusKeyword.current.keywordDesc}"/></option>
     </c:when>
    </c:choose>
   </c:forEach>
  </c:forEach>
</select>
</TD>
</TR>

<TR VALIGN="MIDDLE">
<TD WIDTH="5%" CLASS="whiter"><B><fmt:message key="label.facility"/>:</B></TD>

<TD WIDTH="5%" COLSPAN="2" CLASS="whitel">
<c:set var="selectedFacilityId" value='${status.current.facilityId}'/>
<select name="facilityId" CLASS="HEADER">
  <option value=""><fmt:message key="label.pleaseselect"/></option>
  <c:forEach var="facilityBean" items="${vvProjectFacilityCollection}" varStatus="statusFacility">
  <c:set var="currentFacilityId" value='${statusFacility.current.facilityId}'/>

  <c:choose>
   <c:when test="${currentFacilityId == selectedFacilityId}">
    <option value="<c:out value="${currentFacilityId}"/>" selected><c:out value="${statusFacility.current.facilityName}"/></option>
   </c:when>
   <c:otherwise>
    <option value="<c:out value="${currentFacilityId}"/>"><c:out value="${statusFacility.current.facilityName}"/></option>
   </c:otherwise>
  </c:choose>
  </c:forEach>
</select>
</TD>

<TD WIDTH="2%" CLASS="whiter"><B><fmt:message key="peiproject.label.clientcontact"/>:</B></TD>
<TD WIDTH="6%" CLASS="whitel">
<INPUT TYPE="hidden" NAME="customerContactId" VALUE="<c:out value="${status.current.customerContactId}"/>">
<INPUT CLASS="INVISIBLEHEADWHITE" TYPE="text" NAME="customerContactManager" value="<c:out value="${status.current.customerContactManager}"/>" size="12" readonly>
<INPUT CLASS="SUBMIT" onmouseover="className='SUBMITHOVER'" onMouseout="className='SUBMIT'" name="searchpersonnellike" value="..." OnClick="getpersonnelID()" type="button">
</TD>

<TD WIDTH="5%" COLSPAN="2" CLASS="whitel">
<c:set var="showOnlyBestPractice" value='${status.current.bestPractice}'/>
<c:if test="${showOnlyBestPractice != null}" >
 <c:set var="checkBoxChecked" value='checked'/>
</c:if>
<INPUT TYPE="checkbox" name="bestPractice" ID="bestPractice" value="Y" <c:out value="${checkBoxChecked}"/>>
<B><fmt:message key="peiproject.label.bestpractice"/></B>
</TD>
</TR>

<TR VALIGN="MIDDLE">
<TD WIDTH="5%" CLASS="bluer"><B><fmt:message key="label.status"/>:</B></TD>

<TD WIDTH="5%" CLASS="bluel">
<c:set var="selectedProjectStatus" value='${status.current.projectStatus}'/>
<select name="projectStatus" CLASS="HEADER">
 <option value=""><fmt:message key="label.pleaseselect"/></option>
  <c:forEach var="vvPeiProjectStatusBean" items="${vvPeiProjectStatusCollection}" varStatus="statusProject">
  <c:set var="currentProjectStatus" value='${statusProject.current.projectStatus}'/>

  <c:choose>
   <c:when test="${currentProjectStatus == selectedProjectStatus}">
    <option value="<c:out value="${currentProjectStatus}"/>" selected><c:out value="${statusProject.current.projectStatusDesc}"/></option>
   </c:when>
   <c:otherwise>
    <option value="<c:out value="${currentProjectStatus}"/>"><c:out value="${statusProject.current.projectStatusDesc}"/></option>
   </c:otherwise>
  </c:choose>
  </c:forEach>
</select>
</TD>

<TD WIDTH="5%" CLASS="bluer"><B><fmt:message key="peiproject.label.proposeddate"/>:</B><BR><fmt:message key="label.dateformat"/></TD>
<TD WIDTH="5%" CLASS="bluel">
<fmt:formatDate var="formattedProposedDateToClient" value="${status.current.proposedDateToClient}" pattern="MM/dd/yyyy"/>
<INPUT TYPE="text" CLASS="HEADER" name="proposedDateToClient" ID="proposedDateToClient" value="<c:out value="${formattedProposedDateToClient}"/>" size="8" maxlength="10" Class="DETAILS"><FONT SIZE="4"><a href="javascript: void(0);" ID="linkproposedDateToClient" STYLE="color:#0000ff;cursor:pointer;text-decoration:underline" onClick="return getCalendar(document.peiProjectForm.proposedDateToClient);">&diams;</a></FONT>
</TD>

<TD WIDTH="5%" CLASS="bluer"><B><fmt:message key="label.approveddate"/>:</B><BR><fmt:message key="label.dateformat"/></TD>
<TD WIDTH="5%" COLSPAN="2" CLASS="bluel">
<fmt:formatDate var="formattedApprovedDate" value="${status.current.approvedDate}" pattern="MM/dd/yyyy"/>
<INPUT TYPE="text" CLASS="HEADER" name="approvedDate" ID="approvedDate" value="<c:out value="${formattedApprovedDate}"/>" size="8" maxlength="10" Class="DETAILS"><FONT SIZE="4"><a href="javascript: void(0);" ID="linkapprovedDate" STYLE="color:#0000ff;cursor:pointer;text-decoration:underline" onClick="return getCalendar(document.genericForm.approvedDate);">&diams;</a></FONT>
</TD>
</TR>

<TR VALIGN="MIDDLE">
<TD WIDTH="5%" CLASS="whiter"><B><fmt:message key="label.category"/>:</B></TD>

<TD WIDTH="5%" CLASS="whitel">
<c:set var="selectedProjectCategory" value='${status.current.projectCategory}'/>
<select name="projectCategory" CLASS="HEADER">
  <option value=""><fmt:message key="label.pleaseselect"/></option>
  <c:forEach var="vvProjectCategoryBean" items="${vvProjectCategoryCollection}" varStatus="statusCategory">
  <c:set var="currentProjectCategory" value='${statusCategory.current.projectCategory}'/>

  <c:choose>
   <c:when test="${currentProjectCategory == selectedProjectCategory}">
    <option value="<c:out value="${currentProjectCategory}"/>" selected><c:out value="${statusCategory.current.projectCategoryDesc}"/></option>
   </c:when>
   <c:otherwise>
    <option value="<c:out value="${currentProjectCategory}"/>"><c:out value="${statusCategory.current.projectCategoryDesc}"/></option>
   </c:otherwise>
  </c:choose>
  </c:forEach>
</select>
</TD>

<TD WIDTH="5%" CLASS="bluer"><B><fmt:message key="peiproject.label.startdate"/>:</B><BR><fmt:message key="label.dateformat"/></TD>
<TD WIDTH="5%" CLASS="bluel">
<fmt:formatDate var="formattedStartDate" value="${status.current.startDate}" pattern="MM/dd/yyyy"/>
<INPUT TYPE="text" CLASS="HEADER" name="startDate" ID="startDate" value="<c:out value="${formattedStartDate}"/>" size="8" maxlength="10" Class="DETAILS"><FONT SIZE="4"><a href="javascript: void(0);" ID="linkstartDate" STYLE="color:#0000ff;cursor:pointer;text-decoration:underline" onClick="return getCalendar(document.genericForm.startDate);">&diams;</a></FONT>
</TD>

<TD WIDTH="5%" CLASS="whiter"><B><fmt:message key="peiproject.label.gainshareduration"/>:</B></TD>
<TD WIDTH="2%" CLASS="whitel"><INPUT CLASS="HEADER" TYPE="text" NAME="gainShareDuration" value="<c:out value="${status.current.gainShareDuration}"/>" size="15" maxlength="90"></TD>

</TR>

<TR VALIGN="MIDDLE">
<TD WIDTH="5%" CLASS="bluer"><B><fmt:message key="label.priority"/>:</B></TD>
<TD WIDTH="5%" CLASS="bluel">
<c:set var="selectedPriority" value='${status.current.priority}'/>
<select name="priority" CLASS="HEADER">
  <option value=""><fmt:message key="label.pleaseselect"/></option>
  <c:forEach var="vvProjectPriorityBean" items="${vvProjectPriorityCollection}" varStatus="statusPriority">
  <c:set var="currentPriority" value='${statusPriority.current.projectPriority}'/>

  <c:choose>
   <c:when test="${currentPriority == selectedPriority}">
    <option value="<c:out value="${currentPriority}"/>" selected><c:out value="${statusPriority.current.projectPriorityDesc}"/></option>
   </c:when>
   <c:otherwise>
    <option value="<c:out value="${currentPriority}"/>"><c:out value="${statusPriority.current.projectPriorityDesc}"/></option>
   </c:otherwise>
  </c:choose>
  </c:forEach>
</select>
</TD>

<TD WIDTH="5%" CLASS="bluer"><B><fmt:message key="peiproject.label.projectedcost"/>:</B></TD>
<TD WIDTH="2%" CLASS="bluel"><INPUT CLASS="HEADER" TYPE="text" NAME="pojectedCost" value="<c:out value="${status.current.pojectedCost}"/>" onchange="checkForNumber('pojectedCost')" size="7"></TD>

<TD WIDTH="5%" CLASS="bluer"><B><fmt:message key="peiproject.label.actualcost"/>:</B></TD>
<TD WIDTH="5%" COLSPAN="2" CLASS="bluel"><INPUT CLASS="HEADER" TYPE="text" NAME="actualCost" value="<c:out value="${status.current.actualCost}"/>" onchange="checkForNumber('actualCost')" size="7"></TD>
</TR>

<TR VALIGN="MIDDLE">
<TD WIDTH="5%" CLASS="whiter">&nbsp;</TD>
<TD WIDTH="5%" CLASS="whitel">&nbsp;</TD>

<TD WIDTH="5%" CLASS="whiter"><B><fmt:message key="peiproject.label.projectedfinishdate"/>:</B><BR><fmt:message key="label.dateformat"/></TD>
<TD WIDTH="5%" CLASS="whitel">
<fmt:formatDate var="formattedProjectedFinistedDate" value="${status.current.projectedFinistedDate}" pattern="MM/dd/yyyy"/>
<INPUT TYPE="text" CLASS="HEADER" name="projectedFinistedDate" ID="projectedFinistedDate" value="<c:out value="${formattedProjectedFinistedDate}"/>" size="8" maxlength="10" Class="DETAILS"><FONT SIZE="4"><a href="javascript: void(0);" ID="linkprojectedFinistedDate" STYLE="color:#0000ff;cursor:pointer;text-decoration:underline" onClick="return getCalendar(document.peiProjectForm.projectedFinistedDate);">&diams;</a></FONT>
</TD>


<TD WIDTH="5%" CLASS="whiter"><B><fmt:message key="peiproject.label.actualfinishdate"/>:</B><BR><fmt:message key="label.dateformat"/></TD>
<TD WIDTH="5%" COLSPAN="2" CLASS="whitel">
<fmt:formatDate var="formattedActualFinishDate" value="${status.current.actualFinishDate}" pattern="MM/dd/yyyy"/>
<INPUT TYPE="text" CLASS="HEADER" name="actualFinishDate" ID="actualFinishDate" value="<c:out value="${formattedActualFinishDate}"/>" size="8" maxlength="10" Class="DETAILS"><FONT SIZE="4"><a href="javascript: void(0);" ID="linkactualFinishDate" STYLE="color:#0000ff;cursor:pointer;text-decoration:underline" onClick="return getCalendar(document.peiProjectForm.actualFinishDate);">&diams;</a></FONT>
</TD>
</TR>


<TR VALIGN="MIDDLE">
<TD WIDTH="5%" COLSPAN="7" CLASS="whitel"><B><fmt:message key="peiproject.label.costinformation"/>:
<c:if test="${updatePermission == 'Yes'}">
<html:button property="submitAddSavings" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'" onclick= "addSavingsLine()">
     <fmt:message key="label.add"/>
</html:button>
</c:if>

<TABLE ID="savingstable" BORDER="0" BGCOLOR="#a2a2a2" WIDTH="60%" CELLSPACING="1" CELLPADDING="3">
<c:set var="colorClass" value='blue'/>
<c:set var="savingsCount" value='${0}'/>
<c:forEach var="peiProjectSavingsBean" items="${status.current.savingsCollection}" varStatus="statusSavings">

<c:if test="${statusSavings.index == 0}">
<TR VALIGN="MIDDLE">
<TD width="5%" CLASS="whiter" height="38"><B><fmt:message key="label.currency"/>:</B></TD>
<TD CLASS="whitel" width="5%" COLSPAN="2">
<c:set var="selectedCurrencyId" value='${statusSavings.current.currencyId}'/>
<c:if test="${empty selectedCurrencyId || selectedCurrencyId == ''}" >
 <c:set var="selectedCurrencyId" value='USD'/>
 <tcmis:isCNServer>
	<c:set var="selectedCurrencyId" value='CNY'/>
 </tcmis:isCNServer> 
</c:if>

<SELECT name="currencyId" ID="currencyId" CLASS="DETAILS" onChange= "showCurrencyChangeMessage()">
<c:forEach var="vvCurrencyBean" items="${vvCurrencyCollection}" varStatus="statusCurrency">
<c:set var="currentCurrencyId" value='${statusCurrency.current.currencyId}'/>
<c:choose>
<c:when test="${selectedCurrencyId == currentCurrencyId}" >
 <OPTION value="<c:out value="${currentCurrencyId}"/>" selected><c:out value="${statusCurrency.current.currencyDescription}"/></option>
</c:when>
<c:otherwise>
 <OPTION value="<c:out value="${currentCurrencyId}"/>"><c:out value="${statusCurrency.current.currencyDescription}"/></option>
</c:otherwise>
</c:choose>
</c:forEach>
</SELECT>
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

<TD <c:out value="${colorClass}"/> width="5%"><INPUT TYPE="text" name="peiProjectSavingsBean[<c:out value="${statusSavings.index}"/>].periodName" ID="periodName<c:out value="${statusSavings.index}"/>" value="<c:out value="${statusSavings.current.periodName}"/>" size="6" maxlength="30" Class="DETAILS"></TD>
<TD <c:out value="${colorClass}"/> width="5%"><INPUT TYPE="text" name="peiProjectSavingsBean[<c:out value="${statusSavings.index}"/>].projectedPeriodSavings" ID="projectedPeriodSavings<c:out value="${statusSavings.index}"/>" value="<c:out value="${statusSavings.current.projectedPeriodSavings}"/>" onchange="updateProjectedSavingsTotals(<c:out value="${statusSavings.index}"/>)" size="6" maxlength="30" Class="DETAILS"></TD>
<TD <c:out value="${colorClass}"/> width="5%"><INPUT TYPE="text" name="peiProjectSavingsBean[<c:out value="${statusSavings.index}"/>].actualPeriodSavings" ID="actualPeriodSavings<c:out value="${statusSavings.index}"/>" value="<c:out value="${statusSavings.current.actualPeriodSavings}"/>" onchange="updateActualSavingsTotals(<c:out value="${statusSavings.index}"/>)" size="6" maxlength="30" Class="DETAILS"></TD>


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

<SELECT name="currencyId" ID="currencyId" CLASS="DETAILS" onChange= "showCurrencyChangeMessage()">
<c:forEach var="vvCurrencyBean" items="${vvCurrencyCollection}" varStatus="statusCurrency">
<c:set var="currentCurrencyId" value='${statusCurrency.current.currencyId}'/>
<c:choose>
<c:when test="${selectedCurrencyId == currentCurrencyId}" >
 <OPTION value="<c:out value="${currentCurrencyId}"/>" selected><c:out value="${statusCurrency.current.currencyDescription}"/></option>
</c:when>
<c:otherwise>
 <OPTION value="<c:out value="${currentCurrencyId}"/>"><c:out value="${statusCurrency.current.currencyDescription}"/></option>
</c:otherwise>
</c:choose>
</c:forEach>
</SELECT>
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
<c:if test="${updatePermission == 'Yes'}">
<html:button property="submitAddDocument" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'" onclick="addProjectDocument()">
     <fmt:message key="label.add"/>
</html:button>
</c:if>

<c:set var="personnelId" value='${sessionScope.personnelBean.personnelId}'/>
<c:set var="deletePermission" value=''/>
<tcmis:facilityPermission indicator="true" userGroupId="ProjectMgmt">
 <c:set var="deletePermission" value='Yes'/>
</tcmis:facilityPermission>

<c:if test="${personnelId == projectManagerId}">
 <c:set var="deletePermission" value='Yes'/>
</c:if>

<TABLE ID="documentstable" BORDER="0" BGCOLOR="#a2a2a2" WIDTH="40%" CELLSPACING="1" CELLPADDING="3">
<c:set var="colorClass" value='white'/>
<c:set var="documentCount" value='${0}'/>

<c:forEach var="peiProjectDocumentBean" items="${status.current.documentsCollection}" varStatus="statusDocument">
  <c:if test="${statusDocument.index % 10 == 0}">
   <TR align="center">
   <TH width="5%" height="38"><fmt:message key="label.document"/></TH>
   <TH width="2%" height="38"><fmt:message key="label.delete"/></TH>
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
  <TD width="5%" ID="documentDelete<c:out value="${statusDocument.index}"/>">
  <c:choose>
   <c:when test="${deletePermission == 'Yes'}" >
    <INPUT TYPE="checkbox" name="peiProjectDocumentBean[<c:out value="${statusDocument.index}"/>].delete" ID="delete<c:out value="${statusDocument.index}"/>" value="Y">
   </c:when>
   <c:otherwise>
    &nbsp;
   </c:otherwise>
  </c:choose>
  </TD>
  </TR>
<c:set var="documentCount" value='${documentCount+1}'/>
</c:forEach>

<c:if test="${documentCount == 0}" >
<TR align="center">
 <TH width="5%" height="38"><fmt:message key="label.document"/></TH>
 <TH width="2%" height="38"><fmt:message key="label.delete"/></TH>
</TR>
</c:if>
</TABLE>

<INPUT TYPE="hidden" NAME="documentCount" ID="documentCount" VALUE="<c:out value="${documentCount}"/>">
<INPUT TYPE="hidden" NAME="deletePermission" ID="deletePermission" VALUE="<c:out value="${deletePermission}"/>">
</TD>
</TR>

<TR VALIGN="MIDDLE">
<TD WIDTH="5%" CLASS="whiter"><B><fmt:message key="label.comments"/>:</B></TD>
<TD COLSPAN="6" CLASS="whitel"><TEXTAREA name="comments" rows="5" cols="100"><c:out value="${status.current.comments}"/></TEXTAREA></TD>
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


