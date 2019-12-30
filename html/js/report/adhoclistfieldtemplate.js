var listSelected = new Array();
var listHasInputFlag = false;
var casHasInputFlag = false;
var listFormatSelected = new Array();

function editChemList() {
	 listSelected = new Array();
	 var i=0;
	 var ifound=0;
	 while (ifound < listBeanGrid.getRowsNum()) {
		 var pos = listBeanGrid.getRowId(i);
		 if (listBeanGrid.isItemExists(pos)) {			
			listSelected[ifound] = gridCellValue(listBeanGrid, pos, 'listId');
			ifound++;
		 }
		 i++;
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
			if(selected[i] != null && selected[i].listId == gridCellValue(listBeanGrid,pos,'listId'))
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
			//If found, this should be removed first as it is the last entry (TriggersCompositionThreshold)
			if(checkHasAmountThreshold()) {
				tempListConstraint.pop();//removes the last entry
			}
			//remove this after the previous one as it is the second last entry (TriggersThreshold)
			if(checkHasThreshold()) {
				tempListConstraint.pop();//removes the second last entry				
			}			
			//while adding, add the triggersthreshold first	
			if(selected[l].hasThreshold == 'Y' && $v("supportTriggersThreshold") == 'Y') {
				tempListConstraint.push({value:"TriggersThreshold", text:messagesListDataTemplate.triggersthreshold});					
			}
			//add the triggerscompositionthreshold after the triggersthreshold
			if(selected[l].hasAmountThreshold == 'Y' && $v("supportTriggersThreshold") == 'Y') {
				tempListConstraint.push({value:"TriggersCompositionThreshold", text:messagesListDataTemplate.triggerscompositionthreshold});					
			}
			
			var j = 0;
			var newId = listBeanGrid.getRowsNum();
			var newData = new Array();
			newData[j++] = 'Y';
			newData[j++] = selected[l].listName;
			newData[j++] = '';
			newData[j++] = '';
			newData[j++] = 'SELECT';
			newData[j++] = '';
			newData[j++] = selected[l].listId;
			newData[j++] = '';
			newData[j++] = '';
			newData[j++] = selected[l].hasThreshold;
			newData[j++] = selected[l].hasAmountThreshold;
			var nextId = newId + 1;
            while (listBeanGrid.doesRowExist(nextId)) 
            	nextId++;            
            listConstraint[nextId]=tempListConstraint;
            listBeanGrid.addRow(nextId,newData,listBeanGrid.getRowsNum());			
			addLookupToRow(listBeanGrid, nextId);	
			//hide the operator and value fields on first load since 'SElect' value is not present in contraint drop down anymore.
			var oper = $('listOperator' + nextId);
			oper.style['display'] = 'none';
			var val = $('listValue' + nextId);
			val.style['display'] = 'none';
		}
	}	
	if (listBeanGrid.getRowsNum() > 0)
		listHasInputFlag = true;
	else
		listHasInputFlag = false;	
}

function checkHasThreshold() {
	var found = false;
	for (var i = 0; i < tempListConstraint.length; i++) {
		if ("TriggersThreshold" == tempListConstraint[i].value) 
			found = true;
	}
	return found;
}

function checkHasAmountThreshold() {
	var found = false;
	for (var i = 0; i < tempListConstraint.length; i++) {
		if ("TriggersCompositionThreshold" == tempListConstraint[i].value) 
			found = true;
	}
	return found;
}


function populateCasGrid(selected)
{
	for (var i = 0; i < selected.length; i++)
	{
		var found = false;
		for (var k = 0; k < casBeanGrid.getRowsNum();k++)
		{
			var pos = casBeanGrid.getRowId(k);
			if(selected[i] != null && selected[i].casNum == gridCellValue(casBeanGrid,pos,'casNum'))
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
			var newId = casBeanGrid.getRowsNum();
			var newData = new Array();
			newData[j++] = 'Y';
			newData[j++] = selected[l].casNum;
			newData[j++] = selected[l].preferredName;
			newData[j++] = 'SELECT';
			newData[j++] = 'SELECT';
			newData[j++] = '';
			var nextId = newId + 1;
            while (casBeanGrid.doesRowExist(nextId)) 
            	nextId++;            
			casBeanGrid.addRow(nextId,newData,casBeanGrid.getRowsNum());
		}
	}
	
	if(casBeanGrid.getRowsNum() > 0)
		casHasInputFlag = true;
	else
		casHasInputFlag = false;
}

