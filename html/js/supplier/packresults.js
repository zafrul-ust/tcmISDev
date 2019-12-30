function $(a) {
	return document.getElementById(a);
}
// assuming mm/dd/yyyy format.
function dateToInt(strval) {
 var ind = strval.indexOf('/');
 var intval = strval.substr(ind+4,4)+strval.substr(ind-2,2) + strval.substr(ind+1,2);
 return intval;
}

function okCheck(rowNum) {
    var totalLines = document.getElementById("totalLines").value;
    totalLines = totalLines*1;
    if (totalLines > 50)
    {
        return true;
    }
    //checks to make sure there is a case ID
	var errorHeader = messagesData.validValues + "\n\n";
	var errorBody = "";
	var errorMsg = "";
	var errorCount = 0 ;
	var focus = null;
	var requiredF = new Array (
			 $('boxId_'+rowNum)
			/*,
			 $('dateShipped_'+rowNum),
			 $('dateDelivered_'+rowNum)*/
		);
	if($v('trackSerialNumber_'+rowNum)=='Y')
		requiredF.push($('serialNumber_'+rowNum));		
  	var compareDate = true;
  if( $('ok_'+rowNum).checked )
  {
  for(ii = 0 ; ii < requiredF.length; ii ++ ) {
  		requiredF[ii].value = requiredF[ii].value.trim();
		if(requiredF[ii].value.length == 0 ) {
			errorCount++;
			errorBody += requiredTitle[ii]+ "\n";
			if( focus == null )
				focus = requiredF[ii];
		}
		/*else
		if( ii > 0 && (requiredF[ii].value.length !=10 || !checkdate(requiredF[ii]))) {
			errorCount++;
			errorBody += requiredTitle[ii+3]+ "\n";
			if( focus == null )
				focus = requiredF[ii];
			compareDate = false;
		}*/
	}

	if( errorCount > 0 )
		errorMsg = errorHeader + errorBody;

  if( errorCount == 0 )
  {
    return true;
  }
  else
  {
  alert( errorMsg ) ;
  $('ok_'+rowNum).checked = false;
  focus.focus();
  return false;
  }
}
}

function checkBoxes()
{
    var totalLines = document.getElementById("totalLines").value;
    totalLines = totalLines*1;
    if (totalLines > 50)
    {
        return true;
    }
  // if case id or pallet id is checked, other same case id or same pallet id has to be checked.
	var changed = true;
  // trim all
	for(i = 0 ; i > -1; i++ ) {
		if( $('ok_'+i) == null ) break;
		$('boxId_'+i).value = $('boxId_'+i).value.trim();
		$('palletId_'+i).value =$('palletId_'+i).value.trim();
/*
		$('deliveryTicket_'+i).value = $('deliveryTicket_'+i).value.trim();
		$('trackingNumber_'+i).value = $('trackingNumber_'+i).value.trim();
		$('dateShipped_'+i).value = $('dateShipped_'+i).value.trim();
		$('dateDelivered_'+i).value = $('dateDelivered_'+i).value.trim();
*/
	}
	while( changed ) {
	changed = false;
	for(i = 0 ; i > -1; i++ ) {
		if( $('ok_'+i) == null ) break;
		if( $('ok_'+i).checked == false ) continue;
		var boxId = $('boxId_'+i).value;
		var palletId = $('palletId_'+i).value;

		for(j = 0 ; j> -1; j++ ) {
			if( $('ok_'+j) == null  ) break;
			if(	$('ok_'+j).checked == true ) continue;
			if( boxId != "" && $('boxId_'+j).value == boxId ) {
				$('ok_'+j).checked = true;
				changed = true;
			}
			if( palletId != "" && $('palletId_'+j).value == palletId ) {
				$('ok_'+j).checked = true;
				changed = true;
			}
		}
		if( changed ) break;
	}
	}
}

