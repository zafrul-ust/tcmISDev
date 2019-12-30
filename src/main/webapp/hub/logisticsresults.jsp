<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis"%>


<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="expires" content="-1" />
<link rel="shortcut icon"
	href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp"%>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<!-- Add any other stylesheets you need for the page here -->

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<%--NEW - grid resize--%>
<script type="text/javascript"
	src="/js/common/grid/resultiframegridresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp"%>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
<script type="text/javascript" src="/js/calendar/calendarval.js"></script>

<!-- Add any other javascript you need for the page here -->

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--NEW - dhtmlX grid files--%>
<script type="text/javascript"
	src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript"
	src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript"
	src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript"
	src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript"
	src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript"
	src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srndRowSpan.js"></script>
<script type="text/javascript"
	src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript"
	src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript"
	src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
<script type="text/javascript"
	src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript"
	src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<script type="text/javascript"
	src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--This file has our custom sorting and other utility methos for the grid.--%>
<script type="text/javascript"
	src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/js/hub/bindata.js"></script>

<c:set var="module">
	<tcmis:module />
</c:set>

<title><fmt:message key="label.logistics" /></title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:'<fmt:message key="label.alert"/>',
and:'<fmt:message key="label.and"/>',
recordFound:'<fmt:message key="label.recordFound"/>',
searchDuration:'<fmt:message key="label.searchDuration"/>',
minutes:'<fmt:message key="label.minutes"/>',
seconds:'<fmt:message key="label.seconds"/>',
validvalues:'<fmt:message key="label.validvalues"/>',
submitOnlyOnce:'<fmt:message key="label.submitOnlyOnce"/>',
itemInteger:'<fmt:message key="error.item.integer"/>',
shipinfo:'<fmt:message key="label.shipinfo"/>',
startmarstest:"<fmt:message key="label.startmarstest"/>",
showMarsDetail:"<fmt:message key="label.showmarsdetail"/>",
startMarsTestForRecert:"<fmt:message key="label.startmarstestforrecert"/>",
noCompletedIncomingTest:"<fmt:message key="label.nocompletedincomingtest"/>",
marsDetail:"<fmt:message key="label.marsdetail"/>",
createnewbin:"<fmt:message key="label.createnewbin"/>",
changeitem:"<fmt:message key="label.changeitem"/>",
receivingQcCheckList:"<fmt:message key="receivingQcCheckList"/>",
printGHSlabels:"<fmt:message key="label.printGHSlabels"/>",
dupline:'Add CPP',// use common resource when terms finalized...'<fmt:message key="receiving.label.duplicateline"/>'};
addShippingSample:'<fmt:message key="label.addshippingsample"/>',
printrtklabel:"<fmt:message key="label.printrtklabel"/>"};

var disabledPoLink = false;
<tcmis:featureReleased feature="DisabledPOLink" scope="ALL" companyId="${personnelBean.companyId}">
	disabledPoLink = true;
</tcmis:featureReleased>

<c:set var="showUnitCost" value="Y"/>
<tcmis:featureReleased feature="DisabledUnitCost" scope="ALL"  companyId="${personnelBean.companyId}">
	<c:set var="showUnitCost" value="N"/>
</tcmis:featureReleased>

function refreshSearch() {
	try {
	getSearchFrame().submitSearchForm();
	}catch(e){}
}
// for change item.
function submitSearchForm() {
	refreshSearch();
}

with(milonic=new menuname("dropdown")){
    top="offset=2"
    style = contextWideStyle;
    margin=3;
    aI('text=<fmt:message key="label.shipinfo"/>;url=javascript:enterdotinfo();');
    aI('text=<fmt:message key="label.specs"/>;url=javascript:receiptSpecs();');
    aI('text=<fmt:message key="prevtransactions.title"/>;url=javascript:showPreviousReceiptTransactions();');
//  don't know to use lotStatus or oldLotStatus
//  var status = cellValue(selectedRowId,'oldLotStatus');
//	if ( cellValue(selectedRowId,"quantity") != "" && cellValue(selectedRowId,'qcDate') != "" &&
//		 status == "Write Off Requested") || status == "Incoming"))
    aI('text=<fmt:message key="label.splitreceipt"/>;url=javascript:splitQty();');
//  if( cellValue(selectedRowId,"radianPo") != "" )
    disabledPoLink ? '' : aI('text=<fmt:message key="purchaseOrder"/>;url=javascript:purchaseOrder();');
//  if( cellValue(selectedRowId,"permission") == "Y" )
    aI('text=<fmt:message key="writeonrequest.title"/>;url=javascript:writeOnRequest();');
//    if(  cellValue(selectedRowId,"polchemIg") == 'Y' && cellValue(selectedRowId,"doNumberRequired") == 'N'} ) {
//	  	if(  cellValue(selectedRowId,"unitLabelPrinted") == 'N' )
//    		aI('text=openerunitLabelCatPartNo;url=javascript:unitLabelPartNumber();');
//		else
//    		aI('text=unsetunitLabelPrinted;url=javascript:unitLabelPartNumber();');
//}
}

drawMenus();

function unsetunitLabelPrinted() {
	cell(selectedRowId,"unitLabelPrinted").setValue("N");
}
// Larry Note: only come here when it's 'N'
function unitLabelPartNumber() {
		cell(selectedRowId,"unitLabelPrinted").setValue("Y");
		var itemId = cellValue(selectedRowId,"itemId");
		var branchPlant = cellValue(selectedRowId,"hub");//$v('hub');
		var inventoryGroup = cellValue(selectedRowId,"inventoryGroup");
		loc = "unitlabelpartnumber.do?rowNumber=" + selectedRowId;
		loc = loc + "&itemId=" + itemId;
		loc = loc + "&hub=" + branchPlant;
		loc = loc + "&inventoryGroup=" + inventoryGroup;
		
		if ($v("personnelCompanyId") == 'Radian') 
			  loc = "/tcmIS/hub/" + loc;
		
		openWinGeneric(loc, "terminal_root_cause", "500", "300", "no");
}

var selectedRowId = null;
function showProjectDocuments() {
	receiptId = cellValue(selectedRowId,"receiptId");
	inventoryGroup = cellValue(selectedRowId,"inventoryGroup");
	var loc = "receiptdocuments.do?receiptId=" + receiptId + "&showDocuments=Yes&inventoryGroup=" + inventoryGroup + "";
	
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;
	
	openWinGeneric(loc, "showAllProjectDocuments", "450", "300", "no", "80", "80");
}

function addnewBin()
{
  var newbinURL = "/tcmIS/Hub/AddNewBin?";
  hubid = cellValue(selectedRowId,"hub");
  newbinURL = newbinURL + "HubName=" + hubid;
  openWinGeneric(newbinURL,"add_newbin","400","200","Yes")
}

function enterdotinfo() {
	var itemId = cellValue(selectedRowId,"itemId");
	var loc = "shippinginfo.do?uAction=search&itemId=" + itemId;
	
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;

	try {
		parent.parent.openIFrame("ShippingInfo"+itemId,loc,""+messagesData.shipinfo+" "+itemId,"","N");
	} catch(ex) {
//		var loc = "/tcmIS/Hub/dotshipname?item_id="+cellValue(selectedRowId,"itemId");
		openWinGeneric(loc, '<fmt:message key="label.dotinfo"/>', "1024", "500", "yes", "50", "50");
	}
}

function getReceiptnotes() {
	// var loc =
	// "/tcmIS/Hub/Logistics?session=Active&UserAction=addReceiptNotes";
	receiptId = cellValue(selectedRowId,"receiptId");
	var loc = "updatereceiptnotes.do";
	
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;

	if (receiptId.length > 0) {
		loc = loc + "?receiptId=" + receiptId;
		openWinGeneric(loc, "ReceiptNotes", "550", "250", "yes")
	}
}

function backGroundSetBins(rowid,itemId,value,text) {
	try {
	var totalRow = $v("totalLines");
	for(i = 1; i<= totalRow; i++ )  {
		   row_itemId = cellValue(i,"itemId");
		   if( i != rowid && itemId != row_itemId ) {
			   obj = document.getElementById("bin"+rowid);
			   var index = obj.length;
			   obj.options[index]=new Option(text,value);
		   }
	}
	}catch(ex){};
}

function getBin(value,text,rowid) {
	   obj = document.getElementById("bin"+rowid);
	   var index = obj.length;
	   obj.options[index]=new Option(text,value);
	   obj.options[index].selected = true;
	   itemId = cellValue(rowid,"itemId");
	   setTimeout("backGroundSetBins(" + rowid + ",'"+itemId+"','"+value+"','"+text+"')", 100);
}

function checkaddbins(){
	{  //$v('hub') -> cellValue(selectedRowId,"hub")
		var loc = "showhubbin.do?callbackparam="+selectedRowId+"&branchPlant=" + cellValue(selectedRowId,"hub") + "&userAction=showBins";
		
		if ($v("personnelCompanyId") == 'Radian') 
			  loc = "/tcmIS/hub/" + loc;
		
		var winname = null;
		try {
			winname = openWinGeneric(loc, "showVvHubBins", "300", "150", "no", "80", "80");
			children[children.length] = winname;
			} catch (ex) {
//			openWinGeneric(loc, "showVvHubBins", "300", "150", "no", "80", "80");
		}
	}
}


function receiptSpecs() {
	var loc = "receiptspec.do?receiptId="+cellValue(selectedRowId,"receiptId");
	
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/distribution/" + loc;
	
	openWinGeneric(loc, "<fmt:message key="label.specs"/>", "1024", "500", "yes", "50", "50");
}