var casSelected = new Array();
function addCas()
{
	casSelected = new Array();	
	 var ifound=0;
	 var i=0;
	 while (ifound < casBeanGrid.getRowsNum()) {
		 var pos = casBeanGrid.getRowId(i);
		 if (casBeanGrid.isItemExists(pos)) {
			casSelected[ifound] = gridCellValue(casBeanGrid,pos,'casNum');
			ifound++;
		 }
		 i++;
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
	if(casBeanGrid.getRowsNum() > 0)
		casHasInputFlag = true;
	else
		casHasInputFlag = false;
}

function delList()
{
	listBeanGrid.deleteRow(listSelectedRowId);
	if(listBeanGrid.getRowsNum() > 0)
		listHasInputFlag = true;
	else
		listHasInputFlag = false;	
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
	listFormat = '';
	chemicalFieldListId = '';
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
			listFormat += gridCellValue (grid, pos, 'listFormat') + '|';			
			chemicalFieldListId += gridCellValue (grid, pos, 'chemicalFieldX') + '|';			
		}

		con = gridCellValue(grid,pos, gridNameVariable + 'Constraint');
		op = gridCellValue(grid,pos, gridNameVariable + 'Operator');
		val = gridCellValue(grid,pos,gridNameVariable + 'Value');
				
		if (con != 'None' && con != 'OnList' && con != 'NotOnList' && con != 'TriggersThreshold' 
			&& con!= 'TriggersCompositionThreshold' && con != 'SELECT')
		{
			if (op == 'SELECT') {
				alert(messagesListDataTemplate.operatorsmissing);
				return false;
			} else if (op != 'is null' && op != 'notlisted' && op != 'trace' && op != 'SELECT') {				
				if(val == '') {
					alert(messagesListDataTemplate.valuesmissing);
					return false;
				}
			}
		}
		var amtThres = '';
		if(con == 'None' || con == 'OnList' || con == 'NotOnList' || con == 'TriggersThreshold' || con == 'TriggersCompositionThreshold')
		{
			if(isCasGrid) {
				gridSubmit += gridCellValue(grid,pos,'casNum') + ' ';
			} else {
				gridSubmit += gridCellValue(grid,pos,'listId') + '|';
				amtThres = gridCellValue(listBeanGrid, pos, 'hasAmountThreshold');
			}				
			gridSubmit += con;
			
			if (con == 'TriggersThreshold' && amtThres == 'Y') {
				if(val != '' && (!isFloat(val,false) || val < 0 || val > 100 ))
				{
					validVals = false;
					alert(messagesListDataTemplate.decimalbetweenzeroand100);
					return false;
					//break;
				}
				gridSubmit += ' ' + val;	
			}
			
			if(k != grid.getRowsNum() - 1)
				gridSubmit += ';';
			
		}
		else if(op == 'is null' || op == 'notlisted' || op == 'trace')
		{
			if(con == 'SELECT')
			{
				validVals = false;
				alert(messagesListDataTemplate.constraintsoperators);
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
				alert(messagesListDataTemplate.decimalgreaterthanzero);
				return false;
				//break;
			}
			else if(con == 'SELECT' || op == 'SELECT')
			{
				validVals = false;
				alert(messagesListDataTemplate.constraintsoperators);
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

	$('gridSubmit').value = gridSubmit;
	$('gridDesc').value = gridDesc;
	$('chemicalFieldListId').value = chemicalFieldListId;
	$('listFormat').value = listFormat;
		
	return true;
}

function onListLoadShowVal()
{
	for (var k = 0; k < listBeanGrid.getRowsNum();k++)
	{
		var pos = listBeanGrid.getRowId(k);
		var constraint = $v('listConstraint' + pos);
		var hasAmtThreshold = gridCellValue(listBeanGrid,pos,'hasAmountThreshold')
		var noopflag = false;
		if(constraint == 'None' || constraint == 'OnList' || constraint == 'NotOnList' || constraint == 'TriggersCompositionThreshold') {
			var oper = $('listOperator' + pos);
			oper.style['display'] = 'none';			
			var val = $('listValue' + pos);
			val.style['display'] = 'none';		
			noopflag = true;			
		} else if (constraint == 'TriggersThreshold') {
			if (hasAmtThreshold == 'Y') {
				var oper = $('listOperator' + pos);
				oper.style['display'] = 'none';
				var val = $('listValue' + pos);
				val.style['display'] = '';	
			} else {
				var oper = $('listOperator' + pos);
				oper.style['display'] = 'none';			
				var val = $('listValue' + pos);
				val.style['display'] = 'none';		
			}
			noopflag = true;
		}
		
		if (!noopflag) 
		{
			var operator = $v('listOperator' + pos);
			if(operator == 'notlisted' || operator == 'trace' )
			{
				var val = $('listValue' + pos);
				val.style['display'] = 'none';
			}
		}
	}
}

function onCasLoadShowVal()
{
	for (var k = 0; k < casBeanGrid.getRowsNum();k++)
	{
		var pos = casBeanGrid.getRowId(k);
		var operator = $v('casOperator' + pos);
		if(operator == 'is null')
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

function listConstraintChange() 
{	
	var constraint = $v('listConstraint' + listSelectedRowId);
	var hasAmountThreshold = gridCellValue(listBeanGrid, listSelectedRowId, 'hasAmountThreshold');	
	if(constraint == 'None' || constraint == 'OnList' || constraint == 'NotOnList' 
		|| (constraint == 'TriggersThreshold' && hasAmountThreshold == 'N') || constraint == 'TriggersCompositionThreshold') {
		var oper = $('listOperator' + listSelectedRowId);
		oper.value= 'SELECT';
		oper.style['display'] = 'none';		
		listBeanGrid.cells(listSelectedRowId, listBeanGrid.getColIndexById("listValue")).setValue("");
		var val = $('listValue' + listSelectedRowId);
		val.value = '';
		val.style['display'] = 'none';
	} else if ( constraint == 'TriggersThreshold' && hasAmountThreshold == 'Y') {
		var oper = $('listOperator' + listSelectedRowId);
		oper.value= ' ';
		oper.style['display'] = 'none';
		var val = $('listValue' + listSelectedRowId);	
		val.style['display'] = '';	
	} else {
		var oper = $('listOperator' + listSelectedRowId);		
		oper.style['display'] = '';
		var val = $('listValue' + listSelectedRowId);		
		val.style['display'] = '';
	}
}

function listOptChange()
{
	var operator = $v('listOperator' + listSelectedRowId);
	if(operator == 'notlisted' || operator == 'trace') {
		listBeanGrid.cells(listSelectedRowId, listBeanGrid.getColIndexById("listValue")).setValue("");
		var val = $('listValue' + listSelectedRowId);
		val.value = '';
		val.style['display'] = 'none';
	} else {
		var val = $('listValue' + listSelectedRowId);
		val.style['display'] = '';
	}
}

function casOptChange()
{
	var operator = $v('casOperator' + casSelectedRowId);
	if(operator == 'is null')
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

function moveListOrCasNosFields(direction) {
		
	if (gridDefault == 'list') {
		if (direction) { //up
			listBeanGrid.moveRow(listSelectedRowId ,"up");
		} else { //down
			listBeanGrid.moveRow(listSelectedRowId, "down");
		}		
	} else if (gridDefault == 'cas') {
		if (direction) { //up
			casBeanGrid.moveRow(casSelectedRowId ,"up");
		} else { //down
			casBeanGrid.moveRow(casSelectedRowId, "down");
		}
	}
}

function addLookupToRow(theGrid, rowId) {
	listlookuphtml = ' <input id="listFormatText'+rowId+'" name="listFormatText'+rowId+'" class="inputBox" type="text" readonly="true" size="40"/> ';
	listlookuphtml += ' <input name="searchForListFormat" type="button" class="listButton" onmouseover="this.className=\'listButton listButtonOvr\'" onMouseout="this.className=\'listButton\'" name="None" value="" onclick="lookupListFormat('+rowId+');">';
	gridCell(theGrid, rowId, "listFormatText").setCValue(listlookuphtml);
}

function lookupListFormat(rowId) {
	var listId = gridCellValue(listBeanGrid, rowId, 'listId');	
	var baseListFormatIdString = gridCellValue(listBeanGrid, rowId, 'listFormat');
	children[children.length] =  openWinGeneric("getlistformatdata.do?uAction=editListFormatLoad&listId="+URLEncode(listId)+"&baseListFormatIdString="+encodeURI(baseListFormatIdString),"edit_list_format","800","500","yes");
}

function populateListFormatField(selected, listId) {
	var found = false;
	var text = '';
	var id = '';
	var chemicalFieldId = '';
	
	var temo = '';
	for (var hh = 0; hh < selected.length; hh++)
	{
		temo += hh + " = '" + selected[hh].formatId + "' and '" + selected[hh].formatDesc;
		temo += "\n";
	}
	//alert(temo);
	
	for (var k = 0; k < listBeanGrid.getRowsNum();k++) 
	{
		var pos = listBeanGrid.getRowId(k);
		if(selected != null && listId == gridCellValue(listBeanGrid, pos, 'listId'))
		{			
			found = true;
			for (var i = 0; i < selected.length; i++)
			{				
				if (text == '') 
					text = selected[i].formatDesc;
				else 
					text += "; " + selected[i].formatDesc;	
				
				if (id == '') {
					id = selected[i].formatId;
					chemicalFieldId = listId;
				} 
				else {
					id += "|" + selected[i].formatId;
					chemicalFieldId += "|" + listId;
				} 
			}
			gridCell(listBeanGrid, pos, "listFormat").setCValue(id);			
			listBeanGrid.cellById(pos, listBeanGrid.getColIndexById("listFormat")).setValue(id);
			listBeanGrid.cellById(pos, listBeanGrid.getColIndexById("chemicalFieldX")).setValue(chemicalFieldId);						
			$('listFormatText'+pos).value = text;			
			break;
		}
	}
}

function validateListFormatAndContraint()
{
	var blnListFormatEmpty = false;
	var blnConstraintEmpty = false;
	var blnOperatorEmpty = false;
    var errMsg = "";
	var errCasMsg = "";


    if (gridDefault == 'list') {
        for (var k = 0; k < listBeanGrid.getRowsNum();k++)
        {
            var pos = listBeanGrid.getRowId(k);
            var listFormat = gridCellValue(listBeanGrid, pos, 'listFormat');
            var constraint = $v('listConstraint' + pos);

            if(constraint == 'None') {
                blnConstraintEmpty = true;
            }
            if (listFormat == '' || listFormat == 'No Display') {
                blnListFormatEmpty = true;
            }

            if (blnConstraintEmpty && blnListFormatEmpty) {
                if (errMsg != '')
                    errMsg += "\n ";
                errMsg += "- " + gridCellValue(listBeanGrid, pos, 'listName');
            }
            blnConstraintEmpty = false;
            blnListFormatEmpty = false;
        }
        if (errMsg != '') {
            alert(messagesListDataTemplate.selectlistconstraints + "\n " + errMsg);
            return false;
        }
        //check to make sure the user selected 60 or less chemical list on report
        if (listBeanGrid.getRowsNum() > 60) {
            alert(messagesListDataTemplate.chemicallistlimit.replace("{0}", listBeanGrid.getRowsNum()-60));
            return false;
        }
    }

    if (gridDefault == 'cas') {
        for (var k = 0 ; k < casBeanGrid.getRowsNum();k++)
        {
            var pos = casBeanGrid.getRowId(k);
            var constraint = $v('casConstraint' + pos);
            var operator = $v('casOperator' + pos);

            if(constraint == 'SELECT') {
                blnConstraintEmpty = true;
            }
            if(operator == 'SELECT') {
                blnOperatorEmpty = true;
            }

            if (blnConstraintEmpty && blnOperatorEmpty) {
                if (errCasMsg != '')
                    errCasMsg += "\n ";
                errCasMsg += "- " + gridCellValue(casBeanGrid, pos, 'chemicalName');
            }
            blnConstraintEmpty = false;
            blnListFormatEmpty = false;
        }
        if (errCasMsg != '') {
            alert(messagesListDataTemplate.selectcasconstraints + "\n " + errCasMsg);
            return false;
        }
    }

    return true;
}
