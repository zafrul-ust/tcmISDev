var dhxWins = null;
var children = new Array();
var windowCloseOnEsc = true;
var doneInit = false;

var myWidth = 0;
var myHeight = 0;
function resizeResults() {
	try {
/*		setWindowSizes();
		if (_isIE) {
			myWidth = myWidth - 35 + "px";
			   //myHeight = myHeight - 160 + "px";
		}  
		else
			myWidth = myWidth - 15 + "px";
		
		document.getElementById('resultTable').width = myWidth + "px";
		//document.getElementById('resultTable').height = myHeight + "px";  */
	} catch(ex){}  
}

function submitSearchForm() {
	if($("itemId") != null && isInteger($v("itemId"), false)) {
		showTransitWin();
		$("uAction").value = "search";
		document.genericForm.submit();
	}
	else {
		alert(messagesData.itemInteger);
		$("itemId").value ='';
		document.genericForm.itemId.focus();
		return false;
	}
}


function editOnLoad(action) {
	
	startOnload();
	
	togglePage(0);
	
	doneInit = true;
	for (var i=0; i<tabDataJson.length; i++) {
	 toggleSubPage(i,"0");
	}
	
	if (showErrorMessage){
    	setTimeout('showShippingInfoErrorMessages()',100); /*Showing error messages if any*/
  	}
  	
	resizeResults();
	closeTransitWin();
}

function submitUpdate() {
  if (auditData()) {
  	  showTransitWin();
	  $("uAction").value = 'update';
	  document.genericForm.submit();
  }else {
	  return false;
  }
}

function auditData() {
	var totalErrorCount = 0;
	var result = true;
	var finalErrorMsg = "";
		
	for (var i=0;i<tabDataJson.length;i++) {
		var count = 0;
		var errorMsg = "";
		var componentNo = i + 1;
		
		if (!isInteger($v("erg"+i), true)) {
			errorMsg += "\n"+messagesData.erg;
			count = 1;
		}
		
		if (!isFloat($v("reportableQuantityLb"+i), true)) {
			errorMsg += "\n"+messagesData.rqweight;
			count = 1;
		}
		
		if (!isInteger($v("hazmatId"+i), false)) {
			errorMsg += "\n"+messagesData.hazmatid;
			count = 1;
		}
		
		if (!isInteger($v("iataDgId"+i), true)) {
			errorMsg += "\n"+messagesData.dgid;
			count = 1;
		} 
		
		if (!isInteger($v("adrId"+i), true)) {
			errorMsg += "\n"+messagesData.adrid;
			count = 1;
		}
		
		if (($v("symbol"+i) == 'G' || $v("symbol"+i) == 'A G' || $v("symbol"+i) == 'D G' || $v("symbol"+i) == 'G I') && $v("hazmatTechnicalName"+i).length == 0) {
			errorMsg += "\n"+messagesData.dottechnicalname;
			count = 1;
		} 
		
		if ($v("iataTechnicalNameRequired"+i) == 'Y' && $v("iataTechnicalName"+i).length == 0) {
			errorMsg += "\n"+messagesData.iatatechnicalname;
			count = 1;
		} 
		
		if ($v("adrTechnicalNameRequired"+i) == 'Y' && $v("adrTechnicalName"+i).length == 0) {
			errorMsg += "\n"+messagesData.iatatechnicalname;
			count = 1;
		} 
		
		if ($v("imdgTechnicalNameRequired"+i) == 'Y' && $v("imdgTechnicalName"+i).length == 0) {
			errorMsg += "\n"+messagesData.imdgtechnicalname;
			count = 1;
		}
		
		storageAudit = auditStorageData(i);
		count += storageAudit["count"];
		errorMsg += storageAudit["errorMsg"];
		
		if( count >= 1) {
			totalErrorCount = 1;
			finalErrorMsg += "\n" + messagesData.component + " " + componentNo + ":" + errorMsg + "\n";
		}
		  
	}
	
	if (totalErrorCount > 0) {
		alert(messagesData.validvalues + finalErrorMsg);
		result = false;
	}   
	return result;
}

