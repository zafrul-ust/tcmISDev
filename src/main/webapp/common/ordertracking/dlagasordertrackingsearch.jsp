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


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/ordertracking/dlagasordertracking.js"></script>

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

<script language="JavaScript" type="text/javascript">
<!--
var titleRequired = new Array();
titleRequired["radianPo"] = "<fmt:message key="label.haaspo"/>";
titleRequired["itemId"] = "<fmt:message key="label.itemid"/>";
titleRequired["mrNumber"] = "<fmt:message key="label.mr"/>";

var altSupplierId = new Array(
	""
<c:forEach var="SupplierLocationOvBean" items="${supplierLocationOvBeanCollection}" varStatus="status">
    ,"${status.current.supplier}"
</c:forEach>
);

var altSupplierName = new Array(
	"<fmt:message key="label.pleaseselect"/>"
<c:forEach var="SupplierLocationOvBean" items="${supplierLocationOvBeanCollection}" varStatus="status">
    ,"${status.current.supplierName}"
</c:forEach>
);

var altSupplierLocationId = new Array();
var altSupplierLocationName = new Array();
<c:forEach var="SupplierLocationOvBean" items="${supplierLocationOvBeanCollection}" varStatus="status">

altSupplierLocationId["${status.current.supplier}"] = new Array(
	""
  <c:forEach var="LocationObjBean" items="${status.current.supplierLocations}" varStatus="status1">
    ,"${status1.current.locationId}"
  </c:forEach>
  );

altSupplierLocationName["${status.current.supplier}"] = new Array(
	"<fmt:message key="label.all"/>"
  <c:forEach var="LocationObjBean" items="${status.current.supplierLocations}" varStatus="status1">
    ,"${status1.current.locationShortName}"
  </c:forEach>
  );
</c:forEach>

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
  searchaudit:"<fmt:message key="dlagasordertracking.searchaudit"/>",all:"<fmt:message key="label.all"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
contains:"<fmt:message key="label.contains"/>",
is:"<fmt:message key="label.is"/>",
statusAll:"<fmt:message key="error.statusall"/>",
dateRangeAll:"<fmt:message key="error.daterangeall"/>"
};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="setSearchFrameSize();">

