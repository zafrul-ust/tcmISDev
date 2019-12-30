<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

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
		
		<tcmis:gridFontSizeCss /> <%-- Sets CSS based on the user's preffered font size and which application he is viewing --%>
	
		<script type="text/javascript" src="/js/common/formchek.js"></script>			<%-- Validation support --%>
		<script type="text/javascript" src="/js/common/commonutil.js"></script>
	
		<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script> <%-- This handles all the resizing of the page and frames --%>
		<script type="text/javascript" src="/js/common/disableKeys.js"></script>		<%-- This disables various key press events --%>

		<%-- Right Mouse Click (RMC) Menu support, keep in all pages  --%>
		<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
		<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
		<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
		<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
		<%@ include file="/common/rightclickmenudata.jsp" %>
		
		<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script> 
		<script type="text/javascript" src="/js/jquery/autocomplete.js"></script> 
		<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" /> 
		
		<%-- Grid support --%>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>    
		<%--Uncomment below if you are providing header menu to switch columns on/off--%>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>


		<%-- Page specific javascript --%>
		<script type="text/javascript" src="/js/common/admin/chargenumbermanagementresults.js"></script>

		<title><fmt:message key="chargenumbermanagement" /></title>

		<script language="JavaScript" type="text/javascript">
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
			with(milonic=new menuname("rightClickMenuRemove")){
				top="offset=2"
				style = contextStyle;
				margin=3
				aI("text=<fmt:message key="label.removeLine"/>;url=javascript:removeLine();");
			}

			<%-- Define the right click menus --%>
			with(milonic=new menuname("rightClickMenuUpdateRemove")){
				top="offset=2"
				style = contextStyle;
				margin=3
				aI("text=<fmt:message key="label.updateapprovers"/>;url=javascript:updateLineAppovers();");
				aI("text=<fmt:message key="label.removeLine"/>;url=javascript:removeLine();");
			}

			<%-- Initialize the RCMenus --%>
			drawMenus();
						

			var messagesData = new Array();
			messagesData = {alert:"<fmt:message key="label.alert"/>",
					and:"<fmt:message key="label.and"/>",
					validvalues:"<fmt:message key="label.validvalues"/>",
					submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
					recordFound:"<fmt:message key="label.recordFound"/>",
					searchDuration:"<fmt:message key="label.searchDuration"/>",
					minutes:"<fmt:message key="label.minutes"/>",
					seconds:"<fmt:message key="label.seconds"/>",
					missingchargenumber:"<fmt:message key="label.chargenumber"/>",
					approverName:"<fmt:message key="label.approvername"/>",
					nothingchanged:"<fmt:message key="label.nothing"/>",
					approverNameSame:"<fmt:message key="label.approvernamesame"/>",
					approvalLevelLimit:"<fmt:message key="label.approverlimit"/>",
					approvalLimitGreater:"<fmt:message key="label.approverlimitgreater"/>",
					chargeNumber1Label:"${param.chargeNumber1Label}",
					chargeNumber2Label:"${param.chargeNumber2Label}",
					chargeNumber3Label:"${param.chargeNumber3Label}",
					chargeNumber4Label:"${param.chargeNumber4Label}",
					updateError:"<fmt:message key="error.db.update"/>"
					};



			<%-- Define the columns for the result grid --%>
			var columnConfig = [ 
				{columnId:"permission" },			<%-- Update Permission for entire line --%>
				{columnId:"chargeNumber1NamePermission"},	
				{columnId:"chargeNumber2NamePermission"},
				{columnId:"chargeNumber3NamePermission"},
				{columnId:"chargeNumber4NamePermission"},	<%-- Update Permission for specific column in line --%>
				{columnId:"approvalLevel1Name1Permission"},
				{columnId:"approvalLevel1Name2Permission"},
				{columnId:"approvalLevel2Name1Permission"},
				{columnId:"approvalLevel2Name2Permission"},
				{columnId:"approvalLevel3Name1Permission"},
				{columnId:"approvalLevel3Name2Permission"},
				{columnId:"approvalLevel4Name1Permission"},
				{columnId:"approvalLevel4Name2Permission"},
				{columnId:"approvalLimitCurrencyIdPermission"},
				{columnId:"approvalLevel1LimitPermission"},
				{columnId:"approvalLevel2LimitPermission"},
				{columnId:"approvalLevel3LimitPermission"},
				{columnId:"approvalLevel4LimitPermission"},
				{columnId:"activePermission" }, <%-- use permission: true in column config that this permission applies too --%>

				<c:if test="${param.chargeNumber1Exists == 'Y'}">
					{columnId:"chargeNumber1Name", columnName:<c:choose><c:when test="${empty param.chargeLabel1}">'${param.chargeNumber1Label}'</c:when><c:otherwise>'${param.chargeLabel1}'</c:otherwise></c:choose>, type:'hed', onChange:callOnChangeFunction, align:'center', width:20},
					{columnId:"chargeNumber1"},
					{columnId:"chargeNumber1IdAlias"},
				</c:if>
				<c:if test="${param.chargeNumber2Exists== 'Y'}">
					{columnId:"chargeNumber2Name", columnName:<c:choose><c:when test="${empty param.chargeLabel2}">'${param.chargeNumber2Label}'</c:when><c:otherwise>'${param.chargeLabel2}'</c:otherwise></c:choose>, type:'hed', onChange:callOnChangeFunction, align:'center', width:20},
					{columnId:"chargeNumber2"},
					{columnId:"chargeNumber2IdAlias"},
				</c:if>
				<c:if test="${param.chargeNumber3Exists == 'Y'}">
					{columnId:"chargeNumber3Name", columnName:<c:choose><c:when test="${empty param.chargeLabel3}">'${param.chargeNumber3Label}'</c:when><c:otherwise>'${param.chargeLabel3}'</c:otherwise></c:choose>, type:'hed', onChange:callOnChangeFunction, align:'center', width:20},
					{columnId:"chargeNumber3"},
					{columnId:"chargeNumber3IdAlias"},
				</c:if>
				<c:if test="${param.chargeNumber4Exists == 'Y'}">
					{columnId:"chargeNumber4Name", columnName:<c:choose><c:when test="${empty param.chargeLabel4}">'${param.chargeNumber4Label}'</c:when><c:otherwise>'${param.chargeLabel4}'</c:otherwise></c:choose>, type:'hed', onChange:callOnChangeFunction, align:'center', width:20},
					{columnId:"chargeNumber4"},
					{columnId:"chargeNumber4IdAlias"},
				</c:if>

				{columnId:"active", columnName:'<fmt:message key="label.active"/>',  type:'hchstatus', onChange:activeChange, align:'center', width:5,permission:true},
					
				<c:choose>
					<c:when test="${param.noLevel1ChgAcctApprover == '2'}">
						<c:choose>
						   <c:when test="${param.approveByPrice == 'Y'}">
								{columnId:"approvalLevel1Limit", columnName:'<fmt:message key="label.level1"/>', attachHeader:'<fmt:message key="label.limit"/>', type:'hed', align:'center', width:10,size:50,permission:true},
								{columnId:"approvalLimitCurrencyId", columnName:'#cspan', attachHeader:'<fmt:message key="label.currency"/>', type:'hcoro', align:'center', width:10,permission:true},
								{columnId:"approvalLevel1Name1", columnName:'#cspan', attachHeader:'<fmt:message key="label.approvername1"/>', type:'hed', align:'center', width:10,size:50,submit: false,permission:true},
								{columnId:"approvalLevel1Name2",  columnName:'#cspan', attachHeader:'<fmt:message key="label.approvername2"/>', type:'hed', align:'center', width:10,size:50, submit: false,permission:true},
								{columnId:"approvalLevel1ApproverId1"},
								{columnId:"approvalLevel1ApproverId2"},
							</c:when>
							<c:otherwise>
								{columnId:"approvalLevel1Name1", columnName:'<fmt:message key="label.level1"/>', attachHeader:'<fmt:message key="label.approvername1"/>', type:'hed', align:'center', width:10,size:50,submit: false,permission:true},
								{columnId:"approvalLevel1Name2",  columnName:'#cspan', attachHeader:'<fmt:message key="label.approvername2"/>', type:'hed', align:'center', width:10,size:50, submit: false,permission:true},
								{columnId:"approvalLevel1ApproverId1"},
								{columnId:"approvalLevel1ApproverId2"},
							</c:otherwise>
						</c:choose> 
					</c:when>
					<c:when test="${param.noLevel1ChgAcctApprover == '1'}">
						<c:choose>
						   <c:when test="${param.approveByPrice == 'Y'}">
								{columnId:"approvalLevel1Limit", columnName:'<fmt:message key="label.level1"/>', attachHeader:'<fmt:message key="label.limit"/>', type:'hed', align:'center', width:10,size:50,permission:true},
								{columnId:"approvalLimitCurrencyId", columnName:'#cspan', attachHeader:'<fmt:message key="label.currency"/>', type:'hcoro', align:'center', width:10,permission:true},
								{columnId:"approvalLevel1Name1", columnName:'#cspan', attachHeader:'<fmt:message key="label.approvername1"/>', type:'hed', align:'center', width:10,size:50,submit: false,permission:true},
								{columnId:"approvalLevel1ApproverId1"},
							</c:when>
							<c:otherwise>
								{columnId:"approvalLevel1Name1", columnName:'<fmt:message key="label.level1"/>', attachHeader:'<fmt:message key="label.approvername1"/>', type:'hed', align:'center', width:10,size:50,submit: false,permission:true},
								{columnId:"approvalLevel1ApproverId1"},
							</c:otherwise>
						</c:choose> 
					</c:when>
					<c:otherwise>
						<c:choose>
						   <c:when test="${param.approveByPrice == 'Y'}">
								{columnId:"approvalLevel1Limit", columnName:'<fmt:message key="label.level1"/>', attachHeader:'<fmt:message key="label.limit"/>', type:'hed', align:'center', width:10,size:50,permission:true},
								{columnId:"approvalLimitCurrencyId", columnName:'#cspan', attachHeader:'<fmt:message key="label.currency"/>', type:'hcoro', align:'center', width:10,permission:true},
								{columnId:"approvalLevel1Name1", columnName:'#cspan', attachHeader:'<fmt:message key="label.approvername1"/>', type:'hed', align:'center', width:10,size:50,submit: false,permission:true},
								{columnId:"approvalLevel1ApproverId1"},
							</c:when>
							<c:otherwise>
								{columnId:"approvalLevel1Name1", columnName:'<fmt:message key="label.level1"/>', attachHeader:'<fmt:message key="label.approvername1"/>', type:'hed', align:'center', width:10,size:50,submit: false,permission:true},
								{columnId:"approvalLevel1ApproverId1"},
							</c:otherwise>
						</c:choose> 
					</c:otherwise>
				</c:choose>  
	
				<c:choose>
					<c:when test="${param.noLevel2ChgAcctApprover == '2'}">
						<c:choose>
						   <c:when test="${param.approveByPrice == 'Y'}">
								{columnId:"approvalLevel2Limit", columnName:'<fmt:message key="label.level2"/>', attachHeader:'<fmt:message key="label.limit"/>', type:'hed', align:'center', width:10,size:50,permission:true},
								{columnId:"approvalLevel2Name1", columnName:'#cspan', attachHeader:'<fmt:message key="label.approvername1"/>', type:'hed', align:'center', width:10,size:50,submit: false,permission:true},
								{columnId:"approvalLevel2Name2",  columnName:'#cspan', attachHeader:'<fmt:message key="label.approvername2"/>', type:'hed', align:'center', width:10,size:50, submit: false,permission:true},
								{columnId:"approvalLevel2ApproverId1"},
								{columnId:"approvalLevel2ApproverId2"},
							</c:when>
							<c:otherwise>
								{columnId:"approvalLevel2Name1", columnName:'<fmt:message key="label.level2"/>', attachHeader:'<fmt:message key="label.approvername1"/>', type:'hed', align:'center', width:10,size:50,submit: false,permission:true},
								{columnId:"approvalLevel2Name2",  columnName:'#cspan', attachHeader:'<fmt:message key="label.approvername2"/>', type:'hed', align:'center', width:10,size:50, submit: false,permission:true},
								{columnId:"approvalLevel2ApproverId1"},
								{columnId:"approvalLevel2ApproverId2"},
							</c:otherwise>
						</c:choose> 
					</c:when>
					<c:when test="${param.noLevel2ChgAcctApprover == '1'}">
						<c:choose>
						   <c:when test="${param.approveByPrice == 'Y'}">
								{columnId:"approvalLevel2Limit", columnName:'<fmt:message key="label.level2"/>', attachHeader:'<fmt:message key="label.limit"/>', type:'hed', align:'center', width:10,size:50,permission:true},
								{columnId:"approvalLevel2Name1", columnName:'#cspan', attachHeader:'<fmt:message key="label.approvername1"/>', type:'hed', align:'center', width:10,size:50,submit: false,permission:true},
								{columnId:"approvalLevel2ApproverId1"},
							</c:when>
							<c:otherwise>
								{columnId:"approvalLevel2Name1", columnName:'<fmt:message key="label.level2"/>', attachHeader:'<fmt:message key="label.approvername1"/>', type:'hed', align:'center', width:10,size:50,submit: false,permission:true},
								{columnId:"approvalLevel2ApproverId1"},
							</c:otherwise>
						</c:choose> 
					</c:when>
					<c:otherwise>
					</c:otherwise>
				</c:choose> 

				<c:choose>
					<c:when test="${param.noLevel3ChgAcctApprover == '2'}">
						<c:choose>
						   <c:when test="${param.approveByPrice == 'Y'}">
								{columnId:"approvalLevel3Limit", columnName:'<fmt:message key="label.level3"/>', attachHeader:'<fmt:message key="label.limit"/>', type:'hed', align:'center', width:10,size:50,permission:true},
								{columnId:"approvalLevel3Name1", columnName:'#cspan', attachHeader:'<fmt:message key="label.approvername1"/>', type:'hed', align:'center', width:10,size:50,submit: false,permission:true},
								{columnId:"approvalLevel3Name2",  columnName:'#cspan', attachHeader:'<fmt:message key="label.approvername2"/>', type:'hed', align:'center', width:10,size:50, submit: false,permission:true},
								{columnId:"approvalLevel3ApproverId1"},
								{columnId:"approvalLevel3ApproverId2"},
							</c:when>
							<c:otherwise>
								{columnId:"approvalLevel3Name1", columnName:'<fmt:message key="label.level3"/>', attachHeader:'<fmt:message key="label.approvername1"/>', type:'hed', align:'center', width:10,size:50,submit: false,permission:true},
								{columnId:"approvalLevel3Name2",  columnName:'#cspan', attachHeader:'<fmt:message key="label.approvername2"/>', type:'hed', align:'center', width:10,size:50, submit: false,permission:true},
								{columnId:"approvalLevel3ApproverId1"},
								{columnId:"approvalLevel3ApproverId2"},
							</c:otherwise>
						</c:choose> 
					</c:when>
					<c:when test="${param.noLevel3ChgAcctApprover == '1'}">
						<c:choose>
						   <c:when test="${param.approveByPrice == 'Y'}">
								{columnId:"approvalLevel3Limit", columnName:'<fmt:message key="label.level3"/>', attachHeader:'<fmt:message key="label.limit"/>', type:'hed', align:'center', width:10,size:50,permission:true},
								{columnId:"approvalLevel3Name1", columnName:'#cspan', attachHeader:'<fmt:message key="label.approvername1"/>', type:'hed', align:'center', width:10,size:50,submit: false,permission:true},
								{columnId:"approvalLevel3ApproverId1"},
							</c:when>
							<c:otherwise>
								{columnId:"approvalLevel3Name1", columnName:'<fmt:message key="label.level3"/>', attachHeader:'<fmt:message key="label.approvername1"/>', type:'hed', align:'center', width:10,size:50,submit: false,permission:true},
								{columnId:"approvalLevel3ApproverId1"},
							</c:otherwise>
						</c:choose> 
					</c:when>
					<c:otherwise>
					</c:otherwise>
				</c:choose>   

				<c:choose>
					<c:when test="${param.noLevel4ChgAcctApprover == '2'}">
						<c:choose>
						   <c:when test="${param.approveByPrice == 'Y'}">
								{columnId:"approvalLevel4Limit", columnName:'<fmt:message key="label.level4"/>', attachHeader:'<fmt:message key="label.limit"/>', type:'hed', align:'center', width:10,size:50,permission:true},
								{columnId:"approvalLevel4Name1", columnName:'#cspan', attachHeader:'<fmt:message key="label.approvername1"/>', type:'hed', align:'center', width:10,size:50,submit: false,permission:true},
								{columnId:"approvalLevel4Name2",  columnName:'#cspan', attachHeader:'<fmt:message key="label.approvername2"/>', type:'hed', align:'center', width:10,size:50, submit: false,permission:true},
								{columnId:"approvalLevel4ApproverId1"},
								{columnId:"approvalLevel4ApproverId2"},
							</c:when>
							<c:otherwise>
								{columnId:"approvalLevel4Name1", columnName:'<fmt:message key="label.level4"/>', attachHeader:'<fmt:message key="label.approvername1"/>', type:'hed', align:'center', width:10,size:50,submit: false,permission:true},
								{columnId:"approvalLevel4Name2",  columnName:'#cspan', attachHeader:'<fmt:message key="label.approvername2"/>', type:'hed', align:'center', width:10,size:50, submit: false,permission:true},
								{columnId:"approvalLevel4ApproverId1"},
								{columnId:"approvalLevel4ApproverId2"},
							</c:otherwise>
						</c:choose> 
					</c:when>
					<c:when test="${param.noLevel4ChgAcctApprover == '1'}">
						<c:choose>
						    <c:when test="${param.approveByPrice == 'Y'}">
								{columnId:"approvalLevel4Limit", columnName:'<fmt:message key="label.level4"/>', attachHeader:'<fmt:message key="label.limit"/>', type:'hed', align:'center', width:10,size:50,permission:true},
								{columnId:"approvalLevel4Name1", columnName:'#cspan', attachHeader:'<fmt:message key="label.approvername1"/>', type:'hed', align:'center', width:10,size:50,submit: false,permission:true},
								{columnId:"approvalLevel4ApproverId1"},
							</c:when>
							<c:otherwise>
								{columnId:"approvalLevel4Name1", columnName:'<fmt:message key="label.level4"/>', attachHeader:'<fmt:message key="label.approvername1"/>', type:'hed', align:'center', width:10,size:50,submit: false,permission:true},
								{columnId:"approvalLevel4ApproverId1"},
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
					</c:otherwise>
				</c:choose>
						
				{columnId:"oldActive"},
				{columnId:"newRow"},
				{columnId:"updated"},
				{columnId:"accountSysId"},
				{columnId:"chargeType"},
				{columnId:"chargeId"},
				{columnId:"chargeId1"},
				{columnId:"chargeId2"},
				{columnId:"chargeId3"},
				{columnId:"chargeId4"},
				{columnId:"acctSysApproverId"}
			]; 

			<%-- Define the grid options--%>
			var gridConfig = {
				divName: 'AcctSysApproverBean',	<%--  the div id for the grid. This is the also the variable name used to pass data back in updates --%>
				beanData: 'jsonMainData',	<%--  the data variable name for jsonparse, as in grid.parse( beanData ,"json" ) --%>
				beanGrid: 'mygrid',		<%--  variable to put the grid object in for later use --%>
				config: columnConfig,		<%--  the column config var name, as in var columnConfig = { [ columnId:..,columnName... --%>
				singleClickEdit: true,		<%--  Make TXT type field open edit on one click rahter than double click --%>
				submitDefault: true,
				rowSpan: false,
			    onRightClick: onRightClick,
				onRowSelect: onRowSelect	 		<%--  true: this page has rowSpan > 1 or not, disables smartrendering & sorting --%>
			};
				
			<%--  This is the value array for type:'hcoro' pulldown 'deliveryType' --%> 
			var active = new  Array(
				{text:'<fmt:message key="label.yes"/>',value:'Y'},
			        {text:'<fmt:message key="label.no"/>',value:'N'}
			);

	       var approvalLimitCurrencyId = new  Array(
	    		   <c:forEach var= "cur" items= "${vvCurrencyCollection}" varStatus= "currencyIdStatus" >
		    			<c:if test= "${currencyIdStatus.index != 0 }" >,</c:if>		
		    	        {text:'${cur.currencyDisplay}',value:'${cur.currencyId}'}
	    	       </c:forEach>
	               );
		// -->
		</script>

	</head>

	<body bgcolor="#ffffff" onload="myResultOnLoadWithGrid(gridConfig);">
		<tcmis:form action="/chargenumbermanagementresults.do" onsubmit="return submitFrameOnlyOnce();">
			<div id="errorMessagesAreaBody" style="display:none;">
    				<html:errors/>
				${tcmISError}
				<c:forEach var="error" items="${tcmISErrors}" varStatus="status">
					${error}<br/>
				</c:forEach>        
			</div>
		
			<div class="interface" id="resultsPage">
				<div class="backGroundContent">
					<%-- This is where the grid will be inserted.  The div name is ALSO the name of the form values that will be sent to the server on update --%>
					<div id="AcctSysApproverBean" style="width:100%;height:400px;" style="display: none;"></div>	
					
							<script type="text/javascript">
								<!--
											
								var jsonMainData = {
									rows:[
									<c:forEach var="row" items="${searchResults}" varStatus="status">
										<%-- Check row permission here --%>
										{ id:${status.count},
										  data:['N',
											'N',
											'N',
											'N',
											'N',
											'N',
											'N',
											'N',
											'N',
											'N',
											'N',
											'N',
											'N',
											'N',
											'N',
											'N',
											'N',
											'N',
											'Y',
											<c:if test="${param.chargeNumber1Exists == 'Y'}">
												'${row.chargeNumber1}',
												'${row.chargeNumber1}',
												'charge_number_1',
											</c:if>
											<c:if test="${param.chargeNumber2Exists== 'Y'}">
												'${row.chargeNumber2}',
												'${row.chargeNumber2}',
												<c:choose>
													<c:when test="${param.chargeNumber1Exists == 'Y'}">
														'charge_number_2',
													</c:when>
													<c:otherwise>
														'charge_number_1',
													</c:otherwise>
												</c:choose>
											</c:if>
											<c:if test="${param.chargeNumber3Exists == 'Y'}">
												'${row.chargeNumber3}',
												'${row.chargeNumber3}',
												<c:choose>
													<c:when test="${param.chargeNumber1Exists == 'Y' && param.chargeNumber2Exists == 'Y'}">
														'charge_number_3',
													</c:when>
													<c:when test="${param.chargeNumber1Exists == 'Y' || param.chargeNumber2Exists == 'Y'}">
														'charge_number_2',
													</c:when>
													<c:otherwise>
														'charge_number_1',
													</c:otherwise>
												</c:choose>
											</c:if>
											<c:if test="${param.chargeNumber4Exists == 'Y'}">
												'${row.chargeNumber4}',
												'${row.chargeNumber4}',
												<c:choose>
													<c:when test="${param.chargeNumber1Exists == 'Y' && param.chargeNumber2Exists == 'Y' && param.chargeNumber3Exists == 'Y'}">
														'charge_number_4',
													</c:when>
													<c:when test="${(param.chargeNumber1Exists == 'Y' && param.chargeNumber2Exists == 'Y') || (param.chargeNumber1Exists == 'Y' && param.chargeNumber3Exists == 'Y') || (param.chargeNumber2Exists == 'Y' && param.chargeNumber3Exists == 'Y')}">
														'charge_number_3',
													</c:when>
													<c:when test="${param.chargeNumber1Exists == 'Y' || param.chargeNumber2Exists == 'Y' || param.chargeNumber3Exists == 'Y'}">
														'charge_number_2',
													</c:when>
													<c:otherwise>
														'charge_number_1',
													</c:otherwise>
												</c:choose>
											</c:if>
												<c:choose>
													<c:when test="${row.active == 'Y'}">
														true,
													</c:when>
													<c:otherwise>
														false,
													</c:otherwise>
												</c:choose>
												
												<c:choose>
													<c:when test="${param.noLevel1ChgAcctApprover == '2'}">
														<c:if test="${param.approveByPrice == 'Y'}">
															'${row.approvalLevel1Limit}',
															'${row.approvalLimitCurrencyId}',
														</c:if>
															'${row.approvalLevel1Name1}',
															'${row.approvalLevel1Name2}',
															'${row.approvalLevel1ApproverId1}',
															'${row.approvalLevel1ApproverId2}',
													</c:when>
													<c:when test="${param.noLevel1ChgAcctApprover == '1'}">
															<c:if test="${param.approveByPrice == 'Y'}">
																'${row.approvalLevel1Limit}',
																'${row.approvalLimitCurrencyId}',	
															</c:if>
															'${row.approvalLevel1Name1}',
															'${row.approvalLevel1ApproverId1}',
													</c:when>
													<c:otherwise>
														<c:if test="${param.approveByPrice == 'Y'}">
															'${row.approvalLevel1Limit}',
															'${row.approvalLimitCurrencyId}',
														</c:if>
															'${row.approvalLevel1Name1}',
															'${row.approvalLevel1ApproverId1}',
													</c:otherwise>
											</c:choose>  
								
											<c:choose>
												<c:when test="${param.noLevel2ChgAcctApprover == '2'}">
													<c:if test="${param.approveByPrice == 'Y'}">
														'${row.approvalLevel2Limit}',
													</c:if>
														'${row.approvalLevel2Name1}',
														'${row.approvalLevel2Name2}',
														'${row.approvalLevel2ApproverId1}',
														'${row.approvalLevel2ApproverId2}',
												</c:when>
												<c:when test="${param.noLevel2ChgAcctApprover == '1'}">
												<c:if test="${param.approveByPrice == 'Y'}">
														'${row.approvalLevel2Limit}',
												</c:if>
														'${row.approvalLevel2Name1}',
														'${row.approvalLevel2ApproverId1}',
												</c:when>
												<c:otherwise>
												</c:otherwise>
											</c:choose> 

											<c:choose>
												<c:when test="${param.noLevel3ChgAcctApprover == '2'}">
													<c:if test="${param.approveByPrice == 'Y'}">
														'${row.approvalLevel3Limit}',
													</c:if>
														'${row.approvalLevel3Name1}',
														'${row.approvalLevel3Name2}',
														'${row.approvalLevel3ApproverId1}',
														'${row.approvalLevel3ApproverId2}',
												</c:when>
												<c:when test="${param.noLevel3ChgAcctApprover == '1'}">
													<c:if test="${param.approveByPrice == 'Y'}">
														'${row.approvalLevel3Limit}',
													</c:if>
														'${row.approvalLevel3Name1}',
														'${row.approvalLevel3ApproverId1}',
												</c:when>
												<c:otherwise>
												</c:otherwise>
											</c:choose>   

											<c:choose>
												<c:when test="${param.noLevel4ChgAcctApprover == '2'}">
													<c:if test="${param.approveByPrice == 'Y'}">
														'${row.approvalLevel4Limit}',
													</c:if>
														'${row.approvalLevel4Name1}',
														'${row.approvalLevel4Name2}',
														'${row.approvalLevel4ApproverId1}',
														'${row.approvalLevel4ApproverId2}',
												</c:when>
												<c:when test="${param.noLevel4ChgAcctApprover == '1'}">
													<c:if test="${param.approveByPrice == 'Y'}">
														'${row.approvalLevel4Limit}',
													</c:if>
														'${row.approvalLevel4Name1}',
														'${row.approvalLevel4ApproverId1}',
												</c:when>
												<c:otherwise>
												</c:otherwise>
											</c:choose>   
											
											<c:choose>
												<c:when test="${row.active == 'Y'}">
													true,
												</c:when>
												<c:otherwise>
													false,
												</c:otherwise>
											</c:choose>
											'N',
											false,
											'${param.accountSysId}',
											'${param.chargeType}',
											<c:choose>
												<c:when test="${param.chargeNumber1Exists == 'Y'}">
													'${param.chargeId1}',
												</c:when>
												<c:when test="${param.chargeNumber2Exists == 'Y'}">
													'${param.chargeId2}',
												</c:when>
												<c:when test="${param.chargeNumber3Exists == 'Y'}">
													'${param.chargeId3}',
												</c:when>
												<c:otherwise>
													'${param.chargeId4}',
												</c:otherwise>
											</c:choose>
											'${param.chargeId1}',
											'${param.chargeId2}',
											'${param.chargeId3}',
											'${param.chargeId4}',
											'${row.acctSysApproverId}'
										  ]}<c:if test="${!status.last}">,</c:if>
	 								</c:forEach>
									]};
								//-->  
							</script>
 
					<div id="hiddenElements" style="display: none;">    			
						<%-- Retrieve all the Search Criteria here for re-search after update--%>
					    <input name="minHeight" id="minHeight" type="hidden" value="100">
					    <input name="chargeNumber1Exists" id="chargeNumber1Exists" type="hidden" value="${param.chargeNumber1Exists}">
						<input name="chargeNumber2Exists" id="chargeNumber2Exists" type="hidden" value="${param.chargeNumber2Exists}">
						<input name="chargeNumber3Exists" id="chargeNumber3Exists" type="hidden" value="${param.chargeNumber3Exists}">
						<input name="chargeNumber4Exists" id="chargeNumber4Exists" type="hidden" value="${param.chargeNumber4Exists}">
						<input name="accountSysId" id="accountSysId" type="hidden" value="${param.accountSysId}">
						<input name="chargeType" id="chargeType" type="hidden" value="${param.chargeType}">
						<input name="chargeId" id="chargeId" Id="hidden" value="<c:choose><c:when test="${param.chargeNumber1Exists == 'Y'}">${param.chargeId1}</c:when><c:when test="${param.chargeNumber2Exists == 'Y'}">${param.chargeId2}</c:when><c:when test="${param.chargeNumber3Exists == 'Y'}">${param.chargeId3}</c:when><c:otherwise>${param.chargeId4}</c:otherwise></c:choose>">
						<input name="chargeId1" id="chargeId1" Id="hidden" value="${param.chargeId1}">
						<input name="chargeId2" id="chargeId2" Id="hidden" value="${param.chargeId2}">
						<input name="chargeId3" id="chargeId3" Id="hidden" value="${param.chargeId3}">
						<input name="chargeId4" id="chargeId4" Id="hidden" value="${param.chargeId4}">
						<input name="companyId" id="companyId" type="hidden" value="${param.companyId}">
						<input name="chargeNumber1Label" id="chargeNumber1Label" type="hidden" value="<c:choose><c:when test="${empty param.chargeLabel1}">${param.chargeNumber1Label}</c:when><c:otherwise>${param.chargeLabel1}</c:otherwise></c:choose>">
						<input name="chargeNumber2Label" id="chargeNumber2Label" type="hidden" value="<c:choose><c:when test="${empty param.chargeLabel2}">${param.chargeNumber2Label}</c:when><c:otherwise>${param.chargeLabel2}</c:otherwise></c:choose>">
						<input name="chargeNumber3Label" id="chargeNumber3Label" type="hidden" value="<c:choose><c:when test="${empty param.chargeLabel3}">${param.chargeNumber3Label}</c:when><c:otherwise>${param.chargeLabel3}</c:otherwise></c:choose>">
						<input name="chargeNumber4Label" id="chargeNumber4Label" type="hidden" value="<c:choose><c:when test="${empty param.chargeLabel4}">${param.chargeNumber4Label}</c:when><c:otherwise>${param.chargeLabel4}</c:otherwise></c:choose>">
						<input name="noLevel1ChgAcctApprover" id="noLevel1ChgAcctApprover" type="hidden" value="${param.noLevel1ChgAcctApprover}">
						<input name="noLevel2ChgAcctApprover" id="noLevel2ChgAcctApprover" type="hidden" value="${param.noLevel2ChgAcctApprover}">
						<input name="noLevel3ChgAcctApprover" id="noLevel3ChgAcctApprover" type="hidden" value="${param.noLevel3ChgAcctApprover}">
						<input name="noLevel4ChgAcctApprover" id="noLevel4ChgAcctApprover" type="hidden" value="${param.noLevel4ChgAcctApprover}">
						<input name="approveByPrice" id="approveByPrice" type="hidden" value="${param.approveByPrice}">
						<input name="totalLines" id="totalLines" value="<c:out value="${fn:length(searchResults)}"/>" type="hidden"/>
						<input name="uAction" id="uAction" value="" type="hidden">
						<input name="chargeNumber1" id="chargeNumber1" value="${param.chargeNumber1}" type="hidden">
						<input name="chargeNumber2" id="chargeNumber2" value="${param.chargeNumber2}" type="hidden"> 
						<input name="chargeNumber3" id="chargeNumber3" value="${param.chargeNumber3}" type="hidden">   
						<input name="chargeNumber4" id="chargeNumber4" value="${param.chargeNumber4}" type="hidden"> 
					</div>
				</div>
			</div>
				<tcmis:facilityPermission indicator="true" userGroupId="ChargeNumberSetup" facilityId="${param.facilityId}">
					<script type="text/javascript">
						<!--
								 showUpdateLinks = true;
						//-->
					</script>
				</tcmis:facilityPermission>

		</tcmis:form>
	</body>
</html:html>
