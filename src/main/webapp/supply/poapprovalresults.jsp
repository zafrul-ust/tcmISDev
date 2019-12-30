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
<script type="text/javascript" src="/js/supply/poapprovalresults.js"></script>
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
<script type="text/javascript" src="/js/common/customAlert.js"></script>
<script type="text/javascript" src="/js/common/customConfirm.js"></script>
<title><fmt:message key="poApproval"/></title>
<script language="JavaScript" type="text/javascript">
<!--
<fmt:message var="printpvr" key="label.printpvr"/>
var messagesData = {
			recordFound:"<fmt:message key="label.recordFound"/>",
    		searchDuration:"<fmt:message key="label.searchDuration"/>",
    		minutes:"<fmt:message key="label.minutes"/>",
    		seconds:"<fmt:message key="label.seconds"/>",
            total:"<fmt:message key="label.total"/>",
			pleaseSelect:"<fmt:message key="error.norowselected"/>",
			none:"<fmt:message key="label.none"/>",
			printpvr:"${printpvr}",
			purchaseorder:"<fmt:message key="label.purchaseorder"/>",
			overrideapproval:"<fmt:message key="label.overrideapproval"/>",
			poApprovalChain:"<fmt:message key="label.poapprovalchain"/>",
			ok:"<fmt:message key="label.ok"/>"
			
};

with(milonic=new menuname("rightClickMenu")){
	top = "offset=2";
	style = contextStyle;
	margin = 3;
	aI("text=<fmt:message key="label.showpoapprovalchain"/>;url=javascript:showApprovalChain();");
	aI("text=<fmt:message key="label.viewpo"/>;url=javascript:viewPo();");
}

with(milonic=new menuname("approvalMenu")){
	top = "offset=2";
	style = contextStyle;
	margin = 3;
	aI("text=<fmt:message key="label.approve"/>;url=javascript:approvePo();");
	aI("text=<fmt:message key="label.reject"/>;url=javascript:showRejectionDialog();");
	aI("text=<fmt:message key="label.showpoapprovalchain"/>;url=javascript:showApprovalChain();");
	aI("text=<fmt:message key="label.viewpo"/>;url=javascript:viewPo();");
}

drawMenus();

var columnConfig = [
{ columnId:"permission",
  columnName:'',
  submit:false
},
{ columnId:"grab",
  columnName:'<fmt:message key="label.grab"/><br><input type="checkbox" value="" onClick="return checkAll(\'grab\');" name="chkAllGrab" id="chkAllGrab">',
  type:"hchstatus",
  align:"center",
  width:4,
  onChange: grabPoApproval,
  submit:true
},
{ columnId:"poApprovalId", submit: true },
{ columnId:"radianPo",
  columnName:'<fmt:message key="label.po"/>',
  width:4,
  align:'center',
  submit:true
},
{ columnId:"supplierId" },
{ columnId:"supplier",
  permission:true,
  columnName:'<fmt:message key="label.supplier"/>',
  width:10,
  align:"left",
  tooltip:"Y"
},
{ columnId: "buyerId"
},
{ columnId:"buyerName",
  columnName:'<fmt:message key="label.buyer"/>',
  width:7,
  align:"left"
},
{ columnId:"dateCreated",
  columnName:'<fmt:message key="label.datecreated"/>',
  width:7,
  align:"left"
},
{ columnId:"poValueLocal",
  columnName:'<fmt:message key="label.povaluelocalcurrency"/>',
  align:"center",
  width:7
},
{ columnId:"hubId" },
{ columnId:"hub",
  columnName:'<fmt:message key="label.hub"/>',
  width:10,
  align:"left"
},
{ columnId:"inventoryGroup",
  columnName:'<fmt:message key="label.inventorygroup"/>',
  width:10,
  align:"left"
},
{ columnId:"poValueUSD",
  columnName:'<fmt:message key="label.povalueusd"/>',
  width:7,
  align:"center"
},
{ columnId:"status",
  columnName:'<fmt:message key="label.status"/>',
  width:7
},
{ columnId:"quantity",
  width:3,
  align:"left"
},
{ columnId:"approvalRejectionDate",
  columnName:'<fmt:message key="label.approvedrejecteddate"/>',
  width:8
},
{ columnId:"approvalRejectionComments",
  columnName:'<fmt:message key="label.approvedrejectedcomment"/>',
  width:15,
  tooltip:"Y"
},
{ columnId:"poApprovalNotes",
  columnName:'<fmt:message key="label.poapprovalnotes"/>',
  width:15,
  type:"hed",
  submit:true
}
];

var gridConfig = {
		divName:'poApprovalData',
		beanData:'jsonMainData',
		beanGrid:'beangrid',
		config:'columnConfig',
		rowSpan:false,
		noSmartRender: true,
		submitDefault:false,
		onRightClick:rightClickRow
};

