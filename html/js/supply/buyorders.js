var selectedrow=null;
var selectedRowId=null;
var resultRowCount = 0;
var checkArray = new Array();
var poSupplierRowLoaded = new Array();
var showConvertLinks = false;

function setPoSupplierName(clickedRowId)
{
	var selectedOption = $("poSupplierId"+selectedRowId);
	$("buyOrdersInputBean["+selectedRowId+"].poSupplierName").value = selectedOption.options[selectedOption.selectedIndex].text;
	//TCMISDEV-3975, provide reason when change supplier
	poSupplierChanged(clickedRowId);
}

function poSupplierChanged(clickedRowId) {
    var loc = '/tcmIS/supply/loadChangeSupplierReason.do?callback=loadChangeSupplierReasonCallback&selectedRowId='+clickedRowId;
	openWinGeneric(loc,"selectreason","500","100","no","100","100");
}

function loadChangeSupplierReasonCallback(selectedRowId,reason_id) {
	$("reasonId_"+selectedRowId).value = reason_id;
}

function poSupplierClicked(clickedRowId) {
	var rowAlreadyClicked = false;
	for(var n=0;n<poSupplierRowLoaded.length;n++) {
		if (poSupplierRowLoaded[n] == clickedRowId) {
			rowAlreadyClicked = true;
			break;
		}
	}
	if (rowAlreadyClicked) {
		//don't need to reload data
		$("loadPoSupplierB"+clickedRowId).disabled = true;
	}else {
		poSupplierRowLoaded[poSupplierRowLoaded.length] = clickedRowId;
		showGetPoSupplier($("inventoryGroup"+clickedRowId).value,$("itemId"+clickedRowId).value,$("shipToCompanyId"+clickedRowId).value,$("shipToLocationId"+clickedRowId).value);
	}
}

function setSupplierId(clickedRowId,supplierId) {
	
	var rowAlreadyClicked = false;
	for(var n=0;n<poSupplierRowLoaded.length;n++) {
		if (poSupplierRowLoaded[n] == clickedRowId) {
			rowAlreadyClicked = true;
			break;
		}
	}
	if (rowAlreadyClicked) {
		//don't need to reload data
		$("loadPoSupplierB"+clickedRowId).disabled = true;
		orgSupplierValue = $( "poSupplierId"+clickedRowId ).value;
		if( orgSupplierValue != supplierId ) {
			$( "poSupplierId"+clickedRowId ).value = supplierId;
			setPoSupplierName(clickedRowId);
		}
	}else {
		poSupplierRowLoaded[poSupplierRowLoaded.length] = clickedRowId;
		showGetPoSupplier($("inventoryGroup"+clickedRowId).value,$("itemId"+clickedRowId).value,$("shipToCompanyId"+clickedRowId).value,$("shipToLocationId"+clickedRowId).value,supplierId,clickedRowId);
	}
}

function getBestDuyContractId(prNumber,selectedRowId) {
	callToServer('getBestDuyContractId.do?callback=getBestDuyContractIdCallback&prNumber='+prNumber+'&selectedRowId='+selectedRowId);
}

function getBestDuyContractIdCallback(selectedRowId,bestContractId) {
	$("bestPriceDbuyContractId_"+selectedRowId).value = bestContractId;
	showSourcingInfoNew(selectedRowId);
}


function showGetPoSupplier(inventoryGroup,itemId,shipToCompanyId,shipToLocationId,supplierId,orgSelectedRowId) {
	callToServer('/tcmIS/supply/loadposupplier.do?callback=showGetPoSuppliercallback&itemId='+itemId+'&inventoryGroup='+encodeURIComponent(inventoryGroup)+'&shipToCompanyId='+encodeURIComponent(shipToCompanyId)+'&shipToLocationId='+encodeURIComponent(shipToLocationId)+'&supplierId='+supplierId+'&orgSelectedRowId='+orgSelectedRowId);
}

function showGetPoSuppliercallback(poSupplierColl,useSpplierId,orgSelectedRowId) {
	for (var i=0; i < poSupplierColl.length; i++) {
		if (poSupplierColl[i].supplier != $("poSupplierId"+selectedRowId).value) {
			//add new supplier to drop down
			setOption($("poSupplierId"+selectedRowId).options.length,poSupplierColl[i].supplierName,poSupplierColl[i].supplier,"poSupplierId"+selectedRowId);
		}
	}
	if ( useSpplierId ) {
		if( $( "poSupplierId"+orgSelectedRowId ).value != useSpplierId ) {
			$( "poSupplierId"+orgSelectedRowId ).value = useSpplierId;
			setPoSupplierName(orgSelectedRowId);
		}
	}
	$("loadPoSupplierB"+selectedRowId).disabled = true;
}


