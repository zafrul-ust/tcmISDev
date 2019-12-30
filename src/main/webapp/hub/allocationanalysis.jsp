<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html:html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<tcmis:fontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>


<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>
<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script src="/js/common/disableKeys.js" language="JavaScript"></script>

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>

<%-- For Calendar support --%>
<%--
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>
--%>

<%-- Add any other javascript you need for the page here --%>
<script src="/js/hub/allocationanalysis.js" language="JavaScript"></script>

<script language="JavaScript" type="text/javascript">
<!--
var altHubId = new Array(
<c:forEach var="hubInventoryGroupFacOvBean" items="${hubInventoryGroupFacOvBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"<c:out value="${status.current.branchPlant}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status.current.branchPlant}"/>"
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altInventoryGroup = new Array();
var altInventoryGroupName = new Array();
<c:forEach var="hubInventoryGroupFacOvBean" items="${hubInventoryGroupFacOvBeanCollection}" varStatus="status">
  <c:set var="currentHub" value='${status.current.branchPlant}'/>
  <c:set var="inventoryGroupCollection" value='${status.current.inventoryGroupCollection}'/>

  altInventoryGroup["<c:out value="${currentHub}"/>"] = new Array(""
  <c:forEach var="inventoryGroupBean" items="${inventoryGroupCollection}" varStatus="status1">
        ,"<c:out value="${status1.current.inventoryGroup}"/>"
  </c:forEach>
  );

  altInventoryGroupName["<c:out value="${currentHub}"/>"] = new Array("<fmt:message key="label.all"/>"
  <c:forEach var="inventoryGroupBean" items="${inventoryGroupCollection}" varStatus="status1">
        ,"<c:out value="${status1.current.inventoryGroup}"/>"
  </c:forEach>
  );
</c:forEach>

var altFacilityId = new Array();
var altFacilityName = new Array();
<c:forEach var="hubInventoryGroupFacOvBean" items="${hubInventoryGroupFacOvBeanCollection}" varStatus="status">
  <c:set var="currentHub" value='${status.current.branchPlant}'/>
  <c:set var="inventoryGroupCollection" value='${status.current.inventoryGroupCollection}'/>
  <c:forEach var="inventoryGroupBean" items="${inventoryGroupCollection}" varStatus="status1">
    <c:set var="currentInventoryGroup" value='${status1.current.inventoryGroup}'/>
    <c:set var="facilityBeanCollection" value='${status1.current.facilities}'/>

      altFacilityId["<c:out value="${currentInventoryGroup}"/>"] = new Array(""
      <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status2">
        ,"<c:out value="${status2.current.facilityId}"/>"
      </c:forEach>
      );

      altFacilityName["<c:out value="${currentInventoryGroup}"/>"] = new Array("<fmt:message key="label.all"/>"
      <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status2">
        ,"<c:out value="${status2.current.facilityName}" escapeXml="false"/>"
      </c:forEach>
      );

  </c:forEach>
</c:forEach>

// -->
</script>


<%-- These are for the Grid, uncomment if you are going to use the grid --%>
<%--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<%-- This is for the YUI, uncomment if you will use this --%>
<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>

<title>
<fmt:message key="allocationanalysis.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",daySpanInteger:"<fmt:message key="error.dayspan.integer"/>",
itemMrInteger:"<fmt:message key="error.itemmr.integer"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff">


<tcmis:form action="/allocationanalysis.do" onsubmit="return submitOnlyOnce();">


 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;text-align:center;">
  <br><br><br><fmt:message key="label.pleasewait"/>
 </div>
 <div class="interface" id="mainPage">

<%-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure --%>
<div class="topNavigation" id="topNavigation">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr valign="top">
<td width="200">
<img src="/images/tcmtitlegif.gif" align="left">
</td>
<td align="right">
<img src="/images/tcmistcmis32.gif" align="right">
</td>
</tr>
</table>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td width="70%" class="headingl">
<fmt:message key="allocationanalysis.title"/>
</td>
<td width="30%" class="headingr">
<html:link style="color:#FFFFFF" forward="home">
 <fmt:message key="label.home"/>
</html:link>
</td>
</tr>
</table>
</div>
<div class="contentArea">
<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
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
        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.hub"/>:</td>
        <td width="30%">
         <c:set var="selectedHub" value='${param.hub}'/>
          <select name="hub" id="hub" class="selectBox" onchange="hubChanged();">
          <c:forEach var="hubInventoryGroupFacOvBean" items="${hubInventoryGroupFacOvBeanCollection}" varStatus="status">
            <c:set var="currentHub" value='${status.current.branchPlant}'/>

            <c:choose>
              <c:when test="${empty selectedHub}" >
                <c:set var="selectedHub" value='${status.current.branchPlant}'/>
                <c:set var="inventoryGroupCollection" value='${status.current.inventoryGroupCollection}'/>
              </c:when>
              <c:when test="${currentHub == selectedHub}" >
                <c:set var="inventoryGroupCollection" value='${status.current.inventoryGroupCollection}'/>
              </c:when>
            </c:choose>
            <c:choose>
              <c:when test="${currentHub == selectedHub}">
                <option value="<c:out value="${currentHub}"/>" selected><c:out value="${status.current.hubName}"/></option>
              </c:when>
              <c:otherwise>
                <option value="<c:out value="${currentHub}"/>"><c:out value="${status.current.hubName}"/></option>
              </c:otherwise>
            </c:choose>
          </c:forEach>
          </select></td>
        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.lotstatus"/>:</td>
        <td width="30%">
<c:set var="selectedLotStatus" value="${param.lotStatus}"/>
<c:if test="${selectedLotStatus == null || selectedLotStatus == ''}">
  <c:set var="selectedLotStatus" value=''/>
</c:if>

 <select class="selectBox" name="lotStatus" id="lotStatus">
 	 	<option value=""><fmt:message key="label.all"/></option>
   <c:forEach var="bean" items="${vvLotStatusBeanCollection}" varStatus="status">
   	<c:set var="jspLabel" value=""/>
    <c:if test="${fn:length(status.current.jspLabel) > 0}"><c:set var="jspLabel">${status.current.jspLabel}</c:set></c:if>
     	<c:choose>
			<c:when test="${selectedLotStatus == bean.lotStatus}">
				<option value="<c:out value="${status.current.lotStatus}"/>" selected><fmt:message key="${jspLabel}"/></option>
			</c:when>
			<c:otherwise>
				<option value="${bean.lotStatus}"><fmt:message key="${status.current.jspLabel}"/></option>
			</c:otherwise>
		</c:choose>
  </c:forEach>
 </select>

        </td>
        <td width="10%" class="optionTitleBoldRight">
<c:set var="selectedItemOrMrCriteria" value="${param.itemOrMrCriteria}"/>
<c:if test="${selectedItemOrMrCriteria == null || selectedItemOrMrCriteria == ''}">
  <c:set var="selectedItemOrMrCriteria" value='itemid'/>
</c:if>
<html:select property="itemOrMrCriteria" styleId="itemOrMrCriteria" styleClass="selectBox" value="${selectedItemOrMrCriteria}">
<html:option value="itemid" key="label.itemid"/>
<html:option value="mr" key="label.mr"/>
</html:select>
        </td>
        <td width="10%">
          <fmt:message key="label.is"/>
          <input name="itemOrMr" id="itemOrMr" type="text" class="inputBox" value="<c:out value="${param.itemOrMr}"/>" size="8">
        </td>
      </tr>
      <tr>
        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.inventorygroup"/>:</td>
        <td width="30%">
          <c:set var="selectedInventoryGroup" value='${param.inventoryGroup}'/>
          <select name="inventoryGroup" id="inventoryGroup" class="selectBox" onchange="inventoryGroupChanged();">
            <option value=""><fmt:message key="label.all"/></option>
            <c:forEach var="inventoryGroupFacilityObjBean" items="${inventoryGroupCollection}" varStatus="status">
              <c:set var="currentInventoryGroup" value='${status.current.inventoryGroup}'/>
              <c:choose>
                <c:when test="${empty selectedInventoryGroup}" >
                  <c:set var="selectedInventoryGroup" value=''/>
                  <c:set var="facilityBeanCollection" value=''/>
                </c:when>
                <c:when test="${selectedInventoryGroup == currentInventoryGroup}" >
                  <c:set var="facilityBeanCollection" value='${status.current.facilities}'/>
                </c:when>
              </c:choose>
              <c:choose>
                <c:when test="${currentInventoryGroup == selectedInventoryGroup}">
                  <option value="<c:out value="${currentInventoryGroup}"/>" selected><c:out value="${status.current.inventoryGroupName}"/></option>
                </c:when>
                <c:otherwise>
                  <option value="<c:out value="${currentInventoryGroup}"/>"><c:out value="${status.current.inventoryGroupName}"/></option>
                </c:otherwise>
              </c:choose>
            </c:forEach>
          </select></td>
        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.progressstatus"/>:</td>
        <td width="30%">
<c:set var="selectedProgressStatus" value="${param.progressStatus}"/>
<c:if test="${selectedProgressStatus == null || selectedProgressStatus == ''}">
  <c:set var="selectedProgressStatus" value=''/>
</c:if>
<html:select property="progressStatus" styleId="progressStatus" styleClass="selectBox" value="${selectedProgressStatus}">
<html:option value="" key="label.all"/>
<html:option value="Available" key="label.available"/>
<html:option value="In Supply" key="label.insupply"/>
<html:option value="Not Allocated" key="label.notallocated"/>
<html:option value="On Hand, Not Available" key="allocationanalysis.label.onhand"/>
</html:select>
        </td>
        <td width="10%" class="optionTitleBoldRight">
<c:set var="selectedDaySpanCriteria" value="${param.daySpanCriteria}"/>
<c:if test="${selectedDaySpanCriteria == null || selectedDaySpanCriteria == ''}">
  <c:set var="selectedDaySpanCriteria" value='needed'/>
</c:if>
<html:select property="daySpanCriteria" styleId="daySpanCriteria" styleClass="selectBox" value="${selectedDaySpanCriteria}">
<html:option value="needed" key="label.needed"/>
<html:option value="ontime" key="label.ontime"/>
</html:select>
        </td>
        <td width="10%">
<c:set var="selectedDaySpan" value="${param.daySpan}"/>
<c:if test="${selectedDaySpan == null || selectedDaySpan == ''}">
  <c:set var="selectedDaySpan" value='7'/>
</c:if>
          <fmt:message key="label.within"/>
          <input name="daySpan" id="daySpan" type="text" class="inputBox" value="<c:out value="${selectedDaySpan}"/>" size="3">
          <fmt:message key="label.days"/>
        </td>
      </tr>
      <tr>
        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.facility"/>:</td>
        <td width="30%">
          <c:set var="selectedFacilityId" value='${param.facilityId}'/>
          <select name="facilityId" id="facilityId" class="selectBox">
            <option value=""><fmt:message key="label.all"/></option>
            <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status">
              <c:set var="currentFacilityId" value='${status.current.facilityId}'/>
              <c:choose>
                <c:when test="${empty selectedFacilityId}" >
                  <c:set var="selectedFacilityId" value=''/>
                </c:when>
              </c:choose>
              <c:choose>
                <c:when test="${currentFacilityId == selectedFacilityId}">
                  <option value="<c:out value="${currentFacilityId}"/>" selected><c:out value="${status.current.facilityId}"/></option>
                </c:when>
                <c:otherwise>
                  <option value="<c:out value="${currentFacilityId}"/>"><c:out value="${status.current.facilityId}"/></option>
                </c:otherwise>
              </c:choose>
            </c:forEach>
          </select></td>
        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.sortby"/>:</td>
        <td width="30%">
<c:set var="selectedSortBy" value="${param.sortBy}"/>
<c:if test="${selectedSortBy == null || selectedSortBy == ''}">
  <c:set var="selectedSortBy" value='REQUIRED_DATETIME'/>
</c:if>
<html:select property="sortBy" styleId="sortBy" styleClass="selectBox" value="${selectedSortBy}">
<html:option value="FACILITY_ID" key="label.facilityid"/>
<html:option value="ITEM_ID,REQUIRED_DATETIME" key="allocationanalysis.label.itemneeded"/>
<html:option value="PR_NUMBER,LINE_ITEM" key="label.mrline"/>
<html:option value="PR_NUMBER,LINE_ITEM,REQUIRED_DATETIME" key="allocationanalysis.label.mrlineneeded"/>
<html:option value="REQUIRED_DATETIME" key="label.needed"/>
<html:option value="SYSTEM_REQUIRED_DATETIME" key="allocationanalysis.label.ontimedate"/>
<html:option value="REF_NO" key="label.referenceno"/>
<html:option value="ITEM_TYPE,ITEM_ID,REQUIRED_DATETIME" key="allocationanalysis.label.typeitemneeded"/>
</html:select>
        </td>
        <td width="10%" class="optionTitleBoldRight">
&nbsp;
        </td>
        <td width="10%">
&nbsp;
        </td>
      </tr>
      <tr>
      <td width="10%" class="optionTitleBoldRight"><input name="submitSearch" id="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return validateForm();"></td>
     <td width="30%">
       <html:button property="buttonCreateExcel" styleId="submitSearch" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="generateExcel(); return false;">
        <fmt:message key="label.createexcelfile"/>
       </html:button>
     </td>
     <td colspan="4">&nbsp; </td>
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
<div class="spacerY">&nbsp;
<div id="errorMessagesArea" class="errorMessages" style="display:none;">
<html:errors/>
</div>
</div>
<!-- Error Messages Ends -->

<c:if test="${prOpenOrderBeanCollection != null}" >
<!-- Search results start -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead">
      <a href="#" onclick="showAllocationLegend(); return false;"><fmt:message key="label.showlegend"/></a> 
    </div>
    <div class="dataContent">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
    <c:set var="colorClass" value=''/>
    <c:set var="legendColorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>

    <c:forEach var="prOpenOrderBean" items="${prOpenOrderBeanCollection}" varStatus="status">
    <!-- Need to print the header every 10 rows-->
    <c:if test="${status.index % 10 == 0}">
    <tr>
    <th width="5%"><fmt:message key="label.facility"/></th>
    <th width="5%"><fmt:message key="label.workarea"/></th>
    <th width="5%"><fmt:message key="label.requestor"/></th>
    <th width="5%"><fmt:message key="label.mrline"/></th>
    <th width="2%"><fmt:message key="label.stockingtype"/></th>
    <th width="7%"><fmt:message key="label.partnumber"/></th>
    <th width="20%"><fmt:message key="label.partdescription"/></th>
    <th width="2%"><fmt:message key="label.quantityopen"/>/ <fmt:message key="label.totalquantity"/></th>
    <th width="5%"><fmt:message key="label.needed"/></th>
    <th width="5%"><fmt:message key="allocationanalysis.label.ontimedate"/></th>
    <th width="5%"><fmt:message key="label.allocationtype"/></th>
    <th width="5%"><fmt:message key="label.ref"/></th>
    <th width="5%"><fmt:message key="allocationanalysis.label.suppliermfglot"/></th>
    <th width="5%"><fmt:message key="allocationanalysis.label.itemidinventorygroup"/></th>
    <th width="2%"><fmt:message key="label.qty"/></th>
    <th width="5%"><fmt:message key="label.status"/>(<fmt:message key="label.ageindays"/>)</th>
    <th width="2%"><fmt:message key="label.date"/></th>
    <th width="5%"><fmt:message key="label.qtyonhand"/><br><fmt:message key="label.inventorygroup"/>/<fmt:message key="label.hub"/></th>
    <th width="5%"><fmt:message key="label.qtyavailable"/><br><fmt:message key="label.inventorygroup"/>/<fmt:message key="label.hub"/></th>
    </tr>
    </c:if>


<c:choose>
  <c:when test="${status.index % 2 == 0}">
   <c:set var="colorClass" value=''/>
  </c:when>
  <c:otherwise>
   <c:set var="colorClass" value="alt"/>
  </c:otherwise>
</c:choose>
<!-- change color of first columns depending on status -->
<c:if test="${status.current.critical == 'Y' || status.current.critical == 'y'}">
  <c:set var="colorClass" value="red"/>
</c:if>
<c:if test="${status.current.critical == 'S' || status.current.critical == 's'}">
  <c:set var="colorClass" value="pink"/>
</c:if>

   <tr class="<c:out value="${colorClass}"/>">
     <td width="5%" rowspan="<c:out value="${status.current.counter}"/>"><c:out value="${status.current.facilityId}"/>&nbsp;</td>
     <td width="5%" rowspan="<c:out value="${status.current.counter}"/>"><c:out value="${status.current.application}"/>&nbsp;</td>
     <td width="5%" rowspan="<c:out value="${status.current.counter}"/>"><c:out value="${status.current.requestorLastName}"/>, <c:out value="${status.current.requestorFirstName}"/>&nbsp;</td>
     <td width="5%" rowspan="<c:out value="${status.current.counter}"/>">
<c:if test="${status.current.mrNotes != null && status.current.mrNotes != ''}">

<c:set var="notes">
<c:out value="${status.current.mrNotes}" escapeXml="false"/>
</c:set>

<c:set var="notes">
<tcmis:replace value="${notes}" from="'" to="\\'"/>
</c:set>

<c:set var="notes">
<tcmis:replace value="${notes}" from="\r" to=""/>
</c:set>

<c:set var="notes">
<tcmis:replace value="${notes}" from="\n" to="<br>"/>
</c:set>
<a href="" onclick="showAllNotes('<c:out value="${notes}" escapeXml="true"/>', '<c:out value="${status.current.prNumber}"/>-<c:out value="${status.current.lineItem}"/>'); return false;">
</c:if>
<c:out value="${status.current.prNumber}"/>-<c:out value="${status.current.lineItem}"/>
<c:if test="${status.current.mrNotes != null && status.current.mrNotes != ''}">
</a>
</c:if>
&nbsp;</td>
     <td width="2%" rowspan="<c:out value="${status.current.counter}"/>"><c:out value="${status.current.itemType}"/>&nbsp;</td>
     <td width="7%" rowspan="<c:out value="${status.current.counter}"/>"><c:out value="${status.current.facPartNo}"/>&nbsp;</td>
     <td width="20%" rowspan="<c:out value="${status.current.counter}"/>"><c:out value="${status.current.partDescription}"/>&nbsp;</td>
<%-- now do neededDateCollection --%>
<c:set var="neededDateBeanCollection" value='${status.current.neededDateBeanCollection}'/>
<c:forEach var="neededDateBean" items="${neededDateBeanCollection}" varStatus="status1">
<c:if test="${status1.index > 0 && status.current.counter > 1}">
<tr class="<c:out value="${colorClass}"/>">
</c:if>
     <td width="2%" rowspan="<c:out value="${status1.current.counter}"/>"><fmt:formatNumber value="${status1.current.openQuantity}" type="NUMBER"  maxFractionDigits="2" />/ 
     <fmt:formatNumber value="${status1.current.orderQuantity}" type="NUMBER"  maxFractionDigits="2" />&nbsp;</td>
     <td width="5%" rowspan="<c:out value="${status1.current.counter}"/>"><c:out value="${status1.current.requiredDatetime}"/>&nbsp;</td>
<%-- now do itemCollection --%>
<c:set var="itemBeanCollection" value='${status1.current.itemBeanCollection}'/>
<c:forEach var="itemBean" items="${itemBeanCollection}" varStatus="status2">
<c:if test="${status2.index > 0 && status1.current.counter > 1}">
<tr class="<c:out value="${colorClass}"/>">
</c:if>
<c:set var="legendColorClass" value=''/>
<c:if test="${status2.current.pickable != 'Y' && status2.current.pickable != 'y' && status2.current.allocationType == 'Receipt'}">
  <c:set var="legendColorClass" value="yellow"/>
</c:if>
<c:if test="${status2.current.hazmatIdMissing == 'MISSING'}">
  <c:set var="legendColorClass" value="orange"/>
</c:if>
<c:choose>
  <c:when test="${status.current.lookAheadDays == null || status.current.lookAheadDays == ''}">
    <c:set var="lookAheadDays" value="0"/>
  </c:when>
  <c:otherwise>
    <c:set var="lookAheadDays" value="${status.current.lookAheadDays}"/>
  </c:otherwise>
</c:choose>
<c:set var="lookAheadDateString">
  <tcmis:getDateTag numberOfDaysFromToday="${lookAheadDays}"/>
</c:set>

<fmt:parseDate value="${lookAheadDateString}"
    pattern="MM/dd/yyyy"
    var="lookAheadDate"/>

  <c:if test="${status2.current.refStatus == 'Not Allocated' && ((status.current.deliveryType == 'Schedule' && lookAheadDate > status.current.requiredDatetimeSort) || status.current.deliveryType != 'Schedule')}">
    <c:set var="legendColorClass" value="black"/>
  </c:if>
<%--
$<c:out value="${lookAheadDate}"/>$
*<c:out value="${status.current.requiredDatetimeSort}"/>*
</c:if>
--%>
     <td width="5%" rowspan="<c:out value="${status2.current.counter}"/>" class="<c:out value="${legendColorClass}"/>"><c:out value="${status2.current.systemRequiredDatetime}"/>&nbsp;</td>
     <td width="5%" rowspan="<c:out value="${status2.current.counter}"/>" class="<c:out value="${legendColorClass}"/>"><c:out value="${status2.current.allocationType}"/>&nbsp;</td>
     <td width="5%" rowspan="<c:out value="${status2.current.counter}"/>" class="<c:out value="${legendColorClass}"/>">
       <c:out value="${status2.current.refNo}"/>
       <c:if test="${status2.current.allocationType == 'PO'}">
         -<c:out value="${status2.current.refLine}"/>
       </c:if>
       &nbsp;
     </td>
     <td width="5%" rowspan="<c:out value="${status2.current.counter}"/>" class="<c:out value="${legendColorClass}"/>">
       <c:choose>
         <c:when test="${status2.current.allocationType == 'Receipt'}">
           <c:out value="${status2.current.mfgLot}"/>
         </c:when>
         <c:otherwise>
           <c:out value="${status2.current.supplier}"/>
         </c:otherwise>
       </c:choose>
       &nbsp;
     </td>
     <td width="5%" rowspan="<c:out value="${status2.current.counter}"/>" class="<c:out value="${legendColorClass}"/>"><c:out value="${status2.current.itemId}"/>(<c:out value="${status2.current.inventoryGroup}"/>)&nbsp;</td>
     <td width="2%" rowspan="<c:out value="${status2.current.counter}"/>" class="<c:out value="${legendColorClass}"/>"><fmt:formatNumber value="${status2.current.allocQuantity}" type="NUMBER"  maxFractionDigits="2" />&nbsp;</td>
     <td width="5%" rowspan="<c:out value="${status2.current.counter}"/>" class="<c:out value="${legendColorClass}"/>"><c:out value="${status2.current.refStatus}"/>
<c:if test="${status2.current.lotStatusAge != null}">
(<fmt:formatNumber value="${status2.current.lotStatusAge}" type="NUMBER"  maxFractionDigits="2" />)
</c:if>
     &nbsp;</td>
     <td width="2%" rowspan="<c:out value="${status2.current.counter}"/>" class="<c:out value="${legendColorClass}"/>">
       <c:if test="${status2.current.allocationType == 'PO' && status2.current.originalPromisedDate != status2.current.refDate}">
         <c:out value="${status.current.originalPromisedDate}"/>&nbsp;
       </c:if>
       <c:out value="${status2.current.refDate}"/>
       &nbsp;
     </td>
     <td width="5%" rowspan="<c:out value="${status2.current.counter}"/>" class="<c:out value="${legendColorClass}"/>"><fmt:formatNumber value="${status2.current.qtyOnHand}" type="NUMBER"  maxFractionDigits="2" />/<fmt:formatNumber value="${status2.current.igQtyOnHand}" type="NUMBER"  maxFractionDigits="2" />&nbsp;</td>
     <td width="5%" rowspan="<c:out value="${status2.current.counter}"/>" class="<c:out value="${legendColorClass}"/>"><fmt:formatNumber value="${status2.current.qtyAvailable}" type="NUMBER"  maxFractionDigits="2" />/<fmt:formatNumber value="${status2.current.igQtyAvailable}" type="NUMBER"  maxFractionDigits="2" />&nbsp;</td>
   </tr>
   </c:forEach>
   </c:forEach>
   </c:forEach>
   </table>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty prOpenOrderBeanCollection}" >
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
     </tr>
    </table>
   </c:if>

  </div>
  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</div>
</td></tr>
</table>
<!-- Search results end -->
</c:if>

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;">
<input name="action" id="action" type="hidden" value="createExcel">
 </div>
<!-- Hidden elements end -->

</div> <!-- close of content area -->
</div> <!-- close of interface -->
</tcmis:form>
</body>
</html:html>
