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
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>


<!-- Add any other javascript you need for the page here -->
<script src="/js/hub/openpicklist.js" language="JavaScript"></script>

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
<fmt:message key="openpicklist.title"/>
</title>

<script language="JavaScript" type="text/javascript">
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={     alert:"<fmt:message key="label.alert"/>",
                     and:"<fmt:message key="label.and"/>",
          submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
          pleaseSelectId:"<fmt:message key="openpicklist.alert"/>"};
</script>
  
</head>

<body bgcolor="#ffffff" onload="setSearchFrameSize();">

<tcmis:form action="/picklistreprintresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

<div class="interface" id="searchMainPage">

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="400" border="0" cellpadding="0" cellspacing="0">
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
        <td colspan="3" class="optionTitleBoldLeft"><fmt:message key="openpicklist.pleaseselect"/></td>
      </tr>
      <tr>
        <td class="optionTitleBoldLeft">
           <select name="picklistId" class="selectBox" id="picklistId">
               <option value=''><fmt:message key="openpicklist.idtime"/></option>
             <c:forEach var="picklist" items="${openPicklistColl}" varStatus="status">
               <fmt:formatDate var="fmtTimeGenerated" value="${picklist.picklistPrintDate}" pattern="EEE MMM dd yyyy HH:mm a zzz"/>
               <option value='<c:out value="${picklist.picklistId}"/>'><fmt:message key="openpicklist.picklist.prefix"/> <c:out value="${picklist.picklistId}"/> : <c:out value="${fmtTimeGenerated}"/></option>
             </c:forEach>
           </select>
        </td>
        <td width="5%" class="optionTitleBoldLeft" colspan="2" nowrap>&nbsp;&nbsp;&nbsp;
            <fmt:message key="label.search" />:
					  <select name="searchField" id="searchField" class="selectBox">
            <option value="consolidationNumber"><fmt:message key="label.consolidationno" /></option>
            <option value="carrierCode"><fmt:message key="label.carrier" /></option>
            <option value="prNumber" selected="selected"><fmt:message key="label.mr" /></option>												
						<option value="shipToZip"><fmt:message key="label.shiptozip" /></option>
						<option value="transportationPriority"><fmt:message key="label.transportationpriority" /></option>
					  </select>
		        <html:select property="searchMode" styleId="searchMode" styleClass="selectBox">
						<html:option value="is">
							<fmt:message key="label.is" />
						</html:option>
						<html:option value="contains">
							<fmt:message key="label.contains" />
						</html:option>
						<html:option value="startsWith" key="label.startswith" />
						<html:option value="endsWith" key="label.endswith" />
					</html:select> 
 		            <input class="inputBox" type="text" name="searchArgument" id="searchArgument"
						size="20" onkeypress="return onKeyPress()"></td>         
      </tr>      
      <tr>
					<td width="5%" class="optionTitleBoldRight" nowrap><fmt:message key="label.sortby" />: 
					 <c:set var="selectedSortBy" value="${param.sortBy}" />
                    <html:select property="sortBy" styleId="sortBy" styleClass="selectBox">
                     <c:choose>
                       <c:when test="${param.fromPickingPicklist == 'Y'}">
                        <html:option value="facilityId,application" key="picklistpicking.facilityidworkarea"/>
                        <html:option value="customerServiceRepName" key="label.csr"/>
                        <html:option value="itemId" key="label.itemid"/>
                        <html:option value="prNumber" key="label.mr"/>
                        <html:option value="needDate" key="label.needdate"/>
                        <html:option value="catPartNo" key="label.partnumber"/>
                        <html:option value="shipToLocationId" key="label.shipto"/>
                       </c:when>
                       <c:otherwise>
						<html:option value="consolidationNumber" key="label.consolidationno" />
						<html:option value="bin,consolidationNumber" key="label.bin" />
						<html:option
							value="pickupTime,carrierCode,trailerNumber,stopNumber,shipToCity,shipToStateAbbrev,shipToDodaac,consolidationNumber"
							key="label.datetimecarriertrailerstop" />
						<html:option value="shipToCity,shipToStateAbbrev,shipToDodaac,pickupTime,carrierCode,consolidationNumber"
							key="label.shiptododaacdatetimecarrier" />
                       </c:otherwise>
                    </c:choose>
                    </html:select>                         
                    </td>
					<td class="optionTitleBoldLeft">&nbsp;&nbsp;&nbsp;
					  <input name="showOCONUSonly" id="showOCONUSonly" type="checkbox"
						class="radioBtns" value="YES"><fmt:message key="label.showoconusonly" /> &nbsp;&nbsp;&nbsp; 
					  <input name="showHazardousOnly" id="showHazardousOnly" type="checkbox" class="radioBtns" value="YES">
					  <fmt:message key="label.showhazardousonly" /> &nbsp;&nbsp;&nbsp;</td>
				</tr>     
      <tr>
        <td class="optionTitleBoldLeft" colspan="2">
           <input name="submitSearch" id="submitSearch" type="submit" class="inputBtns" value='<fmt:message key="label.search"/>' onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="submitSearchForm()">
           &nbsp;
	       <input name="createExcel" id="createExcel" type="button" value="<fmt:message key="label.createexcelfile"/>" onclick="generatePickListExcel();" class="inputBtns"  onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"/>
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
<input name="action" id="action" type="hidden" value="search">
<input name="sortBy" id="sortBy" type="hidden" value="${param.sortBy}">
<input type="hidden" name="startSearchTime" id="startSearchTime" value="" />  
<input type="hidden" name="fromPickingPicklist" id="fromPickingPicklist" value="${param.fromPickingPicklist}"/>
<input type="hidden" name="fromCarrierPicking" id="fromCarrierPicking" value="${param.fromCarrierPicking}"/>
<input type="hidden" name="inventoryGroup" id="inventoryGroup" value="${param.inventoryGroup}"/>
<input type="hidden" name="opsEntityId" id="opsEntityId" value="${param.opsEntityId}"/>		
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>