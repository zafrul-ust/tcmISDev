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
	
	<!-- Add any other javascript you need for the page here -->
	<script type="text/javascript" src="/js/client/opencustomer/ordermsds.js"></script>
	
	<!-- These are for the Grid-->
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>    
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
	
	<title>
		<fmt:message key="label.msds"/>
	</title>
	
	<script type="text/javascript">
		showUpdateLinks = false;
	</script>

	
	<script language="JavaScript" type="text/javascript">
	
		with(milonic=new menuname("rightClickMenu")){
			top = "offset=2";
			style = contextStyle;
			margin = 3;
			aI("text=<fmt:message key="label.displaymsds"/>;url=javascript:showMSDS();");
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
			itemInteger:"<fmt:message key="error.item.integer"/>"
			};	

		
		<%-- See webroot/dhtmlxgrid/codebase/dhtmlxcommon_haas.js for option explanations of config & gridconfig --%>
		var columnConfig = [ 
			{columnId:"customerName", columnName:'<fmt:message key="label.customer"/>',tooltip:true, width:20},
			{columnId:"prNumber", columnName:'<fmt:message key="label.purchaseorder"/>', tooltip:true, width:8},
			{columnId:"facPartNo"},
			{columnId:"itemId"},
			{columnId:"lineItem"},
			{columnId:"partDescription", columnName:'<fmt:message key="label.description"/>', tooltip:true, width:68}
			];
		       
	       var gridConfig = {
		       divName:'OrderMsdsInputBean', // the div id to contain the grid. ALSO the var name for the submitted data
		       beanData:'jsonMainData', // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		       beanGrid:'mygrid', // the grid's name, for later reference/usage
		       config:columnConfig, // the column config var name, as in var config = { [ columnId:..,columnName...
		       onRightClick:rightClickRow // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
	       }; 

	</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);">
	<tcmis:form action="/ordermsdsresults.do" onsubmit="return submitFrameOnlyOnce();">
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
				<div id="OrderMsdsInputBean" style="width: 100%; height: 600px;" style="display: none;"></div>

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
							var jsonMainData = new Array();
							var jsonMainData = {
								rows:[<c:forEach var="msds" items="${resultsCollection}" varStatus="status">
									{id:${status.count},
									 data:	['<tcmis:jsReplace value="${msds.customerName}" processMultiLines="true"/>',
										'${msds.prNumber}',
										'${msds.facPartNo}',
										'${msds.itemId}',
										'${msds.lineItem}',
										'<tcmis:jsReplace value="${msds.partDescription}" processMultiLines="true"/>'
										]}<c:if test="${!status.last}">,</c:if>
								       </c:forEach>]};
						</script>
					</c:otherwise>				
				</c:choose>
				<!-- Search results end -->


				<!-- Hidden element start -->
				<div id="hiddenElements" style="display: none;">
					<%--This is the minimum height of the result section you want to display--%> 
					<input name="minHeight" id="minHeight" type="hidden" value="100" />
					<tcmis:setHiddenFields beanName="inputBean"/>
				</div>
			
			</div>
		<!-- close of backGroundContent -->
		</div>
		<!-- interface End -->
	
	</tcmis:form>
</body>	
</html:html>
