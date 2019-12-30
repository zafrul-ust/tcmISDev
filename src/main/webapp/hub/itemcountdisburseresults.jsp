<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>
<html:html lang="true">
<head>
	<jsp:useBean id="today" class="java.util.Date"/>
	<c:set var="javascriptDate"><fmt:message key="javascript.dateformat"/></c:set>
	
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta http-equiv="expires" content="-1">
	<link rel="shortcut icon" href="/images/buttons/tcmIS.ico">
	</link>
	<%@ include file="/common/locale.jsp" %>

	<!--Use this tag to get the correct CSS class.
		This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
	<tcmis:gridFontSizeCss />
	<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />	

	<!-- Add any other stylesheets you need for the page here -->
	<script type="text/javascript" src="/js/common/formchek.js"></script>
	<script type="text/javascript" src="/js/common/commonutil.js"></script>

	<%--NEW - grid resize--%>
	<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
	
	<!-- This handles which key press events are disabled on this page -->
	<script src="/js/common/disableKeys.js"></script>
	
	<!-- This handles the menu style and what happens to the right click on the whole page -->
	<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
	<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
	<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
	<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
	
	<%@ include file="/common/rightclickmenudata.jsp" %>

	<!-- Add any other javascript you need for the page here -->
	<script type="text/javascript" src="/js/hub/itemcountdisbursementresults.js"></script>

	<!-- For Calendar support -->
	<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
	<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
	<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
	
	<!-- These are for the Grid, uncomment if you are going to use the grid -->
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
	
	<%--Uncomment below if you are providing header menu to switch columns on/off--%>
	<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
	
	<%--Uncomment the below if your grid has rowspans --%>

