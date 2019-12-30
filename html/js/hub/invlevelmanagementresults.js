function init() 
{ 				/*This is to initialize the YUI*/
 	this.cfg = new YAHOO.util.Config(this);
 	if (this.isSecure)
 	{
  		this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
 	}

}

/*The reason for this is to show the error messages from the main page*/
function showErrorMessages()
{
  parent.showErrorMessages();
}

function myResultsBodyOnload()
{
    displaySearchDuration();
    setResultFrameSize();
 /*Dont show any update links if the user does not have permissions.
 Remove this section if you don't have any links on the main page
 if (!showUpdateLinks)
 {
  parent.document.getElementById("updateResultLink").style["display"] = "none";
 }
 else
 {
  parent.document.getElementById("updateResultLink").style["display"] = "";
 }
 */
}

// ----------------------------- from invlevelmgmt.js:

function showMinMax(itemID, invengrp)
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
	//var HubName  =  document.getElementById("HubName");
	var hub  =  document.getElementById("sourceHub").value;

	//var invengrp  =  document.getElementById("invengrp");
/*
	loc = "/tcmIS/hub/minmaxchg?UserAction=showlevels&searchlike=ITEM_ID&searchtext="
    loc = loc + itemID;
    loc = loc + "&HubName=" + hub;
    loc = loc + "&invengrp=" + invengrp;
*/
    // alert("url = [" + loc + "]; ");

	loc = "/tcmIS/hub/minmaxlevelmain.do?uAction=showlevels&criterion=itemId&criteria="
    loc = loc + itemID;
    loc = loc + "&hub=" + hub;
    loc = loc + "&inventoryGroup=" + invengrp;

    openWinGeneric(loc,"Show_Min_Max", "800", "600", "yes")
}

function showcrosstab(itemID,invengrp)
{
	// var HubName  =  document.getElementById("HubName");
	var hub  =  document.getElementById("sourceHub").value;
	//alert("hub = [" + hub + "]; ");
	 
	 //var invengrp  =  document.getElementById("invengrp");
	 loc = "/tcmIS/hub/crosstablevels?UserAction=showcrosstablevels&searchlike=ITEM_ID&SearchField="
    loc = loc + itemID;
    loc = loc + "&HubName=" + hub;
    loc = loc + "&invengrp=" + invengrp;
    // alert("url = [" + loc + "]; ");
    
    openWinGeneric(loc, "Show_cross_tab", "600", "400", "yes");
}
