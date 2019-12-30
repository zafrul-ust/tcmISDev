var beangrid;
var selectedRowId = null;
var windowCloseOnEsc = true;

var resizeGridWithWindow = true;

function resultOnLoad()
{
try{

 var creditHoldCount = $("creditHoldCount").value;
 

if (!showUpdateLinks || (creditHoldCount*1 == 0)) //Dont show any update links if the user does not have permissions
 {
  parent.document.getElementById("updateResultLink").style["display"] = "none";
 }
 else if (creditHoldCount*1 > 0)
 {
  parent.document.getElementById("updateResultLink").style["display"] = "";
 }
 
 }
 catch(ex)
 {}	
	
 
 totalLines = document.getElementById("totalLines").value;
 
 if (totalLines > 0)
 {
  document.getElementById("mrReleaseViewBean").style["display"] = "";
  
  initializeGrid();  
 }  
 else
 {
   document.getElementById("mrReleaseViewBean").style["display"] = "none";   
 }

 displayGridSearchDuration();
 
 /*It is important to call this after all the divs have been turned on or off.*/
 setResultFrameSize();
 
}

var lineMap3 = new Array();


function validateForm()
{
	return true;
}

function showMRDocuments()
{ 
	var prNumber = cellValue(beangrid.getSelectedRowId(),"prNumber");
	var companyId = cellValue(beangrid.getSelectedRowId(),"companyId");
	var inventoryGroup = cellValue(beangrid.getSelectedRowId(),"inventoryGroup");
	var loc = "/tcmIS/distribution/showmrdocuments.do?showDocuments=Yes&orderType=MR"+
	           "&prNumber="+prNumber+"&inventoryGroup="+inventoryGroup+"&companyId="+companyId+""+
	           "&opsEntityId="+encodeURIComponent(cellValue(selectedRowId,"opsEntityId"))+"";
	 try {
	 	parent.children[parent.children.length] = openWinGeneric(loc,"showAllMrDocuments","600","300","yes","80","80");
	 } catch (ex){
	 	openWinGeneric(loc,"showAllMrDocuments","450","300","no","80","80");
	 }
}

function allocationPopup(searchKey,auto) {
  parent.showTransitWin();

  var loc = 
		  "/tcmIS/distribution/allocation.do"+
		  "?companyId=" +encodeURIComponent(cellValue(selectedRowId,"companyId"))+
		  "&facilityId="+
		  "&itemId="+ cellValue(selectedRowId,"itemId")+
//		  "&refInventoryGroup="+pp('inventoryGroup')+
		  "&inventoryGroup="+encodeURIComponent(cellValue(selectedRowId,"inventoryGroup"))+
		  "&specList="+
		  "&specification="+ 
		  "&orderPrNumber="+encodeURIComponent(cellValue(selectedRowId,"prNumber"))+
		  "&shipToCompanyId="+
		  "&shipToLocationId="+
		  "&billToCompanyId="+
		  "&billToLocationId="+
		  "&curpath="+"MR "+ encodeURIComponent(cellValue(selectedRowId,"prNumber"))+
		  "&opsEntityId="+encodeURIComponent(cellValue(selectedRowId,"opsEntityId"))+
		  "&priceGroupId="+
		  "&promisedDate="+
          "&needDate="+encodeURIComponent(cellValue(selectedRowId,"needDate"))+
		  "&searchKey="+searchKey+
		  "&opsCompanyId="+encodeURIComponent(cellValue(selectedRowId,"opsCompanyId"))+ //Source_ops_Company_Id 
		  "&scratchPadLineItem=0"+
		  "&orderQuantity="+encodeURIComponent(cellValue(selectedRowId,"orderQuantity"))+  // quantity_needed
		  "&orderType=MR"+ 
//          "&previousPage="+encodeURIComponent("AllocationAnalysis")+ // What is this for
		  "&callbackparam="+encodeURIComponent(""+selectedRowId)+  // Guess don't need this
		  "&partDesc="+encodeURIComponent(cellValue(selectedRowId,"partDescription"))+
		  "&itemType="+
          "&unitOfMeasure="+ 	
		  "&labelSpec="+
		  "&incoTerms="+
		  "&remainingShelfLifePercent="+
          "&shipComplete="+ 
		  "&consolidateShipment="+
		  "&specDetailList="+
		  "&specLibraryList="+ 
		  "&specCocList="+
		  "&specCoaList="+
		  "&currencyId="+
		  "&shipped=0"+ 
          "&pickList=0";

//	  children[children.length] = 
	  openWinGeneric(loc, "AllocationDetail_"+encodeURIComponent('transferRequestId').replace(/[.]/,"_"), "1024", "500", "yes", "50", "50");

}


