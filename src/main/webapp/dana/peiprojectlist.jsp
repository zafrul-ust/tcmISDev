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
<%-- this version based on template.jsp --%>

<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
<%--<link rel="stylesheet" type="text/css" href="/css/clientpages.css"> --%>

<%--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. --%>
<tcmis:fontSizeCss />

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<%-- This handles which key press events are disabeled on this page --%>
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<%-- This handles the menu style and what happens to the right click on the whole page --%>
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>


<%-- Add any other javascript you need for the page here --%>
<script type="text/javascript" src="/js/dana/peiprojects.js" ></script>

<title>
<fmt:message key="peiproject.label.projectlist"/>
</title>

<script language="JavaScript" type="text/javascript">

<%-- add all the javascript messages here, this for internationalization of client side javascript messages --%>
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
</script>
</head>

<body bgcolor="#ffffff">


<tcmis:form action="/peiprojectlist.do" onsubmit="return submitOnlyOnce();">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
 </div>
 <div class="interface" id="mainPage">

<%-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure --%>
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

<%@ include file="/common/clientnavigation.jsp" %>

</div>

<div class="contentArea">

<%-- Search Option Begins --%>
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <%-- Insert all the search option within this div --%>


<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">

<tr>

<td width="5%" rowspan="2" class="optionTitleBoldRight">
	<fmt:message key="label.status"/>:
</td>

<td width="5%" rowspan="2" class="optionTitleLeft">
<html:select property="statusCollectionSelect" styleId="statusCollectionSelect" styleClass="selectBox" size="4" multiple="true">
<html:optionsCollection name="peiProjectInputBean" property="statusCollection" value="projectStatus" label="projectStatusDesc"/>
</html:select>
</td>

<td width="5%" rowspan="2" class="optionTitleBoldRight">
	<fmt:message key="label.category"/>:
</td>

<td width="5%" rowspan="2" class="optionTitleLeft">
<html:select property="categoryCollectionSelect" styleId="categoryCollectionSelect" styleClass="selectBox" size="4" multiple="true">
<html:optionsCollection name="peiProjectInputBean" property="categoryCollection" value="projectCategory" label="projectCategoryDesc"/>
</html:select>

</td>

<td width="5%" rowspan="2" class="optionTitleBoldRight">
	<fmt:message key="peiproject.label.keywords"/>:
</td>

<td width="5%" rowspan="2" class="optionTitleLeft">
<html:select property="keywordsCollectionSelect" styleId="keywordsCollectionSelect" styleClass="selectBox" size="4" multiple="true">
<html:optionsCollection name="peiProjectInputBean" property="keywordsCollection" value="keywordId" label="keywordDesc"/>
<%--<html:options collection="keywordsCollection" property="keywordId" labelProperty="keywordDesc"/>--%>
</html:select>
</td>

<c:set var="keywordSearchText" value='${param.keywordSearchText}'/>
<td width="5%" class="OptionTitleBoldLeft">
<input class="inputBox" type="text" name="keywordSearchText" id="keywordSearchText" value="<c:out value="${keywordSearchText}"/>" size="20">
</td>

<td width="5%" class="optionTitleBoldLeft">
<html:submit property="submitSearch" styleId="submitSearch" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return search()">
     <fmt:message key="peiproject.button.listprojects"/>
</html:submit>
</td>
</tr>

<tr>

<td width="5%" >
<c:set var="keywordAndOrOr" value='${param.keywordAndOrOr}'/>
<c:if test="${empty keywordAndOrOr || keywordAndOrOr == ''}" >
 <c:set var="keywordAndOrOr" value='or'/>
</c:if>

<c:choose>
  <c:when test="${keywordAndOrOr == 'and'}" >
   <input id="keywordAndOrOr" type="radio" name="keywordAndOrOr" value="or">
   <fmt:message key="label.or"/>&nbsp;&nbsp;
   <input id="keywordAndOrOr" type="radio" name="keywordAndOrOr" value="and" checked>
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

<td width="5%" class="alignLeft">
<html:button property="submitNew" styleId="submitNew" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"  onclick="startNewProject()">
     <fmt:message key="peiproject.button.newproject"/>
</html:button>
</td>
</tr>

<tr>

<td width="5%" class="optionTitleBoldRight">
	<fmt:message key="peiproject.label.projectmanager"/>:
</td>

<td width="10%" class="optionTitleLeft">
<input type="hidden" name="projectManagerId" id="projectManagerId" value="<c:out value="${param.projectManagerId}"/>">
<input class="INVISIBLEHEADWHITE"	type="text" name="projectManager" id="projectManager" value="<c:out value="${param.projectManager}"/>" size="12" readonly>
<input class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" name="searchpersonnellike" value="..." onclick="getProjectManager()" type="button">
</td>

<td width="5%" class="optionTitleBoldRight">
	<fmt:message key="label.priority"/>:
</td>

<td width="5%" class="optionTitleLeft">
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

<td width="5%" class="optionTitleLeft">
<c:set var="selectedSortBy" value='${param.sortBy}'/>
<c:if test="${empty selectedSortBy || selectedSortBy == ''}" >
 <c:set var="selectedSortBy" value='PROJECT_STATUS asc'/>
</c:if>

<select class="selectBox" name="sortBy" id="sortBy" size="1">
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
<input type="checkbox" class="radioBtns" name="bestPractice" id="bestPractice" value="Y" <c:out value="${checkBoxChecked}"/>>
	<fmt:message key="peiproject.label.onlybestpractice"/>
</td>

<td width="5%" class="optionTitleBoldLeft">
<html:submit property="submitCreateReport" styleId="submitCreateReport" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
     <fmt:message key="label.createexcelfile"/>
</html:submit>
</td>

</tr>

<tr>

