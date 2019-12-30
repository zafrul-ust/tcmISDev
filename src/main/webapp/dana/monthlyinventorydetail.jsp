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
<%-- this version based on template.jsp --%>

<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
<link rel="stylesheet" type="text/css" href="/css/clientpages.css"></link>

<title>
<fmt:message key="monthlyinventorydetail.title"/>
</title>

<%--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. --%>
<tcmis:fontSizeCss />

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<%-- This handles which key press events are disabeled on this page --%>
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<%-- This handles the menu style and what happens to the right click on the whole page --%>
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>

<%@ include file="/common/rightclickmenudata.jsp" %>

<script type="text/javascript" src="/js/dana/monthlyinventorydetail.js"></script>

<script language="JavaScript" type="text/javascript">

<%-- add all the javascript messages here, this for internationalization of client side javascript messages --%>
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
</script>
</head>

<c:set var="userAction" value='${param.userAction}'/>
<c:set var="doexcelpopup" value='${doexcelpopup}'/>
<c:set var="buttonCreateExcel" value='${param.buttonCreateExcel}'/>
<c:choose>
  <c:when test="${!empty doexcelpopup && !empty buttonCreateExcel}" >
    <body bgcolor="#FFFFFF" text="#000000" link="#FFFFFF" alink="" vlink="#FFFF66" onload="doexcelpopup()">
  </c:when>
  <c:otherwise>
   <body bgcolor="#FFFFFF" text="#000000" link="#FFFFFF" alink="" vlink="#FFFF66">
  </c:otherwise>
</c:choose>

<tcmis:form action="/monthlyinventorydetail.do" onsubmit="return submitOnlyOnce();">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
 </div>
 <div class="interface" id="mainPage">

<%-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure --%>
<div class="topNavigation" id="topNavigation">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td width="200">
<img src="/images/tcmtitlegif.gif" align="left">
</td>
<td>
<img src="/images/tcmistcmis32.gif" align="right">
</td>
</tr>
</table>

<%@ include file="title.jsp" %>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<%-- %><tr><td width="70%" class="headingl">
<fmt:message key="monthlyinventorydetail.title"/>
</td>
<td width="30%" class="headingr">
</td>
</tr> --%>
</table>
</div>

<div class="contentArea">

<%-- Search Option Begins --%>
<table id="searchMaskTable" width="600" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <%-- Insert all the search option within this div --%>

   <%-- start of CONTROL TABLE --%>


<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">

<tr>
<td width="5%">
</td>

<td width="10%" class="optionTitleBoldLeft">
<c:set var="selectedUnitsOrDollars" value='${param.unitsOrDollars}'/>
<c:if test="${empty selectedUnitsOrDollars}">
<c:set var="selectedUnitsOrDollars" value='units'/>
</c:if>

<input type="radio" class="radioBtns" name="unitsOrDollars" id="unitsOrDollars" value="units" <c:if test="${selectedUnitsOrDollars == 'units'}">checked</c:if>><fmt:message key="monthlyinventorydetail.label.units"/>

<input type="radio" class="radioBtns" name="unitsOrDollars" id="unitsOrDollars" value="dollars" <c:if test="${selectedUnitsOrDollars == 'dollars'}">checked</c:if>><fmt:message key="monthlyinventorydetail.label.dollars"/>

</td>
</tr>

<tr>
<td width="5%" class="optionTitleBoldRight">
<fmt:message key="label.facility"/>:&nbsp;
</td>

<td width="15%">
<c:set var="selectedFacilityId" value='${param.facilityId}'/>
<select name="facilityId" id="facilityId" class="selectBox" onchange="getinvociedates()">
<option value="All"><fmt:message key="label.pleaseselect"/></option>
  <c:forEach var="facilityInvoicePeriodViewBean" items="${facilityBeanCollection}" varStatus="status">

  <c:set var="currentFacilityId" value='${status.current.facilityId}'/>
  <c:choose>
   <c:when test="${selectedFacilityId == currentFacilityId}">
    <option value="<c:out value="${currentFacilityId}"/>" selected><c:out value="${status.current.facilityId}"/></option>
   </c:when>
   <c:otherwise>
    <option value="<c:out value="${currentFacilityId}"/>"><c:out value="${status.current.facilityId}"/></option>
   </c:otherwise>
  </c:choose>

  </c:forEach>