function showPreviousReceiptTransactions() {
	var loc = "previoustransactions.do?sortBy=receiptId&submitSearch=yes&receiptId="+cellValue(selectedRowId,"receiptId")+
			  "&branchPlant="+cellValue(selectedRowId,"hub"); //2101;
			  
  	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;	
  	
	openWinGeneric(loc, "Previous_Transactions", "1024", "500", "yes", "50", "50");
}

// TODO: don't show split choice or show error.
//if (quantityfromhas > 0 && qcDate.trim().length() > 0 && !(status.equalsIgnoreCase("Write Off Requested") || status.equalsIgnoreCase("Incoming"))) {
function splitQty(receiptid, Hub, Qtyava, opsEntityId,netPendingAdj) {
	loc = "splitreceipt.do?receiptId=";
	loc = loc + cellValue(selectedRowId,"receiptId");
	loc = loc + "&hub=" + cellValue(selectedRowId,"hub");
//	loc = loc + "&hub=" + $v('hub');//cellValue(selectedRowId,"receiptId");
//	loc = loc + "&opsEntityId=" + cellValue(selectedRowId,"opsEntityId");
	loc = loc + "&quantity=" + cellValue(selectedRowId,"quantity");
	loc = loc + "&qualityControlItem=" + cellValue(selectedRowId,"qualityControlItem");
    loc = loc + "&netPendingAdj=" + cellValue(selectedRowId,"netPendingAdj");
    loc = loc + "&lotStatus=" + cellValue(selectedRowId,"lotStatus");
    loc = loc + "&itemId=" + cellValue(selectedRowId,"itemId");
/*
    if (cellValue(selectedRowId,"netPendingAdj") != "0")
    {
        loc = loc + "&netPendingAdj=" + cellValue(selectedRowId,"netPendingAdj");
    }
	alert(  cellValue(selectedRowId,"receiptId")+":"+cellValue(selectedRowId,"quantity")+":"+ $v('hub')+":"+ cellValue(selectedRowId,"opsEntityId")+":"+cellValue(selectedRowId,"quantity")+":"+cellValue(selectedRowId,"netPendingAdj"));
	*/
    
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;	
	
	openWinGeneric(loc, "SplitReceipt", "400", "300", "no");
}
/*
function splitQty(receiptid, Hub, Qtyava, opsEntityId,netPendingAdj) {
	loc = "/tcmIS/Hub/splityqty?session=Active&UserAction=splitqty&receiptid=";
	loc = loc + cellValue(selectedRowId,"receiptId");
	loc = loc + "&HubNo=" + $v('hub');//cellValue(selectedRowId,"receiptId");
	loc = loc + "&opsEntityId=" + cellValue(selectedRowId,"opsEntityId");
	loc = loc + "&QtyAvailable=" + cellValue(selectedRowId,"quantity");
    if (cellValue(selectedRowId,"netPendingAdj") != "0")
    {
        loc = loc + "&netPendingAdj=" + cellValue(selectedRowId,"netPendingAdj");
    }
	alert(  cellValue(selectedRowId,"receiptId")+":"+cellValue(selectedRowId,"quantity")+":"+ $v('hub')+":"+ cellValue(selectedRowId,"opsEntityId")+":"+cellValue(selectedRowId,"quantity")+":"+cellValue(selectedRowId,"netPendingAdj"));
    openWinGeneric(loc, "Previous_Transactions", "350", "300", "no");
}

*/
function purchaseOrder() {
	var loc = "/tcmIS/supply/purchaseorder.do?po="+cellValue(selectedRowId,"radianPo")+
			  "&Action=searchlike&subUserAction=po";
	openWinGeneric(loc, 'Purchase_Order', "1024", "500", "yes", "50", "50");
}

function writeOnRequest() {
	loc = "writeonrequest.do?receiptId="+cellValue(selectedRowId,"receiptId");
	
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;	
	
//	loc = loc + receiptid;
	openWinGeneric(loc, "writeOnRequest", "400", "300", "no");
}

function checkNoOfLabels(rowId, columnId) {
	v = cellValue( rowId, "noOfLabels" );
	if (!(isInteger(v)) || v * 1 == 0)
		alert('<fmt:message key="labels.label.validquantity"/>');
}

function update() {
	/*
	  if( window['validateForm'] && !validateForm() ) return false;
	  if( !act ) act = 'uAction';
	  if( !val ) val = 'update';
  $(act).value = val;
  if( window['showPleaseWait'] )
	  showPleaseWait();
  else
	  parent.showPleaseWait();
	*/
	$('printKitLabels').value = parent.$v('printKitLabels');
	$('paperSize').value = parent.$v('paperSize');
	$('uAction').value = 'update';
	parent.showPleaseWait();
	haasGrid.parentFormOnSubmit(); //prepare grid for data sending
	document.genericForm.submit();
}

//all same level, at least one item
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

var lotStatusDefault = [
    <c:forEach var="bean" items="${lotStatusColl}" varStatus="status">
       <c:set var="jspLabelEsc" value=""/>
       <c:if test="${fn:length(status.current.jspLabel) > 0}"><c:set var="jspLabel">${status.current.jspLabel}</c:set>
       <c:set var="jspLabelEsc"><fmt:message key="${jspLabel}"/></c:set>
       </c:if>
       <c:if test="${ status.index !=0 }">,</c:if>
       {
            text:'<tcmis:jsReplace value="${jspLabelEsc}" processMultiLines="false" />',
            value:'${bean.lotStatus}'
       }
    </c:forEach>
];

var lotStatusIncoming = [
{
	text:'<fmt:message key='label.incoming'/>',value:'Incoming'
}
];
var lotStatus = new Array();
	<c:forEach var="logisticsBean" items="${pageNameViewBeanCollection}" varStatus="status">
	lotStatus[${status.index+1}] =<c:if test="${ logisticsBean.lotStatus ne 'Incoming' }"> lotStatusDefault</c:if><c:if test="${ logisticsBean.lotStatus eq 'Incoming' }"> lotStatusIncoming</c:if>;
	</c:forEach>

var pickableArr = [
<c:forEach var="bean" items="${lotStatusColl}" varStatus="status">
<c:if test="${ bean.pickable eq 'Y' }">
	<c:if test="${ status.index !=0 }">,</c:if>
	'${bean.lotStatus}'
</c:if>
</c:forEach>
];

var qcInvArr = [
<c:forEach var="bean" items="${qcInv}" varStatus="status">
	<c:if test="${ status.index !=0 }">,</c:if>
	'${bean[0]}'
</c:forEach>
];

function checkPickable(rowId,colownId) {
    //the reason for this is that a part can be incomoing lab test required and Quality Control Item
    var tmpVal = true;
    if (cellValue(rowId,"incomingTesting") == 'Y') {
        tmpVal = lotStatusChangedOk(rowId);
    }

    if (tmpVal) {
        var lots = cellValue(rowId,"lotStatus");
        if( cellValue(rowId,"qualityControlItem") == 'Y' ) {
            for(i=0;i<pickableArr.length;i++) {
                if( pickableArr[i] == lots ) {
                    var invG = cellValue(rowId,"inventoryGroup");
                    for(j=0;j<qcInvArr.length;j++) {
                        if( qcInvArr[j] == invG ) {
                            setCellValue(rowId,beanGrid.getColIndexById("certupdate"),"Yes");
                            return;
                        }
                    }
                    alert('<fmt:message key="label.needqcperm"/>'.replace('{0}',cellValue(rowId,"receiptId")));
                    setCellValue(rowId,colownId,cellValue(rowId,"oldLotStatus"));
                }
            }
        }else {
            if (lots == "Customer Purchase" || lots == "Write Off Requested" || lots == "3PL Purchase" ) {
               loc = "terminalstatusrootcause.do?lotStatus=";
               loc = loc + lots + "&rowNumber=" + rowId;
               
               if ($v("personnelCompanyId") == 'Radian') 
         		  loc = "/tcmIS/hub/" + loc;	
               
               openWinGeneric(loc,"terminal_root_cause","500","300","no");
            }else {
                setRootCauseCallback(rowId,"","","");
            }
        }
    }
} //end of method

function setRootCauseCallback(	rowId, rootcause,	rootcausecompany, rootcausenotes )
{
	cell(rowId,"lotStatusRootCause").setValue(rootcause);
	cell(rowId,"responsibleCompanyId").setValue(rootcausecompany);
	cell(rowId,"lotStatusRootCauseNotes").setValue(rootcausenotes);
}

function cancelRootCauseCallback(rowId,prevLotStatus) {
	if( !prevLotStatus )
		prevLotStatus = cellValue(rowId,"oldLotStatus");
	setCellValue(rowId,"lotStatus",prevLotStatus);
}

function changeMlItem() {
	var checkedCount = 0;
	var selectedItem = cellValue(selectedRowId,'itemId');

	if (checkMlItemInput(selectedItem)) {
//		if (selectedItem.value.trim().length > 0)
		{
			var receiptsList = "";
			var totalRecords = $v("totalLines")*1;
			for (j = 1; j <= totalRecords ; j++) {
				var receiptId = "";
				receiptId = cellValue(j,"receiptId");
				rowCheck = cellValue(j,"ok");
				itemType = cellValue(j,"itemType");
				newChemRequestId = cellValue(j,"newChemRequestId");

				if (rowCheck && itemType == "ML" && !newChemRequestId ) {
					if (checkedCount > 0) {
						receiptsList += ','
					}
					receiptsList += receiptId;
					checkedCount++
				}
			}

			// var labelReceipts =
			// document.getElementById("labelReceipts").value;

			receiptsList = receiptsList.replace(/,/gi, "%2c");
			if (receiptsList.trim().length > 0) {
				var loc = "receivingitemsearchmain.do?receipts=" + receiptsList + "";
				var hubNumber = cellValue(selectedRowId,"hub");//$v('hub'); // hub not used??? cellValue(selectedRowId,"hub")
				loc = loc + "&hubNumber=" + hubNumber;
				loc = loc + "&listItemId=" + selectedItem;
				loc = loc + "&inventoryGroup=" + cellValue(selectedRowId,"inventoryGroup");
				loc = loc + "&catPartNo=" + cellValue(selectedRowId,"catPartNo");
				loc = loc + "&catalog=" + cellValue(selectedRowId,"catalogId");
				loc = loc + "&catalogCompanyId=" + cellValue(selectedRowId,"catalogCompanyId");
				
				if ($v("personnelCompanyId") == 'Radian') 
					  loc = "/tcmIS/hub/" + loc;	

				openWinGeneric(loc, "changeItem", "800", "400", "yes", "80", "80");
			}
		}
	}
}

