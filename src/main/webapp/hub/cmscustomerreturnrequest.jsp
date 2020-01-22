<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

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
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preferred font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss overflow="notHidden"/>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles which key press events are disabled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<%-- This handles the menu style and what happens to the right click on the whole page --%>
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %> 

<%-- For Calendar support --%>
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
<script type="text/javascript" src="/js/hub/cmscustomerreturnrequest.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<title>
	<fmt:message key="cmscustomerreturnrequest.title"/>
</title>

<script language="JavaScript" type="text/javascript">
	//add all the javascript messages here, this for internationalization of client side javascript messages
	var messagesData = {
		alert:"<fmt:message key="label.alert"/>",
		and:"<fmt:message key="label.and"/>",
		all:"<fmt:message key="label.all"/>",
		showlegend:"<fmt:message key="label.showlegend"/>",
		errors:"<fmt:message key="label.errors"/>",
		validvalues:"<fmt:message key="label.validvalues"/>",
		submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
		originalShippedQtyError:"<fmt:message key="error.qtygreater.originalshippedqty"/>",
		returnReason:"<fmt:message key="label.return"/> "+" <fmt:message key="label.reason"/>",
		noRowSelected:'<fmt:message key="error.norowselected"/>',
		xxNonNegativeIntegerError:"<fmt:message key="label.xxnonnegativeinteger"/>",
		requestedQuantity:"<fmt:message key="label.requestedquantity"/>",
		rowX:"<fmt:message key="label.rowx"/>",
		xCannotBeMoreThanY:"<fmt:message key="label.cannotbemore"/>",
		availableQty:"<fmt:message key="label.availableqty"/>",
		invalidInputs:"<fmt:message key="label.invalidinputs"/>",
		xIsRequired:"<fmt:message key="errors.required"/>",
		noticeTitle:"<fmt:message key="label.noticewindowtitle"/>",
		xIsInvalidEmail:"<fmt:message key="errors.email"/>",
		xMustBeNumberOnly:"<fmt:message key="label.xmustbenumberonly"/>",
		phoneNumber:"<fmt:message key="label.phonenumber"/>"
	};
	
	<c:choose>
		<c:when test="${customerReturnRequestResultBean.rmaStatus eq 'Approved'}">
			<c:set var="gridEditPermission" value="N"/>
			<c:set var="inputDisabledAttVal" value="disabled"/>
		</c:when>
		<c:otherwise>
			<c:set var="gridEditPermission" value="Y"/>
			<c:set var="inputDisabledAttVal" value=""/>
		</c:otherwise>
	</c:choose>
	
	var returnReasonConfig = [
		{
			columnId:"permission"
		},
		{
			columnId:"returnReasonId",
			columnName:'<fmt:message key="label.return"/> <fmt:message key="label.reason"/>',
			type:"hcoro",
			width:10
		}
	];
	
	var returnReasonGridConfig = {
		divName: 'returnReasonBean',			<%--  the div id for the grid. This is the also the variable name used to pass data back in updates --%>
		beanData: 'returnReasonJson',			<%--  the data variable name for jsonparse, as in grid.parse( beanData ,"json" ) --%>
		beanGrid: 'returnReasonBeanGrid',		<%--  variable to put the grid object in for later use --%>
		config: 'returnReasonConfig',			<%--  the column config var name, as in var columnConfig = { [ columnId:..,columnName... --%>
		onRowSelect: selectReturnReasonRow,
		submitDefault: true
	};
	
	var returnReasonId = new Array(
		<c:forEach var="vvReasonBeanCollection" items="${vvReasonBeanCollection}" varStatus="status">
			<c:if test="${status.index > 0}">,</c:if>
			{
				text:'<fmt:message key="${vvReasonBeanCollection.reasonLabel}"/>',
				value:'<tcmis:jsReplace value="${vvReasonBeanCollection.returnReasonId}"/>'
			}
		</c:forEach>
	);
	
	var returnReasonJson = {
		rows : [
			<c:forEach var="reasonBean" items="${reasonsBeanCollection}" varStatus="status">
				<c:if test="${status.index > 0}">,</c:if>
				{
					id : ${status.count},
					data : [
						'${gridEditPermission}',
						'${reasonBean.returnReasonId}'
					]
				}
			</c:forEach>
		]
	};
	
	var returnReceiptConfig = [
		{
			columnId:"permission"
		},
		{
			columnId:"receiptId",
			columnName:'<fmt:message key="label.receiptid"/>',
			width:8,
			align:'center'
		},
		{
			columnId:"lotStatus",
			columnName:'<fmt:message key="label.lotstatus"/>',
			width:8,
			align:'center'
		},
		{
			columnId:"mfgLot",
			columnName:'<fmt:message key="label.mfglot"/>',
			width:8,
			align:'center'
		},
		{
			columnId:"expireDate",
			columnName:'<fmt:message key="label.expiredate"/>',
			width:10,
			align:'center'
		},
		{
			columnId:"itemId",
			columnName:'<fmt:message key="label.item"/>',
			width:8,
			align:'center'
		},
		{
			columnId:"catPartNo",
			columnName:'<fmt:message key="label.catpartno"/>',
			width:10,
			align:'center'
		},
		{
			columnId:"totalShipped",
			columnName:'<fmt:message key="label.totalquantity"/>',
			attachHeader:'<fmt:message key="label.shipped"/>',
			width:8,
			align:'center'
		},
		{
			columnId:"totalReturned",
			columnName:'#cspan',
			attachHeader:'<fmt:message key="label.returned"/>',
			width:10,
			align:'center'
		},
		{
			columnId:"totalPendingReturn",
			columnName:'#cspan',
			attachHeader:'<fmt:message key="label.pendingreturn"/>',
			width:10,
			align:'center'
		},
		{
			columnId:"totalAvailable",
			columnName:'#cspan',
			attachHeader:'<fmt:message key="label.availabletorequest"/>',
			width:10,
			align:'center'
		},
		{
			columnId:"quantity",
			columnName:'#cspan',
			attachHeader:'<fmt:message key="label.requested"/>',
			width:10,
			align:'center'
		},
		{
			columnId:"viewIssues",
			columnName:'<fmt:message key="graphs.label.issues"/>',
			width:9,
			align:'center'
		}
	];
	
	var returnReceiptGridConfig = {
		divName: 'returnReceiptBean',			<%--  the div id for the grid. This is the also the variable name used to pass data back in updates --%>
		beanData: 'returnReceiptJson',			<%--  the data variable name for jsonparse, as in grid.parse( beanData ,"json" ) --%>
		beanGrid: 'returnReceiptBeanGrid',		<%--  variable to put the grid object in for later use --%>
		config: 'returnReceiptConfig',			<%--  the column config var name, as in var columnConfig = { [ columnId:..,columnName... --%>
		onRowSelect: selectReturnReceiptRow,
		submitDefault: true
	};
	
	var returnIssueConfig = [
		{
			columnId:"permission"
		},
		{
			columnId:"issueId",
			columnName:'<fmt:message key="label.issueid"/>',
			width:8,
			align:'center'
		},
		{
			columnId:"totalShipped",
			columnName:'<fmt:message key="label.quantity"/>',
			attachHeader:'<fmt:message key="label.shipped"/>',
			width:10,
			align:'center'
		},
		{
			columnId:"totalReturned",
			columnName:'#cspan',
			attachHeader:'<fmt:message key="label.returned"/>',
			width:10,
			align:'center'
		},
		{
			columnId:"totalPendingReturn",
			columnName:'#cspan',
			attachHeader:'<fmt:message key="label.pendingreturn"/>',
			width:10,
			align:'center'
		},
		{
			columnId:"totalAvailable",
			columnName:'#cspan',
			attachHeader:'<fmt:message key="label.availabletorequest"/>',
			width:10,
			align:'center'
		},
		{
			columnId:"quantity",
			columnName:'#cspan',
			attachHeader:'<fmt:message key="label.requested"/>',
			width:10,
			align:'center',
			type:'hed',
			size:10,
			onChange:calculateTotalReturnRequested
		},
		{
			columnId:"note",
			columnName:'<fmt:message key="label.notes"/>',
			width:20,
			align:'center'
		},
		{
			columnId:"returnStatus",
			columnName:'<fmt:message key="label.returnstatus"/>',
			width:8,
			align:'center'
		},
		{
			columnId:"lastRequestedQuantity"
		},
		{
			columnId:"issueReturnApprovedQuantity"
		},
		{
			columnId:"receiptId"
		},
		{
			columnId:"lotStatus"
		},
		{
			columnId:"mfgLot"
		},
		{
			columnId:"expireDate"
		},
		{
			columnId:"itemId"
		},
		{
			columnId:"catPartNo"
		},
		{
			columnId:"returnIssue"
		}
	];
	
	var returnIssueGridConfig = {
		divName: 'returnIssueBean',			<%--  the div id for the grid. This is the also the variable name used to pass data back in updates --%>
		beanData: 'returnIssueJson',			<%--  the data variable name for jsonparse, as in grid.parse( beanData ,"json" ) --%>
		beanGrid: 'returnIssueBeanGrid',		<%--  variable to put the grid object in for later use --%>
		config: 'returnIssueConfig',			<%--  the column config var name, as in var columnConfig = { [ columnId:..,columnName... --%>
		onRowSelect: selectReturnIssueViewRow,
		submitDefault: true
	};
	
	var returnReceiptJson = {
		rows : []
	};
	var returnIssueJsonColl = {};
	var nextIndex;
	var curReceiptTotalShipped = 0;
	var curReceiptTotalReturned = 0;
	var curReceiptTotalPendingReturn = 0;
	var curReceiptTotalAvailable = 0;
	var curReceiptTotalRequested = 0;
	<c:forEach var="issue" items="${mrIssueReceiptDetailViewBeanCollection}" varStatus="status">
		if (returnIssueJsonColl["${issue.receiptId}"] == null) {
			returnIssueJsonColl["${issue.receiptId}"] = {
				rows : []
			};
		}

		nextIndex = returnIssueJsonColl["${issue.receiptId}"].rows.length;
		
		returnIssueJsonColl["${issue.receiptId}"].rows[nextIndex] = {
			id : nextIndex + 1,
			data : [
				//if there's no available, line should never be changed
				<c:choose>
					<c:when test="${issue.totalAvailable gt 0}">
						'${gridEditPermission}',
					</c:when>
					<c:otherwise>
						'N',
					</c:otherwise>
				</c:choose>
				'${issue.issueId}',
				'${issue.totalShipped}',
				'${issue.totalReturned}',
				'${issue.totalPendingReturn}',
				'${issue.totalAvailable}',
				'${issue.issueReturnApprovedQuantity}',
				'<tcmis:jsReplace value="${issue.note}" processMultiLines="true"/>',
				'<tcmis:jsReplace value="${issue.returnStatus}"/>',
				<c:choose>
					<c:when test="${empty issue.issueReturnApprovedQuantity or issue.issueReturnApprovedQuantity eq 0}">
						''
					</c:when>
					<c:otherwise>
						'${issue.issueReturnApprovedQuantity}'
					</c:otherwise>
				</c:choose>,
				'${issue.issueReturnApprovedQuantity}',
				'${issue.receiptId}',
				'<tcmis:jsReplace value="${issue.lotStatus}" processMultiLines="true"/>',
				'<tcmis:jsReplace value="${issue.mfgLot}" processMultiLines="true"/>',
				'<fmt:formatDate value="${issue.expireDate}" pattern="${dateFormatPattern}"/>',
				'${issue.itemId}',
				'<tcmis:jsReplace value="${issue.catPartNo}" processMultiLines="true"/>',
				'<tcmis:jsReplace value="${issue.returnIssue}"/>'
			]
		};

		<c:set var="curReceiptKey" value="${issue.prNumber}-${issue.lineItem}-${issue.receiptId}"/>
		<c:set var="nextIssue" value="${mrIssueReceiptDetailViewBeanCollection[status.index + 1]}"/>
		<c:set var="nextReceiptKey" value="${nextIssue.prNumber}-${nextIssue.lineItem}-${nextIssue.receiptId}"/>
		
		<c:if test="${issue.totalShipped gt 0}">
			curReceiptTotalShipped += ${issue.totalShipped};
		</c:if>
		curReceiptTotalPendingReturn += ${issue.totalPendingReturn};
		curReceiptTotalReturned += ${issue.totalReturned};
		curReceiptTotalRequested += ${(empty issue.issueReturnApprovedQuantity) ? 0 : issue.issueReturnApprovedQuantity};
		curReceiptTotalAvailable += ${issue.totalAvailable};
		
		<c:if test="${status.last or curReceiptKey ne nextReceiptKey}">
			returnReceiptJson.rows[returnReceiptJson.rows.length] = {
				id : returnReceiptJson.rows.length + 1,
				data : [
					'Y',
					'${issue.receiptId}',
					'<tcmis:jsReplace value="${issue.lotStatus}" processMultiLines="true"/>',
					'<tcmis:jsReplace value="${issue.mfgLot}" processMultiLines="true"/>',
					'<fmt:formatDate value="${issue.expireDate}" pattern="${dateFormatPattern}"/>',
					'${issue.itemId}',
					'<tcmis:jsReplace value="${issue.catPartNo}" processMultiLines="true"/>',
					curReceiptTotalShipped,
					curReceiptTotalReturned,
					curReceiptTotalPendingReturn,
					curReceiptTotalAvailable,
					curReceiptTotalRequested,
					'<input name="viewReceiptIssuesBtn' + nextIndex + '" id="viewReceiptIssuesBtn' + nextIndex + '" type="button"'
					+ 'value="<fmt:message key="label.view"/>" onclick="viewReceiptIssues(${issue.receiptId})" class="inputBtns"'
					+ 'onmouseover="this.className=\'inputBtns inputBtnsOver\'" onmouseout="this.className=\'inputBtns\'"/>'
				]
			};
		</c:if>

		<c:if test="${curReceiptKey ne nextReceiptKey}">
			curReceiptTotalShipped = 0;
			curReceiptTotalReturned = 0;
			curReceiptTotalPendingReturn = 0;
			curReceiptTotalAvailable = 0;
			curReceiptTotalRequested = 0;
		</c:if>
	</c:forEach>
	
	/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
	<c:choose>
		<c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISErrors && empty tcmISError && empty confirmSuccess}">
			showNoticeMessage = false;
		</c:when>
		<c:otherwise>
			showNoticeMessage = true;
		</c:otherwise>
	</c:choose>
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid()" onresize="resizeResultsIncludeGrids();">
	<tcmis:form action="/cmscustomerreturnrequest.do" onsubmit="return submitOnlyOnce();">
		<%--NEw -Transit Page Starts --%>
		<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
			<br/><br/><br/>
			<fmt:message key="label.pleasewait"/>
			<br/><br/><br/>
			<img src="/images/rel_interstitial_loading.gif" align="middle"/>
		</div>
		<!-- Transit Page Ends -->
		
		<div class="interface" id="mainPage">
			<!-- Error Messages Begins -->
			<div id="noticeMessagesAreaBody" style="display:none;">
				<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
					<html:errors/><br/>
				</c:if>
				<c:if test="${not empty tcmISError}">
					${tcmISError}<br/>
				</c:if>
				<c:forEach items="${tcmISErrors}" varStatus="status">
					${status.current}<br/>
				</c:forEach>
				<c:if test="${not empty confirmSuccess}">
					<fmt:message key="msg.proceduresuccessful"/>
				</c:if>
			</div>
			<!-- Error Messages Ends -->
			
			<!-- Result Frame Begins -->
			<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
				<div id="resultGridDiv">
					<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<div class="roundcont contentContainer">
									<div class="roundright">
										<div class="roundtop">
											<div class="roundtopright">
												<img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
											</div>
										</div>
										<div class="roundContent">
											<div class="boxhead">
												<c:if test="${gridEditPermission eq 'Y' or inputDisabledAttVal eq ''}">
													<span id="approveButton">
														<a href="javascript:confirmCustomerReturnRequest();">
															<fmt:message key="label.confirm"/>
														</a>
													</span>
												</c:if>
											</div>
											<div class="dataContent">
												<table width="100%" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
													<tr>
														<td colspan="4" class="optionTitleBoldLeft">
															<fieldset>
																<legend>
																	<fmt:message key="label.rma"/>:&nbsp;${customerReturnRequestResultBean.customerRmaId}
																</legend>
																<table border="0">
																	<tr>
																		<td nowrap="nowrap" class="optionTitleBoldRight">
																			<fmt:message key="label.operatingentity"/>:
																		</td>
																		<td nowrap="nowrap" class="optionTitleLeft">
																			${customerReturnRequestResultBean.opsEntityName}
																		</td>
																		<td nowrap="nowrap" class="optionTitleBoldRight">
																			<fmt:message key="label.csr"/>:
																		</td>
																		<td nowrap="nowrap" class="optionTitleLeft">
																			${customerReturnRequestResultBean.csrName}
																		</td>
																		<td nowrap="nowrap" class="optionTitleBoldRight">
																			<fmt:message key="label.status"/>:
																		</td>
																		<td nowrap="nowrap" class="optionTitleLeft">
																			<c:choose>
																				<c:when test="${empty customerReturnRequestResultBean.rmaStatus}">
																					<fmt:message key="label.draft"/>
																				</c:when>
																				<c:otherwise>
																					${customerReturnRequestResultBean.rmaStatus}
																				</c:otherwise>
																			</c:choose>
																		</td>
																	</tr>
																	<tr>
																		<td class="optionTitleBoldRight">
																			<fmt:message key="label.company"/>:
																		</td>
																		<td nowrap="nowrap" class="optionTitleLeft">
																			${customerReturnRequestResultBean.customerName}
																		</td>
																		<td nowrap="nowrap" class="optionTitleBoldRight">
																			<fmt:message key="label.mrline"/>:
																		</td>
																		<td nowrap="nowrap" class="optionTitleLeft">
																			${customerReturnRequestResultBean.prNumber} - ${customerReturnRequestResultBean.lineItem}
																		</td>
																		<td nowrap="nowrap" class="optionTitleBoldRight">
																			<fmt:message key="label.requeststartdate"/>:
																		</td>
																		<td nowrap="nowrap" class="optionTitleLeft">
																			<fmt:formatDate value="${customerReturnRequestResultBean.requestStartDate}" 
																				pattern="${dateFormatPattern}"/>
																		</td>
																	</tr>
																	<tr>
																		<td nowrap="nowrap" class="optionTitleBoldRight">
																			<fmt:message key="label.facility"/>:
																		</td>
																		<td class="optionTitleLeft">
																			${customerReturnRequestResultBean.facilityId}
																		</td>
																		<td nowrap="nowrap" class="optionTitleBoldRight">
																			<fmt:message key="label.partnumber"/>:
																		</td>
																		<td nowrap="nowrap" class="optionTitleLeft">
																			${customerReturnRequestResultBean.facPartNo}
																		</td>
																		<td nowrap="nowrap" class="optionTitleBoldRight">
																			<fmt:message key="label.type"/>:
																		</td>
																		<td nowrap="nowrap" class="optionTitleLeft">
																			<c:choose>
																				<c:when test="${customerReturnRequestResultBean.returnType eq 'CO'}">
																					<fmt:message key="label.customerowned"/>
																				</c:when>
																				<c:otherwise>
																					<fmt:message key="label.forcredit"/>
																				</c:otherwise>
																			</c:choose>
																		</td>
																	</tr>
																	<tr>
																		<td valign="top" nowrap="nowrap" class="optionTitleBoldRight">
																			<fmt:message key="label.shipto"/>:
																		</td>
																		<td valign="top" class="optionTitleLeft">
																			<tcmis:replace value="${customerReturnRequestResultBean.shipToAddress}"
																				from="\\n" to="</br>"/>
																		</td>
																		<td valign="top" nowrap="nowrap" class="optionTitleBoldRight">
																			<fmt:message key="label.description"/>:
																		</td>
																		<td valign="top" class="optionTitleLeft">
																			<tcmis:replace value="${customerReturnRequestResultBean.partDescription}"
																				from="\\n" to="</br>"/>
																		</td>
																	</tr>
																	<tr>
																		<td nowrap="nowrap" class="optionTitleBoldRight">
																			<fmt:message key="label.contact"/>:
																		</td>
																		<td class="optionTitleLeft">
																			${customerReturnRequestResultBean.requestorName}
																		</td>
																		<td nowrap="nowrap" class="optionTitleBoldRight">
																			<fmt:message key="label.orderquantity"/>:
																		</td>
																		<td class="optionTitleLeft">
																			${customerReturnRequestResultBean.orderQuantity}
																		</td>
																	</tr>
																	<tr>
																		<td nowrap="nowrap" class="optionTitleBoldRight">
																			<fmt:message key="label.email"/>:
																		</td>
																		<td class="optionTitleLeft">
																			${customerReturnRequestResultBean.requestorEmail}
																		</td>
																		<td class="optionTitleBoldRight">
																			<fmt:message key="label.orderunitprice"/>:
																		</td>
																		<td class="optionTitleLeft">
																			${customerReturnRequestResultBean.unitPrice}&nbsp;
																			${customerReturnRequestResultBean.currencyId}
																		</td>
																	</tr>
																	<tr>
																		<td nowrap="nowrap" class="optionTitleBoldRight">
																			<fmt:message key="label.phone"/>:
																		</td>
																		<td class="optionTitleLeft">
																			${customerReturnRequestResultBean.requestorPhone}
																		</td>
																		<td class="optionTitleBoldRight">
																			<fmt:message key="label.quantityshipped"/>:
																		</td>
																		<td class="optionTitleLeft">
																			${customerReturnRequestResultBean.quantityShipped}
																		</td>
																	</tr>
																</table>
															</fieldset>
														</td>
													</tr>
													<!--  Return Detail Start-->
													<tr>
														<td colspan="4" class="optionTitleBoldLeft">
															<fieldset id="detailFieldSet">
																<legend>
																	<fmt:message key="label.returndetail"/>
																</legend>
																<table border="0">
																	<tr>
																		<td width="5%" nowrap="nowrap" class="optionTitleBoldRight">
																			<fmt:message key="label.returnrequestor"/>:
																		</td>
																		<td width="5%" class="optionTitleLeft">
																			<input type="text" name="requestorName" id="requestorName"
																				value='<tcmis:inputTextReplace value="${customerReturnRequestResultBean.returnRequestorName}" />'
																				class="inputBox" size="30" maxlength="50" ${inputDisabledAttVal}/>
																		</td>
																		<td width="5%" nowrap="nowrap" class="optionTitleBoldRight">
																			<fmt:message key="label.returnquantityrequested"/>:
																		</td>
																		<td class="optionTitleLeft">
																			<span id="returnQuantityRequestedSpan">${customerReturnRequestResultBean.quantityReturnRequested}</span>
																		</td>
																	</tr>
																	<tr>
																		<td width="5%" class="optionTitleBoldRight">
																			<fmt:message key="label.email"/>:
																		</td>
																		<td width="5%" class="optionTitleLeft">
																			<input type="text" name="requestorEmail" id="requestorEmail" onchange="returnRequestorEmailChanged();"
																				value="<tcmis:inputTextReplace value="${customerReturnRequestResultBean.returnRequestorEmail}" />"
																				class="inputBox" size="30" maxlength="50" ${inputDisabledAttVal}/>
																		</td>
																		<td width="5%" nowrap="nowrap" class="optionTitleBoldRight">
																			<fmt:message key="label.customerreturnrequest.reversedretentionquestion"/>
																		</td>
																		<td class="optionTitleBoldLeft">
																			<c:set var="returnMaterialY" value=""/>
																			<c:set var="returnMaterialN" value=""/>
																			<!-- Space after 'check' string is needed to prevent variables' values from being put next to each other -->
																			<c:choose>
																				<c:when test="${customerReturnRequestResultBean.returnMaterial == 'Y'}">
																					<c:set var="returnMaterialY" value="checked "/>
																				</c:when>
																				<c:when test="${customerReturnRequestResultBean.returnMaterial == 'N'}">
																					<c:set var="returnMaterialN" value="checked "/>
																				</c:when>
																				<c:otherwise>
																					<c:set var="returnMaterialY" value="checked "/>
																				</c:otherwise>
																			</c:choose>
																			<input type="radio" name="returnMaterial" id="returnMaterialRdBtnY" value="Y" ${returnMaterialY} ${inputDisabledAttVal}/>
																			<fmt:message key="label.yes" />&nbsp;
																			<input type="radio" name="returnMaterial" id="returnMaterialRdBtnN" value="N" ${returnMaterialN} ${inputDisabledAttVal}/>
																			<fmt:message key="label.no" />
																		</td>
																	</tr>
																	<tr>
																		<td width="5%" class="optionTitleBoldRight">
																			<fmt:message key="label.phone"/>:
																		</td>
																		<td width="5%" class="optionTitleLeft">
																			<input type="text" name="requestorPhone" id="requestorPhone" onchange="returnRequestorPhoneChanged();"
																			value='<tcmis:inputTextReplace value="${customerReturnRequestResultBean.returnRequestorPhone}" />'
																			class="inputBox" size="30" maxlength="20" ${inputDisabledAttVal}/>
																		</td>
																		<td width="5%" nowrap="nowrap" class="optionTitleBoldRight">
																			<fmt:message key="label.shipcorrectitem"/>
																		</td>
																		<td class="optionTitleBoldLeft">
																			<c:set var="correctItemShippedY" value=""/>
																			<c:set var="correctItemShippedN" value=""/>
																			<!-- Space after 'check' string is needed to prevent variables' values from being put next to each other -->
																			<c:choose>
																				<c:when test="${customerReturnRequestResultBean.correctItemShipped == 'Y'}">
																					<c:set var="correctItemShippedY" value="checked "/>
																				</c:when>
																				<c:when test="${customerReturnRequestResultBean.correctItemShipped == 'N'}">
																					<c:set var="correctItemShippedN" value="checked "/>
																				</c:when>
																				<c:otherwise>
																					<c:set var="correctItemShippedY" value="checked "/>
																				</c:otherwise>
																			</c:choose>
																			<input type="radio" name="correctItemShipped" id="correctItemShippedY" value="Y"
																				${correctItemShippedY} ${inputDisabledAttVal}/>
																			<fmt:message key="label.yes" />&nbsp;
																			<input type="radio" name="correctItemShipped" id="correctItemShippedN" value="N"
																				${correctItemShippedN} ${inputDisabledAttVal}/>
																			<fmt:message key="label.no" />
																		</td>
																	</tr>
																	<tr>
																		<td>&nbsp;</td>
																		<td class="optionTitleBoldLeft">
																			<c:if test="${gridEditPermission == 'Y'}">
																				<span id="reasonAddLine">
																					<a href="#" onclick="addReturnReasonLine(); ">
																						<fmt:message key="label.addline"/>
																					</a>
																				</span>&nbsp;
																				<span id="reasonRemoveLine" style="display: none">
																					<a href="#" onclick="removeReturnReasonLine();">
																						<fmt:message key="label.removeLine"/>
																					</a>
																				</span>
																			</c:if>
																		</td>
																		<td>&nbsp;</td>
																		<td>&nbsp;</td>
																	</tr>
																	<tr>
																		<td width="5%" class="optionTitleBoldRight">
																			<fmt:message key="label.return"/>&nbsp;<fmt:message key="label.reason"/>:
																			<span style='font-size:12.0pt;color:red'>*</span>
																		</td>
																		<td class="optionTitleBoldLeft" colspan="3">
																			<div id="returnReasonBean" style="width:250px;height:160px;" style="display: none;"></div>
																		</td>
																	</tr>
																	<tr>
																		<td width="5%" id="receiptLabelTd" class="optionTitleBoldRight">
																			<fmt:message key="graphs.label.receipts"/>:
																			<span style='font-size:12.0pt;color:red'>*</span>
																		</td>
																		<td class="optionTitleBoldLeft" colspan="3">
																			<div id="returnReceiptBean" style="width:300px;height:160px;" style="display: none;"></div>
																		</td>
																	</tr>
																	<tr>
																		<td class="optionTitleBoldRight">
																			<fmt:message key="label.returnnote"/>:
																		</td>
																		<td class="optionTitleLeft" colspan="3">
																			<textarea name="returnNotes" id="returnNotes" class="inputBox" cols="150" rows="3"
																			${inputDisabledAttVal}><tcmis:inputTextReplace value="${customerReturnRequestResultBean.returnNotes}"/></textarea>
																		</td>
																	</tr>
																</table>
															</fieldset>
														</td>
													</tr>
													<!--  Return Detail End -->
												</table>
											</div>
										</div>
										
										<div class="roundbottom">
											<div class="roundbottomright">
												<img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
											</div>
										</div>
									</div>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<!-- Result Frame -->
			
			<!-- Hidden element start -->
			<div id="hiddenElements" style="display: none;">
				<tcmis:setHiddenFields beanName="searchInput"/>
				<input name="startSearchTime" id="startSearchTime" type="hidden" value=""/>
				<input name="minHeight" id="minHeight" type="hidden" value="100"/>
				<input name="quantityShipped" id="quantityShipped"type="hidden" value="${customerReturnRequestResultBean.quantityShipped}"/>
				<input name="returnQuantityRequested" id="returnQuantityRequested" type="hidden" value="${customerReturnRequestResultBean.quantityReturnRequested}"/>
				<input name="wantReplacement" id="wantReplacement" type="hidden" value="${customerReturnRequestResultBean.wantReplacementMaterial}"/>
				<input name="csrName" id="csrName" type="hidden" value="${customerReturnRequestResultBean.csrName}"/>
				<input name="customerServiceRepId" id="customerServiceRepId" type="hidden"  value="${customerReturnRequestResultBean.customerServiceRepId}"/>
				<input name="catalogId" id="catalogId" type="hidden"  value="${customerReturnRequestResultBean.catalogId}"/>
				<input name="catalogCompanyId" id="catalogCompanyId" type="hidden" value="${customerReturnRequestResultBean.catalogCompanyId}"/>
				<input name="opsEntityId" id="opsEntityId" type="hidden" value="${customerReturnRequestResultBean.opsEntityId}"/>
				<input name="inventoryGroup" id="inventoryGroup" type="hidden" value="${customerReturnRequestResultBean.inventoryGroup}"/>
				<input name="replacementCatPartNo" id="replacementCatPartNo" type="hidden" value="${customerReturnRequestResultBean.replacementCatPartNo}"/>
			</div>
			<!-- Hidden elements end -->
			
			<div id="returnIssueSubmitBean" style="display: none;"></div>
		</div>
	</tcmis:form>
	
	<div id="noticeMessagesArea" class="errorMessages" style="display: none;overflow: auto;"></div>
	
	<div id="issueViewArea" style="display: none;overflow: auto">
		<table id="issueViewTable" width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<div class="roundcont contentContainer">
						<div class="roundright">
							<div class="roundtop">
								<div class="roundtopright">
									<img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
								</div>
							</div>
							<div class="roundContent">
								<div class="boxhead">
									<span>
										<a href="javascript:closeReceiptIssuesPopupWin();">
											<fmt:message key="tablet.done"/>
										</a>
									</span>
								</div>
								<div class="dataContent">
									<div id="returnIssueBean" style="width:100px;height:160px;" style="display: none;"></div>
								</div>
							</div>
							
							<div class="roundbottom">
								<div class="roundbottomright">
									<img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
								</div>
							</div>
						</div>
					</div>
				</td>
			</tr>
		</table>
	</div>
</body>
</html:html>