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
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
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

<script type="text/javascript" src="/js/catalog/manufacturersearch.js"></script>

<title>
<fmt:message key="label.workareagroup"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={
	alert:"<fmt:message key="label.alert"/>",
	and:"<fmt:message key="label.and"/>",
	recordFound:"<fmt:message key="label.recordFound"/>",
	searchDuration:"<fmt:message key="label.searchDuration"/>",
	minutes:"<fmt:message key="label.minutes"/>",
	seconds:"<fmt:message key="label.seconds"/>",
	validvalues:"<fmt:message key="label.validvalues"/>",
	submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
	itemInteger:"<fmt:message key="error.item.integer"/>",
	selectManufacturer:"<fmt:message key="label.selectedmanufacturer"/>",
	selectedRowMsg:"<fmt:message key="label.returnselecteddata"/>",
	descRequired: "<fmt:message key="errors.required"><fmt:param><fmt:message key="label.workareagroup"/></fmt:param></fmt:message>"
	};

var config = [
      	{columnId:"permission"},
      	{columnId:"updated"},
	{columnId:"facility", columnName:'<fmt:message key="label.facility"/>', width:8, tooltip: 'yes'},
	{columnId:"description", columnName:'<fmt:message key="label.workareagroup"/>', width:30, maxlength: 200, type:'hed'},
	{columnId:"reportingEntityId"},
	{columnId:"facilityId"},
	{columnId:"companyId"}
 	];
	
windowCloseOnEsc = true;
window.onresize = resizeFrames;


function addNewRow() {
	var id = myGrid.getRowsNum() + 1;
	var defaultFacilityName = "";
	if (id > 1) {
		defaultFacilityName = gridCellValue(myGrid, 1, 'facility');
	}
	myGrid.addRow(id, ['Y', 'Y', defaultFacilityName, '', '', '${param.facilityId}', '${personnelBean.companyId}'], id - 1);
	myGrid.selectRowById(id, false,true,true);
}

function doUpdate() {
	var blnUpdate = true;
	$('uAction').value = 'update';
	var index = myGrid.getRowsNum();	
	for(var i = 0; i < index; i++)
	{
		var rowId = myGrid.getRowId(i);
		var applicationDesc = cellValue(rowId, "description");
		if (applicationDesc == "") {			
			setRowClass(rowId,'grid_red');
			blnUpdate = false;			
		}
		var  newStr = applicationDesc.replace(/^\s*/, "").replace(/\s*$/, ""); 
		newStr = newStr.replace(/\s{2,}/g, " "); 
		gridCell(myGrid, rowId, "description").setValue(newStr);
	}
	if (blnUpdate == false) {
		alert(messagesData.descRequired);	
		return;
	}
	myGrid.parentFormOnSubmit(); //prepare grid for data sending
	windowClose = false;
	document.genericForm.submit();
	return false;
}

var wagId;
var wagDesc;

function returnSelected() {
	opener.workAreaGroupCallback(${param.rowId}, wagId, wagDesc);
	windowClose = false;
	window.close();
}
var windowClose = true;
function myOnUnload() {
	if (windowClose) {
		opener.parent.closeTransitWin();
	}
}

function onRowSelected(rowId,cellInd) {
	gridCell(myGrid,rowId,"updated").setValue("Y");
	wagId = gridCellValue(myGrid, rowId,'reportingEntityId') + "";
	wagDesc = gridCellValue(myGrid, rowId,'description');
	var returnLink = $('updateResultLink');
	returnLink.innerHTML = ' | <a href="#" onclick="returnSelected(); return false;"><fmt:message key="label.returnworkareagroup"/> : '+wagDesc+'</a>';
	if (wagId.length > 0) {
		returnLink.style["display"] = "";
	}
	else {
		returnLink.style["display"] = "none";
	}
}

function selectRow()
{
	myGrid.selectRowById(selectedRow ,false,true,true);
}

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="popupOnLoadWithGrid();selectRow();" onUnload="myOnUnload();">

<tcmis:form action="/manageworkareagroups.do" onsubmit="return submitOnlyOnce();">
<div class="interface" id="mainPage" style="">

<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
  ${tcmISError}<br/>
  <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
  </c:forEach>
</div>
<!-- Error Messages Ends -->

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
    showErrorMessage = false;
<c:if test="${!empty tcmISError}">
	showErrorMessage = true;
</c:if>
<c:forEach var="errorMsg" items="${tcmISError}">
	<c:if test="${!empty errorMsg}">
		showErrorMessage = true;
	</c:if>
</c:forEach>

//-->
</script>

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br/><br/><br/><fmt:message key="label.pleasewait"/>
  <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/>
</div>
<!-- Transit Page Ends -->

<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
<div id="resultGridDiv" style="display: none;">
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
      <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
	<a href="javascript:addNewRow()"><fmt:message key="label.newworkareagroup"/></a>&nbsp;|
	<a href="javascript:doUpdate()"><fmt:message key="label.update"/></a>
	<span id="updateResultLink" style="display: none">
	</span>
        &nbsp;
      </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

<div class="dataContent">
<c:set var="dataCount" value='0'/>
<!--  result page section start -->

<div id="workAreaGroup" style="height:400px;display: none;" ></div>
<script type="text/javascript">
<!--

_gridConfig.submitDefault = true;
_gridConfig.divName = 'workAreaGroup';
_gridConfig.rowSpan = false;
_gridConfig.beanGrid = 'myGrid';
_gridConfig.onRowSelect = onRowSelected;
_gridConfig.noSmartRender = true;

var jsonData = {
	rows:[<c:forEach var="bean" items="${workAreaGroups}" varStatus="status">
	<tcmis:jsReplace var="description" value="${bean.description}" processMultiLines="true" />
		{ id:${status.index +1},
		  data:['Y',
			'',
  			'${bean.facilityName}',
  			'${description}',
  			'${bean.reportingEntityId}',
  			'${bean.facilityId}',
  			'${bean.companyId}'
  			]
  		}<c:if test="${!status.last}">,</c:if> <c:set var="dataCount" value='${dataCount+1}'/>
  		<c:if test="${bean.reportingEntityId == param.workAreaGroup}"><c:set var="selectedRow" value='${status.index + 1}'/></c:if>
 	</c:forEach>
	]};
var selectedRow = "${selectedRow}";
// -->
</script>


<!-- Search results end -->
</div>

 <%-- result count and time --%>
 <div id="footer" class="messageBar"></div>

  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td></tr>
</table>
</div>

</div><!-- Result Frame Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
	<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
	<input name="facilityId" id=""facilityId"" value="${param.facilityId}" type="hidden"/>
	<input name="rowId" id=""rowId"" value="${param.rowId}" type="hidden"/>
	<input name="uAction" id="uAction" value="" type="hidden"/>
	<!--This sets the start time for time elapesed.-->
	<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
	<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
	<input name="minHeight" id="minHeight" type="hidden" value="100"/>

</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->
</tcmis:form>

<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">
</div>

<script type="text/javascript">
showUpdateLinks = true;
//-->
</script>

</body>
</html:html>