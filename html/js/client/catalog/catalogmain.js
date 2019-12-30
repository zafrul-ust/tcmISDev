//quantity
var partNum = new Array();
var totalPart = 0;
var disableMenu = false;
var oldwidth = 0 ;
var oldheight = 0 ;
var cartgrid = null;
var useLayout = false;
var selectedRowId = null;
var inputSize = new Array();
var accountSysId = new Array();
var saveRowClass = null;
var oldRowId = null;
var dhxWins = null;
var returnFromMr = false;
var catalogFacility = new Array();
var children = new Array();
//this is for hcoro in the shopping cart
var examplePackaging = new Array();
//catalog adds starting points
var numberOfCatalogAddAllowed = 0;
var tmpCatalogFacilityIndex = 0;
var catalogAddNewMsds = false;
var catalogAddNewPartFromExistingItem = false;
var catalogAddNewPartFromExistingItemModifyPkg = false;
var catalogAddNewPart = false;
var catalogAddNewPartFromExistingPart = false;
var catalogAddModifyQpl = false;
var catalogAddNewWorkAreaApproval = false;
var engEvalCatalogAdd = false;
var catalogAddNewPartApprovalCode = false;


function searchOptionClicked() {
    if ($("allCatalogs").checked) {
        $("facilityId").disabled = true;
	    $("applicationId").disabled = true;    
    }else {
        $("facilityId").disabled = '';
	    $("applicationId").disabled = '';
    }
} //end of method

function displaySearchOption() {
    //show full facility catalogs
    var tmpSelectedFacilityId = $v("facilityId");
    var showFullFacilityCatalog = false;
    for (i = 0; i < altActiveFeatureRelease.length; i++) {
        if ((altActiveFeatureRelease[i].facilityId == tmpSelectedFacilityId || altActiveFeatureRelease[i].facilityId == 'ALL')
             && altActiveFeatureRelease[i].feature == 'FullFacilityCatalogSearch') {
            showFullFacilityCatalog = true;
            break;
        }
    }
    //if user has permission to create new catalog adds or
    //user has permission for full facility catalogs
    for (i = 0; i < altUserGroupMemberForCatalogScreen.length; i++) {
        if (altUserGroupMemberForCatalogScreen[i].facilityId == tmpSelectedFacilityId &&
            (altUserGroupMemberForCatalogScreen[i].userGroupId == 'CreateNewChemical' ||
             altUserGroupMemberForCatalogScreen[i].userGroupId == 'FullFacilityCatalogSearch')) {
            showFullFacilityCatalog = true;
            break;
        }
    }

    if (showFullFacilityCatalog)
        $("fullFacilityCatalogDisplay").style.display="";
    else
        $("fullFacilityCatalogDisplay").style.display="none";

} //end of method

function setCartDivSize() {
	// after rendering, the offsetheight and height difference is 5.
	// offsetHeight is outer, height is inner.
    setWindowSizes();
    if( showcart ) {
		var thei = $('searchTableDiv').offsetHeight -22;//
		  var twid = $('cartTableDiv').offsetWidth;//
        if( thei < 145 ) thei = 145;
        if( twid < cartMinWidth ) twid = cartMinWidth;

		$('cartTableDiv').style.height = thei+8 +"px";
		$('cartTableDiv').style.width = twid +"px";

		$('engEvalTableDiv').style.height = thei+12 +"px";
		$('engEvalTableDiv').style.width = twid +"px";
		
		$('msdsMsgTableDiv').style.height = thei+6 +"px";
		$('msdsMsgTableDiv').style.width = twid +"px";

		oldwidth  = twid ;
		oldheight = thei - 15;
		buildCartGrid();

        /*Setting the initial search section height so it can be used later for re-sizing*/
        pageId = 'newcatalog';
        if (isPageVisible(pageId))
        {
         searchSectionHeight = window.document.getElementById("searchFrameDiv").offsetHeight;
         searchSectionHeightInit = searchSectionHeight + 10;
        }
    }

    if ( typeof(intcmIsApplication) != 'undefined' && intcmIsApplication)
	 {
        var topNavigation = document.getElementById("topNavigation");
	    topNavigation.style.display="none";
    }
}  //end of method

function newPartFromExistingItem() {
	checkFacilityCatalog();
	if (numberOfCatalogAddAllowed == 1) {
		$("catalogAddCatalogCompanyId").value = catalogFacility[tmpCatalogFacilityIndex].catalogCompanyId;
	  	$("catalogAddCatalogId").value = catalogFacility[tmpCatalogFacilityIndex].catalogId;
		submitNewPartFromExistingItem();
	}else {
        catalogAddNewMsds = false;
        catalogAddNewPartFromExistingItem = true;
        catalogAddNewPartFromExistingItemModifyPkg = false;
        catalogAddNewPart = false;
        catalogAddNewPartFromExistingPart = false;
        catalogAddModifyQpl = false;
        catalogAddNewWorkAreaApproval = false;
        engEvalCatalogAdd = false;
        catalogAddNewPartApprovalCode = false;
        showCatalogFacilityInputDialog();
	}
} //end of method

function submitNewPartFromExistingItem() {
	showTransitWin("justPleaseWait");
	enableFieldsForFormSubmit();
    $("uAction").value = "newPartFromExistingItem";
	document.genericForm.target='checkOutCartFrame';
	document.genericForm.submit();
    searchOptionClicked();
}

function newPartFromExistingItemModifyPkg() {
	checkFacilityCatalog();
	if (numberOfCatalogAddAllowed == 1) {
		$("catalogAddCatalogCompanyId").value = catalogFacility[tmpCatalogFacilityIndex].catalogCompanyId;
	  	$("catalogAddCatalogId").value = catalogFacility[tmpCatalogFacilityIndex].catalogId;
		submitNewPartFromExistingItemModifyPkg();
	}else {
        catalogAddNewMsds = false;
        catalogAddNewPartFromExistingItem = false;
        catalogAddNewPartFromExistingItemModifyPkg = true;
        catalogAddNewPart = false;
        catalogAddNewPartFromExistingPart = false;
        catalogAddModifyQpl = false;
        catalogAddNewWorkAreaApproval = false;
        engEvalCatalogAdd = false;
        catalogAddNewPartApprovalCode = false;
        showCatalogFacilityInputDialog();
	}
} //end of method

function submitNewPartFromExistingItemModifyPkg() {
	showTransitWin("justPleaseWait");
	enableFieldsForFormSubmit();
    $("uAction").value = "newPartFromExistingItemModifyPkg";
	document.genericForm.target='checkOutCartFrame';
	document.genericForm.submit();
    searchOptionClicked();
}

function newCatalogPart() {
	checkFacilityCatalog();
	if (numberOfCatalogAddAllowed == 1) {
		$("catalogAddCatalogCompanyId").value = catalogFacility[tmpCatalogFacilityIndex].catalogCompanyId;
	  	$("catalogAddCatalogId").value = catalogFacility[tmpCatalogFacilityIndex].catalogId;
		submitNewCatalogPart();
	}else {
        catalogAddNewMsds = false;
        catalogAddNewPartFromExistingItem = false;
        catalogAddNewPartFromExistingItemModifyPkg = false;
        catalogAddNewPart = true;
        catalogAddNewPartFromExistingPart = false;
        catalogAddModifyQpl = false;
        catalogAddNewWorkAreaApproval = false;
        engEvalCatalogAdd = false;
        catalogAddNewPartApprovalCode = false;
        showCatalogFacilityInputDialog();
	}
} //end of method

function submitNewCatalogPart() {
	showTransitWin("justPleaseWait");
	enableFieldsForFormSubmit();
	$("uAction").value = "newCatalogPart";
	document.genericForm.target='checkOutCartFrame';
	document.genericForm.submit();
    searchOptionClicked();
} //end of method

function newPartFromExistingPart() {
	checkFacilityCatalog();
	if (numberOfCatalogAddAllowed == 1) {
		$("catalogAddCatalogCompanyId").value = catalogFacility[tmpCatalogFacilityIndex].catalogCompanyId;
	  	$("catalogAddCatalogId").value = catalogFacility[tmpCatalogFacilityIndex].catalogId;
		submitNewPartFromExistingPart();
	}else {
        catalogAddNewMsds = false;
        catalogAddNewPartFromExistingItem = false;
        catalogAddNewPartFromExistingItemModifyPkg = false;
        catalogAddNewPart = false;
        catalogAddNewPartFromExistingPart = true;
        catalogAddModifyQpl = false;
        catalogAddNewWorkAreaApproval = false;
        engEvalCatalogAdd = false;
        catalogAddNewPartApprovalCode = false;
        showCatalogFacilityInputDialog();
	}
} //end of method

function submitNewPartFromExistingPart() {
	showTransitWin("justPleaseWait");
	enableFieldsForFormSubmit();
	$("uAction").value = "newPartFromExistingPart";
	document.genericForm.target='checkOutCartFrame';
	document.genericForm.submit();
} //end of method

function modifyQpl() {
	checkFacilityCatalog();
	if (numberOfCatalogAddAllowed == 1) {
		$("catalogAddCatalogCompanyId").value = catalogFacility[tmpCatalogFacilityIndex].catalogCompanyId;
	  	$("catalogAddCatalogId").value = catalogFacility[tmpCatalogFacilityIndex].catalogId;
		submitModifyQpl();
	}else {
        catalogAddNewMsds = false;
        catalogAddNewPartFromExistingItem = false;
        catalogAddNewPartFromExistingItemModifyPkg = false;
        catalogAddNewPart = false;
        catalogAddNewPartFromExistingPart = false;
        catalogAddModifyQpl = true;
        catalogAddNewWorkAreaApproval = false;
        engEvalCatalogAdd = false;
        catalogAddNewPartApprovalCode = false;
        showCatalogFacilityInputDialog();
	}
} //end of method

function submitModifyQpl() {
	showTransitWin("justPleaseWait");
	enableFieldsForFormSubmit();
	$("uAction").value = "modifyQpl";
	document.genericForm.target='checkOutCartFrame';
	document.genericForm.submit();
} //end of method

function newWorkAreaApproval() {
	checkFacilityCatalog();
	if (numberOfCatalogAddAllowed == 1) {
		$("catalogAddCatalogCompanyId").value = catalogFacility[tmpCatalogFacilityIndex].catalogCompanyId;
	  	$("catalogAddCatalogId").value = catalogFacility[tmpCatalogFacilityIndex].catalogId;
		submitNewWorkAreaApproval();
	}else {
        catalogAddNewMsds = false;
        catalogAddNewPartFromExistingItem = false;
        catalogAddNewPartFromExistingItemModifyPkg = false;
        catalogAddNewPart = false;
        catalogAddNewPartFromExistingPart = false;
        catalogAddModifyQpl = false;
        catalogAddNewWorkAreaApproval = true;
        engEvalCatalogAdd = false;
        catalogAddNewPartApprovalCode = false;
        showCatalogFacilityInputDialog();
	}
} //end of method

function submitNewWorkAreaApproval() {
	showTransitWin("justPleaseWait");
	enableFieldsForFormSubmit();
	$("uAction").value = "newWorkAreaApproval";
	document.genericForm.target='checkOutCartFrame';
	document.genericForm.submit();
} //end of method

function newPartApprovalCode() {
	checkFacilityCatalog();
	if (numberOfCatalogAddAllowed == 1) {
		$("catalogAddCatalogCompanyId").value = catalogFacility[tmpCatalogFacilityIndex].catalogCompanyId;
	  	$("catalogAddCatalogId").value = catalogFacility[tmpCatalogFacilityIndex].catalogId;
		submitNewPartApprovalCode();
	}else {
        catalogAddNewMsds = false;
        catalogAddNewPartFromExistingItem = false;
        catalogAddNewPartFromExistingItemModifyPkg = false;
        catalogAddNewPart = false;
        catalogAddNewPartFromExistingPart = false;
        catalogAddModifyQpl = false;
        catalogAddNewWorkAreaApproval = false;
        engEvalCatalogAdd = false;
        catalogAddNewPartApprovalCode = true;
        showCatalogFacilityInputDialog();
	}
} //end of method

function submitNewPartApprovalCode() {
	showTransitWin("justPleaseWait");
	enableFieldsForFormSubmit();
	$("uAction").value = "newPartApprovalCode";
	document.genericForm.target='checkOutCartFrame';
	document.genericForm.submit();
} //end of method

function checkFacilityCatalog() {
    numberOfCatalogAddAllowed = 0;
	tmpCatalogFacilityIndex = 0;
	for ( var i=0; i < catalogFacility.length; i++) {
		if (catalogFacility[i].catalogAddAllowed == 'Y') {
			numberOfCatalogAddAllowed++;
			tmpCatalogFacilityIndex = i;
			if (numberOfCatalogAddAllowed > 1) {
				break;
			}
		}
	}
} //end of methos