// MR Release doesn't call this function
function selectRightclick(rowId,cellInd){
	menuChoice = cellValue(beangrid.getSelectedRowId(),"menuChoice");
	if (menuChoice == '3' && cellValue(beangrid.getSelectedRowId(),"qualityHold") == 'Y')
		menuChoice = '4';
    toggleContextMenu('menu'+menuChoice);
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


function initializeGrid(){
	beangrid = new dhtmlXGridObject('mrReleaseViewBean');

	initGridWithConfig(beangrid,config,true,true);
	//beangrid.setEvenoddmap(lineMap3);
	beangrid.enableSmartRendering(true);

	beangrid._haas_row_span = true;
	beangrid._haas_row_span_map = lineMap;
	beangrid._haas_row_span_class_map = lineMap3;
	beangrid._haas_row_span_cols = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19];

	if( typeof( jsonMainData ) != 'undefined' ) {		
		
		beangrid.parse(jsonMainData,"json");
	}
	
	beangrid.attachEvent("onRightClick",selectRow);
	beangrid.attachEvent("onRowSelect",selectRow);
}


function openMr() {
	var parNumber = null;
	prNumber = cellValue(beangrid.getSelectedRowId(),"prNumber");
	var loc = "/tcmIS/distribution/scratchpadmain.do?uAction=searchScratchPadId&scratchPadId="+prNumber+"&tabId="+encodeURIComponent('scratchPad'+prNumber+'');

	 try	
		{
			parent.parent.openIFrame("scratchPad"+prNumber+"",loc,"MR "+prNumber+"","","N");
		}
		catch (ex)
		{
			openWinGeneric(loc,"scratchPad"+prNumber,"900","600","yes","80","80","yes");	
		}
		
}

function printPerforma() {
    var prNumber = null;
	var companyId = null;
	prNumber = cellValue(beangrid.getSelectedRowId(),"prNumber");
	companyId = cellValue(beangrid.getSelectedRowId(),"companyId");

    var loc = "/HaasReports/report/printdistproformainvoice.do?prNumber="+prNumber;
    openWinGeneric(loc,"PrintSalesorder"+$v("prNumber").replace('.','a'),"800","600","yes","80","80","yes");
}

function acceptMR() {
	
	var parNumber = null;
	var companyId = null;
	prNumber = cellValue(beangrid.getSelectedRowId(),"prNumber");
	companyId = cellValue(beangrid.getSelectedRowId(),"companyId");	
	
	if( (null!=prNumber && ''!=prNumber) && (null!= companyId&& ''!=companyId))
	{	
		var flag = true;
		mymap = lineIdMap[rowId0];
		for(var j = 0;j < mymap.length; j++) {
			flag = validatDate(mymap[j]); 
			if(flag == false) break;
		}
		
		if(flag) {
			document.mrrelease.target='';
			$('prNumber').value = prNumber;
			$('companyId').value = companyId;
			
			if (beangrid != null) 
	       		beangrid.parentFormOnSubmit();
	
			document.getElementById('action').value = 'acceptMR';
			parent.showPleaseWait();
			var now = new Date();
			parent.document.getElementById("startSearchTime").value = now.getTime();
			document.mrrelease.submit();
		}
	}
}

