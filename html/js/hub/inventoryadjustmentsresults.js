var beangrid;
var dhxWins = null;
var resizeGridWithWindow = true;

function resultOnLoad()
{

try
 {

 if (!showUpdateLinks) /*Dont show any update links if the user does not have permissions*/
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
  document.getElementById("pendingInventoryViewBean").style["display"] = "";
  
  initializeGrid();  
 }  
 else
 {
   document.getElementById("pendingInventoryViewBean").style["display"] = "none";   
 }

 displayGridSearchDuration();
 
 /*It is important to call this after all the divs have been turned on or off.*/
 setResultFrameSize();
 
 beangrid.enableEditEvents(true,false,false);
}

function initializeGrid(){
	 beangrid = new dhtmlXGridObject('pendingInventoryViewBean');

	 initGridWithConfig(beangrid,config,false,true);
	 if( typeof( jsonMainData ) != 'undefined' ) {
	 beangrid.parse(jsonMainData,"json");

	 }
	  beangrid.attachEvent("onRightClick",selectRightclick);
	}

function selectRightclick(rowId,cellInd){
	beangrid.selectRowById(rowId,null,false,false);	
	var  contextMenuName ="reject";	
	toggleContextMenu(""+contextMenuName+"");
}


//Function for updating records.
function updateInventoryRecords(act)
{
    /*Set any variables you want to send to the server*/
	for(var i=0;i<haasGrid.getRowsNum();i++){ //alert(i+":  "+cellValue(i+1,"okDisplay"));
		try
		  {
		    var rowSpan = lineMap[i];
		    if( rowSpan == null ) continue;
		    var rowid = i+1;
		    if( cellValue(rowid,"okDisplay")+'' == 'true' ) {
		    	var comment = cellValue(rowid,"reviewerComment");
		    	for(j = 0; j < rowSpan; j++) {
		    		rowid2 = rowid+j;
		    		cell(rowid2,"approve").setValue("Y");
		    		cell(rowid2,"reviewerComment").setValue(comment);
		    	}  
		    }
		  }
		  catch (ex)
		  {
//		    alert("here 269");
		  }
		}
	
//	document.genericForm.target='';
	if( !act ) act = 'approve';
	document.getElementById('action').value = act;
	parent.showPleaseWait();
	beangrid.parentFormOnSubmit(); //prepare grid for data sending	 
    document.genericForm.submit(); 
}


function showRejection()
{
	updateInventoryRecords('reject');
	//showAccountInputDialog();
//	document.getElementById('action').value = 'reject';
//	parent.showPleaseWait();	
//	beangrid.parentFormOnSubmit();
 //   document.genericForm.submit();
}

function initializeDhxWins() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function showAccountInputDialog()
{
	var inputDailogWin = document.getElementById("lotStatusDialogWin");
	inputDailogWin.style.display="";

	initializeDhxWins();
	if (!dhxWins.window("lotStatusDialogWin")) {
		// create window first time
		inputWin = dhxWins.createWindow("errorWin",0,0, 400, 118);
		inputWin.setText(messagesData.lotstatus);
		inputWin.clearIcon();  // hide icon
		inputWin.denyResize(); // deny resizing
		inputWin.denyPark();   // deny parking	
		setOption(0,messagesData.pleaseselect,"", "lotStatusSelectBox");		
		//for ( var i=0; i < somArr.length; i++)
		//{			
			//setOption(i+1,somArr[i].id,somArr[i].id, "lotStatusSelectBox");

		//}		
		inputWin.attachObject("lotStatusDialogWin");
		inputWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
		inputWin.center();	   
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		inputWin.setPosition(328, 131); 
		inputWin.button("close").hide();
		inputWin.button("minmax1").hide();
		inputWin.button("park").hide();
		inputWin.setModal(true);
	}
	else
	{
		// just show
		inputWin.setModal(true);
		dhxWins.window("lotStatusDialogWin").show();
	}


}

function cancelButton() {
	dhxWins.window("errorWin").setModal(false);
	dhxWins.window("errorWin").hide();
}


function validateForm()
{
  var errorMessage = messagesData.validvalues + "\n\n";
  var errorCount = 0;
  var lotStatus = $("lotStatus");
  try {
	if (lotStatus.value.trim().length == 0)
     {
	   errorMessage += messagesData.lotstatus + "\n";
       errorCount = 1;
      }	  
    }
  catch(ex) {
        //alert(ex);
        errorCount++;
      }
  
  if (errorCount > 0) {
	    alert(errorMessage);
	    return false;
	  }

	  return true; 
  
}

function continueButton() {
	
	var isValid = validateForm()
	
	if(isValid)
	{	
	dhxWins.window("errorWin").setModal(false);
	dhxWins.window("errorWin").hide();
	document.genericForm.target='';
	document.getElementById('action').value = 'reject';
	parent.showPleaseWait();	
    document.genericForm.submit();
	}
	
}