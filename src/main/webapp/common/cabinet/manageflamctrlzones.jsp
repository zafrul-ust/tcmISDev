<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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

<!-- These are for the Grid-->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
<fmt:message key="label.flammabilitycontrolzone"/>
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
	selectedRowMsg:"<fmt:message key="label.returnselecteddata"/>"
	};

var config = [
      	{columnId:"permission"},
      	{columnId:"updated"},
      	{columnId:"statusBox", columnName:'<fmt:message key="label.active"/>', width:4, type:'hchstatus',submit:false, onChange:'toggleStatus', align:"center"},
		{columnId:"flammabilityControlZoneId", columnName:'<fmt:message key="label.id"/>', width:8},
		{columnId:"flammabilityControlZoneDesc", columnName:'<fmt:message key="label.flammabilitycontrolzone"/>', width:30, maxlength: 100, type:'hed'},
		{columnId:"flashPointLimitOperator", columnName:'<fmt:message key="label.flashpoint"/>', attachHeader:'<fmt:message key="label.operator"/>', width:10, size:7, type:"hcoro", tooltip: true},
	    {columnId:"flashPointLimit", columnName:'#cspan', attachHeader:'<fmt:message key="label.value"/>', width:10, type:'hed', tooltip: true},
	    {columnId:"flashPointLimitUnit", columnName:'#cspan', attachHeader:'<fmt:message key="label.unit"/>', width:10, size:7, type:"hcoro", tooltip: true},
	    {columnId:"amountLimitOperator", columnName:'<fmt:message key="label.amount"/>', attachHeader:'<fmt:message key="label.operator"/>', width:10, size:7, type:"hcoro", tooltip: true},
        {columnId:"amountLimit", columnName:'#cspan', attachHeader:'<fmt:message key="label.value"/>', width:10, type:'hed', tooltip: true},
        {columnId:"amountLimitUnit", columnName:'#cspan', attachHeader:'<fmt:message key="label.unit"/>', width:10, size:7, type:"hcoro", tooltip: true},
		{columnId:"companyId"},
		{columnId:"facilityId"},
		{columnId:"status"}
 	];


//This is the array for type:'hcoro'.
var amountLimitOperator = [{value:'SELECT', text: '<fmt:message key="label.pleaseselect"/>'},
                           {value:'>', text: '>'},
                           {value:'>=', text: '>='}
                           ];

//This is the array for type:'hcoro'.
var flashPointLimitOperator = [{value:'SELECT', text: '<fmt:message key="label.pleaseselect"/>'},
                           {value:'<', text: '<'},
                           {value:'<=', text: '<='}
                           ];

//This is the array for type:'hcoro'.
var flashPointLimitUnit = [{value:'SELECT', text: '<fmt:message key="label.pleaseselect"/>'},
                           {value:'C', text: 'C'},
                           {value:'F', text: 'F'}
                           ];

//This is the array for type:'hcoro'.
var amountLimitUnit = [{value:'SELECT', text: '<fmt:message key="label.pleaseselect"/>'},
                           {value:'lb', text: 'lb'},
                           {value:'gal', text: 'gal'},
                           {value:'kg', text: 'kg'},
                           {value:'liter', text: 'liter'}
                           ];

windowCloseOnEsc = true;
window.onresize = resizeFrames;

function addNewRow() {
	var id = myGrid.getRowsNum() + 1;
	myGrid.addRow(id, ['Y', 'Y', true,'', '', 'SELECT', '', 'SELECT', 'SELECT', '', 'SELECT', '${param.companyId}','${param.facilityId}','A'], id - 1);
	myGrid.selectRowById(id);
}

function doUpdate() {
	if (submitOnlyOnce()) {
		$('uAction').value = 'update';
		myGrid.parentFormOnSubmit(); //prepare grid for data sending
		windowClose = false;
		document.genericForm.submit();
		return false;
	}
}

function toggleStatus(rowId, colId) {
    var checked = $("statusBox" + rowId).checked;
    gridCell(myGrid, rowId, "status").setValue(checked ? "A" : "I");

    if (!checked) {
        myGrid.haasSetRowClass(rowId, "grid_lightgray");
    }
    else {
        var rowIndex=myGrid.getRowIndex(rowId);
        myGrid.haasSetRowClass(rowId, rowIndex % 2 == 0 ? "ev_haas" : "odd_haas");
    }    
}

