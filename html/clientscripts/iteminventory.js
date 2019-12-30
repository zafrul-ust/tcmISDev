var submitcount=0;
var updatecount=0;
function SubmitOnlyOnce()
{
    if (submitcount == 0)
    {
        submitcount++;
        return true;
    }
    else
    {
        alert("This form has already been submitted.\n Please Wait for Results.\n Thanks!");
        return false;
    }
}

function cancel()
{
   window.close();
}


String.prototype.trim = function()
{
// skip leading and trailing whitespace
// and return everything in between
  return this.replace(/^\s*(\b.*\b|)\s*$/, "$1");
}


function showItemManagementLegend()
{
  openWinGeneric("/tcmIS/help/itemManagementLegend.html","ItemManagementLegend","290","300","no","50","50")
}


function searchTextChanged()
{
 var searchText  =  document.getElementById("searchText");
 if (searchText.value.trim().length*1 > 0)
 {
  var minMaxOnly  =  document.getElementById("minMaxOnly");
  minMaxOnly.checked = false;
 }
}

function submitmainpage()
{
  opener.search();
  opener.document.iteminventory.submit();
  window.close();
}

function checkactivestatus(rownum)
{
/*var checkbox = document.getElementById("row_chk"+rownum+"");
var status  =  document.getElementById("status"+rownum+"");
var displayrow =document.getElementById("displayrow"+rownum+"");

if (checkbox.checked)
{
 status.value = "A";
 if ( (rownum*1) % 2 == 0 )
 {
	displayrow.style.backgroundColor = "#E6E8FA";
   displayrow.className ="blue";
 }
 else
 {
   displayrow.style.backgroundColor = "#ffffff";
   displayrow.className = "white";
 }
 className =  "moveup";
}
else
{
	status.value = "I";
   displayrow.style.backgroundColor = "#3b3b3b";
	displayrow.className = "black";
}*/
}

