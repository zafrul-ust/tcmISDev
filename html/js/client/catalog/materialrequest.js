var homeCompanyLogin;
var mygrid;
var chargeNumberGrid2Columns;
var chargeNumberGrid3Columns;
var chargeNumberGrid4Columns;
var chargeNumberGrid5Columns;
var inputSize = new Array();
var selectedRowId = null;
var chargeNumberSelectedRowId = null;
/*Set this to be false if you don't want the grid width to resize based on window size.
* You will also need to set the resultsMaskTable width appropriately in the JSP.*/
var resizeGridWithWindow = true;

var saveRowClass = null;
var oldRowId = null;
var dhxWins = null;
var children = new Array();
var returnToCartOk = false;

var multiplePermissions = true;

var emptyMaxRow = 20;        

// Build up the array for the columns which use different permissions
var permissionColumns = new Array();
permissionColumns = {
        "notes" : true
};

var allGridUndoData;
var allOtherUndoData;
var copyGridIndex;

var userAction = "";
var initial;
//variable used by autocomplete to indicate if the given id is ready for use or not
var isAvailable = true;

/*This function is called onload from the page*/
function myOnLoad(action)
{
	 closeTransitWin();
	 userAction = action;
	 if ((action == 'delete' || action == 'returnToCart') && ($("tcmISError") == null || $("tcmISError").value.length == 0)) {
		if (action == 'returnToCart') {
			//Open the catalog page and load the cart.
			loc = "catalogmain.do?action=returnToCart&prNumber="+$("prNumber").value+"";
			try {
				  parent.parent.closeTabx('materialrequest'+$v("prNumber"));
				  parent.parent.openIFrame("newcatalog",loc,""+messagesData.catalog+"","","N");
			}catch (ex) {
				alert("Not in the application");
				//openWinGeneric(loc,"newcatalog","800","600","yes","50","50");
			}
	 	}else if (action == 'delete') {
			parent.parent.closeTabx('materialrequest'+$v("prNumber"));
		}
	 }else {
	 	 setUserView();
		 /*If there is data to be shown initialize the grid*/
		 var totalLines = document.getElementById("totalLines").value;
		 if (totalLines > 0) {
			  document.getElementById("materialRequestDivId").style["display"] = "";
			  doInitGrid();
         }else {
			  document.getElementById("materialRequestDivId").style["display"] = "none";
		 }
     }
} //end of method

function continueLoadingData() {
	 //select the first row
	 selectedRowId = 1;
	 saveRowClass = getRowClass(1);
	 mygrid.selectRow(mygrid.getRowIndex(selectedRowId),null,false,false);
	  
	 initial = true;
	 selectRow(1);
 
	/*It is important to call this after all the divs have been turned on or off.
	 * This sets all sizes to be a good fit on the screen.*/
	 internalHeightIEOffset = 45;
	 internalHeightFFOffset = 50;
	 internalWidthIEOffset = 52;
	 internalWidthFFOffset = 27;
	 setResultSize();

	 //print this screen
	 if (userAction == 'printScreen') {
		openPrintScreen();
	 }

	 if (userAction == 'submit') {
		 var tmpPrStasus = $("prStatus").value;
		 if ( tmpPrStasus == 'compchk') {
            if ('Y' == $v("chargeNumberApprovalNeeded")) {
                showMessageDialog(messagesData.information,"At least one or more lines on your material request required charge number approval ",false,"");
            }else {
                showMessageDialog(messagesData.information,messagesData.sendtofinancialapprover,false,"");
            }
         }else if ( tmpPrStasus == 'compchk2') {
            showMessageDialog(messagesData.information,messagesData.sendtouseapprover,false,"");
         }else if ( tmpPrStasus == 'approved') {
			showMessageDialog(messagesData.information,messagesData.sendtoreleaser,false,"");
		 }else if ( tmpPrStasus == 'posubmit') {
			showMrAllocationReport();
		 }
	 }

    if( $v("updatable") == 'Y') {    // $v("updatable") == 'Y') {
		 $("dockLabel").style.display="none";
		 $("dockSpan").style.display="none";
		 $("delivertoLabel").style.display="none";
		 $("delivertoSpan").style.display="none";
		 $("lineSpan").style.display="none";
	 }

}

var callingShowMessageDialogFrom = "";
function showMessageDialog(winTitle,message,allowEdit,calledFrom)
{
	$("messageText").innerHTML = message;
	if (allowEdit) {
		$("messageText").readOnly = "";
	}else {
		$("messageText").readOnly = "true";
	}
	callingShowMessageDialogFrom = calledFrom;

	var inputDailogWin = document.getElementById("messageDailogWin");
	inputDailogWin.style.display="";

	initializeDhxWins();
	inputWin = dhxWins.createWindow("showMessageDialog",0,0, 450, 150);
	inputWin.setText(winTitle);
	inputWin.clearIcon();  // hide icon
	inputWin.denyResize(); // deny resizing
	inputWin.denyPark();   // deny parking
	inputWin.attachObject("messageDailogWin");
	inputWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
	inputWin.center();
	// setting window position as default x,y position doesn't seem to work, also hidding buttons.
	inputWin.setPosition(328, 131);
	inputWin.button("close").hide();
	inputWin.button("minmax1").hide();
	inputWin.button("park").hide();
	inputWin.setModal(true);

}

function closeMessageWin() {
  dhxWins.window("showMessageDialog").setModal(false);	
  dhxWins.window("showMessageDialog").hide();
  if (callingShowMessageDialogFrom == 'requestCancel') {
	  submitRequestCancel();
  }else if (callingShowMessageDialogFrom == 'reject') {
	  submitReject();
  }else if (callingShowMessageDialogFrom == 'rejectLine') {
	  submitRejectLine();
  }else if (callingShowMessageDialogFrom == 'approveCancel') {
	  submitApproveCancel();
  }else if (callingShowMessageDialogFrom == 'rejectCancel') {
	  submitRejectCancel();
  }else if (callingShowMessageDialogFrom == 'approveQtyChange') {
	  submitApproveQtyChange();
  }
}

function returnToCart() {
    closeAllchildren();
    saveCurrenData();
	$("action").value = "returnToCart";
	//prepare grid for data sending
	mygrid.parentFormOnSubmit();
	document.genericForm.submit();
	showTransitWin("");
}

//this function will intialize dhtmlXWindow if it's null
function initializeDhxWins() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function showApproverListDetail() {
 callToServer("approverslistdetail.do?action=getApproverListDetail&prNumber="+$("prNumber").value+"&lineItem="+cellValue(selectedRowId,"lineItem"));
}

function showApproverListWin(jsonListApproverMainData, winTitle)
{
	initializeDhxWins();
    var listApproverDataWinArea = document.getElementById('ListApproverDataWinArea');
    listApproverDataWinArea.style.display="";

    if (jsonListApproverMainData == null || jsonListApproverMainData.rows.length == 0) {
        $("noListApproverMessageRow").style.display = "";
        $("listApproverData").style.display = "none";
    }else {
        $("noListApproverMessageRow").style.display = "none";
        $("listApproverData").style.display = "";
    }

    mybeangrid = new dhtmlXGridObject('listApproverDataInnerDiv');
    var listApproverDataInnerDiv = document.getElementById('listApproverDataInnerDiv');
    initGridWithConfigForPopUp(mybeangrid,listApproverConfig,false,true);
    if( typeof( jsonListApproverMainData ) != 'undefined' ) {
        mybeangrid.parse(jsonListApproverMainData,"json");

    }

    approverWin = dhxWins.createWindow("showApproverListDetail",0, 0, 780, 300);
    approverWin.setText(winTitle);
    approverWin.clearIcon();  // hide icon
    //approverWin.denyResize(); // deny resizing
    approverWin.denyPark();   // deny parking

    approverWin.attachObject("ListApproverDataWinArea");
    approverWin.attachEvent("onClose", function(approverWin){approverWin.hide();});
    approverWin.attachEvent("onResizeFinish", function(approverWin){
          var dim = approverWin.getDimension();
          var parentDiv = listApproverDataWinArea;
          parentDiv.style.width = dim[0] + 'px';
          parentDiv.style.height = dim[1] + 'px';
          var containingDiv = listApproverDataInnerDiv;
          containingDiv.style.width = (dim[0] - 20) + 'px';
          containingDiv.style.height = (dim[1] - 80) + 'px';
          var grid1 = mybeangrid;
          grid1.setSizes();
          resizeGridToWidth(grid1, dim[0]);
      });

    approverWin.center();
    // setting window position as default x,y position doesn't seem to work, also hidding buttons.

    approverWin.button("minmax1").hide();
    approverWin.button("park").hide();
    dhxWins.window("showApproverListDetail").show();
}

function showApproverListWinClose() {
  dhxWins.window("showApproverListDetail").hide();
}


//use approval popup
function showApprovedBy(prNumber,lineItem) {
	callToServer("showapprovedby.do?action=searchLineItem&prNumber="+prNumber+"&lineItem="+lineItem+"");
}

// Call this function to get "Finance Approver Information" popup
function showFinanceApprover(prNumber) {
	callToServer("showapprovedby.do?action=searchPr&prNumber="+prNumber);
}

function showApprovedByWin(approvedByJson,action) {
	$("approvedName").innerHTML = approvedByJson.approvedBy;
	$("approverPhone").innerHTML = approvedByJson.phone;
	$("approverEmail").innerHTML = approvedByJson.email;
	$("approvalReason").innerHTML = approvedByJson.reason;
	var inputDailogWin = document.getElementById("showApprovedByArea");
	inputDailogWin.style.display="";

	initializeDhxWins();
	if (!dhxWins.window("showApprovedByWin")) {
		// create window first time
		approvalWin = dhxWins.createWindow("showApprovedByWin",0,0, 400, 200);
		if(action == 'searchLineItem')
			approvalWin.setText(messagesData.lineapprovedby + ":");
		else
			approvalWin.setText(messagesData.financeapproverinformation + ":");

		approvalWin.clearIcon(); // hide icon
		approvalWin.denyResize(); // deny resizing
		approvalWin.denyPark(); // deny parking
		approvalWin.attachObject("showApprovedByArea");
		approvalWin.attachEvent("onClose", function(approvalWin){approvalWin.hide();});
		approvalWin.center();
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		approvalWin.setPosition(328, 131);
		approvalWin.button("minmax1").hide();
		approvalWin.button("park").hide();
	}else {
		// just show
		dhxWins.window("showApprovedByWin").show();
	}
}

function okBtn() {
	dhxWins.window("showApprovedByWin").hide();
}

function showFinancialApprovers(prNumber) {
 callToServer("showapprovers.do?action=getFinanceApproverData&prNumber="+prNumber);
}

function showUseApprovers(prNumber,lineItem) {
	callToServer("showapprovers.do?action=getApproverData&prNumber="+prNumber+"&lineItem="+lineItem);
}


function showWin(jsonApproverMainData, approverLabel, firstLine) {
	initializeDhxWins();
	if (!dhxWins.window("showPendingApproversWin")) {
		document.getElementById('ApproverDataWinArea').style.display="";
		mybeangrid = new dhtmlXGridObject('approverDataInnerDiv');
		mybeangrid.setImagePath("../../dhtmlxGrid/codebase/imgs/");
		pendingApproversConfig[0].columnName = approverLabel;
		initGridWithConfigForPopUp(mybeangrid,pendingApproversConfig,false,true);
		if( typeof( jsonApproverMainData ) != 'undefined' ) {
			mybeangrid.parse(jsonApproverMainData,"json");

		}
		if(firstLine.length>0)
		{
			document.getElementById('noApproverMessageRow').style.display="";
			document.getElementById('noApproverMessageData').innerHTML=firstLine;

		}
		else
		{
			document.getElementById('noApproverMessageRow').style.display="none";
			document.getElementById('noApproverMessageData').innerHTML="";
		}		
		approverWin = dhxWins.createWindow("showPendingApproversWin",0, 0, 461, 300);
		approverWin.setText(messagesData.mrtotalcostapprovaldetail);
		approverWin.clearIcon();  // hide icon
		approverWin.denyResize(); // deny resizing
		approverWin.denyPark();   // deny parking

		approverWin.attachObject("ApproverDataWinArea");
		approverWin.attachEvent("onClose", function(approverWin){approverWin.hide();});
		approverWin.center();
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		approverWin.setPosition(328, 131);

		approverWin.button("minmax1").hide();
		approverWin.button("park").hide();
	}else {
		// just show
		dhxWins.window("showPendingApproversWin").show();
	}
}


function closeOk() {
	dhxWins.window("showPendingApproversWin").hide();
}

function setUserView() {
	var viewType = $("viewType").value;
	if ("REQUEST" == viewType) {
		$("submitSpan").innerHTML = '<a href="javascript:submitMr()"> ' + messagesData.submit +'</a>';
		$("submitSpan").style["display"] = "";
		$("saveSpan").innerHTML = '&nbsp;| <a href="javascript:saveMr()"> ' + messagesData.save +'</a>';
		$("saveSpan").style["display"] = "";
		$("deleteSpan").innerHTML = '&nbsp;| <a href="javascript:deleteMr()"> ' + messagesData.deletemr +'</a>';
		$("deleteSpan").style["display"] = "";
		$("deleteLineSpan").innerHTML = '&nbsp;| <a href="javascript:deleteLine()"> ' + messagesData.deleteline +'</a>';
		$("deleteLineSpan").style["display"] = "";
		if (returnToCartOk) {
			$("returnToCartSpan").innerHTML = '&nbsp;| <a href="javascript:returnToCart()"> ' + messagesData.returntocart +'</a>';
			$("returnToCartSpan").style["display"] = "";
		}else {
			$("returnToCartSpan").innerHTML = '';
			$("returnToCartSpan").style["display"] = "none";
		}
		$("approveSpan").innerHTML = '';
		$("approveSpan").style["display"] = "none";
        $("approveChargeDataSpan").innerHTML = '';
        $("approveChargeDataSpan").style["display"] = "none";
        $("approveChargeDataLineSpan").innerHTML = '';
        $("approveChargeDataLineSpan").style["display"] = "none";
        $("releaseSpan").innerHTML = '';
		$("releaseSpan").style["display"] = "none";
		$("approveUseSpan").innerHTML = '';
		$("approveUseSpan").style["display"] = "none";
		$("approveUseLineSpan").innerHTML = '';
		$("approveUseLineSpan").style["display"] = "none";
		$("rejectSpan").innerHTML = '';
		$("rejectSpan").style["display"] = "none";
		$("rejectLineSpan").innerHTML = '';
		$("rejectLineSpan").style["display"] = "none";
	}else if ("APPROVE" == viewType) {
		$("submitSpan").innerHTML = '';
		$("submitSpan").style["display"] = "none";
		$("saveSpan").innerHTML = '';
		$("saveSpan").style["display"] = "none";
		$("deleteSpan").innerHTML = '';
		$("deleteSpan").style["display"] = "none";
		$("deleteLineSpan").innerHTML = '';
		$("deleteLineSpan").style["display"] = "none";
		if("compchk" == $v("prStatus")) {
            $("approveSpan").innerHTML = '<a href="javascript:approveMr()"> ' + messagesData.approve +'</a>';
		    $("approveSpan").style["display"] = "";
            $("approveChargeDataSpan").innerHTML = '';
            $("approveChargeDataSpan").style["display"] = "none";
            $("approveChargeDataLineSpan").innerHTML = '';
            $("approveChargeDataLineSpan").style["display"] = "none";
        }else if ("compchk4" == $v("prStatus")) {
            $("approveSpan").innerHTML = '';
		    $("approveSpan").style["display"] = "none";
            $("approveChargeDataSpan").innerHTML = '<a href="javascript:approveChargeData()"> ' + messagesData.approve +'</a>';
            $("approveChargeDataSpan").style["display"] = "";
            $("approveChargeDataLineSpan").innerHTML = '&nbsp;| <a href="javascript:approveChargeDataLine()"> ' + messagesData.approveline +'</a>';
            $("approveChargeDataLineSpan").style["display"] = "";
        }else {
            $("approveSpan").innerHTML = '';
		    $("approveSpan").style["display"] = "none";
            $("approveChargeDataSpan").innerHTML = '';
            $("approveChargeDataSpan").style["display"] = "none";
            $("approveChargeDataLineSpan").innerHTML = '';
            $("approveChargeDataLineSpan").style["display"] = "none";
        }
        $("releaseSpan").innerHTML = '';
		$("releaseSpan").style["display"] = "none";
		$("approveUseSpan").innerHTML = '';
		$("approveUseSpan").style["display"] = "none";
		$("approveUseLineSpan").innerHTML = '';
		$("approveUseLineSpan").style["display"] = "none";
		$("rejectSpan").innerHTML = '&nbsp;| <a href="javascript:reject()"> ' + messagesData.reject +'</a>';
		$("rejectSpan").style["display"] = "";
		$("rejectLineSpan").innerHTML = '';
		$("rejectLineSpan").style["display"] = "none";
		$("returnToCartSpan").innerHTML = '';
		$("returnToCartSpan").style["display"] = "none";
	}else if ("RELEASE" == viewType) {
		$("submitSpan").innerHTML = '';
		$("submitSpan").style["display"] = "none";
		$("saveSpan").innerHTML = '';
		$("saveSpan").style["display"] = "none";
		$("deleteSpan").innerHTML = '';
		$("deleteSpan").style["display"] = "none";
		$("deleteLineSpan").innerHTML = '';
		$("deleteLineSpan").style["display"] = "none";
		$("approveSpan").innerHTML = '';
		$("approveSpan").style["display"] = "none";
        $("approveChargeDataSpan").innerHTML = '';
        $("approveChargeDataSpan").style["display"] = "none";
        $("approveChargeDataLineSpan").innerHTML = '';
        $("approveChargeDataLineSpan").style["display"] = "none";
        $("releaseSpan").innerHTML = '<a href="javascript:releaseMr()"> ' + messagesData.release +'</a>';
		$("releaseSpan").style["display"] = "";
		$("approveUseSpan").innerHTML = '';
		$("approveUseSpan").style["display"] = "none";
		$("approveUseLineSpan").innerHTML = '';
		$("approveUseLineSpan").style["display"] = "none";
		$("rejectSpan").innerHTML = '&nbsp;| <a href="javascript:reject()"> ' + messagesData.reject +'</a>';
		$("rejectSpan").style["display"] = "";
		$("rejectLineSpan").innerHTML = '';
		$("rejectLineSpan").style["display"] = "none";
		$("returnToCartSpan").innerHTML = '';
		$("returnToCartSpan").style["display"] = "none";
	}else if ("USE_APPROVE" == viewType) {
		$("submitSpan").innerHTML = '';
		$("submitSpan").style["display"] = "none";
		$("saveSpan").innerHTML = '';
		$("saveSpan").style["display"] = "none";
		$("deleteSpan").innerHTML = '';
		$("deleteSpan").style["display"] = "none";
		$("deleteLineSpan").innerHTML = '';
		$("deleteLineSpan").style["display"] = "none";
		$("approveSpan").innerHTML = '';
		$("approveSpan").style["display"] = "none";
        $("approveChargeDataSpan").innerHTML = '';
        $("approveChargeDataSpan").style["display"] = "none";
        $("approveChargeDataLineSpan").innerHTML = '';
        $("approveChargeDataLineSpan").style["display"] = "none";
        $("releaseSpan").innerHTML = '';
		$("releaseSpan").style["display"] = "none";
		$("approveUseSpan").innerHTML = '<a href="javascript:approveUse()"> ' + messagesData.approve +'</a>';
		$("approveUseSpan").style["display"] = "";
		$("approveUseLineSpan").innerHTML = '&nbsp;| <a href="javascript:approveUseLine()"> ' + messagesData.approveline +'</a>';
		$("approveUseLineSpan").style["display"] = "";
		$("rejectSpan").innerHTML = '&nbsp;| <a href="javascript:reject()"> ' + messagesData.reject +'</a>';
		$("rejectSpan").style["display"] = "";
		$("rejectLineSpan").innerHTML = '&nbsp;| <a href="javascript:rejectLine()"> ' + messagesData.rejectline +'</a>';
		$("rejectLineSpan").style["display"] = "";
		$("returnToCartSpan").innerHTML = '';
		$("returnToCartSpan").style["display"] = "none";
	}else if ("VIEW" == viewType) {
		$("submitSpan").innerHTML = '';
		$("submitSpan").style["display"] = "none";
		if($("canEditMr").value == "Y" || "Y" == $v("canMaintainOrderHeader")) {
			$("saveSpan").innerHTML = '<a href="javascript:saveMr()"> ' + messagesData.save +'</a>';
			$("saveSpan").style["display"] = "";
		}else {
			$("saveSpan").innerHTML = '';
			$("saveSpan").style["display"] = "none";
		}
		$("deleteSpan").innerHTML = '';
		$("deleteSpan").style["display"] = "none";
		$("deleteLineSpan").innerHTML = '';
		$("deleteLineSpan").style["display"] = "none";
		$("approveSpan").innerHTML = '';
		$("approveSpan").style["display"] = "none";
        $("approveChargeDataSpan").innerHTML = '';
        $("approveChargeDataSpan").style["display"] = "none";
        $("approveChargeDataLineSpan").innerHTML = '';
        $("approveChargeDataLineSpan").style["display"] = "none";
        $("releaseSpan").innerHTML = '';
		$("releaseSpan").style["display"] = "none";
		$("approveUseSpan").innerHTML = '';
		$("approveUseSpan").style["display"] = "none";
		$("approveUseLineSpan").innerHTML = '';
		$("approveUseLineSpan").style["display"] = "none";
		$("rejectSpan").innerHTML = '';
		$("rejectSpan").style["display"] = "none";
		$("rejectLineSpan").innerHTML = '';
		$("rejectLineSpan").style["display"] = "none";
		$("returnToCartSpan").innerHTML = '';
		$("returnToCartSpan").style["display"] = "none";
	}else {
		//alert("not suppose to be here");
	}


	if("posubmit" == $("prStatus").value) {
		if($("canEditMr").value == "Y") {
			$("allocationSpan").innerHTML = '&nbsp;| <a href="javascript:showMrAllocationReport()"> ' + messagesData.allocation +'</a>';
			$("allocationSpan").style["display"] = "";
			$("printScreenSpan").innerHTML = '&nbsp;| <a href="javascript:printScreen()"> ' + messagesData.printthispage +'</a>';
			$("printScreenSpan").style["display"] = "";
		}else {
			$("allocationSpan").innerHTML = '<a href="javascript:showMrAllocationReport()"> ' + messagesData.allocation +'</a>';
			$("allocationSpan").style["display"] = "";
			$("printScreenSpan").innerHTML = '&nbsp;| <a href="javascript:printScreen()"> ' + messagesData.printthispage +'</a>';
			$("printScreenSpan").style["display"] = "";
		}
	}else {
        if ("Y" == $v("showMrAllocationOption")) {
            $("allocationSpan").innerHTML = '&nbsp;| <a href="javascript:showMrAllocationReport()"> ' + messagesData.allocation +'</a>';
			$("allocationSpan").style["display"] = "";    
        }else {
            $("allocationSpan").innerHTML = '';
		    $("allocationSpan").style["display"] = "none";
        }
        if ("VIEW" == viewType) {
			if($("canEditMr").value == "Y") {
				$("printScreenSpan").innerHTML = '&nbsp;| <a href="javascript:printScreen()"> ' + messagesData.printthispage +'</a>';
				$("printScreenSpan").style["display"] = "";
			}else {
				$("printScreenSpan").innerHTML = '| <a href="javascript:printScreen()"> ' + messagesData.printthispage +'</a>';
				$("printScreenSpan").style["display"] = "";
			}
		}else {
			$("printScreenSpan").innerHTML = '&nbsp;| <a href="javascript:printScreen()"> ' + messagesData.printthispage +'</a>';
			$("printScreenSpan").style["display"] = "";
		}
	}
	
	// hide options when home company login is used
	if(homeCompanyLogin){
		$("allocationSpan").style["display"] = "none";
		$("printScreenSpan").style["display"] = "none";
		$("scrapObsoleteSpan").style["display"] = "none";   
	}

	//unless this is a draft MR users should not be able to change delivery instructions
	if ("REQUEST" != viewType) {
		//delivery info
		$("deliveryType").disabled = true;
		$("deliverDate").disabled = true;
        try
        {
        $("dock").disabled = true;
		$("deliverTo").disabled = true;
        } catch(ex){}

    }

	if($("canEditMr").value != "Y" && "Y" != $v("canMaintainOrderHeader")) {
		//header info
/*		$("endUser").disabled = true;
		$("department").disabled = true;
		$("contactInfo").disabled = true;*/
		//charge type
		$("chargeTypeD").disabled = true;
		$("chargeTypeI").disabled = true;
		//scrap
		$("scrapObsolete").disabled = true;
		//po info
		$("poCombo").disabled = true;
		$("poInput").disabled = true;
		$("poLineInput").disabled = true;
		$("poQty").disabled = true;
		$("poUom").disabled = true;
		$("poUnitPrice").disabled = true;
		$("currencyCombo").disabled = true;
	} 
	
	if ($v("canEditMr") == 'Y' && $v("reorderMRFeature") == 'true') {
		$("originalOrderName").value = $v("orderName");
		j$("").ready(function() {
			function log(event, data, formatted) {
				validateOrderName(formatted.replace(/(?:\r\n|\r|\n)/g, ""));
			}
			
			j$("#orderName").autocomplete("lookupordername.do",{
				extraParams : {
					companyId : function() {
						return j$("#companyId").val();
					}
				},
				width: 200,
				cacheLength:0, //if cache is allow, when user manually enters one of the previous suggestions, the suggestion list will show the entered suggestion 
				scrollHeight: 150,
				matchCase: true,
				formatItem: function(data, i, n, value) {
					//value sometimes include line break
					var formattedVal = value.replace(/(?:\r\n|\r|\n)/g, "");
					
					//since the backend algorithm will check input string and put it at the top of the list if it is available,
					//we only need to compare input string with first value of the suggestion
					if (i == 1)
						validateOrderName(formattedVal);
					
					return  formattedVal.replace(/ /g, "&nbsp;");
				},
				highlight: function(value, term) {
					return value.replace(new RegExp("(?![^&;]+;)(?!<[^<>]*)(" + term.replace(/ /g, "&nbsp;").replace(/([\^\$\(\)\[\]\{\}\*\.\+\?\|\\])/gi, "\\$1") + ")(?![^<>]*>)(?![^&;]+;)", "gi"), "<strong>$1</strong>");
				},
				parse: function (data) {
					var parsed = [];
					var rows = data.split("\n");
					if ($v('orderName') == $v('originalOrderName'))
						rows.unshift($v("orderName"));
					for (var i=0; i < rows.length; i++) {
						var row = j$.trim(rows[i]);
						if (row) {
							row = row.split("|");
							parsed[parsed.length] = {
								data: row,
								value: row[0],
								result: row[0]
							};
						}
					}
					return parsed;
				}
			});
			
			j$('#orderName').keyup(function () {
				//when user delete the whole input string in one go, we want to return the text box to normal state
				if ($v("orderName").length == 0)
					validateOrderName('');
			});

			j$("#orderName").result(log).next().click(function() {
				j$(this).prev().search();
			});
		});
	}
} //end of setUserView