function checkCaseBoxes()
{
    var totalLines = document.getElementById("totalLines").value;
    totalLines = totalLines*1;
    if (totalLines > 50)
    {
        return true;
    }
  // if case id or pallet id is checked, other same case id or same pallet id has to be checked.
	for(i = 0 ; i > -1; i++ ) {
		if( $('ok_'+i) == null ) break;
		$('boxId_'+i).value = $('boxId_'+i).value.trim();
	}
	var changed = true;
	while( changed ) {
	changed = false;
	for(i = 0 ; i > -1; i++ ) {
		if( $('ok_'+i) == null ) break;
		if( $('ok_'+i).checked == false ) continue;
		var boxId = $('boxId_'+i).value;

		for(j = 0 ; j> -1; j++ ) {
			if( $('ok_'+j) == null  ) break;
			if(	$('ok_'+j).checked == true ) continue;
			if( boxId != "" && $('boxId_'+j).value == boxId ) {
				$('ok_'+j).checked = true;
				changed = true;
			}
		}
		if( changed ) break;
	}
	}
}

function validateDate() {
    var totalLines = document.getElementById("totalLines").value;
    totalLines = totalLines*1;
    if (totalLines > 50)
    {
        return true;
    }
    
    var hasError = false;
	var caseToPalletMap = new Array();
	var caseToMrMap = new Array();
	var palletToShipMap = new Array();
	var palletToDeliveryMap = new Array();
	var palletToDodaacMap = new Array();

	var errorMsg = "";
	var errorHeader = messagesData.validValues+"\n\n";
	var modified = false;
	for(i = 0 ; i > -1; i++ ) {
		if( $('ok_'+i) == null ) break;
		if( $('ok_'+i).checked == false ) continue;
		modified = true;

		var boxId = $('boxId_'+i).value;
		var focus = null;
		if( boxId == "" ) { errorMsg += requiredTitle[0]+"\n"; focus = $('boxId_'+i); }
/*		var shipDate = $('dateShipped_'+i).value;
		if( shipDate.length !=10 || !checkdate( $('dateShipped_'+i) ) ) { errorMsg += requiredTitle[4]+"\n"; if( focus == null ) focus = $('dateShipped_'+i); }
		var deliveryDate = $('dateDelivered_'+i).value;
		if( deliveryDate .length !=10 || !checkdate( $('dateDelivered_'+i) ) ) { errorMsg += requiredTitle[5]+"\n"; if( focus == null ) focus = $('dateDelivered_'+i); }
*/		if( errorMsg != "" ) {
			alert( errorHeader + errorMsg ) ;
			focus.focus();
			return false;
			}

		var pallet = $('palletId_'+i).value;
		var oldPallet  = caseToPalletMap[boxId];
		if( oldPallet == null ) {
			caseToPalletMap[boxId] = pallet;
		}
		else {
			if( oldPallet != pallet ) {
				alert(messagesData.casetopallet);
				$('palletId_'+i).focus();
				return false;
			}
		}

		var mrLine = $('prNumber_lineItem_'+i).value;
		var oldMrLine  = caseToMrMap[boxId];
		if( oldMrLine == null ) {
			caseToMrMap[boxId] = mrLine;
		}
		else {
			if( oldMrLine != mrLine ) {
				alert(messagesData.casetomr);
				$('boxId_'+i).focus();
				return false;
			}
		}
/*		var oldShip  = palletToShipMap[pallet];
		if( pallet != "" )
		if( oldShip == null ) {
			palletToShipMap[pallet] = shipDate;
		}
		else {
			if( oldShip != shipDate ) {
				alert(messagesData.pallettoship);
				$('dateShipped_'+i).focus();
				return false;
			}
		}

		var oldDelivery  = palletToDeliveryMap[pallet];
		if( pallet != "" )
		if( oldDelivery == null ) {
			palletToDeliveryMap[pallet] = deliveryDate;
		}
		else {
			if( oldDelivery != deliveryDate ) {
				alert(messagesData.pallettodelivery);
				$('dateDelivered_'+i).focus();
				return false;
			}
		}
*/		var dodaac = $('ultimateShipToDodaac_'+i).value;
		var oldDodaac  = palletToDodaacMap[pallet];
		if( pallet != "" )
		if( oldDodaac == null ) {
			palletToDodaacMap[pallet] = dodaac;
		}
		else {
			if( oldDodaac != dodaac ) {
				alert(messagesData.pallettododaac);
				$('palletId_'+i).focus();
				return false;
			}
		}

/*
// delivery date >= ship date, both are already valid.
		if( dateToInt(deliveryDate) < dateToInt(shipDate) ) {
				alert(messagesData.shipltdelivery);
				$('dateDelivered_'+i).focus();
				return false;
			}
*/
	}
	if( !modified ) alert(messagesData.pleaseselectbox);

	return modified;
}

