function init()
{
	this.cfg = new YAHOO.util.Config(this);
	if (this.isSecure)
	{
 	this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
	}

	/*Yui pop-ups need to be initialized onLoad to make them work correctly.
	If they are not initialized onLoad they tend to act weird*/
	showErrorMessagesWin = new YAHOO.widget.Panel("errorMessagesArea", { width:"300px", height:"300px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:false,
	draggable:true, modal:false } );
	showErrorMessagesWin.render();
}

/*The reason this is here because you might want a different width/proprties for the pop-up div depending on the page*/
function showErrorMessages()
{
  	var resulterrorMessages = window.frames["resultFrame"].document.getElementById("errorMessagesAreaBody");
  	var errorMessagesAreaBody = document.getElementById("errorMessagesAreaBody");
  	errorMessagesAreaBody.innerHTML = resulterrorMessages.innerHTML;

  	showErrorMessagesWin.show();

  	var errorMessagesArea = document.getElementById("errorMessagesArea");
  	errorMessagesArea.style.display="";
}

function showItemManagementLegend()
{
  	openWinGeneric("/tcmIS/help/itemManagementLegend.html","ItemManagementLegend","290","300","no","50","50")
}
/*
function doSubmitUpdates()
{

    	//Set any variables you want to send to the server
    	var userAction = window.frames["resultFrame"].document.getElementById("userAction");
    	userAction.value = 'update';
    	showPleaseWait();
    	//Submit the form in the result frame
   		window.frames["resultFrame"].document.genericForm.submit();
}
*/
/*function validateUpdates()
{
  	var totalLines = window.frames["resultFrame"].document.getElementById("totalLines").value;

  	// alert(" totalLines = " + totalLines );
  	var okCheckedCount = 0;
  	var replenishQtyUpdateCount = 0;
  	var needDateUpdateCount = 0;
  	var icmrcStatusUpdateCount = 0;
  	var updateCount = 0;
  	var errorCount = 0;
  	var validUpdateCount = 0;
  	var errorInUpdate;
  	var validReplenishQtyUpdate;
  	var validStatusUpdate;

  	for (i=0; i < totalLines; i++)
  	{
  		errorInUpdate = 'notyet';
  		validReplenishQtyUpdate = 'notyet';
  		validStatusUpdate = 'notyet';

  		// okDoUpdateX = okDoUpdate + i;
    	// if (okCheckbox.checked)

    	var replenishQtyI = 'replenishQty' + i;
    	var replenishQtyX = window.frames["resultFrame"].document.getElementById( replenishQtyI );
    	//alert('replenishQtyX.value = ' + replenishQtyX.value );
    	if ( replenishQtyX.value.trim().length > 0)
    	{
    		if ( isInteger(replenishQtyX.value.trim(), true) )
    		{
    			var needDateI = 'needDate' + i;
    			var needDateX = window.frames["resultFrame"].document.getElementById( needDateI );
    			if ( needDateX.value.trim().length > 0)
    			{
    				if ( checkdate( needDateX ) )
    				{
    					validReplenishQtyUpdate = 'true';
    					replenishQtyUpdateCount++;
    				}
    				else
    				{
         				//alert("needDate is not a valid date: " + needDateX.value.trim() );
         				errorInUpdate = 'true';
        				errorCount++;
    				}
    			}
    		}
    		else
    		{
         		alert("quantity not an integer: " + replenishQtyX.value.trim() );
         		errorInUpdate = 'true';
        		errorCount++;
    		}
    	}
    	var icmrcStatusI = 'icmrcStatus' + i;
    	var icmrcStatusX = window.frames["resultFrame"].document.getElementById( icmrcStatusI );
     	//alert('new icmrcStatusX.value = ' + icmrcStatusX.value );

    	var current_icmrcStatusI = 'current_icmrcStatus' + i;
    	var current_icmrcStatusX = window.frames["resultFrame"].document.getElementById( current_icmrcStatusI );

    	//alert('current_icmrcStatusX.value = ' + current_icmrcStatusX.value );

    	if ( icmrcStatusX.value.trim().length > 0)
    	{
    		if ( icmrcStatusX.value.trim() != current_icmrcStatusX.value.trim())
    		{
    			validStatusUpdate = 'true';
    			icmrcStatusUpdateCount++;
    		}
    	}
    	if (validReplenishQtyUpdate == 'true' || validStatusUpdate == 'true' )
    	{
    		if ( errorInUpdate == 'notyet')
    		{
    			validUpdateCount++;
    		}
    	}
    }	// end of for-loop;

  	if ( errorCount > 0 || validUpdateCount == 0) // (okCheckedCount > 0)
  	{
     	//alert("Please select..");
     	alert("messagesData.pleaseMakeAValidUpdate");
     	return false;
  	}
  		else
  	{
     	return true;
  	}
}*/
