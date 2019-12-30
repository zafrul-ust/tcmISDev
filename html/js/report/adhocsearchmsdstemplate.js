var listSelected = new Array();
var casHasInputFlag = false;
var listHasInputFlag = false;
function editChemList()
{
	 listSelected = new Array();
	 for(var i = 0; i < listBeanGrid.getRowsNum();i++)
	 {
		//var pos = listBeanGrid.getRowId(i);
	 	listSelected[i] = gridCellValue(listBeanGrid,i,'listId');
	 }
	 children[children.length] =  openWinGeneric("adhocinventoryreport.do?uAction=editChemListLoad&callerId=list","edit_stor_list","800","500","yes");
}

function populateListGrid(selected)
{
	for (var i = 0; i < selected.length; i++)
	{
		var found = false;
		for (var k = 0; k < listBeanGrid.getRowsNum();k++)
		{
			var pos = listBeanGrid.getRowId(k);
			if(selected[i] != null && selected[i].listId == gridCellValue(listBeanGrid,k,'listId'))
			{
				selected[i]=null;
				break;
			}
		}

	}

	for (var l = 0; l < selected.length; l++)
	{
			if(selected[l] != null)
			{
				var j = 0;
				//var rId = new Date();
				var newId = listBeanGrid.getRowsNum();
				var newData = new Array();
				newData[j++] = 'Y';
				newData[j++] = selected[l].listName;
				newData[j++] = 'SELECT';
				newData[j++] = 'SELECT';
				newData[j++] = '';
				newData[j++] = selected[l].listId;
				listBeanGrid.addRow(newId,newData,listBeanGrid.getRowsNum());
				listHasInputFlag = true;
			}

	}
}

function populateCasGrid(selected)
{
	for (var i = 0; i < selected.length; i++)
	{
		var found = false;
		for (var k = 0; k < casBeanGrid.getRowsNum();k++)
		{
			var pos = casBeanGrid.getRowId(k);
			if(selected[i] != null && selected[i].casNum == gridCellValue(casBeanGrid,k,'casNum'))
			{
				selected[i]=null;
				break;
			}
		}

	}

	for (var l = 0; l < selected.length; l++)
	{
		if(selected[l] != null)
		{
			var j = 0;
			//var rId = new Date();
			var newId = casBeanGrid.getRowsNum();
			var newData = new Array();
			newData[j++] = 'Y';
			newData[j++] = selected[l].casNum;
			newData[j++] = selected[l].preferredName;
			newData[j++] = 'SELECT';
			newData[j++] = 'SELECT';
			newData[j++] = '';
			casBeanGrid.addRow(newId,newData,casBeanGrid.getRowsNum());
			casHasInputFlag = true;
		}
	}
}
var casSelected = new Array();
function addCas()
{
	casSelected = new Array();
	 for(var i = 0; i < casBeanGrid.getRowsNum();i++)
	 {
		//var pos = casBeanGrid.getRowId(i);
		casSelected[i] = gridCellValue(casBeanGrid,i,'casNum');
	 }
	var loc = "casnumbersearchmultiselectmain.do?noNew=Y&loginNeeded=N";
	 children[children.length] = openWinGeneric(loc,"cassearch","650","500","yes","50","50","20","20","no");
}

function onListRightClick(rowId)
{
	listSelectedRowId = rowId;
	toggleContextMenu('rightClickListRemove');
}

function onCasRightClick(rowId)
{
	casSelectedRowId = rowId;
	toggleContextMenu('rightClickCasRemove');
}

function onStorRightClick(rowId)
{
	storSelectedRowId = rowId;
	toggleContextMenu('rightClickStorRemove');
}

function delStor()
{
	storBeanGrid.deleteRow(storSelectedRowId);
}

function delCas()
{
	casBeanGrid.deleteRow(casSelectedRowId);
	if(casBeanGrid.getRowsNum() < 1)
		casHasInputFlag = false;
}

function delList()
{
	listBeanGrid.deleteRow(listSelectedRowId);
	if(listBeanGrid.getRowsNum() < 1)
		listHasInputFlag = false;
}

