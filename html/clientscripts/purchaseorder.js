var submitcount = 0;
var updatecount = 0;
var max = 20 // maximum rows
var color = "#0000ff";
var selected_row = 0;
var selected_rowid = "row1";

// all same level, at least one item
// example code with menu-item and cascade-menu
// menus[menus.length ] = 'text=<font
// color="gray">'+messagesData.iteminv+'</font>;url=javascript:doNothing();' ;
// menus[menus.length =
// 'showmenu=nextLevelMenuName;text='+messagesData.iteminv+';image=';

function replaceMenu(menuname, menus) {
	var i = mm_returnMenuItemCount(menuname);

	for (; i > 1; i--)
		mm_deleteItem(menuname, i);

	for (i = 0; i < menus.length;) {
		var str = menus[i];

		i++;
		mm_insertItem(menuname, i, str);
		// delete original first item.
		if (i == 1)
			mm_deleteItem(menuname, 1);
	}
}

function makeCursorBusy() {
	document.body.style.cursor = 'wait';
}

function makeCursorNormal() {
	document.body.style.cursor = 'auto';
}

String.prototype.trim = function() {
	// skip leading and trailing whitespace
	// and return everything in between
	return this.replace(/^\s*(\b.*\b|)\s*$/, "$1");
}

function SubmitOnlyOnce() {
	if (submitcount == 0) {
		submitcount++;
		return true;
	} else {
		alert("This form has already been submitted.  Thanks!");
		return false;
	}
}

function donothing(entered) {
	return false;
}

function addOptionItem(obj, value, text) {
	var index = obj.length;
	obj.options[index] = new Option(text, value);

	obj.options[index].selected = true;
}

function lookaheadchanged(selectedRow, rownum) {
	lookaheadchanged12 = document.getElementById("lookaheadchanged"
			+ selectedRow + "");
	lookaheadchanged12.value = "Yes";

	lookaheadset = document.getElementById("lookaheaddays" + selectedRow + "_"
			+ rownum + "");
	if (lookaheadset.value.trim().length > 0 && lookaheadset.value * 1 == 0) {
		alert("Please enter a value greater than Zero.");
		lookaheadset.value = "";
	}
}

function resendPo(param)//TCMISDEV-778 button action resend feature to supplier for POs in status ProblemWbuy
{
	if(typeof(param) != 'undefined' && param != null && param == 'resendDBuyPo')
		window.document.purchaseorder.Action.value = 'resendDBuyPo';
	else
		window.document.purchaseorder.Action.value = 'resendWBuyPo';
    return true;
}

function actionValue(name, entered) {
	var actvalue = name.toString();
	window.document.purchaseorder.Action.value = actvalue;

	var result;
	var finalMsgt;
	var allClear = 0;
	var sameShipto = 0;
	var assPrQty = 0;
	var shipTomsg;
	var assPRMsg = "";
	var specMsgCount = 0;
	var finalspecCheckMsg = "";
	var qtyisZero = 0;
	var qtyisZeroMsg = "";

	var itemIdschecked = new Array();
	var itemIdcheckCount = 0;

	var chargeTable = document.getElementById("linetable");
	var allTRs = chargeTable.getElementsByTagName("tr");

	if (actvalue == "confirm") {
		if (allTRs.length == 0) {
			alert("You Cannot Confirm a PO with no Lines");
			result = false;
			return result;
		}
	}

	var supplierDefaultPaymentTerms = document
			.getElementById("supplierDefaultPaymentTerms");
	if (supplierDefaultPaymentTerms.value.trim().length == 0
			&& actvalue == "confirm") {
		if (!confirm("The supplier on this PO is inactive. Do you want to continue?")) {
			result = false;
			return result;
		}
	}

	var currency = document.getElementById("currency");
	if ((currency.value == "None" || currency.value.trim().length == 0)
			&& allTRs.length > 0) {
		alert("Please Pick a Currency.");
		result = false;
		return result;
	}

	var invengrp = document.getElementById("invengrp");
	if (invengrp.value.trim().length == 0) {
		alert("Please Pick a valid Ship To and Inventory Group.");
		// finalMsgt = finalMsgt + " Inventory Group.\n" ;
		// allClear = 1;
		result = false;
		return result;
	}

	if (actvalue == "confirm" || actvalue == "printdraft") {
		finalMsgt = "Please enter valid values for: \n\n";
	} else {

	}

	var consignedpo = document.getElementById("consignedpo");
	var HubName = document.getElementById("HubName");
	/*
	 * if (HubName.value == "None") { finalMsgt = finalMsgt + " Hub.\n" ;
	 * allClear = 1; }
	 */

	var fob = document.getElementById("fob");
	if (fob.value == "None" || fob.value.trim().length == 0) {
		finalMsgt = finalMsgt + " Trade Terms.\n";
		allClear = 1;
	}

	var paymentterms = document.getElementById("paymentterms");
	if (paymentterms.value.trim().length == 0) {
		finalMsgt = finalMsgt + " Payment Terms.\n";
		allClear = 1;
	}

	var validsupplier = document.getElementById("validsupplier");
	if (validsupplier.value == "No") {
		finalMsgt = finalMsgt + " Supplier.\n";
		var suppid = document.getElementById("supplierid");
		suppid.value = "";
		allClear = 1;
	}

	var povalue = document.getElementById("po");
	if (povalue.value.length > 0) {
		var validpo = document.getElementById("validpo");
		if (validpo != null && validpo != undefined && validpo.value == "No") {
			finalMsgt = finalMsgt + " PO.\n";
			povalue.value = "";
			allClear = 1;
		}
	}

	var bpovalue = document.getElementById("bpo");
	var validbpo = document.getElementById("validbpo");
	if (bpovalue.value.length > 0) {
		if (validbpo.value == "No") {
			finalMsgt = finalMsgt + " Blanket PO.\n";
			bpovalue.value = "";
			allClear = 1;
		}

		var bostartdate = document.getElementById("bostartdate");
		if (bostartdate.value.length == 10) {
			if (checkdate(bostartdate) == false) {
				finalMsgt = finalMsgt + " Valid BPO Start Date.\n";
				allClear = 1;
			}
		} else if (bostartdate.value.length > 0) {
			finalMsgt = finalMsgt + " Valid BPO Start Date.\n";
			allClear = 1;
		} else {
			finalMsgt = finalMsgt + " Valid BPO Start Date.\n";
			allClear = 1;
		}

		var boenddate = document.getElementById("boenddate");
		if (boenddate.value.length == 10) {
			if (checkdate(boenddate) == false) {
				finalMsgt = finalMsgt + " Valid BPO End Date.\n";
				allClear = 1;
			}
		} else if (boenddate.value.length > 0) {
			finalMsgt = finalMsgt + " Valid BPO End Date.\n";
			allClear = 1;
		} else {
			finalMsgt = finalMsgt + " Valid BPO End Date.\n";
			allClear = 1;
		}
	}

	// if ((povalue.value.length > 0) && !(bpovalue.value.length > 0) )
	// if (!(povalue.value.length > 0) && (bpovalue.value.length > 0) )

	if (!(povalue.value.length > 0) && !(bpovalue.value.length > 0)) {
		finalMsgt = finalMsgt + " PO.\n";
		finalMsgt = finalMsgt + " Blanket Order.\n";
		allClear = 1;
	}

	var shiptoid1 = document.getElementById("shiptoid");
	var validbpo = document.getElementById("validbpo");
	if ((validbpo.value != "Yes") || (povalue.value.length > 0))
	// if ( (povalue.value.length > 0) && !(bpovalue.value.length > 0) )
	{
		var validshipto = document.getElementById("validshipto");
		if (validshipto.value == "No") {
			finalMsgt = finalMsgt + " Ship To.\n";
			shiptoid1.value = "";
			var shiptocompanyid = document.getElementById("shiptocompanyid");
			shiptocompanyid.value = "";
			// HubName.value = "None";
			allClear = 1;
		}

		var validcarrier = document.getElementById("validcarrier");
		var carrierID = document.getElementById("carrierID");
		// if (carrierID.value.length > 0)
		{
			if (validcarrier.value == "No") {
				finalMsgt = finalMsgt + " Shipping.\n";
				carrierID.value = "";
				allClear = 1;
			}
		}
	}

	var validordertaker = document.getElementById("validordertaker");
	var ordertaker = document.getElementById("ordertaker");
	// if (ordertaker.value.length > 0)
	{
		if (validordertaker.value == "No") {
			finalMsgt = finalMsgt + " Order Taker.\n";
			ordertaker.value = "";
			ordertakerID = document.getElementById("ordertakerID");
			ordertakerID.value = "";
			allClear = 1;
		}
	}

	var validshipto1 = document.getElementById("validshipto");
	if (validshipto1.value == "Yes") {
		shipTomsg = "The Ship To on the PO - " + shiptoid1.value
				+ " is Different From Ship To on\n\n ";
	}

	var nonintegerReceiving = document.getElementById("nonintegerReceiving");
	mytable = document.getElementById("linetable");
	var allTRs = mytable.getElementsByTagName("tr");
	var nowofRows = (allTRs.length) * 1;

	for (j = 0; j < nowofRows; j++) {
		var validitem = document.getElementById("validitem" + (j + 1) + "");
		var linestatus = document.getElementById("linestatus" + (j + 1) + "");
		var originallinestatus = document.getElementById("originallinestatus"
				+ (j + 1) + "");
		var itemtype = document.getElementById("itemtype" + (j + 1) + "");
		var canassignaddcharge = document.getElementById("canassignaddcharge"
				+ (j + 1) + "");

		var ammendment = document.getElementById("ammendment" + (j + 1) + "");
		var linechange = document.getElementById("linechange" + (j + 1) + "");

		var lineNumber = document
				.getElementById("row" + (j + 1) + "linenumber");
		var LineMsg = "";
		var allClearforline = 0;

		if ((linestatus.value != "Remove")) {
			if ((linechange.value == "Yes")
					|| (originallinestatus.value == "Unconfirmed")
					|| (canassignaddcharge.value != "Y"))
			// if (actvalue == "confirm")
			{
				// if ( (originallinestatus.value == "Confirmed") &&
				// (linestatus.value == "Unconfirmed") )
				if (((linechange.value == "Yes") && (ammendment.value * 1 > 0))
						|| ((ammendment.value * 1 > 0)
								&& (linestatus.value != "Confirmed") && (actvalue == "confirm"))) {
					var ammendmentcomments = document
							.getElementById("ammendmentcomments" + (j + 1) + "");
					var ammendcomment = prompt(
							"Enter Comment for Changes to line # "
									+ lineNumber.value + "", ""
									+ ammendmentcomments.value + "");
					ammendmentcomments.value = ammendcomment;
				}

				var lineitemid = document.getElementById("lineitemid" + (j + 1)
						+ "");
				var quantity = document
						.getElementById("lineqty" + (j + 1) + "");
				var lineArchived = document.getElementById("lineArchived"
						+ (j + 1) + "");

				if (quantity.value.trim().length > 0 && quantity.value * 1 == 0
						&& lineitemid.value.trim().length == 0
						&& ammendment.value * 1 == 0) {
					// alert("Here");
					selectedRowStatus1 = document.getElementById("linestatus"
							+ (j + 1) + "");
					selectedRowStatus1.value = "Remove";
				} else if (validitem.value == "No") {
					LineMsg = LineMsg + "       Valid Item.\n";
					lineitemid.value = "";

					allClearforline = 1;
				} else if (lineitemid.value.trim().length == 0) {
					LineMsg = LineMsg + "       Valid Item.\n";

					allClearforline = 1;
				}

				// if ( (validbpo.value != "Yes") || (povalue.value.length > 0)
				// )
				{
					if ((nonintegerReceiving.value == "Y" && isFloat(quantity.value))) {

					} else if (!(isSignedInteger(quantity.value))) {
						LineMsg = LineMsg + "       Valid Quantity.\n";
						allClearforline = 1;
					} else if (quantity.value.trim().length == 0) {
						LineMsg = LineMsg + "       Valid Quantity.\n";
						allClearforline = 1;
					} else if ((originallinestatus.value == "Confirmed")
							&& (quantity.value == 0)) {
						qtyisZero = 1;
						qtyisZeroMsg = qtyisZeroMsg + "The Quantity on Line "
								+ lineNumber.value + " is 0.\n";
					} else if ((lineitemid.value * 1 == 430158)
							|| (lineitemid.value * 1 == 500743)) {
						if (quantity.value > 0) {
							LineMsg = LineMsg
									+ "       Quantity cannot be positive.\n";
							allClearforline = 1;
						}
					} else if (quantity.value < 0 && lineArchived.value == "N") {
						LineMsg = LineMsg
								+ "       Quantity cannot be negative.\n";
						allClearforline = 1;
					}

					/*
					 * var purchasingUnitsPerItem =
					 * document.getElementById("purchasingUnitsPerItem"+(j+1)+"");
					 * if (itemtype.value !="MV" &&
					 * purchasingUnitsPerItem.value.trim().length > 0 &&
					 * (quantity.value%purchasingUnitsPerItem.value != 0)) {
					 * LineMsg = LineMsg + " Quantity for MV item should be
					 * multiple of pkg.\n" ; allClearforline = 1; }
					 */
				}

				var qtyreceived = document.getElementById("qtyreceived"
						+ (j + 1) + "");
				// alert(""+(quantity.value*1)+" Received
				// "+(qtyreceived.value*1)+"");
				// alert((quantity.value*1) < (qtyreceived.value*1));
				// if ( (qtyreceived.value*1) > 0 )
				{
					if (Math.abs((quantity.value * 1)) < Math
							.abs((qtyreceived.value * 1))) {
						LineMsg = LineMsg
								+ "       Cannot Have Qty Less Than Qty Received.\n";
						allClearforline = 1;
					}
				}

				var lineunitprice = document.getElementById("lineunitprice"
						+ (j + 1) + "");
				if (!(isFloat(lineunitprice.value))) {
					if (canassignaddcharge.value != "Y"
							&& lineunitprice.value < 0) {

					} else {
						LineMsg = LineMsg + "       Valid Unit Price.\n";
						allClearforline = 1;
					}
				} else if (lineunitprice.value.trim().length == 0) {
					LineMsg = LineMsg + "       Valid Unit Price.\n";
					allClearforline = 1;
				}

				if ((validbpo.value != "Yes") || (povalue.value.length > 0)) {
					if (canassignaddcharge.value == "Y") {
						var shelflife = document.getElementById("shelflife"
								+ (j + 1) + "");
						if (povalue.value * 1 > 600000) {
							if (!(isInteger(shelflife.value))) {
								LineMsg = LineMsg
										+ "       Valid Shelf Life.\n";
								allClearforline = 1;
							}
						}

						// 01-16-03
						var datepromised = document
								.getElementById("datepromised" + (j + 1) + "");
						if (datepromised.value.length == 10) {
							if (checkdate(datepromised) == false) {
								LineMsg = LineMsg
										+ "       Valid Projected Delivery Date in mm/dd/yyyy Format.\n";
								allClearforline = 1;
							}
						} else if (datepromised.value.length > 0) {
							LineMsg = LineMsg
									+ "       Valid Projected Delivery Date in mm/dd/yyyy Format.\n";
							allClearforline = 1;
						} else {
							LineMsg = LineMsg
									+ "       Valid Projected Delivery Date in mm/dd/yyyy Format.\n";
							allClearforline = 1;
						}

						var projsuppshipdate = document
								.getElementById("projsuppshipdate" + (j + 1)
										+ "");
						if (povalue.value * 1 > 608120) {
							if (projsuppshipdate.value.length == 10) {
								if (checkdate(projsuppshipdate) == false) {
									LineMsg = LineMsg
											+ "       Valid Promised Ship Date in mm/dd/yyyy Format.\n";
									allClearforline = 1;
								}
							} else if (projsuppshipdate.value.length > 0) {
								LineMsg = LineMsg
										+ "       Valid Promised Ship Date in mm/dd/yyyy Format.\n";
								allClearforline = 1;
							} else {
								LineMsg = LineMsg
										+ "       Valid Promised Ship Date in mm/dd/yyyy Format.\n";
								allClearforline = 1;
							}
						}

						if (projsuppshipdate.value.length == 10
								&& datepromised.value.length == 10) // 02-11-03
						{
							var projshipdate = new Date(projsuppshipdate.value
									.trim());
							var promdate = new Date(datepromised.value.trim());

							// alert("projshipdate "+projshipdate+" promdate
							// "+promdate+"");
							if (projshipdate.getTime() > promdate.getTime()) {
								LineMsg = LineMsg
										+ "       Promised Supplier Ship Date cannot be greater than Projected Delivery Date.\n";
								allClearforline = 1;
							}
						}

						var dateneeded = document.getElementById("dateneeded"
								+ (j + 1) + "");
						if (dateneeded.value.length == 10) {
							if (checkdate(dateneeded) == false) {
								LineMsg = LineMsg
										+ "       Valid Date Needed in mm/dd/yyyy Format.\n";
								allClearforline = 1;
							}
						} else if (dateneeded.value.length > 0) {
							LineMsg = LineMsg
									+ "       Valid Date Needed in mm/dd/yyyy Format.\n";
							allClearforline = 1;
						} else {
							LineMsg = LineMsg
									+ "       Valid Date Needed in mm/dd/yyyy Format.\n";
							allClearforline = 1;
						}

						var supplierquantity = document
								.getElementById("supplierqty" + (j + 1) + "");
						if (supplierquantity.value.length > 0) {
							if (!(isFloat(supplierquantity.value))) {
								LineMsg = LineMsg
										+ "       Valid Supplier Quantity.\n";
								allClearforline = 1;
							}
						}

						var supplierunitprice = document
								.getElementById("supplierunitprice" + (j + 1)
										+ "");
						if (supplierunitprice.value.length > 0) {
							if (!(isFloat(supplierunitprice.value))) {
								LineMsg = LineMsg
										+ "       Valid Supplier Unit Price.\n";
								allClearforline = 1;
							}
						}

						var supplierextprice = document
								.getElementById("supplierextprice" + (j + 1)
										+ "");
						if (supplierextprice.value * 1 > 0) {
							var linetotalprice = document
									.getElementById("linetotalprice" + (j + 1)
											+ "");

							// if (linetotalprice.value !=
							// supplierextprice.value)
							if ((linetotalprice.value * 1).toFixed(2) != (supplierextprice.value * 1)
									.toFixed(2)) {
								LineMsg = LineMsg
										+ "       Please Check Total Price of Line and the Supplier Price.\n";
								allClearforline = 1;
							}

						}

						/*
						 * var validspec =
						 * document.getElementById("validspec"+(j+1)+""); if
						 * (validspec.value == "No") { LineMsg = LineMsg + "
						 * Valid Specs.\n" ; allClearforline = 1; }
						 * 
						 * var validflowdown =
						 * document.getElementById("validflowdown"+(j+1)+""); if
						 * (validflowdown.value == "No") { LineMsg = LineMsg + "
						 * Valid Flowdowns.\n" ; allClearforline = 1; }
						 */

						var nofassociatedprs = document
								.getElementById("nofassociatedprs" + (j + 1)
										+ "");
						var totalofassociatedprs = document
								.getElementById("totalofassociatedprs"
										+ (j + 1) + "");

						var itemAlreadyChecked = false;
						if (itemIdschecked != null) {
							for (var i = 0; i < itemIdschecked.length; i++) {
								oldCheckedItemId = itemIdschecked[i];
								if (oldCheckedItemId == lineitemid.value) {
									itemAlreadyChecked = true;
								}
							}
							if (!itemAlreadyChecked) {
								// alert("totalofassociatedprs
								// "+totalofassociatedprs.value+"");
								itemIdschecked[itemIdcheckCount] = lineitemid.value;
								itemIdcheckCount++;
								var totalCheckItemQty = 0;
								for (itmCheck = 0; itmCheck < nowofRows; itmCheck++) {
									var currentLineitemid = document
											.getElementById("lineitemid"
													+ (itmCheck + 1) + "");
									var currentLineQuantity = document
											.getElementById("lineqty"
													+ (itmCheck + 1) + "");
									if (currentLineitemid.value == lineitemid.value
											&& currentLineQuantity.value.trim().length > 0) {
										// alert("line "+(itmCheck+1)*1000+" Qty
										// "+currentLineQuantity.value);
										totalCheckItemQty = totalCheckItemQty
												+ currentLineQuantity.value * 1;
										// alert("totalCheckItemQty
										// "+totalCheckItemQty);
									}
								}
								// alert("totalCheckItemQty
								// "+totalCheckItemQty+" totalofassociatedprs
								// "+totalofassociatedprs.value+"");
								if (totalCheckItemQty < totalofassociatedprs.value * 1) {
									assPrQty = 1;
									assPRMsg = assPRMsg
											+ "The sum of all quantities for item "
											+ lineitemid.value
											+ " is "
											+ totalCheckItemQty
											+ " which is less than "
											+ totalofassociatedprs.value
											+ " which is the quantity associated with the Buy Orders.\n\n";
								}
							}
						}

						var prshipto = document.getElementById("prshipto"
								+ (j + 1) + "");
						if (prshipto.value.trim().length > 0) {
							if (prshipto.value == shiptoid1.value) {

							} else {
								// alert(""+prshipto.value+"
								// "+shiptoid1.value+"");
								shipTomsg = shipTomsg + "Line " + (j + 1)
										+ ": " + prshipto.value + "\n";
								sameShipto = 1;
							}
						}
					}

					var lineSpecAllClear = 0;
					var lineSpecCheckMsg = "";
					var noofspecs = document.getElementById("noofspecsdivrow"
							+ (j + 1) + "" + (j + 1) + "");
					if (noofspecs != null && noofspecs.value * 1 > 0) {
						for (specRow = 0; specRow < noofspecs.value; specRow++) {
							speccocchk = document.getElementById("speccocchk"
									+ (j + 1) + "_" + specRow + "");
							speccoachk = document.getElementById("speccoachk"
									+ (j + 1) + "_" + specRow + "");
							specColor = document.getElementById("specColor"
									+ (j + 1) + "_" + specRow + "").value;
							specCurrentCocRequirement = document
									.getElementById("specCurrentCocRequirement"
											+ (j + 1) + "_" + specRow + "").value;
							specCurrentCoaRequirement = document
									.getElementById("specCurrentCoaRequirement"
											+ (j + 1) + "_" + specRow + "").value;
							if (specColor * 1 == 1) {
								if (specCurrentCocRequirement == 'Y'
										&& !speccocchk.checked) {
									lineSpecAllClear = 1;
								}

								if (specCurrentCoaRequirement == 'Y'
										&& !speccoachk.checked) {
									lineSpecAllClear = 1;
								}
							}
						}
					}
					if (lineSpecAllClear > 0) {
						lineSpecCheckMsg = lineSpecCheckMsg
								+ "       All the required specs are not checked on this line.";
						specMsgCount = 1;
					}

					var nooflinesinaddcharge = document
							.getElementById("nooflinesinaddcharge" + (j + 1)
									+ "");
					if ((nooflinesinaddcharge.value * 1 > 0)
							|| (canassignaddcharge.value != "Y")) {
						var numofaddChargeChecks = 0;
						var numofMaLinesWithQty = 0;
						for (aj = 0; aj < nooflinesinaddcharge.value; aj++) {
							var addchargecheckdivrow = document
									.getElementById("addchargecheckdivrow"
											+ (j + 1) + "" + (j + 1) + ""
											+ (aj + 1) + "");
							if (addchargecheckdivrow.checked) {
								numofaddChargeChecks++;
							}

							chargelinenumber = document
									.getElementById("chargelinenumber"
											+ (aj + 1) + "");
							var chargeLineQuantity = document
									.getElementById("lineqty"
											+ chargelinenumber.value + "");
							// if (chargeLineQuantity.value*1 > 0)
							{
								numofMaLinesWithQty++;
							}
							if (chargeLineQuantity.value * 1 == 0
									&& addchargecheckdivrow.checked) {
								LineMsg = LineMsg
										+ "       Cannot Assign Additional Charges to a Canceled Line.\n";
								LineMsg = LineMsg
										+ "       You can either assign it to a different line or cancel this additional charge line.\n";
								allClearforline = 1;
							}
						}

						if (numofaddChargeChecks == 0
								&& numofMaLinesWithQty > 0) {
							LineMsg = LineMsg
									+ "       Must Assign Additional Charges.\n";
							allClearforline = 1;
						}
					}

					// alert(quantity.value);
					if (quantity.value.trim().length > 0 && quantity.value == 0
							&& !(quantity.value * 1 < qtyreceived.value * 1)) {
						allClearforline = 0;
					}
				} else {
					/*
					 * var nooflinesinaddcharge =
					 * document.getElementById("nooflinesinaddcharge"+(j+1)+"");
					 * alert(nooflinesinaddcharge.value);
					 */

					/*
					 * var nooflinesinaddcharge =
					 * document.getElementById("originallinestatus"+(j+1)+"");
					 * alert(nooflinesinaddcharge.value);
					 */
				}
				// var noofspecs =
				// document.getElementById("noofspecsdivrow"+(j+1)+""+(j+1)+"");
				// alert(noofspecs.value);

			}

			if (allClearforline > 0) {
				finalMsgt = finalMsgt + " For Line " + lineNumber.value + ":\n";
				finalMsgt = finalMsgt + LineMsg;
				allClear = 1;
			}

			if (lineSpecAllClear > 0) {
				finalspecCheckMsg = finalspecCheckMsg + " For Line "
						+ lineNumber.value + ":\n";
				finalspecCheckMsg = finalspecCheckMsg + lineSpecCheckMsg;
			}
		}
	}

	if (actvalue == "confirm") {
		if (sameShipto > 0) {
			shipTomsg = shipTomsg + "\n\nClick Ok to Approve.\n";
			if (confirm(shipTomsg)) {

			} else {
				result = false;
				return result;
			}
		}

		if (qtyisZero > 0) {
			qtyisZeroMsg = qtyisZeroMsg
					+ "\nThese Line(s) Will be Closed and Cannot be Reopened.\n\nYou have to Disassociate any PR's Attached if you Want to Use them on a Different PO.\n\nDo you Want to Continue?\n\nClick Ok to Continue.\n";
			if (confirm(qtyisZeroMsg)) {

			} else {
				result = false;
				return result;
			}
		}
	}

	if (specMsgCount > 0) {
		if (confirm(finalspecCheckMsg
				+ "\n\nDo you want to continue?\n\nClick Ok to continue.\n")) {

		} else {
			result = false;
			return result;
		}
	}

	if (assPrQty > 0) {
		assPRMsg = assPRMsg
				+ "This might lead to creation of new Buy Orders if you do not disassociate some buy orders or update quantities on this PO.\n\n Do you want to continue?\n\nClick Ok to continue.\n";
		if (confirm(assPRMsg)) {

		} else {
			result = false;
			return result;
		}
	}

	if (actvalue == "printdraft") {
		if (allClear < 1) {
			result = true;
			return result;
		} else {
			alert(finalMsgt);
			result = false;
			return result;
		}
	} else if (allClear < 1) {
		result = true;
		HubName.disabled = false;
		paymentterms.disabled = false;
		fob.disabled = false;
		consignedpo.disabled = false;
		currency.disabled = false;

		try {
			var target = document.all.item("TRANSITPAGE");
			target.style["display"] = "";
			var target1 = document.all.item("MAINPAGE");
			target1.style["display"] = "none";
		} catch (ex) {
		}

		updatecount++;
		return result;

	} else {
		if (actvalue == "confirm") {
			alert(finalMsgt);
			result = false;
		} else {
			HubName.disabled = false;
			paymentterms.disabled = false;
			fob.disabled = false;
			consignedpo.disabled = false;
			currency.disabled = false;

			result = true;
			try {
				var target = document.all.item("TRANSITPAGE");
				target.style["display"] = "";
				var target1 = document.all.item("MAINPAGE");
				target1.style["display"] = "none";
			} catch (ex) {
			}
			updatecount++;
		}
		return result;
	}
}

