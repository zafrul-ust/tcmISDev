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
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>

<!-- Add any other javascript you need for the page here -->
<script SRC="/js/common/peiprojects.js" language="JavaScript"></script>
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
<fmt:message key="peiproject.label.projectlist"/>
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

facilityNameArray["<c:out value="${currentCompany}"/>"] = new Array("All"
 <c:forEach var="facilityObjBean" items="${facilityBeanCollection}" varStatus="status1">
,"<c:out value="${status1.current.facilityName}"/>"
  </c:forEach>

  );
 </c:forEach>
// -->
</script>

</head>

<c:set var="submitCreateReport" value='${param.submitCreateReport}'/>
<c:set var="doexcelpopup" value='${doexcelpopup}'/>
<c:choose>
  <c:when test="${!empty doexcelpopup && !empty submitCreateReport}" >
    <body onLoad="doexcelpopup()">
  </c:when>
  <c:otherwise>
   <body>
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
<tr>
  <td width="70%" class="headingl">
<fmt:message key="peiproject.label.projectlist"/>
</td>
<td width="30%" class="headingr">
<html:link style="color:#FFFFFF" forward="home">
 <fmt:message key="label.home"/>
</html:link>
</td>
</tr>
</table>
</div>

<tcmis:form action="/peiprojectlist.do" onsubmit="return submitOnlyOnce();">


<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="1000" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">

 <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
   <tr>
     <td width="5%" rowspan="2" class="optionTitleBoldRight">
       <fmt:message key="label.status"/>:
     </td>

<td rowspan="2" class="optionTitleBoldLeft" width="5%">

<html:select property="statusCollectionSelect" styleId="statusCollectionSelect" size="4" multiple="true" styleClass="selectBox">
<html:optionsCollection name="peiProjectInputBean" property="statusCollection" value="projectStatus" label="projectStatusDesc"/>
</html:select>
<%--
<c:set var="selectedProjectStatus" value='${param.projectStatus}'/>
<select name="projectStatus" CLASS="HEADER">
  <option value="All"><fmt:message key="label.all"/></option>
  <c:forEach var="vvPeiProjectStatusBean" items="${vvPeiProjectStatusCollection}" varStatus="status">
  <c:set var="currentProjectStatus" value='${status.current.projectStatus}'/>
  <c:if test="${currentProjectStatus != '8'}" >
  <c:choose>
   <c:when test="${currentProjectStatus == selectedProjectStatus}">
    <option value="<c:out value="${currentProjectStatus}"/>" selected><c:out value="${status.current.projectStatusDesc}"/></option>
   </c:when>
   <c:otherwise>
    <option value="<c:out value="${currentProjectStatus}"/>"><c:out value="${status.current.projectStatusDesc}"/></option>
   </c:otherwise>
  </c:choose>
  </c:if>
  </c:forEach>
</select>
--%>
</td>

<td rowspan="2" width="5%" class="optionTitleBoldRight">
<fmt:message key="label.category"/>:
</td>

<td rowspan="2" width="5%" class="optionTitleBoldRight">
<html:select property="categoryCollectionSelect" size="4" multiple="true" styleClass="selectBox">
<html:optionsCollection name="peiProjectInputBean" property="categoryCollection" value="projectCategory" label="projectCategoryDesc"/>
</html:select>

<%--<c:set var="selectedProjectCategory" value='${param.projectCategory}'/>
<select name="projectCategory" CLASS="HEADER">
  <option value="All"><fmt:message key="label.all"/></option>
  <c:forEach var="vvProjectCategoryBean" items="${vvProjectCategoryCollection}" varStatus="status">
  <c:set var="currentProjectCategory" value='${status.current.projectCategory}'/>

  <c:choose>
   <c:when test="${currentProjectCategory == selectedProjectCategory}">
    <option value="<c:out value="${currentProjectCategory}"/>" selected><c:out value="${status.current.projectCategoryDesc}"/></option>
   </c:when>
   <c:otherwise>
    <option value="<c:out value="${currentProjectCategory}"/>"><c:out value="${status.current.projectCategoryDesc}"/></option>
   </c:otherwise>
  </c:choose>
  </c:forEach>
</select>--%>
</td>

<td width="5%" rowspan="2" class="optionTitleBoldRight">
<fmt:message key="peiproject.label.keywords"/>:
</td>

