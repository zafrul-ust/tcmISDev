<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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
	This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
	<tcmis:gridFontSizeCss />
	<!-- Add any other stylesheets you need for the page here -->
	
	<script type="text/javascript" src="/js/common/formchek.js"></script>
	<script type="text/javascript" src="/js/common/commonutil.js"></script>
	<%--NEW - grid resize--%>
	<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
	<!-- This handels which key press events are disabeled on this page -->
	<script src="/js/common/disableKeys.js"></script>

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

	<tcmis:fontSizeCss />
<script language="JavaScript" type="text/javascript">
<!--
var messagesData = new Array();
messagesData = {
		submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
		recordFound:"<fmt:message key="label.recordFound"/>",
		searchDuration:"<fmt:message key="label.searchDuration"/>",
		minutes:"<fmt:message key="label.minutes"/>",
		seconds:"<fmt:message key="label.seconds"/>"
	}
var resizeGridWithWindow = true;
var beangrid;

var config = [
{
  columnId:"permission"
},
{ columnId:"percent"
	<c:if test="${param.onlist == 'Yes'}">
	
  ,columnName:'<fmt:message key="label.percent"/>%',
  width:6,
  align:'center',
  tooltip:"Y"
  </c:if>
},
{ columnId:"casNumber",
  columnName:'<fmt:message key="label.casnumber"/>',
  width:8,
  tooltip:"Y"
},
{ columnId:"description",
  columnName:'<fmt:message key="label.rptchemical"/>',
  tooltip:"Y",
  width:40
}
];

var gridConfig = {
		divName:'MsdsReportListViewBean',
		beanData:'jsonMainData',
		beanGrid:'beangrid',
		config:'config',
		rowSpan:false
	};
	
function initializeGrid(){
	initGridWithGlobal(gridConfig);
}

function onLoad()
{	
	var lineCount = document.getElementById("totalLines").value;
	
	if (lineCount > 0)
	{
		document.getElementById("MsdsReportListViewBean").style["display"] = "";
	}  
	else
	{
		document.getElementById("MsdsReportListViewBean").style["display"] = "none";   
	}
	initializeGrid();
	
	displayGridSearchDuration();
	
	setResultFrameSize();
	 
	parent.fillInReportLabels('${param.listName}', '${param.sublistName}');
}

function reSizeResultGrid() {
	setResultFrameSize();
}
//-->
</script>

</head>
<body bgcolor="#ffffff" onload="onLoad();">
<tcmis:form action="/msdsviewerlistresults.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="resultsPage">
	<div class="backGroundContent">
<div id="MsdsReportListViewBean" style="width:100%;height:300px;display: none;"></div>
<!-- Search results start -->
<c:set var="colorClass" value=''/>
<c:if test="${reportListCollection != null}" >
<script type="text/javascript">
<!--
<c:set var="dataCount" value='${fn:length(reportListCollection)}'/>
<c:if test="${!empty reportListCollection}" >
/*Storing the data to be displayed in a JSON object array.*/
var jsonMainData = ${jsonMainData};
</c:if>
-->
</script>


<!-- If the collection is empty say no data found -->
<c:if test="${empty reportListCollection}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>
</c:if>
<div id="hiddenElements" style="display: none;">
	<%--This is the minimum height of the result section you want to display--%> 
	<%--<input name="minHeight" id="minHeight" type="hidden" value="100" />--%>
	<input name="totLines" id="totalLines" type="hidden" value="<c:out value="${dataCount}"/>" />
	<input name="uAction" id="uAction" value="search" type="hidden">
</div>

<!-- results end -->
</div>
</div>

			
</tcmis:form>
</body>
</html:html>


