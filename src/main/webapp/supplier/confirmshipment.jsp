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
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />

<%-- Add any other stylesheets you need for the page here --%>

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<script type="text/javascript" src="/js/common/iframeresizemain.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/supplier/confirmshipment.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<!-- This is for the YUI, uncomment if you will use this -->
<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>
	
<title>
<fmt:message key="shipmentconfirm.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
noRowChecked:"<fmt:message key="error.norowselected"/>",
invalidDateFormat:"<fmt:message key="error.date.invalid"/>",
trackingNumberRequired:"<fmt:message key="label.trackingnumberrequired"/>",
datesrequired:"<fmt:message key="label.datesrequired"/>"
};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="myResultOnload();">

<tcmis:form action="/confirmresults.do" onsubmit="return submitOnlyOnce();">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
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

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td width="70%" class="headingl">
<fmt:message key="shipmentconfirm.title"/>
</td>
<td width="30%" class="headingr">
&nbsp;
</td>
</tr>
</table>
</div>

<div class="contentArea">

<div class="spacerY">&nbsp;</div>

<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
${tcmisError}
</div>
<!-- Error Messages Ends -->

<c:set var="hasPermission" value=''/>
<tcmis:supplierLocationPermission indicator="true" userGroupId="usGovShipConfirm" supplierId="${param.supplier}">
 <script type="text/javascript">
 <!--
  <c:set var="hasPermission" value='true'/>
 //-->
</script>
</tcmis:supplierLocationPermission>


<c:if test="${packShipmentBeanCollection != null}" >
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your mail table will be 100% -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead">
<span id="updateResultLink" style="display:">
	<%--
  <tcmis:facilityPermission indicator="true" userGroupId="Picking QC" facilityId="${status.current.branchPlant}">
    <c:set var="hasPermission" value='true'/>  
  </tcmis:facilityPermission>
  --%>
<c:if test="${hasPermission == 'true'}">
<%--The label said date shipped but the data being passed back was date delivered. -LARRY--%>  
<%--	<c:if test="${empty dateShipped}">
		<c:set var="dateShipped">
			<tcmis:getDateTag numberOfDaysFromToday="0"/>
		</c:set>
	</c:if>--%>
	<fmt:message key="label.dateshipped"/>:&nbsp;&nbsp;
	<input class="inputBox" readonly="ture" name="dateShipped" id="dateShipped" type="text" value="${dateShipped}" size="6" onclick="return getCalendar(document.getElementById('dateShipped'),null,document.getElementById('deliveredDate'));">
	<a href="javascript: void(0);" ID="linkdateShipped" class="datePopUpLink">&nbsp;</a>
	<c:set var="deliveredDate" value="${param.deliveredDate}"/>
	<c:if test="${empty deliveredDate}" >
		<c:set var="deliveredDate" value="${param.dateDelivered}"/>
	</c:if>
	<fmt:message key="label.projecteddeliverydate"/>:&nbsp;&nbsp;
	<input class="inputBox" readonly="ture" name="dateDelivered" id="deliveredDate" type="text" value="${deliveredDate}" size="6" onclick="return getCalendar(document.getElementById('deliveredDate'),null,null,document.getElementById('dateShipped'));">
	<a href="javascript: void(0);" ID="linkdeliveredDate" class="datePopUpLink">&nbsp;</a>
	<c:set var="dateShipped" value="${param.dateShipped}"/>

</c:if>
	 <br>
	 <br>
<%-- 	<a href="#" onclick="udpate(); return false;"><fmt:message key="label.update"/></a> |
--%>
	<a href="#" onclick="confirmShipment(); return false;"><fmt:message key="label.confirmshipment"/> & <fmt:message key="label.returntomain"/></a> |
	<a href="#" onclick="cancel(); return false;"><fmt:message key="label.cancel"/></a>
  &nbsp;