</select>
</td>

</tr>

<tr>
<td width="5%" class="optionTitleBoldRight" class="optionTitleBoldLeft">
<fmt:message key="monthlyinventorydetail.label.invoicedate"/>:&nbsp;
</td>

<td width="15%">
<c:set var="selectedInvoiceDate" value='${param.invoiceDate}'/>
<%--
 This variable is named InvocieDate, but is actually a invoice period which correspnds to a invoice Date
  --%>

<select name="invoiceDate" class="selectBox">
 <option value="All"><fmt:message key="label.pleaseselect"/></option>
 <c:forEach var="facilityInvoicePeriodViewBean" items="${facilityInvoicePeriodViewBeanCollection}" varStatus="status">
 <c:set var="currentInvoiceDate" value='${status.current.invoicePeriod}'/>
  <fmt:formatDate var="formattedDate" value="${status.current.endDate}" pattern="MM/dd/yyyy"/>
  <c:choose>
   <c:when test="${selectedInvoiceDate == 'All'}">
    <option value="<c:out value="${currentInvoiceDate}"/>"><c:out value="${formattedDate}"/></option>
   </c:when>
   <c:when test="${userAction == 'getinvociedates'}">
       <option value="<c:out value="${currentInvoiceDate}"/>"><c:out value="${formattedDate}"/></option>
   </c:when>
   <c:otherwise>
     <c:choose>
      <c:when test="${selectedInvoiceDate != 'All' && selectedInvoiceDate == currentInvoiceDate}">
       <option value="<c:out value="${currentInvoiceDate}"/>" selected><c:out value="${formattedDate}"/></option>
      </c:when>
      <c:otherwise>
       <option value="<c:out value="${currentInvoiceDate}"/>"><c:out value="${formattedDate}"/></option>
      </c:otherwise>
     </c:choose>
   </c:otherwise>
  </c:choose>
  </c:forEach>
</select>
</td>

</tr>

<tr>
<td width="10%" colspan="2" class="optionTitleBoldLeft">
<html:submit property="submitSearch" styleId="submitSearch" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"
		 onclick= "return customerSearch()">
     <fmt:message key="label.search"/>
</html:submit>

<html:submit property="buttonCreateExcel" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"
			 onclick= "return createxlsreport()">
     <fmt:message key="label.createexcelfile"/>
</html:submit>
</td>
</tr>

</table>

   <%-- end of CONTROL TABLE --%>

   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
<%-- Search Option Ends --%>

<div class="spacerY">&nbsp;</div>

<%-- Error Messages Begins --%>
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
<%-- Error Messages Ends --%>

<%-- * * * * * * RESULTS SECTION * * * * * *  --%>

<%-- <c :if test="${monthlyInventoryDetailViewBeanCollection != null}" > --%>