function igchanged() {
  var inventoryGroup0 = document.getElementById("inventoryGroup");
  var facilityId0 = document.getElementById("facilityId");
  var selectedInventoryGroup = inventoryGroup0.value;
//  if(facilityId0 != null) {
    for (var i = facilityId0.length; i > 0; i--) {
      facilityId0[i] = null;
    }
//  }
  showFacilityIdOptions(selectedInventoryGroup);
  facilityId0.selectedIndex = 0;
}


function showFacilityIdOptions(selectedInventoryGroup) {
  var facilityIdArray = new Array();
  facilityIdArray = altFacilityId[selectedInventoryGroup];
  var facilityNameArray = new Array();
  facilityNameArray = altFacilityName[selectedInventoryGroup];

  if(facilityIdArray == null || facilityIdArray.length == 0) {
    setOption(0,messagesData.all,"ALL", "facilityId")
  }
  else {
    for (var i=0; i < facilityIdArray.length; i++) {
      setOption(i,facilityNameArray[i],facilityIdArray[i], "facilityId")
    }
  }
}

function validateForm() {
	var errorMessage = messagesData.validvalues+"\n\n";
	var searchText = document.getElementById("searchText");
	if(searchText.value.trim().length != 0) {
	 var searchWhat = document.getElementById("searchWhat");
	 if(searchWhat.value == 'ITEM_ID') {
		if(!isInteger(searchText.value.trim(), true)) {
		  alert(messagesData.itemIdInteger);
		  return false;
		}
	 }
	 else if(searchWhat.value == 'MR_NUMBER') {
		if(!isFloat(searchText.value.trim(), true)) {
		  alert(errorMessage+messagesData.mr);
		  return false;
		}
	 }
	 else if(searchWhat.value == 'PR_NUMBER') {
		if(!isFloat(searchText.value.trim(), true)) {
		  alert(errorMessage+messagesData.pr);
		  return false;
		}
	 }
	 else if(searchWhat.value == 'RADIAN_PO') {
		if(!isFloat(searchText.value.trim(), true)) {
		  alert(errorMessage+messagesData.po);
		  return false;
		}
	 }
	}

	if(document.getElementById("confirmedButNoAssociation").checked == true) {
		confirmedButNoAssociationChanged();
	}else {
		//can't pick All statuses or confirmed or closed without search criteria
		//or picking specific buyers
    // If a hub is picked they can view all open (unconfirmed POs for that hub)
    if (document.getElementById("opsEntityId").value.length ==0 && document.getElementById("buyerId").value == '0' && (searchText == null || searchText.value.trim().length == 0)) {
      alert(messagesData.missingSearchText);
		  document.getElementById("searchText").focus();
			return false;
    }
    else if (document.getElementById("hub").value.length > 0 && document.getElementById("boForConfirmedPo").checked && (searchText == null || searchText.value.trim().length == 0)) {
      alert(messagesData.missingSearchText);
		  document.getElementById("searchText").focus();
			return false;
    }
    else
    {
    var ob = document.getElementById("statusArray");
		if (ob != null) {
			for (var i = 0; i < ob.options.length; i++) {
				 if (ob.options[ i ].selected) {
					if (ob.options[i].value == 'All Statuses' ||
						 ob.options[i].value == 'Closed' ||
						 ob.options[i].value == 'Confirmed') {
						if ((searchText == null || searchText.value.trim().length == 0) && document.getElementById("buyerId").value == '0') {
							alert(messagesData.missingSearchText);
							document.getElementById("searchText").focus();
							return false;
						}
					//if All except closed - have to pick a hub and Only Unconfirmed PO #s
					}else if (ob.options[i].value == 'All Except Closed') {
//						if (!document.getElementById("boForUnconfirmedPo").checked) {
//							if ((searchText == null || searchText.value.trim().length == 0) && document.getElementById("buyerId").value == '0') {
//								alert(messagesData.missingSearchText);
//								document.getElementById("searchText").focus();
//								return false;
//							}
//						}else {
							if ((document.getElementById("hub").value.length == 0) || (document.getElementById("boForConfirmedPo").checked)) {
								if ((searchText == null || searchText.value.trim().length == 0) && document.getElementById("buyerId").value == '0') {
									alert(messagesData.missingSearchText);
									document.getElementById("searchText").focus();
									return false;
								}
							}
/*
							if (document.getElementById("boForConfirmedPo").checked) {
								if ((searchText == null || searchText.value.trim().length == 0) && document.getElementById("buyerId").value == '0') {
									alert(messagesData.missingSearchText);
									document.getElementById("searchText").focus();
									return false;
								}
							}*/
//						}
					 }
				 }
			}
		}
   }
  }

	return true;
}

function validateCreatePo() {
	//make sure at least one row is selected
/*
	var checked = false;
	for(var i=0; i<resultRowCount; i++) {
		if (document.getElementById("rowNumber" + i) != null) {
			var rowCheckbox = document.getElementById("rowNumber" + i);
			if(rowCheckbox.checked == true) {
				checked = true;
				break;
			}
		}
	}
	if (!checked) {
		alert(messagesData.noRowSelected);
	}
	return checked;
*/
  for(a in checkArray)
    return true;
  alert(messagesData.noRowSelected);
  return false;
}

