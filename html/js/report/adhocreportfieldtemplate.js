function initializeReportGrid() {
    popupOnLoadWithGrid(reportFieldGridConfig);   
}

function reportFieldRowSelect(rowId) {
	reportSelectedRowId = rowId;
}

function moveReportFields(direction) {
	if (direction) { //up
		reportFieldBeanGrid.moveRow(reportSelectedRowId ,"up");
	} else { //down
		reportFieldBeanGrid.moveRow(reportSelectedRowId, "down");
	}
}

function populateReportFieldGrid(selected) {
    //check to see if base field(s) already selected
	/*
    for (var i = 0; i < selected.length; i++) {
		var found = false;
		for (var k = 0; k < reportFieldBeanGrid.getRowsNum();k++) {
			var pos = reportFieldBeanGrid.getRowId(k);
			if(selected[i] != null && selected[i].baseFieldId == gridCellValue(reportFieldBeanGrid,pos,'baseFieldId')){
				selected[i]=null;		
				break;
			}
		}		
	}
    */ 
	
    for (var k = 0; k < reportFieldBeanGrid.getRowsNum();k++) {
		var found = false;
		var pos = reportFieldBeanGrid.getRowId(k);
		for (var i = 0; i < selected.length; i++) {			
			if(selected[i] != null && selected[i].baseFieldId == gridCellValue(reportFieldBeanGrid,pos,'baseFieldId')){
				selected[i]=null;
				found = true;
				break;
			}
		}
		if (!found){			
			reportFieldBeanGrid.deleteRow(pos);	
			k=k-1;
		}
	}
    //add new base field(s) to grid
	for (var l = 0; l < selected.length; l++) {
        if(selected[l] != null){
            var j = 0;
            var newId = reportFieldBeanGrid.getRowsNum();
            var newData = new Array();
            newData[j++] = 'N';
            newData[j++] = selected[l].baseFieldName;
            newData[j++] = selected[l].description;
            newData[j++] = selected[l].baseFieldId;
            var nextId = newId + 1;
            while (reportFieldBeanGrid.doesRowExist(nextId)) 
            	nextId++;            
            reportFieldBeanGrid.addRow(nextId,newData,reportFieldBeanGrid.getRowsNum());            
        }
	}
}

function popReportBaseFields() {
	var baseFieldIdString = '';
	var ifound = 0;
	var i=0;
	 while (ifound < reportFieldBeanGrid.getRowsNum()) {
		 var pos = reportFieldBeanGrid.getRowId(i);
		 if (reportFieldBeanGrid.isItemExists(pos)) {
			ifound++;
			var baseFieldId = gridCellValue(reportFieldBeanGrid,pos,'baseFieldId');
			if(baseFieldIdString == '')
	            baseFieldIdString = baseFieldId;
	        else
	            baseFieldIdString += "|"+baseFieldId;			
		 }
		 i++;
	 }
		
//    for(var i = 0; i < reportFieldBeanGrid.getRowsNum();i++) {    	
//		var baseFieldId = gridCellValue(reportFieldBeanGrid,i,'baseFieldId');
//		if(baseFieldIdString == '')
//            baseFieldIdString = baseFieldId;
//        else
//            baseFieldIdString += "|"+baseFieldId;
//	}

    var loc = "reportbasefields.do?reportType="+$v("reportType")+"&baseFieldIdString="+baseFieldIdString;
	children[children.length] = openWinGeneric(loc,'ReportBaseFields',"720","550","yes","50","50","20","20","no");
}

function onReportRightClick(rowId)
{
	reportSelectedRowId = rowId;
	toggleContextMenu('rightClickReportRemove');
}

function delReport()
{
	reportFieldBeanGrid.deleteRow(reportSelectedRowId);	
}

function validateReportFieldsGrid()
{
	baseFieldId = '';
	baseFieldName = '';
	baseDescription = '';
	
	if (reportFieldBeanGrid.getRowsNum() <= 0)
		return false;
	
	for (var k = 0; k < reportFieldBeanGrid.getRowsNum();k++) {
		var pos = reportFieldBeanGrid.getRowId(k);

		var bsFldId = gridCellValue(reportFieldBeanGrid, pos,'baseFieldId');		
		if(baseFieldId == '')
			baseFieldId = bsFldId;
        else
        	baseFieldId += "|"+ bsFldId;
		
		var desc = gridCellValue(reportFieldBeanGrid, pos, 'baseDescription');		
		if(baseDescription == '')
			baseDescription = desc;
        else
        	baseDescription += "|"+ desc;
		
		var bsFldNm = gridCellValue(reportFieldBeanGrid, pos,'baseFieldName');
		if(baseFieldName == '')
			baseFieldName = bsFldNm;
        else
        	baseFieldName += "|"+ bsFldNm;
	}
	
	$('baseFieldId').value = baseFieldId;
	$('baseFieldName').value = baseFieldName;
	$('baseDescription').value = baseDescription;
	
	return true;
}