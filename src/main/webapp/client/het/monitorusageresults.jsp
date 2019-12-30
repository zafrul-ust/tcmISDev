
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>

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
		<link rel="shortcut icon"
			href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
		<%@ include file="/common/locale.jsp"%>
		<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
		<tcmis:gridFontSizeCss />
		<!-- Add any other stylesheets you need for the page here -->

		<script type="text/javascript" src="/js/common/formchek.js"></script>
		<script type="text/javascript" src="/js/common/commonutil.js"></script>
		<%--NEW - grid resize--%>
		<script type="text/javascript"
			src="/js/common/grid/resultiframegridresize.js"></script>
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
		<script type="text/javascript"
			src="/js/client/het/monitorusageresults.js"></script>

		<!-- These are for the Grid-->
		<script type="text/javascript"
			src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
		<script type="text/javascript"
			src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
		<script type="text/javascript"
			src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
		<%--This is for HTML form integration.--%>
		<script type="text/javascript"
			src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
		<%--This is for smart rendering.--%>
		<script type="text/javascript"
			src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
		<%--This is to suppoert loading by JSON.--%>
		<script type="text/javascript"
			src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
		<%--Uncomment below if you are providing header menu to switch columns on/off--%>
		<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
		<%--Uncomment the below if your grid has rwospans >1--%>
		<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
-->
		<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
		<script type="text/javascript"
			src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
		<%--This file has our custom sorting and other utility methos for the grid.--%>
		<script type="text/javascript"
			src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

		<title>Update Example</title> <script language="JavaScript"
			type="text/javascript">//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData = {
	alert:"<fmt:message key="label.alert"/>",
	and:"<fmt:message key="label.and"/>",
	validvalues:"<fmt:message key="label.validvalues"/>",
	submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
        recordFound:"<fmt:message key="label.recordFound"/>",
        searchDuration:"<fmt:message key="label.searchDuration"/>",
        minutes:"<fmt:message key="label.minutes"/>",
        seconds:"<fmt:message key="label.seconds"/>",
        ok:"<fmt:message key="label.ok"/>",
	nothingSelected:"<fmt:message key="label.norowselected"/>",
        quantityError:"<fmt:message key="label.positivenumber"><fmt:param><fmt:message key="label.quantity"/></fmt:param></fmt:message>",
	dateRequired:"<fmt:message key="errors.required"><fmt:param><fmt:message key="label.usagedate"/></fmt:param></fmt:message>",   
	remarksRequired:"<fmt:message key="errors.required"><fmt:param><fmt:message key="label.remarks"/></fmt:param></fmt:message>",   
	invalidEmployee:"<fmt:message key="errors.invalid"><fmt:param><fmt:message key="label.employee"/></fmt:param></fmt:message>",
      quantity:"<fmt:message key="label.quantity"/>",
      updateoneonly:"<fmt:message key="label.updateoneonly"/>",
      none:"<fmt:message key="label.none"/>"
        };             
