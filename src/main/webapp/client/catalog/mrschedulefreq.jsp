<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html>

<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<%@ include file="/common/locale.jsp" %>
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

<!--
-->
<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<%-- For Calendar support --%>
<%--
<script type="text/javascript" src="/js/calendar/date.js"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>
<script src="/js/calendar/CalendarPopup.js" language="JavaScript"></script>
--%>

<script type="text/javascript" src="/js/client/catalog/mrschedulefreq.js"></script>
<!-- Add any other javascript you need for the page here -->
<title>
<fmt:message key="label.mr"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
errorInput:'<fmt:message key="error.searchInput.integer"/>',
errorRequired:'<fmt:message key="errors.required"/>',
orderedqtychange:"<fmt:message key="label.orderedqtychange"/>",    
dateFieldName:'<fmt:message key="label.startingdate"/>'};

// -->
</script>

<script type="text/javascript">
<!--
// define __block_weekdays array to block out weekdays, 0 means sunday, 1 means monday....6 means saturday.
// e.g.block sunday and  monday.
/*TODO get these values from the database.*/
 var __block_weekdays = new Array(
 0,
 6
 );

// block June 16, 17 or year 2009
 var __block_dates = new Array(
 '20090703',
 '20090907',
 '20091126',
 '20091127',
 '20091224',
 '20091225'
 );
<%--
  var blackOutDate = new Array(
  <c:forEach var="blackOutDaybean" items="${blackOutDayColl}" varStatus="status">
    <c:choose>
      <c:when test="${status.index == 0}">
        <fmt:formatDate var="formattedRequestedDate" value="${blackOutDaybean}" pattern="MMM dd, yyyy"/>
        "<c:out value="${formattedRequestedDate}" />"
      </c:when>
      <c:otherwise>
        <fmt:formatDate var="formattedRequestedDate" value="${blackOutDaybean}" pattern="MMM dd, yyyy"/>
        ,"<c:out value="${formattedRequestedDate}" />"
      </c:otherwise>
    </c:choose>
  </c:forEach>

  <c:forEach var="blackOutDaybean" items="${medianMrLeadTimeDaysColl}" varStatus="status2">
    <c:choose>
      <c:when test="${status2.index == 0}">
        <fmt:formatDate var="formattedRequestedDate1" value="${blackOutDaybean.calendarDate}" pattern="MMM dd, yyyy"/>
        ,"<c:out value="${formattedRequestedDate1}" />"
      </c:when>
      <c:otherwise>
        <fmt:formatDate var="formattedRequestedDate1" value="${blackOutDaybean.calendarDate}" pattern="MMM dd, yyyy"/>
        ,"<c:out value="${formattedRequestedDate1}" />"
      </c:otherwise>
    </c:choose>
  </c:forEach>                
  );--%>
//-->
</script>
    
</head>

<body bgcolor="#ffffff" onload="sendData('${param.action}')">

<tcmis:form action="/mrschedulefreq.do" onsubmit="return submitOnlyOnce();">

<div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
 <br><br><br><fmt:message key="label.pleasewait"/>
 <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>

<div class="interface" id="mainPage">

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="600" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <c:set var="action" value="${param.action}"/>
    <c:if test="${action == 'freqSearch'}" >
    <!-- Insert all the search option within this div -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
      <tr>
        <td width="25%" class="optionTitleBoldRight"><fmt:message key="label.partnum"/>:</td>
        <td width="75%" class="optionTitleLeft" colspan="3">${bean.facPartNo}</td>
      </tr>
      <tr>
        <td width="25%" class="optionTitleBoldRight"><fmt:message key="label.description"/>:</td>
        <td width="75%" class="optionTitleLeft" colspan="3">${bean.partDescription}</td>
      </tr>
      <tr>
        <td colspan="4" margin="4">
