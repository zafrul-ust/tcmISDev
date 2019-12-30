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

function search(entered)
{
   window.document.cabinetcounts.UserAction.value = "Search";
  	binids =document.getElementById("binids");

if ("All" ==binids.value || binids.value.length == 0)
{
 alert("Please do not select \"All Cabinets\".\n\nThis option is not currently supported on this page.");
 return false;
}
else
{
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
}

function updatecount(entered)
{
    window.document.cabinetcounts.UserAction.value = "Update";

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
    loc = "/tcmIS/hub/cabinetcount?session=Active&UserAction=generatexls&receiptid=";
    openWinGenericViewFile(loc,"Generate_XLS","700","600","yes");
}

function hubChanged(object)
{
   removeAllHazards();
   addOptionItem(document.getElementById("binids"),'All','All Cabinets');

   var artist;
   artist = object.options[object.selectedIndex].text;

   var selectedIndex = object.selectedIndex;
   //second reload work area for selected facility
   for (var i = document.cabinetcounts.facilityName.length; i > 0;i--)
   {
      document.cabinetcounts.facilityName.options[i] = null;
   }
   showFacilitylinks(selectedIndex);
   window.document.cabinetcounts.facilityName.selectedIndex = 0;
   //reset selected index the first element in the facility list
   selectedIndex = 1;
   var facID = window.document.cabinetcounts.facilityName.options[window.document.cabinetcounts.facilityName.selectedIndex].text;
   //second reload work area for selected facility
   for (var i = document.cabinetcounts.workAreaName.length; i > 0;i--)
   {
      document.cabinetcounts.workAreaName.options[i] = null;
   }

   showWorkArealinks(facID);
   window.document.cabinetcounts.workAreaName.selectedIndex = 0;
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
    document.cabinetcounts.facilityName.options[href] = optionName;
}

function closeUserWin() {
   window.close();
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

function facilityChanged(object)		//object is the parent's object
{
    removeAllHazards()
    addOptionItem(document.getElementById("binids"),'All','All Cabinets');

    var artist;
    artist = object.options[object.selectedIndex].text;

	 var selectedIndex = object.selectedIndex;
	 //first reload work area for selected facility
    for (var i = document.cabinetcounts.workAreaName.length; i > 0;i--)
    {
        document.cabinetcounts.workAreaName.options[i] = null;
    }
    reloading = true;
    //showWorkArealinks(selectedIndex);
    showWorkArealinks(artist);
    window.document.cabinetcounts.workAreaName.selectedIndex = 0;

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
    document.cabinetcounts.workAreaName.options[href] = optionName;
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



function CheckQtyValue(delname)
{
    var allClear = 0;
    finalMsgt = "Please enter valid values for: \n\n";

    testqty = document.getElementById("qty_picked"+ delname.toString() + "");
	 var v =testqty.value;

    if ( testqty.value*1 < 0 )
    {
        finalMsgt = finalMsgt + " Quantity Picked. \n";
        allClear = 1;
        testqty.value = "";
    }
    else if ( !(isInteger(v)) )
    {
        finalMsgt = finalMsgt + " Quantity Picked. \n";
        allClear = 1;
        testqty.value = "";
    }

    if (allClear > 0)
    {
		alert(finalMsgt);
    }
}

function isEmpty(s)
{
    return ((s == null) || (s.length == 0))
}
function isDigit (c)
{
    return ((c >= "0") && (c <= "9"))
}
function isInteger (s)
{
    var i;
    if (isEmpty(s))
        if (isInteger.arguments.length == 1) return defaultEmptyOK;
        else return (isInteger.arguments[1] == true);
    for (i = 0; i < s.length; i++)
    {
        // Check that current character is number.
        var c = s.charAt(i);
        if (!isDigit(c)) return false;
    }
    return true;
}

function openWinGeneric(destination,WinName,WinWidth, WinHeight,Resizable,topCoor,leftCoor )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=yes,top="+topCoor+",left="+leftCoor+",width="+WinWidth+",height="+WinHeight+",resizable="+ Resizable;
    preview = window.open(destination,WinName,windowprops);
}

function openWinGenericViewFile(destination,WinName,WinWidth, WinHeight, Resizable,topCoor,leftCoor,scrollbars )
{
  if (topCoor == null || topCoor.trim().length == 0)
  {
    topCoor = "200";
  }

  if (leftCoor == null || leftCoor.trim().length == 0)
  {
    leftCoor = "200";
  }

  if (scrollbars == null || scrollbars.trim().length == 0)
  {
    scrollbars = "yes";
  }

  windowprops = "toolbar=no,location=no,directories=no,menubar=yes,scrollbars=yes,status=no,top="+topCoor+",left="+leftCoor+",width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable;
  preview = window.open(destination, WinName,windowprops);
}