function update() {
  checkBoxes();
  if( !validateDate() ) return;
   	parent.showPleaseWait();
   	$('userAction').value = 'update';
	document.supplierLocationForm.action='packresults.do';
 	document.supplierLocationForm.target='';
   	document.supplierLocationForm.submit();
}

function requestWAWFReport() {
 if( !snCheck() ) return;
   	parent.showPleaseWait();
   	$('userAction').value = 'generateInspAsn';
	document.supplierLocationForm.action='packresults.do';
 	document.supplierLocationForm.target='';
   	document.supplierLocationForm.submit();
}

function snCheck()
{
	var totalLines = document.getElementById("totalLines").value;
	//totalLines = totalLines*1;
	for(var i = 0; i < totalLines; ++i)
	{
		if($("ok_" + i).checked)
		{
			snBox = $('serialNumber_' + i);
			if(snBox != null && typeof(snBox) != 'undefinded' && snBox.type != 'hidden' && snBox.value =='')
			{
				alert(messagesData.serialNumbersRequiredOI);
				return false;
			}
			
		}
	}
	return true; 
}





function showErrorMessages()
{
		parent.showErrorMessages();
}

function showUpdateLink() {
	if( showUpdateLinks )
		parent.document.getElementByid("updateResultLink").style['display'] = "";
}

function createExcel() {
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', 'show_excel_report_file','800','600','yes');
		document.supplierLocationForm.target='show_excel_report_file';
		document.supplierLocationForm.action='usgovlabelingresults.do';
  	var userAction = document.getElementById("userAction");
 		userAction.value = 'createExcel';
   	var a = window.setTimeout("document.supplierLocationForm.submit();",1000);

   	return false;
}

function printTable()
{
  openWinGeneric('/tcmIS/common/loadingfile.jsp', 'printTable','800','600','yes');
	document.supplierLocationForm.target='printTable';
	document.supplierLocationForm.action='packresults.do?';
 	var userAction = document.getElementById("userAction");
	userAction.value = 'printTable';
  
  var a = window.setTimeout("document.supplierLocationForm.submit();",1000);
}

function printCaseMSL()
{
  checkCaseBoxes();
  if( !validateDate() ) return;
  if ($("dataChanged").value == "Yes")
  {
   alert(messagesData.updatebeforeprint);
   return;
  }
  openWinGeneric('/tcmIS/common/loadingfile.jsp', 'printCaseMSL','800','600','yes');
	document.supplierLocationForm.target='printCaseMSL';
	document.supplierLocationForm.action='printmsl.do';
 	var userAction = document.getElementById("userAction");
	userAction.value = 'printCaseMSL';
  $("paperSize").value = "46";
  var a = window.setTimeout("document.supplierLocationForm.submit();",1000);
}

function printPalletMSL()
{
  checkBoxes();
  if( !validateDate() ) return;
  if ($("dataChanged").value == "Yes")
  {
   alert(messagesData.updatebeforeprint);
   return;
  }
  openWinGeneric('/tcmIS/common/loadingfile.jsp', 'printPalletMSL','800','600','yes');
	document.supplierLocationForm.target='printPalletMSL';
	document.supplierLocationForm.action='printmsl.do';
 	var userAction = document.getElementById("userAction");
	userAction.value = 'printPalletMSL';
  $("paperSize").value = "46";
  var a = window.setTimeout("document.supplierLocationForm.submit();",1000);
}

