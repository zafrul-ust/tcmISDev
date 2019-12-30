var submitcount = 0;
var updatecount = 0;
var preview;
var selectedRowId;

function selectRow(rowId){
	selectedRowId = rowId;
}

function completeReceivedBy()
{
	
  j$().ready(function() {
	function log(event, data, formatted) {
		j$('#receivedbyid').val(formatted.split(":")[0]);
		$("receivedbyname").className = "HEADER"; 
	} 

	j$("#receivedbyname").autocomplete("/tcmIS/haas/getpersonneldata.do",{
		//extraParams: {activeOnly: function() { return j$('#activeonly').is(':checked'); } },
		extraParams: {activeOnly: function() { return true; },
		              companyId: function() { return "Radian"; } },
		width: 200,
		max: 10,  // default value is 10
		cacheLength:0, // disable cache here because we put "rownum < max" for better performance. Cache will make data off.
		scrollHeight: 200,
		formatItem: function(data, i, n, value) {
			return  value.split(":")[1].substring(0,40);
		},
		formatResult: function(data, value) {
			return value.split(":")[1];
		}
	});
	
	j$('#receivedbyname').bind('keyup',(function(e) {
		  var keyCode = (e.keyCode ? e.keyCode : e.which);
	
		  if( keyCode != 13 && keyCode != 9) // 13 is for Enter; 9 is for Tab
		  	invalidatePersonnel();
	}));
	
	
	j$("#receivedbyname").result(log).next().click(function() {
		j$(this).prev().search();
	});
  }); 
}


function invalidatePersonnel()
{
 var receivedbyname  =  document.getElementById("receivedbyname");
 var receivedbyid  =  document.getElementById("receivedbyid");
 if (receivedbyname.value.length == 0) {
	 receivedbyname.className = "inputBox";
 }else {
	 receivedbyname.className = "inputBox red";
 }
 //set to empty
 receivedbyid.value = "";
}


function lookupPerson()
{
	 var loc = "searchpersonnelmain.do?displayArea=receivedbyname&valueElementId=receivedbyid";
	 
	 if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/haas/" + loc;
	 
	 openWinGeneric(loc,"PersonnelId","650","455","yes","50","50");
}

function personnelChanged(id, name) {
	document.getElementById("receivedbyname").className = "HEADER";
}

function SubmitOnlyOnce() {
	if (submitcount == 0) {
		submitcount++;
		try {
			var target = document.all.item("TRANSITPAGE");
			target.style["display"] = "";
			var target1 = document.all.item("MAINPAGE");
			target1.style["display"] = "none";
		}
		catch (ex) {
		}

		return true;
	}
	else {
		alert("This form has already been submitted.\n Please Wait for Results.\n Thanks!");
		return false;
	}
}

function checkExpireDate(rowId) {
	var result;
	var allClear = 0;
	var finalMsgt;
	finalMsgt = "Please enter valid values for: \n\n";
	var checkBox = document.getElementById("row_chk" + rowId + "");
	if (checkBox.value == "no") {

	}
	if (checkBox.value == "yes") {
		try {
			var selectedElementStatus = document.getElementById("selectElementStatus" + rowId + "");
			var lotStatusaa = new Array();
			lotStatusaa = lotStatus[selectedElementStatus.value];

			if (lotStatusaa.length > 0) {
				var expiryDate = document.getElementById("expiry_date" + rowId + "");
				if ((expiryDate.value == "INDEFINITE") || (expiryDate.value == "Indefinite") || (expiryDate.value == "indefinite")) {
				}
				else {
					if (expiryDate.value.length == 10) {
						if (checkdate(expiryDate) == false) {
							finalMsgt = finalMsgt + " Exp Date. \n";
							expiryDate.value = "";
							allClear = 1;
						}
					}
					else {
						finalMsgt = finalMsgt + " Exp Date. \n";
						allClear = 1;
					}
				}
			}
		}
		catch (ex) {
			// alert("Non-Pickable Status");
		}

		if (allClear < 1) {
			checkBox.value = "yes";
			checkBox.checked = true;
			result = true;
		}
		else {
			checkBox.value = "no";
			checkBox.checked = false;
			alert(finalMsgt);
			result = false;
		}
	}
	return result;
}

function checkLabelExpireDate(rowId) {
	var result;
	var allClear = 0;
	var finalMsgt;
	finalMsgt = "Please enter valid values for: \n\n";
	var checkBox = document.getElementById("row_chk" + rowId + "");
	if (checkBox.value == "no") {

	}
	if (checkBox.value == "yes") {
		try {
			var selectedElementStatus = document.getElementById("selectElementStatus" + rowId + "");
			var lotStatusaa = new Array();
			lotStatusaa = lotStatus[selectedElementStatus.value];

			if (lotStatusaa.length > 0) {
				var mfgLabelExpireDate = document.getElementById("mfgLabelExpireDate" + rowId + "");
				if ((mfgLabelExpireDate.value == "INDEFINITE") || (mfgLabelExpireDate.value == "Indefinite") || (mfgLabelExpireDate.value == "indefinite")) {
                }
				else {
					if (mfgLabelExpireDate.value.length == 10) {
						if (checkdate(mfgLabelExpireDate) == false) {
							finalMsgt = finalMsgt + " Exp Date. \n";
							mfgLabelExpireDate.value = "";
							allClear = 1;
						}
					}
					else {
						finalMsgt = finalMsgt + " Exp Date. \n";
						allClear = 1;
					}
				}
			}
		}
		catch (ex) {
			// alert("Non-Pickable Status");
		}

		if (allClear < 1) {
			checkBox.value = "yes";
			checkBox.checked = true;
			result = true;
		}
		else {
			checkBox.value = "no";
			checkBox.checked = false;
			alert(finalMsgt);
			result = false;
		}
	}
	return result;
}

