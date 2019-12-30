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
	<%@ include file="/common/locale.jsp" %>
	<!--Use this tag to get the correct CSS class.
	This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
	<tcmis:gridFontSizeCss />
	<!-- Add any other stylesheets you need for the page here -->
	
	<script type="text/javascript" src="/js/common/formchek.js"></script>
	<script type="text/javascript" src="/js/common/commonutil.js"></script>
	<%--NEW - grid resize--%>
	<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
	<!-- This handels which key press events are disabeled on this page -->
	<script src="/js/common/disableKeys.js"></script>
	
	<!-- For Calendar support -->
	<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
	<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
	<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
	<script type="text/javascript" src="/js/calendar/calendarval.js"></script>
	
	<!-- This handles the menu style and what happens to the right click on the whole page -->
	<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
	<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
	<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
	<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
	<%@ include file="/common/rightclickmenudata.jsp" %>
	
	<!-- Add any other javascript you need for the page here -->
	<script type="text/javascript" src="/js/client/het/usageloggingresults.js"></script>
		
	<!-- These are for the Grid, uncomment if you are going to use the grid -->
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

	<title><fmt:message key="label.usagelogging"/></title>

	<script language="JavaScript" type="text/javascript">
		<!--
		//add all the javascript messages here, this for internationalization of client side javascript messages
		var messagesData = new Array();
		messagesData={
			alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			validvalues:"<fmt:message key="label.validvalues"/>",
			updateSuccess:"<fmt:message key="msg.updatesuccess"/>",
			recordFound:"<fmt:message key="label.recordFound"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
			searchDuration:"<fmt:message key="label.searchDuration"/>",
			minutes:"<fmt:message key="label.minutes"/>",
			seconds:"<fmt:message key="label.seconds"/>",
			nothingSelected:"<fmt:message key="label.norowselected"/>",
			permitRequired:"<fmt:message key="errors.required"><fmt:param><fmt:message key="label.permit"/></fmt:param></fmt:message>",
			partTypeRequired:"<fmt:message key="errors.required"><fmt:param><fmt:message key="label.parttype"/></fmt:param></fmt:message>",
			substrateRequired:"<fmt:message key="errors.required"><fmt:param><fmt:message key="label.substrate"/></fmt:param></fmt:message>",
			methodRequired:"<fmt:message key="errors.required"><fmt:param><fmt:message key="label.applicationmethod"/></fmt:param></fmt:message>",
			employeeRequired:"<fmt:message key="errors.required"><fmt:param><fmt:message key="label.employee"/></fmt:param></fmt:message>",
			containerRequired:"<fmt:message key="errors.required"><fmt:param><fmt:message key="label.containerid"/></fmt:param></fmt:message>",
			quantityRequired:"<fmt:message key="errors.required"><fmt:param><fmt:message key="label.quantity"/></fmt:param></fmt:message>",
			quantityNotChanged:"<fmt:message key="errors.notchanged"><fmt:param><fmt:message key="label.quantity"/></fmt:param></fmt:message>",
			quantityTooLarge:"<fmt:message key="errors.moreremainingthancheckedout"/>",
			quantityNumber:"<fmt:message key="label.positivenumber"><fmt:param><fmt:message key="label.quantity"/></fmt:param></fmt:message>",
			invalidEmployee:"<fmt:message key="errors.invalid"><fmt:param><fmt:message key="label.employee"/></fmt:param></fmt:message>",
			invalidQuantity:"<fmt:message key="errors.moreremainingthancheckedout"/>",
			invalidQuantity2:"<fmt:message key="errors.moreremainingthanfull"/>",
			invalidUOM:"<fmt:message key="errors.unitofmeasuredoesnotmatchpriorcheckin"/>",
			invalidConversion:"<fmt:message key="errors.unabletoconvertcontainerto"/>",
			invalidContainer:"<fmt:message key="errors.nosuchcontainerforitem"/>",
			invalidEmptyContainer:"<fmt:message key="errors.containerisempty"/>",
			rangeError:"<fmt:message key="errors.range"/>",
			usageDate:"<fmt:message key="label.usagedate"/>",
			itemInteger:"<fmt:message key="error.item.integer"/>",
			otherContainer:"<fmt:message key="othercontainer"/>",
			addThinner:"<fmt:message key="label.addthinner"/>",
			waiting:"<fmt:message key="label.waitingforinputfrom"/>",
			pleaseselect: '<fmt:message key="label.pleaseselect"/>',
			none: '<fmt:message key="label.none"/>'
		};

		var rowSpanMap = new Array();
		var rowSpanClassMap = new Array();
		var rowSpanCols = [0,2,3,11,16];
		var rowSpanLvl2Map = new Array();
		var rowSpanLvl2Cols = [1, 6,15,17,18,19,20,21,22,23,24];
				
		var config = [
		    {columnId:"permission"},
			{columnId:"updated", columnName:'<fmt:message key="label.ok"/>', width:3, type:'hchstatus', align: "center", onChange: toggleUpdated},
			{columnId:"cartName"<c:if test="${cartSearch}">, columnName:'<fmt:message key="label.cart"/>', width:5, align:"center", tooltip:true</c:if>},
			{columnId:"cartId"},
			{columnId:"catPartNo", columnName:'<fmt:message key="label.partno"/>', width:8, align:"center"},
			{columnId:"itemId", columnName:'<fmt:message key="label.item"/>', width:6, align:"center"},
			{columnId:"mixtureName", columnName:'<fmt:message key="label.mixturename"/>', width:10, align:"center", tooltip:true},
			{columnId:"kitId" }, 
			{columnId:"materialId" }, 
			{columnId:"materialDesc" , columnName:'<fmt:message key="label.materialdesc"/>', tooltip:true , width:22 },
			{columnId:"msdsNo", columnName:'<fmt:message key="label.msds"/>',width:6, align:"center" }, 
			{columnId:"employee", columnName:'<fmt:message key="label.employee"/>', type:'hed', width:8, align:"center"}, 
			{columnId:"containerId", columnName:'<fmt:message key="label.containerid"/>', width:12, align:"center"}, 
			{columnId:"amountRemaining", columnName:'<fmt:message key="label.remaining"/>',attachHeader:'<fmt:message key="label.quantity"/>', type:'hed', width:6 }, 
			{columnId:"unitOfMeasure", columnName:'#cspan', attachHeader:'<fmt:message key="label.unit"/>', type:"hcoro", align:"center", width:6},
			{columnId:"discarded", columnName:'<fmt:message key="label.discard"/>', width:4, type:'hchstatus', align: "center"},
			{columnId:"usageDate", columnName:'<fmt:message key="label.usagedate"/>', type:'hcal', width:8,align:'center' }, 
			{columnId:"partType", columnName:'<fmt:message key="label.parttype"/>', type:"hcoro", align:"center", width:10, onChange: toggleSubstrate},
			{columnId:"areaId"<c:if test="${multiBuilding}">, columnName:'<fmt:message key="label.area"/>', type:"hcoro", align:"center", width:10, onChange: toggleBuilding</c:if>},
			{columnId:"buildingId"<c:if test="${multiBuilding}">, columnName:'<fmt:message key="label.building"/>', type:"hcoro", align:"center", width:10, onChange: togglePermits</c:if>},
			{columnId:"permit", columnName:'<fmt:message key="label.permit"/>', type:"hcoro", align:"center", width:8, onChange: setControlDevice},
			{columnId:"controlDevice", columnName:'<fmt:message key="label.controldevice"/>', align:'center', width:8},
			{columnId:"applicationMethod", columnName:'<fmt:message key="label.applicationmethod"/>', type:"hcoro", align:"center", width:10},
			{columnId:"painted", columnName:'<fmt:message key="label.painted"/>', type:'hchstatus', align:'center', width:5 },
			{columnId:"substrate", columnName:'<fmt:message key="label.substrate"/>', type:"hcoro", align:"center", width:10},
			{columnId:"remarks", columnName:'<fmt:message key="label.remarks"/>', type:'hed', width:40, size:40, maxlength:200 }, 
			{columnId:"checkedOut" }, 
			{columnId:"companyId" }, 
			{columnId:"facilityId" }, 
			{columnId:"reportingEntityId"}, 
			{columnId:"applicationId" },
			{columnId:"mixtureId"},
			{columnId:"inMixture"},
			{columnId:"hetMixture"},
			{columnId:"allowSplitKit"},
			{columnId:"separable"},
			{columnId:"containerType"},
			{columnId:"solvent", type: 'hchstatus'},
			{columnId:"msdsInMixture"}, 
			{columnId:"mixtureMsdsCount"}, 
			{columnId:"originalAmount"} 
		];

		var cartSearch = ${cartSearch};
		var multiBuilding = ${multiBuilding};
		<c:if test="${multiBuilding}">
			var areaId = new Array(
					<c:forEach var="area" items="${areas}" varStatus="status">
						 {value:'${area.areaId}',text:'<tcmis:jsReplace value="${area.areaName}"/>'}<c:if test="${!status.last}">,</c:if>
					</c:forEach>
				);
			 
			var buildingId = new Array(
					<c:forEach var="building" items="${buildings}" varStatus="status">
						 {value:'${building.buildingId}',text:'<tcmis:jsReplace value="${building.buildingName}"/>'}<c:if test="${!status.last}">,</c:if>
					</c:forEach>
					,{value:'-1',text:'<tcmis:jsReplace value="Fugitive"/>'}
				);
				
			var altBuildingId = new Array();
			<c:forEach var="building" items="${buildings}" varStatus="status">
				<c:if test="${status.first}">
					<c:set var="current" value="${building.areaId}"/>
					<c:set var="first" value="1"/>
					altBuildingId['${building.areaId}'] = new Array(
				</c:if>
				<c:if test="${current != building.areaId}">
					,-1);
					<c:set var="current" value="${building.areaId}"/>
					<c:set var="first" value="1"/>
					altBuildingId['${building.areaId}'] = new Array(
				</c:if>
				<c:choose><c:when test="${first == 1}"><c:set var="first" value="0"/></c:when><c:otherwise>,</c:otherwise></c:choose>
				'${building.buildingId}'
				<c:if test="${status.last}">
					,-1);
				</c:if>
			</c:forEach>
	
			var altBuildingDesc = new Array();
			<c:forEach var="building" items="${buildings}" varStatus="status">
				<c:if test="${status.first}">
					<c:set var="current" value="${building.areaId}"/>
					<c:set var="first" value="1"/>
					altBuildingDesc['${building.areaId}'] = new Array(
				</c:if>
				<c:if test="${current != building.areaId}">
					,'Fugitive');
					<c:set var="current" value="${building.areaId}"/>
					<c:set var="first" value="1"/>
					altBuildingDesc['${building.areaId}'] = new Array(
				</c:if>
				<c:choose><c:when test="${first == 1}"><c:set var="first" value="0"/></c:when><c:otherwise>,</c:otherwise></c:choose>
				'<tcmis:jsReplace value="${building.buildingName}"/>'
				<c:if test="${status.last}">
					,'Fugitive');
				</c:if>
			</c:forEach>
				
			var altPermitId = new Array();
			<c:forEach var="permit" items="${allPermits}" varStatus="status">
				<c:if test="${status.first}">
					<c:set var="current" value="${permit.buildingId}"/>
					<c:set var="first" value="1"/>
					altPermitId['${permit.buildingId}'] = new Array(
				</c:if>
				<c:if test="${current != permit.buildingId}">
					);
					<c:set var="current" value="${permit.buildingId}"/>
					<c:set var="first" value="1"/>
					altPermitId['${permit.buildingId}'] = new Array(
				</c:if>
				<c:choose><c:when test="${first == 1}"><c:set var="first" value="0"/></c:when><c:otherwise>,</c:otherwise></c:choose>
				'<tcmis:jsReplace value="${permit.permitName}"/>'
				<c:if test="${status.last}">
					);
				</c:if>
			</c:forEach>
	
			var altPermitDesc = new Array();
			<c:forEach var="permit" items="${allPermits}" varStatus="status">
				<c:if test="${status.first}">
					<c:set var="current" value="${permit.buildingId}"/>
					<c:set var="first" value="1"/>
					altPermitDesc['${permit.buildingId}'] = new Array(
				</c:if>
				<c:if test="${current != permit.buildingId}">
					);
					<c:set var="current" value="${permit.buildingId}"/>
					<c:set var="first" value="1"/>
					altPermitDesc['${permit.buildingId}'] = new Array(
				</c:if>
				<c:choose><c:when test="${first == 1}"><c:set var="first" value="0"/></c:when><c:otherwise>,</c:otherwise></c:choose>
				'<tcmis:jsReplace value="${permit.permitName} - ${permit.description}"/>'
				<c:if test="${status.last}">
					);
				</c:if>
			</c:forEach>		
		</c:if>
		
		var partType = [
			{value:'SELECT', text: '<fmt:message key="label.pleaseselect"/>'},
			{value:'F', text: '<fmt:message key="label.aerospace"/>'},
			{value:'R', text: '<fmt:message key="label.aircraftrework"/>'},
			{value:'N', text: '<fmt:message key="label.nonaerospace"/>'}
		];

		var unitOfMeasure = new Array(
			<c:forEach var="uom" items="${unitsOfMeasure}" varStatus="status">
				 {value:'${uom.sizeUnit}',text:'<tcmis:jsReplace value="${uom.sizeUnit}" />'}<c:if test="${!status.last}">,</c:if>
			</c:forEach>
		);

		var permit = [
			{value:'SELECT', text: '<fmt:message key="label.pleaseselect"/>'},
			<c:forEach var="permit" items="${input.permits}" varStatus="status">
				{value:'<tcmis:jsReplace value="${permit.permitName}"/>', text: '<tcmis:jsReplace value="${permit.permitName} - ${permit.description}"/>'},
			</c:forEach>
			{value:'NONE', text: '<fmt:message key="label.none"/>'}
		];


		var controlDevice = [
	     		{value:'', text: '          '}
		];		

		var controlDevices = new Array();
		<c:forEach var="permit" items="${input.permits}" varStatus="status">
			<c:if test="${permit.controlDevicePresent}">controlDevices['<tcmis:jsReplace value="${permit.permitName}"/>'] = '<tcmis:jsReplace value="${permit.controlDevice}"/>';</c:if>
		</c:forEach>

		
		var applicationMethod = [
 			{value:'SELECT', text: '<fmt:message key="label.pleaseselect"/>'},
			<c:forEach var="method" items="${input.applicationMethods}" varStatus="status">
				{value:'${method.methodCode}', text: '${method.methodCode} - <tcmis:jsReplace value="${method.method}"/>'},
			</c:forEach>
			{value:'NA', text: '<fmt:message key="label.unauthorized"/>'}
		];
			
		var applicationMethodForSolvent = [
    		{value:'SELECT', text: '<fmt:message key="label.pleaseselect"/>'},
   			<c:forEach var="method" items="${input.applicationMethods}" varStatus="status">
   				<c:if test="${method.forSolvent}">
   					{value:'${method.methodCode}', text: '${method.methodCode} - <tcmis:jsReplace value="${method.method}"/>'},
   				</c:if>
   			</c:forEach>
   			{value:'NA', text: '<fmt:message key="label.unauthorized"/>'}
   		];

		var substrate = [
			{value:'SELECT', text: '<fmt:message key="label.pleaseselect"/>'},
 			<c:forEach var="substrate" items="${input.substrates}" varStatus="status">
 				<c:if test="${not empty substrate.substrateCode}">{value:'${substrate.substrateCode}', text: '${substrate.substrateCode} - <tcmis:jsReplace value="${substrate.substrate}"/>'},</c:if>
 			</c:forEach>
			{value:'NA', text: '<fmt:message key="label.unauthorized"/>'}
 		];

	       var gridConfig = {
			       divName:'usageLogging', // the div id to contain the grid.
			       beanData:'jsonMainData', // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
			       beanGrid:'mygrid', // the grid's name, as in beanGrid.attachEvent...
			       config:config, // the column config var name, as in var config = { [ columnId:..,columnName...
			       rowSpan:true , // true: this page has rowSpan > 1 or not, disables smartrendering & sorting
			       noSmartRender : false,
				onRightClick: onRightClick, // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
				onRowSelect: onRowSelect,
			       onAfterHaasRenderRow: <c:choose><c:when test="${multiBuilding}">setupRow</c:when><c:otherwise>toggleSubstrateOnce</c:otherwise></c:choose>,
			       selectChild: 0,
			       submitDefault:true // the fields in grid are defaulted to be submitted or not.
	       };

		with(milonic=new menuname("rightClickMenu")){
			top="offset=2"
			style = contextStyle;
			margin=3
			aI("text=<fmt:message key="label.addthinner"/>;url=javascript:launchAddThinner();");
			aI("text=<fmt:message key="label.addcontainertomixture"/>;url=javascript:launchAddContainerToMixture();");
			aI("text=<fmt:message key="label.createadhocmixture"/>;url=javascript:launchCreateAdHocMixture();");
			aI("text=<fmt:message key="label.othercontainer"/>;url=javascript:launchOtherContainer();");
		}

		with(milonic=new menuname("rightClickMenuNoAddContainerToMixtureNoCreateAdHocMixture")){
			top="offset=2"
			style = contextStyle;
			margin=3
			aI("text=<fmt:message key="label.addthinner"/>;url=javascript:launchAddThinner();");
			aI("text=<fmt:message key="label.othercontainer"/>;url=javascript:launchOtherContainer();");
		}

		with(milonic=new menuname("rightClickMenuNoAddContainerToMixture")){
			top="offset=2"
			style = contextStyle;
			margin=3
			aI("text=<fmt:message key="label.addthinner"/>;url=javascript:launchAddThinner();");
			aI("text=<fmt:message key="label.createadhocmixture"/>;url=javascript:launchCreateAdHocMixture();");
			aI("text=<fmt:message key="label.othercontainer"/>;url=javascript:launchOtherContainer();");
		}

		with(milonic=new menuname("rightClickMenuNoCreateAdHocMixture")){
			top="offset=2"
			style = contextStyle;
			margin=3
			aI("text=<fmt:message key="label.addthinner"/>;url=javascript:launchAddThinner();");
			aI("text=<fmt:message key="label.addcontainertomixture"/>;url=javascript:launchAddContainerToMixture();");
			aI("text=<fmt:message key="label.othercontainer"/>;url=javascript:launchOtherContainer();");
		}

		with(milonic=new menuname("rightClickMenuNoOtherContainer")){
			top="offset=2"
			style = contextStyle;
			margin=3
			aI("text=<fmt:message key="label.addthinner"/>;url=javascript:launchAddThinner();");
			aI("text=<fmt:message key="label.addcontainertomixture"/>;url=javascript:launchAddContainerToMixture();");
			aI("text=<fmt:message key="label.createadhocmixture"/>;url=javascript:launchCreateAdHocMixture();");
		 }
		 
		with(milonic=new menuname("rightClickMenuNoOtherContainerNoAddContainerToMixture")){
			top="offset=2"
			style = contextStyle;
			margin=3
			aI("text=<fmt:message key="label.addthinner"/>;url=javascript:launchAddThinner();");
			aI("text=<fmt:message key="label.createadhocmixture"/>;url=javascript:launchCreateAdHocMixture();");
		 }

		with(milonic=new menuname("rightClickMenuNoOtherContainerNoCreateAdHocMixture")){
			top="offset=2"
			style = contextStyle;
			margin=3
			aI("text=<fmt:message key="label.addthinner"/>;url=javascript:launchAddThinner();");
			aI("text=<fmt:message key="label.addcontainertomixture"/>;url=javascript:launchAddContainerToMixture();");
		 }

		with(milonic=new menuname("rightClickMenuNoOtherContainerNoAddContainerToMixtureNoCreateAdHocMixture")){
			top="offset=2"
			style = contextStyle;
			margin=3
			aI("text=<fmt:message key="label.addthinner"/>;url=javascript:launchAddThinner();");
		 }

		drawMenus();

		
		// -->
	</script>
