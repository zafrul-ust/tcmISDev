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
<tcmis:fontSizeCss />
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

<!-- For Calendar support -->

<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>


<!-- Add any other javascript you need for the page here -->
<script src="/js/common/peiprojects.js" language="JavaScript"></script>
<script src="/js/common/projectdocument.js" language="JavaScript"></script>
<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<!-- This is for the YUI, uncomment if you will use this -->
<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>


<title>
<fmt:message key="peiproject.label.project"/>
</title>
</head>

<c:set var="submitPrint" value='${param.submitPrint}'/>
<c:choose>
  <c:when test="${!empty submitPrint && !empty submitPrint}" >
    <body onLoad="showPdfPrint()" bgcolor="#ffffff">
  </c:when>
  <c:otherwise>
   <body bgcolor="#ffffff">
  </c:otherwise>
</c:choose>



 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
 </div>
 <div class="interface" id="mainPage">


<!-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure -->
<div class="topNavigation" id="topNavigation">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td width="200">
<img src="/images/tcmtitlegif.gif" align="left">
</td>
<td>
<img src="/images/tcmistcmis32.gif" align="right">
</td>
</tr>
</table>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td width="70%" class="headingl">
<fmt:message key="peiproject.label.project"/>
</td>
<td width="30%" class="headingr">
<html:link style="color:#FFFFFF" forward="home">
 <fmt:message key="label.home"/>
</html:link>
</td>
</tr>
</table>
</div>

<div class="contentArea">




<html:form action="/peiproject.do" onsubmit="return submitOnlyOnce();">

<c:set var="colorClass" value=''/>
<c:set var="rowCount" value='${0}'/>

<c:if test="${!empty errorMessage}">
 <c:out value="${errorMessage}"/>
</c:if>
<html:errors/>
<!-- Search Option Begins -->
<table id="searchMaskTable" width="800" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">

<c:forEach var="peiProjectViewBean" items="${peiProjectViewBeanCollection}" varStatus="status">

<input type="hidden" name="projectId" id="projectId" class="selectbox" value="<c:out value="${status.current.projectId}"/>">

<tr class="">

 <td width="5%" class="optionTitleBoldRight">
  <fmt:message key="label.company"/>:
 </td>
 <td width="10%" class="optionTitleBoldLeft">
 <c:set var="selectedCompanyId" value='${status.current.companyId}'/>
 <c:set var="selectedCompanyIdName" value=''/>
 <c:forEach var="facilitiesForCompanyObjBean" items="${facilitiesForCompanyObjBeanCollection}" varStatus="companyStatus">
 <c:set var="currentCompanyId" value='${companyStatus.current.companyId}'/>

  <c:choose>
   <c:when test="${empty selectedCompanyId}" >
    <c:set var="selectedCompanyId" value='${companyStatus.current.companyId}'/>
    <c:set var="selectedCompanyIdName" value="${companyStatus.current.companyName}"/>
    <c:set var="facilityBeanCollection" value='${companyStatus.current.facilities}'/>
   </c:when>
   <c:when test="${currentCompanyId == selectedCompanyId}" >
    <c:set var="facilityBeanCollection" value='${companyStatus.current.facilities}'/>
   </c:when>
  </c:choose>

  <c:choose>
   <c:when test="${currentCompanyId == selectedCompanyId}">
    <c:out value="${companyStatus.current.companyName}"/>
    <c:set var="selectedCompanyIdName" value="${companyStatus.current.companyName}"/>
   </c:when>
  </c:choose>
  </c:forEach>
 </td>
<td width="5%" class="optionTitleBoldCenter" colspan="5">

<html:button property="submitPrint" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="printPdf()">
     <fmt:message key="label.print"/>
</html:button>
</td>
</tr>

<tr class="alt">
<td width="5%" class="bluer"><fmt:message key="peiproject.label.projectname"/>:</td>

<td colspan="3" width="5%" class="bluel">
<c:out value="${status.current.projectName}"/>
</td>

<td width="5%" class="bluer"><fmt:message key="peiproject.label.hassprojectmanager"/>:</td>

<td width="6%" colspan="2" class="bluel">
<c:out value="${projectManager}"/>
</td>
</tr>

<tr class="">
<td width="5%" class="whiter"><fmt:message key="peiproject.label.projectdesc"/>:</td>
<td colspan="6" class="whitel"><c:out value="${status.current.projectDesc}"/></td>
</tr>

<tr class="alt">
<td width="5%" class="bluer"><fmt:message key="peiproject.label.keywords"/>:</td>