<td width="5%" rowspan="2" class="optionTitleBoldLeft">
<html:select property="keywordsCollectionSelect" size="4" multiple="true" styleClass="selectBox">
<html:optionsCollection name="peiProjectInputBean" property="keywordsCollection" value="keywordId" label="keywordDesc"/>
<%--<html:options collection="keywordsCollection" property="keywordId" labelProperty="keywordDesc"/>--%>
</html:select>
<%--
<c:set var="selectedKeywordId" value='${param.projectStatus}'/>
<select name="keywordId" size="4" multiple CLASS="HEADER">
  <option value="All"><fmt:message key="label.all"/></option>
  <c:forEach var="vvPeiProjectKeywordBean" items="${vvPeiProjectKeywordCollection}" varStatus="status">
  <c:set var="currentKeywordId" value='${status.current.keywordId}'/>

  <c:choose>
   <c:when test="${currentKeywordId == selectedKeywordId}">
    <option value="<c:out value="${currentKeywordId}"/>" selected><c:out value="${status.current.keywordDesc}"/></option>
   </c:when>
   <c:otherwise>
    <option value="<c:out value="${currentKeywordId}"/>"><c:out value="${status.current.keywordDesc}"/></option>
   </c:otherwise>
  </c:choose>
  </c:forEach>
</select>
--%>
</td>

<c:set var="keywordSearchText" value='${param.keywordSearchText}'/>

<td width="5%" class="optionTitleBoldLeft">
<input class="inputBox" type="text" name="keywordSearchText" id="keywordSearchText" value="<c:out value="${keywordSearchText}"/>" size="20">
</td>

<td width="5%" class="optionTitleBoldLeft">
<html:submit property="submitSearch" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return search()">
     <fmt:message key="peiproject.button.listprojects"/>
</html:submit>
</td>
</tr>

<tr>

<td width="15%" class="optionTitleBoldLeft">
<c:set var="keywordAndOrOr" value='${param.keywordAndOrOr}'/>
<c:if test="${empty keywordAndOrOr || keywordAndOrOr == ''}" >
 <c:set var="keywordAndOrOr" value='or'/>
</c:if>

<c:choose>
  <c:when test="${keywordAndOrOr == 'and'}" >
   <input class="radioBtns" id="keywordAndOrOr" type="radio" name="keywordAndOrOr" value="or">
   <fmt:message key="label.or"/>&nbsp;&nbsp;
   <input class="radioBtns" id="keywordAndOrOr" type="radio" name="keywordAndOrOr" value="and" checked>
   <fmt:message key="label.and"/>
  </c:when>
  <c:otherwise>
   <input id="keywordAndOrOr" type="radio" name="keywordAndOrOr" value="or" checked>
   <fmt:message key="label.or"/>&nbsp;&nbsp;
   <input id="keywordAndOrOr" type="radio" name="keywordAndOrOr" value="and">
   <fmt:message key="label.and"/>
  </c:otherwise>
</c:choose>
</td>

<td width="5%" class="optionTitleBoldLeft">
<html:button property="submitNew" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="startNewProject()">
     <fmt:message key="peiproject.button.newproject"/>
</html:button>
</td>
</tr>

<tr>

<td width="5%" class="optionTitleBoldRight">
<fmt:message key="peiproject.label.projectmanager"/>:
</td>

<td width="10%" class="optionTitleBoldLeft">
<input type="hidden" name="projectManagerId" id="projectManagerId" value="<c:out value="${param.projectManagerId}"/>">
<input class="inputBox" type="text" name="projectManager" id="projectManager" value="<c:out value="${param.projectManager}"/>" size="18" readonly>
<input class="lookupBtn" name="searchpersonnellike" id="searchpersonnellike" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" value="" OnClick="getProjectManager()" type="button">

  <%--
  <c:set var="selectedProjectManager" value='${param.projectManagerId}'/>
  <select name="projectManagerId" CLASS="HEADER">
    <option value="0"><fmt:message key="label.all"/></option>
    <c:forEach var="personnelBean" items="${vvProjectManagerCollection}" varStatus="status">
      <c:set var="currentProjectManager" value='${status.current.personnelId}'/>

      <c:choose>
        <c:when test="${currentProjectManager == selectedProjectManager}">
          <option value="<c:out value="${currentProjectManager}"/>" selected><c:out value="${status.current.firstName}"/>&nbsp;<c:out value="${status.current.lastName}"/></option>
        </c:when>
        <c:otherwise>
          <option value="<c:out value="${currentProjectManager}"/>"><c:out value="${status.current.firstName}"/>&nbsp;<c:out value="${status.current.lastName}"/></option>
        </c:otherwise>
      </c:choose>
    </c:forEach>
  </select>
  --%>
