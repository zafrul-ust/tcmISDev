var beangrid;
var homeCompanyLogin;
var resizeGridWithWindow = true;
var dhxWins;
var isAvailable;
var reorderWin;

function resultOnLoad()
{
	totalLines = document.getElementById("totalLines").value;

	if (totalLines > 0)
	{
		document.getElementById("prOrderTrackBean").style["display"] = "";

		initializeGrid();  
	}  
	else
	{
		document.getElementById("prOrderTrackBean").style["display"] = "none";   
	}

	displayGridSearchDuration();

	/*It is important to call this after all the divs have been turned on or off.*/
	setResultFrameSize();
}

function initializeGrid(){
	beangrid = new dhtmlXGridObject('prOrderTrackBean');

	initGridWithConfig(beangrid,config,false,true);
	if( typeof( jsonMainData ) != 'undefined' ) {
		beangrid.parse(jsonMainData,"json");

	}
    beangrid.attachEvent("onRightClick",onRightclick);
}


function onRightclick(rowId,cellInd){
    var orderType = cellValue(beangrid.getSelectedRowId(),"orderType");
	var lineStatus = cellValue(beangrid.getSelectedRowId(),"requestLineStatus");		  
	var showAllocation = false;
	var showSchedule = false;
	if (lineStatus == "In Progress" || lineStatus == "Partial Del." || lineStatus == "Open" || lineStatus == "Pending Cancellation" ||
		 lineStatus == "Delivered") {
		showAllocation = true;
	}
	
	if (orderType == "SCH"){
		showSchedule = true;
	}

	var aitems = new Array();
	if (orderType == 'EVAL') {
		if (showAllocation) {
			aitems[aitems.length] = "text="+messagesData.mrlineallocation+";url=javascript:showMrLineAllocationReport();";
		}
		if (lineStatus == 'Draft Eval') {
			aitems[aitems.length] = "text="+messagesData.vieweval+";url=javascript:showEvalDetail();";
		}else {
			aitems[aitems.length] = "text="+messagesData.approvaldetail+";url=javascript:showEvalApprovalDetail();";
			aitems[aitems.length] = "text="+messagesData.vieweval+";url=javascript:showEvalDetail();";
		}
	}else {
		//mr allocation
		if (showAllocation) {
			aitems[aitems.length] = "text="+messagesData.mrallocation+";url=javascript:showMrAllocationReport();";
			aitems[aitems.length] = "text="+messagesData.mrlineallocation+";url=javascript:showMrLineAllocationReport();";
		}
		//schedule
		if (showSchedule) {
			aitems[aitems.length] = "text="+messagesData.mrlineschedule+";url=javascript:showDeliverySchedule();";
		}
		//view MR
		if ($("intcmIsApplication").value == 'Y') {
			aitems[aitems.length] = "text="+messagesData.viewmr+";url=javascript:viewMR();";
		}

		//approval detail
		if (lineStatus != "Draft") {
			aitems[aitems.length] = "text="+messagesData.approvaldetail+";url=javascript:showMrApprovalDetail();";
		}
		
		if (cellValue(beangrid.getSelectedRowId(), "reorderMrPermission") == 'Y') {
			aitems[aitems.length] = "text="+messagesData.editOrderName+";url=javascript:showEditOrderName();";
			if (lineStatus != "Draft")
				aitems[aitems.length] = "text="+messagesData.reorderFromMr+";url=javascript:showOrder();";
		}
	} //end of if MR
	replaceMenu('orderTrackingMenu',aitems);
	toggleContextMenu('orderTrackingMenu');

} //end of method

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

function doNothing() {
}