function releaseMRLine() {

	var parNumber = null;
	var companyId = null;
	var lineItem = null;
	prNumber = cellValue(beangrid.getSelectedRowId(),"prNumber");
	companyId = cellValue(beangrid.getSelectedRowId(),"companyId");	
	lineItem = cellValue(beangrid.getSelectedRowId(),"lineItem");	
//	promiseDate = cellValue(beangrid.getSelectedRowId(),"promiseDate");		
	
	if( (null!=prNumber) && (null!= companyId) && validatDate(beangrid.getSelectedRowId()))
	{	
		
		document.mrrelease.target='';
		$('prNumber').value = prNumber;
		$('companyId').value = companyId;
		$('lineItem').value = lineItem;
						
//		if(promiseDate != cellValue(beangrid.getSelectedRowId(),"originalPromiseDate")){
//			$('promiseDate').value = promiseDate;
//		}	
		
		document.getElementById('action').value = 'releaseQualityHold';
		parent.showPleaseWait();
		var now = new Date();
		parent.document.getElementById("startSearchTime").value = now.getTime();
		document.mrrelease.submit();
	}
}

function releaseForceHold() {
	
	var parNumber = null;
	var lineItem = null;
	prNumber = cellValue(beangrid.getSelectedRowId(),"prNumber");
	lineItem = cellValue(beangrid.getSelectedRowId(),"lineItem");	
//	promiseDate = cellValue(beangrid.getSelectedRowId(),"promiseDate");		
	
	if( (null!=prNumber) )
	{	
		
		document.mrrelease.target='';
		$('prNumber').value = prNumber;
		$('lineItem').value = lineItem;
						
		document.getElementById('action').value = 'releaseForceHold';
		parent.showPleaseWait();
		var now = new Date();
		parent.document.getElementById("startSearchTime").value = now.getTime();
		document.mrrelease.submit();
	}
}


function releaseExpertReviewHold() {
	var parNumber = null;
	var lineItem = null;
	prNumber = cellValue(beangrid.getSelectedRowId(),"prNumber");
	lineItem = cellValue(beangrid.getSelectedRowId(),"lineItem");	
//	promiseDate = cellValue(beangrid.getSelectedRowId(),"promiseDate");		
	
	if( (null!=prNumber) )
	{	
		
		document.mrrelease.target='';
		$('prNumber').value = prNumber;
		$('lineItem').value = lineItem;
						
		document.getElementById('action').value = 'releaseExpertReviewHold';
		parent.showPleaseWait();
		var now = new Date();
		parent.document.getElementById("startSearchTime").value = now.getTime();
		document.mrrelease.submit();
	}
}


function validatDate(rowId) {
/*	promiseDate = cellValue(rowId,"promiseDate");	
	
	if(promiseDate == '') {
		alert(messagesData.inputDate);
		return false;
	} else   */
		return true;
}

function cancelMR() {
	
	/*Set any variables you want to send to the server*/
	var parNumber = null;
	var lineItem = null;
	prNumber = cellValue(beangrid.getSelectedRowId(),"prNumber");
	lineItem = cellValue(beangrid.getSelectedRowId(),"lineItem");	
	
	if( (null!=prNumber) && (null!= lineItem))
	{	
		showNotes(messagesData.cancelmrreason,'CancelMRDiv');
	}
	
}

function cancelMRReasonOkClose() {
   $("cancelMRReason").value = $v("cancelMRReasonAreaUSE");
   dhxNoteWins.window(messagesData.cancelmrreason).setModal(false);
   dhxNoteWins.window(messagesData.cancelmrreason).hide();
   
   $('prNumber').value = cellValue(beangrid.getSelectedRowId(),"prNumber");
   $('lineItem').value = cellValue(beangrid.getSelectedRowId(),"lineItem");	
		
	document.mrrelease.target='';
	document.getElementById('action').value = 'cancel';
	parent.showPleaseWait();
	var now = new Date();
	parent.document.getElementById("startSearchTime").value = now.getTime();
	document.mrrelease.submit();
}

function cancelMRReasonClear() {
   $("cancelMRReasonAreaUSE").value = '';
   $("cancelMRReason").value = '';
}

function limitText(id, label, limitNum) {
    var limitCount;
    var limitField  =  document.getElementById(id);
// alert(limitField.value.length);
	if (limitField.value.length > limitNum) {
		limitField.value = limitField.value.substring(0, limitNum);
		var lengthMsg = messagesData.maxlength;
		lengthMsg = lengthMsg.replace(/[{]0[}]/g,label);
		lengthMsg = lengthMsg.replace(/[{]1[}]/g,limitNum);
		alert(lengthMsg);
	} 
}