</head>

<body bgcolor="#ffffff" onload="myResultOnLoadWithGrid(gridConfig);">

	<tcmis:form action="/usageloggingresults.do" onsubmit="return submitFrameOnlyOnce();">

		<%-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
		     The default value of showUpdateLinks is false.--%>
		<script type="text/javascript">
			<!--
			showUpdateLinks = true;
				//-->
		</script>

		<%-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
		So this is just used to feed the YUI pop-up in the main page.
		Similar divs would have to be built to show any other messages in a pop-up.--%>
		<div id="errorMessagesAreaBody" style="display:none;">
			${tcmISError}<br/>
			<c:forEach items="${tcmISErrors}" varStatus="status">
				${status.current}<br/>
			</c:forEach>
		</div>

		<script type="text/javascript">
			<!--
			/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
			<c:choose>
				<c:when test="${empty tcmISErrors and empty tcmISError}">
					showErrorMessage = false;
				</c:when>
				<c:otherwise>
					showErrorMessage = true;
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${updateSuccess == 'Y'}">
					updateSuccess = true;
				</c:when>
				<c:otherwise>
					updateSuccess = false;
				</c:otherwise>
			</c:choose>

			//-->
		</script>

		<div class="interface" id="resultsPage">
			<div class="backGroundContent">
			
				<div id="usageLogging" style="width:100%;height:500px;" style="display: none;"></div>
				
				<c:if test="${!empty loggingCollection}">
					<jsp:useBean id="today" class="java.util.Date"/>
					<script type="text/javascript">
						<!--
						var jsonMainData = new Array();
						var jsonMainData = {
							rows:[<c:forEach var="row" items="${loggingCollection}" varStatus="status">
								{id: ${status.count},
							 	 data:[ <c:choose>
										<c:when test="${row.inMixture && !row.allowSplitKit && !row.separable}">'N',</c:when>
										<c:otherwise>'Y',</c:otherwise>
									</c:choose>
									'',
									'<tcmis:jsReplace value="${row.cartName}"/>',
									'${row.cartId}',
									'<tcmis:jsReplace value="${row.catPartNo}"/>',
									'${row.itemId}',
									'<tcmis:jsReplace value="${row.mixtureName}"/><c:if test="${not empty row.msdsInMixture}"> (MSDS: ${row.msdsInMixture})</c:if>',
									'${row.kitId}',
									'${row.materialId}',
									'<tcmis:jsReplace value="${row.materialDesc}"/>',
									'<tcmis:jsReplace value="${row.custMsdsNo}"/>',
									'<tcmis:jsReplace value="${row.employee}"/>',
									'${row.containerId}',
									'${row.amountRemaining}',
									'${row.unitOfMeasure}',
									false,
									'<fmt:formatDate value="${today}" pattern="${dateFormatPattern}"/>',
									'',
									'${input.areaId}',
									'${input.buildingId}',
									'SELECT',
									'',
									'SELECT',
									'SELECT',
									'',
									'',
									'<fmt:formatDate value="${row.checkedOut}" pattern="${dateFormatPattern}"/>',
									'${row.companyId}',
									'${param.facilityId}',
									'${param.workAreaGroup}',
									'${param.workArea}',
									'${row.mixtureId}',
									${row.inMixture},// in mix
									'<c:choose><c:when test="${row.hetMixture}">Y</c:when><c:otherwise>N</c:otherwise></c:choose>',// in mix
									${row.allowSplitKit},
									${row.separable},
									'${row.containerType}',
									${row.solvent},
									'${row.msdsInMixture}',
									'${row.mixtureMsdsCount}',
									'${row.amountRemaining}'
							 	 ]}<c:if test="${!status.last}">,</c:if>
							</c:forEach>]};

							<c:forEach var="row" items="${loggingCollection}" varStatus="status">
								<c:choose>
									<c:when test="${(!cartSearch || empty row.cartId) && empty row.kitId}">
										<c:set var="currentKey" value='NoCart${status.count}' />
									</c:when>
									<c:when test="${(!cartSearch || empty row.cartId) && !empty row.kitId}">
										<c:set var="currentKey" value='NoCartWithKit${row.mixtureId}-${row.kitId}' />
									</c:when>
									<c:otherwise>
										<c:set var="currentKey" value='Cart${row.cartId}' />
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${empty row.kitId || input.workAreaAllowedSplitKits}">
										<c:set var="currentKeyLvl2" value='NoKit${status.count}' />
									</c:when>
									<c:otherwise>
										<c:set var="currentKeyLvl2" value='${row.catPartNo}-${row.mixtureId}-${row.itemId}-${row.kitId}' />
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${status.first}">
										<c:set var="rowSpanStart" value='0' />
										<c:set var="rowSpanLvl2Start" value='0' />
										<c:set var="rowSpanCount" value='1' />
										rowSpanMap[0] = 1;
										rowSpanLvl2Map[0] = 1;
										rowSpanClassMap[0] = 1;
									</c:when>
									<c:when test="${empty currentKey}">
										<c:choose>
											<c:when test="${currentKeyLvl2 == previousKeyLvl2}">
												rowSpanMap[${rowSpanStart}]++;
												rowSpanMap[${status.index}] = 0;
												rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
												rowSpanLvl2Map[${rowSpanLvl2Start}]++;
												rowSpanLvl2Map[${status.index}] = 0;
											</c:when>
											<c:otherwise>
												<c:set var="rowSpanStart" value='${status.index}' />
												<c:set var="rowSpanCount" value='${rowSpanCount + 1}' />
												<c:set var="rowSpanLvl2Start" value='${status.index}' />
												rowSpanMap[${status.index}] = 1;
												rowSpanLvl2Map[${status.index}] = 1;
												rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
												rowSpanLvl2Map[${status.index}] = 1;
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:when test="${!empty currentKey && currentKey == previousKey}">
										rowSpanMap[${rowSpanStart}]++;
										rowSpanMap[${status.index}] = 0;
										rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
										<c:choose>
											<c:when test="${currentKeyLvl2 == previousKeyLvl2}">
												rowSpanLvl2Map[${rowSpanLvl2Start}]++;
												rowSpanLvl2Map[${status.index}] = 0;
											</c:when>
											<c:otherwise>
												<c:set var="rowSpanLvl2Start" value='${status.index}' />
												rowSpanLvl2Map[${status.index}] = 1;
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<c:set var="rowSpanStart" value='${status.index}' />
										<c:set var="rowSpanCount" value='${rowSpanCount + 1}' />
										<c:set var="rowSpanLvl2Start" value='${status.index}' />
										rowSpanMap[${status.index}] = 1;
										rowSpanLvl2Map[${status.index}] = 1;
										rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
									</c:otherwise>
								</c:choose>
								<c:set var="previousKey" value='${currentKey}' />
								<c:set var="previousKeyLvl2" value='${currentKeyLvl2}' />
							</c:forEach>						
						 //-->
					</script>
				</c:if>
				
				<%-- If the collection is empty say no data found --%>
				<c:if test="${empty loggingCollection}" >			
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
						<tr>
							<td width="100%">
								<c:choose>
									<c:when test ="${cartSearch}">
										No Checked Out Carts found for Cart Name.
									</c:when>
									<c:otherwise>
										<fmt:message key="main.nodatafound"/>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</table>
				</c:if>
	
				<!-- Hidden element start -->
				<div id="hiddenElements" style="display: none;">
					<tcmis:setHiddenFields beanName="input"/>
					<input name="minHeight" id="minHeight" type="hidden" value="100"/>
					  <!-- Popup Calendar input options for usageDate -->
					<input type="hidden" name="blockBefore_usageDate" id="blockBefore_usageDate" value=""/>
					<input type="hidden" name="blockAfter_usageDate" id="blockAfter_usageDate" value=""/>
					<input type="hidden" name="blockBeforeExclude_usageDate" id="blockBeforeExclude_usageDate" value=""/>
					<input type="hidden" name="blockAfterExclude_usageDate" id="blockAfterExclude_usageDate" value="<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>"/>
					<input type="hidden" name="inDefinite_usageDate" id="inDefinite_usageDate" value=""/>
				 </div>
			</div> <!-- close of backGroundContent -->
		</div> <!-- close of interface -->

	</tcmis:form>
</body>
</html:html>