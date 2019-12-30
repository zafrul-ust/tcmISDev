function validateNoPickingForm() {
  if(!isInteger(document.genericForm.prNumber.value.trim(), true)) {
    alert(messagesData.mrNumberInteger);
    return false;
  }
  return true;
}

function pickQtyChg(qtyPickedField, pickQuantity)
{
  var qtyPicked = qtyPickedField.value;
  if(!isFloat(qtyPicked))
  {
     alert(messagesData.qtyInteger);
  }
  else
  if (qtyPicked!=null && qtyPicked.length > 0 && qtyPicked != 0)
  {
     if (qtyPicked != pickQuantity)
     {
        var message = messagesData.diffQty1 + qtyPicked + messagesData.diffQty2 + pickQuantity + messagesData.diffQty3;
        if (! confirm(message))
        {
           qtyPickedField.value='';
        }
     }
  }
}

function showErrorMessages()
{
  parent.showErrorMessages();
}

function myOnload()
{
 setResultFrameSize();
 if (!showUpdateLinks) /*Dont show any update links if the user does not have permissions*/
 {
  parent.document.getElementById("updateResultLink").style["display"] = "none";
 }
 else
 {
  parent.document.getElementById("updateResultLink").style["display"] = "";
 }

 var pickedMrCount = document.getElementById("pickedMrCount");
 if (pickedMrCount.value*1 > 0)
 {
   parent.document.getElementById("mainUpdateLinks").style["display"] = "";
   parent.document.getElementById("pickedResultLink").style["display"] = "";
 }
 else
 {
  parent.document.getElementById("pickedResultLink").style["display"] = "none";
 }
}

function submitSearchForm()
{
  var flag = validateNoPickingForm();
  if(flag) {
   parent.showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}

function hubChanged()
{
  var hub0 = document.getElementById("hub");
  var inventoryGroup0 = document.getElementById("inventoryGroup");
  var facility0 = document.getElementById("facilityId");
  var selectedHub = hub0.value;
  for (var i = inventoryGroup0.length; i > 0; i--)
  {
    inventoryGroup0[i] = null;
  }

  for (var i = facility0.length; i > 0; i--)
  {
    facility0[i] = null;
  }
  showInventoryGroupOptions(selectedHub);
  inventoryGroup0.selectedIndex = 0;
  inventoryGroupChanged();
}

function showInventoryGroupOptions(selectedHub)
{
  var inventoryGroupArray = new Array();
  inventoryGroupArray = altInventoryGroup[selectedHub];

  var inventoryGroupNameArray = new Array();
  inventoryGroupNameArray = altInventoryGroupName[selectedHub];

  if(inventoryGroupArray.length == 0)
  {
    setOption(0, messagesData.all, "", "inventoryGroup")
  }

  for (var i=0; i < inventoryGroupArray.length; i++)
  {
    setOption(i,inventoryGroupNameArray[i],inventoryGroupArray[i], "inventoryGroup")
  }
}

function inventoryGroupChanged() {
	var inventoryGroup0 = document.getElementById("inventoryGroup");
	var facilityId0 = document.getElementById("facilityId");
	var selectedInventoryGroup = inventoryGroup0.value;
	if(facilityId0 != null) {
		for (var i = facilityId0.length; i > 0; i--) {
			facilityId0[i] = null;
		}
	}
	showFacilityIdOptions(selectedInventoryGroup);
	facilityId0.selectedIndex = 0;
}

function showFacilityIdOptions(selectedInventoryGroup) {
	var facilityIdArray = new Array();
	facilityIdArray = altFacilityId[selectedInventoryGroup];
	var facilityNameArray = new Array();
	facilityNameArray = altFacilityName[selectedInventoryGroup];

	if(facilityIdArray == null || facilityIdArray.length == 0) {
		setOption(0,messagesData.all,"", "facilityId")
	} else {
		for (var i=0; i < facilityIdArray.length; i++) {
			setOption(i,facilityNameArray[i],facilityIdArray[i], "facilityId")
		}
	}
}