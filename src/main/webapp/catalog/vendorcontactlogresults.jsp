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
<script type="text/javascript" src="/js/catalog/vendorcontactlogresults.js"></script>
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

var logHistoryGridConfig = {
		divName:'contactLogHistoryBean',
		beanData:'logHistoryJsonData',
		beanGrid:'logHistoryGrid',
		config:'columnConfig',
		rowSpan:false,
		onRowSelect: updateDocumentMenu, 
		onRightClick:showViewDocumentMenu,
		submitDefault:true
};

var columnConfig = [
    {columnId:"permission", submit:false},
    {columnId:"personnelName"<c:if test="${param.calledFrom != 'viewMsds'}">, columnName:'<fmt:message key="label.enteredby"/>'</c:if>},
    {columnId:"contactDateSort"},
    {columnId:"contactDate", columnName:'<fmt:message key="label.date"/>', sorting:"int", hiddenSortingColumn:"contactDateSort", width:11},
    {columnId:"contactStatus", columnName:'<fmt:message key="label.status"/>', width:5},
    {columnId:"contactPurpose", columnName:'<fmt:message key="label.purpose"/>', tooltip:"Y", width:11},
    {columnId:"contactReference", columnName:'<fmt:message key="label.note"/>', tooltip:"Y", width:30},
    {columnId:"contactName"<c:if test="${param.calledFrom != 'viewMsds'}">, columnName:'<fmt:message key="label.name"/>'</c:if>},    
    {columnId:"documentNames"},
    {columnId:"documentUrls"}
];
// -->
</script>
</head>
<body onload="resultOnLoad();">
	<tcmis:form action="/vendorcontactlogresults.do" onsubmit="return submitFrameOnlyOnce();">
<div id="errorMessagesAreaBody" style="display:none;">
	${tcmISError}<br/>
	<c:forEach var="tcmisError" items="${tcmISErrors}">
		${tcmisError}<br/>
	</c:forEach>
	<c:if test="${param.maxData == fn:length(contactLogHistoryBeanCollection)}">
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
				<c:when test="${param.maxData == fn:length(contactLogHistoryBeanCollection)}">
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
				<div id="contactLogHistoryBean" style="width:100%;height:300px;" style="display:none;"></div>
				<c:if test="${contactLogHistoryBeanCollection != null}">
				<script type="text/javascript">
					<c:set var="dataCount" value="${0}"/>
						<c:if test="${!empty contactLogHistoryBeanCollection}" >
							var logHistoryJsonData = {
								rows:[
							<c:forEach var="bean" items="${contactLogHistoryBeanCollection}" varStatus="status">
								<c:if test="${status.index > 0}">,</c:if>
								{ id:${status.index +1},
									data:["Y",
									      '<tcmis:jsReplace value="${bean.personnelName}" processMultiLines="false"/>',
									      "${bean.contactDatetime}",
									      '<fmt:formatDate value="${bean.contactDatetime}" pattern="dd MMM yyyy HH:mm a"/>',
									      '<tcmis:jsReplace value="${bean.contactStatus}" processMultiLines="false"/>',
									      '<tcmis:jsReplace value="${bean.contactPurpose}" processMultiLines="false"/>',
									      '<tcmis:jsReplace value="${bean.contactReference}" processMultiLines="false"/>',
									      '<tcmis:jsReplace value="${bean.contactName}" processMultiLines="false"/>',
									      '<tcmis:jsReplace value="${bean.documentName}" processMultiLines="false"/>',
									      '<tcmis:jsReplace value="${bean.documentUrl}" processMultiLines="false"/>'
									      ]
								}
								<c:set var="dataCount" value="${dataCount+1}"/>
							</c:forEach>
								]
							};
					</c:if>
				</script>
				<!-- If the collection is empty say no data found -->
				<c:if test="${empty contactLogHistoryBeanCollection}">
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
				</div>
			</div>
			<!-- close of backgroundContent -->
		</div>
		<!-- close of interface -->
	</tcmis:form>
</body>
</html:html>