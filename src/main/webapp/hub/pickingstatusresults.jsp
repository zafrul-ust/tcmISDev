<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
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
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
<link rel="shortcut-icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
<%-- For Internationalization, copies data from calendarval.js --%>
<%@ include file="/common/locale.jsp" %>
<tcmis:gridFontSizeCss />
<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles which key press events are disabled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>
<script type="text/javascript" src="/js/hub/pickingstatusresults.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script> 
<title><fmt:message key="pickingStatus"/></title>
<script language="JavaScript" type="text/javascript"/>
<!--
var messagesData = {
			recordFound:"<fmt:message key="label.recordFound"/>",
    		searchDuration:"<fmt:message key="label.searchDuration"/>",
    		minutes:"<fmt:message key="label.minutes"/>",
    		seconds:"<fmt:message key="label.seconds"/>",
            total:"<fmt:message key="label.total"/>",
			pleaseSelect:"<fmt:message key="error.norowselected"/>",
			none:"<fmt:message key="label.none"/>",
			printpvr:"<fmt:message key="label.printpvr"/>",
			overrideapproval:"<fmt:message key="label.overrideapproval"/>",
			limitedquantitymaterial:"<fmt:message key="label.limitedquantitymaterial"/>",
			notregulated:"<fmt:message key="label.notregulated"/>",
			printStraightBOL:"<fmt:message key="label.printstraightbol"/>"
};

with(milonic=new menuname("documentMenu")){
	top = "offset=2";
	style = submenuStyle;
	margin = 3;
	aI("text=<font color='gray'><fmt:message key="label.none"/></font>;url=javascript:doNothing();");
}

with(milonic=new menuname("dotOverrideMenu")){
	top = "offset=2";
	style = submenuStyle;
	margin = 3;
	aI("text=<font color='gray'><fmt:message key="label.none"/></font>;url=javascript:doNothing();");
}

with(milonic=new menuname("rightClickMenu")){
	top = "offset=2";
	style = contextStyle;
	margin = 3;
	<tcmis:facilityPermission indicator="true" userGroupId="overrideShippingInformation">
		aI("showmenu=dotOverrideMenu;text=<fmt:message key="label.dotoverride"/>;image=");
	</tcmis:facilityPermission>
	aI("text=<fmt:message key="label.printdotlabel"/>;url=javascript:printDOTLabel();");
	aI("text=<fmt:message key="label.printiatalabel"/>;url=javascript:printIATALabel();");
	aI("text=<fmt:message key="label.printstraightbol"/>;url=javascript:printStraightBOL();");
	aI("text=<fmt:message key="label.printdeliverylabel"/>;url=javascript:printSampleDeliveryLabel();");
	aI("showmenu=documentMenu;text=<fmt:message key="label.documents"/>;image=");
}

drawMenus();

