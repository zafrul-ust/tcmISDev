var topSectionHeight = 127;
var resultSectionHeight = 0;
var poLineSelectedRowId;
var associatedSelRowId;
var specSelRowId;
var msdsSelRowId;
var flowdownSelRowId;
var tcmbuysSelRowId;
var orderTakerChanged = "N";
var previousRowId = 1;
var previousItemType = 'Y';
var dhxWins = null;
var currShelfLife = '';
var headerInfoChanged = false;
var totalPoPriceInUSD = 0;
var initiatedAjaxForPoLine = false;
var monthNameArr = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
var monthArr = {Jan:0, Feb:1, Mar:2, Apr:3, May:4, Jun:5, Jul:6, Aug:7, Sep:8, Oct:9, Nov:10, Dec:11};
var newLineData = new Array();
var newLineId;
//since we check to save before actually submitting, positive integer value signals page is currently in process of checking and saving lines in preparation for submitPurchaseOrder
//Reason: this is to prevent calls to PurchaseOrderAction (call to save, call to reload, and call to confirm) overwrite each other. This has been observed to cause page not refreshing data after save
var delaySubmissionLine = -1;
//the action which submitPurchaseOrder is called on when the check is initiated
var delaySubmissionAction;

function submitSearchForm(calledFrom) {
	if($v("radianPo") != null && $v("radianPo") != ""){
		var action = document.getElementById("action");
		action.value = "searchlike";
		//if(calledFrom == 'populateRadianPo')
			document.genericForm.submit();
		showTransitWin("");		
	} else {
		var loc = "/tcmIS/supply/searchposmain.do?searchFromPopup=true";
	    openWinGeneric(loc,"showPos","800","450","yes","50","50","no");
	}
}

function reSearchPoNumber() {
	var loc = "/tcmIS/supply/searchposmain.do?searchFromPopup=true&po="+$v("radianPo");
    openWinGeneric(loc,"showPos","800","450","yes","50","50","no");
}

function populateRadianPo(selectedPo,selInvGrp) {
	$("radianPo").value = selectedPo;
	$("inventoryGroup").value = selInvGrp;
	$("inventoryGroupDisplay").innerHTML = selInvGrp;
	submitSearchForm('populateRadianPo');
}

function initializeLineItemGrid() {
    initGridWithGlobal(lineItemGridConfig);
    
    //need to set the headers AFTER initializing grid because underlying logic of the initialization procedure treats ALL
    //commas as delimiters for header names, even those meant for parameters in a function 
	var selectAllSize;
	if (!fontSizePref) {
		selectAllSize = 10;
	} else {
	 	switch (fontSizePref) {
		 	case "Smallest":
		 	case "Largest":
		 		selectAllSize = 8;
		 		break;
		 	case "Medium":
		 	case "Large":
		 		selectAllSize = 9;
		 		break;
		 	case "Small":
		 	default:
		 		selectAllSize = 10;
	 	}
	}
	var selectAllDateHTMLBoxFormat = "<input class=\"inputBox pointer\" readonly=\"true\" type=\"text\" id=\"{0}\" value=\"\" size=\"" + selectAllSize + "\"";
	selectAllDateHTMLBoxFormat += " onchange=\"changeAllDate(\'{1}\', this.value)\"";
	selectAllDateHTMLBoxFormat += " onclick=\"return getCalendar(document.getElementById(\'{0}\'), document.getElementById(\'blockBefore_{1}\'), document.getElementById(\'blockAfter_{1}\'), document.getElementById(\'blockBeforeExclude_{1}\'), document.getElementById(\'blockAfterExclude_{1}\'), document.getElementById(\'inDefinite_{1}\').value);\"/>";

	var selectAllNeedDateHTMLBox = selectAllDateHTMLBoxFormat.replace(/\{0\}/gi, "allNeedDate").replace(/\{1\}/gi, "needDate");
	var selectAllPromisedDateHTMLBox = selectAllDateHTMLBoxFormat.replace(/\{0\}/gi, "allPromisedDate").replace(/\{1\}/gi, "promisedDate");
	var selectAllProjectedDateHTMLBox = selectAllDateHTMLBoxFormat.replace(/\{0\}/gi, "allProjectedDate").replace(/\{1\}/gi, "projectedDate");
	
    var curLabel = lineItemGrid.getColumnLabel(lineItemGrid.getColIndexById("needDate"));
    lineItemGrid.setColumnLabel(lineItemGrid.getColIndexById("needDate"), curLabel + "<br/>" + selectAllNeedDateHTMLBox);
    curLabel = lineItemGrid.getColumnLabel(lineItemGrid.getColIndexById("promisedDate"));
    lineItemGrid.setColumnLabel(lineItemGrid.getColIndexById("promisedDate"), curLabel + "<br/>" + selectAllPromisedDateHTMLBox);
    curLabel = lineItemGrid.getColumnLabel(lineItemGrid.getColIndexById("projectedDate"));
    lineItemGrid.setColumnLabel(lineItemGrid.getColIndexById("projectedDate"), curLabel + "<br/>" + selectAllProjectedDateHTMLBox);
}

function initializeChargePoLineGrid() {
	//(chargesListGridConfig);
	initChargesListGrid();
}

function resultOnload(){
	if ($v("totalLines") > 0) {
		$("PoLineDetailViewBean").style["display"] = "";
		doInitGrid();
	}
	hideDiv();
	poResize();
}

function poResize()
{
   setWindowSizes();
   resizeGridHeight();
   setGridWidth();  
}

function showDiv() {
	  for(var i=5; i<=8; i++){
	   	$('row'+i+'').style.display="";
	  }	  
	  try {
	  	$('row'+9+'').style.display="";
	  } catch(ex) {}
	  
	  $('showGif').style.display="none";
	  $('hideGif').style.display="";	
	  topSectionHeight = 298;	 
	  poResize();
	}

function hideDiv() {
	  for(var i=5; i<=8; i++){
	   	$('row'+i+'').style.display="none";
	  }
	  try {
	  	$('row'+9+'').style.display="none";
	  } catch(ex) {}	  
	  $('showGif').style.display="";
	  $('hideGif').style.display="none";	  
	  topSectionHeight = 127;	
	  poResize();
}

function doInitGrid(){
	mygrid = new dhtmlXGridObject('PoLineDetailViewBean');	

	initGridWithConfig(mygrid,config,-1,true,true);
	if( typeof( jsonMainData ) != 'undefined' ) {		
		mygrid.parse(jsonMainData,"json");
	}
	
} 

function getNewPo() {
	var action = document.getElementById("action");
	action.value = "New";
	document.genericForm.submit();
	showTransitWin("");
}

function validateDataForSubmit(strAction) {
    var actvalue = strAction;
    document.getElementById("action").value = actvalue;

	var result ;
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
	var noofRows = (lineItemGrid.getRowsNum())*1;

	if (actvalue == "confirm")
	{
		if ( noofRows == 0)
		{
			alert("You cannot confirm a PO with no lines");
			result = false;
			return result;
		}
	}

	var supplierDefaultPaymentTerms = document.getElementById("supplierDefaultPaymentTerms");
	if (supplierDefaultPaymentTerms.value.trim().length == 0 && actvalue == "confirm")
	{
		if( !confirm("The supplier on this PO is inactive. Do you want to continue?") )
		{
			result = false;
			return result;
		}
	}
/*
	var currency  =  document.getElementById("currency");
	if ((currency.value == "None" || currency.value.trim().length == 0) && noofRows > 0 )
	{
		alert("Please Pick a Currency.");
		result = false;
		return result;
	}
*/
	var invenGrp  =  document.getElementById("inventoryGroup");
	if (invenGrp.value.trim().length == 0)
    {
		alert("Please pick a valid Ship To and Inventory Group.");        
		result = false;
		return result;
    }

	if (actvalue == "confirm" || actvalue == "printdraft") {
		finalMsgt = "Please enter valid values for: \n\n";
	}
	
	var consignedPo  =  document.getElementById("consignedPo");
	var hubName  =  document.getElementById("branchPlant");    
	var fob  =  document.getElementById("freightOnBoard").options[$('freightOnBoard').selectedIndex];
	var selectFob  =  document.getElementById("freightOnBoard");
	if (fob.value.trim().length == 0 || fob.value == "None")
	{
		finalMsgt = finalMsgt + " Trade Terms.\n" ;
		allClear = 1;
    }

	var paymentTerms =  document.getElementById("paymentTerms").options[$('paymentTerms').selectedIndex];
	var selectPaymentTerms =  document.getElementById("paymentTerms");
	if (paymentTerms.value.trim().length == 0)
	{
		finalMsgt = finalMsgt + " Payment Terms.\n" ;
		allClear = 1;
	}

	if ($v("supplier") == "")
	{
		finalMsgt = finalMsgt + " Supplier.\n" ;
		allClear = 1;
	}

	var shiptoid1  =  document.getElementById("shipToLocationId");
	if ($v("radianPo").length == 0) //&& !(bpovalue.value.length > 0) )
	{
		finalMsgt = finalMsgt + " PO.\n" ;
		//finalMsgt = finalMsgt + " Blanket Order.\n" ;
		allClear = 1;
	} else {
		if (shiptoid1.value == "")
		{
			finalMsgt = finalMsgt + " Ship To.\n" ;
			var shipToCompanyId  =  document.getElementById("shipToCompanyId");
			shipToCompanyId.value = "";
			allClear = 1;
		}

		var carrierID  =  document.getElementById("carrier");
		if (carrierID.value == "No")
		{
			finalMsgt = finalMsgt + " Shipping.\n" ;
			carrierID.value= "";
			$("carrierAccount").value = "";
			allClear = 1;
		}
	}

	var orderTaker  =  document.getElementById("supplierContactName");
	if (orderTaker.value == "")
	{
		finalMsgt = finalMsgt + " Order Taker.\n" ;
		orderTakerID = document.getElementById("supplierContactId");
		orderTakerID.value = "";
		allClear = 1;
	}

	shipTomsg = "The Ship To on the PO - " + shiptoid1.value + " is Different From Ship To on\n\n ";

	var nonintegerReceiving = document.getElementById("nonintegerReceiving");
	var noofRows = (lineItemGrid.getRowsNum())*1;

	for(j = 1; j <= noofRows; j++)
	{
		var validitem = gridCellValue(lineItemGrid,j,'validItem');;
		var linestatus = gridCellValue(lineItemGrid,j,"status");
		var originallinestatus = gridCellValue(lineItemGrid,j,"poLineStatus");
		var itemtype = gridCellValue(lineItemGrid,j,"type");
		var canassignaddcharge = gridCellValue(lineItemGrid,j,"canAssignAddCharge");
		var amendment = gridCellValue(lineItemGrid,j,"amendment");
		var linechange = gridCellValue(lineItemGrid,j,"lineChange");
		var lineNumber = gridCellValue(lineItemGrid,j,"poLineNumber"); 
		var currency  =  gridCellValue(lineItemGrid,j,"currency");
		      
		var LineMsg = "";
		var allClearforline = 0;

		if ( (linestatus != "Remove") )
		{
			if ( (linechange == "Y") || (originallinestatus =="Unconfirmed") || (canassignaddcharge != "Y") || (actvalue == "confirm"))
			{            
				if ( ( (linechange == "Y") && (amendment*1 > 0) ) || ( (amendment*1 > 0) && (linestatus != "Confirmed") && (actvalue == "confirm") ) )
				{
					var ammendmentcomments = gridCellValue(lineItemGrid,j,"ammendmentcomments");
					var amendcomment = prompt("Enter Comment for Changes to line # "+lineNumber+"" ,""+ammendmentcomments+"");
					lineItemGrid.cellById(j, lineItemGrid.getColIndexById('ammendmentcomments')).setValue(amendcomment);					
				}
				var lineitemid  =  gridCellValue(lineItemGrid,j,"itemId");
				var quantity = gridCellValue(lineItemGrid,j,"quantity");
				var lineArchived = gridCellValue(lineItemGrid,j,"lineArchived");

				if (quantity.trim().length > 0 && quantity*1 == 0 && lineitemid.trim().length == 0 && amendment*1 == 0)
				{				
					var selectedRowStatus1 = "Remove";
					lineItemGrid.cellById(j, lineItemGrid.getColIndexById('status')).setValue(selectedRowStatus1);
				}
				else if  (validitem == "No")
				{
					LineMsg = LineMsg + "       Valid Item.\n" ;
					lineItemGrid.cellById(j, lineItemGrid.getColIndexById('itemId')).setValue(""); 
					allClearforline = 1;
				}
				else if ( lineitemid.trim().length == 0 )
				{
					LineMsg = LineMsg + "       Valid Item.\n" ;
					allClearforline = 1;
				}
				
				if (currency == "" || currency == "None" || currency.trim().length == 0)
				{
					LineMsg = LineMsg + "       Valid Currency.\n" ;
					allClearforline = 1;
				}
				
				if ((nonintegerReceiving == "Y" && isFloat(quantity)) )
				{
				}
				else if ( !(isSignedInteger(quantity)) )
				{
					LineMsg = LineMsg + "       Valid Quantity.\n" ;
					allClearforline = 1;
				}
				else if ( quantity.trim().length == 0 )
				{
					LineMsg = LineMsg + "       Valid Quantity.\n" ;
					allClearforline = 1;
				}
				else if ( (originallinestatus == "Confirmed") && (quantity == 0) )
				{
					qtyisZero = 1;
					qtyisZeroMsg = qtyisZeroMsg + "The Quantity on Line "+lineNumber+" is 0.\n";
				}
				else if ( (lineitemid*1 == 430158) || (lineitemid*1 == 500743) )
				{
					if ( quantity > 0 )
					{
						LineMsg = LineMsg + "       Quantity cannot be positive.\n" ;
						allClearforline = 1;
					}
				}
				else if (quantity < 0 && lineArchived == "N")
				{
					LineMsg = LineMsg + "       Quantity cannot be negative.\n" ;
					allClearforline = 1;
				}

				var qtyreceived = gridCellValue(lineItemGrid,j,"quantityReceived");
				//alert(""+(quantity*1)+" Received "+(qtyreceived*1)+"");
				//alert((quantity*1) < (qtyreceived*1));
				if ( Math.abs((quantity*1)) < Math.abs((qtyreceived*1)) )
				{
					LineMsg = LineMsg + "       Cannot Have Qty Less Than Qty Received.\n" ;
					allClearforline = 1;
                }            

				var lineunitprice = gridCellValue(lineItemGrid,j,"unitPrice");
				if ( !(isFloat(lineunitprice)) )
				{
					if (canassignaddcharge != "Y" && lineunitprice < 0)
					{
					}
					else
					{
						LineMsg = LineMsg + "       Valid Unit Price.\n" ;
						allClearforline = 1;
					}
				}
				else if ( lineunitprice.trim().length == 0 )
				{
					LineMsg = LineMsg + "       Valid Unit Price.\n" ;
					allClearforline = 1;
				}

				//if ( (validbpo != "Yes") || (povalue.length > 0) )				
				if ($v("radianPo").length > 0 )
				{
					if (canassignaddcharge == "Y")
					{
						var shelflife =  gridCellValue(lineItemGrid,j,"shelfLife");
						if ($v("radianPo")*1 > 600000)
						{
							if ( !(isInteger(shelflife)) )
							{
								LineMsg = LineMsg + "       Valid Shelf Life.\n" ;
								allClearforline = 1;
							}
						}
						//01-16-03
						var datepromised = gridCellValue(lineItemGrid,j,"projectedDate");
						if (datepromised.length == 0 )                    
						{
							LineMsg = LineMsg + "       Valid Projected Delivery Date.\n" ;
							allClearforline = 1;
						}
						var projsuppshipdate = gridCellValue(lineItemGrid,j,"promisedDate");
						if ($v("radianPo")*1 > 608120)
						{
							if (projsuppshipdate.length == 0 )
							{
								LineMsg = LineMsg + "       Valid Promised Ship Date.\n" ;
								allClearforline = 1;
							}
						}
						if (projsuppshipdate.length == 11 && datepromised.length == 11)   //02-11-03
						{
							var projshipsplit = projsuppshipdate.split("-");
							var promsplit = datepromised.split("-");
							
							var projshipdate = new Date(projshipsplit[2], monthArr[projshipsplit[1]], projshipsplit[0]); //year,month,day
							var promdate = new Date(promsplit[2], monthArr[promsplit[1]], promsplit[0]); //year,month,day
							
							//alert("projshipdate   "+projshipdate+"   promdate   "+promdate+"");
							if ( projshipdate.getTime() > promdate.getTime() )
							{
								LineMsg = LineMsg + "       Promised Supplier Ship Date cannot be greater than Projected Delivery Date.\n" ;
								allClearforline = 1;
							}
						}

						var dateneeded = gridCellValue(lineItemGrid,j,"needDate");
						if (dateneeded.length == 0 )
						{
							LineMsg = LineMsg + "       Valid Date Needed.\n" ;
							allClearforline = 1;
						}

						var supplierquantity = gridCellValue(lineItemGrid,j,"supplierQty");
						if ( supplierquantity.length > 0 && !(isFloat(supplierquantity)) )
						{	                   
								LineMsg = LineMsg + "       Valid Supplier Quantity.\n" ;
								allClearforline = 1;	                   
						}

						var supplierunitprice = gridCellValue(lineItemGrid,j,"supplierUnitPrice");
						if ( supplierunitprice.length > 0 && !(isFloat(supplierunitprice)) )
						{
								LineMsg = LineMsg + "       Valid Supplier Unit Price.\n" ;
								allClearforline = 1;	                
						}

						var supplierextprice = gridCellValue(lineItemGrid,j,"supplierExtPrice");
						if (supplierextprice*1 > 0)
						{
							var linetotalprice = gridCellValue(lineItemGrid,j,"lineTotalPrice");
							if ( (linetotalprice*1).toFixed(2) != (supplierextprice*1).toFixed(2) )
							{
								LineMsg = LineMsg + "       Please Check Total Price of Line and the Supplier Price.\n" ;
								allClearforline = 1;
							}
						 }
						
						var nofassociatedprs = 0;
						if (window['associatedPrsNewGrid'])
							nofassociatedprs = window['associatedPrsNewGrid'].getRowsNum();
						var totalofassociatedprs = $v("totalItemQty");

						var itemAlreadyChecked = false ;
						if (itemIdschecked != null)
						{
							for (var i=0; i < itemIdschecked.length; i++)
							{
								oldCheckedItemId = itemIdschecked[i];
								if (oldCheckedItemId == lineitemid)
								{
									itemAlreadyChecked = true;
								}
							}
							if (!itemAlreadyChecked)
							{
								//alert("totalofassociatedprs   "+totalofassociatedprs+"");
								itemIdschecked[itemIdcheckCount] = lineitemid;
								itemIdcheckCount ++;
								var totalCheckItemQty = 0;
								for(itmCheck=1;itmCheck<=noofRows;itmCheck++)
								{
									var currentLineitemid  =  gridCellValue(lineItemGrid,itmCheck,"itemId"); //document.getElementById("lineitemid"+(itmCheck+1)+"");
									var currentLineQuantity = gridCellValue(lineItemGrid,itmCheck,"quantity"); //document.getElementById("lineqty"+(itmCheck+1)+"");
									if (currentLineitemid == lineitemid && currentLineQuantity.trim().length > 0)
									{
										//alert("line "+(itmCheck)*1000+" Qty "+currentLineQuantity);
										totalCheckItemQty = totalCheckItemQty + currentLineQuantity*1;
										//alert("totalCheckItemQty "+totalCheckItemQty);
									}
								}
								//alert("totalCheckItemQty   "+totalCheckItemQty+"   totalofassociatedprs   "+totalofassociatedprs+"");
								if (  totalCheckItemQty < totalofassociatedprs*1 )
								{
									assPrQty = 1;
									assPRMsg = assPRMsg + "The sum of all quantities for item "+lineitemid+" is "+totalCheckItemQty+" which is less than "+totalofassociatedprs+" which is the quantity associated with the Buy Orders.\n\n";
								}
							}
						}
						var prshipto = gridCellValue(lineItemGrid,j,"prShipTo"); //document.getElementById("prshipto"+(j+1)+"");
						if (prshipto.trim().length > 0)
						{
							if (prshipto == shiptoid1.value)
							{
							}
							else
							{
								//alert(""+ prshipto +"  "+ shiptoid1 +"");
								shipTomsg = shipTomsg + "Line " +(j)+": "+ prshipto +"\n";
								sameShipto = 1;
							}
						}
					}
					var lineSpecAllClear = 0;
					var lineSpecCheckMsg = "";
					if (window['specsGrid']) {							
						var noofspecs = window['specsGrid'].getRowsNum(); //document.getElementById("noofspecsdivrow"+(j+1)+""+(j+1)+"");						
						if ( noofspecs != null && noofspecs*1 > 0)
						{
							for (specRow = 1; specRow <= noofspecs; specRow++)
							{
								var polineNumberInSpecGrid = gridCellValue(window['specsGrid'],specRow,"poLineNumber"); 
								speccocchk = gridCellValue(window['specsGrid'],specRow,"savedCocStr"); //document.getElementById("speccocchk"+(j+1)+"_"+specRow+"");
								speccoachk = gridCellValue(window['specsGrid'],specRow,"savedCoaStr"); //document.getElementById("speccoachk"+(j+1)+"_"+specRow+"");
								specColor = gridCellValue(window['specsGrid'],specRow,"color"); //document.getElementById("specColor"+(j+1)+"_"+specRow+"").value;
								specCurrentCocRequirement = gridCellValue(window['specsGrid'],specRow,"currentCocRequirementStr"); //document.getElementById("specCurrentCocRequirement"+(j+1)+"_"+specRow+"").value;
								specCurrentCoaRequirement = gridCellValue(window['specsGrid'],specRow,"currentCoaRequirementStr"); //document.getElementById("specCurrentCoaRequirement"+(j+1)+"_"+specRow+"").value;
								//alert(speccocchk + " - " +  speccoachk + " - " + specColor + " - " +  specCurrentCocRequirement + " - " + specCurrentCoaRequirement);
								if ( polineNumberInSpecGrid == gridCellValue(lineItemGrid,j,"poLineNumber"))
								{
									if (specColor*1 == 1)
									{
										if (specCurrentCocRequirement == 'disabled|true' && !(speccocchk == 'true')) 
										{ 
											lineSpecAllClear = 1;
										}
		
										if (specCurrentCoaRequirement == 'disabled|true' && !(speccoachk == 'true')) 
										{
											 lineSpecAllClear = 1;
										}
									}	
								}
							}
						}
						if ( lineSpecAllClear >0 )
						{
							lineSpecCheckMsg = lineSpecCheckMsg + "       All the required specs are not checked on this line." ;
							specMsgCount = 1;
						}
					}
					if (window['addChargesList'] && window['chargesListGrid']) {
						
						var nooflinesinaddcharge = (chargesListGrid.getRowsNum())*1; //window['addChargesList'].getRowsNum(); //document.getElementById("nooflinesinaddcharge"+(j+1)+"");
						if ( (nooflinesinaddcharge*1 > 0) || (canassignaddcharge != "Y") )
						{
							var numofaddChargeChecks = 0;
							var numofMaLinesWithQty = 0;
							for(aj = 1; aj <= nooflinesinaddcharge; aj++)
							{
								//document.getElementById("addchargecheckdivrow"+(j+1)+""+(j+1)+""+(aj+1)+"");  //TODO: use the addcharge grid to get the checked values								 
								var addchargecheckdivrow = gridCellValue(window['chargesListGrid'],aj,"selectPo"); 
								if (gridCellValue(window['chargesListGrid'],aj,"poLine") ==  gridCellValue(lineItemGrid,j,"poLineNumber")) {
									if (addchargecheckdivrow == 'true')
									{
										numofaddChargeChecks ++;
								
										var chargelinenumber = gridCellValue(window['chargesListGrid'],aj,"poLineNumber");  //document.getElementById("chargelinenumber"+(aj+1)+"");
										var chargeLineQuantity = gridCellValue(lineItemGrid,chargelinenumber,"quantity"); //gridCellValue(window['chargesListGrid'],aj,"quantity"); //document.getElementById("lineqty"+chargelinenumber.value+"");						
										numofMaLinesWithQty++;						
										if (chargeLineQuantity*1 == 0 ) //&& addchargecheckdivrow.checked)
										{
											LineMsg = LineMsg + "       Cannot Assign Additional Charges to a Canceled Line.\n" ;
											LineMsg = LineMsg + "       You can either assign it to a different line or cancel this additional charge line.\n" ;
											allClearforline = 1;
										}
									}
								}								
							}
							if (numofaddChargeChecks == 0 && numofMaLinesWithQty > 0)
							{
								LineMsg = LineMsg + "       Must Assign Additional Charges.\n" ;
								allClearforline = 1;
							}
						}
					}
					if (quantity.trim().length > 0 && quantity == 0 && !(quantity*1 < qtyreceived*1) )  //TODO: CHECK this logic here. Already checked the quantity before
					{
						allClearforline = 0;
					}					
				}
			}
			
			if (allClearforline > 0)
			{
				finalMsgt = finalMsgt + " For Line "+lineNumber+":\n" ;
				finalMsgt = finalMsgt + LineMsg;
				allClear = 1;
			}			
			if (lineSpecAllClear > 0)
			{
				finalspecCheckMsg = finalspecCheckMsg + " For Line "+lineNumber+":\n" ;
				finalspecCheckMsg = finalspecCheckMsg + lineSpecCheckMsg;
			}
		}
	}

	if (actvalue == "confirm")
	{
		if (sameShipto > 0)
		{
			shipTomsg = shipTomsg + "\n\nClick Ok to Approve.\n";
			if (confirm(shipTomsg))
			{
			}
			else
			{
				result = false;
				return result;
			}
		}
		
		if (qtyisZero > 0)
		{
			qtyisZeroMsg = qtyisZeroMsg + "\nThese Line(s) Will be Closed and Cannot be Reopened.\n\nYou have to Disassociate any PR's Attached if you Want to Use them on a Different PO.\n\nDo you Want to Continue?\n\nClick Ok to Continue.\n";
			if (confirm(qtyisZeroMsg))
			{
			}
			else
			{
				result = false;
				return result;
			}
		}
	}

	if (specMsgCount > 0)
	{
		if (confirm(finalspecCheckMsg+"\n\nDo you want to continue?\n\nClick Ok to continue.\n"))
		{
		}
		else
		{
			result = false;
			return result;
		}
	}
	
	if (assPrQty > 0)
	{
		assPRMsg = assPRMsg + "This might lead to creation of new Buy Orders if you do not disassociate some buy orders or update quantities on this PO.\n\n Do you want to continue?\n\nClick Ok to continue.\n";
		if (confirm(assPRMsg))
		{
		}
		else
		{
			result = false;
			return result;
		}
	}

	if ( actvalue == "printdraft" )
	{
		if (allClear < 1)
		{
			result = true;
			return result;
		}
		else
		{
			alert(finalMsgt);
			result = false;
			return result;
		}
	} 
	else if (allClear < 1) 
	{
		result = true;
		hubName.disabled = false;
		selectPaymentTerms.disabled = false;
		selectFob.disabled = false;
		consignedPo.disabled = false;
		//currency.disabled = false;		
		return result;
	}
	else
	{
		if (actvalue == "confirm")
		{
			alert(finalMsgt);
			result = false;
		}
		else
		{
			hubName.disabled = false;
			selectPaymentTerms.disabled = false;
			selectFob.disabled = false;
			consignedPo.disabled = false;
			//currency.disabled = false;
			result = true;			
		}
		return result;
	}
}

