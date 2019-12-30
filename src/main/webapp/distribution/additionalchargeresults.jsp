<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta http-equiv="expires" content="-1">
	<link rel="shortcut icon" href="/images/buttons/tcmIS.ico">
	</link>
	<%@ include file="/common/locale.jsp" %>

	<!--Use this tag to get the correct CSS class.
		This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
	<tcmis:gridFontSizeCss />

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
	<script type="text/javascript" src="/js/distribution/additionalchargeresults.js"></script>
	
	<%-- These are for the Grid --%>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
	
	<%--Use if you are providing header menu to switch columns on/off--%>
	<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
	
	<%--Use if your grid has rowspans --%>
	<%--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script> --%>

	<%--This is to allow copy and paste. works only in IE--%>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_selection.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_nxml.js"></script>
	
	<%-- dhtmlxgrid_excell_customized.js is Haas Customized stuff,
	 	hcal - the internationalized calendar, 
		hlink- any links you want to provide in the grid, the URL/function to call can be attached based on a even (rowselect etc)
		hch, hschstatus - Checkboxes
		hed - editable field
		hcoro - pulldown
	 --%>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
	
	<%--Custom sorting. This custom sorting function implements case insensitive sorting. --%>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
	
	<script language="JavaScript" type="text/javascript">
	       //add all the javascript messages here, this for internationalization of client side javascript messages       
	       var messagesData = new Array();
	       messagesData= {
		       alert: "<fmt:message key="label.alert"/>",
		       and: "<fmt:message key="label.and"/>", 
		       validvalues: "<fmt:message key="label.validvalues"/>",
			submitOnlyOnce: "<fmt:message key="label.submitOnlyOnce"/>",
			recordFound: "<fmt:message key="label.recordFound"/>",
			searchDuration: "<fmt:message key="label.searchDuration"/>",
			minutes: "<fmt:message key="label.minutes"/>",
			seconds: "<fmt:message key="label.seconds"/>",
			pleasewait: "<fmt:message key="label.pleasewaitmenu"/>",
			item: "<fmt:message key="label.item"/>",
			description: "<fmt:message key="label.description"/>",
			headerCharge: "<fmt:message key="label.headerCharge"/>",
			lineCharge: "<fmt:message key="label.linecharge"/>",
			defaultPrice: "<fmt:message key="label.defaultprice"/>",
			currency: "<fmt:message key="label.currency"/>",
			lastUpdatedDate: "<fmt:message key="label.laastUpdatedDate"/>",
			lastUpdatedBy: "<fmt:message key="label.lastUpdatedBy"/>",
			required: "<fmt:message key="errors.required"><fmt:param><fmt:message key="label.defaultprice"/></fmt:param></fmt:message>",
			notFloat: "<fmt:message key="errors.float"><fmt:param><fmt:message key="label.defaultprice"/></fmt:param></fmt:message>"
	       };
	       
		<%-- The array that config.hcoro requires to exist--%>
		var defaultCurrencyId = new Array(	
			<c:forEach var="cbean" items="${vvCurrencyBeanCollection}" varStatus="status">
				<c:if test="${status.index > 0}">,</c:if>
				{text:'<tcmis:jsReplace value="${cbean.currencyId}"/>',value:'${cbean.currencyId}'}
			</c:forEach>
		);
	
	
	       var config = [ 
	       		{columnId: "permission"},
	       		{columnId: "itemId", columnName:messagesData.item},
	       		{columnId: "description", columnName:messagesData.description, width:30},
	       		{columnId: "headerChargeType", columnName:messagesData.headerCharge, type:"hchstatus", align:"center", sorting:"haasHch"},
	       		{columnId: "lineChargeType", columnName:messagesData.lineCharge, type:"hchstatus", align:"center", sorting:"haasHch"},
	       		{columnId: "defaultPrice", columnName:messagesData.defaultPrice, type:"hed", onChange:markRowChanged, width:7, maxlength:22, size:5, align:"center"},
	       		{columnId: "defaultCurrencyId", columnName:messagesData.currency, align:"center", type: "ro"},
	       		{columnId: "lastUpdated", columnName:messagesData.lastUpdatedDate, hiddenSortingColumn:'hiddenLastUpdated', align:"center", sorting:"int"},
	       		{columnId: "hiddenLastUpdated", sorting:"int"},
	       		{columnId: "lastUpdatedBy", columnName:messagesData.lastUpdatedBy, submit:false},
	       		{columnId: "opsEntityId"},
	       		{columnId: "updated"}
	       ];
	       
	       var gridConfig = {
		       divName: 'additionalChargeResultsGrid', // the div id to contain the grid.
		       beanData: 'jsonMainData', // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		       beanGrid: 'mygrid', // the grid's name, as in beanGrid.attachEvent...
		       config:config, // the column config var name, as in var config = { [ columnId:..,columnName...
		       rowSpan:false, // this page has rowSpan > 1 or not.
		       submitDefault:true, // the fields in grid are defaulted to be submitted or not.
		       noSmartRender:true,
		       onRowSelect:selectRow
	       };
	       
      </script>