function showMrAllocationReport()
{
   var companyId  =  $("companyId");
   var mrNumber  =  $("prNumber");
   var loc = "mrallocationreportmain.do?companyId="+companyId.value+"&mrNumber="+mrNumber.value+"&lineItem=";
   openWinGeneric(loc,"showMrAllocationReport22","800","550","yes","80","80","no")
}


function getNewShipTo()
{
   var addressLine1  =  encodeURIComponent($v("addressLine1"));
   var addressLine2  =  encodeURIComponent($v("addressLine2"));
   var addressLine3  =  encodeURIComponent($v("addressLine3"));
   var city  =  $v("city");
   var stateAbbrev  =  $v("stateAbbrev");
   var zip  =  $v("zip");
   var countryAbbrev  =  $v("countryAbbrev");
   var loc = "newshipto.do?addressLine1="+addressLine1+"&addressLine2="+addressLine2+"&addressLine3="+addressLine3+
   			 "&city="+city+"&state="+stateAbbrev+"&zip="+zip+
   			 "&country="+countryAbbrev;
   openWinGeneric(loc,"newShipTo","750","200","yes","50","50","20","20","no");
}

function fillAddress(a) {
  $("addressLine1Span").innerHTML = a.addressLine1;
  $("addressLine2Span").innerHTML = a.addressLine2;
  $("addressLine3Span").innerHTML = a.addressLine3;
  $("addressSpan").innerHTML = a.city+", "+a.stateAbbrev+", "+a.zip+", "+a.countryAbbrev;
  $("addressLine1").value = a.addressLine1;
  $("addressLine2").value = a.addressLine2;
  $("addressLine3").value = a.addressLine3;
  $("city").value = a.city;
  $("stateAbbrev").value = a.stateAbbrev;
  $("zip").value = a.zip;
  $("countryAbbrev").value = a.countryAbbrev;

  if( $v("oneTimeShipToChanged") == "N") {
  	  $("shiptoLabel").innerHTML = messagesData.shipto +":&nbsp;";
	  $("dockLabel").style.display="none";
	  $("dockSpan").style.display="none";
	  $("delivertoLabel").style.display="none";
	  $("delivertoSpan").style.display="none";
	  $("lineSpan").style.display="none";
	  $("oneTimeShipToChanged").value = 'Y';
  }
}

function saveInitialData() {
	//NOTE THAT GRID STARTS WITH 1 AND NOT 0 (ZERO)
	for (var i = 1; i <= mygrid.getRowsNum(); i++) {
		selectedRowId = i;
		displayLineDetail();
		saveCurrenData();
        //setting the display of mfg lot correctly
        if ($v("showOnHoldForProgram") == 'Y') {
            changeHoldAsCustomerOwned(i);
        }
    }
	continueLoadingData();
}

function approveUseLine() {
	//mark line as approved
	mygrid.cells(selectedRowId,mygrid.getColIndexById("lineFlag")).setValue("Approve");
	approveUseMr();
}

function approveUse() {
	//mark all lines as approve
	for (var i = 1; i <= mygrid.getRowsNum(); i++) {
		mygrid.cells(i,mygrid.getColIndexById("lineFlag")).setValue("Approve");
	}
	approveUseMr();
}

function approveUseMr() {
	saveCurrenData();
	$("action").value = "approveUse";
	//prepare grid for data sending
	mygrid.parentFormOnSubmit();
	document.genericForm.submit();
	showTransitWin("");
}

/*
showMessageDialog(messagesData.comments,"",true,"requestCancel");
mygrid.cells(selectedRowId,mygrid.getColIndexById("approveRejectComments")).setValue($("messageText").value);
*/

function rejectLine() {
	showMessageDialog(messagesData.comments,"",true,"rejectLine");
}

function submitRejectLine() {
	//mark line as Reject
	mygrid.cells(selectedRowId,mygrid.getColIndexById("lineFlag")).setValue("Reject");
	mygrid.cells(selectedRowId,mygrid.getColIndexById("approveRejectComments")).setValue($("messageText").value);
	rejectMr();
}

function reject() {
	showMessageDialog(messagesData.comments,"",true,"reject");
}

function submitReject() {
	//mark all lines as Reject
	for (var i = 1; i <= mygrid.getRowsNum(); i++) {
		mygrid.cells(i,mygrid.getColIndexById("lineFlag")).setValue("Reject");
		mygrid.cells(selectedRowId,mygrid.getColIndexById("approveRejectComments")).setValue($("messageText").value);
	}
	rejectMr();
}

function rejectMr() {
	saveCurrenData();
	$("action").value = "reject";
	//prepare grid for data sending
	mygrid.parentFormOnSubmit();
	document.genericForm.submit();
	showTransitWin("");
}

function releaseMr() {
	saveCurrenData();
	if (auditSubmitData()) {
		$("action").value = "release";
		//prepare grid for data sending
		mygrid.parentFormOnSubmit();
   	    document.genericForm.submit();
		showTransitWin("");
	}else {
		return false;
	}
}

function approveMr() {
	saveCurrenData();
	if (auditSubmitData()) {
		$("action").value = "approve";
		//prepare grid for data sending
		mygrid.parentFormOnSubmit();
   	    document.genericForm.submit();
		showTransitWin("");
	}else {
		return false;
	}
}

function approveChargeDataLine() {
	//mark line as approved
	mygrid.cells(selectedRowId,mygrid.getColIndexById("lineFlag")).setValue("Approve");
	approveChargeDataMr();
}

function approveChargeData() {
	//mark all lines as approve
	for (var i = 1; i <= mygrid.getRowsNum(); i++) {
		mygrid.cells(i,mygrid.getColIndexById("lineFlag")).setValue("Approve");
	}
	approveChargeDataMr();
}

function approveChargeDataMr() {
	saveCurrenData();
	if (auditSubmitData()) {
		$("action").value = "approveChargeData";
		//prepare grid for data sending
		mygrid.parentFormOnSubmit();
   	    document.genericForm.submit();
		showTransitWin("");
	}else {
		return false;
	}
}

function submitMr() {
	canShelfLife();
}

//I have to create this function because the inline - div window for relax shelf life does not
//stop to wait
function goSubmitMr() {
    closeAllchildren();
    saveCurrenData();
	if (auditSubmitData()) {
		$("action").value = "submit";
		//prepare grid for data sending
		mygrid.parentFormOnSubmit();
   	document.genericForm.submit();
		showTransitWin("");
	}else {
		return false;
	}
}

function auditSubmitData() {
	var result = true;
	var i = 1;
	for (; i <= mygrid.getRowsNum(); i++) {
		//QTY
		var tmpVal = cellValue(i,"qty");
		if (tmpVal == null || tmpVal.length == 0) {
			alert(messagesData.missingqty);
			result = false;
			break;
		}else {
			if (!isFloat(tmpVal)) {
				alert(messagesData.missingqty);
				result = false;
				break;
			}
			var tmpVal2 = cellValue(i,"totalQuantityIssued");
			if (tmpVal2 != null && tmpVal2.length > 0) {
				if (tmpVal*1 < tmpVal2*1) {
					alert(messagesData.invalidqtypart1+" "+tmpVal2+" "+messagesData.invalidqtypart2);
					result = false;
					break;
				}
			}
		}
		//need date
		var tmpVal2 = cellValue(i,"defaultNeedByDate");
		if (tmpVal2 == null || tmpVal2.length == 0) {
			alert(messagesData.missingdeliverdate);
			result = false;
			break;
		}
        //hold as customer owned
        if ($v("showOnHoldForProgram") == 'Y') {
            if(cellValue(i,"holdAsCustomerOwnedDataField") == "Y") {
                tmpVal = cellValue(i,"ownerSegmentId");
                if (tmpVal == null || tmpVal.length == 0 || tmpVal.length > 10 || tmpVal == "") {
                    alert(messagesData.missingOwnerSegmentId);
                    result = false;
                    break;
                }
            }
        }
		
       if('Y' != $v("allowOneTimeShipTo") || ('Y' == $v("allowOneTimeShipTo") && 'Y' != $v("updatable") && 'N' == $v("oneTimeShipToChanged"))) {
        //dock
		tmpVal = cellValue(i,"shipToLocationId");
		if (tmpVal == null || tmpVal.length == 0 || tmpVal == "") {
			alert(messagesData.missingdeliverto);
			result = false;
			break;
		}
		//delivery point
		tmpVal = cellValue(i,"deliveryPoint");
		if (tmpVal == null || tmpVal.length == 0 || tmpVal == "") {
			alert(messagesData.missingdeliverto);
			result = false;
			break;
		}
       }
		//PO
		if (!auditPoData(i)) {
			result = false;
			break;
		}
		//Charge numbers
		var key = cellValue(i,"lineItem");
		if (!auditChargeNumberData(i,prAccountColl[key])) {
			result = false;
			break;
		}	
	}
	
	//if encounterred error then select line with errors
	if (!result) {
		mygrid.selectRowById(i);
  		selectedRowId = i;
  		displayLineDetail();
	}
	
	if ($v("reorderMRFeature") == 'true') {
		if (!isAvailable) {
			result = false;
			alert(messagesData.xExist.replace(/\{0\}/g, messagesData.orderName).replace(/\{1\}/g, $v("orderName")));
		} else if ($v("orderName").length > 30) {
			result = false;
			alert(messagesData.maxLength.replace(/\{0\}/g, messagesData.orderName).replace(/\{1\}/g, 30));
		}
	}
	
	return result;
}

function canShelfLife() {
	var i = 1;
	var allLinesHaveShelLife = true;
	for (; i <= mygrid.getRowsNum(); i++) {
		//relax shelf life
		if (cellValue(i,"canRelaxShelfLife") == "Y") {
			$("materialLine").innerHTML=cellValue(i,"lineItem");
			$("daysId").innerHTML=cellValue(i,"remainingShelfLife");
			showRelaxShelfLifeWin();
			allLinesHaveShelLife = false;
			break;
		}
	}
	if(allLinesHaveShelLife)
	{
		goSubmitMr();
	}
}

function showRelaxShelfLifeWin()
{
	var inputDailogWin = document.getElementById("inputDailogWin");
	inputDailogWin.style.display="";

	initializeDhxWins();
	if (!dhxWins.window("relaxShelfLifeWin")) {
		// create window first time
		inputWin = dhxWins.createWindow("relaxShelfLifeWin",0,0, 400, 200);
		inputWin.setText(messagesData.materialonhandtitle);
		inputWin.clearIcon();  // hide icon
		inputWin.denyResize(); // deny resizing
		inputWin.denyPark();   // deny parking

		$("materialLine").style.display ="";
		$("daysId").style.display ="";

		inputWin.attachObject("inputDailogWin");
		inputWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
		inputWin.center();
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		inputWin.setPosition(328, 131);
		inputWin.button("minmax1").hide();
		inputWin.button("park").hide();
		inputWin.button("close").hide();
		inputWin.setModal(true);
	}else{
		// just show
		inputWin.setModal(true);
		dhxWins.window("relaxShelfLifeWin").show();
	}
}