//USE fac_loc_app.charge_type default first unless it's overrides by vv_account_sys.fac_item_charge_type_override
//here's the logic for overriding fac_loc_app.charge_type_default
//vv_account_sys.fac_item_charge_type_override == fac_item.part_charge_type OR vv_accoount_sys.fac_item_charge_type_override = a
//then USE fac_item.part_charge_type
//vv_account_sys.fac_item_charge_type_override:
// d - and fac_item.part_charge_type = d then USE fac_item.part_charge_type (in this case it's d)
// i - and fac_item.part_charge_type = i then USE fac_item.part_charge_type (in this case it's i)
// a - always USE fac_item.part_charge_type
// n - never override => USE fla.charge_type_default
function newEval() {
    if($("applicationId").value == "My Work Areas" ) {
  		alert(messagesData.pleaseSelect);
   }else {
       $("uAction").value = "newEval";
       if(accountSysId[$("itemCatalogLastSearchFacilityId").value].length > 1) {
			// Show the dialog box
			showAccountInputDialog();
		}else {
			$("accountSysId").value = accountSysId[$("itemCatalogLastSearchFacilityId").value][0].id;
            $("facItemChargeTypeOverride").value = accountSysId[$("itemCatalogLastSearchFacilityId").value][0].facItemChargeTypeOverride;
            submitNewReorderEval();
		}
    }
}

function reorderEval() {
    if($("applicationId").value == "My Work Areas" ) {
  		alert(messagesData.pleaseSelect);
   }else {
       $("uAction").value = "reorderEval";
       if(accountSysId[$("itemCatalogLastSearchFacilityId").value].length > 1) {
			// Show the dialog box
			showAccountInputDialog();
		}else {
			$("accountSysId").value = accountSysId[$("itemCatalogLastSearchFacilityId").value][0].id;
            $("facItemChargeTypeOverride").value = accountSysId[$("itemCatalogLastSearchFacilityId").value][0].facItemChargeTypeOverride;
            submitNewReorderEval();
		}
    }
}

//check to see if facility required user to answer additional question
function submitNewReorderEval() {
    if ($v("facLocAppChargeTypeDefault") == 'n') {
        submitEngEval();
    }else if ($v("facItemChargeTypeOverride") != null && $v("facItemChargeTypeOverride").length > 0) {
        submitEngEval();
    }else if ($v("engEvalPartTypeRequired") == 'Y') {
        //check to see if facility required user to answer additional question
        showEngEvalPartTypeInputDialog();
    }else {
        submitEngEval();
    }
}

//check to see if facility has multiple catlaogs
function submitEngEval() {
    checkFacilityCatalog();
	if (numberOfCatalogAddAllowed == 1) {
		$("catalogAddCatalogCompanyId").value = catalogFacility[tmpCatalogFacilityIndex].catalogCompanyId;
	  	$("catalogAddCatalogId").value = catalogFacility[tmpCatalogFacilityIndex].catalogId;
		submitEngEvalFinal();
	}else {
        catalogAddNewMsds = false;
        catalogAddNewPartFromExistingItem = false;
        catalogAddNewPartFromExistingItemModifyPkg = false;
        catalogAddNewPart = false;
        catalogAddNewPartFromExistingPart = false;
        catalogAddModifyQpl = false;
        catalogAddNewWorkAreaApproval = false;
        engEvalCatalogAdd = true;
        catalogAddNewPartApprovalCode = false;
        showCatalogFacilityInputDialog();
	}
}

//this method will finally send request to server
function submitEngEvalFinal() {
    showTransitWin("justPleaseWait");
	enableFieldsForFormSubmit();
	document.genericForm.target='checkOutCartFrame';
	document.genericForm.submit();
}

function loadMrInCart(action)
{
	if (action == 'returnToCart') {
		showTransitWin("");
		getPrData();
		returnFromMr = true;
	}else {
		returnFromMr = false;
	}
}

function getPrData() {
	//callToServer("returntocart.do?action=loaddata&prNumber=768643");
	//alert("here get pr data:"+$("prNumber").value);
	callToServer("returntocart.do?action=loaddata&prNumber="+$("prNumber").value);
}

//this is called from return to cart
function showShoppingCartData(mrDataJson) {
	var prAccountSysId = "";
	var prFacilityId = "";
	for (var i = 0; i < mrDataJson.length; i++) {
		if (i == 0) {
			prAccountSysId = mrDataJson[i].accountSysId;
			prFacilityId = mrDataJson[i].facilityId;
		}
		var tempQty = mrDataJson[i].qty;
		var extPrice = '';
		var catalogPrice = mrDataJson[i].catalogPrice;
		if ('' != catalogPrice) {
			extPrice = (tempQty * catalogPrice).toFixed(2);
		}

		var tmpPackaging = mrDataJson[i].examplePackaging;
		if (tmpPackaging != null) {
			var tmpPkgArray = tmpPackaging.split(" (");
			tmpPackaging = tmpPkgArray[0];
		}

		var rowid = (new Date()).valueOf();
		var shoppingCartRowIdKey = mrDataJson[i].part+mrDataJson[i].workArea+rowid;
		if(partNum[shoppingCartRowIdKey] == null ) {
			partNum[shoppingCartRowIdKey] = {
				prnumber:				mrDataJson[i].prNumber,
				lineitem:				mrDataJson[i].lineItem,
				catalogcompanyid:		mrDataJson[i].catalogCompanyId,
				catalog:         		mrDataJson[i].catalogId,
				inventorygroup:  		mrDataJson[i].inventoryGroup,
				facility:    	  		prFacilityId,
				workarea:    	  		mrDataJson[i].workArea,
				workareadesc:    		mrDataJson[i].workAreaDesc,
				catpartno:           mrDataJson[i].part,
				partgroupno:         mrDataJson[i].partGroupNo,
				partdescription:  	mrDataJson[i].description,
				stockingmethod:  		mrDataJson[i].itemType,
				quantity:        		tempQty,
				uos: 		        		'',
				currencyid:      		mrDataJson[i].currencyId,
				leadtime:	     		mrDataJson[i].leadTimeInDays,
				extprice:            extPrice,
				catalogprice:    		catalogPrice,
				baselineprice:   		mrDataJson[i].baselinePrice,
				minbuy:       			'',
				macpartnum:      		'',
				item: 		     		'',
				examplepackaging: 	tmpPackaging,
				medianmrleadtime: 	mrDataJson[i].medianMrLeadTime,
				dateneeded:          mrDataJson[i].dateNeed,
				notes:          		mrDataJson[i].notes,
				critical:          	mrDataJson[i].critical,
				rowid:       			null,
				rowidd:      			null,
				unitconversionoption:tmpPackaging,
				itemtype:				mrDataJson[i].itemType
			};
			totalPart++;
		}
		addPart(rowid,shoppingCartRowIdKey,mrDataJson[i].orderQuantityRule);
	} //end of for loop
	$('lastSearchFacilityId').value = prFacilityId;
	//set account sys from pr number
	var tmpAccSysArray = new Array({id: prAccountSysId, label: prAccountSysId});
	accountSysId[$("lastSearchFacilityId").value] = tmpAccSysArray;
	//
	reSelectFacilityForCart(prFacilityId,"");
	disableFieldsDataInShoppingCart();
	closeTransitWin();
} //end of method

function reSelectFacilityForCart(tmpSelectedFacilityId,tmpSelectedAppId) {
	if (tmpSelectedFacilityId != null && tmpSelectedFacilityId.length > 0) {
		var facilityDropdown = $("facilityId");
		var facilityIndex = 0;
		for (var j = 0; j < altFacilityId.length; j++) {
			if (altFacilityId[j] == tmpSelectedFacilityId) {
				facilityIndex = j;
				break;
			}
		}
		facilityDropdown.selectedIndex = facilityIndex;
		facilityChangedWithoutWorkareaChanged();

		if (tmpSelectedAppId != null && tmpSelectedAppId.length > 0) {
			var appIdArray = altApplication[tmpSelectedFacilityId];
			var appIndex = -1;
			for (var k = 0; k < appIdArray.length; k++) {
				if (appIdArray[k] == tmpSelectedAppId) {
					appIndex = k;
					break;
				}
			}
			//My Work Areas was added to dropdown when there is more than one work area
			if (appIdArray.length > 1 ) {
				appIndex = appIndex+1;
			}
			
			if(appIndex == -1) appIndex = 0;
			
			$("applicationId").selectedIndex = appIndex;
        }
    }
    workareaChanged();
} //end of method