function showAllocationLegend(){
  openWinGeneric("/tcmIS/help/allocationpagelegend.html","allocationpagelegend","290","300","no","50","50")
}


function submitSearchForm() {
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();
  var flag = validateForm();
  if(flag) {
	document.buyOrderForm.target='resultFrame';
	document.getElementById('action').value="search";
	parent.showPleaseWait();
	return true;
  }else {
	  return false;
  }
}

function init() { /*This is to initialize the YUI*/
  this.cfg = new YAHOO.util.Config(this);
  if (this.isSecure) {
    this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
  }
/*Yui pop-ups need to be initialized onLoad to make them work correctly.
If they are not initialized onLoad they tend to act weird*/
//  legendWin = new YAHOO.widget.Panel("showLegendArea", { width:"300px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:false, draggable:true, modal:false } );
//  legendWin.render();
}

/*The reson this is here because you might want a different width/proprties for the pop-up div depending on the page*/
function showErrorMessages()
{
	parent.showErrorMessages();
}

function myOnload(numberOfRows, poNumber){

//showRadianPo(poNumber);
  if(poNumber != null && poNumber.length > 0) {
    //loc = "/tcmIS/supply/purchorder?po=" + poNumber + "&Action=searchlike&subUserAction=po";
    loc = "/tcmIS/supply/purchaseorder.do?po=" + poNumber + "&Action=searchlike";
//alert("po:" + loc);
    //openWinGeneric(loc,"showradianPo","800","600","yes","50","50");
    try
    {
      parent.parent.openIFrame("purchaseOrder"+poNumber+"",loc,""+messagesData.purchaseorder+" "+poNumber+"","","N");
    }
    catch (ex)
    {
      openWinGeneric(loc,"showradianPo_"+poNumber+"","800","600","yes","50","50");
    }
  }
  if(numberOfRows != null) {
    resultRowCount = numberOfRows;
  }
  displaySearchDuration();
  setResultFrameSize();
  if (!showUpdateLinks) {
    parent.document.getElementById("updateResultLink").style["display"] = "none";
  }
  else {
    parent.document.getElementById("updateResultLink").style["display"] = "";
  }

  parent.$("createPoResultLink").innerHTML = '';
  parent.document.getElementById("createPoResultLink").style["display"] = "none";

  parent.document.getElementById("convertPoResultLink").style["display"] = "none";

  var lastSelectPR = document.getElementById("lastSelectedPr");
  if (lastSelectPR && lastSelectPR.value) {
	  var lastprNumber = lastSelectPR.value;
	  for (var index = 0; index < resultRowCount; index++) {
		  if (document.getElementById("prNumber" + index).value == lastprNumber) {
			  document.getElementById("buyOrdersInputBean[" + index + "].comments").focus();
			  break;
		  }
	  }
  }

  resetApplicationTimer();
}

function searchOnload() {
  setSearchFrameSize();
  var status = document.getElementById("statusArray");
  var optionArray = status.options;
  for(var i=0;i<optionArray.length;i++ ){
    if(optionArray[i].text == 'New') {
      optionArray[i].selected=true;
    }
  }
  document.getElementById("companyIdArray").selectedIndex = 0;
  document.getElementById("boWithNoPo").checked=true;
//  showHub();
}

function boForUnconfirmedPoChanged() {
  document.getElementById("confirmedButNoAssociation").checked=false;
}

function boWithNoPoChanged() {
  document.getElementById("confirmedButNoAssociation").checked=false;
}

function boForConfirmedPoChanged() {
  document.getElementById("confirmedButNoAssociation").checked=false;
}

function confirmedButNoAssociationChanged() {
  document.getElementById("boForUnconfirmedPo").checked=false;
  document.getElementById("boWithNoPo").checked=false;
  document.getElementById("boForConfirmedPo").checked=false;
  if(document.getElementById("confirmedButNoAssociation").checked == true) {
    var status = document.getElementById("statusArray");
    var optionArray = status.options;
    for(var i=0;i<optionArray.length;i++ ){
      if(optionArray[i].text == 'All Except Closed') {
        optionArray[i].selected=true;
      }
      else {
        optionArray[i].selected=false;
      }
    }
  }
}

function checkRowSelectionIntercept(rowNumber, bpoDetail) {
	var headerMsg = messagesData.buyorder + " " + $("prNumber" + rowNumber).value;
	var currentCheckbox = $("rowNumber" + rowNumber);
	if (currentCheckbox && currentCheckbox.checked) {
		customDialog(
				{text: messagesData.expertReviewConfirmMsg, header: headerMsg, 
					severity: "Warning",
					dialogType: "confirm",
					continueText: messagesData.continueMsg, 
					cancelText: messagesData.talktoexpertreview,
					onContinue: function() { checkRowSelection(rowNumber, bpoDetail); },
					onCancel: function() { currentCheckbox.checked = false; }
				}
		);
	}
	else {
		checkRowSelection(rowNumber, bpoDetail);
	}
	return true;
}


