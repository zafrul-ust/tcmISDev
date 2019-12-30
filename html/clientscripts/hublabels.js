var submitcount=0;
var reportFieldCount = 0;

function assignPaper(paper)
{
window.document.hublabels.Paper.value =paper ;
}


function hubchanged(object)
{
	var artist;
   artist = object.options[object.selectedIndex].value;

   var selectedIndex = object.selectedIndex;

	for (var i = document.hublabels.hubroom.length; i > 0;i--)
   {
      document.hublabels.hubroom.options[i] = null;
   }
	showinvlinks(artist);
	window.document.hublabels.hubroom.selectedIndex = 0;
}

function showinvlinks(selectedIndex)
{
    var invgrpid = new Array();
    invgrpid = altinvid[selectedIndex];

	 var invgrpName = new Array();
    invgrpName = altinvName[selectedIndex];

    for (var i=0; i < invgrpid.length; i++)
    {
        setCab(i,invgrpName[i],invgrpid[i])
    }
}

function setCab(href,text,id)
{
    var optionName = new Option(text, id, false, false)
    document.hublabels.hubroom.options[href] = optionName;
}

function closeUserWin() {
   window.close();
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

function dobinPopup()
{
    var testurl5 = "/tcmIS/hub/binlbls?session=Active&generate_labels=yes&UserAction=GenerateLabels&genaction=generatebinlabels";
	 var HubNameqq  =  document.getElementById("HubName");
    testurl5 = testurl5 + "&HubNoforlabel=" + HubNameqq.value ;
    openWinGeneric(testurl5,"Generate_ca1b_labels","600","600","yes")
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

function generatebinlabels(entered)
{
    window.document.hublabels.UserAction.value = "generatebinlabels";

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
    window.document.hublabels.UserAction.value = "Search";
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
    for (var i = document.hublabels.workAreaName.length; i > 0;i--)
    {
        document.hublabels.workAreaName.options[i] = null;
    }
    reloading = true;
    //showWorkArealinks(selectedIndex);
    showWorkArealinks(artist);
    window.document.hublabels.workAreaName.selectedIndex = 0;

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
    document.hublabels.workAreaName.options[href] = optionName;
}

function openWinGeneric(destination,WinName,WinWidth, WinHeight,Resizable,topCoor,leftCoor )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=yes,top="+topCoor+",left="+leftCoor+",width="+WinWidth+",height="+WinHeight+",resizable="+ Resizable;
    preview = window.open(destination,WinName,windowprops);
}