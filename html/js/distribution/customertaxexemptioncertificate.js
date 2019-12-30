var beangrid;

var resizeGridWithWindow = true;

var windowCloseOnEsc = true;

var inputSize= new Array();
inputSize={"stateAbbrev":50,"countryAbbrev":2};

var maxInputLength = new Array();
maxInputLength={"stateAbbrev":50,"countryAbbrev":2};

var taxSelectedRowId = null;
var taxrowids = new Array();


//var maxInputLength = new Array();
//maxInputLength={"countryAbbrev":20,"stateAbbrev":20};

function resultOnLoad()
{	
/* try
 {
 if (!showUpdateLinks) Dont show any update links if the user does not have permissions
 {
  parent.document.getElementById("updateResultLink").style["display"] = "none";
 }
 else
 {
  parent.document.getElementById("updateResultLink").style["display"] = "";
 }
 }
 catch(ex)
 {}
	
 totalLines = document.getElementById("totalLines").value;
 
 
 if (totalLines > 0)
 {
  document.getElementById("billToCustTaxExempViewBean").style["display"] = "";
  
  initializeGrid();  
 }  
 else
 {
   document.getElementById("billToCustTaxExempViewBean").style["display"] = "none";   
 }*/

	
  document.getElementById("billToCustTaxExempViewBean").style["display"] = "";
  //document.getElementById("mainUpdateLinks").style["display"] = "";
  
if( typeof( jsonMainData ) == 'undefined' )
{
	$('totalLines').value =1;
}	
 loadtaxrowids();	
	  
 initializeGrid(); 
 
 if( typeof( jsonMainData ) != 'undefined' )
 {
	 displayNoSearchSecDuration();
 }
 
 
 
 if(beangrid.getRowsNum()>0)
 {
	if(showUpdateLinks)
	$('removeLink').style.display="";
 }
 /*It is important to call this after all the divs have been turned on or off.*/
 setResultSize();
 
 if(showUpdateLinks)
 document.getElementById("mainUpdateLinks").style["display"] = "";
}


function selectRightclick(rowId,cellInd){
	beangrid.selectRowById(rowId,null,false,false);
	
}


function selectRow(rowId,cellInd) {	
	taxSelectedRowId = rowId;
}

function initializeGrid(){
	 beangrid = new dhtmlXGridObject('billToCustTaxExempViewBean');

	 initGridWithConfig(beangrid,config,false,true);
	 if( typeof( jsonMainData ) != 'undefined' ) {
	 beangrid.parse(jsonMainData,"json");

	 }
	 beangrid.attachEvent("onRightClick",selectRightclick);
	 beangrid.attachEvent("onRowSelect",selectRow);
	 
	}

function validateForm()
{
 var beanGridLen = beangrid.getRowsNum(); 
 var errorMessage = messagesData.validvalues + "\n\n";
 var errorCount = 0;
 var errorCount2 = 0;
 
	 if( beangrid.getRowsNum() != 0 ) {
	 for( rowId in taxrowids ) {
		 if( !gridCellValue(beangrid,rowId,"taxExemptionCertificate") ) {
			 errorCount = 1;			  
		 }
		 if( !gridCellValue(beangrid,rowId,"expirationDateStr") ) {
			 errorCount2 = 1;			  
		 }
	  }
	}
	
	  if (errorCount + errorCount2 > 0) {
		  if (errorCount > 0) 
			  errorMessage += messagesData.taxExemptionCertificate+  "\n";
		  if (errorCount2 > 0) 
			  errorMessage += messagesData.expirationDate+  "\n";
		  alert(errorMessage);
		  return false;
	  }
	
	return true;
}

function getValidCountryAbbrev() {
	var cArray = new Array();
	for(i=0;i<shipToCountryAbbrev.length;i++) {
		for(j=0;j<validTaxCountryAbbrev.length;j++) {
			if( shipToCountryAbbrev[i].value == validTaxCountryAbbrev[j] ) {
				cArray[cArray.length] = {text:shipToCountryAbbrev[i].text,value:shipToCountryAbbrev[i].value};
			}
		}
	}
	return cArray;
}


function taxCountryChanged(rowId,columnId,stateval) {
	
	  var selectedCountry = gridCellValue(beangrid,rowId,columnId);
	  var state0 = $("stateAbbrev"+rowId);
	  for (var i = state0.length; i > 0; i--) {
	    state0[i] = null;
	  }
	  var selectedIndex = 0 ;
	  var stateArray = altState[selectedCountry];
	  var stateNameArray = altStateName[selectedCountry];
	  
	  if(stateArray.length == 0) {
	    setOption(0,messagesData.all,"", "stateAbbrev"+rowId)
	  }
	
	  for (var i=0; i < stateArray.length; i++) {
	    setOption(i,stateNameArray[i],stateArray[i], "stateAbbrev"+rowId);
	    if( stateArray[i] == stateval ) selectedIndex = i;
	  }
	  state0.selectedIndex = selectedIndex;
}


function buildStateAbbrev(selectedCountry) {
	  var stateArray = altState[selectedCountry];
	  var stateNameArray = altStateName[selectedCountry];
	  var stateGridArray = new Array();	 
	  if( stateArray.length == 0) {
	    stateGridArray[0] = {text:messagesData.all,value:''};
	  }
	
	  for (var i=0; i < stateArray.length; i++) {
		    stateGridArray[i] = {text:stateNameArray[i],value:stateArray[i]};
	  }
	  return stateGridArray;
}


function addRecord()
{
	var rowid = (new Date()).valueOf();
	ind = beangrid.getRowsNum();
    count = 0 ;
    stateAbbrev[ rowid ] = buildStateAbbrev('USA');
    var thisrow = beangrid.addRow(rowid,"",ind);	   
    beangrid.cells(rowid, count++).setValue('Y');
    beangrid.cells(rowid, count++).setValue('USA');
    beangrid.cells(rowid, count++).setValue('AL');
    beangrid.cells(rowid, count++).setValue("");  
    beangrid.cells(rowid, count++).setValue(""); 
    beangrid.cells(rowid, count++).setValue($v('customerId')); 
    beangrid.cells(rowid, count++).setValue("");
    taxrowids[""+rowid] = rowid;
    taxSelectedRowId = rowid;
    beangrid.selectRow(beangrid.getRowIndex(rowid));
    $('removeLink').style.display="";
    taxCountryChanged(rowid,"countryAbbrev");    
   // $('updateData').style["display"] = "";    
}

function removeRecord()
{
	beangrid.deleteRow(taxSelectedRowId);	
	delete( taxrowids[taxSelectedRowId] ) ;
	if( beangrid.getRowsNum() == 0 ) 
	{	
	$('removeLink').style["display"] = "none";
	
	}	
	return ;
}

function loadtaxrowids() {
	if( typeof( jsonMainData ) != 'undefined' ) {
	
	var up = jsonMainData.rows.length
	for(i=1;i<= up; i++)
		taxrowids[""+i] = i;
	 }
}


function update() {
    
    var isValid = validateForm();
    if(isValid)
    {	
	var now = new Date();
	$("startSearchTime").value = now.getTime();
	$("action").value="update";
	document.genericForm.target='';	
	showPleaseWait();
	beangrid.parentFormOnSubmit(); //prepare grid for data sending	 
    document.genericForm.submit();
    }
  
	}