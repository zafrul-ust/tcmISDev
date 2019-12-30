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
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/hub/freighttracking.js"></script>

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
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--This file has our custom sorting and other utility methos for the grid.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
<fmt:message key="label.freighttracking"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
mustBeAnInteger:"<fmt:message key="errors.integer"/>",
firstTrackingNumber:"<fmt:message key="label.starttrackingnumber"/>",
lastTrackingNumber:"<fmt:message key="label.endtrackingnumber"/>",
startIsGreatertThanEnd:"<fmt:message key="label.startisgreaterthanend"/>",
newHasToBeGreaterThanCurrent:"<fmt:message key="label.newhastobegreaterthancurrent"/>",	
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
pleasewait:"<fmt:message key="label.pleasewaitmenu"/>",
noRowSelected:"<fmt:message key="error.norowselected"/>",
pleaseSelectRowForUpdate:"<fmt:message key="label.pleaseselectarowforupdate"/>",	 
newLabel:"<fmt:message key="label.new"/>"
};

with ( milonic=new menuname("newTrackingBlock") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
 aI( "text=<fmt:message key="label.new"/> ;url=javascript:addNewBlock();" );
}

with ( milonic=new menuname("empty") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
 aI( "text=" );
}
drawMenus();

var config = [
{ columnId:"permission",
  columnName:''
},
{ columnId:"ok",
  columnName:'<fmt:message key="label.ok"/>',
  width:3,
  type:"hch",
  sorting:"haasHch",
  align:"center",
  submit:true
}
,
{ columnId:"hubName",
  columnName:'<fmt:message key="label.hub"/>',
  width:14,
  submit:true
}
,
{ columnId:"batchNumber",
  columnName:'<fmt:message key="label.batchnumber"/>',
  width:14,
  submit:true
}
,
{ columnId:"carrierName",
  columnName:'<fmt:message key="label.carrier"/>',
  width:14,
  submit:true
}
,
{ columnId:"trackingNumberPrefix",
  columnName:'<fmt:message key="label.prefix"/>',
  type:"hed",
  width:14,
  submit:true
}
,
{ columnId:"firstTrackingNumber",
  columnName:'<fmt:message key="label.starttrackingnumber"/>',
  type:"hed",
  width:14,
  submit:true
}
,
{ columnId:"lastTrackingNumber",
  columnName:'<fmt:message key="label.endtrackingnumber"/>',
  type:"hed",
  width:14,
  submit:true
}
,
{ columnId:"currTrackingNumber",
  columnName:'<fmt:message key="label.currenttrackingnumber"/>',
  width:14,
  submit:true
}
,
{ columnId:"status",
  columnName:'<fmt:message key="label.status"/>',
  width:7,
  submit:true
}
,
{ columnId:"checkdigitFunction",
  columnName:'<fmt:message key="label.checkdigit"/>',
  width:7,
  submit:true
}
,
{ columnId:"hub",
  columnName:'<fmt:message key="label.hub"/>',
  width:0,
  submit:true
}
];

// -->
</script>
</head>


<body bgcolor="#ffffff" onload="myOnLoad();">
<tcmis:form action="/freighttrackingresults.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="resultsPage">
<div class="backGroundContent">
<c:set var="dataCount" value='${0}'/>

<div id="hubTrackingNumbersViewBean" style="width:100%;height:400px;"></div>	
<c:if test="${!empty freightTrackingColl}" >
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
		<c:forEach var="freightTrackingBean" items="${freightTrackingColl}" varStatus="status">
			<c:if test="${status.index > 0}">,</c:if>
			<c:set var="currentPermission" value='N'/>
		   <c:if test="${status.current.status == 'NEW'}">
		 		<c:if test="${param.hasEditPermission == 'true'}">
					<c:set var="currentPermission" value='Y'/>
				</c:if>
			</c:if>			
			{ id:${status.index +1},
				<c:choose>
					<c:when test="${(status.current.currTrackingNumber >= status.current.triggeringNumber) && status.current.status == 'CURRENT'}">'class':"grid_yellow",</c:when>
					<c:when test="${status.current.status == 'EXPIRED'}">'class':"grid_red",</c:when>
				</c:choose>
			 data:[ '${currentPermission}',
				 	'',
				 	'${status.current.hubName}',
				 	'${status.current.batchNumber}',
				 	'${status.current.carrierName}',
				 	'${status.current.trackingNumberPrefix}',
				 	'${status.current.firstTrackingNumber}',
				    '${status.current.lastTrackingNumber}',
				    '${status.current.currTrackingNumber}',
				    '${status.current.status}',
				    <c:choose>
				    	<c:when test="${not empty status.current.checkdigitFunction}">'Y'</c:when>
				    	<c:otherwise>'N'</c:otherwise>
				    </c:choose>	
				    ,
				    '${status.current.hub}']}
			 <c:set var="dataCount" value='${dataCount+1}'/>
		 </c:forEach>
	]};
//-->
</script>

</div> <!-- end of grid div. -->
</c:if>

<c:if test="${empty freightTrackingColl}" >
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
<input name="uAction" id="uAction" value="" type="hidden"/>
<input name="minHeight" id="minHeight" type="hidden" value="50"/>
<input name="hasEditPermission" id="hasEditPermission" value="${param.hasEditPermission}" type="hidden"/>	
<input name="includeExpired" id="includeExpired" value="${param.includeExpired}" type="hidden"/>
<input name="lowBatchesOnly" id="lowBatchesOnly" value="${param.lowBatchesOnly}" type="hidden"/>
<input name="hub" id="hub" value="${param.hub}" type="hidden"/>
<input name="carrierName" id="carrierName" value="${param.carrierName}" type="hidden"/>
</div>

</div> <!-- close of interface -->

</tcmis:form>

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