</span>
</div>

    <div class="dataContent">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
    <c:set var="colorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>
		<bean:size collection="${packShipmentBeanCollection}" id="resultSize"/>
    <c:forEach var="shipmentBean" items="${packShipmentBeanCollection}" varStatus="status">
    <c:set var="dataCount" value='${dataCount+1}'/>

    <c:if test="${status.index % 10 == 0}">
    <!-- Need to print the header every 10 rows-->
    <tr>
    <th width="2%"><fmt:message key="label.confirm"/></th>
    <th width="2%"><fmt:message key="label.shipmentid"/></th>
    <th width="10%"><fmt:message key="label.supplierlocation"/></th>
    <th width="15%"><fmt:message key="label.carrier"/></th>
    <th width="15%"><fmt:message key="label.trackingnumber"/></th>
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
     <td width="2%" align="center">
			<input name="packShipConfirmInputBean[<c:out value="${status.index}"/>].ok" id="checkbox${status.index}" type="checkbox" value="ok" onClick="okClick(${status.index})">
     </td>
       <td width="2%">${status.current.currentShipmentId}</td>
       <td width="10%"> ${status.current.shipFromLocationName} </td>
       <td width="15%">${status.current.currentCarrierName}</td>
	   <td width="15%">
	   		<input type="hidden" class="inputBox" name="packShipConfirmInputBean[${status.index}].currentTrackingNumber" id="currentTrackingNumber${status.index}" value="${status.current.currentTrackingNumber}" size="7" onchange="valueChanged('currentTrackingNumber_${status.index}')"/>
        ${status.current.currentTrackingNumber}
     </td>
	   <input type="hidden" name="packShipConfirmInputBean[${status.index}].currentShipmentId"     id="currentShipmentId${status.index}"     value="${status.current.currentShipmentId}"/>
	   <input type="hidden" name="packShipConfirmInputBean[${status.index}].currentCarrierName"    id="currentCarrierName${status.index}"    value="${status.current.currentCarrierName}"/>
   </tr>
   </c:forEach>
   </table>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty packShipmentBeanCollection}" >

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
</td></tr>
</table>
<!-- Search results end -->
</c:if>

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;">
	<input type="hidden" name="rowCount" id="rowCount" value="${resultSize}"/>
	<input type="hidden" name="userAction" id="userAction" value="">
	<input type="hidden" name="transportationMode" id="transportationMode" value="${param.transportationMode}"/>
	<input type="hidden" name="updateErrorFlag" id="updateErrorFlag" value="${updateErrorFlag}"/>
	<input type="hidden" name="personnelId" id="personnelId" type="hidden" value="${personnelBean.personnelId}"/>
<input type="hidden" name="dataChanged" id="dataChanged" value="${param.dataChanged}"/>
<input name="supplier" id="supplier" type="hidden" value="${param.supplier}">
<input name="shipFromLocationId" id="shipFromLocationId" type="hidden" value="${param.shipFromLocationId}">
<input name="oldshipFromLocationId" id="oldshipFromLocationId" type="hidden" value="${param.oldshipFromLocationId}">
<input name="oldsupplier" id="oldsupplier" type="hidden" value="${param.oldsupplier}">
<input name="transportationMode" id="transportationMode" type="hidden" value="${param.transportationMode}">
<input name="searchArgument" id="searchArgument" type="hidden" value="${param.searchArgument}">
<input name="searchMode" id="searchMode" type="hidden" value="${param.searchMode}">
<input name="searchField" id="searchField" type="hidden" value="${param.searchField}">
<c:set var="selectedsuppLocationIdArray" value='${paramValues.suppLocationIdArray}'/>
<select name="suppLocationIdArray" id="suppLocationIdArray" multiple size="5">
<c:forEach items="${selectedsuppLocationIdArray}" varStatus="status1">
 <c:set var="selectedLocation" value='${selectedsuppLocationIdArray[status1.index]}'/>
  <option value="${selectedLocation}">${selectedLocation}</option>
</c:forEach>  
</select>
 </div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->

</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
<!-- Error Messages Begins -->
<div id="confirmErrorMsgArea" class="errorMessages" style="display:none;z-index:2;">
<div id="confirmErrorMsgAreaTitle" class="hd"><fmt:message key="label.errors"/></div>
<div id="confirmErrorMsgAreaBody" class="bd">
<%-- display error message to user --%>
${messageToUser}
</div>
<input name="okC" id="okC" type="button" value="<fmt:message key="label.ok"/>" onClick="return okCClick()" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'"/>
</div>

<script type="text/javascript">
<!--
YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", initWin);
//-->
</script>

</body>
</html:html>

