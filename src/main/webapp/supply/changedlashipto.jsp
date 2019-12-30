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
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
<%-- 
<script type="text/javascript" src="/js/calendar/date.js"></script>
<script type="text/javascript" src="/js/calendar/CalendarPopup.js"></script>
--%>

<!-- Add any other javascript you need for the page here -->


<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
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

<script type="text/javascript" src="/js/supply/changedlashipto.js"></script>
<title>
<fmt:message key="changedlashipto.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
invaliddateformat:"<fmt:message key="label.invaliddateformat"/>", daterequired:"<fmt:message key="label.daterequired"/>",
shiptododaac:"<fmt:message key="label.shiptododaac"/>",markfordodaac:"<fmt:message key="label.markfordodaac"/>",
futuredatenotallowed:"<fmt:message key="label.futuredatenotallowed"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="changeDLAshipto()">

<tcmis:form action="/changedlashipto.do" onsubmit="return submitOnlyOnce();">

<div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
 <br><br><br><fmt:message key="label.pleasewait"/>
 <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>

<div class="interface" id="mainPage">

<div class="contentArea">

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
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">     
<input name="prNumber" id="prNumber" value="${param.prNumber}" type="hidden" >
 <input name="dodaacType" id="dodaacType" value="" type="hidden" disabeled> 
 <input name="mfDodaacType" id="mfDodaacType" value="" type="hidden" disabeled> 
<input name="lineItem" id="lineItem" value="${param.lineItem}" type="hidden" >
<input name="radianPO" id="radianPO" type="hidden" value="${param.radianPO}"> 
      <tr>
        <td class="optionTitleBoldLeft"><fmt:message key="label.shiptododaac"/>:</td>
        <td><input maxlength="6" size="6" name="dodaac" id="dodaac" type="text" class="invGreyInputText" value="${changeDlaShipToViewBeanCollection[0].shipViaDodaac}" onfocus="blur();">
            <button name="submitSearch" id="submitSearch" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" value="..." onclick="submitSearchForm('nododaac1'); return false;"><img src="/images/search_small.gif" alt="Search"></button>
        </td>
          <td class="optionTitleBoldLeft"><fmt:message key="label.markfordodaac"/>:</td>
          <td><input maxlength="6" size="6" name="mfDodaac" id="mfDodaac" type="text" class="invGreyInputText" value="${changeDlaShipToViewBeanCollection[0].shipToDodaac}" onfocus="blur();">
<button name="submitSearch" id="submitSearch" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" value="..." onclick="submitSearchForm('nododaac2'); return false;"><img src="/images/search_small.gif" alt="Search"></button>

          </td>

      </tr>

     <tr>
        <td class="optionTitleBoldLeft"><fmt:message key="label.shiptoaddress"/>:</td>
        <td>
<input name="shipToLocationId" id="mfLocationId" type="hidden" value="${changeDlaShipToViewBeanCollection[0].shipToLocationId}">
<input name="shipViaLocationId" id="locationId" type="hidden" value="${changeDlaShipToViewBeanCollection[0].shipViaLocationId}"> 
<!--<input name="prNumber" id="prNumber" type="hidden" value="${changeDlaShipToViewBeanCollection[0].prNumber}">
<input name="lineItem" id="lineItem" type="hidden" value="${changeDlaShipToViewBeanCollection[0].lineItem}"> 
          -->
          <textarea rows="5" cols="40" name="address" id="address" class="inputBox"  onfocus="blur();">
<c:out value="${changeDlaShipToViewBeanCollection[0].stAddressLine1Display}"/>
<c:out value="${changeDlaShipToViewBeanCollection[0].stAddressLine2Display}"/>
<c:out value="${changeDlaShipToViewBeanCollection[0].stAddressLine3Display}"/>
<c:out value="${changeDlaShipToViewBeanCollection[0].stAddressLine4Display}"/>
          </textarea></td>
          <td class="optionTitleBoldLeft"><fmt:message key="label.markforaddress"/>:</td>
          <td>
          <textarea rows="5" cols="40" name="mfAddress" id="mfAddress" class="inputBox"  onfocus="blur();">
<c:out value="${changeDlaShipToViewBeanCollection[0].mfAddressLine1Display}"/>
<c:out value="${changeDlaShipToViewBeanCollection[0].mfAddressLine2Display}"/>
<c:out value="${changeDlaShipToViewBeanCollection[0].mfAddressLine3Display}"/>
<c:out value="${changeDlaShipToViewBeanCollection[0].mfAddressLine4Display}"/>
          </textarea>
           </td>

      </tr>

     <!--<tr>
        

        <td class="optionTitleBoldLeft"><fmt:message key="label.notes"/>:</td>
        <td>
          <textarea rows="5" cols="40" name="notes" id="notes" class="inputBox">
              <c:out value="${changeDlaShipToViewBeanCollection[0].notes}"/>
          </textarea>
       </td>
       
      </tr>
      -->
  <tr>
      <td width="5%" class="optionTitleBoldRight" nowrap><fmt:message key="label.dlametricdate"/>:</td>
      <td width="10%">
        <fmt:formatDate var="formattedDesiredShipDate" value="${changeDlaShipToViewBeanCollection[0].desiredShipDate}" pattern="${dateFormatPattern}"/>
        <input class="inputBox pointer" readonly type="text" name="desiredShipDate" id="desiredShipDate" value="${formattedDesiredShipDate}" onClick="return getCalendar(document.genericForm.desiredShipDate);" size="10"/>
      </td>
      <td>
        &nbsp;
      </td>
  </tr>
  <tr>
  <td>
    &nbsp;
  </td>
  <td>
   <input name="submitUpdate" id="submitUpdate" type="submit" value="<fmt:message key="label.update"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return submitUpdateForm();">
  </td>
  <td>
   <input name="submitNew" id="submitNew" type="submit" value="<fmt:message key="label.newaddress"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="submitNewForm('nododaac'); return false;">
  </td>
 </tr>
<%-- 
<c:choose>
<c:when test="${changeDlaShipToViewBeanCollection[0].dateAddressOk == null}">
 <tr>
  <td>
    &nbsp;
  </td>
  <td>
   <input name="submitUpdate" id="submitUpdate" type="submit" value="<fmt:message key="label.update"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return submitUpdateForm();">
  </td>
  <td>
   <input name="submitNew" id="submitNew" type="submit" value="<fmt:message key="label.newaddress"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="submitNewForm('nododaac'); return false;">
  </td>
 </tr>
</c:when>
 <c:otherwise>
 
</c:otherwise>
</c:choose>  --%>
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

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;">
   <input name="uAction" id="uAction" value="param.uAction" type="hidden" />
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
	<c:forEach items="${tcmISError}" varStatus="status">
	<c:if test="${!empty status.current }">
  	    ${status.current}<br/>
	</c:if>
	</c:forEach>
</div>
<input name="okC" id="okC" type="button" value="<fmt:message key="label.ok"/>" onClick="window.close()" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'"/>
</div>

<script type="text/javascript">
<!--
YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", initWin);

showErrorMessage = false;
<c:forEach items="${tcmISError}" varStatus="status">
	<c:if test="${!empty status.current }">
		showErrorMessage = true;
	</c:if>
</c:forEach>

//-->
</script>

</body>
</html:html>
