<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis"%>

<html:html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp"%> <!--Use this tag to get the correct CSS class.
	This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss /> <%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script> 
<script type="text/javascript" src="/js/common/commonutil.js"></script> <!-- This handles all the resizing of the page and frames -->
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script><!-- This handles which key press events are disabled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handles the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script> 
<script type="text/javascript" src="/js/menu/mmenudom.js"></script> 
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script> 
<script type="text/javascript" src="/js/menu/contextmenu.js"></script> 
<%@ include file="/common/rightclickmenudata.jsp" %>
	
<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
<script type="text/javascript" src="/js/hub/testrequestsearchresults.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
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

<title></title>
</head>

<body bgcolor="#ffffff" onload="testRequestSearchResultsOnLoad();"  onunload="try{closeAllchildren();}catch(ex){}">

<div class="interface" id="resultsPage">
	<div class="backGroundContent">
		<!--  <div class="dataContent" id="testRequestSearchResults" style="width:100%;height:400px"></div>	-->
		<div id="testRequestSearchResults" style="width:100%;height:400px;" style="display: none;"></div>
		<script language="JavaScript" type="text/javascript">
		<c:if test="${!empty testRequestSearchResults}" >  
			// Generates an anonymous object with properties: id and data (an array) transcribing the results of the bean to the page.
			var trSearchResultData = {
					rows:[ 
						<c:forEach var="tr" items="${testRequestSearchResults}" varStatus="status">
							{id:${status.count},
							 data:[	'Y',
									'<tcmis:jsReplace value="${tr.testRequestId}" processMultiLines="false"/>',
							       	'<tcmis:jsReplace value="${tr.requestStatus}" processMultiLines="true"/>',
									'<fmt:formatDate value="${tr.createDate}" pattern="${dateFormatPattern}"/>',
									'<tcmis:jsReplace value="${tr.catPartNo}" processMultiLines="true"/>',
									'<tcmis:jsReplace value="${tr.partDescription}" processMultiLines="true"/>',		
									'${tr.itemId}',
									'${tr.receiptId}',
									'<tcmis:jsReplace value="${tr.mfgLot}" />',
									'<tcmis:jsReplace value="${tr.lotStatus}" />',						
									'<tcmis:jsReplace value="${tr.facility}" />',						
									'<tcmis:jsReplace value="${tr.requestor}" />',						
									'<fmt:formatDate value="${tr.dateOfReceipt}" pattern="${dateFormatPattern}"/>',
									'<fmt:formatDate value="${tr.dateToLab}" pattern="${dateFormatPattern}"/>',
									'<fmt:formatDate value="${tr.dateReceivedByLab}" pattern="${dateFormatPattern}"/>',
									'<fmt:formatDate value="${tr.dateTestComplete}" pattern="${dateFormatPattern}"/>',	
									'<tcmis:emptyIfZero value="${tr.labAge}"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${tr.labAge}"/></fmt:formatNumber></tcmis:emptyIfZero>',
									'<tcmis:jsReplace value="${tr.materialQualified}" processMultiLines="true"/>',
									'<tcmis:jsReplace value="${tr.qualityTrackingNumber}" processMultiLines="true"/>'
								  ]
							}
					  		<c:if test="${!status.last}">,</c:if>
				  		</c:forEach>
					]};		
			</c:if>	
		</script>		
				
		<%-- If the collection is empty say no data found --%>
		<c:if test="${empty testRequestSearchResults}" >			
			  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
		    <tr>
		       <td width="100%">
		          <fmt:message key="main.nodatafound"/>
		       </td>
		    </tr>
		  </table>
		</c:if>
	</div>
</div>

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
	<form action="donothing.do">
		<input type="hidden" name="uAction" id="uAction" value="search"/>
		<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
		<input name="searchHeight" id="searchHeight" type="hidden" value="150">
		<input name="endSearchTime" id="endSearchTime" type="hidden" value=""/>
		<input name="totalLines" id="totalLines" type="hidden" value="${fn:length(testRequestSearchResults)}"/>
	</form>
