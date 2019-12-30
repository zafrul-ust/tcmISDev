// Generated id and name listed here, search and replace with the correct values.
// Level 0 id and name: companyId hubName
// Level 1 id and name: plantId bldgIg
// Level 2 id and name: bldgId Level2Name
// Level 3 id and name: inventoryGroup inventoryGroupName
// top commons for all...
function $(a) {
	return document.getElementById(a);
}
// the set function that called on onload
function setTopDropDown() {
	var oldL0 = null;
	var oldL1 = null;
	var oldL2 = null;
	var oldL3 = null;
	try {
	var oldL0 = $("oldhub").value;
	var oldL1 = $("oldplantId").value;
	var oldL2 = $("oldbldgId").value;
	var oldL3 = $("oldinventoryGroup").value;
	} catch (ex) {}
	showL0(oldL0,oldL1,oldL2,oldL3);
;}
// Show drop downs with particular value
function showL0(L0value,L1value,L2value,L3value) {
	var idArray = new Array("");
//if use default value instead of empty value, change the below (1) line
	var nameArray = new Array(emptyValues[0]);
	var idArray1 = null;
	var nameArray1 = null;
	try{
		idArray1 = althub;	nameArray1 = althubName;
	}catch(ex){}
	if( idArray1 != null && nameArray1 != null )  {
	idArray = idArray1;
	nameArray = nameArray1;
	}
	var reset = $("hub");
	for (var i = reset.length; i > 0; i--) {
		reset[i] = null;
	}
	var selectI = 0;
	if(nameArray.length == 1 ) { // empty or default value. 
		setOption(0,nameArray[0],idArray[0],"hub");
	}
	else
	if(nameArray.length == 2 ) { // only one value.
		setOption(0,nameArray[1],idArray[1],"hub");
	}
	else
	for (var i=0; i < nameArray.length; i++) 
		{
			setOption(i,nameArray[i],idArray[i],"hub");
			if( L0value == idArray[i] )
				selectI = i;
		}
	$("hub").selectedIndex = selectI;
	showL1($("hub").value,L1value,L2value,L3value);
}
function showL1(L0value,L1value,L2value,L3value) {
	var idArray = new Array("");
//if use default value instead of empty value, change the below (1) line
	var nameArray = new Array(emptyValues[1]);
	var idArray1 = null;
	var nameArray1 = null;
	try{
		idArray1 = altplantId[L0value];	nameArray1 = altbldgIg[L0value];
	}catch(ex){}
	if( idArray1 != null && nameArray1 != null )  {
	idArray = idArray1;
	nameArray = nameArray1;
	}
	var reset = $("plantId");
	for (var i = reset.length; i > 0; i--) {
		reset[i] = null;
	}
	var selectI = 0;
	if(nameArray.length == 1 ) { // empty or default value. 
		setOption(0,nameArray[0],idArray[0],"plantId");
	}
	else
	if(nameArray.length == 2 ) { // only one value.
		setOption(0,nameArray[1],idArray[1],"plantId");
	}
	else
	for (var i=0; i < nameArray.length; i++) 
		{
			setOption(i,nameArray[i],idArray[i],"plantId");
			if( L1value == idArray[i] )
				selectI = i;
		}
	$("plantId").selectedIndex = selectI;
	showL2($("hub").value,$("plantId").value,L2value,L3value);
}
function showL2(L0value,L1value,L2value,L3value) {
	var idArray = new Array("");
//if use default value instead of empty value, change the below (1) line
	var nameArray = new Array(emptyValues[2]);
	var idArray1 = null;
	var nameArray1 = null;
	try{
		idArray1 = altbldgId[L0value][L1value];	nameArray1 = altfacilityId[L0value][L1value];
	}catch(ex){}
	if( idArray1 != null && nameArray1 != null )  {
	idArray = idArray1;
	nameArray = nameArray1;
	}
	var reset = $("bldgId");
	for (var i = reset.length; i > 0; i--) {
		reset[i] = null;
	}
	var selectI = 0;
	if(nameArray.length == 1 ) { // empty or default value. 
		setOption(0,nameArray[0],idArray[0],"bldgId");
	}
	else
	if(nameArray.length == 2 ) { // only one value.
		setOption(0,nameArray[1],idArray[1],"bldgId");
	}
	else
	for (var i=0; i < nameArray.length; i++) 
		{
			setOption(i,nameArray[i],idArray[i],"bldgId");
			if( L2value == idArray[i] )
				selectI = i;
		}
	$("bldgId").selectedIndex = selectI;
	showL3($("hub").value,$("plantId").value,$("bldgId").value,L3value);
}
function showL3(L0value,L1value,L2value,L3value) {
	var idArray = new Array("");
//if use default value instead of empty value, change the below (1) line
	var nameArray = new Array(emptyValues[3]);
	var idArray1 = null;
	var nameArray1 = null;
	try{
		idArray1 = altinventoryGroup[L0value][L1value][L2value];	nameArray1 = altinventoryGroupName[L0value][L1value][L2value];
	}catch(ex){}
	if( idArray1 != null && nameArray1 != null )  {
	idArray = idArray1;
	nameArray = nameArray1;
	}
	var reset = $("inventoryGroup");
	for (var i = reset.length; i > 0; i--) {
		reset[i] = null;
	}
	var selectI = 0;
	if(nameArray.length == 1 ) { // empty or default value. 
		setOption(0,nameArray[0],idArray[0],"inventoryGroup");
	}
	else
	if(nameArray.length == 2 ) { // only one value.
		setOption(0,nameArray[1],idArray[1],"inventoryGroup");
	}
	else
	for (var i=0; i < nameArray.length; i++) 
		{
			setOption(i,nameArray[i],idArray[i],"inventoryGroup");
			if( L3value == idArray[i] )
				selectI = i;
		}
	$("inventoryGroup").selectedIndex = selectI;
}

function hubChanged() {
		showL1($("hub").value,null,null,null);
}
function plantChanged() {
		showL2($("hub").value,$("plantId").value,null,null);
}
function bldgChanged() {
		showL3($("hub").value,$("plantId").value,$("bldgId").value,null);
}


function submitSearchForm()
{
	var now = new Date();
    document.getElementById("startSearchTime").value = now.getTime();
	  if ( validateSearchForm() == false )
    {
     	return false;
    }
    var userAction  =  document.getElementById("userAction");
    userAction.value = "search";
    document.genericForm.target='resultFrame';
    parent.showPleaseWait();
    return true;
}

function generateExcel()
{
  	var isValidForm = validateSearchForm();
  	if ( isValidForm )
  	{ 
  		var action = document.getElementById("userAction");
    	action.value = 'createExcel';
    	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_newGenerateExcel','800','600','yes');
    	document.genericForm.target='_newGenerateExcel';
    	var a = window.setTimeout("document.genericForm.submit();", 500);
  	}
}

function validateSearchForm()
{
	var errorMessage = "";

	var errorCount = 0;

	var searchField  =  document.getElementById("searchField").value;
	//alert("searchField = [" + searchField + "]; ");
  	if(searchField == 'itemId')
  	{
  		var searchArgument  =  document.getElementById("searchArgument");
    	if(!isInteger(searchArgument.value.trim(), true))
    	{
    		errorMessage = errorMessage + messagesData.itemInteger + "\n" ;
      		errorCount++;
    	}
  	}

	if (errorCount > 0)
	{
  		alert(errorMessage);
  		return false;
	}
	else
	{
  		return true;
	}
}