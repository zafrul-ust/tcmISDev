var errorMessage = "";
function validateCriteria()
{
//var errorCount = 0;
  var dailyDate = document.getElementById("dailyDate");
  
  if (dailyDate.value.trim().length == 0)
	{
	 errorMessage = messagesData.validvalues +"\n\n  " + messagesData.date + ".\n" ;
//	 errorCount = 1;
	 alert(errorMessage);
	 dailyDate.value = document.getElementById("todayDate").value;
	  return false; 
	}
/*  
	if (dailyDate.value.trim().length == 10)
	{
	  if (!checkdate(dailyDate))
	  {
	  errorMessage = errorMessage + messagesData.date + "("+messagesData.dateformat+")" +".\n" ;
	  errorCount = 1;
	  dailyDate.value = "";
	  }
	}
	else if (dailyDate.value.trim().length > 0 || dailyDate.value.trim().length == 0)
	{
	 errorMessage = errorMessage + messagesData.date + "("+messagesData.dateformat+")" +".\n" ;
	 errorCount = 1;
	 dailyDate.value = "";
	}
 */
return true;
}

function search() {
  if(validateCriteria())  {
	var now = new Date();
    document.getElementById("startSearchTime").value = now.getTime();
    var userAction = document.getElementById("userAction");
	userAction.value="search";
   	document.genericForm.target='resultFrame';
   	showPleaseWait();
  	return true;
	}
}

function doexcelpopup()
{
/*
 excelfileurl = "/tcmIS/common/viewexcel.jsp";

 openWinGenericExcel(excelfileurl,"show_excel_report_file","610","600","yes");
 */
	  if(!validateCriteria())  {
		alert(errorMessage);
		}
		else {
  	  	var userAction = document.getElementById("userAction");
  		userAction.value = 'buttonCreateExcel';          
		document.genericForm.target='show_excel_report_file';
		openWinGenericViewFile('/tcmIS/common/loadingfile.jsp',"show_excel_report_file","800","600","yes");         
    	var a = window.setTimeout("document.genericForm.submit();",500);
  		}
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
function showErrorMessages()
{
/*
	if( window.name == 'show_excel_report_file' ) 
		window.document.getElementById('errorMessagesAreaBody').style['display'] = '';
	else
*/
		parent.showErrorMessages();
}

var beangrid;

var resizeGridWithWindow = true;

function resultOnLoad() {
	totalLines = document.getElementById("totalLines").value;
	if (totalLines > 0) {
		document.getElementById("dailyInventoryReportBean").style["display"] = "";

		initializeGrid();
	} else {
		document.getElementById("dailyInventoryReportBean").style["display"] = "none";
	}

	displayGridSearchDuration();

	/*It is important to call this after all the divs have been turned on or off.*/
	setResultFrameSize();
}

function initializeGrid() {
	beangrid = new dhtmlXGridObject('dailyInventoryReportBean');

	initGridWithConfig(beangrid, config, false, true);
	if (typeof (jsonMainData) != 'undefined') {
		beangrid.parse(jsonMainData, "json");
	}
	//beangrid.attachEvent("onRowSelect", selectRow);
	//beangrid.attachEvent("onRightClick", selectRightclick);
}




