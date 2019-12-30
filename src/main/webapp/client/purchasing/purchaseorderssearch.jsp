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
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/client/purchasing/purchaseorderssearch.js"></script>

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
<fmt:message key="purchaseorders.label.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="mySearchOnload();">

<tcmis:form action="/purchaseordersresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

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
       <table width="700" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
				<tr class="">
				 <td width="20%" class="optionTitleBoldRight">
					<fmt:message key="label.enteredby"/>:
				 </td>
				 <td width="20%" class="optionTitleLeft">
					<select name="buyerId" id="buyerId" class="selectBox">
					<c:set var="selectedBuyerId" value='${param.buyerId}'/>
					<c:if test="${empty submitSearch && empty submitUpdate && empty submitCreatePo && empty selectedBuyerId}">
					 <c:set var="selectedBuyerId" value='${sessionScope.personnelBean.personnelId}'/>
					</c:if>

					<option value=""><fmt:message key="label.anybuyer"/></option>

					<c:forEach var="personnelNameUserGroupViewBean" items="${vvBuyerCollection}" varStatus="statusPersonnel">
					<c:set var="currentBuyerId" value='${statusPersonnel.current.personnelId}'/>
					<c:choose>
					 <c:when test="${currentBuyerId == selectedBuyerId}">
						<option value="${currentBuyerId}" selected>${statusPersonnel.current.name}</option>
					 </c:when>
					 <c:otherwise>
						<option value="${currentBuyerId}">${statusPersonnel.current.name}</option>
					 </c:otherwise>
					</c:choose>
					</c:forEach>
					</select>
				 </td>

				 <td width="5%" class="optionTitleBoldRight">
					 <fmt:message key="label.search"/>:
				 </td>

				 <td width="55%" class="optionTitleLeft">
				  <html:select property="searchWhat" styleId="searchWhat" styleClass="selectBox">
						<html:option value="catPartNo" key="label.catalogpartnumber"/>
						<html:option value="poNumber" key="label.customerpo"/>
						<html:option value="itemId" key="label.itemid"/>
				  </html:select>

					  <html:select property="searchType" styleId="searchType" styleClass="selectBox">
							<html:option value="CONTAINS" key="label.contain"/>
						  <html:option value="IS" key="label.is"/>
						  <html:option value="START_WITH" key="label.startswith"/>
						  <html:option value="END_WITH" key="label.endswith"/>
						</html:select>

					  <html:text property="searchText" styleId="searchText" size="20" styleClass="inputBox"/>
					</td>
				</tr>

				<tr class="">
					<td class="optionTitleBoldRight">
					 	<fmt:message key="label.inventorygroup"/>:
				 	</td>
				 	<td class="optionTitleLeft">
				 		<c:set var="selectedIg" value='${param.inventoryGroup}'/>
						<c:set var="inventoryGroupColl" value='${vvInventoryGroupCollection}'/>
				       	<bean:size id="inventoryGroupSize" name="inventoryGroupColl"/>
				 		<select name="inventoryGroup" id="inventoryGroup" class="selectBox">
			          		<c:if test="${inventoryGroupSize == 0 || inventoryGroupSize > 1}">
	              				<option value=""><fmt:message key="label.all"/></option>
	            			</c:if>
							<c:forEach var="bean" items="${vvInventoryGroupCollection}" varStatus="status">
								<option value="${bean.inventoryGroup}" <c:if test="${bean.inventoryGroup == selectedIg}">selected</c:if>>${bean.inventoryGroupName}</option>
							</c:forEach>
						 </select>
				 	</td>
					<td class="optionTitleBoldRight">
					 
				 	</td>
				 	<td class="optionTitleLeft">
						<input type="checkbox" name="showOnlyOpenRequests" id="showOnlyOpenRequests" value="Yes" checked class="radioBtns">
						<fmt:message key="purchaserequests.label.showonlyopenbuys"/>
				 	</td>

				<input type="hidden" name="sort" value="raytheonPo">

				</tr>

				<tr>
					<td width="10%" colspan="2" class="optionTitleBoldLeft">
						<input name="submitSearch" id="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="button.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onclick="return submitSearchForm();">
						<input name="buttonCreateExcel" id="buttonCreateExcel" type="submit" class="inputBtns" value="<fmt:message key="label.createexcelfile"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="generateExcel(); return false;">
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
<input name="action" id="action" type="hidden" value="">
<input name="startSearchTime" id="startSearchTime" type="hidden"value="">
<input name="showPreviousPo" id="showPreviousPo" type="hidden"value="${showPreviousPo}">
<input name="previousPoNumber" id="previousPoNumber" type="hidden"value="${previousPoNumber}">	
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>