function checkInvenreadonlyValues(name, entered) {
	var checkbox_id = name.toString();
	var total = window.document.receiving.Total_number.value;
	var result;
	var yes = "yes";
	var no = "no";
	var NONE = "NONE";
	for ( var p = 0; p < total; p++) {
		var line_num;
		eval(line_num = p + 1);
		if (checkbox_id == ("row_chk" + line_num.toString())) {
			var testdate;
			var testqty;
			var testlot;
			var finalMsgt;
			var allClear = 0;

			var testcheckbox;
			;
			eval(testcheckbox = "window.document.receiving.row_chk" + line_num.toString());
			if (((eval(testcheckbox.toString())).value) == no.toString()) {
			}
			if (((eval(testcheckbox.toString())).value) == yes.toString()) {
				eval((eval(testcheckbox.toString())).value = no.toString());
				eval((eval(testcheckbox.toString())).checked = false);
				updatecount--;
				break;
			}

			finalMsgt = "Please enter valid values for: \n\n";

			eval(testqty = "window.document.receiving.qty_recd" + line_num.toString())
			var v = (eval(testqty.toString())).value;
			if (v * 1 < 0) {
				testqty12 = eval("window.document.receiving.qty_recd" + line_num.toString());
				testqty12.value = "";
			}
			else if (!(isInteger(v)) || v * 1 == 0) {
				finalMsgt = finalMsgt + " No of Labels. \n";
				allClear = 1;
				testqty12 = eval("window.document.receiving.qty_recd" + line_num.toString());
				testqty12.value = "";
			}

			if (allClear < 1) {
				eval((eval(testcheckbox.toString())).value = yes.toString());
				;
				eval((eval(testcheckbox.toString())).checked = true);
				;
				result = true;
			}
			else {
				eval((eval(testcheckbox.toString())).value = no.toString());
				;
				eval((eval(testcheckbox.toString())).checked = false);
				;
				alert(finalMsgt);
				result = false;
			}
			break;
		}
	}
	if (true == result) {
		updatecount++;
	}
	return result;
}

function checkInvenValues(name, entered) {
	var checkbox_id = name.toString();
	var total = window.document.receiving.Total_number.value;
	var result;
	var yes = "yes";
	var no = "no";
	var NONE = "NONE";
	for ( var p = 0; p < total; p++) {
		var line_num;
		eval(line_num = p + 1);
		if (checkbox_id == ("row_chk" + line_num.toString())) {
			var testdate;
			var testqty;
			var testlot;
			var finalMsgt;
			var allClear = 0;

			var testcheckbox;
			;
			eval(testcheckbox = "window.document.receiving.row_chk" + line_num.toString());
			if (((eval(testcheckbox.toString())).value) == no.toString()) {
			}
			if (((eval(testcheckbox.toString())).value) == yes.toString()) {
				eval((eval(testcheckbox.toString())).value = no.toString());
				eval((eval(testcheckbox.toString())).checked = false);
				updatecount--;
				break;
			}
			finalMsgt = "Please enter valid values for: \n\n";

			eval(testqty = "window.document.receiving.qty_recd" + line_num.toString())
			var v = (eval(testqty.toString())).value;
			if (v * 1 < 0) {
				testqty12 = eval("window.document.receiving.qty_recd" + line_num.toString());
				testqty12.value = "";
			}
			else if (!(isInteger(v)) || v * 1 == 0) {
				// finalMsgt = finalMsgt + " No of Labels. \n";
				// allClear = 1;
				testqty12 = eval("window.document.receiving.qty_recd" + line_num.toString());
				testqty12.value = "";
			}

			// 07-10-02 -Nawaz
			eval(testrec = "window.document.receiving.recerts" + line_num.toString())
			var recert = (eval(testrec.toString())).value;
			if (recert * 1 < 0) {
				testqty12 = eval("window.document.receiving.recerts" + line_num.toString());
				testqty12.value = "";
			}
			else if (!(isInteger(recert))) {
				testqty12 = eval("window.document.receiving.recerts" + line_num.toString());
				testqty12.value = "";
			}

			try {
				var selectedElementStatus = document.getElementById("selectElementStatus" + line_num.toString() + "");
				var lotStatusaa = new Array();
				lotStatusaa = lotStatus[selectedElementStatus.value];
				if (lotStatusaa.length > 0) {
					var expiryDate = document.getElementById("expiry_date" + line_num + "");
					if ((expiryDate.value == "INDEFINITE") || (expiryDate.value == "Indefinite") || (expiryDate.value == "indefinite")) {
					}
					else {
						if (expiryDate.value.length == 10) {
							if (checkdate(expiryDate) == false) {
								finalMsgt = finalMsgt + " Exp Date. \n";
								expiryDate.value = "";
								allClear = 1;
							}
						}
						else {
							finalMsgt = finalMsgt + " Exp Date. \n";
							allClear = 1;
						}
					}
				}
			}
			catch (ex) {
				// alert("Non-Pickable Status");
			}

			eval(testlot = "window.document.receiving.mfg_lot" + line_num.toString())
			if ((eval(testlot.toString())).value.length < 1) {
				finalMsgt = finalMsgt + " Mfg Lot #. \n";
				allClear = 1;
			}

			var testbin;
			eval(testbin = "window.document.receiving.selectElementBin" + line_num.toString());
			var cur = null;
			var selectBinElement = eval(testbin.toString());
			if(selectBinElement.tagName.toLowerCase() == 'select')
			{
				eval(cur = selectBinElement.selectedIndex);
				var curval = null;
				(curval = selectBinElement.options[cur].value);
				if (curval == NONE.toString()) {
					finalMsgt = finalMsgt + " BIN. \n";
					allClear = 1;
				}
			}

			var dateOfShipment = document.getElementById("dateOfShipment" + line_num + "");
			if (dateOfShipment.value.length == 10) {
				if (checkdate(dateOfShipment) == false) {
					finalMsgt = finalMsgt + " Manufacturer Date of Shipment (DOS). \n";
					allClear = 1;
					// dateOfShipment.value = "";
				}
			}
			else if (dateOfShipment.value.length > 0) {
				finalMsgt = finalMsgt + " Manufacturer Date of Shipment (DOS). \n";
				allClear = 1;
				// dateOfShipment.value = "";
			}

			var dateOfManufacture = document.getElementById("dateOfManufacture" + line_num + "");
			if (dateOfManufacture.value.length == 10) {
				if (checkdate(dateOfManufacture) == false) {
					finalMsgt = finalMsgt + " Date of Manufacture (DOM). \n";
					allClear = 1;
					// dateOfManufacture.value = "";
				}
			}
			else if (dateOfManufacture.value.length > 0) {
				finalMsgt = finalMsgt + " Date of Manufacture (DOM). \n";
				allClear = 1;
				// dateOfManufacture.value = "";
			}

			if (allClear < 1) {
				eval((eval(testcheckbox.toString())).value = yes.toString());
				;
				eval((eval(testcheckbox.toString())).checked = true);
				;
				result = true;
			}
			else {
				eval((eval(testcheckbox.toString())).value = no.toString());
				;
				eval((eval(testcheckbox.toString())).checked = false);
				;
				alert(finalMsgt);
				result = false;
			}
			break;
		}
	}
	if (true == result) {
		updatecount++;
	}
	return result;
}

