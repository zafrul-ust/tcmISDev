var submitcount=0;
var shipupdcount=0;
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


function newbin(entered)
{
    var newbindetails = "/tcmIS/Hub/Cabinetbin?session=Active&UserAction=newBin&hubC=";

    var HubName  =  document.getElementById("hubC");
    var facName  =  document.getElementById("facId");
    var wacName  =  document.getElementById("waName");

	 newbindetails = newbindetails + HubName.value+ "&facilityName=" +facName.value;

	 if (facName.value == "All Facilities")
	 {
		alert("Please select a Facility.");
	 }
	 else
    {
    openWinGeneric(newbindetails,"new_bin_details","500","400","yes")
    }
}

function newcab(entered)
{
    var newcaburl = "/tcmIS/Hub/Cabinetdef?session=Active&UserAction=newcab&hubC=";

    var HubName  =  document.getElementById("hubC");
    var facName  =  document.getElementById("facId");
    var wacName  =  document.getElementById("waName").value;
    wacName = wacName.replace(/&/gi, "%26");
    wacName = wacName.replace(/#/gi, "%23");

	 newcaburl = newcaburl + HubName.value+ "&facilityName=" +facName.value+ "&workAreaName=" + wacName;

    openWinGeneric(newcaburl,"new_cab_details","400","300","yes")
}


function addquit(entered)
{
    window.document.cabinetdefs.UserAction.value = "AddandQuit";

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

function addcont(entered)
{
    window.document.cabinetdefs.UserAction.value = "AddandContinue";

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
    window.document.cabinetdefs.UserAction.value = "Search";

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

function update(entered)
{
    window.document.cabinetdefs.UserAction.value = "Update";

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

function createxls(entered)
{
    loc = "/tcmIS/Hub/Cabinetdef?session=Active&UserAction=generatexls&receiptid=";
    openWinGeneric(loc,"Generate_XLS","700","600","yes");
}

function hubChanged(object)
{
   removeAllHazards();
   addOptionItem(document.getElementById("binids"),'All','All Cabinets');

   var artist;
   artist = object.options[object.selectedIndex].text;

   var selectedIndex = object.selectedIndex;
   //second reload work area for selected facility
   for (var i = document.cabinetdefs.facilityName.length; i > 0;i--)
   {
      document.cabinetdefs.facilityName.options[i] = null;
   }
   showFacilitylinks(selectedIndex);
   window.document.cabinetdefs.facilityName.selectedIndex = 0;
   //reset selected index the first element in the facility list
   selectedIndex = 1;
   var facID = window.document.cabinetdefs.facilityName.options[window.document.cabinetdefs.facilityName.selectedIndex].text;
   //second reload work area for selected facility
   for (var i = document.cabinetdefs.workAreaName.length; i > 0;i--)
   {
      document.cabinetdefs.workAreaName.options[i] = null;
   }

   showWorkArealinks(facID);
   window.document.cabinetdefs.workAreaName.selectedIndex = 0;
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
    document.cabinetdefs.facilityName.options[href] = optionName;
}

function closeUserWin() {
   window.close();
}

function getbins()
{
	var HubName  =  document.getElementById("hubC");
   var facName  =  document.getElementById("facId").value;
   facName = facName.replace(/&/gi, "%26");
   facName = facName.replace(/#/gi, "%23");
   var wacName  =  document.getElementById("waName").value;
   wacName = wacName.replace(/&/gi, "%26");
   wacName = wacName.replace(/#/gi, "%23");

   //alert(""+HubName.value+"  "+facName.value+"     "+wacName.value+" ");

   var binurl = "/tcmIS/Hub/Cabinetmgmt?session=Active&UserAction=getselbins&hubC=";
	binurl = binurl + HubName.value+ "&facilityName=" +facName+ "&workAreaName=" + wacName;

	openWinGeneric(binurl,"getHazardvalues","200","200","yes","50","50")
}

function changeminmaxcab(itemid,cabid,binid,cabname,wacname)
{
	var HubName  =  document.getElementById("hubC");
   var facName  =  document.getElementById("facId");
   //var wacName  =  document.getElementById("waName");

	var lelchgurl = "/tcmIS/Hub/Cabinetlvlchg?session=Active&UserAction=showlevels&hubC=";
	lelchgurl = lelchgurl + HubName.value+ "&facilityName=" +facName.value+ "&workAreaName=" + wacname + "&item_id=" + itemid+"&cabname="+ cabname + "&cabid="+cabid;

	openWinGeneric(lelchgurl,"getchang_elevelscreen","600","400","yes","50","50")
}

function removeAllHazards()
{
	removeAllOptionItem(document.getElementById("binids"));
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

function checkall(checkboxname,sartingvalue)
{
var checkall = document.getElementById("chkall");
var totallines = document.getElementById("totallines").value;
totallines = totallines*1;
var valueq;


var result ;
if (checkall.checked)
{
  result = true;
  valueq = "yes";
}
else
{
  result = false;
  valueq = "no";
}

//alert(result+"   "+totallines);

for ( var p = 0 ; p < totallines ; p ++)
{
        var linecheck = document.getElementById(""+checkboxname+""+(p+1)+"");
        linecheck.checked =result;
        linecheck.value = valueq;
}

}

function facilityChanged(object)		//object is the parent's object
{
    removeAllHazards()
    addOptionItem(document.getElementById("binids"),'All','All Cabinets');

    var artist;
    artist = object.options[object.selectedIndex].text;

	 var selectedIndex = object.selectedIndex;
	 //first reload work area for selected facility
    for (var i = document.cabinetdefs.workAreaName.length; i > 0;i--)
    {
        document.cabinetdefs.workAreaName.options[i] = null;
    }
    reloading = true;
    //showWorkArealinks(selectedIndex);
    showWorkArealinks(artist);
    window.document.cabinetdefs.workAreaName.selectedIndex = 0;

 }
function workareaChanged()
{
    removeAllHazards()
    addOptionItem(document.getElementById("binids"),'All','All Cabinets');
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
    document.cabinetdefs.workAreaName.options[href] = optionName;
}



function openWinGeneric(destination,WinName,WinWidth, WinHeight,Resizable,topCoor,leftCoor )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=yes,top="+topCoor+",left="+leftCoor+",width="+WinWidth+",height="+WinHeight+",resizable="+ Resizable;
    preview = window.open(destination,WinName,windowprops);
}