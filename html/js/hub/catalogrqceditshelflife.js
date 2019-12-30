var selectedItemTab = 0;

function validate() {
	var valid = true;
	var msg = "";
	for (var i = 0; i < $v("totalParts"); i++) {
		var shelfLifeDays = $v("part["+i+"].shelfLifeDays");
		var storageTemp = $v("part["+i+"].storageTemp");
		var shelfLifeBasis = $v("part["+i+"].shelfLifeBasis");
		
		if (shelfLifeDays.length > 0 && (isNaN(shelfLifeDays) || shelfLifeDays == 0 || shelfLifeDays < -1)) {
			msg += ((i*1)+1)+": "+messagesData.positive.replace('{0}',messagesData.shelflifedays);
			msg += "\n";
			valid = false;
		}
		
		if (shelfLifeDays.length == 0 ||
				storageTemp.length == 0 ||
				shelfLifeBasis.length == 0) {
			valid = false;
			msg += ((i*1)+1)+": "+messagesData.allrequired;
			break;
		}
	}
	if (msg.length > 0) {
		alert(msg);
	}
	return valid;
}

function submitUpdate() {
	var valid = validate();
	if (valid) {
		$('uAction').value = 'update';
		genericForm.submit();
	}
	return valid;
}

function updateAndClose() {
	if (parent.opener.updateShelfLifeStorageTemp) {
		var stslArray = [];
		for (var i = 0; i < $v("totalParts"); i++) {
			stslArray[i] = {
				shelfLifeDays:$v("part["+i+"].shelfLifeDays"),
				storageTemp:$v("part["+i+"].storageTemp"),
				shelfLifeBasis:$("part["+i+"].shelfLifeBasis").options[$("part["+i+"].shelfLifeBasis").selectedIndex].value,
				shelfLifeBasisDisplay:$("part["+i+"].shelfLifeBasis").options[$("part["+i+"].shelfLifeBasis").selectedIndex].innerHTML
			};
		}
		parent.opener.updateShelfLifeStorageTemp(stslArray);
	}
	window.close();
}

function toggleItem(itemNum) {
	if (itemNum != selectedItemTab) {
		hideItem(selectedItemTab);
		showItem(itemNum);
		selectedItemTab = itemNum;
	}
}

function hideItem(itemNum) {
	var itemLink =  $("itemLink" + itemNum);
	var itemLinkSpan =  $("itemLinkSpan" + itemNum);
	var itemDiv =  $("itemDiv" + itemNum);

	itemLink.className = "tabLeftSide";
	itemLinkSpan.className = "tabRightSide";
	itemDiv.style["display"] = "none";

}

function showItem(itemNum) {
	var itemLink =  $("itemLink" + itemNum);
	var itemLinkSpan =  $("itemLinkSpan" + itemNum);
	var itemDiv =  $("itemDiv" + itemNum);

	itemLink.className = "selectedTab";
	itemLinkSpan.className = "selectedSpanTab";
	itemDiv.style["display"] = "block";
}

function updateAllDistSelected() {
	var updateAllDistIG = false;
	var updateAllCheckbox = $("applyToAllDist");
	if (updateAllCheckbox != null) {
		updateAllDistIG = updateAllCheckbox.checked;
	}
	
	var i = 0;
	var stDropdown = $("part["+i+"].storageTemp");
	while (stDropdown != null) {
		stDropdown.disabled = updateAllDistIG;
		stDropdown = $("part["+(++i)+"].storageTemp");
	}
}

function cancel() {
	window.close();
}