var config = [
	{ columnId:"permission" },
	{ columnId:"ok", columnName:'<fmt:message key="label.ok"/>', type:'hchstatus', align:'center', width:3, onChange: checkMaxUpdates},
	{ columnId:"workArea", columnName:'<fmt:message key="label.workarea"/>', width:15, tooltip: true },
	{ columnId:"loggerName", columnName:'<fmt:message key="label.loggedby"/>', width:10, tooltip: true },
	{ columnId:"loggedDate", hiddenSortingColumn: 'loggedSort', columnName:'<fmt:message key="label.loggeddate"/>', width:8 },
	{ columnId:"loggedSort"},
	{ columnId:"transactionId", columnName:'<fmt:message key="label.transactionid"/>', align:'center', width:6 },
	{ columnId:"catPartNo", columnName:'<fmt:message key="label.partno"/>', width:8, align:'center' },
	{ columnId:"msdsNo", columnName:'<fmt:message key="label.msdsnumber"/>', width:8, align:'center' },
	{ columnId:"employee", columnName:'<fmt:message key="label.employee"/>', type:'hed', align:'left', width:7, size:4, maxlength:10},
	{ columnId:"containerId", columnName:'<fmt:message key="label.containerid"/>', width:7, align:'center'},
	{ columnId:"building", columnName:'<fmt:message key="label.building"/>', width:7},
	{ columnId:"permit", columnName:'<fmt:message key="label.permit"/>',  type:'hcoro', align:'center', width:10 , onChange: setControlDevice},
	{ columnId:"controlDevice", columnName:'<fmt:message key="label.controldevice"/>', align:'center', width:8},
	{ columnId:"applicationMethod", columnName:'<fmt:message key="label.applicationmethod"/>', type:'hcoro', align:'center', width:10},
	{ columnId:"partType", columnName:'<fmt:message key="label.parttype"/>', type:'hcoro', align:'center', width:10, onChange: toggleSubstrate},
	{ columnId:"painted", columnName:'<fmt:message key="label.painted"/>', type:'hchstatus', align:'center', width:5 },
	{ columnId:"substrate", columnName:'<fmt:message key="label.substrate"/>', type:'hcoro', align:'center', width:10 },
	{ columnId:"quantity", columnName:'<fmt:message key="label.usage"/>', attachHeader:'<fmt:message key="label.quantity"/>', type:'hed', align:'center', width:6, size:4, maxlength:5 },
	{ columnId:"unitOfMeasure", columnName:'#cspan', attachHeader:'<fmt:message key="label.unit"/>', align:"center", width:3},
	{ columnId:"amountRemaining", columnName:'#cspan', attachHeader:'<fmt:message key="label.remaining"/>',  type:'hed', align:'center', width:6, size:4, maxlength:5 },
	{ columnId:"discarded", columnName:'<fmt:message key="label.discard"/>', type:'hchstatus', align:'center', width:5 },
	{ columnId:"usageDate", hiddenSortingColumn: 'usageSort', type:'hcal', columnName:'<fmt:message key="label.usagedate"/>', align:'center', width:8 },
	{ columnId:"usageSort"},
	{ columnId:"tradeName", columnName:'<fmt:message key="label.tradename"/>', align:'center', width:15, tooltip: true },
	{ columnId:"exportedPermission" },
	{ columnId:"exported", columnName:'<fmt:message key="label.exported"/>', type:'hchstatus', align:'center', width:5, permission: true},
	{ columnId:"modifiedPermission" },
	{ columnId:"modified", columnName:'<fmt:message key="label.modified"/>', type:'hchstatus', align:'center', width:5, permission: true},
	{ columnId:"remarks", columnName:'<fmt:message key="label.remarks"/>', type:'hed', width:40, size:40, maxlength:200 }, 
	{ columnId:"itemId" },
	{ columnId:"containerType" },
	{ columnId:"reportingEntityId" },
	{ columnId:"applicationId" },
	{ columnId:"buildingId" },
	{ columnId:"cartId" },
	{ columnId:"userId" },
	{ columnId:"usageId" },
	{ columnId:"exportedDate" },
	{ columnId:"insertDate" }
];

// This is the array for type:'hcoro'. 
var substrate =  new Array(
	{value:'', text: ''},
	{value:'NA', text: '<fmt:message key="label.unauthorized"/>'}
	 <c:forEach var="sub" items="${input.substrates}" varStatus="statusSub">
		,{value:'${sub.substrateCode}', text:'<tcmis:jsReplace value="${sub.substrate}"/>'}
	 </c:forEach>
);

var applicationMethod = new  Array(
	{value:'NA', text: '<fmt:message key="label.unauthorized"/>'}
	<c:forEach var="app" items="${input.applicationMethods}" varStatus="statusApp">
		,{value:'${app.methodCode}', text:'<tcmis:jsReplace value="${app.method}"/>'}
	</c:forEach>
); 

var permit = [
		<c:forEach var="permit" items="${input.permits}" varStatus="status">
			{value:'<tcmis:jsReplace value="${permit.permitName}"/>', text: '<tcmis:jsReplace value="${permit.permitName} - ${permit.description}"/>'},
		</c:forEach>
		{value:'NONE', text: '<fmt:message key="label.none"/>'}
];