function continueBtn()
{
	var selection = false;
	var selectedValue;
	var selectionRButton= document.getElementsByName("deliveryOption");

	for (var i = 0; i < selectionRButton.length; i++)
	{
		if (selectionRButton[i].checked)
		{
			selection = true;
			selectedValue = selectionRButton[i].value;
			break;
		}
	}
	if (selection) {
		var relaxShelfLife = "n";
		if(selectedValue == "Send") {
			relaxShelfLife = "y";
		}
		dhxWins.window("relaxShelfLifeWin").setModal(false);
		var currentRowId = $("materialLine").innerHTML;
		//once user already answer the question, I won't ask again
		mygrid.cells(currentRowId,mygrid.getColIndexById("relaxShelfLife")).setValue(relaxShelfLife);
		mygrid.cells(currentRowId,mygrid.getColIndexById("canRelaxShelfLife")).setValue("N");
		dhxWins.window("relaxShelfLifeWin").hide();
		canShelfLife();
	}
	else
	{
	 alert(messagesData.pickoption);
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

function auditChargeNumberData(currentRowId,chargeData) {
	var result = true; 
	var prRulesIndexForLine = getPrRulesIndexForLine(currentRowId);
	if (prRulesColl[prRulesIndexForLine].prAccountRequired == "y") {
		var chargeNumberOfColumn = getChargeNumberOfColumn(prRulesIndexForLine);
		var totalPercent = 0.0*1;
        var lineChargeType = cellValue(currentRowId,"chargeType");
        for (var i = 0; i < chargeData.length; i++) {
            //the reason we need to check this is because chargeData array contains both charge types
            if (lineChargeType != chargeData[i].chargeType) continue;
            var tmpPercent = chargeData[i].percentage;
            if (tmpPercent != null && tmpPercent.length > 0 && tmpPercent != '&nbsp;') {
                if (isFloat(tmpPercent)) {
					if (chargeNumberOfColumn == "2") {
						if (chargeData[i].accountNumber == null || chargeData[i].accountNumber.length == 0) {
							alert(messagesData.missingchargenumber);
							return false;
						}else {
							totalPercent += tmpPercent*1;
						}
					}else if (chargeNumberOfColumn == "3") {
						hasChargeNumber = false;
						if (prRulesColl[prRulesIndexForLine].chargeAllowNull1 == 'N') {
							if (chargeData[i].accountNumber == null || chargeData[i].accountNumber.length == 0 ) {
								alert(messagesData.missingchargenumber);
								return false;
							}else {
								hasChargeNumber = true;
							}
						}
						if (prRulesColl[prRulesIndexForLine].chargeAllowNull2 == 'N') {
							if (chargeData[i].accountNumber2 == null || chargeData[i].accountNumber2.length == 0 ) {
								alert(messagesData.missingchargenumber);
								return false;
							}else {
								hasChargeNumber = true;
							}
						}
						if (hasChargeNumber) {
							totalPercent += tmpPercent*1;
						}
					}else if (chargeNumberOfColumn == "4") {
						hasChargeNumber = false;
						if (prRulesColl[prRulesIndexForLine].chargeAllowNull1 == 'N') {
							if (chargeData[i].accountNumber == null || chargeData[i].accountNumber.length == 0 ) {
								alert(messagesData.missingchargenumber);
								return false;
							}else {
								hasChargeNumber = true;
							}
						}
						if (prRulesColl[prRulesIndexForLine].chargeAllowNull2 == 'N') {
							if (chargeData[i].accountNumber2 == null || chargeData[i].accountNumber2.length == 0 ) {
								alert(messagesData.missingchargenumber);
								return false;
							}else {
								hasChargeNumber = true;
							}
						}
						if (prRulesColl[prRulesIndexForLine].chargeAllowNull3 == 'N') {
							if (chargeData[i].accountNumber3 == null || chargeData[i].accountNumber3.length == 0 ) {
								alert(messagesData.missingchargenumber);
								return false;
							}else {
								hasChargeNumber = true;
							}
						}
						if (hasChargeNumber) {
							totalPercent += tmpPercent*1;
						}
					}else if (chargeNumberOfColumn == "5") {
						hasChargeNumber = false;
						if (prRulesColl[prRulesIndexForLine].chargeAllowNull1 == 'N') {
							if (chargeData[i].accountNumber == null || chargeData[i].accountNumber.length == 0 ) {
								alert(messagesData.missingchargenumber);
								return false;
							}else {
								hasChargeNumber = true;
							}
						}
						if (prRulesColl[prRulesIndexForLine].chargeAllowNull2 == 'N') {
							if (chargeData[i].accountNumber2 == null || chargeData[i].accountNumber2.length == 0 ) {
								alert(messagesData.missingchargenumber);
								return false;
							}else {
								hasChargeNumber = true;
							}
						}
						if (prRulesColl[prRulesIndexForLine].chargeAllowNull3 == 'N') {
							if (chargeData[i].accountNumber3 == null || chargeData[i].accountNumber3.length == 0 ) {
								alert(messagesData.missingchargenumber);
								return false;
							}else {
								hasChargeNumber = true;
							}
						}
						if (prRulesColl[prRulesIndexForLine].chargeAllowNull4 == 'N') {
							if (chargeData[i].accountNumber4 == null || chargeData[i].accountNumber4.length == 0 ) {
								alert(messagesData.missingchargenumber);
								return false;
							}else {
								hasChargeNumber = true;
							}
						}
						if (hasChargeNumber) {
							totalPercent += tmpPercent*1;
						}
					}
				}else {
					alert(messagesData.missingpercent);
					return false;
				}
			}
		} //end of for each charge numbers
		//make sure the sum of percent is 100
        	if (totalPercent != 100  && totalPercent.toFixed(2) != 100.00) {
			alert(messagesData.invalidpercent);
			return false;
		}
    } //end of if pr_account_required
	return result;
}

function auditPoData(currentRowId) {
	var result = true;
	if (getPoRequired(currentRowId) == "p") {
		//po number
		var tmpVal = cellValue(currentRowId,"poNumber");
		if (tmpVal == null || tmpVal.length == 0) {
			alert(messagesData.missingpo);
			return false;
		}

		if (getPoSeqRequired(currentRowId) == 'U') {
			tmpVal = cellValue(currentRowId,"releaseNumber");
			if (tmpVal == null || tmpVal.length == 0) {
				alert(messagesData.validvalues+" "+messagesData.poline);
				return false;
			}
		}

		var unitPriceRequired = getUnitPriceRequired(currentRowId);
		if (unitPriceRequired == "Required") {
			//po unit price
			tmpVal = cellValue(currentRowId,"unitOfSaleQuantityPerEach");
			if (tmpVal == null || tmpVal.length == 0) {
				alert(messagesData.missingpoqty);
				return false;
			}
			tmpVal = cellValue(currentRowId,"unitOfSale");
			if (tmpVal == null || tmpVal.length == 0) {
				alert(messagesData.missingpouom);
				return false;
			}
			tmpVal = cellValue(currentRowId,"unitOfSalePrice");
			if (tmpVal == null || tmpVal.length == 0) {
				alert(messagesData.missingpounitprice);
				return false;
			}
			tmpVal = cellValue(currentRowId,"currencyId");
			if (tmpVal == null || tmpVal.length == 0) {
				alert(messagesData.missingcurrency);
				return false;
			}
		}else if (unitPriceRequired == "Display") {
			tmpVal = cellValue(currentRowId,"unitOfSalePrice");
			if (tmpVal == null || tmpVal.length == 0) {
				alert(messagesData.unitpricedisplayerror);
				return false;
			}	
		}
	}
	return result;
}

function deleteMr() {
    closeAllchildren();
    $("action").value = "delete";
	document.genericForm.submit();
	showTransitWin("");
}

function printScreen() {
	//don't need to save data from screen if it's not REQUEST
	if ("REQUEST" == $("viewType").value) {
		saveCurrenData();
		$("action").value = "printScreen";
		//prepare grid for data sending
		mygrid.parentFormOnSubmit();
		document.genericForm.submit();
		showTransitWin("justPleaseWait");
	}else {
		openPrintScreen();
	}
}

function openPrintScreen() {
	//var reportLoc = "https://www.tcmis.com/HaasReports/report/materialrequestreportmain.do?prNumber="+$("prNumber").value;
	var reportLoc = "/HaasReports/report/materialrequestreportmain.do?prNumber="+$("prNumber").value;
	openWinGeneric(reportLoc,"materialRequestPrintScreen1111","800","550","yes","100","100");
}

function saveMr() {
	closeAllchildren();
   saveCurrenData();
   logLineItemUpdates();
	//make sure to run audit when user click on Save after submitted MR
	if ("REQUEST" == $v("viewType")) {
		$("action").value = "save";
		//prepare grid for data sending
		mygrid.parentFormOnSubmit();
		document.genericForm.submit();
		showTransitWin("");
	}else {
		if (auditSubmitData()) {
			$("action").value = "save";
			//prepare grid for data sending
			mygrid.parentFormOnSubmit();
			document.genericForm.submit();
			showTransitWin("");
		}
	}
}

function deleteLine() {
    closeAllchildren();
    if (mygrid.getRowsNum() > 1) {
		saveCurrenData();
		$("action").value = "deleteLine";
		mygrid.cells(selectedRowId,mygrid.getColIndexById("lineFlag")).setValue("Delete");
		//prepare grid for data sending
		mygrid.parentFormOnSubmit();
		document.genericForm.submit();
		showTransitWin("");
	}else {
		//delete the whole MR if there is only 1 line item
		deleteMr();
	}
}

function requestCancel() {
    $("cancelWarningMsg").innerHTML =	messagesData.cancelMrConfirmMsg.replace('{0}',gridCellValue(mygrid,mygrid.getSelectedRowId(),'lineItem')).replace('{1}',gridCellValue(mygrid,mygrid.getSelectedRowId(),'catPartNo'));
    var date = new Date();
	var today = date.getDate() + '-' + month_abbrev_Locale_Java[pageLocale][date.getMonth()] + '-' + date.getFullYear();
	
	$("cancelRequestMessageText").value = 'Cancellation requested by ' + $v('firstName') + ' ' + $v('lastName') + ' on ' + today + '.\n\n';
	var inputDailogWin = document.getElementById("cancelRequestMessageText");
	inputDailogWin.style.display="";
	
	callingShowMessageDialogFrom = 'requestCancel';

	initializeDhxWins();
	inputWin = dhxWins.createWindow("showMessageDialog",0,0, 450, 350);
	inputWin.setText(messagesData.confirmCancellation);
	inputWin.clearIcon();  // hide icon
	inputWin.denyResize(); // deny resizing
	inputWin.denyPark();   // deny parking
	inputWin.attachObject("cancelRequestMessageDailogWin");
	inputWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
	inputWin.center();
	// setting window position as default x,y position doesn't seem to work, also hidding buttons.
	inputWin.setPosition(328, 131);
	inputWin.button("close").hide();
	inputWin.button("minmax1").hide();
	inputWin.button("park").hide();
	inputWin.setModal(true);
}

function submitRequestCancel() {
	saveCurrenData();
	$("action").value = "requestCancel";
	mygrid.cells(selectedRowId,mygrid.getColIndexById("lineFlag")).setValue("Cancel");
	mygrid.cells(selectedRowId,mygrid.getColIndexById("approveRejectComments")).setValue("Request cancel comment: " + $("cancelRequestMessageText").value);
	//prepare grid for data sending
	mygrid.parentFormOnSubmit();
	document.genericForm.submit();
	showTransitWin("");
}

function directedCheck() {
    if (cellValue(selectedRowId,"chargeType") == "i") {
        //the reason that I am calling this method rather than saveCurrentData is because
        //I only want to update the prAccountColl data.  I am sure I can use saveCurrentData
        //but the less things I touch the less I mess up the current function
        savePreviousChargeData();
        mygrid.cells(selectedRowId,mygrid.getColIndexById("chargeType")).setValue("d");
        setChargeType();
    }
}

function inDirectedCheck() {
    if (cellValue(selectedRowId,"chargeType") == "d") {
        //the reason that I am calling this method rather than saveCurrentData is because
        //I only want to update the prAccountColl data.  I am sure I can use saveCurrentData
        //but the less things I touch the less I mess up the current function
        savePreviousChargeData();
        mygrid.cells(selectedRowId,mygrid.getColIndexById("chargeType")).setValue("i");
        setChargeType();
    }
}

function deliveryTypeLoad() {
	var selectedDeliveryType = $("deliveryType").value;
	$("deliverDate").value = cellValue(selectedRowId,"defaultNeedByDate");
	if ("Schedule" == selectedDeliveryType) {
		document.getElementById("scheduleSpan").style["display"] = "";
		document.getElementById("deliverDateSpan").style["display"] = "none";
	}else {
		document.getElementById("scheduleSpan").style["display"] = "none";
		document.getElementById("deliverDateSpan").style["display"] = "";
	}
}

function deliveryTypeChanged() {
    var selectedDeliveryType = $("deliveryType").value;

	if ("Schedule" == selectedDeliveryType) {
		mygrid.cells(selectedRowId,mygrid.getColIndexById("deliveryType")).setValue($v("deliveryType"));
		mygrid.cells(selectedRowId,mygrid.getColIndexById("defaultNeedByDate")).setValue("");
		$("deliverDate").value = '';
        document.getElementById("scheduleSpan").style["display"] = "";
		document.getElementById("deliverDateSpan").style["display"] = "none";
		$("qty"+selectedRowId+"").readOnly = true;
        $("qty"+selectedRowId+"").disabled = true;
	}else {
        var tmpVal = cellValue(selectedRowId,"deliveryType");
        if (tmpVal = 'Schedule')
        {
         if (confirm(messagesData.changedeliverytypewarn))
         {
         mygrid.cells(selectedRowId,mygrid.getColIndexById("deliveryType")).setValue($v("deliveryType"));
         mygrid.cells(selectedRowId,mygrid.getColIndexById("defaultNeedByDate")).setValue("");
     	$("deliverDate").value = '';
         var companyId  =  $("companyId");
         var prNumber  =  $("prNumber");
         var lineItem  =  cellValue(selectedRowId,"lineItem");

        var loc = "deleteschedule.do?action=delete&companyId="+companyId.value+"&prNumber="+prNumber.value+"&lineItem="+lineItem+"";
        callToServer(loc);

        var permission = cellValue(selectedRowId,"permission");
        if (permission == 'Y') {
            $("qty"+selectedRowId+"").readOnly = false;
            $("qty"+selectedRowId+"").disabled = false;
        }       
        document.getElementById("scheduleSpan").style["display"] = "none";
		document.getElementById("deliverDateSpan").style["display"] = "";
       }
         else{
         	$("deliveryType").selectedIndex = 1;
         }
      }

    }
}

function setDeliverDate(value)
{
	mygrid.cells(selectedRowId,mygrid.getColIndexById("defaultNeedByDate")).setValue(value);
	$("deliverDate").value = cellValue(selectedRowId,"defaultNeedByDate");
	
}

function setDeliverTo(id)
{
	var select = $(id);
	if(select.value == '' || $('dock').value == '')
		mygrid.cells(selectedRowId,mygrid.getColIndexById("deliveryPointDesc")).setValue('');
	else
		{
			mygrid.cells(selectedRowId,mygrid.getColIndexById("deliveryPointDesc")).setValue(select.options[select.selectedIndex].text);
		}
}

function dockChanged(id) {
	loadDeliverTo();
	var select = $(id);
	if(select.value == '')
		mygrid.cells(selectedRowId,mygrid.getColIndexById("shipToLocationIdDesc")).setValue('');
	else
		mygrid.cells(selectedRowId,mygrid.getColIndexById("shipToLocationIdDesc")).setValue(select.options[select.selectedIndex].text);
	setDeliverTo($('deliverTo').id);
}

function doInitGrid() {
	mygrid = new dhtmlXGridObject('materialRequestDivId');

	// initGridWithConfig(inputGrid,config,rowSpan,submitDefault,singleClickEdit)
	// singleClickEdit: this is for type:"txt",
	initGridWithConfig(mygrid, config, -1, true, true);
	//make sure this is set for onAfterHaasGridRendered to work correctly
	mygrid._haas_bg_render_enabled = true;
	mygrid.enableSmartRendering(true);

	if (typeof (jsonMainData) != 'undefined') {
		mygrid.parse(jsonMainData, "json");
	}

	mygrid.attachEvent("onBeforeSelect",doOnBeforeSelect);
	mygrid.attachEvent("onRowSelect",selectRow);
    mygrid.attachEvent("onRightClick",rightClick);
	mygrid.attachEvent("onAfterHaasGridRendered", saveInitialData);
} //end of method

function changeCritical(rowId) {

	mygrid.selectRow(mygrid.getRowIndex(rowId),null,false,false);
			
	if(cellValue(rowId,"critical") == "true")
		saveRowClass = 'grid_red';
	else if(rowId % 2 == 0)
		saveRowClass = 'odd_haas';
	else 
		saveRowClass = 'ev_haas';
	
	setRowClass(rowId, ''+saveRowClass+'Selected');
}

function doChargeNumberInitGrid2ColumnsProofOfConcept(column1,typeStr){
	chargeNumberGrid2Columns = new dhtmlXGridObject('chargeNumber2ColumnsDivId');
	chargeNumberGrid2Columns.setImagePath("../../dhtmlxGrid/codebase/imgs/");

	chargeNumberGrid2Columns.setHeader(
		"permission,"+
		"columnpermission,"+
		column1+","+
		"columnperm,"+
		"%"
	);

   chargeNumberGrid2Columns.setColumnIds("permission2,chargeNumber2Permission,chargeNumber2,percent2Permission,percent2");

	//_setPermColumn("permission2", false);
	//_setPermColumn("chargeNumberPermission2",false);
	_setPermColumn("chargeNumber2","chargeNumber2Permission");
	_setPermColumn("percent2", "percent2Permission");

	chargeNumberGrid2Columns.setInitWidths("0,0,330,0,100");
   chargeNumberGrid2Columns.setColAlign("left,left,left,left,left")
   chargeNumberGrid2Columns.setColTypes(typeStr);
	//set size for all 'hed' here
	inputSize["chargeNumber2"] = 59;
	inputSize["percent2"] = 15;

	chargeNumberGrid2Columns.attachEvent("onRowSelect",chargeNumberSelectRow);
   chargeNumberGrid2Columns.attachEvent("onRightClick",chargeNumberSelectRow);

   chargeNumberGrid2Columns.enableTooltips("false,false,false,false,false");
   /*This is to enable edit on click. If a cell is editiable it will show as soon as the row is selected.*/
   chargeNumberGrid2Columns.enableEditEvents(true,true,false,true,false);
   chargeNumberGrid2Columns.setSkin("haas");
	chargeNumberGrid2Columns.submitOnlyChanged(false);
	chargeNumberGrid2Columns.setFieldName("{GRID_ID}[{ROW_INDEX}].{COLUMN_ID}");
	chargeNumberGrid2Columns.submitColumns([false,false,true,false,true]);
   //chargeNumberGrid2Columns.setColumnHidden(0,true); // permission
    /*Set the type of sort to do on this column.If the gird has rowspans >1 set all columns sotring to be na.
	sorting type str is case sensistive (X,Z come before a,b). haasStr is caseinsensitve sorting.
	For Date column types you need to write custom sorting funciton which will be triggered by onBeforeSorting event.
	For Editable Date column we will not allow sorting, set the sorting to be na.
	For hch you have to write a custom sorting function if needed on the page, other wise set sorting to na*/
	chargeNumberGrid2Columns.setColSorting("na,na,haasStr,na,int");

    /*This keeps the row height the same, true will wrap cell content and height of row will change.*/
    chargeNumberGrid2Columns.enableMultiline(false);
    /*This is used to tell the grid that we have row height set based on the font size. */
    chargeNumberGrid2Columns.setAwaitedRowHeight(gridRowHeight);
    chargeNumberGrid2Columns.enableSmartRendering(true);

	 chargeNumberGrid2Columns.init();
	/*allow copy and paste feature /
	chargeNumberGrid2Columns.entBox.onselectstart = function(){ return true; };
	/*loading from JSON object*/
	//chargeNumberGrid2Columns.parse(jsonMainData,"json");
	/*This will update the column headers widths according to font size.*/
	//updateColumnWidths(chargeNumberGrid2Columns);

    try
    {
     var chargeNumberTable = document.getElementById("chargeNumberTable");
     chargeNumberTable.style.width = "450px";
    }
    catch(ex)
    {
      //alert("THis means the grid was not initialized");
    }
}

function doChargeNumberInitGrid2Columns(column1,typeStr){
	chargeNumberGrid2Columns = new dhtmlXGridObject('chargeNumber2ColumnsDivId');
	chargeNumberGrid2Columns.setImagePath("../../dhtmlxGrid/codebase/imgs/");

	chargeNumberGrid2Columns.setHeader(
		","+
		column1+","+
		"%"
	);

   chargeNumberGrid2Columns.setColumnIds("permission,chargeNumber2,percent2");

	chargeNumberGrid2Columns.setInitWidths("0,330,100");
   chargeNumberGrid2Columns.setColAlign("left,left,left")
   chargeNumberGrid2Columns.setColTypes(typeStr);
	//set size for all 'hed' here
	inputSize["chargeNumber2"] = 59;
	inputSize["percent2"] = 15;

	chargeNumberGrid2Columns.attachEvent("onRowSelect",chargeNumberSelectRow);
   chargeNumberGrid2Columns.attachEvent("onRightClick",chargeNumberSelectRow);

   chargeNumberGrid2Columns.enableTooltips("false,false,false");
   /*This is to enable edit on click. If a cell is editiable it will show as soon as the row is selected.*/
   chargeNumberGrid2Columns.enableEditEvents(true,false,false);
   chargeNumberGrid2Columns.setSkin("haas");
	chargeNumberGrid2Columns.submitOnlyChanged(false);
	chargeNumberGrid2Columns.setFieldName("{GRID_ID}[{ROW_INDEX}].{COLUMN_ID}");
	chargeNumberGrid2Columns.submitColumns([false,true,true]);
   //chargeNumberGrid2Columns.setColumnHidden(0,true); // permission
    /*Set the type of sort to do on this column.If the gird has rowspans >1 set all columns sotring to be na.
	sorting type str is case sensistive (X,Z come before a,b). haasStr is caseinsensitve sorting.
	For Date column types you need to write custom sorting funciton which will be triggered by onBeforeSorting event.
	For Editable Date column we will not allow sorting, set the sorting to be na.
	For hch you have to write a custom sorting function if needed on the page, other wise set sorting to na*/
	chargeNumberGrid2Columns.setColSorting("na,haasStr,int");

    /*This keeps the row height the same, true will wrap cell content and height of row will change.*/
    chargeNumberGrid2Columns.enableMultiline(false);
    /*This is used to tell the grid that we have row height set based on the font size. */
    chargeNumberGrid2Columns.setAwaitedRowHeight(gridRowHeight);
    chargeNumberGrid2Columns.enableSmartRendering(true);

	 chargeNumberGrid2Columns.init();
	/*allow copy and paste feature /
	chargeNumberGrid2Columns.entBox.onselectstart = function(){ return true; };
	/*loading from JSON object*/
	//chargeNumberGrid2Columns.parse(jsonMainData,"json");
	/*This will update the column headers widths according to font size.*/
	//updateColumnWidths(chargeNumberGrid2Columns);

    try
    {
     var chargeNumberTable = document.getElementById("chargeNumberTable");
     chargeNumberTable.style.width = "450px";
    }
    catch(ex)
    {
      //alert("THis means the grid was not initialized");
    }
}

function doChargeNumberInitGrid3Columns(column1,column2,typeStr){
	chargeNumberGrid3Columns = new dhtmlXGridObject('chargeNumber3ColumnsDivId');
   chargeNumberGrid3Columns.setImagePath("../../dhtmlxGrid/codebase/imgs/");

	chargeNumberGrid3Columns.setHeader(
		","+
		column1+","+
		column2+","+
		"%"
	);

	chargeNumberGrid3Columns.setColumnIds("permission,chargeNumber,chargeNumber2,percent");
	chargeNumberGrid3Columns.setInitWidths("0,165,165,100");
   chargeNumberGrid3Columns.setColAlign("left,left,left,left")
   chargeNumberGrid3Columns.setColTypes(typeStr);
	//set size for all 'hed' here
	inputSize["chargeNumber"] = 27;
	inputSize["chargeNumber2"] = 27;
	inputSize["percent"] = 12;

	chargeNumberGrid3Columns.attachEvent("onRowSelect",chargeNumberSelectRow);
   chargeNumberGrid3Columns.attachEvent("onRightClick",chargeNumberSelectRow);

   chargeNumberGrid3Columns.enableTooltips("false,false,false,false");
   /*This is to enable edit on click. If a cell is editiable it will show as soon as the row is selected.*/
   chargeNumberGrid3Columns.enableEditEvents(true,false,false,false);
   chargeNumberGrid3Columns.setSkin("haas");
	chargeNumberGrid3Columns.submitOnlyChanged(false);
	chargeNumberGrid3Columns.setFieldName("{GRID_ID}[{ROW_INDEX}].{COLUMN_ID}");
	chargeNumberGrid3Columns.submitColumns([false,true,true,true]);
    chargeNumberGrid3Columns.setColumnHidden(0,true); // permission
    /*Set the type of sort to do on this column.If the gird has rowspans >1 set all columns sotring to be na.
	sorting type str is case sensistive (X,Z come before a,b). haasStr is caseinsensitve sorting.
	For Date column types you need to write custom sorting funciton which will be triggered by onBeforeSorting event.
	For Editable Date column we will not allow sorting, set the sorting to be na.
	For hch you have to write a custom sorting function if needed on the page, other wise set sorting to na*/
	chargeNumberGrid3Columns.setColSorting("na,haasStr,haasStr,int");

    /*This keeps the row height the same, true will wrap cell content and height of row will change.*/
    chargeNumberGrid3Columns.enableMultiline(false);
    /*This is used to tell the grid that we have row height set based on the font size. */
    chargeNumberGrid3Columns.setAwaitedRowHeight(gridRowHeight);
    chargeNumberGrid3Columns.enableSmartRendering(true);

    chargeNumberGrid3Columns.init();
	/*allow copy and paste feature */
	chargeNumberGrid3Columns.entBox.onselectstart = function(){ return true; };
	/*loading from JSON object*/
	//chargeNumberGrid3Columns.parse(jsonMainData,"json");
	/*This will update the column headers widths according to font size.*/
	//updateColumnWidths(chargeNumberGrid3Columns);
    try
    {
     var chargeNumberTable = document.getElementById("chargeNumberTable");
     chargeNumberTable.style.width = "450px";
    }
    catch(ex)
    {
      //alert("THis means the grid was not initialized");
    }
}

function doChargeNumberInitGrid4Columns(column1,column2,column3,typeStr){
	chargeNumberGrid4Columns = new dhtmlXGridObject('chargeNumber4ColumnsDivId');
   chargeNumberGrid4Columns.setImagePath("../../dhtmlxGrid/codebase/imgs/");

	chargeNumberGrid4Columns.setHeader(
		","+
		column1+","+
		column2+","+
		column3+","+
		"%"
	);

	chargeNumberGrid4Columns.setColumnIds("permission,chargeNumber,chargeNumber2,chargeNumber3,percent");
	chargeNumberGrid4Columns.setInitWidths("0,120,120,120,70");
   chargeNumberGrid4Columns.setColAlign("left,left,left,left,left")
   chargeNumberGrid4Columns.setColTypes(typeStr);
	//set size for all 'hed' here
	inputSize["chargeNumber"] = 16;
	inputSize["chargeNumber2"] = 16;
	inputSize["chargeNumber3"] = 16;
	inputSize["percent"] = 6;

	chargeNumberGrid4Columns.attachEvent("onRowSelect",chargeNumberSelectRow);
   chargeNumberGrid4Columns.attachEvent("onRightClick",chargeNumberSelectRow);

   chargeNumberGrid4Columns.enableTooltips("false,false,false,false,false");
   /*This is to enable edit on click. If a cell is editiable it will show as soon as the row is selected.*/
   chargeNumberGrid4Columns.enableEditEvents(true,false,false,false,false);
   chargeNumberGrid4Columns.setSkin("haas");
	chargeNumberGrid4Columns.submitOnlyChanged(false);
	chargeNumberGrid4Columns.setFieldName("{GRID_ID}[{ROW_INDEX}].{COLUMN_ID}");
	chargeNumberGrid4Columns.submitColumns([false,true,true,true,true]);
    chargeNumberGrid4Columns.setColumnHidden(0,true); // permission
    /*Set the type of sort to do on this column.If the gird has rowspans >1 set all columns sotring to be na.
	sorting type str is case sensistive (X,Z come before a,b). haasStr is caseinsensitve sorting.
	For Date column types you need to write custom sorting funciton which will be triggered by onBeforeSorting event.
	For Editable Date column we will not allow sorting, set the sorting to be na.
	For hch you have to write a custom sorting function if needed on the page, other wise set sorting to na*/
	chargeNumberGrid4Columns.setColSorting("na,haasStr,haasStr,haasStr,int");

    /*This keeps the row height the same, true will wrap cell content and height of row will change.*/
    chargeNumberGrid4Columns.enableMultiline(false);
    /*This is used to tell the grid that we have row height set based on the font size. */
    chargeNumberGrid4Columns.setAwaitedRowHeight(gridRowHeight);
    chargeNumberGrid4Columns.enableSmartRendering(true);

    chargeNumberGrid4Columns.init();
	/*allow copy and paste feature */
	chargeNumberGrid4Columns.entBox.onselectstart = function(){ return true; };
    try
    {
     var chargeNumberTable = document.getElementById("chargeNumberTable");
     chargeNumberTable.style.width = "450px";
    }
    catch(ex)
    {
      //alert("THis means the grid was not initialized");
    }
}

function doChargeNumberInitGrid5Columns(column1,column2,column3,column4,typeStr){
	chargeNumberGrid5Columns = new dhtmlXGridObject('chargeNumber5ColumnsDivId');
   chargeNumberGrid5Columns.setImagePath("../../dhtmlxGrid/codebase/imgs/");

	chargeNumberGrid5Columns.setHeader(
		","+
		column1+","+
		column2+","+
		column3+","+
		column4+","+
		"%"
	);

	chargeNumberGrid5Columns.setColumnIds("permission,chargeNumber,chargeNumber2,chargeNumber3,chargeNumber4,percent");
	chargeNumberGrid5Columns.setInitWidths("0,92,92,92,92,60");
   chargeNumberGrid5Columns.setColAlign("left,left,left,left,left,left")
   chargeNumberGrid5Columns.setColTypes(typeStr);
	//set size for all 'hed' here
	inputSize["chargeNumber"] = 11;
	inputSize["chargeNumber2"] = 11;
	inputSize["chargeNumber3"] = 11;
	inputSize["chargeNumber4"] = 11;
	inputSize["percent"] = 6;

	chargeNumberGrid5Columns.attachEvent("onRowSelect",chargeNumberSelectRow);
   chargeNumberGrid5Columns.attachEvent("onRightClick",chargeNumberSelectRow);

   chargeNumberGrid5Columns.enableTooltips("false,false,false,false,false,false");
   /*This is to enable edit on click. If a cell is editiable it will show as soon as the row is selected.*/
   chargeNumberGrid5Columns.enableEditEvents(true,false,false,false,false,false);
   chargeNumberGrid5Columns.setSkin("haas");
	chargeNumberGrid5Columns.submitOnlyChanged(false);
	chargeNumberGrid5Columns.setFieldName("{GRID_ID}[{ROW_INDEX}].{COLUMN_ID}");
	chargeNumberGrid5Columns.submitColumns([false,true,true,true,true,true]);
    chargeNumberGrid5Columns.setColumnHidden(0,true); // permission
    /*Set the type of sort to do on this column.If the gird has rowspans >1 set all columns sotring to be na.
	sorting type str is case sensistive (X,Z come before a,b). haasStr is caseinsensitve sorting.
	For Date column types you need to write custom sorting funciton which will be triggered by onBeforeSorting event.
	For Editable Date column we will not allow sorting, set the sorting to be na.
	For hch you have to write a custom sorting function if needed on the page, other wise set sorting to na*/
	chargeNumberGrid5Columns.setColSorting("na,haasStr,haasStr,haasStr,haasStr,int");

    /*This keeps the row height the same, true will wrap cell content and height of row will change.*/
    chargeNumberGrid5Columns.enableMultiline(false);
    /*This is used to tell the grid that we have row height set based on the font size. */
    chargeNumberGrid5Columns.setAwaitedRowHeight(gridRowHeight);
    chargeNumberGrid5Columns.enableSmartRendering(true);

    chargeNumberGrid5Columns.init();
	/*allow copy and paste feature */
	chargeNumberGrid5Columns.entBox.onselectstart = function(){ return true; };
    try
    {
     var chargeNumberTable = document.getElementById("chargeNumberTable");
     chargeNumberTable.style.width = "450px";
    }
    catch(ex)
    {
      //alert("THis means the grid was not initialized");
    }
}

function chargeNumberSelectRow() {
   rightClick = false;
   rowId0 = arguments[0];
   colId0 = arguments[1];
   ee     = arguments[2];

   if( ee != null ) {
   		if( ee.button != null && ee.button == 2 ) rightClick = true;
   		else if( ee.which == 3  ) rightClick = true;
   }
   chargeNumberSelectedRowId = rowId0;
}

function doOnBeforeSelect(rowId,oldRowId) {
	setRowClass(oldRowId, saveRowClass);

	saveRowClass = getRowClass(rowId);
	if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/) == -1)
		overrideDefaultSelect(rowId,saveRowClass);
	return true;
}

