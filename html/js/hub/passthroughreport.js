function submitSearchForm(entered)
{
 	//alert("entered submitSearchForm() - modified 1:40");
    if ( validateSearchForm() == false )
    {
     	return false;
    }
    var now = new Date();
    document.getElementById("startSearchTime").value = now.getTime();
    var userAction  =  document.getElementById("userAction");
    userAction.value = "search";
    document.genericForm.target='resultFrame';
    
    parent.showPleaseWait();   
    return true;
}

function createExcelFile() 
{
    if ( validateSearchForm() == false )
    {
     	return false;
    }
    //var buttonCreateExcel = document.getElementById("buttonCreateExcel");
    //buttonCreateExcel.value = 'createExcel';
    var userAction  =  document.getElementById("userAction");
    userAction.value = "createExcel";
    
	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp',"show_excel_report_file","800","800","yes");             
    document.genericForm.target='show_excel_report_file';
	var a = window.setTimeout("document.genericForm.submit();",500);
    return false;
}

function validateSearchForm()
{
	var errorMessage = messagesData.validvalues + "\n\n";
//	var errorMessage = "";
	var errorCount = 0;
	var dateDeliveredElement = document.getElementById("dateDelivered");
//	var dateDeliveredElement = document.genericForm.dateDelivered;

	if ( dateDeliveredElement.value.trim().length == 0 )
	{
	  	//alert("failed date test");
	    errorMessage = errorMessage + " " + messagesData.usedsince + ".\n" ;
	  	errorCount++;
	  		//dateDeliveredElement.value = "";
	}   
/*
	else
	if ( dateDeliveredElement.value.trim().length != 10 )
	{
	  	//alert("failed date length test");
	  		//errorMessage = errorMessage + messagesData.usedsince + " " + messagesData.date + "(" + messagesData.dateformat+ ")" + ".\n" ;
	  	errorCount++;
	  		//dateDeliveredElement.value = "";
	} 	
	else
	if (!checkdate( dateDeliveredElement ) )
	{
	  	//alert("failed date check");
	  			//errorMessage = errorMessage + messagesData.usedsince + " " + messagesData.date + "(" + messagesData.dateformat+ ")" + ".\n" ;
	  	errorCount++;
	  			//dateDeliveredElement.value = "";
	} 
	
	var dateDeliveredValue = dateDeliveredElement.value.trim();
  	//var dateDelivered = document.getElementById("dateDelivered");
  	//alert("document.genericForm.dateDelivered.value = [" + document.genericForm.dateDelivered.value + "]; ");
	
	alert("dateDeliveredValue = [" + dateDeliveredValue + "]; ");
	if (dateDeliveredValue == "") // == 10)
	{ 	
	 	errorCount++;
	 	dateDeliveredElement.value = "";	
	}
	else 
	{
	} */
	if (errorCount > 0)
	{
  		alert(errorMessage);
  		return false;
	}  
  	return true;
}

function hubchanged()
{
	var hubO = document.getElementById("hub");

	try
   {
     var sourceHubName = null;
     sourceHubName =  hubO.options[hubO.selectedIndex].text;

     var sourceHubNameObject = document.getElementById("sourceHubName");
     sourceHubNameObject.value = sourceHubName;
   }
   catch (ex)
   {
     //alert("Non-Pickable Status");
   }

	var inventoryGroupO = document.getElementById("inventoryGroup");
	var selhub = hubO.value;

	for (var i = inventoryGroupO.length; i > 0;i--)
   {
      inventoryGroupO[i] = null;
   }
	showinvlinks(selhub);
	inventoryGroupO.selectedIndex = 0;
}

function showinvlinks(selectedhub)
{
    var invgrpid = new Array();
    invgrpid = altinvid[selectedhub];

	 var invgrpName = new Array();
    invgrpName = altinvName[selectedhub];

    if(invgrpid.length == 0)
    {
      setCab(0,messagesData.all,"")
    }

    for (var i=0; i < invgrpid.length; i++)
    {
        setCab(i,invgrpName[i],invgrpid[i])
    }
}

function setCab(href,text,id)
{
    var optionName = new Option(text, id, false, false)
    var inventoryGroupO = document.getElementById("inventoryGroup");
	 inventoryGroupO[href] = optionName;
}
<!-- from template................................................ -->
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

function myOnload()
{
 displaySearchDuration();
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
