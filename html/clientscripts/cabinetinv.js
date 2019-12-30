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

function showinventorydetail(itemid,hub)
{
   var binurl = "/tcmIS/hub/inventorydetails?hubNum=";
	binurl = binurl + hub + "&itemid=" +itemid;

	openWinGeneric(binurl,"getinventorydetails","600","400","yes","50","50")
}

function search(entered)
{
	if(validateSearch())
		{
		    window.document.invoicereports.UserAction.value = "Search";
		
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
	
		alert('Item Id must be an integer');
		return false;

		
}

function validateSearch()
{
	var searchwhat = document.getElementsByName('searchwhat');
	
	if(searchwhat[0].value == 'ITEM_ID')
		{
			var searchBox = document.getElementsByName('searchtext');
			if(!checkInteger(searchBox[0].value))
				return false;
		}
	return true;
}

function checkInteger(s)
{
    for (i = 0; i < s.length; i++)
    {
        // Check that current character is number.
        var c = s.charAt(i);
        if (!isDig(c)) 
        	return false;
    }
    return true;
}

function isDig (c)
{  
	return ((c >= "0") && (c <= "9"))
}

function update(entered)
{
    window.document.invoicereports.UserAction.value = "Update";

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
    loc = "/tcmIS/hub/cabinventory?session=Active&UserAction=generatexls&receiptid=";
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
   for (var i = document.invoicereports.facilityName.length; i > 0;i--)
   {
      document.invoicereports.facilityName.options[i] = null;
   }
   showFacilitylinks(selectedIndex);
   window.document.invoicereports.facilityName.selectedIndex = 0;
   //reset selected index the first element in the facility list
   selectedIndex = 1;
   var facID = window.document.invoicereports.facilityName.options[window.document.invoicereports.facilityName.selectedIndex].text;
   //second reload work area for selected facility
   for (var i = document.invoicereports.workAreaName.length; i > 0;i--)
   {
      document.invoicereports.workAreaName.options[i] = null;
   }

   showWorkArealinks(facID);
   window.document.invoicereports.workAreaName.selectedIndex = 0;
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
    document.invoicereports.facilityName.options[href] = optionName;
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

   var binurl = "/tcmIS/hub/cabinventory?session=Active&UserAction=getselbins&hubC=";
	binurl = binurl + HubName.value+ "&facilityName=" +facName+ "&workAreaName=" + wacName;

	openWinGeneric(binurl,"getHazardvalues","200","200","yes","50","50")
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
    for (var i = document.invoicereports.workAreaName.length; i > 0;i--)
    {
        document.invoicereports.workAreaName.options[i] = null;
    }
    reloading = true;
    //showWorkArealinks(selectedIndex);
    showWorkArealinks(artist);
    window.document.invoicereports.workAreaName.selectedIndex = 0;

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
    document.invoicereports.workAreaName.options[href] = optionName;
}



function openWinGeneric(destination,WinName,WinWidth, WinHeight,Resizable,topCoor,leftCoor )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=yes,top="+topCoor+",left="+leftCoor+",width="+WinWidth+",height="+WinHeight+",resizable="+ Resizable;
    preview = window.open(destination,WinName,windowprops);
}