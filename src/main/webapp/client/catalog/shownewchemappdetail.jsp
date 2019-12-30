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


var config = [
  {
  	columnId:"approvalRole",
  	columnName:'<fmt:message key="label.approvalRole"/>',
  	width:14
  },
  {
  	columnId:"status",
  	columnName:'<fmt:message key="label.status"/>',
  	width:6
  },
  {
  	columnId:"chemicalApprovers",
  	columnName:'<fmt:message key="label.chemicalApprovers"/>',
  	width:16
  },
  {
  	columnId:"date",
 	columnName:'<fmt:message key="label.date"/>',
 	width:6
  },
  {
  	columnId:"reason",
 	columnName:'<fmt:message key="label.comments"/>',
    width:40
  }

  ];


var hconfig = [
  {
  	columnId:"materialDesc",
  	columnName:'<fmt:message key="label.materialdescription"/>',
	width:21
  },
  {
  	columnId:"packaging",
  	columnName:'<fmt:message key="label.packaging"/>',
	width:16
  },
  {
  	columnId:"manufacturer",
  	columnName:'<fmt:message key="label.manufacturer"/>',
	width:16
  }
  ];


<c:set var="approvalDetail">
 <fmt:message key="label.approvaldetail"/>
</c:set>

function loadpupup() {
	attachObj = $('resultsPage');
	var title =	'<tcmis:jsReplace value="${approvalDetail}"/>';
	eval('parent.${param.callback}(attachObj,hgridConfig,gridConfig,title)');
}

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="loadpupup()">

<div class="interface" id="resultsPage" style="width:650px;height:400px;">
<div class="backGroundContent">

<c:if test="${!empty headerColl[2]}" >
<c:set var="pos" value='${headerColl[3]}'/>

<div id="HeaderCommonInfo" style="width:100%">
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
    	<tr>
    	<td class="optionTitleBoldRight"><fmt:message key="label.requestid"/>:</td>
    	<td class="optionTitleLeft">&nbsp;${param.requestId}</td>
    	<td class="optionTitleBoldRight"><fmt:message key="label.requestdate"/>:</td>
    	<td class="optionTitleLeft">&nbsp;<fmt:formatDate value="${headerColl[2][0][ pos['REQUEST_DATE'] ]}" pattern="${dateFormatPattern}"/></td>
    	</tr>
    	<tr>
    	<td class="optionTitleBoldRight"><fmt:message key="label.requestor"/>:</td>
    	<td class="optionTitleLeft">&nbsp;${headerColl[2][0][ pos["REQUESTOR_NAME"] ]}</td>
    	<td class="optionTitleBoldRight"><fmt:message key="label.facility"/>:</td>
    	<td class="optionTitleLeft">&nbsp;${headerColl[2][0][ pos["FACILITY_NAME"] ]}</td>
    	</tr>
    	<tr>
    	<td class="optionTitleBoldRight"><fmt:message key="label.status"/>:</td>
    	<td class="optionTitleLeft">&nbsp;${headerColl[2][0][ pos["REQUEST_STATUS_DESC"] ]}</td>
    	<td class="optionTitleBoldRight"><%--<fmt:message key="label.workarea"/>:--%></td>
    	<td class="optionTitleLeft"></td>
    	</tr>
    </table>
</div>

<div id="HeaderBean" style="width:100%;height:100px;"></div>
<!-- Search results start -->
<script type="text/javascript">
<!--
/*This is to keep track of whether to show any update links.
If the user has any update permisions for any row then we show update links.*/
<c:set var="showUpdateLink" value='N'/>

/*Storing the data to be displayed in a JSON object array.*/
var hjsonMainData = {
rows:[
<c:forEach var="bean" items="${headerColl[2]}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
<c:set var="packaging" value=""/>
<c:if test="${bean[ pos['APPROVAL_PATH'] ] != 'MSDS'}"><c:set var="packaging" value="${bean[ pos['PACKAGING'] ]}"/></c:if>
{ id:${status.index +1},
 data:[
  '<tcmis:jsReplace value="${bean[ pos['MATERIAL_DESC'] ]}"/>',
  '<tcmis:jsReplace value="${packaging}"/>',
  '<tcmis:jsReplace value="${bean[ pos['MANUFACTURER'] ]}"/>'
  ]
}

 </c:forEach>
]};
//-->
</script>

</c:if>



<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty beanCollection}" >
<div id="DetailBean" style="width:100%;height:200px;"></div>
<!-- Search results start -->
<script type="text/javascript">
<!--
/*This is to keep track of whether to show any update links.
If the user has any update permisions for any row then we show update links.*/
<c:set var="showUpdateLink" value='N'/>
/*Storing the data to be displayed in a JSON object array.*/
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="bean" items="${beanCollection}" varStatus="status">
<c:set var="tmpApprovalRole"><c:out value="${bean.approvalRole}"></c:out></c:set>
<c:set var="tmpStatus" value='${bean.status}'/>
<c:if test="${!empty bean.restricted && bean.restricted == 'Y' }">
    <c:set var="tmpStatus">${bean.status} (R)</c:set>
</c:if>

<c:if test="${status.index > 0}">,</c:if>
{ id:${status.index +1},
 data:[
  '<tcmis:jsReplace value="${tmpApprovalRole}"/>',
  '${tmpStatus}',
  '<tcmis:jsReplace value="${bean.chemicalApprovers}"/>',
  '<fmt:formatDate value="${bean.approvalDate}" pattern="${dateFormatPattern}"/>',
  '<tcmis:jsReplace value="${bean.reason}" processMultiLines="true"/>'
  ]
}

 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};
//-->
</script>

</c:if>

<c:if test="${empty beanCollection}" >
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

var gridConfig = {
	divName:'DetailBean', // the div id to contain the grid.
	beanData:jsonMainData,     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	beanGrid:'hDetailBeanGrid',     // the grid's name, as in beanGrid.attachEvent...
	config:config,	     // the column config var name, as in var config = { [ columnId:..,columnName...
	rowSpan:false,			 // this page has rowSpan > 1 or not.
	submitDefault:false,    // the fields in grid are defaulted to be submitted or not.
	onRowSelect:null,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
	onRightClick:null   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
//	onBeforeSorting:_onBeforeSorting
};

var hgridConfig = {
	divName:'HeaderBean', // the div id to contain the grid.
	beanData:hjsonMainData,     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	beanGrid:'hbeanGrid',     // the grid's name, as in beanGrid.attachEvent...
	config:hconfig,	     // the column config var name, as in var config = { [ columnId:..,columnName...
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

showUpdateLinks = true;
//-->
</script>

</body>
</html:html>