function printPlacardLabels()
{
  checkBoxes();
  if( !validateDate() ) return;
  if ($("dataChanged").value == "Yes")
  {
   alert(messagesData.updatebeforeprint);
   return;
  }
  openWinGeneric('/tcmIS/common/loadingfile.jsp', 'printPlacardLabels','800','600','yes');
	document.supplierLocationForm.target='printPlacardLabels';
	document.supplierLocationForm.action='printplacardlabels.do';
 	var userAction = document.getElementById("userAction");
	userAction.value = 'printPlacardLabels';
  $("paperSize").value = "46";
  var a = window.setTimeout("document.supplierLocationForm.submit();",1000);
}

function printPackingList()
{
  checkBoxes();
  if( !validateDate() ) return;
  if ($("dataChanged").value == "Yes")
  {
   alert(messagesData.updatebeforeprint);
   return;
  }
  openWinGeneric('/tcmIS/common/loadingfile.jsp', 'printPackingList','800','600','yes');
	document.supplierLocationForm.target='printPackingList';
	document.supplierLocationForm.action='printpackinglist.do';
 	var userAction = document.getElementById("userAction");
	userAction.value = 'printPackingList';
  var a = window.setTimeout("document.supplierLocationForm.submit();",1000);
}

function valueChanged(elementChanged)
{
  $(elementChanged).style.backgroundColor = "#FFFF99";
  $("dataChanged").value = "Yes";
}

function resultOnLoad() {
	displaySearchDuration();
	setResultFrameSize();
	if (!showUpdateLinks) {
		parent.document.getElementById("updateResultLink").style["display"] = "none";
	} else {
		parent.document.getElementById("updateResultLink").style["display"] = "";
		var originInsCount = document.getElementById("originInsCount");
		parent.document.getElementById("requestorigininsLink").style["display"] = originInsCount.value * 1 > 0 ? "" : "none";
	}
}

function showLegend(){
  openWinGeneric("/tcmIS/help/usgovshippinglegend.html","usgovshippinglegend","290","350","no","50","50")
}

function getSequnceId(elementChanged)
{
  var inputRfId = $(elementChanged).value;
  if (inputRfId.trim().length == 24)
  {
  $(elementChanged).value = inputRfId.substr(15,9);
  }
  valueChanged(elementChanged);
}

function checkAllOkBoxes(checkBoxName)
{
var checkAll = document.getElementById("checkAll");
var totalLines = document.getElementById("totalLines").value;
totalLines = totalLines*1;
var result =  checkAll.checked;
var checkedCount = 0;

for ( var p = 0 ; p < totalLines ; p ++)
 {
	try
	{
	var lineCheck = document.getElementById(""+checkBoxName+""+(p)+"");
  if (lineCheck.checked && !result)
  {
    lineCheck.checked =result;
  }
  else if (!lineCheck.checked && result)
  {
    lineCheck.checked=true;
    if (result && !okCheck(p))
    {
      lineCheck.checked =false;
    }
    else
    {
    checkedCount ++;
    }
  }
  }
	catch (ex1)
	{
	}
 }
 if (checkedCount ==0 && result)
 {
   checkAll.checked = false;
 }
}

function getShipToAddress(locationId, dodaac) {
openWinGeneric("/tcmIS/supplier/viewaddress.do?locationId="+locationId+"&ultimateDodaac="+dodaac,'_viewaddress','500','300','yes');
}

