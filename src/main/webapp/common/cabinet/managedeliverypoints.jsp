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

<!-- These are for the Grid, uncomment if you are going to use the grid -->

<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<script type="text/javascript" src="/js/catalog/manufacturersearch.js"></script>

<title>
<fmt:message key="label.deliverypoint"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages

var children = new Array(); 
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
	waitingforinput:"<fmt:message key="label.waitingforinputfrom"/>",
	area:"<fmt:message key="label.area"/>",
	selectedRowMsg:"<fmt:message key="label.returnselecteddata"/>"
	};

var config = [
      	{columnId:"permission", submit:false},
      	{columnId:"updated"},
	{columnId:"statusBox", columnName:'<fmt:message key="label.active"/>', width:4, type:'hchstatus', submit:false, onChange:'toggleStatus', align:"center"},
	{columnId:"dock", columnName:'<fmt:message key="label.dock"/>', width:10, tooltip: true, submit: false},
	{columnId:"deliveryPointDesc", columnName:'<fmt:message key="label.deliverypoint"/>', attachHeader:'<fmt:message key="label.description"/>', width:16, maxlength: 200, type:'hed'},
	{columnId:"deliveryContact", columnName:'#cspan', attachHeader:'<fmt:message key="label.contact"/>', width:14, maxlength: 80, type:'hed'},
	{columnId:"deliveryContactPhone", columnName:'#cspan', attachHeader:'<fmt:message key="label.phone"/>', width:10, maxlength: 30, type:'hed'},
	{columnId: "areaDesc", columnName:'<fmt:message key="label.area"/>', attachHeader:'<fmt:message key="label.area"/>', width:10, tooltip: true, submit:false},
	{columnId: "buildingDesc", columnName:'#cspan', attachHeader:'<fmt:message key="label.building"/>', width:9, tooltip: true, submit:false},
	{columnId: "roomDesc", columnName:'#cspan', attachHeader:'<fmt:message key="label.room"/>', width:9, tooltip: true, submit:false},
	{columnId: "buildingLookup", columnName:'#cspan', attachHeader:'<fmt:message key="label.lookup"/>', width:4, align:'center', submit:false},
	{columnId:"status"},
	{columnId:"areaId"},
	{columnId:"buildingId"},
	{columnId:"roomId"},
	{columnId:"scrapObsolete"},
	{columnId:"inventoryGroupOverride"},
	{columnId:"deliveryPointLongDesc"},
	{columnId:"deliveryPoint"},
	{columnId:"locationId"},
	{columnId:"facilityId"},
	{columnId:"companyId"}
 	];
	
windowCloseOnEsc = true;
window.onresize = resizeFrames;

