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
<title><fmt:message key="label.queueid"/> <c:out value="${param.qId}"/> <fmt:message key="label.problems"/></title>
<script language="JavaScript" type="text/javascript">
<!-- 
var messagesData = {alert:"<fmt:message key="label.alert"/>",
	and:"<fmt:message key="label.and"/>",
	recordFound:'<fmt:message key="label.recordFound"/>',
	searchDuration:'<fmt:message key="label.searchDuration"/>',
	minutes:'<fmt:message key="label.minutes"/>',
	seconds:'<fmt:message key="label.seconds"/>'
};


with ( milonic=new menuname("viewDocumentMenu") ) {
	 top="offset=2";
	 style=submenuStyle;
	 itemheight=17;
	 aI( "showmenu=documentMenu ;text=View Documents ;image=" );
}

with ( milonic=new menuname("documentMenu") ) {
	 top="offset=2";
	 style=submenuStyle;
	 itemheight=17;
	 aI( "text=Document ;url=javascript:viewDocument();" );
}

drawMenus();

var problemHistoryGridConfig = {
		divName:'problemHistoryBean',
		beanData:'jsonData',
		beanGrid:'beanGrid',
		config:'columnConfig',
		rowSpan:false,
		submitDefault:false
};

var columnConfig = [
    {columnId:"permission", submit:false},
    {columnId:"problemReportDate"},
    {columnId:"problemReportDate",columnName:'<fmt:message key="label.reportdate"/>', width:12, sorting:'int', hiddenSortingColumn:'problemReportDate'},
	{columnId:"problemReportedBy",columnName:'<fmt:message key="label.reportedby"/>'},
	{columnId:"problemType",columnName:'<fmt:message key="label.type"/>', type:"hed"},
	{columnId:"problemStatus",columnName:'<fmt:message key="label.statuswhenreported"/>'},
	{columnId:"comments",columnName:'<fmt:message key="label.comments"/>', tooltip:true, width:20, type:'txt'}
];

function returnToOpener() {
	parent.opener.stopShowingWait();
}

function resultOnload() {
	var now = new Date();
	$("startSearchTime").value = now.getTime();
	resultOnLoadWithGrid(problemHistoryGridConfig);
}

// -->
</script>
</head>
<body onload="resultOnload();" onunload="returnToOpener();">
	<tcmis:form action="/problemqueuereason.do" onsubmit="return submitFrameOnlyOnce();">
<div id="errorMessagesAreaBody" style="display:none;">
	${tcmISError}<br/>
	<c:forEach var="tcmisError" items="${tcmISErrors}">
		${tcmisError}<br/>
	</c:forEach>
	<c:if test="${param.maxData == fn:length(problemHistoryColl)}">
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
				<c:when test="${param.maxData == fn:length(problemHistoryColl)}">
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
			
			<div id="resultGridDiv">
			<!-- Search results start -->
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
									<div class="optionTitleBold">
										<fmt:message key="label.queueid"/> <c:out value="${param.qId}"/> <fmt:message key="label.problems"/>
									</div>
									<div id="problemHistoryBean" style="width:100%;height:300px;" style="display:none;"></div>
								</div>
						<%-- result count and time --%>
						<div id="footer" class="messageBar"></div>
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
<!-- Search results end -->
				<c:if test="${problemHistoryColl != null}">
				<script type="text/javascript">
					<c:set var="dataCount" value="${0}"/>
						<c:if test="${!empty problemHistoryColl}" >
							var jsonData = {
								rows:[
							<c:forEach var="bean" items="${problemHistoryColl}" varStatus="status">
								<c:if test="${status.index > 0}">,</c:if>
								{ id:${status.index +1},
									data:["Y",
									      "${bean.problemReportDate}",
									      '<fmt:formatDate value="${bean.problemReportDate}" pattern="${dateTimeFormatPattern}"/>',
									      '<tcmis:jsReplace value="${bean.problemReportedByName}" processMultiLines="false"/>',
									      '<tcmis:jsReplace value="${bean.problemType}" processMultiLines="false"/>',
									      '<tcmis:jsReplace value="${bean.problemStatus}" processMultiLines="false"/>',
									      '<tcmis:jsReplace value="${bean.comments}" processMultiLines="true"/>',
									      ]
								}
								<c:set var="dataCount" value="${dataCount+1}"/>
							</c:forEach>
								]
							};
					</c:if>
				</script>
				<!-- If the collection is empty say no data found -->
				<c:if test="${empty problemHistoryColl}">
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
					<input name="minHeight" id="minHeight" type="hidden" value="500"/>
					<input name="startSearchTime" id="startSearchTime" type="hidden"/>
				</div>
			</div>
			<!-- close of backgroundContent -->
		</div>
		<!-- close of interface -->
	</tcmis:form>
</body>
</html:html>