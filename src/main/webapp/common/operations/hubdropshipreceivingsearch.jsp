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
<%@ include file="/common/opshubig.jsp" %>
<!-- For Calendar support -->
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/operations/hubdropshipreceivingsearch.js"></script>
<script type="text/javascript" src="/js/common/opshubig.js"></script>

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
<fmt:message key="dropshipReceiving"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
function mycallback() {
alert('test');
}

function lookupCustomer() {    
  loc = "../distribution/customerlookupsearchmain.do?popup=Y&displayElementId=customerName&valueElementId=customerId";  
  openWinGeneric(loc,"customerlookup","800","500","yes","50","50","20","20","no"); 
}

function clearCustomer()
{
    document.getElementById("customerName").value = "";
    document.getElementById("customerId").value = "";
}

defaults.ops.nodefault = true;
defaults.hub.nodefault = true;
//defaults.hub.callback = igchanged;
/*
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

altinvid["${currentHub}"] = new Array(
 <c:forEach var="inventoryGroupDefinitionOvBean" items="${inventoryGroupDefinitions}" varStatus="status1">
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
 <c:forEach var="inventoryGroupDefinitionOvBean" items="${inventoryGroupDefinitions}" varStatus="status1">
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
*/

var altDock = new Array();
var altDockDesc = new Array();
<c:forEach var="hubPrefViewOvBean" items="${hubPrefViewOvBeanCollection}" varStatus="status">
  <c:set var="currentHub" value='${status.current.branchPlant}'/>
  <c:set var="inventoryGroupDefinitions" value='${status.current.inventoryGroupDocks}'/>
  <c:forEach var="inventoryGroupDefinitionOvBean" items="${inventoryGroupDefinitions}" varStatus="status1">
    <c:set var="currentIg" value='${status1.current.inventoryGroup}'/>
    <c:set var="requestLineItemShipTos" value='${status1.current.docks}'/>
    altDock["${currentIg}"] = new Array(""
    <c:forEach var="requestLineItemBean" items="${requestLineItemShipTos}" varStatus="status2">
,"${status2.current.dockLocationId}"
<%--
      <c:choose>
        <c:when test="${status2.index > 0}">
          ,"${status2.current.dockLocationId}"
        </c:when>
        <c:otherwise>
          "${status2.current.dockLocationId}"
        </c:otherwise>
      </c:choose>
--%>
    </c:forEach>

    );
    altDockDesc["${currentIg}"] = new Array("<fmt:message key="label.alldocks"/>"
    <c:forEach var="requestLineItemBean" items="${requestLineItemShipTos}" varStatus="status2">
,"${status2.current.dockDesc}"
<%--
      <c:choose>
        <c:when test="${status2.index > 0}">
          ,"${status2.current.dockLocationId}"
        </c:when>
        <c:otherwise>
          "${status2.current.dockLocationId}"
        </c:otherwise>
      </c:choose>
--%>
    </c:forEach>
    );
  </c:forEach>
</c:forEach>

// -->
</script>


<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
alldocks:"<fmt:message key="label.alldocks"/>",
exptdwithin:"<fmt:message key="label.exptdwithin"/>",
mustBeInteger:"<fmt:message key="label.errorinteger"/>"
};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="setSearchFrameSize();attachInvGroupUpdate();setOps();">

<tcmis:form action="/dropshipreceivingresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

<div class="interface" id="searchMainPage"> <!-- Start of interface-->

<div class="contentArea"> <!-- Start of contentArea-->

