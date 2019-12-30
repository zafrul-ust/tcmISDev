<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
	<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script>
	<%--NEW - grid resize--%>
	<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
	<!-- This handels which key press events are disabeled on this page -->
	<script src="/js/common/disableKeys.js"></script>
	
	<!-- This handles the menu style and what happens to the right click on the whole page -->
	<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
	<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
	<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
	<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
	<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
	<%@ include file="/common/rightclickmenudata.jsp" %>
	
	<!-- For Calendar support for column type hcal-->
	<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
	<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
	<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
	<script type="text/javascript" src="/js/calendar/calendarval.js"></script>
	
	<!-- Add any other javascript you need for the page here -->
	<script type="text/javascript" src="/js/catalog/catalogaddqcresults.js"></script>
	
	<!-- These are for the Grid-->
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
	<%--This is for HTML form integration.--%>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
	<%--This is for smart rendering.--%>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
	<%--This is to suppoert loading by JSON.--%>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>    
	<%--Uncomment below if you are providing header menu to switch columns on/off--%>
	<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
	
	<%--Uncomment the below if your grid has rowspans >--%>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
	
	<%--This has the custom cells we built, hcal - the internationalized calendar which we use
	    hlink- this is for any links you want tp provide in the grid, the URL/function to call
	    can be attached based on a even (rowselect etc)
	--%>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>

	<%--This file has our custom sorting and other utility methos for the grid.--%>    
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
	
	<title>
		<fmt:message key="catalogaddqc.title"/>
	</title>

	<script language="JavaScript" type="text/javascript">
	
		with(milonic=new menuname("rightClickMenu")){
			top = "offset=2";
			style = contextStyle;
			margin = 3;
			aI("text=<fmt:message key="label.open"/>;url=javascript:openCatalogQc();");
		}
		
		drawMenus();
		
		//add all the javascript messages here, this for internationalization of client side javascript messages
		var messagesData = new Array();
		messagesData= 
			{alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			validvalues:"<fmt:message key="label.validvalues"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
			open:"<fmt:message key="label.open"/>",
			request:"<fmt:message key="label.requestid"/>",
			recordFound:"<fmt:message key="label.recordFound"/>",
			searchDuration:"<fmt:message key="label.searchDuration"/>",
			minutes:"<fmt:message key="label.minutes"/>",
			seconds:"<fmt:message key="label.seconds"/>",
			itemInteger:"<fmt:message key="error.item.integer"/>",
			msds:"<fmt:message key="label.msds"/>",
			qc:"<fmt:message key="label.qc"/>",
			spec:"<fmt:message key="label.spec"/>",
			chemreq:"<fmt:message key="label.chemreq"/>",
            pendingMsdsIndexing:"<fmt:message key="label.pendingmsdsindexing"/>",
            pendingCompanyMsds:"<fmt:message key="label.pendingsdscustomindexing"/>",
            pendingTranslation:"<fmt:message key="label.pendingtranslation"/>",
            pendingSdsQc:"<fmt:message key="label.pendingsdsqc"/>",
            translation:"<fmt:message key="label.translation"/>"    
            };

		var assignedTo = new  Array(
	     	{value: '', text: 'Unassigned'}<c:if test="${!empty catalogUsers}">,</c:if>
            <c:forEach var="catalogUser" items="${catalogUsers}" varStatus="status">
                {value: '${catalogUser.personnelId}', text: '${catalogUser.personnelName}'}<c:if test="${!status.last}">,</c:if>
            </c:forEach>
        );
		
		<%-- See webroot/dhtmlxgrid/codebase/dhtmlxcommon_haas.js for option explanations of config & gridconfig --%>
		var columnConfig = [ 
			{columnId:"permission"},
			{columnId:"requestId", columnName:'<fmt:message key="label.requestid"/>', width:6, submit:true},
			{columnId:"catalogRequestType", columnName:'<fmt:message key="label.requesttype"/>', width:6},
			{columnId:"companyName", columnName:'<fmt:message key="label.company"/>',tooltip:true, width:8},
			{columnId:"catalogDesc", columnName:'<fmt:message key="label.catalog"/>', tooltip:true, width:8},
			{columnId:"facilityName", columnName:'<fmt:message key="label.facility"/>', tooltip:true, width:8},
			{columnId:"requestor", columnName:'<fmt:message key="label.requestor"/>', tooltip:true, width:8},
			{columnId:"updated", columnName:'<fmt:message key="label.ok"/>', type:'hchstatus', align:'center', width: 2, submit:true},
			{columnId:"assignedTo", columnName:'<fmt:message key="label.assignedto"/>', type:'hcoro', onChange:onChangeAssignedTo, align:'center', tooltip:true, width:10, submit:true},
			{columnId:"requestType"},
			{columnId:"displayStatus",columnName:'<fmt:message key="label.status"/>', tooltip:true, width:11},
			{columnId:"itemId",columnName:'<fmt:message key="label.itemid"/>',width:6},
			{columnId:"materialDesc", columnName:'<fmt:message key="label.materialdesc"/>', tooltip:true, width:20},
			{columnId:"packaging", columnName:'<fmt:message key="label.packaging"/>', tooltip:true, width:15},
			{columnId:"manufacturer", columnName:'<fmt:message key="label.manufacturer"/>', tooltip:true, width:15},
			{columnId:"comments",columnName:'<fmt:message key="label.comments"/>', tooltip:true, width:20},
			{columnId:"submitDate", columnName:'<fmt:message key="label.submitdate"/>', width:12},
			{columnId:"qcDate", columnName:'<fmt:message key="label.msdsqcdate"/>', width:12},
			{columnId:"companyId"},
            {columnId:"qcStatus"},
            {columnId:"lineItem"},
            {columnId:"status"},
            {columnId:"vendorAssignmentStatus"},
            {columnId:"originalAssignedTo",submit:true},
            {columnId:"startingView"},
            {columnId:"sdsIndexingClosed"},
            {columnId:"itemCreationClosed"},
            {columnId:"itemVerified"}
            ];
		       
	       var gridConfig = {
		       divName:'CatalogAddQcInputBean', // the div id to contain the grid. ALSO the var name for the submitted data
		       beanData:'jsonMainData', // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		       beanGrid:'mygrid', // the grid's name, for later reference/usage
		       config:columnConfig, // the column config var name, as in var config = { [ columnId:..,columnName...
		       rowSpan: true, // true: this page has rowSpan > 1 or not, disables smartrendering & sorting
		       submitDefault: false, // the fields in grid are defaulted to be submitted or not.
		       noSmartRender: false, // Explicitly enable smartrender by setting this to false for rowspans
		       onRightClick:rightClickRow, // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
		       selectChild: 1 // Double select single row
	       }; 

		// RowSpan variables:
		// rowSpanMap contains an entry for each row with its associated rowspan 1, 2, ... or 0 for child row 
		// rowSpanClassMap contains the color (odd, even) for the row
		// rowSpanCols contains the indexes of the columns that span
	       var rowSpanMap = new Array();
	       var rowSpanClassMap = new Array();	
	       var rowSpanCols = [1,2,3,4,5,6,7,8,15,16];
	       var rowSpanLvl2Map = new Array();
	       var rowSpanLvl2Cols = [9,10,11];
	</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);">
	<tcmis:form action="/catalogaddqcresults.do" onsubmit="return submitFrameOnlyOnce();">
		<div id="errorMessagesAreaBody" style="display: none;">
			<html:errors /> 
			${tcmISError} 
			<c:forEach items="${tcmISErrors}" varStatus="status">
		  		${catalogRequest}<br />
			</c:forEach>
		</div>
		<script type="text/javascript">
			<c:choose>
				<c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISErrors }">showErrorMessage = false;</c:when>
				<c:otherwise>showErrorMessage = true;</c:otherwise>
			</c:choose>   
		</script>
	
	
		<div class="interface" id="resultsPage">
			<div class="backGroundContent">
				<!--Give the div name that holds the grid the same name as your viewbean or dynabean you want for updates-->
				<div id="CatalogAddQcInputBean" style="width: 100%; height: 600px;" style="display: none;"></div>

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
						    <c:set var="displayUpdateLink" value='N'/>
							var jsonMainData = new Array();
							var jsonMainData = {
								rows:[
								<c:forEach var="catalogRequest" items="${resultsCollection}" varStatus="status">
									<c:forEach var="line" items="${catalogRequest.catalogAddItemData}" varStatus="lineStatus">
										<c:set var="lineCount" value='${lineCount+1}'/>
										<c:set var="requestType" value="${(catalogRequest.startingView == 6 || catalogRequest.startingView == 7 || catalogRequest.startingView == 9)?'Material':'Part'}"/>

                                        <c:set var="lineUpdate" value='${catalogRequest.rowPermission}'/>
                                        <c:set var="tmpAssignedTo" value='${catalogRequest.assignedTo}'/>
                                        <c:if test="${catalogRequest.vendorAssignmentStatus == 'Assigned' || catalogRequest.vendorAssignmentStatus == 'Problem'}">
                                            <c:set var="lineUpdate" value='N'/>
                                            <c:set var="tmpAssignedTo" value='Assigned to Vendor'/>
                                        </c:if>
                                        <c:if test="${catalogRequest.vendorAssignmentStatus == 'Multiple Locale Problem'}">
                                        	<c:set var="lineUpdate" value='N'/>
                                        	<c:choose>
                                        	<c:when test="${empty catalogRequest.assignedTo}">
                                        	<c:set var="tmpAssignedTo" value="Vendor Reported as Problem"/>
                                        	</c:when>
                                        	<c:otherwise>
                                        	<c:set var="tmpAssignedTo" value="${catalogRequest.assignedToFullName}; Vendor Reported as Problem"/>
                                        	</c:otherwise>
                                        	</c:choose>
                                        </c:if>
                                        <%-- if one row is able to update the display Update link --%>
                                        <c:if test="${lineUpdate == 'Y' && displayUpdateLink == 'N'}">
                                            <c:set var="displayUpdateLink" value='Y'/>
                                        </c:if>
                                        <c:set var="lineDisplayStatus" value='${catalogRequest.requestDisplayStatus}'/>
                                        <%-- no longer need this status
                                        <c:if test="${catalogRequest.vendorAssignmentStatus == 'Problem'}">
                                            <c:set var="lineDisplayStatus" value='Vendor Reported as Problem'/>
                                        </c:if>
                                        --%>
                                        <c:if test="${catalogRequest.vendorAssignmentStatus == 'Closed'}">
                                            <%-- the reason for this logic is because SDS Indexing and Item Creation can run in parallel --%>
                                            <c:choose>
                                                <c:when test="${param.status == 'All Statuses' && (catalogRequest.requestDisplayStatus == 'Pending SDS Indexing' || catalogRequest.requestDisplayStatus == 'Pending Item Creation')}">
                                                    <c:if test="${catalogRequest.sdsIndexingClosed == 'N' || catalogRequest.itemCreationClosed == 'N'}">
                                                        <c:set var="lineUpdate" value='N'/>
                                                        <c:set var="tmpAssignedTo" value='Assigned to Vendor'/>
                                                    </c:if>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:set var="lineDisplayStatus" value='${catalogRequest.requestDisplayStatus} (was Assigned to Vendor)'/>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>

                                        <c:if test="${lineCount > 1}">,</c:if>
										{id:${lineCount},
										 data:['${lineUpdate}',
										  '${catalogRequest.requestId}',
										  '${requestType}',
										  '${catalogRequest.companyName}',
										  '${catalogRequest.catalogDesc}',
										  '<tcmis:jsReplace value="${catalogRequest.facilityName}" processMultiLines="true"/>',
										  '<tcmis:jsReplace value="${catalogRequest.fullName}" processMultiLines="true"/>',
										  '',
										  '${tmpAssignedTo}',
										  '<tcmis:jsReplace value="${catalogRequest.requestStatusDesc}" processMultiLines="true"/>',
										  '${lineDisplayStatus}',
										  '${line.itemId}',
										  '<tcmis:jsReplace value="${line.materialDesc}" processMultiLines="true"/>',
										  '<tcmis:jsReplace value="${line.partSize} ${line.sizeUnit} ${line.pkgStyle}" processMultiLines="true"/>',
										  '<tcmis:jsReplace value="${line.manufacturer}" processMultiLines="true"/>',
										  '<tcmis:jsReplace value="${line.comments}" processMultiLines="true"/>',
										  '<fmt:formatDate value="${catalogRequest.submitDate}" pattern="dd MMM yyyy HH:mm a"/>',
										  '<fmt:formatDate value="${catalogRequest.qcDate}" pattern="dd MMM yyyy HH:mm a"/>',
										  '${line.companyId}',
                                          '${catalogRequest.qcStatus}',       
                                          '${line.lineItem}',
                                          '${catalogRequest.requestDisplayStatus}',
                                          '${catalogRequest.vendorAssignmentStatus}',
                                          '${tmpAssignedTo}',
                                          '${catalogRequest.startingView}',
                                          '${catalogRequest.sdsIndexingClosed}',
                                          '${catalogRequest.itemCreationClosed}',
                                          '${line.itemVerified}'
                                          ]}
									 </c:forEach>
								 </c:forEach>
								]};
								<c:choose>
                                    <c:when test="${displayUpdateLink == 'Y'}">
                                        showUpdateLinks = true;
                                    </c:when>
                                    <c:otherwise>
                                        showUpdateLinks = false;
                                    </c:otherwise>
                                </c:choose>
						</script>
					</c:otherwise>				
				</c:choose>
				<!-- Search results end -->

				<%-- determining rowspan --%>
				<c:set var="lineCount" value='0' />
				<script language="JavaScript" type="text/javascript">
					<c:forEach var="catalogRequest" items="${resultsCollection}" varStatus="status">
						<c:set var="rowspan" value="${catalogRequest.lineCount}" />
						<c:forEach var="line" items="${catalogRequest.catalogAddItemData}" varStatus="lineStatus">
							<c:choose>
								<c:when test="${lineStatus.first}">
									rowSpanMap[${lineCount}]= ${rowspan};
									<c:set var="prevLineItem" value="${line.lineItem}"/>
									<c:set var="prevLineIndex" value="${lineCount}"/>
									rowSpanLvl2Map[${lineCount}] = 1;
								</c:when>
								<c:otherwise>
									rowSpanMap[${lineCount}]= 0;
									<c:choose>
										<c:when test="${prevLineItem == line.lineItem}">
											rowSpanLvl2Map[${lineCount}] = 0;
											rowSpanLvl2Map[${prevLineIndex}]++;
										</c:when>
										<c:otherwise>
											<c:set var="prevLineItem" value="${line.lineItem}"/>
											<c:set var="prevLineIndex" value="${lineCount}"/>
											rowSpanLvl2Map[${lineCount}]=1;
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
							rowSpanClassMap[${lineCount}]= ${status.count % 2}; // lineCount == ${lineCount}, status.count == ${status.count}
							<c:set var="lineCount" value='${lineCount+1}'/>
						</c:forEach>
					</c:forEach>
				</script>

				<!-- Hidden element start -->
				<div id="hiddenElements" style="display: none;">
					<%--This is the minimum height of the result section you want to display--%> 
					<input name="minHeight" id="minHeight" type="hidden" value="100" />
					<input name="searchedStatus" id="searchedStatus" type="hidden" value="${param.status}" />
                    <input name="alternateDb" id="alternateDb" type="hidden" value="${param.alternateDb}" />
                    <tcmis:setHiddenFields beanName="inputBean"/>
				</div>
			
			</div>
		<!-- close of backGroundContent -->
		</div>
		<!-- interface End -->
	
	</tcmis:form>
</body>	
</html:html>