function showInventoryDetail(inventoryGroup,catPartNo,partGroupNo)
{
	 var loc = "/tcmIS/hub/itemmanagementdetail.do?"
	 inventoryGroup = inventoryGroup.replace(/#/gi, "%23");
	 inventoryGroup = inventoryGroup.replace(/&/gi, "%26");
    loc = loc + "inventoryGroup=" + inventoryGroup;
    loc = loc + "&catPartNo=" + catPartNo;
	 loc = loc + "&partGroupNo=" + partGroupNo;
    openWinGeneric(loc,"Show_Min_Max","600","400","yes")
}

function showMinMax(catPartNo,invengrp,partGroupNo,count_uom)
{
	var HubName  =  document.getElementById("hubC");
	//var invengrp  =  document.getElementById("invengrp");
	/* loc = "/tcmIS/hub/minmaxchg?UserAction=showlevels&searchlike=CAT_PART_NO&searchtext="
    loc = loc + catPartNo;
    loc = loc + "&HubName=" + HubName.value;
    loc = loc + "&invengrp=" + invengrp;
    loc = loc + "&catPartNo=" + catPartNo;
	 loc = loc + "&partGroupNo=" + partGroupNo;
	 //loc = loc + "&origOperatingMethod=" + operatingMethod;
	 //loc = loc + "&countUom=" + count_uom;
    loc = loc + "&changeOperatingMethod=Yes";*/

    loc = "/tcmIS/hub/minmaxlevelmain.do?uAction=showlevels&criterion=partNumber&criteria="
    loc = loc + catPartNo;
    loc = loc + "&hub=" + HubName.value;
    loc = loc + "&inventoryGroup=" + invengrp;

    openWinGeneric(loc,"Show_Min_Max","800","600","yes");
}

function showdispensesrc(itemid,invengrp)
{
        loc = "/tcmIS/hub/mditeminventory?Action=chginvgrp";
        loc = loc + "&itemid=" + itemid;
        loc = loc + "&invengrp=" + invengrp;

        var hubC  =  document.getElementById("hubC");
        loc = loc + "&hubC=" + hubC.value;

        openWinGeneric(loc,"showdispensingsource","600","400","yes","130","160")
}

function onetimebuy()
{
  var branchPlant  =  document.getElementById("hubC").value;
  if (branchPlant == "2120")
  {
	loc = "/tcmIS/gm/Register?useraction=startingtcmis"
  }
  else if (branchPlant == "2108" || branchPlant == "2109" || branchPlant == "2110" || branchPlant == "2111" || branchPlant == "2112")
  {
        loc = "/tcmIS/sd/Register?useraction=startingtcmis"
  }
  else if (branchPlant == "2122" || branchPlant == "2123")
  {
        loc = "/tcmIS/dana/Register?useraction=startingtcmis"
  }
  else if (branchPlant == "2125")
  {
        loc = "/tcmIS/am/Register?useraction=startingtcmis"
  }
  else if (branchPlant == "2126")
  {
        loc = "/tcmIS/gema/Register?useraction=startingtcmis"
  }
  else if (branchPlant == "2127")
  {
        loc = "/tcmIS/dcx/Register?useraction=startingtcmis"
  }
  else if (branchPlant == "2128")
  {
        loc = "/tcmIS/pge/Register?useraction=startingtcmis"
  }

	openWinGeneric(loc,"opencatalog","600","600","yes","130","160")
}

function checkior (rownum)
{
var ior_chk  =  document.getElementById("ior_chk"+rownum+"");
if (ior_chk.checked)
{
	ior_chk.value = "Y";
}
else
{
	ior_chk.value = "N";
}
}

function checkpercentagetotal()
{

}

function CheckQtyValue (rownum)
{
	var controlname  =  document.getElementById("buy_qty"+rownum+"");
	if ( !(isInteger(controlname.value)) )
   {
   	alert(" Please enter a valid Number.") ;
		controlname.value = "";
   }
}

function updatedepids()
{
 var dpdepID  =  document.getElementById("dpdepID");
 var newdepID  =  document.getElementById("newdepID");
 var dpareaID  =  document.getElementById("dpareaID");
 var newareaID  =  document.getElementById("newareaID");
 var dpprocessID  =  document.getElementById("dpprocessID");
 var newprocessID  =  document.getElementById("newprocessID");

 var depID  =  document.getElementById("depID");
 if (dpdepID.trim.length >0)
 {
  depID.value = dpdepID.value;
 }
 else
 {
  depID.value = newdepID.value;
 }

 var areaID  =  document.getElementById("areaID");
 if (dpareaID.trim.length >0)
 {
  depID.value = dpareaID.value;
 }
 else
 {
  depID.value = newareaID.value;
 }

 var processID  =  document.getElementById("processID");
 if (dpprocessID.trim.length >0)
 {
  depID.value = dpprocessID.value;
 }
 else
 {
  depID.value = newprocessID.value;
 }

}

function addeditinvgrp()
{
        loc = "/tcmIS/hub/assignaddinvgrp?Action=addeditinvgrp";
        var hubC  =  document.getElementById("hubC");
        loc = loc + "&selHub=" + hubC.value;
        openWinGeneric(loc,"addeditinvgrp","500","400","yes","130","160")
}

function assignnewinvegrp(itemid,hhubnum)
{
        loc = "/tcmIS/hub/assignaddinvgrp?Action=chginvgrp";
        loc = loc + "&itemid=" + itemid;
        loc = loc + "&selHub=" + hhubnum;
        openWinGeneric(loc,"chginvgrp","500","400","yes","130","160")
}

function addeditworkarea()
{
        loc = "/tcmIS/hub/assignaddwkarea?Action=addworkarea";
        var hubC  =  document.getElementById("hubC");
        loc = loc + "&selHub=" + hubC.value;
        openWinGeneric(loc,"addeditworkarea","500","400","yes","130","160")
}

function changeworkarea(itemid,invengrp,application,facilityid,companyid)
{
        loc = "/tcmIS/hub/assignaddwkarea?Action=chgwrkareaass";
        loc = loc + "&itemid=" + itemid;
        loc = loc + "&application=" + application;
        loc = loc + "&facilityid=" + facilityid;
        loc = loc + "&companyid=" + companyid;
        invengrp = invengrp.replace(/#/gi, "%23");
        loc = loc + "&invengrp=" + invengrp;

        openWinGeneric(loc,"chgwrkareaass","500","400","yes","130","160")
}

function alertdelete(rownum)
{
testcheckbox  =  document.getElementById("row_chk"+rownum+"");
if ( testcheckbox.checked == true )
{
errMsg = "This Item will be remoeved from the Storage Area associated";
errMsg = errMsg + "\n\nClick Ok to Approve.\n";
if (confirm(shipTomsg))
{

}
else
{
testcheckbox.value = "Yes";
}
}
else
{
testcheckbox.value = "No";
}
}

function hubChanged(object)
{
   removeAllHazards()
   addOptionItem(document.getElementById("invengrp"),'All','All Storage Areas');

   var artist;
   artist = object.options[object.selectedIndex].text;

   var selectedIndex = object.selectedIndex;
   //second reload work area for selected facility
   for (var i = document.iteminventory.facilityName.length; i > 0;i--)
   {
      document.iteminventory.facilityName.options[i] = null;
   }
   showFacilitylinks(selectedIndex);
   window.document.iteminventory.facilityName.selectedIndex = 0;
   //reset selected index the first element in the facility list
   selectedIndex = 1;
   var facID = window.document.iteminventory.facilityName.options[window.document.iteminventory.facilityName.selectedIndex].text;
   //second reload work area for selected facility
   for (var i = document.iteminventory.workAreaName.length; i > 0;i--)
   {
      document.iteminventory.workAreaName.options[i] = null;
   }

	if (document.iteminventory.facilityName.length == 2)
   {
	   window.document.iteminventory.facilityName.selectedIndex = 1;
   }

   showWorkArealinks(facID);
   window.document.iteminventory.workAreaName.selectedIndex = 0;
}

function showFacilitylinks(selectedIndex)
{
    var showFac = hubID[selectedIndex];
    var nickNameValue = new Array();
    var nickNameValueid = new Array();
    nickNameValue = altFacDesc[showFac];
    nickNameValueid = altFacID[showFac];
    for (var i=0; i < nickNameValue.length; i++)
    {
        setFacility(i,nickNameValue[i],nickNameValueid[i])
    }
}

function setFacility(href,text,id)
{
    var optionName = new Option(text, id, false, false)
    document.iteminventory.facilityName.options[href] = optionName;
}

function facilityChanged(object)                //object is the parent's object
{
    removeAllHazards()
    addOptionItem(document.getElementById("invengrp"),'All','All Storage Areas');

    var artist;
    artist = object.options[object.selectedIndex].text;

         var selectedIndex = object.selectedIndex;
         //first reload work area for selected facility
    for (var i = document.iteminventory.workAreaName.length; i > 0;i--)
    {
        document.iteminventory.workAreaName.options[i] = null;
    }
    reloading = true;
    //showWorkArealinks(selectedIndex);
    showWorkArealinks(artist);
    window.document.iteminventory.workAreaName.selectedIndex = 0;

    if (document.iteminventory.workAreaName.length == 2)
    {
	   window.document.iteminventory.workAreaName.selectedIndex = 1;
   	workareaChanged();
    }
 }

function workareaChanged()
{
 removeAllHazards()
 addOptionItem(document.getElementById("invengrp"),'All','All Storage Areas');
 var buildingaddress  =  document.getElementById("buildingaddress");
 buildingaddress.innerHTML = "";

  loc = "/tcmIS/hub/itemshipto?Action=getinvgroups";

  var HubName  =  document.getElementById("facilityName").value;
  var facilityid  =  document.getElementById("workAreaName");
  HubName = HubName.replace(/#/gi, "%23");
  //alert("Get inventory Groups for "+facilityid.value+"  "+HubName+" ");
  if (facilityid.value == "All Facilities" && (document.iteminventory.workAreaName.length > 2) )
  {

  }
  else
  {
   var sourceHubName = null;
   try
   {
     var hubC  =  document.getElementById("hubC");
     sourceHubName =  hubC.options[hubC.selectedIndex].text;
     //var sourceHubNameObject = document.getElementById("sourceHubName");
     //sourceHubNameObject.value = sourceHubName;
   }
   catch (ex)
   {
     //alert("Non-Pickable Status");
   }

  sourceHubName = sourceHubName.replace(/#/gi, "%23");
  loc = loc + "&sourceHubName=" + sourceHubName;
  loc = loc + "&plantid=" + HubName;
  loc = loc + "&buildingid=" + facilityid.value;
  openWinGeneric(loc,"shiptoId","50","50","yes","130","160")
  }
}

function getbuildingaddress()
{
 var buildingaddress  =  document.getElementById("buildingaddress");
 buildingaddress.innerHTML = "";

 var invengrp  =  document.getElementById("invengrp").value;
 if (! (invengrp == "All"))
 {
  loc = "/tcmIS/hub/itemshipto?Action=getstorageadd";

  var HubName  =  document.getElementById("facilityName").value;
  var facilityid  =  document.getElementById("workAreaName").value;
  HubName = HubName.replace(/#/gi, "%23");
  facilityid = facilityid.replace(/#/gi, "%23");
  invengrp = invengrp.replace(/#/gi, "%23");

  var sourceHubName = null;
  try
   {
     var hubC  =  document.getElementById("hubC");
     sourceHubName =  hubC.options[hubC.selectedIndex].text;

     //var sourceHubNameObject = document.getElementById("sourceHubName");
     //sourceHubNameObject.value = sourceHubName;
   }
   catch (ex)
   {
     //alert("Non-Pickable Status");
   }
  
  sourceHubName = sourceHubName.replace(/#/gi, "%23");
  loc = loc + "&sourceHubName=" + sourceHubName;
  loc = loc + "&plantid=" + HubName;
  loc = loc + "&buildingid=" + facilityid;
  loc = loc + "&storagearea=" + invengrp;
  //alert(loc);
  openWinGeneric(loc,"shiptoId","50","50","yes","130","160")
 }
}

function removeAllHazards()
{
        removeAllOptionItem(document.getElementById("invengrp"));
}

function showWorkArealinks(showWA)
{
    var nickNameValue = new Array();
    var nickNameValueid = new Array();
    nickNameValue = altWADesc[showWA];
    nickNameValueid = altWAID[showWA];
    for (var i=0; i < nickNameValue.length; i++)
    {
        setWorkArea(i,nickNameValue[i],nickNameValueid[i])
    }
}

function setWorkArea(href,text,id)
{
    var optionName = new Option(text, id, false, false)
    document.iteminventory.workAreaName.options[href] = optionName;
}

function addOptionItem(obj,value,text) {
     obj.options[obj.length]=new Option(text,value);
}

function removeAllOptionItem(obj) {
     if(obj.length <= 0)
          return;
     var len = obj.length;
     for(i=0;i<len;i++)
          obj.options[0]=null;
}

function update(entered)
{
    window.document.iteminventory.UserAction.value = "UPDATE";
    window.document.iteminventory.SubUserAction.value = "UpdPage";

    try
    {
        var target = document.all.item("TRANSITPAGE");
        target.style["display"] = "";
        var target1 = document.all.item("MAINPAGE");
        target1.style["display"] = "none";
    }
    catch (ex)
    {
    }
    return true;
}

function search(entered)
{
	 var facilityName =  document.getElementById("facilityName");
    if ( facilityName.value != "All Plants" )
    {
    window.document.iteminventory.UserAction.value = "Search";
    window.document.iteminventory.SubUserAction.value = "NA";

    var searchText  =  document.getElementById("searchText");
    var searchlike  =  document.getElementById("searchlike");
    try
    {
     if (searchlike.value == "ITEM_ID")
     {
      if ( !(isInteger(searchText.value)) )
      {
        searchText.value = "";
      }
     }
    }
    catch (ex)
    {
     searchText.value = "";
    }

    try
    {
        var target = document.all.item("TRANSITPAGE");
        target.style["display"] = "";
        var target1 = document.all.item("MAINPAGE");
        target1.style["display"] = "none";
    }
    catch (ex)
    {
    }
    return true;
    }
	 else
    {
        alert("Please Select a Plant.");
        return false;
    }
}

function sendShipto( entered )
{
 with( entered )
 {
  eval( testmfgid="window.document.SupplierLike.shiptoid" )
  if ( ( eval( testmfgid.toString() ) ).value.length > 0 )
  {
     selectedRow=opener.document.getElementById( "shiptoid" );
     selectedRow.value=window.document.SupplierLike.shiptoid.value;
   }
 }
}

function addshiptoID( matidvalue )
{
     document.SupplierLike.shiptoid.value=matidvalue;
}

function senditemId( entered )
{
 with( entered )
 {
  eval( testmfgid="window.document.SupplierLike.itemid" )
  if ( ( eval( testmfgid.toString() ) ).value.length > 0 )
  {
     selectedRow=opener.document.getElementById( "searchText" );
     selectedRow.value=window.document.SupplierLike.itemid.value;
   }
 }
 window.close();
}

function additemID( matidvalue )
{
     document.SupplierLike.itemid.value=matidvalue;
}

/*
function invalidateshipto()
{
    var validfacility  =  document.getElementById("validfacilityid");
    if (validfacility.value == "Yes")
    {
        validfacility.value = "No";

        var facilityid  =  document.getElementById("facilityid");
        facilityid.className="INVALIDTEXT";
    }
}

function invalidateitemid()
{
    var validitemid  =  document.getElementById("validitemid");
    if (validitemid.value == "Yes")
    {
        validitemid.value = "No";

        var itemid  =  document.getElementById("itemid");
        itemid.className="INVALIDTEXT";
    }
}

function getshipTo()
{
	 loc = "/tcmIS/hub/itemshipto?Action=getshipto";

    var HubName  =  document.getElementById("HubName");
    var facilityid  =  document.getElementById("facilityid");
    loc = loc + "&HubName=" + HubName.value;
    loc = loc + "&SearchString=" + facilityid.value;
    openWinGeneric(loc,"shiptoId","500","400","yes","130","160")
}
*/

function getitemId()
{
	loc = "/tcmIS/hub/itemshipto?Action=getitemid";

   var HubName  =  document.getElementById("hubC");
   var plantid  =  document.getElementById("facilityName").value;
   var buildingid  =  document.getElementById("workAreaName");
   var searchText  =  document.getElementById("searchText");
   var searchlike  =  document.getElementById("searchlike");
   var invengrp  =  document.getElementById("invengrp").value;
   try
   {
     var sourceHubName = null;
     sourceHubName =  HubName.options[HubName.selectedIndex].text;

     //var sourceHubNameObject = document.getElementById("sourceHubName");
     //sourceHubNameObject.value = sourceHubName;
   }
   catch (ex)
   {
     //alert("Non-Pickable Status");
   }

   loc = loc + "&HubName=" + HubName.value;
   sourceHubName = sourceHubName.replace(/#/gi, "%23");
   loc = loc + "&sourceHubName=" + sourceHubName;
   plantid = plantid.replace(/#/gi, "%23");
   loc = loc + "&plantid=" + plantid;
   loc = loc + "&buildingid=" + buildingid.value;
   loc = loc + "&SearchString=" + searchText.value;
   loc = loc + "&searchlike=" + searchlike.value;
   invengrp = invengrp.replace(/#/gi, "%23");
   loc = loc + "&invengrp=" + invengrp;

   openWinGeneric(loc,"shiptoId","500","400","yes","130","160")
}


function openWinGeneric(destination,WinName,WinWidth, WinHeight, Resizable )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=yes,top=200,left=200,width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable;
    preview = window.open(destination, WinName,windowprops);
}