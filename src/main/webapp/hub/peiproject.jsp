<!DOCtype html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
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

<%@ include file="/common/locale.jsp" %>
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

<script type="text/javascript" src="/js/common/projectdocument.js"></script>
<script type="text/javascript" src="/js/common/peiprojects.js"></script>

<!-- Add any other javascript you need for the page here -->
<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
all:"<fmt:message key="label.all"/>",savingsMatch:"<fmt:message key="msg.savingsMatch"/>",
selectNumber:"<fmt:message key="msg.selectNumber"/>",
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


<script language="JavaScript" type="text/javascript">
<!--
var companyIdArray = new Array(
<c:forEach var="facilitiesForCompanyObjBean" items="${facilitiesForCompanyObjBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"<c:out value="${status.current.companyId}"/>"
   </c:when>
   <c:otherwise>
    "All","<c:out value="${status.current.companyId}"/>"
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var facilityIdArray = new Array();
var facilityNameArray = new Array();
<c:forEach var="facilitiesForCompanyObjBean" items="${facilitiesForCompanyObjBeanCollection}" varStatus="status">
<c:set var="currentCompany" value='${status.current.companyId}'/>
<c:set var="facilityBeanCollection" value='${status.current.facilities}'/>

facilityIdArray["<c:out value="${currentCompany}"/>"] = new Array(""
 <c:forEach var="facilityObjBean" items="${facilityBeanCollection}" varStatus="status1">
,"<c:out value="${status1.current.facilityId}"/>"
  </c:forEach>

  );

facilityNameArray["<c:out value="${currentCompany}"/>"] = new Array(messagesData.all
 <c:forEach var="facilityObjBean" items="${facilityBeanCollection}" varStatus="status1">
,"<c:out value="${status1.current.facilityName}"/>"
  </c:forEach>

  );
 </c:forEach>
// -->
</script>

</head>

<c:set var="submitPrint" value='${param.submitPrint}'/>
<c:choose>
  <c:when test="${!empty submitPrint && !empty submitPrint}" >
    <body onLoad="showPdfPrint()">
  </c:when>
  <c:otherwise>
   <body>
  </c:otherwise>
</c:choose>

<c:if test="org.apache.struts.action.MESSAGE == null">
  <div class="errorMessages">
    <fmt:message key="error.resourcesnotloaded"/>
  </div>
</c:if>

<div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
 </div>
 <div class="interface" id="mainPage">

<div class="topNavigation" id="topNavigation">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td width="200">
<img src="/images/tcmtitlegif.gif" border=0 align="left">
</td>
<td>
<img src="/images/addeditprojects.gif" border=0 align="right">
</td>
</tr>
</table>


<table class="tableSearch" width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="70%" class="headingl">
      <fmt:message key="peiproject.label.project"/>
    </td>
    <td width="30%" class="headingr">
      <html:link style="color:#ffffff" forward="home">
        <fmt:message key="label.home"/>
      </html:link>
    </td>
  </tr>
</table>
</div>

<tcmis:form action="/peiproject.do" onsubmit="return submitOnlyOnce();">

<c:set var="colorClass" value=''/>
<c:set var="rowCount" value='${0}'/>

<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages">
<c:if test="${!empty errorMessage}">
 <c:out value="${errorMessage}"/>
</c:if>
<html:errors/>
</div>
<!-- Error Messages Ends -->

<div class="contentArea">
<!-- Search Option Begins -->
<table id="searchMaskTable" width="940" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">



<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">


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

<input type="hidden" name="projectId" id="projectId" value="<c:out value="${status.current.projectId}"/>">
<input type="hidden" name="insertOrUpdate" id="insertOrUpdate" value="<c:out value="${status.current.insertOrUpdate}"/>">
<input type="hidden" name="originalCompanyId" id="originalCompanyId" value="<c:out value="${status.current.companyId}"/>">


<c:choose>
          <c:when test="${status.index % 2 == 0}" >
            <c:set var="colorClass" value=''/>
          </c:when>
          <c:otherwise>
            <c:set var="colorClass" value='alt'/>
          </c:otherwise>
        </c:choose>

<tr class="">
 <td width="5%" class="optionTitleBoldRight">
  <fmt:message key="label.company"/>:
 </td>
 <td width="10%" class="optionTitleBoldLeft">
 <c:set var="selectedCompanyId" value='${status.current.companyId}'/>
 <c:set var="selectedCompanyIdName" value=''/>
 <select name="companyId" id="companyId" onchange="projectCompanychanged()" class="selectBox">
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
    <option value="<c:out value="${currentCompanyId}"/>" selected><c:out value="${companyStatus.current.companyName}"/></option>
    <c:set var="selectedCompanyIdName" value="${companyStatus.current.companyName}"/>
   </c:when>
   <c:otherwise>
    <option value="<c:out value="${currentCompanyId}"/>"><c:out value="${companyStatus.current.companyName}"/></option>
   </c:otherwise>
  </c:choose>
  </c:forEach>
 </select>
 </td>



<td width="5%" colspan="5">
<c:choose>
<c:when test="${updatePermission == 'Yes'}">
<html:submit property="submitSaveAsNew" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return saveAsNewProject()">
     <fmt:message key="peiproject.button.saveasnew"/>
</html:submit>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<html:submit property="submitUpdate" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return updateProject()">
     <fmt:message key="button.update"/>
</html:submit>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<html:submit property="submitNew" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return newProject()">
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
<html:button property="submitPrint" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="printPdf()">
     <fmt:message key="label.print"/>
</html:button>
</td>
</tr>

<tr class="alt">
<td width="5%" class="optionTitleBoldRight"><fmt:message key="peiproject.label.projectname"/>:</td>

<td width="5%" colspan="3" class="optionTitleBoldLeft">
<input class="inputBox" type="text" name="projectName" id="projectName" value="<c:out value="${status.current.projectName}"/>" size="50" maxlength="49">
</td>

<td width="5%" class="optionTitleBoldRight"><fmt:message key="peiproject.label.hassprojectmanager"/>:</td>

<td width="6%" nowrap>
<input class="inputBox" type="hidden" name="projectManagerId" id="projectManagerId" value="<c:out value="${projectManagerId}"/>">

<c:set var="submitNew" value='${param.submitNew}'/>
<c:choose>
<c:when test="${!empty submitNew}">
<c:set var="firstname" value='${sessionScope.personnelBean.firstName}'/>
<c:set var="lastname" value='${sessionScope.personnelBean.lastName}'/>
  <input name="projectManager" id="projectManager" type="text" maxlength="18" size="15" 
  	value="<c:out value="${lastname}"/>,<c:out value="${firstname}"/>" class="invGreyInputText" readonly/>
<!--<input class="inputBox" type="text" name="projectManager" id="projectManager" value="<c:out value="${lastname}"/>,<c:out value="${firstname}"/>" size="12" readonly>-->
</c:when>
<c:otherwise>
  <input name="projectManager" id="projectManager" type="text" maxlength="18" size="15" 
  	value="<c:out value="${projectManager}"/>" class="invGreyInputText" readonly/>           
 <!--<input class="inputBox" type="text" name="projectManager" id="projectManager" value="<c:out value="${projectManager}"/>" size="12" readonly>-->
</c:otherwise>
</c:choose>

<c:if test="${adminPermission == 'Yes'}">
 <input class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchpersonnellike" id="searchpersonnellike" value="" OnClick="getProjectManager()" type="button">
 <button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
                 name="None" value=""  OnClick="clearProjectMgr();return false;"><fmt:message key="label.clear"/> </button>
</c:if>

<%--<select name="projectManagerId" class="HEADER">
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
</td>
</tr>

<tr class="">
<td width="5%" class="optionTitleBoldRight"><fmt:message key="peiproject.label.projectdesc"/>:</td>
<td colspan="5" ><textarea class="inputBox" name="projectDesc" id="projectDesc" rows="5" cols="100"><c:out value="${status.current.projectDesc}"/></textarea></td>
</tr>

<tr class="alt">
<td width="5%" class="optionTitleBoldRight"><fmt:message key="peiproject.label.keywords"/>:</td>

<td width="5%" colspan="2"><fmt:message key="peiproject.label.basekeywords"/><br>
<select name="basekeywords" id="basekeywords" size="4" multiple class="selectBox">
 <c:forEach var="vvPeiProjectKeywordBean" items="${status.current.baseKeywordsCollection}" varStatus="statusKeyword">
 <c:set var="currentKeywordId" value='${statusKeyword.current.keywordId}'/>
  <option value="<c:out value="${currentKeywordId}"/>"><c:out value="${statusKeyword.current.keywordDesc}"/></option>
 </c:forEach>
</select>
</td>

<td width="2%">
<input type="button" id="basekeywordsss" name="basekeywordsss" value="===>" name="NewtskButton" onclick= "transferMultipleItems(this.form.basekeywords,this.form.keywordsCollectionSelect)" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" ><br><br><br>
<input type="button" id="basekeywordssss" name="basekeywordsss" value="<===" name="NewtskButton" onclick= "transferMultipleItems(this.form.keywordsCollectionSelect,this.form.basekeywords)" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" >
</td>

<td width="5%" colspan="2"><fmt:message key="peiproject.label.selectedkeywords"/><br>
<select name="keywordsCollectionSelect" id="keywordsCollectionSelect" size="4" multiple class="selectBox">
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
</td>
</tr>

<tr class="">
<td width="5%" class="optionTitleBoldRight">
<fmt:message key="label.facility"/>:
</td>

<td width="5%" class="optionTitleBoldLeft">
<c:set var="selectedFacilityId" value='${status.current.facilityId}'/>
<select name="facilityId" id="facilityId" class="selectBox">
  <option value=""><fmt:message key="label.all"/></option>
  <c:forEach var="facilityObjBean" items="${facilityBeanCollection}" varStatus="statusFacility">
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
</td>

<%--<td width="5%" class=""><fmt:message key="label.facility"/>:</b></td>

<td width="5%" colspan="2" class="">
<c:set var="selectedFacilityId" value='${status.current.facilityId}'/>
<select name="facilityId" class="HEADER">
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
</td>--%>

<td width="2%" class="optionTitleBoldRight"><fmt:message key="peiproject.label.clientcontact"/>:</td>
<td width="6%" class="" nowrap>
<input type="hidden" name="customerContactId" id="customerContactId" value="<c:out value="${status.current.customerContactId}"/>">
<input name="customerContactManager" id="customerContactManager" type="text" maxlength="18" size="15" 
  	value="<c:out value="${status.current.customerContactManager}"/>" class="invGreyInputText" readonly/>      
<!--<input class="inputBox" type="text" name="customerContactManager" id="customerContactManager" value="<c:out value="${status.current.customerContactManager}"/>" size="12" readonly>-->
<input class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchpersonnellike" id="searchpersonnellike" value="" OnClick="getpersonnelID()" type="button">
<button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
                 name="None" value=""  OnClick="clearClientContact();return false;"><fmt:message key="label.clear"/> </button>
</td>

<td width="5%" colspan="2" >
<c:set var="showOnlyBestPractice" value='${status.current.bestPractice}'/>
<c:if test="${showOnlyBestPractice != null}" >
 <c:set var="checkBoxChecked" value='checked'/>
</c:if>
<input type="checkbox" name="bestPractice" id="bestPractice" value="y" <c:out value="${checkBoxChecked}"/>>
<fmt:message key="peiproject.label.bestpractice"/>
</td>
</tr>

<tr class="alt">
<td width="5%" class="optionTitleBoldRight"><fmt:message key="label.status"/>:</td>

<td width="5%" class="optionTitleBoldLeft">
<c:set var="selectedProjectStatus" value='${status.current.projectStatus}'/>
<select name="projectStatus" id="projectStatus" class="selectBox">
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
</td>

<td width="5%" class="optionTitleBoldRight"><fmt:message key="peiproject.label.proposeddate"/>:</td>
<td width="5%" class="optionTitleBoldLeft">
<fmt:formatDate var="formattedProposedDateToClient" value="${status.current.proposedDateToClient}" pattern="${dateFormatPattern}"/>
<input type="text" readonly class="inputBox pointer" name="proposedDateToClient" id="proposedDateToClient" onClick="return getCalendar(document.peiProjectForm.proposedDateToClient);" value="<c:out value="${formattedProposedDateToClient}"/>" size="8" maxlength="10"">
<%-- <a href="javascript: void(0);" id="linkproposedDateToClient" style="color:#0000ff;cursor:pointer;text-decoration:underline" onClick="return getCalendar(document.peiProjectForm.proposedDateToClient);">&diams;</a> --%>
</td>

<td width="5%" class="optionTitleBoldRight"><fmt:message key="label.approveddate"/>:</td>
<td width="5%"  class="optionTitleBoldLeft">
<fmt:formatDate var="formattedApprovedDate" value="${status.current.approvedDate}" pattern="${dateFormatPattern}"/>
<input type="text" readonly class="inputBox pointer" name="approvedDate" id="approvedDate" onClick="return getCalendar(document.peiProjectForm.approvedDate);" value="<c:out value="${formattedApprovedDate}"/>" size="8" maxlength="10">
<%-- <a href="javascript: void(0);" id="linkapprovedDate" style="color:#0000ff;cursor:pointer;text-decoration:underline" onClick="return getCalendar(document.peiProjectForm.approvedDate);">&diams;</a> --%>
</td>
</tr>

<tr class="">
<td width="5%" class="optionTitleBoldRight"><fmt:message key="label.category"/>:</td>

<td width="5%" class="optionTitleBoldLeft">
<c:set var="selectedProjectCategory" value='${status.current.projectCategory}'/>
<select name="projectCategory" id="projectCategory" class="selectBox">
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
</td>

<td width="5%" class="optionTitleBoldRight"><fmt:message key="peiproject.label.startdate"/>:</td>
<td width="5%" class="optionTitleBoldLeft">
<fmt:formatDate var="formattedStartDate" value="${status.current.startDate}" pattern="${dateFormatPattern}"/>
<input type="text" readonly class="inputBox pointer" name="startDate" id="startDate" onClick="return getCalendar(document.peiProjectForm.startDate);" value="<c:out value="${formattedStartDate}"/>" size="8" maxlength="10">
<%-- <a href="javascript: void(0);" id="linkstartDate" style="color:#0000ff;cursor:pointer;text-decoration:underline" onClick="return getCalendar(document.peiProjectForm.startDate);">&diams;</a> --%>
</td>

<td width="5%" class="optionTitleBoldRight"><fmt:message key="peiproject.label.gainshareduration"/>:</td>
<td width="2%" class="optionTitleBoldLeft"><input class="inputBox" type="text" name="gainShareDuration" id="gainShareDuration" value="<c:out value="${status.current.gainShareDuration}"/>" size="15" maxlength="90"></td>


</tr>

<tr class="alt">
<td width="5%" class="optionTitleBoldRight"><fmt:message key="label.priority"/>:</td>
<td width="5%" class="optionTitleBoldLeft">
<c:set var="selectedPriority" value='${status.current.priority}'/>
<select name="priority" id="priority" class="selectBox">
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
</td>

<td width="5%" class="optionTitleBoldRight"><fmt:message key="peiproject.label.projectedcost"/>:</td>
<td width="2%" class="optionTitleBoldLeft"><input class="inputBox" type="text" name="pojectedCost" id="pojectedCost" value="<c:out value="${status.current.pojectedCost}"/>" onchange="checkForNumber('pojectedCost')" size="7"></td>

<td width="5%" class="optionTitleBoldRight"><fmt:message key="peiproject.label.actualcost"/>:</td>
<td width="5%" class="optionTitleBoldLeft"><input class="inputBox" type="text" name="actualCost" id="actualCost" value="<c:out value="${status.current.actualCost}"/>" onchange="checkForNumber('actualCost')" size="7"></td>
</tr>

<tr class="">
<td width="5%" class="">&nbsp;</td>
<td width="5%" class="">&nbsp;</td>

<td width="5%" class="optionTitleBoldRight"><fmt:message key="peiproject.label.projectedfinishdate"/>:</td>
<td width="5%" class="optionTitleBoldLeft">
<fmt:formatDate var="formattedProjectedFinistedDate" value="${status.current.projectedFinistedDate}" pattern="${dateFormatPattern}"/>
<input type="text" readonly class="inputBox pointer" name="projectedFinistedDate" id="projectedFinistedDate" onClick="return getCalendar(document.peiProjectForm.projectedFinistedDate);" value="<c:out value="${formattedProjectedFinistedDate}"/>" size="8" maxlength="10">
<%-- <a href="javascript: void(0);" id="linkprojectedFinistedDate" style="color:#0000ff;cursor:pointer;text-decoration:underline" onClick="return getCalendar(document.peiProjectForm.projectedFinistedDate);">&diams;</a> --%>
</td>


<td width="5%" class="optionTitleBoldRight"><fmt:message key="peiproject.label.actualfinishdate"/>:</td>
<td width="5%" class="optionTitleBoldLeft">
<fmt:formatDate var="formattedActualFinishDate" value="${status.current.actualFinishDate}" pattern="${dateFormatPattern}"/>
<input type="text" readonly class="inputBox pointer" name="actualFinishDate" id="actualFinishDate" onClick="return getCalendar(document.peiProjectForm.actualFinishDate);" value="<c:out value="${formattedActualFinishDate}"/>" size="8" maxlength="10">
<%-- <a href="javascript: void(0);" id="linkactualFinishDate" style="color:#0000ff;cursor:pointer;text-decoration:underline" onClick="return getCalendar(document.peiProjectForm.actualFinishDate);">&diams;</a>  --%>
</td>
</tr>

<tr>
<td width="5%" colspan="6" ><fmt:message key="peiproject.label.costinformation"/>:
<c:if test="${updatePermission == 'Yes'}">
<html:button property="submitAddSavings" styleClass="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onclick= "addSavingsLine()">
     <fmt:message key="label.add"/>
</html:button>
</c:if>

<table class="tableResults" id="savingstable" border="0" width="60%" cellspacing="1" cellpadding="3">
<c:set var="colorClass" value='blue'/>
<c:set var="savingsCount" value='${0}'/>
<c:forEach var="peiProjectSavingsBean" items="${status.current.savingsCollection}" varStatus="statusSavings">

<c:if test="${statusSavings.index == 0}">
<tr class="">
<td width="5%" class="" height="38"><fmt:message key="label.currency"/>:</td>
<td class="" width="5%" colspan="2">
<c:set var="selectedCurrencyId" value='${statusSavings.current.currencyId}'/>
<c:if test="${empty selectedCurrencyId || selectedCurrencyId == ''}" >
 <c:set var="selectedCurrencyId" value='USD'/>
 <tcmis:isCNServer>
 	<c:set var="selectedCurrencyId" value='CNY'/>
 </tcmis:isCNServer>
</c:if>

<select name="currencyId" id="currencyId" class="selectBox" onChange= "showCurrencyChangeMessage()">
<c:forEach var="vvCurrencyBean" items="${vvCurrencyCollection}" varStatus="statusCurrency">
<c:set var="currentCurrencyId" value='${statusCurrency.current.currencyId}'/>
<c:choose>
<c:when test="${selectedCurrencyId == currentCurrencyId}" >
 <option value="<c:out value="${currentCurrencyId}"/>" selected><c:out value="${statusCurrency.current.currencyDescription}"/></option>
</c:when>
<c:otherwise>
 <option value="<c:out value="${currentCurrencyId}"/>"><c:out value="${statusCurrency.current.currencyDescription}"/></option>
</c:otherwise>
</c:choose>
</c:forEach>
</select>
</td>
</tr>
</c:if>

<c:if test="${statusSavings.index % 10 == 0}">
<tr id="savingsRowHeader">
<th width="5%" class="tableheader" height="38"><fmt:message key="label.period"/></th>
<th width="5%" class="tableheader" height="38"><fmt:message key="peiproject.label.projectedsavings"/></th>
<th width="5%" class="tableheader" height="38"><fmt:message key="peiproject.label.actualsavings"/></th>
</tr>
</c:if>

<c:choose>
     <c:when test="${status.index % 2 == 0}" >
      <c:set var="colorClass" value=''/>
     </c:when>
     <c:otherwise>
      <c:set var="colorClass" value='alt'/>
     </c:otherwise>
    </c:choose>

<tr align="center" id="savingsRowId<c:out value="${statusSavings.index}"/>">

<input type="hidden" name="peiProjectSavingsBean[<c:out value="${statusSavings.index}"/>].periodId" id="periodId<c:out value="${statusSavings.index}"/>" value="<c:out value="${statusSavings.current.periodId}"/>">

<td <c:out value="${colorClass}"/> width="5%"><input type="text" name="peiProjectSavingsBean[<c:out value="${statusSavings.index}"/>].periodName" id="periodName<c:out value="${statusSavings.index}"/>" value="<c:out value="${statusSavings.current.periodName}"/>" size="15" maxlength="30"></td>
<td <c:out value="${colorClass}"/> width="5%"><input type="text" name="peiProjectSavingsBean[<c:out value="${statusSavings.index}"/>].projectedPeriodSavings" id="projectedPeriodSavings<c:out value="${statusSavings.index}"/>" value="<c:out value="${statusSavings.current.projectedPeriodSavings}"/>" onchange="updateProjectedSavingsTotals(<c:out value="${statusSavings.index}"/>)" size="6" maxlength="30"></td>
<td <c:out value="${colorClass}"/> width="5%"><input type="text" name="peiProjectSavingsBean[<c:out value="${statusSavings.index}"/>].actualPeriodSavings" id="actualPeriodSavings<c:out value="${statusSavings.index}"/>" value="<c:out value="${statusSavings.current.actualPeriodSavings}"/>" onchange="updateActualSavingsTotals(<c:out value="${statusSavings.index}"/>)" size="6" maxlength="30"></td>


<c:set var="savingsCount" value='${savingsCount+1}'/>
</c:forEach>

<c:if test="${savingsCount == 0}" >
<tr>
<td width="5%" class="" height="38"><fmt:message key="label.currency"/>:</td>
<td class="" width="5%" colspan="2">
 <c:set var="selectedCurrencyId" value='USD'/>
 <tcmis:isCNServer>
 	<c:set var="selectedCurrencyId" value='CNY'/>
 </tcmis:isCNServer>

<select name="currencyId" id="currencyId" class="selectBox" onChange= "showCurrencyChangeMessage()">
<c:forEach var="vvCurrencyBean" items="${vvCurrencyCollection}" varStatus="statusCurrency">
<c:set var="currentCurrencyId" value='${statusCurrency.current.currencyId}'/>
<c:choose>
<c:when test="${selectedCurrencyId == currentCurrencyId}" >
 <option value="<c:out value="${currentCurrencyId}"/>" selected><c:out value="${statusCurrency.current.currencyDescription}"/></option>
</c:when>
<c:otherwise>
 <option value="<c:out value="${currentCurrencyId}"/>"><c:out value="${statusCurrency.current.currencyDescription}"/></option>
</c:otherwise>
</c:choose>
</c:forEach>
</select>
</td>
</tr>

<tr id="savingsRowHeader">
<th width="2%" class="tableheader" height="38"><fmt:message key="label.period"/></th>
<th width="5%" class="tableheader" height="38"><fmt:message key="peiproject.label.projectedsavings"/></th>
<th width="5%" class="tableheader" height="38"><fmt:message key="peiproject.label.actualsavings"/></th>
</tr>
</c:if>

<tr align="center">
<td width="2%" class="black" height="38"><fmt:message key="label.total"/></td>
<td width="5%" class="black" id="totalProjectedPeriodSavings" height="38"><c:out value="${status.current.totalProjectedPeriodSavings}"/></td>
<td width="5%" class="black" id="totalActualPeriodSavings" height="38"><c:out value="${status.current.totalActualPeriodSavings}"/></td>
</tr>
</table>

<input type="hidden" name="savingsCount" id="savingsCount" value="<c:out value="${savingsCount}"/>">
</td>
</tr>

<tr class="alt">
<td width="5%" colspan="6" class="alt"><fmt:message key="peiproject.label.associateddocuments"/>:
<c:if test="${updatePermission == 'Yes'}">
<html:button property="submitAddDocument" styleClass="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onclick="addProjectDocument()">
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

<table id="documentstable" border="0" width="40%" cellspacing="1" cellpadding="3">
<c:set var="colorClass" value='white'/>
<c:set var="documentCount" value='${0}'/>

<c:forEach var="peiProjectDocumentBean" items="${status.current.documentsCollection}" varStatus="statusDocument">
  <c:if test="${statusDocument.index % 10 == 0}">
   <tr align="center">
   <th width="5%" height="38"><fmt:message key="label.document"/></th>
   <th width="2%" height="38"><fmt:message key="label.delete"/></th>
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

  <tr align="center" class="<c:out value="${colorClass}"/>" id="documentRowId<c:out value="${statusDocument.index}"/>">
  <td width="5%" id="documentUrl<c:out value="${statusDocument.index}"/>">
   <a href="<c:out value="${statusDocument.current.url}"/>" TARGET="newDocument<c:out value="${statusDocument.index}"/>" style="color:#0000ff;cursor:pointer;text-decoration:underline">
    <c:out value="${statusDocument.current.documentName}"/>
   </a>
  </td>
  <td width="5%" id="documentDelete<c:out value="${statusDocument.index}"/>">
  <c:choose>
   <c:when test="${deletePermission == 'Yes'}" >
    <input type="checkbox" name="peiProjectDocumentBean[<c:out value="${statusDocument.index}"/>].delete" id="delete<c:out value="${statusDocument.index}"/>" value="Y">
   </c:when>
   <c:otherwise>
    &nbsp;
   </c:otherwise>
  </c:choose>
  </td>
  </tr>
<c:set var="documentCount" value='${documentCount+1}'/>
</c:forEach>

<c:if test="${documentCount == 0}" >
<tr>
 <th width="5%" height="38"><fmt:message key="label.document"/></th>
 <th width="2%" height="38"><fmt:message key="label.delete"/></th>
</tr>
</c:if>
</table>

<input type="hidden" name="documentCount" id="documentCount" value="<c:out value="${documentCount}"/>">
<input type="hidden" name="deletePermission" id="deletePermission" value="<c:out value="${deletePermission}"/>">
</td>
</tr>

<tr>
<td width="5%" class=""><fmt:message key="label.comments"/>:</td>
<td colspan="6" class=""><textarea class="inputBox" name="comments" id="comments" rows="5" cols="100"><c:out value="${status.current.comments}"/></textarea></td>
</tr>
</c:forEach>
</table>



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






<c:if test="${empty peiProjectViewBeanCollection}" >
<c:if test="${peiProjectViewBeanCollection != null}" >

   <!-- If the collection is empty say no data found -->
   <c:if test="${empty pageNameViewBeanCollection}" >

    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData">
     <tr>
      <td width="100%">
       <fmt:message key="label.nodatafound"/>
      </td>
     </tr>
    </table>
   </c:if>


</c:if>
</c:if>

</tcmis:form>
</div>
</body>
</html:html>