<td width="5%" class="optionTitleBoldRight">
	<fmt:message key="label.facility"/>:
</td>

<td width="5%" colspan="6" class="optionTitleLeft">
<c:set var="selectedFacilityId" value='${param.facilityId}'/>
<select name="facilityId" id="facilityId" class="selectBox">
  <option value="All"><fmt:message key="label.all"/></option>
  <c:forEach var="facilityBean" items="${vvProjectFacilityCollection}" varStatus="status">
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

<td width="5%" class="optionTitleBoldLeft">
<html:button property="submitPrint" styleId="submitPrint" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="printprojectlist()">
     <fmt:message key="label.print"/>
</html:button>
</td>

</tr>

</table>

   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
<%-- Search Option Ends --%>

<div class="spacerY">&nbsp;</div>

<%-- Error Messages Begins --%>
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
<%-- Error Messages Ends --%>

<c:set var="colorClass" value=''/>
<c:set var="rowCount" value='${0}'/>


<%-- * * * * * * RESULTS SECTION * * * * * *  --%>

<c:if test="${peiProjectViewListBeanCollection != null}" >
<%-- Search results start --%>
<%-- If you want your results section to span only 50% set this to 50% and your main table will be 100% --%>
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <%-- TEMPLATE EXAMPLE CODE: <div class="boxhead"> <a href="#" onclick="doSomeThing(); return false;"><fmt:message key="label.createexcelfile"/></a></div> --%>
    <div class="dataContent">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
    <c:set var="colorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>
												<%-- * * * * * * START OF RESULTS FOR-EACH BLOCK * * * * * * --%>

<c:forEach var="peiProjectViewBean" items="${peiProjectViewListBeanCollection}" varStatus="status">
<c:set var="rowCount" value='${rowCount+1}'/>
<c:if test="${status.index % 10 == 0}">
<tr align="center">
<th width="2%" ><fmt:message key="label.print"/></th>
<th width="5%" ><fmt:message key="label.category"/></th>
<th width="2%" ><fmt:message key="peiproject.label.bestpractice"/></th>
<th width="2%" ><fmt:message key="label.id"/></th>
<th width="10%" ><fmt:message key="peiproject.label.projectname"/></th>
<th width="15%" ><fmt:message key="peiproject.label.projectdesc"/></th>
<th width="5%" ><fmt:message key="label.facility"/></th>
<th width="5%" ><fmt:message key="peiproject.label.projectmanager"/></th>
<th width="5%" ><fmt:message key="peiproject.label.proposeddate"/><fmt:message key="label.approveddate"/></th>
<th width="5%" ><fmt:message key="peiproject.label.gainshareduration"/></th>
<th width="5%" ><fmt:message key="peiproject.label.projectedfinishdate"/><fmt:message key="peiproject.label.actualfinishdate"/></th>
<th width="5%" ><fmt:message key="peiproject.label.projectedcost"/><fmt:message key="peiproject.label.actualcost"/></th>
<th width="5%" ><fmt:message key="peiproject.label.totalprojectedsavings"/><fmt:message key="peiproject.label.totalactualsavings"/></th>
<th width="5%" ><fmt:message key="label.status"/></th>
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


<tr class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>">
<input type="hidden" name="projectId<c:out value="${status.index}"/>" id="projectId<c:out value="${status.index}"/>" value="<c:out value="${status.current.projectId}"/>">
<td width="2%">
<c:set var="printPdf" value='${status.current.printPdf}'/>
<c:if test="${printPdf != null}" >
 <c:set var="checkBoxChecked" value='checked'/>
</c:if>
<input type="checkbox" class="radioBtns" name="printPdf<c:out value="${status.index}"/>" id="printPdf<c:out value="${status.index}"/>" value="Y" <c:out value="${checkBoxChecked}"/>>
</td>

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
<td width="2%"><c:out value="${status.current.bestPractice}"/>
</td>
<td width="2%">

<a href="javascript: showProjectDetails(<c:out value="${status.current.projectId}"/>);" style="color:#0000ff;cursor:pointer;text-decoration:underline"><c:out value="${status.current.projectId}"/></A>
<%--<A HREF="showpeiproject.do?projectId=<c:out value="${status.current.projectId}"/>" TARGET="new<c:out value="${status.index}"/>" STYLE="color:#0000ff;cursor:pointer;text-decoration:underline"><c:out value="${status.current.projectId}"/></A>--%>
</td>
<td width="10%"><c:out value="${status.current.projectName}"/></td>
<td width="15%"><c:out value="${status.current.projectDesc}"/></td>
<td width="5%"><c:out value="${status.current.facilityId}"/></td>
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
<td width="5%"><c:out value="${status.current.pojectedCost}"/><c:out value="${status.current.actualCost}"/></td>
<td width="5%"><c:out value="${status.current.totalProjectedPeriodSavings}"/><c:out value="${status.current.totalActualPeriodSavings}"/></td>
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
</c:forEach>

	 					<%-- * * * * * * END OF RESULTS FOR-EACH BLOCK * * * * * * --%>

   </table>
									<%-- * * * * * * WRAP-UP of SEARCH RESULTS SECTION * * * * * *  --%>
   <%-- If the collection is empty say no data found --%>
   <c:if test="${empty peiProjectViewListBeanCollection}" >

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

</c:if>								<%-- * * * * * * END OF SEARCH RESULTS SECTION * * * * * *  --%>

<%-- Hidden element start --%>
 <div id="hiddenElements" style="display: none;"></div>
<%-- Hidden elements end --%>

</div> <%-- close of contentArea --%>

<%-- Footer message start --%>
 <div class="messageBar">&nbsp;</div>
<%-- Footer message end --%>

</div> <%-- close of interface --%>

</tcmis:form>
</body>
</html:html>