var columnConfig = [
{ columnId:"permission",
  columnName:'',
  submit:false
},
{ columnId:"okPermission"
},
{ columnId:"pickingGroupIdPermission"
},
{ columnId:"pickingStatePermission" 
},
{ columnId:"rejectionCommentPermission"
},
{ columnId:"ok",
  permission:true,
  columnName:'<fmt:message key="label.ok"/><br><input type="checkbox" value="" onClick="return checkAll(\'ok\');" name="chkAllOk" id="chkAllOk">',
  type:"hchstatus",
  align:"center",
  width:4,
  submit:true
},
{ columnId:"pickingUnitId",
  columnName:'<fmt:message key="label.pickingunit"/>',
  width:4,
  align:'center',
  submit:true
},
{ columnId:"pickingGroupId",
  permission:true,
  columnName:'<fmt:message key="label.pickinggroup"/>',
  width:10,
  type:"hcoro",
  align:"center",
  submit:true
},
{ columnId:"picklistId",
  columnName:'<fmt:message key="label.picklist"/>',
  width:7
},
{ columnId:"pdocs",
  columnName:'<fmt:message key="label.pdoc"/>',
  tooltip:"Y",
  width:7
},
{ columnId:"pickCreationDate",
  columnName:'<fmt:message key="label.pickcreationdate"/>',
  width:7
},
{ columnId:"pickingState",
  permission:true,
  columnName:'<fmt:message key="label.pickingstate"/>',
  width:10,
  type:"hcoro",
  align:"center",
  submit:true
},
{ columnId:"prNumberLineItem",
  columnName:'<fmt:message key="label.mrline"/>',
  submit:false,
  width:7
},
{ columnId:"needDate",
  columnName:'<fmt:message key="label.needed"/>',
  width:7
},
{ columnId:"itemId",
  columnName:'<fmt:message key="label.item"/>',
  width:6
},
{ columnId:"receiptId",
  columnName:'<fmt:message key="label.receipt"/>',
  width:7
},
{ columnId:"quantity",
  columnName:'<fmt:message key="label.qty"/>',
  width:3
},
{ columnId:"itemType",
  columnName:'<fmt:message key="label.itemtype"/>',
  width:4
},
{ columnId:"catalog",
  columnName:'<fmt:message key="label.catalog"/>',
  width:8
},
{ columnId:"poNumber",
  columnName:'<fmt:message key="label.ponumber"/>',
  width:5
},
{ columnId:"facPartNo",
  columnName:'<fmt:message key="label.customerpartnumber"/>',
  width:10
},
{ columnId:"deliveryPoint",
  columnName:'<fmt:message key="label.deliverypoint"/>',
  tooltip:"Y",
  width:10
},
{ columnId:"notes",
  columnName:'<fmt:message key="label.notes"/>',
  tooltip:"Y",
  width:15
},
{ columnId:"holdNotes",
  columnName:'<fmt:message key="label.holdcomments"/>',
  tooltip:"Y",
  width:15
},
{ columnId:"rejectionComment",
  permission: true,
  columnName:'<fmt:message key="label.rejectioncomments"/>',
  tooltip:"Y",
  width:15,
  type:"hed",
  submit:true
},
{ columnId:"facilityId",
  columnName:'<fmt:message key="label.facility"/>',
  width:10,
  tooltip:"Y"
},
{ columnId:"inventoryGroup", columnName:'<fmt:message key="label.inventorygroup"/>', width:10, tooltip:"Y"},
{ columnId:"allocateByMfgLot",
  columnName:'<fmt:message key="label.lotcontrolled"/>',
  width:5,
  tooltip:"Y"
},
{ columnId:"shipToLocationId",
  columnName:'<fmt:message key="label.shipto"/>',
  width:10,
  submit:true	
},
{ columnId:"application",
  columnName:'<fmt:message key="label.workarea"/>',
  width:10,
  submit:true
},
{ columnId:"endUser",
  columnName:'<fmt:message key="label.enduser"/>',
  width:10,
  submit:true
},
{ columnId:"requestor"},
{ columnId:"requestorName", columnName:'<fmt:message key="label.requestor"/>', width:12, tooltip:"Y", submit:true},
{ columnId:"packerOverride", width: 6, align:"center", columnName:'<fmt:message key="label.packeroverride"/>'},
{ columnId:"labelOverride", width: 6, align:"center", columnName:'<fmt:message key="label.labeloverride"/>'},
{ columnId:"qtyOverride", width: 6, align:"center", columnName:'<fmt:message key="label.qtyoverride"/>'},
{ columnId:"dotOverride", width: 8, columnName:'<fmt:message key="label.dotoverride"/>'},
{ columnId:"pickerName", columnName:'<fmt:message key="label.picker"/>', width:12},
{ columnId:"pickDate", columnName:'<fmt:message key="label.datepicked"/>', width:7},
{ columnId:"packerName", columnName:'<fmt:message key="label.packer"/>', width:12},
{ columnId:"packDate", columnName:'<fmt:message key="label.packeddate"/>', width:7},
{ columnId:"dateDelivered", columnName:'<fmt:message key="label.datedelivered"/>',  width:7},
{ columnId:"overrideOption"},
{ columnId:"issueId"},
{ columnId:"tabletShipmentId"}
];

var pickingState = [
	<c:forEach var="ps" items="${vvPickingStateColl}" varStatus="status">
		{disabled:${not ps.pickingStatusPgAssignable}, text:'<tcmis:jsReplace value="${ps.pickingStateDesc}" processMultiLines="false"/>', value:'<tcmis:jsReplace value="${ps.pickingState}" processMultiLines="false"/>'}<c:if test = "${!status.last}">,</c:if>
	</c:forEach>
];

var pickingGroupId = [
	<c:forEach var="pg" items="${vvPickingGroupColl}" varStatus="status">
		{text:'<tcmis:jsReplace value="${pg.pickingGroupName}" processMultiLines="false"/>', value:'<tcmis:jsReplace value="${pg.pickingGroupId}" processMultiLines="false"/>'}<c:if test = "${!status.last}">,</c:if>
	</c:forEach>
];

var gridConfig = {
		divName:'pickingStatusData',
		beanData:'jsonMainData',
		beanGrid:'beangrid',
		config:'columnConfig',
		rowSpan:true,
		noSmartRender: false,
		submitDefault:false,
		onRightClick:rightClickRow
};

var rowSpanMap = new Array();
var rowSpanClassMap = new Array();
var rowSpanCols = [5,6,7,8,9,11,12,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42];

