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

<%--
<script type="text/javascript" src="/js/catalog/manufacturersearch.js"></script>
--%>

<title>
<fmt:message key="label.area"/>
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
	requiredAreaName: "<fmt:message key="errors.required"><fmt:param><fmt:message key="label.areaname"/></fmt:param></fmt:message>",
	requiredBuildingName: "<fmt:message key="errors.required"><fmt:param><fmt:message key="label.buildingname"/></fmt:param></fmt:message>",
	requiredFloorName: "<fmt:message key="errors.required"><fmt:param><fmt:message key="label.floorname"/></fmt:param></fmt:message>",
	requiredRoomName: "<fmt:message key="errors.required"><fmt:param><fmt:message key="label.roomname"/></fmt:param></fmt:message>",
	requiredNewArea: "<fmt:message key="label.pleaseadd"><fmt:param><fmt:message key="label.newarea"/></fmt:param></fmt:message>",
	requiredNewBuilding: "<fmt:message key="label.pleaseadd"><fmt:param><fmt:message key="label.newbuilding"/></fmt:param></fmt:message>",
	requiredNewFloor: "<fmt:message key="label.pleaseadd"><fmt:param><fmt:message key="label.newfloor"/></fmt:param></fmt:message>",
	requiredNewRoom: "<fmt:message key="label.pleaseadd"><fmt:param><fmt:message key="label.newroom"/></fmt:param></fmt:message>",
	selectedRowMsg:"<fmt:message key="label.returnselecteddata"/>",
	duplicateValue:"<fmt:message key="error.duplicatevalue"/>",
	changenotreflect:"<fmt:message key="label.changenotreflect"/>"
	};

var areaConfig = [
	{columnId:"permission", submit:false},
 	{columnId:"updated"},
 	{columnId:"facility", width:10,submit: false, tooltip: true},
	{columnId:"areaName", columnName:'<fmt:message key="label.areaname"/>', width:20, maxlength: 300, type:'hed'},
 	{columnId:"areaDescription", columnName:'<fmt:message key="label.areadescription"/>', width:36, maxlength: 200, type:'hed'},
 	{columnId:"areaId"},
 	{columnId:"facilityId"},
 	{columnId:"companyId"},
	{columnId:"newRow"}
  	];

var buildingConfig = [
      	{columnId:"permission", submit:false},
	{columnId:"updated"},
	{columnId:"area", width:10,submit: false, tooltip: true},
	{columnId:"buildingName", columnName:'<fmt:message key="label.buildingname"/>', width:20, maxlength: 30, type:'hed'},
	{columnId:"buildingDescription", columnName:'<fmt:message key="label.buildingdescription"/>', width:36, maxlength: 200, type:'hed'},
	{columnId:"buildingId"},
	{columnId:"areaId"},
	{columnId:"facilityId"},
	{columnId:"companyId"},
	{columnId:"newRow"}
 	];

var floorConfig = [
	{columnId:"permission", submit:false},
	{columnId:"updated"},
	{columnId:"building", width:10,submit: false, tooltip: true},
	{columnId:"description", columnName:'<fmt:message key="label.floorname"/>', width:12, maxlength: 12, type:'hed'},
	{columnId:"floorId"},
	{columnId:"buildingId"},
	{columnId:"companyId"},
	{columnId:"newRow"}
	];
	
var roomConfig = [
	{columnId:"permission", submit:false},
	{columnId:"updated"},
	{columnId:"floor", width:10, submit:false, tooltip: true},
	{columnId:"roomName", columnName:'<fmt:message key="label.roomname"/>', width:20, maxlength: 30, type:'hed'},
	{columnId:"roomDescription", columnName:'<fmt:message key="label.roomdescription"/>', width:24, maxlength: 200, type:'hed'},
    {columnId:"interior", columnName:'<fmt:message key="label.interior"/>', width:4, type: 'hchstatus', align: "center"},
    {columnId:"roomId"},
	{columnId:"floorId"},
	{columnId:"facilityId"},
	{columnId:"companyId"},
	{columnId:"newRow"}
	];
              	

windowCloseOnEsc = true;
window.onresize = resizeFrames;

function closeAllchildren() 
{ 
	/*if (document.getElementById("uAction").value != "search")*/ {
		try {
			for(var n=0;n<children.length;n++) {
				children[n].closeAllchildren(); 
			}
		} catch(ex)
		{
		}

		if(!window.closed)
			window.close();
	} 
} 