function validateMsdsSearch()
{
    var result = true;
    var errorMsg = "";
    var specificErrorMsg = "";

	 var ph = $v("ph");
	    if (!isFloat(ph,true) || (ph.length!= 0 && ph > 14*1) || (ph.length!= 0 &&ph < 0*1))
	    {
	        errorMsg += "\n"+messagesDataTemplate.ph;
	        result = false;
	    }

	    var flashPoint = $v("flashPoint");
	    if (!isFloat(flashPoint,true))
	    {
	        errorMsg += "\n"+messagesDataTemplate.flashpoint;
	        result = false;
	    }

	    var vaporPressure = $v("vaporPressure");
	    if (!isFloat(vaporPressure,true))
	    {
	        errorMsg += "\n"+messagesDataTemplate.vaporpressure;
	        result = false;
	    }else if (vaporPressure != '' && $v("vaporPressureUnit").length == 0) {
                errorMsg += "\n"+messagesDataTemplate.vaporpressureunit;
	            result = false;
        }

	    var vocPercent = $v("vocPercent");
	    if (!isFloat(vocPercent,true))
	    {
	        errorMsg += "\n"+messagesDataTemplate.voc;
	        result = false;
	    }

	    var vocLwesPercent = $v("vocLwesPercent");
	    if (!isFloat(vocLwesPercent,true))
	    {
	        errorMsg += "\n"+messagesDataTemplate.vocLwesPercent;
	        result = false;
	    }

	    var solidsPercent = $v("solidsPercent");
	    if (!isFloat(solidsPercent,true))
	    {
	        errorMsg += "\n"+messagesDataTemplate.solids;
	        result = false;
	    }

	 	var health = $v("health");
	    if (!isInteger(health,true))
	    {
	        errorMsg += "\n"+messagesDataTemplate.nfpa+":"+messagesDataTemplate.health;
	        result = false;
	    }

	    var flammability = $v("flammability");
	    if (!isInteger(flammability,true))
	    {
	        errorMsg += "\n"+messagesDataTemplate.nfpa+":"+messagesDataTemplate.flammability;
	        result = false;
	    }

	    var reactivity = $v("reactivity");
	    if (!isInteger(reactivity,true))
	    {
	        errorMsg += "\n"+messagesDataTemplate.nfpa+":"+messagesDataTemplate.reactivity;
	        result = false;
	    }

	    var hmisHealth = $v("hmisHealth");
	    if (!isInteger(hmisHealth,true))
	    {
	        errorMsg += "\n"+messagesDataTemplate.hmis+":"+messagesDataTemplate.health;
	        result = false;
	    }

	    var hmisFlammability = $v("hmisFlammability");
	    if (!isInteger(hmisFlammability,true))
	    {
	        errorMsg += "\n"+messagesDataTemplate.hmis+":"+messagesDataTemplate.flammability;
	        result = false;
	    }

	    var hmisReactivity = $v("hmisReactivity");
	    if (!isInteger(hmisReactivity,true))
	    {
	        errorMsg += "\n"+messagesDataTemplate.hmis+":"+messagesDataTemplate.reactivity;
	        result = false;
	    }


	    if ($("manufacturer").className == "inputBox red")
	    {
	        errorMsg += "\n"+messagesDataTemplate.manufacturer;
	        result = false;
	    }

		/*for(var i = 0; i < listBeanGrid.getRowsNum();i++)
		 {
			var pos = listBeanGrid.getRowId(i);
		 	listSelected[i] = gridCellValue(listBeanGrid,pos,'listId');
		 }*/

		if(result == false) {
	        if (specificErrorMsg.length > 0) {
	            if (errorMsg.length > 0) {
	                alert(specificErrorMsg+"\n"+messagesDataTemplate.validvalues+"\n"+errorMsg);
	            }else {
	                alert(specificErrorMsg);
	            }
	        }else {
	            alert(messagesDataTemplate.validvalues+"\n"+errorMsg);
	        }
	    }
	    return result;
}

