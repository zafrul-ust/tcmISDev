function hubchanged()
{
	var hubO = document.getElementById("hub");
	var inventoryGroupO = document.getElementById("inventoryGroup");
	var selhub = hubO.value;

	for (var i = inventoryGroupO.length; i > 0;i--)
   {
      inventoryGroupO[i] = null;
   }
	showinvlinks(selhub);
	inventoryGroupO.selectedIndex = 0;
	removeAllInvocieDates();
}

function showinvlinks(selectedhub)
{
    var invgrpid = new Array();
    invgrpid = altinvid[selectedhub];

	 var invgrpName = new Array();
    invgrpName = altinvName[selectedhub];

    if(invgrpid.length == 0)
    {
      setCab(0,messagesData.pleaseselect,"All");
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

function removeAllInvocieDates()
{
	var invoiceDateO = document.getElementById("invoiceDate");
	 if(invoiceDateO.length <= 0)
          return;

	for (var i = invoiceDateO.length; i > 0;i--)
   {
      invoiceDateO[i] = null;
   }
}

function removeAllAniversaryDates()
{
	var aniversaryDate = document.getElementById("aniversaryDate");
	 if(aniversaryDate.length <= 0)
          return;

	for (var i = aniversaryDate.length; i > 0;i--)
   {
      aniversaryDate[i] = null;
   }
}

function removeAllInventoryGroups()
{
	var inventoryGroup = document.getElementById("inventoryGroup");
	 if(inventoryGroup.length <= 0)
          return;

	for (var i = inventoryGroup.length; i > 0;i--)
   {
      inventoryGroup[i] = null;
   }
}

function companyIdchanged()
{
	var companyId = document.getElementById("companyId");
	var facilityId = document.getElementById("facilityId");
	var selcompanyId = companyId.value;

	for (var i = facilityId.length; i > 0;i--)
   {
      facilityId[i] = null;
   }
	showFacilities(selcompanyId);
	facilityId.selectedIndex = 0;
	removeAllAniversaryDates();
	showAniversaryDates(facilityId.value);
	removeAllInventoryGroups();
	showInventoryGroup(facilityId.value);
}

function showFacilities(selcompanyId)
{
    var facilityid = new Array();
    facilityid = altfacilityid[selcompanyId];

	 var facilityName = new Array();
    facilityName = altfacilityName[selcompanyId];

	 try
	 {
     setFacility(0,messagesData.pleaseselect,"All");

     for (var i=0; i < facilityid.length; i++)
     {
        setFacility(i+1,facilityName[i],facilityid[i])
     }
    }
    catch (ex)
    {
     setFacility(0,messagesData.pleaseselect,"All");
    }
}

function setFacility(href,text,id)
{
    var optionName = new Option(text, id, false, false)
    var facilityId = document.getElementById("facilityId");
	 facilityId[href] = optionName;
}

function facilityIdChanged()
{
	var facilityId = document.getElementById("facilityId");
	var selfacilityId = facilityId.value;
	var aniversaryDate = document.getElementById("aniversaryDate");
   var inventoryGroup = document.getElementById("inventoryGroup");

	for (var i = aniversaryDate.length; i > 0;i--)
   {
      aniversaryDate[i] = null;
   }

   for (var i = inventoryGroup.length; i > 0;i--)
   {
      inventoryGroup[i] = null;
   }

	showAniversaryDates(selfacilityId);
	aniversaryDate.selectedIndex = 0;
	showInventoryGroup(selfacilityId);
   inventoryGroup.selectedIndex = 0;
}

function showAniversaryDates(selfacilityId)
{
	 var aniversaryDate = new Array();
    aniversaryDate = altaniversaryDate[selfacilityId];

	 try
	 {
     setAniversaryDate(0,messagesData.pleaseselect,"All");
     for (var i=0; i < aniversaryDate.length; i++)
     {
        setAniversaryDate(i+1,aniversaryDate[i],aniversaryDate[i])
     }
    }
    catch (ex)
    {
	   setAniversaryDate(0,messagesData.pleaseselect,"All");
    }
}

function setAniversaryDate(href,text,id)
{
    var optionName = new Option(text, id, false, false)
    var aniversaryDate = document.getElementById("aniversaryDate");
	  aniversaryDate[href] = optionName;
}

function showInventoryGroup(selfacilityId)
{
    var invgrpid = new Array();
    invgrpid = altinventoryGroup[selfacilityId];

	 var invgrpName = new Array();
    invgrpName = altinventoryGroupName[selfacilityId];

    try
	 {
     for (var i=0; i < invgrpid.length; i++)
     {
        setInventoryGroup(i,invgrpName[i],invgrpid[i])
     }
    }
    catch (ex)
    {
	   setInventoryGroup(0,messagesData.all,"")
    }
	 //inventoryGroupO.selectedIndex = 0;
}

function setInventoryGroup(href,text,id)
{
    var optionName = new Option(text, id, false, false)
    var inventoryGroup = document.getElementById("inventoryGroup");
	  inventoryGroup[href] = optionName;
}

function validateCriteria()
{
var errorMessage = messagesData.validvalues +"\n\n";
var errorCount = 0;

var aniversaryDate  =  document.getElementById("aniversaryDate");
var facilityId  =  document.getElementById("facilityId");

if (facilityId.value == "All" || facilityId.value.lentgh==0)
{
  errorMessage = errorMessage + messagesData.facilityid + ".\n" ;
  errorCount = 1;
}

if (aniversaryDate.value == "All" || aniversaryDate.value.length==0)
{
 errorMessage = errorMessage + messagesData.startdate + ".\n" ;
 errorCount = 1;
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
 /*Make sure to not set the target of the form to anything other than resultFrame*/
 var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();
 var flag = validateCriteria();
  if(flag) {
   document.genericForm.target='resultFrame';
   parent.showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}

function myOnload() 
{
  displaySearchDuration();
  setResultFrameSize();
}

function generateExcel() {
  var flag = validateCriteria();
  var userAction = document.getElementById("userAction");
  userAction.value = 'buttonCreateExcel';
  if(flag) {
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_newGenerateExcel','650','600','yes');
    document.genericForm.target='_newGenerateExcel';
    var a = window.setTimeout("document.genericForm.submit();",500);
  }
}

/*The reason for this is to show the error messages from the main page*/
function showErrorMessages()
{
  parent.showErrorMessages();
}