function showMrLineAllocationReport()
{
	var companyId  = cellValue(beangrid.getSelectedRowId(),"companyId");    	
	var mrNumber  =  cellValue(beangrid.getSelectedRowId(),"prNumber");    	
	var lineItem  =  cellValue(beangrid.getSelectedRowId(),"lineItem");
	if (mrNumber!= null && lineItem != null && mrNumber!= 0 )
	{
		var loc = "mrallocationreportmain.do?companyId="+companyId+"&mrNumber="+mrNumber+"&lineItem="+lineItem+"";
		openWinGeneric(loc,"showMrAllocationReport22","800","550","yes","80","80","no")
	}

}

function showMrAllocationReport()
{
	var companyId  = cellValue(beangrid.getSelectedRowId(),"companyId");    	
	var mrNumber  =  cellValue(beangrid.getSelectedRowId(),"prNumber");    	
	var lineItem  =  cellValue(beangrid.getSelectedRowId(),"lineItem"); 

	if ( mrNumber != null &&  mrNumber != 0)
	{
		var loc = "mrallocationreportmain.do?companyId="+companyId+"&mrNumber="+mrNumber+"&lineItem=";
		openWinGeneric(loc,"showMrAllocationReport22","800","550","yes","80","80","no")
	} 
}

function showDeliverySchedule()
{
	var orderType  = cellValue(beangrid.getSelectedRowId(),"orderType");	  
	if (orderType == "SCH")
	{
		var companyId =  cellValue(beangrid.getSelectedRowId(),"companyId");    	
		var mrNumber  =  cellValue(beangrid.getSelectedRowId(),"prNumber");    	
		var lineItem  =  cellValue(beangrid.getSelectedRowId(),"lineItem"); 
		var loc = "deliveryschedulemain.do?companyId="+companyId+"&prNumber="+mrNumber+"&lineItem="+lineItem+"";
		openWinGeneric(loc,"showDeliverySchedule22","850","550","yes","100","100","no")
	}
} 
 
function viewMR(mrNumber)
{
	if (isWhitespace(mrNumber))
		mrNumber = cellValue(beangrid.getSelectedRowId(), "prNumber");

	if (mrNumber != null && mrNumber != 0) {
		var loc = "materialrequest.do?action=int&prNumber=" + mrNumber;
		try {
			parent.parent.openIFrame("materialrequest" + mrNumber, loc, ""
					+ messagesData.materialrequest + " " + mrNumber, "", "N");
		} catch (ex) {
			openWinGeneric(loc, "materialrequest" + mrNumber, "800", "600",
					"yes", "50", "50");
		}
	}
}

function showEvalApprovalDetail()
{
	var orderType  = cellValue(beangrid.getSelectedRowId(),"orderType");
	if (orderType == "EVAL")
	{
  		var requestId = cellValue(beangrid.getSelectedRowId(),"requestId");
  		if(requestId.value!='')  {
	 		parent.showEvalApprovalDetail(requestId);
		}
	}
}

function showEvalDetail() {
	var orderType  = cellValue(beangrid.getSelectedRowId(),"orderType");
	if (orderType == "EVAL")
	{
		var requestId  =  cellValue(beangrid.getSelectedRowId(),"requestId");
		if ( requestId != null &&  requestId != 0) {
		  var loc = "engeval.do?uAction=view&requestId="+requestId;
		  try {
			 parent.parent.openIFrame("engeval",loc,""+messagesData.engineeringevaluation+"","","N");
		  }catch (ex) {
			 openWinGeneric(loc,"engeval"+"","800","600","yes","50","50");
		  }
		}
	}
}

function showMrApprovalDetail()
{
	var orderType  = cellValue(beangrid.getSelectedRowId(),"orderType");
	if (orderType != "EVAL")
	{
  		var prNumber = cellValue(beangrid.getSelectedRowId(),"prNumber");
		if(prNumber.value!='')  {
	 		parent.mrApprovalDetail(cellValue(beangrid.getSelectedRowId(),"companyId"),prNumber,cellValue(beangrid.getSelectedRowId(),"lineItem"));
  		}
	}
}

