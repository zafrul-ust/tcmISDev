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
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->

<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/operations/itemmanagementresults.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
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
<fmt:message key="label.item"/> <fmt:message key="label.management"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",replenishqty:"<fmt:message key="label.replenishqty"/>",
needDate:"<fmt:message key="label.needdate"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>" ,
maximum4000:"<fmt:message key="label.maximum4000"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="myResultsOnload()">

<tcmis:form action="/itemmanagementresults.do" onsubmit="return submitFrameOnlyOnce();">

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->

<c:set var="updatePermission" value=''/>
<tcmis:inventoryGroupPermission indicator="true" userGroupId="ItemMgmt" inventoryGroup="${param.inventoryGroup}">
 <c:set var="updatePermission" value='Yes'/>
</tcmis:inventoryGroupPermission>

<c:set var="forceBuyPermission" value=''/>
<tcmis:inventoryGroupPermission indicator="true" userGroupId="ForceBuy" inventoryGroup="${param.inventoryGroup}">
 <c:set var="forceBuyPermission" value='Yes'/>
</tcmis:inventoryGroupPermission>

<c:if test="${inventoryGroupPermission == 'Yes' || forceBuyPermission == 'Yes'}">
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
 <html:errors/>
</div>

<script type="text/javascript">
<!--
/*YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);*/

/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
//-->
</script>
<!-- Error Messages Ends -->

<div class="interface" id="resultsPage"> <!-- start of interface -->
<div class="backGroundContent"> <!-- start of backGroundContent -->

<c:if test="${itemInvDetailViewBeanColl != null}" >
<!-- Search results start -->

