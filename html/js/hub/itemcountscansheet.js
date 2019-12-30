function hubChanged() {

  var hubO = document.getElementById("hub");
  var igO = document.getElementById("inventoryGroup");
  var selectedHub = hubO.value;

  for (var i = igO.length; i > 0; i--) {
    igO[i] = null;
  }
  showIgOptions(selectedHub);
  igO.selectedIndex = 0;
}

function showIgOptions(selectedHub) {
  var igArray = new Array();
  igArray = altInventoryGroupId[selectedHub];

  var igNameArray = new Array();
  igNameArray = altInventoryGroupName[selectedHub];

  if(igArray.length == 0) {
    setOption(0,"All","", "inventoryGroup")
  }

  for (var i=0; i < igArray.length; i++) {
    setOption(i,igNameArray[i],igArray[i], "inventoryGroup")
  }
}


function checkAllCheckBoxes(checkBoxName)
{
var checkAll = document.getElementById("checkAll");
var totalLines = document.getElementById("totalLines").value;
totalLines = totalLines*1;

var result ;
var valueq;
if (checkAll.checked)
{
  result = true;
  //valueq = "yes";
}
else
{
  result = false;
  //valueq = "no";
}

 for ( var p = 0 ; p < totalLines ; p ++)
 {
	try
	{
	var lineCheck = document.getElementById(""+checkBoxName+""+(p*1)+"");
	lineCheck.checked =result;
	//linecheck.value = valueq;
	}
	catch (ex1)
	{

	}
 }
}

function printScanSheet() {
  openWinGeneric('/tcmIS/common/loadingpleasewait.jsp','_printScanSheet','650','600','yes');
  document.genericForm.target='_printScanSheet';

  //var lastSearchHub = document.getElementById("lastSearchHub");
  //var lastSearchInventoryGroup = document.getElementById("lastSearchInventoryGroup");
  //var lastSearchItemType = document.getElementById("lastSearchItemType");
  //document.genericForm.action='/tcmIS/hub/itemcountscansheet.do?submitPrint=printScanSheet&hub='+lastSearchHub.value+'&inventoryGroup='+lastSearchInventoryGroup.value+'&itemtype='+lastSearchItemType.value+'';
  document.genericForm.action='/tcmIS/hub/itemcountscansheet.do?submitPrint=printScanSheet';
  document.genericForm.submit();
  //window.setTimeout("document.genericForm.submit();",500);
}