<td width="5%" colspan="6" class="bluel">
 <c:set var="keywordCount" value='${0}'/>
  <c:forEach var="vvPeiProjectKeywordBean" items="${vvPeiProjectKeywordCollection}" varStatus="statusKeyword">
  <c:set var="selectedKeywordId" value='${statusKeyword.current.keywordId}'/>
   <c:forEach var="peiProjectKeywordBean" items="${status.current.keywordsCollection}" varStatus="statusProjectKeyword">
    <c:set var="currentKeywordId" value='${statusProjectKeyword.current.keywordId}'/>
    <c:choose>
     <c:when test="${currentKeywordId == selectedKeywordId}">
      <c:if test="${keywordCount > 0}">

      </c:if>
      <c:out value="${statusKeyword.current.keywordDesc}"/>
      <c:set var="keywordCount" value='${keywordCount+1}'/>
     </c:when>
    </c:choose>
   </c:forEach>
  </c:forEach>
</td>
</tr>

<tr class="">
<td width="5%" height="35" ALIGN="RIGHT" class="whiter">
<fmt:message key="label.facility"/>:
</td>

<td class="whitel" width="5%" colspan="2" ALIGN="LEFT">
<c:set var="selectedFacilityId" value='${status.current.facilityId}'/>
  <c:forEach var="facilityObjBean" items="${facilityBeanCollection}" varStatus="statusFacility">
  <c:set var="currentFacilityId" value='${statusFacility.current.facilityId}'/>

  <c:choose>
   <c:when test="${currentFacilityId == selectedFacilityId}">
    <c:out value="${statusFacility.current.facilityName}"/>
   </c:when>
  </c:choose>
  </c:forEach>
</select>
</td>

<td width="2%" class="whiter"><fmt:message key="peiproject.label.clientcontact"/>:</td>
<td width="6%" class="whitel">
<c:out value="${status.current.customerContactManager}"/>
</td>

<td width="5%" colspan="2" class="whitel">
<fmt:message key="peiproject.label.bestpractice"/>:

<c:set var="showOnlyBestPractice" value='${status.current.bestPractice}'/>
<c:if test="${showOnlyBestPractice != null}" >
 <c:set var="checkBoxChecked" value='Yes'/>
</c:if>
<c:out value="${checkBoxChecked}"/>
</td>
</tr>

<tr class="alt">
<td width="5%" class="bluer"><fmt:message key="label.status"/>:</td>

<td width="5%" class="bluel">
<c:set var="selectedProjectStatus" value='${status.current.projectStatus}'/>
  <c:forEach var="vvPeiProjectStatusBean" items="${vvPeiProjectStatusCollection}" varStatus="statusProject">
  <c:set var="currentProjectStatus" value='${statusProject.current.projectStatus}'/>

  <c:choose>
   <c:when test="${currentProjectStatus == selectedProjectStatus}">
    <c:out value="${statusProject.current.projectStatusDesc}"/>
   </c:when>
  </c:choose>
  </c:forEach>
</td>

<td width="5%" class="bluer"><fmt:message key="peiproject.label.startdate"/>:<BR><fmt:message key="label.dateformat"/></td>
<td width="5%" class="bluel">
<fmt:formatDate var="formattedStartDate" value="${status.current.startDate}" pattern="MM/dd/yyyy"/>
<c:out value="${formattedStartDate}"/>
</td>

<td width="5%" class="bluer"><fmt:message key="peiproject.label.approveddate"/>:<BR><fmt:message key="label.dateformat"/></td>
<td width="5%" colspan="2" class="bluel">
<fmt:formatDate var="formattedApprovedDate" value="${status.current.approvedDate}" pattern="MM/dd/yyyy"/>
<c:out value="${formattedApprovedDate}"/>
</td>
</tr>

<tr class="">
<td width="5%" class="whiter"><fmt:message key="label.category"/>:</td>

<td width="5%" class="whitel">
<c:set var="selectedProjectCategory" value='${status.current.projectCategory}'/>
  <c:forEach var="vvProjectCategoryBean" items="${vvProjectCategoryCollection}" varStatus="statusCategory">
  <c:set var="currentProjectCategory" value='${statusCategory.current.projectCategory}'/>

  <c:choose>
   <c:when test="${currentProjectCategory == selectedProjectCategory}">
    <c:out value="${statusCategory.current.projectCategoryDesc}"/>
   </c:when>
  </c:choose>
  </c:forEach>
</td>

