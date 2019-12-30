windowCloseOnEsc = true;
var resizeGridWithWindow = true;
var children = new Array();
var beangrid;
var multiplePermissions = true;
var permissionColumns = new Array();
permissionColumns={"pageAccess":true,"adminRole":true};

function submitSearchForm() {
	var now = new Date();
	document.getElementById("startSearchTime").value = now.getTime();

	if (validateSearchForm()) {
		$('uAction').value = 'search';
		document.genericForm.target = 'resultFrame';
		showPleaseWait();
		return true;
	} else {
		return false;
	}
}

function validateSearchForm() {
	var result = true;
	var companyId  =  document.getElementById("companyId");
	if (companyId.value.length == 0 || companyId.value == "Select One") {
		alert(messagesData.pleasemakeselection);
		result = false;
	}
	return result;
}

function resultOnLoad()
{
	totalLines = document.getElementById("totalLines").value; 

	if (totalLines > 0)
	{
		document.getElementById("UserPageAdminViewBean").style["display"] = "";
		
		initializeGrid(); 
		 parent.document.getElementById("mainUpdateLinks").style["display"] = "";
			if(showUpdateLinks)
			{
				parent.document.getElementById("updateResultLink").style["display"] = "";
				document.getElementById("checkAllAccess").style["display"] = "";
			}
			else
			{
				parent.document.getElementById("updateResultLink").style["display"] = "none";
				document.getElementById("checkAllAccess").style["display"] = "none";
			}
		showChecked();
	}  
	else
	{
		document.getElementById("UserPageAdminViewBean").style["display"] = "none"; 
		parent.document.getElementById("mainUpdateLinks").style["display"] = "none";
	}
	
		/*This dislpays our standard footer message*/
	displayGridSearchDuration();

	/*It is important to call this after all the divs have been turned on or off.*/
	setResultFrameSize();
		
    try{
    parent.resetTimer();
   }
   catch (ex)
   {
   }
}

function initializeGrid(){
	beangrid = new dhtmlXGridObject('UserPageAdminViewBean');
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
			case "pageAccess":
			case "adminRole":
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
			beangrid.cells(rowId,beangrid.getColIndexById("pageAccess")).setChecked(true);
			
		}
		if(beangrid.cells(rowId,beangrid.getColIndexById("adminChecked")).getValue() == 'Y')
		{
			beangrid.cells(rowId,beangrid.getColIndexById("adminRole")).setChecked(true);
		}
			
	}
}

function checkOne(rowId,columnId) {
	if(beangrid.cellById(rowId, beangrid.getColIndexById(columnId)).getValue()== 'false')
		{
			if(columnId == "pageAccess")
				{beangrid.cellById(rowId, beangrid.getColIndexById("adminRole")).setChecked(false);}
		}
		else
		{
			beangrid.cellById(rowId, beangrid.getColIndexById(columnId)).setChecked(true);
			if(columnId == "adminRole")
			{beangrid.cellById(rowId, beangrid.getColIndexById("pageAccess")).setChecked(true);}						
		}
	 
	}		
 



function pagePermissionsUpdate() {
	
		document.getElementById('uAction').value="update";
		document.genericForm.target='';
	   	parent.showPleaseWait();
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