var prevRow;
function selectRow()
{
	rightClick = false;
	rowId0 = arguments[0];
	colId0 = arguments[1];
	ee     = arguments[2];
   
	if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/) == -1)
		setRowClass(rowId0,''+saveRowClass+'Selected');

	if( ee != null ) {
   		if( ee.button != null && ee.button == 2 ) rightClick = true;
   		else if( ee.which == 3  ) rightClick = true;
	}
	
	//first save current data before changing row
	if(!initial)
		saveCurrenData();
	prevRow = selectedRowId;
	selectedRowId = rowId0;
	displayLineDetail();

	//check to see if user has permission to request cancellation
	if ("Y" == $("canRequestCancellation").value || "Y" == cellValue(selectedRowId,"orderMaintenancePerm")) {
		if ("0" == cellValue(selectedRowId,"totalQuantityIssued") && "rqcancel" != cellValue(selectedRowId,"cancelStatus") 
				&& "Canceled" != cellValue(selectedRowId,"cancelStatus")) {
			$("requestCancelSpan").innerHTML = '&nbsp;| <a href="javascript:requestCancel()"> ' + messagesData.requestcancel +'</a>';
			$("requestCancelSpan").style["display"] = "";
		}else {
			$("requestCancelSpan").innerHTML = '';
			$("requestCancelSpan").style["display"] = "none";
		}
	}else {
		$("requestCancelSpan").innerHTML = '';
		$("requestCancelSpan").style["display"] = "none";
	}
	
	$("approveCancelSpan").innerHTML = '&nbsp;| <a href="javascript:approveCancel()"> ' + messagesData.approvecancel +'</a>';
	$("rejectCancelSpan").innerHTML = '&nbsp;| <a href="javascript:rejectCancel()"> ' + messagesData.rejectcancel +'</a>';
	$("approveQtyChangeSpan").innerHTML = '&nbsp;| <a href="javascript:approveQtyChange()"> ' + messagesData.approveqtychange +'</a>';
	//check to see if user has permission to approve/reject cancellation
 	if("rqcancel" == cellValue(selectedRowId,"cancelStatus") && "Y" == cellValue(selectedRowId,"orderMaintenancePerm")){
		$("approveCancelSpan").style["display"] = "";
		$("rejectCancelSpan").style["display"] = "";
	} else if ("Canceled" == cellValue(selectedRowId,"cancelStatus") && "Y" == cellValue(selectedRowId,"orderMaintenancePerm")){
		$("approveCancelSpan").style["display"] = "none";
		$("rejectCancelSpan").style["display"] = "";
	} else if ("0" == cellValue(selectedRowId,"totalQuantityIssued") && "Y" == cellValue(selectedRowId,"orderMaintenancePerm")){ 
		$("approveCancelSpan").style["display"] = "";
		$("rejectCancelSpan").style["display"] = "none";
	} else {
		$("approveCancelSpan").style["display"] = "none";
		$("rejectCancelSpan").style["display"] = "none";
	}
 	
	if ("Y" == cellValue(selectedRowId,"orderMaintenancePerm") && 
			typeof($("qty"+selectedRowId+"")) != 'undefined' && $("qty"+selectedRowId+"") != null && true != $("qty"+selectedRowId+"").readOnly && true != $("qty"+selectedRowId+"").disabled) {
		document.getElementById("approveQtyChangeSpan").style["display"] = "";
	}
	else {
  		document.getElementById("approveQtyChangeSpan").style["display"] = "none";
	}
	
	if(!initial) {
		var columnId = mygrid.getColumnId(colId0);
		switch (columnId) {
			case "useApprovalStatus":
				 var currentStatus = cellValue(selectedRowId,"useApprovalStatus");
				 if (currentStatus == "approved") {
				 	showApprovedBy($("prNumber").value,cellValue(selectedRowId,"lineItem"));
				 }else if (currentStatus == "pending") {
				   showUseApprovers($("prNumber").value,cellValue(selectedRowId,"lineItem"));
				 }
				 break;
			/*case "listApprovalStatus":
		      var currentStatus = cellValue(selectedRowId,"listApprovalStatus");
				 if (currentStatus == "Approved" || currentStatus == "Pending") {
				  	showApproverListDetail($("prNumber").value,cellValue(selectedRowId,"lineItem"));
				 }
				 
				break;*/
		}; //end of switch
	    //determine whether to show copy
		
		displayCopyButton();
	}
	
	initial = false;
}  //end of method

function displayCopyButton() {
	if(chargeNumberGrid2Columns != null || chargeNumberGrid3Columns != null ||
       chargeNumberGrid4Columns!= null || chargeNumberGrid5Columns != null) {
        if(mygrid.getRowsNum() > 1 && $("viewType").value == "REQUEST" )
            $("copySpan").style["display"] = "";
        else
            $("copySpan").style["display"] = "none";    
    }
}

function setLineItemPrice(tmpRowId) {
	if($v('hideCatalogPrice') == 'true')
	{
		$("lineItemPrice").style['display'] = "none";
	}
	var quantity = cellValue(tmpRowId,"qty");
	var tmpUnitPrice = 0.00;
	var tmpTotalPrice = 0.00;
	if (getPoRequired(tmpRowId) == "p") {
		var unitPriceRequired = getUnitPriceRequired(tmpRowId);
		if (unitPriceRequired == "Required" || unitPriceRequired == "Display") {
			tmpUnitPrice = cellValue(tmpRowId,"unitOfSaleQuantityPerEach")*cellValue(tmpRowId,"unitOfSalePrice");
			tmpTotalPrice = quantity*tmpUnitPrice;
		}else {
			tmpUnitPrice = cellValue(tmpRowId,"unitPrice")*1;
			tmpTotalPrice = quantity*tmpUnitPrice;
		}
	}else {
		tmpUnitPrice = cellValue(tmpRowId,"unitPrice")*1;
		tmpTotalPrice = quantity*tmpUnitPrice;
	}
	if (tmpUnitPrice != 0.00 && tmpTotalPrice != 0.00) {
		//$("lineItemPrice").innerHTML = quantity+" @ "+tmpUnitPrice.toFixed(2)+"/"+cellValue(tmpRowId,"sizeUnit")+" = "+tmpTotalPrice.toFixed(2)+" "+cellValue(tmpRowId,"currencyId");
		$("lineItemPrice").innerHTML = quantity+" @ "+tmpUnitPrice.toFixed(2)+" = "+tmpTotalPrice.toFixed(2)+" "+cellValue(tmpRowId,"currencyId");

	}else {
		$("lineItemPrice").innerHTML = "";
	}
}

/*
     The extended price is equal to the sum of unit_price of non-po line items and
     the sum of po_unit_price of po line items.  If non-po currency (price_group currency)
     is different than po currency then display "----".  If po line items contain different
     currency then display "----"
*/
function setExtendedPrice() {
	if($v('hideCatalogPrice') == 'true')
	{
		$("extPriceLabel").style['display'] = "none";
		$("extPrice").style['display'] = "none";
	}
	if ("Y" == $("containsFillUpBulk").value) {
		$("extPrice").innerHTML = " tbd ";
		
	}else {
		var nonPoExtenedPrice = 0.00;
		var poExtendedPrice = 0.00;
		var hasPrice = false;
		for (var i = 1; i <= mygrid.getRowsNum(); i++) {
			if (getPoRequired(i) == "p") {
				var unitPriceRequired = getUnitPriceRequired(i);
				if (unitPriceRequired == "Required" || unitPriceRequired == "Display") {
					var tmpPrice = getPoPrice(i);
					if (tmpPrice != -12345.6) {
						poExtendedPrice += tmpPrice;
						hasPrice = true;
					}
				}else {
					var tmpPrice = getNonPoPrice(i);
					if (tmpPrice != -12345.6) {
						nonPoExtenedPrice += tmpPrice;
						hasPrice = true;
					}
				}
			}else {
				var tmpPrice = getNonPoPrice(i);
				if (tmpPrice != -12345.6) {
					nonPoExtenedPrice += tmpPrice;
					hasPrice = true;
				}
			}
		} //end of for each line
		if (hasPrice) {

			$("extPrice").innerHTML = (poExtendedPrice+nonPoExtenedPrice).toFixed(2)+" "+$("currencyId").value;
			if ($("prStatus").value == 'entered') {
				$("mrTotalCostApprovalDetailSpan").style["display"] = "none";
				$("mrTotalCostApprovalDetailSpan").innerHTML = "";

			}else {
				if(approverRequired && !homeCompanyLogin)
					{
						$("mrTotalCostApprovalDetailSpan").innerHTML = '&nbsp;|&nbsp;<a href="javascript:extendedPriceClicked()">'+messagesData.mrtotalcostapprovaldetail+'</a>';
						$("mrTotalCostApprovalDetailSpan").style["display"] = "";
					}
			}
		}else {
			$("extPrice").innerHTML = "";
			$("mrTotalCostApprovalDetailSpan").style["display"] = "none";
			$("mrTotalCostApprovalDetailSpan").innerHTML = "";
		}
	}
}

function extendedPriceClicked() {
	if ($("prStatus").value == 'compchk') {
		showFinancialApprovers($("prNumber").value);
	}else {
		showFinanceApprover($("prNumber").value);
	}
}

function getPoPrice(currentRowId) {
	var result = -12345.6;
	var unitOfSalePrice = cellValue(currentRowId,"unitOfSalePrice");
	var uosqpe = cellValue(currentRowId,"unitOfSaleQuantityPerEach");
	if (unitOfSalePrice != null && uosqpe != null) {
		result = (cellValue(currentRowId,"qty")*uosqpe)*(unitOfSalePrice*getConversionFactor(cellValue(currentRowId,"currencyId")));
	}
	return result;
}

function getUnitPriceRequired(currentRowId) {
	var result = "N/A";
	for(var i = 0; i < prRulesColl.length; i++) {
		if (cellValue(currentRowId,"chargeType") == prRulesColl[i].chargeType) {
			result = prRulesColl[i].unitPriceRequired;
			break;
		}
	}
	return result;
}

function getPoRequired(currentRowId) {
	var result = "n";
	for(var i = 0; i < prRulesColl.length; i++) {
		if (cellValue(currentRowId,"chargeType") == prRulesColl[i].chargeType) {
			result = prRulesColl[i].poRequired;
			break;
		}
	}
	return result;
}

function getPoSeqRequired(currentRowId) {
	var result = "n";
	for(var i = 0; i < prRulesColl.length; i++) {
		if (cellValue(currentRowId,"chargeType") == prRulesColl[i].chargeType) {
			result = prRulesColl[i].poSeqRequired;
			break;
		}
	}
	return result;
}

function getChargeNumberOfColumn(prRulesIndexForLine) {
	var result = "0";
	var chargeLabel1 = prRulesColl[prRulesIndexForLine].chargeLabel1;
	var chargeLabel2 = prRulesColl[prRulesIndexForLine].chargeLabel2;
	var chargeLabel3 = prRulesColl[prRulesIndexForLine].chargeLabel3;
	var chargeLabel4 = prRulesColl[prRulesIndexForLine].chargeLabel4;
	if ((chargeLabel1 != null && chargeLabel1.length > 0) && (chargeLabel2 != null && chargeLabel2.length > 0) &&
		 (chargeLabel3 != null && chargeLabel3.length > 0) && (chargeLabel4 != null && chargeLabel4.length > 0)) {
		result = "5";
	}else if ((chargeLabel1 != null && chargeLabel1.length > 0) && (chargeLabel2 != null && chargeLabel2.length > 0) &&
		 (chargeLabel3 != null && chargeLabel3.length > 0)) {
		result = "4";
	}else if ((chargeLabel1 != null && chargeLabel1.length > 0) && (chargeLabel2 != null && chargeLabel2.length > 0)) {
		result = "3";
	}else if (chargeLabel1 != null && chargeLabel1.length > 0) {
		result = "2";
	}
	return result;
}

function getPrAccountRequired(currentRowId) {
	var result = "n";
	for(var i = 0; i < prRulesColl.length; i++) {
		if (cellValue(currentRowId,"chargeType") == prRulesColl[i].chargeType) {
			result = prRulesColl[i].prAccountRequired;
			break;
		}
	}
	return result;
}

function getPrRulesIndexForLine(currentRowId) {
	var result = 0;
	for(var i = 0; i < prRulesColl.length; i++) {
		if (cellValue(currentRowId,"chargeType") == prRulesColl[i].chargeType) {
			result = i;
			break;
		}
	}
	return result;
}

function getNonPoPrice(currentRowId) {
	var result = -12345.6;
	var unitPrice = cellValue(currentRowId,"unitPrice");
	if (unitPrice != null) {
		result = cellValue(currentRowId,"qty")*(unitPrice*getConversionFactor(cellValue(currentRowId,"currencyId")));
	}
	return result;
}

function getConversionFactor(currencyId) {
	var result = 1;
	for (var i = 0; i < currencyColl.length; i++) {
		if (currencyId == currencyColl[i].currencyId) {
			result = currencyColl[i].exchangeRate;
			break;
		}
	}
	return result;
}

