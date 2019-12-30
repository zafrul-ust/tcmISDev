windowCloseOnEsc = true;
var children = new Array(); 

function validateForm()
{
	// validates the search input field
	var errorMessage = messagesData.validvalues + "\n\n";
	var errorCount = 0;
	
	try
	{
	 var searchInput = document.getElementById("account");	 
	 if ( searchInput.value.trim().length == 0  )
	 {
	    errorMessage +=  messagesData.account + "\n";
	    errorCount = 1;	    
	 }

	 
	} catch (ex) {
	  errorCount++;
	}
	if (errorCount >0)
	 {
	    alert(errorMessage);
	    return false;
	 }	
    return true;
}



function addNewCarrierAccount()
{	
	document.genericForm.target='';
	var action = document.getElementById("action");
	action.value="addNewCarrierAccount";
	var customerId = document.getElementById("customerId");
	var inventoryGroup = document.getElementById("inventoryGroup");
	
	//alert(window.opener.document.getElementById("customerId").value);
	//alert(window.opener.document.getElementById("inventoryGroup").value);
	try {
	if(!fromCustomerDetail)
	{
		
	if(window.opener.document.getElementById("customerId").value.length >0 )
	{	
	customerId.value = window.opener.document.getElementById("customerId").value;
	}
	if(window.opener.document.getElementById("inventoryGroup").value.length >0 )
	{	
	inventoryGroup.value = window.opener.document.getElementById("inventoryGroup").value;
	}
	
	}
	} catch(ex){}

	
	var now = new Date();
    var startSearchTime = document.getElementById("startSearchTime");
	startSearchTime.value = now.getTime();
	var flag = validateForm();
	
	if(flag) 
		
  	{	
  		try{
			window.opener.carrierChanged($v('carrierName'),$v('account'),$v('notes'),$v('callbackparam'));
			close();
		} catch(ex){}
		showTransitWin();
		document.genericForm.submit();  	    
   		return true;
  	}
  	else
  	{
    	return false;
  	}
}

function openNewCarrierWin() 
{
	if(closeCustomerDetailWin)
	{
		var cusId = document.getElementById("customerId").value;	
		var loc = "/tcmIS/distribution/newcarrier.do?action=newcarrier&fromCustomerDetail=Yes&customerId="+custId;
	}	
	
	var loc = "/tcmIS/distribution/newcarrier.do?action=newcarrier";
	children[children.length] = openWinGeneric(loc,"newcarrier","330","160","yes","50","50");
}


function refreshPage(refreshFlag, selectedCarrier)
{		
	if(refreshFlag)
	{		
	document.getElementById("selectedCarrier").value=selectedCarrier;
	document.genericForm.target='';
	var action = document.getElementById("action");
	action.value="newcarrieraccount";
	var now = new Date();
    var startSearchTime = document.getElementById("startSearchTime");
	startSearchTime.value = now.getTime();
	document.genericForm.submit();
	}
}


/*function refreshPageWithCustId(refreshFlag,selectedCarrier,custId,fromCustomerDetail )
{
	if(refreshFlag)
	{	
		alert("fromCustomerDetail in child"+fromCustomerDetail);
	document.getElementById("selectedCarrier").value=selectedCarrier;
	document.getElementById("fromCustomerDetail").value=fromCustomerDetail;
	document.getElementById("customerId").value=custId;
	document.genericForm.target='';
	var action = document.getElementById("action");
	action.value="addNewCarrierAccount?fromCustomerDetail="+fromCustomerDetail;
	var now = new Date();
    var startSearchTime = document.getElementById("startSearchTime");
	startSearchTime.value = now.getTime();
	document.genericForm.submit();
	}
	
	
}
*/

function newAccountWinClose()
{
	if(closeNewAcctWin)
	{ 		  
		window.opener.closeCarrierWin($("hCarrierName").value,$("haccount").value,$("carrierAccountId").value);		
        window.close();
	}
	
	if(closeCustomerDetailWin)
	{ 		
		var cusId = document.getElementById("customerId").value;	 
		var loc = "/tcmIS/distribution/customerupdate.do?customerId="+cusId;	  
		 window.opener.showTransitWin();
	   try
	   {   
		   opener.parent.parent.openIFrame("customerDetails"+cusId+"",loc,""+messagesData.customerdetails+" "+cusId+"","","N");
	   }	
	   catch (ex)
	   {
		   openWinGeneric(loc,"customerDetails"+cusId,"900","600","yes","80","80","yes");	
	   }
	  
		
        window.close();
	}
	
	if (showErrorMessage)
	 {
	  setTimeout('showCarrierAccountErrorMessages()',50); /*Showing error messages if any*/
	 }
}


function showCarrierAccountErrorMessages()
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
  var errorWin = dhxWins.createWindow("errorWin", 0, 0, 520, 110);
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

function showTransitWin() {
	  resizeFramecount = 1;
	  $("searchFrameDiv").style["display"] = "none";
	  $("transitPage").style["display"] = "";
	}


function setDropDowns()
{
	buildDropDown(opshubig,messagesData.pleaseselect,"opsEntityId");
 	$('opsEntityId').onchange = setInv;    
	setInv();
}


function setInv()
{
	 var opsO = $("opsEntityId");
	 var arr = null;
	 
	if( opsO.value != '' ) {
	   	   for(i=0;i< opshubig.length;i++)
	   	   		if( opshubig[i].id == opsO.value ) {
			   	   for(j=0;j< opshubig[i].coll.length;j++)
			   	   {		   	   		
		   	   			arr = opshubig[i].coll[j].coll;
		   	   			break;
			   	   }		
	   	   		    
	   	   		 break;
	   	   		}
	   }
	 buildDropDown(arr,messagesData.pleaseselect,"inventoryGroup");
}

function buildDropDown( arr, defaultObj, eleName ) {
	   var obj = $(eleName);
	   for (var i = obj.length; i > 0;i--)
	     obj[i] = null;
	  // if size = 1 or 2 show last one, first one is all, second one is the only choice.
	  if( arr == null || arr.length == 0 ) 
		  setOption(0,"","", eleName); 
	  else if( arr.length == 1 )
		  setOption(0,arr[0].name,arr[0].id, eleName);
	  else {
	      var start = 0;	  	  
		  for ( var i=0; i < arr.length; i++) {
		    	setOption(start++,arr[i].name,arr[i].id,eleName);
		  }
	  }
	  obj.selectedIndex = 0;
}

function closeAllchildren() 
{ 
// You need to add all your submit button vlues here. If not, the window will close by itself right after we hit submit button.
//	if (document.getElementById("uAction").value != "search") {

			for(var n=0;n<children.length;n++) {
				try {
					children[n].closeAllchildren(); 
				} catch(ex){}
				children[n].close();
			}
		children = new Array();
} 