windowCloseOnEsc = true;
resizeGridWithWindow = true;
var beangrid = null;

function resultOnLoad()
{
	
	totalLines = document.getElementById("totalLines").value; 
	
	if (totalLines > 0)
	{
		document.getElementById("catalogItemSynonymBean").style["display"] = "";
		
		initializeGrid();
		parent.document.getElementById("showUpdateLink").style["display"] = ""; 
		parent.document.getElementById("addItemSpan").style["display"] = "none";
	}  
	else
	{
		document.getElementById("catalogItemSynonymBean").style["display"] = "none";   
	}

	/*This dislpays our standard footer message*/
	displayGridSearchDuration();

	/*It is important to call this after all the divs have been turned on or off.*/
	setResultFrameSize();
	
	if (totalLines == 0)
	{
		parent.document.getElementById('mainUpdateLinks').style["display"] = "";
		parent.document.getElementById("addItemSpan").style["display"] = "";
		parent.document.getElementById("showUpdateLink").style["display"] = "none"; 
				
	}
	  
}

function initializeGrid(){
   totalLines = document.getElementById("totalLines").value;
	if (totalLines > 0)
	{
		document.getElementById("catalogItemSynonymBean").style["display"] = "";
		beangrid = new dhtmlXGridObject('catalogItemSynonymBean');
		initGridWithConfig(beangrid,config,false,true,true);
		if( typeof( jsonMainData ) != 'undefined' ) {
				beangrid.parse(jsonMainData,"json");
				
	   // beangrid.attachEvent("onBeforeSelect",doOnBeforeSelect);
	   // beangrid.attachEvent("onRightClick",selectRightclick);
	   // beangrid.attachEvent("onRowSelect",doOnRowSelected);			
        }
	}  
	else
	{
		document.getElementById("catalogItemSynonymBean").style["display"] = "none";   
	}
}

function submitUpdate() {
	 $("uAction").value = 'update';
	 
	// if (beangrid != null) 
	       beangrid.parentFormOnSubmit();
	// submitOnlyOnce();
	 document.genericForm.submit();
	}