function checkRowSelection(rowNumber, bpoDetail) {
	//don't add row to array if exist
	var rowAlreadyExist = false;
	for(var j=0; j<checkArray.length; j++) {
		if (checkArray[j] == rowNumber) {
			rowAlreadyExist = true;
			break;
		}
	}
	if (!rowAlreadyExist) {
		checkArray[checkArray.length] = rowNumber;
	}

  var asLeastOneRowChecked = false;
  var currentCheckbox = document.getElementById("rowNumber" + rowNumber);
  if(currentCheckbox.checked == true) {
	 asLeastOneRowChecked = true;
	 if(bpoDetail == 'Y') {
      alert(messagesData.blanketOrderMessage);
    }
    //var itemId = null;
    var inventoryGroup = null;
    var companyId = null;
    var customerPo = null;
	 var currentSupplyPath = null;
	 //var poSupplierId = null;
    for(var i=0; i<checkArray.length; i++) {
      var checkRowNum = checkArray[i];
      var rowCheckbox = document.getElementById("rowNumber" + checkRowNum);
      if(rowCheckbox != null && rowCheckbox.checked == true) {
        //var rowItemId = document.getElementById("itemId" + checkRowNum);
        var rowInventoryGroup = document.getElementById("inventoryGroup" + checkRowNum);
        var rowCompanyId = document.getElementById("companyId" + checkRowNum);
        var rowCustomerPo = document.getElementById("customerPoNumber" + checkRowNum);
		  var rowSupplyPath = document.getElementById("supplyPath" + checkRowNum);
		  //var rowPoSupplierId = document.getElementById("poSupplierId" + checkRowNum);
		  //var tmpRowPoSupplierId = rowPoSupplierId.value;
		  /*
		  if(itemId == null) {
          itemId = rowItemId.value;
        }
        */
        if(inventoryGroup == null) {
          inventoryGroup = rowInventoryGroup.value;
        }
        if(companyId == null) {
          companyId = rowCompanyId.value;
        }
        if(customerPo == null) {
          customerPo = rowCustomerPo.value;
        }
		  if(currentSupplyPath == null) {
          currentSupplyPath = rowSupplyPath.value;
        }
			/*
		  if(poSupplierId == null) {
          poSupplierId = tmpRowPoSupplierId;
        }else {
			  if (tmpRowPoSupplierId.length == 0) {
				  if (poSupplierId.length > 0) {
					  tmpRowPoSupplierId = poSupplierId;
				  }
			  }else {
				  if (poSupplierId.length == 0) {
				  	poSupplierId = tmpRowPoSupplierId;
				  }
			  }
		  }
		  */

		  if(companyId == 'SWA' && customerPo != rowCustomerPo.value) {
          alert(messagesData.swaPoMessage);
          currentCheckbox.checked = false;
          return false;
        }
		  if(inventoryGroup != rowInventoryGroup.value) {
			 alert(messagesData.createPoMessage);
          currentCheckbox.checked = false;
          return false;
        }
		  if(currentSupplyPath != rowSupplyPath.value) {
			 alert(messagesData.cannotCombineEdiWbuyOrders);
          currentCheckbox.checked = false;
          return false;
        }
		}
    }
  }else {
	  //if unchecking a line
	  //check to see if any line still checked
	  for(var i=0; i<checkArray.length; i++) {
      	var checkRowNum = checkArray[i];
     		var rowCheckbox = document.getElementById("rowNumber" + checkRowNum);
      	if(rowCheckbox != null && rowCheckbox.checked == true) {
				asLeastOneRowChecked = true;
				break;
			}
	  }
  }

  if (asLeastOneRowChecked) {
	  if (showConvertLinks) {
		  	parent.document.getElementById("convertPoResultLink").style["display"] = "";
	  }
	  else {
		  	parent.document.getElementById("convertPoResultLink").style["display"] = "none";
	  }
	  
	  	//if first time, then set it selected supplier to the first checked line
		if ($("selectedSupplierForPo").value.length == 0) {
			if ($("poSupplierId"+rowNumber).value.length == 0) {
				$("selectedSupplierForPo").value = "No Supplier";
				$("selectedSupplierForPoName").value = messagesData.noSupplier;
			}else {
				$("selectedSupplierForPo").value = $("poSupplierId"+rowNumber).value;
				$("selectedSupplierForPoName").value = $("poSupplierId"+rowNumber).options[$("poSupplierId"+rowNumber).selectedIndex].text;
			}
		}
	   //set display data
	   if(currentCheckbox.checked == true) {
			$("poSelectedSupplierName"+rowNumber).innerHTML = $("selectedSupplierForPoName").value;
		}else {
			$("poSelectedSupplierName"+rowNumber).innerHTML = "";
		}
	  	//show create po link
	   try {
		  if (!showCreatePoResultLink) {
			 parent.document.getElementById("createPoResultLink").style["display"] = "none";
		  }else {
			 var createPoLabel = "";
			 if ($("selectedSupplierForPo").value == 'No Supplier') {
				createPoLabel = messagesData.createPoWithNoSupplier;
			 }else {
				createPoLabel = messagesData.createPoForSupplier+' : '+$("selectedSupplierForPoName").value;
			 }
			 parent.$("createPoResultLink").innerHTML = ' | <a href="#" onclick="createPo()"; return false;">'+createPoLabel+'</a>';
			 parent.document.getElementById("createPoResultLink").style["display"] = "";
		  }
		}catch (ex2){
			parent.document.getElementById("createPoResultLink").style["display"] = "none";
		}

  }else {
	   $("poSelectedSupplierName"+rowNumber).innerHTML = "";
	   $("selectedSupplierForPo").value = "";
	   parent.$("createPoResultLink").innerHTML = '';
	   parent.document.getElementById("createPoResultLink").style["display"] = "none";
	   parent.document.getElementById("convertPoResultLink").style["display"] = "none";	  
  }

} //end of method


