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
<script type="text/javascript" src="/js/catalog/hmirsmgmtresults.js"></script>
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
<title><fmt:message key="hmirsMgmt"/></title>
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
			overrideapproval:"<fmt:message key="label.overrideapproval"/>"
			
};

with(milonic=new menuname("rightClickMenu")){
	top = "offset=2";
	style = contextStyle;
	margin = 3;
	aI("text=<fmt:message key="label.showapprovalchain"/>;url=javascript:showApprovalChain();");
	aI("text=<fmt:message key="label.viewpo"/>;url=javascript:viewPo();");
}

with(milonic=new menuname("approvalMenu")){
	top = "offset=2";
	style = contextStyle;
	margin = 3;
	aI("text=<fmt:message key="label.approve"/>;url=javascript:approvePo();");
	aI("text=<fmt:message key="label.rejct"/>;url=javascript:showRejectionDialog();");
	aI("text=<fmt:message key="label.showapprovalchain"/>;url=javascript:showApprovalChain();");
	aI("text=<fmt:message key="label.viewpo"/>;url=javascript:viewPo();");
}

drawMenus();

var columnConfig = [
{ columnId:"permission",
  submit:false
},
{ columnId:"currentItem",
  submit: true},
{ columnId:"itemId",
  columnName:'<fmt:message key="label.item"/>',
  attachHeader:'<fmt:message key="label.item"/>',
  readonly: true,
  align:"center",
  width:10,
  submit:true
},
{ columnId:"itemLookup",
  columnName:"#cspan",
  attachHeader:'<fmt:message key="label.lookup"/>',
  width:7,
  align:'center'
},
{ columnId:"nsn",
  columnName:'<fmt:message key="label.nsn"/>',
  width:8,
  align:"left",
  submit:true
},
{ columnId:"productDescription",
  columnName:'<fmt:message key="label.productdesc"/>',
  width:16,
  tooltip: "Y",
  align:"left"
},
{ columnId:"mfgName",
  columnName:'<fmt:message key="label.manufacturer"/>',
  attachHeader:'<fmt:message key="label.name"/>',
  tooltip: "Y",
  width:16,
  align:"left"
},
{ columnId:"mfgCageCode",
  columnName:'#cspan',
  attachHeader:'<fmt:message key="label.cagecode"/>',
  width:6,
  align:"left"
},
{ columnId:"saicSupplierName",
  columnName:'<fmt:message key="label.saicsupplier"/>',
  attachHeader:'<fmt:message key="label.name"/>',
  tooltip: "Y",
  align:"left",
  width:16
},
{ columnId:"saicSupplierCageCode",
  columnName:'#cspan',
  attachHeader:'<fmt:message key="label.cagecode"/>',
  width:6,
  align:"left"
},
{ columnId:"saicCode",
  columnName:'#cspan',
  attachHeader:'<fmt:message key="label.code"/>',
  width:6,
  align:"left"
},
{ columnId:"saicMsds",
  columnName:'#cspan',
  attachHeader:'<fmt:message key="label.msds"/>',
  width:7,
  align:"center"
},
{ columnId:"loadDate",
  columnName:'<fmt:message key="label.loaddate"/>',
  width:7,
  align:"left"
},
{ columnId:"lastUpdatedDate",
  columnName:'<fmt:message key="label.lastupdated"/>',
  width:7,
  align:"left"
},
{ columnId:"lastUpdatedByName",
  columnName:'<fmt:message key="label.lastupdatedby"/>',
  width:8,
  align:"left"
},
{ columnId:"hmirsRoadMapId",
  submit: true
}
];

var gridConfig = {
		divName:'hmirsRoadMapData',
		beanData:'jsonMainData',
		beanGrid:'beangrid',
		config:'columnConfig',
		rowSpan:false,
		noSmartRender: true,
		submitDefault:false,
		onRowSelect:selectRow
};

// -->
</script>
</head>
<body onload="resultOnLoad();" onunload="closeAllchildren();">
	<tcmis:form action="/hmirsmgmtresults.do" onsubmit="return submitFrameOnlyOnce();">
