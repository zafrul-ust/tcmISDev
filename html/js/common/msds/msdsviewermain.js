var topSectionHeight = 102;
var resultSectionHeight = 0;
var showMsds = false;
var children = new Array();

function buildDropDown( arr, defaultObj, eleName ) {
   var obj = $(eleName);
   for (var i = obj.length; i > 0;i--)
     obj[i] = null;
  // if size = 1 or 2 show last one, first one is all, second one is the only choice.
  if( arr == null || arr.length == 0 ) 
	  setOption(0,defaultObj.name,defaultObj.id, eleName); 
  else if( arr.length == 1 )
	  setOption(0,arr[0].name,arr[0].id, eleName);
  else {
      var start = 0;
  	  if( defaultObj.nodefault )
  	  	start = 0 ; // will do something??
  	  else {
	  setOption(0,defaultObj.name,defaultObj.id, eleName); 
		  start = 1;
	  }
      for ( var i=0; i < arr.length; i++) {
        setOption(start++,arr[i].name,arr[i].id,eleName);
	  }
  }
  obj.selectedIndex = 0;
}

function setFacAppDropdowns() {
 	buildDropDown(facApplicationArr,defaults.fac,"facilityId");
 	$('facilityId').onchange = facChanged;
    if ($v('defaultFacilityId')) {
        $('facilityId').value = $v('defaultFacilityId');
    }
    facChanged();
}

function facChanged()
{  
   var facilityId = $v("facilityId");
   var arr = null;
   if( facilityId.value != '' ) {
   	   for(i=0;i< facApplicationArr.length;i++)
   	   		if( facApplicationArr[i].id == facilityId ) {
   	   			arr = facApplicationArr[i].coll;
   	   			break;
   	   		}
   }

   buildDropDown(arr,defaults.app,"application");

   if($v("application").length > 0*1) {
	   	$("approvedOnlyDisplay").checked = true;
	   	$("approvedOnlyDisplay").disabled = true;
   }	
   else {
   		$("approvedOnlyDisplay").checked = false;
	   	$("approvedOnlyDisplay").disabled = false;
   }
}

function workAreaChanged() {
	$("applicationList").value = '';
    for (var i=0; i<$('application').options.length; i++) {
	    if ($('application').options[i].selected) {
	      $("applicationList").value = $v("applicationList") +";" + $('application').options[i].value;
	    }
	}
	
	$("applicationList").value = $v("applicationList").substring(1);

	if( $v("applicationList").substring(0,1) == ";" || $v("applicationList").length == 0) {
			$("applicationList").value = "";
			$("approvedOnlyDisplay").checked = false;
			$("approvedOnlyDisplay").disabled = false;
			$("application").selectedIndex = 0;
			$("applicationList").value = $v("applicationList").substring(1);
	}
	else {
			$("approvedOnlyDisplay").checked = true;
			$("approvedOnlyDisplay").disabled = true;
	}

	if($v("applicationList").length == 0) {
		$("approvedOnlyDisplay").disable = false;
	}
	else {
		$("approvedOnlyDisplay").disable = true;
	}
	
	$("oldApplicationList").value = $v("applicationList");
}

function clearAll() {
	$("facilityId").selectedIndex = 0;
	if($v("facilityId") == "")	
	{
		var arr = null;
   		buildDropDown(arr,defaults.app,"application");
	}
	else
		$("application").selectedIndex = 0;
   	if($v("application").length > 0*1) {
	   	$("approvedOnlyDisplay").checked = true;
	   	$("approvedOnlyDisplay").disabled = true;
    }	
    else {
   		$("approvedOnlyDisplay").checked = false;
	   	$("approvedOnlyDisplay").disabled = false;
    }
   	
	$("searchText").value = "";
	clearManufacturer();
	$("fullDatabase").checked = false;
	$("physicalState").value = "";
	$("phCompare").value = ">";
	$("ph").value = "";
	$("flashPointCompare").value = "<";
	$("flashPoint").value = "";
	$("temperatureUnit").value = "F";
	$("vaporPressureCompare").value = ">";
	$("vaporPressure").value = "";
	$("vaporPressureUnit").value = "";
	$("vocPercentCompare").value = ">";
	$("vocPercent").value = "";
	$("solidsPercentCompare").value = ">";
	$("solidsPercent").value = "";
	$("healthCompare").value = ">";
	$("health").value = "";
	$("flammabilityCompare").value = ">";
	$("flammability").value = "";
	$("reactivityCompare").value = ">";
	$("reactivity").value = "";
	$("specificHazard").value = "";
	$("hmisHealthCompare").value = ">";
	$("hmisHealth").value = "";
	$("hmisFlammabilityCompare").value = ">";
	$("hmisFlammability").value = "";
	$("hmisReactivityCompare").value = ">";
	$("hmisReactivity").value = "";
	$("personalProtection").value = "";
    $('vocSearchSelect').value = "";
    $('vocLwesSearchSelect').value = "";
    $('vocLwesPercentCompare').value = ">";
    $('vocLwesPercent').value = "";
    
	myPopupOnLoadWithGrid(listGridConfig);
	myPopupOnLoadWithGrid(casGridConfig);
	showListOrCasNos('list');
    $('list').checked = true;
}