var pickingUnitDocs = [];
<c:if test="${ ! empty pickingUnitDocuments}">
pickingUnitDocs = [
	<c:set var="puid" value="0"/>
	<c:set var="docCount" value="0"/>
	<c:forEach var="pDoc" items="${pickingUnitDocuments}" varStatus="status">
		<c:if test="${puid eq pDoc.pickingUnitId}">
			<c:if test="${docCount > 0}">,</c:if>
		</c:if>
		<c:if test="${puid != pDoc.pickingUnitId}">
			<c:if test="${puid != 0}">]},</c:if>
			<c:set var="docCount" value="0"/>
			<c:set var="puid" value="${pDoc.pickingUnitId}"/>
		{pickingUnitId:"${pDoc.pickingUnitId}",
		  documents:[
		</c:if>
		{type:'<tcmis:jsReplace value="${pDoc.documentType}" processMultiLines="false"/>',
				url:'${pDoc.documentPath}'}
		<c:set var="docCount" value="${docCount+1}"/>
	</c:forEach>
]}];
</c:if>

var shippingOverridePermission = false;
<tcmis:facilityPermission indicator="true" userGroupId="overrideShippingInformation">
	shippingOverridePermission = true;
</tcmis:facilityPermission>
// -->
</script>
</head>
<body onload="resultOnLoad();" onunload="closeAllchildren();">
	<tcmis:form action="/pickingstatusresults.do" onsubmit="return submitFrameOnlyOnce();">
<div id="errorMessagesAreaBody" style="display:none;">
	${tcmISError}<br/>
	<c:forEach var="tcmisError" items="${tcmISErrors}">
		${tcmisError}<br/>
	</c:forEach>
	<c:if test="${param.maxData == fn:length(pickingGroupColl)}">
		<fmt:message key="label.maxdata">
			<fmt:param value="${param.maxData}"/>
		</fmt:message>
		&nbsp;<fmt:message key="label.clickcreateexcelforcompleteresult"/>
	</c:if>
</div>
<script type="text/javascript">
	<c:choose>
		<c:when test="${empty tcmISErrors and empty tcmISError}">
			<c:choose>
				<c:when test="${param.maxData == fn:length(pickingUnitColl)}">
					showErrorMessage = true;
					parent.messagesData.errors = "<fmt:message key="label.noticewindowtitle"/>";
				</c:when>
				<c:otherwise>
					showErrorMessage = false;
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			parent.messagesData.errors = "<fmt:message key="label.errors"/>";
			showErrorMessage = true;
		</c:otherwise>
	</c:choose>
	//-->