function checkMlItemInput(selectedItem) {
	var noLinesChecked = 0;
	var rowNumber = "";
	// var currentcheckBox = $("ok"+rowNumber+"");
	var totalRecords = $v("totalLines")*1;
	//var selectedItem = $("selectedItem");
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

	for (j = 1; j <= totalRecords; j++) {
		//var lineitemID1 = "";
		//lineitemID = cellValue(j,"itemId");
		itemType = cellValue(j,"itemType");
		rowCheck = cellValue(j,"ok");
		newChemRequestId = cellValue(j,"newChemRequestId");

		if ( rowCheck && itemType == "ML") {
//			if (noLinesChecked == 0) {
//				rowNumber = j;
//			}

			noLinesChecked++;
			lineitemID1 = cellValue(j,"itemId");
			if (lineitemID1 != selectedItem) {
				if (allClear == 0) {
					finalMsgt = finalMsgt + "Different ML Item\n";
				}
				allClear += 1;
			}
			else if (newChemRequestId.length > 0) {
				if (allClear == 0) {
					finalMsgt = finalMsgt + "Pending New Chem Request- " + newChemRequestId.value + "\n";
				}
				allClear += 1;
			}
		}
	}

//	if (noLinesChecked == 0) {
//		selectedItem.value = "";
//	}
	if (allClear < 1) {
//		if (noLinesChecked != 0) {
			//selectedItem.value = $("item_id" + rowNumber + "").value;
			//$("selectedInventoryGroup").value = $("inventoryGroup" + rowNumber + "").value;
			//$("selectedCatalogId").value = $("catalogId" + rowNumber + "").value;
			//$("selectedCatPartNo").value = $("catPartNo" + rowNumber + "").value;
			//$("selectedCatalogCompanyId").value = $("catalogCompanyId" + rowNumber + "").value;
	//}
	return true;
	}
	else {
		alert(finalMsgt);
		return false;
	}
}

function printGHSLabel() {
	var itemId =  cellValue(selectedRowId,"itemId");
	var personnelId = document.getElementById("personnelId");
	var printerLocation = document.getElementById("printerLocation");

	var reportLoc = "/HaasReports/report/printghslabels.do"+
		"?itemId="+itemId+
    	"&personnel_Id="+personnelId.value + 
    	"&printerLocation="+printerLocation.value;
	openWinGeneric(reportLoc,"printGHSLabels","300","200","yes","200","200");
}

function printRTKLabel() {
	var itemId =  cellValue(selectedRowId,"itemId");
	var labelQuantity = cellValue(selectedRowId, "noOfLabels" );

	var reportLoc = "printrtklabels.do"
		+ "?itemId="+itemId
		+ "&labelQuantity="+labelQuantity;				

	openWinGeneric(reportLoc,"printRTKLabels","300","200","yes","200","200");
}


var qaStatement = new Array(
	{text:'',value:''},
	{text:'1',value:'1'},
	{text:'2',value:'2'}
);

var lineMap3 = new Array();

var config = [
	{columnId:"permission"},
	{columnId:"okPermission"},
	{columnId:"ok", columnName:'<fmt:message key="label.ok"/>', permission:true, type:"hch", width:4},
	{columnId:"itemId",columnName:'<fmt:message key="label.itemid"/>', width:8},
	{ columnId:"receiptId", columnName:'<fmt:message key="label.receiptid"/>', width:8 }, 
	{ columnId:"itemDesc", columnName:'<fmt:message key="label.description"/>', width:14, tooltip:true }, 
	{ columnId:"inventoryGroupName", width:12, columnName:'<fmt:message key="label.invengroup"/>' , tooltip:"Y"}, 
	{ columnId:"lotStatus", columnName:'<fmt:message key="label.status"/>', type:'hcoro', onChange:checkPickable, width:15 }, 
	{ columnId:"lotStatusAge", columnName:'<fmt:message key="label.lotstatusage"/>' }, 
	{ columnId:"expireDateDisplay", columnName:'<fmt:message key="label.expdate"/>' }, 
	{ columnId:"expireDateStr" }, 
	{ columnId:"expireDateUpdate" }, 
	{ columnId:"bin", columnName:'<fmt:message key="label.bin"/>' ,type:'hdoro' }, 
	{ columnId:"mfgLotPermission" }, 
	{ columnId:"mfgLot", columnName:'<fmt:message key="label.lot"/>', permission:true, type:'hed', width:12 }, 
	{ columnId:"specs", columnName:'<fmt:message key="label.receiptspecs"/>' }, 
	{ columnId:"buyType", columnName:'<fmt:message key="label.buytype"/>', width:10},
	{ columnId:"quantity", columnName:'<fmt:message key="label.quantity"/>', width:5 , align:"right"},
	{ columnId:"componentId"},
	{ columnId:"quantityReceived", columnName:'<fmt:message key="label.qtyreceived"/>', width:5, align:"right" }, 
	{ columnId:"noOfLabelsPermission" },
	{ columnId:"noOfLabels", columnName:'<fmt:message key="label.#labels"/>', width:4, permission:true, type:'hed', onChange:checkNoOfLabels , align:"right"}, 
	{ columnId:"ownerCompanyId", columnName:'<fmt:message key="label.owner"/>' }, 
	{ columnId:"unitCostDisplay",
	    <c:if test="${showUnitCost == 'Y'}" >
	        columnName:'<fmt:message key="label.unitcost"/>',
	    </c:if>
	    width:8, tooltip:"Y" },
	{ columnId:"qualityTrackingNumber", columnName:'<fmt:message key="label.addqualitynote"/>', type:'hed' }, 
	{ columnId:"notes", columnName:'<fmt:message key="label.notes"/>' ,type:"txt", tooltip:"Y", width:14 }, 
	{ columnId:"internalReceiptNotes", columnName:'<fmt:message key="label.internalreceiptnotes"/>', type:"txt", tooltip:"Y", permission:true, width:14 },
	{ columnId:"deliveryTicketPermission"},
	{ columnId:"deliveryTicket", columnName:'<fmt:message key="label.deliveryticket"/>', tooltip:"Y", width:12, size:20, permission:true, type:'hed' },
	{ columnId:"dateOfShipmentDisplay", columnName:'<fmt:message key="label.manufacturerdateofshipment(dos)"/>', width:8, align:"center" }, 
	{ columnId:"dateOfManufactureDisplay", columnName:'<fmt:message key="label.dateofmanufacturer(dom)"/>', width:8, align:"center" }, 
	{ columnId:"dateOfRepackagingDisplay", columnName:'<fmt:message key="label.dateofrepackaging"/>', width:8, align:"center" },
	{ columnId:"dateOfRepackaging"},
	{ columnId:"dateOfReceiptDisplay", columnName:'<fmt:message key="label.dor"/>', width:8, align:"center" },
	{ columnId:"minimumExpireDate", columnName:'<fmt:message key="label.minexpdate"/>' }, 
	{ columnId:"originalReceiptId", columnName:'<fmt:message key="label.origreceiptid"/>' }, 
	{ columnId:"customerReceiptId", columnName:'<fmt:message key="label.legacyreceiptid"/>', type:'hed' }, 
	{ columnId:"po_line", columnName:'<fmt:message key="label.poline"/>' }, 
	{ columnId:"lotStatusRootCause", columnName:'<fmt:message key="label.rootcause"/>', tooltip:true }, 
	{ columnId:"poNumber", columnName:'<fmt:message key="label.customerpo"/>', width:8, tooltip:true }, 
	{ columnId:"recertNumberPermission" }, 
	{ columnId:"recertNumber", columnName:'<fmt:message key="label.recert#"/>', permission:true, type:'hed' }, 
	{ columnId:"netPendingAdj", columnName:'<fmt:message key="label.pendingadjustment"/>', align:"right"}, 
	{ columnId:"lastPrintDateDisplay", columnName:'<fmt:message key="label.lastprintdate"/>' , align:"center" }, 
	{ columnId:"storageTemp", columnName:'<fmt:message key="label.storagetemp"/>', align:"center"  , width:6}, 
	{ columnId:"unitLabelPrinted", columnName:'<fmt:message key="label.unitlabeledper129p"/>', align:"center"  , width:6 }, 
	{ columnId:"packagingDesc", width:12, columnName:'<fmt:message key="label.pkg"/>' , tooltip:true}, 
	{columnId:"incomingTesting",columnName:'<fmt:message key="label.incomingtesting"/>', align:"center"  , width:5},
	{ columnId:"mfgLabelExpireDateDisplay", columnName:'<fmt:message key="label.labelexpiredate"/>', permission:true, sorting:'na', width:7 }, 
	{columnId:"mfgLabelExpireDate"},
	{columnId:"supplierCageCodePermission" },
	{ columnId:"supplierCageCode", columnName:'<fmt:message key="label.cagecode"/>', width:15, permission:true, type:'hed' }, 
	{ columnId:"hub" },
	{ columnId:"inventoryGroup" }, 
	{ columnId:"opsEntityId" }, 
	{ columnId:"radianPo" }, 
	{ columnId:"poLine" }, 
	{ columnId:"pickable" }, 
	{ columnId:"dateOfShipment" }, 
	{ columnId:"dateOfManufacture" }, 
	{columnId:"itemType"},
	{columnId:"dateOfReceipt"},
	{ columnId:"oldLotStatus" }, 
	{ columnId:"qcDate"}, 
	{ columnId:"polchemIg" }, 
	{columnId:"doNumberRequired"},
	{columnId:"unitLabelCatPartNo"},
	{columnId:"netPendingAdj"},
	{columnId:"catalogCompanyId"},
	{columnId:"catPartNo"},
	{columnId:"catalogId"},
	{columnId:"newChemRequestId"},
	{columnId:"receiptDocumentAvailable"},
	{columnId:"responsibleCompanyId"},
	{columnId:"qualityControlItem"},
	{columnId:"unitCost"},
	{columnId:"OrgExpireDateDisplay"},
	{columnId:"manageKitsAsSingleUnit"},
	{columnId:"lotStatusRootCauseNotes"},
	{columnId:"internalReceiptNotesPermission"},
	{columnId:"receivingStatus"},
	{columnId:"labTestComplete"},
	{columnId:"printGHSLabels"},
	{columnId:"certupdate"}
];

