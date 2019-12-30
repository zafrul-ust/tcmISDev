windowCloseOnEsc = true;
var resizeGridWithWindow = true;
var children = new Array();
var beangrid;
var multiplePermissions = true;
var permissionColumns = new Array();
permissionColumns={"companyAccess":true,"adminRole":true,"locked":true};


function resultOnLoad()
{
	totalLines = document.getElementById("totalLines").value; 

	if (totalLines > 0)
	{
		document.getElementById("UserCompanyAdminViewBean").style["display"] = "";
		
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
	}
	else
	{
		document.getElementById("UserCompanyAdminViewBean").style["display"] = "none"; 
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
	beangrid = new dhtmlXGridObject('UserCompanyAdminViewBean');
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
			case "companyAccess":
			case "adminRole":
			case "locked":
				rowChanged(rowId);
				checkOne(rowId,columnId);
			default:
		}
	
}

function itarRoleChanged() {
    rowChanged(beangrid.getSelectedRowId());    
}

function rowChanged(rowId) {
	beangrid.cellById(rowId, beangrid.getColIndexById("updated")).setValue("Y");
}

function checkOne(rowId,columnId) {
	var checkBox = $(columnId + rowId);
	
	if (checkBox != null && !checkBox.disabled)
	{
		if(cell(rowId, columnId).getValue() == 'false')
		{
			if(columnId == "companyAccess")
				{cell(rowId, "adminRole").setChecked(false);}
		}
		else
		{
			cell(rowId, columnId).setChecked(true);
									
		}
	 
	}		
} 



function companyPermissionsUpdate() {
    document.getElementById('uAction').value="update";
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
				rowChanged(p);
			}
		}
	}
	return true;  
}



function createXSL() {
		document.getElementById('uAction').value="createXSL";
		document.genericForm.target='_excel_report_file';
	    openWinGenericExcel('/tcmIS/common/loadingfile.jsp','_excel_report_file','650','600','yes');
		setTimeout("document.genericForm.submit()",300);
}

function showErrorMessages()
{
		parent.showErrorMessages();
}
