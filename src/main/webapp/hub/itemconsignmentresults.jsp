<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

	<!-- Add any other stylesheets you need for the page here -->
	<script type="text/javascript" src="/js/common/formchek.js"></script>
	<script type="text/javascript" src="/js/common/commonutil.js"></script>

	<%-- grid resize--%>
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
	<script type="text/javascript" src="/js/hub/itemconsignmentresults.js"></script>

	<%-- For Calendar support --%>
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
	<%--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>--%>
	
	<%--Uncomment the below if your grid has rowspans --%>
	<%--
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
 	--%>
 
	<%--This is to allow copy and paste. works only in IE--%>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_selection.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_nxml.js"></script>
	
	<%--This has the custom cells we built, hcal, hed, hchstatus --%>
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
			itemInteger: "<fmt:message key="error.item.integer"/>",
			recordFound: "<fmt:message key="label.recordFound"/>",
			searchDuration: "<fmt:message key="label.searchDuration"/>",
			minutes: "<fmt:message key="label.minutes"/>",
			seconds: "<fmt:message key="label.seconds"/>",
			pleasewait: "<fmt:message key="label.pleasewaitmenu"/>",
			item: "<fmt:message key="label.item"/>",
			nonZeroCount:"<fmt:message key="label.nonnegativenumber"><fmt:param><fmt:message key="label.usedquantity"/></fmt:param></fmt:message>",
			usedQuantity:"<fmt:message key="label.usedquantity"/>",
			noUpdate:"<fmt:message key="label.nothing"/>",
			poNumber:"<fmt:message key="label.ponumber"/>",
			price:"<fmt:message key="label.price"/>",
			receipt:"<fmt:message key="label.receipt"/>",
			greaterThanOnHand:"<fmt:message key="label.usedquantitygreaterthanonhand"/>"
	       };
	       
	       <c:set var="poNumber" value='${inputBean.poNumber}'/>
	         <c:choose>
	            <c:when test="${!empty poNumber}">
	             <fmt:formatDate var="fmtToday" value="${inputBean.dateDelivered}" pattern="${dateFormatPattern}"/>
	            </c:when>
	            <c:otherwise>
	             <fmt:formatDate var="fmtToday" value="${today}" pattern="${dateFormatPattern}"/>
	            </c:otherwise>
            </c:choose>
	       
		<%-- See webroot/dhtmlxgrid/codebase/dhtmlxcommon_haas.js for option explanations of config & gridconfig --%>
	       var columnConfig = [ 
	       		{columnId: "permission"},
			{columnId: "inventoryGroupName", columnName:'<fmt:message key="label.inventorygroup"/>',tooltip:true, width:10},
			{columnId: "itemId", columnName:'<fmt:message key="label.itemid"/>', width:6, align:"center", submit:true},
			{columnId: "description", columnName:'<fmt:message key="label.description"/>', tooltip:true, width:<c:choose><c:when test="${inputBean.countByReceipt}">25</c:when><c:otherwise>25</c:otherwise></c:choose>},
			<c:choose>
            <c:when test="${inputBean.countByReceipt}">
				{columnId: "receiptId", columnName:'<fmt:message key="label.receiptid"/>', width:6, align:"center", submit:true},
				{columnId: "originalReceiptId", columnName:'<fmt:message key="label.originalreceiptid"/>', width:6, align:"center"},
				{columnId: "legacyReceiptId", columnName:'<fmt:message key="label.legacyreceiptid"/>', width:6, align:"center"},
				{columnId: "distCustPartList", columnName:'<fmt:message key="label.partnumber"/>', width:7, align:"center"},
				{columnId: "mfgLot", columnName:'<fmt:message key="label.mfglot"/>', width:7, align:"center"},
				{columnId: "expireDate", columnName:'<fmt:message key="label.expirationdate"/>', width:7, align:"center"},
			</c:when>
            <c:otherwise>
                {columnId: "distCustPartList", columnName:'<fmt:message key="label.partnumber"/>', width:7, align:"center"},
            </c:otherwise>
            </c:choose>
            {columnId: "inventoryQuantity", columnName:'<fmt:message key="label.quantityonhand"/>',width:4, sort:"int", align:"right"},
			{columnId: "rowUpdated", columnName:'<fmt:message key="label.ok"/>', type:'hchstatus', width:3, align:"center", onChange:"updateChecked", submit:true, sorting:"haasHch"},
			{columnId: "usedQuantity", columnName:'<fmt:message key="label.usedquantity"/>', type:'hed', width:7, maxlength:22, size:5, align:"center", onChange:"isValidQuantity", submit:true},
			{
  	          columnId:"dateDelivered",
  	          columnName:'<fmt:message key="label.useddate"/> <br> <input readonly class="inputBox" name="needdateAll" id="needdateAll" type="text" value="${fmtToday}" size=9 onclick="return getDate(document.genericForm.needdateAll);" onChange="setCalendarValue(\'dateDelivered\');"/>',
  	          type:'hcal',
  	          align:'center',
  	          submit:true,
              width:7
             },
			{columnId: "shippingReference", columnName:'<fmt:message key="label.customerreference"/>', type:'hed', width:10, maxlength:100, size:15, submit:true},
			{columnId: "inventoryGroup", submit:true},
			{columnId: "catalogPricePermission"},
			{columnId: "catalogPrice", columnName:'<fmt:message key="label.price"/>', type:'hed', width:7, maxlength:22, size:4, align:"right", onChange:"isValidPrice", submit:true},
			{columnId: "isNewCatalogPrice", submit:true},
			{columnId: "currencyIdPermission"},
			{columnId: "currencyId", columnName:'<fmt:message key="label.currency"/>', type:'hcoro', align:"center", width:6, submit:true}

		];
	       
	       var gridConfig = {
		       divName:'ItemConsignmentInputBean', // the div id to contain the grid. ALSO the var name for the submitted data
		       beanData:'jsonMainData', // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		       beanGrid:'mygrid', // the grid's name, for later reference/usage
		       config:columnConfig, // the column config var name, as in var config = { [ columnId:..,columnName...
		       rowSpan:false, // true: this page has rowSpan > 1 or not, disables smartrendering & sorting
		       submitDefault:false // the fields in grid are defaulted to be submitted or not.
			   //onAfterHaasRenderRow:checkBoxOnOff
			       //noSmartRender:true // Turn off smart rendering
		       //onRowSelect:selectRow, // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		       //onRightClick:selectRow // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
	       };
	       
	       function getDate(dateColumn) {
	          return getCalendar(dateColumn,document.genericForm.blockBeforeExcludeDate);
          }
	      

	    // This is the array for type:'hcoro'. //
	       var currencyId = new  Array(
	    		   <c:forEach var= "cur" items= "${vvCurrencyCollection}" varStatus= "currencyIdStatus" >
		    			<c:if test= "${currencyIdStatus.index != 0 }" >,</c:if>		
		    	        {text:'${cur.currencyDisplay}',value:'${cur.currencyId}'}
	    	       </c:forEach>
	               );
       
      </script>