<%-- Search results start --%>
<%-- If you want your results section to span only 50% set this to 50% and your main table will be 100% --%>
<c:if test="${userAction == 'search' || userAction == 'createxlsreport'}" >
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <%-- TEMPLATE EXAMPLE CODE: <div class="boxhead"> <a href="#" onclick="doSomeThing(); return false;"><fmt:message key="label.createexcelfile"/></a></div> --%>
    <div class="dataContent">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
    <c:set var="colorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>
		<c:set var="biInventoryValueTotal"  value='${0}'/>
		<c:set var="biHaasOwnedValueTotal"  value='${0}'/>
		<c:set var="biCustomerOwnedValueTotal"  value='${0}'/>
		<c:set var="recInventoryValueTotal"  value='${0}'/>
		<c:set var="recHaasOwnedValueTotal"  value='${0}'/>
		<c:set var="recCustomerOwnedValueTotal"  value='${0}'/>
		<c:set var="eiInventoryValueTotal"  value='${0}'/>
		<c:set var="eiHaasOwnedValueTotal"  value='${0}'/>
		<c:set var="eiCustomerOwnedValueTotal"  value='${0}'/>
		<c:set var="netUsageValueTotal"  value='${0}'/>
		<c:set var="usageHaasOwnedValueTotal"  value='${0}'/>
		<c:set var="usageCustomerOwnedValueTotal"  value='${0}'/>

	<c:forEach var="monthlyInventoryDetailViewBean" items="${monthlyInventoryDetailViewBeanCollection}" varStatus="status">

	<c:if test="${status.index % 10 == 0}">

			<c:set var="dataCount" value='${dataCount+1}'/>
			<tr class="optionTitleBoldCenter">
			<th rowspan="2" width="5%"><fmt:message key="label.storagearea"/></th>
			<th rowspan="2" width="5%"><fmt:message key="label.itemid"/></th>
			<th rowspan="2" width="5%"><fmt:message key="label.danapart#"/></th>
			<th rowspan="2" width="5%"><fmt:message key="label.productname"/></th>
			<th rowspan="2" width="5%"><fmt:message key="label.packaging"/></th>
			<th rowspan="2" width="5%"><fmt:message key="label.countuom"/></th>
			<c:choose>
  				<c:when test="${selectedUnitsOrDollars == 'units'}" >
    				<th width="4%"  height="15" colspan="3"><fmt:message key="label.startinventory"/></th>
    				<th width="4%"  height="15" colspan="3"><fmt:message key="label.received"/></th>
    				<th width="4%"  height="15" colspan="3"><fmt:message key="label.endinventory"/></th>
    				<th width="4%"  height="15" colspan="3"><fmt:message key="label.usage"/></th>
 				</c:when>
  				<c:otherwise>
    				<th rowspan="2" width="5%"><fmt:message key="label.currency"/></th>
    				<th width="4%"  height="15" colspan="3"><fmt:message key="label.startinventoryvalue"/></th>
    				<th width="4%"  height="15" colspan="3"><fmt:message key="label.receivedvalue"/></th>
    				<th width="4%"  height="15" colspan="3"><fmt:message key="label.endinventoryvalue"/></th>
    				<th width="4%"  height="15" colspan="3"><fmt:message key="label.usagevalue"/></th>
 				</c:otherwise>
			</c:choose>
</tr>

<tr class="optionTitleBoldCenter">
<th width="5%"><fmt:message key="label.haas"/></th>
<th width="5%"><fmt:message key="label.dana"/></th>
<th width="5%"><fmt:message key="label.total"/></th>

<th width="5%"><fmt:message key="label.haas"/></th>
<th width="5%"><fmt:message key="label.dana"/></th>
<th width="5%"><fmt:message key="label.total"/></th>

<th width="5%"><fmt:message key="label.haas"/></th>
<th width="5%"><fmt:message key="label.dana"/></th>
<th width="5%"><fmt:message key="label.total"/></th>

<th width="5%"><fmt:message key="label.haas"/></th>
<th width="5%"><fmt:message key="label.dana"/></th>
<th width="5%"><fmt:message key="label.total"/></th>
</tr>

	</c:if>

    	<c:choose>
  	    	<c:when test="${status.index % 2 == 0}" >
  	    		<c:set var="colorClass" value=''/>
 	   		</c:when>
	     	<c:otherwise>
      			<c:set var="colorClass" value='alt'/>
     	    </c:otherwise>
   	 	</c:choose>

<tr class="<c:out value="${colorClass}"/>">

  <td width="5%"><c:out value="${status.current.inventoryGroup}"/></td>
  <td width="5%"><c:out value="${status.current.itemId}"/></td>
  <td width="5%"><c:out value="${status.current.catPartNo}"/></td>
  <td width="5%"><c:out value="${status.current.itemDesc}"/></td>
  <td width="5%"><c:out value="${status.current.packaging}"/></td>
  <td width="5%"><c:out value="${status.current.countUom}"/></td>

