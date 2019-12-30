var dhxWins = null;
var children = new Array();
var windowCloseOnEsc = true;

function showSection1() {
  document.getElementById("section1").style["display"] = "";
  document.getElementById("newChemTabs").style["display"] = "";
  document.getElementById("section2").style["display"] = "none";
}

function $(a) {
	return document.getElementById(a);
}

function editOnLoad(action) {
	closeTransitWin();
	if (action == 'submit' && !showErrorMessage) {
		startOnload();
		//preselect the the first tab
		if (selectedTabId > 0) {
			togglePage(0);
		}
		setSendBackData();
        if ($v("sourcePage") != 'newConsignedItem')
        {
            opener.addNewPartToCart('Y');
        }
        window.close();
	}else {
		startOnload();
		//preselect the the first tab
		if (selectedTabId > 0) {
			togglePage(0);
		}
		setViewLevel();
		//showPkgInfo();
		
		if (showErrorMessage) {
			showMessageDialog();
		}
	}
}

function closeThisWindow() {
	try {
		opener.parent.closeTransitWin();
	}catch(e){}
}

function setSendBackData() {
	var desc = "";
	for (var i=0;i<tabDataJson.length;i++) {
		//don't need to check closed tabs
		if (tabDataJson[i].status == "closed") continue;

		desc += $v("materialDesc"+i);
		if (i*1 > 0) {
			desc += " | ";
		}
	}
	opener.selectedpartNumber = $v("alternateName");
	opener.selecteddescription = desc;
    
    if ($v("noSpec").checked)
    {
        opener.selectedspecification = 'No Specification';
        opener.selectedSpecListConcat = 'No Specification';
        opener.selectedSpecLibraryList = "global";
        opener.selectedCocConcat = "N";
        opener.selectedCoaConcat = "N";
    }
    else
    {
        opener.selectedspecification = '';
        opener.selectedSpecListConcat = '';
        opener.selectedSpecLibraryList = "global";
        opener.selectedCocConcat = "Y";
        opener.selectedCoaConcat = "N";
    }
    opener.selectedItemId = $v("itemId");
	opener.selectedInventoryGroup = $v("inventoryGroup");
	opener.selectedcatalogPrice = "";

}


function setDataUnits() {
	//determine which unit to display to users
	$("englishUnits").checked = true;
	$("metricUnits").checked = false;
	//load dropdown according to unit

}

function englishUnitChecked() {
	//check to see if metric unit was previous checked
	if ($("metricUnits").checked) {
		if (!checkUnits()) {
			alert("warn users about unit changed here");
			$("englishUnits").checked = false;
			$("metricUnits").checked = true;
		}else {
			$("englishUnits").checked = true;
			$("metricUnits").checked = false;
		}
	}else {
		$("englishUnits").checked = true;
		$("metricUnits").checked = false;
	}
}

function metricUnitChecked() {
	//check to see if english unit was previous checked
	if ($("englishUnits").checked) {
		if (!checkUnits()) {
			alert("warn users about unit changed here");
			$("englishUnits").checked = true;
			$("metricUnits").checked = false;
		}else {
			$("englishUnits").checked = false;
			$("metricUnits").checked = true;
		}
	}else {
		$("englishUnits").checked = false;
		$("metricUnits").checked = true;
	}
}