function currencychanged(val) {
	enDisableConfirm(false);
	if (val == 'USD')
		setConversionRate(1);
	else {
		j$.ajax({
			type : "POST",
			url : "/tcmIS/purchase/popo",
			data : {
				Action : 'getRate',
				currentCurrency : val,
				thePO : $v('po')
			},
			success : setConversionRate
		});
	}

}

function setConversionRate(cnvs) {
	$("currencyExchangeRate").value = cnvs;
	mytable = document.getElementById("linetable");
	var allTRs = mytable.getElementsByTagName("tr");
	var nowofRows = (allTRs.length) * 1;

	if (nowofRows > 0) {
		document.getElementById("addBuyOrders").style["display"] = "none";
	}
	for (zz = 0; zz < nowofRows; zz++) {
		var linestatus = document.getElementById("linestatus" + (zz + 1) + "");
		var canassignaddcharge = document.getElementById("canassignaddcharge"
				+ (zz + 1) + "");

		// alert("722 "+(zz+1)+"");
		if ((linestatus.value != "Remove")) {
			setlineUnconfirmed("" + (zz + 1) + "");
			changeTotalPrice("" + (zz + 1) + "");

			if (canassignaddcharge.value == "Y") {
				// alert("731 "+(zz+1)+"");
				changeSuppTotalPrice("" + (zz + 1) + "");
			}
		}
	}
}

function setlineUnconfirmed(selectedRow) {
	document.getElementById("addBuyOrders").style["display"] = "none";
	lineqty = document.getElementById("lineqty" + selectedRow + "");
	qtyreceived = document.getElementById("qtyreceived" + selectedRow + "");
	selectedlRowStatus = document.getElementById("linestatus" + selectedRow
			+ "");
	selectedRowStatus = document.getElementById("linestatustext" + selectedRow
			+ "");

	if (selectedlRowStatus.value == "Unconfirmed"
			|| selectedlRowStatus.value == "Draft") {

	} else if ((lineqty.value * 1) <= (qtyreceived.value * 1)) {
		selectedRowStatus.innerHTML = "Closed";
	} else {
		selectedRowStatus.innerHTML = "Unconfirmed";
	}

	if ((selectedlRowStatus.value == "Confirmed")
			|| (selectedlRowStatus.value == "Closed")) {
		selectedlRowStatus.value = "Unconfirmed";

		rowlinenumber = document.getElementById("row" + selectedRow
				+ "linenumber");

		ammendmentobj = document
				.getElementById("ammendment" + selectedRow + "");
		ammendment = (ammendmentobj.value * 1) + 1;
		ammendmentobj.value = ammendment;

		linenumber = document.getElementById("linenumberammn" + selectedRow
				+ "");
		linenumber.innerHTML = rowlinenumber.value + "/" + ammendment;

		selectedlRowStatus = document.getElementById("linestatustext"
				+ selectedRow + "");
		selectedlRowStatus.innerHTML = "Unconfirmed";
	}

	linechange = document.getElementById("linechange" + selectedRow + "");
	linechange.value = "Yes";
}

function changeTotalPrice(selectedRow) {
	/*
	 * selectedRowStatus = document.getElementById("linestatus"+selectedRow+"");
	 * selectedRowStatus.value = "Unconfirmed";
	 * 
	 * selectedRowStatus =
	 * document.getElementById("linestatustext"+selectedRow+"");
	 * selectedRowStatus.innerHTML = "Unconfirmed";
	 */

	lineqty = document.getElementById("lineqty" + selectedRow + "");
	lineunitprice = document.getElementById("lineunitprice" + selectedRow + "").value;

	var lineTotalPrice = (lineqty.value * lineunitprice);

	currency = document.getElementById("currency");

	extpricecell = document.getElementById("extpricecell" + selectedRow + "");
	// cell9.parentNode.className = "Inbluer";
	// cell9.parentNode.setAttribute("align",'right');
	extpricecell.innerHTML = ("" + lineTotalPrice.toFixed(4) + " "
			+ currency.value + "");
	// cell9.innerHTML = (lineqty.value*lineunitprice.value) ;

	linetotalpriceo = document.getElementById("linetotalprice" + selectedRow
			+ "");
	linetotalpriceo.value = (lineTotalPrice.toFixed(4));

	changePoTotalPrice();
	setlineUnconfirmed(selectedRow);
}