<%-- 8 - 17 are component data --%>
<%-- 30 & 31 Date of Repacking --%>
<%-- 47 & 48 Label Expire Date --%>
var noRowSpanCols =new Array(8,9,10,11,12,13,14,15,16,17,30,31,47,48);
var rowSpanCols = new Array();

var firstLevelRowSpan = new Array();
for( i = 0 ; i < config.length; i++) {
	 firstLevelRowSpan[i] = true;
}
for( i = 0 ; i < config.length; i++) {
	 firstLevelRowSpan[noRowSpanCols[i]] = false;
}

for( i = 0 ; i < config.length; i++) {
	if( firstLevelRowSpan[i] )
		rowSpanCols[rowSpanCols.length] = i;
}


function getZeroRecordFooterMessage() {
	var startSearchTime = parent.window.document.getElementById("startSearchTime");
	var now = new Date();
	var minutes = 0;
	var seconds = 0;
//the duration is in milliseconds
	var searchDuration = now.getTime()-(startSearchTime.value*1);
	if (searchDuration > (1000*60)) {   //calculating minutes
  		minutes = Math.round((searchDuration / (1000*60)));
  		var remainder = searchDuration % (1000*60);
  	if (remainder > 0) {   //calculating seconds
    	seconds = Math.round(remainder / 1000);
  	}
	}else {  //calculating seconds
  		seconds = Math.round(searchDuration / 1000);
	}
	var footer = parent.document.getElementById("footer");
	if (minutes != 0) {
  		return messagesData.recordFound+": 0 -- "+messagesData.searchDuration+": "+minutes+" "+messagesData.minutes+" "+seconds+" "+messagesData.seconds;
	}else {
  		return messagesData.recordFound+": 0 -- "+messagesData.searchDuration+": "+seconds+" "+messagesData.seconds;
	}
}

function faketotal() {
	if( $v('totalLines') == '0' ) {
		$('totalLines').value = 1;
		setTimeout("parent.document.getElementById('footer').innerHTML=getZeroRecordFooterMessage()",300);
//		parent.document.getElementById('addparttoacllink').style["display"]="";
	}
//	else
//		parent.document.getElementById('addparttoacllink').style["display"]="none";
//	if( '${param.priceGroupId}' )
//		parent.document.getElementById('addparttoacllink').style["display"]="";

}

function closeTransitWin() {
	parent.closeTransitWin();
}

