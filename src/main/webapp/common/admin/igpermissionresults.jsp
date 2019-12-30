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

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support for column type hcal-->
<!--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
-->

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/admin/igpermission.js"></script>

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
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
errors:"<fmt:message key="label.errors"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>"
};

function checkAllAccess() {
var lines = $v('totalLines');
	if( $('chc1').checked ) {
		for(i=1;i<=lines;i++) {
			var cid = 'inventoryGroupAccess'+i;
			if( ! $(cid).disabled && !$(cid).checked) {
				$(cid).checked = true;
				updateHchStatusA(cid);
			}
		}
	}
	else {
		for(i=1;i<=lines;i++) {
			var cid = 'inventoryGroupAccess'+i;
			if( !$(cid).disabled && $(cid).checked) {
				$(cid).checked = false;
				updateHchStatusA(cid);
			}
		}
	}
}

function checkAllAdmin(roleId) {
	var lines = $v('totalLines');
	if( $('checkAll'+roleId).checked ) {
		for(i=1;i<=lines;i++) {
			var cid = "role"+roleId+i;
			if( ! $(cid).disabled && !$(cid).checked) {
				$(cid).checked = true;
				updateHchStatusA(cid);
			}
		}
	}
	else {
		for(i=1;i<=lines;i++) {
			var cid = "role"+roleId+i;
			if( !$(cid).disabled && $(cid).checked) {
				$(cid).checked = false;
				updateHchStatusA(cid);
			}
		}
	}
}


var gridConfig = {
	divName:'UserIgAdmin', // the div id to contain the grid.
	beanData:'jsonData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
	config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
	rowSpan:false,			 // this page has rowSpan > 1 or not.
	submitDefault:true,    // the fields in grid are defaulted to be submitted or not.
	onRowSelect:null,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
	onRightClick:null   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
//	onBeforeSorting:_onBeforeSorting
};

<c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set>
var config = [
  	{columnId:"permission"},
	{columnId:"hubName",columnName:'<fmt:message key="label.hub"/>'},
	{columnId:"inventoryGroupName",columnName:'<tcmis:jsReplace value="${inventorygroup}"/>', type:"ro", width:20, tooltip: true},
	{columnId:"inventoryGroupAccessPermission"},
	{columnId:"inventoryGroupAccess",columnName:'<fmt:message key="label.access"/><br/><input type="checkbox" name="chc1" id="chc1" onclick="checkAllAccess()"/>',
		align:'center',type:"hchstatus",permission:true},
	 <c:set var="adminLabel">
		<fmt:message key="label.admin"/>
	 </c:set>
 	<c:forEach var="HeaderBean" items="${functionalRoles}" varStatus="status">
			{columnId:"role${status.index}Permission"},
 			{columnId:"role${status.index}Original"},
 			{columnId:"role${status.index}Name"},
	</c:forEach>
	 <fmt:bundle basename="com.tcmis.common.resources.VvUserGroup">
	 	<c:forEach var="HeaderBean" items="${functionalRoles}" varStatus="status">
	 			{columnId:"role${status.index}",
	 				    <c:choose>
					    	<c:when test="${status.first}">
					 			columnName:'${adminLabel}',
								attachHeader:'<fmt:message key="${HeaderBean.functionalArea}"/><br/><input type="checkbox" name="checkAll${status.index}" onclick="checkAllAdmin(${status.index})">',
					    	</c:when>
					    	<c:otherwise>
							columnName:'#cspan', 
							attachHeader:'<fmt:message key="${HeaderBean.functionalArea}"/><br/><input type="checkbox" name="checkAll${status.index}" onclick="checkAllAdmin(${status.index})">',
					    	</c:otherwise>
					    </c:choose>
		 			align:'center',type:"hchstatus",width:6,permission:true},
	 	</c:forEach>
	 </fmt:bundle>
	{columnId:"inventoryGroup"},
	{columnId:"inventoryGroupAccessOriginal"},
	{columnId:"companyId"},
	{columnId:"hub"},
	{columnId:"opsEntityId"},
	{columnId:"opsCompanyId"},
	{columnId:"personnelId"}
]; 

function _simpleUpdate(act,val) { 
	  if( window['validateForm'] && !validateForm() ) return false;
	  if( !act ) act = 'uAction';
	  if( !val ) val = 'update';
  $(act).value = val;
	parent.showPleaseWait();
  if(haasGrid) haasGrid.parentFormOnSubmit(); //prepare grid for data sending
  document.genericForm.submit();
  return false;
}

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig)">

<tcmis:form action="/igpermissionresults.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="resultsPage">
<div class="backGroundContent">
<c:set var="dataCount" value='${0}'/>

<div id="UserIgAdmin" style="width:100%;%;height:400px;display: none;" ></div>
<c:if test="${!empty igPermissions}" >
<script type="text/javascript">
<!--

<%--NEW - storing the data to be displayed in the grid in a JSON.--%>
<c:set var="dataCount" value='0'/>

var jsonData = {
        rows:[<c:forEach var="invGroup" items="${igPermissions}" varStatus="status">
		{id: ${status.count},
	         data:[	'Y',
			'${invGroup.hubName}',
			'${invGroup.inventoryGroupName}',
	                '<c:choose><c:when test="${invGroup.userAdmin}">Y</c:when><c:otherwise>N</c:otherwise></c:choose>', <%-- Is the current user an Admin for this inventory Group ? --%>
			${invGroup.accessGranted}, <%-- Does the user in question have access ? --%>
			<c:forEach var="functionalArea" items="${invGroup.functionalAreaAdmin}">
				'<c:choose><c:when test="${functionalArea.userGrantAdmin}">Y</c:when><c:when test="${invGroup.administratingSelf && functionalArea.userAdmin}">Y</c:when><c:otherwise>N</c:otherwise></c:choose>',
				${functionalArea.accessAdmin},
				'${functionalArea.functionalArea}',
			</c:forEach>
			<c:forEach var="functionalArea" items="${invGroup.functionalAreaAdmin}">
				${functionalArea.accessAdmin},
			</c:forEach>
			'${invGroup.inventoryGroup}',
			${invGroup.accessGranted},
			'${invGroup.companyId}',
			'${invGroup.hub}',
			'${invGroup.opsEntityId}',
			'${invGroup.opsCompanyId}',
			'${param.personnelId}'
		]}<c:if test="${!status.last}">,</c:if><c:set var="dataCount" value='${dataCount + 1}'/>
	</c:forEach>
]};

//-->
</script>
</c:if>
</div> <!-- close of backGroundContent -->

<c:if test="${empty igPermissions}" >
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
<input name="uAction" id="uAction" value="update" type="hidden"/>
<input name="personnelId" id="personnelId" value="${param.personnelId}" type="hidden"/>
<input name="companyId" id="companyId" value="${param.companyId}" type="hidden"/>
<input name="fullName" id="fullName" value="${param.fullName}" type="hidden"/>
<input name="opsEntityId" id="opsEntityId" value="${param.opsEntityId}" type="hidden"/>
<input name="hub" id="hub" value="${param.hub}" type="hidden"/>
<input name="inventoryGroup" id="inventoryGroup" value="${param.inventoryGroup}" type="hidden"/>
<input name="updated" id="updated" value="${updated}" type="hidden"/>
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

<!-- permission stuff here --!>
<c:if test="${showUpdateLink eq 'Y'}">
	showUpdateLinks = true;
</c:if>
//-->
</script>

</body>
</html:html>