var resizeGridWithWindow = true;
var windowCloseOnEsc = true; 
var multiplePermissions = true;


function $(a) {
	return document.getElementById(a);
}

function validateSearchCriteriaInput()
{
	var errorMessage = messagesData.validvalues + "\n\n";
	
	var errorCount = 0;
	var warnCount = 0;
	
	var searchField = $v("searchField");
	var searchArgument = $v("searchArgument");
	if (searchField == 'itemId' && !isInteger(searchArgument,true))
	{  
		errorMessage = errorMessage + messagesData.itemInteger;
		$("searchArgument").value = "";
		errorCount = 1;
	}
	
	if (errorCount >0)
	{
	 alert(errorMessage);
	 return false;
	}
	
	return true;
}

function submitSearchForm() {
	if( !validateSearchCriteriaInput() ) return;
	  var now = new Date();
	  $('sourceHubName').value =  $('hub').options[$('hub').selectedIndex].text; 
    document.getElementById("startSearchTime").value = now.getTime();
	var userAction = document.getElementById("uAction");
	userAction.value = 'search';

   	document.genericForm.target='resultFrame';
   	showPleaseWait();
   	document.genericForm.submit();
}




function showErrorMessages() {
  parent.showErrorMessages();
}


function createExcel() {
	if( !validateSearchCriteriaInput() ) return;
	
	$('uAction').value = 'createExcel';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', 'inventory_receive','800','600','yes');
	 document.genericForm.target='inventory_receive';
  	 var a = window.setTimeout("document.genericForm.submit();",1000);
    return false;
}

function resultOnLoad()
{
	
	totalLines = document.getElementById("totalLines").value;
	if (totalLines > 0)
	{
		document.getElementById("inventoryToReceiveBean").style["display"] = "";
		
		initializeGrid();
		if(showUpdateLinks){
			
		parent.document.getElementById("showUpdateLink").style["display"] = ""; 
		parent.document.getElementById("addCitrSpan").style["display"] = "";
		}
		else
		{
			parent.document.getElementById("showUpdateLink").style["display"] = "none"; 
			parent.document.getElementById("addCitrSpan").style["display"] = "none";
		}
	}  
	else
	{
		document.getElementById("inventoryToReceiveBean").style["display"] = "none"; 
		if(showUpdateLinks)
		{
			parent.document.getElementById("addCitrSpan").style["display"] = "";
		    parent.document.getElementById("showUpdateLink").style["display"] = "none"; 
		}
	}

	/*This dislpays our standard footer message*/
	displayGridSearchDuration();

	/*It is important to call this after all the divs have been turned on or off.*/
	setResultFrameSize();
	
	if (totalLines == 0)
	{
		parent.document.getElementById('mainUpdateLinks').style["display"] = "";
		if(showUpdateLinks)
		{	
		   parent.document.getElementById("addCitrSpan").style["display"] = "";
		   parent.document.getElementById("showUpdateLink").style["display"] = "none"; 
		}
		else
		{
			parent.document.getElementById("showUpdateLink").style["display"] = "none"; 
			parent.document.getElementById("addCitrSpan").style["display"] = "none";
		}
				
	}
 
}

function initializeGrid(){
	
	beangrid = new dhtmlXGridObject('inventoryToReceiveBean');

	initGridWithConfig(beangrid, config, -1, true, true);
	if( typeof( jsonMainData ) != 'undefined' ) {
		
	  beangrid.parse(jsonMainData,"json");
	}
	beangrid.attachEvent("onRowSelect", selectRow);
}

function selectRow(rowId, cellInd) {
	selectedRowId = rowId; 
}

function update() {
	numberOfRows = $v('totalLines');
	  
	var flag = validateUpdateForm(numberOfRows);
	if(flag) {
	document.genericForm.target = '';
	
	document.getElementById("uAction").value = 'update';
    
    beangrid.parentFormOnSubmit();
    document.genericForm.submit();
	parent.showPleaseWait();
	}
	  return flag;

}

function validateUpdateForm(numberOfRows) {
	  var flag = true;
	  var selected = false;
	  var entered = false;
	  if(numberOfRows != null) {
	    for(var i=1; i<=numberOfRows; i++) {
		  var checked = false;
		  try {
			  checked = cellValue(i,'ok');
			  if(checked){
			  var qty = cellValue(i,'quantityToReceive');
			  var totQty = cellValue(i,'totalQuantityReceived');
			  if(totQty > qty){
				  entered = true;
			  }
			  }
		  } catch(ex){}
		  if( !checked ) 
		  continue;
		  selected = true;
		  
		 }
	   }
		  if( !selected ) {
			  alert(messagesData.norowselected);
			  return false;
		  }
		  if( entered ) {
			  alert(messagesData.qty);
			  return false;
		  }	
		    
		  
		  return flag;
	}

function setDropDowns()
{
	buildDropDownNew(opshubig,null,"opsEntityId");
	$('opsEntityId').onchange = opsChanged;
    $('hub').onchange = hubChanged;	
    opsChanged();
    
       
}

function buildDropDownNew( arr, defaultObj, eleName ) {
	   var obj = $(eleName);
	   for (var i = obj.length; i >= 0;i--)
	     obj[i] = null;
	  // if size = 1 or 2 show last one, first one is all, second one is the only choice.
	  if( arr == null || arr.length == 0 ) 
	return ;//	  setOption(0,defaultObj.name,defaultObj.id, eleName); 
	  else if( arr.length == 1 )
		  setOption(0,arr[0].name,arr[0].id, eleName);
	  else {
	      var offset = 0 ;
	      if( defaultObj != null ) {
		  	setOption(0,defaultObj.name,defaultObj.id, eleName); 
		  	offset = 1;
		  }
		  for ( var i=0; i < arr.length; i++) {
		    	setOption(i+offset,arr[i].name,arr[i].id,eleName);	      
		  }
	  }
	  obj.selectedIndex = 0;
	}




function hubChanged() 
{

	var opsO = $("opsEntityId");
	   var hubO = $("hub");
	   var arr = null;
	   if( opsO.value != '' && hubO.value != '' ) {
	   	   for(i=0;i< opshubig.length;i++)
	   		if( opshubig[i].id == opsO.value ) {
			   	   for(j=0;j< opshubig[i].coll.length;j++)
		   	   		if( opshubig[i].coll[j].id == hubO.value ) {
		   	   			document.getElementById("sourceHubName").value =  hubO.options[hubO.selectedIndex].text;
		   	   			arr = opshubig[i].coll[j].coll;
		   	   			break;
	   	   		    }
	   	   		 break;
	   	   		}
	   }
	   buildDropDown(arr,defaults.inv,"inventoryGroup");
	   if( defaults.hub.callback ) defaults.hub.callback();

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
   hubChanged();
   if( defaults.ops.callback ) defaults.ops.callback();
}


function addCitr() {
	var opsEntityId = document.getElementById("opsEntityId").value;
	var inventoryGroup = document.getElementById("inventoryGroup").value;
	var hub = document.getElementById("hub").value;
	
	$('sourceHubName').value =  $('hub').options[$('hub').selectedIndex].text;
  	
	
	var loc = "/tcmIS/hub/addnewcitr.do?uAction=new"+
		"&opsEntityId="+opsEntityId+
		"&inventoryGroup="+inventoryGroup+
		"&hub="+hub+
		"&sourceHubName="+ document.getElementById("sourceHubName").value;
			
	var winId= 'addnewcitr';
	openWinGeneric(loc,winId.replace('.','a'),"525","625","yes","50","50","20","20","no");
	
}