/* printing label codes */
//http://192.168.18.176/tcmIS/hub/printcontainerlabels.do?labelReceipts=3513506%2c3540653%2c3540654%2c3553258%2c3557220%2c3557232%2c3557233&paperSize=receiptDocument&hubNumber=2405&skipKitLabels=No
 function printDocumentLabels(labelType) {
		var totalLines = $v("totalLines");
		var checkedCount = 0;
		var labelReceipts = "";
		var po = null;
		var samePo = false;
//		radianPo_
		for ( var p = 1; p <= totalLines; p++) {
			try {
				var ok = cellValue(p,"ok");

				if (ok) {
					var receiptId = cellValue(p,"receiptId");
					if (checkedCount > 0) {
						labelReceipts += ',';
					}
					labelReceipts += receiptId;
					checkedCount++;
					thisPO = null;
					try {
	// make sure it always get it.
						thisPO = cellValue(p,"radianPo");
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
			if( confirm("Do you want to group these receipt certs together?") ) {
//				document.getElementById("groupReceiptDoc").value = "Y";
				groupReceiptDoc = true;
			}
		}

		var hub0 = $("hub");
	    if (labelReceipts.trim().length > 0) {
			var loc = "printcontainerlabels.do?labelReceipts=" + labelReceipts + "";
			loc = loc + "&paperSize=receiptDocument&skipKitLabels=Yes&hubNumber="+hub0.value+"";
			
			if ($v("personnelCompanyId") == 'Radian') 
				  loc = "/tcmIS/hub/" + loc;	
			
			if( groupReceiptDoc ) loc += "&groupReceiptDoc=Y";
			openWinGeneric(loc, "printReceiptLabels11", "800", "500", "yes", "80", "80");


		}

	}

//Nawaz 06-27-02 to handle the generate labels button
 function generatealllabels(entered,paperSize) {
     return generatelabels(entered, true,paperSize);
 }

 function generatelabels(entered, doAllLabels,paperSize) {
     var doAll = (true == doAllLabels ? true : false);
 	var ids = "";
    var cntr = 0;
 	var max = $v("totalLines");
	for (row = 1; row <= max; row++) {
			var ok = cellValue(row,"ok");

// in grid, we alway have value...?			celldValue(row,"quantityReceived") != null
// 		if ( celldValue(row,"quantityReceived") != null && (doAll || ok )) {
 	 		if ( cellValue(row,"noOfLabelsPermission") == "Y" && ( doAll || ok ) ){
 			if (ids.length > 0) {
 				ids += ",";
 			}
 			ids += "" + cellValue(row,"receiptId");
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
//		var generatingForm = document.generateLabelsForm;
//		var receivedForm = document.receiving;
		var cntr = 0;
	 	var max = $v("totalLines");
	    if (paperSize != null && paperSize.trim()== "receiptDetail")
	    {
	     	$("paperSize").value = "receiptDetail";
	    }
	    else
	    {
	    	$("paperSize").value = $v("Paper");
	    }
// This parts are duplicate, I will remove later.
	    for (row = 1; row <= max; row++) {
			var ok = cellValue(row,"ok");
//	 		if ( celldValue(row,"quantityReceived") != null && (doAll || ok )) {
			if ( cellValue(row,"noOfLabelsPermission") == "Y" && ok ) {
				noOfLabels = cellValue( row, "noOfLabels" );
				if (ids.length > 0) {
					ids += ",";
				}
				ids += "" + cellValue(row,"receiptId");
/* I don't see where these are used.
				var receiptIdFieldName = "containerLabelMasterViewBean[" + cntr + "].receiptId";
				var quantityReceivedFieldName = "containerLabelMasterViewBean[" + cntr + "].quantityReceived";
				removeFieldFromFormIfExtant(generatingForm, receiptIdFieldName);
				addFieldToForm(generatingForm, receiptIdFieldName, document.getElementById("receipt_id" + row).value);
				removeFieldFromFormIfExtant(generatingForm, quantityReceivedFieldName);
				addFieldToForm(generatingForm, quantityReceivedFieldName, celldValue(row,"quantityReceived") );
*/
				cntr++;
			}
		}

		if ( parent.document.getElementById("printKitLabels") != null && parent.document.getElementById("printKitLabels").checked) {
			$("skipKitLabels").value = "Yes";
		}
		else {
			$("skipKitLabels").value = "No";
		}

		$('labelReceipts').value = ids;
	    if (ids.trim().length > 0)
	    {
			var oriTarget = document.genericForm.target;
			var oriAction = document.genericForm.action;
			var loc = "printreceiptboxlabels.do?FromLogisticsNew=Y&printLabelsNow=true&quantityReceived="+ noOfLabels;
			
			if ($v("personnelCompanyId") == 'Radian') 
				loc = "/tcmIS/hub/" + loc;
			
			document.genericForm.action = loc;
			haasGrid.parentFormOnSubmit(); //prepare grid for data sending
			document.genericForm.target = "_GenerateLabels";
			document.genericForm.submit();

			document.genericForm.target = oriTarget;
			document.genericForm.action = oriAction;
	    }
	}
////////
function printReceivingBoxLabels() {
	var totalLines = $v("totalLines");

	var checkedCount = 0;
	var labelReceipts = "";
	for ( var p = 1; p <= totalLines; p++) {
		try {
			var ok = cellValue(p,"ok");
			if (ok) {
				var receiptId = cellValue(p,"receiptId");
				if (checkedCount > 0) {
					labelReceipts += ',';
				}
				labelReceipts += receiptId;
				checkedCount++;
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


// -->
</script>
</head>

<body bgcolor="#ffffff"
	onload="faketotal();resultOnLoadWithGrid(gridConfig);setTimeout('loadRowSpans()',100);">

	<tcmis:form action="/logisticsresults.do"
		onsubmit="return submitFrameOnlyOnce();">

		<div class="interface" id="resultsPage">
			<div class="backGroundContent">
				<c:set var="dataCount" value='${0}' />


				<div id="LogisticsBean" style="width: 100%; height: 400px;"></div>
				<c:if test="${ 1 == 1 }">
					<%--
<c:if test="${!empty beanCollection}" >
--%>
					<!-- Search results start -->
					<script type="text/javascript">
<!--

/*This is to keep track of whether to show any update links.
If the user has any update permisions for any row then we show update links.*/
<c:set var="showUpdateLink" value='N'/>
/*Storing the data to be displayed in a JSON object array.*/

/*I am looping thru the map I created in the JSP to set the row spans for different columns*/


function selectRightclick(rowId,cellInd){
// haasGrid.selectRowById(rowId,null,false,false);
 selectRow(rowId,cellInd);
}

var alertcount = 0 ;
function setExpDateChanged(rowid) {
	cell(rowid,"expireDateUpdate").setValue("Yes");
	cell(rowid,"expireDateStr").setValue($v('expireDateDisplay'+rowid));
	//alert(rowid+cellValue(rowid,"expireDateUpdate"));
}

function setRChanged(rowid) {
	cell(rowid,"expireDateUpdate").setValue("Yes");
	cell(rowid,"dateOfReceipt").setValue($v('dateOfReceiptDisplay'+rowid));
	//alert(rowid+cellValue(rowid,"expireDateUpdate"));

}
function setMChanged(rowid) {
	cell(rowid,"expireDateUpdate").setValue("Yes");
	cell(rowid,"dateOfManufacture").setValue($v('dateOfManufactureDisplay'+rowid));
	//alert(rowid+cellValue(rowid,"expireDateUpdate"));
}

function setSChanged(rowid) {
//	cell(rowid,"expireDateUpdate").setValue("Yes");
	cell(rowid,"dateOfShipment").setValue($v('dateOfShipmentDisplay'+rowid));
	//alert(rowid+cellValue(rowid,"expireDateUpdate"));

}

function setRPChanged(rowid) {
	cell(rowid,"dateOfRepackaging").setValue($v('dateOfRepackagingDisplay'+rowid));
	//alert(rowid+cellValue(rowid,"expireDateUpdate"));
}

function myGetCalendarE(rowid)
{
	var rowid0 = rowid;
    return getCalendar($('expireDateDisplay'+rowid0),null,null,null,null,'Y');
}

function myGetCalendarS(rowid)
{
	var rowid0 = rowid;
	return getCalendar($('dateOfShipmentDisplay'+rowid0),null,$('tomorrow'),$('dateOfManufactureDisplay'+rowid0),null);
}
function myGetCalendarM(rowid)
{
	var rowid0 = rowid;
	return getCalendar($('dateOfManufactureDisplay'+rowid0),null,$('tomorrow'),null,$('dateOfShipmentDisplay'+rowid0));
}

function myGetCalendarRP(rowid)
{
	var rowid0 = rowid;
	return getCalendar($('dateOfRepackagingDisplay'+rowid0),$('dateOfManufactureDisplay'+rowid0),null,null,$('dateOfReceiptDisplay'+rowid0));
}

var children = new Array();
function pp(name) {
	var value = parent.$v(name);
//	alert( value );
	return encodeURIComponent(value);
}

function gg(name) {
	var value = null;
	value = cellValue(selectedRowId,name);
//	alert( value );
	return encodeURIComponent(value);
}

var lineMap = new Array();

<c:set var="gridind" value="0"/>
<c:set var="prePar" value=""/>
<c:set var="colorIndex" value="-1"/>

<c:forEach var="bean" items="${pageNameViewBeanCollection}" varStatus="status">
lineMap[${gridind}] = 1;
<c:set var="curPar" value="${bean.receiptId}"/>
<c:if test="${ curPar != prePar }">
	<c:if test="${ isPreviousMultiple eq 'true'}">
		lineMap[${firstLineIndex}] = ${curSpanSize};
	</c:if>
	<c:set var="curSpanSize" value="1"/>
	<c:set var="firstLineIndex" value="${gridind}"/>
</c:if>
<c:if test="${ curPar == prePar }">
	<c:set var="curSpanSize" value="${curSpanSize+1}"/>
</c:if>

<c:set var="colorIndex" value="${colorIndex+1}"/>

<c:if test="${bean.manageKitsAsSingleUnit eq 'N'}">
	<c:set var="isPreviousMultiple" value="true"/>
	<c:if test="${ curPar == prePar }">
		lineMap[${gridind}] = 0;
		<c:set var="colorIndex" value="${colorIndex-1}"/>
	</c:if>
</c:if>
<c:if test="${bean.manageKitsAsSingleUnit ne 'N'}">
	<c:set var="isPreviousMultiple" value=""/>
</c:if>

lineMap3[${gridind}] = ${colorIndex%2} ;

<c:set var="prePar" value="${curPar}"/>
<c:set var="gridind" value="${gridind+1}"/>
</c:forEach>

<c:if test="${ isPreviousMultiple eq 'true'}">
lineMap[${firstLineIndex}] = ${curSpanSize};
//alert( ${firstLineIndex}+":"+ ${curSpanSize});
</c:if>


<c:set var="anyUpdateStatusPermission" value="N"/>
<tcmis:inventoryGroupPermission indicator="true" userGroupId="inventoryReadonly">
	<c:set var="anyUpdateStatusPermission" value="Y"/>
</tcmis:inventoryGroupPermission>
<tcmis:inventoryGroupPermission indicator="true" userGroupId="Inventory">
	<c:set var="anyUpdateStatusPermission" value="Y"/>
</tcmis:inventoryGroupPermission>

<c:set var="childCount" value="0"/>
<c:set var="prePar" value=""/>

var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="logisticsBean" items="${pageNameViewBeanCollection}" varStatus="status">
	<c:set var="receiptHasError" value="N"/>
	<c:forEach var="errorReceiptId" items="${hasError}">
		<c:if test="${errorReceiptId == logisticsBean.receiptId}">
			<c:set var="receiptHasError" value="Y"/>
		</c:if>
	</c:forEach>

<c:set var="curPar" value="${logisticsBean.receiptId}"/>
<c:set var="desc" value="${logisticsBean.itemDesc}"/>

<c:if test="${logisticsBean.manageKitsAsSingleUnit eq 'N'}">
	<c:if test="${ curPar != prePar }">
		<c:set var="childCount" value="0"/>
		<c:set var="descArr" value="${fn:split(logisticsBean.itemDesc, '||')}"/>
	</c:if>
	<c:set var="desc" value="${descArr[childCount]}"/>
	<c:set var="childCount" value="${childCount+1}"/>
</c:if>
<c:set var="prePar" value="${curPar}"/>

<fmt:formatDate var="expireDate" value="${logisticsBean.expireDate}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="expireYear" value="${logisticsBean.expireDate}" pattern="yyyy"/>
<c:if test="${expireYear == '3000'}">
	<c:set var="expireDate"><fmt:message key="label.indefinite"/></c:set>
</c:if>
<fmt:formatDate var="lastPrintDate" value="${status.current.lastPrintDate}" pattern="${dateTimeFormatPattern}"/>
<fmt:formatDate var="dateOfReceipt" value="${status.current.dateOfReceipt}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="dateOfShipment" value="${status.current.dateOfShipment}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="dateOfManufacture" value="${status.current.dateOfManufacture}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="dateOfRepackaging" value="${status.current.dateOfRepackaging}" pattern="${dateFormatPattern}"/>

<c:if test="${dataCount > 0}">,</c:if>
<c:set var="dataCount" value='${dataCount+1}'/>
<c:set var="dup">
<%--
<input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="editButton" value="<fmt:message key="receiving.label.duplicateline"/>" onclick="dupline(${dataCount})" />
--%>
<input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="editButton" value="Add CPP" onclick="dupline(${dataCount})" />
</c:set>

    <c:set var="dateOfReceiptDisplay">
		<input class="inputBox pointer" id="dateOfReceiptDisplay${dataCount}" type="text" value="${dateOfReceipt}" size="9" readonly onClick="return myGetCalendarR(${dataCount})" onchange="setRChanged(${dataCount})"/>
	</c:set>
	<c:set var="dateOfShipmentDisplay">
		<input class="inputBox pointer" id="dateOfShipmentDisplay${dataCount}" type="text" value="${dateOfShipment}" size="9" readonly onClick="return myGetCalendarS(${dataCount})" onchange="setSChanged(${dataCount})"/>
	</c:set>
	<c:set var="dateOfManufactureDisplay">
	<input class="inputBox pointer" id="dateOfManufactureDisplay${dataCount}" type="text" value="${dateOfManufacture}" size="9" readonly onClick="return myGetCalendarM(${dataCount})" onchange="setMChanged(${dataCount})"/>
	</c:set>

	<c:set var="dateOfRepackagingDisplay">
	<input class="inputBox pointer" id="dateOfRepackagingDisplay${dataCount}" type="text" value="${dateOfRepackaging}" size="9" readonly onClick="return myGetCalendarRP(${dataCount})" onchange="setRPChanged(${dataCount})"/>
	</c:set>



<c:set var="InventoryPermission" value="N"/>
<c:set var="recQcAllowed" value="N"/>
<tcmis:inventoryGroupPermission indicator="true" userGroupId="Inventory" inventoryGroup="${logisticsBean.inventoryGroup}">
	<c:set var="InventoryPermission" value="Y"/>
	<c:if test="${anyUpdateStatusPermission eq 'Y'}">
		<c:set var="recQcAllowed" value="Y"/>
	</c:if>
</tcmis:inventoryGroupPermission>

<c:set var="onlynonPickableStatusPermission" value="N"/>
<tcmis:inventoryGroupPermission indicator="true" userGroupId="onlynonPickableStatus" inventoryGroup="${logisticsBean.inventoryGroup}">
	<c:set var="onlynonPickableStatusPermission" value="Y"/>
</tcmis:inventoryGroupPermission>

<c:set var="PickStatusUpdPermission" value="N"/>
<c:set var="stautsupdallowed" value="N"/>
<tcmis:inventoryGroupPermission indicator="true" userGroupId="PickStatusUpd" inventoryGroup="${logisticsBean.inventoryGroup}">
	<c:set var="PickStatusUpdPermission" value="Y"/>

	<c:if test="${onlynonPickableStatusPermission eq 'N' and logisticsBean.qcDate ne ''}">
		<c:set var="stautsupdallowed" value="Y"/>
	</c:if>
</tcmis:inventoryGroupPermission>

<%--//in servlet it's called expupdallowed with onchange = checkExpireDate
//this permission is for expiryDate, mfgLot and recert#--%>
<c:set var="ExpUpdatePermission" value="N"/>
<c:set var="expireDateDisplay" value="${expireDate}"/>
<tcmis:inventoryGroupPermission indicator="true" userGroupId="ExpUpdate" inventoryGroup="${logisticsBean.inventoryGroup}">
	<c:set var="ExpUpdatePermission" value="Y"/>
</tcmis:inventoryGroupPermission>
<c:if test="${ 'Y' ne logisticsBean.qualityControlItem}">
	<c:set var="ExpUpdatePermission" value="Y"/>
	<c:if test="${onlynonPickableStatusPermission eq 'N' and logisticsBean.qcDate ne ''}">
		<c:set var="stautsupdallowed" value="Y"/>
	</c:if>
</c:if>
<c:if test="${ 'Y' eq logisticsBean.lockExpireDate}">
	<c:set var="ExpUpdatePermission" value="N"/>
</c:if>


<c:set var="unitLabelPrinted" value="${logisticsBean.unitLabelPrinted}"/>
<c:if test="${ExpUpdatePermission eq 'Y'}">
	<c:set var="expireDateDisplay">
		<input class="inputBox pointer" id="expireDateDisplay${dataCount}" type="text" value="${expireDate}" size="9" readonly onClick="return myGetCalendarE(${dataCount})" onchange="setExpDateChanged(${dataCount})"/>
	</c:set>
</c:if>

<fmt:formatDate var="qcDate" value="${logisticsBean.qcDate}" pattern="${dateFormatPattern}"/>

<c:set var="ownerCompanyId" value="${logisticsBean.ownerCompanyId}"/>
<c:if test="${ empty ownerCompanyId or 'Radian' eq ownerCompanyId}">
	<c:set var="ownerCompanyId" value="Haas"/>
</c:if>


<c:set var="mfgLabelExpireDate"><fmt:formatDate value="${logisticsBean.mfgLabelExpireDate}" pattern="${dateFormatPattern}"/></c:set>
<c:set var="mfgLabelExpireDateDisplay" value="${mfgLabelExpireDate}"/>
<c:if test="${mfgLabelExpireDate == '01-Jan-3000'}"><c:set var="mfgLabelExpireDateDisplay"><fmt:message key="label.indefinite"/></c:set></c:if>



<%--// I will use recQcAllowed for general permission, ( for check box. ) in servlet this.getupdateStatus() && recqcallowed
// InventoryPermission is in servlet recqcallowed
/*The row ID needs to start with 1 per their design.*/--%>
	{ id:${status.count},
   	<c:choose>
		<c:when test="${'ML' == logisticsBean.itemType}">'class':"grid_green",</c:when>
		<c:when test="${'Y' != logisticsBean.pickable}">'class':"grid_yellow",</c:when>
		<c:when test="${'Y' == receiptHasError}">'class':"grid_red",</c:when>
	</c:choose>
	 data:[ '${recQcAllowed}',
	   	 	'${anyUpdateStatusPermission}',
	  		false,
	  		'${logisticsBean.itemId}',
	  		'${logisticsBean.receiptId}',
	  		'<tcmis:jsReplace value="${desc}" processMultiLines="true"/>',
	  		'${logisticsBean.inventoryGroupName}',
	  		'${logisticsBean.lotStatus}',
	  		'${logisticsBean.lotStatusAge}',
	  		'${expireDateDisplay}',
	  		'${expireDate}',
	  		'N',
	  		'<tcmis:jsReplace value="${logisticsBean.bin}"/>',
	  		'${ExpUpdatePermission}',
	  		'<tcmis:jsReplace value="${logisticsBean.mfgLot}"/>',
	  		'<tcmis:jsReplace value="${logisticsBean.specs}"/>',
	  		<c:choose><c:when test="${logisticsBean.buyTypeFlag == 'Y'}">'${logisticsBean.buyType}'</c:when><c:otherwise>''</c:otherwise></c:choose>,
	  		'${logisticsBean.quantity}',
	  		'${logisticsBean.componentId}',
	  		'${logisticsBean.quantityReceived}',
	  		'${anyUpdateStatusPermission}',
	  		'${logisticsBean.noOfLabels}',
	  		'${ownerCompanyId}',
	  		'${logisticsBean.currencyId} ${logisticsBean.unitCost}',
	  		'${logisticsBean.qualityTrackingNumber}',
	  		'<tcmis:jsReplace value="${logisticsBean.notes}" processMultiLines="true"/>',
	  		'<tcmis:jsReplace value="${logisticsBean.internalReceiptNotes}" processMultiLines="true"/>',
	  		'${InventoryPermission}',
	  		'<tcmis:jsReplace value="${logisticsBean.deliveryTicket}"/>',
	  		'${dateOfShipmentDisplay}',
	  		'${dateOfManufactureDisplay}',
	 		'${dateOfRepackagingDisplay}',
	 		'${dateOfRepackaging}',
	  		'${dateOfReceipt}',
	  		'${logisticsBean.minimumExpireDate}',
	  		'${logisticsBean.originalReceiptId}',
	  		'${logisticsBean.customerReceiptId}',
	  		'${logisticsBean.radianPo}-${logisticsBean.poLine}',
	  		'<tcmis:jsReplace value="${logisticsBean.lotStatusRootCause}" processMultiLines="true"/>',
	  		'${logisticsBean.poNumber}',
	  		'${ExpUpdatePermission}',
	  		'${logisticsBean.recertNumber}',
	  		-1*${logisticsBean.netPendingAdj},
	  		'${lastPrintDate}',
	  		'<tcmis:jsReplace value="${logisticsBean.storageTemp}"/>',
	  		'${unitLabelPrinted}',
	  		'<tcmis:jsReplace value="${logisticsBean.receiptPackaging}" processMultiLines="true"/>',
	  		'${logisticsBean.incomingTesting}',
	  	     <c:choose>
	           <c:when test="${anyUpdateStatusPermission == 'Y'}" >
	           		'<input class="inputBox pointer" id="mfgLabelExpireDateDisplay${dataCount}" type="text" value="${mfgLabelExpireDateDisplay}" size="9" readonly onClick="return myGetCalendarMFG(${dataCount})" onchange="setMFGExpDateChanged(${dataCount})"/>',
	           </c:when>
	           <c:otherwise>
	           		'${mfgLabelExpireDateDisplay}',
	           </c:otherwise>
	     	</c:choose>
	 		'${mfgLabelExpireDate}',
    	 	'${anyUpdateStatusPermission}',
	  		'${logisticsBean.supplierCageCode}',
			'${logisticsBean.hub}',
			'${logisticsBean.inventoryGroup}',
			'${logisticsBean.opsEntityId}',
	  		'${logisticsBean.radianPo}',
	  		'${logisticsBean.poLine}',
	  		'${logisticsBean.pickable}',
	  		'${dateOfShipment}',
	  		'${dateOfManufacture}',
	  		'${logisticsBean.itemType}',
	  		'${dateOfReceipt}',
	  		'${logisticsBean.lotStatus}',
	  		'${qcDate}',
	  		'${logisticsBean.polchemIg}',
	  		'${logisticsBean.doNumberRequired}',
	  		'${logisticsBean.unitLabelCatPartNo}',
	  		'${logisticsBean.netPendingAdj}',
	  		'${logisticsBean.catalogCompanyId}',
	  		'${logisticsBean.catPartNo}',
	  		'${logisticsBean.catalogId}',
	  		'${logisticsBean.newChemRequestId}',
	  		'${logisticsBean.receiptDocumentAvailable}',
	  		'${logisticsBean.responsibleCompanyId}',
	  		'${logisticsBean.qualityControlItem}',
	  		'${logisticsBean.unitCost}',
	  		'${expireDate}',
	  		'${logisticsBean.manageKitsAsSingleUnit}',
	  		'',
	   	 	'${anyUpdateStatusPermission}',
	  		'${logisticsBean.receivingStatus}',
            '${logisticsBean.labTestComplete}',
            '',
            ''
         ]
	}
</c:forEach>
]};

//-->
</script>

					<!-- end of grid div. -->
				</c:if>

				<c:if test="${empty pageNameViewBeanCollection}">
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="tableNoData" id="resultsPageTable">
						<tr>
							<td width="100%"><fmt:message key="main.nodatafound" /></td>
						</tr>
					</table>
				</c:if>

				<div id="hiddenElements" style="display: none;">
					<%-- regular inputs --%>
					<input name="totalLines" id="totalLines" value="${dataCount}"
						type="hidden" /> <input type="hidden" name="uAction" id="uAction"
						value="update" /> <input name='today' id='today' type="hidden"
						value='<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>' />
					<input name='tomorrow' id='tomorrow' type="hidden"
						value='<tcmis:getDateTag numberOfDaysFromToday="1" datePattern="${dateFormatPattern}"/>' />
					<input type="hidden" name="searchArgument" id="searchArgument"
						value="<tcmis:jsReplace value='${param.searchArgument}' />" /> <input type="hidden" 
						name="showNeedingPrint" id="showNeedingPrint"
						value="<tcmis:jsReplace value='${param.showNeedingPrint}' />" /> <input type="hidden" 
						name="includeHistory" id="includeHistory"
						value="<tcmis:jsReplace value='${param.includeHistory}' />" /> <input type="hidden" 
						name="numDaysOld" id="numDaysOld" value="<tcmis:jsReplace value='${param.numDaysOld}' />" /> <input 
						type="hidden" name="printKitLabels" id="printKitLabels"
						value="Yes" /> <input type="hidden" name="skipKitLabels"
						id="skipKitLabels" value="No" /> <input type="hidden"
						name="paperSize" id="paperSize" value="31" /> <input type="hidden"
						name="Paper" id="Paper" value="31" /> <input type="hidden"
						name="labelReceipts" id="labelReceipts" value="31" /> <input
						type="hidden" name="hub" id="hub" value="<tcmis:jsReplace value='${param.hub}' />" /> <input 
						type="hidden" name="hubName" id="hubName" value="<tcmis:jsReplace value='${param.hubName}' />" /> 
					<input type="hidden" name="transactionDate" id="transactionDate"
						value="<tcmis:jsReplace value='${param.transactionDate}' />" /> <input type="hidden" 
						name="receiverId" id="receiverId" value="<tcmis:jsReplace value='${param.receiverId}' />" /> <input 
						type="hidden" name="personnelId" id="personnelId"
						value="${sessionScope.personnelBean.personnelId}" /> <input
						type="hidden" name="printerLocation" id="printerLocation"
						value="${sessionScope.personnelBean.printerLocation}" />


					<%-- selects --%>
					<input name='hub' id='hub' type="hidden" value="<tcmis:jsReplace value='${param.hub}' />" /> <input 
						type="hidden" name="searchField" id="searchField"
						value="<tcmis:jsReplace value='${param.searchField}' />" /> <input type="hidden"
						name="searchMode" id="searchMode" value="<tcmis:jsReplace value='${param.searchMode}' />" /> <input 
						type="hidden" name="inventoryGroup" id="inventoryGroup"
						value="<tcmis:jsReplace value='${param.inventoryGroup}' />" /> <input type="hidden" 
						name="sortBy" id="sortBy" value="<tcmis:jsReplace value='${param.sortBy}' />" /> <input 
						type="hidden" name="companyId" id="companyId"
						value="<tcmis:jsReplace value='${param.companyId}' />" /> 
					<c:forEach items="${paramValues.lotStatus}" varStatus="status">
						<input type="hidden" name="lotStatus"
							id="lotStatus${status.index}" value="${status.current}" />
					</c:forEach>
					<%-- other stuff --%>
					<input name="minHeight" id="minHeight" type="hidden" value="210" />
					<input name="startSearchTime" id="startSearchTime" type="hidden"
						value="${startSearchTime}" /> <input name="endSearchTime"
						id="endSearchTime" type="hidden" value="${endSearchTime}" /> <input
						name="minHeight" id="minHeight" type="hidden" value="210" />

					<input type="hidden" name="personnelCompanyId"  id="personnelCompanyId" value="${personnelBean.companyId}"/>
				</div>

			</div>
			<!-- close of backGroundContent -->
		</div>
		<!-- close of interface -->

	</tcmis:form>

	<!-- You can build your error messages below. But we want to trigger the pop-up from the main page.
So this is just used to feed the pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
	<!-- Error Messages Begins -->
	<div id="errorMessagesAreaBody" style="display: none;">
		${tcmISError}<br />
		<c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br />
		</c:forEach>
	</div>

	<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${empty tcmISErrors and empty tcmISError}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>

var incomingTestOption = '';
var startMarsForRecert = '';
var isUpdate = false;
<c:if test="${param.uAction == 'update'}">
	isUpdate = true;
</c:if>
function loadRowSpans()
{ //return;
 var start = 1;
 var totalRow = haasGrid.getRowsNum();


 for(var i=0;i<totalRow;i++){
	   try
	   {

	     var rowSpan = lineMap[i];
	     if( !rowSpan || rowSpan == 1 ) continue;
	     rowId = i+1;
	     rspan = rowSpan*1;
	     for(j=0;j<config.length;j++) {
	    	 if( firstLevelRowSpan[j] )
	    	 	haasGrid.setRowspan(rowId,j,rspan);
	     }
 }
	   catch (ex)
	   {
	      alert(i +":"+rowId+":"+lineMap[i]);
		}

	 }
 /*Need to call this only if the grid has rowspans > 1*/
 // haasGrid._fixAlterCss();
}

function selectRow()
{
// to show menu directly
   rightClick = false;
   rowId0 = arguments[0];
   colId0 = arguments[1];
   ee     = arguments[2];

   selectedRowId = rowId0;
   if( ee != null ) {
   		if( ee.button != null && ee.button == 2 ) rightClick = true;
   		else if( ee.which == 3  ) rightClick = true;
   }
   columnId = beanGrid.getColumnId(colId0);
//   alert( cellValue(rowId0,"itemId")+":"+cellValue(rowId0,"hub")+":"+rowId0);
   if( columnId == 'bin' ) {
	   if( cellValue(rowId0,"permission") == 'Y' ) {
	   	loadBins(cellValue(rowId0,"itemId"),cellValue(rowId0,"hub"),rowId0);
		return;
	   }
   }


    //do right mouse click
    if (rightClick) {
        var incomingTesting = cellValue(selectedRowId,"incomingTesting");
        //incoming lab testing get data
        if (incomingTesting == 'Y') {
            loadRightClickData(selectedRowId);
        }else {
            incomingTestOption = '';
            startMarsForRecert = '';
            buildRightClickOption();
        }
    } //end of right mouse click
} //end of method

function buildRightClickOption() {
    var vvitems = new Array();
    vvitems[vvitems.length] = 'text=<fmt:message key="label.shipinfo"/>;url=javascript:enterdotinfo();';
    vvitems[vvitems.length] = 'text=<fmt:message key="label.specs"/>;url=javascript:receiptSpecs();';
    vvitems[vvitems.length] = 'text=<fmt:message key="prevtransactions.title"/>;url=javascript:showPreviousReceiptTransactions();';
    if( cellValue(selectedRowId,"receiptDocumentAvailable") == 'Y' )
        vvitems[vvitems.length] = 'text=View/Add Receipt Documents;url=javascript:showProjectDocuments();';
    else
        vvitems[vvitems.length] = 'text=Add Receipt Documents;url=javascript:showProjectDocuments();';
    vvitems[vvitems.length] = 'text=Update Receipt Notes;url=javascript:getReceiptnotes();';
    // use oldLotStatus instead of old one.
    var status = cellValue(selectedRowId,'oldLotStatus');
    if ( cellValue(selectedRowId,"quantity") != "" && cellValue(selectedRowId,'qcDate') != "" && !(
         status == "Write Off Requested" || status == "Incoming" ) )
        vvitems[vvitems.length] = 'text=<fmt:message key="label.splitreceipt"/>;url=javascript:splitQty();';
    if( cellValue(selectedRowId,"radianPo") != "" && !disabledPoLink)
        vvitems[vvitems.length] = 'text=<fmt:message key="purchaseOrder"/>;url=javascript:purchaseOrder();';
    if( cellValue(selectedRowId,"permission") == "Y" )
        vvitems[vvitems.length] = 'text=<fmt:message key="writeonrequest.title"/>;url=javascript:writeOnRequest();';
    if(  cellValue(selectedRowId,"polchemIg") == 'Y' && cellValue(selectedRowId,"doNumberRequired") == 'N')
        vvitems[vvitems.length] = 'text=<fmt:message key="label.unitlabels"/>;url=javascript:unitLabelPartNumber();';
    // add new bin.
    vvitems[vvitems.length] = 'text=<fmt:message key="receiving.button.newbin"/>;url=javascript:checkaddbins();';
    // Why is it using hub name not hub id??
    <%-- moved to main page too. --%>
    <tcmis:inventoryGroupPermission indicator="true" userGroupId="addNewBin" facilityId="${param.hubName}">
        vvitems[vvitems.length] = "text=" + messagesData.createnewbin + ";url=javascript:addnewBin();";
    </tcmis:inventoryGroupPermission>

    if (cellValue(selectedRowId,"itemType") == 'ML' )
        vvitems[vvitems.length] = "text=" + messagesData.changeitem + ";url=javascript:changeMlItem();";
    if (cellValue(selectedRowId,'receivingStatus').toLowerCase() == 'binned' )
        vvitems[vvitems.length] = "text=" + messagesData.receivingQcCheckList + ";url=javascript:openChecklist();";

    //incoming lab testing add data to menu
    if (incomingTestOption.length > 0) {
        vvitems[vvitems.length] = incomingTestOption;
    }
    //incoming lab test for recert
    if (startMarsForRecert.length > 0) {
        vvitems[vvitems.length] = startMarsForRecert;
    }
    
	<tcmis:featureReleased feature="PrintGHSLabels" scope="${param.hubName}" companyId="HAAS">
	 vvitems[vvitems.length] = "text=" + messagesData.printGHSlabels + ";url=javascript:printGHSLabel();";
	</tcmis:featureReleased>
	
	if (!isWhitespace(cellValue(selectedRowId, "receiptId")) && !isWhitespace(cellValue(selectedRowId, "hub")))
		vvitems[vvitems.length] = "text=" + messagesData.addShippingSample + ";url=javascript:addShippingSample();";
		
	vvitems[vvitems.length] = "text=" + messagesData.printrtklabel + ";url=javascript:printRTKLabel();";
	
    replaceMenu('dropdown',vvitems);
    toggleContextMenu("dropdown");

} //end of method

function loadRightClickData(rowId) {
    var url = "rightmouseclickmenu.do?userAction=getIncomingTestRequired&receiptId="+cellValue(selectedRowId,"receiptId");
    callToServer(url+"&callback=processRightClickMenu");
}   //end of method

function processRightClickMenu(xmldoc) {
    incomingTestOption = '';
    startMarsForRecert = '';
    if (xmldoc.incomingTestRequired == 'Y') {
        incomingTestOption = "text=" + messagesData.startmarstest + ";url=javascript:startMARStest("+selectedRowId+");";
    }else if (xmldoc.testRequestId.length > 0) {
        incomingTestOption = "text=" + messagesData.showMarsDetail + ";url=javascript:showMarsDetail("+xmldoc.testRequestId+");";
        //allow user to create MARS request for recert if there already an existing test
        startMarsForRecert = "text=" + messagesData.startMarsTestForRecert + ";url=javascript:startMARStest("+selectedRowId+");";
    }
    buildRightClickOption();
}   //end of method

function startMARStest(selectedRowId) {
    var receiptId = cellValue(selectedRowId,"receiptId");
    var loc = "/tcmIS/haas/testrequestform.do?uAction=create&receiptId="+receiptId;
    try {
        parent.parent.openIFrame("testrequest" , loc, ""+messagesData.marsDetail+" "+ receiptId + "","","N");
    }catch (ex) {
        children[children.length] = openWinGeneric(loc, "testrequest" , "900", "600", "yes", "80", "80", "yes");
    }
} //end of method

function showMarsDetail (testRequestId) {
    tabId = "testRequestDetail_" + testRequestId,
	queryString = "?uAction=search&testRequestId=" + testRequestId,
	destination = "/tcmIS/haas/testrequestform.do" + queryString;
	try {
        parent.parent.openIFrame(tabId,destination, messagesData.marsDetail + " " + testRequestId,'','N');
    }catch (ex) {
		windowName = "testRequestDetail_" + requestId;
		children[children.length] = openWinGeneric(destination, windowName.replace('.', 'a'), "1000", "600", "yes", "50", "50", "20", "20", "no");
	}
} //end of method

function lotStatusChangedOk(rowId) {
    var result = true;
    var selectedLotStatus = cellValue(rowId,"lotStatus");
    for (var i = 0; i < pickableArr.length; i++) {
        if (cellValue(rowId,"incomingTesting") == 'Y') {
            if (selectedLotStatus == pickableArr[i] && cellValue(rowId,"labTestComplete") == 'N') {
                beanGrid.cellById(rowId, beanGrid.getColIndexById("lotStatus")).setValue(cellValue(rowId,"oldLotStatus"));
                alert(messagesData.noCompletedIncomingTest);
                result = false;
                break;
            }
        }
    }
    return result;
} //end of method

function openChecklist() {
	var receiptId = cellValue(selectedRowId, "receiptId");
	var now = new Date();
	var loc = "receivingqcchecklist.do?openerPage=rStatus&searchType=is&sort=Bin&searchWhat=Receipt%20Id&search=" + receiptId;
	loc += "&sourceHub=" + cellValue(selectedRowId, "hub");
	loc += "&opsEntityId=" + cellValue(selectedRowId, "opsEntityId");
	loc += "&startSearchTime=" + now.getTime();
	
	if ($v("personnelCompanyId") == 'Radian') 
		loc = "/tcmIS/hub/" + loc;
	
	openWinGeneric(loc, "receivingQcCheckList" + receiptId, "1000", "950", "yes", "80", "80");

}

function addShippingSample() {
	var receiptId = cellValue(selectedRowId, "receiptId");
	var loc = "shippingsample.do?receiptId=" + receiptId;
	loc += "&hub=" + cellValue(selectedRowId, "hub");
	
	if ($v("personnelCompanyId") == 'Radian') 
		loc = "/tcmIS/hub/" + loc;
	openWinGeneric(loc, "addShippingSample" + receiptId, "350", "200", "yes", "80", "80");
}


var gridConfig = {
		divName:'LogisticsBean', // the div id to contain the grid.
		beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:true,			 // this page has rowSpan > 1 or not.
		evenoddmap:lineMap3,
		noSmartRender:true,//false,
		submitDefault:true,    // the fields in grid are defaulted to be submitted or not.
		onRowSelect:selectRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		onRightClick:selectRow   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
//		onBeforeSorting:_onBeforeSorting
	};

<!-- permission stuff here --!>
showUpdateLinks = true;
var hdoroA = {};

function onselectHdoro(selectId, selectedOptVal) {
	hdoroA[selectId] = selectedOptVal;
}

first = true;
function eXcell_hdoro(cell){
	 if (cell){
	     this.cell = cell;
	     this.grid = this.cell.parentNode.grid;
	 };
	 this.setValue=function(val){
		var columnId = this.grid.getColumnId(this.cell._cellIndex);
		var pColIndex = null;
		if ((typeof multiplePermissions != 'undefined') && (typeof permissionColumns != 'undefined')) {
			if (typeof permissionColumns[columnId] != 'undefined') {
				pColIndex = this.grid.getColIndexById(columnId + 'Permission');
			} else {
				pColIndex = this.grid.getColIndexById('permission');
			}
		} else {
			pColIndex = this.grid.getColIndexById('permission');
		}
		var permission;
		if (pColIndex == undefined) {
			permission = 'N';
		} else {
			permission = this.grid.cellById(this.cell.parentNode.idd,pColIndex).getValue();
		}
		var selectId="" + columnId+ this.cell.parentNode.idd;
		if (permission == 'Y') {
//			hdoroA[selectId] = val;
			var selectValue = "<select class='selectBox' id='" + selectId + "' onchange=\"onselectHdoro('" +  selectId + "',this.options[this.selectedIndex].value);\">";
			try {
				selectValue = selectValue + "<option value='" + val +  "' selected='selected'>" + val + "</option>";
//				var optArray = eval(columnId);
//				for (i=0; i < optArray.length;i++) {
//					if (val == optArray[i]["value"])
//						selectValue = selectValue + "<option value='" + optArray[i]["value"] +  "' selected='selected'>" + optArray[i]["text"] + "</option>";
//					else
//						selectValue = selectValue + "<option value='" + optArray[i]["value"] +  "'>" + optArray[i]["text"] + "</option>";
//				}
				selectValue = selectValue + "</select>"
				this.setCValue(selectValue);
//
			} catch(err) {
				alert("Column with id " + columnId + " has type as hdoro but no global array named " + columnId +" is defined for the select options.");
				return;
			}
		} else {
			var optText = val;
			var columnId = this.grid.getColumnId(this.cell._cellIndex);
			try {
				var optArray = eval(columnId);
				for (i=0; i < optArray.length;i++) {
					if (val == optArray[i]["value"]) {
						optText =  optArray[i]["text"];
					}
				}
			} catch(err) {};
			if (optText.length == 0) {
				this.setCValue("<label id='" + selectId + "'>&nbsp;</label>",optText);
			} else {
				this.setCValue("<label id='" + selectId + "'>" + optText + "</label>",optText);
			}
		}
	 };
	 this.getValue=function(){
		var columnId = this.grid.getColumnId(this.cell._cellIndex);
		var pColIndex = null;
		if ((typeof multiplePermissions != 'undefined') && (typeof permissionColumns != 'undefined')) {
			if (typeof permissionColumns[columnId] != 'undefined') {
				pColIndex = this.grid.getColIndexById(columnId + 'Permission');
			} else {
				pColIndex = this.grid.getColIndexById('permission');
			}
		} else {
			pColIndex = this.grid.getColIndexById('permission');
		}
		var permission;
		if (pColIndex == undefined) {
			permission = 'N';
		} else {
			permission = this.grid.cellById(this.cell.parentNode.idd,pColIndex).getValue();
		}
		var selectId="" + columnId+ this.cell.parentNode.idd;
		if (permission == 'Y') {
			return $v(selectId);
		} else {
			return $(selectId).innerHTML.trim(); // get value
		}
	 };
	 this.edit=function(){};
	 this.detach=function(){
		 return false;
	 };
}
eXcell_hdoro.prototype = new eXcell;    // nest all other methods from base class

var loaded = new Array();
var loadedIndex = new Array();
var url = null;
function loadBins(itemId,hub,index)
{  // this page will only be in	 hub
   if( loadedIndex[index] ) {
	   return;
   }
   if( loaded[itemId] ) {
	   setBins(index,loaded[itemId]);
	   return;
   }
   
   url = "logisticsresults.do?uAction=loaddata&index="+index+"&itemId="+itemId+"&hub="+hub+"&callback=processReqChangeJSON";
   
   if ($v("personnelCompanyId") == 'Radian') 
	   url = "/tcmIS/hub/" + url;	
   
   callToServer(url);
}

function processReqChangeJSON(xmldoc)
{
    var bins = loaded[xmldoc.itemId] = xmldoc.bins;
    setBins(xmldoc.index,bins);
}

function setBins(index,bins) {
	var opts = $('bin'+index).options;
	var val  = $('bin'+index).value;
	for(var i = 0 ; i < bins.length; i++ ) {
		if( val != bins[i] ) opts[opts.length] = new Option(bins[i],bins[i]);
	}
	loadedIndex[index] = true;
}

function myGetCalendarMFG(rowid)
{
	return getCalendar($('mfgLabelExpireDateDisplay'+rowid),null,null,document.genericForm.todayoneyear,null,'Y');
}

function setMFGExpDateChanged(rowid) {
	var setDate = $v('mfgLabelExpireDateDisplay'+rowid);
	if(setDate == 'Indefinite')
		setDate = "01-" + month_abbrev_Locale_Java[pageLocale][0] + "-3000";
	cell(rowid,"mfgLabelExpireDate").setValue(setDate);
}
// end of dynamic load drop down code

//-->
</script>

</body>
</html>