function addNewAreaRow() {
	var id = areaGrid.getRowsNum() + 1;
	var data = ['Y',
		    'Y',
		    '${param.facilityDesc}',
		    '',
		    '',
		    '',
		    '${param.facilityId}',
		    '${personnelBean.companyId}',
  			'Y'
		    ];
	areaGrid.addRow(id, data, id - 1);
	areaGrid.selectRowById(id,false,true,true);
}

function addNewBuildingRow() {
	var id = buildingGrid.getRowsNum() + 1;
	var data = ['Y',
		    'Y',
		    areaName,
		    '',
		    '',
		    '',
		    areaId,
		    '${param.facilityId}',
		    '${personnelBean.companyId}',
  			'Y'
		    ];
	buildingGrid.addRow(id, data, id - 1);
	buildingGrid.selectRowById(id,false,true,true);
}

function addNewFloorRow() {
	var id = floorGrid.getRowsNum() + 1;
	var data = ['Y',
		    'Y',
		    buildingName,
		    '',
		    '',
		    buildingId,
		    '${personnelBean.companyId}',
		    'Y'
		    ];
	floorGrid.addRow(id, data, id - 1);
	floorGrid.selectRowById(id,false,true,true);
}

function addNewRoomRow() {
	var id = roomGrid.getRowsNum() + 1;
	var data = ['Y',
		    'Y',
		    floorName,
		    '',
		    '',
		    true,
            '',
            floorId,
		    '${param.facilityId}',
		    '${personnelBean.companyId}',
  			'Y'
		    ];
	roomGrid.addRow(id, data, id - 1);
	roomGrid.selectRowById(id,false,true,true);
}

function doAreaUpdate() {

	var index = areaGrid.getRowsNum();
	if(index == 0)
	{
		alert(messagesData.requiredNewArea);
		return;
	}
	var checkDupCount = 0;
	var existingCount = 0;
	var checkDup = new Array();
	var existing = new Array();
	for(var i = 0; i < index; i++) {
		
		var rowId = areaGrid.getRowId(i);
		var desc = gridCell(areaGrid,rowId, "areaDescription");
		var name = gridCell(areaGrid,rowId, "areaName");
		if(name.getValue() == "")
		{
			alert(messagesData.requiredNewArea);
			return;
		}
		var  newBuildStr = desc.getValue().replace(/^\s*/, "").replace(/\s*$/, ""); 
		newBuildStr = newBuildStr.replace(/\s{2,}/g, " ");
		gridCell(areaGrid, rowId, "areaDescription").setValue(newBuildStr);
		newBuildStr = name.getValue().replace(/^\s*/, "").replace(/\s*$/, ""); 
		newBuildStr = newBuildStr.replace(/\s{2,}/g, " "); 
		gridCell(areaGrid, rowId, "areaName").setValue(newBuildStr);
		if(gridCell(areaGrid,rowId,'newRow').getValue() == 'Y')
			checkDup[checkDupCount++] = {val:name.getValue(),index:rowId};
		else
			existing[existingCount++] = name.getValue();

	}

	for(var j = 0; j < checkDup.length; j++) {
		var dup = chkDup(checkDup[j].val,existing);
		if(dup == 1) {
			alert(formatMessage(messagesData.duplicateValue,checkDup[j].val));
			areaGrid.selectRowById(checkDup[j].index);
			return;
		}
		 
	}
	alert(formatMessage(messagesData.changenotreflect,opener.document.title));
	$('uAction').value = 'updateAreas';
	areaGrid.parentFormOnSubmit(); //prepare grid for data sending
	windowClose = false;
	document.genericForm.submit();
	return false;
}

