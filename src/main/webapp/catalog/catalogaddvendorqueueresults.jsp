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
<script type="text/javascript" src="/js/catalog/catalogaddvendorqueueresults.js"></script>
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
<title><fmt:message key="catalogaddvendorqueue.title"/></title>

    <c:set var="update" value="N"/>
    <c:set var="vendorColumnPermission" value="N"/>
    <c:set var="showVendorColumn" value="N"/>
    <c:set var="assignedToColumnPermission" value="N"/>
    <c:set var="showTaskColumn" value="N"/>
    <c:set var="showRequestIdColumn" value="N"/>
    <c:set var="showAssigneeColumn" value="Y"/>
    <c:if test="${calledFrom == 'wescoApproval'}">
        <c:set var="vendorColumnPermission" value="Y"/>
        <c:set var="showTaskColumn" value="Y"/>
        <c:set var="showRequestIdColumn" value="Y"/>
        <c:set var="showVendorColumn" value="Y"/>
        <tcmis:catalogPermission indicator="true" userGroupId="catalogAddMSDS">
            <c:set var="update" value='Y'/>
        </tcmis:catalogPermission>
        <c:if test="${(inputBean.taskStatus eq 'Assigned' || inputBean.taskStatus eq 'Open'
        		|| inputBean.taskStatus eq 'Open,Assigned' || empty inputBean.taskStatus
        		|| inputBean.taskStatus eq 'Problem')
        		 && (inputBean.status == 'Pending SDS Sourcing' || inputBean.status == 'Pending SDS Indexing'
        		 || inputBean.status == 'Pending Item Creation')}">
        	<c:set var="assignedToColumnPermission" value="Y"/>
        </c:if>
    </c:if>
    <c:if test="${calledFrom == 'workQueueDetails'}">
        <c:set var="vendorColumnPermission" value="Y"/>
        <c:set var="showTaskColumn" value="Y"/>
        <c:set var="showVendorColumn" value="Y"/>
        <tcmis:catalogPermission indicator="true" userGroupId="catalogAddMSDS">
            <c:set var="update" value='Y'/>
        </tcmis:catalogPermission>
        
        <c:set var="multiLanguage" value="${param.vendorAssignmentStatus eq 'Multiple Locale Problem'}"/>
        <c:set var="showVendorColumn" value="${multiLanguage?'N':showVendorColumn}"/>
        <c:set var="showAssigneeColumn" value="${multiLanguage?'N':'Y'}"/>
    </c:if>
    <c:if test="${calledFrom == 'vendorQueuePage'}">
    	<c:set var="update" value='Y'/>
    	<c:set var="assignedToColumnPermission" value="Y"/>
    	<c:if test="${param.status ne 'Assigned' && param.status ne 'Open' 
    			&& param.status ne 'Pending QC' && param.status ne 'Open,Assigned'}">
    		<c:set var="assignedToColumnPermission" value="N"/>
    		<c:set var="update" value='N'/>
    	</c:if>
    	<c:if test="${param.status eq 'Approved'}">
        	<c:set var="update" value='Y'/>
        </c:if>
        <c:if test="${empty param.task}">
        	<c:set var="showTaskColumn" value="Y"/>
        </c:if>
    </c:if>

	<script type="text/javascript">
		<c:choose>
			<c:when test="${update == 'Y'}">showUpdateLinks = true;</c:when>
			<c:otherwise>showUpdateLinks = false;</c:otherwise>
		</c:choose>
	</script>

	
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
            pendingCompanyMsds:"<fmt:message key="label.pendingmsdscustomindexing"/>",    
            pendingTranslation:"<fmt:message key="label.pendingtranslation"/>",
            pendingSdsQc:"<fmt:message key="label.pendingmsdsqc"/>",    
            translation:"<fmt:message key="label.translation"/>",
            queueid:"<fmt:message key="label.queueid"/>" ,
            item:"<fmt:message key="label.item"/>",
            task:"<fmt:message key="label.task"/>"
            };

		var assignedTo = new  Array(
		    {value: '111', text: 'Inactive User'},
            {value: '', text: 'Unassigned'}<c:if test="${!empty catalogUsers}">,</c:if>
            <c:forEach var="catalogUser" items="${catalogUsers}" varStatus="status">
                {value: '${catalogUser.personnelId}', text: '${catalogUser.personnelName}'}<c:if test="${!status.last}">,</c:if>
            </c:forEach>
        );

        var supplier = new Array(
            {value: '', text: 'Select One'}<c:if test="${!empty catalogVendors}">,</c:if>
            <c:forEach var="bean" items="${catalogVendors}" varStatus="status">
                {
                    value: '${bean.supplier}',
                    text: '${bean.supplierName}'
                }
                <c:if test="${!status.last}">,</c:if>
            </c:forEach>
        );
		
		<%-- See webroot/dhtmlxgrid/codebase/dhtmlxcommon_haas.js for option explanations of config & gridconfig --%>
		var columnConfig = [ 
			{columnId:"permission"},  
			{columnId:"catalogAddRequestId"<c:if test="${showRequestIdColumn == 'Y'}">, columnName:'<fmt:message key="label.requestid"/>'</c:if>, submit:true},
			{columnId:"qId", columnName:'<fmt:message key="label.queueid"/>', width:6, align:'right', submit:true, sorting: "int"},
			{columnId:"colored"},
			{columnId:"slaDeadline"},
			{columnId:"timeRemaining"},
			{columnId:"dueIn", columnName:'<fmt:message key="label.duein"/>', width: 12, align:'center', hiddenSortingColumn:'timeRemaining', sorting:"int"},
			{columnId:"company"<c:if test="${showRequestIdColumn == 'Y'}">, columnName:'<fmt:message key="label.company"/>'</c:if>},
			{columnId:"facility"<c:if test="${showRequestIdColumn == 'Y'}">, columnName:'<fmt:message key="label.facility"/>'</c:if>},
			{columnId:"catalog"<c:if test="${showRequestIdColumn == 'Y'}">, columnName:'<fmt:message key="label.catalog"/>'</c:if>},
			{columnId:"grabbed", columnName:'<fmt:message key="label.grab"/><br><input type="checkbox" onclick="return checkAll(\'grabbed\');" name="chkAllGrab" id="chkAllGrab"/>', 
				type:'hchstatus', onChange:onChangeGrab, align:'center', width:6, submit:true},
			{columnId:"supplierPermission"},
			{columnId:"supplier"<c:if test="${showVendorColumn == 'Y'}">, columnName:'<fmt:message key="label.vendor"/>'</c:if>, type:'hcoro', align:'center', onChange:onChangeSupplier, tooltip:true, width:10, submit:true, permission:true},
			{columnId:"assignedToPermission"},
			{columnId:"assignedTo"<c:if test="${showAssigneeColumn == 'Y'}">, columnName:'<fmt:message key="label.assignedto"/>'</c:if>, type:'hcoro', onChange:onChangeAssignedTo, align:'center', tooltip:true, width:10, submit:true, permission:true},
			{columnId:"assignedToHidden"},
			{columnId:"task"<c:if test="${showTaskColumn == 'Y'}">, columnName:'<fmt:message key="label.task"/>'</c:if>, submit:true},
			{columnId:"status",columnName:'<fmt:message key="label.status"/>', width:11},
			{columnId:"materialDesc", columnName:'<fmt:message key="label.materialdesc"/>', tooltip:true, width:20},
			{columnId:"manufacturer", columnName:'<fmt:message key="label.manufacturer"/>', tooltip:true, width:15},
			{columnId:"localeCode"},
			{columnId:"localeDisplay", columnName:'<fmt:message key="label.language"/>',width:8},
			{columnId:"problemReportDate",columnName:'<fmt:message key="label.problem"/>', attachHeader:'<fmt:message key="label.reportdate"/>', width:12},
			{columnId:"problemReportedBy",columnName:'#cspan', attachHeader:'<fmt:message key="label.reportedby"/>'},
			{columnId:"problemType",columnName:'#cspan', attachHeader:'<fmt:message key="label.type"/>', submit:true},
			{columnId:"comments",columnName:"#cspan",attachHeader:'<fmt:message key="label.comments"/>', tooltip:true, width:20, submit:true},
			{columnId:"requestComments"<c:if test="${showVendorColumn == 'Y'}">, columnName:'<fmt:message key="label.requestcomments"/>', tooltip:true, width: 20</c:if>},
			{columnId:"insertDate",columnName:'<fmt:message key="label.datecreated"/>', width: 12},
			{columnId:"taskCompleteDate", columnName:'<fmt:message key="label.submitdate"/>', width:12},
			{columnId:"approvedDate", columnName:'<fmt:message key="label.approveddate"/>', width:12},
			{columnId:"approvedBy"},
			{columnId:"approvedByName", columnName:'<fmt:message key="label.approvedby"/>', width: 12},
			{columnId:"invoiceNumber", columnName:'<fmt:message key="label.invoicenumber"/>'},
            {columnId:"invoicedDate", columnName:'<fmt:message key="label.invoicedate"/>'},
            {columnId:"lineItem", submit:true},
            {columnId:"companyId", submit:true},
            {columnId:"originalSupplier", submit:true},
            {columnId:"problemChanged", submit:true}
        ];
		       
	       var gridConfig = {
		       divName:'CatalogAddVendorQueueInputBean', // the div id to contain the grid. ALSO the var name for the submitted data
		       beanData:'jsonMainData', // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		       beanGrid:'mygrid', // the grid's name, for later reference/usage
		       config:columnConfig, // the column config var name, as in var config = { [ columnId:..,columnName...
		       rowSpan: false, // true: this page has rowSpan > 1 or not, disables smartrendering & sorting
		       submitDefault: false, // the fields in grid are defaulted to be submitted or not.
		       noSmartRender: true, // Explicitly enable smartrender by setting this to false for rowspans
		       onRightClick:rightClickRow, // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
		       onRowSelect: selectRow
	       };
	</script>