<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>

 <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
 <c:if test="${!empty itemInvDetailViewBeanColl}" >

    <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
    <c:forEach var="itemCountInventoryViewBean" items="${itemInvDetailViewBeanColl}" varStatus="status">

    <c:if test="${status.index % 10 == 0}">
    <!-- Need to print the header every 10 rows-->
    	<c:choose>
     		<c:when test="${status.current.issueGeneration == 'Counting' }" >
    			<tr>
					<th width="5%"><fmt:message key="label.inventorygroup"/><br>(<fmt:message key="label.catalog"/>)</th>
					<th width="5%"><fmt:message key="label.part"/><br/><fmt:message key="label.number"/></th>
					<th width="5%"><fmt:message key="label.setpointsrp/sl/rq"/></th>
					<th width="5%"><fmt:message key="label.receipt"/><br/><fmt:message key="label.processing"/><br/><fmt:message key="label.method"/></th>
					<th width="5%"><fmt:message key="label.billing"/><br/><fmt:message key="label.method"/></th>
					<th width="5%"><fmt:message key="label.stockingmethod"/></th>
					<th width="5%"><fmt:message key="label.usage"/><br/><fmt:message key="label.facility"/></th>
					<th width="5%"><fmt:message key="label.usage"/><br/><fmt:message key="label.workarea"/></th>
					<th width="5%"><fmt:message key="label.count"/><br/><fmt:message key="label.uom"/></th>
					<th width="5%"><fmt:message key="label.inventory"/><br/><fmt:message key="label.uom"/></th>
					<th width="5%"><fmt:message key="label.onhand"/><br/>(<fmt:message key="label.last"/><br/><fmt:message key="label.counted"/>)</th>
					<th width="5%"><fmt:message key="label.in"/><br/><fmt:message key="label.purchasing"/></th>
					<th width="5%"><fmt:message key="label.item"/></th>
					<th width="15%"><fmt:message key="label.itemdescription"/></th>
					<th width="2%"><fmt:message key="label.ok"/></th>
					<th width="5%"><fmt:message key="label.replenishqty"/></th>
					<th width="8%"><fmt:message key="label.needdate"/></th>
					<th width="5%"><fmt:message key="label.notes"/></th>
					<th width="5%"><fmt:message key="label.status"/></th>
    			</tr>
     		</c:when>
     		<c:otherwise>
    			<tr>
					<th width="6%"><fmt:message key="label.inventorygroup"/><br>(<fmt:message key="label.catalog"/>)</th>
					<th width="6%"><fmt:message key="label.part"/><br/><fmt:message key="label.number"/></th>
					<th width="6%"><fmt:message key="label.setpointsrp/sl/rq"/></th>
					<th width="6%"><fmt:message key="label.stockingmethod"/></th>
					<th width="6%"><fmt:message key="label.inventory"/><br/><fmt:message key="label.uom"/></th>
					<th width="5%"><fmt:message key="label.onhand"/></th>
					<th width="6%"><fmt:message key="label.in"/><br/><fmt:message key="label.purchasing"/></th>
					<th width="6%"><fmt:message key="label.item"/></th>
					<th width="15%"><fmt:message key="label.itemdescription"/></th>
					<th width="2%"><fmt:message key="label.ok"/></th>
					<th width="6%"><fmt:message key="label.replenishqty"/></th>
					<th width="8%"><fmt:message key="label.needdate"/></th>
					<th width="5%"><fmt:message key="label.notes"/></th>
    			</tr>
     		</c:otherwise>
    	</c:choose>
    </c:if>

    <c:choose>
     <c:when test="${status.index % 2 == 0}" >
      <c:set var="colorClass" value=''/>
     </c:when>
     <c:otherwise>
      <c:set var="colorClass" value='alt'/>
     </c:otherwise>
    </c:choose>

    <tr class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>">
    <c:choose>
    <c:when test="${status.current.issueGeneration == 'Counting' }" >
    <td width="5%"><c:out value="${status.current.inventoryGroup}"/><br>(<c:out value="${status.current.catalogId}"/>)</td>
		<td width="5%" ><c:out value="${status.current.catPartNo}"/></td>
		<td width="5%">
			<a href="javascript:showMinMax('<c:out value="${status.current.catPartNo}"/>','<c:out value="${status.current.inventoryGroup}"/>','<c:out value="${status.current.partGroupNo}"/>','<c:out value="${status.current.countUom}"/>')">
				<c:out value="${status.current.reorderPoint}"/>/<c:out value="${status.current.stockingLevel}"/>/<c:out value="${status.current.reorderQuantity}"/>
			</a>
		</td>
		<td width="5%" ><c:out value="${status.current.receiptProcessingMethodDesc}"/></td>
		<td width="5%" ><c:out value="${status.current.billingMethod}"/></td>
		<td width="5%" ><c:out value="${status.current.stockingMethod}"/></td>
		<td width="5%" ><c:out value="${status.current.facilityId}"/></td>
		<td width="5%" ><c:out value="${status.current.applicationDesc}"/></td>
		<td width="5%" ><c:out value="${status.current.countUom}"/></td>
		<td width="5%" ><c:out value="${status.current.itemPackaging}"/></td>
		<fmt:formatDate var="fmtLastCountDate" value="${status.current.lastCountDate}" pattern="${dateFormatPattern}"/>
		<td width="5%" ><c:out value="${status.current.partOnHand}"/><br/>(<c:out value="${fmtLastCountDate}"/>)</td>
		<td width="5%" ><c:out value="${status.current.partInPurchasing}"/></td>
		<td width="5%" ><c:out value="${status.current.itemId}"/></td>
		<td width="15%"><c:out value="${status.current.itemDesc}"/></td>
		<td width="2%">
           <input type="checkbox" name='itemInvDetailViewBean[<c:out value="${status.index}"/>].okDoUpdate' id='okDoUpdate<c:out value="${status.index}"/>' value='<c:out value="${status.index}"/>' onClick="checkValues(${status.index})">
        </td>
		<td width="5%">
      <input class="inputBox" name='itemInvDetailViewBean[<c:out value="${status.index}"/>].replenishQty' id='replenishQty<c:out value="${status.index}"/>' type="text" value="" size="6" onChange="checkQtyValue(${status.index})">
    </td>
    <td width="8%">
       <input class="inputBox pointer" readonly name='itemInvDetailViewBean[<c:out value="${status.index}"/>].needDate' id='needDate<c:out value="${status.index}"/>' type="text" value='<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>'
          onclick='return getCalendar(document.genericForm.needDate<c:out value="${status.index}"/>);' size=8>
 <%--    <a href='javascript:void(0);' class="optionTitleBold" id='linkNeedDate<c:out value="${status.index}"/>' onclick='return getCalendar(document.genericForm.needDate<c:out value="${status.index}"/>);'>&diams;</a>
    --%>   
    </td>
    <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
          <textarea name="itemInvDetailViewBean[${status.index}].comment" id="comment${status.index}" onKeyDown="limitText(${status.index},4000);" 
                         onKeyUp="limitText(${status.index},4000);" cols="25" rows="3" class="inputBox"></textarea>
                           </td>
		<td width="5%" >
			<!-- keep the java script onChange for now.. -->
			<select class="selectBox" name='itemInvDetailViewBean[<c:out value="${status.index}"/>].icmrcStatus' id='icmrcStatus<c:out value="${status.index}"/>' > <%-- onChange= "checkactivestatus('1')"> --%>
    			<c:choose>
     				<c:when test="${status.current.icmrcStatus == 'A' }" >
						<option selected="selected" value="A">
							<fmt:message key="label.active"/>
						</option>
						<option value="I">
							<fmt:message key="label.inactive"/>
						</option>
     				</c:when>
     				<c:otherwise>
						<option value="A">
							<fmt:message key="label.active"/>
						</option>
						<option selected="selected" value="I">
							<fmt:message key="label.inactive"/>
						</option>
     				</c:otherwise>
    			</c:choose>
			</select>
		</td>
	  </c:when>
    <c:otherwise>
		<td width="6%"><c:out value="${status.current.inventoryGroup}"/><br>(<c:out value="${status.current.catalogId}"/>)</td>
		<td width="6%" ><c:out value="${status.current.catPartNo}"/></td>
		<td width="6%">
			<%--<a href="javascript:showMinMax('<c:out value="${status.current.catPartNo}"/>','<c:out value="${status.current.inventoryGroup}"/>','<c:out value="${status.current.partGroupNo}"/>','<c:out value="${status.current.countUom}"/>')">--%>
				<c:out value="${status.current.reorderPoint}"/>/<c:out value="${status.current.stockingLevel}"/>/<c:out value="${status.current.reorderQuantity}"/></td>
			<%--</a>--%>
		</td>
		<td width="6%" ><c:out value="${status.current.stockingMethod}"/></td>
		<td width="6%" ><c:out value="${status.current.itemPackaging}"/></td>
		<td width="5%" ><c:out value="${status.current.partOnHand}"/></td>
		<td width="6%" ><c:out value="${status.current.partInPurchasing}"/></td>
		<td width="6%" ><c:out value="${status.current.itemId}"/></td>
		<td width="15%"><c:out value="${status.current.itemDesc}"/></td>
		<td width="2%">
     <input type="checkbox" name='itemInvDetailViewBean[<c:out value="${status.index}"/>].okDoUpdate' id='okDoUpdate<c:out value="${status.index}"/>' value='<c:out value="${status.index}"/>' onClick="checkValues(${status.index})">
    </td>
		<td width="5%">
       <input class="inputBox" name='itemInvDetailViewBean[<c:out value="${status.index}"/>].replenishQty' id='replenishQty<c:out value="${status.index}"/>' type="text" value="" size="6" onChange="checkQtyValue(${status.index})">
    </td>
    <td width="8%">
       <input class="inputBox pointer" readonly name='itemInvDetailViewBean[<c:out value="${status.index}"/>].needDate' id='needDate<c:out value="${status.index}"/>' 
         type="text" value='<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>' onclick='return getCalendar(document.genericForm.needDate<c:out value="${status.index}"/>);' size=8>
