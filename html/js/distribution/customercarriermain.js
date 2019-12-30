var children = new Array();

function submitSearchForm()
{	
	document.genericForm.target='resultFrame';
	var action = document.getElementById("action");
	action.value="search";
	var now = new Date();
    var startSearchTime = document.getElementById("startSearchTime");
	startSearchTime.value = now.getTime();
	var flag = validateForm();
	
	if(flag) 
  	{	showPleaseWait();
   		return true;
  	}
  	else
  	{
    	return false;
  	}
}

function validateForm()
{
    return true;
}

function openNewCarrierAccountWin() 
{
	var loc = "/tcmIS/distribution/newcarrieraccount.do?action=newcarrieraccount";
	children[children.length] = openWinGeneric(loc,"newcarrieraccount"+"","700","400","yes","50","50");
}


function selectedRow()
{ 
  try {
  var openervalueElementId = opener.document.getElementById(valueElementId);
  openervalueElementId.value = returnSelectedId;
  var openerdisplayElementId = opener.document.getElementById(displayElementId);
  openerdisplayElementId.value = returnSelectedValue;
  var openerAccountId = opener.document.getElementById(displayAccount);
  openerAccountId.value = returnSelectedId;
  var openerAccountId = opener.document.getElementById(displayAccount);
  openerAccountId.value = carrierAcctId;
  } catch( ex ) {}
  

  window.close();
  //reset valiable
  returnSelectedValue = null;
  returnSelectedId=null;
  valueElementId=null;
  displayElementId=null;
  displayAccount=null;
  carrierAcctId=null;

}

function mainOnload()
{
  if (valueElementId.length>0 && displayElementId.length>0 )
 {
  document.getElementById("selectedRow").innerHTML="";
 }  
  document.getElementById("showCustomerCarriersOnly").checked = true;
}


function changeCustomerCarrierFlagValue(element) {

	if (element.value == 'Yes')
		element.value = 'No';
	else
		element.value='Yes';
}
function closeCarrierWin(returnCarrierName,returnCarrierAcct,carrierAcctId)
{
	  try {		
	  var openervalueElementId = opener.document.getElementById(valueElementId);
	  openervalueElementId.value = carrierAcctId;
	  var openerdisplayElementId = opener.document.getElementById(displayElementId);
	  openerdisplayElementId.value = returnCarrierName;
	  var openerAccountId = opener.document.getElementById(displayAccount);
	  openerAccountId.value = returnCarrierAcct;
	  } catch( ex ) {}
	  

	  window.close();
	  //reset valiable
	  returnSelectedValue = null;
	  returnSelectedId=null;
	  valueElementId=null;
	  displayElementId=null;
	  displayAccount=null;
	
}

function closeAllchildren() 
{
// You need to add all your submit button vlues here. If not, the window will close by itself right after we hit submit button.
//	if (document.getElementById("uAction").value != 'new') {
		try {
			for(var n=0;n<children.length;n++) {
				try {
				children[n].closeAllchildren(); 
				} catch(ex){}
			children[n].close();
			}
		} catch(ex){}
		children = new Array(); 

}