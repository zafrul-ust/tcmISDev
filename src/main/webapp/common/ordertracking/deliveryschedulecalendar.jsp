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
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
<link rel="stylesheet" href="/css/calendar.css" type="text/css" />
<!-- Add any other stylesheets you need for the page here -->

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<script type="text/javascript" src="/js/common/resultiframeresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<%--
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>
--%>

<!-- Add any other javascript you need for the page here -->
<script src="/js/common/ordertracking/deliveryschedule.js" language="JavaScript"></script>

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

<%--
<title>
<fmt:message key="pagename.title"/>
</title>
--%>

<%-- global variables --%>
<c:set var="showSummary" value="${showSummary}"/>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
dataChanged:"<fmt:message key="label.dataChanged"/>",
frequencyschedulewarn:"<fmt:message key="label.frequencyschedulewarn"/>",      
itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="myOnload('calendar')" >

<tcmis:form action="/deliveryscheduleresults.do" onsubmit="return submitFrameOnlyOnce();">

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->
<c:set var="userViewType" value="${userViewType}"/>

<c:set var="userViewType" value="${userViewType}"/>

<script type="text/javascript">
<!--
  <c:choose>
   <c:when test="${userViewType == 'user' || userViewType == 'alternate' || userViewType == 'editMRQty' || userViewType == 'approverEditMRQty'}" >
       showUpdateLinks = true;
   </c:when>
   <c:otherwise>
       showUpdateLinks = false;
   </c:otherwise>
  </c:choose>
//-->
</script>

<!-- show calendar -->
<script type="text/javascript">
<!--
  showCalendarLink = false;
//-->
</script>

<!-- show summary -->
<script type="text/javascript">
<!--
  <c:choose>
   <c:when test="${showSummary == 'y'}" >
       showSummaryLink = true;
   </c:when>
   <c:otherwise>
       showSummaryLink = false;
   </c:otherwise>
  </c:choose>

var windowCloseOnEsc = true;
//-->
</script>

<script type="text/javascript">
function nextMonthLinkOnclick(date) {
    var action = document.getElementById("action");
    action.value = 'showCalendarNext';
    document.genericForm.target='resultFrame';
    var calendarDate = document.getElementById("calendarDate");
    calendarDate.value = date;
    parent.showPleaseWait();
    var a = window.setTimeout("document.genericForm.submit();",2);
}

function previousMonthLinkOnclick(date) {
    var action = document.getElementById("action");
    action.value = 'showCalendarPervious';
    document.genericForm.target='resultFrame';
    var calendarDate = document.getElementById("calendarDate");
    calendarDate.value = date;
    parent.showPleaseWait();
    var a = window.setTimeout("document.genericForm.submit();",2);
}

function calendarInputFocusLost(date) {
  var val = document.getElementById('calendarInput' + date);
  var inputCellDiv = document.getElementById('inputCellDiv' + date);
  inputCellDiv.style.visibility = 'hidden';
  var inputLabelCellDiv = document.getElementById('inputLabelCellDiv' + date);
  var cellDiv = document.getElementById('cellDiv' + date);

  var diffQty = 0;
  //first calculate diff of qty
  if(val != null && val.value != '' && isFloat(val.value)) {
    diffQty = val.value*1;
    var userChangedData = document.getElementById("userChangedData");
    userChangedData.value = "y";
  }
  if (cellDiv.innerHTML != null && cellDiv.innerHTML != '' && isFloat(cellDiv.innerHTML)) {
    diffQty = diffQty - cellDiv.innerHTML*1;
    var userChangedData = document.getElementById("userChangedData");
    userChangedData.value = "y";
  }

  //set value in div
  if(val != null && val.value != '' && isFloat(val.value) && val.value != '0') {
    cellDiv.innerHTML = '' + val.value;
    cellDiv.style.visibility = 'visible';
    inputLabelCellDiv.style.visibility = 'visible';
  }
  else {
    inputLabelCellDiv.style.visibility = 'hidden';
    cellDiv.innerHTML = '';
    val.value = '';
  }

  var qty = parent.window.frames["searchFrame"].document.getElementById("scheduledQty");
  qty.innerHTML = qty.innerHTML*1 + diffQty;

  return false;
}