</head>
<body onload="resultOnLoad();">
	<tcmis:form action="/catalogaddvendorqueueresults.do" onsubmit="return submitFrameOnlyOnce();">
<div id="errorMessagesAreaBody" style="display:none;">
	${tcmISError}<br/>
	<c:forEach var="tcmisError" items="${tcmISErrors}">
		${tcmisError}<br/>
	</c:forEach>
	<c:if test="${param.maxData == fn:length(resultsCollection)}">
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
				<c:when test="${param.maxData == fn:length(resultsCollection) && !empty resultsCollection}">
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
				<!--Give the div name that holds the grid the same name as your viewbean or dynabean you want for updates-->
				<div id="CatalogAddVendorQueueInputBean" style="width: 100%; height: 600px;" style="display: none;"></div>

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
							<fmt:message var="daysLabel" key="label.days"/>
							var jsonMainData = new Array();
							var jsonMainData = {
								rows:[
								<c:forEach var="queueRow" items="${resultsCollection}" varStatus="status">
										<c:set var="lineCount" value='${lineCount+1}'/>
										
										<c:set var="hasTaskStatus" value="${(not empty inputBean.status && calledFrom eq 'vendorQueuePage') || not empty inputBean.taskStatus}"/>
										<c:set var="userIsAssignee" value="${queueRow.assignedTo eq personnelBean.personnelId}"/>
										<c:set var="vendorMatchesRow" value="${calledFrom eq 'vendorQueuePage' || queueRow.supplier eq userSupplier}"/>
										<%--<c:set var="rowPermission" value="${(hasTaskStatus && queueRow.status ne 'Approved' && queueRow.status ne 'Closed' && (queueRow.status ne 'Pending QC' || not (vendorMatchesRow && userIsAssignee)))}"/>--%>
										<%--<c:set var="rowVendorPermission" value="${(hasTaskStatus && (queueRow.status eq 'Pending QC' || queueRow.status eq 'Open' || queueRow.status eq 'Assigned'))}"/>--%>
										<c:set var="rowVendorPermission" value="${false}"/>
										<c:set var="assigneePermission" value="${(hasTaskStatus && (queueRow.status eq 'Open' || queueRow.status eq 'Assigned' || queueRow.status eq 'Problem') && vendorMatchesRow)}"/>
										<c:set var="rowPermission" value="${assigneePermission || queueRow.status eq 'Pending Approval' || (queueRow.status eq 'Pending QC' && vendorMatchesRow && not userIsAssignee)}"/>

                                        <%-- user cannot change supplier unless work queue item is in Open or Assigned status --%>
                                        <%--<c:set var="rowPermission" value='${update}'/>
                                        <c:set var="rowVendorPermission" value='${vendorColumnPermission}'/>
                                        <c:if test="${queueRow.status ne 'Open' && queueRow.status ne 'Assigned'}">
                                            <c:set var="rowVendorPermission" value='N'/>
											<c:set var="assignedToColumnPermission" value='N'/>
                                            <%-- if called from Work Queue Details then user cannot make any change to row --%>
                                            <%--
                                            <c:if test="${calledFrom == 'workQueueDetails'}">
                                                <c:set var="rowPermission" value='N'/>
                                            </c:if>
                                        </c:if>
                                        
                                        <c:if test="${queueRow.status eq 'Pending QC' && queueRow.assignedTo eq personnelBean.personnelId}">
                                        	<c:set var="rowPermission" value="N"/>
                                        </c:if>
                                        <c:if test="${queueRow.status eq 'Problem' && queueRow.supplier ne userSupplier}">
                                        	<c:set var="assigneePermission" value="N"/>
                                        </c:if>
                                        <c:if test="${empty inputBean.taskStatus}">
                                        	<c:set var="rowPermission" value="N"/>
                                        	<c:set var="rowVendorPermission" value="N"/>
                                        	<c:set var="assigneePermission" value="N"/>
                                        </c:if>
                                        --%>
                                        <c:set var="module"><tcmis:module /></c:set>
                                        
                                        <c:choose>
                                        
                                        <c:when test="${queueRow.status eq 'Closed'}" >
                                    	   <c:set var="tmpAssignedTo" value=""/>
                                        </c:when>
                                        
                                        <c:when test="${vendorMatchesRow}">
                                        <c:set var="tmpAssignedTo" value="${queueRow.assignedTo}"/>
                                        </c:when>
                                        
                                        <c:when test="${module == 'catalog' || module == 'haas' }">
                                        <c:set var="tmpAssignedTo" value='${queueRow.assignedToName}'/>
                                        </c:when> 
                                        
                                        <c:otherwise>
                                        <c:set var="tmpAssignedTo" value='Assigned to Vendor'/>
                                        </c:otherwise>
                                        
                                       </c:choose>
                                       
                                        <c:set var="overdueLabel"><fmt:message key="label.overdue"/>!</c:set>
                                        <c:set var="overdueWarning" value="${queueRow.timeRemaining <= 0?overdueLabel:''}"/>

                                        <c:if test="${lineCount > 1}">,</c:if>
										{id:${lineCount},<c:if test='${queueRow.timeRemaining<0}'>'class':"grid_red",</c:if>
										 data:['${rowPermission?update:"N"}',
										  '${queueRow.catalogAddRequestId}',
										  '${queueRow.qId}',
										  'N',
										  '${queueRow.slaDeadline}',
										  '${queueRow.timeRemaining}',
										  '${queueRow.timeRemaining<0?queueRow.timeRemaining*-1:queueRow.timeRemaining} ${daysLabel} ${overdueWarning}',
										  '<tcmis:jsReplace value="${queueRow.companyName}"/>',
										  '<tcmis:jsReplace value="${queueRow.facilityName}"/>',
										  '<tcmis:jsReplace value="${queueRow.catalogDesc}"/>',
										  '${queueRow.grabbed}',
										  '${rowVendorPermission?vendorColumnPermission:"N"}',
										  '${queueRow.supplier}',
										  '${assigneePermission?assignedToColumnPermission:"N"}',
										  '${tmpAssignedTo}',
										  '${queueRow.assignedTo}',
										  '<tcmis:jsReplace value="${queueRow.task}" processMultiLines="false"/>',
										  '<tcmis:jsReplace value="${queueRow.status}" processMultiLines="false"/>',
										  '<tcmis:jsReplace value="${queueRow.materialDesc}" processMultiLines="false"/>',
										  '<tcmis:jsReplace value="${queueRow.manufacturer}" processMultiLines="false"/>',
										  '<tcmis:jsReplace value="${queueRow.localeCode}" processMultiLines="false"/>',
										  '<tcmis:jsReplace value="${queueRow.localeDisplay}" processMultiLines="false"/>',
										  '<fmt:formatDate value="${queueRow.problemReportDate}" pattern="dd MMM yyyy HH:mm a"/>',
										  '<tcmis:jsReplace value="${queueRow.problemReportedByName}" processMultiLines="false"/>',
										  '<tcmis:jsReplace value="${queueRow.problemType}" processMultiLines="false"/>',
										  '<tcmis:jsReplace value="${queueRow.comments}" processMultiLines="true"/>',
										  '<tcmis:jsReplace value="${queueRow.requestComments}" processMultiLines="true"/>',
										  '<fmt:formatDate value="${queueRow.insertDate}" pattern="dd MMM yyyy HH:mm a"/>',
										  '<fmt:formatDate value="${queueRow.taskCompleteDate}" pattern="dd MMM yyyy HH:mm a"/>',
										  '<fmt:formatDate value="${queueRow.approvedDate}" pattern="dd MMM yyyy HH:mm a"/>',
										  '${queueRow.approvedBy}',
										  '<tcmis:jsReplace value="${queueRow.approvedByName}" processMultiLines="false"/>',
										  '${queueRow.invoiceNumber}',
										  '<fmt:formatDate value="${queueRow.invoicedDate}" pattern="dd MMM yyyy HH:mm a"/>',
										  '${queueRow.lineItem}',
										  '${queueRow.companyId}',
										  '${queueRow.supplier}',
										  ''
                                          ]}
								</c:forEach>
								]};
						</script>
					</c:otherwise>				
				</c:choose>
				<!-- Search results end -->

				<!-- Hidden element start -->
				<div id="hiddenElements" style="display: none;">
					<%--This is the minimum height of the result section you want to display--%> 
					<input name="minHeight" id="minHeight" type="hidden" value="100" />
					<tcmis:jsReplace var="jsUserSupplier" value="${userSupplier}" processMultiLines="false"/>
					<input type="hidden" name="userSupplier" id="userSupplier" value="${jsUserSupplier}"/>
                    <tcmis:setHiddenFields beanName="inputBean"/>
				</div>
			
			</div>
			<!-- close of backgroundContent -->
		</div>
		<!-- close of interface -->
	</tcmis:form>
</body>
</html:html>

