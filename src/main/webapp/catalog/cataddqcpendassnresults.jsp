<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>
<html:html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
<link rel="shortcut-icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
<%-- For Internationalization, copies data from calendarval.js --%>
<%@ include file="/common/locale.jsp" %>
<tcmis:gridFontSizeCss />
<script type="text/javascript" src="/js/jquery/jquery-1.7.1.js"></script>
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
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
<script type="text/javascript" src="/js/calendar/calendarval.js"></script>

<script type="text/javascript" src="/js/common/popupservice.js"></script>
<script type="text/javascript" src="/js/common/gridrowservice.js"></script>
<script type="text/javascript" src="/js/catalog/cataddqcpendassnresults.js"></script>
<title>Catalog Add QC Pending Assignment</title>
<script language="JavaScript" type="text/javascript">
	
		with(milonic=new menuname("rightClickMenu")){
			top = "offset=2";
			style = contextStyle;
			margin = 3;
			aI("text=<fmt:message key="label.approve"/>;url=javascript:approveCatAdd();");
			aI("text=<fmt:message key="label.reject"/>;url=javascript:rejectCatAdd();");
		}
		
		drawMenus();
		
		//add all the javascript messages here, this for internationalization of client side javascript messages
		var messagesData = {
			alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			validvalues:"<fmt:message key="label.validvalues"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
			recordFound:"<fmt:message key="label.recordFound"/>",
			searchDuration:"<fmt:message key="label.searchDuration"/>",
			minutes:"<fmt:message key="label.minutes"/>",
			seconds:"<fmt:message key="label.seconds"/>",
			norowselected:"<fmt:message key="error.norowselected"/>"
        };
		
		var columnConfig = [ 
			{columnId:"permission"},
			{columnId:"ok", columnName:'<fmt:message key="label.ok"/><br><input type="checkbox" name="okAll" id="okAll">', type:"hchstatus", width: 4, align: 'center', onChange: checkOk},
			{columnId:"reversed", columnName:'<fmt:message key="label.reversed"/>',width:4, align: 'center'},
			{columnId:"materialDesc", columnName:'<fmt:message key="label.materialdesc"/>', type:"hed", width:15, size:80},
			{columnId:"tradeName", columnName:'<fmt:message key="label.tradename"/>', type:"hed", width:15, size:80},
			{columnId:"mfgCatalogId", columnName:'<fmt:message key="label.mfgpartno"/>', type:"hed", width:10, size:50},
			{columnId:"grade", columnName:'<fmt:message key="label.grade"/>', type:"hed", width:10, size:50},
			{columnId:"dimension", columnName:'<fmt:message key="label.dimension"/>', type:"hed", width:10, size:50},
			{columnId:"sdsPresent",columnName:'<fmt:message key="label.sds"/>', width:6, align: 'center'},
			{columnId:"manufacturer",columnName:'<fmt:message key="label.manufacturer"/>', width:10},
			{columnId:"requestId",columnName:'<fmt:message key="label.request"/>', width:8},
			{columnId:"companyId",columnName:'<fmt:message key="label.company"/>', width:8},
			{columnId:"catalogId",columnName:'<fmt:message key="label.catalog"/>', width:8},
			{columnId:"facilityId",columnName:'<fmt:message key="label.facility"/>', width:8},
			{columnId:"comments",columnName:'<fmt:message key="label.comments"/>', width:20, tooltip:"Y"},
			{columnId:"lineItem"},
			{columnId:"partId"}
        ];
		       
		var gridConfig = {
			divName:'catAddPendingAssnViewBean', // the div id to contain the grid. ALSO the var name for the submitted data
			beanData:'jsonMainData', // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
			beanGrid:'beangrid', // the grid's name, for later reference/usage
			config:columnConfig, // the column config var name, as in var config = { [ columnId:..,columnName...
			rowSpan: true, // true: this page has rowSpan > 1 or not, disables smartrendering & sorting
		    submitDefault: true, // the fields in grid are defaulted to be submitted or not.
		    noSmartRender: false, // Explicitly enable smartrender by setting this to false for rowspans
		    onRightClick:rightClickRow, // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
		    onRowSelect: selectTableRow,
		    onAfterHaasGridRendered: initializeCatAdd
	    };
		
		var rowSpanMap = [];
		var rowSpanClassMap = [];
		var rowSpanCols = [8,10,11,12,13];
	</script>
</head>

