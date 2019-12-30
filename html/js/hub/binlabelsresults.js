

var resizeGridWithWindow = true;

var saveRowClass = null;

var inputSize = new Array();
inputSize = {  
  "room": 30
};


var maxInputLength = new Array();
maxInputLength={"room":20};

var multiplePermissions = true;

var permissionColumns = new Array();
permissionColumns={"print":true,"statusDisplay":true,"room":true,"onSiteDisplay":true};

function doOnBeforeSelect(rowId,oldRowId) {	
	if (oldRowId != 0) {
		setRowClass(oldRowId, saveRowClass);		
	}
	saveRowClass = getRowClass(rowId);
	if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/) == -1)
		overrideDefaultSelect(rowId,saveRowClass);
	return true;	
}



function resultOnLoad()
{
try{
	
 if (!showUpdateLinks)	 //Dont show any update links if the user does not have permissions
 {
	 parent.document.getElementById("updateRecord").style["display"] = "none";
	 parent.document.getElementById("addNewBinLink").style["display"] = "none";
	 parent.document.getElementById("generateBinLabelsLink").style["display"] = "none";
 }
 else
 {
	 parent.document.getElementById("updateRecord").style["display"] = "";
	 parent.document.getElementById("addNewBinLink").style["display"] = "";
	 parent.document.getElementById("generateBinLabelsLink").style["display"] = "";
  
 }
 }
 catch(ex)
 {}

 totalLines = document.getElementById("totalLines").value;
 
 if (totalLines > 0)
 {
  document.getElementById("binlabelsViewBean").style["display"] = "";
  
  initializeGrid();  
 }  
 else
 {
   document.getElementById("binlabelsViewBean").style["display"] = "none";   
 }

 displayGridSearchDuration();
 
 /*It is important to call this after all the divs have been turned on or off.*/
 setResultFrameSize();
 
 
}

function initializeGrid(){
	 beangrid = new dhtmlXGridObject('binlabelsViewBean');

	 initGridWithConfig(beangrid,config,false,true);
	 if( typeof( jsonMainData ) != 'undefined' ) {
	 beangrid.parse(jsonMainData,"json");

	 }
	 beangrid.attachEvent("onBeforeSelect",doOnBeforeSelect);
	 beangrid.attachEvent("onRowSelect",selectRow);
	 beangrid.attachEvent("onRightClick",selectRightclick);
}

function selectRightclick(rowId, cellInd) {
	beangrid.selectRowById(rowId, null, false, false);	
}

function selectRow( rowId,cellInd) {
	  // to show menu directly
		 if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/) == -1)
			  setRowClass(rowId,''+saveRowClass+'Selected');
		 
		 var columnId = beangrid.getColumnId(cellInd);	 	 
		 
		 	switch (columnId)
		 	{
			 	case "statusDisplay":
			 		var bCheck = cellValue(rowId,"statusDisplay");
			 		//alert("bCheck:"+bCheck);
			 		if(bCheck == 'true')
			 		{ 		   
			 		     beangrid.cellById(rowId,beangrid.getColIndexById("status")).setValue("A");	 			
			 		}
			 		else if(bCheck == 'false')
			 		{
			 			  beangrid.cellById(rowId,beangrid.getColIndexById("status")).setValue("I");
			 		}
			 		break;	
			 	case "ok":
			 		
			 		if (cellValue(rowId,"ok"))
			 		{		 					
			 			if(cellValue(rowId,"status")=='')
			 		    beangrid.cellById(rowId,beangrid.getColIndexById("status")).setValue(cellValue(rowId,"oldStatus"));	
			 		}
			 		
			 			
			 		
			 		break;	
			 	default:
		 	};
		 	
		 	
}



function doOnRowSelected(rowId,cellInd) {
	if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/) == -1)
		setRowClass(rowId,''+saveRowClass+'Selected');
	
  	
}







function generateLabels() 
{
	var selectedPrintCounter = 0;	
	for(var i=1;i<=beangrid.getRowsNum();i++)
	{		
	try 
	{
		if (cellValue(i,"ok"))
		{			
			selectedPrintCounter++;
		}
    }
	 catch (ex){
		 
	 }	 
	
	} 
	if(selectedPrintCounter>0)
	{
		createLabels();
	}
	else
	{
		alert(messagesData.norow);
	}	
}


function createnewbin() {

	var sourceHub = document.getElementById("hub");
	var sourceHubName = document.getElementById("sourceHubName");
	
	if (sourceHubName.value.length > 0 && sourceHub.value.length > 0)
	{
		var loc = "addhubbin.do?branchPlant="+sourceHub.value+"&sourceHubName="+escape(sourceHubName.value)+"&userAction=addNewBin&fromBinManagement=Yes";
		
		if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;
		
		try {
			children[children.length] = openWinGeneric(loc,"addnewBin","600","200","no","80","80");
		} catch (ex){
			openWinGeneric(loc,"addnewBin","600","200","no","80","80");
		}
	}
}



function updateBinLabels()
{
	var errmsg = "";
	var selectedCounter = 0;	
	for(var i=1;i<=beangrid.getRowsNum();i++)
	{		
	try 
	{
		if (cellValue(i,"ok"))
		{			
			selectedCounter++;
			if(cellValue(i,"binType")=='') {
				errmsg = messagesData.required.replace('{0}',messagesData.binType) + '\n';
				selectedCounter = 0;
				break;
			}
			if(!isInteger(cellValue(i,"routeOrder"), true)) {
				errmsg = messagesData.mustBeAnInteger.replace('{0}',messagesData.routeOrder) + '\n';
				selectedCounter = 0;
				break;
		    }
		}
    }
	 catch (ex){
		 
	 }	 
	
	} 
	if(selectedCounter>0)
	{	/*Set any variables you want to send to the server*/		
		document.genericForm.target='';
		document.getElementById('action').value = 'update';
		parent.showPleaseWait();
		beangrid.parentFormOnSubmit(); //prepare grid for data sending	 
	    document.genericForm.submit();
	}
	else
	{
		if (errmsg.length == 0) {
			alert(messagesData.norow);
		}
		else {
			alert(errmsg);
		}
	}
	
  
}


function printSelection()
{
    var selectedPrintCounter = 0;	
	for(var i=1;i<=beangrid.getRowsNum();i++)
	{		
	try 
	{
		if (cellValue(i,"print"))
		{			
			selectedPrintCounter++;
		}
    }
	 catch (ex){
		 
	 }	 
	
	} 
	if(selectedPrintCounter>0)
	{
		
	}
	else
	{
		alert(messagesData.norow);
	}	
}

function createLabels() {
	//showGeneratingWin();

	$("action").value = "generateBinLabels";
	beangrid.parentFormOnSubmit();
	openWinGeneric('/tcmIS/common/loadingpleasewait.jsp','_GenerateLabels','650','600','yes');
	document.genericForm.target = "_GenerateLabels";
	document.genericForm.submit();
	
	setTimeout('window.status="";',5000);
}

function refreshPage()
{
    document.genericForm.target='';
    document.getElementById("action").value = 'search';
	var now = new Date();
    var startSearchTime = parent.document.getElementById("startSearchTime");
	startSearchTime.value = now.getTime();
	parent.showPleaseWait();
	document.genericForm.submit();
		

}