function auditStorageData(idx) {
	var count = 0;
	var errorMsg = "";
	if ($v("acidBase"+idx).length == 0) {
		errorMsg += "\n"+messagesData.acidBase;
		count = 1;
	}
	
	if ($v("corrosive"+idx).length == 0) {
		errorMsg += "\n"+messagesData.corrosive;
		count = 1;
	}
	
	if ($v("aerosol"+idx).length == 0) {
		errorMsg += "\n"+messagesData.aerosol;
		count = 1;
	}
	
	if ($v("flammable"+idx).length == 0) {
		errorMsg += "\n"+messagesData.flammable;
		count = 1;
	}
	
	if ($v("toxic"+idx).length == 0) {
		errorMsg += "\n"+messagesData.toxic;
		count = 1;
	}
	
	if ($v("oxidizer"+idx).length == 0) {
		errorMsg += "\n"+messagesData.oxidizer;
		count = 1;
	}
	
	if ($v("reactive"+idx).length == 0) {
		errorMsg += "\n"+messagesData.reactive;
		count = 1;
	}
	
	if ($v("waterReactive"+idx).length == 0) {
		errorMsg += "\n"+messagesData.waterReactive;
		count = 1;
	}
	
	if ($v("organicPeroxide"+idx).length == 0) {
		errorMsg += "\n"+messagesData.organicPeroxide;
		count = 1;
	}
	
	if ($v("containerMaterial"+idx).length == 0) {
		errorMsg += "\n"+messagesData.container;
		count = 1;
	}
	
	if ( ! (isChecked("waterMiscibleYes"+idx) || isChecked("waterMiscibleNo"+idx))) {
		errorMsg += "\n"+messagesData.waterMiscible;
		count = 1;
	}
	
	if ( ! (isChecked("pyrophoricYes"+idx) || isChecked("pyrophoricNo"+idx))) {
		errorMsg += "\n"+messagesData.pyrophoric;
		count = 1;
	}
	
	if ( ! (isChecked("containerPressureYes"+idx) || isChecked("containerPressureNo"+idx))) {
		errorMsg += "\n"+messagesData.containerPressure;
		count = 1;
	}
	
	return {"count":count,"errorMsg":errorMsg};
	
}

function isChecked(fieldId) {
	var field = $(fieldId);
	return field && field.checked;
}

function regExcape(str) {
// if more special cases, need more lines.
return str.replace(new RegExp("[([]","g"),"[$&]");
}

var tabDataJson = new Array();
var selectedTabId = -1;

/*This funtion build the tab and opens the Iframe that has the page associated with the tab*/
function openTab(tabId,tabURL,tabName,tabIcon,noScrolling)
{
	tabNum = tabDataJson.length + 1;

		tabIndex = tabDataJson.length;
		/*Store the pages that are open in an array so that I don't open more than one frame for the same page*/
		tabDataJson[tabIndex]={tabId:""+tabId+"",status:"open"};
	
		if (tabIcon.length ==0)
		{
		  tabIcon = "/images/spacer14.gif"; //this is to maintain equal heights for all tabs. the heigt of the icon image has to be 14 piexels
		}
	
		var list = document.getElementById("mainTabList");
		var newNode = document.createElement("li");
		newNode.id = tabId +"li";
		var newNodeInnerHTML ="<a href=\"#\" id=\""+tabId+"Link\" class=\"selectedTab\" onmouseup=\"togglePage('"+tabId+"'); return false;\">";
		newNodeInnerHTML +="<span id=\""+tabId+"LinkSpan\" class=\"selectedSpanTab\"><img src=\""+tabIcon+"\" class=\"iconImage\">"+tabName+"&nbsp;"+tabNum+"&nbsp;";
		newNodeInnerHTML +="<br class=\"brNoHeight\"><img src=\"/images/minwidth.gif\" width=\""+(tabName.length*2)+"\" height=\"0\"></span></a>";
		newNode.innerHTML = newNodeInnerHTML;
		list.appendChild(newNode);
		
		$(tabId+"Link").className = "tabLeftSide";
		$(tabId+"LinkSpan").className = "tabRightSide";
		
}