var poApprovalCodes = {};
<c:if test="${not empty poApprovalCodes}">
poApprovalCodes = {
<c:set var="approvalStatus" value=""/>
<c:forEach var="code" items="${poApprovalCodes}" varStatus="status">
	<c:if test="${approvalStatus ne code.poApprovalStatus}">
		<c:set var="approvalStatus" value="${code.poApprovalStatus}"/>
		<c:if test="${status.index > 0}">
			<c:out value="],"/>
		</c:if>
		"<c:out value="${approvalStatus}"/>":[""
	</c:if>
	,"<c:out value="${code.poApprovalCode}"/>"
</c:forEach>
]};
</c:if>
// -->
</script>
</head>
<body onload="resultOnLoad();" onunload="closeAllchildren();">
	<tcmis:form action="/poapprovalresults.do" onsubmit="return submitFrameOnlyOnce();">
<div id="errorMessagesAreaBody" style="display:none;">
	${tcmISError}<br/>
	<c:forEach var="tcmisError" items="${tcmISErrors}">
		${tcmisError}<br/>
	</c:forEach>
	<c:if test="${param.maxData == fn:length(poApprovalColl)}">
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
				<c:when test="${param.maxData == fn:length(poApprovalColl)}">
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
				<div id="poApprovalData" style="width:100%;height:400px;" style="display:none;"></div>
				<c:if test="${poApprovalColl != null}">
				<script type="text/javascript">
					<!--
					<c:set var="dataCount" value="${0}"/>
						<c:if test="${!empty poApprovalColl}" >
							var jsonMainData = {
								rows:[<c:forEach var="bean" items="${poApprovalColl}" varStatus="status">
									<c:set var="dataCount" value="${dataCount + 1}"/>
									
									<fmt:formatDate var="confirmed" value="${bean.insertedOn}" pattern="${dateFormatPattern}"/>
									<fmt:formatDate var="actionDate" value="${bean.lastUpdatedOn}" pattern="${dateFormatPattern}"/>
									<c:set var="approvalPermission" value="Y"/>
									<c:if test="${bean.actionTaken ne 'Pending'}">
										<c:set var="approvalPermission" value="N"/>
									</c:if>
									{ 	id:${status.count},
										data:[	'${approvalPermission}',
										    '',
										    '${bean.poApprovalId}',
										    '${bean.radianPo}',
										    '${bean.supplierId}',
										    '<tcmis:jsReplace value="${bean.supplierName}" processMultiLines="false" />',
											'${bean.buyer}',
											'<tcmis:jsReplace value="${bean.buyerName}" processMultiLines="false" />',
											'${confirmed}',
											'${bean.oePoTotal}',
											'${bean.branchPlant}',
											'<tcmis:jsReplace value="${bean.hubName}" processMultiLines="false"/>',
											'<tcmis:jsReplace value="${bean.inventoryGroup}" processMultiLines="false"/>',
											'${bean.poValueUSD}',
											'<tcmis:jsReplace value="${bean.poApprovalStatus}" processMultiLines="false"/>',
											'${bean.quantity}',
											'${actionDate}',
											'<tcmis:jsReplace value="${bean.statusComment}" processMultiLines="true"/>',
											'<tcmis:jsReplace value="${bean.poApprovalNotes}" processMultiLines="true" />'
										]
									}<c:if test="${!status.last}">,</c:if>
								</c:forEach>
								]};
						</c:if>
					//-->
				</script>
				<!-- If the collection is empty say no data found -->
				<c:if test="${empty poApprovalColl}">
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
					<input type="hidden" name="uAction" id="uAction" value=""/>
					<input name="totalLines" id="totalLines" type="hidden" value="${dataCount}"/>
					<input name="minHeight" id="minHeight" type="hidden" value="100"/>
					<input name="opsEntityId" id="opsEntityId" type="hidden" value="<tcmis:jsReplace value="${param.opsEntityId}"/>"/>
					<input name="buyerId" id="buyerId" type="hidden" value="<tcmis:jsReplace value="${param.buyerId}"/>"/>
					<input name="branchPlant" id="branchPlant" type="hidden" value="<tcmis:jsReplace value="${param.branchPlant}"/>"/>
					<input name="approverId" id="approverId" type="hidden" value="<tcmis:jsReplace value="${param.approverId}"/>"/>
					<input name="inventoryGroup" id="inventoryGroup" type="hidden" value="<tcmis:jsReplace value="${param.inventoryGroup}"/>"/>
					<input name="createdFromDate" id="createdFromDate" type="hidden" value="<tcmis:jsReplace value="${param.createdFromDate}"/>"/>
					<input name="createdToDate" id="createdToDate" type="hidden" value="<tcmis:jsReplace value="${param.createdToDate}"/>"/>
					<input name="supplier" id="supplier" type="hidden" value="<tcmis:jsReplace value="${param.supplier}"/>"/>
					<input name="searchWhat" id="searchWhat" type="hidden" value="<tcmis:jsReplace value="${param.searchWhat}"/>"/>
					<input name="searchType" id="searchType" type="hidden" value="<tcmis:jsReplace value="${param.searchType}"/>"/>
					<input name="searchText" id="searchText" type="hidden" value="<tcmis:jsReplace value="${param.searchText}"/>"/>
					<input name="status" id="status" type="hidden" value="<tcmis:jsReplace value="${param.status}"/>"/>
				</div>
			</div>
			<!-- close of backgroundContent -->
		</div>
		<!-- close of interface -->
	</tcmis:form>
</body>
</html:html>