<td width="5%" class="whiter"><fmt:message key="peiproject.label.projectedfinishdate"/>:<BR><fmt:message key="label.dateformat"/></td>
<td width="5%" class="whitel">
<fmt:formatDate var="formattedProjectedFinistedDate" value="${status.current.projectedFinistedDate}" pattern="MM/dd/yyyy"/>
<c:out value="${formattedProjectedFinistedDate}"/>
</td>

<td width="5%" class="whiter"><fmt:message key="peiproject.label.actualfinishdate"/>:<BR><fmt:message key="label.dateformat"/></td>
<td width="5%" colspan="2" class="whitel">
<fmt:formatDate var="formattedActualFinishDate" value="${status.current.actualFinishDate}" pattern="MM/dd/yyyy"/>
<c:out value="${formattedActualFinishDate}"/>
</td>
</tr>

<tr class="alt">
<td width="5%" class="bluer"><fmt:message key="label.priority"/>:</td>
<td width="5%" class="bluel">
<c:set var="selectedPriority" value='${status.current.priority}'/>
  <c:forEach var="vvProjectPriorityBean" items="${vvProjectPriorityCollection}" varStatus="statusPriority">
  <c:set var="currentPriority" value='${statusPriority.current.projectPriority}'/>

  <c:choose>
   <c:when test="${currentPriority == selectedPriority}">
    <c:out value="${statusPriority.current.projectPriorityDesc}"/>
   </c:when>
  </c:choose>
  </c:forEach>
</select>
</td>

<td width="5%" class="bluer"><fmt:message key="peiproject.label.projectedcost"/>:</td>
<td width="2%" class="bluel"><c:out value="${status.current.pojectedCost}"/></td>

<td width="5%" class="bluer"><fmt:message key="peiproject.label.actualcost"/>:</td>
<td width="5%" colspan="2" class="bluel"><c:out value="${status.current.actualCost}"/></td>
</tr>

<tr>
<td width="5%" colspan="7" class="whitel"><fmt:message key="peiproject.label.costinformation"/>:

<table id="savingstable" border="0" BGCOLOR="#a2a2a2" width="60%" cellspacing="1" cellpadding="3">
<c:set var="colorClass" value='blue'/>
<c:set var="savingsCount" value='${0}'/>
<c:forEach var="peiProjectSavingsBean" items="${status.current.savingsCollection}" varStatus="statusSavings">

<c:if test="${statusSavings.index == 0}">
<tr>
<td width="5%" class="whiter" height="38"><fmt:message key="label.currency"/>:</td>
<td class="whitel" width="5%" colspan="2">
<c:set var="selectedCurrencyId" value='${statusSavings.current.currencyId}'/>

<c:forEach var="vvCurrencyBean" items="${vvCurrencyCollection}" varStatus="statusCurrency">
<c:set var="currentCurrencyId" value='${statusCurrency.current.currencyId}'/>
<c:choose>
<c:when test="${selectedCurrencyId == currentCurrencyId}" >
 <c:out value="${statusCurrency.current.currencyDescription}"/>
</c:when>
</c:choose>
</c:forEach>
</SELECT>
</td>
</tr>
</c:if>

<c:if test="${statusSavings.index % 10 == 0}">
<tr align="center" id="savingsRowHeader">
<th width="5%" class="tableheader" height="38"><fmt:message key="label.period"/></th>
<th width="5%" class="tableheader" height="38"><fmt:message key="peiproject.label.projectedsavings"/></th>
<th width="5%" class="tableheader" height="38"><fmt:message key="peiproject.label.actualsavings"/></th>
</tr>
</c:if>

<c:choose>
  <c:when test="${statusSavings.index % 2 == 0}" >
   <c:set var="colorClass" value='class=blue'/>
  </c:when>
  <c:otherwise>
   <c:set var="colorClass" value='class=white'/>
  </c:otherwise>
</c:choose>

<tr align="center" id="savingsRowId<c:out value="${statusSavings.index}"/>">

<input type="hidden" name="peiProjectSavingsBean[<c:out value="${statusSavings.index}"/>].periodId" id="periodId<c:out value="${statusSavings.index}"/>" value="<c:out value="${statusSavings.current.periodId}"/>">

<td <c:out value="${colorClass}"/> width="5%"><c:out value="${statusSavings.current.periodName}"/></td>
<td <c:out value="${colorClass}"/> width="5%"><c:out value="${statusSavings.current.projectedPeriodSavings}"/></td>
<td <c:out value="${colorClass}"/> width="5%"><c:out value="${statusSavings.current.actualPeriodSavings}"/></td>