var controlDevices = new Array();
<c:forEach var="permit" items="${input.permits}" varStatus="status">
	<c:if test="${permit.controlDevicePresent}">controlDevices['<tcmis:jsReplace value="${permit.permitName}"/>'] = '<tcmis:jsReplace value="${permit.controlDevice}"/>';</c:if>
</c:forEach>

var partType = new  Array(
	{text:'<fmt:message key="label.aerospace"/>',value:'F'},
	{value:'R', text: '<fmt:message key="label.aircraftrework"/>'},
        {text:'<fmt:message key="label.nonaerospace"/>',value:'N'}
        );

with(milonic=new menuname("rightClickMenu")){
	top="offset=2"
	style = contextStyle;
	margin=3
	aI("text=<fmt:message key="label.changehistory"/>;url=javascript:showHistory();");
}

with(milonic=new menuname("rightClickMenuNoHistory")){
	top="offset=2"
	style = contextStyle;
	margin=3
	aI("text=<fmt:message key="label.changehistory"/>;disabled=true;offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;");
 }

drawMenus();

<c:if test="${not empty searchBuildingId}">searchBuildingId = ${searchBuildingId};</c:if>

var altPermitId = new Array();
<c:forEach var="permit" items="${allPermits}" varStatus="status">
	<c:if test="${status.first}"><c:set var="current" value="${permit.buildingId}"/><c:set var="first" value="1"/>
		altPermitId['${permit.buildingId}'] = new Array(
	</c:if>
	<c:if test="${current != permit.buildingId}"><c:set var="current" value="${permit.buildingId}"/><c:set var="first" value="1"/>
		);
		altPermitId['${permit.buildingId}'] = new Array(
	</c:if>
	<c:choose><c:when test="${first == 1}"><c:set var="first" value="0"/></c:when><c:otherwise>,</c:otherwise></c:choose>
	'<tcmis:jsReplace value="${permit.permitName}"/>'
	<c:if test="${status.last}">);</c:if>
</c:forEach>

var altPermitDesc = new Array();
<c:forEach var="permit" items="${allPermits}" varStatus="status">
	<c:if test="${status.first}"><c:set var="current" value="${permit.buildingId}"/><c:set var="first" value="1"/>
		altPermitDesc['${permit.buildingId}'] = new Array(
	</c:if>
	<c:if test="${current != permit.buildingId}"><c:set var="current" value="${permit.buildingId}"/><c:set var="first" value="1"/>
		);
		altPermitDesc['${permit.buildingId}'] = new Array(
	</c:if>
	<c:choose><c:when test="${first == 1}"><c:set var="first" value="0"/></c:when><c:otherwise>,</c:otherwise></c:choose>
	'<tcmis:jsReplace value="${permit.permitName} - ${permit.description}"/>'
	<c:if test="${status.last}">);</c:if>
</c:forEach>

</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoad()">
	<tcmis:form action="/monitorusageresults.do"
		onsubmit="return submitFrameOnlyOnce();">
		<div id="errorMessagesAreaBody" style="display: none;">
			<html:errors />
			${tcmISError}
			<c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br />
			</c:forEach>
		</div>

		<script type="text/javascript">
   <c:choose>
    <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISError && empty tcmISErrors }">
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

				<div id="HetUsageBean" style="width: 100%; height: 400px;"
					style="display: none;"></div>

				<script type="text/javascript"><c:set var="showUpdateLink" value='Y'/>