var flammabilityControlZoneId;
var flammabilityControlZoneDesc;

function returnSelected() {
	opener.flamCtrlZnCallBack(${param.rowId}, flammabilityControlZoneId, flammabilityControlZoneDesc);
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
	flammabilityControlZoneId = gridCellValue(myGrid, rowId,'flammabilityControlZoneId') + "";
	flammabilityControlZoneDesc = gridCellValue(myGrid, rowId,'flammabilityControlZoneDesc');
	var returnLink = $('updateResultLink');
	returnLink.innerHTML = ' | <a href="#" onclick="returnSelected(); return false;"><fmt:message key="label.returnselecteddata"/> : '+flammabilityControlZoneDesc+'</a>';
	if (flammabilityControlZoneId.length > 0) {
		returnLink.style["display"] = "";
	}
	else {
		returnLink.style["display"] = "none";
	}
}

function selectRow() {
	myGrid.selectRowById(selectedRow ,false,true,true);
}

var showUpdateLinks = false;
function setResultSize(extraReduction) {
	 resizeResultscount=1;

	try {
		document.getElementById("transitPage").style["display"] = "none";
		document.getElementById("resultGridDiv").style["display"] = "";

		if (typeof( extraReduction ) != 'undefined') 
			setTimeout('resizeResults('+extraReduction+')',5);
		else
			setTimeout("resizeResults()",5);
	  	setTimeout('resizeResultsCount()',10);
	 }
	 catch (exError){
	 }

	if (showErrorMessage) {
		setTimeout('showErrorMessages()',50); /*Showing error messages if any*/
	}
}

function flammabilitycontrolzoneOnload() {
	initGridWithGlobal(_gridConfig);
	displayNoSearchSecDuration();
 	setResultSize();
 	selectRow();
}

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="flammabilitycontrolzoneOnload();" onUnload="myOnUnload();">

<tcmis:form action="/manageflamctrlzones.do" >
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
      <div id="mainUpdateLinks" style=""> <%-- mainUpdateLinks Begins --%>
	<a href="javascript:addNewRow()"><fmt:message key="label.newflammabilitycontrolzone"/></a>&nbsp;|
	<a href="javascript:doUpdate()"><fmt:message key="label.update"/></a>
	<span id="updateResultLink" style="display: none">
	</span>
        &nbsp;
      </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

<div class="dataContent">

<div id="flammabilitycontrolzone" style="height:400px;display: none;" ></div>
<script type="text/javascript">
<!--

_gridConfig.submitDefault = true;
_gridConfig.divName = 'flammabilitycontrolzone';
_gridConfig.rowSpan = false;
_gridConfig.beanGrid = 'myGrid';
_gridConfig.onRowSelect = onRowSelected;

var jsonData = {
	rows:[<c:forEach var="bean" items="${flammabilitycontrolzones}" varStatus="status">	    
		{ id:${status.count},
			<c:if test="${bean.status == 'I'}">'class': 'grid_lightgray',</c:if>
		  data:['Y',
			'',
			<c:choose><c:when test="${bean.status == 'A'}">true</c:when><c:otherwise>false</c:otherwise></c:choose>,
  			'${bean.flammabilityControlZoneId}',
  			'<tcmis:jsReplace value="${bean.flammabilityControlZoneDesc}"/>',
  			'${bean.flashPointLimitOperator}',
  			'${bean.flashPointLimit}',
  			'${bean.flashPointLimitUnit}',
  			'${bean.amountLimitOperator}',
  			'${bean.amountLimit}',
  			'${bean.amountLimitUnit}',
  			'${bean.companyId}',
  			'${bean.facilityId}',
  			'${bean.status}'
  			]
  		}<c:if test="${!status.last}">,</c:if>
  		<c:if test="${bean.flammabilityControlZoneId == param.flammabilityControlZoneId}"><c:set var="selectedRow" value='${status.count}'/></c:if>
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
	<input name="totalLines" id="totalLines" value="${fn:length(flammabilitycontrolzones)}" type="hidden"/>
	<input name="rowId" id="rowId" value="${param.rowId}" type="hidden"/>
	<input name="flammabilityControlZoneId" id="flammabilityControlZoneId" value="${param.flammabilityControlZoneId}" type="hidden"/>
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