<%-- 		 <a href='javascript:void(0);' class="optionTitleBold" id='linkNeedDate<c:out value="${status.index}"/>' onclick='return getCalendar(document.genericForm.needDate<c:out value="${status.index}"/>);'>&diams;</a>
    --%>	
    </td>
    <td width="5%">
                              <textarea name="itemInvDetailViewBean[${status.index}].comment" id="comment${status.index}" onKeyDown="limitText(${status.index},4000);" 
                         onKeyUp="limitText(${status.index},4000);" cols="25" rows="3" class="inputBox"></textarea>
                           </td>
		</c:otherwise>
 	  </c:choose>

		<input type="hidden" name='itemInvDetailViewBean[<c:out value="${status.index}"/>].itemId' id='itemId<c:out value="${status.index}"/>' value="<c:out value="${status.current.itemId}"/>">
		<input type="hidden" name='itemInvDetailViewBean[<c:out value="${status.index}"/>].partGroupNo' id='partGroupNo<c:out value="${status.index}"/>' value="<c:out value="${status.current.partGroupNo}"/>">
		<input type="hidden" name='itemInvDetailViewBean[<c:out value="${status.index}"/>].inventoryGroup' id='inventoryGroup<c:out value="${status.index}"/>' value="<c:out value="${status.current.inventoryGroup}"/>">
		<input type="hidden" name='itemInvDetailViewBean[<c:out value="${status.index}"/>].catalogId' id='catalogId<c:out value="${status.index}"/>' value="<c:out value="${status.current.catalogId}"/>">
		<input type="hidden" name='itemInvDetailViewBean[<c:out value="${status.index}"/>].catPartNo' id='catPartNo<c:out value="${status.index}"/>' value="<c:out value="${status.current.catPartNo}"/>">
		<input type="hidden" name='itemInvDetailViewBean[<c:out value="${status.index}"/>].facilityId' id='facilityId<c:out value="${status.index}"/>' value="<c:out value="${status.current.facilityId}"/>">
		<input type="hidden" name='itemInvDetailViewBean[<c:out value="${status.index}"/>].application' id='application<c:out value="${status.index}"/>' value="<c:out value="${status.current.application}"/>">
		<input type="hidden" name='itemInvDetailViewBean[<c:out value="${status.index}"/>].companyId' id='companyId<c:out value="${status.index}"/>' value="<c:out value="${status.current.companyId}"/>">
		<input type="hidden" name='itemInvDetailViewBean[<c:out value="${status.index}"/>].current_icmrcStatus' id='current_icmrcStatus<c:out value="${status.index}"/>' value="<c:out value="${status.current.icmrcStatus}"/>">

	</tr>

   <c:set var="dataCount" value='${dataCount+1}'/>
   </c:forEach>
   </table>
   </c:if>

   <!-- If the collection is empty say no data found -->
   <c:if test="${empty itemInvDetailViewBeanColl}" >
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
     </tr>
    </table>
   </c:if>

<!-- Search results end -->
</c:if>

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
 <input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">
 <input name='todayDate' id='todayDate' type="hidden" value='<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>'/>	
 <!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<%-- <tcmis:saveRequestParameter/>--%>
<input name="plantId" id="plantId" type="hidden" value="${param.plantId}">
<input name="submitSearch" id="submitSearch" type="hidden" value="${param.submitSearch}">
<input name="searchArgument" id="searchArgument" type="hidden" value="${param.searchArgument}">
<input name="userAction" id="userAction" type="hidden" value="${param.searchArgument}">
<input name="bldgId" id="bldgId" type="hidden" value="${param.bldgId}">
<input name="searchMode" id="searchMode" type="hidden" value="${param.searchMode}">
<input name="searchField" id="searchField" type="hidden" value="${param.searchField}">
<input name="hub" id="hub" type="hidden" value="${param.hub}">
<input name="inventoryGroup" id="inventoryGroup" type="hidden" value="${param.inventoryGroup}">  
 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>