//this function will intialize dhtmlXWindow if it's null
function initializeDhxWins() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function showTransitWin(messageType,messageText)
{
	if(messageType == "justPleaseWait") {
		$("transitLabel").innerHTML = messagesData.pleasewaitmenu+"...";
	}else if (messageText != null && messageText.length > 0) {
		$("transitLabel").innerHTML = messageText;
	}else {
		$("transitLabel").innerHTML = messagesData.pleasewait;
	}

	var transitDailogWin = document.getElementById("transitDailogWin");
	transitDailogWin.style.display="";

	initializeDhxWins();
	if (!dhxWins.window("transitDailogWin")) {
		// create window first time
		transitWin = dhxWins.createWindow("transitDailogWin",0,0, 400, 200);
		transitWin.setText("");
		transitWin.clearIcon();  // hide icon
		transitWin.denyResize(); // deny resizing
		transitWin.denyPark();   // deny parking

		transitWin.attachObject("transitDailogWin");
		//transitWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
		transitWin.center();
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		transitWin.setPosition(328, 131);
		transitWin.button("minmax1").hide();
		transitWin.button("park").hide();
		transitWin.button("close").hide();
		transitWin.setModal(true);
	}else{
		// just show
		transitWin.setModal(true);
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

function catalogReSize()
{
	resizeFrames();
   setWindowSizes();
   try {
   	try {
     		var searchTableWidth = window.document.getElementById("searchTable").offsetWidth;
    	}catch (ex1) {
     		searchTableWidth = 452;
    	}

     	if (_isIE) {
      	    window.document.getElementById("cartDiv").style["width"] = ""+(myWidth-searchTableWidth-37)+"px";
       	    window.document.getElementById("cartTableDiv").style["width"] =""+(myWidth-searchTableWidth-67)+"px";
			//eng eval
			window.document.getElementById("engEvalDiv").style["width"] = ""+(myWidth-searchTableWidth-37)+"px";
			window.document.getElementById("msdsMsgDiv").style["width"] = ""+(myWidth-searchTableWidth-37)+"px";
		}else {
       	    window.document.getElementById("cartDiv").style["width"] = ""+(myWidth-searchTableWidth-25)+"px";
       	    window.document.getElementById("cartTableDiv").style["width"] =""+(myWidth-searchTableWidth-70)+"px";
			//eng eval
			window.document.getElementById("engEvalDiv").style["width"] = ""+(myWidth-searchTableWidth-25)+"px";
			window.document.getElementById("msdsMsgDiv").style["width"] = ""+(myWidth-searchTableWidth-25)+"px";
		}
	  	if (cartgrid != null) {
	  		reSizeCoLumnWidths(cartgrid);
	  	}
	}catch (ex) {
     //alert("here 112");
   }
}

function enableFieldsForFormSubmit() {
	$("facilityId").disabled = '';
	$("applicationId").disabled = '';
	$("catalog").disabled = '';
	$("posRequestorName").disabled = '';
	//$("posRequestorSearch").disabled = '';
}

function submitSearchForm()
{
 //hide update links	
 document.getElementById('updateResultLink').style["display"] = "none";
 document.getElementById('catalogAddLink').style["display"] = "none";
 document.getElementById('newEvalLink').style["display"] = "none";
 document.getElementById('reorderEvalLink').style["display"] = "none";
 document.getElementById('itemCatalogAddLink').style["display"] = "none";

 /*Make sure to not set the target of the form to anything other than resultFrame*/
 var flag = validateForm();

 if( flag) 
  {
	enableFieldsForFormSubmit();
	var now = new Date();
   document.getElementById("startSearchTime").value = now.getTime();
   var action = document.getElementById("uAction");
   action.value = 'search';

	if (document.getElementById("catalog").value == 'partCatalog' || document.getElementById("catalog").value.startsWith('POS:')) {
		document.genericForm.target='partCatalogResultFrame';
		var fac = document.getElementById('facilityId');
		document.getElementById('lastSearchFacilityId').value = fac.value;
		document.getElementById('lastSearchFacilityDesc').value = fac.options[fac.selectedIndex].text;
		var app = document.getElementById('applicationId');
		document.getElementById('lastSearchApplication').value = app.value;
	   document.getElementById('lastSearchApplicationDesc').value = app.options[app.selectedIndex].text;
	}else if (document.getElementById("catalog").value == 'itemCatalog') {
		document.genericForm.target='itemCatalogResultFrame';
		document.getElementById('itemCatalogLastSearchFacilityId').value = document.getElementById('facilityId').value;
		var app = document.getElementById('applicationId');
		document.getElementById('itemCatalogLastSearchApplication').value = app.value;
	   document.getElementById('itemCatalogLastSearchApplicationDesc').value = app.options[app.selectedIndex].text;
	}else if ($v("catalog") == 'materialCatalog') {
		document.genericForm.target='materialCatalogResultFrame';
        var fac = document.getElementById('facilityId');
        document.getElementById('materialCatalogLastSearchFacilityId').value = fac.value;
        document.getElementById('materialCatalogLastSearchFacilityDesc').value = fac.options[fac.selectedIndex].text;
        var app = document.getElementById('applicationId');
        document.getElementById('materialCatalogLastSearchApplication').value = app.value;
        document.getElementById('materialCatalogLastSearchApplicationDesc').value = app.options[app.selectedIndex].text;
    }
	
	document.getElementById('ecommerceFacility').value = altFacilityEcommerce[ document.getElementById('facilityId').selectedIndex ];

	showPleaseWait();
   document.genericForm.submit();
	//disable fields depending of situation
	if( totalPart > 0 ) {
		disableFieldsDataInShoppingCart();
	}
	if (document.getElementById("catalog").value == 'itemCatalog' || document.getElementById("catalog").value == 'materialCatalog') {
		$("facilityId").disabled = '';
		$("applicationId").disabled = '';
		$("catalog").disabled = '';
	}
    //
    searchOptionClicked();

    return false;
  }
  else
  {
    return false;
  }
}

function validateForm() {
	var auditResult = true;
	//make sure that user enter something in search text when searching item catalog and material catalog
    //and part catalog if user picked All Catalog
    if (document.getElementById("catalog").value == 'itemCatalog' || document.getElementById("catalog").value == 'materialCatalog' ||
        (document.getElementById("catalog").value == 'partCatalog' && $("allCatalogs").checked)) {
		if ($("searchText") != null) {
			if ($v("searchText").trim().length == 0) {
				alert(messagesData.searchmessage);
				auditResult = false;
			}
		}
	}else if (document.getElementById("catalog").value.startsWith('POS:')) {
		if ($("posRequestorId") == null || $("posRequestorId").value.length == 0) {
			alert("missing customer");
			auditResult = false;
		}
	}

    return auditResult;
}

function createXSL() {
	enableFieldsForFormSubmit();
	document.getElementById('uAction').value="createXSL";
	document.genericForm.target='_excel_report_file';
	var fac = document.getElementById('facilityId');
	document.getElementById('facilityName').value = fac.options[fac.selectedIndex].text;
	var app = document.getElementById('applicationId');
	document.getElementById('applicationDesc').value = app.options[app.selectedIndex].text;
	var catType = document.getElementById('catalog');
	document.getElementById('searchTypeName').value = catType.options[catType.selectedIndex].text;

	openWinGenericExcel('/tcmIS/common/loadingfile.jsp','_excel_report_file','650','600','yes');
	setTimeout("document.genericForm.submit()",300);
	//disable fields depending on situation
	if( totalPart > 0 ) {
		disableFieldsDataInShoppingCart();
	}
	if (document.getElementById("catalog").value == 'itemCatalog' || document.getElementById("catalog").value == 'materialCatalog') {
		$("facilityId").disabled = '';
		$("applicationId").disabled = '';
		$("catalog").disabled = '';
	}
}


var userHasFacilityWithCatalogPriceAndWithout = false;
function catalogPriceOption() {
    var tmpHideCatalogPrice = false;
    var tmpShowCatalogPrice = false;
    for (var i=0; i < altFacilityId.length; i++) {
        for (var j = 0; j < altActiveFeatureRelease.length; j++) {
            if (altFacilityId[i] == altActiveFeatureRelease[j].facilityId) {
                if (altActiveFeatureRelease[j].feature == 'HideCatalogPrice') {
                    tmpHideCatalogPrice = true;
                }else {
                    tmpShowCatalogPrice = true;
                }
            }
            if (tmpShowCatalogPrice && tmpHideCatalogPrice) {
                userHasFacilityWithCatalogPriceAndWithout = true;
                break;
            }
        }
        if (userHasFacilityWithCatalogPriceAndWithout)
            break;
    }
} //end of method

function facilityChanged() {
 //rebuild shopping cart if user has both show catalog price facility and hide catalog price facility
 if(userHasFacilityWithCatalogPriceAndWithout) {
	 cartgrid = null;
	 buildCartGrid();
  }
  var selectedFacility = $("facilityId").value;
  var idArray = altApplication[selectedFacility];
  var nameArray = altApplicationDesc[selectedFacility];
  var selectI = 0;
  var inserted = 0 ;
  
  var inv = $("applicationId");
  for (var i = inv.length; i > 0; i--) {
    inv[i] = null;
  }

  if( nameArray != null ) {
	  var startingIndex = 0;
	  if (nameArray.length == 0 || nameArray.length > 1) {
	  	 setOption(0,messagesData.myworkareas,"My Work Areas", "applicationId");
		 startingIndex = 1;
	  }
	  for (var i=0; i < nameArray.length; i++) {
	    	setOption(i+startingIndex,nameArray[i],idArray[i], "applicationId");
	  }
	  //if only one workarea
	  if (nameArray.length == 1) {
	    selectWorkAreaApprovedOnly();
	  }
  }else {
    setOption(0,messagesData.myworkareas,"My Work Areas", "applicationId");
  }
  workareaChanged();
  catalogReSize();
  //reload catalog type (for point of sale)
  reloadCatalogType();
  //
  displaySearchOption();  
} //end of method

function facilityChangedWithoutWorkareaChanged() {
  var selectedFacility = $("facilityId").value;
  var idArray = altApplication[selectedFacility];
  var nameArray = altApplicationDesc[selectedFacility];
  var selectI = 0;
  var inserted = 0 ;

  var inv = $("applicationId");
  for (var i = inv.length; i > 0; i--) {
    inv[i] = null;
  }

  if( nameArray != null ) {
	  var startingIndex = 0;
	  if (nameArray.length == 0 || nameArray.length > 1) {
	  	 setOption(0,messagesData.myworkareas,"My Work Areas", "applicationId");
		 startingIndex = 1;
	  }
	  for (var i=0; i < nameArray.length; i++) {
	    	setOption(i+startingIndex,nameArray[i],idArray[i], "applicationId");
	  }
	  //if only one workarea
	  if (nameArray.length == 1) {
        selectWorkAreaApprovedOnly();
	  }
  }else {
    setOption(0,messagesData.myworkareas,"My Work Areas", "applicationId");
  }
  catalogReSize();
  //reload catalog type (for point of sale)
  reloadCatalogType();
} //end of method

function formatPrice( oriprice ) {
	var price = ""+oriprice;
	var par = price.split(".");
	if( par.length == 1 ) par[1] = "";

	par[0] = "000000000000"+ par[0];
	par[1] = par[1] + "000" ;
	par[0] = par[0].substr( par[0].length-11 );
	par[1] = par[1].substr( 0,3 ) ;
	return par[0]+"."+par[1];
}

function showSelectRow( part ) {
   if(  partNum[ part ] && partNum[ part ].rowid   ) {
   		cartgrid.selectRow(cartgrid.getRowIndex(partNum[ part ].rowid));//,false,false,true);
   		return ;
   }
}

function addPart(rowid,shoppingCartRowIdKey,orderQuantityRule) {
	$('checkoutspan').style["display"] = "";

   if( partNum[shoppingCartRowIdKey].rowid != null  ) {
		return ;
   }

	//I moved rowid as input parameter
	//var rowid = (new Date()).valueOf();
   ind = cartgrid.getRowsNum();
	var thisrow = cartgrid.addRow(rowid,"",ind);
	partNum[shoppingCartRowIdKey].rowidd = thisrow.idd;
	var count = 0;

	var tmpDateNeeded = partNum[shoppingCartRowIdKey].dateneeded;
	//setting global drop down for packaging and dateNeeded
	if ($v("catalog").startsWith("POS:")) {
		//default need date to today
    	tmpDateNeeded = fmtPosDefaultNeedDate;
		//packaging
		if (partNum[shoppingCartRowIdKey].itemtype == "MD" || partNum[shoppingCartRowIdKey].itemtype == "MB") {
			tmpUnitConversionOption = partNum[shoppingCartRowIdKey].unitconversionoption;
			if (tmpUnitConversionOption != null && tmpUnitConversionOption.length > 0) {
				var tmpUnitArray = tmpUnitConversionOption.split(";");
				examplePackaging = new Array();
				for (var i = 0; i < tmpUnitArray.length; i++) {
					examplePackaging[i] = {
						text:		tmpUnitArray[i],
						value: 	tmpUnitArray[i]
					}
				}
			}else {
				examplePackaging = new Array(
					{text:partNum[shoppingCartRowIdKey].examplepackaging,value:partNum[shoppingCartRowIdKey].examplepackaging}
				);
			}
		}else {
			examplePackaging = new Array(
				{text:partNum[shoppingCartRowIdKey].examplepackaging,value:partNum[shoppingCartRowIdKey].examplepackaging}
			);
		}
	}else {
		examplePackaging = new Array(
			{text:partNum[shoppingCartRowIdKey].examplepackaging,value:partNum[shoppingCartRowIdKey].examplepackaging}
		);
	}
		
	cartgrid.cells(rowid,count++).setValue('Y');
	cartgrid.cells(rowid,count++).setValue(shoppingCartRowIdKey);
	cartgrid.cells(rowid,count++).setValue(partNum[shoppingCartRowIdKey].workareadesc);
    cartgrid.cells(rowid,count++).setValue(partNum[shoppingCartRowIdKey].catpartno.replace(/&/g, "&amp;").replace(/ /g, "&nbsp;"));
	cartgrid.cells(rowid,count++).setValue(partNum[shoppingCartRowIdKey].partdescription);
	cartgrid.cells(rowid,count++).setValue(partNum[shoppingCartRowIdKey].quantity);
	cartgrid.cells(rowid,count++).setValue(partNum[shoppingCartRowIdKey].examplepackaging);
	cartgrid.cells(rowid,count++).setValue(partNum[shoppingCartRowIdKey].extprice);
	cartgrid.cells(rowid,count++).setValue(tmpDateNeeded);
	var ecommerceSource = document.getElementById("ecommerceSource");
	if (ecommerceSource.value == 'OCI') {
		cartgrid.cells(rowid,count++).setValue(partNum[shoppingCartRowIdKey].leadtime);
	}else {
		cartgrid.cells(rowid,count++).setValue(partNum[shoppingCartRowIdKey].medianmrleadtime);
	}
	cartgrid.cells(rowid,count++).setValue(partNum[shoppingCartRowIdKey].notes);
	if (partNum[shoppingCartRowIdKey].critical == 'true') {
		var tmpColumnIndex = count++;
		cartgrid.cells(rowid,tmpColumnIndex).setValue(true);
		$("critical"+rowid).checked = true;
		setRowClass(rowid,'grid_red');
	}else {
		cartgrid.cells(rowid,count++).setValue(false);
	}
	cartgrid.cells(rowid,count++).setValue(partNum[shoppingCartRowIdKey].facility.replace(/&/g, "&amp;"));
	cartgrid.cells(rowid,count++).setValue(partNum[shoppingCartRowIdKey].catalog);
	cartgrid.cells(rowid,count++).setValue(partNum[shoppingCartRowIdKey].catalogcompanyid);
	cartgrid.cells(rowid,count++).setValue(partNum[shoppingCartRowIdKey].partgroupno);
	cartgrid.cells(rowid,count++).setValue(partNum[shoppingCartRowIdKey].catalogprice);
	cartgrid.cells(rowid,count++).setValue(partNum[shoppingCartRowIdKey].baselineprice);
	cartgrid.cells(rowid,count++).setValue(partNum[shoppingCartRowIdKey].currencyid);
	cartgrid.cells(rowid,count++).setValue(orderQuantityRule);
	cartgrid.cells(rowid,count++).setValue(partNum[shoppingCartRowIdKey].inventorygroup.replace(/&/g, "&amp;"));
	cartgrid.cells(rowid,count++).setValue(partNum[shoppingCartRowIdKey].itemtype);
	cartgrid.cells(rowid,count++).setValue(partNum[shoppingCartRowIdKey].prnumber);
	cartgrid.cells(rowid,count++).setValue(partNum[shoppingCartRowIdKey].lineitem);
	cartgrid.cells(rowid,count++).setValue(partNum[shoppingCartRowIdKey].workarea.replace(/&/g, "&amp;"));
	cartgrid.cells(rowid,count++).setValue(""); 			//chargeType
	cartgrid.cells(rowid,count++).setValue("");        //poNumber
	cartgrid.cells(rowid,count++).setValue("");        //releaseNumber
	cartgrid.cells(rowid,count++).setValue("");        //chargeNumber
	cartgrid.cells(rowid,count++).setValue("");        //posWorkAreaOption
	cartgrid.cells(rowid,count++).setValue(partNum[shoppingCartRowIdKey].stockingmethod);
	partNum[shoppingCartRowIdKey].rowid = rowid;

	disableFieldsDataInShoppingCart();

	//select the last added part
	cartgrid.selectCell(cartgrid.getRowById(rowid), cartgrid.getColIndexById("quantity"), null,false, false, true);
	selectRow(rowid,cartgrid.getColIndexById("quantity"));

	posShoppingCartAdditionalProcessRequired();
} //end of method

function buildCartGrid() {
	$('cartDiv').style['visibility'] = 'visible';

   if( cartgrid != null ) return;
   cartgrid = new dhtmlXGridObject('cartTableDiv');

	$('cartTableDiv').style.height = (oldheight+5)+"px" ;
	$('cartTableDiv').style.width = (oldwidth -2)+"px";
   cartgrid.setImagePath("../../dhtmlxGrid/codebase/imgs/");

	var headerCols = new Array(
		"",
		"shoppingCartRowIdKey",
		messagesData.hworkarea,
		messagesData.hpart,
		messagesData.hdesc,
		messagesData.hquantity,
		messagesData.hexamplepackaging,
		messagesData.hextprice,
		messagesData.hdateneeded,
		messagesData.hleadtimeindays,
		messagesData.hnotes,
		messagesData.hcritical,
		"facilityId",
		"catalogId",
		"catalogCompanyId",
		"partGroupNo",
		"catalogPrice",
		"baselinePrice",
		"currencyId",
		"orderQuanityRule",
		"inventoryGroup",
		"itemType",
		"prNumber",
		"lineItem",
		"application",
		"chargeType",
		"poNumber",
		"releaseNumber",
		"chargeNumber",
		"posWorkAreaOption",
		"stockingMethod"
	);

	cartgrid.setColumnIds("permission,shoppingCartRowIdKey,workArea,catPartNo,partDescription,quantity,examplePackaging,extPrice,dateNeeded,leadTime,notes,"+
								 "critical,facilityId,catalogId,catalogCompanyId,partGroupNo,catalogPrice,baselinePrice,currencyId,orderQuanityRule,inventoryGroup,"+
		                   "itemType,prNumber,lineItem,application,chargeType,poNumber,releaseNumber,chargeNumber,posWorkAreaOption,stockingMethod");

    var tempSelectFacilityId = $v('facilityId');
    //HideCatalogPrice feature release
    var hideCatalogPriceFeature = false;
    for (var i = 0; i < altActiveFeatureRelease.length; i++) {
        if ((altActiveFeatureRelease[i].facilityId == tempSelectFacilityId || altActiveFeatureRelease[i].facilityId == 'ALL')
             && altActiveFeatureRelease[i].feature == 'HideCatalogPrice') {
            hideCatalogPriceFeature = true;
            break;
        }
    }
    
    var colWidths = new Array("0","0","7","7","7","5","5","7","8","10","5","5","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0");
    
    if(hideLeadTime)
    	{
			colWidths[9] = "0";
			headerCols[9] = "";
    	}

    if(hideCatalogPriceFeature) {
    	$('showCatalogPrice').value = 'N';
    	colWidths[7] = "0";
    	headerCols[7] = "";
	}else
		$('showCatalogPrice').value = 'Y';
    
	if ('OCI' == $("ecommerceSource").value) 
	{
		colWidths[8] = "0";
		colWidths[10] = "0";
		colWidths[11] = "0";
    	headerCols[8] = "";
    	headerCols[10] = "";
    	headerCols[11] = "";
	}
	cartgrid.setHeader(headerCols.toString());
    cartgrid.setInitWidths(colWidths.toString());
    
	cartgrid.setColAlign("left,left,left,left,left,right,left,left,left,left,left,"+
								"center,left,left,left,left,left,left,left,left,left,"+
		                  "left,left,left,left,left,left,left,left,left,left")
		                  
	                 
   cartgrid.setColTypes("ro,txt,ro,ro,ro,hed,hcoro,ro,hcal,ro,txt,"+
								"hchstatus,ro,ro,ro,ro,ro,ro,ro,ro,ro,"+
		                  "ro,ro,ro,txt,ro,ro,ro,ro,ro,ro");
	//set size for all 'hed' here
	inputSize["quantity"] = 4;
	inputSize["dateNeeded"] = 10;
    
//	cartgrid.attachEvent("onBeforeSelect",doOnBeforeSelect);
	cartgrid.attachEvent("onRowSelect",selectRow);
   //cartgrid.attachEvent("onRightClick",selectRow);

	/*CANNOT SORT SHOPPING CART DATA BECAUSE I ASSUMING LINE_ITEM ARE IN ORDER */
	cartgrid.setColSorting("na,na,haasStr,haasStr,haasStr,int,haasStr,int,na,int,haasStr,"+
								  "haasStr,na,haasStr,haasStr,haasStr,haasStr,haasStr,haasStr,haasStr,haasStr," +
   							  "haasStr,haasStr,haasStr,haasStr,haasStr,haasStr,haasStr,haasStr,haasStr,haasStr");
   
   cartgrid.enableTooltips("false,false,true,true,true,false,true,false,false,false,true,"+
									"false,false,false,false,false,false,false,false,false,false,"+
		                     "false,false,false,false,false,false,false,false,false,false");
   /*This is to enable edit on click. If a cell is editiable it will show as soon as the row is selected.*/
   cartgrid.enableEditEvents(true,false,false,false,false,false,false,false,false,false,false,
		 							  false,false,false,false,false,false,false,false,false,false,
		 							  false,false,false,false,false,false,false,false,false,false);
   cartgrid.setSkin("haas");
	cartgrid.submitOnlyChanged(false);
	cartgrid.setFieldName("{GRID_ID}[{ROW_INDEX}].{COLUMN_ID}");
	cartgrid.submitColumns([false,false,true,true,true,true,true,true,true,true,true,
		                     true,true,true,true,true,true,true,true,true,true,
	                        true,true,true,true,true,true,true,true,true,true]);
	cartgrid.init();
	/*This will update the column headers widths according to font size.*/
	updateColumnWidths(cartgrid);
	/*This is to copy the ctrl+c to clipboard, and ctrl+v to paste to clipboard.*/
	//cartgrid.entBox.onselectstart = function(){ return true; };
	
	setHaasGrid(cartgrid);
	catalogReSize();
}

function selectRow()
{
	rightClick = false;
   rowId0 = arguments[0];
   colId0 = arguments[1];
   ee     = arguments[2];

	selectedRowId = rowId0;
	//calculate extended price
	if (selectedRowId != null) {
		calculateExtPrice();
	}

	oldRowId = rowId0;

	for (var i=0; i<cartgrid.getRowsNum(); i++)
	{
		rowId = cartgrid.getRowId(i);
		var criticalValue=  cellValue (rowId,"critical");
		if(criticalValue == "true") {
			setRowClass(rowId,'grid_red');
		}else {
			if (i % 2 == 1) {
					setRowClass(rowId, 'grid_lightblue');
			}else {
					setRowClass(rowId, 'grid_white');
			}
		}

	}
	saveRowClass = getRowClass(rowId0);
	setRowClass(rowId0,''+saveRowClass+'Selected');

   if( ee != null ) {
   		if( ee.button != null && ee.button == 2 ) rightClick = true;
   		else if( ee.which == 3  ) rightClick = true;
   }

	var existingPrNumber =  cellValue (selectedRowId,"prNumber");
	if (existingPrNumber == null || existingPrNumber.length == 0) {
		$('delspan').innerHTML = '| <a href="javascript:checkDeletePart()"> ' + messagesData.hdeleteline +'</a>';
   	$('delspan').style["display"] = "";
	}else {
		$('delspan').style["display"] = "none";
	}

	//set ordering limit if required
	if ($v("catalog").startsWith("POS:")) {
		calculatePosOrderingLimit();
	}

} //end of method

function calculateExtPrice() {
	var tmpCatalogPrice = cellValue(selectedRowId,"catalogPrice");
	if (tmpCatalogPrice != null && tmpCatalogPrice.length > 0) {
		var tmpQty = cellValue(selectedRowId,"quantity");
		cartgrid.cells(selectedRowId,cartgrid.getColIndexById("extPrice")).setValue((tmpQty * tmpCatalogPrice).toFixed(2));
	}

}  //end of method

function checkDeletePart() {
	if( confirm(messagesData.confirmdeletepart) ) {
		deletePart();
	}
	$('delspan').style['display'] = "none";
}

function deletePart() {
	var selectedShoppingCartRowIdKey = cellValue(selectedRowId,"shoppingCartRowIdKey");
	rowid =  partNum[selectedShoppingCartRowIdKey].rowid;
	delete partNum[selectedShoppingCartRowIdKey];
	totalPart--;
	selectedRowId = cartgrid.getRowId(0);
	oldRowId = selectedRowId;
	selectRow(selectedRowId);
	cartgrid.deleteRow(rowid);
	if( totalPart == 0 ) {
		$('checkoutspan').style["display"] = "none";
		enableFieldsForFormSubmit();
		if ($v("catalog").startsWith('POS:')) {
			//clear point of sale data
			clearPointOfSaleData();
		}
		returnFromMr = false;
	}
}

function buildOciCartTable() {
	var table = document.getElementById("cartHiddenFormDiv");
	var inner = "";
	var i =0;
	for (p in partNum) { 
		i++;
		inner += // part num
			'<input name="NEW_ITEM-MATNR['+i+
			']" id="NEW_ITEM_MATNR_'+i+
			'" value="" type="hidden"/>'+
			// part unit
			'<input name="NEW_ITEM-UNIT['+i+
			']" id="NEW_ITEM_UNIT_'+i+
			'" value="" type="hidden"/>'+
			// part price
			'<input name="NEW_ITEM-PRICE['+i+
			']" id="NEW_ITEM_PRICE_'+i+
			'" value="" type="hidden"/>'+
			// part currency
			'<input name="NEW_ITEM-CURRENCY['+i+
			']" id="NEW_ITEM_CURRENCY_'+i+
			'" value="" type="hidden"/>'+
			// part leadtime
			'<input name="NEW_ITEM-LEADTIME['+i+
			']" id="NEW_ITEM_LEADTIME_'+i+
			'" value="" type="hidden"/>'+
			// mac part num
			'<input name="NEW_ITEM-MANUFACTMAT['+i+
			']" id="NEW_ITEM_MANUFACTMAT_'+i+
			'" value="" type="hidden"/>'+
			// part quantity
			'<input name="NEW_ITEM-QUANTITY['+i+
			']" id="NEW_ITEM_QUANTITY_'+i+
			'" value="" type="hidden"/>';
	}
	    
	table.innerHTML = inner;

	var c =0;
	for (p in partNum) {
		c++;
		document.getElementById("NEW_ITEM_MATNR_" + c).value = partNum[ p ].catpartno;
		document.getElementById("NEW_ITEM_UNIT_" + c).value =     partNum[ p ].uos;//"EA";
		document.getElementById("NEW_ITEM_PRICE_" + c).value =   formatPrice( partNum[ p ].catalogprice ); //"00000000005.000";
		document.getElementById("NEW_ITEM_CURRENCY_" + c).value = partNum[ p ].currencyid ;
		document.getElementById("NEW_ITEM_LEADTIME_" + c).value = "0" ;
		document.getElementById("NEW_ITEM_MANUFACTMAT_" + c).value = partNum[ p ].macpartnum ;
		document.getElementById("NEW_ITEM_QUANTITY_" + c).value = cellValue(partNum[ p ].rowid,"quantity") ;  //user might change this in the grid
	}
}	

function Checkout() {
	if ('OCI' == $("ecommerceSource").value) {
		if(validateOciCheckout()) {
			buildOciCartTable();
		   document.returnForm.submit();
		}
	}else if ('PNNL' == $("ecommerceSource").value){
		if (validateCheckout()) {
			if(accountSysId[$("lastSearchFacilityId").value].length > 1) {
				//I set this so that I can figure out where this is coming from when user is prompted for accounting system
				$("uAction").value = "pnnlCheckOut";
				// Show the dialog box
				showAccountInputDialog();
			}else {
				$("accountSysId").value = accountSysId[$("lastSearchFacilityId").value][0].id;
				submitPnnlCheckout();
			}
		}
	}
	else if ('AEROJETIP' == $("ecommerceSource").value){
		if (validateCheckout()) {
			submitAerojetCheckout();
		}
	} else if ('SEAGATEIP' == $("ecommerceSource").value) {	
		if (validateCheckout()) {
			if(accountSysId[$("lastSearchFacilityId").value].length > 1) {
				//I set this so that I can figure out where this is coming from when user is prompted for accounting system
				$("uAction").value = "seagateCheckout";
				// Show the dialog box
				showAccountInputDialog();
			}else {
				$("accountSysId").value = accountSysId[$("lastSearchFacilityId").value][0].id;
				submitSeagateCheckout();
			}
		}
	}
	else {
		if (validateCheckout()) {
			if ($v("catalog").startsWith('POS:')) {
				submitPointOfSaleCheckout();
			}else {
				if(accountSysId[$("lastSearchFacilityId").value].length > 1) {
					//I set this so that I can figure out where this is coming from when user is prompted for accounting system
					$("uAction").value = "checkOut";
					// Show the dialog box
					showAccountInputDialog();
				}else {
					$("accountSysId").value = accountSysId[$("lastSearchFacilityId").value][0].id;
					submitCheckout();
				}
			}
		}
	}
}

function submitPnnlCheckout() {
	enableFieldsForFormSubmit();
	$("uAction").value = "pnnlCheckOut";
	document.genericForm.target='checkOutCartFrame';
	cartgrid.parentFormOnSubmit(); //prepare grid for data sending
	document.genericForm.submit();
	clearShoppingCart();
	window.close();
}

function submitCheckout() {
	showTransitWin("justPleaseWait");
	enableFieldsForFormSubmit();
	$("uAction").value = "checkOut";
	document.genericForm.target='checkOutCartFrame';
	cartgrid.parentFormOnSubmit(); //prepare grid for data sending
	document.genericForm.submit();
	clearShoppingCart();
}

function loadMrShoppingCart(mrShopCartJson)
{
  clearShoppingCart()
  //Loop thru the JSON and populate the cart.
}

function clearShoppingCart() {
	for(p in partNum) {
		var selectedShoppingCartRowIdKey = cellValue(partNum[ p ].rowid,"shoppingCartRowIdKey");
		rowid =  partNum[selectedShoppingCartRowIdKey].rowid;
		delete partNum[selectedShoppingCartRowIdKey];
		totalPart--;
		selectedRowId = null;
		cartgrid.deleteRow(rowid);
	}
    /*Removing the elements from the form so we don't submit this multiple times.*/
	 for (var i=0; i<cartgrid.formInputs.length; i++) {
        cartgrid.parentForm.removeChild(cartgrid.formInputs[i]);
	} 

    if( totalPart == 0 ) $('checkoutspan').style["display"] = "none";
	$('delspan').style['display'] = "none";
	//reset shopping cart
	cartgrid = null;
	//initialize shopping cart grid
	buildCartGrid();
	returnFromMr = false;
}

function resultFrameAddToCart() {
	if (document.getElementById("catalog").value == 'partCatalog' || document.getElementById("catalog").value.startsWith('POS:')) {
		window.frames["partCatalogResultFrame"].doBeforeAddToCart();
	}
}

function showProp65Warning(warning)
{
	$("prop65WarningText").innerHTML = warning;
	$("prop65Warning").style["display"] = "block";
}

function clearProp65Warning()
{
	$("prop65WarningText").innerHTML = '';
	$("prop65Warning").style["display"] = "none";
}

function showCartCommentDialog(comments)
{
	$("cartCommentText").innerHTML = comments;
	
	var inputDailogWin = document.getElementById("cartCommentDailogWin");
	inputDailogWin.style.display="";

	initializeDhxWins();
	if (!dhxWins.window("showCartCommentDialog")) {
		// create window first time
		inputWin = dhxWins.createWindow("showCartCommentDialog",0,0, 400, 200);
		inputWin.setText(messagesData.cartcomment);
		inputWin.clearIcon();  // hide icon
		inputWin.denyResize(); // deny resizing
		inputWin.denyPark();   // deny parking
		inputWin.attachObject("cartCommentDailogWin");
		inputWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
		inputWin.center();
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		inputWin.setPosition(328, 131);
		inputWin.button("close").hide();
		inputWin.button("minmax1").hide();
		inputWin.button("park").hide();
		inputWin.setModal(true);
	}
	else
	{
		// just show
		inputWin.setModal(true);
		dhxWins.window("showCartCommentDialog").show();
	}
}

function cartCommentOk() {
	dhxWins.window("showCartCommentDialog").setModal(false);
	dhxWins.window("showCartCommentDialog").hide();
	window.frames["partCatalogResultFrame"].addToCart();
}

function cartCommentCancel() {
	dhxWins.window("showCartCommentDialog").setModal(false);
	dhxWins.window("showCartCommentDialog").hide();
}

var origFacilityIdArray;
var origFacilityNameArray;
var origFacilityEcommerceArray;
var origApplicationArray;
var origApplicationDescArray;
var origAccountSysIdArray;
var facilityDataCopied = false;
var facilityDataChanged = false;
var previousCatalogType = "partCatalog";

function catalogTypeChanged() {
	var tmpCatalogType = document.getElementById("catalog").value;
	//because I am using the same grid for both partCatalog and posCatalog
	//I need hide the grid and force users to re-query if he/she switches between part and pos catalog
	if (tmpCatalogType == 'partCatalog' || tmpCatalogType == 'itemCatalog' || tmpCatalogType == 'materialCatalog') {
		if (tmpCatalogType == 'partCatalog' && previousCatalogType.startsWith('POS:')) {
			$('lastSearchFacilityId').value = $('myDefaultFacilityId').value;
			$('lastSearchFacilityDesc').value = '';
			$('lastSearchApplication').value = '';
		}
		//restore point of sale manager's facility data
		if (facilityDataChanged) {
			altFacilityId = origFacilityIdArray;
			altFacilityName = origFacilityNameArray;
			altFacilityEcommerce = origFacilityEcommerceArray;
			altApplication = origApplicationArray;
			altApplicationDesc = origApplicationDescArray;
			accountSysId = origAccountSysIdArray;
			//clear out POS user data
			clearCustomer();
			loadFacility();
			facilityDataChanged = false;
		}
	}else if (tmpCatalogType.startsWith('POS:')) {
		if (previousCatalogType == 'partCatalog') {
			$('lastSearchFacilityId').value = '';
			$('lastSearchFacilityDesc').value = '';
			$('lastSearchApplication').value = '';
		}
		//copy point of sale manager's facility data so I can switch it back when he/she is back to the part
		//or item catalog
		if (!facilityDataCopied) {
			origFacilityIdArray = altFacilityId;
			origFacilityNameArray = altFacilityName;
			origFacilityEcommerceArray = altFacilityEcommerce;
			origApplicationArray = altApplication;
			origApplicationDescArray = altApplicationDesc;
			origAccountSysIdArray = accountSysId;
			facilityDataCopied = true;
		}
	}
    displaySelectedSearchTypeDetail();
	previousCatalogType = tmpCatalogType;
} //end of method

function displaySelectedSearchTypeDetail() {
  if ($v("catalog") == 'partCatalog' || document.getElementById("catalog").value.startsWith('POS:')) {
	  $('cartDiv').style["display"] = "";
	  $('engEvalDiv').style["display"] = "none";
	  $('msdsMsgDiv').style["display"] = "none";
	  enableFieldsForFormSubmit();
	  reSelectFacilityForCart($('lastSearchFacilityId').value,$('lastSearchApplication').value);
	  if( totalPart > 0 ) {
		  disableFieldsDataInShoppingCart();
	  }
  }else if ($v("catalog") == 'itemCatalog') {
	  $('cartDiv').style["display"] = "none";
	  $('engEvalDiv').style["display"] = "";
	  $('msdsMsgDiv').style["display"] = "none";
	  reSelectFacilityForCart($('itemCatalogLastSearchFacilityId').value,$('itemCatalogLastSearchApplication').value);
	  $("facilityId").disabled = '';
	  $("applicationId").disabled = '';
  }else if ($v("catalog") == 'materialCatalog') {
	  $('cartDiv').style["display"] = "none";
	  $('engEvalDiv').style["display"] = "none";
	  $('msdsMsgDiv').style["display"] = "";
	  reSelectFacilityForCart($v('materialCatalogLastSearchFacilityId'),$v('materialCatalogLastSearchApplication'));
	  $("facilityId").disabled = '';
	  $("applicationId").disabled = '';
  }
}

function disableFieldsDataInShoppingCart() {
	$("facilityId").disabled = true;
	$("catalog").disabled = true;
	$("posRequestorName").disabled = true;
	//$("posRequestorSearch").disabled = true;
}

//NEW for POS
function invalidateRequestor()
{
	var posRequestorName  =  document.getElementById("posRequestorName");
 	if (posRequestorName.value.length == 0) {
   	posRequestorName.className = "inputBox";
 	}else {
   	posRequestorName.className = "inputBox red";
 	}
 	//set to empty
 	document.getElementById("posRequestorId").value = "";
	//hide result section and clear last search data
	$('lastSearchFacilityId').value = '';
	$('lastSearchFacilityDesc').value = '';
	$('lastSearchApplication').value = '';
	document.getElementById("resultGridDiv").style["display"] = "none";
}

function clearCustomer() {
	document.getElementById("posRequestorId").value = '';
	document.getElementById("posRequestorName").value = '';
}

function changePersonnel() {
  loc = "searchpersonnelmain.do?";
  children[children.length] = openWinGeneric(loc,"changepersonnel","600","450","yes","50","50","20","20","no");
}

function personnelChanged(pid,fullName,callbackparam) {
	showTransitWin("justPleaseWait");
	document.getElementById("posRequestorId").value = pid;
	document.getElementById("posRequestorName").value = fullName;
	document.getElementById("posRequestorName").className = "inputBox";
	document.getElementById("resultGridDiv").style["display"] = "none";
    callToServer('loaduserfacilityworkareasdata.do?callback=loadUserFacilityWorkAreaDropDown&uAction=loadPointOfSaleUserData&posRequestorId='+pid+
                 '&posInventoryGroup='+$v("catalog"));
}


function loadUserFacilityWorkAreaDropDown(facilityIdArray,facilityNameArray,facilityEcommerceArray,applicationArray,applicationDescArray) {
	//update data arrays here
	facilityDataChanged = true; 
	altFacilityId = facilityIdArray;
	altFacilityName = facilityNameArray;
	altFacilityEcommerce = facilityEcommerceArray;
	altApplication = applicationArray;
	altApplicationDesc = applicationDescArray;
	loadFacility();
	closeTransitWin();	
}

function loadFacility() {
  var idArray = altFacilityId;
  var nameArray = altFacilityName;
  //clear current data
  var inv = $("facilityId");
  for (var i = inv.length; i > 0; i--) {
    inv[i] = null;
  }
  //load new data
  if( nameArray != null ) {
	  for (var i=0; i < nameArray.length; i++) {
	    setOption(i,nameArray[i],idArray[i], "facilityId");
	  }
  }else {
    setOption(0,messagesData.myFacilities,"My Facilities", "facilityId");
  }
  facilityChanged();
} //end of method


function reloadCatalogType() {
	var tmpSelectedFacilityId = $v('facilityId');
    var reloadCatalogType = false;
    //user has permission for point of sales
    for (var j = 0; j < altMyFacPosIg.length; j++) {
        if (altMyFacPosIg[j].facilityId == tmpSelectedFacilityId && altMyFacPosIg[j].createMr == 'Y') {
            reloadCatalogType = true;
            break;
        }
    }

    //facility has permission for MSDS catalog
    if (!reloadCatalogType) {
        for (var i = 0; i < altActiveFeatureRelease.length; i++) {
            if ((altActiveFeatureRelease[i].facilityId == tmpSelectedFacilityId || altActiveFeatureRelease[i].facilityId == 'ALL')
                 && altActiveFeatureRelease[i].feature == 'MsdsCatalog') {
                reloadCatalogType = true;
                break;
            }
	    }
    }

    //if user has permission to do point of sale for the facility or
    //reload catalog type dropdown because the previously selected facility
    //has point of sale inventory group or has MSDS catalog
    if (reloadCatalogType || $("catalog").length > 2) {
        loadCatalogType();
    }
}

function loadCatalogType() {
	//clear current data
  	var tmpCatalogType = $("catalog");
	var previousSelectedIndex = tmpCatalogType.selectedIndex;
	for (var i = tmpCatalogType.length; i > 0; i--) {
   	    tmpCatalogType[i] = null;
  	}

    var catalogCount = 0;
    //load new data
	setOption(catalogCount++,messagesData.partCatalog,'partCatalog', "catalog");
	setOption(catalogCount++,messagesData.itemCatalog,'itemCatalog', "catalog");

    //load msds catalog if it's release to selected facility
	var tmpSelectedFacilityId = $v('facilityId');
    var hasMaterialCatalog = false;
    for (var i = 0; i < altActiveFeatureRelease.length; i++) {
		if ((altActiveFeatureRelease[i].facilityId == tmpSelectedFacilityId || altActiveFeatureRelease[i].facilityId == 'ALL')
             && altActiveFeatureRelease[i].feature == 'MsdsCatalog') {
            hasMaterialCatalog = true;
            break;
        }
	}
    if(hasMaterialCatalog) {
        setOption(catalogCount++,messagesData.materialCatalog,'materialCatalog', "catalog");
    }

    //load point of sale inventory group
	for (var i = 0; i < altMyFacPosIg.length; i++) {
		if (altMyFacPosIg[i].facilityId == tmpSelectedFacilityId && altMyFacPosIg[i].createMr == 'Y') {
			//i+catalogCount is because the first index is for partCatalog and itemCatalog and (maybe msds catalog)
			setOption((i+catalogCount),'POS: '+altMyFacPosIg[i].inventoryGroupName,'POS: '+altMyFacPosIg[i].inventoryGroup, "catalog");
		}
	}

    /*
    //if partCatalog or itemCatalog was previous selected then reselect it
	if (previousSelectedIndex*1 > 1) {
		previousSelectedIndex = 0;
	}
    */
    //reselect to what is was before reloaded
    tmpCatalogType.selectedIndex = previousSelectedIndex;
} //end of method

function posShoppingCartAdditionalProcessRequired() {
	if ($v("catalog").startsWith("POS:")) {
		//load account sys data if user add the very first part to shopping cart
		if (cartgrid.getRowsNum() == 1) {
			if(accountSysId[$("lastSearchFacilityId").value].length > 1) {
				//I set this so that I can figure out where this is coming from when user is prompted for accounting system
				$("uAction").value = "getPosAccountSysData";
				// Show the dialog box
				showAccountInputDialog();
			}else {
				$("accountSysId").value = accountSysId[$("lastSearchFacilityId").value][0].id;
				submitPosAccountSysData();
			}
		}else {
			//prompt user for charge numbers
			//for now user has to use one charge numbers for entire MR
			// promptUserForChargeNumber();
			
			//copy charge info from 1st row to newly added row
			tmpRowId = cartgrid.getRowId(0);
			cartgrid.cells(selectedRowId,cartgrid.getColIndexById("chargeType")).setValue(cellValue(tmpRowId,"chargeType"));
			cartgrid.cells(selectedRowId,cartgrid.getColIndexById("chargeNumber")).setValue(cellValue(tmpRowId,"chargeNumber"));
			cartgrid.cells(selectedRowId,cartgrid.getColIndexById("poNumber")).setValue(cellValue(tmpRowId,"poNumber"));
			cartgrid.cells(selectedRowId,cartgrid.getColIndexById("releaseNumber")).setValue(cellValue(tmpRowId,"releaseNumber"));
			cartgrid.cells(selectedRowId,cartgrid.getColIndexById("posWorkAreaOption")).setValue(cellValue(tmpRowId,"posWorkAreaOption"));
		}
		//set ordering limit if required
		calculatePosOrderingLimit();
	}
} //end of method

function submitPosAccountSysData() {
	showTransitWin("justPleaseWait");
	callToServer("catalogmain.do?uAction=getPosAccountSysData&accountSysId="+$v("accountSysId")+"&facilityId="+$v("lastSearchFacilityId")+
		          "&applicationId="+$v("lastSearchApplication")+"&posRequestorId="+$v("posRequestorId"));
} //end of method

var posWorkAreaOption;
var posOrderingLimit = "";
var posRemainingLimit;
var posDirectedChargeAppColl = new Array();
var posPrRulesColl = new Array();
var fmtPosDefaultNeedDate = "";
function loadPosAccountSysData(workAreaOption,orderingLimit,directedChargeAppColl,prRulesColl,defaultNeedDate) {
	posWorkAreaOption = workAreaOption;
	posOrderingLimit = orderingLimit;
	posRemainingLimit = orderingLimit;
	posDirectedChargeAppColl = directedChargeAppColl;
	posPrRulesColl = prRulesColl;
	fmtPosDefaultNeedDate = defaultNeedDate;
	//reload work area dropdown if facility uses directed charge
	//if user selected a work area that is directed charge then reload dropdown with only directed charge work areas
	//otherwise, reload dropdown with only non-directed charge work areas
	if (posDirectedChargeAppColl != null && posDirectedChargeAppColl.length > 0) {
		reloadWorkareaDropDown(workAreaOption);
	}
	//set ordering limit if required
	calculatePosOrderingLimit();
	closeTransitWin();
	//prompt user for charge number
	promptUserForChargeNumber();
} //end of method

function promptUserForChargeNumber() {
	cartgrid.cells(selectedRowId,cartgrid.getColIndexById("posWorkAreaOption")).setValue(posWorkAreaOption);
	cartgrid.cells(selectedRowId,cartgrid.getColIndexById("dateNeeded")).setValue(fmtPosDefaultNeedDate);
	if (posWorkAreaOption == 'nonDirected') {
		var tmpRequiredChargePoInfo = false;
		for (var i = 0; i < posPrRulesColl.length; i++) {
			if (posPrRulesColl[i].poRequired == 'p' || posPrRulesColl[i].prAccountRequired == 'y') {
				tmpRequiredChargePoInfo = true;
				break;
			}
		}
		if (tmpRequiredChargePoInfo) {
			showTransitWin("justPleaseWait");
			var url = 'catalogmain.do?uAction=getPosAccountSyschargeNumberPoData&accountSysId='+$v("accountSysId")+'&facilityId='+$v("lastSearchFacilityId")+
		          	 '&facilityName='+$v("lastSearchFacilityDesc")+'&applicationId='+$v("lastSearchApplication")+'&posRequestorId='+$v("posRequestorId")+
				 		 '&sourcePage=pointOfSale';
			children[children.length] = openWinGeneric(url,"pointOfSaleChargeInfo",475,380,'no' );
		}
	}
} //end of method

function setPointOfSaleChargeNumberPoData(chargeType,chargeNumber,poNumber,releaseNumber,userEnteredChargeNumber) {
	if (chargeNumber.length == 0 && poNumber.length == 0) {
		closeTransitWin();
		//remove row if user did not enter a charge number when one is required
		deletePart();
		$('delspan').style['display'] = "none";
	}else {
		cartgrid.cells(selectedRowId,cartgrid.getColIndexById("chargeType")).setValue(chargeType);
		cartgrid.cells(selectedRowId,cartgrid.getColIndexById("chargeNumber")).setValue(chargeNumber);
		cartgrid.cells(selectedRowId,cartgrid.getColIndexById("poNumber")).setValue(poNumber);
		cartgrid.cells(selectedRowId,cartgrid.getColIndexById("releaseNumber")).setValue(releaseNumber);
		if (chargeNumber.length > 0 && userEnteredChargeNumber) {
			checkChargeNumbers(chargeType,chargeNumber);
		}else {
			closeTransitWin();
		}
	}
}

function validateChargeNumber(chargeNumbersOk) {
	if (chargeNumbersOk == 'OK') {
		closeTransitWin();
	}else {
		alert(messagesData.invalidChargeNumbers);
		cartgrid.cells(selectedRowId,cartgrid.getColIndexById("chargeType")).setValue("");
		cartgrid.cells(selectedRowId,cartgrid.getColIndexById("poNumber")).setValue("");
		cartgrid.cells(selectedRowId,cartgrid.getColIndexById("releaseNumber")).setValue("");
		cartgrid.cells(selectedRowId,cartgrid.getColIndexById("chargeNumber")).setValue("");
		promptUserForChargeNumber();
	}
} //end of method

function checkChargeNumbers(chargeType,chargeNumbers) {
	callToServer("catalogmain.do?uAction=checkChargeNumbers&companyId="+$v("companyId")+"&accountSysId="+$v("accountSysId")+"&chargeType="+chargeType+
		          "&chargeNumbers="+chargeNumbers);
} //end of method

function clearPointOfSaleData() {
	if (posDirectedChargeAppColl != null && posDirectedChargeAppColl.length > 0) {
		reloadWorkareaDropDown("none");
	}
	posWorkAreaOption = null;
	posOrderingLimit = "";
	posRemainingLimit = null;
	posDirectedChargeAppColl = null;
	posPrRulesColl = null;
	fmtPosDefaultNeedDate = "";
	$("orderLimitSpan").innerHTML = "";
} //end of method

function calculatePosOrderingLimit() {
	if (posOrderingLimit.length > 0 && posOrderingLimit != 'Unlimited') {
		var tmpTotalShoppingCartPrice = 0;
		for (var i = 0; i < cartgrid.getRowsNum(); i++) {
			tmpRowId = cartgrid.getRowId(i);
			var tmpExtPrice = cellValue(tmpRowId,"extPrice");
			if (tmpExtPrice != null) {
				tmpTotalShoppingCartPrice += tmpExtPrice*1;
			}
		}

		if (tmpTotalShoppingCartPrice > 0) {
			posRemainingLimit = Math.round((posOrderingLimit*1-tmpTotalShoppingCartPrice)*100)/100;
			$("orderLimitSpan").innerHTML = messagesData.remainingFinancialLimit+": "+posRemainingLimit;
		}else {
			posRemainingLimit = posOrderingLimit;
			$("orderLimitSpan").innerHTML = messagesData.remainingFinancialLimit+": "+posOrderingLimit;
		}
	}
} //end of method

//workAreaOption
//directed: limit to only directed work areas
//nonDirected: limit to only non-directed work areas
//none: show all work areas
function reloadWorkareaDropDown(workAreaOption) {
  var selectedFacility = $("facilityId").value;
  var idArray = altApplication[selectedFacility];
  var nameArray = altApplicationDesc[selectedFacility];

  var inv = $("applicationId");
  for (var i = inv.length; i > 0; i--) {
    inv[i] = null;
  }
  var selectedAppIndex = 0;

  if( nameArray != null ) {
	  var startingIndex = 0;
	  for (var i=0; i < nameArray.length; i++) {
		  if (workAreaOption == 'directed') {
			  if (posDirectedChargeAppColl != null && posDirectedChargeAppColl.length > 0) {
				var workAreaIsDirected = false;
				for (var j = 0; j < posDirectedChargeAppColl.length; j++) {
					if (idArray[i] == posDirectedChargeAppColl[j].application) {
						workAreaIsDirected = true;
						break;
					}
				}
				if (workAreaIsDirected) {
					//set this so I can selected it later
					if (idArray[i] == $v("lastSearchApplication")) {
						selectedAppIndex = startingIndex;
					}
					setOption(startingIndex++,nameArray[i],idArray[i], "applicationId");
				}else {
					//skip non-directed work area
					continue;
				}
			  }else {
				   //set this so I can selected it later
					if (idArray[i] == $v("lastSearchApplication")) {
						selectedAppIndex = startingIndex;
					}
					//if posDirectedChargeAppColl is empty then don't limit work areas
					setOption(startingIndex++,nameArray[i],idArray[i], "applicationId");
			  }
		  }else if (workAreaOption == 'nonDirected') {
			  if (posDirectedChargeAppColl != null && posDirectedChargeAppColl.length > 0) {
				var workAreaIsDirected = false;
				for (var j = 0; j < posDirectedChargeAppColl.length; j++) {
					if (idArray[i] == posDirectedChargeAppColl[j].application) {
						workAreaIsDirected = true;
						break;
					}
				}
				if (!workAreaIsDirected) {
					//set this so I can selected it later
					if (idArray[i] == $v("lastSearchApplication")) {
						selectedAppIndex = startingIndex;
					}
					setOption(startingIndex++,nameArray[i],idArray[i], "applicationId");
				}else {
					//skip directed work area
					continue;
				}
			  }else {
				   //set this so I can selected it later
					if (idArray[i] == $v("lastSearchApplication")) {
						selectedAppIndex = startingIndex;
					}
					//if posDirectedChargeAppColl is empty then don't limit work areas
					setOption(startingIndex++,nameArray[i],idArray[i], "applicationId");
			  }
		  }else {
			  //set this so I can selected it later
				if (idArray[i] == $v("lastSearchApplication")) {
					selectedAppIndex = startingIndex;
				}
			  //show all work areas
			  setOption(startingIndex++,nameArray[i],idArray[i], "applicationId");
		  }
	  }
	  //if only one workarea
	  if (nameArray.length == 1) {
        selectWorkAreaApprovedOnly();
	  }
  }else {
    setOption(0,messagesData.myworkareas,"My Work Areas", "applicationId");
  }

  //select the last search
  inv.selectedIndex = selectedAppIndex;

} //end of method

function submitPointOfSaleCheckout() {
	showTransitWin("justPleaseWait");
	enableFieldsForFormSubmit();
	$("uAction").value = "pointOfSaleCheckOut";
	$("facilityName").value = $v("lastSearchFacilityDesc");
	document.genericForm.target='checkOutCartFrame';
	cartgrid.parentFormOnSubmit(); //prepare grid for data sending
	document.genericForm.submit();
	clearShoppingCart();
	clearPointOfSaleData();
} //end of method

function closeAllchildren()
{
	// You need to add all your submit button vlues here. If not, the window will close by itself right after we hit submit button.
	//	if (document.getElementById("uAction").value != 'new') {
	try {
		for(var n=0;n<children.length;n++) {
			try {
				children[n].closeAllchildren();
			} catch(ex){}
			children[n].close();
		}
	} catch(ex){}
	children = new Array();
}

function showPosPickingScreen(mrNumber,tmpFacilityId,tmpFacilityName)
{
 closeTransitWin();
 if ( mrNumber != null &&  mrNumber != 0)
 {
  var loc = "pointofsale.do?action=search&prNumber="+mrNumber+"&facilityId="+tmpFacilityId+"&facilityName="+tmpFacilityName;
  try
  {
    parent.openIFrame("pointofsalepickingscreen"+mrNumber,loc,""+messagesData.materialrequest+" "+mrNumber,"","N");
  }
  catch (ex)
  {
	 openWinGeneric(loc,"pointofsalepickingscreen"+mrNumber+"","800","600","yes","50","50");
  }
 }
}


function selectWorkAreaApprovedOnly() {
    $("activeForFacility").checked = false;
    $("allCatalogs").checked = false;
    $("workAreaApproved").checked = true;
    $("workAreaApproved").disabled = "";
    $("fullFacilityCatalogs").checked = false;
}  //end of method

function deSelectWorkAreaApprovedOnly() {
    $("activeForFacility").checked = true;
    $("allCatalogs").checked = false;
    $("workAreaApproved").checked = false;
    $("workAreaApproved").disabled = "disabled";
    $("fullFacilityCatalogs").checked = false;
}  //end of method

function displayWorkAreaOptions() {
	if ($("applicationId").value != "My Work Areas") {
		if (document.getElementById("catalog").value == 'partCatalog') {
            selectWorkAreaApprovedOnly();
		}else if (document.getElementById("catalog").value == 'itemCatalog') {
			document.getElementById("evalWorkAreaSpan").innerHTML = messagesData.forthisworkarea;
		}
	}else {
		if (document.getElementById("catalog").value == 'partCatalog') {
            deSelectWorkAreaApprovedOnly();
		}else if (document.getElementById("catalog").value == 'itemCatalog') {
			document.getElementById("evalWorkAreaSpan").innerHTML = messagesData.formyworkareas;
		}
	}
} //end of method


function workareaChanged() {
    //first
    displayWorkAreaOptions();
    //then
    showResultFrameForSearchCriteria();
}

//determine whether to show or hide result data frame
//hide if last search work area is different than selected work area
function showResultFrameForSearchCriteria() {
	if (document.getElementById("catalog").value == 'partCatalog' || document.getElementById("catalog").value.startsWith('POS:')) {
	 if (document.getElementById("partCatalogLastSearchDurationMsg").value.length == 0) {
		document.getElementById("resultGridDiv").style["display"] = "none";
	 }else {
		 if ($("facilityId").value == $("lastSearchFacilityId").value && $("applicationId").value == $("lastSearchApplication").value) {
			 document.getElementById("resultGridDiv").style["display"] = "";
			 myResultFrameId = "partCatalogResultFrame";
			 document.getElementById("partCatalogDiv").style["display"] = "";
			 document.getElementById("itemCatalogDiv").style["display"] = "none";
			 document.getElementById("materialCatalogDiv").style["display"] = "none";
			 reDisplayGridSearchDuration(document.getElementById("partCatalogLastSearchDurationMsg"));
		 }else {
			 document.getElementById("resultGridDiv").style["display"] = "none";
		 }
	 }
	 if (document.getElementById("catalog").value == 'partCatalog') {
	 	document.getElementById("partCatalogCriteriaDiv").style["display"] = "";
		document.getElementById("posCatalogCriteriaDiv").style["display"] = "none";
	 }else {
		document.getElementById("partCatalogCriteriaDiv").style["display"] = "none";
		document.getElementById("posCatalogCriteriaDiv").style["display"] = "";
	 }
	 
	 document.getElementById("itemCatalogCriteriaDiv").style["display"] = "none";
	 document.getElementById("materialCatalogCriteriaDiv").style["display"] = "none";
	 //don't show eng eval options
 	 document.getElementById('newEvalLink').style["display"] = "none";
 	 document.getElementById('reorderEvalLink').style["display"] = "none";
 	 document.getElementById('newMsdsLink').style["display"] = "none";
	 document.getElementById('newApprovalCodeLink').style["display"] = "none";
     document.getElementById('itemCatalogAddLink').style["display"] = "none";
  }else if (document.getElementById("catalog").value == 'itemCatalog') {
	 if (document.getElementById("itemCatalogLastSearchDurationMsg").value.length == 0) {
		document.getElementById("resultGridDiv").style["display"] = "none";
	 }else {
		 if ($("facilityId").value == $("itemCatalogLastSearchFacilityId").value && $("applicationId").value == $("itemCatalogLastSearchApplication").value) {
			 document.getElementById("resultGridDiv").style["display"] = "";
			 myResultFrameId = "itemCatalogResultFrame";
			 document.getElementById("partCatalogDiv").style["display"] = "none";
			 document.getElementById("itemCatalogDiv").style["display"] = "";
			 document.getElementById("materialCatalogDiv").style["display"] = "none";
			 reDisplayGridSearchDuration(document.getElementById("itemCatalogLastSearchDurationMsg"));
		 }else {
		  	document.getElementById("resultGridDiv").style["display"] = "none";
		 }
	 }
	 
	 //change search criteria
	 document.getElementById("partCatalogCriteriaDiv").style["display"] = "none";
	 document.getElementById("posCatalogCriteriaDiv").style["display"] = "none";
	 document.getElementById("itemCatalogCriteriaDiv").style["display"] = "";
	 document.getElementById("materialCatalogCriteriaDiv").style["display"] = "none";
	
	 if (intcmIsApplication) {
		 document.getElementById('newEvalLink').style["display"] = "";
	 }
	
	 document.getElementById('reorderEvalLink').style["display"] = "none";
	 //don't show add to cart
	 document.getElementById('updateResultLink').style["display"] = "none";
	 document.getElementById('catalogAddLink').style["display"] = "none";
	 
	 document.getElementById('newMsdsLink').style["display"] = "none";
	 document.getElementById('newApprovalCodeLink').style["display"] = "none";
	 document.getElementById('itemCatalogAddLink').style["display"] = "none";
  }else if( $v("catalog") == 'materialCatalog') {
     if ($v("facilityId") == $v("materialCatalogLastSearchFacilityId") && $v("applicationId") == $v("materialCatalogLastSearchApplication")) {
         document.getElementById("resultGridDiv").style["display"] = "";
         myResultFrameId = "materialCatalogResultFrame";
         document.getElementById("partCatalogDiv").style["display"] = "none";
         document.getElementById("itemCatalogDiv").style["display"] = "none";
         document.getElementById("materialCatalogDiv").style["display"] = "";
         reDisplayGridSearchDuration(document.getElementById("materialCatalogLastSearchDurationMsg"));
     }else {
        document.getElementById("resultGridDiv").style["display"] = "none";
     }
	 //change search criteria
	 document.getElementById("partCatalogCriteriaDiv").style["display"] = "none";
	 document.getElementById("posCatalogCriteriaDiv").style["display"] = "none";
	 document.getElementById("itemCatalogCriteriaDiv").style["display"] = "none";
	 document.getElementById("materialCatalogCriteriaDiv").style["display"] = "";
	
	 document.getElementById('newEvalLink').style["display"] = "none";
	
	 document.getElementById('reorderEvalLink').style["display"] = "none";
	 //don't show add to cart
	 document.getElementById('updateResultLink').style["display"] = "none";
	 document.getElementById('catalogAddLink').style["display"] = "none";
	 
	 document.getElementById('newMsdsLink').style["display"] = "";
	 document.getElementById('newApprovalCodeLink').style["display"] = "none";
     document.getElementById('itemCatalogAddLink').style["display"] = "none";
  }
  
} //end of method

function newMsds() {
    $("materialId").value = "";
    $("custMsdsNo").value = "";
    askForCatalog();
} //end of method

function askForCatalog() {
    checkFacilityCatalog();
	if (numberOfCatalogAddAllowed == 1) {
		$("catalogAddCatalogCompanyId").value = catalogFacility[tmpCatalogFacilityIndex].catalogCompanyId;
	  	$("catalogAddCatalogId").value = catalogFacility[tmpCatalogFacilityIndex].catalogId;
		submitNewMsds();
	}else {
        catalogAddNewMsds = true;
        catalogAddNewPartFromExistingItem = false;
        catalogAddNewPartFromExistingItemModifyPkg = false;
        catalogAddNewPart = false;
        catalogAddNewPartFromExistingPart = false;
        catalogAddModifyQpl = false;
        catalogAddNewWorkAreaApproval = false;
        engEvalCatalogAdd = false;
        catalogAddNewPartApprovalCode = false;
        showCatalogFacilityInputDialog();
	}
} //end of method

function submitNewMsds() {
	showTransitWin("justPleaseWait");
	enableFieldsForFormSubmit();
	$("uAction").value = "newMsds";
	document.genericForm.target='checkOutCartFrame';
	document.genericForm.submit();
} //end of method

function newApprovalCode(materialId,custMsdsNo) {
    $("materialId").value = materialId;
    $("custMsdsNo").value = custMsdsNo;
    askForCatalog();
} //end of method

function previousEvalOrderClicked() {
	if ($("previousEvalOrder").checked) {
		$("evalRequestor").disabled = '';
		$("evalWorkArea").disabled = '';
		$("evalRequestorSpan").style["color"] = "black";
		$("evalWorkAreaSpan").style["color"] = "black";
	}else {
		$("evalRequestor").disabled = 'true';
		$("evalWorkArea").disabled = 'true';
		$("evalRequestor").checked = '';
		$("evalWorkArea").checked = '';
		$("evalRequestorSpan").style["color"] = "gray";
		$("evalWorkAreaSpan").style["color"] = "gray";	
	}
}

function validateCheckout()
{
	var rowNum = cartgrid.getRowsNum();	
	var rowId;
	var qtyVal;
	var isValid = false;
	var errorCount = 0;
	var errorMessage = messagesData.validvalues+"\n\n";	
	for (var i=0; i<rowNum; i++) {
		rowId = cartgrid.getRowId(i);
        qtyVal = cellValue(rowId,"quantity");
        if (cellValue(rowId,"catalogId") == 'TCM- MN') {
            if((isFloat(qtyVal))) {
                isValid = true;
            }else {
                 errorMessage +=  messagesData.hquantity + "\n";
                 isValid = false;
                 cartgrid.selectCell(cartgrid.getRowById(rowId), cartgrid.getColIndexById("quantity"), null,false, false, true);
                 selectRow(rowId,cartgrid.getColIndexById("quantity"));
                 errorCount = 1;
                 break;
            }
        }else {    
            if((isPositiveInteger(qtyVal))) {
                isValid = true;
            }else {
                 if(!isPositiveInteger(qtyVal)) {
                     errorMessage +=  messagesData.hquantity + "\n";
                     isValid = false;
                     cartgrid.selectCell(cartgrid.getRowById(rowId), cartgrid.getColIndexById("quantity"), null,false, false, true);
                     selectRow(rowId,cartgrid.getColIndexById("quantity"));
                 }
                 errorCount = 1;
                 break;
            }
        }
    } //end of loop

	if ($v("catalog").startsWith('POS:')) {
		if (posOrderingLimit.length > 0 && posOrderingLimit != 'Unlimited') {
			if (posRemainingLimit < 0) {
				alert(messagesData.mrExceededFinancialLimit);
				isValid = false;
			}
		}
	}

	if (errorCount >0) {
		alert(errorMessage);
	}
	return isValid;
}

function validateOciCheckout()
{
	var rowNum = cartgrid.getRowsNum();
	var rowId;
	var qtyVal;
	var isValid = false;
	var errorCount = 0;
	var errorMessage = messagesData.validvalues+"\n\n";
	for (var i=0; i<rowNum; i++)
	{
		rowId = cartgrid.getRowId(i);
		qtyVal = cellValue(rowId,"quantity");
		if((isPositiveInteger(qtyVal))) {
			isValid = true;
		}else {
//			 doOnBeforeSelect(rowId);
			 if(!isPositiveInteger(qtyVal))
			 {
				 errorMessage +=  messagesData.hquantity + "\n";
				 isValid = false;
				 cartgrid.selectCell(cartgrid.getRowById(rowId), cartgrid.getColIndexById("quantity"), null,false, false, true);
				 selectRow(rowId,cartgrid.getColIndexById("quantity"));
			 }
		 	 errorCount = 1;
		 	 break;
		}
	} //end of loop

	if (errorCount >0) {
		alert(errorMessage);
	}
	return isValid;
}

function showAccountInputDialog()
{
	var inputDailogWin = document.getElementById("accountSysDailogWin");
	inputDailogWin.style.display="";

	initializeDhxWins();
	if (!dhxWins.window("showAccountInputDialog")) {
		// create window first time
		inputWin = dhxWins.createWindow("showAccountInputDialog",0,0, 400, 118);
		inputWin.setText(messagesData.accountsysteminputdialog);
		inputWin.clearIcon();  // hide icon
		inputWin.denyResize(); // deny resizing
		inputWin.denyPark();   // deny parking	
		setOption(0,messagesData.pleaseselect,"", "accountSystemSelectBox");
		var tmpAccSys = new Array();
		if (document.getElementById("catalog").value == 'itemCatalog') {
			tmpAccSys = accountSysId[$("itemCatalogLastSearchFacilityId").value];
		}else {
			tmpAccSys = accountSysId[$("lastSearchFacilityId").value];
		}
		for ( var i=0; i < tmpAccSys.length; i++)
		{			
			if($v('companyId') == 'SEAGATE')
				setOption(i+1,tmpAccSys[i].id,tmpAccSys[i].id, "accountSystemSelectBox");
			else
				setOption(i+1,tmpAccSys[i].desc,tmpAccSys[i].id, "accountSystemSelectBox");

		}		
		inputWin.attachObject("accountSysDailogWin");
		inputWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
		inputWin.center();	   
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		inputWin.setPosition(328, 131); 
		inputWin.button("close").hide();
		inputWin.button("minmax1").hide();
		inputWin.button("park").hide();
		inputWin.setModal(true);
	}
	else
	{
		// just show
		inputWin.setModal(true);
		dhxWins.window("showAccountInputDialog").show();
	}
}

function accountSysOkClose()
{
	var selectedAccountSysId = document.getElementById("accountSystemSelectBox");	
	if((selectedAccountSysId.length>0) && (selectedAccountSysId.value!=""))
	{
	  $("accountSysId").value = selectedAccountSysId.value;
	  dhxWins.window("showAccountInputDialog").setModal(false); 	
	  dhxWins.window("showAccountInputDialog").hide();
	  var tmpAction = $("uAction").value;
	  if (tmpAction == "checkOut") {
	  	submitCheckout();
	  }else if (tmpAction == "pnnlCheckOut") {
	  	submitPnnlCheckout();
	  }else if (tmpAction == "newEval" || tmpAction == "reorderEval") {
          //since we are in here that means there are more then 1 account sys for facility
          var tmpAccSys = accountSysId[$("itemCatalogLastSearchFacilityId").value];
          $("facItemChargeTypeOverride").value = tmpAccSys[selectedAccountSysId.selectedIndex-1].facItemChargeTypeOverride;
          submitNewReorderEval();
	  }else if (tmpAction == "getPosAccountSysData") {
		  submitPosAccountSysData();
	  }else if (tmpAction == "seagateCheckout") {
		  submitSeagateCheckout();
	  }
	}	
} //end of method

function showEngEvalPartTypeInputDialog()
{
    var inputDailogWin = document.getElementById("engEvalPartTypeDailogWin");
	inputDailogWin.style.display="";
	initializeDhxWins();
	if (!dhxWins.window("engEvalPartTypeInputDialog")) {
		// create window first time
		inputWin = dhxWins.createWindow("engEvalPartTypeInputDialog",0,0, 400, 140);
		inputWin.setText(messagesData.pleaseselect);
		inputWin.clearIcon();  // hide icon
		inputWin.denyResize(); // deny resizing
		inputWin.denyPark();   // deny parking
		inputWin.attachObject("engEvalPartTypeDailogWin");
		inputWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
		inputWin.center();
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		inputWin.setPosition(328, 131);
		inputWin.button("close").hide();
		inputWin.button("minmax1").hide();
		inputWin.button("park").hide();
		inputWin.setModal(true);
	}
	else
	{
		// just show
		inputWin.setModal(true);
		dhxWins.window("engEvalPartTypeInputDialog").show();
	}
}

function engEvalPartTypeOkClose() {
	var selectedVal = "";
    if ($("productionMaterialYes").checked) {
        selectedVal = 'd';
    }else if ($("productionMaterialNo").checked) {
        selectedVal = 'i';
    }
    if(selectedVal.length > 0) {
	  $("engEvalPartType").value = selectedVal;
	  dhxWins.window("engEvalPartTypeInputDialog").setModal(false);
	  dhxWins.window("engEvalPartTypeInputDialog").hide();
	  submitEngEval();
	}
} //end of method

function showMrScreen(mrNumber)
{
 closeTransitWin();	
 if ( mrNumber != null &&  mrNumber != 0)
 {
  var loc = "materialrequest.do?action=int&prNumber="+mrNumber;
  try
  {
    parent.openIFrame("materialrequest"+mrNumber,loc,""+messagesData.materialrequest+" "+mrNumber,"","N");
  }
  catch (ex)
  {
	 openWinGeneric(loc,"materialrequest"+mrNumber+"","800","600","yes","50","50");
  }
 }
}

function showEvalScreen(requestId)
{
 closeTransitWin();
 if ( requestId != null &&  requestId != 0)
 {
  var loc = "engeval.do?uAction=view&requestId="+requestId;
  try
  {
    parent.openIFrame("engeval",loc,""+messagesData.engineeringevaluation+"","","N");
  }
  catch (ex)
  {
	 openWinGeneric(loc,"engeval"+requestId+"","800","600","yes","50","50");
  }
 }
}

function showCatalogAddRequestScreen(requestId,modifyPkg)
{
 closeTransitWin();
 if ( requestId != null &&  requestId != 0)
 {
  var loc = "catalogaddrequest.do?uAction=view&requestId="+requestId+"&newPartFromExistingItemModifyPkg="+modifyPkg;
  try
  {
    parent.openIFrame("cataddreq"+requestId,loc,""+messagesData.cataddreq+" "+requestId,"","N");
  }
  catch (ex)
  {
	 openWinGeneric(loc,"cataddreq"+requestId+"","800","600","yes","50","50");
  }
 }
}

function showCatalogAddMsdsRequestScreen(requestId)
{
 closeTransitWin();
 if ( requestId != null &&  requestId != 0)
 {
  var loc = "catalogaddmsdsrequest.do?uAction=view&requestId="+requestId;
  try
  {
    parent.openIFrame("cataddreq"+requestId,loc,""+messagesData.cataddreq+" "+requestId,"","N");
  }
  catch (ex)
  {
	 openWinGeneric(loc,"cataddreq"+requestId+"","800","600","yes","50","50");
  }
 }
}


function showPreviousOrderedEvalDetail(itemId) {
	//this is to keep track which row is selected in popup grid
	var selectedPreviousOrderEngEvalRowId = null;
	showPreviousOrderEngEvalDetail(itemId,$v("itemCatalogLastSearchFacilityId"),$v("itemCatalogLastSearchApplication"));
}

function showCatalogFacilityInputDialog()
{
	var inputDailogWin = document.getElementById("catalogFacilityDailogWin");
	inputDailogWin.style.display="";

	initializeDhxWins();
	if (!dhxWins.window("showCatalogFacilityInputDialog")) {
		// create window first time
		inputWin = dhxWins.createWindow("showCatalogFacilityInputDialog",0,0, 400, 118);
		inputWin.setText(messagesData.pleaseSelectaCatalog);
		inputWin.clearIcon();  // hide icon
		inputWin.denyResize(); // deny resizing
		inputWin.denyPark();   // deny parking
		var catalogCount = 0;
        setOption(catalogCount++,messagesData.pleaseselect,"", "catalogFacilitySelectBox");
        for ( var i=0; i < catalogFacility.length; i++)
		{
			if (catalogFacility[i].catalogAddAllowed == 'Y') {
				setOption(catalogCount++,catalogFacility[i].catalogDesc,catalogFacility[i].catalogId, "catalogFacilitySelectBox");
			}
		}
		inputWin.attachObject("catalogFacilityDailogWin");
		inputWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
		inputWin.center();
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		inputWin.setPosition(328, 131);
		inputWin.button("close").hide();
		inputWin.button("minmax1").hide();
		inputWin.button("park").hide();
		inputWin.setModal(true);
	}
	else
	{
		// just show
		inputWin.setModal(true);
		dhxWins.window("showCatalogFacilityInputDialog").show();
	}
}

function getWorkArea() {
	loc = "workareasearchmain.do?status=A&facilityId="+$v("facilityId")+"&facility="+encodeURIComponent($("facilityId").options[$("facilityId").selectedIndex].text);	
    children[children.length] = openWinGeneric(loc,"workAreaSearch","580","550","yes","50","50","20","20","no");	
}

function catalogFacilityOkClose() {
	var selectedCatalogId = document.getElementById("catalogFacilitySelectBox");
	if((selectedCatalogId.length>0) && (selectedCatalogId.value!="")) {
      //looping thru array to get match for selected catalog
      for ( var i=0; i < catalogFacility.length; i++) {
          if (catalogFacility[i].catalogId == selectedCatalogId.value) {
            $("catalogAddCatalogCompanyId").value = catalogFacility[i].catalogCompanyId;
	        $("catalogAddCatalogId").value = catalogFacility[i].catalogId;
            break;
          }
      }

	  dhxWins.window("showCatalogFacilityInputDialog").setModal(false);
	  dhxWins.window("showCatalogFacilityInputDialog").hide();
      if (catalogAddNewMsds) {
        submitNewMsds();
      }else if (catalogAddNewPartFromExistingItem) {
        submitNewNewPartFromExistingItem();
      }else if (catalogAddNewPartFromExistingItemModifyPkg) {
        submitNewPartFromExistingItemModifyPkg();
      }else if (catalogAddNewPart) {
        submitNewCatalogPart();
      }else if (catalogAddNewPartFromExistingPart) {
        submitNewPartFromExistingPart();
      }else if (catalogAddModifyQpl) {
        submitModifyQpl();
      }else if (catalogAddNewWorkAreaApproval) {
        submitNewWorkAreaApproval();
      }else if (engEvalCatalogAdd) {
        submitEngEvalFinal();
      }else if (catalogAddNewPartApprovalCode) {
        submitNewPartApprovalCode();
      }
    }
} //end of method

function submitAerojetCheckout() {
	messagesData.pleasewait = messagesData.checkoutinprocess;
	showTransitWin("");
	enableFieldsForFormSubmit();
	loc = window.location+""; 
	if( loc.indexOf('puchoutstartxml') != -1 ) 
		document.genericForm.action = "aerojetcheckoutxml.do";
	else
		document.genericForm.action = "aerojetcheckout.do";
	$("uAction").value = "aerojetCheckOut";
	document.genericForm.target= "_self";//'checkOutCartFrame';
	$("accountSysId").value = accountSysId[$("lastSearchFacilityId").value][0].id;
	cartgrid.parentFormOnSubmit(); //prepare grid for data sending
	document.genericForm.submit();
	clearShoppingCart();
//	window.close();
}

function submitSeagateCheckout() {
    messagesData.pleasewait = messagesData.checkoutinprocess;
    showTransitWin("");
    enableFieldsForFormSubmit();
    document.genericForm.action = "seagatecheckout.do";
    $("uAction").value = "seagateCheckOut";
    document.genericForm.target= top.name;
    cartgrid.parentFormOnSubmit(); //prepare grid for data sending
    document.genericForm.submit();
    clearShoppingCart();   
}

function launchAribaRepair() {
	showTransitWin(messagesData.waitingforinputfrom.replace('{0}',messagesData.aribarepair));
	document.getElementById('uAction').value="aribarepair";
	document.genericForm.target='ariba_repair';
	children[children.length] = openWinGeneric('/tcmIS/common/loadingpleasewait.jsp','ariba_repair','650','600','yes');
	setTimeout("document.genericForm.submit()",300);
}

function submitAribaRepair(aribaRepairString)
{
	showTransitWin(messagesData.waitingforinputfrom.replace('{0}',messagesData.aribarepair));
	$('aribaRepairMRsString').value=aribaRepairString;
	$('uAction').value="aribarepairSubmit";
	document.genericForm.target=top.name;
	setTimeout("document.genericForm.submit()",300);
}

function cancelSeagateCheckout() {	
	messagesData.pleasewait = messagesData.cancelcheckout;
	showTransitWin("");
	enableFieldsForFormSubmit();
	document.genericForm.action = "seagatecheckout.do";	
	$("uAction").value = "cancelSeagateCheckOut";	
	document.genericForm.target= top.name;
	document.genericForm.submit();	
	clearShoppingCart();
}

