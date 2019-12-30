function submitSearchForm() {
	document.genericForm.target = 'resultFrame';
	document.getElementById("uAction").value = 'search';
	// set start search time
	var now = new Date();
	var startSearchTime = document.getElementById("startSearchTime");
	startSearchTime.value = now.getTime();
	var flag = true; //validateForm();
	if (flag) {
		showPleaseWait();
		return true;
	}
	else {
		return false;
	}
}

var mygrid;
var selectedRowId = null;
// Set this to be false if you don't want the grid width to resize based on window size.
var resizeGridWithWindow = true;

var multiplePermissions = true;
// Build up the array for the columns which use different permissions
var permissionColumns = new Array();
permissionColumns = {"deleteCurrency" : true ,
					 "currencyId" : true};
					
var showUpdateLinks = false;
/*
 * This is really the same as before. Except now there is a call to initialize
 * the grid.
 */
function resultOnLoad() {

		if (!showUpdateLinks) // Dont show any update links if the user does not have permissions
			parent.document.getElementById("updateResultLink").style["display"] = "none";
		else
			parent.document.getElementById("updateResultLink").style["display"] = "";

		// make result area visible if data exist
		document.getElementById("currencyBean").style["display"] = "";
		// build the grid for display
		doInitGrid();

	/* This displays our standard footer message */
	//displayGridSearchDuration();

	/*
	 * It is important to call this after all the divs have been turned on
	 * or off.
	 */
	setResultFrameSize();
}

function doInitGrid() {
	mygrid = new dhtmlXGridObject('currencyBean');

	// initGridWithConfig(inputGrid,config,rowSpan,submitDefault,singleClickEdit)
	initGridWithConfig(mygrid, config, -1, true, false);
	if (typeof (jsonMainData) != 'undefined') {
		mygrid.parse(jsonMainData, "json");
	}

	// mygrid.attachEvent("onBeforeSelect",doOnBeforeSelect);
	mygrid.attachEvent("onRowSelect", selectRow);
	//mygrid.attachEvent("onRightClick", selectRightclick);
}

function selectRow(rowId, cellInd) {
	selectedRowId = rowId; 
}

function updateCurrency() {
	if (validationforUpdate()) {
		document.genericForm.target = '';
		document.getElementById('uAction').value = 'update';

		parent.showPleaseWait(); 

		if (mygrid != null)
			mygrid.parentFormOnSubmit(); 

		document.genericForm.submit(); 
	}
}

// validate the whole grid
function validationforUpdate() {
	var rowsNum = mygrid.getRowsNum();;
	var defaultCount = 0;
	var notdeletecount = 0;
	for ( var p = 1; p < (rowsNum + 1); p++) {
		if (cellValue(p, "currencyId") == "") {
			mygrid.selectRowById(p, null, false, false);
			alert(messagesData.missingcurrency);
			return false;
		}
		if (cellValue(p, "remittanceId") == "" && cellValue(p, "deleteCurrency") != "true" ) {
			mygrid.selectRowById(p, null, false, false);
			alert(messagesData.missingremittance);
			return false;
		}
		
		if( cellValue(p, "deleteCurrency") != "true") 
			notdeletecount++;
		
		if (cellValue(p, "defaultCurrency") == "true" && cellValue(p, "deleteCurrency") != "true") {
			defaultCount++;
		}
	}
	
	if(defaultCount == 1 || notdeletecount == 0 ) {
		return true;
	} else if(defaultCount == 0 ) 
		alert(messagesData.mustchooseonedefault);
	else
		alert(messagesData.onlyonedefaultallowed);

	return false;
}

function buildNewCurValudset() {
	var newItemIdArray = new Array();
	for( i=0;i < currencyIdArr.length; i++) 
		newItemIdArray[currencyIdArr[i].value] = {text:currencyIdArr[i].text,value:currencyIdArr[i].value};

//	for( rowid = 1; rowid <= ind; rowid++ ) {
//  delete the incoming opsentity id	
//	delete( newItemIdArray[$v('opsEntityId')] ) ;
	for( rowid in rowids ) {
		val = gridCellValue(haasGrid,rowid,"currencyId");
		if( val )
			delete( newItemIdArray[val] ) ;
	}
	var valArray = new Array();
	for( v in newItemIdArray )
		valArray[valArray.length] = newItemIdArray[v];
	return valArray;
}

function addCurrency() {

   var ind = mygrid.getRowsNum();  
   var rowid = ind*1+1;
   
   var arr = buildNewCurValudset();
   if( !arr || !arr.length || arr.length ==1 ) return; // empty or just 'please select'
   rowids[rowid] = rowid;
   currencyId[ rowid ] = arr;
   remittanceId[ rowid ] = buildRemittanceId(currencyId[rowid].value );
  
   var thisrow = mygrid.addRow(rowid,"",ind);
 
   mygrid.selectRow(mygrid.getRowIndex(rowid),null,false,false);
     
   mygrid.cells(rowid,mygrid.getColIndexById("permission")).setValue('Y');
   mygrid.cells(rowid,mygrid.getColIndexById("currencyIdPermission")).setValue('Y');
   mygrid.cells(rowid,mygrid.getColIndexById("deleteCurrencyPermission")).setValue('N');
   mygrid.cells(rowid,mygrid.getColIndexById("currencyId")).setValue("");
   mygrid.cells(rowid,mygrid.getColIndexById("remittanceId")).setValue("");
   mygrid.cells(rowid,mygrid.getColIndexById("defaultCurrency")).setValue(false);
   mygrid.cells(rowid,mygrid.getColIndexById("deleteCurrency")).setValue(false);
   mygrid.cells(rowid,mygrid.getColIndexById("enteredByName")).setValue($v("personnelName"));
   mygrid.cells(rowid,mygrid.getColIndexById("enteredDate")).setValue($v("toDate"));

}



function checkOne(rowId) {
	for(var i=0;i<mygrid.getRowsNum();i++){
		cRowid = mygrid.getRowId(i);
		
		if( !$("defaultCurrency"+rowId).disabled && $("defaultCurrency"+rowId).checked && cRowid != rowId && $("defaultCurrency"+cRowid).checked) {
			$("defaultCurrency"+cRowid).checked = false;
			updateHchStatusA("defaultCurrency"+cRowid);
		}
	} 
}