function togglePage(tabId)
{
 //toggle page only if the page passed is not the selected tab
 if (selectedTabId != tabId)
 {
	    var tabLink =  document.getElementById(tabId+"Link");
	    var tabLinkSpan =  document.getElementById(tabId+"LinkSpan");

	      setVisible(tabId, true);
	      tabLink.className = "selectedTab";
	      tabLink.style["display"] = "";
	
	      tabLinkSpan.className = "selectedSpanTab";
	      tabLinkSpan.style["display"] = "";

	      if(selectedTabId != -1) {
	      	  setVisible(selectedTabId, false);
		      $(selectedTabId+"Link").className = "tabLeftSide";
		
		      $(selectedTabId+"LinkSpan").className = "tabRightSide";
	      }
	  
	  selectedTabId = tabId;
  }else {
/*	   var tabLink =  document.getElementById(selectedTabId+"Link");
	   tabLink.style["display"] = "";
	   var tabLinkSpan =  document.getElementById(selectedTabId+"LinkSpan");
	   tabLinkSpan.style["display"] = "";
	   setVisible(tabId, true); */
  }
  
}

function setVisible(tabId, yesno)
{
   var target =  document.getElementById("newItem"+tabId+"");
   if (yesno)
   {
         target.style["display"] = "";
   }
   else
   {
          target.style["display"] = "none";
   }
}

var subTabDataJson = new Array(5);
subTabDataJson[0] = new Array();
subTabDataJson[1] = new Array();
subTabDataJson[2] = new Array();
subTabDataJson[3] = new Array();
subTabDataJson[4] = new Array();
var selectedSubTabId="";
/*This funtion build the tab and opens the Iframe that has the page associated with the tab*/
function openSubTab(subTabId,tabURL,tabName,tabIcon,noScrolling,tabId)
{
	/*Store the pages that are open in an array so that I don't open more than one frame for the same page*/
	subTabDataJson[subTabId][tabId]={tabId:""+subTabId+tabId+"",status:"open"};

	if (tabIcon.length ==0)
	{
	  tabIcon = "/images/spacer14.gif"; //this is to maintain equal heights for all tabs. the heigt of the icon image has to be 14 piexels
	}

	var subList = document.getElementById("subTabList"+tabId);
	var newNode = document.createElement("li");
	newNode.id = tabId +"li";
	var newNodeInnerHTML ="<a href=\"#\" id=\""+subTabId+tabId+"subLink\" class=\"selectedTab\" onmouseup=\"toggleSubPage('"+tabId+"','"+subTabId+"'); return false;\">";
	newNodeInnerHTML +="<span id=\""+subTabId+tabId+"subLinkSpan\" class=\"selectedSpanTab\"><img src=\""+tabIcon+"\" class=\"iconImage\">"+tabName+"&nbsp;";
	newNodeInnerHTML +="<br class=\"brNoHeight\"><img src=\"/images/minwidth.gif\" width=\""+(tabName.length*2)+"\" height=\"0\"></span></a>";
	newNode.innerHTML = newNodeInnerHTML;
	subList.appendChild(newNode);

}

function toggleSubPage(tabId,subTabId)
{

 var tabIdString = "" + subTabId + tabId + "";
 //toggle page only if the page passed is not the selected tab
 if (doneInit)
 {
	  for (var i=0;i<subTabDataJson.length ;i++)
	  {
		    var tabLink =  document.getElementById(subTabDataJson[i][tabId].tabId+"subLink");
		    var tabLinkSpan =  document.getElementById(subTabDataJson[i][tabId].tabId+"subLinkSpan");
		    if (subTabDataJson[i][tabId].tabId == tabIdString)
		    {
		      setSubTabVisible(subTabDataJson[i][tabId].tabId, true);
		      tabLink.className = "selectedTab";
		      tabLink.style["display"] = "";
		
		      tabLinkSpan.className = "selectedSpanTab";
		      tabLinkSpan.style["display"] = "";

		      selectedSubTabId = subTabId;
		    }
		    else
		    {
		      setSubTabVisible(subTabDataJson[i][tabId].tabId, false);
		      tabLink.className = "tabLeftSide";
		
		      tabLinkSpan.className = "tabRightSide";
		    }
	  }
  }
}


