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
	<script type="text/javascript" src="/js/client/het/cartmanagementresults.js"></script>
		
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
			checkingWarning:"<fmt:message key="label.checkinwarning"/>",
			employeeRequired:"<fmt:message key="errors.required"><fmt:param><fmt:message key="label.employee"/></fmt:param></fmt:message>",
			invalidEmployee:"<fmt:message key="errors.invalid"><fmt:param><fmt:message key="label.employee"/></fmt:param></fmt:message>",
			itemRequired:"<fmt:message key="errors.morerequired"><fmt:param><fmt:message key="label.container"/></fmt:param><fmt:param>1</fmt:param><fmt:param><fmt:message key="label.item"/></fmt:param></fmt:message>",
			nameRequired:"<fmt:message key="errors.required"><fmt:param><fmt:message key="label.cartname"/></fmt:param></fmt:message>",
			waiting:"<fmt:message key="label.waitingforinputfrom"><fmt:param><fmt:message key="label.cartitemsearch"/></fmt:param></fmt:message>",
			verifyDelete:"<fmt:message key="label.verifydelete"><fmt:param><fmt:message key="label.cart"/></fmt:param></fmt:message>",
			onCart:"<fmt:message key="errors.containeroncart"/>"
		};

		var rowSpanMap = new Array();
		var rowSpanClassMap = new Array();
		var rowSpanCols = [4,5,6,7,8];
		var rowSpanLvl2Map = new Array();
		var rowSpanLvl2Cols = [9,10,11];
				
		var config = [
		      	{columnId:"permission"},
			{columnId:"cartNamePermission"},
			{columnId:"employeePermission"},
			{columnId:"checkedOutPermission"},
			{columnId:"sortOrderPermission"},
			{columnId:"active", columnName:'<fmt:message key="label.checkout"/>', width:4, type:'hchstatus', align: "center", onChange: confirmCheckin},
			{columnId:"cartName", columnName:'<fmt:message key="label.cartname"/>', type:'hed', width:8, align:"center", maxlength: 8, permission: true},
			{columnId:"employee", columnName:'<fmt:message key="label.employee"/>', type:'hed', width:12, align:"center", maxlength: 30, permission: true},
			{columnId:"checkedOut", columnName:'<fmt:message key="label.checkoutdate"/>', type:'hcal', width:8,align:'center', permission: true }, 
			{columnId:"application" , columnName:'<fmt:message key="label.workarea"/>', tooltip:true , width:22 },
			{columnId:"sortOrder", columnName:'<fmt:message key="label.sortorder"/>', width:4, type:'hed', maxlength: 4, align: "center", onChange: rippleChangesToKit, permission: true},
			{columnId:"catPartNo", columnName:'<fmt:message key="label.partno"/>', width:8, align:"center"},
			{columnId:"itemId", columnName:'<fmt:message key="label.item"/>', width:6, align:"center"},
			{columnId:"containerId" , columnName:'<fmt:message key="label.containerid"/>', tooltip:true , width:12 },
			{columnId:"materialDesc" , columnName:'<fmt:message key="label.materialdesc"/>', tooltip:true , width:22 },
			{columnId:"custMsdsNo", columnName:'<fmt:message key="label.msds"/>',width:6, align:"center" }, 
			{columnId:"containerSize" , columnName:'<fmt:message key="label.containersize"/>', tooltip:true , width:22 },
			{columnId:"cartId"},
			{columnId:"newCart"},
			{columnId:"newCartItem"},
			{columnId:"cartItemDeleted"},
			{columnId:"oldName"},
			{columnId:"oldStatus"},
			{columnId:"oldSortOrder"},
			{columnId:"companyId" }, 
			{columnId:"facilityId" }, 
			{columnId:"applicationId" }
		];
				
		var gridConfig = {
			divName:'cartManagement', // the div id to contain the grid.
			beanData:'jsonMainData', // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
			beanGrid:'mygrid', // the grid's name, as in beanGrid.attachEvent...
			config:config, // the column config var name, as in var config = { [ columnId:..,columnName...
			rowSpan:true , // true: this page has rowSpan > 1 or not, disables smartrendering & sorting
			noSmartRender : false,
			onRightClick: onRightClick, // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
			onRowSelect: selectRow,
			selectChild: 1,
			submitDefault:true // the fields in grid are defaulted to be submitted or not.
		};

		with(milonic=new menuname("rightClickMenu")){
			top="offset=2"
			style = contextStyle;
			margin=3
			aI("text=<fmt:message key="label.removecontainer"/>;url=javascript:removeItem();");
			aI("text=<fmt:message key="label.addcontainer"/>;url=javascript:addItem();");
			aI("text=<fmt:message key="label.deletecart"/>;url=javascript:deleteCart();");
		}

		with(milonic=new menuname("rightClickMenuNoRemove")){
			top="offset=2"
			style = contextStyle;
			margin=3
			aI("text=<fmt:message key="label.addcontainer"/>;url=javascript:addItem();");
			aI("text=<fmt:message key="label.deletecart"/>;url=javascript:deleteCart();");
		 }

		drawMenus();
		
		// -->
	</script>
