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

<script type="text/javascript" src="/js/client/catalog/showpreviousorderengevaldetail.js"></script>

<title>
<fmt:message key="label.previouslyevaluated"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};


var config = [
  {
  	columnId:"approvalRole",
  	columnName:'<fmt:message key="label.facility"/>',
  	width:10
  },
  {
  	columnId:"status",
  	columnName:'<fmt:message key="label.workarea"/>'
  },
  {
  	columnId:"submitDate",
 	columnName:'<fmt:message key="label.submitdate"/>'
  },
  {
  	columnId:"dateDelivered",
 	columnName:'<fmt:message key="label.datedelivered"/>'
  },
  {
  	columnId:"status",
  	columnName:'<fmt:message key="label.status"/>'
  },
  {
  	columnId:"requestorInfo",
 	columnName:'<fmt:message key="label.reuqestphemail"/>',
	width:15  
  },
  {
  	columnId:"reason",
 	columnName:'<fmt:message key="label.comment"/>',
	width:20
  },
  {
  	columnId:"requestor",
 	columnName:'<fmt:message key="label.requestor"/>',
	width:0
  },
  {
  	columnId:"statusValue",
 	columnName:'<fmt:message key="label.status"/>',
	width:0  
  },
  {
  	columnId:"facilityId",
 	columnName:'<fmt:message key="label.facility"/>',
	width:0
  },
  {
  	columnId:"application",
 	columnName:'<fmt:message key="label.workarea"/>',
	width:0
  },
  {
  	columnId:"prNumber",
 	columnName:'<fmt:message key="label.prnumber"/>',
	width:0
  },
  {
  	columnId:"lineItem",
 	columnName:'<fmt:message key="label.line"/>',
	width:0
  },
  {
  	columnId:"itemId",
 	columnName:'<fmt:message key="label.item"/>',
	width:0 
  }

  ];


function loadpopup() {
	attachObj = $('resultsPage');
	var title =	'<fmt:message key="label.previouslyevaluated"/>';
	var commentAttachObj =	$('evalCommentDailogWin');
	eval('parent.${param.callback}(attachObj,gridConfig,title,commentAttachObj)');
}

var jsonMainData = new Array();
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="loadpopup()">

<div class="interface" id="resultsPage" style="width:650px;height:400px;">
<div class="backGroundContent">

<c:if test="${!empty beanCollection}" >
<div id="HeaderCommonInfo" style="width:100%">
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
		 <tr>
    		<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.item"/>: </td>
    		<td width="90%" class="optionTitleLeft">${beanCollection[0].itemId}</td>
    	</tr>
		 <tr>
    		<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.desc"/>: </td>
    		<td width="90%" class="optionTitleLeft">${beanCollection[0].itemDesc}</td>
    	</tr>
    </table>
</div>
</c:if>