<fieldset>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="10%" class="optionTitleBoldRight">
					<fmt:message key="label.quantity"/>:&nbsp;
				</td>
				<td width="10%" class="optionTitleBoldLeft">
					<input name="mrScheduleFreqInputBean[0].quantity" id="quantity" type="text" class="inputBox" value="" size="6"/>
				</td>
				<td width="10%"  class="optionTitleBoldRight">
					<fmt:message key="label.frequency"/>:&nbsp;
				</td>                
                <td width="70%"  class="optionTitleBoldLeft">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td nowrap>
									<input name="mrScheduleFreqInputBean[0].frequency" id="frequency_month" type="radio" class="radioBtns" value="month">
								          <fmt:message key="label.monthly"/>
									</td>
									<td nowrap>
										<fmt:message key="label.every"/> <input name="mrScheduleFreqInputBean[0].month" id="month" type="text" class="inputBox" size="6" onclick="selectRadioOption('frequency_month')"/> <fmt:message key="label.months"/>
									</td>
								</tr>
								<tr>
									<td>
									<input name="mrScheduleFreqInputBean[0].frequency" id="frequency_week" type="radio" class="radioBtns" value="week" checked/>
								          <fmt:message key="label.weekly"/>
									</td>
									<td>
										<fmt:message key="label.every"/> <input name="mrScheduleFreqInputBean[0].week" id="week" type="text" class="inputBox" size="6" onclick="selectRadioOption('frequency_week')"/> <fmt:message key="label.weeks"/>
									</td>
								</tr>
								<tr>
									<td >
									<input name="mrScheduleFreqInputBean[0].frequency" id="frequency_days" type="radio" class="radioBtns" value="day">
								          <fmt:message key="label.daily"/>
									</td>
									<td nowrap>
										<fmt:message key="label.every"/> <input name="mrScheduleFreqInputBean[0].day" id="day" type="text" class="inputBox" size="6" onclick="selectRadioOption('frequency_days')"/> <fmt:message key="label.calendardays"/>
									</td>
								</tr>
							</table>
				</td>
			</tr>
			<tr>
				<td width="25%"  class="optionTitleBoldRight" nowrap>
					<fmt:message key="label.totaltodeliver"/>:&nbsp;
				</td>
				<td width="25%"  class="optionTitleBoldLeft">
					<input name="mrScheduleFreqInputBean[0].total" id="total" type="text" class="inputBox" value="" size="6"/>
				</td>
				<td width="25%"  class="optionTitleBoldRight">
					<fmt:message key="label.startingdate"/>:&nbsp;
				</td>
				<td width="25%"  class="optionTitleBoldLeft">
                    <%--TODO change this to use client side date--%>

                    <c:set var="medianMrLeadTime" value="${medianMrLeadTime}"/>
                    <input type="hidden" name="mrScheduleFreqInputBean[0].today" id="today" value='<tcmis:getDateTag numberOfDaysFromToday="${medianMrLeadTime}" datePattern="${dateFormatPattern}"/>' />
					<%--<input class="inputBox pointer" readonly type="text" name="startingDate" id="startingDate" value="" onClick="return showCalendar($('startingDate'),$('today'));" size="10"/>--%>

                    <input class="inputBox pointer" readonly type="text" name="mrScheduleFreqInputBean[0].startingDate" id="startingDate" value="" onClick="return getCalendar($('startingDate'),$('today'));" size="10"/>
                    <%--<a href="javascript: void(0);" class="optionTitleBold" id="linkstartingDate" onclick="return showCalendar();">&diams;</a> --%>

                </td>
			</tr>
		</table>
		</fieldset>
		</td>
      </tr>
      <tr>
      <td>
		<input name="Submit" id="Submit" type="submit" value="<fmt:message key="label.ok"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return submitSearchForm();">
</td>
      <td>
<input name="cancel" id="cancel" type="button" value="<fmt:message key="label.cancel"/>" class="inputBtns"  onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"
 onclick="window.close()"/>
</td>
      </tr>
    </table>
   <!-- End search options -->
   </c:if>
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
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
</table>
</div>
<!-- Error Messages Ends -->


<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;">
 <input name="facPartNo" id="facPartNo" type="hidden" value="${bean.facPartNo}"/>
 <input name="itemId" id="itemId" type="hidden" value="${bean.itemId}"/>
 <input name="orderedQty" id="orderedQty" type="hidden" value="${bean.quantity}"/>
 <input name="prNumber" id="prNumber" type="hidden" value="${param.prNumber}"/>
 <input name="lineItem" id="lineItem" type="hidden" value="${param.lineItem}"/>
 <input name="companyId" id="companyId" type="hidden" value="${param.companyId}"/>     
 <input name="action" id="action" type="hidden" value="${param.action}"/>    
 <input name="totalQty" id="totalQty" type="hidden" value="${totalQty}"/>
 <input name="medianMrLeadTime" id="medianMrLeadTime" value="<c:out value="${medianMrLeadTime}"/>" type="hidden">
 <input name="requestor" id="requestor" type="hidden" value="${param.requestor}"/>
 <input name="requestLineStatus" id="requestLineStatus" type="hidden" value="${param.requestLineStatus}"/>
 </div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html>