function changeSuppTotalPrice(selectedRow) {
	supplierqty = document.getElementById("supplierqty" + selectedRow + "");
	supplierunitprice = document.getElementById("supplierunitprice"
			+ selectedRow + "").value;
	supplierextprice = document.getElementById("supplierextprice" + selectedRow
			+ "");

	if (supplierqty.value.trim().length > 0
			&& supplierunitprice.trim().length > 0) {
		var lineTotalPrice = (supplierqty.value * supplierunitprice.value);
		var currency = document.getElementById("currency");

		supplierextprice.value = ("" + lineTotalPrice.toFixed(4) + " "
				+ currency.value + "");

	} else {
		supplierextprice.value = "";
	}
	setlineUnconfirmed(selectedRow);
}

function changePoTotalPrice() {
	mytable = document.getElementById("linetable");

	var allTRs = mytable.getElementsByTagName("tr");
	var nowofRows = (allTRs.length) * 1;
	var totalpoPrice = 0;

	for (j = 0; j < nowofRows; j++) {
		lineqty = document.getElementById("lineqty" + (j + 1) + "");
		lineunitprice = document.getElementById("lineunitprice" + (j + 1) + "").value;

		lineunitprice *= $v('currencyExchangeRate');

		totalpoPrice += ((lineqty.value * 1) * (lineunitprice * 1));
	}

	totalpoPrice = totalpoPrice.toFixed(2);

	if (parseFloat(totalpoPrice) > 15000
			&& $v('allowPOFinancialConfirmaiton') != 'Y') {
		$('confirm').style.display = 'none';
	} else {
		// $('confirm').style.display = '';
		checkForCreditCartConf();
	}

	if ($v('isUSBuyer') == 'Y')
		totalpoPrice += ' USD';

	totalcostpo = document.getElementById("totalcost");
	totalcostpo.value = totalpoPrice;
}

function enDisableConfirm(doIt) {
	localConfirm = $('confirm');
	localConfirm.disabled = doIt;
}

function removeAllLines() {
	var chargeTable = document.getElementById("linetable");

	var allTRs = chargeTable.getElementsByTagName("tr");
	var nowofRows = (allTRs.length) * 1;

	for (j = 0; j < nowofRows; j++) {
		for (i = 0; i < chargeTable.childNodes.length; i++) {
			chargeTable.removeChild(chargeTable.childNodes[i]);
		}
		var divname1 = "divrow" + (j + 1);
		mydetailtable = document.getElementById(divname1);
		mydetailtable.removeNode(true);
	}
}

function checkflowChecks(numdetrow) {
	var allClear = 0;
	setlineUnconfirmed(numdetrow);

	var cocforflow = document.getElementById("cocforflow" + numdetrow + "");
	if (cocforflow.checked) {
		var noofspecs = document.getElementById("noofspecsdivrow" + numdetrow
				+ "" + numdetrow + "");
		if (noofspecs.value * 1 > 0) {
			for (j = 0; j < noofspecs.value; j++) {
				speccocchk = document.getElementById("speccocchk" + numdetrow
						+ "_" + j + "");
				if (speccocchk.checked) {
					allClear = 1;
				}
			}

			if (allClear < 1) {

			} else {
				alert("You cannot select this with a COC for a Specific Spec selected.");
				cocforflow.checked = false;
			}
		}
	}
}

function checkSpecChecks(numdetrow, specLine) {
	setlineUnconfirmed(numdetrow);
	/*
	 * speccocchk =
	 * document.getElementById("speccocchk"+numdetrow+"_"+specLine+""); if
	 * (speccocchk.checked) { var cocforflow=
	 * document.getElementById("cocforflow"+numdetrow+""); if
	 * (cocforflow.checked) { alert("You cannot select this with the Generic COC
	 * selected."); speccocchk.checked = false; } }
	 */
}

function checkflowflowChecks(numdetrow) {
	var allClear = 0;
	setlineUnconfirmed(numdetrow);

	var cocforflow = document.getElementById("coaforflow" + numdetrow + "");
	if (cocforflow.checked) {
		var noofspecs = document.getElementById("noofspecsdivrow" + numdetrow
				+ "" + numdetrow + "");
		if (noofspecs.value * 1 > 0) {
			for (j = 0; j < noofspecs.value; j++) {
				speccocchk = document.getElementById("speccoachk" + numdetrow
						+ "_" + j + "");
				if (speccocchk.checked) {
					allClear = 1;
				}
			}

			if (allClear < 1) {

			} else {
				alert("You cannot select this with a COA for a Specific Spec selected.");
				cocforflow.checked = false;
			}
		}
	}
}

function checkFlowChecks(numdetrow, specLine) {
	setlineUnconfirmed(numdetrow);
	/*
	 * speccocchk =
	 * document.getElementById("speccoachk"+numdetrow+"_"+specLine+""); if
	 * (speccocchk.checked) { var cocforflow=
	 * document.getElementById("coaforflow"+numdetrow+""); if
	 * (cocforflow.checked) { alert("You cannot select this with the Generic COA
	 * selected."); speccocchk.checked = false; } }
	 */
}

function checkAddChargeLines(numdetrow, datatransferstatusclosed) {
	var nooflinesinaddcharge = document.getElementById("nooflinesinaddcharge"
			+ numdetrow + "");
	for (j = 0; j < nooflinesinaddcharge.value; j++) {
		chargelinenumber = document.getElementById("chargelinenumber" + (j + 1)
				+ "");

		chargeAncnumber = document.getElementById("chargeAncnumber" + numdetrow
				+ "" + chargelinenumber.value + "");

		checkAncValue = chargeAncnumber.value;
		// alert(checkAncValue);
		
		addchargecheck = document.getElementById("addchargecheckdivrow"
				+ numdetrow + "" + numdetrow + "" + (j + 1) + "");
		
		if (checkAncValue == "Yes") {			
			addchargecheck.checked = true;			
		}

		if( datatransferstatusclosed != null && datatransferstatusclosed == 'Y')
			addchargecheck.disabled=true;
	}
}

function refreshlinecharges(numdetrow, setUnconfirmedFlag) {
	// alert("hi! "+numdetrow+"");
	mytable = document.getElementById("linetable");

	var allTRs = mytable.getElementsByTagName("tr");
	var str = "# of table Rows: " + allTRs.length + "\n";
	var nowofRows = (allTRs.length) * 1;
	var rownum = numdetrow;
	var divname = "divrow" + numdetrow;
	var chargeTable = document.getElementById(divname);

	for (i = 0; i < chargeTable.childNodes.length; i++) {
		chargeTable.removeChild(chargeTable.childNodes[i]);
	}

	newdivtablebody = document.createElement("TBODY");

	newdivcurrent_row = document.createElement("TR");
	newdivcurrent_row.className = "bluenocur";
	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.setAttribute("align", 'right');
	newdivcurrent_cell.setAttribute("colSpan", "2");
	newdivcurrent_cell.innerHTML = "<B>Last Confirmed:</B>";
	newdivcurrent_row.appendChild(newdivcurrent_cell);

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "lastconfirmedcell" + divname + rownum;
	newdivcurrent_cell.setAttribute("align", 'left');
	newdivcurrent_cell.setAttribute("colSpan", "2");
	newdivcurrent_cell.innerHTML = "";
	newdivcurrent_row.appendChild(newdivcurrent_cell);

	newdivtablebody.appendChild(newdivcurrent_row);

	newdivcurrent_row = document.createElement("TR");
	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.setAttribute("align", 'right');
	newdivcurrent_cell.setAttribute("colSpan", "2");
	newdivcurrent_cell.innerHTML = "<B>Desc:</B>";
	newdivcurrent_row.appendChild(newdivcurrent_cell);

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "itemdesc" + divname + rownum;
	newdivcurrent_cell.setAttribute("align", 'left');
	newdivcurrent_cell.setAttribute("colSpan", "2");
	newdivcurrent_cell.innerHTML = "";
	newdivcurrent_row.appendChild(newdivcurrent_cell);

	newdivtablebody.appendChild(newdivcurrent_row);

	newdivcurrent_row = document.createElement("TR");
	newdivcurrent_row.id = "secondarysupplierrow" + divname + rownum;
	newdivcurrent_row.className = "bluenocur";

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "secondarysuppliertitle" + divname + rownum;
	newdivcurrent_cell.setAttribute("align", 'right');
	newdivcurrent_cell.setAttribute("colSpan", "2");
	newdivcurrent_cell.innerHTML = "<B>Secondary Supplier:</B>";
	newdivcurrent_row.appendChild(newdivcurrent_cell);

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "secondarysuppliercell" + divname + rownum;
	newdivcurrent_cell.setAttribute("align", 'left');
	newdivcurrent_cell.setAttribute("colSpan", "2");
	newdivcurrent_cell.innerHTML = "";
	newdivcurrent_row.appendChild(newdivcurrent_cell);

	newdivtablebody.appendChild(newdivcurrent_row);

	newdivcurrent_row = document.createElement("TR");
	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.setAttribute("align", 'left');
	newdivcurrent_cell.setAttribute("colSpan", "2");
	newdivcurrent_cell.innerHTML = "<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id='reflinechgs' name='reflinechgs' OnClick=refreshAddCharges('"
			+ rownum + "') value=\"Refresh\">";
	newdivcurrent_row.appendChild(newdivcurrent_cell);

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.setAttribute("align", 'right');
	newdivcurrent_cell.setAttribute("colSpan", "2");
	newdivcurrent_cell.innerHTML = "<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id='showlinehistory' name='showlinehistory' OnClick=showPOLineHistory('"
			+ rownum + "') value=\"Line History\">";
	newdivcurrent_row.appendChild(newdivcurrent_cell);

	newdivtablebody.appendChild(newdivcurrent_row);

	// creating all po line details
	newdivcurrent_row = document.createElement("TR");
	newdivcurrent_row.id = "detailrow1" + divname + rownum;
	newdivcurrent_row.className = "bluenocur";
	newdivcurrent_cell = document.createElement("TH");
	newdivcurrent_cell.id = "titleline1" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '2%');
	newdivcurrent_cell.setAttribute("align", 'right');
	newdivcurrent_cell.innerHTML = "<B>Select</B>";
	newdivcurrent_row.appendChild(newdivcurrent_cell);

	newdivcurrent_cell = document.createElement("TH");
	newdivcurrent_cell.id = "titleline2" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '5%');
	newdivcurrent_cell.setAttribute("align", 'right');
	newdivcurrent_cell.innerHTML = "<B>Line</B>";
	newdivcurrent_row.appendChild(newdivcurrent_cell);

	newdivcurrent_cell = document.createElement("TH");
	newdivcurrent_cell.id = "titleline3" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '5%');
	newdivcurrent_cell.setAttribute("align", 'right');
	newdivcurrent_cell.innerHTML = "<B>Item</B>";
	newdivcurrent_row.appendChild(newdivcurrent_cell);

	newdivcurrent_cell = document.createElement("TH");
	newdivcurrent_cell.id = "titleline4" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '10%');
	newdivcurrent_cell.setAttribute("align", 'right');
	newdivcurrent_cell.innerHTML = "<B>Desc</B>";
	newdivcurrent_row.appendChild(newdivcurrent_cell);

	newdivtablebody.appendChild(newdivcurrent_row);

	var lineadded = 0;
	var Color1 = "white";

	for (j = 0; j < nowofRows; j++) {
		var itemID = document.getElementById("lineitemid" + (j + 1) + "");
		var itemType = document.getElementById("itemtype" + (j + 1) + "");
		var validitem = document.getElementById("validitem" + (j + 1) + "");
		var linestatus = document.getElementById("linestatus" + (j + 1) + "");
		var canassignaddcharge = document.getElementById("canassignaddcharge"
				+ (j + 1) + "");
		var lineQuantity = document.getElementById("lineqty" + (j + 1) + "");
		var dataTransferStatusClosed = document.getElementById("dataTransferStatusClosed" + (j + 1) + "");

		if ((itemID.value.length > 0) && (canassignaddcharge.value == "Y")
				&& (validitem.value == "Yes") && (linestatus.value != "Remove")) {
			lineadded++;

			if (lineadded % 2 == 0) {
				Color1 = "whitenocur";
			} else {
				Color1 = "bluenocur";
			}

			newdivcurrent_row = document.createElement("TR");
			newdivcurrent_row.id = "linechargerow" + divname + rownum + j;
			newdivcurrent_row.className = Color1;

			newdivcurrent_cell = document.createElement("TD");
			newdivcurrent_cell.id = "select" + divname + rownum + j;
			newdivcurrent_cell.setAttribute("width", '2%');
			newdivcurrent_cell.setAttribute("align", 'center');
			if (lineQuantity.value * 1 == 0) {
				innerHTMLStr = "<INPUT TYPE=\"checkbox\" value=\"Yes\" ID=\"addchargecheck"
						+ divname
						+ rownum
						+ lineadded
						+ "\" NAME=\"addchargecheck"
						+ divname
						+ rownum
						+ lineadded
						+ "\" onClick=\"setlineUnconfirmed('"
						+ rownum + "')\"" ;
				
				if (dataTransferStatusClosed.value == 'Y') 
					innerHTMLStr += " disabled";
				
				innerHTMLStr += ">";
			} else {
				innerHTMLStr = "<INPUT TYPE=\"checkbox\" value=\"Yes\" ID=\"addchargecheck"
						+ divname
						+ rownum
						+ lineadded
						+ "\" NAME=\"addchargecheck"
						+ divname
						+ rownum
						+ lineadded
						+ "\" onClick=\"setlineUnconfirmed('"
						+ rownum + "')\"" ;
				
				if (dataTransferStatusClosed.value == 'Y') 
					innerHTMLStr += " disabled";
					
				innerHTMLStr += ">";
			}
			newdivcurrent_cell.innerHTML = innerHTMLStr;
			newdivcurrent_row.appendChild(newdivcurrent_cell);

			newdivcurrent_cell = document.createElement("TD");
			newdivcurrent_cell.id = "line" + divname + rownum + j;
			newdivcurrent_cell.setAttribute("width", '5%');
			newdivcurrent_cell.setAttribute("align", 'center');
			newdivcurrent_cell.innerHTML = (j + 1)
					+ "<INPUT TYPE=\"hidden\" ID=\"chargeAncnumber"
					+ rownum
					+ ""
					+ (j + 1)
					+ "\" NAME=\"chargeAncnumber"
					+ rownum
					+ ""
					+ (j + 1)
					+ "\" VALUE=\"No\"><INPUT TYPE=\"hidden\" ID=\"chargelinenumber"
					+ lineadded + "\" NAME=\"chargelinenumber" + lineadded
					+ "\" VALUE=\"" + (j + 1) + "\">";
			newdivcurrent_row.appendChild(newdivcurrent_cell);

			newdivcurrent_cell = document.createElement("TD");
			newdivcurrent_cell.id = "line" + divname + rownum + j;
			newdivcurrent_cell.setAttribute("width", '5%');
			newdivcurrent_cell.setAttribute("align", 'center');
			newdivcurrent_cell.innerHTML = itemID.value;
			newdivcurrent_row.appendChild(newdivcurrent_cell);

			var para = document.getElementById(
					"itemdescdivrow" + (j + 1) + (j + 1) + "").cloneNode(true);
			newdivcurrent_row.appendChild(para);

			newdivtablebody.appendChild(newdivcurrent_row);
		}
	}
	newdivcurrent_row = document.createElement("TR");
	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.setAttribute("align", 'left');
	newdivcurrent_cell.setAttribute("colSpan", "4");
	newdivcurrent_cell.innerHTML = "<INPUT TYPE=\"hidden\" ID=\"nooflinesinaddcharge"
			+ rownum
			+ "\" NAME=\"nooflinesinaddcharge"
			+ rownum
			+ "\" VALUE=\"" + lineadded + "\">";
	newdivcurrent_row.appendChild(newdivcurrent_cell);
	newdivtablebody.appendChild(newdivcurrent_row);

	chargeTable.appendChild(newdivtablebody);

	if (setUnconfirmedFlag == "Yes") {
		setlineUnconfirmed(rownum);
	}
}

