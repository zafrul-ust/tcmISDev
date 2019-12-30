<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
	<jsp:useBean id="today" class="java.util.Date"/>
	<c:set var="javascriptDate"><fmt:message key="javascript.dateformat"/></c:set>
	
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta http-equiv="expires" content="-1">
	<link rel="shortcut icon" href="/images/buttons/tcmIS.ico">
	</link>
	<%@ include file="/common/locale.jsp" %>

	<!--Use this tag to get the correct CSS class.
		This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
	<tcmis:gridFontSizeCss />

	<!-- Add any other stylesheets you need for the page here -->
	<script type="text/javascript" src="/js/common/formchek.js"></script>
	<script type="text/javascript" src="/js/common/commonutil.js"></script>

	<%--NEW - grid resize--%>
	<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
	
	<!-- This handles which key press events are disabled on this page -->
	<script src="/js/common/disableKeys.js"></script>
	
	<!-- This handles the menu style and what happens to the right click on the whole page -->
	<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
	<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
	<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
	<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
	
	<%@ include file="/common/rightclickmenudata.jsp" %>

	<!-- Add any other javascript you need for the page here -->
	<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
	<script type="text/javascript" src="/js/common/cabinet/clientcabinetlabelresults.js"></script>
	<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
	<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
	
	<!-- These are for the Grid, uncomment if you are going to use the grid -->
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
	 
	<%--This is to allow copy and paste. works only in IE--%>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_selection.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_nxml.js"></script>
	
	<%--This has the custom cells we built, hcal - the internationalized calendar which we use hlink- this is for any links you want tp provide in the grid, the URL/function to call can be attached based on a even (rowselect etc) --%>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
	
	<%--Custom sorting. This custom sorting function implements case insensitive sorting. --%>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

	<script language="JavaScript" type="text/javascript">
		//add all the javascript messages here, this for internationalization of client side javascript messages
		var messagesData = new Array();
		messagesData= {
			alert: "<fmt:message key="label.alert"/>",
			and: "<fmt:message key="label.and"/>", 
			validvalues: "<fmt:message key="label.validvalues"/>",
			submitOnlyOnce: "<fmt:message key="label.submitOnlyOnce"/>",
			itemInteger: "<fmt:message key="error.item.integer"/>",
			recordFound: "<fmt:message key="label.recordFound"/>",
			searchDuration: "<fmt:message key="label.searchDuration"/>",
			minutes: "<fmt:message key="label.minutes"/>",
			seconds: "<fmt:message key="label.seconds"/>",
			selectCabinet : "<fmt:message key="label.pleaseselectaworkarea"/>",
			generateBinLabel : "<fmt:message key="cabinetbinlabel.label.generatebinlabel"/>",
			pleasewait: "<fmt:message key="label.pleasewaitmenu"/>"
	       };

		with(milonic=new menuname("binDetailMenu")){
			top="offset=2";
			style = contextStyle;
			margin=3;
			aI("text=<fmt:message key="binlabels.title"/>;url=javascript:showBinDetails();");
		};
		drawMenus();

		<%-- See webroot/dhtmlxgrid/codebase/dhtmlxcommon_haas.js for option explanations of config & gridconfig --%>
	       var config = [ 
	       		{columnId: "permission"},
			{columnId: "print", columnName:'<fmt:message key="label.print"/><br><input type="checkbox" value="" onClick="return checkAll(\'print\');" name="checkAllPrint" id="checkAllPrint">', type:"hchstatus", width:6, align:"center", submit:true, sorting:"na"},
			{columnId: "cabinetIdDisplay", columnName:'<fmt:message key="label.id"/>', align:"center", submit:true},
			{columnId: "cabinetName", columnName:'<fmt:message key="label.description"/>', tooltip:true, width:30},
			{columnId: "application", columnName:'<fmt:message key="label.workareagroup"/>', submit: true, tooltip:true, width:30},
			{columnId: "cabinetId", submit:true}
	       ];
	       
	       var gridConfig = {
		       divName:'clientCabinetLabelResults', // the div id to contain the grid.
		       beanData:'jsonMainData', // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		       beanGrid:'mygrid', // the grid's name, as in beanGrid.attachEvent...
		       config:config, // the column config var name, as in var config = { [ columnId:..,columnName...
		       rowSpan:false, // true: this page has rowSpan > 1 or not, disables smartrendering & sorting
		       backgroundRender: true,
		       submitDefault:false, // the fields in grid are defaulted to be submitted or not.
		       onRightClick:rightClickRow // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
	       };
       
      </script>