<body>
	<tcmis:form action="/catalogaddqcresults.do" onsubmit="return submitFrameOnlyOnce();">
		<div id="errorMessagesAreaBody" style="display: none;">
			<html:errors /> 
			${tcmisError} 
			<c:forEach items="${tcmISErrors}" varStatus="status">
		  		${catalogRequest}<br />
			</c:forEach>
		</div>
		<script type="text/javascript">
			<c:choose>
				<c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmisError }">showErrorMessage = false;</c:when>
				<c:otherwise>showErrorMessage = true;</c:otherwise>
			</c:choose>   
		</script>
	
	
		<div class="interface" id="resultsPage">
			<div class="backGroundContent">
				<!--Give the div name that holds the grid the same name as your viewbean or dynabean you want for updates-->
				<div id="catAddPendingAssnViewBean" style="width: 100%; height: 600px;" style="display: none;"></div>

				<c:choose>
					<c:when test="${empty resultsCollection}">
						<%-- If the collection is empty say no data found --%> 
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
							<tr>
								<td width="100%"><fmt:message key="main.nodatafound" /></td>
							</tr>
						</table>
					</c:when> 
					<c:otherwise>
						<script type="text/javascript">
							<c:set var="lineCount" value="${0}" />
							var jsonMainData = new Array();
							var jsonMainData = {
								rows:[
								<c:forEach var="catalogRequest" items="${resultsCollection}" varStatus="status">
									<c:set var="lineCount" value="${lineCount+1}"/>
                                    <c:if test="${lineCount > 1}">,</c:if>
										{id:${lineCount},
										 data:['Y',
											 '',
											'<c:out value="${catalogRequest.itemQcStatus eq 'Reversed'?'Y':'N'}"/>',
											'<tcmis:jsReplace value="${catalogRequest.materialDesc}" processMultiLines="false"/>',
											'<tcmis:jsReplace value="${catalogRequest.tradeName}" processMultiLines="false"/>',
											'<tcmis:jsReplace value="${catalogRequest.mfgCatalogId}" processMultiLines="false"/>',
											'<tcmis:jsReplace value="${catalogRequest.grade}" processMultiLines="false"/>',
											'<tcmis:jsReplace value="${catalogRequest.dimension}" processMultiLines="false"/>',
											'<a id="sdsPresent${lineCount}" href="#">${catalogRequest.sdsPresent eq "Y"?"Yes":"No"}</a>',
											'<tcmis:jsReplace value="${catalogRequest.manufacturer}" processMultiLines="false"/>',
											'${catalogRequest.requestId}',
											'<tcmis:jsReplace value="${catalogRequest.companyName}" processMultiLines="false"/>',
											'<tcmis:jsReplace value="${catalogRequest.catalogDesc}" processMultiLines="false"/>',
											'<tcmis:jsReplace value="${catalogRequest.facilityName}" processMultiLines="false"/>',
											'<tcmis:jsReplace value="${catalogRequest.comments}" processMultiLines="true"/>',
											'${catalogRequest.lineItem}',
											'${catalogRequest.partId}'
                                          ]}
								 </c:forEach>
								]};
						</script>
					</c:otherwise>				
				</c:choose>
				<!-- Search results end -->

				<%-- determining rowspan --%>
				<c:set var="lineCount" value='0' />
				<script language="JavaScript" type="text/javascript">
					<c:set var="previousKey" value=''/>
					<c:set var="rowSpanStart" value="${0}"/>
					<c:set var="rowSpanCount" value="${0}"/>
					<c:forEach var="catalogRequest" items="${resultsCollection}" varStatus="status">
						<c:set var="currentKey" value='${catalogRequest.requestId}' />
						<c:choose>
							<c:when test="${currentKey == previousKey}">
								rowSpanMap[${rowSpanStart}]++;
								rowSpanMap[${status.index}] = 0;
								rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
							</c:when>
							<c:otherwise>
								<c:set var="rowSpanCount" value="${rowSpanCount + 1}"/>
								<c:set var="rowSpanStart" value="${status.index}"/>
								rowSpanMap[${status.index}]=1;
								rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
							</c:otherwise>
						</c:choose>
						<c:set var="previousKey" value='${currentKey}'/>
					</c:forEach>
				</script>

				<!-- Hidden element start -->
				<div id="hiddenElements" style="display: none;">
					<%--This is the minimum height of the result section you want to display--%> 
					<input name="minHeight" id="minHeight" type="hidden" value="100" />
					<input name="rejectionComment" id="rejectionComment" type="hidden"/>
                    <tcmis:setHiddenFields beanName="inputBean"/>
				</div>
			
			</div>
		<!-- close of backGroundContent -->
		</div>
		<!-- interface End -->
	</tcmis:form>
</body>
</html:html>