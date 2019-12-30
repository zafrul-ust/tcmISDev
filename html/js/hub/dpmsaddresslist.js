function init() { /*This is to initialize the YUI*/
 this.cfg = new YAHOO.util.Config(this);
 if (this.isSecure)
 {
  this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
 }
}

function onLoadSearch() {
 setSearchFrameSize();
 var now = new Date();
 document.getElementById("startSearchTime").value = now.getTime();
}
 
/*The reason for this is to show the error messages from the main page*/
function showErrorMessages()
{
  parent.showErrorMessages();
}

function myResultOnload()
{
 displaySearchDuration(); 
 setResultFrameSize();
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
}

var selectedRowId=null;
var previousRowId=null;
var previousClass=null;



function submitSearchListForm()
{
 var now = new Date();
 document.getElementById("startSearchTime").value = now.getTime();
 document.genericForm.target="resultFrame";
 var flag = validateForm();
  if(flag) {
   parent.showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}


function validateForm() {
  var flag = true;
  var dodaac = document.getElementById("dodaac");
  var searchText = document.getElementById("searchText");
  if((dodaac == null || dodaac.value.length == 0) && (searchText == null || searchText.value.length == 0)) {
      //alert('DODAAC or Search Text is required');
      alert(messagesData.dodaacorsearchtextrequired);
      flag = false;
  }
  return flag;
}





function selectRow(rowId)
{
   //alert("rowId  "+rowId+"");
    var selectedRow = document.getElementById("rowId"+rowId+"");
    var selectedRowClass = document.getElementById("colorClass"+rowId+"");
	selectedRow.className = "selected"+selectedRowClass.value+"";

    if (previousRowId != null && rowId != previousRowId) {
      var previousRow = document.getElementById("rowId"+previousRowId+"");
	  var previousRowClass = document.getElementById("colorClass"+previousRowId+"");
      previousRow.className = previousRowClass.value;
     }

     selectedRowId =rowId;
    previousRowId = rowId;
    selectedRow = selectedRowClass;
    var selectedRowDodaac = document.getElementById("dodaac"+rowId+"");
    var selectedDodaac = document.getElementById("selectedDodaac");
    selectedDodaac.value = selectedRowDodaac.value;

    var selectedRowDodaacType = document.getElementById("dodaacType"+rowId+"");
    var selectedDodaacType = document.getElementById("selectedDodaacType");
    selectedDodaacType.value = selectedRowDodaacType.value;

    var selectedRowLocationId = document.getElementById("locationId"+rowId+"");
    var selectedLocationId = document.getElementById("selectedLocationId");
    selectedLocationId.value = selectedRowLocationId.value;

    var selectedRowAddress = document.getElementById("address"+rowId+"");
    var selectedAddress = document.getElementById("selectedAddress");
    selectedAddress.value = selectedRowAddress.value;
}