function dateCellClicked(date) {
  var userViewType = document.getElementById('userViewType');
  if (userViewType.value == "user" || userViewType.value == "alternate" || userViewType.value == "editMRQty" || userViewType.value == "approverEditMRQty") {
    var today = new Date();
    var calendarDate=new Date();
    <fmt:parseDate value="${param.calendarDate}" pattern="MM/dd/yyyy" var="myDate"/>
    <c:choose>
      <c:when test="${myDate != null && !empty myDate}">
        calendarDate.setYear(<fmt:formatDate value="${myDate}" pattern="yyyy"/>);
        calendarDate.setMonth(<fmt:formatDate value="${myDate}" pattern="MM"/> -1);
        calendarDate.setDate(date);
      </c:when>
      <c:otherwise>
        calendarDate.setDate(date);
      </c:otherwise>
    </c:choose>
    if(today<calendarDate) {
      var inputCellDiv = document.getElementById('inputCellDiv' + date);
      inputCellDiv.style.visibility = 'visible';
      var calendarInput = document.getElementById("calendarInput" + date);
      calendarInput.focus();
    }
  }
  return false;
}

//-->
</script>


<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
 <html:errors/>
</div>

<script type="text/javascript">
<!--
/*YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);*/

/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
//-->
</script>
<!-- Error Messages Ends -->

<div class="interface" id="resultsPage">
<div class="backGroundContent">

 <c:set var="dataCount" value='${4}'/>
 <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
 <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
   <tr><td>
    <tcmis:calendar tableId="foo"
                tableClass="calendarTable"
                availableDayClass="availableDayClass"
                unavailableDayClass="unavailableDayClass"
                displayDate="${displayDate}"
                dateDisplayMap="dateDisplayMap"
                blackoutDayCollection="blackOutDayColl"
                cellWidth="14%"
                headerDateFormat="MMMM yyyy"
                weekdayFormat="EEEE"
                previousMonthLink="#"
                nextMonthLink="#"
                inputLabel="QTY:"
                />
   </td></tr>
 </table>

<!-- Search results end -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="action" id="action" type="hidden" value="">
<input name="calendarDate" id="calendarDate" value="<c:out value="${displayDate}"/>" type="hidden">
<input name="lastDisplayCalendarDate" id="lastDisplayCalendarDate" value="<c:out value="${displayDate}"/>" type="hidden">
<input name="companyId" id="companyId" type="hidden"value="<c:out value="${companyId}"/>">
<input name="prNumber" id="prNumber" type="hidden" value="<c:out value="${param.prNumber}"/>">
<input name="lineItem" id="lineItem" type="hidden"value="<c:out value="${lineItem}"/>">
<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">
<input name="maxRowId" id="maxRowId" value="<c:out value="${dataCount}"/>" type="hidden">
<input name="totalOrderedQuantity" id="totalOrderedQuantity" value="" type="hidden">
<input name="medianMrLeadTime" id="medianMrLeadTime" value="<c:out value="${medianMrLeadTime}"/>" type="hidden">
<input name="userChangedData" id="userChangedData" value="<c:out value="${userChangedData}"/>" type="hidden">
<input name="source" id="source" type="hidden"value="<c:out value="${param.source}"/>">
<input name="requestLineStatus" id="requestLineStatus" type="hidden" value="${param.requestLineStatus}"/>
<input name="requestor" id="requestor" type="hidden" value="${requestor}"/>
<input name="lastAction" id="lastAction" type="hidden" value="${param.action}"/>
<input name="calendarAction" id="calendarAction" type="hidden" value="calendar"/>
<%--
<input name="requestLineStatus" id="requestLineStatus" value="<c:out value="${requestLineStatus}"/>" type="hidden">
<input name="facilityId" id="facilityId" value="<c:out value="${facilityId}"/>" type="hidden"> 
<input name="requestor" id="requestor" value="<c:out value="${requestor}"/>" type="hidden">
<input name="userViewType" id="userViewType" value="<c:out value="${userViewType}"/>" type="hidden">
--%>
<input name="facilityId" id="facilityId" type="hidden" value="${param.facilityId}"/> 
<input name="requestor" id="requestor" type="hidden" value="${param.requestor}"/>
<input name="userViewType" id="userViewType" type="hidden" value="${param.userViewType}"/> 
<input name="requestedDateToDeliver0" id="requestedDateToDeliver0" type="hidden" value='${requestedDateToDeliver0}'/> 

<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<%--
<tcmis:saveRequestParameter/>

<input name="requestedDateToDeliver0" id="requestedDateToDeliver0" type="hidden" value='<fmt:formatDate value="${requestedDateToDeliver0}" pattern="${dateFormatPattern}"/>'/> 
--%>

<%--This is the minimum height of the result section you want to display--%>
<input name="minHeight" id="minHeight" type="hidden" value="200">

 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>