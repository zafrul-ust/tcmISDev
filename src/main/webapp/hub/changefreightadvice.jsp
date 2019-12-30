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
 <%@ include file="/common/locale.jsp" %>
<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>

  <script type="text/javascript" src="/js/hub/changefreightadvice.js"></script>

<title>
<fmt:message key="label.changefreightadvice"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validValues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
quantity:"<fmt:message key="label.quantity"/>",
projectedShipDate:"<fmt:message key="label.projectedshipdate"/>",
consolidationNo:"<fmt:message key="label.consolidationno"/>",
reallychangeallorderforconsolidationnumbertotl:"<fmt:message key="label.reallychangeallorderforconsolidationnumbertotl"/>",
okforupdate:"<fmt:message key="label.okforupdate"/>",
clickcanceltopick:"<fmt:message key="label.clickcanceltopick"/>",
mustbepositive:"<fmt:message key="label.positiveinteger"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="changeFreightAdvice()" onunload="closeAllchildren();"  onresize="resizeFrames()" >

<div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
 <br><br><br><fmt:message key="label.pleasewait"/>
 <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<tcmis:form action="/freightadviceresults.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="mainPage" style="">

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
  <c:if test="${freightAdviceViewBeanCollection != null}" >
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div --> 
   <c:if test="${!empty freightAdviceViewBeanCollection}" >
    <c:forEach var="freightAdviceViewBean" items="${freightAdviceViewBeanCollection}" varStatus="status">
    <table width="100%" border="0" cellpadding="0" cellspacing="2" class="tableSearch">
      <tr>
      	<td width="10%">&nbsp;</td> 
        <td width="30%" class="optionTitleBoldRight" nowrap><fmt:message key="label.transportationmode"/>:</td>
        <td width="60%" class="optionTitleBoldLeft">
			 <c:set var="selectedLtl" value='selected'/>
			 <c:set var="selectedParcel" value=''/>
			 <c:set var="selectedTl" value=''/>
			 <c:if test="${status.current.transportationMode == 'Parcel'}">
				<c:set var="selectedLtl" value=''/>
				<c:set var="selectedParcel" value='selected'/>
				<c:set var="selectedTl" value=''/>
			 </c:if>
			 <c:if test="${status.current.transportationMode == 'TL'}">
				<c:set var="selectedLtl" value=''/>
				<c:set var="selectedParcel" value=''/>
				<c:set var="selectedTl" value='selected'/>
			 </c:if>
			 <select name="transportationMode" id="transportationMode" class="selectBox" onchange="transportationModeChanged()">
				<option value="Parcel" ${selectedParcel}><fmt:message key="label.parcel" /></option>
				<option value="LTL" ${selectedLtl}><fmt:message key="label.ltl" /></option>
				<option value="TL" ${selectedTl}><fmt:message key="label.tl" /></option>
			</select>	
			<input type="hidden" name="originalTransportationMode" id="originalTransportationMode" value="<c:out value="${status.current.transportationMode}"/>"> 
		  </td>
 	  </tr>
    <tr>
    <td width="10%">&nbsp;</td> 
    <td width="30%" class="optionTitleBoldRight" nowrap><fmt:message key="label.projectedshipdate"/>:</td>
    <td width="60%">
    	<c:set var="todayDate">			
			<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>
		</c:set>
		<input type="hidden" name="todayDate" id="todayDate" value="${todayDate}"/>
		<c:set var="fmtDate" value=''/>
		<c:if test="${status.current.scheduledShipDate != null}">
			<fmt:formatDate var="fmtDate" value="${status.current.scheduledShipDate}" pattern="${dateFormatPattern}"/>
		</c:if>
		<input name="dateShipped" id="dateShipped" readonly type="text" value="${fmtDate}" onchange="clearConsolidationNumber()" onClick="return getCalendar(document.getElementById('dateShipped'),null,null,document.genericForm.todayDate);" class="inputBox pointer" size="10"/>
   	</td>
    </tr>

	 <tr id="consolidationNumberDiv" style="visibility:visible">
	 	<td width="10%">&nbsp;</td> 
		<td width="30%" class="optionTitleBoldRight" nowrap><fmt:message key="label.consolidationno"/>:</td>
		<td width="60%" nowrap>
			<input class="inputBox" type="text" name="consolidationNumber" id="consolidationNumber" value="${status.current.consolidationNumber}" readonly="true" size="10">
			<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="selectedConsolidationNumber" id="selectedConsolidationNumber" value="..." align="right" onClick="searchConsolidationNumber();"/>
			<input name="clearB" id="clearB" type="button" value="<fmt:message key="label.clear"/>" onClick="clearConsolidationNumber()" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'"/>
			<input type="hidden" name="originalConsolidationNumber" id="originalConsolidationNumber" value="<c:out value="${status.current.consolidationNumber}"/>"> 
		</td>
	 </tr>

	 <tr>
    <td width="10%" valign="top" class="optionTitleBoldRight">  
		<fmt:message key="label.notes"/>:&nbsp;</td>
	<td width="90%" colspan="2" class="optionTitleBoldLeft">  
		<textarea cols="50" rows="5" class="inputBox" name="notes" id="notes">${status.current.notes}</textarea>
   	</td>
      </tr>
      <tr> 
      <td width="100%" colspan="3" class="optionTitleBoldCenter">
        <input name="ok" id="ok" type="button" value="<fmt:message key="label.update"/>" onclick='submitAdvice();' class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'"/>&nbsp;&nbsp;
		<input name="cancelBtn" id="cancelBtn" type="button" value="<fmt:message key="label.cancel"/>" onclick='window.close();' class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'"/>
        <input type="hidden" name="packingGroupId" id="packingGroupId" value="<c:out value="${status.current.packingGroupId}"/>">
		  <input type="hidden" name="hub" id="hub" value="<c:out value="${status.current.hub}"/>">
		  <input type="hidden" name="inventoryGroup" id="inventoryGroup" value="<c:out value="${status.current.inventoryGroup}"/>">
		  <input type="hidden" name="prNumber" id="prNumber" value="<c:out value="${status.current.prNumber}"/>">
        <input type="hidden" name="lineItem" id="lineItem" value="<c:out value="${status.current.lineItem}"/>">
      </tr>
    </table>
   </c:forEach>
   </c:if>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty freightAdviceViewBeanCollection}" >
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
     </tr>
    </table>    
    
   </c:if>
  
   <!-- End search options -->
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
   </c:if>
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

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;">
 <input name="action" id="action" value="changefreightadviceok" type="hidden"/>
 <input name="changefreightadviceok" id="changefreightadviceok" value="changefreightadviceok" type="hidden"/>
 <input name="refresh" id="refresh" value="${refresh}" type="hidden"/>
 <input name="messageToUser" id="messageToUser" value="${messageToUser}" type="hidden"/>
 <input name="minHeight" id="minHeight" type="hidden" value="100">
 </div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div id="footer" class="messageBar">&nbsp;</div>
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