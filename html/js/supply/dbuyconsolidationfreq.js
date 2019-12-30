var resizeGridWithWindow = true;

var multiplePermissions = true;
var beanGrid;
var dhxFreezeWins = null;
var children = new Array();

var permissionColumns = new Array();
permissionColumns={"inventoryGroupName":true,"okDoUpdate":true,"supplyPath":true,"runTime":true,"runDay":true};

function submitSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
 document.genericForm.target='resultFrame';
 document.getElementById("uAction").value = 'search'; 
 //set start search time
 var now = new Date();
 var startSearchTime = document.getElementById("startSearchTime");
 startSearchTime.value = now.getTime();
 var flag = validateForm();
 if(flag) {
   showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}

function validateForm(){
	return true;
}

function doUpdate() {
	  numberOfRows = $v('totalLines');
	   var flag = validateUpdateForm(numberOfRows);
	  if(flag) {
		document.getElementById("uAction").value = 'update';
	    
	    beanGrid.parentFormOnSubmit();
	    document.genericForm.submit();
		parent.showPleaseWait();
	  }
	  return flag;
}

function doDelete() {
	  numberOfRows = $v('totalLines');
	  
	  var flag = validateDeleteForm(numberOfRows);
	  if(flag) {
		document.getElementById("uAction").value = 'delete';
	    
	    beanGrid.parentFormOnSubmit();
	    document.genericForm.submit();
		parent.showPleaseWait();
	  }
	  return flag;
}

function validateDeleteForm(numberOfRows) {
	  var flag = true;
	  var selected = false;
	  if(numberOfRows != null) {
	    for(var i=1; i<=numberOfRows; i++) {
		  var checked = false;
		  try {
			  checked = cellValue(i,'okDoUpdate');
		  } catch(ex){}
		  if( !checked ) continue;
		  selected = true;
	    }
	  }
		  if( !selected ) {
			  alert(messagesData.norowselected);
			  return false;
		  }	  
		  
		  return flag;
	}
		  

function validateUpdateForm(numberOfRows) {
	var errorMessage = messagesData.validvalues+"\n";;
	var errorCount = 0;
	  var flag = true;
	  var selected = false;
	  if(numberOfRows != null) {
	    for(var i=1; i<=numberOfRows; i++) {
		  var checked = false;
		  try {
			  checked = cellValue(i,'okDoUpdate');
		  } catch(ex){}
		  if( !checked ) continue;
		  selected = true;
		  
		  var inventoryGroup = cellValue(i,"inventoryGroupName");
		  var supplyPath = cellValue(i,"supplyPath");
		  var runTime = cellValue(i,"runTime");
		  var runDay = cellValue(i,"runDay");
		  var found = false;
		  var found1 = false;
		  var found2 = false;
		  var found3 = false;
		  if (supplyPath.trim() == "-1") {
	        found = true;
	         }    
		  if (runTime.trim() == "-1") {
		        found1 = true;
		      }
		  if (runDay != null && runDay.trim() == "-1") {
		        found2 = true;
		      }
		  if (inventoryGroup != null && inventoryGroup== "") {
			  found3 = true;
		      }
	            
	    }
	  }
	 
	  if( found ) {
		  errorMessage += messagesData.supplyPath+"\n";
		  errorCount = 1;
	  } 
	  if( found1 ) {
		  errorMessage += messagesData.runTime+"\n";
		  errorCount = 1;
	  } 
	  if(found2) {
		  errorMessage +=  messagesData.runDay+"\n";
		    errorCount = 1;
	  }
	  if(found3) {
		  errorMessage +=  messagesData.inventoryGroup+"\n\n";
		    errorCount = 1;
	  }
	  if( !selected ) {
		  errorMessage +=  messagesData.norowselected;
		    errorCount = 1;
	  }
	  if(errorCount >0 ) {
	  	   alert(errorMessage);
	  	  
		  return false;
	  }   
	  
	  return flag;
}
function showPageWait() {
	
	  var mainPage = parent.document.getElementById("mainPage");
	    mainPage.style["display"] = "none";
	  var transitPage = parent.document.getElementById("transitPage"); 
	    transitPage.style["display"] = "none";
	}