</head>
<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);">
	<tcmis:form action="/clientcabinetlabelresults.do" onsubmit="return submitFrameOnlyOnce();">
		<script  language="JavaScript" type="text/javascript">
			<c:if test= "${!empty searchResultBeanCollection}" >
				var jsonMainData = {
					rows: [	<c:forEach var= "row" items= "${searchResultBeanCollection}" varStatus= "status" >
							{ id: ${status.index + 1},
							  data: ['Y',
							  	 '',
							  	 '<fmt:formatNumber type="number" groupingUsed="false" minIntegerDigits="8" value="${row.cabinetId}" />',
							  	 '<tcmis:jsReplace value="${row.cabinetName}"/>',
							  	 '<tcmis:jsReplace value="${row.applicationDesc}"/>',
							  	 '${row.cabinetId}'
							  	] 
							}<c:if test= "${!status.last}" >,</c:if>
						</c:forEach>
					]
				};
			</c:if>
		</script>		
			
		<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
			The default value of showUpdateLinks is false.
		-->
		<tcmis:inventoryGroupPermission indicator="true" userGroupId="Hub ItemCount" inventoryGroup="${inventoryGroup}">
			<script type="text/javascript">
				showUpdateLinks = true;
			</script>
		</tcmis:inventoryGroupPermission>
		
		<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
			So this is just used to feed the YUI pop-up in the main page.
			Similar divs would have to be built to show any other messages in a pop-up.-->
		<!-- Error Messages Begins -->
		<div id="errorMessagesAreaBody" style="display:none;">
			${tcmisError}
		</div>
		<script type="text/javascript">
			<!--
			<c:choose> 
				<c:when test= "${empty tcmisError}" >
					showErrorMessage = false;
				</c:when>
				<c:otherwise>
					showErrorMessage = true;
				</c:otherwise> 
			</c:choose>//-->
		</script>
		<!-- Error Messages Ends -->

		<div class="interface" id="resultsPage">
			<div class="backGroundContent">
				<div id="clientCabinetLabelResults" style="width:100%;height:400px;" style="display: none;" >
				</div>
				<!-- If the collection is empty say no data found -->
				<c:if test="${empty searchResultBeanCollection}" >
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
						<tr>
							<td width="100%">
								<fmt:message key="main.nodatafound" />
							</td>
						</tr>
					</table>
				</c:if>
				<!-- Hidden element start -->
				<div id="hiddenElements" style="display: none;">
					<tcmis:setHiddenFields beanName="cabinetLabelInputBean"/>
				</div>
				<!-- Hidden elements end -->
			</div>
			<!-- close of backGroundContent -->
		</div>
		<!-- close of interface -->
	</tcmis:form>
	<iframe id="LabelPrintFrame" src="/blank.html" name="LabelPrintFrame" style="border:0px; width:0px; height:0px;"></iframe>
	<form name="LabelPrintForm" id="LabelPrintForm" method="post" action="/tcmIS/Hub/Cabinetlabel" target="LabelPrintFrame">
	</form>	
	
	<div id="generatingWin" class="errorMessages" style="display:none;left:20%;top:20%;z-index:5;">
		<table width="100%" border="0" cellpadding="2" cellspacing="1">
			<tr><td>&nbsp;</td></tr>
			<tr><td>&nbsp;</td></tr>
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td align="center" id="messageText">
					<fmt:message key="label.generatinglabels"/>
				</td>
			</tr>
			<tr>
				<td align="center">
					<img src="/images/rel_interstitial_loading.gif" align="middle">
				</td>
			</tr>
		</table>
	</div>	
	
	</body>
</html:html>	