function showBuyOrderTransfer(prNumber,shipTo,inventory,company,transferRoute,item,type,qty){
alert("Contact Tech Support");
//don't want comma delimited values
/*
    loc = "/tcmIS/purchase/showtcmbuys?Action=buildBuyOrderTransferPage&prNumber=";
    loc = loc + prNumber;
    loc = loc + "&shipTo=" + shipTo;
	 loc = loc + "&inventory=" + inventory;
	 loc = loc + "&company=" + company;
	 loc = loc + "&transferRoute=" + transferRoute;
	 loc = loc + "&item=" + item;
	 loc = loc + "&type=" + type;
	 loc = loc + "&qty=" + qty;

    openWinGeneric(loc,"showBuyOrderTransferPage","650","300","yes","80","80")
*/
}

function showSchedule() {
  var mrNumber = document.getElementById("mrNumber" + selectedRowId).value;
  var mrLineItem = document.getElementById("mrLineItem" + selectedRowId).value;

  loc = "/tcmIS/purchase/showtcmbuys?Action=showschedulde&itemID=" + mrNumber + "&lineID="+mrLineItem;
  openWinGeneric(loc,"tcmbuyhistory","400","460","yes","80","80")
}

function getSuggestedSupplier() {
  var partId = document.getElementById("partId" + selectedRowId).value;
  var requestId = document.getElementById("requestId" + selectedRowId).value;
  var catalogId = document.getElementById("catalogId" + selectedRowId).value;

  loc = "/tcmIS/purchase/posupplier?Action=suggestedsupp" + "&catpartno=" + partId + "&requestid=" +requestId+ "&catalogid=" +catalogId;
  openWinGeneric(loc,"suggestedsupplier","450","200","yes","200","200")
}

function editItemNotes() {
  var itemId = document.getElementById("itemId" + selectedRowId).value;
  loc = "/tcmIS/supply/edititemnotes.do?itemId=" + itemId;
  openWinGeneric(loc,"editItemNotes","800","600","yes","50","50");
}

function showShippingInfo() {
  var itemId = document.getElementById("itemId" + selectedRowId).value;
  var loc = "/tcmIS/hub/shippinginfo.do?uAction=search&itemId=" + itemId;
   	
  parent.parent.openIFrame("ShippingInfo"+itemId,loc,""+messagesData.shipinfo+" "+itemId,"","Y");
}

function supplierItemNotes() {
  var itemId = document.getElementById("itemId" + selectedRowId).value;
  var itemDesc = document.getElementById("itemDesc" + selectedRowId).value;
  var opsEntityId = document.getElementById("opsEntityId" + selectedRowId).value;
  var poSupplierId = "";
  var poSupplierName = "";
  
  if(document.getElementById("poSupplierId" + selectedRowId) != null && $v("poSupplierId"+ selectedRowId).length > 0) {
  	poSupplierId = document.getElementById("poSupplierId" + selectedRowId).value;
  	poSupplierName = $("poSupplierId" + selectedRowId).options[$("poSupplierId" + selectedRowId).selectedIndex].text;
  }

  loc = "showsupplieritemnotes.do?uAction=search&searchMode=is&searchField=itemId&searchArgument="+itemId+
  		"&itemId="+itemId+"&itemDesc="+encodeURIComponent(itemDesc)+"&opsEntityId="+opsEntityId+
  		"&supplier=" + poSupplierId+"&supplierName=" + encodeURIComponent(poSupplierName);
  openWinGeneric(loc,"editItemNotes","1000","300","yes","50","50");
}