<!-- Search Option Begins -->
<table id="searchMaskTable" width="760" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <!-- Search Option Table Start -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
      <%-- row 1 --%>
      <tr>
	       <td nowrap  width="8%" class="optionTitleBoldRight" nowrap><fmt:message key="label.operatingentity"/>:</td>
	      <td width="6%" class="optionTitleBoldLeft">
	         <select name="operatingEntityId" id="opsEntityId" class="selectBox">
	         </select>
	      </td>
	       <td width="10%" class="optionTitleBoldRight" nowrap>
	         <fmt:message key="label.searchby"/>:
	       </td>
	       <td width="45%" class="optionTitleBoldLeft" nowrap>
	        <c:set var="selectedSearchBy" value='${param.searchWhat}'/>
	        <select name="searchWhat" id="searchWhat" class="selectBox">
	          <option value="itemDescription" selected><fmt:message key="label.itemdesc"/></option>
	          <option value="itemId"><fmt:message key="label.itemid"/></option>
	          <option value="mrNumber"><fmt:message key="label.mrnumber"/></option>
	          <option value="radianPo"><fmt:message key="label.haaspo"/></option>
	        </select>
	
	        <c:set var="selectedSearchType" value='${param.searchType}'/>
	        <select name="searchType" id="searchType" class="selectBox">
	          <option value="LIKE" selected><fmt:message key="label.contain"/></option>
	          <option value="IS"><fmt:message key="label.is"/></option>
	          <option value="STARTSWITH"><fmt:message key="label.startswith"/></option>
	          <option value="ENDSWITH"><fmt:message key="label.endswith"/></option>
	        </select>
	
	        <input class="inputBox" type="text" name="searchText" id="searchText" value="<c:out value='${param.searchText}'/>" size="10">
	       </td>      
      </tr>
      <tr>
	        <td nowrap width="6%"  class="optionTitleBoldRight"><fmt:message key="label.hub"/>:</td>
	      <td  width="10%" class="optionTitleBoldLeft">
	         <select name="branchPlant" id="hub"  onchange="igchanged();" class="selectBox">
	         </select>
	      </td>
	      <td width="10%" class="optionTitleBoldRight">
	          <fmt:message key="label.sort"/>:
		</td>
		<td width="45%">
	        <c:set var="selectedSortBy" value='${param.sortBy}'/>
	        <select name="sortBy" id="sortBy" class="selectBox">
	          <option value="EXPECTED,LAST_SUPPLIER,MR_NUMBER" selected><fmt:message key="label.dateexptdsupplier"/></option>
	          <option value="MR_NUMBER,MR_LINE_ITEM"><fmt:message key="label.mrline"/></option>
	          <option value="RADIAN_PO,LINE_ITEM,MR_NUMBER"><fmt:message key="label.popoline"/></option>
	          <option value="LAST_SUPPLIER,EXPECTED,MR_NUMBER"><fmt:message key="label.supplierdateexptd"/></option>
	        </select>
		</td>
      </tr>
       <%-- row 3 --%>
      <tr>
	       <td nowrap  width="8%" class="optionTitleBoldRight"><fmt:message key="label.inventorygroup"/>:</td>
	      <td width="14%" class="optionTitleBoldLeft">
	       <select name="inventoryGroup" id="inventoryGroup"  onchange="igchanged()" class="selectBox">
	       </select>
	      </td>
	       <td>&nbsp;</td>
	       <td width="30%">
	          <b><fmt:message key="label.exptdwithin"/></b>&nbsp;
		  <input class="inputBox" type="text" name="expectedWithin" id="expectedWithin" value="30" size="1"/>
		  &nbsp;<b><fmt:message key="label.days"/></b>
	       </td>
      </tr>     

      <%-- row 4 --%>
      <tr>
       <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.shipto"/>:</td>
       <td width="30%">

<c:set var="selectedShipTo" value='${param.dockId}'/>
<c:set var="shipToCount" value='${0}'/>
<select name="dockId" id="dockId" class="selectBox">
  <option value=""><fmt:message key="label.alldocks"/></option>
<c:if test="${!empty param.inventoryGroup}">
  <c:forEach var="requestLineItemBean" items="${requestLineItemShipTos}" varStatus="status">
      <c:set var="shipToCount" value='${shipToCount+1}'/>
      <c:set var="currentShipTo" value='${status.current.dockLocationId}'/>
      <c:choose>
        <c:when test="${selectedShipTo == currentShipTo}">
          <option value="${currentShipTo}" selected>${status.current.dockLocationId}</option>
        </c:when>
        <c:otherwise>
          <option value="${currentShipTo}">${status.current.dockLocationId}</option>
        </c:otherwise>
      </c:choose>
  </c:forEach>
</c:if>
<%--
  <c:if test="${shipToCount == 0}">
   <option value=""><fmt:message key="label.all"/></option>
  </c:if>
--%>
</select>

       </td>
       <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.customer"/>:</td>
       <td width="30%" colspan="5">
        	<input type="text" name="customerName" id="customerName" value="" size="20"  onkeypress="onKeySearch(event,lookupCustomer);" class="invGreyInputText" readonly/>
      	 	<input name="customerId" id="customerId" type="hidden" value=""/>  
      		<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="selectedCustomer" value="..." align="right" onClick="lookupCustomer()"/>
     		<button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
                                             name="None" value=""  OnClick="clearCustomer();return false;"><fmt:message key="label.clear"/> </button>
        </td>
      

      </tr>

    <%-- buttons --%>
    <tr>
      <td width="5%" colspan="2" class="optionTitleBoldLeft">
        <input name="submitSearch" id="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="button.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onclick="return submitSearchForm();">

        <input name="buttonCreateExcel" id="buttonCreateExcel" type="button" class="inputBtns" value="<fmt:message key="label.createexcelfile"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="generateExcel();">
       
      </td>
    </tr>
    </table>
    <!-- Search Option Table end -->
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
<input name="action" id="action" type="hidden" value="">
<input name="startSearchTime" id="startSearchTime" type="hidden"value="">
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>