function setChargeType() {
	var currentChargeType = null;
	if (cellValue(selectedRowId,"chargeType") == "d") {
		$("chargeTypeD").checked = "checked";
		$("chargeTypeI").checked = "";
		currentChargeType = "d";
	}else {
		$("chargeTypeD").checked = "";
		$("chargeTypeI").checked = "checked";
		currentChargeType = "i";
	}

	//don't show charge type if there is only one
	if (prRulesColl.length == 2) {
		$("chargeTypeSpan").style["display"] = "";
	}else {
		$("chargeTypeSpan").style["display"] = "none";    
	}

	//determine what charge number or po to show
    //this whether user have permission to edit charge data for facility or user is
    //a charge approver and this MR-line is pending charge approval
    var canEditChargeData = cellValue(selectedRowId,"canEditLineChargeData");
    var key = cellValue(selectedRowId,"lineItem");
	var poRequired = "n";
	var poSeqRequired = "n";
	var unitPriceRequired = "N/A";
	var prAccountRequired = "n";
	var chargeDisplay1 = "n";
	var chargeDisplay2 = "n";
	var chargeDisplay3 = "n";
	var chargeDisplay4 = "n";
	var chargeLabel1 = "";
	var chargeLabel2 = "";
	var chargeLabel3 = "";
	var chargeLabel4 = "";
	var editDirectedCharge1 = "";
	var editDirectedCharge2 = "";
	var editDirectedCharge3 = "";
	var editDirectedCharge4 = "";
    var directedChargeTypeEditable = "";
    var maxChargeNumberToDisplay = "";
    for(var i = 0; i < prRulesColl.length; i++) {
		if (currentChargeType == prRulesColl[i].chargeType) {
			poRequired = prRulesColl[i].poRequired;
			poSeqRequired = prRulesColl[i].poSeqRequired;
			unitPriceRequired = prRulesColl[i].unitPriceRequired;
			prAccountRequired = prRulesColl[i].prAccountRequired;
			chargeDisplay1 = prRulesColl[i].chargeDisplay1;
			chargeDisplay2 = prRulesColl[i].chargeDisplay2;
			chargeDisplay3 = prRulesColl[i].chargeDisplay3;
			chargeDisplay4 = prRulesColl[i].chargeDisplay4;
			chargeLabel1 = prRulesColl[i].chargeLabel1;
			chargeLabel2 = prRulesColl[i].chargeLabel2;
			chargeLabel3 = prRulesColl[i].chargeLabel3;
			chargeLabel4 = prRulesColl[i].chargeLabel4;
			editDirectedCharge1 = prRulesColl[i].chargeAllowEdit1;
			editDirectedCharge2 = prRulesColl[i].chargeAllowEdit2;
			editDirectedCharge3 = prRulesColl[i].chargeAllowEdit3;
			editDirectedCharge4 = prRulesColl[i].chargeAllowEdit4;
            directedChargeTypeEditable = prRulesColl[i].directedChargeTypeEditable;
            maxChargeNumberToDisplay = prRulesColl[i].maxChargeNumberToDisplay;
            break;
		}
	}
    //charge number
	if (prAccountRequired == "y") {
		if ((chargeLabel1 != null && chargeLabel1.length > 0) && (chargeLabel2 != null && chargeLabel2.length > 0) &&
			 (chargeLabel3 != null && chargeLabel3.length > 0) && (chargeLabel4 != null && chargeLabel4.length > 0)) {
			var typeStr = "ro,ro,ro,ro,ro,ro";
			if(currentChargeType == "d") {
				var directedCharge = cellValue(selectedRowId,"directedChargeForDirect");
				if (directedCharge == "Y") {
                    if (directedChargeTypeEditable == "Y" && canEditChargeData == "Y" ) {
                        $("chargeTypeD").disabled = false;
					    $("chargeTypeI").disabled = false;
                    }else {
                        $("chargeTypeD").disabled = true;
					    $("chargeTypeI").disabled = true;
                    }
					typeStr = "ro,ro,ro,ro,ro,ro";
					//intialize grid
					chargeNumberGrid5Columns == null;
					doChargeNumberInitGrid5Columns(chargeLabel1,chargeLabel2,chargeLabel3,chargeLabel4,typeStr);
					displayChargeNumber5Columns(chargeNumberForDirectColl[key],"N",prAccountColl[key],currentChargeType);
				}else {
                    if(canEditChargeData == "Y") {
                        $("chargeTypeD").disabled = false;
					    $("chargeTypeI").disabled = false;
                    }else {
                        $("chargeTypeD").disabled = true;
					    $("chargeTypeI").disabled = true;
                    }
                    if (cellValue(selectedRowId,"chargeNumbersFromDirectedChargeD") == 'Y') {
						typeStr = "ro";
						if (editDirectedCharge1 == "Y") {
							typeStr += ",hed";
						}else {
							typeStr += ",ro";
						}
						if (editDirectedCharge2 == "Y") {
							typeStr += ",hed";
						}else {
							typeStr += ",ro";
						}
						if (editDirectedCharge3 == "Y") {
							typeStr += ",hed";
						}else {
							typeStr += ",ro";
						}
						if (editDirectedCharge4 == "Y") {
							typeStr += ",hed";
						}else {
							typeStr += ",ro";
						}
						typeStr += ",hed";
					}else {
                        if(canEditChargeData == "Y") {
                            typeStr = "ro,hed,hed,hed,hed,hed";
                        }else {
                            typeStr = "ro,ro,ro,ro,ro,ro";
                        }
                    }

					//intialize grid
					chargeNumberGrid5Columns == null;
					doChargeNumberInitGrid5Columns(chargeLabel1,chargeLabel2,chargeLabel3,chargeLabel4,typeStr);
					displayChargeNumberEmpty5Columns(canEditChargeData,prAccountColl[key],currentChargeType,maxChargeNumberToDisplay);
				}
			}else {
				var directedCharge = cellValue(selectedRowId,"directedChargeForIndirect");
				if (directedCharge == "Y") {
					if (directedChargeTypeEditable == "Y" && canEditChargeData == "Y" ) {
                        $("chargeTypeD").disabled = false;
					    $("chargeTypeI").disabled = false;
                    }else {
                        $("chargeTypeD").disabled = true;
					    $("chargeTypeI").disabled = true;
                    }
					typeStr = "ro,ro,ro,ro,ro,ro";
					//intialize grid
					chargeNumberGrid5Columns == null;
					doChargeNumberInitGrid5Columns(chargeLabel1,chargeLabel2,chargeLabel3,chargeLabel4,typeStr);
					displayChargeNumber5Columns(chargeNumberForIndirectColl[key],"N",prAccountColl[key],currentChargeType);
				}else {
                    if(canEditChargeData == "Y") {
                        $("chargeTypeD").disabled = false;
					    $("chargeTypeI").disabled = false;
                    }else {
                        $("chargeTypeD").disabled = true;
					    $("chargeTypeI").disabled = true;
                    }
                    if (cellValue(selectedRowId,"chargeNumbersFromDirectedChargeI") == 'Y') {
						typeStr = "ro";
						if (editDirectedCharge1 == "Y") {
							typeStr += ",hed";
						}else {
							typeStr += ",ro";
						}
						if (editDirectedCharge2 == "Y") {
							typeStr += ",hed";
						}else {
							typeStr += ",ro";
						}
						if (editDirectedCharge3 == "Y") {
							typeStr += ",hed";
						}else {
							typeStr += ",ro";
						}
						if (editDirectedCharge4 == "Y") {
							typeStr += ",hed";
						}else {
							typeStr += ",ro";
						}
						typeStr += ",hed";
					}else {
                        if(canEditChargeData == "Y") {
                            typeStr = "ro,hed,hed,hed,hed,hed";
                        }else {
                            typeStr = "ro,ro,ro,ro,ro,ro";
                        }
                    }
					//intialize grid
					chargeNumberGrid5Columns == null;
					doChargeNumberInitGrid5Columns(chargeLabel1,chargeLabel2,chargeLabel3,chargeLabel4,typeStr);
					displayChargeNumberEmpty5Columns(canEditChargeData,prAccountColl[key],currentChargeType,maxChargeNumberToDisplay);
				}
			}
			$("chargeNumber2ColumnsDivId").style["display"] = "none";
			$("chargeNumber3ColumnsDivId").style["display"] = "none";
			$("chargeNumber4ColumnsDivId").style["display"] = "none";
			$("chargeNumber5ColumnsDivId").style["display"] = "";
		}else if ((chargeLabel1 != null && chargeLabel1.length > 0) && (chargeLabel2 != null && chargeLabel2.length > 0) &&
			       (chargeLabel3 != null && chargeLabel3.length > 0)) {
			var typeStr = "ro,ro,ro,ro,ro";
			if(currentChargeType == "d") {
                var directedCharge = cellValue(selectedRowId,"directedChargeForDirect");
				if (directedCharge == "Y") {
                    if (directedChargeTypeEditable == "Y" && canEditChargeData == "Y" ) {
                        $("chargeTypeD").disabled = false;
					    $("chargeTypeI").disabled = false;
                    }else {
                        $("chargeTypeD").disabled = true;
					    $("chargeTypeI").disabled = true;
                    }
					typeStr = "ro,ro,ro,ro,ro";
					//intialize grid
					chargeNumberGrid4Columns == null;
					doChargeNumberInitGrid4Columns(chargeLabel1,chargeLabel2,chargeLabel3,typeStr);
					displayChargeNumber4Columns(chargeNumberForDirectColl[key],"N",prAccountColl[key],currentChargeType);
				}else {
                    if(canEditChargeData == "Y") {
                        $("chargeTypeD").disabled = false;
					    $("chargeTypeI").disabled = false;
                    }else {
                        $("chargeTypeD").disabled = true;
					    $("chargeTypeI").disabled = true;    
                    }
                    if (cellValue(selectedRowId,"chargeNumbersFromDirectedChargeD") == 'Y') {
						typeStr = "ro";
						if (editDirectedCharge1 == "Y") {
							typeStr += ",hed";
						}else {
							typeStr += ",ro";
						}
						if (editDirectedCharge2 == "Y") {
							typeStr += ",hed";
						}else {
							typeStr += ",ro";
						}
						if (editDirectedCharge3 == "Y") {
							typeStr += ",hed";
						}else {
							typeStr += ",ro";
						}
						typeStr += ",hed";
					}else {
                        if(canEditChargeData == "Y") {
                            typeStr = "ro,hed,hed,hed,hed";
                        }else {
                            typeStr = "ro,ro,ro,ro,ro";
                        }
                    }
					//intialize grid
					chargeNumberGrid4Columns == null;
					doChargeNumberInitGrid4Columns(chargeLabel1,chargeLabel2,chargeLabel3,typeStr);
					displayChargeNumberEmpty4Columns(canEditChargeData,prAccountColl[key],currentChargeType,maxChargeNumberToDisplay);
				}
			}else {
				var directedCharge = cellValue(selectedRowId,"directedChargeForIndirect");
				if (directedCharge == "Y") {
                    if (directedChargeTypeEditable == "Y" && canEditChargeData == "Y" ) {
                        $("chargeTypeD").disabled = false;
					    $("chargeTypeI").disabled = false;
                    }else {
                        $("chargeTypeD").disabled = true;
					    $("chargeTypeI").disabled = true;
                    }
                    typeStr = "ro,ro,ro,ro,ro";
					//intialize grid
					chargeNumberGrid4Columns == null;
					doChargeNumberInitGrid4Columns(chargeLabel1,chargeLabel2,chargeLabel3,typeStr);
					displayChargeNumber4Columns(chargeNumberForIndirectColl[key],"N",prAccountColl[key],currentChargeType);
				}else {
                    if(canEditChargeData == "Y") {
                        $("chargeTypeD").disabled = false;
					    $("chargeTypeI").disabled = false;
                    }else {
                        $("chargeTypeD").disabled = true;
					    $("chargeTypeI").disabled = true;
                    }
                    if (cellValue(selectedRowId,"chargeNumbersFromDirectedChargeI") == 'Y') {
						typeStr = "ro";
						if (editDirectedCharge1 == "Y") {
							typeStr += ",hed";
						}else {
							typeStr += ",ro";
						}
						if (editDirectedCharge2 == "Y") {
							typeStr += ",hed";
						}else {
							typeStr += ",ro";
						}
						if (editDirectedCharge3 == "Y") {
							typeStr += ",hed";
						}else {
							typeStr += ",ro";
						}
						typeStr += ",hed";
					}else {
                        if(canEditChargeData == "Y") {
                            typeStr = "ro,hed,hed,hed,hed";
                        }else {
                            typeStr = "ro,ro,ro,ro,ro";
                        }
                    }
					//intialize grid
					chargeNumberGrid4Columns == null;
					doChargeNumberInitGrid4Columns(chargeLabel1,chargeLabel2,chargeLabel3,typeStr);
					displayChargeNumberEmpty4Columns(canEditChargeData,prAccountColl[key],currentChargeType,maxChargeNumberToDisplay);
				}
			}
			$("chargeNumber2ColumnsDivId").style["display"] = "none";
			$("chargeNumber3ColumnsDivId").style["display"] = "none";
			$("chargeNumber4ColumnsDivId").style["display"] = "";
			$("chargeNumber5ColumnsDivId").style["display"] = "none";
		}else if ((chargeLabel1 != null && chargeLabel1.length > 0) && (chargeLabel2 != null && chargeLabel2.length > 0)) {
			var typeStr = "ro,ro,ro,ro";
			if(currentChargeType == "d") {
				var directedCharge = cellValue(selectedRowId,"directedChargeForDirect");
				if (directedCharge == "Y") {
					if (directedChargeTypeEditable == "Y" && canEditChargeData == "Y" ) {
                        $("chargeTypeD").disabled = false;
					    $("chargeTypeI").disabled = false;
                    }else {
                        $("chargeTypeD").disabled = true;
					    $("chargeTypeI").disabled = true;
                    }
					typeStr = "ro,ro,ro,ro";
					//intialize grid
					chargeNumberGrid3Columns == null;
					doChargeNumberInitGrid3Columns(chargeLabel1,chargeLabel2,typeStr);
					displayChargeNumber3Columns(chargeNumberForDirectColl[key],"N",prAccountColl[key],currentChargeType);
				}else {
                    if(canEditChargeData == "Y") {
                        $("chargeTypeD").disabled = false;
					    $("chargeTypeI").disabled = false;
                    }else {
                        $("chargeTypeD").disabled = true;
					    $("chargeTypeI").disabled = true;
                    }
                    if (chargeDisplay1 == "y" && chargeDisplay2 == "y") {
						typeStr = "ro,ro,ro,hed";
						//intialize grid
						chargeNumberGrid3Columns == null;
						doChargeNumberInitGrid3Columns(chargeLabel1,chargeLabel2,typeStr);
						displayChargeNumber3Columns(chargeNumberForDirectColl[key],canEditChargeData,prAccountColl[key],currentChargeType);
					}else {
						if (cellValue(selectedRowId,"chargeNumbersFromDirectedChargeD") == 'Y') {
							typeStr = "ro";
							if (editDirectedCharge1 == "Y") {
								typeStr += ",hed";
							}else {
								typeStr += ",ro";
							}
							if (editDirectedCharge2 == "Y") {
								typeStr += ",hed";
							}else {
								typeStr += ",ro";
							}
							typeStr += ",hed";
						}else {
                            if(canEditChargeData == "Y") {
                                typeStr = "ro,hed,hed,hed";
                            }else {
                                typeStr = "ro,ro,ro,ro";
                            }
                        }
						//intialize grid
						chargeNumberGrid3Columns == null;
						doChargeNumberInitGrid3Columns(chargeLabel1,chargeLabel2,typeStr);
						displayChargeNumberEmpty3Columns(canEditChargeData,prAccountColl[key],currentChargeType,maxChargeNumberToDisplay);
					}
				}
			}else {
				var directedCharge = cellValue(selectedRowId,"directedChargeForIndirect");
				if (directedCharge == "Y") {
                    if (directedChargeTypeEditable == "Y" && canEditChargeData == "Y" ) {
                        $("chargeTypeD").disabled = false;
					    $("chargeTypeI").disabled = false;
                    }else {
                        $("chargeTypeD").disabled = true;
					    $("chargeTypeI").disabled = true;
                    }
					typeStr = "ro,ro,ro,ro";
					//intialize grid
					chargeNumberGrid3Columns == null;
					doChargeNumberInitGrid3Columns(chargeLabel1,chargeLabel2,typeStr);
					displayChargeNumber3Columns(chargeNumberForIndirectColl[key],"N",prAccountColl[key],currentChargeType);
				}else {
                    if(canEditChargeData == "Y") {
                        $("chargeTypeD").disabled = false;
					    $("chargeTypeI").disabled = false;
                    }else {
                        $("chargeTypeD").disabled = true;
					    $("chargeTypeI").disabled = true;
                    }
                    if (chargeDisplay1 == "y" && chargeDisplay2 == "y") {
						typeStr = "ro,ro,ro,hed";
						//intialize grid
						chargeNumberGrid3Columns == null;
						doChargeNumberInitGrid3Columns(chargeLabel1,chargeLabel2,typeStr);
						displayChargeNumber3Columns(chargeNumberForIndirectColl[key],canEditChargeData,prAccountColl[key],currentChargeType);
					}else {
						if (cellValue(selectedRowId,"chargeNumbersFromDirectedChargeI") == 'Y') {
							typeStr = "ro";
							if (editDirectedCharge1 == "Y") {
								typeStr += ",hed";
							}else {
								typeStr += ",ro";
							}
							if (editDirectedCharge2 == "Y") {
								typeStr += ",hed";
							}else {
								typeStr += ",ro";
							}
							typeStr += ",hed";
						}else {
                            if(canEditChargeData == "Y") {
                                typeStr = "ro,hed,hed,hed";
                            }else {
                                typeStr = "ro,ro,ro,ro";
                            }
                        }
						//intialize grid
						chargeNumberGrid3Columns == null;
						doChargeNumberInitGrid3Columns(chargeLabel1,chargeLabel2,typeStr);
						displayChargeNumberEmpty3Columns(canEditChargeData,prAccountColl[key],currentChargeType,maxChargeNumberToDisplay);
					}
				}
			}
			$("chargeNumber2ColumnsDivId").style["display"] = "none";
			$("chargeNumber3ColumnsDivId").style["display"] = "";
			$("chargeNumber4ColumnsDivId").style["display"] = "none";
			$("chargeNumber5ColumnsDivId").style["display"] = "none";
		}else if (chargeLabel1 != null && chargeLabel1.length > 0) {
			var typeStr = "ro,ro,ro";
			if(currentChargeType == "d") {
				var directedCharge = cellValue(selectedRowId,"directedChargeForDirect");
				if (directedCharge == "Y") {
					if (directedChargeTypeEditable == "Y" && canEditChargeData == "Y" ) {
                        $("chargeTypeD").disabled = false;
					    $("chargeTypeI").disabled = false;
                    }else {
                        $("chargeTypeD").disabled = true;
					    $("chargeTypeI").disabled = true;
                    }
					typeStr = "ro,ro,ro";
					//intialize grid
					chargeNumberGrid2Columns == null;
					doChargeNumberInitGrid2Columns(chargeLabel1,typeStr);
					displayChargeNumber2Columns(chargeNumberForDirectColl[key],"N",prAccountColl[key],currentChargeType);
				}else {
                    if(canEditChargeData == "Y") {
                        $("chargeTypeD").disabled = false;
					    $("chargeTypeI").disabled = false;
                    }else {
                        $("chargeTypeD").disabled = true;
					    $("chargeTypeI").disabled = true;
                    }
                    if (chargeDisplay1 == "y") {
						typeStr = "ro,ro,hed";
						//intialize grid
						chargeNumberGrid2Columns == null;
						doChargeNumberInitGrid2Columns(chargeLabel1,typeStr);
						displayChargeNumber2Columns(chargeNumberForDirectColl[key],canEditChargeData,prAccountColl[key],currentChargeType);
					}else {
                        if(canEditChargeData == "Y") {
                            typeStr = "ro,hed,hed";
                        }else {
                            typeStr = "ro,ro,ro";
                        }
                        //intialize grid
						chargeNumberGrid2Columns == null;
						doChargeNumberInitGrid2Columns(chargeLabel1,typeStr);
						displayChargeNumberEmpty2Columns(canEditChargeData,prAccountColl[key],currentChargeType,maxChargeNumberToDisplay);
					}
				}
			}else {
				var directedCharge = cellValue(selectedRowId,"directedChargeForIndirect");
				if (directedCharge == "Y") {
					if (directedChargeTypeEditable == "Y" && canEditChargeData == "Y" ) {
                        $("chargeTypeD").disabled = false;
					    $("chargeTypeI").disabled = false;
                    }else {
                        $("chargeTypeD").disabled = true;
					    $("chargeTypeI").disabled = true;
                    }
					typeStr = "ro,ro,ro";
					//intialize grid
					chargeNumberGrid2Columns == null;
					doChargeNumberInitGrid2Columns(chargeLabel1,typeStr);
					displayChargeNumber2Columns(chargeNumberForIndirectColl[key],"N",prAccountColl[key],currentChargeType);
				}else {
                    if(canEditChargeData == "Y") {
                        $("chargeTypeD").disabled = false;
					    $("chargeTypeI").disabled = false;
                    }else {
                        $("chargeTypeD").disabled = true;
					    $("chargeTypeI").disabled = true;
                    }
                    if (chargeDisplay1 == "y") {
						typeStr = "ro,ro,hed";
						//intialize grid
						chargeNumberGrid2Columns == null;
						doChargeNumberInitGrid2Columns(chargeLabel1,typeStr);
						displayChargeNumber2Columns(chargeNumberForIndirectColl[key],canEditChargeData,prAccountColl[key],currentChargeType);
					}else {
                        if(canEditChargeData == "Y") {
                            typeStr = "ro,hed,hed";
                        }else {
                            typeStr = "ro,ro,ro";
                        }
                        //intialize grid
						chargeNumberGrid2Columns == null;
						doChargeNumberInitGrid2Columns(chargeLabel1,typeStr);
						displayChargeNumberEmpty2Columns(canEditChargeData,prAccountColl[key],currentChargeType,maxChargeNumberToDisplay);
					}
				}
			}
			$("chargeNumber2ColumnsDivId").style["display"] = "";
			$("chargeNumber3ColumnsDivId").style["display"] = "none";
			$("chargeNumber4ColumnsDivId").style["display"] = "none";
			$("chargeNumber5ColumnsDivId").style["display"] = "none";
		}
	}else {
		$("chargeNumber2ColumnsDivId").style["display"] = "none";
		$("chargeNumber3ColumnsDivId").style["display"] = "none";
		$("chargeNumber4ColumnsDivId").style["display"] = "none";
		$("chargeNumber5ColumnsDivId").style["display"] = "none";
	}	
	
	//determine whether to show copy
    displayCopyButton();

    //po
	if (poRequired == "p") {
		//po number
		$("poLabelSpan").style["display"] = "";
		if (currentChargeType == "d") {
			if (facAccountSysPoForDirectColl[key].length == 0) {
				$("poComboSpan").style["display"] = "none";
				$("poInputSpan").style["display"] = "";
				$("poInput").value = cellValue(selectedRowId,"poNumber");
			}else {
				//populate po dropdown
				loadPo(facAccountSysPoForDirectColl[key]);
				$("poComboSpan").style["display"] = "";
				$("poInputSpan").style["display"] = "none";
			}
		}else {
			if (facAccountSysPoForIndirectColl[key].length == 0) {
				$("poComboSpan").style["display"] = "none";
				$("poInputSpan").style["display"] = "";
				$("poInput").value = cellValue(selectedRowId,"poNumber");
			}else {
				//populate po dropdown
				loadPo(facAccountSysPoForIndirectColl[key]);
				$("poComboSpan").style["display"] = "";
				$("poInputSpan").style["display"] = "none";
			}
		}
		if (poSeqRequired == 'U') {
			$("poLineSpan").style["display"] = "";
		}else {
			$("poLineSpan").style["display"] = "none";	
		}
		$("poLineInput").value = cellValue(selectedRowId,"releaseNumber");

		//po unit price
		if (unitPriceRequired == 'Required' || unitPriceRequired == 'Display') {
			$("poQtyLabelSpan").style["display"] = "";
			$("poQtyUomSpan").style["display"] = "";
			$("poUnitPriceLabelSpan").style["display"] = "";
			$("poUnitPriceCurrencySpan").style["display"] = "";
			var uofqpe = cellValue(selectedRowId,"unitOfSaleQuantityPerEach");
			if (uofqpe != null) {
				if (uofqpe.length > 0) {
					$("poQty").value = cellValue(selectedRowId,"qty")*uofqpe;
				}else {
					$("poQty").value = "";
				}
			}else {
				$("poQty").value = "";
			}
			$("poUom").value = cellValue(selectedRowId,"unitOfSale");
			$("poUnitPrice").value = cellValue(selectedRowId,"unitOfSalePrice");
			loadCurrency();
			if (unitPriceRequired == 'Display') {
				$("poQty").disabled = true;
				$("poUom").disabled = true;
				$("poUnitPrice").disabled = true;
				$("currencyCombo").disabled = true;
			}
		}else {
			$("poQtyLabelSpan").style["display"] = "none";
			$("poQtyUomSpan").style["display"] = "none";
			$("poUnitPriceLabelSpan").style["display"] = "none";
			$("poUnitPriceCurrencySpan").style["display"] = "none";
		}
	}else {
		$("poLabelSpan").style["display"] = "none";
		$("poComboSpan").style["display"] = "none";
		$("poInputSpan").style["display"] = "none";
		$("poLineSpan").style["display"] = "none";
		$("poQtyLabelSpan").style["display"] = "none";
		$("poQtyUomSpan").style["display"] = "none";
		$("poUnitPriceLabelSpan").style["display"] = "none";
		$("poUnitPriceCurrencySpan").style["display"] = "none";
	}
}