function checkUnits() {
	var unitCheckedOk = true;
	//header data
	if ($v("shippingWeight").trim().length > 0) {
		unitCheckOk = false;
	}
	if ($v("shippingWeightUnit") != 'Select One') {
		unitCheckedOk = false;
	}

	//tabs data
	for (var i=0;i<tabDataJson.length;i++) {
		//don't need to check closed tabs
		if (tabDataJson[i].status == "closed") continue;
		if ($v("materialCriteria"+i) == 'MSDS Required') {
			if ($v("cylinderType"+i) == 'Non Cylinder') {
				if ($v("partSize"+i).trim().length > 0) {
					unitCheckedOk = false;
				}
				if ($v("sizeUnit"+i) != 'Select One') {
					unitCheckedOk = false;
				}
				if ($v("pkgSize"+i).trim().length > 0) {
					unitCheckedOk = false;
				}
				if ($v("pkgSizeUnit"+i) != 'Select One') {
					unitCheckedOk = false;
				}
			}else {
				if ($v("cylinderSize"+i).trim().length > 0) {
					unitCheckedOk = false;
				}
				if ($v("cylinderSizeUnit"+i) != 'Select One') {
					unitCheckedOk = false;
				}
			}
		}
		if (!unitCheckedOk) {
			togglePage(i);
			break;
		}
	}
	return unitCheckedOk;
}

function showPkgInfo() {
	if ($v("materialCriteria"+selectedTabId) == 'MSDS Required') {
		$("cylinderTypeSpan"+selectedTabId).style["display"] = "";
		if ($v("cylinderType"+selectedTabId) == 'Non Cylinder') {
			$("nonCylinderPackagingSpan"+selectedTabId).style["display"] = "";
			$("cylinderPackagingSpan"+selectedTabId).style["display"] = "none";
			$("noMsdsSpan"+selectedTabId).style["display"] = "none";
		}else {
			$("nonCylinderPackagingSpan"+selectedTabId).style["display"] = "none";
			$("cylinderPackagingSpan"+selectedTabId).style["display"] = "";
			$("noMsdsSpan"+selectedTabId).style["display"] = "none";
		}
	}else {
		$("cylinderTypeSpan"+selectedTabId).style["display"] = "none";
		$("nonCylinderPackagingSpan"+selectedTabId).style["display"] = "none";
		$("cylinderPackagingSpan"+selectedTabId).style["display"] = "none";
		$("noMsdsSpan"+selectedTabId).style["display"] = "";
	}
}

function msdsRequiredChecked() {
	$("materialCriteria"+selectedTabId).value = 'MSDS Required';
	showPkgInfo();
}

function noMsdsRequiredChecked() {
	$("materialCriteria"+selectedTabId).value = 'No MSDS Required';
	showPkgInfo();
}

function nonCylinderChecked() {
	$("cylinderType"+selectedTabId).value = 'Non Cylinder';
	showPkgInfo();
}

function cylinderChecked() {
	$("cylinderType"+selectedTabId).value = 'Cylinder';
	showPkgInfo();
}


function cancel() {
	window.close();
}

function submitUpdate() {
  if (auditData()) {
	  showTransitWin();
	  enableFieldsForFormSubmit();
	  $("uAction").value = 'submit';
	  document.genericForm.submit();
  }else {
	  setViewLevel();
	  return false;
  }
}

