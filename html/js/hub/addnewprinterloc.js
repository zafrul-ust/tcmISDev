var resizeGridWithWindow = true;
var beangrid;
var selectedRowId = null;
windowCloseOnEsc = true;
var multiplePermissions = true;

var permissionColumns = new Array();
permissionColumns={"labelStock":true,"printerResolutionDpi":true,"printerPath":true};


function resultOnLoad() {
	
	var done = document.getElementById("done").value;
	if(done == 'Y'){
		window.close();
		//window.opener.refreshPage();
	}
	else
	{
	document.getElementById("locationlabelPrinterBean").style["display"] = "";
	initializeGrid();
		
	setResultSize();
	}
	
}

function buildDropDown( arr, defaultObj, eleName ) {
	   var obj = $(eleName);
	   for (var i = obj.length; i > 0;i--)
	     obj[i] = null;
	  // if size = 1 or 2 show last one, first one is all, second one is the only choice.
	  if( arr == null || arr.length == 0 ) 
		  setOption(0,defaultObj.name,defaultObj.id, eleName); 
	  else if( arr.length == 1 )
		  setOption(0,arr[0].name,arr[0].id, eleName);
	  else {
	      var start = 0;
	  	  if( defaultObj.nodefault )
	  	  	start = 0 ; // will do something??
	  	  else {
		  setOption(0,defaultObj.name,defaultObj.id, eleName); 
			  start = 1;
		  }
		  for ( var i=0; i < arr.length; i++) {
		    	setOption(start++,arr[i].name,arr[i].id,eleName);
		  }
	  }
	  obj.selectedIndex = 0;
	}

function setOps() {
 	buildDropDown(opshubig,defaults.ops,"opsEntityId");
 	$('opsEntityId').onchange = opsChanged;
      opsChanged();
 }

function opsChanged()
{
   var opsO = $("opsEntityId");
   var arr = null;
   if( opsO.value != '' ) {
   	   for(i=0;i< opshubig.length;i++)
   	   		if( opshubig[i].id == opsO.value ) {
   	   			arr = opshubig[i].coll;
   	   			break;
   	   		}
   }

   buildDropDown(arr,defaults.hub,"hub");
   
}

function initializeGrid() {
	beangrid = new dhtmlXGridObject('locationlabelPrinterBean');

	
	initGridWithConfig(beangrid, config, true, true);
	if (typeof (jsonMainData) != 'undefined') {
		beangrid.parse(jsonMainData, "json");

	}
	 beangrid.attachEvent("onRowSelect",doOnRowSelected);
	//beangrid.attachEvent("onRightClick", selectRightclick);
}

function addPrinterPath(){
    try
    {
	if(beangrid == null) {
	     return false; 
	   } 
            
    var ind = beangrid.getRowsNum();  
    var rowid = ind*1+1;
    var thisrow = beangrid.addRow(rowid,"",ind);
        
    beangrid.cells(rowid,beangrid.getColIndexById("labelStockPermission")).setValue('Y');
    beangrid.cells(rowid,beangrid.getColIndexById("labelStock")).setValue('');
    beangrid.cells(rowid,beangrid.getColIndexById("printerResolutionDpiPermission")).setValue('Y');
    beangrid.cells(rowid,beangrid.getColIndexById("printerResolutionDpi")).setValue('');
    beangrid.cells(rowid,beangrid.getColIndexById("printerPathPermission")).setValue('Y');
    beangrid.cells(rowid,beangrid.getColIndexById("printerPath")).setValue('');
    
    var rowsNum = beangrid.getRowsNum();
    
    } catch(ex){
        alert(messagesData.norowselected);
    }
}

function doOnRowSelected(rowId) {
	 selectedRowId = rowId;
}

function removePrinterPath(){
	beangrid.deleteRow(selectedRowId);
 } 

function validateForm(){
	var errorMessage = messagesData.validvalues + "\n";
	var errorCount = 0;
	
	if(document.getElementById("printerLocation").value.trim().length == 0){
		 errorMessage +=  messagesData.name + "\n";
		 errorCount = 1;
	}
	if(document.getElementById("companyId").value.trim().length == 0){
		 errorMessage +=  messagesData.company + "\n";
		 errorCount = 1;
	}
	
	if( beangrid.cells(1,beangrid.getColIndexById("labelStock")).getValue()=='' || 
			beangrid.cells(1,beangrid.getColIndexById("printerPath")).getValue()=='') 
	  {
		 errorMessage +=  messagesData.printerpath + "\n";
		 errorCount = 1;
		
	  }
	
	if (errorCount >0)
	 {
	    alert(errorMessage);
	    return false;
	 }
   return true;
}

function newPrinterLocation(){
	if(validateForm()){
		document.getElementById("uAction").value = 'insert';
		beangrid.parentFormOnSubmit();
		document.genericForm.submit();
		showPleaseWait();
		return true;
	  }
	  else
	  {
	    return false;
	  }
}



function getSupplier()
{

loc = "/tcmIS/distribution/entitysuppliersearchmain.do?valueElementId=supplier&statusFlag=true&displayElementId=supplierName&fromNewPrinter=y";
openWinGeneric(loc,"Supplier","800","500","yes","50","50");
}

function clearSupplier()
{
    document.getElementById("supplierName").value = "";
    document.getElementById("supplier").value = "";
}

function getFacility()
{

loc = "/tcmIS/hub/companyfacilitiesmain.do?uAction=display&valueElementId=facilityId&displayElementId=facilityName";
openWinGeneric(loc,"Facilities","800","500","yes","50","50");
}

function clearFacility()
{
    document.getElementById("facilityName").value = "";
    document.getElementById("facilityId").value = "";
}