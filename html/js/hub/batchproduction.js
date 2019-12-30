function init() { /*This is to initialize the YUI*/
 this.cfg = new YAHOO.util.Config(this);
 if (this.isSecure)
 {
  this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
 }

  showErrorMessagesWin = new YAHOO.widget.Panel("errorMessagesArea", { width:"400px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:true, draggable:true, modal:false } );
  showErrorMessagesWin.render();
}

/*The reason for this is to show the error messages from the main page*/
function showErrorMessages()
{
  parent.showErrorMessages();
}

function sethub() 
{
 	showInvOptions(document.getElementById("hub").options[0].value);
}

function hubChanged() 
{

  var hub = document.getElementById("hub");
  var inv = document.getElementById("inventoryGroup");
  var selectedHub = hub.value;

  for (var i = inv.length; i > 0; i--) {
    inv[i] = null;
  }
  showInvOptions(selectedHub);
}

function invChanged() 
{

  var hub = document.getElementById("hub");
  var inv = document.getElementById("inventoryGroup");
  var vessel = document.getElementById("vesselId");
  
  var selectedHub = hub.value;
  var selectedInv = inv.value;
  
  for (var i = vessel.length; i > 0; i--) 
  {
    vessel[i] = null;
  }
  
  showVesselOptions(selectedHub,selectedInv);
}

function showInvOptions(selectedHub) 
{
  var idArray = altinvGrpid[selectedHub];
  var nameArray = altinvGrpName[selectedHub];
  
  if(nameArray ==null || nameArray.length == 0) 
  {
    setOption(0,messagesData.pleaseSelect,"", "inventoryGroup");
  }
  else 
  {
	  for (var i=0; i < nameArray.length; i++) 
	  {
    	setOption(i,nameArray[i],idArray[i], "inventoryGroup");
  	  }
  }
  document.getElementById("inventoryGroup").selectedIndex = 0;
  showVesselOptions(selectedHub, document.getElementById("inventoryGroup").value ) ;
}

function showVesselOptions(selectedHub,selectedInv) 
{

  var idArray = altvesselid[selectedHub][selectedInv];
  var nameArray = altvesselName[selectedHub][selectedInv];
  
  var inv = document.getElementById("vesselId");
  for (var i = inv.length; i > 0; i--) 
  {
    inv[i] = null;
  }

  if(nameArray ==null || nameArray.length == 0) 
  {
    setOption(0,messagesData.pleaseSelect,"", "vesselId");
  }
  else 
  {
	  for (var i=0; i < nameArray.length; i++) 
	  {
    	setOption(i,nameArray[i],idArray[i], "vesselId");
  	  }
  }
  document.getElementById("vesselId").selectedIndex = 0;
}

function createNewBatch()
{
	// the following function is implemented by BlendingBatchInfoAction;
	
 	openWinGeneric("blendingbatchinfomain.do?action=init","batchinfopage","800","500","yes");
 	
    //window.location=
    // "/tcmIS/hub/blendingbatchinfomain.do?action=init";	
}

function submitSearchForm()
{
 	var flag = validateForm();
  	if(flag) 
  	{
   		parent.showPleaseWait();
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
// the following is the one in use...
function createExcelFile() 
{
    if ( validateForm() == false )
    {
     	return false;
    }
    var buttonCreateExcel = document.getElementById("buttonCreateExcel");
    buttonCreateExcel.value = 'createExcel';
    var action = document.getElementById("action");
    action.value = 'createExcel';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp',"show_excel_report_file","800","800","yes");             
    document.cabinetManagementForm.target='show_excel_report_file';
    var a = window.setTimeout("document.genericForm.submit();",500);
    return false;
}
/*
function doGenerateExcel() 
{
  var flag = validateForm();
  if(flag) 
  {
    var action = document.getElementById("action");
    action.value = 'createExcel';
    openWinGeneric('/tcmIS/common/loadingfile.jsp','_newGenerateExcel','650','600','yes');
    document.genericForm.target='_newGenerateExcel';
    //document.genericForm.action='/tcmIS/hub/allocationanalysis.do';
    var a = window.setTimeout("document.genericForm.submit();",500);
  }
}
*/
function myResultsBodyOnload()
{
// alert("myResultsBodyOnload()...");
 setResultFrameSize();
 /*Dont show any update links if the user does not have permissions.
 Remove this section if you don't have any links on the main page*/
 /*if (!showUpdateLinks)
 {
  parent.document.getElementById("updateResultLink").style["display"] = "none";
 }
 else
 {
  parent.document.getElementById("updateResultLink").style["display"] = "";
 }*/
}

function doCloseBatch( batchId )
{
	parent.showPleaseWait();
    window.location=
     "/tcmIS/hub/batchproductionclosebatch.do?batchIdToClose=" + batchId;
    // "/tcmIS/hub/blendingbatchinfomain.do?batchIdToClose=" + batchId;
}
