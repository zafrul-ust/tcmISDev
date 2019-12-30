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
<%@ include file="/common/locale.jsp" %>

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
<script type="text/javascript" src="/js/hub/packconfirmshipment.js"></script>

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
    validvalues:"<fmt:message key="label.validvalues"/>",
    dateshipped:"<fmt:message key="label.dateshipped"/>",
    pleaseconfirmshipeddate:"<fmt:message key="label.pleaseconfirmshipeddate"/>",
    missingSerialNumber:"<fmt:message key="label.missingserialnumber"/>",
    trackingNumber:"<fmt:message key="label.trackingnumber"/>",
    shipConfirmed:"<fmt:message key="label.shipconfirmed"/>",
    invalidDateFormat:"<fmt:message key="error.date.invalid"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="myResultOnload()">

<tcmis:form action="/packconfirmshipment.do" onsubmit="return submitOnlyOnce();">

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
</div>
<!-- Error Messages Ends -->
<c:set var="hasPermission" value='true'/>
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
  <tcmis:facilityPermission indicator="true" userGroupId="shipConfirm" facilityId="${param.sourceHub}">
    <c:set var="hasPermission" value='true'/>
  </tcmis:facilityPermission>
<c:if test="${hasPermission == 'true'}">
	<c:set var="deliveredDate" value="${param.deliveredDate}"/>
	<!--<c:if test="${empty deliveredDate}">
		<c:set var="deliveredDate">
			<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>
		</c:set>
	</c:if>
	-->
	<fmt:message key="label.dateshipped"/>
	<input class="inputBox pointer" name="deliveredDate" id="deliveredDate" type="text" value="" size="8" readonly="true" maxlength="10" onClick="return getCalendar(document.genericForm.deliveredDate);"/>

	 <br>
	 <br>
	<a href="#" onclick="confirmShipment(); return false;"><fmt:message key="label.confirmshipment"/> & <fmt:message key="label.returntomain"/></a> |
	<a href="#" onclick="cancel(); return false;"><fmt:message key="label.cancel"/></a>
</c:if>
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
    <th width="1%"><fmt:message key="label.confirm"/>
      <input type="checkbox" value="" onClick="checkall(${resultSize});" name="chkAll" id="chkAll"></th>
    <th width="5%"><fmt:message key="label.facility"/></th>
    <th width="15%"><fmt:message key="label.carrier"/></th>
    <th width="15%"><fmt:message key="label.trackingnumber"/></th>
    <th width="15%"><fmt:message key="label.manifestid"/></th>
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
     <td width="1%" align="center">
      <c:if test="${hasPermission == 'true'}">
       <input name="packShipConfirmInputBean[<c:out value="${status.index}"/>].selected" id="checkbox<c:out value="${status.index}"/>" type="checkbox" onclick="checkSerialNumber(${status.index})" value="<c:out value="${status.index}"/>">
      </c:if>
     </td>
        <td width="15%"><c:out value="${status.current.facilityId}"/></td>
     <td width="15%"><c:out value="${status.current.carrierName}"/></td>
	 <td width="15%"><c:out value="${status.current.trackingNumber}"/></td>

	 <c:set var="tmpFedexParcelHazardousDocId" value=''/>
	 <c:set var="tmpHazardous" value=''/>
	 <c:if test="${param.transportationMode == 'parcel' || param.transportationMode == 'Parcel'}">
		<c:set var="tmpFedexParcelHazardousDocId" value="${status.current.fedexParcelHazardousDocId}"/>
		<c:set var="tmpHazardous" value="${status.current.hazardous}"/>
	 </c:if>

	 <td width="15%"><c:out value="${tmpFedexParcelHazardousDocId}"/></td>
	   <input type="hidden" name="packShipConfirmInputBean[<c:out value="${status.index}"/>].carrierCode" id="carrierCode<c:out value="${status.index}"/>" value="${status.current.carrierCode}"/>
	   <input type="hidden" name="packShipConfirmInputBean[<c:out value="${status.index}"/>].leadTrackingNumber" id="leadTrackingNumber<c:out value="${status.index}"/>" value="${status.current.trackingNumber}"/>
       <input type="hidden" name="packShipConfirmInputBean[<c:out value="${status.index}"/>].facilityId" id="facilityId<c:out value="${status.index}"/>" value="${status.current.facilityId}"/>
       <input type="hidden" name="packShipConfirmInputBean[<c:out value="${status.index}"/>].hazardous" id="hazardous<c:out value="${status.index}"/>" value="${tmpHazardous}"/>
       <input type="hidden" name="packShipConfirmInputBean[<c:out value="${status.index}"/>].fedexParcelHazardousDocId" id="fedexParcelHazardousDocId<c:out value="${status.index}"/>" value="${tmpFedexParcelHazardousDocId}"/>
       <input type="hidden" name="packShipConfirmInputBean[<c:out value="${status.index}"/>].trackSerialNumber" id="trackSerialNumber<c:out value="${status.index}"/>" value="${status.current.trackSerialNumber}"/>
       <input type="hidden" name="packShipConfirmInputBean[<c:out value="${status.index}"/>].missingSerialNumber" id="missingSerialNumber<c:out value="${status.index}"/>" value="${status.current.missingSerialNumber}"/>

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
  <input name="rowCount" id="rowCount" type="hidden" value="${resultSize}"/>
	<input name="action" id="action" type="hidden" value=""/>
	<input name="sourceHub" id="sourceHub" value='<tcmis:jsReplace value="${param.sourceHub}"/>' type="hidden"/>
	<input name="transportationMode" id="transportationMode" value='<tcmis:jsReplace value="${param.transportationMode}"/>' type="hidden"/>
	<input name="updateErrorFlag" id="updateErrorFlag" value="${updateErrorFlag}" type="hidden"/>
	<input name="personnelId" id="personnelId" type="hidden" value="${personnelBean.personnelId}"/>
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
</div>

<script type="text/javascript">
<!--
YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", initWin);
//-->
</script>

</body>
</html:html>