function storageAreaGridMod(type,val)
{
	switch(type)
	{
		case 'facility':
			var size = storBeanGrid.getRowsNum();
			for(var i = size - 1; i >= 0;i--)
			{
				var pos = storBeanGrid.getRowId(i);
				if(gridCellValue(storBeanGrid,pos,'facilityId') !=  val)
					storBeanGrid.deleteRow(pos);
			}
		break;
		case 'area':
			var size = storBeanGrid.getRowsNum();
			for(var i = size - 1; i >= 0;i--)
			{
				var pos = storBeanGrid.getRowId(i);
				if(gridCellValue(storBeanGrid,pos,'areaId') !=  val)
					storBeanGrid.deleteRow(pos);
			}
		break;
		case 'building':
			var size = storBeanGrid.getRowsNum();
			for(var i = size - 1; i >= 0;i--)
			{
				var pos = storBeanGrid.getRowId(i);
				if(gridCellValue(storBeanGrid,pos,'buildingId') !=  val)
					storBeanGrid.deleteRow(pos);
			}
		break;
		case 'floor':
			var size = storBeanGrid.getRowsNum();
			for(var i = size - 1; i >= 0;i--)
			{
				var pos = storBeanGrid.getRowId(i);
				if(gridCellValue(storBeanGrid,pos,'floorId') !=  val)
					storBeanGrid.deleteRow(pos);
			}
		break;

		default:
			var size = storBeanGrid.getRowsNum();
			for(var i = size - 1; i >= 0;i--)
			{
				var pos = storBeanGrid.getRowId(i);
				storBeanGrid.deleteRow(pos);
			}
		break;
	}
}

function validateGrid()
{
	gridSubmit = '';
	gridDesc = '';
	var grid = null;
	var isCasGrid = true;
	if($('casNos').checked == true)
	{
		$('gridType').value = 'cas';
		grid = casBeanGrid;
		var ops = document.getElementById('chemicalFieldList');
		if(ops != null && ops != undefined)
			for ( var i = ops.length - 1; i >= 0; i--) {
				ops[i] = null;
			}
	}
	else
	{
		isCasGrid = false;
		$('gridType').value = 'list';
		grid = listBeanGrid;
		var chemicalFieldList = $('chemicalFieldList');
		if(chemicalFieldList != null && chemicalFieldList != undefined)
			selectAllItems(chemicalFieldList);
	}

	var gridNameVariable = '';
	if(isCasGrid)
	{
		gridNameVariable = 'cas';
	}
	else
	{
		gridNameVariable = 'list';
	}

	for (var k = 0; k < grid.getRowsNum();k++)
	{
		var pos = grid.getRowId(k);
		var val = '';
		if(isCasGrid)
		{
			gridDesc += gridCellValue(grid,pos, 'casNum') + '#@&' + gridCellValue(grid,pos, 'chemicalName') + '&@#';
		}
		else
		{
			gridDesc += gridCellValue(grid,pos, 'listName') + '&@#';
		}
		
		$('gridDesc').value = gridDesc;  

		con = gridCellValue(grid,pos, gridNameVariable + 'Constraint');
		op = gridCellValue(grid,pos, gridNameVariable + 'Operator');
		val = gridCellValue(grid,pos,gridNameVariable + 'Value');

		if(op == 'is null' || op == '> threshold' || op == '>= threshold')
		{
			if(con == 'SELECT')
			{
				validVals = false;
				alert(messagesDataTemplate.constraintsoperators);
				return false;
			}
			else
			{
				if(isCasGrid)
					gridSubmit += gridCellValue(grid,pos,'casNum') + ' ';
				else
					gridSubmit += gridCellValue(grid,pos,'listId') + '|';
				gridSubmit += con + ' ';
				gridSubmit += op;
				if(k != grid.getRowsNum() - 1)
					gridSubmit += ';';
			}
		}
		else if(val != '')
		{
			if(!isFloat(val,false) || val < 0 )
			{
				validVals = false;
				alert(messagesDataTemplate.decimalgreaterthanzero);
				return false;
				//break;
			}
			else if(con == 'SELECT' || op == 'SELECT')
			{
				validVals = false;
				alert(messagesDataTemplate.decimalgreaterthanzero);
				return false;
				//break;
			}
			else
			{
				if(isCasGrid)
					gridSubmit += gridCellValue(grid,pos,'casNum') + ' ';
				else
					gridSubmit += gridCellValue(grid,pos,'listId') + '|';
				gridSubmit += con + ' ';
				gridSubmit += op + ' ' + val;
				if(k != grid.getRowsNum() - 1)
						gridSubmit += ';';

			}
		}
		else
		{
			if(isCasGrid)
				gridSubmit += gridCellValue(grid,pos,'casNum');
			else
				gridSubmit += gridCellValue(grid,pos,'listId');

			if(k != grid.getRowsNum() - 1)
				gridSubmit += ';';
		}


	}

	if(typeof(storBeanGrid) != 'undefined' && $("storageAreaId").value == '')
	{
		var storageAreaIdString = '';
		var storageAreaDescString = '';
		for (var l = 0; l < storBeanGrid.getRowsNum();l++)
		{
			var pos = storBeanGrid.getRowId(l);
			storageAreaDescString +=  gridCellValue(storBeanGrid,pos,'storageAreaDesc') + '&@#';
			if(l == 0)
				storageAreaIdString += gridCellValue(storBeanGrid,pos,'storageAreaId');
			else
				storageAreaIdString +=  "|" + gridCellValue(storBeanGrid,pos,'storageAreaId');
		}
		$("storageAreaId").value = storageAreaIdString;
		$("storageAreaDesc").value = storageAreaDescString;
	}

	var searchField = $("searchField");
	$("searchFieldDesc").value = searchField.options[searchField.selectedIndex].text;
	var matchType = $("matchType");
	$("matchTypeDesc").value = matchType.options[matchType.selectedIndex].text;
	var specificHazard = $("specificHazard");
	$("specificHazardDesc").value = specificHazard.options[specificHazard.selectedIndex].text;
	var personalProtection = $("personalProtection");
	$("personalProtectionDesc").value = personalProtection.options[personalProtection.selectedIndex].text;
	$("mfgDesc").value = $("mfgId").value;
	return true;
}