<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
 
	<%--This is to allow copy and paste. works only in IE--%>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_selection.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_nxml.js"></script>
	
	<%--This has the custom cells we built, hcal - the internationalized calendar which we use hlink- this is for any links you want tp provide in the grid, the URL/function to call can be attached based on a even (rowselect etc) --%>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
	
	<%--Custom sorting. This custom sorting function implements case insensitive sorting. --%>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
		
	<!-- Popup window support -->
	<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>	

	<script language="JavaScript" type="text/javascript">
	var rowSpanMap = new Array();
	var rowSpanClassMap = new Array();
	var rowSpanCols = [0,1,2];
	
	
		//add all the javascript messages here, this for internationalization of client side javascript messages
		var messagesData = new Array();
		messagesData= {
		       alert: "<fmt:message key="label.alert"/>",
		       and: "<fmt:message key="label.and"/>", 
		       validvalues: "<fmt:message key="label.validvalues"/>",
			additem:"<fmt:message key="label.waitingforinputfrom"><fmt:param><fmt:message key="label.additem"/></fmt:param></fmt:message>",
			submitOnlyOnce: "<fmt:message key="label.submitOnlyOnce"/>",
			itemInteger: "<fmt:message key="error.item.integer"/>",
			recordFound: "<fmt:message key="label.recordFound"/>",
			searchDuration: "<fmt:message key="label.searchDuration"/>",
			minutes: "<fmt:message key="label.minutes"/>",
			seconds: "<fmt:message key="label.seconds"/>",
			pleasewait: "<fmt:message key="label.pleasewaitmenu"/>",
			item: "<fmt:message key="label.item"/>",
			actualCount: "<fmt:message key="label.actualcount"/>",
			nonZeroCount:"<fmt:message key="label.nonnegativenumber"><fmt:param><fmt:message key="label.actualcount"/></fmt:param></fmt:message>",
			linesexcluded: "<fmt:message key="label.linesexcluded"/>",
			issueonreceiptmessage: "<fmt:message key="label.issueonreceiptmessage"/>",
			quantitycounted: "<fmt:message key="error.quantitycountedpercent"/>",
			doublecheckquantity: "<fmt:message key="label.doublecheckquantity"/>",
			countedaftercurrentcount: "<fmt:message key="error.countedaftercurrentcount"/>",
			receivedaftercurrentcount: "<fmt:message key="error.receivedaftercurrentcount"/>",
			receivedaftercountstart: "<fmt:message key="error.receivedaftercountstart"/>",
			missingdisbursementvals: "<fmt:message key="error.missingdisbursementvals"/>",
			additemcountwarning: "<fmt:message key="label.additemcountwarning"/>"
	       };
	       
		<%-- See webroot/dhtmlxgrid/codebase/dhtmlxcommon_haas.js for option explanations of config & gridconfig --%>
		   <c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set>
		   <c:set var="inpurchasing"><fmt:message key="label.inpurchasing"/></c:set>  
	       var config = [ 
	       		{columnId: "permission"},
	        {columnId: "mdItemId", columnName:'<fmt:message key="label.disbursement"/>', tooltip:true, submit:true},
			{columnId: "catPartNo", columnName:'<fmt:message key="label.partnumber"/>', tooltip:true, submit:true},
			{columnId: "inventoryGroupName", columnName:'<tcmis:jsReplace value="${inventorygroup}"/>'},
			{columnId: "itemId", columnName:'<fmt:message key="label.item"/>', width:6, align:"center", submit:true},
			{columnId: "description", columnName:'<fmt:message key="label.description"/>', tooltip:true, width:20},
			{columnId: "inPurchasing"},
			{columnId: "packaging"},       		
			{columnId: "inventoryQuantity", columnName:'<fmt:message key="label.onhand"/>',attachHeader:'<fmt:message key="label.quantity"/>', sort:"int", align:"right"},
			{columnId: "uom", columnName:'#cspan', attachHeader:'<fmt:message key="label.countuom"/>', tooltip:true},       		
			{columnId: "lastBin", columnName:'<fmt:message key="label.bin"/>', tooltip:true, width:8},
			{columnId: "countedQuantity", columnName:'<fmt:message key="label.actualcount"/>', type:"hed", width:7, maxlength:22, size:5, align:"center", onChange:"validateCountedQuantity", submit:true},
			{columnId: "counted", columnName:'<fmt:message key="label.counted"/><br><input type="checkbox" value="" onClick="return checkAll(\'counted\');" name="checkAllCounted" id="checkAllCounted">', type:"hchstatus", width:6, align:"center", onChange:"countedChecked", submit:true, sorting:"haasHch"},
			{columnId: "issueOnReceipt", submit:true},
			{columnId: "companyId", submit:true},
			{columnId: "catalogId", submit:true},
			{columnId: "partGroupNo", submit:true},
			{columnId: "countId", submit:true},
			{columnId: "countType", submit:true},
			{columnId: "lastDateOfReceipt"},
			{columnId: "lastReceiptQcDate"},
			{columnId: "lastDateCounted"},
			{columnId: "startDate"},
			{columnId: "expectedQuantity"},
			{columnId: "updatePerm"}
	       ];
	       
	       var gridConfig = {
		       divName:'ItemCountDisplayViewBean', // the div id to contain the grid.
		       beanData:'jsonMainData', // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		       beanGrid:'mygrid', // the grid's name, as in beanGrid.attachEvent...
		       config:config, // the column config var name, as in var config = { [ columnId:..,columnName...
		       rowSpan:true, // true: this page has rowSpan > 1 or not, disables smartrendering & sorting
		       submitDefault:false,
			   	noSmartRender: false, // Explicitly enable smartrender by setting this to false for rowspans
				variableHeight:false,
				selectChild: 1
	       };
       
      </script>