function showLineNotes(rowid) {
	var notesVisible = document.getElementById("notesVisible" + rowid + "");
	if (notesVisible.value == "No") {
		var lineNotesDiv = document.getElementById("lineNotes" + rowid + "");
		lineNotesDiv.style.display = "block";
		lineNoteslink

		var lineNoteslink = document.getElementById("lineNoteslink" + rowid + "");
		lineNoteslink.style.display = "none";

		notesVisible.value = "Yes";
	}
	else {
		var lineNotesDiv = document.getElementById("lineNotes" + rowid + "");
		lineNotesDiv.style.display = "none";
		lineNoteslink

		var lineNoteslink = document.getElementById("lineNoteslink" + rowid + "");
		lineNoteslink.style.display = "block";

		notesVisible.value = "No";
	}
}

function showInternalReceiptNotes(rowid) {
	var notesVisible = document.getElementById("internalReceiptnotesVisible" + rowid + "");
	if (notesVisible.value == "No") {
		var lineNotesDiv = document.getElementById("internalReceiptNotes" + rowid + "");
		lineNotesDiv.style.display = "block";
		lineNoteslink

		var lineNoteslink = document.getElementById("internalReceiptNoteslink" + rowid + "");
		lineNoteslink.style.display = "none";

		notesVisible.value = "Yes";
	}
	else {
		var lineNotesDiv = document.getElementById("internalReceiptNotes" + rowid + "");
		lineNotesDiv.style.display = "none";
		lineNoteslink

		var lineNoteslink = document.getElementById("internalReceiptNoteslink" + rowid + "");
		lineNoteslink.style.display = "block";

		notesVisible.value = "No";
	}
}



function addBintoMainPage() {
	var totallines = opener.document.getElementById("Total_number").value * 1;
	var selectedRow = false;
	var vvHubBin = document.getElementById("vvHubBin").value;
	var itemId = document.getElementById("itemId");
	var selectedRowNumber = document.getElementById("rowNumber").value;

	for ( var rowNumber = 1; rowNumber <= totallines; rowNumber++) {
		var mainItemId = opener.document.getElementById("item_id" + rowNumber + "");
		if (mainItemId.value == itemId.value) {
			var mainBinO = opener.document.getElementById("selectElementBin" + rowNumber + "");
			if(mainBinO.tagName.toLowerCase() != 'select')
				continue;
			if (selectedRowNumber == rowNumber) {
				selectedRow = true;
			}
			else {
				selectedRow = false;
			}

			try {
				var binName = null;
				binName = mainBinO.value;
				if (mainBinO.value == "NONE" || mainBinO.value.length == 0) {
					mainBinO[0] = null;
					opener.addOptionItem(rowNumber, vvHubBin, vvHubBin, selectedRow);
				}
				else {
					opener.addOptionItem(rowNumber, vvHubBin, vvHubBin, selectedRow);
				}
			}
			catch (ex) {
				// alert("error");
			}

		}
	}
	cancel();
}

function addOptionItem(rowNumber, value, text, selectedRow) {
	obj = document.getElementById("selectElementBin" + rowNumber + "")
	if(obj.tagName.toLowerCase() != 'select')
		return;
	var index = obj.length;
	obj.options[index] = new Option(text, value);
	if (selectedRow) {
		obj.options[index].selected = true;
	}
}

function showVvHubBins(itemId, rowNumber, branchPlant) {
	var loc = "showlogisticsbin.do?branchPlant=" + branchPlant + "&userAction=showBins&rowNumber=" + rowNumber + "&itemId=";
	loc = loc + itemId;
	
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;
	
	openWinGeneric(loc, "showffVvHubBins", "300", "150", "no", "80", "80");
}

function showreceivingpagelegend() {
	openWinGeneric("/tcmIS/help/receivingpagelegend.html", "receivingpagelegend", "290", "300", "no", "50", "50")
}

function showlogisticspagelegend() {
	var showLegendArea = document.getElementById("showLegendArea");
	showLegendArea.style.display = "";

	var dhxWins = new dhtmlXWindows();
	dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	var legendWin = dhxWins.createWindow(messagesData.showlegend, 0, 0, 400, 180);
	legendWin.setText(messagesData.showlegend);
	legendWin.clearIcon(); // hide icon
	legendWin.denyResize(); // deny resizing
	legendWin.denyPark(); // deny parking
	legendWin.attachObject(showLegendArea);
	legendWin.attachEvent("onClose", function(legendWin) {
		legendWin.hide();
	});
	legendWin.center();
}

function showProjectDocuments(receiptId, inventoryGroup) {
	var loc = "receiptdocuments.do?receiptId=" + receiptId + "&showDocuments=Yes&inventoryGroup=" + inventoryGroup + "";
	
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;
	
	openWinGeneric(loc, "showAllProjectDocuments", "450", "300", "no", "80", "80");
}

// Nawaz 03-23-05 to handle the generate large labels button
function generatelargelabels() {
	window.document.receiving.UserAction.value = "UPDATE";
	window.document.receiving.SubUserAction.value = "generatelargelabels";

	try {
		var target = document.all.item("TRANSITPAGE");
		target.style["display"] = "";
		var target1 = document.all.item("MAINPAGE");
		target1.style["display"] = "none";
	}
	catch (ex) {
	}
	return true;
}

function dolargelabelPopup() {
	var testurl4 = "/tcmIS/hub/printlargelabel?session=Active";
	openWinGeneric(testurl4, "Generate_large_labels", "600", "600", "yes")
}

function setprinterRes(printerRes) {
	window.document.receiving.printerRes.value = printerRes;
}

