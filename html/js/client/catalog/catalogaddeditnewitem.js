var dhxWins = null;
var children = new Array();
var windowCloseOnEsc = true;
var closeOpenerTransitWin = true;

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
	$("uAction").value = action;
	if ((action == 'submitEditNewItem' || action == 'addItem') && !showErrorMessage) {
		opener.calledFrom = $v("calledFrom");
		opener.itemAlreadyInQpl = $v("itemAlreadyInQpl");
		opener.itemIdInQpl = $v("itemIdInQpl");
		opener.dataArray = submittedDataArray;
		if ($v("calledFrom") == 'searchGlobalItem') {
			opener.addItemToQPL();
		}else if ($v("calledFrom") == 'catalogAddRequest') {
			opener.addNewItemToQpl();
            closeOpenerTransitWin = false;
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
	for (var i=0;i<tabDataJson.length;i++) {
		bindACkeys(i);
	}
}

function closeThisWindow() {
	try {
		if ($v("calledFrom") == 'searchGlobalItem') {
			opener.parent.closeTransitWin();
			if ($v("uAction") != 'submitEditNewItem' && $v("uAction") != 'addItem') {
				$("uAction").value = 'deleteLine';
	  			document.genericForm.submit();
			}
		}else if ($v("calledFrom") == 'catalogAddRequest') {
            if (closeOpenerTransitWin) {
                opener.closeTransitWin();
            }
        }
	}catch(e){}
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
  if($v('addOnLineItem') != '')
	 $('lineItem').value =  $v('addOnLineItem'); 
  if (auditData()) {
	  showTransitWin();
	  enableFieldsForFormSubmit();
	  $("uAction").value = 'submitEditNewItem';
	  document.genericForm.submit();
  }else {
	  setViewLevel();
	  return false;
  }
}

function auditReplaceMsds() {
	var validMsds = true;
	for(var i = 1; i <= tabDataJson.length;i++) {
		var replaceMsds = j$("#replacesMsds"+i).val();
		var replaceMsdsValidator = j$("#replacesMsdsValidator"+i).val();
		
		validMsds = (replaceMsds == replaceMsdsValidator);
		if (!validMsds) {
			break;
		}
	}
	
	if (validMsds) {
		return true;
	}
	return false;
}

function auditData() {
	var result = true;

	var missingFields = "";

    //shipping weight
    if (!isEmptyV("shippingWeight")) {
        if (isNaN($v(("shippingWeight")))) {
		    missingFields += "\t"+messagesData.shippingWeight+"\n";
        }else {
            if (isEmptyV("shippingWeightUnit") || 'Select One' == $("shippingWeightUnit").value) {
			    missingFields += "\t"+messagesData.shippingWeight+" "+messagesData.unit+"\n";
		    }
        }
    }
    
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
		if (isEmptyV("mfgTradeName"+i)) {
			missingFields += "\t"+messagesData.mfgtradename+"\n";
		}

		if (!isEmptyV("mfgCatalogId"+i)) {
			if ($v("mfgCatalogId"+i).length > 30) {
				missingFields += "\t"+messagesData.manufacturerpartnumber+" - "+messagesData.charsmax+"\n";
			}
		}

		if ($("netWtLabelSpan"+i).style.display.length == 0) {
			if (isEmptyV("netwt"+i)) {
				missingFields += "\t"+messagesData.netsize+"\n";
			}
		}
		if ($("netWtUnitLabelSpan"+i).style.display.length == 0) {
			if (isEmptyV("netwtUnit"+i) || 'Select One' == $("netwtUnit"+i).value) {
				missingFields += "\t"+messagesData.netunit+"\n";
			}
		}
		if (isEmptyV("componentsPerItem"+i)) {
			missingFields += "\t"+messagesData.numpercomp+"\n";
		}
		if (isEmptyV("partSize"+i)) {
			missingFields += "\t"+messagesData.size+"\n";
		}else if (isNaN($v(("partSize"+i)))) {
            missingFields += "\t"+messagesData.size+"\n";
        }
		if (isEmptyV("sizeUnit"+i) || 'Select One' == $("sizeUnit"+i).value) {
			missingFields += "\t"+messagesData.unit+"\n";
		}
	
		if ($("pkgStyle"+i).className != "inputBox") {
			$("pkgStyle"+i).value = "";
		}
		if (isEmptyV("pkgStyle"+i)) {
			missingFields += "\t"+messagesData.packagestyle+"\n";
		}
	}

	if (missingFields.length > 0) {
		alert(messagesData.validvalues+"\n"+missingFields);
		result = false;
	}
	if (!auditReplaceMsds()) {
		alert(messagesData.invalidReplaceMsds);
		result = false;
	}
	return result;
}

function enableFieldsForFormSubmit() {
	setMaterialViewLevel('');
}

function setViewLevel() {
	//starting view is by line not by component
	//all component of kit will have the same starting view
	//so getting the first value will work
	lineStartingView = $v("lineStartingView"+0);
	if (lineStartingView == '0') {
		setMaterialViewLevel('');
	}else {
		setMaterialViewLevel(true);
	}
}