function genDown(id)
{
	var val = document.getElementById("boxId_" + id).value;	
	if(val == '')
		alert(messagesData.scanorenterfirst);		
	else
	{
		var copyOrGen = null;
		do
		{
			copyOrGen = prompt(messagesData.caseidcopyorgen, "");
		}
		while(copyOrGen != null && copyOrGen != 1 && copyOrGen != 2)
				
		if(copyOrGen != null)
		{
			var count = null;
			var which = null;
			if(copyOrGen == 1)
			{
				do
				{
					count = prompt(messagesData.numtocopydown, "");
				} while(count != null && !isInteger(count,false))
			}
			else
			{
				do
				{
				  count = prompt(messagesData.numtogendown, "");
				} while(count != null && !isInteger(count,false))
			}

			if (count != null) 
			{	 
				 for(var i = 0; i < count;i++)
				 {
					var nextInput = parseInt(id) + i + 1;
					if(copyOrGen != 1)
					{
						val = (parseInt(val, 16) + 1).toString(16);
					 	while(val.length < 9)
					 		val = '0' + val; 				 
					}
				 	var box = document.getElementById("boxId_" + nextInput);
				 	if(box == null || typeof(box) == 'undefined')
				 	{
				 		alert(messagesData.cantcompleteop);
				 		break;
				 	}
				 	else
				 	{
				 		box.value = val.toUpperCase();
				 		box.style["BACKGROUND-COLOR"] = "";
				 	}
				 }
			}		
		}
			
	}
	
}

function copyDown(id)
{
	var val = document.getElementById("palletId_" + id).value;
	if(val == '')
		alert(messagesData.scanorenterfirst);
	else
	{
		do
		{
		    count = prompt(messagesData.palletidcopy, "");
		}
		while(count != null && !isInteger(count,false))
		
		if(val == '')
			alert(messagesData.scanorenterfirst);
		if (count != null) 
		{	
			 var val = document.getElementById("palletId_" + id).value;	 
			 for(var i = 0; i < count;i++)
			 {
				var nextInput = parseInt(id) + i + 1;
				var box = document.getElementById("palletId_" + nextInput);
			 	if(box == null || typeof(box) == 'undefined')
			 	{
			 		alert(messagesData.cantcompleteop);
			 		break;
			 	}
			 	else
			 	{
					box.value = val.toUpperCase();
					box.style["BACKGROUND-COLOR"] = ""; 
			 	}
			 }
		}
	}
}