</head>
<body bgcolor="#ffffff" onload="myResultOnLoadWithGrid(gridConfig);">
	<tcmis:form action="/itemconsignmentresults.do" onsubmit="return submitFrameOnlyOnce();">
		<script  language="JavaScript" type="text/javascript">
			<c:if test= "${!empty resultsCollection}" >
		
			var jsonMainData = {
					rows: [
						<c:forEach var= "row" items= "${resultsCollection}" varStatus= "status" >
						
							<c:set var="updatePerm" value="N"/>
							<tcmis:inventoryGroupPermission indicator="true" userGroupId="itemConsignment" inventoryGroup="${row.inventoryGroup}">
								<c:set var="updatePerm" value="Y"/>
							</tcmis:inventoryGroupPermission>
		
							<c:set var="isNewCatalogPrice" value="false"/>
							<c:set var="catalogPricePermission" value="N"/>
							<c:set var="currencyIdPermission" value="N"/>
							<c:set var="colorClass" value=''/>
							<c:set var="defaultCurrencyId" value='${row.currencyId}'/>
									
							<c:if test= "${row.catalogPrice == null || row.catalogPrice == '' || row.catalogPrice == '0'}" >
								<c:set var="colorClass" value='grid_red'/>	
								<c:if test= "${updatePerm == 'Y'}">
									<c:set var="isNewCatalogPrice" value="true"/>
									<c:set var="catalogPricePermission" value="Y"/>		
									<c:set var="currencyIdPermission" value="Y"/>
									<c:set var="defaultCurrencyId" value='GBP'/>
								</c:if>
							</c:if>

							<fmt:formatNumber var="catalogPriceFormat" value="${row.catalogPrice}" pattern="${totalcurrencyformat}"></fmt:formatNumber>

							<c:if test= "${status.index != 0 }" >, </c:if>
							{ id: ${status.index + 1},'class':"${colorClass}",
							  data: ['${updatePerm}',
							  	 '${row.inventoryGroupName}', 
							  	 '${row.itemId}',
							  	 '<tcmis:jsReplace value="${row.itemDesc}" processMultiLines="true"/>',
								<c:choose>
                                <c:when test="${inputBean.countByReceipt}">
                                '${row.receiptId}',
                                '${row.transferReceiptId}',
								'${row.customerReceiptId}',
								'<tcmis:jsReplace value="${row.distCustomerPartList}" processMultiLines="true"/>',
							  	 '<tcmis:jsReplace value="${row.mfgLot}" processMultiLines="false"/>',
								'<fmt:formatDate value="${row.expireDate}" pattern="${dateFormatPattern}"/>',
                            </c:when>
                            <c:otherwise>
                                	'<tcmis:jsReplace value="${row.distCustomerPartList}" processMultiLines="true"/>',
                            </c:otherwise>
                            </c:choose>
							  	 '${row.quantity}&nbsp;',
							    	 false,
							    	 '',
							    	 '${fmtToday}',
                                     '',
							    	 '${row.inventoryGroup}',
							    	 '${catalogPricePermission}',
							    	 '${catalogPriceFormat}',
							    	 ${isNewCatalogPrice},
							    	 '${currencyIdPermission}',
							    	 '${defaultCurrencyId}'
							    	 ]
							}
						</c:forEach>
					]
				};
			</c:if>
		</script>		
			
		<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
			The default value of showUpdateLinks is false.
		-->
		<tcmis:inventoryGroupPermission indicator="true" userGroupId="itemConsignment">
			<script type="text/javascript">
				showUpdateLinks = true;
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
				<%--Results div--%>
				<div id="ItemConsignmentInputBean" style="width:100%;height:400px;" style="display: none;" >
				</div>
				<!-- If the collection is empty say no data found -->
				<c:if test="${empty resultsCollection}" >
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
					<tcmis:setHiddenFields beanName="inputBean"/>
					
					<!-- Popup Calendar input options for dateDelivered -->
					<input type="hidden" name="blockBefore_dateDelivered" id="blockBefore_dateDelivered" value=""/>
					<input type="hidden" name="blockAfter_dateDelivered" id="blockAfter_dateDelivered" value=""/>
					<input type="hidden" name="blockBeforeExclude_dateDelivered" id="blockBeforeExclude_dateDelivered" value=""/>
					<input type="hidden" name="blockAfterExclude_dateDelivered" id="blockAfterExclude_dateDelivered" value=""/>
					<input type="hidden" name="inDefinite_dateDelivered" id="inDefinite_dateDelivered" value=""/>
					<input type="hidden" name="poNumber" id="poNumber" value=""/>
					<input type="hidden" name="dateDelivered" id="dateDelivered" value=""/>
					</div>
				<!-- Hidden elements end -->
			</div>
			<!-- close of backGroundContent -->
		</div>
		<!-- close of interface -->
	</tcmis:form>
</body>
</html:html>	