function newPurchaseOrder() {

}

function submitPurchaseOrder(actionName) {
	try {
		//Background: the below two actions wants to update everything, including non-LineItemGrid grids, which is not possible since the data
		//is overwritten everytime a new row is selected from lineItemGrid. To overcome this, make sure before a new row is selected and
		//there is any change to non-LineItemGrid grids, user is prompted to choose whether to save.
		//In case users make changes to non-LineItemGrid grids and then perform the actions without changing to new row, save grids then proceed
		
		//If a line is changed while in state Closed or Confirmed, the amendment value is increased. If the line is not saved before calling confirm procedure,
		//the data non-LineItemGrids will be removed because they are associated with the old amendment.
		if ((actionName == 'resendWBuyPo' || actionName == 'confirm')) {
			//if this is not a call from loop, initiate loop
			if (delaySubmissionLine === -1) {
				delaySubmissionLine = 1;
				delaySubmissionAction = actionName;
			}
			
			//main body of the loop. It will call appropriate procedure to save data when needed
			var curRowId = delaySubmissionLine;
			//set status to Unconfirmed if currently selected line is in state Draft and save procedure is done successfully on the line
			//reason: this is done to allow Draft line be confirmed, since the confirm procedure ignores lines not being saved by save procedure, which ignore lines in state Draft
			//explain: 
			//-	if user discards change, the loop will continue and starting from the next iteration, delaySubmissionLine > poLineSelectedRowId + 1
			//-	if user accepts change and the save procedure fails, the loop will stop and will not get here
			//- if user accepts change and the save procedure succeeds, the loop will continue to here
			if (delaySubmissionLine === poLineSelectedRowId + 1 && gridCellValue(lineItemGrid, poLineSelectedRowId, 'status') === 'Draft') {
				setGridCellValue(lineItemGrid, poLineSelectedRowId, 'status', 'Unconfirmed');
				setGridCellValue(lineItemGrid, poLineSelectedRowId, 'poLineStatus', 'Unconfirmed');
			}
			for (; curRowId <= lineItemGrid.getRowsNum(); curRowId++)
				if (curRowId === poLineSelectedRowId && checkForChanges(curRowId)) {
					//the next loop will start on the next row
					delaySubmissionLine = curRowId + 1;
					savePoLineChanges(curRowId);
					return;
				//we call amendment update procedure on changed lines in lineItemGrid only if it has new amendment
				} else if (checkForChanges(curRowId, gridCellValue(lineItemGrid, curRowId, 'amendment') !== gridCellValue(lineItemGrid, curRowId, 'oldAmendment'))) {
					//the next loop will start on the next row
					delaySubmissionLine = curRowId + 1;
					//the page doesn't have data of this row's non-LineItemGrid grids, so we need to query and then update
					updateLineAmendment(curRowId);
					return;
				}
			
			delaySubmissionLine = -1;
		} else
			delaySubmissionLine = -1;
		
		//form is only submitted if the action is neither resendWBuyPo nor confirm, or if it is, the loop has already gone through all lines
		if (delaySubmissionLine === -1) {
			if (actionName == 'resendWBuyPo') {
				if (lineItemGrid.getRowsNum() == 0) {
					alert(messagesData.pleaseadd.replace("{0}", messagesData.line));
					return;
				}
			} else {
				poLineDateChanged();
				if (!validateDataForSubmit(actionName))
					return false;
			}
			
			$("action").value = actionName;
			//the server side doesn't retrieve data in non-LineItemGrid grids when this method is called
			lineItemGrid.parentFormOnSubmit();
			document.genericForm.submit();
			showTransitWin("");
		}
	} catch (e) {
		alert(messagesData.genericerror);
		delaySubmissionLine = -1;
	}
}

function getShipTo() {
    var po  =  document.getElementById("radianPo");

    if (po.value == "")
	{
    	alert("Please Choose a Valid PO.");
	} else {
		loc = "/tcmIS/supply/poshiptomain.do";
		openWinGeneric(loc,"ShipTo","600","500","yes","50","50");
	}
}

function shipToChanged(s) {
    var shipToContent = s.locationDesc ;
    var shiptoline1 = "";
    var shiptoline2 = "";
    var shiptoline3 = "";
    var shiptoline4 = "";
  	if( s.addressLine1.length > 0)
  		shiptoline1 += s.addressLine1+"<br/>";
  		
  	if( s.addressLine2.length > 0)
  		shiptoline2 += s.addressLine2+"<br/>";
  		
  	if( s.addressLine3.length > 0)
  		shiptoline3 += s.addressLine3+"<br/>";
  		
  	shiptoline4 += s.city+","+s.stateAbbrev+" "+s.zip+" "+s.countryAbbrev;
  	
  	$("shipToLine1").innerHTML = shiptoline1;
  	$("shipToLine2").innerHTML = shiptoline2;
  	$("shipToLine3").innerHTML = shiptoline3;
  	$("shipToLine4").innerHTML = shiptoline4;
  	$("shipToAddressLine1Display").value = s.addressLine1;
  	$("shipToAddressLine2Display").value = s.addressLine2;
  	$("shipToAddressLine3Display").value = s.addressLine3;
  	$("shipToAddressLine4Display").value = shiptoline4;
  	$("shipToCompanyIdDisplay").innerHTML = s.shipToCompanyId;
  	$("shipToLocationIdDisplay").value = s.locationId;
  	$("shipToCompanyId").value = s.shipToCompanyId;
  	$("shipToLocationId").value = s.locationId;
  	$("shipToFacilityId").value = s.facilityId;
  	
  	getInventoryGroup(s.locationId);
}

function getInventoryGroup(locationId) 
{
	showTransitWin("");
	loc = "/tcmIS/supply/selectinvngroup.do?Action=selectInventoryGrp&shipToLocationId=" + locationId;
	openWinGeneric(loc,"InventoryGroup","600","500","yes","50","50");
}


function inventoryGroupSelected(invnGrpInfo) {
	//invnGrpInfo also contains a currencyId value
	$("opsEntityId").value = invnGrpInfo.opsEntityId;
	$("inventoryGroup").value = invnGrpInfo.inventoryGroup;
	$("inventoryGroupDisplay").innerHTML = invnGrpInfo.inventoryGroup;
	headerChanged();
	return submitPurchaseOrder("saveNew");
}

function getSupplier() {
	var inventoryGroup = $v("inventoryGroup");
	var opsEntityId = $v("opsEntityId");
	
	if (poLineSelectedRowId == null || poLineSelectedRowId == "") {
		poLineSelectedRowId = 1;
	}
    var shipToId  =  document.getElementById("shipToLocationId");
	if ( shipToId.value == "" || shipToId.value == null)
	{
    	alert("Please choose a valid Ship To.");
	} else {
		var rowNumber = poLineSelectedRowId;	
		loc = "/tcmIS/distribution/entitysuppliersearchmain.do?valueElementId=supplier&statusFlag=true&displayElementId=supplierName&fromPurchaseOrderPage=Y&inventoryGroup="+inventoryGroup+"&opsEntityId="+opsEntityId+"&rowNumber="+rowNumber;
		openWinGeneric(loc,"Supplier","600","500","yes","50","50");	
	}
}

function supplierChanged(s) {
  	$("supplierName").value = s.returnSelectedValue;
  	$("supplierLine1").innerHTML = s.returnAddressLine1;
  	if (s.returnAddressLine2.length > 0) {
  		$("supplierLine2").innerHTML = s.returnAddressLine2;
  		$("supplierLine3").innerHTML = s.returnAddressLine3;
  	} else {
  		$("supplierLine2").innerHTML = s.returnAddressLine3;
  		$("supplierLine3").innerHTML = '';
  	}
	$("supplierLine4").innerHTML = '';
  	$("supplrAddressLine1Display").value = s.returnAddressLine1;
  	$("supplrAddressLine2Display").value = s.returnAddressLine2;
  	$("supplrAddressLine3Display").value = s.returnAddressLine3;
  	$("supplierPhoneDisplay").innerHTML = s.returnMfgPhone;
  	$("supplierPhone").value = s.returnMfgPhone;
  	$("supplier").value = s.returnSelectedId;
  	$("qualificationLevel").value = s.returnQualificationLevel;
  	$("qualificationLevelDisplay").innerHTML = s.returnQualificationLevel;
	$("paymentTerms").value = s.returnPaymentTerms;
	if (poLineSelectedRowId == null || poLineSelectedRowId == "") {
		poLineSelectedRowId = 1;
	}
	var inventoryGroup = $v("inventoryGroup");
	getCorrectPaymentTerms(poLineSelectedRowId,inventoryGroup,s.returnSelectedId);
	
	//clear out information about order taker and open popup window to choose new one
	$("supplierContactId").value = "";
  	$("supplierContactName").value = "";
  	$("supplierContactPhoneDisplay").innerHTML = "";
  	$("supplierContactPhone").value = "";
  	$("supplierContactFaxDisplay").innerHTML = "";
  	$("supplierContactFax").value = "";
  	$("emailDisplay").innerHTML = "";
  	$("email").value = "";
  	getOrderTaker();
}