function doBuildingUpdate() {
	var index = buildingGrid.getRowsNum();
	if(index == 0) {
		alert(messagesData.requiredNewBuilding);
		return;
	}
	var checkDupCount = 0;
	var existingCount = 0;
	var checkDup = new Array();
	var existing = new Array();
	for(var i = 0; i < index; i++) {
		var rowId = buildingGrid.getRowId(i);
		var desc = gridCell(buildingGrid,rowId, "buildingDescription");
		var name = gridCell(buildingGrid,rowId, "buildingName");
		if(name.getValue() == ""){
			alert(messagesData.requiredBuildingName);
			return;
		}
		var  newBuildStr = desc.getValue().replace(/^\s*/, "").replace(/\s*$/, ""); 
		newBuildStr = newBuildStr.replace(/\s{2,}/g, " "); 
		gridCell(buildingGrid, rowId, "buildingDescription").setValue(newBuildStr);
		newBuildStr = name.getValue().replace(/^\s*/, "").replace(/\s*$/, ""); 
		newBuildStr = newBuildStr.replace(/\s{2,}/g, " "); 
		gridCell(buildingGrid, rowId, "buildingName").setValue(newBuildStr);
		if(gridCell(buildingGrid,rowId,'newRow').getValue() == 'Y')
			checkDup[checkDupCount++] = {val:name.getValue(),index:rowId};
		else
			existing[existingCount++] = name.getValue();

	}

	for(var j = 0; j < checkDup.length; j++) {
		var dup = chkDup(checkDup[j].val,existing);
		if(dup == 1){
			alert(formatMessage(messagesData.duplicateValue,checkDup[j].val));
			buildingGrid.selectRowById(checkDup[j].index);
			return;
		}
	}
	
	alert(formatMessage(messagesData.changenotreflect,opener.document.title));
	$('uAction').value = 'updateBuildings';
	buildingGrid.parentFormOnSubmit(); //prepare grid for data sending
	windowClose = false;
	document.genericForm.submit();
	return false;
}

function doFloorUpdate() {
	var index = floorGrid.getRowsNum();
	if(index == 0) {
		alert(messagesData.requiredNewFloor);
		return;
	}
	var checkDupCount = 0;
	var existingCount = 0;
	var checkDup = new Array();
	var existing = new Array();
	for(var i = 0; i < index; i++) {
		var rowId = floorGrid.getRowId(i);
		var desc = gridCell(floorGrid,rowId, "description");
		if(desc.getValue() == "") {
			alert(messagesData.requiredFloorName);
			return;
		}
		var  newFloorStr = desc.getValue().replace(/^\s*/, "").replace(/\s*$/, ""); 
		newFloorStr = newFloorStr.replace(/\s{2,}/g, " "); 
		desc.setValue(newFloorStr);
		if(gridCell(floorGrid,rowId,'newRow').getValue() == 'Y') {
			checkDup[checkDupCount++] = {val:desc.getValue(),index:rowId};
		}
		else {
			existing[existingCount++] = desc.getValue();
		}
	}

	for(var j = 0; j < checkDup.length; j++) {
		if(chkDup(checkDup[j].val, existing) == 1) {
			alert(formatMessage(messagesData.duplicateValue,checkDup[j].val));
			floorGrid.selectRowById(checkDup[j].index);
			return;
		}
	}
	
	alert(formatMessage(messagesData.changenotreflect,opener.document.title));
	$('uAction').value = 'updateFloors';
	floorGrid.parentFormOnSubmit(); //prepare grid for data sending
	windowClose = false;
	document.genericForm.submit();
	return false;
}

function doRoomUpdate() {
	$('uAction').value = 'updateRooms';
	var index = roomGrid.getRowsNum();
	if(index == 0)
	{
		alert(messagesData.requiredNewRoom);
		return;
	}
	var checkDupCount = 0;
	var existingCount = 0;
	var checkDup = new Array();
	var existing = new Array();
	for(var i = 0; i < index; i++)
	{
		
		var rowId = roomGrid.getRowId(i);
		var desc = gridCell(roomGrid,rowId, "roomDescription");
		var name = gridCell(roomGrid,rowId, "roomName");
		if(name.getValue() == "")
		{
			alert(messagesData.requiredRoomName);
			return;
		}
		var  newRoomStr = desc.getValue().replace(/^\s*/, "").replace(/\s*$/, ""); 
		newRoomStr = newRoomStr.replace(/\s{2,}/g, " "); 
		gridCell(roomGrid, rowId, "roomDescription").setValue(newRoomStr);
		newBuildStr = name.getValue().replace(/^\s*/, "").replace(/\s*$/, ""); 
		newBuildStr = newBuildStr.replace(/\s{2,}/g, " "); 
		gridCell(roomGrid, rowId, "roomName").setValue(newBuildStr);
		if(gridCell(roomGrid,rowId,'newRow').getValue() == 'Y')
			checkDup[checkDupCount++] = {val:name.getValue(),index:rowId};
		else
			existing[existingCount++] = name.getValue();
	}
	
	for(var j = 0; j < checkDup.length; j++)
	{
		var dup = chkDup(checkDup[j].val,existing);
		if(dup == 1)
		{
			alert(formatMessage(messagesData.duplicateValue,checkDup[j].val));
			roomGrid.selectRowById(checkDup[j].index);
			return;
		}
		 
	}

	alert(formatMessage(messagesData.changenotreflect,opener.document.title));
	roomGrid.parentFormOnSubmit(); //prepare grid for data sending
	windowClose = false;
	document.genericForm.submit();
	return false;
}