var selectedRowId;
function selectRow(e,rowId) 
{
	var selectedRow = document.getElementById("rowId" + rowId + "");

	var selectedRowClass = document.getElementById("colorClass" + rowId + "");
	selectedRow.className = "selected " + selectedRowClass.value + "";

	if (selectedRowId != null && rowId != selectedRowId) {
		var previouslySelectedRow = document.getElementById("rowId" + selectedRowId + "");
		var previouslySelectedRowClass = document.getElementById("colorClass" + selectedRowId + "");
		previouslySelectedRow.className = "" + previouslySelectedRowClass.value + "";
	}
	selectedRowId = rowId;
		if(e != null && ((e.button != null && e.button == 2) || e.which == 3)) {
			var ritems = new Array();
			selDeliveryTicket = document.getElementById("deliveryTicket_" + rowId + "").value;
			selPalletId = document.getElementById("palletId_" + rowId + "").value;
			
			if(selPalletId != '')
				ritems[ritems.length] =	'text='+messagesData.printserialnumberspalletid.replace('{0}', selPalletId) + ';url=javascript:printSerialNums("'+selPalletId+'","'+selDeliveryTicket+'","");';
			else
			{
				selCaseId = document.getElementById("boxId_" + rowId + "").value;
				if(selCaseId != '')
					ritems[ritems.length] =	'text='+messagesData.printserialnumberscaseid.replace('{0}', selCaseId) + ';url=javascript:printSerialNums("","'+selDeliveryTicket+'","'+selCaseId+'");';
			}
			ritems[ritems.length] =	'text='+messagesData.printserialnumbersdeliveryticket.replace('{0}', selDeliveryTicket) + ';url=javascript:printSerialNums("","'+selDeliveryTicket+'","");';
			if(document.getElementById("updatePalletPermission_" + selectedRowId + "").value == 'Yes')
			{
				selDesiredShipDate = document.getElementById("desiredShipDate_" + rowId + "").value;				
				ritems[ritems.length] =	'text='+messagesData.createserialnumberexcel.replace('{0}', ' ' + selDeliveryTicket) + ';url=javascript:createSerialNumberExcel();';
				ritems[ritems.length] =	'text='+messagesData.uploadserialnumberexcel.replace('{0}', ' ' + selDeliveryTicket) + ';url=javascript:uploadSerialNums("'+selDeliveryTicket+'","'+selDesiredShipDate+'");';
			}
			ritems[ritems.length] =	'text=' + messagesData.printPicklist + ';url=javascript:printInventoryPicklist(' + rowId  + ');';
			replaceMenu('serialNums',ritems);
			toggleContextMenu('serialNums');
	   }


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

function printSerialNums(sPI,sDT,sCI)
{    
	var reportLoc = "/HaasReports/report/printConfigurableReport.do?rpt_ReportBeanId=DlaSerialNumberListReportDefinition";
	if(sPI != '')
		reportLoc += "&pr_pallet_id=" + sPI;
	if(sCI != '')
		reportLoc += "&pr_boxId=" + sCI;
	reportLoc += "&pr_shipFromLocationId="+document.getElementById("shipFromLocationId_"+selectedRowId).value+"&pr_supplier="+document.getElementById("supplier").value + "&pr_deliveryTicket="+sDT; 
    openWinGeneric(reportLoc,"viewDlaSerialNumberList","800","550","yes","100","100");
}

function createSerialNumberExcel() {

	   openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', 'ShowSerialNumExcel','800','600','yes');
	   document.supplierLocationForm.target='ShowSerialNumExcel';
 	   var userAction = document.getElementById("userAction");
	   userAction.value = 'createSerialNumExcel';
	   $('selShipFromLocationId').value = $v('shipFromLocationId_'+selectedRowId),
	   $('supplierSalesOrderNo').value = $v('deliveryTicket_'+selectedRowId),
       window.setTimeout("document.supplierLocationForm.submit();",1000);
}


function uploadSerialNums(sDT,sDS)
{
	openWinGeneric("/tcmIS/supplier/usgovlabelingserialnum.do?supplier="+$v('supplier')+"&uAction=showUploadList&supplierSalesOrderNo="+sDT+"&desiredShipDateEnd="+encodeURIComponent(sDS).replace(/-/g, '%2D'),'UploadSerialNumbers','500','300','yes');
}

function printInventoryPicklist(rowNum) {
	var headerBeanId = 0;
	var form = document.createElement("form");
	form.setAttribute("name", "inventoryPicklist");
	addHiddenVariable(form, "uAction", "printFromView");
	//depends on whether user select print one from RMC or multiple from overhead action, add inputs to form
	if (typeof rowNum === 'undefined') {
		var prNumberLineItemArr = new Array();
		for (var rowId = 0; rowId < $v("totalLines"); rowId++) {
			var curPrNumberLineItem = $v("prNumber_" + rowId) + "-" + $v("lineItem_" + rowId);
			if ($("ok_" + rowId).checked && !prNumberLineItemArr[curPrNumberLineItem]) {
				prNumberLineItemArr[curPrNumberLineItem] = true;
				addHiddenVariable(form, "headerBean[" + headerBeanId + "].catPartNo", $v("catPartNo_" + rowId));
				addHiddenVariable(form, "headerBean[" + headerBeanId + "].mfgDateRequired", $v("mfgDateRequired_" + rowId));
				addHiddenVariable(form, "headerBean[" + headerBeanId + "].originInspectionRequired", $v("originInspectionRequired_" + rowId));
				addHiddenVariable(form, "headerBean[" + headerBeanId + "].qtyToPick", $v("orderQuantity_" + rowId));
				addHiddenVariable(form, "headerBean[" + headerBeanId + "].mrNumber", $v("prNumber_" + rowId));
				addHiddenVariable(form, "headerBean[" + headerBeanId + "].mrLineItem", $v("lineItem_" + rowId));
				addHiddenVariable(form, "headerBean[" + headerBeanId + "].poLine", $v("poLine_" + rowId));
				addHiddenVariable(form, "headerBean[" + headerBeanId + "].radianPo", $v("radianPo_" + rowId));
				addHiddenVariable(form, "headerBean[" + headerBeanId + "].vendorShipDate", $v("desiredShipDate_" + rowId));
				addHiddenVariable(form, "headerBean[" + headerBeanId + "].customerPo", $v("customerPoNo_" + rowId).indexOf("-") != -1 ? $v("customerPoNo_" + rowId).split("-")[1] : $v("customerPoNo_" + rowId));
				addHiddenVariable(form, "headerBean[" + headerBeanId + "].shiptoAddress", $v("shiptoAddress_" + rowId));
				addHiddenVariable(form, "headerBean[" + headerBeanId + "].oconus", $v("oconus_" + rowId));
				addHiddenVariable(form, "headerBean[" + headerBeanId + "].prNumber", $v("prNumber_" + rowId));
				addHiddenVariable(form, "headerBean[" + headerBeanId + "].lineItem", $v("lineItem_" + rowId));
				addHiddenVariable(form, "headerBean[" + headerBeanId + "].supplierSalesOrderNo", $v("deliveryTicket_" + rowId));
				headerBeanId++;
			}
		}
	} else {
		addHiddenVariable(form, "headerBean[" + headerBeanId + "].catPartNo", $v("catPartNo_" + rowNum));
		addHiddenVariable(form, "headerBean[" + headerBeanId + "].mfgDateRequired", $v("mfgDateRequired_" + rowNum));
		addHiddenVariable(form, "headerBean[" + headerBeanId + "].originInspectionRequired", $v("originInspectionRequired_" + rowNum));
		addHiddenVariable(form, "headerBean[" + headerBeanId + "].qtyToPick", $v("orderQuantity_" + rowNum));
		addHiddenVariable(form, "headerBean[" + headerBeanId + "].mrNumber", $v("prNumber_" + rowNum));
		addHiddenVariable(form, "headerBean[" + headerBeanId + "].mrLineItem", $v("lineItem_" + rowNum));
		addHiddenVariable(form, "headerBean[" + headerBeanId + "].poLine", $v("poLine_" + rowNum));
		addHiddenVariable(form, "headerBean[" + headerBeanId + "].radianPo", $v("radianPo_" + rowNum));
		addHiddenVariable(form, "headerBean[" + headerBeanId + "].vendorShipDate", $v("desiredShipDate_" + rowNum));
		addHiddenVariable(form, "headerBean[" + headerBeanId + "].customerPo", $v("customerPoNo_" + rowNum).indexOf("-") != -1 ? $v("customerPoNo_" + rowNum).split("-")[1] : $v("customerPoNo_" + rowNum));
		addHiddenVariable(form, "headerBean[" + headerBeanId + "].shiptoAddress", $v("shiptoAddress_" + rowNum));
		addHiddenVariable(form, "headerBean[" + headerBeanId + "].oconus", $v("oconus_" + rowNum));
		addHiddenVariable(form, "headerBean[" + headerBeanId + "].prNumber", $v("prNumber_" + rowNum));
		addHiddenVariable(form, "headerBean[" + headerBeanId + "].lineItem", $v("lineItem_" + rowNum));
		addHiddenVariable(form, "headerBean[" + headerBeanId + "].supplierSalesOrderNo", $v("deliveryTicket_" + rowNum));
	}
	openWinGeneric("/tcmIS/common/loadingfile.jsp", "printInventoryPicklist", "900", "600", "yes", "80", "80");
	form.setAttribute("target", "printInventoryPicklist");
	form.setAttribute("action", "/HaasReports/report/getinventorypicklist.do");
	//form needs to be attached to page to submit
	document.getElementsByTagName('body')[0].appendChild(form);
	form.submit();
	//remove form once done
	form.parentNode.removeChild(form);
}