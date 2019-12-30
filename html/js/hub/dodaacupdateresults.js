function showErrorMessages()
{
    parent.showErrorMessages();
}

function myResultOnload()
{
    displaySearchDuration();
    setResultFrameSize();
    if (!showUpdateLinks)
    {
        parent.document.getElementById("updateResultLink").style["display"] = "none";
    }
    else
    {
        parent.document.getElementById("updateResultLink").style["display"] = "";
    }
}
var selectedRowId = null;
var previousRowId = null;
var previousClass = null;
function highlightRow(rowid)
{
    var selectedRow = document.getElementById("rowId" + rowid);
    var selectedRowClass = document.getElementById("colorClass" + rowid);
    selectedRow.className = "selected" + selectedRowClass.value + "";
    //reset previous selected row back to it original color
    if (previousRowId != null && rowid != previousRowId)
    {
        var previousRow = document.getElementById("rowId" + previousRowId);
        var previousRowClass = document.getElementById("colorClass" + previousRowId);
        previousRow.className = previousRowClass.value;
    }
    selectedRowId = rowid;
    previousRowId = rowid;
    selectedRow = selectedRowClass;

}

function countryChanged(rowid) {
  var country0 = $('countryAbbrev_'+rowid);
  var state0 = $('stateAbbrev_'+rowid);
  var selectedCountry = country0.value;
  for (var i = state0.length; i > 0; i--) {
    state0[i] = null;
  }

  showStateOptions(rowid,selectedCountry);
  state0.selectedIndex = 0;

}

function showStateOptions(rowid,selectedCountry) {
  var stateArray = new Array();
  stateArray = altState[selectedCountry];
  var sid = 'stateAbbrev_'+rowid;

  if( stateArray == null ) {
	setOption(0,messagesData.pleaseselect,"", sid );
	return;
  }


  var stateNameArray = new Array();
  stateNameArray = altStateName[selectedCountry];
  var start = 0 ;
  if( stateArray.length !=1 ) {
	setOption(0,messagesData.pleaseselect,"", sid );
        start = 1;
  }
  for (var i=0; i < stateArray.length; i++) 
    setOption(start++,stateNameArray[i],stateArray[i], sid);
}
