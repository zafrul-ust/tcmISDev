
function mainOnload()
{
  if($v("popup") == 'Y') {  //alert("here");
	windowCloseOnEsc = true; }
  else
  	windowCloseOnEsc = false;  	
  
  if (valueElementId.length>0 && displayElementId.length>0 )
 {
  document.getElementById("selectedRow").innerHTML="";
 }  
}


function submitSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
  var isValidSearchForm = checkInput();
  var now = new Date();
  document.getElementById("uAction").value = 'search';
  document.getElementById("startSearchTime").value = now.getTime();
  
  if(isValidSearchForm) {
   document.genericForm.target='resultFrame';
   showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}

function createXSL() {
	if( !checkInput() ) return;
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', 'cust_search_lookup','800','600','yes');
		document.genericForm.target='cust_search_lookup';
  	var userAction = document.getElementById("uAction");
 		userAction.value = 'createExcel';
    var a = window.setTimeout("document.genericForm.submit();",1000);
    //document.genericForm.submit();
   	return false;
}


function checkInput()
{
var errorMessage = messagesData.validvalues + "\n\n";
var errorCount = 0;

try
{
 var searchCustomerIdArgument = document.getElementById("searchCustomerIdArgument");

 if ( searchCustomerIdArgument.value.trim().length != 0 && (!(isSignedInteger(searchCustomerIdArgument.value.trim())) || searchCustomerIdArgument.value*1 == 0 ) )
 {
    errorMessage +=  messagesData.customerid + "\n";
    errorCount = 1;
    receiptId.value = "";
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

function showLegend()
{
  var showLegendArea = document.getElementById("showLegendArea");
  showLegendArea.style.display="";

  var dhxWins = new dhtmlXWindows()
  dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  if (!dhxWins.window(messagesData.showlegend)) {
  // create window first time
  var legendWin = dhxWins.createWindow(messagesData.showlegend, 0, 0, 400, 100);
  legendWin.setText(messagesData.showlegend);
  legendWin.clearIcon();  // hide icon
  legendWin.denyResize(); // deny resizing
  legendWin.denyPark();   // deny parking
  legendWin.attachObject("showLegendArea");
  legendWin.attachEvent("onClose", function(legendWin){legendWin.hide();});
  legendWin.center();
  }
  else
  {
    // just show
    dhxWins.window("legendwin").show();
  }
}


function selectedRow()
{ 
  try {
	  var openervalueElementId = opener.document.getElementById(valueElementId);
	  openervalueElementId.value = returnSelectedId;
	  var openerdisplayElementId = opener.document.getElementById(displayElementId);
	  openerdisplayElementId.value = returnSelectedValue; 
	  
	  if( returnSelectedABCClassification != null && returnSelectedABCClassification.trim().length > 0)
	  	opener.$('abcClassification').innerHTML = messagesData.customerclass+": "+returnSelectedABCClassification+"";
	  else
	  	opener.$('abcClassification').innerHTML = messagesData.customerclass+": "+messagesData.na;
  } catch( ex ) {}

  try {
	  opener.customerChanged(returnSelectedId,returnSelectedValue);
  } catch( ex ) {}

  window.close();
  //reset valiable
  returnSelectedValue = null;
  returnSelectedId=null;
  valueElementId=null;
  displayElementId=null;
  displayAccount=null;
}

function subnew() {
  var loc = "/tcmIS/distribution/customerrequestupdate.do?uAction=new&customerRequestType=New";
  try
  {
    parent.parent.openIFrame("newCustomerRequestDetail",loc,""+messagesData.newcustomerrequest+"","","N");
  }
  catch (ex)
  {
    openWinGeneric(loc,"newCustomerRequestDetail"+"","800","600","yes","50","50");
  }

  	return false;
}
