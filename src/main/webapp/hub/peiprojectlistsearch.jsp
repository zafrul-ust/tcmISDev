<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
<script type="text/javascript" src="/js/common/searchiframeresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/peiproject/peiprojectlist.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
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
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",selectProject:"<fmt:message key="msg.selectProject"/>",
selectPrintProject:"<fmt:message key="msg.selectPrintProject"/>",
savingsMatch:"<fmt:message key="msg.savingsMatch"/>",
selectNumber:"<fmt:message key="msg.selectNumber"/>",
all:"<fmt:message key="label.all"/>"
};
// -->
</script>
<script language="JavaScript" type="text/javascript">
<!--
var companyIdArray = new Array(
<c:forEach var="facilitiesForCompanyObjBean" items="${facilitiesForCompanyObjBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"${status.current.companyId}"
   </c:when>
   <c:otherwise>
    "All","${status.current.companyId}"
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var facilityIdArray = new Array();
var facilityNameArray = new Array();
<c:forEach var="facilitiesForCompanyObjBean" items="${facilitiesForCompanyObjBeanCollection}" varStatus="status">
<c:set var="currentCompany" value='${status.current.companyId}'/>
<c:set var="facilityBeanCollection" value='${status.current.facilities}'/>

facilityIdArray["${currentCompany}"] = new Array(""
 <c:forEach var="facilityObjBean" items="${facilityBeanCollection}" varStatus="status1">
,"${status1.current.facilityId}"
  </c:forEach>

  );

facilityNameArray["${currentCompany}"] = new Array( "<fmt:message key="label.all"/>"
 <c:forEach var="facilityObjBean" items="${facilityBeanCollection}" varStatus="status1">
,"${status1.current.facilityName}"
  </c:forEach>

  );
 </c:forEach>
// -->
</script>


</head>

<body bgcolor="#ffffff" onload="setSearchFrameSize();">

<!--Uncomment for production-->
<tcmis:form action="/peiprojectlistresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

<div class="interface" id="searchMainPage"> <!-- Start of interface-->

<div class="contentArea"> <!-- Start of contentArea-->

<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <!-- Search Option Table Start -->
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
    <option value="${currentProjectStatus}" selected>${status.current.projectStatusDesc}</option>
   </c:when>
   <c:otherwise>
    <option value="${currentProjectStatus}">${status.current.projectStatusDesc}</option>
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
<html:select property="categoryCollectionSelect" styleId="categoryCollectionSelect" size="4" multiple="true" styleClass="selectBox">
<html:optionsCollection name="peiProjectInputBean" property="categoryCollection" value="projectCategory" label="projectCategoryDesc"/>
</html:select>

<%--<c:set var="selectedProjectCategory" value='${param.projectCategory}'/>
<select name="projectCategory" CLASS="HEADER">
  <option value="All"><fmt:message key="label.all"/></option>
  <c:forEach var="vvProjectCategoryBean" items="${vvProjectCategoryCollection}" varStatus="status">
  <c:set var="currentProjectCategory" value='${status.current.projectCategory}'/>

  <c:choose>
   <c:when test="${currentProjectCategory == selectedProjectCategory}">
    <option value="${currentProjectCategory}" selected>${status.current.projectCategoryDesc}</option>
   </c:when>
   <c:otherwise>
    <option value="${currentProjectCategory}">${status.current.projectCategoryDesc}</option>
   </c:otherwise>
  </c:choose>
  </c:forEach>
</select>--%>
</td>

<td width="5%" rowspan="2" class="optionTitleBoldRight" nowrap>
<fmt:message key="peiproject.label.keywords"/>:
</td>

<td width="5%" rowspan="2" class="optionTitleBoldLeft">
<html:select property="keywordsCollectionSelect" styleId="keywordsCollectionSelect" size="4" multiple="true" styleClass="selectBox">
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
    <option value="${currentKeywordId}" selected>${status.current.keywordDesc}</option>
   </c:when>
   <c:otherwise>
    <option value="${currentKeywordId}">${status.current.keywordDesc}</option>
   </c:otherwise>
  </c:choose>
  </c:forEach>
</select>
--%>
</td>

<c:set var="keywordSearchText" value='${param.keywordSearchText}'/>

<td width="5%" class="optionTitleBoldLeft">
<input class="inputBox" type="text" name="keywordSearchText" id="keywordSearchText" value="${keywordSearchText}" size="20">
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

</tr>

<tr>

<td width="5%" class="optionTitleBoldRight" nowrap>
<fmt:message key="peiproject.label.projectmanager"/>:
</td>

<td width="10%"  nowrap>

<input type="hidden" name="projectManagerId" id="projectManagerId" value="${param.projectManagerId}">
<input name="projectManager" id="projectManager" type="text" maxlength="18" size="15" class="invGreyInputText" readonly/>                                                           

<%--<span id="projectManager" name="projectManager"></span>
<input class="inputBox" type="text" name="projectManager" id="projectManager" value="${param.projectManager}" size="18" readonly>--%>
<input class="lookupBtn" name="searchpersonnellike" id="searchpersonnellike" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" value="" OnClick="getProjectManager()" type="button">
<button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
                 name="None" value=""  OnClick="clearProjectMgr();return false;"><fmt:message key="label.clear"/> </button>
  <%--
  <c:set var="selectedProjectManager" value='${param.projectManagerId}'/>
  <select name="projectManagerId" CLASS="HEADER">
    <option value="0"><fmt:message key="label.all"/></option>
    <c:forEach var="personnelBean" items="${vvProjectManagerCollection}" varStatus="status">
      <c:set var="currentProjectManager" value='${status.current.personnelId}'/>

      <c:choose>
        <c:when test="${currentProjectManager == selectedProjectManager}">
          <option value="${currentProjectManager}" selected>${status.current.firstName}&nbsp;${status.current.lastName}</option>
        </c:when>
        <c:otherwise>
          <option value="${currentProjectManager}">${status.current.firstName}&nbsp;${status.current.lastName}</option>
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
    <option value="${currentPriority}" selected="selected">${status.current.projectPriorityDesc}</option>
   </c:when>
   <c:otherwise>
    <option value="${currentPriority}">${status.current.projectPriorityDesc}</option>
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
<input class="radioBtns" type="checkbox" name="bestPractice" id="bestPractice" value="Y" ${checkBoxChecked}>
<fmt:message key="peiproject.label.onlybestpractice"/>
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
    <option value="${currentCompanyId}" selected="selected">${status.current.companyName}</option>
    <c:set var="selectedCompanyIdName" value="${status.current.companyName}"/>
   </c:when>
   <c:otherwise>
    <option value="${currentCompanyId}">${status.current.companyName}</option>
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
    <option value="${currentFacilityId}" selected="selected">${status.current.facilityName}</option>
   </c:when>
   <c:otherwise>
    <option value="${currentFacilityId}">${status.current.facilityName}</option>
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
</tr>
<tr>

<td colspan="4" width="5%" class="optionTitleBoldLeft">
<html:submit property="submitSearch" styleId="submitSearch" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return search()">
     <fmt:message key="peiproject.button.listprojects"/>
</html:submit>

<html:button property="submitNew" styleId="submitNew" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="startNewProject()">
     <fmt:message key="peiproject.button.newproject"/>
</html:button>

<html:button property="submitCreateReport" styleId="submitCreateReport" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="doexcelpopup();">
     <fmt:message key="label.createexcelfile"/>
</html:button>
</td>
<td colspan="3"/>
</tr>
    </table>
    <!-- Search Option Table end -->
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
<!-- Search Option Ends -->


<!-- Error Messages Begins -->
<!-- Build this section only if there is an error message to display.
     For the search section, we show the error messages within its frame
-->
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
<div class="spacerY">&nbsp;
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
</c:if>
<!-- Error Messages Ends -->

</div> <!-- close of contentArea -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="userAction" id="userAction" value="">
<input type="hidden" name="printProjectIdList" id="printProjectIdList" value="">
<input type="hidden" name="startSearchTime" id="startSearchTime" value=""/>       

</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

<!--Uncomment for production-->
</tcmis:form>
</body>
</html:html>