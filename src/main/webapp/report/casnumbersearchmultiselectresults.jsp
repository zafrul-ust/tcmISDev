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

		<%-- Right Mouse Click (RMC) Menu support  --%>
		<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
		<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
		<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
		<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
		<%@ include file="/common/rightclickmenudata.jsp" %>
	
		<%-- Form popup Calendar support --%>
		<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
		<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
		<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
	
		<%-- Grid support --%>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>    
		<%--Uncomment below if you are providing header menu to switch columns on/off--%>
		<%--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>--%>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
		
	<script type="text/javascript" src="/js/report/casnumbersearchmultiselect.js"></script>

	<title>
		<fmt:message key="label.material"/>
	</title>

	<script language="JavaScript" type="text/javascript">
		<!--
		//add all the javascript messages here, this for internationalization of client side javascript messages
		var messagesData = new Array();
		messagesData = {
			alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			recordFound:"<fmt:message key="label.recordFound"/>",
			searchDuration:"<fmt:message key="label.searchDuration"/>",
			minutes:"<fmt:message key="label.minutes"/>",
			seconds:"<fmt:message key="label.seconds"/>",
			validvalues:"<fmt:message key="label.validvalues"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
			itemInteger:"<fmt:message key="error.item.integer"/>",
			selectedRowMsg:"<fmt:message key="label.selectedcasnumber"/>"
		};

		var config = [
			{columnId:"permission"},
			{columnId:"select", columnName:'<fmt:message key="label.select"/>', width:4, type:'hchstatus',align:'center'},
			{columnId:"casNum", columnName:'<fmt:message key="label.casnum"/>', width:8, align:"center"},
			{columnId:"ecNum", columnName:'<fmt:message key="label.ecnumber"/>', width:8, align:"center"},
			{columnId:"preferredName", columnName:'<fmt:message key="label.chemicalname"/>', width:35, tooltip:true}
			];

		<%-- Define the grid options--%>
		var gridConfig = {
			divName: 'beanData',	<%--  the div id for the grid. This is the also the variable name used to pass data back in updates --%>
			beanData: 'jsonData',	<%--  the data variable name for jsonparse, as in grid.parse( beanData ,"json" ) --%>
			beanGrid: 'mygrid',		<%--  variable to put the grid object in for later use --%>
			config: config,		<%--  the column config var name, as in var columnConfig = { [ columnId:..,columnName... --%>
			rowSpan: false, 		<%--  true: this page has rowSpan > 1 or not, disables smartrendering & sorting --%>
			noSmartRender:true,
			onAfterHaasRenderRow:checkPreSelect
			};

		<%-- Check for passed error message that will require an error inline popup --%>
		<c:choose>
			<c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISError && empty tcmISErrors }">
				  <c:choose>
					   <c:when test="${param.maxData == fn:length(chemSynonymViewBeanCollection)}">
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

		// -->
	</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);">
	<tcmis:form action="/casnumbersearchmultiselectresults.do" onsubmit="return submitFrameOnlyOnce();">
		<div class="interface" id="resultsPage">
			<div class="backGroundContent">
				<c:set var="dataCount" value='${0}'/>

				<c:if test="${!empty chemSynonymViewBeanCollection}" >
					<div id="beanData" style="width:100%;height:400px;"></div>
						<script type="text/javascript">
							<!--
							var jsonData = {
									rows:[<c:forEach var="bean" items="${chemSynonymViewBeanCollection}" varStatus="status">
										{ 	id:${status.index +1},
											data:[
												'Y',
												false,
												'<tcmis:jsReplace value="${bean.casNumber}" processMultiLines="true" />',
											      '<tcmis:jsReplace value="${bean.ecNumber}" processMultiLines="true" />',
												'<tcmis:jsReplace value="${bean.preferredName}" processMultiLines="true" />'
											]
										}<c:if test="${!status.last}">,</c:if> <c:set var="dataCount" value='${dataCount+1}'/>
									</c:forEach>]};
							//-->
						</script>
				</c:if>

				<c:if test="${empty chemSynonymViewBeanCollection}" >
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
						<tr>
							<td width="100%">
								<fmt:message key="main.nodatafound"/>
							</td>
						</tr>
					</table>
				</c:if>

				<div id="hiddenElements" style="display: none;">
					<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
					<input name="loginNeeded" id="loginNeeded" value="${param.loginNeeded}" type="hidden"/>
					<input name="maxData" id="maxData" type="hidden" value="${param.maxData}">
				</div>
			</div>
		</div>
	</tcmis:form>

<!-- You can build your error messages below. But we want to trigger the pop-up from the main page.
So this is just used to feed the pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
	<div id="errorMessagesAreaBody" style="display:none;">
  				<html:errors/>
  				${tcmISError}
  				<c:forEach var="tcmisError" items="${tcmISErrors}" >
					${tcmisError}<br/>
  				</c:forEach>    
  				<c:if test="${param.maxData == fn:length(chemSynonymViewBeanCollection)}">
			  <fmt:message key="label.maxdata"> 
			   <fmt:param value="${param.maxData}"/>
			  </fmt:message>
 			</c:if>  
	</div>

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/


showUpdateLinks = true;
//-->
</script>

</body>
</html:html>