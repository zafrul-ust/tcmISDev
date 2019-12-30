var selectedrow=null;

/*The reason for this is to show the error messages from the main page*/
function showErrorMessages()
{
  parent.showErrorMessages();
}

function myOnload()
{
   setResultFrameSize();
   parent.document.getElementById("mainUpdateLinks").style["display"] = "";
}

function selectRow(rowid)
{
    //highlight selected row if the personnel is active
   {
    var selectedRow = document.getElementById("rowId"+rowid+"");
    var selectedRowClass = document.getElementById("colorClass"+rowid+"");
    selectedRow.className = "selected"+selectedRowClass.value+"";

    //update selected Item display
    var selectedItem = parent.document.getElementById("selectedItem");
    var currentItem = document.getElementById("itemId"+rowid+"");
    selectedItem.innerHTML = '<a href="#" onclick="selectedItem(); return false;">'+messagesData.selectedItem+' : '+currentItem.value+'</a> |';
    //set variable to main
    parent.returnSelectedItemId = document.getElementById("itemId"+rowid+"").value;
    /*parent.displayElementId = document.getElementById("displayElementId").value;
    parent.valueElementId = document.getElementById("valueElementId").value;*/

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