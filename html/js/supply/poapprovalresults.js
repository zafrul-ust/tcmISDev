var beangrid;
var resizeGridWithWindow = true;
var saveRowClass = null;
var selectedRowId = null;
var children = [];

function resultOnLoad() {
	totalLines = document.getElementById("totalLines").value;
	if (totalLines > 0) {
		document.getElementById("poApprovalData").style["display"] = "";
		initGridWithGlobal(gridConfig);
		
	}
	else {
		document.getElementById("poApprovalData").style["display"] = "none";
	}
	
	displayGridSearchDuration();
	setResultFrameSize();
}

function selectRow(rowId, cellInd) {
	//beangrid.selectRowById(rowId, null, false, false);
/*
	if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/) == -1)
		setRowClass(rowId, '' + saveRowClass + 'Selected'); */

	selectedRowId = rowId;
}

function rightClickRow(rowId, cellInd) {
	selectRow(rowId, cellInd);
	if (cellValue(rowId, "permission") == "Y") {
		toggleContextMenu("approvalMenu");
	}
	else {
		toggleContextMenu("rightClickMenu")
	}
}

function grabPoApproval() {
	var rowCount = beangrid.getRowsNum();

	rowCount = rowCount * 1;
	
	var oneIsGrabbed = false;
	for ( var p = 1; p <= rowCount; p++) {
		var cid = "grab" + p;
		if ($(cid) != null && $(cid).checked) {
			oneIsGrabbed = true;
			break;
		}
	}
	parent.setApproveButtonVisible(oneIsGrabbed);
}

function approveCheckedOrders() {
	parent.showPleaseWait();
	$("uAction").value = "approveAll";
	beangrid.parentFormOnSubmit();
	document.genericForm.submit();
}

function approvePo() {
	var checkbox = $("grab"+selectedRowId);
	try {
		checkbox.checked = true;
		approveCheckedOrders();
	} catch(e) {
		
	}
}

function rejectPo(rejectData) {
	//customConfirm("Are you sure you want to reject this PO?");
	//console.log("PO " + rejectData.poNumber + " rejected");
}

function showRejectionDialog() {
	var poNumber = cellValue(selectedRowId, "radianPo");
	var opsEntityId = $v("opsEntityId");
	var loc = "poapprovalreject.do?uAction=selectRejectionCode&poNumber="+poNumber+"&opsEntityId="+opsEntityId;
	children[children.length] = openWinGeneric(loc,"RejectPurchaseOrder_"+poNumber+"","300","300","yes","50","50","yes");
}

/*function showRejectionDialog() {
	var poNumber = cellValue(selectedRowId, "radianPo");
	var formDiv = j$("<div></div>");
	var dropdownLabel = j$("<label></label>").text("???Reason???: ");
	dropdownLabel.attr("for","poApprovalCode");
	dropdownLabel.addClass("optionTitleBoldLeft");
	formDiv.append(dropdownLabel);
	var dropdown = j$("<select></select>");
	dropdown.attr("id","poApprovalCode");
	dropdown.attr("name","poApprovalCode");
	dropdown.css("vertical-align","top");
	for (i in poApprovalCodes["Rejected"]) {
		var option = j$("<option></option>").text(poApprovalCodes["Rejected"][i]);
		option.attr("value",poApprovalCodes["Rejected"][i]);
		dropdown.append(option);
	}
	formDiv.append(dropdown);
	formDiv.append(j$("<br/>"));
	formDiv.append(j$("<br/>"));
	var commentBoxLabel = j$("<label></label>").text("???Comments???: ");
	commentBoxLabel.attr("for","poApprovalCode");
	commentBoxLabel.addClass("optionTitleBoldLeft");
	formDiv.append(commentBoxLabel);
	var commentBox = j$("<textarea></textarea>");
	commentBox.attr("id","actionComment");
	commentBox.attr("name","actionComment");
	formDiv.append(commentBox);
	var poNumberInput = j$("<input></input>");
	poNumberInput.attr("id", "radianPo");
	poNumberInput.attr("name", "radianPo");
	poNumberInput.attr("type", "hidden");
	poNumberInput.val(poNumber);
	formDiv.append(poNumberInput);
	var rejectText = "???Reject PO??? " + poNumber;
	
	
	customDialog({text:formDiv.html(), header:rejectText, 
					dialogType: "confirm", 
					severity: "Warning",
					continueText:rejectText, cancelText:"???Cancel???",
					onContinue:function() {
						rejectPo({poNumber: parent.document.getElementById("radianPo").value});
					}});
}*/

function showApprovalChain() {
	var poNumber = cellValue(selectedRowId, "radianPo");
	var poApprId = cellValue(selectedRowId, "poApprovalId");
	var cancelled = false;
	customDialog({text:"Loading...", dialogType:"loader", onCancel: function() { cancelled = true; }});
	j$.ajax({
		url:"poapprovalmain.do",
		cache:false,
		dataType:"json",
		data:{uAction: "showApprovalChain", radianPo:poNumber, poApprovalId:poApprId},
		success: function(data) {
			var chain = data.approvalChain;
			var tblDiv = j$("<div></div>");
			var tbl = j$("<table></table>");
			for(i in chain) {
				var row = j$("<tr></tr>");
				var limit = j$("<td></td>").text(chain[i].approverLimit);
				limit.css("padding-left", "20px");
				row.append(limit);
				var name = j$("<td></td>").text(chain[i].approverName);
				name.css("padding-left", "20px");
				row.append(name);
				var action = j$("<td></td>").text(chain[i].approvalAction);
				action.css("padding-left", "20px");
				row.append(action);
				if (chain[i].approvalAction == "Approved" || chain[i].approvalAction == "Rejected") {
					row.css("font-weight", "bold");
				}
				tbl.append(row);
			}
			tblDiv.append(tbl);
			
			if ( ! cancelled) {
				customDialog({text:tblDiv.html(), dialogType: "alert", header:messagesData.poApprovalChain+" - "+poNumber, continueText:messagesData.ok});
			}
		}
	});
}

function viewPo() {
	var radianPo = cellValue(selectedRowId,"radianPo");		   	  
	var loc = "/tcmIS/supply/purchaseorder.do?po="+ radianPo + "&action=searchlike";
	try {
		parent.parent.openIFrame("purchaseOrder"+radianPo+"",loc,""+messagesData.purchaseorder+" "+radianPo+"","","N");
	} catch (ex) {
		openWinGeneric(loc,"PurchaseOrder_"+radianPo+"","800","600","yes","50","50","yes");
	}
}

function closeAllchildren() {
	customDialog("destroy");
}

function doRefresh(requestTabId) {
	window.setTimeout('finishRefresh();', 10);
}

function finishRefresh() {
	// redo the search
	document.genericForm.target='';
	document.getElementById('uAction').value = 'search';

	parent.showPleaseWait();


	document.genericForm.submit();
}