function getCorrectPaymentTerms(rowNumber,inventoryGroup,returnSelectedId)
{
	 j$.ajax({
         type: "POST",
         url: "/tcmIS/supply/getpaymentterms.do",
	   	   data: {
		 	supplierId: returnSelectedId,
		 	inventoryGroup: inventoryGroup
		  },
         success: setPaymentTerms
       });	 
}

function setPaymentTerms(res) {
	var results = jQuery.parseJSON(res);
	if(results.Message != null && results.Message != undefined) 
		 alert(results.Message);
	else
	{
	  	var r = results.paymentTerms;
		if (r != null) {
			$("supplierDefaultPaymentTerms").value = r;
			$("paymentTerms").value = r;
		}
	}
}

function getSecondarySupplier()
{
	if (poLineSelectedRowId == null || poLineSelectedRowId == "") {
		poLineSelectedRowId = 1;
	}

    var loc = "/tcmIS/distribution/entitysuppliersearchmain.do?secondarySupplier=Y&valueElementId=supplier&statusFlag=true&fromPurchaseOrderPage=Y&displayElementId=supplierName";
	var secSuppId  =  gridCellValue(lineItemGrid, poLineSelectedRowId,'supplier');
    var invenGrp  =  $v("inventoryGroup");
    var opsEntityId  =  $v("opsEntityId");
    
    if (!isEmptyString(secSuppId))
    	loc += "&autoSearch=y&supplierName="+secSuppId;
    loc = loc + "&rowNumber="+poLineSelectedRowId;
    loc = loc + "&inventoryGroup="+invenGrp;
    loc = loc + "&opsEntityId=" + opsEntityId;
    openWinGeneric(loc,"SecondarySupplierID","800","600","yes","50","50");
}

function secondarySupplierChanged (s) {
	if (poLineSelectedRowId == null || poLineSelectedRowId == "") {
		poLineSelectedRowId = 1;
	}
	
	var secondarySupplierElement = null;
	if ($('addChargesList').style.display == "")
		secondarySupplierElement = $("addChargeSecondarySupplier");
	else
		secondarySupplierElement = $("materialSecondarySupplier");
	
	if (gridCellValue(lineItemGrid, poLineSelectedRowId,'supplier') != s.returnSelectedId) {
		secondarySupplierElement.innerHTML = s.returnSelectedValue +" "+ s.returnAddressLine1 +" "+ s.returnAddressLine2 +" "+ s.returnAddressLine3;
	  	lineItemGrid.cellById(poLineSelectedRowId, lineItemGrid.getColIndexById("supplier")).setValue(s.returnSelectedId);
		setLineUnconfirmed(poLineSelectedRowId);
	}
}

function invalidateOrderTaker() {
	orderTakerChanged = "Y";
	headerChanged();
}

function getOrderTaker() {
	if($v("supplier").length == 0) {
		alert("Choose a supplier before choosing the contact.");
		return false;
	}
	invalidateOrderTaker();
    var loc = "/tcmIS/supply/posuppliercontact.do?action=search&supplier="+$v("supplier")+"&displayElementId=supplierContactName&valueElementId=supplierContactId&fromSupplierPriceList=N";
    openWinGeneric(loc,"shoSupplierContacts","800","450","yes","50","50","no");
}

function setOrderTakerChanged(id, name, phone, fax, email ) {	
  	$("supplierContactId").value = id;
  	$("supplierContactName").value = name;
  	$("supplierContactPhoneDisplay").innerHTML = phone;
  	$("supplierContactPhone").value = phone;
  	$("supplierContactFaxDisplay").innerHTML = fax;
  	$("supplierContactFax").value = fax;
  	$("emailDisplay").innerHTML = "<a href=\"mailto:" + email + "\">" + email + "</a>";
  	$("email").value = email;
}

/*
function getShipping() {
	loc = "/tcmIS/purchase/pocarrier?carrierID=VT&inventoryGroup=Jefferson%20North";
	openWinGeneric(loc,"Shipping","600","500","yes","50","50");
}
*/
//TODO: finish here once further info is offered
function shippingChanged(s) {
    var shipToContent = s.locationDesc+"<br/>";
  
  	if( s.addressLine1.length > 0)
  		shipToContent += s.addressLine1+"<br/>";
  		
  	if( s.addressLine2.length > 0)
  		shipToContent += s.addressLine2+"<br/>";
  		
  	if( s.addressLine3.length > 0)
  		shipToContent += s.addressLine3+"<br/>";
  		
  	shipToContent += s.city+","+s.stateAbbrev+" "+s.zip+" "+s.countryAbbrev+"<br/>";
  	//$("shippingSpan").innerHTML = shipToContent;

}


function showEmailNotes()
{
    var povalue  =  document.getElementById("radianPo");
	 
    if (povalue.value.trim().length > 0) {
        loc = "/tcmIS/supply/poemailmain.do?phase=init&pONumber="+povalue.value;
  	    openWinGeneric(loc,"ShowEmailNotes","700","620","yes","50","50");
    }
}

function showPoNotes()
{
    var povalue  =  document.getElementById("radianPo");

	if (povalue.value.trim().length > 0) {
        loc = "/tcmIS/supply/ponotesmain.do?phase=init&pONumber="+povalue.value;
  	    openWinGeneric(loc,"ShowPONotes","700","620","yes","50","50");
    }	
}

function getCarrier()
{
    var inventoryGroup =  $v("inventoryGroup");
    var shipToLocationId = $v("shipToLocationId");

    if ( !isEmptyString(shipToLocationId) && !isEmptyString(inventoryGroup) )
    {
    	loc = "/tcmIS/supply/pocarriermain.do?source=purchaseOrderPage&displayArea=carrierName&valueElementId=carrier&callbackfunction=carrierChanged";
	    loc += "&inventoryGroup=" + inventoryGroup;
	    loc += "&searchArgument=" + $v("carrierDisplay");

        openWinGeneric(loc,"carrierID","600","500","yes","50","50","20","20","no");
    }
    else if (isEmptyString(shipToLocationId))
    {
        alert(messagesData.selectavalid.replace("{0}", messagesData.shipto));
    }
    else if (isEmptyString(inventoryGroup))
    {
        alert(messagesData.selectavalid.replace("{0}", messagesData.inventorygroup));
    }
}

function carrierChanged(returnSelectedCarrierCode,returnSelectedCarrierName,returnSelectedAccount,returnSelectedCompanyHub, callbackparam) {
	$("carrier").value = returnSelectedCarrierCode;
	$("carrierDisplay").value = returnSelectedCarrierCode;
	$("carrierAccount").value = returnSelectedAccount;
	$("carrierCompanyId").value = returnSelectedCarrierName;
	returnSelectedCarrierName = returnSelectedCarrierName + "/" + (returnSelectedAccount != null ? returnSelectedAccount : "");
	$("carrierLine1").innerHTML = returnSelectedCarrierName;
	$("carrierHub").value = returnSelectedCompanyHub;
	$("carrierLine2").innerHTML = returnSelectedCompanyHub != null ? returnSelectedCompanyHub : "";
	
}


function showLineExpediteNotesHistory() {
	if (poLineSelectedRowId == null || poLineSelectedRowId == "") {
		poLineSelectedRowId = 1;
	}
	var povalue  =  document.getElementById("radianPo");
	var poline  =  gridCellValue(lineItemGrid, poLineSelectedRowId,'poLine');
	
	loc = "/tcmIS/supply/polineexpeditehistory.do?poLine="+poline+
			"&radianPo="+povalue.value + 
			"&action=search";
	openWinGeneric(loc,"ShowPoLineNotesHistory","600","520","yes","50","50");	
} 

function showItemExpediteNotesHistory() {
	if (poLineSelectedRowId == null || poLineSelectedRowId == "") {
		poLineSelectedRowId = 1;
	}
	var itemId  =  gridCellValue(lineItemGrid,poLineSelectedRowId,'itemId');
	
	loc = "/tcmIS/supply/polineexpeditehistory.do?itemId="+itemId+"&action=search";
	openWinGeneric(loc,"ShowPoLineItemNotesHistory","600","520","yes","50","50");	
}

function showPoExpediteNotes() {
	var povalue  =  document.getElementById("radianPo");
	
	loc = "/tcmIS/supply/newpoexpediting.do?action=searchnewpoexpedite&radianPo="+povalue.value+"";
	openWinGeneric(loc,"ShowPoExpediteNotes"+povalue.value.replace('.','a')+"","900","400","yes","100","100");	
} 