function onloadset() {

	
	 /*Dont show any update links if the user does not have permissions.
	 Remove this section if you don't have any links on the main page*/
	 if (!showUpdateLinks)
	 {
	  parent.document.getElementById("updateResultLink").style["display"] = "none";
	 }
	 else
	 {
	  parent.document.getElementById("updateResultLink").style["display"] = "";
	 }

		var totalLines = document.getElementById("totalLines").value;
		if (totalLines > 0) {
			// make result area visible if data exist
			document.getElementById("dBuyConsFreqBean").style["display"] = "";
			// build the grid for display
			doInitGrid();
		}
		else {
			document.getElementById("dBuyConsFreqBean").style["display"] = "none";
		}
		setResultFrameSize();
	    displayGridSearchDuration();
	
}	

function doInitGrid() {
	 beanGrid = new dhtmlXGridObject('dBuyConsFreqBean');
	// initGridWithConfig(inputGrid,config,rowSpan,submitDefault,singleClickEdit)
	// if rowSpan == true, sorting and smart rendering won't work; if
	// rowSpan == false, sorting and smart rendering will work
	// rowSpan == -1 is recommended for the page with update function
	// -1 is for disable rowSpan and smart rendering, but sorting still
	// works; false will disable rowSpan and sorting but smart rendering is
	// enabled
	// set submitDefault to true: Send the data to the server
	// singleClickEdit: this is for type:"txt",
	initGridWithConfig(beanGrid, config, -1, true, true);
	if (typeof (jsonMainData) != 'undefined') {
		beanGrid.parse(jsonMainData, "json");
	}

	// set all kinds of event for the grid. refer to http://www.dhtmlx.com
	// for more events
	// mygrid.attachEvent("onBeforeSelect",doOnBeforeSelect);
	beanGrid.attachEvent("onRowSelect", doOnRowSelected);
	beanGrid.attachEvent("onRightClick", selectRightclick);
}

function addNew(){
    try
    {
	if(beanGrid == null || selectedRowId == null) {
	     return false; 
	   } 
            
    var ind = beanGrid.getRowsNum();  
    var rowid = ind*1+1;
    var thisrow = beanGrid.addRow(rowid,"",ind);
    
    beanGrid.cells(rowid,beanGrid.getColIndexById("permission")).setValue('Y');
    beanGrid.cells(rowid,beanGrid.getColIndexById("inventoryGroupNamePermission")).setValue('Y');
    beanGrid.cells(rowid,beanGrid.getColIndexById("inventoryGroupName")).setValue('');
    beanGrid.cells(rowid,beanGrid.getColIndexById("needByTolerance")).setValue('');
	//if($v("inventoryGroup"+rowid).length > 0)
    var inventoryGroupName = $v("inventoryGroup");
    if(inventoryGroupName != null && inventoryGroupName != "")
    	$("inventoryGroupName"+rowid).value = $v("inventoryGroup"); 
    else
    	{
        var elOptNew = document.createElement('option');
	    elOptNew.text = 'Please Select';
	    elOptNew.value = '';
	    var ig = document.getElementById("inventoryGroupName"+rowid);
	    try {
	    	ig.add(elOptNew, ig.options[0]); // standards compliant; doesn't work in IE
	      }
	      catch(ex) {
	  	    ig.add(elOptNew,0);
	      }
		ig.options[0].selected = true;
    }
    beanGrid.cells(rowid,beanGrid.getColIndexById("okDoUpdatePermission")).setValue('Y');
    beanGrid.cells(rowid,beanGrid.getColIndexById("okDoUpdate")).setValue(false);
    beanGrid.cells(rowid,beanGrid.getColIndexById("supplyPathPermission")).setValue('Y');
    beanGrid.cells(rowid,beanGrid.getColIndexById("supplyPath")).setValue('');
    beanGrid.cells(rowid,beanGrid.getColIndexById("runDayPermission")).setValue('Y');
    beanGrid.cells(rowid,beanGrid.getColIndexById("runDay")).setValue('');
    beanGrid.cells(rowid,beanGrid.getColIndexById("runTimePermission")).setValue('Y');
    beanGrid.cells(rowid,beanGrid.getColIndexById("runTime")).setValue('');
    beanGrid.cells(rowid,beanGrid.getColIndexById("enteredBy")).setValue('');
    beanGrid.cells(rowid,beanGrid.getColIndexById("transactionDate")).setValue('');
    beanGrid.cells(rowid,beanGrid.getColIndexById("opsCompanyId")).setValue(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("opsCompanyId")).getValue());
    beanGrid.cells(rowid,beanGrid.getColIndexById("opsEntityId")).setValue(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("opsEntityId")).getValue());
    beanGrid.cells(rowid,beanGrid.getColIndexById("inventoryGroup")).setValue(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("inventoryGroup")).getValue());
    beanGrid.cells(rowid,beanGrid.getColIndexById("oldRunTime")).setValue('');
    beanGrid.cells(rowid,beanGrid.getColIndexById("oldSupplyPath")).setValue('');
    beanGrid.cells(rowid,beanGrid.getColIndexById("status")).setValue('');
    
    var rowsNum = beanGrid.getRowsNum();
    $("totalLines").value = rowsNum;
    //showPageWait();
    } catch(ex){
        alert(messagesData.norowselected);
    }
}

