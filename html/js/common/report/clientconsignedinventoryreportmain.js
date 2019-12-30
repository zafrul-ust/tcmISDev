
function facilityChanged() {
  var facilityO = document.getElementById("facilityId");
  var inventoryO = document.getElementById("inventoryGroup");
  var selectedFacility = facilityO.value;

  for (var i = inventoryO.length; i > 0;i--) {
    inventoryO[i] = null;
  }
  showInventoryLinks(selectedFacility);
  inventoryO.selectedIndex = 0;
}

function showInventoryLinks(selectedFacility) {
    var inventoryGroup = new Array();
    inventoryGroup = altInventoryGroup[selectedFacility];

    var inventoryGroupName = new Array();
    inventoryGroupName = altInventoryGroupName[selectedFacility];

    if(inventoryGroup.length == 0)
    {
      setCab(0,messagesData.all,"")
    }

    for (var i=0; i < inventoryGroup.length; i++)
    {
        setCab(i,inventoryGroupName[i],inventoryGroup[i])
    }
}

function setCab(href,text,id)
{
    var optionName = new Option(text, id, false, false)
    var inventoryO = document.getElementById("inventoryGroup");
    inventoryO[href] = optionName;
}

function submitSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
  var isValidSearchForm = validateSearchCriteriaInput();
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();

  if(isValidSearchForm) {
   $("uAction").value = 'search';
   document.genericForm.target='resultFrame';
   showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}

function validateSearchCriteriaInput()
{
   var result = true;
   if (checkexpiresWithin()) {
    result = false;
   }
   if (checkexpiresAfter()) {
    result = false;
   }
   return result;
}

function checkexpiresWithin()
{
 var expiresWithin = document.getElementById("expiresWithin");

 if ( expiresWithin.value.trim().length > 0 && !(isSignedInteger(expiresWithin.value)) )
 {
    alert(messagesData.entervalidintegerforexpireswithin);
    expiresWithin.value = "";
    return true;
 }
 else
 {
	return false;
 }
}

function checkexpiresAfter()
{
 var expiresAfter = document.getElementById("expiresAfter");

 if ( expiresAfter.value.trim().length > 0 && !(isSignedInteger(expiresAfter.value)) )
 {
    alert(messagesData.entervalidintegerforexpiresafter);
    expiresAfter.value = "";
    return true;
 }
 else
 {
	return false;
 }
}

function createXSL(){
  var flag = true;//validateForm();
  if(flag) {
	$('uAction').value = 'createExcel';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_DisplayOnlyExcel','850','500','yes');
    document.genericForm.target='_DisplayOnlyExcel';
    var a = window.setTimeout("document.genericForm.submit();",50);
  }
}