</script>
		<div class="interface" id="resultsPage">
			<div class="backGroundContent">
				<div id="pickingStatusData" style="width:100%;height:400px;" style="display:none;"></div>
				<c:if test="${pickingUnitColl != null}">
				<script type="text/javascript">
					<!--
					<c:set var="dataCount" value="${0}"/>
						<c:if test="${!empty pickingUnitColl}" >
							var jsonMainData = {
								rows:[<c:forEach var="bean" items="${pickingUnitColl}" varStatus="status">
									<c:set var="dataCount" value="${dataCount + 1}"/>
									{ 	id:${status.count},
										data:[	'Y',
										      	'Y',
											    'Y',
												'${bean.pickingStatusPgAssignable}',
												'${bean.pickingStatusPgAssignable}',
										    '',
										    '${bean.pickingUnitId}',
										    '<tcmis:jsReplace value="${bean.pickingGroupId}" processMultiLines="false" />',
											'${bean.picklistId}',
											'<tcmis:jsReplace value="${bean.pdocs}" processMultiLines="false" />',
											'<fmt:formatDate value="${bean.pickCreationDate}" pattern="${dateFormatPattern}"/>',
											'<tcmis:jsReplace value="${bean.pickingState}" processMultiLines="false" />',
											'${bean.prNumber} - ${bean.lineItem}',
											'<fmt:formatDate value="${bean.needDate}" pattern="${dateFormatPattern}"/>',
											'${bean.itemId}',
											'${bean.receiptId}',
											'${bean.quantity}',
											'${bean.itemType}',
											'<tcmis:jsReplace value="${bean.catalogDesc}" processMultiLines="false"/>',
											'${bean.poNumber}',
											'${bean.facPartNo}',
											'<tcmis:jsReplace value="${bean.deliveryPoint}" processMultiLines="false" />',
											'<tcmis:jsReplace value="${bean.notes}" processMultiLines="true" />',
											'<tcmis:jsReplace value="${bean.holdComments}" processMultiLines="true" />',
											'<tcmis:jsReplace value="${bean.rejectionComment}" processMultiLines="true"/>',
											'<tcmis:jsReplace value="${bean.facilityName}" processMultiLines="false"/>',
											'<tcmis:jsReplace value="${bean.inventoryGroup}" processMultiLines="false"/>',
											'${bean.allocateByMfgLot}',
											'${bean.shipToLocationId}',
											'<tcmis:jsReplace value="${bean.applicationDesc}" processMultiLines="false"/>',
											'<tcmis:jsReplace value="${bean.endUser}" processMultiLines="false"/>',
											'${bean.requestor}',
											'<tcmis:jsReplace value="${bean.requestorName}" processMultiLines="false"/>',
											'${bean.packerOverride?"Y":"N"}',
											'${bean.labelOverride?"Y":"N"}',
											'${bean.qtyOverride?"Y":"N"}',
											'<tcmis:jsReplace value="${bean.dotOverride}" processMultiLines="false"/>',
											'<tcmis:jsReplace value="${bean.pickerName}" processMultiLines="false"/>',
											'<fmt:formatDate value="${bean.pickDate}" pattern="${dateFormatPattern}"/>',
											'<tcmis:jsReplace value="${bean.packerName}" processMultiLines="false"/>',
											'<fmt:formatDate value="${bean.packDate}" pattern="${dateFormatPattern}"/>',
											'<fmt:formatDate value="${bean.dateDelivered}" pattern="${dateFormatPattern}"/>',
											'${bean.overrideOption?"Y":"N"}',
											'${bean.issueId}',
											'${bean.tabletShipmentId}'
										]
									}<c:if test="${!status.last}">,</c:if>
								</c:forEach>
								]};
							
							<c:forEach var="pickUnit" items="${pickingUnitColl}" varStatus="status">
								<c:set var="currentKey" value='${pickUnit.pickingUnitId}' />
								<c:choose>
									<c:when test="${status.first}">
										<c:set var="rowSpanStart" value='0' />
										<c:set var="rowSpanCount" value='1' />
										rowSpanMap[0] = 1;
										rowSpanClassMap[0] = 1;
									</c:when>
									<c:when test="${currentKey == previousKey}">
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
					//-->
				</script>
				<!-- If the collection is empty say no data found -->
				<c:if test="${empty pickingUnitColl}">
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
				<!-- Hidden Element start -->
				<div id="hiddenElements" style="display:none">
					<c:set var="ppsOverrideApproval" value="N"/>
					<tcmis:facilityPermission indicator="true" userGroupId="PPSOverrideApproval" facilityId="${param.facility}">
						<c:set var="ppsOverrideApproval" value="Y"/>
					</tcmis:facilityPermission>
					<input type="hidden" name="uAction" id="uAction" value=""/>
					<input type="hidden" name="dotOverride" id="dotOverride" value=""/>
					<input type="hidden" name="pickingUnitId" id="pickingUnitId" value=""/>
					<input type="hidden" name="tabletShipmentId" id="tabletShipmentId" value=""/>
					<input type="hidden" name="sourceHub" id="sourceHub" value="<tcmis:jsReplace value="${param.sourceHub}"/>"/>
					<input type="hidden" name="overrideApproval" id="overrideApproval" value="${ppsOverrideApproval}"/>
					<input type="hidden" name="filterDate" id="filterDate" value="<tcmis:jsReplace value="${param.filterDate}"/>"/>
					<input type="hidden" name="pickingState" id="pickingState" value="<tcmis:jsReplace value="${param.pickingState}"/>"/>
					<input type="hidden" name="searchWhat" id="searchWhat" value="<tcmis:jsReplace value="${param.searchWhat}"/>"/>
					<input type="hidden" name="searchType" id="searchType" value="<tcmis:jsReplace value="${param.searchType}"/>"/>
					<input type="hidden" name="searchText" id="searchText" value="<tcmis:jsReplace value="${param.searchText}"/>"/>
					<input type="hidden" name="pickingGroupId" id="pickingGroupId" value="<tcmis:jsReplace value="${param.pickingGroupId}"/>"/>
					<input type="hidden" name="orderBy" id="orderBy" value="<tcmis:jsReplace value="${param.orderBy}"/>"/>
					<input type="hidden" name="companyId" id="companyId" value="${hubCompanyId}"/>
					<input type="hidden" name="labelPrintQty" id="labelPrintQty" value="1"/>
					<input type="hidden" name="labelPrintType" id="labelPrintType" value=""/>
					<input type="hidden" name="paperSize" id="paperSize" value="31"/>
					<input name="totalLines" id="totalLines" type="hidden" value="${dataCount}"/>
					<input name="minHeight" id="minHeight" type="hidden" value="100"/>
				</div>
			</div>
			<!-- close of backgroundContent -->
		</div>
		<!-- close of interface -->
	</tcmis:form>
</body>
</html:html>