function dup(){
    try
    {
	if(beanGrid == null || selectedRowId == null) {
	     return false; 
	   } 
            
    var ind = beanGrid.getRowsNum();  
    var rowid = ind*1+1;
    var thisrow = beanGrid.addRow(rowid,"",ind);
    
    beanGrid.cells(rowid,beanGrid.getColIndexById("permission")).setValue('Y');
    beanGrid.cells(rowid,beanGrid.getColIndexById("inventoryGroupNamePermission")).setValue('Y');
    beanGrid.cells(rowid,beanGrid.getColIndexById("inventoryGroupName")).setValue('');
    beanGrid.cells(rowid,beanGrid.getColIndexById("needByTolerance")).setValue(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("needByTolerance")).getValue());
    var igName = beanGrid.cells(selectedRowId,beanGrid.getColIndexById("inventoryGroupName")).getValue();
    var ig = document.getElementById("inventoryGroupName"+rowid);
    for (var i=0 ; i < ig.length; i++)
    {
    	if (ig.options[i].value == igName || ig.options[i].text == igName ){
    		ig.options[i].selected = true;
    		break;
    	}
    }
    beanGrid.cells(rowid,beanGrid.getColIndexById("okDoUpdatePermission")).setValue('Y');
    beanGrid.cells(rowid,beanGrid.getColIndexById("okDoUpdate")).setValue(false);
    beanGrid.cells(rowid,beanGrid.getColIndexById("supplyPathPermission")).setValue('Y');
    beanGrid.cells(rowid,beanGrid.getColIndexById("supplyPath")).setValue('');
    var spName = beanGrid.cells(selectedRowId,beanGrid.getColIndexById("supplyPath")).getValue();
    var sp = document.getElementById("supplyPath"+rowid);
    for (var i=0 ; i < sp.length; i++)
    {
    	if (sp.options[i].value == spName){
    		sp.options[i].selected = true;
    		break;
    	}
    }
    beanGrid.cells(rowid,beanGrid.getColIndexById("runDayPermission")).setValue('Y');
    beanGrid.cells(rowid,beanGrid.getColIndexById("runDay")).setValue('');
    var rdName = beanGrid.cells(selectedRowId,beanGrid.getColIndexById("runDay")).getValue();
    var rd = document.getElementById("runDay"+rowid);
    for (var i=0 ; i < rd.length; i++)
    {
    	if (rd.options[i].value == rdName){
    		rd.options[i].selected = true;
    		break;
    	}
    }
    beanGrid.cells(rowid,beanGrid.getColIndexById("runTimePermission")).setValue('Y');
    beanGrid.cells(rowid,beanGrid.getColIndexById("runTime")).setValue('');
    var rtName = beanGrid.cells(selectedRowId,beanGrid.getColIndexById("runTime")).getValue();
    var rt = document.getElementById("runTime"+rowid);
    for (var i=0 ; i < rt.length; i++)
    {
    	if (rt.options[i].value == rtName){
    		rt.options[i].selected = true;
    		break;
    	}
    }
    beanGrid.cells(rowid,beanGrid.getColIndexById("enteredBy")).setValue('');
    beanGrid.cells(rowid,beanGrid.getColIndexById("transactionDate")).setValue('');
    beanGrid.cells(rowid,beanGrid.getColIndexById("opsCompanyId")).setValue(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("opsCompanyId")).getValue());
    beanGrid.cells(rowid,beanGrid.getColIndexById("opsEntityId")).setValue(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("opsEntityId")).getValue());
    beanGrid.cells(rowid,beanGrid.getColIndexById("inventoryGroup")).setValue(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("inventoryGroup")).getValue());
    beanGrid.cells(rowid,beanGrid.getColIndexById("oldRunTime")).setValue('');
    beanGrid.cells(rowid,beanGrid.getColIndexById("oldSupplyPath")).setValue('');
    beanGrid.cells(rowid,beanGrid.getColIndexById("status")).setValue('');
    
    var rowsNum = beanGrid.getRowsNum();
    $("totalLines").value = rowsNum;
    //showPageWait();
    } catch(ex){
        alert(messagesData.norowselected);
    }
}