function setMaterialViewLevel(flag) {
//	$("selectedManufacturer").disabled = flag;
	for (var i=0;i<tabDataJson.length;i++) {
		$("manufacturer"+i).disabled = flag;
		$("materialDesc"+i).disabled = flag;
		$("mfgTradeName"+i).disabled = flag;
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

	j$("#newItem0").clone(true).attr("id","newItem"+partCount).appendTo("#tabsdiv");
	
	j$("#newItem"+partCount).find("*[id]").each(function() { j$(this).attr("id", j$(this).attr("id").slice(0, -1) + partCount); });
	j$("#newItem"+partCount).find("*[name]").each(function() { j$(this).attr("name", j$(this).attr("name").replace("0",partCount)); });
	addOnclickFunctions(partCount);

    $("mfgId"+partCount).value = "";
	$("manufacturer"+partCount).value = "";
    $("customerMsdsNumber"+partCount).value = "";
    if ($v("showReplacesMsds") == 'Y') {
        $("replacesMsds"+partCount).value = "";
        j$("#replacesMsds"+partCount).data("boundToKeystroke", false);
        $("replacesMsdsValidator"+partCount).value="";
    }
    //$("aerosolContainer"+partCount).value = "";
    $("radioactive"+partCount).value = "";
    $("nanomaterial"+partCount).value = "";
    $("materialDesc"+partCount).value = "";
	$("grade"+partCount).value = "";
	$("mfgTradeName"+partCount).value = "";
	$("mfgCatalogId"+partCount).value = "" ;
	$("dimension"+partCount).value = "";
	$("netwt"+partCount).value = "";
	$("netwtUnit"+partCount).selectedIndex = 0;
	$("componentsPerItem"+partCount).value = 1;
	$("partSize"+partCount).value = "";
	$("sizeUnit"+partCount).selectedIndex = 0 ;
	$("sizeUnit"+partCount).onchange = unitChanged;
	//$("prePkgStyle"+partCount).selectedIndex = 0 ;
	//$("pkgSize"+partCount).value = "" ;
	//$("pkgSizeUnit"+partCount).selectedIndex = 0 ;
	$("pkgStyle"+partCount).value = "" ;
    $("newTabComponent"+partCount).value = "New";
    $("netWtRequired"+partCount).value = "";
	$('partCount').value = 1+ parseInt(partCount);
	$("partId"+partCount).value = "";

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
	bindACkeys(tabId);
	
	
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

  if ($v("calledFrom") == 'searchGlobalItem' || $v("calledFrom") == 'catalogAddRequest') {
	  if ($v("lineStartingView"+0) == '0') {
          if (selectedTabId == 0) {
             toggleContextMenu('catalogAddEditNewItemContextMenuWithoutDelete');
          }else {
             toggleContextMenu('catalogAddEditNewItemContextMenu');
          }
      }else {
        toggleContextMenu('catalogAddEditNewItemContextMenuEmpty');
      }
  }else {
	  toggleContextMenu('catalogAddEditNewItemContextMenuEmpty');
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
			dhxWins.window("transitDailogWin").hide();
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
		transitWin.setModal(true);
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

function bindACkeys(idx) {
	var replacesMsds = "replacesMsds";
	var replacesMsdsValidator = "replacesMsdsValidator";
	// check the flag set in qplBind so that the function is not called every time the row renders
	var jjj = j$("#" + replacesMsds + idx).data("boundToKeystroke");
	if (j$("#" + replacesMsds + idx).data("boundToKeystroke") !== true) {
		msdsBind(idx, replacesMsds, replacesMsdsValidator);
	}
}

function msdsBind(rowId, el1, el2) {
	j$().ready(function() {
		function log(event, data, formatted) {
			j$("#" + el2 + rowId).val(formatted.split(":")[0]);
			$(el1 + rowId).className = "inputBox";
		} 
		
		j$("#"+el1+rowId).result(log).next().click(function() {
			j$(this).prev().search();
		});
	
	j$("#" + el1 + rowId).autocomplete("catalogaddrequest.do?uAction=msdsRequest",{
		extraParams: {
			requestId: function() { return j$("#requestId").val(); }
		},
		width: 500,
		max: 20,  // default value is 10
		cacheLength:0, // disable cache here because we put "rownum < max" for better performance. Cache will make data off.
		scrollHeight: 150,
		formatItem: function(data, i, n, value) {
			return  value;
		},
		formatResult: function(data, value) {
			return value;
		},
		parse: function(data) {
			var results = jQuery.parseJSON(data);
			var parsed = new Array();
			for (var row in results.CustomerMSDSNumberResults){
				parsed[parsed.length] = {
						data: results.CustomerMSDSNumberResults[row].customerMsdsNumber,
						value: results.CustomerMSDSNumberResults[row].customerMsdsNumber,
						result: results.CustomerMSDSNumberResults[row].customerMsdsNumber
				};
			}
			return parsed;
		} 
	});
	
	j$("#" + el1 + rowId).bind('keyup',(function(e) {
		var keyCode = (e.keyCode ? e.keyCode : e.which);
		
		if (keyCode != 13 /* Enter */ && keyCode != 9 /* Tab */) {
			invalidateRequestor(rowId, el1, el2);
		}
	}));
	// set a flag so that this function does not get called every time the row renders
	j$("#" + el1 + rowId).data("boundToKeystroke", true);
	
	j$("#" + el1 + rowId).result(log).next().click(function() {
		j$(this).prev().search();
	});

	});
}

function invalidateRequestor(rowId, el1, el2) {
	var requestorName = document.getElementById(el1 + rowId);
	if (requestorName.value.length == 0) {
		requestorName.className = "inputBox";
	}
	else {
		requestorName.className = "inputBox red";
	}
	if (el2 != null) {
		j$("#"+el2+rowId).val("");
	}
}