function showOpenBpo() {
  var prNumber = document.getElementById("prNumber" + selectedRowId).value;

  loc = "/tcmIS/supply/showblankets?prnumber=" + prNumber + "&Action=showblankets";
  openWinGeneric(loc,"OpenBlankets","800","400","yes","50","50")
}

function showItemTcmBuys() {
  var itemId = document.getElementById("itemId" + selectedRowId).value;
  var opsEntityId = document.getElementById("opsEntityId" + selectedRowId).value;

  loc = "/tcmIS/supply/tcmbuyhistory.do?itemId=" + itemId;
  loc = loc + "&opsEntityId=" +opsEntityId
  openWinGeneric(loc,"apptcmbuyhistory","800","600","yes","50","50")
}

function showMismatch() {
  var poNumber = document.getElementById("radianPo" + selectedRowId).value.trim();

  loc = "/tcmIS/supplier/mismatch.do?po="+poNumber;
  openWinGeneric(loc,"pomismatch","900","600","yes","80","80")
}
function leadTimePlots() {
	openWinGeneric('../distribution/createsupplyleadtimechart.do?catPartNo='+ encodeURIComponent( document.getElementById("partId" + selectedRowId).value.trim() ) +
            '&inventoryGroup=' + document.getElementById("inventoryGroup" + selectedRowId).value.trim() +
            '&catalogId=' + document.getElementById("catalogId" + selectedRowId).value.trim() +
            //'&catalogCompanyId=' + document.getElementById("catalogCompanyId" + selectedRowId).value.trim() +
            //'&partGroupNo=' + document.getElementById("partGroupNo" + selectedRowId).value.trim() +
            '&inventoryGroupName=' + document.getElementById("inventoryGroup" + selectedRowId).value.trim()
            ,"LeadTimePlots","800","400",'yes' );
}

function showRadianPo() {

  var poNumber = document.getElementById("radianPo" + selectedRowId).value.trim();

//alert("selectedRowId:"+selectedRowId+"   poNumber:"+poNumber);
    //loc = "/tcmIS/supply/purchorder?po=" + poNumber + "&Action=searchlike&subUserAction=po";
  loc = "/tcmIS/supply/purchaseorder.do?po=" + poNumber + "&Action=searchlike";
    //openWinGeneric(loc,"showradianPo","800","600","yes","50","50");

    try
    {
      parent.parent.openIFrame("purchaseOrder"+poNumber+"",loc,""+messagesData.purchaseorder+" "+poNumber+"","","N");
    }
    catch (ex)
    {
      openWinGeneric(loc,"showradianPo_"+poNumber+"","800","600","yes","50","50");
    }
}

function showSurplusInventory() {
  var itemId = document.getElementById("itemId" + selectedRowId).value;
  loc = "/tcmIS/supply/surplusinventory.do?itemId=" + itemId;
  openWinGeneric(loc,"showsurplusinventory","800","400","yes","50","50")
}

function statusChanged(rowNumber) {
  var supplyPath = document.getElementById("supplyPath" + rowNumber);
  var status = document.getElementById("status" + rowNumber);
  if (supplyPath.value == "Dbuy") {
    if (status.value.search(/DBuy/) == -1) {
      alert(messagesData.dbuyMessage);
    }
  }
  if (supplyPath.value == "Wbuy") {
    if (status.value.search(/WBuy/) == -1) {
      alert(messagesData.dbuyMessage);
    }
  }
}

function generateExcel() {
  var flag = validateForm();
  if(flag) {
    var action = document.getElementById("action");
    action.value = 'createExcel';
	 var hubId = document.getElementById("hub");
	 document.getElementById("hubName").value = hubId[hubId.selectedIndex].text;
	 var inventoryGroup = document.getElementById("inventoryGroup");
	 document.getElementById("inventoryGroupName").value = inventoryGroup[inventoryGroup.selectedIndex].text;
	 var facilityId = document.getElementById("facilityId");
	 document.getElementById("facilityName").value = facilityId[facilityId.selectedIndex].text;
	 var companyId = document.getElementById("companyIdArray");
	 document.getElementById("companyName").value = companyId[companyId.selectedIndex].text;
	 var buyerId = document.getElementById("buyerId");
	 document.getElementById("buyerName").value = buyerId[buyerId.selectedIndex].text;
	 var searchWhat = document.getElementById("searchWhat");
	 document.getElementById("searchWhatDesc").value = searchWhat[searchWhat.selectedIndex].text;
	 var searchType = document.getElementById("searchType");
	 document.getElementById("searchTypeDesc").value = searchType[searchType.selectedIndex].text;
	 var supplyPath = document.getElementById("supplyPath");
	 document.getElementById("supplyPathDesc").value = supplyPath[supplyPath.selectedIndex].text;
	 var status = document.getElementById("statusArray");
	 document.getElementById("statusDesc").value = status[status.selectedIndex].text;
	 var sortBy = document.getElementById("sortBy");
	 document.getElementById("sortByDesc").value = sortBy[sortBy.selectedIndex].text;
	 openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_BuyOrderGenerateExcel','650','600','yes');
    document.buyOrderForm.target='_BuyOrderGenerateExcel';
    var a = window.setTimeout("document.buyOrderForm.submit();",500);
  }
}

