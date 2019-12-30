var submitcount=0;
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

function showcrosstab(itemID,invengrp)
{
	 var HubName  =  document.getElementById("HubName");
	 //var invengrp  =  document.getElementById("invengrp");
	 loc = "/tcmIS/hub/crosstablevels?UserAction=showcrosstablevels&searchlike=ITEM_ID&SearchField="
    loc = loc + itemID;
    loc = loc + "&HubName=" + HubName.value;
    loc = loc + "&invengrp=" + invengrp;
    openWinGeneric(loc,"Show_cross_tab","400","400","yes")
}

function hubchanged(object)
{
 var HubName  =  document.getElementById("HubName");
 artist = HubName.value;

 //var artist;
 //artist = object.options[object.selectedIndex].value;

   var selectedIndex = object.selectedIndex;

	for (var i = document.iteminventory.invengrp.length; i > 0;i--)
   {
      document.iteminventory.invengrp.options[i] = null;
   }
	showinvlinks(artist);
	window.document.iteminventory.invengrp.selectedIndex = 0;
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
    document.iteminventory.invengrp.options[href] = optionName;
}

function actionValue(name, entered)
{
    var actvalue = name.toString();
    window.document.iteminventory.UserAction.value = actvalue;

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

function openWinGeneric(destination,WinName,WinWidth, WinHeight, Resizable )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=yes,top=200,left=200,width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable;
    preview = window.open(destination, WinName,windowprops);
}

function showMinMax(itemID,invengrp)
{
	/*if (hubname == 'Goleta Rio Hub')
	{
	loc = "/cgi-bin/rayedit/golmmchk.cgi?bp=";
	}
	else if (hubname == 'El Segundo Hub')
	{
	loc = "/cgi-bin/rayedit/elsegmmchk.cgi?bp=";
	}
	else
	{
	 loc = "/cgi-bin/radadmin/mmchk.cgi?bp=";
	}*/

	var HubName  =  document.getElementById("HubName");
	//var invengrp  =  document.getElementById("invengrp");
	 loc = "/tcmIS/hub/minmaxchg?displaySearchOptions=No&UserAction=showlevels&searchlike=ITEM_ID&searchtext="
    loc = loc + itemID;
    loc = loc + "&HubName=" + HubName.value;
    loc = loc + "&invengrp=" + invengrp;
    openWinGeneric(loc,"Show_Min_Max","600","600","yes")
}

function createxls(entered)
{
    loc = "/tcmIS/Hub/LevelMgmt?UserAction=CreateExl";
    openWinGenericXls(loc,"ItemId","800","600","yes");
}

function openWinGenericXls(destination,WinName,WinWidth, WinHeight, Resizable )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=yes,scrollbars=yes,status=yes,top=200,left=200,width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable;
    preview = window.open(destination, WinName,windowprops);
}

function cancel()
{
    window.close();
}