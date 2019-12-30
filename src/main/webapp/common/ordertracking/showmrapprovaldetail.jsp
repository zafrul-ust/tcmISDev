<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

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
<%--Uncomment the below if your grid has rwospans >1--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
-->
<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--This file has our custom sorting and other utility methos for the grid.--%>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
<fmt:message key="label.approvaldetail"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",minutes:"<fmt:message key="label.minutes"/>",seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>"};


var financeJsonMainData = new Array();
var releaserJsonMainData = new Array();
var useJsonMainData = new Array();
var listJsonMainData = new Array();
var approverJsonMainData = new Array();

var financeApproverConfig = [
  {
	columnId:"name",
	columnName:'<fmt:message key="label.approver"/>',
	width:16
  },
  {
	columnId:"email",
	columnName:'<fmt:message key="label.email"/>',
	width:21
  },
  {
	columnId:"phone",
	columnName:'<fmt:message key="label.phone"/>',
	width:16
  }
];


var releaserConfig = [
  {
  	columnId:"name",
  	columnName:'<fmt:message key="label.approver"/>',
	width:16
  },
  {
  	columnId:"email",
  	columnName:'<fmt:message key="label.email"/>',
	width:21
  },
  {
  	columnId:"phone",
  	columnName:'<fmt:message key="label.phone"/>',
	width:16
  }
];

var useApproverConfig = [
  {
  	columnId:"name",
  	columnName:'<fmt:message key="label.approver"/>',
	width:16
  },
  {
  	columnId:"email",
  	columnName:'<fmt:message key="label.email"/>',
	width:21
  },
  {
  	columnId:"phone",
  	columnName:'<fmt:message key="label.phone"/>',
	width:16
  }
];

<c:set var="approvalDate">
 <fmt:message key="label.approvaldate"/>
</c:set>

<c:set var="approvalStatus">
 <fmt:message key="label.approvalstatus"/>
</c:set>

<c:set var="approvalComments">
 <fmt:message key="label.approvalcomments"/>
</c:set>


var listApproverConfig = [
   {
		columnId:"list",
		columnName:'<fmt:message key="label.list"/>',
		width:20
	},
	{
		columnId:"approvalStatus",
		columnName:'<tcmis:jsReplace value="${approvalStatus}"/>',
		width:10
	},
	{
		columnId:"approver",
		columnName:'<fmt:message key="label.approver"/>',
		width:10
	},
	{
		columnId:"approvalDate",
	    columnName:'<tcmis:jsReplace value="${approvalDate}"/>',
		sorting:"int",
		hiddenSortingColumn:"happrovalDate"
	},
	{
		columnId:"approvalComments",
		columnName:'<tcmis:jsReplace value="${approvalComments}"/>',
		width:12
	},
	{
		columnId:"mrLimit",
		columnName:'<fmt:message key="label.approver.mrlimit"/>',
		width:10
	},
	{
		columnId:"ytd",
		columnName:'<fmt:message key="label.approver.ytdlimit"/>',
		width:12
	},
	{
		columnId:"period",
		columnName:'<fmt:message key="label.approver.periodlimit"/>',
		width:12
	}
];

var approverConfig = [
     {
        columnId:"mrLine",
        columnName:'<fmt:message key="label.mrline"/>',
        width:10
    },
   {
		columnId:"approvalType",
		columnName:'<fmt:message key="label.approvaltype"/>',
		width:20
	},
	{
		columnId:"approvalStatus",
		columnName:'<tcmis:jsReplace value="${approvalStatus}"/>',
		width:10
	},
	{
		columnId:"approver",
		columnName:'<fmt:message key="label.approver"/>',
		width:10
	},
    {
		columnId:"email",
		columnName:'<fmt:message key="label.email"/>',
		width:10
	},
    {
		columnId:"phone",
		columnName:'<fmt:message key="label.phone"/>',
		width:10
	},
    {
		columnId:"approvalDate",
		columnName:'<tcmis:jsReplace value="${approvalDate}"/>',
		sorting:"int",
		hiddenSortingColumn:"happrovalDate"
	},
	{
		columnId:"approvalComments",
		columnName:'<tcmis:jsReplace value="${approvalComments}"/>',
		width:12
	}
];

<c:set var="approvalDetail">
 <fmt:message key="label.approvaldetail"/>
</c:set>

function loadpupup() {
	attachObj = $('resultsPage');
	var title =	'<tcmis:jsReplace value="${approvalDetail}"/> : <fmt:message key="label.mrline"/> ${param.prNumber} - ${param.lineItem}';
	eval('parent.${param.callback}(attachObj,financeGridConfig,releaserGridConfig,useGridConfig,listGridConfig,approverGridConfig,title)');
}

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="loadpupup()">

<div class="interface" id="resultsPage" style="width:650px;height:550px;">
<div class="backGroundContent">