function selectRow(rowId)
{
   var selectedRow = document.getElementById("rowId"+rowId+"");
   var selectedRowClass = document.getElementById("colorClass"+rowId+"");
   var lastSelectPR = document.getElementById("lastSelectedPr");
   lastSelectPR.value = document.getElementById("prNumber" + rowId).value;

   selectedRow.className = "selected"+selectedRowClass.value+""; 
  
   if (selectedRowId != null && rowId != selectedRowId)
   {
     var previouslySelectedRow = document.getElementById("rowId"+selectedRowId+"");
     var previouslySelectedRowClass = document.getElementById("colorClass"+selectedRowId+"");
     previouslySelectedRow.className = ""+previouslySelectedRowClass.value+"";
   } 
   selectedRowId = rowId;

   var deliveryType = document.getElementById("deliveryType["+selectedRowId+"]").value;
   var oldStatus = document.getElementById("oldStatus["+selectedRowId+"]").value;
   var poNumber = document.getElementById("radianPo" + selectedRowId).value.trim();
   var bpoDetail = document.getElementById("bpoDetail" + selectedRowId).value;
   var surplusInventory = document.getElementById("surplusInventory" + selectedRowId).value;
   var consolidationAllowed = document.getElementById("consolidationAllowed" + selectedRowId).value;
   var itemId = $v("itemId" + selectedRowId);
   
   vitems = new Array();
   vitems[vitems.length ] = "text="+messagesData.showsuggestedsupplier+";url=javascript:getSuggestedSupplier();";
   vitems[vitems.length ] = "text="+messagesData.showitempurchasehistory+";url=javascript:showItemTcmBuys();";
   vitems[vitems.length ] = "text="+messagesData.itemNotes+";url=javascript:editItemNotes();";
   vitems[vitems.length ] = "text="+messagesData.supplieritemnotes+";url=javascript:supplierItemNotes();";
   vitems[vitems.length ] = "text="+messagesData.shipinfo+";url=javascript:showShippingInfo();";
   vitems[vitems.length ] = "text="+messagesData.showsourcinginfo+";url=javascript:showSourcingInfo();";
   vitems[vitems.length ] = "text="+messagesData.leadtimeplots+";url=javascript:leadTimePlots();";
   
   if (itemId.length != 0)
	  vitems[vitems.length ] = "text="+messagesData.quickview+";url=javascript:showQuickItemView();";
   
   if (bpoDetail == "Y")
   {
	  vitems[vitems.length ] = "text="+messagesData.showblankets+";url=javascript:showOpenBpo();";
   }
   if (surplusInventory == "Y")
   {
	  vitems[vitems.length ] = "text="+messagesData.surplusinventory+";url=javascript:showSurplusInventory();";
   }
   if (consolidationAllowed == "Y")
   {
	  vitems[vitems.length ] = "text="+messagesData.showbuyordertransfer+";url=javascript:showBuyOrderTransfer();";
   }
   
   if (deliveryType == "Y")
   {
	  vitems[vitems.length ] = "text="+messagesData.showdeliveryschedule+";url=javascript:showSchedule();";
   }

   if (oldStatus == 'ProblemDBuy')
   {
	  vitems[vitems.length ] = "text="+messagesData.mismatchcomments+";url=javascript:showMismatch();";
   }

   if (poNumber.length != 0)
   {
	  vitems[vitems.length ] = "text="+messagesData.viewpurchaseorder+";url=javascript:showRadianPo();";
   }
   
// alert(vitems); 
   replaceMenu('buyOrdersMenu',vitems);  
   
   toggleContextMenu('buyOrdersMenu');
} 


function replaceMenu(menuname,menus){
	  var i = mm_returnMenuItemCount(menuname);

	  for(;i> 1; i-- )
			mm_deleteItem(menuname,i);

	  for( i = 0; i < menus.length; ){
 		var str = menus[i];

 		i++;
		mm_insertItem(menuname,i,str);
		// delete original first item.
    	if( i == 1 ) mm_deleteItem(menuname,1);
      }
}

