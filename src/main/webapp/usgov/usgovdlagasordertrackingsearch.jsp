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

<!--Use this tag to get the correct CSS class
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
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
<script type="text/javascript" src="/js/calendar/CalendarPopup.js"></script>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/usgov/usgovdlagasordertracking.js"></script>

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
<fmt:message key="ordertracking.label.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
myworkareas:"<fmt:message key="label.myworkareas"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="setSearchFrameSize();">

<tcmis:form action="/usgovdlagasordertrackingresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

<div class="interface" id="searchMainPage">

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
        <td class="optionTitleBoldRight"><fmt:message key="label.ordertype"/></td>
        <td>
<input name="orderType850" id="orderType850" type="checkbox" class="radioBtns" value="yes"> 850
<input name="orderType940" id="orderType940" type="checkbox" class="radioBtns" value="yes"> 940
       </td>

        <td class="optionTitleBoldRight"><fmt:message key="label.openordersonly"/><input name="openOrder" id="openOrder" type="checkbox" class="radioBtns" value="yes"></td>
        <td class="optionTitleBoldRight"><fmt:message key="label.waitingfordpmsonly"/><input name="waiting" id="waiting" type="checkbox" class="radioBtns" value="yes"></td>

      </tr>
       <tr>
        <td class="optionTitleBoldRight"><fmt:message key="label.docnumber"/>:</td>
        <td colspan="3"><input name="docNum" id="docNum" type="text" class="inputBox"></td>
</tr>
        <tr>
        <td class="optionTitleBoldRight"><fmt:message key="label.contractnumber"/>:</td>
        <td><input name="contractNumber" id="contractNumber" type="text" class="inputBox" value="SPM4AR07D0100" onfocus="blur();" size="12"></td>
        <td class="optionTitleBoldRight"><fmt:message key="label.callnumber"/>:</td>
        <td><input name="deliveryOrder" id="deliveryOrder" type="text" class="inputBox"></td>
      </tr>
<%--
      <tr>
        <td class="optionTitleBoldRight"><fmt:message key="label.deliveryorder"/>:</td>
        <td colspan="3"><input name="deliveryOrder" id="deliveryOrder" type="text" class="inputBox" maxlength="4"></td>
        
      </tr>
--%>
      <tr>
<td class="optionTitleBoldRight"><fmt:message key="label.nsn"/>:</td>
        <td><input name="nsn" id="nsn" type="text" class="inputBox"></td>
<%--
          <td class="optionTitleBoldRight">
              <fmt:message key="label.dodaactype"/>:</td>
          <td>
          <select class="selectBox"  name="useApplication" id="application" size="1"  onchange="applicationChanged()">
            <option value="">POE</option>
            <option value="">CCP</option>
          </select>
          </td>
--%>
          <td class="optionTitleBoldRight"><fmt:message key="label.ultimatedodaac"/>:</td>
        <td><input name="ultimateDodaac" id="ultimateDodaac" type="text" class="inputBox"></td>
      </tr>
<%--
       <tr>
        <td class="optionTitleBoldRight"><fmt:message key="label.nsn"/>:</td>
        <td colspan="3"><input name="nsn" id="nsn" type="text" class="inputBox"></td>
</tr>
--%>
      <tr>
      <td colspan="2">
        <input name="submitSearch" id="submitSearch" type="submit" value="<fmt:message key="label.search"/>" class="inputBtns" 
           onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="submitSearchForm();" />
       
       <input name="CreateExcelFile" id="CreateExcelFile" type="button" onclick="generateExcel();" value="<fmt:message key="label.createexcelfile"/>"
         class="inputBtns"  onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" />

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
<input name="action" id="action" value="" type="hidden">
<input name="startSearchTime" id="startSearchTime" type="hidden" value="">
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>
