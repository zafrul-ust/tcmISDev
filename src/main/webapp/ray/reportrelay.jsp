<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>
<tcmis:loggedIn indicator="true" forwardPage="/ray/index.jsp"/>
<html:html lang="true">
<head>
<%
  String id = ";jsessionid=" + request.getSession(false).getId();
%>
<meta http-equiv="refresh" content="1; URL='/tcmIS/ray/runexcelreport.do<%=id %>'"/>


<title>
  <bean:message key="excel.title"/>
</title>
<SCRIPT LANGUAGE="JavaScript">
<!-- Beginning of JavaScript --------
/*
	JavaScript Timer
	Written by Jerry Aman, Optima System, June 1, 1996.
	Part of the PageSpinner distribution.

	Portions based upon the JavaScript setTimeout example at:
	http://home.netscape.com/eng/mozilla/Gold/handbook/javascript/

	We will not be held responsible for any unwanted
	effects due to the usage of this script or any derivative.
	No warrantees for usability for any specific application are
	given or implied.

	You are free to use and modify this script,
	if the credits above are given in the source code
*/

var	timerID = null
var	timerRunning = false
var	startDate
var	startSecs

function stopclock()
{
	if(timerRunning)
		clearTimeout(timerID)
		timerRunning = false
}

function startclock()
{
	startDate = new Date()
	startSecs = (startDate.getHours()*60*60) + (startDate.getMinutes()*60)
						+ startDate.getSeconds()

	stopclock()
	showtime()
}


/*	-------------------------------------------------
	showtime()
	Puts the amount of time that has passed since
	loading the page into the field named timerField in
	the form named timeForm
	-------------------------------------------------	*/

function showtime()
{
	// this doesn't work correctly at midnight...

	var now = new Date()
	var nowSecs = (now.getHours()*60*60) + (now.getMinutes()*60) + now.getSeconds()
	var elapsedSecs = nowSecs - startSecs;

	var hours = Math.floor( elapsedSecs / 3600 )
	elapsedSecs = elapsedSecs - (hours*3600)

	var minutes = 	Math.floor( elapsedSecs / 60 )
	elapsedSecs = elapsedSecs - (minutes*60)

	var seconds = elapsedSecs

	var timeValue = "" + hours
	timeValue  += ((minutes < 10) ? ":0" : ":") + minutes
	timeValue  += ((seconds < 10) ? ":0" : ":") + seconds

		// Update display
	document.timerForm.timerField.value = timeValue
	timerID = setTimeout("showtime()",1000)
	timerRunning = true
}

/*	-------------------------------------------------
	GetReward
	Here you decide what to do depending upon
	how long the user has waited
	In this case we display an alert and set the
	HREF property of theLink to another page

	Immortal statements by Groucho Marx, 1890-1977
	-------------------------------------------------	*/
function GetReward(theLink)
{
	var msgString;

	var now = new Date()
	var nowSecs = (now.getHours()*60*60) + (now.getMinutes()*60) + now.getSeconds()
	var elapsedSecs = nowSecs - startSecs;

	theLink.href = "groucho.html" // page to go

	if ( elapsedSecs > 70) 	// After 70 secs
		msgString =  "Time flies like an arrow.  Fruit flies like a banana."
	else

	if ( elapsedSecs > 50) // After 50 secs
		msgString = "I've had a perfectly wonderful evening.  But this wasn't it."
	else

	if ( elapsedSecs > 30) // After 30 secs
		msgString =  "Those are my principles. If you don't like them I have others."
	else
	{
		msgString = ("Sorry, no reward yet...")
		theLink.href = "#" // Don't go to another page yet...
	}

	window.alert(msgString);	// But let's display an alert first!
}


// -- End of JavaScript code -------------- -->
</SCRIPT>
</head>
<BODY  onLoad="startclock()">

<FONT FACE="Arial" SIZE=5 COLOR="#000080">
<CENTER>Timer
</FONT><BR>
<FONT FACE="Arial" SIZE=3>
The report has been<BR>
running for
</FONT>
<FORM NAME="timerForm" onSubmit="0">
<INPUT TYPE="text" NAME="timerField" SIZE=10 VALUE ="" readonly>
</FORM>
<img src="/images/tcmintro.gif"/><br>
 <bean:message key="reportrelay.message.wait"/>
</CENTER>

</body>
</html:html>