function copyLineData() {
    var canEditMr = $("canEditMr").value;
    if (canEditMr == "Y" || "Y" == cellValue(selectedRowId,"orderMaintenancePerm")) {
        var key = cellValue(selectedRowId,"lineItem");

    }
} //end of method

function saveCurrenData() {
	var canEditMr = $("canEditMr").value;
	if (canEditMr == "Y" || "Y" == cellValue(selectedRowId,"orderMaintenancePerm") || "Y" == cellValue(selectedRowId,"canEditLineChargeData") || ("APPROVE" == $v("viewType") && "compchk4" == $v("prStatus"))) {
		var key = cellValue(selectedRowId,"lineItem");
		var currentChargeType = cellValue(selectedRowId,"chargeType");
		if(!homeCompanyLogin){
			if ($("scrapObsolete").checked) {
				mygrid.cells(selectedRowId,mygrid.getColIndexById("scrap")).setValue("y");
			}else {
				mygrid.cells(selectedRowId,mygrid.getColIndexById("scrap")).setValue("n");
			}
		}
		mygrid.cells(selectedRowId,mygrid.getColIndexById("deliveryType")).setValue($("deliveryType").value);
		mygrid.cells(selectedRowId,mygrid.getColIndexById("defaultNeedByDate")).setValue($("deliverDate").value);

        if( $v("oneTimeShipToChanged") == 'Y') {
			mygrid.cells(selectedRowId,mygrid.getColIndexById("shipToLocationId")).setValue("");
			mygrid.cells(selectedRowId,mygrid.getColIndexById("deliveryPoint")).setValue("");
		}else if ($v("allowOneTimeShipTo") == 'Y' &&  $v("updatable") == 'Y') {

		}else {
			var deliverToEle = $("deliverTo");
			var deliverTo = deliverToEle.value;
			var deliverToDescription = deliverToEle.options[deliverToEle.selectedIndex].text;
            mygrid.cells(selectedRowId,mygrid.getColIndexById("shipToLocationId")).setValue($("dock").value);
		    mygrid.cells(selectedRowId,mygrid.getColIndexById("deliveryPoint")).setValue(deliverTo);
            mygrid.cells(selectedRowId,mygrid.getColIndexById("deliveryPointDesc")).setValue(deliverToDescription);
        }

        var poRequired = "n";
		var unitPriceRequired = "N/A";
		var prAccountRequired = "n";
		var chargeLabel1 = "";
		var chargeLabel2 = "";
		var chargeLabel3 = "";
		var chargeLabel4 = "";
		for(var i = 0; i < prRulesColl.length; i++) {
			if (currentChargeType == prRulesColl[i].chargeType) {
				poRequired = prRulesColl[i].poRequired;
				unitPriceRequired = prRulesColl[i].unitPriceRequired;
				prAccountRequired = prRulesColl[i].prAccountRequired;
				chargeLabel1 = prRulesColl[i].chargeLabel1;
				chargeLabel2 = prRulesColl[i].chargeLabel2;
				chargeLabel3 = prRulesColl[i].chargeLabel3;
				chargeLabel4 = prRulesColl[i].chargeLabel4;
				break;
			}
		}
		//PO number
		if (poRequired == "p") {
			savePoData(unitPriceRequired);
		} //end of po required
		//charge numbers
		if (prAccountRequired == "y") {
			if ((chargeLabel1 != null && chargeLabel1.length > 0) && (chargeLabel2 != null && chargeLabel2.length > 0) &&
				 (chargeLabel3 != null && chargeLabel3.length > 0) && (chargeLabel4 != null && chargeLabel4.length > 0)) {
				saveChargeNumber5Columns(key,currentChargeType);
			}else if ((chargeLabel1 != null && chargeLabel1.length > 0) && (chargeLabel2 != null && chargeLabel2.length > 0) &&
				       (chargeLabel3 != null && chargeLabel3.length > 0)) {
				saveChargeNumber4Columns(key,currentChargeType);
			}else if ((chargeLabel1 != null && chargeLabel1.length > 0) && (chargeLabel2 != null && chargeLabel2.length > 0)) {
				saveChargeNumber3Columns(key,currentChargeType);
			}else if (chargeLabel1 != null && chargeLabel1.length > 0) {
			  	saveChargeNumber2Columns(key,currentChargeType);
			}
		}
		
	} //end of canEditMr
} //end of saveCurrentData

function savePreviousChargeData() {
	var canEditMr = $("canEditMr").value;
	if (canEditMr == "Y" || "Y" == cellValue(selectedRowId,"orderMaintenancePerm") || "Y" == cellValue(selectedRowId,"canEditLineChargeData") || ("APPROVE" == $v("viewType") && "compchk4" == $v("prStatus"))) {
		var key = cellValue(selectedRowId,"lineItem");
		var currentChargeType = cellValue(selectedRowId,"chargeType");

		var prAccountRequired = "n";
		var chargeLabel1 = "";
		var chargeLabel2 = "";
		var chargeLabel3 = "";
		var chargeLabel4 = "";
		for(var i = 0; i < prRulesColl.length; i++) {
			if (currentChargeType == prRulesColl[i].chargeType) {
				prAccountRequired = prRulesColl[i].prAccountRequired;
				chargeLabel1 = prRulesColl[i].chargeLabel1;
				chargeLabel2 = prRulesColl[i].chargeLabel2;
				chargeLabel3 = prRulesColl[i].chargeLabel3;
				chargeLabel4 = prRulesColl[i].chargeLabel4;
				break;
			}
		}
		//charge numbers
		if (prAccountRequired == "y") {
			if ((chargeLabel1 != null && chargeLabel1.length > 0) && (chargeLabel2 != null && chargeLabel2.length > 0) &&
				 (chargeLabel3 != null && chargeLabel3.length > 0) && (chargeLabel4 != null && chargeLabel4.length > 0)) {
				saveChargeNumber5Columns(key,currentChargeType);
			}else if ((chargeLabel1 != null && chargeLabel1.length > 0) && (chargeLabel2 != null && chargeLabel2.length > 0) &&
				       (chargeLabel3 != null && chargeLabel3.length > 0)) {
				saveChargeNumber4Columns(key,currentChargeType);
			}else if ((chargeLabel1 != null && chargeLabel1.length > 0) && (chargeLabel2 != null && chargeLabel2.length > 0)) {
				saveChargeNumber3Columns(key,currentChargeType);
			}else if (chargeLabel1 != null && chargeLabel1.length > 0) {
			  	saveChargeNumber2Columns(key,currentChargeType);
			}
		}
	} //end of canEditMr
} //end of savePreviousChargeData

function saveChargeNumber2Columns(lineKeyId,currentChargeType) {
	//first remove previous data
    removeChargeTypeFromData(lineKeyId,currentChargeType);

    var chargeNumbers = "";
	var j = prAccountColl[lineKeyId].length;
	for (var i = 1; i <= chargeNumberGrid2Columns.getRowsNum(); i++) {
		var chargeNumber1 = chargeNumberGrid2Columns.cells(i,1).getValue();
		var percent = chargeNumberGrid2Columns.cells(i,2).getValue();

		if (chargeNumber1 != null && chargeNumber1.length > 0 && chargeNumber1 != "&nbsp;") {
			prAccountColl[lineKeyId][j++] = {
				accountNumber: 	chargeNumber1.trim(),
				accountNumber2:   '',
				percentage:			percent,
				chargeType:			currentChargeType
			};
			if (percent != null && percent.length > 0 && percent != "&nbsp;" ) {
				chargeNumbers += chargeNumber1.trim()+"!"+percent.trim()+"|";
			}
		}
	} //end of loop
	if (chargeNumbers.length > 0) {
		mygrid.cells(selectedRowId,mygrid.getColIndexById("chargeNumbers")).setValue(chargeNumbers);
	}
}

function saveChargeNumber3Columns(lineKeyId,currentChargeType) {
	//first remove previous data
    removeChargeTypeFromData(lineKeyId,currentChargeType);

    var chargeNumbers = "";
	var j = prAccountColl[lineKeyId].length;
	for (var i = 1; i <= chargeNumberGrid3Columns.getRowsNum(); i++) {
		var chargeNumber1 = chargeNumberGrid3Columns.cells(i,1).getValue();
		var chargeNumber2 = chargeNumberGrid3Columns.cells(i,2).getValue();
		var percent = chargeNumberGrid3Columns.cells(i,3).getValue();

		if ((chargeNumber1 != null && chargeNumber1.length > 0 && chargeNumber1 != "&nbsp;") ||
			 (chargeNumber2 != null && chargeNumber2.length > 0 && chargeNumber2 != "&nbsp;")) {
			prAccountColl[lineKeyId][j++] = {
				accountNumber: 	chargeNumber1.trim(),
				accountNumber2:   chargeNumber2.trim(),
				percentage:			percent,
				chargeType:			currentChargeType
			};
			if (percent != null && percent.length > 0 && percent != "&nbsp;" ) {
				chargeNumbers += chargeNumber1.trim()+"!"+chargeNumber2.trim()+"!"+percent.trim()+"|";
			}
		}
	}
	if (chargeNumbers.length > 0) {
		mygrid.cells(selectedRowId,mygrid.getColIndexById("chargeNumbers")).setValue(chargeNumbers);
	}
}

function removeChargeTypeFromData(lineKeyId,currentChargeType) {
    //remove the last element first since the splice method will skip the data up
    for (var i = prAccountColl[lineKeyId].length - 1; i >= 0; i-- ) {
        if (prAccountColl[lineKeyId][i].chargeType == currentChargeType) {
            prAccountColl[lineKeyId].splice(i,1);
        }
    }
}

function saveChargeNumber4Columns(lineKeyId,currentChargeType) {
    //first remove previous data
    removeChargeTypeFromData(lineKeyId,currentChargeType);

    var chargeNumbers = "";
	var j = prAccountColl[lineKeyId].length;
	for (var i = 1; i <= chargeNumberGrid4Columns.getRowsNum(); i++) {
		var chargeNumber1 = chargeNumberGrid4Columns.cells(i,1).getValue();
		var chargeNumber2 = chargeNumberGrid4Columns.cells(i,2).getValue();
		var chargeNumber3 = chargeNumberGrid4Columns.cells(i,3).getValue();
		var percent = chargeNumberGrid4Columns.cells(i,4).getValue();

		if ((chargeNumber1 != null && chargeNumber1.length > 0 && chargeNumber1 != "&nbsp;") ||
			 (chargeNumber2 != null && chargeNumber2.length > 0 && chargeNumber2 != "&nbsp;") ||
			 (chargeNumber3 != null && chargeNumber3.length > 0 && chargeNumber3 != "&nbsp;")) {
			prAccountColl[lineKeyId][j++] = {
				accountNumber: 	chargeNumber1.trim(),
				accountNumber2:   chargeNumber2.trim(),
				accountNumber3:   chargeNumber3.trim(),
				percentage:			percent,
				chargeType:			currentChargeType
			};
			if (percent != null && percent.length > 0 && percent != "&nbsp;" ) {
				chargeNumbers += chargeNumber1.trim()+"!"+chargeNumber2.trim()+"!"+chargeNumber3.trim()+"!"+percent.trim()+"|";
			}
		}
	}
	if (chargeNumbers.length > 0) {
		mygrid.cells(selectedRowId,mygrid.getColIndexById("chargeNumbers")).setValue(chargeNumbers);
	}
}

function saveChargeNumber5Columns(lineKeyId,currentChargeType) {
	//first remove previous data
    removeChargeTypeFromData(lineKeyId,currentChargeType);

    var chargeNumbers = "";
	var j = prAccountColl[lineKeyId].length;
	for (var i = 1; i <= chargeNumberGrid5Columns.getRowsNum(); i++) {
		var chargeNumber1 = chargeNumberGrid5Columns.cells(i,1).getValue();
		var chargeNumber2 = chargeNumberGrid5Columns.cells(i,2).getValue();
		var chargeNumber3 = chargeNumberGrid5Columns.cells(i,3).getValue();
		var chargeNumber4 = chargeNumberGrid5Columns.cells(i,4).getValue();
		var percent = chargeNumberGrid5Columns.cells(i,5).getValue();

		if ((chargeNumber1 != null && chargeNumber1.length > 0 && chargeNumber1 != "&nbsp;") ||
			 (chargeNumber2 != null && chargeNumber2.length > 0 && chargeNumber2 != "&nbsp;") ||
			 (chargeNumber3 != null && chargeNumber3.length > 0 && chargeNumber3 != "&nbsp;") ||
			 (chargeNumber4 != null && chargeNumber4.length > 0 && chargeNumber4 != "&nbsp;")) {
			prAccountColl[lineKeyId][j++] = {
				accountNumber: 	chargeNumber1.trim(),
				accountNumber2:   chargeNumber2.trim(),
				accountNumber3:   chargeNumber3.trim(),
				accountNumber4:   chargeNumber4.trim(),
				percentage:			percent,
				chargeType:			currentChargeType
			};
			if (percent != null && percent.length > 0 && percent != "&nbsp;" ) {
				chargeNumbers += chargeNumber1.trim()+"!"+chargeNumber2.trim()+"!"+chargeNumber3.trim()+"!"+chargeNumber4.trim()+"!"+percent.trim()+"|";
			}
		}
	}
	if (chargeNumbers.length > 0) {
		mygrid.cells(selectedRowId,mygrid.getColIndexById("chargeNumbers")).setValue(chargeNumbers);
	}
}

function savePoData(unitPriceRequired) {
	//po number
	var tmpPoNumber = $("poCombo");
	if (tmpPoNumber == null || tmpPoNumber.value.length == 0) {
		tmpPoNumber = $("poInput");
	}
	if (tmpPoNumber != null && tmpPoNumber.value.length > 0) {
		mygrid.cells(selectedRowId,mygrid.getColIndexById("poNumber")).setValue(tmpPoNumber.value);
	}else {
		mygrid.cells(selectedRowId,mygrid.getColIndexById("poNumber")).setValue("");
	}
	var tmpReleaseNumber = $("poLineInput");
	if (tmpReleaseNumber != null && tmpReleaseNumber.value.length > 0) {
		mygrid.cells(selectedRowId,mygrid.getColIndexById("releaseNumber")).setValue(tmpReleaseNumber.value);
	}else {
		mygrid.cells(selectedRowId,mygrid.getColIndexById("releaseNumber")).setValue("");
	}

	//po unit price
	if (unitPriceRequired == 'Required' || unitPriceRequired == 'Display') {
		var tmp = $("poQty");
		if (tmp != null && tmp.value.length > 0 && tmp.value != 0) {
			var tmp2 = tmp.value/cellValue(selectedRowId,"qty");
			mygrid.cells(selectedRowId,mygrid.getColIndexById("unitOfSaleQuantityPerEach")).setValue(tmp2);
		}else {
			mygrid.cells(selectedRowId,mygrid.getColIndexById("unitOfSaleQuantityPerEach")).setValue("");
		}
		tmp = $("poUom");
		if (tmp != null && tmp.value.length > 0) {
			mygrid.cells(selectedRowId,mygrid.getColIndexById("unitOfSale")).setValue(tmp.value);
		}else {
			mygrid.cells(selectedRowId,mygrid.getColIndexById("unitOfSale")).setValue("");
		}
		tmp = $("poUnitPrice");
		if (tmp != null && tmp.value.length > 0) {
			mygrid.cells(selectedRowId,mygrid.getColIndexById("unitOfSalePrice")).setValue(tmp.value);
		}else {
			mygrid.cells(selectedRowId,mygrid.getColIndexById("unitOfSalePrice")).setValue("");
		}
		
		if (unitPriceRequired == 'Display') {
			$("currencyCombo").disabled = '';
		}
		tmp = $("currencyCombo");
		if (tmp != null) {
			if (tmp.value.length > 0) {
				mygrid.cells(selectedRowId,mygrid.getColIndexById("currencyId")).setValue(tmp.value);
			}
		}
		if (unitPriceRequired == 'Display') {
			$("currencyCombo").disabled = true;
		}

	} //end of unit price required or display
}

function displayChargeNumberEmpty2Columns(canEditChargeNumber,savedDataArray,currentChargeType,maxChargeNumberToDisplay) {
	clearChargeTable2Columns();
	var rowCount = 0;
	for(var j = 0; j < savedDataArray.length; j++ ) {
        if (currentChargeType == savedDataArray[j].chargeType) {
            if (savedDataArray[j].accountNumber != null && savedDataArray[j].accountNumber.length > 0) {
                chargeNumberGrid2Columns.addRow(rowCount+1,"",rowCount);
                var count = 0;
                chargeNumberGrid2Columns.cells(rowCount+1,count++).setValue(canEditChargeNumber);
                chargeNumberGrid2Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].accountNumber);
                if (savedDataArray[j].percentage != 0) {
                    chargeNumberGrid2Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].percentage);
                }else {
                    chargeNumberGrid2Columns.cells(rowCount+1,count++).setValue('');
                }
                rowCount++;
            }
        }
    }
    if (canEditChargeNumber == 'Y') {
		var emptyRow = emptyMaxRow-rowCount;
        try {
            var tmpNumberOfRow = parseInt(maxChargeNumberToDisplay);
            if (tmpNumberOfRow > 0) {
                emptyRow = tmpNumberOfRow - rowCount;
            }
        }catch(e) {alert("ererror");}

        for (var i = 0; i < emptyRow; i++) {
			chargeNumberGrid2Columns.addRow(rowCount+1,"",rowCount);
			var count = 0;
			chargeNumberGrid2Columns.cells(rowCount+1,count++).setValue(canEditChargeNumber);
			chargeNumberGrid2Columns.cells(rowCount+1,count++).setValue('');
            if (emptyRow == 1) {
                chargeNumberGrid2Columns.cells(rowCount+1,count++).setValue('100');
            }else {
                chargeNumberGrid2Columns.cells(rowCount+1,count++).setValue('');
            }
            rowCount++;
		}
	}
}

function displayChargeNumber2Columns(dataArray,canEditChargeNumber,savedDataArray,currentChargeType) {
	clearChargeTable2Columns();
	//looping thru master data
	for (var i = 0; i < dataArray.length; i++) {
		var percent = dataArray[i].percent;
		if (percent == null || percent.length == 0) {
			//looping thru saved data
			for (var j = 0; j < savedDataArray.length; j++) {
				if (currentChargeType == savedDataArray[j].chargeType) {
					if (dataArray[i].chargeNumber1 == savedDataArray[j].accountNumber) {
						var tmp = savedDataArray[j].percentage;
						if (tmp != null && tmp.length > 0) {
							percent = tmp;
							break;
						}
					}
				}
			}
		}
		chargeNumberGrid2Columns.addRow(i+1,"",i);
		var count = 0;
		chargeNumberGrid2Columns.cells(i+1,count++).setValue(canEditChargeNumber);
		chargeNumberGrid2Columns.cells(i+1,count++).setValue(dataArray[i].chargeNumber1);
		chargeNumberGrid2Columns.cells(i+1,count++).setValue(percent);
	}
}

function clearChargeTable2Columns() {
	for(var i = chargeNumberGrid2Columns.getRowsNum(); i > 0; i--) {
		chargeNumberGrid2Columns.deleteRow(i);
	}
}

function displayChargeNumberEmpty3Columns(canEditChargeNumber,savedDataArray,currentChargeType,maxChargeNumberToDisplay) {
	clearChargeTable3Columns();
	var rowCount = 0;
	for(var j = 0; j < savedDataArray.length; j++ ) {
		if (currentChargeType == savedDataArray[j].chargeType) {
			chargeNumberGrid3Columns.addRow(rowCount+1,"",rowCount);
			var count = 0;
			chargeNumberGrid3Columns.cells(rowCount+1,count++).setValue(canEditChargeNumber);
			chargeNumberGrid3Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].accountNumber);
			chargeNumberGrid3Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].accountNumber2);
			if (savedDataArray[j].percentage != 0) {
				chargeNumberGrid3Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].percentage);
			}else {
				chargeNumberGrid3Columns.cells(rowCount+1,count++).setValue('');
			}
			rowCount++;
		}
	}
	if (canEditChargeNumber == 'Y') {
		var emptyRow = emptyMaxRow-rowCount;
        try {
            var tmpNumberOfRow = parseInt(maxChargeNumberToDisplay);
            if (tmpNumberOfRow > 0) {
                emptyRow = tmpNumberOfRow - rowCount;
            }
        }catch(e) {}

        for (var i = 0; i < emptyRow; i++) {
			chargeNumberGrid3Columns.addRow(rowCount+1,"",rowCount);
			var count = 0;
			chargeNumberGrid3Columns.cells(rowCount+1,count++).setValue(canEditChargeNumber);
			chargeNumberGrid3Columns.cells(rowCount+1,count++).setValue('');
			chargeNumberGrid3Columns.cells(rowCount+1,count++).setValue('');
            if (emptyRow == 1) {
                chargeNumberGrid3Columns.cells(rowCount+1,count++).setValue('100');
            }else {
                chargeNumberGrid3Columns.cells(rowCount+1,count++).setValue('');
            }
            rowCount++;
		}
	}
}