function auditData() {
	var result = true;

	var missingFields = "";
	
	if (!isFloat($v("shippingWeight"), true))
		missingFields += "\t"+messagesData.shippingweight+"\n";
		
	//material data
	for (var i=0;i<tabDataJson.length;i++) {
		//don't need to audit closed tabs
		if (tabDataJson[i].status == "closed") continue;
		
		if (isEmptyV("manufacturer"+i)) {
			missingFields += "\t"+messagesData.manufacturer+"\n";
		}
		
		if (isEmptyV("materialDesc"+i)) {
			missingFields += "\t"+messagesData.materialdescription+"\n";
		}
/*		if (isEmptyV("mfgTradeName"+i)) {
			missingFields += "\t"+messagesData.mfgtradename+"\n";
		} */

		if (!isEmptyV("mfgCatalogId"+i)) {
			if ($v("mfgCatalogId"+i).length > 30) {
				missingFields += "\t"+messagesData.manufacturerpartnumber+" - "+messagesData.charsmax+"\n";
			}
		}

		if ($("netWtLabelSpan"+i)!= null && $("netWtLabelSpan"+i).style.display.length == 0) {
			if (isEmptyV("netwt"+i) || !isFloat($v("netwt"+i), false)) {
				missingFields += "\t"+messagesData.netsize+"\n";
			}
		}
		if ($("netWtUnitLabelSpan"+i)!= null && $("netWtUnitLabelSpan"+i).style.display.length == 0) {
			if (isEmptyV("netwtUnit"+i) || 'Select One' == $("netwtUnit"+i).value) {
				missingFields += "\t"+messagesData.netunit+"\n";
			}
		}
		if (isEmptyV("componentsPerItem"+i) || !isPositiveInteger($v("componentsPerItem"+i), false)) {
			missingFields += "\t"+messagesData.numperpart+"\n";
		}
		if (isEmptyV("partSize"+i) || !isFloat($v("partSize"+i), false)) {
			missingFields += "\t"+messagesData.size+"\n";
		}
		if (isEmptyV("sizeUnit"+i) || 'Select One' == $("sizeUnit"+i).value) {
			missingFields += "\t"+messagesData.unit+"\n";
		}
		if (isEmptyV("pkgStyle"+i)) {
			missingFields += "\t"+messagesData.packagestyle+"\n";
		}
	}
	
	if (missingFields.length > 0) {
		alert(messagesData.validvalues+"\n"+missingFields);
		result = false;
	}
	return result;
}

function enableFieldsForFormSubmit() {
	setMaterialViewLevel('');
}

function setViewLevel() {
	if ($v("startingView") == '0') {
		setMaterialViewLevel('');
	}else {
		setMaterialViewLevel(true);
	}
}

function setMaterialViewLevel(flag) {
  try{
	$("selectedManufacturer").disabled = flag;
  } catch(ex) {}
	for (var i=0;i<tabDataJson.length;i++) {
		$("manufacturer"+i).disabled = flag;
		$("manufacturerContact"+i).disabled = flag;
		$("manufacturerNotes"+i).disabled = flag;
		$("manufacturerUrl"+i).disabled = flag;
		$("materialDesc"+i).disabled = flag;
		$("mfgTradeName"+i).disabled = flag;
		/*
		$("grade"+i).disabled = flag;
		$("mfgCatalogId"+i).disabled = flag;
		$("dimension"+i).disabled = flag;
		$("netwt"+i).disabled = flag;
		$("netwtUnit"+i).disabled = flag;
		$("componentsPerItem"+i).disabled = flag;
		$("partSize"+i).disabled = flag;
		$("sizeUnit"+i).disabled = flag;
		$("pkgStyle"+i).disabled = flag;
		*/
	}
}

//this function will intialize dhtmlXWindow if it's null
function initializeDhxWins() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function unitChanged() {
	var tmp = altSizeUnitRequiredNetWt[$("sizeUnit"+selectedTabId).selectedIndex];
	if (tmp == 'Y') {
		$("netWtRequired"+selectedTabId).value = "Yes";
		$("netWtLabelSpan"+selectedTabId).style["display"] = "";
		$("netWtUnitLabelSpan"+selectedTabId).style["display"] = "";
		$("netWtSpan"+selectedTabId).style["display"] = "";
		$("netWtUnitSpan"+selectedTabId).style["display"] = "";
	}else {
		$("netWtRequired"+selectedTabId).value = "No";
		$("netWtLabelSpan"+selectedTabId).style["display"] = "none";
		$("netWtUnitLabelSpan"+selectedTabId).style["display"] = "none";
		$("netWtSpan"+selectedTabId).style["display"] = "none";
		$("netWtUnitSpan"+selectedTabId).style["display"] = "none";
	} 
}

function isArray(testObject) {
      return testObject &&
             !( testObject.propertyIsEnumerable('length')) &&
             typeof testObject === 'object' &&
             typeof testObject.length === 'number';
}

function regExcape(str) {
// if more special cases, need more lines.
return str.replace(new RegExp("[([]","g"),"[$&]");
}