</td>

<td width="5%" class="optionTitleBoldRight">
<fmt:message key="label.priority"/>:
</td>

<td width="5%" class="optionTitleBoldLeft">
<c:set var="selectedPriority" value='${param.priority}'/>
<select name="priority" id="priority" class="selectBox">
  <option value="All"><fmt:message key="label.all"/></option>
  <c:forEach var="vvProjectPriorityBean" items="${vvProjectPriorityCollection}" varStatus="status">
  <c:set var="currentPriority" value='${status.current.projectPriority}'/>

  <c:choose>
   <c:when test="${currentPriority == selectedPriority}">
    <option value="<c:out value="${currentPriority}"/>" selected><c:out value="${status.current.projectPriorityDesc}"/></option>
   </c:when>
   <c:otherwise>
    <option value="<c:out value="${currentPriority}"/>"><c:out value="${status.current.projectPriorityDesc}"/></option>
   </c:otherwise>
  </c:choose>
  </c:forEach>
</select>
</td>

<td width="5%" class="optionTitleBoldRight">
<fmt:message key="label.sortby"/>:
</td>

<td width="5%" class="optionTitleBoldLeft">
<c:set var="selectedSortBy" value='${param.sortBy}'/>
<c:if test="${empty selectedSortBy || selectedSortBy == ''}" >
 <c:set var="selectedSortBy" value='PROJECT_STATUS asc'/>
</c:if>

<select class="selectBox"  name="sortBy" id="sortBy" size="1">
<option  value="PROJECT_CATEGORY asc" <c:if test="${selectedSortBy == 'PROJECT_CATEGORY asc'}" >selected</c:if>><fmt:message key="label.category"/></option>
<option  value="PROJECTED_FINISTED_DATE desc" <c:if test="${selectedSortBy == 'PROJECTED_FINISTED_DATE desc'}" >selected</c:if>><fmt:message key="label.finishdate"/></option>
<option  value="PRIORITY desc" <c:if test="${selectedSortBy == 'PRIORITY desc'}" >selected</c:if>><fmt:message key="label.priority"/></option>
<option  value="PROJECT_STATUS asc" <c:if test="${selectedSortBy == 'PROJECT_STATUS asc'}" >selected</c:if>><fmt:message key="label.status"/></option>
</select>
</td>

<td width="5%" class="optionTitleBoldLeft">
<c:set var="bestPractice" value='${param.bestPractice}'/>
<c:if test="${bestPractice != null}" >
 <c:set var="checkBoxChecked" value='checked'/>
</c:if>
<input class="radioBtns" type="checkbox" name="bestPractice" id="bestPractice" value="Y" <c:out value="${checkBoxChecked}"/>>
<fmt:message key="peiproject.label.onlybestpractice"/>
</td>

<td width="5%" class="optionTitleBoldRight">


<html:submit property="submitCreateReport" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
     <fmt:message key="label.createexcelfile"/>
</html:submit>
</td>

</tr>

<tr>