</head>

<body bgcolor="#ffffff" onload="myResultOnLoadWithGrid(gridConfig);">

	<tcmis:form action="/cartmanagementresults.do" onsubmit="return submitFrameOnlyOnce();">

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
				<div id="cartManagement" style="width:100%;height:500px;" style="display: none;"></div>
				<script type="text/javascript">
					<!--
					<jsp:useBean id="today" class="java.util.Date"/>
					formattedToday = '<fmt:formatDate value="${today}" pattern="${dateFormatPattern}"/>';
					var jsonMainData = new Array();
					var jsonMainData = {
						rows:[<c:forEach var="row" items="${cartCollection}" varStatus="status">
							{id: ${status.count},
						 	 data:[ 'Y',
								<c:choose>
									<c:when test="${!row.active}">'Y','Y', 'Y', 'Y',</c:when>
									<c:otherwise>'N', 'N', 'N', 'N',</c:otherwise>
								</c:choose>
								${row.active},
								'<tcmis:jsReplace value="${row.cartName}"/>',
								'<tcmis:jsReplace value="${row.employee}"/>',
								'<c:choose><c:when test="${!row.active}"><fmt:formatDate value="${today}" pattern="${dateFormatPattern}"/></c:when><c:otherwise><fmt:formatDate value="${row.checkedOut}" pattern="${dateFormatPattern}"/></c:otherwise></c:choose>',
								'<tcmis:jsReplace value="${row.applicationDesc}"/>',
								'${row.sortOrder}',
								'${row.catPartNo}',
								'${row.itemId}',
								'${row.containerId}',
								'<tcmis:jsReplace value="${row.materialDesc}"/>',
								'<tcmis:jsReplace value="${row.custMsdsNo}"/>',
								'<tcmis:jsReplace value="${row.containerSize}"/>',
								'${row.cartId}',
								false,
								false,
								false,
								'<tcmis:jsReplace value="${row.cartName}"/>',
								'${row.cartStatus}',
								'${row.sortOrder}',
								'${param.facilityId}',
								'${param.workArea}'
						 	 ]}<c:if test="${!status.last}">,</c:if>
						</c:forEach>]};

						<c:forEach var="row" items="${cartCollection}" varStatus="status">
							<c:set var="currentKey" value='${row.cartId}' />
							<c:choose>
								<c:when test="${not empty row.kitId}">
									<c:set var="currentKeyLvl2" value='kit${row.kitId}' />
								</c:when>
								<c:otherwise>
									<c:set var="currentKeyLvl2" value='cntr${row.containerId}' />
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
						<jsp:useBean id="input" class="com.tcmis.client.het.beans.CartManagementInputBean" scope="request"/>
						<jsp:setProperty name="input" property="totalLines" value='${rowSpanCount}' />
					 //-->
				</script>
				<!-- Hidden element start -->
				<div id="hiddenElements" style="display: none;">
					<tcmis:setHiddenFields beanName="input"/>
					<input name="minHeight" id="minHeight" type="hidden" value="100"/>
					  <!-- Popup Calendar input options for usageDate -->
					<input type="hidden" name="blockBefore_checkedOut" id="blockBefore_checkedOut" value=""/>
					<input type="hidden" name="blockAfter_checkedOut" id="blockAfter_checkedOut" value=""/>
					<input type="hidden" name="blockBeforeExclude_checkedOut" id="blockBeforeExclude_checkedOut" value=""/>
					<input type="hidden" name="blockAfterExclude_checkedOut" id="blockAfterExclude_checkedOut" value="<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>"/>
					<input type="hidden" name="inDefinite_checkedOut" id="inDefinite_checkedOut" value=""/>
				 </div>
			</div> <!-- close of backGroundContent -->
		</div> <!-- close of interface -->

	</tcmis:form>
</body>
</html:html>