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
		document.getElementById("quoteHistoryViewBean").style["display"] = "";

		initializeGrid();  
	}  
	else
	{
		document.getElementById("quoteHistoryViewBean").style["display"] = "none";   
	}

	/*This dislpays our standard footer message*/
	displayNoSearchSecDuration();

	/*It is important to call this after all the divs have been turned on or off.*/
	setResultSize();
	document.getElementById("mainUpdateLinks").style["display"] = "";
}


function initializeGrid(){
	 beangrid = new dhtmlXGridObject('quoteHistoryViewBean');

	 initGridWithConfig(beangrid,config,false,true);
	 if( typeof( jsonMainData ) != 'undefined' ) {
	 beangrid.parse(jsonMainData,"json");

	 }
	// beangrid.attachEvent("onRowSelect",selectRow);
	 beangrid.attachEvent("onRightClick",selectRightclick);
	}


function selectRightclick(rowId,cellInd){
	beangrid.selectRowById(rowId,null,false,false);	
	toggleContextMenu("openQuoteMenu");
}

function openQuoteTab() {
	var prNumber = cellValue(beangrid.getSelectedRowId(),"hprNumber");
//    var type = cellValue(beangrid.getSelectedRowId(),"type");
    var tabId = 'scratchPad'+prNumber+'';
    var loc = "/tcmIS/distribution/scratchpadmain.do?uAction=searchScratchPadId&scratchPadId="+prNumber+"&tabId="+encodeURIComponent(tabId);
 	
 	try
	{
		opener.parent.openIFrame(tabId,loc,'Q '+prNumber+'','','N');
    }
	catch (ex){
		try
		{
	        opener.parent.opener.parent.openIFrame(tabId,loc,'Q '+prNumber+'','','N');
	    }
		catch (ex)
		{
			openWinGeneric(loc,"scratchPad"+prNumber,"900","600","yes","80","80","yes");
		}
	}
}