//Nawaz 06-27-02 to handle the generate labels button
function generatealllabels(entered,paperSize) {
    return generatelabels(entered, true,paperSize);
}

function generatelabels(entered, doAllLabels,paperSize) {
    var doAll = (true == doAllLabels ? true : false);
	var ids = "";
    var receivedForm = document.receiving;
    var cntr = 0;
	var max = receivedForm.Total_number.value;

    for (row = 1; row <= max; row++) {
		if ( document.getElementById("row_chk" + row) != null && document.getElementById("qty_recd" + row) != null && (doAll || document.getElementById("row_chk" + row).checked)) {
			if (ids.length > 0) {
				ids += ",";
			}
			ids += "" + document.getElementById("receipt_id" + row).value;
			cntr++;
		}
	}
    if (ids.trim().length > 0)
    {
    openWinGeneric('/tcmIS/common/loadingpleasewait.jsp','_GenerateLabels','650','600','yes');
    setTimeout("generatelabelsCallback(" + doAllLabels + ",'"+paperSize+"')", 1000);
    }
    return false;
}

function generatelabelsCallback(doAllLabels,paperSize) {
	var doAll = (true == doAllLabels ? true : false);
	var ids = "";
	var generatingForm = document.generateLabelsForm; 
	var receivedForm = document.receiving;
	var cntr = 0;
	var max = receivedForm.Total_number.value;
    if (paperSize != null && paperSize.trim()== "receiptDetail")
    {
     generatingForm.paperSize.value = "receiptDetail";
    }
    else
    {
     generatingForm.paperSize.value = receivedForm.Paper.value;
    }

    for (row = 1; row <= max; row++) {
		if ( document.getElementById("row_chk" + row) != null && document.getElementById("qty_recd" + row) != null && (doAll || document.getElementById("row_chk" + row).checked)) {
			if (ids.length > 0) {
				ids += ","; 
			}
			ids += "" + document.getElementById("receipt_id" + row).value;
			var receiptIdFieldName = "containerLabelMasterViewBean[" + cntr + "].receiptId";
			var quantityReceivedFieldName = "containerLabelMasterViewBean[" + cntr + "].quantityReceived";
			removeFieldFromFormIfExtant(generatingForm, receiptIdFieldName);
			addFieldToForm(generatingForm, receiptIdFieldName, document.getElementById("receipt_id" + row).value);
			removeFieldFromFormIfExtant(generatingForm, quantityReceivedFieldName);
			addFieldToForm(generatingForm, quantityReceivedFieldName, document.getElementById("qty_recd" + row).value);
			cntr++;
		}
	}

	if (document.getElementById("printKitLabels") != null && document.getElementById("printKitLabels").checked) {
		addFieldToForm(generatingForm, "skipKitLabels", "Yes");
	}
	
	generatingForm.labelReceipts.value = ids;    
    if (ids.trim().length > 0)
    {
    generatingForm.target = "_GenerateLabels";
	generatingForm.submit();
    }
}

function addFieldToForm(form, field, value) {
	var hiddenField = document.createElement("input");
        hiddenField.setAttribute("type", "hidden");
        hiddenField.setAttribute("name", field);
        hiddenField.setAttribute("id", field);
        hiddenField.setAttribute("value", value);
        form.appendChild(hiddenField);	
}

function removeFieldFromFormIfExtant(form, fieldId) {
	var field = document.getElementById(fieldId);
	if (field != null) {
		form.removeChild(field);
	}
}

function showrecforinvtransfr(trnsreqid) {
	loc = "transfertransactions.do?transferRequestId=";
	loc = loc + trnsreqid;
	
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;
	
	openWinGeneric(loc, "Previous_Transfer_Transactions", "700", "400", "yes")
}

function enterdotinfo(item_id) {
	var loc = "shippinginfo.do?uAction=search&itemId=" + item_id;
	
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;
   	
	parent.parent.openIFrame("ShippingInfo"+item_id,loc,"ShippingInfo "+item_id,"","N");
}

function sendtervalues() {
	linenumberchoos = document.getElementById("linenumberchoos").value;

	rootcause = document.getElementById("rootcause");
	rootcausecompany = document.getElementById("rootcausecompany");
	rootcausenotes = document.getElementById("rootcausenotes");

	mainrootcause = opener.document.getElementById("rootcause" + linenumberchoos + "");
	mainrootcause.value = rootcause.value;

	mainrootcausecompany = opener.document.getElementById("rootcausecompany" + linenumberchoos + "");
	mainrootcausecompany.value = rootcausecompany.value;

	mainrootcausenotes = opener.document.getElementById("rootcausenotes" + linenumberchoos + "");
	mainrootcausenotes.value = rootcausenotes.value;

	window.close();
}

function checkterminalstatus(rownumberis) {
	var lotstatus = document.getElementById("selectElementStatus" + rownumberis + "");
	var origlotstatus = document.getElementById("origlotstatus" + rownumberis + "");

	if (lotstatus.value.length == 0) {
		var selecelemet = lotstatus.selectedIndex;
		var testelementtext = lotstatus.options[selecelemet].text;
		if (origlotstatus.value != testelementtext) {
			alert("You don't have permissions to change the status to " + testelementtext + ".")
		}

		i = 0;
		while (i < lotstatus.length) {
			var elementtext = lotstatus.options[i].text;

			if (elementtext == origlotstatus.value) {
				lotstatus.selectedIndex = i;
			}
			i += 1;
		}
	}
	else {
		if (lotstatus.value == "Customer Purchase" || lotstatus.value == "Write Off Requested" || lotstatus.value == "3PL Purchase") {
			loc = "/tcmIS/Hub/ReceivingQC?session=Active&UserAction=showterminalstatus&termlotstatus=";
			loc = loc + lotstatus.value + "&terlinenumber=" + rownumberis;
			openWinGeneric(loc, "terminal_root_cause", "500", "300", "no");
		}
		else {
			mainrootcause = document.getElementById("rootcause" + rownumberis + "");
			mainrootcause.value = "";

			mainrootcausecompany = document.getElementById("rootcausecompany" + rownumberis + "");
			mainrootcausecompany.value = "";

			mainrootcausenotes = document.getElementById("rootcausenotes" + rownumberis + "");
			mainrootcausenotes.value = "";
		}
	}

}