<c:if test="${!empty financeApproverDataCollection}" >
	<div id="FinanceLabel" style="width:100%">
   	<table width="100%" border="0" cellpadding="0" cellspacing="0">
    		<tr>
    			<td class="optionTitleBoldCenter"><fmt:message key="label.financeapproverinformation"/></td>
    		</tr>
    	</table>
	</div>
	
	<div id="FinanceDiv" style="width:100%;height:100px;"></div>
	<!-- Search results start -->
	<script type="text/javascript">
	<!--
		/*Storing the data to be displayed in a JSON object array.*/
		var financeJsonMainData = {
		rows:[
		<c:forEach var="bean" items="${financeApproverDataCollection}" varStatus="status">
			<c:if test="${status.index > 0}">,</c:if>
			{ id:${status.index +1},
			 data:[
			  '<tcmis:jsReplace value="${bean.fullName}"/>',
			  '${bean.email}',
			  '${bean.phone}'
			  ]
			}
		 </c:forEach>
		]};
	//-->
	</script>
</c:if>

<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty releaserDataCollection}" >
	<div id="ReleaserLabel" style="width:100%">
   	<table width="100%" border="0" cellpadding="0" cellspacing="0">
    		<tr>
    			<td class="optionTitleBoldCenter"><fmt:message key="label.releaserinformation"/></td>
    		</tr>
    	</table>
	</div>

	<div id="ReleaserDiv" style="width:100%;height:100px;"></div>
		<!-- Search results start -->
		<script type="text/javascript">
		<!--
			/*Storing the data to be displayed in a JSON object array.*/
			var releaserJsonMainData = {
			rows:[
			<c:forEach var="bean" items="${releaserDataCollection}" varStatus="status">
				<c:if test="${status.index > 0}">,</c:if>
				{ id:${status.index +1},
				 data:[
				  '<tcmis:jsReplace value="${bean.fullName}"/>',
				  '${bean.email}',
				  '${bean.phone}'
				  ]
				}
			 </c:forEach>
			]};
		//-->
		</script>
</c:if>

<c:if test="${!empty useApproverDataCollection}" >
	<div id="UseLabel" style="width:100%">
   	<table width="100%" border="0" cellpadding="0" cellspacing="0">
    		<tr>
    			<td class="optionTitleBoldCenter"><fmt:message key="label.useapproverinformation"/></td>
    		</tr>
    	</table>
	</div>

	<div id="UseDiv" style="width:100%;height:100px;"></div>
	<!-- Search results start -->
	<script type="text/javascript">
	<!--
		/*Storing the data to be displayed in a JSON object array.*/
		var useJsonMainData = {
		rows:[
		<c:forEach var="bean" items="${useApproverDataCollection}" varStatus="status">
			<c:if test="${status.index > 0}">,</c:if>
			{ id:${status.index +1},
			 data:[
			  '<tcmis:jsReplace value="${bean.fullName}"/>',
			  '${bean.email}',
			  '${bean.phone}'
			  ]
			}
			 
		 </c:forEach>
		]};
	//-->
	</script>

</c:if>

<c:if test="${!empty listApproverCollection}" >
	<div id="ListLabel" style="width:100%">
   	<table width="100%" border="0" cellpadding="0" cellspacing="0">
    		<tr>
    			<td class="optionTitleBoldCenter"><fmt:message key="label.listapproverinformation"/></td>
    		</tr>
    	</table>
	</div>

	<div id="ListDiv" style="width:100%;height:145px;"></div>
	<!-- Search results start -->
	<script type="text/javascript">
	<!--
		/*Storing the data to be displayed in a JSON object array.*/
		var listJsonMainData = {
		rows:[
		<c:forEach var="bean" items="${listApproverCollection}" varStatus="status">
			<c:set var="mrSeparator" value=":"/>
			<c:if test="${bean.mrLimit == null}">
				<c:set var="mrSeparator" value=""/>
			</c:if>
			<c:set var="ytdSeparator" value=":"/>
			<c:if test="${bean.ytdLimit == null}">
				<c:set var="ytdSeparator" value=""/>
			</c:if>
			<c:set var="periodSeparator" value=":"/>
			<c:if test="${bean.periodLimit == null}">
				<c:set var="periodSeparator" value=""/>
			</c:if>

			<c:if test="${status.index > 0}">,</c:if>
			{ id:${status.index +1},
			 data:[
				  '<tcmis:jsReplace value="${bean.listName}"/>',
				  '${bean.listApprovalStatus}',
				  '<tcmis:jsReplace value="${bean.listApprover}"/>',
				  '<fmt:formatDate value="${bean.listApprovalDateRaw}" pattern="${dateFormatPattern}"/>',
				  '<tcmis:jsReplace value="${bean.listApprovalComment}" processMultiLines="true"/>',
				  '${bean.mrValue} ${mrSeparator} ${bean.mrLimit} ${bean.mrLimitUnit}',
				  '${bean.ytdLimitValue} ${ytdSeparator} ${bean.ytdLimit} ${bean.ytdLimitUnit}',
				  '${bean.periodLimitValue} ${periodSeparator} ${bean.periodLimit} ${bean.periodLimitUnit}'
			  ]
			}

		 </c:forEach>
		]};
	//-->
	</script>

