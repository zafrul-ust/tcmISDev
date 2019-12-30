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

function showallocationpagelegend()
{
        openWinGeneric("/tcmIS/help/allocationpagelegend.html","allocationpagelegend","290","300","no","50","50")
}

function hubchanged(object)
{
	var artist;
   artist = object.options[object.selectedIndex].value;

   var selectedIndex = object.selectedIndex;

	for (var i = document.allocationana.invengrp.length; i > 0;i--)
   {
      document.allocationana.invengrp.options[i] = null;
   }
	showinvlinks(artist);
	window.document.allocationana.invengrp.selectedIndex = 0;

    var indexselectec = object.options[object.selectedIndex]
    var indexofxompany = object.options[object.selectedIndex].value;
    for (var i = document.allocationana.FacName.length;i > 0;i--)
    {
        document.allocationana.FacName.options[i] = null;
    }
    reloading = true;
    showlinks(indexofxompany);
    window.document.allocationana.FacName.selectedIndex = 0;
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
    document.allocationana.invengrp.options[href] = optionName;
}


function reshow(object)
{
    artist = object.options[object.selectedIndex].text;

}

function showlinks(selectedIndex)
{
    var indextoshow = hubnumbers[selectedIndex];
    var companytoshow = hubnames[indextoshow];
    var nickNameValue = new Array();
    var nickNameValueid = new Array();
    nickNameValue = altName[companytoshow];
    nickNameValueid = altNameid[companytoshow];
    for (var i=0; i < nickNameValue.length; i++)
    {
        opt(i,nickNameValue[i],nickNameValueid[i])
    }
}

function opt(href,text,id)
{
    var optionName = new Option(text, id, false, false)
    document.allocationana.FacName.options[href] = optionName;
}

function openWinGeneric(destination,WinName,WinWidth, WinHeight, Resizable )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=no,top=200,left=200,width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable;
    preview = window.open(destination, WinName,windowprops);
}


function search(entered)
{
    window.document.allocationana.UserAction.value = "Search";
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
    loc = "/tcmIS/Hub/AllocationAnalysis?UserAction=createxls";

   /*var HubName  =  document.getElementById("HubName");
   loc = loc + "&HubName="+HubName.value;

	 var selectElementStatus  =  document.getElementById("selectElementStatus");
	 loc = loc + "&selectElementStatus="+selectElementStatus.value;

	var within  =  document.getElementById("within");
	 loc = loc + "&within="+within.value;

	var mrnum  =  document.getElementById("mrnum");
	 loc = loc + "&mrnum="+mrnum.value;

	var progressstat  =  document.getElementById("progressstat");
	 loc = loc + "&progressstat="+progressstat.value;

	var itemid  =  document.getElementById("itemid");
	 loc = loc + "&itemid="+itemid.value;

	var sortBy  =  document.getElementById("sortBy");
	 loc = loc + "&sortBy="+sortBy.value;*/

    openWinGeneric(loc,"ItemId","800","600","yes");
}

function showallNotes(comments,mrline)
{
	windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=yes,top=240,left=280,width=550,height=250,resizable=yes";
   preview = window.open("","ddavv",windowprops);
   preview.document.write("<HTML><HEAD>\n");
   preview.document.write("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=iso-8859-1\">\n");
   preview.document.write("<TITLE>Notes for "+mrline+" </TITLE>\n");
   preview.document.write("</HEAD>  \n");
   preview.document.write("<BODY onBlur=\"self.focus()\">\n");
   preview.document.write("<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"0\" WIDTH=\"100%\">\n");
   preview.document.write("<TR><TD WIDTH=\"70%\" ALIGN=\"LEFT\" HEIGHT=\"22\" BGCOLOR=\"#000066\"><FONT FACE=\"Arial\" COLOR=\"#FFFFFF\"><B>Notes for "+mrline+"</B></FONT></TD></TR></TABLE>\n");
   preview.document.write("<TR><TD>&nbsp;</TD></TR>\n");
   preview.document.write("<TABLE BORDER=\"0\" CELLPADDING=\"3\" cellspacing=\"3\" BGCOLOR=\"#FFFFFF\">\n");
   preview.document.write("<TR><TD WIDTH=\"40%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
   preview.document.write("<FONT FACE=\"Arial\">"+comments+"</FONT></TD></TR>\n");
   preview.document.write("<TR><TD WIDTH=\"40%\" ALIGN=\"CENTER\" VALIGN=\"MIDDLE\">\n");
	preview.document.write("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Close\" onClick= \"javascript:window.close()\" NAME=\"closeItemNoteB\">\n");

   preview.document.write("</TD></TR></TABLE></BODY></HTML>\n");
}

function cancel()
{
    window.close();
}