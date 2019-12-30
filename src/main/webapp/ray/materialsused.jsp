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
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/ray/materialsused.js"></SCRIPT>

<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>


<title>
<fmt:message key="materialsused.title"/>
</title>
<script type="text/javascript">
<!--
var altfacilityId = new Array(
<c:forEach var="facAppUserGrpOvBean" items="${facAppUserGrpOvBeanColl}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"<c:out value="${status.current.facilityId}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status.current.facilityId}"/>"
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altapplication = new Array();
var altapplicationdesc = new Array();
<c:forEach var="facAppUserGrpOvBean" items="${facAppUserGrpOvBeanColl}" varStatus="status">
<c:set var="currentFacilityId" value='${status.current.facilityId}'/>
<c:set var="applicationObjBeanCollection" value='${status.current.applicationVar}'/>

<c:set var="applicationCount" value='${0}'/>
altapplication["<c:out value="${currentFacilityId}"/>"] = new Array(
 <c:forEach var="applicationObjBean" items="${applicationObjBeanCollection}" varStatus="status1">
  <c:choose>
   <c:when test="${applicationCount > 0}">
    ,"<c:out value="${status1.current.application}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status1.current.application}"/>"
   </c:otherwise>
  </c:choose>
  <c:set var="applicationCount" value='${applicationCount+1}'/>
  </c:forEach>
  );

<c:set var="applicationCount" value='${0}'/>
altapplicationdesc["<c:out value="${currentFacilityId}"/>"] = new Array(
 <c:forEach var="applicationObjBean" items="${applicationObjBeanCollection}" varStatus="status1">
  <c:choose>
   <c:when test="${applicationCount > 0}">
    ,"<c:out value="${status1.current.applicationDesc}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status1.current.applicationDesc}"/>"
   </c:otherwise>
  </c:choose>
  <c:set var="applicationCount" value='${applicationCount+1}'/>
  </c:forEach>
  );
 </c:forEach>
// -->
</script>
<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>
</head>

<c:set var="doexcelpopup" value='${doexcelpopup}'/>
<c:set var="submitCreateReport" value='${param.submitCreateReport}'/>
<c:set var="submitSearch" value='${param.submitSearch}'/>

<c:choose>
  <c:when test="${!empty doexcelpopup && !empty submitCreateReport}" >
    <body bgcolor="#ffffff" onLoad="doexcelpopup()">
  </c:when>
  <c:otherwise>
<body bgcolor="#ffffff">
  </c:otherwise>
</c:choose>

<tcmis:form action="/materialsused.do" onsubmit="return submitOnlyOnce();">

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

<%@ include file="title.jsp" %>

</div>

<div class="contentArea">

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
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr>
<td width="5%" class="optionTitleBoldRight">
<fmt:message key="label.facility"/>:
</td>

<td width="15%">
<c:set var="selectedFacilityId" value='${param.facilityId}'/>
<select class="selectBox" name="facilityId" id="facilityId" onchange="facilityIdChanged()">
  <c:forEach var="facAppUserGrpOvBean" items="${facAppUserGrpOvBeanColl}" varStatus="status">
  <c:set var="currentFacilityId" value='${status.current.facilityId}'/>

  <c:choose>
   <c:when test="${empty selectedFacilityId}" >
    <c:set var="selectedFacilityId" value='${status.current.facilityId}'/>
    <c:set var="applicationObjBeanJspCollection" value='${status.current.applicationVar}'/>
    <c:set var="userGroupObjBeanJspCollection" value='${status.current.userGroupVar}'/>
    <c:set var="facilityDockObjBeanJspCollection" value='${status.current.facDockVar}'/>
   </c:when>
   <c:when test="${currentFacilityId == selectedFacilityId}" >
    <c:set var="applicationObjBeanJspCollection" value='${status.current.applicationVar}'/>
    <c:set var="userGroupObjBeanJspCollection" value='${status.current.userGroupVar}'/>
    <c:set var="facilityDockObjBeanJspCollection" value='${status.current.facDockVar}'/>
   </c:when>
  </c:choose>

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

<td width="5%" class="optionTitleBoldRight">
<fmt:message key="materialsused.label.begin"/>:
</td>

<td width="5%" class="optionTitleLeft">
<c:choose>
 <c:when test="${param.dateDeliveredStart == null || empty param.dateDeliveredStart}">
  <fmt:formatDate var="formattedStartDate" value="${defaultStart}" pattern="MM/dd/yyyy"/>
 </c:when>
 <c:otherwise>
  <c:set var="formattedStartDate" value='${param.dateDeliveredStart}'/>
 </c:otherwise>
</c:choose>

<input class="inputBox" type="text" name="dateDeliveredStart" id="dateDeliveredStart" value="<c:out value="${formattedStartDate}"/>" size="8" maxlength="10"><a href="javascript: void(0);" id="linkdateDeliveredStart" onClick="return getCalendar(document.genericForm.dateDeliveredStart);" class="datePopUpLink">&diams;</a>
</td>