//this function will intialize dhtmlXWindow if it's null
function initializeDhxWins() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function showEditOrderName() {
	$("orderNamePopupDiv").style.display = "";
	$("orderName").value = cellValue(beangrid.getSelectedRowId(), "orderName");

	initializeDhxWins();
	var orderNamePopupWin;
	if (!dhxWins.window("orderNamePopupWin")) {
		// create window first time
		orderNamePopupWin = dhxWins.createWindow("orderNamePopupWin", 0, 0, 315, 100);
		orderNamePopupWin.setText(messagesData.editOrderName);
		orderNamePopupWin.clearIcon(); // hide icon
		orderNamePopupWin.denyResize(); // deny resizing
		orderNamePopupWin.denyPark(); // deny parking
		orderNamePopupWin.attachObject("orderNamePopupDiv");
		orderNamePopupWin.attachEvent("onClose", function(orderNamePopupWin) {
			closeEditOrderName();
		});
		orderNamePopupWin.setModal(true);
		orderNamePopupWin.center();
		
		j$("").ready(function() {
			function log(event, data, formatted) {
				validateOrderName(formatted.replace(/(?:\r\n|\r|\n)/g, ""));
			}
			
			j$("#orderName").autocomplete("lookupordername.do",{
				extraParams : {
					companyId : function() {
						return cellValue(beangrid.getSelectedRowId(), "companyId");
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
					if ($v('orderName') == cellValue(beangrid.getSelectedRowId(), "orderName"))
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
	} else {
		// just show
		orderNamePopupWin = dhxWins.window("orderNamePopupWin");
		orderNamePopupWin.show();
		orderNamePopupWin.center();
		orderNamePopupWin.setModal(true);
	}
}

function closeEditOrderName() {
	if (dhxWins) {
		var orderNamePopupWin = dhxWins.window("orderNamePopupWin");
		$("orderNamePopupDiv").style.display = "none";
		orderNamePopupWin.setModal(false);
		orderNamePopupWin.hide();
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
	if (!isWhitespace($v('orderName')) && firstOption != $v('orderName') && $v('orderName') != cellValue(beangrid.getSelectedRowId(), "orderName")) {
		$("orderName").className = "inputBox red";
		isAvailable = false;
	} else {
		$("orderName").className = "inputBox";
		isAvailable = true;
	}
}

function editOrderName() {
	if (typeof isAvailable == "undefined" || $v('orderName') == cellValue(beangrid.getSelectedRowId(), "orderName"))
		editOrderNameComplete('');
	else if (isAvailable) {
		if ($v("orderName").length > 30)
			alert(messagesData.maxLength.replace(/\{0\}/g, messagesData.orderName).replace(/\{1\}/g, 30));
		else
			j$.ajax({
				type : "POST",
				url : "ordertrackingmain.do",
				data : {
					action : "editOrderName",
					companyId : cellValue(beangrid.getSelectedRowId(), "companyId"),
					prNumber : cellValue(beangrid.getSelectedRowId(), "prNumber"),
					orderName : $v("orderName")
				},
				success : editOrderNameComplete
			});
	} else
		alert(messagesData.xExist.replace(/\{0\}/g, messagesData.orderName).replace("{1}", $v("orderName")));
}

function editOrderNameComplete(msg) {
	if (isWhitespace(msg)) {
		closeEditOrderName();
		setCellValue(beangrid.getSelectedRowId(), "orderName", $v("orderName"));
	} else
		alert(msg);
}

function showOrder() {
	var prNumber = cellValue(beangrid.getSelectedRowId(), "prNumber");
	if(!isWhitespace(prNumber))
		reorderWin = openWinGeneric("reordermrmain.do?prNumber=" + prNumber, "reorderMR" + prNumber, "800", "600", "yes", "50", "50");
}

function viewReorderedMR(prNumber) {
	viewMR(prNumber);
	if (reorderWin)
		reorderWin.close();
}