function displayChargeNumber3Columns(dataArray,canEditChargeNumber,savedDataArray,currentChargeType) {
	clearChargeTable3Columns();
	//looping thru master data
	for (var i = 0; i < dataArray.length; i++) {
		var percent = dataArray[i].percent;
		if (percent == null || percent.length == 0) {
			//looping thru saved data
			for (var j = 0; j < savedDataArray.length; j++) {
				if (currentChargeType == savedDataArray[j].chargeType) {
					if (dataArray[i].chargeNumber1 == savedDataArray[j].accountNumber &&
						 dataArray[i].chargeNumber2 == savedDataArray[j].accountNumber2) {
						var tmp = savedDataArray[j].percentage;
						if (tmp != null && tmp.length > 0) {
							percent = tmp;
							break;
						}
					}
				}
			}
		}
		chargeNumberGrid3Columns.addRow(i+1,"",i);
		var count = 0;
		chargeNumberGrid3Columns.cells(i+1,count++).setValue(canEditChargeNumber);
		chargeNumberGrid3Columns.cells(i+1,count++).setValue(dataArray[i].chargeNumber1);
		chargeNumberGrid3Columns.cells(i+1,count++).setValue(dataArray[i].chargeNumber2);
		chargeNumberGrid3Columns.cells(i+1,count++).setValue(percent);
	}
}

function clearChargeTable3Columns() {
	for(var i = chargeNumberGrid3Columns.getRowsNum(); i > 0; i--) {
		chargeNumberGrid3Columns.deleteRow(i);
	}
}

function displayChargeNumberEmpty4Columns(canEditChargeNumber,savedDataArray,currentChargeType,maxChargeNumberToDisplay) {
	clearChargeTable4Columns();
	var rowCount = 0;
    for(var j = 0; j < savedDataArray.length; j++ ) {
        if (currentChargeType == savedDataArray[j].chargeType) {
            chargeNumberGrid4Columns.addRow(rowCount+1,"",rowCount);
            var count = 0;
            chargeNumberGrid4Columns.cells(rowCount+1,count++).setValue(canEditChargeNumber);
            chargeNumberGrid4Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].accountNumber);
            chargeNumberGrid4Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].accountNumber2);
            chargeNumberGrid4Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].accountNumber3);
            if (savedDataArray[j].percentage != 0) {
                chargeNumberGrid4Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].percentage);
            }else {
                chargeNumberGrid4Columns.cells(rowCount+1,count++).setValue('');
            }
            rowCount++;
        }
    }
    if (canEditChargeNumber == 'Y') {
		var emptyRow = emptyMaxRow-rowCount;
        try {
            var tmpNumberOfRow = parseInt(maxChargeNumberToDisplay);
            if (tmpNumberOfRow > 0) {
                emptyRow = tmpNumberOfRow - rowCount;
            }
        }catch(e) {}

        for (var i = 0; i < emptyRow; i++) {
			chargeNumberGrid4Columns.addRow(rowCount+1,"",rowCount);
			var count = 0;
			chargeNumberGrid4Columns.cells(rowCount+1,count++).setValue(canEditChargeNumber);
			chargeNumberGrid4Columns.cells(rowCount+1,count++).setValue('');
			chargeNumberGrid4Columns.cells(rowCount+1,count++).setValue('');
			chargeNumberGrid4Columns.cells(rowCount+1,count++).setValue('');
            if (emptyRow == 1) {
                chargeNumberGrid4Columns.cells(rowCount+1,count++).setValue('100');
            }else {
                chargeNumberGrid4Columns.cells(rowCount+1,count++).setValue('');
            }
            rowCount++;
		}
	}
}

function displayChargeNumber4Columns(dataArray,canEditChargeNumber,savedDataArray,currentChargeType) {
	clearChargeTable4Columns();
	//looping thru master data
	for (var i = 0; i < dataArray.length; i++) {
		var percent = dataArray[i].percent;
		if (percent == null || percent.length == 0) {
			//looping thru saved data
			for (var j = 0; j < savedDataArray.length; j++) {
				if (currentChargeType == savedDataArray[j].chargeType) {
					if (dataArray[i].chargeNumber1 == savedDataArray[j].accountNumber ||
						 dataArray[i].chargeNumber2 == savedDataArray[j].accountNumber2 ||
						 dataArray[i].chargeNumber3 == savedDataArray[j].accountNumber3) {
						var tmp = savedDataArray[j].percentage;
						if (tmp != null && tmp.length > 0) {
							percent = tmp;
							break;
						}
					}
				}
			}
		}
		chargeNumberGrid4Columns.addRow(i+1,"",i);
		var count = 0;
		chargeNumberGrid4Columns.cells(i+1,count++).setValue(canEditChargeNumber);
		chargeNumberGrid4Columns.cells(i+1,count++).setValue(dataArray[i].chargeNumber1);
		chargeNumberGrid4Columns.cells(i+1,count++).setValue(dataArray[i].chargeNumber2);
		chargeNumberGrid4Columns.cells(i+1,count++).setValue(dataArray[i].chargeNumber3);
		chargeNumberGrid4Columns.cells(i+1,count++).setValue(percent);
	}
}

function clearChargeTable4Columns() {
	for(var i = chargeNumberGrid4Columns.getRowsNum(); i > 0; i--) {
		chargeNumberGrid4Columns.deleteRow(i);
	}
}

function displayChargeNumberEmpty5Columns(canEditChargeNumber,savedDataArray,currentChargeType,maxChargeNumberToDisplay) {
	clearChargeTable5Columns();
	var rowCount = 0;
	for(var j = 0; j < savedDataArray.length; j++ ) {
		if (currentChargeType == savedDataArray[j].chargeType) {
			chargeNumberGrid5Columns.addRow(rowCount+1,"",rowCount);
			var count = 0;
			chargeNumberGrid5Columns.cells(rowCount+1,count++).setValue(canEditChargeNumber);
			chargeNumberGrid5Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].accountNumber);
			chargeNumberGrid5Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].accountNumber2);
			chargeNumberGrid5Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].accountNumber3);
			chargeNumberGrid5Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].accountNumber4);
			if (savedDataArray[j].percentage != 0) {
				chargeNumberGrid5Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].percentage);
			}else {
				chargeNumberGrid5Columns.cells(rowCount+1,count++).setValue('');
			}
			rowCount++;
		}
	}
	if (canEditChargeNumber == 'Y') {
		var emptyRow = emptyMaxRow-rowCount;
        try {
            var tmpNumberOfRow = parseInt(maxChargeNumberToDisplay);
            if (tmpNumberOfRow > 0) {
                emptyRow = tmpNumberOfRow - rowCount;
            }
        }catch(e) {}

        for (var i = 0; i < emptyRow; i++) {
			chargeNumberGrid5Columns.addRow(rowCount+1,"",rowCount);
			var count = 0;
			chargeNumberGrid5Columns.cells(rowCount+1,count++).setValue(canEditChargeNumber);
			chargeNumberGrid5Columns.cells(rowCount+1,count++).setValue('');
			chargeNumberGrid5Columns.cells(rowCount+1,count++).setValue('');
			chargeNumberGrid5Columns.cells(rowCount+1,count++).setValue('');
			chargeNumberGrid5Columns.cells(rowCount+1,count++).setValue('');
            if (emptyRow == 1) {
                chargeNumberGrid5Columns.cells(rowCount+1,count++).setValue('100');
            }else {
                chargeNumberGrid5Columns.cells(rowCount+1,count++).setValue('');
            }
            rowCount++;
		}
	}
}

function displayChargeNumber5Columns(dataArray,canEditChargeNumber,savedDataArray,currentChargeType) {
	clearChargeTable5Columns();
	//looping thru master data
	for (var i = 0; i < dataArray.length; i++) {
		var percent = dataArray[i].percent;
		if (percent == null || percent.length == 0) {
			//looping thru saved data
			for (var j = 0; j < savedDataArray.length; j++) {
				if (currentChargeType == savedDataArray[j].chargeType) {
					if (dataArray[i].chargeNumber1 == savedDataArray[j].accountNumber ||
						 dataArray[i].chargeNumber2 == savedDataArray[j].accountNumber2 ||
						 dataArray[i].chargeNumber3 == savedDataArray[j].accountNumber3 ||
						 dataArray[i].chargeNumber4 == savedDataArray[j].accountNumber4) {
						var tmp = savedDataArray[j].percentage;
						if (tmp != null && tmp.length > 0) {
							percent = tmp;
							break;
						}
					}
				}
			}
		}
		chargeNumberGrid5Columns.addRow(i+1,"",i);
		var count = 0;
		chargeNumberGrid5Columns.cells(i+1,count++).setValue(canEditChargeNumber);
		chargeNumberGrid5Columns.cells(i+1,count++).setValue(dataArray[i].chargeNumber1);
		chargeNumberGrid5Columns.cells(i+1,count++).setValue(dataArray[i].chargeNumber2);
		chargeNumberGrid5Columns.cells(i+1,count++).setValue(dataArray[i].chargeNumber3);
		chargeNumberGrid5Columns.cells(i+1,count++).setValue(dataArray[i].chargeNumber4);
		chargeNumberGrid5Columns.cells(i+1,count++).setValue(percent);
	}
}

function clearChargeTable5Columns() {
	for(var i = chargeNumberGrid5Columns.getRowsNum(); i > 0; i--) {
		chargeNumberGrid5Columns.deleteRow(i);
	}
}

function loadCurrency() {
	var currencyCombo = $("currencyCombo");
	var selectedIndex = 0;
	var tmpVal = cellValue(selectedRowId,"currencyId");
	if (currencyCombo == null || currencyCombo.length == 0) {
		for (var i = 0; i < currencyColl.length; i++) {
			setOption(i,currencyColl[i].currencyDescription,currencyColl[i].currencyId, "currencyCombo");
			if (tmpVal == currencyColl[i].currencyId) {
				selectedIndex = i;
			}
		}
	}else {
		for(var i = 0; i < currencyCombo.length; i++) {
			if (tmpVal == currencyCombo.options[i].value) {
				selectedIndex = i;
				break;
			}
		}
	}
	currencyCombo.selectedIndex = selectedIndex;
}

function loadPo(dataArray) {
	var poCombo = $("poCombo");
	//clear previous data
	for (var i = poCombo.length; i > 0; i--) {
    poCombo[i] = null;
   }
	var selectedIndex = 0;
	var tmpVal = cellValue(selectedRowId,"poNumber");
	for (var i=0; i < dataArray.length; i++) {
		setOption(i,dataArray[i].poNumber,dataArray[i].poNumber, "poCombo");
		if (tmpVal == dataArray[i].poNumber) {
			selectedIndex = i;
		}
	}
	poCombo.selectedIndex = selectedIndex;	
}

function displayLineDetail() {
	$("lineItemDetail").innerHTML = messagesData.lineitemdetail+" "+selectedRowId;
	//set charge type
	setChargeType();

	//price
	setLineItemPrice(selectedRowId);
	setExtendedPrice();
	//scrap/obsolete
	if(!homeCompanyLogin){
		if (cellValue(selectedRowId,"scrap") == 'Y' || cellValue(selectedRowId,"scrap") == 'y') {
			$("scrapObsolete").checked = "checked";
		}else {
			$("scrapObsolete").checked = "";
		}
	}
	//delivery instructions
	var deliveryType = $("deliveryType");
	var selectedIndex = 0;
	var tmpVal = cellValue(selectedRowId,"deliveryType");
    /*Making the qty column read-only if the MR is a schedule*/
    var permission = cellValue(selectedRowId,"permission");
    if (permission == 'Y' && tmpVal == 'Schedule') {
        $("qty"+selectedRowId+"").readOnly = true;
        $("qty"+selectedRowId+"").disabled = true;
    }

    if (deliveryType == null || deliveryType.length == 0) {
		for (var i=0; i < deliveryTypeColl.length; i++) {
	    	setOption(i,deliveryTypeColl[i].description,deliveryTypeColl[i].deliveryType, "deliveryType");
			if (tmpVal == deliveryTypeColl[i].deliveryType) {
				selectedIndex = i;
			}
	  }
	}else {
		for (var i=0; i < deliveryTypeColl.length; i++) {
			if (tmpVal == deliveryTypeColl[i].deliveryType) {
				selectedIndex = i;
			}
	  }
	}
	deliveryType.selectedIndex = selectedIndex;
	//dock
    try {
    loadDock();
    }catch (ex) {}
    //deliveryTypeChanged();
    deliveryTypeLoad(); //calling this as I need to differentiate between a change and an initial load
    displayPoChargeNumber();
}

function displayPoChargeNumber() {

}

function loadDock() {
    try {
    var dock = $("dock");
	//clear previous data
	for (var i = dock.length; i > 0; i--) {
    dock[i] = null;
   }
	var selectedIndex = 0;
	var tmpVal = cellValue(selectedRowId,"shipToLocationId");
	var key = cellValue(selectedRowId,"lineItem");
	var dockColl = dockDeliveryPointColl[key].dockColl;
	var k = 0;
	for (var i=0; i < dockColl.length; i++) {
		if(k == 0 && dockColl.length > 1)
		{
			setOption(k,messagesData.selectone,"", "dock");
			k++;
			i = -1;
			continue;
		}
		setOption(k,dockColl[i].dockDesc,dockColl[i].dockLocationId, "dock");
		if (tmpVal == dockColl[i].dockLocationId) {
			selectedIndex = k;
		}
		k++;
	}
	dock.selectedIndex = selectedIndex;
	loadDeliverTo();
    } catch(ex) {}
}

function loadDeliverTo() {
    try {
    var selectedDock = $("dock").value;
    if(selectedDock == "")
    {
    	var deliverTo = $("deliverTo");
		for (var i = deliverTo.length; i > 0; i--) {
		    deliverTo[i] = null;
		   }
    	setOption(0,messagesData.selectone,"", "deliverTo");
    }
    else
    {
		var deliverTo = $("deliverTo");
		//clear previous data
		for (var i = deliverTo.length; i > 0; i--) {
	    deliverTo[i] = null;
	   }
		var selectedIndex = 0;
		var tmpVal = cellValue(selectedRowId,"deliveryPoint");
		var key = cellValue(selectedRowId,"lineItem");
		var dockColl = dockDeliveryPointColl[key].dockColl;
		for (var i=0; i < dockColl.length; i++) {
			if (dockColl[i].dockLocationId == selectedDock) {
				var deliveryPointColl = dockColl[i].deliveryPointColl;
					var k = 0;
					for (var j=0; j < deliveryPointColl.length; j++) {
						if(k == 0 && deliveryPointColl.length > 1)
							{
								setOption(k,messagesData.selectone,"", "deliverTo");
								k++;
								j = -1;
								continue;
							}
						setOption(k,deliveryPointColl[j].deliveryPointDesc,deliveryPointColl[j].deliveryPoint, "deliverTo");
						if (tmpVal == deliveryPointColl[j].deliveryPoint) {
							selectedIndex = k;
						}
						k++;
					}
				break;
			}
		}
		$('deliverTo').selectedIndex = selectedIndex;
    }
    } catch(ex) {}
}

function showDeliverySchedule()
{
  if (selectedRowId != null)
  {
     var companyId  =  $("companyId");
     var prNumber  =  $("prNumber");
     var lineItem  =  cellValue(selectedRowId,"lineItem");
	  var tmpQty  =  cellValue(selectedRowId,"qty");
	  var loc = "deliveryschedulemain.do?selectedRowId="+selectedRowId+"&source=materialRequest&companyId="+companyId.value+"&prNumber="+prNumber.value+"&lineItem="+lineItem+"&requestedQty="+tmpQty+"&prStatus="+$v("prStatus");     
     children[children.length] = openWinGeneric(loc,"showDeliverySchedule22","850","620","yes","100","100","no");
  }
}

function closeAllchildren()
{    
// You need to add all your submit button vlues here. If not, the window will close by itself right after we hit submit button.
		try {
			for(var n=0;n<children.length;n++) {
				children[n].closeAllchildren();
			}
		} catch(ex)
		{
		}
}

function undoCopyFromGrids()
{
	var curRowGrid = mygrid.getSelectedRowId();
	var k = 0;
	copyDeliveryType = null;
	copyDeliverDate = null;
	var setGridData;
	for(var i = 1; i < prAccountColl.length; i++)
	{
		if(mygrid.cells(i,mygrid.getColIndexById("directedChargeForDirect")).getValue() == 'Y' 
			|| mygrid.cells(i,mygrid.getColIndexById("directedChargeForIndirect")).getValue() == 'Y')
			setGridData = false;
		else
			setGridData = true;
		if(i != copyGridIndex)
			{
                mygrid.cells(i,mygrid.getColIndexById("scrap")).setValue(allOtherUndoData[i].scrapObsolete);
                mygrid.cells(i,mygrid.getColIndexById("deliveryType")).setValue(allOtherUndoData[i].copyDeliveryType);
				mygrid.cells(i,mygrid.getColIndexById("defaultNeedByDate")).setValue(allOtherUndoData[i].copyDeliverDate);
				mygrid.cells(i,mygrid.getColIndexById("shipToLocationId")).setValue(allOtherUndoData[i].dock);
				mygrid.cells(i,mygrid.getColIndexById("deliveryPoint")).setValue(allOtherUndoData[i].deliverTo);
				mygrid.cells(i,mygrid.getColIndexById("shipToLocationIdDesc")).setValue(allOtherUndoData[i].shipToLocationIdDesc);
				mygrid.cells(i,mygrid.getColIndexById("deliveryPointDesc")).setValue(allOtherUndoData[i].deliveryPointDesc);
				if(setGridData)
				{
					if(allOtherUndoData[i].originalChargeType != null)
					{			
						mygrid.cells(i,mygrid.getColIndexById("chargeType")).setValue(allOtherUndoData[i].originalChargeType);
					}
					prAccountColl[i] = allOtherUndoData[i].originalChargeTypeData ;
                    mygrid.cells(i,mygrid.getColIndexById("chargeNumbers")).setValue(allOtherUndoData[i].originalLineChargeNumber);
                }
				if(i == curRowGrid)
				{
					displayLineDetail();
				}
			}
	}
	var tmp = selectedRowId;
	selectedRowId = mygrid.getSelectedRowId();
	setChargeType();
	selectedRowId = tmp;
	$("undoSpan").style["display"] = "none";

}

function copyDataInGrid()
{
    copyGridIndex = mygrid.getSelectedRowId();
    saveCurrenData();

    var copyFrom = prAccountColl[copyGridIndex];
    var oneChargeTypeI = false;
    for(var i = 0; i < copyFrom.length; i++)
        if(copyFrom[i].percentage != '')
            {
                oneChargeTypeI = true;
                break;
            }

    var directedChargeForDirectCopyRow = mygrid.cells(copyGridIndex,mygrid.getColIndexById("directedChargeForDirect")).getValue();
    var directedChargeForIndirectCopyRow = mygrid.cells(copyGridIndex,mygrid.getColIndexById("directedChargeForIndirect")).getValue();

    if((directedChargeForDirectCopyRow != 'Y' && directedChargeForIndirectCopyRow != 'Y' && $('chargeTypeD').checked  && copyFrom.length != 0)
        || (directedChargeForDirectCopyRow != 'Y' && directedChargeForIndirectCopyRow != 'Y' && $('chargeTypeI').checked && oneChargeTypeI)
        || $v('deliverDate') != ''
        || $v('dock') != ''
        || $v('deliverTo') != ''
        ) {
        $("undoSpan").style["display"] = "";
        var curChargeType = mygrid.cells(copyGridIndex,mygrid.getColIndexById("chargeType")).getValue();
        allOtherUndoData = {};

        for(var i = 1; i < prAccountColl.length; i++) {
            if(i != copyGridIndex) {
                    //Save all previous data before copy
                    var dataCollection = new Array();
                    var nextChargeType = mygrid.cells(i,mygrid.getColIndexById("chargeType")).getValue();
                    if(curChargeType != nextChargeType) {
                        dataCollection = {copyDeliveryType:mygrid.cells(i,mygrid.getColIndexById("deliveryType")).getValue(),
                            copyDeliverDate:mygrid.cells(i,mygrid.getColIndexById("defaultNeedByDate")).getValue(),
                            originalChargeTypeData: prAccountColl[i],
                            scrapObsolete:mygrid.cells(i,mygrid.getColIndexById("scrap")).getValue(),
                            dock:mygrid.cells(i,mygrid.getColIndexById("shipToLocationId")).getValue(),
                            deliverTo:mygrid.cells(i,mygrid.getColIndexById("deliveryPoint")).getValue(),
                            shipToLocationIdDesc:mygrid.cells(i,mygrid.getColIndexById("shipToLocationIdDesc")).getValue(),
                            deliveryPointDesc:mygrid.cells(i,mygrid.getColIndexById("deliveryPointDesc")).getValue(),
                            originalChargeType:nextChargeType,
                            originalLineChargeNumber:mygrid.cells(i,mygrid.getColIndexById("chargeNumbers")).getValue()
                        }
                    }else {
                        dataCollection = {copyDeliveryType:mygrid.cells(i,mygrid.getColIndexById("deliveryType")).getValue(),
                            copyDeliverDate:mygrid.cells(i,mygrid.getColIndexById("defaultNeedByDate")).getValue(),
                            scrapObsolete:mygrid.cells(i,mygrid.getColIndexById("scrap")).getValue(),
                            dock:mygrid.cells(i,mygrid.getColIndexById("shipToLocationId")).getValue(),
                            deliverTo:mygrid.cells(i,mygrid.getColIndexById("deliveryPoint")).getValue(),
                            shipToLocationIdDesc:mygrid.cells(i,mygrid.getColIndexById("shipToLocationIdDesc")).getValue(),
                            deliveryPointDesc:mygrid.cells(i,mygrid.getColIndexById("deliveryPointDesc")).getValue(),
                            originalChargeTypeData: prAccountColl[i],
                            originalLineChargeNumber:mygrid.cells(i,mygrid.getColIndexById("chargeNumbers")).getValue()
                        }
                    }
                    allOtherUndoData[i] = dataCollection;

                    //Grid info copy to
                    if(directedChargeForDirectCopyRow != 'Y' && directedChargeForIndirectCopyRow != 'Y'
                        && mygrid.cells(i,mygrid.getColIndexById("directedChargeForDirect")).getValue() != 'Y'
                        && mygrid.cells(i,mygrid.getColIndexById("directedChargeForIndirect")).getValue() != 'Y')
                    {
                        prAccountColl[i] = copyFrom.slice();
                        if(curChargeType != nextChargeType)
                            mygrid.cells(i,mygrid.getColIndexById("chargeType")).setValue(curChargeType);
                        mygrid.cells(i,mygrid.getColIndexById("chargeNumbers")).setValue(mygrid.cells(copyGridIndex,mygrid.getColIndexById("chargeNumbers")).getValue());
                    }
                    //scrap
                    mygrid.cells(i,mygrid.getColIndexById("scrap")).setValue(mygrid.cells(copyGridIndex,mygrid.getColIndexById("scrap")).getValue());
                    //Delivery Instructions info copy to
                    mygrid.cells(i,mygrid.getColIndexById("deliveryType")).setValue(mygrid.cells(copyGridIndex,mygrid.getColIndexById("deliveryType")).getValue());
                    mygrid.cells(i,mygrid.getColIndexById("defaultNeedByDate")).setValue(mygrid.cells(copyGridIndex,mygrid.getColIndexById("defaultNeedByDate")).getValue());
                    //dock
                    var dockSelectionRule = mygrid.cells(i,mygrid.getColIndexById("dockSelectionRule")).getValue();
                    var copyShipToLocationId = mygrid.cells(copyGridIndex,mygrid.getColIndexById("shipToLocationId")).getValue();
                    if('FIXED' != dockSelectionRule && copyShipToLocationId != '') {
                        mygrid.cells(i,mygrid.getColIndexById("shipToLocationId")).setValue(copyShipToLocationId);
                        mygrid.cells(i,mygrid.getColIndexById("shipToLocationIdDesc")).setValue(mygrid.cells(copyGridIndex,mygrid.getColIndexById("shipToLocationIdDesc")).getValue());
                    }
                    //delivery point
                    var deliveryPointSelectionRule = mygrid.cells(i,mygrid.getColIndexById("deliveryPointSelectionRule")).getValue();
                    var copyDeliveryPointDesc = mygrid.cells(copyGridIndex,mygrid.getColIndexById("deliveryPoint")).getValue();
                    if('FIXED' != deliveryPointSelectionRule && copyDeliveryPointDesc != '') {
                        mygrid.cells(i,mygrid.getColIndexById("deliveryPoint")).setValue(mygrid.cells(copyGridIndex,mygrid.getColIndexById("deliveryPoint")).getValue());
                        mygrid.cells(i,mygrid.getColIndexById("deliveryPointDesc")).setValue(mygrid.cells(copyGridIndex,mygrid.getColIndexById("deliveryPointDesc")).getValue());
                    }
                }
            }
        } else
            alert(messagesData.nocopy);
} //end of method

