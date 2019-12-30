var mygrid;
/*Set this to be false if you don't want the grid width to resize based on window size.*/
var resizeGridWithWindow = true;

function resultOnLoad()
{
 totalLines = document.getElementById("totalLines").value; 
 if (totalLines > 0)
 {
	 document.getElementById("createExcelLink").style["display"] = "";
	 document.getElementById("polchemDpmsStuckOrderBean").style["display"] = "";
	 doInitGrid();
 }  
 else 
 {
	 document.getElementById("createExcelLink").style["display"] = "none";
	 document.getElementById("polchemDpmsStuckOrderBean").style["display"] = "none";   
 }

/*This dislpays our standard footer message*/
 displayNoSearchSecDuration()

 /*It is important to call this after all the divs have been turned on or off.*/
 setResultSize();
}

function doInitGrid() {
	
	mygrid = new dhtmlXGridObject('polchemDpmsStuckOrderBean');

	// initGridWithConfig(inputGrid,config,rowSpan,submitDefault,singleClickEdit)
	// if rowSpan == true, sorting and smart rendering won't work; if
	// rowSpan == false, sorting and smart rendering will work
	// rowSpan == -1 is recommended for the page with update function
	// -1 is for disable rowSpan and smart rendering, but sorting still
	// works; false will disable rowSpan and sorting but smart rendering is
	// enabled
	// set submitDefault to true: Send the data to the server
	// singleClickEdit: this is for type:"txt",
	initGridWithConfig(mygrid, columnConfig, -1, false, false);
	if (typeof (jsonMainData) != 'undefined') {
		mygrid.parse(jsonMainData, "json");
	}
}

//Function for updating records.
function generateExcel(){
	var action = document.getElementById("action");
    action.value = 'createExcel';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_newGenerateExcel','650','600','yes');
    document.genericForm.target='_newGenerateExcel';
    var a = window.setTimeout("document.genericForm.submit();",500);
}