// 01-14-03
function createxls() {
	var loc = "/tcmIS/Hub/Receiving?session=Active&UserAction=createxls";
	openWinGenericViewFile(loc, "createXls", "800", "600", "yes");
}

// Nawaz 12-27-02 to handle the generate xls button
function generatexls(entered) {
	window.document.receiving.UserAction.value = "UPDATE";
	window.document.receiving.SubUserAction.value = "generatexls";
	loc = "/tcmIS/Hub/Logistics?session=Active&UserAction=Update&SubUserAction=generatexls";
	openWinGenericViewFile(loc, "ItemId", "800", "600", "yes");
}

function openWinGenericViewFile(destination, WinName, WinWidth, WinHeight, Resizable, topCoor, leftCoor, scrollbars) {
	if (topCoor == null || topCoor.trim().length == 0) {
		topCoor = "200";
	}

	if (leftCoor == null || leftCoor.trim().length == 0) {
		leftCoor = "200";
	}

	if (scrollbars == null || scrollbars.trim().length == 0) {
		scrollbars = "yes";
	}

	windowprops = "toolbar=no,location=no,directories=no,menubar=yes,scrollbars=yes,status=no,top=" + topCoor + ",left=" + leftCoor + ",width=" + WinWidth + ",height=" + WinHeight + ",resizable=" + Resizable;
	preview = window.open(destination, WinName, windowprops);
}

// 11-14-02
// 11-14-06
function getReceiptnotes(receiptId) {
	// var loc =
	// "/tcmIS/Hub/Logistics?session=Active&UserAction=addReceiptNotes";
	var loc = "updatereceiptnotes.do";
	
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;

	if (receiptId.length > 0) {
		loc = loc + "?receiptId=" + receiptId;
		openWinGeneric(loc, "ReceiptNotes", "600", "400", "yes")
	}
}

// 07-10-02
function splitQty(receiptid, Hub, Qtyava, opsEntityId,netPendingAdj) {
	loc = "/tcmIS/Hub/splityqty?session=Active&UserAction=splitqty&receiptid=";
	loc = loc + receiptid;
	loc = loc + "&HubNo=" + Hub;
	loc = loc + "&opsEntityId=" + opsEntityId;
	loc = loc + "&QtyAvailable=" + Qtyava;
    if (netPendingAdj != "0")
    {
        loc = loc + "&netPendingAdj=" + netPendingAdj;
    }
    openWinGeneric(loc, "Previous_Transactions", "350", "300", "no");
}

// 06-25-02
function addnewBin() {
	var testbin;
	eval(testbin = "window.document.receiving.HubName");
	var cur = null;
	eval(cur = (eval(testbin.toString())).selectedIndex);
	var curval = null;
	(curval = (eval(testbin.toString())).options[cur].value);
	// alert(curval);
	var newbinURL = "/tcmIS/Hub/AddNewBin?";
	newbinURL = newbinURL + "&HubName=" + curval;

	openWinGeneric(newbinURL, "add_newbin", "500", "225", "Yes");

}

function showPreviousReceiptTransactions(receiptid, Hub) {
	// loc =
	// "/tcmIS/Hub/PreviousTransactions?session=Active&UserAction=previousreceiving&receiptid=";
	loc = "previoustransactions.do?sortBy=receiptId&submitSearch=yes&receiptId=";
	loc = loc + receiptid;
	
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;
	
	// loc = loc + "&HubName=" + Hub;
	loc = loc + "&branchPlant=" + Hub;
	openWinGeneric(loc, "Previous_Transactions", "800", "400", "yes")
}

function showPreviousTransactions(originallot, Hub) {
	// loc =
	// "/tcmIS/Hub/PreviousTransactions?session=Active&UserAction=previousreceiving&mfglot=";
	var loc = "previoustransactions.do?sortBy=receiptId&submitSearch=yes&mfgLot=";
	loc = loc + originallot;
	// loc = loc + "&HubName=" + Hub;
	loc = loc + "&branchPlant=" + Hub;
	
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;
	
	openWinGeneric(loc, "Previous_Transactions", "800", "400", "yes")
}

function showEmtyBins(itemID, LineNo, Hub) {
	loc = "/tcmIS/servlet/radian.web.servlets.internal.InternalShowEmptyBins?itemID=";
	loc = loc + itemID;
	loc = loc + "&HubNo=" + Hub;
	loc = loc + "&LineNo=" + LineNo;
	openWinGeneric(loc, "Show_Empty_Bins", "300", "150", "no")
}

function openWinGeneric(destination, WinName, WinWidth, WinHeight, Resizable) {
	windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=no,top=200,left=200,width=" + WinWidth + ",height=" + WinHeight + ",resizable=" + Resizable;
	preview = window.open(destination, WinName, windowprops);
}

function addbin(name, entered) {
	var found = false;
	var bin_id = prompt("Enter bin # ", "");
	if (bin_id == null) {
		return false;
	}
	if (bin_id.length < 1) {
		alert("Please enter bin name");
		return false;
	}
	var button_id = name.toString();
	var total = window.document.receiving.Total_number.value;
	if (total == 1) {
		window.document.receiving.UserAction.value = "UPDATE";
		window.document.receiving.SubUserAction.value = "AddBin";
		var line_item = window.document.receiving.item_id.value;
		window.document.receiving.AddBin_Item_Id.value = line_item.toString();
		eval(window.document.receiving.AddBin_Item_Id.value = line_item.toString());
		window.document.receiving.AddBin_Bin_Id.value = bin_id.toString();
		window.document.receiving.DuplLineNumber.value = "NA";
		window.document.receiving.AddBin_Line_No.value = "1";
		found = true;
	}
	else {
		for ( var p = 0; p < total; p++) {
			var line_num;
			eval(line_num = p + 1);
			if (button_id == ("ItemButton" + line_num.toString())) {
				window.document.receiving.UserAction.value = "UPDATE";
				window.document.receiving.SubUserAction.value = "AddBin";
				var line_item = window.document.receiving.item_id[p].value;
				window.document.receiving.AddBin_Item_Id.value = line_item.toString();
				eval(window.document.receiving.AddBin_Item_Id.value = line_item.toString());
				window.document.receiving.AddBin_Bin_Id.value = bin_id.toString();
				window.document.receiving.DuplLineNumber.value = "NA";
				window.document.receiving.AddBin_Line_No.value = line_num.toString();
				found = true;
				break;
			}
		}
	}
	if (found == true) {
		try {
			var target = document.all.item("TRANSITPAGE");
			target.style["display"] = "";
			var target1 = document.all.item("MAINPAGE");
			target1.style["display"] = "none";
		}
		catch (ex) {
		}
		return true;
	}
	return false;
}

