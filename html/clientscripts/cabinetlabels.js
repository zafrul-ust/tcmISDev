var submitcount=0;
var reportFieldCount = 0;
var noPopUp = false;

function assignPaper(paper)
{
window.document.cabinetlabels.Paper.value =paper ;
}

function addquitbin(entered)
{
    window.document.cabinetlabels.UserAction.value = "AddandQuitbin";

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

function addcontbin(entered)
{
    window.document.cabinetlabels.UserAction.value = "AddandContinuebin";

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

function wacabchanged(object)
{
	var artist;
   artist = object.options[object.selectedIndex].text;

   var selectedIndex = object.selectedIndex;

	for (var i = document.cabinetlabels.cabName.length; i > 0;i--)
   {
      document.cabinetlabels.cabName.options[i] = null;
   }

	showcablinks(selectedIndex);
	window.document.cabinetlabels.cabName.selectedIndex = 0;
}

function showcablinks(selectedIndex)
{
    var showFac = altWaId[selectedIndex];
    var nickNameValue = new Array();
    var nickNameValueid = new Array();
    nickNameValue = altCabDesc[showFac];
    nickNameValueid = altCabId[showFac];
    for (var i=0; i < nickNameValue.length; i++)
    {
        setCab(i,nickNameValue[i],nickNameValueid[i])
    }
}

function setCab(href,text,id)
{
    var optionName = new Option(text, id, false, false)
    document.cabinetlabels.cabName.options[href] = optionName;
}

function hubChanged(object)
{
   //removeAllHazards();
   //addOptionItem(document.getElementById("binids"),'All','All Cabinets');

   var artist;
   artist = object.options[object.selectedIndex].text;

   var selectedIndex = object.selectedIndex;
   //second reload work area for selected facility
   for (var i = document.cabinetlabels.facilityName.length; i > 0;i--)
   {
      document.cabinetlabels.facilityName.options[i] = null;
   }
   showFacilitylinks(selectedIndex);
   window.document.cabinetlabels.facilityName.selectedIndex = 0;
   //reset selected index the first element in the facility list
   selectedIndex = 1;
   var facID = window.document.cabinetlabels.facilityName.options[window.document.cabinetlabels.facilityName.selectedIndex].text;
   //second reload work area for selected facility
   for (var i = document.cabinetlabels.workAreaName.length; i > 0;i--)
   {
      document.cabinetlabels.workAreaName.options[i] = null;
   }

   showWorkArealinks(facID);
   window.document.cabinetlabels.workAreaName.selectedIndex = 0;
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
    document.cabinetlabels.facilityName.options[href] = optionName;
}

function closeUserWin() {
   window.close();
}

function docaballlabelsPopup() {
	var testurl5 = "/tcmIS/Hub/Cabinetlabel?session=Active&generate_labels=yes&UserAction=GenerateLabels&genaction=allcabilabels";
	var paperSize  =  window.document.cabinetlabels ? window.document.cabinetlabels.Paper.value : '31';
	testurl5 = testurl5 + "&paperSize=" + paperSize ;
	var HubName  =  document.getElementById("hubC");
	testurl5 = testurl5 + "&HubNoforlabel=" + (HubName ? HubName.value : getUrlParameter("HubNoforlabel"));
	if (noPopUp) {
		testurl5 += "&printTxtFile=yes";
		window.location = testurl5;
	}
	else {
		openWinGeneric(testurl5,"Generate_ca1b_labels","600","600","yes");
	}
}

function generateallcabbinlabels(entered)
{
    window.document.cabinetlabels.UserAction.value = "generateallcablabels";

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

function docabPopup(){
	var testurl5 = "/tcmIS/Hub/Cabinetlabel?session=Active&generate_labels=yes&UserAction=GenerateLabels&genaction=generatecablabels";
	var paperSize  =  window.document.cabinetlabels ? window.document.cabinetlabels.Paper.value : '31';
	testurl5 = testurl5 + "&paperSize=" + paperSize ;
	var HubName  =  document.getElementById("hubC");
	testurl5 = testurl5 + "&HubNoforlabel=" + (HubName ? HubName.value : getUrlParameter("HubNoforlabel"));
	if (noPopUp) {
		testurl5 += "&printTxtFile=yes";
		window.location = testurl5;
	}
	else {
		openWinGeneric(testurl5,"Generate_ca1b_labels","600","600","yes");
	}
}

function docaballPopup() {
	var testurl5 = "/tcmIS/Hub/Cabinetlabel?session=Active&generate_labels=yes&UserAction=GenerateLabels&genaction=generatecabbinlabels";
	var paperSize  =  window.document.cabinetlabels ? window.document.cabinetlabels.Paper.value : '31';
	testurl5 = testurl5 + "&paperSize=" + paperSize ;
	var HubName  =  document.getElementById("hubC");
	testurl5 = testurl5 + "&HubNoforlabel=" + (HubName ? HubName.value : getUrlParameter("HubNoforlabel"));
	if (noPopUp) {
		testurl5 += "&printTxtFile=yes";
		window.location = testurl5;
	}
	else {
		openWinGeneric(testurl5,"Generate_bin_sslabels","600","600","yes");
	}
}


function dobaselinePopup(){
	var testurl5 = "/tcmIS/Hub/Cabinetlabel?session=Active&generate_labels=yes&UserAction=GenerateLabels&genaction=generatebaseline";
	var paperSize  =  window.document.cabinetlabels ? window.document.cabinetlabels.Paper.value : '31';
	testurl5 = testurl5 + "&paperSize=" + paperSize ;
	var HubName  =  document.getElementById("hubC");
	testurl5 = testurl5 + "&HubNoforlabel=" + (HubName ? HubName.value : getUrlParameter("HubNoforlabel"));
	if (noPopUp) {
		testurl5 += "&printTxtFile=yes";
		window.location = testurl5;
	}
	else {
		openWinGeneric(testurl5,"Generate_Baseline","600","600","yes")
	}
}

function getUrlParameter( name ) {
  name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
  var regexS = "[\\?&]"+name+"=([^&#]*)";
  var regex = new RegExp( regexS );
  var results = regex.exec( window.location.href );
  if( results == null )
    return "";
  else
    return results[1];
}

function getbins()
{
	var HubName  =  document.getElementById("hubC");
   var facName  =  document.getElementById("facId");
   var wacName  =  document.getElementById("waName");

   //alert(""+HubName.value+"  "+facName.value+"     "+wacName.value+" ");

   var binurl = "/tcmIS/Hub/Cabinetlabel?session=Active&UserAction=getselbins&hubC=";
	binurl = binurl + HubName.value+ "&facilityName=" +facName.value+ "&workAreaName=" + wacName.value;

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


function showbindetails(cabinetid,hubname,facilityId)
{
    var bindetails = "/tcmIS/Hub/Cabinetbin?session=Active&UserAction=Bindetails&cabinetId=" +cabinetid;
    bindetails += "&hubC=" + hubname ;
    bindetails += "&facilityName=" + facilityId ;
    openWinGeneric(bindetails,"bin_details","700","400","yes")
}

function dobinlabelspopup()
{
    var testurl5 = "/tcmIS/Hub/Cabinetbin?session=Active&generate_labels=yes&UserAction=GenerateLabels&genaction=generatebinlistlabels";
	var paperSize  =  window.document.cabinetlabels.Paper.value;
    testurl5 = testurl5 + "&paperSize=" + paperSize ;
    var HubName  =  document.getElementById("hubC");
    testurl5 = testurl5 + "&HubNoforlabel=" + HubName.value ;
	if (noPopUp) {
		testurl5 += "&printTxtFile=yes";
		window.location = testurl5;
	}
	else {
		openWinGeneric(testurl5,"Generate_bin_labels","600","600","yes");
	}
}


function checkValues(name,entered)
{
   var linecheck = document.getElementById(""+name+"");
   if (linecheck.checked)
   {
        linecheck.value = "yes";
   }
   else
   {
        linecheck.value = "no";
   }
}


function generatebinlistlabels(entered)
{
    window.document.cabinetlabels.UserAction.value = "generatebinlabels";

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

function generatebaseline(entered)
{
    window.document.cabinetlabels.UserAction.value = "generatebaseline";

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

function generatecabbinlabels(entered)
{
    window.document.cabinetlabels.UserAction.value = "generatecabbinlabels";

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

function generatecabbinlabels(entered)
{
    window.document.cabinetlabels.UserAction.value = "generatecabbinlabels";

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

function generatecablabels(entered)
{
    window.document.cabinetlabels.UserAction.value = "generatecablabels";

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

function search(entered)
{
    window.document.cabinetlabels.UserAction.value = "Search";
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


function facilityChanged(object)		//object is the parent's object
{
    //removeAllHazards()
    //addOptionItem(document.getElementById("binids"),'All','All Cabinets');

    var artist;
    artist = object.options[object.selectedIndex].text;

	 var selectedIndex = object.selectedIndex;
	 //first reload work area for selected facility
    for (var i = document.cabinetlabels.workAreaName.length; i > 0;i--)
    {
        document.cabinetlabels.workAreaName.options[i] = null;
    }
    reloading = true;
    //showWorkArealinks(selectedIndex);
    showWorkArealinks(artist);
    window.document.cabinetlabels.workAreaName.selectedIndex = 0;

 }
function workareaChanged()
{
    //removeAllHazards()
    //addOptionItem(document.getElementById("binids"),'All','All Cabinets');
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
    document.cabinetlabels.workAreaName.options[href] = optionName;
}



function openWinGeneric(destination,WinName,WinWidth, WinHeight,Resizable,topCoor,leftCoor )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=yes,top="+topCoor+",left="+leftCoor+",width="+WinWidth+",height="+WinHeight+",resizable="+ Resizable;
    preview = window.open(destination,WinName,windowprops);
}
