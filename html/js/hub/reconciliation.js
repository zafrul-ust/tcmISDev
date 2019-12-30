var myJasonArr = new Array();
var hubId;
var actualQuantity_hedFunction = "onChange= checkActualOnHand";
var dhxWins = null;
var beangrid;
var children = new Array();

var resizeGridWithWindow = true;


function submitSearchForm()
{
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();
  
  if(isValidSearchForm()) {
   $('uAction').value = 'search';
   document.reconciliationForm.target='resultFrame';
   showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}
function isValidSearchForm() 
{
	var errorMessage = "";
	var errorCount = 0;
	
	var countDateString  =  document.getElementById("countDateString").value;	
	var searchType  =  document.getElementById("countType").value;	
	
	var searchInput = document.getElementById("searchArgument");  	
	var searchField = document.getElementById("searchField");

	if($('startNewCountBtn') == null && $('countDateString') != null && $v('countDateString').length == 0) {
		return false;
	}	
	else if( $('startNewCountBtn') != null && $('closeCountBtn') != null &&
		$('startNewCountBtn').style.visibility == 'hidden' && $('closeCountBtn').style.visibility == 'hidden'&& $v('countDateString').length == 0)
	{
		return false;
	}
	else if( $('startNewCountBtn') != null && $('closeCountBtn') != null &&
		$('startNewCountBtn').style.visibility == 'hidden' && $('closeCountBtn').style.visibility == 'hidden')
	{
	
	}	
	else
	{
	  	if(countDateString  == '')
	  	{
	  		 errorMessage +=messagesData.mustselectcountdate+ "\n\n";
	  		 errorCount ++;
	    	
	  	}
	}
  	var opsEntityId = document.getElementById("opsEntityId");
  	if(opsEntityId.value.trim().length == 0) 
	{ 
	     errorMessage += messagesData.missingoperatingentity+"\n\n";
	     errorCount ++;
	}
   /* if(searchType == 'Single Item' && !isInteger(searchArgument.value.trim(), false)) 
    {
    		document.getElementById("searchArgument").value = '';
    		alert(messagesData.itemInteger);
    		return false;
  	}*/
  	
  	 if ((searchField.value.trim().length != 0 ) && (  (searchField.value == "itemId") || (searchField.value == "receiptId")      ) )			
	 {
	    if((searchInput.value.trim().length != 0) && (!(isPositiveInteger(searchInput.value.trim())) || (searchInput.value*1 == 0 )  ))
	    { 
	     errorMessage +=  messagesData.searchInput+ "\n";
	     errorCount ++;
	     searchInput.value = "";
	 
	    }
	 }	
  	
  	if (errorCount >0)
	 {
	    alert(errorMessage);
	    return false;
	 }	
   return true;
}

function createXSL()
{
  if(isValidSearchForm()) {
	$('uAction').value = 'createExcel';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_ReconciliationExcel','650','600','yes');
    document.reconciliationForm.target='_ReconciliationExcel';
    var a = window.setTimeout("document.reconciliationForm.submit();",500);
  }
}


function buildDropDownNew( arr, defaultObj, eleName ) {
   var obj = $(eleName);
   for (var i = obj.length; i >= 0;i--)
     obj[i] = null;
  // if size = 1 or 2 show last one, first one is all, second one is the only choice.
  if( arr == null || arr.length == 0 ) 
	  return ;//	  setOption(0,defaultObj.name,defaultObj.id, eleName); 
  else if( arr.length == 1 )
	  setOption(0,arr[0].name,arr[0].id, eleName);
  else {
      var offset = 0 ;
      if( defaultObj != null ) {
	  	setOption(0,defaultObj.name,defaultObj.id, eleName); 
	  	offset = 1;
	  }
	  for ( var i=0; i < arr.length; i++) {
	    	setOption(i+offset,arr[i].name,arr[i].id,eleName);	      
	  }
  }
  obj.selectedIndex = 0;
}


function opsValChanged()
{  
   var opsO = $("opsEntityId");
   var arr = null;
   if( opsO.value != '' ) {
   	   for(i=0;i< opshubig.length;i++)
   	   		if( opshubig[i].id == opsO.value ) {
   	   			arr = opshubig[i].coll;
   	   			break;
   	   		}
   }
   
   if( opsO.value != '' )
   		buildDropDownNew(arr,null,"hub");
   else
   		buildDropDown(arr,defaults.hub,"hub");

   hubValChanged();
   //if( defaults.ops.callback ) defaults.ops.callback();
}

function showRoomOptions(selectedHub) 
{
  var roomArray = new Array();
  roomArray = altRoomId[selectedHub];

  var roomNameArray = new Array();
  roomNameArray = altRoomName[selectedHub];

  if(roomArray != null && roomArray.length == 0) 
  {
    setOption(0, messagesData.none, "", "room")
    setOption(1, "No Room", "No Room", "room")
  }

  var roomO = document.getElementById("room");

  for (var i = roomO.length; i > 0; i--) 
  {
    roomO[i] = null;
  }
  
  if (roomArray != null)
  {
	  for (var i=0; i < roomArray.length; i++) 
	  {
	    if (i ==0 )
        {
           setOption(i,roomNameArray[i],roomArray[i], "room")
        }
        else if (i ==1)
        {
            setOption(1, "No Room", "No Room", "room")
            setOption(i+1,roomNameArray[i],roomArray[i], "room")
        }
        else
        {
            setOption(i+1,roomNameArray[i],roomArray[i], "room")
        }

      }
  }
}

function startNewCount() {
	 
	var hub = document.getElementById("hub").value; 
	if ( hub.trim().length == 0 ) {
		alert(messagesData.choosehubbeforestartnewcount);
		return false;
	}
	else
	{ 
		showTransitWin(messagesData.startnewcount);
		var  loc = "addCountDate.do?uAction=showAddCountDate&hub="+hub;	
		openWinGeneric(loc, "Add_Date_Count" + "", "430", "150", "yes", "50", "50");
	}
}

function closeCount() {
	var hub = document.getElementById("hub").value;
    var countId = document.getElementById("countId").value;
    if ( hub.trim().length == 0 ) {
		alert(messagesData.choosehubbeforestartnewcount);
		return false;
	}
	else if (countId.trim().length > 0)
	{
	    var countDateString = document.getElementById("countDateString").value;
		showTransitWin(messagesData.closecount);
		callToServer("reconciliationmain.do?uAction=setCountDateClose&hub="+hub+"&countDateString="+countDateString+"&callback=closeTransitWin&countId="+$v('countId'));
    }
}

function createPdf() {
	var inventoryGroup = "";
	var inventoryGroupName = "";
	for ( var i = 1; i < document.getElementById("inventoryGroup").length; i++) {
		if ($("inventoryGroup").options[i].selected == true || $("inventoryGroup").options[0].selected == true) {
			inventoryGroup += "&invGrp=" + $("inventoryGroup").options[i].value;
			inventoryGroupName += "&invGrpName="
					+ $("inventoryGroup").options[i].text;
		}
	}


	var selectedLotStatus = "";
	for ( var i = 0; i < document.getElementById("lotStatus").length; i++) {
		if ($("lotStatus").options[i].selected == true) {
			selectedLotStatus += "&lotStatus=" + $("lotStatus").options[i].value;
		}
	}

	var includeLotStatus = "&includeLotStatus=";
	var isInclude = document.getElementById("radiobutton_0").checked;
	if (isInclude == true)
	{
		includeLotStatus = includeLotStatus + "Inclusive"; 
	}
	else
	{
		includeLotStatus = includeLotStatus + "Exclusive"; 
	}
	var loc = "/HaasReports/report/printInventoryReconciliation.do?hub="
			+ $v("hub") + inventoryGroup + inventoryGroupName + "&hubName="
			+ $v("hubName") + "&countId=" + $v("countId") + "&pbmReceipt="
			+ $("checkbox").checked + "&searchAttribute=" + $v("searchField")
			+ "&searchOperator=" + $v("searchMode") + "&searchValue="
			+ $v("searchArgument") + "&personnelId=" + $v("personnelId")
			+ "&companyId=" + $v("companyId") + "&room=" + $v("room")
			+ "&binFrom=" + $v("binFrom") + "&binTo=" + $v("binTo")
			+ "&printRecordedOnHand=" + $("printOnHandCheckbox").checked 
			+ selectedLotStatus + includeLotStatus  
			+ "&locale=" + pageLocale;
	openWinGeneric(loc, "PrintInventoryReconciliation", "800", "600", "yes",
			"50", "50", "20", "20", "yes");
	return false;
}

function searchTypeChanged() {
   
}

// to do 
function isGridValid() {
   rowId0 = arguments[0];
   colId0 = arguments[1];
   
   if(!isInteger(cellValue(rowId0, mygrid.getColIndexById("actualQuantity")), true)) 
    {
	   		beangrid.cells(rowId0, mygrid.getColIndexById("actualQuantity")).setValue(0);
    		alert(messagesData.itemInteger);
    		return false;
  	}
	return true;
}

function updateData() 
{
	  $('uAction').value = 'update';
	  parent.showPleaseWait();
	  beangrid.parentFormOnSubmit();	
	  parent.document.reconciliationForm.target='resultFrame';
	  window.setTimeout("document.reconciliationForm.submit();",500);

}


function setCountDate()
{
	setOption(0,"","", "countDateString");
	
	$('createPdfBtn').style.visibility = 'hidden';	
}

function countDateUpdateAjaxCall()
{

	var hubVal = document.getElementById("hub").value;
	var valueExists = false; 
	if(typeof(hubVal)!='undefined')
	{	
		resetDropDown();

		if(hubVal!="")
		{	  
			hubId = hubVal;			
			for (var i = 0; i < myJasonArr.length; i++) 
			{
				if( myJasonArr[i].id == hubId)
				{
					updateCountDateDropDown(myJasonArr[i].arr);
					valueExists = true;
					break;
				} 	  
			}
//alert("hubVal:"+hubVal+" uAction:" +$("uAction").value );
			if(!valueExists && $("uAction").value != "setCountDateClose" )
			{	
				callToServer("reconciliationmain.do?uAction=getCountDateDropdown&hub="+hubVal);
			}

		}		
	}
}


function attachCountDateUpdate()
{
	defaults.hub.callback = countDateUpdateAjaxCall;
	$('opsEntityId').onchange = opsValChanged;
}


function resetDropDown()
{
	var dropDown  = $("countDateString");
	for (var i = dropDown.length; i > 0;i--)
	{	  
		dropDown[i] = null;
	}	  
	setCountDate();
	$('countStartedByName').value = "";
	$('countType').value ="";
	$('countId').value ="";
}


function updateCountDateDropDown(jsonObj, deletedCountId)
{
	var tmpJasonArray	
	var dontAddVal = false;
/*	if(jsonObj.length>0)
	{	 
		if(typeof(tmpJasonArray)!='undefined')
		{	
			for (var i = 0; i < tmpJasonArray.length; i++)
			{	
				if(tmpJasonArray[i].id == hubId)	
				{
					tmpJasonArray[i]={id:hubId,arr:jsonObj};
					dontAddVal = true;
				}	
			}

			if(!dontAddVal)
			{	
				tmpJasonArray[tmpJasonArray.length]={id:hubId,arr:jsonObj};
				tmpJasonArray.length++;
			}
		}	
		else
		{			
			tmpJasonArray= new Array({id: hubId, arr: jsonObj});

		}

		myJasonArr = tmpJasonArray;
	}*/
	resetDropDown();	
	for (var i = 0; i < jsonObj.length; i++) 
	{	//alert("i:"+i+" jsonObj.length: "+jsonObj.length+"--"+jsonObj[i].countDateDateValue);
		setOption(i,jsonObj[i].countDateDateValue,jsonObj[i].countId,"countDateString");
		setOption(i,jsonObj[i].countId,jsonObj[i].countType,"countTypeDropDown");	
		setOption(i,jsonObj[i].countStartedByName,jsonObj[i].countStartedByName,"countStartedByDropDown");	
	}
	
	if(jsonObj.length>0)
	{	
		$('countType').value =jsonObj[0].countType;
		$('countId').value =jsonObj[0].countId;
		$('inputDate').value =jsonObj[0].countDateDateValue;
		$('createPdfBtn').style.visibility = 'visible';
		$('countStartedByName').value =jsonObj[0].countStartedByName;
		if ($('countType').value == "Cycle Count") {
			$('skipCountedCheckbox').disabled = false;
		}
		else {
			$('skipCountedCheckbox').checked = false;
			$('skipCountedCheckbox').disabled = true;
		}
	}

	if( typeof(deletedCountId) != 'undefined' && deletedCountId != "" && self.resultFrame.document.getElementById("countId") != null && deletedCountId == self.resultFrame.document.getElementById("countId").value) {
		document.getElementById("resultFrame").src = "/blank.html";	
		$('resultGridDiv').style.display = "none";
	}
}

function checkActualOnHand(rowId,cellInd)
{
   var onhandValue = cellValue(rowId,cellInd);
 
   var errorMessage = messagesData.validvalues+"\n\n";
   var errorCount = 0;   
   try {
	    if((onhandValue.length != 0) && !((cellValue(rowId,'nonIntegerReceiving') == 'Y' && onhandValue >= 0.0 ) || (isPositiveInteger(onhandValue) ||   onhandValue == 0)))
	    {     
	     errorMessage +=  messagesData.actualQuantity + "\n";
	     errorCount = 1;   
	     beangrid.selectCell(beangrid.getRowById(rowId), beangrid.getColIndexById("actualQuantity"), null,false, false, true);
	     beangrid.cellById(rowId,beangrid.getColIndexById("actualQuantity")).setValue("");         
	    } 
   }catch (ex) {	 
	  errorCount++;
   }
   
   if (errorCount >0) {
	    alert(errorMessage);	  
   }	
}


function initializeDhxWins() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function showTransitWin(messageType)
{
	var waitingMsg = messagesData.waitingforinputfrom+"...";
	var transitLabel  = document.getElementById("transitLabel");
	var transitDailogWin = document.getElementById("transitDailogWin");

	transitLabel.innerHTML =waitingMsg.replace(/[{]0[}]/g,messageType);
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


function updateCountType(countType)
{
	
	$('countType').value=countType;
	
/*	if (countType=="Single Item") {
    document.getElementById("searchArgument").style.display="";
  }	 else {
    document.getElementById("searchArgument").style.display="none";
  }*/
}


function countDropDownChanged()
{
	var countDropDown =$v('countDateString');
	var dateDropDown =  $('countDateString');
	var countTypeDown =  $('countTypeDropDown');
	var countStartedByDropDown =  $('countStartedByDropDown');
	var countTypeVal;
	var countIdVal;
	var countStartedBy;
	for(i=0;i< countTypeDown.length;i++)		
	{		
		if( countTypeDown[i].text == countDropDown ) {
	   	countTypeVal = countTypeDown[i].value;
	   	countIdVal = countTypeDown[i].text;
	   	countStartedBy = countStartedByDropDown[i].value;
	   	break;
	   	}
	} 
	$('countStartedByName').value = countStartedBy;
	$('countType').value=countTypeVal;
	$('countId').value=countIdVal;
	$('inputDate').value=dateDropDown.options[dateDropDown.selectedIndex].text;
	
//alert("countDropDown"+countDropDown+"     countDateString"+$v("countDateString"));	
	if(countDropDown  == '')
	    $('createPdfBtn').style.visibility = 'hidden';
	else 
	    $('createPdfBtn').style.visibility = 'visible';
	
	if ($('countType').value == "Cycle Count") {
		$('skipCountedCheckbox').disabled = false;
	}
	else {
		$('skipCountedCheckbox').checked = false;
		$('skipCountedCheckbox').disabled = true;
	}
		  		 
}	

function setLinks()
{
	var dateDropDown =  parent.$('countDateString');
	parent.$('spanid1').innerHTML = "&nbsp;"+ dateDropDown.options[dateDropDown.selectedIndex].text+"&nbsp;";
	parent.$('spanid2').innerHTML = "&nbsp;"+ parent.$v('countType');
	
	if( parent.$('spanid3').style["display"]=="")
		parent.$('spanid4').innerHTML = "&nbsp;"+ $v('approvedBy');

	if($v('approvedBy') == "" && (parent.$v('countType') == "All Inventory" || parent.$v('countType') == "Item")) 
		parent.$('addReceiptLink').style["display"]=""; 
	else 
		parent.$('addReceiptLink').style["display"]="none"; 
}

function initializeGrid(){
	 beangrid = new dhtmlXGridObject('ReconciliationBean');

	 initGridWithConfig(beangrid,config,false,false);
	 
	 if( typeof( jsonMainData ) != 'undefined' )
	 	beangrid.parse(jsonMainData,"json");
	 	
	 beangrid.attachEvent("onRightClick",selectRightclick);
}

function selectRightclick(rowId,cellInd){
	// Show right-click menu
	if(showMenu ==  "Y")
		toggleContextMenu('rightClickMenu');
}


function resultOnLoad()
{
 try{
	
	 if (!showUpdateLinks)	 
		 parent.$("updateResultLink").style["display"] = "none";
	 else
     {
         parent.$("updateResultLink").style["display"] = "";
         if( parent.$v('countType') == "All Inventory" || parent.$v('countType') == "Item")
         {
             parent.$('addReceiptLink').style["display"]=""; 
         }
     }

        if (showApproveUpdateLinks)
		 parent.$("approveLink").style["display"] = "";
	 else
		 parent.$("approveLink").style["display"] = "none";

     if((null!=countApprovalStatus) && (countApprovalStatus=="Approved") )
	 {
		 parent.$("approveLink").style["display"] = "none";
		 parent.$('spanid3').style["display"] = "";
		 parent.$('spanid4').style["display"] = "";
		 parent.$("updateLink1").style["display"] = "none";
		
	 }
	 else
	 {
		 //parent.$("approveLink").style["display"] = "";
		 parent.$('spanid3').style["display"] = "none";
		 parent.$('spanid4').style["display"] = "none";
		 //parent.$("updateLink1").style["display"] = "";
	 }
 }
 catch(ex)
 {}

 totalLines = document.getElementById("totalLines").value;
 
 if (totalLines > 0)
 {
	  document.getElementById("ReconciliationBean").style["display"] = "";
	  initializeGrid();  
 }  
 else
 {
   	document.getElementById("ReconciliationBean").style["display"] = "none";   
 }

 displayGridSearchDuration();
 
 /*It is important to call this after all the divs have been turned on or off.*/
 setResultFrameSize();
}


function approveData() 
{
	var answer;

	answer = confirm(messagesData.countapprovealert);	
	if(answer==true)
	{
		$('uAction').value = 'approve';
		parent.showPleaseWait();  
		beangrid.parentFormOnSubmit();	
		parent.document.reconciliationForm.target='resultFrame';
		window.setTimeout("document.reconciliationForm.submit();",500);
	}	
	
}

function addReceipts(){
    var opsEntityId = $v("opsEntityId");
	var opsEntityName = $v("opsEntityName");
	var hub = $v("hub");
	var inventoryGroup = $v("inventoryGroup");

	loc = "/tcmIS/distribution/inventorylookupmain.do?opsEntityId="+encodeURIComponent(opsEntityId)+"&hub="+encodeURIComponent(hub)+"&inventoryGroup="+encodeURIComponent(inventoryGroup)+"&countId="+$v("countId")+"&fromReconciliation=Y";
  
	openWinGeneric(loc,"InventoryLookUp","950","450","yes","50","50","20","20","no");
}


function setOpsValues() {
    buildDropDown(opshubig,defaults.ops,"opsEntityId");
 	$('opsEntityId').onchange = opsChanged;
    $('hub').onchange = hubValChanged;
    if(defaultOpsEntityId != null && defaultOpsEntityId.length > 0){
    	$('opsEntityId').value = defaultOpsEntityId;
    }
    opsChanged();
    if(defaultOpsEntityId != null && defaultOpsEntityId.length > 0 && defaultHub != null && defaultHub.length > 0) {
    	$('hub').value = defaultHub;
    	hubValChanged();
    }
    if(defaultOpsEntityId != null && defaultOpsEntityId.length > 0 && defaultHub != null && defaultHub.length > 0 && preferredInventoryGroup != null && preferredInventoryGroup.length > 0) {
    	$('inventoryGroup').value = preferredInventoryGroup;
    }
}


function hubValChanged()
{
   var opsO = $("opsEntityId");
   var hubO = $("hub");
   var arr = null;
   if( opsO.value != '' && hubO.value != '' ) {
   	   for(i=0;i< opshubig.length;i++)
   	   		if( opshubig[i].id == opsO.value ) {
		   	   for(j=0;j< opshubig[i].coll.length;j++)
	   	   		if( opshubig[i].coll[j].id == hubO.value ) {
	   	   			arr = opshubig[i].coll[j].coll;
	   	   			break;
   	   		    }
   	   		 break;
   	   		}
   }
   
   $('hubName').value = hubO.options[hubO.selectedIndex].text;
     
   checkBinPermission($('hubName').value);
   
   buildDropDown(arr,defaults.inv,"inventoryGroup");
   showRoomOptions($v("hub"));
   
   if( defaults.hub.callback ) defaults.hub.callback();

}

function alertSuccess() {
	alert(messagesData.addedreceiptsuccessfully);
}


function checkBinPermission(selectedHub) {
	 
	  //var hubName = opshubig[selectedHub]
	  var found = false; 

	  for(var i=0; i < facilityPermissionArray.length; i++) {
	    if(facilityPermissionArray[i] == selectedHub) {
	      found = true;
	    }
	  }
	  if(found) {
	    try {	    
		    $('startNewCountBtn').style.visibility = 'visible';
		    $('closeCountBtn').style.visibility = 'visible';
		} catch(ex) {}    
	  }
	  else {
	    try {	    
	    	$('startNewCountBtn').style.visibility = 'hidden';
	    	$('closeCountBtn').style.visibility = 'hidden';
	    } catch(ex) {}
	  }
	}

function showVvHubBins() {
	var rowNumber = beangrid.getSelectedRowId();
	var itemId = cellValue(rowNumber,"itemId");
	var branchPlant =  document.getElementById("hub").value;
	
	var loc = "/tcmIS/hub/showhubbin.do?branchPlant=" + branchPlant
			+ "&userAction=showBins&pageId=recon&rowNumber=" + rowNumber + "&itemId=" + itemId;

	try {
		children[children.length] = openWinGeneric(loc, "showVvHubBins", "300",
				"150", "no", "80", "80");
	} catch (ex) {
		children[children.length] = openWinGeneric(loc, "showVvHubBins", "300", "150", "no", "80", "80");
	}
	parent.showTransitWin(messagesData.pickNewBin);
}

function closeAllchildren()
{
// if (document.getElementById("uAction").value != 'new') {
 try {
  for(var n=0;n<children.length;n++) {
   try {
    children[n].closeAllchildren();
    } catch(ex){}
   children[n].close();
   }
  } catch(ex){}
  children = new Array();
// }
}
function updateRoom(r)
{
	alert("in receiving");
}
    
function changeSearchTypeOptions(selectedValue)
{
	  var searchType = $('searchMode');
	  for (var i = searchType.length; i > 0;i--)
		  searchType[i] = null;

	  var actuallyAddedCount = 0;
	  for (var i=0; i < searchMyArr.length; i++) 
	  {
		    if((selectedValue == 'itemId' || selectedValue == 'receiptId') && (searchMyArr[i].value == 'contains' || searchMyArr[i].value == 'endsWith'))
		    	continue;
		    else if (selectedValue == 'catPartNo' && (searchMyArr[i].value == 'is' || searchMyArr[i].value == 'endsWith'))
		    	continue;
		    setOption(actuallyAddedCount,searchMyArr[i].text,searchMyArr[i].value, "searchMode")
		    if (selectedValue == 'bin' && searchMyArr[i].value == 'is')
		    	searchType.selectedIndex = actuallyAddedCount;
		    actuallyAddedCount++;
	  }
}

