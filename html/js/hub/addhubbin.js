function validateForm() {
   var result = true;
   var errmsg = "";
   
   if ($v("room").trim().length == 0) {
	   errmsg += messagesData.required.replace('{0}',messagesData.room) + '\n';
   }
   
   if ($v("binType").trim().length == 0) {
	   errmsg += messagesData.required.replace('{0}',messagesData.binType) + '\n';
   }
   
   if (errmsg.length > 0) {
	   alert(errmsg);
	   result = false;
   }
   return result;
}

function addBintoMainPage()
{
 var totallines = opener.document.getElementById("totallines").value*1;
 var selectedRow = false;
 var vvHubBin = document.getElementById("vvHubBin").value;
 var itemId = document.getElementById("itemId");
 var selectedRowNumber = document.getElementById("rowNumber").value;

 for ( var rowNumber = 0 ; rowNumber < totallines ; rowNumber ++)
 {
  var mainItemId = opener.document.getElementById("itemId"+rowNumber+"");
  if (mainItemId.value == itemId.value)
  {
   var mainBinO = opener.document.getElementById("bin"+rowNumber+"");
   //alert("Found the line to add Bin "+vvHubBin+"");
   if (selectedRowNumber == rowNumber)
   {
     selectedRow = true;
   }
   else
   {
     selectedRow = false;
   }

   try
   {
     var binName = null;
     binName =  mainBinO.value;
	  if (mainBinO.value == "NONE" || mainBinO.value.length == 0)
	  {
	    mainBinO[0] = null;
       opener.addOptionItem(rowNumber,vvHubBin,vvHubBin,selectedRow);
	  }
	  else
	  {
       opener.addOptionItem(rowNumber,vvHubBin,vvHubBin,selectedRow);
	  }
   }
   catch (ex)
   {
//		alert("error");
   }

  }
 }
 cancel();
}

function addnewBin()
{
 var sourceHub = document.getElementById("sourceHub");
 var sourceHubName = document.getElementById("sourceHubName");

 if (sourceHubName.value.length > 0 && sourceHub.value.length > 0)
 {
  var loc = "addhubbin.do?branchPlant="+sourceHub.value+"&sourceHubName="+escape(sourceHubName.value)+"&userAction=addNewBin&&fromBinManagement=Yes";
  
  if ($v("personnelCompanyId") == 'Radian') 
	  loc = "/tcmIS/hub/" + loc;
  
  try {
 	children[children.length] = openWinGeneric(loc,"addnewBin","600","200","no","80","80");
  } catch (ex){
 	openWinGeneric(loc,"addnewBin","600","200","no","80","80");
  }
 }
}

function cancel()
{
    window.close();
}

function showRoomOptions() 
{
 var selectedHub = $v('branchPlant');
  var roomArray = new Array();  
  if(null!= $('room' ))
  {	
  roomArray = altRoomId[selectedHub];  
 	  
  var obj = $('room');	  
   for (var i = obj.length; i > 0;i--)
   {	  
   obj[i] = null;
   }
  var roomNameArray = new Array();
  roomNameArray = altRoomName[selectedHub]; 
  try { 
	  
  if(null!= roomArray) 
  {
	if(roomArray.length == 0)
	{	
     setOption(0, messagesData.none, "", "room")
	}
  for (var i=0; i < roomArray.length; i++)
  {
    if((roomNameArray[i]=="All"))
    {
    	if (roomArray.length>1)
        {
        setOption(0, messagesData.pleaseselect, "", "room")
        }
        else
        {
            setOption(0, messagesData.none, "", "room")
        }
    }
    else
    {
	setOption(i,roomNameArray[i],roomArray[i], "room")
    }
  }
   
  }
  }
  
  catch (e) {
	  alert(e);
		// TODO: handle exception
	}
  
  
  if($('selectedRoom').value.trim().length > 0 )
  {
	  setSelect("room",$v('selectedRoom'));	  
  }
 }
}

function createnewroom() {	
	//var loc = "/tcmIS/hub/addnewroom.do?branchPlant="+$v('branchPlant')+"&sourceHubName="+$v('sourceHubName')+"&userAction=showNewRoom";
	var loc = "addnewroom.do?branchPlant="+$v('branchPlant')+"&sourceHubName="+escape($v('sourceHubName'))+"&userAction=showNewRoom";
	
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;
	
	openWinGeneric(loc,"addnewRoom","600","200","yes","80","80");	
}

function refreshAddBinsPage(refreshFlag, selectedRoom, sourceHub,sourceHubName )
{		
	if(refreshFlag)
	{		
	$('selectedRoom').value=selectedRoom;
	document.genericForm.target='';
	var action = document.getElementById("userAction");	
	action.value="addNewBin";
	$('branchPlant').value=sourceHub;
	$('sourceHubName').value=sourceHubName;	
	
	//var now = new Date();
    //var startSearchTime = document.getElementById("startSearchTime");
	//startSearchTime.value = now.getTime();
	document.genericForm.submit();
	}
}

function submitBinAdd() {
	return validateForm();
}


function newBinsWinlose()
{
	if(fromBinManagement)
	{ 		
		window.opener.refreshPage();    	   
        window.close();
		
	}	

}

function doUploadTemplate() {
	
	  var loc = "addhubbin.do?branchPlant="+$v('branchPlant')+"&userAction=uploadNewBins";
	  document.genericForm.target='UploadNewBins';	  
	  if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;
	  
	  try {
	 	children[children.length] = openWinGeneric(loc,"addnewBin","600","200","no","80","80");
	  } catch (ex){
	 	openWinGeneric(loc,"UploadNewBins","600","200","no","80","80");
	  }
	 
	return  false;		
}
