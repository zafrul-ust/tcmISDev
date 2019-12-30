function $(a) {
	return document.getElementById(a);
}

function myOnload() {
	parent.document.getElementById('transitPage').style["display"]="none";
	parent.document.getElementById('resultFrameDiv').style["display"]="";
	setResultFrameSize();
	if( showUpdateLinks == true ) {
		parent.document.getElementById('updateResultLink').style["display"]="";
	}
	else {
		parent.document.getElementById('updateResultLink').style["display"]="none";
	}
}

function submitFormData() {
	var action = document.getElementById("action");
  action.value = 'submit';
	var flag = validateForm();
  if(flag) {
    parent.showPleaseWait();
    return true;
  }
  else
  {
    return false;
  }
}

function validateForm() {
	var result = true;
	return result;
}

function editOnLoad() {
	//assuming the default selected tab is the first tab
	unitChanged(0);
  startOnload();
}

function unitChanged(partCount) {
	var unit = document.getElementById("unit"+partCount);
	var recordFound = false;
	var count = 0;
	for (var i = 0; i < netWtRequired.length;i++) {
    var tmp = netWtRequired[i];
		if (unit.value == tmp) {
			count = i;
			recordFound = true;
			break;
		}
  }
	//
	if (recordFound) {
		$("netWtRequired"+partCount).value = "Yes";
		$("netWtVolLabel"+partCount).style["display"] = "";
		$("netWtVolUnitLabel"+partCount).style["display"] = "";
		$("dimensionLabel"+partCount).style["display"] = "";
		$("netWtVol"+partCount).style["display"] = "";
		$("netWtVolUnit"+partCount).style["display"] = "";
		$("dimension"+partCount).style["display"] = "";
	}else {
		$("netWtRequired"+partCount).value = "No";
		$("netWtVolLabel"+partCount).style["display"] = "none";
		$("netWtVolUnitLabel"+partCount).style["display"] = "none";
		$("dimensionLabel"+partCount).style["display"] = "none";
		$("netWtVol"+partCount).style["display"] = "none";
		$("netWtVolUnit"+partCount).style["display"] = "none";
		$("dimension"+partCount).style["display"] = "none";
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
	var universalReplace = "newItemInputBean["; // for those inputs
	var dupStr = new Array();
	var partCount = $('partCount').value;
	var newDivHTML = $('newItem0').innerHTML;
	
// put the column that needs change the rownum...
	dupStr[0] = new Array(  "netWtVolLabel",
							"netWtVolUnitLabel",
							"dimensionLabel",
							"netWtRequired",
							"component",
							"size",
							"unit",
							"pkgStyle",
							"netWtVol",
							"netWtVolUnit",
							"dimension",
							"materialDesc",
							"grade",
							"manufacturer",
							"mfgPartNo",
							//"searchManufacture(",
              "newComponent",
              "unitChanged("
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

//	$("tabsdiv").innerHTML = $("tabsdiv").innerHTML + replHTML;
	$("component"+partCount).value = "";
	$("size"+partCount).value = "";
	$("unit"+partCount).selectedIndex = 0 ;
	$("pkgStyle"+partCount).selectedIndex = 0 ;
	$("netWtVol"+partCount).value = "";
	$("netWtVolUnit"+partCount).selectedIndex = 0 ;
	$("materialDesc"+partCount).value = "";
	$("dimension"+partCount).value = "";
	$("grade"+partCount).value = "";
	$("manufacturer"+partCount).value = "";
	$("mfgPartNo"+partCount).value = "";
  $("newComponent"+partCount).value = "New";

	$('partCount').value = 1+ parseInt(partCount);
//	setResultFrameSize();
	return;
}

var tabDataJson = new Array();
var selectedTabId="";

/*This funtion build the tab and opens the Iframe that has the page associated with the tab*/
function openTab(tabId,tabURL,tabName,tabIcon,noScrolling)
{
 var foundExistingDiv = false;
 for (var i=0; i<tabDataJson.length; i++)
 {
  if (tabDataJson[i].tabId == tabId)
  {
   foundExistingDiv = true;
   togglePage(tabId);
  }
 }

 if (!foundExistingDiv)
 {
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
  newNodeInnerHTML +="<span id=\""+tabId+"LinkSpan\" class=\"selectedSpanTab\"><img src=\""+tabIcon+"\" class=\"iconImage\">"+tabName+"&nbsp;&nbsp;";
  //newNodeInnerHTML +="<img src=\"/images/closex.gif\" alt=\"Close Tab\" title=\"Close Tab\" onclick=\"closeTabx('"+tabId+"')\">";
  //newNodeInnerHTML +="<button type=\"button\" class=\"\" onmouseover=\"this.className='closexOver'\" onmouseout=\"this.className=''\" onmouseup=\"closeTabx('"+tabId+"')\"><img src=\"/images/closex031.gif\"></button>";
  newNodeInnerHTML +="<br class=\"brNoHeight\"><img src=\"/images/minwidth.gif\" width=\""+(tabName.length*2)+"\" height=\"0\"></span></a>";
  newNode.innerHTML = newNodeInnerHTML;
  list.appendChild(newNode);
/*
  var maindivs =  document.getElementById("maindivs");
  newDiv=document.createElement("div");
  newDiv.id = tabId+"div";*/

  /*var scrolling= "auto";
  if (noScrolling == "Y")
  {
   scrolling = "no";
  }*/

/*  var innHtmlline = "<iframe src=\""+tabURL+"\" name="+tabId+"frame id="+tabId+"frame width=\"100%\" height=\"600\" scrolling=\""+scrolling+"\" marginwidth=\"0\" frameborder=\"0\" style=\"\">";
  innHtmlline +="  [Your user agent does not support frames or is currently configured";
  innHtmlline +="  not to display frames. However, you may visit";
  innHtmlline +="  <A href=\"\balnk.html\">the related document.</A>]";
  innHtmlline +="</iframe>";
  newDiv.innerHTML=innHtmlline;*/
/*  maindivs.appendChild(newDiv);*/
  togglePage(tabId);
 }
}

function togglePage(tabId)
{
 //alert("Here togglePage "+tabId+" "+selectedTabId+"");
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
  }
  else
  {
   var tabLink =  document.getElementById(selectedTabId+"Link");
   tabLink.style["display"] = "";
   var tabLinkSpan =  document.getElementById(selectedTabId+"LinkSpan");
   tabLinkSpan.style["display"] = "";

   setVisible(tabId, true);
  }

  var newChemPackagingChange =  document.getElementById("newChemPackagingChange");
  if (newChemPackagingChange.value == "Y")
  {
  toggleContextMenu('nchemcontextMenu');
  }
  /*Doing this so that when the page first comes up and the first thing the
   user does is a right click out side of the tab area, the right click is correct (normal).*/
  setTimeout('toggleContextMenuToNormal()',50);
  /*setTabFrameSize(tabId);*/
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
         unitChanged(tabId);
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
      var newComponent =  document.getElementById("newComponent"+selectedTabId+"");
      newComponent.value = "closed";
      selectedTabIndex=i;
     }
  }
  findNextTab(selectedTabIndex);
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

function submitMainPage()
{
 opener.submitSearchForm();
}