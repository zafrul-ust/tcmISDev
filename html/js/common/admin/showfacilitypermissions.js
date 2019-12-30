windowCloseOnEsc = true;
var resizeGridWithWindow = true;
var children = new Array();
var beangrid;
var multiplePermissions = true;
var permissionColumns = new Array();
permissionColumns={"facilityAccess":true,"adminRole":true,"defaultFacilityId":true};


function resultOnLoad()
{
	totalLines = document.getElementById("totalLines").value; 

	if (totalLines > 0)
	{
		document.getElementById("UserFacilityAdminViewBean").style["display"] = "";
		
		initializeGrid(); 
		 document.getElementById("mainUpdateLinks").style["display"] = "";
			if(showUpdateLinks)
			{
				document.getElementById("updateResultLink").style["display"] = "";
				document.getElementById("checkAllAccess").style["display"] = "";
			}
			else
			{
				document.getElementById("updateResultLink").style["display"] = "none";
				document.getElementById("checkAllAccess").style["display"] = "none";
			}
		showChecked();
	}  
	else
	{
		document.getElementById("UserFacilityAdminViewBean").style["display"] = "none"; 
		document.getElementById("mainUpdateLinks").style["display"] = "none";
	}
	
		/*This dislpays our standard footer message*/
	displayNoSearchSecDuration();

	/*It is important to call this after all the divs have been turned on or off.*/
	setResultSize();
		
    try{
    parent.resetTimer();
   }
   catch (ex)
   {
   }
}

function initializeGrid(){
	beangrid = new dhtmlXGridObject('UserFacilityAdminViewBean');
	initGridWithConfig(beangrid,config,-1,true,true);
	if( typeof( jsonMainData ) != 'undefined' ) {
			beangrid.parse(jsonMainData,"json");
		  }
	beangrid.attachEvent("onRowSelect",selectRow);
}

function getCell(rowId,colId) {
	return gridCell(selectedGrid,rowId,colId);
}

function selectRow(rowId, cellInd) {
	var columnId = beangrid.getColumnId(cellInd);
	   	switch (columnId) {
			case "facilityAccess":
			case "adminRole":
			case "defaultFacilityId":
				rowChanged(rowId);
				checkOne(rowId,columnId);
			default:
		}
	
}


function rowChanged(rowId) {
	beangrid.cellById(rowId, beangrid.getColIndexById("modified")).setValue("Y");
}


function showChecked()
{ 
	var numRows = beangrid.getRowsNum();
	for (var rowId = 1; rowId <= numRows; rowId++) {
		
		if(beangrid.cells(rowId,beangrid.getColIndexById("accessChecked")).getValue() == 'Y')
		{
			beangrid.cells(rowId,beangrid.getColIndexById("facilityAccess")).setChecked(true);
		}
		if(beangrid.cells(rowId,beangrid.getColIndexById("adminChecked")).getValue() == 'Y')
		{
			beangrid.cells(rowId,beangrid.getColIndexById("adminRole")).setChecked(true);
		}
		if(beangrid.cells(rowId,beangrid.getColIndexById("defaultChecked")).getValue() == 'Y')
		{
			beangrid.cells(rowId,beangrid.getColIndexById("defaultFacilityId")).setChecked(true);
		}
		
	}
}

function checkOne(rowId,columnId) {
	var checkBox = $(columnId + rowId);
	
	if (checkBox != null)
	{
		if(cell(rowId, columnId).getValue() == 'false')
		{
			if(columnId == "facilityAccess")
				{
				  cell(rowId, "adminRole").setChecked(false);
				  if(cell(rowId, "defaultFacilityId").getValue() == 'true')
				  {
					  alert(messagesData.noDefault);
					  cell(rowId, columnId).setChecked(true);
					  
				  }
				}
		}
		else
		{
			if(columnId == "defaultFacilityId")
			 {
				if(!checkSingleCheck(columnId))
				{cell(rowId, columnId).setChecked(false);}
				else{
					cell(rowId, "facilityAccess").setChecked(true);
					cell(rowId, columnId).setChecked(true);
				}
								
			 }
			else
			{
			     cell(rowId, columnId).setChecked(true);
			}
									
		}
	 
	}		
} 

function checkSingleCheck(columnId)
{
	var count=0;
	var numRows = beangrid.getRowsNum();
	for (var i = 1; i <= numRows; i++)
	{
		if(cell(i, columnId).getValue() == 'true')
		{
			count++;
		}
	}
	if(count>1)	
	{
		alert("Cannot set more than one default facility");
		return false;
	}
	return true;
	
}



function companyPermissionsUpdate() {
	
		document.getElementById('uAction').value="editupdate";
		document.genericForm.target='';
	   	showPleaseWait();
	   	beangrid.parentFormOnSubmit();
		document.genericForm.submit();
	
}

function checkAll(checkboxname) {
	var checkall = $("checkAllAccess");
	var rowsNum = beangrid.getRowsNum()*1;	
	var alertCount = 0;

	// Make sure the background render has rendered all rows
	renderAllRows();
	
	if( checkall.checked ) {
		for(var p = 1 ; p < (rowsNum+1) ; p ++) {
			var cid = checkboxname+p;
			if($(cid) != null && !$(cid).disabled && !$(cid).checked) {
				$(cid).checked = true;
				updateHchStatusA(cid);
				rowChanged(p);
			}
		}
	}
	else {
		for(var p = 1 ; p < (rowsNum+1) ; p ++) {
			var cid = checkboxname+p;
			if( !$(cid) != null && !$(cid).disabled && $(cid).checked) {
				$(cid).checked = false;
				updateHchStatusA(cid);
				{cell(p, "adminRole").setChecked(false);}

				if((cid).checked && cell(p, "defaultFacilityId").checked ) {
						alert(messagesData.noDefault);
						continue;			
				}
				rowChanged(p);
			}
		}
	}
	return true;  
}



function createXSL() {
		document.getElementById('uAction').value="createXSLEdit";
		document.genericForm.target='_excel_report_file';
	    openWinGenericExcel('/tcmIS/common/loadingfile.jsp','_excel_report_file','650','600','yes');
		setTimeout("document.genericForm.submit()",300);
}

function enableFieldsForFormSubmit(checkboxname) {
	var numRows = beangrid.getRowsNum();
	
	for (var i = 1; i <= numRows; i++)
	 {
		var cid = checkboxname+i;
		if( !$(cid) != null && $(cid).disabled && $(cid).checked)
		{
			
			$(cid).disabled = '';
			cell(i, checkboxname).setValue(true);
		}
		
	 }
}


function editFacilityPermissionsUpdate() {
	 enableFieldsForFormSubmit("facilityAccess");
	 enableFieldsForFormSubmit("adminRole");
	 enableFieldsForFormSubmit("defaultFacilityId");
	document.getElementById('uAction').value="editupdate";
	document.genericForm.target='';
   	parent.showPleaseWait();
   	beangrid.parentFormOnSubmit();
	document.genericForm.submit();
}


