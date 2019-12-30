var beangrid;
var openSPadCounter=1;
/*Set this to be false if you don't want the grid width to resize based on window size.*/
var resizeGridWithWindow = true;

function resultOnLoad()
{
	/*
	try{

		if (!showUpdateLinks) //Dont show any update links if the user does not have permissions/
		{
			document.getElementById("updateResultLink").style["display"] = "none";
		}
		else
		{
			document.getElementById("updateResultLink").style["display"] = "";

		}
	}
	catch(ex)
	{}
	
	*/

	totalLines = document.getElementById("totalLines").value; 

	if (totalLines > 0)
	{
		document.getElementById("poHistoryViewBean").style["display"] = "";

		initializeGrid();  
	}  
	else
	{
		document.getElementById("poHistoryViewBean").style["display"] = "none";   
	}

	/*This dislpays our standard footer message*/
	displayNoSearchSecDuration();

	/*It is important to call this after all the divs have been turned on or off.*/
	setResultSize();
	document.getElementById("mainUpdateLinks").style["display"] = "";
}


function initializeGrid(){
	 beangrid = new dhtmlXGridObject('poHistoryViewBean');

	 initGridWithConfig(beangrid,config,false,true);
	 if( typeof( jsonMainData ) != 'undefined' ) {
	 	beangrid.parse(jsonMainData,"json");
	 }
	 beangrid.attachEvent("onRightClick",selectRightclick);
}

function selectRightclick(rowId,cellInd){
	beangrid.selectRowById(rowId,null,false,false);	
	toggleContextMenu("openPOMenu");
}

function showPurchOrder()
{
	var HaasPO = cellValue(beangrid.getSelectedRowId(),"hRadianPo");
    var loc = "/tcmIS/supply/purchaseorder.do?po="+HaasPO+"&Action=searchlike&subUserAction=po";
    
 	try
	{
		opener.parent.openIFrame("purchaseOrder"+HaasPO+"",loc,""+messagesData.purchaseorder+" "+HaasPO+"","","N");
    }
	catch (ex){
		try
		{
	        opener.parent.opener.parent.openIFrame("purchaseOrder"+HaasPO+"",loc,""+messagesData.purchaseorder+" "+HaasPO+"","","N");
	    }
		catch (ex)
		{
			openWinGeneric(loc,"showradianPo","800","600","yes","50","50");
		}
	}
}