</div>
<!-- Hidden elements end -->
<!-- close of interface -->

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages"
	style="display: none; overflow: auto;"></div>

<script language="JavaScript" type="text/javascript">
	//add all the javascript messages here, this for internationalization of client side javascript messages
	
	var messagesData = {
		alert:"<fmt:message key="label.alert"/>",
		and:"<fmt:message key="label.and"/>",
		all:"<fmt:message key="label.all"/>",
		errors:"<fmt:message key="label.errors"/>",  
		validvalues:"<fmt:message key="label.validvalues"/>",
		pleasewait:"<fmt:message key="label.pleasewait"/>",	
		recordFound:"<fmt:message key="label.recordFound"/>",
		searchDuration:"<fmt:message key="label.searchDuration"/>",
		minutes:"<fmt:message key="label.minutes"/>",
		seconds:"<fmt:message key="label.seconds"/>",
		submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
		marsDetail:"<fmt:message key="label.marsdetail"/>"
	};
	
	// Right-click menus are built using the milonic menu package.
	with(milonic=new menuname("testRequestRightClick")){
		 top="offset=2"
		 style = contextStyle;
		 margin=3
		 aI("text=<fmt:message key="label.showmarsdetail"/>;url=javascript:showRequestDetail();");
		}
	drawMenus();

	// Grid configuration is defined using a set of anonymous objects.
	// First, an object with properties to define the grid. Second, an array of column objects to define the column layout.
	// Third, a JSON data construct with rows built from the associated data bean.
	var testResultGridConfig = {
			divName: 'testRequestSearchResults',
			beanData: 'trSearchResultData',
			beanGrid: 'trSearchResultsGrid',
			config: 'trSearchResultsGridConfig',
			rowSpan: false,
			backgroundRender: true,
			submitDefault: false,
			onRightClick:'showRightClick'
	};
	var trSearchResultsGridConfig = [
		{columnId:'permission'},
		{columnId:'testRequestId', columnName:'<fmt:message key="label.requestno"/>', width:5},
		{columnId:'requestStatus', columnName:'<fmt:message key="label.status"/>', width:4},
		{columnId:'createDate', columnName:'<fmt:message key="label.datecreated"/>', width:7},
		{columnId:'catPartNo', columnName:'<fmt:message key="label.partno"/>', width:9},
		{columnId:'partDescription', columnName:'<fmt:message key="label.partdescription"/>', width:20, tooltip:true},
		{columnId:'itemId', columnName:'<fmt:message key="label.item"/>', width:4},
		{columnId:'receiptId', columnName:'<fmt:message key="label.receiptid"/>', width:5},
		{columnId:'mfgLot', columnName:'<fmt:message key="label.lotnumber"/>', width:8},
		{columnId:'lotStatus', columnName:'<fmt:message key="label.lotstatus"/>', width:5},
		{columnId:'facility', columnName:'<fmt:message key="label.facility"/>', width:15, tooltip: true},
		{columnId:'requestor', columnName:'<fmt:message key="label.requestor"/>', width:10},
		{columnId:'dateOfReceipt', columnName:'<fmt:message key="receivedreceipts.label.dor"/>', width:7},
		{columnId:'dateToLab', columnName:'<fmt:message key="label.datesenttolab"/>', width:7},
		{columnId:'dateReceivedByLab', columnName:'<fmt:message key="label.datesamplesreceived"/>', width:7},
		{columnId:'dateTestComplete', columnName:'<fmt:message key="label.datetestscompleted"/>', width:7},
		{columnId:'labAge', columnName:'<fmt:message key="label.daysinlab"/>', width:7},
		{columnId:'materialQualified', columnName:'<fmt:message key="label.materialqualified"/>', width:5},
		{columnId:'qualityNote', columnName:'<fmt:message key="label.qualitynote"/>', width:5}
     	];

</script>
</body>
</html:html>