</head>
<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);">
	<tcmis:form action="/additionalchargeresults.do" onsubmit="return submitFrameOnlyOnce();">
		<%-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
			The default value of showUpdateLinks is false.
		--%>
		<c:set var="perm" value="N"/>
		<tcmis:opsEntityPermission indicator="true" userGroupId="additionalChargeMain" opsEntityId="${additionalChargeInputBean.opsEntityId}">
			<script type="text/javascript">showUpdateLinks = true;</script>
			<c:set var="perm" value="Y"/>
		</tcmis:opsEntityPermission>
		<script language="JavaScript" type="text/javascript">
			<c:if test= "${!empty CatalogItemAddChargeBeanCollection}" >
				//  The color stuff is not working at this moment, I will use
				//  javascript to fix it.
				var jsonMainData = {
					rows: [
						<c:forEach var="row" items= "${CatalogItemAddChargeBeanCollection}" varStatus= "status" >
							<tcmis:jsReplace var="description" value="${row.itemDescription}" processMultiLines="true" />
							<fmt:formatDate var="lastUpdated" value="${row.lastUpdated}" pattern="${dateFormatPattern}"/>
							<c:if test= "${status.index != 0 }" >, </c:if>
							{ id: ${status.index + 1},
							  data: ['${perm}',
							  	 '${row.itemId}',
							  	 '${description}',
							  	 ${row.headerChargeType},
							  	 ${row.lineChargeType},
							  	 <fmt:formatNumber var="defaultPrice" value="${row.defaultPrice}"  pattern="${totalcurrencyformat}"></fmt:formatNumber>'${defaultPrice}',
							  	 '${row.defaultCurrencyId}',
							  	 '${lastUpdated}',
							  	 '${row.lastUpdated.time}',
							  	 '${row.updateByName}',
							  	 '${param.opsEntityId}',
							  	 ''
							  	 ]
							}
							<c:set var= "dataCount" value='${status.index + 1}' />
						</c:forEach>]
					};
			</c:if>
		</script>
				
		<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
			So this is just used to feed the YUI pop-up in the main page.
			Similar divs would have to be built to show any other messages in a pop-up.-->
		<!-- Error Messages Begins -->
		<div id="errorMessagesAreaBody" style="display:none;">
			${tcmisError}
		</div>
		<script type="text/javascript">
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
			</c:choose>
		</script>
		<!-- Error Messages Ends -->

		<div class="interface" id="resultsPage">
			<div class="backGroundContent">
				<div id="additionalChargeResultsGrid" style="width:100%;%;height:400px;" style="display: none;">
				</div>
				<c:if test="${empty additionalChargeBeanCollection}" >
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
					<tcmis:setHiddenFields beanName="additionalChargeInputBean"/>
                    <input name="searchField" id="searchField" type="hidden" value="${param.searchField}">
                    <input name="searchMode" id="searchMode" type="hidden" value="${param.searchMode}">
                    <input name="searchArgument" id="searchArgument" type="hidden" value="${param.searchArgument}">
                    <input name="opsEntityId" id="opsEntityId" type="hidden" value="${param.opsEntityId}">
                    <input name="headerChargesOnly" id="headerChargesOnly" type="hidden" value="${param.headerChargesOnly}">
                </div>
				<!-- Hidden elements end -->
			</div>
			<!-- close of backGroundContent -->
		</div>
		<!-- close of interface -->
	</tcmis:form>
</body>
</html>	