<td width="5%" class="optionTitleBoldRight">
  <fmt:message key="label.company"/>:
 </td>
 <td width="10%" class="optionTitleBoldLeft">
 <c:set var="selectedCompanyId" value='${param.companyId}'/>
 <c:set var="selectedCompanyIdName" value=''/>
 <select name="companyId" id="companyId" onchange="companychanged()" class="selectBox">
 <option value="All"><fmt:message key="label.all"/></option>
 <c:forEach var="facilitiesForCompanyObjBean" items="${facilitiesForCompanyObjBeanCollection}" varStatus="status">
 <c:set var="currentCompanyId" value='${status.current.companyId}'/>

  <c:choose>
   <c:when test="${empty selectedCompanyId}" >
    <%--<c:set var="selectedCompanyId" value='${status.current.companyId}'/>
    <c:set var="selectedCompanyIdName" value="${status.current.companyName}"/>
    <c:set var="facilityBeanCollection" value='${status.current.facilities}'/>--%>
    <c:set var="selectedCompanyId" value='All'/>
    <c:set var="selectedCompanyIdName" value='All'/>
   </c:when>
   <c:when test="${currentCompanyId == selectedCompanyId}" >
    <c:set var="facilityBeanCollection" value='${status.current.facilities}'/>
   </c:when>
  </c:choose>

  <c:choose>
   <c:when test="${currentCompanyId == selectedCompanyId}">
    <option value="<c:out value="${currentCompanyId}"/>" selected><c:out value="${status.current.companyName}"/></option>
    <c:set var="selectedCompanyIdName" value="${status.current.companyName}"/>
   </c:when>
   <c:otherwise>
    <option value="<c:out value="${currentCompanyId}"/>"><c:out value="${status.current.companyName}"/></option>
   </c:otherwise>
  </c:choose>
  </c:forEach>
 </select>
 </td>

<td width="5%" class="optionTitleBoldRight">
<fmt:message key="label.facility"/>:
</td>

<td width="5%" class="optionTitleBoldLeft">
<c:set var="selectedFacilityId" value='${param.facilityId}'/>
<select name="facilityId" id="facilityId" class="selectBox">
  <option value="All"><fmt:message key="label.all"/></option>
  <c:forEach var="facilityObjBean" items="${facilityBeanCollection}" varStatus="status">
  <c:set var="currentFacilityId" value='${status.current.facilityId}'/>

  <c:choose>
   <c:when test="${currentFacilityId == selectedFacilityId}">
    <option value="<c:out value="${currentFacilityId}"/>" selected><c:out value="${status.current.facilityName}"/></option>
   </c:when>
   <c:otherwise>
    <option value="<c:out value="${currentFacilityId}"/>"><c:out value="${status.current.facilityName}"/></option>
   </c:otherwise>
  </c:choose>
  </c:forEach>
</select>
</td>
    <td width="5%">
     </td>
     <td width="5%">
     </td>

     <td width="5%">
     </td>
<td width="5%" class="optionTitleBoldRight">
  <html:button property="submitPrint" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="printprojectlist()">
     <fmt:message key="label.print"/>
   </html:button>
</td>
</tr>
</table>
   <!-- End search options -->
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
<!-- Search Option Ends -->

<div class="spacerY">&nbsp;</div>

<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
<!-- Error Messages Ends -->

<c:if test="${peiProjectViewListBeanCollection != null}" >
<!-- Search results start -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
    </div>
    <div class="roundContent">
      <div class="boxhead"> </div>
      <div class="dataContent">
          <c:set var="colorClass" value=''/>
          <c:set var="dataCount" value='${0}'/>
          <c:set var="rowCount" value='${0}'/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
<c:forEach var="peiProjectViewBean" items="${peiProjectViewListBeanCollection}" varStatus="status">
<c:set var="rowCount" value='${rowCount+1}'/>
<c:if test="${status.index % 10 == 0}">

