function init() { /*This is to initialize the YUI*/
 this.cfg = new YAHOO.util.Config(this);
 if (this.isSecure)
 {
  this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
 }

  showErrorMessagesWin = new YAHOO.widget.Panel("errorMessagesArea", { width:"400px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:true, draggable:true, modal:false } );
  showErrorMessagesWin.render();
}

/*The reason for this is to show the error messages from the main page*/
function showErrorMessages()
{
  parent.showErrorMessages();
}


function submitSearchForm()
{
 	var flag = validateForm();
  	if(flag) 
  	{
   		parent.showPleaseWait();
   		return true;
  	}
  	else
  	{
    	return false;
  	}
}

function validateForm()
{
	return true;
}

function myResultsBodyOnload()
{
// alert("myResultsBodyOnload()...");
 setResultFrameSize();
 parent.document.getElementById("selectedRow").innerHTML="";
}

var selectedrow=null;
function selectRow(rowid)
{
    //highlight selected row if the personnel is active
   {
    var selectedRow = document.getElementById("rowId"+rowid+"");
    var selectedRowClass = document.getElementById("colorClass"+rowid+"");
    selectedRow.className = "selected"+selectedRowClass.value+"";

    //update selected user display
    var selectedUser = parent.document.getElementById("selectedRow");
    var currentRow = document.getElementById("locationId"+rowid+"");
    selectedUser.innerHTML = '<a href="#" onclick="selectedRow(); return false;">'+messagesData.selectedRowMsg+' : '+currentRow.value+'</a>';
    //set variable to main
    parent.returnSelectedValue = currentRow.value;
    parent.returnSelectedId    = document.getElementById("locationId"+rowid+"").value;

    //reset previous selected row back to it original color
    if (selectedrow != null && rowid != selectedrow)
    {
       var previouslySelectedRow = document.getElementById("rowId"+selectedrow+"");
       var previouslySelectedRowClass = document.getElementById("colorClass"+selectedrow+"");
       previouslySelectedRow.className = ""+previouslySelectedRowClass.value+"";
    }
    selectedrow =rowid;
   }
}
