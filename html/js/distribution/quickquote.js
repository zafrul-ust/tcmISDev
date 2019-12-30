function lookupCustomer() {    
  loc = "../distribution/customerlookupsearchmain.do?popup=Y&displayElementId=customerName&valueElementId=customerId";  
  openWinGeneric(loc,"customerlookup","800","500","yes","50","50","20","20","no"); 
}

function customerChanged(id, name) {
	if($v("itemId").length > 0) {
    	getSuggestedPrice();
    	submitSearch();
    }
}

function clearCustomer() {
    document.getElementById("customerName").value = "";
    document.getElementById("customerName").className = "inputBox";
    document.getElementById("customerId").value = "";
    
    if($v("itemId").length > 0) {
    	getSuggestedPrice();
    	submitSearch();
    }
    
    return false;
}

function customerChanged(id, name) {
	document.getElementById("customerName").className = "inputBox";
}

function clearItemSearch() {
	$("searchPartNumberMode").value = "";
	$("partNumber").value = "";
	$("searchCustomerPartNumberMode").value = "";
	$("customerPartNumber").value = "";
	$("searchPartDescMode").value = "";
	$("partDesc").value = "";
	$("searchTextMode").value = "";
	$("text").value = "";
	$("searchSpecificationMode").value = "";
	$("specification").value = "";
	$("searchAlternateMode").value = "";
	$("alternate").value = "";
}

function submitSearch() {
	
	if (validateSearchCriteriaInput()) {
		$("uAction").value = 'search';
		document.genericForm.target = 'resultFrame';
		
		try {
			$('transitPage').style.display='';
		} catch(ex) {}
		
		document.genericForm.submit();
/*		
		var loc = "quickquoteresults.do?uAction=search&searchKey="+$v("searchKey")+"&region=&itemId="+$v("itemId")+
				  "&inventoryGroup="+$v("inventoryGroup")+"&opsEntityId="+$v("opsEntityId")+"&customerId="+$v("customerId");
		window.frames["resultFrame"].location = loc;
*/		
		return false;
	}
	else {
		return false;
	}
}

function lookupItem() {
	if($v("opsEntityId").length == 0) {
		alert(messagesData.pleaseinput.replace(/[{]0[}]/g,messagesData.operatingentity));
		return false;
	} else {
		var loc = "quickitemsearchmain.do?inventoryGroup=" + encodeURIComponent($v("inventoryGroup"))+"&opsEntityId="+ encodeURIComponent($v("opsEntityId"));
		if($v("searchPartNumberMode").length > 0)
			loc += "&searchPartNumberMode=" + encodeURIComponent($v("searchPartNumberMode"));
		if($v("itemId").length > 0)
			loc += "&partNumber=" + encodeURIComponent($v("itemId"));
		if($v("searchCustomerPartNumberMode").length > 0)
			loc += "&searchCustomerPartNumberMode=" + encodeURIComponent($v("searchCustomerPartNumberMode"));
		if($v("customerPartNumber").length > 0)
			loc += "&customerPartNumber=" + encodeURIComponent($v("customerPartNumber"));
		if($v("searchPartDescMode").length > 0)
			loc += "&searchPartDescMode=" + encodeURIComponent($v("searchPartDescMode"));
		if($v("partDesc").length > 0)
			loc += "&partDesc=" + encodeURIComponent($v("partDesc"));
		if($v("searchTextMode").length > 0)
			loc += "&searchTextMode=" + encodeURIComponent($v("searchTextMode"));
		if($v("text").length > 0)
			loc += "&text=" + encodeURIComponent($v("text"));
		if($v("searchSpecificationMode").length > 0)
			loc += "&searchSpecificationMode=" + encodeURIComponent($v("searchSpecificationMode"));
		if($v("specification").length > 0)
			loc += "&specification=" + encodeURIComponent($v("specification"));
		if($v("searchAlternateMode").length > 0)
			loc += "&searchAlternateMode=" + encodeURIComponent($v("searchAlternateMode"));
		if($v("alternate").length > 0)
			loc += "&alternate=" + encodeURIComponent($v("alternate"));
		openWinGeneric(loc,'itemsearch',"900","600","yes","50","50","20","20","no"); 
	}
}

function setItem(itemId, desc) {
	$("itemId").value = itemId;
	$("itemDesc").value = desc;
	
	$("itemDescSpan").innerHTML = desc;
	if (desc.indexOf("<BR>") != -1)
		$("itemDescSpan").innerHTML = desc.substring(0,desc.indexOf("<BR>"));
	else if (desc.indexOf("<br>") != -1)
		$("itemDescSpan").innerHTML = desc.substring(0,desc.indexOf("<br>"));
		
	$("itemDescSpan").innerHTML = $("itemDescSpan").innerHTML.substring(0,100)+" .....";
	
	getSuggestedPrice();
		
	submitSearch();
}

function saveItemSearch(searchPartNumberMode, partNumber, searchCustomerPartNumberMode, customerPartNumber, 
		searchPartDescMode, partDesc, searchTextMode, text, searchSpecificationMode, specification, searchAlternateMode, alternate) {
	$("searchPartNumberMode").value = searchPartNumberMode;
	$("partNumber").value = partNumber;
	$("searchCustomerPartNumberMode").value = searchCustomerPartNumberMode;
	$("customerPartNumber").value = customerPartNumber;
	$("searchPartDescMode").value = searchPartDescMode;
	$("partDesc").value = partDesc;
	$("searchTextMode").value = searchTextMode;
	$("text").value = text;
	$("searchSpecificationMode").value = searchSpecificationMode;
	$("specification").value = specification;
	$("searchAlternateMode").value = searchAlternateMode;
	$("alternate").value = alternate;
}