// Assuming standard template names. and a
// totalRow variable, which is the current total number of rows.
function mydup() {
// init hard code part, page dependent..
	var universalReplace = "catalogAddItemBean["; // for those inputs
	var dupStr = new Array();
	var partCount = $('partCount').value;
	var newDivHTML = $('newItem0').innerHTML;
	
// put the column that needs change the rownum...
	dupStr[0] = new Array(  "manufacturer","mfgId",
							"materialDesc",
							"grade",
							"mfgTradeName",
                            "manufacturerContact",
                            "manufacturerNotes",
                            "manufacturerUrl",
                            "newTabComponent",
							"netWtRequired",
							"mfgCatalogId",
							"dimension",
							"netwt",
							"netwtUnit",
							"componentsPerItem",
							"partSize",
							"sizeUnit",
							"pkgStyle",
		 					"netWtLabelSpan",
		 					"netWtUnitLabelSpan",
		 					"netWtSpan",
		 					"netWtUnitSpan",
		 					"materialCriteria",
		 					"cylinderTypeSpan",
							"cylinderType",
		               "nonCylinderPackagingSpan",
		 					"prePkgStyle",
		 					"pkgSize",
		 					"pkgSizeUnit",
							"cylinderPackagingSpan",
		               "cylinderComponentsPerItem",
		 					"cylinderSize",
		 					"cylinderSizeUnit",
		 					"cylinderPkgStyle",
		 					"cylinderMaterial",
		 					"valueType",
							 "noMsdsSpan",
		 					"noMsdsComponentsPerItem",
		 					"noMsdsPkgStyle"
							);
    var replHTML = '<div id="newItem'+partCount+'">' + newDivHTML + '</div>';
	for( jj = 0 ;jj < dupStr[0].length; jj++ )
			replHTML = replHTML.replace(new RegExp( regExcape(dupStr[0][jj])+"0","g"),dupStr[0][jj]+partCount);
	if(	universalReplace != null && universalReplace != "" )
			replHTML = replHTML.replace(new RegExp( regExcape(universalReplace)+"0","g"),universalReplace+partCount);

	var newDiv = document.createElement('div');
	newDiv.id = "newItem"+partCount;
	$("tabsdiv").appendChild(newDiv);
	$("newItem"+partCount).innerHTML = replHTML;

    $("mfgId"+partCount).value = "";
	$("manufacturer"+partCount).value = "";
    $("manufacturerContact"+partCount).value = "";
    $("manufacturerNotes"+partCount).value = "";
    $("manufacturerUrl"+partCount).value = "";  
    $("materialDesc"+partCount).value = "";
	$("grade"+partCount).value = "";
	$("mfgTradeName"+partCount).value = "";
	$("mfgCatalogId"+partCount).value = "" ;
	$("dimension"+partCount).value = "";
	$("netwt"+partCount).value = "";
	$("netwtUnit"+partCount).selectedIndex = 0;
	$("componentsPerItem"+partCount).value = 0;
	$("partSize"+partCount).value = "";
	$("sizeUnit"+partCount).selectedIndex = 0 ;
	$("sizeUnit"+partCount).onchange = unitChanged;
	//$("prePkgStyle"+partCount).selectedIndex = 0 ;
	//$("pkgSize"+partCount).value = "" ;
	//$("pkgSizeUnit"+partCount).selectedIndex = 0 ;
	$("pkgStyle"+partCount).selectedIndex = 0 ;
    $("newTabComponent"+partCount).value = "New";
    $("netWtRequired"+partCount).value = "";
	$('partCount').value = 1+ parseInt(partCount);
	return;
}

var tabDataJson = new Array();
var selectedTabId="";

/*This funtion build the tab and opens the Iframe that has the page associated with the tab*/
function openTab(tabId,tabURL,tabName,tabIcon,noScrolling)
{
	var foundExistingDiv = false;
	var tabNum = 1;
	for (var i=0; i<tabDataJson.length; i++)
	{
		if (tabDataJson[i].status == "open")
	 	tabNum = tabNum + 1;

		if (tabDataJson[i].tabId == tabId) {
			foundExistingDiv = true;
			togglePage(tabId);
		}
	}

	if (!foundExistingDiv) {
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
	togglePage(tabId);
	}
 	unitChanged();
}