function chkDup(val, arr) 
{
	var first = 0;
	var upto  = arr.length;
	var high = arr.length - 1;
	var low = 0;
	val = new String(val);
	val = val.toLowerCase();
	
	while (low <= high) {
		mid = parseInt((low + high) / 2)
		element = new String(arr[mid]);
		element = element.toLowerCase();
	   if (element > val) {
		high = mid - 1;
	   } else if (element < val) {
		low = mid + 1;
	   } else {
		return 1;
	   }
	}
	return -1;
}

var buildingDesc;
var areaDesc;
var roomId;
var floorId;
var buildingId;
var areaId;
var roomDesc;
var areaName;
var roomName;
var buildingName;
var interior;
var floorName;

function returnSelected() {
	opener.buildingCallback(${param.rowId}, $v("buildingId"), buildingName, roomId, roomName, $v("areaId"), areaName, interior, floorName);
	try{
		opener.parent.closeTransitWin();
	}
	catch(e){
		opener.closeTransitWin();
	}
	windowClose = false;
	window.close();
}

var windowClose = true;

function myOnUnload() {
	if (windowClose) {
		if (opener.parent) {
			opener.parent.closeTransitWin();
		}
		else {
			opener.closeTransitWin();
		}
	}
}

var selectedBuildingRowId = "";
var selectedAreaRowId = "";
var selectedFloorRowId = "";
var selectedRoomRowId = "";
var preSelect = true;

function onAreaRowSelected(rowId,cellInd) {
	if(preSelect || selectedAreaRowId != rowId)
	{
		$('areaUpdateResultLink').style["display"] = "none";
		$('buildingUpdateLinks').style["display"] = "none";	
		$('floorUpdateLinks').style["display"] = "none";	
		$('roomUpdateLinks').style["display"] = "none";	
		$('buildingUpdateResultLink').style["display"] = "none";
		$('floorUpdateResultLink').style["display"] = "none";
		$('roomUpdateResultLink').style["display"] = "none";
		gridCell(areaGrid,rowId,"updated").setValue("Y");
		areaId = gridCellValue(areaGrid, rowId,'areaId') + "";
		$("areaId").value = areaId;
		areaDesc = gridCellValue(areaGrid, rowId,'areaDescription');
		areaName = gridCellValue(areaGrid, rowId,'areaName');
		if (areaId.length > 0) {
			$('buildingUpdateLinks').style["display"] = "";	
		} 
		else {
			areaId = "none";
			$('buildingUpdateLinks').style["display"] = "none";	
			$('floorUpdateLinks').style["display"] = "none";	
			$('roomUpdateLinks').style["display"] = "none";	
		}
		selectBuildingGrid(areaId,rowId);
	}
	selectedAreaRowId = rowId;
}

function selectBuildingGrid(areaId,rowId) {
	buildingGrid.clearAll();
	floorGrid.clearAll();
	roomGrid.clearAll();
	if (areaId != "none") {
		buildingGrid.parse( buildingData[areaId] ,"json");
	}
	if(preSelect) {
		 if (selectedBuildingRowId > 0) {
			buildingGrid.selectRowById(selectedBuildingRowId,false,true,true);
		 }
		 else if(buildingGrid.getRowsNum() > 0) {
			buildingGrid.selectRowById(1 ,false,true,true);
		 }
	}
	selectedBuildingRowId = "";
	selectedFloorRowId = "";
}