function lookupManufacturer() {
 var loc = "manufacturersearchmain.do?allowNew=N&loginNeeded=N";
 children[children.length] = openWinGeneric(loc,"manufacturersearch","500","500","yes","50","50","20","20","no");
}

function manufacturerChanged(manufacturer, manufacturerDesc, phone, email, contact, notes, url) {
	$("mfgId").value = manufacturer;
	$("manufacturer").value = manufacturerDesc;
	$("manufacturer").title = manufacturerDesc;
}

function clearManufacturer() {
	$("mfgId").value = "";
	$("manufacturer").value = "";
	$("manufacturer").title = "";
}

function validateForm() {
    var result = true;
    var errorMsg = "";
    var specificErrorMsg = "";
 // This validation is for simple search   
    if($v("hideOrShowDiv") == "hide" && isWhitespace($v("simpleSearchText"))) {
		alert(messagesData.validvalues+"\n\n"+messagesData.searchText);
		return false;
	}
	
	var workAreaFlag = true;
	if($("application").selectedIndex == -1 || $("application").selectedIndex == 0) {
		workAreaFlag = false;
	}
		
    if ($v("hideOrShowDiv") == "show" && isWhitespace($v("searchText")) && $v("physicalState").length == 0 && $v("mfgId").length == 0 
    	&& isWhitespace($v("ph")) && isWhitespace($v("flashPoint")) && isWhitespace($v("vaporPressure")) && isWhitespace($v("vocPercent")) && isWhitespace($v("solidsPercent"))
    	&& isWhitespace($v("health")) && isWhitespace($v("flammability")) && isWhitespace($v("reactivity")) && $v("specificHazard").length == 0
    	&& isWhitespace($v("hmisHealth")) && isWhitespace($v("hmisFlammability")) && isWhitespace($v("hmisReactivity")) && $v("personalProtection").length == 0
    	//&& listOrCasNoInputFlag == false && workAreaFlag == false
    	&& workAreaFlag == false 
    	&& $("stocked").checked == false && $("approvedOnlyDisplay").checked == false && $("kitOnly").checked == false  )
    {
        if (specificErrorMsg.length == 0) {
            specificErrorMsg = messagesData.searchTextRequired;
        }else {
            specificErrorMsg += "\n"+messagesData.searchTextRequired;
        }
        alert(specificErrorMsg);
        result = false;
    }
	
	var validVals = validateGrid();
	if(!validVals)
		return false;	
	return result;
}
var gridSubmit = '';
function submitSearchForm() {
	if (!validateForm()) 
		return false;
	
	$('gridSubmit').value = gridSubmit;
    
     if($("approvedOnlyDisplay").checked)
	 	 $("approvedOnly").value = "Y";
	 else
	 	 $("approvedOnly").value = "";
    
    $("uAction").value = 'search';
    var now = new Date();
    var startSearchTime = document.getElementById("startSearchTime");
    startSearchTime.value = now.getTime();
    showPleaseWait();
    document.msdsViewerForm.target='resultFrame';
    document.msdsViewerForm.submit(); 
}