function update(entered) {
	window.document.receiving.UserAction.value = "UPDATE";
	window.document.receiving.SubUserAction.value = "UpdPage";
	window.document.receiving.DuplLineNumber.value = "NA";
	window.document.receiving.AddBin_Item_Id.value = "NA";
	window.document.receiving.AddBin_Bin_Id.value = "NA";
	try {
		var target = document.all.item("TRANSITPAGE");
		target.style["display"] = "";
		var target1 = document.all.item("MAINPAGE");
		target1.style["display"] = "none";
	}
	catch (ex) {
	}
	return true;
}

function validateForm() {
	var searchArgument = document.getElementById('SearchField');
	searchArgument.value = searchArgument.value.trim();

	var searchField = document.getElementById('searchlike');
	if ((searchField.value == 'RADIAN_PO' || searchField.value == 'ITEM_ID' || searchField.value == 'RECEIPT_ID' || searchField.value == 'ORIGINAL_RECEIPT_ID') && !isInteger(searchArgument.value, true)) {
		alert("This search value must be an integer");
		return false;
	}

	var hub0 = document.getElementById("HubName");
	if (hub0.value == 'All' && searchArgument.value.length == 0) {
		alert("Please input search text.");
		return false;
	}

	return true;
}

function search(entered) {
	var flag = validateForm();
	if (flag) {
		window.document.receiving.UserAction.value = "NEW";
		window.document.receiving.SubUserAction.value = "NA";
		window.document.receiving.DuplLineNumber.value = "NA";
		window.document.receiving.AddBin_Item_Id.value = "NA";
		window.document.receiving.AddBin_Bin_Id.value = "NA";
		try {
			var target = document.all.item("TRANSITPAGE");
			target.style["display"] = "";
			var target1 = document.all.item("MAINPAGE");
			target1.style["display"] = "none";
		}
		catch (ex) {
		}
		return true;
	}
	else {
		return false;
	}
}

function assignPaper(paper) {
	window.document.receiving.Paper.value = paper;
}

function doInvenPopup() {
	var testurl5 = "/tcmIS/Hub/Logistics?session=Active&generate_labels=yes&UserAction=GenerateLabels";
	var paperSize = window.document.receiving.Paper.value;
	// alert(paperSize);
	testurl4 = testurl5 + "&paperSize=" + paperSize;

	var testbin;
	eval(testbin = "window.document.receiving.HubName");
	var cur = null;
	eval(cur = (eval(testbin.toString())).selectedIndex);
	var curval = null;
	(curval = (eval(testbin.toString())).options[cur].value);
	testurl4 = testurl4 + "&HubNoforlabel=" + curval;

	var printKitLabels = document.getElementById("printKitLabels");
	if (printKitLabels.checked) {
		testurl4 = testurl4 + "&printKitLabels=" + printKitLabels.value;
	}

	openWinGeneric(testurl4, "Generate_labels", "600", "600", "yes")
}

function sendbin(name, entered) {
	var itemID = window.document.EmptyBins.ItemID.value;
	var lineNum = window.document.EmptyBins.LineNum.value;

	var testbin;
	eval(testbin = "window.document.EmptyBins.EmptyBin");
	var cur = null;
	eval(cur = (eval(testbin.toString())).selectedIndex);
	var curval = null;
	(curval = (eval(testbin.toString())).options[cur].value);

	opener.document.receiving.UserAction.value = "UPDATE";
	opener.document.receiving.SubUserAction.value = "AddBin";
	opener.document.receiving.AddBin_Item_Id.value = itemID.toString();
	opener.document.receiving.AddBin_Bin_Id.value = curval.toString();
	opener.document.receiving.DuplLineNumber.value = "NA";
	opener.document.receiving.AddBin_Line_No.value = lineNum.toString();

	opener.document.receiving.submit();
	window.close();
}

function cancel() {
	window.close();
}

function printReceivingBoxLabels() {
	var totalLines = document.getElementById("Total_number").value;
	var checkedCount = 0;
	var labelReceipts = "";
	for ( var p = 0; p < totalLines; p++) {
		try {
			var ok = document.getElementById("row_chk" + (p + 1) + "");

			if (ok.checked) {
				var receiptId = document.getElementById("receipt_id" + (p + 1) + "");
				if (checkedCount > 0) {
					labelReceipts += ','
				}
				labelReceipts += receiptId.value;
				checkedCount++
			}
		}
		catch (ex) {
		}
	}

	// var labelReceipts = document.getElementById("labelReceipts").value;
	// labelReceipts = labelReceipts.replace(/,/gi, "%2c");
	if (labelReceipts.trim().length > 0) {
		var loc = "printreceiptboxlabels.do?labelReceipts=" + labelReceipts + "";
		loc = loc + "&paperSize=64";
		
		if ($v("personnelCompanyId") == 'Radian') 
			  loc = "/tcmIS/hub/" + loc;
		
		openWinGeneric(loc, "printReceiptLabels11", "800", "500", "yes", "80", "80");
	}
}


