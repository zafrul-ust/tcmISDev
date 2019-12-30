<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis"%>

<html:html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp"%>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />
<!-- CSS for YUI -->
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
<!-- Add any other stylesheets you need for the page here -->

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<script type="text/javascript" src="/js/common/resultiframeresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp"%>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/hub/forcebuy.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<!-- This is for the YUI, uncomment if you will use this -->
<script type="text/javascript" src="/yui/build/yahoo/yahoo.js"></script>
<script type="text/javascript" src="/yui/build/event/event.js"></script>
<script type="text/javascript" src="/yui/build/dom/dom.js"></script>
<script type="text/javascript" src="/yui/build/animation/animation.js"></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js"></script>

<title><fmt:message key="forcebuy.title" /></title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
rpl:"<fmt:message key="label.replenishqty"/>",
needDate:"<fmt:message key="label.needdate"/>",
maximum4000:"<fmt:message key="label.maximum4000"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="standardResultOnLoad();">

<tcmis:form action="/forcebuyresults.do" onsubmit="return submitFrameOnlyOnce();">

	<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->
    <c:set var="forceBuyPermission" value='' />
	<tcmis:inventoryGroupPermission indicator="true" userGroupId="ForceBuy" inventoryGroup="${param.inventoryGroup}">
		<c:set var="forceBuyPermission" value='Yes' />
	</tcmis:inventoryGroupPermission>

	<c:if test="${forceBuyPermission == 'Yes'}">
	 <script type="text/javascript">
	 <!--
	  showUpdateLinks = true;
	 //-->
	 </script>
	</c:if>


	<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
	<!-- Error Messages Begins -->	
	<div id="errorMessagesAreaBody" style="display:none;">
      ${tcmISError}<br/>
      <c:forEach items="${tcmISErrors}" varStatus="status">
  	    ${status.current}<br/>
      </c:forEach>
    </div>

<script type="text/javascript">
<!--
YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);