</head>
<body bgcolor="#ffffff" onload="myResultOnLoadWithGrid(gridConfig);">
	<tcmis:form action="/itemcountresults.do" onsubmit="return submitFrameOnlyOnce();">
		<script  language="JavaScript" type="text/javascript">
			<c:if test= "${!empty ItemCountViewBeanCollection}" >
				var jsonMainData = {
					rows: [
						<c:forEach var= "p" items= "${ItemCountViewBeanCollection}" varStatus= "status" >
							<c:set var="updatePerm" value="N"/>
							<tcmis:inventoryGroupPermission indicator="true" userGroupId="Hub ItemCount" inventoryGroup="${p.inventoryGroup}">
								<c:set var="updatePerm" value="Y"/>
							</tcmis:inventoryGroupPermission>
							<c:set var="perm" value="${updatePerm}"/><c:set var="checked" value="false"/><c:set var="startDate" value="${p.startDate}"/>
							<c:if test= "${status.index != 0 }" >, </c:if>
							{ id: ${status.index + 1},
							  <c:if test="${p.issueOnReceipt}">'class':"grid_red",<c:set var="checked" value="true"/></c:if>
							  <c:if test="${p.preReceipt}">'class':"grid_black",<c:set var="perm" value="N"/><c:set var="checked" value="true"/></c:if>
							  data: ['${perm}',
							     '${p.mdItemId}',
							  	 '${p.catPartNo}',
							  	 '${p.inventoryGroupName}',
							  	 '${p.itemId}',
							  	 '<tcmis:jsReplace value="${p.description}" processMultiLines="true"/>',
							  	 '${p.inPurchasing}&nbsp;',
							  	 '<tcmis:jsReplace value="${p.packaging}" processMultiLines="true"/>',
							  	 '${p.inventoryQuantity}&nbsp;',
							  	 '<tcmis:jsReplace value="${p.uom}" processMultiLines="true"/>',
							  	 '${p.lastBin}',
							  	 '${p.inventoryQuantity}',
							    	 <c:choose><c:when test="${checked == true}">true</c:when><c:otherwise>false</c:otherwise></c:choose>,
								 <%--hidden here.--%>
								 <c:choose><c:when test="${p.issueOnReceipt}">'Y'</c:when><c:otherwise>'N'</c:otherwise></c:choose>,
								 '${p.companyId}',
								 '${p.catalogId}',
								 '${p.partGroupNo}',
							    	 '${p.countId}',
							    	 '${p.countType}',
							    	 <fmt:formatDate var="dispDate" value="${p.lastDateOfReceipt}" pattern="${javascriptDate}"/>'${dispDate}',
							    	 <fmt:formatDate var="dispDate" value="${p.lastReceiptQcDate}" pattern="${javascriptDate}"/>'${dispDate}',
							    	 <fmt:formatDate var="dispDate" value="${p.lastDateCounted}" pattern="${javascriptDate}"/>'${dispDate}',
							    	 <fmt:formatDate var="dispDate" value="${p.startDate}" pattern="${javascriptDate}"/>'${dispDate}',
							    	 '${p.inventoryQuantity}',
							    	 '${updatePerm}'
							  	] 
							}
						</c:forEach>
					]
				};
			
				<%-- determining rowspan --%>
				<c:set var="rowSpanCount" value='0' />
				<c:forEach var="row" items="${ItemCountViewBeanCollection}" varStatus="status">
				<c:set var="currentKey" value='${row.mdItemId}' />
					<c:choose>
						<c:when test="${status.first}">
							<c:set var="rowSpanStart" value='0' />
							<c:set var="rowSpanCount" value='1' />
							<c:set var="prevSpanCount" value="${rowSpanCount}"/>
							rowSpanMap[0] = 1;
							rowSpanClassMap[0] = 1;
						</c:when>
						<c:when test="${!empty currentKey && currentKey == previousKey}">
							rowSpanMap[${rowSpanStart}]++;
							rowSpanMap[${status.index}] = 0;
							rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
						</c:when>
						<c:otherwise>
							<c:set var="rowSpanCount" value='${rowSpanCount + 1}' />
							<c:set var="rowSpanStart" value='${status.index}' />
							rowSpanMap[${status.index}] = 1;
							rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
						</c:otherwise>
					</c:choose>
					<c:set var="previousKey" value='${currentKey}' />
				</c:forEach> 
			</c:if>
		</script>		
			
		<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
			The default value of showUpdateLinks is false.
		-->
		<tcmis:inventoryGroupPermission indicator="true" userGroupId="Hub ItemCount" inventoryGroup="${param.inventoryGroup}">
			<script type="text/javascript">
				showUpdateLinks = true;
				hubOnly = <c:choose><c:when test="${empty param.inventoryGroup}">true</c:when><c:when test="${fn:length(paramValues.inventoryGroup) > 1}">true</c:when><c:otherwise>false</c:otherwise></c:choose>;
				showPoNumber = <c:choose><c:when test="${tcmis:isFeatureReleased(personnelBean,'AllowPOOnItemCount', param.inventoryGroup)}">true</c:when><c:otherwise>false</c:otherwise></c:choose>;
			</script>
		</tcmis:inventoryGroupPermission>
		
		<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
			So this is just used to feed the YUI pop-up in the main page.
			Similar divs would have to be built to show any other messages in a pop-up.-->
		<!-- Error Messages Begins -->
		<div id="errorMessagesAreaBody" style="display:none;">
			${tcmisError}
		</div>
		<script type="text/javascript">
			<!--
			/*YAHOO.namespace("example.aqua");
			YAHOO.util.Event.addListener(window, "load", init);*/
			/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
			<c:choose> 
				<c:when test= "${empty tcmisError}" >
					showErrorMessage = false;
				</c:when>
				<c:otherwise>
					showErrorMessage = true;
				</c:otherwise> 
			</c:choose>//-->
		</script>
		<!-- Error Messages Ends -->

		<div class="interface" id="resultsPage">
			<div class="backGroundContent">
				<c:if test="${!empty ItemCountViewBeanCollection}" >
					<table width="100%">
						<tr>
							<td nowrap width="6%"  class="optionTitleBoldRight">
								<fmt:message key="label.showSearchResultDate"/>:&nbsp;
							</td>
							<td  width="10%" class="optionTitleBoldLeft">
								<fmt:formatDate var="fmtStartDate" value="${startDate}" pattern="${dateFormatPattern}"/>						
								${fmtStartDate}
							</td>	
							<td nowrap width="6%"  class="optionTitleBoldRight">
								<fmt:message key="label.actualCountDate2"/>:
							</td>
							<td  width="10%" class="optionTitleBoldLeft">
								<fmt:formatDate var="fmtToday" value="${today}" pattern="${dateFormatPattern}"/>						
			 					<input class="inputBox pointer" readonly type="text" name="countDate" id="countDate" value="${fmtToday}" onClick="return getCalendar(this,null,null,null,'${fmtToday}');" onchange="dateChangedValidation();" maxlength="10" size="9"/>						</td>	
						</tr>
					</table>
				</c:if>
				<%--NEW - there is no results table anymore--%>
				<div id="ItemCountDisplayViewBean" style="width:100%;height:400px;" style="display: none;" >
				</div>
				<!-- If the collection is empty say no data found -->
				<c:if test="${empty ItemCountViewBeanCollection}" >
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
						<tr>
							<td width="100%">
								<fmt:message key="main.nodatafound" />
							</td>
						</tr>
					</table>
				</c:if>
				<!-- Hidden element start -->
				<div id="hiddenElements" style="display: none;">
					<tcmis:setHiddenFields beanName="itemCountInputBean"/>
					<input type="hidden" name="poNumber" id="poNumber" value=""/>
				    <input type="hidden" name="checkSigDigit" id="checkSigDigit" value="1"/>							
				</div>
				<!-- Hidden elements end -->
			</div>
			<!-- close of backGroundContent -->
		</div>
		<!-- close of interface -->
	</tcmis:form>
	<div id="transitDailogWin" class="errorMessages" style="display: none;overflow: auto;">
		<table width="100%" border="0" cellpadding="2" cellspacing="1">
			<tr><td>&nbsp;</td></tr>
			<tr><td>&nbsp;</td></tr>
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td align="center" id="transitLabel">
				</td>
			</tr>
			<tr>
				<td align="center">
					<img src="/images/rel_interstitial_loading.gif" align="middle">
				</td>
			</tr>
		</table>
	</div>	
</body>
</html:html>	