//http://192.168.18.176/tcmIS/hub/printcontainerlabels.do?labelReceipts=3513506%2c3540653%2c3540654%2c3553258%2c3557220%2c3557232%2c3557233&paperSize=receiptDocument&hubNumber=2405&skipKitLabels=No
function printDocumentLabels(labelType) {
	var totalLines = document.getElementById("Total_number").value;
	var checkedCount = 0;
	var labelReceipts = "";
	var po = null;
	var samePo = false;
//	radianPo_
	for ( var p = 0; p < totalLines; p++) {
		try {
			var ok = document.getElementById("row_chk" + (p + 1) + "");
			if (ok.checked) {
				var receiptId = document.getElementById("receipt_id" + (p + 1) + "");
				if (checkedCount > 0) {
					labelReceipts += ',';
				}
				labelReceipts += receiptId.value;
				checkedCount++;
				thisPO = null; 
				try {
// make sure it always get it.					
					thisPO = document.getElementById("radianPo_" + (p + 1)).innerHTML;
					thisPO = thisPO.split("-")[0];
				} catch(exx) {}
				if( po == null ) {
					po = thisPO;
					samePo = true;
				}
				if( !thisPO )
					samePo = false;
				if( samePo && po != thisPO ) {
					samePo = false;
				}
			}
		}
		catch (ex) {
		}
	}

	var groupReceiptDoc = false;
	if( checkedCount >1 && samePo ) {

//		if( confirm("Do you want to group these receipt certs together?") ) {
		if( confirm("Do you want to group these receipt certs together?\nClick 'OK' to proceed with them grouped.\nClick 'Cancel' to proceed without them grouped.") ) {
//			document.getElementById("groupReceiptDoc").value = "Y";
			groupReceiptDoc = true;
		}
	}

	// var labelReceipts = document.getElementById("labelReceipts").value;
	// labelReceipts = labelReceipts.replace(/,/gi, "%2c");
    var hub0 = document.getElementById("HubName");
    if (labelReceipts.trim().length > 0) {
		var loc = "printcontainerlabels.do?labelReceipts=" + labelReceipts + "";
		loc = loc + "&paperSize=receiptDocument&skipKitLabels=Yes&hubNumber="+hub0.value+"";
		if( groupReceiptDoc ) loc += "&groupReceiptDoc=Y";
		
		if ($v("personnelCompanyId") == 'Radian') 
			  loc = "/tcmIS/hub/" + loc;
		
		openWinGeneric(loc, "printReceiptLabels11", "800", "500", "yes", "80", "80");
	}
   
}

function printReceiptDetailLabels(labelType) {
    generatealllabels("","receiptDetail");
    /*var totalLines = document.getElementById("Total_number").value;
	var checkedCount = 0;
	var labelReceipts = "";
	for ( var p = 0; p < totalLines; p++) {
		try {
			var ok = document.getElementById("row_chk" + (p + 1) + "");

			if (ok.checked) {
				var receiptId = document.getElementById("receipt_id" + (p + 1) + "");
				if (checkedCount > 0) {
					labelReceipts += ','
				}
				labelReceipts += receiptId.value;
				checkedCount++
			}
		}
		catch (ex) {
		}
	}

	// var labelReceipts = document.getElementById("labelReceipts").value;
	// labelReceipts = labelReceipts.replace(/,/gi, "%2c");
    var hub0 = document.getElementById("HubName");
    if (labelReceipts.trim().length > 0) {
		var loc = "/tcmIS/hub/printcontainerlabels.do?labelReceipts=" + labelReceipts + "";
		loc = loc + "&paperSize=receiptDetail&hubNumber="+hub0.value+"";
		openWinGeneric(loc, "printReceiptLabels11", "800", "500", "yes", "80", "80");
	}*/
}

function changeMlItem() {
	var checkedCount = 0;
	var selectedItem = $("selectedItem");
	if (checkMlItemInput()) {
		if (selectedItem.value.trim().length > 0) {
			var receiptsList = "";
			var totalRecords = $("Total_number");
			for (j = 0; j < (totalRecords.value * 1); j++) {
				var receiptId = "";
				receiptId = $("receipt_id" + (j + 1) + "");
				currentcheckBox1 = $("row_chk" + (j + 1) + "");
				itemType = $("itemType" + (j + 1) + "");
				newChemRequestId = $("newChemRequestId" + (j + 1) + "");

				if (currentcheckBox1.checked && itemType.value == "ML" && newChemRequestId.value.trim().length == 0) {
					if (checkedCount > 0) {
						receiptsList += ','
					}
					receiptsList += receiptId.value;
					checkedCount++
				}
			}

			// var labelReceipts =
			// document.getElementById("labelReceipts").value;
			receiptsList = receiptsList.replace(/,/gi, "%2c");
			if (receiptsList.trim().length > 0) {
				var loc = "receivingitemsearchmain.do?receipts=" + receiptsList + "";
				var hubNumber = document.getElementById("HubName").value;
				loc = loc + "&hubNumber=" + hubNumber;
				loc = loc + "&listItemId=" + $("selectedItem").value;
				loc = loc + "&inventoryGroup=" + $("selectedInventoryGroup").value;
				loc = loc + "&catPartNo=" + $("selectedCatPartNo").value;
				loc = loc + "&catalog=" + $("selectedCatalogId").value;
				loc = loc + "&catalogCompanyId=" + $("selectedCatalogCompanyId").value;

				if ($v("personnelCompanyId") == 'Radian') 
					  loc = "/tcmIS/hub/" + loc;
				
				openWinGeneric(loc, "changeItem", "800", "400", "yes", "80", "80");
			}
		}
	}
}

function checkMlItemInput() {
	var noLinesChecked = 0;
	var rowNumber = "";
	// var currentcheckBox = $("ok"+rowNumber+"");
	var totalRecords = $("Total_number");
	var selectedItem = $("selectedItem");
	// var lineitemID = $("itemId"+rowNumber+"");

	/*
	 * if (currentcheckBox.checked) { noLinesChecked ++; }
	 */

	/*
	 * if ( selectedItem.value.trim().length > 0 && (lineitemID.value !=
	 * selectedItem.value) ) { alert("You cannot choose a receipt with
	 * Different ML Item"); currentcheckBox.checked = false; return false; }
	 */

	var allClear = 0;
	var finalMsgt;
	finalMsgt = "You cannot select receipts with: \n\n";

	for (j = 0; j < (totalRecords.value * 1); j++) {
		var lineitemID1 = "";
		lineitemID = $("item_id" + ((j + 1)) + "");
		itemType = $("itemType" + (j + 1) + "");
		rowCheck = document.getElementById("row_chk" + (j + 1) + "");
		newChemRequestId = $("newChemRequestId" + (j + 1) + "");

		if (rowCheck.checked && itemType.value == "ML") {
			if (noLinesChecked == 0) {
				rowNumber = (j + 1);
			}

			noLinesChecked++;
			lineitemID1 = $("item_id" + (j + 1) + "");
			if (lineitemID.value != lineitemID1.value) {
				if (allClear == 0) {
					finalMsgt = finalMsgt + "Different ML Item\n";
				}
				allClear += 1;
			}
			else if (newChemRequestId.value.trim().length > 0) {
				if (allClear == 0) {
					finalMsgt = finalMsgt + "Pending New Chem Request- " + newChemRequestId.value + "\n";
				}
				allClear += 1;
			}
		}
	}

	if (noLinesChecked == 0) {
		selectedItem.value = "";
	}

	if (allClear < 1) {
		if (noLinesChecked != 0) {
			selectedItem.value = $("item_id" + rowNumber + "").value;
			$("selectedInventoryGroup").value = $("inventoryGroup" + rowNumber + "").value;
			$("selectedCatalogId").value = $("catalogId" + rowNumber + "").value;
			$("selectedCatPartNo").value = $("catPartNo" + rowNumber + "").value;
			$("selectedCatalogCompanyId").value = $("catalogCompanyId" + rowNumber + "").value;
		}
		return true;
	}
	else {
		alert(finalMsgt);
		return false;
	}
}