/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${empty tcmISErrors and empty tcmISError}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
//-->
</script>
	<!-- Error Messages Ends -->

	<div class="interface" id="resultsPage"><!-- start of interface -->
	<div class="backGroundContent"><!-- start of backGroundContent --> 
	  <c:if test="${itemInvDetailViewBeanColl != null}">
		<!-- Search results start -->

		<c:set var="colorClass" value='' />
		<c:set var="dataCount" value='${0}' />

		<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
		<c:if test="${!empty itemInvDetailViewBeanColl}">

			<table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
				<c:forEach var="itemCountInventoryViewBean" items="${itemInvDetailViewBeanColl}" varStatus="status">

			     <c:if test="${status.index % 10 == 0}">
					<!-- Need to print the header every 10 rows-->
    			          <tr>
							<th width="8%"><fmt:message key="label.inventorygroup" /></th>
							<th width="5%"><fmt:message key="label.catalog" /></th>
							<th width="8%"><fmt:message key="label.part" /><br /><fmt:message key="label.number" /></th>
							<th width="5%"><fmt:message key="label.specs" /></th>
							<th width="2%"><fmt:message key="label.setpointsrp/sl/rq" /></th>
							<th width="2%"><fmt:message key="label.stockingmethod" /></th>
							<th width="10%"><fmt:message key="label.inventory" /><br />
							<fmt:message key="label.uom" /></th>
							<th width="5%"><fmt:message key="label.onhand" /></th>
							<th width="2%"><fmt:message key="label.in" /><br />
							<fmt:message key="label.purchasing" /></th>
							<th width="5%"><fmt:message key="label.item" /></th>
							<th width="15%"><fmt:message key="label.itemdescription" /></th>
					<c:choose>
     		            <c:when test="${forceBuyPermission == 'Yes'}" >
							<th width="2%"><fmt:message key="label.ok" /></th>
							<th width="5%"><fmt:message key="label.replenishqty" /></th>
							<th width="5%"><fmt:message key="label.needdate" /></th>
							<th width="5%"><fmt:message key="label.notes"/></th>
     		          </c:when>
    	           </c:choose>	
    	           </tr>					
			    </c:if>

					<c:choose>
						<c:when test="${status.index % 2 == 0}">
							<c:set var="colorClass" value='' />
						</c:when>
						<c:otherwise>
							<c:set var="colorClass" value='alt' />
						</c:otherwise>
					</c:choose>
					<tr class="${colorClass}" id="rowId${status.index}" onmouseup="selectRow('${status.index}')">

          <input type="hidden" name="colorClass${status.index}" id="colorClass<c:out value="${status.index}"/>" value="${colorClass}" />
					<input type="hidden" name='itemInvDetailViewBean[${status.index}].itemId'
             id='itemId<c:out value="${status.index}"/>' value="${status.current.itemId}" />
		      <input type="hidden" name='itemInvDetailViewBean[${status.index}].itemId'
             id='itemId<c:out value="${status.index}"/>' value="${status.current.itemId}" />
					<input type="hidden" name='itemInvDetailViewBean[${status.index}].partGroupNo'
							id='partGroupNo<c:out value="${status.index}"/>' value="${status.current.partGroupNo}" />
						<input type="hidden" name='itemInvDetailViewBean[${status.index}].inventoryGroup'
							id='inventoryGroup<c:out value="${status.index}"/>' value="${status.current.inventoryGroup}" />
						<input type="hidden" name='itemInvDetailViewBean[${status.index}].catalogId'
							id='catalogId<c:out value="${status.index}"/>' value="${status.current.catalogId}" />
						<input type="hidden" name='itemInvDetailViewBean[${status.index}].catPartNo'
							id='catPartNo<c:out value="${status.index}"/>' value="${status.current.catPartNo}" />
						<input type="hidden" name='itemInvDetailViewBean[${status.index}].facilityId'
							id='facilityId<c:out value="${status.index}"/>' value="${status.current.facilityId}" />
						<input type="hidden" name='itemInvDetailViewBean[${status.index}].application'
							id='application<c:out value="${status.index}"/>' value="${status.current.application}" />
						<input type="hidden" name='itemInvDetailViewBean[${status.index}].companyId'
							id='companyId<c:out value="${status.index}"/>' value="${status.current.companyId}" />
						<input type="hidden" name='itemInvDetailViewBean[${status.index}].catalogCompanyId'
							id='catalogCompanyId<c:out value="${status.index}"/>' value="${status.current.catalogCompanyId}" />                        
                        <%--<input type="hidden" name='itemInvDetailViewBean[${status.index}].current_icmrcStatus'
							id='current_icmrcStatus"${status.index}"/>' value="${status.current.icmrcStatus}" />--%>

              <td width="8%">${status.current.inventoryGroup}</td>
						<td width="5%">${status.current.catalogId}</td>
						<td width="8%">${status.current.catPartNo}</td>
						<td width="2%">${status.current.specList}</td>
						<td width="2%">
						${status.current.reorderPoint}/${status.current.stockingLevel}/${status.current.reorderQuantity}</td>
						<td width="2%">${status.current.stockingMethod}</td>
						<td width="10%">${status.current.itemPackaging}</td>
						<td width="5%">${status.current.partOnHand}</td>
						<td width="2%">${status.current.partInPurchasing}</td>
						<td width="5%">${status.current.itemId}</td>
						<td width="15%">${status.current.itemDesc}</td>
						<c:if test="${forceBuyPermission == 'Yes'}">
							<c:choose>
								<c:when test="${status.current.itemType == 'MA' || status.current.itemType == 'MX' || status.current.itemType == 'MV' || status.current.itemType == 'OB'}">
									<td width="2%"><input type="checkbox" name='itemInvDetailViewBean[${status.index}].okDoUpdate'
										id='okDoUpdate${status.index}' value='${status.index}' onClick="checkValues(${status.index})" /></td>
									<td width="5%"><input class="inputBox" name='itemInvDetailViewBean[${status.index}].replenishQty'
										id='replenishQty${status.index}' type="text" value="" size="6" onChange="checkQtyValue(${status.index})" /></td>
									<td width="5%"><input name='itemInvDetailViewBean[${status.index}].needDateAfter'
										id='needDateAfter${status.index}' type="hidden"
										value='<tcmis:getDateTag numberOfDaysFromToday="-1" datePattern="${dateFormatPattern}"/>' /> <input readonly
										class="inputBox" name='itemInvDetailViewBean[${status.index}].needDate' id='needDate${status.index}'
										type="text" value='<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>' size=8
										onclick='return getCalendar(document.genericForm.needDate${status.index},document.getElementById("needDateAfter${status.index}"))'
										onChange="checkCalendarValue(${status.index})" /></td>
									<td width="5%"><textarea name="itemInvDetailViewBean[${status.index}].comment" id="comment${status.index}"
										onKeyDown="limitText(${status.index},4000);" onKeyUp="limitText(${status.index},4000);" cols="25" rows="3"
										class="inputBox"></textarea></td>
								</c:when>
								<c:otherwise>
									<td width="2%">&nbsp;</td>
									<td width="5%">&nbsp;</td>
									<td width="5%">&nbsp;</td>
									<td width="5%">&nbsp;</td>
								</c:otherwise>
							</c:choose>
						</c:if>
					</tr>					


					<c:set var="dataCount" value='${dataCount+1}' />
				</c:forEach>
			</table>
		</c:if>

		<!-- If the collection is empty say no data found -->
		<c:if test="${empty itemInvDetailViewBeanColl}">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
				<tr>
					<td width="100%"><fmt:message key="main.nodatafound" /></td>
				</tr>
			</table>
		</c:if>

		<!-- Search results end -->
	</c:if> <!-- Hidden element start -->
	<div id="hiddenElements" style="display: none;">
	    <input name='todayDate' id='todayDate' type="hidden" value='<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>'/>							 
	    <input name="totalLines" id="totalLines" value="${dataCount}" type="hidden" />
		<input name="uAction" id="uAction" value="update" type="hidden" />
		<input name="opsEntityId" id="opsEntityId" value="${param.opsEntityId}" type="hidden" />
		<input name="hub" id="hub" value="${param.hub}" type="hidden" />
		<input name="inventoryGroup" id="inventoryGroup" value="${param.inventoryGroup}" type="hidden" />
		<input name="searchArgument" id="searchArgument" value="${param.searchArgument}" type="hidden" />
		<input name="searchField" id="searchField" value="${param.searchField}" type="hidden" />
		<input name="searchMode" id="searchMode" value="${param.searchMode}" type="hidden" />
		<input name="showMinMaxOnly" id="showMinMaxOnly" value="${param.showMinMaxOnly}" type="hidden" /> 
	
	</div>
	<!-- Hidden elements end --></div>
	<!-- close of backGroundContent --></div>
	<!-- close of interface -->

</tcmis:form>
</body>
</html:html>