function onBuildingRowSelected(rowId,cellInd) {
	if(preSelect || selectedBuildingRowId != rowId)
	{
		$('roomUpdateLinks').style["display"] = "none";	
		$('floorUpdateLinks').style["display"] = "none";
		$('roomUpdateResultLink').style["display"] = "none";	
		$('floorUpdateResultLink').style["display"] = "none";
		$('buildingUpdateResultLink').style["display"] = "none";
		$('areaUpdateResultLink').style["display"] = "none";
		gridCell(buildingGrid,rowId,"updated").setValue("Y");
		buildingId = gridCellValue(buildingGrid, rowId,'buildingId') + "";
		$("buildingId").value = buildingId;
		buildingDesc = gridCellValue(buildingGrid, rowId,'buildingDescription');
		buildingName = gridCellValue(buildingGrid, rowId,'buildingName');
		if (buildingId.length > 0) {
			$('floorUpdateLinks').style["display"] = "";	
		} 
		else {
			buildingId = "none";
			$('floorUpdateLinks').style["display"] = "none";	
		
		}
		selectFloorGrid(buildingId);
	}
	selectedBuildingRowId = rowId
}

function selectFloorGrid(buildingId) {
	floorGrid.clearAll();
	roomGrid.clearAll();
	if (buildingId != "none") {
		floorGrid.parse( floorData[buildingId] ,"json");
	}
	if(preSelect) {
		 if (selectedFloorRowId > 0) {
			floorGrid.selectRowById(selectedFloorRowId,false,true,true);
		 }
		 else if(floorGrid.getRowsNum() > 0) {
			floorGrid.selectRowById(1 ,false,true,true);
		 }
	}
	selectedFloorRowId = "";

}

function onFloorRowSelected(rowId,cellInd) {
	if(preSelect || selectedFloorRowId != rowId)
	{
		$('roomUpdateLinks').style["display"] = "none";	
		$('roomUpdateResultLink').style["display"] = "none";	
		$('floorUpdateResultLink').style["display"] = "none";	
		$('buildingUpdateResultLink').style["display"] = "none";
		$('areaUpdateResultLink').style["display"] = "none";
		gridCell(floorGrid,rowId,"updated").setValue("Y");
		floorId = gridCellValue(floorGrid, rowId,'floorId') + "";
		$("floorId").value = floorId;
		floorName = gridCellValue(floorGrid, rowId,'description');
		if (floorId.length > 0) {
			$('roomUpdateLinks').style["display"] = "";	
		} 
		else {
			floorId = "none";
			$('roomUpdateLinks').style["display"] = "none";	
		
		}
		selectRoomGrid(floorId);
	}
	selectedFloorRowId = rowId;
}

function selectRoomGrid(floorId) {
	roomGrid.clearAll();
	if (floorId != "none") {
		roomGrid.parse( buildingRoomData[floorId] ,"json");
	}
	if(preSelect) {
		 if (selectedRoomRowId > 0) {
			roomGrid.selectRowById(selectedRoomRowId,false,true,true);		 
		 }
		 else if(roomGrid.getRowsNum() > 0) {
			 	roomGrid.selectRowById(1 ,false,true,true);
		 }
	}
	selectedRoomRowId = "";
}

function onRoomRowSelected(rowId,cellInd) {
	if(preSelect || selectedRoomRowId != rowId)
	{
		gridCell(roomGrid,rowId,"updated").setValue("Y");
		roomId = gridCellValue(roomGrid, rowId,'roomId') + "";
		roomDesc = gridCellValue(roomGrid, rowId,'roomDescription');	
		roomName = gridCellValue(roomGrid, rowId,'roomName');
		if (roomDesc != null && roomDesc.length > 0) {
			roomName = roomName + ' - ' + roomDesc;
		}
		interior = gridCellValue(roomGrid, rowId,'interior') == "true" ? "I" : "E";
		var returnLink = $('areaUpdateResultLink');
		var returnLink2 = $('buildingUpdateResultLink');
		var returnLink3 = $('floorUpdateResultLink');
		var returnLink4 = $('roomUpdateResultLink');
		var text = ' | <a href="#" onclick="returnSelected(); return false;"><fmt:message key="label.return"/> : '+areaName+' \/ <fmt:message key="label.building"/> '+ buildingName+' \/ '+floorName+' <fmt:message key="label.floor"/> \/ <fmt:message key="label.room"/> '+roomName+'</a>';
		returnLink.innerHTML = text;
		returnLink2.innerHTML = text;
		returnLink3.innerHTML = text;
		returnLink4.innerHTML = text;
		if (roomId.length > 0) {
			returnLink.style["display"] = "";
			returnLink2.style["display"] = "";
			returnLink3.style["display"] = "";
			returnLink4.style["display"] = "";
		}
		else {
			returnLink.style["display"] = "none";
			returnLink2.style["display"] = "none";
			returnLink3.style["display"] = "none";
			returnLink4.style["display"] = "none";
		}
	}
	preSelect = false;
	selectedRoomRowId = rowId;
}