</tr>

<tr>
<td width="5%" class="optionTitleBoldRight">
<fmt:message key="label.workarea"/>:
</td>

<td width="15%">
<c:set var="selectedApplication" value='${param.application}'/>
<c:set var="applicationCount" value='${0}'/>
<select class="selectBox" name="application" id="application" onchange="applicationChanged()">
 <option value="All"><fmt:message key="label.pleaseselect"/></option>
  <c:forEach var="applicationObjBean" items="${applicationObjBeanJspCollection}" varStatus="status">
  <c:set var="applicationCount" value='${applicationCount+1}'/>
  <c:set var="currentApplication" value='${status.current.application}'/>

  <c:choose>
   <c:when test="${selectedApplication == currentApplication}">
    <option value="<c:out value="${currentApplication}"/>" selected><c:out value="${status.current.applicationDesc}"/></option>
   </c:when>
   <c:otherwise>
    <option value="<c:out value="${currentApplication}"/>"><c:out value="${status.current.applicationDesc}"/></option>
   </c:otherwise>
  </c:choose>
  </c:forEach>
</select>
</td>

<td width="5%" class="optionTitleBoldRight">
<fmt:message key="materialsused.label.end"/>:
</td>

<td class="optionTitleLeft">
<c:choose>
 <c:when test="${param.dateDeliveredEnd == null || empty param.dateDeliveredEnd}">
  <fmt:formatDate var="formattedEndDate" value="${defaultEndDate}" pattern="MM/dd/yyyy"/>
 </c:when>
 <c:otherwise>
  <c:set var="formattedEndDate" value='${param.dateDeliveredEnd}'/>
 </c:otherwise>
</c:choose>

<input class="inputBox" type="text" name="dateDeliveredEnd" id="dateDeliveredEnd" value="<c:out value="${formattedEndDate}"/>" size="8" maxlength="10" class="optionTitleBoldLeft"><a href="javascript: void(0);" id="linkdateDeliveredEnd" onClick="return getCalendar(document.genericForm.dateDeliveredEnd);" class="datePopUpLink">&diams;</a>
</td>

<tr>
<td width="5%" colspan="4" class="optionTitleLeft">
<html:submit property="submitSearch" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "return search()">
     <fmt:message key="label.search"/>
</html:submit>

<html:submit property="submitCreateReport" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "return update()">
     <fmt:message key="label.createexcelfile"/>
</html:submit>
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

<c:if test="${materialsUsedViewBeanCollection != null}" >
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"></div>
    <div class="dataContent">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
<c:forEach var="materialsUsedViewBean" items="${materialsUsedViewBeanCollection}" varStatus="status">
<c:if test="${status.index % 10 == 0}">
<tr>
<th width="5%"><fmt:message key="label.partnumber"/></th>
<th width="10%"><fmt:message key="label.materialdesc"/></th>
<th width="5%"><fmt:message key="label.manufacturer"/></th>
<th width="10%"><fmt:message key="label.packaging"/></th>
<th width="2%"><fmt:message key="label.materialid"/></th>

</tr>
</c:if>

<c:choose>
   <c:when test="${status.index % 2 == 0}" >
    <c:set var="colorClass" value='='/>
   </c:when>
   <c:otherwise>
    <c:set var="colorClass" value='alt'/>
   </c:otherwise>
</c:choose>

<c:set var="kitCollection"  value='${status.current.kitCollection}'/>
<bean:size id="listSize" name="kitCollection"/>
<c:set var="mainRowSpan" value='${status.current.rowSpan}' />

<tr class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>">

<td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.catPartNo}"/></td>

<c:forEach var="materialsUsedViewBean" items="${kitCollection}" varStatus="kitstatus">
 <c:if test="${kitstatus.index > 0 && listSize > 1 }">
  <tr class="<c:out value="${colorClass}"/>" id="childRowId<c:out value="${status.index}"/><c:out value="${kitstatus.index}"/>">
 </c:if>

 <td width="10%" class="alighLeft"><c:out value="${kitstatus.current.materialDesc}"/></td>
 <c:if test="${kitstatus.index == 0}">
  <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>" class="optionTitleLeft"><c:out value="${status.current.mfgDesc}"/></td>
 </c:if>
<td width="10%" class="alighLeft"><c:out value="${kitstatus.current.packaging}"/></td>
<td width="2%">
<a href="javascript:showMsds('<c:out value="${kitstatus.current.materialId}"/>','<c:out value="${status.current.facilityId}"/>')" class="datePopUpLink"><c:out value="${kitstatus.current.materialId}"/></a>
</td>

</c:forEach>
</tr>
<c:set var="dataCount" value='${dataCount+1}'/>
</c:forEach>
</table>

   <!-- If the collection is empty say no data found -->
   <c:if test="${empty materialsUsedViewBeanCollection}" >
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
 <div id="hiddenElements" style="display: none;"></div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>