<tcmis:form action="/dlagasordertrackingresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

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
 		<td width="5%" class="optionTitleBoldRight" nowrap="nowrap">
  			<fmt:message key="label.supplierparent"/>:
 		</td>
		<td width="20%" class="optionTitleLeft">
 			<input type="hidden" name="oldsupplier" id="oldsupplier" value=""> <%-- OnChange="suppchanged(document.suppliershipping.supplier)"  --%>
 			<select name="supplier" id="supplier" class="selectBox" OnChange="supplierChanged()">
       <option value="" selected><fmt:message key="label.all"/></option>
        <c:set var="canSearchHubs" value='false'/>
        <c:choose>
        	<c:when test="${sessionScope.personnelBean.companyId == 'Radian'}">
        	 	<c:set var="canSearchHubs" value='true'/>
        	</c:when>
        	<c:otherwise>
        		<tcmis:supplierPermission indicator="true" userGroupId="SupplierAndHubOrders" supplier="" companyId="">
        			 	<c:set var="canSearchHubs" value='true'/>
      			</tcmis:supplierPermission>
        	</c:otherwise>        
        </c:choose>
        <c:if test="${canSearchHubs == 'true'}">  
            <option value="wesco"><fmt:message key="label.wesco"/></option>
        </c:if>
       <c:set var="selectedSupplier" value='${param.supplier}'/>
       <c:forEach var="SupplierLocationOvBean" items="${supplierLocationOvBeanCollection}" varStatus="status">
        <c:set var="currentSupplier" value='${status.current.supplier}'/>
           <%--<c:choose>
            <c:when test="${empty selectedSupplier}" >
              <c:set var="selectedSupplier" value='${currentSupplier}'/>
              <c:set var="initSuppLocBeanCollection" value='${status.current.supplierLocations}'/>
            </c:when>
            <c:when test="${currentSupplier == selectedSupplier}" >
              <c:set var="initSuppLocBeanCollection" value='${status.current.supplierLocations}'/>
            </c:when>
          </c:choose>--%>

           <c:choose>
            <c:when test="${currentSupplier == selectedSupplier}" >
              <option value="${currentSupplier}" selected><c:out value="${status.current.supplierName}" escapeXml="false"/></option>
            </c:when>
            <c:otherwise>
              <option value="${currentSupplier}" ><c:out value="${status.current.supplierName}" escapeXml="false"/></option>
            </c:otherwise>
          </c:choose>
         </c:forEach>
        </select>
 		</td>
    <td width="5%" class="optionTitleBoldRight" colspan="3" nowrap><fmt:message key="label.search" />:
			<select name="searchField" id="searchField" class="selectBox" onchange="addRemContains(this.options[this.selectedIndex].value);">
			  <option value="radianPo"><fmt:message key="label.haaspo"/></option>
			  <option value="milstripCode"><fmt:message key="label.milstrip"/></option>
              <option value="poNumber"><fmt:message key="label.customerpo"/></option>              
              <option value="trackingNumber"><fmt:message key="label.trackingnumber"/></option>
              <option value="facPartNo"><fmt:message key="label.partnumber"/></option>
              <%--<option value="transportationControlNo"><fmt:message key="label.tcn"/></option>--%>
              <option value="callNumber"><fmt:message key="label.callnumber"/></option>
              <option value="prNumber"><fmt:message key="label.mrnumber"/></option>
              <option value="serialNumber"><fmt:message key="label.serialnumber"/></option>
              <option value="shipViaDodaac"><fmt:message key="label.shiptododaac" /></option>
			  <option value="shipToDodaac"><fmt:message key="label.ultimatedodaac" /></option>
            </select>

			<html:select property="searchMode" styleId="searchMode" styleClass="selectBox">
				<html:option value="is"><fmt:message key="label.is"/></html:option>
				<%--<html:option value="startsWith" key="label.startswith"/>
				<html:option value="endsWith" key="label.endswith"/>--%>
			</html:select>

			<input class="inputBox" type="text" name="searchArgument" id="searchArgument" size="20"> <%-- onkeypress="return onKeyPress()"--%>
		</td>
         </tr>
      <tr>
 		<td width="5%"  rowspan="2" class="optionTitleBoldRight" nowrap="nowrap">
  			<fmt:message key="label.supplierlocation"/>:&nbsp;
 		</td>

		<td width="10%" rowspan="2" class="optionTitleLeft">
 			<input type="hidden" name="oldshipFromLocationId" id="oldshipFromLocationId" value=""/>
      <c:set var="selectedsuppLocationIdArray" value='${paramValues.suppLocationIdArray}'/>
       <select name="suppLocationIdArray" id="suppLocationIdArray" class="selectBox" multiple size="4">
            <c:set var="selectedAll" value=''/>
            <c:if test="${empty suppLocationIdArray}" >
             <c:set var="selectedAll" value='selected'/>
            </c:if>
            <option value="" ${selectedAll}><fmt:message key="label.all"/></option>
            <c:forEach var="LocationObjBean" items="${initSuppLocBeanCollection}" varStatus="status">
              <c:set var="currentLocation" value='${status.current.locationId}'/>
              <c:choose>
                <c:when test="${empty suppLocationIdArray}" >
                  <option value="<c:out value="${currentLocation}"/>"><c:out value="${status.current.locationShortName}" escapeXml="false"/></option>
                </c:when>
                <c:otherwise>
                  <c:set var="selectedFlag" value=''/>
                  <c:forEach items="${suppLocationIdArray}" varStatus="status1">
                    <c:set var="selectedLocation" value='${selectedsuppLocationIdArray[status1.index]}'/>
                    <c:if test="${currentLocation == selectedLocation}">
                      <c:set var="selectedFlag" value='selected'/>
                    </c:if>
                  </c:forEach>
                  <option value="<c:out value="${currentLocation}"/>" <c:out value="${selectedFlag}"/>><c:out value="${status.current.locationShortName}" escapeXml="false"/></option>
                </c:otherwise>
              </c:choose>
            </c:forEach>
          </select>
        </td>
        <%--<td width="10%" colspan="4" class="optionTitleBoldRight">--%><%--<fmt:message key="label.openordersonly"/><input type="checkbox" class="radioBtns" value="Yes" onClick="checkOpenOrdersOnly()" name="openOrdersOnly" id="openOrdersOnly">--%><%--</td>--%>          
        <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.status" />:</td>
			  <td width="10%" class="optionTitleLeft">
         <select name="status" id="status" class="selectBox">
           <option value="ALLEXCEPTCANCELED"><fmt:message key="label.allexceptcancelled"/></option>
           <option value=""><fmt:message key="label.all"/></option>
           <option value="OPEN ORDERS"><fmt:message key="label.openordersonly"/></option>
           <option value="AWAITING ADDRESS"><fmt:message key="label.awaitingaddress"/></option>
           <option value="PO NOT CONFIRMED"><fmt:message key="label.atsupplierposent"/></option>
           <%--<option value="SUPPLIER 855 IN ERROR"><fmt:message key="label.supplier855error"/></option>--%>
           <option value="AT SUPPLIER - NOT SHIPPED"><fmt:message key="label.poconfirmed"/></option>
           <option value="PICKED NOT SHIP CONFIRMED"><fmt:message key="label.pickedforshipment"/></option>
           <%--<option value="SHIPPED BUT NOT VERIFIED"><fmt:message key="label.shippednotverified"/></option>--%>
           <%--<option value="IN PREPERATION FOR ASN"><fmt:message key="label.inpreparationforasn"/></option>--%>
           <option value="SHIPPED"><fmt:message key="label.shipped"/></option>           
           <%--<option value="ASN SENT"><fmt:message key="label.asnsent"/></option>--%>
           <option value="CANCELED"><fmt:message key="label.canceled"/></option>                      
        </select>
        </td>
      </tr>
      <c:set var="supplierAndHubOrdersPermission" value='n'/>
      <c:if test="${sessionScope.personnelBean.companyId == 'Radian'}">
      		<c:set var="supplierAndHubOrdersPermission" value='y'/>
	  </c:if>
      <c:choose>
      	<c:when test="${supplierAndHubOrdersPermission == 'y'}">
	      	<tr class="alignLeft">
	      	   <td width="10%" class="optionTitleBoldLeft" colspan="4">
	    			&nbsp; <fmt:message key="label.contractnumber" />: &nbsp;
			        <select name="contractNumber" id="contractNumber" class="selectBox">
			        		<option value="SPE4A616D0226">SPE4A616D0226</option>
			        		<option value="SPM4AR07D0100">SPM4AR07D0100</option>
			        </select>
			 	</td>
		    </tr>	
		     <tr> 
		      <c:choose>
			      <c:when test="${canSearchHubs == 'true'}">
			      <td class="optionTitleBoldRight">
			                <fmt:message key="label.hub"/>:&nbsp;
			       </td>
			      <td class="optionTitleBoldLeft">
			          <select name="hub" id="hub" class="selectBox">
				          <option value="ALL" selected><fmt:message key="label.all" /></option>
				          <c:forEach var="hubBean" items="${dlaHubBeanCollection}" varStatus="status">
				                <option value="${status.current.branchPlant}">${status.current.hubName}</option>
				          </c:forEach>
			          </select>
			        </td>
			        </c:when>
			        <c:otherwise>
			        <td/><td/>
			        </c:otherwise>
		        </c:choose>
		     	<td width="5%" class="optionTitleBoldRight" colspan="3" nowrap>
		            <select name="dateOpt" id="dateOpt" class="selectBox">
		                  <option value="orderDate"><fmt:message key="label.usgovorderdate"/></option>
		                  <option value="dateShipped"><fmt:message key="label.actualshipdate"/></option>
		                  <option value="dateSent"><fmt:message key="label.datesent"/></option>
		                  <option value="desiredShipDate"><fmt:message key="label.dlametricdate"/></option>
		            </select>
		            <fmt:message key="label.from"/>
		            <input type="text" name="dateFrom" id="dateFrom" readonly class="inputBox pointer"  style="cursor:pointer;" value="" onclick="return getCalendar(document.getElementById('dateFrom'),null,document.getElementById('dateTo'));" size="10">
		            &nbsp;<fmt:message key="label.to"/>&nbsp;&nbsp;
		            <input type="text" name="dateTo" id="dateTo" readonly class="inputBox pointer"  style="cursor:pointer;" value="" onclick="return getCalendar(document.getElementById('dateTo'),document.getElementById('dateFrom'));" size="10">
		        </td>
		    </tr>
      	</c:when>
      	<c:otherwise>
      		<input type="hidden" name="contractNumber" id="contractNumber" value="SPE4A616D0226"/>
      		<tr class="alignLeft">
      			<td width="5%" class="optionTitleBoldRight" colspan="3" nowrap>
		            <select name="dateOpt" id="dateOpt" class="selectBox">
		                  <option value="orderDate"><fmt:message key="label.usgovorderdate"/></option>
		                  <option value="dateShipped"><fmt:message key="label.actualshipdate"/></option>
		                  <option value="dateSent"><fmt:message key="label.datesent"/></option>
		                  <option value="desiredShipDate"><fmt:message key="label.dlametricdate"/></option>
		            </select>
		            <fmt:message key="label.from"/>
		            <input type="text" name="dateFrom" id="dateFrom" readonly class="inputBox pointer"  style="cursor:pointer;" value="" onclick="return getCalendar(document.getElementById('dateFrom'),null,document.getElementById('dateTo'));" size="10">
		            &nbsp;<fmt:message key="label.to"/>&nbsp;&nbsp;
		            <input type="text" name="dateTo" id="dateTo" readonly class="inputBox pointer"  style="cursor:pointer;" value="" onclick="return getCalendar(document.getElementById('dateTo'),document.getElementById('dateFrom'));" size="10">
		        </td>
      		</tr>
      		<tr> 
		      <c:choose>
			      <c:when test="${canSearchHubs == 'true'}">
			      <td class="optionTitleBoldRight">
			                <fmt:message key="label.hub"/>:&nbsp;
			       </td>
			      <td class="optionTitleBoldLeft">
			          <select name="hub" id="hub" class="selectBox">
				          <option value="ALL" selected><fmt:message key="label.all" /></option>
				          <c:forEach var="hubBean" items="${dlaHubBeanCollection}" varStatus="status">
				                <option value="${status.current.branchPlant}">${status.current.hubName}</option>
				          </c:forEach>
			          </select>
			        </td>
			        </c:when>
			        <c:otherwise>
			        <td/><td/>
			        </c:otherwise>
		        </c:choose>
		     </tr>
      	</c:otherwise>
      </c:choose>

      <tr class="alignLeft">
      <td colspan="6" class="optionTitleLeft">
		  <input type="submit" name="search" id="search" value="<fmt:message key="label.search"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="return submitSearchForm()"/>
 		  <input name="CreateExcelFile" id="CreateExcelFile" type="button" value="<fmt:message key="label.createexcelfile"/>" class="inputBtns"  onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return generateExcel()">
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
<input type="hidden" name="userAction" id="userAction" value="search"/>
<input type="hidden" name="startSearchTime" id="startSearchTime" value=""/>
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>
