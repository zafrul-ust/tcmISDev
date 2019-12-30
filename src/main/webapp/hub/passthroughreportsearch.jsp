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

<%@ include file="/common/locale.jsp" %>
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

<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/hub/passthroughreport.js"></script>

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
<fmt:message key="passthroughreport.label.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
enterValidDate:"<fmt:message key="label.enterValidDate"/>",
all:"<fmt:message key="label.all"/>",usedsince:"<fmt:message key="passthroughreport.label.usedsince"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
enterValidDate:"<fmt:message key="error.date.invalid"/>"}; 
// -->
</script>
<script language="JavaScript" type="text/javascript">
<!--
var althubid = new Array(
<c:forEach var="hubInventoryGroupOvBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"${status.current.branchPlant}"
   </c:when>
   <c:otherwise>
    "${status.current.branchPlant}"
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altinvid = new Array();
var altinvName = new Array();
<c:forEach var="hubInventoryGroupOvBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
<c:set var="currentHub" value='${status.current.branchPlant}'/>
<c:set var="inventoryGroupDefinitions" value='${status.current.inventoryGroupCollection}'/>

altinvid["${currentHub}"] = new Array(
 <c:forEach var="inventoryGroupObjBean" items="${inventoryGroupDefinitions}" varStatus="status1">
 <c:choose>
   <c:when test="${status1.index > 0}">
    ,"${status1.current.inventoryGroup}"
   </c:when>
   <c:otherwise>
    "${status1.current.inventoryGroup}"
   </c:otherwise>
  </c:choose>
  </c:forEach>
  );

altinvName["${currentHub}"] = new Array(
 <c:forEach var="inventoryGroupObjBean" items="${inventoryGroupDefinitions}" varStatus="status1">
 <c:choose>
   <c:when test="${status1.index > 0}">
    ,"${status1.current.inventoryGroupName}"
   </c:when>
   <c:otherwise>
    "${status1.current.inventoryGroupName}"
   </c:otherwise>
  </c:choose>
  </c:forEach>
  );
 </c:forEach>
// -->
</script>
<c:set var="submitSearch" value='${param.submitSearch}'/>
<c:set var="buttonCreateExcel" value='${param.buttonCreateExcel}'/>
<c:set var="userAction" value='${param.userAction}'/>

</head>

<body bgcolor="#ffffff" onload="setSearchFrameSize();">

<tcmis:form action="/passthroughreportresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

<div class="interface" id="searchMainPage">

<div class="contentArea">

<!-- Search Option Begins -->

<table id="searchMaskTable" width="750" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">

  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr>
 <td width="5%" class="optionTitleBoldRight">
  <fmt:message key="label.hub"/>:
 </td>
 <td width="10%" class="optionTitleBoldLeft">
 <c:set var="selectedHub" value='${param.hub}'/>
 <c:set var="selectedHubName" value=''/>
 <select name="hub" id="hub" onchange="hubchanged()" class="selectBox">
   <c:forEach var="hubInventoryGroupOvBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
  <c:set var="currentHub" value='${status.current.branchPlant}'/>

  <c:choose>
   <c:when test="${empty selectedHub}" >
    <c:set var="selectedHub" value='${status.current.branchPlant}'/>
    <c:set var="selectedHubName" value="${status.current.hubName}"/>
    <c:set var="inventoryGroupDefinitions" value='${status.current.inventoryGroupCollection}'/>
   </c:when>
   <c:when test="${currentHub == selectedHub}" >
    <c:set var="inventoryGroupDefinitions" value='${status.current.inventoryGroupCollection}'/>
   </c:when>
  </c:choose>

  <c:choose>
   <c:when test="${currentHub == selectedHub}">
    <option value="${currentHub}" selected>${status.current.hubName}</option>
    <c:set var="selectedHubName" value="${status.current.hubName}"/>
   </c:when>
   <c:otherwise>
    <option value="${currentHub}">${status.current.hubName}</option>
   </c:otherwise>
  </c:choose>
  </c:forEach>
 </select>
 </td>
 <td width="5%" class="optionTitleBoldRight">
   <fmt:message key="passthroughreport.label.usedsince"/>:
 </td>
 <td width="10%" class="optionTitleLeft">
  	<c:set var="formattedIssueConfirmed" value='${param.dateDelivered}'/>
  	<input type="text" readonly name="dateDelivered" id="dateDelivered" value="${formattedIssueConfirmed}" size="8" maxlength="10" class="inputBox pointer"
  	  onClick="return getCalendar(document.genericForm.dateDelivered);">
<%--  	<a href="javascript: void(0);" id="linkdateDelivered" onClick="return getCalendar(document.genericForm.dateDelivered);" style="color:#0000ff;cursor:pointer;text-decoration:underline">&diams;</a>
 --%> 
 </td>

</tr>

<tr>
 <td width="5%" class="optionTitleBoldRight">
 <fmt:message key="label.inventorygroup"/>:
 </td>
 <td width="10%" class="optionTitleBoldLeft">
 <c:set var="selectedIg" value='${param.inventoryGroup}'/>
 <c:set var="invenGroupCount" value='${0}'/>
 <select name="inventoryGroup" id="inventoryGroup" class="selectBox">
  <c:forEach var="inventoryGroupObjBean" items="${inventoryGroupDefinitions}" varStatus="status">
    <c:set var="invenGroupCount" value='${invenGroupCount+1}'/>
    <c:set var="currentIg" value='${status.current.inventoryGroup}'/>
    <c:choose>
      <c:when test="${empty selectedIg}" >
        <c:set var="selectedIg" value=""/>
      </c:when>
      <c:when test="${currentIg == selectedIg}" >
      </c:when>
    </c:choose>

    <c:choose>
      <c:when test="${currentIg == selectedIg}">
        <option value="${currentIg}" selected>${status.current.inventoryGroupName}</option>
      </c:when>
      <c:otherwise>
        <option value="${currentIg}">${status.current.inventoryGroupName}</option>
      </c:otherwise>
    </c:choose>
  </c:forEach>
  <c:if test="${invenGroupCount == 0}">
   <option value=""><fmt:message key="label.all"/></option>
  </c:if>
 </select>
 </td>
 <td width="5%" class="optionTitleBoldRight">
   <fmt:message key="label.sortby"/>:
 </td>

 <td width="10%" class="optionTitleBoldLeft">
  <html:select property="sort" styleId="sort" styleClass="selectBox">
  <html:option value="DATE_DELIVERED,CUSTOMER_PO" key="passthroughreport.sort.usecustomerpo"/>
  <html:option value="ITEM_ID,INVOICE_DATE" key="passthroughreport.sort.itemincoice"/>
  <html:option value="ITEM_ID,DATE_DELIVERED" key="passthroughreport.sort.itemusedate"/>
  <html:option value="FAC_PART_NO,INVOICE_DATE" key="passthroughreport.sort.partinvoice"/>
  <html:option value="FAC_PART_NO,DATE_DELIVERED" key="passthroughreport.sort.partusedate"/>
  </html:select>
 </td>
</tr>
<tr>
 <td width="100%" class="optionTitleBoldLeft" colspan="4">
   <html:submit property="submitSearch" styleId="submitSearch" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" 
   		onclick="return submitSearchForm();"> 
     <fmt:message key="label.search"/>
   </html:submit>
<html:submit property="buttonCreateExcel" styleId="buttonCreateExcel" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" 
		onclick="return createExcelFile();">
     <fmt:message key="label.createexcelfile"/>
</html:submit>
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

<!-- Hidden element start http://localhost/tcmIS/hub/passthroughreportmain.do -->

<div id="hiddenElements" style="display: none;"> 

 <input name="userAction" id="userAction" type="hidden"> 
 <input type="hidden" name="startSearchTime" id="startSearchTime" value=""/>
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>