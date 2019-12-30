<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis"%>

<html:html lang="true">
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
	<meta http-equiv="expires" content="-1"/>
	<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
	<%@ include file="/common/locale.jsp"%>
	<tcmis:gridFontSizeCss />

	<script type="text/javascript" src="/js/common/formchek.js"></script>
	<script	type="text/javascript" src="/js/common/commonutil.js"></script>
	<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
	<script src="/js/common/disableKeys.js"></script>

	<!-- This handels the menu style and what happens to the right click on the whole page -->
	<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
	<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
	<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
	<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
	<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
	<%@ include file="/common/rightclickmenudata.jsp" %>

	<!-- For Calendar support -->
	<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
	<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
	<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

	<!-- These are for the Grid-->
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
	<%--Uncomment the below if your grid has row span > 1--%>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>

	<!-- Add any other javascript you need for the page here -->
	<script	type="text/javascript" src="/js/supplier/inventorymanagementresults.js"></script>
	<script src="/js/jquery/jquery-1.6.4.js"></script>

	<title></title>

	<script language="JavaScript" type="text/javascript">
		<%-- Standard var for Internationalized messages--%>
		var messagesData = {
			alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			validvalues:"<fmt:message key="label.validvalues"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
			recordFound:"<fmt:message key="label.recordFound"/>",
			searchDuration:"<fmt:message key="label.searchDuration"/>",
			minutes:"<fmt:message key="label.minutes"/>",
			seconds:"<fmt:message key="label.seconds"/>",
			forVal:"<fmt:message key="label.for"/>",
			pleasewait:"<fmt:message key="label.pleasewait"/>",
			addInventory:"<fmt:message key="label.addinventory"/>",
			receivePOInventory:"<fmt:message key="label.receivepoinventory"/>",
			addAdjustment:"<fmt:message key="label.addadjustment"/>",
			editInventory:"<fmt:message key="label.editinventory"/>",
			editPO:"<fmt:message key="label.editpo"/>",
			genericError:"<fmt:message key="generic.error"/>",
			printLabels:"<fmt:message key="label.printlabels"/>",
			notes:"<fmt:message key="label.notes"/>",
			showNotes:"<fmt:message key="label.shownotes"/>",
			convertPO:"<fmt:message key="label.convertpo"/>",
			convertInventory:"<fmt:message key="label.convertinventory"/>"
		};
		
		<%-- Check for errors to display --%>
		<c:choose>
			<c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISError && empty tcmISErrors }">
				showErrorMessage = false;
			</c:when>
			<c:otherwise>
				showErrorMessage = true;
			</c:otherwise>
		</c:choose>

    	<%-- Define the right click menus --%>
    	with(milonic=new menuname("locationLevelRightClickMenu")) {
			top="offset=2"
			style = contextStyle;
			margin=3
	    	<%-- SetMMLevels is location-specific; if All shipFromLocation search is allowed, change permission check to dynamic --%>
			<tcmis:supplierLocationPermission indicator="true" userGroupId="SetMMLevels" supplierId="${param.supplier}" locationId="${param.shipFromLocationId}">
		       	aI("text=<fmt:message key="label.editminmaxlevel"/>;url=javascript:parent.openUpdateInsertPartPopup('Y');");
			</tcmis:supplierLocationPermission>
	        aI("text=<fmt:message key="label.showminmaxchangehistory"/>;url=javascript:showMinMaxHistory();");
	        aI("text=<fmt:message key="label.addinventory"/>;url=javascript:parent.openInventoryPopup('Y', 'addInventory');");
	        aI("text=<fmt:message key="label.addpo"/>;url=javascript:parent.openPOPopup('Y', 'addPO');");
    	}
    	
    	with(milonic=new menuname("typeRightClickMenu")) {
			top="offset=2"
			style = contextStyle;
			margin=3
			aI("text=;url=;image=");
    	}

    	<%-- Initialize the Right Mouse Click Menus --%>
    	drawMenus();
    	
    	var inventoryAdjCode = new Array(
   	    	<c:forEach var="vvInventoryAdjCodeBean" items="${vvInventoryAdjCodeBeanColl}" varStatus="status">
        		<c:if test="${!status.first}">,</c:if>
        		{
        			name: "<tcmis:jsReplace value='${vvInventoryAdjCodeBean.code}'/>",
        			id: "<tcmis:jsReplace value='${vvInventoryAdjCodeBean.code}'/>"
        		}
   	    	</c:forEach>
    	);
    	
    	<%-- Define the columns for the result grid --%>
    	var config = [
			{
				columnId: "permission"
			},
			{
				columnId:"locationDesc",
				columnName:'<fmt:message key="label.location"/>',
				width:15,
				align: 'center',
				tooltip: true
			},
			{
				columnId:"catPartNo",
				columnName:'<fmt:message key="label.partno"/>',
				width:8,
				align: 'center',
				tooltip: true
			},
			{
				columnId:"conversionGroup",
				columnName:'<fmt:message key="label.conversiongroup"/>',
				width:6,
				align: 'center'
			},
			{
				columnId:"gfp",
				columnName:'<fmt:message key="label.gfp"/>',
				width:4,
				align: 'center'
			},
			{
				columnId:"reorderPoint",
				columnName:'<fmt:message key="label.minmax"/>',
				attachHeader:'<fmt:message key="label.reorderpoint"/>',
				width:7,
				align: 'center'
			},
			{
				columnId:"stockingLevel",
				columnName:'#cspan',
				attachHeader:'<fmt:message key="label.stockinglevel"/>',
				width:7,
				align: 'center'
			},
			{
				columnId:"purchaseQty",
				columnName:'<fmt:message key="label.purchaseqty"/>',
				width:5,
				align: 'center'
			},
			{
				columnId:"partDescription",
				columnName:'<fmt:message key="label.partdescription"/>',
				width:15,
				align: 'center',
				tooltip: true
			},
			{
				columnId:"uom",
				columnName:'<fmt:message key="label.uom"/>',
				width:7,
				align: 'center'
			},
			{
				columnId:"transactionType",
				columnName:'<fmt:message key="label.type"/>',
				width:5,
				align: 'center'
			},
			{
				columnId:"inventoryId",
				columnName:'<fmt:message key="label.inventory"/>/<fmt:message key="label.vendorpo"/>',
				attachHeader:'<fmt:message key="label.inventoryid"/>',
				width:6,
				align: 'center',
				tooltip: true
			},
			{
				columnId:"supplierPoNumber",
				columnName:'#cspan',
				attachHeader:'<fmt:message key="label.vendorpo"/>',
				width:7,
				align: 'center',
				tooltip: true
			},
			{
				columnId:"blanketPo",
				columnName:'#cspan',
				attachHeader:'<fmt:message key="label.blanketpo"/>',
				width:7,
				align: 'center'
			},
			{
				columnId:"vmi",
				columnName:'#cspan',
				attachHeader:'<fmt:message key="label.vmi"/>',
				width:4,
				align: 'center'
			},
			{
				columnId:"bolTrackingNum",
				columnName:'#cspan',
				attachHeader:'<fmt:message key="label.boltrackingnumber"/>',
				width:7,
				align: 'center',
				tooltip: true
			},
			{
				columnId:"quantity",
				columnName:'#cspan',
				attachHeader:'<fmt:message key="label.orderquantity"/>',
				width:5,
				align: 'center'
			},
			{
				columnId:"mfgLot",
				columnName:'#cspan',
				attachHeader:'<fmt:message key="label.lot"/>',
				width:5,
				align: 'center',
				tooltip: true
			},
			{
				columnId:"bin",
				columnName:'#cspan',
				attachHeader:'<fmt:message key="label.storagelocation"/>',
				width:5,
				align: 'center',
				tooltip: true
			},
			{
				columnId:"expireDate",
				columnName:'#cspan',
				attachHeader:'<fmt:message key="label.expiredate"/>',
				width:7,
				align: 'center'
			},
			{
				columnId:"dateOfManufacture",
				columnName:'#cspan',
				attachHeader:'<fmt:message key="label.dateofmanufacture"/>',
				width:7,
				align: 'center'
			},
			{
				columnId:"transactionUserName",
				columnName:'#cspan',
				attachHeader:'<fmt:message key="label.user"/>',
				width:10,
				align: 'center',
				tooltip: true
			},
			{
				columnId:"transactionDate",
				columnName:'#cspan',
				attachHeader:'<fmt:message key="label.transactiondate"/>',
				width:7,
				align: 'center'
			},
			{
				columnId:"status",
				columnName:'<fmt:message key="label.status"/>',
				width:7,
				align: 'center',
				tooltip: true
			},
			{
				columnId:"customerPoNumber",
				columnName:'<fmt:message key="label.usgovdo"/>/<fmt:message key="label.wescopo"/>',
				attachHeader:'<fmt:message key="label.dono"/>',
				width:10,
				align: 'center',
				tooltip: true
			},
			{
				columnId:"radianPoLine",
				columnName:'#cspan',
				attachHeader:'<fmt:message key="label.poline"/>',
				width:6,
				align: 'center',
				tooltip: true
			},
			{
				columnId:"customerPoQty",
				columnName:'#cspan',
				attachHeader:'<fmt:message key="label.qty"/>',
				width:4,
				align: 'center'
			},
			{
				columnId:"availableQty",
				columnName:'<fmt:message key="label.availableqty"/>',
				width:5,
				align: 'center'
			},
			{
				columnId:"openQty",
				columnName:'<fmt:message key="label.openqty"/>',
				width:5,
				align: 'center'
			},
			{
				columnId:"shipFromLocationId"
			},
			{
				columnId:"transactionId"
			},
			{
				columnId:"inventoryLevelId"
			},
			{
				columnId:"domRequired"
			},
			{
				columnId:"notes"
			},
			{
				columnId:"canConvertPartNo"
			},
			{
				columnId:"locationShortName"
			}
		];

		<%-- Define the grid options--%>
		var gridConfig = {
			divName: 'TransactionManagementViewBean',			<%--  the div id for the grid. This is the also the variable name used to pass data back in updates --%>
			beanData: 'jsonMainData',			<%--  the data variable name for jsonparse, as in grid.parse( beanData ,"json" ) --%>
			beanGrid: 'beanGrid',				<%--  variable to put the grid object in for later use --%>
			config: 'config',					<%--  the column config var name, as in var columnConfig = { [ columnId:..,columnName... --%>
			onRightClick: buildRightClickOption,
			onRowSelect: selectRow,
			rowSpan: true,						<%--  this page has rowSpan > 1 or not. --%>
			noSmartRender: false,
			submitDefault: true
		};
		
		//set up the rows to be merged, and the variables needed
    	var rowSpanCols = getRowSpanColsArr("locationDesc, catPartNo, partDescription, gfp, reorderPoint, stockingLevel, purchaseQty, uom, inventoryId, conversionGroup");
        var lineMap = new Array();
        var lineMap3 = new Array();
	</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid();">
	<tcmis:form action="/inventorymanagementresults.do" onsubmit="return submitFrameOnlyOnce();">
		<div class="interface" id="resultsPage">
	        <div class="backGroundContent">
	            <div id="TransactionManagementViewBean" style="width: 100%; height: 400px;" style="display: none;"></div>
				<!-- Error Messages Begins -->
				<div id="errorMessagesAreaBody" style="display:none;">
					<html:errors/>${tcmISError}
					<c:forEach var="error" items="${tcmISErrors}">
						${error}<br/>
					</c:forEach>
				</div>
	            <c:choose>
	            	<%-- If the collection is empty say no data found --%>
	            	<c:when test="${empty transactionManagementViewBeanCollection}" >
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
	                	         use fmt:formatDate to format dates (existing patterns: dateFormatPattern, dateTimeFormatPattern)--%>
	                	    var jsonMainData = {
	                	    rows:[
	                	        <c:forEach var="myBean" items="${transactionManagementViewBeanCollection}" varStatus="status">
	                            	<c:if test="${!status.first}">,</c:if>
	                            	{
	                            		id:${status.count},
	                                	data:[
	                                	    'Y',
	                                	    '<tcmis:jsReplace value="${myBean.locationDesc}"/>',
	                                	    '<tcmis:jsReplace value="${myBean.catPartNo}"/>',
	                                	    '<tcmis:jsReplace value="${myBean.conversionGroup}"/>',
	                                	    '${myBean.gfp}',
	                                	    '${myBean.reorderPoint}',
	                                	    '${myBean.stockingLevel}',
	                                	    '${myBean.purchaseQty}',
	                                	    '<tcmis:jsReplace value="${myBean.partDescription}" processMultiLines="true"/>',
	                                	    '${myBean.uom}',
	                                	    '${myBean.transactionType}',
	                                	    '${myBean.inventoryId}',
	                                	    '${myBean.supplierPoNumber}',
	                                	    '${myBean.blanketPo}',
	                                	    '${myBean.vmi}',
	                                	    '<tcmis:jsReplace value="${myBean.bolTrackingNum}"/>',
	                                	    '${myBean.quantity}',
	                                	    '<tcmis:jsReplace value="${myBean.mfgLot}"/>',
	                                	    '<tcmis:jsReplace value="${myBean.bin}"/>',
	                                        '<fmt:formatDate value="${myBean.expireDate}" pattern="${dateFormatPattern}"/>',
	                                        '<fmt:formatDate value="${myBean.dateOfManufacture}" pattern="${dateFormatPattern}"/>',
	                                	    '${myBean.transactionUserName}',
	                                        '<fmt:formatDate value="${myBean.transactionDate}" pattern="${dateFormatPattern}"/>',
	                                	    '<tcmis:jsReplace value="${myBean.status}"/>',
	                                	    '<tcmis:jsReplace value="${myBean.customerPoNo}"/>',
	                                	    '${myBean.radianPoLine}',
	                                	    '${myBean.customerPoQty}',
	                                	    '${myBean.availableQty}',
	                                	    '${myBean.openQty}',
	                                	    '${myBean.shipFromLocationId}',
	                                	    '${myBean.transactionId}',
	                                	    '${myBean.inventoryLevelId}',
	                                	    '${myBean.domRequired}',
	                                	    '<tcmis:jsReplace value="${myBean.notes}" processMultiLines="true"/>',
	                                	    '${myBean.canConvertPartNo}',
	                                	    '<tcmis:jsReplace value="${myBean.locationShortName}" processMultiLines="true"/>'
										]
	                            	}
	                        	</c:forEach>
							]};
					    	showUpdateLinks = true;
		                	<c:set var="prevKey" value=''/>
							<c:set var="dataCount" value='0'/>
							<c:forEach var="bean" items="${transactionManagementViewBeanCollection}" varStatus="status">
								<c:set var="curKey" value='${bean.shipFromLocationId}${bean.catPartNo}${bean.inventoryId}'/>
								<c:choose>
									<c:when test="${curKey != prevKey}">
										lineMap[${status.index}] = 1;
										<c:set var="prevKey" value='${curKey}'/>
										<c:set var="dataCount" value="${dataCount + 1}"/>
										<c:set var="parent" value="${status.index}"/>
									</c:when>
									<c:otherwise>
										lineMap[${parent}]++;
									</c:otherwise>
								</c:choose>
								lineMap3[${status.index}] = ${dataCount % 2};
							</c:forEach>
	                	</script>
	            	</c:otherwise>
	            </c:choose>
	
	            <!-- Hidden element start -->
	            <div id="hiddenElements" style="display: none;">
	            	<input name="labelQuantity" id="labelQuantity" type="hidden" value=""/>
	            	<%-- Retrieve all the Search Criteria here for re-search after update--%>
	            	<tcmis:setHiddenFields beanName="searchInput"/>
				</div>
	            <!-- Hidden elements end -->
		    </div>
		</div>
	</tcmis:form>
	<div id="notesWin" class="errorMessages" style="display: none;overflow: auto;"></div>
</body>
</html:html>