function togglePage(tabId)
{
 /*If the page being toggled is already closed, ignore the toggle.
  This can happen when they click the x on the taab.
 */
 for (var i=0; i<tabDataJson.length; i++)
 {
   if (tabDataJson[i].tabId == tabId)
   {
    if (tabDataJson[i].status == "closed")
    {
      return;
    }
   }
 }

 //toggle page only if the page passed is not the selected tab
 if (selectedTabId != tabId)
 {
  for (var i=0; i<tabDataJson.length; i++)
  {
    var tabLink =  document.getElementById(tabDataJson[i].tabId+"Link");
    var tabLinkSpan =  document.getElementById(tabDataJson[i].tabId+"LinkSpan");
    if (tabDataJson[i].tabId == tabId && tabDataJson[i].status == "open")
    {
      setVisible(tabDataJson[i].tabId, true);
      tabLink.className = "selectedTab";
      tabLink.style["display"] = "";
      /*tabLink.onmouseover = "";
      tabLink.onmouseout = "";*/

      tabLinkSpan.className = "selectedSpanTab";
      tabLinkSpan.style["display"] = "";
      /*tabLinkSpan.onmouseover = "";
      tabLinkSpan.onmouseout = "";*/
      selectedTabId = tabId;
    }
    else
    {
      setVisible(tabDataJson[i].tabId, false);
      tabLink.className = "tabLeftSide";
      /*tabLink.onmouseover = "className='selectedTab'";
      tabLink.onmouseout = "className='tabLeftSide'";*/

      tabLinkSpan.className = "tabRightSide";
      /*tabLinkSpan.onmouseover = "className='selectedSpanTab'";
      tabLinkSpan.onmouseout = "className='tabRightSide'";*/
    }
  }
  }else {
   var tabLink =  document.getElementById(selectedTabId+"Link");
   tabLink.style["display"] = "";
   var tabLinkSpan =  document.getElementById(selectedTabId+"LinkSpan");
   tabLinkSpan.style["display"] = "";

   setVisible(tabId, true);
  }

  if (selectedTabId == 0) {
	 toggleContextMenu('newCatalogItemContextMenuWithoutDelete');
  }else {
	 toggleContextMenu('newCatalogItemContextMenu');
  }

  /*Doing this so that when the page first comes up and the first thing the
   user does is a right click out side of the tab area, the right click is correct (normal).*/
  setTimeout('toggleContextMenuToNormal()',50);
}

function setVisible(tabId, yesno)
{
    try
    {
        var target =  document.getElementById("newItem"+tabId+"");
        if (yesno)
        {
         //alert("Here setVisible true  "+tabId+"");
         target.style["display"] = "";

        }
        else
        {
          //alert("Here setVisible false  "+tabId+"");
          target.style["display"] = "none";
        }
    }
    catch (ex)
    {
      alert("Here exception in setVisible");
    }
}

function addNewTab()
{
  mydup(); 
  openTab(''+($('partCount').value*1-1)+'','',messagesData.component,'','');      
}