function setCurrencyId() {
	if($v("opsEntityId").length > 0) {
		for ( var i=0; i < opsCurrencyArray.length; i++) {
		  	if($v("opsEntityId") == opsCurrencyArray[i].opsEntityId) {
		    	$("currencyId").value = opsCurrencyArray[i].homeCurrencyId;
		    }
		}
	}
}

function getSuggestedPrice(getItemDesc) {
	var url="quickquote.do?uAction=getPrice"+
	            "&inventoryGroup="+$v('inventoryGroup')+
	            "&currencyId="+$v('currencyId')+
	            "&itemId="+$v('itemId')+"&getItemDesc="+getItemDesc+
	            "&customerId="+$v('customerId');

	callToServer(url);
}

function setSuggestedSellPrice(suggestedSellPrice, itemDesc) {
	if(suggestedSellPrice != null && suggestedSellPrice.length > 0)
		$("priceSpan").innerHTML = $v('currencyId')+" "+suggestedSellPrice;
	else
		$("priceSpan").innerHTML = "";
	
	if(itemDesc != null && itemDesc.length > 0) {
		$("itemDescSpan").innerHTML = itemDesc;
		if (itemDesc.indexOf("<BR>") != -1)
			$("itemDescSpan").innerHTML = itemDesc.substring(0,itemDesc.indexOf("<BR>"));
		else if (itemDesc.indexOf("<br>") != -1)
			$("itemDescSpan").innerHTML = itemDesc.substring(0,itemDesc.indexOf("<br>"));
			
		$("itemDescSpan").innerHTML = $("itemDescSpan").innerHTML.substring(0,100)+" .....";
		
		$("itemDesc").value = itemDesc;
		
	}
}

function onKeySearch(e) {
  var keyCode;
  if(window.event)
  {
    keyCode = window.event.keyCode;     //IE
  }else
  {
    try
    {
      keyCode = e.which;     //firefox
    }
    catch (ex){}
  }
  if (keyCode==13) {
  	getSuggestedPrice('Y');
    submitSearch();
    return false;
  }
}

function validateSearchCriteriaInput() {
	if($v("opsEntityId").length == 0) {
		alert(messagesData.pleaseinput.replace(/[{]0[}]/g,messagesData.operatingentity));
		return false;
	}
	
	if($v("itemId").length == 0) {
		alert(messagesData.inputitem);
		return false;
	}
	
	return true;
}

function myResize() {
	setWindowSizes();
	
	var iFrameHeight = myHeight - 125; //$("searchFrameDiv").offsetHeight; 
	$("resultFrame").height = iFrameHeight + "px";
//	window.frames['resultFrame'].resizeGrids();
}

var dhxFreezeWins = null;
function initializeDhxWins() {
	if (dhxFreezeWins == null) {
		dhxFreezeWins = new dhtmlXWindows();
		dhxFreezeWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function showTransitWin(messageType)
{
	var waitingMsg = messagesData.waitingforinputfrom+"...";
	$("transitLabel").innerHTML = waitingMsg.replace(/[{]0[}]/g,messageType);

	var transitDailogWin = document.getElementById("transitDailogWin");
	transitDailogWin.innerHTML = document.getElementById("transitDailogWinBody").innerHTML;
	transitDailogWin.style.display="";

	initializeDhxWins();
	if (!dhxFreezeWins.window("transitDailogWin")) {
		// create window first time
		transitWin = dhxFreezeWins.createWindow("transitDailogWin",0,0, 400, 200);
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
		dhxFreezeWins.window("transitDailogWin").show();
	}
}

function closeTransitWin() {
	if (dhxFreezeWins != null) {
		if (dhxFreezeWins.window("transitDailogWin")) {
			dhxFreezeWins.window("transitDailogWin").setModal(false);
			dhxFreezeWins.window("transitDailogWin").hide();
		}
	}
}


function setOpsHubIGValue(opsEntityId, hub, inventoryGroup) {
	var opsList = $("opsEntityId");
	
	if(opsEntityId != null && opsEntityId != ''){
		for(i=0;i< opsList.length;i++){
   	   		if( opsList[i].value == opsEntityId ) {
   	   			$("opsEntityId").val = opsEntityId;
   	   			opsList[i].selected = true;

   	   			opsChanged();
   	   			var hubList = $("hub");
   	   			
   	   			if(hub != null && hub != ''){
	   	   			for(j=0;i< hubList.length;j++){
	   	    	   		if( hubList[j].value == hub ) {
	   	    	   			$("hub").val = hub;
	   	    	   			hubList[j].selected = true;
	
	   	    	   			hubChanged();
	   	    	   			var igList = $("inventoryGroup");
	   	    	   			
	   	    	   			if(inventoryGroup != null && inventoryGroup != ''){
		   	    	   			for(k=0;i< igList.length;k++){
		   		   	    	   		if( igList[j].value == inventoryGroup ) {
		   		   	    	   			$("inventoryGroup").val = inventoryGroup;
		   		   	    	   			igList[k].selected = true;		 
		   		   	    	   			
		   		   	    	   			break;
		   		   	    	   		}
		   		   	   			}	   	    	   				
	   	    	   			}	   	    	   			
	   	    	   			break;
	   	    	   		}
	   	   			}  	   				
   	   			}
   	   			break;
   	   		}
		}
	}
}