function $(a) {
	return document.getElementById(a);
}

function submitSearchForm() {
	var SearchButton = document.getElementById("SearchButton");
	// to be compatable with new code
	try {SearchButton.click();} catch( ex ) {}
	// document.genericForm.submit();
}

function unitLabelPartNumber(rowNumber) {
	var unitLabelPrinted = document.getElementById("unitLabelPrinted" + rowNumber + "");
	if (unitLabelPrinted.checked) {
		unitLabelPrinted.value = "Y";
		var itemId = document.getElementById("item_id" + rowNumber + "");
		var branchPlant = document.getElementById("branchPlant" + rowNumber + "");
		var inventoryGroup = document.getElementById("inventoryGroup" + rowNumber + "");

		var loc = "unitlabelpartnumber.do?&rowNumber=" + rowNumber;
		loc = loc + "&itemId=" + itemId.value;
		loc = loc + "&hub=" + branchPlant.value;
		loc = loc + "&inventoryGroup=" + inventoryGroup.value;
		
		if ($v("personnelCompanyId") == 'Radian') 
			  loc = "/tcmIS/hub/" + loc;
		
		openWinGeneric(loc, "terminal_root_cause", "500", "300", "no");
	}
	else {
		unitLabelPrinted.value = "N";
	}
}

function sendUnitLabelPartNumber() {
	rowNumber = document.getElementById("rowNumber").value;
	catPartNo = document.getElementById("catPartNo");

	openerunitLabelCatPartNo = opener.document.getElementById("unitLabelCatPartNo" + rowNumber + "");
	openerunitLabelCatPartNo.value = catPartNo.value;

	window.close();
}

function writeOnRequest(receiptid) {
	var loc = "writeonrequest.do?receiptId=";
	loc = loc + receiptid;
	
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;
	
	openWinGeneric(loc, "writeOnRequest", "400", "300", "no");
}

function receiptSpecs(receiptId) {
	// var receiptId = $v("receiptId"+selectedRowId+"");
	var loc = "receiptspec.do?receiptId=" + receiptId;
	
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/distribution/" + loc;
	
	openWinGeneric(loc, "receiptSpecs", "800", "400", "yes", "50", "50", "20", "20", "no");
}

function startMARStest(receiptId)
{
	var loc = "testrequestform.do?uAction=create&receiptId="+receiptId;
	
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/haas/" + loc;

	try {
		parent.openIFrame("testrequest" , loc, "MARS Detail"+ receiptId + "","","N");
	}
	catch (ex) {
		openWinGeneric(loc, "testrequest" , "900", "600", "yes", "80", "80", "yes");
	}   
}

function showPurchOrder(radianPo) {
	var loc = "/tcmIS/supply/purchaseorder.do?po=" + radianPo + "&Action=searchlike&subUserAction=po";
	try {
		parent.parent.openIFrame("purchaseOrder" + radianPo + "", loc, "Purchase Order" + radianPo + "", "", "N");
	}
	catch (ex) {
		openWinGeneric(loc, "showradianPo" + radianPo.replace(/[.]/, "_") + "", "800", "600", "yes", "50", "50");
	}
}

function changeSearchTypeOptions(selectedValue)
{
	  var searchType = $('searchfor');
	  for (var i = searchType.length; i > 0;i--)
		  searchType[i] = null;

	  var actuallyAddedCount = 0;
	  for (var i=0; i < searchMyArr.length; i++) 
	  {
		    if(searchMyArr[i].value == 'Like' &&
			    	(selectedValue == 'RADIAN_PO' || selectedValue == 'RECEIPT_ID' || selectedValue == 'ORIGINAL_RECEIPT_ID' || selectedValue == 'ITEM_ID'))
		    	continue;
		    setOption(actuallyAddedCount++,searchMyArr[i].text,searchMyArr[i].value, "searchfor");
	  }
}

function printLabTestForm(){
	var receiptId = null;

	receiptId = $v("receipt_id"+selectedRowId);

    var loc = "/HaasReports/report/printLabTestForm.do?receiptId="+receiptId;
    openWinGeneric(loc,"PrintLabTestForm","800","600","yes","80","80","yes");
}

function printGHSLabel() {
	var itemId =  document.getElementById("item_id"+selectedRowId);
	var personnelId = document.getElementById("personnelId");
	var printerLocation = document.getElementById("printerLocation");

	var reportLoc = "/HaasReports/report/printghslabels.do"+
    	"?itemId="+itemId.value + 
    	"&personnel_Id="+personnelId.value + 
    	"&printerLocation="+printerLocation.value;
	openWinGeneric(reportLoc,"printGHSLabels","300","200","yes","200","200");
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

function printRTKLabel() {
	var itemId =  document.getElementById("item_id"+selectedRowId);
	var labelQuantity = document.getElementById("qty_recd"+selectedRowId);

	var reportLoc = "printrtklabels.do"
    	+ "?itemId="+itemId.value
		+ "&labelQuantity="+labelQuantity.value;		
	
	if ($v("personnelCompanyId") == 'Radian') 
		reportLoc = "/tcmIS/hub/" + reportLoc;
	
	openWinGeneric(reportLoc,"printRTKLabels","300","200","yes","200","200");
}