function removeTab()
{
 // if (selectedTabId.length > 0 && selectedTabId !='home')
 var selectedTabIndex=0;
 if (selectedTabId.length > 0 && selectedTabId != "0")
 {
  var tabLink =  document.getElementById(selectedTabId+"Link");
  tabLink.style["display"] = "none";
  var tabLinkSpan =  document.getElementById(selectedTabId+"LinkSpan");
  tabLinkSpan.style["display"] = "none";

  var target =  document.getElementById("newItem"+selectedTabId+"");
  target.style["display"] = "none";

/*  var pageIframe =  document.getElementById(""+selectedTabId+"frame");
  pageIframe.src = "/blank.html";*/

  for (var i=0; i<tabDataJson.length; i++)
  {
     if (tabDataJson[i].tabId == selectedTabId)
     {
      //selectedTabId = tempSelectedTab;
      tabDataJson[i].status = "closed";       
      $("newTabComponent"+selectedTabId+"").value = "closed";
      selectedTabIndex=i;
     }
  }
  findNextTab(selectedTabIndex);
 } 

// Reassign an ordered number for tab  
 var list = document.getElementById("mainTabList");
 var a = list.getElementsByTagName('span');
 var tabNum = 1;
 for (var i=0;i<a.length;i++) {
   if(tabDataJson[i].status == "open") {
     a[i].innerText = a[i].textContent = messagesData.component+" "+tabNum+" ";
     tabNum = tabNum +1;
   } 
 }

}

function findNextTab(closedTabIndex)
{
 //alert("Here findNextTab "+closedTabIndex+"");
 var foundNextTab = false;
 //alert(closedTabIndex);
 for (var i=closedTabIndex-1; i>=0; i--)
 {
   //alert(""+tabDataJson[i].tabId+"  "+tabDataJson[i].status+" "+foundNextTab+"");
   if (tabDataJson[i].status == "open" && !foundNextTab)
   {
    togglePage (tabDataJson[i].tabId);
    foundNextTab = true;
    break;
   }
 }

 //alert("Between Tabs"+foundNextTab+"");
 if (!foundNextTab)
 {
  for (var i=tabDataJson.length-1; i>closedTabIndex; i--)
  {
   //alert(""+tabDataJson[i].tabId+"  "+tabDataJson[i].status+" "+foundNextTab+"");
   if (tabDataJson[i].status == "open" && !foundNextTab)
   {
    togglePage (tabDataJson[i].tabId);
    foundNextTab = true;
    break;
   }
  }
 }
}

function lookupManufacturer() {
// alert("selectedTabId"+selectedTabId);
 var manufacturer = document.getElementById("manufacturer"+selectedTabId).value.trim();
 var loc = "manufacturersearchmain.do?userAction=search&searchArgument="+manufacturer+"&allowNew=N";
 children[children.length] = openWinGeneric(loc,"manufacturersearch","500","500","yes","50","50","20","20","no");
}

function stopShowingWait() {
	//do nothing
}

function manufacturerChanged(newId,newDesc) {
	document.getElementById("mfgId"+selectedTabId).value = newId;
	document.getElementById("manufacturer"+selectedTabId).value = newDesc;
}


function lookupPkgStyle() {
 var pkgStyle = document.getElementById("pkgStyle"+selectedTabId).value.trim();
 var loc = "packagestylesearchmain.do?uAction=search&searchArgument="+pkgStyle;
 children[children.length] = openWinGeneric(loc,"packagestylesearch","500","500","yes","50","50","20","20","no"); 
}

function pkgStyleChanged(newPkgValue) {
	document.getElementById("pkgStyle"+selectedTabId).value = newPkgValue;
}

function closeTransitWin() {
	if (dhxWins != null) {
		if (dhxWins.window("transitDailogWin")) {
			dhxWins.window("transitDailogWin").setModal(false);
			dhxWins.window("transitDailogWin").close();
		}
	}
}

function showTransitWin()
{
	document.getElementById("transitLabel").innerHTML = messagesData.pleasewait;
	
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
		dhxWins.window("transitDailogWin").show();
	}
}

function showMessageDialog()
{
	var inputDailogWin = document.getElementById("messageDailogWin");
	inputDailogWin.style.display="";

	initializeDhxWins();
	if (!dhxWins.window("showMessageDialog")) {
		// create window first time
		inputWin = dhxWins.createWindow("showMessageDialog",0,0, 450, 150);
		inputWin.setText(messagesData.errors);
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
	else
	{
		// just show
		inputWin.setModal(true);
		dhxWins.window("showMessageDialog").show();
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