function onListLoadShowVal()
{
	for (var k = 0; k < listBeanGrid.getRowsNum();k++)
	{
		var pos = listBeanGrid.getRowId(k);
		var operator = $v('listOperator' + k);
		if(operator == 'is null' || operator == '> threshold' || operator == '>= threshold')
		{
			var val = $('listValue' + pos);
			val.style['display'] = 'none';
		}
	}
}

function onCasLoadShowVal()
{
	for (var k = 0; k < casBeanGrid.getRowsNum();k++)
	{
		var pos = casBeanGrid.getRowId(k);
		var operator = $v('casOperator' + pos);
		if(operator == 'is null' || operator == '> threshold' || operator == '>= threshold')
		{
			var val = $('casValue' + pos);
			val.style['display'] = 'none';
		}
	}
}

function listRoSel(rowId)
{
	listSelectedRowId = rowId;
}

function casRoSel(rowId)
{
	casSelectedRowId = rowId;
}

function listOptChange()
{
	var operator = $v('listOperator' + listSelectedRowId);
	if(operator == 'is null' || operator == '> threshold' || operator == '>= threshold')
	{
		listBeanGrid.cells(listSelectedRowId, listBeanGrid.getColIndexById("listValue")).setValue("");
		var val = $('listValue' + listSelectedRowId);
		val.value = '';
		val.style['display'] = 'none';
	}
	else
	{
		var val = $('listValue' + listSelectedRowId);
		val.style['display'] = '';
	}
}

