var selectedRowId=null;



function selectPRow(rowId) {
	var selectedRow = document.getElementById("rowIdP" + rowId + "");
	var selectedRowClass = document.getElementById("colorClass" + rowId + "");
	selectedRow.className = "selected" + selectedRowClass.value + "";
	
	if (selectedRowId != null && rowId != selectedRowId) {
		var previouslySelectedRow = document.getElementById("rowIdP" + selectedRowId + "");
		var previouslySelectedRowClass = document.getElementById("colorClass" + selectedRowId + "");
		previouslySelectedRow.className = "" + previouslySelectedRowClass.value + "";
	}
	
	selectedRowId = rowId;
	var dataCount = $v("pDataCount" + selectedRowId + "");
	var content = $v("content" + dataCount + "");
	
	if (content.length > 0)
	{	   
	  toggleContextMenu('qsMenu');
	}
	else
	{		
		toggleContextMenu('');
	}

}

function selectRRow(rowId) {
	var selectedRow = document.getElementById("rowIdR" + rowId + "");
	var selectedRowClass = document.getElementById("colorClass" + rowId + "");
	selectedRow.className = "selected" + selectedRowClass.value + "";
	
	if (selectedRowId != null && rowId != selectedRowId) {
		var previouslySelectedRow = document.getElementById("rowIdR" + selectedRowId + "");
		var previouslySelectedRowClass = document.getElementById("colorClass" + selectedRowId + "");
		previouslySelectedRow.className = "" + previouslySelectedRowClass.value + "";
	}
	
	selectedRowId = rowId;
	var dataCount = $v("selectedDataCount" + selectedRowId + "");
	var content = $v("content" + dataCount + "");
	
	if (content.length > 0)
	{
	  toggleContextMenu('qsMenu');
	}
	else
	{		
		toggleContextMenu('');
	}
	
}

function selectSRow(rowId) {
	var selectedRow = document.getElementById("rowIdS" + rowId + "");
	var selectedRowClass = document.getElementById("colorClass" + rowId + "");
	
	selectedRow.className = "selected" + selectedRowClass.value + "";
	
	
	if (selectedRowId != null && rowId != selectedRowId) {
		var previouslySelectedRow = document.getElementById("rowIdS" + selectedRowId + "");
		var previouslySelectedRowClass = document.getElementById("colorClass" + selectedRowId + "");
		previouslySelectedRow.className = "" + previouslySelectedRowClass.value + "";
	}
	
	selectedRowId = rowId;
	
	var dataCount = $v("sDataCount" + selectedRowId + "");
	var online = $v("online" + dataCount + "");
	
	
	if (online == 'Y')
	  {		
		toggleContextMenu('specMenu');
	  }
	else
	{		
		toggleContextMenu('');
	}
		
}



function openPdf()
{
	var dataCount =  document.getElementById("selectedDataCount"+selectedRowId+"").value;
	 var pdf =  document.getElementById("content"+dataCount+"").value;
	 var x=window.open(pdf);
     x.focus();
}

function viewSpec()
{
	var dataCount =  document.getElementById("sDataCount"+selectedRowId+"").value;
	//var fullFilePath = document.getElementById("content"+dataCount+"").value;
	//var pdf = fullFilePath.substring(fullFilePath.lastIndexOf('/')+1,fullFilePath.length);

    var tmpUrl = '';
    if ($v("secureDocViewer") == 'true')
        tmpUrl = '/DocViewer/client/';
    
    openWinGeneric(tmpUrl + 'docViewer.do?uAction=viewSpec' +
             '&companyId='+$v('companyId')+
             '&catalogId='+encodeURIComponent($v('catalogId'))+
             '&catpartno='+ encodeURIComponent($v('catPartNo'))+
             '&opsEntityId='+$v('opsEntityId')+
             '&inventoryGroup='+$v('inventoryGroup')+
             '&spec=' + $v("specId"+dataCount+"") ,"ViewSpec","800","600",'yes' );
}