<c:if test="${!empty hetUsageBeanColl}" >  
var jsonMainData =  {
rows:[
<c:forEach var="bean" items="${hetUsageBeanColl}" varStatus="status">
	<c:choose>
		<c:when test="${bean.deleted}">
			<c:set var="updatePerm" value='N'/>
		</c:when>
		<c:when test="${bean.exported}">
			<c:set var="updatePerm" value='N'/>
			<tcmis:facilityPermission indicator="true" userGroupId="heTrackingEditor" facilityId="${param.facilityId}">
				<c:set var="updatePerm" value='Y'/>
			</tcmis:facilityPermission>
		</c:when>
		<c:otherwise>
			<c:set var="updatePerm" value='Y'/>
		</c:otherwise>
	</c:choose>
	{ id:${status.index +1},
	 data:['${updatePerm}',
	  false,
	  '<tcmis:jsReplace value="${bean.application}"/>',
	  '<tcmis:jsReplace value="${bean.loggerName}"/>',
      '<fmt:formatDate value="${bean.insertDate}" pattern="${dateFormatPattern}"/>',
      '${bean.insertDate}',
	  '${bean.transactionId}',
	  '${bean.catPartNo}',
	  '${bean.msdsNo}',
	  '<tcmis:jsReplace value="${bean.employee}"/>',
	  '${bean.containerId}',
	  '<tcmis:jsReplace value="${bean.buildingName}"/>',
	  '<tcmis:jsReplace value="${bean.permit}"/>',
	  '<tcmis:jsReplace value="${bean.controlDevice}"/>',
	  '<tcmis:jsReplace value="${bean.applicationMethod}"/>',
	  '${bean.partType}',
	  ${bean.painted},
	  '${bean.substrate}',
	  '${bean.quantity}',
	  '${bean.unitOfMeasure}',
	  '${bean.amountRemaining}',
	  ${bean.discarded},
	  '<fmt:formatDate value="${bean.usageDate}" pattern="${dateFormatPattern}"/>',
      '${bean.usageDate}',
	  '<tcmis:jsReplace value="${bean.tradeName}"/>',
	  <c:choose><c:when test="${bean.exported}">'Y'</c:when><c:otherwise>'N'</c:otherwise></c:choose>,
	  ${bean.exported},
	  'N',
	  ${bean.modified},
	  '<tcmis:jsReplace value="${bean.remarks}"/>',
	  '${bean.itemId}',
	  '${bean.containerType}',
	  '${bean.reportingEntityId}',
	  '${bean.applicationId}',
	  '${bean.buildingId}',
	  '${bean.cartId}',
	  '${bean.userId}',
	  '${bean.usageId}',
	  '${fmtInsertDate}',
	  '<fmt:formatDate value="${bean.exportedDate}" pattern="${dateFormatPattern}"/>'
	  ]}<c:if test="${!status.last}">,</c:if>
 </c:forEach>
]};
</c:if>
</script>

				<!-- If the collection is empty say no data found -->
				<c:if test="${empty hetUsageBeanColl}">
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="tableNoData" id="resultsPageTable">
						<tr>
							<td width="100%"><fmt:message key="main.nodatafound" /></td>
						</tr>
					</table>
				</c:if>

				<!-- Search results end -->

				<!-- Hidden element start -->
				<div id="hiddenElements" style="display: none;">
					<tcmis:setHiddenFields beanName="input" />
					<input name="uAction" id="uAction" value="" type="hidden">
						<input name="minHeight" id="minHeight" type="hidden" value="100">

							<!-- Popup Calendar input options for usageDate --> <input
							type="hidden" name="blockBefore_usageDate"
							id="blockBefore_usageDate" value="" /> <input type="hidden"
							name="blockAfter_usageDate" id="blockAfter_usageDate" value="" />
							<input type="hidden" name="blockBeforeExclude_usageDate"
							id="blockBeforeExclude_usageDate" value="" /> <input
							type="hidden" name="blockAfterExclude_usageDate"
							id="blockAfterExclude_usageDate"
							value="<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>" />
							<input type="hidden" name="inDefinite_usageDate"
							id="inDefinite_usageDate" value="" />
				</div>
				<!-- Hidden elements end -->

			</div>
			<!-- close of backGroundContent -->
		</div>
		<!-- close of interface -->

		<c:if test="${showUpdateLink == 'Y'}">
			<script type="text/javascript">        showUpdateLinks = true;
        </script>
		</c:if>

	</tcmis:form>
</body>
</html:html>