function casOptChange()
{
	var operator = $v('casOperator' + casSelectedRowId);
	if(operator == 'is null' || operator == '> threshold' || operator == '>= threshold')
	{
		casBeanGrid.cells(casSelectedRowId, casBeanGrid.getColIndexById("casValue")).setValue("");
		var val = $('casValue' + casSelectedRowId);
		val.value = '';
		val.style['display'] = 'none';
	}
	else
	{
		var val = $('casValue' + casSelectedRowId);
		val.style['display'] = '';
	}
}

/*todo I don't think this is use so I am blocking it out for now.
function popStorageAreas(reportType)
{

if($v("facilityId") == "All") {
	alert(messagesData.pleaseselect.replace('{0}',messagesData.facility));
	return false;
}

var idString = '';
var rowNum = storBeanGrid.getRowsNum();
if(rowNum > 0) {
	for (var p = 0; p < rowNum; p ++) {
		var pos = storBeanGrid.getRowId(p);
		if(idString == '')
			idString = gridCellValue(storBeanGrid,pos,"storageAreaId");
		else
			idString += "|"+gridCellValue(storBeanGrid,pos,"storageAreaId");
	}
}
else {
	idString = '';
}

var loc = "storagearea.do?reportType="+reportType+"&StorageAreaIdString="+idString;
if($v("facilityId") != 'All')
	loc += "&facilityId="+$v("facilityId");
if($v("areaId") != 'All')
	loc += "&areaId="+$v("areaId");
if($v("buildingId") != 'All')
	loc += "&buildingId="+$v("buildingId");
if($v("floorId") != 'All')
	loc += "&floorId="+$v("floorId");

children[children.length] = openWinGeneric(loc,'StorageArea',"720","550","yes","50","50","20","20","no");
}
*/

function showOrHideMsdsNoSpan() {
	/*
		if($v("searchField") == 'customer_msds_number')
			$("msdsNoSpan").style.display = "";
		else
			$("msdsNoSpan").style.display = "none";

		try {
			hideTooltip('hiddenDesc');
		} catch(ex) {}
	*/
		if($v("searchField") == 'item_id' || $v("searchField") == 'material_id' || $v("searchField") == 'customer_msds_number')
			$("matchType").value = 'in csv list';
		else
			$("matchType").value = 'like';
	}

function openMsdsNoTextArea(v)
{
	if(v == "create list")
	{
		children[children.length] = openWinGeneric("advancedmsdsviewermain.do?uAction=showtextarea","copypasteformattextarea","325","370","yes","50","50","20","20","no");
		$('matchType').value = 'in csv list';
	}
}

function setStorageAreas(idString)
{
	var idArray=idString.split("|");

	// Set up the displayed report fields
	for(var i=storBeanGrid.getRowsNum();i >= 0;i--){
		storBeanGrid.deleteRow(i);
	}

	var storageAreaIdString = "";
	var storageAreaDescString = "";
	for ( var i=0; i < idArray.length; i++) {
  		var fieldInfo = idArray[i].split(";");
  		var j = 0;
  		addedRowId = storBeanGrid.getRowsNum();
		var newData = new Array();
		newData[j++] = fieldInfo[1];
		newData[j++] = fieldInfo[0];
		newData[j++] = fieldInfo[2];
		newData[j++] = fieldInfo[3];
		newData[j++] = fieldInfo[4];
		newData[j++] = fieldInfo[5];
	    var thisrow = storBeanGrid.addRow(addedRowId,newData,addedRowId);

	    storageAreaIdString += "|" + fieldInfo[0];
	    storageAreaDescString += fieldInfo[1] + '&@#';

	}

	$("storageAreaId").value = storageAreaIdString.substring(1);
	$("storageAreaDesc").value = storageAreaDescString;
}


// I put try catch here because I got javascript error on IE
// Not sure why we need this here. -Cindy
function rightClick()
{
	try {
		var e = window.event; 
		if(e != null && e.button)
		{
			if( e.target.parentElement.className.indexOf('haas') == -1 && e.target.parentElement.parentElement.className.indexOf('haas') == -1)
				toggleContextMenu(null);
		}
	} catch(ex) {}
}
	
function addMouseEvent() {
		addEvent(document,'mousedown',rightClick,false);
}