function setSubTabVisible(tabId, yesno)
{
    var target =  document.getElementById("newType"+tabId+"");
    if (yesno)
    {
         target.style["display"] = "";

    }
    else
    {
     	target.style["display"] = "none";
    }
}

function popMSDSup() {
	var materialId = $v("materialId"+selectedTabId);
	var loc = "/tcmIS/all/ViewMsds?showspec=N&act=msds&cl=Internal&id="+materialId; 
	children[children.length] = openWinGeneric(loc,"ViewMsds","900","700","yes","50","50","20","20","no"); 
} 

function showTempInfo(displayElement) {
	$(displayElement).innerHTML = messagesData.infochangeddisplaylater; 
}

function getDOTInfo(partCount) {
 $("partCount").value = partCount;
 showTransitWin();
 var loc = "dotshippingnamemain.do?";  
 children[children.length] = openWinGeneric(loc,"DotInfo","800","500","yes","50","50","20","20","no");
}

function setDOTInfo(d) {
	var partCount = $v("partCount");
	$("properShippingName"+partCount).value = d.properShippingName;
	$("hazmatId"+partCount).value = d.hazmatId;
	$("identificationNumber"+partCount).value = d.unNumber;
	$("hazmatTechnicalName"+partCount).value = "";   // d.technicalName;
	$("hazardClass"+partCount).value = d.hazardClass;
	$("packingGroup"+partCount).value = d.packingGroup;
	$("symbol"+partCount).value = d.symbol;
//	$("subsidiaryHazardClass"+partCount).value = "";
    if ( d.symbol == "G" ||  d.symbol == "A G" ||  d.symbol == "D G" ||  d.symbol == "G I") {
    	$("hazmatTechnicalName"+partCount).className = "inputBox"; 
    	$("hazmatTechnicalName"+partCount).readOnly = false;
    }
    else {
    	$("hazmatTechnicalName"+partCount).className = "invGreyInputText"; 
    	$("hazmatTechnicalName"+partCount).readOnly = true;
    }
    
    $("dotDeclaration"+partCount).innerHTML = messagesData.infochangeddisplaylater; 
}

function getIATAInfo(partCount) {
 $("partCount").value = partCount;
 showTransitWin();
 var loc = "iatashippingnamemain.do?";  
 children[children.length] = openWinGeneric(loc,"IATAInfo","800","500","yes","50","50","20","20","no");
}

function setIataInfo(ia) {
	var partCount = $v("partCount");

// iataMixtureSolution	
// iataRegSubrisk 
	$("iataDgId"+partCount).value = ia.iataDgId;
	$("iataIdentificationNumber"+partCount).value = ia.identificationNumber;
	$("iataProperShippingName"+partCount).value = ia.properShippingName;
	$("iataClassOrDivision"+partCount).value = ia.classOrDivision;
	$("iataSubrisk"+partCount).value = ia.subrisk;           // Not sure
	$("iataHazardLabel"+partCount).value = ia.hazardLabel;	
	$("iataPackingGroup"+partCount).value = ia.packingGroup;
	$("iataSpecialProvision"+partCount).value = ia.specialProvision;
	$("iataErgCode"+partCount).value = ia.ergCode;
	$("iataTechnicalName"+partCount).value = "";
	$("iataTechnicalNameRequired"+partCount).value = ia.technicalNameRequired;
	if(ia.technicalNameRequired == 'Y') {
		$("iataTechnicalName"+partCount).className = "inputBox"; 
    	$("iataTechnicalName"+partCount).readOnly = false;
	}
	else {
		$("iataTechnicalName"+partCount).className = "invGreyInputText"; 
    	$("iataTechnicalName"+partCount).readOnly = true;
	}
	
	$("iataDeclaration"+partCount).innerHTML = messagesData.infochangeddisplaylater; 
}