function rightClick (rowId, cellId) {
    selectedRowId = rowId;
    // Show right-click menu
    var currentStatus = cellValue(rowId,"requestLineStatus");
    var menuItems = new Array();
    if (currentStatus != "Draft") {
        menuItems[menuItems.length] = 'text='+messagesData.currentLineApprovalDetail+';url=javascript:showApproverLineDetail();';
        //todo block this out for now tngo: Nov 19th
        //the reason is I need to talk with DSC/Nawaz about displaying the data
        // menuItems[menuItems.length] = 'text='+messagesData.allLinesApprovalDetail+';url=javascript:showApproverDetail();';
        if (approverRequired) {
            menuItems[menuItems.length] = 'text='+messagesData.mrTotalCostApprovalDetail+';url=javascript:extendedPriceClicked();';
        }
    }else {
        /*not needed at this time
        if ($v("allocateByChargeNumber") == 'true' && "Y" == $v("canEditMr") && cellValue(rowId,"holdAsCustomerOwnedDataField") != "Y" ) {
            menuItems[menuItems.length] = 'text='+messagesData.transferFromProgram+';url=javascript:showOverrideAllocateChargeNumber();';
        }else {
            menuItems[menuItems.length] = 'text=';   
        }
        */
    }
    
    if(homeCompanyLogin){
    	menuItems[menuItems.length] = "text="+messagesData.mrlineallocation+";url=javascript:showMrLineAllocationReport();";  
    }
    
    //put all together
    if (menuItems.length > 0) {
        replaceMenu('rightClickMenu',menuItems);
        toggleContextMenu('rightClickMenu');
    }
}  //end of method

function showOverrideAllocateChargeNumber() {
    var currentChargeType = cellValue(selectedRowId,"chargeType");
    var chargeLabel1 = "";
    var chargeLabel2 = "";
    var chargeLabel3 = "";
    var chargeLabel4 = "";
    var allocateByChargeId1 = "";
    var allocateByChargeId2 = "";
    var allocateByChargeId3 = "";
    var allocateByChargeId4 = "";
    for(var i = 0; i < prRulesColl.length; i++) {
        if (currentChargeType == prRulesColl[i].chargeType) {
            chargeLabel1 = encodeURIComponent(prRulesColl[i].chargeLabel1);
            chargeLabel2 = encodeURIComponent(prRulesColl[i].chargeLabel2);
            chargeLabel3 = encodeURIComponent(prRulesColl[i].chargeLabel3);
            chargeLabel4 = encodeURIComponent(prRulesColl[i].chargeLabel4);
            allocateByChargeId1 = prRulesColl[i].allocateByChargeId1;
            allocateByChargeId2 = prRulesColl[i].allocateByChargeId2;
            allocateByChargeId3 = prRulesColl[i].allocateByChargeId3;
            allocateByChargeId4 = prRulesColl[i].allocateByChargeId4;
            break;
        }
    }
    openWinGeneric("materialrequest.do?action=overrideAllocateChargeNumber&prNumber="+$("prNumber").value+"&lineItem="+cellValue(selectedRowId,"lineItem")+
                 "&chargeLabel1="+chargeLabel1+"&chargeLabel2="+chargeLabel2+"&chargeLabel3="+chargeLabel3+"&chargeLabel4="+chargeLabel4+
                 "&allocateByChargeId1="+allocateByChargeId1+"&allocateByChargeId2="+allocateByChargeId2+"&allocateByChargeId3="+allocateByChargeId3+
                 "&allocateByChargeId4="+allocateByChargeId4+"&allocateByChargeNumber1="+encodeURIComponent(cellValue(selectedRowId,"allocateByChargeNumber1"))+
                 "&allocateByChargeNumber2="+encodeURIComponent(cellValue(selectedRowId,"allocateByChargeNumber2"))+"&allocateByChargeNumber3="+encodeURIComponent(cellValue(selectedRowId,"allocateByChargeNumber3"))+
                 "&allocateByChargeNumber4="+encodeURIComponent(cellValue(selectedRowId,"allocateByChargeNumber4")),"overrideAllocateChargeNumber","400","200","yes","80","80");
}

function overrideAllocateChargeNumberData(chargeNumber1,chargeNumber2,chargeNumber3,chargeNumber4) {
    if (chargeNumber1.length > 0)
        mygrid.cells(selectedRowId,mygrid.getColIndexById("allocateByChargeNumber1")).setValue(chargeNumber1);
    if (chargeNumber2.length > 0)
        mygrid.cells(selectedRowId,mygrid.getColIndexById("allocateByChargeNumber2")).setValue(chargeNumber2);
    if (chargeNumber3.length > 0)
        mygrid.cells(selectedRowId,mygrid.getColIndexById("allocateByChargeNumber3")).setValue(chargeNumber3);
    if (chargeNumber4.length > 0)
        mygrid.cells(selectedRowId,mygrid.getColIndexById("allocateByChargeNumber4")).setValue(chargeNumber4);
}

function showApproverLineDetail() {
    //callToServer("approverslistdetail.do?action=getApproverDetail&prNumber="+$("prNumber").value+"&lineItem="+cellValue(selectedRowId,"lineItem"));
    showMrApprovalDetail($("prNumber").value,cellValue(selectedRowId,"lineItem"));
}

function showApproverDetail() {
 callToServer("approverslistdetail.do?action=getApproverDetail&prNumber="+$("prNumber").value+"&lineItem=");
}

// all same level, at least one item
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

function changeHoldAsCustomerOwned(rowId) {
    /*
    if(cellValue(rowId,"holdAsCustomerOwnedDataField") == "Y" && $v("viewType") != 'VIEW') {
        mygrid.cells(rowId,mygrid.getColIndexById("allocateByChargeNumber1")).setValue("");
        mygrid.cells(rowId,mygrid.getColIndexById("allocateByChargeNumber2")).setValue("");
        mygrid.cells(rowId,mygrid.getColIndexById("allocateByChargeNumber3")).setValue("");
        mygrid.cells(rowId,mygrid.getColIndexById("allocateByChargeNumber4")).setValue("");
    }
    */
    settingHoldOwnerSegment(rowId);
    settingHoldMfgLot(rowId);
}

function settingHoldMfgLot(rowId) {
    if(cellValue(rowId,"holdAsCustomerOwnedDataField") == "Y") {
        mygrid.cells(rowId,mygrid.getColIndexById("allocateByMfgLotPermission")).setValue("N");
        mygrid.cells(rowId,mygrid.getColIndexById("allocateByMfgLot")).setValue("");
    }else {
        mygrid.cells(rowId,mygrid.getColIndexById("allocateByMfgLotPermission")).setValue(cellValue(rowId,"lineAllocateByMfgLotPerm"));
        mygrid.cells(rowId,mygrid.getColIndexById("allocateByMfgLot")).setValue(cellValue(rowId,"allocateByMfgLot"));
    }
}

function settingHoldOwnerSegment(rowId) {
    if ($v("viewType") != 'VIEW') {
        if(cellValue(rowId,"holdAsCustomerOwnedDataField") == "Y") {
            if ($v("canSetMrOnHoldForProgram") == 'Y') {
                mygrid.cells(rowId,mygrid.getColIndexById("ownerSegmentIdPermission")).setValue("Y");
            }else {
                mygrid.cells(rowId,mygrid.getColIndexById("ownerSegmentIdPermission")).setValue("N");
            }
        }else {
            mygrid.cells(rowId,mygrid.getColIndexById("ownerSegmentIdPermission")).setValue("N");
        }
        mygrid.cells(rowId,mygrid.getColIndexById("ownerSegmentId")).setValue(cellValue(rowId,"ownerSegmentId"));
    }
}

function cancelCloseWin() {
	  dhxWins.window("showMessageDialog").setModal(false);	
	  dhxWins.window("showMessageDialog").hide();

}

//Get quantity from po-line table and then check the qty
function checkPOLineQty(rowId) {
	selectedRowId = rowId;

	var callArgs = new Object();
	callArgs.prNumber = $v("prNumber");
	callArgs.lineItem = cellValue(rowId,"lineItem").trim();

	j$.post("/tcmIS/supply/findbuyorderpolineqty.do", j$.param(callArgs), checkQty);  
}

function checkQty(data,status){
	$("cancelWarningMsg").innerHTML = '';
	if("approveQtyChange" == $v("action")) {
		if(data.trim() != 0) {
			$("cancelWarningMsg").innerHTML = messagesData.qtyinpolinetable.replace('{0}', data).replace('{1}',gridCellValue(mygrid,mygrid.getSelectedRowId(),'catPartNo'));
			$("cancelWarningMsg").style.color = 'red';
		}
		approveQtyChangeWithPOLineQtyQuantity();
	} else if("approveCancel" == $v("action")) {
		if(data.trim() != 0) {
			$("cancelWarningMsg").innerHTML = messagesData.qtyinpolinetable.replace('{0}', data).replace('{1}',gridCellValue(mygrid,mygrid.getSelectedRowId(),'catPartNo'));
			$("cancelWarningMsg").style.color = 'red';
		}
		approveCancelWithPOLineQtyQuantity();
	} else {
	    var poLineQuantity = data;
	    var totalQuantityIssued = cellValue(selectedRowId,"totalQuantityIssued");
	    
	    var qty = cellValue(selectedRowId,"qty");

	    if( qty < poLineQuantity*1 + totalQuantityIssued*1) {
			alert(messagesData.qtylessthanissuedandpo);
			$("qty"+selectedRowId).focus();
	    } 
	}
}


function approveCancel() {
	$("action").value = "approveCancel";
	checkPOLineQty(selectedRowId);
}
	
	
function approveCancelWithPOLineQtyQuantity() {
	var date = new Date();
	var today = date.getDate() + '-' + month_abbrev_Locale_Java[pageLocale][date.getMonth()] + '-' + date.getFullYear();
		
	$("cancelWarningMsg").innerHTML += messagesData.approvecancelconfirmmsg.replace('{0}',gridCellValue(mygrid,mygrid.getSelectedRowId(),'lineItem')).replace('{1}',gridCellValue(mygrid,mygrid.getSelectedRowId(),'catPartNo'));

	$("cancelRequestMessageText").value = 'Cancellation approved by ' + $v('firstName') + ' ' + $v('lastName') + ' on ' + today + '.\n\n';
	var inputDailogWin = document.getElementById("cancelRequestMessageText");
	inputDailogWin.style.display="";
	
	callingShowMessageDialogFrom = 'approveCancel';

	initializeDhxWins();
	inputWin = dhxWins.createWindow("showMessageDialog",0,0, 450, 350);
	inputWin.setText(messagesData.approvecancel);
	inputWin.clearIcon();  // hide icon
	inputWin.denyResize(); // deny resizing
	inputWin.denyPark();   // deny parking
	inputWin.attachObject("cancelRequestMessageDailogWin");
	inputWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
	inputWin.center();
	// setting window position as default x,y position doesn't seem to work, also hidding buttons.
	inputWin.setPosition(328, 131);
	inputWin.button("close").hide();
	inputWin.button("minmax1").hide();
	inputWin.button("park").hide();
	inputWin.setModal(true);
}

function submitApproveCancel() {
	mygrid.cells(selectedRowId,mygrid.getColIndexById("lineFlag")).setValue("Approve Cancel");
	var comment = cellValue(selectedRowId,"approveRejectComments");
	comment += '<br>' + "Approve cancel request comment: " + $("cancelRequestMessageText").value;
	mygrid.cells(selectedRowId,mygrid.getColIndexById("approveRejectComments")).setValue(comment);	
		
	saveCurrenData();
	//prepare grid for data sending
	mygrid.parentFormOnSubmit();
	document.genericForm.submit();
	showTransitWin("");
}

function rejectCancel() {
	$("cancelWarningMsg").innerHTML = messagesData.rejectcancelconfirmmsg.replace('{0}',gridCellValue(mygrid,mygrid.getSelectedRowId(),'lineItem')).replace('{1}',gridCellValue(mygrid,mygrid.getSelectedRowId(),'catPartNo'));
	var date = new Date();
	var today = date.getDate() + '-' + month_abbrev_Locale_Java[pageLocale][date.getMonth()] + '-' + date.getFullYear();
		
	$("cancelRequestMessageText").value = 'Cancellation rejected by ' + $v('firstName') + ' ' + $v('lastName') + ' on ' + today + '.\n\n';
	var inputDailogWin = document.getElementById("cancelRequestMessageText");
	inputDailogWin.style.display="";
	
	callingShowMessageDialogFrom = 'rejectCancel';

	initializeDhxWins();
	inputWin = dhxWins.createWindow("showMessageDialog",0,0, 450, 350);
	inputWin.setText(messagesData.rejectcancel);
	inputWin.clearIcon();  // hide icon
	inputWin.denyResize(); // deny resizing
	inputWin.denyPark();   // deny parking
	inputWin.attachObject("cancelRequestMessageDailogWin");
	inputWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
	inputWin.center();
	// setting window position as default x,y position doesn't seem to work, also hidding buttons.
	inputWin.setPosition(328, 131);
	inputWin.button("close").hide();
	inputWin.button("minmax1").hide();
	inputWin.button("park").hide();
	inputWin.setModal(true);
}

function submitRejectCancel() {
	mygrid.cells(selectedRowId,mygrid.getColIndexById("lineFlag")).setValue("Reject Cancel");
	var comment = cellValue(selectedRowId,"approveRejectComments");
	comment += '<br>' + "Reject cancel request comment: " + $("cancelRequestMessageText").value;
	mygrid.cells(selectedRowId,mygrid.getColIndexById("approveRejectComments")).setValue(comment);	
	
	saveCurrenData();
	$("action").value = "rejectCancel";
	//prepare grid for data sending
	mygrid.parentFormOnSubmit();
	document.genericForm.submit();
	showTransitWin("");
}

function approveQtyChangeWithPOLineQtyQuantity() {	
		var date = new Date();
		var today = date.getDate() + '-' + month_abbrev_Locale_Java[pageLocale][date.getMonth()] + '-' + date.getFullYear();
		
		$("cancelWarningMsg").innerHTML += messagesData.approveqtychangeconfirmmsg.replace('{0}',gridCellValue(mygrid,mygrid.getSelectedRowId(),'lineItem')).replace('{1}',gridCellValue(mygrid,mygrid.getSelectedRowId(),'catPartNo'));
			
		$("cancelRequestMessageText").value = 'Qty change approved by ' + $v('firstName') + ' ' + $v('lastName') + ' on ' + today + '.\n\n';
		var inputDailogWin = document.getElementById("cancelRequestMessageText");
		inputDailogWin.style.display="";
		
		callingShowMessageDialogFrom = 'approveQtyChange';

		initializeDhxWins();
		inputWin = dhxWins.createWindow("showMessageDialog",0,0, 450, 350);
		inputWin.setText(messagesData.approveqtychange);
		inputWin.clearIcon();  // hide icon
		inputWin.denyResize(); // deny resizing
		inputWin.denyPark();   // deny parking
		inputWin.attachObject("cancelRequestMessageDailogWin");
		inputWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
		inputWin.center();
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		inputWin.setPosition(328, 131);
		inputWin.button("close").hide();
		inputWin.button("minmax1").hide();
		inputWin.button("park").hide();
		inputWin.setModal(true);
}


function approveQtyChange() {
	$("action").value = "approveQtyChange";
	checkPOLineQty(selectedRowId);
}

function submitApproveQtyChange() {
	mygrid.cells(selectedRowId,mygrid.getColIndexById("lineFlag")).setValue("Approve Qty Change");
	var comment = cellValue(selectedRowId,"notes");
	comment += '<br>' + "Approve Qty Change note: " + $("cancelRequestMessageText").value;
	mygrid.cells(selectedRowId,mygrid.getColIndexById("notes")).setValue(comment);	
	
	saveCurrenData();
	$("action").value = "approveQtyChange";
	//prepare grid for data sending
	mygrid.parentFormOnSubmit();
	document.genericForm.submit();
	showTransitWin("");  
}

function showMrLineAllocationReport()
{
	var companyId  = $v("companyId");   	
	var mrNumber  = $v('prNumber');     	
	var lineItem  =  cellValue(selectedRowId,"lineItem");
	if (mrNumber!= null && lineItem != null && mrNumber!= 0 )
	{
		var loc = "mrallocationreportmain.do?companyId="+companyId+"&mrNumber="+mrNumber+"&lineItem="+lineItem+"";
		openWinGeneric(loc,"showMrAllocationReport22","800","550","yes","80","80","no");
	}
}

function setHiddenColumn(rowId, colId){
	var flagValue = cellValue(rowId,colId);
	var col = colId.substring(0, colId.length-4);
	if(flagValue == true || flagValue == "true")
		mygrid.cells(rowId,mygrid.getColIndexById(col)).setValue("y");
	else
		mygrid.cells(rowId,mygrid.getColIndexById(col)).setValue("n");
}

function logLineItemUpdates(){
	for(var i = 1; i < prAccountColl.length; i++) {
		var origQty = mygrid.cells(i,mygrid.getColIndexById("origQty")).getValue();	
		var qty = mygrid.cells(i,mygrid.getColIndexById("qty")).getValue(); 
		
		var changeComment = '';
		
		if(origQty != qty){
			changeComment += 'qty from ' + origQty + ' to ' + qty;
		}
		
		if(homeCompanyLogin) {
			var origRelaxShelfLife = mygrid.cells(i,mygrid.getColIndexById("origRelaxShelfLifeFlag")).getValue(); 
			var relaxShelfLife = mygrid.cells(i,mygrid.getColIndexById("relaxShelfLifeFlag")).getValue(); 
			var origDropShipOverride = mygrid.cells(i,mygrid.getColIndexById("origDropShipOverride")).getValue(); 
			var dropShipOverride = mygrid.cells(i,mygrid.getColIndexById("dropShipOverride")).getValue(); 
			
			if(origRelaxShelfLife != relaxShelfLife ){
				if(changeComment != '')
					changeComment += ', ';
				
				changeComment += 'relax shelf life to ' + relaxShelfLife;
			}
			if(origDropShipOverride != dropShipOverride){
				if(changeComment != '')
					changeComment += ', ';
				
				changeComment += 'drop ship override to ' + dropShipOverride;
			}
		}	
		
		if(changeComment != ''){
			var notes = mygrid.cells(i,mygrid.getColIndexById("notes")).getValue();
			var date = new Date();
			var today = date.getDay() + '-' + month_abbrev_Locale_Java[pageLocale][date.getMonth()] + '-' + date.getYear();
			
			changeComment = $v('firstName') + ' ' + $v('lastName') + ' updated ' + changeComment + ' on ' + today + '.';
			notes += '\n\n' + changeComment;

			mygrid.cells(i,mygrid.getColIndexById("notes")).setValue(notes);
		}	
	}
}

/**
 * Order Name is valid if:
 * 	- it's empty string
 * 	- it's the same as the first suggestion
 *  - it's different from the first suggestion, but the same as the original value (since the lookup only looks for existence and not whether the found record is that of this prNumber)
 * @param firstOption
 * @returns
 */
function validateOrderName(firstOption) {
	if (!isWhitespace($v('orderName')) && firstOption != $v('orderName') && $v('orderName') != $v("originalOrderName")) {
		$("orderName").className = "inputBox red";
		isAvailable = false;
	} else {
		$("orderName").className = "inputBox";
		isAvailable = true;
	}
}