<tr>
<th width="2%"><fmt:message key="label.print"/></th>
<th width="5%"><fmt:message key="label.company"/></th>
<th width="5%"><fmt:message key="label.facility"/></th>
<th width="5%"><fmt:message key="label.category"/></th>
<th width="2%"><fmt:message key="peiproject.label.bestpractice"/></th>
<th width="2%"><fmt:message key="label.id"/></th>
<th width="10%"><fmt:message key="peiproject.label.projectname"/></th>
<th width="15%"><fmt:message key="peiproject.label.projectdesc"/></th>
<th width="5%"><fmt:message key="peiproject.label.projectmanager"/></th>
<th width="5%"><fmt:message key="peiproject.label.proposeddate"/><hr><fmt:message key="label.approveddate"/></th>
<th width="5%"><fmt:message key="peiproject.label.gainshareduration"/></th>
<th width="5%"><fmt:message key="peiproject.label.projectedfinishdate"/><hr><fmt:message key="peiproject.label.actualfinishdate"/></th>
<th width="5%"><fmt:message key="peiproject.label.projectedcost"/><hr><fmt:message key="peiproject.label.actualcost"/></th>
<th width="5%"><fmt:message key="peiproject.label.totalprojectedsavings"/><hr><fmt:message key="peiproject.label.totalactualsavings"/></th>
<th width="5%"><fmt:message key="label.status"/></th>
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

   <tr class="<c:out value="${colorClass}"/>">
       <input type="hidden" name="projectId<c:out value="${status.index}"/>" id="projectId<c:out value="${status.index}"/>" value="<c:out value="${status.current.projectId}"/>">
       <td width="2%">
           <c:set var="printPdf" value='${status.current.printPdf}'/>
           <c:if test="${printPdf != null}" >
           <c:set var="checkBoxChecked" value='checked'/>
           </c:if>
        <input type="checkbox" name="printPdf<c:out value="${status.index}"/>" id="printPdf<c:out value="${status.index}"/>" value="Y" <c:out value="${checkBoxChecked}"/>>
      </td>
      <td width="5%"><c:out value="${status.current.companyId}"/></td>
      <td width="5%"><c:out value="${status.current.facilityId}"/></td>
      <td width="5%">
         <c:set var="selectedProjectCategory" value='${status.current.projectCategory}'/>
         <c:forEach var="vvProjectCategoryBean" items="${vvProjectCategoryCollection}" varStatus="statusCategory">
         <c:set var="currentProjectCategory" value='${statusCategory.current.projectCategory}'/>
         <c:choose>
         <c:when test="${currentProjectCategory == selectedProjectCategory}">
         <c:out value="${statusCategory.current.projectCategoryDesc}"/>
         </c:when>
         </c:choose>
         </c:forEach>
       </select>
     </td>
     <td width="2%"><c:out value="${status.current.bestPractice}"/></td>
     <td width="2%">

            <a href="javascript: showProjectDetails(<c:out value="${status.current.projectId}"/>);" style="color:#0000ff;cursor:pointer;text-decoration:underline"><c:out value="${status.current.projectId}"/></a>
           <%--<a href="<c:out value="${linkModule}"/>/showpeiproject.do?projectId=<c:out value="${status.current.projectId}"/>" target="new<c:out value="${status.index}"/>" style="color:#0000ff;cursor:pointer;text-decoration:underline"><c:out value="${status.current.projectId}"/></a>--%>
         </td>
     <td width="10%"><c:out value="${status.current.projectName}"/></td>
     <td width="15%"><c:out value="${status.current.projectDesc}"/></td>
     <td width="5%"><c:out value="${status.current.projectManager}"/></td>

     <td width="5%">
     <fmt:formatDate var="formattedProposedDateToClient" value="${status.current.proposedDateToClient}" pattern="MM/dd/yyyy"/>
      <c:out value="${formattedProposedDateToClient}"/>
       <hr>
<fmt:formatDate var="formattedApprovedDate" value="${status.current.approvedDate}" pattern="MM/dd/yyyy"/>
<c:out value="${formattedApprovedDate}"/>
</td>
<td width="5%"><c:out value="${status.current.gainShareDuration}"/></td>

<td width="5%">
<fmt:formatDate var="formattedProjectedFinistedDate" value="${status.current.projectedFinistedDate}" pattern="MM/dd/yyyy"/>
<c:out value="${formattedProjectedFinistedDate}"/><hr>
<fmt:formatDate var="formattedActualFinishDate" value="${status.current.actualFinishDate}" pattern="MM/dd/yyyy"/>
<c:out value="${formattedActualFinishDate}"/>
</td>
<td width="5%"><c:out value="${status.current.pojectedCost}"/><hr><c:out value="${status.current.actualCost}"/></td>
<td width="5%"><c:out value="${status.current.totalProjectedPeriodSavings}"/><hr><c:out value="${status.current.totalActualPeriodSavings}"/></td>
<td width="5%">
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
</tr>

<c:set var="dataCount" value='${dataCount+1}'/>
</c:forEach>
</table>

<!-- If the collection is empty say no data found -->
   <c:if test="${empty peiprojectlistViewBeanCollection}" >
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
     </tr>
    </table>
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
</c:if>

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="rowCount" id="rowCount" value="<c:out value="${rowCount}"/>">
</div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->
</tcmis:form>
</div>
</body>
</html:html>