function closeAllchildren() { 
	try {
		for(var n=0;n<children.length;n++) {
			try {
				children[n].closeAllchildren(); 
			} catch(ex){}
			children[n].close();
		}
	} catch(ex){}
	children = new Array();
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

function addNewRow() {
	var id = myGrid.getRowsNum() + 1;
	var data = ['Y',
		    'Y',
		    true,
		    '${param.dock}',
	            '',
	            '',
	            '',
	            '',
	            '',
	            '',
	            '<input name="searchForBuilding' + id + '" type="button" value="..." class="lookupBtn" onmouseover="this.className=\'lookupBtn lookupBtnOver\'" onmouseout="this.className=\'lookupBtn\'"  onclick="lookupBuilding(' + id + ');">',
		    'A', 
		    '', //area
		    '', //building
		    '', // room
		    '', // scrap
		    '', // ig override
		    '', // long desc
		    '', // id
		    '${param.locationId}',
		    '${param.facilityId}',
		    '${personnelBean.companyId}'
		    ];
	myGrid.addRow(id, data, id - 1);
	myGrid.selectRowById(id);
}

function doUpdate() {
	$('uAction').value = 'update';
	myGrid.parentFormOnSubmit(); //prepare grid for data sending
	windowClose = false;
	document.genericForm.submit();
	return false;
}

var selectedId;
var selectedDesc;
var windowClose = true;
function myOnUnload() {
	if (windowClose) {
		opener.parent.closeTransitWin();
		closeAllchildren();
	}
}
function returnSelected() {
	opener.deliveryPointCallback(${param.rowId}, selectedId, selectedDesc);
	try{
		opener.parent.closeTransitWin();
	}catch(e){opener.closeTransitWin();}
	windowClose = false;
	window.close();
}

function onRowSelected(rowId,cellInd) {
	gridCell(myGrid,rowId,"updated").setValue("Y");
	selectedId = gridCellValue(myGrid, rowId,'deliveryPoint') + "";
	$("curDeliveryPoint").value = selectedId;
	selectedDesc = gridCellValue(myGrid, rowId,'deliveryPointDesc');
	var returnLink = $('updateResultLink');
	returnLink.innerHTML = ' | <a href="#" onclick="returnSelected(); return false;"><fmt:message key="label.returndeliverypoint"/> : '+selectedDesc+'</a>';
	if (selectedId.length > 0) {
		returnLink.style["display"] = "";
	}
	else {
		returnLink.style["display"] = "none";
	}
}

function lookupBuilding(rowId) {
	showTransitWin(formatMessage(messagesData.waitingforinput, messagesData.area)+"...");
	var loc = "managebuildingrooms.do?uAction=search&facilityId=${param.facilityId}";
	loc += "&facilityDesc=${param.facilityDesc}";
	loc += "&rowId="+rowId;
	loc += "&areaId="+gridCellValue(myGrid, rowId, "areaId");
	loc += "&buildingId="+gridCellValue(myGrid, rowId, "buildingId");
	loc += "&roomId="+gridCellValue(myGrid, rowId, "roomId");
	children[children.length] = openWinGeneric(loc,"Managebuildingrooms","860","675","yes","50","50","20","20","no");
}

var dhxWins = null;
//this function will intialize dhtmlXWindow if it's null
function initializeDhxWins() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}
function closeTransitWin() {
	if (dhxWins != null) {
		if (dhxWins.window("transitDialogWin")) {
			dhxWins.window("transitDialogWin").setModal(false);
			dhxWins.window("transitDialogWin").hide();
		}
	}
}
function showTransitWin(message) {
	if (message != null && message.length > 0) {
		document.getElementById("transitLabel").innerHTML = message;
	}else {
		document.getElementById("transitLabel").innerHTML = messagesData.pleasewait;
	}

	var transitDialogWin = document.getElementById("transitDialogWin");
	transitDialogWin.style.display="";

	initializeDhxWins();
	if (!dhxWins.window("transitDialogWin")) {
		// create window first time
		transitWin = dhxWins.createWindow("transitDialogWin",0,0, 400, 200);
		transitWin.setText("");
		transitWin.clearIcon();  // hide icon
		transitWin.denyResize(); // deny resizing
		transitWin.denyPark();   // deny parking

		transitWin.attachObject("transitDialogWin");
		//transitWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
		transitWin.center();
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		transitWin.setPosition(328, 131);
		transitWin.button("minmax1").hide();
		transitWin.button("park").hide();
		transitWin.button("close").hide();
		transitWin.setModal(true);
	}else{
		// just show
		transitWin.setModal(true);
		dhxWins.window("transitDialogWin").show();
	}
}

function buildingCallback(rowId, buildingId, buildingDesc, roomId, roomDesc, areaId, areaDesc) {
	gridCell(myGrid, rowId, "buildingId").setValue(buildingId);
	gridCell(myGrid, rowId, "buildingDesc").setValue(buildingDesc);
	gridCell(myGrid, rowId, "roomId").setValue(roomId);
	gridCell(myGrid, rowId, "roomDesc").setValue(roomDesc);
	gridCell(myGrid, rowId, "areaId").setValue(areaId);
	gridCell(myGrid, rowId, "areaDesc").setValue(areaDesc);
}

var selectedRowId = "";
function myOnLoad() {
	popupOnLoadWithGrid();

	// If there was a previously selected building, re select it
	 if (selectedRowId.length > 0) {
		// myGrid.selectRowById(selectedRowId ,false,true,true);
		 window.setTimeout( ( function ( grid, rowId ) { return function () { grid.selectRowById(rowId ,false,true,true); }; } )( myGrid, selectedRowId), 200 );
	 }	
}

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="myOnLoad();" onUnload="myOnUnload();">

<tcmis:form action="/managedeliverypoints.do" onsubmit="return submitOnlyOnce();">
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



