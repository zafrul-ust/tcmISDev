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
<script type="text/javascript" src="/js/ray/workareausage.js"></script>

<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>


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
<fmt:message key="materialsused.title"/>
</title>
<script type="text/javascript">
<!--
var altfacilityId = new Array(
<c:forEach var="facAppUserGrpOvBean" items="${facAppUserGrpOvBeanColl}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"${status.current.facilityId}"
   </c:when>
   <c:otherwise>
    "${status.current.facilityId}"
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
altapplication["${currentFacilityId}"] = new Array(
 <c:forEach var="applicationObjBean" items="${applicationObjBeanCollection}" varStatus="status1">
  <c:choose>
   <c:when test="${applicationCount > 0}">
    ,"${status1.current.application}"
   </c:when>
   <c:otherwise>
    "${status1.current.application}"
   </c:otherwise>
  </c:choose>
  <c:set var="applicationCount" value='${applicationCount+1}'/>
  </c:forEach>
  );

<c:set var="applicationCount" value='${0}'/>
altapplicationdesc["${currentFacilityId}"] = new Array(
 <c:forEach var="applicationObjBean" items="${applicationObjBeanCollection}" varStatus="status1">
  <c:choose>
   <c:when test="${applicationCount > 0}">
    ,"${status1.current.applicationDesc}"
   </c:when>
   <c:otherwise>
    "${status1.current.applicationDesc}"
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
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
selectFacility:"<fmt:message key="msg.selectFacility"/>",selectWorkarea:"<fmt:message key="msg.selectWorkarea"/>",
pleaseSelect:"<fmt:message key="label.pleaseselect"/>"
};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="setSearchFrameSize();">

<!--Uncomment for production-->
<tcmis:form action="/workareausageresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

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
    <option value="${currentFacilityId}" selected>${status.current.facilityName}</option>
   </c:when>
   <c:otherwise>
    <option value="${currentFacilityId}">${status.current.facilityName}</option>
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

<input class="inputBox" type="text" name="dateDeliveredStart" id="dateDeliveredStart" value="${formattedStartDate}" size="8" maxlength="10"><a href="javascript: void(0);" id="linkdateDeliveredStart" onClick="return getCalendar(document.genericForm.dateDeliveredStart);" class="datePopUpLink">&diams;</a>
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
    <option value="${currentApplication}" selected="selected>${status.current.applicationDesc}</option>    
   </c:when>
   <c:otherwise>
    <option value="${currentApplication}">${status.current.applicationDesc}</option>
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

<input class="inputBox" type="text" name="dateDeliveredEnd" id="dateDeliveredEnd" value="${formattedEndDate}" size="8" maxlength="10" class="optionTitleBoldLeft"><a href="javascript: void(0);" id="linkdateDeliveredEnd" onClick="return getCalendar(document.genericForm.dateDeliveredEnd);" class="datePopUpLink">&diams;</a>
</td>

<tr>
<td width="5%" colspan="4" class="optionTitleLeft">
<html:submit property="submitSearch" styleId="submitSearch" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "return search()">
     <fmt:message key="label.search"/>
</html:submit>

<html:submit property="submitCreateReport" styleId="submitCreateReport" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "doexcelpopup();return false;">
     <fmt:message key="label.createexcelfile"/>
</html:submit>
</td>
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
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

<!--Uncomment for production-->
</tcmis:form>
</body>
</html:html>