function originalversion() {
	var itemnotestable = document.getElementById("mainheadertable");
	itemnotestable.className = "moveup";

	var itemnotestable = document.getElementById("orderdetail");
	itemnotestable.className = "scroll_column75";

}

function printversion() {
	var itemnotestable = document.getElementById("mainheadertable");
	itemnotestable.className = "displaynone";

	var itemnotestable = document.getElementById("orderdetail");
	itemnotestable.className = "moveup";

}

function createViewContactLog(materialId, revisionDate) {
	var loc = "/tcmIS/catalog/contactlog.do?uAction=view&materialId="
			+ materialId;
	loc += "&revisionDate=" + URLEncode(revisionDate);
	openWinGeneric(loc, "contactlog", "800", "700", "yes", "50", "50", "20",
			"20", "no");
}

function stopShowingWait() {
	// stub to prevent error when contact log closes
}

function addLineCharge(entered, hideAddBuyOrders) {
	// alert("Add Line Charge");
	// getting reference to my Line Table
	mytable = document.getElementById("linetable");

	var allTRs = mytable.getElementsByTagName("tr");
	var str = "# of table Rows: " + allTRs.length + "\n";
	var nowofRows = (allTRs.length) * 1;
	var rownum = (allTRs.length) * 1 + 1;
	// alert(str);
	if (hideAddBuyOrders) {
		document.getElementById("addBuyOrders").style["display"] = "none";
	}

	// if (rownum > 1)
	{
		var Color = "white";
		if (rownum % 2 == 0) {
			Color = "blue";
		} else {
			Color = "white";
		}

		var lineNumberc = 0;

		if (allTRs.length == 0) {
			lineNumberc = rownum;
		} else {
			var lineNumber = document.getElementById("row" + allTRs.length
					+ "linenumber");
			lineNumberc = (Math.floor((lineNumber.value) * 1) + 1);
		}

		Nototallines = document.getElementById("totallines");
		Nototallines.value = rownum;

		// get the reference for the body
		var mybody = document.getElementById("mainpara");

		// creates an element whose tag name is TBODY
		var mytablebody = document.getElementById("totallinesbody");
		if (mytablebody == null) {
			mytablebody = document.createElement("TBODY");
			mytablebody.id = "totallinesbody";
		}
		// creating all cells
		// creates an element whose tag name is TR
		mycurrent_row = document.createElement("TR");
		mycurrent_row.onmouseup = function() {
			var materials = document.getElementById("materialId"
					+ (1 + this.rowIndex)).innerHTML.split(",");
			var menus = [];
			for (m in materials) {
				mrd = materials[m].split(" : ");
				menu = "text=" + materials[m]
						+ ";url=javascript:createViewContactLog('" + mrd[0]
						+ "','" + mrd[1] + "');";
				menus[menus.length] = menu;
			}
			if (menus.length > 0) {
				replaceMenu('contactLogMenu', menus);
				toggleContextMenu('contactLogMenu');
			}
		};
		mycurrent_row.id = "row" + rownum;
		mycurrent_row.className = Color;
		// mycurrent_row.attachEvent("onclick", catchevent);
		// mycurrent_row.attachEvent("mouseover", catchoverevent);

		// creates an element whose tag name is TD
		mycurrent_cell = document.createElement("TD");
		mycurrent_cell.id = "linenumberammn" + rownum;

		if (mycurrent_cell.attachEvent) {
			mycurrent_cell.attachEvent("onclick", catchevent);
			mycurrent_cell.attachEvent("onmouseover", catchoverevent);
		}
		if (mycurrent_cell.addEventListener) {
			mycurrent_cell.addEventListener("click", catchevent, false);
			mycurrent_cell.addEventListener("mouseover", catchoverevent, false);
		}
		mycurrent_cell.style.cursor = "hand";

		mycurrent_cell.setAttribute("width", '2%');
		mycurrent_cell.innerHTML = lineNumberc + "/0";
		// appends the cell TD into the row TR
		mycurrent_row.appendChild(mycurrent_cell);

		// creates an element whose tag name is TD
		mycurrent_cell = document.createElement("TD");
		mycurrent_cell.id = "ghscompliantcell" + rownum;
		mycurrent_cell.setAttribute("width", '5%');
		mycurrent_cell.innerHTML = "";
		// appends the cell TD into the row TR
		mycurrent_row.appendChild(mycurrent_cell);

		// creates an element whose tag name is TD
		mycurrent_cell = document.createElement("TD");
		mycurrent_cell.id = "cell2" + rownum;
		mycurrent_cell.setAttribute("width", '5%');
		mycurrent_cell.setAttribute("align", 'left');
		name = "lineitemid" + (rownum);
		mycurrent_cell.innerHTML = "<INPUT TYPE=\"hidden\" ID=\"itemtype"
				+ rownum
				+ "\" NAME=\"itemtype"
				+ rownum
				+ "\" VALUE=\"\"><INPUT TYPE=\"hidden\" ID=\"validitem"
				+ rownum
				+ "\" NAME=\"validitem"
				+ rownum
				+ "\" VALUE=\"No\"><input type=\"text\" size=\"5\" CLASS=\"INVINVALIDTEXT\" onChange=\"invalidateItem('"
				+ rownum
				+ "')\" name='"
				+ name
				+ "' id='"
				+ name
				+ "'><BUTTON id='button"
				+ name
				+ "' name='button"
				+ name
				+ "' OnClick=getChargeItemDetail('"
				+ rownum
				+ "') type=button><IMG src=\"/images/search_small.gif\" alt=\"Item ID\"></BUTTON>";
		// appends the cell TD into the row TR
		mycurrent_row.appendChild(mycurrent_cell);

		// creates an element whose tag name is TD
		mycurrent_cell = document.createElement("TD");
		mycurrent_cell.id = "itemtypecell" + rownum;
		mycurrent_cell.setAttribute("width", '2%');
		mycurrent_cell.innerHTML = "";
		// appends the cell TD into the row TR
		mycurrent_row.appendChild(mycurrent_cell);

		// creates an element whose tag name is TD
		mycurrent_cell = document.createElement("TD");
		mycurrent_cell.id = "cell5" + rownum;
		mycurrent_cell.setAttribute("width", '4%');
		name = "dateneeded" + (rownum);
		mycurrent_cell.innerHTML = "";
		// mycurrent_cell.innerHTML = "<input type=\"text\" size=\"6\"
		// CLASS=\"HEADER\" onChange=\"setlineUnconfirmed('"+rownum+"')\"
		// name='"+name+"' id='"+name+"'><a href=\"javascript: void(0);\"
		// onClick=\"return
		// getCalendar(document.purchaseorder."+name+");\">&diams;</a>";
		// appends the cell TD into the row TR
		mycurrent_row.appendChild(mycurrent_cell);

		// creates an element whose tag name is TD
		mycurrent_cell = document.createElement("TD");
		mycurrent_cell.id = "cell12" + rownum;
		mycurrent_cell.setAttribute("width", '6.5%');
		name = "projsuppshipdate" + (rownum);
		mycurrent_cell.innerHTML = "";
		// mycurrent_cell.innerHTML = "<input type=\"text\" size=\"6\"
		// CLASS=\"HEADER\" onChange=\"setlineUnconfirmed('"+rownum+"')\"
		// name='"+name+"' id='"+name+"'><a href=\"javascript: void(0);\"
		// onClick=\"return
		// getCalendar(document.purchaseorder."+name+");\">&diams;</a>";
		// appends the cell TD into the row TR
		mycurrent_row.appendChild(mycurrent_cell);

		// creates an element whose tag name is TD
		mycurrent_cell = document.createElement("TD");
		mycurrent_cell.id = "cell6" + rownum;
		mycurrent_cell.setAttribute("width", '7%');
		name = "datepromised" + (rownum);
		mycurrent_cell.innerHTML = "";
		// mycurrent_cell.innerHTML = "<input type=\"text\" size=\"6\"
		// CLASS=\"HEADER\" onChange=\"setlineUnconfirmed('"+rownum+"')\"
		// name='"+name+"' id='"+name+"'><a href=\"javascript: void(0);\"
		// onClick=\"return
		// getCalendar(document.purchaseorder."+name+");\">&diams;</a>";
		// appends the cell TD into the row TR
		mycurrent_row.appendChild(mycurrent_cell);

		// creates an element whose tag name is TD
		mycurrent_cell = document.createElement("TD");
		mycurrent_cell.id = "cell7" + rownum;
		mycurrent_cell.setAttribute("width", '4%');
		mycurrent_cell.setAttribute("align", 'right');
		name = "lineqty" + (rownum);
		mycurrent_cell.innerHTML = "<input type=\"text\" size=\"2\" CLASS=\"HEADER\" onFocus=\"enDisableConfirm(true);\" onBlur=\"enDisableConfirm(false);\" onChange=\"changeTotalPrice('"
				+ rownum + "')\" name='" + name + "' id='" + name + "'>";
		// appends the cell TD into the row TR
		mycurrent_row.appendChild(mycurrent_cell);

		// creates an element whose tag name is TD
		mycurrent_cell = document.createElement("TD");
		mycurrent_cell.id = "pakgingcell" + rownum;
		mycurrent_cell.setAttribute("width", '17%');
		mycurrent_cell.innerHTML = "";
		// appends the cell TD into the row TR
		mycurrent_row.appendChild(mycurrent_cell);

		// creates an element whose tag name is TD
		mycurrent_cell = document.createElement("TD");
		mycurrent_cell.id = "cell8" + rownum;
		mycurrent_cell.setAttribute("width", '5%');
		mycurrent_cell.setAttribute("align", 'right');
		name = "lineunitprice" + (rownum);
		mycurrent_cell.innerHTML = "<input type=\"text\" size=\"4\" CLASS=\"HEADER\" onFocus=\"enDisableConfirm(true);\" onBlur=\"enDisableConfirm(false);\" onChange=\"changeTotalPrice('"
				+ rownum + "')\" name='" + name + "' id='" + name + "'>";
		// appends the cell TD into the row TR
		mycurrent_row.appendChild(mycurrent_cell);

		// creates an element whose tag name is TD
		mycurrent_cell = document.createElement("TD");
		mycurrent_cell.id = "extpricecell" + rownum;
		mycurrent_cell.setAttribute("width", '7%');
		mycurrent_cell.setAttribute("align", 'right');
		mycurrent_cell.innerHTML = "";
		// appends the cell TD into the row TR
		mycurrent_row.appendChild(mycurrent_cell);

		// creates an element whose tag name is TD
		mycurrent_cell = document.createElement("TD");
		mycurrent_cell.id = "qtyreceivedcell" + rownum;
		mycurrent_cell.setAttribute("width", '3%');
		mycurrent_cell.innerHTML = "";
		// appends the cell TD into the row TR
		mycurrent_row.appendChild(mycurrent_cell);

		// creates an element whose tag name is TD
		mycurrent_cell = document.createElement("TD");
		mycurrent_cell.id = "qtyvouchered" + rownum;
		mycurrent_cell.setAttribute("width", '3%');
		mycurrent_cell.innerHTML = "";
		// appends the cell TD into the row TR
		mycurrent_row.appendChild(mycurrent_cell);

		// creates an element whose tag name is TD
		mycurrent_cell = document.createElement("TD");
		mycurrent_cell.id = "qtyreturned" + rownum;
		mycurrent_cell.setAttribute("width", '3%');
		// mycurrent_cell.innerHTML = "";
		mycurrent_cell.innerHTML = "<INPUT TYPE=\"hidden\" ID=\"row"
				+ rownum
				+ "row\" NAME=\"row"
				+ rownum
				+ "row\" VALUE=\""
				+ rownum
				+ "\"><INPUT TYPE=\"hidden\" ID=\"ammendmentcomments"
				+ rownum
				+ "\" NAME=\"ammendmentcomments"
				+ rownum
				+ "\" VALUE=\"\"><INPUT TYPE=\"hidden\" ID=\"ammendment"
				+ rownum
				+ "\" NAME=\"ammendment"
				+ rownum
				+ "\" VALUE=\"0\"><INPUT TYPE=\"hidden\" ID=\"originallinestatus"
				+ rownum
				+ "\" NAME=\"originallinestatus"
				+ rownum
				+ "\" VALUE=\"Draft\"><INPUT TYPE=\"hidden\" ID=\"row"
				+ rownum
				+ "linenumber\" NAME=\"row"
				+ rownum
				+ "linenumber\" VALUE=\""
				+ lineNumberc
				+ "\"><INPUT TYPE=\"hidden\" ID=\"linestatus"
				+ rownum
				+ "\" NAME=\"linestatus"
				+ rownum
				+ "\" VALUE=\"Draft\"><INPUT TYPE=\"hidden\" ID=\"linetotalprice"
				+ rownum
				+ "\" NAME=\"linetotalprice"
				+ rownum
				+ "\" VALUE=\"0\"><INPUT TYPE=\"hidden\" ID=\"qtyreceived"
				+ rownum
				+ "\" NAME=\"qtyreceived"
				+ rownum
				+ "\" VALUE=\"0\"><INPUT TYPE=\"hidden\" ID=\"lineArchived"
				+ rownum
				+ "\" NAME=\"lineArchived"
				+ rownum
				+ "\" VALUE=\"\"><INPUT TYPE=\"hidden\" ID=\"linechange"
				+ rownum
				+ "\" NAME=\"linechange"
				+ rownum
				+ "\" VALUE=\"Yes\"><INPUT TYPE=\"hidden\" ID=\"canassignaddcharge"
				+ rownum
				+ "\" NAME=\"canassignaddcharge"
				+ rownum
				+ "\" VALUE=\"N\"><INPUT TYPE=\"hidden\" ID=\"purchasingUnitsPerItem"
				+ rownum + "\" NAME=\"purchasingUnitsPerItem" + rownum
				+ "\" VALUE=\"\"><INPUT TYPE=\"hidden\" NAME=\"everConfirmed"
				+ rownum + "\" ID=\"everConfirmed" + rownum + "\" VALUE=\"\">";
		// appends the cell TD into the row TR
		mycurrent_row.appendChild(mycurrent_cell);

		// creates an element whose tag name is TD
		mycurrent_cell = document.createElement("TD");
		mycurrent_cell.id = "linestatustext" + rownum;
		mycurrent_cell.setAttribute("width", '4%');
		mycurrent_cell.innerHTML = "Draft";
		// appends the cell TD into the row TR
		mycurrent_row.appendChild(mycurrent_cell);

		mycurrent_cell = document.createElement("TD");
		mycurrent_cell.setAttribute("width", '0');
		mycurrent_cell.style.display = "none";
		mycurrent_cell.id = "materialId" + rownum;
		mycurrent_cell.innerHTML = "";
		mycurrent_row.appendChild(mycurrent_cell);

		/*
		 * // creates an element whose tag name is TD for ARCHIVED 12-04-02
		 * mycurrent_cell=document.createElement("TD"); mycurrent_cell.id =
		 * "cell11"+rownum; mycurrent_cell.setAttribute("width",'1'); name =
		 * "lineArchived" + (rownum); //mycurrent_cell.innerHTML = "<input
		 * type=\"text\" size=\"4\" CLASS=\"HEADER\" name='"+name+"'
		 * id='"+name+"'>"; mycurrent_cell.innerHTML = "<INPUT TYPE=\"hidden\"
		 * NAME='"+name+"' VALUE=\"\">"; // appends the cell TD into the row TR
		 * mycurrent_row.appendChild(mycurrent_cell);
		 */

		// appends the row TR into TBODY
		mytablebody.appendChild(mycurrent_row);

		// appends TBODY into TABLE
		mytable.appendChild(mytablebody);

		var divname = "divrow" + rownum;

		newdivtable = document.createElement("TABLE");
		newdivtable.setAttribute("width", '100%');
		newdivtable.setAttribute("CELLSPACING", '1');
		newdivtable.setAttribute("CELLPADDING", '3');
		newdivtable.id = divname;
		newdivtable.className = "displaynone";

		newdivtablebody = document.createElement("TBODY");

		newdivcurrent_row = document.createElement("TR");
		newdivcurrent_row.className = "bluenocur";
		newdivcurrent_cell = document.createElement("TD");
		newdivcurrent_cell.setAttribute("align", 'right');
		newdivcurrent_cell.setAttribute("colSpan", "2");
		newdivcurrent_cell.innerHTML = "<B>Last Confirmed:</B>";
		newdivcurrent_row.appendChild(newdivcurrent_cell);

		newdivcurrent_cell = document.createElement("TD");
		newdivcurrent_cell.id = "lastconfirmedcell" + divname + rownum;
		newdivcurrent_cell.setAttribute("align", 'left');
		newdivcurrent_cell.setAttribute("colSpan", "2");
		newdivcurrent_cell.innerHTML = "";
		newdivcurrent_row.appendChild(newdivcurrent_cell);

		newdivtablebody.appendChild(newdivcurrent_row);

		newdivcurrent_row = document.createElement("TR");

		newdivcurrent_cell = document.createElement("TD");
		newdivcurrent_cell.setAttribute("align", 'right');
		newdivcurrent_cell.setAttribute("colSpan", "2");
		newdivcurrent_cell.innerHTML = "<B>Desc:</B>";
		newdivcurrent_row.appendChild(newdivcurrent_cell);

		newdivcurrent_cell = document.createElement("TD");
		newdivcurrent_cell.id = "itemdesc" + divname + rownum;
		newdivcurrent_cell.setAttribute("align", 'left');
		newdivcurrent_cell.setAttribute("colSpan", "2");
		newdivcurrent_cell.innerHTML = "";
		newdivcurrent_row.appendChild(newdivcurrent_cell);

		newdivtablebody.appendChild(newdivcurrent_row);

		newdivcurrent_row = document.createElement("TR");
		newdivcurrent_row.id = "secondarysupplierrow" + divname + rownum;
		newdivcurrent_row.className = "bluenocur";

		newdivcurrent_cell = document.createElement("TD");
		newdivcurrent_cell.id = "secondarysuppliertitle" + divname + rownum;
		newdivcurrent_cell.setAttribute("align", 'right');
		newdivcurrent_cell.setAttribute("colSpan", "2");
		newdivcurrent_cell.innerHTML = "<B>Secondary Supplier:</B>";
		newdivcurrent_row.appendChild(newdivcurrent_cell);

		newdivcurrent_cell = document.createElement("TD");
		newdivcurrent_cell.id = "secondarysuppliercell" + divname + rownum;
		newdivcurrent_cell.setAttribute("align", 'left');
		newdivcurrent_cell.setAttribute("colSpan", "2");
		newdivcurrent_cell.innerHTML = "";
		newdivcurrent_row.appendChild(newdivcurrent_cell);

		newdivtablebody.appendChild(newdivcurrent_row);

		newdivcurrent_row = document.createElement("TR");
		newdivcurrent_cell = document.createElement("TD");
		newdivcurrent_cell.setAttribute("align", 'left');
		newdivcurrent_cell.setAttribute("colSpan", "2");
		newdivcurrent_cell.innerHTML = "<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id='reflinechgs' name='reflinechgs' OnClick=refreshAddCharges('"
				+ rownum + "') value=\"Refresh\">";
		newdivcurrent_row.appendChild(newdivcurrent_cell);

		newdivcurrent_cell = document.createElement("TD");
		newdivcurrent_cell.setAttribute("align", 'right');
		newdivcurrent_cell.setAttribute("colSpan", "2");
		newdivcurrent_cell.innerHTML = "<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id='showlinehistory' name='showlinehistory' OnClick=showPOLineHistory('"
				+ rownum + "') value=\"Line History\">";
		newdivcurrent_row.appendChild(newdivcurrent_cell);

		newdivtablebody.appendChild(newdivcurrent_row);

		// creating all po line details
		newdivcurrent_row = document.createElement("TR");
		newdivcurrent_row.id = "detailrow1" + divname + rownum;
		newdivcurrent_row.className = "bluenocur";
		newdivcurrent_cell = document.createElement("TH");
		newdivcurrent_cell.id = "titleline1" + divname + rownum;
		newdivcurrent_cell.setAttribute("width", '2%');
		newdivcurrent_cell.setAttribute("align", 'right');
		newdivcurrent_cell.innerHTML = "<B>Select</B>";
		newdivcurrent_row.appendChild(newdivcurrent_cell);

		newdivcurrent_cell = document.createElement("TH");
		newdivcurrent_cell.id = "titleline2" + divname + rownum;
		newdivcurrent_cell.setAttribute("width", '5%');
		newdivcurrent_cell.setAttribute("align", 'right');
		newdivcurrent_cell.innerHTML = "<B>Line</B>";
		newdivcurrent_row.appendChild(newdivcurrent_cell);

		newdivcurrent_cell = document.createElement("TH");
		newdivcurrent_cell.id = "titleline3" + divname + rownum;
		newdivcurrent_cell.setAttribute("width", '5%');
		newdivcurrent_cell.setAttribute("align", 'right');
		newdivcurrent_cell.innerHTML = "<B>Item</B>";
		newdivcurrent_row.appendChild(newdivcurrent_cell);

		newdivcurrent_cell = document.createElement("TH");
		newdivcurrent_cell.id = "titleline4" + divname + rownum;
		newdivcurrent_cell.setAttribute("width", '10%');
		newdivcurrent_cell.setAttribute("align", 'right');
		newdivcurrent_cell.innerHTML = "<B>Desc</B>";
		newdivcurrent_row.appendChild(newdivcurrent_cell);

		newdivtablebody.appendChild(newdivcurrent_row);

		var lineadded = 0;
		var Color1 = "white";

		for (j = 0; j < nowofRows; j++) {
			var itemID = document.getElementById("lineitemid" + (j + 1) + "");
			var itemType = document.getElementById("itemtype" + (j + 1) + "");
			var validitem = document.getElementById("validitem" + (j + 1) + "");
			var linestatus = document.getElementById("linestatus" + (j + 1)
					+ "");
			var canassignaddcharge = document
					.getElementById("canassignaddcharge" + (j + 1) + "");
			var lineQuantity = document
					.getElementById("lineqty" + (j + 1) + "");

			if ((itemID.value.length > 0) && (canassignaddcharge.value == "Y")
					&& (validitem.value == "Yes")
					&& (linestatus.value != "Remove")) {
				lineadded++;

				if (lineadded % 2 == 0) {
					Color1 = "whitenocur";
				} else {
					Color1 = "bluenocur";
				}

				newdivcurrent_row = document.createElement("TR");
				newdivcurrent_row.id = "linechargerow" + divname + rownum + j;
				newdivcurrent_row.className = Color1;

				newdivcurrent_cell = document.createElement("TD");
				newdivcurrent_cell.id = "select" + divname + rownum + j;
				newdivcurrent_cell.setAttribute("width", '2%');
				newdivcurrent_cell.setAttribute("align", 'center');
				if (lineQuantity.value * 1 == 0) {
					newdivcurrent_cell.innerHTML = "<INPUT TYPE=\"checkbox\" value=\"Yes\" NAME=\"addchargecheck"
							+ divname
							+ rownum
							+ lineadded
							+ "\" ID=\"addchargecheck"
							+ divname
							+ rownum
							+ lineadded
							+ "\" onClick=\"setlineUnconfirmed('"
							+ rownum + "')\">";
				} else {
					newdivcurrent_cell.innerHTML = "<INPUT TYPE=\"checkbox\" value=\"Yes\" NAME=\"addchargecheck"
							+ divname
							+ rownum
							+ lineadded
							+ "\" ID=\"addchargecheck"
							+ divname
							+ rownum
							+ lineadded
							+ "\" onClick=\"setlineUnconfirmed('"
							+ rownum + "')\">";
				}
				newdivcurrent_row.appendChild(newdivcurrent_cell);

				newdivcurrent_cell = document.createElement("TD");
				newdivcurrent_cell.id = "line" + divname + rownum + j;
				newdivcurrent_cell.setAttribute("width", '5%');
				newdivcurrent_cell.setAttribute("align", 'center');
				newdivcurrent_cell.innerHTML = (j + 1)
						+ "<INPUT TYPE=\"hidden\" NAME=\"chargeAncnumber"
						+ rownum
						+ ""
						+ (j + 1)
						+ "\" ID=\"chargeAncnumber"
						+ rownum
						+ ""
						+ (j + 1)
						+ "\" VALUE=\"No\"><INPUT TYPE=\"hidden\" ID=\"chargelinenumber"
						+ lineadded + "\" NAME=\"chargelinenumber" + lineadded
						+ "\" VALUE=\"" + (j + 1) + "\">";
				newdivcurrent_row.appendChild(newdivcurrent_cell);

				newdivcurrent_cell = document.createElement("TD");
				newdivcurrent_cell.id = "line" + divname + rownum + j;
				newdivcurrent_cell.setAttribute("width", '5%');
				newdivcurrent_cell.setAttribute("align", 'center');
				newdivcurrent_cell.innerHTML = itemID.value;
				newdivcurrent_row.appendChild(newdivcurrent_cell);

				var para = document.getElementById(
						"itemdescdivrow" + (j + 1) + (j + 1) + "").cloneNode(
						true);
				newdivcurrent_row.appendChild(para);

				newdivtablebody.appendChild(newdivcurrent_row);
			}
		}
		newdivtable.appendChild(newdivtablebody);
		// alert(lineadded);
		newdivcurrent_row = document.createElement("TR");
		newdivcurrent_cell = document.createElement("TD");
		newdivcurrent_cell.setAttribute("align", 'left');
		newdivcurrent_cell.setAttribute("colSpan", "4");
		newdivcurrent_cell.innerHTML = "<INPUT TYPE=\"hidden\" ID=\"nooflinesinaddcharge"
				+ rownum
				+ "\" NAME=\"nooflinesinaddcharge"
				+ rownum
				+ "\" VALUE=\"" + lineadded + "\">";
		newdivcurrent_row.appendChild(newdivcurrent_cell);
		newdivtablebody.appendChild(newdivcurrent_row);

		mybody.appendChild(newdivtable);

		// alert("Done Add Line Charge");
	}
}