var areaGridConfig = {
		divName:'areas',
		beanData:'areaJsonData',
		beanGrid:'areaGrid',
		config:'areaConfig',
		rowSpan:false,
		onRowSelect: onAreaRowSelected,
		submitDefault:true
	};

var buildingGridConfig = {
	divName:'buildings',
	beanData:'buildingJsonData',
	beanGrid:'buildingGrid',
	config:'buildingConfig',
	rowSpan:false,
	onRowSelect: onBuildingRowSelected,
	submitDefault:true
};

var floorGridConfig = {
		divName:'floors',
		beanData:'floorJsonData',
		beanGrid:'floorGrid',
		config:'floorConfig',
		rowSpan:false,
		onRowSelect: onFloorRowSelected,
		submitDefault:true
	};

var roomGridConfig = {
		divName:'rooms',
		beanData:'roomJsonData',
		beanGrid:'roomGrid',
		config:'roomConfig',
		rowSpan:false,
		onRowSelect: onRoomRowSelected,
		submitDefault:true
};


function myOnLoad() {
	initGridWithGlobal(areaGridConfig);
	initGridWithGlobal(buildingGridConfig);
	initGridWithGlobal(floorGridConfig);
	initGridWithGlobal(roomGridConfig);
	//displayNoSearchSecDuration();
	 setResultSize();
}

function select(){
	 // If there was a previously selected area, re select it
	 if (selectedAreaRowId.length > 0) {
		
		setTimeout("areaGrid.selectRowById(selectedAreaRowId ,false,true,true)",50);
	 }
	 else {
		 if(areaGrid.getRowsNum() > 0) {
			setTimeout("areaGrid.selectRowById(1 ,false,true,true)",50);
		 }
		 else {
			 preSelect = false;
		 }
	 }
}