function generateExcel() 
{	
	if (!validateForm()) 
			return false;
	
	$('gridSubmit').value = gridSubmit;
    
    $("applicationList").value = '';
    for (var i=0; i<$('application').options.length; i++) {
	    if ($('application').options[i].selected) {
	      $("applicationList").value = $v("applicationList") +";" + $('application').options[i].value;
	    }
	}
	
	$("applicationList").value = $v("applicationList").substring(1);
	if($v("applicationList").substring(0,1) == ";") 
		$("applicationList").value = "";
		
	 if($("approvedOnlyDisplay").checked)
	 	 $("approvedOnly").value = "Y";
	 else
	 	 $("approvedOnly").value = "";

	 $("uAction").value = 'createExcel';
	 openWinGenericViewFile('/tcmIS/common/loadingfile.jsp',"MSDSExcel","650","600","yes");             
	 document.msdsViewerForm.target='MSDSExcel';
	 var a = window.setTimeout("document.msdsViewerForm.submit();",500);
}

function onKeySearch(e,doFunction) {
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
	    catch (ex){
	      //return false;
	    }
	  }
	 
	  if (keyCode==13) {
	    doFunction();
	  }
	}

function showOrHideMsdsNoSpan() {
/*
	if($v("searchField") == 'customer_msds_number')
		$("msdsNoSpan").style.display = "";
	else
		$("msdsNoSpan").style.display = "none";

	try {
		hideTooltip('hiddenDesc');
	} catch(ex) {}
*/	
	if($v("searchField") == 'item_id' || $v("searchField") == 'material_id' || $v("searchField") == 'customer_msds_number')
		$("matchType").value = 'in csv list';
	else
		$("matchType").value = 'like';
}

function okInput() {
	var msdsNos = $v("msdsNos").replace(/\s+/g, ';');

	$("searchText").value = $v("matchType")+":"+msdsNos;
	hideTooltip('hiddenDesc');
}

function showTooltip(tooltipId, parentId, posX, posY)
{
    it = document.getElementById(tooltipId);

    it.style.width = it.offsetWidth + 'px';
    it.style.height = it.offsetHeight + 'px';
     
    img = document.getElementById(parentId); 
    
    // if tooltip is too wide, shift left to be within parent 
    if (posX + it.offsetWidth > img.offsetWidth) posX = img.offsetWidth - it.offsetWidth;
    if (posX < 0 ) posX = 0; 
        
    x = xstooltip_findPosX(img) + posX;
    y = xstooltip_findPosY(img) + posY;
        
    it.style.top = y + 'px';
    it.style.left = x + 'px';//alert("top"+it.style.top+"     left:"+it.style.left);
    
    it.style.visibility = 'visible';
    it.style.width = '260px';
    it.style.height = '160px'; 
/*    
    if($v("searchText").search(":") == -1)
    	$("msdsNos").value = $v("searchText");
    else
    	$("msdsNos").value = $v("searchText").substring(4);
*/
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

var shiftPressed = false;

function detectShiftDown()
{
    var key;
      if(window.event)
      {
        key = window.event.keyCode;     //IE
        if (key==16) {shiftPressed=true;}	
      }else
      {
        try
        {
          key = e.which;     //firefox
          if (key==16) {shiftPressed=true;}	
        }
        catch (ex){
          //return false;
        }
      }
}

function detectShiftUp()
{
      var key;
      if(window.event)
      {
        key = window.event.keyCode;     //IE
        if (key == 16) {shiftPressed=false;}	
      }else
      {
        try
        {
          key = e.which;     //firefox
          if (key == 16) {shiftPressed=false;}	
        }
        catch (ex){
          //return false;
        }
      }
}

function openMsdsNoTextArea(v)
{
	if(v == "create list")
	{
		children[children.length] = openWinGeneric("advancedmsdsviewermain.do?uAction=showtextarea","copypasteformattextarea","325","370","yes","50","50","20","20","no");
		$('matchType').value = 'in csv list';
	}
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

function myPopupOnLoadWithGrid(gridConfig)
{
	// stopPleaseWait();
	 if( !gridConfig ) gridConfig = _gridConfig;
	 try{
	 if (!showUpdateLinks) /*Dont show any update links if the user does not have permissions*/
	 {
	  $("updateResultLink").style["display"] = "none";
	 }
	 else
	 {
	 
	  $("updateResultLink").style["display"] = "";
	//  $("mainUpdateLinks").style["display"] = "";
	 }
	 }
	 catch(ex)
	 {}

	// totalLines = $("totalLines").value; 
	 initGridWithGlobal(gridConfig);
	 displayNoSearchSecDuration();
}



