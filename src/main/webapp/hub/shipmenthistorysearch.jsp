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
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<!--Use this tag to get the correct CSS class
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<%@ include file="/common/locale.jsp" %>
<%@ include file="/common/opshubig.jsp" %>

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
<script type="text/javascript" src="/js/common/opshubig.js"></script>
<script type="text/javascript" src="/js/hub/shipmenthistory.js"></script>

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

<!--  These are for the autocomplete function -->
<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script> 
<script type="text/javascript" src="/js/jquery/autocomplete.js"></script> 
<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" />  

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",all:"<fmt:message key="label.all"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

// Can be removed to show My Entities, My hubs and My Inventory Group
defaults.ops.nodefault = true;
defaults.hub.nodefault = true;

defaults.hub.callback = igchanged;
/*
// drop down
var althubid = new Array(
<c:forEach var="hubPrefViewOvBean" items="${hubPrefViewOvBeanCollection}" varStatus="status">
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
<c:forEach var="hubPrefViewOvBean" items="${hubPrefViewOvBeanCollection}" varStatus="status">
<c:set var="currentHub" value='${status.current.branchPlant}'/>
<c:set var="inventoryGroupDefinitions" value='${status.current.inventoryGroupDocks}'/>

altinvid["${currentHub}"] = new Array(""
 <c:forEach var="inventoryGroupDefinitionOvBean" items="${inventoryGroupDefinitions}" varStatus="status1">
,"${status1.current.inventoryGroup}"
  </c:forEach>
  );
altinvName["${currentHub}"] = new Array("All"
 <c:forEach var="inventoryGroupDefinitionOvBean" items="${inventoryGroupDefinitions}" varStatus="status1">
,"${status1.current.inventoryGroupName}"
  </c:forEach>

  );
 </c:forEach>  */


var altigid = new Array();
var altigName = new Array();
<c:forEach var="hubPrefViewOvBean" items="${hubPrefViewOvBeanCollection}" varStatus="status">
  <c:set var="currentHub" value='${status.current.branchPlant}'/>
  <c:set var="inventoryGroupDefinitions" value='${status.current.inventoryGroupDocks}'/>
  <c:forEach var="inventoryGroupDefinitionOvBean" items="${inventoryGroupDefinitions}" varStatus="status1">
    <c:set var="currentIg" value='${status1.current.inventoryGroup}'/>
    <c:set var="requestLineItemShipTos" value='${status1.current.docks}'/>
    altigid["${currentIg}"] = new Array(""
    <c:forEach var="requestLineItemBean" items="${requestLineItemShipTos}" varStatus="status2">
	,"${status2.current.dockLocationId}"
    </c:forEach>
    );
    altigName["${currentIg}"] = new Array("All"
    <c:forEach var="requestLineItemBean" items="${requestLineItemShipTos}" varStatus="status2">
		,"${status2.current.dockLocationId}"
    </c:forEach>
    );
  </c:forEach>
</c:forEach>

j$().ready(function() {
	function log(event, data, formatted) {
		j$('#customerId').val(formatted.split(":")[0]);
		$("customerName").className = "inputBox"; 
	} 
	
	j$("#customerName").autocomplete("/tcmIS/distribution/findcustomer.do",{
			width: 350,
			max: 100,  // default value is 10
			cacheLength:0, // disable cache here because we put "rownum < max" for better performance. Cache will make data off.
			scrollHeight: 120,
			formatItem: function(data, i, n, value) {
				return  value.split(":")[0]+" "+value.split(":")[1].substring(0,32);
			},
			formatResult: function(data, value) {
				return value.split(":")[1];
			}
	});
	
	j$('#customerName').bind('keyup',(function(e) {
		  var keyCode = (e.keyCode ? e.keyCode : e.which);

		  if( keyCode != 13 && keyCode != 9) // 13 is for Enter; 9 is for Tab
		  	invalidateCustomer();
	}));
	
	j$("#customerName").result(log).next().click(function() {
		j$(this).prev().search();
	});
}); 

function invalidateCustomer()
{
 var customerName  =  document.getElementById("customerName");
 var customerId  =  document.getElementById("customerId");
 if (customerName.value.length == 0) {
   customerName.className = "inputBox";
 }else {
   customerName.className = "inputBox red";
 }
 //set to empty
 customerId.value = "";
}
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="setSearchFrameSize();setOps();igchanged();">

<tcmis:form action="shipmenthistoryresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