function addLineItem(entered, hideAddBuyOrders) {
	// getting reference to my Line Table
	mytable = document.getElementById("linetable");

	var allTRs = mytable.getElementsByTagName("tr");
	var str = "# of table Rows: " + allTRs.length + "\n";
	var rownum = (allTRs.length) * 1 + 1;
	// alert(str);

	if (hideAddBuyOrders) {
		document.getElementById("addBuyOrders").style["display"] = "none";
	}

	var lineNumberc = 0;

	if (allTRs.length == 0) {
		lineNumberc = rownum;
	} else {
		var lineNumber = document.getElementById("row" + allTRs.length
				+ "linenumber");
		lineNumberc = (Math.floor((lineNumber.value) * 1) + 1);
	}

	var Color = "white";
	if (rownum % 2 == 0) {
		Color = "blue";
	} else {
		Color = "white";
	}

	Nototallines = document.getElementById("totallines");
	Nototallines.value = rownum;

	// get the reference for the body
	var mybody = document.getElementById("mainpara");

	// creates an element whose tag name is TBODY
	var mytablebody = document.getElementById("totallinesbody");
	if (mytablebody == null) {
		mytablebody = document.createElement("TBODY");
		mytablebody.id = "totallinesbody";
	}
	// creating all cells
	// creates an element whose tag name is TR
	mycurrent_row = document.createElement("TR");
	mycurrent_row.onmouseup = function() {
		var materials = document.getElementById("materialId"
				+ (this.rowIndex + 1)).innerHTML.split(",");
		var menus = [];
		for (m in materials) {
			mrd = materials[m].split(" : ");
			menu = "text=" + materials[m]
					+ ";url=javascript:createViewContactLog('" + mrd[0] + "','"
					+ mrd[1] + "');";
			menus[menus.length] = menu;
		}
		if (menus.length > 0) {
			replaceMenu('contactLogMenu', menus);
			toggleContextMenu('contactLogMenu');
		}
	};
	mycurrent_row.id = "row" + rownum;
	mycurrent_row.className = Color;
	// mycurrent_row.attachEvent("onclick", catchevent);
	// mycurrent_row.attachEvent("mouseover", catchoverevent);

	// creates an element whose tag name is TD
	mycurrent_cell = document.createElement("TD");
	mycurrent_cell.id = "linenumberammn" + rownum;
	if (mycurrent_cell.attachEvent) {
		mycurrent_cell.attachEvent("onclick", catchevent);
		mycurrent_cell.attachEvent("onmouseover", catchoverevent);
	} else {
		mycurrent_cell.addEventListener("click", catchevent, false);
		mycurrent_cell.addEventListener("mouseover", catchoverevent, false);
	}
	mycurrent_cell.style.cursor = "hand";

	mycurrent_cell.setAttribute("width", '2%');
	mycurrent_cell.innerHTML = lineNumberc + "/0";
	// appends the cell TD into the row TR
	mycurrent_row.appendChild(mycurrent_cell);

	// creates an element whose tag name is TD
	mycurrent_cell = document.createElement("TD");
	mycurrent_cell.id = "ghscompliantcell" + rownum;
	mycurrent_cell.setAttribute("width", '5%');
	mycurrent_cell.innerHTML = "";
	// appends the cell TD into the row TR
	mycurrent_row.appendChild(mycurrent_cell);

	// creates an element whose tag name is TD
	mycurrent_cell = document.createElement("TD");
	mycurrent_cell.id = "cell2" + rownum;
	mycurrent_cell.setAttribute("width", '5%');
	mycurrent_cell.setAttribute("align", 'left');
	name = "lineitemid" + (rownum);
	mycurrent_cell.innerHTML = "<input type=\"text\" size=\"5\" CLASS=\"INVINVALIDTEXT\" name='"
			+ name
			+ "' onChange=\"invalidateItem('"
			+ rownum
			+ "')\" id='"
			+ name
			+ "'><BUTTON id='button"
			+ name
			+ "' name='button"
			+ name
			+ "' OnClick=getItemDetail('"
			+ rownum
			+ "') type=button><IMG src=\"/images/search_small.gif\" alt=\"Item ID\"></BUTTON>";
	// appends the cell TD into the row TR
	mycurrent_row.appendChild(mycurrent_cell);

	// creates an element whose tag name is TD
	mycurrent_cell = document.createElement("TD");
	mycurrent_cell.id = "itemtypecell" + rownum;
	mycurrent_cell.setAttribute("width", '2%');
	mycurrent_cell.innerHTML = "";
	// appends the cell TD into the row TR
	mycurrent_row.appendChild(mycurrent_cell);

	// creates an element whose tag name is TD
	mycurrent_cell = document.createElement("TD");
	mycurrent_cell.id = "cell5" + rownum;
	mycurrent_cell.setAttribute("width", '4%');
	name = "dateneeded" + (rownum);
	mycurrent_cell.innerHTML = "<input type=\"text\" size=\"6\" CLASS=\"HEADER\" name='"
			+ name
			+ "' MAXLENGTH=\"10\" onChange=\"setlineUnconfirmed('"
			+ rownum
			+ "')\" id='"
			+ name
			+ "'><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"datelink"
			+ name
			+ "\" onClick=\"return getCalendar(document.purchaseorder."
			+ name + ");\">&diams;</a></FONT>";
	// appends the cell TD into the row TR
	mycurrent_row.appendChild(mycurrent_cell);

	// creates an element whose tag name is TD
	mycurrent_cell = document.createElement("TD");
	mycurrent_cell.id = "cell12" + rownum;
	mycurrent_cell.setAttribute("width", '6.5%');
	name = "projsuppshipdate" + (rownum);
	mycurrent_cell.innerHTML = "<input type=\"text\" size=\"6\" CLASS=\"HEADER\" name='"
			+ name
			+ "' MAXLENGTH=\"10\" onChange=\"setlineUnconfirmed('"
			+ rownum
			+ "')\" id='"
			+ name
			+ "'><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"datelink"
			+ name
			+ "\" onClick=\"return changeCalanderInfo(document.purchaseorder."
			+ name + ",'" + rownum + "');\">&diams;</a></FONT>";
	// appends the cell TD into the row TR
	mycurrent_row.appendChild(mycurrent_cell);

	// creates an element whose tag name is TD
	mycurrent_cell = document.createElement("TD");
	mycurrent_cell.id = "cell6" + rownum;
	mycurrent_cell.setAttribute("width", '7%');
	name = "datepromised" + (rownum);
	mycurrent_cell.innerHTML = "<input type=\"text\" size=\"6\" CLASS=\"HEADER\" name='"
			+ name
			+ "' MAXLENGTH=\"10\" onChange=\"setlineUnconfirmed('"
			+ rownum
			+ "')\" id='"
			+ name
			+ "'><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"datelink"
			+ name
			+ "\" onClick=\"return changeCalanderInfo(document.purchaseorder."
			+ name + ",'" + rownum + "');\">&diams;</a></FONT>";

	// appends the cell TD into the row TR
	mycurrent_row.appendChild(mycurrent_cell);

	// creates an element whose tag name is TD
	mycurrent_cell = document.createElement("TD");
	mycurrent_cell.id = "cell7" + rownum;
	mycurrent_cell.setAttribute("width", '4%');
	mycurrent_cell.setAttribute("align", 'right');
	name = "lineqty" + (rownum);
	mycurrent_cell.innerHTML = "<input type=\"text\" size=\"2\" CLASS=\"HEADER\" name='"
			+ name
			+ "' onFocus=\"enDisableConfirm(true);\" onBlur=\"enDisableConfirm(false);\"  onChange=\"changeTotalPrice('"
			+ rownum + "')\" id='" + name + "'>";
	// appends the cell TD into the row TR
	mycurrent_row.appendChild(mycurrent_cell);

	// creates an element whose tag name is TD
	mycurrent_cell = document.createElement("TD");
	mycurrent_cell.id = "pakgingcell" + rownum;
	mycurrent_cell.setAttribute("width", '17%');
	mycurrent_cell.innerHTML = "";
	// appends the cell TD into the row TR
	mycurrent_row.appendChild(mycurrent_cell);

	// creates an element whose tag name is TD
	mycurrent_cell = document.createElement("TD");
	mycurrent_cell.id = "cell8" + rownum;
	mycurrent_cell.setAttribute("width", '5%');
	mycurrent_cell.setAttribute("align", 'right');
	name = "lineunitprice" + (rownum);
	mycurrent_cell.innerHTML = "<input type=\"text\" size=\"4\" CLASS=\"HEADER\" name='"
			+ name
			+ "' onFocus=\"enDisableConfirm(true);\" onBlur=\"enDisableConfirm(false);\"  onChange=\"changeTotalPrice('"
			+ rownum + "')\" id='" + name + "'>";
	// appends the cell TD into the row TR
	mycurrent_row.appendChild(mycurrent_cell);

	// creates an element whose tag name is TD
	mycurrent_cell = document.createElement("TD");
	mycurrent_cell.id = "extpricecell" + rownum;
	mycurrent_cell.setAttribute("width", '7%');
	mycurrent_cell.setAttribute("align", 'right');
	// mycurrent_cell.setAttribute("align",'right');
	mycurrent_cell.innerHTML = "0";
	// appends the cell TD into the row TR
	mycurrent_row.appendChild(mycurrent_cell);

	// creates an element whose tag name is TD
	mycurrent_cell = document.createElement("TD");
	mycurrent_cell.id = "qtyreceivedcell" + rownum;
	mycurrent_cell.setAttribute("width", '3%');
	mycurrent_cell.innerHTML = "";
	// appends the cell TD into the row TR
	mycurrent_row.appendChild(mycurrent_cell);

	// creates an element whose tag name is TD
	mycurrent_cell = document.createElement("TD");
	mycurrent_cell.id = "qtyvouchered" + rownum;
	mycurrent_cell.setAttribute("width", '3%');
	mycurrent_cell.innerHTML = "";
	// appends the cell TD into the row TR
	mycurrent_row.appendChild(mycurrent_cell);

	// creates an element whose tag name is TD
	mycurrent_cell = document.createElement("TD");
	mycurrent_cell.id = "qtyreturned" + rownum;
	mycurrent_cell.setAttribute("width", '3%');
	mycurrent_cell.innerHTML = "";
	// appends the cell TD into the row TR
	mycurrent_row.appendChild(mycurrent_cell);

	// creates an element whose tag name is TD
	mycurrent_cell = document.createElement("TD");
	mycurrent_cell.id = "linestatustext" + rownum;
	mycurrent_cell.setAttribute("width", '4%');
	mycurrent_cell.innerHTML = "Draft";
	// appends the cell TD into the row TR
	mycurrent_row.appendChild(mycurrent_cell);

	mycurrent_cell = document.createElement("TD");
	mycurrent_cell.setAttribute("width", '0');
	mycurrent_cell.style.display = "none";
	mycurrent_cell.id = "materialId" + rownum;
	mycurrent_cell.innerHTML = "";
	mycurrent_row.appendChild(mycurrent_cell);

	/*
	 * // creates an element whose tag name is TD for ARCHIVED 12-04-02
	 * mycurrent_cell=document.createElement("TD"); mycurrent_cell.id =
	 * "cell11"+rownum; mycurrent_cell.setAttribute("width",'1'); name =
	 * "lineArchived" + (rownum); //mycurrent_cell.innerHTML = "<input
	 * type=\"text\" size=\"4\" CLASS=\"HEADER\" name='"+name+"'
	 * id='"+name+"'>"; mycurrent_cell.innerHTML = "<INPUT TYPE=\"hidden\"
	 * NAME='"+name+"' VALUE=\"\">"; // appends the cell TD into the row TR
	 * mycurrent_row.appendChild(mycurrent_cell);
	 */

	// appends the row TR into TBODY
	mytablebody.appendChild(mycurrent_row);

	// appends TBODY into TABLE
	mytable.appendChild(mytablebody);

	// creates an element whose tag name is DIV
	// newdiv = document.createElement("DIV");
	var divname = "divrow" + rownum;
	// newdiv.id = divname;
	// newdiv.className = "displaynone";

	// newdiv.innerHTML = "<B><U>PO Line Detail: </U></B>";

	newdivtable = document.createElement("TABLE");
	newdivtable.setAttribute("width", '100%');
	newdivtable.setAttribute("CELLSPACING", '1');
	newdivtable.setAttribute("CELLPADDING", '3');
	newdivtable.id = divname;
	newdivtable.className = "displaynone";

	newdivtablebody = document.createElement("TBODY");
	// creating all po line details
	newdivcurrent_row = document.createElement("TR");
	newdivcurrent_row.id = "lastconfirmedrow" + divname + rownum;
	newdivcurrent_row.className = "whitenocur";

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "lastconfirmedtitle" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '10%');
	newdivcurrent_cell.setAttribute("align", 'right');
	newdivcurrent_cell.innerHTML = "<B>Last Confirmed: </B>";
	newdivcurrent_row.appendChild(newdivcurrent_cell);

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "lastconfirmedcell" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '90%');
	newdivcurrent_cell.setAttribute("align", 'left');
	newdivcurrent_cell.innerHTML = "";
	newdivcurrent_row.appendChild(newdivcurrent_cell);

	newdivtablebody.appendChild(newdivcurrent_row);

	newdivcurrent_row = document.createElement("TR");
	newdivcurrent_row.id = "detailrow1" + divname + rownum;
	newdivcurrent_row.className = "bluenocur";
	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "titleline1" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '10%');
	newdivcurrent_cell.setAttribute("align", 'right');
	newdivcurrent_cell.innerHTML = "<B>Line:</B>";
	newdivcurrent_row.appendChild(newdivcurrent_cell);

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "row1detail" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '90%');
	newdivcurrent_cell.setAttribute("align", 'left');

	// var innHtmlline = rownum+"&nbsp;&nbsp;&nbsp;&nbsp;<B>MPN:</B><input
	// type=\"text\" size=\"12\" MAXLENGTH=\"30\" CLASS=\"HEADER\"
	// name='mpn"+rownum+"' id='mpn"+rownum+"'> ";
	var innHtmlline = "<INPUT CLASS=\"INVISIBLEHEADBLUE\" TYPE=\"text\" NAME=\"detaillinenumber"
			+ rownum
			+ "\" ID=\"detaillinenumber"
			+ rownum
			+ "\" value=\""
			+ lineNumberc
			+ "\" size=\"1\" readonly><B>MPN:</B><input type=\"text\" size=\"12\" CLASS=\"INVISIBLEHEADBLUE\"  name='mpn"
			+ rownum
			+ "' id='mpn"
			+ rownum
			+ "' MAXLENGTH=\"500\" onChange=\"setlineUnconfirmed('"
			+ rownum
			+ "')\" readonly> ";
	innHtmlline = innHtmlline
			+ "&nbsp;&nbsp;&nbsp;&nbsp;<B>Supplier PN:</B><input type=\"text\" size=\"12\" MAXLENGTH=\"30\" CLASS=\"HEADER\" name='supplierpn"
			+ rownum + "' id='supplierpn" + rownum
			+ "' onChange=\"setlineUnconfirmed('" + rownum + "')\"> ";
	// innHtmlline = innHtmlline +"&nbsp;&nbsp;&nbsp;&nbsp;<B>Customer
	// PO:</B><input type=\"text\" size=\"12\" CLASS=\"HEADER\"
	// name='customerpo"+rownum+"' id='customerpo"+rownum+"'> ";
	innHtmlline = innHtmlline
			+ "&nbsp;&nbsp;&nbsp;&nbsp;<B>DPAS:</B><input type=\"text\" size=\"1\" MAXLENGTH=\"4\" CLASS=\"HEADER\" name='dpas"
			+ rownum + "' id='dpas" + rownum
			+ "' onChange=\"setlineUnconfirmed('" + rownum + "')\"> ";
	innHtmlline = innHtmlline
			+ "&nbsp;&nbsp;&nbsp;&nbsp;<B>Shelf Life:</B><input type=\"text\" size=\"2\" CLASS=\"HEADER\" name='shelflife"
			+ rownum + "' id='shelflife" + rownum
			+ "' onChange=\"checkShelfLife(this.value,'" + rownum
			+ "')\">&nbsp;<B>%</B>";
	innHtmlline = innHtmlline + "&nbsp;&nbsp;<span name='shelflifebasis"
			+ rownum + "' id='shelflifebasis" + rownum + "'></span><br>";
	innHtmlline = innHtmlline
			+ "&nbsp;&nbsp;<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name='showlinehistory' id='showlinehistory' OnClick=showPOLineHistory('"
			+ rownum + "') style=\"width:90px;\" value=\"Line History\"> ";
	innHtmlline = innHtmlline
			+ "&nbsp;&nbsp;<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name='expediteNotes"
			+ rownum
			+ "' id='expediteNotes"
			+ rownum
			+ "' style=\"width:160px;display:none\" OnClick=enterExpediteNotes('"
			+ rownum + "') value=\"Enter Line Expedite Notes\"> ";
	innHtmlline = innHtmlline
			+ "&nbsp;&nbsp;<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name='showexpeditenoteshistory' id='showexpeditenoteshistory' OnClick=showexpeditenotes('"
			+ rownum
			+ "') style=\"width:150px;\" value=\"Line Expediting History\"> ";
	innHtmlline = innHtmlline
			+ "&nbsp;&nbsp;<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name='showitemexpeditenoteshistory' id='showitemexpeditenoteshistory' OnClick=showItemexpeditenotes('"
			+ rownum
			+ "') style=\"width:150px;\" value=\"Item Expediting History\"> ";
	innHtmlline = innHtmlline
			+ "&nbsp;&nbsp;<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name='showsourcinginfo' id='showsourcinginfo' OnClick=showSoucingInfo('"
			+ rownum
			+ "') style=\"width:150px;\" value=\"View/Edit Sourcing Info\"> ";
	innHtmlline = innHtmlline + "<input type=\"hidden\" name='slHidden"
			+ rownum + "' id='slHidden" + rownum + "'\">";
	newdivcurrent_cell.innerHTML = innHtmlline;
	newdivcurrent_row.appendChild(newdivcurrent_cell);
	newdivtablebody.appendChild(newdivcurrent_row);

	newdivcurrent_row = document.createElement("TR");
	newdivcurrent_row.id = "supplierdetail" + divname + rownum;
	newdivcurrent_row.className = "whitenocur";
	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "supplierdetailline" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '10%');
	newdivcurrent_cell.setAttribute("align", 'right');
	newdivcurrent_cell.innerHTML = "<B></B>";
	newdivcurrent_row.appendChild(newdivcurrent_cell);

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "supplier1detail" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '90%');
	newdivcurrent_cell.setAttribute("align", 'left');

	var innHtmlline = "<input type=\"hidden\" size=\"5\" CLASS=\"HEADER\" name='supplierqty"
			+ rownum
			+ "' id='supplierqty"
			+ rownum
			+ "' onChange=\"changeSuppTotalPrice('" + rownum + "')\"> ";
	innHtmlline = innHtmlline
			+ "<input type=\"hidden\" size=\"20\" MAXLENGTH=\"30\" CLASS=\"HEADER\" name='supplierpkg"
			+ rownum + "' id='supplierpkg" + rownum
			+ "' onChange=\"setlineUnconfirmed('" + rownum + "')\"> ";
	innHtmlline = innHtmlline
			+ "<input type=\"hidden\" size=\"5\" CLASS=\"HEADER\" name='supplierunitprice"
			+ rownum + "' id='supplierunitprice" + rownum
			+ "' onChange=\"changeSuppTotalPrice('" + rownum + "')\"> ";
	innHtmlline = innHtmlline
			+ "<input type=\"hidden\" size=\"8\" CLASS=\"INVISIBLEHEADWHITE\" name='supplierextprice"
			+ rownum + "' id='supplierextprice" + rownum + "' readonly> ";
	innHtmlline = innHtmlline
			+ "<B>Supplier Ship From Location:</B><INPUT TYPE=\"text\" CLASS=\"INVISIBLEHEADWHITE\" size=\"8\" name=\"shipFromLocationId"
			+ rownum + "\" id=\"shipFromLocationId" + rownum
			+ "\" VALUE=\"\" readonly>";
	innHtmlline = innHtmlline
			+ "&nbsp;&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name='enterSoleSource"
			+ rownum
			+ "' id='enterSoleSource"
			+ rownum
			+ "' style=\"width:150px;display:none\" OnClick=enterSoleSourcePriceQuote('"
			+ rownum + "') value=\"Sole Source Price Quote\"> ";

	newdivcurrent_cell.innerHTML = innHtmlline;
	newdivcurrent_row.appendChild(newdivcurrent_cell);
	newdivtablebody.appendChild(newdivcurrent_row);

	/*
	 * newdivcurrent_row=document.createElement("TR"); newdivcurrent_row.id =
	 * "detailrow2"+divname+rownum; newdivcurrent_row.className =
	 * "bluenocureed";
	 * 
	 * newdivcurrent_cell=document.createElement("TD"); newdivcurrent_cell.id =
	 * "titleline2"+divname+rownum;
	 * newdivcurrent_cell.setAttribute("width",'10%');
	 * newdivcurrent_cell.setAttribute("align",'right');
	 * newdivcurrent_cell.innerHTML = " ";
	 * newdivcurrent_row.appendChild(newdivcurrent_cell);
	 * 
	 * newdivcurrent_cell=document.createElement("TD"); newdivcurrent_cell.id =
	 * "row2detail"+divname+rownum;
	 * newdivcurrent_cell.setAttribute("width",'90%');
	 * newdivcurrent_cell.setAttribute("align",'left');
	 * newdivcurrent_cell.innerHTML =
	 * "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>DPAS:</B><input
	 * type=\"text\" size=\"5\" CLASS=\"HEADER\" name='dpas"+rownum+"'
	 * id='dpas"+rownum+"'> ";
	 * newdivcurrent_row.appendChild(newdivcurrent_cell);
	 * 
	 * newdivtablebody.appendChild(newdivcurrent_row);
	 */

	newdivcurrent_row = document.createElement("TR");
	newdivcurrent_row.id = "detailrow2" + divname + rownum;
	newdivcurrent_row.className = "bluenocur";

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "titleline2" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '10%');
	newdivcurrent_cell.setAttribute("align", 'right');
	newdivcurrent_cell.innerHTML = "<B>Desc: </B>";
	newdivcurrent_row.appendChild(newdivcurrent_cell);

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "itemdesc" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '90%');
	newdivcurrent_cell.setAttribute("align", 'left');
	newdivcurrent_cell.innerHTML = "";
	newdivcurrent_row.appendChild(newdivcurrent_cell);

	newdivtablebody.appendChild(newdivcurrent_row);

	newdivcurrent_row = document.createElement("TR");
	newdivcurrent_row.id = "secondarysupplierrow" + divname + rownum;
	newdivcurrent_row.className = "whitenocur";

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "secondarysuppliertitle" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '10%');
	newdivcurrent_cell.setAttribute("align", 'right');
	newdivcurrent_cell.innerHTML = "<B>Secondary Supplier:</B>";
	newdivcurrent_row.appendChild(newdivcurrent_cell);

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "secondarysuppliercell" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '90%');
	newdivcurrent_cell.setAttribute("align", 'left');
	newdivcurrent_cell.innerHTML = "";
	newdivcurrent_row.appendChild(newdivcurrent_cell);

	newdivtablebody.appendChild(newdivcurrent_row);

	newdivcurrent_row = document.createElement("TR");
	newdivcurrent_row.id = "detailrow2" + divname + rownum;
	newdivcurrent_row.className = "bluenocur";

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "itemverbytitle" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '10%');
	newdivcurrent_cell.setAttribute("align", 'right');
	newdivcurrent_cell.innerHTML = "<B>Verified By:</B>";
	newdivcurrent_row.appendChild(newdivcurrent_cell);

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "itemverifiedby" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '90%');
	newdivcurrent_cell.setAttribute("align", 'left');
	newdivcurrent_cell.innerHTML = "";
	newdivcurrent_row.appendChild(newdivcurrent_cell);

	newdivtablebody.appendChild(newdivcurrent_row);

	newdivcurrent_row = document.createElement("TR");
	newdivcurrent_row.id = "lookahead" + divname + rownum;
	newdivcurrent_row.className = "whitenocur";

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "lookaheadtitle" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '10%');
	newdivcurrent_cell.setAttribute("align", 'right');
	newdivcurrent_cell.setAttribute("vAlign", 'top');
	newdivcurrent_cell.innerHTML = "<B>Look Ahead: </B> <A HREF=\"javascript:getlookaheads('"
			+ rownum
			+ "')\" STYLE=\"color:#0000ff;cursor:hand\"><IMG ID=\"lookaheadimg"
			+ rownum + "\" src=\"/images/plus.jpg\" alt=\"Specs\"></A>";
	newdivcurrent_row.appendChild(newdivcurrent_cell);

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "lookaheaddetail" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '90%');
	newdivcurrent_cell.setAttribute("align", 'left');
	newdivcurrent_cell.innerHTML = "";
	newdivcurrent_row.appendChild(newdivcurrent_cell);
	newdivtablebody.appendChild(newdivcurrent_row);

	newdivcurrent_row = document.createElement("TR");
	newdivcurrent_row.id = "detailrow3" + divname + rownum;
	newdivcurrent_row.className = "bluenocur";

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "titleline3" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '10%');
	newdivcurrent_cell.setAttribute("align", 'right');
	newdivcurrent_cell.innerHTML = "<B>Line Notes: </B><BR><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name='getcjbjkb' id='getcjbjkb" + rownum + "' OnClick=getCannedComments('"
			+ rownum + "') value=\"Canned\">";
	newdivcurrent_row.appendChild(newdivcurrent_cell);

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "row3detail" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '90%');
	newdivcurrent_cell.setAttribute("align", 'left');
	newdivcurrent_cell.setAttribute("vAlign", 'top');
	innnoteHtmlline = "<TEXTAREA name=\"linenotes" + divname + rownum
			+ "\" id=\"linenotes" + divname + rownum
			+ "\" rows=\"5\" cols=\"130\" onChange=\"setlineUnconfirmed('"
			+ rownum + "')\"></TEXTAREA>";
	innnoteHtmlline = innnoteHtmlline + "&nbsp;&nbsp;&nbsp;&nbsp;";
	newdivcurrent_cell.innerHTML = innnoteHtmlline;

	newdivcurrent_row.appendChild(newdivcurrent_cell);
	newdivtablebody.appendChild(newdivcurrent_row);

	newdivcurrent_row = document.createElement("TR");
	newdivcurrent_row.id = "detailrowdelnotes" + divname + rownum;
	newdivcurrent_row.className = "whitenocur";

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "titleline3" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '10%');
	newdivcurrent_cell.setAttribute("align", 'right');
	newdivcurrent_cell.innerHTML = "<B>Delivery Notes: </B>";
	newdivcurrent_row.appendChild(newdivcurrent_cell);

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "row3detail" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '90%');
	newdivcurrent_cell.setAttribute("align", 'left');
	newdivcurrent_cell.setAttribute("vAlign", 'top');
	innnoteHtmlline = "<TEXTAREA name=\"deliverylinenotes" + divname + rownum
			+ "\" id=\"deliverylinenotes" + divname + rownum
			+ "\" rows=\"2\" cols=\"130\" onChange=\"setlineUnconfirmed('"
			+ rownum + "')\"></TEXTAREA>";
	innnoteHtmlline = innnoteHtmlline + "&nbsp;&nbsp;&nbsp;&nbsp;";
	newdivcurrent_cell.innerHTML = innnoteHtmlline;

	newdivcurrent_row.appendChild(newdivcurrent_cell);
	newdivtablebody.appendChild(newdivcurrent_row);

	newdivcurrent_row = document.createElement("TR");
	newdivcurrent_row.id = "detailrow4" + divname + rownum;
	newdivcurrent_row.className = "bluenocur";

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "titleline4" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '10%');
	newdivcurrent_cell.setAttribute("align", 'right');
	newdivcurrent_cell.setAttribute("vAlign", 'top');
	newdivcurrent_cell.innerHTML = "<B>Item Notes: </B> <A HREF=\"javascript:getItemNotes('"
			+ rownum
			+ "')\" STYLE=\"color:#0000ff;cursor:hand\"><IMG ID=\"itemnotesimage"
			+ rownum
			+ "\" src=\"/images/plus.jpg\" alt=\"Item Notes\"></A><BR><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id='additemnotes" + rownum + "' name='additemnotes' OnClick=addItemNotes('"
			+ rownum + "') value=\"Add\">";
	newdivcurrent_row.appendChild(newdivcurrent_cell);

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "row4detail" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '90%');
	newdivcurrent_cell.setAttribute("align", 'left');
	newdivcurrent_cell.innerHTML = "";
	// newdivcurrent_cell.innerHTML = "<TEXTAREA
	// name=\"itemnotes"+divname+rownum+"\" rows=\"2\"
	// cols=\"100\"></TEXTAREA>";
	newdivcurrent_row.appendChild(newdivcurrent_cell);
	newdivtablebody.appendChild(newdivcurrent_row);

	newdivcurrent_row = document.createElement("TR");
	newdivcurrent_row.id = "detailrow4" + divname + rownum;
	newdivcurrent_row.className = "whitenocur";

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "titleline4" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '10%');
	newdivcurrent_cell.setAttribute("align", 'right');
	newdivcurrent_cell.setAttribute("vAlign", 'top');
	newdivcurrent_cell.innerHTML = "<B>Part Notes: </B> <A HREF=\"javascript:getpartNotes('"
			+ rownum
			+ "')\" STYLE=\"color:#0000ff;cursor:hand\"><IMG ID=\"partnotesimage"
			+ rownum + "\" src=\"/images/plus.jpg\" alt=\"Part Notes\"></A>";
	newdivcurrent_row.appendChild(newdivcurrent_cell);

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "partnotes" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '90%');
	newdivcurrent_cell.setAttribute("align", 'left');
	newdivcurrent_cell.innerHTML = "";
	// newdivcurrent_cell.innerHTML = "<TEXTAREA
	// name=\"itemnotes"+divname+rownum+"\" rows=\"2\"
	// cols=\"100\"></TEXTAREA>";
	newdivcurrent_row.appendChild(newdivcurrent_cell);
	newdivtablebody.appendChild(newdivcurrent_row);

	newdivcurrent_row = document.createElement("TR");
	newdivcurrent_row.id = "detailrow5" + divname + rownum;
	newdivcurrent_row.className = "bluenocur";

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "titleline5" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '10%');
	newdivcurrent_cell.setAttribute("align", 'right');
	newdivcurrent_cell.setAttribute("vAlign", 'top');
	newdivcurrent_cell.innerHTML = "<B>Associated PRs: </B> <A HREF=\"javascript:getAssociatedPrs('"
			+ rownum
			+ "')\" STYLE=\"color:#0000ff;cursor:hand\"><IMG ID=\"associatedprimg"
			+ rownum
			+ "\" src=\"/images/plus.jpg\" alt=\"Associated PRS\"></A><BR><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name='editassociatePr00"
			+ rownum
			+ "' id='editassociatePr00"
			+ rownum
			+ "' OnClick=edditAssociatedPrs('" + rownum + "') value=\"Edit\">";
	newdivcurrent_row.appendChild(newdivcurrent_cell);

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "row5detail" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '90%');
	newdivcurrent_cell.setAttribute("align", 'left');
	newdivcurrent_cell.innerHTML = "";
	newdivcurrent_row.appendChild(newdivcurrent_cell);
	newdivtablebody.appendChild(newdivcurrent_row);

	newdivcurrent_row = document.createElement("TR");
	newdivcurrent_row.id = "detailrow6" + divname + rownum;
	newdivcurrent_row.className = "whitenocur";

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "titleline6" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '10%');
	newdivcurrent_cell.setAttribute("align", 'right');
	newdivcurrent_cell.setAttribute("vAlign", 'top');
	newdivcurrent_cell.innerHTML = "<B>Specs: </B> <A HREF=\"javascript:getspecs('"
			+ rownum
			+ "')\" STYLE=\"color:#0000ff;cursor:hand\"><IMG ID=\"specimg"
			+ rownum
			+ "\" src=\"/images/plus.jpg\" alt=\"Specs\"></A><BR><A HREF=\"javascript:showlegend()\" STYLE=\"color:#0000ff;cursor:hand\">Legend</A>";
	newdivcurrent_row.appendChild(newdivcurrent_cell);

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "row6detail" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '90%');
	newdivcurrent_cell.setAttribute("align", 'left');
	newdivcurrent_cell.innerHTML = "";
	newdivcurrent_row.appendChild(newdivcurrent_cell);
	newdivtablebody.appendChild(newdivcurrent_row);

	newdivcurrent_row = document.createElement("TR");
	newdivcurrent_row.id = "detailrow7" + divname + rownum;
	newdivcurrent_row.className = "bluenocur";

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "titleline7" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '10%');
	newdivcurrent_cell.setAttribute("align", 'right');
	newdivcurrent_cell.setAttribute("vAlign", 'top');
	newdivcurrent_cell.innerHTML = "<B>Flowdowns: </B> <A HREF=\"javascript:getflowdowns('"
			+ rownum
			+ "')\" STYLE=\"color:#0000ff;cursor:hand\"><IMG ID=\"flowdownimg"
			+ rownum + "\" src=\"/images/plus.jpg\" alt=\"Flow Down\"></A>";
	newdivcurrent_row.appendChild(newdivcurrent_cell);

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "row7detail" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '90%');
	newdivcurrent_cell.setAttribute("align", 'left');
	newdivcurrent_cell.innerHTML = "";
	newdivcurrent_row.appendChild(newdivcurrent_cell);
	newdivtablebody.appendChild(newdivcurrent_row);

	newdivcurrent_row = document.createElement("TR");
	newdivcurrent_row.id = "detailrowmsds" + divname + rownum;
	newdivcurrent_row.className = "whitenocur";

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "titlelinemsds" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '10%');
	newdivcurrent_cell.setAttribute("align", 'right');
	newdivcurrent_cell.setAttribute("vAlign", 'top');
	newdivcurrent_cell.innerHTML = "<B>MSDS: </B> <A HREF=\"javascript:getmsdsrevdate('"
			+ rownum
			+ "')\" STYLE=\"color:#0000ff;cursor:hand\"><IMG ID=\"msdsimg"
			+ rownum + "\" src=\"/images/plus.jpg\" alt=\"Specs\"></A>";
	newdivcurrent_row.appendChild(newdivcurrent_cell);

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "rowmsdsdetail" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '90%');
	newdivcurrent_cell.setAttribute("align", 'left');
	newdivcurrent_cell.innerHTML = "";
	newdivcurrent_row.appendChild(newdivcurrent_cell);
	newdivtablebody.appendChild(newdivcurrent_row);

	newdivcurrent_row = document.createElement("TR");
	newdivcurrent_row.id = "detailrow8" + divname + rownum;
	newdivcurrent_row.className = "bluenocur";

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "titleline8" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '10%');
	newdivcurrent_cell.setAttribute("align", 'right');
	newdivcurrent_cell.setAttribute("vAlign", 'top');
	newdivcurrent_cell.innerHTML = "<B>TCM Buys: </B> <A HREF=\"javascript:gettcmBuys('"
			+ rownum
			+ "')\" STYLE=\"color:#0000ff;cursor:hand\"><IMG ID=\"tcmbuyimg"
			+ rownum + "\" src=\"/images/plus.jpg\" alt=\"TCM Buys\"></A>";
	newdivcurrent_row.appendChild(newdivcurrent_cell);

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "row8detail" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '90%');
	newdivcurrent_cell.setAttribute("align", 'left');
	newdivcurrent_cell.innerHTML = "";
	newdivcurrent_row.appendChild(newdivcurrent_cell);
	newdivtablebody.appendChild(newdivcurrent_row);

	newdivcurrent_row = document.createElement("TR");
	newdivcurrent_row.id = "detailrow9" + divname + rownum;
	newdivcurrent_row.className = "whitenocur";

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "titleline9" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '10%');
	newdivcurrent_cell.setAttribute("align", 'right');
	newdivcurrent_cell.setAttribute("vAlign", 'top');
	newdivcurrent_cell.innerHTML = "<B>Client Buys: </B> <A HREF=\"javascript:getclientBuys('"
			+ rownum
			+ "')\" STYLE=\"color:#0000ff;cursor:hand\"><IMG ID=\"clientbuys"
			+ rownum + "\" src=\"/images/plus.jpg\" alt=\"Client Buys\"></A>";
	newdivcurrent_row.appendChild(newdivcurrent_cell);

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "row9detail" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '90%');
	newdivcurrent_cell.setAttribute("align", 'left');
	newdivcurrent_cell.innerHTML = "";
	newdivcurrent_row.appendChild(newdivcurrent_cell);
	newdivtablebody.appendChild(newdivcurrent_row);

	newdivcurrent_row = document.createElement("TR");
	newdivcurrent_row.id = "detailrow10" + divname + rownum;
	newdivcurrent_row.className = "bluenocur";

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "titleline10" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '10%');
	newdivcurrent_cell.setAttribute("align", 'right');
	newdivcurrent_cell.innerHTML = "";
	newdivcurrent_row.appendChild(newdivcurrent_cell);

	newdivcurrent_cell = document.createElement("TD");
	newdivcurrent_cell.id = "row10detail" + divname + rownum;
	newdivcurrent_cell.setAttribute("width", '90%');
	newdivcurrent_cell.setAttribute("align", 'left');

	var invisibleElements = "<INPUT TYPE=\"hidden\" ID=\"linestatus" + rownum
			+ "\" NAME=\"linestatus" + rownum + "\" VALUE=\"Draft\">";
	invisibleElements += "<INPUT TYPE=\"hidden\" ID=\"row" + rownum
			+ "linenumber\" NAME=\"row" + rownum + "linenumber\" VALUE=\""
			+ lineNumberc + "\">";
	invisibleElements += "<INPUT TYPE=\"hidden\" ID=\"row" + rownum
			+ "row\" NAME=\"row" + rownum + "row\" VALUE=\"" + rownum + "\">";
	invisibleElements += "<INPUT TYPE=\"hidden\" ID=\"row" + rownum
			+ "row\" NAME=\"row" + rownum + "row\" VALUE=\"No\">";
	invisibleElements += "<INPUT TYPE=\"hidden\" ID=\"partnotestatus" + rownum
			+ "\" NAME=\"partnotestatus" + rownum + "\" VALUE=\"No\">";
	invisibleElements += "<INPUT TYPE=\"hidden\" ID=\"linechange" + rownum
			+ "\" NAME=\"linechange" + rownum + "\" VALUE=\"Yes\">";
	invisibleElements += "<INPUT TYPE=\"hidden\" ID=\"originallinestatus"
			+ rownum + "\" NAME=\"originallinestatus" + rownum
			+ "\" VALUE=\"Draft\">";
	invisibleElements += "<INPUT TYPE=\"hidden\" ID=\"ammendment" + rownum
			+ "\" NAME=\"ammendment" + rownum + "\" VALUE=\"0\">";
	invisibleElements += "<INPUT TYPE=\"hidden\" ID=\"ammendmentcomments"
			+ rownum + "\" NAME=\"ammendmentcomments" + rownum
			+ "\" VALUE=\"\">";
	invisibleElements += "<INPUT TYPE=\"hidden\" ID=\"itemtype" + rownum
			+ "\" NAME=\"itemtype" + rownum + "\" VALUE=\"\">";
	invisibleElements += "<INPUT TYPE=\"hidden\" ID=\"associatedprsstatus"
			+ rownum + "\" NAME=\"associatedprsstatus" + rownum
			+ "\" VALUE=\"No\">";
	invisibleElements += "<INPUT TYPE=\"hidden\" ID=\"nofassociatedprs"
			+ rownum + "\" NAME=\"nofassociatedprs" + rownum
			+ "\" VALUE=\"0\">";
	invisibleElements += "<INPUT TYPE=\"hidden\" ID=\"totalofassociatedprs"
			+ rownum + "\" NAME=\"totalofassociatedprs" + rownum
			+ "\" VALUE=\"0\">";
	invisibleElements += "<INPUT TYPE=\"hidden\" ID=\"specstatus" + rownum
			+ "\" NAME=\"specstatus" + rownum + "\" VALUE=\"No\">";
	invisibleElements += "<INPUT TYPE=\"hidden\" ID=\"msdsstatus" + rownum
			+ "\" NAME=\"msdsstatus" + rownum + "\" VALUE=\"No\">";
	invisibleElements += "<INPUT TYPE=\"hidden\" ID=\"validspec" + rownum
			+ "\" NAME=\"validspec" + rownum + "\" VALUE=\"No\">";
	invisibleElements += "<INPUT TYPE=\"hidden\" ID=\"flowdownstatus" + rownum
			+ "\" NAME=\"flowdownstatus" + rownum + "\" VALUE=\"No\">";
	invisibleElements += "<INPUT TYPE=\"hidden\" ID=\"validflowdown" + rownum
			+ "\" NAME=\"validflowdown" + rownum + "\" VALUE=\"No\">";
	invisibleElements += "<INPUT TYPE=\"hidden\" ID=\"tcmbuystatus" + rownum
			+ "\" NAME=\"tcmbuystatus" + rownum + "\" VALUE=\"No\">";
	invisibleElements += "<INPUT TYPE=\"hidden\" ID=\"clientbuystatus" + rownum
			+ "\" NAME=\"clientbuystatus" + rownum + "\" VALUE=\"No\">";
	invisibleElements += "<INPUT TYPE=\"hidden\" ID=\"validitem" + rownum
			+ "\" NAME=\"validitem" + rownum + "\" VALUE=\"No\">";
	invisibleElements += "<INPUT TYPE=\"hidden\" ID=\"qtyreceived" + rownum
			+ "\" NAME=\"qtyreceived" + rownum + "\" VALUE=\"0\">";
	invisibleElements += "<INPUT TYPE=\"hidden\" ID=\"linetotalprice" + rownum
			+ "\" NAME=\"linetotalprice" + rownum + "\" VALUE=\"0\">";
	invisibleElements += "<INPUT TYPE=\"hidden\" ID=\"prshipto" + rownum
			+ "\" NAME=\"prshipto" + rownum + "\" VALUE=\"\">";
	invisibleElements += "<INPUT TYPE=\"hidden\" ID=\"noofspecs" + divname + ""
			+ rownum + "\" NAME=\"noofspecs" + divname + "" + rownum
			+ "\" VALUE=\"0\">";
	invisibleElements += "<INPUT TYPE=\"hidden\" ID=\"noofflowdowns" + divname
			+ "" + rownum + "\" NAME=\"noofflowdowns" + divname + "" + rownum
			+ "\" VALUE=\"0\">";
	invisibleElements += "<INPUT TYPE=\"hidden\" ID=\"nooflinesinaddcharge"
			+ rownum + "\" NAME=\"nooflinesinaddcharge" + rownum
			+ "\" VALUE=\"0\">";
	invisibleElements += "<INPUT TYPE=\"hidden\" ID=\"nooflookaheads" + rownum
			+ "\" NAME=\"nooflookaheads" + rownum + "\" VALUE=\"0\">";
	invisibleElements += "<INPUT TYPE=\"hidden\" ID=\"lookaheadstatus" + rownum
			+ "\" NAME=\"lookaheadstatus" + rownum + "\" VALUE=\"No\">";
	invisibleElements += "<INPUT TYPE=\"hidden\" ID=\"validlookahead" + rownum
			+ "\" NAME=\"validlookahead" + rownum + "\" VALUE=\"No\">";
	invisibleElements += "<INPUT TYPE=\"hidden\" ID=\"lineArchived" + rownum
			+ "\" NAME=\"lineArchived" + rownum + "\" VALUE=\"\">";
	invisibleElements += "<INPUT TYPE=\"hidden\" ID=\"lookaheadchanged"
			+ rownum + "\" NAME=\"lookaheadchanged" + rownum
			+ "\" VALUE=\"No\">";
	invisibleElements += "<INPUT TYPE=\"hidden\" ID=\"canassignaddcharge"
			+ rownum + "\" NAME=\"canassignaddcharge" + rownum
			+ "\" VALUE=\"N\">";
	invisibleElements += "<INPUT TYPE=\"hidden\" ID=\"purchasingUnitsPerItem"
			+ rownum + "\" NAME=\"purchasingUnitsPerItem" + rownum
			+ "\" VALUE=\"\">";
	invisibleElements += "<INPUT TYPE=\"hidden\" ID=\"buttonlineitemid"
			+ rownum + "\" NAME=\"buttonlineitemid" + rownum + "\" VALUE=\"\">";
	/*
	 * invisibleElements += "<INPUT TYPE=\"hidden\"
	 * NAME=\"shipFromLocationId"+rownum+"\" VALUE=\"\">";
	 */
	invisibleElements += "<INPUT TYPE=\"hidden\" ID=\"supplierSalesOrderNo"
			+ rownum + "\" NAME=\"supplierSalesOrderNo" + rownum
			+ "\" VALUE=\"\">";
	invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"everConfirmed" + rownum
			+ "\" ID=\"everConfirmed" + rownum + "\" VALUE=\"\">";

	invisibleElements += "<INPUT TYPE=\"hidden\" ID=\"itemnotestatus" + rownum
			+ "\" NAME=\"itemnotestatus" + rownum + "\" VALUE=\"No\">";
	invisibleElements += "<INPUT TYPE=\"hidden\" ID=\"dataTransferStatusClosed" + rownum
	+ "\" NAME=\"dataTransferStatusClosed" + rownum + "\" VALUE=\"0\">";
	newdivcurrent_cell.innerHTML = invisibleElements;
	newdivcurrent_row.appendChild(newdivcurrent_cell);
	newdivtablebody.appendChild(newdivcurrent_row);

	newdivtable.appendChild(newdivtablebody);

	mybody.appendChild(newdivtable);
}