function showQuickItemView() {
	var inventoryGroup = $v("inventoryGroup" + selectedRowId);
	var inventoryGroupName  =  $v("inventoryGroupName" + selectedRowId);
	var itemId = $v("itemId" + selectedRowId);
	var itemDesc = $v("itemDesc" + selectedRowId);
	
	var loc = "/tcmIS/distribution/quickquote.do?readonly=Y&opsEntityId="+encodeURIComponent($v('opsEntityId'))+"&inventoryGroupName="+inventoryGroupName+
			  "&itemDesc="+encodeURIComponent(itemDesc)+"&currencyId="+$v("currencyId")+
			  "&inventoryGroup="+inventoryGroup+"&itemId="+itemId;
	openWinGeneric(loc,"quickView","1100","800","yes","80","80","no");
	//children[children.length] = openWinGenericDefault(loc,"quickView","yes","80","80","no");
}


function showSourcingInfo(rowId) { 
	if( rowId == null )
		rowId = selectedRowId;
//	 supplier=" +poSupplierId;
//	 var prNumber = $("prNumber" + selectedRowId).value; 
//		window.setTimeout('getBestDuyContractId('+prNumber+','+selectedRowId+');', 10);
//it's a callback, therefore needs current rowid.	  
//	getBestDuyContractId($("prNumber" + RowId).value,RowId);
	showSourcingInfoNew(rowId);
}

function showSourcingInfoNew(selectedRowId) {
	  var itemId = document.getElementById("itemId" + selectedRowId).value;
	  var inventoryGroup = document.getElementById("inventoryGroup" + selectedRowId).value;
	  var opsEntityId = document.getElementById("opsEntityId" + selectedRowId).value;

//	  loc = "/tcmIS/distribution/showsupplierpl.do?itemId=" + itemId;
	  var loc = "/tcmIS/distribution/buyordersupplierpricelist.do?uAction=search&searchField=itemId|number&searchMode=is&searchArgument=" + itemId;
	  loc = loc + "&branchPlant="+document.getElementById("branchPlant" + selectedRowId).value;
	  loc = loc + "&inventoryGroup=" +inventoryGroup;
	  loc = loc + "&opsEntityId=" +opsEntityId;
	  loc = loc + "&showExpired=N";// +inventoryGroup +
	  loc = loc + "&uPrice="+document.getElementById("catalogPrice_" + selectedRowId).value;
	  loc = loc + "&itemId="+document.getElementById("itemId" + selectedRowId).value;
	  loc = loc + "&prNumber="+document.getElementById("prNumber" + selectedRowId).value;
	  loc = loc + "&orderQuantity="+document.getElementById("orderQuantity" + selectedRowId).value;
	  loc = loc + "&shipToLocationId="+encodeURIComponent($("shipToLocationId" + selectedRowId).value);
	  loc = loc + "&shipToCompanyId="+encodeURIComponent($("shipToCompanyId" + selectedRowId).value);
	  loc = loc + "&ShipToLocationId="+encodeURIComponent($("shipToLocationId" + selectedRowId).value);
	  loc = loc + "&needDate="+document.getElementById("needDate" + selectedRowId).value;
	  loc = loc + "&companyId="+encodeURIComponent($("companyId" + selectedRowId).value);
	  loc = loc + "&customerPoNumber="+encodeURIComponent($("customerPoNumber" + selectedRowId).value);
	  oldPoSupplierName = document.getElementById("buyOrdersInputBean[" + selectedRowId +"].oldPoSupplierName").value;
	  if( oldPoSupplierName != null ) {
		  loc = loc + "&oldBuyerId=" + $("oldBuyerId[" + selectedRowId +"]").value;
		  loc = loc + "&oldPoSupplierName=" +encodeURIComponent(oldPoSupplierName.value);
		  loc = loc + "&oldComments=" + encodeURIComponent( $("oldComments[" + selectedRowId +"]").value );
	  }
	  loc = loc + "&buyerId=" + $("buyerId" + selectedRowId).value;
	  loc = loc + "&comments=" + encodeURIComponent( $("buyOrdersInputBean[" + selectedRowId +"].comments").value );
	  loc = loc + "&supplyPath=" + $("supplyPath" + selectedRowId).value;
	  loc = loc + "&selectedRowId="+selectedRowId;
	  var rowAlreadyClicked = false;
		for(var n=0;n<poSupplierRowLoaded.length;n++) {
			if (poSupplierRowLoaded[n] == selectedRowId) {
				rowAlreadyClicked = true;
				break;
			}
		}
	  loc = loc + "&rowAlreadyClicked="+rowAlreadyClicked;
	  
	  if(document.getElementById("poSupplierId" + selectedRowId) != null) {
	  	var poSupplierId = document.getElementById("poSupplierId" + selectedRowId).value;
	  	var supplierName = document.getElementById("poSupplierId" + selectedRowId).options[document.getElementById("poSupplierId" + selectedRowId).selectedIndex].text;
	  	loc = loc + "&supplier=" +poSupplierId;
	    loc = loc + "&poSupplierName=" +encodeURIComponent(supplierName);
	  }
//	  alert(loc);
	  openWinGeneric(loc,"showsupplierpl","800","600","yes","50","50")
	}
