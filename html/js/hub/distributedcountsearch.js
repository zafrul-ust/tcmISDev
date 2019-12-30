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
      setCab(0,"All","")
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

function checkItemId()
{
var errorMessage = messagesData.validValues+ "\n\n";;
var errorCount = 0;
var searchWhat = document.getElementById("searchWhat");

if (searchWhat.value == 'itemId')
{
 var searchText = document.getElementById("searchText");
 if (searchText.value.trim().length > 0 && !(isInteger(searchText.value)))
 {
  errorMessage = errorMessage + "Item Id" + "\n";
  errorCount = 1;
 }
}

 if (errorCount >0)
 {
  alert(errorMessage);
  return false;
 }
 else
 {
  return true;
 }
}

function submitSearchForm()
{
  var flag = true;
  flag = checkItemId();

  if (flag)
  {
   var sourceHub  =  document.getElementById("sourceHub");
   var inventoryGroup  =  document.getElementById("inventoryGroup");
	 if (sourceHub.value == "All" || sourceHub.value.trim() == 0 )
	 {
	  alert(messagesData.hubMustBeSelected);
	  flag = false;
	 }
  }

  if(flag) {
   parent.showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}