function catchoverevent() {
	parentrow = event.srcElement.parentNode;
}

function catchevent() {
	parentrow = event.srcElement.parentNode;

	var url = /\d/;
	var result = selected_rowid.match(url);
	var numberline = result[0];

	selectedItemRow = document.getElementById("" + selected_rowid + "row");
	parentItemRow = document.getElementById("" + parentrow.id + "row");

	selectedRowStatus = document.getElementById("linestatus"
			+ selectedItemRow.value + "");
	parentRowStatus = document.getElementById("linestatus"
			+ parentItemRow.value + "");

	// alert(selectedRowStatus.value);
	// alert(parentRowStatus.value);

	var Color = "white";
	if (((selectedItemRow.value) * 1) % 2 == 0) {
		Color = "#E6E8FA";
	} else {
		Color = "#FFFFFF";
	}

	if (selectedRowStatus.value != "Remove") {
		selectedRow = document.getElementById(selected_rowid);
		selectedRow.style.backgroundColor = Color;
	}

	if (parentRowStatus.value != "Remove") {
		parentrow.style.backgroundColor = "#8a8aff";
	}

	// parentrow.style.display = "none"

	var divrowid = "div" + selected_rowid;
	var target1 = document.getElementById(divrowid);
	target1.style.display = "none";

	if (parentRowStatus.value != "Remove") {
		var divrowid = "div" + parentrow.id;
		var target1 = document.getElementById(divrowid);
		target1.style.display = "block";
	}

	selected_rowid = parentrow.id;
}