function mySetResultSize(extraReduction) {
	 resizeResultscount=1;

	 try {
		//document.getElementById("transitPage").style["display"] = "none";

	  	var resultGridDiv = document.getElementById("resultGridDiv");
		resultGridDiv.style["display"] = "";

		document.getElementById("buildingUpdateLinks").style["display"] = "";

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

/*This is called to set the grid height.*/
function setGridHeight(resultGridHeight) {
	try {
		$("buildings").style.height = (resultGridHeight * 0.3)-3 + "px";
		$("rooms").style.height = (resultGridHeight * 0.65)-3 + "px";
	}
	catch(ex) {
      		//alert("THis means the grid was not initialized");
	}
}
/*This is called to set the grid width.*/
function setGridWidth() {
	setWindowSizes(); 
	myWidth -= _isIE ? internalWidthIEOffset : internalWidthFFOffset;
    
	try {
		$("buildings").style.width = myWidth + "px";
		$("rooms").style.width = myWidth + "px";
		lastWindowWidth = myWidth;
	}
	catch(ex) {
      	//alert("THis means the grid was not initialized");
	}
}

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="myOnLoad();select()" onunload="myOnUnload();">

<tcmis:form action="/managebuildingrooms.do" onsubmit="return submitOnlyOnce();">
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
		<tr>
			<td>
				<div class="roundcont contentContainer">
					<div class="roundright">
  						<div class="roundtop">
    							<div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  						</div>
  						<div class="roundContent">
    							<div class="boxhead"> <%-- boxhead Begins --%>
      								<div id="areaUpdateLinks" style=""></div> <%-- buildingUpdateLinks Begins --%>
								<a href="javascript:addNewAreaRow()"><fmt:message key="label.newarea"/></a>&nbsp;|
								<a href="javascript:doAreaUpdate()"><fmt:message key="label.updateareas"/></a>
								<span id="areaUpdateResultLink" style="display: none">
								</span>
        								&nbsp;
		    					</div> <%-- boxhead Ends --%>
    							
    							<div class="dataContent">
								<c:set var="dataCount" value='0'/>
								<div id="areas" style="height:104px;display: none;" ></div>
								<script type="text/javascript">
								<!--				
								var areaJsonData = {
								<c:forEach var="list" items="${areas}" varStatus="status">

									rows:[<c:forEach var="area" items="${list.areaList}" varStatus="status">
										{ id:${status.count},
										  data:['Y',
											'N',
											'<tcmis:jsReplace value="${param.facilityDesc}" processMultiLines="true" />',
											'<tcmis:jsReplace value="${area.areaName}" processMultiLines="true" />',
								  			'<tcmis:jsReplace value="${area.areaDescription}" processMultiLines="true" />',
								  			'${area.areaId}',
								  			'${list.facilityId}',
								  			'${list.companyId}',
								  			'N'
								  			]
								  		}<c:if test="${!status.last}">,</c:if> <c:set var="dataCount" value='${dataCount+1}'/>
								  		</c:forEach>
								  		]};
								</c:forEach>
								
								// -->
								</script>
							</div>
							<div class="boxhead"> <%-- boxhead Begins --%>
      								<span id="buildingUpdateLinks" style="display: none"> <%-- buildingUpdateLinks Begins --%>
									<a href="javascript:addNewBuildingRow()"><fmt:message key="label.newbuilding"/></a>
									| <a href="javascript:doBuildingUpdate()"><fmt:message key="label.updatebuildings"/></a>
									<span id="buildingUpdateResultLink" style="display: none">
									</span>
      								 <%-- buildingUpdateLinks Ends --%>
      								 </span>
    							</div> <%-- boxhead Ends --%>
    						
    							<div class="dataContent">
								<c:set var="dataCount" value='0'/>
								
								<div id="buildings" style="height:156px;display: none;" ></div>
								<script type="text/javascript">
								<!--
								var buildingData = new Array();
								
								var buildingJsonData = {
										rows: []
								};
								buildingData["none"] = buildingJsonData;
								var buildingData = new Array();
								<c:forEach var="list" items="${areas}" varStatus="status">
									<c:forEach var="area" items="${list.areaList}" varStatus="status">
									<c:if test="${area.areaId == param.areaId}">selectedAreaRowId = "${status.count}";</c:if>	
										buildingData["${area.areaId}"] = {
										rows:[<c:forEach var="building" items="${area.buildingList}" varStatus="status">
											{ id:${status.count},
											  data:['Y',
												'N',
												'<tcmis:jsReplace value="${area.areaName}" processMultiLines="true" />',
												'<tcmis:jsReplace value="${building.buildingName}" processMultiLines="true" />',
									  			'<tcmis:jsReplace value="${building.buildingDescription}" processMultiLines="true" />',					  
									  			'${building.buildingId}',
												'${area.areaId}',
									  			'${list.facilityId}',
									  			'${list.companyId}',
									  			'N'
									  			]
									  		}<c:if test="${!status.last}">,</c:if> <c:set var="dataCount" value='${dataCount+1}'/>
									 	</c:forEach>
										]};
									
									</c:forEach>
								</c:forEach>
								
								// -->
								</script>
							</div>
							
							<div class="boxhead">
      								<span id="floorUpdateLinks" style="display: none">
										<a href="javascript:addNewFloorRow()"><fmt:message key="label.newfloor"/></a>
										| <a href="javascript:doFloorUpdate()"><fmt:message key="label.updatefloors"/></a>
										<span id="floorUpdateResultLink" style="display: none">
										</span>
      								 </span>
    							</div>
    						
    							<div class="dataContent">
								<c:set var="dataCount" value='0'/>
								
								<div id="floors" style="height:104px;display: none;" ></div>
								<script type="text/javascript">
								<!--
								var floorData = new Array();
								
								var floorJsonData = {
										rows: []
								};
								floorData["none"] = floorJsonData;
								var floorData = new Array();
								<c:forEach var="list" items="${areas}" varStatus="status">
									<c:forEach var="area" items="${list.areaList}" varStatus="status">
										<c:forEach var="building" items="${area.buildingList}" varStatus="status">
											<c:if test="${building.buildingId == param.buildingId}">selectedBuildingRowId = "${status.count}";</c:if>	
											floorData["${building.buildingId}"] = {
											rows:[<c:forEach var="floor" items="${building.floorList}" varStatus="status">
												{ id:${status.count},
												  data:['Y',
													'N',
													'<tcmis:jsReplace value="${building.buildingName}" processMultiLines="true" />',					  
													'<tcmis:jsReplace value="${floor.description}" processMultiLines="true" />',					  
													'${floor.floorId}',
													'${building.buildingId}',
													'${list.companyId}',
													'N'
													]
												}<c:if test="${!status.last}">,</c:if> <c:set var="dataCount" value='${dataCount+1}'/>
											</c:forEach>
											]};
										</c:forEach>
									</c:forEach>
								</c:forEach>
								
								// -->
								</script>
							</div>
							
    					  		<div class="boxhead"> <%-- boxhead Begins --%>
      								<span id="roomUpdateLinks" style="display: none"> <%-- buildingUpdateLinks Begins --%>
    					  			<a href="javascript:addNewRoomRow()"><fmt:message key="label.newroom"/></a>
      								| <a href="javascript:doRoomUpdate()"><fmt:message key="label.updaterooms"/></a>
									<span id="roomUpdateResultLink" style="display: none">
									</span>
      								</span> <%-- buildingUpdateLinks Ends --%>
    							</div> <%-- boxhead Ends --%>

							<div class="dataContent">
								
								<div id="rooms" style="height:180px;display: none;" ></div>
								<script type="text/javascript">
								<!--
								var buildingRoomData = new Array();
								
								var roomJsonData = {
										rows: []
								};
								buildingRoomData["none"] = roomJsonData;
								var buildingRoomData = new Array();
								<c:forEach var="list" items="${areas}" varStatus="status">
									<c:forEach var="area" items="${list.areaList}" varStatus="status">
										<c:forEach var="building" items="${area.buildingList}" varStatus="status">
											<c:forEach var="floor" items="${building.floorList}" varStatus="status">
												buildingRoomData["${floor.floorId}"] = {
													rows: [<c:forEach var="room" items="${floor.roomList}" varStatus="roomStatus">
														{id:${roomStatus.count},
														 data:['Y',
															'N',
															'<tcmis:jsReplace value="${floor.description}" processMultiLines="true" />',
															'<tcmis:jsReplace value="${room.roomName}" processMultiLines="true" />',
											  				'<tcmis:jsReplace value="${room.roomDescription}" processMultiLines="true" />',
                                                             ${room.interior},
                                                             '${room.roomId}',
											  				'${floor.floorId}',
											  				'${list.facilityId}',
											  				'${list.companyId}',
												  			'N'
														]}<c:if test="${!roomStatus.last}">,</c:if>
													</c:forEach>]
												};
												<c:if test="${floor.floorId == param.floorId}">selectedFloorRowId = '${status.count}';</c:if>
												<c:forEach var="room" items="${floor.roomList}" varStatus="roomStatus">
													<c:if test="${room.roomId == param.roomId}">selectedRoomRowId = '${roomStatus.count}';</c:if>
												</c:forEach>
											</c:forEach>
										</c:forEach>
									</c:forEach>
								</c:forEach>
								// -->
								</script>
							</div>
							
							 <%-- result count and time --%>
							 <div id="footer" class="messageBar"></div>
						</div>
						<div class="roundbottom">
							<div class="roundbottomright"> 
								<img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> 
							</div>
						</div>
					</div>
				</div>
			</td>
		</tr>
	</table>
</div>

</div><!-- Result Frame Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
	<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
	<input name="areaId" id="areaId" value="${param.areaId}" type="hidden"/>
	<input name="buildingId" id="buildingId" value="${param.buildingId}" type="hidden"/>
	<input name="floorId" id="floorId" value="${param.floorId}" type="hidden"/>
	<input name="facilityId" id="facilityId" value="${param.facilityId}" type="hidden"/>
	<input name="facilityDesc" id="facilityDesc" value="${param.facilityDesc}" type="hidden"/>
	<input name="rowId" id="rowId" value="${param.rowId}" type="hidden"/>
	<input name="uAction" id="uAction" value="" type="hidden"/>
	<!--This sets the start time for time elapesed.-->
	<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
	<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
	<input name="minHeight" id="minHeight" type="hidden" value="400"/>

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