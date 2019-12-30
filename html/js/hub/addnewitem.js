function validateForm() {
	
	var errorMsg = '';
	if( !$v("sourceInventoryGroup"))
		errorMsg += "\n"+messagesData.inventorygroup;
	if( !$v('itemId'))
		errorMsg += "\n"+messagesData.itemid;
	if( $v("sourceInventoryGroup") == $v("inventoryGroup"))
		errorMsg += "\n"+messagesData.sameInventoryGroup;
	if( errorMsg != '' ) {
		  alert(messagesData.validvalues+errorMsg);
		  return false;
	}
	return true;
}

function newItemWinClose()
{
	if (showErrorMessage)
	 {
		setTimeout('showErrorMessages()',50); /*Showing error messages if any*/
	 }
	
	if(closeNewItemWin)
	{ 
		//window.opener.refreshPage();    	   
        window.close();
	}
	
}

function showErrorMessages()
{
  var resulterrorMessages = document.getElementById("errorMessagesAreaBody");
  var errorMessagesArea = document.getElementById("errorMessagesArea");
  errorMessagesArea.innerHTML = resulterrorMessages.innerHTML;

  var errorMessagesArea = document.getElementById("errorMessagesArea");
  errorMessagesArea.style.display="";

  var dhxWins = new dhtmlXWindows();
  dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  if (!dhxWins.window("errorWin")) {
  // create window first time
  var errorWin = dhxWins.createWindow("errorWin", 0, 0, 320, 150);
  errorWin.setText(messagesData.errors);
  errorWin.clearIcon();  // hide icon
  errorWin.denyResize(); // deny resizing
  errorWin.denyPark();   // deny parking
  errorWin.attachObject("errorMessagesArea");
  errorWin.attachEvent("onClose", function(errorWin){errorWin.hide();});
  errorWin.center();
  }
  else
  {
    // just show
    dhxWins.window("errorWin").show();
  }
}

function newItem() {
	document.genericForm.target='';
	document.getElementById("uAction").value = "addnewitem";
	var now = new Date();
    var startSearchTime = document.getElementById("startSearchTime");    
	startSearchTime.value = now.getTime();
   var flag = validateForm();
	
	if(flag) 
	{
		showPleaseWait();
		document.genericForm.submit();  	   
   		return true;
  	}
  	else
  	{
    	return false;
  	}
		
}

var children = new Array();

function getItem() {
	if($v("sourceInventoryGroup").length == 0) {
		alert(messagesData.missingInventoryGroup);
		return false;
	}
			// new item no shipto.
//	var loc = "/tcmIS/supply/poitemsearchmain.do?uAction=new&shipToId=" + shipid+ 
	var loc = "/tcmIS/supply/poitemsearchmain.do?uAction=new&"+ 
	"&inventoryGroup=" + $v("sourceInventoryGroup")+
	"&companyId=Radian";// + gg("inventoryGroup");
	var winId= 'poitemsearchmain';//+$v("prNumber");
	children[children.length] = openWinGeneric(loc,winId.replace('.','a'),"1024","600","yes","50","50","20","20","no");
	
}

function itemChanged(itemId,itemDesc) {
	$('itemDesc').value = itemDesc;
	$('itemId').value = itemId;
}

function closeAllchildren() {
	// do nothing now.
}


function clearItem()
{
    document.getElementById("itemDesc").value = "";
    document.getElementById("itemId").value = "";
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
		  setOption(0,defaultObj.name,defaultObj.id, eleName); 
		  for ( var i=0; i < arr.length; i++) {
		    	setOption(i+1,arr[i].name,arr[i].id,eleName);
		  }
	  }
	  obj.selectedIndex = 0;
	}

	function setOps(opshubigColl,defaultArr) {
		buildDropDown(opshubigColl,defaults.ops,"sourceOpsEntityId");
	 //	$('sourceOpsEntityId').onchange = opsChanged;
		checkOpsChanged();
	    $('sourceHub').onchange = hubChanged;	
	    opsChanged();
	}

	function opsChanged()
	{  
		var opshubigColl;
		var defaultArr;
		opshubigColl = opshubig;
	    defaultArr = defaults;
	  	
	  	
	   var opsO = $("srcOpsEntityId");
	   var arr = null;
	   if( opsO.value != '' && opsO.value != 'all') {
	   	   for(i=0;i< opshubigColl.length;i++)
	   	   		if( opshubigColl[i].id == opsO.value ) {
	   	   			arr = opshubigColl[i].coll;
	   	   			break;
	   	   		}
	   }

	   buildDropDown(arr,defaultArr.hub,"sourceHub");
	   hubChanged();
	   
	 
	}

	function hubChanged()
	{
		var opshubigColl;
		var defaultArr;
		opshubigColl = opshubig;
	    defaultArr = defaults;
	  		

	   var opsO = $("srcOpsEntityId");
	   var hubO = $("sourceHub");
	   var arr = null;
	   if( opsO.value != '' && hubO.value != '' && opsO.value != 'all' && hubO.value != 'all' ) {
	   	   for(i=0;i< opshubigColl.length;i++)
	   	   		if( opshubigColl[i].id == opsO.value ) {
			   	   for(j=0;j< opshubigColl[i].coll.length;j++)
		   	   		if( opshubigColl[i].coll[j].id == hubO.value ) {
		   	   			arr = opshubigColl[i].coll[j].coll;
		   	   			break;
	   	   		    }
	   	   		 break;
	   	   		}
	   }
	   buildDropDown(arr,defaultArr.inv,"sourceInventoryGroup");
	   disableSelect();
	}
	
	function checkOpsChanged()
	{
	   var opsO = $("sourceOpsEntityId");
	   var srcOpsEntityId = $("srcOpsEntityId");
	   if ((opsO.value != srcOpsEntityId.value))
	   {
		   
		   opsChanged();
	       
	   }
	}
	
	function disableSelect()
	{
		document.getElementById("sourceOpsEntityId").disabled = "disabled";
	}
	
	