function removeline(name, entered, hideAddBuyOrders) {
	mytable = document.getElementById("linetable");
	var allTRs = mytable.getElementsByTagName("tr");
	var nowofRows = (allTRs.length) * 1;
	if (hideAddBuyOrders) {
		document.getElementById("addBuyOrders").style["display"] = "none";
	}

	if (nowofRows > 0) {

		// alert(selected_rowid);
		selectedItemRow = document.getElementById("" + selected_rowid + "row");
		// alert(selectedItemRow.value);
		selectedRowStatus = document.getElementById("linestatus"
				+ selectedItemRow.value + "");

		selectedOriginalRowStatus = document
				.getElementById("originallinestatus" + selectedItemRow.value
						+ "");

		var removedraftline = 0;
		if (selectedRowStatus.value == "Confirmed") {
			alert("A confirmed PO line cannot be deleted");
		}
		/*
		 * else if (selectedRowStatus.value == "Draft") { var chargeTable =
		 * document.getElementById("linetable"); var allTRs =
		 * chargeTable.getElementsByTagName("tr"); var nowofRows =
		 * (allTRs.length)*1;
		 * 
		 * for (j = 0; j<nowofRows; j++) { var selectedRowtoTest = "row" +
		 * (j+1); //alert(""+selectedRowtoTest+" "+selected_rowid+""); if
		 * (selectedRowtoTest == selected_rowid) { selectedRowStatus.value =
		 * "Remove"; removedraftline = j; selectedRow =
		 * document.getElementById(selected_rowid);
		 * selectedRow.style.backgroundColor = "#3b3b3b";
		 * selectedRow.style.display = "none"; selectedRow.className =
		 * "displaynone";
		 * 
		 * selectedRow =
		 * document.getElementById("divrow"+selectedItemRow.value+"");
		 * selectedRow.style.display = "none"; selectedRow.className =
		 * "displaynone";
		 * 
		 * selecteditemRowStatus =
		 * document.getElementById("linestatustext"+selectedItemRow.value+"");
		 * selecteditemRowStatus.innerHTML = "Remove";
		 *  }
		 * 
		 * if ( (removedraftline > 0) && (j>removedraftline)) {
		 * selectedRowStatus = document.getElementById("linestatus"+(j+1)+"");
		 * 
		 * var Color ="white"; if ( j%2==0 ) { Color ="#E6E8FA"; } else { Color
		 * ="#FFFFFF"; }
		 * 
		 * if (selectedRowStatus.value != "Remove") { selectedRow =
		 * document.getElementById("row"+(j+1)+"");
		 * selectedRow.style.backgroundColor = Color; } } } }
		 */
		else if (selectedRowStatus.value != "Remove") {
			selectedRowStatus.value = "Remove";

			selectedRow = document.getElementById(selected_rowid);
			selectedRow.style.backgroundColor = "#3b3b3b";

			selectedRow = document.getElementById("divrow"
					+ selectedItemRow.value + "");
			selectedRow.style.display = "none";
			selectedRow.className = "displaynone";

			selecteditemRowStatus = document.getElementById("linestatustext"
					+ selectedItemRow.value + "");
			selecteditemRowStatus.innerHTML = "Remove";

			confirmbutton = document.getElementById("confirm");
			alert("Hiding 3");
			confirmbutton.style.display = "none";
		}
	}
}