function getADRInfo(partCount) {
 $("partCount").value = partCount;
 showTransitWin();
 var loc = "adrshippingnamemain.do?";  
 children[children.length] = openWinGeneric(loc,"ADRInfo","800","500","yes","50","50","20","20","no");
}

function setADRInfo(a) {
	var partCount = $v("partCount");
	
	$("adrId"+partCount).value = a.adrId;
	$("adrPackingGroup"+partCount).value = a.packingGroup;
	$("adrUnNo"+partCount).value = a.unNumber;
	$("adrProperShippingName"+partCount).value = a.technicalName;
	$("adrClass"+partCount).value = a.adrClass;
	$("adrProperShippingName"+partCount).value = a.properShippingName;
	$("adrClassificationCode"+partCount).value = a.classificationCode;
	$("adrLimitedQuanity"+partCount).value = a.limitedQuantity;
	$("adrTransportCategory"+partCount).value = a.transportCategory;
	$("adrTunnelCode"+partCount).value = a.tunnelCode;
	
	$("adrDeclaration"+partCount).innerHTML = messagesData.infochangeddisplaylater;
// Larry: I added this	
	$("adrTechnicalName"+partCount).value = "";
	$("adrTechnicalNameRequired"+partCount).value = a.technicalNameRequired;
//	a.technicalNameRequired  = "Y";
//	alert( a.technicalNameRequired );
	if(a.technicalNameRequired == 'Y') {
		$("adrTechnicalName"+partCount).className = "inputBox"; 
    	$("adrTechnicalName"+partCount).readOnly = false;
//    	alert('here');
	}
	else {
		$("adrTechnicalName"+partCount).className = "invGreyInputText"; 
    	$("adrTechnicalName"+partCount).readOnly = true;
	}
	
//	$("adrSubrisk"+partCount).value = a.subrisk;           // Not sure

}

function getIMDGInfo(partCount) {
 $("partCount").value = partCount;
 showTransitWin();
 var loc = "imdgshippingnamemain.do?";  
 children[children.length] = openWinGeneric(loc,"IMDGInfo","800","500","yes","50","50","20","20","no");
}

function setIMDGInfo(im) {
	var partCount = $v("partCount");
	
//	private String imdgTechnicalName;

	$("imdgId"+partCount).value = im.imdgId; 
	$("imdgProperShippingName"+partCount).value = im.properShippingName; 
	$("imdgClassOrDivision"+partCount).value = im.classOrDivision;
	$("imdgSubsidiaryRisk"+partCount).value = im.subsidiaryRisk; // Not sure
	$("imdgPackingGroup"+partCount).value = im.packingGroup;
	$("imdgTechnicalName"+partCount).value = "";
	$("imdgUnNumber"+partCount).value = im.unNumber;
	$("imdgTechnicalNameRequired"+partCount).value = im.technicalNameRequired;
	if(im.technicalNameRequired == 'Y') {
		$("imdgTechnicalName"+partCount).className = "inputBox"; 
    	$("imdgTechnicalName"+partCount).readOnly = false;
	}
	else {
		$("imdgTechnicalName"+partCount).className = "invGreyInputText"; 
    	$("imdgTechnicalName"+partCount).readOnly = true;
	}
	
	$("imdgDeclaration"+partCount).innerHTML = messagesData.infochangeddisplaylater; 
}