<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty beanCollection}" >
	<div id="previousOrderEvalDetailBean" style="width:100%;height:200px;"></div>
	<!-- Search results start -->
	<script type="text/javascript">
		<!--
		/*Storing the data to be displayed in a JSON object array.*/
		jsonMainData = {
		rows:[
		<c:forEach var="bean" items="${beanCollection}" varStatus="status">
			<c:if test="${status.index > 0}">,</c:if>

			<c:set var="tmpStatus"><fmt:message key="label.insupply"/></c:set>
			<c:set var="tmpStatusValue" value='insupply'/>
			<c:choose>
				<c:when test="${bean.dateDelivered eq null}">
			   	<c:choose>
			 			<c:when test="${bean.releaseDate eq null}">
			         	<c:set var="tmpStatus"><fmt:message key="label.pendingapproval"/></c:set>
			            <c:set var="tmpStatusValue" value='pendingapproval'/>
						</c:when>
			 			<c:otherwise>
			  				<c:set var="tmpStatus"><fmt:message key="label.insupply"/></c:set>
			            <c:set var="tmpStatusValue" value='insupply'/>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
			 	  <c:choose>
			 			<c:when test="${bean.evalCompletedDate eq null}">
			 				<c:set var="tmpStatus"><fmt:message key="label.ongoingeval"/></c:set>
			            <c:set var="tmpStatusValue" value='ongoingeval'/>
						</c:when>
			 			<c:otherwise>
			 				<c:set var="tmpStatus"><fmt:message key="label.completedeval"/></c:set>
			            <c:set var="tmpStatusValue" value='completedeval'/>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>

			{ id:${status.index +1},
			 data:[
			  '<tcmis:jsReplace value="${bean.facilityName}"/>',
			  '<tcmis:jsReplace value="${bean.applicationDesc}"/>',
			  '<fmt:formatDate value="${bean.submitDate}" pattern="${dateFormatPattern}"/>',
			  '<fmt:formatDate value="${bean.dateDelivered}" pattern="${dateFormatPattern}"/>',
			  '${tmpStatus}',
			  '<tcmis:jsReplace value="${bean.requestorName}"/> <tcmis:jsReplace value="${bean.phone}"/> <tcmis:jsReplace value="${bean.email}"/>',
			  '<tcmis:jsReplace value="${bean.evalComment}" processMultiLines="true"/>',
			  '${bean.requestor}',
			  '${tmpStatusValue}',
			  '<tcmis:jsReplace value="${bean.facilityId}"/>',
			  '<tcmis:jsReplace value="${bean.application}"/>',
			  '${bean.prNumber}',
			  '${bean.lineItem}',
			  '${bean.itemId}' 	  
			  ]
			}
			<c:set var="dataCount" value='${dataCount+1}'/>
		 </c:forEach>
		]};
	//-->
	</script>
</c:if>

<c:if test="${!empty beanCollection}" >
<div id="ActionInfo" style="width:100%">
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
		 <tr><td>&nbsp;</td></tr>
		 <tr>
    		<td width="50%" class="optionTitleBoldRight">
				<span id="completedSpan" name="completedSpan" style="display:none">
					<input name="continue1"  id="continue1"  type="button" value="<fmt:message key="label.completedeval"/>" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onClick="showEvalCommentDialog();"/>
				</span>
				<span id="updateSpan" name="updateSpan" style="display:none">
					<input name="updateB"  id="updateB"  type="button" value="<fmt:message key="label.update"/>" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onClick="showEvalCommentDialog();"/>
				</span>
			</td>
    		<td width="50%" class="optionTitleLeft">
				<input name="closeB"  id="closeB"  type="button" value="<fmt:message key="label.close"/>" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onClick="closeOk();"/>
			</td>
    	</tr>
		<tr><td>&nbsp;</td></tr> 
	 </table>
</div>
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
	divName:'previousOrderEvalDetailBean', // the div id to contain the grid.
	beanData:jsonMainData,     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	beanGrid:'previousOrderEvalDetailBeanGrid',     // the grid's name, as in beanGrid.attachEvent...
	config:config,	     // the column config var name, as in var config = { [ columnId:..,columnName...
	rowSpan:false,			 // this page has rowSpan > 1 or not.
	submitDefault:false,    // the fields in grid are defaulted to be submitted or not.
	onRowSelect:previousOrderSelectRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
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

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<input name="personnelId" id="personnelId" value="${personnelBean.personnelId}" type="hidden"/>
</div>
<!-- Hidden elements end -->

<div id="evalCommentDailogWin" class="errorMessages" style="display: none;overflow: auto;">
<table width="100%" border="0" cellpadding="2" cellspacing="1">
<tr>
<td align="center" width="100%">
	<textarea cols="70" rows="5" class="inputBox" name="evalCommentText" id="evalCommentText"></textarea>
</td>
</tr>
<tr>
<td align="center" width="100%">
<span id="popupTitle" style="display:none"><fmt:message key="label.comment"/></span>	
<input name="evalCommentOk"  id="evalCommentOk"  type="button" value="<fmt:message key="label.continue"/>" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onClick="evalCommentOk();"/>
&nbsp;<input name="evalCommentCancel"  id="evalCommentCancel"  type="button" value="<fmt:message key="label.cancel"/>" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onClick="evalCommentCancel();"/>
</td>
</tr>
</table>
</div>

</body>
</html:html>