<div class="interface" id="searchMainPage">

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
<tr>
	<td nowrap  width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.operatingentity"/>:</td>
    <td width="35%" class="optionTitleBoldLeft">
         <select name="opsEntityId" id="opsEntityId" class="selectBox">
         </select>
    </td>
    <td width="15%" class="optionTitleBoldRight" nowrap>
		<fmt:message key="label.shipmentid"/>:&nbsp;
	</td>
	<td width="40%">
		<input class="inputBox" type="text" name="shipmentId" id="shipmentId" value="" maxlength="10" size="10"/>
	</td>
	<td width="15%" class="optionTitleBoldRight" nowrap>
		<fmt:message key="label.mr"/>:&nbsp;
	</td>
	<td width="40%" nowrap>
		<input class="inputBox" type="text" name="prNumber" id="prNumber" value="" maxlength="15" size="10"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</td>
	
</tr>

<tr>
	<td nowrap width="10%"  class="optionTitleBoldRight"><fmt:message key="label.hub"/>:</td>
    <td  width="35%" class="optionTitleBoldLeft">
         <select name="hub" id="hub" class="selectBox">
         </select>
    </td>
    <td width="15%" class="optionTitleBoldRight" nowrap>
		<fmt:message key="label.trackingno"/>:&nbsp;
	</td>
	<td width="40%" nowrap>
		<input class="inputBox" type="text" name="shipmentOrTracking" id="shipmentOrTracking" value=""  size="35"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</td>	
  
</tr>

<tr>
	<td nowrap  width="10%" class="optionTitleBoldRight"><fmt:message key="label.inventorygroup"/>:</td>
	<td width="35%" class="optionTitleBoldLeft">
	    <select name="inventoryGroup" id="inventoryGroup" value='${param.inventoryGroup}' onchange="igchanged();" class="selectBox">
	    </select>
	</td>
	 <td class="optionTitleBoldRight"> <fmt:message key="label.customer"/>:</td>
      <td class="optionTitleLeft" nowrap>
      	<input type="text" name="customerName" id="customerName" value="" size="35"  class="inputBox"/>
      	<input name="customerId" id="customerId" type="hidden" value=""/>  
      	<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="selectedCustomer" value="..." align="right" onClick="lookupCustomer()"/>
     	<!-- <button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
                                             name="None" value=""  OnClick="clearCustomer();return false;"><fmt:message key="label.clear"/> </button>  -->
      </td>
	
</tr>
<tr> 
	<td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.datedelivered"/>:
	</td>
	<td width="35%" class="optionTitleBoldLeft"  nowrap>
		<fmt:message key="label.from"/>
		<input class="inputBox pointer" readonly type="text" name="fromDate" id="fromDate" value="<tcmis:getDateTag numberOfDaysFromToday="-30" datePattern="${dateFormatPattern}"/>" onClick="return getCalendar(document.genericForm.fromDate,null,null,null,document.genericForm.toDate);" size="10"/>
		<fmt:message key="label.to"/>
		<input class="inputBox pointer" readonly type="text" name="toDate" id="toDate" value="<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>" onClick="return getCalendar(document.genericForm.toDate,document.genericForm.fromDate);" size="10"/>
	</td>
	<td width="15%" class="optionTitleBoldRight">
		<fmt:message key="label.shipto"/>:&nbsp;
	</td>
	<td width="40%"  class="optionTitleBoldLeft">

<c:set var="selectedShipTo" value='${param.shipTo}'/>
<c:set var="shipToCount" value='${0}'/>
<select name="dock" id="dock" class="selectBox">
  <option value=""><fmt:message key="label.all"/></option>
<c:if test="${!empty param.inventoryGroup}">
  <c:forEach var="requestLineItemBean" items="${requestLineItemShipTos}" varStatus="status">
      <c:set var="shipToCount" value='${shipToCount+1}'/>
      <c:set var="currentShipTo" value='${status.current.dockLocationId}'/>
      <c:choose>
        <c:when test="${selectedShipTo == currentShipTo}">
          <option value="<c:out value="${currentShipTo}"/>" selected><c:out value="${status.current.dockLocationId}"/></option>
        </c:when>
        <c:otherwise>
          <option value="<c:out value="${currentShipTo}"/>"><c:out value="${status.current.dockLocationId}"/></option>
        </c:otherwise>
      </c:choose>
  </c:forEach>
</c:if>
</select>

</td>	 
	
</tr>
<tr>
<td colspan="3" >
<input type="submit" name="submitSearch" id="submitSearch" value="<fmt:message key="label.search"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="return search()"/>

<input name="createExcel" id="createExcel" type="button"
						value="<fmt:message key="label.createexcelfile"/>" onclick="generateExcel();" class="inputBtns"
						onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" />
</td>
</tr>    </table>
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
<input name="uAction" id="uAction" value="search"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
<input type="hidden" name="pageid" id="pageid" value="${param.pageid}"/>
<input type="hidden" name="personnelCompanyId"  id="personnelCompanyId" value="${personnelBean.companyId}""/>
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>