function unremoveline(entered) {
	mytable = document.getElementById("linetable");
	var allTRs = mytable.getElementsByTagName("tr");
	var nowofRows = (allTRs.length) * 1;

	if (nowofRows > 0) {
		// alert(selected_rowid);
		selectedItemRow = document.getElementById("" + selected_rowid + "row");
		// alert(selectedItemRow.value);
		selectedRowStatus = document.getElementById("linestatus"
				+ selectedItemRow.value + "");

		var removedraftline = 0;
		if (selectedRowStatus.value == "Remove") {
			selectedRowStatus.value = "Unconfirmed";

			/*
			 * var Color ="white"; if ( ((selectedItemRow.value)*1)%2==0 ) {
			 * Color ="#E6E8FA"; } else { Color ="#FFFFFF"; }
			 */

			selectedRow = document.getElementById(selected_rowid);
			selectedRow.style.backgroundColor = "#8a8aff";

			selectedRow = document.getElementById("divrow"
					+ selectedItemRow.value + "");
			selectedRow.style.display = "block";

			selecteditemRowStatus = document.getElementById("linestatustext"
					+ selectedItemRow.value + "");
			selecteditemRowStatus.innerHTML = "Unconfirmed";
		}
	}
}

var currShelfLife = "Error";
var currRow = -1;
function checkShelfLife(value, rownum) {
	setlineUnconfirmed(rownum);
	currShelfLife = value;
	currRow = rownum;

	nofassociatedprs = document.getElementById("nofassociatedprs" + rownum).value;
	var remainingShelfLifePercentCount = 0;
	for (j = 0; j < nofassociatedprs; j++) {
		remainingShelfLifePercent = document
				.getElementById("remainingShelfLifePercent" + rownum + (j + 1)).value;
		if (remainingShelfLifePercent != null
				&& remainingShelfLifePercent != '') {
			remainingShelfLifePercentCount++;
		}
	}

	if (value != null && value != '' && remainingShelfLifePercentCount > 0) {
		var url = "checkshelflife.do?po=" + document.getElementById('po').value
				+ "&shelfLife=" + value + "&itemId="
				+ document.getElementById('lineitemid' + rownum).value;

		var linestatus = document.getElementById("linestatus" + rownum + "").value;
		var ammendment = document.getElementById('ammendment' + rownum).value;

		if (linestatus == 'Confirmed'
				|| (linestatus == 'Unconfirmed' && parseInt(ammendment) > 0))
			url += "&uAction=Type1";
		else
			url += "&uAction=Type2";
		callToServer(url);
	}
}

function shelfLifeResults(results) {
	if (results.rows.length > 0) {
		var msg = "Remaining Shelf Life percent is less than what is required.\n\n";

		for (var i = 0; i < results.rows.length; i++)
			msg += "Please contact CSR (" + results.rows[i].data[0]
					+ ") for MR " + results.rows[i].data[1] + "-"
					+ results.rows[i].data[2]
					+ " to adjust the customer requirement.\n\n";

		msg += "Not doing so will create a duplicate buy order.\nDo you want to continue?";

		if (!confirm(msg)) {
			document.getElementById('shelflife' + currRow).value = document
					.getElementById('slHidden' + currRow).value;
		} else {
			document.getElementById('slHidden' + currRow).value = currShelfLife;
		}
		currShelfLife = "Error";
		currRow = -1;
	} else {
		document.getElementById('slHidden' + currRow).value = currShelfLife;
		currShelfLife = "Error";
		currRow = -1;
	}
}
