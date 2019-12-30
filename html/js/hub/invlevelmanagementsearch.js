
function submitSearchForm()
{
	// alert("this is the new search js ...");
    if ( validateSearchForm() == false )
    {
     	return false;
    }
    var action  =  document.getElementById("action");
    action.value = "search";
    document.genericForm.target='resultFrame';
    parent.showPleaseWait();   
    return true;
}

function validateSearchForm()
{
	var errorMessage = "";
	
	var errorCount = 0;

	var searchField  =  document.getElementById("searchField").value;	
  	if(searchField == 'ITEM_ID') 
  	{
  		var searchArgument  =  document.getElementById("searchArgument");
    	if(!isInteger(searchArgument.value.trim(), true)) 
    	{
    		errorMessage = errorMessage + messagesData.itemInteger + "\n" ;     		
      		errorCount++;
    	}
  	}
	var doLimitToRecentField = document.getElementById("doLimitToRecent");
	if (doLimitToRecentField.checked == true)
	{
		var daysSinceIssuedLimitField = document.getElementById("daysSinceIssuedLimit").value.trim();
  		if(daysSinceIssuedLimitField.length == 0 ) 
  		{
    		errorMessage = errorMessage + messagesData.specifyDaysSinceIssuedLimit + ".\n" ;
    		errorCount++;
  		}
		else
		{
  			if(!isInteger( daysSinceIssuedLimitField, true) ) 
  			{
    			errorMessage = errorMessage + messagesData.daysSinceIssuedLimitInteger + ".\n" ;
    			errorCount++;
  			}
  		}
  	}
  	
	if (errorCount > 0)
	{
  		alert(errorMessage);
  		return false;
	}
	else
	{
  		return true;
	}
}

function generateExcel() 
{
  	var isValidForm = validateSearchForm();
  	if ( isValidForm ) 
  	{
  		var action = document.getElementById("action");
    	action.value = 'createExcel';
    	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_newGenerateExcel','650','600','yes');
    	document.genericForm.target='_newGenerateExcel';
    	var a = window.setTimeout("document.genericForm.submit();", 500);
  	}
}

// ----------- from receiving.js:

function hubchanged()
{
	var hubO = document.getElementById("sourceHub");

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
	showInventoryGroup(selhub);
	inventoryGroupO.selectedIndex = 0;
}

function showInventoryGroup(selectedhub)
{
    var invgrpid = new Array();
    invgrpid = altinvid[selectedhub];

	 var invgrpName = new Array();
    invgrpName = altinvName[selectedhub];

    if(invgrpid.length == 0)
    {
      setInventoryGroup(0,"All","")
    }

    for (var i=0; i < invgrpid.length; i++)
    {
        setInventoryGroup(i,invgrpName[i],invgrpid[i])
    }
}

function setInventoryGroup(href,text,id)
{
    var optionName = new Option(text, id, false, false)
    var inventoryGroupO = document.getElementById("inventoryGroup");
	 inventoryGroupO[href] = optionName;
}