function selectRightclick(rowId,cellInd){
	beanGrid.selectRowById(rowId,null,false,false);
	doOnRowSelected(rowId,cellInd);
	toggleContextMenu("newMenu");

}

function doOnRowSelected(rowId,cellInd) {
	 var columnId = beanGrid.getColumnId(cellInd);  
	 selectedRowId = rowId;
}

function createXSL(){
	  if(validateForm()) {
		$('uAction').value = 'createExcel';
	    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_DBuyConsolidationFreqExcel','650','600','yes');
	    document.genericForm.target='_DBuyConsolidationFreqExcel';
	    var a = window.setTimeout("document.genericForm.submit();",50);
	  }
	}

function updateNeedByTolerance(inventoryGroup,needByTolerance)
{
	//parent.showTransitWin(messagesData.needByTolerance);
	for(var i = 1; i <= beanGrid.getRowsNum();i++)
		 if(beanGrid.cells(i,beanGrid.getColIndexById("inventoryGroupName")).getValue() == inventoryGroup.trim())
			 beanGrid.cells(i,beanGrid.getColIndexById("needByTolerance")).setValue(needByTolerance + " Day(s)");
	//closeTransitWin();
}

function editNeedByTolerance()
{
	var inventoryGroupName = beanGrid.cells(selectedRowId,beanGrid.getColIndexById("inventoryGroupName")).getValue();
	var inventoryGroup = beanGrid.cells(selectedRowId,beanGrid.getColIndexById("inventoryGroup")).getValue();
	var needByTolerance = beanGrid.cells(selectedRowId,beanGrid.getColIndexById("needByTolerance")).getValue();
	var loc = "/tcmIS/supply/editneedbytolerance.do?inventoryGroupName="+inventoryGroupName+"&inventoryGroup="+inventoryGroup+"&needByTolerance=" + needByTolerance+"&opsEntityId=" + $('opsEntityId').value;
	parent.showTransitWin(messagesData.needByTolerance);
	parent.children[children.length] = openWinGeneric(loc,"IgdNeedByTolerance","415","215","yes","80","80","yes");
}

function showTransitWin(messageType)
{
var waitingMsg = messagesData.waitingforinputfrom+"...";
 $("transitLabel").innerHTML = waitingMsg.replace(/[{]0[}]/g,messageType);

var transitDailogWin = document.getElementById("transitDailogWin");
 transitDailogWin.innerHTML = document.getElementById("transitDailogWinBody").innerHTML;
 transitDailogWin.style.display="";

 initializeDhxWins();
if (!dhxFreezeWins.window("transitDailogWin")) {
 // create window first time
 transitWin = dhxFreezeWins.createWindow("transitDailogWin",0,0, 400, 200);
  transitWin.setText("");
  transitWin.clearIcon();  // hide icon
 transitWin.denyResize(); // deny resizing
 transitWin.denyPark();   // deny parking

  transitWin.attachObject("transitDailogWin");
 //transitWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
 transitWin.center();
 // setting window position as default x,y position doesn't seem to work, also hidding buttons.
 transitWin.setPosition(300, 200);
  transitWin.button("minmax1").hide();
  transitWin.button("park").hide();
  transitWin.button("close").hide();
  transitWin.setModal(true);
 }else{
 // just show
 transitWin.setModal(true);  // freeze the window here
 dhxFreezeWins.window("transitDailogWin").show();
 }
}

function initializeDhxWins() {
	if (dhxFreezeWins == null) {
	  dhxFreezeWins = new dhtmlXWindows();
	  dhxFreezeWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	 }
	}

function closeTransitWin() {
if (dhxFreezeWins != null) {
 if (dhxFreezeWins.window("transitDailogWin")) {
   dhxFreezeWins.window("transitDailogWin").setModal(false);
   dhxFreezeWins.window("transitDailogWin").hide();
  }
 }
}

function closeAllchildren()
{
// if (document.getElementById("uAction").value != 'new') {
 try {
  for(var n=0;n<children.length;n++) {
   try {
    children[n].closeAllchildren();
    } catch(ex){}
   children[n].close();
   }
  } catch(ex){}
  children = new Array();
// }
}