<div id="errorMessagesAreaBody" style="display:none;">
	${tcmISError}<br/>
	<c:forEach var="tcmisError" items="${tcmISErrors}">
		${tcmisError}<br/>
	</c:forEach>
	<c:if test="${param.maxData == fn:length(hmirsRoadMap)}">
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
				<c:when test="${param.maxData == fn:length(hmirsRoadMap)}">
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
				<div id="hmirsRoadMapData" style="width:100%;height:400px;" style="display:none;"></div>
				<c:if test="${hmirsRoadMap != null}">
				<script type="text/javascript">
					<!--
					<c:set var="dataCount" value="${0}"/>
						<c:if test="${!empty hmirsRoadMap}" >
							var jsonMainData = {
								rows:[<c:forEach var="bean" items="${hmirsRoadMap}" varStatus="status">
									<c:set var="dataCount" value="${dataCount + 1}"/>
									<c:set var="approvalPermission" value="N"/>
									<tcmis:permission indicator="true" userGroupId="UpdateHMIRS">
										<c:set var="approvalPermission" value="Y"/>
									</tcmis:permission>
									
									<fmt:formatDate var="loadDate" value="${bean.loadDate}" pattern="${dateFormatPattern}"/>
									<fmt:formatDate var="lastUpdatedDate" value="${bean.lastUpdatedDate}" pattern="${dateFormatPattern}"/>
									{ 	id:${status.count},
										data:[	'${approvalPermission}',
										    '${bean.itemId}',
										    '${bean.itemId}',
										    '<input type="button" id="itemLookup${staus.count}" name="itemLookup${status.count}" value="..." class="lookupBtn" onmouseover="this.className=\'lookupBtn lookupBtnOver\'" onmouseout="this.className=\'lookupBtn\'"  onclick="lookupItem(${bean.nsn});" style="display:${approvalPermission == "N"?"none":"inline"}">'
										    +'&nbsp;<input type="button" id="clearItem${status.count}" name="clearItem${status.count}" value="<fmt:message key="label.clear"/>" class="smallBtns" onmouseover="this.className=\'smallBtns smallBtnsOver\'" onmouseout="this.className=\'smallBtns\'" onclick="clearItem(${status.count})"/>',
										    '<c:out value="${bean.nsn}"/>',
										    '<c:out value="${bean.productDesc}"/>',
											'<c:out value="${bean.manufacturerName}"/>',
											'<c:out value="${bean.manufacturerCageCode}"/>',
											'<c:out value="${bean.saicSupplierName}"/>',
											'<c:out value="${bean.supplierCageCode}"/>',
											'<c:out value="${bean.saicSupplierCode}"/>',
											'<c:out value="${bean.saicMsdsId}"/>',
											'${loadDate}',
											'${lastUpdatedDate}',
											'${bean.lastUpdatedByName}',
											'${bean.hmirsRoadMapId}'
										]
									}<c:if test="${!status.last}">,</c:if>
								</c:forEach>
								]};
						</c:if>
					//-->
				</script>
				<!-- If the collection is empty say no data found -->
				<c:if test="${empty hmirsRoadMap}">
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
					<input name="loadDateFrom" id="loadDateFrom" type="hidden" value="<tcmis:jsReplace value="${param.loadDateFrom}"/>"/>
					<input name="loadDateTo" id="loadDateTo" type="hidden" value="<tcmis:jsReplace value="${param.loadDateTo}"/>"/>
					<input name="searchWhat" id="searchWhat" type="hidden" value="<tcmis:jsReplace value="${param.searchWhat}"/>"/>
					<input name="searchType" id="searchType" type="hidden" value="<tcmis:jsReplace value="${param.searchType}"/>"/>
					<input name="searchText" id="searchText" type="hidden" value="<tcmis:jsReplace value="${param.searchText}"/>"/>
					<input name="loadDateFrom" id="loadDateFrom" type="hidden" value="<tcmis:jsReplace value="${param.displayOnlyUnmappedNsns}"/>"/>
				</div>
			</div>
			<!-- close of backgroundContent -->
		</div>
		<!-- close of interface -->
	</tcmis:form>
</body>
</html:html>