<c:set var="savingsCount" value='${savingsCount+1}'/>
</c:forEach>

<c:if test="${savingsCount == 0}" >
<tr>
<td width="5%" class="whiter" height="38"><fmt:message key="label.currency"/>:</td>
<td class="whitel" width="5%" colspan="2">
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
</SELECT>
</td>
</tr>

<tr align="center" id="savingsRowHeader">
<th width="2%" class="tableheader" height="38"><fmt:message key="label.period"/></th>
<th width="5%" class="tableheader" height="38"><fmt:message key="peiproject.label.projectedsavings"/></th>
<th width="5%" class="tableheader" height="38"><fmt:message key="peiproject.label.actualsavings"/></th>
</tr>
</c:if>

<tr>
<td width="2%" class="black" height="38"><fmt:message key="label.total"/></td>
<td width="5%" class="blackl" id="totalProjectedPeriodSavings" height="38"><c:out value="${status.current.totalProjectedPeriodSavings}"/></td>
<td width="5%" class="blackl" id="totalActualPeriodSavings" height="38"><c:out value="${status.current.totalActualPeriodSavings}"/></td>
</tr>
</table>

<input type="hidden" name="savingsCount" id="savingsCount" value="<c:out value="${savingsCount}"/>">
</td>
</tr>

<tr class="alt">
<td width="5%" colspan="7" class="bluel"><fmt:message key="peiproject.label.associateddocuments"/>:

<table id="documentstable" border="0" width="40%" cellspacing="1" cellpadding="3">
<c:set var="colorClass" value='white'/>
<c:set var="documentCount" value='${0}'/>

<c:forEach var="peiProjectDocumentBean" items="${status.current.documentsCollection}" varStatus="statusDocument">
  <c:if test="${statusDocument.index % 10 == 0}">
   <tr align="center">
   <th width="5%" height="38"><fmt:message key="label.document"/></th>
   </tr>
  </c:if>

  <c:choose>
   <c:when test="${statusDocument.index % 2 == 0}" >
    <c:set var="colorClass" value='alt'/>
   </c:when>
   <c:otherwise>
    <c:set var="colorClass" value=''/>
   </c:otherwise>
  </c:choose>

  <input type="hidden" name="peiProjectDocumentBean[<c:out value="${statusDocument.index}"/>].url" id="url<c:out value="${statusDocument.index}"/>" value="<c:out value="${statusDocument.current.url}"/>">
  <input type="hidden" name="peiProjectDocumentBean[<c:out value="${statusDocument.index}"/>].documentId" id="documentId<c:out value="${statusDocument.index}"/>" value="<c:out value="${statusDocument.current.documentId}"/>">

  <tr class="<c:out value="${colorClass}"/>" id="documentRowId<c:out value="${statusDocument.index}"/>">
  <td width="5%" id="documentUrl<c:out value="${statusDocument.index}"/>">
   <a href="<c:out value="${statusDocument.current.url}"/>" target="newDocument<c:out value="${statusDocument.index}"/>" style="color:#0000ff;cursor:pointer;text-decoration:underline">
    <c:out value="${statusDocument.current.documentName}"/>
   </a>
  </td>
  </tr>
<c:set var="documentCount" value='${documentCount+1}'/>
</c:forEach>

<c:if test="${documentCount == 0}" >
<tr align="center">
 <th width="5%" height="38"><fmt:message key="label.document"/></th>
</tr>
</c:if>
</table>

<input type="hidden" name="documentCount" id="documentCount" value="<c:out value="${documentCount}"/>">
<input type="hidden" name="deletePermission" id="deletePermission" value="<c:out value="${deletePermission}"/>">
</td>
</tr>

<tr>
<td width="5%" class="whiter"><fmt:message key="label.comments"/>:</td>
<td colspan="6" class="whitel"><c:out value="${status.current.comments}"/></td>
</tr>
</c:forEach>
</table>

<c:if test="${empty peiProjectViewBeanCollection}" >
<c:if test="${peiProjectViewBeanCollection != null}" >
<table border="0" cellspacing=0 cellpadding=0 width="100%" class="moveup">
<tr>
<td width="100%">
<fmt:message key="main.nodatafound"/>
</td>
</tr>
</table>
</c:if>
</c:if>

</div>
  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</div>
</td></tr>
</table>
<!-- Search results end -->



<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;"></div>
<!-- Hidden elements end -->



</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->

</html:form>
</div>
</body>
</html:html>


