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
<script type="text/javascript" src="/js/client/purchasing/purchaserequestssearch.js"></script>

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
<fmt:message key="purchaserequests.label.title"/>
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

<body bgcolor="#ffffff" onload="setSearchFrameSize();">

<tcmis:form action="/purchaserequestsresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

<div class="interface" id="searchMainPage">

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="740" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
       <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
				<tr class="">
				<td width="15%" class="optionTitleBoldRight">
					 <fmt:message key="label.inventorygroup"/>:
				 </td>
				 <td width="30%" class="optionTitleLeft">
				 <c:set var="selectedIg" value='${param.inventoryGroup}'/>
				
	       <c:set var="inventoryGroupColl" value='${vvInventoryGroupCollection}'/>
	       <bean:size id="inventoryGroupSize" name="inventoryGroupColl"/>
				 <select name="inventoryGroup" id="inventoryGroup" class="selectBox">
					 <c:choose>
						 <c:when test="${inventoryGroupSize == 0}">
               <option value=""><fmt:message key="label.all"/></option>
             </c:when>
             <c:otherwise>
              <c:if test="${inventoryGroupSize > 1}">
	              <option value=""><fmt:message key="label.all"/></option>
	            </c:if>
						 </c:otherwise>
					 </c:choose>

					 <c:forEach var="personnelNameUserGroupViewBean" items="${vvInventoryGroupCollection}" varStatus="status">
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
				 </select>
				 </td>
				 <%--
				 <td width="15%" class="optionTitleBoldRight">
					<fmt:message key="label.buyer"/>:
				 </td>
				 <td width="30%" class="optionTitleLeft">
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
				 --%>

				 <td width="8%" class="optionTitleBoldRight">
					 <fmt:message key="label.search"/>:
				 </td>

				 <td width="42%" class="optionTitleLeft">
				  <html:select property="searchWhat" styleId="searchWhat" styleClass="selectBox">
						<html:option value="poNumber" key="label.customerpo"/>
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
				 <td width="15%">&nbsp;</td>

				 <td width="30%" class="optionTitleLeft" >
					<c:choose>
					<c:when test="${empty submitSearch && empty submitUpdate && empty submitCreatePo && empty submitCreateReport}">
					 <input type="checkbox" name="showOnlyOpenBuys" id="showOnlyOpenBuys" value="Yes" checked class="radioBtns">
					</c:when>
					<c:otherwise>
					 <html:checkbox property="showOnlyOpenBuys" styleId="showOnlyOpenBuys" value="Yes" styleClass="radioBtns"/>
					</c:otherwise>
					</c:choose>
					<fmt:message key="purchaserequests.label.showonlyopenbuys"/>
				</td>
					<%-- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; --%>
				<td width="8%" class="optionTitleBoldRight">
					<fmt:message key="label.sortby"/>:
				</td>
				<td width="42%" class="optionTitleLeft">
					<html:select property="sortBy" styleId="sortBy" styleClass="selectBox">
					 <html:option value="itemId" key="label.item"/>
					</html:select>
				 </td>

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
<input type="hidden" name="supplyPath" id="supplyPath" value="Customer">
<input name="startSearchTime" id="startSearchTime" type="hidden"value="">
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>