<c:choose>
  <c:when test="${selectedUnitsOrDollars == 'units'}" >
  <td width="5%"><tcmis:emptyIfZero value="${status.current.biHaasOwnedQuantity}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.biHaasOwnedQuantity}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${status.current.biCustomerOwnedQuantity}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.biCustomerOwnedQuantity}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.biInventory}"/></fmt:formatNumber></td>
  <td width="5%"><tcmis:emptyIfZero value="${status.current.recHaasOwnedQuantity}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.recHaasOwnedQuantity}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${status.current.recCustomerOwnedQuantity}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.recCustomerOwnedQuantity}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.quantityReceived}"/></fmt:formatNumber></td>
  <td width="5%"><tcmis:emptyIfZero value="${status.current.eiHaasOwnedQuantity}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.eiHaasOwnedQuantity}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${status.current.eiCustomerOwnedQuantity}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.eiCustomerOwnedQuantity}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.eiInventory}"/></fmt:formatNumber></td>
  <td width="5%"><tcmis:emptyIfZero value="${status.current.usageHaasOwnedQuantity}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.usageHaasOwnedQuantity}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${status.current.usageCustomerOwnedQuantity}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.usageCustomerOwnedQuantity}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.netUsageQuantity}"/></fmt:formatNumber></td>
  </c:when>
  <c:otherwise>

      <c:set var="biInventoryValue"  value='${status.current.biInventoryValue}'/>
      <c:set var="biHaasOwnedValue"  value='${status.current.biHaasOwnedValue}'/>
      <c:set var="biCustomerOwnedValue"  value='${status.current.biCustomerOwnedValue}'/>
      <c:set var="recInventoryValue"  value='${status.current.recInventoryValue}'/>
      <c:set var="recHaasOwnedValue"  value='${status.current.recHaasOwnedValue}'/>
      <c:set var="recCustomerOwnedValue"  value='${status.current.recCustomerOwnedValue}'/>
      <c:set var="eiInventoryValue"  value='${status.current.eiInventoryValue}'/>
      <c:set var="eiHaasOwnedValue"  value='${status.current.eiHaasOwnedValue}'/>
      <c:set var="eiCustomerOwnedValue"  value='${status.current.eiCustomerOwnedValue}'/>
      <c:set var="netUsageValue"  value='${status.current.netUsageValue}'/>
      <c:set var="usageHaasOwnedValue"  value='${status.current.usageHaasOwnedValue}'/>
      <c:set var="usageCustomerOwnedValue"  value='${status.current.usageCustomerOwnedValue}'/>

  <td width="5%"><c:out value="${status.current.currencyId}"/></td>
  <td width="5%"><tcmis:emptyIfZero value="${status.current.biHaasOwnedValue}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.biHaasOwnedValue}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${status.current.biCustomerOwnedValue}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.biCustomerOwnedValue}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.biInventoryValue}"/></fmt:formatNumber></td>
  <td width="5%"><tcmis:emptyIfZero value="${status.current.recHaasOwnedValue}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.recHaasOwnedValue}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${status.current.recCustomerOwnedValue}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.recCustomerOwnedValue}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.recInventoryValue}"/></fmt:formatNumber></td>
  <td width="5%"><tcmis:emptyIfZero value="${status.current.eiHaasOwnedValue}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.eiHaasOwnedValue}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${status.current.eiCustomerOwnedValue}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.eiCustomerOwnedValue}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.eiInventoryValue}"/></fmt:formatNumber></td>
  <td width="5%"><tcmis:emptyIfZero value="${status.current.usageHaasOwnedValue}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.usageHaasOwnedValue}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><tcmis:emptyIfZero value="${status.current.usageCustomerOwnedValue}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.usageCustomerOwnedValue}"/></fmt:formatNumber></tcmis:emptyIfZero></td>
  <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.netUsageValue}"/></fmt:formatNumber></td>
  </c:otherwise>
</c:choose>
</tr>
</c:forEach>
		<c:if test="${dataCount > 0}">
			<c:choose>
  			<c:when test="${selectedUnitsOrDollars == 'dollars'}" >
  				<tcmis:monthlyInventoryDetailTotalsTag indicator="danapage"/>
  			</c:when>
			</c:choose>
		</c:if>

   </table>
   <%-- If the collection is empty say no data found --%>
   		<c:if test="${empty monthlyInventoryDetailViewBeanCollection}" >

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
</td></tr>
</table>

		<%-- * * * * * * END OF SEARCH RESULTS SECTION * * * * * *  --%>

</c:if >

<%-- Hidden element start --%>
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="userAction" value="" id="userAction">
</div>
<%-- Hidden elements end --%>

</div> <%-- close of contentArea --%>

<%-- Footer message start --%>
 <div class="messageBar">&nbsp;</div>
<%-- Footer message end --%>

</div> <%-- close of interface --%>
</tcmis:form>
</body>
</html:html>