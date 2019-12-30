<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
	<tcmis:gridFontSizeCss />

	<script type="text/javascript" src="/js/common/formchek.js"></script>
	<script type="text/javascript" src="/js/common/commonutil.js"></script>
	<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
	<!-- This handels which key press events are disabeled on this page -->
	<script src="/js/common/disableKeys.js"></script>

	<!-- This handels the menu style and what happens to the right click on the whole page -->
	<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
	<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
	<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
	<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
	<%@ include file="/common/rightclickmenudata.jsp" %>

	<!-- Add any other javascript you need for the page here -->
	<script type="text/javascript" src="/js/hub/allocationanalysis.js"></script>

	<!-- These are for the Grid, uncomment if you are going to use the grid -->
	<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
	<script	type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
	<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
	<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
<title>
<fmt:message key="allocationanalysis.title"/>
</title>

<script language="JavaScript" type="text/javascript">
	//add all the javascript messages here, this for internationalization of client side javascript messages
	var messagesData = new Array();
	messagesData= {
		alert:"<fmt:message key="label.alert"/>",
		and:"<fmt:message key="label.and"/>",
		notes:"<fmt:message key="label.notes"/>",
		submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
		daySpanInteger:"<fmt:message key="error.dayspan.integer"/>",
		recordFound:"<fmt:message key="label.recordFound"/>",
		searchDuration:"<fmt:message key="label.searchDuration"/>",
		minutes:"<fmt:message key="label.minutes"/>",
		seconds:"<fmt:message key="label.seconds"/>",
		itemMrInteger:"<fmt:message key="error.itemmr.integer"/>"
	};

	with(milonic=new menuname("allMenu")){
		top="offset=2"
		style = contextStyle;
		margin=3
		aI("text=<fmt:message key="label.mrnotes"/>;url=javascript:showAllNotes();");
		aI("text=<fmt:message key="label.openmr"/>;url=javascript:openMR();");
		aI("text=<fmt:message key="label.showallocatableig"/>;url=javascript:allocationPopup('IG');");
		aI("text=<fmt:message key="label.showallocatableregion"/>;url=javascript:allocationPopup('REGION');");
		aI("text=<fmt:message key="label.showallocatableglobal"/>;url=javascript:allocationPopup('GLOBAL');");
	}

	with(milonic=new menuname("transferReqMenuWNotes")){
		top="offset=2"
		style = contextStyle;
		margin=3
		aI("text=<fmt:message key="label.mrnotes"/>;url=javascript:showAllNotes();");	 
		aI("text=<fmt:message key="label.showallocatableig"/>;url=javascript:allocationPopup('IG');");
		aI("text=<fmt:message key="label.showallocatableregion"/>;url=javascript:allocationPopup('REGION');");
		aI("text=<fmt:message key="label.showallocatableglobal"/>;url=javascript:allocationPopup('GLOBAL');");
	}

	with(milonic=new menuname("transferReqMenu")){
		top="offset=2"
		style = contextStyle;
		margin=3
		aI("text=<fmt:message key="label.showallocatableig"/>;url=javascript:allocationPopup('IG');");
		aI("text=<fmt:message key="label.showallocatableregion"/>;url=javascript:allocationPopup('REGION');");
		aI("text=<fmt:message key="label.showallocatableglobal"/>;url=javascript:allocationPopup('GLOBAL');");
	}

	with(milonic=new menuname("showNotes")){
		top="offset=2"
		style = contextStyle;
		margin=3
		aI("text=<fmt:message key="label.mrnotes"/>;url=javascript:showAllNotes();");
	}

	with(milonic=new menuname("openMRMenu")){
		top="offset=2"
		style = contextStyle;
		margin=3
		aI("text=<fmt:message key="label.openmr"/>;url=javascript:openMR();");
		aI("text=<fmt:message key="label.showallocatableig"/>;url=javascript:allocationPopup('IG');");
		aI("text=<fmt:message key="label.showallocatableregion"/>;url=javascript:allocationPopup('REGION');");
		aI("text=<fmt:message key="label.showallocatableglobal"/>;url=javascript:allocationPopup('GLOBAL');");
	}

	drawMenus();

	var config = [
		{
			columnId:"customerName",
			columnName:'<fmt:message key="label.customer"/>',
			tooltip:"Y",
			width:7
		},
		{
			columnId:"facilityName",
			columnName:'<fmt:message key="label.facility"/>',
			tooltip:"Y",
			width:5
		},
		{
			columnId:"workAreaDesc",
			columnName:'<fmt:message key="label.workarea"/>',
			tooltip:"Y",
			width:5
		},
		{
			columnId:"requestor",
			columnName:'<fmt:message key="label.requestor"/>',
			tooltip:"Y",
			width:5
		},
		{
			columnId:"csr",
			columnName:'<fmt:message key="label.csr"/>',
			tooltip:"Y",
			width:5
		},
		{
			columnId:"",
			columnName:'<fmt:message key="label.mrline"/><br/>(<fmt:message key="label.oconus"/>)',
			tooltip:"Y",
			width:7
		},
		{
			columnId:"",
			columnName:'<fmt:message key="label.status"/>',
			tooltip:"Y",
			width:5
		},
		{
			columnId:"",
			columnName:'<fmt:message key="label.stockingtype"/>',
			tooltip:"Y",
			width:5
		},
		{
			columnId:"",
			columnName:'<fmt:message key="label.ownerinventorygroup"/>',
			tooltip:"Y",
			width:8
		},
		{
			columnId:"",
			columnName:'<fmt:message key="label.partnumber"/>',
			tooltip:"Y",
			width:9
		},
		{
			columnId:"partDescription",
			columnName:'<fmt:message key="label.partdescription"/>',
			width:10,
			type:'txt'
		},
		{
			columnId:"",
			columnName:'<fmt:message key="label.quantityopen"/>/ <fmt:message key="label.totalquantity"/>',
			tooltip:"Y",
			width:5
		},
		{
			columnId:"",
			columnName:'<fmt:message key="label.totalopenvalue"/><br/>(<fmt:message key="label.currency"/>)',
			tooltip:"Y",
			width:5
		},
		{
			columnId:"",
			columnName:'<fmt:message key="label.needed"/>',
			tooltip:"Y",
			width:9
		},
		{
			columnId:"",
			columnName:'<fmt:message key="allocationanalysis.label.ontimedate"/>',
			tooltip:"Y",
			width:7
		},
		{
			columnId:"",
			columnName:'<fmt:message key="label.allocationtype"/>',
			tooltip:"Y",
			width:5
		},
		{
			columnId:"",
			columnName:'<fmt:message key="label.ref"/>',
			tooltip:"Y",
			width:7
		},
		{
			columnId:"",
			columnName:'<fmt:message key="allocationanalysis.label.suppliermfglot"/>',
			tooltip:"Y",
			width:7
		},
		{
			columnId:"itemId",
			columnName:'<fmt:message key="label.itemid"/>',
			tooltip:"Y",
			width:5
		},
		{
			columnId:"",
			columnName:'<fmt:message key="label.qty"/>',
			width:5
		},
		{
			columnId:"",
			columnName:'<fmt:message key="label.date"/>',
			tooltip:"Y",
			width:7
		},
		{
			columnId:"",
			columnName:'<fmt:message key="label.qtyonhand"/><br/><fmt:message key="label.inventorygroup"/>/<fmt:message key="label.hub"/>',
			tooltip:"Y",
			width:8
		},
		{
			columnId:"",
			columnName:'<fmt:message key="label.qtyavailable"/><br/><fmt:message key="label.inventorygroup"/>/<fmt:message key="label.hub"/>',
			tooltip:"Y",
			width:8
		},
		{
			columnId:"",
			columnName:'<fmt:message key="label.sourceinventorygroup"/>',
			tooltip:"Y",
			width:8
		},
		{
			columnId:"",
			columnName:'<fmt:message key="label.status"/>(<fmt:message key="label.ageindays"/>)',
			tooltip:"Y",
			width:10
		},
		{
			columnId:"",
			columnName:'<fmt:message key="label.customerpartnumber"/>',
			tooltip:"Y",
			width:5
		},
		{
			columnId:"prNumber"
		},
		{
			columnId:"lineItem"
		},
		{
			columnId:"materialRequestOrigin"
		},
		{
			columnId:"distribution"
		},
		{
			columnId:"facilityId"
		},
		{
			columnId:"ownerInventoryGroup"
		},
		{
			columnId:"companyId"
		},
		{
			columnId:"shipToLocationId"
		},
		{
			columnId:"priceGroupId"
		},
		{
			columnId:"billToCompanyId"
		},
		{
			columnId:"billToLocationId"
		},
		{
			columnId:"incoTerms"
		},
		{
			columnId:"unitOfSale"
		},
		{
			columnId:"shipComplete"
		},
		{
			columnId:"consolidateShipment"
		},
		{
			columnId:"catalogPrice"
		},
		{
			columnId:"currencyId"
		},
		{
			columnId:"opsEntityId"
		},
		{
			columnId:"opsCompanyId"
		},
		{
			columnId:"labelSpec"
		},
		{
			columnId:"specList"
		},
		{
			columnId:"specListConcat"
		},
		{
			columnId:"specLibraryConcat"
		},
		{
			columnId:"specDetailConcat"
		},
		{
			columnId:"specCocConcat"
		},
		{
			columnId:"specCoaConcat"
		},
		{
			columnId:"shipToCompanyId"
		},
		{
			columnId:"remainingShelfLifePercent"
		},
		{
			columnId:"promisedDate"
		},
		{
			columnId:"mrNotes"
		},
		{
			columnId:"orderQuantity"
		},
		{
			columnId:"openQuantity"
		},
		{
			columnId:"requiredDatetime"
		}
	];

	var gridConfig = {
		divName: 'allocationAnalysisBean',	<%--  the div id for the grid. This is the also the variable name used to pass data back in updates --%>
		beanData: 'jsonMainData',			<%--  the data variable name for jsonparse, as in grid.parse( beanData ,"json" ) --%>
		beanGrid: 'beanGrid',				<%--  variable to put the grid object in for later use --%>
		config: 'config',					<%--  the column config var name, as in var columnConfig = { [ columnId:..,columnName... --%>
		onRightClick: selectRow,			<%--  a javascript function to be called on right click with rowId & cellId as args --%>
		submitDefault: true,
		rowSpan: true,						<%--  this page has rowSpan > 1 or not. --%>
		noSmartRender: false
	};
	
	//set up the rows to be merged, and the variables needed
	var rowSpanCols = [0,1,2,3,4,5,6,7,8,9,10,11];
	var lineMap = new Array();
	var lineMap3 = new Array();
</script>
</head>

<body bgcolor="#ffffff" onload="myResultOnLoad();">
	<tcmis:form action="/allocationanalysisresults.do" onsubmit="return submitFrameOnlyOnce();">
		<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     	The default value of showUpdateLinks is false.
		-->
		<script type="text/javascript">
			showUpdateLinks = true;
		</script>

		<div class="interface" id="resultsPage">
			<div class="backGroundContent">
				<div id="allocationAnalysisBean" style="width: 100%; height: 400px;" style="display: none;"></div>
				<c:choose>
					<c:when test="${empty prOpenOrderBeanCollection}">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
							<tr>
								<td width="100%">
									<fmt:message key="main.nodatafound"/>
								</td>
							</tr>
						</table>
					</c:when>
					<c:otherwise>
						<script type="text/javascript">
							<%-- Loop through the results and output each row, formatting the results as necessary
								use tcmis:jsReplace to escape special characters for ANY user entered text
								use fmt:formatNumber to format numbers (existing patterns: unitpricecurrencyformat, totalcurrencyformat, oneDigitformat)
								use mt:formatDate to format dates (existing patterns: dateFormatPattern, dateTimeFormatPattern)--%>
							var jsonMainData = new Array();
    	            	    var jsonMainData = {
    	            	    rows:[
								<c:forEach var="prOpenOrderBean" items="${prOpenOrderBeanCollection}" varStatus="status">
									<c:set var="quantityDisplay" value=""/>
									<c:set var="totalOpenValue" value=""/>
									<c:set var="neededDisplay" value=""/>
									<c:set var="fmtRequiredDatetime" value=""/>
									<c:set var="lookAheadDateString"><tcmis:getDateTag numberOfDaysFromToday="0"/></c:set>
									<c:set var="fmtSystemRequiredDatetime" value=""/>
									<c:set var="jspLabel" value="label.noalloc"/>
									<c:set var="refDisplay" value=""/>
									<c:set var="suppliermfglotDisplay" value=""/>
									<c:set var="itemIdDisplay" value=""/>
									<c:set var="inventoryGroupNameDisplay" value=""/>
									<c:set var="allocQuantityDisplay" value=""/>
									<c:set var="jspLabel1" value="allocationanalysis.label.onhand"/>
									<c:set var="statusDisplay" value=""/>
									<c:set var="fmtOriginalPromisedDate" value=""/>
									<c:set var="fmtRefDate" value=""/>
									<c:set var="dateDisplay" value=""/>
									<c:set var="igQtyOnHandDisplay" value=""/>
									<c:set var="igQtyAvailableDisplay" value=""/>
									<c:set var="orderQuantityDisplay" value=""/>
									<c:set var="openQuantityDisplay" value=""/>
									<c:set var="colorClass" value=""/>
									<%-- quantityDisplay --%>
									<c:set var="quantityDisplay">
										<fmt:formatNumber value="${prOpenOrderBean.openQuantity}" type="NUMBER"  maxFractionDigits="2" />/<fmt:formatNumber value="${prOpenOrderBean.orderQuantity}" type="NUMBER"  maxFractionDigits="2" />
									</c:set>
									<%-- totalOpenValue --%>
									<c:if test="${!empty prOpenOrderBean.totalOpenValue}">
										<c:set var="totalOpenValue">
											<fmt:formatNumber value='${prOpenOrderBean.totalOpenValue}' type="NUMBER"  maxFractionDigits="2"/> (<tcmis:jsReplace value="${prOpenOrderBean.currencyId}"/>)
										</c:set>
									</c:if>
									<%-- fmtRequiredDatetime --%>
									<c:set var="fmtRequiredDatetime">
										<fmt:formatDate value="${prOpenOrderBean.requiredDatetime}" pattern="${dateFormatPattern}"/>
									</c:set>
									<%-- neededTime --%>
									<c:set var="neededDisplay">
										<tcmis:jsReplace value="${prOpenOrderBean.requiredDatetimeType}"/>&nbsp;${fmtRequiredDatetime}
									</c:set>
									<%-- lookAheadDateString --%>
									<c:if test="${prOpenOrderBean.lookAheadDays != null && prOpenOrderBean.lookAheadDays != ''}">
										<c:set var="lookAheadDateString">
											<tcmis:getDateTag numberOfDaysFromToday="${prOpenOrderBean.lookAheadDays}"/>
										</c:set>
									</c:if>
									<%-- fmtSystemRequiredDatetime --%>
									<c:set var="fmtSystemRequiredDatetime">
										<fmt:formatDate value="${prOpenOrderBean.systemRequiredDatetime}" pattern="${dateFormatPattern}"/>
									</c:set>
									<%-- jspLabel --%>
									<c:if test="${prOpenOrderBean.allocationType != ''}">
										<c:set var="jspLabel">
											label.${fn:toLowerCase(fn:replace(prOpenOrderBean.allocationType, ' ', ''))}
										</c:set>
									</c:if>
									<%-- refDisplay --%>
									<c:choose>
										<c:when test="${prOpenOrderBean.allocationType == 'PO'}">
											<c:set var="refDisplay" value="${prOpenOrderBean.refNo}-${prOpenOrderBean.refLine}"/>
										</c:when>
										<c:otherwise>
											<c:set var="refDisplay" value="${prOpenOrderBean.refNo}"/>
										</c:otherwise>
									</c:choose>
									<%-- suppliermfglotDisplay --%>
									<c:choose>
										<c:when test="${prOpenOrderBean.allocationType == 'Receipt'}">
											<c:set var="suppliermfglotDisplay">
												<tcmis:jsReplace value="${prOpenOrderBean.mfgLot}"/>
											</c:set>
										</c:when>
										<c:otherwise>
											<c:set var="suppliermfglotDisplay">
												<tcmis:jsReplace value="${prOpenOrderBean.supplier}"/>
											</c:set>
										</c:otherwise>
									</c:choose>
									<%-- itemIdDisplay --%>
									<c:set var="itemIdDisplay">
										<tcmis:jsReplace value="${prOpenOrderBean.itemId}"/>
									</c:set>
									<%-- inventoryGroupNameDisplay --%>
									<c:set var="inventoryGroupNameDisplay">
										<tcmis:jsReplace value="${prOpenOrderBean.inventoryGroupName}"/>
									</c:set>
									<%-- allocQuantityDisplay --%>
									<c:set var="allocQuantityDisplay">
										<fmt:formatNumber value="${prOpenOrderBean.allocQuantity}" type="NUMBER"  maxFractionDigits="2" />
									</c:set>
									<%-- jspLabel1 --%>
									<c:if test="${prOpenOrderBean.refStatus != 'On Hand, Not Available'}">
										<c:set var="jspLabel1">
											label.${fn:toLowerCase(fn:replace(fn:replace(prOpenOrderBean.refStatus, '/', ''), ' ', ''))}
										</c:set>
									</c:if>
									<%-- statusDisplay --%>
									<c:choose>
										<c:when test="${prOpenOrderBean.lotStatusAge != null}">
											<c:set var="statusDisplay">
												<fmt:message key="${jspLabel1}"/>(<fmt:formatNumber value="${prOpenOrderBean.lotStatusAge}" type="NUMBER"  maxFractionDigits="2" />)
											</c:set>
										</c:when>
										<c:otherwise>
											<c:set var="statusDisplay">
												<fmt:message key="${jspLabel1}"/>
											</c:set>
										</c:otherwise>
									</c:choose>
									<%-- fmtOriginalPromisedDate --%>
									<c:set var="fmtOriginalPromisedDate">
										<fmt:formatDate value="${prOpenOrderBean.originalPromisedDate}" pattern="${dateFormatPattern}"/>
									</c:set>
									<%-- fmtRefDate --%>
									<c:set var="fmtRefDate">
										<fmt:formatDate value="${prOpenOrderBean.refDate}" pattern="${dateFormatPattern}"/>
									</c:set>
									<%-- dateDisplay --%>
									<c:choose>
										<c:when test="${prOpenOrderBean.allocationType == 'PO' && prOpenOrderBean.originalPromisedDate != prOpenOrderBean.refDate}">
											<c:set var="dateDisplay">
												${fmtOriginalPromisedDate}&nbsp;${fmtRefDate}
											</c:set>
										</c:when>
										<c:otherwise>
											<c:set var="dateDisplay">
												${fmtRefDate}
											</c:set>
										</c:otherwise>
									</c:choose>
									<%-- igQtyOnHandDisplay --%>
									<c:set var="igQtyOnHandDisplay">
										<fmt:formatNumber value="${prOpenOrderBean.igQtyOnHand}" type="NUMBER"  maxFractionDigits="2" />/<fmt:formatNumber value="${prOpenOrderBean.qtyOnHand}" type="NUMBER"  maxFractionDigits="2" />
									</c:set>
									<%-- igQtyAvailableDisplay --%>
									<c:set var="igQtyAvailableDisplay">
										<fmt:formatNumber value="${prOpenOrderBean.igQtyAvailable}" type="NUMBER"  maxFractionDigits="2" />/<fmt:formatNumber value="${prOpenOrderBean.qtyAvailable}" type="NUMBER"  maxFractionDigits="2" />
									</c:set>
									<%-- orderQuantityDisplay --%>
									<c:set var="orderQuantityDisplay">
										<tcmis:jsReplace value="${prOpenOrderBean.orderQuantity}"/>
									</c:set>
									<%-- openQuantityDisplay --%>
									<c:set var="openQuantityDisplay">
										<tcmis:jsReplace value="${prOpenOrderBean.openQuantity}"/>
									</c:set>
									<%-- colorClass --%>
									<c:if test="${prOpenOrderBean.critical == 'Y' || prOpenOrderBean.critical == 'y'}">
										<c:set var="colorClass" value="grid_red"/>
									</c:if>
									<c:if test="${prOpenOrderBean.critical == 'S' || prOpenOrderBean.critical == 's'}">
										<c:set var="colorClass" value="grid_pink"/>
									</c:if>
									<c:if test="${not empty prOpenOrderBean.releaseStatus and prOpenOrderBean.releaseStatus != 'Released' && prOpenOrderBean.lineItem != '0'}">
										<c:set var="colorClass" value="grid_green"/>
									</c:if>
									<c:if test="${prOpenOrderBean.pickable != 'Y' && prOpenOrderBean.pickable != 'y' && prOpenOrderBean.allocationType == 'Receipt'}">
										<c:set var="colorClass" value="grid_yellow"/>
									</c:if>
									<c:if test="${prOpenOrderBean.hazmatIdMissing == 'MISSING'}">
										<c:set var="colorClass" value="grid_orange"/>
									</c:if>											
									<fmt:parseDate value="${lookAheadDateString}" pattern="MM/dd/yyyy" var="lookAheadDate"/>
									<c:if test="${prOpenOrderBean.refStatus == 'Not Allocated' && ((prOpenOrderBean.deliveryType == 'Schedule' && lookAheadDate > prOpenOrderBean.requiredDatetimeSort) || prOpenOrderBean.deliveryType != 'Schedule')}">
										<c:set var="colorClass" value="grid_black"/>
									</c:if>
									<c:if test="${!status.first}">,</c:if>
	                            	{
	                            		id:${status.count},
	                            		'class':"${colorClass}",
	                            		data:[
		                            		'<tcmis:jsReplace value="${prOpenOrderBean.customerName}"/>',
		                            		'<tcmis:jsReplace value="${prOpenOrderBean.facilityName}"/>',
    		                        		'<tcmis:jsReplace value="${prOpenOrderBean.applicationDesc}"/>',
        		                    		<c:choose>
            		                			<c:when test="${! empty prOpenOrderBean.requestorFirstName}">
                		            				'<tcmis:jsReplace value="${prOpenOrderBean.requestorLastName}"/>,<tcmis:jsReplace value="${prOpenOrderBean.requestorFirstName}"/>',
 			                           			</c:when>
    		                        			<c:otherwise>
        		                    				'<tcmis:jsReplace value="${prOpenOrderBean.requestorLastName}"/>',
            		                			</c:otherwise>
                		            		</c:choose>
                		            		'<tcmis:jsReplace value="${prOpenOrderBean.customerServiceRepName}"/>',
                		            		<c:choose>
                		        				<c:when test="${prOpenOrderBean.oconus != null && prOpenOrderBean.oconus != ''}">
                    		    					'<tcmis:jsReplace value="${prOpenOrderBean.prNumber}"/>-<tcmis:jsReplace value="${prOpenOrderBean.lineItem}"/><br>(<tcmis:jsReplace value="${prOpenOrderBean.oconus}"/>)</br>',
                    		    				</c:when>
		                        				<c:otherwise>
    		    	                				'<tcmis:jsReplace value="${prOpenOrderBean.prNumber}"/>-<tcmis:jsReplace value="${prOpenOrderBean.lineItem}"/>',
        		                				</c:otherwise>
                		        			</c:choose>
                    		    			'<tcmis:jsReplace value="${prOpenOrderBean.releaseStatus}"/>',
											'<tcmis:jsReplace value="${prOpenOrderBean.itemType}"/>',
											'<tcmis:jsReplace value="${prOpenOrderBean.ownerInventoryGroupName}"/>',
											<c:choose>
												<c:when test="${prOpenOrderBean.globalCatalog == 'Y'}">
													'<tcmis:jsReplace value="${prOpenOrderBean.alternateName}"/>',
													'<tcmis:jsReplace value="${prOpenOrderBean.catalogItemDescription}" processMultiLines="true"/>',
												</c:when>
												<c:otherwise>
													'<tcmis:jsReplace value="${prOpenOrderBean.facPartNo}"/>',
													'<tcmis:jsReplace value="${prOpenOrderBean.partDescription}" processMultiLines="true"/>',
												</c:otherwise>
											</c:choose>
											'${quantityDisplay}',
											'${totalOpenValue}',
											'${neededDisplay}',
											'${fmtSystemRequiredDatetime}',
											'<fmt:message key="${jspLabel}"/>',
											'${refDisplay}',
											'${suppliermfglotDisplay}',
											'${itemIdDisplay}',
											'${allocQuantityDisplay}',	
											'${dateDisplay}',
											'${igQtyOnHandDisplay}',
											'${igQtyAvailableDisplay}',
											'${inventoryGroupNameDisplay}',
											'${statusDisplay}',
											'<tcmis:jsReplace value="${prOpenOrderBean.distCustomerPartList}"/>',
											
											<%-- hidden columns --%>
											'<tcmis:jsReplace value="${prOpenOrderBean.prNumber}"/>',
											'<tcmis:jsReplace value="${prOpenOrderBean.lineItem}"/>',
											'<tcmis:jsReplace value="${prOpenOrderBean.materialRequestOrigin}"/>',
											'<tcmis:jsReplace value="${prOpenOrderBean.distribution}"/>',
											'<tcmis:jsReplace value="${prOpenOrderBean.facilityId}"/>',
											'<tcmis:jsReplace value="${prOpenOrderBean.ownerInventoryGroup}"/>',
											'<tcmis:jsReplace value="${prOpenOrderBean.companyId}"/>',
											'<tcmis:jsReplace value="${prOpenOrderBean.shipToLocationId}"/>',
											'<tcmis:jsReplace value="${prOpenOrderBean.priceGroupId}"/>',
											'<tcmis:jsReplace value="${prOpenOrderBean.billToCompanyId}"/>',
											'<tcmis:jsReplace value="${prOpenOrderBean.billToLocationId}"/>',
											'<tcmis:jsReplace value="${prOpenOrderBean.incoTerms}"/>',
											'<tcmis:jsReplace value="${prOpenOrderBean.unitOfSale}"/>',
											'<tcmis:jsReplace value="${prOpenOrderBean.shipComplete}"/>',
											'<tcmis:jsReplace value="${prOpenOrderBean.consolidateShipment}"/>',
											'<tcmis:jsReplace value="${prOpenOrderBean.catalogPrice}"/>',
											'<tcmis:jsReplace value="${prOpenOrderBean.currencyId}"/>',
											'<tcmis:jsReplace value="${prOpenOrderBean.opsEntityId}"/>',
											'<tcmis:jsReplace value="${prOpenOrderBean.opsCompanyId}"/>',
											'<tcmis:jsReplace value="${prOpenOrderBean.labelSpec}"/>',
											'<tcmis:jsReplace value="${prOpenOrderBean.specList}"/>',
											'<tcmis:jsReplace value="${prOpenOrderBean.specListConcat}"/>',
											'<tcmis:jsReplace value="${prOpenOrderBean.specLibraryConcat}"/>',
											'<tcmis:jsReplace value="${prOpenOrderBean.specDetailConcat}"/>',
											'<tcmis:jsReplace value="${prOpenOrderBean.specCocConcat}"/>',
											'<tcmis:jsReplace value="${prOpenOrderBean.specCoaConcat}"/>',
											'<tcmis:jsReplace value="${prOpenOrderBean.shipToCompanyId}"/>',
											'<tcmis:jsReplace value="${prOpenOrderBean.remainingShelfLifePercent}"/>',
											'<fmt:formatDate value="${prOpenOrderBean.promisedDate}" pattern="${dateFormatPattern}"/>',
											'<tcmis:jsReplace value="${prOpenOrderBean.mrNotes}" processMultiLines="true"/>',
											'${orderQuantityDisplay}',
											'${openQuantityDisplay}',
											'${fmtRequiredDatetime}'
										]
	                            	}
								</c:forEach>
							]};
						</script>
						
						<%--logic to decide which rows to be merged together--%>
						<c:set var="prevAllocation" value=''/>
						<c:set var="dataCount" value='0'/>
						<c:forEach var="prOpenOrderBean" items="${prOpenOrderBeanCollection}" varStatus="status">
			        		<c:set var="curAllocation" value='${prOpenOrderBean.prNumber}-${prOpenOrderBean.lineItem}-${prOpenOrderBean.oconus}'/>
							<c:choose>
								<c:when test="${curAllocation != prevAllocation}">
									<script type="text/javascript">lineMap[${status.index}] = 1;</script>
									<c:set var="prevAllocation" value='${prOpenOrderBean.prNumber}-${prOpenOrderBean.lineItem}-${prOpenOrderBean.oconus}'/>
									<c:set var="dataCount" value="${dataCount + 1}"/>
									<c:set var="parent" value="${status.index}"/>
								</c:when>
								<c:otherwise>
									<script type="text/javascript">lineMap[${parent}]++;</script>
								</c:otherwise>
							</c:choose>
							<script type="text/javascript">lineMap3[${status.index}] = ${dataCount % 2};</script>
						</c:forEach>
						
					</c:otherwise>
				</c:choose>
				<!-- Hidden element start -->
				<div id="hiddenElements" style="display: none;">
					<input type="hidden" name="totalLines" id="totalLines" value="${fn:length(prOpenOrderBeanCollection)}" />
					<input type="hidden" name="uAction" id="uAction" value="" />
					<%-- Store all search criteria in hidden elements, need this to re-query the database after updates --%>
					<tcmis:saveRequestParameter/>
				</div>
				<!-- Hidden elements end -->
			</div>
			<!-- close of backGroundContent -->
		</div>
		<!-- close of interface -->
	</tcmis:form>

	<!-- Error Messages Begins -->
	<div id="errorMessagesAreaBody" style="display:none;">
		${tcmISError}<br/>
		<c:forEach var="tcmisError" items="${tcmISErrors}">
			${tcmisError}<br/>
		</c:forEach>
	</div>

	<script type="text/javascript">
		<c:choose>
			<c:when test="${requestScope['org.apache.struts.action.ERROR'] == null and empty tcmISErrors and empty tcmISError}">
				showErrorMessage = false;
			</c:when>
			<c:otherwise>
				parent.messagesData.errors = "<fmt:message key="label.errors"/>";
				showErrorMessage = true;
			</c:otherwise>
		</c:choose>
	</script>
	<!-- Error Messages Ends -->
	
	<div id="mrLineNotesArea" style="display: none;"></div>
</body>
</html:html>