function showSoucingInfo() {
	
	if (poLineSelectedRowId == null || poLineSelectedRowId == "") {
		poLineSelectedRowId = 1;
	}
	
    var itemID  =  gridCellValue(lineItemGrid,poLineSelectedRowId,'itemId');
    
	if ((itemID != null && itemID != "")) {
		
		var inventoryGroup  =  document.getElementById("inventoryGroup");
    	var opsEntityId  =  document.getElementById("opsEntityId");
    	var suppid  =  document.getElementById("supplier");
    	var supplierName = document.getElementById("supplierName").value;
    	supplierName = supplierName.replace(/&/gi, "%26");
    	supplierName = supplierName.replace(/#/gi, "%23");
		var hub  =  document.getElementById("branchPlant");

    	loc = "/tcmIS/distribution/editpricelist.do?uAction=search&searchField=itemId%7Cnumber&searchMode=is&searchArgument=" + itemID;
		loc = loc + "&supplier=" + suppid.value;
		loc = loc + "&supplierName=" + supplierName;
		loc = loc + "&inventoryGroup=" + inventoryGroup.value;
		loc = loc + "&hub=" + hub.value;
	    loc = loc + "&opsEntityId=" + opsEntityId.value;
		
	    openWinGeneric(loc, "showSourcingInfo", "1024", "600", "yes", "50", "50");
		
    }
	
}


function showPOLineHistory() {
	if (poLineSelectedRowId == null || poLineSelectedRowId == "") {
		poLineSelectedRowId = 1;
	}
	var loc = "/tcmIS/supply/polinehistory.do?radianPO1=0";
    
	var po  =  document.getElementById("radianPo");
    //var bpo  =  document.getElementById("bpo");
    
    var amendment = gridCellValue(lineItemGrid, poLineSelectedRowId,'amendment');
    var poline  =  gridCellValue(lineItemGrid, poLineSelectedRowId,'poLine'); 

    if ( (po.value.length > 0) && (amendment != null && amendment != "" ) && amendment > 0 )
    {
       loc = loc + "&poLine=" + poline;
       loc = loc + "&amendment=" + amendment;

       if ( po != null && po != "" ) {
           loc = loc + "&Action=view&subUserAction=po";
           loc = loc + "&radianPo="+ po.value;
       }
       //else if (bpo != null and bpo != "")
       //{
       //    loc = loc + "&Action=view&subUserAction=bpo";
       //    loc = loc + "&radianPO=" + bpo.value;
       //}
       openWinGeneric(loc,"showPOLineHistory","1024", "600","yes","50", "50");
    }
}

function discardChangesMade(){
	var oldNeedDate = gridCellValue(lineItemGrid,previousRowId,"oldNeedDate"); 
	var oldPromisedDate = gridCellValue(lineItemGrid,previousRowId,"oldPromisedDate");
	var oldProjectedDate = gridCellValue(lineItemGrid,previousRowId,"oldProjectedDate");
	var oldItemId = gridCellValue(lineItemGrid,previousRowId,"oldItemId"); 
	var oldQuantity = gridCellValue(lineItemGrid,previousRowId,"oldQuantity");
	var oldUnitPriceDisp = gridCellValue(lineItemGrid,previousRowId,"oldUnitPriceDisp");
	var oldCurrency = gridCellValue(lineItemGrid,previousRowId,"oldCurrency");
	var oldSupplierPn = gridCellValue(lineItemGrid,previousRowId,"oldSupplierPn");
	var oldDpas = gridCellValue(lineItemGrid,previousRowId,"oldDpas");
	var oldShelfLife = gridCellValue(lineItemGrid,previousRowId,"oldShelfLife");
	var oldAmendment = gridCellValue(lineItemGrid,previousRowId,"oldAmendment");
    var newLineAmendment = gridCellValue(lineItemGrid, previousRowId, "poLineNumber") + "/" + oldAmendment;
	
	lineItemGrid.cellById(previousRowId, lineItemGrid.getColIndexById("needDate")).setValue(oldNeedDate);
	lineItemGrid.cellById(previousRowId, lineItemGrid.getColIndexById("promisedDate")).setValue(oldPromisedDate);
	lineItemGrid.cellById(previousRowId, lineItemGrid.getColIndexById("projectedDate")).setValue(oldProjectedDate);
	lineItemGrid.cellById(previousRowId, lineItemGrid.getColIndexById("itemId")).setValue(oldItemId);
	lineItemGrid.cellById(previousRowId, lineItemGrid.getColIndexById("quantity")).setValue(oldQuantity);
	lineItemGrid.cellById(previousRowId, lineItemGrid.getColIndexById("unitPriceDisp")).setValue(oldUnitPriceDisp);
	lineItemGrid.cellById(previousRowId, lineItemGrid.getColIndexById("currency")).setValue(oldCurrency);
	lineItemGrid.cellById(previousRowId, lineItemGrid.getColIndexById("supplierPn")).setValue(oldSupplierPn);
	lineItemGrid.cellById(previousRowId, lineItemGrid.getColIndexById("dpas")).setValue(oldDpas);
	lineItemGrid.cellById(previousRowId, lineItemGrid.getColIndexById("shelfLife")).setValue(oldShelfLife);
	//need to reset amendment value, since it is automatically increased by 1 when line is changed while its status is Closed or Confirmed
	setGridCellValue(lineItemGrid, previousRowId, "amendment", oldAmendment);
	setGridCellValue(lineItemGrid, previousRowId, "line", newLineAmendment);
}

//check for any change to current row in lineItemGrid
function checkForChanges(oldRowId, additionalCondition){
	if (additionalCondition === null)
		additionalCondition = true;
	if (oldRowId != 0 ) {
		if (additionalCondition ||
				gridCellValue(lineItemGrid, oldRowId,'lookAheadChanged') == 'Y' ||
				gridCellValue(lineItemGrid, oldRowId,'specChanged') == 'Y' ||
				gridCellValue(lineItemGrid, oldRowId,'flowdownChanged') == 'Y' ||
				gridCellValue(lineItemGrid, oldRowId,'chargeListChanged') == 'Y') {
			var response = confirm("You have made some changes for PO Line "+oldRowId+". Would you like to save them?\nClick 'OK' to save changes and 'Cancel' to discard changes.");
			if (response) {
				return true;
			} else {
				previousRowId = oldRowId;
				//remove the flags or reset them to 'N' as all the changes will be lost.
				lineItemGrid.cellById(previousRowId, lineItemGrid.getColIndexById("lineChange")).setValue("N");				
				lineItemGrid.cellById(previousRowId, lineItemGrid.getColIndexById("lookAheadChanged")).setValue("N");
				lineItemGrid.cellById(previousRowId, lineItemGrid.getColIndexById("specChanged")).setValue("N");
				lineItemGrid.cellById(previousRowId, lineItemGrid.getColIndexById("flowdownChanged")).setValue("N");
				lineItemGrid.cellById(previousRowId, lineItemGrid.getColIndexById("chargeListChanged")).setValue("N");
				
				discardChangesMade();
			}
		}
	}
	return false;
}

// lineItemDetailRows and addChargesList are mutually exclusive
// secondarySupplierEdit belongs to one or the other
// making these rules explicit
function showLineItemDetailRows() {
	$('lineItemDetailRows').style.display = "";
	$('addChargesList').style.display = "none";
	
}

function showAddChargesList() {
	$('lineItemDetailRows').style.display = "none";
	$('addChargesList').style.display = "";
}

function selectRow(rowId) 
{	
	if (rowId != previousRowId) {
		if (checkForChanges(previousRowId)) {
			savePoLineChanges(previousRowId);
		}
		initiatedAjaxForPoLine = true;
	}

	//use the initiatedAjaxForPoLine flag so that selectRow() method is not fired 
	//repeatedly when the same po line is clicked by the user multiple times.
	if (rowId == previousRowId) {
		if (!initiatedAjaxForPoLine) {
			initiatedAjaxForPoLine = true;
		} else {			
			return;
		}	
	}
	
	showTransitWin("");
	try {
		poLineSelectedRowId = rowId;
		var type  =  gridCellValue(lineItemGrid, poLineSelectedRowId,'isMaterialTypeItem');	
		var differentSupplierOnLine = gridCellValue(lineItemGrid, poLineSelectedRowId,'differentSupplierOnLine');
		var secondarySupplierAllowed = gridCellValue(lineItemGrid, poLineSelectedRowId,'secondarySupplierOnPo');
		var everConfirmed = gridCellValue(lineItemGrid, poLineSelectedRowId,'everConfirmed');
		var changeSupplier = gridCellValue(lineItemGrid, poLineSelectedRowId,'changeSupplier');
		var itemType = gridCellValue(lineItemGrid, rowId,'type');
		var status = gridCellValue(lineItemGrid, rowId,'status');
		var poLineClosed = gridCellValue(lineItemGrid, rowId,'poLineClosed');
		
		var secondarySupplierElement = null;
		if (type == "N") {
			showAddChargesList();
			if (previousRowId != 0 && previousRowId != rowId) {
				resetPreviousRowIdStatuses(previousRowId, previousItemType);
			}
			selectPoLinesForChargeType(rowId);
			
			$('addChargeLastConfirmed').innerHTML = gridCellValue(lineItemGrid, poLineSelectedRowId,'lastConfirmed');
			$('addChargeSecondarySupplier').innerHTML = gridCellValue(lineItemGrid, rowId,'secondarySupplierAddress');
			$('addChargeItemDesc').innerHTML=gridCellValue(lineItemGrid, poLineSelectedRowId,'itemDescription');
			$('addChargePoLineNumber').innerHTML = gridCellValue(lineItemGrid, poLineSelectedRowId,'poLineNumber');
			
			previousRowId = rowId;
			secondarySupplierElement = $('addChargeSearchSecondarySupplier');
		} else {		
			showLineItemDetailRows();
			if (previousRowId != 0) {
				resetPreviousRowIdStatuses(previousRowId, previousItemType);
			}	
			$('materialTypeItem').style.display = "";
			
			//logic to show/hide the edit button of the associated prs grid
			if (window['editAssociatedPrs'])
			{
				if (poLineClosed == 'true' || status == 'Canceled')
					$("associatedPrsLink").style.display = "none";
				else 
					$("associatedPrsLink").style.display = "";
			}
			
			collapseAllGrids();
			initialGridLoads();		
			populatePoLineDetail(rowId);
			
			previousRowId = rowId;
			secondarySupplierElement = $('searchSecondarySupplier');
		}
		calculateSuppTotalPrice(rowId);
		
		if (secondarySupplierElement)
			if ( (changeSupplier != "N" && secondarySupplierAllowed == "Y") || ((differentSupplierOnLine == "Y" || secondarySupplierAllowed == "Y") && everConfirmed != "Y") )
				secondarySupplierElement.style.display = '';
			else
				secondarySupplierElement.style.display = 'None';
		
		previousItemType = type;
	} catch (e) {
		alert(messagesData.genericerror);
	}
	closeTransitWin();
}

function populatePoLineDetail(rowId){
	//td/span - 
	j$('#lastconfirmed').html(gridCellValue(lineItemGrid, rowId,'lastConfirmed'));
	j$('#shelfLifeBasis').html(gridCellValue(lineItemGrid, rowId,'shelfLifeBasis'));
	j$('#itemDesc').html(gridCellValue(lineItemGrid, rowId,'itemDescription'));
	j$('#materialSecondarySupplier').html(gridCellValue(lineItemGrid, rowId,'secondarySupplierAddress'));	
	j$('#itemVerifiedBy').html(gridCellValue(lineItemGrid, rowId,'itemVerifiedBy') + " <b>" + messagesData.on + "</b> " + (gridCellValue(lineItemGrid, rowId,'verifiedOn')));
	j$('#verifiedOn').html(gridCellValue(lineItemGrid, rowId,'verifiedOn'));
	j$('#detailPoLineNumber').html(gridCellValue(lineItemGrid, rowId,'poLineNumber'));
	j$('#mpn').html(gridCellValue(lineItemGrid, rowId,'mpn'));
	j$('#shipFromLocationId').html(gridCellValue(lineItemGrid, rowId,'shipFromLocationId'));
	//input-
	j$('#supplierPn').val(gridCellValue(lineItemGrid, rowId,'supplierPn'));
	j$('#dpas').val(gridCellValue(lineItemGrid, rowId,'dpas'));
	j$('#shelfLife').val(gridCellValue(lineItemGrid, rowId,'shelfLife'));
	//textarea
	j$('#lineNotes').val(gridCellValue(lineItemGrid, rowId,'lineNotes'));
	j$('#deliveryComments').val(gridCellValue(lineItemGrid, rowId,'deliveryComments'));
	//header item (not required anymore as currency is line item attribute)
	//j$('#currency').val(gridCellValue(lineItemGrid, rowId,'currency'));
	//checkbox
	if (gridCellValue(lineItemGrid, rowId,'msdsRequestedDate') != null && gridCellValue(lineItemGrid, rowId,'msdsRequestedDate') == 'Y')
		j$('#msdsRequestedDate').prop( "checked", true );
	else 
		j$('#msdsRequestedDate').prop( "checked", false );
}

function resetPreviousRowIdStatuses(previousRowId, previousItemType) {
	if (previousItemType == 'Y') {
		$('materialTypeItem').style.display = "none";
		
		$("lookAheadStatus").value = "No";
		$("itemNotesStatus").value = "No";
		$("partNotesStatus").value= "No";
		$("specsStatus").value = "No";   
		$('flowdownsStatus').value = "No";
		$('msdsStatus').value = "No";
		$("associatedPrsStatus").value = "No";
		$('tcmBuysStatus').value = "No";
		$("clientBuysStatus").value= "No";	
		
		$("lookAheadLoaded").value = "No";
		$("itemNotesLoaded").value = "No";
		$("partNotesLoaded").value= "No";
		$("specsLoaded").value = "No";   
		$('flowdownsLoaded').value = "No";
		$('msdsLoaded').value = "No";
		$("associatedPrsLoaded").value = "No";
		$('tcmBuysLoaded').value = "No";
		$("clientBuysLoaded").value= "No";	
		
	} else  {
		if ($('addChargesList') != null && $('addChargesDivBlock') != null ) {
			$('addChargesDivBlock').style.display = "none";	
			$("chargesListStatus").value = "No";
			$("chargesListLoaded").value = "No";
		}
	}	
}

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

function enterSoleSourcePriceQuote()
{
	if (poLineSelectedRowId == null || poLineSelectedRowId == "") {
		poLineSelectedRowId = 1;
	}
    //https://www.tcmis.com/cgi-bin/purch/ss_enter.cgi?777777-1000=Y
    var loc = "https://www.tcmis.com/cgi-bin/purch/ss_enter.cgi?";
	var po  =  document.getElementById("radianPo");
	var poline  =  gridCellValue(lineItemGrid, poLineSelectedRowId,'poLine'); 

    if (po.value.length > 0)
    {
        loc = loc + po.value+"-"+poline+"=Y";
        openWinGeneric(loc,"enterSoleSourcePriceQuotein","300","300","yes","300","300");        
    }    
}

function selectPoLinesForChargeType(rowId){	
	var poAddChargeLine = gridCellValue(lineItemGrid, rowId,'poLine');	
	var relatedPoLines = gridCellValue(lineItemGrid, rowId,'relatedPoLines');	
	var splitRelatedPoLineNumbers = relatedPoLines.split(",");
	if (window['addChargesDivBlock']) {		
		$('addChargesDivBlock').style.display = "";
	}
	if (window['chargesListBean']) {
		if ($v('chargesListLoaded') == "No") {
			initChargesListGrid();
			for (var k = 0; k < window['chargesListGrid'].getRowsNum();k++) 
			{
				var pos = window['chargesListGrid'].getRowId(k);
				var poLineNumber = gridCellValue(window['chargesListGrid'], pos,'poLine');		
				if (checkHasPoLine(splitRelatedPoLineNumbers, poLineNumber)) {			
					window['chargesListGrid'].cellById(pos, window['chargesListGrid'].getColIndexById("selectPo")).setValue(true);			
				} else {
					window['chargesListGrid'].cellById(pos, window['chargesListGrid'].getColIndexById("selectPo")).setValue(false);			
				}
				window['chargesListGrid'].cellById(pos, window['chargesListGrid'].getColIndexById("poAddChargeLine")).setValue(poAddChargeLine);
			}
			$('chargesListLoaded').value = "Yes";
		}	
	}
}
	
function checkHasPoLine(poLineList, poLineNumber) {
	var found = false;
	for (var i = 0; i < poLineList.length; i++) {
		if (poLineNumber == poLineList[i]) 
			found = true;
	}
	return found;
}

	
function setLineUnconfirmed(rowId)
{
	if (poLineSelectedRowId == null || poLineSelectedRowId == "") {
		poLineSelectedRowId = 1;
	}
	if (rowId == undefined || rowId == "" || rowId == null)
		rowId = poLineSelectedRowId;
    var lineqty = gridCellValue(lineItemGrid, rowId,'quantity');
    var qtyreceived = gridCellValue(lineItemGrid, rowId,'quantityReceived');
    var selectedlRowStatus = gridCellValue(lineItemGrid, rowId,'status');
    
	if (selectedlRowStatus == "Unconfirmed" || selectedlRowStatus == "Draft") {
		//
    } else if ( (lineqty*1) <= (qtyreceived*1) ) {
    	//selectedRowStatus.innerHTML = "Closed";
    	lineItemGrid.cellById(rowId, lineItemGrid.getColIndexById("status")).setValue("Closed");
    } else {
    	//selectedRowStatus.innerHTML = "Unconfirmed";
    	lineItemGrid.cellById(rowId, lineItemGrid.getColIndexById("status")).setValue("Unconfirmed");
    }

    if ( (selectedlRowStatus == "Confirmed") || (selectedlRowStatus == "Closed"))
    {
        var rowlinenumber = gridCellValue(lineItemGrid, rowId,'poLineNumber');
        var amendmentobj = gridCellValue(lineItemGrid, rowId,'amendment');
        setGridCellValue(lineItemGrid, rowId, "oldAmendment", amendmentobj);
        amendment = (amendmentobj*1)+1;
        lineItemGrid.cellById(rowId, lineItemGrid.getColIndexById("amendment")).setValue(amendment);
        var newLineAmendment = rowlinenumber + "/" + amendment;
        lineItemGrid.cellById(rowId, lineItemGrid.getColIndexById('line')).setValue(newLineAmendment);
		lineItemGrid.cellById(rowId, lineItemGrid.getColIndexById("status")).setValue("Unconfirmed");
    }
    lineItemGrid.cellById(rowId, lineItemGrid.getColIndexById("lineChange")).setValue("Y");
}

function onAssocicatedPrsRightClick(rowId)
{
	associatedSelRowId = rowId;
	var mrNumber  =  gridCellValue(window['associatedPrsNewGrid'],associatedSelRowId,'mrNumber'); 
	var lineItem = gridCellValue(window['associatedPrsNewGrid'],associatedSelRowId,'mrLineItem');
	if (mrNumber.length == 0 || lineItem.length == 0) {
		mm_editItem('rightClickAssociatedPr',0,'text=<font color="gray">'+messagesData.showmrlineallocationreport+'</font>;url=javascript:doNothing();' );
	}
	else {
		mm_editItem('rightClickAssociatedPr',0,'text='+messagesData.showmrlineallocationreport+';url=javascript:getMrLineAllocationReport();' );
	}
	toggleContextMenu('rightClickAssociatedPr');
}

function onLineItemRightClick(rowId)
{
	poLineSelectedRowId = rowId;
		
	lineItemGrid.selectRowById(rowId, null, true, true);
	
	var menuItems = new Array();
	poLineDateChangedRowLevel(rowId);
	
	var poLineChanges = gridCellValue(lineItemGrid, poLineSelectedRowId,'lineChange');
	if (poLineChanges == 'Y')
		menuItems[menuItems.length] = 'text='+messagesData.savepoline+';url=javascript:savePoLineChanges('+poLineSelectedRowId+');';
		
	var currentStatus = gridCellValue(lineItemGrid, poLineSelectedRowId,'status');
	var qtyRcvd = gridCellValue(lineItemGrid, poLineSelectedRowId,'quantityReceived');
	if (currentStatus == 'Remove') {
		menuItems[menuItems.length] = 'text='+messagesData.undoRemove+';url=javascript:unRemoveLine();';
		if (poLineChanges == 'N')
			menuItems[menuItems.length] = 'text='+messagesData.savepoline+';url=javascript:deleteSelectedPOLine('+poLineSelectedRowId+');';
	} else {
		if (qtyRcvd == 0) {
			menuItems[menuItems.length] = 'text='+messagesData.removeLine+';url=javascript:removeLine();';
		}
	}
	
	var amendment = gridCellValue(lineItemGrid, poLineSelectedRowId,'amendment');
	if(amendment > 0)
		menuItems[menuItems.length] = 'text='+messagesData.linehistory+';url=javascript:showPOLineHistory();';
	
	var type = gridCellValue(lineItemGrid, poLineSelectedRowId,'isMaterialTypeItem');	
	if (type== 'Y') {
		menuItems[menuItems.length] = 'text='+messagesData.enterlineexpeditenotes+';url=javascript:enterExpediteNotes();';
		menuItems[menuItems.length] = 'text='+messagesData.lineexpeditinghistory+';url=javascript:showLineExpediteNotesHistory();';
		menuItems[menuItems.length] = 'text='+messagesData.itemexpeditinghistory+';url=javascript:showItemExpediteNotesHistory();';
		menuItems[menuItems.length] = 'text='+messagesData.editsourcinginfo+';url=javascript:showSoucingInfo();';		
	} 

	//replace the static menu with the dynamic menu
    if (menuItems.length > 0) {
        replaceMenu('rightClickLineItem',menuItems);
        toggleContextMenu('rightClickLineItem');
    }    
}

function onSpecRightClickMenu(rowId, cellId) {	 
	if (poLineSelectedRowId == null || poLineSelectedRowId == "") {
		poLineSelectedRowId = 1;
	}
	specSelRowId = rowId;
	if(gridCellValue(window['specsGrid'],rowId,'onLine') != ''  
		&& gridCellValue(window['specsGrid'],rowId,'onLine') == 'Y'
		&& gridCellValue(window['specsGrid'],rowId,'content') != '' )
		toggleContextMenu("rightClickSpec");
	else
		toggleContextMenu('contextMenu');
}

function onMsdsRightClickMenu(rowId, cellId) {	 
	if (poLineSelectedRowId == null || poLineSelectedRowId == "") {
		poLineSelectedRowId = 1;
	}
	msdsSelRowId = rowId;
	if(gridCellValue(window['msdsGrid'],rowId,'onLine') != '' 
		&& gridCellValue(window['msdsGrid'],rowId,'onLine') == 'Y'
		&& gridCellValue(window['msdsGrid'],rowId,'content') != '' )
		toggleContextMenu("rightClickMsds");
	else
		toggleContextMenu('contextMenu');
}

function onFlowdownsRightClickMenu(rowId, cellId) {	 
	if (poLineSelectedRowId == null || poLineSelectedRowId == "") {
		poLineSelectedRowId = 1;
	}
	flowdownSelRowId = rowId;
	if(gridCellValue(window['flowdownsGrid'],rowId,'onLine') != '' 
		&& gridCellValue(window['flowdownsGrid'],rowId,'onLine') == 'Y'
		&& gridCellValue(window['flowdownsGrid'],rowId,'content') != '' )
		toggleContextMenu("rightClickFlowdowns");
	else
		toggleContextMenu('contextMenu');
}

function onTcmBuysRightClick(rowId, cellId) {
	tcmbuysSelRowId = rowId;
	toggleContextMenu('rightClickTcmBuys');
}

function viewSpecDoc() {
	var content = gridCellValue(window['specsGrid'],specSelRowId,'content');
	var opsEntityId =  document.getElementById("opsEntityId").value;	
	var invenGrp  =  document.getElementById("inventoryGroup");	
	var specId = gridCellValue(window['specsGrid'],specSelRowId,'specId');
	
	var loc = "../haas/docViewer.do?uAction=viewSpec&fileName="+content+"&opsEntityId="+opsEntityId+"&inventoryGroup="+invenGrp+"&spec="+specId;    
    openWinGeneric(loc,"ViewSpecDoc","650","500","yes","50","50");	
}

function viewFlowdown() {
	var content = gridCellValue(window['flowdownsGrid'],flowdownSelRowId,'content');
	
	var loc = content;    
    openWinGeneric(loc,"ViewFlowdownsDoc","650","500","yes","50","50");	
}

function viewMsds() {
	var content = gridCellValue(window['msdsGrid'],msdsSelRowId,'content');
		
	var loc = content;    
    openWinGeneric(loc,"ViewMsds","950","800","yes","150","150");	
}

function viewPurchaseOrder() {
	var po = gridCellValue(window['tcmBuysGrid'],tcmbuysSelRowId,'pon');
	var loc = "/tcmIS/supply/purchaseorder.do?action=searchlike&radianPo="+po;    
    openWinGeneric(loc,"ViewPurchaseOrder","950","800","yes","150","150");
}

function doNothing()
{
	 toggleContextMenu('contextMenu');
}

function getCannedComments()
{
	if (poLineSelectedRowId == null || poLineSelectedRowId == "") {
		poLineSelectedRowId = 1;
	}
	//loc = "/tcmIS/purchase/showcannedcomments?lineID=" + rowname;
	var loc = "/tcmIS/supply/pocannedcommentsmain.do?poLineID="+poLineSelectedRowId;    
    openWinGeneric(loc,"POCannedComments","650","500","yes","50","50");	
}

function addSelCannedComments(c)
{
	if (poLineSelectedRowId == null || poLineSelectedRowId == "") {
		poLineSelectedRowId = 1;
	}
	var linenotesvalue = $v("lineNotes");
	$("lineNotes").value = linenotesvalue + "\n" + c.comment;
	lineItemGrid.cellById(poLineSelectedRowId, lineItemGrid.getColIndexById('lineNotes')).setValue($("lineNotes").value);
	setLineUnconfirmed(poLineSelectedRowId);
}

function addItemNotes()
{   
	if (poLineSelectedRowId == null || poLineSelectedRowId == "") {
		poLineSelectedRowId = 1;
	}
    var loc = "/tcmIS/supply/edititemnotes.do?itemId=";    
    var itemID  =  gridCellValue(lineItemGrid, poLineSelectedRowId, 'itemId');
    if (itemID != null && itemID != "") {
        loc = loc + itemID; 
        //collapseItemNotesGrid(rowId);      
        $("itemNotesLoaded").value = "No";        
        openWinGeneric(loc,"editItemNotes","800","600","yes","160","150");
        //call these to if we can modify the return function call in itemnotes.js
        //$("itemNotesLoaded").value = "No";
        getItemNotes();
    }
}

//this function will intialize dhtmlXWindow if it's null
function initializeDhxWins() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function showTransitWin(messageType)
{
	if(messageType == "justPleaseWait") {
		$("transitLabel").innerHTML = messagesData.pleasewaitmenu+"...";
	}else {
		$("transitLabel").innerHTML = messagesData.pleasewait;
	}

	var transitDailogWin = document.getElementById("transitDailogWin");
	transitDailogWin.style.display="";

	initializeDhxWins();
	if (!dhxWins.window("transitDailogWin")) {
		// create window first time
		var transitWin = dhxWins.createWindow("transitDailogWin",0,0, 400, 200);
		transitWin.setText("");
		transitWin.clearIcon();  // hide icon
		transitWin.denyResize(); // deny resizing
		transitWin.denyPark();   // deny parking
		transitWin.attachObject("transitDailogWin");
		//transitWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
		transitWin.center();
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		//transitWin.setPosition(328, 131);
		//transitWin.setPosition(750, 25);
		//transitWin.screenLeft
		//transitWin.screenTop
		transitWin.button("minmax1").hide();
		transitWin.button("park").hide();
		transitWin.button("close").hide();
		transitWin.bringToTop();
		transitWin.focus();
		transitWin.setModal(true);		
	}else{
		// just show
		dhxWins.window("transitDailogWin").setModal(true);
		dhxWins.window("transitDailogWin").show();
	}
}

function closeTransitWin() {
	if (dhxWins != null) {
		if (dhxWins.window("transitDailogWin")) {
			dhxWins.window("transitDailogWin").setModal(false);
			dhxWins.window("transitDailogWin").hide();
		}
	}
}
/*
function showlegend()
{
	openWinGeneric("/tcmIS/help/pospeclegend.html","pospecslegend","250","325","no","50","50")
}
*/
function getMrLineAllocationReport()
{
	var companyId  = gridCellValue(window['associatedPrsNewGrid'],associatedSelRowId,'companyId');
	var mrNumber  =  gridCellValue(window['associatedPrsNewGrid'],associatedSelRowId,'mrNumber'); 
	var lineItem = gridCellValue(window['associatedPrsNewGrid'],associatedSelRowId,'mrLineItem');
	
	if (companyId.length > 0 ) {
		var loc = "/tcmIS/distribution/mrallocationreportmain.do?fromCustomerOrdertracking=Y&companyId="+companyId+"&mrNumber="+mrNumber+"&lineItem="+lineItem+"";
        openWinGeneric(loc,"showMrAllocationReport","800","550","yes","80","80","no");
	}
}

function getSuggestedSupplier()
{
	  var partId = gridCellValue(window['associatedPrsNewGrid'],associatedSelRowId,'partnumber');
	  var requestId  =  gridCellValue(window['associatedPrsNewGrid'],associatedSelRowId,'requestId');
	  var catalogId = gridCellValue(window['associatedPrsNewGrid'],associatedSelRowId,'catalogId');

	  var loc = "/tcmIS/supply/showsuggestedsupplier.do?Action=showsuggestedsupplier" + "&catPartNo=" + partId + "&requestId=" +requestId+ "&catalogId=" +catalogId;
	  openWinGeneric(loc,"suggestedsupplier","500","450","yes","200","200")
}

function editAssociatedPrs(poLineNumber)
{  
	//document.getElementById("addbuyorderslink").style["display"] = "none";
	var HubName  =  $v("branchPlant"); 
	
	var shiptoid  =  $v('shipToLocationId');
    shiptoid = shiptoid.replace(/&/gi, "%26");
    shiptoid = shiptoid.replace(/#/gi, "%23");
    
    //var povalue  =  $v("po");
	var buyerid  =  $v("buyer");
	var invengrp = $v( "inventoryGroup" );
    var attachedpr = $v("attachedPr");
    var itemID  =  gridCellValue(lineItemGrid,poLineSelectedRowId,'itemId');
    var validitem  =  gridCellValue(lineItemGrid,poLineSelectedRowId,'validItem');
    
    if (invengrp.trim().length == 0) {
	     alert("Please Choose a Valid Inventory Group.");
	} else if (validitem == "No" || itemID.trim().length==0) {
         alert("Please Choose a Valid Item Id");
    } else {
        var loc = "/tcmIS/supply/editassociatedprsmain.do?hubName="+HubName;
        var amendment  =  gridCellValue(lineItemGrid, poLineSelectedRowId,'amendment');
        var radianPO = $v("radianPo");

        if (radianPO.length > 0) {
            if (validitem == "Yes") {
            	loc = loc + "&itemId=" + itemID;
            }
			loc = loc + "&lineId=" + gridCellValue(lineItemGrid, poLineSelectedRowId,'poLineNumber');
            loc = loc + "&radianPo=" + radianPO;

			var prshipto  =  gridCellValue(lineItemGrid, poLineSelectedRowId,'prShipTo');
            prshipto = prshipto.replace(/&/gi, "%26");
            prshipto = prshipto.replace(/#/gi, "%23");
			if ( prshipto.trim().length > 0 ) {
				loc = loc  + "&shipTo=" + prshipto;
			} else if (shiptoid.length > 0) {
				loc = loc  + "&shipTo=" + shiptoid;
			} else {
				loc = loc  + "&shipTo=";
			}
			
			var shiptocompanyid  =  $v("shipToCompanyId");
			
			loc = loc + "&shipToLocationId=" + shiptoid;
			loc = loc + "&shipToCompanyId=" + shiptocompanyid;
			loc = loc + "&buyerId=" + buyerid;
			loc = loc + "&amendment=" + amendment;
			loc = loc + "&uAction=editassociatedPRs";
			loc = loc + "&userAction=editassociatedPRs";
			loc = loc + "&inventoryGroup=" + invengrp;
			loc = loc + "&attachedpr=" + attachedpr;
			loc = loc + "&fromPo=Y";
			openWinGeneric(loc,"editassociatedPRs","1000","600","yes","40","40");
        }
    }	
}

function refereshEditAssociatedPrs(isFullyDisassociated) {
	collapseAssociatedPRsGrid(poLineSelectedRowId);
	$("associatedPrsLoaded").value = "No";
	getAssociatedPrs(poLineSelectedRowId);
	if (isFullyDisassociated) {
		if (gridCellValue(lineItemGrid, poLineSelectedRowId, "poLineStatus") != "Confirmed"
				&& gridCellValue(lineItemGrid, poLineSelectedRowId, "oldAmendment") === "0") {
			setGridCellValue(lineItemGrid, poLineSelectedRowId, "itemId", "");
			setGridCellValue(lineItemGrid, poLineSelectedRowId, "type", "");
			setGridCellValue(lineItemGrid, poLineSelectedRowId, "itemDescription", "");
			setGridCellValue(lineItemGrid, poLineSelectedRowId, "uom", "");
			setGridCellValue(lineItemGrid, poLineSelectedRowId, "validItem", "No");
		}
		
		if (gridCellValue(lineItemGrid, poLineSelectedRowId, "quantity") != "0") {
			setGridCellValue(lineItemGrid, poLineSelectedRowId, "needDate", "");
			setGridCellValue(lineItemGrid, poLineSelectedRowId, "promisedDate", "");
			setGridCellValue(lineItemGrid, poLineSelectedRowId, "projectedDate", "");
			setGridCellValue(lineItemGrid, poLineSelectedRowId, "quantity", "");
			setGridCellValue(lineItemGrid, poLineSelectedRowId, "unitPriceDisp", "");
			setGridCellValue(lineItemGrid, poLineSelectedRowId, "extendedPriceDisp", "");
			populateUnitPrice(poLineSelectedRowId);
		}
	}
}

function showSchedule()
{
	var	mrnumber = gridCellValue(window['associatedPrsNewGrid'],associatedSelRowId,'mrNumber');
	var	lineitem = gridCellValue(window['associatedPrsNewGrid'],associatedSelRowId,'mrLineItem');
	
    loc = "/tcmIS/supply/showscheduletcmbuys.do?Action=showschedulde&prNumber=";
    loc = loc + mrnumber;
    loc = loc + "&lineItem="+lineitem;
    openWinGeneric(loc,"tcmbuyhistory","400","460","yes","40","40")
}


function showLPPranges()
{
	var itemid = gridCellValue(lineItemGrid,poLineSelectedRowId,'itemId');
	var	mrnumber = gridCellValue(window['associatedPrsNewGrid'],associatedSelRowId,'prnumber');
	var	lineitem = gridCellValue(window['associatedPrsNewGrid'],associatedSelRowId,'mrLineItem');
	var	invengrp = $v( "inventoryGroup" );
	var	companyid = gridCellValue(window['associatedPrsNewGrid'],associatedSelRowId,'companyId');
	var currencyid = gridCellValue(lineItemGrid,poLineSelectedRowId,'currency'); // $v("currency");
	var opsEntityId = $v('opsEntityId');
	
    //var loc = "/tcmIS/purchase/showtcmbuys?itemID=";
    var loc = "/tcmIS/supply/showlppranges.do?itemId=";
       loc = loc + itemid;
       loc = loc + "&mrNumber=" + mrnumber;
       loc = loc + "&mrLineItem=" + lineitem;
       loc = loc + "&inventoryGroup=" + invengrp;
       loc = loc + "&companyId=" + companyid;    
       loc = loc + "&currency=" + currencyid; 
       loc = loc + "&opsEntityId=" + opsEntityId;
       loc = loc + "&Action=showlpprange";

    openWinGeneric(loc,"showlpprange","650","420","yes","40","40");
}

function sendSupplierData()
{
	//alert("sendSupplierData");
}

function showErrorMessages() {
	  var resulterrorMessages = document.getElementById("errorMessagesAreaBody");
	  var errorMessagesArea = document.getElementById("errorMessagesArea");
	  errorMessagesArea.innerHTML = resulterrorMessages.innerHTML;

	  var errorMessagesArea = document.getElementById("errorMessagesArea");
	  errorMessagesArea.style.display="";

	  var dhxWins = new dhtmlXWindows();
	  dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	  if (!dhxWins.window("errorWin")) {
	  // create window first time
	  var errorWin = dhxWins.createWindow("errorWin", 0, 0, 450, 300);
	  errorWin.setText(messagesData.errors);
	  errorWin.clearIcon();  // hide icon
	  errorWin.denyResize(); // deny resizing
	  errorWin.denyPark();   // deny parking
	  errorWin.attachObject("errorMessagesArea");
	  errorWin.attachEvent("onClose", function(errorWin){errorWin.hide();});
	  errorWin.center();
	  }
	  else
	  {
	    // just show
	    dhxWins.window("errorWin").show();
	  }
}

function prepareNewItemData(isMaterialTypeItem) {
	var lineId = getNewLineId();
	var j = 0;
	newLineData = new Array();
	var dateEditablePerm = isMaterialTypeItem;
    if(isMaterialTypeItem == 'Y')
    	canAssignAddCharge = 'Y';
    else 
    	canAssignAddCharge = 'N';

	newLineData[j++] = 'Y';		//permission
	newLineData[j++] = lineId + "/0";  //line - line/amendment
	newLineData[j++] = '';		//ghsCompliant
	newLineData[j++] = '';		//itemId
	newLineData[j++] = '<input name="lookupItemId" type="button" value="..." class="lookupBtn" onmouseover="this.className=\'lookupBtn lookupBtnOver\'" onmouseout="this.className=\'lookupBtn\'"  onclick="getItemDetail();"></c:if>';		//itemIdLookup
	newLineData[j++] = '';		//type	
	newLineData[j++] = dateEditablePerm;			//needDatePermission
	newLineData[j++] = '';		//needDate
	newLineData[j++] = dateEditablePerm;			//promisedDatePermission
	newLineData[j++] = '';		//promisedDate
	newLineData[j++] = dateEditablePerm;			//projectedDatePermission
	newLineData[j++] = '';		//projectedDate	
	newLineData[j++] = 'Y';		//quantityPermission
	newLineData[j++] = '';		//quantity
	newLineData[j++] = '';		//uom
	newLineData[j++] = 'Y';		//unitPriceDispPermission
	newLineData[j++] = '';		//unitPriceDisp
	newLineData[j++] = '';		//extendedPriceDisp
	newLineData[j++] = 'Y';		//currencyPermission
	newLineData[j++] = '' ;    //$v('currency');//currency 
	newLineData[j++] = '0';		//quantityReceived
	newLineData[j++] = '';		//quantityVoc
	newLineData[j++] = '';			//quantityReturned
	newLineData[j++] = 'Draft';		//status
	newLineData[j++] = '0';			//amendment
	newLineData[j++] = 'Draft';		//poLineStatus
	newLineData[j++] = lineId*1000;			//poLine
	newLineData[j++] = isMaterialTypeItem;			//isMaterialTypeItem
	newLineData[j++] = '';			//lastConfirmed
	newLineData[j++] = '';			//secondarySupplierAddress
	newLineData[j++] = '';			//itemDescription
	newLineData[j++] = '';			//mpn
	newLineData[j++] = '';			//supplierPn
	newLineData[j++] = '';			//dpas
	newLineData[j++] = '';			//shelfLife
	newLineData[j++] = '';			//shelfLifeBasis
	newLineData[j++] = '';			//supplierDetail
	newLineData[j++] = '';			//verifiedOn
	newLineData[j++] = '';			//itemVerifiedBy
	newLineData[j++] = '';			//lineNotes
	newLineData[j++] = '';			//deliveryComments
	newLineData[j++] = 'N';			//everConfirmed
	newLineData[j++] = 'No';			//validItem
	newLineData[j++] = 'Y';			//differentSupplierOnLine
	newLineData[j++] = '';			//secondarySupplierOnPo
	newLineData[j++] = '';			//changeSupplier
	newLineData[j++] = '';			//itemUnitPrice
	newLineData[j++] = '';			//shipFromLocationId
	newLineData[j++] = '';			//relatedPoLines
	newLineData[j++] = '';			//prShipTo
	newLineData[j++] = lineId;			//poLineNumber
	newLineData[j++] = 'Y';			//lineChange
	newLineData[j++] = '';			//ammendmentcomments
	newLineData[j++] = '';			//lineArchived
	newLineData[j++] = canAssignAddCharge;			//canAssignAddCharge
	newLineData[j++] = '';			//supplierQty
	newLineData[j++] = '';			//supplierUnitPrice
	newLineData[j++] = '';			//supplierExtPrice
	newLineData[j++] = '0';			//lineTotalPrice
	newLineData[j++] = '';			//unitPrice
	newLineData[j++] = '';			//extendedPrice
	newLineData[j++] = '';			//oldNeedDate
	newLineData[j++] = '';			//oldPromisedDate  
	newLineData[j++] = '';				//oldProjectedDate
	newLineData[j++] = '';				//msdsRequestedDate
	newLineData[j++] = '';				//flowdownChanged
	newLineData[j++] = '';				//specChanged
	newLineData[j++] = '';				//lookAheadChanged
	newLineData[j++] = '';				//chargeListChanged
	newLineData[j++] = '';				//quantityVouchered
	newLineData[j++] = '';				//supplier
	newLineData[j++] = '';				//oldItemId
	newLineData[j++] = '';				//oldQuantity
	newLineData[j++] = '';				//oldUnitPriceDisp
	newLineData[j++] = '';				 //oldCurrency
	newLineData[j++] = '';				//oldSupplierPn
	newLineData[j++] = '';				//oldDpas
	newLineData[j++] = '';				//oldShelfLife
	newLineData[j++] = '';				//currencyConversionRate
	newLineData[j++] = '';				//poLineClosed
	newLineData[j++] = '';				//dateConfirmed
	newLineData[j++] = lineItemGrid.getRowsNum();				//rowNumber
	newLineData[j++] = '0';				//oldAmendment
}

function addLineItem(){
	prepareNewItemData('Y');
	getItemDetail();
}

function addLineCharge() {
	prepareNewItemData('N');
	getItemDetail();
}

function getNewLineId() {  //from the grid
	if (lineItemGrid.getRowsNum() == 0 ) {
		return 1;
	}
	var lineNumber = gridCellValue(lineItemGrid, lineItemGrid.getRowId(lineItemGrid.getRowsNum()-1), "poLineNumber");
	
	return Number(lineNumber) + 1;
}

function printPo() {
	var po = document.getElementById("radianPo").value;
	var HubName = document.getElementById("branchPlant").value;
   
	var supplierCountryAbbrev = document.getElementById("supplierCountryAbbrev").value;
	var opsEntityId = document.getElementById("opsEntityId").value;

	if (isEmptyString(po)) {
		alert(messagesData.validpo);
	} else {
		 var tmp = "";
		 while (true) {
			tmp = prompt(messagesData.chooseprintpo,tmp);
			if (tmp == null) {	//user hit cancel
				break;
			}
			//determine whether the user enter the correct value
			if (tmp == "0" || tmp == "1" || tmp == "2" || tmp == "3" || tmp == "4") {
				break;
			}else {
				alert(messagesData.chooseprintpo);
			}
		}	//end of while

		var tmpLocale = "";
		if (opsEntityId.trim() == "HAASTCMIT" || opsEntityId.trim() == "HAASBASSRL") {
			while (true) {
				tmpLocale = prompt("Please pick the PO Print Language:'1' Italiano '2' English", tmpLocale);
				if (tmpLocale == null) { // user hit cancel
					break;
				}
				// determine whether the user enter the correct value
				if (tmpLocale == "1" || tmpLocale == "2") {
					if (tmpLocale == "1") {
						tmpLocale = "it_IT";
					} else {
						tmpLocale = "en_US";
					}
					break;
				} else {
					alert("Please pick the PO Print Language:'1' Italiano '2' English");
				}
			}
		} else if (supplierCountryAbbrev == 'CHN' && opsEntityId == 'AVICHAAS') {
			tmpLocale = "zh_CN";
		} else if (HubName == "2403" || HubName == "2470") {
			while (true) {
				tmpLocale = prompt("Język wydruku: proszę wybrać wydruk '1' po polsku '2' po angielsku", tmpLocale);
				if (tmpLocale == null) { // user hit cancel
					break;
				}
				// determine whether the user enter the correct value
				if (tmpLocale == "1" || tmpLocale == "2") {
					if (tmpLocale == "1") {
						tmpLocale = "pl_PL";
					} else {
						tmpLocale = "en_US";
					}
					break;
				} else {
					alert("Język wydruku: proszę wybrać wydruk '1' po polsku '2' po angielsku");
				}
			} // end of while
		} else if (HubName == "2186" || HubName == "2208"
				|| HubName == "2188" || HubName == "2189"
				|| HubName == "2190" || HubName == "2402"
				|| HubName == "2400") {
			while (true) {
				tmpLocale = prompt("Please pick the PO Print Language:'1' Deutsch '2' English", tmpLocale);
				if (tmpLocale == null) { // user hit cancel
					break;
				}
				// determine whether the user enter the correct value
				if (tmpLocale == "1" || tmpLocale == "2") {
					if (tmpLocale == "1") {
						tmpLocale = "de_DE";
					} else {
						tmpLocale = "en_US";
					}
					break;
				} else {
					alert("Please pick the PO Print Language:'1' Deutsch '2' English");
				}
			}
		} else if (HubName == "2453") {
			while (true) {
				tmpLocale = prompt( "S'il vous plaît prendre le PO Imprimer Langue: '1' Français '2' Anglais", tmpLocale);
				if (tmpLocale == null) { // user hit cancel
					break;
				}
				// determine whether the user enter the correct value
				if (tmpLocale == "1" || tmpLocale == "2") {
					if (tmpLocale == "1") {
						tmpLocale = "fr_FR";
					} else {
						tmpLocale = "en_US";
					}
					break;
				} else {
					alert("S'il vous plaît prendre le PO Imprimer Langue: '1' Français '2' Anglais");
				}
			}
		}
        
		if (tmp != null) {
			if (tmp == "0" && !validateDataForSubmit("printdraft", this)) {
				return;
			}
			
			loc = "/tcmIS/supply/faxpo?Session=7&po=" + po
			// 12-11-02 pass info to server: 1 - New Order, 2 - Confirming Order and 3 - Admended Order
				+ "&HeaderNote=" + tmp 
				+ "&localeCode=" + tmpLocale
				+ "&totalCost=" + $("totalCostSpan").innerHTML
				+ "&totalCostCurrencyId=" + $v("totalCostCurrencyId");
			openWinGeneric(loc, "printpo", "700", "600", "yes", "200", "200");
		}
	}
}

function getItemDetail() {	
	if (poLineSelectedRowId == null || poLineSelectedRowId == "") {
		poLineSelectedRowId = 1;
	}
	
	var shipToId  =  $v("shipToLocationId");
	if (shipToId == "" || shipToId == null) {
		alert("Please Choose a Valid Ship To.");
		return false;
	}
	var mode = "";
	var materialType = newLineData[27];
	if ( materialType == 'N' ) {		
		mode = "lineCharge";
	}
	var shipToCompanyId =  $v("shipToCompanyId");
	var	invengrp = $v( "inventoryGroup" );
	var loc = "/tcmIS/supply/poitemsearchmain.do?uAction=search"+ 
				"&inventoryGroup="+invengrp+
				"&companyId="+shipToCompanyId+
				"&mode="+mode+
				"&poLineNumber="+getNewLineId()+
				"&shipToId="+shipToId;	
	openWinGeneric(loc,"PoItemSearch","1024","600","yes","50","50","20","20","no");
	
	return true;
}


function itemChanged(itemId,itemDesc,itemType) {
	var poLineNumber = lineItemGrid.getRowsNum() + 1;
	
    lineItemGrid.addRow(poLineNumber, newLineData, lineItemGrid.getRowsNum());
	lineItemGrid.selectRowById(poLineNumber, null, true, true);
	
	var oldItemId = gridCellValue(lineItemGrid,poLineNumber,"itemId");	
	lineItemGrid.cellById(poLineNumber, lineItemGrid.getColIndexById("itemDescription")).setValue(itemDesc);
	lineItemGrid.cellById(poLineNumber, lineItemGrid.getColIndexById("itemId")).setValue(itemId);
	lineItemGrid.cellById(poLineNumber, lineItemGrid.getColIndexById("type")).setValue(itemType);
	lineItemGrid.cellById(poLineNumber, lineItemGrid.getColIndexById("validItem")).setValue("Yes");
	
	if (oldItemId != itemId) {
		itemIdChanged(poLineNumber);
	}
			
}

function itemIdChanged(rowId) {	
	setLineUnconfirmed(rowId);
	//reload the data in all the grids as itemid has changes.
	initiatedAjaxForPoLine = false;  // reload the grids after the item id has changed. reset the flag so that selectRow() method is fired.
	resetGridStatuses(rowId);
}

function resetGridStatuses(rowId) {		
	var materialType = gridCellValue(lineItemGrid,rowId,"isMaterialTypeItem");
	if (materialType == 'Y') {
		$('materialTypeItem').style.display = "none";		
		
		if ($v("lookAheadStatus") == "Yes" || $v("lookAheadLoaded") == "Yes" ) {
			$("lookAheadLoaded").value = "No";
			$("lookAheadStatus").value = "No";
			getLookAheads();
		}
		
		if ($v("itemNotesStatus") == "Yes" || $v("itemNotesLoaded") == "Yes" ) {
			$("itemNotesStatus").value = "No";
			$("itemNotesLoaded").value = "No";
			getItemNotes();
		}
		
		if ($v("partNotesStatus") == "Yes" || $v("partNotesLoaded") == "Yes" ) {
			$("partNotesStatus").value = "No";
			$("partNotesLoaded").value = "No";
			getPartNotes();
		}
		
		if ($v("specsStatus") == "Yes" || $v("specsLoaded") == "Yes" ) {
			$("specsStatus").value = "No";
			$("specsLoaded").value = "No";
			getSpecs();
		}
		
		if ($v("flowdownsStatus") == "Yes" || $v("flowdownsLoaded") == "Yes" ) {
			$("flowdownsStatus").value = "No";
			$("flowdownsLoaded").value = "No";
			getFlowdowns();
		}
		
		if ($v("msdsStatus") == "Yes" || $v("msdsLoaded") == "Yes" ) {
			$("msdsStatus").value = "No";
			$("msdsLoaded").value = "No";
			getMsds();
		}
		
		if ($v("associatedPrsStatus") == "Yes" || $v("associatedPrsLoaded") == "Yes" ) {
			$("associatedPrsStatus").value = "No";
			$("associatedPrsLoaded").value = "No";
			getAssociatedPrs();
		}
		
		if ($v("tcmBuysStatus") == "Yes" || $v("tcmBuysLoaded") == "Yes" ) {
			$("tcmBuysStatus").value = "No";
			$("tcmBuysLoaded").value = "No";
			getTcmBuys();
		}
		
		if ($v("clientBuysStatus") == "Yes" || $v("clientBuysLoaded") == "Yes" ) {
			$("clientBuysStatus").value = "No";
			$("clientBuysLoaded").value = "No";
			getClientBuys();
		}
	} else  {
		if ($('addChargesList') != null && $('addChargesDivBlock') != null ) {
			$('addChargesDivBlock').style.display = "none";		
			
			showAddChargesList();
			selectPoLinesForChargeType(rowId);
			$('addChargeLastConfirmed').innerHTML = gridCellValue(lineItemGrid, rowId,'lastConfirmed');
			$('addChargeSecondarySupplier').innerHTML = gridCellValue(lineItemGrid, rowId,'secondarySupplierAddress');
			$('addChargeItemDesc').innerHTML = gridCellValue(lineItemGrid, rowId,'itemDescription');					
		}
	}
	selectRow(rowId);
}

function populateUnitPrice(rowId) {
	var unitPriceDisp = gridCellValue(lineItemGrid,rowId,"unitPriceDisp"); 
	var split = unitPriceDisp.split(" ");
	if (split != null && split[0] != null && split[0] > 0 && isFloat(split[0]))
		lineItemGrid.cellById(rowId, lineItemGrid.getColIndexById("unitPrice")).setValue(split[0]);
	
	var currency = gridCellValue(lineItemGrid,rowId,"currency")
	lineCurrencyConversionRate(currency, rowId); // call this only if the financial approval conditions meet
	setLineUnconfirmed(rowId);
	changeTotalPrice(rowId);
}

function changeTotalPrice(rowId) {
	var lineqty = gridCellValue(lineItemGrid,rowId,'quantity'); 
    var lineunitprice = gridCellValue(lineItemGrid,rowId,'unitPrice');
    var totalPolinePrice = 0 ;
    if ((lineqty != null && lineqty != "") && (lineunitprice != null && lineunitprice != "")) {
    	totalPolinePrice = ((lineqty*1) * (lineunitprice*1));
    	totalPolinePrice = totalPolinePrice.toFixed(4);
    }    	
    lineItemGrid.cellById(rowId, lineItemGrid.getColIndexById("lineTotalPrice")).setValue(totalPolinePrice);
    lineItemGrid.cellById(rowId, lineItemGrid.getColIndexById("extendedPriceDisp")).setValue(totalPolinePrice);
    changePoTotalPriceInUSD();
    setLineUnconfirmed(rowId);
    updateTotalCost();
}

//loop through all the polines to find the usd total. multiply the amounts with the conversionrate
function changePoTotalPriceInUSD() {
    var currencySelectedForUSD = new Array();
    currencySelectedForUSD = getCurrency();

    var totalCurrPriceInUSD = new Array();
    totalCurrPriceInUSD = getTotalCurrPriceInUSD(currencySelectedForUSD);

    var totalPoPriceInUSD = 0;
    var val = 0;

    for(j=0; j<currencySelectedForUSD.length; j++)
    {
    	val = totalCurrPriceInUSD[j]*1;    	
    	totalPoPriceInUSD += val*1;  
    }
    totalPoPriceInUSD = totalPoPriceInUSD.toFixed(4);
    $('orderTotalInUSD').value = totalPoPriceInUSD;
    
    if ($v("poFinancialApprovalRequired") == "Y") {
    	var allowPOFinancialConfirmaiton = $v("allowPOFinancialConfirmaiton");
    	if (window['submitConfirmLink']) {		 
    	   $('submitConfirmLink').style.display = 'none';
    	}
    	if ($v("allowPOFinancialConfirmaiton") == 'Y') {
    		if (window['financialapprovallink'] ) {
	    		$('financialapprovallink').style.display = '';
    		}
	    }
    }
    else {
    	if (window['submitConfirmLink']) {		 
    	  $('submitConfirmLink').style.display = '';
    	}
    }
}

function getTotalCurrPriceInUSD(currencySelected) {
	var totPriceInUSDForCurrSelected = new Array();
	var nowofRows = (lineItemGrid.getRowsNum())*1;
	var ifound = 0;
	for(cnt = 0; cnt < currencySelected.length; cnt++) {
		var arrCurrency = currencySelected[cnt];
		var currLineTotalPrice = 0;
		for(j = 1; j <= nowofRows; j++)
	    {
			var currency = gridCellValue(lineItemGrid,j,'currency');
			var lineqty = gridCellValue(lineItemGrid,j,'quantity');	  
			var lineunitprice = gridCellValue(lineItemGrid,j,'unitPrice');
			var currencyConversionRate = gridCellValue(lineItemGrid,j,'currencyConversionRate');
			if (currency == arrCurrency) {
				currLineTotalPrice += ( (lineqty*1) * (lineunitprice*1) * (currencyConversionRate*1));	
				totPriceInUSDForCurrSelected[cnt] = currLineTotalPrice;	
			}					
	    }
	}
	return totPriceInUSDForCurrSelected;
}


function getCurrency() {
	var currencySelected = new Array();
	var nowofRows = (lineItemGrid.getRowsNum())*1;
	var ifound = 0;
	for(j=1;j<=nowofRows;j++)
    {
		var currency = gridCellValue(lineItemGrid,j,'currency')
		if (! contains(currencySelected, currency)) {
			currencySelected[ifound] = currency;
			ifound++;
		}					
    }
	return currencySelected;
}

function contains(a, obj) {
    for (var i = 0; i < a.length; i++) {
        if (a[i] === obj) {
            return true;
        }
    }
    return false;
}


function calculateSuppTotalPrice(rowId) {
	supplierQty = gridCellValue(lineItemGrid,rowId,"supplierQty");
	supplierUnitPrice = gridCellValue(lineItemGrid,rowId,"supplierUnitPrice");
    supplierExtPrice = gridCellValue(lineItemGrid,rowId,"supplierExtPrice");

    if (supplierQty.trim().length > 0 && supplierUnitPrice.trim().length > 0) {
    	var lineTotalPrice = (supplierQty * supplierUnitPrice);
		var currency = $v("currency");
		supplierExtPrice = (""+lineTotalPrice.toFixed(4)+" "+currency+"");
		lineItemGrid.cellById(rowId, lineItemGrid.getColIndexById("supplierExtPrice")).setValue(supplierExtPrice);
    } else {
    	supplierExtPrice = "";
    	lineItemGrid.cellById(rowId, lineItemGrid.getColIndexById("supplierExtPrice")).setValue(supplierExtPrice);
    }
}

function changeSuppTotalPrice(rowId) {
	calculateSuppTotalPrice(rowId);
    setLineUnconfirmed(rowId);
}

function secondarySupplierPNChanged(id) {
	if (poLineSelectedRowId == null || poLineSelectedRowId == "") {
		poLineSelectedRowId = 1;
	}
	if (!isEmptyString(id)) {
		lineItemGrid.cellById(poLineSelectedRowId, lineItemGrid.getColIndexById(id)).setValue($v(id));
		setLineUnconfirmed(poLineSelectedRowId);
	}
}

function checkShelfLife() {
	if (poLineSelectedRowId == null || poLineSelectedRowId == "") {
		poLineSelectedRowId = 1;
	}
	setLineUnconfirmed(poLineSelectedRowId);
	
	currShelfLife = $v('shelfLife');
    var totalofassociatedprs = 0;

    if (window['associatedPrsNewGrid'])
    	totalofassociatedprs = window['associatedPrsNewGrid'].getRowsNum();
    
    var remainingShelfLifePercentCount = 0;
    for( j=1; j<=totalofassociatedprs; j++ ) {
    	remainingShelfLifePercent = gridCellValue(window['associatedPrsNewGrid'],j,'remainingShelfLifePercent');
    	if(remainingShelfLifePercent != null && remainingShelfLifePercent != '') {
    		remainingShelfLifePercentCount++;
    	}
    }
    lineItemGrid.cellById(poLineSelectedRowId, lineItemGrid.getColIndexById("shelfLife")).setValue($v("shelfLife"));
    var po = $v('radianPo');
    var itemId = gridCellValue(lineItemGrid,poLineSelectedRowId,'itemId');
    if(currShelfLife != null && currShelfLife != '' && remainingShelfLifePercentCount > 0) {
    	
		var url = "checkshelflife.do?po="+po+"&shelfLife="+currShelfLife+ "&itemId="+itemId;
		var linestatus = gridCellValue(lineItemGrid,poLineSelectedRowId,'status'); //document.getElementById("linestatus"+rownum+"").value;
		var ammendment = gridCellValue(lineItemGrid,poLineSelectedRowId,'amendment'); //document.getElementById('ammendment'+rownum).value;
		
		if(linestatus == 'Confirmed' || (linestatus == 'Unconfirmed' &&  parseInt(ammendment) > 0))
			url += "&uAction=Type1";
		else
			url += "&uAction=Type2";		
		callToServer(url);
    }
}

function shelfLifeResults(results)
{
	if(results.rows.length > 0)  {
		var msg = "Remaining Shelf Life percent is less than what is required.\n\n";	
		
		for(var i = 0; i < results.rows.length; i++)
			msg += "Please contact CSR ("+results.rows[i].data[0]+") for MR "+results.rows[i].data[1]+"-"+results.rows[i].data[2]+" to adjust the customer requirement.\n\n";    

		msg += "Not doing so will create a duplicate buy order.\nDo you want to continue?";
		if( !confirm(msg)) {
			document.getElementById('shelfLife').value = $('slHidden').value;
	    } else {
	    	$('slHidden').value = currShelfLife;
	    }
		currShelfLife  = "Error";
	} else {
 		$('slHidden').value = currShelfLife;
	    currShelfLife  = "Error";
	}
}

function poLineDateChanged()
{
	var noOfRows = (lineItemGrid.getRowsNum())*1;
	if (noOfRows == 0)
		return;
    for(rowId = 1; rowId <= noOfRows; rowId++)
    {
    	var oldNeedDate = gridCellValue(lineItemGrid,rowId,"oldNeedDate"); 
    	var oldPromisedDate = gridCellValue(lineItemGrid,rowId,"oldPromisedDate");
    	var oldProjectedDate = gridCellValue(lineItemGrid,rowId,"oldProjectedDate");

    	var currNeedDate = gridCellValue(lineItemGrid,rowId,"needDate"); 
    	var currPromisedDate = gridCellValue(lineItemGrid,rowId,"promisedDate");
    	var currProjectedDate = gridCellValue(lineItemGrid,rowId,"projectedDate");
    	
    	if (oldNeedDate != currNeedDate){
    		setLineUnconfirmed(rowId);
    		break;
    	} else if (oldPromisedDate != currPromisedDate){
    		setLineUnconfirmed(rowId);
    		break;
    	} else if (oldProjectedDate != currProjectedDate){
    		setLineUnconfirmed(rowId);
    		break;
    	}
    }
}

function poLineDateChangedRowLevel(rowId)
{	
	var oldNeedDate = gridCellValue(lineItemGrid,rowId,"oldNeedDate"); 
	var oldPromisedDate = gridCellValue(lineItemGrid,rowId,"oldPromisedDate");
	var oldProjectedDate = gridCellValue(lineItemGrid,rowId,"oldProjectedDate");

	var currNeedDate = gridCellValue(lineItemGrid,rowId,"needDate"); 
	var currPromisedDate = gridCellValue(lineItemGrid,rowId,"promisedDate");
	var currProjectedDate = gridCellValue(lineItemGrid,rowId,"projectedDate");
	
	if (oldNeedDate != currNeedDate){
		setLineUnconfirmed(rowId);    		
	} else if (oldPromisedDate != currPromisedDate){
		setLineUnconfirmed(rowId);    		
	} else if (oldProjectedDate != currProjectedDate){
		setLineUnconfirmed(rowId); 
	} 
}

function lineCurrencyConversionRate(val, rowId)
{	
	//alert(val + " and " + rowId);
	if(val == 'USD') {
		//setConversionRate(1);
		lineItemGrid.cellById(rowId, lineItemGrid.getColIndexById("currencyConversionRate")).setValue(1);
	} else {
		j$.ajax({
		    type: "POST",
		    url: "/tcmIS/supply/purchaseorder.do",
			data: {action: 'getRate',
					currentCurrency: val,
					radianPo: $v('radianPo')
			      },
			async: false,
		    success: setConversionRate
		  });
	}
}

function setConversionRate(cnvs) {
	if (poLineSelectedRowId == null || poLineSelectedRowId == "") {
		poLineSelectedRowId = 1;
	}
	lineItemGrid.cellById(poLineSelectedRowId, lineItemGrid.getColIndexById("currencyConversionRate")).setValue(cnvs);
}


function headerChanged() {
	headerInfoChanged = true;
}

function saveHeaderInformation() {
	showTransitWin("");	
	var varConsignedPo = "";
	if ($("consignedPo").checked)
		varConsignedPo = 'y';
	else 
		varConsignedPo = 'n';
	
	j$.ajax({
		 type: "POST",
		 url: "/tcmIS/supply/purchaseorder.do",
		 data: {radianPo: $v("radianPo"),
			 	branchPlant: $v("branchPlant"),		 		
		 		action: "saveOnlyHeader",
		 		critical: $v("critical"),
		 		freightOnBoard: $("freightOnBoard").options[$("freightOnBoard").selectedIndex].value,
		 		paymentTerms: $("paymentTerms").options[$("paymentTerms").selectedIndex].value,
		 		carrier: $v("carrier"),		 		
		 		supplier: $v("supplier"),
		 		buyer: $v("buyer"),
		 		shipToCompanyId: $v("shipToCompanyId"),
		 		shipToFacilityId: $v("shipToFacilityId"),
		 		shipToLocationId: $v("shipToLocationId"),
		 		customerPo: $v("customerPo"),
		 		dateSent: $v("dateSent"),
		 		dateAccepted: $v("dateAccepted"),
		 		qualificationLevel: $v("qualificationLevel"),
		 		inventoryGroup: $v("inventoryGroup"),
		 		consignedPo: varConsignedPo,
		 		supplierContactId: $v("supplierContactId")
		 },
		 success: headerSaveComplete
   });
}

function headerSaveComplete(msg) {
	closeTransitWin();
	 if(msg != "OK")
		 alert(msg);
	 else
	 {
		 alert("Header information saved successfully!");
		 $("newUnsavedPo").value = "N";
	 }
}

function saveLookaheadInfo(rowNumber) {
	showTransitWin("");	
	clearGridForms();
	
	if (window['lookAheadGrid']) 
		window['lookAheadGrid'].parentFormOnSubmit();
	$("action").value="saveOnlyLookAhead";
	if (poLineSelectedRowId == null || poLineSelectedRowId == "") {
		poLineSelectedRowId = 1;
	}
	$("poLine").value = gridCellValue(lineItemGrid,poLineSelectedRowId,"poLine");
	$("rowNumber").value = gridCellValue(lineItemGrid,poLineSelectedRowId,"rowNumber");
	
	var frmdt = serializeIncludingDisabled(); // serializes the form's elements.
	//window.document.getElementById("lookAheadTransitPage").style["display"] = "";	
    j$.ajax({
		 type: "POST",
		 url: "/tcmIS/supply/purchaseorder.do",
		 data: frmdt, 
		 success: lookaheadSaveComplete
    }); 
    
}

function lookaheadSaveComplete(msg) {
	closeTransitWin();
	if (poLineSelectedRowId == null || poLineSelectedRowId == "") {
		poLineSelectedRowId = 1;
	}
	clearGridForms();
	 if(msg != "OK") {
		 alert(msg);		 
	 } 
	 else
	 {
		 $("lookAheadStatus").value = 'No';
		 $("lookAheadLoaded").value = 'No';
		 getLookAheads();
		 lineItemGrid.cellById(previousRowId, lineItemGrid.getColIndexById("lookAheadChanged")).setValue("N");
		 alert("Look Ahead information saved successfully for PO line " + gridCellValue(lineItemGrid,poLineSelectedRowId,"poLineNumber"));
	 }	 
}

function savePoLineChangesFromLink() {
	if (lineItemGrid.getRowsNum() == 0) {
		alert(messagesData.pleaseadd.replace("{0}", messagesData.line));
		return;
	}
	if (poLineSelectedRowId == null || poLineSelectedRowId == "") {
		poLineSelectedRowId = 1;
	}
	savePoLineChanges(poLineSelectedRowId);
}

var poLineNumberSaved = 0;

function savePoLineChanges(rowNumber) {
	showTransitWin("");
	clearGridForms();
	var type  =  gridCellValue(lineItemGrid, rowNumber,'isMaterialTypeItem');

	if (type == "N") {
		if (window['chargesListGrid'])
			chargesListGrid.parentFormOnSubmit();
	} else {    		
		if (window['flowdownsGrid'])
			flowdownsGrid.parentFormOnSubmit();
		if (window['lookAheadGrid']) 
			lookAheadGrid.parentFormOnSubmit();		
		if (window['specsGrid'])
			specsGrid.parentFormOnSubmit();
	}
	
	var status =  gridCellValue(lineItemGrid, rowNumber,'status');
	if (status == 'Remove') {
		previousRowId = 0; //set to 0 to avoid doing operations on deleted line
		$("action").value="deletePoLine";
	} else {
		poLineDateChangedRowLevel(rowNumber);
		$("action").value="saveOnePoLine";
	}
	
	lineItemGrid.parentFormOnSubmit();
	
	$("poLine").value = gridCellValue(lineItemGrid,rowNumber,"poLine");
	$("rowNumber").value = gridCellValue(lineItemGrid,rowNumber,"rowNumber");
	
	var frmdt =  serializeIncludingDisabled(); // serializes the form's elements.
	poLineNumberSaved = rowNumber;
    j$.ajax({
		 type: "POST",
		 async: false,
		 url: "/tcmIS/supply/purchaseorder.do",
		 data: frmdt, 
		 success: savePoLineChangesComplete
    });
}

function savePoLineChangesComplete(msg) {
	try {
		closeTransitWin();
		if (poLineNumberSaved == null || poLineNumberSaved == "" || poLineNumberSaved == 0) {
			poLineNumberSaved = previousRowId;
		}
		var status  =  gridCellValue(lineItemGrid, poLineNumberSaved,'status');
		if(msg != "OK") {
			alert(msg);
			delaySubmissionLine = -1;
			//if save procedure failed and the status is "Draft", make no further change (i.e. retain unsaved line's data)
			if (status == "Draft") 
				return;
		} else {
			alert("PO Line information saved successfully for line " + gridCellValue(lineItemGrid,poLineNumberSaved,"poLineNumber"));
			//if save procedure succeeds, reset flags and move saved data to storage columns 
			//reset all the flags for changes to N
			lineItemGrid.cellById(poLineNumberSaved, lineItemGrid.getColIndexById("lineChange")).setValue("N");
			lineItemGrid.cellById(poLineNumberSaved, lineItemGrid.getColIndexById("lookAheadChanged")).setValue("N");
			lineItemGrid.cellById(poLineNumberSaved, lineItemGrid.getColIndexById("specChanged")).setValue("N");
			lineItemGrid.cellById(poLineNumberSaved, lineItemGrid.getColIndexById("flowdownChanged")).setValue("N");
			lineItemGrid.cellById(poLineNumberSaved, lineItemGrid.getColIndexById("chargeListChanged")).setValue("N");
			
			//set the data into the linegrid
			lineItemGrid.cellById(poLineNumberSaved, lineItemGrid.getColIndexById("lineNotes")).setValue($v('lineNotes'));
			lineItemGrid.cellById(poLineNumberSaved, lineItemGrid.getColIndexById("deliveryComments")).setValue($v('deliveryComments'));
			
			var needDate = gridCellValue(lineItemGrid,poLineNumberSaved,"needDate"); 
			var promisedDate = gridCellValue(lineItemGrid,poLineNumberSaved,"promisedDate");
			var projectedDate = gridCellValue(lineItemGrid,poLineNumberSaved,"projectedDate");
			var itemId = gridCellValue(lineItemGrid,poLineNumberSaved,"itemId"); 
			var quantity = gridCellValue(lineItemGrid,poLineNumberSaved,"quantity");
			var unitPriceDisp = gridCellValue(lineItemGrid,poLineNumberSaved,"unitPriceDisp");
			var currency = gridCellValue(lineItemGrid,poLineNumberSaved,"currency");
			var supplierPn = gridCellValue(lineItemGrid,poLineNumberSaved,"supplierPn");
			var dpas = gridCellValue(lineItemGrid,poLineNumberSaved,"dpas");
			var shelfLife = gridCellValue(lineItemGrid,poLineNumberSaved,"shelfLife");
				
			lineItemGrid.cellById(poLineNumberSaved, lineItemGrid.getColIndexById("oldNeedDate")).setValue(needDate);
			lineItemGrid.cellById(poLineNumberSaved, lineItemGrid.getColIndexById("oldPromisedDate")).setValue(promisedDate);
			lineItemGrid.cellById(poLineNumberSaved, lineItemGrid.getColIndexById("oldProjectedDate")).setValue(projectedDate);
			lineItemGrid.cellById(poLineNumberSaved, lineItemGrid.getColIndexById("oldItemId")).setValue(itemId);
			lineItemGrid.cellById(poLineNumberSaved, lineItemGrid.getColIndexById("oldQuantity")).setValue(quantity);
			lineItemGrid.cellById(poLineNumberSaved, lineItemGrid.getColIndexById("oldUnitPriceDisp")).setValue(unitPriceDisp);
			lineItemGrid.cellById(poLineNumberSaved, lineItemGrid.getColIndexById("oldCurrency")).setValue(currency);
			lineItemGrid.cellById(poLineNumberSaved, lineItemGrid.getColIndexById("oldSupplierPn")).setValue(supplierPn);
			lineItemGrid.cellById(poLineNumberSaved, lineItemGrid.getColIndexById("oldDpas")).setValue(dpas);
			lineItemGrid.cellById(poLineNumberSaved, lineItemGrid.getColIndexById("oldShelfLife")).setValue(shelfLife);
			setGridCellValue(lineItemGrid, poLineNumberSaved, "oldAmendment", gridCellValue(lineItemGrid, poLineNumberSaved, "amendment"));
		}
		
		clearGridForms();
		var type  =  gridCellValue(lineItemGrid, poLineNumberSaved,'isMaterialTypeItem');
		//if currently in the submitPurchaseOrder loop, skip reloading since it will remove any unsaved data
		if (delaySubmissionLine !== -1)
			submitPurchaseOrder(delaySubmissionAction);
		else if ($v("action") == "deletePoLine" || type == "N" || status == "Draft")
			reloadPoLine();
	} catch (e) {
		alert(messagesData.genericerror);
		delaySubmissionLine = -1;
	}
}

function saveMsdsInfo()
{
	showTransitWin("");	
	if (poLineSelectedRowId == null || poLineSelectedRowId == "") {
		poLineSelectedRowId = 1;
	}
	clearGridForms();
	//msds info to be saved is the msdshceck box. if it is checked or unchecked.
	$("action").value="saveMsdsInfo";
	$("poLine").value = gridCellValue(lineItemGrid,poLineSelectedRowId,"poLine");
	$("rowNumber").value = gridCellValue(lineItemGrid,poLineSelectedRowId,"rowNumber");
	lineItemGrid.parentFormOnSubmit();
	var frmdt =  serializeIncludingDisabled(); // serializes the form's elements.
		
    j$.ajax({
		 type: "POST",
		 url: "/tcmIS/supply/purchaseorder.do",
		 data: frmdt, 
		 success: msdsSaveComplete
    });
}

function msdsSaveComplete(msg) {
	closeTransitWin();
	if (poLineSelectedRowId == null || poLineSelectedRowId == "") {
		poLineSelectedRowId = 1;
	}
	 if(msg != "OK") {
		 alert(msg);		 
	 } 
	 else
	 {
		 if (j$("#msdsRequestedDate").prop('checked'))		 
			 lineItemGrid.cellById(poLineSelectedRowId, lineItemGrid.getColIndexById("msdsRequestedDate")).setValue("Y");
		 else
			 lineItemGrid.cellById(poLineSelectedRowId, lineItemGrid.getColIndexById("msdsRequestedDate")).setValue("N");
		 alert("MSDS information saved successfully for line " + gridCellValue(lineItemGrid,poLineSelectedRowId,"poLineNumber"));
		 lineItemGrid.cellById(poLineSelectedRowId, lineItemGrid.getColIndexById("lineChange")).setValue("N"); //TODO:Check if we need this?
	 }
	 clearGridForms();
}

function saveSpecsInfo()
{
	showTransitWin("");
	if (poLineSelectedRowId == null || poLineSelectedRowId == "") {
		poLineSelectedRowId = 1;
	}
	clearGridForms();
	
	if (window['specsGrid'])		
		specsGrid.parentFormOnSubmit();
	$("poLine").value = gridCellValue(lineItemGrid,poLineSelectedRowId,"poLine");
	$("rowNumber").value = gridCellValue(lineItemGrid,poLineSelectedRowId,"rowNumber");
	lineItemGrid.parentFormOnSubmit();
	$("action").value="saveOnlySpecs";
	var frmdt =  serializeIncludingDisabled(); // serializes the form's elements.
		
    j$.ajax({
		 type: "POST",
		 url: "/tcmIS/supply/purchaseorder.do",
		 data: frmdt, 
		 success: specsSaveComplete
    });
}

function specsSaveComplete(msg) {
	closeTransitWin();
	if (poLineSelectedRowId == null || poLineSelectedRowId == "") {
		poLineSelectedRowId = 1;
	}
	 if(msg != "OK") {
		 alert(msg);		 
	 } else {
		 $("specsStatus").value = 'No';
		 $("specsLoaded").value = 'No';
		 getSpecs();
		 lineItemGrid.cellById(poLineSelectedRowId, lineItemGrid.getColIndexById("specChanged")).setValue("N");
		 lineItemGrid.cellById(poLineSelectedRowId, lineItemGrid.getColIndexById("lineChange")).setValue("N"); //TODO:Check if we need this?
		 alert("Specs information saved successfully for line " + gridCellValue(lineItemGrid,poLineSelectedRowId,"poLineNumber"));
	 }
	 clearGridForms();
}

function saveFlowdownsInfo()
{
	showTransitWin("");	
	if (poLineSelectedRowId == null || poLineSelectedRowId == "") {
		poLineSelectedRowId = 1;
	}
	clearGridForms();
	//Flowdowns info to be saved is the msdshceck box. if it is checked or unchecked.
	if (window['flowdownsGrid'])
		flowdownsGrid.parentFormOnSubmit();
	$("poLine").value = gridCellValue(lineItemGrid,poLineSelectedRowId,"poLine");
	$("rowNumber").value = gridCellValue(lineItemGrid,poLineSelectedRowId,"rowNumber");
	lineItemGrid.parentFormOnSubmit();
	$("action").value="saveOnlyFlowdowns";
	var frmdt =  serializeIncludingDisabled(); // serializes the form's elements.
		
    j$.ajax({
		 type: "POST",
		 url: "/tcmIS/supply/purchaseorder.do",
		 data: frmdt, 
		 success: flowdownsSaveComplete
    });
}

function flowdownsSaveComplete(msg) {
	closeTransitWin();
	if (poLineSelectedRowId == null || poLineSelectedRowId == "") {
		poLineSelectedRowId = 1;
	}
	 if(msg != "OK") {
		 alert(msg);		 
	 } else {
		 $("flowdownsStatus").value = 'No';
		 $("flowdownsLoaded").value = 'No';
		 getFlowdowns(poLineSelectedRowId);
         lineItemGrid.cellById(poLineSelectedRowId, lineItemGrid.getColIndexById("flowdownChanged")).setValue("N");
         lineItemGrid.cellById(poLineSelectedRowId, lineItemGrid.getColIndexById("lineChange")).setValue("N"); //TODO:Check if we need this?
		 alert("Flowdowns information saved successfully for line " + gridCellValue(lineItemGrid,poLineSelectedRowId,"poLineNumber"));
	 }
	 clearGridForms();
}

function deleteSelectedPOLine()
{
	showTransitWin("");	
	if (poLineSelectedRowId == null || poLineSelectedRowId == "") {
		poLineSelectedRowId = 1;
	}
	clearGridForms();
	previousRowId = 0; //set to 0 to avoid doing operations on deleted line
	$("action").value="deletePoLine";
	$("poLine").value = gridCellValue(lineItemGrid,poLineSelectedRowId,"poLine");
	$("rowNumber").value = gridCellValue(lineItemGrid,poLineSelectedRowId,"rowNumber");
	lineItemGrid.parentFormOnSubmit();
	var frmdt =  serializeIncludingDisabled(); // serializes the form's elements.
		
    j$.ajax({
		 type: "POST",
		 url: "/tcmIS/supply/purchaseorder.do",
		 data: frmdt, 
		 success: deleteSelectedPOLineComplete
    });
}

function deleteSelectedPOLineComplete(msg) {
	closeTransitWin();
	clearGridForms();
	 if(msg != "OK") {
		 alert(msg);		 
	 } else {
		 reloadPoLine(); //call this method to refresh the grid
		 alert("Selected PO Line has been successfully deleted!");
	 }
}

function performFinancialApproval()
{
	showTransitWin("");
	j$.ajax({
		 type: "POST",
		 url: "/tcmIS/supply/purchaseorder.do",
		 data: {radianPo: $v("radianPo"),				
		 		action: "financialApproval"
		 },
		 success: financialApprovalComplete
   });
}

function financialApprovalComplete(msg) {
	//alert(msg);
	closeTransitWin();
	clearGridForms();
	 if(msg == "Error") {
		 alert(messagesData.genericerror);		 
	 } else {		 
		 //document.getElementById("submitConfirmLink").style["display"] = "";
		 document.getElementById("financialapprovallink").style["display"] = "none";
		 alert(msg);
	 }
}

function reloadPoLine() {
	showTransitWin("");
	callToServer("purchaseorder.do?action=reloadpoline&po="+$v("radianPo")+"&radianPo="+$v("radianPo"));
}

function reloadPoLineData(tmplineItemJsonData, tmpNewChargeLineItemJsonData) {
	try {
		lineItemJsonData = tmplineItemJsonData;
		initializeLineItemGrid();
		if (delaySubmissionLine === -1) {
			poLineSelectedRowId = 1;
			lineItemGrid.selectRowById(poLineSelectedRowId, null, true, true);
		}
		chargesListJsonData = tmpNewChargeLineItemJsonData;
		//initializeChargePoLineGrid();
		closeTransitWin();
	} catch (e) {
		alert(messagesData.genericerror);
		delaySubmissionLine = -1;
	}
	
	if (delaySubmissionLine !== -1)
		submitPurchaseOrder(delaySubmissionAction);
}

function clearGridForms() {
	lineItemGrid.formInputs = [];  // we need this hack as the dhtmlxgrid is keeping track of the elements created by parentFormOnSubmit() in this array. after clearing from the page clear from this array as well
	if (window['chargesListGrid'])
		chargesListGrid.formInputs = [];
	if (window['flowdownsGrid'])
		flowdownsGrid.formInputs = [];
	if (window['lookAheadGrid']) 
		lookAheadGrid.formInputs = [];	
	if (window['specsGrid'])
		specsGrid.formInputs = [];
	
	j$("input[name^='flowdownsBean']").remove();
	j$("input[name^='lineItemBean']").remove();
	j$("input[name^='specsBean']").remove();
	j$("input[name^='lookAheadBean']").remove();
	j$("input[name^='chargesListBean']").remove();
}

function chargeGridUpdated(rowId, colId) {
	if (poLineSelectedRowId == null || poLineSelectedRowId == "") {
		poLineSelectedRowId = 1;
	}
	setLineUnconfirmed(poLineSelectedRowId);	
	window['chargesListGrid'].cellById(rowId, window['chargesListGrid'].getColIndexById("changed")).setValue("Y");
	lineItemGrid.cellById(poLineSelectedRowId, lineItemGrid.getColIndexById("chargeListChanged")).setValue("Y");
}

function flowdownsGridUpdated(rowId, colId) {
	if (poLineSelectedRowId == null || poLineSelectedRowId == "") {
		poLineSelectedRowId = 1;
	}
	setLineUnconfirmed(poLineSelectedRowId);
	window['flowdownsGrid'].cellById(rowId, window['flowdownsGrid'].getColIndexById("changed")).setValue("Y");
	lineItemGrid.cellById(poLineSelectedRowId, lineItemGrid.getColIndexById("flowdownChanged")).setValue("Y");
}

function specGridDataUpdated(rowId, colId) {
	if (poLineSelectedRowId == null || poLineSelectedRowId == "") {
		poLineSelectedRowId = 1;
	}
	setLineUnconfirmed(poLineSelectedRowId);
	window['specsGrid'].cellById(rowId, window['specsGrid'].getColIndexById("changed")).setValue("Y");
	lineItemGrid.cellById(poLineSelectedRowId, lineItemGrid.getColIndexById("specChanged")).setValue("Y");
}

//change to the lookahead fields does not cause a change to the po lines. i.e. the line is set as unconfirmed
function lookAheadGridUpdated(rowId, colId) {
	if (poLineSelectedRowId == null || poLineSelectedRowId == "") {
		poLineSelectedRowId = 1;
	}
	var lookAheadDays = gridCellValue(window['lookAheadGrid'],rowId,'lookAheadDays');
	if (lookAheadDays.length > 0 && lookAheadDays*1 == 0) {
	     alert("Please enter a value greater than Zero.");	    
	     window['lookAheadGrid'].cellById(rowId, window['lookAheadGrid'].getColIndexById("lookAheadDays")).setValue("");
	}
	window['lookAheadGrid'].cellById(rowId, window['lookAheadGrid'].getColIndexById("changed")).setValue("Y");
	lineItemGrid.cellById(poLineSelectedRowId, lineItemGrid.getColIndexById("lookAheadChanged")).setValue("Y");
}

function getBuyOrders() {
	var shipToId  =  $v("shipToLocationId");
	shipToId = shipToId.replace(/&/gi, "%26");
	shipToId = shipToId.replace(/#/gi, "%23");
	var invenGrp = $v( "inventoryGroup" );
	//var validBpo  =  document.getElementById("validBpo");
	
	var alertMsg = "";
	if (isEmptyString(shipToId))
		alertMsg += "\n" + messagesData.shipto;
	if (isEmptyString(invenGrp))
		alertMsg += "\n" + messagesData.inventorygroup;
	if (isEmptyString($v("opsEntityId")))
		alertMsg += "\n" + messagesData.operatingentity;
	if (isEmptyString($v("supplier")))
		alertMsg += "\n" + messagesData.supplier;
	
	if (alertMsg.length > 0) {
		alert(messagesData.validvalues + alertMsg);
		return;
	}
	
	if ($v("newUnsavedPo") == "Y")
		alert(messagesData.saveheadertocontinue);
	else {
		loc = "/tcmIS/supply/editassociatedprsmain.do?hubName="+$v("branchPlant");

	    if (!isEmptyString($v("radianPo")))
	    {
	    	//this disable links unless page is reloaded - doesn't make sense
	    	/*document.getElementById("addlineitemlink").style["display"] = "none";
	    	document.getElementById("addlinechargelink").style["display"] = "none";
	    			 
	    	if ($("editAssociatedPrs"))
	    		document.getElementById("editAssociatedPrs").style["display"] = "none";*/
	    	
	        loc = loc + "&radianPo=" + $v("radianPo");
	        loc = loc  + "&shipToLocationId=" + shipToId;
	        loc = loc + "&shipToCompanyId=" + $v("shipToCompanyId");
	        loc = loc + "&buyerId=" + $v("buyer");
	        loc = loc + "&userAction=addBuyOrdersToPO";
	        loc = loc + "&uAction=addBuyOrdersToPO";
	        loc = loc + "&inventoryGroup=" + invenGrp;
	        loc = loc + "&attachedPr=" + $v("attachedPr");

	        openWinGeneric(loc,"addBuyOrdersToPO","900","600","yes","40","40");
	    }
	 }
}

function removeLine() 
{
	//document.getElementById("addbuyorderslink").style["display"] = "none";
	var noofRows = (lineItemGrid.getRowsNum())*1;	
	if (poLineSelectedRowId == null || poLineSelectedRowId == "") {
		poLineSelectedRowId = 1;
	}
    if (noofRows > 0)
    {	
	    var selectedRowStatus = gridCellValue(lineItemGrid,poLineSelectedRowId,"status");
		var selectedOriginalRowStatus = gridCellValue(lineItemGrid,poLineSelectedRowId,"poLineStatus");
		
	    var removedraftline = 0;
	    if (selectedRowStatus == "Confirmed" || selectedRowStatus == "Closed") {
	    	alert("A confirmed PO line cannot be deleted");
	    } else if (selectedRowStatus != "Remove") {   
		    setRemovedRowColor(poLineSelectedRowId);
		    if ($("submitConfirmLink"))
		    	document.getElementById("submitConfirmLink").style["display"] = "none";		    
	    	lineItemGrid.cellById(poLineSelectedRowId, lineItemGrid.getColIndexById("status")).setValue("Remove");	        
	    }
    }
}

function unRemoveLine()
{
    var noofRows = (lineItemGrid.getRowsNum())*1;	
	if (poLineSelectedRowId == null || poLineSelectedRowId == "") {
		poLineSelectedRowId = 1;
	}
    if (noofRows > 0)
    {
    	var selectedRowStatus = gridCellValue(lineItemGrid,poLineSelectedRowId,"status");
		var selectedOriginalRowStatus = gridCellValue(lineItemGrid,poLineSelectedRowId,"poLineStatus");
	    if (selectedRowStatus == "Remove") {
		    lineItemGrid.cellById(poLineSelectedRowId, lineItemGrid.getColIndexById("status")).setValue("Unconfirmed");
	        setRowColor(poLineSelectedRowId);	        
	    }
    }
}

//set the grid row's color to even or odd based on the passed row
function setRowColor(row) {
	lineItemGrid.rowsAr[row].className = row % 2 == 0 ? "odd_haas" : "even_haas";
}

function setRemovedRowColor(row) { //#3b3b3b
	lineItemGrid.rowsAr[row].className = "grid_lightgray";
}

function checkForCrediCartConf()
{
    var paymentterms  =  $("paymentTerms").options[$("paymentTerms").selectedIndex].value;
    var opsEntityId  =  $v("opsEntityId");
    var allowPOCreditConfirmaiton  =  $v("allowPOCreditConfirmaiton");

    if ($("submitConfirmLink"))
    	if (opsEntityId == "HAASTCMUSA" && paymentterms == "Credit Card") {
    	if (allowPOCreditConfirmaiton == "false") {
    		document.getElementById("submitConfirmLink").style["display"] = "none";
    	}
		} else {
			document.getElementById("submitConfirmLink").style["display"] = "";
		}
    headerChanged();
}

function enterExpediteNotes()
{
    var po  =  $v("radianPo"); 	
    var HubName  =  $v("branchPlant");
    if (poLineSelectedRowId == null || poLineSelectedRowId == "") {
		poLineSelectedRowId = 1;
	}
	var itemID = gridCellValue(lineItemGrid,poLineSelectedRowId,"itemId");
	var poLine = gridCellValue(lineItemGrid,poLineSelectedRowId,"poLine");

	if ( (po.length > 0) && (itemID.length > 0 && itemID*1 >0) && (HubName.length > 0 && HubName*1 >0))
    {
        loc="/tcmIS/supply/newpoexpediting.do?action=searchnewpoexpedite&radianPo="+po+"&poLine="+poLine;
        openWinGeneric(loc,"enterExpediteNotesin"+po.replace('.','a')+"","900","400","yes","100","100")
    }
}

var legendArea = "showLegendArea";
var legendSize = 180;

function showLegend() {
	var showLegendArea = document.getElementById(legendArea);
	showLegendArea.style.display = "";
	showLegendArea.style.position="absolute";

	var dhxWins = new dhtmlXWindows();
	dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	var legendWin = dhxWins.createWindow(messagesData.showlegend, 0, 0, 400, legendSize);
	legendWin.setText(messagesData.showlegend);
	legendWin.clearIcon(); // hide icon
	legendWin.denyResize(); // deny resizing
	legendWin.denyPark(); // deny parking
	legendWin.attachObject(legendArea);
	legendWin.keepInViewport(true);	
	legendWin.center();
	legendWin.focus();
	legendWin.bringToTop();
	legendWin.attachEvent("onClose", function(legendWin) {
		legendWin.hide();
	});	
}

function addSpecialCharge() {
	var invalidValues = "";
	if ($v("radianPo").trim().length == 0)
		invalidValues += messagesData.po;
	if ($v("supplier").trim().length == 0) {
		if (invalidValues.length != 0)
			invalidValues += "\n";
		invalidValues += messagesData.supplier;
	}
	
	if (invalidValues.length != 0) {
		alert(messagesData.validvalues + "\n\n" + invalidValues);
		return;
	}
	
	showTransitWin("");
	j$.ajax({
		 type: "POST",
		 url: "/tcmIS/supply/purchaseorder.do",
		 data: {
			radianPo: $v("radianPo"),
		 	action: "addSpecialCharge",	 		
		 	supplier: $v("supplier")
		 },
		 success: addSpecialChargeComplete
   });
}

function addSpecialChargeComplete(msg) {
	closeTransitWin();
	if(msg != "OK")
		alert(msg);
	else {
		$("addSpecialChargeLink").style.display = "none";
		$("bulkPoText").innerHTML = messagesData.yes;
		alert(messagesData.proceduresuccessful);
	}
}

function updateLineAmendment(rowNumber) {
	try {
		showTransitWin("");
		clearGridForms();
		poLineDateChangedRowLevel(rowNumber);
		$("action").value="updateAmendment";
		lineItemGrid.parentFormOnSubmit();
		
		$("poLine").value = gridCellValue(lineItemGrid,rowNumber,"poLine");
		$("rowNumber").value = gridCellValue(lineItemGrid,rowNumber,"rowNumber");
		
		var frmdt =  serializeIncludingDisabled(); // serializes the form's elements.
		poLineNumberSaved = rowNumber;
	    j$.ajax({
			 type: "POST",
			 async: false,
			 url: "/tcmIS/supply/purchaseorder.do",
			 data: frmdt, 
			 success: updateLineAmendmentComplete
	    });
	} catch (e) {
		//in case of unexpected error, change the flag to prevent it from blocking submitPurchaseOrder
		alert(messagesData.genericerror);
		delaySubmissionLine = -1;
	}
}

function updateLineAmendmentComplete(msg) {
	try {
		closeTransitWin();
		if (msg === "OK") {
			alert("PO Line information saved successfully for line " + gridCellValue(lineItemGrid, poLineNumberSaved, "poLineNumber"));
			
			setGridCellValue(lineItemGrid, poLineNumberSaved, "oldNeedDate", gridCellValue(lineItemGrid, poLineNumberSaved, "needDate"));
			setGridCellValue(lineItemGrid, poLineNumberSaved, "oldPromisedDate", gridCellValue(lineItemGrid, poLineNumberSaved, "promisedDate"));
			setGridCellValue(lineItemGrid, poLineNumberSaved, "oldProjectedDate", gridCellValue(lineItemGrid, poLineNumberSaved, "projectedDate"));
			setGridCellValue(lineItemGrid, poLineNumberSaved, "oldItemId", gridCellValue(lineItemGrid, poLineNumberSaved, "itemId"));
			setGridCellValue(lineItemGrid, poLineNumberSaved, "oldQuantity", gridCellValue(lineItemGrid, poLineNumberSaved, "quantity"));
			setGridCellValue(lineItemGrid, poLineNumberSaved, "oldUnitPriceDisp", gridCellValue(lineItemGrid, poLineNumberSaved, "unitPriceDisp"));
			setGridCellValue(lineItemGrid, poLineNumberSaved, "oldCurrency", gridCellValue(lineItemGrid, poLineNumberSaved, "currency"));
			setGridCellValue(lineItemGrid, poLineNumberSaved, "oldAmendment", gridCellValue(lineItemGrid, poLineNumberSaved, "amendment"));
			setGridCellValue(lineItemGrid, poLineNumberSaved, "lineChange", "N");
			
			if (delaySubmissionLine !== -1)
				submitPurchaseOrder(delaySubmissionAction);
		} else {
			alert(msg);
			delaySubmissionLine = -1;
		}
	} catch (e) {
		//in case of unexpected error, change the flag to prevent it from blocking submitPurchaseOrder
		alert(messagesData.genericerror);
		delaySubmissionLine = -1;
	}
}

//serialize() method of jQuery disregards disabled fields while creating URL-style input for ajax call.
//this method temporarily enables those fields to be included in the input, then resetting the disabled status after done creating input.
function serializeIncludingDisabled() {
	//regex from jQuery to search for relevant elements
	var rinput = /^(?:color|date|datetime|datetime-local|email|hidden|month|number|password|range|search|tel|text|time|url|week)$/i,
	rselectTextarea = /^(?:select|textarea)/i;
	
	var allForms = j$("form");
	var allDisabledElements = new Array();
	//going through all forms in this page and enabling any fields that has name attribute, is disabled, and matches the criterias for being relevant
	for (var i = 0; i < allForms.length; i++)
		for (var j = 0; j < allForms[i].length; j++) {
			var curField = allForms[i][j];
			if (curField.name 
					&& curField.disabled 
					&& (curField.checked 
							|| rselectTextarea.test(curField.nodeName) 
							|| rinput.test(curField.type))) {
				allDisabledElements.push(curField);
				curField.disabled = false;
			}
		}
	// serializes the form's elements.
	var frmdt =  j$("form").serialize();
	//resetting disabled status of changed fields
	for (var i = 0; i < allDisabledElements.length; i++)
		allDisabledElements[i].disabled = true;
	
	return frmdt;
}

function updateTotalCost() {
	if (isEmptyString($v("totalCostCurrencyId"))) {
		alert(messagesData.selectavalid.replace("{0}", messagesData.currency));
		return;
	}
	
	var lineGridRowsNum = lineItemGrid.getRowsNum();
	if (lineGridRowsNum > 0) {
		var linesData = "";
		for (var i = 1; i <= lineGridRowsNum; i++) {
			var lineTotalCost = gridCellValue(lineItemGrid,i,"extendedPriceDisp");
			var lineCurrencyId = gridCellValue(lineItemGrid,i,"currency");
			if (!isEmptyString(lineTotalCost) && Number(lineTotalCost) != 0 && !isEmptyString(lineCurrencyId)) {
				var dateToUse = "";
				if (!isEmptyString(gridCellValue(lineItemGrid,i,"lastConfirmed")))
					dateToUse = gridCellValue(lineItemGrid,i,"lastConfirmed");
				else if (!isEmptyString(gridCellValue(lineItemGrid,i,"dateConfirmed")))
					dateToUse = gridCellValue(lineItemGrid,i,"dateConfirmed");
				linesData += lineTotalCost
							+ ","
							+ lineCurrencyId
							+ ","
							+ dateToUse
							+ ";";
			}
		}
		
		if (isEmptyString(linesData))
			updateTotalCostComplete(0);
		else {
			j$.ajax({
				 type: "POST",
				 url: "/tcmIS/supply/purchaseorder.do",
				 data: {
				 	action: "getTotalCost",	 		
				 	list: linesData,
				 	currencyTo: $v("totalCostCurrencyId")
				 },
				 success: updateTotalCostComplete
		   });
		}
	}
}

function updateTotalCostComplete(returnedResult) {
	var totalCost = Number(returnedResult);
	if (isNaN(totalCost)) {
		alert(returnedResult);
		$("totalCostSpan").innerHTML = "";
		return;
	}
	
	//occasionally toFixed function fails, so we force a cut
	var totalCostStr = "" + totalCost.toFixed(4);
	try {
		var dotPos = totalCostStr.indexOf(".");
		if (totalCostStr.substr(dotPos + 1).length > 4)
			totalCostStr = totalCostStr.substr(0, dotPos) + "." + totalCostStr.substr(dotPos + 1, dotPos + 5);
	} catch (e) {}
	$("totalCostSpan").innerHTML = totalCostStr;
}

/**
 * Iterate through each row and if it is editable, change the column's value at the row and calls the onChange function
 * @param colId columnId whose values at each row will be changed
 * @param value the new value
 * @returns
 */
function changeAllDate(colId, value) {
	for (var curRowId = 1; curRowId <= lineItemGrid.getRowsNum(); curRowId++) {
		if (getColPermission(lineItemGrid, curRowId, colId) == 'Y') {
			setGridCellValue(lineItemGrid, curRowId, colId, value);
			poLineDateChangedRowLevel(curRowId);
		}
	}
}

function checkSelectedLineQuantity(valueToCompare) {
	var curQty;
	if ('Y' === gridCellValue(lineItemGrid, poLineSelectedRowId, "lineChange"))
		curQty = Number(gridCellValue(lineItemGrid, poLineSelectedRowId, "oldQuantity"));
	else
		curQty = Number(gridCellValue(lineItemGrid, poLineSelectedRowId, "quantity"));
	if (curQty > Number(valueToCompare))
		return 1;
	else if (curQty == Number(valueToCompare))
		return 0;
	else
		return -1;
}

function isEmptyString(s) {
	try {
		if (s == null || s.trim().length < 1)
			return true;
		else
			return false;
	} catch (e) {
		return false;
	}
}

j$(document).ready(function() {
	j$("input").attr("autocomplete","off");
});