<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
<div id="resultGridDiv" style="display: none;">
<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br/><br/><br/><fmt:message key="label.pleasewait"/>
  <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/>
</div>
<!-- Transit Page Ends -->
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
	<a href="javascript:addNewRow()"><fmt:message key="label.newdeliverypoint"/></a>&nbsp;|
	<a href="javascript:doUpdate()"><fmt:message key="label.update"/></a>
	<span id="updateResultLink" style="display: none">
	</span>
        &nbsp;
      </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

<div class="dataContent">
<c:set var="dataCount" value='0'/>
<!--  result page section start -->

<div id="deliveryPoint" style="height:400px;display: none;" ></div>
<script type="text/javascript">
<!--

_gridConfig.submitDefault = true;
_gridConfig.divName = 'deliveryPoint';
_gridConfig.rowSpan = false;
_gridConfig.beanGrid = 'myGrid';
_gridConfig.onRowSelect = onRowSelected;

var jsonData = {
	rows:[<c:forEach var="bean" items="${deliveryPoints}" varStatus="status">
		{ id:${status.index +1},
		  <c:if test="${bean.status == 'I'}">'class': 'grid_lightgray',</c:if>			
		  data:['Y',
			'N',
  			<c:choose><c:when test="${bean.status == 'A'}">true</c:when><c:otherwise>false</c:otherwise></c:choose>,
  			'${param.dock}',
  			'<tcmis:jsReplace value="${bean.deliveryPointDesc}" processMultiLines="true" />',
  			'<tcmis:jsReplace value="${bean.deliveryContact}" processMultiLines="true" />',
  			'<tcmis:jsReplace value="${bean.deliveryContactPhone}" processMultiLines="true" />',
  			'<tcmis:jsReplace value="${bean.areaName}" processMultiLines="true" />',
  			'<tcmis:jsReplace value="${bean.buildingName}" processMultiLines="true" />',
  			'<tcmis:jsReplace value="${bean.roomName}" processMultiLines="true" />',
  			'<input name="searchForBuilding${status.count}" type="button" value="..." class="lookupBtn" onmouseover="this.className=\'lookupBtn lookupBtnOver\'" onmouseout="this.className=\'lookupBtn\'"  onclick="lookupBuilding(${status.count});">',
  			'${bean.status}',
 			'${bean.areaId}',
  			'${bean.buildingId}',
  			'${bean.roomId}',
  			'${bean.scrapObsolete}',
  			'${bean.inventoryGroupOverride}',
  			'<tcmis:jsReplace value="${bean.deliveryPointLongDesc}" processMultiLines="true" />',
  			'${bean.deliveryPoint}',
  			'${bean.locationId}',
  			'${bean.facilityId}',
  			'${bean.companyId}'	
  			]
  		}<c:if test="${!status.last}">,</c:if> <c:set var="dataCount" value='${dataCount+1}'/>
 	</c:forEach>
	]};
<c:forEach var="bean" items="${deliveryPoints}" varStatus="status">
	<c:if test="${bean.deliveryPoint == param.curDeliveryPoint}">selectedRowId = '${status.count}';</c:if>
</c:forEach>
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
	<div id="transitDialogWin" class="errorMessages" style="display: none;overflow: auto;">
						<table width="100%" border="0" cellpadding="2" cellspacing="1">
							<tr><td>&nbsp;</td></tr>
							<tr><td>&nbsp;</td></tr>
							<tr><td>&nbsp;</td></tr>
							<tr>
								<td align="center" id="transitLabel">
								</td>
							</tr>
							<tr>
								<td align="center">
									<img src="/images/rel_interstitial_loading.gif" align="middle">
								</td>
							</tr>
						</table>
					</div>
</div><!-- Result Frame Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
	<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
	<input name="dock" id="dock" value="${param.dock}" type="hidden"/>
	<input name="curDeliveryPoint" id="curDeliveryPoint" value="${param.curDeliveryPoint}" type="hidden"/>
	<input name="locationId" id="locationId" value="${param.locationId}" type="hidden"/>
	<input name="facilityDesc" id="facilityDesc" value="${param.facilityDesc}" type="hidden"/>
	<input name="facilityId" id="facilityId" value="${param.facilityId}" type="hidden"/>
	<input name="rowId" id="rowId" value="${param.rowId}" type="hidden"/>
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