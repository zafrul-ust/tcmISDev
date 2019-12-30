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


<html>
<head>

<script type="text/javascript" src="/js/common/formchek.js"></script>

<!-- calendar -->
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/CalendarPopup.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
<script type="text/javascript" src="/js/calendar/date.js"></script>
<script type="text/javascript" src="/js/calendar/util.js"></script>

<script type="text/javascript">
function nextMonthLinkOnclick(date) {
//I get called when next month link is clicked but I don't do anything
alert("next");
return false;
}

function previousMonthLinkOnclick(date) {
//I get called when previous month link is clicked but I don't do anything
}

function calendarInputFocusLost(date) {
  var val = document.getElementById('calendarInput' + date);
  var inputCellDiv = document.getElementById('inputCellDiv' + date);
  inputCellDiv.style.visibility = 'hidden';
  var inputLabelCellDiv = document.getElementById('inputLabelCellDiv' + date);

  var cellDiv = document.getElementById('cellDiv' + date);
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
  return false;
}


function dateCellClicked(date) {
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
  return false;
}
</script>

<SCRIPT LANGUAGE="JavaScript">
function showCalendar() {
  var cal4 = new CalendarPopup();
  cal4.setMonthNames('Januar','Februar','März','April','Mag','Juni','Juli','August','September','Oktober','November','Dezember');
  cal4.setDayHeaders('S','M','D','M','D','F','S');
  cal4.setWeekStartDay(1);
  cal4.setTodayText("Heute");
  cal4.addDisabledDates("08/25/2007");
  cal4.setDisabledWeekDays(3); //can't select Thursdays
  cal4.select(document.forms[0].fubar,'bar','dd/MM/yyyy');
  return false;
}
</SCRIPT>

<link rel="stylesheet" href="/css/calendar.css" type="text/css" />
</head>
<body>
<center>

<%
java.util.Map myMap = new java.util.HashMap();
myMap.put("08/10/2007", "3");
request.setAttribute("myMap", myMap);

%>

<FORM action="trong.do">
<input type="text" name="fubar" id="fubar"><a href="#" onclick="return showCalendar();" name="bar" id="bar">calendar</a>
<tcmis:calendar tableId="foo" 
                tableClass="calendarTable"
                availableDayClass="availableDayClass"
                unavailableDayClass="unavailableDayClass" 
                displayDate="${param.calendarDate}" 
                dateDisplayMap="myMap" 
                blackoutDayCollection="myColl"
                cellWidth="50px"
                headerDateFormat="MMM/yyyy"
                weekdayFormat="EE"
                nextMonthLink="#"
                previousMonthLink="#"
                inputLabel="QTY:"/>
</FORM>
</center>

<body>

</html>
