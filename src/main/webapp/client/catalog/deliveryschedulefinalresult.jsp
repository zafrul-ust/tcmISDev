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
	<script type="text/javascript" src="/js/calendar/calendarval.js"></script>
	
	<!-- Add any other javascript you need for the page here -->
	
	
	<!-- These are for the Grid-->
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
	<%--This is for HTML form integration.--%>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
	<%--This is for smart rendering.--%>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
	<%--This is to suppoert loading by JSON.--%>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>    
	<%--Uncomment below if you are providing header menu to switch columns on/off--%>
	<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
	
	<%--Uncomment the below if your grid has rowspans >--%>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
	
	<%--This has the custom cells we built, hcal - the internationalized calendar which we use
	    hlink- this is for any links you want tp provide in the grid, the URL/function to call
	    can be attached based on a even (rowselect etc)
	--%>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>

	<%--This file has our custom sorting and other utility methos for the grid.--%>    
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
	
	<title>
		<fmt:message key="label.final"/> <fmt:message key="deliveryschedule.label.title"/>
	</title>
	
<script type="text/javascript" language="JavaScript">
<!--
var windowCloseOnEsc = true;

<%-- See webroot/dhtmlxgrid/codebase/dhtmlxcommon_haas.js for option explanations of config & gridconfig --%>
var columnConfig = [ 
	{columnId:"permission"},  
	{columnId:"dateToDelivery", columnName:'<fmt:message key="label.datetodeliver"/>', align:'center'},
	{columnId:"qty", columnName:'<fmt:message key="label.qty"/>', align:'center'}
    ];
       
   var gridConfig = {
       divName:'deliveryScheduleFinalBean', // the div id to contain the grid. ALSO the var name for the submitted data
       beanData:'jsonMainData', // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
       beanGrid:'beangrid', // the grid's name, for later reference/usage
       config:'columnConfig' // the column config var name, as in var config = { [ columnId:..,columnName...
   };
   
   var messagesData = new Array();
		messagesData = {alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
   		recordFound:"<fmt:message key="label.recordFound"/>",
   		searchDuration:"<fmt:message key="label.searchDuration"/>",
   		minutes:"<fmt:message key="label.minutes"/>",
   		seconds:"<fmt:message key="label.seconds"/>",
   		validvalues:"<fmt:message key="label.validvalues"/>",
   		msdsMaintenance:"<fmt:message key="msdsMaintenance"/>",
   		submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

function myOnLoad() {
	document.getElementById("deliveryScheduleFinalBean").style["display"] = ""; 
    /*this is the new part.*/
    initGridWithGlobal(gridConfig); 
}

-->			
</script>

</head>
<body bgcolor="#ffffff" onload="myOnLoad();">
	<table width="100%" id="detailsDataTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
		<tr>
			<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.prnumber" />:&nbsp;</td>
			<td width="15%" class="optionTitleLeft">${headerBean.prNumber}</td>
		</tr>
		<tr>
			<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.lineitem" />:&nbsp;</td>
			<td width="15%" class="optionTitleLeft">${headerBean.lineItem}</td>
		</tr>
		<tr>
			<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.totalqty" />:&nbsp;</td>
			<td width="15%" class="optionTitleLeft">${headerBean.quantity}</td>
		</tr>
	</table>
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
				<div id="deliveryScheduleFinalBean" style="width: 100%; height: 300px;" style="display: none;"></div>

				<c:choose>
					<c:when test="${empty deliveryScheduleFinalBeanCollection}">
                        <td width="100%"><fmt:message key="main.nodatafound" /></td>
					</c:when> 
					<c:otherwise>
						<script type="text/javascript">
							<c:set var="lineCount" value='0'/>
							var jsonMainData = new Array();
							var jsonMainData = {
								rows:[
								<c:forEach var="deliveryScheduleFinalBean" items="${deliveryScheduleFinalBeanCollection}" varStatus="status">
									<c:set var="lineCount" value='${lineCount+1}'/>
									<fmt:formatDate var="deliveryDate" value="${deliveryScheduleFinalBean.dateToDeliver}" pattern="${dateFormatPattern}"/>
									<c:if test="${lineCount > 1}">,</c:if>
									{id:${lineCount},
									 data:['N',
									  '${deliveryDate}',
									  '${deliveryScheduleFinalBean.qty}'
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
					<%--<input name="minHeight" id="minHeight" type="hidden" value="100" />--%>
					<input name="totalLines" id="totalLines" type="hidden" value="<c:out value="${lineCount}"/>">
					<input name="uAction" id="uAction" value="search" type="hidden">
			        <input name="minHeight" id="minHeight" type="hidden" value="100"> 
				</div>
			
			</div>
		<!-- close of backGroundContent -->
		</div>
		<!-- interface End -->
	
</body>
</html:html>