function getSubRisk(displayElement, type) {
 showTransitWin();
 $("displayElement").value = displayElement;
// showTransitWin();
 var loc = "subrisk.do?itemId="+$v("itemId")+"&type="+type+"&riskString="+$v(displayElement);
 
 //"subrisk.do?orderType=Scratch Pad&status=ScratchPad&prNumber=86405.004&companyId=HAAS&totalLineCharge=0&currencyId=USD&inventoryGroup=Miami%20Distribution&creditStatus=Good&orderStatus=Draft&noAddChargePermission=N&opsEntityId=HAASPSUSA"; 
 children[children.length] = openWinGeneric(loc,"SubRisk","820","350","yes","50","50","20","20","no");
}

function setSubRisk(subRisk) {
	$($v("displayElement")).value = subRisk;
}

function clearField(displayElement) {
	$(displayElement).value = "";
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

//this function will intialize dhtmlXWindow if it's null
function initializeDhxWins() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function showTransitWin()
{
	initializeDhxWins();
	
	if (!dhxWins.window("transitDailogWin")) {
	
		var transitDailogWin = document.getElementById("transitDailogWin");
		transitDailogWin.style.display="";
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

function showShippingInfoErrorMessages(errMsg)
{
  var resulterrorMessages = $("errorMessagesAreaBody");
  var errorMessagesArea = $("errorMessagesArea");
  if(errMsg == null)
  	errorMessagesArea.innerHTML = resulterrorMessages.innerHTML;
  else
	errorMessagesArea.innerHTML = errMsg;
	
  var errorMessagesArea = $("errorMessagesArea");
  errorMessagesArea.style.display="";

  var dhxWins = new dhtmlXWindows();
  dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  if (!dhxWins.window(messagesData.errors)) {
    // create window first time
    var errorWin = dhxWins.createWindow(messagesData.errors, 0, 0, 450, 300);
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

function closeMessageWin() {
  dhxWins.window("showMessageDialog").setModal(false);
  dhxWins.window("showMessageDialog").hide();
}

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

function showTooltip(tooltipId, parentId, posX, posY)
{
    it = document.getElementById(tooltipId);
// alert("top"+it.style.top+"     left:"+it.style.left);
//    if ((it.style.top == '' || it.style.top == '0pt' || it.style.top == '0px') 
//        && (it.style.left == '' || it.style.left == '0pt' || it.style.left == '0px'))
//    {
        // need to fixate default size (MSIE problem)
        it.style.width = it.offsetWidth + 'px';
        it.style.height = it.offsetHeight + 'px';
//  alert("width"+it.style.width+"     height:"+it.style.height);      
        img = document.getElementById(parentId); 
    
        // if tooltip is too wide, shift left to be within parent 
        if (posX + it.offsetWidth > img.offsetWidth) posX = img.offsetWidth - it.offsetWidth;
        if (posX < 0 ) posX = 0; 
        
        x = xstooltip_findPosX(img) + posX;
        y = xstooltip_findPosY(img)*1.5 + posY;
        
        it.style.top = y + 'px';
        it.style.left = x + 'px';//alert("top"+it.style.top+"     left:"+it.style.left);
//    }
    
    it.style.visibility = 'visible';
    it.style.width = '1000px';
    var descHeight = (($v("descLength") / 150)+1)*12;
    it.style.height = descHeight + 'px'; 
}

function hideTooltip(id)
{
    it = document.getElementById(id); 
    it.style.visibility = 'hidden'; 
}


function xstooltip_findPosX(obj) 
{
  var curleft = 0;
  if (obj.offsetParent) 
  {
    while (obj.offsetParent) 
        {
            curleft += obj.offsetLeft
            obj = obj.offsetParent;
        }
    }
    else if (obj.x)
        curleft += obj.x;
    return curleft;
}

function xstooltip_findPosY(obj) 
{
    var curtop = 0;
    if (obj.offsetParent) 
    {
        while (obj.offsetParent) 
        {
            curtop += obj.offsetTop
            obj = obj.offsetParent;
        }
    }
    else if (obj.y)
        curtop += obj.y;
    return curtop;
}

function expertReviewList() {
	var loc = "expertreviewitem.do?uAction=search&item="+$v("itemId");
	openWinGeneric(loc, "expertReviewItem", "800", "450", "yes", "80", "80");
}