</c:if>

<c:if test="${!empty approverCollection}" >
	<div id="ApprovalLabel" style="width:100%">
   	<table width="100%" border="0" cellpadding="0" cellspacing="0">
    		<tr>
    			<td class="optionTitleBoldCenter"><fmt:message key="label.financeapproverinformation"/></td>
    		</tr>
    	</table>
	</div>

	<div id="ApproverDiv" style="width:100%;height:145px;"></div>
	<!-- Search results start -->
	<script type="text/javascript">
	<!--
		/*Storing the data to be displayed in a JSON object array.*/
		var approverJsonMainData = {
		rows:[
		<c:forEach var="bean" items="${approverCollection}" varStatus="status">
			<c:if test="${status.index > 0}">,</c:if>
			{ id:${status.index +1},
			 data:[
				  '${bean.mrLine}',
				  '<tcmis:jsReplace value="${bean.approvalType}"/>',
				  '${bean.approvalStatus}',
				  '<tcmis:jsReplace value="${bean.approverName}"/>',
                  '<tcmis:jsReplace value="${bean.approvalEmail}"/>',
                  '<tcmis:jsReplace value="${bean.approvalPhone}"/>',   
                  '<fmt:formatDate value="${bean.approvalDate}" pattern="${dateFormatPattern}"/>',
				  '<tcmis:jsReplace value="${bean.approvalComment}" processMultiLines="true"/>'
			  ]
			}

		 </c:forEach>
		]};
	//-->
	</script>

</c:if>


<c:if test="${empty financeApproverDataCollection && empty releaserDataCollection && empty useApproverDataCollection && empty listApproverCollection && empty approverCollection}" >
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
</div>

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

<!-- You can build your error messages below. But we want to trigger the pop-up from the main page.
So this is just used to feed the pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
  ${tcmISError}<br/>
  <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
  </c:forEach>
</div>

<script type="text/javascript">
<!--

var financeGridConfig = {
	divName:'FinanceDiv', // the div id to contain the grid.
	beanData:financeJsonMainData,     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	beanGrid:'financeBeanGrid',     // the grid's name, as in beanGrid.attachEvent...
	config:financeApproverConfig,	     // the column config var name, as in var config = { [ columnId:..,columnName...
	rowSpan:false,			 // this page has rowSpan > 1 or not.
	submitDefault:false,    // the fields in grid are defaulted to be submitted or not.
	onRowSelect:null,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
	onRightClick:null   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
//	onBeforeSorting:_onBeforeSorting
};

var releaserGridConfig = {
	divName:'ReleaserDiv', // the div id to contain the grid.
	beanData:releaserJsonMainData,     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	beanGrid:'releaserBeanGrid',     // the grid's name, as in beanGrid.attachEvent...
	config:releaserConfig,	     // the column config var name, as in var config = { [ columnId:..,columnName...
	rowSpan:false,			 // this page has rowSpan > 1 or not.
	submitDefault:false,    // the fields in grid are defaulted to be submitted or not.
	onRowSelect:null,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
	onRightClick:null   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
//	onBeforeSorting:_onBeforeSorting
};

var useGridConfig = {
	divName:'UseDiv', // the div id to contain the grid.
	beanData:useJsonMainData,     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	beanGrid:'useBeanGrid',     // the grid's name, as in beanGrid.attachEvent...
	config:useApproverConfig,	     // the column config var name, as in var config = { [ columnId:..,columnName...
	rowSpan:false,			 // this page has rowSpan > 1 or not.
	submitDefault:false,    // the fields in grid are defaulted to be submitted or not.
	onRowSelect:null,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
	onRightClick:null   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
//	onBeforeSorting:_onBeforeSorting
};

var listGridConfig = {
	divName:'ListDiv', // the div id to contain the grid.
	beanData:listJsonMainData,     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	beanGrid:'listBeanGrid',     // the grid's name, as in beanGrid.attachEvent...
	config:listApproverConfig,	     // the column config var name, as in var config = { [ columnId:..,columnName...
	rowSpan:false,			 // this page has rowSpan > 1 or not.
	submitDefault:false,    // the fields in grid are defaulted to be submitted or not.
	onRowSelect:null,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
	onRightClick:null   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
//	onBeforeSorting:_onBeforeSorting
};

var approverGridConfig = {
	divName:'ApproverDiv', // the div id to contain the grid.
	beanData:approverJsonMainData,     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	beanGrid:'approverBeanGrid',     // the grid's name, as in beanGrid.attachEvent...
	config:approverConfig,	     // the column config var name, as in var config = { [ columnId:..,columnName...
	rowSpan:false,			 // this page has rowSpan > 1 or not.
	submitDefault:false,    // the fields in grid are defaulted to be submitted or not.
	onRowSelect:null,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
	onRightClick:null   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
//	onBeforeSorting:_onBeforeSorting
};

/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${empty tcmISErrors and empty tcmISError}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>

//-->
</script>

</body>
</html:html>