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
	<script type="text/javascript" src="/js/client/het/cartitemsearchresults.js"></script>
	
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
	
	<title>
	    <fmt:message key="label.cartitemsearch"/>
	</title>

	<script language="JavaScript" type="text/javascript">
	<!--
		 showUpdateLinks = true;

		//add all the javascript messages here, this for internationalization of client side javascript messages
		var messagesData = new Array();
		messagesData = {
			alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			recordFound:"<fmt:message key="label.recordFound"/>",
			searchDuration:"<fmt:message key="label.searchDuration"/>",
			minutes:"<fmt:message key="label.minutes"/>",
			seconds:"<fmt:message key="label.seconds"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
			partNo:"<fmt:message key="label.partno"/>",
			returnItem:"<fmt:message key="label.return"/>"
		};
		
		var rowSpanMap = new Array();
		var rowSpanClassMap = new Array();
		var rowSpanCols = [0,1];
				
		var config = [
			{columnId:"catPartNo", columnName:'<fmt:message key="label.partno"/>', width:8, align:"center"},
			{columnId:"itemId", columnName:'<fmt:message key="label.item"/>', width:6, align:"center"},
			{columnId:"custMsdsNo", columnName:'<fmt:message key="label.msds"/>',width:6, align:"center" }, 
			{columnId:"containerId", columnName:'<fmt:message key="label.containerid"/>', width:8, align:"center"},
			{columnId:"materialDesc" , columnName:'<fmt:message key="label.materialdesc"/>', tooltip:true , width:22 },
			{columnId:"containerSize" , columnName:'<fmt:message key="label.containersize"/>', tooltip:true , width:22 }
		];
	
		var gridConfig = {
			divName:'cartItem', // the div id to contain the grid.
			beanData:'jsonMainData', // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
			beanGrid:'mygrid', // the grid's name, as in beanGrid.attachEvent...
			config:config, // the column config var name, as in var config = { [ columnId:..,columnName...
			rowSpan:true , // true: this page has rowSpan > 1 or not, disables smartrendering & sorting
			noSmartRender : false,
			onRowSelect: selectRow,
			submitDefault:false // the fields in grid are defaulted to be submitted or not.
		};
	
	//-->
	</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);<c:if test="${not empty cartItemCollection}">mygrid.selectRow(0, true);</c:if>">

	<tcmis:form action="/cartitemsearchresults.do" onsubmit="return submitFrameOnlyOnce();">
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
				<div id="cartItem" style="width:100%;height:500px;" style="display: none;"></div>
				<script type="text/javascript">
					<!--
					var jsonMainData = new Array();
					var jsonMainData = {
						rows:[<c:forEach var="row" items="${cartItemCollection}" varStatus="status">
							{id: ${status.count},
						 	 data:[ '${row.catPartNo}',
								'${row.itemId}',
								'<tcmis:jsReplace value="${row.custMsdsNo}"/>',
								'${row.containerId}',
								'<tcmis:jsReplace value="${row.materialDesc}"/>',
								'<tcmis:jsReplace value="${row.containerSize}"/>'
						 	 ]}<c:if test="${!status.last}">,</c:if>
						</c:forEach>]};

						<c:forEach var="row" items="${cartItemCollection}" varStatus="status">
							<c:choose>
								<c:when test="${empty row.kitId}">
									<c:set var="currentKey" value='NoKit${status.count}' />
								</c:when>
								<c:otherwise>
									<c:set var="currentKey" value='Cart${row.kitId}' />
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty row.kitId}">
									<c:set var="currentKeyLvl2" value='NoKit${status.count}' />
								</c:when>
								<c:otherwise>
									<c:set var="currentKeyLvl2" value='${row.catPartNo}-${row.itemId}-${row.kitId}' />
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${status.first}">
									<c:set var="dataCount" value='1' />
									<c:set var="rowSpanStart" value='0' />
									<c:set var="rowSpanCount" value='1' />
									rowSpanMap[0] = 1;
									rowSpanClassMap[0] = 1;
								</c:when>
								<c:when test="${empty currentKey}">
									<c:set var="rowSpanStart" value='${status.index}' />
									<c:set var="rowSpanCount" value='${rowSpanCount + 1}' />
									<c:set var="rowSpanLvl2Start" value='${status.index}' />
									rowSpanMap[${status.index}] = 1;
									rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
								</c:when>
								<c:when test="${!empty currentKey && currentKey == previousKey}">
									rowSpanMap[${rowSpanStart}]++;
									rowSpanMap[${status.index}] = 0;
									rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
								</c:when>
								<c:otherwise>
									<c:set var="dataCount" value='${dataCount + 1}' />
									<c:set var="rowSpanStart" value='${status.index}' />
									<c:set var="rowSpanCount" value='${rowSpanCount + 1}' />
									rowSpanMap[${status.index}] = 1;
									rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
								</c:otherwise>
							</c:choose>
							<c:set var="previousKey" value='${currentKey}' />
						</c:forEach>						
					 //-->
				</script>
				<!-- Hidden element start -->
				<div id="hiddenElements" style="display: none;">
					<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden"/>
					<input name="minHeight" id="minHeight" type="hidden" value="100"/>
					<input name="workAreaNAme" id="workAreaNAme" type="hidden" value="${param.workAreaName}"/>
				 </div>
			</div> <!-- close of backGroundContent -->
		</div> <!-- close of interface -->

	</tcmis:form>
</body>
</html:html>
